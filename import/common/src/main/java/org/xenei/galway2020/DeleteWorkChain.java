package org.xenei.galway2020;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

import org.apache.jena.rdf.model.Model;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DeleteWorkChain extends AbstractWorkChain {
	
	private static final Logger LOG = LoggerFactory.getLogger(DeleteWorkChain.class);
	
	public DeleteWorkChain( ModelSource source, ModelSink sink, ModelSink retryQueue  )
	{
		super( source, Collections.<Enhancer> emptyList(), sink, retryQueue );
	}
	
	public DeleteWorkChain( ModelSource source, List<Enhancer> enhancers, ModelSink sink, ModelSink retryQueue ) {
		super( source, enhancers, sink, retryQueue );
	}
	
	public DeleteWorkChain( String graphName, ModelSource source, ModelSink sink, ModelSink retryQueue  )
	{
		super( graphName, source, Collections.<Enhancer> emptyList(), sink, retryQueue );
	}
	
	public DeleteWorkChain( String graphName, ModelSource source, List<Enhancer> enhancers, ModelSink sink, ModelSink retryQueue ) {
		super( graphName, source, enhancers, sink, retryQueue );
	}

	@Override
	protected Logger getLog() {
		return LOG;
	}

	@Override
	protected boolean performSink(String graphName, ModelSink sink, Model model)
			throws IOException {
		return sink.delete( model, graphName );
	}
	
}
