#!/bin/bash
/opt/jboss/wildfly/bin/jboss-cli.sh -c <<EOF
batch
module add --name=com.mysql --resources=/opt/jboss/mysql-connector-java-8.0.21/mysql-connector-java-8.0.21.jar --dependencies=javax.api,javax.transaction.api
/subsystem=datasources/jdbc-driver=mysql:add(driver-name=mysql,driver-module-name=com.mysql,driver-xa-datasource-class-name=com.mysql.cj.jdbc.MysqlXADataSource)
data-source add --name=ssbd04mokDS --jndi-name=java:/jdbc/ssbd04mokDS --driver-name=mysql --connection-url=jdbc:mysql://db:3306/$MYSQL_DATABASE?useUnicode=true&connectionCollation=utf8_general_ci&characterSetResults=utf8&characterEncoding=utf8 --user-name=$MYSQL_USER_MOK --password=$MYSQL_PASSWORD_MOK
data-source add --name=ssbd04molDS --jndi-name=java:/jdbc/ssbd04molDS --driver-name=mysql --connection-url=jdbc:mysql://db:3306/$MYSQL_DATABASE?useUnicode=true&connectionCollation=utf8_general_ci&characterSetResults=utf8&characterEncoding=utf8 --user-name=$MYSQL_USER_MOL --password=$MYSQL_PASSWORD_MOL
data-source add --name=ssbd04mobDS --jndi-name=java:/jdbc/ssbd04mobDS --driver-name=mysql --connection-url=jdbc:mysql://db:3306/$MYSQL_DATABASE?useUnicode=true&connectionCollation=utf8_general_ci&characterSetResults=utf8&characterEncoding=utf8 --user-name=$MYSQL_USER_MOB --password=$MYSQL_PASSWORD_MOB
data-source add --name=ssbd04authDS --jndi-name=java:/jdbc/ssbd04authDS --driver-name=mysql --connection-url=jdbc:mysql://db:3306/$MYSQL_DATABASE?useUnicode=true&connectionCollation=utf8_general_ci&characterSetResults=utf8&characterEncoding=utf8 --user-name=$MYSQL_USER_AUTH --password=$MYSQL_PASSWORD_AUTH
run-batch
EOF