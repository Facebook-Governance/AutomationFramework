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

package com.yodlee.yodleeApi.ManualAccounts;

import java.io.FileNotFoundException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.databene.benerator.anno.Source;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.yodlee.yodleeApi.assertions.JsonAssertionUtil;
import com.yodlee.yodleeApi.common.Base;
import com.yodlee.yodleeApi.common.Configuration;
import com.yodlee.yodleeApi.constants.HttpStatus;
import com.yodlee.yodleeApi.constants.JSONPaths;
import com.yodlee.yodleeApi.helper.AccountsHelper;
import com.yodlee.yodleeApi.helper.SessionHelper;
import com.yodlee.yodleeApi.pojo.EnvSession;
import com.yodlee.yodleeApi.pojo.HttpMethodParameters;
import com.yodlee.yodleeApi.utils.apiUtils.AccountUtils;
import com.yodlee.yodleeApi.utils.commonUtils.CommonUtils;
import com.yodlee.taas.annote.Feature;
import com.yodlee.taas.annote.FeatureName;
import com.yodlee.taas.annote.Info;
import com.yodlee.taas.annote.SubFeature;

import io.restassured.response.Response;

/**
 * @author bhuvaneshwari
 *
 */
@Feature(featureName = { FeatureName.MANUAL_ACCOUNTS })
public class TestManualAccounts extends Base {

	List<Integer> statusCodesCard = new ArrayList<>();
	public Map<String, String> accNameAccNumberMap = new HashMap<String, String>();
	CommonUtils commonUtils = new CommonUtils();
	AccountsHelper accountsHelper = new AccountsHelper();
	AccountUtils accountUtils = new AccountUtils();
	JsonAssertionUtil jsonAssertionUtil = new JsonAssertionUtil();
	Configuration configurationObj = Configuration.getInstance();


	@BeforeClass
	public void setUp() {
		// TODO
	}
	
	@Info(subfeature = { SubFeature.ADD_MANUAL_ACCOUNT }, userStoryId = "")
	@Test(dataProvider = "feeder", enabled = true)
	@Source("\\TestData\\CSVFiles\\manualAccounts\\AddManualAccount\\addManualAccountBankAndInvestment.csv")
	public void testAddManualAccountBankAndInvestment(String Testcaseid, String TestcaseName, String accountTypeKey,
			String accountType, String accountNameKey, String accountName, String nicknameKey, String nickname,
			String accountNumberKey, String accountNumber, String loginNameKey, String loginName, String passwordKey,
			String password, String includeInNetWorthKey, String includeInNetWorth, String memoKey, String memo,
			String urlKey, String url, String amountKey, Double amount, String currencyKey, String currency, int status,
			String respFileName, String filePath, Long defectId, String enabled) throws JSONException {
	
		commonUtils.isTCEnabled(enabled, Testcaseid);
		System.out.println("TestCase id is " + Testcaseid + " TestCase Name is " + TestcaseName);

		/*
		 * String requestBody =
		 * CreateJSONObject.createManualActObjForBankAndInvContainer(accountTypeKey,
		 * accountType, accountNameKey, accountName, nicknameKey, nickname,
		 * accountNumberKey, accountNumber, loginNameKey, loginName, passwordKey,
		 * password, includeInNetWorthKey, includeInNetWorth, memoKey, memo, urlKey,
		 * url, amountKey, amount, currencyKey, currency);
		 */
		String requestBody = accountsHelper.createManualActObjForBankAndInvContainer(accountType, accountName, nickname,
				accountNumber, loginName, password, null, includeInNetWorth, memo, url, amount, currency);
		/*
		 * Response addManualAccountResponse =
		 * RestUtil.postRequestWithBodyParam(requestBody,
		 * InitialLoader.cobSessionObj.getPath() + Constants.ACCOUNTS_URL,
		 * InitialLoader.cobSessionObj.getCobSession(),
		 * InitialLoader.cobSessionObj.getUserSession());
		 * addManualAccountResponse.then().log().all();
		 */

		HttpMethodParameters httpParams = HttpMethodParameters.builder().build();
		httpParams.setBodyParams(requestBody);
		Response addManualAccountResponse = accountUtils.addManualAccount(httpParams,
				configurationObj.getCobrandSessionObj());
		if (addManualAccountResponse.getStatusCode() == status) {
			/*
			 * CommonUtils.saveJson(addManualAccountResponse.prettyPrint(), filePath,
			 * respFileName); CommonUtils.assertJSON(addManualAccountResponse, status,
			 * respFileName, filePath);
			 */
			jsonAssertionUtil.assertResponse(addManualAccountResponse, status, respFileName, filePath);
		} else {
			commonUtils.saveJson(addManualAccountResponse.prettyPrint(), filePath, respFileName);
			Assert.assertFalse(Testcaseid + "::" + TestcaseName + " is Failed", true);
		}
	}

