package com.example.pubusub.pubsubDemo.creditscoring.sender;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gcp.pubsub.core.PubSubTemplate;
import org.springframework.cloud.gcp.pubsub.integration.outbound.PubSubMessageHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.messaging.MessageHandler;
import org.springframework.stereotype.Component;

@Component
public class ProjectTwoSender {

    @Value("${gcp.project-2.topic}")
    private String topic;

    @Bean("project2_messageSender")
    @ServiceActivator(inputChannel = "project2_pubsubOutputChannel")
    public MessageHandler messageSender(@Qualifier("project1_pubSubTemplate") PubSubTemplate pubsubTemplate) {
        return new PubSubMessageHandler(pubsubTemplate, topic);
    }
}
