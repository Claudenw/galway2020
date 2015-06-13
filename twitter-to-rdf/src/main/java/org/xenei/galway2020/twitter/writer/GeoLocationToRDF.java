package org.xenei.galway2020.twitter.writer;

import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.RDFList;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.vocabulary.RDF;

import twitter4j.GeoLocation;

public class GeoLocationToRDF {

	private final Model model;

	public GeoLocationToRDF(Model model) {
		this.model = model;
	}

	public Resource write(GeoLocation geoLoc) {
		Resource point = model.createResource();
		point.addProperty(RDF.type, RDFWriter.gnPoint);

		point.addLiteral(RDFWriter.gnLat, geoLoc.getLatitude());
		point.addLiteral(RDFWriter.gnLong, geoLoc.getLongitude());
		return point;
	}

	public Resource writeCoordinates(GeoLocation[][] geoLocMatrix) {
		RDFList lst = model.createList();
		for (GeoLocation[] geoLst : geoLocMatrix) {
			RDFList subList = model.createList();
			lst.add(subList);
			for (GeoLocation geoLoc : geoLst) {
				lst.add(write(geoLoc));
			}
		}
		return lst;
	}
}
