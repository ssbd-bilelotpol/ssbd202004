-- Accounts
-- bcrypt(12345678) = $2y$12$WSEHsn4jxmPQAEAHUAPfMOTwRi52jbaBfA0QOoZHvPgkr2CoxQ5sS
DELETE
FROM account_access_level;
DELETE
FROM account;
DELETE
FROM account_details;

INSERT INTO account_details (id, email, first_name, last_name, phone_number, version, modification_date_time, creation_date_time)
VALUES (-1, 'ks@lodz.pl', 'Konrad', 'Stępniak', '123456789', 0, now(), now());
INSERT INTO account (id, active, confirm, login, password, version, account_details_id, modification_date_time, creation_date_time)
VALUES (-1, true, true, 'kstepniak', '$2y$12$WSEHsn4jxmPQAEAHUAPfMOTwRi52jbaBfA0QOoZHvPgkr2CoxQ5sS', 0, -1, now(), now());
INSERT INTO account_access_level (access_level, version, account_id, modification_date_time, creation_date_time)
VALUES ('admin', 0, -1, now(), now());
INSERT INTO account_access_level (access_level, version, account_id, modification_date_time, creation_date_time)
VALUES ('manager', 0, -1, now(), now());
INSERT INTO account_access_level (access_level, version, account_id, modification_date_time, creation_date_time)
VALUES ('customer_service', 0, -1, now(), now());
INSERT INTO account_access_level (access_level, version, account_id, modification_date_time, creation_date_time)
VALUES ('client', 0, -1, now(), now());
INSERT INTO account_auth_info (last_ip_address, current_auth, last_success_auth, last_incorrect_auth, incorrect_auth_count, creation_date_time, modification_date_time, version, account_id)
VALUES (null, null, null, null, 0, now(), now(), 0, -1);

INSERT INTO account_details (id, email, first_name, last_name, phone_number, version, modification_date_time, creation_date_time)
VALUES (-2, 'az@lodz.pl', 'Adam', 'Zambrzycki', '123456789', 0, now(), now());
INSERT INTO account (id, active, confirm, login, password, version, account_details_id, modification_date_time, creation_date_time)
VALUES (-2, true, true, 'azambrzycki', '$2y$12$WSEHsn4jxmPQAEAHUAPfMOTwRi52jbaBfA0QOoZHvPgkr2CoxQ5sS', 0, -2, now(), now());
INSERT INTO account_access_level (access_level, version, account_id, modification_date_time, creation_date_time)
VALUES ('manager', 0, -2, now(), now());
INSERT INTO account_access_level (access_level, version, account_id, modification_date_time, creation_date_time)
VALUES ('customer_service', 0, -2, now(), now());
INSERT INTO account_access_level (access_level, version, account_id, modification_date_time, creation_date_time)
VALUES ('client', 0, -2, now(), now());
INSERT INTO account_auth_info (last_ip_address, current_auth, last_success_auth, last_incorrect_auth, incorrect_auth_count, creation_date_time, modification_date_time, version, account_id)
VALUES (null, null, null, null, 0, now(), now(), 0, -2);

INSERT INTO account_details (id, email, first_name, last_name, phone_number, version, modification_date_time, creation_date_time)
VALUES (-3, 'js@lodz.pl', 'Jacek', 'Stańczyk', '123456789', 0, now(), now());
INSERT INTO account (id, active, confirm, login, password, version, account_details_id, modification_date_time, creation_date_time)
VALUES (-3, true, true, 'jstanczyk', '$2y$12$WSEHsn4jxmPQAEAHUAPfMOTwRi52jbaBfA0QOoZHvPgkr2CoxQ5sS', 0, -3, now(), now());
INSERT INTO account_access_level (access_level, version, account_id, modification_date_time, creation_date_time)
VALUES ('customer_service', 0, -3, now(), now());
INSERT INTO account_access_level (access_level, version, account_id, modification_date_time, creation_date_time)
VALUES ('client', 0, -3, now(), now());
INSERT INTO account_auth_info (last_ip_address, current_auth, last_success_auth, last_incorrect_auth, incorrect_auth_count, creation_date_time, modification_date_time, version, account_id)
VALUES (null, null, null, null, 0, now(), now(), 0, -3);

INSERT INTO account_details (id, email, first_name, last_name, phone_number, version, modification_date_time, creation_date_time)
VALUES (-4, 'jz@lodz.pl', 'Jakub', 'Zygmunt', '123456789', 0, now(), now());
INSERT INTO account (id, active, confirm, login, password, version, account_details_id, modification_date_time, creation_date_time)
VALUES (-4, true, true, 'jzygmunt', '$2y$12$WSEHsn4jxmPQAEAHUAPfMOTwRi52jbaBfA0QOoZHvPgkr2CoxQ5sS', 0, -4, now(), now());
INSERT INTO account_access_level (access_level, version, account_id, modification_date_time, creation_date_time)
VALUES ('client', 0, -4, now(), now());
INSERT INTO account_auth_info (last_ip_address, current_auth, last_success_auth, last_incorrect_auth, incorrect_auth_count, creation_date_time, modification_date_time, version, account_id)
VALUES (null, null, null, null, 0, now(), now(), 0, -4);

