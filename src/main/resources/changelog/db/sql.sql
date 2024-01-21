-- Adminer 4.8.1 PostgreSQL 16.1 (Debian 16.1-1.pgdg120+1) dump

\connect
"clever_lab_task_14";

CREATE TABLE "public"."databasechangelog"
(
    "id"            character varying(255) NOT NULL,
    "author"        character varying(255) NOT NULL,
    "filename"      character varying(255) NOT NULL,
    "dateexecuted"  timestamp              NOT NULL,
    "orderexecuted" integer                NOT NULL,
    "exectype"      character varying(10)  NOT NULL,
    "md5sum"        character varying(35),
    "description"   character varying(255),
    "comments"      character varying(255),
    "tag"           character varying(255),
    "liquibase"     character varying(20),
    "contexts"      character varying(255),
    "labels"        character varying(255),
    "deployment_id" character varying(10)
) WITH (oids = false);

INSERT INTO "databasechangelog" ("id", "author", "filename", "dateexecuted", "orderexecuted", "exectype", "md5sum",
                                 "description", "comments", "tag", "liquibase", "contexts", "labels", "deployment_id")
VALUES ('001', 'vitikova', 'changelog/001-changelog.xml', '2024-01-21 14:47:39.473045', 1, 'EXECUTED',
        '9:34ef515e83beeb85c76bdd0a9aa040e4', 'sql', '', NULL, '4.25.1', NULL, NULL, '5837659436'),
       ('002', 'vitikova', 'changelog/002-changelog.xml', '2024-01-21 14:47:39.486724', 2, 'EXECUTED',
        '9:7acf7e471ae687ae36210ba5d14fc98e', 'sql', '', NULL, '4.25.1', NULL, NULL, '5837659436'),
       ('003', 'vitikova', 'changelog/003-changelog.xml', '2024-01-21 14:47:39.531573', 3, 'EXECUTED',
        '9:fc88e9bc026c08118af5ddf646189c38', 'sql', '', NULL, '4.25.1', NULL, NULL, '5837659436'),
       ('004', 'vitikova', 'changelog/004-changelog.xml', '2024-01-21 14:47:39.553407', 4, 'EXECUTED',
        '9:ce923c59430c80d719035e50f16ab920', 'sql', '', NULL, '4.25.1', NULL, NULL, '5837659436'),
       ('005', 'vitikova', 'changelog/005-changelog.xml', '2024-01-21 14:47:39.596232', 5, 'EXECUTED',
        '9:f8d2dba9e452a116158e092e818ed2b7', 'sql', '', NULL, '4.25.1', NULL, NULL, '5837659436'),
       ('006', 'vitikova', 'changelog/006-changelog.xml', '2024-01-21 14:47:39.620385', 6, 'EXECUTED',
        '9:f93e60b8c49bbd87c5c0b22b7350d9e2', 'sql', '', NULL, '4.25.1', NULL, NULL, '5837659436'),
       ('007', 'vitikova', 'changelog/007-changelog.xml', '2024-01-21 14:47:39.644083', 7, 'EXECUTED',
        '9:75a6b47cf7fd8d936c034ef3eb5773f3', 'sql', '', NULL, '4.25.1', NULL, NULL, '5837659436');

CREATE TABLE "public"."databasechangeloglock"
(
    "id"          integer NOT NULL,
    "locked"      boolean NOT NULL,
    "lockgranted" timestamp,
    "lockedby"    character varying(255),
    CONSTRAINT "databasechangeloglock_pkey" PRIMARY KEY ("id")
) WITH (oids = false);

INSERT INTO "databasechangeloglock" ("id", "locked", "lockgranted", "lockedby")
VALUES (1, 'f', NULL, NULL);

CREATE SEQUENCE house_id_seq INCREMENT 1 MINVALUE 15 MAXVALUE 9223372036854775807 CACHE 1;

