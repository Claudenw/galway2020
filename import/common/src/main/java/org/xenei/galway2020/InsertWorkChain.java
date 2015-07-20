package org.xenei.galway2020;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

import org.apache.jena.rdf.model.Model;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class InsertWorkChain extends AbstractWorkChain {
	
	private static final Logger LOG = LoggerFactory.getLogger(InsertWorkChain.class);
	
	public InsertWorkChain( ModelSource source, ModelSink sink, ModelSink retryQueue  )
	{
		super( source, Collections.<Enhancer> emptyList(), sink, retryQueue );
	}
	
	public InsertWorkChain( ModelSource source, List<Enhancer> enhancers, ModelSink sink, ModelSink retryQueue ) {
		super( source, enhancers, sink, retryQueue );
	}
	
	public InsertWorkChain( String graphName, ModelSource source, ModelSink sink, ModelSink retryQueue  )
	{
		super( graphName, source, Collections.<Enhancer> emptyList(), sink, retryQueue );
	}
	
	public InsertWorkChain( String graphName, ModelSource source, List<Enhancer> enhancers, ModelSink sink, ModelSink retryQueue ) {
		super( graphName, source, enhancers, sink, retryQueue );
	}

	@Override
	protected Logger getLog() {
		return LOG;
	}

	@Override
	protected boolean performSink(String graphName, ModelSink sink, Model model)
			throws IOException {
		LOG.debug( "Performing sink on: "+graphName);
		return sink.insert(model, graphName);
	}
}
