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

import java.util.LinkedHashMap;
import java.util.Map;

import org.databene.benerator.anno.Source;
import org.json.JSONArray;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.yodlee.yodleeApi.common.Base;
import com.yodlee.yodleeApi.common.Configuration;
import com.yodlee.yodleeApi.helper.TestTemplate;
import com.yodlee.yodleeApi.helper.UserHelper;
import com.yodlee.yodleeApi.pojo.EnvSession;
import com.yodlee.yodleeApi.pojo.HttpMethodParameters;
import com.yodlee.yodleeApi.utils.apiUtils.UserUtils;
import com.yodlee.yodleeApi.utils.commonUtils.CommonUtils;

import io.restassured.response.Response;

/**
 * Modified & Added Test Cases
 * 
 * @author Bhuvaneshwari
 */
public class TestAccessToken extends Base {
	String appIdValid = "10003600"; // To read AppID
	String appIds = "10003600,10003700,10003620"; // To read Mutliple APPID
	String NoAppString, NoAppId = ""; // To validate the AppId string parameter.
	String InvalidAppId = "App@123$"; // To Validate the Invalid AppId
	String Morevalue = "1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21"; // To Validate the Maximum supported
																				// APPID
	String finAppURL = "";
	String tokenValue = "";
	String userSession = "";
	String loginName, password;

	Configuration configurationObj = Configuration.getInstance();
	UserHelper userHelper = new UserHelper();
	UserUtils userUtils = new UserUtils();
	CommonUtils commonUtils = new CommonUtils();

	@BeforeClass
	public void setup() {
		loginName = "accessToken" + System.currentTimeMillis();
		password = "TEST@123";
		String userRegistrationJson = userHelper.userRegistrationObject(loginName, password);

		HttpMethodParameters httpMethodParameters = HttpMethodParameters.builder().build();
		httpMethodParameters.setBodyParams(userRegistrationJson);

		EnvSession session = EnvSession.builder().cobSession(configurationObj.getCobrandSessionObj().getCobSession())
				.path(configurationObj.getCobrandSessionObj().getPath()).build();
		Response response = (Response) userUtils.userRegistrationResponse(session, httpMethodParameters);

		if (response.statusCode() == 200) {
			loginName = response.jsonPath().getString("user.loginName");
			userSession = response.jsonPath().getString("user.session.userSession");
			System.out.println("userSession:" + userSession);
		} else
			Assert.fail("User Registration has failed!Response - " + response.asString());
	}

	@Test(dataProvider = "feeder", enabled = true, priority = 1)
	@Source("\\TestData\\CSVFiles\\User\\accessToken.csv")
	public void testAccessTokenNonSDG(String userStoryID, String testcaseName, String userSession,
			String cobrandSession, String appId, int status, String defectID, String enable) {
		commonUtils.isTCEnabled(enable, userStoryID);
		System.out.println("userStoryID:" + userStoryID);
		int appIdCount = -1;
		String appIdTobeUsed = null;
		if (appId.equalsIgnoreCase("VALID")) // Passing One Valid App ID
		{
			appIdCount = 1;
			appIdTobeUsed = appIdValid;
		} else if (appId.equalsIgnoreCase("MULTIPLE")) // Passing More than One Valid App Id
		{
			appIdCount = 3;
			appIdTobeUsed = appIds;
		} else if (appId.equalsIgnoreCase("INVALID")) // Passing Invalid APP ID
		{
			appIdTobeUsed = InvalidAppId;
		} else if (appId.equalsIgnoreCase("NOAPPID")) // Without Passing AppId Value
		{
			appIdTobeUsed = "NOAPPID";
		} else if (appId.equalsIgnoreCase("NOAPPSTRING")) // Without Passing AppIds String
		{
			appIdTobeUsed = "NOAPPSTRING";
		} else if (appId.equalsIgnoreCase("INVALIDAPPSTRING")) // By passing Wrong AppIds String Name
		{
			appIdTobeUsed = "INVALIDAPPSTRING";
		} else if (appId.equalsIgnoreCase("20STRING")) // Passing More than 20 AppIds
		{
			appIdTobeUsed = Morevalue;
		}

		Map<String, Object> queryParam = new LinkedHashMap<>();

		if (appIdTobeUsed.equalsIgnoreCase("NOAPPID")) {
			queryParam.put("appIds", "");
		} else if (appIdTobeUsed.equalsIgnoreCase("NOAPPSTRING")) {
			queryParam.put("", "");
		} else if (appIdTobeUsed.equalsIgnoreCase("INVALIDAPPSTRING")) {
			queryParam.put("appi", "");
		} else {
			queryParam.put("appIds", appIdTobeUsed);
		}

		HttpMethodParameters httpParams = HttpMethodParameters.builder().build();
		httpParams.setQueryParams(queryParam);

		EnvSession session = EnvSession.builder().cobSession(configurationObj.getCobrandSessionObj().getCobSession())
				.userSession(configurationObj.getCobrandSessionObj().getUserSession())
				.path(configurationObj.getCobrandSessionObj().getPath()).build();

		Response response = userUtils.getAccessTokens(httpParams, session);

		if (appId.equalsIgnoreCase("NOAPPSTRING") || appId.equalsIgnoreCase("INVALIDAPPSTRING")) {
			Assert.assertTrue(response.asString().contains("Required String parameter 'appIds' is not present"),
					response.asString()); // Asserting the Error Msg for Invalid/Wrong and No Filter Name
		} else if (appId.equalsIgnoreCase("INVALID")) {
			Assert.assertTrue(response.asString().contains("Resource not found"), response.asString()); // Asserting the
		} else if (appId.equalsIgnoreCase("NOAPPID")) {
			Assert.assertTrue(response.asString().contains("Invalid value for finappId"), response.asString()); // Asserting
		} else if (appId.equalsIgnoreCase("20STRING")) {
			Assert.assertTrue(response.asString().contains("The maximum number of finappId permitted is 20"),
					response.asString()); // Asserting Error Msg for the AppId more than the Maximum AppId
		} else // Validating the Valid & Multiple AppID
		{
			Assert.assertEquals(response.statusCode(), 200, "Wrong status code for getAccessToken");
			JSONObject test = new JSONObject(response.asString());
			JSONArray accessTokenArray = test.getJSONObject("user").getJSONArray("accessTokens");
			Assert.assertEquals(accessTokenArray.length(), appIdCount, "Mismatch in the number of appIds returned");
			for (int i = 0; i < accessTokenArray.length(); i++) {
				Assert.assertTrue(accessTokenArray.getJSONObject(i).has("appId"));
				Assert.assertTrue(accessTokenArray.getJSONObject(i).has("url"));
				Assert.assertTrue(accessTokenArray.getJSONObject(i).has("value"));
			}
		}
	}
}
