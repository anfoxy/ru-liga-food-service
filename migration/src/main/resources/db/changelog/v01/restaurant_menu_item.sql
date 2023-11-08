create type restaurant_menu_status AS ENUM
    (
    'RESTAURANT_MENU_ACTIVE',
    'RESTAURANT_MENU_DENIED'
    );

create table if not exists restaurant_menu_item
(
    restaurant_menu_item_id uuid not null default gen_random_uuid(),
    restaurant_id uuid not null,
    name varchar(255) not null,
    price numeric(10,2) not null,
    description text,
    status restaurant_menu_status not null,
    image varchar(255),
    create_dttm timestamptz  not null default now(),
    modify_dttm timestamptz  not null default now(),
    constraint restaurant_menu_item_pk primary key (restaurant_menu_item_id),
    constraint restaurant_menu_item_restaurant_fk foreign key (restaurant_id)
    references restaurant (restaurant_id)
    );

comment on table restaurant_menu_item is 'Заказ';
comment on column restaurant_menu_item.restaurant_menu_item_id is 'Идентификатор меню';
comment on column restaurant_menu_item.restaurant_id is 'ресторан';
comment on column restaurant_menu_item.name is 'название';
comment on column restaurant_menu_item.price is 'цена';
comment on column restaurant_menu_item.description is 'описание';
comment on column restaurant_menu_item.image is 'изображение';
comment on column restaurant_menu_item.status is 'Статус';
comment on column restaurant_menu_item.create_dttm is 'Дата время вставки записи в таблицу';
comment on column restaurant_menu_item.modify_dttm is 'Дата время последнего изменения записи';
