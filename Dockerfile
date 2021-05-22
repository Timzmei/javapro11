FROM openjdk:11
#LABEL maintainer="didin@djamware.com"
VOLUME /tmp
EXPOSE 8086
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} javapro11-1.0-SNAPSHOT.jar
ENTRYPOINT ["java","-Dspring.profiles.active=dev","-jar","javapro11-1.0-SNAPSHOT.jar"]
