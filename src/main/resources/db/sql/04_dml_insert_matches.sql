--liquibase formatted sql

--changeset s:1
INSERT INTO Match (id_player_1, id_player_2, id_winner)
VALUES (1, 2, 1),
       (1, 3, 3),
       (1, 4, 4),
       (2, 3, 2),
       (2, 4, 2),
       (2, 5, 5),
       (3, 4, 4),
       (3, 5, 5),
       (3, 6, 6),
       (4, 5, 5),
       (4, 6, 6),
       (4, 7, 4),
       (5, 6, 5),
       (5, 7, 5),
       (5, 8, 5),
       (6, 7, 7),
       (6, 8, 8),
       (6, 9, 6),
       (7, 8, 8),
       (7, 9, 9);