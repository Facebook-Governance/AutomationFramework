/**
* Copyright (c) 2018 Yodlee Inc. All Rights Reserved.
*
* This software is the confidential and proprietary information of
* Yodlee, Inc. Use is subject to license terms.
*
* @author Raghavendra Yadav G
*/

package com.yodlee.yodleeApi.Budget;

import com.yodlee.yodleeApi.helper.AccountsHelper;
import com.yodlee.yodleeApi.helper.BudgetHelper;
import com.yodlee.yodleeApi.helper.GroupsHelper;
import com.yodlee.yodleeApi.helper.ProvidersHelper;
import com.yodlee.yodleeApi.helper.UserHelper;

import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import com.yodlee.yodleeApi.common.Base;
import com.yodlee.yodleeApi.common.Configuration;
import com.yodlee.yodleeApi.common.LoginFormFactory;
import com.yodlee.yodleeApi.constants.Constants;
import com.yodlee.yodleeApi.constants.JSONPaths;
import com.yodlee.yodleeApi.constants.JsonPath;
import com.yodlee.yodleeApi.constants.URLConstants;
import com.yodlee.yodleeApi.pojo.EnvSession;
import com.yodlee.yodleeApi.pojo.HttpMethodParameters;
import com.yodlee.yodleeApi.pojo.User;
import com.yodlee.yodleeApi.utils.apiUtils.AccountUtils;
import com.yodlee.yodleeApi.utils.apiUtils.BudgetUtils;
import com.yodlee.yodleeApi.utils.apiUtils.GroupUtils;
import com.yodlee.yodleeApi.utils.apiUtils.ProviderAccountUtils;
import com.yodlee.yodleeApi.utils.commonUtils.CommonUtils;

import org.apache.commons.lang.RandomStringUtils;
import org.databene.benerator.anno.Source;
import org.jose4j.lang.JoseException;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import io.restassured.response.Response;

public class getBudgetYSL extends Base {

	// Initializing the class objects
	Response getYslResp = null;
	CommonUtils commonUtils = new CommonUtils();
	HttpMethodParameters groupJson1 = null;
	BudgetHelper bHelper = new BudgetHelper();
	GroupUtils groupUtils = new GroupUtils();
	BudgetUtils budgetUtils = new BudgetUtils();
	SoftAssert softassert = new SoftAssert();
	ProviderAccountUtils providerAccountUtils = new ProviderAccountUtils();
	AccountUtils accountUtils = new AccountUtils();
	AccountsHelper accountsHelper = new AccountsHelper();
	// Initializing the string variables
	String currencyType = null;
	String groupId1 = null;
	String groupName = null;
	String BudgetAmount1 = null;
	String json;
	String jsonadd;
	GroupsHelper groupsHelper = new GroupsHelper();
	Random rand = new Random();
	String userName = "BudegtUser" + System.currentTimeMillis();
	public ArrayList<Long> accountIdList;
	// Initializing the list variables
	ArrayList<Integer> categoryId = new ArrayList<Integer>();
	ArrayList<String> categoryType = new ArrayList<String>();
	public static Long providerAccountId = null;
	EnvSession sessionObj = null;
	UserHelper userHelper = new UserHelper();
	Configuration config = Configuration.getInstance();

	LoginFormFactory loginFormFactory = new LoginFormFactory();
	ProvidersHelper providersHelper = new ProvidersHelper();
	// Initializing data provider paths
	final static String createBudget = "\\TestData\\CSVFiles\\Budget\\createBudget_Test1.csv";
	final static String readYsl1 = "\\TestData\\CSVFiles\\Budget\\readYSLAPI_1.csv";
	final static String readYsl2 = "\\TestData\\CSVFiles\\Budget\\readYSLAPI_2.csv";
	final static String readYsl3 = "\\TestData\\CSVFiles\\Budget\\readYSLAPI_3.csv";
	final static String readYsl4 = "\\TestData\\CSVFiles\\Budget\\readYSLAPI_4.csv";
	final static String readYsl5 = "\\TestData\\CSVFiles\\Budget\\readYSLAPI_5.csv";

	
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
	
