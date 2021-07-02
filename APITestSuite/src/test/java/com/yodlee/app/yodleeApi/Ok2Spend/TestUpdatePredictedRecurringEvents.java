/*******************************************************************************
*
* * Copyright (c) 2018 Yodlee, Inc. All Rights Reserved.
*
* *
*
* * This software is the confidential and proprietary information of Yodlee, Inc.
*
* * Use is subject to license terms.
*
*******************************************************************************/

package com.yodlee.app.yodleeApi.Ok2Spend;

import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import org.databene.benerator.anno.Source;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import com.yodlee.yodleeApi.assertions.JsonAssertionUtil;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.yodlee.yodleeApi.common.Base;
import com.yodlee.yodleeApi.common.Configuration;
import com.yodlee.yodleeApi.constants.JSONPaths;
import com.yodlee.yodleeApi.helper.AccountsHelper;
import com.yodlee.yodleeApi.helper.SenseTransactionHelper;
import com.yodlee.yodleeApi.helper.UserHelper;
import com.yodlee.yodleeApi.pojo.EnvSession;
import com.yodlee.yodleeApi.pojo.HttpMethodParameters;
import com.yodlee.yodleeApi.pojo.User;
import com.yodlee.yodleeApi.utils.apiUtils.AccountUtils;
import com.yodlee.yodleeApi.utils.apiUtils.PredictedUtils;
import com.yodlee.yodleeApi.utils.apiUtils.ProviderAccountUtils;
import com.yodlee.yodleeApi.utils.apiUtils.UserUtils;
import com.yodlee.yodleeApi.utils.commonUtils.CommonUtils;
import io.restassured.response.Response;

/**
* @author Mahadev A N
*
*/
public class TestUpdatePredictedRecurringEvents extends Base {

	private long providerAccountId;
	private Configuration configurationObj = Configuration.getInstance();
	private ProviderAccountUtils providerAccountUtils = new ProviderAccountUtils();
	private UserUtils userUtils = new UserUtils();
	private UserHelper userHelper = new UserHelper();
	private CommonUtils commonUtils = new CommonUtils();
	private AccountUtils accountUtils = new AccountUtils();
	private AccountsHelper accountsHelper = new AccountsHelper();
	private SenseTransactionHelper senseTransactionsHelper = new SenseTransactionHelper();
	private PredictedUtils predictedUtils = new PredictedUtils();
	private JsonAssertionUtil jsonAssertionUtil = new JsonAssertionUtil();
	private EnvSession sessionObj = null;

	@BeforeClass(alwaysRun = true)
	public void AddAccount() throws JSONException {
		User userInfo = User.builder().build();
		userInfo.setUsername("YSLSense" + System.currentTimeMillis());
		userInfo.setPassword("Test@123");
		sessionObj = EnvSession.builder().cobSession(configurationObj.getCobrandSessionObj().getCobSession())
				.path(configurationObj.getCobrandSessionObj().getPath()).build();
		userHelper.getUserSession(userInfo, sessionObj);

		long providerId = 16441;
		Response response = providerAccountUtils.addProviderAccountStrict(providerId, "fieldarray",
				"gerRecNew.site16441.1", "site16441.1", sessionObj);
		providerAccountId = response.jsonPath().getLong(JSONPaths.PROVIDER_ACC_ID);
		System.out.println("provider acct id:" + providerAccountId);
	}

