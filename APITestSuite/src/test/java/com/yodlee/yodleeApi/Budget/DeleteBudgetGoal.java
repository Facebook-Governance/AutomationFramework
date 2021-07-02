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

import static org.testng.Assert.assertEquals;

import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;


import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.yodlee.yodleeApi.common.Base;
import com.yodlee.yodleeApi.common.Configuration;

import com.yodlee.yodleeApi.constants.JSONPaths;
import com.yodlee.yodleeApi.constants.JsonPath;

import com.yodlee.yodleeApi.pojo.EnvSession;
import com.yodlee.yodleeApi.pojo.HttpMethodParameters;
import com.yodlee.yodleeApi.pojo.User;

import com.yodlee.yodleeApi.utils.apiUtils.BudgetUtils;
import com.yodlee.yodleeApi.utils.apiUtils.GroupUtils;
import com.yodlee.yodleeApi.utils.apiUtils.ProviderAccountUtils;
import com.yodlee.yodleeApi.utils.apiUtils.SFGUtils;


import org.apache.commons.lang.RandomStringUtils;
import org.databene.benerator.anno.Source;
import org.jose4j.lang.JoseException;

import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;


import io.restassured.response.Response;

public class DeleteBudgetGoal extends Base {


	
	public static Long providerAccountId = null;
	

	// ArrayList initialization
	
	private Configuration config = Configuration.getInstance();

	// Hash Map deceleration

	// Soft assertion object initialization
	
	private BudgetUtils budgetUtils = new BudgetUtils();
	private BudgetHelper bHelper = new BudgetHelper();


	private GroupUtils groupUtils = new GroupUtils();

	private String arrayOfGoalData[]=null;
	private String userName = "BudegtUser" + System.currentTimeMillis();
//	String  userName="Myenb1298";
	private EnvSession sessionObj = null;
	private UserHelper userHelper = new UserHelper();
	private ProviderAccountUtils providerAccountUtils = new ProviderAccountUtils();
	private static List<Integer> itemAccountIds = new LinkedList<Integer>();
	private ArrayList<String> goalNameList = new ArrayList<String>();
	
	private SaveForGoalHelper sfg = new SaveForGoalHelper();
	static String getGoalId;
	private ArrayList<String> listGoalId = new ArrayList<String>();
	
