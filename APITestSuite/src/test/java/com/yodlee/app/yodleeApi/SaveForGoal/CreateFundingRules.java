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

import static org.testng.Assert.assertEquals;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.databene.benerator.anno.Source;
import org.json.JSONObject;
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
import com.yodlee.yodleeApi.utils.commonUtils.CommonUtils;

import io.restassured.response.Response;

public class CreateFundingRules extends Base {
	public static Long providerAccountId = null;
	String userSession = "";
	static String getGoalId;
	EnvSession sessionObj = null;
	String userName = "SFGYSL" + System.currentTimeMillis();
	Configuration configurationObj = Configuration.getInstance();
	UserHelper userHelper = new UserHelper();
	JsonAssertionUtil jsonAssertionutil = new JsonAssertionUtil();
	ProviderAccountUtils providerAccountUtils = new ProviderAccountUtils();
	SaveForGoalHelper sfg = new SaveForGoalHelper();
	SFGUtils sfgUtils = new SFGUtils();
	CommonUtils commonUtils=new CommonUtils();
	static List<Integer> itemAccountIds = new ArrayList<>();

	@BeforeClass(alwaysRun = true)
	public void createUser() throws ParseException, SQLException {
		System.out.println("******STARTING***********");
		User userInfo = User.builder().build();
		userInfo.setUsername(userName);
		userInfo.setPassword("Test@123");
		sessionObj = EnvSession.builder().cobSession(configurationObj.getCobrandSessionObj().getCobSession())
				.path(configurationObj.getCobrandSessionObj().getPath()).build();
		userHelper.getUserSession(userInfo, sessionObj);
		long providerId = 16441;
		providerAccountId = providerId;
		Response response = providerAccountUtils.addProviderAccountStrict(providerId, "fieldarray",
				"renuka21.site16441.2", "site16441.2", sessionObj);
		providerAccountId = response.jsonPath().getLong(JSONPaths.PROVIDER_ACC_ID);
		System.out.println("providerAccountId1::::===" + providerAccountId);

		String bodyParams = sfg.createGoalJson("Goal1", "1", sfg.sfgDateFormate("0,0,1"),
				sfg.sfgDateFormate("1,0,0"), "500", "USD", "13000", "USD", "MONTHLY", "NOT_STARTED", "NONE", "FALSE","");
		getGoalId = sfg.createGoal(bodyParams, sessionObj);
		itemAccountIds = sfg.getItemAccountIds(userName);
		
		String createDestAccountBodyParam=sfg.createDestinationAccount(itemAccountIds.get(1),1000,"USD");
		sfg.createDestAccount(getGoalId, createDestAccountBodyParam, sessionObj);
	}

	@Test(enabled = true, dataProvider = "feeder", priority = 1)
	@Source("\\TestData\\CSVFiles\\SaveForGoal\\CreateFundingRule.csv")
	public void createFundingRule(String TestCaseId, String TestCase, String goalAccountItemId, String goalId,
			String recurringAmount, String recurringCurrency, String frequency, String isAutoTransferRule,
			String startDate, String endDate, String errorCode, String errorMsg, String statusCode,String enabled)
			throws ParseException, SQLException {

		commonUtils.isTCEnabled(enabled, TestCaseId);
		String createFundingRuleBodyParam;

		HttpMethodParameters httpmethodParam = HttpMethodParameters.builder().build();
		HashMap<String, Object> createFundingRulePathParam = new HashMap<>();

		createFundingRulePathParam.put("goalId", goalId.isEmpty() ? getGoalId :goalId);
		
		int indexOfItemAcntId = Integer.parseInt(goalAccountItemId);
		createFundingRuleBodyParam = sfg.createFundingRule(itemAccountIds.get(indexOfItemAcntId), recurringAmount,
				recurringCurrency, frequency, isAutoTransferRule, sfg.sfgDateFormate(startDate),
				sfg.sfgDateFormate(endDate));

		httpmethodParam.setPathParams(createFundingRulePathParam);
		httpmethodParam.setBodyParams(createFundingRuleBodyParam);

		Response createFundingRuleResp = sfgUtils.createFundingRule(httpmethodParam, sessionObj);
		if (createFundingRuleResp.statusCode() == 400) {
			JSONObject Obj = new JSONObject(createFundingRuleResp.asString());
			assertEquals(Obj.get("errorCode"), errorCode);
			assertEquals(Obj.get("errorMessage"), errorMsg);
		} else {
			assertEquals(createFundingRuleResp.getStatusCode(),201);
		}
	}
}
