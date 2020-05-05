#!/bin/bash


PGPASSWORD=$bamboo_DB_PASSWORD psql -U $DB_USER -h $DB_HOST -p $DB_PORT -d $DB_NAME -c "drop owned by $DB_USER;"

PGPASSWORD=$bamboo_DB_PASSWORD psql -U $DB_USER -h $DB_HOST -p $DB_PORT -d $DB_NAME -f ./dbCreate.sql

PGPASSWORD=$bamboo_DB_PASSWORD psql -U $DB_USER -h $DB_HOST -p $DB_PORT -d $DB_NAME -f ./dbInit.sql
