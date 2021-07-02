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

package com.yodlee.yodleeApi.sdg.script;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.databene.benerator.anno.Source;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import com.yodlee.taas.annote.Feature;
import com.yodlee.taas.annote.FeatureName;
import com.yodlee.yodleeApi.common.Base;
import com.yodlee.yodleeApi.constants.Constants;
import com.yodlee.yodleeApi.constants.JSONPaths;
import com.yodlee.yodleeApi.constants.TestGroup;
import com.yodlee.yodleeApi.database.DbQueries;
import com.yodlee.yodleeApi.helper.AccountsHelper;
import com.yodlee.yodleeApi.helper.DbHelper;
import com.yodlee.yodleeApi.helper.SessionHelper;
import com.yodlee.yodleeApi.pojo.AdditionalDataSet;
import com.yodlee.yodleeApi.pojo.EnvSession;
import com.yodlee.yodleeApi.pojo.HttpMethodParameters;
import com.yodlee.yodleeApi.pojo.User;
import com.yodlee.yodleeApi.sdg.ProcessSdg;
import com.yodlee.yodleeApi.sdg.SdgHelper;
import com.yodlee.yodleeApi.sdg.SdgJSONCreation;
import com.yodlee.yodleeApi.utils.apiUtils.ProviderAccountUtils;
import com.yodlee.yodleeApi.utils.apiUtils.UserUtils;
import com.yodlee.yodleeApi.utils.commonUtils.CommonUtils;

import io.restassured.response.Response;

/**
 * This class doesn't run with generic configuration this requires below
 * configuration:
 * 
 * @author uChouhan- Revised by Rahul Kumar
 *
 */
@Feature(featureName = { FeatureName.SDG })
public class TestCoverageAttribute extends Base {

	public static final String testCoverageAttributeSdg = "\\TestData\\CSVFiles\\Coverage\\CoverageTestCases.csv";
	public static final String testCoverageAttributeSdg1 = "\\TestData\\CSVFiles\\Coverage\\CoverageTestCases1.csv";
	public static final String testCoverageAttributeSdg2 = "\\TestData\\CSVFiles\\Coverage\\CoverageTestCases2.csv";

	EnvSession envSessionObj = null;

	@Source(testCoverageAttributeSdg)
	@Test(enabled = true, groups = { TestGroup.SDG, TestGroup.REGRESSION }, dataProvider = "feeder")
	public void testCoverage(String tcId, String tcName, String sequence, String sequenceFilePath,
			String sequenceResFile, String subrand, String cobrand, String enabled, String usTestcaseId,
			String priority, String loginFormType) throws Exception {

		CommonUtils commonUtils = new CommonUtils();
		SessionHelper sessionHelper = new SessionHelper();
		System.out.println("################ Execution of Test case :" + tcName + "#####################");
		commonUtils.isTCEnabled(enabled, tcName);

		// Creating a new user.
		System.out.println("User Registration Started");
		String metricUserName = "TestYSLSdg" + "Metric" + System.currentTimeMillis();
		String metricPassword = "Metric@123";

		User userInfo = User.builder().build();
		userInfo.setUsername(metricUserName);
		userInfo.setPassword(metricPassword);
		userInfo.setLocale(Constants.LOCALE_US);

		try {
			// User Registration
			envSessionObj = sessionHelper.getSessionObjectByUserRegistration(userInfo);

			// Executing sdg Test case
			String xmlFile = sequenceFilePath + sequenceResFile;
			ProcessSdg processSdg = new ProcessSdg(xmlFile);
			processSdg.processSdgXml(sequence, loginFormType, envSessionObj);

		} catch (Exception e) {
			e.printStackTrace();
			Assert.fail("Failed to execute userRegistraion() case due to : " + e);
		}
	}

