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

import java.math.BigDecimal;
import java.time.Instant;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.databene.benerator.anno.Source;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.yodlee.DBHelper;
import com.yodlee.yodleeApi.assertions.JsonAssertionUtil;
import com.yodlee.yodleeApi.common.Base;
import com.yodlee.yodleeApi.common.Configuration;
import com.yodlee.yodleeApi.constants.Constants;
import com.yodlee.yodleeApi.constants.JSONPaths;


import com.yodlee.yodleeApi.helper.HoldingsHelper;
import com.yodlee.yodleeApi.helper.ProviderAccountsHelper;
import com.yodlee.yodleeApi.helper.UserHelper;
import com.yodlee.yodleeApi.interfaces.Session;
import com.yodlee.yodleeApi.pojo.EnvSession;
import com.yodlee.yodleeApi.pojo.HttpMethodParameters;
import com.yodlee.yodleeApi.utils.apiUtils.AccountUtils;
import com.yodlee.yodleeApi.utils.apiUtils.DerivedUtils;
import com.yodlee.yodleeApi.utils.apiUtils.HoldingUtils;
import com.yodlee.yodleeApi.utils.apiUtils.ProviderAccountUtils;
import com.yodlee.yodleeApi.utils.apiUtils.ProviderUtils;
import com.yodlee.yodleeApi.utils.apiUtils.RefreshUtils;
import com.yodlee.yodleeApi.utils.apiUtils.TransactionUtils;
import com.yodlee.yodleeApi.utils.apiUtils.UserUtils;
import com.yodlee.yodleeApi.utils.commonUtils.CommonUtils;

import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;

/**
 * @author partha sham
 *
 */
public class TestTeacaExceptional extends Base{

	static final String VALID_DIF_TIME_ZONE_FOR_USER_REG = "\\TestData\\CSVFiles\\Teaca\\User\\testUserDetails.csv";
	static final String VALID_DIF_TIME_ZONE_FOR_USER_REG1 = "\\TestData\\CSVFiles\\Teaca\\User\\testUserDetails1.csv";
	static final String VALID_GET_USER_DETAILS = "\\TestData\\CSVFiles\\User\\teacaGetUserDetails.csv";
	public static final String ADD_NON_MFA_IAV_MS = "\\TestData\\CSVFiles\\ProviderAccounts\\IAV\\addnonMFAIAVforMS.csv";
	static final String PROVIDER_SKIP_INVALID_VALUE = "\\TestData\\CSVFiles\\Teaca\\providerInvalidSkip.csv";
	static final String TEACA_GET_TXN_OLD_DATE = "\\TestData\\CSVFiles\\Teaca\\teacaGetTransactionforOldDate.csv";
	static final String TEACA_GET_PROVIDERS_VERIFY_CONTAINERS="\\TestData\\CSVFiles\\Teaca\\teacaGetProvidersVerifyContainers.csv";
	static final String TEACA_GET_HOLDINGS_AND_SEDOL="\\TestData\\CSVFiles\\Teaca\\teacaGetHoldingsIsinAndSedol.csv";
	static final String TEACA_VERIFY_UTC_TIME="\\TestData\\CSVFiles\\Teaca\\teacaVerifyUTCTime.csv";
	static final String TEACA_DELETE_PROVIDER_ACCOUNT="\\TestData\\CSVFiles\\Teaca\\teacaDeleteProviderAccount.csv";
	String json;
	static String newUserSession;
	int status = 200;
	private long providerAccountId;
	String userName;
	String userPassWord;

