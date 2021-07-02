/**
* Copyright (c) 2018 Yodlee Inc. All Rights Reserved.
*
* This software is the confidential and proprietary information of
* Yodlee, Inc. Use is subject to license terms.
*
* @author Raghavendra Yadav G
*/

package com.yodlee.yodleeApi.Budget;


import com.yodlee.yodleeApi.helper.BudgetHelper;

import com.yodlee.yodleeApi.helper.SaveForGoalHelper;
import com.yodlee.yodleeApi.helper.UserHelper;

import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import java.util.Random;

import com.yodlee.yodleeApi.common.Base;
import com.yodlee.yodleeApi.common.Configuration;

import com.yodlee.yodleeApi.constants.JSONPaths;


import com.yodlee.yodleeApi.pojo.EnvSession;
import com.yodlee.yodleeApi.pojo.HttpMethodParameters;
import com.yodlee.yodleeApi.pojo.User;

import com.yodlee.yodleeApi.utils.apiUtils.BudgetUtils;
import com.yodlee.yodleeApi.utils.apiUtils.GroupUtils;
import com.yodlee.yodleeApi.utils.apiUtils.ProviderAccountUtils;
import com.yodlee.yodleeApi.utils.apiUtils.SFGUtils;
import com.yodlee.yodleeApi.utils.commonUtils.CommonUtils;

import org.apache.commons.lang.RandomStringUtils;
import org.databene.benerator.anno.Source;
import org.jose4j.lang.JoseException;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import io.restassured.response.Response;


public class getBudgetGoalYSL extends Base {


	// Variable initialization

	public static Long providerAccountId = null;
	

	// ArrayList initialization
	private ArrayList<Integer> categoryId = new ArrayList<Integer>();
	 private ArrayList<String> categoryType = new ArrayList<String>();
	 private Configuration config = Configuration.getInstance();

	// Hash Map deceleration

	// Soft assertion object initialization
	
	 private BudgetUtils budgetUtils = new BudgetUtils();
	 private BudgetHelper bHelper = new BudgetHelper();

	 private HttpMethodParameters groupJson1 = null;
	 private HttpMethodParameters groupJson2 = null;
	 private HttpMethodParameters groupJson3 = null;
	 private HttpMethodParameters groupJson4 = null;
	 private String groupId1 = null;
	 private String groupId2 = null;
	 private String groupId3 = null;
	 private String groupId4 = null;
	 private GroupUtils groupUtils = new GroupUtils();
	 private String currencyType = null;
	 private String currencyType2 = null;
	 private String currencyType3 = null;
	 private String currencyType4 = null;
	 private String groupName = null;
	 private String groupName2 = null;
	 private String groupName3 = null;
	 private String groupName4 = null;
	 private String arrayOfGoalData[]=null;
	 private Random rand = new Random();
	 private String userName = "BudegtUser" + System.currentTimeMillis();
	 private EnvSession sessionObj = null;
	 private UserHelper userHelper = new UserHelper();
	 private ProviderAccountUtils providerAccountUtils = new ProviderAccountUtils();
	 private static List<Integer> itemAccountIds = new LinkedList<Integer>();
	 private ArrayList<String> listBudgetAmont = new ArrayList<String>();
	 private ArrayList<String> arrayOfCatType = new ArrayList<String>();
	 private ArrayList<String> goalNameList = new ArrayList<String>();
	
