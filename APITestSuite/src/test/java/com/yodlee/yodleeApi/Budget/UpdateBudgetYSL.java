/**
* Copyright (c) 2018 Yodlee Inc. All Rights Reserved.
*
* This software is the confidential and proprietary information of
* Yodlee, Inc. Use is subject to license terms.
*
* @author Raghavendra Yadav G
*/

package com.yodlee.yodleeApi.Budget;

import static org.testng.Assert.assertEquals;

import com.yodlee.yodleeApi.common.Base;
import com.yodlee.yodleeApi.common.Configuration;
import com.yodlee.yodleeApi.constants.JSONPaths;
import com.yodlee.yodleeApi.helper.BudgetHelper;
import com.yodlee.yodleeApi.helper.UserHelper;
import com.yodlee.yodleeApi.pojo.EnvSession;
import com.yodlee.yodleeApi.pojo.HttpMethodParameters;
import com.yodlee.yodleeApi.pojo.User;
import com.yodlee.yodleeApi.utils.apiUtils.BudgetUtils;
import com.yodlee.yodleeApi.utils.apiUtils.GroupUtils;
import com.yodlee.yodleeApi.utils.apiUtils.ProviderAccountUtils;
import com.yodlee.yodleeApi.utils.commonUtils.CommonUtils;

import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

import org.apache.commons.lang.RandomStringUtils;
import org.databene.benerator.anno.Source;
import org.jose4j.lang.JoseException;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import io.restassured.response.Response;

public class UpdateBudgetYSL extends Base {

	// Variable initialization
	String zeroBudgetAmount = "0";
	String userSession = null;
	String cobSession = null;
	public static Long providerAccountId = null;
	String negativeAmount = "-200";

	// ArrayList initialization
	ArrayList<Integer> categoryId = new ArrayList<Integer>();
	ArrayList<String> categoryType = new ArrayList<String>();
	Configuration config = Configuration.getInstance();

	// Hash Map deceleration
	HashMap<Integer, String> budgetDetails = new HashMap<Integer, String>();

	// Soft assertion object initialization
	SoftAssert softAssert = new SoftAssert();
	CommonUtils CommonUtils = new CommonUtils();
	BudgetUtils budgetUtils = new BudgetUtils();
	BudgetHelper bHelper = new BudgetHelper();

	HttpMethodParameters groupJson1 = null;
	HttpMethodParameters groupJson2 = null;
	String groupId1 = null;
	GroupUtils groupUtils = new GroupUtils();
	String currencyType = null;
	String groupName = null;
	Random rand = new Random();
	String userName = "BudegtUser" + System.currentTimeMillis();
	EnvSession sessionObj = null;
	UserHelper userHelper = new UserHelper();
	ProviderAccountUtils providerAccountUtils = new ProviderAccountUtils();

	// Initialization of .CSV file location
	final static String createBudget = "\\TestData\\CSVFiles\\Budget\\createBudget_Test1.csv";
	final static String updateBudget1 = "\\TestData\\CSVFiles\\Budget\\UpdateBudget_Test1.csv";
	final static String updateBudget2 = "\\TestData\\CSVFiles\\Budget\\UpdateBudget_Test2.csv";
	final static String updateBudget3 = "\\TestData\\CSVFiles\\Budget\\UpdateBudget_Test3.csv";
	final static String updateBudget4 = "\\TestData\\CSVFiles\\Budget\\UpdateBudget_Test4.csv";
	final static String updateBudget5 = "\\TestData\\CSVFiles\\Budget\\UpdateBudget_Test5.csv";
	final static String updateBudget6 = "\\TestData\\CSVFiles\\Budget\\UpdateBudget_Test6.csv";

	
	@BeforeClass(alwaysRun = true)
	public void createUser() throws ParseException, SQLException {
		System.out.println("******STARTING***********");
		User userInfo = User.builder().build();
		userInfo.setUsername(userName);
		userInfo.setPassword("Test@123");
		sessionObj = EnvSession.builder().cobSession(config.getCobrandSessionObj().getCobSession())
				.path(config.getCobrandSessionObj().getPath()).build();
		userHelper.getUserSession(userInfo, sessionObj);
		long providerId = 16441;
		providerAccountId = providerId;
		Response response = providerAccountUtils.addProviderAccountStrict(providerId, "fieldarray",
				"getBudget.site16441.1", "site16441.1", sessionObj);
		providerAccountId = response.jsonPath().getLong(JSONPaths.PROVIDER_ACC_ID);
	}
	
