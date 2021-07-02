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
import org.jose4j.lang.JoseException;
import org.json.JSONException;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.yodlee.yodleeApi.assertions.JsonAssertionUtil;
import com.yodlee.yodleeApi.common.Base;
import com.yodlee.yodleeApi.common.Configuration;
import com.yodlee.yodleeApi.constants.Constants;
import com.yodlee.yodleeApi.constants.HttpStatus;
import com.yodlee.yodleeApi.constants.URLConstants;
import com.yodlee.yodleeApi.helper.SessionHelper;
import com.yodlee.yodleeApi.helper.TestTemplate;
import com.yodlee.yodleeApi.helper.UserHelper;
import com.yodlee.yodleeApi.pojo.EnvSession;
import com.yodlee.yodleeApi.pojo.HttpMethodParameters;
import com.yodlee.yodleeApi.utils.apiUtils.TransactionUtils;
import com.yodlee.yodleeApi.utils.apiUtils.UserUtils;
import com.yodlee.yodleeApi.utils.commonUtils.CommonUtils;

import io.restassured.response.Response;

/**
 * Test Class that verifies User Login and logout functionality.
 *
 * @author Bhuvaneshwari
 */

public class TestUserLogin extends Base {

	CommonUtils commonUtils = new CommonUtils();
	UserUtils userUtils = new UserUtils();
	UserHelper userHelper = new UserHelper();
	JsonAssertionUtil jsonAssertionUil = new JsonAssertionUtil();
	Configuration configurationObj = Configuration.getInstance();
	TransactionUtils transactionUtils = new TransactionUtils();

