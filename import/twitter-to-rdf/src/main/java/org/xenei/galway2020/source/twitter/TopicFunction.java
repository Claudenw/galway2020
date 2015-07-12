package org.xenei.galway2020.source.twitter;

import java.util.function.Function;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Convert a string to a topic for a search.
 *
 */
public class TopicFunction implements Function<String, String> {
	private final static Logger LOG = LoggerFactory
			.getLogger(TopicFunction.class);

	@Override
	public String apply(String t) {

		LOG.debug("Getting topic {}", t);
		return String.format("#%s", t);

	}

}
