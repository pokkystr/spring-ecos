package com.example.cryptography.component;

import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

@Slf4j
@SpringBootTest
public class AesComponentTests {

    @Autowired
    private AesComponent aesComponent;

    @Value("${cryptography.aes.salt}")
    private String salt;
    private final String VALUE = "hello-demo-text";

    @Test
    void test_encrypt() {
        String val_encrypt = aesComponent.encrypt(VALUE, salt);
        System.out.println(val_encrypt);
        log.info(val_encrypt);
    }

    @Test
    void test_decrypt() {
        String val_encrypt = aesComponent.encrypt(VALUE, salt);
        String val_decrypt = aesComponent.decrypt(val_encrypt, salt);
        Assert.assertEquals(val_decrypt, VALUE);
    }

}
