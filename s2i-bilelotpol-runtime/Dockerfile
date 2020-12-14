FROM jboss/wildfly:21.0.0.Final

LABEL maintainer="Adam Zambrzycki <adam@zambrzycki.net>" \
      io.openshift.s2i.scripts-url="image:///usr/libexec/s2i/bin"

USER root
RUN yum install wget -y && yum clean all -y

# Install mysql-driver
USER jboss
RUN wget https://cdn.mysql.com//Downloads/Connector-J/mysql-connector-java-8.0.21.tar.gz -O /opt/jboss/mysql-driver.tar.gz
RUN tar -xzvf /opt/jboss/mysql-driver.tar.gz

# Copy add_module.sh script
COPY ./add_module.sh /opt/jboss/

# Wildfly arguments
ARG WILDFLY_USERNAME
ARG WILDFLY_PASSWORD

ENV WILDFLY_USERNAME=${WILDFLY_USERNAME}
ENV WILDFLY_PASSWORD=${WILDFLY_PASSWORD}

# MySQL arguments
ARG MYSQL_USER
ARG MYSQL_PASSWORD
ARG MYSQL_DATABASE
ARG MYSQL_USER_MOK
ARG MYSQL_PASSWORD_MOK
ARG MYSQL_USER_MOB
ARG MYSQL_PASSWORD_MOB
ARG MYSQL_USER_MOL
ARG MYSQL_PASSWORD_MOL
ARG MYSQL_USER_AUTH
ARG MYSQL_PASSWORD_AUTH

ENV MYSQL_USER=${MYSQL_USER}
ENV MYSQL_PASSWORD=${MYSQL_PASSWORD}
ENV MYSQL_DATABASE=${MYSQL_DATABASE}
ENV MYSQL_USER_MOK=${MYSQL_USER_MOK}
ENV MYSQL_PASSWORD_MOK=${MYSQL_PASSWORD_MOK}
ENV MYSQL_USER_MOB=${MYSQL_USER_MOB}
ENV MYSQL_PASSWORD_MOB=${MYSQL_PASSWORD_MOB}
ENV MYSQL_USER_MOL=${MYSQL_USER_MOL}
ENV MYSQL_PASSWORD_MOL=${MYSQL_PASSWORD_MOL}
ENV MYSQL_USER_AUTH=${MYSQL_USER_AUTH}
ENV MYSQL_PASSWORD_AUTH=${MYSQL_PASSWORD_AUTH}

# Generate wildfly configuration
RUN /opt/jboss/wildfly/bin/add-user.sh -u "${WILDFLY_USERNAME}" -p "${WILDFLY_PASSWORD}" --silent
RUN (/opt/jboss/wildfly/bin/standalone.sh -b=0.0.0.0 -bmanagement=0.0.0.0 &) && /opt/jboss/wildfly/bin/jboss-cli.sh -c &>/dev/null; while [[ $? -ne 0 ]]; do sleep 1; /opt/jboss/wildfly/bin/jboss-cli.sh -c &>/dev/null; done; sh /opt/jboss/add_module.sh &>/dev/null

USER root
RUN chgrp -R 0 /opt/jboss && chmod -R g=u /opt/jboss

# Prepare image for S2I
COPY ./s2i/bin/ /usr/libexec/s2i
COPY ./s2i/bin/ /usr/libexec/s2i/bin

USER jboss
