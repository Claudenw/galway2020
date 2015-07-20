/* CVS $Id: $ */
package org.xenei.galway2020.vocab; 
import org.apache.jena.rdf.model.*;
 
/**
 * Vocabulary definitions from file:/home/claude/git/galway2020/import/common/src/main/resources/vocabs/22-rdf-syntax-ns.ttl 
 * @author Auto-generated by schemagen on 20 Jul 2015 21:40 
 */
public class Rdf_syntax_ns {
    /** <p>The RDF model that holds the vocabulary terms</p> */
    private static Model m_model = ModelFactory.createDefaultModel();
    
    /** <p>The namespace of the vocabulary as a string</p> */
    public static final String NS = "http://www.w3.org/1999/02/22-rdf-syntax-ns#";
    
    /** <p>The namespace of the vocabulary as a string</p>
     *  @see #NS */
    public static String getURI() {return NS;}
    
    /** <p>The namespace of the vocabulary as a resource</p> */
    public static final Resource NAMESPACE = m_model.createResource( NS );
    
    /** <p>The first item in the subject RDF list.</p> */
    public static final Property first = m_model.createProperty( "http://www.w3.org/1999/02/22-rdf-syntax-ns#first" );
    
    /** <p>The object of the subject RDF statement.</p> */
    public static final Property object = m_model.createProperty( "http://www.w3.org/1999/02/22-rdf-syntax-ns#object" );
    
    /** <p>The predicate of the subject RDF statement.</p> */
    public static final Property predicate = m_model.createProperty( "http://www.w3.org/1999/02/22-rdf-syntax-ns#predicate" );
    
    /** <p>The rest of the subject RDF list after the first item.</p> */
    public static final Property rest = m_model.createProperty( "http://www.w3.org/1999/02/22-rdf-syntax-ns#rest" );
    
    /** <p>The subject of the subject RDF statement.</p> */
    public static final Property subject = m_model.createProperty( "http://www.w3.org/1999/02/22-rdf-syntax-ns#subject" );
    
    /** <p>The subject is an instance of a class.</p> */
    public static final Property type = m_model.createProperty( "http://www.w3.org/1999/02/22-rdf-syntax-ns#type" );
    
    /** <p>Idiomatic property used for structured values.</p> */
    public static final Property value = m_model.createProperty( "http://www.w3.org/1999/02/22-rdf-syntax-ns#value" );
    
    /** <p>The datatype of RDF literals storing fragments of HTML content</p> */
    public static final Resource HTML = m_model.createResource( "http://www.w3.org/1999/02/22-rdf-syntax-ns#HTML" );
    
    /** <p>The class of plain (i.e. untyped) literal values, as used in RIF and OWL 2</p> */
    public static final Resource PlainLiteral = m_model.createResource( "http://www.w3.org/1999/02/22-rdf-syntax-ns#PlainLiteral" );
    
    /** <p>The datatype of XML literal values.</p> */
    public static final Resource XMLLiteral = m_model.createResource( "http://www.w3.org/1999/02/22-rdf-syntax-ns#XMLLiteral" );
    
    /** <p>The datatype of language-tagged string values</p> */
    public static final Resource langString = m_model.createResource( "http://www.w3.org/1999/02/22-rdf-syntax-ns#langString" );
    
}
