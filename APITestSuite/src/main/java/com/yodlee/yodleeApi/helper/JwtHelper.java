
/*******************************************************************************
 *
 * * Copyright (c) 2020 Yodlee, Inc. All Rights Reserved.
 *
 * *
 *
 * * This software is the confidential and proprietary information of Yodlee, Inc.
 *
 * * Use is subject to license terms.
 *
 *******************************************************************************/

package com.yodlee.yodleeApi.helper;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.Security;
import java.security.interfaces.RSAPrivateKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.jose4j.jws.AlgorithmIdentifiers;
import org.jose4j.jws.JsonWebSignature;
import org.jose4j.jwt.JwtClaims;
import org.jose4j.jwt.NumericDate;
import org.jose4j.lang.JoseException;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.impl.PublicClaims;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonPrimitive;
import com.yodlee.insights.yodleeApi.utils.InsightsProperties;
import com.yodlee.yodleeApi.common.Configuration;
import com.yodlee.yodleeApi.constants.JWTConstants;
import com.yodlee.yodleeApi.interfaces.Session;
import com.yodlee.yodleeApi.pojo.HttpMethodParameters;
import com.yodlee.yodleeApi.utils.apiUtils.AccountUtils;
import com.yodlee.yodleeApi.utils.commonUtils.CommonUtils;

import io.restassured.response.Response;

public class JwtHelper {
	
	/**
	 * @param userName: userName is passed as null if we are generating corandJWTToken
	 * @return
	 * @throws JoseException
	 */
	
	InsightsProperties prop = new InsightsProperties();		
	private static final Logger LOGGER = LoggerFactory.getLogger(JwtHelper.class);
	
	public String getJWTToken(String userName) throws JoseException {
		Configuration configuration = Configuration.getInstance();
		String signedJwt=createJWTToken(userName,null,configuration.getJwtInfo().getJwtKey(), configuration.getJwtInfo().getRsaPrivateKey());
		return signedJwt;
	}
	
	private String createJWTToken(String userName,HashMap<String, String> additionalPayloads,String jwtKey,String privateKeyString) throws JoseException{

		//Building claims
		JwtClaims claims = new JwtClaims();
		claims.setAudience(JWTConstants.AUDIENCE);
		claims.setIssuer(jwtKey);
		claims.setIssuedAtToNow();
		NumericDate tokenExpDate = NumericDate.now();
		tokenExpDate.addSeconds(JWTConstants.SECONDS);
		claims.setExpirationTime(tokenExpDate);

		if (userName != null && !userName.isEmpty())
			claims.setClaim("sub", userName);
		if(additionalPayloads != null) {
			for (Map.Entry<String, String> pair: additionalPayloads.entrySet()) {
				claims.setClaim(pair.getKey(), pair.getValue());
	        }
		}
		System.out.println("Senders end claims is:: " + claims.toJson());

		// SIGNING the token
        //RsaJsonWebKey jsonSignKey = RsaJwkGenerator.generateJwk(2048);

        PrivateKey privateKey = null;
		try {
			//Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
			System.out.println("JWT Key is::" + jwtKey);
			byte[] content = privateKeyString.getBytes();// Getting from config property file

			String pkcs8Pem = new String(content, StandardCharsets.UTF_8);
			byte[] pkcs8EncodedBytes = org.apache.commons.codec.binary.Base64.decodeBase64(pkcs8Pem);
			KeyFactory factory = KeyFactory.getInstance("RSA");
			PKCS8EncodedKeySpec privKeySpec = new PKCS8EncodedKeySpec(pkcs8EncodedBytes);
			privateKey = factory.generatePrivate(privKeySpec);
		}
		catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (InvalidKeySpecException e) {
			e.printStackTrace();
		}

		//Generating signed JWT Token
		JsonWebSignature jws = new JsonWebSignature();
		jws.setKey(privateKey);
		jws.setPayload(claims.toJson());
		jws.setHeader("typ", JWTConstants.TYP);
		jws.setAlgorithmHeaderValue(AlgorithmIdentifiers.RSA_USING_SHA512);// Setting the algorithm to be used
		String signedJwt = jws.getCompactSerialization();// payload is signed using this compactSerialization
		System.out.println("Signed key for sender is::" + signedJwt);

		return signedJwt;
	}
	
	/**
	 * @param userName: userName is passed as null if we are generating corandJWTToken
	 * @param additionalPayloads: additional payload data that needs to be passed
	 * This is to do PDV testing and not used for normal calls
	 * @return
	 * @throws JoseException
	 */
	public String getPDVJWTToken(String userName, HashMap<String, String> additionalPayloads) throws JoseException {
		Configuration configuration = Configuration.getInstance();

		String signedJwt=createJWTToken(userName,additionalPayloads,configuration.getJwtInfo().getPdvJwtKey(), configuration.getJwtInfo().getPdvRsaPrivateKey());
		return signedJwt;
	}
	
	//new changes
		