	@Info(subfeature = { SubFeature.ADD_MANUAL_ACCOUNT }, userStoryId = "")
	@Test(dataProvider = "feeder", enabled = true)
	@Source("\\TestData\\CSVFiles\\manualAccounts\\AddManualAccount\\DifferentCobAndUserSession.csv")
	public void testAddManualAccountBankAndInvestmentForDifferntCobAndUserSession(String Testcaseid,
			String TestcaseName, String cobSession, String userSession, int status, String resFile, String filePath,
			String defectId, String enabled) throws JSONException {
		commonUtils.isTCEnabled(enabled, Testcaseid);
		
		System.out.println("TestCase id is " + Testcaseid + " TestCase Name is " + TestcaseName);
		/*
		 * String requestBody =
		 * CreateJSONObject.createManualActObjForBankAndInvContainer("accountType",
		 * "savings", "accountName", "SAVINGS", "nickname",
		 * "brokerage account nick name", "accountNumber", "1", "loginName",
		 * "brokerage account login name", "password", "brokerage account password",
		 * "includeInNetWorth", "TRUE", "memo", "brokerage account note", "url",
		 * "http://test.com", "amount", 100, "currency", "USD");
		 */
		String requestBody = accountsHelper.createManualActObjForBankAndInvContainer("savings", "SAVINGS",
				"brokerage account nick name", "1", "brokerage account login name", "brokerage account password", null,
				"TRUE", "brokerage account note", "http://test.com", 100, "USD");
		/*
		 * Response addManualAccountResponse =
		 * RestUtil.postRequestWithBodyParam(requestBody,
		 * InitialLoader.cobSessionObj.getPath() + Constants.ACCOUNTS_URL,
		 * SessionUtil.getSessionToken(cobSession, "cob"),
		 * SessionUtil.getSessionToken(userSession, "user"));
		 * addManualAccountResponse.then().log().all();
		 */
		HttpMethodParameters httpParams = HttpMethodParameters.builder().build();
		httpParams.setBodyParams(requestBody);

		String cobSessionToken = SessionHelper.getSessionToken(cobSession, "cob");
		String userSessionToken = SessionHelper.getSessionToken(userSession, "user");
		System.out.println("userSessionToken:::::::" + userSessionToken);
		EnvSession session = EnvSession.builder().cobSession(cobSessionToken).userSession(userSessionToken)
				.path(configurationObj.getCobrandSessionObj().getPath()).build();
		
		System.out.println("session iss:" + session);
		Response addManualAccountResponse = accountUtils.addManualAccount(httpParams, session);

		if (addManualAccountResponse.getStatusCode() == status) {
			/*
			 * CommonUtils.saveJson(addManualAccountResponse.prettyPrint(), filePath,
			 * resFile + ".json"); CommonUtils.assertJSON(addManualAccountResponse, status,
			 * resFile + ".json", filePath);
			 */
			jsonAssertionUtil.assertResponse(addManualAccountResponse, status, resFile + ".json", filePath);
		} else {
			commonUtils.saveJson(addManualAccountResponse.prettyPrint(), filePath, resFile);
			Assert.assertFalse(Testcaseid + "::" + TestcaseName + " is Failed", true);
		}

	}

