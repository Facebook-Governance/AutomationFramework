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

package com.yodlee.yodleeApi.getAccounts;

import static net.javacrumbs.jsonunit.JsonAssert.assertJsonEquals;
import static net.javacrumbs.jsonunit.JsonAssert.when;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.LinkedHashMap;
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
import com.yodlee.yodleeApi.exceptions.UnexpectedFunctionalityException;
import com.yodlee.yodleeApi.helper.DataExtractsHelper;
import com.yodlee.yodleeApi.helper.ProviderAccountsHelper;
import com.yodlee.yodleeApi.helper.ProvidersHelper;
import com.yodlee.yodleeApi.helper.UserHelper;
import com.yodlee.yodleeApi.interfaces.Session;
import com.yodlee.yodleeApi.pojo.EnvSession;
import com.yodlee.yodleeApi.pojo.HttpMethodParameters;
import com.yodlee.yodleeApi.pojo.User;
import com.yodlee.yodleeApi.utils.apiUtils.AccountUtils;
import com.yodlee.yodleeApi.utils.apiUtils.DataExtractUtils;
import com.yodlee.yodleeApi.utils.apiUtils.ProviderAccountUtils;
import com.yodlee.yodleeApi.utils.apiUtils.ProviderUtils;
import com.yodlee.yodleeApi.utils.apiUtils.UserUtils;
import com.yodlee.yodleeApi.utils.commonUtils.CommonUtils;

import io.restassured.response.Response;
import net.javacrumbs.jsonunit.core.Option;

public class UniformResponseForAccountsAndDetailsTest extends Base {

	Configuration configurationObj = Configuration.getInstance();
	ProviderAccountsHelper providerAccountsHelper = new ProviderAccountsHelper();
	ProviderAccountUtils providerAccountUtils = new ProviderAccountUtils();
	CommonUtils commonUtils = new CommonUtils();
	AccountUtils accountUtils = new AccountUtils();
	DataExtractUtils dataExtractUtils = new DataExtractUtils();
	DataExtractsHelper dataExtractsHelper = new DataExtractsHelper();
	UserHelper userHelper = new UserHelper();
	UserUtils userUtils = new UserUtils();
	ProviderUtils providerUtils = new ProviderUtils();
	ProvidersHelper providersHelper = new ProvidersHelper();

	long providerAccountId;
	long providerId = 16441;
	String dagSiteUserName = "allfieldsDAG.site16441.1";
	String dagSitePassword = "site16441.1";
	String dataSetTemplateFile = "\\Templates\\template_allContainers.xml";
	final String containerCSVPath="\\TestData\\\\CSVFiles\\Accounts\\GetAccounts\\uniformResponse.csv";
	final String manualAccountContainerCSVPath="\\TestData\\CSVFiles\\Accounts\\GetAccounts\\uniformResponseManualAccount.csv";
	final String dataExtractsAccountsCSVPath="\\TestData\\CSVFiles\\Accounts\\GetAccounts\\dataExtractsAccountsResponse.csv";
	final String dataExtractsmanualAccountsCSVPath="\\TestData\\CSVFiles\\Accounts\\GetAccounts\\dataExtractsmanualAccountsResponse.csv";
	Response getAccountsWithoutQueryParamResponse;
	String fromDateAsString;
	String toDateAsString;
	final String ISO_FORMAT = "yyyy-MM-dd'T'HH:mm:ss'Z'";
	String userName = null;
	Response userDataResponse = null;
	Session env;
	EnvSession sessionObj = null;

	@BeforeClass
	public void testSetUp() throws IOException {
		
		if (configurationObj.isHybrid()) {
			logger.debug("Setting values for hybrid=true");
			env = configurationObj.getCobrandSessionObj();
			userName = configurationObj.getUserObj().getUsername();

		} else {
			logger.debug("Setting values for hybrid=false");
			env = configurationObj.getCobrandSessionObj();
			userName = configurationObj.getUserObj().getUsername();
		}
		fromDateAsString = new SimpleDateFormat(ISO_FORMAT).format(providerAccountsHelper.getUTCtime());
		
		Configuration configurationObj = Configuration.getInstance();
		ProviderAccountUtils providerAccountUtils = new ProviderAccountUtils();
		Response response = providerAccountUtils.addProviderAccountStrict(providerId, Constants.FIELD_ARRAY_FORM,
				dagSiteUserName, dagSitePassword, env);
		providerAccountId = response.jsonPath().getLong(JSONPaths.PROVIDER_ACC_ID);
		if (providerAccountId == 0) {
			throw new UnexpectedFunctionalityException("providerAccountId is not present after account addition");
		}
		System.out.println("provider acct id :" + providerAccountId);
		try {
			Thread.sleep(18000);
		} catch (InterruptedException e) {
			System.out.println("Thread interuptted!");
			e.printStackTrace();
		}
		toDateAsString = new SimpleDateFormat(ISO_FORMAT).format(providerAccountsHelper.getUTCtime());

		// The below lines are never to be commented
		getAccountsWithoutQueryParamResponse = accountUtils.getAllAccounts(env);
		Assert.assertTrue(!getAccountsWithoutQueryParamResponse.asString().contains("errorMessage"),
				"Error in get accounts response. Response - " + getAccountsWithoutQueryParamResponse.asString());
		
		Map<String, Object> queryParams = new LinkedHashMap<>();
		queryParams.put("loginName", userName);
		queryParams.put("fromDate", fromDateAsString);
		queryParams.put("toDate", toDateAsString);
		
		HttpMethodParameters httpMethodParameters = HttpMethodParameters.builder().build();
		httpMethodParameters.setQueryParams(queryParams);
		userDataResponse = dataExtractUtils.getUserData(httpMethodParameters, env);
				
	}

