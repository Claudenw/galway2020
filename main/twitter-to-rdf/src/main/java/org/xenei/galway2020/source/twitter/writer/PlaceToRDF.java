package org.xenei.galway2020.source.twitter.writer;

import org.apache.commons.lang3.StringUtils;
import org.apache.jena.datatypes.BaseDatatype;
import org.apache.jena.datatypes.RDFDatatype;
import org.apache.jena.graph.impl.LiteralLabel;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.rdf.model.ResourceFactory;
import org.apache.jena.vocabulary.DC_11;
import org.xenei.galway2020.utils.OwlFuncs;
import org.xenei.galway2020.vocab.Galway2020;
import org.xenei.galway2020.vocab.Geonames;
import org.xenei.galway2020.vocab.Geosparql;

import twitter4j.GeoLocation;
import twitter4j.Place;

public class PlaceToRDF {

	private final Model model;
	private final GeoLocationToRDF geoLocationWriter;
	
	private static final WktLiteral wktLiteral = new WktLiteral();

	public PlaceToRDF(Model model) {
		this.model = model;
		this.geoLocationWriter = new GeoLocationToRDF(model);
	}

	public Resource getId(String id) {
		String url = String.format(
				"http://galway2020.xenei.net/twitter/place#%s", id);
		return model.createResource(url, Geonames.Feature);
	}

	public void addBoundingBox( Resource resource,  String type, GeoLocation[][] boundingBox)
	{
		StringBuilder sb = new StringBuilder( type );
		for (int i=0;i<boundingBox.length;i++)
		{
			if (i==0)
			{
				sb.append("(");
			}
			else
			{
				sb.append(", ");
			}
			GeoLocation[] geolocSet = boundingBox[i];
			for (int j=0;j<geolocSet.length;j++)
			{
				if (j==0)
				{
					sb.append("(");
				}
				else
				{
					sb.append(", ");
				}
				sb.append( String.format( "%s %s" , geolocSet[j].getLongitude(), geolocSet[j].getLatitude()));
			}
			sb.append(")");
		}
		sb.append(")");
		
		//Literal lit = ResourceFactory.createTypedLiteral(sb.toString(), dType)
		resource.addProperty( Geosparql.asWKT, sb.toString());
	}
	
	public Resource write(Place placeObj) {
		Resource place = getId(placeObj.getId());
		// add the bounding box
		if (placeObj.getBoundingBoxCoordinates() != null) {
			Resource r = model.createResource();
			place.addProperty( Galway2020.boundingBox, r );
			addBoundingBox( r, placeObj.getBoundingBoxType(), placeObj.getBoundingBoxCoordinates());	
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
		 // add geometry
		if (placeObj.getGeometryCoordinates() != null) {
			Resource r = model.createResource();
			place.addProperty( Galway2020.geometry, r );
			addBoundingBox( r, placeObj.getGeometryType(), placeObj.getGeometryCoordinates());		
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
	
	public static class WktLiteral extends BaseDatatype {
	    

	    public static final String CRS84 = "<http://www.opengis.net/def/crs/OGC/1.3/CRS84>";

	    //public static final RDFDatatype wktLiteralType = new WktLiteral();

	    private WktLiteral() {
	        super(Geosparql.wktLiteral.getURI());
	    }

	    /**
	     * Convert a value of this datatype out
	     * to lexical form.
	     */
	    public String unparse(Object value) {
	        return value.toString();
	    }

	    /**
	     * Parse a lexical form of this datatype to a value
	     */
	    public Object parse(String lexicalForm) {
	        return new TypedValue(String.format("%s %s", WktLiteral.CRS84, lexicalForm), this.getURI());
	    }

	    /**
	     * Compares two instances of values of the given datatype.
	     * This does not allow rationals to be compared to other number
	     * formats, Lang tag is not significant.
	     *
	     * @param value1 First value to compare
	     * @param value2 Second value to compare
	     * @return Value to determine whether both are equal.
	     */
	    public boolean isEqual(LiteralLabel value1, LiteralLabel value2) {
	        return value1.getDatatype() == value2.getDatatype()
	                && value1.getValue().equals(value2.getValue());
	    }
	}
}
