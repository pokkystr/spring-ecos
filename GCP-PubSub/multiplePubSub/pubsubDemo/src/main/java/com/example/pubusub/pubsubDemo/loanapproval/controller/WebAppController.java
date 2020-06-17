package com.example.pubusub.pubsubDemo.loanapproval.controller;

import com.example.pubusub.pubsubDemo.loanapproval.sender.Project1PubsubOutboundGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WebAppController {

    @Autowired
    private Project1PubsubOutboundGateway project1PubsubOutboundGateway;

    @PostMapping("/publishMessage")
    public ResponseEntity<String> publishMessage(@RequestParam("message") String message) {
        System.out.println(message);
        project1PubsubOutboundGateway.send(message);
        return ResponseEntity.ok("OK");
    }
}
