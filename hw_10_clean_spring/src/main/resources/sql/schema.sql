drop database if exists web_jdbc;
create database web_jdbc;
use web_jdbc;
drop table if exists groups_table;
drop table if exists students_table;

create table groups_table
(
    id           bigint auto_increment
        primary key,
    name varchar(255) not null
);

create table students_table
(
    id        bigint auto_increment
        primary key,
    name varchar(255) not null,
        age int not null,
    group_id    bigint not null,
        foreign key (group_id) references groups_table (id)
);