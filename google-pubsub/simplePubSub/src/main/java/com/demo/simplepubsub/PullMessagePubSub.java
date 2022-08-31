package com.demo.simplepubsub;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gcp.pubsub.core.PubSubTemplate;
import org.springframework.cloud.gcp.pubsub.integration.AckMode;
import org.springframework.cloud.gcp.pubsub.integration.inbound.PubSubInboundChannelAdapter;
import org.springframework.cloud.gcp.pubsub.support.BasicAcknowledgeablePubsubMessage;
import org.springframework.cloud.gcp.pubsub.support.GcpPubSubHeaders;
import org.springframework.context.annotation.Bean;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.channel.PublishSubscribeChannel;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;


@Slf4j
@Component
public class PullMessagePubSub {

    @Value("${config.subscriptions}")
    private String subscriptions;

    /**
     *adapter.setAckMode(AckMode.AUTO); //can be update ackMode to manual !!*/

    @Bean
    public PubSubInboundChannelAdapter messageChannelAdapter(@Qualifier("myInputChannel") MessageChannel inputChannel,
                                                             PubSubTemplate pubSubTemplate) {
        PubSubInboundChannelAdapter adapter = new PubSubInboundChannelAdapter(pubSubTemplate, subscriptions);
        adapter.setOutputChannel(inputChannel);
        adapter.setPayloadType(String.class);
        adapter.setAckMode(AckMode.AUTO); //can be update ackMode to manual
        return adapter;
    }

    @Bean("myInputChannel")
    public MessageChannel inputMessageChannel() {
        return new PublishSubscribeChannel();
    }

    /**
     *message.ack();  <<< if you config ack mode = manual this line need set !!*/
    @ServiceActivator(inputChannel = "myInputChannel")
    public void messageReceiver(String payload, @Header(GcpPubSubHeaders.ORIGINAL_MESSAGE) BasicAcknowledgeablePubsubMessage message) {
        log.info("Message id: {}", message.getPubsubMessage().getMessageId());
        log.info("Message Payload: {}", payload);
//        message.ack(); <<< if you config ack mode = manual this line need set !!
    }
}
