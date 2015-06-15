package org.xenei.galway2020.sink.fuseki;

import org.apache.jena.rdf.model.Model;

public class DeleteEntity extends AbstractEntity {

	
	public DeleteEntity(Model model) {
		super(model, "DELETE");
	}
	
	public DeleteEntity(Model model, String graphName) {
		super(model, graphName, "DELETE");
	}

}
