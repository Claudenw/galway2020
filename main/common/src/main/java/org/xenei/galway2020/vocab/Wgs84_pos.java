/* CVS $Id: $ */
package org.xenei.galway2020.vocab; 
import org.apache.jena.rdf.model.*;
 
/**
 * Vocabulary definitions from file:/home/claude/git/galway2020/main/common/src/main/resources/vocabs/wgs84_pos.rdf 
 * @author Auto-generated by schemagen on 09 Jul 2015 20:52 
 */
public class Wgs84_pos {
    /** <p>The RDF model that holds the vocabulary terms</p> */
    private static Model m_model = ModelFactory.createDefaultModel();
    
    /** <p>The namespace of the vocabulary as a string</p> */
    public static final String NS = "http://www.w3.org/2003/01/geo/wgs84_pos#";
    
    /** <p>The namespace of the vocabulary as a string</p>
     *  @see #NS */
    public static String getURI() {return NS;}
    
    /** <p>The namespace of the vocabulary as a resource</p> */
    public static final Resource NAMESPACE = m_model.createResource( NS );
    
    /** <p>The WGS84 altitude of a SpatialThing (decimal meters above the local reference 
     *  ellipsoid).</p>
     */
    public static final Property alt = m_model.createProperty( "http://www.w3.org/2003/01/geo/wgs84_pos#alt" );
    
    /** <p>The WGS84 latitude of a SpatialThing (decimal degrees).</p> */
    public static final Property lat = m_model.createProperty( "http://www.w3.org/2003/01/geo/wgs84_pos#lat" );
    
    /** <p>A comma-separated representation of a latitude, longitude coordinate.</p> */
    public static final Property lat_long = m_model.createProperty( "http://www.w3.org/2003/01/geo/wgs84_pos#lat_long" );
    
    /** <p>The relation between something and the point, or other geometrical thing in 
     *  space, where it is. For example, the realtionship between a radio tower and 
     *  a Point with a given lat and long. Or a relationship between a park and its 
     *  outline as a closed arc of points, or a road and its location as a arc (a 
     *  sequence of points). Clearly in practice there will be limit to the accuracy 
     *  of any such statement, but one would expect an accuracy appropriate for the 
     *  size of the object and uses such as mapping .</p>
     */
    public static final Property location = m_model.createProperty( "http://www.w3.org/2003/01/geo/wgs84_pos#location" );
    
    /** <p>The WGS84 longitude of a SpatialThing (decimal degrees).</p> */
    public static final Property long_ = m_model.createProperty( "http://www.w3.org/2003/01/geo/wgs84_pos#long" );
    
    /** <p>A point, typically described using a coordinate system relative to Earth, 
     *  such as WGS84.Uniquely identified by lat/long/alt. i.e. spaciallyIntersects(P1, 
     *  P2) :- lat(P1, LAT), long(P1, LONG), alt(P1, ALT), lat(P2, LAT), long(P2, 
     *  LONG), alt(P2, ALT). sameThing(P1, P2) :- type(P1, Point), type(P2, Point), 
     *  spaciallyIntersects(P1, P2).</p>
     */
    public static final Resource Point = m_model.createResource( "http://www.w3.org/2003/01/geo/wgs84_pos#Point" );
    
    /** <p>Anything with spatial extent, i.e. size, shape, or position. e.g. people, 
     *  places, bowling balls, as well as abstract areas like cubes.</p>
     */
    public static final Resource SpatialThing = m_model.createResource( "http://www.w3.org/2003/01/geo/wgs84_pos#SpatialThing" );
    
}
