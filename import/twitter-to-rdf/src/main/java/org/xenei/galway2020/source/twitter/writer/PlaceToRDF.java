package org.xenei.galway2020.source.twitter.writer;

import org.apache.commons.lang3.StringUtils;
import org.apache.jena.datatypes.BaseDatatype;
import org.apache.jena.datatypes.RDFDatatype;
import org.apache.jena.graph.impl.LiteralLabel;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.rdf.model.ResourceFactory;
import org.apache.jena.vocabulary.DC_11;
import org.apache.jena.vocabulary.RDF;
import org.xenei.galway2020.utils.NSTools;
import org.xenei.galway2020.utils.OwlFuncs;
import org.xenei.galway2020.vocab.Galway2020;
import org.xenei.galway2020.vocab.Geonames;
import org.xenei.galway2020.vocab.Geosparql;
import org.xenei.galway2020.vocab.Wgs84_pos;

import twitter4j.GeoLocation;
import twitter4j.Place;

public class PlaceToRDF {

	private final Model model;
	
	private static final WktLiteral wktLiteral = new WktLiteral();

	private static final String ID_FMT = NSTools.createURL("place/id%s");
	
	public PlaceToRDF(Model model) {
		this.model = model;
	}
	
	public Resource getId(String id) {
		String url = String.format(ID_FMT, id);
		return model.createResource(url, Geonames.Feature);
	}
	
	public Resource getSubId(String id, String type) {
		String url = String.format(
				ID_FMT+"/%s", id,type);
		return  model.createResource(url);
	}
	

	/**
	 * Write a bounding box entry on the resource as a "asWKT
	 * @param resource The resource to add the bounding box to.
	 * @param type The type of bounding box.
	 * @param boundingBox The collection of GeoLocations.
	 */
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
	
	public Resource collapseToPoint( Resource point, GeoLocation[][] boundingBox)
	{
		Double lon = 0.0;
		Double lat = 0.0;
		int count = 0;
		for (GeoLocation[] geoLocSet : boundingBox )
		{
			for (GeoLocation geoLoc : geoLocSet)
			{
				count++;
				lat += geoLoc.getLatitude();
				lon += geoLoc.getLongitude();
			}
		}
		lon /= count;
		lat /= count;
		point.addProperty(RDF.type, Wgs84_pos.Point);
		point.addLiteral(Wgs84_pos.lat, lat);
		point.addLiteral(Wgs84_pos.long_, lon);
		return point;
	}
	
	/**
	 * Write a geolocation as a point object at r.
	 * @param point the Resource to write the location on.
	 * @param geoLoc The location to write
	 * @return The point parameter
	 */
	public Resource writePoint(Resource point, GeoLocation geoLoc) {
		point.addProperty(RDF.type, Wgs84_pos.Point);
		point.addLiteral(Wgs84_pos.lat, geoLoc.getLatitude());
		point.addLiteral(Wgs84_pos.long_, geoLoc.getLongitude());
		return point;
	}
	
	public Resource write(Place placeObj) {
		Resource place = getId(placeObj.getId());
		// add the bounding box
		if (placeObj.getBoundingBoxCoordinates() != null) {
			Resource r = getSubId( placeObj.getId(), "boundingBox");
			place.addProperty( Galway2020.boundingBox, r );
			addBoundingBox( r, placeObj.getBoundingBoxType(), placeObj.getBoundingBoxCoordinates());
			collapseToPoint( Galway2020.geoLocation, placeObj.getBoundingBoxCoordinates());
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
			Resource r = getSubId( placeObj.getId(), "geometry");
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
