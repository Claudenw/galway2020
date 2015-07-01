# Notes

## Requirements

* Simple Performance Metrics:
    - Number of followers vs time (vs competitors)
    - Number of mentions (@ and #) vs time (vs competitors)
    - Number of people engaged (based on a specific role, e.g., 1 mention in the last month) vs time (vs competitors)
    - Tweet post/reach vs time (vs competitors)
    - Geographic density of mentions/tweets
* Campaign Enablers:
    - Top 2...100...250 most engaged users
    - Top 100 most influential users (based on their number of followers) engaged (based on retweets of @galway2020 or #galway2020)
    - Top 100 most active retweeters

## Useful links

### Spring Framework

- [http://steveliles.github.io/setting_up_embedded_jetty_8_and_spring_mvc_with_maven.html](http://steveliles.github.io/setting_up_embedded_jetty_8_and_spring_mvc_with_maven.html)
- [https://github.com/steveliles/jetty-embedded-spring-mvc](https://github.com/steveliles/jetty-embedded-spring-mvc)
- [http://www.luckyryan.com/2013/02/06/setup-a-simple-spring-mvc-site-with-maven/](http://www.luckyryan.com/2013/02/06/setup-a-simple-spring-mvc-site-with-maven/)

### Design

- [view-source:http://getbootstrap.com/examples/dashboard/](view-source:http://getbootstrap.com/examples/dashboard/)

### Serial Charts

- [http://www.amcharts.com/demos/](http://www.amcharts.com/demos/)
- [http://www.amcharts.com/demos/line-chart-with-scroll-and-zoom/#theme-light](http://www.amcharts.com/demos/line-chart-with-scroll-and-zoom/#theme-light)
- [http://www.amcharts.com/demos/reversed-value-axis/#](http://www.amcharts.com/demos/reversed-value-axis/#)

### Tables

- [https://datatables.net/examples/styling/bootstrap.html](https://datatables.net/examples/styling/bootstrap.html)
- [http://datatables.net/examples/basic_init/table_sorting.html](http://datatables.net/examples/basic_init/table_sorting.html)

### Tables (other options)

- [http://drvic10k.github.io/bootstrap-sortable/](http://drvic10k.github.io/bootstrap-sortable/)
- [http://github.hubspot.com/sortable/](http://github.hubspot.com/sortable/)
- [http://wenzhixin.net.cn/p/bootstrap-table/docs/examples.html](http://wenzhixin.net.cn/p/bootstrap-table/docs/examples.html)

### Highlight active page

- [http://www.paulund.co.uk/use-jquery-to-highlight-active-menu-item](http://www.paulund.co.uk/use-jquery-to-highlight-active-menu-item)

### Install Java and Tomcat

- [http://tecadmin.net/steps-to-install-java-on-centos-5-6-or-rhel-5-6/](http://tecadmin.net/steps-to-install-java-on-centos-5-6-or-rhel-5-6/)
- [https://alextheedom.wordpress.com/cloud/amazon-free-usage-tier-installing-tomcat-7-on-an-ec2-linux-instance/](https://alextheedom.wordpress.com/cloud/amazon-free-usage-tier-installing-tomcat-7-on-an-ec2-linux-instance/)
- [http://www.bogotobogo.com/Java/tutorial/Tomcat7_Ubuntu_14_Install_On_Amazon_EC2.php](http://www.bogotobogo.com/Java/tutorial/Tomcat7_Ubuntu_14_Install_On_Amazon_EC2.php)


## Deployment

I fired up a EC2 instance in Amazon WS.

- Public IP: 52.25.50.67
- Instance ID: i-08504dfe
- Instance Type: t2.micro
- Public DNS: ec2-52-25-50-67.us-west-2.compute.amazonaws.com

### EC2 instance configuration

To SSH the machine:
```
# Change permissions over the pem file
$ chmod 400 ~/Downloads/galway2020ec2.pem

# Access the machine
$ ssh -i ~/Downloads/galway2020ec2.pem ubuntu@52.25.50.67
```

What I did can we summarized in the following set of command lines:
```
# Change role to root and go to /opt
$ sudo -i
$ cd /opt

# Download and uncompress Java 1.7
$ wget --no-cookies --no-check-certificate --header "Cookie: gpw_e24=http%3A%2F%2Fwww.oracle.com%2F; oraclelicense=accept-securebackup-cookie" "http://download.oracle.com/otn-pub/java/jdk/7u79-b15/jdk-7u79-linux-x64.tar.gz"
$ tar xzf jdk-7u79-linux-x64.tar.gz

# Install Java
$ update-alternatives --install /usr/bin/java java /opt/jdk1.7.0_79/bin/java 1

# Check that the installation was correct
$ java -version

# Set JAVA_HOME
$ echo $JAVA_HOME # should be empty
$ export JAVA_HOME=/opt/jdk1.7.0_79/
$ echo $JAVA_HOME # should return a path
$ export PATH=$JAVA_HOME/bin:$PATH

# Download and uncompress Tomcat 7
$ wget http://ftp.cixug.es/apache/tomcat/tomcat-7/v7.0.62/bin/apache-tomcat-7.0.62.tar.gz
$ tar xzvf apache-tomcat-7.0.62.tar.gz

# Start Tomcat 7
$ cd apache-tomcat-7.0.62/
$ sh bin/catalina.sh run

# Change permissions for the webapps folder
$ chmod 777 webapps/
```

### Software

I developed a Spring MVC application using the following JavaScript libraries:

- [http://www.amcharts.com/](http://www.amcharts.com/): For the charts of time series.
- [http://leafletjs.com/](http://leafletjs.com/): For the heat map.
- [http://d3js.org/](http://d3js.org/): For the force graph.
- [http://datatables.net/](http://datatables.net/): For the tables.

To compile locally:
```
mvn clean package
```

To test locally:
```
mvn jetty:run
```

then open [http://localhost:8080/g2020-dashboard-1.0/](http://localhost:8080/g2020-dashboard-1.0/) in the browser.

To publish in EC2 (the folder webapps should have the permissions open for all users):
```
scp -i ~/Downloads/galway2020ec2.pem target/g2020-dashboard-1.0.war ubuntu@52.25.50.67:/opt/apache-tomcat-7.0.62/webapps
```

### Others

Possible script to start Tomcat
```
!/bin/sh
# Tomcat init script for Linux.
#
# chkconfig: 2345 96 14
# description: The Apache Tomcat servlet/JSP container.
JAVA_HOME=/usr/java/jdk1.7.0_40/
CATALINA_HOME=/usr/share/apache-tomcat-7.0.42
export JAVA_HOME CATALINA_HOME
exec $CATALINA_HOME/bin/catalina.sh $*
```
