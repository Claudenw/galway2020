package org.xenei.galway2020.twitter.writer;

import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.sparql.vocabulary.FOAF;
import org.apache.jena.vocabulary.DC;

import twitter4j.HashtagEntity;
import twitter4j.MediaEntity;
import twitter4j.Status;
import twitter4j.SymbolEntity;
import twitter4j.URLEntity;
import twitter4j.UserMentionEntity;

public class StatusToRDF {

	private final Model model;
	private final MediaEntityToRDF mediaEntityWriter;
	private final GeoLocationToRDF geoLocationWriter;
	private final HashtagToRDF hashtagWriter;
	private final UserToRDF userWriter;
	private final PlaceToRDF placeWriter;
	private final SymbolToRDF symbolWriter;
	private final UrlEntityToRDF urlWriter;
	
	public Resource getId( long id )
	{
		String url = String.format("http://galway2020.xenei.net/twitter/status#%s", id );
		return model.createResource( url, FOAF.Document );
	}
	
	public StatusToRDF(Model model)
	{
		this.model = model;
		this.mediaEntityWriter = new MediaEntityToRDF( model );
		this.geoLocationWriter = new GeoLocationToRDF( model );
		this.hashtagWriter = new HashtagToRDF( model );
		this.userWriter = new UserToRDF( model );
		this.placeWriter = new PlaceToRDF( model );
		this.symbolWriter = new SymbolToRDF( model );
		this.urlWriter = new UrlEntityToRDF( model );
		}
	
	public Resource write( Status status ) {
		
		Resource main = getId( status.getId() );
		main.addLiteral( RDFWriter.accessLevel, status.getAccessLevel());
		if (status.getContributors()!=null)
		{
			for (long l : status.getContributors())
			{
				main.addLiteral( RDFWriter.contributorId, l );
			}
		}
		main.addLiteral( DC.date,  status.getCreatedAt() );
		//Not in the graph
		//main.addLiteral( RDFWriter.retweetId, status.getCurrentUserRetweetId());
		
		for ( MediaEntity media : status.getExtendedMediaEntities())
		{
			main.addLiteral( RDFWriter.media, mediaEntityWriter.write( media ));
		}
		
		main.addLiteral( RDFWriter.favoriteCount, status.getFavoriteCount() );
		if (status.getGeoLocation() != null)
		{
			geoLocationWriter.write( status.getGeoLocation() );
		}
		
		for (HashtagEntity hashtag : status.getHashtagEntities())
		{
			main.addProperty( DC.subject, hashtagWriter.write( hashtag ));
		}
		
		if (status.getInReplyToStatusId() >= 0)
		{
			main.addProperty( RDFWriter.replyTo, getId(status.getInReplyToStatusId()));
		}

		userWriter.setUserScreenName(status.getInReplyToUserId(), status.getInReplyToScreenName());
		
		main.addProperty( DC.language, status.getLang());
		
		for (MediaEntity media : status.getMediaEntities())
		{
			main.addProperty( RDFWriter.media, mediaEntityWriter.write(media));
		}
		
		if (status.getPlace() != null)
		{
			main.addProperty( RDFWriter.place, placeWriter.write( status.getPlace() ));
		}
		
		main.addLiteral( RDFWriter.retweetCount, status.getRetweetCount());
		
		if (status.getRetweetedStatus() != null)
		{
			write( status.getRetweetedStatus() );
		}
		
		if (status.getScopes() != null)
		{
			for (String placeId : status.getScopes().getPlaceIds())
			{
				main.addLiteral( RDFWriter.scope, placeWriter.getId( placeId ) );
			}
		}
		
		
		main.addLiteral( RDFWriter.source, status.getSource());
		
		for(SymbolEntity symbol : status.getSymbolEntities())
		{
			symbolWriter.write( symbol );
		}
		
		main.addLiteral( RDFWriter.text, status.getText());

		for(URLEntity url : status.getURLEntities())
		{
			main.addLiteral( FOAF.topic, urlWriter.write( url ));
		}
		
		if (status.getUser() != null)
		{
			userWriter.write( status.getUser() ).addProperty( RDFWriter.tweet, main );
		}
		
		for (UserMentionEntity userMention : status.getUserMentionEntities())
		{
			main.addLiteral( RDFWriter.mentions, userWriter.write( status.getUser(), userMention ));
		}
		return main;
	}
}
