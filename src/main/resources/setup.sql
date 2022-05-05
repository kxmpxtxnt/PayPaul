create database if not exists paypaul;
create table if not exists paypaul.money(
    uuid VARCHAR(64) not null,
    money bigint default 0 not null,
    primary key (uuid)
);
create table if not exists paypaul.log(
    time long not null,
    sender VARCHAR(64) not null,
    receiver VARCHAR(64) not null,
    amount long default 0 not null
);