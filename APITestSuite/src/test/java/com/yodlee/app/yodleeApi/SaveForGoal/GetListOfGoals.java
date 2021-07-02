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

import static org.testng.Assert.assertTrue;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import com.yodlee.yodleeApi.assertions.JsonAssertionUtil;
import com.yodlee.yodleeApi.common.Base;
import com.yodlee.yodleeApi.common.Configuration;
import com.yodlee.yodleeApi.constants.JSONPaths;
import com.yodlee.yodleeApi.helper.SaveForGoalHelper;
import com.yodlee.yodleeApi.helper.UserHelper;
import com.yodlee.yodleeApi.pojo.EnvSession;
import com.yodlee.yodleeApi.pojo.User;
import com.yodlee.yodleeApi.utils.apiUtils.AccountUtils;
import com.yodlee.yodleeApi.utils.apiUtils.ProviderAccountUtils;
import com.yodlee.yodleeApi.utils.apiUtils.SFGUtils;

import io.restassured.response.Response;

public class GetListOfGoals extends Base {
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

	@Test(description="AT-105951,AT-105952,AT-105953,AT-105954,AT-105955",enabled = true, priority = 1, dataProvider = "dp")
	public void getListOfGoals(String itemAccountId, String goalNames, String itemAccountIds, String expectedGoalNames)
			throws SQLException, ParseException {
		List<String> expected = Arrays.asList(expectedGoalNames.split(","));

		String[] names = goalNames.split(",");

		for (String goalName : names) {
			String bodyParams = sfg.createGoalJson(goalName, "1", sfg.sfgDateFormate("0,0,1"), sfg.sfgDateFormate("1,0,0"),
					"500", "USD", "13000", "USD", "EVERY_2_WEEKS", "NOT_STARTED", "NONE", "FALSE","");
			
			String goalId = sfg.createGoalWithGoalName(goalName,bodyParams,sessionObj);
			sfg.createDestAccount(Integer.parseInt(itemAccountId), goalId,sessionObj);
		}

		Response getAllGoalForAccount1 = sfg.getAllGoalsRespectiveToAcnt(itemAccountIds,sessionObj);
		List<HashMap<String, List<HashMap<String, String>>>> list = getAllGoalForAccount1.jsonPath().getList("account");

		list.stream().forEach(t -> {

			t.get("goals").forEach(t1 -> {
				assertTrue(expected.contains(t1.get("name")), "Account doesn't have goal::" + t1.get("name"));

			});

		});
	}
	
	@DataProvider(name = "dp")
	public Object[][] dp() {
		List<Integer> itemAccountIds = sfg.getItemAccountIds(userName);
		String[][] cases = new String[2][4];
		for (int i = 0; i < cases.length; i++) {
			cases[i][0] = String.valueOf(itemAccountIds.get(i));
			int k = i < 1 ? 0 : 2;
			cases[i][1] = "GoalName" + (k + 1) + "," + "GoalName" + (k + 2);

			String itemAccountidsToVerify = k == 0 ? String.valueOf(itemAccountIds.get(0))
					: String.valueOf(itemAccountIds.get(0) + "," + itemAccountIds.get(1));
			cases[i][2] = itemAccountidsToVerify;

			if (k == 0) {
				cases[i][3] = "GoalName1,GoalName2";
			} else {
				cases[i][3] = "GoalName1,GoalName2,GoalName3,GoalName4";
			}

		}
		System.out.println(cases);
		return cases;
	}

}
