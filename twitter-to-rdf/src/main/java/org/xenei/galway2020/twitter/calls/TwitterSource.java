package org.xenei.galway2020.twitter.calls;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;

import org.apache.commons.configuration.CompositeConfiguration;
import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.SystemConfiguration;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.util.iterator.ClosableIterator;
import org.apache.jena.util.iterator.WrappedIterator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xenei.galway2020.ModelSource;
import org.xenei.galway2020.twitter.writer.StatusToRDF;

import twitter4j.Query;
import twitter4j.QueryResult;
import twitter4j.Status;
import twitter4j.TwitterException;


public class TwitterSource implements ModelSource {
	
	private final Configuration cfg;
	private final OAuthSetUp authorize;
	private final static Logger LOG = LoggerFactory.getLogger(TwitterSource.class);
	
	public TwitterSource(Configuration cfg) throws TwitterException, IOException
	{
		// read properties from system configuration first.
		this.cfg = new CompositeConfiguration( new SystemConfiguration() );
		// then from configuration passed in.
		((CompositeConfiguration)this.cfg).addConfiguration( cfg );
//		consumerKey = System.getProperty("consumer.key");
//		consumerSecret = System.getProperty("consumer.secret");
//		accessTokenKey = System.getProperty("oauth.accessToken");
//		accessTokenSecret = System.getProperty("oauth.accessTokenSecret");	
		authorize = new OAuthSetUp( this.cfg );
	}
	
	private List<String> getTopics()
	{
		return Arrays.asList(cfg.getStringArray("hashtag"));	
	}
	

	public ClosableIterator<Model> modelIterator() {
		return WrappedIterator.create( getTopics().iterator() )
				.mapWith( new Function<String,Model>(){
					public Model apply(String topic) {
						Model model = ModelFactory.createDefaultModel();
						StatusToRDF statusWriter = new StatusToRDF(model);
						 Query query = new Query(topic);
						    QueryResult result;
							try {
								result = authorize.twitter.search(query);
								for (Status status : result.getTweets()) {
									statusWriter.write(status);
								}
							} catch (TwitterException e) {
								LOG.error( "Unable to execute query: "+query, e );
							}
						    
						return model;
					}});
	
	}
	

	

}
