#!/bin/bash

escape_string () {
    local string="$1"
    echo ${string//\//\\/}
}

DB_URL=$(escape_string $DB_URL)
MOK_PASSWORD=$(escape_string $bamboo_MOK_PASSWORD)
MOB_PASSWORD=$(escape_string $bamboo_MOB_PASSWORD)
MOL_PASSWORD=$(escape_string $bamboo_MOL_PASSWORD)
AUTH_PASSWORD=$(escape_string $bamboo_AUTH_PASSWORD)
API_URL=$(escape_string $API_URL)
JWT_KEY=$(escape_string $bamboo_JWT_KEY_PASSWORD)
ETAG_KEY=$(escape_string $bamboo_ETAG_KEY_PASSWORD)
FRONTEND_URL=$(escape_string $FRONTEND_URL)
API_KEY=$(escape_string $bamboo_API_KEY_PASSWORD)
API_SERVER=$(escape_string $API_SERVER)
SENDER=$(escape_string $SENDER)
GOOGLE_SITE_KEY=$(escape_string $GOOGLE_SITE_KEY)
GOOGLE_SECRET=$(escape_string $bamboo_GOOGLE_SECRET_PASSWORD)


sed "
s/<<DB_URL>>/$DB_URL/g;
s/<<mokCP_PASSWORD>>/$MOK_PASSWORD/g;
s/<<mobCP_PASSWORD>>/$MOB_PASSWORD/g;
s/<<molCP_PASSWORD>>/$MOL_PASSWORD/g;
s/<<authCP_PASSWORD>>/$AUTH_PASSWORD/g
" ../src/main/webapp/WEB-INF/payara-resources.xml.example > ../src/main/webapp/WEB-INF/payara-resources.xml

sed "
s/<<REACT_APP_URL>>/$API_URL/g;
s/<<REACT_APP_GOOGLE_RECAPTCHA_SITE_KEY>>/$GOOGLE_SITE_KEY/g;
" ../src/main/frontend/.env.example > ../src/main/frontend/.env

sed "
s/<<JWT_SECRET_KEY>>/$JWT_KEY/g;
s/<<ETAG_SECRET_KEY>>/$ETAG_KEY/g;
s/<<FRONTEND_URL>>/$FRONTEND_URL/g;
s/<<API_KEY>>/$API_KEY/g;
s/<<API_SERVER>>/$API_SERVER/g;
s/<<SENDER>>/$SENDER/g;
s/<<GOOGLE_SECRET>>/$GOOGLE_SECRET/g
" ../src/main/resources/config.properties.example  > ../src/main/resources/config.properties
