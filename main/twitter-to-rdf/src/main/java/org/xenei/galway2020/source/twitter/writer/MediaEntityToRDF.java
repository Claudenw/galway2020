package org.xenei.galway2020.source.twitter.writer;

import org.apache.commons.lang3.StringUtils;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.vocabulary.DC_11;
import twitter4j.MediaEntity;
import twitter4j.MediaEntity.Size;

public class MediaEntityToRDF {

	private final Model model;
	private final UrlEntityToRDF urlWriter;
	private final static String[] sizes = { "thumb", "small", "medium", "large" };

	public Resource getId(long id) {
		String url = String.format(
				"http://galway2020.xenei.net/twitter/media/%s", id);
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
			retval.addLiteral(RDFWriter.width, size.getWidth());
			retval.addLiteral(RDFWriter.height, size.getHeight());
			if (size.getResize() == Size.FIT) {
				retval.addLiteral(RDFWriter.resize, "Fit");
			}
			if (size.getResize() == Size.CROP) {
				retval.addLiteral(RDFWriter.resize, "Crop");
			}
			if (sizeType >= 0 && sizeType < sizes.length) {
				retval.addLiteral(RDFWriter.size, sizes[sizeType]);
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
			RDFWriter.Util.markSameAs(media, urlWriter.write(mediaObj));
		}
		if (StringUtils.isNotBlank(mediaObj.getMediaURL())) {
			RDFWriter.Util.markSameAs(media, writeURL(mediaObj.getMediaURL()));
		}
		if (StringUtils.isNotBlank(mediaObj.getMediaURLHttps())) {
			RDFWriter.Util.markSameAs(media,
					writeURL(mediaObj.getMediaURLHttps()));
		}
		media.addProperty(DC_11.type, mediaObj.getType());
		return media;
	}
}
