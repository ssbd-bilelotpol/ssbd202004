CREATE TABLE account
(
    id                     bigint                      NOT NULL,
    creation_date_time     timestamp without time zone NOT NULL,
    modification_date_time timestamp without time zone,
    version                bigint,
    active                 boolean                     NOT NULL,
    confirm                boolean                     NOT NULL,
    login                  character varying(30)       NOT NULL,
    password               character varying(60)       NOT NULL,
    account_details_id     bigint                      NOT NULL
);

CREATE TABLE account_access_level
(
    access_level           character varying(31)       NOT NULL,
    id                     bigint                      NOT NULL,
    creation_date_time     timestamp without time zone NOT NULL,
    modification_date_time timestamp without time zone,
    version                bigint,
    account_id             bigint                      NOT NULL
);

CREATE TABLE account_details
(
    id                     bigint                      NOT NULL,
    creation_date_time     timestamp without time zone NOT NULL,
    modification_date_time timestamp without time zone,
    version                bigint,
    email                  character varying(255)      NOT NULL,
    first_name             character varying(30)       NOT NULL,
    last_name              character varying(30)       NOT NULL,
    phone_number           character varying(15)       NOT NULL
);

CREATE TABLE account_auth_info
(
    id                      bigint                      NOT NULL,
    last_ip_address         character varying(45),
    current_auth            timestamp without time zone,
    last_success_auth       timestamp without time zone,
    last_incorrect_auth     timestamp without time zone,
    account_id              bigint                      NOT NULL
);

CREATE SEQUENCE account_seq
    START WITH 1
    INCREMENT BY 10
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;

ALTER TABLE account_access_level
    ALTER COLUMN id ADD GENERATED BY DEFAULT AS IDENTITY (
        SEQUENCE NAME account_access_level_id_seq
        START WITH 1
        INCREMENT BY 1
        NO MINVALUE
        NO MAXVALUE
        CACHE 1
        );


CREATE SEQUENCE account_details_seq
    START WITH 1
    INCREMENT BY 10
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;

ALTER TABLE account_auth_info
    ALTER COLUMN id ADD GENERATED BY DEFAULT AS IDENTITY (
        SEQUENCE NAME account_auth_info_seq
            START WITH 1
            INCREMENT BY 1
            NO MINVALUE
            NO MAXVALUE
            CACHE 1
        );

CREATE TABLE airplane_schema
(
    id                     bigint                      NOT NULL,
    creation_date_time     timestamp without time zone NOT NULL,
    modification_date_time timestamp without time zone,
    version                bigint,
    cols                   integer                     NOT NULL,
    rows                   integer                     NOT NULL,
    CONSTRAINT airplane_schema_cols_check CHECK ((cols >= 1)),
    CONSTRAINT airplane_schema_rows_check CHECK ((rows >= 1))
);

ALTER TABLE airplane_schema
    ALTER COLUMN id ADD GENERATED BY DEFAULT AS IDENTITY (
        SEQUENCE NAME airplane_schema_id_seq
        START WITH 1
        INCREMENT BY 1
        NO MINVALUE
        NO MAXVALUE
        CACHE 1
        );

CREATE TABLE airport
(
    id                     bigint                      NOT NULL,
    creation_date_time     timestamp without time zone NOT NULL,
    modification_date_time timestamp without time zone,
    version                bigint,
    city                   character varying(32)       NOT NULL,
    code                   character varying(255)      NOT NULL,
    country                character varying(32)       NOT NULL,
    name                   character varying(32)       NOT NULL
);


ALTER TABLE airport
    ALTER COLUMN id ADD GENERATED BY DEFAULT AS IDENTITY (
        SEQUENCE NAME airport_id_seq
        START WITH 1
        INCREMENT BY 1
        NO MINVALUE
        NO MAXVALUE
        CACHE 1
        );

CREATE TABLE benefit
(
    id                     bigint                      NOT NULL,
    creation_date_time     timestamp without time zone NOT NULL,
    modification_date_time timestamp without time zone,
    version                bigint,
    description            character varying(255)      NOT NULL,
    name                   character varying(128)      NOT NULL
);


