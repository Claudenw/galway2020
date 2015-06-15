package org.xenei.galway2020.source.twitter.writer;

import org.apache.commons.lang3.StringUtils;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.sparql.vocabulary.FOAF;
import org.apache.jena.vocabulary.DC_11;
import twitter4j.URLEntity;

public class UrlEntityToRDF {

	private Model model;

	public UrlEntityToRDF(Model model) {
		this.model = model;
	}

	public Resource write(URLEntity url) {
		if (StringUtils.isBlank(url.getURL())) {
			throw new IllegalArgumentException("Entity url may not be null");
		}
		Resource r = model.createResource(url.getURL(), FOAF.Document);
		if (StringUtils.isNotBlank(url.getDisplayURL())) {
			r.addLiteral(DC_11.title, url.getDisplayURL());
		}
		if (StringUtils.isNotBlank(url.getExpandedURL())) {
			RDFWriter.Util.markSameAs(r,
					model.createResource(url.getExpandedURL()));
		}

		return r;
	}
}