	/*
	 * 1.Bug#-808942 1. Register user with different timezone and verify the
	 * same with getuser API. GMT,AEST, UTC and PST
	 * AT-42594
	 */
	@Test(enabled = true, dataProvider = Constants.FEEDER, alwaysRun = true, priority = 1)
	@Source(VALID_DIF_TIME_ZONE_FOR_USER_REG)
	public void teacaUserRegistrationWithdifferentTimeZone(String TCId, String TCTitle, String password, String email,
														   String firstName, String lastName, String address1, String address2, String state, String city, String zip,
														   String country, String currency, String timeZone, String dateFormat, int status, String ResFile,
														   String FilePath, String Enabled, String Track,String DefectID) {
		CommonUtils commonUtils = new CommonUtils();
		JsonAssertionUtil jsonAssertionUtil = new JsonAssertionUtil();
		UserUtils userUtils = new UserUtils();
		UserHelper userHelper =  new UserHelper();
		Configuration configuration = Configuration.getInstance();
		commonUtils.isTCEnabled(Enabled, TCId);
		System.out.println("*****teacaUserRegistrationWithdifferentTimeZone Starts*****");
		if (Track.equals("FALSE")) {
			String loginName = "YSL" + System.currentTimeMillis();
			userName = loginName;
			userPassWord = password;
			json = userHelper.createUserRegistrationObject(password, email, firstName, lastName, address1, address2, state, city, zip, country, currency, timeZone, dateFormat);
			System.out.println("User Registration file is ready " + json);
			HttpMethodParameters httpParams =  HttpMethodParameters.builder().build();
			httpParams.setBodyParams(json);
			// Register the user
			Response response = (Response) userUtils.userRegistrationResponse(configuration.getCobrandSessionObj(), httpParams);
			System.out.println("Response after the registering user" + response.prettyPrint());
			newUserSession = response.jsonPath().getString("user.session.userSession");
			System.out.println("newUserSession ::"+newUserSession);
			
			Session session = Configuration.getInstance().getCobrandSessionObj();
			
			
			session.setUserSession(newUserSession);
			System.out.println("session.getUserSession() :"+session.getUserSession());
			
			EnvSession envSession=EnvSession.builder().build();
			envSession.setCobSession(configuration.getCobrandSessionObj().getCobSession());
			envSession.setPath(configuration.getCobrandSessionObj().getPath());
			envSession.setUserSession(newUserSession);
			System.out.println("envSession.getUserSession()"+ envSession.getUserSession());
			
			// Get the user and verify the time zone
			Response getUserDetails = userUtils.getUserDetails(envSession);
			getUserDetails.then().log().all();
			jsonAssertionUtil.assertJSON(getUserDetails, status, ResFile, FilePath);
		}
		System.out.println("*****teacaUserRegistrationWithdifferentTimeZone Ends*****");
	}

	/*
	 * Bug#815937 2. getTransactions() should return response if fromDate set to
	 * > 100 years pre-requisites 1. Cobrand login and user login 2. Get
	 * transaction using > 100 years ; Example
	 * /transactions?fromDate=2100-01-01: Response should be empty
	 */
	@Test(enabled = true, priority = 2,dataProvider=Constants.FEEDER)
	@Source(TEACA_GET_TXN_OLD_DATE)
	public void teacaGetTransactionforOldDate(String testcaseId, String description, String defectId, String enabled) {
		
		CommonUtils commonUtils = new CommonUtils();
		TransactionUtils transactionUtils = new TransactionUtils();
		Configuration configuration = Configuration.getInstance();
		commonUtils.isTCEnabled(enabled, testcaseId);
		System.out.println("*****teacaGetTransactionforOldDate Starts*****");
		Map<String, String> queryParam = new HashMap<>();
		queryParam.put("fromDate", "2120-01-01");
		HttpMethodParameters httpParam = HttpMethodParameters.builder().build();
		httpParam.setQueryParams(queryParam);
		Response getTransDetails = transactionUtils.getAllTransactions(httpParam, configuration.getCobrandSessionObj());
		getTransDetails.then().log().all();

		ValidatableResponse res = getTransDetails.then();
		System.out.println("EMPTY JSON Length is" + getTransDetails.prettyPrint().length());
		// compare empty response
		res.log().all();
		res.statusCode(status);
		int length = getTransDetails.prettyPrint().length();

		if (length < 8 || length > 8) {
			System.out.println("IT SHOULD FAIL");
			Assert.fail();
		}
		System.out.println("*****teacaGetTransactionforOldDate Ends*****");
	}

