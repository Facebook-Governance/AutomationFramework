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

package com.yodlee.yodleeApi.Teaca;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import org.databene.benerator.anno.Source;
import org.json.JSONException;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.yodlee.yodleeApi.common.Base;
import com.yodlee.yodleeApi.common.Configuration;
import com.yodlee.yodleeApi.common.NonSdgLoginForm;
import com.yodlee.yodleeApi.constants.Constants;
import com.yodlee.yodleeApi.constants.HttpStatus;
import com.yodlee.yodleeApi.helper.ProviderAccountsHelper;
import com.yodlee.yodleeApi.pojo.HttpMethodParameters;
import com.yodlee.yodleeApi.utils.apiUtils.AccountUtils;
import com.yodlee.yodleeApi.utils.apiUtils.ProviderAccountUtils;
import com.yodlee.yodleeApi.utils.commonUtils.CommonUtils;

import io.restassured.response.Response;

/**
 * @author partha sham
 *
 */
public class TestTeacaAcountsYsl extends Base{

	String providerId = "16442";
	String loginFormType = "loginform";
	String dagUserName = "iavms11.site16442.2";
	String dagPassword = "site16442.2";
	boolean isincludeDataSet = true;
	String includeDataSet = "ACCT_PROFILE.FULL_ACCT_NUMBER,ACCT_PROFILE.BANK_TRANSFER_CODE,ACCT_PROFILE.HOLDER_NAME";
	int status = 200;
	private long providerAccountId;
	String providerObejct = "providerobject";

	public static final String MFA_IAV_CSV = "\\TestData\\CSVFiles\\Teaca\\account\\iav.csv";
	public static final String ACCOUNTBECLOSED_CSV = "\\TestData\\CSVFiles\\Teaca\\account\\accounttobeclosed.csv";
	public static final String AccountWithoutOptionalValue_CSV = "\\TestData\\CSVFiles\\Teaca\\account\\AccountWithoutOptionalValue.csv";
	public static final String MFAUsingSimplifiedLoginForm_CSV = "\\TestData\\CSVFiles\\Teaca\\account\\MFAUsingSimplifiedLoginForm.csv";
	public static final String AccountProviderObject_CSV = "\\TestData\\CSVFiles\\Teaca\\account\\AccountProviderObject.csv";
	public static final String AccoundAdd_16291_CSV = "\\TestData\\CSVFiles\\Teaca\\account\\AccoundAdd_16291.csv";
	public static final String AddAccount8999_CSV = "\\TestData\\CSVFiles\\Teaca\\account\\AddAccount8999.csv";

	CommonUtils commonUtils = new CommonUtils();
	ProviderAccountUtils providerAccountUtils = new ProviderAccountUtils();
	ProviderAccountsHelper providerAccountsHelper = new ProviderAccountsHelper();
	Configuration configurationObj = Configuration.getInstance();
	NonSdgLoginForm nonSdgLoginForm = NonSdgLoginForm.getInstance();
	AccountUtils accountUtils = new AccountUtils();

