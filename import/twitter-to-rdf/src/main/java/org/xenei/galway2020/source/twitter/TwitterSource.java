package org.xenei.galway2020.source.twitter;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.commons.lang3.StringUtils;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.util.iterator.ExtendedIterator;
import org.apache.jena.util.iterator.LazyIterator;
import org.apache.jena.util.iterator.NiceIterator;
import org.apache.jena.util.iterator.WrappedIterator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xenei.galway2020.ModelSource;

import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;

/**
 * A Model Source that reads twitter posts.
 * 
 * Looks for posts by hashtag and user reference.
 * 
 * Returns models for those specific posts as well as any retweets, additional tags and 
 * users mentioned in the tweets.
 *
 */
public class TwitterSource implements ModelSource {
	public final static String LAST_ID = "lastTweet";
	
	final static Logger LOG = LoggerFactory
			.getLogger(TwitterSource.class);

	private final PropertiesConfiguration tracker;
	private final Twitter twitter;
	private final Configuration cfg;
	private final Long lastTweet;
	

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
	 * <li>tracking - a file that is used to track the last tweet read.
	 * </ul>
	 * 
	 * @param cfg
	 *            The configuration file
	 * @throws TwitterException
	 * @throws IOException
	 * @throws ConfigurationException 
	 */
	public TwitterSource(Configuration cfg) throws TwitterException,
			IOException, ConfigurationException {
		this.cfg = cfg;
		if (StringUtils.isBlank(cfg.getString("consumer.key")))
		{
			throw new IllegalArgumentException( "consumer.key missing from configuration file");
		}
		if (StringUtils.isBlank(cfg.getString("consumer.secret")))
		{
			throw new IllegalArgumentException( "consumer.secret missing from configuration file");
		}
		File f = new File( cfg.getString( "tracking" ) );
		if (! f.exists())
		{
			f.createNewFile();
		}
		tracker = new PropertiesConfiguration( f);
		tracker.setAutoSave(true);
		twitter = new TwitterFactory().getInstance();   
	    twitter.setOAuthConsumer(cfg.getString("consumer.key"), cfg.getString("consumer.secret"));
	    twitter.addRateLimitStatusListener(new Throttle() );
	    lastTweet = tracker.getLong( LAST_ID, 0);
	}

	/**
	 * A list of the hashtag entries with the hash included.
	 * @return A list of the hashtag entries with the hash included.
	 */
	private ExtendedIterator<String> getTopics() {
		return WrappedIterator.create(Arrays.asList(cfg.getStringArray("hashtag")).iterator()).mapWith(new TopicFunction());
	}


	/**
	 * Get the list of user strings from the configuration.
	 * @return Get the list of user strings from the configuration with the '@' included.
	 */
	private ExtendedIterator<String> getUsers() {
		return WrappedIterator.create(Arrays.asList(cfg.getStringArray("user")).iterator()).mapWith(new UserNameFunction());
	}

	

	@Override
	public ExtendedIterator<Model> modelIterator() {

		// get the iterator of topic and user strings
		ExtendedIterator<String> strIter =  getTopics().andThen(getUsers());
		final TweetModelIterator tmi = new TweetModelIterator( lastTweet, tracker, twitter, strIter);

		return  WrappedIterator.create( tmi );

	}

//	/**
//	 * A lazily constructed iterator that returns models.
//	 *
//	 */
//	public abstract class LazyModelIterator extends LazyIterator<Model> {
//
//		@Override
//		public Model removeNext() {
//			throw new UnsupportedOperationException();
//		}
//
//		@Override
//		public <X extends Model> ExtendedIterator<Model> andThen(
//				Iterator<X> other) {
//			return NiceIterator.andThen(this, other);
//		}
//
//		@Override
//		public List<Model> toList() {
//			return NiceIterator.asList(this);
//		}
//
//		@Override
//		public Set<Model> toSet() {
//			return NiceIterator.asSet(this);
//		}
//
//	}

}
