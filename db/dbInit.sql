-- Accounts
-- bcrypt(12345678) = $2y$12$WSEHsn4jxmPQAEAHUAPfMOTwRi52jbaBfA0QOoZHvPgkr2CoxQ5sS
DELETE
FROM account_access_level;
DELETE
FROM account;
DELETE
FROM account_details;

INSERT INTO account_details (id, email, first_name, last_name, phone_number, version, modification_date_time,
                             creation_date_time)
VALUES (-1, 'ks@lodz.pl', 'Konrad', 'Stępniak', '123456789', 0, now(), now());
INSERT INTO account (id, active, confirm, login, password, version, account_details_id, modification_date_time,
                     creation_date_time)
VALUES (-1, true, true, 'kstepniak', '$2y$12$WSEHsn4jxmPQAEAHUAPfMOTwRi52jbaBfA0QOoZHvPgkr2CoxQ5sS', 0, -1, now(),
        now());
INSERT INTO account_access_level (access_level, version, account_id, modification_date_time, creation_date_time)
VALUES ('admin', 0, -1, now(), now());
INSERT INTO account_access_level (access_level, version, account_id, modification_date_time, creation_date_time)
VALUES ('manager', 0, -1, now(), now());
INSERT INTO account_access_level (access_level, version, account_id, modification_date_time, creation_date_time)
VALUES ('customer_service', 0, -1, now(), now());
INSERT INTO account_access_level (access_level, version, account_id, modification_date_time, creation_date_time)
VALUES ('client', 0, -1, now(), now());
INSERT INTO account_auth_info (last_ip_address, current_auth, last_success_auth, last_incorrect_auth,
                               incorrect_auth_count, creation_date_time, modification_date_time, version, account_id)
VALUES (null, null, null, null, 0, now(), now(), 0, -1);

INSERT INTO account_details (id, email, first_name, last_name, phone_number, version, modification_date_time,
                             creation_date_time)
VALUES (-2, 'az@lodz.pl', 'Adam', 'Zambrzycki', '123456789', 0, now(), now());
INSERT INTO account (id, active, confirm, login, password, version, account_details_id, modification_date_time,
                     creation_date_time)
VALUES (-2, true, true, 'azambrzycki', '$2y$12$WSEHsn4jxmPQAEAHUAPfMOTwRi52jbaBfA0QOoZHvPgkr2CoxQ5sS', 0, -2, now(),
        now());
INSERT INTO account_access_level (access_level, version, account_id, modification_date_time, creation_date_time)
VALUES ('manager', 0, -2, now(), now());
INSERT INTO account_access_level (access_level, version, account_id, modification_date_time, creation_date_time)
VALUES ('customer_service', 0, -2, now(), now());
INSERT INTO account_access_level (access_level, version, account_id, modification_date_time, creation_date_time)
VALUES ('client', 0, -2, now(), now());
INSERT INTO account_auth_info (last_ip_address, current_auth, last_success_auth, last_incorrect_auth,
                               incorrect_auth_count, creation_date_time, modification_date_time, version, account_id)
VALUES (null, null, null, null, 0, now(), now(), 0, -2);

INSERT INTO account_details (id, email, first_name, last_name, phone_number, version, modification_date_time,
                             creation_date_time)
VALUES (-3, 'js@lodz.pl', 'Jacek', 'Stańczyk', '123456789', 0, now(), now());
INSERT INTO account (id, active, confirm, login, password, version, account_details_id, modification_date_time,
                     creation_date_time)
VALUES (-3, true, true, 'jstanczyk', '$2y$12$WSEHsn4jxmPQAEAHUAPfMOTwRi52jbaBfA0QOoZHvPgkr2CoxQ5sS', 0, -3, now(),
        now());
INSERT INTO account_access_level (access_level, version, account_id, modification_date_time, creation_date_time)
VALUES ('customer_service', 0, -3, now(), now());
INSERT INTO account_access_level (access_level, version, account_id, modification_date_time, creation_date_time)
VALUES ('client', 0, -3, now(), now());
INSERT INTO account_auth_info (last_ip_address, current_auth, last_success_auth, last_incorrect_auth,
                               incorrect_auth_count, creation_date_time, modification_date_time, version, account_id)
