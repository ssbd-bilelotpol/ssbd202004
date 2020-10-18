FROM jboss/wildfly

RUN /opt/jboss/wildfly/bin/add-user.sh -u admin -p password --silent
ENTRYPOINT /opt/jboss/wildfly/bin/standalone.sh -b=0.0.0.0 -bmanagement=0.0.0.0
