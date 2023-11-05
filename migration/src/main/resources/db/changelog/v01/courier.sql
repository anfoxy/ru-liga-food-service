create sequence if not exists courier_seq;

create type courier_status AS ENUM
    (
    'COURIER_ACTIVE',
    'COURIER_NOT_ACTIVE',
    'COURIER_DELIVERS'
    );

create table if not exists courier
(
    courier_id bigint not null default nextval ('courier_seq'),
    phone varchar(11) not null,
    coordinates varchar(255) not null,
    status courier_status not null,
    create_dttm timestamptz  not null default now(),
    modify_dttm timestamptz  not null default now(),
    constraint courier_pk primary key (courier_id)
    );

comment on table courier is 'Курьеры';
comment on column courier.courier_id is 'Идентификатор курьера';
comment on column courier.phone is 'Телефон';
comment on column courier.coordinates is 'Координаты';
comment on column courier.status is 'Статус';
comment on column courier.create_dttm is 'Дата время вставки записи в таблицу';
comment on column courier.modify_dttm is 'Дата время последнего изменения записи';