	/**
	 * Before class that adds an account.
	 */
	@Test(enabled = true, dataProvider = "feeder", priority = 1)
	@Source(createBudget)
	public void initiateCobAndUserSession(String testCaseId, String testCase, String enabled, String testNo1,
			String catId, String catType, String currType) throws Exception, IOException, JoseException {

		HttpMethodParameters httpParams = HttpMethodParameters.builder().build();
		Map<String, Object> queryParams = accountsHelper.createQueryParamsForGetAccounts(null,
				accountsHelper.getFilterValue("bank"), String.valueOf(providerAccountId), null, null, null, null);
		httpParams.setQueryParams(queryParams);

		Response getAccountsResponse = accountUtils.getAccounts(httpParams, sessionObj);
		JSONObject bankObject = new JSONObject(getAccountsResponse.asString());
		JSONArray accountArray = bankObject.getJSONArray(JSONPaths.ACCOUNT);
		for (int count = 0; count < accountArray.length(); count++) {
			JSONObject accountObject = accountArray.getJSONObject(count);
			Long itemAccountId = accountObject.getLong(Constants.ID);
			String accountID = String.valueOf(itemAccountId);
			jsonadd = groupsHelper.addAccountGroupJson(itemAccountId);
		}
		
		groupName = RandomStringUtils.randomAlphabetic(10);
		groupJson1 = bHelper.createGroupJson(groupName);

		Response GroupResponse = groupUtils.createGroup(groupJson1, config.getInstance().getCobrandSessionObj());
		groupId1 = GroupResponse.jsonPath().getString("group.id");
		String path = "" + groupId1 + Constants.PATH_SEPARATOR + URLConstants.ACCOUNT_ADD;

		HttpMethodParameters httpMethodParameters = groupsHelper.createPathParam("groupId1", path);
		httpMethodParameters.setBodyParams(jsonadd);

		Response resUpdateGrpIn = groupUtils.updateGroup(httpMethodParameters, sessionObj);

		categoryId.add(Integer.parseInt(catId.split(",")[rand.nextInt(10)]));
		categoryType.add(catType.split(",")[rand.nextInt(3)]);
		BudgetAmount1 = String.valueOf(rand.nextInt(1000));
		currencyType = currType.split(",")[rand.nextInt(10)];
		String catDataAndCatTypeBodyReqParam = bHelper.CreateCatDataAndCatType(groupId1, categoryType, categoryId,
				BudgetAmount1, currencyType);

		HttpMethodParameters httpMethodParameters1 = HttpMethodParameters.builder().build();
		httpMethodParameters1.setBodyParams(catDataAndCatTypeBodyReqParam);
		BudgetUtils budgetUtils1 = new BudgetUtils();
		budgetUtils1.createBudget(httpMethodParameters1, sessionObj);
	}

	@Test(enabled = true, dataProvider = "feeder", priority = 2)
	@Source("\\TestData\\CSVFiles\\Budget\\readYSLAPI_5.csv")
	public void getBudgetGroupDetails(String testcase, String testCaseId, String enabled, String url, int TestCaseNo)
			throws Exception {

		System.out.println(" EXECUTING TEST CASE............" + testcase + "TESTCASE ID.........." + testCaseId);
		commonUtils.isTCEnabled(enabled, testCaseId);
		HashMap<String, Object> params = new HashMap<>();
		params.put("groupId", groupId1);
		HttpMethodParameters parameters = HttpMethodParameters.builder().queryParams(params).build();

		getYslResp = budgetUtils.getBudget(parameters, sessionObj);

		softassert.assertTrue(
				getYslResp.jsonPath().getString(JsonPath.groupName).equals(groupName)
						&& getYslResp.jsonPath().getString(JsonPath.budgetGrpId).equals(groupId1),
				"Input parameters empty and returns budget group name and id without passing any query parameters");
	}

	@Test(enabled = true, dataProvider = "feeder", priority = 3)
	@Source(readYsl1)
	public void getBudgetCatData(String testcaseId, String testCase, String enabled, String url, int TestCaseNo)
			throws Exception {

		System.out.println(" EXECUTING TEST CASE............" + testCase + "TESTCASE ID.........." + testcaseId);
		commonUtils.isTCEnabled(enabled, testcaseId);
		HashMap<String, Object> params = new HashMap<>();
		if (testCase.contains("category data")) {
			params.put("include", "categoryData");
			HttpMethodParameters parameters = HttpMethodParameters.builder().queryParams(params).build();

			getYslResp = budgetUtils.getBudget(parameters, sessionObj);
			softassert.assertTrue(
					getYslResp.jsonPath().getString(JsonPath.budgetCatId).equals(categoryId)
							&& !(getYslResp.jsonPath().getString(JsonPath.budgetCatType).equals("null")),
					"Input params category data and returns category Data without summary entity");
			softassert.assertTrue(
					getYslResp.jsonPath().getString(JsonPath.budgetCatId).equals(categoryId)
							&& getYslResp.jsonPath().getString(JsonPath.budgetCatType).equals(categoryType)
							&& getYslResp.jsonPath().getString(JsonPath.budgetAmount).equals(BudgetAmount1),
					"Input params category data and returns category Data without summary entity");
		} else {

			params.put("include", "categoryTypeData");
			HttpMethodParameters parameters = HttpMethodParameters.builder().queryParams(params).build();

			getYslResp = budgetUtils.getBudget(parameters, sessionObj);

			softassert.assertTrue(
					getYslResp.jsonPath().getString(JsonPath.budgetCatType).equals(categoryType)
							&& getYslResp.jsonPath().getString(JsonPath.budgetAmount).equals(BudgetAmount1),
					"Input params category Type data and returns category Type without summary entity");
		}
	}

