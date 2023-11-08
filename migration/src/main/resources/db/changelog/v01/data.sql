-- Добавление тестовых данных в таблицу courier
INSERT INTO courier (courier_id, phone, coordinates, status)
VALUES
    ('e5c2046d-4c3b-4ef0-8e34-ff5f64864e13', '5555555551', '(10.0, 10.0)', 'COURIER_ACTIVE'),
    ('416eebf3-9b41-42e1-8356-ff9947362a0e', '5555555552', '(10.0, 20.0)', 'COURIER_ACTIVE'),
    ('d5ec5f67-7e0e-4f4e-8e09-ff0f74f0008b', '5555555553', '(10.0, 30.0)', 'COURIER_ACTIVE'),
    ('e136f366-0c35-4324-8272-fef2b94e6d8f', '5555555554', '(10.0, 40.0)', 'COURIER_ACTIVE'),
    ('1a51ea64-63db-428d-8f53-ff0d6428364b', '5555555555', '(10.0, 50.0)', 'COURIER_ACTIVE'),
    ('15784be0-c2b2-4ca5-8d2c-fefb37e4c320', '5555555556', '(10.0, 60.0)', 'COURIER_ACTIVE');

-- Добавление тестовых данных в таблицу customer
INSERT INTO customer (customer_id, phone, email, address)
VALUES
    ('a8cafd60-bc6c-4f3c-9da4-ff6d020ba735', '1111111111', 'customer1@example.com', '(20.0, 10.0)'),
    ('e7c2424d-b65b-42b9-9d9b-ff777406c083', '2222222222', 'customer2@example.com', '(20.0, 20.0)'),
    ('3f8e8a07-122f-48d0-96b7-ff3abbd8c810', '3333333333', 'customer3@example.com', '(20.0, 30.0)');

-- Добавление тестовых данных в таблицу restaurant
INSERT INTO restaurant (restaurant_id, address, status, restaurant_name)
VALUES
    ('62b57e4d-4cc2-4aa3-9a3d-ffcf1ed9cfdb', '(30.0, 10.0)', 'RESTAURANT_ACTIVE', 'name1'),
    ('02b52014-3e2d-4b91-9cc5-ff56488a4ad3', '(30.0, 20.0)', 'RESTAURANT_ACTIVE', 'name2'),
    ('a6f529c9-6e8e-4ee3-9110-ff04e6f49c32', '(30.0, 30.0)', 'RESTAURANT_ACTIVE', 'name3');

-- Добавление тестовых данных в таблицу orders
INSERT INTO orders (order_id, customer_id, restaurant_id, status, courier_id)
VALUES
    ('fc861a76-389b-49a7-9e4a-ff7a3c02b5c1', 'a8cafd60-bc6c-4f3c-9da4-ff6d020ba735', '62b57e4d-4cc2-4aa3-9a3d-ffcf1ed9cfdb', 'CUSTOMER_CREATED', 'e5c2046d-4c3b-4ef0-8e34-ff5f64864e13'),
    ('2f0d00de-2c27-4c64-95e7-ffbbdceeb6ea', 'e7c2424d-b65b-42b9-9d9b-ff777406c083', '02b52014-3e2d-4b91-9cc5-ff56488a4ad3', 'CUSTOMER_CREATED', '416eebf3-9b41-42e1-8356-ff9947362a0e'),
    ('3ab5e26e-71a9-4c14-9c5a-ffcf21b02c0e', '3f8e8a07-122f-48d0-96b7-ff3abbd8c810', 'a6f529c9-6e8e-4ee3-9110-ff04e6f49c32', 'CUSTOMER_CREATED', 'd5ec5f67-7e0e-4f4e-8e09-ff0f74f0008b');

-- Добавление тестовых данных в таблицу restaurant_menu_item
INSERT INTO restaurant_menu_item (restaurant_menu_item_id, restaurant_id, name, price, description, image, status)
VALUES
    ('ae86c43a-d4a5-4b27-9329-ff8d0e9eb932', '62b57e4d-4cc2-4aa3-9a3d-ffcf1ed9cfdb', 'Item 1', 10.99, 'Description 1', 'image1.jpg', 'RESTAURANT_MENU_ACTIVE'),
    ('6ceab7f3-5702-45a4-9302-ff567c2cc843', '62b57e4d-4cc2-4aa3-9a3d-ffcf1ed9cfdb', 'Item 2', 8.50, 'Description 2', 'image2.jpg', 'RESTAURANT_MENU_ACTIVE'),
    ('c9faffec-842b-45a6-9a83-ff8360b43f5d', '62b57e4d-4cc2-4aa3-9a3d-ffcf1ed9cfdb', 'Item 3', 12.56, 'Description 3', 'image3.jpg', 'RESTAURANT_MENU_ACTIVE'),
    ('4beafbb9-5713-45a5-9309-ff08f1e60a5e', '02b52014-3e2d-4b91-9cc5-ff56488a4ad3', 'Item 4', 11.87, 'Description 4', 'image4.jpg', 'RESTAURANT_MENU_ACTIVE'),
    ('7aeabf14-5732-45a7-9332-ff4c7f0b8b57', '02b52014-3e2d-4b91-9cc5-ff56488a4ad3', 'Item 5', 12.87, 'Description 5', 'image5.jpg', 'RESTAURANT_MENU_ACTIVE'),
    ('85eafbb5-5715-45a8-9354-ff93a1e38b54', '02b52014-3e2d-4b91-9cc5-ff56488a4ad3', 'Item 6', 13.45, 'Description 6', 'image6.jpg', 'RESTAURANT_MENU_ACTIVE'),
    ('62eabf10-5730-45a6-9356-ff90a7e70a56', 'a6f529c9-6e8e-4ee3-9110-ff04e6f49c32', 'Item 7', 14.65, 'Description 7', 'image7.jpg', 'RESTAURANT_MENU_ACTIVE'),
    ('c8eafbf8-5718-45a8-9369-ff64b5e56b59', 'a6f529c9-6e8e-4ee3-9110-ff04e6f49c32', 'Item 8', 2.73, 'Description 8', 'image8.jpg', 'RESTAURANT_MENU_ACTIVE'),
    ('f8eafff9-5729-45a9-9380-ff24b6e78b60', 'a6f529c9-6e8e-4ee3-9110-ff04e6f49c32', 'Item 9', 7.75, 'Description 9', 'image9.jpg', 'RESTAURANT_MENU_ACTIVE'),
    ('06eafba0-573a-45aa-93a1-ff27b7e90a61', '62b57e4d-4cc2-4aa3-9a3d-ffcf1ed9cfdb', 'Item 10', 5.43, 'Description 10', 'image10.jpg', 'RESTAURANT_MENU_ACTIVE');

-- Добавление тестовых данных в таблицу order_item
INSERT INTO order_item (order_item_id, order_id, restaurant_menu_item_id, quantity)
VALUES
    ('7eaabffd-574f-45af-93ff-ff6d8b3bf6f9', 'fc861a76-389b-49a7-9e4a-ff7a3c02b5c1', 'ae86c43a-d4a5-4b27-9329-ff8d0e9eb932', 2),
    ('4eaabfef-574d-45af-93fe-ff364c9c06f6', '2f0d00de-2c27-4c64-95e7-ffbbdceeb6ea', '6ceab7f3-5702-45a4-9302-ff567c2cc843', 1),
    ('deaabffc-574e-45af-93fc-ff0c4e9db8f6', '3ab5e26e-71a9-4c14-9c5a-ffcf21b02c0e', 'c9faffec-842b-45a6-9a83-ff8360b43f5d', 3);