package org.xenei.galway2020.sink.fuseki;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.zip.DeflaterOutputStream;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

import org.apache.commons.configuration.Configuration;
import org.apache.http.HttpStatus;
import org.apache.http.StatusLine;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.Credentials;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.entity.DeflateInputStream;
import org.apache.http.client.entity.InputStreamFactory;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
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
	
//	private Map<String,InputStreamFactory> getContentDecoderMap()
//	{
//		Map<String,InputStreamFactory> retval = new HashMap<String,InputStreamFactory>();
//		retval.put( GZIP, new InputStreamFactory(){
//
//			@Override
//			public InputStream create(InputStream instream) throws IOException {
//				return new GZIPInputStream( instream );
//			}});
//		retval.put( DEFLATE, new InputStreamFactory(){
//
//			@Override
//			public InputStream create(InputStream instream) throws IOException {
//				return new DeflateInputStream( instream );
//			}});
//		return retval;
//	}

	public boolean insert(Model model) throws IOException {
		return execute( new InsertEntity( model ));
	}

	public boolean insert(Model model, String graphName) throws IOException {
		return execute( new InsertEntity( model, graphName ));
	}
	

	private boolean execute( AbstractEntity entity ) throws IOException
	{
		
		HttpPost post = new HttpPost( entity.modUrl(cfg.getString(HOST)) );
		post.setEntity( entity );
		CloseableHttpResponse response = httpclient.execute(post, ctxt );
//		if (response.getStatusLine().getStatusCode() == HttpStatus.SC_UNSUPPORTED_MEDIA_TYPE )
//		{
//			if (encoding.equals("gzip"))
//			{
//				encoding = DEFLATE;
//				entity.setContentEncoding( encoding );
//				return execute( entity );
//			}
//			if (encoding.equals(DEFLATE))
//			{
//				encoding=null;
//				entity.setContentEncoding( encoding );
//				return execute( entity );
//			}
//			return false;
//		}
		return response.getStatusLine().getStatusCode() >= HttpStatus.SC_OK &&
				response.getStatusLine().getStatusCode() < HttpStatus.SC_MULTIPLE_CHOICES;	
	}
	
	public boolean delete(Model model) throws IOException {
		return execute( new DeleteEntity( model ));
	}

	
	public boolean delete(Model model, String graphName) throws IOException {
		return execute( new DeleteEntity( model, graphName ));
	}
	

//	private OutputStream modifyStream(OutputStream outstream) throws IOException {
//		if (encoding != null)
//		{
//			if (encoding.equals(FusekiModelSink.GZIP))
//			{
//				return new GZIPOutputStream( outstream );
//			}
//			if (encoding.equals(FusekiModelSink.DEFLATE))
//			{
//				return new DeflaterOutputStream( outstream );
//			}
//		}
//		return outstream;
//	}
}
