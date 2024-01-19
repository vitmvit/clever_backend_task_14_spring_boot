-- Adminer 4.8.1 PostgreSQL 16.1 (Debian 16.1-1.pgdg120+1) dump

\connect
"clever_lab_task_14";

CREATE SEQUENCE house_id_seq INCREMENT 1 MINVALUE 1 MAXVALUE 9223372036854775807 CACHE 1;

CREATE TABLE "public"."house"
(
    "number"      integer                                NOT NULL,
    "create_date" timestamp(6),
    "id"          bigint DEFAULT nextval('house_id_seq') NOT NULL,
    "uuid"        uuid                                   NOT NULL,
    "area"        character varying(255)                 NOT NULL,
    "city"        character varying(255)                 NOT NULL,
    "country"     character varying(255)                 NOT NULL,
    "street"      character varying(255)                 NOT NULL,
    CONSTRAINT "house_pkey" PRIMARY KEY ("id"),
    CONSTRAINT "house_uuid_key" UNIQUE ("uuid")
) WITH (oids = false);


CREATE SEQUENCE house_history_id_seq INCREMENT 1 MINVALUE 1 MAXVALUE 9223372036854775807 CACHE 1;

CREATE TABLE "public"."house_history"
(
    "type"      smallint,
    "date"      timestamp(6),
    "house_id"  bigint,
    "id"        bigint DEFAULT nextval('house_history_id_seq') NOT NULL,
    "person_id" bigint,
    CONSTRAINT "house_history_pkey" PRIMARY KEY ("id")
) WITH (oids = false);


CREATE TABLE "public"."house_owner"
(
    "house_id"  bigint NOT NULL,
    "person_id" bigint NOT NULL
) WITH (oids = false);


CREATE SEQUENCE person_id_seq INCREMENT 1 MINVALUE 1 MAXVALUE 9223372036854775807 CACHE 1;

CREATE TABLE "public"."person"
(
    "create_date"     timestamp(6),
    "house_id"        bigint                                  NOT NULL,
    "id"              bigint DEFAULT nextval('person_id_seq') NOT NULL,
    "update_date"     timestamp(6),
    "uuid"            uuid                                    NOT NULL,
    "name"            character varying(255)                  NOT NULL,
    "passport_number" character varying(255),
    "passport_series" character varying(255),
    "sex"             character varying(255)                  NOT NULL,
    "surname"         character varying(255)                  NOT NULL,
    CONSTRAINT "person_pkey" PRIMARY KEY ("id"),
    CONSTRAINT "person_uuid_key" UNIQUE ("uuid")
) WITH (oids = false);


ALTER TABLE ONLY "public"."house_owner" ADD CONSTRAINT "fk_house_owner_id_to_id" FOREIGN KEY (house_id) REFERENCES person(id) NOT DEFERRABLE;
ALTER TABLE ONLY "public"."house_owner" ADD CONSTRAINT "fk_owner_house_id_to_id" FOREIGN KEY (person_id) REFERENCES house(id) NOT DEFERRABLE;
ALTER TABLE ONLY "public"."person" ADD CONSTRAINT "fk_home_resident_id_to_id" FOREIGN KEY (house_id) REFERENCES house(id) NOT DEFERRABLE;

-- 2024-01-19 12:47:14.263478+00