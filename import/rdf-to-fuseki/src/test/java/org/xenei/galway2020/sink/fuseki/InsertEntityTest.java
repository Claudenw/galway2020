package org.xenei.galway2020.sink.fuseki;

import static org.junit.Assert.*;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

import org.apache.commons.io.IOUtils;
import org.apache.http.Header;
import org.apache.http.client.entity.DeflateInputStream;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.rdf.model.ResourceFactory;
import org.junit.Test;

public class InsertEntityTest {

	//private Configuration cfg;
	private Model model;
	private InsertEntity entity;

	private static final String eol=System.getProperty("line.separator");
	private static final String expected = "INSERT DATA \n{\n<urn:foo:s>  <urn:foo:p>  <urn:foo:o> .\n\n}\n";
	private static final String expectedNamed = "INSERT DATA \n{\nGRAPH <graphname> { \n<urn:foo:s>  <urn:foo:p>  <urn:foo:o> .\n\n}\n}\n";
	public InsertEntityTest() {
		model = ModelFactory.createDefaultModel();
		model.add(ResourceFactory.createResource("urn:foo:s"),
				ResourceFactory.createProperty("urn:foo:p"),
				ResourceFactory.createResource("urn:foo:o"));
	//	cfg = new BaseConfiguration();
	//	cfg.addProperty("host.update", "http://localhost:3030/tst/data?default");
	}

	@Test
	public void testWrite() throws IOException {

		entity = new InsertEntity(model, null);
		
		Header header = entity.getContentType();
		assertEquals("Content-Type", header.getName());
		assertEquals("application/sparql-update; charset=UTF-8",
				header.getValue());

		header = entity.getContentEncoding();
		assertNull(header);

		assertEquals(-1, entity.getContentLength());
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		entity.writeTo(baos);
		baos.flush();
		String s = new String(baos.toByteArray());
		assertEquals( expected, s );
	}

	@Test
	public void testWriteToGraph() throws IOException {

		entity = new InsertEntity(model, "graphname");
		
		Header header = entity.getContentType();
		assertEquals("Content-Type", header.getName());
		assertEquals("application/sparql-update; charset=UTF-8",
				header.getValue());

		header = entity.getContentEncoding();
		assertNull(header);

		assertEquals(-1, entity.getContentLength());
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		entity.writeTo(baos);
		baos.flush();
		String s = new String(baos.toByteArray());
		assertEquals( expectedNamed, s );
	}

}
