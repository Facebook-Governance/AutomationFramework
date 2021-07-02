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

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import org.databene.benerator.anno.Source;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import com.yodlee.yodleeApi.assertions.JsonAssertionUtil;
import com.yodlee.yodleeApi.common.Base;
import com.yodlee.yodleeApi.common.Configuration;
import com.yodlee.yodleeApi.common.LoginFormFactory;
import com.yodlee.yodleeApi.constants.Constants;
import com.yodlee.yodleeApi.constants.HttpStatus;
import com.yodlee.yodleeApi.constants.JSONPaths;
import com.yodlee.yodleeApi.helper.AccountsHelper;
import com.yodlee.yodleeApi.helper.ProvidersHelper;
import com.yodlee.yodleeApi.helper.TransactionsHelper;
import com.yodlee.yodleeApi.pojo.HttpMethodParameters;
import com.yodlee.yodleeApi.utils.apiUtils.AccountUtils;
import com.yodlee.yodleeApi.utils.apiUtils.ProviderAccountUtils;
import com.yodlee.yodleeApi.utils.commonUtils.CommonUtils;
import com.yodlee.yodleeApi.utils.commonUtils.PropertyFileUtil;

import io.restassured.response.Response;

/**
 * 
 * Test Class that verifies Get Accounts & Get Account Details functionality.
 * 
 * @author bhuvaneshwari
 *
 */
public class TestGetAccountDetails extends Base {

	public List<Long> accountIdList;
	
	public Map<String, Map<String, Long>> containerMap = new HashMap<String, Map<String, Long>>();
	long deletedAccountId = 0;
	public List<Long> updatedAccountList = new ArrayList<>();
	public Set<Long> updatedAccountSet = new HashSet<>();
	public static Long providerAccountId1 = null;
	CommonUtils commonUtils = new CommonUtils();
	TransactionsHelper transactionsHelper = new TransactionsHelper();
	AccountUtils accountUtils = new AccountUtils();
	AccountsHelper accountsHelper = new AccountsHelper();
	JsonAssertionUtil jsonAssertionUtil = new JsonAssertionUtil();
	ProviderAccountUtils providerAccountUtils = new ProviderAccountUtils();
	Configuration configurationObj = Configuration.getInstance();
	PropertyFileUtil propertyFileUtilObj = new PropertyFileUtil();
	SoftAssert softAssert = new SoftAssert();



	public Map<String, String> accNameAccNumberMap = new HashMap<String, String>();

	/**
	 * Before class that adds an account.
	 */
	@BeforeClass(alwaysRun = true)
	public void setUpTest() {
		if (configurationObj.isUserStatic() && !configurationObj.isFirstRun()) {

			Properties properties = propertyFileUtilObj.loadParameters("providerAccountId.properties");
			providerAccountId1 = Long.parseLong(properties.getProperty(getClassName()));
			System.out.println("providerAccountId111:" + providerAccountId1);
		} else {
			logger.info("********* EXECUTING BEFORE CLASS FOR ACTIVE STATUS*****************");
			long providerId = 16441;
			providerAccountId1 = providerId;

			try {				
				Response response = providerAccountUtils.addProviderAccountStrict(providerId, Constants.SIMPLIFIED_FORM,
						"GetAccountDetails1.site16441.1", "site16441.1", configurationObj.getCobrandSessionObj());
				providerAccountId1 = response.jsonPath().getLong(JSONPaths.PROVIDER_ACC_ID);
				System.out.println("providerAccountId1::::===" + providerAccountId1);

				if (configurationObj.isHybrid()) {
					providerAccountId1 = response.jsonPath().getLong("providerAccount[0].id");

				} else {
					providerAccountId1 = response.jsonPath().getLong("providerAccount.id");
				}
				System.out.println("providerAccountId1::::===" + providerAccountId1);

				if (configurationObj.isUserStatic()) {
					propertyFileUtilObj.writePropertyFile(getClassName(),
							String.valueOf(accountsHelper.providerAccountId));
				}
				logger.info("Provider Account Id : " + accountsHelper.providerAccountId);
			} catch (JSONException e) {
				e.printStackTrace();
			}

		}
		loadMap();
	}

