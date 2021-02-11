package com.example.cryptography.component;

import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.springframework.stereotype.Component;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.security.*;
import java.util.Base64;

@Component
public class AesGcmComponent {
	
	private static final String password = "ohfFp0a1qYNQZDKEKo65dpEF9dBr0bTW";
	private static final String iv = "rjS9mgfvEpf9d88c";
	
	public String encrypt(String value)
			throws InvalidKeyException, UnsupportedEncodingException, NoSuchAlgorithmException, NoSuchProviderException,
			NoSuchPaddingException, InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException {
		Cipher cipher = initCipher(Cipher.ENCRYPT_MODE);
		byte[] encryptedBytes = cipher.doFinal(value.getBytes("UTF-8"));
		return Base64.getEncoder().encodeToString(encryptedBytes);
	}

	public String decrypt(String encValue)
			throws InvalidKeyException, UnsupportedEncodingException, NoSuchAlgorithmException, NoSuchProviderException,
			NoSuchPaddingException, InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException {
		Cipher cipher = initCipher(Cipher.DECRYPT_MODE);
		byte[] plainBytes = cipher.doFinal(Base64.getDecoder().decode(encValue));
		return new String(plainBytes);
	}
	
	private Cipher initCipher(int cipherMode) throws UnsupportedEncodingException, NoSuchAlgorithmException, NoSuchProviderException, NoSuchPaddingException, InvalidKeyException, InvalidAlgorithmParameterException {
		Security.addProvider(new BouncyCastleProvider());
		SecretKeySpec keySpecification = new SecretKeySpec(password.getBytes("UTF-8"), "AES");
		Cipher cipher = Cipher.getInstance("AES/GCM/NoPadding", "BC");
		GCMParameterSpec spec = new GCMParameterSpec(16 * 8, iv.getBytes("UTF-8"));
		cipher.init(cipherMode, keySpecification, spec);
		return cipher;
	}

}
