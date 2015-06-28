package org.xenei.galway2020.enhancer.uri;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import org.xenei.galway2020.Enhancer;
import org.xenei.galway2020.enhancer.uri.handlers.URLHandlerFactory;
import org.xenei.galway2020.utils.CfgTools;
import org.apache.log4j.Level;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xenei.uri.PatternReplacer;
import org.xenei.uri.URIMatcher;
import org.xenei.uri.URIRewriter;
import org.apache.commons.configuration.Configuration;
import org.apache.jena.arq.querybuilder.SelectBuilder;
import org.apache.jena.graph.Node;
import org.apache.jena.graph.Triple;
import org.apache.jena.query.Query;
import org.apache.jena.query.QueryExecution;
import org.apache.jena.query.QueryExecutionFactory;
import org.apache.jena.query.QuerySolution;
import org.apache.jena.query.ResultSet;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.rdf.model.RDFNode;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.rdf.model.ResourceFactory;
import org.apache.jena.sparql.core.Var;
import org.apache.jena.vocabulary.RDF;

public class URIEnhancer implements Enhancer {
	private static Logger LOG = LoggerFactory.getLogger(URIEnhancer.class);

	private Configuration config;

	/**
	 * A list of rewriters that describe how to rewrite a URI into another URI
	 * for further checking.
	 */
	private final List<URIRewriter> rewriteList = new ArrayList<URIRewriter>();
	/**
	 * A list of matchers that will determine which URIs will not be processed.
	 */
	private final List<URIMatcher> ignoreList = new ArrayList<URIMatcher>();

	private final Query uriQuery;

	private final URLHandlerFactory factory;

	public URIEnhancer(Configuration config) throws IOException,
			URISyntaxException {
		this.config = config;
		parseRewriterList(config.subset("rewriter"));
		parseIgnoreList(config.subset("ignore"));
		List<Triple> lst = new ArrayList<Triple>();
		lst = loadClassList(lst, config.getStringArray("class"));
		lst = loadPropertyList(lst, config.getStringArray("property"));
		if (lst.isEmpty()) {
			throw new IllegalArgumentException(
					"Configuration must have at least one class or property defined");
		}
		SelectBuilder sb = new SelectBuilder().addVar("?url");
		if (lst.size() == 1) {
			sb.addWhere(lst.get(0));
		} else {
			for (Triple t : lst) {
				SelectBuilder subSelect = new SelectBuilder().addWhere(t);
				sb.addUnion(subSelect);
			}
		}
		uriQuery = sb.setDistinct(true).build();
		factory = new URLHandlerFactory(this);
	}

	@Override
	public void shutdown() {
		rewriteList.clear();
		ignoreList.clear();
	}

	private void parseIgnoreList(Configuration cfg) {
		for (String ignoreKey : CfgTools.getPrefix(cfg)) {
			ignoreList
					.add(parseMatcher(new URIMatcher(), cfg.subset(ignoreKey)));
		}
	}

	private URIMatcher parseMatcher(URIMatcher matcher, Configuration cfg) {
		if (cfg.containsKey("scheme")) {
			matcher.setScheme(cfg.getString("scheme"));
		}
		if (cfg.containsKey("host")) {
			matcher.setHost(cfg.getString("host"));
		}
		if (cfg.containsKey("port")) {
			matcher.setPort(cfg.getInt("port"));
		}
		if (cfg.containsKey("fragment")) {
			matcher.setFragment(cfg.getString("fragment"));
		}
		if (cfg.containsKey("path")) {
			matcher.setPath(cfg.getString("path"));
		}
		return matcher;
	}

	private URIRewriter parseRewriter(Configuration cfg) {
		PatternReplacer patternReplacer = new PatternReplacer(
				cfg.getString("pattern"));
		patternReplacer = (PatternReplacer) parseMatcher(patternReplacer, cfg);
		return new URIRewriter(patternReplacer);
	}

	private void parseRewriterList(Configuration cfg) {
		for (String rewriteKey : CfgTools.getPrefix(cfg)) {
			rewriteList.add(parseRewriter(cfg.subset(rewriteKey)));
		}
	}

	private List<Triple> loadClassList(List<Triple> lst, String[] classes) {
		if (classes == null) {
			return lst;
		}
		Var url = Var.alloc("url");
		for (String clz : classes) {
			lst.add(new Triple(url.asNode(), RDF.type.asNode(), ResourceFactory
					.createResource(clz).asNode()));
		}
		return lst;
	}

