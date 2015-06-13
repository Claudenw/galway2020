package org.xenei.galway2020.twitter.writer;

import java.io.InputStream;
import java.net.URL;

import org.apache.commons.io.IOUtils;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.junit.Before;
import org.junit.Test;

import twitter4j.JSONArray;
import twitter4j.JSONException;
import twitter4j.JSONObject;
import twitter4j.JSONTokener;
import twitter4j.Status;
import twitter4j.TwitterException;
import twitter4j.TwitterObjectFactory;

public class StatusToRDFTest {

	private TwitterObjectFactory factory;
	private JSONArray statuses;
	private Model model;
	private StatusToRDF writer;
	
	@Before
	public void setup() throws JSONException {	
		model = ModelFactory.createDefaultModel();
		writer = new StatusToRDF( model );

		InputStream is = StatusToRDFTest.class.getResourceAsStream( "/status.json");
		JSONTokener tokener = new JSONTokener( is );
		JSONObject obj = new JSONObject( tokener );
		statuses = obj.getJSONArray( "statuses");

	}
	
	@Test
	public void testSimpleParse() throws TwitterException, JSONException
	{
		Status status = TwitterObjectFactory.createStatus( statuses.get(0).toString());
		writer.write(status);
		model.write( System.out, "TURTLE" );
	}
	
}
