/*******************************************************************************
 *
 * * Copyright (c) 201 Yodlee, Inc. All Rights Reserved.
 *
 * *
 *
 * * This software is the confidential and proprietary information of Yodlee, Inc.
 *
 * * Use is subject to license terms.
 *
 *******************************************************************************/
package com.yodlee.app.yodleeApi.CategoriesMerchants;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import org.databene.benerator.anno.Source;
import org.jose4j.lang.JoseException;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import junit.framework.Assert;
import com.yodlee.insights.yodleeApi.utils.InsightsHelper;
import com.yodlee.insights.yodleeApi.utils.InsightsProperties;
import com.yodlee.yodleeApi.assertions.JsonAssertionUtil;
import com.yodlee.yodleeApi.common.Base;
import com.yodlee.yodleeApi.common.Configuration;
import com.yodlee.yodleeApi.constants.JSONPaths;
import com.yodlee.yodleeApi.helper.CategoriesMerchantsHelper;
import com.yodlee.yodleeApi.helper.JwtHelper;
import com.yodlee.yodleeApi.helper.SaveForGoalHelper;
import com.yodlee.yodleeApi.helper.UserHelper;
import com.yodlee.yodleeApi.pojo.EnvSession;
import com.yodlee.yodleeApi.pojo.HttpMethodParameters;
import com.yodlee.yodleeApi.pojo.User;
import com.yodlee.yodleeApi.utils.apiUtils.ProviderAccountUtils;
import com.yodlee.yodleeApi.utils.apiUtils.SFGUtils;
import com.yodlee.yodleeApi.utils.apiUtils.UserUtils;
import com.yodlee.yodleeApi.utils.commonUtils.CommonUtils;
import com.yodlee.yodleeApi.utils.apiUtils.TaskUtils;
import com.yodlee.yodleeApi.utils.apiUtils.TransactionUtils;



import io.restassured.response.Response;

public class TestUtilizedCategoriesMerchants extends Base {
	public static Long providerAccountId = null;

	String loginName, password;
	String userSession = "";
	EnvSession sessionObj = null;
	CommonUtils commontUtils = new CommonUtils();
	InsightsHelper insightsHelper = new InsightsHelper();
	Configuration configurationObj = Configuration.getInstance();
	UserUtils userUtils = new UserUtils();
	ProviderAccountUtils providerAccountUtils = new ProviderAccountUtils();
	CommonUtils commonUtils = new CommonUtils();
	SaveForGoalHelper saveForGoalHelper = new SaveForGoalHelper();
	UserHelper userHelper = new UserHelper();
	JsonAssertionUtil jsonAssertionutil = new JsonAssertionUtil();
	SaveForGoalHelper sfg = new SaveForGoalHelper();
	TaskUtils taskUtils = new TaskUtils();
	JwtHelper jwtHelper = new JwtHelper();
	String envUser = null;
	String jwtUserAuthToken = null;
	InsightsProperties prop = new InsightsProperties();	
	HashMap<String,ArrayList<String>> negativeExpectedValuesMap;
	TransactionUtils transactionUtils = new TransactionUtils();
	HttpMethodParameters httpParams = null;
	CategoriesMerchantsHelper categoriesMerchanthelper = new CategoriesMerchantsHelper();
	HashMap<String, Object> notificationsQueryParam = null;;
	

	@BeforeClass(alwaysRun = true)
	public void addAccount() throws JoseException, IOException, InterruptedException {
		System.out.println("initiated execution");
		envUser=prop.readPropertiesFile().getProperty("envUser");			
		User userInfo = User.builder().build();
		userInfo.setUsername("CatMerchants" + System.currentTimeMillis());
		userInfo.setPassword("TEST@123");
	    sessionObj = EnvSession.builder().cobSession(configurationObj.getCobrandSessionObj().getCobSession())
				.path(configurationObj.getCobrandSessionObj().getPath()).build();
		//userHelper.getUserSession(userInfo.getUsername(),userInfo.getPassword(),sessionObj)	;	
		userHelper.getUserSession(userInfo, sessionObj);	
		long providerId = 16441;
		providerAccountId = providerId;
		Response response = providerAccountUtils.addProviderAccountStrict(providerId, "fieldarray",
		"UtilizedCatsMerchant.site16441.1", "site16441.1", sessionObj);
		providerAccountId = response.jsonPath().getLong(JSONPaths.PROVIDER_ACC_ID);
		System.out.println("providerAccountId1::::===" + providerAccountId);		
		jwtUserAuthToken = jwtHelper.getJWTToken(userInfo.getUsername(),envUser);
		jwtUserAuthToken = "Bearer ".concat(jwtUserAuthToken);		
		negativeExpectedValuesMap = categoriesMerchanthelper.readNegativeExpectedValues();
	}

