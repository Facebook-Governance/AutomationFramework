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
 * @author psham
 *
 */
public class TestTeacaProviderAccounts extends Base {

	String loginFormType = "loginform";
	static boolean isincludeDataSet = true;
	static String includeDataSet = "ACCT_PROFILE.FULL_ACCT_NUMBER,ACCT_PROFILE.BANK_TRANSFER_CODE,ACCT_PROFILE.HOLDER_NAME";
	int status = 200;
	private long providerAccountId;
	String providerObejct = "providerobject";
	public String dagUN = "DAPI.site16442.1";
	public String dagPWD = "site16442.1";
	String providerAccountIdAdd;
	String dagUser = "Rajeev_Site.site16441.7";
	String dagPasswrod = "site16441.7";
	JSONObject loginFormMFA;
	
	CommonUtils commonUtils = new CommonUtils();
	Configuration configuration = Configuration.getInstance();
	ProviderAccountsHelper providerAccountsHelper = new ProviderAccountsHelper();
	ProviderAccountUtils providerAccountUtils = new ProviderAccountUtils();
	AccountUtils accountUtils = new AccountUtils();
	NonSdgLoginForm nonSdgLoginForm = NonSdgLoginForm.getInstance();
	
	//@Test(enabled = true, dataProvider = Constants.FEEDER)
	@Source("\\TestData\\CSVFiles\\Teaca\\teacaIAVAccountUpdate.csv")
	public void teacaIAVAccountUpdate(String testCaseID, String testCaseName, String providerId, String dagUserName, String dagPassword, 
			String tokenValue, String firstQues, String firstAns, String secondQues, String secondAns, String bugID, String enabled) {
		
		commonUtils.isTCEnabled(enabled, testCaseID);

		System.out.println("Data Set: " + includeDataSet);
		Map<String, Object> mapQuery = new HashMap<>();
		mapQuery.put("providerId", providerId);
		String jsonInput = providerAccountsHelper.createInputForAccount(Long.valueOf(providerId), dagUserName, dagPassword, loginFormType, isincludeDataSet,includeDataSet);
		HttpMethodParameters httpParams = HttpMethodParameters.builder().build();
		httpParams.setBodyParams(jsonInput);
		httpParams.setQueryParams(mapQuery);
		providerAccountId = providerAccountUtils.addProviderAccountMFA(httpParams, configuration.getCobrandSessionObj());
		
		System.out.println("Provider Account Id is : " + providerAccountId);
		commonUtils.waitForSeconds(10);
		HashMap<String, Object> mapPath = new HashMap<>();
		HashMap<String, String> mapQueryParam = new HashMap<>();
		mapPath.put("providerAccountId", providerAccountId);
		commonUtils.waitForSeconds(20);
		mapQueryParam.put("include", "credentials");
		HttpMethodParameters httpParam = HttpMethodParameters.builder().build();
		httpParam.setPathParams(mapPath);
		httpParam.setQueryParams(mapQueryParam);
		Response refreshResponse = providerAccountUtils.getProviderAcctDetails(httpParam,
				configuration.getCobrandSessionObj());

		try {
			commonUtils.waitForSeconds(10);
			/*HttpMethodParameters httpMethodParameters = HttpMethodParameters.builder().build();
			httpMethodParameters.setPathParams(mapPath);
			Response refreshResponse1 = providerAccountUtils.getProviderAcctDetails(httpMethodParameters,
					configuration.getCobrandSessionObj());*/
			String strBodyJson = providerAccountsHelper.createMFATokenObject(Constants.ID, Constants.TOKEN,
					Constants.VALUE, tokenValue);
			HttpMethodParameters httpMethodParameters2 = HttpMethodParameters.builder().build();
			httpMethodParameters2.setBodyParams(strBodyJson);
			HashMap<String, Object> mapQueryParams = new HashMap<>();
			mapQueryParams.put("providerAccountId", providerAccountId);
			httpMethodParameters2.setQueryParams(mapQueryParams);
			providerAccountUtils.mfaChallengeToken(httpMethodParameters2, configuration.getCobrandSessionObj());
						
			commonUtils.waitForSeconds(10);
			String strBodyJson2 = providerAccountsHelper.createInputForMFASecurityQA(firstQues, firstAns, secondQues,
					secondAns);
			HttpMethodParameters httpMethodParameters3 = HttpMethodParameters.builder().build();
			httpMethodParameters3.setBodyParams(strBodyJson2);
			httpMethodParameters3.setQueryParams(mapQueryParams);
			providerAccountUtils.mfaChallengeQandA(httpMethodParameters3, configuration.getCobrandSessionObj());
		
		} catch (JSONException e) {
			e.printStackTrace();
		}
		providerAccountUtils.checkRefreshStatusUntilActAddition(configuration.getCobrandSessionObj(),
				String.valueOf(providerAccountId));
		HttpMethodParameters httpMethodParameters = HttpMethodParameters.builder().build();
		httpMethodParameters.setPathParams(mapPath);
		Response refreshResponse1 = providerAccountUtils.getProviderAcctDetails(httpMethodParameters,
				configuration.getCobrandSessionObj());

		commonUtils.assertStatusCode(refreshResponse1, status);
		System.out.println("STATUS" + refreshResponse1.jsonPath().getString("providerAccount.refreshInfo.status"));
		Assert.assertEquals("SUCCESS", refreshResponse1.jsonPath().getString("providerAccount.refreshInfo.status"));

		// Get accounts for bank transfercode
		// update IVA Account
		System.out.println("********************* UPDATING IAV TO PFM ACCOUNT ********************");

		httpMethodParameters.setQueryParams(mapPath);
		Response resUpdate = providerAccountUtils.updateProviderAccount(httpMethodParameters, configuration.getCobrandSessionObj());	
		Response deleteProviderAccountResponse = providerAccountUtils.deleteProviderAccount(httpMethodParameters,
				configuration.getCobrandSessionObj());
		
		if (deleteProviderAccountResponse.statusCode() == HttpStatus.NO_CONTENT_204)
			System.out.println("ProviderAcctId " + providerAccountId + " Deleted");
	}

