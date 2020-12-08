/* Author: fourbarman */

/*
Создание таблицы items.
В таблице поля: id serial pk, id_item, name, description, created_time.
*/
create table items
(
    id   serial primary key,
    id_item varchar(255),
    name varchar(255),
    description varchar(255),
    created_time timestamp
);