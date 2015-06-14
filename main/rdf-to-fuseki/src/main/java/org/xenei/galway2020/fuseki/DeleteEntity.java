package org.xenei.galway2020.fuseki;

import org.apache.jena.rdf.model.Model;

public class DeleteEntity extends AbstractEntity {

	
	public DeleteEntity(Model model) {
		super(model, "INSERT");
	}
	
	public DeleteEntity(Model model, String graphName) {
		super(model, graphName, "INSERT");
	}

}
