package org.xenei.galway2020.enhancer.uri.handlers;

import java.net.URLConnection;

import javax.ws.rs.core.MediaType;

import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.vocabulary.DCTypes;
import org.apache.jena.vocabulary.RDF;
import org.xenei.galway2020.enhancer.uri.URIEnhancer;

/**
 * Create an image handler.
 *
 */
public class ImageHandler extends URIHandler {

	/**
	 * Constructor.
	 * 
	 * @param factory
	 *            The URLHandlerFactory that we can use.
	 * @param mediaType
	 *            The media type of the object.
	 * @param haveContent
	 *            true if there is content.
	 * @param connection
	 *            The connection to the URL resource.
	 * @param urlResource
	 *            The urlResource.
	 * @param updates
	 *            The model to write the updates to.
	 * @param enhancer
	 *            The URIEnhancer instance
	 */
	public ImageHandler(URLHandlerFactory factory, MediaType mediaType,
			URLConnection connection, Resource urlResource, Model updates,
			URIEnhancer enhancer) {
		super(factory, mediaType, true, connection, urlResource, updates,
				enhancer);
	}

	/**
	 * Return an image type.
	 */
	@Override
	public void handle() {
		super.handle();
		getWritingResource().addProperty(RDF.type, DCTypes.Image);
	}

}
