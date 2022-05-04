create database if not exists paypaul;
create table if not exists paypaul.money(
    uuid VARCHAR(16) not null,
    money bigint default 0 not null,
    primary key (uuid)
);
create table if not exists paypaul.log(
    time long not null,
    sender VARCHAR(16) not null,
    receiver VARCHAR(16) not null,
    amount long default 0 not null
)