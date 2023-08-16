FROM ghcr.io/graalvm/jdk:ol8-java17-22.3.3
ARG JAR_FILE=build/libs/reactive-template-0.0.1-SNAPSHOT.jar
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java", "-jar","/app/app.jar"]
