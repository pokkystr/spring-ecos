package com.example.pubusub.pubsubDemo.projetone.controller;

import com.example.pubusub.pubsubDemo.projetone.sender.ProjectOnePubSubOutboundGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WebAppController {

    @Autowired
    private ProjectOnePubSubOutboundGateway projectOnePubSubOutboundGateway;

    @PostMapping("/publishMessage")
    public ResponseEntity<String> publishMessage(@RequestParam("message") String message) {
        System.out.println(message);
        projectOnePubSubOutboundGateway.send(message);
        return ResponseEntity.ok("OK");
    }
}
