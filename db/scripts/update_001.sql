-- create table items
-- (
--     id   serial primary key not null,
--     name varchar(2000)
-- );

create table items
(
    id   serial primary key not null ,
    name varchar(255),
    description varchar(255),
    created_time timestamp
);