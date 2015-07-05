package org.xenei.galway2020.utils;

import org.xenei.galway2020.vocab.Galway2020;

public class NSTools {
	private static String prefix = Galway2020.NS.substring(0, Galway2020.NS.length()-1);
	
	public static String createURL( String suffix ) {
		return String.format( "%s/%s",prefix,suffix);
	}
}
