create table if not exists products (
    id bigint not null,
    custom_id bigint,
    description varchar(255) not null,
    name varchar(255) not null,
    price_in_cents bigint not null,
    promotion_price_in_cents bigint not null,
    primary key (id)
);

create sequence products_SEQ start with 1 increment by 50;