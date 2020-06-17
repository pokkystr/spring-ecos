package com.example.pubusub.pubsubDemo.loanapproval.sender;

import org.springframework.integration.annotation.MessagingGateway;
import org.springframework.stereotype.Service;

@Service
@MessagingGateway(defaultRequestChannel = "project1_pubsubOutputChannel")
public interface Project1PubsubOutboundGateway {
    void send(String message);
}
