create table if not exists order_item
(
    order_item_id uuid not null default gen_random_uuid(),
    order_id uuid not null,
    restaurant_menu_item_id uuid not null,
    price numeric(10,2),
    quantity integer not null,
    create_dttm timestamptz  not null default now(),
    modify_dttm timestamptz  not null default now(),
    constraint order_item_pk primary key (order_item_id),
    constraint order_item_orders_fk foreign key (order_id)
    references orders (order_id),
    constraint restaurant_menu_item_order_item_fk foreign key (restaurant_menu_item_id)
    references restaurant_menu_item (restaurant_menu_item_id)
    );

comment on table order_item is 'Пункт заказа';
comment on column order_item.order_item_id is 'Идентификатор пункт заказа';
comment on column order_item.order_id is 'Заказ';
comment on column order_item.restaurant_menu_item_id is 'Пункт меню';
comment on column order_item.price is 'цена';
comment on column order_item.quantity is 'количество';
comment on column order_item.create_dttm is 'Дата время вставки записи в таблицу';
comment on column order_item.modify_dttm is 'Дата время последнего изменения записи';
