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
public class TestAutoContainerLookUp extends Base {
	
	CommonUtils commonUtils = new CommonUtils();
	Configuration configurationObj = Configuration.getInstance();
	SessionHelper sessionHelper = new SessionHelper();

	public static final String testAutoContainerLookUp = "\\TestData\\CSVFiles\\Sdg\\AutoContainerLookUp.csv";
	EnvSession envSessionObj = null;

	@Source(testAutoContainerLookUp)
	@Test(enabled = true, groups = { TestGroup.SDG, TestGroup.REGRESSION }, dataProvider = "feeder")
	public void testAutoContainerLookUp(String usTestcaseId, int testcaseId, String testcaseName, String sequence,
			String sequenceFilePath, String sequenceResFile, String subrand, String cobrand, String priority,
			String formType, String enabled) throws Exception {

		commonUtils.isTCEnabled(enabled, testcaseName);

		try {
			// User Login to get newUserSession object
			envSessionObj = sessionHelper.getSessionObjByUserLogin(configurationObj.getUserObj().getUsername(),
					configurationObj.getUserObj().getPassword());
			System.out.println("New userSession created is::" + envSessionObj.getUserSession());

			// Calling sdg method to execute this Test class
			String xmlFile = sequenceFilePath + sequenceResFile;
			ProcessSdg processSdg = new ProcessSdg(xmlFile);
			processSdg.processSdgXml(sequence, formType, envSessionObj);

		} catch (Exception e) {
			e.printStackTrace();
			Assert.fail("Failed to execute testAutoContainerLookUp() case due to : " + e);

		}

	}

	@AfterClass(alwaysRun = true)
	public void unregister() {
		System.out.println("Doing unregistartion for userSession::=" + envSessionObj.getUserSession());
		UserUtils userUtils = new UserUtils();
		userUtils.unRegisterUser(envSessionObj);
	}
}
