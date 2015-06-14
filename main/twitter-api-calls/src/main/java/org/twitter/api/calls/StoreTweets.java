package org.twitter.api.calls;

import org.xenei.galway2020.twitter;
import org.xenei.galway2020.twitter.Model;

import twitter4j.QueryResult;
import twitter4j.Status;
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
	
}
