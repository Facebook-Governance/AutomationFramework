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
@Feature(featureName = { FeatureName.SDG })
public class TestPIIGranularConfigsDefault extends Base {
	
	public static final String TestPIIGranularConfigs = "\\TestData\\CSVFiles\\Sdg\\PIIGranularConfigsDefault.csv";
	CommonUtils commonUtils = new CommonUtils();
	SessionHelper sessionHelper = new SessionHelper();
	
	@Source(TestPIIGranularConfigs)
	@Test(enabled = true, groups = { TestGroup.SDG, TestGroup.REGRESSION }, dataProvider = "feeder")
	public void testPIIGranular(String UsTestCaseId, String tcId, String tcName, String sequence,
			String sequenceFilePath, String sequenceResFile, String subrand, String cobrand, String enabled,
			String priority, String loginFormType) throws Exception {

		commonUtils.isTCEnabled(enabled, tcName);
		System.out.println("***************************TestCase Id = " + UsTestCaseId + "***********************");
		try {
			// User Login to get newUserSession object
			EnvSession envSessionObj = sessionHelper.getSessionObjByUserLogin(
					Configuration.getInstance().getUserObj().getUsername(),
					Configuration.getInstance().getUserObj().getPassword());
			System.out.println("New userSession created is::" + envSessionObj.getUserSession());

			// Calling sdg method to execute this Test class
			String xmlFile = sequenceFilePath + sequenceResFile;
			ProcessSdg processSdg = new ProcessSdg(xmlFile);
			processSdg.processSdgXml(sequence, loginFormType, envSessionObj);

			/*//Removed newly Unregistering user
			UserUtils userUtils = new UserUtils();
			userUtils.unRegisterUser(envSessionObj);*/

		} catch (Exception e) {
			e.printStackTrace();
			Assert.fail("Failed to execute testPIIGranular() case due to : " + e);

		}
	}
}
