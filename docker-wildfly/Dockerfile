FROM jboss/wildfly

# RUN mkdir -p /opt/jboss/wildfly/modules/system/layers/base/com/mysql/main
USER root
RUN yum install wget vim -y
USER jboss
RUN wget https://cdn.mysql.com//Downloads/Connector-J/mysql-connector-java-8.0.21.tar.gz -O /opt/jboss/mysql-driver.tar.gz
RUN tar -xzvf /opt/jboss/mysql-driver.tar.gz
COPY ./add_module.cli /opt/jboss/

ARG WILDFLY_USERNAME
ARG WILDFLY_PASSWORD

ENV WILDFLY_USERNAME=${WILDFLY_USERNAME}
ENV WILDFLY_PASSWORD=${WILDFLY_PASSWORD}

RUN /opt/jboss/wildfly/bin/add-user.sh -u "${WILDFLY_USERNAME}" -p "${WILDFLY_PASSWORD}" --silent

COPY configuration/app.properties /opt/jboss/wildfly/standalone/configuration/app.properties
RUN (/opt/jboss/wildfly/bin/standalone.sh -b=0.0.0.0 -bmanagement=0.0.0.0 &) && /opt/jboss/wildfly/bin/jboss-cli.sh -c &>/dev/null; while [[ $? -ne 0 ]]; do sleep 1; /opt/jboss/wildfly/bin/jboss-cli.sh -c &>/dev/null; done; /opt/jboss/wildfly/bin/jboss-cli.sh -c --file=/opt/jboss/add_module.cli
ENTRYPOINT /opt/jboss/wildfly/bin/standalone.sh -b=0.0.0.0 -bmanagement=0.0.0.0