package org.twitter.api.calls;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.apache.commons.io.FileUtils;

import twitter4j.Query;
import twitter4j.QueryResult;
import twitter4j.Status;
import twitter4j.TwitterException;

public class SearchTweets {

	
	SearchTweets() {
		
	}
	
	public void findTweets(OAuthSetUp auth,List<String> queryList) throws TwitterException {
		
		for(String queryText : queryList) {
			 Query query = new Query(queryText);
			 
			    QueryResult result = auth.twitter.search(query);
			    
			    //Store the results in a graph
			    StoreTweets.storeTweets(result);
		}
		
		
	}
	protected List<String> getAllQuerys(String fileName) throws IOException {
	    ClassLoader classLoader = getClass().getClassLoader();
		List<String> lines = FileUtils.readLines(new File(classLoader.getResource(fileName).getFile()), "utf-8");
		return lines;
	}
}