	/**
	 * Test Get ACCOUNT Details API with all valid scenarios.
	 *
	 * @param tcId
	 *            Test case id taken from CSV file.
	 * @param tcName
	 *            Test case name taken from CSV file.
	 * @param container
	 *            Container value taken from CSV file.
	 * @param filePath
	 *            filepath where JSON file is located.
	 * @param resFile
	 *            JSON file name.
	 * @throws FileNotFoundException
	 */
	@Test(dataProvider = "feeder", alwaysRun = true, priority = 13)
	@Source("\\TestData\\CSVFiles\\Accounts\\GetAccountDetails\\testGetAccountDetailsPositiveScenarios.csv")
	public void testGetAccountDetailsPositiveScenarios(String tcId, String tcName, String container, String accountName,
			String filePath, String resFile, Long defectId, String ignoreKeys, String enabled) throws FileNotFoundException {
		commonUtils.isTCEnabled(enabled, tcId);
		System.out.println("TestCase id is " + tcId);
		
		/* * Response getAccountsResponse =
		 * ApiUtils.getAccountSummary(Constants.ACTIVE_STATUS, container,
		 * String.valueOf(ProviderAccountUtils.providerAccountId),
		 * Constants.EMPTY_STRING, InitialLoader.cobSessionObj);*/
		HttpMethodParameters httpParams = HttpMethodParameters.builder().build();
		Map<String, Object> queryParams = accountsHelper.createQueryParamsForGetAccounts(Constants.ACTIVE_STATUS,
				container, String.valueOf(providerAccountId1), null, null, null, null);
		httpParams.setQueryParams(queryParams);

		Response getAccountsResponse = accountUtils.getAccounts(httpParams, configurationObj.getCobrandSessionObj());

		String[] listAccountName = accountName.split(",");

		String[] listJsonName = resFile.split(",");

		accountIdList = new ArrayList<>();
		try {
			JSONObject object = new JSONObject(getAccountsResponse.asString());
			JSONArray accountArray = object.getJSONArray(JSONPaths.ACCOUNT);
			int length = accountArray.length();
			System.out.println("No of Accounts are : " + length);
			for (int count = 0; count < accountArray.length(); count++) {
				JSONObject accountObject = accountArray.getJSONObject(count);
				long itemAccountId = accountObject.getLong(Constants.ID);
				String aName1 = accountObject.getString("accountName");
				System.out.println("Account Name at " + count + " is " + aName1);
				accountIdList.add(itemAccountId);
				// map.put(listAccountName[count], itemAccountId);
			}

		} catch (JSONException e) {
			Assert.fail("Failed to get list of account ids ");
		}

		if (accountIdList != null || !accountIdList.isEmpty())
			for (long accountId : accountIdList) {

				
				/* * * Response getAccountDetailsResponse = ApiUtils.getAccountDetails(container,
				 * accountId, InitialLoader.cobSessionObj);*/
				 

				LinkedHashMap<String, String> queryParam = new LinkedHashMap<String, String>();
				queryParam.put("container", container);
				Map<String, Object> pathParams = new HashMap<>();
				pathParams.put("accountId", accountId);
				HttpMethodParameters httpParam = HttpMethodParameters.builder().build();
				httpParam.setPathParams(pathParams);
				httpParam.setQueryParams(queryParam);

				Response getAccountDetailsResponse = accountUtils.getAccountDetailsForAccountId(httpParam,
						configurationObj.getCobrandSessionObj());
				System.out.println("Response::::" + getAccountDetailsResponse.asString());
				String aName = getAccountDetailsResponse.jsonPath().getString("account[0].accountName");
				System.out.println("Account Name in Response is " + aName);
				for (int x = 0; x < listAccountName.length; x++) {
					if (aName.equalsIgnoreCase(listAccountName[x])) {
						System.out.println("Json is " + listJsonName[x]);

						String accNum = "";
						if (configurationObj.getMaskingEnabled()) {

							commonUtils.saveJson(getAccountDetailsResponse.prettyPrint(), filePath, listJsonName[x]);
							String jsonFile = commonUtils.readJsonFile(commonUtils.getPath(filePath + listJsonName[x]));
							JSONObject obj = new JSONObject(jsonFile);
							JSONArray accArray = obj.getJSONArray(JSONPaths.ACCOUNT);

							for (int c = 0; c < accArray.length(); c++) {

								String accName = accArray.getJSONObject(c).getString("accountName");
								System.out.println("accName passed is::" + accName);
								if (!accName.equalsIgnoreCase("YODLEE")) {
									accNum = accMasking(accNum);
								}

								if (accArray.getJSONObject(c).has("accountNumber"))
									accArray.getJSONObject(c).put("accountNumber", accNum);
								
								
								
							/*	if (container.equalsIgnoreCase("insurance")) {
									if (accArray.getJSONObject(c).has("includeInNetWorth")) {
										System.out.println("entered 1st");
										accArray.getJSONObject(c).remove("includeInNetWorth");
									}
									if (accArray.getJSONObject(c).has("includeInNetworth")) {
										System.out.println("entered 2nd");
										accArray.getJSONObject(c).remove("includeInNetworth");
									}
									if (accArray.getJSONObject(c).has("includeInNetWorth")) {
										System.out.println("includeInNetWorth cond::::::");
										accArray.getJSONObject(c).put("includeInNetWorth",
												configurationObj.isNetworthEnabled());
									}
								}*/
								System.out.println("includeInNetWorth::::"+ configurationObj.getNetworthEnabled());
								
								System.out.println("Account Object " + c + "is " + accArray.getJSONObject(c).toString());
							}

							String jsonString = obj.toString();

							System.out.println(" JSON File Data " + jsonString);
							
							System.out.println("API Response Data " + getAccountDetailsResponse.asString());

							jsonAssertionUtil.assertLenientJSONForCobrandId(getAccountDetailsResponse, HttpStatus.OK,
									jsonString);

							
							/* * * commonUtils.assertLenientJSONForCobrandId(getAccountDetailsResponse,
							 * HttpStatus.OK, jsonString);*/
							 

						} else {
									
							jsonAssertionUtil.assertResponse(getAccountDetailsResponse, HttpStatus.OK, listJsonName[x], filePath);

							
							/* * * commonUtils.assertLenientJSON(getAccountDetailsResponse, HttpStatus.OK,
							 * listJsonName[x], filePath);*/
							 

						}

					}
				}
			}
	}

