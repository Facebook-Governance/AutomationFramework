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

package com.yodlee.yodleeApi.Cobrand;

import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;

import org.databene.benerator.anno.Source;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.SkipException;
import org.testng.annotations.Test;

import com.yodlee.yodleeApi.assertions.JsonAssertionUtil;
import com.yodlee.yodleeApi.common.Configuration;
import com.yodlee.yodleeApi.helper.TestTemplate;
import com.yodlee.yodleeApi.constants.HttpStatus;
import com.yodlee.yodleeApi.helper.CobrandHelper;
import com.yodlee.yodleeApi.pojo.Cobrand;
import com.yodlee.yodleeApi.pojo.EnvSession;
import com.yodlee.yodleeApi.pojo.HttpMethodParameters;
import com.yodlee.yodleeApi.helper.SessionHelper;
import io.restassured.response.Response;

/**
 * Test Class that verifies Cobrand Login and logout functionality.
 *
 *@author bhuvaneshwari
 *
 */

public class TestCobLogin extends TestTemplate {
	
	Configuration configurationObj=Configuration.getInstance();
	JsonAssertionUtil jsonPath =new JsonAssertionUtil();

	Boolean localePresent;
	String responseFile;

	/**
	 * This method is used for Cobrand Login with Body param
	 * 
	 * @param testCaseId
	 * @param testCaseName
	 * @param API
	 * @param cobrandLoginKey
	 * @param cobrandLogin
	 * @param passwordKey
	 * @param password
	 * @param locale_key
	 * @param locale
	 * @param userSessionKey
	 * @param userSessionValue
	 * @param contentType
	 * @param resFile
	 * @param resFileForInvalidLocale
	 * @param filePath
	 * @param status
	 * @param defectID
	 * @param enabled
	 */
	@Test(enabled = true, dataProvider = "feeder", alwaysRun = true)
	@Source("\\TestData\\CSVFiles\\Cobrand\\cobLoginBodyParam.csv")
	public void cobrandLoginUsingBodyParam(String testCaseId, String testCaseName, String API, String cobrandLoginKey,
			String cobrandLogin, String passwordKey, String password, String locale_key, String locale,
			String userSessionKey, String userSessionValue, String contentType, String resFile,
			String resFileForInvalidLocale, String filePath, int status, String defectID, String enabled) {

		commonUtils.isTCEnabled(enabled, testCaseId);
		System.out.println("Test Case Id is " + testCaseId + " test case is " + testCaseName);

		Response response = doCobrandLogin(testCaseId, testCaseName, cobrandLogin, password, locale, cobrandLoginKey, passwordKey, locale_key);
		
		if (configurationObj.getCobrandObj().getLocale().contains(locale) || locale.equalsIgnoreCase("fr_CA"))
		 {
			localePresent = true;
			responseFile = resFile;
		} else {
			localePresent = false;
			responseFile = resFileForInvalidLocale;
		}
		
		jsonPath.assertResponse(response, status, responseFile, filePath);
	}

	/**
	 * This method is used for Cobrand Login using Query param
	 * 
	 * @param testCaseId
	 * @param testCaseName
	 * @param API
	 * @param cobrandLoginKey
	 * @param cobrandLogin
	 * @param passwordKey
	 * @param password
	 * @param locale_key
	 * @param locale
	 * @param userSessionKey
	 * @param userSessionValue
	 * @param contentType
	 * @param resFileQueryParamEnabled
	 * @param filePath
	 * @param statusQueryParamEnabled
	 * @param defectID
	 * @param enabled
	 * @throws FileNotFoundException
	 */
	@Test(enabled = true, dataProvider = "feeder", alwaysRun = true)
	@Source("\\TestData\\CSVFiles\\Cobrand\\cobLoginQueryParam.csv")
	public void cobrandLoginUsingQueryParam(String testCaseId, String testCaseName, String API, String cobrandLoginKey,
			String cobrandLogin, String passwordKey, String password, String locale_key, String locale,
			String userSessionKey, String userSessionValue, String contentType, String resFileQueryParamEnabled,
			String filePath, int statusQueryParamEnabled, String defectID, String enabled)
			throws FileNotFoundException {
		commonUtils.isTCEnabled(enabled, testCaseId);
		
		System.out.println("Inside query param::"+configurationObj.isQueryParamEnabled());
		
		if (!configurationObj.isQueryParamEnabled())
		{
			String message = "QueryParam is disabled for this environment";
			Reporter.log(message,true);
			throw new SkipException(message);
		}

		System.out.println("Test Case Id is " + testCaseId + " test case is " + testCaseName);
		
	//	if (configurationObj.getDefaultSupportedLocaleList().contains(locale)) {

		if (cobrandLogin.trim().equalsIgnoreCase("VALID")) {
			cobrandLogin = Configuration.getInstance().getCobrandObj().getCobrandLogin();
		}
		if (password.trim().equalsIgnoreCase("VALID")) {
			password = Configuration.getInstance().getCobrandObj().getCobrandPassword();
		}
		Map<String, String> queryParams = new HashMap<>();
		queryParams.put(cobrandLoginKey, cobrandLogin);
		queryParams.put(passwordKey, password);
		queryParams.put(locale_key, locale);

		HttpMethodParameters httpMethodParameters = HttpMethodParameters.builder().build();
		httpMethodParameters.setQueryParams(queryParams);

		Response response = (Response) cobrandUtils.getCobrandLoginResponse(httpMethodParameters,
				configurationObj.getCobrandSessionObj().getPath());
		response.then().log().all();

		JsonAssertionUtil jsonPath =new JsonAssertionUtil();
		jsonPath.assertResponse(response, statusQueryParamEnabled, resFileQueryParamEnabled, filePath);
		
		/*}else {
			String message = "Invalid locale";
			Reporter.log(message,true);
			throw new SkipException(message);
		}*/
		

	}