	@Test(enabled = true, dataProvider = "feeder", priority = 1)
	@Source("\\TestData\\CSVFiles\\Ok2Spend_EndPoint\\UpdateRecurringEventsNegative.csv")
	public void testUpdateRecurringNegativeScenario(String TestCaseId, String TestCase, Double amount, String currency,
			String consumer, String baseType, int categoryId, String categorySource, String startDate, String memo,
			String accountId, String checkNumber, String frequency, String endDate, String transactionType,
			String predictedEventStatus, String userApprovalStatus, int httpStatusCode, String resFile,
			String resFilePath, String Enabled) throws JSONException, InterruptedException, IOException {
		commonUtils.isTCEnabled(Enabled, TestCaseId);
		JsonParser jsonParser = new JsonParser();
		Long accountIds = accountsHelper.getAccountId("bank", sessionObj);
		String crr = senseTransactionsHelper.createPredictedEventobject(100.00, "USD", "consumer",
				"CREDIT", 29, "SYSTEM", "0,0,1", "memo", accountIds.toString(), "1234", "WEEKLY", "0,1,0", "DEPOSIT");
		
		HttpMethodParameters httpParams = HttpMethodParameters.builder().build();
		httpParams.setBodyParams(crr);

		Response createResponse = predictedUtils.createPredictedEvents(httpParams, sessionObj);

		if (!createResponse.asString().contains("predictedEvent")) {
			Assert.fail("Create predicted events failed\n" + crr);
		}
		String urrString = senseTransactionsHelper.updatePredictedEventobject(amount, currency, consumer,
				baseType, categoryId, categorySource, startDate, memo, accountId, checkNumber, frequency, endDate,
				transactionType, predictedEventStatus, userApprovalStatus);
		
		
		JSONObject json = new JSONObject(createResponse.asString().toString());
		String predictedEventId = json.getJSONObject("predictedEvents").get("Id").toString();
		System.out.println("predictedEventId::"+predictedEventId);

		//ur.setRecurringEventId(id);
		JsonObject jo = (JsonObject) jsonParser.parse(urrString);
		JsonObject jsonObj = jo.getAsJsonObject("predictedEvent");
		if (TestCaseId.equalsIgnoreCase("AT-115386")) {
			jsonObj.remove("categoryId");
			jsonObj.addProperty("categoryId", "ABC");
			JsonObject request = new JsonObject();
			JsonObject account = new JsonObject();
			account.add("predictedEvent", jsonObj);
			urrString = request.toString();
		}
		if (TestCaseId.equalsIgnoreCase("AT-115388")) {
			jsonObj.getAsJsonObject("amount").remove("amount");
			jsonObj.getAsJsonObject("amount").addProperty("amount", "Alphabet");
			JsonObject request = new JsonObject();
			JsonObject account = new JsonObject();
			account.add("predictedEvent", jsonObj);
			urrString = request.toString();
		}
		if (TestCaseId.equalsIgnoreCase("AT-115385")) {
			predictedEventId="12345";
		}
		
		HttpMethodParameters httpMethodParameters = HttpMethodParameters.builder().build();
		httpMethodParameters.setBodyParams(urrString);
		Map<String, Object> pathParams = new HashMap<String, Object>();
		pathParams.put("predictedEventId", predictedEventId);
		httpMethodParameters.setPathParams(pathParams);
		
		Response updateResponse =predictedUtils.updatePredictedEvents(httpMethodParameters, sessionObj);
		System.out.println("updateResponse::"+updateResponse);
		jsonAssertionUtil.assertJSON(updateResponse, httpStatusCode, resFile, resFilePath);
	}

	@Test(enabled = true, dataProvider = "feeder", priority = 2)
	@Source("\\TestData\\CSVFiles\\Ok2Spend_EndPoint\\UpdateRecurringEventsNegativeSystem.csv")
	public void testUpdateRecurringNegativeSystem(String TestCaseId, String TestCase, Double amount, String currency,
			String consumer, String baseType, int categoryId, String categorySource, String startDate, String memo,
			String accountId, String checkNumber, String frequency, String endDate, String transactionType,
			String predictedEventStatus, String userApprovalStatus, int httpStatusCode, String resFile,
			String resFilePath, String Enabled) throws Exception {
		commonUtils.isTCEnabled(Enabled, TestCaseId);
		System.out.println("TestCase::::"+TestCase);
		Long account = accountsHelper.getAccountId(accountId, sessionObj);

		HashMap<String, String> queryParams = new HashMap<String, String>();
		queryParams.put("accountId", account.toString());
		queryParams.put("sourceType", "SYSTEM_PREDICTED");

		HttpMethodParameters httpParams = HttpMethodParameters.builder().build();
		httpParams.setQueryParams(queryParams);

		Response eventResponse = predictedUtils.getPredictedEvents(httpParams, sessionObj);
		System.out.println("eventResponse::"+eventResponse.asString());
		String predictedEventId = senseTransactionsHelper.getRecurringEventId(eventResponse, "VALID");
		System.out.println("predictedEventIdpredictedEventId::"+predictedEventId);

		String urrString = senseTransactionsHelper.updatePredictedEventobject(amount, currency, consumer,
				baseType, categoryId, categorySource, startDate, memo, account.toString(), checkNumber, frequency, endDate,
				transactionType, predictedEventStatus, userApprovalStatus);

		if(TestCase.contains("Invalid json")){
			urrString = urrString.replace("predictedEvent", "recurringEvent");
		}
		if(TestCase.contains("invalid_id in pathParameter")){
			predictedEventId="4444444";
		}
		HttpMethodParameters httpMethodParameters = HttpMethodParameters.builder().build();
		httpMethodParameters.setBodyParams(urrString);
		Map<String, Object> pathParams = new HashMap<String, Object>();
		pathParams.put("predictedEventId", predictedEventId);
		httpMethodParameters.setPathParams(pathParams);
		Response updateResponse = predictedUtils.updatePredictedEvents(httpMethodParameters, sessionObj);
		commonUtils.assertStatusCode(updateResponse, httpStatusCode);
	}

