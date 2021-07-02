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


/**
 * @author bhuvaneshwari
 */
package com.yodlee.app.yodleeApi.Ok2Spend;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.testng.Assert.assertFalse;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;

import org.databene.benerator.anno.Source;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.testng.annotations.AfterClass;
import org.testng.Assert;
import org.testng.SkipException;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.google.gson.JsonParser;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.yodlee.yodleeApi.common.Configuration;
import com.yodlee.yodleeApi.constants.JSONPaths;
import com.yodlee.yodleeApi.helper.AccountsHelper;
import com.yodlee.yodleeApi.helper.CreateRecurringEventRequest;
import com.yodlee.yodleeApi.helper.TransactionsHelper;
import com.yodlee.yodleeApi.helper.UserHelper;
import com.yodlee.yodleeApi.assertions.JsonAssertionUtil;
import com.yodlee.yodleeApi.common.Base;
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
 * @author Mahadev
 *
 */
public class TestGetPredictedEvents extends Base {

	private String deletedId;
	private String manualId;

	private long providerAccountId;
	private EnvSession sessionObj = null;
	private UserUtils userUtils = new UserUtils();
	private ProviderAccountUtils providerAccountUtils = new ProviderAccountUtils();
	private TransactionsHelper transactionsHelper = new TransactionsHelper();
	private Configuration configurationObj = Configuration.getInstance();
	private AccountUtils accountUtils = new AccountUtils();
	private PredictedUtils predictedUtils = new PredictedUtils();
	private AccountsHelper accountsHelper = new AccountsHelper();
	private CommonUtils commonUtils = new CommonUtils();
	private JsonAssertionUtil jsonAssertionUtil = new JsonAssertionUtil();
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

	private String updateToStopped() throws Exception {
		CreateRecurringEventRequest crr = transactionsHelper.createRecurringTxnRequest(100.01, "USD", "MANUAL TXN",
				"CREDIT", 29, "SYSTEM", "0,0,1", "memo", "bank", "", "MONTHLY", "0,6,0", "DEPOSIT", sessionObj);
		HttpMethodParameters httpParams = HttpMethodParameters.builder().build();
		httpParams.setBodyParams(crr.getJsonString());

		Response createRecurringEvent = accountUtils.createRecurringEvents(httpParams, sessionObj);
		crr.setRecurringEventId(createRecurringEvent);

		HashMap<String, String> queryParams = new HashMap<String, String>();
		queryParams.put("sourceType", "MANUAL");

		HttpMethodParameters httpMethodParameters = HttpMethodParameters.builder().build();
		httpMethodParameters.setQueryParams(queryParams);

		Response response =  predictedUtils.getPredictedEvents(httpMethodParameters, sessionObj);

		String id = getRecurringEventId(response, "VALID");

		JSONObject ure = new JSONObject(crr.getJsonString());
		ure.getJSONObject("account").getJSONObject("recurringEvent").put("predictedEventStatus", "STOPPED");
		ure.getJSONObject("account").getJSONObject("recurringEvent").put("userApprovalStatus", "REJECTED");

		HttpMethodParameters httpParameter = HttpMethodParameters.builder().build();

		LinkedHashMap<String, Object> pathParam = new LinkedHashMap<String, Object>();
		pathParam.put("id", id);

		httpParameter.setBodyParams(ure.toString());
		httpParameter.setPathParams(pathParam);
		accountUtils.updateAllRecurringEvents(httpParameter, sessionObj);
		return manualId = id;
	}

