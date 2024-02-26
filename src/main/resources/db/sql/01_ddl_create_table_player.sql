--liquibase formatted sql

--changeset s:1
CREATE TABLE IF NOT EXISTS Player
(
    id SERIAL PRIMARY KEY,
    name VARCHAR(64) NOT NULL
);

--changeset s:2
ALTER TABLE Player
ADD CONSTRAINT name_unique UNIQUE (name);
