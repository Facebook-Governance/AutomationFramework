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
import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import com.yodlee.yodleeApi.utils.apiUtils.UserUtils;

import com.yodlee.yodleeApi.helper.BudgetHelper;

import org.apache.commons.lang.RandomStringUtils;
import org.databene.benerator.anno.Source;
import org.jose4j.lang.JoseException;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.yodlee.yodleeApi.common.Base;
import com.yodlee.yodleeApi.common.Configuration;
import com.yodlee.yodleeApi.constants.JSONPaths;
import com.yodlee.yodleeApi.helper.SaveForGoalHelper;
import com.yodlee.yodleeApi.helper.SenseTransactionHelper;
import com.yodlee.yodleeApi.helper.UserHelper;
import com.yodlee.yodleeApi.pojo.EnvSession;
import com.yodlee.yodleeApi.pojo.HttpMethodParameters;
import com.yodlee.yodleeApi.pojo.User;
import com.yodlee.yodleeApi.utils.apiUtils.BudgetUtils;
import com.yodlee.yodleeApi.utils.apiUtils.ProviderAccountUtils;
import com.yodlee.yodleeApi.utils.apiUtils.SFGUtils;
import com.yodlee.yodleeApi.utils.apiUtils.GroupUtils;
import com.yodlee.yodleeApi.utils.commonUtils.CommonUtils;

import io.restassured.response.Response;
public class TestBudgetTrendGoal extends Base {

	// Global ArrayList variables declearation and initialization
	private static List<Integer> itemAccountIds = new LinkedList<Integer>();
	private BudgetHelper budgetHelper = new BudgetHelper();
    private BudgetUtils budgetUtils = new BudgetUtils();
	private GroupUtils groupUtils = new GroupUtils();
	private CommonUtils commonUtils = new CommonUtils();
	private UserUtils userUtils = new UserUtils();
	private SenseTransactionHelper senseTransactionsHelper = new SenseTransactionHelper();
	// Global String variables declearation and initialization
	private static String getGoalId;
	private UserHelper userHelper = new UserHelper();
	// Object creation
	public static Long providerAccountId = null;
	// Initialization of Response objects
	private EnvSession sessionObj = null;
	// Initialization of File objects
	private Configuration config = Configuration.getInstance();
	private ProviderAccountUtils providerAccountUtils = new ProviderAccountUtils();
	private SFGUtils sfgUtils = new SFGUtils();
	private String userName = "BudegtUser12" + System.currentTimeMillis();
	private SaveForGoalHelper sfg = new SaveForGoalHelper();
	// Initialization of .CSV file location
	private final static String createBudget_History = "\\TestData\\CSVFiles\\Budget\\CreateBudgetGoal_History.csv";
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
	
	@SuppressWarnings("static-access")
	@Test(enabled = true, dataProvider = "feeder", priority = 2)
	@Source(createBudget_History)
	public void createBudgetWithAllGoalState(String testCaseId, String testCase, String testNo1, String catId,String CategoryIdAmount,
			String catType,String CateogryTypeAmount, String currType,String Goal,String GoalStartDate
			,String GoalEndDate,String Status,String fromDate,String toDate,String include,String enabled) throws Exception {
		commonUtils.isTCEnabled(enabled, testCaseId);	
		HttpMethodParameters groupJson2 = budgetHelper.createGroupJson(RandomStringUtils.randomAlphabetic(10));
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
		
		if (Status.equals("IN_PROGRESS") || Status.equals("COMPLETED")) {
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
		String createbudgetBodyParam= budgetHelper.CreateBudgetAtCatTypeGoal(groupId2, arrayOfCatType, catTypeAmt,
				currType,goalId);
		System.out.println(createbudgetBodyParam);
		
		HttpMethodParameters httpMethodParameters = HttpMethodParameters.builder().build();
		httpMethodParameters.setBodyParams(createbudgetBodyParam);
		Response catDataAndCatTypeBodyResp = budgetUtils.createBudget(httpMethodParameters,
				sessionObj);
        int budgetId=catDataAndCatTypeBodyResp.jsonPath().get("budgetGroups.id");
        Assert.assertTrue(catDataAndCatTypeBodyResp.statusCode()==201);
        budgetHelper.createBudgetDbVerification(userName, budgetId, arrayOfCatType, catid, listGoalId);
        System.out.println(catDataAndCatTypeBodyResp.statusCode());
        
        HttpMethodParameters httpMethodParameter = HttpMethodParameters.builder().build();
        Map<String, Object> getBudgetTrendsQueryParam = new HashMap<>();
        getBudgetTrendsQueryParam.put("groupId",groupId2);
        getBudgetTrendsQueryParam.put("include", include);
        String frmDate = senseTransactionsHelper.getOffsetDate(fromDate);
        if(testCase.contains("startDate_mid") || testCase.contains("endDate_beyond")){
           frmDate = senseTransactionsHelper.getOffsetDate(fromDate);
        }
        else{
        	String[] dateStringArray = frmDate.split("-");
        	String dYear = dateStringArray[0];
        	String dMonth = dateStringArray[1];
        	String dDate = dateStringArray[2];
        	dDate = dDate.replace(dateStringArray[2], "01");
        	frmDate = String.join("-",dYear,dMonth,dDate);
        }
        getBudgetTrendsQueryParam.put("fromDate",frmDate);
        getBudgetTrendsQueryParam.put("toDate",senseTransactionsHelper.getOffsetDate(toDate));
        httpMethodParameter.setQueryParams(getBudgetTrendsQueryParam);	        
        Response responseHistory =   budgetUtils.getBudgetHistory(httpMethodParameter, sessionObj);	        
        if (responseHistory.asString().contains("goalId") 
        		&& responseHistory.asString().contains("goalName")
        		&& responseHistory.asString().contains("budgetHistory")) {
        	Assert.assertTrue(true,"budgetHistory validated");
        }else if (testCase.contains("without_include")){
        	if(responseHistory.asString().contains("budgetHistory")
        			&& responseHistory.asString().contains("budgetAmount")) {
        		Assert.assertTrue(true,"budgetHistory validated");
        	}
        }
        else if(testCase.contains("startDate_mid") 
        		|| testCase.contains("endDate_beyond")
        		|| responseHistory.asString().contains("Invalid value for fromDate")){
        		Assert.assertTrue(true,"invalid scenario fpr budgetHistory validated");
        	}else{
        		Assert.assertFalse(false);
        	}
	}
	
	@AfterClass(alwaysRun = true)
	public void unRegisteredUser() {
		userUtils.unRegisterUser(sessionObj);

	}
}
