package org.xenei.galway2020.processor;

import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.util.iterator.ClosableIterator;
import org.apache.log4j.Logger;
import org.xenei.galway2020.ModelSink;
import org.xenei.galway2020.ModelSource;


public class Processor implements Runnable {
	
	private static final Logger LOG = Logger.getLogger(Processor.class);
	private Configuration cfg;
	private ModelSink sink;
	private ModelSource source;
	
	public static void main(String... args) throws ClassNotFoundException, NoSuchMethodException, SecurityException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, ConfigurationException
	{
		Processor p = new Processor( new PropertiesConfiguration( args[0] ) );
		p.run();
	}
		
	public Processor(Configuration cfg) throws ClassNotFoundException, NoSuchMethodException, SecurityException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException
	{
		this.cfg = cfg;
		sink = (ModelSink) createClass( "sink");
		source = (ModelSource) createClass( "source" );
	}
	

	private Object createClass( String key ) throws ClassNotFoundException, NoSuchMethodException, SecurityException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException
	{
		Configuration pCfg = this.cfg.subset(key);
		String className = pCfg.getString("class");
		Configuration cCfg = pCfg.subset( "config");
		Class<?> cls = Class.forName(className);
		Constructor<?> ctor = cls.getConstructor( Configuration.class );
		return ctor.newInstance( cCfg );
	}


	public void run() {
		ClosableIterator<Model> modelIter = source.modelIterator();
		try {
			if ( sink.insert( modelIter.next() ) )
			{
				LOG.error( "Could not update data" );
			}
		} catch (IOException e) {
			LOG.error(	e.getMessage(), e );
		}
		finally {
			modelIter.close();
		}
	}
	

}
