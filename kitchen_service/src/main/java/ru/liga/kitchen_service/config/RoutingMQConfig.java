package ru.liga.kitchen_service.config;

import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Declarables;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RoutingMQConfig {

    @Bean
    public Declarables myQueue2() {
        Queue restaurantQueue = new Queue("restaurantQueue", false);
        DirectExchange directExchange = new DirectExchange("directExchange");
        return new Declarables(restaurantQueue, directExchange,
                BindingBuilder.bind(restaurantQueue).to(directExchange).with("restaurant.message"));
    }

}