CREATE TABLE "public"."house"
(
    "id"          bigint DEFAULT nextval('house_id_seq') NOT NULL,
    "uuid"        uuid                                   NOT NULL,
    "area"        character varying(255)                 NOT NULL,
    "country"     character varying(255)                 NOT NULL,
    "city"        character varying(255)                 NOT NULL,
    "street"      character varying(255)                 NOT NULL,
    "number"      integer                                NOT NULL,
    "create_date" timestamp(6),
    CONSTRAINT "house_pkey" PRIMARY KEY ("id"),
    CONSTRAINT "house_uuid_key" UNIQUE ("uuid")
) WITH (oids = false);

INSERT INTO "house" ("id", "uuid", "area", "country", "city", "street", "number", "create_date")
VALUES (1, '6d316b83-126e-4090-bc81-4125a68923c0', 'AreaOne', 'Country', 'City', 'StreetOne', 19,
        '2024-01-19 13:16:13.992677'),
       (2, '71d9d979-48d9-420c-9454-912225476fef', 'AreaTwo', 'Country', 'City', 'StreetTwo', 56,
        '2024-01-19 13:17:12.830255'),
       (3, 'f291c4bb-58a0-4800-bfc8-104217051d24', 'AreaThree', 'Country', 'City', 'StreetThree', 43,
        '2024-01-19 13:17:47.442394'),
       (4, '7c204d2c-b694-4dc4-a73a-9fa99b02973f', 'AreaTwo', 'Country', 'City', 'StreetOne', 98,
        '2024-01-19 13:18:17.0791'),
       (5, 'b6baf98c-ea5a-48ec-a4e5-645998c1cceb', 'AreaOne', 'Country', 'City', 'StreetOne', 13,
        '2024-01-19 13:18:39.91152');

CREATE SEQUENCE house_history_id_seq INCREMENT 1 MINVALUE 15 MAXVALUE 9223372036854775807 CACHE 1;

CREATE TABLE "public"."house_history"
(
    "id"        bigint DEFAULT nextval('house_history_id_seq') NOT NULL,
    "house_id"  bigint,
    "person_id" bigint,
    "date"      timestamp(6),
    "type"      type,
    CONSTRAINT "house_history_pkey" PRIMARY KEY ("id")
) WITH (oids = false);

INSERT INTO "house_history" ("id", "house_id", "person_id", "date", "type")
VALUES (1, 2, 1, '2024-01-19 15:52:48.646771', 'TENANT'),
       (2, 1, 4, '2024-01-19 15:53:10.927848', 'OWNER');

CREATE TABLE "public"."house_owner"
(
    "house_id"  bigint NOT NULL,
    "person_id" bigint NOT NULL
) WITH (oids = false);

INSERT INTO "house_owner" ("house_id", "person_id")
VALUES (1, 6),
       (1, 1),
       (1, 3),
       (2, 7),
       (4, 8),
       (3, 9),
       (4, 7),
       (2, 2),
       (2, 4),
       (3, 5),
       (4, 10);

DELIMITER;;

CREATE TRIGGER "house_owner_trigger"
    AFTER UPDATE OF ON "public"."house_owner" FOR EACH ROW
EXECUTE FUNCTION house_owner_function();;

DELIMITER;

CREATE SEQUENCE person_id_seq INCREMENT 1 MINVALUE 15 MAXVALUE 9223372036854775807 CACHE 1;

CREATE TABLE "public"."person"
(
    "id"              bigint DEFAULT nextval('person_id_seq') NOT NULL,
    "uuid"            uuid                                    NOT NULL,
    "name"            character varying(255)                  NOT NULL,
    "surname"         character varying(255)                  NOT NULL,
    "sex"             character varying(255)                  NOT NULL,
    "passport_series" character varying(255),
    "passport_number" character varying(255),
    "create_date"     timestamp(6),
    "update_date"     timestamp(6),
    "house_id"        bigint                                  NOT NULL,
    CONSTRAINT "person_passport_series_passport_number_key" UNIQUE ("passport_series", "passport_number"),
    CONSTRAINT "person_pkey" PRIMARY KEY ("id"),
    CONSTRAINT "person_uuid_key" UNIQUE ("uuid")
) WITH (oids = false);

