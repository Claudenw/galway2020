package org.xenei.galway2020;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

import org.apache.jena.rdf.model.Model;
import org.apache.jena.util.iterator.ExtendedIterator;
import org.slf4j.Logger;
import org.xenei.galway2020.sink.common.LoggingSink;

/**
 * A chain comprising a ModelSource, zero or more Enhancers, a ModelSink and an optional ModelSink requeue.
 * 
 */
public abstract class AbstractWorkChain implements Runnable {
	private final ModelSource source;
	private final ModelSink sink;
	private final ModelSink retryQueue;
	private final List<Enhancer> enhancers;
	private final String graphName;
	
	
	public AbstractWorkChain(  ModelSource source, ModelSink sink, ModelSink retryQueue )
	{
		this( null, source, Collections.<Enhancer> emptyList(), sink, retryQueue );
	}
	
	public AbstractWorkChain( String graphName, ModelSource source, ModelSink sink, ModelSink retryQueue )
	{
		this( graphName, source, Collections.<Enhancer> emptyList(), sink, retryQueue );
	}
	
	public AbstractWorkChain( ModelSource source, List<Enhancer> enhancers, ModelSink sink, ModelSink retryQueue ) {
		this( null, source, enhancers, sink, retryQueue );
	}
	
	public AbstractWorkChain( String graphName, ModelSource source, List<Enhancer> enhancers, ModelSink sink, ModelSink retryQueue ) {
		this.source=source;
		this.enhancers = enhancers;
		this.sink=sink;
		this.retryQueue = retryQueue==null?new LoggingSink():retryQueue;
		this.graphName = graphName;
	}
	
	abstract protected Logger getLog();
	
	abstract protected boolean performSink( String graphName, ModelSink sink, Model model ) throws IOException;

	
	public final void run() 
	{
		getLog().info( "Starting");
		int i=0;
		ExtendedIterator<Model> iter = source.modelIterator();
		try {
			for (Enhancer enh : enhancers )
			{
				iter = iter.mapWith( enh );
			}
			while (iter.hasNext())
			{
				Model model = iter.next();
				try {
					if (! performSink( graphName, sink , model))
					{
						performSink( graphName, retryQueue, model );
					}
				} catch (IOException e) {
					try {
						performSink( graphName, retryQueue, model );
					}
					catch (IOException e2)
					{
						getLog().error( "Unable to send model to sink or retryQueue -- DATA LOST");
					}
				}
				finally {
					model.close();
				}
				getLog().info( String.format( "finished model #%s", ++i));
			}
		}
		finally {
		getLog().info( "Shutting down");
			for (Enhancer enh : enhancers)
			{
				enh.shutdown();
			}
			getLog().info( "Finished" );
		}
	}
}
