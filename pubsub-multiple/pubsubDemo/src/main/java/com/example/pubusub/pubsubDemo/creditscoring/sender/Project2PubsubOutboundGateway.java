package com.example.pubusub.pubsubDemo.creditscoring.sender;

import org.springframework.integration.annotation.MessagingGateway;
import org.springframework.stereotype.Service;

@Service
@MessagingGateway(defaultRequestChannel = "project2_pubsubOutputChannel")
public interface Project2PubsubOutboundGateway {
    void send(String message);
}
