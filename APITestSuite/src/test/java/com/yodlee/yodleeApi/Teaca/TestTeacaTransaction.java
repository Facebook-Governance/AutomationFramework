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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.databene.benerator.anno.Source;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.yodlee.taas.annote.Info;
import com.yodlee.taas.annote.SubFeature;
import com.yodlee.yodleeApi.common.Base;
import com.yodlee.yodleeApi.common.Configuration;
import com.yodlee.yodleeApi.constants.Constants;
import com.yodlee.yodleeApi.constants.JSONPaths;
import com.yodlee.yodleeApi.helper.ProviderAccountsHelper;
import com.yodlee.yodleeApi.helper.UserHelper;
import com.yodlee.yodleeApi.pojo.HttpMethodParameters;
import com.yodlee.yodleeApi.utils.apiUtils.ProviderAccountUtils;
import com.yodlee.yodleeApi.utils.apiUtils.TransactionUtils;
import com.yodlee.yodleeApi.utils.apiUtils.UserUtils;
import com.yodlee.yodleeApi.utils.commonUtils.CommonUtils;

import io.restassured.response.Response;

/**
 * @author partha sham
 *
 */
public class TestTeacaTransaction extends Base {

	String dagUser = "rajeev4ktrans.site16441.1";// "mamathauser1.site16441.2";
	String dagPasswrod = "site16441.1";// "site16441.2";
	String providerAccountId = null;
	String strProviderId = "16441";
	String strLoginFormType = "loginForm";
	int status = 200;
	String newUserSession = "";
	String json = "";
	String providerAccountId1 = null;
	public static final String TRANSACTION_GET_SKIP_TOP = "\\TestData\\CSVFiles\\Teaca\\Transaction\\TRANSACTION_GET_SKIP_TOP.csv";
	public static final String TRANSACTION = "\\TestData\\CSVFiles\\Teaca\\Transaction\\TRANSACTION.csv";
	public static final String TRANSACTION_GET_TRANS_CARD = "\\TestData\\CSVFiles\\Teaca\\Transaction\\TRANSACTION_GET_TRANS_CARD.csv";

	@BeforeClass
	public void smokeTestAddAccount() throws InterruptedException {

		ProviderAccountUtils providerAccountUtils =  new ProviderAccountUtils();
		ProviderAccountsHelper providerAccountsHelper = new ProviderAccountsHelper();
		CommonUtils commonUtils =  new CommonUtils();
        Configuration configurationObj = Configuration.getInstance();
		providerAccountUtils = new ProviderAccountUtils();
		Map<String, Object> mapQuery = new HashMap<>();
		mapQuery.put("providerId", strProviderId);		
		String jsonInput = providerAccountsHelper.createInputForAccount(Long.valueOf(strProviderId), dagUser, dagPasswrod, strLoginFormType, false, "");
		HttpMethodParameters httpParams = HttpMethodParameters.builder().build();
		httpParams.setBodyParams(jsonInput);
		httpParams.setQueryParams(mapQuery);
		Response addAccountResponse = providerAccountUtils.addProviderAccount(httpParams, configurationObj.getCobrandSessionObj());
		int providerAccountStatusCode = addAccountResponse.getStatusCode();
		System.out.println("providerAccountStatusCode:" + providerAccountStatusCode);
		providerAccountId = String.valueOf(addAccountResponse.jsonPath().getLong(JSONPaths.PROVIDER_ACC_ID));
		if (providerAccountStatusCode == 201) {
			providerAccountId = addAccountResponse.jsonPath().getString("providerAccount.id");
			System.out.println("Site account id is : " + providerAccountId);
			System.out.println("provider acct id:" + providerAccountId);
		}
		HttpMethodParameters httpParamsGetProviderAcct = providerAccountsHelper.createInputForGetProviderAcctDetails(Long.valueOf(providerAccountId), Constants.CREDENTIALS, null);
		Response getProviderAccountDetails = providerAccountUtils.getProviderAcctDetails(httpParamsGetProviderAcct,	Configuration.getInstance().getCobrandSessionObj());

		commonUtils.assertStatusCode(getProviderAccountDetails, status);
		System.out.println("STATUS" + getProviderAccountDetails.jsonPath().getString("providerAccount.refreshInfo.status"));
		Assert.assertEquals("SUCCESS", getProviderAccountDetails.jsonPath().getString("providerAccount.refreshInfo.status"));
	}

