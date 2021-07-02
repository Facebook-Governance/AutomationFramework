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
import org.testng.annotations.Test;

import com.yodlee.yodleeApi.common.Base;
import com.yodlee.yodleeApi.constants.Constants;
import com.yodlee.yodleeApi.constants.TestGroup;
import com.yodlee.yodleeApi.helper.SessionHelper;
import com.yodlee.yodleeApi.pojo.EnvSession;
import com.yodlee.yodleeApi.pojo.User;
import com.yodlee.yodleeApi.sdg.ProcessSdg;
import com.yodlee.yodleeApi.sdg.RequestSequence;
import com.yodlee.yodleeApi.utils.apiUtils.UserUtils;
import com.yodlee.yodleeApi.utils.commonUtils.CommonUtils;

/**
 * This class doesn't run with generic configuration this requires below
 * configuration:
 * 
 * @author Rahul Kumar
 *
 */
public class TestGetProviderAccountsViewCredentials extends Base {

	public static final String testMFAQuetionAndAnswer1 = "\\TestData\\CSVFiles\\Sdg\\EditMFAQandA1.csv";
	RequestSequence requestSequence = null;
	CommonUtils commonUtils = new CommonUtils();
	SessionHelper sessionHelper = new SessionHelper();

	@Source(testMFAQuetionAndAnswer1)
	@Test(enabled = true, groups = { TestGroup.SDG, TestGroup.REGRESSION }, dataProvider = "feeder")
	public void testGetProviderAccountsViewCredentials(String usTestCaseId, int testCaseId, String testCaseName,
			String sequence, String sequenceFilePath, String sequenceJson, String subrand, String cobrand,
			String priority, String loginFormType, String enabled) throws Exception {

		commonUtils.isTCEnabled(enabled, testCaseName);
		System.out.println("***************************TestCase = " + testCaseName + "***********************");
		System.out.println("***************************TestCase Id = " + testCaseId + "***********************");

		// Creating a new user.
		User userInfo = User.builder().build();
		userInfo.setUsername(Constants.YSL_USER + System.currentTimeMillis());
		userInfo.setPassword(Constants.USER_PASSWORD);
		userInfo.setLocale(Constants.LOCALE_US);

		try {

			// User Registration
			EnvSession envSessionObj = sessionHelper.getSessionObjectByUserRegistration(userInfo);
			System.out.println("New userSession created is::" + envSessionObj.getUserSession());

			// Executing sdg Test case
			String xmlFile = sequenceFilePath + sequenceJson;
			ProcessSdg processSdg = new ProcessSdg(xmlFile);
			processSdg.processSdgXml(sequence, loginFormType, envSessionObj);

			// Added newly Unregistering user
			UserUtils userUtils = new UserUtils();
			userUtils.unRegisterUser(envSessionObj);
			System.out.println("*********Completed TestGetProviderAccountsViewCredentials***********");

		} catch (Exception e) {
			e.printStackTrace();
			Assert.fail("Failed to execute testGetProviderAccountsViewCredentials() case due to : " + e);

		}
	}
}
