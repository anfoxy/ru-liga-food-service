create table if not exists customer
(
    customer_id uuid not null default gen_random_uuid(),
    phone varchar(11) not null,
    email varchar(255) not null,
    address varchar(255) not null,
    create_dttm timestamptz  not null default now(),
    modify_dttm timestamptz  not null default now(),
    constraint customer_pk primary key (customer_id)
    );

comment on table customer is 'Клиенты';
comment on column customer.customer_id is 'Идентификатор клиента';
comment on column customer.phone is 'Телефон';
comment on column customer.email is 'Почта';
comment on column customer.address is 'Адрес';
comment on column customer.create_dttm is 'Дата время вставки записи в таблицу';
comment on column customer.modify_dttm is 'Дата время последнего изменения записи';

