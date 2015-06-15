package org.xenei.galway2020.twitter.writer;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import org.apache.jena.atlas.lib.DateTimeUtils;
import org.apache.jena.datatypes.xsd.XSDDatatype;
import org.apache.jena.rdf.model.Literal;
import org.apache.jena.rdf.model.ResourceFactory;

public class DateToRDF {
	
	public static Literal write( Date date ) {
		Calendar cal = GregorianCalendar.getInstance();
		cal.setTime(date);
		String lex = DateTimeUtils.calendarToXSDDateTimeString(cal);
		return ResourceFactory.createTypedLiteral(lex, XSDDatatype.XSDdateTime);
	}

}