	/**
	 * This method is used for Cobrand Logout
	 * 
	 * @param testCaseId
	 * @param testCaseName
	 * @param cobrandLoginKey
	 * @param cobrandLogin
	 * @param passwordKey
	 * @param password
	 * @param localeKey
	 * @param locale
	 * @param cobSessionFromCsv
	 * @param userSessionKey
	 * @param userSessionValue
	 * @param contentType
	 * @param resFile
	 * @param filePath
	 * @param status
	 * @param defectID
	 * @param enabled
	 */
	@Test(enabled = true, dataProvider = "feeder", alwaysRun = true)
	@Source("\\TestData\\CSVFiles\\Cobrand\\cobLogout.csv")
	public void cobrandLogout(String testCaseId, String testCaseName, String cobrandLoginKey, String cobrandLogin,
			String passwordKey, String password, String localeKey, String locale, String cobSessionFromCsv,
			String userSessionKey, String userSessionValue, String contentType, String resFile, String filePath,
			int status, String defectID, String enabled) {
		Configuration configurationObj=Configuration.getInstance();
		commonUtils.isTCEnabled(enabled, testCaseId);
		Response response = doCobrandLogin(testCaseId, testCaseName, cobrandLogin, password, locale, cobrandLoginKey, passwordKey, localeKey);

	//	if (configurationObj.getDefaultSupportedLocaleList().contains(locale)) {
		String cobSession = SessionHelper.getSessionToken(cobSessionFromCsv, "cob");
		if (cobSessionFromCsv.equalsIgnoreCase("VALID")) {
			JSONObject jsonObj = new JSONObject(response.asString());
			JSONObject session = jsonObj.getJSONObject("session");
			cobSession = session.getString("cobSession");
		}
		System.out.println("Cobsession Invalid::" + cobSession);
	//	EnvSession cobLogoutSessionObj = new EnvSession(cobSession, null, configurationObj.getCobrandSessionObj().getPath());
		
		EnvSession cobLogoutSessionObj = EnvSession.builder().cobSession(cobSession)
				.path(configurationObj.getCobrandSessionObj().getPath()).build();
		Response logoutResponse = cobrandUtils.cobrandLogout(cobLogoutSessionObj);

//		if (logoutResponse.getStatusCode() == HttpStatus.NO_CONTENT_204) {
//			Assert.assertEquals(logoutResponse.getStatusCode(), HttpStatus.NO_CONTENT_204);
//		} else {
//			JsonPathAssertionUtil jsonPath = new JsonPathAssertionUtil();
//			jsonPath.assertResponse(response, HttpStatus.BAD_REQUEST,resFile, filePath);
//		}
		
		JsonAssertionUtil jsonPath =new JsonAssertionUtil();
		
		if (logoutResponse.getStatusCode() == HttpStatus.NO_CONTENT_204) {
			Assert.assertEquals(logoutResponse.getStatusCode(), HttpStatus.NO_CONTENT_204);
		} else {
			if (!commonUtils.saveJson(logoutResponse.prettyPrint(), filePath, resFile))
			//	jsonPathAssertionUtil.assertLenientJSON(logoutResponse, HttpStatus.BAD_REQUEST, resFile, filePath);
		
			jsonPath.assertResponse(logoutResponse, HttpStatus.BAD_REQUEST, resFile, filePath);
			else
				System.out.println("Response files are newly created");
		}
		/*}else {
			String message = "Invalid locale";
			Reporter.log(message,true);
			throw new SkipException(message);
		}*/
		
	}

	private Response doCobrandLogin(String testCaseId, String testCaseName, String cobrandLogin, String password,
			String locale, String cobrandLoginKey, String passwordKey, String localeKey) {
		Cobrand cobrand = Cobrand.builder().build();
		cobrand.setCobrandLoginKey(cobrandLoginKey);
		cobrand.setCobrandPasswordKey(passwordKey);
		cobrand.setLocaleKey(localeKey);

		System.out.println("Test Case Id is " + testCaseId + " test case is " + testCaseName);

		if (cobrandLogin.trim().equalsIgnoreCase("VALID")) {
			cobrandLogin = Configuration.getInstance().getCobrandObj().getCobrandLogin();
		}
		if (password.trim().equalsIgnoreCase("VALID")) {
			password = Configuration.getInstance().getCobrandObj().getCobrandPassword();
		}
		cobrand.setCobrandLogin(cobrandLogin);
		cobrand.setCobrandPassword(password);
		cobrand.setLocale(locale);

		CobrandHelper cobrandHelper= new CobrandHelper();
		String jsonObject = cobrandHelper.createCobrandLoginJson(cobrand);

		HttpMethodParameters httpParms = HttpMethodParameters.builder().build();
		httpParms.setBodyParams(jsonObject);

		Response response = (Response) cobrandUtils.getCobrandLoginResponse(httpParms,
				configurationObj.getCobrandSessionObj().getPath());
		response.then().log().all();
		return response;
	}
}