	@Test(enabled = true, dataProvider = "feeder", alwaysRun = false, priority = 1)
	@Source("\\TestData\\CSVFiles\\User\\testUserLogin.csv")
	public void testUserLoginUsingBodyParam(String testCaseId, String tcName, String loginNameKey, String loginName,
			String loginNameForLogin, String passwordKey, String password, String passwordLogin, String localeKey,
			String locale, String email, String cobSession, int statusCode, String contentType, String filePath,
			String resFile, String resFileForInvalidLocale, String defectId, String enabled, String specific)
			throws JoseException {
		commonUtils.isTCEnabled(enabled, testCaseId);
		String logData = "";

		System.out.println("TestCase id is " + testCaseId + " TestCase Name is " + tcName);

		// if (configurationObj.getDefaultSupportedLocaleList().contains(locale)) {

		JSONObject jsonObject = null;
		String errorCode;
		String updateLoginName = loginName;
		String updateLoginNameForLogin = loginNameForLogin;
		long dynamicValue = System.currentTimeMillis();
		String sessionToken = SessionHelper.getSessionToken(cobSession, "cob");
		System.out.println("CobSession :: " + sessionToken);
		if (!tcName.equalsIgnoreCase("withoutUserName") && !tcName.equalsIgnoreCase("NullUserNameInvalidCob")
				&& !tcName.equalsIgnoreCase("NullUserNameInvalidCob")
				&& !tcName.equalsIgnoreCase("LoginNamePasswordEmpty")) {
			updateLoginName = loginName + dynamicValue;
			updateLoginNameForLogin = loginNameForLogin + dynamicValue;
		}
		boolean isUserAlreadyExists = false;
		Response newUserResponse = null;
		if (!specific.equalsIgnoreCase("withoutRegister")) {
			String userRegisterObjJSON = userHelper.createJSONforUserRegister(loginNameKey, updateLoginName,
					passwordKey, password, email);

			HttpMethodParameters httpParams = HttpMethodParameters.builder().build();
			httpParams.setBodyParams(userRegisterObjJSON);
			EnvSession session = EnvSession.builder().cobSession(sessionToken)
					.path(configurationObj.getCobrandSessionObj().getPath() + URLConstants.USER_REGISTER).build();

			newUserResponse = (Response) userUtils.userRegistrationResponse(session, httpParams);
			System.out.println("Inside if block");

		} else {
			isUserAlreadyExists = true;
		}

		if (!specific.equalsIgnoreCase("withoutRegister")) {
			if (newUserResponse.statusCode() == HttpStatus.BAD_REQUEST) {
				try {

					JSONObject alreadyExists = new JSONObject(newUserResponse.asString());
					errorCode = alreadyExists.getString("errorCode");
					if (errorCode.equalsIgnoreCase("Y400")) {
						isUserAlreadyExists = true;
					}

				} catch (Exception e) {
					System.out.println("Username is Unique");
					logData = logData + "Username is Unique";
				}
			}
		}

		if (specific.equalsIgnoreCase("withoutRegister")) {
			String userParam = userHelper.createJSONforUserLogin(loginNameKey, updateLoginNameForLogin, passwordKey,
					password, localeKey, locale);

			HttpMethodParameters httpParams = HttpMethodParameters.builder().build();
			httpParams.setBodyParams(userParam);
			EnvSession session = EnvSession.builder().cobSession(sessionToken)
					.path(configurationObj.getCobrandSessionObj().getPath()).build();

			Response userLoginResponse = (Response) userUtils.userLoginResponse(httpParams, session);

			jsonAssertionUil.assertResponse(userLoginResponse, statusCode, resFile, filePath);

		} else if (specific.equalsIgnoreCase("invalidUrl")) {
			String userParam = userHelper.createJSONforUserLogin(loginNameKey, updateLoginNameForLogin, passwordKey,
					password, localeKey, locale);

			HttpMethodParameters httpParams = HttpMethodParameters.builder().build();
			httpParams.setBodyParams(userParam);
			/*
			 * EnvSession session = new EnvSession(sessionToken, null,
			 * configurationObj.getCobrandSessionObj().getPath() + "v1/users/login");
			 */

			EnvSession session = EnvSession.builder().cobSession(sessionToken)
					.path(configurationObj.getCobrandSessionObj().getPath() + "v1/users/login").build();

			Response userLoginResponse = (Response) userUtils.userLoginResponse(httpParams, session);

			jsonAssertionUil.assertResponse(userLoginResponse, statusCode, resFile, filePath);

		} else if (newUserResponse.statusCode() == HttpStatus.OK || isUserAlreadyExists) {

			/*
			 * * * if (InitialLoader.defaultLocaleList.contains(locale) ||
			 * InitialLoader.supportedLocaleList.contains(locale) ||
			 * locale.equalsIgnoreCase("null") || locale.equalsIgnoreCase("invalid") ||
			 * locale.contains(",") || locale.equalsIgnoreCase("") ||
			 * locale.equalsIgnoreCase("en_US")) { localePresent = true; responseFile =
			 * resFile; } else { localePresent = false; responseFile =
			 * resFileForInvalidLocale; }
			 */

			String userParam = userHelper.createJSONforUserLogin(loginNameKey, updateLoginNameForLogin, passwordKey,
					passwordLogin, localeKey, locale);

			HttpMethodParameters httpParams = HttpMethodParameters.builder().build();
			httpParams.setBodyParams(userParam);
			EnvSession session = EnvSession.builder().cobSession(sessionToken)
					.path(configurationObj.getCobrandSessionObj().getPath()).build();

			Response userLoginResponse = (Response) userUtils.userLoginResponse(httpParams, session);

			logData = logData + userLoginResponse.then().extract().statusLine() + "\n";
			logData = logData + userLoginResponse.then().extract().headers() + "\n";
			logData = logData + userLoginResponse.then().extract().asString() + "\n";

			// if (localePresent) {
			if (userLoginResponse.statusCode() == HttpStatus.OK) {
				jsonObject = new JSONObject(userLoginResponse.asString());
				JSONObject user = jsonObject.getJSONObject("user");
				JSONObject session1 = user.getJSONObject("session");
				String userSession = session1.getString("userSession");
				EnvSession session2 = EnvSession.builder().cobSession(sessionToken).userSession(userSession)
						.path(configurationObj.getCobrandSessionObj().getPath()).build();
				userUtils.unRegisterUser(session2);

			}
			if ((userLoginResponse.statusCode() == HttpStatus.BAD_REQUEST) && !isUserAlreadyExists) {
				jsonObject = new JSONObject(newUserResponse.asString());
				JSONObject user = jsonObject.getJSONObject("user");
				JSONObject ses = user.getJSONObject("session");
				String userSession = ses.getString("userSession");

				EnvSession session3 = EnvSession.builder().cobSession(sessionToken).userSession(userSession)
						.path(configurationObj.getCobrandSessionObj().getPath()).build();
				userUtils.unRegisterUser(session3);

			}
			jsonAssertionUil.assertResponse(userLoginResponse, statusCode, resFile, filePath);

			/*
			 * * * JsonPathAssertionUtil jsonPath = new JsonPathAssertionUtil();
			 * jsonPath.assertResponse(userLoginResponse, statusCode, resFile + ".json",
			 * filePath);
			 */

		} else if (newUserResponse.statusCode() == HttpStatus.BAD_REQUEST) {
			jsonAssertionUil.assertResponse(newUserResponse, statusCode, resFile, filePath);

			/**
			 * JsonPathAssertionUtil jsonPath = new JsonPathAssertionUtil();
			 * jsonPath.assertResponse(newUserResponse, statusCode, resFile + ".json",
			 * filePath);
			 */

		}

		/**
		 * } else { String message = "Invalid locale"; Reporter.log(message, true);
		 * throw new SkipException(message); }
		 */

	}

