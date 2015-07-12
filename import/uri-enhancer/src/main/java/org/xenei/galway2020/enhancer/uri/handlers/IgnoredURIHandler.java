package org.xenei.galway2020.enhancer.uri.handlers;

import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.Resource;

public class IgnoredURIHandler extends URLHandlerImpl {

	public IgnoredURIHandler(Resource readingResource, Model model) {
		super(URLHandlerFactory.UNKNOWN, readingResource, model);
	}

	@Override
	public void handle() {
		// do nothing
	}

}
