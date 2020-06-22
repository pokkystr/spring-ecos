package com.demo.apipubsub.biding.gcp.pubsub;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.algorithms.Algorithm;
import com.demo.apipubsub.bean.gcp.pubsub.OAuthRequest;
import com.demo.apipubsub.bean.gcp.pubsub.TokenResponse;
import com.google.auth.oauth2.ServiceAccountJwtAccessCredentials;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.interfaces.RSAPrivateKey;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public abstract class PubSubBiding extends RestTemplateBiding {

    private static final Logger logger = LoggerFactory.getLogger(PubSubBiding.class);

    @Value("${gcp.pubsub.timeout}")
    protected int timeout;
    @Value("${gcp.pubsub.max-message}")
    protected int maxMessage;

    @Value("${gcp.oauth.url}")
    private String oauthUrl;
    @Value("${gcp.oauth.grant-type}")
    private String grantType;
    @Value("${gcp.oauth.scope}")
    private String scope;

    @Value("${gcp.pubsub.expiry-length}")
    private int expiryLength;
    @Value("${gcp.oauth.audience}")
    private String audience;
    @Value("${gcp.pubsub.credential.location}")
    private String crentialFile;

    private ServiceAccountJwtAccessCredentials credentials;

    @PostConstruct
    public void init() throws IOException {
        credentials = null;
        try {
            File initialFile = new File(crentialFile);
            InputStream dbAsStream = new FileInputStream(initialFile);
            credentials = ServiceAccountJwtAccessCredentials.fromStream(dbAsStream);
        } catch (IOException e) {
            logger.error(e.getMessage());
            throw new IOException(e.getMessage());
        }
    }

    public String gcpOAuthToken() throws RestClientException {
        String jwtToken = createJWTToken();
        String accessToken = requestAccessToken(jwtToken);
        return accessToken;
    }

    private String requestAccessToken(String signedJwt) {
        String token = "";
        try {
            RestTemplate restTemplate = initRestTemplate(timeout);
            OAuthRequest req = new OAuthRequest();
            req.setGrant_type(grantType);
            req.setAssertion(signedJwt);
            HttpHeaders header = new HttpHeaders();
            header.setContentType(MediaType.APPLICATION_JSON);
            HttpEntity<OAuthRequest> request = new HttpEntity<>(req, header);

            logger.debug("requestAccessToken ", request.toString());

            ResponseEntity<TokenResponse> responseEntity = restTemplate.exchange(oauthUrl, HttpMethod.POST, request, TokenResponse.class);
            if (responseEntity.getStatusCode().is2xxSuccessful()) {
                token = responseEntity.getBody().getAccessToken();
            }
            logger.debug("requestAccessToken[signedJwt:" + signedJwt + ",token:" + token + "]");
        } catch (RestClientException e) {
            logger.error("requestAccessToken[signedJwt:" + signedJwt + ",token:" + token + "]", e);
        }
        return token;
    }

    private String createJWTToken() {
        RSAPrivateKey privateKey = (RSAPrivateKey) credentials.getPrivateKey();

        Date expTime = new Date(System.currentTimeMillis() + TimeUnit.SECONDS.toMillis(expiryLength));
        JWTCreator.Builder token = JWT.create()
                .withIssuedAt(new Date())
                .withExpiresAt(expTime).withIssuer(credentials.getClientEmail())
                .withAudience(audience).withKeyId(credentials.getPrivateKeyId())
                .withClaim("scope", scope)
                .withSubject(credentials.getClientEmail())
                .withClaim("email", credentials.getClientEmail());

        Algorithm algorithm = Algorithm.RSA256(null, privateKey);
        return token.sign(algorithm);
    }
}
