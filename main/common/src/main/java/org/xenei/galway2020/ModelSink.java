package org.xenei.galway2020;

import java.io.IOException;

import org.apache.jena.rdf.model.Model;

public interface ModelSink {
	
	/**
	 * Add the model to the sink's default graph.
	 * @param model The model to add to the default graph.
	 * @throws IOException 
	 */
	public boolean insert( Model model ) throws IOException;
	
	/**
	 * Add the model the the sink in the named graph.
	 * @param model The model to add to the graph.
	 * @param graphName The graph to add to.
	 * @return 
	 * @throws IOException 
	 */
	public boolean insert( Model model, String graphName) throws IOException;

	/**
	 * Delete the model from the sink's default graph.
	 * @param model The model to add to the default graph.
	 * @throws IOException 
	 */
	public boolean delete( Model model ) throws IOException;
	
	/**
	 * Delete the model from the sink in the named graph.
	 * @param model The model to add to the graph.
	 * @param graphName The graph to add to.
	 * @return 
	 * @throws IOException 
	 */
	public boolean delete( Model model, String graphName) throws IOException;
	
}