	/*
	 * Bug#832260 /providers/ If containersName is null then test case should
	 * fail-- Verifying null containers
	 */
	@Test(enabled = true,dataProvider=Constants.FEEDER)
	@Source(TEACA_GET_PROVIDERS_VERIFY_CONTAINERS)
	public void teacaGetProvidersVerifyContainers(String testcaseId,String description,String defectId, String enabled) {
		
		CommonUtils commonUtils = new CommonUtils();
		Configuration configuration = Configuration.getInstance();
		ProviderUtils providerUtils = new ProviderUtils();
		commonUtils.isTCEnabled(enabled, testcaseId);
		System.out.println("*****teacaGetProvidersVerifyContainers Starts*****");
		List list = new ArrayList();
		Map<String, Object> mapPath = new HashMap<>();
		HttpMethodParameters httpParam = HttpMethodParameters.builder().build();
		System.out.println("Started teacaGetProvidersVerifyContainers");
		for (int i = 0; i < configuration.getDefaultSites().length - 1; i++) {
			mapPath.put("providerId", configuration.getDefaultSites()[i]);
			httpParam.setPathParams(mapPath);
			Response response = providerUtils.getProviderDetails(httpParam, configuration.getCobrandSessionObj());
			System.out.println("Response after calling providers " + response.prettyPrint());
			System.out.println("TRACK STARTS");
			JSONObject jp = new JSONObject(response.prettyPrint());
			System.out.println("TRACK");
			String res = response.prettyPrint();
			System.out.println("RES and LENGTH " + res.length() + " " + res);
			if (res.length() > 9) {
				JSONObject containersNames = jp.getJSONArray(Constants.PROVIDER).getJSONObject(0);// .getJSONArray("containerNames");
				JSONArray jarray = containersNames.getJSONArray("containerNames");
				for (Object value : jarray) {
					System.out.println("FINAL " + value.toString());
					if (null == value.toString()) {
						Assert.fail();
					} else
						list.add(value.toString());
				}
			}
			System.out.println("CONTAINERS are " + list);
		}
		System.out.println("*****teacaGetProvidersVerifyContainers Ends*****");

	}

	/*
	 * Bug#856994 getUser API. Verify response. Should not miss any attributes
	 * Two test cases added
	 * AT-42615
	 */

	@Test(enabled = true, priority = 2, dataProvider = Constants.FEEDER)
	@Source(VALID_GET_USER_DETAILS)
	public void teacaGetUserDetails(String testCaseId, String testcaseName, int status, String responseFile,
									String filePath, String enabled) {
		
		CommonUtils commonUtils = new CommonUtils();
		Configuration configuration = Configuration.getInstance();
		UserUtils userUtils = new UserUtils();
		JsonAssertionUtil jsonAssertionUtil = new JsonAssertionUtil();
		commonUtils.isTCEnabled(enabled, testCaseId);
		System.out.println("*****teacaGetUserDetails Starts*****");
		Response response = null;

		System.out.println("VALUES from the source file" + testCaseId + " " + testcaseName + " " + responseFile + " " + filePath);
		System.out.println("Trying to get user details");
		if (testcaseName.equals("SimpleGetUerDetails")) {
			response = userUtils.getUserDetails(configuration.getCobrandSessionObj());
			System.out.println("The response is " + response.prettyPrint());
			jsonAssertionUtil.assertJSON(response, status, responseFile, filePath);
		} else {
			response =userUtils.getUserDetails(configuration.getCobrandSessionObj());
			System.out.println("The response with different user " + response.prettyPrint());
			jsonAssertionUtil.assertJSON(response, status, responseFile, filePath);
		}
		System.out.println("*****teacaGetUserDetails Ends*****");
	}

