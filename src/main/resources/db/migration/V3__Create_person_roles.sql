create table if not exists person_role (
    person_id bigint not null,
    role_id bigint not null,
    primary key (person_id, role_id),
    constraint role_id_fk foreign key (role_id) references roles (id),
    constraint role_person_id_fk foreign key (person_id) references persons (id)
);