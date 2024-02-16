--liquibase formatted sql

--changeset s:1
CREATE TABLE IF NOT EXISTS Player
(
    id SERIAL PRIMARY KEY,
    name VARCHAR(64) NOT NULL
);
