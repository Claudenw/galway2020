package org.xenei.galway2020.source.twitter;

import java.util.Iterator;

import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xenei.galway2020.source.twitter.writer.UserToRDF;

import twitter4j.Twitter;
import twitter4j.TwitterException;

/**
 * An iterator of user data models.
 * 
 * These are models based on the user information from Twitter.
 *
 */
public class UserModelIterator implements Iterator<Model> {
	private final static Logger LOG = LoggerFactory.getLogger(UserModelIterator.class);
	
	private final Iterator<String> users;
	private final Twitter twitter;

	/**
	 * Constructor
	 * @param twitter The Twitter interface
	 * @param users The iterator of user IDs
	 */
	public UserModelIterator(Twitter twitter, Iterator<String> users)
	{
		this.users=users;
		this.twitter = twitter;
	}
	
	@Override
	public boolean hasNext() {
		return users.hasNext();
	}

	@Override
	public Model next() {
		Model model = ModelFactory.createDefaultModel();
		UserToRDF userWriter = new UserToRDF(model);
		String user = users.next();
		LOG.info( "Processing user: {}", user );
		try {
			userWriter.write(twitter.showUser( user));
		} catch (TwitterException e) {
			LOG.error( "Error retrieving user: "+user);		
		}
		return model;
	}

}
