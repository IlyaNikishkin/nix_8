drop database if exists groups_students;
create database groups_students;
use groups_students;
drop table if exists groups_table;
drop table if exists students_table;

create table groups_table
(
    id           bigint auto_increment
        primary key,
    created      datetime(6)  null,
    updated      datetime(6)  null,
    name varchar(255) not null
);

create table students_table
(
    id        bigint auto_increment
        primary key,
    created   datetime(6)  null,
    updated   datetime(6)  null,
    name varchar(255) not null,
    age int not null,
    group_id    bigint not null,
    foreign key (group_id) references groups_table (id)
);
