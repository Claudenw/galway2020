package org.xenei.galway2020.fuseki;

import java.io.IOException;

import org.apache.commons.configuration.Configuration;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.jena.rdf.model.Model;
import org.xenei.galway2020.ModelSink;

public class FusekiModelSink implements ModelSink {
	
	private static final String HOST_UPDATE = "host.update";
	private static final String HOST_QUERY = "host.query";
	private final CloseableHttpClient httpclient;
	
	private Configuration cfg;
	
	public FusekiModelSink( Configuration cfg )
	{
		this.cfg = cfg;
		httpclient = HttpClients.createDefault();
	}

	public boolean insert(Model model) throws IOException {
		return insert( new InsertEntity( model ));
	}

	public boolean insert(Model model, String graphName) throws IOException {
		return insert( new InsertEntity( model, graphName ));
	}
	

	private boolean insert( InsertEntity entity ) throws IOException
	{
		HttpPost post = getPost( HOST_UPDATE );
		post.setEntity( entity );
		CloseableHttpResponse response = httpclient.execute(post);
		return response.getStatusLine().getStatusCode() == 200;
	}
	
	public boolean delete(Model model) throws IOException {
		return delete( new DeleteEntity( model, null ));
	}

	
	public boolean delete(Model model, String graphName) throws IOException {
		return delete( new DeleteEntity( model, graphName ));
	}
	
	private boolean delete( DeleteEntity entity ) throws IOException
	{
		HttpPost post = getPost( HOST_UPDATE );
		post.setEntity( entity );
		CloseableHttpResponse response = httpclient.execute(post);
		return response.getStatusLine().getStatusCode() == 200;
	}
	
	private HttpPost getPost( String key ) {
		
		HttpPost post = new HttpPost( cfg.getString( key ));

		//TODO add configuration of post here.
		//TODO add authentication here.
		return post;
	}

}
