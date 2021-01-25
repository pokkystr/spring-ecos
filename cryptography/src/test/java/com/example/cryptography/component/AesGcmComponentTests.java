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
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;

@Slf4j
@SpringBootTest
public class AesGcmComponentTests {

    @Autowired
    private AesGcmComponent aesGcmComponent;
    private final String VALUE = "hello-demo-text";

    @Test
    void test_encrypt() throws NoSuchPaddingException, NoSuchAlgorithmException, IllegalBlockSizeException, BadPaddingException, NoSuchProviderException, InvalidKeyException, UnsupportedEncodingException, InvalidAlgorithmParameterException {
        String val_encrypt = aesGcmComponent.encrypt(VALUE);
        System.out.println(val_encrypt);
        log.info(val_encrypt);
    }

    @Test
    void test_decrypt() throws NoSuchPaddingException, NoSuchAlgorithmException, IllegalBlockSizeException, BadPaddingException, NoSuchProviderException, InvalidKeyException, UnsupportedEncodingException, InvalidAlgorithmParameterException {
        String val_encrypt = aesGcmComponent.encrypt(VALUE);
        String val_decrypt = aesGcmComponent.decrypt(val_encrypt);
        Assert.assertEquals(val_decrypt, VALUE);
    }
}
