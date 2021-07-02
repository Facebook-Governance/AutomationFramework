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

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.apache.commons.configuration.HierarchicalConfiguration;
import org.databene.benerator.anno.Source;
import org.json.JSONArray;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.yodlee.taas.annote.Feature;
import com.yodlee.taas.annote.FeatureName;
import com.yodlee.taas.annote.Info;
import com.yodlee.taas.annote.SubFeature;
import com.yodlee.yodleeApi.common.Base;
import com.yodlee.yodleeApi.common.Configuration;
import com.yodlee.yodleeApi.constants.Constants;
import com.yodlee.yodleeApi.helper.AccountsHelper;
import com.yodlee.yodleeApi.pojo.AdditionalDataSet;
import com.yodlee.yodleeApi.pojo.HttpMethodParameters;
import com.yodlee.yodleeApi.sdg.ProcessSdg;
import com.yodlee.yodleeApi.sdg.RequestSequence;
import com.yodlee.yodleeApi.sdg.SdgHelper;
import com.yodlee.yodleeApi.utils.apiUtils.AccountUtils;
import com.yodlee.yodleeApi.utils.commonUtils.CommonUtils;

import io.restassured.response.Response;

/**
 * MorganStanley New Account Type for Loan is defined in this Catalog and used
 * as DagUN and DagPassword. So providing these credentials in XML
 * 
 * @author Rahul Kumar
 *
 */
@Feature(featureName = { FeatureName.ACCOUNTS })
public class TestGetAccounts extends Base {

	public static final String VALID_LOAN_ACCOUNTS = "\\TestData\\CSVFiles\\Sdg\\Accounts\\testGetAccountsforLoan.csv";
	public static final String VALID_INVESTMENT_ACCOUNTS = "\\TestData\\CSVFiles\\Sdg\\Accounts\\testGetAccountsforInvestment.csv";
	CommonUtils commonUtils = new CommonUtils();
	AccountsHelper accountsHelper = new AccountsHelper();
	SdgHelper sdgHelper = new SdgHelper();
	AccountUtils accountUtils = new AccountUtils();

	Long providerAccountId;
	Response getProvidersResponse;
	// String dagSiteUserName = "Testme.site16441.7";
	// String dagSitePassword = "site16441.7";
	// Long siteId = 16641L;

	@BeforeClass
	public void testSetUp() throws IOException {

		String filePath = "..\\src\\test\\resources\\TestData\\XML\\Accounts\\";
		String responseFile = "DataSetForAddAccount.xml";
		System.out.println("Response file path is::" + filePath + responseFile);
		String siteUserName = null;
		String sitePwd = null;
		String siteId = null;

		// Loading XML and populating dataset values
		ProcessSdg processSdg = new ProcessSdg(filePath + responseFile);
		List<Object> listOfMaps = processSdg.list;
		List<AdditionalDataSet> additionalDataSets = null;
		if (!listOfMaps.isEmpty()) {

			Map<String, Map<String, Object>> xmlTagWithDetailsMap = (Map<String, Map<String, Object>>) listOfMaps
					.get(0);
			if (xmlTagWithDetailsMap.containsKey("add-provider-accounts1")) {

				Map<String, Object> requestMapDetails = xmlTagWithDetailsMap.get("add-provider-accounts1");

				if (requestMapDetails.containsKey("siteUser"))
					siteUserName = requestMapDetails.get("siteUser").toString();

				if (requestMapDetails.containsKey("sitePwd"))
					sitePwd = requestMapDetails.get("sitePwd").toString();

				if (requestMapDetails.containsKey("siteId"))
					siteId = requestMapDetails.get("siteId").toString();

				System.out.println("dataList.dataset value is==" + requestMapDetails.get("dataset"));
				if (requestMapDetails.get("dataset") != null
						&& !requestMapDetails.get("dataset").toString().isEmpty()) {
					RequestSequence requestSequence = RequestSequence.getInstance();
					additionalDataSets = requestSequence
							.getDataSet((List<HierarchicalConfiguration>) requestMapDetails.get("dataset"));
				}

				// Adding Sdg Account
				providerAccountId = sdgHelper.addProviderAccountSdg(null,
						null, siteUserName, sitePwd, siteId, additionalDataSets,
						Configuration.getInstance().getCobrandSessionObj(), "fieldarray", Constants.DATA_SET);
				System.out.println("providerAccountId value testclass is::"+providerAccountId);
			}

		}

		if (providerAccountId == 0) {
			throw new RuntimeException("providerAccountId is not present after account addition");

		}
	}

