package org.xenei.galway2020.sink.fuseki.auth;

import org.apache.commons.configuration.Configuration;
import org.apache.jena.atlas.web.auth.HttpAuthenticator;

public interface AuthFactory {
	
	HttpAuthenticator create( Configuration cfg );
}
