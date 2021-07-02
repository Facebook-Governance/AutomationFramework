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

import com.yodlee.taas.annote.Feature;
import com.yodlee.taas.annote.FeatureName;
import com.yodlee.taas.annote.TestGroup;
import com.yodlee.yodleeApi.common.Base;
import com.yodlee.yodleeApi.constants.Constants;
import com.yodlee.yodleeApi.helper.SessionHelper;
import com.yodlee.yodleeApi.pojo.EnvSession;
import com.yodlee.yodleeApi.pojo.User;
import com.yodlee.yodleeApi.sdg.ProcessSdg;
import com.yodlee.yodleeApi.utils.apiUtils.UserUtils;
import com.yodlee.yodleeApi.utils.commonUtils.CommonUtils;

/**
 * This class is used to test Dedup scenario for GET_ACCOUNTS
 * 
 * @author Rahul Kumar
 *
 */
@Feature(featureName = { FeatureName.SDG })
public class TestDedup extends Base {

	public final String testDedupProfilesSdg = "\\TestData\\CSVFiles\\Dedup\\Dedup.csv";

	CommonUtils commonUtils = new CommonUtils();
	SessionHelper sessionHelper = new SessionHelper();

	@Source(testDedupProfilesSdg)
	@Test(enabled = true, groups = { TestGroup.REGRESSION }, dataProvider = Constants.FEEDER)
	public void testDedup(String tcId, String tcName, String sequence, String sequenceFilePath, String sequenceResFile,
			String subrand, String cobrand, String enabled, String usTestcaseId, String priority, String loginFormType)
			throws Exception {

		double startTime = System.currentTimeMillis();
		commonUtils.isTCEnabled(enabled, tcName);

		System.out.println("*******TestCase : " + tcName + " & Sequence : " + sequence + "*******");

		// Creating a new user.
		System.out.println("User Registration Started");
		String dedupUserName = "YSL" + "dedupTest" + System.currentTimeMillis();
		String dedupPassword = "DEDUP@123";

		User userInfo = User.builder().build();
		userInfo.setUsername(dedupUserName);
		userInfo.setPassword(dedupPassword);

		try {

			// User Registration
			EnvSession envSessionObj = sessionHelper.getSessionObjectByUserRegistration(userInfo);
			System.out.println("New userSession created is::" + envSessionObj.getUserSession());

			// Executing sdg Test case
			String xmlFile = sequenceFilePath + sequenceResFile;
			ProcessSdg processSdg = new ProcessSdg(xmlFile);
			processSdg.processSdgXml(sequence, loginFormType, envSessionObj);

			System.out.println("*********Completed TestDedup test class***********");

			// Unregister user.
			UserUtils userUtils = new UserUtils();
			userUtils.unRegisterUser(envSessionObj);

		} catch (Exception e) {
			e.printStackTrace();
			Assert.fail("Failed to execute testDedup() case due to : " + e);

		} finally {
			long endTime = System.currentTimeMillis();
			System.out.println("Time taken for the execution of tcName " + tcName + " is == "
					+ (long) (endTime - startTime) / 60000 + " Minutes and "
					+ ((int) ((((endTime - startTime) / 60000) - (long) ((endTime - startTime) / 60000)) * 60))
					+ " Seconds");
		}
	}
}
