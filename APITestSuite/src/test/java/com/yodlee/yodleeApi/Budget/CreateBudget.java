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

package com.yodlee.yodleeApi.Budget;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Random;

import org.apache.commons.lang.RandomStringUtils;
import org.databene.benerator.anno.Source;
import org.jose4j.lang.JoseException;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import com.yodlee.DBHelper;
import com.yodlee.yodleeApi.common.Base;
import com.yodlee.yodleeApi.common.Configuration;
import com.yodlee.yodleeApi.constants.JSONPaths;
import com.yodlee.yodleeApi.helper.BudgetHelper;
import com.yodlee.yodleeApi.helper.ProviderAccountsHelper;
import com.yodlee.yodleeApi.helper.UserHelper;
import com.yodlee.yodleeApi.pojo.EnvSession;
import com.yodlee.yodleeApi.pojo.HttpMethodParameters;
import com.yodlee.yodleeApi.pojo.User;
import com.yodlee.yodleeApi.utils.apiUtils.BudgetUtils;
import com.yodlee.yodleeApi.utils.apiUtils.GroupUtils;
import com.yodlee.yodleeApi.utils.apiUtils.ProviderAccountUtils;
import com.yodlee.yodleeApi.utils.commonUtils.CommonUtils;
import com.yodlee.yodleeApi.utils.commonUtils.PropertyFileUtil;

import io.restassured.response.Response;

public class CreateBudget extends Base {

	// Global ArrayList variables declearation and initialization
	ArrayList<Integer> categoryId = new ArrayList<Integer>();
	ArrayList<String> categoryType = new ArrayList<String>();
	ArrayList<String> arrayOfCatId = new ArrayList<String>();
	ArrayList<Integer> arrayOfCatIdInt = new ArrayList<Integer>();
	ArrayList<String> arrayOfCatType = new ArrayList<String>();
	ArrayList<Integer> arrayOfCatTypeInt = new ArrayList<Integer>();
	ArrayList<String> arrayData = new ArrayList<String>();
	
	// Global String variables declearation and initialization
	String newRandomUser = null;
	String currencyType = null;
	HttpMethodParameters groupJson1 = null;
	HttpMethodParameters groupJson2 = null;
	String groupId1 = null;
	String groupId2 = null;
	String zeroBudgetAmount = "0";
	String negativeAmount = "-200";
	String userSession = null;
	String cobSession = null;
	String validations = "10171599890";
	UserHelper userHelper = new UserHelper();

	// Object creation
	SoftAssert softAssert = new SoftAssert();
	CommonUtils commonUtils = new CommonUtils();
	GroupUtils groupUtils = new GroupUtils();
	BudgetUtils budgetUtils = new BudgetUtils();
	DBHelper dbHelper = new DBHelper();
	public static Long providerAccountId = null;

	// Initialization of Response objects
	Response groupResponse = null;
	Response groupResponse1 = null;
	public String dagUN = "GetAccountsDag.site16441.1";
	public String dagPwd = "site16441.1";
	EnvSession sessionObj = null;
	// Initialization of File objects
	File categoryIdFile = null;
	File categoryTypeFile = null;
	File currencyTypeFile = null;
	BudgetHelper bHelper = new BudgetHelper();
	Configuration config = Configuration.getInstance();
	ProviderAccountUtils providerAccountUtils = new ProviderAccountUtils();
	PropertyFileUtil propertyFileUtilObj = new PropertyFileUtil();
	ProviderAccountsHelper providerAccountsHelper = new ProviderAccountsHelper();
	Random rand = new Random();
	String userName = "BudegtUser" + System.currentTimeMillis();

	// Initialization of .CSV file location
	final static String createBudget_Test1 = "\\TestData\\CSVFiles\\Budget\\createBudget_Test1.csv";
	final static String createBudget_Test2 = "\\TestData\\CSVFiles\\Budget\\createBudget_Test2.csv";
	final static String createBudget_Test3 = "\\TestData\\CSVFiles\\Budget\\createBudget_Test3.csv";
	final static String createBudget_Test4 = "\\TestData\\CSVFiles\\Budget\\createBudget_Test4.csv";
	final static String createBudget_Test5 = "\\TestData\\CSVFiles\\Budget\\createBudget_Test5.csv";
	final static String createBudget_Test6 = "\\TestData\\CSVFiles\\Budget\\createBudget_Test6.csv";
	final static String createBudget_Test7 = "\\TestData\\CSVFiles\\Budget\\createBudget_Test7.csv";
	final static String createBudget_Test8 = "\\TestData\\CSVFiles\\Budget\\createBudget_Test8.csv";

