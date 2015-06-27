package org.xenei.galway2020.enhancer.uri;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Collections;
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

	private Level logLevel;

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

		logLevel = Level.toLevel(config.getString("log-level", "INFO")
				.toUpperCase());
		parseRewriterList(config.subset("rewriter"));
		parseIgnoreList(config.subset("ignore"));
		SelectBuilder sb = loadClassList(config.getStringArray("class"));
		sb = loadPropertyList(sb, config.getStringArray("property"));
		if (sb == null) {
			throw new IllegalArgumentException(
					"Configuration must have at least one class or property defined");
		}
		uriQuery = sb.setDistinct(true).build();
		factory = new URLHandlerFactory(this);
	}

	@Override
	public void shutdown()
	{
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

	private SelectBuilder loadClassList(String[] classes) {
		if (classes == null) {
			return null;
		}
		Var url = Var.alloc("url");
		SelectBuilder builder = new SelectBuilder().addVar(url);
		boolean first = true;
		for (String clz : classes) {
			SelectBuilder sub = new SelectBuilder().addWhere(url, RDF.type,
					ResourceFactory.createResource(clz));
			if (first) {
				builder.addSubQuery(sub.addVar(url));
				first = false;
			} else {
				builder.addUnion(sub);
			}
		}
		return builder;
	}

	private SelectBuilder loadPropertyList(SelectBuilder sb, String[] properties) {
		if (properties == null) {
			return null;
		}
		Var url = Var.alloc("url");
		SelectBuilder builder = null;
		boolean first;
		if (sb == null) {
			builder = new SelectBuilder().addVar(url);
			first = true;
		} else {
			builder = sb;
			first = false;
		}

		for (String property : properties) {
			SelectBuilder sub = new SelectBuilder().addWhere("?x",
					ResourceFactory.createProperty(property), url);
			if (first) {
				builder.addSubQuery(sub.addVar(url));
				first = false;
			} else {
				builder.addUnion(sub);
			}
		}
		return builder;
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
			return false;
		}

		URI uri;
		try {
			uri = new URI(node.toString());
		} catch (URISyntaxException e) {
			LOG.error("{} is not a valid URI: {}", node.toString(),
					e.toString());
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
		return retval.equals(uriStr) ? null : retval;
	}

	@Override
	public Model apply(Model model) {
		Model updates = ModelFactory.createDefaultModel();
		try (QueryExecution qexec = QueryExecutionFactory.create(uriQuery,
				model)) {
			ResultSet results = qexec.execSelect();
			for (; results.hasNext();) {
				QuerySolution soln = results.nextSolution();
				Resource r = soln.getResource("url"); // Get a result variable -
														// must be a resource
				try {
					factory.getHandler(r, updates).handle();
				} catch (RuntimeException e) {
					LOG.error(e.getMessage(), e);
				}
			}
		} catch (RuntimeException e) {
			LOG.error(e.getMessage(), e);
		}
		return model.add(updates);

	}

}
