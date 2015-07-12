package org.xenei.galway2020.utils;

import org.apache.jena.rdf.model.Resource;
import org.apache.jena.vocabulary.OWL;

public class OwlFuncs {
	
	public static void makeSameAs( Resource r1, Resource r2)
	{
		r1.addProperty( OWL.sameAs,  r2 );
		r2.addProperty( OWL.sameAs, r1 );
	}

}
