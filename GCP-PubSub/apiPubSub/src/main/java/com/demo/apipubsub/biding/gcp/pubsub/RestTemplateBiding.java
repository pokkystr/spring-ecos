package com.demo.apipubsub.biding.gcp.pubsub;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

public abstract class RestTemplateBiding {

    protected RestTemplate initRestTemplate(String accessToken, int timeout) {
        RestTemplate restTemplate = new RestTemplate(createHttpComponentsClientHttpRequestFactory(timeout));
        if (accessToken != null) {
            restTemplate.getInterceptors().add(bearerTokenInterceptor(accessToken));
        } else {
            restTemplate.getInterceptors().add(noTokenInterceptor());
        }
        return restTemplate;
    }

    protected RestTemplate initRestTemplate(int timeout) {
        return new RestTemplate(createHttpComponentsClientHttpRequestFactory(timeout));
    }

    protected RestTemplate initSSLRestTemplate(String accessToken, int timeout) {
        RestTemplate restTemplate = new RestTemplate(clientHttpRequestFactory(timeout));
        if (accessToken != null) {
            restTemplate.getInterceptors().add(bearerTokenInterceptor(accessToken));
        } else {
            restTemplate.getInterceptors().add(noTokenInterceptor());
        }
        return restTemplate;
    }

    protected RestTemplate initSSLRestTemplate(int timeout) {
        return new RestTemplate(clientHttpRequestFactory(timeout));
    }

    protected HttpHeaders initHeader() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        return headers;
    }

    private ClientHttpRequestInterceptor bearerTokenInterceptor(String accessToken) {
        return (request, bytes, execution) -> {
            request.getHeaders().add("Authorization", "Bearer " + accessToken);
            return execution.execute(request, bytes);
        };
    }

    private ClientHttpRequestInterceptor noTokenInterceptor() {
        return (request, bytes, execution) -> {
            throw new IllegalStateException("Can't access because without an access token");
        };
    }

    protected SimpleClientHttpRequestFactory clientHttpRequestFactory(int timeout) {
        SimpleClientHttpRequestFactory clientHttpRequestFactory = new SimpleClientHttpRequestFactory();
        clientHttpRequestFactory.setConnectTimeout(timeout);
        clientHttpRequestFactory.setReadTimeout(timeout);
        return clientHttpRequestFactory;
    }

    private HttpComponentsClientHttpRequestFactory createHttpComponentsClientHttpRequestFactory(int timeout) {
        HttpComponentsClientHttpRequestFactory httpRequestFactory = new HttpComponentsClientHttpRequestFactory();
        httpRequestFactory.setConnectionRequestTimeout(timeout);
        httpRequestFactory.setConnectTimeout(timeout);
        httpRequestFactory.setReadTimeout(timeout);
        return httpRequestFactory;
    }
}
