#FROM openjdk:11
#LABEL maintainer="didin@djamware.com"
#VOLUME /tmp
#EXPOSE 8086
#ARG JAR_FILE=target/*.jar
#COPY ${JAR_FILE} javapro11-0.0.1-SNAPSHOT.jar
#ENTRYPOINT ["java","-Dspring.profiles.active=dev","-jar","javapro11-0.0.1-SNAPSHOT.jar"]

FROM maven:3.6.3-jdk-11-slim AS MAVEN_BUILD
#FROM maven:3.5.2-jdk-8-alpine AS MAVEN_BUILD FOR JAVA 8
ARG SPRING_ACTIVE_PROFILE
MAINTAINER Jasmin
COPY pom.xml /build/
COPY src /build/src/
WORKDIR /build/
RUN mvn clean install -Dspring.profiles.active=$SPRING_ACTIVE_PROFILE && mvn package -B -e -Dspring.profiles.active=$SPRING_ACTIVE_PROFILE
FROM openjdk:11-slim
#FROM openjdk:8-alpine FOR JAVA 8
WORKDIR /app
COPY --from=MAVEN_BUILD /build/target/javapro11-*.jar /app/javapro11.jar
ENTRYPOINT ["java", "-jar", "javapro11.jar"]
