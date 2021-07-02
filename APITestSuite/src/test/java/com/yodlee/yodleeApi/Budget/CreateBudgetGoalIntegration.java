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

import static org.testng.Assert.assertEquals;


import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.commons.lang.RandomStringUtils;
import org.databene.benerator.anno.Source;
import org.jose4j.lang.JoseException;
import org.json.JSONObject;
import org.testng.Assert;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;


import com.yodlee.yodleeApi.common.Base;
import com.yodlee.yodleeApi.common.Configuration;
import com.yodlee.yodleeApi.constants.JSONPaths;
import com.yodlee.yodleeApi.constants.JsonPath;
import com.yodlee.yodleeApi.helper.BudgetHelper;

import com.yodlee.yodleeApi.helper.SaveForGoalHelper;
import com.yodlee.yodleeApi.helper.UserHelper;
import com.yodlee.yodleeApi.pojo.EnvSession;
import com.yodlee.yodleeApi.pojo.HttpMethodParameters;
import com.yodlee.yodleeApi.pojo.User;
import com.yodlee.yodleeApi.utils.apiUtils.BudgetUtils;
import com.yodlee.yodleeApi.utils.apiUtils.GroupUtils;
import com.yodlee.yodleeApi.utils.apiUtils.ProviderAccountUtils;
import com.yodlee.yodleeApi.utils.apiUtils.SFGUtils;
import com.yodlee.yodleeApi.utils.commonUtils.CommonUtils;


import io.restassured.response.Response;

public class CreateBudgetGoalIntegration extends Base {

	// Global ArrayList variables declearation and initialization

	private ArrayList<String> arrayOfGoalData = new ArrayList<String>();
	static List<Integer> itemAccountIds = new LinkedList<Integer>();
	ArrayList<String> listBudgetAmont = new ArrayList<String>();
	ArrayList<String> listCatDataBudgetAmont = new ArrayList<String>();
	ArrayList<String> createbudgetBodyParam = new ArrayList<String>();
	
	// Global String variables declearation and initialization
	static String getGoalId;

	private String currencyType = null;
	
	private UserHelper userHelper = new UserHelper();

	// Object creation

	private CommonUtils commonUtils = new CommonUtils();
	private GroupUtils groupUtils = new GroupUtils();
	private BudgetUtils budgetUtils = new BudgetUtils();
	public static Long providerAccountId = null;

	// Initialization of Response objects

	EnvSession sessionObj = null;
	// Initialization of File objects
	private	String arrayOfGoalData1[]=null;
	private ArrayList<String> goalNameList = new ArrayList<String>();
	private ArrayList<String> listGoalId = new ArrayList<String>();
	
	private BudgetHelper bHelper = new BudgetHelper();
	private Configuration config = Configuration.getInstance();
	private ProviderAccountUtils providerAccountUtils = new ProviderAccountUtils();
	private SFGUtils sfgUtils = new SFGUtils();
	private Random rand = new Random();
	private String userName = "BudegtUser12" + System.currentTimeMillis();
	private SaveForGoalHelper sfg = new SaveForGoalHelper();
	// Initialization of .CSV file location
	final static String createBudget_Test = "\\TestData\\CSVFiles\\Budget\\CreateBudgetGoal_Test.csv";
	final static String createBudget_Test1 = "\\TestData\\CSVFiles\\Budget\\CreateBudgetGoal_Test1.csv";
	final static String createBudget_Test2 = "\\TestData\\CSVFiles\\Budget\\CreateBudgetGoal_Test2.csv";
	final static String createBudget_Test3 = "\\TestData\\CSVFiles\\Budget\\CreateBudgetGoal_Test3.csv";
	final static String createBudget_Test4 = "\\TestData\\CSVFiles\\Budget\\CreateBudgetGoal_Test4.csv";
	final static String createBudget_Test5 = "\\TestData\\CSVFiles\\Budget\\CreateBudgetGoal_Test5.csv";
	final static String createBudget_Test6 = "\\TestData\\CSVFiles\\Budget\\CreateBudgetGoal_Test6.csv";
	final static String createBudget_Test7 = "\\TestData\\CSVFiles\\Budget\\CreateBudgetGoal_Test7.csv";


	/**
	 * In before class initializing the cobrand and new usersession 2 - Adding
	 * Dag xml site 3 - Creating Group and fetching group id.
	 * 
	 * @throws IOException
	 * @throws JoseException
	 **/
	

