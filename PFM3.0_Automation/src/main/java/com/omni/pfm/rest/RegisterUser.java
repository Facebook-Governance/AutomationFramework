/**
* Copyright (c) 2018 Yodlee Inc. All Rights Reserved.  
*
* This software is the confidential and proprietary information of
* Yodlee, Inc. Use is subject to license terms.
*
* @author msuhaib
*/

package com.omni.pfm.rest;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;
import java.util.Properties;

import org.json.JSONException;
import org.json.JSONObject;

import com.omni.pfm.listeners.EReporter;
import com.omni.pfm.testBase.TestBase;
import com.omni.pfm.utility.PropsUtil;
import com.relevantcodes.extentreports.LogStatus;

import io.restassured.RestAssured;
import io.restassured.config.SSLConfig;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

/**
 * @author msuhaib
 *
 */
public class RegisterUser {
	public static String restVersion;
	public static String coBrandSessionToken;
	public static Response newUserResponse;
	private static String usersSessionToken;
	private static String memId;
	private static String userName = null;
	private static String finalUserName = null;
	private static String locale;
	public static Properties properties = new Properties();

	public static void init() {
		RestAssured.urlEncodingEnabled = "true".equals(properties.getProperty("UrlEncoding").toLowerCase()) ? true
				: false;
		RestAssured.baseURI = properties.getProperty("baseURI");
		SSLConfig sslConfig1 = new SSLConfig();
		RestAssured.config = RestAssured.config().sslConfig(sslConfig1.allowAllHostnames());
	}

	public static String initCobrand(String configFile) {
		LoadParameters(configFile);
		init();
		restVersion = properties.getProperty("restVersion");
		coBrandSessionToken = getCobrandSessionToken();
		newUserResponse = getNewUserResponse();
		try {
			usersSessionToken = newUserResponse.jsonPath()
					.getString("userContext.conversationCredentials.sessionToken");
			if (usersSessionToken != null) {
				memId = newUserResponse.jsonPath().getString("userId");
				userName = newUserResponse.jsonPath().getString("loginName");
				locale = newUserResponse.jsonPath().getString("userContext.locale");
				finalUserName = userName;
				System.out.println("MEM ID =" + memId);
			} else {
				EReporter.log(LogStatus.INFO, "User Registration is not successful as the usersSessionToken is null");
			}
		} catch (Exception er) {
			EReporter.log(LogStatus.INFO, "User Registration is not successful.");
			er.printStackTrace();
		}
		return finalUserName;
	}

	public static Properties getProperty() {
		return properties;
	}

