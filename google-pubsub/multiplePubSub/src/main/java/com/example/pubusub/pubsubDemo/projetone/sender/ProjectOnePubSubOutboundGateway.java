package com.example.pubusub.pubsubDemo.projetone.sender;

import org.springframework.integration.annotation.MessagingGateway;
import org.springframework.stereotype.Service;

@Service
@MessagingGateway(defaultRequestChannel = "project1_pubsubOutputChannel")
public interface ProjectOnePubSubOutboundGateway {
    void send(String message);
}