	@Test(enabled = true, dataProvider = "feeder", alwaysRun = false, priority = 1)
	@Source("\\TestData\\CSVFiles\\User\\testUserLoginUsingQueryParam.csv")
	public void testUserLoginUsingQueryParam(String testCaseId, String tcName, String loginNameKey, String loginName,
			String passwordKey, String password, String localeKey, String locale, String email, int statusCode,
			String contentType, String filePath, String resFile, String resFileForQueryParamDisabled, String defectId,
			String enabled) throws JoseException {
		commonUtils.isTCEnabled(enabled, testCaseId);

		System.out.println("TestCase id is " + testCaseId + " TestCase Name is " + tcName);

		// if (configurationObj.getDefaultSupportedLocaleList().contains(locale)) {

		String userSession = null;
		JSONObject jsonObject = null;
		String fileName;
		int status;
		if (configurationObj.isQueryParamEnabled()) {
			fileName = resFile;
			status = statusCode;
		} else {
			fileName = resFileForQueryParamDisabled;
			status = HttpStatus.BAD_REQUEST;
		}
		String userRegisterObjJSON = userHelper.createJSONforUserRegister(loginNameKey, loginName, passwordKey,
				password, email);

		Map<String, String> queryParam = new HashMap<>();
		queryParam.put("registerParam", userRegisterObjJSON.toString());

		HttpMethodParameters httpMethodParameters = HttpMethodParameters.builder().build();
		httpMethodParameters.setQueryParams(queryParam);
		EnvSession session = EnvSession.builder().cobSession(configurationObj.getCobrandSessionObj().getCobSession())
				.path(configurationObj.getCobrandSessionObj().getPath()).build();
		;

		Response newUserResponse = (Response) userUtils.userLoginResponse(httpMethodParameters, session);

		String errorCode;
		boolean flag = false;
		if (configurationObj.isQueryParamEnabled()) {
			if (newUserResponse.statusCode() == HttpStatus.BAD_REQUEST) {
				try {
					JSONObject alreadyExists = new JSONObject(newUserResponse.asString());
					errorCode = alreadyExists.getString("errorCode");
					if (errorCode.equalsIgnoreCase("Y400")) {
						flag = true;
					}

				} catch (Exception e) {
					System.out.println("Username is Unique");
				}
			}
			if (newUserResponse.statusCode() == HttpStatus.OK || flag) {

				Map<String, String> userLoginMap = new HashMap<>();
				if (loginNameKey != null && loginNameKey != "")
					userLoginMap.put(loginNameKey, loginName);
				if (passwordKey != null && passwordKey != "")
					userLoginMap.put(passwordKey, password);
				if (localeKey != "" && localeKey != null)
					userLoginMap.put(localeKey, locale);

				HttpMethodParameters httpParams = HttpMethodParameters.builder().build();
				httpParams.getFormParams().get(userLoginMap);
				Response userLoginResponse = (Response) userUtils.userLoginResponse(httpParams,
						configurationObj.getCobrandSessionObj());

				if (userLoginResponse.statusCode() == HttpStatus.OK) {
					jsonObject = new JSONObject(userLoginResponse.asString());
				}
				if (userLoginResponse.statusCode() == HttpStatus.BAD_REQUEST) {
					jsonObject = new JSONObject(newUserResponse.asString());
				}
				JSONObject user = jsonObject.getJSONObject("user");
				JSONObject sess = user.getJSONObject("session");
				userSession = sess.getString("userSession");
				userUtils.unRegisterUser(session);

				JsonAssertionUtil jsonPath = new JsonAssertionUtil();
				jsonPath.assertResponse(userLoginResponse, statusCode, resFile, filePath);

				/*
				 * * * JsonPathAssertionUtil jsonPath = new JsonPathAssertionUtil();
				 * jsonPath.assertResponse(userLoginResponse, statusCode, resFile + ".json",
				 * filePath);
				 */

			} else {
				jsonAssertionUil.assertResponse(newUserResponse, statusCode, resFile, filePath);

				/*
				 * * * JsonPathAssertionUtil jsonPath = new JsonPathAssertionUtil();
				 * jsonPath.assertResponse(newUserResponse, statusCode, resFile + ".json",
				 * filePath);
				 */

			}
		} else if (newUserResponse.statusCode() == HttpStatus.BAD_REQUEST) {

			jsonAssertionUil.assertResponse(newUserResponse, statusCode, resFile, filePath);

			/*
			 * * * JsonPathAssertionUtil jsonPath = new JsonPathAssertionUtil();
			 * jsonPath.assertResponse(newUserResponse, statusCode, resFile + ".json",
			 * filePath);
			 */

		}

		/*
		 * * } else { String message = "Invalid locale"; Reporter.log(message, true);
		 * throw new SkipException(message); }
		 */

	}

