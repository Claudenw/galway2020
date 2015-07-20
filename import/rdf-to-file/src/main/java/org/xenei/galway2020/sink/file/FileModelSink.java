package org.xenei.galway2020.sink.file;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.commons.configuration.Configuration;
import org.apache.commons.lang3.StringUtils;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.riot.Lang;
import org.apache.jena.riot.RDFLanguages;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xenei.galway2020.ModelSink;

public class FileModelSink implements ModelSink {

	private static final String FILE_DIR = "dir";

	private final File dir;
	private final Lang lang = RDFLanguages.TURTLE;
	private final String prefix = "fms";
	private static final Logger LOG = LoggerFactory.getLogger(FileModelSink.class);

	public FileModelSink(Configuration cfg) {
		dir = new File(cfg.getString(FILE_DIR));
		if ( ! dir.exists())
		{
			throw new IllegalArgumentException( String.format( "Directory %s does not exist", dir));
		}
		if (! dir.canWrite())
		{
			throw new IllegalArgumentException( String.format( "Directory %s can not be written", dir));
		}				
	}

	private boolean insertUnnamed(Model model) throws IOException {
		File outFile = File.createTempFile(prefix, "."
				+ lang.getFileExtensions().get(0), dir);
		model.write(new FileOutputStream(outFile), lang.getName());
		LOG.debug( "Model written to "+outFile);
		return true;
	}

	private File getFile(String graphName) {
		return new File(dir, String.format("%s.%s", graphName, lang
				.getFileExtensions().get(0)));
	}

	public boolean insert(Model model, String graphName) throws IOException {
		LOG.debug( "Insert into '{}'", graphName );
		if (StringUtils.isBlank( graphName))
		{
			return insertUnnamed( model );
		}
		File outFile = getFile(graphName);
		
		if (outFile.exists()) {
			Model m = ModelFactory.createDefaultModel();
			m.read(new FileInputStream(outFile), outFile.getName() + "#",
					lang.getName());
			m.add(model);
			m.write(new FileOutputStream(outFile), lang.getName());
		} else {
			model.write(new FileOutputStream(outFile), lang.getName());
		}
		LOG.debug( "Model written to "+outFile);
		return true;
	}

	public boolean delete(Model model, String graphName) throws IOException {
		LOG.debug( "Deleting from '{}'", graphName );
		if (StringUtils.isBlank( graphName ))
		{
			throw new IOException(
					"Delete without graphName is not supported");
		}
		File outFile = getFile(graphName);
		if (outFile.exists()) {
			Model m = ModelFactory.createDefaultModel();
			m.read(new FileInputStream(outFile), outFile.getName() + "#",
					lang.getName());
			m.remove(model);
			if (m.isEmpty()) {
				outFile.delete();
			} else {
				m.write(new FileOutputStream(outFile), lang.getName());
			}
		}
		LOG.debug( "Model deleted from "+outFile);
		return true;
	}

}