	@Test(alwaysRun = true, dataProvider = "feeder", enabled = true, priority = 14)
	@Source("\\TestData\\CSVFiles\\Accounts\\UpdateStatus\\testUpdateStatusForMultipleAccountId.csv")
	public void testDeleteAccountForMultipleAccountIds(String tcId, String oldStatus, String container,
			String accountReconType, String providerAccountId, int statusCode, String defectId, String resFile,
			String filePath, String enabled, String specific) {
		commonUtils.isTCEnabled(enabled, tcId);
		System.out.println("TestCase id is " + tcId);
		// Response getAccountsResponse =
		// ApiUtils.getAccountSummary(accountsHelper.getFilterValue(oldStatus),
		// accountsHelper.getFilterValue(container),
		// accountsHelper.getProviderAccountId(providerAccountId),accountsHelper.getFilterValue(accountReconType),
		// InitialLoader.cobSessionObj);
		
		Map<String, Object> queryParam = accountsHelper.createQueryParamsForGetAccounts(
				accountsHelper.getFilterValue(oldStatus), accountsHelper.getFilterValue(container),
				String.valueOf(providerAccountId1), null,
				accountsHelper.getFilterValue(accountReconType), null, null);
		
		HttpMethodParameters httpParams = HttpMethodParameters.builder().build();
		httpParams.setQueryParams(queryParam);
		
		Response getAccountsResponse = accountUtils.getAccounts(httpParams, configurationObj.getCobrandSessionObj());		
		
		Long itemAccountId = null;
		List<Long> multipleItemAccountId = new ArrayList<Long>();
		int count;
		Response deleteAccountIdResponse;
		try {
			JSONObject object = new JSONObject(getAccountsResponse.asString());
			JSONArray accountArray = object.getJSONArray(JSONPaths.ACCOUNT);
			for (count = 0; count < accountArray.length(); count++) {
				JSONObject accountObject = accountArray.getJSONObject(count);
				itemAccountId = accountObject.getLong(Constants.ID);
				multipleItemAccountId.add(itemAccountId);
			}

		} catch (Exception e) {
				e.printStackTrace();
		}
		System.out.println("Multiple Account Ids are: " + multipleItemAccountId);
		HttpMethodParameters httpMethodParameters = HttpMethodParameters.builder().build();
		Map<String, Object> pathParams = new HashMap<>();
		pathParams.put("multipleItemAccountId", multipleItemAccountId);
		httpMethodParameters.setPathParams(pathParams);
		
		deleteAccountIdResponse = accountUtils.deleteAccount(httpMethodParameters, configurationObj.getCobrandSessionObj());
	
		// deleteAccountIdResponse = ApiUtils.deleteAccountId(multipleItemAccountId,
		// InitialLoader.cobSessionObj); deleteAccountIdResponse.then().log().all();
		if (deleteAccountIdResponse.statusCode() == statusCode)
			Assert.assertTrue(true, "TestCase Passed");
		else {
			softAssert.fail("TC Failed");
		}
		softAssert.assertAll();
	}

