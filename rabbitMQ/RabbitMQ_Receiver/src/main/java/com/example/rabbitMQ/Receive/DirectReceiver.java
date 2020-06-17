package com.example.rabbitMQ.Receive;

import com.example.rabbitMQ.bean.Customer;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;

@RabbitListener(queues = "customer.direct")
public class DirectReceiver {

    @RabbitHandler
    public void receiveDirect(Customer customer) {
        System.out.println("DirectReceiver " + customer);
    }
}
