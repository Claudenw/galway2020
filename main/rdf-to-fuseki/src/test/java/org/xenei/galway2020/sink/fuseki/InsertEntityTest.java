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

		entity = new InsertEntity(model);
		
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
//	@Test
//	public void testWriteGZIP() throws IOException {
//		entity = new InsertEntity(model, FusekiModelSink.GZIP);
//		
//		Header header = entity.getContentType();
//		assertEquals("Content-Type", header.getName());
//		assertEquals("application/sparql-update; charset=UTF-8",
//				header.getValue());
//
//		header = entity.getContentEncoding();
//		assertEquals("Content-Encoding", header.getName());
//		assertEquals(FusekiModelSink.GZIP, header.getValue());
//
//		assertEquals(-1, entity.getContentLength());
//		ByteArrayOutputStream baos = new ByteArrayOutputStream();
//		entity.writeTo(baos);
//		baos.close();
//		ByteArrayOutputStream baos2 = new ByteArrayOutputStream();
//		IOUtils.copy(
//				new GZIPInputStream(
//						new ByteArrayInputStream(baos.toByteArray())), baos2);
//		String s = new String(baos2.toByteArray());
//		assertEquals( expected, s );
//	}
//
//	@Test
//	public void testWriteGZIPToGraph() throws IOException {
//		entity = new InsertEntity(model, "graphname", FusekiModelSink.GZIP);
//		
//		Header header = entity.getContentType();
//		assertEquals("Content-Type", header.getName());
//		assertEquals("application/sparql-update; charset=UTF-8",
//				header.getValue());
//
//		header = entity.getContentEncoding();
//		assertEquals("Content-Encoding", header.getName());
//		assertEquals(FusekiModelSink.GZIP, header.getValue());
//
//		assertEquals(-1, entity.getContentLength());
//		ByteArrayOutputStream baos = new ByteArrayOutputStream();
//		entity.writeTo(baos);
//		baos.flush();
//		ByteArrayOutputStream baos2 = new ByteArrayOutputStream();
//		IOUtils.copy(
//				new GZIPInputStream(
//						new ByteArrayInputStream(baos.toByteArray())), baos2);
//		String s = new String(baos2.toByteArray());
//		assertEquals( expectedNamed, s );
//	}
//	
//	@Test
//	public void testWriteDeflate() throws IOException {
//		entity = new InsertEntity(model, FusekiModelSink.DEFLATE);
//		
//		Header header = entity.getContentType();
//		assertEquals("Content-Type", header.getName());
//		assertEquals("application/sparql-update; charset=UTF-8",
//				header.getValue());
//
//		header = entity.getContentEncoding();
//		assertEquals("Content-Encoding", header.getName());
//		assertEquals(FusekiModelSink.DEFLATE, header.getValue());
//
//		assertEquals(-1, entity.getContentLength());
//		ByteArrayOutputStream baos = new ByteArrayOutputStream();
//		entity.writeTo(baos);
//		baos.flush();
//		ByteArrayOutputStream baos2 = new ByteArrayOutputStream();
//		IOUtils.copy(
//				new DeflateInputStream(new ByteArrayInputStream(baos
//						.toByteArray())), baos2);
//		String s = new String(baos.toByteArray());
//		assertEquals( expected, s );
//	}
//	
//	@Test
//	public void testWriteDeflateToGraph() throws IOException {
//		entity = new InsertEntity(model, "graphname", FusekiModelSink.DEFLATE);
//		
//		Header header = entity.getContentType();
//		assertEquals("Content-Type", header.getName());
//		assertEquals("application/sparql-update; charset=UTF-8",
//				header.getValue());
//
//		header = entity.getContentEncoding();
//		assertEquals("Content-Encoding", header.getName());
//		assertEquals(FusekiModelSink.DEFLATE, header.getValue());
//
//		assertEquals(-1, entity.getContentLength());
//		ByteArrayOutputStream baos = new ByteArrayOutputStream();
//		entity.writeTo(baos);
//		baos.flush();
//		ByteArrayOutputStream baos2 = new ByteArrayOutputStream();
//		IOUtils.copy(
//				new DeflateInputStream(new ByteArrayInputStream(baos
//						.toByteArray())), baos2);
//		String s = new String(baos.toByteArray());
//		assertEquals( expectedNamed, s );
//	}
//	
//	@Test
//	public void testGZIP() throws IOException {
//		
//		ByteArrayOutputStream baos = new ByteArrayOutputStream();
//		OutputStream out = new GZIPOutputStream( baos, true );
//		out.write( "Hello World".getBytes());
//		out.flush();
//		
//		ByteArrayOutputStream baos2 = new ByteArrayOutputStream();
//		IOUtils.copy(
//				new GZIPInputStream(
//						new ByteArrayInputStream(baos.toByteArray())), baos2);
//		String s = new String(baos2.toByteArray());
//		assertEquals( "Hello World", s );
//	}
//	
//	@Test
//	public void testGZIPRead() throws IOException {
//		InputStream is = InsertEntityTest.class.getResourceAsStream( "/x.gzip");
//		
//		ByteArrayOutputStream baos2 = new ByteArrayOutputStream();
//		IOUtils.copy(
//				new GZIPInputStream(
//						is), baos2);
//		String s = new String(baos2.toByteArray());
//		assertEquals( "Hello World\n", s );
//		
//		is = InsertEntityTest.class.getResourceAsStream( "/x.gzip");
//		ByteArrayOutputStream baos3 = new ByteArrayOutputStream();
//		IOUtils.copy(
//				is, baos3);
// s = new String(baos3.toByteArray());
//		assertEquals( "Hello World", s );
//	}
}
