package org.xenei.galway2020.sink.fuseki;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import org.apache.commons.configuration.Configuration;
import org.apache.http.HttpStatus;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.jena.rdf.model.Model;
import org.xenei.galway2020.ModelSink;

public class FusekiModelSink implements ModelSink {
	
	private static final String HOST = "host";
	private static final String USER = "user";
	private static final String PWD = "pwd";
	
	//public static final String GZIP = "gzip";
	//public static final String DEFLATE = "deflate";
	
	private final CloseableHttpClient httpclient;
	private HttpClientContext ctxt = null;
	
	private Configuration cfg;
	//private String encoding;
	
	public FusekiModelSink( Configuration cfg )
	{
		this.cfg = cfg;
		//this.encoding = GZIP;
		
		if (!cfg.containsKey(HOST))
		{
			throw new IllegalArgumentException( "Configuration must have a "+HOST+" parameter");
		}
		URL url = null;
		try {
			url = new URL( cfg.getString( HOST ));
		}
		catch (MalformedURLException e )
		{
			throw new IllegalArgumentException( String.format( "Configuration option %s must contain a vailid URL: %s caused %s",
					HOST, cfg.getString( HOST ), e.getMessage()));
		}
		
		HttpClientBuilder builder = HttpClientBuilder.create();
		//builder.setContentDecoderRegistry(getContentDecoderMap());
		httpclient = builder.build();
		
		if (cfg.containsKey( USER ))
		{
			ctxt = new HttpClientContext();
			
			CredentialsProvider provider = new BasicCredentialsProvider();
			provider.setCredentials(
					new AuthScope( url.getHost(), url.getPort(), AuthScope.ANY_REALM ),
					new UsernamePasswordCredentials( cfg.getString( USER ), cfg.getString( PWD, ""))
					);
			ctxt.setCredentialsProvider(provider);
		
		}
	}

	@Override
	public boolean insert(Model model, String graphName) throws IOException {
		return execute( new InsertEntity( model, graphName ) );
	}
	

	private boolean execute( AbstractEntity entity ) throws IOException
	{
		
		HttpPost post = new HttpPost( entity.modUrl(cfg.getString(HOST)) );
		post.setEntity( entity );
		CloseableHttpResponse response = httpclient.execute(post, ctxt );
		return response.getStatusLine().getStatusCode() >= HttpStatus.SC_OK &&
				response.getStatusLine().getStatusCode() < HttpStatus.SC_MULTIPLE_CHOICES;	
	}
	
	@Override
	public boolean delete(Model model, String graphName) throws IOException {
		return execute( new DeleteEntity( model, graphName ));
	}
	
}
