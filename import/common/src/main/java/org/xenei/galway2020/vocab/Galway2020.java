/* CVS $Id: $ */
package org.xenei.galway2020.vocab; 
import org.apache.jena.rdf.model.*;
 
/**
 * Vocabulary definitions from file:/home/claude/git/galway2020/import/common/src/main/resources/vocabs/galway2020.ttl 
 * @author Auto-generated by schemagen on 20 Jul 2015 21:40 
 */
public class Galway2020 {
    /** <p>The RDF model that holds the vocabulary terms</p> */
    private static Model m_model = ModelFactory.createDefaultModel();
    
    /** <p>The namespace of the vocabulary as a string</p> */
    public static final String NS = "http://data.galway2020.ie/ns/twitter#";
    
    /** <p>The namespace of the vocabulary as a string</p>
     *  @see #NS */
    public static String getURI() {return NS;}
    
    /** <p>The namespace of the vocabulary as a resource</p> */
    public static final Resource NAMESPACE = m_model.createResource( NS );
    
    public static final Property accessLevel = m_model.createProperty( "http://data.galway2020.ie/ns/twitter#accessLevel" );
    
    /** <p>a bounding box for location.</p> */
    public static final Property boundingBox = m_model.createProperty( "http://data.galway2020.ie/ns/twitter#boundingBox" );
    
    /** <p>Identifies a bounding box that contains this bounding box</p> */
    public static final Property containedBy = m_model.createProperty( "http://data.galway2020.ie/ns/twitter#containedBy" );
    
    public static final Property contributorId = m_model.createProperty( "http://data.galway2020.ie/ns/twitter#contributorId" );
    
    /** <p>The country where the tweet was created.</p> */
    public static final Property country = m_model.createProperty( "http://data.galway2020.ie/ns/twitter#country" );
    
    /** <p>The country code for the country where the tweet was created.</p> */
    public static final Property countryCode = m_model.createProperty( "http://data.galway2020.ie/ns/twitter#countryCode" );
    
    /** <p>The number of times this tweet has been favorited</p> */
    public static final Property favoriteCount = m_model.createProperty( "http://data.galway2020.ie/ns/twitter#favoriteCount" );
    
    /** <p>The number of followers of this user</p> */
    public static final Property followersCount = m_model.createProperty( "http://data.galway2020.ie/ns/twitter#followersCount" );
    
    /** <p>The number of friends of this user</p> */
    public static final Property friendsCount = m_model.createProperty( "http://data.galway2020.ie/ns/twitter#friendsCount" );
    
    /** <p>A point where the tweet came from</p> */
    public static final Property geoLocation = m_model.createProperty( "http://data.galway2020.ie/ns/twitter#geoLocation" );
    
    /** <p>the bounding box of a geometry</p> */
    public static final Property geometry = m_model.createProperty( "http://data.galway2020.ie/ns/twitter#geometry" );
    
    /** <p>The height of an image</p> */
    public static final Property height = m_model.createProperty( "http://data.galway2020.ie/ns/twitter#height" );
    
    /** <p>The result for an attempted GET on a URL.</p> */
    public static final Property httpStatus = m_model.createProperty( "http://data.galway2020.ie/ns/twitter#httpStatus" );
    
    /** <p>The number of friends the user</p> */
    public static final Property location = m_model.createProperty( "http://data.galway2020.ie/ns/twitter#location" );
    
    /** <p>Identifies a media object associated with the tweet.</p> */
    public static final Property media = m_model.createProperty( "http://data.galway2020.ie/ns/twitter#media" );
    
    /** <p>The users that were mentioned in the tweet</p> */
    public static final Property mentions = m_model.createProperty( "http://data.galway2020.ie/ns/twitter#mentions" );
    
    /** <p>A textual representation of a place</p> */
    public static final Property place = m_model.createProperty( "http://data.galway2020.ie/ns/twitter#place" );
    
    /** <p>The type of place identified.</p> */
    public static final Property placeType = m_model.createProperty( "http://data.galway2020.ie/ns/twitter#placeType" );
    
    /** <p>The tweet this is a reply to</p> */
    public static final Property replyTo = m_model.createProperty( "http://data.galway2020.ie/ns/twitter#replyTo" );
    
    /** <p>The suggested method for resizing the image.</p> */
    public static final Property resize = m_model.createProperty( "http://data.galway2020.ie/ns/twitter#resize" );
    
    /** <p>identifies a retweet of a tweet</p> */
    public static final Property retweet = m_model.createProperty( "http://data.galway2020.ie/ns/twitter#retweet" );
    
    /** <p>The number of retweets of a tweet</p> */
    public static final Property retweetCount = m_model.createProperty( "http://data.galway2020.ie/ns/twitter#retweetCount" );
    
    /** <p>The geo scope that this tweet was targeted to</p> */
    public static final Property scope = m_model.createProperty( "http://data.galway2020.ie/ns/twitter#scope" );
    
    /** <p>Identifies the size of an image.</p> */
    public static final Property size = m_model.createProperty( "http://data.galway2020.ie/ns/twitter#size" );
    
    /** <p>The device type that created this tweet</p> */
    public static final Property source = m_model.createProperty( "http://data.galway2020.ie/ns/twitter#source" );
    
    /** <p>Identifies the status of this user</p> */
    public static final Property status = m_model.createProperty( "http://data.galway2020.ie/ns/twitter#status" );
    
    /** <p>The number statuses for the user</p> */
    public static final Property statusCount = m_model.createProperty( "http://data.galway2020.ie/ns/twitter#statusCount" );
    
    /** <p>The street address of the location.</p> */
    public static final Property streetAddress = m_model.createProperty( "http://data.galway2020.ie/ns/twitter#streetAddress" );
    
    /** <p>The text of a tweet</p> */
    public static final Property text = m_model.createProperty( "http://data.galway2020.ie/ns/twitter#text" );
    
    /** <p>The timezone the tweet was generated in</p> */
    public static final Property timeZone = m_model.createProperty( "http://data.galway2020.ie/ns/twitter#timeZone" );
    
    /** <p>The offset of the timezone from GMT</p> */
    public static final Property timeZoneOffset = m_model.createProperty( "http://data.galway2020.ie/ns/twitter#timeZoneOffset" );
    
    /** <p>The width of an image</p> */
    public static final Property width = m_model.createProperty( "http://data.galway2020.ie/ns/twitter#width" );
    
    /** <p>A hashtag from a tweet.</p> */
    public static final Resource Hashtag = m_model.createResource( "http://data.galway2020.ie/ns/twitter#Hashtag" );
    
    /** <p>Identifies the size of something.</p> */
    public static final Resource Size = m_model.createResource( "http://data.galway2020.ie/ns/twitter#Size" );
    
    /** <p>Identifies the tweet system.</p> */
    public static final Resource Tweet = m_model.createResource( "http://data.galway2020.ie/ns/twitter#Tweet" );
    
    /** <p>Identifies the twitter system.</p> */
    public static final Resource Twitter = m_model.createResource( "http://data.galway2020.ie/ns/twitter#Twitter" );
    
    /** <p>Identifies user on the twitter system.</p> */
    public static final Resource User = m_model.createResource( "http://data.galway2020.ie/ns/twitter#User" );
    
}