	@Test(enabled = true, priority = 1, dataProvider = Constants.FEEDER)
	@Info(subfeature = { SubFeature.GET_TRANSACTIONS }, userStoryId = "")
	@Source(TRANSACTION)
	public void teacaGetTransaction(String testcaseId, String description, String DefectID, String enabled) {
		
		CommonUtils commonUtils = new CommonUtils();
		Configuration configuration = Configuration.getInstance();
		TransactionUtils transactionUtils = new TransactionUtils();
		commonUtils.isTCEnabled(enabled, testcaseId);
		System.out.println("teacaGetTransaction Started.....");
		List<Long> containerCount = new ArrayList<Long>();

		Long id = null;
		Map<String, String> params = new HashMap<String, String>();
		params.put("fromDate", "2001-01-01");
		HttpMethodParameters httpParam = HttpMethodParameters.builder().build();
		httpParam.setQueryParams(params);
		Response response = transactionUtils.getAllTransactions(httpParam, configuration.getCobrandSessionObj());
		
		Assert.assertNotNull(response.prettyPrint());
		Assert.assertEquals(200, response.statusCode());

		JSONObject res = new JSONObject(response.prettyPrint());
		org.json.JSONArray getTrans = res.getJSONArray("transaction");

		for (int count = 0; count < getTrans.length(); count++) {
			JSONObject json = getTrans.getJSONObject(count);
			id = json.getLong("id");
			containerCount.add(id);
		}

		System.out.println("Total counts are " + containerCount.size());
		Long actual = (long) 500;
		Long value = (long) containerCount.size();
		Assert.assertEquals(actual, value);
		System.out.println("teacaGetTransaction Ends");
	}

	@Test(dataProvider = "feeder", enabled = true, priority = 2)
	@Info(subfeature = { SubFeature.GET_TRANSACTIONS }, userStoryId = "")
	@Source(TRANSACTION_GET_SKIP_TOP)
	public void teacaGetSkipAndTopTransaction(String testCaseId, String testCaseName, String parameter, String value,
			int status, String filePath, String fileName, String enabled, int verifyCount, String DefectID) {
		
		TransactionUtils transactionUtils = new TransactionUtils();
		Configuration configuration =  Configuration.getInstance();
		CommonUtils commonUtils = new CommonUtils();
		
		commonUtils.isTCEnabled(enabled, testCaseId);
		System.out.println("smokeTestGetTransaction Strared.....");
		List<Long> containerCount = new ArrayList<Long>();
		Long id = null;
		Map<String, String> params = new HashMap<String, String>();
		if (parameter.equals("topSkip")) {
			params.put("fromDate", "2001-01-01");
			params.put("skip", "40");
			params.put("top", "450");
		} else {
			params.put("fromDate", "2001-01-01");
			params.put(parameter, value);
		}
		HttpMethodParameters httpParam = HttpMethodParameters.builder().build();
		httpParam.setQueryParams(params);
		Response response = transactionUtils.getAllTransactions(httpParam, configuration.getCobrandSessionObj());
		System.out.println("Response " + response.prettyPrint());
		JSONObject res = new JSONObject(response.prettyPrint());
		if (value.equals("501")) {
			Assert.assertEquals("Permitted values of top between 1 - 500", response.jsonPath().getString("errorMessage"));
		} else {
			org.json.JSONArray getTrans = res.getJSONArray("transaction");
			for (int count = 0; count < getTrans.length(); count++) {
				JSONObject json = getTrans.getJSONObject(count);
				id = json.getLong("id");
				containerCount.add(id);
			}
			if (testCaseId.equals("6")) {
				Assert.assertEquals(450, containerCount.size());
			} else if (!testCaseId.equals("6") || !testCaseId.equals("2")) {
				System.out.println("TESTCASE Name and TESTCaseID " + testCaseName + " TESTCaseID " + testCaseId);
				Assert.assertEquals(verifyCount, containerCount.size());
				System.out.println("PASSED ::" + "TESTCASE Name and TESTCaseID " + testCaseName + " TESTCaseID " + testCaseId);
			}
			System.out.println("The total count " + testCaseId + " " + " testCaseName " + containerCount.size());
		}
	}

