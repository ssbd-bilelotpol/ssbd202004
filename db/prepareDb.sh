#!/bin/bash

DB_CREATE=$(cat ./dbCreate.sql)
DB_INIT=$(cat ./dbInit.sql)

PGPASSWORD=$bamboo_DB_PASSWORD psql -v ON_ERROR_STOP=1 -U $DB_USER -h $DB_HOST -p $DB_PORT -d $DB_NAME -c "BEGIN; drop owned by $DB_USER; $DB_CREATE; $DB_INIT; COMMIT;"
