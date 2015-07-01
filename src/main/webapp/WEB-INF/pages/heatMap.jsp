<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<html>
<head>
    <title>Galway2020 Heat Map</title>
    <style>
        .main { padding: 15px 0px 0px 0px; }
        #map { width: 100%; height: 600px; }
    </style>
    <link type="text/css" href="${pageContext.request.contextPath}/js/leaflet-0.7.3/leaflet.css" rel="stylesheet" />
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/leaflet-0.7.3/leaflet.js"></script>
</head>
<body>
    <div id="map"></div>

    <script type="text/javascript" src="${pageContext.request.contextPath}/js/leaflet-0.7.3/leaflet-heat.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/leaflet-0.7.3/realworld.10000.js"></script>
    <script>
        var map = L.map('map').setView([53.2706, -9.0567], 11); // lat, long, zoom
        var tiles = L.tileLayer(
            //'http://{s}.tiles.mapbox.com/v3/{id}/{z}/{x}/{y}.png', {
            'http://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png', {
            attribution: '<a href="https://galway2020.ie">Galway2020</a>',
            //id: 'examples.map-20v6611k' // needed by mapbox
        }).addTo(map);
        var heat = L.heatLayer(addressPoints, {blur: 20, radius: 15}).addTo(map);
    </script>
</body>
</html>