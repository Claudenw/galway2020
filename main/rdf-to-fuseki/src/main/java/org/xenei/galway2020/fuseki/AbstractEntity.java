package org.xenei.galway2020.fuseki;

import java.io.IOException;
import java.io.OutputStream;

import org.apache.commons.lang3.StringUtils;
import org.apache.jena.rdf.model.Model;

public class AbstractEntity extends ModelEntity {

	private final String graphName;
	private final String action;
	
	public AbstractEntity(Model model, String action) {
		this(model,null, action);
	}
	
	public AbstractEntity(Model model, String graphName, String action) {
		super(model);
		if (StringUtils.isBlank(action))
		{
			throw new IllegalArgumentException( "Action may not be null or empty");
		}
		this.graphName = graphName;
		this.action = action;
	}
	
	@Override
	public final void writeTo(OutputStream outstream) throws IOException {
		outstream.write(String.format( "%s DATA %n{%n", action).getBytes());
		if (StringUtils.isNotBlank( graphName ))
		{
			outstream.write( String.format( "GRAPH <%s> { %n", graphName ).getBytes());
		}
		super.writeTo( outstream );
		if (StringUtils.isNotBlank( graphName ))
		{
			outstream.write( String.format( "%n}%n}%n").getBytes());
		}
		else
		{
			outstream.write( String.format( "%n}%n").getBytes());
		}
	}
}
