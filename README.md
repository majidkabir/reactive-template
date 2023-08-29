# reactive-template
A template for a reactive spring-boot application that contains the below list

1. Handling web request with RouterFunction
2. R2DBC for project and JDBC for flyway
3. Using test container in integration test that handles migration of DB with flyway
4. Validation by annotation
5. OpenAPI and Swagger
6. MapStruct

# Building docker
>./gradlew bootBuildImage

# Starting the application
>./gradlew bootRun

This command will spin up the DB by starting the docker-compose file and after that run the application.

# Swagger UI
http://localhost:8080/webjars/swagger-ui/index.html