	/**
	 * In before class initializing the cobrand and new usersession 2 - Adding
	 * Dag xml site 3 - Creating Group and fetching group id.
	 * 
	 * @throws IOException
	 * @throws JoseException
	 **/
	

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
		
		groupJson1 = bHelper.createGroupJson(RandomStringUtils.randomAlphabetic(10));
		groupJson2 = bHelper.createGroupJson(RandomStringUtils.randomAlphabetic(10));

		Response groupResponse = groupUtils.createGroup(groupJson1, sessionObj);
		groupId1 = groupResponse.jsonPath().getString("group.id");
		Response groupResponse1 = groupUtils.createGroup(groupJson2,sessionObj);
		groupId2 = groupResponse1.jsonPath().getString("group.id");
		arrayData.add(newRandomUser);
	}


	@SuppressWarnings("static-access")
	@Test(enabled = true, dataProvider = "feeder", priority = 3)
	@Source(createBudget_Test3)
	public void createBudgetTest3(String testCaseId, String testCase, String enabled, String testNo1, String catId,
			String catType, String currType) throws Exception {

		commonUtils.isTCEnabled(enabled, testNo1);
		System.out.println(" EXECUTING TEST CASE   >>>>>>>>>>>>>>   " + testCase + " testCase ID   >>>>>>>>>>>>>>   "
				+ testCaseId);
		categoryId.clear();
		categoryId.add(Integer.parseInt(catId.split(",")[rand.nextInt(10)]));
		String BudgetAmount = String.valueOf(rand.nextInt(1000));
		currencyType = currType.split(",")[rand.nextInt(10)];
		String catDataAndCatTypeBodyReqParam = bHelper.CreateBudgetAtCatData(groupId1, categoryId, BudgetAmount,
				currencyType);

		HttpMethodParameters httpMethodParameters = HttpMethodParameters.builder().build();
		httpMethodParameters.setBodyParams(catDataAndCatTypeBodyReqParam);
		
		Response catDataAndCatTypeBodyResp = budgetUtils.createBudget(httpMethodParameters,
				sessionObj);

		if (catDataAndCatTypeBodyResp.statusCode() == 200)
			bHelper.verifyBudgeatAtDB(newRandomUser, currencyType, "catData", BudgetAmount, false);
		else
			softAssert.fail("There is something issue while getting API response for create budget at category data");
	}

	@SuppressWarnings("static-access")
	@Test(enabled = true, dataProvider = "feeder", priority = 3)
	@Source(createBudget_Test7)
	public void createBudgetTest7(String testCaseId, String testCase, String enabled, String testNo1, String catId,
			String catType, String currType) throws Exception {

		commonUtils.isTCEnabled(enabled, testNo1);
		System.out.println(" EXECUTING TEST CASE   >>>>>>>>>>>>>>   " + testCase + " testCase ID   >>>>>>>>>>>>>>   "
				+ testCaseId);
		categoryId.clear();
		categoryId.add(Integer.parseInt(catId.split(",")[rand.nextInt(10)]));
		String BudgetAmount = String.valueOf(rand.nextInt(1000));
		currencyType = currType.split(",")[rand.nextInt(10)];
		String catDataAndCatTypeBodyReqParam = bHelper.CreateBudgetAtCatData(groupId1, categoryId, BudgetAmount,
				currencyType);

		HttpMethodParameters httpMethodParameters = HttpMethodParameters.builder().build();
		httpMethodParameters.setBodyParams(catDataAndCatTypeBodyReqParam);

		Response catDataAndCatTypeBodyResp = budgetUtils.createBudget(httpMethodParameters,
				sessionObj);

		if (catDataAndCatTypeBodyResp.statusCode() == 200)
			bHelper.verifyBudgeatAtDB(newRandomUser, currencyType, "catData", BudgetAmount, false);
		else
			softAssert.fail("There is something issue while getting API response for create budget at category data");
	}

	@SuppressWarnings("static-access")
	@Test(enabled = true, dataProvider = "feeder", priority = 2)
	@Source(createBudget_Test2)
	public void createBudgetTest2(String TestCaseId, String TestCase, String enabled, String UniqueNo, String catId,
			String catType, String currType) throws Exception {

		commonUtils.isTCEnabled(enabled, TestCaseId);
		System.out.println(" EXECUTING TEST CASE   >>>>>>>>>>>>>>   " + TestCase + " testCase ID   >>>>>>>>>>>>>>   "
				+ TestCaseId);

		arrayOfCatId.add(catId.split(",")[rand.nextInt(10)]);
		currencyType = currType.split(",")[rand.nextInt(10)];
		String BudgetAmount = String.valueOf(rand.nextInt(1000));
		arrayOfCatId.add(catId.split(",")[rand.nextInt(10)]);

		for (int i = 0; i < arrayOfCatId.size(); i++) {
			arrayOfCatIdInt.add(Integer.parseInt(arrayOfCatId.get(i)));
		}
		String catDataBodyReqParam = bHelper.CreateBudgetAtCatData(groupId2, arrayOfCatIdInt, BudgetAmount,
				currencyType);

		HttpMethodParameters httpMethodParameters = HttpMethodParameters.builder().build();
		httpMethodParameters.setBodyParams(catDataBodyReqParam);
		BudgetUtils budgetUtils = new BudgetUtils();
		Response catDataAndCatTypeBodyResp = budgetUtils.createBudget(httpMethodParameters,
				sessionObj);

		if (catDataAndCatTypeBodyResp.statusCode() == 200)
			bHelper.verifyBudgeatAtDB(newRandomUser, currencyType, "catData", BudgetAmount, false);
		else
			softAssert.fail("There is something issue while getting API response for create budget at category data");

	}

	@SuppressWarnings("static-access")
	@Test(enabled = true, dataProvider = "feeder", priority = 2)
	@Source(createBudget_Test1)
	public void createBudgetTest1(String testCaseId, String testCase, String enabled, String testNo1, String catId,
			String catType, String currType) throws Exception {

		commonUtils.isTCEnabled(enabled, testNo1);
		System.out.println(" EXECUTING TEST CASE   >>>>>>>>>>>>>>   " + testCase + " testCase ID   >>>>>>>>>>>>>>   "
				+ testCaseId);
		arrayOfCatType.clear();
		arrayOfCatType.add(catType.split(",")[1]);
		currencyType = currType.split(",")[rand.nextInt(10)];
		String BudgetAmount = String.valueOf(rand.nextInt(1000));
		arrayOfCatType.add(catType.split(",")[0]);
		arrayData.add(BudgetAmount);
		String catTypeBodyReqParam = bHelper.CreateBudgetAtCatType(groupId2, arrayOfCatType, BudgetAmount,
				currencyType);

		HttpMethodParameters httpMethodParameters = HttpMethodParameters.builder().build();
		httpMethodParameters.setBodyParams(catTypeBodyReqParam);
		BudgetUtils budgetUtils = new BudgetUtils();
		Response catDataAndCatTypeBodyResp = budgetUtils.createBudget(httpMethodParameters,
				sessionObj);

		if (catDataAndCatTypeBodyResp.statusCode() == 200)
			bHelper.verifyBudgeatAtDB(newRandomUser, currencyType, "catType", BudgetAmount, false);
		else
			softAssert.fail(
					"There is something issue while getting API response for create budget for array of category Data");
	}

	@SuppressWarnings("static-access")
	@Test(enabled = true, dataProvider = "feeder", priority = 5)
	@Source(createBudget_Test6)
	public void createBudgetTest6(String testCaseId, String testCase, String enabled, String testNo1, String catId,
			String catType, String currType) throws Exception {

		commonUtils.isTCEnabled(enabled, testNo1);
		System.out.println(" EXECUTING TEST CASE   >>>>>>>>>>>>>>   " + testCase + " testCase ID   >>>>>>>>>>>>>>   "
				+ testCaseId);
		categoryId.clear();
		categoryType.clear();
		categoryId.add(Integer.parseInt(catId.split(",")[rand.nextInt(10)]));
		categoryType.add(catType.split(",")[rand.nextInt(3)]);
		String BudgetAmount1 = String.valueOf(rand.nextInt(1000));
		currencyType = currType.split(",")[rand.nextInt(10)];
		String catDataAndCatTypeBodyReqParam = bHelper.CreateCatDataAndCatType(groupId2, categoryType, categoryId,
				BudgetAmount1, currencyType);

		HttpMethodParameters httpMethodParameters1 = HttpMethodParameters.builder().build();
		httpMethodParameters1.setBodyParams(catDataAndCatTypeBodyReqParam);
		BudgetUtils budgetUtils1 = new BudgetUtils();
		Response catDataAndCatTypeBodyResp1 = budgetUtils1.createBudget(httpMethodParameters1,
				sessionObj);

		if (catDataAndCatTypeBodyResp1.statusCode() == 200)
			bHelper.verifyBudgeatAtDB(newRandomUser, currencyType, "catType", BudgetAmount1, false);
		else
			softAssert.fail(
					"There is something issue while getting API response for create budget at category data and category Type");
	}

	@SuppressWarnings("static-access")
	@Test(enabled = true, dataProvider = "feeder", priority = 5)
	@Source(createBudget_Test8)
	public void createBudgetTest8(String testCaseId, String testCase, String enabled, String testNo1, String catId,
			String catType, String currType) throws Exception {

		commonUtils.isTCEnabled(enabled, testNo1);
		System.out.println(" EXECUTING TEST CASE   >>>>>>>>>>>>>>   " + testCase + " testCase ID   >>>>>>>>>>>>>>   "
				+ testCaseId);
		categoryId.clear();
		categoryType.clear();
		categoryId.add(Integer.parseInt(catId.split(",")[rand.nextInt(10)]));
		categoryType.add(catType.split(",")[rand.nextInt(3)]);
		String BudgetAmount1 = String.valueOf(rand.nextInt(1000));
		currencyType = currType.split(",")[rand.nextInt(10)];
		String catDataAndCatTypeBodyReqParam = bHelper.CreateCatDataAndCatType(groupId2, categoryType, categoryId,
				BudgetAmount1, currencyType);

		HttpMethodParameters httpMethodParameters1 = HttpMethodParameters.builder().build();
		httpMethodParameters1.setBodyParams(catDataAndCatTypeBodyReqParam);
		BudgetUtils budgetUtils1 = new BudgetUtils();
		Response catDataAndCatTypeBodyResp1 = budgetUtils1.createBudget(httpMethodParameters1,
				sessionObj);

		if (catDataAndCatTypeBodyResp1.statusCode() == 200)
			bHelper.verifyBudgeatAtDB(newRandomUser, currencyType, "catType", BudgetAmount1, false);
		else
			softAssert.fail(
					"There is something issue while getting API response for create budget at category data and category Type");
	}

	@Test(enabled = true, dataProvider = "feeder", priority = 1)
	@Source(createBudget_Test5)
	public void createBudgetTest5(String testCaseId, String testCase, String enabled, String testNo1, String catId,
			String catType, String currType) throws Exception {

		commonUtils.isTCEnabled(enabled, testNo1);
		System.out.println(" EXECUTING TEST CASE   >>>>>>>>>>>>>>   " + testCase + " testCase ID   >>>>>>>>>>>>>>   "
				+ testCaseId);
		categoryId.clear();
		categoryType.clear();
		arrayOfCatId.add(catId.split(",")[rand.nextInt(10)]);
		arrayOfCatType.add(catType.split(",")[rand.nextInt(3)]);

		for (int i = 0; i < arrayOfCatId.size(); i++) {
			arrayOfCatIdInt.add(Integer.parseInt(arrayOfCatId.get(i)));
		}

		String BudgetAmount = String.valueOf(rand.nextInt(1000));
		currencyType = currType.split(",")[rand.nextInt(10)];
		String InvalidAccGrpIdReq = bHelper.CreateBudgetAtCatData(validations, arrayOfCatIdInt, BudgetAmount,
				currencyType);

		HttpMethodParameters httpMethodParameters = HttpMethodParameters.builder().build();
		httpMethodParameters.setBodyParams(InvalidAccGrpIdReq);
		BudgetUtils budgetUtils = new BudgetUtils();
		Response catDataAndCatTypeBodyResp = budgetUtils.createBudget(httpMethodParameters,
				sessionObj);
		softAssert.assertEquals(catDataAndCatTypeBodyResp.jsonPath().getString("errorMessage"),
				"Invalid value for Account Group ID", "Invalid value for Account Group ID");
		softAssert.assertAll();
	}
}