	@BeforeClass(alwaysRun = true)
	public void createUser() throws ParseException, SQLException, JoseException {
		System.out.println("******STARTING***********");
		User userInfo = User.builder().build();
		userInfo.setUsername(userName);
		userInfo.setPassword("Test@123");
		sessionObj = EnvSession.builder().cobSession(config.getCobrandSessionObj().getCobSession())
				.path(config.getCobrandSessionObj().getPath()).build();
		//userHelper.getUserSession("InsightsEngineusers26 ", "TEST@123", sessionObj); 
		userHelper.getUserSession(userInfo, sessionObj);
		long providerId = 16441;
		providerAccountId = providerId;
		Response response = providerAccountUtils.addProviderAccountStrict(providerId, "fieldarray",
				"budget1_v1.site16441.1", "site16441.1", sessionObj);
		providerAccountId = response.jsonPath().getLong(JSONPaths.PROVIDER_ACC_ID);
		Assert.assertTrue(providerAccountId!=null);
		
		}
	@Test(enabled = true, dataProvider = "feeder",priority=1)
	@Source(createBudget_Test)
	public void createPredefinedSFG(String testCaseId, String testCase, String enabled, String testNo1,
			String catId, String catType, String currType,String Goal,String GoalStartDate
		    ,String GoalEndDate) throws Exception, IOException, JoseException {
	
				
		
		String bodyParams = null;
		arrayOfGoalData1=Goal.split(",");
		
		System.out.println(arrayOfGoalData);
		for (int i = 0; i <4; i++) {
			String golaName=RandomStringUtils.randomAlphabetic(10);
			goalNameList.add(golaName);
		bodyParams = sfg.createGoalJson(golaName ,arrayOfGoalData1[1], sfg.sfgDateFormate(GoalStartDate),sfg.sfgDateFormate(GoalEndDate) , arrayOfGoalData1[2],
				arrayOfGoalData1[3], arrayOfGoalData1[4], arrayOfGoalData1[5], arrayOfGoalData1[6], arrayOfGoalData1[7], arrayOfGoalData1[8],
				arrayOfGoalData1[9],"");
		
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
		
	
		createFundingRuleBodyParam = sfg.createFundingRule(itemAccountIds.get(0), arrayOfGoalData1[4],
				arrayOfGoalData1[5], arrayOfGoalData1[6], arrayOfGoalData1[11], sfg.sfgDateFormate(GoalStartDate),
				sfg.sfgDateFormate(GoalEndDate));

		httpmethodParam.setPathParams(createFundingRulePathParam);
		httpmethodParam.setBodyParams(createFundingRuleBodyParam);

		Response createFundingRuleResp = sfgUtils.createFundingRule(httpmethodParam, sessionObj);
		Assert.assertTrue(createFundingRuleResp.getStatusCode()==201);
		}
	
	}
	@SuppressWarnings("static-access")
	@Test(enabled = true, dataProvider = "feeder", priority = 2)
	@Source(createBudget_Test1)
	public void createBudgetWithAllGoalState(String testCaseId, String testCase, String testNo1, String catId,String CategoryIdAmount,
			String catType,String CateogryTypeAmount, String currType,String Goal,String GoalStartDate
    ,String GoalEndDate,String Status,String enabled) throws Exception {
		commonUtils.isTCEnabled(enabled, testCaseId);	
		HttpMethodParameters groupJson2 = bHelper.createGroupJson(RandomStringUtils.randomAlphabetic(10));
        Response groupResponse1 = groupUtils.createGroup(groupJson2,sessionObj);
        String groupId2 = groupResponse1.jsonPath().getString("group.id");
		
		
		List<String> arrayOfCatType=new ArrayList<String>();
		List<String> catTypeAmt=new ArrayList<String>();

		if (!catType.isEmpty()) {
		arrayOfCatType =Arrays.asList(catType.split(","));
		catTypeAmt=Arrays.asList(CateogryTypeAmount.split(","));		}
		List<Integer> catid=new ArrayList<Integer>();
		if (!catId.isEmpty()) 
		{
			catid = Stream.of(catId.split(",")).map(Integer::parseInt).collect(Collectors.toList());

		}
	
		
		SFGUtils sfgUtils = new SFGUtils();
		ArrayList<String> listGoalId = new ArrayList<String>();
		String bodyParams = null;
		String arrayOfGoalData[]=Goal.split(",");
		
		System.out.println(arrayOfGoalData);
		bodyParams = sfg.createGoalJson(arrayOfGoalData[0], arrayOfGoalData[1], sfg.sfgDateFormate(GoalStartDate),sfg.sfgDateFormate(GoalEndDate) , arrayOfGoalData[2],
				arrayOfGoalData[3], arrayOfGoalData[4], arrayOfGoalData[5], arrayOfGoalData[6], arrayOfGoalData[7], arrayOfGoalData[8],
				arrayOfGoalData[9],"");
		
		System.out.println(bodyParams);

		HttpMethodParameters httpMethodParameters1 = HttpMethodParameters.builder().build();
		httpMethodParameters1.setBodyParams(bodyParams);
		Response response = sfgUtils.createGoal(httpMethodParameters1, sessionObj);
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
		
		if (Status.equals("IN_PROGRESS")) {
			HashMap<String, Object> pathParam = new HashMap<>();
			pathParam.put("goalIdUpdate", goalId);
			HttpMethodParameters httpMethodParameters = HttpMethodParameters.builder().build();
			
			httpMethodParameters.setPathParams(pathParam);
			String updateBodyParams = sfg.updateGoalBodyParam("updatename", arrayOfGoalData[1], arrayOfGoalData[2], arrayOfGoalData[3],
					arrayOfGoalData[4], arrayOfGoalData[5], arrayOfGoalData[6], sfg.sfgDateFormate(GoalStartDate),
					sfg.sfgDateFormate(GoalEndDate), Status, arrayOfGoalData[8], arrayOfGoalData[9]);
			httpMethodParameters.setBodyParams(updateBodyParams);
			Response resp = sfgUtils.updateGoal(httpMethodParameters, sessionObj);
		}
		else if (Status.equals("COMPLETED")) {
			HashMap<String, Object> pathParam = new HashMap<>();
			pathParam.put("goalIdUpdate", goalId);
			HttpMethodParameters httpMethodParameters = HttpMethodParameters.builder().build();
			
			httpMethodParameters.setPathParams(pathParam);
			String updateBodyParams = sfg.updateGoalBodyParam("updatename", arrayOfGoalData[1], arrayOfGoalData[2], arrayOfGoalData[3],
					arrayOfGoalData[4], arrayOfGoalData[5], arrayOfGoalData[6], sfg.sfgDateFormate(GoalStartDate),
					sfg.sfgDateFormate(GoalEndDate), Status, arrayOfGoalData[8], arrayOfGoalData[9]);
			httpMethodParameters.setBodyParams(updateBodyParams);
			Response resp = sfgUtils.updateGoal(httpMethodParameters, sessionObj);
		}
		
		
		sfg.updateGoalToPastDate(goalId, 15);
		String createbudgetBodyParam= bHelper.CreateBudgetAtCatTypeGoal(groupId2, arrayOfCatType, catTypeAmt,
				currType,goalId);
		System.out.println(createbudgetBodyParam);
		
		HttpMethodParameters httpMethodParameters = HttpMethodParameters.builder().build();
		httpMethodParameters.setBodyParams(createbudgetBodyParam);
		BudgetUtils budgetUtils = new BudgetUtils();
		Response catDataAndCatTypeBodyResp = budgetUtils.createBudget(httpMethodParameters,
				sessionObj);
        int budgetId=catDataAndCatTypeBodyResp.jsonPath().get("budgetGroups.id");
        Assert.assertTrue(catDataAndCatTypeBodyResp.statusCode()==201);
        bHelper.createBudgetDbVerification(userName, budgetId, arrayOfCatType, catid, listGoalId);
        System.out.println(catDataAndCatTypeBodyResp.statusCode());
       
	
		
	}
	@SuppressWarnings("static-access")
	@Test(enabled = true, dataProvider = "feeder", priority = 3,dependsOnMethods="createPredefinedSFG")
	@Source(createBudget_Test2)
	public void createBudgetCatTypeGoal(String testCaseId, String testCase, String testNo1, String catId,String CategoryIdAmount,
			String catType,String CateogryTypeAmount, String currType,String Goal,String GoalStartDate
    ,String GoalEndDate, String enabled) throws Exception {
		commonUtils.isTCEnabled(enabled, testCaseId);
			List<String> arrayOfCatType=new ArrayList<String>();
			List<String> catTypeAmt=new ArrayList<String>();

			if (!catType.isEmpty()) {
			arrayOfCatType =Arrays.asList(catType.split(","));
			catTypeAmt=Arrays.asList(CateogryTypeAmount.split(","));	
			}
			List<Integer> catid=new ArrayList<Integer>();
			List<String> catidAmount=new ArrayList<String>();
			if (!catId.isEmpty()) 
			{
				catid = Stream.of(catId.split(",")).map(Integer::parseInt).collect(Collectors.toList());
				catidAmount = Arrays.asList(CategoryIdAmount.split(","));

			}
		HttpMethodParameters	groupJson1 = bHelper.createGroupJson(RandomStringUtils.randomAlphabetic(10));
		HttpMethodParameters groupJson2 = bHelper.createGroupJson(RandomStringUtils.randomAlphabetic(10));

		Response groupResponse = groupUtils.createGroup(groupJson1, sessionObj);
		String groupId1 = groupResponse.jsonPath().getString("group.id");
		Response groupResponse1 = groupUtils.createGroup(groupJson2,sessionObj);
		String groupId2 = groupResponse1.jsonPath().getString("group.id");
		
		if (!catType.isEmpty()) {
			List<String> goalid=new ArrayList<String>();
			goalid.add(listGoalId.get(0));
		createbudgetBodyParam.add(bHelper.CreateBudgetAtCatTypeGoal(groupId2, arrayOfCatType, catTypeAmt,
				currencyType,goalid.get(0)));
		System.out.println(createbudgetBodyParam);
		createbudgetBodyParam.add(bHelper.CreateBudgetAtCatTypeGoal(groupId1, arrayOfCatType, catTypeAmt,
				currencyType,goalid.get(0)));
		System.out.println(createbudgetBodyParam);
		for(int i=0;i<createbudgetBodyParam.size();i++)
		{
		HttpMethodParameters httpMethodParameters = HttpMethodParameters.builder().build();
		httpMethodParameters.setBodyParams(createbudgetBodyParam.get(i));
		BudgetUtils budgetUtils = new BudgetUtils();
		Response catDataAndCatTypeBodyResp = budgetUtils.createBudget(httpMethodParameters,
				sessionObj);
		int budgetId=catDataAndCatTypeBodyResp.jsonPath().get("budgetGroups.id");
		Assert.assertTrue(catDataAndCatTypeBodyResp.statusCode()==201);
		  bHelper.createBudgetDbVerification(userName, budgetId, arrayOfCatType, catid, goalid);
	        }
		}
	
		if (!catId.isEmpty()) {
		List<String> goalid=new ArrayList<String>();
			goalid.add(listGoalId.get(0));
			goalid.add(listGoalId.get(1));
			String	catDataAndCatTypeBodyReqParam = bHelper.CreateBudgetAtCatDataGoal(groupId1, catid, catidAmount,
						currencyType,goalid);
			HttpMethodParameters httpMethodParameters = HttpMethodParameters.builder().build();
			httpMethodParameters.setBodyParams(catDataAndCatTypeBodyReqParam);
			BudgetUtils budgetUtils = new BudgetUtils();
			Response catDataAndCatTypeBodyResp = budgetUtils.createBudget(httpMethodParameters,
					sessionObj);
			int budgetId=catDataAndCatTypeBodyResp.jsonPath().get("budgetGroups.id");

			  bHelper.createBudgetDbVerification(userName, budgetId, arrayOfCatType, catid, goalid);
		        System.out.println(catDataAndCatTypeBodyResp.statusCode());		
	}
	}
	
