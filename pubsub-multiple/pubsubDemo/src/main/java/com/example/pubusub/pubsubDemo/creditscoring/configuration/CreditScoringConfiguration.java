package com.example.pubusub.pubsubDemo.creditscoring.configuration;

import com.example.pubusub.pubsubDemo.loanapproval.configuration.LoanApprovalConfiguration;
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
public class CreditScoringConfiguration {
    private static final Logger LOGGER = LoggerFactory.getLogger(LoanApprovalConfiguration.class);

    @Value("${gcp.project-2.topic}")
    private String topic;
    @Value("${gcp.project-2.credentialpath}")
    private String credential;
    @Value("${gcp.project-2.project-id}")
    private String projectId;
    @Value("${gcp.project-2.subscription}")
    private String subscription;

    @Bean(name = "project2_IdProvider")
    public GcpProjectIdProvider project2_IdProvider() {
        return new DefaultGcpProjectIdProvider() {
            @Override
            public String getProjectId() {
                return projectId;
            }
        };
    }

    @Bean(name = "project2_credentialsProvider")
    public CredentialsProvider project2_credentialsProvider() {
        return () -> {
            File initialFile = new File(credential);
            InputStream dbAsStream = new FileInputStream(initialFile);
            return ServiceAccountCredentials.fromStream(dbAsStream);
        };
    }

    @Bean("project2_pubSubSubscriberTemplate")
    public PubSubSubscriberTemplate pubSubSubscriberTemplate(
            @Qualifier("project2_subscriberFactory") SubscriberFactory subscriberFactory) {
        return new PubSubSubscriberTemplate(subscriberFactory);
    }


    @Bean("project2_publisherFactory")
    public DefaultPublisherFactory publisherFactory(
            @Qualifier("project2_IdProvider") GcpProjectIdProvider projectIdProvider,
            @Qualifier("project2_credentialsProvider") CredentialsProvider credentialsProvider) {
        final DefaultPublisherFactory defaultPublisherFactory = new DefaultPublisherFactory(projectIdProvider);
        defaultPublisherFactory.setCredentialsProvider(credentialsProvider);
        return defaultPublisherFactory;
    }

    @Bean("project2_subscriberFactory")
    public DefaultSubscriberFactory subscriberFactory(
            @Qualifier("project2_IdProvider") GcpProjectIdProvider projectIdProvider,
            @Qualifier("project2_credentialsProvider") CredentialsProvider credentialsProvider) {
        final DefaultSubscriberFactory defaultSubscriberFactory = new DefaultSubscriberFactory(projectIdProvider);
        defaultSubscriberFactory.setCredentialsProvider(credentialsProvider);
        return defaultSubscriberFactory;
    }

    @Bean(name = "project2_pubsubInputChannel")
    public MessageChannel pubsubInputChannel() {
        return new DirectChannel();
    }

    @Bean(name = "project2_pubSubTemplate")
    public PubSubTemplate project2_PubSubTemplate(
            @Qualifier("project2_publisherFactory") PublisherFactory publisherFactory,
            @Qualifier("project2_subscriberFactory") SubscriberFactory subscriberFactory,
            @Qualifier("project2_credentialsProvider") CredentialsProvider credentialsProvider) {
        if (publisherFactory instanceof DefaultPublisherFactory) {
            ((DefaultPublisherFactory) publisherFactory).setCredentialsProvider(credentialsProvider);
        }
        return new PubSubTemplate(publisherFactory, subscriberFactory);
    }

    @Bean(name = "project2_messageChannelAdapter")
    public PubSubInboundChannelAdapter messageChannelAdapter(
            @Qualifier("project2_pubsubInputChannel") MessageChannel inputChannel,
            @Qualifier("project2_pubSubTemplate") PubSubTemplate pubSubTemplate) {

        PubSubInboundChannelAdapter adapter = new PubSubInboundChannelAdapter(pubSubTemplate, subscription);
        adapter.setOutputChannel(inputChannel);
        adapter.setAckMode(AckMode.MANUAL);
        /**you can user auto*/
        return adapter;
    }
}
