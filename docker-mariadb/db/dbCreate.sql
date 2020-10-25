CREATE TABLE account
(
    id                     bigint      NOT NULL,
    creation_date_time     datetime    NOT NULL,
    modification_date_time datetime,
    version                bigint,
    active                 boolean     NOT NULL,
    confirm                boolean     NOT NULL,
    login                  varchar(30) NOT NULL,
    password               varchar(60) NOT NULL,
    created_by             bigint,
    modified_by            bigint,
    account_details_id     bigint      NOT NULL
);

create or replace table account_seq
(
    next_val bigint null
);
insert into account_seq (next_val) values (0);

CREATE TABLE account_access_level
(
    access_level           varchar(31) NOT NULL,
    id                     bigint      NOT NULL auto_increment PRIMARY KEY ,
    creation_date_time     datetime    NOT NULL,
    modification_date_time datetime,
    version                bigint,
    created_by             bigint,
    modified_by            bigint,
    account_id             bigint      NOT NULL
);


CREATE TABLE account_details
(
    id                     bigint       NOT NULL auto_increment primary key ,
    creation_date_time     datetime     NOT NULL,
    modification_date_time datetime,
    version                bigint,
    email                  varchar(255) NOT NULL,
    first_name             varchar(30)  NOT NULL,
    last_name              varchar(30)  NOT NULL,
    phone_number           varchar(15)  NOT NULL,
    created_by             bigint,
    modified_by            bigint
);


create or replace table account_details_seq
(
    next_val bigint null
);

CREATE TABLE account_auth_info
(
    id                     bigint   NOT NULL AUTO_INCREMENT PRIMARY KEY ,
    last_ip_address        varchar(45),
    current_auth           datetime,
    last_success_auth      datetime,
    last_incorrect_auth    datetime,
    incorrect_auth_count   integer  NOT NULL,
    creation_date_time     datetime NOT NULL,
    modification_date_time datetime,
    version                bigint,
    account_id             bigint   NOT NULL,
    created_by             bigint,
    modified_by            bigint
);

CREATE TABLE airplane_schema
(
    id                     bigint   NOT NULL primary key auto_increment,
    creation_date_time     datetime NOT NULL,
    modification_date_time datetime,
    version                bigint,
    cols                   integer  NOT NULL,
    `rows`                   integer  NOT NULL,
    name                   varchar(45),
    empty_columns          varchar(45),
    empty_rows             varchar(45),
    created_by             bigint,
    modified_by            bigint,
    CONSTRAINT airplane_schema_cols_check CHECK ((cols >= 1)),
    CONSTRAINT airplane_schema_rows_check CHECK ((`rows` >= 1))
);

CREATE TABLE airport
(
    id                     bigint       NOT NULL auto_increment primary key ,
    creation_date_time     datetime     NOT NULL,
    modification_date_time datetime,
    version                bigint,
    city                   varchar(32)  NOT NULL,
    code                   varchar(255) NOT NULL,
    country                varchar(32)  NOT NULL,
    name                   varchar(32)  NOT NULL,
    created_by             bigint,
    modified_by            bigint
);


CREATE TABLE benefit
(
    id                     bigint       NOT NULL auto_increment primary key ,
    creation_date_time     datetime     NOT NULL,
    modification_date_time datetime,
    version                bigint,
    description            varchar(255) NOT NULL,
    name                   varchar(128) NOT NULL,
    created_by             bigint,
    modified_by            bigint
);


CREATE TABLE connection
(
    id                     bigint        NOT NULL primary key auto_increment,
    creation_date_time     datetime      NOT NULL,
    modification_date_time datetime,
    version                bigint,
    base_price             numeric(9, 2) NOT NULL,
    destination_id         bigint        NOT NULL,
    source_id              bigint        NOT NULL,
    created_by             bigint,
    modified_by            bigint
);


ALTER TABLE connection
    ADD CONSTRAINT connection_unique UNIQUE (destination_id, source_id);

CREATE TABLE connection_stats
(
    connection_id bigint        NOT NULL,
    profit        numeric(9, 2) NOT NULL
);

