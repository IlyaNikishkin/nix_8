drop database if exists bank;
create database bank;
use bank;
drop table if exists users;
drop table if exists accounts;
drop table if exists transactions;

create table users
(
    id           bigint auto_increment
        primary key,
    name varchar(255) not null
);

create table accounts
(
    id        bigint auto_increment
        primary key,
    balance bigint not null,
    wallet varchar(255) not null,
    user_id    bigint not null,
        foreign key (user_id) references users (id)
);

create table transactions
(
    id bigint auto_increment
        primary key,
    amount bigint not null,
    date datetime(6)  null,
    receiver_id  bigint not null,
        foreign key (receiver_id) references accounts (id),
    sender_id    bigint not null,
        foreign key (sender_id) references accounts (id)
)