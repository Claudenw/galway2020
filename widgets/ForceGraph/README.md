# Notes

The general idea is to load the output of Jena Fuseki into a D3 force graph. This force graph will display the connection between hashtags appearing together in a tweet.

* There is a special input format for D3. To convert from one format to the other there is a Python script `resultSet2Graph.py`.
```
python resultSet2Graph.py
```
will take the `hash_tags_jena.json` file and generate the `hash_tags_d3.json` file.

* There is an HTML file that loads the `hash_tags_d3.json` file and display a force graph using D3 library.
```
python -m SimpleHTTPServer 8000
```
will start a server in the folder and you can go to the URL [http://localhost:8000/test_page.html](http://localhost:8000/test_page.html) in your browser to visualize the graph.