INSERT INTO "person" ("id", "uuid", "name", "surname", "sex", "passport_series", "passport_number", "create_date",
                      "update_date", "house_id")
VALUES (1, '33447590-3f82-4ae7-a9f6-4b140475c18f', 'Sophia', 'Johnson', 'MALE', 'AB', '1234567',
        '2024-01-19 13:23:18.356477', '2024-01-19 13:23:18.356477', 1),
       (2, '54b9906a-8747-4af7-b641-2a803849f2d1', 'Noah', 'Smith', 'FEMALE', 'CD', '9876543',
        '2024-01-19 13:24:19.361614', '2024-01-19 13:24:19.361614', 2),
       (3, '6c19889a-bb5a-4125-b168-6a1b9c4dd418', 'Olivia', 'Brown', 'FEMALE', 'EF', '2468101',
        '2024-01-19 13:25:00.894308', '2024-01-19 13:25:00.894308', 1),
       (4, '7736bed0-5b5c-4fbc-a919-e56f947a59b7', 'Liam', 'Davis', 'MALE', 'GH', '7654321',
        '2024-01-19 13:26:03.433571', '2024-01-19 13:26:03.433571', 3),
       (5, 'd21df8c3-2771-4dcb-9d4d-5f7a971b0c07', 'Emma', 'Wilson', 'FEMALE', 'IJ', '5432167',
        '2024-01-19 13:26:54.07691', '2024-01-19 13:26:54.07691', 3),
       (6, '74c3452d-ec27-4cb7-9cd5-e177607bf75c', 'Jackson', 'Martinez', 'MALE', 'KL', '2109876',
        '2024-01-19 13:27:48.176436', '2024-01-19 13:27:48.176436', 4),
       (7, 'cd7c5f0f-337b-4db1-8bc0-794d0d98685d', 'Ava', 'Anderson', 'FEMALE', 'MN', '6789012',
        '2024-01-19 13:28:35.280602', '2024-01-19 13:28:35.280602', 5),
       (8, '1e390f7a-2181-42cb-af3d-62fe2a263d08', 'Lucas', 'Thompson', 'MALE', 'OP', '0987654',
        '2024-01-19 13:29:24.912137', '2024-01-19 13:29:24.912137', 4),
       (9, '0953550a-4c67-4d3a-b95d-e7366d0c097c', 'Isabella', 'Lee', 'FEMALE', 'QR', '4321098',
        '2024-01-19 13:30:14.038769', '2024-01-19 13:30:14.038769', 3),
       (10, 'f8cba101-e82b-4e43-8eeb-3c5fac1e5f45', 'Ethan', 'Harris', 'MALE', 'ST', '8765432',
        '2024-01-19 13:31:19.378083', '2024-01-19 13:31:19.378083', 1);

DELIMITER;;

CREATE TRIGGER "house_tenant_trigger"
    AFTER UPDATE
    ON "public"."person"
    FOR EACH ROW EXECUTE FUNCTION house_tenant_function();;

DELIMITER;

ALTER TABLE ONLY "public"."house_owner" ADD CONSTRAINT "fk_house_owner_id_to_id" FOREIGN KEY (house_id) REFERENCES house(id) NOT DEFERRABLE;
ALTER TABLE ONLY "public"."house_owner" ADD CONSTRAINT "fk_owner_house_id_to_id" FOREIGN KEY (person_id) REFERENCES person(id) NOT DEFERRABLE;

ALTER TABLE ONLY "public"."person" ADD CONSTRAINT "fk_home_resident_id_to_id" FOREIGN KEY (house_id) REFERENCES house(id) NOT DEFERRABLE;

-- 2024-01-21 11:48:19.947601+00