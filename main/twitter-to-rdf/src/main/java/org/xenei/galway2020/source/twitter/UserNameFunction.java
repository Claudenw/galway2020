package org.xenei.galway2020.source.twitter;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;

import org.apache.jena.util.iterator.ExtendedIterator;
import org.apache.jena.util.iterator.WrappedIterator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UserNameFunction implements Function<String, String> {
	private final static Logger LOG = LoggerFactory
			.getLogger(UserNameFunction.class);

	@Override
	public String apply(String t) {

		LOG.debug("Getting username {}", t);
		return String.format("@%s", t);

	}

}
