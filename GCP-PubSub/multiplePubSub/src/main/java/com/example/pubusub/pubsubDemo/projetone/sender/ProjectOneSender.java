package com.example.pubusub.pubsubDemo.projetone.sender;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gcp.pubsub.core.PubSubTemplate;
import org.springframework.cloud.gcp.pubsub.integration.outbound.PubSubMessageHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.messaging.MessageHandler;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.ListenableFutureCallback;

@Slf4j
@Component
public class ProjectOneSender {

    @Value("${gcp.project-1.topic}")
    private String topic;

    @Primary
    @Bean("project1_messageSender")
    @ServiceActivator(inputChannel = "project1_pubsubOutputChannel")
    public MessageHandler messageSender(@Qualifier("project1_pubSubTemplate") PubSubTemplate pubsubTemplate) {
        PubSubMessageHandler adapter = new PubSubMessageHandler(pubsubTemplate, topic);
        adapter.setPublishCallback(new ListenableFutureCallback<>() {
            @Override
            public void onFailure(Throwable ex) {
                log.error("{} There was an error sending the message {}", topic, ex.getMessage());
            }

            @Override
            public void onSuccess(String result) {
                log.debug("{} Message was sent successfully.", topic);
            }
        });

        return adapter;
    }
}
