/*******************************************************************************
 *
 * * Copyright (c) 2018 Yodlee, Inc. All Rights Reserved.
 *
 * *
 *
 * * This software is the confidential and proprietary information of Yodlee, Inc.
 *
 * * Use is subject to license terms.
 *
 *******************************************************************************/
package com.yodlee.restApi.helper;

import java.util.HashMap;

import com.yodlee.yodleeApi.pojo.HttpMethodParameters;

public class UserHelper {

	public HttpMethodParameters createUserRegistrationObject(String coBrandSessionToken, String newUserLoginName,
			String newUserPassword) {

		HashMap<String, String> params = new HashMap<String, String>();
		params.put("cobSessionToken", coBrandSessionToken);
		params.put("userCredentials.loginName", newUserLoginName);
		params.put("userCredentials.password", newUserPassword);
		params.put("userCredentials.objectInstanceType", "com.yodlee.ext.login.PasswordCredentials");
		params.put("userProfile.emailAddress", "rkumar10@yodlee.com");
		params.put("userPreferences[0]", "PREFERRED_CURRENCY~USD");
		params.put("userPreferences[1]", "PREFERRED_DATE_FORMAT~MM/dd/yyyy");
		params.put("userProfile.firstName", "Pavithra");
		params.put("userProfile.lastName", "Kiradi");
		params.put("userProfile.middleInitial", "S");
		params.put("userProfile.objectInstanceType", "com.yodlee.core.usermanagement.UserProfile");
		params.put("userProfile.address1", "3600 Bridge Parkway");
		params.put("userProfile.address2", "Suite 200");
		params.put("userProfile.city", "Redwood City");
		params.put("userProfile.country", "USA");

		HttpMethodParameters httpParameters = HttpMethodParameters.builder().build();
		httpParameters.setFormParams(params);
		return httpParameters;

	}

	public HttpMethodParameters getUserSession(String newUserLoginName, String newUserPassword,
			String coBrandSessionToken) {
		HashMap<String, String> params = new HashMap<String, String>();

		params.put("login", newUserLoginName);
		params.put("password", newUserPassword);
		params.put("cobSessionToken", coBrandSessionToken);
		HttpMethodParameters httpParameters = HttpMethodParameters.builder().build();
		httpParameters.setFormParams(params);
		return httpParameters;

	}
}
