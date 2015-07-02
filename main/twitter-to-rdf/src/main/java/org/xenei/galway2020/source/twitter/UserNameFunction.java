package org.xenei.galway2020.source.twitter;

import java.util.function.Function;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Class that adds "@" to strings to make them user ids.
 *
 */
public class UserNameFunction implements Function<String, String> {
	private final static Logger LOG = LoggerFactory
			.getLogger(UserNameFunction.class);

	@Override
	public String apply(String t) {

		LOG.debug("Getting username {}", t);
		return String.format("@%s", t);

	}

}
