package com.demo.apipubsub.service;

public interface SubscriptionService {
    void pullMessage();

    void modifyAckDeadline(String ackIds);

    void acknowledgeMessage(String ackIds);
}