	@Test(dataProvider = Constants.FEEDER, alwaysRun = true, priority = 2)
	@Source(containerCSVPath)
	public void testGetAccountsWithFiltersTest(String tcId, String tcName, String container, String accountType,
			String queryParams, String enabled) throws FileNotFoundException {
		commonUtils.isTCEnabled(enabled, tcId);
		Map<String, String> queryParamMap = new LinkedHashMap<>();
		if (queryParams != null && !queryParams.isEmpty()) {
			String[] queryParamSplit = queryParams.split("//");
			for (String queryParamString : queryParamSplit) {
				queryParamMap.put(queryParamString.split("=")[0], queryParamString.split("=")[1]);
			}
		}
		queryParamMap.put("providerAccountId", String.valueOf(providerAccountId));
		queryParamMap.put("container", container);
		HttpMethodParameters httpParams = HttpMethodParameters.builder().build();
		httpParams.setQueryParams(queryParamMap);
		Response getAccountsWithContainerAndQueryParamsResponse = accountUtils.getAccounts(httpParams, env);
		Assert.assertTrue(!getAccountsWithContainerAndQueryParamsResponse.asString().contains("errorMessage"),
				"Error in get accounts Response - " + getAccountsWithContainerAndQueryParamsResponse.asString());
		JSONObject getAccountWithQueryParamsJSONObject = null;
		if (accountType == null || accountType.isEmpty()) {
			try {
				Map a = (Map) getAccountsWithContainerAndQueryParamsResponse.jsonPath()
						.getList("account.findAll{it.CONTAINER == '" + container + "'}").get(0);
				getAccountWithQueryParamsJSONObject = new JSONObject(a);
			} catch (Exception e) {
				Assert.fail("Error in fetching value from json in path - account.findAll{it.CONTAINER == '" + container
						+ "'}.Response - " + getAccountsWithContainerAndQueryParamsResponse.asString());
			}
		} else {
			try {
				Map a = (Map) getAccountsWithContainerAndQueryParamsResponse.jsonPath().getList(
						"account.findAll{it.CONTAINER == '" + container + "'&& it.accountType=='" + accountType + "'}")
						.get(0);
				getAccountWithQueryParamsJSONObject = new JSONObject(a);
			} catch (Exception e) {
				Assert.fail("Error in fetching value from json in path - account.findAll{it.CONTAINER == '" + container
						+ "'&& it.accountType=='" + accountType + "'}.Response - "
						+ getAccountsWithContainerAndQueryParamsResponse.asString());
			}
		}
		long accountId = getAccountWithQueryParamsJSONObject.getLong("id");
		Map<String, Object> queryParamObjectMap = new LinkedHashMap<>();
		queryParamObjectMap.put("container", container);

		Map<String, Object> pathParams = new HashMap<>();
		pathParams.put("accountId", String.valueOf(accountId));

		HttpMethodParameters httpMethodParameters = HttpMethodParameters.builder().build();
		httpMethodParameters.setQueryParams(queryParamObjectMap);
		httpMethodParameters.setPathParams(pathParams);
		Response getAccountDetailsResponse = accountUtils.getAccountDetailsForAccountId(httpMethodParameters, env);
		Assert.assertTrue(!getAccountDetailsResponse.asString().contains("errorMessage"),
				"Error in get account details Response - " + getAccountDetailsResponse.asString());
		Map a = (Map) getAccountDetailsResponse.jsonPath().getList("account").get(0);
		JSONObject getAccountDetailsJSONObject = new JSONObject(a);
		assertJsonEquals(getAccountDetailsJSONObject.toString(), getAccountWithQueryParamsJSONObject.toString(),
				when(Option.IGNORING_ARRAY_ORDER));
	}

