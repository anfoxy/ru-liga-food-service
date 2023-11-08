create type orders_status AS ENUM
    (
    'CUSTOMER_CREATED',
    'CUSTOMER_PAID',
    'CUSTOMER_CANCELLED',
    'KITCHEN_ACCEPTED',
    'KITCHEN_DENIED',
    'KITCHEN_REFUNDED',
    'KITCHEN_PREPARING',
    'DELIVERY_PENDING',
    'DELIVERY_PICKING',
    'DELIVERY_DENIED',
    'DELIVERY_REFUNDED',
    'DELIVERY_DELIVERING',
    'DELIVERY_COMPLETE'
    );

create table if not exists orders
(
    order_id uuid not null default gen_random_uuid(),
    customer_id uuid not null,
    restaurant_id uuid not null,
    status orders_status not null,
    courier_id uuid,
    timestamp timestamptz not null default now(),
    create_dttm timestamptz not null default now(),
    modify_dttm timestamptz not null default now(),
    constraint order_pk primary key (order_id),
    constraint order_customer_fk foreign key (customer_id)
    references customer (customer_id),
    constraint order_restaurant_fk foreign key (restaurant_id)
    references restaurant (restaurant_id),
    constraint order_courier_fk foreign key (courier_id)
    references courier (courier_id)
    );

comment on table orders is 'Заказ';
comment on column orders.order_id is 'Идентификатор клиента';
comment on column orders.customer_id is 'Клиент';
comment on column orders.restaurant_id is 'Ресторан';
comment on column orders.status is 'Статус';
comment on column orders.courier_id is 'Курьер';
comment on column orders.timestamp is 'Время заказа';
comment on column orders.create_dttm is 'Дата время вставки записи в таблицу';
comment on column orders.modify_dttm is 'Дата время последнего изменения записи';