	private String deleteSeries() {
		HashMap<String, String> queryParams = new HashMap<String, String>();
		queryParams.put("sourceType", "SYSTEM_PREDICTED");
		queryParams.put("predictedEventStatus", "ACTIVE");

		HttpMethodParameters httpMethodParameters = HttpMethodParameters.builder().build();
		httpMethodParameters.setQueryParams(queryParams);
		Response getRecurringResponse = predictedUtils.getPredictedEvents(httpMethodParameters, sessionObj);

		if (!getRecurringResponse.asString().contains("predictedEvents")) {
			Assert.fail("No system recurring events present in the response");
		}
		String seriesId = getRecurringEventId(getRecurringResponse, "VALID");
		HttpMethodParameters pathParam = accountsHelper.createPathParam("seriesId", seriesId);

		Response deleteResponse = accountUtils.deleteRecurringEvents(pathParam, sessionObj);
		if (deleteResponse.statusCode() != 204) {
			Assert.fail("Not able to delete series");
		} else {
			deletedId = seriesId;
		}
		return deletedId;
	}

	/**
	 * Returns the recurring event id from API response if it is not passed in the
	 * CSV
	 *
	 * @param getRecurringResponse
	 * @param recurringEventId
	 * @return
	 */
	private String getRecurringEventId(Response getRecurringResponse, String recurringEventId) {
		if (recurringEventId.equalsIgnoreCase("VALID")) {
			JSONObject response = new JSONObject(getRecurringResponse.asString());
			String srcType = getRecurringResponse.jsonPath().get("predictedEvents[0].sourceType");
			JSONArray account = response.getJSONArray("predictedEvents");
			Integer id;
			if (account.getJSONObject(0).has("recurringEvents")) {
				id = getRecurringResponse.jsonPath().get("account[0].recurringEvents[0].id");
			} else {
				id = getRecurringResponse.jsonPath().get("predictedEvents[0].id");
			}
			return id.toString();
		} else if (recurringEventId.equalsIgnoreCase("OTHER_USER")) {
			JSONObject response = new JSONObject(getRecurringResponse.asString());
			JSONArray account = response.getJSONArray("account");
			Integer id;
			if (account.getJSONObject(0).has("recurringEvents")) {
				id = getRecurringResponse.jsonPath().get("account[0].recurringEvents[0].id");
			} else {
				id = getRecurringResponse.jsonPath().get("account[1].recurringEvents[0].id");
			}
			id = id - 100;
			return id.toString();
		} else if (recurringEventId.equalsIgnoreCase("INVALID")) {
			Integer id = 123466;
			return id.toString();
		} else if (recurringEventId.equalsIgnoreCase("NULL")) {
			return null;
		} else {
			return recurringEventId;
		}
	}

	public String getAccountString(Response response) throws JSONException {
		JSONObject account = new JSONObject(response.asString());
		JSONArray accountsArray = account.getJSONArray("account");
		String[] accountIds = new String[accountsArray.length()];
		String accountString = accountsArray.getJSONObject(0).getString("id");
		for (int i = 0; i < accountsArray.length(); i++) {
			if (accountsArray.getJSONObject(i).getString("CONTAINER").equals("bank")
					|| accountsArray.getJSONObject(i).getString("CONTAINER").equals("creditCard")) {

				accountIds[i] = accountsArray.getJSONObject(i).getString("id");
				accountString += "," + accountsArray.getJSONObject(i).getString("id");
			}
		}
		return accountString;
	}

