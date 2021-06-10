#FROM adoptopenjdk/openjdk11:alpine
#FROM amazoncorretto:11-alpine-jdk
#FROM openjdk:11
FROM openjdk:11-jre-slim
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} app.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","/app.jar"]
