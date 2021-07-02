
/*******************************************************************************
*
* * Copyright (c) 2019 Yodlee, Inc. All Rights Reserved.
*
* 
 *
* * This software is the confidential and proprietary information of Yodlee, Inc.
*
* * Use is subject to license terms.
*
*******************************************************************************/

package com.yodlee.yodleeApi.sdg.script;

import java.sql.Connection;
import java.text.SimpleDateFormat;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.databene.benerator.anno.Source;
import org.json.JSONArray;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.yodlee.DBHelper;
import com.yodlee.taas.annote.Feature;
import com.yodlee.taas.annote.FeatureName;
import com.yodlee.yodleeApi.common.Base;
import com.yodlee.yodleeApi.common.Configuration;
import com.yodlee.yodleeApi.constants.Constants;
import com.yodlee.yodleeApi.constants.JsonPath;
import com.yodlee.yodleeApi.constants.TestGroup;
import com.yodlee.yodleeApi.database.DbQueries;
import com.yodlee.yodleeApi.exceptions.UnexpectedFunctionalityException;
import com.yodlee.yodleeApi.helper.AccountsHelper;
import com.yodlee.yodleeApi.helper.DbHelper;
import com.yodlee.yodleeApi.helper.SessionHelper;
import com.yodlee.yodleeApi.pojo.AdditionalDataSet;
import com.yodlee.yodleeApi.pojo.EnvSession;
import com.yodlee.yodleeApi.pojo.HttpMethodParameters;
import com.yodlee.yodleeApi.pojo.User;
import com.yodlee.yodleeApi.sdg.SdgHelper;
import com.yodlee.yodleeApi.utils.apiUtils.DataExtractUtils;
import com.yodlee.yodleeApi.utils.apiUtils.ProviderAccountUtils;
import com.yodlee.yodleeApi.utils.apiUtils.UserUtils;
import com.yodlee.yodleeApi.utils.commonUtils.CommonUtils;

import io.restassured.response.Response;

/**
 * @author Namitha N (TIPS)-->Revised by Rahul Kumar
 *
 */
@Feature(featureName = { FeatureName.SDG })
public class TestStudentLoanNewFields extends Base {

	public static final String testStudentLoanNewFields = "\\TestData\\CSVFiles\\StudentLoan\\NewFields.csv";
	Connection conn = null;
	Response getProvidersResponse;
	EnvSession envSessionObj = null;
	AccountsHelper accountsHelper = new AccountsHelper();
	String filePath = "..\\src\\test\\resources\\TestData\\XML\\StudentLoan\\";
	String fromDateForDEEnabled_Implicitly, toDateForDEEnabled_Implicitly, loanAcct1 = "LoanAccount1",
			loanAcct2 = "LoanAccount3", guarantorPresentMsg = "guarantor is present in Get Accounts API Response",
			guarantorInvalidMsg = "Wrong value for guarantor in Get Accounts API Response",
			lendorPresentMsg = "lender is present in Get Accounts API Response",
			lendorInvalidMsg = "Wrong value for lender in Get Accounts API Response",
			loanStatusPresentMsg = "sourceAccountStatus is present in Get Accounts API Response",
			loanStatusInvalidMsg = "Wrong value for sourceAccountStatus in Get Accounts API Response",
			repaymentPlanTypePresentMsg = "repaymentPlanType is present in Get Accounts API Response",
			repaymentPlanTypeInvalidMsg = "Wrong value for repaymentPlanType in Get Accounts API Response",
			guarantorInvalidMsg1 = "Wrong value for guarantor in data Extract response",
			lendorInvalidMsg1 = "Wrong value for lender in data Extract response",
			loanStatusInvalidMsg1 = "Wrong value for sourceAccountStatus in data Extract response",
			repaymentPlanTypeInvalidMsg1 = "Wrong value for repaymentPlanType in data Extract response";

