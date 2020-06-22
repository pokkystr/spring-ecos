package com.demo.apipubsub;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class MyGcpPubPlanetApplication {

	public static void main(String[] args) {
		SpringApplication.run(MyGcpPubPlanetApplication.class, args);
	}

//	@Bean
//	@ServiceActivator(inputChannel = "myOutputChannel")
//	public MessageHandler messageSender(PubSubTemplate pubsubTemplate) {
//		return new PubSubMessageHandler(pubsubTemplate, "demo.sample");
//	}
//
//	@MessagingGateway(defaultRequestChannel = "myOutputChannel")
//	public interface PubsubOutboundGateway {
//		void sendToPubsub(String text);
//	}

}
