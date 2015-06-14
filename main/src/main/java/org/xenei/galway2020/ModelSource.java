package org.xenei.galway2020;


import org.apache.jena.rdf.model.Model;
import org.apache.jena.util.iterator.ClosableIterator;

public interface ModelSource {

	ClosableIterator<Model> modelIterator();
}