	@BeforeClass
	public void testSetUp() throws Exception {
		System.out.println(
				" ==== DB Check for new table repayment_plan_type,loan_status and columns introduced in loan_ext table ==== ");
		DBHelper dbHelper1 = new DBHelper();
		conn = dbHelper1.getDBConnection();

		DbHelper dbHelper = new DbHelper();
		Object value = dbHelper.getValueFromDB(DbQueries.GET_REPAYMENT_COUNT);
		Assert.assertTrue(value.equals(8),
				" Failed as number of rows is not matching for repayment_plan_type table  in DB");
		Object value1 = dbHelper.getValueFromDB(DbQueries.GET_STATUS_COUNT);
		Assert.assertTrue(value1.equals(7), " Failed as number of rows is not matching for loan_status table in DB");
		try {
			dbHelper.getResultSet(conn, DbQueries.GET_REPAYMENTPLAN_TYPE);
			dbHelper.getResultSet(conn, DbQueries.GET_LOANSTATUS_ID);
		} catch (Exception e) {
			e.printStackTrace();

			Assert.fail("LoanStatusId or RepaymentPlanTypeId coulmn is not present in loan_ext table");
		} finally {
			if (conn != null) {
				conn.close();
			}
		}
	}

	@Source(testStudentLoanNewFields)
	@Test(enabled = true, groups = { TestGroup.SDG, TestGroup.REGRESSION }, dataProvider = Constants.FEEDER)
	public void testStudentLoanNewFields(String tcId, String tcName, String datasetFile, String datasetFile1,
			String lender1, String guarantor1, String loanStatus1, String repayment1, String lender2, String guarantor2,
			String loanStatus2, String repayment2, String isUpdate, String container, String priority, String enabled)
			throws Exception {

		CommonUtils commonUtils = new CommonUtils();
		commonUtils.isTCEnabled(enabled, tcName);
		System.out.println(
				"**********************************************************************************************************************");
		System.out.println("TestCase : " + tcName + "");
		System.out.println(
				"**********************************************************************************************************************");

		SessionHelper sessionHelper = new SessionHelper();
		// Register a new user.
		System.out.println("User Registration Started");
		String loanUserName = "TestYSLSdg" + "LoanNewField" + System.currentTimeMillis();
		String loanPassword = "STULoan@123";

		User userInfo = User.builder().build();
		userInfo.setUsername(loanUserName);
		userInfo.setPassword(loanPassword);
		userInfo.setLocale(Constants.LOCALE_US);

		try {

			// User Registration
			envSessionObj = sessionHelper.getSessionObjectByUserRegistration(userInfo);

			List<AdditionalDataSet> dataSet = accountsHelper.getAdditionalDataSet(filePath + datasetFile);

			// Add provider with specified dataset
			SdgHelper sdgHelper = new SdgHelper();
			long providerAccountId = sdgHelper.addProviderAccountSdg(null, null, loanUserName, loanPassword, "19686",
					dataSet, Configuration.getInstance().getCobrandSessionObj(), "fieldarray", "dataset");

			Thread.sleep(20000);

			if (providerAccountId == 0l) {
				throw new UnexpectedFunctionalityException("providerAccountId is not present after account addition");
			}
			System.out.println("provider acct id :" + providerAccountId);

			// Get Accounts
			Map<String, String> queryParam = new LinkedHashMap<>();
			queryParam.put("container", container);
			Response getAccountsResponse = null;

			HttpMethodParameters httpMethodParameters = HttpMethodParameters.builder().build();
			httpMethodParameters.setQueryParams(queryParam);
			ProviderAccountUtils providerAccountUtils = new ProviderAccountUtils();
			getAccountsResponse = providerAccountUtils.getProviderAcctDetails(httpMethodParameters, envSessionObj);
			JSONObject obj = new JSONObject(getAccountsResponse.asString());

			// Check if update flow exists or not
			if (isUpdate.equalsIgnoreCase("FALSE")) {
				JSONArray accountsArray = obj.getJSONArray(JsonPath.ACCOUNT);
				for (int i = 0; i < accountsArray.length(); i++) {
					JSONObject accountObject = accountsArray.getJSONObject(i);
					if (accountObject.getString("accountName").equalsIgnoreCase(loanAcct1)) {
						assertValues(accountObject, guarantor1, lender1, loanStatus1, repayment1);
					} else if (accountObject.getString("accountName").equalsIgnoreCase(loanAcct2)) {
						assertValues(accountObject, guarantor2, lender2, loanStatus2, repayment2);
					}
				}

			} else {
				JSONArray accountsArray = obj.getJSONArray(JsonPath.ACCOUNT);
				for (int i = 0; i < accountsArray.length(); i++) {
					JSONObject accountObject = accountsArray.getJSONObject(i);
					Assert.assertTrue(!accountObject.toString().contains("guarantor"), guarantorPresentMsg);
					Assert.assertTrue(!accountObject.toString().contains("lender"), lendorPresentMsg);
					Assert.assertTrue(!accountObject.toString().contains("sourceAccountStatus"), loanStatusPresentMsg);
					Assert.assertTrue(!accountObject.toString().contains("repaymentPlanType"),
							repaymentPlanTypePresentMsg);
				}

				List<AdditionalDataSet> dataSet1 = accountsHelper.getAdditionalDataSet(filePath + datasetFile1);

				// Update provider with specified dataset
				String loginForm1 = sdgHelper.getLoginFormWithDataSetOrDataSetName(Long.parseLong("19686"),
						loanUserName, loanPassword, "fieldarray", dataSet1, "dataset");
				Map<String, Object> queryParams = new LinkedHashMap<>();
				queryParams.put("providerAccountIds", providerAccountId);

				HttpMethodParameters httpParams = HttpMethodParameters.builder().build();
				httpParams.setBodyParams(loginForm1);
				httpParams.setQueryParams(queryParams);
				providerAccountUtils.updateProviderAccount(httpParams, envSessionObj);

				Thread.sleep(20000);

				// Get Accounts
				Map<String, String> queryParam1 = new LinkedHashMap<>();
				queryParam1.put("container", container);
				Response getAccountsResponse1 = null;

				HttpMethodParameters httpMethodParameters1 = HttpMethodParameters.builder().build();
				httpMethodParameters1.setQueryParams(queryParam1);
				getAccountsResponse1 = providerAccountUtils.getProviderAcctDetails(httpMethodParameters1,
						envSessionObj);

				JSONObject obj1 = new JSONObject(getAccountsResponse1.asString());
				JSONArray accountsArray1 = obj1.getJSONArray(JsonPath.ACCOUNT);
				for (int i = 0; i < accountsArray1.length(); i++) {
					JSONObject accountObject1 = accountsArray1.getJSONObject(i);
					if (accountObject1.getString("accountName").equalsIgnoreCase(loanAcct1)) {
						assertValues(accountObject1, guarantor1, lender1, loanStatus1, repayment1);

					} else if (accountObject1.getString("accountName").equalsIgnoreCase(loanAcct2)) {
						assertValues(accountObject1, guarantor2, lender2, loanStatus2, repayment2);
					}
				}

			}

		} catch (Exception e) {
			e.printStackTrace();
			Assert.fail("Failed to execute userRegistraion() case due to : " + e);

		}

		if (tcName.equalsIgnoreCase("Add account with ADVANCE_AGG_DATA : PAYMENT_DETAILS with container")) {

			Response userDataResponse = checkUserResponse(envSessionObj, loanUserName);

			if (userDataResponse.jsonPath().get("userData[0].account[0].accountName").toString()
					.equalsIgnoreCase(loanAcct1)) {
				assertValuesInDataExtractRes(userDataResponse, guarantor1, lender1, loanStatus1, repayment1);
			} else if (userDataResponse.jsonPath().get("userData[0].account[0].accountName").toString()
					.equalsIgnoreCase(loanAcct2)) {
				assertValuesInDataExtractRes(userDataResponse, guarantor2, lender2, loanStatus2, repayment2);
			}

		}
		UserUtils userUtils = new UserUtils();
		userUtils.unRegisterUser(envSessionObj);
	}

