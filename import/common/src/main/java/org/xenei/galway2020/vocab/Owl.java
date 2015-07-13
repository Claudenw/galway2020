/* CVS $Id: $ */
package org.xenei.galway2020.vocab; 
import org.apache.jena.rdf.model.*;
 
/**
 * Vocabulary definitions from file:/home/claude/git/galway2020/import/common/src/main/resources/vocabs/owl.ttl 
 * @author Auto-generated by schemagen on 13 Jul 2015 18:21 
 */
public class Owl {
    /** <p>The RDF model that holds the vocabulary terms</p> */
    private static Model m_model = ModelFactory.createDefaultModel();
    
    /** <p>The namespace of the vocabulary as a string</p> */
    public static final String NS = "http://www.w3.org/2002/07/owl#";
    
    /** <p>The namespace of the vocabulary as a string</p>
     *  @see #NS */
    public static String getURI() {return NS;}
    
    /** <p>The namespace of the vocabulary as a resource</p> */
    public static final Resource NAMESPACE = m_model.createResource( NS );
    
    /** <p>The ontology's owl:versionInfo as a string</p> */
    public static final String VERSION_INFO = "$Date: 2009/11/15 10:54:12 $";
    
    /** <p>The property that determines the class that a universal property restriction 
     *  refers to.</p>
     */
    public static final Property allValuesFrom = m_model.createProperty( "http://www.w3.org/2002/07/owl#allValuesFrom" );
    
    /** <p>The property that determines the predicate of an annotated axiom or annotated 
     *  annotation.</p>
     */
    public static final Property annotatedProperty = m_model.createProperty( "http://www.w3.org/2002/07/owl#annotatedProperty" );
    
    /** <p>The property that determines the subject of an annotated axiom or annotated 
     *  annotation.</p>
     */
    public static final Property annotatedSource = m_model.createProperty( "http://www.w3.org/2002/07/owl#annotatedSource" );
    
    /** <p>The property that determines the object of an annotated axiom or annotated 
     *  annotation.</p>
     */
    public static final Property annotatedTarget = m_model.createProperty( "http://www.w3.org/2002/07/owl#annotatedTarget" );
    
    /** <p>The property that determines the predicate of a negative property assertion.</p> */
    public static final Property assertionProperty = m_model.createProperty( "http://www.w3.org/2002/07/owl#assertionProperty" );
    
    /** <p>The property that determines the cardinality of an exact cardinality restriction.</p> */
    public static final Property cardinality = m_model.createProperty( "http://www.w3.org/2002/07/owl#cardinality" );
    
    /** <p>The property that determines that a given class is the complement of another 
     *  class.</p>
     */
    public static final Property complementOf = m_model.createProperty( "http://www.w3.org/2002/07/owl#complementOf" );
    
    /** <p>The property that determines that a given data range is the complement of 
     *  another data range with respect to the data domain.</p>
     */
    public static final Property datatypeComplementOf = m_model.createProperty( "http://www.w3.org/2002/07/owl#datatypeComplementOf" );
    
    /** <p>The property that determines that two given individuals are different.</p> */
    public static final Property differentFrom = m_model.createProperty( "http://www.w3.org/2002/07/owl#differentFrom" );
    
    /** <p>The property that determines that a given class is equivalent to the disjoint 
     *  union of a collection of other classes.</p>
     */
    public static final Property disjointUnionOf = m_model.createProperty( "http://www.w3.org/2002/07/owl#disjointUnionOf" );
    
    /** <p>The property that determines that two given classes are disjoint.</p> */
    public static final Property disjointWith = m_model.createProperty( "http://www.w3.org/2002/07/owl#disjointWith" );
    
    /** <p>The property that determines the collection of pairwise different individuals 
     *  in a owl:AllDifferent axiom.</p>
     */
    public static final Property distinctMembers = m_model.createProperty( "http://www.w3.org/2002/07/owl#distinctMembers" );
    
    /** <p>The property that determines that two given classes are equivalent, and that 
     *  is used to specify datatype definitions.</p>
     */
    public static final Property equivalentClass = m_model.createProperty( "http://www.w3.org/2002/07/owl#equivalentClass" );
    
    /** <p>The property that determines that two given properties are equivalent.</p> */
    public static final Property equivalentProperty = m_model.createProperty( "http://www.w3.org/2002/07/owl#equivalentProperty" );
    
    /** <p>The property that determines the collection of properties that jointly build 
     *  a key.</p>
     */
    public static final Property hasKey = m_model.createProperty( "http://www.w3.org/2002/07/owl#hasKey" );
    
    /** <p>The property that determines the property that a self restriction refers to.</p> */
    public static final Property hasSelf = m_model.createProperty( "http://www.w3.org/2002/07/owl#hasSelf" );
    
    /** <p>The property that determines the individual that a has-value restriction refers 
     *  to.</p>
     */
    public static final Property hasValue = m_model.createProperty( "http://www.w3.org/2002/07/owl#hasValue" );
    
    /** <p>The property that determines the collection of classes or data ranges that 
     *  build an intersection.</p>
     */
    public static final Property intersectionOf = m_model.createProperty( "http://www.w3.org/2002/07/owl#intersectionOf" );
    