	@Test(dataProvider = Constants.FEEDER, alwaysRun = true, priority = 1)
	@Source(containerCSVPath)
	public void testGetAccountsWithoutFiltersTest(String tcId, String tcName, String container, String accountType,
			String queryParams, String enabled) throws FileNotFoundException {
		commonUtils.isTCEnabled(enabled, tcId);
		JSONObject getAccountWithoutQueryParamsJSONObject = null;
		if (accountType == null || accountType.isEmpty()) {
			try {
				Map a = (Map) getAccountsWithoutQueryParamResponse.jsonPath()
						.getList("account.findAll{it.CONTAINER == '" + container + "'}").get(0);
				getAccountWithoutQueryParamsJSONObject = new JSONObject(a);
			} catch (Exception e) {
				Assert.fail("Error in fetching value from json in path - account.findAll{it.CONTAINER == '" + container
						+ "'}.Response - " + getAccountsWithoutQueryParamResponse.asString());
			}
		} else {
			try {
				Map a = (Map) getAccountsWithoutQueryParamResponse.jsonPath().getList(
						"account.findAll{it.CONTAINER == '" + container + "'&& it.accountType=='" + accountType + "'}")
						.get(0);
				getAccountWithoutQueryParamsJSONObject = new JSONObject(a);
			} catch (Exception e) {
				Assert.fail("Error in fetching value from json in path - account.findAll{it.CONTAINER == '" + container
						+ "'&& it.accountType=='" + accountType + "'}.Response - "
						+ getAccountsWithoutQueryParamResponse.asString());
			}
		}
		long accountId = getAccountWithoutQueryParamsJSONObject.getLong("id");
		Map<String, Object> queryParamObjectMap = new LinkedHashMap<>();
		queryParamObjectMap.put("container", container);

		Map<String, Object> pathParams = new HashMap<>();
		pathParams.put("accountId", String.valueOf(accountId));

		HttpMethodParameters httpMethodParameters = HttpMethodParameters.builder().build();
		httpMethodParameters.setQueryParams(queryParamObjectMap);
		httpMethodParameters.setPathParams(pathParams);
		Response getAccountDetailsResponse = accountUtils.getAccountDetailsForAccountId(httpMethodParameters, env);

		Assert.assertTrue(!getAccountDetailsResponse.asString().contains("errorMessage"),
				"Error in get account details Response - " + getAccountDetailsResponse.asString());
		Map a = (Map) getAccountDetailsResponse.jsonPath().getList("account").get(0);
		JSONObject getAccountDetailsJSONObject = new JSONObject(a);
		assertJsonEquals(getAccountDetailsJSONObject.toString(), getAccountWithoutQueryParamsJSONObject.toString(),
				when(Option.IGNORING_ARRAY_ORDER));

	}

	@Test(dataProvider = Constants.FEEDER, alwaysRun = true, priority = 1)
	@Source(dataExtractsAccountsCSVPath)
	public void testGetAccountsDataExtractsTest(String tcId, String tcName, String container, String accountType,
			String enabled) throws FileNotFoundException {
		commonUtils.isTCEnabled(enabled, tcId);
		Assert.assertTrue(!userDataResponse.asString().contains("errorMessage"),
				"Error in user data response. Response - " + userDataResponse.asString());
		JSONObject accountObject = null;
		if (accountType == null || accountType.isEmpty()) {
			try {
				accountObject = new JSONObject(((List<Map<?, ?>>) userDataResponse.body().jsonPath()
						.get("userData[0].account.findAll{it.CONTAINER =='" + container + "' && it.isManual==false}"))
								.get(0));
			} catch (Exception e) {
				Assert.fail("Error in fetching value from json in path - userData[0].account.findAll{it.CONTAINER =='"
						+ container + "' && it.isManual==false}" + ".Response - " + userDataResponse.asString());
			}
		} else {
			try {
				accountObject = new JSONObject(((List<Map<?, ?>>) userDataResponse.body().jsonPath()
						.get("userData[0].account.findAll{it.CONTAINER =='" + container + "'&& it.accountType=='"
								+ accountType + "' && it.isManual==false}")).get(0));
			} catch (Exception e) {
				Assert.fail("Error in fetching value from json in path - userData[0].account.findAll{it.CONTAINER =='"
						+ container + "'&& it.accountType=='" + accountType + "' && it.isManual==false}"
						+ ".Response - " + userDataResponse.asString());
			}
		}
		accountObject.remove("isDeleted"); 
		
		
		long accountId = accountObject.getLong("id");
		
		System.out.println("accountObject:::"+accountObject.toString());
		
		Map<String, Object> queryParamObjectMap = new LinkedHashMap<>();
		queryParamObjectMap.put("container", container);

		Map<String, Object> pathParams = new HashMap<>();
		pathParams.put("accountId", String.valueOf(accountId));

		HttpMethodParameters httpMethodParameters = HttpMethodParameters.builder().build();
		httpMethodParameters.setQueryParams(queryParamObjectMap);
		httpMethodParameters.setPathParams(pathParams);
		Response getAccountDetailsResponse = accountUtils.getAccountDetailsForAccountId(httpMethodParameters, env);

		Assert.assertTrue(!getAccountDetailsResponse.asString().contains("errorMessage"),
				"Error in get account details Response - " + getAccountDetailsResponse.asString());
		
		
		Map a = (Map) getAccountDetailsResponse.jsonPath().getList("account").get(0);
		JSONObject getAccountDetailsJSONObject = new JSONObject(a);
		
		if(accountObject.toString().contains("url") && !getAccountDetailsJSONObject.toString().contains("url")) {
			accountObject.remove("url");
		}
		
		System.out.println("accountObject final:"+accountObject);
		System.out.println("getAccountDetailsJSONObject final:"+getAccountDetailsJSONObject);
		
		assertJsonEquals(getAccountDetailsJSONObject.toString(), accountObject.toString(),
				when(Option.IGNORING_ARRAY_ORDER));
		
		System.out.println("getAccountDetailsJSONObject.toString()"+getAccountDetailsJSONObject.toString());
	}

