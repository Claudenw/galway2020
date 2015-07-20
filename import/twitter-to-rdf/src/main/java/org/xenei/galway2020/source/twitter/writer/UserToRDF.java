package org.xenei.galway2020.source.twitter.writer;

import org.apache.commons.lang3.StringUtils;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.sparql.vocabulary.FOAF;
import org.apache.jena.vocabulary.DC_11;
import org.apache.jena.vocabulary.RDF;
import org.apache.jena.vocabulary.RDFS;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xenei.galway2020.utils.DateToRDF;
import org.xenei.galway2020.utils.NSTools;
import org.xenei.galway2020.vocab.FOAF_Extra;
import org.xenei.galway2020.vocab.Galway2020;
import org.xenei.galway2020.source.twitter.TwitterInfo;

import twitter4j.MediaEntity.Size;
import twitter4j.URLEntity;
import twitter4j.User;
import twitter4j.UserMentionEntity;

public class UserToRDF {
	
	private final static Logger LOG = LoggerFactory
			.getLogger(UserToRDF.class);

	private final Model model;
	private final UrlEntityToRDF urlWriter;
	private final MediaEntityToRDF mediaWriter;
	private final StatusToRDF statusWriter;

	public Resource getId(long id) {
		if (id < 0) {
			throw new IllegalArgumentException("UserID may not be less that 0");
		}
		String url = String.format(
				NSTools.createURL( "user#%s"), id);
		Resource resource = model.createResource(url, Galway2020.User);
		resource.addProperty( RDF.type, FOAF.Person);
		return resource;
	}

	/**
	 * Constructor.
	 * Adds the (Twitter type FOAF.Agent) 
	 * @param model The model to write to.
	 */
	public UserToRDF(Model model) {
		this(model, new StatusToRDF(model));
	}

	/**
	 * Create the UserToRDF.
	 * @param model The model to add data to.
	 * @param statusWriter A status writer instance to prevent recursion.
	 */
	public UserToRDF(Model model, StatusToRDF statusWriter) {
		this.model = model;
		this.urlWriter = new UrlEntityToRDF(model);
		this.mediaWriter = new MediaEntityToRDF(model);
		this.statusWriter = statusWriter;
		
	}

	/**
	 * Write the userObject to the RDF store.
	 * @param userObj The object to write
	 * @return The user Resource in the graph.
	 */
	public Resource write(User userObj) {
		LOG.debug( "Writing user {}",userObj.getId());
		Resource user = getId(userObj.getId());
		if (StringUtils.isNotBlank(userObj.getBiggerProfileImageURL())) {
			user.addProperty(FOAF.img, mediaWriter.writeURL(
					userObj.getBiggerProfileImageURL(), Size.LARGE));
		}
		if (StringUtils.isNotBlank(userObj.getBiggerProfileImageURLHttps())) {
			user.addProperty(FOAF.img, mediaWriter.writeURL(
					userObj.getBiggerProfileImageURLHttps(), Size.LARGE));
		}
		
		user.addLiteral(DC_11.date, DateToRDF.asDateTime(userObj.getCreatedAt()));
		if (StringUtils.isNotBlank(userObj.getDescription())) {
			user.addLiteral(DC_11.description, userObj.getDescription());
		}
		for (URLEntity url : userObj.getDescriptionURLEntities()) {
			if (StringUtils.isNotBlank(url.getURL())) {
				user.addProperty(FOAF.interest, urlWriter.write(url));
			}
		}
		user.addLiteral(Galway2020.favoriteCount, userObj.getFavouritesCount());
		user.addLiteral(Galway2020.followersCount, userObj.getFollowersCount());
		user.addLiteral(Galway2020.friendsCount, userObj.getFriendsCount());
		if (StringUtils.isNotBlank(userObj.getLang())) {
			user.addLiteral(DC_11.language, userObj.getLang());
		}
		if (StringUtils.isNotBlank(userObj.getLocation())) {
			user.addLiteral(Galway2020.location, userObj.getLocation());
		}
		if (StringUtils.isNotBlank(userObj.getMiniProfileImageURL())) {
			user.addProperty(FOAF.img, mediaWriter.writeURL(
					userObj.getMiniProfileImageURL(), Size.SMALL));
		}
		if (StringUtils.isNotBlank(userObj.getMiniProfileImageURLHttps())) {
			user.addProperty(FOAF.img, mediaWriter.writeURL(
					userObj.getMiniProfileImageURLHttps(), Size.SMALL));
		}
		setName(user, userObj.getName());

		if (StringUtils.isNotBlank(userObj.getOriginalProfileImageURL())) {
			user.addProperty(FOAF.img, mediaWriter.writeURL(
					userObj.getOriginalProfileImageURL(), Size.MEDIUM));
		}
		if (StringUtils.isNotBlank(userObj.getOriginalProfileImageURLHttps())) {
			user.addProperty(FOAF.img, mediaWriter.writeURL(
					userObj.getOriginalProfileImageURLHttps(), Size.MEDIUM));
			;
		}
		addOnlineAccount(user, userObj.getScreenName());
		if (userObj.getStatus() != null) {
			user.addProperty(Galway2020.status,
					statusWriter.write(userObj.getStatus()));
		}
		user.addLiteral(Galway2020.statusCount, userObj.getStatusesCount());
		if (StringUtils.isNotBlank(userObj.getTimeZone())) {
			user.addLiteral(Galway2020.timeZone, userObj.getTimeZone());
		}
		if (StringUtils.isNotBlank(userObj.getURL())) {
			user.addProperty(FOAF.homepage,
					model.createResource(userObj.getURL()));
		}
		if (userObj.getURLEntity() != null
				&& StringUtils.isNotBlank(userObj.getURLEntity().getURL())) {
			user.addProperty(FOAF.homepage,
					urlWriter.write(userObj.getURLEntity()));
		}
		user.addLiteral(Galway2020.timeZoneOffset, userObj.getUtcOffset());
		LOG.debug( "Finsihed writing user {}",userObj.getId());
		return user;
	}

	/**
	 * Write data for a user mentioning another user in a post
	 * @param userObj The user doing the mention
	 * @param userMention The mention
	 * @return the Resource of the user that was mentioned.
	 */
	public Resource write(UserMentionEntity userMention) {
		Resource mentioned = getId(userMention.getId());
		addOnlineAccount(mentioned, userMention.getScreenName());
		setName(mentioned, userMention.getName());
		return mentioned;
	}

	private void setName(Resource user, String name) {
		if (StringUtils.isNotBlank(name)) {
			user.addLiteral(FOAF.name, name);
		}
	}

	/**
	 * Add the screen name for the user and make the user a twitter account.
	 * If screenName is null or blank no data is added. 
	 * @param user The Resource for the user
	 * @param screenName The screen name for the user.
	 */
	private void addOnlineAccount(Resource user, String screenName) {
		if (StringUtils.isNotBlank(screenName)) {
			
			Resource acct = model.createResource(String.format(
					"http://galway2020.xenei.net/twitter/twiterAccount#%s",
					screenName), FOAF.OnlineAccount);
			model.add(user, FOAF_Extra.foafAccount, acct);
			acct.addProperty(FOAF.accountServiceHomepage, TwitterInfo.TWITTER_URL);
			acct.addProperty( RDFS.label, screenName );
		}
	}

	public void setUserScreenName(long id, String screenName) {
		if (id > 0) {
			addOnlineAccount(getId(id), screenName);
		}
	}
}
