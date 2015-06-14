package org.xenei.galway2020.fuseki;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.util.Map;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.message.BasicHeader;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.riot.Lang;
import org.apache.jena.riot.RDFLanguages;
import org.apache.log4j.Logger;

public class ModelEntity implements HttpEntity {
	
	private Model model;
	private Lang lang = RDFLanguages.TURTLE;
	private final Logger LOG = Logger.getLogger(ModelEntity.class);
	private Thread contentThread = null;
	
	public ModelEntity( Model model )
	{
		this.model = model;
	}

	public boolean isRepeatable() {
		return true;
	}

	public boolean isChunked() {
		return false;
	}

	public long getContentLength() {
		return -1; // don't know size.
	}

	public Header getContentType() {
		return new BasicHeader( "Content-Type", lang.getHeaderString());
	}

	public Header getContentEncoding() {
		return new BasicHeader( "Content-Encoding", "UTF-8");
	}

	public InputStream getContent() throws IOException,
			UnsupportedOperationException {
		if (contentThread != null && contentThread.isAlive())
		{
			throw new IllegalArgumentException("Only one input stream at a time");
		}
		// take the copy of the stream and re-write it to an InputStream
		PipedInputStream in = new PipedInputStream();
		final PipedOutputStream out = new PipedOutputStream(in);
		contentThread = new Thread(
		        new Runnable() {
		            public void run () {
		                try {
		                    // write the original OutputStream to the PipedOutputStream
		                    writeTo(out);
		                }
		                catch (IOException e) {
		                    LOG.error( "Error in input stream", e );
		                }
		            }
		        }
		);
		contentThread.start();
		return in;
	}

	public void writeTo(OutputStream outstream) throws IOException {
		Map<String,String> map = model.getNsPrefixMap();
		for (String pfx : map.keySet())
		{
			model.removeNsPrefix(pfx);
		}		
		model.write(outstream, lang.getName());
		model.setNsPrefixes(map);
	}

	public boolean isStreaming() {
		return true;
	}

	public void consumeContent() throws IOException {
		if (contentThread != null && contentThread.isAlive())
		{
			contentThread.interrupt();
		}
		this.contentThread = null;
	}

}
