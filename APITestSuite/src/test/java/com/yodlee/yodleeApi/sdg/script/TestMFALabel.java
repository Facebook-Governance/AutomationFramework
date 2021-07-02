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
 *******************************************************************************//*
@author : Shobha Gangaiah
 * Description : Mandatory constraint of label input to be removed for Q & A sites
 * 
 
package com.yodlee.yodleeApi.sdg.script;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.testng.Assert.assertEquals;

import java.util.HashMap;
import java.util.Map;

import org.databene.benerator.anno.Source;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.testng.annotations.Test;

import com.yodlee.commonUtils.RestUtil;
import com.yodlee.yodleeApi.common.Base;
import com.yodlee.yodleeApi.common.Configuration;
import com.yodlee.yodleeApi.common.SdgLoginForm;
import com.yodlee.yodleeApi.constants.Constants;
import com.yodlee.yodleeApi.helper.ProviderAccountsHelper;
import com.yodlee.yodleeApi.helper.SessionHelper;
import com.yodlee.yodleeApi.pojo.EnvSession;
import com.yodlee.yodleeApi.pojo.HttpMethodParameters;
import com.yodlee.yodleeApi.pojo.User;
import com.yodlee.yodleeApi.utils.apiUtils.ProviderAccountUtils;
import com.yodlee.yodleeApi.utils.commonUtils.CommonUtils;

import io.restassured.response.Response;

*//**
 * @author Rahul Kumar
 *
 *//*
public class TestMFALabel extends Base {

	String loginForm = "";
	JSONObject loginFormMFA;
	public String providerAccountId = null;
	public String tokenValue = "123456";
	public String firstAns = "Texas";
	public String secondAns = "w3schools";
	JSONObject parentproviderAccount = null;
	JSONArray providerAccountArray = null;
	JSONObject providerAccountObject = null;
	JSONArray loginForm2 = null;
	JSONObject loginFormObject = null;
	public String newUserSession = "";
	SessionHelper sessionHelper = new SessionHelper();
	SdgLoginForm sdgLoginForm = SdgLoginForm.getInstance();
	CommonUtils commonUtils = new CommonUtils();
	ProviderAccountUtils providerAccountUtils = new ProviderAccountUtils();
	ProviderAccountsHelper providerAccountsHelper = new ProviderAccountsHelper();
	Configuration configurationObj = Configuration.getInstance();
	EnvSession envSessionObj =null;

	*//**
	 * @param providerId
	 * @param dagUserName
	 * @param dagPwd
	 * @param loginFormType
	 * @throws InterruptedException
	 *//*
	public void addMfaAccount(String providerId, String dagUserName, String dagPwd, String loginFormType)
			throws InterruptedException {

		// Creating User object
		User userInfo = User.builder().build();
		userInfo.setUsername("TestYSLSdg" + "MFALabel" + System.currentTimeMillis());
		userInfo.setPassword("TEST@12345");

		// User Registration
		envSessionObj = sessionHelper.getSessionObjectByUserRegistration(userInfo);
		System.out.println("New userSession created is::" + envSessionObj.getUserSession());

		// Getting LoginForm
		Map<String, String> credentialMap = new HashMap<>(2);
		credentialMap.put("dagUsername", dagUserName);
		credentialMap.put("dagPassword", dagPwd);
		String loginForm = sdgLoginForm.getUpdatedLoginFormWithCredentials(Long.valueOf(providerId), loginFormType,
				Integer.valueOf(Constants.ROW), credentialMap);
		System.out.println("LoginForm is::" + loginForm);

		// Adding MFA-Account
		EnvSession envSession = new EnvSession(configurationObj.getCobrandSessionObj().getCobSession(),
				envSessionObj.getUserSession(), configurationObj.getCobrandSessionObj().getPath());
		HttpMethodParameters httpMethodParameters = providerAccountsHelper
				.createInputParamsForAddProviderAccount(Long.valueOf(providerId), loginForm);

		long providerAccountId = providerAccountUtils.addProviderAccountMFA(httpMethodParameters, envSession);
		Thread.sleep(Constants.SECONDS);
		System.out.println("Site account id is : " + providerAccountId);

	}

