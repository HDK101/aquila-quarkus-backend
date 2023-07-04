create table if not exists persons (
    id bigint not null,
    birth date not null,
    email varchar(255) not null,
    login varchar(255) not null,
    name varchar(255) not null,
    password_hash varchar(255) not null,
    phone varchar(255) not null,
    primary key (id)
);

create sequence persons_SEQ start with 1 increment by 50;