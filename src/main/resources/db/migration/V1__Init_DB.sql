create sequence hibernate_sequence start 1 increment 1;

create table payment (
    id           int8 not null,
    nd           int4,
    payment_date timestamp,
    value        float4,
    primary key (id)
);

create table subscriber (
    id         int8 not null,
    address    varchar(255),
    balance    float4,
    first_name varchar(255),
    last_name  varchar(255),
    nd         int4,
    password   varchar(255),
    patronymic varchar(255),
    primary key (id)
);

create table user_role (
    user_id int8 not null,
    roles   varchar(255)
);
create table usr (
    id       int8    not null,
    active   boolean not null,
    password varchar(255),
    username varchar(255),
    primary key (id)
);

alter table if exists user_role
    add constraint user_role_user_fk
        foreign key (user_id) references usr