	@Test(description = "AT-99034,AT-99035,AT-99036,AT-99037,AT-99038,AT-99039,AT-99039:DB verification", enabled = true)
	public void TestDbEntries() throws Exception {

		DbHelper dbHelper = new DbHelper();

		SoftAssert sAssert = new SoftAssert();
		Object attributeName = dbHelper.getValueFromDB(DbQueries.GET_ATTRIBUTE_VALUE + "16", "ATTRIBUTE_NAME");
		Object suminfo = (String) dbHelper.getValueFromDB(DbQueries.GET_SUM_INFO_CONFIG_REF + "16",
				"SUM_INFO_CONFIG_REF");
		List<Object> coverageAmountType = dbHelper.getCollectionValueFromDB(DbQueries.GET_COVERAGE_AMOUNT_TYPE, null,
				"COVERAGE_AMOUNT_TYPE", 1);
		List<Object> coverageType = dbHelper.getCollectionValueFromDB(DbQueries.GET_COVERAGE_TYPE, null,
				"COVERAGE_TYPE", 1);
		List<Object> tagId = dbHelper.getCollectionValueFromDB(DbQueries.GET_TAG_ID + "16", null, "TAG_ID", 1);
		List<Object> frequency = dbHelper.getCollectionValueFromDB(DbQueries.GET_REFRESH_FREQUENCY + "16", null,
				"REFRESH_FREQUENCY", 1);
		sAssert.assertTrue(attributeName.toString().equalsIgnoreCase("ADVANCE_AGG_DATA.COVERAGE"),
				"ADVANCE_AGG_DATA.COVERAGE isnt present in dataset_attributes");
		sAssert.assertTrue(
				suminfo.toString().contains("COM.YODLEE.CORE.SDG.ATTRIBUTE.ADVANCE_AGG_DATA.COVERAGE_SUPPORTED"),
				"COVERAGE_SUPPORTED isnt present sum info");
		sAssert.assertTrue(coverageAmountType.contains("CoverageAmount"),
				"CoverageAmount isnt found in coverageAmountType ");
		sAssert.assertTrue(coverageAmountType.contains("MonthlyBenefit"),
				"MonthlyBenefit isnt found in coverageAmountType");
		sAssert.assertTrue(coverageType.contains("DeathCover"), "DeathCover isnt found in coverageType");
		sAssert.assertTrue(coverageType.contains("TotalPermanentDisability"),
				"TotalPermanentDisability isnt found in coverageType");
		sAssert.assertTrue(coverageType.contains("AccidentalDeathCover"),
				"TotalPermanentDisability isnt found in coverageType");
		sAssert.assertTrue(coverageType.contains("IncomeProtection"), "IncomeProtection isnt found in coverageType");
		sAssert.assertTrue(coverageType.contains("DeathTotalPermanentDisability"),
				"DeathTotalPermanentDisability isnt found in coverageType");
		sAssert.assertTrue(tagId.toString().contains("12"), "tagId 12 isnt found ");
		sAssert.assertTrue(tagId.toString().contains("84"), "tagId 84 isnt found");
		sAssert.assertTrue(frequency.toString().contains("2592000"), "refrence frequency 2592000 isnt found");
		sAssert.assertAll();
	}

