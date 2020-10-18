FROM jboss/wildfly

ARG WILDFLY_USERNAME
ARG WILDFLY_PASSWORD

ENV WILDFLY_USERNAME=${WILDFLY_USERNAME}
ENV WILDFLY_PASSWORD=${WILDFLY_PASSWORD}

RUN /opt/jboss/wildfly/bin/add-user.sh -u "${WILDFLY_USERNAME}" -p "${WILDFLY_PASSWORD}" --silent
ENTRYPOINT /opt/jboss/wildfly/bin/standalone.sh -b=0.0.0.0 -bmanagement=0.0.0.0