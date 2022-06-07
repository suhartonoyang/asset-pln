package com.project.assetpln.utils;

import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.GCMParameterSpec;

import org.springframework.stereotype.Component;

import com.project.assetpln.model.AesConfiguration;

@Component
public class EncryptorAesGcmPassword {

	private static final Charset UTF_8 = StandardCharsets.UTF_8;

	// return a base 64 encoded aes encrypted text
	public String encrypt(byte[] pText, String password, AesConfiguration aesConfiguration) throws Exception {
		System.out.println("Start encrypt");
		if (aesConfiguration == null) {
			throw new Exception("AES Configuration not found in database!");
		}

		// 16 bytes salt
		byte[] salt = CryptoUtils.getRandomNonce(aesConfiguration.getSaltLengthByte());

		// GCM recommended 12 bytes iv ?
		byte[] iv = CryptoUtils.getRandomNonce(aesConfiguration.getIvLengthByte());

		// secret key from password
		SecretKey aesKeyFromPassword = CryptoUtils.getAESKeyFromPassword(password.toCharArray(), salt);

		Cipher cipher = Cipher.getInstance(aesConfiguration.getEncryptAlgo());

		// AES-GCM needs GCMParameterSpec
		cipher.init(cipher.ENCRYPT_MODE, aesKeyFromPassword, new GCMParameterSpec(aesConfiguration.getTagLengthByte(), iv));

		byte[] cipherText = cipher.doFinal(pText);

		// prefix IV and salt to cipher text
		byte[] cipherTextWithIVSalt = ByteBuffer.allocate(iv.length + salt.length + cipherText.length).put(iv).put(salt).put(cipherText)
				.array();

		// string representation
		return Base64.getEncoder().encodeToString(cipherTextWithIVSalt);
	}

	// we need the same password, salt and iv to decrypt it
	public String decrypt(String cText, String password, AesConfiguration aesConfiguration) throws Exception {
		System.out.println("Start decrypt");
		if (aesConfiguration == null) {
			throw new Exception("AES Configuration not found in database!");
		}
		
		System.out.println(cText + " - " + password + " - " + UTF_8);
		System.out.println(cText.getBytes(UTF_8));
		
		byte[] decode = Base64.getDecoder().decode(cText.getBytes(UTF_8));
		
		// get back the iv and salt from cipher text
		ByteBuffer bb = ByteBuffer.wrap(decode);
		byte[] iv = new byte[aesConfiguration.getIvLengthByte()];
		bb.get(iv);

		byte[] salt = new byte[aesConfiguration.getSaltLengthByte()];
		bb.get(salt);

		byte[] cipherText = new byte[bb.remaining()];
		bb.get(cipherText);

		// get back the aes key from the same password and salt
		SecretKey aesKeyFromPassword = CryptoUtils.getAESKeyFromPassword(password.toCharArray(), salt);

		Cipher cipher = Cipher.getInstance(aesConfiguration.getEncryptAlgo());

		cipher.init(Cipher.DECRYPT_MODE, aesKeyFromPassword, new GCMParameterSpec(aesConfiguration.getTagLengthByte(), iv));

		byte[] plainText = cipher.doFinal(cipherText);

		return new String(plainText, UTF_8);

	}
}
