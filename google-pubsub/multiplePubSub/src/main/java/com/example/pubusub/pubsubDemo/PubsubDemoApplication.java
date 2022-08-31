package com.example.pubusub.pubsubDemo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.gcp.autoconfigure.pubsub.GcpPubSubAutoConfiguration;
import org.springframework.cloud.gcp.autoconfigure.pubsub.GcpPubSubReactiveAutoConfiguration;

@SpringBootApplication(exclude = {
        GcpPubSubAutoConfiguration.class,
        GcpPubSubReactiveAutoConfiguration.class
})
public class PubsubDemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(PubsubDemoApplication.class, args);
    }

}
