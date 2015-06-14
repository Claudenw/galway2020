package org.xenei.galway2020.fuseki;


import org.apache.jena.rdf.model.Model;

public class InsertEntity extends AbstractEntity {

	public InsertEntity(Model model) {
		super(model, "INSERT");
	}
	
	public InsertEntity(Model model, String graphName) {
		super(model, graphName, "INSERT");
	}

}