	@Info(subfeature = { SubFeature.ADD_MANUAL_ACCOUNT }, userStoryId = "")
	@Test(dataProvider = "feeder", enabled = true)
	@Source("\\TestData\\CSVFiles\\manualAccounts\\AddManualAccount\\addManualAccountBankAndInvestmentNegative.csv")
	public void testAddManualAccountBankAndInvestmentNegative(String Testcaseid, String TestcaseName,
			String accountTypeKey, String accountType, String accountNameKey, String accountName, String nicknameKey,
			String nickname, String accountNumberKey, String accountNumber, String loginNameKey, String loginName,
			String passwordKey, String password, String includeInNetWorthKey, String includeInNetWorth, String memoKey,
			String memo, String urlKey, String url, String amountKey, Double amount, String currencyKey,
			String currency, int status, String respFileName, String filePath, Long defectId, String enabled)
			throws JSONException, FileNotFoundException {
		commonUtils.isTCEnabled(enabled, Testcaseid);
		String requestBody ="";

		System.out.println("TestCase id is " + Testcaseid + " TestCase Name is " + TestcaseName+"::"+TestcaseName);
         try
         {
		requestBody = accountsHelper.createManualActObjForBankAndInvContainer(accountType, accountName, nickname,
				accountNumber, loginName, password, null, includeInNetWorth, memo, url, amount, currency);
		System.out.println("After requestBody::"+requestBody);
		
         }
         catch(Exception e)
         {
        	 System.out.println("Exception is::"+e.getMessage());
        	 e.printStackTrace();
         }
		
		HttpMethodParameters httpParams = HttpMethodParameters.builder().build();
		httpParams.setBodyParams(requestBody);
		Response addManualAccountResponse = accountUtils.addManualAccount(httpParams,
				configurationObj.getCobrandSessionObj());
		System.out.println("addManual account");
		if (addManualAccountResponse.getStatusCode() == HttpStatus.CREATED) {

			String[] listJsonName = respFileName.split(",");

			String[] listAccountName = accountName.split(",");
			String aName = addManualAccountResponse.jsonPath().getString("account[0].accountName");
			int x = 0;

			for (x = 0; x < listAccountName.length; x++) {
				if (aName.equalsIgnoreCase(listAccountName[x])) {
					System.out.println("Json is " + listJsonName[x]);

					String accNum = "";
					commonUtils.saveJson(addManualAccountResponse.prettyPrint(), filePath, listJsonName[x]);
					String jsonFile = commonUtils.readJsonFile(commonUtils.getPath(filePath + listJsonName[x]));
					JSONObject obj = new JSONObject(jsonFile);
					if (configurationObj.getMaskingEnabled()) {
						JSONArray accArray = obj.getJSONArray(JSONPaths.ACCOUNT);

						for (int c = 0; c < accArray.length(); c++) {

							String accName = accArray.getJSONObject(c).getString("accountName");
							accNum = accArray.getJSONObject(c).getString("accountNumber");
							System.out.println("accNum passed is::" + accNum);
							if (!accName.equalsIgnoreCase("YODLEE")) {
								accNum = accMasking(accNum);
							}

							if (accArray.getJSONObject(c).has("accountNumber"))
								accArray.getJSONObject(c).put("accountNumber", accNum);

						}
					}
					System.out.println("obj:::" + obj);
					jsonAssertionUtil.assertLenientJSON(addManualAccountResponse, obj.toString());
				}

			
			}

		}

		else if (addManualAccountResponse.getStatusCode() == status) {
			
			jsonAssertionUtil.assertResponse(addManualAccountResponse, status, respFileName, filePath);
		} else {
			Assert.assertFalse(Testcaseid + "::" + TestcaseName + " is Failed", true);
		}
	}

	@Info(subfeature = { SubFeature.ADD_MANUAL_ACCOUNT }, userStoryId = "")
	@Test(enabled = false, dataProvider = "feeder")
	@Source("\\TestData\\CSVFiles\\manualAccounts\\AddManualAccount\\AddManualAccountForBill.csv")
	public void testManualAccountForBill(String TestCaseId, String testCase, String accountTypeValue,
			String accountNameValue, String nicknameValue, String accountNumberValue, Double amountValue,
			String currencyValue, String frequencyValue, String dateValue, String urlValue, String loginNameValue,
			String passwordValue, String memoValue, int status, String respFile, String filePath, Long defectId,
			String enabled) throws JSONException {
		commonUtils.isTCEnabled(enabled, TestCaseId);

		System.out.println("TestCase id is " + TestCaseId + " TestCase Name is " + testCase);

		String formatedDate = commonUtils.getOffsetDate(dateValue);
		/*
		 * String params =
		 * CreateJSONObject.createManualActObjForBillContainer(accountTypeValue,
		 * accountNameValue, nicknameValue, accountNumberValue, loginNameValue,
		 * passwordValue, frequencyValue, formatedDate, memoValue, urlValue,
		 * amountValue, currencyValue);
		 */
		String params = accountsHelper.createManualActObjForBillContainer(accountTypeValue, accountNameValue,
				nicknameValue, accountNumberValue, loginNameValue, passwordValue, frequencyValue, formatedDate,
				memoValue, urlValue, amountValue, currencyValue);
		/*
		 * Response addManualAcocuntforBill = RestUtil.postRequestWithBodyParam(params,
		 * InitialLoader.cobSessionObj.getPath() + Constants.ACCOUNTS_URL,
		 * InitialLoader.cobSessionObj.getCobSession(),
		 * InitialLoader.cobSessionObj.getUserSession());
		 * addManualAcocuntforBill.then().log().all();
		 */
		HttpMethodParameters httpMethodParameters = HttpMethodParameters.builder().build();
		httpMethodParameters.setBodyParams(params);
		Response addManualAcocuntforBill = accountUtils.addManualAccount(httpMethodParameters,
				configurationObj.getCobrandSessionObj());

		if (addManualAcocuntforBill.getStatusCode() == status) {
			/*
			 * CommonUtils.saveJson(addManualAcocuntforBill.prettyPrint(), filePath,
			 * respFile); CommonUtils.assertJSON(addManualAcocuntforBill, status, respFile,
			 * filePath);
			 */
			jsonAssertionUtil.assertResponse(addManualAcocuntforBill, status, respFile, filePath);
		} else {
			Assert.assertFalse(TestCaseId + "::" + testCase + " is Failed", true);
		}
	}