	/*
	 * BugÂ 942265 - [Amelia-L2]-PFM to IAV account update is failing while
	 * updating account without credentials Adding PFM account Updating IVA
	 * account without userName and password
	 */
	@Test(enabled = true, dataProvider = Constants.FEEDER)
	@Source("\\TestData\\CSVFiles\\Teaca\\teacaPFMToIVAWithoutCred.csv")
	public void teacaPFMToIVAWithoutCred(String testCaseID, String testCaseName, String bugID, String enabled) {

		commonUtils.isTCEnabled(enabled, testCaseID);

		String providerId = "16441";
		String providerAccountId = "";
		boolean isRefreshSuccess = false;
		Response response = null;
		Map<String, Object> mapQuery = new HashMap<>();
		mapQuery.put("providerId", providerId);
		System.out.println("DONE");
		String jsonInput = providerAccountsHelper.createInputForAccount(Long.valueOf(providerId), dagUser, dagPasswrod, "loginForm", false, "");
		System.out.println("jsonString: " + jsonInput);
		HttpMethodParameters httpParams = HttpMethodParameters.builder().build();
		httpParams.setBodyParams(jsonInput);
		httpParams.setQueryParams(mapQuery);
		Response addAccountResponse = providerAccountUtils.addProviderAccount(httpParams, configuration.getCobrandSessionObj());
		providerAccountId = addAccountResponse.jsonPath().getString("providerAccount.id");
		int providerAccountStatusCode = addAccountResponse.getStatusCode();
		System.out.println("providerAccountStatusCode: "+ providerAccountStatusCode);
		String refreshStatus = null;
		
		if (providerAccountStatusCode == 201) {
			
			System.out.println("Site account id is : " + providerAccountId);
			isRefreshSuccess = providerAccountUtils.checkRefreshStatusUntilActAddition(configuration.getCobrandSessionObj(), String.valueOf(providerAccountId));
			System.out.println("provider acct id: " + providerAccountId);
		} 
		HashMap<String, Object> mapPath = new HashMap<>();
		mapPath.put("providerAccountId", providerAccountId);
		if (isRefreshSuccess) {
			HttpMethodParameters httpMethodParameters = HttpMethodParameters.builder().build();
			httpMethodParameters.setPathParams(mapPath);
			Response refreshResponse1 = providerAccountUtils.getProviderAcctDetails(httpMethodParameters, configuration.getCobrandSessionObj());
			Assert.assertEquals("SUCCESS",refreshResponse1.jsonPath().getString("providerAccount.refreshInfo.status"));
		}
		// Update to IVA without cred
		HttpMethodParameters httpMethodParameters =  HttpMethodParameters.builder().build();
		httpMethodParameters.setQueryParams(mapPath);
		Response resUpdate = providerAccountUtils.updateProviderAccount(httpMethodParameters, configuration.getCobrandSessionObj());

		isRefreshSuccess = providerAccountUtils.checkRefreshStatusUntilActAddition(configuration.getCobrandSessionObj(), String.valueOf(providerAccountId));
		HttpMethodParameters httpParam = HttpMethodParameters.builder().build();
		httpParam.setPathParams(mapPath);
		if (isRefreshSuccess) {
			Response refreshResponse1 = providerAccountUtils.getProviderAcctDetails(httpParam, configuration.getCobrandSessionObj());
			System.out.println("After updating PFM to IVA account "+ refreshResponse1.prettyPrint());
			Assert.assertEquals("SUCCESS", refreshResponse1.jsonPath().getString("providerAccount.refreshInfo.status"));
		}

		response = accountUtils.getAccounts(null, configuration.getCobrandSessionObj());
		System.out.println("The Account response is with bank trasfercode"+ response.prettyPrint());
		// delete account
		
		Response deleteProviderAccountResponse = providerAccountUtils.deleteProviderAccount(httpParam, configuration.getCobrandSessionObj());
		if (deleteProviderAccountResponse.statusCode() == HttpStatus.NO_CONTENT_204)
			System.out.println(providerAccountId + " Deleted");
	}

