/** Standalone configuration for qonsole on index page */

define( [], function() {
  return {
    prefixes: {
      "rdf":      "http://www.w3.org/1999/02/22-rdf-syntax-ns#",
      "rdfs":     "http://www.w3.org/2000/01/rdf-schema#",
      "owl":      "http://www.w3.org/2002/07/owl#",
      "xsd":      "http://www.w3.org/2001/XMLSchema#",
      "g2020":    "http://data.galway2020.ie/ns/twitter#",
      "foaf":     "http://xmlns.com/foaf/0.1/",
      "dc11":     "http://purl.org/dc/elements/1.1/",
      "geo":      "http://www.w3.org/2003/01/geo/wgs84_pos#"
    },
    queries: [
      { "name": "Hashtags",
        "query": "SELECT ?hashtagId ?hashTag\n" +
                 "WHERE {\n" +
                 "  ?hashtagId rdf:type g2020:Hashtag;\n" +
                 "    rdfs:label ?hashTag .\n" +
                 "}\n",
        "prefixes": ["g2020", "rdfs", "rdf"]
      },
      { "name": "Selection of triples",
        "query": "SELECT ?subject ?predicate ?object\nWHERE {\n" +
                 "  ?subject ?predicate ?object\n}\n" +
                 "LIMIT 25"
      },
      { "name": "Properties of Tweets",
        "query": "SELECT distinct ?p ?label\n" +
                 "WHERE {\n" +
                 " ?tweet a g2020:Tweet ;\n" +
                 "    ?p [] .\n" +
                 " 	?p rdfs:label ?label\n" +
                 "}",
       "prefixes": ["g2020", "rdfs", "foaf", "owl", "rdf"]
      },
      { "name": "Properties of Users",
        "query": "SELECT distinct ?p ?label\n" +
                 "WHERE {\n" +
                 " ?tweet a g2020:User ;\n" +
                 "    ?p [] .\n" +
                 " 	?p rdfs:label ?label\n" +
                 "}",
       "prefixes": ["g2020", "rdfs", "foaf", "owl", "rdf"]
      }
    ]
  };
} );
