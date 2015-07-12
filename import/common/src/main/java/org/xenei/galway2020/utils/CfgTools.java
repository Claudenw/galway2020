package org.xenei.galway2020.utils;

import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Set;

import org.apache.commons.configuration.Configuration;

public class CfgTools {
	/**
	 * Get the set of prefixes for the configuration.
	 * @param cfg The configuration to parse.
	 * @return the set of prefixes from the configuration in order they appeared.
	 */
	public static Set<String> getPrefix(Configuration cfg) {
		Set<String> result = new LinkedHashSet<String>();
		Iterator<String> iter = cfg.getKeys();
		while (iter.hasNext()) {
			result.add(iter.next().split("\\.")[0]);
		}
		return result;
	}

}
