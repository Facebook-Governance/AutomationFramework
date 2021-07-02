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
import com.yodlee.yodleeApi.common.Configuration;
import com.yodlee.yodleeApi.constants.TestGroup;
import com.yodlee.yodleeApi.helper.SessionHelper;
import com.yodlee.yodleeApi.pojo.EnvSession;
import com.yodlee.yodleeApi.sdg.ProcessSdg;
import com.yodlee.yodleeApi.utils.apiUtils.UserUtils;
import com.yodlee.yodleeApi.utils.commonUtils.CommonUtils;

/**
 * @author Rahul Kumar
 *
 */
public class TestRefreshStatus extends Base {

	public static final String testRefreshStatus = "\\TestData\\CSVFiles\\Sdg\\RefreshStatus.csv";
	EnvSession sessionObj = null;
	CommonUtils commonUtils = new CommonUtils();
	SessionHelper sessionHelper = new SessionHelper();
	Configuration configurationObj = Configuration.getInstance();

	@Source(testRefreshStatus)
	@Test(enabled = true, groups = { TestGroup.SDG, TestGroup.REGRESSION }, dataProvider = "feeder")
	@Info(userStoryId = "B-07119", subfeature = { SubFeature.SDG3 }, userStoryName = "Test refresh status")
	public void testRefreshStatus(String testCaseId, String testCaseName, String sequence, String sequenceFilePath,
			String sequenceResFile, String subrand, String cobrand, String usTestcaseId, String priority,
			String formType, String enabled) throws Exception {

		try {
			commonUtils.isTCEnabled(enabled, testCaseId);
			System.out.println("***************************TestCase Id = " + testCaseId + "***********************");

			// User Login to get newUserSession object
			EnvSession envSessionObj = sessionHelper.getSessionObjByUserLogin(
					configurationObj.getUserObj().getUsername(), configurationObj.getUserObj().getPassword());
			System.out.println("New userSession created is::" + envSessionObj.getUserSession());

			// Calling sdg method to execute this Test class
			String xmlFile = sequenceFilePath + sequenceResFile;
			ProcessSdg processSdg = new ProcessSdg(xmlFile);
			processSdg.processSdgXml(sequence, formType, envSessionObj);
			
		} catch (Exception e) {
			e.printStackTrace();
			Assert.fail("Failed to execute testRefreshStatus() case due to : " + e);
		}

	}

/*	@AfterClass(alwaysRun = true)
	public void unregister() {
		UserUtils userUtils = new UserUtils();
		System.out.println("Doing unregistartion");
		userUtils.unRegisterUser(sessionObj);
	}*/
}
