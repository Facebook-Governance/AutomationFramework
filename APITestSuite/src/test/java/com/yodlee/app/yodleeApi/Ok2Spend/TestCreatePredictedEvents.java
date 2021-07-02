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
import com.yodlee.yodleeApi.utils.apiUtils.PredictedUtils;
import com.yodlee.yodleeApi.utils.apiUtils.ProviderAccountUtils;
import com.yodlee.yodleeApi.utils.apiUtils.UserUtils;
import com.yodlee.yodleeApi.utils.commonUtils.CommonUtils;
import com.yodlee.yodleeApi.common.Base;
import io.restassured.response.Response;

/**
* @author Mahadev A N
*
*/
public class TestCreatePredictedEvents extends Base {

	private static Long providerAccountId = null;
	private EnvSession sessionObj = null;
	private Configuration configurationObj = Configuration.getInstance();
	private UserUtils userUtils = new UserUtils();
	private ProviderAccountUtils providerAccountUtils = new ProviderAccountUtils();
	private CommonUtils commonUtils = new CommonUtils();
	private AccountUtils accountUtils = new AccountUtils();
	private AccountsHelper accountsHelper = new AccountsHelper();
	private UserHelper userHelper = new UserHelper();
	private JsonAssertionUtil jsonAssertionUtil = new JsonAssertionUtil();
	private PredictedUtils predictedUtils = new PredictedUtils();
	private SenseTransactionHelper senseTransactionsHelper = new SenseTransactionHelper();

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
				"getrecTrans.site16441.2", "site16441.2", sessionObj);
		providerAccountId = response.jsonPath().getLong(JSONPaths.PROVIDER_ACC_ID);
		System.out.println("providerAccountId1::::===" + providerAccountId);
	}

	/**
	 * Negative scenarios for POST /recurringEvents API
	 *
	 * @param TestCaseId
	 * @param TestCase
	 * @param amount
	 * @param currency
	 * @param consumer
	 * @param baseType
	 * @param categoryId
	 * @param categorySource
	 * @param startDate
	 * @param memo
	 * @param accountId
	 * @param checkNumber
	 * @param frequency
	 * @param endDate
	 * @param httpStatusCode
	 * @param resFile
	 * @param resFilePath
	 * @param Enabled
	 * @throws Exception
	 */
	@Test(enabled = true, dataProvider = "feeder")
	@Source("\\TestData\\CSVFiles\\Ok2Spend_EndPoint\\CreateRecurringEventsNegative.csv")
	public void testCreatRecurringEventNegativeScenario(String TestCaseId, String TestCase, Double amount,
			String currency, String consumer, String baseType, int categoryId, String categorySource, String startDate,
			String memo, String accountId, String checkNumber, String frequency, String endDate, String transactionType,
			int httpStatusCode, String resFile, String resFilePath, String Enabled) throws Exception {
		commonUtils.isTCEnabled(Enabled, TestCaseId);
		System.out.println("TestCaseTestCase::"+TestCase);
		Long accountIds = accountsHelper.getAccountId(accountId, sessionObj);
		String crr = senseTransactionsHelper.createPredictedEventobject(amount, currency, consumer,
				baseType, categoryId, categorySource, startDate, memo, accountIds.toString(), checkNumber, frequency, endDate,
				 "DEPOSIT");
		
		JsonParser jsonParser = new JsonParser();
		JsonObject json = (JsonObject) jsonParser.parse(crr);
		JsonObject jsonObj = json.getAsJsonObject("predictedEvent");
		if (TestCase.equalsIgnoreCase("InvalidAlphabetValforCategoryId")){
			jsonObj.remove("categoryId");
			jsonObj.addProperty("categoryId", "ABC");
			JsonObject request = new JsonObject();
			request.add("predictedEvent", jsonObj);
			crr = request.toString();
		}
		else if (TestCase.equalsIgnoreCase("InvalidAmountAsString")) {
			jsonObj.getAsJsonObject("amount").remove("amount");
			jsonObj.getAsJsonObject("amount").addProperty("amount", "Alphabet");
			JsonObject request = new JsonObject();
			request.add("predictedEvent", jsonObj);
			crr = request.toString();
		}

		else if (TestCase.equalsIgnoreCase("Invalidjsonin")) {
			jsonObj.getAsJsonObject("amount").remove("amount");
			jsonObj.getAsJsonObject("amount").addProperty("amount", "300");
			JsonObject request = new JsonObject();
			request.add("predictedE", jsonObj);
			crr = request.toString();
		}
		else if (TestCase.equalsIgnoreCase("InvalidTransactionTypeBuy")
				||TestCase.equalsIgnoreCase("InvalidTransactionType_Invalid")){
			jsonObj.remove("transactionType");
			jsonObj.addProperty("transactionType", "xyz");
			JsonObject request = new JsonObject();
			request.add("predictedEvent", jsonObj);
			crr = request.toString();
		}	

		HttpMethodParameters httpParams = HttpMethodParameters.builder().build();
		httpParams.setBodyParams(crr);

		Response response = predictedUtils.createPredictedEvents(httpParams, sessionObj);
		jsonAssertionUtil.assertJSON(response, httpStatusCode, resFile, resFilePath);
	}

	/**
	 * Positive scenarios for Create Recurring Event
	 *
	 * @param TestCaseId
	 * @param TestCase
	 * @param amount
	 * @param currency
	 * @param consumer
	 * @param baseType
	 * @param categoryId
	 * @param categorySource
	 * @param startDate
	 * @param memo
	 * @param accountId
	 * @param checkNumber
	 * @param frequency
	 * @param endDate
	 * @param httpStatusCode
	 * @param resFile
	 * @param resFilePath
	 * @param Enabled
	 * @throws Exception
	 */
	@Test(enabled = true, dataProvider = "feeder")
	@Source("\\TestData\\CSVFiles\\Ok2Spend_EndPoint\\CreateRecurringEventsPositive.csv")
	public void testCreateRecurringEventPositive(String TestCaseId, String TestCase, Double amount, String currency,
			String consumer, String baseType, int categoryId, String categorySource, String startDate, String memo,
			String accountId, String checkNumber, String frequency, String endDate, int httpStatusCode, String resFile,
			String resFilePath, String Enabled) throws Exception {
		commonUtils.isTCEnabled(Enabled, TestCaseId);
		System.out.println("Test Case Id is " + TestCaseId + " test case is " + TestCase);
		Long accountIds = accountsHelper.getAccountId(accountId, sessionObj);
		String crr = senseTransactionsHelper.createPredictedEventobject(amount, currency, consumer,
				baseType, categoryId, categorySource, startDate, memo, accountIds.toString(), checkNumber, frequency, endDate,
				 "DEPOSIT");
		HttpMethodParameters httpParams = HttpMethodParameters.builder().build();
		httpParams.setBodyParams(crr);
		Response response = predictedUtils.createPredictedEvents(httpParams, sessionObj);
		jsonAssertionUtil.assertJSON(response, httpStatusCode, resFile, resFilePath);
	}

	/**
	 * Scenarios for POST /predictedEvent when refTransactionId is passed
	 */
	@Test(enabled = true, dataProvider = "feeder")
	@Source("\\TestData\\CSVFiles\\Ok2Spend_EndPoint\\CreateRecurringEventsWithRefId.csv")
	public void testCreateRecurringEventWithRefId(String TestCaseId, String TestCase, String refTransactionId,
			String startDate, String accountId, String frequency, String endDate, int httpStatusCode, String resFile,
			String resFilePath, String Enabled, String sDate, String eDate) throws Exception {
		commonUtils.isTCEnabled(Enabled, TestCaseId);
		System.out.println("Test Case Id is " + TestCaseId + " test case is " + TestCase);

		if(TestCase.contains("RefIdOfOtherAccount") && (TestCaseId.contains("AT-115274")||TestCaseId.contains("AT-115275"))){
			Long accountIds = accountsHelper.getAccountId(accountId, sessionObj);
			Long refTransactionIds = accountsHelper.getAccountId(refTransactionId, sessionObj);

			Long transactionId = senseTransactionsHelper.getAggregatedTransactionId(sessionObj, refTransactionIds.toString(), refTransactionId, sDate, eDate);
			String createRefObject = senseTransactionsHelper.createPredictedEventForRefId(accountIds.toString(), transactionId.toString(),endDate, startDate,frequency);
			HttpMethodParameters httpParams = HttpMethodParameters.builder().build();
			httpParams.setBodyParams(createRefObject);
			Response response = predictedUtils.createPredictedEvents(httpParams, sessionObj);
			jsonAssertionUtil.assertJSON(response, httpStatusCode, resFile, resFilePath);
		}else if(TestCase.contains("CardContainer") && (TestCaseId.contains("AT-115262,AT-115263")|| TestCaseId.contains("AT-115280"))){ 
			Long accountIds = accountsHelper.getAccountId(accountId, sessionObj);
			Long transactionId = senseTransactionsHelper.getAggregatedTransactionId(sessionObj, accountIds.toString(), refTransactionId, sDate, eDate);
			String createRefObject = senseTransactionsHelper.createPredictedEventForRefId(accountIds.toString(), transactionId.toString(),endDate, startDate,frequency);
			HttpMethodParameters httpParams = HttpMethodParameters.builder().build();
			httpParams.setBodyParams(createRefObject);
			Response response = predictedUtils.createPredictedEvents(httpParams, sessionObj);
			jsonAssertionUtil.assertJSON(response, httpStatusCode, resFile, resFilePath);
		}else {
			Long accountIds = accountsHelper.getAccountId(accountId, sessionObj);
			Long transactionId = senseTransactionsHelper.getAggregatedTransactionId(sessionObj, accountIds.toString(), refTransactionId, sDate, eDate);
			String createRefObject = senseTransactionsHelper.createPredictedEventForRefId(accountIds.toString(), transactionId.toString(),endDate, startDate,frequency);
			HttpMethodParameters httpParams = HttpMethodParameters.builder().build();
			httpParams.setBodyParams(createRefObject);
			Response response = predictedUtils.createPredictedEvents(httpParams, sessionObj);
			jsonAssertionUtil.assertJSON(response, httpStatusCode, resFile, resFilePath);
		}
	}

	@Test(enabled = false, dataProvider = "feeder",priority = 4)
	@Source("\\TestData\\CSVFiles\\Ok2Spend_EndPoint\\CreateRecurringEventsAccountStatus.csv")
	public void testCreateRecurringEventAccountStatus(String TestCaseId, String TestCase, Double amount,
			String currency, String consumer, String baseType, int categoryId, String categorySource, String startDate,
			String memo, String container, String checkNumber, String frequency, String endDate, String transactionType,
			String accountStatus, int httpStatusCode, String resFile, String resFilePath, String Enabled)
			throws Exception {
		commonUtils.isTCEnabled(Enabled, TestCaseId);
		Long accountId = accountsHelper.getAccountId(container, sessionObj);

		String crr = senseTransactionsHelper.createPredictedEventobject(amount, currency, consumer,
				baseType, categoryId, categorySource, startDate, memo, container, checkNumber, frequency, endDate,
				transactionType);
		if (accountStatus.equalsIgnoreCase("DELETED")) {
			HttpMethodParameters createParams = accountsHelper.createPathParam("accountId",accountId.toString());
			accountUtils.deleteAccount(createParams, sessionObj);
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
		queryParam.put("container", container);

		Map<String, Object> pathParams = new HashMap<>();
		pathParams.put("accountId", accountId.toString());

		HttpMethodParameters httpMethodParameters = HttpMethodParameters.builder().build();
		httpMethodParameters.setBodyParams(bodyParam.toString());
		httpMethodParameters.setQueryParams(queryParam);
		httpMethodParameters.setPathParams(pathParams);

		accountUtils.updateManualOrAggregateAccount(httpMethodParameters, sessionObj);

		HttpMethodParameters httpParams = HttpMethodParameters.builder().build();
		httpParams.setBodyParams(crr);
		Response createResponse = predictedUtils.createPredictedEvents(httpParams, sessionObj);

		if (accountStatus.equalsIgnoreCase("DELETED")) {
			HttpMethodParameters createParams = accountsHelper.createPathParam("accountId",
					accountId.toString());
			accountUtils.deleteAccount(createParams, sessionObj);
		}

		JSONObject bodyParams = new JSONObject();
		JSONObject accounts = new JSONObject();
		try {
			account.put("accountStatus", "ACTIVE");
			bodyParams.put("account", accounts);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		LinkedHashMap<String, String> queryParams = new LinkedHashMap<String, String>();
		queryParams.put("container", container);

		Map<String, Object> pathParam = new HashMap<>();
		pathParam.put("accountId", accountId.toString());

		HttpMethodParameters updateParam = HttpMethodParameters.builder().build();
		updateParam.setBodyParams(bodyParams.toString());
		updateParam.setQueryParams(queryParam);
		updateParam.setPathParams(pathParams);

		accountUtils.updateManualOrAggregateAccount(updateParam, sessionObj);
		jsonAssertionUtil.assertJSON(createResponse, httpStatusCode, resFile, resFilePath);
	}
	@AfterClass(alwaysRun = true)
	public void unRegisteredUser() {
		userUtils.unRegisterUser(sessionObj);

	}
}

