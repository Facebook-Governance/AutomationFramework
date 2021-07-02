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

package com.yodlee.yodleeApi.Accounts;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.databene.benerator.anno.Source;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.yodlee.taas.annote.Feature;
import com.yodlee.taas.annote.FeatureName;
import com.yodlee.yodleeApi.assertions.JsonAssertionUtil;
import com.yodlee.yodleeApi.common.Base;
import com.yodlee.yodleeApi.common.Configuration;
import com.yodlee.yodleeApi.common.NonSdgLoginForm;
import com.yodlee.yodleeApi.constants.Constants;
import com.yodlee.yodleeApi.constants.JSONPaths;
import com.yodlee.yodleeApi.helper.AccountsHelper;
import com.yodlee.yodleeApi.helper.ProvidersHelper;
import com.yodlee.yodleeApi.helper.SessionHelper;
import com.yodlee.yodleeApi.pojo.EnvSession;
import com.yodlee.yodleeApi.pojo.HttpMethodParameters;
import com.yodlee.yodleeApi.utils.apiUtils.AccountUtils;
import com.yodlee.yodleeApi.utils.apiUtils.ProviderAccountUtils;
import com.yodlee.yodleeApi.utils.commonUtils.CommonUtils;

import io.restassured.response.Response;

/**
 * @author mallikan
 *
 */
@Feature(featureName = { FeatureName.ACCOUNTS })
public class TestGetInvestmentOptions extends Base {
	public String dagUN = "BankYodlee.site16441.1";
	public String dagPwd = "site16441.1";
	Long providerAccountId;
	public static final String VALID_GET_INVESTMENT_OPTIONS_CSV_1 = "\\TestData\\CSVFiles\\Accounts\\GetInvestmentOptions\\testGetInvestmentOptions1.csv";
	public static final String VALID_GET_INVESTMENT_OPTIONS_CSV_2 = "\\TestData\\CSVFiles\\Accounts\\GetInvestmentOptions\\testGetInvestmentOptionsWithChangedStatus.csv";
	public static final String VALID_GET_INVESTMENT_OPTIONS_CSV_3 = "\\TestData\\CSVFiles\\Accounts\\GetInvestmentOptions\\testGetInvestmentOptions3.csv";
	public static final String VALID_GET_INVESTMENT_OPTIONS_CSV_4 = "\\TestData\\CSVFiles\\Accounts\\GetInvestmentOptions\\testGetInvestmentOptions4.csv";
	public List<Long> accountIdList;
	NonSdgLoginForm nonSdgLoginForm = NonSdgLoginForm.getInstance();
	ProviderAccountUtils providerAccountUtils = new ProviderAccountUtils();
	ProvidersHelper providersHelper = new ProvidersHelper();
	Configuration configurationObj = Configuration.getInstance();
	ProviderAccountUtils providerAccountUtils1 = new ProviderAccountUtils();
	CommonUtils commonUtils = new CommonUtils();
	Set<Long> accountIds = new HashSet<>();
	AccountUtils accountUtils = new AccountUtils();
	AccountsHelper accountsHelper = new AccountsHelper();
	JsonAssertionUtil jsonAssertionUtil = new JsonAssertionUtil();

	public static Long providerAccountId1 = null;

	@BeforeClass(alwaysRun = true)
	public void setUpTest() {

		logger.info("********* EXECUTING BEFORE CLASS *****************");
		long providerId = 16441;
		Map<String, String> fieldValues = new HashMap<String, String>();
		fieldValues.put(Constants.DAG_USERNAME, dagUN);
		fieldValues.put(Constants.DAG_PASSWORD, dagPwd);
		providerAccountId1 = providerId;

		Response response = providerAccountUtils.addProviderAccountStrict(providerId, Constants.SIMPLIFIED_FORM,
				"BankYodlee.site16441.1", "site16441.1", configurationObj.getCobrandSessionObj());
		
		providerAccountId1 = response.jsonPath().getLong(JSONPaths.PROVIDER_ACC_ID);
		System.out.println("providerAccountId1::::===" + providerAccountId1);

	}

