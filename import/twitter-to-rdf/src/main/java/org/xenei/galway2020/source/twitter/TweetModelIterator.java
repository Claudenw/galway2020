package org.xenei.galway2020.source.twitter;

import java.util.Collection;
import java.util.Iterator;
import java.util.function.Function;

import org.apache.commons.configuration.Configuration;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.util.iterator.ExtendedIterator;
import org.apache.jena.util.iterator.WrappedIterator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xenei.galway2020.source.twitter.writer.StatusToRDF;

import twitter4j.Query;
import twitter4j.QueryResult;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;

/**
 * A class that creates and merges iterators on models.
 *
 */
public class TweetModelIterator implements Iterator<Model> {
	private final static Logger LOG = LoggerFactory.getLogger(TweetModelIterator.class);
	
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
		this( tracker,  WrappedIterator.createIteratorIterator(WrappedIterator.create(queryIter).mapWith( new StatusMap(lastId,twitter))));
	}
	
	/**
	 * Constructor.
	 * @param lastId the last id from the last run
	 * @param twitter The twitter interface
	 * @param queryIter The iterator of strings that are to be queried
	 */
	public TweetModelIterator(Long lastId, Twitter twitter, Iterator<String> queryIter) {
		this( null, WrappedIterator.createIteratorIterator(WrappedIterator.create(queryIter).mapWith( new StatusMap(lastId,twitter))));
	}
	
	/**
	 * Constructor.
	 * @param statusCollection A collection of status objects to process.
	 */
	public TweetModelIterator(Collection<Status> statusCollection) {
		this( null,  WrappedIterator.create(statusCollection.iterator()));
	}
	
	/**
	 * Constructor
	 * @param tracker the configuration to track id. May be null.
	 * @param statusIterator The extended iterator of statuses.
	 */
	private TweetModelIterator(Configuration tracker, ExtendedIterator<Status> statusIterator) {
		this.tracker = tracker;
		this.statusIter = statusIterator;

	}
	
	/**
	 * Get the iterator of discovered user ids with '@' sign
	 * @return the ExtendedIterator of recorded user ids.
	 */


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
		
		// track this ID if we are tracking.
		if (tracker != null)
		{
			tracker.setProperty(TwitterSource.LAST_ID, Long.valueOf(status.getId()));
		}
		LOG.debug( "Returning model for {}", status.getId());
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
			if ( !wrapped.hasNext() )
			{
			
				if (queryResult.hasNext())
				{
					try {
						newWrapped(queryResult.nextQuery());
					} catch (TwitterException e) {
						LOG.error( "Can not get new queryResult: "+e.getMessage(), e );
						return false;
					}
				}
			}
			return wrapped.hasNext();
		}

		@Override
		public Status next() {
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

}
