package com.demo.apipubsub.service;

public interface PublisherService {
    void pushMessage();

    void modifyAckDeadline(String ackIds);

    void acknowledgeMessage(String ackIds);
}