	@Test(enabled = true, dataProvider = "feeder", priority = 3)
	@Source("\\TestData\\CSVFiles\\Ok2Spend_EndPoint\\UpdateRecurringEventsPositive1.csv")
	public void testUpdateREPositiveScenario(String TestCaseId, String TestCase, Double amount, String currency,
			String consumer, String baseType, int categoryId, String categorySource, String startDate, String memo,
			String accountId, String checkNumber, String frequency, String endDate, String transactionType,
			String predictedEventStatus, String userApprovalStatus, int httpStatusCode, String Enabled)
			throws Exception {
		commonUtils.isTCEnabled(Enabled, TestCaseId);
		System.out.println("Test Case Id is " + TestCaseId + " test case is " + TestCase);
		Long accountIds = accountsHelper.getAccountId(accountId, sessionObj);

		String crr = senseTransactionsHelper.createPredictedEventobject(100.00, "USD", "consumer",
				"DEBIT", 92, "SYSTEM", "0,0,1", "memo", accountIds.toString(), "1234", "MONTHLY", "0,3,0", "DEPOSIT");
		
		
		HttpMethodParameters httpParams = HttpMethodParameters.builder().build();
		httpParams.setBodyParams(crr);

		Response createResponse = predictedUtils.createPredictedEvents(httpParams, sessionObj);
		
		String urrString = senseTransactionsHelper.updatePredictedEventobject(amount, currency, consumer,
				baseType, categoryId, categorySource, startDate, memo, accountIds.toString(), checkNumber, frequency, endDate,
				transactionType, predictedEventStatus, userApprovalStatus);
		
		if (!createResponse.asString().contains("predictedEvent")) {
			Assert.fail("Create recurring events failed\n" + crr);
		}
		
		JSONObject json = new JSONObject(createResponse.asString().toString());
		String predictedEventId = json.getJSONObject("predictedEvents").get("Id").toString();
		System.out.println("predictedEventId::"+predictedEventId);	
		
		Map<String, Object> pathParams = new HashMap<String, Object>();
		pathParams.put("predictedEvenId", predictedEventId);

		HttpMethodParameters httpMethodParameters = HttpMethodParameters.builder().build();
		httpMethodParameters.setBodyParams(urrString);
		httpMethodParameters.setPathParams(pathParams);

		Response updateResponse = predictedUtils.updatePredictedEvents(httpMethodParameters, sessionObj);
		commonUtils.assertStatusCode(updateResponse, httpStatusCode);
		//validateRecurringEvent(predictedEventId, accountId);
	}

	@Test(enabled = true, dataProvider = "feeder", priority = 4)
	@Source("\\TestData\\CSVFiles\\Ok2Spend_EndPoint\\URESystem.csv")
	public void testUpdateRecurringSystem(String TestCaseId, String TestCase, Double amount, String currency,
			String consumer, String baseType, int categoryId, String categorySource, String startDate, String memo,
			String accountId, String checkNumber, String frequency, String endDate, String transactionType,
			String predictedEventStatus, String userApprovalStatus, int httpStatusCode, String Enabled)
			throws Exception {
		commonUtils.isTCEnabled(Enabled, TestCaseId);
		Long account = accountsHelper.getAccountId(accountId, sessionObj);

		HashMap<String, String> queryParams = new HashMap<String, String>();
		queryParams.put("accountId", account.toString());
		queryParams.put("sourceType", "SYSTEM_PREDICTED");

		HttpMethodParameters httpParams = HttpMethodParameters.builder().build();
		httpParams.setQueryParams(queryParams);

		Response eventResponse = predictedUtils.getPredictedEvents(httpParams, sessionObj);
		System.out.println("eventResponse::"+eventResponse.asString());
		String predictedEventId = senseTransactionsHelper.getRecurringEventId(eventResponse, "VALID");
		System.out.println("predictedEventIdpredictedEventId::"+predictedEventId);

		String urrString = senseTransactionsHelper.updatePredictedEventobject(amount, currency, consumer,
				baseType, categoryId, categorySource, startDate, memo, account.toString(), checkNumber, frequency, endDate,
				transactionType, predictedEventStatus, userApprovalStatus);

		HttpMethodParameters httpMethodParameters = HttpMethodParameters.builder().build();
		httpMethodParameters.setBodyParams(urrString);
		Map<String, Object> pathParams = new HashMap<String, Object>();
		pathParams.put("predictedEventId", predictedEventId);
		httpMethodParameters.setPathParams(pathParams);
		Response updateResponse = predictedUtils.updatePredictedEvents(httpMethodParameters, sessionObj);
		commonUtils.assertStatusCode(updateResponse, httpStatusCode);
	}