	@Test(alwaysRun = true, enabled = true, dataProvider = Constants.FEEDER, priority = 1)
	@Source(VALID_GET_INVESTMENT_OPTIONS_CSV_1)
	public void testGetInvestmentOptions(String tcid, String tcName, String container, String accountIdKey,
			String accountIdvalue, String includeKey, String include, String accountReconTypeKey,
			String accountReconType, int status, String resFile, String filePath, Long defectId, String enabled) {
		commonUtils.isTCEnabled(enabled, tcid);
		System.out.println("Test Case Id: " + tcid);
		if (accountIdvalue.equalsIgnoreCase("valid")) {

			// Creating SessionObj
			/*EnvSession session = new EnvSession(configurationObj.getCobrandSessionObj().getCobSession(),
					configurationObj.getCobrandSessionObj().getUserSession(),
					configurationObj.getCobrandSessionObj().getPath());*/
			
			EnvSession session = EnvSession.builder().cobSession(configurationObj.getCobrandSessionObj().getCobSession())
					.userSession(configurationObj.getCobrandSessionObj().getUserSession())
					.path(configurationObj.getCobrandSessionObj().getPath()).build();

			Map<String, Object> queryParam = accountsHelper.createQueryParamsForGetAccounts(String.valueOf(status),
					container, String.valueOf(providerAccountId), null, accountReconType, null, null);

			HttpMethodParameters httpParams = HttpMethodParameters.builder().build();
			httpParams.setQueryParams(queryParam);

			Response res = accountUtils.getAccounts(httpParams, session);
			try {
				List<Long> list = res.jsonPath().getList("account.id");
				Set<Long> ids = new HashSet<>(list);
				accountIds.addAll(ids);
			} catch (Exception e) {
				logger.info("Account id not coming...");
			}
		}

		// Creating SessionObj
		/*EnvSession session = new EnvSession(configurationObj.getCobrandSessionObj().getCobSession(),
				configurationObj.getCobrandSessionObj().getUserSession(),
				configurationObj.getCobrandSessionObj().getPath());*/
		
		EnvSession session = EnvSession.builder().cobSession(configurationObj.getCobrandSessionObj().getCobSession())
				.userSession(configurationObj.getCobrandSessionObj().getUserSession())
				.path(configurationObj.getCobrandSessionObj().getPath()).build();

		Map<String, Object> queryParam = new HashMap<>();

		queryParam = accountsHelper.createQueryParamsForGetAccounts(null, null, null, accountIdvalue, accountReconType,
				include, null);

		HttpMethodParameters httpParams = HttpMethodParameters.builder().build();
		httpParams.setQueryParams(queryParam);

		Response getInvestmentResponse = accountUtils.getAccountInvetmentOptions(httpParams, session);
		jsonAssertionUtil.assertResponse(getInvestmentResponse, status, resFile, filePath);
	}

	@Test(alwaysRun = true, enabled = true, dataProvider = Constants.FEEDER, priority = 2)
	@Source(VALID_GET_INVESTMENT_OPTIONS_CSV_3)
	public void testGetInvestmentOptionsForDifferentCobAndUserSession(String tcid, String tcName, String container,
			String accountIdKey, String includeKey, String include, String accountReconTypeKey, String accountReconType,
			String cobSession, String userSession, int statusCode, String resFile, String filePath, Long defectId,
			String enabled) {
		System.out.println("Test case Id id " + tcid + " TestCase Name is " + tcName);
		commonUtils.isTCEnabled(enabled, tcid);
		String accountId = "";

		// Creating QueryParam
		Map<String, Object> queryParam = new HashMap<>();

		queryParam = accountsHelper.createQueryParamsForGetAccounts(null, null, null, accountId, accountReconType,
				include, null);
		
		String cob = SessionHelper.getSessionToken(cobSession, "cob");
		String user = SessionHelper.getSessionToken(userSession, "user");
		// Creating SessionObj
		
		  EnvSession session =  EnvSession.builder().cobSession(cob).userSession(user).path(configurationObj.getCobrandSessionObj().getPath()).build();
				  
		HttpMethodParameters httpParams = HttpMethodParameters.builder().build();
		httpParams.setQueryParams(queryParam);
		Response getInvestmentResponse = accountUtils.getAccountInvetmentOptions(httpParams,
				session);
		jsonAssertionUtil.assertResponse(getInvestmentResponse, statusCode, resFile, filePath);

	}

