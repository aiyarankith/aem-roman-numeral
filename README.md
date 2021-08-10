# Roman Numeral

Roman Numeral is an application to convert an integer to a Roman numeral.

## Getting Started

Follow these steps to run this application on your local machine for development and testing purposes.

### Prerequisites

You will need the following installed before running the application

```bash
Java
Maven
```

### Running the application

#####1.Docker compose
The docker-compose is binding to localhost, so just running below command and opening the browser would be suffice. 

```bash
docker run -p 127.0.0.1:8080:8080/tcp <container_name>/<image_id>
```

#####2. Manually running the application

```bash
cd roman-numeral
```

Install the application

```bash
mvn clean install
```

Start the application

```bash
mvn spring-boot:run
```

You should see the Spring Boot startup logs in your terminal


## Testing the application

Unit tests are leveraging the Spring Boot Test framework as well as JUnit 5. The goal of creating these tests is for a 
comprehensive end-to-end coverage of the application, web service, and business logic. Both positive and negative 
scenarios are included for assertion that existing functionality will not be broken with future development. Coding was 
performed in a TDD manner to ensure feature requirements are met while implementing.

To run all unit tests, use the command

```bash
mvn clean test
```

To debug the application, uncomment lines 69-74 in the pom.xml file and run

```bash
mvn spring-boot:run
```

The application will pause and listen for debugger localhost:5005 before resuming the startup. This allows you to inspect the code during application runtime in case of any issues.

## Packaging layout

<pre>
roman-numeral/
  │   └── src/
  │       ├── main/
  │       │   ├── java/
  │       │   │   └── com/adobe/aem/
  │       │   │       ├── controller/
  │       │   │       │   └── AEMController.java
  │       │   │       ├── domain/
  │       │   │       │   └── Numeral.java
  │       │   │       ├── exception/
  │       │   │       │   ├── ApiError.java
  │       │   │       │   ├── InvalidRequestException.java
  │       │   │       │   └── RestExceptionHandler.java
  │       │   │       ├── service/
  │       │   │       │   └── AEMService.java
  │       │   │       ├── util/
  │       │   │       │   └── RequestValidator.java
  │       │   │       └── AEMApplication.java
  │       │   └── resources
  │       │       └── application.properties
  │       └── test/
  │           └── java/
  │               └── com/adobe/aem/
  │                   ├── controller/
  │                   │   └── AEMControllerTest.java
  │                   ├── service/
  │                   │   └── AEMlServiceTest.java
  │                   └── AEMApplicationTest.java
  ├── pom.xml
  └── README.md
</pre>

## Usage

REST APIs available to call

### Roman numeral conversion feature

```bash
GET http://localhost:8080/romannumeral?query={integer}
```

```bash
GET http://localhost:8080/romannumeral?min={integer}&max={integer}
```

### Monitoring/Metrics

```bash
GET http://localhost:8080/actuator/health #service health
GET http://localhost:8080/actuator/metrics #available metrics
GET http://localhost:8080/actuator/metrics/{metric.name} #metric info
```

### Shutdown

```bash
POST http://localhost:8080/actuator/shutdown
```


## Technologies

[Maven](https://maven.apache.org/) - dependency management

[Spring Boot](https://spring.io/projects/spring-boot) - web application framework

[Spring Boot Actuator](https://docs.spring.io/spring-boot/docs/current/reference/html/production-ready-features.html) - monitoring

