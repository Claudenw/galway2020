package org.xenei.galway2020.twitter.writer;

import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.riot.thrift.wire.RDF_StreamRow;
import org.apache.jena.sparql.vocabulary.FOAF;
import org.apache.jena.vocabulary.DC;
import org.apache.jena.vocabulary.RDFS;

import twitter4j.HashtagEntity;

public class HashtagToRDF {

	private final Model model;
	
	public HashtagToRDF(Model model)
	{
		this.model = model;
	}
	
	public Resource getId( String hashTag )
	{
		String url = String.format("http://galway2020.xenei.net/twitter/hashtag#%s", hashTag );
		Resource r = model.createResource( url, RDFWriter.Hashtag );
		r.addLiteral( DC.subject, hashTag );
		r.addLiteral( RDFS.label, hashTag );
		return r;
	}
	
	public Resource write( HashtagEntity hashTag )
	{
		return getId( hashTag.getText());
	}
}
