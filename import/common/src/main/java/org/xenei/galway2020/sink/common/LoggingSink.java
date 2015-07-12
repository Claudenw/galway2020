package org.xenei.galway2020.sink.common;

import java.io.IOException;

import org.apache.commons.lang3.StringUtils;
import org.apache.jena.rdf.model.Model;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xenei.galway2020.ModelSink;

public class LoggingSink implements ModelSink {
	public static final Logger LOG = LoggerFactory.getLogger(LoggingSink.class);


	@Override
	public boolean insert(Model model, String graphName) throws IOException {
		if (StringUtils.isBlank( graphName ))
		{
			LOG.error( String.format( "Unable to insert model in default graph -- DATA LOST", graphName ));
		}
		else
		{
			LOG.error( String.format( "Unable to insert model in graph %s -- DATA LOST", graphName ));
		}
		return true;
	}

	@Override
	public boolean delete(Model model, String graphName) throws IOException {
		if(StringUtils.isBlank( graphName ))
		{
			LOG.error( "Unable to delete model from default graph -- DATA LOST");
		}
		else
		{
			LOG.error( String.format( "Unable to delete model from graph %s -- DATA LOST", graphName ));
		}
		return true;
	}

}
