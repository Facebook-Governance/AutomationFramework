/*
* Copyright (c) 2020 Yodlee, Inc. All Rights Reserved.
*
* This software is the confidential and proprietary information of Yodlee, Inc.
* Use is subject to license terms.
*
*/
package com.yodlee.insights.yodleeApi.utils.v2;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class ExceptionFactory {

	private ExceptionFactory() {}

	private static Map<String, String> Y0_errorCodes = new HashMap<String, String>();

	private static Map<String, String> Y8_errorCodes = new HashMap<String, String>();

	private static Map<String, String> Y9_errorCodes = new HashMap<String, String>();

	private static Map<String, String> Y4_errorCodes = new HashMap<String, String>();

	private static Map<String, String> Y7_errorCodes = new HashMap<String, String>();
	
	static {
		InputStream inputStream = null;
		try {
			Properties configProperties = new Properties();
			inputStream = ExceptionFactory.class.getClassLoader().getResourceAsStream("errorMessages.properties");
			configProperties.load(inputStream);
			configProperties.forEach((key, value) -> {
				if (((String) key).startsWith("Y8")) {
					Y8_errorCodes.put((String) key, (String) value);
				}
				if (((String) key).startsWith("Y9")) {
					Y9_errorCodes.put((String) key, (String) value);
				}
				if (((String) key).startsWith("Y0")) {
					Y0_errorCodes.put((String) key, (String) value);
				}
				if (key.equals("Y400")) {
					Y4_errorCodes.put((String) key, (String) value);
				}
				if (key.equals("Y700")) {
					Y7_errorCodes.put((String) key, (String) value);
				}
			});
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (inputStream != null) {
				try {
					inputStream.close();
				} catch (IOException e) {}
			}
		}
	}
/*
	public static void createAndThrowException(String errorCode, Object[] args) {
		if (!Y8_errorCodes.containsKey(errorCode) && !Y9_errorCodes.containsKey(errorCode)
				&& !Y0_errorCodes.containsKey(errorCode) && !Y4_errorCodes.containsKey(errorCode)
				&& !Y7_errorCodes.containsKey(errorCode)) {
			throw new YSLRuntimeException("", new String[] {"UnDefined errorcode"});
		}
		if (Y0_errorCodes.containsKey(errorCode)) {
			throw new AuthenticationException(errorCode, args);
		}
		if (errorCode.equals("Y807")) {
			throw new ResourceNotFoundException(errorCode, args);
		}
		if (errorCode.equals("Y901") || errorCode.equals("Y847")) {
			throw new FeatureNotSupportedException(errorCode, args);
		}
		if (Y8_errorCodes.containsKey(errorCode) || Y4_errorCodes.containsKey(errorCode)) {
			throw new InvalidInputException(errorCode, args);
		}
		if (errorCode.equals("Y902") || errorCode.equals("Y903") || errorCode.equals("Y904")) {
			throw new YSLRuntimeException(errorCode, args);
		}
		if (Y7_errorCodes.containsKey(errorCode)) {
			throw new InsufficientPrivilegesException(errorCode, args);
		}
	}

	public static void createAndThrowSAMLException(String errorCode, String errorSubCode, Object[] args) {
		if (!"Y013".equals(errorCode)) {
			throw new YSLRuntimeException("", new String[] {"UnDefined errorcode"});
		}else {
			throw new SAMLAuthenticationException(errorCode, errorSubCode, args);
		}
	}*/
}
