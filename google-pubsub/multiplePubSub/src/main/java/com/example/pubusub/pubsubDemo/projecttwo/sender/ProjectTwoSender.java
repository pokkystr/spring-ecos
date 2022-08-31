package com.example.pubusub.pubsubDemo.projecttwo.sender;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gcp.pubsub.core.PubSubTemplate;
import org.springframework.cloud.gcp.pubsub.integration.outbound.PubSubMessageHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.ListenableFutureCallback;

@Slf4j
@Component
public class ProjectTwoSender {

    @Value("${gcp.project-2.topic}")
    private String topic;

    @Primary
    @Bean("project2_messageSender")
    @ServiceActivator(inputChannel = "project2_pubsubOutputChannel")
    public void messageSender(@Qualifier("project1_pubSubTemplate") PubSubTemplate pubsubTemplate) {
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
    }
}
