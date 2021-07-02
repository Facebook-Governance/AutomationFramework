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

import static net.javacrumbs.jsonunit.JsonAssert.assertJsonEquals;
import static net.javacrumbs.jsonunit.JsonAssert.when;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import org.databene.benerator.anno.Source;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.yodlee.yodleeApi.assertions.JsonAssertionUtil;
import com.yodlee.yodleeApi.common.Base;
import com.yodlee.yodleeApi.common.Configuration;
import com.yodlee.yodleeApi.constants.ConfigKeysConstants;
import com.yodlee.yodleeApi.constants.Constants;
import com.yodlee.yodleeApi.constants.HttpStatus;
import com.yodlee.yodleeApi.constants.JSONPaths;
import com.yodlee.DBHelper;
import com.yodlee.yodleeApi.helper.SessionHelper;
import com.yodlee.yodleeApi.helper.UserHelper;
import com.yodlee.yodleeApi.pojo.EnvSession;
import com.yodlee.yodleeApi.pojo.HttpMethodParameters;
import com.yodlee.yodleeApi.utils.apiUtils.UserUtils;
import com.yodlee.yodleeApi.utils.commonUtils.CommonUtils;

import io.restassured.response.Response;
import net.javacrumbs.jsonunit.core.Option;

/**
 * @author mallikan
 *
 */
public class TestUserRegistration extends Base {

	Map<String, String> users = new HashMap<String, String>();
	Map<String, String> usersQP = new HashMap<String, String>();
	private static final String USER = "\\TestData\\CSVFiles\\User\\UserRegistration.csv";
	private static final String USER1 = "\\TestData\\CSVFiles\\User\\UserRegistrationWithQueryParam.csv";
	private static final String USER_UNREGISTER = "\\TestData\\CSVFiles\\User\\unRegister.csv";

	CommonUtils commonUtils = new CommonUtils();
	Configuration configurationObj = Configuration.getInstance();
	JsonAssertionUtil jsonAssertionUtil = new JsonAssertionUtil();
	UserUtils userUtils = new UserUtils();
	UserHelper userHelper = new UserHelper();

	// @Info(subfeature = {SubFeature.USER_REGISTER}, userStoryId = "")
	@Test(enabled = true, dataProvider = "feeder", priority = 1)
	@Source(USER)
	public void testUserRegister(String TestCaseId, String testCaseName, String loginNameKey, String loginName,
			String passwordKey, String password, String emailKey, String email, String firstKey, String first,
			String lastKey, String last, String address1Key, String address1, String stateKey, String state,
			String cityKey, String city, String zipKey, String zip, String countryKey, String country,
			String currencyKey, String currency, String timeZoneKey, String timeZone, String dateFormatKey,
			String dateFormat, String localeKey, String locale, String nameKey, String addressKey,
			String preferencesKey, String userKey, String contentType, int status, String filePath, String fileName,
			String Enabled, String DefectID) {

		System.out.println("Test Case Id: " + TestCaseId);
		commonUtils.isTCEnabled(Enabled, TestCaseId);
		String dynamicLoginName = loginName + System.currentTimeMillis();

		try {
			// Creating json

			String userRegisterObjJSON = userHelper.userRegisterJson(loginNameKey, dynamicLoginName, passwordKey,
					password, emailKey, email, firstKey, first, lastKey, last, address1Key, address1, stateKey, state,
					cityKey, city, zipKey, zip, countryKey, country, currencyKey, currency, timeZoneKey, timeZone,
					dateFormatKey, dateFormat, localeKey, locale, nameKey, addressKey, preferencesKey, userKey);

			System.out.println("currency:::::::" + currency + "dateFormat::::::" + dateFormat);

			// Set all to HttpParam
			HttpMethodParameters httpMethodParameters = HttpMethodParameters.builder().build();
			httpMethodParameters.setBodyParams(userRegisterObjJSON);

			EnvSession sessionObj = EnvSession.builder()
					.cobSession(configurationObj.getCobrandSessionObj().getCobSession())
					.path(configurationObj.getCobrandSessionObj().getPath()).build();

			Response newUserResponse = (Response) userUtils.userRegistrationResponse(sessionObj, httpMethodParameters);

			if (newUserResponse.getStatusCode() == 200) {
				String userSession = newUserResponse.jsonPath().getString(JSONPaths.USER_SESSION_TOKEN);

				EnvSession session = EnvSession.builder()
						.cobSession(configurationObj.getCobrandSessionObj().getCobSession()).userSession(userSession)
						.path(configurationObj.getCobrandSessionObj().getPath()).build();

				System.out.println("New user Registered : " + dynamicLoginName);

				Response response = userUtils.unRegisterUser(session);
				if (response.getStatusCode() == 204)
					System.out.println("User Unregistered : " + dynamicLoginName);
				else
					System.out.println("Unregister failed for user : " + dynamicLoginName);
			}

			if (timeZone.trim().equalsIgnoreCase("")) {
				String jsonFile = commonUtils.readJsonFile(commonUtils.getPath(filePath + fileName));
				JSONObject file = new JSONObject(jsonFile);
				JSONObject user1 = file.getJSONObject("user");
				JSONObject preferences = user1.getJSONObject("preferences");

				if (preferences.has("timeZone"))
					preferences.put("timeZone", Configuration.getInstance().getDefaultTimeZone());

				String jsonString = file.toString();

				System.out.println(" JSON String is " + jsonString);
			}

				DBHelper dbHelper = new DBHelper();
				String date = dbHelper.getCobrandAcl(Configuration.getInstance().getCobrandObj().getCobrandId(),
						ConfigKeysConstants.Param_ACl_Val_date);

				String cur = dbHelper.getCobrandAcl(Configuration.getInstance().getCobrandObj().getCobrandId(),
						ConfigKeysConstants.Param_ACL_Val_Currency);

				JSONObject jsonObj = readJsonFile(commonUtils.getPath(filePath + fileName));

				JSONObject jsonObj1 = jsonObj.getJSONObject("user");
				JSONObject jsonObj2 = jsonObj1.getJSONObject("preferences");
				jsonObj2.remove("dateFormat");
				jsonObj2.remove("currency");
				jsonObj2.remove("timeZone");
				jsonObj2.put("dateFormat", date);
				jsonObj2.put("currency", cur);
				jsonObj2.put("timeZone", configurationObj.getDefaultTimeZone());
			
			assertJsonEquals(jsonObj, newUserResponse.asString(), when(Option.IGNORING_ARRAY_ORDER));

		} catch (Exception e) {
			System.out.println("Exception is " + e.getMessage());
		}
	}

