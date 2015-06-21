package org.xenei.galway2020.enhancer.uri.handlers;

import java.net.URLConnection;

import javax.ws.rs.core.MediaType;

import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.Resource;

/**
 * Handle an RDF result. 
 * We handle the result by reading the RDF document and processing it.
 *
 */
public class RDFHandler extends URIHandler {

	/**
	 * Constructor.
	 * @param factory The URLHandlerFactory that we can use.
	 * @param mediaType The media type of the object.
	 * @param connection The connection to the URL resource.
	 * @param urlResource The urlResource.
	 * @param updates The model to write the updates to.
	 */
	public RDFHandler(URLHandlerFactory factory, MediaType mediaType, URLConnection connection,
			Resource urlResource, Model updates) 
	{
		super(factory, mediaType, true, urlResource, updates);
	}

}
