# Galway 2020 Capital Of Culture Bid Data System - Data Import

The data import system is a java application based on the <a href="https://jena.apache.org>Apache Jena</a> rdf graph store.
The base application is called the _processor_ and comprises 3 basic components:

* A _source_ that creates an RDF graph (Called a Model).
* Zero or more _enhancers_ that add extra data to the model.
* one or two _sinks_ that write the graph somewhere.  The processor requires a "sink" be defined and allows for an optional _sink_ implementation that operates as a "retryQueue".  the retryQueue is where the model will be written if the sink fails for some reason.

## Processor ##
The processor reads causes the _source_ to produce an iterator of models.  It then iterates over the models passeing each of them through any defined _enhancers_ before sending the model to the _sink_.  If the _sink_ fails the the model is sent to the "retryQueue" _sink_.  If no "retryQueue" is defined a message is sent to the log indicating that the model was not saved and all data lost.

## Source ##
The source reads data from a 3rd party and transforms it into a model.  As of this writing (12-July-2014) we have 2 sources: Twitter and the file system.

The Twitter source reads tweets posted about specific topics and adds them to the system.  It also handles retrieving data about specific users and generating a model about them.

The file source reads files from the file system and is designed to read the files written by the file sink (see below).

## Enhancer ##
The enhancer adds extra data to the graph.   As of this writing (12-July-2014) we have 2 enhancers: URI and reasoner. 

The URI enhancer checks known URI in the model and verifies that they are reachable.  It adds extra data about the endpoint -- mime type, data size, etc.

The reasoner enhancer reads applies an RDF semantic reasoner to the model to produce extra data.  It is designed to apply any of the Jena based reasoners.  Our current implementation applies the <a href="https://jena.apache.org/documentation/inference/#owl">OWL-Mini</a> version.

## Sink ##
The sink writes the final model to a datastore.   As of this writing (12-July-2014) we have 2 sinks: <a href="http://jena.apache.org/documentation/serving_data/">Fuseki</a> and file. 

The Fuseki sink write the data to a fuseki (or other updatable SPARQL) endpoint.

The file sink writes to the local file system and was originally intended for testing but has found production use as the retryQueue sink.

