package org.xenei.galway2020.enhancer.uri.handlers;

import java.io.IOException;
import java.net.URLConnection;
import java.util.List;

import javax.ws.rs.core.MediaType;

import org.apache.commons.lang3.StringUtils;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.Property;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.vocabulary.DC;
import org.apache.jena.vocabulary.DCTypes;
import org.apache.jena.vocabulary.DC_11;
import org.apache.jena.vocabulary.RDF;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.CharacterData;
import org.w3c.dom.Comment;
import org.w3c.dom.Document;
import org.w3c.dom.EntityReference;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.tidy.Tidy;

/**
 * Handle an HTML type.
 *
 */
public class HTMLHandler extends URIHandler {

	public static final Logger LOG = LoggerFactory.getLogger(HTMLHandler.class);
	
	/**
	 * Constructor.
	 * @param factory The URLHandlerFactory that we can use.
	 * @param mediaType The media type of the object.
	 * @param haveContent true if there is content.
	 * @param connection The connection to the URL resource.
	 * @param urlResource The urlResource.
	 * @param updates The model to write the updates to.
	 */
	public HTMLHandler(URLHandlerFactory factory, MediaType mediaType,
			URLConnection connection, Resource urlResource, Model updates) {
		super(factory, mediaType, true, connection, urlResource, updates);
	}

	/**
	 * Resource type is an interactive resource.
	 */
	@Override
	public void handle() {
		super.handle();
		getWritingResource().addProperty(RDF.type, DCTypes.InteractiveResource);
		List<String> lst = getConnection().getHeaderFields().get("Content-Language");
		if (lst != null)
		{
			for (String s : lst)
			{
				addLiteral( DC_11.language, s);
			}
		}

		Tidy tidy = new Tidy(); // obtain a new Tidy instance
		tidy.setXHTML(true); // set desired config options using tidy setters
								// (equivalent to command line options)
		tidy.setFixUri(true);
		tidy.setFixBackslash(true);
		tidy.setAsciiChars(true);
		tidy.setFixComments(true);
		tidy.setMakeClean(true);
		tidy.setMakeBare(true);
		tidy.setQuiet(true); 
		tidy.setShowErrors(0);
		tidy.setShowWarnings(false);
		try {
			Document document = tidy.parseDOM(getConnection().getInputStream(), null);
			
			findContentLanguage(document);
			
			addLiteral(DC_11.title, document.getElementsByTagName("title"));
			processMeta(document.getElementsByTagName("meta"));
		} catch (IOException e) {
			LOG.error(e.getMessage(), e);
		}

	}

	 private String getText(Node node)
	    {
	        StringBuffer reply = new StringBuffer();

	        NodeList children = node.getChildNodes();
	        for (int i = 0; i < children.getLength(); i++)
	        {
	            Node child = children.item(i);

	            if ((child instanceof CharacterData && !(child instanceof Comment)) || child instanceof EntityReference)
	            {
	                reply.append(child.getNodeValue());
	            }
	            else if (child.getNodeType() == Node.ELEMENT_NODE)
	            {
	                reply.append(getText(child));
	            }
	        }

	        return reply.toString();
	    }
	 
	private void addLiteral(Property property, NodeList nl) {
		for (int i = 0; i < nl.getLength(); i++) {
			addLiteral(property, getText(nl.item(i)));
		}
	}

	private void addLiteral(Property property, String value) {
		if (StringUtils.isNotBlank(value)) {
			getWritingResource().addLiteral(property, value.trim());
		}
	}

	private void processMetaName( NamedNodeMap nnm )
	{
		Node n2 = nnm.getNamedItem("name");
		if (n2 != null) {
			String type = n2.getNodeValue();
			n2 = nnm.getNamedItem("content");
			if (n2 != null) {
				String content = n2.getNodeValue();

				if (StringUtils.isNotBlank(type)
						&& StringUtils.isNotBlank(content)) {
					if ("description".equalsIgnoreCase(type)) {
						addLiteral(DC_11.description, content);
					} else if ("keywords".equalsIgnoreCase(type)) {
						String[] words = content.split(",");
						for (String word : words) {
							addLiteral(DC_11.subject, word);
						}
					} else if ("author".equalsIgnoreCase(type)) {
						addLiteral(DC_11.creator, content);
					}
				}
			}
		}
	}
	
	private void processMetaHttpEquiv( NamedNodeMap nnm )
	{
		Node n2 = nnm.getNamedItem("http-equiv");
		if (n2 != null) {
			String type = n2.getNodeValue();
			n2 = nnm.getNamedItem("content");
			if (n2 != null) {
				String content = n2.getNodeValue();

				if (StringUtils.isNotBlank(type)
						&& StringUtils.isNotBlank(content)) {
					if ("content-language".equalsIgnoreCase(type)) {
						addLiteral(DC_11.language, content);
					} 
				}
			}
		}
	}
	private void processMeta(NodeList nl) {
		for (int i = 0; i < nl.getLength(); i++) {
			Node n = nl.item(i);
			NamedNodeMap nnm = n.getAttributes();
			processMetaName( nnm );
			processMetaHttpEquiv( nnm );
		}
	}
	
	private void findContentLanguage( Document document )
	{
		NodeList nl = document.getElementsByTagName("html");
		for (int i=0;i<nl.getLength();i++)
		{
			Node n = nl.item(i);
			NamedNodeMap nnm = n.getAttributes();
			Node n2 = nnm.getNamedItem("lang");
			if (n2 != null)
			{
				addLiteral(DC_11.language, n2.getNodeValue());
			}
		}
	}

}