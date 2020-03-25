CREATE EXTENSION IF NOT EXISTS plpgsql WITH SCHEMA pg_catalog;
CREATE SCHEMA IF NOT EXISTS public;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SET check_function_bodies = false;
SET client_min_messages = warning;
SET search_path = public, pg_catalog;
SET default_tablespace = '';
SET default_with_oids = false;

DROP INDEX IF EXISTS access_level_mapping_index;
DROP VIEW IF EXISTS auth_view;
DROP TABLE IF EXISTS access_level_mapping;
DROP TABLE IF EXISTS account_info;
DROP TABLE IF EXISTS account;
DROP TABLE IF EXISTS access_level;


CREATE TABLE account (
    id SERIAL PRIMARY KEY,
    login VARCHAR(16) UNIQUE NOT NULL,
    password VARCHAR(60) NOT NULL,
	confirm BOOLEAN DEFAULT false NOT NULL,
    active BOOLEAN DEFAULT false NOT NULL
);
ALTER TABLE account OWNER TO ssbd04admin;

CREATE TABLE account_info (
    id SERIAL PRIMARY KEY REFERENCES account (id),
    firstname VARCHAR(32) NOT NULL,
    lastname VARCHAR(32) NOT NULL
);
ALTER TABLE account_info OWNER TO ssbd04admin;


CREATE TABLE access_level(
	id SERIAL PRIMARY KEY,
	name VARCHAR(50) UNIQUE NOT NULL
);
ALTER TABLE access_level OWNER TO ssbd04admin;


CREATE TABLE access_level_mapping(
	account_id INT NOT NULL,
  	access_level_id INT NOT NULL,
	PRIMARY KEY (account_id, access_level_id),
	CONSTRAINT access_level_mapping_account_id_fkey FOREIGN KEY (account_id)
    	REFERENCES account (id) MATCH FULL
    	ON UPDATE CASCADE ON DELETE CASCADE,
  	CONSTRAINT ccess_level_mapping_access_level_id_fkey FOREIGN KEY (access_level_id)
    	REFERENCES access_level (id) MATCH FULL
    	ON UPDATE CASCADE ON DELETE CASCADE
);
ALTER TABLE access_level_mapping OWNER TO ssbd04admin;


CREATE VIEW auth_view AS
	SELECT DISTINCT a.id AS user_id, a.login, a.password, al.name AS access_level, a.confirm FROM account AS a
		INNER JOIN access_level_mapping AS alm ON a.id = alm.account_id
		INNER JOIN access_level AS al ON alm.access_level_id = al.id
	WHERE a.active = true;
ALTER TABLE auth_view OWNER TO ssbd04admin;

CREATE INDEX access_level_mapping_account_id ON access_level_mapping USING btree (account_id);


REVOKE ALL ON TABLE auth_view FROM ssbd04mok;
REVOKE ALL ON TABLE auth_view FROM ssbd04payara;
GRANT ALL ON TABLE auth_view TO ssbd04mok;
GRANT SELECT ON TABLE auth_view TO ssbd04payara;
GRANT ALL ON SCHEMA public TO ssbd04admin;


-- password bcrypt(f1f29o0w685z6b5y6)
INSERT INTO account (login, password, confirm, active) VALUES ('Client1', '$2y$12$q7/eTgBgHgZYNk8yKAMS0ezbDzPHxL6l1Q4ui0Yw4TMoa7E/b9VvK', true, true);
INSERT INTO account_info(firstname, lastname) VALUES ('Jakub', 'Kowalski');

-- password bcrypt(2xhbrh0ooxhwgt4p2)
INSERT INTO account (login, password, confirm, active) VALUES ('Manager1', '$2y$12$vp5v.En/lfvGBAk6H5.L9.MW.q.KUJf69RtWAMMj7T1EZW7n1CsHW', true, true);
INSERT INTO account_info(firstname, lastname) VALUES ('Stefan', 'Nowak');

-- password bcrypt(vtxnj5etdu2804k9t)
INSERT INTO account (login, password, confirm, active) VALUES ('ClientService1', '$2y$12$tpzK8T/h3P0iOFpfMqw3Be6EAGQYUJvJa8G/DZ161jtAkL8wIKAeq', true, true);
INSERT INTO account_info(firstname, lastname) VALUES ('Konrad', 'Polkowski');

-- password bcrypt(2l0euu8w1b84vofp9)
INSERT INTO account (login, password, confirm, active) VALUES ('Admin1', '$2y$12$nKN08oyOQWUJ.BjIWzkSw.6wujFzyLnRaOnsHE.Lwj/hMHd73Jumm', false, true);
INSERT INTO account_info(firstname, lastname) VALUES ('Adam', 'Piątkowski');


INSERT INTO access_level(name) VALUES ('CLIENT');
INSERT INTO access_level(name) VALUES ('MANAGER');
INSERT INTO access_level(name) VALUES ('CLIENT_SERVICE');
INSERT INTO access_level(name) VALUES ('ADMIN');

INSERT INTO access_level_mapping(account_id, access_level_id) VALUES (1, 1);
INSERT INTO access_level_mapping(account_id, access_level_id) VALUES (2, 2);
INSERT INTO access_level_mapping(account_id, access_level_id) VALUES (3, 3);
INSERT INTO access_level_mapping(account_id, access_level_id) VALUES (4, 4);