CREATE TABLE flight
(
    id                     bigint        NOT NULL,
    creation_date_time     datetime      NOT NULL,
    modification_date_time datetime,
    version                bigint,
    end_date_time          datetime      NOT NULL,
    flight_code            varchar(30)   NOT NULL,
    price                  numeric(9, 2) NOT NULL,
    start_date_time        datetime      NOT NULL,
    status                 varchar(64)   NOT NULL,
    airplane_schema_id     bigint        NOT NULL,
    connection_id          bigint        NOT NULL,
    created_by             bigint,
    modified_by            bigint
);

create or replace table flight_seq
(
    next_val bigint null
);
insert into flight_seq (next_val) values (0);

CREATE TABLE passenger
(
    id                     bigint       NOT NULL,
    creation_date_time     datetime     NOT NULL,
    modification_date_time datetime,
    version                bigint,
    email                  varchar(255) NOT NULL,
    first_name             varchar(30)  NOT NULL,
    last_name              varchar(30)  NOT NULL,
    phone_number           varchar(15)  NOT NULL,
    seat_id                bigint       NOT NULL,
    ticket_id              bigint       NOT NULL,
    flight_id              bigint       NOT NULL,
    created_by             bigint,
    modified_by            bigint
);



create or replace table passenger_seq
(
    next_val bigint null
);
insert into passenger_seq (next_val) values (0);

CREATE TABLE seat
(
    id                     bigint   NOT NULL primary key auto_increment,
    creation_date_time     datetime NOT NULL,
    modification_date_time datetime,
    version                bigint,
    col                    integer  NOT NULL,
    `row`                  integer  NOT NULL,
    airplane_schema_id     bigint   NOT NULL,
    seat_class_id          bigint   NOT NULL,
    created_by             bigint,
    modified_by            bigint
);


CREATE TABLE seat_class
(
    id                     bigint        NOT NULL auto_increment primary key ,
    creation_date_time     datetime      NOT NULL,
    modification_date_time datetime,
    version                bigint,
    name                   varchar(30)   NOT NULL,
    price                  numeric(9, 2) NOT NULL,
    color                  varchar(64)   NOT NULL,
    created_by             bigint,
    modified_by            bigint
);


CREATE TABLE seat_class_benefits
(
    seat_class_id bigint NOT NULL,
    benefit_id    bigint NOT NULL
);


CREATE TABLE ticket
(
    id                     bigint        NOT NULL,
    creation_date_time     datetime      NOT NULL,
    modification_date_time datetime,
    version                bigint,
    total_price            numeric(9, 2) NOT NULL,
    account_id             bigint        NOT NULL,
    flight_id              bigint        NOT NULL,
    created_by             bigint,
    modified_by            bigint
);

create or replace table ticket_seq
(
    next_val bigint null
);
insert into ticket_seq (next_val) values (0);


CREATE TABLE verification_token
(
    type                   varchar(31) NOT NULL,
    id                     varchar(36) NOT NULL,
    creation_date_time     datetime    NOT NULL,
    modification_date_time datetime,
    version                bigint,
    expire_date_time       datetime    NOT NULL,
    account_id             bigint      NOT NULL,
    created_by             bigint,
    modified_by            bigint
);

-- Constraints Primary Keys

ALTER TABLE  account
    ADD CONSTRAINT account_account_details_id_unique UNIQUE (account_details_id);

ALTER TABLE  account_auth_info
    ADD CONSTRAINT account_auth_info_account_id_unique UNIQUE (account_id);

ALTER TABLE  account_details
    ADD CONSTRAINT account_details_email_unique UNIQUE (email);

ALTER TABLE  account
    ADD CONSTRAINT account_login_unique UNIQUE (login);

ALTER TABLE account
    ADD CONSTRAINT account_pkey PRIMARY KEY (id);

ALTER TABLE airplane_schema
    ADD CONSTRAINT airplane_schema_name_unique UNIQUE (name);

ALTER TABLE  airport
    ADD CONSTRAINT airport_code_unique UNIQUE (code);