ALTER TABLE benefit
    ALTER COLUMN id ADD GENERATED BY DEFAULT AS IDENTITY (
        SEQUENCE NAME benefit_id_seq
        START WITH 1
        INCREMENT BY 1
        NO MINVALUE
        NO MAXVALUE
        CACHE 1
        );

CREATE TABLE connection
(
    id                     bigint                      NOT NULL,
    creation_date_time     timestamp without time zone NOT NULL,
    modification_date_time timestamp without time zone,
    version                bigint,
    base_price             numeric(9, 2)               NOT NULL,
    destination_id         bigint                      NOT NULL,
    source_id              bigint                      NOT NULL
);

CREATE TABLE connection_stats
(
    connection_id          bigint                       NOT NULL,
    profit                 numeric(9, 2)                 NOT NULL
);


ALTER TABLE connection
    ALTER COLUMN id ADD GENERATED BY DEFAULT AS IDENTITY (
        SEQUENCE NAME connection_id_seq
        START WITH 1
        INCREMENT BY 1
        NO MINVALUE
        NO MAXVALUE
        CACHE 1
        );

CREATE TABLE flight
(
    id                     bigint                      NOT NULL,
    creation_date_time     timestamp without time zone NOT NULL,
    modification_date_time timestamp without time zone,
    version                bigint,
    end_date_time          timestamp without time zone NOT NULL,
    flight_code            character varying(30)       NOT NULL,
    price                  numeric(9, 2)               NOT NULL,
    start_date_time        timestamp without time zone NOT NULL,
    status                 character varying(64)       NOT NULL,
    airplane_schema_id     bigint                      NOT NULL,
    connection_id          bigint                      NOT NULL
);

CREATE SEQUENCE flight_seq
    START WITH 1
    INCREMENT BY 10
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;

CREATE TABLE passenger
(
    id                     bigint                      NOT NULL,
    creation_date_time     timestamp without time zone NOT NULL,
    modification_date_time timestamp without time zone,
    version                bigint,
    email                  character varying(255)      NOT NULL,
    first_name             character varying(30)       NOT NULL,
    last_name              character varying(30)       NOT NULL,
    phone_number           character varying(15)       NOT NULL,
    seat_id                bigint                      NOT NULL,
    ticket_id              bigint                      NOT NULL
);

CREATE SEQUENCE passenger_seq
    START WITH 1
    INCREMENT BY 10
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;

CREATE TABLE seat
(
    id                     bigint                      NOT NULL,
    creation_date_time     timestamp without time zone NOT NULL,
    modification_date_time timestamp without time zone,
    version                bigint,
    col                    integer                     NOT NULL,
    "row"                  integer                     NOT NULL,
    airplane_schema_id     bigint                      NOT NULL,
    seat_class_id          bigint                      NOT NULL
);


CREATE TABLE seat_class
(
    id                     bigint                      NOT NULL,
    creation_date_time     timestamp without time zone NOT NULL,
    modification_date_time timestamp without time zone,
    version                bigint,
    name                   character varying(30)       NOT NULL,
    price                  numeric(9, 2)               NOT NULL
);


CREATE TABLE seat_class_benefits
(
    seat_class_id bigint NOT NULL,
    benefit_id    bigint NOT NULL
);

ALTER TABLE seat_class
    ALTER COLUMN id ADD GENERATED BY DEFAULT AS IDENTITY (
        SEQUENCE NAME seat_class_id_seq
        START WITH 1
        INCREMENT BY 1
        NO MINVALUE
        NO MAXVALUE
        CACHE 1
        );

ALTER TABLE seat
    ALTER COLUMN id ADD GENERATED BY DEFAULT AS IDENTITY (
        SEQUENCE NAME seat_id_seq
        START WITH 1
        INCREMENT BY 1
        NO MINVALUE
        NO MAXVALUE
        CACHE 1
        );

CREATE TABLE ticket
(
    id                     bigint                      NOT NULL,
    creation_date_time     timestamp without time zone NOT NULL,
    modification_date_time timestamp without time zone,
    version                bigint,
    total_price            numeric(9, 2)               NOT NULL,
    account_id             bigint                      NOT NULL,
    flight_id              bigint                      NOT NULL
);

