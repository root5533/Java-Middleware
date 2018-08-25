# Java Middleware 
This is a Java Middlware that facilating client to requst services in various servers.


## Instructions
1. compile & Run Middleware 
    ```
    javac Middleware.java
   
    java Middleware
    ```
2. compile & run Server.Here it register the server's all services in Service directory .(currently implemented two servers).
   Available services:\
	Server: addService, encryptionService\
	Server2: findGCDService, guestLuckyNumberService
	
 
    ```
    javac Server.java

    java Server
    ```
3. compile & run Client.\
   client can lookup the services.
     ```
    javac Client.java
   
    java Client lookupService <ServiceName>
    ex: java Client lookupService addService
    ```

   client can requst the services
    ```
    java Client addService 12 45
    java Client encryptionService password 
    java Client findGCDService 12 45
    java Client guestLuckyNumberService 3
    ```
