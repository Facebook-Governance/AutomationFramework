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

import com.yodlee.taas.annote.Info;
import com.yodlee.taas.annote.SubFeature;
import com.yodlee.yodleeApi.common.Base;
import com.yodlee.yodleeApi.constants.Constants;
import com.yodlee.yodleeApi.constants.TestGroup;
import com.yodlee.yodleeApi.helper.SessionHelper;
import com.yodlee.yodleeApi.pojo.EnvSession;
import com.yodlee.yodleeApi.pojo.User;
import com.yodlee.yodleeApi.sdg.ProcessSdg;
import com.yodlee.yodleeApi.utils.apiUtils.UserUtils;
import com.yodlee.yodleeApi.utils.commonUtils.CommonUtils;

/**
 * @author Rahul Kumar
 *
 */
public class TestDataPurge extends Base {

	public static final String testDataPurge = "\\TestData\\CSVFiles\\Sdg\\DataPurge\\DataPurge.csv";
	EnvSession envSessionObj = null;
	CommonUtils commonUtils = new CommonUtils();
	SessionHelper sessionHelper = new SessionHelper();

	@Source(testDataPurge)
	@Test(enabled = true, groups = { TestGroup.SDG, TestGroup.REGRESSION }, dataProvider = "feeder")
	@Info(userStoryId = "B-07119", subfeature = { SubFeature.SDG3 }, userStoryName = "Test DataPurge")
	public void testDataPurge(String testCaseId, String testCaseName, String sequence, String sequenceFilePath,
			String sequenceJson, String subrand, String cobrand, String enabled, String usTestcaseId, String priority,
			String loginFormType) throws Exception {

		commonUtils.isTCEnabled(enabled, testCaseName);
		System.out.println("***************************TestCase = " + testCaseName + "***********************");
		System.out.println("***************************TestCase Id = " + testCaseId + "***********************");

		// Creating a new user.
		User userInfo = User.builder().build();
		userInfo.setUsername(Constants.YSL_USER + "TestDataPurge" + System.currentTimeMillis());
		userInfo.setPassword("Password#123");
		userInfo.setLocale(Constants.LOCALE_US);

		try {

			// User Registration
			envSessionObj = sessionHelper.getSessionObjectByUserRegistration(userInfo);
			System.out.println("New userSession created is::" + envSessionObj.getUserSession());

			// Executing sdg Test case
			String xmlFile = sequenceFilePath + sequenceJson;
			ProcessSdg processSdg = new ProcessSdg(xmlFile);
			processSdg.processSdgXml(sequence, loginFormType, envSessionObj);

			// Added newly Unregistering user
			UserUtils userUtils = new UserUtils();
			userUtils.unRegisterUser(envSessionObj);
			System.out.println("*********Completed TestDataPurge***********");

		} catch (Exception e) {
			e.printStackTrace();
			Assert.fail("Failed to execute testDataPurge() case due to : " + e);
		}
	}

	@AfterClass(alwaysRun = true)
	public void unregister() {
		System.out.println("Doing unregistartion for userSession::=" + envSessionObj.getUserSession());
		UserUtils userUtils = new UserUtils();
		userUtils.unRegisterUser(envSessionObj);
	}

}