    /** <p>The property that determines that two given properties are inverse.</p> */
    public static final Property inverseOf = m_model.createProperty( "http://www.w3.org/2002/07/owl#inverseOf" );
    
    /** <p>The property that determines the cardinality of a maximum cardinality restriction.</p> */
    public static final Property maxCardinality = m_model.createProperty( "http://www.w3.org/2002/07/owl#maxCardinality" );
    
    /** <p>The property that determines the cardinality of a maximum qualified cardinality 
     *  restriction.</p>
     */
    public static final Property maxQualifiedCardinality = m_model.createProperty( "http://www.w3.org/2002/07/owl#maxQualifiedCardinality" );
    
    /** <p>The property that determines the collection of members in either a owl:AllDifferent, 
     *  owl:AllDisjointClasses or owl:AllDisjointProperties axiom.</p>
     */
    public static final Property members = m_model.createProperty( "http://www.w3.org/2002/07/owl#members" );
    
    /** <p>The property that determines the cardinality of a minimum cardinality restriction.</p> */
    public static final Property minCardinality = m_model.createProperty( "http://www.w3.org/2002/07/owl#minCardinality" );
    
    /** <p>The property that determines the cardinality of a minimum qualified cardinality 
     *  restriction.</p>
     */
    public static final Property minQualifiedCardinality = m_model.createProperty( "http://www.w3.org/2002/07/owl#minQualifiedCardinality" );
    
    /** <p>The property that determines the class that a qualified object cardinality 
     *  restriction refers to.</p>
     */
    public static final Property onClass = m_model.createProperty( "http://www.w3.org/2002/07/owl#onClass" );
    
    /** <p>The property that determines the data range that a qualified data cardinality 
     *  restriction refers to.</p>
     */
    public static final Property onDataRange = m_model.createProperty( "http://www.w3.org/2002/07/owl#onDataRange" );
    
    /** <p>The property that determines the datatype that a datatype restriction refers 
     *  to.</p>
     */
    public static final Property onDatatype = m_model.createProperty( "http://www.w3.org/2002/07/owl#onDatatype" );
    
    /** <p>The property that determines the n-tuple of properties that a property restriction 
     *  on an n-ary data range refers to.</p>
     */
    public static final Property onProperties = m_model.createProperty( "http://www.w3.org/2002/07/owl#onProperties" );
    
    /** <p>The property that determines the property that a property restriction refers 
     *  to.</p>
     */
    public static final Property onProperty = m_model.createProperty( "http://www.w3.org/2002/07/owl#onProperty" );
    
    /** <p>The property that determines the collection of individuals or data values 
     *  that build an enumeration.</p>
     */
    public static final Property oneOf = m_model.createProperty( "http://www.w3.org/2002/07/owl#oneOf" );
    
    /** <p>The property that determines the n-tuple of properties that build a sub property 
     *  chain of a given property.</p>
     */
    public static final Property propertyChainAxiom = m_model.createProperty( "http://www.w3.org/2002/07/owl#propertyChainAxiom" );
    
    /** <p>The property that determines that two given properties are disjoint.</p> */
    public static final Property propertyDisjointWith = m_model.createProperty( "http://www.w3.org/2002/07/owl#propertyDisjointWith" );
    
    /** <p>The property that determines the cardinality of an exact qualified cardinality 
     *  restriction.</p>
     */
    public static final Property qualifiedCardinality = m_model.createProperty( "http://www.w3.org/2002/07/owl#qualifiedCardinality" );
    
    /** <p>The property that determines that two given individuals are equal.</p> */
    public static final Property sameAs = m_model.createProperty( "http://www.w3.org/2002/07/owl#sameAs" );
    
    /** <p>The property that determines the class that an existential property restriction 
     *  refers to.</p>
     */
    public static final Property someValuesFrom = m_model.createProperty( "http://www.w3.org/2002/07/owl#someValuesFrom" );
    
    /** <p>The property that determines the subject of a negative property assertion.</p> */
    public static final Property sourceIndividual = m_model.createProperty( "http://www.w3.org/2002/07/owl#sourceIndividual" );
    
    /** <p>The property that determines the object of a negative object property assertion.</p> */
    public static final Property targetIndividual = m_model.createProperty( "http://www.w3.org/2002/07/owl#targetIndividual" );
    
    /** <p>The property that determines the value of a negative data property assertion.</p> */
    public static final Property targetValue = m_model.createProperty( "http://www.w3.org/2002/07/owl#targetValue" );
    
    /** <p>The property that determines the collection of classes or data ranges that 
     *  build a union.</p>
     */
    public static final Property unionOf = m_model.createProperty( "http://www.w3.org/2002/07/owl#unionOf" );
    
    /** <p>The property that determines the collection of facet-value pairs that define 
     *  a datatype restriction.</p>
     */
    public static final Property withRestrictions = m_model.createProperty( "http://www.w3.org/2002/07/owl#withRestrictions" );
    
    /** <p>This is the empty class.</p> */
    public static final Resource Nothing = m_model.createResource( "http://www.w3.org/2002/07/owl#Nothing" );
    
    /** <p>The class of OWL individuals.</p> */
    public static final Resource Thing = m_model.createResource( "http://www.w3.org/2002/07/owl#Thing" );
    
}