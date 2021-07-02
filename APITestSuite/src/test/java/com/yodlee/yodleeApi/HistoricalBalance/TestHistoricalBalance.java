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

package com.yodlee.yodleeApi.HistoricalBalance;

import com.yodlee.taas.annote.Feature;
import com.yodlee.taas.annote.FeatureName;
import com.yodlee.taas.annote.Info;
import com.yodlee.taas.annote.SubFeature;
import io.restassured.response.Response;

import java.util.Map;
import java.util.Properties;

import org.databene.benerator.anno.Source;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.yodlee.yodleeApi.assertions.JsonAssertionUtil;
import com.yodlee.yodleeApi.common.Base;
import com.yodlee.yodleeApi.common.Configuration;
import com.yodlee.yodleeApi.constants.*;
import com.yodlee.yodleeApi.helper.AccountsHelper;
import com.yodlee.yodleeApi.helper.ProviderAccountsHelper;
import com.yodlee.yodleeApi.pojo.HttpMethodParameters;
import com.yodlee.yodleeApi.utils.apiUtils.AccountUtils;
import com.yodlee.yodleeApi.utils.apiUtils.ProviderAccountUtils;
import com.yodlee.yodleeApi.utils.commonUtils.CommonUtils;
import com.yodlee.yodleeApi.utils.commonUtils.PropertyFileUtil;

/**
 * @author bhuvaneshwari
 *
 */
@Feature(featureName = { FeatureName.HISTORICALBALANCE })
public class TestHistoricalBalance extends Base {

	
	 /** Date implementation
	 * -----------------------------------------------------------------------------
	 * ------------ fromDate and to date passed as 0,0,0 in some places to get the
	 * current date. 0,0,0 means current Year+0-month+0-day+0 ex:current Date is
	 * 2017-04-04 if 0,0,0 is passed as fromdate or todate in csv then the formated
	 * date will be 2017-04-04 please give -ve values (ex:0,0,-1) to get older
	 * dates)
	 * -----------------------------------------------------------------------------
	 * -------------- Account id implementation
	 * -----------------------------------------------------------------------------
	 * -------------- To get valid account id please give the container names in the
	 * csv (accountIdContainer) for invalid accountId please type invalid
	 * -----------------------------------------------------------------------------
	 * ---------------*/
	 

	ProviderAccountsHelper providerAccountsHelper;
	public static Long providerAccountId1 = null;

	AccountsHelper accountsHelper;
	Response getAllAccountResponse;
	String updatedId;
	@BeforeClass
	public void setUp() throws JSONException {
		providerAccountsHelper = new ProviderAccountsHelper();
		accountsHelper = new AccountsHelper();
		Configuration configuration = Configuration.getInstance();
		PropertyFileUtil propertyFileUtilObj = new PropertyFileUtil();
		ProviderAccountUtils providerAccountUtils = new ProviderAccountUtils();
		 
		long providerId = 16441;
		if (configuration.isUserStatic() && !configuration.isFirstRun()) {

			Properties properties = propertyFileUtilObj.loadParameters("providerAccountId.properties");
			providerAccountId1 = Long.parseLong(properties.getProperty(getClassName()));
			System.out.println("providerAccountId111:" + providerAccountId1);
		} else {
			
			Response response = providerAccountUtils.addProviderAccountStrict(providerId, "loginform",
					"yslAuto1.site16441.1", "site16441.1", configuration.getCobrandSessionObj());
			
			providerAccountId1 = response.jsonPath().getLong("providerAccount.id");
			if (configuration.isUserStatic()) {
				propertyFileUtilObj.writePropertyFile(getClassName(),
						String.valueOf(providerAccountId1));
			}
		
		}
	}

