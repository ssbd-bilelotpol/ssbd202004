#!/bin/sh
source .env
cd docker-mariadb && docker build . -t th7nder/bilelotpol-db:0.2  \
    --build-arg MYSQL_USER_MOK=$MYSQL_USER_MOK \
    --build-arg MYSQL_PASSWORD_MOK=$MYSQL_PASSWORD_MOK  \
    --build-arg MYSQL_USER_MOL=$MYSQL_USER_MOL \
    --build-arg MYSQL_PASSWORD_MOL=$MYSQL_PASSWORD_MOL  \
    --build-arg MYSQL_USER_MOB=$MYSQL_USER_MOB \
    --build-arg MYSQL_PASSWORD_MOB=$MYSQL_PASSWORD_MOB  \
    --build-arg MYSQL_USER_AUTH=$MYSQL_USER_AUTH \
    --build-arg MYSQL_PASSWORD_AUTH=$MYSQL_PASSWORD_AUTH  
cd .. 
cd docker-wildlfy && docker build . -t th7nder/bilelotpol-wildfly:0.2  \
    --build-arg MYSQL_USER_MOK=$MYSQL_USER_MOK \
    --build-arg MYSQL_PASSWORD_MOK=$MYSQL_PASSWORD_MOK  \
    --build-arg MYSQL_USER_MOL=$MYSQL_USER_MOL \
    --build-arg MYSQL_PASSWORD_MOL=$MYSQL_PASSWORD_MOL  \
    --build-arg MYSQL_USER_MOB=$MYSQL_USER_MOB \
    --build-arg MYSQL_PASSWORD_MOB=$MYSQL_PASSWORD_MOB  \
    --build-arg MYSQL_USER_AUTH=$MYSQL_USER_AUTH \
    --build-arg MYSQL_PASSWORD_AUTH=$MYSQL_PASSWORD_AUTH \
    --build-arg WILDFLY_USERNAME=$WILDFLY_USERNAME \
    --build-arg WILDFLY_PASSWORD=$WILDFLY_PASSWORD \
    --build-arg MYSQL_USER=$MYSQL_USER \
    --build-arg MYSQL_PASSWORD=$MYSQL_PASSWORD \
    --build-arg MYSQL_DATABASE=$MYSQL_DATABASE