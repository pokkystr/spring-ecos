package com.example.jwt;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.security.*;
import java.util.Base64;

@SpringBootTest
class JwtApplicationTests {

    @Test
    void contextLoads() {
    }


    public static void main(String[] args) throws NoSuchAlgorithmException {
        KeyPairGenerator keyGenerator = KeyPairGenerator.getInstance("RSA");
        keyGenerator.initialize(1024);

        KeyPair kp = keyGenerator.genKeyPair();
        PublicKey publicKey = kp.getPublic();
        PrivateKey privateKey = kp.getPrivate();

        String encodedPublicKey = Base64.getEncoder().encodeToString(publicKey.getEncoded());
        System.out.println("Public Key:");
        System.out.println(convertToPublicKey(encodedPublicKey));

        System.out.println("Private Key:");
        System.out.println(convertToPublicKey(Base64.getEncoder().encodeToString(privateKey.getEncoded())));

        String token = generateJwtToken(privateKey);
        System.out.println("TOKEN:");
        System.out.println(token);

        token = token.split("\\.")[0]+".eyJhZ2UiOjMwLCJuYW1lIjoiZHJlYW0iLCJhZG1pbiI6dHJ1ZX0."+token.split("\\.")[2];
        printStructure(token, publicKey);
    }

    @SuppressWarnings("deprecation")
    public static String generateJwtToken(PrivateKey privateKey) {
        return Jwts.builder()
                .setPayload("{\n" +
                        "  \"age\": 34,\n" +
                        "  \"name\": \"pigke dream\",\n" +
                        "  \"admin\": true\n" +
                        "}")
                .signWith(SignatureAlgorithm.RS256, privateKey).compact();
    }

    //Print structure of JWT
    public static void printStructure(String token, PublicKey publicKey) {

        var parseClaimsJws = Jwts.parser().setSigningKey(publicKey)
                .parse(token);
//                Jwts.parser()
//                .setSigningKey(publicKey).parseClaimsJws(token);

        System.out.println("Header     : " + parseClaimsJws.getHeader());
        System.out.println("Body       : " + parseClaimsJws.getBody());
//        System.out.println("Signature  : " + parseClaimsJws.getSignature());
    }

    // Add BEGIN and END comments
    private static String convertToPublicKey(String key) {
        StringBuilder result = new StringBuilder();
        result.append("-----BEGIN PUBLIC KEY-----\n");
        result.append(key);
        result.append("\n-----END PUBLIC KEY-----");
        return result.toString();
    }


}
