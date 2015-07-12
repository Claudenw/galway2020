<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<html>
<head>
    <title>Engaged Users vs Time</title>
    <style>
        .main { padding-top: 5px; }
        #chartdiv {
        	width	: 100%;
        	height	: 500px;
        }
    </style>
</head>
<body>
    <!-- AmCharts includes -->
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/amcharts/amcharts.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/amcharts/serial.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/amcharts/themes/light.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/amcharts/plugins/dataloader/dataloader.min.js"></script>

    <!-- Export plugin includes and styles -->
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/amcharts/plugins/export/export.min.js"></script>
    <link type="text/css" href="${pageContext.request.contextPath}/js/amcharts/plugins/export/export.css" rel="stylesheet">

    <h2>Engaged Users vs. Time</h2>
    <div id="chartdiv"></div>

    <script type="text/javascript">
        var chart = AmCharts.makeChart("chartdiv", {
            "type": "serial",
            "theme": "light",
            "legend": {
                "useGraphSettings": true
            },
            "marginRight": 80,
            "autoMarginOffset": 20,
            "marginTop": 7,
            "dataLoader": {
                "url": "${pageContext.request.contextPath}/data/amchart_test/engagement_time.json",
                "showErrors": true,
                "complete": function(chart) {
                    console.log("Loading complete");
                },
                "load": function(options, chart) {
                    console.log("File loaded: ", options.url);
                },
                "error": function(options, chart) {
                    console.log("Error occurred loading file: ", options.url);
                }
            },
            "valueAxes": [{
                "axisAlpha": 0.2,
                "dashLength": 1,
                "position": "left",
                "title": "Engaged users"
            }],
            "mouseWheelZoomEnabled": true,
            "graphs": [{
                "id": "g1",
                "balloonText": "[[category]]<br/><b><span style='font-size:14px;'>Galway: [[value]]</span></b>",
                "bullet": "round",
                "bulletBorderAlpha": 1,
                "bulletColor": "#FFFFFF",
                "hideBulletsCount": 50,
                "title": "Galway",
                "valueField": "galway",
                "useLineColorForBulletBorder": true
            }, {
                "id": "g2",
                "balloonText": "[[category]]<br/><b><span style='font-size:14px;'>Dublin: [[value]]</span></b>",
                "bullet": "round",
                "bulletBorderAlpha": 1,
                "bulletColor": "#FFFFFF",
                "hideBulletsCount": 50,
                "title": "Dublin",
                "valueField": "dublin",
                "useLineColorForBulletBorder": true
            }],
            "chartScrollbar": {
                "autoGridCount": true,
                "graph": "g1",
                "scrollbarHeight": 40
            },
            "chartCursor": {

            },
            "categoryField": "date",
            "categoryAxis": {
                "parseDates": true,
                "axisColor": "#DADADA",
                "dashLength": 1,
                "minorGridEnabled": true
            },
            "export": {
                "enabled": true
            }
        });

        chart.addListener("rendered", zoomChart);
        zoomChart();

        // this method is called when chart is first initiated as we listen for "dataUpdated" event
        function zoomChart() {
            // different zoom methods can be used - zoomToIndexes, zoomToDates, zoomToCategoryValues
            chart.zoomToIndexes(chart.dataLoader.length - 40, chart.dataLoader.length - 1);
        }
    </script>
</body>
</html>