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
package com.yodlee.app.yodleeApi.Ok2Spend;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import org.databene.benerator.anno.Source;
import org.json.JSONException;
import org.json.JSONObject;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.yodlee.yodleeApi.assertions.JsonAssertionUtil;
import com.yodlee.yodleeApi.common.Base;
import com.yodlee.yodleeApi.common.Configuration;
import com.yodlee.yodleeApi.constants.JSONPaths;
import com.yodlee.yodleeApi.helper.AccountsHelper;
import com.yodlee.yodleeApi.helper.SenseTransactionHelper;
import com.yodlee.yodleeApi.helper.TransactionsHelper;
import com.yodlee.yodleeApi.helper.UserHelper;
import com.yodlee.yodleeApi.pojo.EnvSession;
import com.yodlee.yodleeApi.pojo.HttpMethodParameters;
import com.yodlee.yodleeApi.pojo.User;
import com.yodlee.yodleeApi.utils.apiUtils.AccountUtils;
import com.yodlee.yodleeApi.utils.apiUtils.ManualTransactionUtils;
import com.yodlee.yodleeApi.utils.apiUtils.PredictedUtils;
import com.yodlee.yodleeApi.utils.apiUtils.ProviderAccountUtils;
import com.yodlee.yodleeApi.utils.apiUtils.UserUtils;
import com.yodlee.yodleeApi.utils.commonUtils.CommonUtils;

import io.restassured.response.Response;

/**
 * @author Mahadev
 *
 */
public class TestCreateManualTransaction extends Base {

	private long providerAccountId;
	private EnvSession sessionObj = null;
	private CommonUtils commonUtils = new CommonUtils();
	private Configuration configurationObj = Configuration.getInstance();
	private UserUtils userUtils = new UserUtils();
	private SenseTransactionHelper senseTransactionsHelper = new SenseTransactionHelper();
	private TransactionsHelper tansactionsHelper = new TransactionsHelper();
	private JsonAssertionUtil jsonAssertionUtil = new JsonAssertionUtil();
	private ProviderAccountUtils providerAccountUtils = new ProviderAccountUtils();
	private AccountUtils accountUtils = new AccountUtils();
	private ManualTransactionUtils manualTransactionUtils = new ManualTransactionUtils();
	private AccountsHelper accountsHelper = new AccountsHelper();
	private UserHelper userHelper = new UserHelper();

