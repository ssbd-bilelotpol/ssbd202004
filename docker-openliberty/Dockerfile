FROM openliberty/open-liberty:full-java11-openj9-ubi
USER root
RUN yum install wget -y
USER default
COPY server.xml /config/
RUN mkdir /config/drivers
RUN wget https://cdn.mysql.com//Downloads/Connector-J/mysql-connector-java-8.0.21.tar.gz -O /tmp/mysql-driver.tar.gz
RUN tar -C /tmp/ -xzf /tmp/mysql-driver.tar.gz
RUN cp /tmp/mysql-connector-java-8.0.21/mysql-connector-java-8.0.21.jar /config/drivers

# Running configure.sh takes ~20s at docker build time but will greatly reduce
# container start time. You may not want to run this for local development if
# you are constantly changing the app layer, but should used for production
RUN configure.sh
