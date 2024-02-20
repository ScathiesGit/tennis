--liquibase formatted sql

--changeset s:1
INSERT INTO Player (name)
VALUES ('Екатерина'),
       ('Михаил'),
       ('Ольга'),
       ('Дмитрий'),
       ('Анна'),
       ('Сергей'),
       ('Ирина'),
       ('Владимир'),
       ('Наталья');