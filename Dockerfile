FROM openjdk:8-jdk-alpine
VOLUME /tmp
ARG JAR_FILE=target/eimantas-backend-0.0.1-SNAPSHOT.jar
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java","-Dspring.profiles.active=${stage}","-jar","/app.jar"]
