package org.xenei.galway2020.sink.fuseki;

import java.io.IOException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.jena.atlas.web.auth.HttpAuthenticator;
import org.apache.jena.graph.Graph;
import org.apache.jena.riot.web.HttpOp;
import org.apache.jena.riot.web.HttpResponseHandler;
import org.apache.jena.web.DatasetGraphAccessorHTTP;

public class DatasetGraphAccessorImpl extends DatasetGraphAccessorHTTP
{
	 private HttpAuthenticator                authenticator ;
	 private HttpResponse			response;
	 private HttpResponseHandler 	capturingHandler = new CapturingHandler();
	 
	public DatasetGraphAccessorImpl(String remote,
			HttpAuthenticator authenticator) {
		super(remote, authenticator);
		this.authenticator = authenticator;
	}
	
	public HttpResponse getResponse() {
		return response;
	}

	@Override
	protected void doPost(String url, Graph data) {
		HttpResponseHandler handler = new HttpResponseHandler(){

			@Override
			public void handle(String baseIRI, HttpResponse response)
					throws IOException {
				// TODO Auto-generated method stub
				
			}};
        HttpEntity entity = graphToHttpEntity(data) ;
        HttpOp.execHttpPost(url, entity, null, capturingHandler, null, null, authenticator);
    }

	public class CapturingHandler implements HttpResponseHandler {
		@Override
		public void handle(String baseIRI, HttpResponse response)
				throws IOException {
			DatasetGraphAccessorImpl.this.response = response;
		}
	}
}
