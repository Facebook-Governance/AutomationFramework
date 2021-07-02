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
import java.util.List;
import org.json.JSONObject;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import com.yodlee.yodleeApi.assertions.JsonAssertionUtil;
import com.yodlee.yodleeApi.common.Configuration;
import com.yodlee.yodleeApi.constants.JSONPaths;
import com.yodlee.yodleeApi.constants.SaveForGoalConstants;
import com.yodlee.yodleeApi.helper.SaveForGoalHelper;
import com.yodlee.yodleeApi.helper.UserHelper;
import com.yodlee.yodleeApi.pojo.EnvSession;
import com.yodlee.yodleeApi.pojo.User;
import com.yodlee.yodleeApi.utils.apiUtils.ProviderAccountUtils;
import com.yodlee.yodleeApi.utils.apiUtils.SFGUtils;

import io.restassured.response.Response;
import com.yodlee.yodleeApi.common.Base;

public class DeleteFundingRules extends Base {
	public static Long providerAccountId = null;
	String userSession="";
	EnvSession sessionObj=null;
	String userName="SFGYSL"+System.currentTimeMillis();
	Configuration configurationObj= Configuration.getInstance();
	UserHelper userHelper= new UserHelper();
	JsonAssertionUtil jsonAssertionutil=new JsonAssertionUtil();
	ProviderAccountUtils providerAccountUtils=new ProviderAccountUtils();
	SaveForGoalHelper sfg=new SaveForGoalHelper(); 
	SFGUtils sfgUtils=new SFGUtils();
	static List<Integer> itemAccountIds=new ArrayList<>();

	@BeforeClass(alwaysRun=true)
	public void createUser() throws ParseException
	{
		System.out.println("******STARTING***********");
		User userInfo=User.builder().build();
		userInfo.setUsername(userName);
		userInfo.setPassword("Test@123");
		sessionObj = EnvSession.builder().cobSession(configurationObj.getCobrandSessionObj().getCobSession())
				.path(configurationObj.getCobrandSessionObj().getPath()).build();
		userHelper.getUserSession(userInfo, sessionObj);
		long providerId=16441;
		providerAccountId=providerId;
		Response response = providerAccountUtils.addProviderAccountStrict(providerId, "fieldarray",
				"renuka21.site16441.2", "site16441.2", sessionObj);
		providerAccountId = response.jsonPath().getLong(JSONPaths.PROVIDER_ACC_ID);
		System.out.println("providerAccountId1::::===" + providerAccountId);
	}	

	@Test(description="AT-105956,AT-105957,AT-105958",enabled=true,priority=1)
	public void deleteFundingRuleAPI() throws SQLException, ParseException{

		itemAccountIds=sfg.getItemAccountIds(userName);	
		
		String bodyParams= sfg.createGoalJson("goalName100", "1",
				sfg.sfgDateFormate("0,0,1"), sfg.sfgDateFormate("1,0,0"), "500", "USD", "13000", "USD", "MONTHLY", "NOT_STARTED", "NONE", "FALSE","");
		String goalId=sfg.createGoal(bodyParams, sessionObj);
		
		String createDestAccountBodyParam=sfg.createDestinationAccount(itemAccountIds.get(1),1000,"USD");
		sfg.createDestAccount(goalId, createDestAccountBodyParam, sessionObj);
		
		String createFundingRuleBodyParam=sfg.createFundingRule(itemAccountIds.get(1),"1000","USD","MONTHLY","false",sfg.sfgDateFormate("0,0,1"),sfg.sfgDateFormate("1,0,0"));
		String fundingRuleId=sfg.createFundingRule(goalId, createFundingRuleBodyParam, sessionObj);
		
		//<-------verifying by passing invalid goalId------->
		JSONObject invalidGoalIdObj = new JSONObject(sfg.deleteFundingRule(sessionObj, SaveForGoalConstants.InvalidGoalId, fundingRuleId).asString());
		assertEquals(SaveForGoalConstants.ErrorMsgForInvalidGoal, invalidGoalIdObj.get("errorMessage"));
		assertEquals(SaveForGoalConstants.ErrorCode1, invalidGoalIdObj.get("errorCode"));
		
		//<-------verifying by passing Invalid funding ruleId------->
		JSONObject invalidFundingIdObj = new JSONObject(sfg.deleteFundingRule(sessionObj, goalId, SaveForGoalConstants.InvalidFundingRuleId).asString());
		assertEquals(SaveForGoalConstants.ErrorMsgForInvalidFundingId, invalidFundingIdObj.get("errorMessage"));
		assertEquals(SaveForGoalConstants.ErrorCode1, invalidFundingIdObj.get("errorCode"));
		
		//<-------verifying by passing valid goalId and fundingRuleId--------->
		assertEquals(sfg.deleteFundingRule(sessionObj,goalId, fundingRuleId).statusCode(), 204);
	}
}
