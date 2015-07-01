<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<html>
<head>
    <title>HashTags Graph</title>
    <style>
        .main { padding: 0px; }
        .node {
          stroke-width: 1.5px;
        }
        .node text {
          pointer-events: none;
          font: 10px sans-serif;
        }
        .link {
          stroke: #ccc;
        }
    </style>
</head>
<body>
    <script src="${pageContext.request.contextPath}/js/d3/d3.min.js" charset="utf-8"></script>
    <script>
    //Constants for the SVG
    var width = 1280,
        height = 580;

    //Set up the colour scale
    var color = d3.scale.category20();

    //Set up the force layout
    var force = d3.layout.force()
        .charge(-120)
        .linkDistance(80)
        .size([width, height]);

    //Append a SVG to the body of the html page. Assign this SVG as an object to svg
    var svg = d3.select("body").append("svg")
        //.attr("width", width)
        //.attr("height", height);
        .attr({
            "width": "100%",
            "height": "100%"
          })
        .attr("viewBox", "0 0 " + width + " " + height )
        .attr("preserveAspectRatio", "xMidYMid meet")
        //.call(d3.behavior.zoom().on("zoom", redraw));

    //Read the data from the mis element
    //var mis = document.getElementById('mis').innerHTML;
    //graph = JSON.parse(mis);

    d3.json("${pageContext.request.contextPath}/data/hash_tags_d3.json", function(error, graph) {
        if (error) throw error;

        //Creates the graph data structure out of the json data
        force
    	.nodes(graph.nodes)
    	.links(graph.links)
    	.start();

        //Create all the line svgs but without locations yet
        var link = svg.selectAll(".link")
    	.data(graph.links)
    	.enter().append("line")
    	.attr("class", "link")
    	.style("stroke-width", function(d) { return Math.sqrt(d.value); });

        //Do the same with the circles for the nodes - no
        //Changed
        var node = svg.selectAll(".node")
    	.data(graph.nodes)
    	.enter().append("g")
    	.attr("class", "node")
    	.attr("r", 5)
    	.style("fill", function(d) { return color(d.group); })
    	.call(force.drag);

        node.append("circle")
    	.attr("r", 8)
    	.style("fill", function(d) { return color(d.group); });

        node.append("text")
    	  .attr("dx", 10)
    	  .attr("dy", ".35em")
    	  .text(function(d) { return d.name });
        //End changed

        //Now we are giving the SVGs co-ordinates - the force layout is generating the co-ordinates which this code is using to update the attributes of the SVG elements
        force.on("tick", function() {
    	link.attr("x1", function(d) { return d.source.x; })
    	    .attr("y1", function(d) { return d.source.y; })
    	    .attr("x2", function(d) { return d.target.x; })
    	    .attr("y2", function(d) { return d.target.y; });

    	//Changed
    	d3.selectAll("circle").attr("cx", function(d) { return d.x; })
    	    .attr("cy", function(d) { return d.y; });
    	d3.selectAll("text").attr("x", function(d) { return d.x; })
    	    .attr("y", function(d) { return d.y; });
    	//End Changed
        });
    });
    </script>
</body>
</html>