package org.xenei.galway2020.source.twitter;

import java.util.Collection;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.TreeSet;
import java.util.function.Function;

import org.apache.commons.configuration.Configuration;
import org.apache.commons.lang3.StringUtils;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.util.iterator.ExtendedIterator;
import org.apache.jena.util.iterator.WrappedIterator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xenei.galway2020.source.twitter.writer.StatusToRDF;

import twitter4j.HashtagEntity;
import twitter4j.Query;
import twitter4j.QueryResult;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.UserMentionEntity;

/**
 * A class that creates and merges iterators on models.
 *
 */
public class TweetModelIterator implements Iterator<Model> {
	private final static Logger LOG = LoggerFactory.getLogger(TweetModelIterator.class);
	private final Set<String> users;
	private final Set<String> hashTags;
	private final Set<Status> retweets;
	
	private final Twitter twitter;
	private final Iterator<Status> statusIter;
	private final Configuration tracker;
	
	/**
	 * Constructor that tracks the tweet ids.
	 * @param lastId The last id from the last run.
	 * @param tracker The configuration that trackes the id
	 * @param twitter The Twitter interface
	 * @param queryIter The iterator of strings that are to be queried.
	 */
	public TweetModelIterator(Long lastId, Configuration tracker, Twitter twitter, Iterator<String> queryIter) {
		this( tracker, twitter,  WrappedIterator.createIteratorIterator(WrappedIterator.create(queryIter).mapWith( new StatusMap(lastId,twitter))));
	}
	
	/**
	 * Constructor.
	 * @param lastId the last id from the last run
	 * @param twitter The twitter interface
	 * @param queryIter The iterator of strings that are to be queried
	 */
	public TweetModelIterator(Long lastId, Twitter twitter, Iterator<String> queryIter) {
		this( null, twitter,  WrappedIterator.createIteratorIterator(WrappedIterator.create(queryIter).mapWith( new StatusMap(lastId,twitter))));
	}
	
	/**
	 * Constructor.
	 * @param twitter The twitter interface
	 * @param statusCollection A collection of status objects to process.
	 */
	public TweetModelIterator(Twitter twitter, Collection<Status> statusCollection) {
		this( null, twitter, WrappedIterator.create(statusCollection.iterator()));
	}
	
	/**
	 * Constructor
	 * @param tracker the configuration to track id. May be null.
	 * @param twitter the twitter interface
	 * @param statusIterator The extended iterator of statuses.
	 */
	private TweetModelIterator(Configuration tracker, Twitter twitter, ExtendedIterator<Status> statusIterator) {
		this.twitter = twitter;
		this.tracker = tracker;
		this.statusIter = statusIterator;
		
		users = new HashSet<String>();
		hashTags = new HashSet<String>();
		retweets = new TreeSet<Status>( new StatusComparator() );
	}
	
	/**
	 * Get the iterator of discovered user ids.
	 * @return the iterator of recorded user ids.
	 */
	public Iterator<String> getUsers()
	{
		return WrappedIterator.create(users.iterator()).mapWith(new UserNameFunction());
	}
	
	/**
	 * Get the iterator of discovered hashtags
	 * @return the iterator of recorded hashtags
	 */
	public Iterator<String> getHashTags()
	{
		return WrappedIterator.create(hashTags.iterator()).mapWith(new TopicFunction());
	}
	
	/**
	 * Get the collection of discovered retweets.
	 * @return the collection of retweets.
	 */
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
		
		// add user to discovered users
		if (status.getUser() != null && StringUtils.isNotBlank( status.getUser().getScreenName()))
		{
			users.add( status.getUser().getScreenName() );
		}
	
		// add hashtags to discovered hashtags.
		for (HashtagEntity hashtag : status.getHashtagEntities())
		{
			hashTags.add(hashtag.getText());
		}
		
		// add mentioned users to discovered users.
		for (UserMentionEntity ume : status.getUserMentionEntities())
		{
			users.add(ume.getScreenName());
		}
		
		// add reply-to to discovered users.
		if (StringUtils.isNotBlank(status.getInReplyToScreenName()))
		{
			users.add( status.getInReplyToScreenName());
		}
		
		// add retweets to discovered retweets
		if (status.isRetweeted())
		{
			try {
				retweets.addAll( twitter.getRetweets( status.getId()) );
			} catch (TwitterException e) {
				LOG.warn( "Unable to retrieve retweets for "+status.getId());
			}
            
		}
		
		// track this ID if we are tracking.
		if (tracker != null)
		{
			tracker.setProperty(TwitterSource.LAST_ID, Long.valueOf(status.getId()));
		}
		return model;
		
	}
	
	/**
	 * Class to convert an iterator of strings into status (tweet) objects.
	 *
	 */
	private static class StatusMap implements Function<String,Iterator<Status>> {

		private final Twitter twitter;
		private final long startId;
		
		/**
		 * Constructor
		 * @param startId All resulting tweet ids must be greater than this one.
		 * @param twitter The twitter interface.
		 */
		public StatusMap(long startId, Twitter twitter)
		{
			this.startId = startId;
			this.twitter = twitter;
		}
		
		@Override
		public Iterator<Status> apply(String queryStr) {
			try {
				return new StatusIterator( startId, twitter, queryStr );
			} catch (TwitterException e) {
				throw new IllegalStateException( e );
			}
		}

	}
	
	/**
	 * An iterator of status.
	 *
	 */
	private static class StatusIterator implements Iterator<Status> {
		private Twitter twitter;
		private QueryResult queryResult;
		private Iterator<Status> wrapped;
		
		/**
		 * Constructor.
		 * @param startId All resulting tweet ids must be greater than this one.
		 * @param twitter the Twitter interface.
		 * @param queryStr The String to make the query from.
		 * @throws TwitterException
		 */
		public StatusIterator(long startId, Twitter twitter, String queryStr) throws TwitterException
		{
			this.twitter = twitter;
			Query q = new Query(queryStr);
			q.setSinceId( startId );
			newWrapped( q );
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
		
		/**
		 * Method to execute a query and set up the wrapped iterator.
		 * @param query The query to execute
		 * @throws TwitterException on query error.
		 */
		private void newWrapped(Query query) throws TwitterException {	
			queryResult = twitter.search(query);
			wrapped = queryResult.getTweets().iterator();
		}
		
	}
	
	/**
	 * A comparator for statuses in the collection.
	 * 
	 * Compares based on id.
	 *
	 */
	private static class StatusComparator implements Comparator<Status> {

		@Override
		public int compare(Status o1, Status o2) {
			return Long.compare( o1.getId(), o2.getId());
		}
		
	}

}
