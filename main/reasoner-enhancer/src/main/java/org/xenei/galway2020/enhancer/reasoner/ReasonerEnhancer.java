package org.xenei.galway2020.enhancer.reasoner;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Enumeration;

import org.apache.commons.configuration.Configuration;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.reasoner.Reasoner;
import org.apache.jena.reasoner.ReasonerRegistry;
import org.apache.jena.riot.Lang;
import org.apache.jena.riot.RDFLanguages;
import org.apache.jena.riot.RiotException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xenei.galway2020.Enhancer;

/**
 * An enhancer that performs reasoning across the model.
 *
 */
public class ReasonerEnhancer implements Enhancer {

	private final Reasoner reasoner;
	private final Model tbox;
	private static final Logger LOG = LoggerFactory
			.getLogger(ReasonerEnhancer.class);

	/**
	 * Constructor
	 * 
	 * The configuration file may specify a 'reasoner' attribute with one of the
	 * following values. (Case does not matter.)
	 * <ul>
	 * <li>OWLMicro</li>
	 * <li>OWLMini - Default</li>
	 * <li>OWL</li>
	 * <li>RDFS</li>
	 * <li>RDFSSimple</li>
	 * <li>Transitive</li>
	 * <ul>
	 * 
	 * Reads the vocabularies in the vocab properties of the Common project.
	 * 
	 * @param cfg
	 *            The configuration file.
	 * @throws URISyntaxException
	 *             if there are issues locating the vocab properties
	 * @throws IOException
	 *             If there are issues reading the vocab properties.
	 */
	public ReasonerEnhancer(Configuration cfg) throws URISyntaxException,
			IOException {
		tbox = buildTBox();
		String rName = cfg.getString("reasoner", "OWLMICRO").toUpperCase();
		if ("OWLMICRO".equals(rName)) {
			reasoner = ReasonerRegistry.getOWLMicroReasoner();
		} else if ("OWLMINI".equals(rName)) {
			reasoner = ReasonerRegistry.getOWLMiniReasoner();
		} else if ("OWL".equals(rName)) {
			reasoner = ReasonerRegistry.getOWLReasoner();
		} else if ("RDFS".equals(rName)) {
			reasoner = ReasonerRegistry.getRDFSReasoner();
		} else if ("RDFSSIMPLE".equals(rName)) {
			reasoner = ReasonerRegistry.getRDFSSimpleReasoner();
		} else if ("TRANSITIVE".equals(rName)) {
			reasoner = ReasonerRegistry.getTransitiveReasoner();
		} else {
			LOG.warn(String
					.format("Reasoner %s was not found.  Using OwlMini instead.",
							rName));
			reasoner = ReasonerRegistry.getOWLMiniReasoner();
		}
	}

	private Model buildTBox() throws URISyntaxException, IOException {
		Model m = ModelFactory.createDefaultModel();
		Enumeration<URL> en = Thread.currentThread().getContextClassLoader()
				.getResources("vocabs");
		if (en.hasMoreElements()) {
			URL dirURL = en.nextElement();
			File dir = new File(dirURL.toURI());

			for (File f : dir.listFiles()) {

				String[] parts = f.getName().split("\\.");

				Lang l = RDFLanguages.fileExtToLang(parts[parts.length - 1]);
				if (l != null) {
					try {
						m.read(f.getAbsolutePath(), l.getName());
					} catch (RiotException e) {
						LOG.error(e.getMessage(), e);
					}
				}
			}
		}
		return m;
	}

	@Override
	public Model apply(Model t) {
		return ModelFactory.createInfModel(reasoner, tbox, t);
	}

}
