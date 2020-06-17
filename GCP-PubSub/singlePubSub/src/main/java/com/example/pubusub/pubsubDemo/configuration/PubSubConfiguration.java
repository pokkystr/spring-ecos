package com.example.pubusub.pubsubDemo.configuration;


import com.google.api.gax.core.FixedCredentialsProvider;
import com.google.auth.Credentials;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.pubsub.v1.Publisher;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;

import java.io.FileInputStream;
import java.io.IOException;

@Configuration
public class PubSubConfiguration {

    @Value("${gcp.pubsub.credentials.location}")
    private Resource credentialFile;
    @Value("${gcp.project-id}")
    private String projectId;
    @Value("${gcp.pubsub.topic}")
    private String topic;


    @Bean("defaultPublisher")
    public Publisher createPublisher() throws IOException {
        Credentials credentials = GoogleCredentials.fromStream(new FileInputStream(credentialFile.getFile()));
        FixedCredentialsProvider provider = FixedCredentialsProvider.create(credentials);
        Publisher publisher = Publisher.newBuilder(topic).setCredentialsProvider(provider).build();
        return publisher;
    }
}