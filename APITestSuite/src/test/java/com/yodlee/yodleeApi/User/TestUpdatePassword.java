/*******************************************************************************
 *
 * * Copyright (c) 2019 Yodlee, Inc. All Rights Reserved.
 *
 * *
 *
 * * This software is the confidential and proprietary information of Yodlee, Inc.
 *
 * * Use is subject to license terms.
 *
 *******************************************************************************/

package com.yodlee.yodleeApi.User;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import org.databene.benerator.anno.Source;
import org.json.JSONException;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.jcraft.jsch.Session;
import com.yodlee.yodleeApi.assertions.JsonAssertionUtil;
import com.yodlee.yodleeApi.common.Base;
import com.yodlee.yodleeApi.common.Configuration;
import com.yodlee.yodleeApi.helper.SessionHelper;
import com.yodlee.yodleeApi.helper.TestTemplate;
import com.yodlee.yodleeApi.helper.UserHelper;
import com.yodlee.yodleeApi.pojo.EnvSession;
import com.yodlee.yodleeApi.pojo.HttpMethodParameters;
import com.yodlee.yodleeApi.utils.apiUtils.UserUtils;
import com.yodlee.yodleeApi.utils.commonUtils.CommonUtils;

import io.restassured.response.Response;

/**
 * @author bhuvaneshwari
 *
 */
public class TestUpdatePassword extends Base {

	String loginName = "passuserA" + System.currentTimeMillis();
	String password = "Upassword@123";
	String otherUser = "ysltokenss1";
	String otherPassword = "TEST@123";
	String tokenValue = null;

	static String userSession = null;
	static String otherUserSession = null;

	Configuration configurationObj = Configuration.getInstance();
	UserHelper userHelper = new UserHelper();
	UserUtils userUtils = new UserUtils();
	CommonUtils commonUtils = new CommonUtils();
	JsonAssertionUtil jsonAssertionUtil = new JsonAssertionUtil();

	@BeforeClass
	public void setUp() throws JSONException {

		String userRegistrationJson = userHelper.userRegistrationObject(loginName, password);

		HttpMethodParameters httpMethodParameters = HttpMethodParameters.builder().build();
		httpMethodParameters.setBodyParams(userRegistrationJson);
		/*
		 * EnvSession session = new
		 * EnvSession(configurationObj.getCobrandSessionObj().getCobSession(), null,
		 * configurationObj.getCobrandSessionObj().getPath());
		 */

		EnvSession session = EnvSession.builder().cobSession(configurationObj.getCobrandSessionObj().getCobSession())
				.path(configurationObj.getCobrandSessionObj().getPath()).build();

		Response response = (Response) userUtils.userRegistrationResponse(session, httpMethodParameters);

		if (response.statusCode() == 200) {
			loginName = response.jsonPath().getString("user.loginName");
			userSession = response.jsonPath().getString("user.session.userSession");
			System.out.println("userSession:" + userSession);
		}

		userRegistrationJson = userHelper.userRegistrationObject(otherUser, otherPassword);

		httpMethodParameters.setBodyParams(userRegistrationJson);
		EnvSession userSession = EnvSession.builder()
				.cobSession(configurationObj.getCobrandSessionObj().getCobSession())
				.path(configurationObj.getCobrandSessionObj().getPath()).build();

		response = (Response) userUtils.userRegistrationResponse(session, httpMethodParameters);

		if (response.statusCode() == 200) {
			otherUser = response.jsonPath().getString("user.loginName");
			otherUserSession = response.jsonPath().getString("user.session.userSession");
			System.out.println("otherUserSession:" + otherUserSession);
		}

		HttpMethodParameters httpParams = HttpMethodParameters.builder().build();

		Map<String, String> queryParam = new HashMap<>();
		queryParam.put("loginName", otherUser);
		httpParams.setQueryParams(queryParam);
		Response token = userUtils.getUserCredentialToken(httpParams, userSession);

		tokenValue = token.jsonPath().getString("token");
		System.out.println("tokenValue:" + tokenValue);

	}

	@Test(dataProvider = "feeder", enabled = true, priority = 1)
	@Source("\\TestData\\CSVFiles\\User\\updatePassword.csv")

	public void updatePassword(String userStoryID, String testcaseName, String loginNames, String oldPassword,
			String newPassword, int status, String responseFile, String responsePath, String enable) {

		System.out.println("userStoryID:" + userStoryID);
		commonUtils.isTCEnabled(enable, userStoryID);

		String updatePasswordJSON = userHelper.updatePasswordwithOldPasswordJSON(loginName, oldPassword, newPassword);

		HttpMethodParameters httpParams = HttpMethodParameters.builder().build();
		httpParams.setBodyParams(updatePasswordJSON);

		EnvSession session = EnvSession.builder().cobSession(configurationObj.getCobrandSessionObj().getCobSession())
				.userSession(userSession).path(configurationObj.getCobrandSessionObj().getPath()).build();

		Response response = userUtils.updateUserPassword(session, httpParams);

		jsonAssertionUtil.assertResponse(response, response.getStatusCode(), responseFile, responsePath);

	}