	@Info(subfeature = { SubFeature.ADD_MANUAL_ACCOUNT }, userStoryId = "")
	@Test(enabled = false, dataProvider = "feeder")
	@Source("\\TestData\\CSVFiles\\manualAccounts\\AddManualAccount\\testManualAccountForBillNegative.csv")
	public void testManualAccountForBillNegative(String TestCaseId, String testCase, String accountTypeValue,
			String accountNameValue, String nicknameValue, String accountNumberValue, Double amountValue,
			String currencyValue, String frequencyValue, String dateValue, String urlValue, String loginNameValue,
			String passwordValue, String memoValue, int status, String respFile, String filePath, Long defectId,
			String enabled) throws JSONException {
		commonUtils.isTCEnabled(enabled, TestCaseId);
	
		System.out.println("TestCase id is " + TestCaseId + " TestCase Name is " + testCase);
		String formatedDate = commonUtils.getOffsetDate(dateValue);

		/*
		 * String params =
		 * CreateJSONObject.createManualActObjForBillContainer(accountTypeValue,
		 * accountNameValue, nicknameValue, accountNumberValue, loginNameValue,
		 * passwordValue, frequencyValue, formatedDate, memoValue, urlValue,
		 * amountValue, currencyValue);
		 */
		String params = accountsHelper.createManualActObjForBillContainer(accountTypeValue, accountNameValue,
				nicknameValue, accountNumberValue, loginNameValue, passwordValue, frequencyValue, formatedDate,
				memoValue, urlValue, amountValue, currencyValue);
		/*
		 * Response addManualAcocuntforBill = RestUtil.postRequestWithBodyParam(params,
		 * InitialLoader.cobSessionObj.getPath() + Constants.ACCOUNTS_URL,
		 * InitialLoader.cobSessionObj.getCobSession(),
		 * InitialLoader.cobSessionObj.getUserSession());
		 * 
		 * addManualAcocuntforBill.then().log().all();
		 */
		HttpMethodParameters httpMethodParameters = HttpMethodParameters.builder().build();
		httpMethodParameters.setBodyParams(params);
		Response addManualAcocuntforBill = accountUtils.addManualAccount(httpMethodParameters,
				configurationObj.getCobrandSessionObj());
		if (addManualAcocuntforBill.getStatusCode() == status) {
			/*
			 * CommonUtils.saveJson(addManualAcocuntforBill.prettyPrint(), filePath,
			 * respFile); CommonUtils.assertJSON(addManualAcocuntforBill, status, respFile,
			 * filePath);
			 */
			jsonAssertionUtil.assertResponse(addManualAcocuntforBill, status, respFile, filePath);
		} else {
			Assert.assertFalse(TestCaseId + "::" + testCase + " is Failed", true);
		}
	}

	@Info(subfeature = { SubFeature.ADD_MANUAL_ACCOUNT }, userStoryId = "")
	@Test(enabled = false, dataProvider = "feeder")
	@Source("\\TestData\\CSVFiles\\manualAccounts\\AddManualAccount\\testManualAccountForCardAndLoan1.csv")
	public void testManualAccountForCardAndLoan_1(String testCaseId, String testCase, String accountTypeValue,
			String accountNameValue, String nicknameValue, String accountNumberValue, Double amountValue,
			String currencyValue, Double balanceAmountValue, String balanceCurrencyValue, String frequencyValue,
			String dueDateValue, String urlValue, String loginNameValue, String passwordValue,
			String includeInNetWorthValue, String memoValue, int status, String respFile, String filePath,
			Long defectId, String enabled) throws JSONException {
		executeManualAcc(testCaseId, testCase, accountTypeValue, accountNameValue, nicknameValue, accountNumberValue,
				amountValue, currencyValue, balanceAmountValue, balanceCurrencyValue, frequencyValue, dueDateValue,
				urlValue, loginNameValue, passwordValue, includeInNetWorthValue, memoValue, status, respFile, filePath,
				enabled);
	}

	@Info(subfeature = { SubFeature.ADD_MANUAL_ACCOUNT }, userStoryId = "")
	@Test(enabled = false, dataProvider = "feeder")
	@Source("\\TestData\\CSVFiles\\manualAccounts\\AddManualAccount\\testManualAccountForCardAndLoan2.csv")
	public void testManualAccountForCardAndLoan_2(String testCaseId, String testCase, String accountTypeValue,
			String accountNameValue, String nicknameValue, String accountNumberValue, Double amountValue,
			String currencyValue, Double balanceAmountValue, String balanceCurrencyValue, String frequencyValue,
			String dueDateValue, String urlValue, String loginNameValue, String passwordValue,
			String includeInNetWorthValue, String memoValue, int status, String respFile, String filePath,
			Long defectId, String enabled) throws JSONException {
		executeManualAcc(testCaseId, testCase, accountTypeValue, accountNameValue, nicknameValue, accountNumberValue,
				amountValue, currencyValue, balanceAmountValue, balanceCurrencyValue, frequencyValue, dueDateValue,
				urlValue, loginNameValue, passwordValue, includeInNetWorthValue, memoValue, status, respFile, filePath,
				enabled);
	}

