# Hospital Management System (HMS)

This project is a **Hospital Management System** built using **Spring Boot**. It provides RESTful APIs to manage hospital operations such as doctors, patients, and visits.

## Table of Contents
- [Features](#features)
- [Technologies Used](#technologies-used)
- [Prerequisites](#prerequisites)
- [Installation and Setup](#installation-and-setup)
- [Configuration](#configuration)
- [Running the Application](#running-the-application)
- [API Documentation](#api-documentation)

## Features
- **Doctor Management**: Create, Read, Update, and Delete doctor records.
- **Patient Management**: Create, Read, Update, and Delete patient records.
- **Visit Management**: Manage patient visits to doctors.
- **Data Validation**: Input validation using Spring Validation.
- **Exception Handling**: Global exception handling for better error responses.
- **Logging**: Application logging configured.

## Technologies Used
- **Java 17**
- **Spring Boot 3.2.2**
- **Spring Data JPA**: For database interactions.
- **MySQL**: Relational database management system.
- **MapStruct**: For efficient Entity-DTO mapping.
- **Lombok**: To reduce boilerplate code.
- **SpringDoc OpenAPI (Swagger UI)**: For API documentation.
- **Maven**: Dependency management and build tool.

## Prerequisites
Before running the application, ensure you have the following installed:
- [Java Development Kit (JDK) 17](https://www.oracle.com/java/technologies/javase/jdk17-archive-downloads.html)
- [Apache Maven](https://maven.apache.org/)
- [MySQL Server](https://dev.mysql.com/downloads/mysql/)

## Installation and Setup

1.  **Clone the Repository**
    ```bash
    git clone <repository_url>
    cd Hospital-Management-System
    ```

2.  **Database Setup**
    Ensure your MySQL server is running. The application is configured to create the database `hms` if it doesn't exist.

## Configuration
The application configuration can be found in `src/main/resources/application.properties`.

Default Configuration:
- **Server Port**: `8080`
- **Database URL**: `jdbc:mysql://localhost:3306/`
- **Database Username**: `username`
- **Database Password**: `password`

If your MySQL credentials differ, update the `application.properties` file:

```properties
spring.datasource.username=your_username
spring.datasource.password=your_password
```

## Running the Application

You can run the application using Maven or directly via the main class.

**Using Maven:**
```bash
./mvnw spring-boot:run
```

**Using the JAR file:**
First, build the project:
```bash
./mvnw clean package
```
Then run the jar:
```bash
java -jar target/hms-0.0.1-SNAPSHOT.jar
```

The application will start on `http://localhost:8083`.

## API Documentation

The project includes Swagger UI for interactive API documentation. Once the application is running, you can access it at:

**Swagger UI URL**: [http://localhost:8083/swagger-ui/index.html](http://localhost:8083/swagger-ui/index.html)

### Main Endpoints
- **Doctors**: `/doctors`
- **Patients**: `/patients`
- **Visits**: `/visits`

## Project Structure
```
src/main/java/com/codegnan/
├── controller/       # REST Controllers
├── dto/              # Data Transfer Objects
├── entity/           # JPA Entities
├── exceptions/       # Custom Exception Handling
├── mapper/           # MapStruct Mappers
├── repo/             # JPA Repositories
└── service/          # Business Logic
```
