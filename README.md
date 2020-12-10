# log-parser solution
This project takes a log file path as an input argument 
and parses this log file to get events entered in an HSQL database table.

<h2><a id="Usage_20"></a>Usage</h2>
<p>
<li>
To create jar file, navigate to the project folder and run :  mvn clean install
</li>
<li>
This should create a jar file in target folder.
Copy this jar file to required folder.
</li>
Run java -jar [jar file name] [filePath] to run the jar file 
<li>
This should create logs/app.log and data folder in the path where jar file is being run
</li>
<li>
data folder will contain the file based database schema containing events parsed in Event table
</li>
</p>

<H2>Considerations</H2>
<p>
<li>
Large files in GBs do not need to be loaded in memory at once. This program can handle large files
</li>
<li>
Use of multi threading while parsing rows of files via gson api 
</li>
<li>
Logging enable using logback with policies defined to roll over logs
</li>
<li>
Error, Info , Debug and Trace level log implemented
</li>
<li>
Javadocs added
</li>
</p>

<H2>Further Improvements</H2>
<li>
In Memory database (H2/HSQL DB)can be set up to test the application
</li>
<li>
Mockito can be used with Junit and test coverage can be improved.
</li>