	@Source(testCoverageAttributeSdg1)
	@Test(enabled = true, groups = { TestGroup.SDG, TestGroup.REGRESSION }, dataProvider = "feeder")
	public void refreshFrequencyCheckForInsurance(String tcId, String tcName, String sequenceFilePath,
			String sequenceResFile) throws InterruptedException {

		String cUserName = "Coverage" + System.currentTimeMillis();
		String cPassword = "TEST@123";

		System.out.println("####### Execution of Test case in refreshFrequencyCheckForInsurance() :" + tcId + "######");

		User userInfo = User.builder().build();
		userInfo.setUsername(cUserName);
		userInfo.setPassword(cPassword);
		userInfo.setLocale(Constants.LOCALE_US);

		SessionHelper sessionHelper = new SessionHelper();
		EnvSession envSessionObj = sessionHelper.getSessionObjectByUserRegistration(userInfo);
		SdgHelper sdgHelper = new SdgHelper();
		AccountsHelper accountsHelper = new AccountsHelper();

		try {
			System.out.println("path is::::==="+(sequenceFilePath + sequenceResFile));
			List<AdditionalDataSet> additionalDataSet = accountsHelper
					.getAdditionalDataSet(sequenceFilePath + sequenceResFile);
			String loginForm = sdgHelper.getLoginFormWithDataSetOrDataSetName(Long.parseLong("19686"), cUserName,
					cPassword, "fieldarray", additionalDataSet, "dataset");

			// Preparing httpParams
			SdgJSONCreation sdgJSONCreation = new SdgJSONCreation();
			LinkedHashMap<String, Object> param = sdgJSONCreation.passOneQueryParam("providerId", "19686");
			HttpMethodParameters httpParms = HttpMethodParameters.builder().build();
			httpParms.setBodyParams(loginForm);
			httpParms.setQueryParams(param);

			// Hitting API:POST /providerAccounts
			ProviderAccountUtils providerAccountUtils = new ProviderAccountUtils();
			Response response = providerAccountUtils.addProviderAccount(httpParms, envSessionObj);
			String providerAccountId = response.jsonPath().get(JSONPaths.PROVIDER_ACCOUNT_ID).toString();
			Thread.sleep(10000);

			Map<String, String> queryParam = new LinkedHashMap<String, String>();
			queryParam.put("container", "insurance");

			HttpMethodParameters httpParms1 = HttpMethodParameters.builder().build();
			httpParms1.setQueryParams(queryParam);

			Response getAccountsResponse = providerAccountUtils.getProviderAcctDetails(httpParms1, envSessionObj);
			CommonUtils commonUtils = new CommonUtils();
			JSONObject obj = new JSONObject(getAccountsResponse.asString());
			JSONArray accountsArray = obj.getJSONArray("account");
			JSONObject accountObject = accountsArray.getJSONObject(0);
			JSONArray dataSetInfo = accountObject.getJSONArray("dataset");
			String nextUpdateDate = null;
			String lastUpdatedDate = null;
			for (int i = 0; i < dataSetInfo.length() - 1; i++) {
				if (dataSetInfo.getJSONObject(i).getString("name").equalsIgnoreCase("ADVANCE_AGG_DATA")) {
					nextUpdateDate = dataSetInfo.getJSONObject(i).getString("nextUpdateScheduled");// Inprogress right
																									// now
					lastUpdatedDate = dataSetInfo.getJSONObject(i).getString("lastUpdateAttempt");
					break;
				}
			}
			System.out.println("Next Update date : " + nextUpdateDate);
			System.out.println("Last Update date : " + lastUpdatedDate);
			int days = (int) commonUtils.daysDifference(nextUpdateDate, lastUpdatedDate);
			System.out.println("next update date in days : " + days);
			Assert.assertTrue(days <= 31 && days >= 28, "Next update date is less than 30 days");
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

	@Source(testCoverageAttributeSdg2)
	@Test(description = "AT-99051:refreshFrequencyCheckForInvestment", enabled = true)
	public void refreshFrequencyCheckForInvestment(String tcId, String tcName, String sequenceFilePath,
			String sequenceResFile) throws InterruptedException {

		String cUserName = "Coverage" + System.currentTimeMillis();
		String cPassword = "TEST@123";

		User userInfo = User.builder().build();
		userInfo.setUsername(cUserName);
		userInfo.setPassword(cPassword);
		userInfo.setLocale(Constants.LOCALE_US);

		SessionHelper sessionHelper = new SessionHelper();
		EnvSession envSessionObj = sessionHelper.getSessionObjectByUserRegistration(userInfo);
		SdgHelper sdgHelper = new SdgHelper();
		AccountsHelper accountsHelper = new AccountsHelper();

		try {
			System.out.println("path 2 is::::==="+(sequenceFilePath + sequenceResFile));

			List<AdditionalDataSet> additionalDataSet = accountsHelper
					.getAdditionalDataSet(sequenceFilePath + sequenceResFile);
			String loginForm = sdgHelper.getLoginFormWithDataSetOrDataSetName(Long.parseLong("19686"), cUserName,
					cPassword, "fieldarray", additionalDataSet, "dataset");

			// Preparing httpParams
			SdgJSONCreation sdgJSONCreation = new SdgJSONCreation();
			LinkedHashMap<String, Object> param = sdgJSONCreation.passOneQueryParam("providerId", "19686");
			HttpMethodParameters httpParms = HttpMethodParameters.builder().build();
			httpParms.setBodyParams(loginForm);
			httpParms.setQueryParams(param);

			// Hitting API:POST /providerAccounts
			ProviderAccountUtils providerAccountUtils = new ProviderAccountUtils();
			Response response = providerAccountUtils.addProviderAccount(httpParms, envSessionObj);
			String providerAccountId = response.jsonPath().get(JSONPaths.PROVIDER_ACCOUNT_ID).toString();
			Thread.sleep(10000);

			Map<String, String> queryParam = new LinkedHashMap<String, String>();
			queryParam.put("container", "investment");

			HttpMethodParameters httpParms1 = HttpMethodParameters.builder().build();
			httpParms1.setQueryParams(queryParam);

			Response getAccountsResponse = providerAccountUtils.getProviderAcctDetails(httpParms1, envSessionObj);

			JSONObject obj = new JSONObject(getAccountsResponse.asString());
			JSONArray accountsArray = obj.getJSONArray("account");
			JSONObject accountObject = accountsArray.getJSONObject(0);
			JSONArray dataSetInfo = accountObject.getJSONArray("dataset");
			String nextUpdateDate = null;
			String lastUpdatedDate = null;
			for (int i = 0; i < dataSetInfo.length() - 1; i++) {
				if (dataSetInfo.getJSONObject(i).getString("name").equalsIgnoreCase("ADVANCE_AGG_DATA")) {
					nextUpdateDate = dataSetInfo.getJSONObject(i).getString("nextUpdateScheduled");
					lastUpdatedDate = dataSetInfo.getJSONObject(i).getString("lastUpdateAttempt");
					break;
				}
			}
			System.out.println("Next Update date : " + nextUpdateDate);
			System.out.println("Last Update date : " + lastUpdatedDate);
			CommonUtils commonUtils = new CommonUtils();
			int days = (int) commonUtils.daysDifference(nextUpdateDate, lastUpdatedDate);
			System.out.println("next update date in days : " + days);
			Assert.assertTrue(days <= 31 && days >= 28, "Next update date is not a month gap from last updated date");
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

	@AfterClass(alwaysRun = true)
	public void unregister() {
		System.out.println("Doing unregistartion for userSession::=" + envSessionObj.getUserSession());
		UserUtils userUtils = new UserUtils();
		userUtils.unRegisterUser(envSessionObj);
	}

}
