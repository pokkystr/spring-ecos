package com.demo.simplepubsub;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@RestController
public class MessageController {

    @Autowired
    private MyGcpPubPlanetApplication.PubsubOutboundGateway messagingGateway;

    @PostMapping("/postMessage")
    public String publishMessage(@RequestBody String message) {
        messagingGateway.sendToPubsub(message);
        System.out.println(new Date().getTime() + " - " + message);
        return "OK";
    }

}