	*//**
	 * Adding MFA Account, without label field in Q&A loginform and field Array
	 * 
	 * @param usTcId
	 * @param testCaseName
	 * @param dagSite
	 * @param array
	 * @param providerId
	 * @param dagUN
	 * @param dagPwd
	 * @param enabled
	 * @throws Exception
	 *//*
	@Source("\\TestData\\CSVFiles\\Sdg\\MFA\\testLabel.csv")
	@Test(dataProvider = "feeder", alwaysRun = true)
	public void testAddAccount(String usTcId, String testCaseName, String dagSite, String loginFormType,
			String providerId, String dagUserName, String dagPwd, String enabled) throws Exception {

		commonUtils.isTCEnabled(enabled, usTcId);
		System.out.println("TestCase Name " + testCaseName);

		// Adding MFA-Account
		addMfaAccount(providerId, dagUserName, dagPwd, loginFormType);
		Thread.sleep(Constants.SECONDS);

		// Calling Get Refresh API
		System.out.println("Calling GET Refresh ");
		HttpMethodParameters httpMethodParameters= HttpMethodParameters.builder().build();
		EnvSession envSession = new EnvSession(configurationObj.getCobrandSessionObj().getCobSession(),
				envSessionObj.getUserSession(), configurationObj.getCobrandSessionObj().getPath());
		HttpMethodParameters httpMethodParameters = providerAccountsHelper
				.createInputParamsForAddProviderAccount(Long.valueOf(providerId), loginForm);

		ProviderAccountUtils providerAccountUtils = new ProviderAccountUtils();
		providerAccountUtils.getAllProviderAccounts(envSessionObj.getUserSession());
		
		Response refreshResponse = ProviderAccountUtils.getProviderDetailsWithoutFilters(
				String.valueOf(providerAccountId), newUserSession, InitialLoader.sdgSubBrandSessionObj);

		if (dagSite.equals("SecurityQA")) {
			ProviderAccountUtils.MFAChallengeQandAWithoutLabel(String.valueOf(providerAccountId), firstAns, secondAns,
					newUserSession, InitialLoader.sdgSubBrandSessionObj);

		} else if (dagSite.equals("MultiLevel")) {
			ProviderAccountUtils.MFAChallengeTokens(String.valueOf(providerAccountId), Constants.ID, Constants.TOKEN,
					Constants.VALUE, tokenValue, newUserSession, InitialLoader.sdgSubBrandSessionObj);
			waitForSeconds(10);
			ProviderAccountUtils.MFAChallengeQandAWithoutLabel(String.valueOf(providerAccountId), firstAns, secondAns,
					newUserSession, InitialLoader.sdgSubBrandSessionObj);
		} else if (dagSite.equals("SecurityQAField")) {
			ProviderAccountUtils.MFAChallengeQandAWithoutLabelFieldArray(String.valueOf(providerAccountId), firstAns,
					secondAns, newUserSession, InitialLoader.sdgSubBrandSessionObj);
		}
		// Checking for Successful Account addition
		waitForSeconds(60);
		Response refreshResponse1 = ProviderAccountUtils.getProviderDetailsWithoutFilters(
				String.valueOf(providerAccountId), newUserSession, InitialLoader.sdgSubBrandSessionObj);
		refreshResponse1.then().log().all();
		String accountStatus = refreshResponse1.jsonPath().getString("providerAccount[0].status");
		String additionalAccountStatus = refreshResponse1.jsonPath()
				.getString("providerAccount[0].dataset[0].additionalStatus");

		if ((accountStatus.equals("SUCCESS") && (additionalAccountStatus.equals("AVAILABLE_DATA_RETRIEVED")))
				|| (accountStatus.equals("PARTIAL_SUCCESS")
						&& (additionalAccountStatus.equals("AVAILABLE_DATA_RETRIEVED")))) {
			assertTrue("Account Addition Successful", true);

		} else if (accountStatus.equals("IN_PROGRESS") && (additionalAccountStatus.equals("ACCT_SUMMARY_RECEIVED"))) {
			assertTrue("Account Addition Successful", true);
		} else {
			assertFalse("Account Addition Failed", true);
		}

	}

	
	 * Adding MFA Account with wrong credentials and updating with right credentials
	 * without label field in Q&A loginform and field Array
	 
