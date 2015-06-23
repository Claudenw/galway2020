import json
from pprint import pprint

"""
Convert a resultSet JSON into a Graph JSON valid input for D3
"""

hash_data = {}
hash_nodes = []
hash_links = []

# Parse the Jena JSON and extract the vocabulary
with open('hash_tags_jena.json') as json_data:
    data = json.load(json_data)
    #print(data)
    #print data["results"]["bindings"]
    vocabulary = []
    for binding in data["results"]["bindings"]:
	"""
	A binding from Jena
	{
	    "label1": { "type": "literal" , "value": "Galway" } ,
	    "label2": { "type": "literal" , "value": "IBackGalway" } ,
	    "N": { "datatype": "http://www.w3.org/2001/XMLSchema#integer" , "type": "typed-literal" , "value": "100" }
	}
	"""
	#print binding
	label1 = binding["label1"]["value"]
	if not label1 in vocabulary:
	    vocabulary.append(label1)
	label2 = binding["label2"]["value"]
	if not label2 in vocabulary:
	    vocabulary.append(label2)

# Convert vocabulary into node objects of the final JSON
for word in vocabulary:
    node = {}
    """
    A node in the graph
    {"name":"Myriel","group":1}
    """
    node["name"] = word
    node["group"] = 1
    hash_nodes.append(node)

# Convert the bindings into links of the final JSON
for binding in data["results"]["bindings"]:
    link = {}
    """
    A link in the graph
    {"source":1,"target":0,"value":1}
    """
    link["source"] = vocabulary.index(binding["label1"]["value"])
    link["target"] = vocabulary.index(binding["label2"]["value"])
    link["value"] = binding["N"]["value"]
    hash_links.append(link)

# Append nodes and links into one JSON object
hash_data["nodes"] = hash_nodes
hash_data["links"] = hash_links
#output_data = json.dumps(hash_data)
#print output_data

# Write JSON into a file
with open('hash_tags_d3.json', 'w') as outfile:
    json.dump(hash_data, outfile, indent=4)

## END
