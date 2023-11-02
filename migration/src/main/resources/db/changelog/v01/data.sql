-- Добавление тестовых данных в таблицу courier
INSERT INTO courier (phone, coordinates, status)
VALUES
    ('5555555551', '(10.0, 10.0)', 'COURIER_ACTIVE'),
    ('5555555552', '(10.0, 20.0)', 'COURIER_ACTIVE'),
    ('5555555553', '(10.0, 30.0)', 'COURIER_ACTIVE'),
    ('5555555554', '(10.0, 40.0)', 'COURIER_ACTIVE'),
    ('5555555555', '(10.0, 50.0)', 'COURIER_ACTIVE'),
    ('5555555556', '(10.0, 60.0)', 'COURIER_ACTIVE');

-- Добавление тестовых данных в таблицу customer
INSERT INTO customer (phone, email, address)
VALUES
    ('1111111111', 'customer1@example.com', '(20.0, 10.0)'),
    ('2222222222', 'customer2@example.com', '(20.0, 20.0)'),
    ('3333333333', 'customer3@example.com', '(20.0, 30.0)');

-- Добавление тестовых данных в таблицу restaurant
INSERT INTO restaurant (address, status,restaurant_name)
VALUES
    ('(30.0, 10.0)', 'RESTAURANT_ACTIVE','name1'),
    ('(30.0, 20.0)', 'RESTAURANT_ACTIVE','name2'),
    ('(30.0, 30.0)', 'RESTAURANT_ACTIVE','name3');

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
    (1, 'Item 2', 8.50, 'Description 2', 'image2.jpg', 'RESTAURANT_MENU_ACTIVE'),
    (1, 'Item 3', 12.56, 'Description 3', 'image3.jpg', 'RESTAURANT_MENU_ACTIVE'),
    (2, 'Item 4', 11.87, 'Description 4', 'image4.jpg', 'RESTAURANT_MENU_ACTIVE'),
    (2, 'Item 5', 12.87, 'Description 5', 'image5.jpg', 'RESTAURANT_MENU_ACTIVE'),
    (2, 'Item 6', 13.45, 'Description 6', 'image6.jpg', 'RESTAURANT_MENU_ACTIVE'),
    (3, 'Item 7', 14.65, 'Description 7', 'image7.jpg', 'RESTAURANT_MENU_ACTIVE'),
    (3, 'Item 8', 2.73, 'Description 8', 'image8.jpg', 'RESTAURANT_MENU_ACTIVE'),
    (3, 'Item 9', 7.75, 'Description 9', 'image9.jpg', 'RESTAURANT_MENU_ACTIVE'),
    (1, 'Item 10', 5.43, 'Description 10', 'image10.jpg', 'RESTAURANT_MENU_ACTIVE');

-- Добавление тестовых данных в таблицу order_item
INSERT INTO order_item (order_id, restaurant_menu_item_id, quantity)
VALUES
    (1, 1, 2),
    (2, 2, 1),
    (3, 3, 3);