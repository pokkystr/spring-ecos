package com.demo.apipubsub.service.impl;

import com.demo.apipubsub.bean.gcp.pubsub.PullMessage;
import com.demo.apipubsub.biding.gcp.pubsub.PubSubBiding;
import com.demo.apipubsub.service.SubscriptionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

@Service
public class DemoSubscriptServiceImpl extends PubSubBiding implements SubscriptionService {
    private static final Logger logger = LoggerFactory.getLogger(DemoSubscriptServiceImpl.class);

    @Value("${gcp.pubsub.subscription}")
    private String subscriptionUrl;

    private static String PULL_PATH = "pull?alt=json";

    @Override
    public void pullMessage() {
        PullMessage body = new PullMessage();
        String data = null;
        try {

            RestTemplate restTemplate = initRestTemplate(gcpOAuthToken(), timeout);
            HttpHeaders headers = initHeader();
            body.setMaxMessages(maxMessage);
            body.setReturnImmediately(true);
            HttpEntity<PullMessage> request = new HttpEntity<>(body, headers);
            ResponseEntity<String> result = restTemplate.exchange(subscriptionUrl.concat(PULL_PATH), HttpMethod.POST, request, String.class);
            if (result.getStatusCode().equals(HttpStatus.OK)) {
                data = result.getBody();
            }

            logger.debug("Message : ", data);

//            logger.debug("ApiSubscriptionComponent pullMessage[token:" + token +
//                    ",serviceUrl:" + serviceUrl +
//                    ",reqParameter:" + body.toString() +
//                    ",result:" + result.getBody().getReceivedMessages().toString() +
//                    ",status:" + result.getStatusCode().value() + "]");
        } catch (RestClientException e) {
            logger.error(e.getMessage());
        }
    }

    @Override
    public void modifyAckDeadline(String ackIds) {

    }

    @Override
    public void acknowledgeMessage(String ackIds) {

    }

}
