--liquibase formatted sql

--changeset s:1
CREATE TABLE IF NOT EXISTS Match
(
    id SERIAL PRIMARY KEY,
    id_player_1 INT NOT NULL REFERENCES player (id),
    id_player_2 INT NOT NULL  REFERENCES player (id),
    id_winner INT  REFERENCES player (id),
    CONSTRAINT id_p1_not_equals_id_p2 CHECK (id_player_1 != Match.id_player_2)
);