	 private List<String> arrayOfCatType2=new ArrayList<String>();
	 private List<Integer> categoryId2 = new ArrayList<Integer>();
	 private SaveForGoalHelper sfg = new SaveForGoalHelper();
	 private List<String> arrayOfCatType3=new ArrayList<String>();
	 private List<Integer> categoryId3 = new ArrayList<Integer>();
	 private List<String> arrayOfCatType4=new ArrayList<String>();
	 private List<Integer> categoryId4 = new ArrayList<Integer>();
	 private static String getGoalId;
	 private ArrayList<String> listGoalId = new ArrayList<String>();
	 private String BudgetAmount1=null;
	 private String BudgetAmount2=null;
	 private List<String> BudgetAmount3 = new ArrayList<String>();
	 private List<String> BudgetAmount4 = new ArrayList<String>();
	 private CommonUtils commonUtils = new CommonUtils();
	 private Response getYslResp = null;
	 SFGUtils sfgUtils = new SFGUtils();
	// Initialization of .CSV file location
	final static String createBudget = "\\TestData\\CSVFiles\\Budget\\CreateBudgetGoal_Test.csv";
	final static String readYsl1 = "\\TestData\\CSVFiles\\Budget\\readYSLAPIGoal_1.csv";
	final static String readYsl2 = "\\TestData\\CSVFiles\\Budget\\readYSLAPIGoal_2.csv";
	final static String readYsl3 = "\\TestData\\CSVFiles\\Budget\\readYSLAPIGoal_3.csv";
	final static String readYsl4 = "\\TestData\\CSVFiles\\Budget\\readYSLAPIGoal_4.csv";
	

	
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
				"renuka21.site16441.2", "site16441.2", sessionObj);
		providerAccountId = response.jsonPath().getLong(JSONPaths.PROVIDER_ACC_ID);
		Assert.assertTrue(providerAccountId!=null);

	}
	
	@Test(enabled = true, dataProvider = "feeder",priority=1)
	@Source(createBudget)
	public void initiateCobAndUserSession(String testCaseId, String testCase, String enabled, String testNo1,
			String catId, String catType, String currType,String Goal,String GoalStartDate
		    ,String GoalEndDate) throws Exception, IOException, JoseException {
		groupName = RandomStringUtils.randomAlphabetic(10);
		groupJson1 = bHelper.createGroupJson(groupName);

		Response GroupResponse = groupUtils.createGroup(groupJson1, sessionObj);
		groupId1 = GroupResponse.jsonPath().getString("group.id");

		categoryId.add(Integer.parseInt(catId.split(",")[rand.nextInt(10)]));
		categoryType.add(catType.split(",")[rand.nextInt(3)]);
		BudgetAmount1 = String.valueOf(rand.nextInt(1000));
		currencyType = "USD";
		listBudgetAmont.add(BudgetAmount1);
		listBudgetAmont.add(BudgetAmount1);
		arrayOfCatType.add(catType.split(",")[0]);
		arrayOfCatType.add(catType.split(",")[1]);
		String catDataAndCatTypeBodyReqParam = bHelper.CreateCatDataAndCatType(groupId1, arrayOfCatType, categoryId,
				BudgetAmount1, currencyType);

		HttpMethodParameters httpMethodParameters1 = HttpMethodParameters.builder().build();
		httpMethodParameters1.setBodyParams(catDataAndCatTypeBodyReqParam);
		BudgetUtils budgetUtils1 = new BudgetUtils();
		Response resp1=budgetUtils1.createBudget(httpMethodParameters1, sessionObj);
		Assert.assertTrue(resp1.getStatusCode()==201);
	
		
		
		String bodyParams = null;
		 arrayOfGoalData=Goal.split(",");
		
		System.out.println(arrayOfGoalData);
		for (int i = 0; i <4; i++) {
			String golaName=RandomStringUtils.randomAlphabetic(10);
			goalNameList.add(golaName);
		bodyParams = sfg.createGoalJson(golaName ,arrayOfGoalData[1], sfg.sfgDateFormate(GoalStartDate),sfg.sfgDateFormate(GoalEndDate) , arrayOfGoalData[2],
				arrayOfGoalData[3], arrayOfGoalData[4], arrayOfGoalData[5], arrayOfGoalData[6], arrayOfGoalData[7], arrayOfGoalData[8],
				arrayOfGoalData[9],"");
		
		System.out.println(bodyParams);

		HttpMethodParameters httpMethodParameters2 = HttpMethodParameters.builder().build();
		httpMethodParameters2.setBodyParams(bodyParams);
		Response response = sfgUtils.createGoal(httpMethodParameters2, sessionObj);
		String goalId=response.jsonPath().getString("goal[0].id");
		listGoalId.add(goalId);
		System.out.println(goalId);
		itemAccountIds = sfg.getItemAccountIds(userName);
		String createDestAccountBodyParam1=sfg.createDestinationAccount(itemAccountIds.get(0),1000,"USD");
		sfg.createDestAccount(goalId, createDestAccountBodyParam1, sessionObj);
		String createFundingRuleBodyParam;
		HttpMethodParameters httpmethodParam = HttpMethodParameters.builder().build();
		HashMap<String, Object> createFundingRulePathParam = new HashMap<>();

		createFundingRulePathParam.put("goalId", goalId.isEmpty() ? getGoalId :goalId);
		
		createFundingRuleBodyParam = sfg.createFundingRule(itemAccountIds.get(0), arrayOfGoalData[4],
				arrayOfGoalData[5], arrayOfGoalData[6], arrayOfGoalData[11], sfg.sfgDateFormate(GoalStartDate),
				sfg.sfgDateFormate(GoalEndDate));

		httpmethodParam.setPathParams(createFundingRulePathParam);
		httpmethodParam.setBodyParams(createFundingRuleBodyParam);

		Response createFundingRuleResp = sfgUtils.createFundingRule(httpmethodParam, sessionObj);
		Assert.assertTrue(createFundingRuleResp.getStatusCode()==201);

		}
		groupName2 = RandomStringUtils.randomAlphabetic(9);
		groupJson2 = bHelper.createGroupJson(groupName2);

		Response GroupResponse2 = groupUtils.createGroup(groupJson2, sessionObj);
		groupId2 = GroupResponse2.jsonPath().getString("group.id");
		arrayOfCatType2.add(catType.split(",")[0]);
		arrayOfCatType2.add(catType.split(",")[1]);
		categoryId2.add(Integer.parseInt(catId.split(",")[rand.nextInt(10)]));
		BudgetAmount2=String.valueOf(rand.nextInt(1000));
		currencyType2 = "USD";
		List<String> goalid2=new ArrayList<String>();
		goalid2.add(listGoalId.get(1));
		String catDataAndCatTypeBodyReqParam2 = bHelper.CreateCatDataAndCatTypeGoal(groupId2, arrayOfCatType2, categoryId2,
				BudgetAmount2, currencyType2,goalid2);
		HttpMethodParameters httpMethodParameters2 = HttpMethodParameters.builder().build();
		httpMethodParameters2.setBodyParams(catDataAndCatTypeBodyReqParam2);
		BudgetUtils budgetUtils2 = new BudgetUtils();
		Response resp2=budgetUtils2.createBudget(httpMethodParameters2, sessionObj);
		Assert.assertTrue(resp2.getStatusCode()==201);
		groupName3 = RandomStringUtils.randomAlphabetic(8);
		groupJson3 = bHelper.createGroupJson(groupName3);

		Response GroupResponse3 = groupUtils.createGroup(groupJson3, sessionObj);
		groupId3 = GroupResponse3.jsonPath().getString("group.id");
		arrayOfCatType3.add(catType.split(",")[0]);
		arrayOfCatType3.add(catType.split(",")[1]);
		categoryId3.add(Integer.parseInt(catId.split(",")[rand.nextInt(10)]));
		BudgetAmount3.add(String.valueOf(rand.nextInt(1000)));
		currencyType3 ="USD";
		List<String> goalid3=new ArrayList<String>();
		goalid3.add(listGoalId.get(2));
		String catDataAndCatTypeBodyReqParam3 = bHelper.CreateBudgetAtCatDataGoal(groupId3, categoryId3, BudgetAmount3, currencyType,goalid3);
		HttpMethodParameters httpMethodParameters3 = HttpMethodParameters.builder().build();
		httpMethodParameters3.setBodyParams(catDataAndCatTypeBodyReqParam3);
		BudgetUtils budgetUtils3 = new BudgetUtils();
		Response budgetCreate3=budgetUtils3.createBudget(httpMethodParameters3, sessionObj);
		Assert.assertTrue(budgetCreate3.getStatusCode()==201);
		groupName4 = RandomStringUtils.randomAlphabetic(7);
		groupJson4 = bHelper.createGroupJson(groupName4);

		Response GroupResponse4 = groupUtils.createGroup(groupJson4, sessionObj);
		groupId4 = GroupResponse4.jsonPath().getString("group.id");
		arrayOfCatType4.add(catType.split(",")[0]);
		arrayOfCatType4.add(catType.split(",")[1]);
		categoryId4.add(Integer.parseInt(catId.split(",")[rand.nextInt(10)]));
		BudgetAmount4.add(String.valueOf(rand.nextInt(1000)));
		BudgetAmount4.add(String.valueOf(rand.nextInt(1000)));
		currencyType4 = "USD";
		List<String> goalid4=new ArrayList<String>();
		goalid4.add(listGoalId.get(3));
		String catDataAndCatTypeBodyReqParam4 = bHelper.CreateBudgetAtCatTypeGoal(groupId4, arrayOfCatType4, BudgetAmount4, currencyType4,goalid4.get(0));
		HttpMethodParameters httpMethodParameters4 = HttpMethodParameters.builder().build();
		httpMethodParameters4.setBodyParams(catDataAndCatTypeBodyReqParam4);
		BudgetUtils budgetUtils4 = new BudgetUtils();
		Response budgetCreate4=budgetUtils4.createBudget(httpMethodParameters4, sessionObj);
		Assert.assertTrue(budgetCreate4.getStatusCode()==201);
	}
	@Test(enabled = true, dataProvider = "feeder", priority = 2,dependsOnMethods="initiateCobAndUserSession")
	@Source(readYsl1)
	public void getBudgetGroupDetails(String testCaseId, String testcase, String CategoryId, String CategoryIdAmount, 
			String CateogryType,String CateogryTypeAmount,String CurrencyType,String Goal,String GoalStartDate,String GoalEndDate,
			String httpStatus,String ResFile,String FilePath,String GoalId,String groupId,String ErrorCode,String ExpectedErrorMsg,String Enabled)
			throws Exception {
//test case 43
		System.out.println(" EXECUTING TEST CASE............" + testcase + "TESTCASE ID.........." + testCaseId);
		commonUtils.isTCEnabled(Enabled, testCaseId);
		HashMap<String, Object> params = new HashMap<>();
		List<String> gpid=new ArrayList<String>();
		List<String> gpname=new ArrayList<String>();
		String groupids[]=null;
		if (!groupId.isEmpty()) {
			 groupids=groupId.split(",");
			if (groupids.length==2) {
				gpid.add(groupId1);
				gpid.add(groupId2);
				gpname.add(groupName);
				gpname.add(groupName2);
				params.put("groupId", groupId1+","+groupId2);
			}
			else {
			params.put("groupId", groupId);
			}
		}
		else {
		params.put("groupId", groupId1);
		gpid.add(groupId1);
		gpname.add(groupName);
		}
		HttpMethodParameters parameters = HttpMethodParameters.builder().queryParams(params).build();

		getYslResp = budgetUtils.getBudget(parameters, sessionObj);
		System.out.println(getYslResp.asString());
		if (getYslResp.getStatusCode()!=200) {
			Assert.assertEquals(getYslResp.jsonPath().get("errorCode"),ErrorCode);
			Assert.assertEquals(getYslResp.jsonPath().get("errorMessage"), "Invalid value for accountGroupId");
	
		}
		else {
		
		
			int actualid=getYslResp.jsonPath().get("budget[0].groupId");
			String actualGroupName=getYslResp.jsonPath().get("budget[0].groupName");
			
		Assert.assertTrue(gpid.contains(Integer.toString(actualid)));
		Assert.assertTrue(gpname.contains(actualGroupName));
		if (groupids==null) {
			System.out.println("only one i should displyed");
		}
		else{
			int actualid1=getYslResp.jsonPath().get("budget[1].groupId");
			String actualGroupName1=getYslResp.jsonPath().get("budget[1].groupName");
			
		Assert.assertTrue(gpid.contains(Integer.toString(actualid1)));
		Assert.assertTrue(gpname.contains(actualGroupName1));
		}
		}
}
	@Test(enabled = true, dataProvider = "feeder", priority = 3,dependsOnMethods="initiateCobAndUserSession")
	@Source(readYsl2)
	public void getBudgetGroupWithoutGoalAssociated(String testCaseId, String testcase, String CategoryId, String CategoryIdAmount, 
			String CateogryType,String CateogryTypeAmount,String CurrencyType,String Goal,String GoalStartDate,String GoalEndDate,
			String httpStatus,String ResFile,String FilePath,String GoalId,String groupId,String getCategoryID,String getCategoryType,String getGoal,String ErrorCode,String ExpectedErrorMsg,String Enabled)
			throws Exception {
//test case 46 47
		System.out.println(" EXECUTING TEST CASE............" + testcase + "TESTCASE ID.........." + testCaseId);
		commonUtils.isTCEnabled(Enabled, testCaseId);
		HashMap<String, Object> params = new HashMap<>();
		if (!groupId.isEmpty()) {
			params.put("groupId", groupId);
		}
		else {
		params.put("groupId", groupId1);
		}
		
		
		
		if (!getCategoryID.isEmpty() && !getCategoryType.isEmpty()&& !getGoal.isEmpty()) {
			params.put("include", "categoryTypeData,categoryData,goal");
		}
		else if(!getCategoryType.isEmpty()&& !getGoal.isEmpty()&& getCategoryID.isEmpty())
		{
			params.put("include", "categoryTypeData,goal");
		}
		else if(!getCategoryID.isEmpty()&& !getGoal.isEmpty()&& getCategoryType.isEmpty()) {
			params.put("include", "categoryData,goal");
		}
		
		
		HttpMethodParameters parameters = HttpMethodParameters.builder().queryParams(params).build();
    	getYslResp = budgetUtils.getBudget(parameters, sessionObj);
		if (!getCategoryID.isEmpty() && !getCategoryType.isEmpty()&& !getGoal.isEmpty()) {
			int actualid=getYslResp.jsonPath().get("budget[0].groupId");
			String actualGroupName=getYslResp.jsonPath().get("budget[0].groupName");
			String actualGroupCatrgoryType1=getYslResp.jsonPath().get("budget[0].categoryTypeData[0].categoryType");
			float actualGroupCatrgoryTypeAmount1=getYslResp.jsonPath().get("budget[0].categoryTypeData[0].budgetAmount.amount");
			String actualGroupCatrgoryType2=getYslResp.jsonPath().get("budget[0].categoryTypeData[1].categoryType");
			float actualGroupCatrgoryTypeAmount2=getYslResp.jsonPath().get("budget[0].categoryTypeData[1].budgetAmount.amount");
		
			Assert.assertEquals(actualid, Integer.parseInt(groupId1));
			Assert.assertEquals(actualGroupName, groupName);
			Assert.assertEquals(actualGroupCatrgoryType1, arrayOfCatType.get(0));
			Assert.assertEquals(actualGroupCatrgoryTypeAmount1,Float.parseFloat(BudgetAmount1));
			Assert.assertEquals(actualGroupCatrgoryType2, arrayOfCatType.get(1));
			Assert.assertEquals(actualGroupCatrgoryTypeAmount2,Float.parseFloat(BudgetAmount1));
			Assert.assertFalse((getYslResp.asString()).contains("goal"));
		}
		else if(!getCategoryType.isEmpty()&& !getGoal.isEmpty())
		{
			int actualid=getYslResp.jsonPath().get("budget[0].groupId");
			String actualGroupName=getYslResp.jsonPath().get("budget[0].groupName");
			String actualGroupCatrgoryType1=getYslResp.jsonPath().get("budget[0].categoryTypeData[0].categoryType");
			float actualGroupCatrgoryTypeAmount1=getYslResp.jsonPath().get("budget[0].categoryTypeData[0].budgetAmount.amount");
			String actualGroupCatrgoryType2=getYslResp.jsonPath().get("budget[0].categoryTypeData[1].categoryType");
			float actualGroupCatrgoryTypeAmount2=getYslResp.jsonPath().get("budget[0].categoryTypeData[1].budgetAmount.amount");
		
			Assert.assertEquals(actualid, Integer.parseInt(groupId1));
			Assert.assertEquals(actualGroupName, groupName);
			Assert.assertEquals(actualGroupCatrgoryType1, arrayOfCatType.get(0));
			Assert.assertEquals(actualGroupCatrgoryTypeAmount1,Float.parseFloat(BudgetAmount1));
			Assert.assertEquals(actualGroupCatrgoryType2, arrayOfCatType.get(1));
			Assert.assertEquals(actualGroupCatrgoryTypeAmount2,Float.parseFloat(BudgetAmount1));
			Assert.assertFalse((getYslResp.asString()).contains("goal"));
		}
		else if(!getCategoryID.isEmpty()&& !getGoal.isEmpty()) {
			int actualid=getYslResp.jsonPath().get("budget[0].groupId");
			String actualGroupName=getYslResp.jsonPath().get("budget[0].groupName");
			System.out.println((getYslResp.asString()).contains("goal"));
			int actualGroupCatrgoryType2=getYslResp.jsonPath().get("budget[0].categoryData[0].categoryId");
			float actualGroupCatrgoryTypeAmount2=getYslResp.jsonPath().get("budget[0].categoryData[0].budgetAmount.amount");
		
			Assert.assertEquals(actualid, Integer.parseInt(groupId1));
			Assert.assertEquals(actualGroupName, groupName);
		//	Assert.assertFalse(actualGroupCatrgoryType1, arrayOfCatType.get(0));
			Assert.assertTrue(actualGroupCatrgoryType2==categoryId.get(0));
			Assert.assertEquals(actualGroupCatrgoryTypeAmount2,Float.parseFloat(BudgetAmount1));
			Assert.assertFalse((getYslResp.asString()).contains("goal"));
		}
		
		
		
		
	
	
	}
	
	@Test(enabled = true, dataProvider = "feeder", priority = 4,dependsOnMethods="initiateCobAndUserSession")
	@Source(readYsl3)
	public void getBudgetGroupWithGoalAssociated(String testCaseId, String testcase, String CategoryId, String CategoryIdAmount, 
			String CateogryType,String CateogryTypeAmount,String CurrencyType,String Goal,String GoalStartDate,String GoalEndDate,
			String httpStatus,String ResFile,String FilePath,String GoalId,String groupId,String getCategoryID,String getCategoryType,String getGoal,String ErrorCode,String ExpectedErrorMsg,String Enabled)
			throws Exception {
//test case 50,52
		System.out.println(" EXECUTING TEST CASE............" + testcase + "TESTCASE ID.........." + testCaseId);
		commonUtils.isTCEnabled(Enabled, testCaseId);
		HashMap<String, Object> params = new HashMap<>();
		if (!groupId.isEmpty()) {
			params.put("groupId", groupId);
		}
		else {
		params.put("groupId", groupId2);
		}
		
		
		
		if (!getCategoryID.isEmpty() && !getCategoryType.isEmpty()&& !getGoal.isEmpty()) {
			
			params.put("include", getCategoryType+","+getCategoryID+","+getGoal);
		}
		else if(!getCategoryType.isEmpty()&& !getGoal.isEmpty()&& getCategoryID.isEmpty())
		{
			params.put("include", getCategoryType+","+getGoal);
		}
		else if(!getCategoryID.isEmpty()&& !getGoal.isEmpty()&& getCategoryType.isEmpty()) {
			params.put("include", getCategoryID+","+getGoal);
		}
		else if(!getCategoryID.isEmpty()&& getGoal.isEmpty()&& !getCategoryType.isEmpty()) {
			params.put("include", getCategoryType+","+getCategoryID);
		}
		else if(getCategoryID.isEmpty()&& !getGoal.isEmpty()&& getCategoryType.isEmpty()) {
			params.put("include",getGoal);
		}
		
		
		
		HttpMethodParameters parameters = HttpMethodParameters.builder().queryParams(params).build();
    	getYslResp = budgetUtils.getBudget(parameters, sessionObj);
    	 if (getYslResp.getStatusCode()!=200) {
    		 Assert.assertEquals(getYslResp.jsonPath().get("errorCode"), "Y800");
    		 Assert.assertEquals(getYslResp.jsonPath().get("errorMessage"), "Invalid value for include");
		}
    	 else {
    	if (!getCategoryID.isEmpty() && !getCategoryType.isEmpty()&& !getGoal.isEmpty()) {
    		int actualid=getYslResp.jsonPath().get("budget[0].groupId");
    		String actualGroupName=getYslResp.jsonPath().get("budget[0].groupName");
    		String actualGroupCatrgoryType1=getYslResp.jsonPath().get("budget[0].categoryTypeData[0].categoryType");
    		float actualGroupCatrgoryTypeAmount1=getYslResp.jsonPath().get("budget[0].categoryTypeData[0].budgetAmount.amount");
    		String actualGroupCatrgoryType2=getYslResp.jsonPath().get("budget[0].categoryTypeData[1].categoryType");
    		float actualGroupCatrgoryTypeAmount2=getYslResp.jsonPath().get("budget[0].categoryTypeData[1].budgetAmount.amount");
    		int actualCatId=getYslResp.jsonPath().get("budget[0].categoryData[0].categoryId");
    		float actualCatIdAmt=getYslResp.jsonPath().get("budget[0].categoryData[0].budgetAmount.amount");
    		int actualGoalId=getYslResp.jsonPath().get("budget[0].goal[0].goalId");
    		String actualGoalFreq=getYslResp.jsonPath().get("budget[0].goal[0].frequency");
    		String actualGoalName=getYslResp.jsonPath().get("budget[0].goal[0].goalName");
    		float derivedActualSavingAmt=getYslResp.jsonPath().get("budget[0].goal[0].derivedActualSaving.amount");
    		float derivedExpectedSaving=getYslResp.jsonPath().get("budget[0].goal[0].derivedExpectedSaving.amount");
    		float recurringAmount=getYslResp.jsonPath().get("budget[0].goal[0].recurringAmount.amount");
    		String status=getYslResp.jsonPath().get("budget[0].goal[0].status");
    		boolean isOffTrack=getYslResp.jsonPath().get("budget[0].goal[0].isOffTrack");
            Assert.assertEquals(actualid, Integer.parseInt(groupId2));
    		Assert.assertEquals(actualGroupName, groupName2);
    		Assert.assertEquals(actualGroupCatrgoryType1, arrayOfCatType2.get(0));
    		Assert.assertEquals(actualGroupCatrgoryTypeAmount1,Float.parseFloat(BudgetAmount2));
    		Assert.assertEquals(actualGroupCatrgoryType2, arrayOfCatType2.get(1));
    		Assert.assertEquals(actualGroupCatrgoryTypeAmount2,Float.parseFloat(BudgetAmount2));
    		int expectedId=categoryId2.get(0);
    		System.out.println(expectedId);
    		System.out.println(actualCatId);
    		Assert.assertTrue(actualCatId==expectedId);
    		Assert.assertEquals(actualCatIdAmt,Float.parseFloat(BudgetAmount2));
    		Assert.assertEquals(actualGoalId,Integer.parseInt(listGoalId.get(1)));
    		Assert.assertEquals(actualGoalName,goalNameList.get(1));
    		Assert.assertEquals(actualGoalFreq,arrayOfGoalData[6]);
    		Assert.assertTrue(derivedActualSavingAmt==1000.0);
    		Assert.assertTrue(derivedExpectedSaving==11000.0);
    		Assert.assertTrue(recurringAmount==10000.0);
    		Assert.assertEquals(status,"NOT_STARTED");
    		Assert.assertEquals(isOffTrack,false);
    	}
    	else if(!getCategoryType.isEmpty()&& !getGoal.isEmpty()&& getCategoryID.isEmpty())
		{
    		int actualid=getYslResp.jsonPath().get("budget[0].groupId");
    		String actualGroupName=getYslResp.jsonPath().get("budget[0].groupName");
    		String actualGroupCatrgoryType1=getYslResp.jsonPath().get("budget[0].categoryTypeData[0].categoryType");
    		float actualGroupCatrgoryTypeAmount1=getYslResp.jsonPath().get("budget[0].categoryTypeData[0].budgetAmount.amount");
    		String actualGroupCatrgoryType2=getYslResp.jsonPath().get("budget[0].categoryTypeData[1].categoryType");
    		float actualGroupCatrgoryTypeAmount2=getYslResp.jsonPath().get("budget[0].categoryTypeData[1].budgetAmount.amount");
    		int actualGoalId=getYslResp.jsonPath().get("budget[0].goal[0].goalId");
    		String actualGoalFreq=getYslResp.jsonPath().get("budget[0].goal[0].frequency");
    		String actualGoalName=getYslResp.jsonPath().get("budget[0].goal[0].goalName");
    		float derivedActualSavingAmt=getYslResp.jsonPath().get("budget[0].goal[0].derivedActualSaving.amount");
    		float derivedExpectedSaving=getYslResp.jsonPath().get("budget[0].goal[0].derivedExpectedSaving.amount");
    		float recurringAmount=getYslResp.jsonPath().get("budget[0].goal[0].recurringAmount.amount");
    		String status=getYslResp.jsonPath().get("budget[0].goal[0].status");
    		boolean isOffTrack=getYslResp.jsonPath().get("budget[0].goal[0].isOffTrack");
    		Assert.assertEquals(actualid, Integer.parseInt(groupId2));
    		Assert.assertEquals(actualGroupName, groupName2);
    		Assert.assertEquals(actualGroupCatrgoryType1, arrayOfCatType2.get(0));
    		Assert.assertEquals(actualGroupCatrgoryTypeAmount1,Float.parseFloat(BudgetAmount2));
    		Assert.assertEquals(actualGroupCatrgoryType2, arrayOfCatType2.get(1));
    		Assert.assertEquals(actualGroupCatrgoryTypeAmount2,Float.parseFloat(BudgetAmount2));
    		Assert.assertEquals(actualGoalId,Integer.parseInt(listGoalId.get(1)));
    		Assert.assertEquals(actualGoalName,goalNameList.get(1));
    		Assert.assertEquals(actualGoalFreq,arrayOfGoalData[6]);
    		Assert.assertTrue(derivedActualSavingAmt==1000.0);
    		Assert.assertTrue(derivedExpectedSaving==11000.0);
    		Assert.assertTrue(recurringAmount==10000.0);
    		Assert.assertEquals(status,"NOT_STARTED");
    		Assert.assertEquals(isOffTrack,false);
		}
    	else if(!getCategoryID.isEmpty()&& !getGoal.isEmpty()&&getCategoryType.isEmpty()) {
    		int actualid=getYslResp.jsonPath().get("budget[0].groupId");
			String actualGroupName=getYslResp.jsonPath().get("budget[0].groupName");
		
			int actualCatId=getYslResp.jsonPath().get("budget[0].categoryData[0].categoryId");
			float actualCatIdAmt=getYslResp.jsonPath().get("budget[0].categoryData[0].budgetAmount.amount");
			int actualGoalId=getYslResp.jsonPath().get("budget[0].goal[0].goalId");
			String actualGoalFreq=getYslResp.jsonPath().get("budget[0].goal[0].frequency");
			String actualGoalName=getYslResp.jsonPath().get("budget[0].goal[0].goalName");
			float derivedActualSavingAmt=getYslResp.jsonPath().get("budget[0].goal[0].derivedActualSaving.amount");
			float derivedExpectedSaving=getYslResp.jsonPath().get("budget[0].goal[0].derivedExpectedSaving.amount");
			float recurringAmount=getYslResp.jsonPath().get("budget[0].goal[0].recurringAmount.amount");
			String status=getYslResp.jsonPath().get("budget[0].goal[0].status");
			boolean isOffTrack=getYslResp.jsonPath().get("budget[0].goal[0].isOffTrack");
	        Assert.assertEquals(actualid, Integer.parseInt(groupId2));
			Assert.assertEquals(actualGroupName, groupName2);
			int expectedId=categoryId2.get(0);
			System.out.println(expectedId);
			System.out.println(actualCatId);
			Assert.assertTrue(actualCatId==expectedId);
			Assert.assertEquals(actualCatIdAmt,Float.parseFloat(BudgetAmount2));
			Assert.assertEquals(actualGoalId,Integer.parseInt(listGoalId.get(1)));
			Assert.assertEquals(actualGoalName,goalNameList.get(1));
			Assert.assertEquals(actualGoalFreq,arrayOfGoalData[6]);
			Assert.assertTrue(derivedActualSavingAmt==1000.0);
			Assert.assertTrue(derivedExpectedSaving==11000);
			Assert.assertTrue(recurringAmount==10000.0);
			Assert.assertEquals(status,"NOT_STARTED");
			Assert.assertEquals(isOffTrack,false);
    	}
    	else if(!getCategoryID.isEmpty()&& getGoal.isEmpty()&& !getCategoryType.isEmpty()) {
    	int actualid=getYslResp.jsonPath().get("budget[0].groupId");
		String actualGroupName=getYslResp.jsonPath().get("budget[0].groupName");
		String actualGroupCatrgoryType1=getYslResp.jsonPath().get("budget[0].categoryTypeData[0].categoryType");
		float actualGroupCatrgoryTypeAmount1=getYslResp.jsonPath().get("budget[0].categoryTypeData[0].budgetAmount.amount");
		String actualGroupCatrgoryType2=getYslResp.jsonPath().get("budget[0].categoryTypeData[1].categoryType");
		float actualGroupCatrgoryTypeAmount2=getYslResp.jsonPath().get("budget[0].categoryTypeData[1].budgetAmount.amount");
		int actualCatId=getYslResp.jsonPath().get("budget[0].categoryData[0].categoryId");
		float actualCatIdAmt=getYslResp.jsonPath().get("budget[0].categoryData[0].budgetAmount.amount");
		
        Assert.assertEquals(actualid, Integer.parseInt(groupId2));
		Assert.assertEquals(actualGroupName, groupName2);
		Assert.assertEquals(actualGroupCatrgoryType1, arrayOfCatType2.get(0));
		Assert.assertEquals(actualGroupCatrgoryTypeAmount1,Float.parseFloat(BudgetAmount2));
		Assert.assertEquals(actualGroupCatrgoryType2, arrayOfCatType2.get(1));
		Assert.assertEquals(actualGroupCatrgoryTypeAmount2,Float.parseFloat(BudgetAmount2));
		int expectedId=categoryId2.get(0);
		System.out.println(expectedId);
		System.out.println(actualCatId);
		Assert.assertTrue(actualCatId==expectedId);
		Assert.assertEquals(actualCatIdAmt,Float.parseFloat(BudgetAmount2));
    	}
    	else if(getCategoryID.isEmpty()&& !getGoal.isEmpty()&& getCategoryType.isEmpty()) {
		int actualGoalId=getYslResp.jsonPath().get("budget[0].goal[0].goalId");
		String actualGoalFreq=getYslResp.jsonPath().get("budget[0].goal[0].frequency");
		String actualGoalName=getYslResp.jsonPath().get("budget[0].goal[0].goalName");
		float derivedActualSavingAmt=getYslResp.jsonPath().get("budget[0].goal[0].derivedActualSaving.amount");
		float derivedExpectedSaving=getYslResp.jsonPath().get("budget[0].goal[0].derivedExpectedSaving.amount");
		float recurringAmount=getYslResp.jsonPath().get("budget[0].goal[0].recurringAmount.amount");
		String status=getYslResp.jsonPath().get("budget[0].goal[0].status");
		boolean isOffTrack=getYslResp.jsonPath().get("budget[0].goal[0].isOffTrack");
		Assert.assertEquals(actualGoalId,Integer.parseInt(listGoalId.get(1)));
		Assert.assertEquals(actualGoalName,goalNameList.get(1));
		Assert.assertEquals(actualGoalFreq,arrayOfGoalData[6]);
		Assert.assertTrue(derivedActualSavingAmt==1000.0);
		Assert.assertTrue(derivedExpectedSaving==11000.0);
		Assert.assertTrue(recurringAmount==10000.0);
		Assert.assertEquals(status,"NOT_STARTED");
		Assert.assertEquals(isOffTrack,false);
    	}
    	 }
    	
	
	}
	
	@Test(enabled = true, dataProvider = "feeder", priority = 5,dependsOnMethods="initiateCobAndUserSession")
	@Source(readYsl4)
	public void getBudgetGroupWithGoalAssociatedAndCatdatType(String testCaseId, String testcase, String CategoryId, String CategoryIdAmount, 
			String CateogryType,String CateogryTypeAmount,String CurrencyType,String Goal,String GoalStartDate,String GoalEndDate,
			String httpStatus,String ResFile,String FilePath,String GoalId,String groupId,String getCategoryID,String getCategoryType,String getGoal,String ErrorCode,String ExpectedErrorMsg,String Enabled)
			throws Exception {

		HashMap<String, Object> params2 = new HashMap<>();
	
		if (getCategoryID.isEmpty() && !getCategoryType.isEmpty()) {
			params2.put("groupId", groupId4);
			params2.put("include", getCategoryType+","+getGoal);
		}
		else if (!getCategoryID.isEmpty() && getCategoryType.isEmpty())
		{
			params2.put("groupId", groupId3);
			params2.put("include", getCategoryID+","+getGoal);
		}
		HttpMethodParameters parameters2 = HttpMethodParameters.builder().queryParams(params2).build();
    	getYslResp = budgetUtils.getBudget(parameters2, sessionObj);
    	
    	if (getCategoryID.isEmpty() && !getCategoryType.isEmpty()) {
    		int actualid=getYslResp.jsonPath().get("budget[0].groupId");
    		String actualGroupName=getYslResp.jsonPath().get("budget[0].groupName");
    		String actualGroupCatrgoryType1=getYslResp.jsonPath().get("budget[0].categoryTypeData[0].categoryType");
    		float actualGroupCatrgoryTypeAmount1=getYslResp.jsonPath().get("budget[0].categoryTypeData[0].budgetAmount.amount");
    		String actualGroupCatrgoryType2=getYslResp.jsonPath().get("budget[0].categoryTypeData[1].categoryType");
    		float actualGroupCatrgoryTypeAmount2=getYslResp.jsonPath().get("budget[0].categoryTypeData[1].budgetAmount.amount");
    		int actualGoalId=getYslResp.jsonPath().get("budget[0].goal[0].goalId");
    		String actualGoalFreq=getYslResp.jsonPath().get("budget[0].goal[0].frequency");
    		String actualGoalName=getYslResp.jsonPath().get("budget[0].goal[0].goalName");
    		float derivedActualSavingAmt=getYslResp.jsonPath().get("budget[0].goal[0].derivedActualSaving.amount");
    		float derivedExpectedSaving=getYslResp.jsonPath().get("budget[0].goal[0].derivedExpectedSaving.amount");
    		float recurringAmount=getYslResp.jsonPath().get("budget[0].goal[0].recurringAmount.amount");
    		String status=getYslResp.jsonPath().get("budget[0].goal[0].status");
    		boolean isOffTrack=getYslResp.jsonPath().get("budget[0].goal[0].isOffTrack");
    		Assert.assertEquals(actualid, Integer.parseInt(groupId4));
    		Assert.assertEquals(actualGroupName, groupName4);
    		Assert.assertEquals(actualGroupCatrgoryType1, arrayOfCatType2.get(0));
    		Assert.assertEquals(actualGroupCatrgoryTypeAmount1,Float.parseFloat(BudgetAmount4.get(0)));
    		Assert.assertEquals(actualGroupCatrgoryType2, arrayOfCatType2.get(1));
    		Assert.assertEquals(actualGroupCatrgoryTypeAmount2,Float.parseFloat(BudgetAmount4.get(1)));
    		Assert.assertEquals(actualGoalId,Integer.parseInt(listGoalId.get(3)));
    		Assert.assertEquals(actualGoalName,goalNameList.get(3));
    		Assert.assertEquals(actualGoalFreq,arrayOfGoalData[6]);
    		Assert.assertTrue(derivedActualSavingAmt==1000.0);
    		Assert.assertTrue(derivedExpectedSaving==11000.0);
    		Assert.assertTrue(recurringAmount==10000.0);
    		Assert.assertEquals(status,"NOT_STARTED");
    		Assert.assertEquals(isOffTrack,false);
		}
		else if (!getCategoryID.isEmpty() && getCategoryType.isEmpty())
		{
			int actualid=getYslResp.jsonPath().get("budget[0].groupId");
			String actualGroupName=getYslResp.jsonPath().get("budget[0].groupName");
		
			int actualCatId=getYslResp.jsonPath().get("budget[0].categoryData[0].categoryId");
			float actualCatIdAmt=getYslResp.jsonPath().get("budget[0].categoryData[0].budgetAmount.amount");
			int actualGoalId=getYslResp.jsonPath().get("budget[0].goal[0].goalId");
			String actualGoalFreq=getYslResp.jsonPath().get("budget[0].goal[0].frequency");
			String actualGoalName=getYslResp.jsonPath().get("budget[0].goal[0].goalName");
			float derivedActualSavingAmt=getYslResp.jsonPath().get("budget[0].goal[0].derivedActualSaving.amount");
			float derivedExpectedSaving=getYslResp.jsonPath().get("budget[0].goal[0].derivedExpectedSaving.amount");
			float recurringAmount=getYslResp.jsonPath().get("budget[0].goal[0].recurringAmount.amount");
			String status=getYslResp.jsonPath().get("budget[0].goal[0].status");
			boolean isOffTrack=getYslResp.jsonPath().get("budget[0].goal[0].isOffTrack");
	        Assert.assertEquals(actualid, Integer.parseInt(groupId3));
			Assert.assertEquals(actualGroupName, groupName3);
			int expectedId=categoryId3.get(0);
			System.out.println(expectedId);
			System.out.println(actualCatId);
			Assert.assertTrue(actualCatId==expectedId);
			Assert.assertEquals(actualCatIdAmt,Float.parseFloat(BudgetAmount3.get(0)));
			Assert.assertEquals(actualGoalId,Integer.parseInt(listGoalId.get(2)));
			Assert.assertEquals(actualGoalName,goalNameList.get(2));
			Assert.assertEquals(actualGoalFreq,arrayOfGoalData[6]);
			Assert.assertTrue(derivedActualSavingAmt==1000.0);
			Assert.assertTrue(derivedExpectedSaving==11000.0);
			Assert.assertTrue(recurringAmount==10000.0);
			Assert.assertEquals(status,"NOT_STARTED");
			Assert.assertEquals(isOffTrack,false);
		}
    	
		
	}

}