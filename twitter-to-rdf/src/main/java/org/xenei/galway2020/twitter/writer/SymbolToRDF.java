package org.xenei.galway2020.twitter.writer;

import org.apache.jena.rdf.model.Model;

import twitter4j.GeoLocation;
import twitter4j.Place;
import twitter4j.Scopes;
import twitter4j.SymbolEntity;

public class SymbolToRDF {

	private Model model;
	
	public SymbolToRDF(Model model)
	{
		this.model = model;
	}
	
	public void write( SymbolEntity place )
	{
		// nothing to do
	}
}
