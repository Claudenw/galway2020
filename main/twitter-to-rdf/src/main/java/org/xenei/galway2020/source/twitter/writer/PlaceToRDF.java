package org.xenei.galway2020.source.twitter.writer;

import org.apache.commons.lang3.StringUtils;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.vocabulary.DC_11;

import twitter4j.Place;

public class PlaceToRDF {

	private final Model model;
	private final GeoLocationToRDF geoLocationWriter;

	public PlaceToRDF(Model model) {
		this.model = model;
		this.geoLocationWriter = new GeoLocationToRDF(model);
	}

	public Resource getId(String id) {
		String url = String.format(
				"http://galway2020.xenei.net/twitter/place#%s", id);
		return model.createResource(url, RDFWriter.gnFeature);
	}

	public Resource write(Place placeObj) {
		Resource place = getId(placeObj.getId());
		if (placeObj.getBoundingBoxCoordinates() != null) {
			place.addProperty(RDFWriter.boundingBox, geoLocationWriter
					.writeCoordinates(placeObj.getBoundingBoxCoordinates()));
		}
		if (StringUtils.isNotEmpty(placeObj.getBoundingBoxType())) {
			place.addLiteral(RDFWriter.boundingBoxType,
					placeObj.getBoundingBoxType());
		}
		for (Place subPlace : placeObj.getContainedWithIn()) {
			place.addProperty(RDFWriter.containedWithin, write(subPlace));
		}
		if (StringUtils.isNotEmpty(placeObj.getCountry())) {
			place.addLiteral(RDFWriter.country, placeObj.getCountry());
		}
		if (StringUtils.isNotEmpty(placeObj.getCountryCode())) {
			place.addLiteral(RDFWriter.countryCode, placeObj.getCountryCode());
		}
		if (StringUtils.isNotEmpty(placeObj.getFullName())) {
			place.addLiteral(RDFWriter.gnName, placeObj.getFullName());
		}
		if (placeObj.getGeometryCoordinates() != null) {
			place.addProperty(RDFWriter.geometry, geoLocationWriter
					.writeCoordinates(placeObj.getGeometryCoordinates()));
		}
		if (StringUtils.isNotEmpty(placeObj.getGeometryType())) {
			place.addLiteral(RDFWriter.geometryType, placeObj.getGeometryType());
		}
		if (StringUtils.isNotEmpty(placeObj.getName())) {
			place.addLiteral(DC_11.title, placeObj.getName());
		}
		if (StringUtils.isNotEmpty(placeObj.getPlaceType())) {
			place.addLiteral(RDFWriter.placeType, placeObj.getPlaceType());
		}
		if (StringUtils.isNotEmpty(placeObj.getStreetAddress())) {
			place.addLiteral(RDFWriter.streetAddress,
					placeObj.getStreetAddress());
		}
		if (StringUtils.isNotEmpty(placeObj.getURL())) {
			RDFWriter.Util.markSameAs(place,
					model.createResource(placeObj.getURL()));
		}
		return place;
	}
}
