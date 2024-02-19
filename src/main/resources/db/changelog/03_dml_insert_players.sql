--liquibase formatted sql

--changeset s:1
INSERT INTO Player (name)
VALUES ('Player 1'),
       ('Player 2'),
       ('Player 3'),
       ('Player 4'),
       ('Player 5');