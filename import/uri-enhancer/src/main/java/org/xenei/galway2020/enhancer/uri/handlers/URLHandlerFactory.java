package org.xenei.galway2020.enhancer.uri.handlers;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.net.URLConnection;

import javax.ws.rs.core.MediaType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xenei.galway2020.enhancer.uri.URIEnhancer;
import org.apache.commons.lang3.StringUtils;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.vocabulary.RDFS;

/**
 * Find a handler for a urlResource.
 *
 */
public class URLHandlerFactory {
	private static Logger LOG = LoggerFactory
			.getLogger(URLHandlerFactory.class);

	/**
	 * The configuration for the topic map.
	 */
	private final URIEnhancer uriEnhancer;

	/**
	 * A set of custom media type definitions that we will use.
	 */
	public static final MediaType APP_RDF_XML = new MediaType("application",
			"rdf+xml");
	public static final MediaType IMAGE = new MediaType("image", "*");
	public static final MediaType UNKNOWN = MediaType.valueOf("unknown/*");

	public URLHandlerFactory(URIEnhancer uriEnhancer) {
		this.uriEnhancer = uriEnhancer;
	}

	/**
	 * Get the configuration.
	 * 
	 * @return The current configuration.
	 */
	public URIEnhancer getConfig() {
		return uriEnhancer;
	}

	/**
	 * Get the urlResource handler for a URI.
	 * 
	 * @param uri
	 *            The uri to connect to.
	 * @param urlResource
	 *            The resource we are processing
	 * @param updates
	 *            The model to send updates to.
	 * @return A URLhandler or null if the URI should be ignored.
	 */
	private URLHandler getHandler(String uri, Resource urlResource,
			Model updates) {
		if (uriEnhancer.isIgnored(uri)) {
			return new IgnoredURIHandler(urlResource, updates);
		}
		// connection to see if we can connect.
		URLConnection objectConnection = null;
		// default to an unknown media type.
		MediaType mediaType = UNKNOWN;
		try {
			LOG.debug("Processing {} ", uri);

			objectConnection = new URL(uri).openConnection();
			objectConnection.setConnectTimeout(10000); // 10 second timeout
			objectConnection.setReadTimeout(10000); // 10 second timeout
			// connect to the resource.
			objectConnection.connect();
			// if there is a connection type returned set the media type.
			if (objectConnection.getContentType() != null) {
				mediaType = MediaType
						.valueOf(objectConnection.getContentType());
			}
			// since we connected we assume we have content.
			boolean haveContent = true;
			// if an HTTP connection then it is HTTP content.
			if (objectConnection instanceof HttpURLConnection) {
				HttpURLConnection hConnection = (HttpURLConnection) objectConnection;
				if (hConnection.getResponseCode() >= 200
						&& hConnection.getResponseCode() < 300) {
					// we have valid results
					if (hConnection.getResponseCode() == HttpURLConnection.HTTP_NO_CONTENT) {
						haveContent = false;
					}
				} else {
					if (hConnection.getResponseCode() == 301) {
						// moved permanently
						String newURL = hConnection.getHeaderField("Location");
						if (StringUtils.isNotBlank(newURL)) {
							new UnresolvableHandler(this, UNKNOWN,
									hConnection.getResponseMessage(),
									urlResource, updates, uriEnhancer).handle();
							Resource newResource = updates
									.createResource(newURL);
							// OwlFuncs.makeSameAs(newResource, urlResource );
							urlResource.addProperty(RDFS.seeAlso, newResource);

							return getHandler(newResource, updates);
						}
					}
					// not a 200 series response
					haveContent = false;

					LOG.warn("{} {} ({})", urlResource,
							hConnection.getResponseMessage(),
							hConnection.getResponseCode());
					return new UnresolvableHandler(this, UNKNOWN,
							hConnection.getResponseMessage(), urlResource,
							updates, uriEnhancer);
				}
			}

			// if we have content
			if (haveContent) {
				// check if there is an RDF result
				if (APP_RDF_XML.getSubtype().equals(mediaType.getSubtype())) {
					return new RDFHandler(this, mediaType, objectConnection,
							urlResource, updates, uriEnhancer);
				}
				// check if there is an IMAGE result
				if (IMAGE.isCompatible(mediaType)) {
					return new ImageHandler(this, mediaType, objectConnection,
							urlResource, updates, uriEnhancer);
				}
				// check if there is an HTML result.
				if (MediaType.TEXT_HTML_TYPE.isCompatible(mediaType)) {
					return new HTMLHandler(this, mediaType, objectConnection,
							urlResource, updates, uriEnhancer);
				}
			}
			// here we have an unknown type and possibly content
			return new URIHandler(this, mediaType, haveContent,
					objectConnection, urlResource, updates, uriEnhancer);
		} catch (SocketTimeoutException e) {
			// server did not respond within the time limit. This is
			// equivalent to
			// a 408, 503 or 504 response
			return new UnresolvableHandler(this, mediaType, e, urlResource,
					updates, uriEnhancer);
		} catch (MalformedURLException e) {
			// URL is invalid
			return new UnresolvableHandler(this, mediaType, e, urlResource,
					updates, uriEnhancer);
		} catch (IOException e) {
			// could not communicate with endpoint
			return new UnresolvableHandler(this, mediaType, e, urlResource,
					updates, uriEnhancer);
		}
	}

	/**
	 * Get the urlResource handler for the urlResource.
	 * 
	 * @param urlResource
	 *            The Resource we are processing
	 * @param updates
	 *            The model we will write updates to.
	 * 
	 * @return A URLHandler.
	 */
	public URLHandler getHandler(Resource urlResource, Model updates) {

		if (urlResource.isURIResource()) {
			return getHandler(urlResource.getURI(), urlResource, updates);
		}
		return new NonURIHandler(UNKNOWN, urlResource, updates);
	}

	/**
	 * rewrite the URIHandler results.
	 * 
	 * This handles the case where known unresolvable URIs can be converted into
	 * resolvable URIs by the configuration.
	 * 
	 * rewritten handlers are not rewritten.
	 * 
	 * @param handler
	 *            the URI handler to rewrite.
	 * @return a rewritten URI handler or null if the URI can not be rewritten.
	 */
	public URIHandler rewriteHandler(URIHandler handler) {
		if (handler instanceof RewrittenHandler) {
			return null;
		}
		String rewrittenUri = uriEnhancer.rewrite(handler.getURIString());
		if (rewrittenUri != null) {
			Resource rewrittenResource = handler.getModel().createResource(
					rewrittenUri);
			URLHandler rewrittenHandler = getHandler(rewrittenUri,
					rewrittenResource, handler.getModel());
			if (rewrittenHandler instanceof URIHandler) {
				// OwlFuncs.makeSameAs( handler.getWritingResource(),
				// rewrittenResource );
				handler.getWritingResource().addProperty(RDFS.seeAlso,
						rewrittenHandler.getWritingResource());
				return new RewrittenHandler(((URIHandler) rewrittenHandler));
			}
		}
		return null;
	}
}
