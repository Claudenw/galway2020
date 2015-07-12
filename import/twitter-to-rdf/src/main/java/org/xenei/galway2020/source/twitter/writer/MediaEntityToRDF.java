package org.xenei.galway2020.source.twitter.writer;

import org.apache.commons.lang3.StringUtils;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.sparql.vocabulary.FOAF;
import org.apache.jena.vocabulary.DC_11;
import org.apache.jena.vocabulary.RDF;
import org.xenei.galway2020.utils.NSTools;
import org.xenei.galway2020.utils.OwlFuncs;
import org.xenei.galway2020.vocab.Galway2020;

import twitter4j.MediaEntity;
import twitter4j.MediaEntity.Size;

public class MediaEntityToRDF {

	private final Model model;
	private final UrlEntityToRDF urlWriter;
	private final static String[] sizes = { "thumb", "small", "medium", "large" };

	public Resource getId(long id) {
		String url = String.format( NSTools.createURL("media#%s" ), id);
		return model.createResource(url);
	}

	public MediaEntityToRDF(Model model) {
		this.model = model;
		this.urlWriter = new UrlEntityToRDF(model);
	}

	public Resource writeURL(String url, int sizeType, Size size) {
		if (StringUtils.isBlank(url)) {
			throw new IllegalArgumentException("URL may not be null");
		}
		Resource retval = model.createResource(url);
		if (size != null) {
			retval.addLiteral(Galway2020.width, size.getWidth());
			retval.addLiteral(Galway2020.height, size.getHeight());
			if (size.getResize() == Size.FIT) {
				retval.addLiteral(Galway2020.resize, "Fit");
			}
			if (size.getResize() == Size.CROP) {
				retval.addLiteral(Galway2020.resize, "Crop");
			}
			if (sizeType >= 0 && sizeType < sizes.length) {
				retval.addLiteral(Galway2020.size, sizes[sizeType]);
			}
		}
		return retval;
	}

	public Resource writeURL(String url, int sizeType) {
		return writeURL(url, sizeType, null);
	}

	public Resource writeURL(String url) {
		return writeURL(url, -1, null);
	}

	public Resource write(MediaEntity mediaObj) {
		Resource media = getId(mediaObj.getId());

		if (StringUtils.isNotBlank(mediaObj.getURL())) {
			OwlFuncs.makeSameAs(media, urlWriter.write(mediaObj));
		}
		if (StringUtils.isNotBlank(mediaObj.getMediaURL())) {
			OwlFuncs.makeSameAs(media, writeURL(mediaObj.getMediaURL()));
		}
		if (StringUtils.isNotBlank(mediaObj.getMediaURLHttps())) {
			OwlFuncs.makeSameAs(media,
					writeURL(mediaObj.getMediaURLHttps()));
		}
		media.addProperty(DC_11.type, mediaObj.getType());
		if (mediaObj.getType().equals("photo"))
		{
			media.addProperty( RDF.type, FOAF.Image);
		}
		return media;
	}
}
