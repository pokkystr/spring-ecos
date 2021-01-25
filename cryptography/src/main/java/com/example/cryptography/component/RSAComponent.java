package com.example.cryptography.component;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.Base64;

@Component
public class RSAComponent {

	/**
	 * Padding will be random encryption with same value
	 */
	private static final String RSA_ECB_PKCS1_PADDING = "RSA/ECB/PKCS1Padding";
	private PublicKey publicKey;
	private PrivateKey privateKey;
	
	public RSAComponent(@Qualifier("rsa-public-key")PublicKey publicKey, @Qualifier("rsa-private-key")PrivateKey privateKey) {
		this.publicKey = publicKey;
		this.privateKey = privateKey;
	}
	
	public String encrypt(String value) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
		Cipher cipher = Cipher.getInstance(RSA_ECB_PKCS1_PADDING);
		cipher.init(Cipher.ENCRYPT_MODE, publicKey);
		byte[] byteEncrypt = cipher.doFinal(value.getBytes());
		String encValue = Base64.getEncoder().encodeToString(byteEncrypt);
		return encValue;
	}
	
	public String decrypt(String encryption) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, UnsupportedEncodingException, IllegalBlockSizeException, BadPaddingException {
		Cipher cipher = Cipher.getInstance(RSA_ECB_PKCS1_PADDING);
		cipher.init(Cipher.DECRYPT_MODE, privateKey);
		byte[] decordValue = Base64.getDecoder().decode(encryption);
		byte[] decryptValue = cipher.doFinal(decordValue);
		String value = new String(decryptValue, "UTF-8");
		return value;
	}
}
