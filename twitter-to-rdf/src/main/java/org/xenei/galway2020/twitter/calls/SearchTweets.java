package org.xenei.galway2020.twitter.calls;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.commons.configuration.*;

import twitter4j.Query;
import twitter4j.QueryResult;
import twitter4j.ResponseList;
import twitter4j.Status;
import twitter4j.TwitterException;
import twitter4j.User;

public class SearchTweets {

	SearchTweets() {

	}

	public void findTweets(OAuthSetUp auth, List<String> queryList)
			throws TwitterException {

		for (String queryText : queryList) {
			Query query = new Query(queryText);

			QueryResult result = auth.twitter.search(query);

			// Store the results in a graph
			StoreTweets.storeTweets(result);
		}

	}

	protected List<String> getAllQuerys(String fileName) throws IOException {
		ClassLoader classLoader = getClass().getClassLoader();
		List<String> lines = FileUtils.readLines(new File(classLoader
				.getResource(fileName).getFile()), "utf-8");
		return lines;
	}

	protected Configuration getConfigObject(String fileName) {
		Configuration config = null;
		ClassLoader classLoader = getClass().getClassLoader();
		try {
			config = new PropertiesConfiguration(classLoader.getResource(
					fileName).getFile());
		} catch (ConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return config;

	}

	public void findTweets(OAuthSetUp auth, Configuration cfg)
			throws TwitterException {

		queryAndStore(auth, cfg.getStringArray("hashtag"));

	}

	private void queryAndStore(OAuthSetUp auth, String[] twitterQueries)
			throws TwitterException {
		for (String queryText : twitterQueries) {
			Query query = new Query(queryText);

			QueryResult result = auth.twitter.search(query);

			// Store the results in a graph
			StoreTweets.storeTweets(result);
		}
	}

	public void findUsers(OAuthSetUp auth, Configuration cfg) throws TwitterException {

		String[] twitterQueries = cfg.getStringArray("user");
		
		for (String queryText : twitterQueries) {
			Query query = new Query(queryText);

			ResponseList<User> result = auth.twitter.searchUsers(queryText, 0);

			// Store the results in a graph
			StoreTweets.storeTweets(result);
			
		}
		
	}

}
