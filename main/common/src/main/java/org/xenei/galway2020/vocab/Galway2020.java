/* CVS $Id: $ */
package org.xenei.galway2020.vocab; 
import org.apache.jena.rdf.model.*;
 
/**
 * Vocabulary definitions from file:/home/claude/git/galway2020/main/common/src/main/vocabs/galway2020.ttl 
 * @author Auto-generated by schemagen on 21 Jun 2015 21:00 
 */
public class Galway2020 {
    /** <p>The RDF model that holds the vocabulary terms</p> */
    private static Model m_model = ModelFactory.createDefaultModel();
    
    /** <p>The namespace of the vocabulary as a string</p> */
    public static final String NS = "http://galway2020.xenei.net/ns/twitter#";
    
    /** <p>The namespace of the vocabulary as a string</p>
     *  @see #NS */
    public static String getURI() {return NS;}
    
    /** <p>The namespace of the vocabulary as a resource</p> */
    public static final Resource NAMESPACE = m_model.createResource( NS );
    
    public static final Property accessLevel = m_model.createProperty( "http://galway2020.xenei.net/ns/twitter#accessLevel" );
    
    public static final Property boundingBox = m_model.createProperty( "http://galway2020.xenei.net/ns/twitter#boundingBox" );
    
    public static final Property boundingBoxType = m_model.createProperty( "http://galway2020.xenei.net/ns/twitter#boundingBoxType" );
    
    public static final Property containedBy = m_model.createProperty( "http://galway2020.xenei.net/ns/twitter#containedBy" );
    
    public static final Property contributorId = m_model.createProperty( "http://galway2020.xenei.net/ns/twitter#contributorId" );
    
    public static final Property country = m_model.createProperty( "http://galway2020.xenei.net/ns/twitter#country" );
    
    public static final Property countryCode = m_model.createProperty( "http://galway2020.xenei.net/ns/twitter#countryCode" );
    
    public static final Property favoriteCount = m_model.createProperty( "http://galway2020.xenei.net/ns/twitter#favoriteCount" );
    
    public static final Property followersCount = m_model.createProperty( "http://galway2020.xenei.net/ns/twitter#followersCount" );
    
    public static final Property friendsCount = m_model.createProperty( "http://galway2020.xenei.net/ns/twitter#friendsCount" );
    
    public static final Property geometry = m_model.createProperty( "http://galway2020.xenei.net/ns/twitter#geometry" );
    
    public static final Property geometryType = m_model.createProperty( "http://galway2020.xenei.net/ns/twitter#geometryType" );
    
    public static final Property height = m_model.createProperty( "http://galway2020.xenei.net/ns/twitter#height" );
    
    public static final Property httpStatus = m_model.createProperty( "http://galway2020.xenei.net/ns/twitter#httpStatus" );
    
    public static final Property location = m_model.createProperty( "http://galway2020.xenei.net/ns/twitter#location" );
    
    public static final Property media = m_model.createProperty( "http://galway2020.xenei.net/ns/twitter#media" );
    
    public static final Property mentions = m_model.createProperty( "http://galway2020.xenei.net/ns/twitter#mentions" );
    
    public static final Property place = m_model.createProperty( "http://galway2020.xenei.net/ns/twitter#place" );
    
    public static final Property placeType = m_model.createProperty( "http://galway2020.xenei.net/ns/twitter#placeType" );
    
    public static final Property replyTo = m_model.createProperty( "http://galway2020.xenei.net/ns/twitter#replyTo" );
    
    public static final Property resize = m_model.createProperty( "http://galway2020.xenei.net/ns/twitter#resize" );
    
    public static final Property retweet = m_model.createProperty( "http://galway2020.xenei.net/ns/twitter#retweet" );
    
    public static final Property retweetCount = m_model.createProperty( "http://galway2020.xenei.net/ns/twitter#retweetCount" );
    
    public static final Property scope = m_model.createProperty( "http://galway2020.xenei.net/ns/twitter#scope" );
    
    public static final Property size = m_model.createProperty( "http://galway2020.xenei.net/ns/twitter#size" );
    
    public static final Property source = m_model.createProperty( "http://galway2020.xenei.net/ns/twitter#source" );
    
    public static final Property status = m_model.createProperty( "http://galway2020.xenei.net/ns/twitter#status" );
    
    public static final Property statusCount = m_model.createProperty( "http://galway2020.xenei.net/ns/twitter#statusCount" );
    
    public static final Property streetAddress = m_model.createProperty( "http://galway2020.xenei.net/ns/twitter#streetAddress" );
    
    public static final Property text = m_model.createProperty( "http://galway2020.xenei.net/ns/twitter#text" );
    
    public static final Property timeZone = m_model.createProperty( "http://galway2020.xenei.net/ns/twitter#timeZone" );
    
    public static final Property timeZoneOffset = m_model.createProperty( "http://galway2020.xenei.net/ns/twitter#timeZoneOffset" );
    
    public static final Property tweet = m_model.createProperty( "http://galway2020.xenei.net/ns/twitter#tweet" );
    
    public static final Property width = m_model.createProperty( "http://galway2020.xenei.net/ns/twitter#width" );
    
    public static final Resource Hashtag = m_model.createResource( "http://galway2020.xenei.net/ns/twitter#Hashtag" );
    
    public static final Resource Size = m_model.createResource( "http://galway2020.xenei.net/ns/twitter#Size" );
    
    public static final Resource Tweet = m_model.createResource( "http://galway2020.xenei.net/ns/twitter#Tweet" );
    
    public static final Resource Twitter = m_model.createResource( "http://galway2020.xenei.net/ns/twitter#Twitter" );
    
    public static final Resource User = m_model.createResource( "http://galway2020.xenei.net/ns/twitter#User" );
    
}
