package org.xenei.galway2020.twitter.writer;

import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.sparql.vocabulary.FOAF;
import org.apache.jena.vocabulary.DC;
import org.apache.jena.vocabulary.OWL;

import twitter4j.GeoLocation;
import twitter4j.Place;

public class PlaceToRDF {

	private final Model model;
	private final GeoLocationToRDF geoLocationWriter;
	
	public PlaceToRDF(Model model)
	{
		this.model = model;
		this.geoLocationWriter = new GeoLocationToRDF( model );
	}
	
	public Resource getId( String id )
	{	
			String url = String.format("http://galway2020.xenei.net/twitter/place#%s", id );
			return model.createResource( url, RDFWriter.gnFeature );
	}
	
	public Resource write( Place placeObj )
	{
		Resource place = getId( placeObj.getId() );
		// FIXME should this be an array like RDF structure?
		place.addProperty( RDFWriter.boundingBox, geoLocationWriter.writeCoordinates( placeObj.getBoundingBoxCoordinates() ));
		
		
		place.addLiteral( RDFWriter.boundingBoxType, placeObj.getBoundingBoxType());
		
		for (Place subPlace : placeObj.getContainedWithIn())
		{
			place.addProperty( RDFWriter.containedWithin, write( subPlace ));
		}
		
		place.addLiteral( RDFWriter.country, placeObj.getCountry() );
		place.addLiteral( RDFWriter.countryCode, placeObj.getCountryCode() );
		place.addLiteral( RDFWriter.gnName,	placeObj.getFullName());
		place.addProperty( RDFWriter.geometry, geoLocationWriter.writeCoordinates( placeObj.getGeometryCoordinates() ));
		place.addLiteral( RDFWriter.geometryType, placeObj.getGeometryType());
		place.addLiteral( DC.title, placeObj.getName());
		place.addLiteral( RDFWriter.placeType, placeObj.getPlaceType());
		place.addLiteral( RDFWriter.streetAddress, placeObj.getStreetAddress());
		place.addProperty( OWL.sameAs, model.createResource(placeObj.getURL()));
		return place;
	}
}
