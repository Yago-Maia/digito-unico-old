package com.desafio.inter.service;

import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.Cipher;

import org.springframework.stereotype.Service;

import com.desafio.inter.errorHandler.DataBaseOperationException;

@Service
public class EncryptionService {
	
	public static final String ALGORITHM = "RSA";
	
	public String encrypt(String data, String publicKeyText) {
		byte[] cipherText = null;
		
		PublicKey publicKey = getPublicKey(publicKeyText);

		try {
			final Cipher cipher = Cipher.getInstance(ALGORITHM);
			cipher.init(Cipher.ENCRYPT_MODE, publicKey);
			cipherText = cipher.doFinal(data.getBytes());
		} catch (Exception e) {
			throw new DataBaseOperationException("An error occurred while running encryption.");
		}
		
		String encrypt = Base64.getEncoder().encodeToString(cipherText);

		return encrypt;
	}

	private PublicKey getPublicKey(String pub) {
		
		byte[] publicBytes = Base64.getDecoder().decode(pub.getBytes());
		X509EncodedKeySpec keySpec = new X509EncodedKeySpec(publicBytes);
		PublicKey pubKey = null;
		
		try {
			KeyFactory keyFactory = KeyFactory.getInstance(ALGORITHM);
			pubKey = keyFactory.generatePublic(keySpec);
		} catch (Exception e) {
			throw new DataBaseOperationException("An error occurred while running encryption.");
		}
		
		
		return pubKey;
	}
	
}
