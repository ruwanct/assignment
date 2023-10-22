# Mancala Game API

### Application Architecture & Technologies Used:
* Backed application is built using Spring Boot 3.1.3 and Java 17.
* Liquibase is used for database migration.
* Postgres SQL is used as the database.
* Swagger is used for API documentation.
* Docker is used to containerize the application.
* Docker Compose is used to run the application and the database.
* Used TestContainers to run the integration tests.
* JUnit 5 is used for unit testing.
* Mockito is used for mocking.
* Used builder pattern to build the response objects.


### Pre-requisites to run the application with Docker
1. Docker & Docker Compose installed (I used Docker Desktop for Mac)
2. Java 17 
3. Gradle (I used IntelliJ Gradle Wrapper v8.2.1)
### How to run the backend application with docker?
1. Unzip the backend file
2. Go to the backend folder (root) or open it in IntelliJ or your favorite IDE
3. Run the following commands

###### if you opened the project in IntelliJ
```bash
./gradlew clean bootJar 
```
###### Or else on terminal(cmd) run the following command
```bash
./gradle clean bootJar 
```

###### Then run the following command to build the docker image
```bash
docker-compose build
```
###### Then run the following command to start the postgres SQL docker container
```bash
docker-compose up -d postgresql
```
###### Then run the following command to start the backend  docker container
```bash
docker-compose up app
```
4. The backend application should be running on port 8080
5. You can check the health of the application by going to the following URL: http://localhost:8080/actuator/health
6. You access the swagger UI by going to the following URL: http://localhost:8080/swagger-ui.html



###### To stop the application, run the following command:
```bash
docker-compose down
```

### Liquibase migration scripts:
* I used Liquibase as the DB migration tool.
* The migration scripts are located in the following folder: `/src/main/resources/db.changelog/`
* You will find 3 scripts inside the `changelog` folder
  1. `001-initial-schema.sql` : Creates the database tables

### Integration Tests:
* When you run the integration tests, the application will start a postgres SQL docker container using TestContainers.



### Future Improvements to the API:
* Secure the API with Spring Security. Probably use JWT for authentication and authorization.
* Use Socket.io to make the game real-time.



### _Thanks for reading this far. I hope you enjoyed it :)_
