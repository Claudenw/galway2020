package org.xenei.galway2020.source.twitter;

import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.TreeSet;
import java.util.function.Function;

import org.apache.commons.lang3.StringUtils;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.rdf.model.ResIterator;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.sparql.vocabulary.FOAF;
import org.apache.jena.util.iterator.ExtendedIterator;
import org.apache.jena.util.iterator.IteratorIterator;
import org.apache.jena.util.iterator.Map1;
import org.apache.jena.util.iterator.WrappedIterator;
import org.apache.jena.vocabulary.RDF;
import org.apache.jena.vocabulary.RDFS;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xenei.galway2020.source.twitter.writer.StatusToRDF;
import org.xenei.galway2020.vocab.Galway2020;

import twitter4j.HashtagEntity;
import twitter4j.Query;
import twitter4j.QueryResult;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.User;
import twitter4j.UserMentionEntity;

public class TweetModelIterator implements Iterator<Model> {
	private final static Logger LOG = LoggerFactory.getLogger(TweetModelIterator.class);
	
	private Set<String> users;
	private Set<String> hashTags;
	private Set<Status> retweets;
	
	private Twitter twitter;
	private Iterator<Status> statusIter;
	
	public TweetModelIterator(Twitter twitter, Iterator<String> queryIter) {
		this.twitter = twitter;
		Iterator<Iterator<Status>> iiStatus = WrappedIterator.create(queryIter).mapWith( new StatusMap(twitter));
		statusIter = WrappedIterator.createIteratorIterator(iiStatus);
		users = new HashSet<String>();
		hashTags = new HashSet<String>();
		retweets = new TreeSet<Status>( new StatusComparator() );
	}
	
	public TweetModelIterator(Twitter twitter, Collection<Status> statusCollection) {
		this.twitter = twitter;
		statusIter = statusCollection.iterator();
		users = new HashSet<String>();
		hashTags = new HashSet<String>();
		retweets = new TreeSet<Status>( new StatusComparator() );
	}
	
	public Iterator<String> getUsers()
	{
		return WrappedIterator.create(users.iterator()).mapWith(new UserNameFunction());
	}
	
	public Iterator<String> getHashTags()
	{
		return WrappedIterator.create(hashTags.iterator()).mapWith(new TopicFunction());
	}
	
	public Collection<Status> getRetweets()
	{
		return retweets;
	}

	@Override
	public boolean hasNext() {
		return statusIter.hasNext();
	}

	@Override
	public Model next() {
		Model model = ModelFactory.createDefaultModel();
		StatusToRDF statusWriter = new StatusToRDF(model);
		Status status = statusIter.next();
		LOG.debug( "Processing tweet: {}", status.getId());
		statusWriter.write(status);
		// handle registry
		users.add( status.getUser().getName() );
	
		//users.add(  status.getContributors() );
		for (HashtagEntity hashtag : status.getHashtagEntities())
		{
			hashTags.add(hashtag.getText());
		}
		
		for (UserMentionEntity ume : status.getUserMentionEntities())
		{
			users.add(ume.getScreenName());
		}
		
		if (StringUtils.isNotBlank(status.getInReplyToScreenName()))
		{
			users.add( status.getInReplyToScreenName());
		}
		
		if (status.isRetweeted())
		{
			try {
				retweets.addAll( twitter.getRetweets( status.getId()) );
			} catch (TwitterException e) {
				LOG.warn( "Unable to retrieve retweets for "+status.getId());
			}
            
		}
		
		return model;
		
	}
	
	private static class StatusMap implements Function<String,Iterator<Status>> {

		private Twitter twitter;
		
		public StatusMap(Twitter twitter)
		{
			this.twitter = twitter;
		}
		
		@Override
		public Iterator<Status> apply(String queryStr) {
			try {
				return new StatusIterator( twitter, queryStr );
			} catch (TwitterException e) {
				throw new IllegalStateException( e );
			}
		}

	}
	
	private static class StatusIterator implements Iterator<Status> {
		private Twitter twitter;
		private QueryResult queryResult;
		private Iterator<Status> wrapped;
		
		StatusIterator(Twitter twitter, String queryStr) throws TwitterException
		{
			this.twitter = twitter;
			newWrapped( new Query(queryStr) );
		}
		
		@Override
		public boolean hasNext() {
			return wrapped.hasNext() || queryResult.hasNext();		
		}

		@Override
		public Status next() {
			if (!wrapped.hasNext())
			{
				try {
					newWrapped(queryResult.nextQuery());
				} catch (TwitterException e) {
					LOG.error( "Can not get new queryResult: "+e.getMessage(), e );
					throw new NoSuchElementException( );
				}
			}
			return wrapped.next();
		}
		
		private void newWrapped(Query query) throws TwitterException {	
			queryResult = twitter.search(query);
			wrapped = queryResult.getTweets().iterator();
		}
		
	}
	
	private static class StatusComparator implements Comparator<Status> {

		@Override
		public int compare(Status o1, Status o2) {
			return Long.compare( o1.getId(), o2.getId());
		}
		
	}

}
