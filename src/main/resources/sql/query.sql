drop database if exists kade;

create database if not exists kade;

use kade;

create table customer(
    id varchar(15) primary key,
    name varchar(50) not null,
    address text not null,
    tel varchar(15) not null
);

create table item(
    code varchar(15) primary key,
    description text not null,
    unit_price double not null,
    qty_on_hand int not null
);

create table supplier(
    supplierId varchar(15) primary key,
    name varchar(50) not null,
    shop varchar(20) not null,
    tel varchar(15) not null
);