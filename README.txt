README.txt for Tomcat7MavenPluginTester
=======================================

Last Updated: 08/31/2012

This project is only to test the properties of Maven's tomcat7-maven-plugin 
(http://tomcat.apache.org/maven-plugin-2.0-SNAPSHOT/tomcat7-maven-plugin/) and 
see how it works in a Vaadin (https://vaadin.com/home) environment.

How to Run
----------

1) download project
2) download maven if necessary
3) setup maven to work at the command line
4) cd to the project folder (the one with the pom.xml file in it)
5) type "mvn clean install tomcat7:run"
6) open a browser and type in the URL http://localhost:9090/test-app

Things to Notice
----------------

This is a Vaadin webapp, so it has a production mode in web.xml.  What this is 
for is to allow a debugger to launch when "?debug" is added to the URL, so in 
this case, "http://localhost:9090/test-app?debug".  The default production mode 
is false, that is, unless you specifically set it, you can launch the debugger.

To set production mode to true (no debugger), you add this to web.xml:

	<context-param>
		<description>Vaadin production mode</description>
		<param-name>productionMode</param-name>
		<param-value>true</param-value>
	</context-param>

Now web.xml is a web resource and I added Maven resource filtering to this file 
so that I could turn production mode on and off using a properties file.  
web.xml now looks like this...

	<context-param>
		<description>Vaadin production mode</description>
		<param-name>productionMode</param-name>
		<param-value>${production.mode}</param-value>
	</context-param>
	
...and I have a file called test-app.properties that looks like this:

	# Test-app properties file
	#
	production.mode=true

The test-app project is setup to do web resource filtering as you can see in 
the pom.xml file.  If you execute "mvn clean install tomcat7:run" you will be 
able to launch the debugger with "http://localhost:9090/test-app?debug".  This 
means the Vaadin web app is NOT is production mode!  This is because the Maven 
Tomcat server is using the project web.xml (src/main/webapp/WEB-INF/web.xml) 
and NOT the file in the war file 
(target/tomcat7-maven-plugin-test-0.0.1-SNAPSHOT/WEB-INF/web.xml).  Tomcat uses 
the web.xml that still has the placeholder in it and since it is not "true" or 
"false", it defaults to false.  To satisfy yourself that this is true, change 
the production mode to "true" in the project web.xml file 
(src/main/webapp/WEB-INF/web.xml) and you will not be able to get the web app 
debugger to launch.

This demonstrates what I believe is a bug in tomcat7-maven-plug: it does not 
use the configuration files in the war file or directory.  And if it does not, 
and you use Maven filtering, how can you test your war file?

The Fix (update)
----------------

Okay, it's not a but per se.  What is happening is that Maven Filtering makes
its changes in a directory that Maven Tomcat doesn't look at.  But you can tell
Tomcat where the directory with the filtered files is.

The default directory for Maven filtering is ${project.build.directory}/
${project.build.finalName} so you can the Tomcat plugin to look at this with:

<project>
  ...
  <build>
    ...
    <plugins>
      ...
      <plugin>
        <groupId>org.apache.tomcat.maven</groupId>
        <artifactId>tomcat7-maven-plugin</artifactId>
        <version>${tomcat7MavenPlugin}</version>

        <configuration>
          ...

          <!-- Have Tomcat look in the file filtering folder -->
          <warSourceDirectory>
            ${project.build.directory}/${project.build.finalName}
          </warSourceDirectory>
        </configuration>
        ...
        
The default POM file has this source directory commented out so that you can
see the behavior without it.  Uncommenting the lines fix the problems.

