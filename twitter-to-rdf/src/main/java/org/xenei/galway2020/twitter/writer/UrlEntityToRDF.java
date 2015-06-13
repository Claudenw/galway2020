package org.xenei.galway2020.twitter.writer;

import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.rdf.model.ResourceFactory;
import org.apache.jena.sparql.vocabulary.FOAF;
import org.apache.jena.vocabulary.DC;
import org.apache.jena.vocabulary.OWL;

import twitter4j.GeoLocation;
import twitter4j.Place;
import twitter4j.Scopes;
import twitter4j.SymbolEntity;
import twitter4j.URLEntity;

public class UrlEntityToRDF {

	private Model model;
	
	public UrlEntityToRDF(Model model)
	{
		this.model = model;
	}
	
	public Resource write( URLEntity url )
	{
		Resource r = model.createResource( url.getURL(), FOAF.Document );
		if (url.getDisplayURL() != null)
		{
			r.addLiteral( DC.title, url.getDisplayURL() );
		}
		if (url.getExpandedURL() != null)
		{
			r.addProperty( OWL.sameAs, model.createResource( url.getExpandedURL() ) );	
		}
		
		return r;
	}
}
