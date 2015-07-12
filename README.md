# Galway 2020 Capital Of Culture Bid Data System

This project comprises the systems that create the Galway 2020 data collection and presentation systems.

The components are:

* <a href='docs/readme.html'>iocs</a> - The documentation describing the system. 
* <a href='import/readme.html'>import</a> - The backend data colections system that imports data from 3rd parties into the ODP.  This is a java based platform that reads data and delivers it as RDF graphs to the ODP.
* <a href='odp/readme.html'>odp</a> - The Open Data Platform (ODP) that delivers the data for analysis.  This is an Apache Jena based RDF platform.
* <a href='permissions/readme.html'>permissions</a> - The jena-permissions implementation to secure the data in the ODP.