	/*
	 * Bug#832699 Add IVA account with correct details. Later update with
	 * invalid value of ID from loginform . It should throw Y800.
	 *
	 */
	//And
	/*
	 * Bug#883315 For IAV Add Account and Update Account call the
     * IAV_REQUEST_TYPE_ID in IAV_DATA_REQUEST_EXT should be set to 1
	 * AT-42601,AT-42622
	 */
	@Test(dataProvider = Constants.FEEDER, priority = 3, enabled = true)
	@Source(ADD_NON_MFA_IAV_MS)
	public void teacaAddAccountAndUpdateWithWrongIDValue(String testCaseId, String testCaseName,String providerId,
														 String loginFormType, String dagUserName, String dagPassword, int status, String includeDataSet,
														 boolean isincludeDataSet, String addAccountResFile, String filePath, String enabled,String DefectID) throws JSONException {
		
		CommonUtils commonUtils = new CommonUtils();
		commonUtils.isTCEnabled(enabled, testCaseId);
		ProviderAccountsHelper providerAccountsHelper = new ProviderAccountsHelper();
		ProviderAccountUtils providerAccountUtils = new ProviderAccountUtils();
		DBHelper dbHelper = new DBHelper();
		Configuration configuration = Configuration.getInstance();
		
		System.out.println("*****teacaAddAccountAndUpdateWithWrongIDValue Starts*****");
		System.out.println("********************* NON MFA ACCOUNT ADDITION-IAV ********************");
		System.out.println("Executing test case: " + testCaseName);
		Map<String, Object> mapQuery = new HashMap<>();
		mapQuery.put("providerId", providerId);		
		String jsonInput = providerAccountsHelper.createInputForAccount(Long.valueOf(providerId), dagUserName, dagPassword, loginFormType, false, "");
		HttpMethodParameters httpParams = HttpMethodParameters.builder().build();
		httpParams.setBodyParams(jsonInput);
		httpParams.setQueryParams(mapQuery);
		Response getProviderAccountResponse = providerAccountUtils.addProviderAccount(httpParams, configuration.getCobrandSessionObj());
		providerAccountId = getProviderAccountResponse.jsonPath().getLong(JSONPaths.PROVIDER_ACC_ID);
		//commonUtils.assertStatusCode(getProviderAccountResponse, status);
		System.out.println("provider account id:" + providerAccountId);

		// Verify the IAV_DATA_REQUEST_EXT table bug#883315
		String query = "select * from IAV_DATA_REQUEST_EXT where mem_site_acc_id=" + providerAccountId;
		try {
			BigDecimal ivaRequestTypeId = dbHelper.getValueFromDB(query,"IAV_REQUEST_TYPE_ID");
			System.out.println("IVA REQUEST TYPE ID VALUE after adding account  :" + ivaRequestTypeId);
			int ivaRequestTypeId1 = Integer.valueOf(ivaRequestTypeId.intValue());
			System.out.println("Asserting values of IVA_REQUEST_TYPE_ID should be 1 after adding account");
			Assert.assertEquals(1, ivaRequestTypeId1);

		}catch(Exception e) {
			e.printStackTrace();
		}
		LinkedHashMap<String, String> queryParam = new LinkedHashMap<>();
		queryParam.put("providerAccountIds", String.valueOf(providerAccountId));
		HttpMethodParameters httpMethodParameters = HttpMethodParameters.builder().build();
		httpMethodParameters.setQueryParams(queryParam);
		Response updateAccountResponse = providerAccountUtils.updateProviderAccount(httpMethodParameters, configuration.getCobrandSessionObj());

		System.out.println("THE RESPONSE after the update account with valid details" + updateAccountResponse.prettyPrint());
		query = "select * from IAV_DATA_REQUEST_EXT where mem_site_acc_id=" + providerAccountId;
		try {
			BigDecimal ivaRequestTypeId = dbHelper.getValueFromDB(query,"IAV_REQUEST_TYPE_ID");

			System.out.println("IVA REQUEST TYPE ID VALUE AFTER Updating IVA account  :" + ivaRequestTypeId);
			int ivaRequestTypeId1 = Integer.valueOf(ivaRequestTypeId.intValue());
			System.out.println("Asserting values of IVA_REQUEST_TYPE_ID should be 1 after Updating account");

			Assert.assertEquals(1, ivaRequestTypeId1);

		}catch(Exception e) {
			e.printStackTrace();
		} 
		jsonInput = providerAccountsHelper.createInputForAccount(Long.valueOf(providerId), dagUserName, dagPassword, loginFormType,isincludeDataSet, includeDataSet);
		jsonInput = jsonInput.replace("65499", "abc");
		System.out.println("JSON FILE CHECKPOINT " + jsonInput);

		// update with wrong cred expecting y800 eror.
		queryParam = new LinkedHashMap<>();
		Long providerAccId = null;
		queryParam.put("providerAccountIds", String.valueOf(providerAccountId));
		httpMethodParameters = HttpMethodParameters.builder().build();
		httpMethodParameters.setQueryParams(queryParam);
		updateAccountResponse = providerAccountUtils.updateProviderAccount(httpMethodParameters, configuration.getCobrandSessionObj());

		System.out.println("THE RESPONSE after the update account with invalid details" + updateAccountResponse.prettyPrint());
		System.out.println("The Vaue is  " + updateAccountResponse.statusCode());
		System.out.println("ERROR CODE" + updateAccountResponse.jsonPath().getString("errorCode"));
		if (!updateAccountResponse.jsonPath().getString("errorCode").equals("Y800")) {
			Assert.fail();
		}
		System.out.println("*****teacaAddAccountAndUpdateWithWrongIDValue Ends*****");
	}

