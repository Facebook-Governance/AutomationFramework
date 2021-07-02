/*******************************************************************************
 *
 * * Copyright (c) 2020 Yodlee, Inc. All Rights Reserved.
 *
 * *
 *
 * * This software is the confidential and proprietary information of Yodlee, Inc.
 *
 * * Use is subject to license terms.
 *
 *******************************************************************************/
package com.yodlee.app.yodleeApi.Insights;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.databene.benerator.anno.Source;
import org.eclipse.jetty.http.HttpStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import com.google.gson.Gson;
import com.yodlee.insights.yodleeApi.utils.CreateSegment;
import com.yodlee.insights.yodleeApi.utils.InsightsCommons;
import com.yodlee.insights.yodleeApi.utils.InsightsConstants;
import com.yodlee.insights.yodleeApi.utils.InsightsDBUtils;
import com.yodlee.insights.yodleeApi.utils.InsightsGenerics;
import com.yodlee.insights.yodleeApi.utils.InsightsHelper;
import com.yodlee.insights.yodleeApi.utils.InsightsProperties;
import com.yodlee.yodleeApi.common.Base;
import com.yodlee.yodleeApi.common.Configuration;
import com.yodlee.yodleeApi.helper.JwtHelper;
import com.yodlee.yodleeApi.helper.UserHelper;
import com.yodlee.yodleeApi.pojo.EnvSession;
import com.yodlee.yodleeApi.pojo.HttpMethodParameters;
import com.yodlee.yodleeApi.pojo.User;
import com.yodlee.yodleeApi.utils.apiUtils.ProviderAccountUtils;
import com.yodlee.yodleeApi.utils.apiUtils.UserUtils;
import com.yodlee.yodleeApi.utils.apiUtils.InsightUtils;
import com.yodlee.yodleeApi.utils.apiUtils.InvokerUtils;
import com.yodlee.yodleeApi.utils.commonUtils.CommonUtils;
import io.restassured.response.Response;


public class TestGetUserSegment extends Base {
	public static Long providerAccountId = null;

	protected Logger logger = LoggerFactory.getLogger(TestGetUserSegment.class);
	String loginName, password;
	String userSession = "";
	EnvSession sessionObj = null;
	Configuration configurationObj = Configuration.getInstance();
	UserUtils userUtils = new UserUtils();
	ProviderAccountUtils providerAccountUtils = new ProviderAccountUtils();
	JwtHelper jwtHelper = new JwtHelper();
	InsightUtils insightUtils = new InsightUtils();
	CommonUtils commontUtils = new CommonUtils();
	UserHelper userHelper = new UserHelper();
	InvokerUtils invokerUtils = new InvokerUtils();
	InsightsHelper insightsHelper = new InsightsHelper();
	InsightsGenerics insightsGenerics = null;
	HashMap<String,ArrayList<String>> negativeExpectedValuesMap;
	InsightsProperties prop = new InsightsProperties();	
	InsightsCommons insightsCommons = new InsightsCommons();
	InsightsDBUtils insightsDBUtils = new InsightsDBUtils();
	String  jwtUserAuthToken = null;
	Number userId = null;
	String jwtCobAuthToken = "";
	String envCobrand=null;
	String envUser=null;
	String cobrandUser=null;
	
	
	
	@BeforeClass(alwaysRun = true)
	public void setUp() throws Exception {
		System.out.println("initiated execution for Create User Segment API");
		insightsGenerics = new InsightsGenerics();		
		envCobrand=prop.readPropertiesFile().getProperty("envCobrand");
		envUser=prop.readPropertiesFile().getProperty("envUser");	
		User userInfo = User.builder().build();
	    userInfo.setUsername("CreateSegment" + System.currentTimeMillis());
		//userInfo.setUsername("lbwinsightusr1"); loginUser = userInfo.getUsername();
		System.out.println("User ----> "+userInfo.getUsername());
		userInfo.setPassword("TEST@123");
		sessionObj = EnvSession.builder().cobSession(configurationObj.getCobrandSessionObj().getCobSession())
				.path(configurationObj.getCobrandSessionObj().getPath()).build();
		userHelper.getUserSession(userInfo, sessionObj);	
		//userHelper.getUserSession(userInfo.getUsername(),userInfo.getPassword(),sessionObj)	;			
		jwtCobAuthToken = "Bearer ".concat(jwtHelper.getJWTToken(cobrandUser,envCobrand));	
		jwtUserAuthToken = "Bearer ".concat(jwtHelper.getJWTToken(userInfo.getUsername(),envUser));	
		userId = insightsGenerics.getMemId(sessionObj);
		Thread.sleep(55000);
		negativeExpectedValuesMap = insightsGenerics.readNegativeExpectedValuesForUserSegment();
		}
	
	@Test(enabled = true, dataProvider = "feeder")
	@Source("\\TestData\\CSVFiles\\Insights\\GetUserSegment.csv")
	public void testGetCreatedUserSegment(String testCaseId,String testCaseDescription,String income,String incomeCurrency,String incomeRange,String city,
			String state,String country,String zipCode,String age,String networthValue,String networthCurrency,String homeOwneShip,String lifeStage,boolean deleteUserSegment,int expStatusCode,String tcEnabled) throws ParseException, InterruptedException, IOException {
		commontUtils.isTCEnabled(tcEnabled, testCaseId);
		HttpMethodParameters httpParams = HttpMethodParameters.builder().build();
		Gson gson = new Gson();
		CreateSegment segment = insightsCommons.constructCreateUserSegmentPayload(income, incomeCurrency, incomeRange, city, state, country, zipCode, age, networthValue, networthCurrency, homeOwneShip, lifeStage);
		httpParams=insightsHelper.getHttpParams(InsightsConstants.USER_SUB_REQUEST, null, null, jwtUserAuthToken,null,null,null,null);
		httpParams.setBodyParams(gson.toJson(segment));
		httpParams.setHttpMethod("POST");
		httpParams.setContentType("application/json");
		Response createUserSegmentResponse = insightUtils.createUserSegments(httpParams, null);
		httpParams.setHttpMethod("GET");
		Response getUserSegmentResponse = insightUtils.createUserSegments(httpParams, null);
		if(createUserSegmentResponse.getStatusCode()!=expStatusCode) {
			Assert.fail("Status Code Doesn't Match for Create User Segment");
		}
		if(getUserSegmentResponse.getStatusCode()!=com.yodlee.yodleeApi.constants.HttpStatus.OK) {
			Assert.fail("Status Code Doesn't Match for Get User Segment");
		}
		insightsCommons.verifyUserSegmentResponse(testCaseId,createUserSegmentResponse.getStatusCode(), createUserSegmentResponse, negativeExpectedValuesMap);
		if(deleteUserSegment)
		 insightsDBUtils.deleteGeneratedInsights(Long.parseLong(userId.toString()),envCobrand);
		insightsCommons.verifyGetUserSegmentResponse(segment, getUserSegmentResponse);
		
	}	
	
	
	@AfterClass(alwaysRun = true)
	public void unRegisteredUser() {
		userUtils.unRegisterUser(sessionObj);		
	}

}