	@Test(dataProvider = "feeder", enabled = true, priority = 3)
	public void addAccountForTransactonStatement() {

		CommonUtils commonUtils = new CommonUtils();
		Configuration  configuration = Configuration.getInstance();
		UserHelper userHelper =  new UserHelper();
		UserUtils userUtils = new UserUtils();
		ProviderAccountsHelper providerAccountsHelper = new ProviderAccountsHelper();
		ProviderAccountUtils providerAccountUtils = new ProviderAccountUtils();
		System.out.println("Register the user");
		// Register the user
		String loginName = "SMOKE" + System.currentTimeMillis();
		json = userHelper.userRegistrationObject(loginName, "TEST@123");
		HttpMethodParameters httpParam = HttpMethodParameters.builder().build();
		httpParam.setBodyParams(json);
		String strResponse = userUtils.userRegistration(configuration.getCobrandSessionObj(), httpParam);
		System.out.println("User registration is done with auth token ::"+strResponse);
		
		// Add the account using cred
		System.out.println("Add account is ....goong on ");
		Map<String, Object> mapQuery = new HashMap<>();
		mapQuery.put("providerId", strProviderId);		
		String jsonInput = providerAccountsHelper.createInputForAccount(Long.valueOf(strProviderId), "bteliteaca.site16441.2", "site16441.2", strLoginFormType, false, "");
		HttpMethodParameters httpParams = HttpMethodParameters.builder().build();
		httpParams.setBodyParams(jsonInput);
		httpParams.setQueryParams(mapQuery);
		Response addAccountResponse = providerAccountUtils.addProviderAccount(httpParams, configuration.getCobrandSessionObj());
		int providerAccountStatusCode = addAccountResponse.getStatusCode();
		System.out.println("providerAccountStatusCode:" + providerAccountStatusCode);
		if (providerAccountStatusCode == 201) {
			providerAccountId1 = addAccountResponse.jsonPath().getString("providerAccount.id");
			System.out.println("Site account id is : " + providerAccountId1);
			System.out.println("provider acct id:" + providerAccountId1);
			System.out.println("Account addition is done now we need to test for SUCCESS");
			// Verify the add account status
			HttpMethodParameters httpParamsGetProviderAcct = providerAccountsHelper.createInputForGetProviderAcctDetails(Long.valueOf(providerAccountId), Constants.CREDENTIALS, null);
			Response getProviderAccountDetails = providerAccountUtils.getProviderAcctDetails(httpParamsGetProviderAcct, Configuration.getInstance().getCobrandSessionObj());
			commonUtils.assertStatusCode(getProviderAccountDetails, status);
			System.out.println("STATUS" + getProviderAccountDetails.jsonPath().getString("providerAccount.refreshInfo.status"));
			Assert.assertEquals("SUCCESS", getProviderAccountDetails.jsonPath().getString("providerAccount.refreshInfo.status"));
		}
	}

	/*
	 * Bug- 891183 Test Case ID -AT-48001
	 * 
	 */
	

	@Test(dataProvider = "feeder", enabled = true, priority = 4, dependsOnMethods = "addAccountForTransactonStatement")
	@Info(subfeature = { SubFeature.GET_TRANSACTIONS }, userStoryId = "")
	@Source(TRANSACTION_GET_TRANS_CARD)
	public void teacaGetTransactionStatement(String testCaseId, String testCaseName, String parameter, String value,
			int status, String filePath, String fileName, String enabled, int verifyCount, String DefectID) throws InterruptedException {

		System.out.println("teacaGetTransactionStatement Started.....");
		CommonUtils commonUtils = new CommonUtils();
		TransactionUtils transactionUtils = new TransactionUtils();
		ProviderAccountUtils providerAccountUtils = new ProviderAccountUtils();
		Configuration configuration = Configuration.getInstance();
		UserUtils userUtils = new UserUtils();
		commonUtils.isTCEnabled(enabled, testCaseId);

		// verify the transaction count
		Map<String, String> params = new HashMap<String, String>();
		params.put("fromDate", "2006-01-01");
		Response response;
		HttpMethodParameters httpQuery = HttpMethodParameters.builder().build();
		httpQuery.setQueryParams(params);
		response = transactionUtils.getTransactionCount(httpQuery, configuration.getCobrandSessionObj());

		System.out.println("The total count " + testCaseId + " " + " testCaseName "+ response.jsonPath().getLong("transaction.TOTAL.count"));
		Assert.assertEquals(4, response.jsonPath().getLong("transaction.TOTAL.count"));
		// delete the account
		Map<String, Object>  mapParam = new HashMap<>();
		mapParam.put("providerAccountId", providerAccountId1);
		HttpMethodParameters httpParams = HttpMethodParameters.builder().build();
		httpParams.setPathParams(mapParam);
		response = providerAccountUtils.deleteProviderAccount(httpParams, configuration.getCobrandSessionObj());
		System.out.println(response.statusCode());
		Assert.assertEquals(204, response.statusCode());

		// un -register the user
		System.out.println("Un Regisger the User");
		response = userUtils.unRegisterUser(configuration.getCobrandSessionObj());
		System.out.println("After user unregister response code " + response.statusCode());
		Assert.assertEquals(204, response.statusCode());
		System.out.println("After the user unresiter no content to dipaly" + response.prettyPrint());
		System.out.println("Un Regisger of user is done");
	}

	@AfterClass
	public void smokeTestdeleteAccount() {

		ProviderAccountUtils providerAccountUtils = new ProviderAccountUtils();
		Configuration configuration = Configuration.getInstance();
		Map<String, Object>  mapParam = new HashMap<>();
		mapParam.put("providerAccountId", providerAccountId1);
		HttpMethodParameters httpParams = HttpMethodParameters.builder().build();
		httpParams.setPathParams(mapParam);
		Response response = providerAccountUtils.deleteProviderAccount(httpParams, configuration.getCobrandSessionObj());
		System.out.println(response.statusCode());
		Assert.assertEquals(204, response.statusCode());

	}
}
