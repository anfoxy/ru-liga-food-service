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
        Queue notification = new Queue("notification", false);
        DirectExchange directExchange = new DirectExchange("directExchange");
        return new Declarables(notification, directExchange,
                BindingBuilder.bind(notification).to(directExchange).with("notification.message"));
    }

}