	/*
	 * Bug#771012 1 User registration is not working when we set the country as
	 * USA 2 Content type validation issue in Update password API
	 * AT-42589
	 */
	@Test(enabled = true, dataProvider = Constants.FEEDER,priority=4)
	@Source(VALID_DIF_TIME_ZONE_FOR_USER_REG1)
	public void teacaUserRegWithUsaAndContentType(String TCId, String TCTitle, String password, String email,
												  String firstName, String lastName, String address1, String address2, String state, String city, String zip,
												  String country, String currency, String timeZone, String dateFormat, int status, String ResFile,
												  String FilePath, String Enabled, String Track, String DefectID) {
		
		CommonUtils commonUtils = new CommonUtils();
		UserHelper userHelper = new UserHelper();
		Configuration configuration = Configuration.getInstance();
		UserUtils userUtils = new UserUtils();
		commonUtils.isTCEnabled(Enabled, TCId);
		System.out.println("*****teacaUserRegWithUsaAndContentType Starts*****");
		if (Track.equals("TRUE")) {
			String loginName = "YSL" + System.currentTimeMillis();
			json = userHelper.createUserRegistrationObject(loginName, password, email, firstName, lastName, address1, state, city, zip, country, currency, timeZone, dateFormat);
			System.out.println("User Registration file is ready " + json);
			HttpMethodParameters httpParams =  HttpMethodParameters.builder().build();
			httpParams.setBodyParams(json);
			// Register the user
			Response response = (Response) userUtils.userRegistrationResponse(configuration.getCobrandSessionObj(), httpParams);
			System.out.println("Response after the registering user" + response.prettyPrint());
			newUserSession = response.jsonPath().getString("user.session.userSession");
			String[] contentType = { "application/json", "text/plain", "text/html", "application/javascript" };
			String[] newPasswords = { "Abcd@123", "Wxyz@123", "Wnop@123", "Wrst@123" };

			for (int i = 0; i < contentType.length; i++) {
				String newPassword = newPasswords[i];
				json = userHelper.updatePasswordwithOldPasswordJSON(loginName, password, newPassword);
				httpParams.setBodyParams(json);
				response = userUtils.updateUserPassword(configuration.getCobrandSessionObj(), httpParams);
				System.out.println("After updating password and contentType " + response.prettyPrint());
				password = newPassword;
			}
		}
		System.out.println("*****teacaUserRegWithUsaAndContentType Ends*****");
	}