	/*
	 * 1 Added IVA MFA 2 Deleted IVA MFA account
	 *
	 */
	@Test(priority = 1, dataProvider = Constants.FEEDER)
	@Source(MFA_IAV_CSV)
	public void testAddMfaIAVAccount(String testcaseID, String testcaseName, String providerId, String dagUserName,
			String dagPwd, String tokenValue, String firstQues, String firstAns, String secondQues, String secondAns,
			String defectID, String enabled) throws Exception {

		commonUtils.isTCEnabled(enabled, testcaseID);;
		String updatedLoginForm = null;
		Response response = null;
		Map<String, String> credentialsMap = new HashMap<>();
		credentialsMap.put(Constants.DAG_USERNAME, dagUserName);
		credentialsMap.put(Constants.DAG_PASSWORD, dagPwd);

		try {
			updatedLoginForm = nonSdgLoginForm.getUpdatedLoginFormWithCredentials(Long.valueOf(providerId),
					Constants.LOGIN_FORM, Constants.TWO_COUNT, credentialsMap);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		System.out.println("Updated Login Form Object: " + updatedLoginForm);

		Map<String, Object> mapQuery = new HashMap<>();
		mapQuery.put("providerId", providerId);
		HttpMethodParameters httpParams = HttpMethodParameters.builder().build();
		httpParams.setBodyParams(updatedLoginForm);
		httpParams.setQueryParams(mapQuery);
		providerAccountId = providerAccountUtils.addProviderAccountMFA(httpParams,	configurationObj.getCobrandSessionObj());
		System.out.println("Site account id from scripts is : " + providerAccountId);
		
		commonUtils.waitForSeconds(10);
		System.out.println("Waiting is over");
		Map<String, Object> mapPath = new HashMap<>();
		mapPath.put("providerAccountId", providerAccountId);
		HttpMethodParameters httpMethodParameters = HttpMethodParameters.builder().build();
		httpMethodParameters.setPathParams(mapPath);
		Response refreshResponse = providerAccountUtils.getProviderAcctDetails(httpMethodParameters, configurationObj.getCobrandSessionObj());
		System.out.println("Get Provider Account status code ::"+refreshResponse.getStatusCode());
		
		try {
			System.out.println("creating mfa token");
			String strBodyJson = providerAccountsHelper.createMFATokenObject(Constants.ID, Constants.TOKEN,
					Constants.VALUE, tokenValue);
			HttpMethodParameters httpMethodParameters2 = HttpMethodParameters.builder().build();
			httpMethodParameters2.setBodyParams(strBodyJson);
			HashMap<String, Object> mapQueryParams = new HashMap<>();
			mapQueryParams.put("providerAccountIds", providerAccountId);
			httpMethodParameters2.setQueryParams(mapQueryParams);
			System.out.println("mfa token created");
			System.out.println("changing mfa token");
			response = providerAccountUtils.mfaChallengeToken(httpMethodParameters2, configurationObj.getCobrandSessionObj());
			System.out.println("RESPONSE after token: "+response.prettyPrint()+ "\nStatus CODE: "+response.statusCode());
			System.out.println("Waiting is over after updating token");
			commonUtils.waitForSeconds(15);
			
			String strBodyJson2 = providerAccountsHelper.createInputForMFASecurityQA(firstQues, firstAns, secondQues,secondAns);
			HttpMethodParameters httpMethodParameters3 = HttpMethodParameters.builder().build();
			httpMethodParameters3.setBodyParams(strBodyJson2);
			httpMethodParameters3.setQueryParams(mapQueryParams);
			System.out.println("create Input For MFA Security QA ::"+strBodyJson2);
			response = providerAccountUtils.mfaChallengeQandA(httpMethodParameters3, configurationObj.getCobrandSessionObj());			
			
		} catch (Exception e) {
			throw new Exception("Error while creating MFA account");
		}
		providerAccountUtils.checkRefreshStatusUntilActAddition(configurationObj.getCobrandSessionObj(),
				String.valueOf(providerAccountId));
		HttpMethodParameters httpMethodParameter = HttpMethodParameters.builder().build();
		httpMethodParameter.setFormParams(mapPath);
		Response refreshResponse1 = providerAccountUtils.getProviderAcctDetails(httpMethodParameter,
				configurationObj.getCobrandSessionObj());
		refreshResponse1.then().log().all();
		commonUtils.assertStatusCode(refreshResponse1, status);
		System.out.println("STATUS" + refreshResponse1.jsonPath().getString("providerAccount.refreshInfo.status"));
		Assert.assertEquals("SUCCESS", refreshResponse1.jsonPath().getString("providerAccount[0].refreshInfo.status").toString().replaceAll("\\[[[|\\]]]", ""));
	}

	// 891079 - Sammedia || 522/508 Time out errors
	@Test(priority = 4, dataProvider = Constants.FEEDER)
	@Source(MFAUsingSimplifiedLoginForm_CSV)
	public void testMFAUsingSimplifiedLoginForm(String testcaseID, String testcaseName, String providerId,
			String dagUserName, String dagPwd, String tokenValue, String firstQues, String firstAns, String secondQues,
			String secondAns, String defectID, String enabled) throws Exception {

		commonUtils.isTCEnabled(enabled, testcaseID);
		String updatedLoginForm = null;
		Response response = null;
		Map<String, String> credentialsMap = new HashMap<>();
		credentialsMap.put(Constants.DAG_USERNAME, dagUserName);
		credentialsMap.put(Constants.DAG_PASSWORD, dagPwd);

		try {
			updatedLoginForm = nonSdgLoginForm.getUpdatedLoginFormWithCredentials(Long.valueOf(providerId),
					Constants.LOGIN_FORM, Constants.TWO_COUNT, credentialsMap);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		System.out.println("Updated Login Form Object: " + updatedLoginForm);

		Map<String, Object> mapQuery = new HashMap<>();
		mapQuery.put("providerId", providerId);
		HttpMethodParameters httpParams = HttpMethodParameters.builder().build();
		httpParams.setBodyParams(updatedLoginForm);
		httpParams.setQueryParams(mapQuery);
		providerAccountId = providerAccountUtils.addProviderAccountMFA(httpParams,	configurationObj.getCobrandSessionObj());
		System.out.println("Site account id from scripts is testMFAUsingSimplifiedLoginForm: " + providerAccountId);
		
		commonUtils.waitForSeconds(10);
		System.out.println("Waiting is over");
		Map<String, Object> mapPath = new HashMap<>();
		mapPath.put("providerAccountId", providerAccountId);
		HttpMethodParameters httpMethodParameters = HttpMethodParameters.builder().build();
		httpMethodParameters.setPathParams(mapPath);
		Response refreshResponse = providerAccountUtils.getProviderAcctDetails(httpMethodParameters, configurationObj.getCobrandSessionObj());
		System.out.println("Get Provider Account status code ::"+refreshResponse.getStatusCode());
		refreshResponse.then().log().all();

		try {
			System.out.println("creating mfa token");
			String strBodyJson = providerAccountsHelper.createMFATokenObject(Constants.ID, Constants.TOKEN,
					Constants.VALUE, tokenValue);
			HttpMethodParameters httpMethodParameters2 = HttpMethodParameters.builder().build();
			httpMethodParameters2.setBodyParams(strBodyJson);
			HashMap<String, Object> mapQueryParams = new HashMap<>();
			mapQueryParams.put("providerAccountIds", providerAccountId);
			httpMethodParameters2.setQueryParams(mapQueryParams);
			System.out.println("mfa token created");
			System.out.println("changing mfa token");
			response = providerAccountUtils.mfaChallengeToken(httpMethodParameters2, configurationObj.getCobrandSessionObj());
			System.out.println("RESPONSE after token: "+response.prettyPrint()+ "\nStatus CODE: "+response.statusCode());
			System.out.println("Waiting is over after updating token");
			commonUtils.waitForSeconds(15);
			
			String strBodyJson2 = providerAccountsHelper.createInputForMFASecurityQA(firstQues, firstAns, secondQues,secondAns);
			HttpMethodParameters httpMethodParameters3 = HttpMethodParameters.builder().build();
			httpMethodParameters3.setBodyParams(strBodyJson2);
			httpMethodParameters3.setQueryParams(mapQueryParams);
			System.out.println("create Input For MFA Security QA testMFAUsingSimplifiedLoginForm::"+strBodyJson2);
			response = providerAccountUtils.mfaChallengeQandA(httpMethodParameters3, configurationObj.getCobrandSessionObj());			
			
		} catch (Exception e) {
			throw new Exception("Error while creating MFA account");
		}
		providerAccountUtils.checkRefreshStatusUntilActAddition(configurationObj.getCobrandSessionObj(),
				String.valueOf(providerAccountId));

		refreshResponse = providerAccountUtils.getProviderAcctDetails(httpMethodParameters,
				configurationObj.getCobrandSessionObj());
		refreshResponse.then().log().all();
		Assert.assertEquals("SUCCESS", refreshResponse.jsonPath().getString("providerAccount.refreshInfo.status"));
		Response deleteProviderAccountResponse = providerAccountUtils.deleteProviderAccount(httpMethodParameters,
				configurationObj.getCobrandSessionObj());

		deleteProviderAccountResponse.then().log().all();
		if (deleteProviderAccountResponse.statusCode() == HttpStatus.NO_CONTENT_204)
			System.out.println(providerAccountId + " Deleted");

		Assert.assertEquals(deleteProviderAccountResponse.statusCode(), HttpStatus.NO_CONTENT_204);
	}

	/*
	 * https://blrbugzilla.yodlee.com/show_bug.cgi?id=843288 The Add Account by
	 * passing the Provider object entity is working fine No exceptions thrown
	 */
	@Test(priority = 6, dataProvider = Constants.FEEDER)
	@Source(AccountProviderObject_CSV)
	public void teacaAccountProviderObject(String testcaseID, String testcaseName, String defectID, String enabled) {

		commonUtils.isTCEnabled(enabled, testcaseID);
		String strProviderId = "16441";
		Map<String, Object> mapQuery = new HashMap<>();
		mapQuery.put("providerId", strProviderId);
		String jsonInput = providerAccountsHelper.createInputForAccount(Long.valueOf(strProviderId), dagUserName,
				dagPassword, "providerobject", false, includeDataSet);
		HttpMethodParameters httpParams = HttpMethodParameters.builder().build();
		httpParams.setBodyParams(jsonInput);
		httpParams.setQueryParams(mapQuery);
		Response addAccountResponse = providerAccountUtils.addProviderAccount(httpParams,
				configurationObj.getCobrandSessionObj());
		Assert.assertEquals(addAccountResponse.getStatusCode(), HttpStatus.CREATED);
	}

	/*
	 * https://blrbugzilla.yodlee.com/show_bug.cgi?id=850044
	 */
	@Test(priority = 7, dataProvider = Constants.FEEDER)
	@Source(AccoundAdd_16291_CSV)
	public void teacaAccoundAdd_16291(String testcaseID, String testcaseName, String defectID, String enabled) {

		commonUtils.isTCEnabled(enabled, testcaseID);
		String strProviderId = "16291";
		Map<String, Object> mapQuery = new HashMap<>();
		mapQuery.put("providerId", strProviderId);
		String jsonInput = providerAccountsHelper.createInputForAccount(Long.valueOf(strProviderId), dagUserName,
				dagPassword, "loginForm", false, includeDataSet);
		
		HttpMethodParameters httpParams = HttpMethodParameters.builder().build();
		httpParams.setBodyParams(jsonInput);
		httpParams.setQueryParams(mapQuery);
		Response addAccountResponse = providerAccountUtils.addProviderAccount(httpParams, configurationObj.getCobrandSessionObj());
		
		Assert.assertEquals("IN_PROGRESS", addAccountResponse.jsonPath().getString("providerAccount.refreshInfo.status"));
	}

	/*
	 * 860755 In Production, We are getting below Framework error while account addition
	 */
	@Test(priority = 8, dataProvider = Constants.FEEDER)
	@Source(AddAccount8999_CSV)
	public void teacaAddAccount8999(String testcaseID, String testcaseName, String defectID, String enabled) {

		commonUtils.isTCEnabled(enabled, testcaseID);
		String strProviderId = "8999";
		Map<String, Object> mapQuery = new HashMap<>();
		mapQuery.put("providerId", strProviderId);
		String jsonInput = providerAccountsHelper.createInputForAccount(Long.valueOf(strProviderId), dagUserName, dagPassword, "loginForm", false, includeDataSet);
		HttpMethodParameters httpParams = HttpMethodParameters.builder().build();
		httpParams.setBodyParams(jsonInput);
		httpParams.setQueryParams(mapQuery);
		Response addAccountResponse = providerAccountUtils.addProviderAccount(httpParams, configurationObj.getCobrandSessionObj());
		addAccountResponse.then().log().all();
		Assert.assertEquals("IN_PROGRESS", addAccountResponse.jsonPath().getString("providerAccount.refreshInfo.status"));
	}
}
