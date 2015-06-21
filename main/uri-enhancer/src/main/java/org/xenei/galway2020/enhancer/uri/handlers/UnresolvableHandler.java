package org.xenei.galway2020.enhancer.uri.handlers;

import javax.ws.rs.core.MediaType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xenei.galway2020.ns.RDFWriter;
import org.apache.commons.lang3.StringUtils;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.Resource;

/**
 * An Unresolvable handler handles the case where the URI resolution fails or the server 
 * does not respond.
 *
 */
public class UnresolvableHandler extends URIHandler {
	private static Logger LOG = LoggerFactory
			.getLogger(UnresolvableHandler.class);

	private final String reason;

	/**
	 * Constructor.
	 * @param factory The URLHandlerFactory that we can use.
	 * @param mediaType The media type of the object.
	 * @param reason The Exception that caused Unresolvable state.
	 * @param urlResource The urlResource.
	 * @param updates The model to write the updates to.
	 */
	public UnresolvableHandler(URLHandlerFactory factory,MediaType mediaType, Exception reason,
			Resource urlResource, Model updates) {
		this(factory,mediaType, reason.toString(), urlResource, updates);
	}
	
	/**
	 * Constructor.
	 * @param factory The URLHandlerFactory that we can use.
	 * @param mediaType The media type of the object.
	 * @param reason The reason URL was unresolvable.
	 * @param urlResource The urlResource.
	 * @param updates The model to write the updates to.
	 */
	public UnresolvableHandler(URLHandlerFactory factory,MediaType mediaType, String reason,
			Resource urlResource, Model updates) {
		super(factory,mediaType, false, urlResource, updates);
		this.reason = reason;
		LOG.warn("{} {}", urlResource, this.reason);
	}

	@Override
	public void handle() {
		super.handle();
		if (StringUtils.isNotBlank( reason ))
		{
			getWritingResource().addLiteral( RDFWriter.httpStatus, reason);
		}
	}
	
	
	
}
