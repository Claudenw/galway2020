package org.xenei.galway2020.sink.fuseki;

import java.io.IOException;
import java.io.OutputStream;
import java.util.zip.DeflaterOutputStream;
import java.util.zip.GZIPOutputStream;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.Header;
import org.apache.http.message.BasicHeader;
import org.apache.jena.rdf.model.Model;

public class AbstractEntity extends ModelEntity {

	private final String graphName;
	private final String action;
	private String encoding;
	
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
		this.encoding = null;
	}
	
	public String modUrl(String url)
	{
		return url;
	}

	
	public String getGraphName() {
		return graphName;
	}
	
	public Header getContentType() {
		return new BasicHeader( "Content-Type", "application/sparql-update; charset=UTF-8");
	}
	
	public void setContentEncoding(String encoding)
	{
		this.encoding = encoding;
	}
	
//	public Header getContentEncoding() {
//		if (encoding != null) {
//			if (encoding.equals("gzip"))
//			{
//				return new BasicHeader( "Content-Encoding", FusekiModelSink.GZIP);
//			}
//			if (encoding.equals("deflate"))
//			{
//				return new BasicHeader( "Content-Encoding", FusekiModelSink.DEFLATE);
//			}
//		}
//		return null;
//	}
	
//	private OutputStream modifyStream(OutputStream outstream) throws IOException {
//		if (encoding != null)
//		{
//			if (encoding.equals(FusekiModelSink.GZIP))
//			{
//				return new GZIPOutputStream( outstream, true );
//			}
//			if (encoding.equals(FusekiModelSink.DEFLATE))
//			{
//				return new DeflaterOutputStream( outstream, true );
//			}
//		}
//		return outstream;
//	}
	
	@Override
	public final void writeTo(OutputStream outstream) throws IOException {
		//OutputStream ostream = modifyStream( outstream );
		
		
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
		outstream.flush();
	}
}
