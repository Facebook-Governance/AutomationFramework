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

/**
 * This class doesn't run with generic configuration this requires below
 * configuration:
 * 
 * @author Rahul Kumar
 *
 */
@Feature(featureName = { FeatureName.SDG })
public class TestDedupForAccounts extends Base {

	public static final String testDedupProfilesSdg = "\\TestData\\CSVFiles\\Dedup\\DedupAD.csv";
	EnvSession envSessionObj = null;
	CommonUtils commonUtils = new CommonUtils();
	SessionHelper sessionHelper = new SessionHelper();
	double startTime = System.currentTimeMillis();
	
	@Source(testDedupProfilesSdg)
	@Test(enabled = true, groups = { TestGroup.SDG, TestGroup.REGRESSION }, dataProvider = "feeder")
	public void testDedupForAccounts(String usTestCaseId, String testCaseId, String testCaseName, String sequence,
			String sequenceFilePath, String sequenceResFile, String subrand, String cobrand, String priority,
			String loginFormType, String enabled) throws Exception {
		
		double startTimePerTestCase = System.currentTimeMillis();
		commonUtils.isTCEnabled(enabled, testCaseName);
		System.out.println("****TestCase : " + testCaseName + " & Sequence : " + sequence + "******");

		// Creating a new user.
		System.out.println("User Registration Started");
		String dedupUserName = "TestYSLSdg" + "Dedup" + System.currentTimeMillis();
		String dedupPassword = "DEDUP@123";

		User userInfo = User.builder().build();
		userInfo.setUsername(dedupUserName);
		userInfo.setPassword(dedupPassword);
		userInfo.setLocale(Constants.LOCALE_US);
		try {

			// User Registration
			envSessionObj = sessionHelper.getSessionObjectByUserRegistration(userInfo);

			// Executing sdg Test case
			String xmlFile = sequenceFilePath + sequenceResFile;
			ProcessSdg processSdg = new ProcessSdg(xmlFile);
			processSdg.processSdgXml(sequence, loginFormType, envSessionObj);

			System.out.println("Completed TestDedupForAccounts class");

		} catch (Exception e) {
			e.printStackTrace();
			Assert.fail("Failed to execute testDedupForAccounts() case due to : " + e);
		}finally {
			long endTime = System.currentTimeMillis();
			System.out.println("Time taken for the execution of tcName " + testCaseName
					+ " of class TestDedupForAccounts is == " + (long) (endTime - startTimePerTestCase) / 60000
					+ " Minutes and " + ((int) ((((endTime - startTimePerTestCase) / 60000)
							- (long) ((endTime - startTimePerTestCase) / 60000)) * 60))
					+ " Seconds");
		}
	}

	@AfterClass(alwaysRun = true)
	public void unregister() {
		System.out.println("Doing unregistartion for userSession::=" + envSessionObj.getUserSession());
		UserUtils userUtils = new UserUtils();
		userUtils.unRegisterUser(envSessionObj);
		
		long endTime = System.currentTimeMillis();
		System.out.println("Time taken for the execution for class TestDedupForAccounts is == "
				+ (long) (endTime - startTime) / 60000 + " Minutes and "
				+ ((int) ((((endTime - startTime) / 60000) - (long) ((endTime - startTime) / 60000)) * 60))
				+ " Seconds");
	}
}