	@Test(enabled = true, dataProvider = "feeder")
	@Source(createBudget)
	public void initiateCobAndUserSession(String testCaseId, String testCase, String enabled, String testNo1,
			String catId, String catType, String currType) throws Exception, IOException, JoseException {
		groupName = RandomStringUtils.randomAlphabetic(10);
		groupJson1 = bHelper.createGroupJson(groupName);

		Response GroupResponse = groupUtils.createGroup(groupJson1, sessionObj);
		groupId1 = GroupResponse.jsonPath().getString("group.id");

		categoryId.add(Integer.parseInt(catId.split(",")[rand.nextInt(10)]));
		categoryType.add(catType.split(",")[rand.nextInt(3)]);
		String BudgetAmount1 = String.valueOf(rand.nextInt(1000));
		currencyType = currType.split(",")[rand.nextInt(10)];
		String catDataAndCatTypeBodyReqParam = bHelper.CreateCatDataAndCatType(groupId1, categoryType, categoryId,
				BudgetAmount1, currencyType);

		HttpMethodParameters httpMethodParameters1 = HttpMethodParameters.builder().build();
		httpMethodParameters1.setBodyParams(catDataAndCatTypeBodyReqParam);
		BudgetUtils budgetUtils1 = new BudgetUtils();
		budgetUtils1.createBudget(httpMethodParameters1, sessionObj);
	}

	@Test(enabled = true, dataProvider = "feeder", priority = 3)
	@Source(updateBudget1)
	public void updateBudget1(String testCaseId, String testCase, String enabled, String testNo1, String catId,
			String catType, String currType) throws Exception {

		CommonUtils.isTCEnabled(enabled, testCaseId);
		System.out.println(" EXECUTING TEST CASE............" + testCase + "TESTCASE ID.........." + testCaseId);

		String catDataBodyReqParam = bHelper.UpdateBudgetAtCatData(groupId1, categoryId, zeroBudgetAmount, "USD");

		HttpMethodParameters httpMethodParameters = HttpMethodParameters.builder().build();
		httpMethodParameters.setBodyParams(catDataBodyReqParam);

		Response updateResponseForCatdata = budgetUtils.updateBudget(httpMethodParameters,
				sessionObj);

		softAssert.assertEquals(updateResponseForCatdata.jsonPath().getString("errorMessage"),
				"Budget cannot be updated for zero, negative Amount",
				"Budget cannot be updated for zero, negative Amount at category Data level");

		String CatDataNegBgtAmt = bHelper.UpdateBudgetAtCatData(groupId1, categoryId, currencyType + "", "USD");

		httpMethodParameters.setBodyParams(CatDataNegBgtAmt);
		Response updateResponseForCatDataNegBgtAmt = budgetUtils.updateBudget(httpMethodParameters,
				sessionObj);
		softAssert.assertEquals(updateResponseForCatDataNegBgtAmt.jsonPath().getString("errorMessage"),
				"Budget cannot be updated for zero, negative Amount",
				"Budget cannot be updated for zero, negative Amount at category Data level");

		String catTypeBodyReqParam = bHelper.UpdateBudgetAtCatType(groupId1, categoryType, zeroBudgetAmount, "USD");
		httpMethodParameters.setBodyParams(catTypeBodyReqParam);
		Response updateResponseForCatdata1 = budgetUtils.updateBudget(httpMethodParameters,
				sessionObj);
		System.out.println("updateResponseForCatdata1::"+updateResponseForCatdata1.asString());
		softAssert.assertEquals(updateResponseForCatdata1.jsonPath().getString("errorMessage"),
				"Budget cannot be updated for zero, negative Amount",
				"Budget cannot be updated for zero, negative Amount at category Type level");

		String CatTypeNegBgtAmt = bHelper.UpdateBudgetAtCatType(groupId1, categoryType, negativeAmount, "USD");
		httpMethodParameters.setBodyParams(CatTypeNegBgtAmt);
		Response updateResponseForCatTypeNegBgtAmt = budgetUtils.updateBudget(httpMethodParameters,
				sessionObj);
		softAssert.assertEquals(updateResponseForCatTypeNegBgtAmt.jsonPath().getString("errorMessage"),
				"Budget cannot be updated for zero, negative Amount",
				"Budget cannot be updated for zero, negative Amount at category Type level");

	}