	@Test(enabled = true, dataProvider = "feeder", priority = 1)
	@Source("\\TestData\\CSVFiles\\Ok2Spend_EndPoint\\GetPredictedEvents.csv")
	public void testRecurringEvents(String testCaseID, String testcase,String resFilePath, String resFile,String container, String Enabled, String recurringEventStatus,
			String sourceType, String userAprovalStatus) throws JSONException, InterruptedException, IOException {
		commonUtils.isTCEnabled(Enabled, testCaseID);
		Long accountString = accountsHelper.getAccountId(container, sessionObj);
		HashMap<String, String> queryParams = new HashMap<String, String>();

		queryParams.put("accountId", accountString.toString());
		queryParams.put("predictedEventStatus", recurringEventStatus);
		queryParams.put("sourceType", sourceType);
		queryParams.put("userAprovalStatus", userAprovalStatus);

		HttpMethodParameters httpMethodParameters = HttpMethodParameters.builder().build();
		httpMethodParameters.setQueryParams(queryParams);
		Response getRecuringEventsResponse = predictedUtils.getPredictedEvents(httpMethodParameters, sessionObj);

		if(testcase.contains("optout") || testcase.contains("optin")){
			JsonParser jsonParser = new JsonParser();
			JsonObject eventObject;
			JsonObject responseObj = (JsonObject) jsonParser.parse(getRecuringEventsResponse.asString().toString());
			JsonArray eventsArray = responseObj.getAsJsonArray("predictedEvents");
				if(testcase.contains("optin")){
					eventObject = (JsonObject)eventsArray.get(1);
				}else{
					eventObject = (JsonObject)eventsArray.get(0);
				}
				int confindencePercent = eventObject.get("confidence").getAsInt();
				String confindenceLevel = eventObject.get("confidenceLevel").getAsString();
				String userApprovalStatus = eventObject.get("userApprovalStatus").getAsString();

				if (confindencePercent>70 && confindenceLevel.equals("HIGH") && userApprovalStatus.contains("APPROVED")){
					Assert.assertTrue(true,"identified series is optout series");
					return;
				}
				else if (confindencePercent<70 && confindenceLevel.contains("LOW") && userApprovalStatus.contains("PENDING")){
					Assert.assertTrue(true,"identified series is optin series");
					return;
				}
				else{
					Assert.fail("Some issue userApprovalStatus- identified series is not optout optin series");
					return;
				}
		}

		 if (getRecuringEventsResponse.asString().contains("frequency")
				|| getRecuringEventsResponse.asString().contains("sourceType")
				|| getRecuringEventsResponse.asString().contains("confidence")) {
			Assert.assertTrue(true);
		} else {
			assertFalse(false,"sourceType key not found");
		}
	}

	@Test(enabled = true, dataProvider = "feeder", priority = 7)
	@Source("\\TestData\\CSVFiles\\Ok2Spend_EndPoint\\GetPredictedEventsWithFilters.csv")
	public void testRecurringEventsWithFilters(String testCaseID, String testcase, String resFilePath, String resFile,
			String Enabled, String predictedEventStatus, String sourceType, String userAprovalStatus) throws Exception {
		commonUtils.isTCEnabled(Enabled, testCaseID);
		CreateRecurringEventRequest crr = transactionsHelper.createRecurringTxnRequest(100.0, "INR", "Description",
				"DEBIT", 92, "SYSTEM", "0,0,1", "memo", "BANK", "1234", "WEEKLY", "0,0,10", "DEPOSIT", sessionObj);

		HttpMethodParameters httpParams = HttpMethodParameters.builder().build();
		httpParams.setBodyParams(crr.getJsonString());

		Response response = accountUtils.createRecurringEvents(httpParams, sessionObj);

		HashMap<String, String> queryParams = new HashMap<String, String>();

		queryParams.put("predictedEventStatus", predictedEventStatus);
		queryParams.put("sourceType", sourceType);
		queryParams.put("userAprovalStatus", userAprovalStatus);

		HttpMethodParameters httpMethodParameters = HttpMethodParameters.builder().build();
		httpMethodParameters.setQueryParams(queryParams);

		Response getRecuringEventsResponse = predictedUtils.getPredictedEvents(httpMethodParameters, sessionObj);
		System.out.println("getRecuringEventsResponse::"+getRecuringEventsResponse.asString());
		if (getRecuringEventsResponse.asString().contains("frequency") && getRecuringEventsResponse.asString().contains("sourceType")
				&& getRecuringEventsResponse.asString().contains("confidence")) {
			Assert.assertTrue(true);
		}

		else if (predictedEventStatus.contains("STOPPED")) {
				Assert.assertTrue(getRecuringEventsResponse.asString().contains("{}"));
		}
		else {
			assertFalse(false,"Issue with API response");
		}
	}

