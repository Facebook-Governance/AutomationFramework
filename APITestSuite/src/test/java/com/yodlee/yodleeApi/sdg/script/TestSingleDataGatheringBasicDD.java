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

import java.util.List;

import org.databene.benerator.anno.Source;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.yodlee.yodleeApi.common.Base;
import com.yodlee.yodleeApi.common.Configuration;
import com.yodlee.yodleeApi.helper.SessionHelper;
import com.yodlee.yodleeApi.helper.ToMoveCommon;
import com.yodlee.yodleeApi.pojo.EnvSession;
import com.yodlee.yodleeApi.sdg.ProcessSdg;
import com.yodlee.yodleeApi.utils.commonUtils.CommonUtils;

/**
 * @author Rahul Kumar
 *
 */
public class TestSingleDataGatheringBasicDD extends Base {

	EnvSession envSessionObj = null;
	public static final String testSingleDataGatheringBasicDD = "\\TestData\\CSVFiles\\Sdg\\BASICAGG_HOLDER_DOC.csv";
	CommonUtils commonUtils = new CommonUtils();
	SessionHelper sessionHelper = new SessionHelper();
	Configuration configurationObj = Configuration.getInstance();
	
	@Source(testSingleDataGatheringBasicDD)
	@Test(enabled = true, dataProvider = "feeder")

	public void testDocDownload(String tcId, String tcName, String sequence, String sequenceFilePath,
			String sequenceResFile, String subrand, String cobrand, String priority, String loginFormType,
			String enabled) throws Exception {

		System.out.println("***************************TestCase Id = " + tcId + "***********************");
		commonUtils.isTCEnabled(enabled, tcName);

		ToMoveCommon toMoveCommon = new ToMoveCommon();
		List<String> executionMode = toMoveCommon.getExecutionMode(subrand, cobrand);
		logger.info("Execution Mode in testDocDownload() : " + executionMode);

		try {
			// User Login to get newUserSession object
			envSessionObj = sessionHelper.getSessionObjByUserLogin(configurationObj.getUserObj().getUsername(),
					configurationObj.getUserObj().getPassword());
			System.out.println("New userSession created is::" + envSessionObj.getUserSession());

			// Calling sdg method to execute this Test class
			String xmlFile = sequenceFilePath + sequenceResFile;
			ProcessSdg processSdg = new ProcessSdg(xmlFile);
			processSdg.processSdgXml(sequence, loginFormType, envSessionObj);

		} catch (Exception e) {
			e.printStackTrace();
			Assert.fail("Failed to execute testDocDownloadSec() case due to : " + e);
		}
	}

	/*@AfterClass(alwaysRun = true)
	public void unregister() {
		UserUtils userUtils = new UserUtils();
		System.out.println("Doing unregistartion");
		userUtils.unRegisterUser(envSessionObj);
	}*/
}