CREATE SEQUENCE ticket_seq
    START WITH 1
    INCREMENT BY 10
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


CREATE TABLE verification_token
(
    type                   character varying(31)       NOT NULL,
    id                     uuid                        NOT NULL,
    creation_date_time     timestamp without time zone NOT NULL,
    modification_date_time timestamp without time zone,
    version                bigint,
    expire_date_time       timestamp without time zone NOT NULL,
    account_id             bigint                      NOT NULL
);

-- Constraints Primary Keys
ALTER TABLE ONLY account_access_level
    ADD CONSTRAINT account_access_level_pkey PRIMARY KEY (id);

ALTER TABLE ONLY account_auth_info
    ADD CONSTRAINT account_auth_info_pkey PRIMARY KEY (id);

ALTER TABLE ONLY account_access_level
    ADD CONSTRAINT account_access_level_unique UNIQUE (account_id, access_level);

ALTER TABLE ONLY account
    ADD CONSTRAINT account_account_details_id_unique UNIQUE (account_details_id);

ALTER TABLE ONLY account_auth_info
    ADD CONSTRAINT account_auth_info_account_id_unique UNIQUE (account_id);

ALTER TABLE ONLY account_details
    ADD CONSTRAINT account_details_email_unique UNIQUE (email);

ALTER TABLE ONLY account_details
    ADD CONSTRAINT account_details_pkey PRIMARY KEY (id);

ALTER TABLE ONLY account
    ADD CONSTRAINT account_login_unique UNIQUE (login);

ALTER TABLE ONLY account
    ADD CONSTRAINT account_pkey PRIMARY KEY (id);

ALTER TABLE ONLY airplane_schema
    ADD CONSTRAINT airplane_schema_pkey PRIMARY KEY (id);

ALTER TABLE ONLY airport
    ADD CONSTRAINT airport_pkey PRIMARY KEY (id);

ALTER TABLE ONLY benefit
    ADD CONSTRAINT benefit_pkey PRIMARY KEY (id);

ALTER TABLE ONLY connection
    ADD CONSTRAINT connection_pkey PRIMARY KEY (id);

ALTER TABLE ONLY connection_stats
    ADD CONSTRAINT connection_id PRIMARY KEY (connection_id);

ALTER TABLE ONLY flight
    ADD CONSTRAINT flight_flight_code_unique UNIQUE (flight_code);

ALTER TABLE ONLY flight
    ADD CONSTRAINT flight_pkey PRIMARY KEY (id);

ALTER TABLE ONLY passenger
    ADD CONSTRAINT passenger_pkey PRIMARY KEY (id);

ALTER TABLE ONLY seat_class_benefits
    ADD CONSTRAINT seat_class_benefits_pkey PRIMARY KEY (seat_class_id, benefit_id);

ALTER TABLE ONLY seat_class
    ADD CONSTRAINT seat_class_name_unique UNIQUE (name);

ALTER TABLE ONLY seat_class
    ADD CONSTRAINT seat_class_pkey PRIMARY KEY (id);

ALTER TABLE ONLY seat
    ADD CONSTRAINT seat_pkey PRIMARY KEY (id);

ALTER TABLE ONLY ticket
    ADD CONSTRAINT ticket_pkey PRIMARY KEY (id);

ALTER TABLE ONLY verification_token
    ADD CONSTRAINT verification_token_pkey PRIMARY KEY (id);

-- Foreign Keys

ALTER TABLE ONLY account
    ADD CONSTRAINT account_account_details_fk FOREIGN KEY (account_details_id) REFERENCES account_details (id);

ALTER TABLE ONLY account_access_level
    ADD CONSTRAINT account_account_access_level_fk FOREIGN KEY (account_id) REFERENCES account (id);

ALTER TABLE ONLY account_auth_info
    ADD CONSTRAINT account_auth_info_account_fk FOREIGN KEY (account_id) REFERENCES account (id);

ALTER TABLE ONLY connection
    ADD CONSTRAINT connection_airport_dst_fk FOREIGN KEY (destination_id) REFERENCES airport (id);