	@Test(enabled = true, dataProvider = "feeder")
	@Source("\\TestData\\CSVFiles\\TransactionCategoriesMerchants\\GetUtilizedCategories.csv")
	public void testGetUtilizedCategories(String testCaseId, String testDescription,String  authentication, String fromDate ,String toDate,
			String categoryType,int expectedUtilizedCategoiresCnt,String expectedUtilizedCatDetails,String negTestScenario,int statusCode,String tcEnabled) throws Exception {
		boolean testStatus=false;
		commontUtils.isTCEnabled(tcEnabled, testCaseId);
		httpParams = HttpMethodParameters.builder().build();
		notificationsQueryParam = new HashMap<>();
		Response utilizedCategoriesResponse  = null;
		if(!fromDate.isEmpty() && !fromDate.equals("NA"))
			   notificationsQueryParam.put("fromDate", categoriesMerchanthelper.formatFromAndToDate(Integer.parseInt(fromDate)));
		if(!toDate.isEmpty() && !toDate.equals("NA"))
			   notificationsQueryParam.put("toDate", categoriesMerchanthelper.formatFromAndToDate(Integer.parseInt(toDate)));			
		if(!categoryType.isEmpty())
			  notificationsQueryParam.put("categoryType", categoryType);	
		httpParams.setQueryParams(notificationsQueryParam);
		if(authentication.equals("Jwt")) {
			Thread.sleep(20000);
			httpParams=insightsHelper.getHttpParams("categoriesMerchantsRequest", null, "", jwtUserAuthToken,null,null,null,null);
			httpParams.setQueryParams(notificationsQueryParam);
			utilizedCategoriesResponse = transactionUtils.getTransactionsUtilizedCategories(httpParams, null);
		}else {
			httpParams.setQueryParams(notificationsQueryParam);
			utilizedCategoriesResponse = transactionUtils.getTransactionsUtilizedCategories(httpParams, sessionObj);
		}		
		System.out.println("Asserting Status code");
		if(utilizedCategoriesResponse.getStatusCode()==statusCode)
			testStatus=  categoriesMerchanthelper.validateAPIResponseForUtilizedCategories(utilizedCategoriesResponse,categoryType,expectedUtilizedCategoiresCnt,expectedUtilizedCatDetails,utilizedCategoriesResponse.getStatusCode(), negTestScenario,negativeExpectedValuesMap);
		else {
			System.out.println("Status Code Doesn't match");
		}
		System.out.println(testStatus?"TestCase PASSED":"TestCase FAILED");		
		if(!testStatus)
			Assert.fail();		
	}
	
	@Test(enabled = true, dataProvider = "feeder")
	@Source("\\TestData\\CSVFiles\\TransactionCategoriesMerchants\\GetUtilizedMerchants.csv")
	public void testGetUtilizedMerchants(String testCaseId, String testDescription,String  authentication, String fromDate ,String toDate,
			String categoryType,int expectedUtilizedCategoiresCnt,String expectedUtilizedCatDetails,String negTestScenario,int statusCode,String tcEnabled) throws Exception {
		boolean testStatus=false;
		commontUtils.isTCEnabled(tcEnabled, testCaseId);
		httpParams = HttpMethodParameters.builder().build();
		notificationsQueryParam = new HashMap<>();
		httpParams.setHeader("Authorization", jwtUserAuthToken);
		if(!fromDate.isEmpty() && !fromDate.equals("NA"))
		   notificationsQueryParam.put("fromDate", categoriesMerchanthelper.formatFromAndToDate(Integer.parseInt(fromDate)));
		if(!toDate.isEmpty() && !toDate.equals("NA"))
		   notificationsQueryParam.put("toDate", categoriesMerchanthelper.formatFromAndToDate(Integer.parseInt(toDate)));		
		httpParams.setQueryParams(notificationsQueryParam);
		Response utilizedMerchantsResponse = null;
		if(authentication.equals("Jwt")) {
			Thread.sleep(4000);
			httpParams=insightsHelper.getHttpParams("categoriesMerchantsRequest", null, "", jwtUserAuthToken,null,null,null,null);
			httpParams.setQueryParams(notificationsQueryParam);
			utilizedMerchantsResponse = transactionUtils.getTransactionsUtilizedMerchants(httpParams, null);
		}else {
			httpParams.setQueryParams(notificationsQueryParam);
			utilizedMerchantsResponse = transactionUtils.getTransactionsUtilizedMerchants(httpParams, sessionObj);
		}	
		System.out.println("Asserting Status code");
		if(utilizedMerchantsResponse.getStatusCode()==statusCode)
			testStatus=  categoriesMerchanthelper.validateAPIResponseForUtilizedMerchants(utilizedMerchantsResponse,categoryType,expectedUtilizedCategoiresCnt,expectedUtilizedCatDetails,utilizedMerchantsResponse.getStatusCode(), negTestScenario,negativeExpectedValuesMap);
		else {
			System.out.println("Status Code Doesn't match");
		}
		System.out.println(testStatus?"TestCase PASSED":"TestCase FAILED");		
		if(!testStatus)
			Assert.fail();		
	}

	
	@AfterClass(alwaysRun = true)
	public void unRegisteredUser() {
		userUtils.unRegisterUser(sessionObj);

	}

}
