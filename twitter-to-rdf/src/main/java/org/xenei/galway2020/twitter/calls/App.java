package org.xenei.galway2020.twitter.calls;

public class App 
{
	
	
	public static void main(String args[]) throws Exception{
	    OAuthSetUp authorize = new OAuthSetUp();
	    SearchTweets search = new SearchTweets();
	    search.findTweets(authorize,search.getAllQuerys("twitter.cfg") );
	    System.exit(0);
	  }
	
	
	
}