ALTER TABLE ONLY connection
    ADD CONSTRAINT connection_airport_src_fk FOREIGN KEY (source_id) REFERENCES airport (id);

ALTER TABLE ONLY connection_stats
    ADD CONSTRAINT connection_connection_fk FOREIGN KEY (connection_id) REFERENCES connection (id);

ALTER TABLE ONLY seat_class_benefits
    ADD CONSTRAINT seat_class_fk FOREIGN KEY (seat_class_id) REFERENCES seat_class (id);

ALTER TABLE ONLY seat_class_benefits
    ADD CONSTRAINT benefit_fk FOREIGN KEY (benefit_id) REFERENCES benefit (id);

ALTER TABLE ONLY flight
    ADD CONSTRAINT flight_airplane_schema_fk FOREIGN KEY (airplane_schema_id) REFERENCES airplane_schema (id);

ALTER TABLE ONLY flight
    ADD CONSTRAINT flight_connection_fk FOREIGN KEY (connection_id) REFERENCES connection (id);

ALTER TABLE ONLY passenger
    ADD CONSTRAINT passenger_seat_fk FOREIGN KEY (seat_id) REFERENCES seat (id);

ALTER TABLE ONLY passenger
    ADD CONSTRAINT passenger_ticket_fk FOREIGN KEY (ticket_id) REFERENCES ticket (id);

ALTER TABLE ONLY seat
    ADD CONSTRAINT seat_airplane_schema_fk FOREIGN KEY (airplane_schema_id) REFERENCES airplane_schema (id);

ALTER TABLE ONLY seat
    ADD CONSTRAINT seat_seat_class_fk FOREIGN KEY (seat_class_id) REFERENCES seat_class (id);

ALTER TABLE ONLY ticket
    ADD CONSTRAINT ticket_account_fk FOREIGN KEY (account_id) REFERENCES account (id);

ALTER TABLE ONLY ticket
    ADD CONSTRAINT ticket_flight_fk FOREIGN KEY (flight_id) REFERENCES flight (id);

ALTER TABLE ONLY verification_token
    ADD CONSTRAINT verification_token_account_fk FOREIGN KEY (account_id) REFERENCES account (id);


-- Indexes

CREATE INDEX account_account_details_fk ON account USING btree (account_details_id);

CREATE INDEX account_account_access_level_fk ON account_access_level USING btree (account_id);

CREATE INDEX account_auth_info_account_fk ON account_auth_info USING btree (account_id);

CREATE INDEX connection_airport_dst_fk ON connection USING btree (destination_id);

CREATE INDEX connection_airport_src_fk ON connection USING btree (source_id);

CREATE INDEX flight_airplane_schema_fk ON flight USING btree (airplane_schema_id);

CREATE INDEX flight_connection_fk ON flight USING btree (connection_id);

CREATE INDEX passenger_seat_fk ON passenger USING btree (seat_id);

CREATE INDEX passenger_ticket_fk ON passenger USING btree (ticket_id);

CREATE INDEX seat_airplane_schema_fk ON seat USING btree (airplane_schema_id);

CREATE INDEX seat_seat_class_fk ON seat USING btree (seat_class_id);

CREATE INDEX benefit_fk ON seat_class_benefits USING btree (benefit_id);

CREATE INDEX seat_class_fk ON seat_class_benefits USING btree (seat_class_id);

CREATE INDEX ticket_account_fk ON ticket USING btree (account_id);

CREATE INDEX ticket_flight_fk ON ticket USING btree (flight_id);

CREATE INDEX verification_token_account_fk ON verification_token USING btree (account_id);

-- AccountDetails

ALTER TABLE account_details
    OWNER TO ssbd04admin;

GRANT
    SELECT,
    INSERT,
    UPDATE,
    DELETE ON account_details TO ssbd04mok;

GRANT
    SELECT ON account_details TO ssbd04mol;

GRANT
    SELECT ON account_details TO ssbd04mob;

ALTER SEQUENCE account_details_seq OWNER TO ssbd04admin;

GRANT
    USAGE, SELECT ON account_details_seq TO ssbd04mok;

GRANT
    SELECT ON account_details_seq TO ssbd04mob;

-- AccountAuthInfo