	@Test(alwaysRun = true, enabled = true, dataProvider = "feeder", priority = 15)
	@Source("\\TestData\\CSVFiles\\Accounts\\UpdateStatus\\testUpdateStatus.csv")
	public void testUpdateStatus(String tcId, String oldStatus, String updateStatus, String container,
			String accountName, String accountReconType, String providerAccountId, int statusCode,
			int detailsStatusCode, Long defectId, String resFile, String filePath, String enabled, String ignoreKeys, String specific)
			throws FileNotFoundException {
		int count = 0;

		commonUtils.isTCEnabled(enabled, tcId);
		System.out.println("TestCase id is " + tcId);

		// Response getAccountsResponse =
		// ApiUtils.getAccountSummary(accountsHelper.getFilterValue(oldStatus),
		// accountsHelper.getFilterValue(container),
		// accountsHelper.getProviderAccountId(providerAccountId),
		// accountsHelper.getFilterValue(accountReconType),
		// InitialLoader.cobSessionObj);
		Map<String, Object> queryParams = accountsHelper.createQueryParamsForGetAccounts(
				accountsHelper.getFilterValue(oldStatus), accountsHelper.getFilterValue(container),
				String.valueOf(providerAccountId1), null,
				accountsHelper.getFilterValue(accountReconType), null, null);
		
		HttpMethodParameters httpParam = HttpMethodParameters.builder().build();
		httpParam.setQueryParams(queryParams);
		
		Response getAccountsResponse = accountUtils.getAccounts(httpParam, configurationObj.getCobrandSessionObj());
		
		Long itemAccountId = null;
		String accName;
		Response updatedStatusResponse = null;
		try {
			JSONObject object = new JSONObject(getAccountsResponse.asString());
			JSONArray accountArray = object.getJSONArray(JSONPaths.ACCOUNT);
			for (count = 0; count < accountArray.length(); count++) {
				JSONObject accountObject = accountArray.getJSONObject(count);
				accName = accountObject.getString("accountName");
				if (accName.equalsIgnoreCase(accountName)) {
					itemAccountId = accountObject.getLong(Constants.ID);
					break;
				}
			}

			if (updateStatus.equalsIgnoreCase("DELETED")) {
				
				HttpMethodParameters httpMethodParameters = HttpMethodParameters.builder().build();
				Map<String, Object> pathParams = new HashMap<>();
				pathParams.put("itemAccountId", itemAccountId);
				httpMethodParameters.setPathParams(pathParams);
			
				updatedStatusResponse=accountUtils.deleteAccount(httpMethodParameters, configurationObj.getCobrandSessionObj());
				deletedAccountId = itemAccountId;

			}
			
			/*  if (updateStatus.equalsIgnoreCase("DELETED")) { updatedStatusResponse =
			 * ApiUtils.deleteAccountId(itemAccountId, InitialLoader.cobSessionObj);
			 * deletedAccountId = itemAccountId; }*/
			  else {
				if (oldStatus.equalsIgnoreCase(""))
					itemAccountId = deletedAccountId;
				Map<String, Object> queryParam = accountsHelper.createQueryParamsForGetAccounts(updateStatus, null, null,
						null, null, null, null);
				HttpMethodParameters httpMethodParameters = HttpMethodParameters.builder().build();
				httpMethodParameters = accountsHelper.createPathParam("itemAccountId", itemAccountId);
				String bodyParam= accountsHelper.createBodyParamsForUpdateManualAccnt(null, updateStatus, null, null, null);
			//	httpMethodParameters.setQueryParams(queryParam);
				httpMethodParameters.setBodyParams(bodyParam);

				updatedStatusResponse = accountUtils.updateManualOrAggregateAccount(httpMethodParameters,
						configurationObj.getCobrandSessionObj());
				
			/*	 * updatedStatusResponse = ApiUtils.updatedStatusResponse(updateStatus,
				 * InitialLoader.cobSessionObj, itemAccountId);*/
				 
				updatedAccountSet.add(itemAccountId);

			}
			commonUtils.assertStatusCode(updatedStatusResponse, statusCode);
			if (specific.equalsIgnoreCase("FIRST")) {
				updateMap(container, updateStatus, itemAccountId);
				System.out.println("container :" + container + " updateStatus :" + updateStatus + " itemAccountId "
						+ itemAccountId);
				System.out.println(" Inside First");
			}
		} catch (JSONException e) {
			Assert.fail("Failed to get list of account ids ");
		}
		
		/* * Response getAccountDetailsResponse = ApiUtils.getAccountDetails(container,
		 * itemAccountId, InitialLoader.cobSessionObj);*/
		 
		Map<String, Object> queryParam = accountsHelper.createQueryParamsForGetAccounts(null, container, null, null, null, null, null);
		HttpMethodParameters httpParams = HttpMethodParameters.builder().build();
		httpParams.setQueryParams(queryParam);
		Map<String, Object> pathParams = new HashMap<>();
		pathParams.put("itemAccountId", itemAccountId);
		httpParams.setPathParams(pathParams);
		Response getAccountDetailsResponse = accountUtils.getAccountDetailsForAccountId(httpParams,
				configurationObj.getCobrandSessionObj());
		//resFile += ".json";
		String accNum = "";
		if (configurationObj.getMaskingEnabled() && getAccountDetailsResponse.statusCode() == 200) {
			//commonUtils.saveJson(getAccountDetailsResponse.prettyPrint(), filePath, resFile);
			String jsonFile = commonUtils.readJsonFile(commonUtils.getPath(filePath + resFile));
			JSONObject obj = new JSONObject(jsonFile);
			JSONArray accArray = obj.getJSONArray(JSONPaths.ACCOUNT);

			for (int c = 0; c < accArray.length(); c++) {

				String accName1 = accArray.getJSONObject(c).getString("accountName");
				if (!accName1.equalsIgnoreCase("YODLEE")) {
					accNum = accMasking(accName1);
				}
				if (accArray.getJSONObject(c).has("accountNumber"))
					accArray.getJSONObject(c).put("accountNumber", accNum);
				System.out.println("Account Object " + count + "is " + accArray.getJSONObject(c).toString());
			}

			String jsonString = obj.toString();
			System.out.println(" JSON File Data " + jsonString);
			
			System.out.println("API Response Data " + getAccountDetailsResponse.asString());
			jsonAssertionUtil.assertLenientJSONForCobrandId(getAccountDetailsResponse, HttpStatus.OK, jsonString);

			// commonUtils.assertLenientJSONForCobrandId(getAccountDetailsResponse,
			// HttpStatus.OK, jsonString);
		} else {
			
			System.out.println("detailsStatusCode ::"+detailsStatusCode);
			jsonAssertionUtil.assertResponse(getAccountDetailsResponse, detailsStatusCode, resFile, filePath);
		}

		if (specific.equalsIgnoreCase("LAST")) {
			for (Long id : updatedAccountSet) {
				String status = accountsHelper.createBodyParamsForUpdateManualAccnt(null, "ACTIVE", null, null, null);
				HttpMethodParameters httpMethodParameters = HttpMethodParameters.builder().build();
				httpMethodParameters = accountsHelper.createPathParam("accountId", id);
				httpMethodParameters.setBodyParams(status);
				//httpMethodParameters.setQueryParams(params);
				
				accountUtils.updateManualOrAggregateAccount(httpMethodParameters,
						configurationObj.getCobrandSessionObj());

				// ApiUtils.updatedStatusResponse("ACTIVE", InitialLoader.cobSessionObj, id);
			}

		}

	}