	@Test(enabled = true, dataProvider = "feeder", priority = 4)
	@Source(readYsl2)
	public void getBudgetCatDataAndType(String testCaseId, String testcase, String enabled, String url, int TestCaseNo)
			throws Exception {

		System.out.println(" EXECUTING TEST CASE............" + testcase + "TESTCASE ID.........." + testCaseId);
		commonUtils.isTCEnabled(enabled, testCaseId);
		HashMap<String, Object> params = new HashMap<>();
		if (testcase.contains("summary")) {
			params.put("include", "categoryData,categoryTypeData");
			HttpMethodParameters parameters = HttpMethodParameters.builder().queryParams(params).build();

			getYslResp = budgetUtils.getBudget(parameters, sessionObj);

			softassert.assertTrue(
					getYslResp.jsonPath().getString(JsonPath.budgetCategoryDataCatId).equals(categoryId)
							&& !(getYslResp.jsonPath().getString(JsonPath.budgetCategoryDataCatName).equals("null")),
					"Input params category data and category Type data and returns without summary entity");
			softassert.assertTrue(
					getYslResp.jsonPath().getString(JsonPath.budgetCategoryDataCatId).equals(categoryId)
							&& getYslResp.jsonPath().getString(JsonPath.budgetCategoryDataCatType).equals(categoryType)
							&& getYslResp.jsonPath().getString(JsonPath.budgetCategoryDataBudAmt).equals(BudgetAmount1),
					"Input params category data and category Type data and returns without summary entity");
			softassert.assertTrue(
					getYslResp.jsonPath().getString(JsonPath.budgetCategoryDataCatType).equals(categoryType)
							&& getYslResp.jsonPath().getString(JsonPath.budgetCategoryDataBudAmt).equals(BudgetAmount1),
					"Input params category data and category Type data and returns without summary entity");
		} else {
			if(testcase.contains("without summary entity")){
				params.put("include", "categoryData,categoryTypeData");
				HttpMethodParameters parameters = HttpMethodParameters.builder().queryParams(params).build();
				getYslResp = budgetUtils.getBudget(parameters, sessionObj);
				softassert.assertTrue(
						getYslResp.jsonPath().getString(JsonPath.budgetCatType).equals(categoryType)
						&& getYslResp.jsonPath().getString("budget.budgetAmount").equals(BudgetAmount1),
						"Input params include categoryData and categoryTypeData and categoryType and categoryId filters");
			}else if(testcase.contains("Input params include")){
				params.put("include", "categoryData,categoryTypeData");
				params.put("categoryType", "INCOME");
				params.put("categoryId", "13");
				HttpMethodParameters parameters = HttpMethodParameters.builder().queryParams(params).build();
				getYslResp = budgetUtils.getBudget(parameters, sessionObj);
				softassert.assertTrue(
						getYslResp.jsonPath().getString(JsonPath.budgetCatType).equals(categoryType)
						&& getYslResp.jsonPath().getString("budget.budgetAmount").equals(BudgetAmount1),
						"Input params include categoryData and categoryTypeData and categoryType and categoryId filters");
			}
			else{
				params.put("include", "categoryData,categoryTypeData");
				params.put("categoryType", "INCOME");
				params.put("categoryId", "3");
				HttpMethodParameters parameters = HttpMethodParameters.builder().queryParams(params).build();
				getYslResp = budgetUtils.getBudget(parameters, sessionObj);
				softassert.assertTrue(
						getYslResp.jsonPath().getString(JsonPath.budgetCatType).equals(categoryType)
						&& getYslResp.jsonPath().getString("budget.budgetAmount").equals(BudgetAmount1),
						"Input params include categoryData and categoryTypeData and categoryType and categoryId filters");
			}
		}
	}

