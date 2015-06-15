package org.xenei.galway2020.twitter.calls;

public class App 
{
	
	
	public static void main(String args[]) throws Exception{
		
	    OAuthSetUp authorize = new OAuthSetUp();
	    SearchTweets search = new SearchTweets();
	    search.findTweets(authorize,search.getConfigObject("twitter.properties") );
	    search.findUsers(authorize,search.getConfigObject("twitter.properties") );
	    System.exit(0);
	  }
	
	
	
}