	@SuppressWarnings("static-access")
	@Test(enabled = true, dataProvider = "feeder", priority = 4,dependsOnMethods="createPredefinedSFG")
	@Source(createBudget_Test3)
	public void createBudgetTestWithcatDataAndTypeData(String testCaseId, String testCase, String testNo1,
			String catId,String CategoryIdAmount,
			String catType, String CateogryTypeAmount,String currType,String Goal,String GoalStartDate
    ,String GoalEndDate,int httpStatus,String ResFile,String FilePath,String ErrorCode,String ExpectedErrorMsg,String Enabled) throws Exception {
		commonUtils.isTCEnabled(Enabled, testCaseId);
		HttpMethodParameters groupJson1 = bHelper.createGroupJson(RandomStringUtils.randomAlphabetic(10));
		Response groupResponse = groupUtils.createGroup(groupJson1, sessionObj);
		String groupId1 = groupResponse.jsonPath().getString("group.id");
		List<String> arrayOfCatType2=new ArrayList<String>();
		List<Integer> categoryId2=new ArrayList<Integer>();
        String BudgetAmount2=String.valueOf(rand.nextInt(1000));
		String currencyType2 = currType.split(",")[rand.nextInt(10)];
		List<String> goalid2=new ArrayList<String>();
		List<String> arrayOfCatType=new ArrayList<String>();
		List<String> catTypeAmt=new ArrayList<String>();

		if (!catType.isEmpty()) {
		arrayOfCatType =Arrays.asList(catType.split(","));
		catTypeAmt=Arrays.asList(CateogryTypeAmount.split(","));	
		}
		List<Integer> catid=new ArrayList<Integer>();
		List<String> catidAmount=new ArrayList<String>();
		if (!catId.isEmpty()) 
		{
			catid = Stream.of(catId.split(",")).map(Integer::parseInt).collect(Collectors.toList());
			catidAmount = Arrays.asList(CategoryIdAmount.split(","));

		}
		String catDataAndCatTypeBodyReqParam2=null;
		goalid2.add(listGoalId.get(1));
		if (!Goal.isEmpty()) {
		 catDataAndCatTypeBodyReqParam2 = bHelper.CreateCatDataAndCatTypeGoal(groupId1, arrayOfCatType, catid,
				CategoryIdAmount, currencyType2,goalid2);
		}
		else {
			catDataAndCatTypeBodyReqParam2 = bHelper.CreateCatDataAndCatType(groupId1, arrayOfCatType, catid,
					CategoryIdAmount, currencyType2);
			}
		HttpMethodParameters httpMethodParameters2 = HttpMethodParameters.builder().build();
		httpMethodParameters2.setBodyParams(catDataAndCatTypeBodyReqParam2);
		BudgetUtils budgetUtils2 = new BudgetUtils();
		Response catDataAndCatTypeBodyResp=budgetUtils2.createBudget(httpMethodParameters2, sessionObj);
		if(catDataAndCatTypeBodyResp.getStatusCode()!=201) {
			JSONObject Obj = new JSONObject(catDataAndCatTypeBodyResp.asString());
			Assert.assertEquals(ErrorCode, Obj.get("errorCode"));
			Assert.assertEquals(ExpectedErrorMsg, Obj.get("errorMessage"));	
		}else {
			assertEquals(catDataAndCatTypeBodyResp.getStatusCode(),201);
			int budgetId=catDataAndCatTypeBodyResp.jsonPath().get("budgetGroups.id");
            bHelper.createBudgetDbVerification(userName, budgetId, arrayOfCatType, catid, goalid2);
	
		    }
		 
		
	}
	@SuppressWarnings("static-access")
	@Test(enabled = true, dataProvider = "feeder", priority = 5,dependsOnMethods="createPredefinedSFG")
	@Source(createBudget_Test4)
	public void createBudgetValidAndInvalidGoal(String testCaseId, String testCase, String testNo1, String catId,String CategoryIdAmount,
			String catType,String CateogryTypeAmount, String currType,String Goal,String GoalStartDate
		    ,String GoalEndDate,int httpStatus,String ResFile,String FilePath,String GoalId,String ErrorCode,String ExpectedErrorMsg,String Enabled) throws Exception {
		commonUtils.isTCEnabled(Enabled, testCaseId);
		HttpMethodParameters groupJson1 = bHelper.createGroupJson(RandomStringUtils.randomAlphabetic(10));

		Response groupResponse = groupUtils.createGroup(groupJson1, sessionObj);
		String groupId1 = groupResponse.jsonPath().getString("group.id");
		
		List<String> arrayOfCatType=new ArrayList<String>();
		List<String> catTypeAmt=new ArrayList<String>();

		if (!catType.isEmpty()) {
		arrayOfCatType =Arrays.asList(catType.split(","));
		catTypeAmt=Arrays.asList(CateogryTypeAmount.split(","));	
		}
		List<Integer> catid=new ArrayList<Integer>();
		List<String> catidAmount=new ArrayList<String>();
		if (!catId.isEmpty()) 
		{
			catid = Stream.of(catId.split(",")).map(Integer::parseInt).collect(Collectors.toList());
			catidAmount = Arrays.asList(CategoryIdAmount.split(","));

		}
		String BudgetAmount2=String.valueOf(rand.nextInt(1000));
		String currencyType2 = currType.split(",")[rand.nextInt(10)];
		List<String> goalid2=new ArrayList<String>();
	
		if (!GoalId.isEmpty()) {
			String goalsids[]=GoalId.split(",");
			goalid2.add(goalsids[0]);
			if (goalsids.length==2) {
				goalid2.add(listGoalId.get(1));
			}
			
		}
		else {
			goalid2.add(listGoalId.get(1));
			
		}
		String catDataAndCatTypeBodyReqParam=null;
		if (!catType.isEmpty() && !catType.isEmpty())
		{
			 catDataAndCatTypeBodyReqParam = bHelper.CreateCatDataAndCatTypeGoal(groupId1, arrayOfCatType, catid,
					 CategoryIdAmount, currencyType2,goalid2);
		}
		else {
			 catDataAndCatTypeBodyReqParam = bHelper.CreateBudgetWithOnlyGOalId(groupId1,listGoalId);

		}
		
		
		
		HttpMethodParameters httpMethodParameters = HttpMethodParameters.builder().build();
		httpMethodParameters.setBodyParams(catDataAndCatTypeBodyReqParam);
		
		Response catDataAndCatTypeBodyResp = budgetUtils.createBudget(httpMethodParameters,
				sessionObj);
		if(catDataAndCatTypeBodyResp.getStatusCode()!=201) {
			JSONObject Obj = new JSONObject(catDataAndCatTypeBodyResp.asString());
			Assert.assertEquals(ErrorCode, Obj.get("errorCode"));
			Assert.assertEquals(ExpectedErrorMsg, Obj.get("errorMessage"));	
		}else {
			assertEquals(catDataAndCatTypeBodyResp.getStatusCode(),201);
			int budgetId=catDataAndCatTypeBodyResp.jsonPath().get("budgetGroups.id");
            bHelper.createBudgetDbVerification(userName, budgetId, arrayOfCatType, catid, goalid2);
	
		    }
		
	}
	@SuppressWarnings("static-access")
	@Test(enabled = true, dataProvider = "feeder", priority = 6,dependsOnMethods="createPredefinedSFG")
	@Source(createBudget_Test5)
	public  void createBudgetWithDiffUserGoal(String testCaseId, String testCase, String testNo1, String catId,String CategoryIdAmount,
			String catType,String CateogryTypeAmount, String currType,String Goal,String GoalStartDate
		    ,String GoalEndDate,int httpStatus,String ResFile,String FilePath,String userName
     ,String ErrorCode,String ExpectedErrorMsg,String Enabled) throws Exception {
		
		HttpMethodParameters groupJson1 = bHelper.createGroupJson(RandomStringUtils.randomAlphabetic(10));

		Response groupResponse = groupUtils.createGroup(groupJson1, sessionObj);
        String groupId1 = groupResponse.jsonPath().getString("group.id");
		

		List<String> arrayOfCatType=new ArrayList<String>();
		List<String> catTypeAmt=new ArrayList<String>();

		if (!catType.isEmpty()) {
		arrayOfCatType =Arrays.asList(catType.split(","));
		catTypeAmt=Arrays.asList(CateogryTypeAmount.split(","));	
		}
		List<Integer> catid=new ArrayList<Integer>();
		List<String> catidAmount=new ArrayList<String>();
		if (!catId.isEmpty()) 
		{
			catid = Stream.of(catId.split(",")).map(Integer::parseInt).collect(Collectors.toList());
			catidAmount = Arrays.asList(CategoryIdAmount.split(","));

		}
		
	
		List<String> goalid2=new ArrayList<String>();
		
		SFGUtils sfgUtils = new SFGUtils();
		
		if (!userName.isEmpty()) {
		User userInfo = User.builder().build();
		userInfo.setUsername(userName+ System.currentTimeMillis());
		userInfo.setPassword("Test@123");
		EnvSession sessionObj = EnvSession.builder().cobSession(config.getCobrandSessionObj().getCobSession())
				.path(config.getCobrandSessionObj().getPath()).build();
		//userHelper.getUserSession("InsightsEngineusers26 ", "TEST@123", sessionObj); 
		UserHelper userHelper1=new UserHelper();
		userHelper1.getUserSession(userInfo, sessionObj);
		long providerId1 = 16441;
		providerAccountId = providerId1;
		Response response = providerAccountUtils.addProviderAccountStrict(providerId1, "fieldarray",
				"getBudget.site16441.1", "site16441.1", sessionObj);
		providerAccountId = response.jsonPath().getLong(JSONPaths.PROVIDER_ACC_ID);
        String arrayOfGoalData[]=Goal.split(",");
		
		System.out.println(arrayOfGoalData);
		String  bodyParams = sfg.createGoalJson(arrayOfGoalData[0], arrayOfGoalData[1], sfg.sfgDateFormate(GoalStartDate),sfg.sfgDateFormate(GoalEndDate) , arrayOfGoalData[2],
				arrayOfGoalData[3], arrayOfGoalData[4], arrayOfGoalData[5], arrayOfGoalData[6], arrayOfGoalData[7], arrayOfGoalData[8],
				arrayOfGoalData[9],"");
		goalid2.add(listGoalId.get(1));
		HttpMethodParameters httpMethodParameters2 = HttpMethodParameters.builder().build();
		httpMethodParameters2.setBodyParams(bodyParams);
		Response response1 = sfgUtils.createGoal(httpMethodParameters2, sessionObj);
		String goalId=response1.jsonPath().getString("goal[0].id");
		goalid2.add(goalId);
		}
		else {
			goalid2.add(listGoalId.get(1));
		}
		
		if (Goal.isEmpty()) {
		LinkedHashMap<String, Object> pathParam =new LinkedHashMap<>(); 
		pathParam.put("goalIdUpdate", listGoalId.get(1));
		HttpMethodParameters httpParams = HttpMethodParameters.builder().build();
		httpParams.setPathParams(pathParam);
		Response resp=sfgUtils.deleteGoal(httpParams, sessionObj);
		}
		
		String catDataAndCatTypeBodyReqParam=null;
		catDataAndCatTypeBodyReqParam = bHelper.CreateCatDataAndCatTypeGoal(groupId1, arrayOfCatType, catid,
				 CategoryIdAmount, currType,goalid2);
		
		HttpMethodParameters httpMethodParameters = HttpMethodParameters.builder().build();
		httpMethodParameters.setBodyParams(catDataAndCatTypeBodyReqParam);
		
		Response catDataAndCatTypeBodyResp = budgetUtils.createBudget(httpMethodParameters,
				sessionObj);
		if(catDataAndCatTypeBodyResp.getStatusCode()!=201) {
			JSONObject Obj = new JSONObject(catDataAndCatTypeBodyResp.asString());
			Assert.assertEquals(ErrorCode, Obj.get("errorCode"));
			Assert.assertEquals(ExpectedErrorMsg, Obj.get("errorMessage"));	
		}else {
			assertEquals(catDataAndCatTypeBodyResp.getStatusCode(),201);
			int budgetId=catDataAndCatTypeBodyResp.jsonPath().get("budgetGroups.id");
            bHelper.createBudgetDbVerification(userName, budgetId, arrayOfCatType, catid, goalid2);
	
		    }
	
	}
	
