package org.xenei.galway2020.enhancer.uri.handlers;

import javax.ws.rs.core.MediaType;

import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.Resource;


/**
 * Handler for a non resolvable type. Usually a literal.
 *
 */
public class NonURIHandler extends URLHandlerImpl {

	/**
	 * Constructor.
	 * @param mediaType The media type of the object.
	 * @param urlResource The urlResource.
	 * @param updates The model to write the updates to.
	 */
	public NonURIHandler(MediaType mediaType, 
			Resource urlResource, Model updates) {
		super( mediaType, urlResource, updates );

	}

}
