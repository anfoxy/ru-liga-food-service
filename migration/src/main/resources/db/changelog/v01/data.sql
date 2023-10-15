-- Добавление тестовых данных в таблицу courier
INSERT INTO courier (phone, coordinates, status)
VALUES
    ('1234567890', POINT(10.0, 20.0), 'active'),
    ('9876543210', POINT(30.0, 40.0), 'complete'),
    ('5555555555', POINT(50.0, 60.0), 'denied');

-- Добавление тестовых данных в таблицу customer
INSERT INTO customer (phone, email, address)
VALUES
    ('1111111111', 'customer1@example.com', 'Адрес 1'),
    ('2222222222', 'customer2@example.com', 'Адрес 2'),
    ('3333333333', 'customer3@example.com', 'Адрес 3');

-- Добавление тестовых данных в таблицу restaurant
INSERT INTO restaurant (address, status)
VALUES
    ('Адрес 4', 'active'),
    ('Адрес 5', 'complete'),
    ('Адрес 6', 'denied');

-- Добавление тестовых данных в таблицу orders
INSERT INTO orders (customer_id, restaurant_id, status, courier_id)
VALUES
    (1, 1, 'active', 1),
    (2, 2, 'complete', 2),
    (3, 3, 'denied', 3);

-- Добавление тестовых данных в таблицу restaurant_menu_item
INSERT INTO restaurant_menu_item (restaurant_id, name, price, description, image)
VALUES
    (1, 'Item 1', 10.99, 'Description 1', 'image1.jpg'),
    (2, 'Item 2', 8.50, 'Description 2', 'image2.jpg'),
    (3, 'Item 3', 12.75, 'Description 3', 'image3.jpg');

-- Добавление тестовых данных в таблицу order_item
INSERT INTO order_item (order_id, restaurant_menu_item_id, quantity)
VALUES
    (1, 1, 2),
    (2, 2, 1),
    (3, 3, 3);