ALTER TABLE account_auth_info
    OWNER TO ssbd04admin;

GRANT
    SELECT,
    INSERT,
    UPDATE,
    DELETE ON account_auth_info TO ssbd04mok;

GRANT
    SELECT ON account_auth_info TO ssbd04mol;

GRANT
    SELECT ON account_auth_info TO ssbd04mob;

ALTER SEQUENCE account_auth_info_seq OWNER TO ssbd04admin;

GRANT
    USAGE, SELECT ON account_auth_info_seq TO ssbd04mok;

GRANT
    SELECT ON account_auth_info_seq TO ssbd04mob;

-- Account

ALTER TABLE account
    OWNER TO ssbd04admin;

GRANT
    SELECT,
    INSERT,
    UPDATE,
    DELETE ON account TO ssbd04mok;

GRANT
    SELECT ON account TO ssbd04mol;

GRANT
    SELECT ON account TO ssbd04mob;


ALTER SEQUENCE account_seq OWNER TO ssbd04admin;

GRANT
    USAGE, SELECT ON account_seq TO ssbd04mok;

GRANT
    SELECT ON account_seq TO ssbd04mob;

-- VerificationToken

ALTER TABLE verification_token
    OWNER TO ssbd04admin;

GRANT
    SELECT,
    INSERT,
    DELETE ON verification_token TO ssbd04mok;

-- AccountAccessLevel


ALTER TABLE account_access_level
    OWNER TO ssbd04admin;


ALTER SEQUENCE account_access_level_id_seq OWNER TO ssbd04admin;

GRANT
    SELECT,
    INSERT,
    UPDATE,
    DELETE ON account_access_level TO ssbd04mok;

GRANT
    SELECT ON account_access_level TO ssbd04mol;

GRANT
    SELECT ON account_access_level TO ssbd04mob;

GRANT
    USAGE, SELECT ON account_access_level_id_seq TO ssbd04mok;

GRANT
    SELECT ON account_access_level_id_seq TO ssbd04mob;

-- AirplaneSchema

ALTER TABLE airplane_schema
    OWNER TO ssbd04admin;


ALTER SEQUENCE airplane_schema_id_seq OWNER TO ssbd04admin;

GRANT
    SELECT,
    INSERT,
    UPDATE,
    DELETE ON airplane_schema TO ssbd04mol;

GRANT
    SELECT ON airplane_schema TO ssbd04mok;

GRANT
    SELECT ON airplane_schema TO ssbd04mob;

GRANT
    USAGE, SELECT ON airplane_schema_id_seq TO ssbd04mol;

GRANT
    SELECT ON airplane_schema_id_seq TO ssbd04mob;

-- Airport

ALTER TABLE airport
    OWNER TO ssbd04admin;


ALTER SEQUENCE airport_id_seq OWNER TO ssbd04admin;

GRANT
    SELECT,
    INSERT,
    UPDATE,
    DELETE ON airport TO ssbd04mol;

GRANT
    SELECT ON airport TO ssbd04mok;

GRANT
    SELECT ON airport TO ssbd04mob;

GRANT
    USAGE, SELECT ON airport_id_seq TO ssbd04mol;

GRANT
    SELECT ON airport_id_seq TO ssbd04mob;


-- Connection

ALTER TABLE CONNECTION
    OWNER TO ssbd04admin;

ALTER TABLE connection_stats
    OWNER TO ssbd04admin;

ALTER SEQUENCE connection_id_seq OWNER TO ssbd04admin;

GRANT
    SELECT,
    INSERT,
    UPDATE,
    DELETE ON CONNECTION TO ssbd04mol;

GRANT
    SELECT,
    INSERT,
    UPDATE,
    DELETE ON CONNECTION_STATS TO ssbd04mol;

GRANT
    SELECT ON CONNECTION TO ssbd04mok;

GRANT
    SELECT ON CONNECTION TO ssbd04mob;

GRANT
    SELECT ON CONNECTION_STATS TO ssbd04mok;

GRANT
    SELECT ON CONNECTION_STATS TO ssbd04mob;

GRANT
    USAGE, SELECT ON connection_id_seq TO ssbd04mol;
