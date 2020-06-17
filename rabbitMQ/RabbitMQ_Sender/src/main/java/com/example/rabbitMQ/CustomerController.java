package com.example.rabbitMQ;

import com.example.rabbitMQ.bean.Customer;
import com.example.rabbitMQ.service.CustomerService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CustomerController {

    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @PostMapping(path = "/customer/topic")
    public ResponseEntity<?> topic(@RequestBody Customer body) {
        String resp = customerService.sendTopic(body);
        return ResponseEntity.status(HttpStatus.CREATED).body(resp);
    }

    @PostMapping(path = "/customer/direct")
    public ResponseEntity<?> direct(@RequestBody Customer body) {
        String resp = customerService.sendDirect(body);
        return ResponseEntity.status(HttpStatus.CREATED).body(resp);
    }
}
