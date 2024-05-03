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

### H2 Database Console
The H2 database console is available at the following URL:

```http://localhost:8080/api/h2-console```


## Caveats
1. Username and passwords are left plain because this is a demo application.
2. We are storing the medication image directly on our local file system storage because we needed to make the app as self-enclosing as possible for this demo, to reduce external dependency. Ideally, we would store the url in our db and then the actual image blob on a blob storage like AWS S3 or Google Cloud Storage.
3. We're using an in-memory H2 database for this demo. Ideally, we would use a more robust database like PostgreSQL or MySQL.

