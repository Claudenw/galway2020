package org.xenei.galway2020.enhancer.uri.handlers;

import java.net.URLConnection;
import java.util.Date;

import javax.ws.rs.core.MediaType;

import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.rdf.model.ResourceFactory;
import org.apache.jena.vocabulary.OWL;
import org.xenei.galway2020.utils.DateToRDF;

/**
 * URI handler.
 *
 */
public class URIHandler extends URLHandlerImpl {
	// true if there is content.
	protected final boolean haveContent;
	protected final URLConnection connection;
	protected final URLHandlerFactory factory;

	/**
	 * Constructor.
	 * @param factory The URLHandlerFactory that we can use.
	 * @param mediaType The media type of the object.
	 * @param haveContent true if there is content.
	 * @param connection The connection to the URL resource.
	 * @param urlResource The urlResource.
	 * @param updates The model to write the updates to.
	 */
	public URIHandler(URLHandlerFactory factory, MediaType mediaType, boolean haveContent, URLConnection connection,
			Resource urlResource, Model updates) {
		super(mediaType, urlResource, updates);
		this.haveContent = haveContent;
		this.connection = connection;
		this.factory = factory;
	}

	/**
	 * Constructor.
	 * @param factory The URLHandlerFactory that we can use.
	 * @param mediaType The media type of the object.
	 * @param haveContent true if there is content.
	 * @param urlResource The urlResource.
	 * @param updates The model to write the updates to.
	 */
	public URIHandler(URLHandlerFactory factory, MediaType mediaType, boolean haveContent, 
			Resource urlResource, Model updates) {
		super(mediaType, urlResource, updates);
		this.haveContent = haveContent;
		this.connection = null;
		this.factory = factory;
	}
	
	/**
	 * Get the URI string for the urlResource object.
	 * @return the urlResource object URI string.
	 */
	public String getURIString() {
		return getWritingResource().getURI();
	}
	
	public URLConnection getConnection()
	{
		return connection;
	}

	/**
	 * Handle this in standard fashion with the additional operation to 
	 * check for rewriting the result.
	 * If the urlResource can be rewritten the rewritten URL is also handled.
	 */
	@Override
	public void handle() {
		// write the media type
		super.handle();
		
		if (haveContent)
		{
			if (connection.getDate() != 0)
			{
				DateToRDF.addDate(getWritingResource(), new Date( connection.getDate() ));
			}
			addLength( connection.getContentLengthLong() );
			if (! connection.getURL().toExternalForm().equals( getURIString() ))
			{
				
				Resource other = getWritingResource().getModel().createResource(connection.getURL().toExternalForm());
				getWritingResource().addProperty( OWL.sameAs, other);
				other.addProperty( OWL.sameAs, getWritingResource() );
			}
		} 
		
		
		// Attempt rewrite of URI for more data
		URLHandler rewritenHandler = factory.rewriteHandler(this);
		if (rewritenHandler != null) {
			rewritenHandler.handle();
		}

	}

}