	@Test(alwaysRun = true, enabled = true, dataProvider = Constants.FEEDER, priority = 4)
	@Source(VALID_GET_INVESTMENT_OPTIONS_CSV_2)
	public void testGetInvestmentOptionsWithChangedStatus(String tcid, String tcName, String accountIdKey,
			String statusKey, String status, String container, String includeKey, String include,
			String accountReconTypeKey, String accountReconType, int statusCode, String updatedStatus, String resFile,
			String filePath, Long defectId, String enabled) {
		commonUtils.isTCEnabled(enabled, tcid);
		System.out.println("Test case Id id " + tcid + " TestCase Name is " + tcName);
		/*EnvSession session = new EnvSession(configurationObj.getCobrandSessionObj().getCobSession(),
				configurationObj.getCobrandSessionObj().getUserSession(),
				configurationObj.getCobrandSessionObj().getPath());*/
		
		EnvSession session = EnvSession.builder().cobSession(configurationObj.getCobrandSessionObj().getCobSession())
				.userSession(configurationObj.getCobrandSessionObj().getUserSession())
				.path(configurationObj.getCobrandSessionObj().getPath()).build();
		Map<String, Object> queryParam = accountsHelper.createQueryParamsForGetAccounts(status, container,
				String.valueOf(providerAccountId), null, accountReconType, null, null);

		HttpMethodParameters httpParam = HttpMethodParameters.builder().build();
		httpParam.setQueryParams(queryParam);

		// Hitting the API
		Response getAccountsResponse = accountUtils.getAccounts(httpParam, session);

		int count;
		Long itemAccountId = null;
		try {
			JSONObject accountObject = new JSONObject(getAccountsResponse.asString());
			JSONArray accountArray = accountObject.getJSONArray(JSONPaths.ACCOUNT);

			for (count = 0; count < accountArray.length(); count++) {
				itemAccountId = accountArray.getJSONObject(count).getLong(Constants.ID);
				System.out.println("Account id changed is " + itemAccountId);

				// Creating HttpParams
				Map<String, Object> queryParams = accountsHelper.createQueryParamsForGetAccounts(updatedStatus, null,
						null, null, null, null, null);
				Map<String, Object> pathParam = new HashMap<>();
				pathParam.put("accountId", itemAccountId);

				HttpMethodParameters httpMethodParameters = HttpMethodParameters.builder().build();
				httpMethodParameters.setQueryParams(queryParams);
				httpMethodParameters.setPathParams(pathParam);

				Response updatedStatusResponse = accountUtils.updateManualOrAggregateAccount(httpMethodParameters,
						session);

				Map<String, Object> queryParamGetAcc = accountsHelper.createQueryParamsForGetAccounts(updatedStatus,
						container, String.valueOf(providerAccountId), null, accountReconType, null, null);

				HttpMethodParameters httpParams = HttpMethodParameters.builder().build();
				httpParams.setQueryParams(queryParamGetAcc);

				Response getAccntsResponse = accountUtils.getAccounts(httpParams, session);

				break;
			}
		} catch (JSONException e) {

			Assert.fail("Failed to get list of account ids");

		}

		String accountId = "";
		Map<String, Object> queryPrams = accountsHelper.createQueryParamsForGetAccounts(null, null, null, accountId,
				accountReconType, include, null);
		HttpMethodParameters httpParams = HttpMethodParameters.builder().build();
		httpParams.setQueryParams(queryPrams);

		Response getInvestmentResponse = accountUtils.getAccountInvetmentOptions(httpParams, session);
		jsonAssertionUtil.assertResponse(getInvestmentResponse, statusCode, resFile, filePath);

	}

