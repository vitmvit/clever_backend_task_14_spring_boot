-- CREATE SCHEMA IF NOT EXISTS test;
--
-- CREATE SEQUENCE IF NOT EXISTS house_id_seq INCREMENT 1 MINVALUE 1 MAXVALUE 9223372036854775807 CACHE 1;
-- CREATE SEQUENCE IF NOT EXISTS house_history_id_seq INCREMENT 1 MINVALUE 1 MAXVALUE 9223372036854775807 CACHE 1;
-- CREATE SEQUENCE IF NOT EXISTS person_id_seq INCREMENT 1 MINVALUE 1 MAXVALUE 9223372036854775807 CACHE 1;
--
-- CREATE TYPE IF NOT EXISTS type AS ENUM ('OWNER', 'TENANT');
--
-- CREATE TABLE IF NOT EXISTS test.house
-- (
--     id          bigint                 NOT NULL,
--     uuid        uuid                   NOT NULL,
--     area        character varying(255) NOT NULL,
--     country     character varying(255) NOT NULL,
--     city        character varying(255) NOT NULL,
--     street      character varying(255) NOT NULL,
--     number      integer                NOT NULL,
--     create_date timestamp(6),
--     CONSTRAINT house_pkey PRIMARY KEY (id),
--     CONSTRAINT house_uuid_key UNIQUE (uuid)
--     );
--
-- CREATE TABLE IF NOT EXISTS test.person
-- (
--     id              bigint                 NOT NULL,
--     uuid            uuid                   NOT NULL,
--     name            character varying(255),
--     surname         character varying(255) NOT NULL,
--     sex             character varying(255) NOT NULL,
--     passport_series character varying(255),
--     passport_number character varying(255),
--     create_date     timestamp(6),
--     update_date     timestamp(6),
--     house_id        bigint                 NOT NULL,
--     CONSTRAINT person_passport_series_passport_number_key UNIQUE (passport_series, passport_number)
--     CONSTRAINT person_pkey PRIMARY KEY (id),
--     CONSTRAINT person_uuid_key UNIQUE (uuid)
--     );
--
--
-- CREATE TABLE IF NOT EXISTS test.house_history
-- (
--     id        bigint NOT NULL,
--     house_id  bigint,
--     person_id bigint,
--     date      timestamp(6),
--     type      type
--     CONSTRAINT house_history_pkey PRIMARY KEY (id)
--     );
--
-- CREATE TABLE IF NOT EXISTS test.house_owner
-- (
--     house_id  bigint NOT NULL,
--     person_id bigint NOT NULL
-- );
--
-- ALTER TABLE ONLY test.house_owner
--     ADD CONSTRAINT fk_house_owner_id_to_id FOREIGN KEY (house_id) REFERENCES house (id) NOT DEFERRABLE;
-- ALTER TABLE ONLY test.house_owner
--     ADD CONSTRAINT fk_owner_house_id_to_id FOREIGN KEY (person_id) REFERENCES person (id) NOT DEFERRABLE;
-- ALTER TABLE ONLY test.person
--     ADD CONSTRAINT fk_home_resident_id_to_id FOREIGN KEY (house_id) REFERENCES house (id) NOT DEFERRABLE;
--
-- CREATE
--     OR REPLACE FUNCTION house_tenant_function()
--     RETURNS TRIGGER AS
-- '
--     BEGIN
--         INSERT INTO house_history (house_id, person_id, date, type)
--         VALUES (NEW.house_id, NEW.id, CURRENT_TIMESTAMP, ''TENANT'');
--
--         RETURN NEW;
--     END;
-- '
--     LANGUAGE plpgsql;
--
-- CREATE TRIGGER house_tenant_trigger
--     AFTER UPDATE
--     ON person
--     FOR EACH ROW
-- EXECUTE FUNCTION house_tenant_function();
--
-- CREATE
--     OR REPLACE FUNCTION house_owner_function()
--     RETURNS trigger AS
-- '
--     BEGIN
--     INSERT INTO house_history (house_id, person_id, date, type)
--     VALUES (NEW.house_id, NEW.person_id, CURRENT_TIMESTAMP, ''OWNER''); RETURN NEW; END;
-- '
--     LANGUAGE 'plpgsql';
--
-- CREATE TRIGGER house_owner_trigger
--     AFTER UPDATE OF person_id
--     ON house_owner
--     FOR EACH ROW
-- EXECUTE FUNCTION house_owner_function();
--
-- INSERT INTO test.house (id, uuid, area, city, country, street, number, create_date)
-- VALUES (201, '6d316b83-126e-4090-bc81-4125a98923c0', 'AreaOne', 'City', 'Country', 'StreetOne', 19,
--         '2024-01-19 13:16:13.992677');
--
-- INSERT INTO test.person (id, uuid, name, surname, sex, passport_number, passport_series,
--                          create_date, update_date, house_id)
-- VALUES (145, '33447590-3f82-4ae7-a9f6-4b140475c18f', 'Sophia', 'Johnson', 'MALE', 'AB', '1234567',
--         '2024-01-19 13:23:18.356477','2024-01-19 13:23:18.356477', 1);
--
-- INSERT INTO house_history (id, house_id, person_id, date, type)
-- VALUES (1, 201, 145, '2024-01-19 15:52:48.646771', 'TENANT'),
--        (2, 201, 145, '2024-01-19 15:53:10.927848', 'OWNER');
--
-- INSERT INTO house_owner (house_id, person_id)
-- VALUES (201, 145);
--
--
-- Adminer 4.8.1 PostgreSQL 16.1 (Debian 16.1-1.pgdg120+1) dump

