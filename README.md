# Mutants
This repository contains the source code for the code challenge proposed.
The application contains the solved tasks listed in the challenge, as well as documentation on the source code
to ease the understanding of the procedure followed

## Pre-requisites
In order to run this application locally, you will require the following:
* Maven
* MySQL Database 5.7.26
* Any REST client (like Postman)

## Build
To build the application, use the following command from the CLI
```shell
mvn clean package
```
This will build the application. This can take some time, due to the required dependencies from Maven.
This command will also execute the tests, to ensure everything is ok with the application

## Run
Before running the application, be sure to set the configuration for your database installation in the `application.yml`
file. You will need to modify the `spring.datasource` properties, including `username`, `password` and `url`. Also, you can
pass the proper values via environment variables.

Use the following command to run the application setting up environment variables
```shell
mvn spring-boot:run -Dspring-boot.run.arguments=--spring.datasource.username=<user>,--spring.datasource.password=<password>,--spring.datasource.url=<databaseUrl>
```

Use the following command to run the application with the configuration from the `application.yml` file
```shell
mvn spring-boot:run
```

By default, the application will be deployed on the localhost. To check if everything is working correctly, check the health endpoint
```
GET http://localhost:8080/actuator/health
```

## Usage
To validate a dna sequence, use the endpoint
```
POST http://localhost:8080/mutant
```
Sending the proper request:
```json
{
    "dna": ["ATGCCA","CAGAGC","TTATGT","AAAATT","CTCTTA","TCACTG"]
}
```

To retrieve the stats, use the endpoint
```
GET http://localhost:8080/stats
```

## Demo
There is a demo published in the following external link:
[Demo here](http://mutantschallenge-env-1.eba-gbpepfvp.us-east-2.elasticbeanstalk.com/)