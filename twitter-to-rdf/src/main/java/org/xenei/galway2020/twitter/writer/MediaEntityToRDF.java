package org.xenei.galway2020.twitter.writer;

import java.util.Map;
import java.util.Map.Entry;

import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.sparql.vocabulary.FOAF;
import org.apache.jena.vocabulary.DC;
import org.apache.jena.vocabulary.OWL;

import twitter4j.MediaEntity;
import twitter4j.MediaEntity.Size;

public class MediaEntityToRDF {

	private final Model model;
	private final UrlEntityToRDF urlWriter;
	private final static String[] sizes = { "thumb", "small", "medium", "large" };
	
	public Resource getId( long id )
	{	
			String url = String.format("http://galway2020.xenei.net/twitter/media/%s", id );
			return model.createResource( url );
	}
	
	public MediaEntityToRDF(Model model)
	{
		this.model = model;
		this.urlWriter = new UrlEntityToRDF( model );
	}
	
	private Resource writeSize( long id, Entry<Integer,Size> size )
	{
		String url = String.format("http://galway2020.xenei.net/twitter/media/%s/size/%s", id, sizes[size.getKey()] );
		Resource retval = model.createResource( url, RDFWriter.Size );
		Size s = size.getValue();
		retval.addLiteral( RDFWriter.width, s.getWidth());
		retval.addLiteral( RDFWriter.height, s.getHeight());
		retval.addLiteral( RDFWriter.resize, (s.getResize()==Size.FIT? "Fit": "Crop") );
		return retval;
	}
	
	public Resource write( MediaEntity mediaObj )
	{
		Resource media = getId( mediaObj.getId() );
		media.addProperty( OWL.sameAs, urlWriter.write( mediaObj ));
		media.addProperty( OWL.sameAs, model.createResource(mediaObj.getMediaURL()));
		media.addProperty( OWL.sameAs, model.createResource(mediaObj.getMediaURLHttps()));
		
		for (Entry<Integer,Size> size : mediaObj.getSizes().entrySet())
		{
			media.addProperty( RDFWriter.size, writeSize(mediaObj.getId(), size) );
		}
	    media.addProperty( DC.type, mediaObj.getType());
	    return media;
	}
}
