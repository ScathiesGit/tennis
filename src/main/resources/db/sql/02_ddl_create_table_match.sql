--liquibase formatted sql

--changeset s:1
CREATE TABLE IF NOT EXISTS Match
(
    id SERIAL PRIMARY KEY,
    id_player_1 INT NOT NULL,
    id_player_2 INT NOT NULL,
    id_winner INT,
    CONSTRAINT id_p1_not_equals_id_p2 CHECK (id_player_1 != Match.id_player_2)
);

--changeset s:2
ALTER TABLE Match
ALTER COLUMN id_winner SET NOT NULL;