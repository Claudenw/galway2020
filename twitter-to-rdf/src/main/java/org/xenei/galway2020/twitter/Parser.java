package org.xenei.galway2020.twitter;

import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.xenei.galway2020.twitter.writer.StatusToRDF;

import twitter4j.Query;
import twitter4j.QueryResult;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.conf.ConfigurationBuilder;

public class Parser {

	public static void main(String[] args) throws TwitterException {
	    ConfigurationBuilder cb = new ConfigurationBuilder();
	    cb.setJSONStoreEnabled(true);

	    	Model model = ModelFactory.createDefaultModel();
	    	StatusToRDF statusWriter = new StatusToRDF( model );
	    Twitter twitter = new TwitterFactory(cb.build()).getInstance();
	    Query query = new Query("galway2020");
	    QueryResult result = twitter.search(query);
	    for (Status status : result.getTweets()) {
	    	statusWriter.write(status);
	    }
	    model.write( System.out );
	}
}
