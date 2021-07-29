create sequence hibernate_sequence start 100;

create table if not exists exchange
(
    id        int              not null,
    timestamp timestamp,
    date      date,
    currency  varchar(3),
    rate      double precision not null default 0.000000,
    constraint exchange_pk primary key (id)
);

create table if not exists request
(
    id           int not null,
    request_type varchar(4),
    timestamp    timestamp,
    client       varchar(255),
    constraint request_pk primary key (id)
);
