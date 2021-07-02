/*******************************************************************************
 *
 * * Copyright (c) 2018 Yodlee, Inc. All Rights Reserved.
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
 * This class is used to test Consent scenarios.
 * 
 * @author Sankar Ganesh Viswanathan
 *
 */
@Feature(featureName = { FeatureName.SDG })
public class TestOBPureApiFlow extends Base {

	final String srcOpenBankingPureApiPositive = "\\TestData\\CSVFiles\\Sdg\\OpenBanking\\PureAPI\\OpenBankingPureApiPositive.csv";
	final String srcOpenBankingPureApiNegative = "\\TestData\\CSVFiles\\Sdg\\OpenBanking\\PureAPI\\OpenBankingPureApiNegative.csv";

	CommonUtils commonUtils = new CommonUtils();
	SessionHelper sessionHelper = new SessionHelper();

	@Source(srcOpenBankingPureApiPositive)
	@Test(enabled = true, groups = { TestGroup.REGRESSION }, dataProvider = Constants.FEEDER)
	public void openBankingPureAPIPositive(String tcId, String tcName, String sequence, String sequenceFilePath,
			String sequenceResFile, String subrand, String cobrand, String enabled, String usTestcaseId,
			String priority) throws Exception {
		
		commonUtils.isTCEnabled(enabled, tcName);

		System.out.println("*******TestCase : " + tcName + " & Sequence : " + sequence + "*******");

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
			processSdg.processSdgXml(sequence, null, envSessionObj);

			System.out.println("********* Test Case : " + tcName + " Execution Completed***********");

			// Unregister user.
			UserUtils userUtils = new UserUtils();
			userUtils.unRegisterUser(envSessionObj);

		} catch (Exception e) {
			e.printStackTrace();
			Assert.fail("Failed to execute test case: " + tcName  + " due to : " + e);
		}
	}
	
	@Source(srcOpenBankingPureApiNegative)
	@Test(enabled = true, groups = { TestGroup.REGRESSION }, dataProvider = Constants.FEEDER)
	public void openBankingPureAPINegative(String tcId, String tcName, String sequence, String sequenceFilePath,
			String sequenceResFile, String subrand, String cobrand, String enabled, String usTestcaseId,
			String priority) throws Exception {
		
		commonUtils.isTCEnabled(enabled, tcName);

		System.out.println("*******TestCase : " + tcName + " & Sequence : " + sequence + "*******");

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
			processSdg.processSdgXml(sequence, null, envSessionObj);

			System.out.println("********* Test Case : " + tcName + " Execution Completed***********");

			// Unregister user.
			UserUtils userUtils = new UserUtils();
			userUtils.unRegisterUser(envSessionObj);

		} catch (Exception e) {
			e.printStackTrace();
			Assert.fail("Failed to execute test case: " + tcName  + " due to : " + e);
		}
	}
}
