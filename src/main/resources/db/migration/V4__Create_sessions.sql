create table if not exists sessions (
    id bigint not null,
    person_id bigint not null,
    primary key (id),
    constraint session_person_id_fk foreign key (person_id) references persons (id)
);

create sequence sessions_SEQ start with 1 increment by 1;