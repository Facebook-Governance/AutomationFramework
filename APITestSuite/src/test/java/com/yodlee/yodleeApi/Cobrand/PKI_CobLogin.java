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

package com.yodlee.yodleeApi.Cobrand;

import io.restassured.response.Response;

import org.databene.benerator.anno.Source;
import org.json.JSONException;
import org.testng.annotations.Test;

import com.yodlee.yodleeApi.assertions.JsonAssertionUtil;
import com.yodlee.yodleeApi.common.Base;
import com.yodlee.yodleeApi.common.Configuration;
import com.yodlee.yodleeApi.helper.SessionHelper;
import com.yodlee.yodleeApi.pojo.EnvSession;
import com.yodlee.yodleeApi.utils.apiUtils.CobrandUtils;
import com.yodlee.yodleeApi.utils.commonUtils.CommonUtils;

public class PKI_CobLogin extends Base {
	Configuration configurationObj = Configuration.getInstance();
	CommonUtils commonUtils = new CommonUtils();
	JsonAssertionUtil jsonAssertionUtil = new JsonAssertionUtil();
	CobrandUtils cobrandUtils = new CobrandUtils();

	@Test(enabled = true, dataProvider = "feeder", alwaysRun = true)
	@Source("\\TestData\\CSVFiles\\Cobrand\\PKI_enabled.csv")
	public void PKICobLogin(String testCaseId, String TestCase, String API, String cobSessionKey,
			String cobSessionValue, String userSessionKey, String userSessionValue, String resFile,
			String resFileForPKIDisabled, String filePath, int status, int statusForPKIDisabled, String DefectID,
			String enabled) throws JSONException, InterruptedException {
		commonUtils.isTCEnabled(enabled, testCaseId);
		System.out.println("Test Case Id is " + testCaseId + " test case is " + TestCase);
		String fileName;
		int statusCode;
		if (configurationObj.getCobrandObj().isPKIEnabled()) {
			fileName = resFile;
			statusCode = status;
		} else {
			fileName = resFileForPKIDisabled;
			statusCode = statusForPKIDisabled;
		}

		String cobSession = SessionHelper.getSessionToken(cobSessionValue, "cob");
		String userSession = SessionHelper.getSessionToken(userSessionValue, "user");

		/*EnvSession envSession = new EnvSession(cobSession, userSession,
				configurationObj.getCobrandSessionObj().getPath(), cobSessionKey, userSessionKey);*/

		EnvSession envSession = EnvSession.builder().cobSession(cobSession).cobSessionKey(cobSessionKey).userSession(userSession)
				.userSessionKey(userSessionKey).path(configurationObj.getCobrandSessionObj().getPath()).build();

		Response response = (Response) cobrandUtils.getPublicKeyResponse(envSession);

		jsonAssertionUtil.assertResponse(response, statusCode, fileName, filePath);
	}
}
