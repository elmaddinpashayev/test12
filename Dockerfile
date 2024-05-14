FROM openjdk:17-jdk-alpine
ARG JAR_FILE=target/*.jar
ENTRYPOINT ["java", "-jar", "/app.jar"]