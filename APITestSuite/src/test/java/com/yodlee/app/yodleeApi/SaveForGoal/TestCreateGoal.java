/*******************************************************************************
 *
 * * Copyright (c) 201 Yodlee, Inc. All Rights Reserved.
 *
 * *
 *
 * * This software is the confidential and proprietary information of Yodlee, Inc.
 *
 * * Use is subject to license terms.
 *
 *******************************************************************************/
package com.yodlee.app.yodleeApi.SaveForGoal;

import java.util.ArrayList;
import java.util.HashMap;

import org.databene.benerator.anno.Source;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import com.yodlee.yodleeApi.assertions.JsonAssertionUtil;
import com.yodlee.yodleeApi.common.Base;
import com.yodlee.yodleeApi.common.Configuration;
import com.yodlee.yodleeApi.constants.JSONPaths;
import com.yodlee.yodleeApi.helper.SaveForGoalHelper;
import com.yodlee.yodleeApi.helper.UserHelper;
import com.yodlee.yodleeApi.pojo.EnvSession;
import com.yodlee.yodleeApi.pojo.HttpMethodParameters;
import com.yodlee.yodleeApi.pojo.User;
import com.yodlee.yodleeApi.utils.apiUtils.ProviderAccountUtils;
import com.yodlee.yodleeApi.utils.apiUtils.SFGUtils;
import com.yodlee.yodleeApi.utils.apiUtils.UserUtils;
import com.yodlee.yodleeApi.utils.commonUtils.CommonUtils;

import io.restassured.response.Response;

public class TestCreateGoal extends Base {
	public static Long providerAccountId = null;

	String loginName, password;
	String userSession = "";
	EnvSession sessionObj = null;
	Configuration configurationObj = Configuration.getInstance();
	UserUtils userUtils = new UserUtils();
	ProviderAccountUtils providerAccountUtils = new ProviderAccountUtils();
	CommonUtils commonUtils = new CommonUtils();
	SaveForGoalHelper saveForGoalHelper = new SaveForGoalHelper();
	UserHelper userHelper = new UserHelper();
	JsonAssertionUtil jsonAssertionutil = new JsonAssertionUtil();
	SaveForGoalHelper sfg = new SaveForGoalHelper();
	HashMap<String,ArrayList<String>> negativeExpectedValuesMap;

	@BeforeClass(alwaysRun = true)
	public void addAccount() {
		System.out.println("initiated execution");
		User userInfo = User.builder().build();
		userInfo.setUsername("SFGYSL" + System.currentTimeMillis());
		userInfo.setPassword("Test@123");
		sessionObj = EnvSession.builder().cobSession(configurationObj.getCobrandSessionObj().getCobSession())
				.path(configurationObj.getCobrandSessionObj().getPath()).build();
		userHelper.getUserSession(userInfo, sessionObj);
		long providerId = 16441;
		providerAccountId = providerId;
		Response response = providerAccountUtils.addProviderAccountStrict(providerId, "fieldarray",
				"sfgtestdata.site16441.1", "site16441.1", sessionObj);
		providerAccountId = response.jsonPath().getLong(JSONPaths.PROVIDER_ACC_ID);
		System.out.println("providerAccountId1::::===" + providerAccountId);
		negativeExpectedValuesMap = sfg.readNegativeExpectedValues();

	}

	@Test(enabled = true, dataProvider = "feeder", priority = 1)

	@Source("\\TestData\\CSVFiles\\SaveForGoal\\CreateCustomGoal.csv")
	public void createGoal(String TestCaseId, String TestCase, String Name, String CategoryID, String TargetAmount,
			String TargetAmountCurency, String RecurringAmount, String RecurringAmountCurrency, String Frequency,
			String StartDate, String TargetDate, String Status, String FundSetupStatus, String isFTSelected,
			int httpStatus, String ResFile, String FilePath, String Enabled) throws Exception {

		System.out.println("TestCase--->"+TestCaseId);
		commonUtils.isTCEnabled(Enabled, TestCaseId);		
		SFGUtils sfgUtils = new SFGUtils();
		String bodyParams = null;
		if (StartDate == "" && TargetDate == "") {
			bodyParams = sfg.createGoalJson(Name, CategoryID, StartDate, TargetDate, RecurringAmount,
					RecurringAmountCurrency, TargetAmount, TargetAmountCurency, Frequency, Status, FundSetupStatus,
					isFTSelected,"");
		} else if (StartDate == "") {
			bodyParams = sfg.createGoalJson(Name, CategoryID, StartDate, sfg.sfgDateFormate(TargetDate),
					RecurringAmount, RecurringAmountCurrency, TargetAmount, TargetAmountCurency, Frequency, Status,
					FundSetupStatus, isFTSelected,"");

		} else if (TargetDate == "") {
			bodyParams = sfg.createGoalJson(Name, CategoryID, sfg.sfgDateFormate(StartDate), TargetDate,
					RecurringAmount, RecurringAmountCurrency, TargetAmount, TargetAmountCurency, Frequency, Status,
					FundSetupStatus, isFTSelected,"");

		} else {
			bodyParams = sfg.createGoalJson(Name, CategoryID, sfg.sfgDateFormate(StartDate),
					sfg.sfgDateFormate(TargetDate), RecurringAmount, RecurringAmountCurrency, TargetAmount,
					TargetAmountCurency, Frequency, Status, FundSetupStatus, isFTSelected,"");

		}
		System.out.println(bodyParams);

		HttpMethodParameters httpMethodParameters = HttpMethodParameters.builder().build();
		httpMethodParameters.setBodyParams(bodyParams);
		Response response = sfgUtils.createGoal(httpMethodParameters, sessionObj);
		JsonAssertionUtil jsonPath = new JsonAssertionUtil();
		jsonPath.assertResponse(response, httpStatus, ResFile, FilePath);

	}

