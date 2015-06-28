package org.xenei.galway2020.enhancer.uri.handlers;

import javax.ws.rs.core.MediaType;

import org.xenei.galway2020.enhancer.uri.URIEnhancer;
import org.xenei.galway2020.vocab.Galway2020;
import org.apache.commons.lang3.StringUtils;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.Resource;

/**
 * An Unresolvable handler handles the case where the URI resolution fails or
 * the server does not respond.
 *
 */
public class UnresolvableHandler extends URIHandler {
	private final String reason;

	/**
	 * Constructor.
	 * 
	 * @param factory
	 *            The URLHandlerFactory that we can use.
	 * @param mediaType
	 *            The media type of the object.
	 * @param reason
	 *            The Exception that caused Unresolvable state.
	 * @param urlResource
	 *            The urlResource.
	 * @param updates
	 *            The model to write the updates to.
	 * @param enhancer
	 *            The URIEnhancer instance.
	 */
	public UnresolvableHandler(URLHandlerFactory factory, MediaType mediaType,
			Exception reason, Resource urlResource, Model updates,
			URIEnhancer enhancer) {
		this(factory, mediaType, reason.toString(), urlResource, updates,
				enhancer);
	}

	/**
	 * Constructor.
	 * 
	 * @param factory
	 *            The URLHandlerFactory that we can use.
	 * @param mediaType
	 *            The media type of the object.
	 * @param reason
	 *            The reason URL was unresolvable.
	 * @param urlResource
	 *            The urlResource.
	 * @param updates
	 *            The model to write the updates to.
	 * @param enhancer
	 *            The URIEnhancer instance.
	 */
	public UnresolvableHandler(URLHandlerFactory factory, MediaType mediaType,
			String reason, Resource urlResource, Model updates,
			URIEnhancer enhancer) {
		super(factory, mediaType, false, urlResource, updates, enhancer);
		this.reason = reason;
	}

	@Override
	public void handle() {
		super.handle();
		if (StringUtils.isNotBlank(reason)) {
			getWritingResource().addLiteral(Galway2020.httpStatus, reason);
		}
	}

}