	/*
	 * 858920 - Onist: error on `DAG CreditCard` and `DAG Loan` (production)
	 * This is for credit card and then deleting providerAccount
	 */
	@Test(enabled = true, dataProvider = Constants.FEEDER)
	@Source("\\TestData\\CSVFiles\\Teaca\\teacaAdddCredtCardAccount.csv")
	public void teacaAdddCredtCardAccount(String testCaseID,
			String testCaseName, String bugID, String enabled) {

		commonUtils.isTCEnabled(enabled, testCaseID);
		String providerId = "8998";
		String providerAccountId = "";
		boolean isRefreshSuccess = false;
		Map<String, Object> mapQuery = new HashMap<>();
		mapQuery.put("providerId", providerId);
		System.out.println("DONE");
		String jsonInput = providerAccountsHelper.createInputForAccount(Long.valueOf(providerId), "dubey4.creditCard2", "creditCard2", "loginForm", Boolean.valueOf(enabled), "");
		System.out.println("jsonString: " + jsonInput);
		HttpMethodParameters httpParams = HttpMethodParameters.builder().build();
		httpParams.setBodyParams(jsonInput);
		httpParams.setQueryParams(mapQuery);
		Response addAccountResponse = providerAccountUtils.addProviderAccount(httpParams, configuration.getCobrandSessionObj());
		providerAccountId = addAccountResponse.jsonPath().getString("providerAccount.id");
		System.out.println("DONE");
		int providerAccountStatusCode = addAccountResponse.getStatusCode();
		System.out.println("providerAccountStatusCode: "+ providerAccountStatusCode);

		if (providerAccountStatusCode == 201) {
			System.out.println("Site account id is : " + providerAccountId);
			isRefreshSuccess = providerAccountUtils.checkRefreshStatusUntilActAddition(configuration.getCobrandSessionObj(),	String.valueOf(providerAccountId));
			System.out.println("provider acct id: " + providerAccountId);
		}
		HashMap<String, Object> mapPath = new HashMap<>();
		mapPath.put("providerAccountId", providerAccountId);
		HttpMethodParameters httpParam = HttpMethodParameters.builder().build();
		httpParam.setPathParams(mapPath);
		if (isRefreshSuccess) {
			Response refreshResponse1 = providerAccountUtils.getProviderAcctDetails(httpParam,configuration.getCobrandSessionObj());
			Assert.assertEquals(refreshResponse1.jsonPath().getString("providerAccount.refreshInfo.status"), "SUCCESS");
		}

		// delete account
		Response deleteProviderAccountResponse = providerAccountUtils.deleteProviderAccount(httpParam, configuration.getCobrandSessionObj());
		if (deleteProviderAccountResponse.statusCode() == HttpStatus.NO_CONTENT_204)
			System.out.println(providerAccountId + " Deleted");
	}

