package org.xenei.galway2020.source.twitter.writer;

import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.vocabulary.DC_11;
import org.apache.jena.vocabulary.RDFS;

import twitter4j.HashtagEntity;

public class HashtagToRDF {

	private final Model model;

	public HashtagToRDF(Model model) {
		this.model = model;
	}

	public Resource getId(String hashTag) {
		String url = String.format(
				"http://galway2020.xenei.net/twitter/hashtag#%s", hashTag);
		Resource r = model.createResource(url, RDFWriter.Hashtag);
		r.addLiteral(DC_11.subject, hashTag);
		r.addLiteral(RDFS.label, hashTag);
		return r;
	}

	public Resource write(HashtagEntity hashTag) {
		return getId(hashTag.getText());
	}
}
