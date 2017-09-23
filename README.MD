# Feature Flag REST Api

## Premises
This is my solution for the test challenge. Altough it can seem to be complete, **it is not**, meaning that I am aware that the solution has some lacks (few unit tests, a bit chaotic in the URL of the different REST api...), but putting all togheter it could have taken ages and for what I understood the challenge is only to have a starting point for the technical interview.
I am also aware that there are tools for doing what you required from me. First of all, the very nice tool called [Togglz](https://www.togglz.org/), it could have taken much less time to me to setup it and work with it. But because all the technical challenge was centered on that, I thought it was like cheating.
# Introduction
**Feature Flag REST Api** is a RESTful Api written on top of Spring Boot. It uses its MVC libraries to setup the rest endpoints and other libraries for test. But it is agnostic from a point of view of the datasource, leaving the possibility to extend it to use any datasource: files, dbs or anything else.
For this reason, altough a greater level of abstraction was possible on the definition of the model classes and the datasource interfaces, I preferred to keep the definition in separated classes.

# Architecture
For this reasons we have 4 basic models in this project:
 * *Capabilities* provided by the *CapabilitySource*
 * *Versions* provided by the *VersionSource*
 * *Users* provided by the *ClientSource* (sorry, I should have refactored the name)
 * *Companies* provided by the *CompanySource*

The implementation of those sources leads to the way in which you retrieve your data. You can, for example, register in the Spring ApplicationContext a bean that extends one of the Sources by reading it from a database. For my implementation all those sources are based on a *ConcurrentHashMap* (to avoid a bit of boilerplate code, I abstracted a bit of it in a generic class called **GenericDatasource**, but the 4 sources implementation are in the *name.lorenzani.andrea.featurescontrol.datasource.impl* package.

Those sources are expected to be used by a REST api. Again, for being sure that I could have easily extended the data retriever API with different data sources (let's say, to have the same api available with Togglz, for example), these API are abstracted in the ***GenericDataRestApi***. I gave one implementation, the one for my abstract data sources, that is called **CustomRestApi**.
Tomorrow that I want a completely different implementation of the sources I can follow one of these two paths:
 * Create an adaptor that implements the datasources interfaces
 * Create a class that extends *GenericDataRestApi* and implement the needed methods

### First API level: Data retrievers
These API are considered the first level of the architecture. They expose the internal data. This was my choice to be sure of having the needed level of scalability: every data source is an autonomous entity, it can be scaled and replicated and redesigned and reengineered independentely. ***Of course I put all of this in one project for simplicity*** but the way in which the whole project works means that you can create another Spring Boot project and copy there one of these parts and run it alone, and everything will be nice anyway.

##### Special task: data populators
Being my datasource based on an inmemory map, I needed a way to populate my maps. This is done within the package *name.lorenzani.andrea.featurescontrol.restcontrollers.datapopulator* that contains different REST endpoint to add users, capabilities, companies...
The endpoints are invoked with the following:
* curl -X POST localhost:8080/user/add/<id>/<name>
* curl -X POST localhost:8080/capability/add/<capability>
* curl -X PUT localhost:8080/user/add/capabilities/<userid>/<list_of_capabilities>
* curl -X POST localhost:8080/company/add/<id>/<name>
* curl -X PUT localhost:8080/company/add/capabilities/<id>/<list_of_capabilities>
* curl -X POST localhost:8080/version/add/<id>
* curl -X PUT localhost:8080/version/add/capabilities/<id>/<list_of_capabilities>

There is in the root directory a script **test.sh** as example to fill a bit of information.

### Second API level: Data aggregator
This is the only group of REST endpoints that need to be exposed to the clients. This RESTful APIs are retrieving the data from the **Data Retrievers** and they compute the list of capabilities. For this reason there are 3 endpoints:
* curl -X GET localhost:8080/custom/valid/<version>
    * This tells to the client if the version of his app is valid ( *"yay"* ) or not ( *"nay"* ) 
* curl -X GET localhost:8080/custom/check/<version>/<user>
    * This tells to the client what are the features available for HIM intersected those for the given version of the app
* curl -X GET localhost:8080/custom/check/<version>/<user>/<company> 
    * As before but the intersection is also with the company
    
The result that the API is sending back is a serialized json object of the class **CheckReply**. Its body is a list of Longs. This is because we don't really need to let the client eventually understand (by controlling his network) what we are transmitting to his phone. He could eventually enable some features if he would understand the payload.

For this reason the structure of the project makes quite easy to introduce an encryption layer for the data. I didn't implemented it, anyway.

#### Special task: error handling, logging...
Altough I didn't really clean all the project (for example, I left some logging that are not really needed), I tried to put everything was needed to have a fully monitored application. Each API initiate a **RequestId** that is sent to the client in case of error (it should be logged, but I didn't put a real log structure in place).

Errors are captured and rethrown for identifying the different problems (to be honest I would have needed more cases), and a JSON is sent with error, error number and request id in case of error.

HTTP Verbs are used in the way they should be used: POST for request (creation), PUT for update, GET for getting informations. Thread safe data structures are used to handle data.

# ... and how to start
Obviously the project is a MAVEN project, so you can compile and execute it by running:

> mvn install

> java -jar target/features-control-0.0.1-SNAPSHOT.jar