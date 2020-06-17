package com.example.rabbitMQ.amqp;

import com.example.rabbitMQ.bean.Customer;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MessageSender {

    @Autowired
    private TopicExchange topic;
    @Autowired
    private RabbitTemplate rabbitTemplate;
    @Autowired
    private DirectExchange direct;

    public void sendTopicMessage(Customer customer) {
        String key = "key.topic";
        rabbitTemplate.convertAndSend(topic.getName(), key, customer);
    }

    public void sendDirectMessage(Customer customer) {
        String key = "key.direct";
        rabbitTemplate.convertAndSend(direct.getName(), key, customer);
    }
}
