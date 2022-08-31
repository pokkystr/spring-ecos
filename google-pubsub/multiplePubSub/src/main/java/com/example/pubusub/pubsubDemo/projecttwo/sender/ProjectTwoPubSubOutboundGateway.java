package com.example.pubusub.pubsubDemo.projecttwo.sender;

import org.springframework.integration.annotation.MessagingGateway;
import org.springframework.stereotype.Service;

@Service
@MessagingGateway(defaultRequestChannel = "project2_pubsubOutputChannel")
public interface ProjectTwoPubSubOutboundGateway {
    void send(String message);
}
