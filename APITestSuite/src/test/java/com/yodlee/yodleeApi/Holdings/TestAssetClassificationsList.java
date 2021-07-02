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



import io.restassured.response.Response;
import org.databene.benerator.anno.Source;
import org.json.JSONException;
import org.testng.annotations.Test;

import com.yodlee.yodleeApi.assertions.JsonAssertionUtil;
import com.yodlee.yodleeApi.common.Configuration;
import com.yodlee.yodleeApi.helper.SessionHelper;
import com.yodlee.yodleeApi.interfaces.Session;
import com.yodlee.yodleeApi.pojo.EnvSession;
import com.yodlee.yodleeApi.utils.apiUtils.HoldingUtils;
import com.yodlee.yodleeApi.utils.commonUtils.CommonUtils;
import com.yodlee.yodleeApi.common.Base;



/**
 * @author bhuvaneshwari
 *
 */
public class TestAssetClassificationsList extends Base {
	
	CommonUtils commonUtils = new CommonUtils();
	HoldingUtils holdingUtils = new HoldingUtils();
	@Test(enabled = true, dataProvider = "feeder", alwaysRun = true)
	@Source("\\TestData\\CSVFiles\\Holdings\\assetClassificationList.csv")
	public void assetClassificationList(String testCaseId, String TestCase, String API, String cobSessionKey,
			String cobSessionValue, String userSessionKey, String userSessionValue, String resFile, String filePath,
			int status, String enabled, String method) throws JSONException, InterruptedException {
	
		commonUtils.isTCEnabled(enabled, testCaseId);
		Configuration configurationObj = Configuration.getInstance();

		System.out.println("Test Case Id is " + testCaseId + " test case is " + TestCase);

		if(userSessionKey.equalsIgnoreCase("valid")) {
			userSessionKey=null;
		}
		if(cobSessionKey.equalsIgnoreCase("valid")) {
			cobSessionKey=null;
		}
		
		EnvSession session = EnvSession.builder().cobSession(SessionHelper.getSessionToken(cobSessionValue, "cob"))
				.userSession(SessionHelper.getSessionToken(userSessionValue, "user")).cobSessionKey(cobSessionKey)
				.userSessionKey(userSessionKey).path(configurationObj.getCobrandSessionObj().getPath()).build();

		Response response = holdingUtils.getAssetClassificationList(session);
		response.then().log().all();
		
		JsonAssertionUtil jsonAssertionUtil = new JsonAssertionUtil();
		jsonAssertionUtil.assertLenientJSON(response, status, resFile, filePath);
		
		
		
		

	}
}

