package com.demo.apipubsub.bean.gcp.pubsub;

public class OAuthRequest {
    String grant_type;
    String assertion;

    public String getGrant_type() {
        return grant_type;
    }

    public void setGrant_type(String grant_type) {
        this.grant_type = grant_type;
    }

    public String getAssertion() {
        return assertion;
    }

    public void setAssertion(String assertion) {
        this.assertion = assertion;
    }
}