	@Test(enabled = true, dataProvider = Constants.FEEDER, alwaysRun = true, priority = 1)
	@Source("\\TestData\\CSVFiles\\User\\testUserLoginForSubCategory.csv")
	public void testUserLoginUsingBodyParamForSubCategory(String testCaseId, String tcName, String loginNameKey,
			String loginName, String passwordKey, String password, String localeKey, String localeForResgister,
			String localeForLogin, String email, String cobSession, int statusCode, String contentType, String filePath,
			String resFile, String defectId, String specific, String enabled)
			throws JSONException, InterruptedException, JoseException {
		commonUtils.isTCEnabled(enabled, testCaseId);

		System.out.println("TestCase id is " + testCaseId + " TestCase Name is " + tcName);
		String userSession = null;
		JSONObject jsonObject = null;
		String errorCode;
		boolean isUserAlreadyExists = false;
		Response newUserResponse = null;
		String dynamicLoginName = loginName + System.currentTimeMillis();
		String userRegisterObjJSON = userHelper.createJSONforUserRegister(loginNameKey, dynamicLoginName, passwordKey,
				password, email, localeKey, localeForResgister);

		LinkedHashMap<String, String> queryParam = new LinkedHashMap<>();
		queryParam.put("registerParam", userRegisterObjJSON);

		HttpMethodParameters httpParams = HttpMethodParameters.builder().build();
		httpParams.setQueryParams(queryParam);
		EnvSession newUserSession = EnvSession.builder()
				.cobSession(configurationObj.getCobrandSessionObj().getCobSession())
				.path(configurationObj.getCobrandSessionObj().getPath() + URLConstants.USER_REGISTER).build();
		;

		newUserResponse = (Response) userUtils.userRegistrationResponse(newUserSession, httpParams);

		if (newUserResponse.statusCode() == HttpStatus.BAD_REQUEST) {
			try {

				JSONObject alreadyExists = new JSONObject(newUserResponse.asString());
				errorCode = alreadyExists.getString("errorCode");
				if (errorCode.equalsIgnoreCase("Y400")) {
					isUserAlreadyExists = true;
				}

			} catch (Exception e) {
				System.out.println("Username is Unique");
			}
		}
		if (newUserResponse.statusCode() == HttpStatus.OK || isUserAlreadyExists) {
			String userParam = userHelper.createJSONforUserLogin(loginNameKey, dynamicLoginName, passwordKey, password,
					localeKey, localeForLogin);

			HttpMethodParameters httpMethodParameters = HttpMethodParameters.builder().build();
			httpMethodParameters.setBodyParams(userParam);
			String sessionToken = SessionHelper.getSessionToken(cobSession, "cob");
			EnvSession envSession = EnvSession.builder().cobSession(sessionToken)
					.path(configurationObj.getCobrandSessionObj().getPath()).build();
			Response userLoginResponse = (Response) userUtils.userLoginResponse(httpMethodParameters, envSession);

			Response response = transactionUtils.getTxnCategories(configurationObj.getCobrandSessionObj());

			if (userLoginResponse.statusCode() == HttpStatus.OK) {
				jsonObject = new JSONObject(userLoginResponse.asString());
				JSONObject user = jsonObject.getJSONObject("user");
				JSONObject session = user.getJSONObject("session");
				userSession = session.getString("userSession");
				EnvSession envSessionResponse = EnvSession.builder()
						.cobSession(configurationObj.getCobrandSessionObj().getCobSession()).userSession(userSession)
						.path(configurationObj.getCobrandSessionObj().getPath()).build();
				userUtils.unRegisterUser(envSessionResponse);
			}
			if ((userLoginResponse.statusCode() == HttpStatus.BAD_REQUEST) && !isUserAlreadyExists) {
				jsonObject = new JSONObject(newUserResponse.asString());
				JSONObject user = jsonObject.getJSONObject("user");
				JSONObject session = user.getJSONObject("session");
				userSession = session.getString("userSession");
				EnvSession env = EnvSession.builder()
						.cobSession(configurationObj.getCobrandSessionObj().getCobSession()).userSession(userSession)
						.path(configurationObj.getCobrandSessionObj().getPath()).build();
				userUtils.unRegisterUser(env);
			}

			jsonAssertionUil.assertResponse(response, statusCode, resFile, filePath);

		} else if (newUserResponse.statusCode() == HttpStatus.BAD_REQUEST) {
			jsonAssertionUil.assertResponse(newUserResponse, statusCode, resFile, filePath);
		}

	}

