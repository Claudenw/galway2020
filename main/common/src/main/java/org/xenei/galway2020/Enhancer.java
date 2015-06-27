package org.xenei.galway2020;

import java.util.function.Function;

import org.apache.jena.rdf.model.Model;

public interface Enhancer extends Function<Model,Model>{
	public void shutdown();
}
