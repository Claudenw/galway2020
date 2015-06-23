package org.xenei.galway2020.source.twitter.calls;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.SystemConfiguration;

import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.auth.AccessToken;
import twitter4j.auth.RequestToken;

public class OAuthSetUp {

	private static String consumerKey;
	private static String consumerSecret;

	public Twitter twitter = TwitterFactory.getSingleton();
	
	/**
	 * Creates am OAuthSetup from the system properties:
	 * <ul>
	 * <li>consumer.key</li>
	 * <li>consumer.secret</li>
	 * </ul?
	 * @throws TwitterException
	 * @throws IOException
	 */
	public OAuthSetUp() throws TwitterException, IOException {
		this( new SystemConfiguration() );
	}
	
	/**
	 * Creates am OAuthSetup from the provided properties:
	 * <ul>
	 * <li>consumer.key</li>
	 * <li>consumer.secret</li>
	 * </ul>
	 * 
	 * @param cfg The configuration file that contains the above properties.
	 * @throws TwitterException
	 * @throws IOException
	 */
	public OAuthSetUp(Configuration cfg) throws TwitterException, IOException {
		consumerKey = cfg.getString("consumer.key");
		consumerSecret = cfg.getString("consumer.secret");    
	    twitter.setOAuthConsumer(consumerKey, consumerSecret);  
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
		    twitter.verifyCredentials();
		    //persist to the accessToken for future reference.
		   
				//storeAccessToken(twitter.verifyCredentials().getId() , accessToken);
	
		    //Status status = twitter.updateStatus("OAuth Set up Complete");
		    //System.out.println("Successfully updated the status to [" + status.getText() + "].");
    
	}
}
