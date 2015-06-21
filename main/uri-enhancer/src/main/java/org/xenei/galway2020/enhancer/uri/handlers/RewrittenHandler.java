package org.xenei.galway2020.enhancer.uri.handlers;

/**
 * Rewrite a URI.  
 * This handles the case where a non-resolvable URI can be rewritten to a resolvable URI.
 * 
 * This class is primarily used to prevent recursion.
 *
 */
public class RewrittenHandler extends URIHandler {

	/**
	 * Constructor.
	 * @param handler the earlier URIHandler.
	 */
	public RewrittenHandler(URIHandler handler) {
		super(handler.factory, handler.mediaType, handler.haveContent, handler.connection,
				handler.readingResource, handler.model);
	}

}
