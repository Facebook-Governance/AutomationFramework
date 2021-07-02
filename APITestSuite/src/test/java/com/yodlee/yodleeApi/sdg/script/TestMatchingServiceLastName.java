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
import org.testng.SkipException;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.yodlee.DBHelper;
import com.yodlee.yodleeApi.common.Base;
import com.yodlee.yodleeApi.common.Configuration;
import com.yodlee.yodleeApi.constants.Constants;
import com.yodlee.yodleeApi.constants.TestGroup;
import com.yodlee.yodleeApi.database.DbQueries;
import com.yodlee.yodleeApi.helper.DbHelper;
import com.yodlee.yodleeApi.helper.SessionHelper;
import com.yodlee.yodleeApi.pojo.EnvSession;
import com.yodlee.yodleeApi.pojo.User;
import com.yodlee.yodleeApi.sdg.ProcessSdg;
import com.yodlee.yodleeApi.utils.apiUtils.UserUtils;
import com.yodlee.yodleeApi.utils.commonUtils.CommonUtils;

/**
 * This class doesn't run with generic configuration this requires below
 * configuration: FULL NAME Should be disabled and only last name match key
 * should be enabled.
 *
 * @author Rahul Kumar
 *
 */
public class TestMatchingServiceLastName extends Base {

	public static final String testMatchingService = "\\TestData\\CSVFiles\\Sdg\\Verification\\TestMSLastName.csv";
	CommonUtils commonUtils = new CommonUtils();
	SessionHelper sessionHelper = new SessionHelper();
	DbHelper dbHelper = new DbHelper();
	@BeforeClass
	public void verifyKeyEnablement() throws Exception {
		System.out.println(
				" ==== DB Check for COM.YODLEE.CORE.FUNDSTRANSFER.ACCOUNT_HOLDER_LAST_NAME_MATCH key is enabled or not ==== ");
		String cobrandId = Configuration.getInstance().getCobrandObj().getCobrandId();

		String res = dbHelper.getValueFromDB(DbHelper.GET_PARAMVALUE_MSLN + cobrandId, "PARAM_VALUE").toString();
		if (res.equalsIgnoreCase("1") || res.equalsIgnoreCase("TRUE") ) {
			System.out.println(
					"COM.YODLEE.CORE.FUNDSTRANSFER.ACCOUNT_HOLDER_LAST_NAME_MATCH key is enabled for the cobrandId " + cobrandId);
		} else if (res.equalsIgnoreCase("0") || res.isEmpty() || res.equalsIgnoreCase("FALSE")) {
			System.out.println("SKIPPING :: The required key is not enabled for the cobrand and hence skipping the test cases");
			throw new SkipException("The required key is not enabled for the cobrand and hence skipping the test cases");
		}
		
		System.out.println(
					" ==== DB Check for COM.YODLEE.CORE.FUNDSTRANSFER.ACCOUNT_HOLDER.FIRST_LAST_NAME_MATCH is enabled or not ==== ");
		String res1 = dbHelper.getValueFromDB(DbHelper.GET_PARAMVALUE_MSFNLN + cobrandId, "PARAM_VALUE").toString();
			if (res1.equalsIgnoreCase("0") || res1.isEmpty() || res1.equalsIgnoreCase("FALSE")) {
				System.out.println(
						"COM.YODLEE.CORE.FUNDSTRANSFER.ACCOUNT_HOLDER.FIRST_LAST_NAME_MATCH key is not enabled and hence continue with the execution");
			} else if (res1.equalsIgnoreCase("1") || res1.equalsIgnoreCase("TRUE")) {
				System.out.println("SKIPPING :: Both FirstNameLastName and LastName param keys are enabled and hence skipping the tests");
				throw new SkipException("Both FirstNameLastName and LastName param keys are enabled and hence skipping the tests");
			}

	}


	@Source(testMatchingService)
	@Test(enabled = true, groups = { TestGroup.SDG, TestGroup.REGRESSION }, dataProvider = "feeder")
	public void testMatchingServiceForSdgLastName(String usTestCaseId, int testCaseId, String testCaseName,
			String firstName, String lastName, String sequence, String sequenceFilePath, String sequenceJson,
			String subrand, String cobrand, String priority, String loginFormType, String enabled) throws Exception {

		commonUtils.isTCEnabled(enabled, testCaseName);
		System.out.println("***************************TestCase = " + testCaseName + "***********************");
		System.out.println("***************************TestCase Id = " + usTestCaseId + "***********************");

		// Creating a new user.
		User userInfo = User.builder().build();
		userInfo.setUsername(Constants.YSL_USER + System.currentTimeMillis());
		userInfo.setPassword(Constants.USER_PASSWORD);
		userInfo.setFirst(firstName);
		userInfo.setLast(lastName);

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
			System.out.println("*********Completed TestMatchingServiceLastName***********");

		} catch (Exception e) {
			e.printStackTrace();
			Assert.fail("Failed to execute testMatchingServiceForSdgLastName() case due to : " + e);

		}
	}

}
