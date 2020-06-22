package com.demo.apipubsub.service.impl;

import com.demo.apipubsub.biding.gcp.pubsub.PubSubBiding;
import com.demo.apipubsub.service.PublisherService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;

public class DemoPublisherServiceImpl extends PubSubBiding implements PublisherService {
    private static final Logger logger = LoggerFactory.getLogger(DemoPublisherServiceImpl.class);

    @Value("${gcp.pubsub.topics}")
    private String topicUrl;

    private static String PUBLISH_PATH = "publish?alt=json";

    @Override
    public void pushMessage() {

    }

    @Override
    public void modifyAckDeadline(String ackIds) {

    }

    @Override
    public void acknowledgeMessage(String ackIds) {

    }

}
