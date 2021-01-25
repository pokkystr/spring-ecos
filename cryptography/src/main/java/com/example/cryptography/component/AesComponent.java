package com.example.cryptography.component;

import lombok.SneakyThrows;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.springframework.stereotype.Component;

import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.Security;
import java.util.Base64;

@Component
public class AesComponent {

    private static final String AES_ECB_PKCS_7_PADDING = "AES/ECB/PKCS7Padding";

    private static Cipher initCipher(int cipherMode, String dynamicKey) throws NoSuchPaddingException, NoSuchAlgorithmException, NoSuchProviderException, InvalidKeyException {
        Security.addProvider(new BouncyCastleProvider());
        Cipher cipher = Cipher.getInstance(AES_ECB_PKCS_7_PADDING, "BC");
        cipher.init(cipherMode, new SecretKeySpec(
                dynamicKey.getBytes(StandardCharsets.UTF_8), "AES"));
        return cipher;
    }

    @SneakyThrows
    public String encrypt(String plaintext, String dynamicKey) {
        Cipher cipher = initCipher(Cipher.ENCRYPT_MODE, dynamicKey);
        return Base64.getEncoder().encodeToString(
                cipher.doFinal(plaintext.getBytes(StandardCharsets.UTF_8)));
    }

    @SneakyThrows
    public String decrypt(String ciphertext, String dynamicKey) {
        Cipher cipher = initCipher(Cipher.DECRYPT_MODE, dynamicKey);
        return new String(cipher.doFinal(Base64.getDecoder().decode(ciphertext)));
    }

}
