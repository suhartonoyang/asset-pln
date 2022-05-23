package com.project.assetpln.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

/**
 * AES-GCM inputs - 12 bytes IV, need the same IV and secret keys for encryption and decryption.
 * <p>
 * The output consist of iv, encrypted content, and auth tag in the following format:
 * output = byte[] {i i i c c c c c c ...}
 * <p>
 * i = IV bytes
 * c = content bytes (encrypted content, auth tag)
 */

import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.security.Key;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import co.id.sdinpresende7be.model.AesConfiguration;
import co.id.sdinpresende7be.service.AesConfigurationService;

@Component
public class EncryptorAesGcm {
	
	@Value("${password.key.aes}")
	private String PASSWORD_KEY;

	public void encrypt(File inputFile, File outputFile) throws Exception {
		System.out.println("start encrypt file");
		String passwordKey = PASSWORD_KEY.substring(0, 16);
		Key secretKey = new SecretKeySpec(passwordKey.getBytes(), "AES");
		Cipher cipher = Cipher.getInstance("AES");
		cipher.init(Cipher.ENCRYPT_MODE, secretKey);

		System.out.println("create inputStream");
		FileInputStream inputStream = new FileInputStream(inputFile);
		System.out.println("create inputBytes");
		byte[] inputBytes = new byte[(int) inputFile.length()];
		System.out.println("write inputBytes");
		inputStream.read(inputBytes);

		System.out.println("create outputBytes");
		byte[] outputBytes = cipher.doFinal(inputBytes);

		System.out.println("create outputStream");
		FileOutputStream outputStream = new FileOutputStream(outputFile);
		System.out.println("write outputStream");
		outputStream.write(outputBytes);

		inputStream.close();
		outputStream.close();
	}

	public void decrypt(File inputFile, File outputFile) throws Exception {
		System.out.println("start decrypt file");
		String passwordKey = PASSWORD_KEY.substring(0, 16);
		Key secretKey = new SecretKeySpec(passwordKey.getBytes(), "AES");
		Cipher cipher = Cipher.getInstance("AES");
		cipher.init(Cipher.DECRYPT_MODE, secretKey);

		System.out.println("create inputStream");
		FileInputStream inputStream = new FileInputStream(inputFile);
		System.out.println("create inputBytes");
		byte[] inputBytes = new byte[(int) inputFile.length()];
		System.out.println("write inputBytes");
		inputStream.read(inputBytes);
		System.out.println("create outputBytes");
		byte[] outputBytes = cipher.doFinal(inputBytes);

		System.out.println("create outputStream");
		FileOutputStream outputStream = new FileOutputStream(outputFile);
		System.out.println("write outputStream");
		outputStream.write(outputBytes);

		inputStream.close();
		outputStream.close();

	}
}