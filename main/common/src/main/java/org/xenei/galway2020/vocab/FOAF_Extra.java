package org.xenei.galway2020.vocab;

import org.apache.jena.rdf.model.Property;
import org.apache.jena.rdf.model.ResourceFactory;
import org.apache.jena.sparql.vocabulary.FOAF;

public class FOAF_Extra {
	public static Property foafAccount = ResourceFactory.createProperty(FOAF.NS, "account");
}
