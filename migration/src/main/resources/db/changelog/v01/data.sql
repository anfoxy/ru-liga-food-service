-- Добавление тестовых данных в таблицу courier
INSERT INTO courier (phone, coordinates, status)
VALUES
    ('1234567890', POINT(10.0, 20.0), 'COURIER_ACTIVE'),
    ('9876543210', POINT(30.0, 40.0), 'COURIER_ACTIVE'),
    ('5555555555', POINT(50.0, 60.0), 'COURIER_ACTIVE');

-- Добавление тестовых данных в таблицу customer
INSERT INTO customer (phone, email, address)
VALUES
    ('1111111111', 'customer1@example.com', 'Нижний Новгород, Советский район, дом 5'),
    ('2222222222', 'customer2@example.com', 'Нижний Новгород, Приокский район, дом 1'),
    ('3333333333', 'customer3@example.com', 'Нижний Новгород, Нижегородский район, дом 1');

-- Добавление тестовых данных в таблицу restaurant
INSERT INTO restaurant (address, status,restaurant_name)
VALUES
    ('Нижний Новгород, Приокский район, дом 7', 'RESTAURANT_ACTIVE','name1'),
    ('Нижний Новгород, Нижегородский район, дом 8', 'RESTAURANT_ACTIVE','name2'),
    ('Нижний Новгород, Советский район, дом 9', 'RESTAURANT_ACTIVE','name3');

-- Добавление тестовых данных в таблицу orders
INSERT INTO orders (customer_id, restaurant_id, status, courier_id)
VALUES
    (1, 1, 'CUSTOMER_CREATED', 1),
    (2, 2, 'CUSTOMER_CREATED', 2),
    (3, 3, 'CUSTOMER_CREATED', 3);

-- Добавление тестовых данных в таблицу restaurant_menu_item
INSERT INTO restaurant_menu_item (restaurant_id, name, price, description, image, status)
VALUES
    (1, 'Item 1', 10.99, 'Description 1', 'image1.jpg', 'RESTAURANT_MENU_ACTIVE'),
    (2, 'Item 2', 8.50, 'Description 2', 'image2.jpg', 'RESTAURANT_MENU_ACTIVE'),
    (3, 'Item 3', 12.75, 'Description 3', 'image3.jpg', 'RESTAURANT_MENU_ACTIVE');

-- Добавление тестовых данных в таблицу order_item
INSERT INTO order_item (order_id, restaurant_menu_item_id, quantity)
VALUES
    (1, 1, 2),
    (2, 2, 1),
    (3, 3, 3);