	@Info(userStoryId = "", subfeature = { SubFeature.GET_HISTORICAL_BALANCE })
	@Test(dataProvider = "feeder", enabled = true)
	@Source("\\TestData\\CSVFiles\\Accounts\\GetHistoricalBalance\\GetHistoricalBalancePositiveScenarios.csv")
	public void testGetHistoricalBalancePositiveScenarios(String tcId, String tcName, String accountIdContainer,
			String accountName, String includeCF, String fromDate, String toDate, String interval, String skip,
			String top, int status, String respfile, String filePath, String defectId, String enabled) {
		CommonUtils commonUtils= new CommonUtils();
		commonUtils.isTCEnabled(enabled, tcId);
		AccountUtils accountUtils = new AccountUtils();
		AccountsHelper accountsHelper = new AccountsHelper();
		Configuration configurationObj = Configuration.getInstance();
		JsonAssertionUtil jsonAssertionUtil = new JsonAssertionUtil();
		System.out.println("TestCase id is " + tcId + " TestCase Name is " + tcName);
		int count = 0;
		
		HttpMethodParameters httpParams = HttpMethodParameters.builder().build();
		Map<String, Object> queryParams = accountsHelper.createQueryParamsForGetAccounts(Constants.ACTIVE_STATUS,
				accountIdContainer, String.valueOf(providerAccountId1), null, null, null, null);
		httpParams.setQueryParams(queryParams);

		Response getAccountsResponse = accountUtils.getAccountDetailsForAccountId(httpParams, configurationObj.getCobrandSessionObj());
		JSONObject object = new JSONObject(getAccountsResponse.asString());
		JSONArray accountArray = object.getJSONArray(JSONPaths.ACCOUNT);
		JSONObject accountObject = null;
		for (count = 0; count < accountArray.length(); count++) {
			accountObject = accountArray.getJSONObject(count);
			String accName = accountObject.getString("accountName");
			if (accName.equalsIgnoreCase(accountName))
				break;
		}
		Long itemAccountId = accountObject.getLong(Constants.ID);
		
		Map<String, Object> queryParam = accountsHelper.createQueryParamsForGetHistoricalBalance(
				String.valueOf(itemAccountId), includeCF, fromDate, toDate, interval, null, skip, top);
		HttpMethodParameters httpMethodParameters = HttpMethodParameters.builder().build();
		httpMethodParameters.setQueryParams(queryParam);
		Response historicalBalanceResponse = accountUtils.getHistoricalBalance(httpMethodParameters,
				configurationObj.getCobrandSessionObj());

		
		if (historicalBalanceResponse.statusCode() == status)
			
			jsonAssertionUtil.assertResponse(historicalBalanceResponse, status, respfile, filePath);
		else {
			Assert.assertFalse(true, tcId + "::" + tcName + " is Failed");
		}

	}