	@Test(enabled = true, dataProvider = "feeder",priority = 2)
	@Source(updateBudget2)
	public void updateBudget2(String testCaseId, String testCase, String enabled, String testNo1, String catId,
			String catType, String currType) throws Exception {

		CommonUtils.isTCEnabled(enabled, testCaseId);
		System.out.println(" EXECUTING TEST CASE............" + testCase + "TESTCASE ID.........." + testCaseId);

		String catDataBodyReqParam1 = bHelper.UpdateBudgetAtCatData(groupId1, categoryId, currencyType, "AEDS");
		HttpMethodParameters httpMethodParameters = HttpMethodParameters.builder().build();
		httpMethodParameters.setBodyParams(catDataBodyReqParam1);
		Response updateResponseForCatdata1 = budgetUtils.updateBudget(httpMethodParameters,
				sessionObj);
		softAssert.assertEquals(updateResponseForCatdata1.jsonPath().getString("errorMessage"),
				"Budget currency is invalid", "Budget currency is invalid at category Data level");

		String catTypeBodyReqParam = bHelper.UpdateBudgetAtCatType(groupId1, categoryType, currencyType, "AEDS");
		httpMethodParameters.setBodyParams(catTypeBodyReqParam);
		Response updateResponseForCatType = budgetUtils.updateBudget(httpMethodParameters,
				sessionObj);
		softAssert.assertEquals(updateResponseForCatType.jsonPath().getString("errorMessage"),
				"Budget currency is invalid", "Budget currency is invalid at category Type level");
	}

	@Test(enabled = true, dataProvider = "feeder",priority = 1)
	@Source(updateBudget3)
	public void updateBudget3(String testCaseId, String testCase, String enabled, String testNo1, String catId,
			String catType, String currType) throws Exception {

		CommonUtils.isTCEnabled(enabled, testCaseId);
		System.out.println(" EXECUTING TEST CASE............" + testCase + "TESTCASE ID.........." + testCaseId);
		categoryType.add("EXPENSE");
		String updatebgtNewCatTypeAndExsCatTypeReq = bHelper.UpdateBudgetAtCatType(groupId1, categoryType, currencyType,
				"USD");
		HttpMethodParameters httpMethodParameters = HttpMethodParameters.builder().build();
		httpMethodParameters.setBodyParams(updatebgtNewCatTypeAndExsCatTypeReq);
		budgetUtils.updateBudget(httpMethodParameters, sessionObj);
		bHelper.verifyBudgeatAtDB(userName, "AED", "catType", "30", false);

			CommonUtils.isTCEnabled(enabled, testCaseId);
			System.out.println(" EXECUTING TEST CASE............" + testCase + "TESTCASE ID.........." + testCaseId);

			String InvalidAccGrpIdReqParam = bHelper.UpdateBudgetAtCatData("1017149389", categoryId, currencyType,
					"USD");
			HttpMethodParameters httpMethodParameters1 = HttpMethodParameters.builder().build();
			httpMethodParameters1.setBodyParams(InvalidAccGrpIdReqParam);
			Response UpdateBgtRespInvalidGrpId = budgetUtils.updateBudget(httpMethodParameters1,
					sessionObj);
			softAssert.assertEquals(UpdateBgtRespInvalidGrpId.jsonPath().getString("errorMessage"),
					"Invalid value for groupId", "Invalid value for account group Id");
	}
	