	@SuppressWarnings("static-access")
	@Test(enabled = true, dataProvider = "feeder", priority = 7,dependsOnMethods="createPredefinedSFG")
	@Source(createBudget_Test6)
	public void createBudgetWithDiffAccGoal(String testCaseId, String testCase, String testNo1, String catId,String CategoryIdAmount,
			String catType,String CateogryTypeAmount, String currType,String Goal,String GoalStartDate
		    ,String GoalEndDate,int httpStatus,String ResFile,String FilePath,String GoalId,String ErrorCode,String ExpectedErrorMsg,String Enabled) throws Exception {
		commonUtils.isTCEnabled(Enabled, testCaseId);
		

		List<Integer> itemAccountId = bHelper.getItemAccountIds(userName, "18");

		HttpMethodParameters bodyParam = BudgetHelper.createGroupBodyParam(RandomStringUtils.randomAlphabetic(10), itemAccountId);
		Response groupResponse = groupUtils.createGroup(bodyParam, sessionObj);
		String groupId = groupResponse.jsonPath().getString(JsonPath.budgetGroupId);
		
		List<String> arrayOfCatType=new ArrayList<String>();
		List<String> catTypeAmt=new ArrayList<String>();

		if (!catType.isEmpty()) {
		arrayOfCatType =Arrays.asList(catType.split(","));
		catTypeAmt=Arrays.asList(CateogryTypeAmount.split(","));	
		}
		List<Integer> catid=new ArrayList<Integer>();
		List<String> catidAmount=new ArrayList<String>();
		if (!catId.isEmpty()) 
		{
			catid = Stream.of(catId.split(",")).map(Integer::parseInt).collect(Collectors.toList());
			catidAmount = Arrays.asList(CategoryIdAmount.split(","));

		}
		
		List<String> goalid2=new ArrayList<String>();

			SFGUtils sfgUtils = new SFGUtils();
			
			String bodyParams = null;
			arrayOfGoalData1=Goal.split(",");
			
			System.out.println(arrayOfGoalData);
			
				String golaName=RandomStringUtils.randomAlphabetic(10);
				goalNameList.add(golaName);
			bodyParams = sfg.createGoalJson(golaName ,arrayOfGoalData1[1], sfg.sfgDateFormate(GoalStartDate),sfg.sfgDateFormate(GoalEndDate) , arrayOfGoalData1[2],
					arrayOfGoalData1[3], arrayOfGoalData1[4], arrayOfGoalData1[5], arrayOfGoalData1[6], arrayOfGoalData1[7], arrayOfGoalData1[8],
					arrayOfGoalData1[9],"");
			
			System.out.println(bodyParams);

			HttpMethodParameters httpMethodParameters2 = HttpMethodParameters.builder().build();
			httpMethodParameters2.setBodyParams(bodyParams);
			Response response = sfgUtils.createGoal(httpMethodParameters2, sessionObj);
			String goalId=response.jsonPath().getString("goal[0].id");
			goalid2.add(goalId);
			System.out.println(goalId);
			itemAccountIds = sfg.getItemAccountIds(userName);
	        String createDestAccountBodyParam1=sfg.createDestinationAccount(bHelper.getItemAccountIds(userName, "10").get(0),1000,"USD");
			sfg.createDestAccount(goalId, createDestAccountBodyParam1, sessionObj);
			
		
		String catDataAndCatTypeBodyReqParam=null;
		if (!catType.isEmpty() && !catType.isEmpty())
		{
			 catDataAndCatTypeBodyReqParam = bHelper.CreateCatDataAndCatTypeGoal(groupId, arrayOfCatType, catid,
					 CategoryIdAmount, currType,goalid2);
		}
		else {
			 catDataAndCatTypeBodyReqParam = bHelper.CreateBudgetWithOnlyGOalId(groupId,listGoalId);

		}
		HttpMethodParameters httpMethodParameters = HttpMethodParameters.builder().build();
		httpMethodParameters.setBodyParams(catDataAndCatTypeBodyReqParam);
		
		Response catDataAndCatTypeBodyResp = budgetUtils.createBudget(httpMethodParameters,
				sessionObj);
		if(catDataAndCatTypeBodyResp.getStatusCode()!=201) {
			JSONObject Obj = new JSONObject(catDataAndCatTypeBodyResp.asString());
			Assert.assertEquals(ErrorCode, Obj.get("errorCode"));
			Assert.assertEquals(ExpectedErrorMsg, Obj.get("errorMessage"));	
		}else {
			assertEquals(catDataAndCatTypeBodyResp.getStatusCode(),201);
			int budgetId=catDataAndCatTypeBodyResp.jsonPath().get("budgetGroups.id");
            bHelper.createBudgetDbVerification(userName, budgetId, arrayOfCatType, catid, goalid2);
	
		    }
		
	}
	
