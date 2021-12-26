# Simple Issue Tracker
## Table of contents

* [General Info](#general-info)
* [Architecture](#architecture)
    * [Services](#services)
    * [Repositories](#repositories)
    * [Validators](#validators)
    * [Storage](#storage)
    * [Domain](#domain)
    * [Endpoint](#endpoint)
* [Technologies](#technologies)
* [Run Test](#run-test)
* [Setup](#setup)
    * [Requirements](#requirements)
    * [Running with Docker Compose or Java](#running-with-docker-compose-or-java)
    * [Running with Maven](#running-with-maven)

## General Info

This project is a simple issue tracker that enables developers to create developers, issues and plan issues based on
estimated point values through a Restful API and a React client UI.

## Architecture

This project uses *Spring Boot* and *Spring Framework* dependency injection (DI) and IoC capabilities to create beans
and inject objects into each other. It also uses *Spring Web MVC* to implement Restful endpoints, *Spring Data Jpa* to
create entities, repositories, *Spring Boot Test, JUnit, and Mockito framework* to run integration and unit tests. It
has a layered architecture with presentation, service, validator and persistence layers.

### Services

The code contains services with using transactions to manage, create, update, delete entities.

You can find services in the `service` sub-package.

### Repositories

The code uses Spring Data Jpa and Hibernate to manage the database and create tables, queries, ...

You can find repositories in the `repository` sub-package.

### Validators

To validate the input data from the client, there is a validator for each entity.

You can find validators in the `validator` sub-package.

### Storage

To store data, there is a default **H2 in-memory data store** to store, access data.

### Domain

This code uses Jpa `@Entity` and `@Table` to create jpa entities.

You can find domains in `domain` sub-package.

### Endpoint (Rest Controller)

To create Rest APIs, there is a rest controller that implements APIs and creates output JSON objects, and processes
input JSON objects.

You can find endpoint implementation in `controller` sub-package.

## Technologies

- Java 11
- Spring Boot 2.6.1
- Maven 3.6.0
- JUnit 5
- Mockito 4
- React.js 17
- Bootstrap 5.0

## Run Test

To run integration and unit tests, you can use:

```
mvn test
```

You can find integration and unit tests in `src/test/java` folder.

## Setup

### Requirements

- Java 11
- Maven 3.6.0
- Docker
- Docker Compose

### Running with Docker Compose or Java

To run the application you can either use docker-compose:

```
docker-compose up -d
```

You can check the running containers:

```
docker ps
```

or Java:

```
java -jar issue-tracker-1.0.0.jar --server.port=9000
```

### Running with Maven

To run the app you can use the following maven commands:

```
mvn clean test
mvn spring-boot:run
```

Once the server is running you can check the results with:

UI:

```
http://localhost:9000/
```

![Issues](https://github.com/mehdichitforoosh/simple-issue-tracker/blob/master/screenshot-1.png?raw=true)

![Planner](https://github.com/mehdichitforoosh/simple-issue-tracker/blob/master/screenshot-2.png?raw=true)

or Rest APIs:

```
POST http://localhost:9000/api/v1/developers              { "name":"Mehdi Chitforoosh" }
PUT http://localhost:9000/api/v1/developers/{id}          { "name":"Mehdi Chitforoosh", version: 0 }
GET http://localhost:9000/api/v1/developers/{id}
GET http://localhost:9000/api/v1/developers?startIndex={start}&itemsPerPage={size}&name={string}
DELETE http://localhost:9000/api/v1/developers/{id}

POST http://localhost:9000/api/v1/issues/stories          { "title":"Add a button", "description":"", "status":"NEW", "estimatedPoint": 8 }
PUT http://localhost:9000/api/v1/issues/stories/{id}      { "title":"Add a button", "description":"", "status":"NEW", "estimatedPoint": 8, version: 0 }
POST http://localhost:9000/api/v1/issues/bugs             { "title":"Add a button", "description":"", "status":"NEW", "priority": "MAJOR" }
PUT http://localhost:9000/api/v1/issues/bugs/{id}         { "title":"Add a button", "description":"", "status":"NEW", "priority": "MAJOR", version: 0 }
GET http://localhost:9000/api/v1/issues/{id}
GET http://localhost:9000/api/v1/issues?startIndex={start}&itemsPerPage={size}&title={string}
DELETE http://localhost:9000/api/v1/issues/{id}
```