	/**
	 * @param configFile
	 */
	public static void LoadParameters(String configFile) {
		InputStream fis = RegisterUser.class.getClassLoader()
				.getResourceAsStream("RestProperties" + File.separator + configFile);
		try {
			properties.load(fis);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static String getCobrandSessionToken() {
		HashMap<String, String> params = new HashMap<>();
		params.put("cobrandLogin", properties.getProperty("coBrandLogin"));
		params.put("cobrandPassword", properties.getProperty("coBrandPassword"));
		Response response = RestUtil.post(params, "/v1.0/authenticate/coblogin");
		response.then().log().all();
		return response.jsonPath().getString("cobrandConversationCredentials.sessionToken");
	}

	/**
	 * Generic method to get the user response while creating a new user * changes -
	 * added key to verify open banking enabled and create user with currency GBP if
	 * enabled *
	 * 
	 * @author sbhat1 *
	 * @return response
	 * @throws JSONException 
	 */

	public static Response getNewUserResponse() {
		HashMap<String, String> params = new HashMap<>();
		params.put("cobSessionToken", coBrandSessionToken);
		if (TestBase.openBankingEnabled.equals("true")) {
			params.put("userPreferences[0]", "PREFERRED_CURRENCY~GBP");
			params.put("userProfile.country", "UK");
			params.put("userProfile.city", "London");
		} else {
			params.put("userPreferences[0]", "PREFERRED_CURRENCY~USD");
			params.put("userProfile.country", "USA");
			params.put("userProfile.city", "San Jose");
		}
		params.put("userCredentials.loginName", getRandomUserName());
		params.put("userCredentials.password", "Password#");
		params.put("userCredentials.objectInstanceType", "com.yodlee.ext.login.PasswordCredentials");
		params.put("userProfile.emailAddress", "kongara.sravan@yodlee.com");
		params.put("userPreferences[1]", "PREFERRED_DATE_FORMAT~MM/dd/yyyy");
		params.put("userProfile.firstName", "Kongara");
		params.put("userProfile.lastName", "Sravan");
		params.put("userProfile.middleInitial", "T");
		params.put("userProfile.objectInstanceType", "com.yodlee.core.usermanagement.UserProfile");
		params.put("userProfile.address1", "3600 Bridge Parkway");
		params.put("userProfile.address2", "Suite 200");
		Response response = RestUtil.post(params, "/v1.0/jsonsdk/UserRegistration/register3");
		response.then().log().all();
		return response;
	}

	public static String getRandomUserName() {
		System.out.println("Getting random user Name...");

		// String username = "AUTONPR" + System.currentTimeMillis();
		String username = PropsUtil.getEnvPropertyValue("cnf_Environment").toUpperCase() + System.currentTimeMillis();
		System.out.println("Got random User : " + username);
		EReporter.log(LogStatus.INFO, "User Name: " + username);
		return username;
	}

	public static void main(String[] args) throws InterruptedException, SQLException {
		getUser();
	}

	public static void getUser() throws InterruptedException, SQLException {

		String user = initCobrand("Config-AutoPlatform.Properties");
		System.out.print("\n The User is [" + user + "]");
	}

	/*
	 * void login(){ initCobrand("AutoNPR.properties");
	 * 
	 * }
	 */

	// Added by Abhi - SUSTQA
	public static boolean addAccountAPI(String dagName, String dagPwd) throws Exception {
		String siteID = addAccount(coBrandSessionToken, usersSessionToken, dagName, dagPwd);
		return pollRefreshStatus(coBrandSessionToken, usersSessionToken, siteID);
	}

	/*
	 * Added by Abhi - SUSTQA Account addition through API
	 */
	public static String addAccount(String coBrandSessionToken, String usersSessionToken, String name, String Pwd) {
		String siteAccountId;
		HashMap<String, String> params = new HashMap<>();
		params.put("cobSessionToken", coBrandSessionToken);
		params.put("userSessionToken", usersSessionToken);
		params.put("siteId", "16441");
		params.put("credentialFields.enclosedType", "com.yodlee.common.FieldInfoSingle");
		params.put("credentialFields[0].displayName", "Catalog");
		params.put("credentialFields[0].fieldType.typeName", "IF_LOGIN");
		params.put("credentialFields[0].name", "LOGIN");
		params.put("credentialFields[0].value", name);
		params.put("credentialFields[0].valueIdentifier", "LOGIN");
		params.put("credentialFields[0].valueMask", "LOGIN_FIELD");
		params.put("credentialFields[0].isEditable", "true");
		params.put("credentialFields[1].displayName", "Password");
		params.put("credentialFields[1].fieldType.typeName", "IF_PASSWORD");
		params.put("credentialFields[1].name", "PASSWORD"); // San Mateo
		params.put("credentialFields[1].value", Pwd);
		params.put("credentialFields[1].valueIdentifier", "PASSWORD");
		params.put("credentialFields[1].valueMask", "LOGIN_FIELD"); // San Mateo
		params.put("credentialFields[1].isEditable", "true");

		Response response = RestUtil.post(params, "/v1.0/jsonsdk/SiteAccountManagement/addSiteAccount1");
		response.then().log().all();
		siteAccountId = response.jsonPath().getString("siteAccountId");
		return siteAccountId;
	}

	/*
	 * Added by Abhi- SUSTQA Method to check status of account addtion
	 */
	public static boolean pollRefreshStatus(String cobSession, String userSession, String siteAccountId)
			throws Exception {
		System.out.println("\nChecking the refresh status...");
		long startTime = System.currentTimeMillis();
		long elapsedTime = 0L;
		boolean flag = false;
		String refreshStatus = "REFRESH_TRIGGERED";
		String addStatus = "";
		while (refreshStatus.equals("REFRESH_TRIGGERED") || refreshStatus.equals("LOGIN_SUCCESS")
				|| refreshStatus.equals("PARTIAL_COMPLETE")) {
			elapsedTime = (new Date()).getTime() - startTime;
			Response siteRefreshInfo = getSiteRefreshInfo(cobSession, userSession, siteAccountId);
			refreshStatus = siteRefreshInfo.jsonPath().getString("siteRefreshStatus.siteRefreshStatus");
			addStatus = siteRefreshInfo.jsonPath().getString("siteAddStatus.siteAddStatus");
			System.out.println("#################### " + refreshStatus + " ##################");
			System.out.println("*************************" + elapsedTime);

			if (refreshStatus.equalsIgnoreCase("[FAILED]") || elapsedTime > 5 * 60 * 1000) {
				break;
			}
		}
		if (refreshStatus.equals("REFRESH_COMPLETED")) {
			if (addStatus.equals("ADDED")) {
				System.out.println("\nrefreshStatus = " + refreshStatus);
				System.out.println("\tThe refresh has completed successfully.");
				flag = true;
			}

		} else {
			flag = false;
		}

		return flag;
	}

	private static Response getSiteRefreshInfo(String cobSession, String userSession, String siteaccountId) {

		HashMap<String, String> siteparams = new HashMap<String, String>();
		siteparams.put("cobSessionToken", cobSession);
		siteparams.put("userSessionToken", userSession);
		siteparams.put("memSiteAccId", siteaccountId);

		Response siteRefreshInfo = RestUtil.post(siteparams, "/v1.0/jsonsdk/Refresh/getSiteRefreshInfo");
		siteRefreshInfo.then().log().all();

		return siteRefreshInfo;
	}

	public static String registerUserUsingYSL(String yslURL, String cobrandName, String cobrandUsername,
			String cobrandPassword) {
		try {
			RestAssured.baseURI = yslURL;
			System.out.println("YSL URL :: " + yslURL);
			HashMap<String, String> headers = new HashMap<String, String>();
			headers.put("Cobrand-Name", cobrandName);
			headers.put("Content-Type", "application/json");
			headers.put("Api-version", "1.1");
			RequestSpecification rs = RestAssured.given();
			rs.headers(headers);
			String cobrandLoginBody = "{\"cobrand\":{\"cobrandLogin\": \"{cobrandUsername}\",\"cobrandPassword\": \"{cobrandPassword}\",\"locale\": \"en_US\"}}";
			cobrandLoginBody = cobrandLoginBody.replace("{cobrandUsername}", cobrandUsername);
			cobrandLoginBody = cobrandLoginBody.replace("{cobrandPassword}", cobrandPassword);
			rs.body(cobrandLoginBody);
			Response response = rs.post("/cobrand/login");
			response.then().log().all();
			String cobrandToken = response.jsonPath().getString("session.cobSession");
			RequestSpecification rs1 = RestAssured.given();
			String newUserName = "User" + new Date().getTime();
			HashMap<String, String> userRegHeaders = new HashMap<String, String>();
			userRegHeaders.put("Cobrand-Name", cobrandName);
			userRegHeaders.put("Content-Type", "application/json");
			userRegHeaders.put("Api-version", "1.1");
			userRegHeaders.put("Authorization", "cobSession=" + cobrandToken);
			System.out.println(cobrandToken);
			rs1.headers(userRegHeaders);
			JSONObject main = new JSONObject();
			JSONObject userObj = new JSONObject();
			JSONObject nameobj = new JSONObject();
			JSONObject addressobj = new JSONObject();
			JSONObject preferencesobj = new JSONObject();
			nameobj.put("first", "Kongara");
			nameobj.put("last", "sravan");
			addressobj.put("address", "200 Lincoln Ave");
			addressobj.put("state", "CA");
			addressobj.put("city", "Salinas");
			addressobj.put("zip", "93901");
			addressobj.put("country", "US");
			preferencesobj.put("currency", "USD");
			preferencesobj.put("timeZone", "PST");
			preferencesobj.put("dateFormat", "MM/dd/yyyy");
			preferencesobj.put("locale", "en_US");
			userObj.put("loginName", newUserName);
			userObj.put("password", PropsUtil.getEnvPropertyValue("userPassword"));
			userObj.put("email", "abc@yodlee.com");
			userObj.put("name", nameobj);
			userObj.put("address", addressobj);
			userObj.put("preferences", preferencesobj);
			main.put("user", userObj);
			System.out.println(main.toString());
			rs1.body(main.toString());
			Response userRegResponse = rs1.post("user/register");
			System.out.println(userRegResponse.asString());
			userRegResponse.then().log().all();
			String username = userRegResponse.jsonPath().getString("user.loginName");
			return username;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
