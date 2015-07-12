package org.xenei.galway2020;

import java.io.IOException;

import org.apache.jena.rdf.model.Model;

public interface ModelSink {
	
	/**
	 * Add the model the the sink in the named graph.
	 * 
	 * If the named graph is null or a blank string may insert into the default graph.
	 * 
	 * If the sink can not handle a null or blank graph name it must throw an IOException.
	 * 
	 * @param model The model to add to the graph.
	 * @param graphName The graph to add to, may be null.
	 * @return true if the data was written, false if there was a failure.
	 * @throws IOException on error.
	 */
	public boolean insert( Model model, String graphName) throws IOException;

	/**
	 * Delete the model from the sink in the named graph.
	 * 
	 * If the named graph is null or a blank string may insert into the default graph.
	 * 
	 * If the sink can not handle a null or blank graph name it must throw an IOException.
	 * 
	 * @param model The model to add to the graph.
	 * @param graphName The graph to add to. may be null.
	 * @return true if the data was deleted, false if there was a failure.
	 * @throws IOException on error.
	 */
	public boolean delete( Model model, String graphName) throws IOException;
	
}
