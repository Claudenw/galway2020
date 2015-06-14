package org.twitter.api.calls;

import twitter4j.Query;
import twitter4j.QueryResult;
import twitter4j.Status;
import twitter4j.TwitterException;

public class SearchTweets {

	
	SearchTweets() {
		
	}
	
	public void findTweets(OAuthSetUp auth,String ...strings) throws TwitterException {
		
		for(String queryText : strings) {
			 Query query = new Query(queryText);
			 
			    QueryResult result = auth.twitter.search(query);
			    
			    //Store the results in a graph
			    StoreTweets.storeTweets(result);
		}
		
	}
	
}