INSERT INTO account_details (id, email, first_name, last_name, phone_number, version, modification_date_time, creation_date_time)
VALUES (-5, 'jn@lodz.pl', 'Jakub', 'Nozderka', '123456789', 0, now(), now());
INSERT INTO account (id, active, confirm, login, password, version, account_details_id, modification_date_time, creation_date_time)
VALUES (-5, true, true, 'jnozderka', '$2y$12$WSEHsn4jxmPQAEAHUAPfMOTwRi52jbaBfA0QOoZHvPgkr2CoxQ5sS', 0, -5, now(), now());
INSERT INTO account_access_level (access_level, version, account_id, modification_date_time, creation_date_time)
VALUES ('admin', 0, -5, now(), now());
INSERT INTO account_access_level (access_level, version, account_id, modification_date_time, creation_date_time)
VALUES ('manager', 0, -5, now(), now());
INSERT INTO account_access_level (access_level, version, account_id, modification_date_time, creation_date_time)
VALUES ('customer_service', 0, -5, now(), now());
INSERT INTO account_auth_info (last_ip_address, current_auth, last_success_auth, last_incorrect_auth, incorrect_auth_count, creation_date_time, modification_date_time, version, account_id)
VALUES (null, null, null, null, 0, now(), now(), 0, -5);

INSERT INTO account_details (id, email, first_name, last_name, phone_number, version, modification_date_time, creation_date_time)
VALUES (-6, 'sz@lodz.pl', 'Stefan', 'Żaryn', '123456789', 0, now(), now());
INSERT INTO account (id, active, confirm, login, password, version, account_details_id, modification_date_time, creation_date_time)
VALUES (-6, true, true, 'szaryn', '$2y$12$WSEHsn4jxmPQAEAHUAPfMOTwRi52jbaBfA0QOoZHvPgkr2CoxQ5sS', 0, -6, now(), now());
INSERT INTO account_access_level (access_level, version, account_id, modification_date_time, creation_date_time)
VALUES ('admin', 0, -6, now(), now());
INSERT INTO account_access_level (access_level, version, account_id, modification_date_time, creation_date_time)
VALUES ('manager', 0, -6, now(), now());
INSERT INTO account_access_level (access_level, version, account_id, modification_date_time, creation_date_time)
VALUES ('customer_service', 0, -6, now(), now());
INSERT INTO account_access_level (access_level, version, account_id, modification_date_time, creation_date_time)
VALUES ('client', 0, -6, now(), now());
INSERT INTO account_auth_info (last_ip_address, current_auth, last_success_auth, last_incorrect_auth, incorrect_auth_count, creation_date_time, modification_date_time, version, account_id)
VALUES (null, null, null, null, 0, now(), now(), 0, -6);

--SeatClasses

INSERT INTO seat_class (name, price, color, creation_date_time, modification_date_time,  created_by, version)
VALUES ('Klasa ekonomiczna', 100.00, 'BLUE', now(), null, -1, 0);
INSERT INTO seat_class (name, price, color, creation_date_time, modification_date_time,  created_by, version)
VALUES ('Klasa premium', 200.00, 'RED', now(), null, -1, 0);
INSERT INTO seat_class (name, price, color, creation_date_time, modification_date_time,  created_by, version)
VALUES ('Klasa biznes', 300.00, 'PURPLE', now(), null, -1, 0);

--Benefits

INSERT INTO benefit (creation_date_time, modification_date_time, version, description, name, created_by, modified_by)
VALUES (now(), null, 0, 'Rozszerzone menu baru pokładowego', 'Sky bar', -1, null);
INSERT INTO benefit (creation_date_time, modification_date_time, version, description, name, created_by, modified_by)
VALUES (now(), null, 0, 'Darmowy dostęp do filmów z oferty', 'Pakiet telewizyjny', -1, null);

--SeatClassBenefits

INSERT INTO seat_class_benefits (seat_class_id, benefit_id) VALUES (2, 2);
INSERT INTO seat_class_benefits (seat_class_id, benefit_id) VALUES (3, 1);
INSERT INTO seat_class_benefits (seat_class_id, benefit_id) VALUES (3, 2);

-- Airports
INSERT INTO airport (city, code, name, country, version, modification_date_time, creation_date_time)
VALUES ('Warszawa', 'WSZ', 'Chopina', 'PL', 0, now(), now());

INSERT INTO airport (city, code, name, country, version, modification_date_time, creation_date_time)
VALUES ('Łódź', 'LDZ', 'Reymonta', 'PL', 0, now(), now());

INSERT INTO airport (city, code, name, country, version, modification_date_time, creation_date_time)
VALUES ('Radom', 'RDM', 'Port lotniczy', 'PL', 0, now(), now());

INSERT INTO airport (city, code, name, country, version, modification_date_time, creation_date_time)
VALUES ('Londyn', 'LDN', 'Heathrow', 'GB', 0, now(), now());

-- Connections
INSERT INTO connection (id, creation_date_time, modification_date_time, version, base_price, destination_id, source_id)
VALUES (-1, now(), now(), 0, 150, 1, 2);
INSERT INTO connection (id, creation_date_time, modification_date_time, version, base_price, destination_id, source_id)
VALUES (-2, now(), now(), 0, 150, 2, 4);
INSERT INTO connection (id, creation_date_time, modification_date_time, version, base_price, destination_id, source_id)
VALUES (-3, now(), now(), 0, 150, 1, 4);
INSERT INTO connection (id, creation_date_time, modification_date_time, version, base_price, destination_id, source_id)
VALUES (-4, now(), now(), 0, 150, 3, 2);