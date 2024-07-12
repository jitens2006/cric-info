# REST CRUD API for Player Information, 

* The API is a sample API for managing Player related Information. 

# API Endpoints
The app defines following CRUD APIs.

   POST      /api/v1/players        		     ``Add PlayerInfo``
      
   GET       /api/v1/players        		     ``Fetch all Player details``
   
   GET       /api/v1/players/{id}   			   `` Fetch all Player details ``
   
   PUT       /api/v1/players/{id}   		       ``Update Player details``
   
   DELETE    /api/v1/secure/players/{id}         `` Secure endpoint with Basic Auth  ``
   
   
## How to Run

This application is packaged as a jar which has Tomcat 8 embedded. 

* Clone this repository 
* Make sure you are using JDK 1.8 and Gradle 6.x
* Use in-memory H2 Data.
* You can build the project and run the tests by command ```gradle build ```
* Run the project using ``` java -jar build/libs/cric-info-0.0.1-SNAPSHOT.jar ```

### Get information about system health, configurations, etc.

http://localhost:8090/actuator/health

http://localhost:8090/actuator/info

### To view Swagger API Docs
Run the server and browse to  ``` localhost:8090/swagger-ui.html ```

### To view H2 in-memory database
To view and query the database you can browse to ``` http://localhost:8090/h2-console ```

### Features
* Logging events using Spring AOP during CRUD operation for Auditing. 


* Events saved into APPLICATION_EVENT table in-memory DB table.


* Spring actuator endpoints for checking health and info endpoints. 


* Rest Endpoint(/api/v1/secure/players/{id}) secured with basic Auth


* Exception Handling using @AdviceController


* Hibernate Validator for Validating JSON


* Logback Configuration to Console