	/*
	 * 858920 - Onist: error on `DAG CreditCard` and `DAG Loan` (production)
	 * This is for Loan and then deleting providerAccount
	 */
	@Test(enabled = true, dataProvider = Constants.FEEDER)
	@Source("\\TestData\\CSVFiles\\Teaca\\teacaAddLoanAccount.csv")
	public void teacaAddLoanAccount(String testCaseID, String testCaseName, String bugID, String enabled) {

		commonUtils.isTCEnabled(enabled, testCaseID);
		String providerId = "8999";;
		String providerAccountId = "";
		boolean isRefreshSuccess = false;
		Map<String, Object> mapQuery = new HashMap<>();
		mapQuery.put("providerId", providerId);
		System.out.println("DONE testing");
		String jsonInput = providerAccountsHelper.createInputForAccount(Long.valueOf(providerId), "dubey4.Loans1", "Loans1", Constants.LOGIN_FORM, Boolean.valueOf(enabled), includeDataSet);
		System.out.println("jsonString: " + jsonInput);
		HttpMethodParameters httpParams = HttpMethodParameters.builder().build();
		httpParams.setBodyParams(jsonInput);
		httpParams.setQueryParams(mapQuery);
		Response addAccountResponse = providerAccountUtils.addProviderAccount(httpParams, configuration.getCobrandSessionObj());
		providerAccountId = addAccountResponse.jsonPath().getString("providerAccount.id");
		int providerAccountStatusCode = addAccountResponse.getStatusCode();
		System.out.println("providerAccountStatusCode: "+ providerAccountStatusCode);

		if (providerAccountStatusCode == 201) {
			System.out.println("Site account id is : " + providerAccountId);
			isRefreshSuccess = providerAccountUtils.checkRefreshStatusUntilActAddition(configuration.getCobrandSessionObj(),	String.valueOf(providerAccountId));
			System.out.println("provider acct id: " + providerAccountId);
		}
		HashMap<String, Object> mapPath = new HashMap<>();
		mapPath.put("providerAccountId", providerAccountId);
		HttpMethodParameters httpParam = HttpMethodParameters.builder().build();
		httpParam.setPathParams(mapPath);
		if (isRefreshSuccess) {
			Response refreshResponse1 = providerAccountUtils.getProviderAcctDetails(httpParam,configuration.getCobrandSessionObj());
			Assert.assertEquals("SUCCESS", refreshResponse1.jsonPath().getString("providerAccount.refreshInfo.status"));
		}

		// delete account
		Response deleteProviderAccountResponse = providerAccountUtils.deleteProviderAccount(httpParam, configuration.getCobrandSessionObj());
		
		if (deleteProviderAccountResponse.statusCode() == HttpStatus.NO_CONTENT_204)
			System.out.println(providerAccountId + " Deleted");
	}

