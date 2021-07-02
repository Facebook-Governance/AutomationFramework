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

import org.databene.benerator.anno.Source;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.Test;

import com.yodlee.taas.annote.Feature;
import com.yodlee.taas.annote.FeatureName;
import com.yodlee.yodleeApi.common.Base;
import com.yodlee.yodleeApi.common.Configuration;
import com.yodlee.yodleeApi.constants.TestGroup;
import com.yodlee.yodleeApi.helper.SessionHelper;
import com.yodlee.yodleeApi.pojo.EnvSession;
import com.yodlee.yodleeApi.sdg.ProcessSdg;
import com.yodlee.yodleeApi.utils.apiUtils.UserUtils;
import com.yodlee.yodleeApi.utils.commonUtils.CommonUtils;

@Feature(featureName = { FeatureName.SDG })
public class TestSdgHealthCheck extends Base {

	public static final String SdgHealthCheck = "\\TestData\\CSVFiles\\Sdg\\SdgHealthCheck.csv";
	EnvSession envSessionObj = null;
	CommonUtils commonUtils = new CommonUtils();
	SessionHelper sessionHelper = new SessionHelper();
	Configuration configurationObj = Configuration.getInstance();
	
	@Source(SdgHealthCheck)
	@Test(enabled = true, groups = { TestGroup.SDG, TestGroup.REGRESSION }, dataProvider = "feeder")
	public void test(String testCaseId, String testCaseName, String sequence, String sequenceFilePath,
			String sequenceJson, String Subrand, String Cobrand, String enabled, String priority, String loginFormType)
			throws Exception {

		commonUtils.isTCEnabled(enabled, testCaseName);
		System.out.println("***************************TestCase = " + testCaseName + "***********************");
		try {
			// User Login to get newUserSession object
			envSessionObj = sessionHelper.getSessionObjByUserLogin(configurationObj.getUserObj().getUsername(),
					configurationObj.getUserObj().getPassword());
			System.out.println("New userSession created is::" + envSessionObj.getUserSession());

			// Calling sdg method to execute this Test class
			String xmlFile = sequenceFilePath + sequenceJson;
			ProcessSdg processSdg = new ProcessSdg(xmlFile);
			processSdg.processSdgXml(sequence, loginFormType, envSessionObj);

		} catch (Exception e) {
			e.printStackTrace();
			Assert.fail("Failed to execute TestSdgHealthCheck.java case due to : " + e);

		}
	}

	/*@AfterClass(alwaysRun = true)
	public void unregister() {
		System.out.println("Doing unregistartion for userSession::=" + envSessionObj.getUserSession());
		UserUtils userUtils = new UserUtils();
		userUtils.unRegisterUser(envSessionObj);
	}*/
}