	@Source("\\TestData\\CSVFiles\\Sdg\\MFA\\testUpdateLabel.csv")
	@Test(dataProvider = "feeder", alwaysRun = true)
	public void testUpdate(String usTcId, String testCaseName, String dagSite, String array, String providerId,
			String dagUN, String dagPwd, String dagUpdatedUN, String dagUpdatedPwd, String enabled) throws Exception {
		CommonUtils.isTCEnabled(enabled, usTcId);
		System.out.println("TestCase Name " + testCaseName);
		// Add MFA Account
		AddMfa(providerId, dagUN, dagPwd, array, InitialLoader.sdgSubBrandSessionObj);
		Thread.sleep(1000);
		Response refreshResponse = ProviderAccountUtils.getProviderDetailsWithoutFilters(
				String.valueOf(providerAccountId), newUserSession, InitialLoader.sdgSubBrandSessionObj);
		String status = refreshResponse.jsonPath().getString("providerAccount[0].status");
		String additionalStatus = refreshResponse.jsonPath()
				.getString("providerAccount[0].dataset[0].additionalStatus");
		refreshResponse.then().log().all();
		assertEquals("FAILED", status, "status has not FAILED after account addition using wrong crednetials");
		System.out.println("Account addition failed with = " + additionalStatus);
		// Update MFA Account
		String updatedloginFormMFA = ProvidersUtilsV2.getLoginFormWithDataSet(Long.parseLong(providerId), dagUpdatedUN,
				dagUpdatedPwd, array, null);
		System.out.println("updatedloginFormMFA : " + updatedloginFormMFA);
		Response updateResponse = ProviderAccountUtils.editCredentialsForMfaAccounts(updatedloginFormMFA,
				providerAccountId, newUserSession, InitialLoader.sdgSubBrandSessionObj);
		updateResponse.then().log().all();
		waitForSeconds(90);
		Response refreshUpdateResponse = ProviderAccountUtils.getProviderDetailsWithoutFilters(
				String.valueOf(providerAccountId), newUserSession, InitialLoader.sdgSubBrandSessionObj);
		refreshUpdateResponse.then().log().all();

		if (dagSite.equals("SecurityQA")) {
			ProviderAccountUtils.MFAChallengeQandAWithoutLabel(String.valueOf(providerAccountId), firstAns, secondAns,
					newUserSession, InitialLoader.sdgSubBrandSessionObj);

		} else if (dagSite.equals("MultiLevel")) {
			ProviderAccountUtils.MFAChallengeTokens(String.valueOf(providerAccountId), Constants.ID, Constants.TOKEN,
					Constants.VALUE, tokenValue, newUserSession, InitialLoader.sdgSubBrandSessionObj);
			waitForSeconds(10);
			ProviderAccountUtils.MFAChallengeQandAWithoutLabel(String.valueOf(providerAccountId), firstAns, secondAns,
					newUserSession, InitialLoader.sdgSubBrandSessionObj);
		} else if (dagSite.equals("SecurityQAField")) {
			ProviderAccountUtils.MFAChallengeQandAWithoutLabelFieldArray(String.valueOf(providerAccountId), firstAns,
					secondAns, newUserSession, InitialLoader.sdgSubBrandSessionObj);

		}
		waitForSeconds(60);
		refreshUpdateResponse = ProviderAccountUtils.getProviderDetailsWithoutFilters(String.valueOf(providerAccountId),
				newUserSession, InitialLoader.sdgSubBrandSessionObj);
		refreshUpdateResponse.then().log().all();
		String accountStatus = refreshUpdateResponse.jsonPath().getString("providerAccount[0].status");
		String additionalAccountStatus = refreshUpdateResponse.jsonPath()
				.getString("providerAccount[0].dataset[0].additionalStatus");
		if ((accountStatus.equals("SUCCESS") && (additionalAccountStatus.equals("AVAILABLE_DATA_RETRIEVED")))
				|| (accountStatus.equals("PARTIAL_SUCCESS")
						&& (additionalAccountStatus.equals("AVAILABLE_DATA_RETRIEVED")))) {
			assertTrue("Account Addition Successful by updating with correct credentials", true);
		} else if (accountStatus.equals("IN_PROGRESS") && (additionalAccountStatus.equals("ACCT_SUMMARY_RECEIVED"))) {
			assertTrue("Account Addition Successful by updating with correct credentials", true);
		} else {
			assertFalse("Account Addition Failed", true);
		}

	}

