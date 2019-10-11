#
# Build stage
#
FROM maven:3.6.0-jdk-11-slim AS build
COPY src /home/app/src
COPY pom.xml /home/app
RUN mvn -f /home/app/pom.xml clean package -P docker -DskipTests

#
# Package stage
#
FROM java:8
EXPOSE 8080
ADD /target/websocket-0.0.1-SNAPSHOT.jar websocket-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java", "-jar", "websocket-0.0.1-SNAPSHOT.jar"]