	@Info(subfeature = { SubFeature.ADD_MANUAL_ACCOUNT }, userStoryId = "")
	@Test(enabled = false, dataProvider = "feeder")
	@Source("\\TestData\\CSVFiles\\manualAccounts\\AddManualAccount\\testManualAccountForCardAndLoan3.csv")
	public void testManualAccountForCardAndLoan_3(String testCaseId, String testCase, String accountTypeValue,
			String accountNameValue, String nicknameValue, String accountNumberValue, Double amountValue,
			String currencyValue, Double balanceAmountValue, String balanceCurrencyValue, String frequencyValue,
			String dueDateValue, String urlValue, String loginNameValue, String passwordValue,
			String includeInNetWorthValue, String memoValue, int status, String respFile, String filePath,
			Long defectId, String enabled) throws JSONException {
		executeManualAcc(testCaseId, testCase, accountTypeValue, accountNameValue, nicknameValue, accountNumberValue,
				amountValue, currencyValue, balanceAmountValue, balanceCurrencyValue, frequencyValue, dueDateValue,
				urlValue, loginNameValue, passwordValue, includeInNetWorthValue, memoValue, status, respFile, filePath,
				enabled);
	}

	@Info(subfeature = { SubFeature.ADD_MANUAL_ACCOUNT }, userStoryId = "")
	@Test(enabled = false, dataProvider = "feeder")
	@Source("\\TestData\\CSVFiles\\manualAccounts\\AddManualAccount\\testManualAccountForCardAndLoan4.csv")
	public void testManualAccountForCardAndLoan_4(String testCaseId, String testCase, String accountTypeValue,
			String accountNameValue, String nicknameValue, String accountNumberValue, Double amountValue,
			String currencyValue, Double balanceAmountValue, String balanceCurrencyValue, String frequencyValue,
			String dueDateValue, String urlValue, String loginNameValue, String passwordValue,
			String includeInNetWorthValue, String memoValue, int status, String respFile, String filePath,
			Long defectId, String enabled) throws JSONException {
		executeManualAcc(testCaseId, testCase, accountTypeValue, accountNameValue, nicknameValue, accountNumberValue,
				amountValue, currencyValue, balanceAmountValue, balanceCurrencyValue, frequencyValue, dueDateValue,
				urlValue, loginNameValue, passwordValue, includeInNetWorthValue, memoValue, status, respFile, filePath,
				enabled);
	}

	@Info(subfeature = { SubFeature.ADD_MANUAL_ACCOUNT }, userStoryId = "")
	@Test(enabled = false, dataProvider = "feeder")
	@Source("\\TestData\\CSVFiles\\manualAccounts\\AddManualAccount\\testManualAccountForCardAndLoan5.csv")
	public void testManualAccountForCardAndLoan_5(String testCaseId, String testCase, String accountTypeValue,
			String accountNameValue, String nicknameValue, String accountNumberValue, Double amountValue,
			String currencyValue, Double balanceAmountValue, String balanceCurrencyValue, String frequencyValue,
			String dueDateValue, String urlValue, String loginNameValue, String passwordValue,
			String includeInNetWorthValue, String memoValue, int status, String respFile, String filePath,
			Long defectId, String enabled) throws JSONException {
		executeManualAcc(testCaseId, testCase, accountTypeValue, accountNameValue, nicknameValue, accountNumberValue,
				amountValue, currencyValue, balanceAmountValue, balanceCurrencyValue, frequencyValue, dueDateValue,
				urlValue, loginNameValue, passwordValue, includeInNetWorthValue, memoValue, status, respFile, filePath,
				enabled);
	}

