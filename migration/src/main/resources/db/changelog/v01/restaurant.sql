create sequence if not exists restaurant_seq;

create table if not exists restaurant
(
    restaurant_id bigint not null default nextval ('restaurant_seq'),
    address varchar(255) not null,
    status varchar(255) not null,
    restaurant_name varchar(255) not null,
    create_dttm timestamptz  not null default now(),
    modify_dttm timestamptz  not null default now(),
    constraint restaurant_pk primary key (restaurant_id)
    );

comment on table restaurant is 'Рестораны';
comment on column restaurant.restaurant_id is 'Идентификатор ресторана';
comment on column restaurant.status is 'Статус';
comment on column restaurant.address is 'Адрес';
comment on column restaurant.restaurant_name is 'Название';
comment on column restaurant.create_dttm is 'Дата время вставки записи в таблицу';
comment on column restaurant.modify_dttm is 'Дата время последнего изменения записи';