	@Test(enabled = true, dataProvider = "feeder",priority = 1)
	@Source(updateBudget4)
	public void updateBudget4(String testCaseId, String testCase, String enabled, String testNo1, String catId,
			String catType, String currType) throws Exception {

		CommonUtils.isTCEnabled(enabled, testCaseId);
		System.out.println(" EXECUTING TEST CASE............" + testCase + "TESTCASE ID.........." + testCaseId);
		categoryId.add(32);
		String updatebgtNewCatDataAndExsCatDataReq = bHelper.UpdateBudgetAtCatData(groupId1, categoryId, currencyType,
				"USD");
		HttpMethodParameters httpMethodParameters = HttpMethodParameters.builder().build();
		httpMethodParameters.setBodyParams(updatebgtNewCatDataAndExsCatDataReq);
		budgetUtils.updateBudget(httpMethodParameters, sessionObj);
		bHelper.verifyBudgeatAtDB(userName, "USD", "catData", currencyType, false);
		categoryType.add("TRANSFER");
		String updatebgtNewCatTypeAndExsCatTypeReq = bHelper.UpdateBudgetAtCatType(groupId1, categoryType,
				currencyType, "USD");
		HttpMethodParameters httpMethodParameters1 = HttpMethodParameters.builder().build();
		httpMethodParameters1.setBodyParams(updatebgtNewCatTypeAndExsCatTypeReq);
		budgetUtils.updateBudget(httpMethodParameters1, sessionObj);
		HashMap<String, Object> params = new HashMap<>();
		params.put("include", "categoryData");
		HttpMethodParameters parameters = HttpMethodParameters.builder().queryParams(params).build();
		Response getYslResp = budgetUtils.getBudget(parameters, sessionObj);
		softAssert.assertEquals(getYslResp.jsonPath().getString("categoryType"),
				"EXPENSE", "EXPENSE");
	}

	@Test(enabled = true, dataProvider = "feeder",priority = 5)
	@Source(updateBudget6)
	public void updateBudget6(String testCaseId, String testCase, String enabled, String testNo1, String catId,
			String catType, String currType) throws Exception {

		CommonUtils.isTCEnabled(enabled, testCaseId);
		System.out.println(" EXECUTING TEST CASE............" + testCase + "TESTCASE ID.........." + testCaseId);
		categoryType.add("TRANSFERS");
		String updatebgtNewCatTypeAndExsCatTypeReq = bHelper.UpdateBudgetAtCatType(groupId1, categoryType, currencyType,
				"USD");
		HttpMethodParameters httpMethodParameters = HttpMethodParameters.builder().build();
		httpMethodParameters.setBodyParams(updatebgtNewCatTypeAndExsCatTypeReq);
		Response updatebgtNewCatTypeAndExsCatTypeResp = budgetUtils.updateBudget(httpMethodParameters,
				sessionObj);
		softAssert.assertEquals(updatebgtNewCatTypeAndExsCatTypeResp.jsonPath().getString("errorMessage"),
				"Invalid value for categoryType", "Invalid value for categoryType");

	}

	@Test(enabled = true, dataProvider = "feeder",priority = 6)
	@Source(updateBudget5)
	public void updateBudget5(String testCaseId, String testCase, String enabled, String testNo1, String catId,
			String catType, String currType) throws Exception {

		CommonUtils.isTCEnabled(enabled, testCaseId);
		System.out.println(" EXECUTING TEST CASE............" + testCase + "TESTCASE ID.........." + testCaseId);
		categoryId.clear();
		categoryId.add(323);
		String updatebgtNewCatDataAndExsCatDataReq = bHelper.UpdateBudgetAtCatData(groupId1, categoryId, currencyType,
				"USD");
		HttpMethodParameters httpMethodParameters = HttpMethodParameters.builder().build();
		httpMethodParameters.setBodyParams(updatebgtNewCatDataAndExsCatDataReq);
		Response updateResponseForCatdata = budgetUtils.updateBudget(httpMethodParameters,
				sessionObj);
		softAssert.assertEquals(updateResponseForCatdata.jsonPath().getString("errorMessage"),
				"Invalid value for categoryId", "Invalid value for categoryId");

	}
}