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
/**
 * @author jagannathanvs 
 * B-32759 - YSO requirements : SPS enhancements - Merge operations
 *
 */
package com.yodlee.yodleeApi.sdg.script;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.databene.benerator.anno.Source;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.yodlee.taas.annote.Info;
import com.yodlee.yodleeApi.common.Base;
import com.yodlee.yodleeApi.constants.Constants;
import com.yodlee.yodleeApi.helper.AccountsHelper;
import com.yodlee.yodleeApi.helper.SessionHelper;
import com.yodlee.yodleeApi.helper.SpsHelper;
import com.yodlee.yodleeApi.pojo.AdditionalDataSet;
import com.yodlee.yodleeApi.pojo.EnvSession;
import com.yodlee.yodleeApi.pojo.HttpMethodParameters;
import com.yodlee.yodleeApi.pojo.User;
import com.yodlee.yodleeApi.sdg.ProcessSdg;
import com.yodlee.yodleeApi.utils.apiUtils.ProviderAccountUtils;
import com.yodlee.yodleeApi.utils.apiUtils.SPSUtils;
import com.yodlee.yodleeApi.utils.commonUtils.CommonUtils;

import io.restassured.response.Response;

public class TestSPSV1_1 extends Base {

	String dagSiteUserName = "SPS_MergeOperations.site16441.1";
	String dagSitePassword = "site16441.1";
	String dataSetTemplateFile = "template.xml";
	String filePath = "..\\src\\test\\resources\\TestData\\XML\\SPS\\";

	@Test(dataProvider = "feeder", enabled = true)
	@Info(userStoryId = "B-32759")
	@Source("\\TestData\\CSVFiles\\SPS\\SPS_V1_1.csv")
	public void testSPS(String UsTestcaseId, String tcId, String tcName, String sequence, String sequenceFilePath,
			String sequenceResFile, String Subrand, String Cobrand, String enabled, String priority, String formType,
			String params) throws Exception {

		CommonUtils commonUtils = new CommonUtils();
		SessionHelper sessionHelper = new SessionHelper();
		AccountsHelper accountsHelper = new AccountsHelper();

		System.out.println("###########################The Execution of Test case :" + tcName
				+ "############################################################");
		commonUtils.isTCEnabled(enabled, tcName);
		System.out.println(
				"**********************************************************************************************************************");
		System.out.println("TestCase : " + tcName + " & Sequence : " + sequence);
		System.out.println(
				"**********************************************************************************************************************");

		String userName = "TestYSLSdg" + System.currentTimeMillis();
		String password = "DEDUP@123";

		// Register a new user.
		System.out.println("User Registration Started");
		User userInfo = User.builder().build();
		userInfo.setUsername(userName);
		userInfo.setPassword(password);

		try {

			// User Registration
			EnvSession envSession = sessionHelper.getSessionObjectByUserRegistration(userInfo);

			// Populate csv values in to Map
			Map<String, String> inputParams = new HashMap<String, String>();
			if (params != null) {
				inputParams = populateParams(params);
			}

			// Create SPS Config
			SpsHelper spsHelper = new SpsHelper();
			String configReq = spsHelper.createJsonRequestForSPSConfig(inputParams.get("configKey"),
					inputParams.get("configValue"), inputParams.get("cobrandId"));
			Map<String, String> headers = new HashMap<String, String>();
			headers.put("authToken", "123456789");

			HttpMethodParameters httpMethodParameters = HttpMethodParameters.builder().build();
			httpMethodParameters.setBodyParams(configReq);
			httpMethodParameters.setAddHeaders(headers);

			SPSUtils spsUtils = new SPSUtils();
			Response resp = spsUtils.createSPSConfig(httpMethodParameters);

			// Test Case Execution
			
			  String xmlFile = sequenceFilePath + sequenceResFile;
			  System.out.println("xmlFile file to process is::" + sequenceResFile);
			  ProcessSdg processSDG = new ProcessSdg(xmlFile);
			  processSDG.processSdgXml(sequence, Constants.SIMPLIFIED_FORM, envSession);
			  ProviderAccountUtils providerAccountUtils = new ProviderAccountUtils();
			 

			// Validation of YSL response
			List<AdditionalDataSet> dataSet = accountsHelper.getAdditionalDataSet(filePath + dataSetTemplateFile);
			Response addAccountResponse = providerAccountUtils.addProviderAccountWithDataSet(dagSiteUserName,
					dagSitePassword, "16441", dataSet, envSession, Constants.FIELD_ARRAY_FORM, Constants.DATA_SET);
			
			String errorCode = addAccountResponse.jsonPath().getString("errorCode");
			String errorMessage = addAccountResponse.jsonPath().getString("errorMessage");

			Assert.assertEquals(errorCode, "Y827");
			Assert.assertEquals(errorMessage, "Maximum threshold for the day has been reached. Please try later");

		} catch (Exception e) {
			e.printStackTrace();
			Assert.fail("Failed to execute userRegistraion() case due to : " + e);
		}

	}

	/**
	 * This method is taking params details from CSv and storing the details into a
	 * HashMap
	 * 
	 * @param params this is param is coming from csv , values like
	 * @return mapToReturn
	 */
	private Map<String, String> populateParams(String params) {

		String[] split = params.split(";");

		Map<String, String> mapToReturn = new HashMap<String, String>();

		for (String string : split) {

			String[] equals = string.split("=");
			mapToReturn.put(equals[0].trim(), equals[1].trim());

		}
		return mapToReturn;
	}

}