ALTER TABLE  benefit
    ADD CONSTRAINT benefit_name_unique UNIQUE (name);

ALTER TABLE  connection_stats
    ADD CONSTRAINT connection_id PRIMARY KEY (connection_id);

ALTER TABLE  flight
    ADD CONSTRAINT flight_flight_code_unique UNIQUE (flight_code);

ALTER TABLE  flight
    ADD CONSTRAINT flight_pkey PRIMARY KEY (id);

ALTER TABLE  passenger
    ADD CONSTRAINT passenger_pkey PRIMARY KEY (id);

ALTER TABLE  passenger
    ADD CONSTRAINT passenger_seat_taken UNIQUE (flight_id, seat_id);

ALTER TABLE  seat_class_benefits
    ADD CONSTRAINT seat_class_benefits_pkey PRIMARY KEY (seat_class_id, benefit_id);

ALTER TABLE  seat_class
    ADD CONSTRAINT seat_class_name_unique UNIQUE (name);

ALTER TABLE  ticket
    ADD CONSTRAINT ticket_pkey PRIMARY KEY (id);

ALTER TABLE  verification_token
    ADD CONSTRAINT verification_token_pkey PRIMARY KEY (id);

-- Foreign Keys

ALTER TABLE  account
    ADD CONSTRAINT account_account_account_details_fk FOREIGN KEY (account_details_id) REFERENCES account_details (id);

ALTER TABLE  account
    ADD CONSTRAINT account_created_by_fk FOREIGN KEY (created_by) REFERENCES account (id);

ALTER TABLE  account
    ADD CONSTRAINT account_modified_by_fk FOREIGN KEY (modified_by) REFERENCES account (id);

ALTER TABLE  account_access_level
    ADD CONSTRAINT account_access_level_account_account_access_level_fk FOREIGN KEY (account_id) REFERENCES account (id);

ALTER TABLE  account_access_level
    ADD CONSTRAINT account_access_level_created_by_fk FOREIGN KEY (created_by) REFERENCES account (id);

ALTER TABLE  account_access_level
    ADD CONSTRAINT account_access_level_modified_by_fk FOREIGN KEY (modified_by) REFERENCES account (id);

ALTER TABLE  account_auth_info
    ADD CONSTRAINT account_auth_info_account_auth_info_account_fk FOREIGN KEY (account_id) REFERENCES account (id);

ALTER TABLE  account_auth_info
    ADD CONSTRAINT created_by_fk FOREIGN KEY (created_by) REFERENCES account (id);

ALTER TABLE  account_auth_info
    ADD CONSTRAINT modified_by_fk FOREIGN KEY (modified_by) REFERENCES account (id);

ALTER TABLE
    connection
        ADD CONSTRAINT connection_airport_dst_fk FOREIGN KEY (destination_id) REFERENCES airport (id);

ALTER TABLE
    connection
        ADD CONSTRAINT connection_airport_src_fk FOREIGN KEY (source_id) REFERENCES airport (id);

ALTER TABLE
    connection
        ADD CONSTRAINT connection_created_by_fk FOREIGN KEY (created_by) REFERENCES account (id);

ALTER TABLE
    connection
        ADD CONSTRAINT connection_connection_modified_by_fk FOREIGN KEY (modified_by) REFERENCES account (id);

ALTER TABLE  connection_stats
    ADD CONSTRAINT connection_stats_connection_stats_connection_connection_fk FOREIGN KEY (connection_id) REFERENCES connection (id);

ALTER TABLE  seat_class_benefits
    ADD CONSTRAINT seat_class_benefits_seat_class_fk FOREIGN KEY (seat_class_id) REFERENCES seat_class (id);

ALTER TABLE  seat_class_benefits
    ADD CONSTRAINT seat_class_benefits_benefit_fk FOREIGN KEY (benefit_id) REFERENCES benefit (id);

ALTER TABLE  flight
    ADD CONSTRAINT flight_flight_airplane_schema_fk FOREIGN KEY (airplane_schema_id) REFERENCES airplane_schema (id);

