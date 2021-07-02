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
import com.yodlee.yodleeApi.assertions.JsonAssertionUtil;
import com.yodlee.yodleeApi.common.Base;
import com.yodlee.yodleeApi.common.Configuration;
import com.yodlee.yodleeApi.constants.JSONPaths;
import com.yodlee.yodleeApi.helper.AccountsHelper;
import com.yodlee.yodleeApi.helper.CreateRecurringEventRequest;
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
import io.restassured.response.Response;

/**
* 
* YSL -Automation suite of DELETE /recurringEvents
* 
* @author Mahadev A N
*
*/
public class TestDeletePredictedRecurringEvents extends Base {

	private static Long providerAccountId = null;
	private EnvSession sessionObj = null;
	private UserUtils userUtils = new UserUtils();
	private Configuration configurationObj = Configuration.getInstance();
	private ProviderAccountUtils providerAccountUtils = new ProviderAccountUtils();
	private UserHelper userHelper = new UserHelper();
	private CommonUtils commonUtils = new CommonUtils();
	private TransactionsHelper transactionsHelper = new TransactionsHelper();
	private AccountUtils accountUtils = new AccountUtils();
	private JsonAssertionUtil jsonAssertionUtil = new JsonAssertionUtil();
	private AccountsHelper accountsHelper = new AccountsHelper();
	private PredictedUtils predictedUtils = new PredictedUtils();
	private SenseTransactionHelper senseTransactionHelper =  new SenseTransactionHelper();

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
		System.out.println("providerAccountId1::::===" + providerAccountId);
	}

	@Test(enabled = true, dataProvider = "feeder", priority = 2)
	@Source("\\TestData\\CSVFiles\\Ok2Spend_EndPoint\\DeletePredictedEvent.csv")
	public void deleteRecurringEvent(String tcid, String tcName, int httpStatusCode, String resFile, String resFilePath,
			String recurringEventId, String sourceType, String enabled) throws Exception {
		commonUtils.isTCEnabled(enabled, tcid);
		System.out.println("Test Case Id is " + tcid + " test case is " + tcName);

		CreateRecurringEventRequest crr = transactionsHelper.createRecurringTxnRequest(100.00, "USD", "consumer",
				"CREDIT", 29, "SYSTEM", "0,0,1", "memo", "bank", "1234", "WEEKLY", "0,1,0", "DEPOSIT", sessionObj);
		HttpMethodParameters httpMethodParameters = HttpMethodParameters.builder().build();
		httpMethodParameters.setBodyParams(crr.getJsonString());
		accountUtils.createRecurringEvents(httpMethodParameters, sessionObj);

		HashMap<String, String> queryParams = new HashMap<String, String>();
		queryParams.put("sourceType", sourceType);

		HttpMethodParameters httpParam = HttpMethodParameters.builder().build();
		httpParam.setQueryParams(queryParams);

		Response getRecurringResponse = predictedUtils.getPredictedEvents(httpParam, sessionObj);
		System.out.println("getRecurringResponse :::" + getRecurringResponse.asString());
		String id = senseTransactionHelper.getRecurringEventId(getRecurringResponse, recurringEventId);

		Map<String, Object> pathParam1 = new LinkedHashMap<String, Object>();
		HttpMethodParameters httpParam1 = HttpMethodParameters.builder().build();
		pathParam1.put("predictedEventId", id);
		httpParam1.setPathParams(pathParam1);
		Response deleteResponse = predictedUtils.deletePredictedEvents(httpParam1, sessionObj);
		jsonAssertionUtil.assertJSON(deleteResponse, httpStatusCode, resFile, resFilePath);
	}

	@Test(enabled = true, dataProvider = "feeder", priority = 3)
	@Source("\\TestData\\CSVFiles\\Ok2Spend_EndPoint\\DeleteRecurringEventDeleted.csv")
	public void deleteRecurringEventDeleted(String tcid, String tcName, int httpStatusCode, String resFile,
			String resFilePath, String recurringEventId, String sourceType, String enabled) throws Exception {
		commonUtils.isTCEnabled(enabled, tcid);
		System.out.println("Test Case Id is " + tcid + " test case is " + tcName);

		CreateRecurringEventRequest crr = transactionsHelper.createRecurringTxnRequest(100.00, "USD", "consumer",
				"CREDIT", 29, "SYSTEM", "0,0,1", "memo", "bank", "1234", "WEEKLY", "0,1,0", "DEPOSIT", sessionObj);

		HttpMethodParameters httpMethodParameters = HttpMethodParameters.builder().build();
		httpMethodParameters.setBodyParams(crr.getJsonString());
		accountUtils.createRecurringEvents(httpMethodParameters, sessionObj);

		HashMap<String, String> queryParams = new HashMap<String, String>();
		queryParams.put("sourceType", sourceType);

		HttpMethodParameters httpParam = HttpMethodParameters.builder().build();
		httpParam.setQueryParams(queryParams);

		Response getRecurringResponse = predictedUtils.getPredictedEvents(httpParam, sessionObj);
		System.out.println("getRecurringResponse ::" + getRecurringResponse.asString());
		String id = senseTransactionHelper.getRecurringEventId(getRecurringResponse, recurringEventId);
		System.out.println("ididididid::"+id);
		
		HttpMethodParameters httpParam1 = HttpMethodParameters.builder().build();
		Map<String, Object> pathParam = new LinkedHashMap<String, Object>();
		pathParam.put("predictedEventId", id);
		httpParam1.setPathParams(pathParam);
		Response deleteResponse = predictedUtils.deletePredictedEvents(httpParam1, sessionObj);
		deleteResponse = predictedUtils.deletePredictedEvents(httpParam1, sessionObj);
		jsonAssertionUtil.assertJSON(deleteResponse, httpStatusCode, resFile, resFilePath);
	}

	@Test(enabled = true, dataProvider = "feeder", priority = 4)
	@Source("\\TestData\\CSVFiles\\Ok2Spend_EndPoint\\DeleteRecurringEventNegative2.csv")
	public void deleteRecurringEventNegative(String tcid, String tcName, int httpStatusCode, String resFile,
			String resFilePath, String recurringEventId, String sourceType, String accountStatus, String enabled)
			throws Exception {
		commonUtils.isTCEnabled(enabled, tcid);
		HashMap<String, String> queryParams = new HashMap<String, String>();
		queryParams.put("sourceType", sourceType);

		HttpMethodParameters httpParams = HttpMethodParameters.builder().build();
		httpParams.setQueryParams(queryParams);
		Response getRecurringEventsResponse = accountUtils.getRecurringEvents(httpParams, sessionObj);

		System.out.println("getRecurringEventsResponse ::" + getRecurringEventsResponse.asString() + "testcase id ::"
				+ tcid + tcName);
		Integer accountId = getRecurringEventsResponse.jsonPath().get("account[0].id");
		System.out.println("accountId ::" + accountId);
		String container = getRecurringEventsResponse.jsonPath().get("account[0].CONTAINER");

		if (accountStatus.equalsIgnoreCase("DELETED")) {
			HttpMethodParameters httpParameter = accountsHelper.createPathParam("accountId", accountId.toString());
			accountUtils.deleteAccount(httpParameter, sessionObj);
		}

		JSONObject bodyParam = new JSONObject();
		JSONObject account = new JSONObject();
		try {
			account.put("accountStatus", accountStatus);
			bodyParam.put("account", account);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		Map<String, Object> pathParam = new LinkedHashMap<String, Object>();
		pathParam.put("accountId", accountId);
		LinkedHashMap<String, String> queryParam = new LinkedHashMap<String, String>();
		queryParam.put("container", container);

		HttpMethodParameters httpMethodParameters = HttpMethodParameters.builder().build();
		httpMethodParameters.setBodyParams(bodyParam.toString());
		httpMethodParameters.setPathParams(pathParam);

		accountUtils.updateManualOrAggregateAccount(httpMethodParameters, sessionObj);

		httpParams.setPathParams(pathParam);
		Response deleteResponse = predictedUtils.deletePredictedEvents(httpParams, sessionObj);

		JSONObject bodyParams = new JSONObject();
		JSONObject accounts = new JSONObject();
		try {
			accounts.put("accountStatus", "ACTIVE");
			bodyParams.put("account", accounts);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		LinkedHashMap<String, String> Params = new LinkedHashMap<String, String>();
		Params.put("container", container);

		HttpMethodParameters httpMethodParameter = HttpMethodParameters.builder().build();
		httpMethodParameter.setQueryParams(queryParam);

		accountUtils.updateManualOrAggregateAccount(httpMethodParameters, sessionObj);
		jsonAssertionUtil.assertJSON(deleteResponse, httpStatusCode, resFile, resFilePath);
	}

	@AfterClass(alwaysRun = true)
	public void unRegisteredUser() {
		userUtils.unRegisterUser(sessionObj);
	}
}
