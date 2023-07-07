create table business_status (
    id bigint not null,
    status varchar(255),
    primary key (id)
);

create sequence business_status_SEQ start with 1;

insert into business_status(
    id,
    status
) 
values(
    nextval(business_status_SEQ),
    "CLOSED"
)