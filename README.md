# Project Management API

## Overview
This project is a simple Project Management REST API built with Spring Boot.  
It allows you to manage projects and their associated tasks, supporting features such as task creation, assignment, priority, due dates, and status tracking.  
The application uses an H2 in-memory database for easy setup and testing, and exposes endpoints for creating and retrieving projects and tasks.


## Configuration
- **src/main/resources/application.properties**: Contains configuration properties for the Spring Boot application, including database connection settings and JPA configurations.


## Build and Run

### : Run with Gradle (Locally)

1. **Build the application:**
   ```
   ./gradlew build
   ```

2. **Run the application:**
   ```
   ./gradlew bootRun


## Accessing the Application
Once the application is running, you can access it at [http://localhost:8080](http://localhost:8080).

## Accessing the H2 Database Console
Visit [http://localhost:8080/h2-console](http://localhost:8080/h2-console)  
- **JDBC URL:** `jdbc:h2:mem:projectdb`  
- **Username:** `sa`  
- **Password:** *(leave blank)*

---