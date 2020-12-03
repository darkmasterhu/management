FROM openjdk:11-jre-slim-buster

MAINTAINER darkmasterhu@gmail.com

# copy the packaged jar file into our docker image
COPY build/libs/management.jar /management.jar

# set the startup command to execute the jar
CMD ["java", "-jar", "/management.jar"]

EXPOSE 8080:8080