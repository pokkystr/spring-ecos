package com.example.pubusub.pubsubDemo.controller;

import com.example.pubusub.pubsubDemo.publisher.PublisherMessageComponent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController
public class PublisherController {
    private static final Logger logger = LoggerFactory.getLogger(PublisherController.class);

    @Autowired
    private PublisherMessageComponent publisherMessageComponent;

    @PostMapping("/postMessage")
    public String publishMessage(@RequestParam("message") String message) {
        logger.info(LocalDateTime.now() + " _ " + message);
        publisherMessageComponent.publish(message);
        return "OK";
    }
}
