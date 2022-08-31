package com.example.pubusub.pubsubDemo.projetone.configuration;

import com.google.api.gax.core.CredentialsProvider;
import com.google.auth.oauth2.ServiceAccountCredentials;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gcp.core.DefaultGcpProjectIdProvider;
import org.springframework.cloud.gcp.core.GcpProjectIdProvider;
import org.springframework.cloud.gcp.pubsub.core.PubSubTemplate;
import org.springframework.cloud.gcp.pubsub.core.subscriber.PubSubSubscriberTemplate;
import org.springframework.cloud.gcp.pubsub.integration.AckMode;
import org.springframework.cloud.gcp.pubsub.integration.inbound.PubSubInboundChannelAdapter;
import org.springframework.cloud.gcp.pubsub.support.DefaultPublisherFactory;
import org.springframework.cloud.gcp.pubsub.support.DefaultSubscriberFactory;
import org.springframework.cloud.gcp.pubsub.support.PublisherFactory;
import org.springframework.cloud.gcp.pubsub.support.SubscriberFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.messaging.MessageChannel;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

@Configuration
public class ProjectOneConfiguration {

    @Value("${gcp.project-1.topic}")
    private String topic;
    @Value("${gcp.project-1.credentialpath}")
    private String credential;
    @Value("${gcp.project-1.project-id}")
    private String projectId;
    @Value("${gcp.project-1.subscription}")
    private String subscription;

    @Bean(name = "project1_IdProvider")
    public GcpProjectIdProvider project1_IdProvider() {
        return new DefaultGcpProjectIdProvider() {
            @Override
            public String getProjectId() {
                return projectId;
            }
        };
    }

    @Bean(name = "project1_credentialsProvider")
    public CredentialsProvider project1_credentialsProvider() {
        return () -> {
            File initialFile = new File(credential);
            InputStream dbAsStream = new FileInputStream(initialFile);
            return ServiceAccountCredentials.fromStream(dbAsStream);
        };
    }

    @Bean("project1_pubSubSubscriberTemplate")
    public PubSubSubscriberTemplate pubSubSubscriberTemplate(
            @Qualifier("project1_subscriberFactory") SubscriberFactory subscriberFactory) {
        return new PubSubSubscriberTemplate(subscriberFactory);
    }

    @Bean("project1_publisherFactory")
    public DefaultPublisherFactory publisherFactory(
            @Qualifier("project1_IdProvider") GcpProjectIdProvider projectIdProvider,
            @Qualifier("project1_credentialsProvider") CredentialsProvider credentialsProvider) {
        final DefaultPublisherFactory defaultPublisherFactory = new DefaultPublisherFactory(projectIdProvider);
        defaultPublisherFactory.setCredentialsProvider(credentialsProvider);
        return defaultPublisherFactory;
    }

    @Bean("project1_subscriberFactory")
    public DefaultSubscriberFactory subscriberFactory(
            @Qualifier("project1_IdProvider") GcpProjectIdProvider projectIdProvider,
            @Qualifier("project1_credentialsProvider") CredentialsProvider credentialsProvider) {
        final DefaultSubscriberFactory defaultSubscriberFactory = new DefaultSubscriberFactory(projectIdProvider);
        defaultSubscriberFactory.setCredentialsProvider(credentialsProvider);
        return defaultSubscriberFactory;
    }

    @Bean(name = "project1_pubsubInputChannel")
    public MessageChannel pubsubInputChannel() {
        return new DirectChannel();
    }

    @Bean(name = "project1_pubSubTemplate")
    public PubSubTemplate project1_PubSubTemplate(
            @Qualifier("project1_publisherFactory") PublisherFactory publisherFactory,
            @Qualifier("project1_subscriberFactory") SubscriberFactory subscriberFactory,
            @Qualifier("project1_credentialsProvider") CredentialsProvider credentialsProvider) {
        if (publisherFactory instanceof DefaultPublisherFactory) {
            ( (DefaultPublisherFactory) publisherFactory ).setCredentialsProvider(credentialsProvider);
        }
        return new PubSubTemplate(publisherFactory, subscriberFactory);
    }

    @Bean(name = "project1_messageChannelAdapter")
    public PubSubInboundChannelAdapter messageChannelAdapter(
            @Qualifier("project1_pubsubInputChannel") MessageChannel inputChannel,
            @Qualifier("project1_pubSubTemplate") PubSubTemplate pubSubTemplate) {

        PubSubInboundChannelAdapter adapter = new PubSubInboundChannelAdapter(pubSubTemplate, subscription);
        adapter.setOutputChannel(inputChannel);
        adapter.setAckMode(AckMode.AUTO);
        /**you can user auto*/
        return adapter;
    }
}
