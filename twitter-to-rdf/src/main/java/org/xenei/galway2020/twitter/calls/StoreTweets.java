package org.xenei.galway2020.twitter.calls;

import org.xenei.galway2020.twitter.Parser;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.xenei.galway2020.twitter.writer.StatusToRDF;

import twitter4j.Query;
import twitter4j.QueryResult;
import twitter4j.ResponseList;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.User;
import twitter4j.conf.ConfigurationBuilder;

public class StoreTweets {

	protected static void storeTweets(QueryResult result) {
		
		ConfigurationBuilder cb = new ConfigurationBuilder();
		cb.setJSONStoreEnabled(true);

		Model model = ModelFactory.createDefaultModel();
		StatusToRDF statusWriter = new StatusToRDF(model);
		for (Status status : result.getTweets()) {
			statusWriter.write(status);
		}
		model.write(System.out);
	}
	
protected static void storeTweets(ResponseList<User> result) {
		
		ConfigurationBuilder cb = new ConfigurationBuilder();
		cb.setJSONStoreEnabled(true);

		Model model = ModelFactory.createDefaultModel();
		StatusToRDF statusWriter = new StatusToRDF(model);
		for (User user : result) {
//			statusWriter.write(user);
		}
		model.write(System.out);
	}
}