	/*
	 *
	 * Bug#837517 ISIN and SEDOL fields should be populated for getHoldings API
	 * if available for the user Data Approach- Verifying two attributes in this
	 * method
	 */
	@Test(enabled = true, priority = 5,dataProvider=Constants.FEEDER)
	@Source(TEACA_GET_HOLDINGS_AND_SEDOL)
	public void teacaGetHoldingsIsinAndSedol(String testcaseId,String description,String defectId, String enabled) {
		
		CommonUtils commonUtils = new CommonUtils();
		ProviderAccountUtils providerAccountUtils =  new ProviderAccountUtils();
		ProviderAccountsHelper providerAccountsHelper = new ProviderAccountsHelper();
		HoldingUtils holdingUtils = new HoldingUtils();
		HoldingsHelper holdingsHelper = new HoldingsHelper();
		DerivedUtils derivedUtils = new DerivedUtils();
		Configuration configuration = Configuration.getInstance();
		commonUtils.isTCEnabled(enabled, testcaseId);
		System.out.println("******teacaGetHoldongIsinAndSedol Starts*****");
		String strProviderId = "16441";
		// add account
		Map<String, Object> mapQuery = new HashMap<>();
		mapQuery.put("providerId", strProviderId);		
		String jsonInput = providerAccountsHelper.createInputForAccount(Long.valueOf(strProviderId), "assetdaguser11.site16441.31", "site16441.31", "loginForm", false, "");
		HttpMethodParameters httpParams = HttpMethodParameters.builder().build();
		httpParams.setBodyParams(jsonInput);
		httpParams.setQueryParams(mapQuery);
		Response getProviderAccountResponse = providerAccountUtils.addProviderAccount(httpParams, configuration.getCobrandSessionObj());
		providerAccountId = getProviderAccountResponse.jsonPath().getLong(JSONPaths.PROVIDER_ACC_ID);
		//commonUtils.assertStatusCode(getProviderAccountResponse, status);
		System.out.println("provider account id:" + providerAccountId);
		Response response = holdingUtils.getHoldings(null, configuration.getCobrandSessionObj());
		System.out.println("The holdings Response is " + response.prettyPrint());
		System.out.println("The values are" + response.jsonPath().getString("holding.sedol"));
		String sedolValues = response.jsonPath().getString("holding.sedol");
		String isinValues = response.jsonPath().getString("holding.isin");
		Assert.assertNotNull(sedolValues);
		Assert.assertNotNull(isinValues);

		// holding summary.
		Response response1 = derivedUtils.getDerivedHoldingSummary(null, configuration.getCobrandSessionObj());
		System.out.println("The holdings Response is " + response.prettyPrint());
		System.out.println("The values are" + response.jsonPath().getString("holding.sedol"));
		String sedolValues1 = response.jsonPath().getString("holding.sedol");
		String isinValues1 = response.jsonPath().getString("holding.isin");
		Assert.assertNotNull(sedolValues1);
		Assert.assertNotNull(isinValues1);

		System.out.println("******teacaGetHoldongIsinAndSedol Ends*****");
	}

