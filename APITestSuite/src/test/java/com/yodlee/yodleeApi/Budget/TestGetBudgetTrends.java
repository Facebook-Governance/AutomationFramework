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
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.TreeMap;
import java.util.List;

import org.apache.commons.lang.RandomStringUtils;
import org.databene.benerator.anno.Source;
import org.jose4j.lang.JoseException;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import com.yodlee.DBHelper;
import com.yodlee.yodleeApi.common.Base;
import com.yodlee.yodleeApi.common.Configuration;
import com.yodlee.yodleeApi.constants.JSONPaths;
import com.yodlee.yodleeApi.helper.BudgetHelper;
import com.yodlee.yodleeApi.helper.ProviderAccountsHelper;
import com.yodlee.yodleeApi.helper.SaveForGoalHelper;
import com.yodlee.yodleeApi.helper.UserHelper;
import com.yodlee.yodleeApi.pojo.EnvSession;
import com.yodlee.yodleeApi.pojo.HttpMethodParameters;
import com.yodlee.yodleeApi.pojo.User;
import com.yodlee.yodleeApi.utils.apiUtils.AccountUtils;
import com.yodlee.yodleeApi.utils.apiUtils.BudgetUtils;
import com.yodlee.yodleeApi.utils.apiUtils.GroupUtils;
import com.yodlee.yodleeApi.utils.apiUtils.ProviderAccountUtils;
import com.yodlee.yodleeApi.utils.apiUtils.SFGUtils;
import com.yodlee.yodleeApi.utils.apiUtils.UserUtils;
import com.yodlee.yodleeApi.utils.commonUtils.CommonUtils;
import com.yodlee.yodleeApi.utils.commonUtils.PropertyFileUtil;

import io.restassured.response.Response;
import service.DBUtility;
import com.yodlee.DBHelper;

public class TestGetBudgetTrends extends Base {

	 public static Long providerAccountId = null;
	    String loginName, password;
	    String userSession = "";
	    EnvSession sessionObj = null;
	    Configuration configurationObj = Configuration.getInstance();
	    UserUtils userUtils = new UserUtils();
	    ProviderAccountUtils providerAccountUtils = new ProviderAccountUtils();
	    CommonUtils commonUtils = new CommonUtils();
	    UserHelper userHelper = new UserHelper();
	    String accountGroupId = null;
	    HashMap<String,ArrayList<String>> negativeExpectedValuesMap;
	    BudgetUtils budgetUtils = new BudgetUtils();
	    BudgetHelper budgetHelper = new BudgetHelper();
	    List<String> itemAccountIdList=null;
	    String userName = "BudgetYSL" + System.currentTimeMillis(); 
	   

	   
	    @BeforeClass(alwaysRun = true)
		public void setUpData() throws  NumberFormatException, SQLException, JoseException {
	    	System.out.println("******STARTING***********");
	        negativeExpectedValuesMap = budgetHelper.readNegativeExpectedValues();
	    	budgetHelper.getBudgetTrendsQueryDB(userName);
	    	User userInfo = User.builder().build();
			userInfo.setUsername(userName);
			userInfo.setPassword("TEST@123");
			sessionObj = EnvSession.builder().cobSession(configurationObj.getCobrandSessionObj().getCobSession())
					.path(configurationObj.getCobrandSessionObj().getPath()).build();
			userHelper.getUserSession(userInfo, sessionObj);
			//userHelper.getUserSession("budgettest104", "Test@123", sessionObj);
			long providerId = 16441;
			providerAccountId = providerId;
			Response response = providerAccountUtils.addProviderAccountStrict(providerId, "fieldarray",
					"budgettrends.site16441.1", "site16441.1", sessionObj);
			providerAccountId = response.jsonPath().getLong(JSONPaths.PROVIDER_ACC_ID);
			System.out.println("providerAccountId1::::===" + providerAccountId);
			ArrayList<Long> itemAccountsList = budgetHelper.getItemAccountsOfContainers(sessionObj);
			accountGroupId = budgetHelper.prepDataToGetBudgetGoalHistory(sessionObj,userName,itemAccountsList);  
			System.out.println("Account Group Id "+accountGroupId);
		//	transactionList=budgetHelper.getTransactionsMap(itemAccountId);
		} 
	    
	   
	    @Test(enabled = true,dataProvider = "feeder")
	    @Source("\\TestData\\CSVFiles\\Budget\\GetBudgetTrends.csv")
	    public void getBudgetTrendsHistory(String testCaseId, String testDescription, String fromDate,String toDate,String includeValues,String categoryIds,String categoryType,String container,int httpStatusCode, String enabled) throws Exception {
	    	System.out.println("Executing testcase --->  "+testCaseId);
	        commonUtils.isTCEnabled(enabled, testCaseId); 
	        fromDate = budgetHelper.parseDate(fromDate);
	        toDate = budgetHelper.parseDate(toDate);
	        String[] validateAttributes= {testCaseId,fromDate,toDate,includeValues,categoryIds,categoryType};
	        HttpMethodParameters httpMethodParameters = HttpMethodParameters.builder().build();
	        Map<String, Object> getBudgetTrendsQueryParam = new HashMap<>();
	        getBudgetTrendsQueryParam.put("groupId",accountGroupId);
	        getBudgetTrendsQueryParam.put("include", includeValues);
	        getBudgetTrendsQueryParam.put("fromDate",fromDate);
	        getBudgetTrendsQueryParam.put("toDate",toDate);
	        httpMethodParameters.setQueryParams(getBudgetTrendsQueryParam);	        
	        Response response =   budgetUtils.getBudgetHistory(httpMethodParameters, sessionObj);	        
	        System.out.println(response.asString());	
	        budgetHelper.validateGetBudgetHistoryResponse(response,httpStatusCode,validateAttributes,negativeExpectedValuesMap);
	      }   
	 }
