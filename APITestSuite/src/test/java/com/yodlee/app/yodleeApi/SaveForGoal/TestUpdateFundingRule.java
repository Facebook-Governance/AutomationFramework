/**
* Copyright (c) 2019 Yodlee Inc. All Rights Reserved.
*
* This software is the confidential and proprietary information of
* Yodlee, Inc. Use is subject to license terms.
*
* @author Renuka K
*/ 
package com.yodlee.app.yodleeApi.SaveForGoal;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.databene.benerator.anno.Source;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;


import com.yodlee.yodleeApi.common.Base;
import com.yodlee.yodleeApi.common.Configuration;
import com.yodlee.yodleeApi.constants.JSONPaths;
import com.yodlee.DBHelper;
import com.yodlee.yodleeApi.helper.SaveForGoalHelper;
import com.yodlee.yodleeApi.helper.UserHelper;
import com.yodlee.yodleeApi.pojo.EnvSession;
import com.yodlee.yodleeApi.pojo.HttpMethodParameters;
import com.yodlee.yodleeApi.pojo.User;
import com.yodlee.yodleeApi.utils.apiUtils.AccountUtils;
import com.yodlee.yodleeApi.utils.apiUtils.BudgetUtils;
import com.yodlee.yodleeApi.utils.apiUtils.ProviderAccountUtils;
import com.yodlee.yodleeApi.utils.apiUtils.SFGUtils;
import com.yodlee.yodleeApi.utils.apiUtils.UserUtils;
import com.yodlee.yodleeApi.utils.commonUtils.CommonUtils;

import io.restassured.response.Response;
import service.DBUtility;

public class TestUpdateFundingRule extends Base {
    public static Long providerAccountId = null;

    String loginName, password;
    String userSession = "";
    EnvSession sessionObj = null;
    Configuration configurationObj = Configuration.getInstance();
    UserUtils userUtils = new UserUtils();
    ProviderAccountUtils providerAccountUtils = new ProviderAccountUtils();
    CommonUtils commonUtils = new CommonUtils();
    UserHelper userHelper = new UserHelper();
    TreeMap<Long,Long> accountsBalancesMap = new TreeMap();  
    SaveForGoalHelper sfg = new SaveForGoalHelper();
    String goalId = null;
    HashMap<String,ArrayList<String>> negativeExpectedValuesMap;
    SFGUtils sfgUtils = new SFGUtils();
    String userName = "SFGYSL" + System.currentTimeMillis();
    ArrayList<String> goalIdsList=new ArrayList<String>();
    ArrayList<String> fundingRuleIdList=new ArrayList<String>();
    DBUtility dbUtility = new DBUtility();
    DBHelper dbHelper = new DBHelper();
    ArrayList<String> goalIdsForUpdate=new ArrayList<String>();
    List<Long> itemAccounts = null;
    static int itemAccountCnt=0;
   

   
    @BeforeClass(alwaysRun = true)
	public void setUpData() throws ParseException, NumberFormatException, SQLException {
    	System.out.println("******STARTING***********");
    	User userInfo = User.builder().build();
		userInfo.setUsername(userName);
		userInfo.setPassword("TEST@123");
		sessionObj = EnvSession.builder().cobSession(configurationObj.getCobrandSessionObj().getCobSession())
				.path(configurationObj.getCobrandSessionObj().getPath()).build();
		userHelper.getUserSession(userInfo, sessionObj);
		long providerId = 16441;
		providerAccountId = providerId;
		Response response = providerAccountUtils.addProviderAccountStrict(providerId, "fieldarray",
				"sfgtestdata.site16441.1", "site16441.1", sessionObj);
		providerAccountId = response.jsonPath().getLong(JSONPaths.PROVIDER_ACC_ID);
		System.out.println("providerAccountId1::::===" + providerAccountId);
		AccountUtils accountUtils = new AccountUtils();
	    accountsBalancesMap = sfg.getAvailableBalances(accountUtils.getAllAccounts(sessionObj));   
	    itemAccounts = new ArrayList<Long>(accountsBalancesMap.keySet());
	    negativeExpectedValuesMap = sfg.readNegativeExpectedValues();
	    goalId=sfg.prepareGoalsForTest(sessionObj);
	    goalIdsList.add(goalId);
	    sfg.updateGoalForFundingRuleUpdate("IN_PROGRESS",goalId,sessionObj);
	    System.out.println("goal Id"+goalId);
	    for(int i=0;i<20;i++) {	
	    	sfg.createDestAccount(goalId, sfg.createDestinationAccount(Integer.parseInt(itemAccounts.get(0).toString()),500,"USD"), sessionObj);
	    	System.out.println("destination account "+(i+1) +" is " +itemAccounts.get(0).toString());
	    	String funRuleId=sfg.createFundingRulesForUpdate(Integer.parseInt(itemAccounts.get(0).toString()),goalId,sessionObj); 	
	    	System.out.println("funding rule id "+(i+1) +" is " +funRuleId);
	    	fundingRuleIdList.add(funRuleId);
	    	//sfg.changeGoalStatus(goalId, "INPROGRESS", "2");
	    	
	    }	    	   
	} 
    
   
    @Test(enabled = true,dataProvider = "feeder")
    @Source("\\TestData\\CSVFiles\\SaveForGoal\\UpdateFundingRule.csv")
    public void updateFundingRule(String testCaseId, String testDescription, String recurringAmount, String recurringCurrency,String frequency, int totalFundingRules,
        String container,int httpStatusCode, String enabled) throws Exception {
        System.out.println("Executing testcase --->  "+testCaseId);
        commonUtils.isTCEnabled(enabled, testCaseId); 
        String bodyParams = null;
        bodyParams= sfg.updateFundingRule(accountsBalancesMap.keySet(), recurringAmount, recurringCurrency, frequency,container,totalFundingRules,goalIdsList,sessionObj);
        Map<String, Object> createUpdateFRPathParam = new HashMap<>();
        Map<String, Object> createUpdateQueryParam = new HashMap<>();
        createUpdateFRPathParam.put("goalId",goalIdsList.get(0));
        createUpdateQueryParam.put("id", fundingRuleIdList.get(itemAccountCnt));
        itemAccountCnt++;
        System.out.println("Body Param for create Destination Account ");
        System.out.println(bodyParams);
        HttpMethodParameters httpMethodParameters = HttpMethodParameters.builder().build();
        httpMethodParameters.setBodyParams(bodyParams);
        httpMethodParameters.setPathParams(createUpdateFRPathParam);
        httpMethodParameters.setQueryParams(createUpdateQueryParam);
        Response response = sfgUtils.updateFundingRule(httpMethodParameters, sessionObj);
        Assert.assertEquals(response.getStatusCode(), httpStatusCode);
        //sfg.validateUpdateFundingRuleResponse(response,totalFundingRules,httpStatusCode,testCaseId,negativeExpectedValuesMap);        
     }   
    


   

    @AfterClass(alwaysRun = true)
    public void unRegisteredUser() {
        userUtils.unRegisterUser(sessionObj);

    }

}