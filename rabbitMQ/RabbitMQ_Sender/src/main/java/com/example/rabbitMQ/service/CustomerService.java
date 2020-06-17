package com.example.rabbitMQ.service;

import com.example.rabbitMQ.amqp.MessageSender;
import com.example.rabbitMQ.bean.Customer;
import com.google.gson.Gson;
import org.springframework.stereotype.Service;

@Service
public class CustomerService {

    private final MessageSender sender;

    public CustomerService(MessageSender sender) {
        this.sender = sender;
    }

    public String sendTopic(Customer customer) {
        Gson gson = new Gson();
        System.out.println(gson.toJson(customer));
        sender.sendTopicMessage(customer);
        return "sendTopic customer ID " + customer.getId() + " OK";
    }

    public String sendDirect(Customer customer) {
        Gson gson = new Gson();
        System.out.println(gson.toJson(customer));
        sender.sendDirectMessage(customer);
        return "sendDirect customer ID " + customer.getId() + " OK";
    }
}