	@Test(dataProvider = Constants.FEEDER, alwaysRun = true, priority = 1)
	@Source(VALID_LOAN_ACCOUNTS)
	@Info(subfeature = { SubFeature.GET_ACCOUNTS }, userStoryId = "")
	public void testGetAccountsforLoan(String tcId, String tcName, String container, String accountTypeExpected,
			String defectId, String enabled) throws FileNotFoundException {

		logger.info("********* Priority 1 *****************");
		commonUtils.isTCEnabled(enabled, tcName);

		System.out.println("TestCase id is " + tcId + " TestCase Name is " + tcName);

		// Creating HttpMethodParameter
		Map<String, Object> queryParam = accountsHelper.createQueryParamsForGetAccounts(null, container,
				String.valueOf(providerAccountId), null, null, null, null);
		HttpMethodParameters httpMethodParameters = HttpMethodParameters.builder().build();
		httpMethodParameters.setQueryParams(queryParam);

		Response getAccountsResponse = accountUtils.getAccounts(httpMethodParameters,
				Configuration.getInstance().getCobrandSessionObj());

		if (getAccountsResponse != null) {

			JSONObject responseObj = new JSONObject(getAccountsResponse.asString());
			if (responseObj.toString().contains("account")) {
				JSONArray accountsArray = responseObj.getJSONArray("account");
				JSONObject accountObject = accountsArray.getJSONObject(0);
				String accountType = accountObject.getString("accountType");
				Assert.assertEquals(accountType, accountTypeExpected, "Wrong value for accountType");
			} else {
				Assert.fail("*************GET_ACCOUNT response is coming as EMPTY**************");
			}
		}
	}

	@Test(dataProvider = Constants.FEEDER, alwaysRun = true, priority = 2)
	@Source(VALID_INVESTMENT_ACCOUNTS)
	@Info(subfeature = { SubFeature.GET_ACCOUNTS }, userStoryId = "")
	public void testGetAccountsforInvestment(String tcId, String tcName, String container, String accountTypeExpected,
			String defectId, String enabled) throws FileNotFoundException {

		logger.info("********* Priority 2 *****************");
		commonUtils.isTCEnabled(enabled, tcName);

		System.out.println("TestCase id is " + tcId + " TestCase Name is " + tcName);

		// Creating HttpMethodParameter
		Map<String, Object> queryParam = accountsHelper.createQueryParamsForGetAccounts(null, container,
				String.valueOf(providerAccountId), null, null, null, null);
		HttpMethodParameters httpMethodParameters = HttpMethodParameters.builder().build();
		httpMethodParameters.setQueryParams(queryParam);

		Response getAccountsResponse = accountUtils.getAccounts(httpMethodParameters,
				Configuration.getInstance().getCobrandSessionObj());

		if (getAccountsResponse != null) {

			JSONObject responseObj = new JSONObject(getAccountsResponse.asString());
			if (responseObj.toString().contains("account")) {
				JSONArray accountsArray = responseObj.getJSONArray("account");
				JSONObject accountObject = accountsArray.getJSONObject(0);
				String accountType = accountObject.getString("accountType");
				Assert.assertEquals(accountType, accountTypeExpected, "Wrong value for accountType");
			} else {
				Assert.fail("*************GET_ACCOUNT response is coming as EMPTY**************");
			}
		}

	}
}
