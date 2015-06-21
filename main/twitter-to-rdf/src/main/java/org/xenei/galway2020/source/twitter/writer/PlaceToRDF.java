package org.xenei.galway2020.source.twitter.writer;

import org.apache.commons.lang3.StringUtils;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.vocabulary.DC_11;
import org.xenei.galway2020.utils.OwlFuncs;
import org.xenei.galway2020.vocab.Galway2020;
import org.xenei.galway2020.vocab.Geonames;

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
		return model.createResource(url, Geonames.Feature);
	}

	public Resource write(Place placeObj) {
		Resource place = getId(placeObj.getId());
		if (placeObj.getBoundingBoxCoordinates() != null) {
			place.addProperty(Galway2020.boundingBox, geoLocationWriter
					.writeCoordinates(placeObj.getBoundingBoxCoordinates()));
		}
		if (StringUtils.isNotEmpty(placeObj.getBoundingBoxType())) {
			place.addLiteral(Galway2020.boundingBoxType,
					placeObj.getBoundingBoxType());
		}
		for (Place subPlace : placeObj.getContainedWithIn()) {
			place.addProperty(Galway2020.containedBy, write(subPlace));
		}
		if (StringUtils.isNotEmpty(placeObj.getCountry())) {
			place.addLiteral(Galway2020.country, placeObj.getCountry());
		}
		if (StringUtils.isNotEmpty(placeObj.getCountryCode())) {
			place.addLiteral(Galway2020.countryCode, placeObj.getCountryCode());
		}
		if (StringUtils.isNotEmpty(placeObj.getFullName())) {
			place.addLiteral(Geonames.name, placeObj.getFullName());
		}
		if (placeObj.getGeometryCoordinates() != null) {
			place.addProperty(Galway2020.geometry, geoLocationWriter
					.writeCoordinates(placeObj.getGeometryCoordinates()));
		}
		if (StringUtils.isNotEmpty(placeObj.getGeometryType())) {
			place.addLiteral(Galway2020.geometryType, placeObj.getGeometryType());
		}
		if (StringUtils.isNotEmpty(placeObj.getName())) {
			place.addLiteral(DC_11.title, placeObj.getName());
		}
		if (StringUtils.isNotEmpty(placeObj.getPlaceType())) {
			place.addLiteral(Galway2020.placeType, placeObj.getPlaceType());
		}
		if (StringUtils.isNotEmpty(placeObj.getStreetAddress())) {
			place.addLiteral(Galway2020.streetAddress,
					placeObj.getStreetAddress());
		}
		if (StringUtils.isNotEmpty(placeObj.getURL())) {
			OwlFuncs.makeSameAs(place,
					model.createResource(placeObj.getURL()));
		}
		return place;
	}
}