	@Test(alwaysRun = true, enabled = true, dataProvider = "feeder", priority = 16)
	@Source("\\TestData\\CSVFiles\\Accounts\\UpdateStatus\\testDeleteAccountForChangedStatus.csv")
	public void testDeleteAccountForChangedStatus(String testcaseID, String steps, String input, String enabled,
			String defectID) {
		System.out.println("Container HashMap is " + containerMap);
		// Delete all the account ids.
		for (Map.Entry<String, Map<String, Long>> map : containerMap.entrySet()) {
			Map<String, Long> statMap = map.getValue();
			String container = map.getKey();
			for (Map.Entry<String, Long> idMap : statMap.entrySet()) {
				Long accountId = idMap.getValue();

				Map<String, Object> pathParam = new HashMap<>();
				pathParam.put("accountId", accountId);
				HttpMethodParameters httpMethodParameters = HttpMethodParameters.builder().build();
				httpMethodParameters.setPathParams(pathParam);

				Response deleteAccountIdResponse = accountUtils.deleteAccount(httpMethodParameters,
						configurationObj.getCobrandSessionObj());

				
				/* * Response deleteAccountIdResponse =
				 * ApiUtils.deleteAccountId(accountId,InitialLoader.cobSessionObj);
				 * deleteAccountIdResponse.then().log().all(); Response getAccountIdResponse =
				 * ApiUtils.getAccountDetails(container,accountId, InitialLoader.cobSessionObj);*/
				 
				LinkedHashMap<String, String> queryParam = new LinkedHashMap<String, String>();
				queryParam.put("container", container);
				Map<String, Object> pathParams = new HashMap<>();
				pathParams.put("accountId", accountId);

				HttpMethodParameters httpParams = HttpMethodParameters.builder().build();
				httpParams.setPathParams(pathParams);
				httpParams.setQueryParams(queryParam);

				Response getAccountIdResponse = accountUtils.getAccountDetailsForAccountId(httpParams,
						configurationObj.getCobrandSessionObj());

				if (deleteAccountIdResponse.statusCode() != HttpStatus.NO_CONTENT_204
						&& getAccountIdResponse.statusCode() != HttpStatus.BAD_REQUEST) {
					softAssert.fail("Failed to delete bank accounts and get account details ...");
				}
			}

		}
		softAssert.assertAll();
	}

