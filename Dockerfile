FROM maven:3.8.3-openjdk-17 AS build
COPY src /home/app/src
COPY pom.xml /home/app
RUN mvn -f /home/app/pom.xml clean package
EXPOSE 8080
ENTRYPOINT ["java","-jar","/home/app/target/spring_rest_docker.jar"]

FROM mysql:5.7.15

MAINTAINER luca

ENV MYSQL_DATABASE=bookmarks \
    MYSQL_ROOT_PASSWORD=roomless \
    MYSQL_ROOT_HOSTS=% \
    MYSQL_USER=root \
    MYSQL_ALLOW_EMPTY_PASSWORD=1

ADD src/main/resources/schema.sql /docker-entrypoint-initdb.d

EXPOSE 3306