	public void assertValues(JSONObject accountObject, String guarantorValue, String lenderValue,
			String loanStatusValue, String repaymentPlanTypeValue) {
		if (guarantorValue.isEmpty()) {
			Assert.assertTrue(!accountObject.toString().contains("guarantor"), guarantorPresentMsg);
		} else {
			Assert.assertEquals(accountObject.getString("guarantor"), guarantorValue, guarantorInvalidMsg);
		}
		if (lenderValue.isEmpty()) {
			Assert.assertTrue(!accountObject.toString().contains("lender"), lendorPresentMsg);
		} else {
			Assert.assertEquals(accountObject.getString("lender"), lenderValue, lendorInvalidMsg);
		}
		if (loanStatusValue.isEmpty()) {
			Assert.assertTrue(!accountObject.toString().contains("sourceAccountStatus"), loanStatusPresentMsg);
		} else {
			Assert.assertEquals(accountObject.getString("sourceAccountStatus"), loanStatusValue, loanStatusInvalidMsg);
		}
		if (repaymentPlanTypeValue.isEmpty()) {
			Assert.assertTrue(!accountObject.toString().contains("repaymentPlanType"), repaymentPlanTypePresentMsg);
		} else {
			Assert.assertEquals(accountObject.getString("repaymentPlanType"), repaymentPlanTypeValue,
					repaymentPlanTypeInvalidMsg);
		}
	}