	@Test(enabled = true, dataProvider = "feeder", priority = 2)

	@Source("\\TestData\\CSVFiles\\SaveForGoal\\CreateGoalInvalidStartDate.csv")
	public void createGoalInvalidStartDate(String TestCaseId, String TestCase, String Name, String CategoryID,
			String TargetAmount, String TargetAmountCurency, String RecurringAmount, String RecurringAmountCurrency,
			String Frequency, String StartDate, String TargetDate, String Status, String FundSetupStatus,
			String isFTSelected, String DateFormate, int httpStatus, String ResFile, String FilePath, String Enabled)
					throws Exception {
		System.out.println(TestCaseId);
		commonUtils.isTCEnabled(Enabled, TestCaseId);
		SFGUtils sfgUtils = new SFGUtils();
		String bodyParams = sfg.createGoalJson(Name, CategoryID, sfg.sfgInvaildDateFormate(StartDate, DateFormate),
				sfg.sfgDateFormate(TargetDate), RecurringAmount, RecurringAmountCurrency, TargetAmount,
				TargetAmountCurency, Frequency, Status, FundSetupStatus, isFTSelected,"");
		System.out.println(bodyParams);

		HttpMethodParameters httpMethodParameters = HttpMethodParameters.builder().build();
		httpMethodParameters.setBodyParams(bodyParams);
		Response response = sfgUtils.createGoal(httpMethodParameters, sessionObj);
		JsonAssertionUtil jsonPath = new JsonAssertionUtil();
		jsonPath.assertResponse(response, httpStatus, ResFile, FilePath);

	}

	@Test(enabled = true, dataProvider = "feeder", priority = 3)

	@Source("\\TestData\\CSVFiles\\SaveForGoal\\CreateGoalInvalidTargateDate.csv")
	public void createGoalInvalidTargateDate(String TestCaseId, String TestCase, String Name, String CategoryID,
			String TargetAmount, String TargetAmountCurency, String RecurringAmount, String RecurringAmountCurrency,
			String Frequency, String StartDate, String TargetDate, String Status, String FundSetupStatus,
			String isFTSelected, String DateFormate, int httpStatus, String ResFile, String FilePath, String Enabled)
					throws Exception {
		System.out.println(TestCaseId);
		commonUtils.isTCEnabled(Enabled, TestCaseId);
		SFGUtils sfgUtils = new SFGUtils();
		String bodyParams = sfg.createGoalJson(Name, CategoryID, sfg.sfgDateFormate(StartDate),
				sfg.sfgInvaildDateFormate(TargetDate, DateFormate), RecurringAmount, RecurringAmountCurrency,
				TargetAmount, TargetAmountCurency, Frequency, Status, FundSetupStatus, isFTSelected,Thread.currentThread().getStackTrace()[1].getMethodName());
		System.out.println(bodyParams);
		HttpMethodParameters httpMethodParameters = HttpMethodParameters.builder().build();
		httpMethodParameters.setBodyParams(bodyParams);
		Response response = sfgUtils.createGoal(httpMethodParameters, sessionObj);
		JsonAssertionUtil jsonPath = new JsonAssertionUtil();
		jsonPath.assertResponse(response, httpStatus, ResFile, FilePath);

	}
	
	@Test(enabled = true, dataProvider = "feeder", priority = 4)	
	@Source("\\TestData\\CSVFiles\\SaveForGoal\\CreateGoalWithWithoutRecurringAmtFreqAndTargetDate.csv")
	public void createGoalWithAndWithoutTargetDateAndRecurringAmountFrequencyPositiveNegative(String testCaseId, String testCase, String name, String categoryID,
			String targetAmount, String targetAmountCurency, String recurringAmount, String recurringAmountCurrency,
			String frequency, String startDate, String targetDate, String status, String fundSetupStatus,
			String isFTSelected, int httpStatus, String resFile, String filePath, String enabled)
					throws Exception {
		System.out.println(testCaseId);
		commonUtils.isTCEnabled(enabled, testCaseId);
		SFGUtils sfgUtils = new SFGUtils();
		String bodyParams = sfg.createGoalJsonForRecurringAmountAndTargetDate(name, categoryID, sfg.sfgDateFormate(startDate),
				sfg.sfgDateFormate(targetDate), recurringAmount, recurringAmountCurrency,
				targetAmount, targetAmountCurency, frequency, status, fundSetupStatus, isFTSelected);
		System.out.println(bodyParams);
		HttpMethodParameters httpMethodParameters = HttpMethodParameters.builder().build();
		httpMethodParameters.setBodyParams(bodyParams);
		Response response = sfgUtils.createGoal(httpMethodParameters, sessionObj);
		sfg.validateCreateGoalResponse(response,response.getStatusCode(),testCaseId,negativeExpectedValuesMap);
		

	}

	@AfterClass(alwaysRun = true)
	public void unRegisteredUser() {
		userUtils.unRegisterUser(sessionObj);

	}

}
