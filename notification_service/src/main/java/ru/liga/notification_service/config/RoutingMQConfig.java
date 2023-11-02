package ru.liga.notification_service.config;

import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Declarables;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RoutingMQConfig {

    @Bean
    public Declarables myQueue() {
        Queue restaurantQueue = new Queue("restaurantQueue", false);
        Queue customerQueue = new Queue("customerQueue", false);
        Queue courierQueue = new Queue("courierQueue", false);

        DirectExchange directExchange = new DirectExchange("directExchange");

        return new Declarables(restaurantQueue, customerQueue, courierQueue, directExchange,
                BindingBuilder.bind(restaurantQueue).to(directExchange).with("restaurant.order"),
                BindingBuilder.bind(customerQueue).to(directExchange).with("customer.order"),
                BindingBuilder.bind(courierQueue).to(directExchange).with("courier.order"));
    }

}