	public void assertValuesInDataExtractRes(Response userDataResponse, String guarantorValue, String lenderValue,
			String loanStatusValue, String repaymentPlanTypeValue) {
		Assert.assertEquals(userDataResponse.jsonPath().get("userData[0].account[0].guarantor").toString(),
				guarantorValue, guarantorInvalidMsg1);
		Assert.assertEquals(userDataResponse.jsonPath().get("userData[0].account[0].lender").toString(), lenderValue,
				lendorInvalidMsg1);
		Assert.assertEquals(userDataResponse.jsonPath().get("userData[0].account[0].sourceAccountStatus").toString(),
				loanStatusValue, loanStatusInvalidMsg1);
		Assert.assertEquals(userDataResponse.jsonPath().get("userData[0].account[0].repaymentPlanType").toString(),
				repaymentPlanTypeValue, repaymentPlanTypeInvalidMsg1);
	}

	private Response checkUserResponse(EnvSession session, String username) {

		CommonUtils commonUtils = new CommonUtils();
		fromDateForDEEnabled_Implicitly = new SimpleDateFormat(Constants.ISO_FORMAT)
				.format(commonUtils.addSecondsToDate(commonUtils.getUTCtime(), -30));
		toDateForDEEnabled_Implicitly = new SimpleDateFormat(Constants.ISO_FORMAT).format(commonUtils.getUTCtime());

		// Calling getUserData
		DataExtractUtils dataExtractUtils = new DataExtractUtils();
		Map<String, Object> queryParams = new LinkedHashMap<>();
		queryParams.put("eventName", "DATA_UPDATES");
		queryParams.put("loginName", username);
		queryParams.put("fromDate", fromDateForDEEnabled_Implicitly);
		queryParams.put("toDate", toDateForDEEnabled_Implicitly);
		HttpMethodParameters httpMethodParameters1 = HttpMethodParameters.builder().build();
		httpMethodParameters1.setQueryParams(queryParams);

		Response userDataResponse = dataExtractUtils.getUserData(httpMethodParameters1,
				Configuration.getInstance().getCobrandSessionObj());

		return userDataResponse;

	}
}
