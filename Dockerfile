FROM mysql:5.7.15

MAINTAINER luca

ENV MYSQL_DATABASE=bookmarks \
    MYSQL_ROOT_PASSWORD=roomless \
    MYSQL_ROOT_HOSTS=% \
    MYSQL_USER=root \
    MYSQL_ALLOW_EMPTY_PASSWORD=1

ADD src/main/resources/schema.sql /docker-entrypoint-initdb.d

EXPOSE 3306
