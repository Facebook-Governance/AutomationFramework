/**
 * Copyright (c) 2014 Yodlee, Inc. All Rights Reserved.
 *
 * This software is the confidential and proprietary information of Yodlee, Inc. 
 * Use is subject to license terms.
 */
package com.omni.pfm.unregister;

import io.restassured.RestAssured;
import io.restassured.response.Response;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;



public class RestUtil {
    public static Properties properties = new Properties();

    /*private static Properties props= new Properties();
    static{
    	try {
    		props.load(new FileInputStream(new File("config_file")));
    	} catch (IOException e) {
    		// TODO Auto-generated catch block
    		e.printStackTrace();
    	}
    }*/
    public static Response get(Map<String, String> params, String path) {
	Response response = RestAssured.given().log().all().queryParams(params).header("Accept", "application/json").get(path);
	return response;
    }

    public static Response post(Map<String, String> params, String path) {
	Response response = RestAssured.given().log().all().formParams(params).post(path);
	return response;
    }

    public static String getCobrandSessionToken() {

	HashMap<String, String> params = new HashMap<String, String>();
	params.put("cobrandLogin", properties.getProperty("coBrandLogin"));
	params.put("cobrandPassword", properties.getProperty("coBrandPassword"));

	Response response = RestUtil.post(params,
		"https://rest.autopfmc.corp.yodlee.com:3443/services/srest/ymc/v1.0/authenticate/coblogin");
	response.then().log().all();
	return response.jsonPath().getString("cobrandConversationCredentials.sessionToken");
    }

    public static String testlogin(String cobSession, String loginName, String password) throws Exception {

	HashMap<String, String> params = new HashMap<String, String>();
	params.put("cobSessionToken", cobSession);
	params.put("login", loginName);
	params.put("password", password);

	Response login = RestUtil.post(params, "https://rest.autopfmc.corp.yodlee.com:3443/services/srest/ymc/v1.0/authenticate/login");
	login.then().log().all();
	return login.jsonPath().getString("userContext.conversationCredentials.sessionToken");
    }

    public static void testUnregisterUser(String cobSession, String userSession) {

	HashMap<String, String> params = new HashMap<String, String>();
	params.put("cobSessionToken", cobSession);
	params.put("userSessionToken", userSession);

	Response logout = RestUtil.post(params,
		"https://rest.autopfmc.corp.yodlee.com:3443/services/srest/ymc/v1.0/jsonsdk/UserRegistration/unregister");
	logout.then().log().all();
    }

    public static void main(String[] args) throws Exception {
	InputStream fis = RestUtil.class.getClassLoader().getResourceAsStream("EnvironmentDetails/AutoNPR.Properties");
	try {
	    properties.load(fis);
	} catch (IOException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	}
	System.out.println(properties.getProperty("coBrandLogin"));
	String cobrandSession = getCobrandSessionToken();
	String userSession = testlogin(cobrandSession, "YCOM1467120433712", "Test@123");
	testUnregisterUser(cobrandSession, userSession);
    }
}
