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
package com.yodlee.app.yodleeApi.SaveForGoal;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import java.text.ParseException;
import java.util.HashMap;
import org.databene.benerator.anno.Source;
import org.json.JSONObject;
import org.junit.Assert;
import com.yodlee.yodleeApi.assertions.JsonAssertionUtil;
import com.yodlee.yodleeApi.common.Base;
import com.yodlee.yodleeApi.common.Configuration;
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

public class TestUpdateGoal extends Base {

	String userSession = "";
	EnvSession sessionObj = null;
	static String getGoalId;
	Configuration configurationObj = Configuration.getInstance();
	UserUtils userUtils = new UserUtils();
	ProviderAccountUtils providerAccountUtils = new ProviderAccountUtils();
	CommonUtils commonUtils = new CommonUtils();
	SaveForGoalHelper saveForGoalHelper = new SaveForGoalHelper();
	UserHelper userHelper = new UserHelper();
	JsonAssertionUtil jsonAssertionutil = new JsonAssertionUtil();
	SaveForGoalHelper sfg = new SaveForGoalHelper();
	SFGUtils sfgUtils = new SFGUtils();

	@BeforeClass(alwaysRun = true)
	public void createUser() throws ParseException {
		User userInfo = User.builder().build();
		userInfo.setUsername("SFGYSL" + System.currentTimeMillis());
		userInfo.setPassword("Test@123");

		sessionObj = EnvSession.builder().cobSession(configurationObj.getCobrandSessionObj().getCobSession())
				.path(configurationObj.getCobrandSessionObj().getPath()).build();
		
		userHelper.getUserSession(userInfo, sessionObj);
		
		String createGoalBodyParams = sfg.createGoalJson("GoalName500", "1", sfg.sfgDateFormate("0,0,1"),
				sfg.sfgDateFormate("1,0,0"), "100", "USD", "1300", "USD", "MONTHLY", "NOT_STARTED", "NONE", "FALSE","");
		getGoalId=sfg.createGoal(createGoalBodyParams,sessionObj);
	}


	@Test(enabled = true, dataProvider = "feeder", priority = 1)
	@Source("\\TestData\\CSVFiles\\SaveForGoal\\UpdateGoal.csv")
	public void updateGoal(String TestCaseId, String TestCase, String GoalName, String CategoryID, String TargetAmount,
			String TargetAmountCurency, String RecurringAmount, String RecurringAmountCurrency, String Frequency,
			String StartDate, String TargetDate, String Status, String FundSetupStatus, String isFTSelected,
			String Enabled, String StatusCode, String ErrorCode, String ErrorMessage) throws ParseException {

		commonUtils.isTCEnabled(Enabled, TestCaseId);
		System.out.println("Executing testcase --->  "+TestCaseId);
		HashMap<String, Object> pathParam = new HashMap<>();
		pathParam.put("goalIdUpdate", getGoalId);
		HttpMethodParameters httpMethodParameters = HttpMethodParameters.builder().build();
		
		httpMethodParameters.setPathParams(pathParam);

		String updateBodyParams;
		if (StartDate == "" && TargetDate == "") {
			updateBodyParams = sfg.updateGoalBodyParam(GoalName, CategoryID, TargetAmount, TargetAmountCurency,
					RecurringAmount, RecurringAmountCurrency, Frequency, StartDate, TargetDate, Status, FundSetupStatus,
					isFTSelected);
		} else {
			updateBodyParams = sfg.updateGoalBodyParam(GoalName, CategoryID, TargetAmount, TargetAmountCurency,
					RecurringAmount, RecurringAmountCurrency, Frequency, sfg.sfgDateFormate(StartDate),
					sfg.sfgDateFormate(TargetDate), Status, FundSetupStatus, isFTSelected);
		}

		httpMethodParameters.setBodyParams(updateBodyParams);
		Response resp = sfgUtils.updateGoal(httpMethodParameters, sessionObj);

		int expectedStatusCode = Integer.parseInt(StatusCode);
		if (expectedStatusCode == 400) {
			JSONObject Obj = new JSONObject(resp.asString());
			Assert.assertEquals(ErrorCode, Obj.get("errorCode"));
			Assert.assertEquals(ErrorMessage, Obj.get("errorMessage"));
		} else {
			Assert.assertEquals(expectedStatusCode, resp.getStatusCode());
		}
	}
}
