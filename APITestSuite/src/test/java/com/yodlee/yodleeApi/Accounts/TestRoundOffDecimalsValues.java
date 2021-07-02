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

package com.yodlee.yodleeApi.Accounts;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.databene.benerator.anno.Source;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.yodlee.DBHelper;
import com.yodlee.yodleeApi.assertions.JsonAssertionUtil;
import com.yodlee.yodleeApi.common.Base;
import com.yodlee.yodleeApi.common.Configuration;
import com.yodlee.yodleeApi.constants.JSONPaths;
import com.yodlee.yodleeApi.helper.BudgetHelper;
import com.yodlee.yodleeApi.helper.RoundOffDecimalsHelper;
import com.yodlee.yodleeApi.helper.SaveForGoalHelper;
import com.yodlee.yodleeApi.helper.UserHelper;
import com.yodlee.yodleeApi.pojo.EnvSession;
import com.yodlee.yodleeApi.pojo.HttpMethodParameters;
import com.yodlee.yodleeApi.pojo.User;
import com.yodlee.yodleeApi.utils.apiUtils.AccountUtils;
import com.yodlee.yodleeApi.utils.apiUtils.BudgetUtils;
import com.yodlee.yodleeApi.utils.apiUtils.FincheckUtils;
import com.yodlee.yodleeApi.utils.apiUtils.ProviderAccountUtils;
import com.yodlee.yodleeApi.utils.apiUtils.SFGUtils;
import com.yodlee.yodleeApi.utils.apiUtils.StatementUtils;
import com.yodlee.yodleeApi.utils.apiUtils.UserUtils;
import com.yodlee.yodleeApi.utils.commonUtils.CommonUtils;

import io.restassured.response.Response;
import service.DBUtility;

public class TestRoundOffDecimalsValues extends Base {
	public static Long providerAccountId = null;

	private String loginName, password;
	private String userSession = "";
	private EnvSession sessionObj = null;
	private Configuration configurationObj = Configuration.getInstance();
	private UserUtils userUtils = new UserUtils();
	private ProviderAccountUtils providerAccountUtils = new ProviderAccountUtils();
	private CommonUtils commonUtils = new CommonUtils();
	private RoundOffDecimalsHelper roundOffDecimalsHelper = new RoundOffDecimalsHelper();
	private UserHelper userHelper = new UserHelper();
	private JsonAssertionUtil jsonAssertionutil = new JsonAssertionUtil();
	private SaveForGoalHelper sfg = new SaveForGoalHelper();
	private HashMap<String,ArrayList<String>> negativeExpectedValuesMap;
	private FincheckUtils fincheckUtils = new FincheckUtils();
	private AccountUtils accountUtils = new AccountUtils();
	private String recurringEventId = null;
	private String accountGroupId = null;
	private BudgetUtils budgetUtils = new BudgetUtils();
	private BudgetHelper budgetHelper = new BudgetHelper();
	StatementUtils statementUtils= new StatementUtils();
	private int decimalPrecision = 0;
	private char precision;
	ArrayList<Long> itemAccountsList = new ArrayList<Long>();
    
	

	@BeforeClass(alwaysRun = true)
	public void addAccount() throws SQLException {
		System.out.println("initiated execution");
		User userInfo = User.builder().build();
		userInfo.setUsername("RoundOffDecimals" + System.currentTimeMillis());
		userInfo.setPassword("TEST@123");
		sessionObj = EnvSession.builder().cobSession(configurationObj.getCobrandSessionObj().getCobSession())
				.path(configurationObj.getCobrandSessionObj().getPath()).build();
		userHelper.getUserSession(userInfo, sessionObj);
		long providerId = 16441;
		providerAccountId = providerId;
		Response response = providerAccountUtils.addProviderAccountStrict(providerId, "loginform",
				"roundoffdecimal.site16441.1", "site16441.1", sessionObj);
		providerAccountId = response.jsonPath().getLong(JSONPaths.PROVIDER_ACC_ID);
		System.out.println("providerAccountId1::::===" + providerAccountId);	
		itemAccountsList = budgetHelper.getItemAccountsOfContainers(sessionObj);
		accountGroupId = budgetHelper.prepDataToGetBudgetGoalHistory(sessionObj,userInfo.getUsername(),itemAccountsList); 
		precision = roundOffDecimalsHelper.getCobrandDecimalPrecesion();
		decimalPrecision = Integer.parseInt(Character.toString(precision));		
		itemAccountsList.clear();
		itemAccountsList = roundOffDecimalsHelper.getItemAccountsOfContainers(sessionObj);
	}