	@SuppressWarnings("static-access")
	@Test(enabled = true, dataProvider = "feeder", priority = 8,dependsOnMethods="createPredefinedSFG")
	@Source(createBudget_Test7)
	public void createBudgetWithDuplicateGoal(String testCaseId, String testCase, String testNo1, String catId,String CategoryIdAmount,
			String catType,String CateogryTypeAmount, String currType,String Goal,String GoalStartDate
		    ,String GoalEndDate,int httpStatus,String ResFile,String FilePath,String GoalId,String ErrorCode,String ExpectedErrorMsg,String Enabled) throws Exception {
		commonUtils.isTCEnabled(Enabled, testCaseId);
		HttpMethodParameters groupJson1 = bHelper.createGroupJson(RandomStringUtils.randomAlphabetic(10));

		Response groupResponse = groupUtils.createGroup(groupJson1, sessionObj);
		String groupId1 = groupResponse.jsonPath().getString("group.id");
		
		List<String> arrayOfCatType=new ArrayList<String>();
		List<String> catTypeAmt=new ArrayList<String>();

		if (!catType.isEmpty()) {
		arrayOfCatType =Arrays.asList(catType.split(","));
		catTypeAmt=Arrays.asList(CateogryTypeAmount.split(","));	
		}
		List<Integer> catid=new ArrayList<Integer>();
		List<String> catidAmount=new ArrayList<String>();
		if (!catId.isEmpty()) 
		{
			catid = Stream.of(catId.split(",")).map(Integer::parseInt).collect(Collectors.toList());
			catidAmount = Arrays.asList(CategoryIdAmount.split(","));

		}
		String BudgetAmount2=String.valueOf(rand.nextInt(1000));
		String currencyType2 = currType.split(",")[rand.nextInt(10)];
		List<String> goalid2=new ArrayList<String>();
	
		if (!GoalId.isEmpty()) {
			String goalsids[]=GoalId.split(",");
			goalid2.add(goalsids[0]);
			if (goalsids.length==2) {
				goalid2.add(listGoalId.get(1));
			}
			
		}
		else {
			goalid2.add(listGoalId.get(1));
			
				 goalid2.add(listGoalId.get(1));
			 
		}
		String catDataAndCatTypeBodyReqParam=null;
		if (!catType.isEmpty() && !catType.isEmpty())
		{
			 catDataAndCatTypeBodyReqParam = bHelper.CreateCatDataAndCatTypeGoal(groupId1, arrayOfCatType, catid,
					 CategoryIdAmount, currencyType2,goalid2);
		}
		else {
			 catDataAndCatTypeBodyReqParam = bHelper.CreateBudgetWithOnlyGOalId(groupId1,listGoalId);

		}
		
		
		
		HttpMethodParameters httpMethodParameters = HttpMethodParameters.builder().build();
		httpMethodParameters.setBodyParams(catDataAndCatTypeBodyReqParam);
		
		Response catDataAndCatTypeBodyResp = budgetUtils.createBudget(httpMethodParameters,
				sessionObj);
		if(catDataAndCatTypeBodyResp.getStatusCode()!=201) {
			JSONObject Obj = new JSONObject(catDataAndCatTypeBodyResp.asString());
			Assert.assertEquals(ErrorCode, Obj.get("errorCode"));
			Assert.assertEquals(ExpectedErrorMsg, Obj.get("errorMessage"));	
		}else {
			assertEquals(catDataAndCatTypeBodyResp.getStatusCode(),201);
			int budgetId=catDataAndCatTypeBodyResp.jsonPath().get("budgetGroups.id");
            bHelper.createBudgetDbVerification(userName, budgetId, arrayOfCatType, catid, goalid2);
	
		    }
		
	}
	
}