	private void executeManualAcc(String testCaseId, String testCase, String accountTypeValue, String accountNameValue,
			String nicknameValue, String accountNumberValue, Double amountValue, String currencyValue,
			Double balanceAmountValue, String balanceCurrencyValue, String frequencyValue, String dueDateValue,
			String urlValue, String loginNameValue, String passwordValue, String includeInNetWorthValue,
			String memoValue, int status, String respFile, String filePath, String enabled) {
		commonUtils.isTCEnabled(enabled, testCaseId);

		System.out.println("Testcase Id :" + testCaseId + " testCase name  :" + testCase);
		String formatedDate = commonUtils.getOffsetDate(dueDateValue);
		/*
		 * String params =
		 * CreateJSONObject.createManualActObjForCardAndLoanContainer(accountTypeValue,
		 * accountNameValue, nicknameValue, accountNumberValue, loginNameValue,
		 * passwordValue, frequencyValue, formatedDate, memoValue, urlValue,
		 * includeInNetWorthValue, amountValue, currencyValue, balanceAmountValue,
		 * balanceCurrencyValue);
		 */
		String params = accountsHelper.createManualActObjForCardAndLoanContainer(accountTypeValue, accountNameValue,
				nicknameValue, accountNumberValue, loginNameValue, passwordValue, frequencyValue, formatedDate,
				memoValue, urlValue, includeInNetWorthValue, amountValue, currencyValue, balanceAmountValue,
				balanceCurrencyValue);
		/*
		 * Response response = RestUtil.postRequestWithBodyParam(params,
		 * InitialLoader.cobSessionObj.getPath() + Constants.ACCOUNTS_URL,
		 * InitialLoader.cobSessionObj.getCobSession(),
		 * InitialLoader.cobSessionObj.getUserSession()); response.then().log().all();
		 */

		HttpMethodParameters httpParams = HttpMethodParameters.builder().build();
		httpParams.setBodyParams(params);
		Response response = accountUtils.addManualAccount(httpParams, configurationObj.getCobrandSessionObj());

		// CommonUtils.saveJson(response.prettyPrint(), filePath, respFile);
		if (response.getStatusCode() == status)
			// CommonUtils.assertJSON(response, status, respFile, filePath);
			jsonAssertionUtil.assertResponse(response, status, respFile, filePath);
		else {
			Assert.assertFalse(testCaseId + "::" + testCase + " is Failed", true);
		}
	}

	@Test(enabled = false, dataProvider = "feeder")
	@Source("\\TestData\\CSVFiles\\manualAccounts\\AddManualAccount\\testManualAccountForCardAndLoanNegative.csv")
	@Info(subfeature = { SubFeature.ADD_MANUAL_ACCOUNT }, userStoryId = "")
	public void testManualAccountForCardAndLoanNegative(String testCaseId, String testCase, String accountTypeValue,
			String accountNameValue, String nicknameValue, String accountNumberValue, Double amountValue,
			String currencyValue, Double balanceAmountValue, String balanceCurrencyValue, String frequencyValue,
			String dueDateValue, String urlValue, String loginNameValue, String passwordValue,
			String includeInNetWorthValue, String memoValue, int status, String respFile, String filePath,
			Long defectId, String enabled) throws JSONException {
		commonUtils.isTCEnabled(enabled, testCaseId);
		
		System.out.println("TestCase id is " + testCaseId + " TestCase Name is " + testCase);
		String formatedDate = commonUtils.getOffsetDate(dueDateValue);
		/*
		 * String params =
		 * CreateJSONObject.createManualActObjForCardAndLoanContainer(accountTypeValue,
		 * accountNameValue, nicknameValue, accountNumberValue, loginNameValue,
		 * passwordValue, frequencyValue, formatedDate, memoValue, urlValue,
		 * includeInNetWorthValue, amountValue, currencyValue, balanceAmountValue,
		 * balanceCurrencyValue);
		 */
		String params = accountsHelper.createManualActObjForCardAndLoanContainer(accountTypeValue, accountNameValue,
				nicknameValue, accountNumberValue, loginNameValue, passwordValue, frequencyValue, formatedDate,
				memoValue, urlValue, includeInNetWorthValue, amountValue, currencyValue, balanceAmountValue,
				balanceCurrencyValue);
		/*
		 * Response response = RestUtil.postRequestWithBodyParam(params,
		 * InitialLoader.cobSessionObj.getPath() + Constants.ACCOUNTS_URL,
		 * InitialLoader.cobSessionObj.getCobSession(),
		 * InitialLoader.cobSessionObj.getUserSession()); response.then().log().all();
		 */

		HttpMethodParameters httpParams = HttpMethodParameters.builder().build();
		httpParams.setBodyParams(params);
		Response response = accountUtils.addManualAccount(httpParams, configurationObj.getCobrandSessionObj());
		// CommonUtils.saveJson(response.prettyPrint(), filePath, respFile);
		if (response.getStatusCode() == status)
			// CommonUtils.assertJSON(response, status, respFile, filePath);
			jsonAssertionUtil.assertResponse(response, status, respFile, filePath);

		else {
			Assert.assertFalse(testCaseId + "::" + testCase + " is Failed", true);
		}
	}

	@Info(subfeature = { SubFeature.ADD_MANUAL_ACCOUNT }, userStoryId = "")
	@Test(enabled = false, dataProvider = "feeder")
	@Source("\\TestData\\CSVFiles\\manualAccounts\\AddManualAccount\\testManualAccountForInsurance1.csv")
	public void testManualAccountForInsurance_1(String testCaseId, String testCase, String accountTypeValue,
			String accountNameValue, String nicknameValue, String accountNumberValue, Double amountDue,
			String currencyDueValue, Double balanceAmountValue, String balanceCurrencyValue, String frequencyValue,
			String dueDateValue, String urlValue, String loginNameValue, String passwordValue,
			String includeInNetWorthValue, String isAssetValue, String memoValue, int status, String resFile,
			String filePath, Long defectId, String enabled) throws JSONException {

		evaluateInsurance(testCaseId, testCase, accountTypeValue, accountNameValue, nicknameValue, accountNumberValue,
				amountDue, currencyDueValue, balanceAmountValue, balanceCurrencyValue, frequencyValue, dueDateValue,
				urlValue, loginNameValue, passwordValue, includeInNetWorthValue, isAssetValue, memoValue, status,
				resFile, filePath, enabled);

	}