	/*
	 * Bug#829819 There is should be no limit set for skip parameter in
	 * getProviders() API
	 */
	@Test(enabled = true, dataProvider = Constants.FEEDER,priority=6)
	@Source(PROVIDER_SKIP_INVALID_VALUE)
	public void teacaGetProvidersWithSkip(String testCaseId, String testCase, String value, String errorCode,
										  String errorMessage, String defectId, String enabled) {
		
		CommonUtils commonUtils = new CommonUtils();
		Configuration configuration = Configuration.getInstance();
		ProviderUtils providerUtils = new ProviderUtils();
		commonUtils.isTCEnabled(enabled, testCaseId);
		System.out.println("*****teacaGetProvidersWithSkip Starts*****");
		System.out.println("TEST CASE EXECUTION ::" + testCase);
		System.out.println("WITHOUT SKIP");
		Response response = providerUtils.getProviderDetails(null, configuration.getCobrandSessionObj());

		Map<String, String> params = new HashMap<String, String>();
		params.put("skip", value);
		HttpMethodParameters httpMethodParameters = HttpMethodParameters.builder().build();
		httpMethodParameters.setQueryParams(params);
		System.out.println("WITH SKIP");
		if(value.equals("-1")){
			response = providerUtils.getProviders(httpMethodParameters, configuration.getCobrandSessionObj());
			System.out.println("The response after invalid value of skip" + response.prettyPrint());
			Assert.assertEquals(errorCode, response.jsonPath().getString("errorCode"));
			Assert.assertEquals(errorMessage, response.jsonPath().getString("errorMessage"));
			System.out.println("*****teacaGetProvidersWithSkip Ends*****");
		}else{
			response = providerUtils.getProviders(httpMethodParameters, configuration.getCobrandSessionObj());
			System.out.println("The response after Valid value of skip" + response.prettyPrint());
			//It take care to pass or fail
			JSONObject res = new JSONObject(response.prettyPrint());
			JSONArray getProvider = res.getJSONArray("provider");
		}
	}
	/*
	 * Bug#887661 In getAccounts and getAccountDetails() Api the timestamp for
	 * the " "lastRefreshed" "lastRefreshAttempt" "nextRefreshScheduled" fields
	 * should be in UTC format
	 *
	 * Steps involved. 1. Refresh with POST 2. Refresh with GET 3. Get Accounts
	 * for bank container 4. Get provier accounts 5. Verify UTC time for
	 * lastRefresh, Attempt and NextRefresh
	 *
	 */
	// add acount is doing in " teacaGetHoldingsIsinAndSedol " so it is depends on that method
	@Test(enabled = true,dependsOnMethods="teacaGetHoldingsIsinAndSedol",priority=7,dataProvider=Constants.FEEDER)
	@Source(TEACA_VERIFY_UTC_TIME)
	public void teacaVerifyUTCTime(String testcaseId,String description, String defectId, String enabled) {
		
		CommonUtils commonUtils = new CommonUtils();
		commonUtils.isTCEnabled(enabled, testcaseId);
		AccountUtils accountUtils = new AccountUtils();
		RefreshUtils refreshUtils = new RefreshUtils();
		ProviderAccountUtils providerAccountUtils = new ProviderAccountUtils();
		Configuration configuration = Configuration.getInstance();
		System.out.println("*****teacaVerifyUTCTime Starts*****");
		String utcTime = Instant.now().toString().trim();
		System.out.println("The UTC time is Before Trimming " + utcTime);
		// 0 to 12 char taking from utc time. ignoring seconds.
		// Removing SECONDS --This is benchmark to verify UTC time
		utcTime = utcTime.substring(0, 13);
		System.out.println("The UTC time is After Trimming " + utcTime);
		Map<String, Object> mapPath = new HashMap<>();
		mapPath.put("providerAccountId", providerAccountId);
		HttpMethodParameters httpParams = HttpMethodParameters.builder().build();
		httpParams.setPathParams(mapPath);		
		Response response = refreshUtils.getRefreshProviderAccount(httpParams, configuration.getCobrandSessionObj());
		System.out.println("Before  Refresh " + response.prettyPrint());
		response = refreshUtils.postRefreshProviderAccount(httpParams, configuration.getCobrandSessionObj());
		System.out.println("After  Refresh POST::response " + response.prettyPrint());
		response = refreshUtils.getRefreshProviderAccount(httpParams, configuration.getCobrandSessionObj());
		System.out.println("After  Refresh GET:: response " + response.prettyPrint());
		Map<String, String> params = new HashMap<String, String>();
		params.put("container", "bank");
		HttpMethodParameters httpParam = HttpMethodParameters.builder().build();
		httpParam.setQueryParams(params);
		// GET accounts API Calling with container name bank
		response = accountUtils.getAccounts(httpParam, configuration.getCobrandSessionObj());
		System.out.println("Account GET:: response " + response.prettyPrint());

		String accountLastRefreshed = response.jsonPath().getString("account.refreshinfo.lastRefreshed");
		String accountRefreshedAttempt = response.jsonPath().getString("account.refreshinfo.lastRefreshAttempt");
		String accountNextRefresh = response.jsonPath().getString("account.refreshinfo.nextRefreshScheduled");
		// Removing SECONDS
		accountLastRefreshed = accountLastRefreshed.substring(1, 14);
		accountRefreshedAttempt = accountRefreshedAttempt.substring(1, 14);
		accountNextRefresh = accountNextRefresh.substring(1, 14);
		System.out.println("After trimming values are " + accountLastRefreshed + " " + accountRefreshedAttempt + " "
				+ accountNextRefresh);

		// GET- providerAccounts API calling with provider account id
		Map<String, Object> mapPathParam = new HashMap<>();
		mapPathParam.put("providerAccountId", providerAccountId);
		HttpMethodParameters httpMethodParameters = HttpMethodParameters.builder().build();
		httpMethodParameters.setPathParams(mapPathParam); 
		response = providerAccountUtils.getProviderAcctDetails(httpMethodParameters, configuration.getCobrandSessionObj());
		System.out.println("ProviderAccounts GET:: response " + response.prettyPrint());

		String ProviderLastRefreshed = response.jsonPath().getString("providerAccount.refreshInfo.lastRefreshed");
		String ProviderRefreshedAttempt = response.jsonPath()
				.getString("providerAccount.refreshInfo.lastRefreshAttempt");
		String ProviderNextRefresh = response.jsonPath().getString("providerAccount.refreshInfo.nextRefreshScheduled");
		// Removing SECONDS
		ProviderLastRefreshed = ProviderLastRefreshed.substring(0, 13);
		ProviderRefreshedAttempt = ProviderRefreshedAttempt.substring(0, 13);
		ProviderNextRefresh = ProviderNextRefresh.substring(0, 13);
		System.out.println("After trimming values are " + ProviderLastRefreshed + " " + ProviderRefreshedAttempt + " "
				+ ProviderNextRefresh);
		/*
		 * verifying UTC time. Trimmed refreshed Value: removed seconds from the
		 * UTC time
		 */
		System.out.println("Comparing UTC time");
		Assert.assertEquals(ProviderRefreshedAttempt, ProviderLastRefreshed);
		Assert.assertEquals(accountRefreshedAttempt, accountLastRefreshed);
		Assert.assertEquals(utcTime, ProviderLastRefreshed);
		Assert.assertEquals(utcTime, accountLastRefreshed);

		System.out.println("*****teacaVerifyUTCTime Ends*****");

	}