	@Test(dataProvider = "feeder", enabled = true, alwaysRun = true)
	@Info(userStoryId = "", subfeature = { SubFeature.GET_HISTORICAL_BALANCE })
	@Source("\\TestData\\CSVFiles\\Accounts\\GetHistoricalBalance\\GetHistoricalBalanceForClosedStatus.csv")
	public void testGetHistoricalBalanceForClosedStatus(String tcId, String tcName, String accountIdContainer,
			String accountName, String status, String includeCF, String fromDate, String toDate, String interval,
			String skip, String top, int statusCode, String respfile, String filePath, String enabled, String isAccId) {
		CommonUtils commonUtils = new CommonUtils();
		commonUtils.isTCEnabled(enabled, tcId);
		AccountUtils accountUtils= new AccountUtils();
		JsonAssertionUtil jsonAssertionUtil = new JsonAssertionUtil();
		Configuration configurationObj = Configuration.getInstance();

		int count = 0;
		String id = null;
		JSONObject accountObject = null;
		System.out.println("TestCase id is " + tcId);
		if (isAccId.equalsIgnoreCase("FALSE")) {
			/*Response getAccountsResponse = ApiUtils.getAccountSummary(Constants.EMPTY_STRING, accountIdContainer,
					String.valueOf(ProviderAccountUtils.providerAccountId), Constants.EMPTY_STRING,
					InitialLoader.cobSessionObj);
			getAccountsResponse.then().log().all();*/
			
			HttpMethodParameters httpParams = HttpMethodParameters.builder().build();
			Map<String, Object> queryParams = accountsHelper.createQueryParamsForGetAccounts(null,
					accountIdContainer, String.valueOf(providerAccountId1), null, null, null, null);
			httpParams.setQueryParams(queryParams);

			Response getAccountsResponse = accountUtils.getAccounts(httpParams, configurationObj.getCobrandSessionObj());
			JSONObject object = new JSONObject(getAccountsResponse.asString());
			JSONArray accountArray = object.getJSONArray(JSONPaths.ACCOUNT);
			for (count = 0; count < accountArray.length(); count++) {
				accountObject = accountArray.getJSONObject(count);
				String accName = accountObject.getString("accountName");
				if (accName.equalsIgnoreCase(accountName))
					break;
			}
			Long itemAccountId = accountObject.getLong(Constants.ID);
			String bodyParam = accountsHelper.createBodyParamsForUpdateManualAccnt(null, "ACTIVE", null, null, null);
			HttpMethodParameters httpMethodParameters = HttpMethodParameters.builder().build();
			httpMethodParameters = accountsHelper.createPathParam("itemAccountId", itemAccountId);
			httpMethodParameters.setBodyParams(bodyParam);
			
			Response updatedStatusResponse=accountUtils.updateManualOrAggregateAccount(httpMethodParameters,
					configurationObj.getCobrandSessionObj());
			/*Response updatedStatusResponse = ApiUtils.updatedStatusResponse(status, InitialLoader.cobSessionObj,
					itemAccountId);*/
			id = String.valueOf(itemAccountId);
		}
		if (isAccId.equalsIgnoreCase("FALSE")) {
			updatedId = id;
			id = "";

		} else {
			id = updatedId;
		}
		Map<String, Object> queryParam = accountsHelper.createQueryParamsForGetHistoricalBalance(
				id, includeCF, fromDate, toDate, interval, null, skip, top);
		HttpMethodParameters httpMethodParameters = HttpMethodParameters.builder().build();
		httpMethodParameters.setQueryParams(queryParam);
		Response historicalBalanceResponse = accountUtils.getHistoricalBalance(httpMethodParameters,
				configurationObj.getCobrandSessionObj());
		
		/*Response historicalBalanceResponse = accountsHelper.getHistoricalBalance(id, accountIdContainer, includeCF,
				fromDate, toDate, interval, skip, top);*/
		if (historicalBalanceResponse.getStatusCode() == statusCode) {
			/*CommonUtils.saveJson(historicalBalanceResponse.prettyPrint(), filePath, respfile + ".json");
			CommonUtils.assertLenientJSON(historicalBalanceResponse, HttpStatus.OK, respfile + ".json", filePath);*/
			jsonAssertionUtil.assertResponse(historicalBalanceResponse, HttpStatus.OK, respfile, filePath);
		} else {
			Assert.assertFalse(true, tcId + "::" + tcName + " is Failed");
		}

	}