	@Test(enabled = true, dataProvider = "feeder", priority = 5)
	@Source("\\TestData\\CSVFiles\\Ok2Spend_EndPoint\\UpdateRecurringEventsAccountStatus.csv")
	public void testUpdateRecurringAccountStatus(String TestCaseId, String TestCase, Double amount, String currency,
			String consumer, String baseType, int categoryId, String categorySource, String startDate, String memo,
			String container, String checkNumber, String frequency, String endDate, String transactionType,
			String accountStatus, int httpStatusCode, String resFile, String resFilePath, String Enabled)
			throws Exception {
		commonUtils.isTCEnabled(Enabled, TestCaseId);
		Long account = accountsHelper.getAccountId(container, sessionObj);

		String crr = senseTransactionsHelper.createPredictedEventobject(amount, currency, consumer,
				baseType, categoryId, categorySource, startDate, memo, account.toString(), checkNumber, frequency, endDate,
				transactionType);
	
		HttpMethodParameters httpParams = HttpMethodParameters.builder().build();
		httpParams.setBodyParams(crr);
		Response createResponse = predictedUtils.createPredictedEvents(httpParams, sessionObj);

		JSONObject json = new JSONObject(createResponse.asString().toString());
		String predictedEventId = json.getJSONObject("predictedEvents").get("Id").toString();
		System.out.println("predictedEventId::"+predictedEventId);
		
		String urrString = senseTransactionsHelper.updatePredictedEventobject(amount, currency, consumer,
				baseType, categoryId, categorySource, startDate, memo, account.toString(), checkNumber, frequency, endDate,
				transactionType, null, null);
		
		if (accountStatus.equalsIgnoreCase("DELETED")) {
			HttpMethodParameters createParams = accountsHelper.createPathParam("accountId",
					account.toString());
			accountUtils.deleteAccount(createParams, sessionObj);
		}

		JSONObject bodyParam = new JSONObject();
		JSONObject account1 = new JSONObject();
		try {
			account1.put("accountStatus", accountStatus);
			bodyParam.put("account", account1);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		LinkedHashMap<String, String> queryParam = new LinkedHashMap<String, String>();

		Map<String, Object> pathParams = new HashMap<>();
		pathParams.put("accountId", account.toString());

		HttpMethodParameters httpMethodParameters = HttpMethodParameters.builder().build();
		httpMethodParameters.setQueryParams(queryParam);
		httpMethodParameters.setPathParams(pathParams);

		accountUtils.updateManualOrAggregateAccount(httpMethodParameters, sessionObj);

		HttpMethodParameters httpMethodParameter = HttpMethodParameters.builder().build();
		httpMethodParameter.setBodyParams(predictedEventId);
		httpMethodParameter.setBodyParams(urrString);

		Response updateResponse = predictedUtils.updatePredictedEvents(httpMethodParameter, sessionObj);

		if (accountStatus.equalsIgnoreCase("DELETED")) {
			HttpMethodParameters createParams = accountsHelper.createPathParam("accountId",
					account.toString());
			accountUtils.deleteAccount(createParams, sessionObj);
		}

		JSONObject bodyParams = new JSONObject();
		JSONObject accounts = new JSONObject();
		try {
			account1.put("accountStatus", "ACTIVE");
			bodyParams.put("account", accounts);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		Map<String, Object> pathParam = new HashMap<>();
		pathParam.put("accountId", account.toString());

		HttpMethodParameters updateParam = HttpMethodParameters.builder().build();
		updateParam.setQueryParams(queryParam);
		updateParam.setPathParams(pathParams);

		accountUtils.updateManualOrAggregateAccount(updateParam, sessionObj);
		commonUtils.assertStatusCode(updateResponse, httpStatusCode);
	}

	@AfterClass(alwaysRun = true)
	public void unRegisteredUser() {
		userUtils.unRegisterUser(sessionObj);

	}
}
