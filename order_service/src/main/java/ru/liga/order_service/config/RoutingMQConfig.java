package ru.liga.order_service.config;

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
        Queue customerQueue = new Queue("customerQueue", false);
        DirectExchange directExchange = new DirectExchange("directExchange");
        return new Declarables(customerQueue, directExchange,
                BindingBuilder.bind(customerQueue).to(directExchange).with("customer.message"));
    }

}