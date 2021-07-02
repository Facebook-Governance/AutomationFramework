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
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.TreeMap;

import org.databene.benerator.anno.Source;
import org.jose4j.lang.JoseException;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.yodlee.yodleeApi.assertions.JsonAssertionUtil;
import com.yodlee.yodleeApi.common.Base;
import com.yodlee.yodleeApi.common.Configuration;
import com.yodlee.yodleeApi.constants.Constants;
import com.yodlee.yodleeApi.constants.Container;
import com.yodlee.yodleeApi.constants.JSONPaths;
import com.yodlee.yodleeApi.helper.AccountsHelper;
import com.yodlee.yodleeApi.helper.SaveForGoalHelper;
import com.yodlee.yodleeApi.helper.UserHelper;
import com.yodlee.yodleeApi.pojo.EnvSession;
import com.yodlee.yodleeApi.pojo.HttpMethodParameters;
import com.yodlee.yodleeApi.pojo.User;
import com.yodlee.yodleeApi.utils.apiUtils.AccountUtils;
import com.yodlee.yodleeApi.utils.apiUtils.ProviderAccountUtils;
import com.yodlee.yodleeApi.utils.apiUtils.SFGUtils;
import com.yodlee.yodleeApi.utils.apiUtils.UserUtils;
import com.yodlee.yodleeApi.utils.commonUtils.CommonUtils;

import io.restassured.response.Response;
import service.DBUtility;

public class TestCreateUpdateDestinationAccount extends Base {
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
   // String userName = "usertestgoal290";
    ArrayList<String> goalIdsList=new ArrayList<String>();
    DBUtility dbUtility = new DBUtility();
    ArrayList<String> goalIdsForUpdate=new ArrayList<String>();
   

   
    @BeforeClass(alwaysRun = true)
	public void setUpData() throws ParseException, JoseException {
    	System.out.println("******STARTING***********");
		User userInfo = User.builder().build();
		userInfo.setUsername(userName);
		userInfo.setPassword("TEST@123");
		sessionObj = EnvSession.builder().cobSession(configurationObj.getCobrandSessionObj().getCobSession())
				.path(configurationObj.getCobrandSessionObj().getPath()).build();
		userHelper.getUserSession(userInfo, sessionObj);
		//userHelper.getUserSession(userInfo.getUsername(), userInfo.getPassword(), sessionObj);
		long providerId = 16441;
		providerAccountId = providerId;
		Response response = providerAccountUtils.addProviderAccountStrict(providerId, "fieldarray",
				"sfgtestdata.site16441.1", "site16441.1", sessionObj);
		providerAccountId = response.jsonPath().getLong(JSONPaths.PROVIDER_ACC_ID);
		System.out.println("providerAccountId1::::===" + providerAccountId);
		AccountUtils accountUtils = new AccountUtils();
	    accountsBalancesMap = sfg.getAvailableBalances(accountUtils.getAllAccounts(sessionObj));   
	    negativeExpectedValuesMap = sfg.readNegativeExpectedValues();
	    for(int i=0;i<3;i++) {
	    	goalId=sfg.prepareGoalsForTest(sessionObj);
	    	goalIdsList.add(goalId);
	    }	       
	  sfg.changeAccountStatus(accountsBalancesMap.keySet());
	} 
    
   
    @Test(enabled = true,dataProvider = "feeder", priority = 1)
    @Source("\\TestData\\CSVFiles\\SaveForGoal\\CreateDestinationAccount.csv")
    public void createDestinationAccount(String testCaseId, String testDescription, String allocationAmt, String allocationCurrency, int totalDestAccounts,
        String container,int httpStatusCode, String enabled) throws Exception {
        System.out.println("Executing testcase --->  "+testCaseId);
        commonUtils.isTCEnabled(enabled, testCaseId); 
        goalId=sfg.prepareGoalsForTest(sessionObj);
        goalIdsForUpdate.add(goalId);
        String bodyParams = null;
        bodyParams = sfg.createDestinationAccountJson(totalDestAccounts, accountsBalancesMap.keySet(), allocationAmt, allocationCurrency,container,goalIdsList);
        if(container.contains("inprogress")) 
        	goalId=goalIdsList.get(0).toString();   
        if(container.contains("completed")) 
        	goalId=goalIdsList.get(1).toString(); 
        if(container.contains("deleted")) 
        	goalId=goalIdsList.get(2).toString();         
        Map<String, Object> createDestAccntPathParam = new HashMap<>();
        createDestAccntPathParam.put("goalId",goalId);
        System.out.println("Body Param for create Destination Account ");
        System.out.println(bodyParams);
        HttpMethodParameters httpMethodParameters = HttpMethodParameters.builder().build();
        httpMethodParameters.setBodyParams(bodyParams);
        httpMethodParameters.setPathParams(createDestAccntPathParam);
        Response response = sfgUtils.createDestinationAccount(httpMethodParameters, sessionObj);
        System.out.println("Add Destination Accounts Response "+response.asString());
        sfg.validateAddDestAccountResponse(response,totalDestAccounts,httpStatusCode,testCaseId,negativeExpectedValuesMap);
     } 
   