	/*
	 * @Test(alwaysRun = true, enabled = true, dataProvider = Constants.FEEDER,
	 * priority = 3)
	 * 
	 * @Source(VALID_GET_INVESTMENT_OPTIONS_CSV_4) public void
	 * testGetInvestmentOptionsWithChangedXML(String tcid, String tcName, String
	 * container, String accountIdKey, String includeKey, String include, String
	 * status, String accountReconTypeKey, String accountReconType, String resFile,
	 * String filePath, Long defectId, String enabled) throws Exception {
	 * System.out.println("Test case Id id " + tcid + " TestCase Name is " +
	 * tcName); CommonUtils commonUtils = new CommonUtils(); AccountUtils
	 * accountUtils = new AccountUtils(); ProviderAccountUtils providerAccountUtils
	 * = new ProviderAccountUtils(); commonUtils.isTCEnabled(enabled, tcid);
	 * ProviderAccountsHelper providerAccountsHelper = new ProviderAccountsHelper();
	 * 
	 * // Creating SessionObj Configuration configurationObj =
	 * Configuration.getInstance(); EnvSession session = new
	 * EnvSession(configurationObj.getCobrandSessionObj().getCobSession(),
	 * configurationObj.getCobrandSessionObj().getUserSession(),
	 * configurationObj.getCobrandSessionObj().getPath());
	 * 
	 * 
	 * //Creating HttpParams Map<String, Object> queryParams =
	 * accountsHelper.createQueryParamsForGetAccounts(status, container,
	 * String.valueOf(providerAccountId), null, accountReconType, include, null);
	 * 
	 * 
	 * HttpMethodParameters httpParams = new HttpMethodParameters();
	 * httpParams.setQueryParams(queryParams);
	 * 
	 * Response getAccRes = accountUtils.getAccounts(httpParams, session);
	 * 
	 * String accountId = "";
	 * 
	 * 
	 * WebDriver driver = DriverFactory.getDriver(); DagHome dagHome = new
	 * DagHome(driver); dagHome.launchURL(); dagHome.catlogLogin("BankYodlee",
	 * "BankYodlee");// Catlog Login dagHome.uploadXmlFile("Investments",
	 * "investmentchange.xml"); driver.quit();
	 * 
	 * String jsonString = providerAccountsHelper.createInputForAccount(16441L,
	 * dagUN, dagPwd, "loginform", false, "");
	 * 
	 * HttpMethodParameters httpMethodParameters = new HttpMethodParameters();
	 * httpMethodParameters.setBodyParams(jsonString);
	 * 
	 * HashMap<String, String> queryParam = new HashMap<String, String>();
	 * queryParam.put("providerAccountIds", String.valueOf(providerAccountId));
	 * httpMethodParameters.setQueryParams(queryParam);
	 * 
	 * Response response =
	 * providerAccountUtils.updateProviderAccount(httpMethodParameters, session);
	 * new ProviderAccountUtils().checkRefreshStatusUntilActAddition(session,
	 * String.valueOf(providerAccountId));
	 * 
	 * 
	 * 
	 * //Creating HttpParams Map<String, Object> queryParams1 =
	 * accountsHelper.createQueryParamsForGetAccounts(status, container,
	 * String.valueOf(providerAccountId), null, accountReconType, null, null);
	 * 
	 * HttpMethodParameters httpMethodParam = new HttpMethodParameters();
	 * httpMethodParameters.setQueryParams(queryParams1);
	 * 
	 * Response getAccountsResponse1 = accountUtils.getAccounts(httpMethodParam,
	 * session);
	 * 
	 * 
	 * 
	 * Map<String, Object> queryPrams =
	 * accountsHelper.createQueryParamsForGetAccounts(null, null, null, accountId,
	 * accountReconType, include, null); HttpMethodParameters httpParams2 = new
	 * HttpMethodParameters(); httpParams.setQueryParams(queryPrams);
	 * 
	 * Response getInvestmentResponse =
	 * accountUtils.getAccountInvetmentOptions(httpParams2, session);
	 * 
	 * 
	 * JsonAssertionUtil jsonAssertionUtil = new JsonAssertionUtil();
	 * jsonAssertionUtil.assertResponse(getInvestmentResponse, HttpStatus.OK,
	 * resFile, filePath);
	 * 
	 * 
	 * WebDriver driver1 = DriverFactory.getDriver(); DagHome dagHome1 = new
	 * DagHome(driver1); dagHome1.launchURL(); dagHome1.catlogLogin("BankYodlee",
	 * "BankYodlee");// Catlog Login dagHome1.uploadXmlFile("Investments",
	 * "investmentold.xml"); driver1.quit();
	 * 
	 * String jsonString1 = providerAccountsHelper.createInputForAccount(16441L,
	 * dagUN, dagPwd, "loginform", false, ""); System.out.println("jsonString:" +
	 * jsonString1); System.out.println("Update provider accouts");
	 * 
	 * 
	 * HttpMethodParameters httpMethodParameters1 = new HttpMethodParameters();
	 * httpMethodParameters.setBodyParams(jsonString1);
	 * 
	 * HashMap<String, String> queryParam3 = new HashMap<String, String>();
	 * queryParam.put("providerAccountIds", String.valueOf(providerAccountId));
	 * httpMethodParameters.setQueryParams(queryParam3);
	 * 
	 * Response response1 =
	 * providerAccountUtils.updateProviderAccount(httpMethodParameters1, session);
	 * 
	 * new ProviderAccountUtils().checkRefreshStatusUntilActAddition(session,
	 * String.valueOf(providerAccountId));
	 * 
	 * 
	 * }
	 */

	@AfterClass(alwaysRun = true)
	public void deleteAccount() {
		// create a pathParam
		Map<String, Object> pathParam = new HashMap<>();
		pathParam.put("providerAccountId", providerAccountId);

		HttpMethodParameters httpMethodParameters = HttpMethodParameters.builder().build();
		httpMethodParameters.setPathParams(pathParam);

		Response deleteResponse = providerAccountUtils.deleteProviderAccount(httpMethodParameters,
				Configuration.getInstance().getCobrandSessionObj());

		deleteResponse.then().log().all();
	}

}
