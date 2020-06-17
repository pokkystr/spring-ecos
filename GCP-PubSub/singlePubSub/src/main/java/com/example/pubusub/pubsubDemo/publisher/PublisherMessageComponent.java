package com.example.pubusub.pubsubDemo.publisher;

import com.google.cloud.pubsub.v1.Publisher;
import com.google.protobuf.ByteString;
import com.google.pubsub.v1.PubsubMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
public class PublisherMessageComponent {
    private static final Logger logger = LoggerFactory.getLogger(PublisherMessageComponent.class);

    private Publisher publisher;

    public PublisherMessageComponent(@Qualifier("defaultPublisher") Publisher publisher) {
        this.publisher = publisher;
    }

    public void publish(String message) {
        try {
            ByteString data = ByteString.copyFromUtf8(message);
            PubsubMessage pubsubMessage = PubsubMessage.newBuilder().setData(data).build();
            publisher.publish(pubsubMessage);
            logger.info("Publish message complete");
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
    }

}
