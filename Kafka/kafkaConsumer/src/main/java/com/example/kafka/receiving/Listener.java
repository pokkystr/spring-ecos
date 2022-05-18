package com.example.kafka.receiving;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class Listener {
    private static final Logger logger = LogManager.getFormatterLogger(Listener.class);

    @KafkaListener(topics = "demo.1", groupId = "demo1", concurrency = "1")
    public void consume(String message) throws IOException {
        logger.info("Consume Message: %s", message);
    }

}