	@Test(enabled = true, dataProvider = "feeder", alwaysRun = false, priority = 1)
	@Source("\\TestData\\CSVFiles\\User\\UpdateUserDetails.csv")
	public void testUpdateUserDetailsForLocale(String tcId, String tcName, String loginNameKey, String loginName,
			String passwordKey, String password, String localeKey, String locale, String updatedLocale, String email,
			String cobSession, String userSession, int statusCode, String contentType, String filePath, String resFile,
			String defectId, String enabled) throws JoseException {

		commonUtils.isTCEnabled(enabled, tcId);

		System.out.println("TestCase id is " + tcId + " TestCase Name is " + tcName);

		// if (configurationObj.getDefaultSupportedLocaleList().contains(locale)) {

		String updateLoginName = loginName + System.currentTimeMillis();
		String cobSessionToken = SessionHelper.getSessionToken(cobSession, "cob");

		String userRegisterObjJSON = userHelper.createJSONforUserRegister(loginNameKey, updateLoginName, passwordKey,
				password, email);

		HttpMethodParameters httpParams = HttpMethodParameters.builder().build();
		httpParams.setBodyParams(userRegisterObjJSON);
		EnvSession session = EnvSession.builder()
				.cobSession(cobSessionToken)
				.path(configurationObj.getCobrandSessionObj().getPath()).build();
		String userSessionToken = userUtils.userRegistration(session, httpParams);
		session.setUserSession(userSessionToken);

		// if (InitialLoader.defaultLocaleList.contains(locale) ||
		// InitialLoader.supportedLocaleList.contains(locale)
		// || locale.equalsIgnoreCase("") ||
		// tcName.equalsIgnoreCase("NullLocaleForUpdateUserDetails")) {
		// localePresent = true;
		// responseFile = resFile;
		// } else {
		// localePresent = false;
		// responseFile = resFileForInvalidLocale;
		// }

		String jsonResponse = userHelper.createJSONforUserRegister("", "", "", "", email, localeKey, updatedLocale);
		HttpMethodParameters userUpdateParam = HttpMethodParameters.builder().build();
		userUpdateParam.setBodyParams(jsonResponse);

		Response updateUserDetailsResponse = userUtils.updateUserDetails(userUpdateParam, session);

		jsonAssertionUil.assertResponse(updateUserDetailsResponse, statusCode, resFile, filePath);

		/*
		 * } else { String message = "Invalid locale"; Reporter.log(message, true);
		 * throw new SkipException(message); }
		 */

	}