	@Info(userStoryId = "", subfeature = { SubFeature.GET_HISTORICAL_BALANCE })
	@Test(dataProvider = "feeder", enabled = true, alwaysRun = true, priority=1)
	@Source("\\TestData\\CSVFiles\\Accounts\\GetHistoricalBalance\\GetHistoricalBalanceNegativeScenarios.csv")
	public void testGetHistoricalBalanceNegativeScenarios(String tcId, String tcName, String accountIdContainer,
			String includeCF, String fromDate, String toDate, String interval, String skip, String top, int status,
			String respfile, String filePath, String defectId, String enabled) {
		CommonUtils commonUtils= new CommonUtils();
		commonUtils.isTCEnabled(enabled, tcId);
		AccountUtils accountUtils = new AccountUtils();
		JsonAssertionUtil jsonAssertionUtil = new JsonAssertionUtil();
		System.out.println("TestCase id is " + tcId + " TestCase Name is " + tcName);
		int count = 0;
		/*Response getAccountsResponse = ApiUtils.getAccountSummary(Constants.ACTIVE_STATUS, accountIdContainer,
				String.valueOf(ProviderAccountUtils.providerAccountId), Constants.EMPTY_STRING,
				InitialLoader.cobSessionObj);
		getAccountsResponse.then().log().all();*/
		
		HttpMethodParameters httpParams = HttpMethodParameters.builder().build();
		Map<String, Object> queryParams = accountsHelper.createQueryParamsForGetAccounts(Constants.ACTIVE_STATUS,
				accountIdContainer, String.valueOf(providerAccountId1), null, null, null, null);
		httpParams.setQueryParams(queryParams);

		Configuration configurationObj = Configuration.getInstance();
		Response getAccountsResponse = accountUtils.getAccounts(httpParams, configurationObj.getCobrandSessionObj());

		if (getAccountsResponse.statusCode() == HttpStatus.BAD_REQUEST) {
			/*CommonUtils.saveJson(getAccountsResponse.prettyPrint(), filePath, respfile);
			CommonUtils.assertJSON(getAccountsResponse, status, respfile, filePath);*/
			jsonAssertionUtil.assertJSON(getAccountsResponse, status, respfile, filePath);
		} else {
			JSONObject object = new JSONObject(getAccountsResponse.asString());
			JSONArray accountArray = object.getJSONArray(JSONPaths.ACCOUNT);

			JSONObject accountObject = accountArray.getJSONObject(count);
			Long itemAccountId = accountObject.getLong(Constants.ID);
			/*Response historicalBalanceResponse = accountsHelper.getHistoricalBalance(String.valueOf(itemAccountId),
					accountIdContainer, includeCF, fromDate, toDate, interval, skip, top);*/
			
			Map<String, Object> queryParam = accountsHelper.createQueryParamsForGetHistoricalBalance(
					String.valueOf(itemAccountId), includeCF, fromDate, toDate, interval, null, skip, top);
			HttpMethodParameters httpMethodParameters = HttpMethodParameters.builder().build();
			httpMethodParameters.setQueryParams(queryParam);
			Response historicalBalanceResponse = accountUtils.getHistoricalBalance(httpMethodParameters,
					configurationObj.getCobrandSessionObj());

			Assert.assertEquals(historicalBalanceResponse.statusCode(), status);
			jsonAssertionUtil.assertJSON(historicalBalanceResponse, status, respfile, filePath);
			/*CommonUtils.saveJson(historicalBalanceResponse.prettyPrint(), filePath, respfile);
			CommonUtils.assertJSON(historicalBalanceResponse, status, respfile, filePath);*/
		}
		// TODO
	}

	public String getClassName() {
		String className = "";
		Class<?> enclosingClass = getClass().getEnclosingClass();
		if (enclosingClass != null) {
			className = enclosingClass.getName();
		} else {
			className = getClass().getName();
		}
		return className;
	}

	/**
	 * After class that deletes the provider account id.
	 */
	@AfterClass(alwaysRun = true)
	public void deleteAccount() {
		ProviderAccountUtils providerAccountUtils = new ProviderAccountUtils();
		/*Response deleteResponse = ProviderAccountUtils.deleteProviderAccount(ProviderAccountUtils.providerAccountId,
				InitialLoader.cobSessionObj);
		deleteResponse.then().log().all();*/
		HttpMethodParameters httpParams = HttpMethodParameters.builder().build();
		httpParams.setBodyParams(providerAccountId1.toString());
		Response deleteResponse = providerAccountUtils.deleteProviderAccount(httpParams, Configuration.getInstance().getCobrandSessionObj());
	}
}
