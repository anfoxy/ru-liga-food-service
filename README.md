<h1>Сервис по доставке еды. Общая структура.</h1>
Общая структура сервиса по доставке еды:
<br>

- Order Service - сервис заказов
- Kitchen Service - сервис ресторана
- Delivery Service - сервис доставки
- Notification Service - сервис уведомлений (реализация отправки PUSH через RabbitMQ)

<h2>Dependency Bom</h2>
В dependency модуле указаны все зависимости, которые используются в проекте и остальных модулях, а также их актуальные версии.
<br>
Все зависимости храняться в <b>pom.xml</b>
<br>

<h2>Common</h2>
В модуле Common находятся сущности, репозитории, exception, а также mapper.

<h2>Migration</h2>
Модуль Migration взаимодействует с Базой данный с помощью <b>LiquidBase.</b>
<br>
С помощью Migration создаются базовые таблицы в БД, необходимые для работы всего проекта.

<h2>Security Service</h2>
Данный модуль отвечает за регистрацию, авторизацию и аутентификацию пользователей.
<br>

<h2>Cloud Gateway</h2>
Данный модуль перенаправляет запросу к общему порту <b>http://127.0.0.1:8080</b>
на необходимый сервис.
<br>

<h2>Order Service</h2>
Сервис заказов, в котором можно получить, создать и обновить заказ.

Когда пользователь создает заказ, тот сохраняется в базу данных со статусом <b>CUSTOMER_CREATED</b>.

После оплаты заказа (Вызовом эндпоинта клиента <b>PUT /{id}/update/status?status=CUSTOMER_PAID</b>), отправляется сообщение в <b>Kitchen Service</b>.
Сообщение отправляется через RabbitMQ на <b>Notification Service</b> который перенаправляет сообщение с id заказа на сервис ресторана(<b>Kitchen Service</b>)

Получает события из очереди от Kitchen Service о статусе заказа при новых изменениях
  
API:
- PUT /order-service/order/{id}/update/status?status=<СТАТУС ЗАКАЗА> - обновление статуса заказа

- GET /order-service/order/{id} - получить заказ по id

- PUT /order-service/order/{id}/update - обновить заказ по id (Изменение значения курьера и статуса)

- POST /order-service/order/create - создать заказ
  
<h2>Kitchen Service</h2>
Сервис ресторанов, в котором можно:
- принять или отклонить заказ
- изменить состояние заказа (приготовить)
- открыть\закрыть ресторан 

После того как заказ был принят приготовлен (изменен статус заказа на <b>DELIVERY_PENDING</b>)
Производится уведомление Delivery Service посредством <b>RabbitMQ</b> через <b>Notification Service</b> на сервис курьеров(<b>Delivery Service</b>)

При отказе от заказа (выставления статуса <b>KITCHEN_DENIED</b>) отправляется уведомление на сервис заказов с оповещением, что ресторана отказался от заказа.

<h2>Delivery Service</h2>
Сервис курьеров, в котором можно:

- получить список доступных заказов
- принять заказ
- взять заказ с ресторана
- доставить заказ клиенту
- отказаться от заказа


При получении запроса из <b>Kitchen Service</b> Происходит поиск ближайшего активного курьера который и назначается на доставку. <br>
<br>
Если курьер был не найден, то отправляется сообщение в <b>Kitchen Service</b>

При успешной попытке найти ближайшего курьера - отправляется сообщение соответствующему курьеру через RabbitMQ.

Если курьер принимает заказ (Вызовом эндпоинта клиента <b>PUT /delivery-service/delivery/accepted?id_order=...&id_courier=...</b>), идет обращение через <b>Feign</b> к сервису ресторана

<h2>Notification Service</h2>
Является информационным сервисом, в котором реализовано 1 <b>Queue Listener</b> для перенаправления асинхронных обращений на нужный сервис по ключу.


<h2>Тест:</h2>

Необходимо создать бд food_service и authorization_server

Докер:  

docker run -lt --rm --name rabbitmq -p 5672:5672 -p 15672:15672 rabbitmq:3-management

docker run --name food-service -p 5432:5432 -e POSTGRES_PASSWORD=1234 -d postgres

Запускаем auth_service, cloud_gateway, commons, notification_service, order_service, kitchen_service, delivery_service

Для использования swagger и postman необходимо будет ввести Bearer token, росле чего будет доступен весь функционал.
Данные для входа: логин: user, пароль: password


Первым делом нам необходимо создать заказ 

POST http://localhost:8081/order-service/order/create.

В таком варианте будет занято меню ресторана :


    { 
     "restaurant": "a6f529c9-6e8e-4ee3-9110-ff04e6f49c32",

      "customer": "3f8e8a07-122f-48d0-96b7-ff3abbd8c810",

      "orderItems": [
        {
        "quantity": 5,
        "restaurantMenuItem": "6ceab7f3-5702-45a4-9302-ff567c2cc843"
        }
       ]
    }

Данный вариант будет успешным

    {

     "restaurant": "a6f529c9-6e8e-4ee3-9110-ff04e6f49c32",

     "customer": "3f8e8a07-122f-48d0-96b7-ff3abbd8c810",

     "orderItems": [
         {
         "quantity": 5,
         "restaurantMenuItem": "c8eafbf8-5718-45a8-9369-ff64b5e56b59"
         }
       ]
     }

После мы должны оплатить заказ. Для подтверждения успешной оплаты

PUT http://localhost:8081/order-service/order/<uuid_нового_заказа>/update/status?status=CUSTOMER_PAID 

На кухне мы получим сообщение о новом заказе

Мы его можем принять:

POST http://localhost:8083/kitchen-service/order/<uuid_нового_заказа>/accepted

Отклонить:

POST http://localhost:8083/kitchen-service/order/<uuid_нового_заказа>/denied

Если мы возьмемся за заказ, то завершить его можно:

POST http://localhost:8083/kitchen-service/order/<uuid_нового_заказа>/complete

Когда мы завершим приготовление заказа, то свободный курьер получит уведомление (Если свободных курьеров нет, то ресторан получит об этом уведомление)

Курьер должен принять его:

POST http://localhost:8084/delivery-service/delivery/accepted?id_order=<uuid_заказа>&id_courier=<uuid_courier>

После чего он может отклонить доставку:

POST http://localhost:8084/delivery-service/delivery/<uuid_заказа>/denied

Забрать заказ с ресторана: 

POST http://localhost:8084/delivery-service/delivery/<uuid_заказа>/delivering

И подтвердить завершение доставки:

POST http://localhost:8084/delivery-service/delivery/<uuid_заказа>/complete

Если по какой-то причине отказался ресторан или курьер, то статус заказа меняется на соответствующий.


С помощью POST http://localhost:8081/order-service/order/<uuid_заказа>/update/status?status= можно подтвердить возврат средств  

С помощью POST http://localhost:8083/kitchen-service/restaurant/<uuid_restaurant>/open можно открыть, 

а с помощь POST http://localhost:8083/kitchen-service/restaurant/<uuid_restaurant>/open - закрыть ресторан.

Если ресторан закрыт, то нельзя создавать заказ для него.


