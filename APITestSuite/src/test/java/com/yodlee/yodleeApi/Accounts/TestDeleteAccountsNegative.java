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

import java.util.Map;

import org.databene.benerator.anno.Source;
import org.json.JSONException;
import org.testng.annotations.Test;

import com.yodlee.yodleeApi.assertions.JsonAssertionUtil;
import com.yodlee.yodleeApi.common.Configuration;
import com.yodlee.yodleeApi.helper.AccountsHelper;
import com.yodlee.yodleeApi.helper.TransactionsHelper;
import com.yodlee.yodleeApi.pojo.HttpMethodParameters;
import com.yodlee.yodleeApi.utils.apiUtils.AccountUtils;
import com.yodlee.yodleeApi.utils.apiUtils.ProviderAccountUtils;
import com.yodlee.yodleeApi.utils.commonUtils.CommonUtils;
import com.yodlee.yodleeApi.common.Base;

import io.restassured.response.Response;


/**
 * @author mallikan
 *
 */
public class TestDeleteAccountsNegative extends Base {
	
	CommonUtils commonUtils = new CommonUtils();
	AccountUtils accountUtils = new AccountUtils();
	AccountsHelper accountsHelper = new AccountsHelper();
	JsonAssertionUtil jsonAssertionUtil = new JsonAssertionUtil();

	@Test(enabled = true, dataProvider = "feeder", alwaysRun = true)
	@Source("\\TestData\\CSVFiles\\Accounts\\DeleteAccounts\\Negative\\testDeleteAccount_ErrorMessage.csv")
	public void testDeleteAccount_ErrorMessage(String testCaseId, String accountId, String title, int status,
			String resFile, String enabled, String fileLocation) throws JSONException {

		commonUtils.isTCEnabled(enabled, testCaseId);
		Configuration configurationObj = Configuration.getInstance();

		// Creating Path Param
		HttpMethodParameters httpParams = accountsHelper.createPathParam("accountId", accountId);

		// Hitting API
		Response response = accountUtils.deleteAccount(httpParams, configurationObj.getCobrandSessionObj());

		try {
			jsonAssertionUtil.assertJSON(response, status, resFile, fileLocation);

		} catch (Exception e) {
			System.out.println(
					resFile + " is not found on ../src/test/resources/TestData/NamedAPI/ErrorMessage/GetNamedAPI/");
		}
	}
}