	@Test(enabled = true, dataProvider = "feeder")
	@Source("\\TestData\\CSVFiles\\Accounts\\RoundOffDecimalVals\\RoundOffDecimalPrecisions.csv")	
	public void testRoundOffDecimalPlacesPrecisions(String testCaseId, String testCase, String yslApiCall,String validateAttributes,
				int httpStatus,String Enabled) throws Exception {
		Response response = null;
		System.out.println("Executing TestCase--->"+testCaseId);
		commonUtils.isTCEnabled(Enabled, testCaseId);	
		String[] validateAttributesArr = validateAttributes.split(","); 
		
		HttpMethodParameters httpMethodParameters = HttpMethodParameters.builder().build();
		if (yslApiCall.equals("GetRecurringEvents")) {
			response = accountUtils.getRecurringEvents(httpMethodParameters, sessionObj);
			recurringEventId = roundOffDecimalsHelper.validateRecurringEvents(response, httpStatus,validateAttributesArr, decimalPrecision,yslApiCall);
		}	
		if (yslApiCall.equals("GetRecurringEventsById")) {
			HashMap<String, Object> pathParam = new HashMap<>();
			pathParam.put("recurringEventById", recurringEventId);
			httpMethodParameters.setPathParams(pathParam);
			response = accountUtils.getRecurringEvents(httpMethodParameters, sessionObj);			
			roundOffDecimalsHelper.validateRecurringEvents(response, httpStatus,validateAttributesArr, decimalPrecision,yslApiCall);
		}		
		//Fincheck
		if (yslApiCall.equals("FinCheckSummaryAllIndicators")) {
		response = fincheckUtils.getSummary(httpMethodParameters, sessionObj);
		roundOffDecimalsHelper.validateFinCheckIndicators(response, httpStatus, validateAttributesArr, decimalPrecision,yslApiCall);		
		}		

		//create and GetBudgetTrends		
		if (yslApiCall.equals("GetBudgetTrends")) {
			HttpMethodParameters budgetHttpMethodParameters = HttpMethodParameters.builder().build();
	        Map<String, Object> getBudgetTrendsQueryParam = new HashMap<>();
	        getBudgetTrendsQueryParam.put("groupId",accountGroupId);
	        getBudgetTrendsQueryParam.put("include", "categoryTypeData");
	        getBudgetTrendsQueryParam.put("fromDate",roundOffDecimalsHelper);
	        getBudgetTrendsQueryParam.put("toDate",roundOffDecimalsHelper);
	        budgetHttpMethodParameters.setQueryParams(getBudgetTrendsQueryParam);	        
	       // response =  budgetUtils.getBudgetHistory(budgetHttpMethodParameters, sessionObj);
	       // roundOffDecimalsHelper.validateBudgetTrends(response, httpStatus, validateAttributesArr, decimalPrecision,yslApiCall);
		}		
		//Get Statements summary		
		if (yslApiCall.equals("GetStatementSummary")) {
				HttpMethodParameters statementsHttpMethodParameters = HttpMethodParameters.builder().build();
		        Map<String, Object> getStmtSummaryQueryParam = new HashMap<>();
		        getStmtSummaryQueryParam.put("accountId",itemAccountsList.get(0).toString());
		        getStmtSummaryQueryParam.put("container", "creditCard");
		        statementsHttpMethodParameters.setQueryParams(getStmtSummaryQueryParam);	        
			    response =  statementUtils.getStatements(statementsHttpMethodParameters, sessionObj);
			    roundOffDecimalsHelper.validateStmtSummary(response, httpStatus, validateAttributesArr, decimalPrecision,yslApiCall);
		}		
	}
	
	@AfterClass(alwaysRun = true)
	public void unRegisteredUser() {
		userUtils.unRegisterUser(sessionObj);

	}

}
