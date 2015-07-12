package org.xenei.galway2020.enhancer.reasoner;

import static org.junit.Assert.assertTrue;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URISyntaxException;

import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.rdf.model.ResourceFactory;
import org.apache.jena.sparql.vocabulary.FOAF;
import org.apache.jena.vocabulary.RDF;
import org.junit.Before;
import org.junit.Test;
import org.xenei.galway2020.vocab.Galway2020;

public class ReasonerEnhancerTest {
	private ReasonerEnhancer enhancer;

	@Before
	public void setup() throws URISyntaxException, IOException {
		enhancer = new ReasonerEnhancer(null);
	}

	@Test
	public void testAddition() throws FileNotFoundException {
		Model m = ModelFactory.createDefaultModel();
		Resource s = ResourceFactory.createResource("http://example.com/test");
		m.add(s, RDF.type, Galway2020.Tweet);
		Model m2 =  enhancer.apply(m);
		
	
		//m2.getInfModel().write( System.out, "TURTLE");
		assertTrue(m2.contains(s, RDF.type, FOAF.Document));
		
	}

}
