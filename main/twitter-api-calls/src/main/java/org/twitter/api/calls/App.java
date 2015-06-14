package org.twitter.api.calls;


public class App 
{
	
	
	public static void main(String args[]) throws Exception{
	    OAuthSetUp authorize = new OAuthSetUp();
	    SearchTweets search = new SearchTweets();
	    search.findTweets(authorize,"#IbackGalway :)","#Galway2020 :)" );
	    System.exit(0);
	  }
	
}
