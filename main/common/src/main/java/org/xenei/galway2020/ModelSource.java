package org.xenei.galway2020;


import org.apache.jena.rdf.model.Model;
import org.apache.jena.util.iterator.ExtendedIterator;

public interface ModelSource {

	ExtendedIterator<Model> modelIterator();
}
