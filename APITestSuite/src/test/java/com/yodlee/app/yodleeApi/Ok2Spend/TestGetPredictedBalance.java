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

import java.sql.Connection;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import org.apache.commons.lang.time.DateUtils;
import org.databene.benerator.anno.Source;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.google.inject.PrivateBinder;
import com.yodlee.DBHelper;
import com.yodlee.yodleeApi.common.Base;
import com.yodlee.yodleeApi.common.Configuration;
import com.yodlee.yodleeApi.constants.JSONPaths;
import com.yodlee.yodleeApi.helper.AccountsHelper;
import com.yodlee.yodleeApi.helper.DbHelper;
import com.yodlee.yodleeApi.helper.SenseTransactionHelper;
import com.yodlee.yodleeApi.helper.UserHelper;
import com.yodlee.yodleeApi.pojo.EnvSession;
import com.yodlee.yodleeApi.pojo.HttpMethodParameters;
import com.yodlee.yodleeApi.pojo.User;
import com.yodlee.yodleeApi.utils.apiUtils.PredictedUtils;
import com.yodlee.yodleeApi.utils.apiUtils.ProviderAccountUtils;
import com.yodlee.yodleeApi.utils.apiUtils.UserUtils;
import com.yodlee.yodleeApi.utils.commonUtils.CommonUtils;

import io.restassured.response.Response;
import static net.javacrumbs.jsonunit.core.Option.IGNORING_ARRAY_ORDER;
import static net.javacrumbs.jsonunit.JsonAssert.assertJsonEquals;
import static net.javacrumbs.jsonunit.JsonAssert.when;

/**
* @author Mahadev
*
*/
public class TestGetPredictedBalance extends Base {

	private AccountsHelper accountsHelper = new AccountsHelper();
	private UserUtils userUtils = new UserUtils();
	private Configuration configurationObj = Configuration.getInstance();
	private ProviderAccountUtils providerAccountUtils = new ProviderAccountUtils();
	private UserHelper userHelper = new UserHelper();
	private CommonUtils commonUtils = new CommonUtils();
	private PredictedUtils predictedUtils = new PredictedUtils();
	private EnvSession sessionObj = null;
	private SenseTransactionHelper senseTransactionHelper = new SenseTransactionHelper();
	private static Long providerAccountId1 = null;
	private String userID = "";
	@BeforeClass(alwaysRun = true)
	public void AddAccount() throws JSONException {
		long providerId = 16441;
		User userInfo = User.builder().build();
		userInfo.setUsername("YSLSense" + System.currentTimeMillis());
		userInfo.setPassword("Test@123");
		userID = senseTransactionHelper.getMemIdOfUser(userInfo.getUsername());
		sessionObj = EnvSession.builder().cobSession(configurationObj.getCobrandSessionObj().getCobSession())
				.path(configurationObj.getCobrandSessionObj().getPath()).build();
		userHelper.getUserSession(userInfo, sessionObj);	

		Response response = providerAccountUtils.addProviderAccountStrict(providerId, "fieldarray",
				"luminChange.site16441.2", "site16441.2", sessionObj);
		
		providerAccountId1 = response.jsonPath().getLong(JSONPaths.PROVIDER_ACC_ID);
		System.out.println("providerAccountId1::::===" + providerAccountId1);	
	}

	@Test(enabled = true, dataProvider = "feeder", priority = 1)
	@Source("\\TestData\\CSVFiles\\Ok2Spend_EndPoint\\GetPredictedBalances.csv")
	public void getBalances(String tcid, String tcName, int httpStatusCode, String resFile, String resFilePath,
			String accountId, String fromDate, String toDate, String interval, String enabled) throws Exception {
		commonUtils.isTCEnabled(enabled, tcid);
		System.out.println("TestCase Id is:" + tcid);
		HttpMethodParameters httpParams = HttpMethodParameters.builder().build();
		Map<String, Object> queryParams = senseTransactionHelper.createQueryParamsForGetBalances(sessionObj, accountId,
				fromDate, toDate, interval);
		httpParams.setQueryParams(queryParams);
		Response response = predictedUtils.getPredictedBalances(httpParams, sessionObj);
		validateResponse(response, httpStatusCode, resFile, resFilePath);
	}