	@Test(enabled = true, dataProvider = "feeder", priority = 5)
	@Source(readYsl3)
	public void getBudgetCatDataAndTypeGrpId(String testCaseId, String testcase, String enabled, String url,
			int TestCaseNo) throws Exception {

		System.out.println(" EXECUTING TEST CASE............" + testcase + "TESTCASE ID.........." + testCaseId);
		commonUtils.isTCEnabled(enabled, testCaseId);
		HashMap<String, Object> params = new HashMap<>();
		if (testcase.contains("categoryData")) {
			params.put("groupId", groupId1);
			params.put("include", "categoryData");
			HttpMethodParameters parameters = HttpMethodParameters.builder().queryParams(params).build();

			getYslResp = budgetUtils.getBudget(parameters, sessionObj);

			softassert.assertTrue(
					getYslResp.jsonPath().getString(JsonPath.budgetCategoryDataCatId).equals(categoryId)
							&& !(getYslResp.jsonPath().getString(JsonPath.budgetCategoryDataCatName).equals("null")),
					"Input params category data and category Type data and returns without summary entity");
			softassert.assertTrue(
					getYslResp.jsonPath().getString(JsonPath.budgetCategoryDataCatName).equals(categoryId)
							&& getYslResp.jsonPath().getString(JsonPath.budgetCategoryDataCatType).equals(categoryType)
							&& getYslResp.jsonPath().getString(JsonPath.budgetCategoryDataBudAmt).equals(BudgetAmount1),
					"Input params category data and category Type data and returns without summary entity");
			softassert.assertTrue(
					getYslResp.jsonPath().getString(JsonPath.budgetCategoryDataCatType).equals(categoryType)
							&& getYslResp.jsonPath().getString(JsonPath.budgetCategoryDataBudAmt).equals(BudgetAmount1),
					"Input params category data and category Type data and returns without summary entity");
		} else {
			params.put("groupId", groupId1);
			params.put("include", "categoryTypeData");
			HttpMethodParameters parameters = HttpMethodParameters.builder().queryParams(params).build();

			getYslResp = budgetUtils.getBudget(parameters, sessionObj);

			softassert.assertTrue(
					getYslResp.jsonPath().getString(JsonPath.budgetCategoryDataCatType).equals(categoryType)
							&& getYslResp.jsonPath().getString(JsonPath.budgetCategoryDataBudAmt).equals(BudgetAmount1),
					"Input params include categoryData and categoryTypeData and categoryType and categoryId filters");

		}

	}

	@Test(enabled = true, dataProvider = "feeder", priority = 6)
	@Source(readYsl4)
	public void getBudgetNegValidations(String testCaseId, String testcase, String enabled, String url, int TestCaseNo)
			throws Exception {

		System.out.println(" EXECUTING TEST CASE............" + testcase + "TESTCASE ID.........." + testCaseId);
		commonUtils.isTCEnabled(enabled, testCaseId);
		HashMap<String, Object> params = new HashMap<>();
		if (testcase.contains("group Id")) {
			params.put("groupId", "1234");
			HttpMethodParameters parameters = HttpMethodParameters.builder().queryParams(params).build();

			getYslResp = budgetUtils.getBudget(parameters, sessionObj);

			softassert.assertTrue(
					getYslResp.jsonPath().getString("errorMessage").equals("Invalid value for accountGroupId"),
					"Verify the API response by passing group Id as null or invalid in URL query parameters");
		} else if (testcase.contains("CategoryType")) {
			params.put("groupId", groupId1);
			params.put("include", "categoryTypeData");
			params.put("categoryType", "null");
			HttpMethodParameters parameters = HttpMethodParameters.builder().queryParams(params).build();

			getYslResp = budgetUtils.getBudget(parameters, sessionObj);

			softassert.assertTrue(
					getYslResp.jsonPath().getString("errorMessage").equals("Invalid value for categoryType"),
					"Verify the API response by passing  CategoryType as null or invalid in URL query parameters");
		} else {

			params.put("groupId", groupId1);
			params.put("include", "categoryData");
			params.put("categoryId", "234");
			HttpMethodParameters parameters = HttpMethodParameters.builder().queryParams(params).build();

			getYslResp = budgetUtils.getBudget(parameters, sessionObj);

			softassert.assertTrue(
					getYslResp.jsonPath().getString("errorMessage").equals("Invalid value for categoryData"),
					"Verify the API response by passing  CategoryType as null or invalid in URL query parameters");
		}

	}
}
