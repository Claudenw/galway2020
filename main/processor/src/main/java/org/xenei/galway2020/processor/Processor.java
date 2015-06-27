package org.xenei.galway2020.processor;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;

import org.apache.commons.configuration.BaseConfiguration;
import org.apache.commons.configuration.CompositeConfiguration;
import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.commons.configuration.SystemConfiguration;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
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

	/**
	 * create an instance.
	 * 
	 * Will read multiple property files.  The The first one is the base, the second overrides options in base and so on.
	 * Finally, loads the System properties as the final file.  In this way System properties set with -D 
	 * on the java command will override or provide data.
	 * 
	 * @param args A list of property files.  My be empty.
	 * 
	 * @throws ClassNotFoundException
	 * @throws NoSuchMethodException
	 * @throws SecurityException
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 * @throws IllegalArgumentException
	 * @throws InvocationTargetException
	 * @throws ConfigurationException
	 */
	public static void main(String... args) throws ClassNotFoundException,
			NoSuchMethodException, SecurityException, InstantiationException,
			IllegalAccessException, IllegalArgumentException,
			InvocationTargetException, ConfigurationException {
		
		CompositeConfiguration cfg = null;
		
		for (String s : args)
		{
			if (cfg == null)
			{
				cfg = new CompositeConfiguration( new PropertiesConfiguration(s));
			}
			else
			{
				cfg.addConfiguration( new PropertiesConfiguration(s));
			}
		}
		
		if (cfg == null)
		{
			// use a base config so that updates go to it.
			cfg = new CompositeConfiguration( new BaseConfiguration() );
		}
		
		cfg.addConfiguration( new SystemConfiguration() );
		
		setupLogging( cfg.subset("log4j") );
		
		Processor p = new Processor(cfg);
		p.run();
	}
	
	/**
	 * Initialize the logging system with a LOG4J implementation.
	 * @param cfg The configuration
	 */
	public static void setupLogging( Configuration cfg )
	{
		Properties p = new Properties();
		Iterator<String> iter = cfg.getKeys();
		if (iter.hasNext())
		{
			while (iter.hasNext())
			{
				String key = iter.next();
				p.setProperty( String.format( "log4j.%s", key ), cfg.getString(key));
			}
			PropertyConfigurator.configure( p );
		}
		else {
			BasicConfigurator.configure();
		}
	}

	/**
	 * Constructor.
	 * 
	 * The following options must be in the configuration:
	 * <ul>
	 * <li>action=INSERT or DELETE - The action to take with the data.
	 * <li>graphName - The graph to write the data to in the sink (Optional).
	 * <li>source.class - the ModelSource implementation class</li>
	 * <li>source.config... - the configuration options for the ModelSource constructor</li>
	 * <li>enhancer.X.class - An Enhancer implementation class.</li>
	 * <li>enhancer.X.config - The configuration options for Enhancer X.
	 * <li>enhancer.Y.class - An Enhancer implementation class.</li>
	 * <li>enhancer.Y.config - The configuration options for Enhancer Y.
	 * <li>sink.class - the ModelSink implementation class</li>
	 * <li>sink.config... - the configuration options for the ModelSink constructor</li>
	 * <li>retryQueue.class - the ModelSink implementation class for the retry queue</li>
	 * <li>retryQueue.config... - the configuration options for the retry queue constructor</li>
	 * </ul>
	 * 
	 * Enhancers are optional.  RetryQueue is optional.
	 * 
	 * @param cfg
	 * @throws ClassNotFoundException
	 * @throws NoSuchMethodException
	 * @throws SecurityException
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 * @throws IllegalArgumentException
	 * @throws InvocationTargetException
	 */
	public Processor(Configuration cfg) throws ClassNotFoundException,
			NoSuchMethodException, SecurityException, InstantiationException,
			IllegalAccessException, IllegalArgumentException,
			InvocationTargetException {
		String actionStr = cfg.getString( "action");
		if (StringUtils.isBlank(actionStr))
		{
			throw new IllegalArgumentException( "action must not be null or a null string");
		}
		Action action = Action.valueOf( actionStr.toUpperCase());
		LOG.info( "Action: "+action);
		LOG.info( "To Graph: "+cfg.getString( "graphName", "(none defined)"));
		
		ModelSink sink = (ModelSink) createClass( checkClassCfg(cfg, "sink" ));
		LOG.info( "Sink: "+sink.getClass());
		ModelSource source = (ModelSource) createClass( checkClassCfg(cfg, "source"));
		LOG.info( "Source: "+source.getClass());
		List<Enhancer> enhancers = createEnhancers(cfg.subset("enhancer"));
		
		ModelSink retryQueue = null;
		if (cfg.containsKey( "retryQueue"))
		{
			retryQueue = (ModelSink) createClass( checkClassCfg(cfg, "retryQueue"));
			LOG.info( "Retry Queue: "+retryQueue.getClass());
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
	
	private Configuration checkClassCfg( Configuration cfg, String subCfgName )
	{
		Configuration subCfg = cfg.subset(subCfgName);
		
		Iterator<String> iter = subCfg.getKeys();
		while (iter.hasNext())
		{
			System.out.println( String.format( "%s : %s", subCfgName, iter.next()));
		}
		
		if (!subCfg.containsKey("class"))
		{
			throw new IllegalArgumentException( String.format( "Section %s does not define 'class' property", subCfgName ));
		}

		Configuration cfg2 = subCfg.subset("config");
		if (cfg2 == null)
		{
			throw new IllegalArgumentException( String.format( "Section %s does not define 'config' property", subCfgName ));
		}
		return subCfg;
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
			LOG.info( "Enhancer: "+lst.get(lst.size()-1).getClass());		
		}
		return lst;
	}

	@Override
	public void run() {
		workChain.run();
	}

}
