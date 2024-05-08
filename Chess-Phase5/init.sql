drop database chess;
create database chess;
use chess;
create table users (
    username varchar(255) primary key,
    password varchar(255) not null,
    email varchar(255) not null
);
create table auth (
    authToken varchar(255) primary key,
    username varchar(255) not null
);
create table games (
    gameID int auto_increment primary key,
    whiteUsername varchar(255),
    blackUsername varchar(255),
    observers longtext,
    gameName varchar(255),
    game longtext
)