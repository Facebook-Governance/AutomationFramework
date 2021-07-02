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
package com.yodlee.yodleeApi.Sps;

import java.util.HashMap;
import java.util.Map;

import org.databene.benerator.anno.Source;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.yodlee.yodleeApi.common.Base;
import com.yodlee.yodleeApi.common.Configuration;
import com.yodlee.yodleeApi.constants.Constants;
import com.yodlee.yodleeApi.constants.JSONPaths;
import com.yodlee.yodleeApi.helper.SessionHelper;
import com.yodlee.yodleeApi.helper.SpsHelper;
import com.yodlee.yodleeApi.pojo.EnvSession;
import com.yodlee.yodleeApi.pojo.HttpMethodParameters;
import com.yodlee.yodleeApi.utils.apiUtils.SPSUtils;
import com.yodlee.yodleeApi.utils.apiUtils.UserUtils;
import com.yodlee.yodleeApi.utils.commonUtils.CommonUtils;
import com.yodlee.taas.annote.Info;
import com.yodlee.yodleeApi.utils.apiUtils.ProviderAccountUtils;
import com.yodlee.yodleeApi.helper.UserHelper;


import io.restassured.response.Response;

public class TestSPSV1 extends Base {
	
	Configuration configurationObj = Configuration.getInstance();
	CommonUtils commonUtils = new CommonUtils();
	SessionHelper sessionHelper = new SessionHelper();
	UserHelper userHelper = new UserHelper();
	UserUtils userUtils = new UserUtils();

	long providerId = 16441;
	@Test(dataProvider = "feeder", enabled = true)
	@Info(userStoryId = "B-32759")
	@Source("\\TestData\\CSVFiles\\SPS\\SPS_V1.csv")
	public void spsTest(String UsTestcaseId, String tcId, String tcName, String Subrand, String Cobrand, String enabled,
			String priority, String formType, String params, String dagSiteUserName, String dagSitePassword,
			int count) {

		Response addAccountResponse = null;
		System.out.println("###########################The Execution of Test case :" + tcName
				+ "############################################################");
		commonUtils.isTCEnabled(enabled, tcName);
		System.out.println(
				"**********************************************************************************************************************");
		System.out.println("TestCase : " + tcName);
		System.out.println(
				"**********************************************************************************************************************");

		// Register a new user.
		System.out.println("User Registration Started");
		
		String loginNameKey = "loginName";
		String userName = "SPS" + "Test" + System.currentTimeMillis();
		String passwordKey = "password";
		String password = "DEDUP@123";
		String emailKey = "email";
		String email = "email@yodlee.com";
		String userKey = "user";

				try {

					// User Registration
					
					String userRegisterObjJSON = userHelper.userRegisterJson(loginNameKey, userName, passwordKey,
							password, emailKey, email, null, null, null, null, null, null, null, null,
							null, null, null, null, null, null, null, null, null, null,
							null, null, null, null, null, null, null, userKey);
					
					// Set all to HttpParam
					HttpMethodParameters httpMethodParameters = HttpMethodParameters.builder().build();
					httpMethodParameters.setBodyParams(userRegisterObjJSON);
					
					EnvSession sessionObj = EnvSession.builder()
							.cobSession(configurationObj.getCobrandSessionObj().getCobSession())
							.path(configurationObj.getCobrandSessionObj().getPath()).build();

					Response newUserResponse = (Response) userUtils.userRegistrationResponse(sessionObj, httpMethodParameters);

					if (newUserResponse.getStatusCode() == 200) {
						String userSession = newUserResponse.jsonPath().getString(JSONPaths.USER_SESSION_TOKEN);

						sessionObj = EnvSession.builder()
								.cobSession(configurationObj.getCobrandSessionObj().getCobSession()).userSession(userSession)
								.path(configurationObj.getCobrandSessionObj().getPath()).build();

						System.out.println("New user Registered : " + userName);
					}
					
					// Populate csv values in to Map
					Map<String, String> inputParams = new HashMap<String, String>();
					if (params != null) {
						inputParams = populateParams(params);
					}
					
					//Create SPS Config
					SpsHelper spsHelper = new SpsHelper();
					String configReq = spsHelper.createJsonRequestForSPSConfig(inputParams.get("configKey"),
							inputParams.get("configValue"), inputParams.get("cobrandId"));
					Map<String, String> headers = new HashMap<String, String>();
					headers.put("authToken", "123456789");
					
					httpMethodParameters.setBodyParams(configReq);
					httpMethodParameters.setAddHeaders(headers);
					
					SPSUtils spsUtils = new SPSUtils();
					Response resp = spsUtils.createSPSConfig(httpMethodParameters);

			// forming add request and validating responses
					ProviderAccountUtils providerAccountUtils = new ProviderAccountUtils();
			for (int i = 0; i < count; i++) {
				addAccountResponse = providerAccountUtils.addProviderAccountNonStrict(providerId, Constants.SIMPLIFIED_FORM, dagSiteUserName, dagSitePassword, sessionObj);
			}

			if (tcName.equals("SPS_Across_Site_Limit")) {
				for (int i = 0; i < 3; i++) {
					addAccountResponse = providerAccountUtils.addProviderAccountNonStrict(providerId, Constants.SIMPLIFIED_FORM, dagSiteUserName, dagSitePassword, sessionObj);
				}
			}

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