ALTER TABLE  flight
    ADD CONSTRAINT flight_flight_connection_fk FOREIGN KEY (connection_id) REFERENCES connection (id);

ALTER TABLE  flight
    ADD CONSTRAINT flight_created_by_fk FOREIGN KEY (created_by) REFERENCES account (id);

ALTER TABLE  flight
    ADD CONSTRAINT flight_modified_by_fk FOREIGN KEY (modified_by) REFERENCES account (id);

ALTER TABLE  passenger
    ADD CONSTRAINT passenger_passenger_seat_fk FOREIGN KEY (seat_id) REFERENCES seat (id);

ALTER TABLE  passenger
    ADD CONSTRAINT passenger_passenger_ticket_fk FOREIGN KEY (ticket_id) REFERENCES ticket (id);

ALTER TABLE  passenger
    ADD CONSTRAINT passenger_created_by_fk FOREIGN KEY (created_by) REFERENCES account (id);

ALTER TABLE  passenger
    ADD CONSTRAINT passenger_modified_by_fk FOREIGN KEY (modified_by) REFERENCES account (id);

ALTER TABLE  seat
    ADD CONSTRAINT seat_seat_airplane_schema_fk FOREIGN KEY (airplane_schema_id) REFERENCES airplane_schema (id);

ALTER TABLE  seat
    ADD CONSTRAINT seat_seat_seat_class_fk FOREIGN KEY (seat_class_id) REFERENCES seat_class (id);

ALTER TABLE  seat
    ADD CONSTRAINT seat_created_by_fk FOREIGN KEY (created_by) REFERENCES account (id);

ALTER TABLE  seat
    ADD CONSTRAINT seat_modified_by_fk FOREIGN KEY (modified_by) REFERENCES account (id);

ALTER TABLE  ticket
    ADD CONSTRAINT ticket_ticket_account_fk FOREIGN KEY (account_id) REFERENCES account (id);

ALTER TABLE  ticket
    ADD CONSTRAINT ticket_ticket_flight_fk FOREIGN KEY (flight_id) REFERENCES flight (id);

ALTER TABLE  ticket
    ADD CONSTRAINT ticket_created_by_fk FOREIGN KEY (created_by) REFERENCES account (id);

ALTER TABLE  ticket
    ADD CONSTRAINT ticket_modified_by_fk FOREIGN KEY (modified_by) REFERENCES account (id);

ALTER TABLE  verification_token
    ADD CONSTRAINT verification_token_verification_token_account_fk FOREIGN KEY (account_id) REFERENCES account (id);

ALTER TABLE  verification_token
    ADD CONSTRAINT verification_token_created_by_fk FOREIGN KEY (created_by) REFERENCES account (id);

ALTER TABLE  verification_token
    ADD CONSTRAINT verification_token_modified_by_fk FOREIGN KEY (modified_by) REFERENCES account (id);


-- Indexes

CREATE INDEX account_account_details_fk ON account(account_details_id) using btree ;

CREATE INDEX account_details_created_by_fk ON account_details(created_by) USING btree;

CREATE INDEX account_details_modified_by_fk ON account_details (modified_by) USING btree;

CREATE INDEX account_account_access_level_fk ON account_access_level (account_id) USING btree;

CREATE INDEX account_access_level_created_by_fk ON account_access_level (created_by) USING btree;

CREATE INDEX account_access_level_modified_by_fk ON account_access_level (modified_by) USING btree;

CREATE INDEX account_auth_info_account_fk ON account_auth_info (account_id) USING btree;

CREATE INDEX account_auth_info_created_by_fk ON account_auth_info (created_by) USING btree;

CREATE INDEX account_auth_info_modified_by_fk ON account_auth_info (modified_by) USING btree;

CREATE INDEX connection_airport_dst_fk ON connection (destination_id) USING btree;

CREATE INDEX connection_airport_src_fk ON connection (source_id) USING btree;

CREATE INDEX connection_created_by_fk ON connection (created_by) USING btree;

