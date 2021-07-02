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

package com.yodlee.yodleeApi.Holdings;

import org.databene.benerator.anno.Source;
import org.json.JSONException;
import org.testng.annotations.Test;

import com.yodlee.yodleeApi.assertions.JsonAssertionUtil;
import com.yodlee.yodleeApi.common.Base;
import com.yodlee.yodleeApi.common.Configuration;
import com.yodlee.yodleeApi.helper.SessionHelper;
import com.yodlee.yodleeApi.interfaces.Session;
import com.yodlee.yodleeApi.pojo.EnvSession;
import com.yodlee.yodleeApi.utils.apiUtils.HoldingUtils;
import com.yodlee.yodleeApi.utils.commonUtils.CommonUtils;

import io.restassured.response.Response;


/**
 * @author bhuvaneshwari
 *
 */
public class TestHoldingTypeList extends Base {

	CommonUtils commonUtils = new CommonUtils();
	HoldingUtils holdingUtils = new HoldingUtils();
	@Test(enabled = true, dataProvider = "feeder", alwaysRun = true)
	@Source("\\TestData\\CSVFiles\\Holdings\\HoldingTypeList.csv")
	public void HoldingType(String testCaseId, String TestCase, String API, String cobSessionKey,
			String cobSessionValue, String userSessionKey, String userSessionValue, String resFile, String filePath,
			int status, String enabled) throws JSONException, InterruptedException {
		
		commonUtils.isTCEnabled(enabled, testCaseId);
		System.out.println("Test Case Id is " + testCaseId + " test case is " + TestCase);
		Configuration configurationObj = Configuration.getInstance();

		/*Session session = new EnvSession(SessionHelper.getSessionToken(cobSessionValue, "cob"),
				SessionHelper.getSessionToken(userSessionValue, "user"),
				configurationObj.getCobrandSessionObj().getPath(), cobSessionKey, userSessionKey);*/
		
		EnvSession session = EnvSession.builder().cobSession(SessionHelper.getSessionToken(cobSessionValue, "cob"))
				.userSession(SessionHelper.getSessionToken(userSessionValue, "user")).cobSessionKey(cobSessionKey)
				.userSessionKey(userSessionKey).path(configurationObj.getCobrandSessionObj().getPath()).build();

		Response response = holdingUtils.getHoldingType(session);
		JsonAssertionUtil jsonAssertionUtil = new JsonAssertionUtil();
		jsonAssertionUtil.assertLenientJSON(response, status, resFile, filePath);
	}

}
