package com.example.pubusub.pubsubDemo.creditscoring.receiver;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gcp.pubsub.support.BasicAcknowledgeablePubsubMessage;
import org.springframework.cloud.gcp.pubsub.support.GcpPubSubHeaders;
import org.springframework.context.annotation.Bean;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.messaging.MessageHandler;
import org.springframework.stereotype.Component;

@Component
public class ProjectTwoReceiver {
    private static final Logger LOGGER = LoggerFactory.getLogger(ProjectTwoReceiver.class);

    @Bean("project2_messageReceiver")
    @ServiceActivator(inputChannel = "project2_pubsubInputChannel")
    public MessageHandler messageReceiver() {
        return message -> {
            LOGGER.info("Message headers {}", message.getHeaders());
            LOGGER.info("Message arrived! Payload: " + new String((byte[]) message.getPayload()));
            BasicAcknowledgeablePubsubMessage originalMessage = message.getHeaders().get(GcpPubSubHeaders.ORIGINAL_MESSAGE, BasicAcknowledgeablePubsubMessage.class);
            originalMessage.ack();
            /** if bussice case un success
             * set originalMessage.nack(); */
        };
    }
}
