/*
* Copyright (c) 2021 Yodlee, Inc. All Rights Reserved.
*
* This software is the confidential and proprietary information of Yodlee, Inc.
* Use is subject to license terms.
* 
*/
package com.yodlee.insights.common.util;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Hex;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;


@Component
public class MacHelper {
	private static  Logger logger = LoggerFactory.getLogger(MacHelper.class);
	private  final String DEFAULT_ENCODING = "UTF-8";
	private  final String HMAC_SHA512 = "HmacSHA512";

	private  Mac getMac(String key) throws CloneNotSupportedException, UnsupportedEncodingException, InvalidKeyException{
		Mac mac = null ;
		SecretKeySpec keySpec = new SecretKeySpec( key.getBytes(DEFAULT_ENCODING), HMAC_SHA512);
		mac = org.apache.commons.codec.digest.HmacUtils.getHmacSha512(key.getBytes());
		mac.init(keySpec);
		return mac ;
	}

	public String getMessageDigest(String message, String key) {
		String rslt = null;
		try {
			Mac mac = getMac(key);
			byte[] encrData = mac.doFinal(message.getBytes(DEFAULT_ENCODING));
			rslt = String.valueOf(Hex.encodeHex(encrData));
		} catch (Exception e) {
			logger.error("Exception while calculating digest", e);
		}
		return rslt;
	}
	
	
	public static void main(String[] args) {
		String securityKey = "yodlee123";

		String memId = "11276678";
		String cobrandId = "10011373";

		String messageToEncrypt = memId + "," + cobrandId;
		MacHelper macHelper = new MacHelper();
		String generatedToken = macHelper.getMessageDigest(messageToEncrypt, securityKey);
		logger.error("generatedToken: " + generatedToken);
	}
}
