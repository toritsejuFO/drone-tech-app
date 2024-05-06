# Drone Tech App

Implementation of a Drone Tech App for the Drone Tech Inc. company. Built using SpringBoot 3.2.0.

The application provides REST API endpoints to manage drones and the medications to be loaded on these drones.

## Required Software
* Java Development Kit (JDK) 17 or higher
* Apache Maven (Optional, provided in the project root directory as a wrapper)

## Build, Run, & Test
### Building the Project
The project uses Maven as a build tool. It already contains ./mvn wrapper script, so there's no need to install mvn locally).

To build the project execute the following command:

```./mvnw clean package```

### Running the Project
To run the project execute the following command:

```./mvnw spring-boot:run```

To run prebuilt package use the following command:

```java -jar target/app-0.0.1-SNAPSHOT.jar```

### Running the Tests
To run the tests execute the following command:

```./mvnw test```

### Accessing the Application
The application will be accessible at the following URL:

```http://localhost:8080/api/v1/```

The application uses Basic Authentication.

Default username: `user` <br>
Default password: `password`

### API Documentation
The API documentation is available at the following URL:

```http://localhost:8080/api/v1/swagger-ui.html```

You can also find the collection on postman here

[<img src="https://run.pstmn.io/button.svg" alt="Run In Postman" style="width: 128px; height: 32px;">](https://app.getpostman.com/run-collection/5534062-e64272c0-906e-4f23-8ea5-fd0c7f7490e3?action=collection%2Ffork&source=rip_markdown&collection-url=entityId%3D5534062-e64272c0-906e-4f23-8ea5-fd0c7f7490e3%26entityType%3Dcollection%26workspaceId%3Db35fd6d8-723a-4b38-ac07-0892d6d92e2d#?env%5BDroneTech%5D=W3sia2V5IjoiYmFzZVVybCIsInZhbHVlIjoibG9jYWxob3N0OjgwODAiLCJlbmFibGVkIjp0cnVlLCJ0eXBlIjoiZGVmYXVsdCIsInNlc3Npb25WYWx1ZSI6ImxvY2FsaG9zdDo4MDgwIiwic2Vzc2lvbkluZGV4IjowfSx7ImtleSI6InVzZXIiLCJ2YWx1ZSI6InVzZXIiLCJlbmFibGVkIjp0cnVlLCJ0eXBlIjoiZGVmYXVsdCIsInNlc3Npb25WYWx1ZSI6InVzZXIiLCJzZXNzaW9uSW5kZXgiOjF9LHsia2V5IjoicGFzcyIsInZhbHVlIjoicGFzc3dvcmQiLCJlbmFibGVkIjp0cnVlLCJ0eXBlIjoiZGVmYXVsdCIsInNlc3Npb25WYWx1ZSI6InBhc3N3b3JkIiwic2Vzc2lvbkluZGV4IjoyfV0=)

### H2 Database Console
The H2 database console is available at the following URL:

```http://localhost:8080/api/h2-console```


## Caveats
1. Username and passwords are left plain because this is a demo application.
2. We are storing the medication image directly on our local file system storage because we needed to make the app as self-enclosing as possible for this demo, to reduce external dependency. Ideally, we would store the url in our db and then the actual image blob on a blob storage like AWS S3 or Google Cloud Storage.
3. We're using an in-memory H2 database for this demo. Ideally, we would use a more robust database like PostgreSQL or MySQL.