CREATE INDEX connection_modified_by_fk ON connection (modified_by) USING btree;

CREATE INDEX flight_airplane_schema_fk ON flight (airplane_schema_id) USING btree;

CREATE INDEX flight_connection_fk ON flight (connection_id) USING btree;

CREATE INDEX flight_created_by_fk ON flight (created_by) USING btree;

CREATE INDEX flight_modified_by_fk ON flight (modified_by) USING btree;

CREATE INDEX passenger_seat_fk ON passenger (seat_id) USING btree;

CREATE INDEX passenger_ticket_fk ON passenger (ticket_id) USING btree;

CREATE INDEX passenger_created_by_fk ON passenger (created_by) USING btree;

CREATE INDEX passenger_modified_by_fk ON passenger (modified_by) USING btree;

CREATE INDEX seat_airplane_schema_fk ON seat (airplane_schema_id) USING btree;

CREATE INDEX seat_seat_class_fk ON seat (seat_class_id) USING btree;

CREATE INDEX seat_created_by_fk ON seat (created_by) USING btree;

CREATE INDEX seat_modified_by_fk ON seat (modified_by) USING btree;

CREATE INDEX benefit_fk ON seat_class_benefits (benefit_id) USING btree;

CREATE INDEX seat_class_fk ON seat_class_benefits (seat_class_id) USING btree;

CREATE INDEX ticket_account_fk ON ticket (account_id) USING btree;

CREATE INDEX ticket_flight_fk ON ticket (flight_id) USING btree;

CREATE INDEX ticket_created_by_fk ON ticket (created_by) USING btree;

CREATE INDEX ticket_modified_by_fk ON ticket (modified_by) USING btree;

CREATE INDEX verification_token_account_fk ON verification_token (account_id) USING btree;

CREATE INDEX verification_token_created_by_fk ON verification_token (created_by) USING btree;

CREATE INDEX verification_token_modified_by_fk ON verification_token (modified_by) USING btree;

-- AccountDetails

GRANT
    SELECT,
        INSERT,
        UPDATE,
        DELETE ON account_details TO ssbd04mok@'%';
GRANT
    SELECT ON account_details TO ssbd04mol@'%';

GRANT
    SELECT ON account_details TO ssbd04mob@'%';

GRANT
    USAGE, SELECT ON account_details_seq TO ssbd04mok@'%';

GRANT
    SELECT ON account_details_seq TO ssbd04mob@'%';

GRANT
    SELECT ON account_details_seq TO ssbd04mol@'%';

-- AccountAuthInfo

GRANT
    SELECT,
        INSERT,
        UPDATE,
        DELETE ON account_auth_info TO ssbd04mok@'%';

GRANT
    SELECT ON account_auth_info TO ssbd04mol@'%';

GRANT
    SELECT ON account_auth_info TO ssbd04mob@'%';

-- Account

GRANT
    SELECT,
        INSERT,
        UPDATE,
        DELETE ON account TO ssbd04mok@'%';

GRANT
    SELECT ON account TO ssbd04mol@'%';

GRANT
    SELECT ON account TO ssbd04mob@'%';


GRANT
    USAGE, SELECT, INSERT, UPDATE ON account_seq TO ssbd04mok@'%';

GRANT
    SELECT ON account_seq TO ssbd04mob@'%';

GRANT
    SELECT ON account_seq TO ssbd04mol@'%';

-- VerificationToken

GRANT
    SELECT,
        INSERT,
        DELETE ON verification_token TO ssbd04mok@'%';

-- AccountAccessLevel

GRANT
    SELECT,
        INSERT,
        UPDATE,
        DELETE ON account_access_level TO ssbd04mok@'%';

GRANT
    SELECT ON account_access_level TO ssbd04mol@'%';

GRANT
    SELECT ON account_access_level TO ssbd04mob@'%';

-- AirplaneSchema

GRANT
    SELECT,
        INSERT,
        UPDATE,
        DELETE ON airplane_schema TO ssbd04mol@'%';

GRANT
    SELECT ON airplane_schema TO ssbd04mok@'%';

