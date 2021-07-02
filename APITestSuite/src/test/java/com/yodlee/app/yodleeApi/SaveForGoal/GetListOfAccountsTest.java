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
import static org.testng.Assert.assertTrue;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import org.json.JSONObject;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import com.yodlee.yodleeApi.assertions.JsonAssertionUtil;
import com.yodlee.yodleeApi.common.Base;
import com.yodlee.yodleeApi.common.Configuration;
import com.yodlee.yodleeApi.constants.JSONPaths;
import com.yodlee.yodleeApi.constants.JsonPath;
import com.yodlee.yodleeApi.constants.SaveForGoalConstants;
import com.yodlee.yodleeApi.helper.SaveForGoalHelper;
import com.yodlee.yodleeApi.helper.UserHelper;
import com.yodlee.yodleeApi.pojo.EnvSession;
import com.yodlee.yodleeApi.pojo.User;
import com.yodlee.yodleeApi.utils.apiUtils.AccountUtils;
import com.yodlee.yodleeApi.utils.apiUtils.ProviderAccountUtils;
import com.yodlee.yodleeApi.utils.apiUtils.SFGUtils;

import io.restassured.response.Response;

public class GetListOfAccountsTest extends Base{
	public static Long providerAccountId = null;
	String userSession = "";
	EnvSession sessionObj = null;
	String userName = "SFGYSL" + System.currentTimeMillis();
	Configuration configurationObj = Configuration.getInstance();
	UserHelper userHelper = new UserHelper();
	JsonAssertionUtil jsonAssertionutil = new JsonAssertionUtil();
	ProviderAccountUtils providerAccountUtils = new ProviderAccountUtils();
	SaveForGoalHelper sfg = new SaveForGoalHelper();
	SFGUtils sfgUtils = new SFGUtils();
	AccountUtils accountUtil = new AccountUtils();
	static List<Integer> itemAccountIds = new LinkedList<Integer>();

	@BeforeClass(alwaysRun = true)
	public void createUser() throws ParseException {
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
	}

	@Test(description="AT-106007,AT-106008,AT-106009,AT-106010,AT-106011",enabled=true,priority=1)
	public void getListOfAccounts() throws SQLException, ParseException, InterruptedException {
		itemAccountIds = sfg.getItemAccountIds(userName);

		String createGoalBodyParams1 = sfg.createGoalJson("GoalName500", "1", sfg.sfgDateFormate("0,0,1"),
				sfg.sfgDateFormate("1,0,0"), "100", "USD", "13000", "USD", "MONTHLY", "NOT_STARTED", "NONE", "FALSE","");
		String goalId1=sfg.createGoal(createGoalBodyParams1,sessionObj);	

		String createGoalBodyParams2 = sfg.createGoalJson("GoalName600", "1", sfg.sfgDateFormate("0,0,1"),
				sfg.sfgDateFormate("1,0,0"), "100", "USD", "1300", "USD", "MONTHLY", "NOT_STARTED", "NONE", "FALSE","");
		String goalId2=sfg.createGoal(createGoalBodyParams2,sessionObj);	

		String createDestAccountBodyParam1=sfg.createDestinationAccount(itemAccountIds.get(0),1000,"USD");
		sfg.createDestAccount(goalId1, createDestAccountBodyParam1, sessionObj);

		String createDestAccountBodyParam2=sfg.createDestinationAccount(itemAccountIds.get(1),100,"USD");
		sfg.createDestAccount(goalId2, createDestAccountBodyParam2, sessionObj);

		//Invalid accountItemId
		JSONObject invalidIdObj = new JSONObject(sfg.getAllGoalsRespectiveToAcnt(SaveForGoalConstants.InvalidAccountId,sessionObj).asString());
		assertEquals(SaveForGoalConstants.ErrorCode1, invalidIdObj.get("errorCode"));
		assertEquals(SaveForGoalConstants.ErrorMsgForInvalidAcountId, invalidIdObj.get("errorMessage"));
		
		String itemAccountId1=itemAccountIds.get(0).toString();
		Response respWithAccount1=sfg.getAllGoalsRespectiveToAcnt(itemAccountId1,sessionObj);
		
		assertEquals(respWithAccount1.jsonPath().getString(JsonPath.getAccountName), SaveForGoalConstants.goalAccount1);
		assertEquals(respWithAccount1.jsonPath().getString(JsonPath.getGoalName), SaveForGoalConstants.goal1);
		
		String itemAccountsId2= itemAccountIds.get(1).toString();
		String bothItemAccountIds= itemAccountId1+","+itemAccountsId2;
		
		Response respWithAccount1And2=sfg.getAllGoalsRespectiveToAcnt(bothItemAccountIds,sessionObj);
		String []itemAccountIds= bothItemAccountIds.split(",");
		
		String ExpectedGoalName[]= {SaveForGoalConstants.goal1,SaveForGoalConstants.goal2};
		String ExpectedAccountName[]= {SaveForGoalConstants.goalAccount2,SaveForGoalConstants.goalAccount1};
		
		for(int i=0;i<itemAccountIds.length;i++) {
			assertTrue(Arrays.asList(ExpectedAccountName).contains(respWithAccount1And2.jsonPath().getString("account["+i+"].accountName")));
			assertTrue(Arrays.asList(ExpectedGoalName).contains(respWithAccount1And2.jsonPath().getString("account["+i+"].goals[0].name")));	
		}
	}
}

