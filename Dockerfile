FROM jboss/wildfly

RUN mkdir -p /opt/jboss/wildfly/modules/system/layers/base/com/mysql/main
USER root
RUN yum install wget -y
USER jboss
RUN wget https://cdn.mysql.com//Downloads/Connector-J/mysql-connector-java-8.0.21.tar.gz -O /opt/jboss/mysql-driver.tar.gz
RUN tar -xzvf /opt/jboss/mysql-driver.tar.gz
RUN cp -v /opt/jboss/mysql-connector-java-8.0.21/mysql-connector-java-8.0.21.jar /opt/jboss/wildfly/modules/system/layers/base/com/mysql/main/
COPY ./docker/module.xml /opt/jboss/wildfly/modules/system/layers/base/com/mysql/main/

RUN /opt/jboss/wildfly/bin/add-user.sh -u admin -p password --silent
ENTRYPOINT /opt/jboss/wildfly/bin/standalone.sh -b=0.0.0.0 -bmanagement=0.0.0.0
