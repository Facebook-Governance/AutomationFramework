/**
 * Copyright (c) 2018 Yodlee Inc. All Rights Reserved.
 *
 * This software is the confidential and proprietary information of
 * Yodlee, Inc. Use is subject to license terms.
 *
 * @author Rajeev Anantharaman Iyer
 */
package com.yodlee.customizationtool.util;

import com.yodlee.sdp.common.crypto.CryptoUtil;
import java.security.KeyPair;
import java.util.Base64;
import java.util.HashMap;
import java.util.UUID;

public class GenerateJWTKeys
{
	public static HashMap<String,String> generateKeys() throws Exception
	{
		HashMap<String,String> jwtKeys = new HashMap();
		byte[] publicKeyBytes = null;
		byte[] privateKeyBytes = null;
		KeyPair rsaKeyPair = null;

		try
		{
			rsaKeyPair = CryptoUtil.generateRSAKeyPair();
		}
		catch (Exception e)
		{
			throw new Exception(e);
		}

		publicKeyBytes = rsaKeyPair.getPublic().getEncoded();
		privateKeyBytes = rsaKeyPair.getPrivate().getEncoded();

		String sdpPrivateKey = "-----BEGIN RSA PRIVATE KEY-----"+Base64.getMimeEncoder().encodeToString(privateKeyBytes)+"-----END RSA PRIVATE KEY-----";
		String sdpPublicKey = "-----BEGIN PUBLIC KEY-----"+Base64.getMimeEncoder().encodeToString(publicKeyBytes)+"-----END PUBLIC KEY-----";

		jwtKeys.put("issuerID",UUID.randomUUID().toString());
		jwtKeys.put("publicKey",sdpPublicKey.replace("\n","").replace("\r",""));
		jwtKeys.put("privateKey",sdpPrivateKey.replace("\n","").replace("\r",""));

		System.out.println("Issuer ID:::::: \n" + jwtKeys.get("issuerID"));
		System.out.println("Public Key:::::: \n" + jwtKeys.get("publicKey"));
		System.out.println("Private Key:::::: \n" + jwtKeys.get("privateKey"));

		return jwtKeys;
	}

	public static void main(String[] args) throws Exception {
		generateKeys();
		generateKeys();
		generateKeys();
		generateKeys();
	}
}