	@BeforeClass(alwaysRun = true)
	public void AddAccount() throws Exception {
		User userInfo = User.builder().build();;
		String username = "YSLSense" + System.currentTimeMillis();
		userInfo.setUsername(username);
		userInfo.setPassword("Test@123");
		sessionObj = EnvSession.builder().cobSession(configurationObj.getCobrandSessionObj().getCobSession())
				.path(configurationObj.getCobrandSessionObj().getPath()).build();
		userHelper.getUserSession(userInfo, sessionObj);

		long providerId = 16441;
		Response response = providerAccountUtils.addProviderAccountStrict(providerId, "fieldarray",
				"gerRecNew.site16441.1", "site16441.1", sessionObj);
		providerAccountId = response.jsonPath().getLong(JSONPaths.PROVIDER_ACC_ID);
		System.out.println("providerAccountId1::::===" + providerAccountId);
	}
	@Test(enabled = true, dataProvider = "feeder")
	@Source("\\TestData\\CSVFiles\\Ok2Spend_EndPoint\\CreateManualTxnNegativescenario.csv")
	public void testCreateManualTxnNegativeScenario(String testCaseId, String TestCase, Double amount, String currency,
			String consumer, String baseType, int category, String categorySource, String date, String memo,
			String accountId, String checkNumber, String transactionType, int httpStatus, String ResFile,
			String FilePath, String Enabled) throws Exception {

		Long accountIdString = accountsHelper.getAccountId(accountId, sessionObj);
		commonUtils.isTCEnabled(Enabled, testCaseId);
		String ctrString = senseTransactionsHelper.createManualTxnObject(amount, currency,
				consumer, baseType, category, categorySource, date, memo, accountIdString.toString(), checkNumber, transactionType);
		JsonParser jsonParser = new JsonParser();
		JsonObject jo = (JsonObject) jsonParser.parse(ctrString);
		JsonObject jsonObj = jo.getAsJsonObject("manualTransaction");

		if (TestCase.equalsIgnoreCase("character")) {
			jsonObj.remove("categoryId");
			jsonObj.addProperty("categoryId", "ABC");
			JsonObject request = new JsonObject();
			request.add("manualTransaction", jsonObj);
			ctrString = request.toString();
		}

		if (TestCase.equalsIgnoreCase("amountasastring_sell")) {
			jsonObj.getAsJsonObject("amount").remove("amount");
			jsonObj.getAsJsonObject("amount").addProperty("amount", "Alphabet");
			JsonObject request = new JsonObject();
			request.add("manualTransaction", jsonObj);
			ctrString = request.toString();
		}

		if (TestCase.equalsIgnoreCase("Invalidjson")) {
			jsonObj.getAsJsonObject("amount").remove("amount");
			jsonObj.getAsJsonObject("amount").addProperty("amount", 1236);
			JsonObject request = new JsonObject();
			request.add("manualTransaction", jsonObj);
			ctrString = request.toString();
		}

		if (TestCase.equalsIgnoreCase("referenceTransactionid_manual")) {
			jsonObj.addProperty("refTransactionId", "99999");
			JsonObject request = new JsonObject();
			request.add("manualTransaction", jsonObj);
			ctrString = request.toString();
		}
		HttpMethodParameters bodyParam = HttpMethodParameters.builder().build();
		bodyParam.setBodyParams(ctrString);
		Response response =	manualTransactionUtils.createManualTransaction(bodyParam, sessionObj);
		jsonAssertionUtil.assertJSON(response, httpStatus, ResFile, FilePath);
	}

