README.txt for Tomcat7MavenPluginTester
=======================================

This project is only to test the properties of Maven's tomcat7-maven-plugin (http://tomcat.apache.org/maven-plugin-2.0-SNAPSHOT/tomcat7-maven-plugin/) and see how it works in a Vaadin (https://vaadin.com/home) environment.

How to Run
----------

1) download project
2) download maven if necessary
3) setup maven to work at the command line
4) cd to the project folder (the one with the pom.xml file in it)
5) type "mvn clean install tomcat7:run"
6) open a browser and type in the URL http://localhost:9090/test-app