    @Test(enabled = true,dataProvider = "feeder" , priority = 2)
    @Source("\\TestData\\CSVFiles\\SaveForGoal\\UpdateDestinationAccount.csv")
    public void updateDestinationAccount(String testCaseId, String testDescription, String allocationAmt, String allocationCurrency, int totalDestAccounts,
        String container,int httpStatusCode, String enabled) throws Exception {
        System.out.println("Executing testcase --->  "+testCaseId);
        commonUtils.isTCEnabled(enabled, testCaseId); 
        goalId=goalIdsForUpdate.get(0);
        goalIdsForUpdate.remove(0);
        String bodyParams = null;
        bodyParams = sfg.createDestinationAccountJson(totalDestAccounts, accountsBalancesMap.keySet(), allocationAmt, allocationCurrency,container,goalIdsList);
        if(container.contains("inprogress") || container.contains("completed") || container.contains("deleted")) {
        	goalId=goalIdsList.get(0).toString();
        	goalIdsList.remove(0);
        }
        LinkedHashMap<String, Object> createDestAccntPathParam = new LinkedHashMap<>();
        createDestAccntPathParam.put("goalId",goalId);
        System.out.println("Body Param for update Destination Account ");
        System.out.println(bodyParams);
        HttpMethodParameters httpMethodParameters = HttpMethodParameters.builder().build();
        httpMethodParameters.setBodyParams(bodyParams);
        httpMethodParameters.setPathParams(createDestAccntPathParam);
        Response response = sfgUtils.updateDestinationAccount(httpMethodParameters, sessionObj);
        System.out.println("Update Destination Accounts Response "+response.asString());
        Assert.assertEquals(response.getStatusCode(), httpStatusCode);
        if(httpStatusCode!=204)
        sfg.validateAddDestAccountResponse(response,totalDestAccounts,httpStatusCode,testCaseId,negativeExpectedValuesMap);
     }
    
    @Test(enabled = true,dataProvider = "feeder")
    @Source("\\TestData\\CSVFiles\\SaveForGoal\\CategoriesAndFrequencies.csv")
    public void goalCategoriesFrequencies(String testCaseId, String testDescription, String getType ,int totCategoriesFrequencies,int httpStatusCode, String enabled) throws Exception {
        System.out.println("Executing testcase --->  "+testCaseId);
        commonUtils.isTCEnabled(enabled, testCaseId); 
        HttpMethodParameters httpMethodParameters = HttpMethodParameters.builder().build();
	    LinkedHashMap<String, Object> createUpdateFRPathParam = new LinkedHashMap<>();
	    createUpdateFRPathParam.put("",getType);
	    httpMethodParameters.setPathParams(createUpdateFRPathParam);
	    Response response = sfgUtils.getGoalWithFundingRules(httpMethodParameters, sessionObj);
        Assert.assertEquals(response.getStatusCode(), httpStatusCode);
        sfg.validateCatFreqResponse(response,getType,totCategoriesFrequencies);
      }
    