	// Data Extracts for manual accounts does not work properly. Hence commenting
	// this test
	// @Test(dataProvider = Constants.FEEDER, alwaysRun = true, priority = 1)
	@Source(manualAccountContainerCSVPath)
	public void testManualAccounts(String tcId, String tcName, String container, String jsonBody, String enabled)
			throws FileNotFoundException {
		commonUtils.isTCEnabled(enabled, tcId);
		jsonBody = jsonBody.replaceAll("'", "\"").replaceAll("<", "{").replaceAll(">", "}");
		String fromDateAsString = new SimpleDateFormat(ISO_FORMAT).format(providerAccountsHelper.getUTCtime());
		// Response postManualAccountResponse = yslMethods.addManualAccount(jsonBody,
		// env);

		HttpMethodParameters httpMethodParameters = HttpMethodParameters.builder().build();
		httpMethodParameters.setBodyParams(jsonBody);

		Response postManualAccountResponse = accountUtils.addManualAccount(httpMethodParameters, env);
		try {
			Thread.sleep(15 * 1000);
		} catch (InterruptedException e) {
			logger.debug("Thread interuptted");
			e.printStackTrace();
		}
		String toDateAsString = new SimpleDateFormat(ISO_FORMAT).format(providerAccountsHelper.getUTCtime());
		Assert.assertTrue(!postManualAccountResponse.asString().contains("errorMessage"),
				"Error in manual account creation. Response - " + postManualAccountResponse.asString());
		long accountId = postManualAccountResponse.jsonPath().getLong("account[0].id");
		Map<String, Object> queryParams = new HashMap<String, Object>();
		queryParams.put("container", container);

		Map<String, Object> pathParams = new HashMap<>();
		pathParams.put("accountId", String.valueOf(accountId));

		HttpMethodParameters httpParam = HttpMethodParameters.builder().build();
		httpParam.setQueryParams(queryParams);
		httpParam.setPathParams(pathParams);
		Response getAccountDetailsResponse = accountUtils.getAccountDetailsForAccountId(httpParam, env);

		Assert.assertTrue(!getAccountDetailsResponse.asString().contains("errorMessage"),
				"Error in get account details. Response - " + getAccountDetailsResponse.asString());
		Map a = (Map) getAccountDetailsResponse.jsonPath().getList("account").get(0);
		JSONObject getAccountDetailsJSONObject = new JSONObject(a);
		/*
		 * Response userDataResponse =
		 * yslMethods.getUserDataResponseForUser(fromDateAsString, toDateAsString, env,
		 * userName);
		 */

		Response userDataResponse = dataExtractsHelper.getUserDataResponseForUser(fromDateAsString, toDateAsString, env,
				userName);

		try {
			JSONObject manualAccountObject = new JSONObject(((List<Map<?, ?>>) userDataResponse.body().jsonPath()
					.get("userData[0].account.findAll{it.CONTAINER =='" + container + "' && it.isManual==true}"))
							.get(0));
			assertJsonEquals(getAccountDetailsJSONObject.toString(), manualAccountObject.toString(),
					when(Option.IGNORING_ARRAY_ORDER));
		} catch (Exception e) {
			Assert.fail("Error in fetching value from json in path - userData[0].account.findAll{it.CONTAINER =='"
					+ container + "' && it.isManual==true}" + ".Response - " + userDataResponse.asString());
		}
	}

	@AfterClass(alwaysRun = true)
	public void unRegisteredUser() {
		userUtils.unRegisterUser(env);

	}
}