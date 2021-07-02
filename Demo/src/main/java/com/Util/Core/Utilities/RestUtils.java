/**
 * Copyright (c) 2018 Yodlee Inc. All Rights Reserved.
 *
 * This software is the confidential and proprietary information of
 * Yodlee, Inc. Use is subject to license terms.
 *
 * @author Rajeev Anantharaman Iyer
 */
package com.Util.Core.Utilities;

import io.restassured.RestAssured;
import io.restassured.config.SSLConfig;
import io.restassured.response.Response;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import com.Util.datpprocessor.DataProperty;

public class RestUtils {

	public static String restVersion;
	public static String coBrandSessionToken;
	public static Response newUserResponse;
	private static String usersSessionToken;
	private static String memId;
	private static String userName = null;
	private static String finalUserName = null;

	public static Response get(Map<String, String> params, String path) {
		Response response = RestAssured.given().log().all().queryParams(params).header("Accept", "application/json")
				.get(path);
		return response;
	}

	public static Response post(Map<String, String> params, String path) {
		Response response = RestAssured.given().log().all().formParams(params).post(path);
		return response;
	}

	public static void init(String restURLEncoding) {
		//RestAssured.urlEncodingEnabled = "true".equals(String.valueOf(properties.isRestURLEncoding())) ? true : false;
		RestAssured.urlEncodingEnabled = "true".equals(restURLEncoding) ? true : false;
		SSLConfig sslConfig1 = new SSLConfig();
		RestAssured.config = RestAssured.config().sslConfig(sslConfig1.allowAllHostnames());
	}

	public static String initCobrand(String restURLEncoding, String restVer,
	                                 String baseUri, String cobLogin,
	                                 String cobPassword, String newUsername,
	                                 String newUserPassword) {
		synchronized (RestUtils.class) {

			//LoadParameters(configFile);
			init(restURLEncoding);
			restVersion = restVer;
			String baseURI = baseUri;
			coBrandSessionToken = getCobrandSessionToken(baseURI, cobLogin, cobPassword);
			newUserResponse = getNewUserResponse(baseURI, newUsername, newUserPassword);
			try {
				usersSessionToken = newUserResponse.jsonPath()
						.getString("userContext.conversationCredentials.sessionToken");
				if (usersSessionToken != null) {
					memId = newUserResponse.jsonPath().getString("userId");
					userName = newUserResponse.jsonPath().getString("loginName");
					newUserResponse.jsonPath().getString("userContext.locale");
					finalUserName = userName;
					System.out.println("MEM ID =" + memId);
				} else {
				}
			} catch (Exception er) {
				er.printStackTrace();
			}
			return finalUserName;
		}
	}
	
	public static String getCobrandSessionToken(String url, String cobLogin, String cobPassword) {
		HashMap<String, String> params = new HashMap();
		params.put("cobrandLogin", cobLogin);
		params.put("cobrandPassword", cobPassword);
		Response response = post(params, url+"v1.0/authenticate/coblogin");
		response.then().log().all();
		return response.jsonPath().getString("cobrandConversationCredentials.sessionToken");
	}

	
	public static Response getNewUserResponse(String baseURI, String newUserName, String newUserPassword) {
		HashMap<String, String> params = new HashMap();
		params.put("cobSessionToken", coBrandSessionToken);
		params.put("userCredentials.loginName", newUserName);
		params.put("userCredentials.password", newUserPassword);
		params.put("userCredentials.objectInstanceType", "com.yodlee.ext.login.PasswordCredentials");
		params.put("userProfile.emailAddress", "riyer@yodlee.com");
		params.put("userPreferences[0]", "PREFERRED_CURRENCY~USD");
		params.put("userPreferences[1]", "PREFERRED_DATE_FORMAT~MM/dd/yyyy");
		params.put("userProfile.firstName", "autoqa");
		params.put("userProfile.lastName", "subbranding");
		params.put("userProfile.middleInitial", "T");
		params.put("userProfile.objectInstanceType", "com.yodlee.core.usermanagement.UserProfile");
		params.put("userProfile.address1", "3600 Bridge Parkway");
		params.put("userProfile.address2", "Suite 200");
		params.put("userProfile.city", "San Jose"); // San Mateo
		params.put("userProfile.country", "USA");
		Response response = post(params, baseURI+"v1.0/jsonsdk/UserRegistration/register3");
		response.then().log().all();
		return response;
	}

	public static void getUser() throws InterruptedException, SQLException {

		/*String user = initCobrand("true","/v2.0",
				"https://10.79.7.243/services/srest/DNS1572920546641/",
				"testCredPrivate","@ID7xui@0&", "sb_user_21238","Password#");*/

		String user = initCobrand("true","/v2.0",
				"https://10.79.7.243/services/srest/DNS1574236006161/",
				"testCredPrivateEdit","$aFOgeY^$4", "sb_user_21Nov20191","Password#");
		System.out.print("\n The User is [" + user + "]");
	}

	public static void main(String[] args) throws InterruptedException, SQLException {
		getUser();
	}

}
