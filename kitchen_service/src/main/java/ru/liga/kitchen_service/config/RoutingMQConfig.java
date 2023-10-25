package ru.liga.kitchen_service.config;

import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Declarables;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RoutingMQConfig {

    @Bean
    public Declarables myQueue() {
        Queue queueNizhegorodsky = new Queue("queueNizhegorodsky", false);
        Queue queuePrioksky = new Queue("queuePrioksky", false);
        Queue queueSovetsky = new Queue("queueSovetsky", false);
        DirectExchange directExchange = new DirectExchange("directExchange");

        return new Declarables(queueNizhegorodsky, queuePrioksky, queueSovetsky, directExchange,
                BindingBuilder.bind(queueNizhegorodsky).to(directExchange).with("district.nizhegorodsky"),
                BindingBuilder.bind(queuePrioksky).to(directExchange).with("district.prioksky"),
                BindingBuilder.bind(queueSovetsky).to(directExchange).with("district.sovetsky"));
    }

}