	/*
	 * 873450 - Moneydashboard | YSL | Error Handling Fix required to be done
	 */
	@Test(enabled = true, dataProvider = Constants.FEEDER)
	@Source("\\TestData\\CSVFiles\\Teaca\\teacaProviderAccountsMFAMissingLABEL.csv")
	public void teacaProviderAccountsMFAMissingLABEL(String testCaseID, String testCaseName, 
			String providerId, String dagUserName, String dagPwd, String tokenValue, String firstQues, 
			String firstAns, String secondQues, String secondAns, String bugID, String enabled) {

		commonUtils.isTCEnabled(enabled, testCaseID);;
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
		Long providerAccountId = providerAccountUtils.addProviderAccountMFA(httpParams,	configuration.getCobrandSessionObj());
		System.out.println("Site account id from scripts is : " + providerAccountId);
		
		commonUtils.waitForSeconds(10);
		System.out.println("Waiting is over");
		Map<String, Object> mapPath = new HashMap<>();
		mapPath.put("providerAccountId", providerAccountId);
		HttpMethodParameters httpMethodParameters = HttpMethodParameters.builder().build();
		httpMethodParameters.setPathParams(mapPath);
		Response refreshResponse = providerAccountUtils.getProviderAcctDetails(httpMethodParameters, configuration.getCobrandSessionObj());
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
			response = providerAccountUtils.mfaChallengeToken(httpMethodParameters2, configuration.getCobrandSessionObj());
			System.out.println("RESPONSE after token: "+response.prettyPrint()+ "\nStatus CODE: "+response.statusCode());
			System.out.println("Waiting is over after updating token");
			commonUtils.waitForSeconds(15);
			
			String strBodyJson2 = providerAccountsHelper.createInputForMFASecurityQANegative(firstQues, firstAns, secondQues,secondAns);
			HttpMethodParameters httpMethodParameters3 = HttpMethodParameters.builder().build();
			httpMethodParameters3.setBodyParams(strBodyJson2);
			httpMethodParameters3.setQueryParams(mapQueryParams);
			System.out.println("create Input For MFA Security QA ::"+strBodyJson2);
			response = providerAccountUtils.mfaChallengeQandA(httpMethodParameters3, configuration.getCobrandSessionObj());			
			System.out.println("RESPONSE after MFA Q and A: "+response.prettyPrint()+ "\nStatus CODE: "+response.statusCode());
			System.out.println("Waiting is over after updating Q and A");
			Assert.assertEquals(response.jsonPath().getString("errorMessage"), "Required field/value - label missing in the input");
		} catch (JSONException e) {
			e.printStackTrace();
		}
		// delete account
		Response deleteProviderAccountResponse = providerAccountUtils.deleteProviderAccount(httpMethodParameters,configuration.getCobrandSessionObj());
		
