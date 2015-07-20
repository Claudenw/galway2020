package org.xenei.galway2020.sink.fuseki;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import org.apache.commons.configuration.Configuration;
import org.apache.http.HttpHost;
import org.apache.http.HttpStatus;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.AuthCache;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.impl.auth.BasicScheme;
import org.apache.http.impl.client.BasicAuthCache;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.jena.rdf.model.Model;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xenei.galway2020.ModelSink;

/**
 * A sink that writes to a fuseki or other SPARQL server.
 */
public class FusekiModelSink implements ModelSink {
	private static final Logger LOG = LoggerFactory.getLogger(FusekiModelSink.class);
	private static final String HOST = "host";
	private static final String USER = "user";
	private static final String PWD = "pwd";

	private final CloseableHttpClient httpclient;
	private final HttpClientContext ctxt;
	private final HttpHost target;

	private Configuration cfg;

	public FusekiModelSink(Configuration cfg) {
		this.cfg = cfg;
		ctxt = HttpClientContext.create();
		httpclient = HttpClientBuilder.create().build();

		if (!cfg.containsKey(HOST)) {
			throw new IllegalArgumentException("Configuration must have a "
					+ HOST + " parameter");
		}
		URL url = null;
		try {
			url = new URL(cfg.getString(HOST));
		} catch (MalformedURLException e) {
			throw new IllegalArgumentException(
					String.format(
							"Configuration option %s must contain a vailid URL: %s caused %s",
							HOST, cfg.getString(HOST), e.getMessage()));
		}
		target = new HttpHost(url.getHost(), url.getPort());

		if (cfg.containsKey(USER)) {

			CredentialsProvider provider = new BasicCredentialsProvider();

			provider.setCredentials(
					new AuthScope(target),
					new UsernamePasswordCredentials(cfg.getString(USER), cfg
							.getString(PWD, "")));

			// Create AuthCache instance
			AuthCache authCache = new BasicAuthCache();
			// Generate BASIC scheme object and add it to the local
			// auth cache
			BasicScheme basicAuth = new BasicScheme();
			authCache.put(target, basicAuth);

			// Add AuthCache to the execution context

			ctxt.setCredentialsProvider(provider);
			ctxt.setAuthCache(authCache);

		}

	}

	@Override
	public boolean insert(Model model, String graphName) throws IOException {
		return execute(new InsertEntity(model, graphName));
	}

	private boolean execute(AbstractEntity entity) throws IOException {
		LOG.debug( "Executing Fuseki Model Sink");
		HttpPost post = new HttpPost(entity.modUrl(cfg.getString(HOST)));
		post.setEntity(entity);
		CloseableHttpResponse response = httpclient.execute(target, post, ctxt);
		LOG.debug( "Sink returned {}", response.getStatusLine().toString());
		return response.getStatusLine().getStatusCode() >= HttpStatus.SC_OK
				&& response.getStatusLine().getStatusCode() < HttpStatus.SC_MULTIPLE_CHOICES;
	}

	@Override
	public boolean delete(Model model, String graphName) throws IOException {
		return execute(new DeleteEntity(model, graphName));
	}

}