	@Test(enabled = true, dataProvider = "feeder", priority = 2)
	@Source(USER1)
	public void testUserRegisterWithQueryParams(String TestCaseId, String testCaseName, String loginNameKey,
			String loginName, String passwordKey, String password, String emailKey, String email, String firstKey,
			String first, String lastKey, String last, String address1Key, String address1, String stateKey,
			String state, String cityKey, String city, String zipKey, String zip, String countryKey, String country,
			String currencyKey, String currency, String timeZoneKey, String timeZone, String dateFormatKey,
			String dateFormat, String localeKey, String locale, String nameKey, String addressKey,
			String preferencesKey, String userKey, String contentType, int statusforPKIOff, int statusforPKIOn,
			String filePath, String fileNameWithPKIOn, String fileNameWithPKIOff, String Enabled, String DefectID) {

		System.out.println("Test Case Id: " + TestCaseId);

		commonUtils.isTCEnabled(Enabled, TestCaseId);
		String dynamicLoginName = loginName;
		if (!testCaseName.equalsIgnoreCase("empty loginname")
				&& !testCaseName.equalsIgnoreCase("login name less than expected length")
				&& !testCaseName.equalsIgnoreCase("login name same as password")) {
			dynamicLoginName = loginName + System.currentTimeMillis();
		}
		String fileName;
		int status;
		if (configurationObj.isQueryParamEnabled()) {
			fileName = fileNameWithPKIOn;
			status = statusforPKIOn;
		} else {
			fileName = fileNameWithPKIOff;
			status = statusforPKIOff;
		}

		String userRegisterJSON = userHelper.userRegisterJson(loginNameKey, dynamicLoginName, passwordKey, password,
				emailKey, email, firstKey, first, lastKey, last, address1Key, address1, stateKey, state, cityKey, city,
				zipKey, zip, countryKey, country, currencyKey, currency, timeZoneKey, timeZone, dateFormatKey,
				dateFormat, localeKey, locale, nameKey, addressKey, preferencesKey, userKey);

		System.out.println("userRegisterJSON::::" + userRegisterJSON);

		LinkedHashMap<String, String> queryParam = new LinkedHashMap<>();
		queryParam.put("registerParam", userRegisterJSON);

		HttpMethodParameters httpParams = HttpMethodParameters.builder().build();
		httpParams.setQueryParams(queryParam);
		EnvSession session = EnvSession.builder().cobSession(configurationObj.getCobrandSessionObj().getCobSession())
				.path(configurationObj.getCobrandSessionObj().getPath()).build();

		Response newUserResponse = (Response) userUtils.userRegistrationResponse(session, httpParams);

		if (newUserResponse.getStatusCode() == 200) {
			String userSession = newUserResponse.jsonPath().getString(JSONPaths.USER_SESSION_TOKEN);

			System.out.println("New user Registered : " + dynamicLoginName);

			session.setUserSession(userSession);

			Response response = userUtils.unRegisterUser(session);
			if (response != null) {
				if (response.getStatusCode() == 204)
					System.out.println("User Unregistered : " + dynamicLoginName);
				else
					System.out.println("Unregister failed for user : " + dynamicLoginName);
			}
		}
		commonUtils.assertStatusCode(newUserResponse, status);

		jsonAssertionUtil.assertResponse(newUserResponse, status, fileName + ".json", filePath);
	}