		if (deleteProviderAccountResponse.statusCode() == HttpStatus.NO_CONTENT_204)
			System.out.println("ProvideAccountId " + providerAccountId + " Deleted");
	}
	
	/*
	 * PFM add account and update the account
	 */
	@Test(dataProvider = Constants.FEEDER, enabled = true)
	@Source("\\TestData\\CSVFiles\\Teaca\\teacaAddAndUpdateAccountLoginForm.csv")
	public void teacaAddAndUpdateAccountLoginForm(String testCaseID, String testCaseName, String bugID, String enabled) {

		commonUtils.isTCEnabled(enabled, testCaseID);
		String providerId = "16441";
		String providerAccountId = "";
		boolean isRefreshSuccess = false;
		Response response = null;
		Map<String, Object> mapQuery = new HashMap<>();
		mapQuery.put("providerId", providerId);
		System.out.println("DONE");
		String jsonInput = providerAccountsHelper.createInputForAccount(Long.valueOf(providerId), dagUser, dagPasswrod, "loginForm", false, "");
		System.out.println("jsonString: " + jsonInput);
		HttpMethodParameters httpParams = HttpMethodParameters.builder().build();
		httpParams.setBodyParams(jsonInput);
		httpParams.setQueryParams(mapQuery);
		Response addAccountResponse = providerAccountUtils.addProviderAccount(httpParams, configuration.getCobrandSessionObj());
		providerAccountId = addAccountResponse.jsonPath().getString("providerAccount.id");
		int providerAccountStatusCode = addAccountResponse.getStatusCode();
		System.out.println("providerAccountStatusCode: "+ providerAccountStatusCode);
		String refreshStatus = null;
		HashMap<String, Object> mapPath = new HashMap<>();
		mapPath.put("providerAccountId", providerAccountId);
		if (providerAccountStatusCode == 201) {
			System.out.println("Site account id is : " + providerAccountId);
			isRefreshSuccess = providerAccountUtils.checkRefreshStatusUntilActAddition(configuration.getCobrandSessionObj(),	String.valueOf(providerAccountId));
			System.out.println("provider acct id: " + providerAccountId);
		}
		// update the account
		HttpMethodParameters httpMethodParameters =  HttpMethodParameters.builder().build();
		httpMethodParameters.setQueryParams(mapPath);
		Response resUpdate = providerAccountUtils.updateProviderAccount(httpMethodParameters, configuration.getCobrandSessionObj());

		providerAccountStatusCode = addAccountResponse.getStatusCode();
		System.out.println("providerAccountStatusCode: " + providerAccountStatusCode);
		refreshStatus = null;

		if (providerAccountStatusCode == 200) {
			System.out.println("Site account id is : " + providerAccountId);
			isRefreshSuccess = providerAccountUtils.checkRefreshStatusUntilActAddition(configuration.getCobrandSessionObj(),	String.valueOf(providerAccountId));
			System.out.println("provider acct id: " + providerAccountId);
		}

		// delete account
		HttpMethodParameters httpParam = HttpMethodParameters.builder().build();
		httpParam.setPathParams(mapPath);
		Response deleteProviderAccountResponse = providerAccountUtils.deleteProviderAccount(httpParam, configuration.getCobrandSessionObj());
		if (deleteProviderAccountResponse.statusCode() == HttpStatus.NO_CONTENT_204)
			System.out.println(providerAccountIdAdd + " Deleted");
	}

	// Add IAV account and update IAV.
	@Test(dataProvider = Constants.FEEDER, enabled = true)
	@Source("\\TestData\\CSVFiles\\Teaca\\teacaAddAndUpdateAccountLoginFormIAV.csv")
	public void teacaAddAndUpdateAccountLoginFormIAV(String testCaseID, String testCaseName, String bugID, String enabled) {

		commonUtils.isTCEnabled(enabled, testCaseID);
		String providerId = "16441";
		String providerAccountId = "";
		boolean isRefreshSuccess = false;
		Map<String, Object> mapQuery = new HashMap<>();
		mapQuery.put("providerId", providerId);
		String jsonInput = providerAccountsHelper.createInputForAccount(Long.valueOf(providerId), dagUser, dagPasswrod, "loginform", Boolean.valueOf(enabled), "");
		System.out.println("jsonString: " + jsonInput);
		HttpMethodParameters httpParams = HttpMethodParameters.builder().build();
		httpParams.setBodyParams(jsonInput);
		httpParams.setQueryParams(mapQuery);
		Response addAccountResponse = providerAccountUtils.addProviderAccount(httpParams, configuration.getCobrandSessionObj());
		providerAccountId = addAccountResponse.jsonPath().getString("providerAccount.id");
		int providerAccountStatusCode = addAccountResponse.getStatusCode();
		System.out.println("providerAccountStatusCode: "+ providerAccountStatusCode);
		String refreshStatus = null;

		HashMap<String, Object> mapPath = new HashMap<>();
		mapPath.put("providerAccountIds", providerAccountId);
		if (providerAccountStatusCode == 201) {
			System.out.println("Site account id is : " + providerAccountId);
			isRefreshSuccess = providerAccountUtils.checkRefreshStatusUntilActAddition(configuration.getCobrandSessionObj(), String.valueOf(providerAccountId));
			System.out.println("provider acct id: " + providerAccountId);

		}
		// update the account
		HttpMethodParameters httpMethodParameters =  HttpMethodParameters.builder().build();
		httpMethodParameters.setQueryParams(mapPath);
		Response resUpdate = providerAccountUtils.updateProviderAccount(httpMethodParameters, configuration.getCobrandSessionObj());
		providerAccountStatusCode = resUpdate.getStatusCode();
		System.out.println("providerAccountStatusCode: "+ providerAccountStatusCode);
		refreshStatus = null;

		HashMap<String, Object> mapPath1 = new HashMap<>();
		mapPath1.put("providerAccountId", providerAccountId);
		HttpMethodParameters httpParam = HttpMethodParameters.builder().build();
		httpParam.setPathParams(mapPath1);
		if (providerAccountStatusCode == 200) {
			System.out.println("Site account id is : " + providerAccountId);
			isRefreshSuccess = providerAccountUtils.checkRefreshStatusUntilActAddition(configuration.getCobrandSessionObj(), String.valueOf(providerAccountId));
			System.out.println("provider acct id: " + providerAccountId);
			Response refreshResponse1 = providerAccountUtils.getProviderAcctDetails(httpParam, configuration.getCobrandSessionObj());
			Assert.assertEquals("SUCCESS", refreshResponse1.jsonPath().getString("providerAccount.refreshInfo.status"));
		} else {
			System.out
					.println("Making it fail because its status code should be 200 but got"
							+ providerAccountStatusCode);
			Assert.assertEquals("SUCCESS", "SUCCESS");

		}
		// delete account
		Response deleteProviderAccountResponse = providerAccountUtils.deleteProviderAccount(httpParam, configuration.getCobrandSessionObj());
		
		if (deleteProviderAccountResponse.statusCode() == HttpStatus.NO_CONTENT_204)
			System.out.println(providerAccountIdAdd + " Deleted");
	}

	/*
	 * Add IAV Account and update to PFM
	 */
	@Test(dataProvider = Constants.FEEDER, enabled = true)
	@Source("\\TestData\\CSVFiles\\Teaca\\teacaAddIVAToUpdateToPFM.csv")
	public void teacaAddIVAToUpdateToPFM(String testCaseID,String testCaseName, String bugID, String enabled) {
		
		commonUtils.isTCEnabled(enabled, testCaseID);
		String providerId = "16441";
		String providerAccountId = "";
		boolean isRefreshSuccess = false;;
		LinkedHashMap<String, String> queryParams = new LinkedHashMap<>();
		queryParams.put("providerId", "16441");
		Map<String, Object> mapQuery = new HashMap<>();
		mapQuery.put("providerId", providerId);
		String jsonInput = providerAccountsHelper.createInputForAccount(Long.valueOf(providerId), dagUser, dagPasswrod, "loginform", false, "");
		System.out.println("jsonString: " + jsonInput);
		HttpMethodParameters httpParams = HttpMethodParameters.builder().build();
		httpParams.setBodyParams(jsonInput);
		httpParams.setQueryParams(mapQuery);
		Response addAccountResponse = providerAccountUtils.addProviderAccount(httpParams, configuration.getCobrandSessionObj());
		providerAccountId = addAccountResponse.jsonPath().getString("providerAccount.id");
		int providerAccountStatusCode = addAccountResponse.getStatusCode();
		System.out.println("providerAccountStatusCode: "
				+ providerAccountStatusCode);
		String refreshStatus = null;

		if (providerAccountStatusCode == 201) {
			System.out.println("Site account id is : " + providerAccountId);
			isRefreshSuccess = providerAccountUtils.checkRefreshStatusUntilActAddition(configuration.getCobrandSessionObj(),	String.valueOf(providerAccountId));
			System.out.println("provider acct id: " + providerAccountId);
		}

		// update the account
		HashMap<String, Object> mapPath = new HashMap<>();
		mapPath.put("providerAccountIds", providerAccountId);
		HttpMethodParameters httpMethodParameters =  HttpMethodParameters.builder().build();
		httpMethodParameters.setQueryParams(mapPath);
		Response resUpdate = providerAccountUtils.updateProviderAccount(httpMethodParameters, configuration.getCobrandSessionObj());
		providerAccountStatusCode = resUpdate.getStatusCode();
		System.out.println("providerAccountStatusCode: "+ providerAccountStatusCode);
		refreshStatus = null;

		HashMap<String, Object> mapPath1 = new HashMap<>();
		mapPath1.put("providerAccountId", providerAccountId);
		HttpMethodParameters httpParam = HttpMethodParameters.builder().build();
		httpParam.setPathParams(mapPath1);
		if (providerAccountStatusCode == 200) {
			System.out.println("Site account id is : " + providerAccountId);
			isRefreshSuccess = providerAccountUtils.checkRefreshStatusUntilActAddition(configuration.getCobrandSessionObj(),	String.valueOf(providerAccountId));
			System.out.println("provider acct id: " + providerAccountId);
			Response refreshResponse1 = providerAccountUtils.getProviderAcctDetails(httpParam, configuration.getCobrandSessionObj());
			Assert.assertEquals("SUCCESS", refreshResponse1.jsonPath().getString("providerAccount.refreshInfo.status"));
		} else {
			System.out.println("Making it fail because its status code should be 200 but got"+ providerAccountStatusCode);
			Assert.assertEquals("SUCCESS", "FAILURE");
		}
		// delete account
		Response deleteProviderAccountResponse = providerAccountUtils.deleteProviderAccount(httpParam, configuration.getCobrandSessionObj());
		if (deleteProviderAccountResponse.statusCode() == HttpStatus.NO_CONTENT_204)
			System.out.println(providerAccountIdAdd + " Deleted");
	}

	/*
	 * Add PFM account and udpate to IAV
	 */
	@Test(enabled = true, dataProvider = Constants.FEEDER)
	@Source("\\TestData\\CSVFiles\\Teaca\\teacaAddPFMToUpdateIAV.csv")
	public void teacaAddPFMToUpdateIAV(String testCaseID, String testCaseName, String bugID, String enabled) {

		commonUtils.isTCEnabled(enabled, testCaseID);
		String providerId = "16441";
		String providerAccountId = "";
		boolean isRefreshSuccess = false;;
		LinkedHashMap<String, String> queryParams = new LinkedHashMap<>();
		queryParams.put("providerId", "16441");
		Map<String, Object> mapQuery = new HashMap<>();
		mapQuery.put("providerId", providerId);
		String jsonInput = providerAccountsHelper.createInputForAccount(Long.valueOf(providerId), dagUser, dagPasswrod, "loginform", false, "");
		System.out.println("jsonString: " + jsonInput);
		HttpMethodParameters httpParams = HttpMethodParameters.builder().build();
		httpParams.setBodyParams(jsonInput);
		httpParams.setQueryParams(mapQuery);
		Response addAccountResponse = providerAccountUtils.addProviderAccount(httpParams, configuration.getCobrandSessionObj());
		providerAccountId = addAccountResponse.jsonPath().getString("providerAccount.id");
		int providerAccountStatusCode = addAccountResponse.getStatusCode();
		System.out.println("providerAccountStatusCode: "+ providerAccountStatusCode);

		HashMap<String, Object> mapPath = new HashMap<>();
		mapPath.put("providerAccountIds", providerAccountId);
		if (providerAccountStatusCode == 201) {
			System.out.println("Site account id is : " + providerAccountId);
			isRefreshSuccess = providerAccountUtils.checkRefreshStatusUntilActAddition(configuration.getCobrandSessionObj(),	String.valueOf(providerAccountId));
			System.out.println("provider acct id: " + providerAccountId);
		}

		// update the account
		HttpMethodParameters httpMethodParameters =  HttpMethodParameters.builder().build();
		httpMethodParameters.setQueryParams(mapPath);
		Response resUpdate = providerAccountUtils.updateProviderAccount(httpMethodParameters, configuration.getCobrandSessionObj());
		providerAccountStatusCode = resUpdate.getStatusCode();
		System.out.println("providerAccountStatusCode: "+ providerAccountStatusCode);

		HashMap<String, Object> mapPath1 = new HashMap<>();
		mapPath1.put("providerAccountId", providerAccountId);
		HttpMethodParameters httpParam = HttpMethodParameters.builder().build();
		httpParam.setPathParams(mapPath1);
		if (providerAccountStatusCode == 200) {
			System.out.println("Site account id is : " + providerAccountId);
			isRefreshSuccess = providerAccountUtils.checkRefreshStatusUntilActAddition(configuration.getCobrandSessionObj(),	String.valueOf(providerAccountId));
			System.out.println("provider acct id: " + providerAccountId);
			Response refreshResponse1 = providerAccountUtils.getProviderAcctDetails(httpParam,configuration.getCobrandSessionObj());
			Assert.assertEquals("SUCCESS", refreshResponse1.jsonPath().getString("providerAccount.refreshInfo.status"));
		} else {
			System.out.println("Making it fail because its status code should be 200 but got"+ providerAccountStatusCode);
			Assert.assertEquals("SUCCESS", "FAILURE");
		}
		// delete account
		Response deleteProviderAccountResponse = providerAccountUtils.deleteProviderAccount(httpParam, configuration.getCobrandSessionObj());	
		if (deleteProviderAccountResponse.statusCode() == HttpStatus.NO_CONTENT_204)
			System.out.println(providerAccountIdAdd + " Deleted");
	}
}
