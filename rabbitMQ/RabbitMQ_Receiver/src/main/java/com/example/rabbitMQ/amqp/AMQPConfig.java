package com.example.rabbitMQ.amqp;

import com.example.rabbitMQ.Receive.DirectReceiver;
import com.example.rabbitMQ.Receive.TopicReceiver;
import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AMQPConfig {

    @Bean
    public DirectExchange directExchange() {
        return new DirectExchange("customer.direct");
    }

    @Bean
    public TopicExchange topicExchange() {
        return new TopicExchange("customer.topic");
    }

    @Bean
    public Queue autoDeletedQueue() {
        return new AnonymousQueue();
    }

    @Bean
    public Binding bindingDirect(DirectExchange topic, Queue autoDeletedQueue) {
        return BindingBuilder.bind(autoDeletedQueue).to(topic).with("key.direct");
    }

    @Bean
    public Binding bindingTopic(TopicExchange topic, Queue autoDeletedQueue) {
        return BindingBuilder.bind(autoDeletedQueue).to(topic).with("key.topic");
    }

    @Bean
    public TopicReceiver receiver() {
        return new TopicReceiver();
    }

    @Bean
    public DirectReceiver directReceiver() {
        return new DirectReceiver();
    }
}