    @Test(enabled = true,dataProvider = "feeder")
    @Source("\\TestData\\CSVFiles\\SaveForGoal\\CreateEditDestAccntAvailableBal.csv")
    public void createEditDestAccntFreeAvailableBal(String testCaseId, String testDescription, String allocationAmt, String allocationCurrency, int totalDestAccounts,
       String apiCall, String container,int httpStatusCode, String enabled) throws Exception {
        System.out.println("Executing testcase --->  "+testCaseId);
        commonUtils.isTCEnabled(enabled, testCaseId); 
        goalId=sfg.prepareGoalsToCheckFreeAvailableBal(sessionObj);
        HttpMethodParameters httpMethodParameters = HttpMethodParameters.builder().build();
        HashMap<String, Object> pathParam = new HashMap<>();
		pathParam.put("goalIdUpdate", goalId);
		httpMethodParameters.setPathParams(pathParam);
		String updateBodyParams =null;
		if(!apiCall.contains("update")) 
		updateBodyParams = sfg.updateGoalBodyParam("", "", "", "","", "", "", "", "", "IN_PROGRESS", "",	"");
		httpMethodParameters.setBodyParams(updateBodyParams);
		Response resp = sfgUtils.updateGoal(httpMethodParameters, sessionObj);
		String bodyParams = null;
        bodyParams = sfg.createDestinationAccountJson(totalDestAccounts, accountsBalancesMap.keySet(), allocationAmt, allocationCurrency,container,goalIdsList);
        Map<String, Object> createDestAccntPathParam = new HashMap<>();
        createDestAccntPathParam.put("goalId",goalId);
        System.out.println("Body Param for create Destination Account ");
        System.out.println(bodyParams);
        httpMethodParameters.setBodyParams(bodyParams);
        httpMethodParameters.setPathParams(createDestAccntPathParam);
        Response response = null;
        response = sfgUtils.createDestinationAccount(httpMethodParameters, sessionObj);       
        if(apiCall.contains("update"))   {     	
        	String bodyParams1 = null;
            bodyParams = sfg.createDestinationAccountJson(totalDestAccounts, accountsBalancesMap.keySet(), allocationAmt, allocationCurrency,container,goalIdsList);
            Map<String, Object> createDestAccntPathParam1= new HashMap<>();
            createDestAccntPathParam.put("goalId",goalId);
            HttpMethodParameters httpMethodParameters1 = HttpMethodParameters.builder().build();
            httpMethodParameters1.setBodyParams(bodyParams);
            httpMethodParameters1.setPathParams(createDestAccntPathParam);
            response = sfgUtils.updateDestinationAccount(httpMethodParameters1, sessionObj);
            Assert.assertEquals(response.getStatusCode(), httpStatusCode);
        	       
        }	
        if(response.getStatusCode()!=204)
        sfg.validateAddDestAccountResponse(response,totalDestAccounts,httpStatusCode,testCaseId,negativeExpectedValuesMap);
        if(apiCall.contains("getGoals")) {
        	Map<String, Object> getGoalsIncludeAccountsQueryParam = new HashMap<>();
        	getGoalsIncludeAccountsQueryParam.put("include", "accounts");
        	HttpMethodParameters httpMethodParametersGetGoals = HttpMethodParameters.builder().build();
        	httpMethodParametersGetGoals.setQueryParams(getGoalsIncludeAccountsQueryParam);
        	response=sfgUtils.getGoalWithFundingRules(httpMethodParametersGetGoals, sessionObj);
        	sfg.validateGetGoalsResposne(response, allocationAmt);        	
        }	        	        
        System.out.println("Add Destination Accounts Response "+response.asString());        		
        
     }
    
    @AfterClass(alwaysRun = true)
    public void unRegisteredUser() {
        userUtils.unRegisterUser(sessionObj);

    }

}