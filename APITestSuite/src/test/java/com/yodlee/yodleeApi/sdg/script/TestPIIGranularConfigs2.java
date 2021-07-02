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
 * This class does't run with generic configuration this requires changes to PII
 * attributes enabled in attribute_cob_config update ATTRIBUTE_COB_CONFIG set
 * configs =
 * '[{"container":["bank","investment"],"configs":{"scrapeMultiHolderName":false}}]'
 * where attribute_id = 3 and cobrand_id = cobrandId; update
 * ATTRIBUTE_COB_CONFIG set configs =
 * '[{"container":["bank","investment"],"configs":{"datasetAttributesPi":["1","3"]}}]'
 * where attribute_id = 4 and cobrand_id = cobrandId;
 * 
 * 
 * @author Rahul Kumar
 *
 */
@Feature(featureName = { FeatureName.SDG })
public class TestPIIGranularConfigs2 extends Base {
	
	public static final String TestPIIGranularConfigs = "\\TestData\\CSVFiles\\Sdg\\PIIGranularConfigs2.csv";
	CommonUtils commonUtils = new CommonUtils();
	SessionHelper sessionHelper = new SessionHelper();
	
	@Source(TestPIIGranularConfigs)
	@Test(enabled = true, groups = { TestGroup.SDG, TestGroup.REGRESSION }, dataProvider = "feeder")
	public void testPiiGranular(String usTestCaseId, String tcId, String tcName, String sequence,
			String sequenceFilePath, String sequenceResFile, String subrand, String cobrand, String enabled,
			String priority, String loginFormType) throws Exception {

		commonUtils.isTCEnabled(enabled, tcName);
		System.out.println("***************************TestCase = " + tcName + "***********************" + sequence);
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

			/*// Removed newly Unregistering user
			UserUtils userUtils = new UserUtils();
			userUtils.unRegisterUser(envSessionObj);*/

		} catch (Exception e) {
			e.printStackTrace();
			Assert.fail("Failed to execute testPiiGranular() case due to : " + e);

		}
	}
}