	@Info(subfeature = { SubFeature.ADD_MANUAL_ACCOUNT }, userStoryId = "")
	@Test(enabled = false, dataProvider = "feeder")
	@Source("\\TestData\\CSVFiles\\manualAccounts\\AddManualAccount\\testManualAccountForInsurance2.csv")
	public void testManualAccountForInsurance_2(String testCaseId, String testCase, String accountTypeValue,
			String accountNameValue, String nicknameValue, String accountNumberValue, Double amountDue,
			String currencyDueValue, Double balanceAmountValue, String balanceCurrencyValue, String frequencyValue,
			String dueDateValue, String urlValue, String loginNameValue, String passwordValue,
			String includeInNetWorthValue, String isAssetValue, String memoValue, int status, String resFile,
			String filePath, Long defectId, String enabled) throws JSONException {

		evaluateInsurance(testCaseId, testCase, accountTypeValue, accountNameValue, nicknameValue, accountNumberValue,
				amountDue, currencyDueValue, balanceAmountValue, balanceCurrencyValue, frequencyValue, dueDateValue,
				urlValue, loginNameValue, passwordValue, includeInNetWorthValue, isAssetValue, memoValue, status,
				resFile, filePath, enabled);

	}

	@Info(subfeature = { SubFeature.ADD_MANUAL_ACCOUNT }, userStoryId = "")
	@Test(enabled = false, dataProvider = "feeder")
	@Source("\\TestData\\CSVFiles\\manualAccounts\\AddManualAccount\\testManualAccountForInsurance3.csv")
	public void testManualAccountForInsurance_3(String testCaseId, String testCase, String accountTypeValue,
			String accountNameValue, String nicknameValue, String accountNumberValue, Double amountDue,
			String currencyDueValue, Double balanceAmountValue, String balanceCurrencyValue, String frequencyValue,
			String dueDateValue, String urlValue, String loginNameValue, String passwordValue,
			String includeInNetWorthValue, String isAssetValue, String memoValue, int status, String resFile,
			String filePath, Long defectId, String enabled) throws JSONException {

		evaluateInsurance(testCaseId, testCase, accountTypeValue, accountNameValue, nicknameValue, accountNumberValue,
				amountDue, currencyDueValue, balanceAmountValue, balanceCurrencyValue, frequencyValue, dueDateValue,
				urlValue, loginNameValue, passwordValue, includeInNetWorthValue, isAssetValue, memoValue, status,
				resFile, filePath, enabled);

	}

	@Info(subfeature = { SubFeature.ADD_MANUAL_ACCOUNT }, userStoryId = "")
	@Test(enabled = false, dataProvider = "feeder")
	@Source("\\TestData\\CSVFiles\\manualAccounts\\AddManualAccount\\testManualAccountForInsurance4.csv")
	public void testManualAccountForInsurance_4(String testCaseId, String testCase, String accountTypeValue,
			String accountNameValue, String nicknameValue, String accountNumberValue, Double amountDue,
			String currencyDueValue, Double balanceAmountValue, String balanceCurrencyValue, String frequencyValue,
			String dueDateValue, String urlValue, String loginNameValue, String passwordValue,
			String includeInNetWorthValue, String isAssetValue, String memoValue, int status, String resFile,
			String filePath, Long defectId, String enabled) throws JSONException {

		evaluateInsurance(testCaseId, testCase, accountTypeValue, accountNameValue, nicknameValue, accountNumberValue,
				amountDue, currencyDueValue, balanceAmountValue, balanceCurrencyValue, frequencyValue, dueDateValue,
				urlValue, loginNameValue, passwordValue, includeInNetWorthValue, isAssetValue, memoValue, status,
				resFile, filePath, enabled);

	}

	@Info(subfeature = { SubFeature.ADD_MANUAL_ACCOUNT }, userStoryId = "")
	@Test(enabled = false, dataProvider = "feeder")
	@Source("\\TestData\\CSVFiles\\manualAccounts\\AddManualAccount\\testManualAccountForInsurance5.csv")
	public void testManualAccountForInsurance_5(String testCaseId, String testCase, String accountTypeValue,
			String accountNameValue, String nicknameValue, String accountNumberValue, Double amountDue,
			String currencyDueValue, Double balanceAmountValue, String balanceCurrencyValue, String frequencyValue,
			String dueDateValue, String urlValue, String loginNameValue, String passwordValue,
			String includeInNetWorthValue, String isAssetValue, String memoValue, int status, String resFile,
			String filePath, Long defectId, String enabled) throws JSONException {

		evaluateInsurance(testCaseId, testCase, accountTypeValue, accountNameValue, nicknameValue, accountNumberValue,
				amountDue, currencyDueValue, balanceAmountValue, balanceCurrencyValue, frequencyValue, dueDateValue,
				urlValue, loginNameValue, passwordValue, includeInNetWorthValue, isAssetValue, memoValue, status,
				resFile, filePath, enabled);

	}