	public void updateMap(String container, String updateStatus, Long itemAccountId) {
		Map<String, Long> statusMap;
		if (containerMap.containsKey(container)) {
			statusMap = containerMap.get(container);

		} else {
			statusMap = new HashMap<String, Long>();
		}
		statusMap.put(updateStatus, itemAccountId);
		containerMap.put(container, statusMap);
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

	public String accMasking(String accountName) {

		Configuration configurationObj = Configuration.getInstance();
		String accountNumber = null;
		if (accNameAccNumberMap.containsKey(accountName))
			accountNumber = accNameAccNumberMap.get(accountName);

		System.out.println("Account Number in Yodlee " + accountNumber + " accountName " + accountName);
		int len = accountNumber.length();
		String accNum = "";
		int i;
		for (i = 0; i < 8 - configurationObj.getMaskLength(); i++)
			accNum += configurationObj.getMaskCharacter();
		accountNumber = accountNumber.substring(len - configurationObj.getMaskLength(), len);
		accNum += accountNumber;
		System.out.println("Masked Account Number: " + accNum);
		return accNum;
	}

	public void loadMap() {
		accNameAccNumberMap.put("brokerageMargin", "989898139");
		accNameAccNumberMap.put("moneyMarket", "989898139");
		accNameAccNumberMap.put("401k", "989898139");
		accNameAccNumberMap.put("403b", "989898139");
		accNameAccNumberMap.put("trust", "989898139");
		accNameAccNumberMap.put("annuity", "989898139");
		accNameAccNumberMap.put("simple", "989898139");
		accNameAccNumberMap.put("custodial", "989898139");
		accNameAccNumberMap.put("brokerageCashOption", "989898139");
		accNameAccNumberMap.put("brokerageMarginOption", "989898139");
		accNameAccNumberMap.put("individual", "989898139");
		accNameAccNumberMap.put("corporate", "989898139");
		accNameAccNumberMap.put("jttic", "989898139");
		accNameAccNumberMap.put("jtwros", "989898139");
		accNameAccNumberMap.put("communityProperty", "989898139");
		accNameAccNumberMap.put("jointByEntirety", "989898139");
		accNameAccNumberMap.put("conservatorship", "989898139");
		accNameAccNumberMap.put("roth", "989898139");
		accNameAccNumberMap.put("rothConversion", "989898139");
		accNameAccNumberMap.put("rollover", "989898139");

		accNameAccNumberMap.put("TESTDATA", "503-1123xxx");
		accNameAccNumberMap.put("TESTDATA1", "503-1123xxx");
		accNameAccNumberMap.put("TESTDATA2", "503-1123xxx");

		accNameAccNumberMap.put("DAG_BILLING", "555555");
		accNameAccNumberMap.put("DAG_BILLING1", "555555");
		accNameAccNumberMap.put("DAG_BILLING2", "555555");
		accNameAccNumberMap.put("DAG_BILLING3", "555555");

		accNameAccNumberMap.put("Super CD Plus", "354-2334");
		accNameAccNumberMap.put("Super CD Plus1", "354-2334");
		accNameAccNumberMap.put("Super CD Plus2", "354-2334");

		accNameAccNumberMap.put("DAG INSURANCE", "5555556");
		accNameAccNumberMap.put("DAG INSURANCE1", "5555556");
		accNameAccNumberMap.put("DAG INSURANCE2", "5555556");

	}

	@AfterClass(alwaysRun = true)
	public void deleteAccount() {
		Map<String, Object> pathParams = new HashMap<>();
		pathParams.put("providerAccountId", providerAccountId1);
		HttpMethodParameters httpParams = HttpMethodParameters.builder().build();
		httpParams.setPathParams(pathParams);

		Response deleteResponse = providerAccountUtils.deleteProviderAccount(httpParams,
				configurationObj.getCobrandSessionObj());

		/*
		 * * Response deleteResponse =
		 * ProviderAccountUtils.deleteProviderAccount(ProviderAccountUtils.
		 * providerAccountId, InitialLoader.cobSessionObj);
		 */

		deleteResponse.then().log().all();
	}

}