VALUES (null, null, null, null, 0, now(), now(), 0, -3);

INSERT INTO account_details (id, email, first_name, last_name, phone_number, version, modification_date_time,
                             creation_date_time)
VALUES (-4, 'jz@lodz.pl', 'Jakub', 'Zygmunt', '123456789', 0, now(), now());
INSERT INTO account (id, active, confirm, login, password, version, account_details_id, modification_date_time,
                     creation_date_time)
VALUES (-4, true, true, 'jzygmunt', '$2y$12$WSEHsn4jxmPQAEAHUAPfMOTwRi52jbaBfA0QOoZHvPgkr2CoxQ5sS', 0, -4, now(),
        now());
INSERT INTO account_access_level (access_level, version, account_id, modification_date_time, creation_date_time)
VALUES ('client', 0, -4, now(), now());
INSERT INTO account_auth_info (last_ip_address, current_auth, last_success_auth, last_incorrect_auth,
                               incorrect_auth_count, creation_date_time, modification_date_time, version, account_id)
VALUES (null, null, null, null, 0, now(), now(), 0, -4);

INSERT INTO account_details (id, email, first_name, last_name, phone_number, version, modification_date_time,
                             creation_date_time)
VALUES (-5, 'jn@lodz.pl', 'Jakub', 'Nozderka', '123456789', 0, now(), now());
INSERT INTO account (id, active, confirm, login, password, version, account_details_id, modification_date_time,
                     creation_date_time)
VALUES (-5, true, true, 'jnozderka', '$2y$12$WSEHsn4jxmPQAEAHUAPfMOTwRi52jbaBfA0QOoZHvPgkr2CoxQ5sS', 0, -5, now(),
        now());
INSERT INTO account_access_level (access_level, version, account_id, modification_date_time, creation_date_time)
VALUES ('admin', 0, -5, now(), now());
INSERT INTO account_access_level (access_level, version, account_id, modification_date_time, creation_date_time)
VALUES ('manager', 0, -5, now(), now());
INSERT INTO account_access_level (access_level, version, account_id, modification_date_time, creation_date_time)
VALUES ('customer_service', 0, -5, now(), now());
INSERT INTO account_auth_info (last_ip_address, current_auth, last_success_auth, last_incorrect_auth,
                               incorrect_auth_count, creation_date_time, modification_date_time, version, account_id)
VALUES (null, null, null, null, 0, now(), now(), 0, -5);

INSERT INTO account_details (id, email, first_name, last_name, phone_number, version, modification_date_time,
                             creation_date_time)
VALUES (-6, 'sz@lodz.pl', 'Stefan', 'Żaryn', '123456789', 0, now(), now());
INSERT INTO account (id, active, confirm, login, password, version, account_details_id, modification_date_time,
                     creation_date_time)
VALUES (-6, true, true, 'szaryn', '$2y$12$WSEHsn4jxmPQAEAHUAPfMOTwRi52jbaBfA0QOoZHvPgkr2CoxQ5sS', 0, -6, now(), now());
INSERT INTO account_access_level (access_level, version, account_id, modification_date_time, creation_date_time)
VALUES ('admin', 0, -6, now(), now());
INSERT INTO account_access_level (access_level, version, account_id, modification_date_time, creation_date_time)
VALUES ('manager', 0, -6, now(), now());
INSERT INTO account_access_level (access_level, version, account_id, modification_date_time, creation_date_time)
VALUES ('customer_service', 0, -6, now(), now());
INSERT INTO account_access_level (access_level, version, account_id, modification_date_time, creation_date_time)
VALUES ('client', 0, -6, now(), now());
INSERT INTO account_auth_info (last_ip_address, current_auth, last_success_auth, last_incorrect_auth,
                               incorrect_auth_count, creation_date_time, modification_date_time, version, account_id)
VALUES (null, null, null, null, 0, now(), now(), 0, -6);

-- Airports
INSERT INTO airport (id, city, code, name, country, version, modification_date_time, creation_date_time) 
VALUES (0, 'Warszawa', 'WSZ', 'Chopina', 'Polska', 0, now(), now())