	@Test(enabled = true, dataProvider = "feeder", priority = 2)
	@Source("\\TestData\\CSVFiles\\Ok2Spend_EndPoint\\GetPredictedEventsWithId.csv")
	public void testRecurringEventsWithId(String testCaseID, String testcase, String dagUsername, String dagPassword,
			String providerId, String resFilePath, String resFile, String Enabled, String recurringEventStatus,
			String sourceType, String userAprovalStatus) throws JSONException, InterruptedException, IOException {
		commonUtils.isTCEnabled(Enabled, testCaseID);
		System.out.println("Test Case Id is " + testCaseID + " test case is " + testcase);

		HashMap<String, String> queryParams = new HashMap<String, String>();

		queryParams.put("predictedEventStatus", recurringEventStatus);
		queryParams.put("sourceType", sourceType);
		queryParams.put("userAprovalStatus", userAprovalStatus);

		HttpMethodParameters httpMethodParameters = HttpMethodParameters.builder().build();
		httpMethodParameters.setQueryParams(queryParams);

		Response getRecuringEventsResponse = predictedUtils.getPredictedEvents(httpMethodParameters, sessionObj);
		String recurringEventId = getRecurringEventId(getRecuringEventsResponse, "VALID");

		HashMap<String, String> queryParam1 = new HashMap<String, String>();
		HttpMethodParameters httpParamsId = HttpMethodParameters.builder().build();
		HashMap<String, Object> pathParam = new HashMap<>();
		pathParam.put("predictedEventId",recurringEventId);
		httpParamsId.setPathParams(pathParam);
		httpParamsId.setQueryParams(queryParam1);

		Response getWithIdResponse = predictedUtils.getPredictedEvents(httpParamsId, sessionObj);

		if (getWithIdResponse.asString().contains("frequency") || getWithIdResponse.asString().contains("sourceType")
				|| getWithIdResponse.asString().contains("confidence")) {
			Assert.assertTrue(true);
		} else {
			assertFalse(false,"sourceType key not found");
		}
	}

	@Test(enabled = true, dataProvider = "feeder", priority = 11)
	@Source("\\TestData\\CSVFiles\\Ok2Spend_EndPoint\\GetPredictedEventsWithIdAndFilter.csv")
	public void testRecurringEventsWithIdAndFilters(String testCaseID, String testcase, String resFilePath,
			String resFile, String Enabled, String recurringEventStatus, String sourceType, String userAprovalStatus,
			String accountId) throws Exception {
		commonUtils.isTCEnabled(Enabled, testCaseID);
		Long accountString = accountsHelper.getAccountId(accountId, sessionObj);

		HashMap<String, String> queryParam = new HashMap<String, String>();
		queryParam.put("predictedEventStatus", recurringEventStatus);
		queryParam.put("sourceType", sourceType);
		queryParam.put("userApprovalStatus", userAprovalStatus);
		queryParam.put("accountId", accountString.toString());


		HttpMethodParameters httpParams = HttpMethodParameters.builder().build();
		httpParams.setQueryParams(queryParam);

		Response getRecuringEventsResponse = predictedUtils.getPredictedEvents(httpParams, sessionObj);
		if (getRecuringEventsResponse.asString().contains("predictedEventStatus")) {
			HashMap<String, String> queryParams = new HashMap<String, String>();

			queryParams.put("predictedEventStatus", recurringEventStatus);
			queryParams.put("sourceType", sourceType);
			queryParams.put("userApprovalStatus", userAprovalStatus);

			HttpMethodParameters httpParam = HttpMethodParameters.builder().build();
			httpParam.setQueryParams(queryParams);
			Response getRecuringIdsResponse = predictedUtils.getPredictedEvents(httpParams, sessionObj);
			if (getRecuringIdsResponse.asString().contains("predictedEvents")
					&& getRecuringIdsResponse.asString().contains("predictedEventStatus")
					&& getRecuringIdsResponse.asString().contains("confidence")) {
				Assert.assertTrue(true);
			}
		} else {
			if (recurringEventStatus == "STOPPED") {
				jsonAssertionUtil.assertJSON(getRecuringEventsResponse, resFile, resFilePath);
			}else{
				assertFalse(false, "Issue with getting the events ");
			}
		}
	}

