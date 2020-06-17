package com.example.rabbitMQ.Receive;

import com.example.rabbitMQ.bean.Customer;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;

@RabbitListener(queues = "customer.topic")
public class TopicReceiver {

    @RabbitHandler
    public void receiveTopic(Customer customer) {
        System.out.println("TopicReceiver + " + customer);
    }
}
