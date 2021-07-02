/**
* Copyright (c) 2018 Yodlee Inc. All Rights Reserved.
*
* This software is the confidential and proprietary information of
* Yodlee, Inc. Use is subject to license terms.
*
* @author Raghavendra Yadav G
*/

package com.yodlee.yodleeApi.Budget;



import com.yodlee.yodleeApi.common.Base;
import com.yodlee.yodleeApi.common.Configuration;
import com.yodlee.yodleeApi.constants.JSONPaths;
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

import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
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

import io.restassured.response.Response;

public class UpdateBudgetGoalYSL extends Base {


	public static Long providerAccountId = null;


	// ArrayList initialization
	private ArrayList<Integer> categoryId = new ArrayList<Integer>();
	private ArrayList<String> categoryType = new ArrayList<String>();
	private Configuration config = Configuration.getInstance();

	// Hash Map deceleration


	private BudgetUtils budgetUtils = new BudgetUtils();
	private BudgetHelper bHelper = new BudgetHelper();

	private HttpMethodParameters groupJson1 = null;
	private HttpMethodParameters groupJson2 = null;
	private String groupId1 = null;
	private GroupUtils groupUtils = new GroupUtils();
	private String currencyType = null;
	private String groupName = null;
	private Random rand = new Random();
	private String userName = "BudegtUser" + System.currentTimeMillis();
	private EnvSession sessionObj = null;
	private UserHelper userHelper = new UserHelper();
	private ProviderAccountUtils providerAccountUtils = new ProviderAccountUtils();
	private static List<Integer> itemAccountIds = new LinkedList<Integer>();
	private ArrayList<String> listBudgetAmont = new ArrayList<String>();
	private ArrayList<String> arrayOfCatType = new ArrayList<String>();
	private ArrayList<String> goalNameList = new ArrayList<String>();
	private 	SaveForGoalHelper sfg = new SaveForGoalHelper();
	private static String getGoalId;
	private ArrayList<String> listGoalId = new ArrayList<String>();
	private String BudgetAmount1=null;
	SFGUtils sfgUtils = new SFGUtils();
	// Initialization of .CSV file location
	final static String createBudget = "\\TestData\\CSVFiles\\Budget\\CreateBudgetGoal_Test.csv";
	final static String updateBudget1 = "\\TestData\\CSVFiles\\Budget\\UpdateBudgetGoal_Test1.csv";
	final static String updateBudget2 = "\\TestData\\CSVFiles\\Budget\\UpdateBudgetGoal_Test2.csv";
	final static String updateBudget3 = "\\TestData\\CSVFiles\\Budget\\UpdateBudgetGoal_Test3.csv";
	final static String updateBudget4 = "\\TestData\\CSVFiles\\Budget\\UpdateBudgetGoal_Test4.csv";
	final static String updateBudget5 = "\\TestData\\CSVFiles\\Budget\\UpdateBudget_Test5.csv";
	final static String updateBudget6 = "\\TestData\\CSVFiles\\Budget\\UpdateBudget_Test6.csv";
	final static String updateBudget7 = "\\TestData\\CSVFiles\\Budget\\UpdateBudgetGoal_Test7.csv";
	final static String updateBudget8 = "\\TestData\\CSVFiles\\Budget\\UpdateBudgetGoal_Test8.csv";

	
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
	public void createPredefinedSFG(String testCaseId, String testCase, String enabled, String testNo1,
			String catId, String catType, String currType,String Goal,String GoalStartDate
		    ,String GoalEndDate) throws Exception, IOException, JoseException {
		groupName = RandomStringUtils.randomAlphabetic(10);
		groupJson1 = bHelper.createGroupJson(groupName);

		Response GroupResponse = groupUtils.createGroup(groupJson1, sessionObj);
		groupId1 = GroupResponse.jsonPath().getString("group.id");

		categoryId.add(Integer.parseInt(catId.split(",")[rand.nextInt(10)]));
		categoryType.add(catType.split(",")[rand.nextInt(3)]);
		 BudgetAmount1 = String.valueOf(rand.nextInt(1000));
		currencyType = currType.split(",")[rand.nextInt(10)];
		listBudgetAmont.add(BudgetAmount1);
		listBudgetAmont.add(BudgetAmount1);
		arrayOfCatType.add(catType.split(",")[0]);
		arrayOfCatType.add(catType.split(",")[1]);
		String catDataAndCatTypeBodyReqParam = bHelper.CreateCatDataAndCatType(groupId1, arrayOfCatType, categoryId,
				BudgetAmount1, currencyType);

		HttpMethodParameters httpMethodParameters1 = HttpMethodParameters.builder().build();
		httpMethodParameters1.setBodyParams(catDataAndCatTypeBodyReqParam);
		BudgetUtils budgetUtils1 = new BudgetUtils();
		budgetUtils1.createBudget(httpMethodParameters1, sessionObj);
				
	
		
		String bodyParams = null;
		String arrayOfGoalData[]=Goal.split(",");
		
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
		
		int indexOfItemAcntId = Integer.parseInt(arrayOfGoalData[10]);
		createFundingRuleBodyParam = sfg.createFundingRule(itemAccountIds.get(0), arrayOfGoalData[4],
				arrayOfGoalData[5], arrayOfGoalData[6], arrayOfGoalData[11], sfg.sfgDateFormate(GoalStartDate),
				sfg.sfgDateFormate(GoalEndDate));

		httpmethodParam.setPathParams(createFundingRulePathParam);
		httpmethodParam.setBodyParams(createFundingRuleBodyParam);

		Response createFundingRuleResp = sfgUtils.createFundingRule(httpmethodParam, sessionObj);
		Assert.assertTrue(createFundingRuleResp.getStatusCode()==201);
		}
		
	}

	@Test(enabled = true, dataProvider = "feeder", priority = 2,dependsOnMethods="createPredefinedSFG")
	@Source(updateBudget1)
	public void updateBudgetWithValidData(String testCaseId, String testCase, String goalName, String CategoryId,String CategoryIdAmount,
			String CategoryType,String CategoryTypeAmount
      , String CurrencyType,String Goal,String GoalStartDate,String GoalEndDate,
			String CategoryIdUpdate,String CategoryIdAmountUpdate,String CategoryTypeUpdate,String CategoryTypeAmountUpdate,
			String CurrencyTypeUpdate,String GoalUpdate,String ErrorCode,String ExpectedErrorMsg, String Enabled) throws Exception {
		//upadting goal to budget 	//16  17
		
	//	String groupName2 = RandomStringUtils.randomAlphabetic(10);
		HttpMethodParameters groupJson2 = bHelper.createGroupJson(goalName);

		Response GroupResponse = groupUtils.createGroup(groupJson2, sessionObj);
		String groupId2 = GroupResponse.jsonPath().getString("group.id");
		List<String> goalid2=new ArrayList<String>();
		List<String> arrayOfCatType=new ArrayList<String>();
		List<String> catTypeAmt=new ArrayList<String>();
		List<String> goalid2Update=new ArrayList<String>();
		List<String> arrayOfCatTypeUpdate=new ArrayList<String>();
		List<String> catTypeAmtUpdate=new ArrayList<String>();

		if (!CategoryType.isEmpty()) {
		arrayOfCatType =Arrays.asList(CategoryType.split(","));
		catTypeAmt=Arrays.asList(CategoryTypeAmount.split(","));	
		arrayOfCatTypeUpdate =Arrays.asList(CategoryTypeUpdate.split(","));
		catTypeAmtUpdate=Arrays.asList(CategoryTypeAmountUpdate.split(","));
		}
		List<Integer> catid=new ArrayList<Integer>();
		List<String> catidAmount=new ArrayList<String>();
		List<Integer> catidUpdate=new ArrayList<Integer>();
		List<String> catidAmountUpdate=new ArrayList<String>();
		if (!CategoryId.isEmpty()) 
		{
			catid = Stream.of(CategoryId.split(",")).map(Integer::parseInt).collect(Collectors.toList());
			catidAmount = Arrays.asList(CategoryIdAmount.split(","));
			if (!CategoryIdUpdate.isEmpty()) {
			catidUpdate = Stream.of(CategoryIdUpdate.split(",")).map(Integer::parseInt).collect(Collectors.toList());
			catidAmountUpdate = Arrays.asList(CategoryIdAmountUpdate.split(","));
			}


		}
		goalid2.add(listGoalId.get(0));
		String catDataAndCatTypeBodyReqParam=null;
		if (!Goal.isEmpty()) 
		{
			if(!CategoryId.isEmpty()&&CategoryType.isEmpty()) {
				 catDataAndCatTypeBodyReqParam = bHelper.CreateBudgetAtCatDataGoal(groupId2, catid, catidAmount, CurrencyType,goalid2);

			}
			else if(CategoryId.isEmpty()&& !CategoryType.isEmpty()){
				 catDataAndCatTypeBodyReqParam = bHelper.CreateBudgetAtCatTypeGoal(groupId2, arrayOfCatType, catTypeAmt, CurrencyType,goalid2.get(0));

			}
			else if (!CategoryId.isEmpty()&& !CategoryType.isEmpty()){
			catDataAndCatTypeBodyReqParam = bHelper.CreateCatDataAndCatTypeGoal(groupId2, arrayOfCatType, catid,
					CategoryIdAmount, CurrencyType,goalid2);
			}
		}
		else {
		 catDataAndCatTypeBodyReqParam = bHelper.CreateCatDataAndCatType(groupId2, arrayOfCatType, catid,
				CategoryIdAmount, CurrencyType);
		}

		HttpMethodParameters httpMethodParameters1 = HttpMethodParameters.builder().build();
		httpMethodParameters1.setBodyParams(catDataAndCatTypeBodyReqParam);
		BudgetUtils budgetUtils1 = new BudgetUtils();
		Response budgetresp =budgetUtils1.createBudget(httpMethodParameters1, sessionObj);
		int budgetId=budgetresp.jsonPath().get("budgetGroups.id");
		String catDataBodyReqParam=null;
		if (!GoalUpdate.isEmpty()) {
			goalid2.clear();
			goalid2.add(GoalUpdate);
		}
		
		
		if(!CategoryIdUpdate.isEmpty()&&CategoryTypeUpdate.isEmpty()) {
			catDataBodyReqParam = bHelper.UpdateBudgetAtCatDataGoal(groupId2,catidUpdate, catidAmountUpdate,CurrencyTypeUpdate,goalid2);

		}
		else if(CategoryIdUpdate.isEmpty()&& !CategoryTypeUpdate.isEmpty() ){
			catDataBodyReqParam = bHelper.UpdateBudgetAtCatTypeGoal(groupId2, arrayOfCatTypeUpdate, catTypeAmtUpdate, CurrencyType,goalid2);

		}
		else if (!CategoryIdUpdate.isEmpty()&& !CategoryTypeUpdate.isEmpty()){
			catDataBodyReqParam = bHelper.updateCatDataAndCatTypeGoal(groupId2,arrayOfCatTypeUpdate, catidUpdate,CategoryIdAmountUpdate, catTypeAmtUpdate, CurrencyTypeUpdate,goalid2);

		}
		else{
			catDataBodyReqParam = bHelper.updateBudgetWithOnlyGOalId("",goalid2);
		}
		
	
		//String catDataBodyReqParam = bHelper.updateCatDataAndCatTypeGoal(groupId2,arrayOfCatTypeUpdate, catidUpdate,CategoryIdAmountUpdate, catTypeAmtUpdate, CurrencyTypeUpdate,goalid2);
        HttpMethodParameters httpMethodParameters = HttpMethodParameters.builder().build();
		httpMethodParameters.setBodyParams(catDataBodyReqParam);
        Response updateResponseForCatdata = budgetUtils.updateBudget(httpMethodParameters,
				sessionObj);
        if(updateResponseForCatdata.getStatusCode()!=204) {
			JSONObject Obj = new JSONObject(updateResponseForCatdata.asString());
			Assert.assertEquals(ErrorCode, Obj.get("errorCode"));
			Assert.assertEquals(ExpectedErrorMsg, Obj.get("errorMessage"));	
		}else
        {
			 
        	bHelper.updateBudgetDbVerification(userName, budgetId, arrayOfCatTypeUpdate, catidUpdate, goalid2,catTypeAmtUpdate,catidAmountUpdate,CurrencyTypeUpdate);
			 
			 }

		}

	@Test(enabled = true, dataProvider = "feeder",priority = 3,dependsOnMethods="createPredefinedSFG")
	@Source(updateBudget2)
	public void updateBudgetWithCategoryIDData(String testCaseId, String testCase, String goalName, String CategoryId,String CategoryIdAmount,
			String CategoryType,String CategoryTypeAmount
, String CurrencyType,String Goal,String GoalStartDate,String GoalEndDate,
			String CategoryIdUpdate,String CategoryIdAmountUpdate,String CategoryTypeUpdate,String CategoryTypeAmountUpdate,
			String CurrencyTypeUpdate,String GoalUpdate,String ErrorCode,String ExpectedErrorMsg, String Enabled) throws Exception {
		//update with only categoryData and goalId test case 18
	
		HttpMethodParameters groupJson2 = bHelper.createGroupJson(goalName);

		Response GroupResponse = groupUtils.createGroup(groupJson2, sessionObj);
		String groupId2 = GroupResponse.jsonPath().getString("group.id");
		List<String> goalid2=new ArrayList<String>();
		List<String> arrayOfCatType=new ArrayList<String>();
		List<String> catTypeAmt=new ArrayList<String>();
		List<String> goalid2Update=new ArrayList<String>();
		List<String> arrayOfCatTypeUpdate=new ArrayList<String>();
		List<String> catTypeAmtUpdate=new ArrayList<String>();

		if (!CategoryType.isEmpty()) {
		arrayOfCatType =Arrays.asList(CategoryType.split(","));
		catTypeAmt=Arrays.asList(CategoryTypeAmount.split(","));	
		arrayOfCatTypeUpdate =Arrays.asList(CategoryTypeUpdate.split(","));
		catTypeAmtUpdate=Arrays.asList(CategoryTypeAmountUpdate.split(","));
		}
		List<Integer> catid=new ArrayList<Integer>();
		List<String> catidAmount=new ArrayList<String>();
		List<Integer> catidUpdate=new ArrayList<Integer>();
		List<String> catidAmountUpdate=new ArrayList<String>();
		
			if (!CategoryId.isEmpty()) {
			catid = Stream.of(CategoryId.split(",")).map(Integer::parseInt).collect(Collectors.toList());
			catidAmount = Arrays.asList(CategoryIdAmount.split(","));
			}
			
			if (!CategoryIdUpdate.isEmpty()) {
			catidUpdate = Stream.of(CategoryIdUpdate.split(",")).map(Integer::parseInt).collect(Collectors.toList());
			catidAmountUpdate = Arrays.asList(CategoryIdAmountUpdate.split(","));
			}


		
		goalid2.add(listGoalId.get(0));
		String catDataAndCatTypeBodyReqParam=null;
		if (!Goal.isEmpty()) 
		{
			if(!CategoryId.isEmpty()&&CategoryType.isEmpty()) {
				 catDataAndCatTypeBodyReqParam = bHelper.CreateBudgetAtCatDataGoal(groupId2, catid, catidAmount, CurrencyType,goalid2);

			}
			else if(CategoryId.isEmpty()&& !CategoryType.isEmpty()){
				 catDataAndCatTypeBodyReqParam = bHelper.CreateBudgetAtCatTypeGoal(groupId2, arrayOfCatType, catTypeAmt, CurrencyType,goalid2.get(0));

			}
			else if (!CategoryId.isEmpty()&& !CategoryType.isEmpty()){
			catDataAndCatTypeBodyReqParam = bHelper.CreateCatDataAndCatTypeGoal(groupId2, arrayOfCatType, catid,
					CategoryIdAmount, CurrencyType,goalid2);
			}
		}
		else {
		 catDataAndCatTypeBodyReqParam = bHelper.CreateCatDataAndCatType(groupId2, arrayOfCatType, catid,
				CategoryIdAmount, CurrencyType);
		}

		HttpMethodParameters httpMethodParameters1 = HttpMethodParameters.builder().build();
		httpMethodParameters1.setBodyParams(catDataAndCatTypeBodyReqParam);
		BudgetUtils budgetUtils1 = new BudgetUtils();
		Response budgetresp =budgetUtils1.createBudget(httpMethodParameters1, sessionObj);
		int budgetId=budgetresp.jsonPath().get("budgetGroups.id");
		String catDataBodyReqParam=null;
		if (!GoalUpdate.isEmpty()) {
			goalid2.clear();
			goalid2.add(GoalUpdate);
		}
		else {
			goalid2.add(listGoalId.get(1));
		}
		
		
		if(!CategoryIdUpdate.isEmpty()&&CategoryTypeUpdate.isEmpty()) {
			catDataBodyReqParam = bHelper.UpdateBudgetAtCatDataGoal(groupId2,catidUpdate, catidAmountUpdate,CurrencyTypeUpdate,goalid2);

		}
		else if(CategoryIdUpdate.isEmpty()&& !CategoryTypeUpdate.isEmpty() ){
			catDataBodyReqParam = bHelper.UpdateBudgetAtCatTypeGoal(groupId2, arrayOfCatTypeUpdate, catTypeAmtUpdate, CurrencyType,goalid2);

		}
		else if (!CategoryIdUpdate.isEmpty()&& !CategoryTypeUpdate.isEmpty()){
			catDataBodyReqParam = bHelper.updateCatDataAndCatTypeGoal(groupId2,arrayOfCatTypeUpdate, catidUpdate,CategoryIdAmountUpdate, catTypeAmtUpdate, CurrencyTypeUpdate,goalid2);

		}
		else{
			catDataBodyReqParam = bHelper.updateBudgetWithOnlyGOalId(groupId1,goalid2);
		}
		
	
		//String catDataBodyReqParam = bHelper.updateCatDataAndCatTypeGoal(groupId2,arrayOfCatTypeUpdate, catidUpdate,CategoryIdAmountUpdate, catTypeAmtUpdate, CurrencyTypeUpdate,goalid2);
        HttpMethodParameters httpMethodParameters = HttpMethodParameters.builder().build();
		httpMethodParameters.setBodyParams(catDataBodyReqParam);
        Response updateResponseForCatdata = budgetUtils.updateBudget(httpMethodParameters,
				sessionObj);
        if(updateResponseForCatdata.getStatusCode()!=204) {
			JSONObject Obj = new JSONObject(updateResponseForCatdata.asString());
			Assert.assertEquals(ErrorCode, Obj.get("errorCode"));
			Assert.assertEquals(ExpectedErrorMsg, Obj.get("errorMessage"));	
		}else
        {
			 
        	bHelper.updateBudgetDbVerification(userName, budgetId, arrayOfCatTypeUpdate, catidUpdate, goalid2,catTypeAmtUpdate,catidAmountUpdate,CurrencyTypeUpdate);
			 
			 }
		
	}
	@Test(enabled = true, dataProvider = "feeder",priority = 4,dependsOnMethods="createPredefinedSFG")
	@Source(updateBudget3)
	public void updateBudgetWithCategoryTypeData(String testCaseId, String testCase, String goalName, String CategoryId,String CategoryIdAmount,
			String CategoryType,String CategoryTypeAmount
, String CurrencyType,String Goal,String GoalStartDate,String GoalEndDate,
			String CategoryIdUpdate,String CategoryIdAmountUpdate,String CategoryTypeUpdate,String CategoryTypeAmountUpdate,
			String CurrencyTypeUpdate,String GoalUpdate,String ErrorCode,String ExpectedErrorMsg, String Enabled) throws Exception {
	//18

		HttpMethodParameters groupJson2 = bHelper.createGroupJson(goalName);

		Response GroupResponse = groupUtils.createGroup(groupJson2, sessionObj);
		String groupId2 = GroupResponse.jsonPath().getString("group.id");
		List<String> goalid2=new ArrayList<String>();
		List<String> arrayOfCatType=new ArrayList<String>();
		List<String> catTypeAmt=new ArrayList<String>();
		List<String> goalid2Update=new ArrayList<String>();
		List<String> arrayOfCatTypeUpdate=new ArrayList<String>();
		List<String> catTypeAmtUpdate=new ArrayList<String>();

		if (!CategoryType.isEmpty()) {
		arrayOfCatType =Arrays.asList(CategoryType.split(","));
		catTypeAmt=Arrays.asList(CategoryTypeAmount.split(","));	
		arrayOfCatTypeUpdate =Arrays.asList(CategoryTypeUpdate.split(","));
		catTypeAmtUpdate=Arrays.asList(CategoryTypeAmountUpdate.split(","));
		}
		List<Integer> catid=new ArrayList<Integer>();
		List<String> catidAmount=new ArrayList<String>();
		List<Integer> catidUpdate=new ArrayList<Integer>();
		List<String> catidAmountUpdate=new ArrayList<String>();
		if (!CategoryId.isEmpty()) 
		{
			catid = Stream.of(CategoryId.split(",")).map(Integer::parseInt).collect(Collectors.toList());
			catidAmount = Arrays.asList(CategoryIdAmount.split(","));
			if (!CategoryIdUpdate.isEmpty()) {
			catidUpdate = Stream.of(CategoryIdUpdate.split(",")).map(Integer::parseInt).collect(Collectors.toList());
			catidAmountUpdate = Arrays.asList(CategoryIdAmountUpdate.split(","));
			}


		}
		goalid2.add(listGoalId.get(0));
		String catDataAndCatTypeBodyReqParam=null;
		if (!Goal.isEmpty()) 
		{
			if(!CategoryId.isEmpty()&&CategoryType.isEmpty()) {
				 catDataAndCatTypeBodyReqParam = bHelper.CreateBudgetAtCatDataGoal(groupId2, catid, catidAmount, CurrencyType,goalid2);

			}
			else if(CategoryId.isEmpty()&& !CategoryType.isEmpty()){
				 catDataAndCatTypeBodyReqParam = bHelper.CreateBudgetAtCatTypeGoal(groupId2, arrayOfCatType, catTypeAmt, CurrencyType,goalid2.get(0));

			}
			else if (!CategoryId.isEmpty()&& !CategoryType.isEmpty()){
			catDataAndCatTypeBodyReqParam = bHelper.CreateCatDataAndCatTypeGoal(groupId2, arrayOfCatType, catid,
					CategoryIdAmount, CurrencyType,goalid2);
			}
		}
		else {
		 catDataAndCatTypeBodyReqParam = bHelper.CreateCatDataAndCatType(groupId2, arrayOfCatType, catid,
				CategoryIdAmount, CurrencyType);
		}
		goalid2.add(listGoalId.get(1));

		HttpMethodParameters httpMethodParameters1 = HttpMethodParameters.builder().build();
		httpMethodParameters1.setBodyParams(catDataAndCatTypeBodyReqParam);
		BudgetUtils budgetUtils1 = new BudgetUtils();
		Response budgetresp =budgetUtils1.createBudget(httpMethodParameters1, sessionObj);
		int budgetId=budgetresp.jsonPath().get("budgetGroups.id");
		String catDataBodyReqParam=null;
		catDataBodyReqParam = bHelper.updateBudgetWithOnlyGOalId(groupId1,goalid2);
	    HttpMethodParameters httpMethodParameters = HttpMethodParameters.builder().build();
	httpMethodParameters.setBodyParams(catDataBodyReqParam);
    Response updateResponseForCatdata = budgetUtils.updateBudget(httpMethodParameters,
			sessionObj);
	Assert.assertTrue(updateResponseForCatdata.getStatusCode()==204);

	bHelper.updateBudgetDbVerification(userName, budgetId,goalid2);

   
    

	}

	@Test(enabled = true, dataProvider = "feeder", priority = 5,dependsOnMethods="createPredefinedSFG")
	@Source(updateBudget4)
	public void updateBudgetWithOnlyGoalID(String testCaseId, String testCase, String goalName, String CategoryId,String CategoryIdAmount,
			String CategoryType,String CategoryTypeAmount
, String CurrencyType,String Goal,String GoalStartDate,String GoalEndDate,
			String CategoryIdUpdate,String CategoryIdAmountUpdate,String CategoryTypeUpdate,String CategoryTypeAmountUpdate,
			String CurrencyTypeUpdate,String GoalUpdate,String ErrorCode,String ExpectedErrorMsg, String Enabled) throws Exception {
		
		HttpMethodParameters groupJson2 = bHelper.createGroupJson(goalName);

		Response GroupResponse = groupUtils.createGroup(groupJson2, sessionObj);
		String groupId2 = GroupResponse.jsonPath().getString("group.id");
		List<String> goalid2=new ArrayList<String>();
		List<String> arrayOfCatType=new ArrayList<String>();
		List<String> catTypeAmt=new ArrayList<String>();
		List<String> goalid2Update=new ArrayList<String>();
		List<String> arrayOfCatTypeUpdate=new ArrayList<String>();
		List<String> catTypeAmtUpdate=new ArrayList<String>();

		if (!CategoryType.isEmpty()) {
		arrayOfCatType =Arrays.asList(CategoryType.split(","));
		catTypeAmt=Arrays.asList(CategoryTypeAmount.split(","));	
		arrayOfCatTypeUpdate =Arrays.asList(CategoryTypeUpdate.split(","));
		catTypeAmtUpdate=Arrays.asList(CategoryTypeAmountUpdate.split(","));
		}
		List<Integer> catid=new ArrayList<Integer>();
		List<String> catidAmount=new ArrayList<String>();
		List<Integer> catidUpdate=new ArrayList<Integer>();
		List<String> catidAmountUpdate=new ArrayList<String>();
		if (!CategoryId.isEmpty()) 
		{
			catid = Stream.of(CategoryId.split(",")).map(Integer::parseInt).collect(Collectors.toList());
			catidAmount = Arrays.asList(CategoryIdAmount.split(","));
			if (!CategoryIdUpdate.isEmpty()) {
			catidUpdate = Stream.of(CategoryIdUpdate.split(",")).map(Integer::parseInt).collect(Collectors.toList());
			catidAmountUpdate = Arrays.asList(CategoryIdAmountUpdate.split(","));
			}


		}
		goalid2.add(listGoalId.get(0));
		String catDataAndCatTypeBodyReqParam=null;
		if (!Goal.isEmpty()) 
		{
			if(!CategoryId.isEmpty()&&CategoryType.isEmpty()) {
				 catDataAndCatTypeBodyReqParam = bHelper.CreateBudgetAtCatDataGoal(groupId2, catid, catidAmount, CurrencyType,goalid2);

			}
			else if(CategoryId.isEmpty()&& !CategoryType.isEmpty()){
				 catDataAndCatTypeBodyReqParam = bHelper.CreateBudgetAtCatTypeGoal(groupId2, arrayOfCatType, catTypeAmt, CurrencyType,goalid2.get(0));

			}
			else if (!CategoryId.isEmpty()&& !CategoryType.isEmpty()){
			catDataAndCatTypeBodyReqParam = bHelper.CreateCatDataAndCatTypeGoal(groupId2, arrayOfCatType, catid,
					CategoryIdAmount, CurrencyType,goalid2);
			}
		}
		else {
		 catDataAndCatTypeBodyReqParam = bHelper.CreateCatDataAndCatType(groupId2, arrayOfCatType, catid,
				CategoryIdAmount, CurrencyType);
		}
		goalid2.add(listGoalId.get(0));

		HttpMethodParameters httpMethodParameters1 = HttpMethodParameters.builder().build();
		httpMethodParameters1.setBodyParams(catDataAndCatTypeBodyReqParam);
		BudgetUtils budgetUtils1 = new BudgetUtils();
		Response budgetresp =budgetUtils1.createBudget(httpMethodParameters1, sessionObj);
		int budgetId=budgetresp.jsonPath().get("budgetGroups.id");
		String catDataBodyReqParam=null;
		if(!CategoryIdUpdate.isEmpty()&&CategoryTypeUpdate.isEmpty()) {
			catDataBodyReqParam = bHelper.UpdateBudgetAtCatDataGoal(groupId2,catidUpdate, catidAmountUpdate,CurrencyTypeUpdate,goalid2);

		}
		else if(CategoryIdUpdate.isEmpty()&& !CategoryTypeUpdate.isEmpty() ){
			catDataBodyReqParam = bHelper.UpdateBudgetAtCatTypeGoal(groupId2, arrayOfCatTypeUpdate, catTypeAmtUpdate, CurrencyType,goalid2);

		}
		else if (!CategoryIdUpdate.isEmpty()&& !CategoryTypeUpdate.isEmpty()){
			catDataBodyReqParam = bHelper.updateCatDataAndCatTypeGoal(groupId2,arrayOfCatTypeUpdate, catidUpdate,CategoryIdAmountUpdate, catTypeAmtUpdate, CurrencyTypeUpdate,goalid2);

		}
		else{
			catDataBodyReqParam = bHelper.updateBudgetWithOnlyGOalId(groupId1,goalid2);
		}
	    HttpMethodParameters httpMethodParameters = HttpMethodParameters.builder().build();
	httpMethodParameters.setBodyParams(catDataBodyReqParam);
    Response updateResponseForCatdata = budgetUtils.updateBudget(httpMethodParameters,
			sessionObj);
    JSONObject Obj = new JSONObject(updateResponseForCatdata.asString());
	Assert.assertEquals(ErrorCode, Obj.get("errorCode"));
	Assert.assertEquals(ExpectedErrorMsg, Obj.get("errorMessage"));	
	
	
		

	}
	}