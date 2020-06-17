package com.example.rabbitMQ.amqp;

import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MessageConfig {

    @Bean
    public TopicExchange topic() {
        return new TopicExchange("customer.topic");
    }

    @Bean
    public DirectExchange direct() {
        return new DirectExchange("customer.direct");
    }

    @Bean
    public MessageSender sender() {
        return new MessageSender();
    }
}
