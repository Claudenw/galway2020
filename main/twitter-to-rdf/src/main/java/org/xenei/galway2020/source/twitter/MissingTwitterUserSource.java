package org.xenei.galway2020.source.twitter;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.lang3.StringUtils;
import org.apache.jena.arq.querybuilder.SelectBuilder;
import org.apache.jena.query.Query;
import org.apache.jena.query.QueryExecution;
import org.apache.jena.query.QueryExecutionFactory;
import org.apache.jena.query.QueryFactory;
import org.apache.jena.query.ResultSet;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.rdf.model.ResourceFactory;
import org.apache.jena.sparql.core.Var;
import org.apache.jena.sparql.lang.sparql_11.ParseException;
import org.apache.jena.sparql.vocabulary.FOAF;
import org.apache.jena.util.iterator.ExtendedIterator;
import org.apache.jena.util.iterator.WrappedIterator;
import org.apache.jena.vocabulary.RDF;
import org.apache.jena.vocabulary.RDFS;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xenei.galway2020.ModelSource;
import org.xenei.galway2020.vocab.FOAF_Extra;
import org.xenei.galway2020.vocab.Galway2020;

import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;

/**
 * A Model Source that reads twitter posts.
 * 
 * Looks for posts by hashtag and user reference.
 * 
 * Returns models for those specific posts as well as any retweets, additional tags and 
 * users mentioned in the tweets.
 *
 */
public class MissingTwitterUserSource implements ModelSource {
	public final static String LAST_ID = "lastTweet";
	
	public final static Resource TWITTER_URL = ResourceFactory
			.createResource("https://twitter.com/");

	
	private final static Logger LOG = LoggerFactory
			.getLogger(MissingTwitterUserSource.class);

	private final Twitter twitter;
	private final Set<String> userIds;

	/**
	 * Constructs a twitter source.
	 * 
	 * The configuration file should provide the following:
	 * <ul>
	 * <li>consumer.key - OAuth key</li>
	 * <li>consumer.secret - OAuth secret</li>
	 * <li>hashtag - a hashtag (without the #) to follow. There may be multiple
	 * hashtag entries.</li>
	 * <li>user - a user (without the at-sign) to follow. There may be multiple
	 * user entries.</li>
	 * <li>tracking - a file that is used to track the last tweet read.
	 * </ul>
	 * 
	 * @param cfg
	 *            The configuration file
	 * @throws TwitterException
	 * @throws IOException
	 * @throws ConfigurationException 
	 * @throws ParseException 
	 */
	public MissingTwitterUserSource(Configuration cfg) throws TwitterException,
			IOException, ConfigurationException, ParseException {
		if (StringUtils.isBlank(cfg.getString("consumer.key")))
		{
			throw new IllegalArgumentException( "consumer.key missing from configuration file");
		}
		if (StringUtils.isBlank(cfg.getString("consumer.secret")))
		{
			throw new IllegalArgumentException( "consumer.secret missing from configuration file");
		}
		
		String serviceURL = cfg.getString("url");
		if (serviceURL == null)
		{
			throw new IllegalArgumentException( "url missing from configuration file");
		}
		
		LOG.info( "Reading users from "+serviceURL );
		twitter = new TwitterFactory().getInstance();   
	    twitter.setOAuthConsumer(cfg.getString("consumer.key"), cfg.getString("consumer.secret"));
	    twitter.addRateLimitStatusListener(new Throttle() );
	    
	    Var user = Var.alloc( "user" );
	    Var userId = Var.alloc( "userId" );
	    Var account = Var.alloc( "account" );
	    SelectBuilder selectBuilder = new SelectBuilder()
	    .setDistinct(true)
	    .addVar( userId )
	    .addWhere( user, RDF.type, Galway2020.User )
	    .addWhere( user, FOAF_Extra.foafAccount, account )
	    .addWhere( account, FOAF.accountServiceHomepage, TWITTER_URL)
	    .addWhere( account, RDFS.label, userId )
	    .addFilter( "not exists {?user <"+FOAF.img+"> []}");
	   
	    userIds = new HashSet<String>();
	    String queryString = String.format( "SELECT ?userId WHERE { SERVICE <%s> { %s }}",
	    		serviceURL, selectBuilder );
	    Query query = QueryFactory.create(queryString) ;
	    try (QueryExecution qexec = QueryExecutionFactory.create(query, ModelFactory.createDefaultModel())) {
	    
	        ResultSet results = qexec.execSelect() ;
	        while  (results.hasNext())
	        {
	        	String id = results.next().get("userId").asLiteral().getString();
	        	if (StringUtils.isNotBlank( id))
	        	{
	        		userIds.add( id );
	        	}
	        }
	    }
	    LOG.info( userIds.size()+" users to process");
	}

	

	@Override
	public ExtendedIterator<Model> modelIterator() {
		return WrappedIterator.create(new UserModelIterator( twitter, userIds.iterator()));
	}

}
