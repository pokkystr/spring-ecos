package com.example.cryptography.component;

import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

@Slf4j
@SpringBootTest
public class RSAComponentTests {

    @Autowired
    private RSAComponent rsaComponent;

    private final String VALUE = "hello-demo-text";

    @Test
    void test_rsa_encrypt() throws IllegalBlockSizeException, InvalidKeyException, BadPaddingException, NoSuchAlgorithmException, NoSuchPaddingException {
        String val = rsaComponent.encrypt(VALUE);
        log.info(val);
        Assert.assertNotNull(val);
    }

    @Test
    void test_res_decrypt() throws IllegalBlockSizeException, InvalidKeyException, BadPaddingException, NoSuchAlgorithmException, NoSuchPaddingException, UnsupportedEncodingException {
        String val_encrypt = rsaComponent.encrypt(VALUE);
        String val_decrypt = rsaComponent.decrypt(val_encrypt);
        Assert.assertEquals(VALUE, val_decrypt);
        log.info("OK");
    }
}
