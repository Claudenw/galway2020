package org.xenei.galway2020.processor;

import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.util.iterator.ClosableIterator;
import org.apache.log4j.Logger;
import org.xenei.galway2020.AbstractWorkChain;
import org.xenei.galway2020.DeleteWorkChain;
import org.xenei.galway2020.Enhancer;
import org.xenei.galway2020.InsertWorkChain;
import org.xenei.galway2020.ModelSink;
import org.xenei.galway2020.ModelSource;
import org.xenei.galway2020.utils.CfgTools;

public class Processor implements Runnable {

	public enum Action { INSERT, DELETE };
			
	private static final Logger LOG = Logger.getLogger(Processor.class);
	
	private final AbstractWorkChain workChain;

	public static void main(String... args) throws ClassNotFoundException,
			NoSuchMethodException, SecurityException, InstantiationException,
			IllegalAccessException, IllegalArgumentException,
			InvocationTargetException, ConfigurationException {
		Processor p = new Processor(new PropertiesConfiguration(args[0]));
		p.run();
	}

	public Processor(Configuration cfg) throws ClassNotFoundException,
			NoSuchMethodException, SecurityException, InstantiationException,
			IllegalAccessException, IllegalArgumentException,
			InvocationTargetException {
		
		ModelSink sink = (ModelSink) createClass( cfg.subset( "sink" ));
		ModelSource source = (ModelSource) createClass( cfg.subset("source"));
		List<Enhancer> enhancers = createEnhancers(cfg.subset("enhancer"));
		Action action = Action.valueOf( cfg.getString( "action", "action not defined" ).toUpperCase());
		ModelSink retryQueue = null;
		if (cfg.containsKey( "retryQueue"))
		{
			retryQueue = (ModelSink) createClass( cfg.subset( "retryQueue"));
		}
		if (action == Action.INSERT )
		{
			workChain = new InsertWorkChain( cfg.getString( "graphName"), source, enhancers, sink, retryQueue );
		}
		else
		{
			workChain = new DeleteWorkChain( cfg.getString( "graphName"), source, enhancers, sink, retryQueue );
		}
	}

	private Object createClass(Configuration cfg) throws ClassNotFoundException,
			NoSuchMethodException, SecurityException, InstantiationException,
			IllegalAccessException, IllegalArgumentException,
			InvocationTargetException {
		String className = cfg.getString("class");
		Configuration cCfg = cfg.subset("config");
		Class<?> cls = Class.forName(className);
		Constructor<?> ctor = cls.getConstructor(Configuration.class);
		return ctor.newInstance(cCfg);
	}
	
	private List<Enhancer> createEnhancers( Configuration cfg ) throws ClassNotFoundException, NoSuchMethodException, SecurityException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException
	{
		List<Enhancer> lst = new ArrayList<Enhancer>();
		for (String key : CfgTools.getPrefix(cfg))
		{
			lst.add( (Enhancer) createClass( cfg.subset(key)));
		}
		return lst;
	}

	public void run() {
		workChain.run();
	}

}
