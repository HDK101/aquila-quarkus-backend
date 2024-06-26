create table if not exists roles (
    id bigint not null,
    name_display varchar(255) not null,
    name_id varchar(255) not null,
    primary key (id),
    constraint name_display_uq unique(name_display)
);

create sequence if not exists roles_SEQ start with 1 increment by 1;