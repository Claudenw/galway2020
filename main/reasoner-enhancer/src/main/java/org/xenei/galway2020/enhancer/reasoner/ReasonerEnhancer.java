package org.xenei.galway2020.enhancer.reasoner;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Collection;
import org.apache.commons.configuration.Configuration;
import org.apache.jena.graph.Capabilities;
import org.apache.jena.graph.Graph;
import org.apache.jena.graph.GraphEventManager;
import org.apache.jena.graph.GraphStatisticsHandler;
import org.apache.jena.graph.Node;
import org.apache.jena.graph.TransactionHandler;
import org.apache.jena.graph.Triple;
import org.apache.jena.graph.compose.Difference;
import org.apache.jena.rdf.model.InfModel;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.reasoner.Reasoner;
import org.apache.jena.reasoner.ReasonerRegistry;
import org.apache.jena.riot.Lang;
import org.apache.jena.riot.RDFLanguages;
import org.apache.jena.riot.RiotException;
import org.apache.jena.shared.AddDeniedException;
import org.apache.jena.shared.DeleteDeniedException;
import org.apache.jena.shared.PrefixMapping;
import org.apache.jena.util.iterator.ExtendedIterator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xenei.galway2020.Enhancer;
import org.xenei.classpathutils.ClassPathUtils;
import org.xenei.classpathutils.filter.NotFilter;
import org.xenei.classpathutils.filter.SuffixFilter;

/**
 * An enhancer that performs reasoning across the model.
 *
 */
public class ReasonerEnhancer implements Enhancer {
	private boolean firstTime;
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
	 *            The configuration file, may be null.
	 * @throws URISyntaxException
	 *             if there are issues locating the vocab properties
	 * @throws IOException
	 *             If there are issues reading the vocab properties.
	 */
	public ReasonerEnhancer(Configuration cfg) throws URISyntaxException,
			IOException {
		firstTime = true;
		tbox = buildTBox();
		String rName = cfg == null ? "OWLMini" : cfg.getString("reasoner",
				"OWLMini");
		if ("OWLMICRO".equalsIgnoreCase(rName)) {
			reasoner = ReasonerRegistry.getOWLMicroReasoner();
		} else if ("OWLMINI".equalsIgnoreCase(rName)) {
			reasoner = ReasonerRegistry.getOWLMiniReasoner();
		} else if ("OWL".equalsIgnoreCase(rName)) {
			reasoner = ReasonerRegistry.getOWLReasoner();
		} else if ("RDFS".equalsIgnoreCase(rName)) {
			reasoner = ReasonerRegistry.getRDFSReasoner();
		} else if ("RDFSSIMPLE".equalsIgnoreCase(rName)) {
			reasoner = ReasonerRegistry.getRDFSSimpleReasoner();
		} else if ("TRANSITIVE".equalsIgnoreCase(rName)) {
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

		Collection<URL> col = ClassPathUtils.getResources("vocabs",
				new NotFilter(new SuffixFilter("/")));

		for (URL f : col) {

			LOG.debug("Reading url: {}", f);

			String[] parts = f.getPath().split("\\.");

			Lang l = RDFLanguages.fileExtToLang(parts[parts.length - 1]);
			if (l != null) {
				try {
					m.read(f.openStream(), f.toExternalForm(), l.getName());
				} catch (RiotException e) {
					LOG.error(e.getMessage(), e);
				}
			}

		}
		return m;
	}

	@Override
	public void shutdown() {
		tbox.close();
	}

	@Override
	public Model apply(Model t) {
		InfModel infModel = ModelFactory.createInfModel(reasoner, tbox, t);
		if (firstTime) {
			firstTime = false;
			return infModel;
		} else {

			Graph g = new Difference(infModel.getGraph(), new UnclosableGraph(
					tbox.getGraph()));
			return ModelFactory.createModelForGraph(g);
		}
	}

	public class UnclosableGraph implements Graph {
		private Graph graph;

		UnclosableGraph(Graph graph) {
			this.graph = graph;
		}

		@Override
		public boolean dependsOn(Graph other) {
			return graph.dependsOn(other);
		}

		@Override
		public TransactionHandler getTransactionHandler() {
			return graph.getTransactionHandler();
		}

		@Override
		public Capabilities getCapabilities() {
			return graph.getCapabilities();
		}

		@Override
		public GraphEventManager getEventManager() {
			return graph.getEventManager();
		}

		@Override
		public GraphStatisticsHandler getStatisticsHandler() {
			return graph.getStatisticsHandler();
		}

		@Override
		public PrefixMapping getPrefixMapping() {
			return graph.getPrefixMapping();
		}

		@Override
		public void add(Triple t) throws AddDeniedException {
			graph.add(t);
		}

		@Override
		public void delete(Triple t) throws DeleteDeniedException {
			graph.delete(t);
		}

		@Override
		public ExtendedIterator<Triple> find(Triple m) {
			return graph.find(m);
		}

		@Override
		public ExtendedIterator<Triple> find(Node s, Node p, Node o) {
			return graph.find(s, p, o);
		}

		@Override
		public boolean isIsomorphicWith(Graph g) {
			return graph.isIsomorphicWith(g);
		}

		@Override
		public boolean contains(Node s, Node p, Node o) {
			return graph.contains(s, p, o);
		}

		@Override
		public boolean contains(Triple t) {
			return graph.contains(t);
		}

		@Override
		public void clear() {
			graph.clear();
		}

		@Override
		public void remove(Node s, Node p, Node o) {
			graph.remove(s, p, o);
		}

		@Override
		public void close() {
			// do nothing
		}

		@Override
		public boolean isEmpty() {
			return graph.isEmpty();
		}

		@Override
		public int size() {
			return graph.size();
		}

		@Override
		public boolean isClosed() {
			return graph.isClosed();
		}

	}
}
