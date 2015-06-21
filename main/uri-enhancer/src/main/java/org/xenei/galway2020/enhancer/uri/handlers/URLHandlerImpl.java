package org.xenei.galway2020.enhancer.uri.handlers;

import java.util.Arrays;
import java.util.List;

import javax.ws.rs.core.MediaType;

import org.apache.jena.graph.Node;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.vocabulary.OWL;
import org.apache.jena.vocabulary.RDF;
import org.apache.jena.vocabulary.RDFS;
import org.apache.jena.vocabulary.DCTerms;;

/**
 * Am abstract urlResource handler.
 *
 */
abstract public class URLHandlerImpl implements URLHandler {
	// the model we are going to update.
	protected final Model model;
	// the urlResource that we are processing.
	protected final Resource readingResource;
	// the urlResource that we are writing.
	protected final Resource writingResource;
	// the media type of the object.
	protected MediaType mediaType;

	/**
	 * Constructor
	 * @param mediaType the media type of the object.
	 * @param topic The topic the urlResource is for.
	 * @param urlResource the urlResource.
	 */
	public URLHandlerImpl(MediaType mediaType, 
			Resource urlResource, Model updates) {
		this.mediaType = mediaType;
		this.readingResource = urlResource;
		this.model = updates;
		this.writingResource = model.createResource( urlResource.getURI());
	}

	@Override
	public final MediaType getMediaType() {
		return mediaType;
	}

	@Override
	public String toString() {
		return String.format("%s: %s", this.getClass(), this.readingResource);
	}

	@Override
	public final Resource getReadingResource() {
		return readingResource;
	}

	@Override
	public final Resource getWritingResource() {
		return writingResource;
	}
	
	@Override
	public final Model getModel() {
		return model;
	}
	
	/**
	 * Adds the urlResource subject as a topic example.
	 * 
	 */
	@Override
	public void handle() {
		getWritingResource().addLiteral( URLHandler.content_type, getMediaType().toString());
	}
	
	protected void addLength( long length )
	{
		if (length > -1)
		{
			getWritingResource().addLiteral( URLHandler.content_length, length );
		}
	}

}
