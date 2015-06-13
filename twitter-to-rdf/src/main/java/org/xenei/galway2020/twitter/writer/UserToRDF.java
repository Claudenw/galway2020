package org.xenei.galway2020.twitter.writer;

import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.rdf.model.ResourceFactory;
import org.apache.jena.sparql.vocabulary.FOAF;
import org.apache.jena.vocabulary.DC;
import org.apache.jena.vocabulary.RDF;

import twitter4j.HashtagEntity;
import twitter4j.URLEntity;
import twitter4j.User;
import twitter4j.UserMentionEntity;

public class UserToRDF {

	private final Model model;
	private final UrlEntityToRDF urlWriter;
	
	public Resource getId( long id )
	{
		if (id < 0)
		{
			throw new IllegalArgumentException( "UserID may not be less that 0");
		}
		String url = String.format("http://galway2020.xenei.net/twitter/user#%s", id );
		return model.createResource( url, FOAF.Person );
	}
	
	public UserToRDF(Model model)
	{
		this.model = model;
		this.urlWriter = new UrlEntityToRDF( model );
	}
	
	public Resource write( User userObj )
	{
		Resource user = getId( userObj.getId() );
		user.addLiteral( FOAF.img, ResourceFactory.createResource(userObj.getBiggerProfileImageURL()));
		user.addLiteral( FOAF.img, ResourceFactory.createResource(userObj.getBiggerProfileImageURLHttps()));
		user.addLiteral( DC.date, userObj.getCreatedAt());
		user.addLiteral( DC.description, userObj.getDescription());
		for (URLEntity url : userObj.getDescriptionURLEntities())
		{
			user.addLiteral( FOAF.interest,urlWriter.write( url ));
		}
		user.addLiteral( RDFWriter.favoriteCount, userObj.getFavouritesCount());
		user.addLiteral( RDFWriter.followersCount, userObj.getFollowersCount() );
		user.addLiteral( RDFWriter.friendsCount, userObj.getFriendsCount() );
		user.addLiteral( DC.language, userObj.getLang() );
		user.addLiteral( RDFWriter.location, userObj.getLocation());
		user.addLiteral( FOAF.img, ResourceFactory.createResource(userObj.getMiniProfileImageURL()));
		user.addLiteral( FOAF.img, ResourceFactory.createResource(userObj.getMiniProfileImageURLHttps()));
		setName( user, userObj.getName() );
		user.addLiteral( FOAF.img, ResourceFactory.createResource(userObj.getOriginalProfileImageURL()));
		user.addLiteral( FOAF.img, ResourceFactory.createResource(userObj.getOriginalProfileImageURLHttps()));;
		addOnlineAccount( user, userObj.getScreenName());
		if (userObj.getStatus() != null)
		{
			user.addLiteral( RDFWriter.status, userObj.getStatus());
		}
		user.addLiteral( RDFWriter.statusCount,userObj.getStatusesCount());
		user.addLiteral( RDFWriter.timeZone,userObj.getTimeZone());
		if (userObj.getURL() != null)
		{
			user.addLiteral( FOAF.homepage, model.createResource(userObj.getURL()));	
		}
		if (userObj.getURLEntity() != null)
		{
			user.addLiteral( FOAF.homepage, urlWriter.write(userObj.getURLEntity()));
		}
		user.addLiteral( RDFWriter.timeZoneOffset, userObj.getUtcOffset());
		return user;
	}
	
	public Resource write( User userObj, UserMentionEntity userMention )
	{
		Resource user = getId( userObj.getId() );
		Resource mentioned = getId( userMention.getId() );
		model.add( user, FOAF.knows, mentioned );
		addOnlineAccount( mentioned, userMention.getScreenName());
		setName( mentioned, userMention.getName());
		return mentioned;
	}
	
	private void setName( Resource user , String name )
	{
		user.addLiteral( FOAF.name, name);
	}
	private void addOnlineAccount(Resource user, String screenName)
	{
		model.add( RDFWriter.Twitter, RDF.type, FOAF.Agent);
		Resource acct = model.createResource(
				String.format("http://galway2020.xenei.net/twitter/twiterAccount#%s", screenName ), 
				FOAF.OnlineAccount);
		model.add( user, RDFWriter.foafAccount, acct  );
		acct.addProperty( FOAF.accountServiceHomepage, RDFWriter.TwitterURL);
	}
	
	public void setUserScreenName( long id, String screenName )
	{
		if (id >- 0)
		{
			addOnlineAccount( getId( id ), screenName );
		}
	}
}