-- ALTER TABLE house DROP CONSTRAINT fk_home_resident_id_to_id;
-- DROP CONSTRAINT IF EXISTS fk_home_resident_id_to_id;
-- DROP TABLE IF EXISTS house_owner;
-- DROP TABLE IF EXISTS house_history;
-- DROP TABLE IF EXISTS house;
-- DROP TABLE IF EXISTS person;
-- DROP SEQUENCE IF EXISTS house_id_seq;
-- DROP SEQUENCE IF EXISTS house_history_id_seq;
-- DROP SEQUENCE IF EXISTS person_id_seq;
-- DROP SCHEMA IF EXISTS task;

CREATE SCHEMA IF NOT EXISTS task;

CREATE SEQUENCE IF NOT EXISTS house_id_seq INCREMENT 1 MINVALUE 1 MAXVALUE 9223372036854775807 CACHE 1;

CREATE TABLE IF NOT EXISTS task.house
(
    number
    integer
    NOT
    NULL,
    create_date
    timestamp
(
    6
),
    id bigint DEFAULT nextval
(
    'house_id_seq'
) NOT NULL,
    uuid uuid NOT NULL,
    area character varying
(
    255
) NOT NULL,
    city character varying
(
    255
) NOT NULL,
    country character varying
(
    255
) NOT NULL,
    street character varying
(
    255
) NOT NULL,
    CONSTRAINT house_pkey PRIMARY KEY
(
    id
),
    CONSTRAINT house_uuid_key UNIQUE
(
    uuid
)
    );


CREATE SEQUENCE IF NOT EXISTS house_history_id_seq INCREMENT 1 MINVALUE 1 MAXVALUE 9223372036854775807 CACHE 1;

CREATE TABLE IF NOT EXISTS task.house_history
(
    date timestamp
(
    6
),
    house_id bigint,
    id bigint DEFAULT nextval
(
    'house_history_id_seq'
) NOT NULL,
    person_id bigint,
    type character varying
(
    255
),
    CONSTRAINT "house_history_pkey" PRIMARY KEY
(
    id
)
    );


CREATE TABLE IF NOT EXISTS task.house_owner
(
    house_id
    bigint
    NOT
    NULL,
    person_id
    bigint
    NOT
    NULL
);


CREATE SEQUENCE IF NOT EXISTS person_id_seq INCREMENT 1 MINVALUE 1 MAXVALUE 9223372036854775807 CACHE 1;

CREATE TABLE IF NOT EXISTS task.person
(
    create_date timestamp
(
    6
),
    house_id bigint NOT NULL,
    id bigint DEFAULT nextval
(
    'person_id_seq'
) NOT NULL,
    update_date timestamp
(
    6
),
    uuid uuid NOT NULL,
    name character varying
(
    255
) NOT NULL,
    passport_number character varying
(
    255
),
    passport_series character varying
(
    255
),
    sex character varying
(
    255
) NOT NULL,
    surname character varying
(
    255
) NOT NULL,
    CONSTRAINT "person_pkey" PRIMARY KEY
(
    id
),
    CONSTRAINT "person_uuid_key" UNIQUE
(
    uuid
),
    CONSTRAINT "person_uuid_passport_series_passport_number_key" UNIQUE
(
    uuid,
    passport_series,
    passport_number
)
    );


-- ALTER TABLE ONLY task.house_owner ADD CONSTRAINT fk_house_owner_id_to_id FOREIGN KEY (house_id) REFERENCES house(id) NOT DEFERRABLE;
-- ALTER TABLE ONLY task.house_owner ADD CONSTRAINT fk_owner_house_id_to_id FOREIGN KEY (person_id) REFERENCES person(id) NOT DEFERRABLE;
-- ALTER TABLE ONLY task.person ADD CONSTRAINT fk_home_resident_id_to_id FOREIGN KEY (house_id) REFERENCES house(id) NOT DEFERRABLE;

-- 2024-01-23 11:52:28.955145+00


INSERT INTO task.house (id, uuid, area, city, country, street, number, create_date)
VALUES (2, '9703f730-f88c-4a12-b8cc-ade61ff91a3a', 'AreaOne', 'City', 'Country', 'StreetOne', 19,
        '2024-01-19 13:16:13.992677');

INSERT INTO task.person (id, uuid, name, surname, sex, passport_number, passport_series,
                         create_date, update_date, house_id)
VALUES (1, '33447590-3f82-4ae7-a9f6-4b140475c18f', 'Sophia', 'Johnson', 'MALE', 'AB', '1234567',
        '2024-01-19 13:23:18.356477', '2024-01-19 13:23:18.356477', 1);

INSERT INTO task.house_history (id, house_id, person_id, date, type)
VALUES (1, 1, 1, '2024-01-19 15:52:48.646771', 'TENANT'),
       (2, 1, 1, '2024-01-19 15:53:10.927848', 'OWNER');

INSERT INTO task.house_owner (house_id, person_id)
VALUES (2013, 145);