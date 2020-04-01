-- bcrypt(12345) = $2a$12$ErwxKPgpDAKFJ/3Eb.4xb.ll/w4.3KYU.RWz0G0NvIjUcwD/WG1ym
DELETE FROM account_access_level;
DELETE FROM account;
DELETE FROM account_details;

INSERT INTO account_details (id, email, first_name, last_name, phone_number, version) VALUES (-1, 'ks@lodz.pl', 'Konrad', 'Stępniak', '123456789', 0);
INSERT INTO account (id, active, confirm, login, password, version, account_details_id) VALUES (-1, true, true, 'ks', '$2a$12$ErwxKPgpDAKFJ/3Eb.4xb.ll/w4.3KYU.RWz0G0NvIjUcwD/WG1ym', 0, -1);
INSERT INTO account_access_level (access_level, version, account_id) VALUES ('admin', 0, -1);
INSERT INTO account_access_level (access_level, version, account_id) VALUES ('manager', 0, -1);
INSERT INTO account_access_level (access_level, version, account_id) VALUES ('customer_service', 0, -1);
INSERT INTO account_access_level (access_level, version, account_id) VALUES ('client', 0, -1);

INSERT INTO account_details (id, email, first_name, last_name, phone_number, version) VALUES (-2, 'az@lodz.pl', 'Adam', 'Zambrzycki', '123456789', 0);
INSERT INTO account (id, active, confirm, login, password, version, account_details_id) VALUES (-2, true, true, 'az', '$2a$12$ErwxKPgpDAKFJ/3Eb.4xb.ll/w4.3KYU.RWz0G0NvIjUcwD/WG1ym', 0, -2);
INSERT INTO account_access_level (access_level, version, account_id) VALUES ('manager', 0, -2);
INSERT INTO account_access_level (access_level, version, account_id) VALUES ('customer_service', 0, -2);
INSERT INTO account_access_level (access_level, version, account_id) VALUES ('client', 0, -2);

INSERT INTO account_details (id, email, first_name, last_name, phone_number, version) VALUES (-3, 'js@lodz.pl', 'Jacek', 'Stańczyk', '123456789', 0);
INSERT INTO account (id, active, confirm, login, password, version, account_details_id) VALUES (-3, true, true, 'js', '$2a$12$ErwxKPgpDAKFJ/3Eb.4xb.ll/w4.3KYU.RWz0G0NvIjUcwD/WG1ym', 0, -3);
INSERT INTO account_access_level (access_level, version, account_id) VALUES ('customer_service', 0, -3);
INSERT INTO account_access_level (access_level, version, account_id) VALUES ('client', 0, -3);

INSERT INTO account_details (id, email, first_name, last_name, phone_number, version) VALUES (-4, 'jz@lodz.pl', 'Jakub', 'Zygmunt', '123456789', 0);
INSERT INTO account (id, active, confirm, login, password, version, account_details_id) VALUES (-4, true, true, 'jz', '$2a$12$ErwxKPgpDAKFJ/3Eb.4xb.ll/w4.3KYU.RWz0G0NvIjUcwD/WG1ym', 0, -4);
INSERT INTO account_access_level (access_level, version, account_id) VALUES ('client', 0, -4);

INSERT INTO account_details (id, email, first_name, last_name, phone_number, version) VALUES (-5, 'jn@lodz.pl', 'Jakub', 'Nozderka', '123456789', 0);
INSERT INTO account (id, active, confirm, login, password, version, account_details_id) VALUES (-5, true, true, 'jn', '$2a$12$ErwxKPgpDAKFJ/3Eb.4xb.ll/w4.3KYU.RWz0G0NvIjUcwD/WG1ym', 0, -5);
INSERT INTO account_access_level (access_level, version, account_id) VALUES ('admin', 0, -5);
INSERT INTO account_access_level (access_level, version, account_id) VALUES ('manager', 0, -5);
INSERT INTO account_access_level (access_level, version, account_id) VALUES ('customer_service', 0, -5);

INSERT INTO account_details (id, email, first_name, last_name, phone_number, version) VALUES (-6, 'sz@lodz.pl', 'Stefan', 'Żaryn', '123456789', 0);
INSERT INTO account (id, active, confirm, login, password, version, account_details_id) VALUES (-6, true, true, 'sz', '$2a$12$ErwxKPgpDAKFJ/3Eb.4xb.ll/w4.3KYU.RWz0G0NvIjUcwD/WG1ym', 0, -6);
INSERT INTO account_access_level (access_level, version, account_id) VALUES ('admin', 0, -6);
INSERT INTO account_access_level (access_level, version, account_id) VALUES ('manager', 0, -6);
INSERT INTO account_access_level (access_level, version, account_id) VALUES ('customer_service', 0, -6);
INSERT INTO account_access_level (access_level, version, account_id) VALUES ('client', 0, -6);
