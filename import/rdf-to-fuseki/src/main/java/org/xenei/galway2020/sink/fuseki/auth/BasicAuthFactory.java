package org.xenei.galway2020.sink.fuseki.auth;

import org.apache.commons.configuration.Configuration;
import org.apache.commons.lang3.StringUtils;
import org.apache.jena.atlas.web.auth.HttpAuthenticator;
import org.apache.jena.atlas.web.auth.SimpleAuthenticator;

public class BasicAuthFactory implements AuthFactory {

	@Override
	public HttpAuthenticator create(Configuration cfg) {
		String userName = cfg.getString( "username" );
		String password = cfg.getString( "password" );
		if (StringUtils.isBlank( userName ))
		{
			throw new IllegalArgumentException( "Username may not be null or empty");
		}
		if (password == null)
		{
			throw new IllegalArgumentException( "Password may not be null");			
		}
		return new SimpleAuthenticator( userName, password.toCharArray());
	}

}