	private List<Triple> loadPropertyList(List<Triple> lst, String[] properties) {
		if (properties == null) {
			return lst;
		}
		Var url = Var.alloc("url");
		for (String property : properties) {
			lst.add(new Triple(Node.ANY, ResourceFactory.createProperty(
					property).asNode(), url.asNode()));
		}
		return lst;
	}

	public void addToIgnoreList(Resource r) {
		if (r.isURIResource()) {
			try {
				URIMatcher matcher = new URIMatcher(new URI(r.getURI()));
				ignoreList.add(matcher);
				LOG.debug("Added {} to ignore list", matcher);
			} catch (URISyntaxException e) {
				LOG.error("{} is not a valid URI: {}", r.getURI(), e.toString());
			}
		}
	}

	/**
	 * Searches the ignoreList.
	 *
	 * @param node
	 * @return true if the node is a URI node and matches a URI in the
	 *         ignoreList.
	 */
	public boolean isIgnored(RDFNode node) {
		if (!node.isURIResource()) {
			LOG.debug("Ignoring {}", node);
			return false;
		}
		return isIgnored(node.toString());
	}

	/**
	 * Searches the ignoreList.
	 *
	 * @param uriStr
	 *            The URI as a string.
	 * @return true if the uriStr and matches a URI in the ignoreList.
	 */
	public boolean isIgnored(String uriStr) {
		URI uri;
		try {
			uri = new URI(uriStr);
		} catch (URISyntaxException e) {
			LOG.error("{} is not a valid URI: {}", uriStr, e.toString());
			return false;
		}

		for (URIMatcher ignored : ignoreList) {
			if (ignored.matches(uri)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Searches the ignoreList.
	 *
	 * @param uri
	 *            The uri to check
	 * @return true if the uri matches a URI in the ignoreList.
	 */
	public boolean isIgnored(URI uri) {

		for (URIMatcher ignored : ignoreList) {
			if (ignored.matches(uri)) {
				LOG.debug("Ignoring {}", uri);
				return true;
			}
		}
		return false;
	}

	/**
	 * The log level
	 *
	 * @return
	 */
	public Level getLogLevel() {
		Object retval = config.getProperty("log-level");
		return retval == null ? Level.INFO : (Level) retval;
	}

	/**
	 * Rewrite the input string (as a uri) into a new uri string.
	 *
	 * will iterate through the rewriteList in order. If the rewriter matches
	 * the URI the URI is edited as per the rewriter instructions and the new
	 * URI is used for subsequent rewriter matching. This allows a primitive
	 * chaining of uri edits.
	 *
	 * If there is an error (e.g. uriStr is not a URI or one of the rewriters
	 * failes, null is returned.
	 *
	 * If the string is not rewritten, null is returned.
	 *
	 * @param uriStr
	 *            the string to rewrite.
	 * @return a new URI string or null.
	 */
	public String rewrite(String uriStr) {
		URI uri;
		try {
			uri = new URI(uriStr);
		} catch (URISyntaxException e) {
			LOG.error("{} is not a valid URI: {}", uriStr, e.toString());
			return null;
		}

		for (URIRewriter rewriter : rewriteList) {
			try {
				uri = rewriter.rewrite(uri);
			} catch (URISyntaxException e) {
				LOG.error("Error rewriting {} : {}", uri, e.toString());
				e.printStackTrace();
			}
		}
		String retval = uri.toString();
		if (retval.equals(uriStr) || isIgnored(uri)) {
			return null;
		}
		return retval;
	}

	@Override
	public Model apply(Model model) {
		Model updates = ModelFactory.createDefaultModel();
		LOG.debug("Query {}", uriQuery);
		try (QueryExecution qexec = QueryExecutionFactory.create(uriQuery,
				model)) {
			ResultSet results = qexec.execSelect();
			for (; results.hasNext();) {
				QuerySolution soln = results.nextSolution();
				Resource r = soln.getResource("url"); // Get a result variable -
														// must be a resource
				LOG.debug("processing {}", r);
				if (!isIgnored(r)) {
					try {
						factory.getHandler(r, updates).handle();
					} catch (RuntimeException e) {
						LOG.error(e.getMessage(), e);
					}
				}
			}
		} catch (RuntimeException e) {
			LOG.error(e.getMessage(), e);
		}
		return model.add(updates);

	}

}
