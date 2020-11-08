FROM mariadb

ARG MYSQL_USER_MOK
ENV MYSQL_USER_MOK=${MYSQL_USER_MOK}

ARG MYSQL_PASSWORD_MOK
ENV MYSQL_PASSWORD_MOK=${MYSQL_PASSWORD_MOK}

ARG MYSQL_USER_MOL
ENV MYSQL_USER_MOL=${MYSQL_USER_MOL}

ARG MYSQL_PASSWORD_MOL
ENV MYSQL_PASSWORD_MOL=${MYSQL_PASSWORD_MOL}

ARG MYSQL_USER_MOB
ENV MYSQL_USER_MOB=${MYSQL_USER_MOB}

ARG MYSQL_PASSWORD_MOB
ENV MYSQL_PASSWORD_MOB=${MYSQL_PASSWORD_MOB}

ARG MYSQL_USER_AUTH
ENV MYSQL_USER_AUTH=${MYSQL_USER_AUTH}

ARG MYSQL_PASSWORD_AUTH
ENV MYSQL_PASSWORD_AUTH=${MYSQL_PASSWORD_AUTH}


ADD ./db/* /docker-entrypoint-initdb.d/
RUN sed -i "s/ssbd04mok@/$MYSQL_USER_MOK@/g" /docker-entrypoint-initdb.d/dbCreate.sql && \
    sed -i "s/ssbd04mol@/$MYSQL_USER_MOL@/g" /docker-entrypoint-initdb.d/dbCreate.sql && \
    sed -i "s/ssbd04mob@/$MYSQL_USER_MOB@/g" /docker-entrypoint-initdb.d/dbCreate.sql && \
    sed -i "s/ssbd04auth@/$MYSQL_USER_AUTH@/g" /docker-entrypoint-initdb.d/dbCreate.sql

RUN echo "CREATE USER $MYSQL_USER_MOK@'%' IDENTIFIED BY '$MYSQL_PASSWORD_MOK';" >> /docker-entrypoint-initdb.d/dbAddUsers.sql && \
    echo "CREATE USER $MYSQL_USER_MOL@'%' IDENTIFIED BY '$MYSQL_PASSWORD_MOL';" >> /docker-entrypoint-initdb.d/dbAddUsers.sql && \
    echo "CREATE USER $MYSQL_USER_MOB@'%' IDENTIFIED BY '$MYSQL_PASSWORD_MOB';" >> /docker-entrypoint-initdb.d/dbAddUsers.sql && \
    echo "CREATE USER $MYSQL_USER_AUTH@'%' IDENTIFIED BY '$MYSQL_PASSWORD_AUTH';" >> /docker-entrypoint-initdb.d/dbAddUsers.sql
