# H320VectorClock

The VectorClockLogger is the interface module to use the library. Examples are in the Sample/Server and Sample/Client
modules. This networking example is a simple echo server example. To run these modules:
- Start the server.
- Start the client - you can start any number of clients.
- Begin typing the message in the client command line. 

The ComonsLogging configuration in the Sample package is named ```commons-logging.properties```. The Client will have to write this file on their own. Note that the client may use another implementation by assigning their logging implmentation to the variable "org.apache.commons.logging.Log". For example, for the sample project - the commons logging property file looks like this:

```
org.apache.commons.logging.Log=org.apache.commons.logging.impl.Log4JLogger
log4j.configuration=log4j.properties
```

The sample is using Log4J as the logging implementation. Configuration for this file is found in the "log4j.properties" file. 

One thing to note about the log4j.properties file is that you can write the log to file by specifying the path in the variable "log4j.appender.file.File". There is an example shown in that file. 
