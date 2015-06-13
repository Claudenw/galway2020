package org.xenei.galway2020.twitter.writer;

import org.apache.jena.rdf.model.Property;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.rdf.model.ResourceFactory;
import org.apache.jena.sparql.vocabulary.FOAF;

public interface RDFWriter {
	public static String NS="http://galway2020.xenei.net/ns/twitter#";
	public static String GN_NS="http://www.geonames.org/ontology";
	public static String WGS84_NS="http://www.w3.org/2003/01/geo/wgs84_pos#";
	//public static String TZ_NS="http://www.w3.org/2006/timezone#";
	public static Property text = ResourceFactory.createProperty( NS, "text" );
	public static Property retweetCount = ResourceFactory.createProperty( NS, "retweenCount" );
	public static Property accessLevel = ResourceFactory.createProperty( NS, "accessLevel" );
	public static Property contributorId = ResourceFactory.createProperty( NS, "contributorId" );
	public static Property favoriteCount = ResourceFactory.createProperty( NS, "favoriteCount" );
	public static Property followersCount = ResourceFactory.createProperty( NS, "followersCount" );
	public static Property friendsCount = ResourceFactory.createProperty( NS, "friendsCount" );
	public static Property foafAccount  = ResourceFactory.createProperty( FOAF.NS, "account" );
	public static Property location  = ResourceFactory.createProperty( NS, "location" );
	public static Property replyTo  = ResourceFactory.createProperty( NS, "replyTo" );
	public static Property source  = ResourceFactory.createProperty( NS, "source" );
	public static Property scope  = ResourceFactory.createProperty( NS, "scope" );
	public static Property mentions  = ResourceFactory.createProperty( NS, "source" );
	public static Property timeZone = ResourceFactory.createProperty( NS, "timeZone" );
	public static Property timeZoneOffset =  ResourceFactory.createProperty( NS, "timeZoneOffset" );
	
	public static Property place  = ResourceFactory.createProperty( NS, "place" );
public static Property country = ResourceFactory.createProperty( NS, "country" );
	public static Property countryCode = ResourceFactory.createProperty( GN_NS, "countryCode" );
	
	
	public static Property gnName = ResourceFactory.createProperty( GN_NS, "name" );
	public static Property gnLat = ResourceFactory.createProperty( WGS84_NS, "lat" );
	public static Property gnLong = ResourceFactory.createProperty( WGS84_NS, "long" );
	public static Property placeType = ResourceFactory.createProperty( NS, "placeType" );
	public static Property streetAddress = ResourceFactory.createProperty( NS, "streetAddress" );
	
	public static Property media = ResourceFactory.createProperty( NS, "media" );
	public static Property height = ResourceFactory.createProperty( NS, "height" );
	public static Property width = ResourceFactory.createProperty( NS, "width" );
	public static Property resize = ResourceFactory.createProperty( NS, "resize" );
	public static Property size = ResourceFactory.createProperty( NS, "size" );
	
	public static Property status = ResourceFactory.createProperty( NS, "status" );
	public static Property statusCount = ResourceFactory.createProperty( NS, "statusCount" );
	public static Property displayURL =  ResourceFactory.createProperty( NS, "displayURL" );
	public static Property expandedURL =  ResourceFactory.createProperty( NS, "expandedURL" );
	public static Property tweet =  ResourceFactory.createProperty( NS, "tweet" );
	public static Property boundingBox =  ResourceFactory.createProperty( NS, "boundingBox" );
	public static Property boundingBoxType =  ResourceFactory.createProperty( NS, "boundingBoxType" );
	public static Property geometry =  ResourceFactory.createProperty( NS, "geometry" );
	public static Property geometryType =  ResourceFactory.createProperty( NS, "geometryType" );
	public static Property containedWithin =  ResourceFactory.createProperty( NS, "containedBy" );
	
	public static Resource Size  = ResourceFactory.createResource( NS+"Size" );
	public static Resource Hashtag  = ResourceFactory.createResource( NS+"Hashtag" );
	public static Resource gnFeature = ResourceFactory.createResource( GN_NS+"Feature" );
	public static Resource gnPoint = ResourceFactory.createResource( GN_NS+"Point" );
	public static Resource Twitter  = ResourceFactory.createResource( NS+"Twitter" );
	public static Resource TwitterURL = ResourceFactory.createResource( "https://twitter.com/");
	
}