	public  String getJWTToken(String userName, String context) throws IOException {
		// SIGNING the token
		PrivateKey privateKey = null;
		byte[] content = null;

		try {
			Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
			 if(context.equalsIgnoreCase("senseCobrand") || context.equalsIgnoreCase("senseCobrandV2") || context.equalsIgnoreCase("wellnessCobrand") || context.equalsIgnoreCase("wellnessCobrandV2")) {
				 content = prop.readPropertiesFile().getProperty("cobPrivateKeyString").getBytes();;
			 }else {
				// content = (context.equals("senseUser") ? prop.readPropertiesFile().getProperty("sensePrivateKeyString"):prop.readPropertiesFile().getProperty("wellnessPrivateKeyString")).getBytes();
				 
				 if(context.equalsIgnoreCase("senseUser")) {
					 content =  prop.readPropertiesFile().getProperty("sensePrivateKeyString").getBytes();
				 }
				 if(context.equalsIgnoreCase("wellnessUser")) {
					 content =  prop.readPropertiesFile().getProperty("wellnessPrivateKeyString").getBytes();
				 }
				 if(context.equalsIgnoreCase("senseUserV2")) {
					 content =  prop.readPropertiesFile().getProperty("senseNewPrivateKeyString").getBytes();
				 }
				 if(context.equalsIgnoreCase("wellnessUserV2")) {
					 content =  prop.readPropertiesFile().getProperty("L1WellQAPrivateKeyString").getBytes();
				 }
			 }

			String pkcs8Pem = new String(content, StandardCharsets.UTF_8);
			byte[] pkcs8EncodedBytes = org.apache.commons.codec.binary.Base64.decodeBase64(pkcs8Pem);
			KeyFactory factory = KeyFactory.getInstance("RSA");
			PKCS8EncodedKeySpec privKeySpec = new PKCS8EncodedKeySpec(pkcs8EncodedBytes);
			privateKey = factory.generatePrivate(privKeySpec);

		} catch (NoSuchAlgorithmException e) {
			LOGGER.error("No such algorithm exception during creation of jwt token. {}", e);
		} catch (InvalidKeySpecException e) {
			LOGGER.error("Invalid key spec exception during creation of jwt token. {}", e);
		}

		// Generating signed JWT Token
		Algorithm algo = Algorithm.RSA512(null, (RSAPrivateKey) privateKey);
		Long expiryTime  = 0L;
		Date today = new Date();
		String jwtToken = null;
		if (context.equals("senseCobrand")) {
			expiryTime = System.currentTimeMillis() + (3000 * 1000);
			jwtToken = JWT.create().withIssuer(prop.readPropertiesFile().getProperty("COB_JWT_KEY"))
					.withClaim(PublicClaims.ISSUED_AT, today).withClaim(PublicClaims.EXPIRES_AT, new Date(expiryTime))
					.withSubject(userName).withClaim("cobrandId", "10000004").sign(algo);
		} 
		if (context.equals("senseCobrandV2")) {
			expiryTime = System.currentTimeMillis() + (3000 * 1000);
			jwtToken = JWT.create().withIssuer(prop.readPropertiesFile().getProperty("COB_JWT_KEY"))
					.withClaim(PublicClaims.ISSUED_AT, today).withClaim(PublicClaims.EXPIRES_AT, new Date(expiryTime))
					.withSubject(userName).withClaim("cobrandId", "10019488").sign(algo);
		} 
		if (context.equals("wellnessCobrand")) {
			expiryTime = System.currentTimeMillis() + (3000 * 1000);
			jwtToken = JWT.create().withIssuer(prop.readPropertiesFile().getProperty("COB_JWT_KEY"))
					.withClaim(PublicClaims.ISSUED_AT, today).withClaim(PublicClaims.EXPIRES_AT, new Date(expiryTime))
					.withSubject(userName).withClaim("cobrandId", "10011373").sign(algo);
		}
		if (context.equals("wellnessCobrandV2")) {
			expiryTime = System.currentTimeMillis() + (3000 * 1000);
			jwtToken = JWT.create().withIssuer(prop.readPropertiesFile().getProperty("COB_JWT_KEY"))
					.withClaim(PublicClaims.ISSUED_AT, today).withClaim(PublicClaims.EXPIRES_AT, new Date(expiryTime))
					.withSubject(userName).withClaim("cobrandId", "10011372").sign(algo);
		}
		if (context.equals("senseUser")) {
			expiryTime = System.currentTimeMillis() + (6000 * 1000);
			jwtToken = JWT.create().withIssuer(prop.readPropertiesFile().getProperty("SENSE_JWT_KEY"))
					.withClaim(PublicClaims.ISSUED_AT, today).withClaim(PublicClaims.EXPIRES_AT, new Date(expiryTime))
					.withSubject(userName).sign(algo);
		}
		if (context.equals("senseUserV2")) {
			expiryTime = System.currentTimeMillis() + (6000 * 1000);
			jwtToken = JWT.create().withIssuer(prop.readPropertiesFile().getProperty("SENSE_NEW_JWT_KEY"))
					.withClaim(PublicClaims.ISSUED_AT, today).withClaim(PublicClaims.EXPIRES_AT, new Date(expiryTime))
					.withSubject(userName).sign(algo);
		}
		 if (context.equals("wellnessUser")) {
			expiryTime = System.currentTimeMillis() + (1500 * 1000);
			jwtToken = JWT.create().withIssuer(prop.readPropertiesFile().getProperty("WELLNESS_JWT_KEY"))
					.withClaim(PublicClaims.ISSUED_AT, today).withClaim(PublicClaims.EXPIRES_AT, new Date(expiryTime))
					.withSubject(userName).sign(algo);
		}
		 if (context.equals("wellnessUserV2")) {
				expiryTime = System.currentTimeMillis() + (1500 * 1000);
				jwtToken = JWT.create().withIssuer(prop.readPropertiesFile().getProperty("L1WELLQA_JWT_KEY"))
						.withClaim(PublicClaims.ISSUED_AT, today).withClaim(PublicClaims.EXPIRES_AT, new Date(expiryTime))
						.withSubject(userName).sign(algo);
			}
		if (context.equals("pdv")) {
			expiryTime = System.currentTimeMillis() + (6000 * 1000);
			jwtToken = JWT.create().withIssuer(prop.readPropertiesFile().getProperty("PDV_JWT_KEY"))
					.withClaim(PublicClaims.ISSUED_AT, today).withClaim(PublicClaims.EXPIRES_AT, new Date(expiryTime))
					.withSubject(userName).sign(algo);
		}
		return jwtToken;
	}
}
