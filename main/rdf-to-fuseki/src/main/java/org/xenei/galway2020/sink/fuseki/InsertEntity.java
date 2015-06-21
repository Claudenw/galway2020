package org.xenei.galway2020.sink.fuseki;


import org.apache.jena.rdf.model.Model;

public class InsertEntity extends AbstractEntity {

	public InsertEntity(Model model, String graphName) {
		super(model, graphName, "INSERT");
	}

}
