package org.xenei.galway2020.sink.fuseki;

import static org.junit.Assert.*;

import java.io.IOException;

import org.apache.commons.configuration.BaseConfiguration;
import org.apache.commons.configuration.Configuration;
import org.apache.jena.arq.querybuilder.SelectBuilder;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.rdf.model.ResourceFactory;
import org.junit.Test;

public class FusekiModelSinkTest {

	private Configuration cfg;
	private Model model;
	private FusekiModelSink sink;
	
	public FusekiModelSinkTest() {
		model = ModelFactory.createDefaultModel();
		model.add( ResourceFactory.createResource("urn:foo:s"), 
				 ResourceFactory.createProperty("urn:foo:p1"),
				 ResourceFactory.createResource("urn:foo:o1"));
		cfg = new BaseConfiguration();
		cfg.addProperty( "host", "http://localhost:3030/tst/update");
		sink = new FusekiModelSink( cfg );
	}
	
//	@Test
//	public void testInsert() throws IOException
//	{
////		SelectBuilder qb = new SelectBuilder().addVar( "?s")
////				.addWhere( "?s",  "?p", "?o");
////		
//		assertTrue( sink.insert(model) );
//	}
//	
//	
//	@Test
//	public void testDelete() throws IOException
//	{
////		SelectBuilder qb = new SelectBuilder().addVar( "?s")
////				.addWhere( "?s",  "?p", "?o");
////		
//		assertTrue( sink.delete(model) );
//	}
	
}
