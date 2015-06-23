package org.xenei.galway2020.source.twitter;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;

import org.apache.commons.configuration.Configuration;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.rdf.model.ResIterator;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.rdf.model.ResourceFactory;
import org.apache.jena.sparql.vocabulary.FOAF;
import org.apache.jena.util.iterator.ExtendedIterator;
import org.apache.jena.util.iterator.LazyIterator;
import org.apache.jena.util.iterator.NiceIterator;
import org.apache.jena.util.iterator.WrappedIterator;
import org.apache.jena.vocabulary.RDF;
import org.apache.jena.vocabulary.RDFS;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xenei.galway2020.ModelSource;
import org.xenei.galway2020.source.twitter.calls.OAuthSetUp;
import org.xenei.galway2020.source.twitter.writer.StatusToRDF;
import org.xenei.galway2020.vocab.Galway2020;

import twitter4j.Query;
import twitter4j.QueryResult;
import twitter4j.Status;
import twitter4j.TwitterException;

public class TwitterSource implements ModelSource {

	private final Configuration cfg;
	private final OAuthSetUp authorize;
	private final static Logger LOG = LoggerFactory
			.getLogger(TwitterSource.class);

	public final static Resource TWITTER_URL = ResourceFactory
			.createResource("https://twitter.com/");

	/**
	 * Constructs a twitter source.
	 * 
	 * The configuration file should provide the following:
	 * <ul>
	 * <li>consumer.key - OAuth key</li>
	 * <li>consumer.secret - OAuth secret</li>
	 * <li>hashtag - a hashtag (without the #) to follow. There may be multiple
	 * hashtag entries.</li>
	 * <li>user - a user (without the at-sign) to follow. There may be multiple
	 * user entries.</li>
	 * </ul>
	 * 
	 * @param cfg
	 *            The configuration file
	 * @throws TwitterException
	 * @throws IOException
	 */
	public TwitterSource(Configuration cfg) throws TwitterException,
			IOException {
		// read properties from system configuration first.
		this.cfg = cfg;

		authorize = new OAuthSetUp(this.cfg);
	}

	private ExtendedIterator<String> getTopics() {
		return getTopics(Arrays.asList(cfg.getStringArray("hashtag")));
	}

	private ExtendedIterator<String> getTopics(Collection<String> data) {
		return WrappedIterator.create(data.iterator()).mapWith(
				new Function<String, String>() {
					@Override
					public String apply(String t) {
						return String.format("#%s", t);
					}
				});
	}

	private ExtendedIterator<String> getUsers() {
		return getUsers(Arrays.asList(cfg.getStringArray("user")));
	}

	private ExtendedIterator<String> getUsers(Collection<String> data) {
		return WrappedIterator.create(data.iterator()).mapWith(
				new Function<String, String>() {

					@Override
					public String apply(String t) {
						return String.format("@%s", t);
					}
				});
	}

	public class LazyModelIterator extends LazyIterator<Model> {

		private RecordingModelIterator rmi;

		public LazyModelIterator(RecordingModelIterator rmi) {
			this.rmi = rmi;
		}

		@Override
		public Model removeNext() {
			throw new UnsupportedOperationException();
		}

		@Override
		public <X extends Model> ExtendedIterator<Model> andThen(
				Iterator<X> other) {
			return NiceIterator.andThen(this, other);
		}

		@Override
		public List<Model> toList() {
			return NiceIterator.asList(this);
		}

		@Override
		public Set<Model> toSet() {
			return NiceIterator.asSet(this);
		}

		@Override
		public ExtendedIterator<Model> create() {
			return getTopics(rmi.getHashtags()).andThen(
					getUsers(rmi.getUsers())).mapWith(new QueryMap());
		}

	}

	@Override
	public ExtendedIterator<Model> modelIterator() {

		ExtendedIterator<Model> modelIter = getTopics().andThen(getUsers())
				.mapWith(new QueryMap());

		RecordingModelIterator rmi = new RecordingModelIterator(modelIter);

		return rmi.andThen(new LazyModelIterator(rmi));

	}

	private class RecordingModelIterator implements ExtendedIterator<Model> {
		private Set<String> hashtags = new HashSet<String>();
		private Set<String> users = new HashSet<String>();
		private ExtendedIterator<Model> modelIter;

		public RecordingModelIterator(ExtendedIterator<Model> modelIter) {
			this.modelIter = modelIter;
		}

		@Override
		public boolean hasNext() {
			return modelIter.hasNext();
		}

		public Set<String> getHashtags() {
			return hashtags;
		}

		public Set<String> getUsers() {
			return users;
		}

		@Override
		public Model next() {
			Model retval = modelIter.next();
			ResIterator rIter = retval.listSubjectsWithProperty(RDF.type,
					Galway2020.Hashtag);
			while (rIter.hasNext()) {
				Resource r = rIter.next().getPropertyResourceValue(RDFS.label);
				if (r != null) {
					hashtags.add(r.asLiteral().getValue().toString());
				}
			}

			rIter = retval.listSubjectsWithProperty(RDF.type,
					FOAF.OnlineAccount);
			while (rIter.hasNext()) {
				Resource r = rIter.next().getPropertyResourceValue(RDFS.label);
				if (r != null) {
					users.add(r.asLiteral().getValue().toString());
				}
			}

			return retval;

		}

		@Override
		public Model removeNext() {
			return modelIter.removeNext();
		}

		@Override
		public void remove() {
			modelIter.remove();
		}

		@Override
		public <X extends Model> ExtendedIterator<Model> andThen(
				Iterator<X> other) {
			return modelIter.andThen(other);
		}

		@Override
		public void close() {
			modelIter.close();
		}

		@Override
		public ExtendedIterator<Model> filterKeep(Predicate<Model> f) {
			return modelIter.filterKeep(f);
		}

		@Override
		public ExtendedIterator<Model> filterDrop(Predicate<Model> f) {
			return modelIter.filterDrop(f);
		}

		@Override
		public void forEachRemaining(Consumer<? super Model> action) {
			modelIter.forEachRemaining(action);
		}

		@Override
		public <U> ExtendedIterator<U> mapWith(Function<Model, U> map1) {
			return modelIter.mapWith(map1);
		}

		@Override
		public List<Model> toList() {
			return modelIter.toList();
		}

		@Override
		public Set<Model> toSet() {
			return modelIter.toSet();
		}

	}

	/**
	 * Convert a string to a model. Performs the search and returns the result
	 * as a model.
	 * 
	 */
	private class QueryMap implements Function<String, Model> {
		@Override
		public Model apply(String topic) {
			Model model = ModelFactory.createDefaultModel();
			StatusToRDF statusWriter = new StatusToRDF(model);
			Query query = new Query(topic);

			QueryResult result = null;
			do {
				try {
					result = authorize.twitter.search(query);
					for (Status status : result.getTweets()) {
						statusWriter.write(status);
					}
				} catch (TwitterException e) {
					LOG.error("Unable to execute query: " + query, e);
				}
				query = result.nextQuery();
			} while (query != null);

			return model;
		}

	}

}