	/*
	 * Bug#837996
	 * Call delete Provider Account and check in
	 *  DB ( in mem_site_acc table is_disabled should be set to 1 and in
	 *  item_account table status should be set to 3 for all accounts )
	 *
	 *  Steps:
	 *  1. Delete Provider Account
	 *  2. Connect to DB
	 *  3. Fetch the column info
	 *  4. Verify
	 */
	@Test(enabled=true,dependsOnMethods="teacaVerifyUTCTime",dataProvider=Constants.FEEDER)
	@Source(TEACA_DELETE_PROVIDER_ACCOUNT)
	public void teacaDeleteProviderAccount(String testcaseId,String description, String defectId, String enabled){
		
		CommonUtils commonUtils = new CommonUtils();
		Configuration configuration = Configuration.getInstance();
		ProviderAccountUtils providerAccountUtils = new ProviderAccountUtils();
		DBHelper dbHelper = new DBHelper();
		commonUtils.isTCEnabled(enabled, testcaseId);
		System.out.println("*****teacaDeleteProviderAccount Start*****");
		Map<String, Object> mapPath = new HashMap<>();
		mapPath.put("providerAccountId", providerAccountId);
		HttpMethodParameters httpParams = HttpMethodParameters.builder().build();
		httpParams.setPathParams(mapPath);
		Response response = providerAccountUtils.deleteProviderAccount(httpParams, configuration.getCobrandSessionObj()); 
		System.out.println("Status code after deleting the Provider Account"+response.statusCode());
		System.out.println("After delete provider accounts"+response.prettyPrint());

		String query = "select * from mem_site_acc where mem_site_acc_id=" + providerAccountId;
		String query1 = "select * from item_account where cache_item_id in ( select cache_item_id from server_stats where mem_site_acc_id="+providerAccountId+")";
		try {
			BigDecimal is_disabled = dbHelper.getValueFromDB(query,"is_disabled");
			System.out.println("Is DISABLED VALUE  :" + is_disabled);
			int disabledValue = Integer.valueOf(is_disabled.intValue());
			Assert.assertEquals(1, disabledValue);
			BigDecimal item_account_status_id = dbHelper.getValueFromDB(query1,"item_account_status_id");
			int itemAccountStatusId = Integer.valueOf(item_account_status_id.intValue());
			System.out.println("Item Account Status id VALUE  :" + item_account_status_id);
			Assert.assertEquals(3, itemAccountStatusId);
		}catch(Exception e) {
			e.printStackTrace();
			Assert.assertEquals(1, 2);
		}
		System.out.println("*****teacaDeleteProviderAccount Ends*****");
	}
}
