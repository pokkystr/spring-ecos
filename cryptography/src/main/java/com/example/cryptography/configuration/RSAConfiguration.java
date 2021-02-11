package com.example.cryptography.configuration;

import org.bouncycastle.util.io.pem.PemObject;
import org.bouncycastle.util.io.pem.PemReader;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;

import java.io.FileReader;
import java.io.IOException;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

@Configuration
public class RSAConfiguration {

    @Value("${certs.public}")
    private Resource pathPublicKey;
    @Value("${certs.private}")
    private Resource pathPrivateKey;

    @Bean("rsa-public-key")
    PublicKey publicKey() throws IOException, InvalidKeySpecException, NoSuchAlgorithmException {
        FileReader reader = new FileReader(pathPublicKey.getFile());
        try (PemReader pemReader = new PemReader(reader)) {
            PemObject spki = pemReader.readPemObject();
            PublicKey key = KeyFactory.getInstance("RSA").generatePublic(new X509EncodedKeySpec(spki.getContent()));
            return key;
        }
    }

    @Bean("rsa-private-key")
    PrivateKey privateKey() throws IOException, InvalidKeySpecException, NoSuchAlgorithmException {
        FileReader reader = new FileReader(pathPrivateKey.getFile());
        try (PemReader pemReader = new PemReader(reader)) {
            PemObject spki = pemReader.readPemObject();
            PrivateKey key = KeyFactory.getInstance("RSA").generatePrivate(new PKCS8EncodedKeySpec(spki.getContent()));
            return key;
        }
    }

}
