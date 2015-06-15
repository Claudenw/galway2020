package org.xenei.galway2020.sink.file;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.commons.configuration.Configuration;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.riot.Lang;
import org.apache.jena.riot.RDFLanguages;
import org.apache.log4j.Logger;
import org.xenei.galway2020.ModelSink;

public class FileModelSink implements ModelSink {

	private static final String FILE_DIR = "dir";

	private final File dir;
	private final Lang lang = RDFLanguages.TURTLE;
	private final String prefix = "fms";
	private final Logger LOG = Logger.getLogger(FileModelSink.class);

	public FileModelSink(Configuration cfg) {
		dir = new File(cfg.getString(FILE_DIR));
	}

	public boolean insert(Model model) throws IOException {
		File outFile = File.createTempFile(prefix, "."
				+ lang.getFileExtensions().get(0), dir);
		model.write(new FileOutputStream(outFile), lang.getName());
		return true;
	}

	private File getFile(String graphName) {
		return new File(dir, String.format("%s.%s", graphName, lang
				.getFileExtensions().get(0)));
	}

	public boolean insert(Model model, String graphName) throws IOException {
		if (graphName == null)
		{
			return insert(model);
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
		return true;
	}

	public boolean delete(Model model) throws IOException {
		throw new UnsupportedOperationException(
				"Delete without graphName is not supported");
	}

	public boolean delete(Model model, String graphName) throws IOException {
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
		return true;
	}

}
