FROM openliberty/open-liberty:full-java11-openj9-ubi
COPY server.xml /config/
RUN mkdir /config/drivers
COPY mysql-connector-java-8.0.21.jar /config/drivers/

# Running configure.sh takes ~20s at docker build time but will greatly reduce
# container start time. You may not want to run this for local development if
# you are constantly changing the app layer, but should used for production
RUN configure.sh