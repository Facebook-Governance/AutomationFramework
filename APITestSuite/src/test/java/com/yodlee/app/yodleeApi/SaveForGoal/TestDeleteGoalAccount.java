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

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONObject;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import com.yodlee.yodleeApi.assertions.JsonAssertionUtil;
import com.yodlee.yodleeApi.common.Base;
import com.yodlee.yodleeApi.common.Configuration;
import com.yodlee.yodleeApi.helper.SaveForGoalHelper;
import com.yodlee.yodleeApi.helper.UserHelper;
import com.yodlee.yodleeApi.pojo.EnvSession;
import com.yodlee.yodleeApi.pojo.User;
import com.yodlee.yodleeApi.utils.apiUtils.ProviderAccountUtils;
import com.yodlee.yodleeApi.utils.apiUtils.SFGUtils;
import io.restassured.response.Response;
import com.yodlee.yodleeApi.constants.JSONPaths;
import com.yodlee.yodleeApi.constants.JsonPath;
import com.yodlee.yodleeApi.constants.SaveForGoalConstants;

public class TestDeleteGoalAccount extends Base{
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
		Response response = providerAccountUtils.addProviderAccountStrict(providerId, "fieldarray",
				"renuka21.site16441.2", "site16441.2", sessionObj);
		providerAccountId = response.jsonPath().getLong(JSONPaths.PROVIDER_ACC_ID);
		System.out.println("providerAccountId1::::===" + providerAccountId);
	}	

	@Test(description="AT-104426,AT-104427,AT-104428,AT-104429,AT-104430,AT-104431",enabled=true,priority=1)
	public void deleteGoalAcnt() throws SQLException, ParseException, InterruptedException {
		itemAccountIds=sfg.getItemAccountIds(userName);

		//<-----creating goal1------>
		String createGoalbodyParams1= sfg.createGoalJson("Goal1", "1",
				sfg.sfgDateFormate("0,0,1"), sfg.sfgDateFormate("1,0,0"), "500", "USD", "13000", "USD", "EVERY_2_WEEKS", "NOT_STARTED", "NONE", "FALSE","");
		String goalId1=sfg.createGoal(createGoalbodyParams1, sessionObj);
		
		//<-----Adding destination account to goal1------>
		String createDestAccountBodyParam=sfg.createDestinationAccount(itemAccountIds.get(1),1000,"USD");
		sfg.createDestAccount(goalId1, createDestAccountBodyParam, sessionObj);
	
		//<-----adding funding rule to goal1------>
		String createFundingRuleBodyParam=sfg.createFundingRule(itemAccountIds.get(1),"1000","USD","ONE_TIME","FALSE",sfg.sfgDateFormate("0,0,1"),sfg.sfgDateFormate("0,0,1"));
		sfg.createFundingRule(goalId1, createFundingRuleBodyParam, sessionObj);
		
		Response getGoalDetailResp=sfg.getGoalDetails(goalId1,sessionObj);

		String getFundingRuleAcntId = getGoalDetailResp.jsonPath().getString(JsonPath.GOALACCOUNTID);
		assertTrue(itemAccountIds.contains(Integer.parseInt(getFundingRuleAcntId)));

		//AT-104426 : passing valid goalId and accountId.
		sfg.deleteGoalAccounts(goalId1,itemAccountIds.get(1),sessionObj);

		
		//AT-104427 : funding rule associated with goal should be deleted after deleting account.
		try {
			sfg.getGoalDetails(goalId1,sessionObj).jsonPath().getString(JsonPath.GOALACCOUNTID);
			assertTrue(false, "Funding Rule Entity is found in the response after deleting goal account which is bug.");
		}catch(Exception e) {
			assertTrue(true, "Funding Rule Entity is not found in the response after deleting goal account.");
		}
		
		//<-----creating goal2------>
		String createGoalbodyParams2= sfg.createGoalJson("Goal2", "1",
				sfg.sfgDateFormate("0,0,1"), sfg.sfgDateFormate("1,0,0"), "500", "USD", "1300", "USD", "MONTHLY", "NOT_STARTED", "NONE", "FALSE","");
		String goalId2=sfg.createGoal(createGoalbodyParams2, sessionObj);
		
		//<-----Adding destination account to goal2------>
		String createDestAccountBodyParam2=sfg.createDestinationAccount(itemAccountIds.get(0),1000,"USD");
		sfg.createDestAccount(goalId2, createDestAccountBodyParam2, sessionObj);

		
		//AT-104430,AT-104431 : Assert when account is invalid
		JSONObject InvalidAccountIdObj = new JSONObject(sfg.deleteGoalAccounts(goalId2,itemAccountIds.get(2),sessionObj).asString());
		assertEquals(SaveForGoalConstants.ErrorCode1, InvalidAccountIdObj.get("errorCode"));
        assertEquals(InvalidAccountIdObj.get("errorMessage"), SaveForGoalConstants.ErrorMsgForInvalidAccountId);
	
		//<-------updating goal status Not_Started to Completed.------>
        String updateBodyParams= sfg.updateGoalBodyParam("", "", "", "","", "", "", "","", "COMPLETED", "", "");
        sfg.updateGoal(goalId2, updateBodyParams, sessionObj);

        
		//AT-104428: the user should not be able to delete account when goal is in completed status.
		JSONObject CompletedStatusGoalObj = new JSONObject(sfg.deleteGoalAccounts(goalId2,itemAccountIds.get(0),sessionObj).asString());
		assertEquals(SaveForGoalConstants.ErrorCode1, CompletedStatusGoalObj.get("errorCode"));
		assertEquals(CompletedStatusGoalObj.get("errorMessage"), SaveForGoalConstants.ErrorMsgForInvalidGoal);

		
		//AT-104429 : Invalid goal Id
		JSONObject invalidGoalIdObj= new JSONObject(sfg.deleteGoalAccounts(SaveForGoalConstants.InvalidGoalId,itemAccountIds.get(0),sessionObj).asString());
		assertEquals(SaveForGoalConstants.ErrorCode1, invalidGoalIdObj.get("errorCode"));
		assertEquals(invalidGoalIdObj.get("errorMessage"),SaveForGoalConstants.ErrorMsgForInvalidGoal);
	}
}