	@Test(enabled = true, dataProvider = "feeder")
	@Source("\\TestData\\CSVFiles\\Ok2Spend_EndPoint\\CreateManualTxnPositive.csv")
	public void testCreateManualTxnPositive(String TestCaseId, String TestCase, Double amount, String currency,
			String consumer, String baseType, int categoryId, String categorySource, String date, String memo,
			String accountId, String checkNumber, String transactionType, int httpStatusCode, String ResFile,
			String FilePath, String Enabled) throws Exception {
		commonUtils.isTCEnabled(Enabled, TestCaseId);
		Long accountIdString = accountsHelper.getAccountId(accountId, sessionObj);
		
		if(categorySource.contains("USER")){
			categoryId = tansactionsHelper.createAndGetUserCategory(sessionObj,"business amount","29");
		}
		String createTransactionRequest = senseTransactionsHelper.createManualTxnObject(amount, currency,
				consumer, baseType, categoryId, categorySource, date, memo, accountIdString.toString(), checkNumber, transactionType);
		HttpMethodParameters httpParams = HttpMethodParameters.builder().build();
		httpParams.setBodyParams(createTransactionRequest);
		System.out.println("createTransactionRequest::"+createTransactionRequest);

		Response response = manualTransactionUtils.createManualTransaction(httpParams, sessionObj);
		jsonAssertionUtil.assertJSON(response, httpStatusCode, ResFile, FilePath);
	}

/*	@Test(enabled = true, dataProvider = "feeder")
	@Source("\\TestData\\CSVFiles\\Ok2Spend_EndPoint\\CreateManualTxnWithRefId.csv")
	public void testCreateManualTxnWithRefId(String TestCaseId, String TestCase, String startDate, String frequency,
			String endDate, String referenceId, String accountId, int httpStatusCode, String ResFile, String FilePath,
			String Enabled) throws Exception {
		commonUtils.isTCEnabled(Enabled, TestCaseId);
		CreateRecurringEventRequest ctr = transactionsHelper.createREWithRefId(accountId, referenceId, frequency,
				startDate, endDate, sessionObj);

		HttpMethodParameters httpParams = HttpMethodParameters.builder().build();
		httpParams.setBodyParams(ctr.getJsonString());

		Response response = transactionUtils.createManualTxns(httpParams, sessionObj);
		commonUtils.assertStatusCode(response, httpStatusCode);
	}
*/
	@Test(enabled = true, dataProvider = "feeder", priority=4)
	@Source("\\TestData\\CSVFiles\\Ok2Spend_EndPoint\\CreateManualTxnAccountStatus.csv")
	public void testCreateManualTxnAccountStatus(String TestCaseId, String TestCase, Double amount, String currency,
			String consumer, String baseType, int categoryId, String categorySource, String date, String memo,
			String accountId, String checkNumber, String transactionType, String accountStatus, int httpStatusCode,
			String ResFile, String FilePath, String Enabled) throws Exception {
		commonUtils.isTCEnabled(Enabled, TestCaseId);
		accountUtils.getAllAccounts(sessionObj);
		Long accountIdString = accountsHelper.getAccountId(accountId, sessionObj);
		String createTransactionsRequest = senseTransactionsHelper.createManualTxnObject(amount, currency,
				consumer, baseType, categoryId, categorySource, date, memo, accountId, checkNumber, transactionType);
		if (accountStatus.equalsIgnoreCase("DELETED")) {
			HttpMethodParameters httpMethodParameter = HttpMethodParameters.builder().build();
			httpMethodParameter.setBodyParams(accountIdString.toString());
			accountUtils.deleteAccount(httpMethodParameter, sessionObj);
		}
		JSONObject bodyParam = new JSONObject();
		JSONObject account = new JSONObject();
		try {
			account.put("accountStatus", accountStatus);
			bodyParam.put("account", account);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		LinkedHashMap<String, String> queryParam = new LinkedHashMap<String, String>();
		queryParam.put("container", accountId);

		Map<String, Object> pathParams = new HashMap<>();
		pathParams.put("accountId", accountIdString.toString());

		HttpMethodParameters httpParam = HttpMethodParameters.builder().build();
		httpParam.setQueryParams(queryParam);
		httpParam.setBodyParams(bodyParam.toString());
		httpParam.setPathParams(pathParams);

		accountUtils.updateManualOrAggregateAccount(httpParam, sessionObj);

		HttpMethodParameters httpBodyParam = HttpMethodParameters.builder().build();
		httpBodyParam.setBodyParams(createTransactionsRequest);
		Response response =  manualTransactionUtils.createManualTransaction(httpBodyParam, sessionObj);

		if (accountStatus.equalsIgnoreCase("DELETED")) {
			HttpMethodParameters createParams = accountsHelper.createPathParam("accountId",
					accountIdString.toString());
			accountUtils.deleteAccount(createParams, sessionObj);
		}

		JSONObject bodyParams = new JSONObject();
		try {
			account.put("accountStatus", "ACTIVE");
			bodyParams.put("account", account);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		LinkedHashMap<String, String> queryParameter = new LinkedHashMap<String, String>();
		queryParameter.put("container", accountId);

		Map<String, Object> pathParam = new HashMap<>();
		pathParam.put("accountId", accountIdString.toString());

		HttpMethodParameters updateParam = HttpMethodParameters.builder().build();
		updateParam.setBodyParams(bodyParams.toString());
		updateParam.setQueryParams(queryParameter);
		updateParam.setPathParams(pathParam);

		accountUtils.updateManualOrAggregateAccount(updateParam, sessionObj);
		jsonAssertionUtil.assertJSON(response, httpStatusCode, ResFile, FilePath);
	}

	@AfterClass(alwaysRun = true)
	public void unRegisteredUser() {
		userUtils.unRegisterUser(sessionObj);

	}
}