	@Test(enabled = true, dataProvider = "feeder", alwaysRun = false, priority = 1)
	@Source("\\TestData\\CSVFiles\\User\\testUserLogout.csv")
	public void testUserLogout(String tcId, String tcName, String loginNameKey, String loginName, String passwordKey,
			String password, String localeKey, String locale, String email, String cobSession, String userSession,
			int statusCode, String contentType, String filePath, String resFile, String defectId, String enabled)
			throws JSONException, JoseException {

		commonUtils.isTCEnabled(enabled, tcId);

		System.out.println("TestCase id is " + tcId + " TestCase Name is " + tcName);

		// if (configurationObj.getDefaultSupportedLocaleList().contains(locale)) {

		JSONObject jsonObject = null;

		String userRegisterObjJSON = userHelper.createJSONforUserRegister(loginNameKey, loginName, passwordKey,
				password, email);
		String cobSessionToken = SessionHelper.getSessionToken(cobSession, "cob");

		HttpMethodParameters httpMethodParameters = HttpMethodParameters.builder().build();
		httpMethodParameters.setBodyParams(userRegisterObjJSON);
		EnvSession session = EnvSession.builder()
				.cobSession(cobSessionToken)
				.path(configurationObj.getCobrandSessionObj().getPath()).build();
		Response newUserResponse = (Response) userUtils.userRegistrationResponse(session, httpMethodParameters);

		String errorCode;
		boolean isUserAlreadyExists = false;
		if (newUserResponse.statusCode() == HttpStatus.BAD_REQUEST) {
			try {
				JSONObject alreadyExists = new JSONObject(newUserResponse.asString());
				errorCode = alreadyExists.getString("errorCode");
				if (errorCode.equalsIgnoreCase("Y400")) {
					isUserAlreadyExists = true;
				}
			} catch (Exception e) {
				System.out.println("Username is Unique");
			}
		}
		if (newUserResponse.statusCode() == HttpStatus.OK || isUserAlreadyExists) {
			String userParam = userHelper.createJSONforUserLogin(loginNameKey, loginName, passwordKey, password,
					localeKey, locale);

			HttpMethodParameters httpParams = HttpMethodParameters.builder().build();
			httpParams.setBodyParams(userParam);
			EnvSession session1 = EnvSession.builder()
					.cobSession(cobSessionToken)
					.path(configurationObj.getCobrandSessionObj().getPath()).build();;

			Response userLoginResponse = (Response) userUtils.userLoginResponse(httpMethodParameters, session1);
			Response userLogoutResponse = null;
			if (userLoginResponse.statusCode() == HttpStatus.OK) {
				if (userSession.equalsIgnoreCase("VALID")) {
					jsonObject = new JSONObject(userLoginResponse.asString());
					JSONObject user = jsonObject.getJSONObject("user");
					JSONObject session2 = user.getJSONObject("session");
					userSession = session2.getString("userSession");
				} else
					userSession = SessionHelper.getSessionToken(userSession, "user");

				EnvSession session3 = EnvSession.builder()
						.cobSession(cobSessionToken).userSession(userSession)
						.path(configurationObj.getCobrandSessionObj().getPath()).build();
				userLogoutResponse = userUtils.userLogout(session3);

			}
			if ((userLoginResponse.statusCode() == HttpStatus.BAD_REQUEST) && !isUserAlreadyExists) {
				if (userSession.equalsIgnoreCase("VALID")) {
					jsonObject = new JSONObject(newUserResponse.asString());
					JSONObject user = jsonObject.getJSONObject("user");
					JSONObject ses = user.getJSONObject("session");
					userSession = ses.getString("userSession");
				} else
					userSession = SessionHelper.getSessionToken(userSession, "user");
				// userLogoutResponse = ApiUtils.userLogout(InitialLoader.cobSessionObj,
				// SessionUtil.getSessionToken(cobSessionFromcsv, "cob"), userSession);
				EnvSession session3 = EnvSession.builder()
						.cobSession(cobSessionToken).userSession(userSession)
						.path(configurationObj.getCobrandSessionObj().getPath()).build();
				userLogoutResponse = userUtils.userLogout(session3);
			}

			if (userLogoutResponse.statusCode() == HttpStatus.NO_CONTENT_204) {
				Assert.assertEquals(userLogoutResponse.statusCode(), HttpStatus.NO_CONTENT_204);
			} else {

				jsonAssertionUil.assertResponse(userLogoutResponse, statusCode, resFile, filePath);

				/*
				 * * * JsonPathAssertionUtil jsonPath = new JsonPathAssertionUtil();
				 * jsonPath.assertResponse(userLogoutResponse, statusCode, resFile + ".json",
				 * filePath);
				 */

			}

		} else if (newUserResponse.statusCode() == HttpStatus.BAD_REQUEST) {
			jsonAssertionUil.assertResponse(newUserResponse, statusCode, resFile, filePath);

			/**
			 * JsonPathAssertionUtil jsonPath = new JsonPathAssertionUtil();
			 * jsonPath.assertResponse(newUserResponse, statusCode, resFile + ".json",
			 * filePath);
			 */

		}

		/*
		 * * } else { String message = "Invalid locale"; Reporter.log(message, true);
		 * throw new SkipException(message); }
		 */

	}

}
