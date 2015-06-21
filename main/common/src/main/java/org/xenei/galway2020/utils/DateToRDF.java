package org.xenei.galway2020.utils;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import org.apache.jena.atlas.lib.DateTimeUtils;
import org.apache.jena.datatypes.xsd.XSDDatatype;
import org.apache.jena.rdf.model.Literal;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.rdf.model.ResourceFactory;
import org.apache.jena.vocabulary.DC_11;

public class DateToRDF {
	
	public static Literal asDateTime( Date date ) {
		Calendar cal = GregorianCalendar.getInstance();
		cal.setTime(date);
		String lex = DateTimeUtils.calendarToXSDDateTimeString(cal);
		return ResourceFactory.createTypedLiteral(lex, XSDDatatype.XSDdateTime);
	}
	
	public static void addDate( Resource resource, Date date )
	{
		resource.addLiteral(DC_11.date,  DateToRDF.asDateTime(date));
	}

}
