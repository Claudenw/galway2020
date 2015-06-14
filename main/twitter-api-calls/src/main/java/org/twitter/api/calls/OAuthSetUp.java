package org.twitter.api.calls;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.auth.AccessToken;
import twitter4j.auth.RequestToken;

public class OAuthSetUp {

	private static String consumerKey;
	private static String consumerSecret;
	private static String accessTokenKey;
	private static String accessTokenSecret;
	public Twitter twitter = TwitterFactory.getSingleton();
	public OAuthSetUp() throws TwitterException, IOException {

		consumerKey = System.getProperty("consumer.key");
		consumerSecret = System.getProperty("consumer.secret");
		accessTokenKey = System.getProperty("oauth.accessToken");
		accessTokenSecret = System.getProperty("oauth.accessTokenSecret");	
	    
	    twitter.setOAuthConsumer(consumerKey, consumerSecret);
	    
	   
	}
	
	private static void storeAccessToken(long useId, AccessToken accessToken){
	   System.out.println(accessToken.getToken());
	    System.out.println(accessToken.getTokenSecret());
	  }
	private static void storeAccessToken(int useId, AccessToken accessToken){
	    //store accessToken.getToken()
	    //store accessToken.getTokenSecret()
	  }
	private static void initialSetUP(Twitter twitter) throws TwitterException, IOException {
		 RequestToken requestToken = twitter.getOAuthRequestToken();
		    AccessToken accessToken = null;
		    
		    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		    while (null == accessToken) {
		      System.out.println("Open the following URL and grant access to your account:");
		      System.out.println(requestToken.getAuthorizationURL());
		      System.out.print("Enter the PIN(if aviailable) or just hit enter.[PIN]:");
		      String pin = br.readLine();
		      try{
		         if(pin.length() > 0){
		           accessToken = twitter.getOAuthAccessToken(requestToken, pin);
		         }else{
		           accessToken = twitter.getOAuthAccessToken();
		         }
		      } catch (TwitterException te) {
		        if(401 == te.getStatusCode()){
		          System.out.println("Unable to get the access token.");
		        }else{
		          te.printStackTrace();
		        }
		      }
		    }
		    //persist to the accessToken for future reference.
		   
				storeAccessToken(twitter.verifyCredentials().getId() , accessToken);
	
		    Status status = twitter.updateStatus("OAuth Set up Complete");
		    System.out.println("Successfully updated the status to [" + status.getText() + "].");
    
	}
}
