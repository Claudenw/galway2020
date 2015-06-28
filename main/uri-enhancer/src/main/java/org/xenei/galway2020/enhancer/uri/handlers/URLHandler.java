package org.xenei.galway2020.enhancer.uri.handlers;

import javax.ws.rs.core.MediaType;

import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.Property;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.rdf.model.ResourceFactory;

/**
 * Handle a urlResource.
 * 
 * A urlResource is a triple anchored of an instance of a topic.
 *
 */
public interface URLHandler {

	public static final Property content_type = ResourceFactory.createProperty(
			"http://www.w3.org/2011/http#", "content-type");
	public static final Property content_length = ResourceFactory
			.createProperty("http://www.w3.org/2011/http#", "content-length");

	/**
	 * Get the media type of the predicate in the urlResource.
	 * 
	 * @return The media type of the object.
	 */
	public MediaType getMediaType();

	/**
	 * The resource we are reading/processing from the master model
	 */
	public Resource getReadingResource();

	/**
	 * The resource we are writing to the update model
	 */
	public Resource getWritingResource();

	/**
	 * handle adding the information about the object to the map.
	 * 
	 * Enhancers must be careful not to write data into the queried model as
	 * this may result in a ConcurrentModificationException for in memory
	 * graphs.
	 *
	 */
	public void handle();

	/**
	 * The model we are updating.
	 * 
	 * @return the model to update.
	 */
	public Model getModel();

}