	// Initialization of .CSV file location
	final static String createBudget = "\\TestData\\CSVFiles\\Budget\\CreateBudgetGoal_Test.csv";
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
	public void createBudgetPredefinedSFG(String testCaseId, String testCase, String enabled, String testNo1,
			String catId, String catType, String currType,String Goal,String GoalStartDate
		    ,String GoalEndDate) throws Exception, IOException, JoseException {
		
				
		SFGUtils sfgUtils = new SFGUtils();
		
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
		
		int indexOfItemAcntId = Integer.parseInt(arrayOfGoalData[10]);
		createFundingRuleBodyParam = sfg.createFundingRule(itemAccountIds.get(0), arrayOfGoalData[4],
				arrayOfGoalData[5], arrayOfGoalData[6], arrayOfGoalData[11], sfg.sfgDateFormate(GoalStartDate),
				sfg.sfgDateFormate(GoalEndDate));

		httpmethodParam.setPathParams(createFundingRulePathParam);
		httpmethodParam.setBodyParams(createFundingRuleBodyParam);

		Response createFundingRuleResp = sfgUtils.createFundingRule(httpmethodParam, sessionObj);
		Assert.assertTrue(createFundingRuleResp.getStatusCode()==201);

		}}
		
	@Test(enabled = true, dataProvider = "feeder", priority = 2,dependsOnMethods="createBudgetPredefinedSFG")
	@Source("\\TestData\\CSVFiles\\Budget\\DeleteBudgetGoal.csv")
	public void deleteBudgetWithValidId(String TestCaseId, String TestCase, String groupName, String CategoryType,
			String CategoryId, String Amount, String Currency, String CategoryIdQueryParam,
			String CategoryTypeQueryParam, String ContainerTypeId,String ErrorCode,String ExpectedErrorMsg,String ExpectedCategoryType,String ExpectedCategoryId,String GoalId,String Enabled
     ) {
	
     int budgetId=0;
		List<Integer> categoryIds = null;
	if (CategoryId != null && !CategoryId.isEmpty()) {
			categoryIds = Stream.of(CategoryId.split(",")).map(Integer::parseInt).collect(Collectors.toList());
		}

		List<String> categoryTypes = null;
		if (CategoryType != null && !CategoryType.isEmpty()) {
			categoryTypes = Arrays.asList(CategoryType.split(","));
		}

		List<Integer> itemAccountId = bHelper.getItemAccountIds(userName, ContainerTypeId);

		HttpMethodParameters bodyParam = BudgetHelper.createGroupBodyParam(groupName, itemAccountId);
		Response groupResponse = groupUtils.createGroup(bodyParam, sessionObj);
		String groupId = groupResponse.jsonPath().getString(JsonPath.budgetGroupId);

		
		HttpMethodParameters httpMethodParameters = HttpMethodParameters.builder().build();
      String goaliddelete=null;
      String goaliddelete2=null;
		if (CategoryType.isEmpty()) {
			List<String> amt=new ArrayList<String>();
			List<String> goalId=new ArrayList<String>();
			for (int i = 0; i < categoryIds.size(); i++) {
				amt.add(Amount);
			}
			goalId.add(listGoalId.get(1));
			goaliddelete=listGoalId.get(1);
			String catIdBodyReqParam = bHelper.CreateBudgetAtCatDataGoal(groupId, categoryIds, amt, Currency,goalId);
			httpMethodParameters.setBodyParams(catIdBodyReqParam);
			Response bdget=budgetUtils.createBudget(httpMethodParameters, sessionObj);
			budgetId=bdget.jsonPath().get("budgetGroups.id");
		} else if (CategoryId.isEmpty()) {
			List<String> amt=new ArrayList<String>();
			for (int i = 0; i < categoryTypes.size(); i++) {
				amt.add(Amount);
			}
			
			goaliddelete=listGoalId.get(0);
			String catTypeBodyReqParam = bHelper.CreateBudgetAtCatTypeGoal(groupId, categoryTypes, amt, Currency,listGoalId.get(0));
			httpMethodParameters.setBodyParams(catTypeBodyReqParam);
			Response bdget=budgetUtils.createBudget(httpMethodParameters, sessionObj);
			budgetId=bdget.jsonPath().get("budgetGroups.id");
		} else {
			List<String> goalId=new ArrayList<String>();
			goalId.add(listGoalId.get(0));
			goalId.add(listGoalId.get(1));
			goaliddelete=listGoalId.get(0);
			goaliddelete2=listGoalId.get(1);
			String catDataAndCatTypeBodyReqParam = bHelper.CreateCatDataAndCatTypeGoal(groupId, categoryTypes,
					categoryIds, Amount, Currency,goalId);
			httpMethodParameters.setBodyParams(catDataAndCatTypeBodyReqParam);
			Response bdget=budgetUtils.createBudget(httpMethodParameters, sessionObj);
			budgetId=bdget.jsonPath().get("budgetGroups.id");
		}

		HttpMethodParameters httpMethodParameters1 = HttpMethodParameters.builder().build();
		HashMap<String, Object> getGoalQueryParam = new HashMap<>();
		getGoalQueryParam.put("groupId", groupId);

		Response deletBudgetResp=null;
		if (CategoryIdQueryParam.isEmpty() && CategoryTypeQueryParam.isEmpty()) {
			if (!CategoryType.isEmpty() && !CategoryId.isEmpty())
			{
				 goaliddelete= goaliddelete+","+goaliddelete2;
				getGoalQueryParam.put("goalId",goaliddelete);
				httpMethodParameters1.setQueryParams(getGoalQueryParam);
				 deletBudgetResp=budgetUtils.deleteBudgetGoal(httpMethodParameters1, sessionObj);
			}else {
				
				 if (!GoalId.isEmpty()) { goaliddelete=GoalId; }
				
			getGoalQueryParam.put("goalId", goaliddelete);
			httpMethodParameters1.setQueryParams(getGoalQueryParam);
			 deletBudgetResp=budgetUtils.deleteBudgetGoal(httpMethodParameters1, sessionObj);
			}
		}
		else if (CategoryIdQueryParam.isEmpty()) {
			getGoalQueryParam.put("categoryType", CategoryTypeQueryParam);
			httpMethodParameters1.setQueryParams(getGoalQueryParam);
			 deletBudgetResp=budgetUtils.deleteBudgetCategoryType(httpMethodParameters1, sessionObj);
		} else if (CategoryTypeQueryParam.isEmpty()) {
			getGoalQueryParam.put("categoryId", CategoryIdQueryParam);
			httpMethodParameters1.setQueryParams(getGoalQueryParam);
			 deletBudgetResp=budgetUtils.deleteBudgetCategory(httpMethodParameters1, sessionObj);
		} 

		

		if(deletBudgetResp.getStatusCode()!=204) {
			JSONObject Obj = new JSONObject(deletBudgetResp.asString());
			Assert.assertEquals(ErrorCode, Obj.get("errorCode"));
			Assert.assertEquals(ExpectedErrorMsg, Obj.get("errorMessage"));	
		}else {
			assertEquals(deletBudgetResp.getStatusCode(),204);
				bHelper.deleteBudgetDbVerification(userName, budgetId,CategoryTypeQueryParam,CategoryIdQueryParam,goaliddelete,false);
			
		 
			
		}
	
		
		
		
	}
	@Test(enabled = true, dataProvider = "feeder", priority = 3,dependsOnMethods="createBudgetPredefinedSFG")
	@Source("\\TestData\\CSVFiles\\Budget\\DeleteBudgetGoal2.csv")
	public void deleteBudgetWithInvalidGoalId(String TestCaseId, String TestCase, String groupName, String CategoryType,
			String CategoryId, String Amount, String Currency, String CategoryIdQueryParam,String GroupIdQueryParam,
			String CategoryTypeQueryParam, String ContainerTypeId,String ErrorCode,String ExpectedErrorMsg,String ExpectedCategoryType,String ExpectedCategoryId,String GoalId,String Enabled) {
		
		
         int budgetId=0;
		List<Integer> categoryIds = null;
		if (CategoryId != null && !CategoryId.isEmpty()) {
			categoryIds = Stream.of(CategoryId.split(",")).map(Integer::parseInt).collect(Collectors.toList());
		}

		List<String> categoryTypes = null;
		if (CategoryType != null && !CategoryType.isEmpty()) {
			categoryTypes = Arrays.asList(CategoryType.split(","));
		}

		List<Integer> itemAccountId = bHelper.getItemAccountIds(userName, ContainerTypeId);

		HttpMethodParameters bodyParam = BudgetHelper.createGroupBodyParam(groupName, itemAccountId);
		Response groupResponse = groupUtils.createGroup(bodyParam, sessionObj);
		String groupId = groupResponse.jsonPath().getString(JsonPath.budgetGroupId);

		if(!GroupIdQueryParam.isEmpty()) {
			groupId=GroupIdQueryParam;	
		}
		HttpMethodParameters httpMethodParameters = HttpMethodParameters.builder().build();
      String goaliddelete=null;
      String goaliddelete2=null;
		if (CategoryType.isEmpty()) {
			List<String> amt=new ArrayList<String>();
			List<String> goalId=new ArrayList<String>();
			amt.add(Amount);
			goalId.add(listGoalId.get(1));
			goaliddelete=listGoalId.get(1);
			String catIdBodyReqParam = bHelper.CreateBudgetAtCatDataGoal(groupId, categoryIds, amt, Currency,goalId);
			httpMethodParameters.setBodyParams(catIdBodyReqParam);
			Response bdget=budgetUtils.createBudget(httpMethodParameters, sessionObj);
			if(GroupIdQueryParam.isEmpty()) {
			budgetId=bdget.jsonPath().get("budgetGroups.id");
			}
		} else if (CategoryId.isEmpty()) {
			List<String> amt=new ArrayList<String>();
			amt.add(Amount);
			goaliddelete=listGoalId.get(0);
			String catTypeBodyReqParam = bHelper.CreateBudgetAtCatTypeGoal(groupId, categoryTypes, amt, Currency,listGoalId.get(0));
			httpMethodParameters.setBodyParams(catTypeBodyReqParam);
			Response bdget=budgetUtils.createBudget(httpMethodParameters, sessionObj);
			if(GroupIdQueryParam.isEmpty()) {
			budgetId=bdget.jsonPath().get("budgetGroups.id");
			}
		} else {
			List<String> goalId=new ArrayList<String>();
			goalId.add(listGoalId.get(1));
			goaliddelete=listGoalId.get(0);
			goaliddelete2=listGoalId.get(1);
			String catDataAndCatTypeBodyReqParam = bHelper.CreateCatDataAndCatTypeGoal(groupId, categoryTypes,
					categoryIds, Amount, Currency,goalId);
			httpMethodParameters.setBodyParams(catDataAndCatTypeBodyReqParam);
			Response bdget=budgetUtils.createBudget(httpMethodParameters, sessionObj);
			if(GroupIdQueryParam.isEmpty()) {
			budgetId=bdget.jsonPath().get("budgetGroups.id");
			}
		}

		HttpMethodParameters httpMethodParameters1 = HttpMethodParameters.builder().build();
		HashMap<String, Object> getGoalQueryParam = new HashMap<>();
		getGoalQueryParam.put("groupId", groupId);

		Response deletBudgetResp=null;
		if (CategoryIdQueryParam.isEmpty()&& !CategoryTypeQueryParam.isEmpty()) {
			getGoalQueryParam.put("categoryType", CategoryTypeQueryParam);
			httpMethodParameters1.setQueryParams(getGoalQueryParam);
			 deletBudgetResp=budgetUtils.deleteBudgetCategoryType(httpMethodParameters1, sessionObj);
		} else if (!CategoryTypeQueryParam.isEmpty() && CategoryIdQueryParam.isEmpty()) {
			getGoalQueryParam.put("categoryId", CategoryIdQueryParam);
			httpMethodParameters1.setQueryParams(getGoalQueryParam);
			 deletBudgetResp=budgetUtils.deleteBudgetCategory(httpMethodParameters1, sessionObj);
		} 
		else {
			
			httpMethodParameters1.setQueryParams(getGoalQueryParam);
			 deletBudgetResp=budgetUtils.deleteBudget(httpMethodParameters1, sessionObj);
		}

		

		if(deletBudgetResp.getStatusCode()!=204) {
			JSONObject Obj = new JSONObject(deletBudgetResp.asString());
			Assert.assertEquals(ErrorCode, Obj.get("errorCode"));
			Assert.assertEquals(ExpectedErrorMsg, Obj.get("errorMessage"));	
		}else {
			assertEquals(deletBudgetResp.getStatusCode(),204);
				bHelper.deleteBudgetDbVerification(userName, budgetId,CategoryType,CategoryId,goaliddelete,true);
			
			
		}
		
		
		
		
	}
	
	@Test(enabled = true, dataProvider = "feeder", priority = 4,dependsOnMethods="createBudgetPredefinedSFG")
	@Source("\\TestData\\CSVFiles\\Budget\\DeleteBudgetGoal3.csv")
	public void deleteBudgetMultipletimes(String TestCaseId, String TestCase, String groupName, String CategoryType,
			String CategoryId, String Amount, String Currency, String CategoryIdQueryParam,String GroupIdQueryParam,
			String CategoryTypeQueryParam, String ContainerTypeId,String ErrorCode,String ExpectedErrorMsg,String ExpectedCategoryType,String ExpectedCategoryId,String GoalId,String Enabled) {
		
		  int budgetId=0;
			List<Integer> categoryIds = null;
		if (CategoryId != null && !CategoryId.isEmpty()) {
				categoryIds = Stream.of(CategoryId.split(",")).map(Integer::parseInt).collect(Collectors.toList());
			}

			List<String> categoryTypes = null;
			if (CategoryType != null && !CategoryType.isEmpty()) {
				categoryTypes = Arrays.asList(CategoryType.split(","));
			}

			List<Integer> itemAccountId = bHelper.getItemAccountIds(userName, ContainerTypeId);

			HttpMethodParameters bodyParam = BudgetHelper.createGroupBodyParam(groupName, itemAccountId);
			Response groupResponse = groupUtils.createGroup(bodyParam, sessionObj);
			String groupId = groupResponse.jsonPath().getString(JsonPath.budgetGroupId);

			
			HttpMethodParameters httpMethodParameters = HttpMethodParameters.builder().build();
	      String goaliddelete=null;
	      String goaliddelete2=null;
			if (CategoryType.isEmpty()) {
				List<String> amt=new ArrayList<String>();
				List<String> goalId=new ArrayList<String>();
				for (int i = 0; i < categoryIds.size(); i++) {
					amt.add(Amount);
				}
				goalId.add(listGoalId.get(1));
				goaliddelete=listGoalId.get(1);
				String catIdBodyReqParam = bHelper.CreateBudgetAtCatDataGoal(groupId, categoryIds, amt, Currency,goalId);
				httpMethodParameters.setBodyParams(catIdBodyReqParam);
				Response bdget=budgetUtils.createBudget(httpMethodParameters, sessionObj);
				budgetId=bdget.jsonPath().get("budgetGroups.id");
			} else if (CategoryId.isEmpty()) {
				List<String> amt=new ArrayList<String>();
				for (int i = 0; i < categoryTypes.size(); i++) {
					amt.add(Amount);
				}
				
				goaliddelete=listGoalId.get(0);
				String catTypeBodyReqParam = bHelper.CreateBudgetAtCatTypeGoal(groupId, categoryTypes, amt, Currency,listGoalId.get(0));
				httpMethodParameters.setBodyParams(catTypeBodyReqParam);
				Response bdget=budgetUtils.createBudget(httpMethodParameters, sessionObj);
				budgetId=bdget.jsonPath().get("budgetGroups.id");
			} else {
				List<String> goalId=new ArrayList<String>();
				goalId.add(listGoalId.get(0));
				goalId.add(listGoalId.get(1));
				goaliddelete=listGoalId.get(0);
				goaliddelete2=listGoalId.get(1);
				String catDataAndCatTypeBodyReqParam = bHelper.CreateCatDataAndCatTypeGoal(groupId, categoryTypes,
						categoryIds, Amount, Currency,goalId);
				httpMethodParameters.setBodyParams(catDataAndCatTypeBodyReqParam);
				Response bdget=budgetUtils.createBudget(httpMethodParameters, sessionObj);
				budgetId=bdget.jsonPath().get("budgetGroups.id");
			}

			HttpMethodParameters httpMethodParameters1 = HttpMethodParameters.builder().build();
			HashMap<String, Object> getGoalQueryParam = new HashMap<>();
			getGoalQueryParam.put("groupId", groupId);

			Response deletBudgetResp=null;
			if (CategoryIdQueryParam.isEmpty() && CategoryTypeQueryParam.isEmpty()) {
				if (!CategoryType.isEmpty() && !CategoryId.isEmpty())
				{
					 goaliddelete= goaliddelete+","+goaliddelete2;
					getGoalQueryParam.put("goalId",goaliddelete);
					httpMethodParameters1.setQueryParams(getGoalQueryParam);
					 deletBudgetResp=budgetUtils.deleteBudgetGoal(httpMethodParameters1, sessionObj);
					 deletBudgetResp=budgetUtils.deleteBudgetGoal(httpMethodParameters1, sessionObj);
				}else {
					
					 if (!GoalId.isEmpty()) { goaliddelete=GoalId; }
					
				getGoalQueryParam.put("goalId", goaliddelete);
				httpMethodParameters1.setQueryParams(getGoalQueryParam);
				 deletBudgetResp=budgetUtils.deleteBudgetGoal(httpMethodParameters1, sessionObj);
				 deletBudgetResp=budgetUtils.deleteBudgetGoal(httpMethodParameters1, sessionObj);
				}
			}
			else if (CategoryIdQueryParam.isEmpty()) {
				getGoalQueryParam.put("categoryType", CategoryTypeQueryParam);
				httpMethodParameters1.setQueryParams(getGoalQueryParam);
				 deletBudgetResp=budgetUtils.deleteBudgetCategoryType(httpMethodParameters1, sessionObj);
				 deletBudgetResp=budgetUtils.deleteBudgetCategoryType(httpMethodParameters1, sessionObj);
			} else if (CategoryTypeQueryParam.isEmpty()) {
				getGoalQueryParam.put("categoryId", CategoryIdQueryParam);
				httpMethodParameters1.setQueryParams(getGoalQueryParam);
				 deletBudgetResp=budgetUtils.deleteBudgetCategory(httpMethodParameters1, sessionObj);
				 deletBudgetResp=budgetUtils.deleteBudgetCategory(httpMethodParameters1, sessionObj);
			} 

			

			if(deletBudgetResp.getStatusCode()!=204) {
				JSONObject Obj = new JSONObject(deletBudgetResp.asString());
				Assert.assertEquals(ErrorCode, Obj.get("errorCode"));
				Assert.assertEquals(ExpectedErrorMsg, Obj.get("errorMessage"));	
			}else {
				assertEquals(deletBudgetResp.getStatusCode(),204);
					bHelper.deleteBudgetDbVerification(userName, budgetId,CategoryTypeQueryParam,CategoryIdQueryParam,goaliddelete,false);
				
			 
				
			}
		
			
		
		
		
	}
	@Test(enabled = true, dataProvider = "feeder", priority = 5,dependsOnMethods="createBudgetPredefinedSFG")
	@Source("\\TestData\\CSVFiles\\Budget\\DeleteBudgetGoal4.csv")
	public void deleteBudgetInvalidQuerryParam(String TestCaseId, String TestCase, String groupName, String CategoryType,
			String CategoryId, String Amount, String Currency, String CategoryIdQueryParam,String GroupIdQueryParam,
			String CategoryTypeQueryParam, String ContainerTypeId,String ErrorCode,String ExpectedErrorMsg,String ExpectedCategoryType,String ExpectedCategoryId,String QueryParam,String Enabled) {
		
		  int budgetId=0;
			List<Integer> categoryIds = null;
		if (CategoryId != null && !CategoryId.isEmpty()) {
				categoryIds = Stream.of(CategoryId.split(",")).map(Integer::parseInt).collect(Collectors.toList());
			}

			List<String> categoryTypes = null;
			if (CategoryType != null && !CategoryType.isEmpty()) {
				categoryTypes = Arrays.asList(CategoryType.split(","));
			}

			List<Integer> itemAccountId = bHelper.getItemAccountIds(userName, ContainerTypeId);

			HttpMethodParameters bodyParam = BudgetHelper.createGroupBodyParam(groupName, itemAccountId);
			Response groupResponse = groupUtils.createGroup(bodyParam, sessionObj);
			String groupId = groupResponse.jsonPath().getString(JsonPath.budgetGroupId);

			
			HttpMethodParameters httpMethodParameters = HttpMethodParameters.builder().build();
	      String goaliddelete=null;
	    
			if (CategoryType.isEmpty()) {
				List<String> amt=new ArrayList<String>();
				List<String> goalId=new ArrayList<String>();
				for (int i = 0; i < categoryIds.size(); i++) {
					amt.add(Amount);
				}
				goalId.add(listGoalId.get(1));
				goaliddelete=listGoalId.get(1);
				String catIdBodyReqParam = bHelper.CreateBudgetAtCatDataGoal(groupId, categoryIds, amt, Currency,goalId);
				httpMethodParameters.setBodyParams(catIdBodyReqParam);
				Response bdget=budgetUtils.createBudget(httpMethodParameters, sessionObj);
				budgetId=bdget.jsonPath().get("budgetGroups.id");
			} else if (CategoryId.isEmpty()) {
				List<String> amt=new ArrayList<String>();
				for (int i = 0; i < categoryTypes.size(); i++) {
					amt.add(Amount);
				}
				
				goaliddelete=listGoalId.get(0);
				String catTypeBodyReqParam = bHelper.CreateBudgetAtCatTypeGoal(groupId, categoryTypes, amt, Currency,listGoalId.get(0));
				httpMethodParameters.setBodyParams(catTypeBodyReqParam);
				Response bdget=budgetUtils.createBudget(httpMethodParameters, sessionObj);
				budgetId=bdget.jsonPath().get("budgetGroups.id");
			} else {
				List<String> goalId=new ArrayList<String>();
				goalId.add(listGoalId.get(0));
				
				String catDataAndCatTypeBodyReqParam = bHelper.CreateCatDataAndCatTypeGoal(groupId, categoryTypes,
						categoryIds, Amount, Currency,goalId);
				httpMethodParameters.setBodyParams(catDataAndCatTypeBodyReqParam);
				Response bdget=budgetUtils.createBudget(httpMethodParameters, sessionObj);
				budgetId=bdget.jsonPath().get("budgetGroups.id");
			}

			HttpMethodParameters httpMethodParameters1 = HttpMethodParameters.builder().build();
			HashMap<String, Object> getGoalQueryParam = new HashMap<>();
			getGoalQueryParam.put("groupId", groupId);

			Response deletBudgetResp=null;
			
					
				getGoalQueryParam.put("galId", goaliddelete);
				httpMethodParameters1.setQueryParams(getGoalQueryParam);
				 deletBudgetResp=budgetUtils.deleteBudgetGoal(httpMethodParameters1, sessionObj);
				
			

			

			if(deletBudgetResp.getStatusCode()!=204) {
				JSONObject Obj = new JSONObject(deletBudgetResp.asString());
				Assert.assertEquals(ErrorCode, Obj.get("errorCode"));
				Assert.assertEquals(ExpectedErrorMsg, Obj.get("errorMessage"));	
			}else {
				assertEquals(deletBudgetResp.getStatusCode(),204);
				
			    	bHelper.deleteBudgetDbVerification(userName, budgetId,CategoryType,CategoryId,goaliddelete,false);
		
			 
				
			}
		
			
		
		
		
	}
}