	@Test(enabled = true, dataProvider = "feeder", priority = 4)
	@Source("\\TestData\\CSVFiles\\Ok2Spend_EndPoint\\GetPredictedEventsWithUpdate.csv")
	public void testRecurringEventsWithUpdate(String testCaseId, String testcase, int httpStatusCode, String resFile,
			String resFilePath, String Enabled) throws Exception {
		if ("false".equalsIgnoreCase(Enabled)) {
			throw new SkipException("Skipping Test Case ==> " + testcase);
		}
		HttpMethodParameters httpParam = HttpMethodParameters.builder().build();
		HashMap<String, String> queryParams = new HashMap<String, String>();
		Long accountLong;
		try {
			accountLong = accountsHelper.getAccountId(null, sessionObj);
		} catch (Exception e) {
			accountLong = null;
			queryParams.put("accountId", null);
		}
		if (accountLong != null)
			queryParams.put("accountId", accountLong.toString());
		queryParams.put("sourceType", null);
		queryParams.put("predictedEventStatus", null);
		queryParams.put("userApprovalStatus", null);
		updateToStopped();

		Response getRecurringIdResponse = predictedUtils.getPredictedEvents(httpParam, sessionObj);

		if (getRecurringIdResponse.asString().contains("predictedEventStatus")
				|| getRecurringIdResponse.asString().contains("sourceType")
				|| getRecurringIdResponse.asString().contains("confidence")) {
			Assert.assertTrue(true);
		} else {
			assertFalse(false,"sourceType key not found");
		}

	}

	@Test(enabled = true, dataProvider = "feeder", priority = 1)
	@Source("\\TestData\\CSVFiles\\Ok2Spend_EndPoint\\GetPredictedEventsNegative.csv")
	public void testRecurringEventsNegative(String testCaseID, String testcase, int httpStatusCode, String resFile,
			String resFilePath, String Enabled, String accountId, String predictedEventStatus, String sourceType,
			String userAprovalStatus) throws Exception {
		commonUtils.isTCEnabled(Enabled, testCaseID);
		HashMap<String, String> queryParam = new HashMap<String, String>();
		queryParam.put("accountId", accountId);
		queryParam.put("predictedEventStatus", predictedEventStatus);
		queryParam.put("sourceType", sourceType);
		queryParam.put("userApprovalStatus", userAprovalStatus);

		HttpMethodParameters httpParams = HttpMethodParameters.builder().build();
		httpParams.setQueryParams(queryParam);

		Response getRecurringResponse = predictedUtils.getPredictedEvents(httpParams, sessionObj);
		commonUtils.assertStatusCode(getRecurringResponse, httpStatusCode);
		jsonAssertionUtil.assertJSON(getRecurringResponse, httpStatusCode, resFile, resFilePath);
	}
	@Test(enabled = true, dataProvider = "feeder", priority = 8)
	@Source("\\TestData\\CSVFiles\\Ok2Spend_EndPoint\\GetRecurringEventsWithDelete.csv")
	public void testRecurringEventsWithDelete(String testCaseID, String testcase, int httpStatusCode, String resFile,
			String resFilePath, String Enabled) throws Exception {
		commonUtils.isTCEnabled(Enabled, testCaseID);
		HashMap<String, String> queryParams = new HashMap<String, String>();

		String recurringEventID = deleteSeries();
		HttpMethodParameters httpParam = HttpMethodParameters.builder().build();
		httpParam.setQueryParams(queryParams);

		HashMap<String, Object> pathParam = new HashMap<>();
		pathParam.put("predictedEventId",recurringEventID);
		httpParam.setPathParams(pathParam);

		Response getRecurringIdResponse = predictedUtils.getPredictedEvents(httpParam, sessionObj);

		if (getRecurringIdResponse.asString().contains("{}")) {
			assertTrue(true);
		}else{
			assertFalse(false,"Issue with deleting the events");
		}
	}
	
	@AfterClass(alwaysRun = true)
	public void unRegisteredUser() {
		userUtils.unRegisterUser(sessionObj);

	}
}