	@Info(subfeature = { SubFeature.ADD_MANUAL_ACCOUNT }, userStoryId = "")
	@Test(enabled = false, dataProvider = "feeder")
	@Source("\\TestData\\CSVFiles\\manualAccounts\\AddManualAccount\\testManualAccountForInsurance6.csv")
	public void testManualAccountForInsurance_6(String testCaseId, String testCase, String accountTypeValue,
			String accountNameValue, String nicknameValue, String accountNumberValue, Double amountDue,
			String currencyDueValue, Double balanceAmountValue, String balanceCurrencyValue, String frequencyValue,
			String dueDateValue, String urlValue, String loginNameValue, String passwordValue,
			String includeInNetWorthValue, String isAssetValue, String memoValue, int status, String resFile,
			String filePath, Long defectId, String enabled) throws JSONException {

		evaluateInsurance(testCaseId, testCase, accountTypeValue, accountNameValue, nicknameValue, accountNumberValue,
				amountDue, currencyDueValue, balanceAmountValue, balanceCurrencyValue, frequencyValue, dueDateValue,
				urlValue, loginNameValue, passwordValue, includeInNetWorthValue, isAssetValue, memoValue, status,
				resFile, filePath, enabled);

	}

	private void evaluateInsurance(String testCaseId, String testCase, String accountTypeValue, String accountNameValue,
			String nicknameValue, String accountNumberValue, Double amountDue, String currencyDueValue,
			Double balanceAmountValue, String balanceCurrencyValue, String frequencyValue, String dueDateValue,
			String urlValue, String loginNameValue, String passwordValue, String includeInNetWorthValue,
			String isAssetValue, String memoValue, int status, String resFile, String filePath, String enabled) {
		commonUtils.isTCEnabled(enabled, testCaseId);
		System.out.println("TestCase Id is " + testCaseId + " testCase is " + testCase);
		// DueDate must be either current date or future date.So 0,0,0 means
		// yyyy-mm-dd(current date)
		dueDateValue = commonUtils.getOffsetDate(dueDateValue);

		/*
		 * String params =
		 * CreateJSONObject.createManualActObjForInsuranceContainer(accountTypeValue,
		 * accountNameValue, nicknameValue, accountNumberValue, loginNameValue,
		 * passwordValue, frequencyValue, dueDateValue, memoValue, urlValue,
		 * includeInNetWorthValue, isAssetValue, balanceAmountValue,
		 * balanceCurrencyValue, amountDue, currencyDueValue);
		 */

		String params = accountsHelper.createManualActObjForInsuranceContainer(accountTypeValue, accountNameValue,
				nicknameValue, accountNumberValue, loginNameValue, passwordValue, frequencyValue, dueDateValue,
				memoValue, urlValue, includeInNetWorthValue, isAssetValue, balanceAmountValue, balanceCurrencyValue,
				amountDue, currencyDueValue);
		/*
		 * Response response = RestUtil.postRequestWithBodyParam(params,
		 * InitialLoader.cobSessionObj.getPath() + Constants.ACCOUNTS_URL,
		 * InitialLoader.cobSessionObj.getCobSession(),
		 * InitialLoader.cobSessionObj.getUserSession()); response.then().log().all();
		 */

		HttpMethodParameters httpParams = HttpMethodParameters.builder().build();
		httpParams.setBodyParams(params);
		Response response = accountUtils.addManualAccount(httpParams, configurationObj.getCobrandSessionObj());

		// CommonUtils.saveJson(response.prettyPrint(), filePath, resFile);
		if (response.statusCode() == status)
			// CommonUtils.assertJSON(response, status, resFile, filePath);
			jsonAssertionUtil.assertResponse(response, status, resFile, filePath);

		else {
			Assert.assertFalse(testCaseId + "::" + testCase + " is Failed", true);
		}
	}

	public String accMasking(String accountNumber) {
		System.out.println("Account Number in Yodlee " + accountNumber);
		int len = accountNumber.length();
		String accNum = "";
		int i;
		for (i = 0; i < 8 - Configuration.getInstance().getMaskLength(); i++)
			accNum += Configuration.getInstance().getMaskCharacter();
		accountNumber = accountNumber.substring(len - Configuration.getInstance().getMaskLength(), len);
		accNum += accountNumber;
		System.out.println("Masked Account Number: " + accNum);
		return accNum;
	}

}