	@Test(enabled = true, dataProvider = "feeder", priority = 3)
	@Source(USER_UNREGISTER)
	public void unRegisterUserUsingQueryParam(String TestCaseId, String testCaseName, String loginNameKey,
			String loginName, String passwordKey, String password, String emailKey, String email, String firstKey,
			String first, String lastKey, String last, String address1Key, String address1, String stateKey,
			String state, String cityKey, String city, String zipKey, String zip, String countryKey, String country,
			String currencyKey, String currency, String timeZoneKey, String timeZone, String dateFormatKey,
			String dateFormat, String localeKey, String locale, String nameKey, String addressKey,
			String preferencesKey, String userKey, String contentType, int status, String userSession, String filePath,
			String fileName, String fileNameForQueryParamDisabled, String Enabled, String DefectID) {

		String dynamicLoginName = loginName + System.currentTimeMillis();
		commonUtils.isTCEnabled(Enabled, TestCaseId);
		System.out.println("TestCaseId in unRegisterUserUsingQueryParam::" + TestCaseId);
		Response newUserResponse = null;
		String resFile;
		int statusCode;
		try {
			if (configurationObj.isQueryParamEnabled()) {
				resFile = fileName;
				statusCode = status;
			} else {
				resFile = fileNameForQueryParamDisabled;
				statusCode = HttpStatus.BAD_REQUEST;
			}
			// Creating json
			String userRegisterJSON = userHelper.userRegisterJson(loginNameKey, dynamicLoginName, passwordKey, password,
					emailKey, email, firstKey, first, lastKey, last, address1Key, address1, stateKey, state, cityKey,
					city, zipKey, zip, countryKey, country, currencyKey, currency, timeZoneKey, timeZone, dateFormatKey,
					dateFormat, localeKey, locale, nameKey, addressKey, preferencesKey, userKey);

			if (userSession.equalsIgnoreCase("VALID")) {

				// Forming query param
				Map<String, String> userRegisterQueryParam = new HashMap<String, String>();
				userRegisterQueryParam.put("registerParam", userRegisterJSON);

				// Set all to HttpParam

				HttpMethodParameters httpMethodParameters = HttpMethodParameters.builder().build();
				httpMethodParameters.setQueryParams(userRegisterQueryParam);

				// Creating SessionObj
				EnvSession session = EnvSession.builder()
						.cobSession(configurationObj.getCobrandSessionObj().getCobSession())
						.path(configurationObj.getCobrandSessionObj().getPath()).build();

				// Calling Api to Hit
				newUserResponse = (Response) userUtils.userRegistrationResponse(session, httpMethodParameters);

				if (newUserResponse.getStatusCode() == HttpStatus.OK) {
					userSession = newUserResponse.jsonPath().getString(JSONPaths.USER_SESSION_TOKEN);
					System.out.println("New user Registered : " + dynamicLoginName);
				}

			} else {
				userSession = SessionHelper.getSessionToken("user", userSession);
			}
			if (!configurationObj.isQueryParamEnabled() && userSession.equalsIgnoreCase("VALID")) {

				jsonAssertionUtil.assertResponse(newUserResponse, statusCode, resFile, filePath);

			} else {

				// creating session object
				System.out.println("CobsessionOBj" + configurationObj.getCobrandSessionObj().getCobSession());
				System.out.println("UserSession" + userSession);
				EnvSession sessionObj = EnvSession.builder()
						.cobSession(configurationObj.getCobrandSessionObj().getCobSession()).userSession(userSession)
						.path(configurationObj.getCobrandSessionObj().getPath()).build();

				Response response = userUtils.unRegisterUser(sessionObj);
				if (response.getStatusCode() != 204) {
					jsonAssertionUtil.assertResponse(response, HttpStatus.UNAUTHORISED, resFile, filePath);

				} else
					commonUtils.assertStatusCode(response, statusCode);
			}

		} catch (Exception e) {
			System.out.println("Exception is " + e.getMessage());
			Assert.fail("Test Case Failed : " + TestCaseId);
		}
	}

	public String getPath(String filePath) {
		if (filePath.contains(System.getProperty("user.dir"))) {
			return filePath;
		}
		String workingDir = System.getProperty("user.dir");

		if (filePath.contains("..")) {
			filePath = filePath.substring(2);
		}

		if (filePath.contains(".json"))
			filePath = workingDir + filePath;
		else
			filePath = workingDir + filePath + ".json";

		return filePath;
	}

	public JSONObject readJsonFile(String filePath) throws FileNotFoundException {
		String sCurrentLine;
		String jsonFile = "";
		try (BufferedReader br = new BufferedReader(new FileReader(getPath(filePath)))) {
			while ((sCurrentLine = br.readLine()) != null) {
				jsonFile += sCurrentLine;
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
		JSONObject jsonObject = new JSONObject(jsonFile);
		return jsonObject;
	}

}