	@Test(dataProvider = "feeder", enabled = true, priority = 2)
	@Source("\\TestData\\CSVFiles\\User\\resetPassword.csv")
	public void resetPasswordParameterName(String userStoryID, String testcaseName, String cobrandSession,
			String loginNameKey, String loginNameValue, String tokenKey, String tokenValues, String newPasswordKey,
			String newPasswordValue, String status, String responseFile, String responsePath, String enable,
			String defectID) {
		System.out.println("userStoryID:" + userStoryID);
		commonUtils.isTCEnabled(enable, userStoryID);

		String updatePasswordJSON = null;
		if (tokenValues.trim().equalsIgnoreCase("VALID")) {
			updatePasswordJSON = userHelper.getResetPassword(loginNameKey, loginNameValue, tokenKey, tokenValue,
					newPasswordKey, newPasswordValue);
		} else {
			updatePasswordJSON = userHelper.getResetPassword(loginNameKey, loginNameValue, tokenKey, tokenValues,
					newPasswordKey, newPasswordValue);
		}

		HttpMethodParameters httpMethodParameters = HttpMethodParameters.builder().build();
		httpMethodParameters.setBodyParams(updatePasswordJSON);

		String cobSessionToken = SessionHelper.getSessionToken(cobrandSession, "cob");

		EnvSession session = EnvSession.builder().cobSession(cobSessionToken).userSession(otherUserSession)
				.path(configurationObj.getCobrandSessionObj().getPath()).build();

		Response response = userUtils.updateUserPassword(session, httpMethodParameters);
		jsonAssertionUtil.assertResponse(response, response.getStatusCode(), responseFile, responsePath);

	}

	@Test(dataProvider = "feeder", enabled = true, priority = 3)
	@Source("\\TestData\\CSVFiles\\User\\withoutCobSessionParameter.csv")
	public void changePasswordWithoutCobAndUserkey(String userStoryID, String testcaseName, String cobSessionKey,
			String cobSessionValue, String userSessionKey, String userSessionValue, String loginNameKey,
			String loginNameValue, String oldPasswordKey, String oldPasswordValue, String newPasswordKey,
			String newPasswordValue, String status, String responseFile, String responsePath, String enable,
			String defectID) {
		System.out.println("userStoryID:" + userStoryID);
		commonUtils.isTCEnabled(enable, userStoryID);

		String updatePasswordJSON = null;
		if (loginNameValue.trim().equalsIgnoreCase("VALID")) {
			updatePasswordJSON = userHelper.updatePasswordwithOldPassword(loginNameKey, loginName, oldPasswordKey,
					oldPasswordValue, newPasswordKey, newPasswordValue);
		} else {
			updatePasswordJSON = userHelper.updatePasswordwithOldPassword(loginNameKey, loginNameValue, oldPasswordKey,
					oldPasswordValue, newPasswordKey, newPasswordValue);

		}
		System.out.println("updatePasswordJSON:" + updatePasswordJSON);
		
		String cobSession= SessionHelper.getSessionToken(cobSessionValue, "cob");
		String userSessionValues = SessionHelper.getSessionToken(userSessionValue, "user");
		
		 /*userSessionKey = SessionHelper.getSessionToken(userSessionKey, "user");
		 cobSessionKey=SessionHelper.getSessionToken(userSessionKey, "user");*/
		 
		 if(userSessionKey.equalsIgnoreCase("VALID")) {
			 userSessionKey=null;
		 }
		 
		 if(cobSessionKey.equalsIgnoreCase("VALID")) {
			 cobSessionKey=null;
		 }
		 
		 
		HttpMethodParameters httpMethodParameters = HttpMethodParameters.builder().build();
		httpMethodParameters.setBodyParams(updatePasswordJSON);
		EnvSession session =null;
		if (userSessionValue.trim().equalsIgnoreCase("CLASSUSER")) {

			 session = EnvSession.builder().cobSession(cobSession)
					.cobSessionKey(cobSessionKey).userSession(userSession).userSessionKey(userSessionKey)
					.path(configurationObj.getCobrandSessionObj().getPath()).build();
			System.out.println("session with class user:::"+session.toString()+"userStoryID::"+userStoryID);
		} else {
			 session = EnvSession.builder().cobSession(cobSession)
					.cobSessionKey(cobSessionKey).userSession(userSessionValues).userSessionKey(userSessionKey)
					.path(configurationObj.getCobrandSessionObj().getPath()).build();

		}
		Response response = userUtils.updateUserPassword(session, httpMethodParameters);
		jsonAssertionUtil.assertResponse(response, response.getStatusCode(), responseFile, responsePath);

	}

}
