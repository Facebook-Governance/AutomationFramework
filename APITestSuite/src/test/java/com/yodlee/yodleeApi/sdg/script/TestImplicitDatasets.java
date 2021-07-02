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
import com.yodlee.taas.annote.Info;
import com.yodlee.taas.annote.SubFeature;
import com.yodlee.yodleeApi.common.Base;
import com.yodlee.yodleeApi.common.Configuration;
import com.yodlee.yodleeApi.constants.TestGroup;
import com.yodlee.yodleeApi.helper.SessionHelper;
import com.yodlee.yodleeApi.pojo.EnvSession;
import com.yodlee.yodleeApi.sdg.ProcessSdg;
import com.yodlee.yodleeApi.utils.commonUtils.CommonUtils;

/**
 * @author Rahul Kumar
 *
 */
@Feature(featureName = { FeatureName.SDG })
public class TestImplicitDatasets extends Base {

	public static final String testImplicitdatasets = "\\TestData\\CSVFiles\\Sdg\\Implicitdatasets.csv";
	CommonUtils commonUtils = new CommonUtils();
	SessionHelper sessionHelper = new SessionHelper();
	double startTime = System.currentTimeMillis();

	@Source(testImplicitdatasets)
	@Info(userStoryId = "B-07119", subfeature = { SubFeature.SDG3 }, userStoryName = "Implicit data set is returned")
	@Test(enabled = true, groups = { TestGroup.SDG, TestGroup.REGRESSION }, dataProvider = "feeder")
	public void testDependentDatasetAttr(String testCaseId, String testCaseName, String sequence,
			String sequenceFilePath, String sequenceJson, String subrand, String cobrand, String enabled,
			String priority, String loginFormType) throws Exception {

		double startTimePerTestCase = System.currentTimeMillis();
		commonUtils.isTCEnabled(enabled, testCaseName);
		System.out.println("**************TestCase = " + testCaseName + "*********");
		try {
			// User Login to get newUserSession object
			EnvSession envSessionObj = sessionHelper.getSessionObjByUserLogin(
					Configuration.getInstance().getUserObj().getUsername(),
					Configuration.getInstance().getUserObj().getPassword());
			System.out.println("New userSession created is::" + envSessionObj.getUserSession());

			// Calling sdg method to execute this Test class
			String xmlFile = sequenceFilePath + sequenceJson;
			ProcessSdg processSdg = new ProcessSdg(xmlFile);
			processSdg.processSdgXml(sequence, loginFormType, envSessionObj);

		} catch (Exception e) {
			e.printStackTrace();
			Assert.fail("Failed to execute testDependentDatasetAttr() case due to : " + e);

		} finally {
			long endTime = System.currentTimeMillis();
			System.out.println("Time taken for the execution of tcName " + testCaseName
					+ " of class TestImplicitDatasets is == " + (long) (endTime - startTimePerTestCase) / 60000
					+ " Minutes and " + ((int) ((((endTime - startTimePerTestCase) / 60000)
							- (long) ((endTime - startTimePerTestCase) / 60000)) * 60))
					+ " Seconds");
		}
	}

	@AfterClass
	public void totalRunTime() {
		long endTime = System.currentTimeMillis();
		System.out.println("Time taken for the execution for class TestImplicitDatasets is == "
				+ (long) (endTime - startTime) / 60000 + " Minutes and "
				+ ((int) ((((endTime - startTime) / 60000) - (long) ((endTime - startTime) / 60000)) * 60))
				+ " Seconds");
	}
}
