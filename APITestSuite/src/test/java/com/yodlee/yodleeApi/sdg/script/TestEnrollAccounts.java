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
import com.yodlee.yodleeApi.constants.Constants;
import com.yodlee.yodleeApi.constants.TestGroup;
import com.yodlee.yodleeApi.helper.SessionHelper;
import com.yodlee.yodleeApi.pojo.EnvSession;
import com.yodlee.yodleeApi.pojo.User;
import com.yodlee.yodleeApi.sdg.ProcessSdg;
import com.yodlee.yodleeApi.utils.apiUtils.UserUtils;
import com.yodlee.yodleeApi.utils.commonUtils.CommonUtils;

@Feature(featureName = { FeatureName.SDG })
public class TestEnrollAccounts extends Base {

	public static final String testPIIprofilesSdg = "\\TestData\\CSVFiles\\Sdg\\EBILL.csv";
	CommonUtils commonUtils = new CommonUtils();
	SessionHelper sessionHelper = new SessionHelper();
	double startTime = System.currentTimeMillis();

	@Source(testPIIprofilesSdg)
	@Test(enabled = true, groups = { TestGroup.SDG, TestGroup.REGRESSION }, dataProvider = "feeder")
	public void testEnrollAccounts(String tcId, String tcName, String sequence, String sequenceFilePath,
			String sequenceResFile, String subrand, String cobrand, String usTestcaseId, String priority,
			String loginFormType, String enabled) throws Exception {

		double startTimePerTestCase = System.currentTimeMillis();
		commonUtils.isTCEnabled(enabled, tcName);
		System.out.println("***************************TestCase = " + tcName + "***********************");
		System.out.println("***************************TestCase Id = " + tcId + "***********************");

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
			String xmlFile = sequenceFilePath + sequenceResFile;
			ProcessSdg processSdg = new ProcessSdg(xmlFile);
			processSdg.processSdgXml(sequence, loginFormType, envSessionObj);

			// Added newly Unregistering user
			UserUtils userUtils = new UserUtils();
			userUtils.unRegisterUser(envSessionObj);
			System.out.println("*********Completed TestEnrollAccounts***********");

		} catch (Exception e) {
			e.printStackTrace();
			Assert.fail("Failed to execute testEnrollAccounts() case due to : " + e);
		} finally {
			long endTime = System.currentTimeMillis();
			System.out.println("Time taken for the execution of tcName " + tcName
					+ " of class TestEnrollAccounts is == " + (long) (endTime - startTimePerTestCase) / 60000
					+ " Minutes and " + ((int) ((((endTime - startTimePerTestCase) / 60000)
							- (long) ((endTime - startTimePerTestCase) / 60000)) * 60))
					+ " Seconds");
		}
	}

	@AfterClass
	public void totalRunTime() {
		long endTime = System.currentTimeMillis();
		System.out.println("Time taken for the execution for class TestEnrollAccounts is == "
				+ (long) (endTime - startTime) / 60000 + " Minutes and "
				+ ((int) ((((endTime - startTime) / 60000) - (long) ((endTime - startTime) / 60000)) * 60))
				+ " Seconds");
	}
}
