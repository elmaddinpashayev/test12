FROM openjdk:17-jdk-alpine
ENTRYPOINT ["java", "-jar", "%~dp0\myjar.jar"]