	@Test(enabled = true, dataProvider = "feeder", priority = 1)
	@Source("\\TestData\\CSVFiles\\Ok2Spend_EndPoint\\GetPredictedBalancesMultipleAccounts.csv")
	public void getBalancesMultipleAccounts(String tcid, String tcName, int httpStatusCode, String resFile,
			String resFilePath, String account1, String account2, String fromDate, String toDate, String interval,
			String enabled) throws Exception {
		commonUtils.isTCEnabled(enabled, tcid);
		System.out.println("TestCase Id is:" + tcid);

		Map<String, Object> queryParams = accountsHelper.createQueryParamsForMultipleAccounts(sessionObj, account1,
				account2, fromDate, toDate, interval);
		HttpMethodParameters httpParams = HttpMethodParameters.builder().build();
		httpParams.setQueryParams(queryParams);
		Response getBalanceResponse = predictedUtils.getPredictedBalances(httpParams, sessionObj);
		if (tcName.contains("Both invalid")) {
			validateResponse(getBalanceResponse, httpStatusCode, resFile, resFilePath);
		} else {
			Assert.assertEquals(400, httpStatusCode);
		}
	}
	
	@Test(enabled = true, dataProvider = "feeder", priority = 1)
	@Source("\\TestData\\CSVFiles\\Ok2Spend_EndPoint\\GetPredictedBalancesFTUE.csv")
	public void getBalancesForFTUE(String tcid, String tcName, int httpStatusCode,
			String accountId, String fromDate, String toDate,String interval,String preferredAccounts,String FTUE,String enabled) throws Exception {
		commonUtils.isTCEnabled(enabled, tcid);
		System.out.println("TestCase Id is:" + tcid);
		if(FTUE.equals("DONE")){
			Long bankAccountId = accountsHelper.getAccountId("bank", sessionObj);
			senseTransactionHelper.updateFTUEStatus(userID,bankAccountId);
		}
		HttpMethodParameters httpParams = HttpMethodParameters.builder().build();
		Map<String, Object> queryParams = senseTransactionHelper.createQueryParamsForGetBalancesFTUE(sessionObj, accountId,
				fromDate, toDate, interval,preferredAccounts);
		httpParams.setQueryParams(queryParams);
		Response response = predictedUtils.getPredictedBalances(httpParams, sessionObj);
		if (response.asString().contains("predictedBalances") && response.asString().contains("ftue")) {
			Assert.assertTrue("predictedBalances response validated", true);
		}
		else if (response.asString().contains("accountId and preferredAccounts parameters cannot be passed simultaneously")) {
			Assert.assertTrue("accountId and preferredAccounts parameters cannot be passed simultaneously", true);
		}else{
			Assert.assertFalse(false);
		}
	}
	
	public void validateResponse(Response response, int httpStatusCode, String resFile, String resFilePath)
			throws Exception {
		System.out.println("response::" + response.asString());
		System.out.println("httpStatusCode::" + httpStatusCode);
		CommonUtils commonUtils = new CommonUtils();
		if (response == null) {
			Assert.assertEquals(400, httpStatusCode);
		} else if (response.asString().contains("predictedBalances")) {
			Assert.assertEquals(200, httpStatusCode);
		} else {
			String storedResponse = commonUtils.readJsonFile(commonUtils.getPath(resFilePath + resFile));
					
					//commonUtils.readJsonFile(getPath(resFilePath + resFile));
			String expectedResponse = getActualResponse(storedResponse);
			JSONObject actualObject = new JSONObject(response.asString());
			assertJsonEquals(expectedResponse, actualObject.toString(), when(IGNORING_ARRAY_ORDER));
		}
	}

	/**
	 * Converts the date offsets present in the stored response to actual dates
	 *
	 * @param storedResponse
	 *            Stored response in string form
	 * @return Returns the modified json response with actual dates
	 * @throws Exception
	 */
	private String getActualResponse(String storedResponse) throws Exception {
		JSONObject responseObject = new JSONObject(storedResponse);
		if (!storedResponse.contains("referenceCode")) {
			JSONArray accounts = responseObject.getJSONArray("account");
			int size = accounts.length();

			for (int i = 0; i < size; i++) {
				JSONObject account = accounts.getJSONObject(i);
				JSONArray balances = account.getJSONArray("balances");
				int balanceSize = balances.length();
				for (int j = 0; j < balanceSize; j++) {
					changeOffsetToDate(balances.getJSONObject(j));
				}
			}
		}
		return responseObject.toString();
	}

	/**
	 * Changes the offset into a date
	 *
	 * @param jsonObject
	 *            jsonObject with offset present for
	 * @throws JSONException
	 */
	private static void changeOffsetToDate(JSONObject jsonObject) throws JSONException {
		if (jsonObject.has("date")) {
			DateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
			int offset;
			try {
				offset = jsonObject.getInt("date");
			} catch (Exception e) {
				return;
			}
			System.out.println(offset);
			Date d = new Date();
			d = DateUtils.addDays(d, offset);
			jsonObject.remove("date");
			String formattedString = format1.format(d);
			jsonObject.put("date", formattedString);
		}

	}
	@AfterClass(alwaysRun = true)
	public void unRegisteredUser() {
		userUtils.unRegisterUser(sessionObj);

	}
}