GRANT
    SELECT ON airplane_schema TO ssbd04mob@'%';


-- Airport

GRANT
    SELECT,
        INSERT,
        UPDATE,
        DELETE ON airport TO ssbd04mol@'%';

GRANT
    SELECT ON airport TO ssbd04mok@'%';

GRANT
    SELECT ON airport TO ssbd04mob@'%';


-- Connection

GRANT
    SELECT,
        INSERT,
        UPDATE,
        DELETE ON connection TO ssbd04mol@'%';

GRANT
    SELECT,
        INSERT,
        UPDATE,
        DELETE ON connection_stats TO ssbd04mol@'%';

GRANT
    SELECT ON connection TO ssbd04mok@'%';

GRANT
    SELECT,
        UPDATE
        ON connection TO ssbd04mob@'%';

GRANT
    SELECT ON connection_stats TO ssbd04mok@'%';

GRANT
    SELECT,
        UPDATE
        ON connection_stats TO ssbd04mob@'%';


-- Flight

GRANT
    SELECT,
        INSERT,
        UPDATE,
        DELETE ON flight TO ssbd04mol@'%';

GRANT
    SELECT ON flight TO ssbd04mok@'%';

GRANT
    SELECT ON flight TO ssbd04mob@'%';

GRANT UPDATE (version) ON flight to ssbd04mob@'%';


GRANT
    USAGE, SELECT, INSERT, UPDATE ON flight_seq TO ssbd04mol@'%';

GRANT
    SELECT ON flight_seq TO ssbd04mob@'%';

-- Benefit

GRANT
    SELECT,
        INSERT,
        UPDATE,
        DELETE ON benefit TO ssbd04mol@'%';

GRANT
    SELECT ON benefit TO ssbd04mok@'%';

GRANT
    SELECT ON benefit TO ssbd04mob@'%';

-- SeatClass

GRANT
    SELECT,
        INSERT,
        UPDATE,
        DELETE ON seat_class TO ssbd04mol@'%';

GRANT
    SELECT ON seat_class TO ssbd04mok@'%';

GRANT
    SELECT ON seat_class TO ssbd04mob@'%';


-- Seat


GRANT
    SELECT,
        INSERT,
        UPDATE,
        DELETE ON seat TO ssbd04mol@'%';

GRANT
    SELECT ON seat TO ssbd04mok@'%';

GRANT
    SELECT, UPDATE ON seat TO ssbd04mob@'%';


-- Ticket

GRANT
    SELECT,
        INSERT,
        UPDATE,
        DELETE ON ticket TO ssbd04mob@'%';

GRANT
    SELECT ON ticket TO ssbd04mok@'%';

GRANT
    SELECT ON ticket TO ssbd04mol@'%';


GRANT
    USAGE, SELECT, INSERT, UPDATE ON ticket_seq TO ssbd04mob@'%';

GRANT
    SELECT ON ticket_seq TO ssbd04mol@'%';

-- Passenger

GRANT
    SELECT,
        INSERT,
        UPDATE,
        DELETE ON passenger TO ssbd04mob@'%';

GRANT
    SELECT ON passenger TO ssbd04mok@'%';

GRANT
    SELECT ON passenger TO ssbd04mol@'%';

GRANT
    USAGE, SELECT, INSERT, UPDATE ON passenger_seq TO ssbd04mob@'%';

GRANT
    SELECT ON passenger_seq TO ssbd04mol@'%';

-- SeatClass <-> Benefit

GRANT
    SELECT,
        INSERT,
        UPDATE,
        DELETE ON seat_class_benefits TO ssbd04mol@'%';

GRANT
    SELECT ON seat_class_benefits TO ssbd04mok@'%';

GRANT
    SELECT ON seat_class_benefits TO ssbd04mob@'%';

-- Auth

CREATE VIEW auth_view AS
SELECT login, password, access_level
FROM account a
         INNER JOIN account_access_level b on a.id = b.account_id
WHERE confirm = true;

GRANT SELECT ON auth_view TO ssbd04auth@'%';