GRANT
    SELECT ON connection_id_seq TO ssbd04mob;


-- Flight
ALTER TABLE flight
    OWNER TO ssbd04admin;

GRANT
    SELECT,
    INSERT,
    UPDATE,
    DELETE ON flight TO ssbd04mol;

GRANT
    SELECT ON flight TO ssbd04mok;

GRANT
    SELECT ON flight TO ssbd04mob;

ALTER SEQUENCE flight_seq OWNER TO ssbd04admin;

GRANT
    USAGE, SELECT ON flight_seq TO ssbd04mol;

GRANT
    SELECT ON flight_seq TO ssbd04mob;

-- Benefit

ALTER TABLE benefit
    OWNER TO ssbd04admin;

ALTER SEQUENCE benefit_id_seq OWNER TO ssbd04admin;

GRANT
    SELECT,
    INSERT,
    UPDATE,
    DELETE ON benefit TO ssbd04mob;

GRANT
    SELECT ON benefit TO ssbd04mok;

GRANT
    SELECT ON benefit TO ssbd04mol;

GRANT
    USAGE, SELECT ON benefit_id_seq TO ssbd04mob;

GRANT
    SELECT ON benefit_id_seq TO ssbd04mol;

-- SeatClass

ALTER TABLE seat_class
    OWNER TO ssbd04admin;


ALTER SEQUENCE seat_class_id_seq OWNER TO ssbd04admin;

GRANT
    SELECT,
    INSERT,
    UPDATE,
    DELETE ON seat_class TO ssbd04mol;

GRANT
    SELECT ON seat_class TO ssbd04mok;

GRANT
    SELECT ON seat_class TO ssbd04mob;

GRANT
    USAGE, SELECT ON seat_class_id_seq TO ssbd04mol;

GRANT
    SELECT ON seat_class_id_seq TO ssbd04mob;


--Seat

ALTER TABLE seat
    OWNER TO ssbd04admin;


ALTER SEQUENCE seat_id_seq OWNER TO ssbd04admin;

GRANT
    SELECT,
    INSERT,
    UPDATE,
    DELETE ON seat TO ssbd04mol;

GRANT
    SELECT ON seat TO ssbd04mok;

GRANT
    SELECT ON seat TO ssbd04mob;

GRANT
    USAGE, SELECT ON seat_id_seq TO ssbd04mol;

GRANT
    SELECT ON seat_id_seq TO ssbd04mob;


-- Ticket

ALTER TABLE ticket
    OWNER TO ssbd04admin;

GRANT
    SELECT,
    INSERT,
    UPDATE,
    DELETE ON ticket TO ssbd04mob;

GRANT
    SELECT ON ticket TO ssbd04mok;

GRANT
    SELECT ON ticket TO ssbd04mol;

ALTER SEQUENCE ticket_seq OWNER TO ssbd04admin;

GRANT
    USAGE, SELECT ON ticket_seq TO ssbd04mob;

-- Passenger

ALTER TABLE passenger
    OWNER TO ssbd04admin;

GRANT
    SELECT,
    INSERT,
    UPDATE,
    DELETE ON passenger TO ssbd04mob;

GRANT
    SELECT ON passenger TO ssbd04mok;

GRANT
    SELECT ON passenger TO ssbd04mol;

ALTER SEQUENCE passenger_seq OWNER TO ssbd04admin;

GRANT
    USAGE, SELECT ON passenger_seq TO ssbd04mob;

-- SeatClass <-> Benefit

ALTER TABLE seat_class_benefits
    OWNER TO ssbd04admin;

GRANT
    SELECT,
    INSERT,
    UPDATE,
    DELETE ON seat_class_benefits TO ssbd04mol;

GRANT
    SELECT ON seat_class_benefits TO ssbd04mok;

GRANT
    SELECT ON seat_class_benefits TO ssbd04mob;

-- Auth

CREATE VIEW auth_view AS
SELECT login, password, access_level
FROM account a
         INNER JOIN account_access_level b on a.id = b.account_id
WHERE confirm = true;

ALTER VIEW auth_view OWNER TO ssbd04admin;
GRANT SELECT ON auth_view TO ssbd04auth;