	@Source("\\TestData\\CSVFiles\\Sdg\\MFA\\testLabelNegative.csv")
	@Test(dataProvider = "feeder", alwaysRun = true)
	public void testLabelNegative(String usTcId, String testCaseName, String array, String providerId, String dagUN,
			String dagPwd, String label1, String labelValue1, String label2, String labelValue2, String enabled)
			throws Exception {
		CommonUtils.isTCEnabled(enabled, usTcId);
		System.out.println("TestCase Name " + testCaseName);
		// Add Mfa Account
		AddMfa(providerId, dagUN, dagPwd, array, InitialLoader.sdgSubBrandSessionObj);
		Thread.sleep(10000);
		// Calling Get Refresh API
		Response refreshResponse = ProviderAccountUtils.getProviderDetailsWithoutFilters(
				String.valueOf(providerAccountId), newUserSession, InitialLoader.sdgSubBrandSessionObj);
		refreshResponse.then().log().all();
		MFAChallengeQandAWithEditableLabelField(String.valueOf(providerAccountId), firstAns, secondAns, label1,
				labelValue1, label2, labelValue2, newUserSession, InitialLoader.sdgSubBrandSessionObj);
		waitForSeconds(180);
		// Checking for Successful Account addition
		Response refreshResponse1 = ProviderAccountUtils.getProviderDetailsWithoutFilters(
				String.valueOf(providerAccountId), newUserSession, InitialLoader.sdgSubBrandSessionObj);
		refreshResponse1.then().log().all();
		String accountStatus = refreshResponse1.jsonPath().getString("providerAccount[0].status");
		String additionalAccountStatus = refreshResponse1.jsonPath()
				.getString("providerAccount[0].dataset[0].additionalStatus");
		if (accountStatus.equals("SUCCESS") && (additionalAccountStatus.equals("AVAILABLE_DATA_RETRIEVED"))) {
			assertTrue("Account Addition Successful", true);
		} else if (accountStatus.equals("IN_PROGRESS") && (additionalAccountStatus.equals("ACCT_SUMMARY_RECEIVED"))) {
			assertTrue("Account Addition Successful", true);
		} else {
			assertFalse("Account Addition Failed", true);
		}

	}

	// Q&A field Array with Editable label field
	public static Response MFAChallengeQandAWithEditableLabelField(String providerAccountId, String firstAns,
			String secondAns, String label1, String labelValue1, String label2, String labelValue2, String userSession,
			CommonEnvSession obj) throws JSONException {
		String json = "{\"loginForm\": {\"mfaTimeout\": 90900,\"formType\": \"questionAndAnswer\",\"row\": [{\"id\": \"SQandA--QUESTION_1--Row--1\",\"fieldRowChoice\": \"0001\",\"form\": \"0001\",\""
				+ label1 + "\": \"" + labelValue1
				+ "\",\"field\": [{\"id\": \"SQandA--QUESTION_1--1\",\"name\": \"QUESTION_1\",\"isOptional\": false,\"value\": \""
				+ firstAns
				+ "\",\"valueEditable\": \"true\",\"type\": \"text\"}]}, {\"id\": \"SQandA--QUESTION_2--Row--2\",\"fieldRowChoice\": \"0002\",\"form\": \"0001\",\""
				+ label2 + "\": \"" + labelValue2
				+ "\",\"field\": [{\"id\": \"SQandA--QUESTION_2--2\",\"name\": \"QUESTION_2\",\"isOptional\": false,\"value\": \""
				+ secondAns + "\",\"valueEditable\": \"true\",\"type\": \"text\"}]}]}}";
		Map<String, String> param = CommonUtils.passOneQueryParam(ProviderAccountUtils.MULTIPLE_ACCOUNT_IDS_FILTER,
				String.valueOf(providerAccountId));
		Response response = RestUtil.putRequestWithBodyandQueryParam(json, param,
				obj.getPath() + Constants.PROVIDER_ACCOUNTS_WRITE_FRAMEWORK_URL, obj.getCobSession(), userSession);
		response.then().log().all();
		return response;
	}

}
*/