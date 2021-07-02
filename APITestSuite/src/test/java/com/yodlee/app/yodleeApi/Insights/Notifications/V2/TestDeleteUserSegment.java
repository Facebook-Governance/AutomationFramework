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
package com.yodlee.app.yodleeApi.Insights.Notifications.V2;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import org.databene.benerator.anno.Source;
import org.eclipse.jetty.http.HttpStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.google.gson.Gson;
import com.google.gson.JsonParser;
import com.yodlee.insights.views.pojo.CommonEntity;
import com.yodlee.insights.yodleeApi.utils.AutomationTestResults;
import com.yodlee.insights.yodleeApi.utils.CreateSegment;
import com.yodlee.insights.yodleeApi.utils.FailureReason;
import com.yodlee.insights.yodleeApi.utils.InsightsCommons;
import com.yodlee.insights.yodleeApi.utils.InsightsConstants;
import com.yodlee.insights.yodleeApi.utils.InsightsDBUtils;
import com.yodlee.insights.yodleeApi.utils.InsightsGenerics;
import com.yodlee.insights.yodleeApi.utils.InsightsHelper;
import com.yodlee.insights.yodleeApi.utils.InsightsLevelVerifications;
import com.yodlee.insights.yodleeApi.utils.InsightsProperties;
import com.yodlee.insights.yodleeApi.utils.NotifiedInsightsDataValidation;
import com.yodlee.insights.yodleeApi.utils.TestInfo;
import com.yodlee.yodleeApi.assertions.JsonAssertionUtil;
import com.yodlee.yodleeApi.common.Base;
import com.yodlee.yodleeApi.common.Configuration;
import com.yodlee.yodleeApi.helper.JwtHelper;
import com.yodlee.yodleeApi.helper.SaveForGoalHelper;
import com.yodlee.yodleeApi.helper.UserHelper;
import com.yodlee.yodleeApi.pojo.EnvSession;
import com.yodlee.yodleeApi.pojo.HttpMethodParameters;
import com.yodlee.yodleeApi.pojo.User;
import com.yodlee.yodleeApi.utils.apiUtils.ProviderAccountUtils;
import com.yodlee.yodleeApi.utils.apiUtils.TaskUtils;
import com.yodlee.yodleeApi.utils.apiUtils.UserUtils;
import com.yodlee.yodleeApi.utils.apiUtils.BoardUtils;
import com.yodlee.yodleeApi.utils.apiUtils.InsightUtils;
import com.yodlee.yodleeApi.utils.apiUtils.InsightsUtilV1;
import com.yodlee.yodleeApi.utils.apiUtils.InvokerUtils;
import com.yodlee.yodleeApi.utils.commonUtils.CommonUtils;

import io.restassured.response.Response;
import junit.framework.Assert;

public class TestDeleteUserSegment extends Base {
	public static Long providerAccountId = null;

	String loginName, password;
	String userSession = "";
	EnvSession sessionObj = null;
	Configuration configurationObj = Configuration.getInstance();
	UserUtils userUtils = new UserUtils();
	ProviderAccountUtils providerAccountUtils = new ProviderAccountUtils();
	CommonUtils commonUtils = new CommonUtils();
	SaveForGoalHelper saveForGoalHelper = new SaveForGoalHelper();
	UserHelper userHelper = new UserHelper();
	JsonAssertionUtil jsonAssertionutil = new JsonAssertionUtil();
	SaveForGoalHelper sfg = new SaveForGoalHelper();
	CommonUtils commontUtils = new CommonUtils();
	JwtHelper jwtHelper = new JwtHelper();
	TaskUtils taskUtils = null;
	InsightUtils insightUtils = new InsightUtils();
	InvokerUtils invokerUtils = new InvokerUtils();
	InsightsHelper insightsHelper = new InsightsHelper();
	InsightsGenerics insightsGenerics = null;
	InsightsLevelVerifications insightsLevelVerifications = new InsightsLevelVerifications();
	HashMap<String,ArrayList<String>> negativeExpectedValuesMap;
	String loginUser = null;
	String  jwtUserAuthToken = null;
	FailureReason failureReason = new  FailureReason();
	static Map<String ,String>  notificationsTestSummary = new HashMap<String,String>();
	HashMap<String,String> entityIdsMap = new HashMap<String,String>();
	Number userId = null;
	JsonParser jsonParser = new JsonParser();
	List<TestInfo> testsInfoList;
	String insightExpectedKeys = null;
	InsightsDBUtils insightsDBUtils;
	AutomationTestResults automationTestResults = new AutomationTestResults();
	BoardUtils boardUtils = new BoardUtils();
	String jwtCobAuthToken = "";
	protected Logger logger = LoggerFactory.getLogger(TestGetInsightsForUnableToRefreshAccount.class);
	InsightsProperties prop = new InsightsProperties();	
	String envCobrand=null;
	String envUser=null,cobrandUser=null;
	Response userInsightsResponse = null;
	CommonEntity commonEntity = new CommonEntity();
	InsightsCommons insightsCommons = new InsightsCommons();
	NotifiedInsightsDataValidation validateInsights = null;
	User userInfo = User.builder().build();
	InsightsUtilV1 insightsUtilsV2 = new InsightsUtilV1();
	
	
	
	@BeforeClass(alwaysRun = true)
	public void setUp() throws Exception {
		System.out.println("initiated execution for DELETE User Segment API");
		insightsDBUtils = new InsightsDBUtils();
		System.out.println("initiated execution");	
		cobrandUser = prop.readPropertiesFile().getProperty("cobrandUser");
		String cobrand = prop.readPropertiesFile().getProperty("envCobrand");
		envCobrand = (configurationObj.getApiVersion().equals("1.1")?cobrand:(configurationObj.getApiVersion().equals("2")?cobrand.substring(0, cobrand.length()-2):null));		
		String user=prop.readPropertiesFile().getProperty("envUser");	
		envUser = (configurationObj.getApiVersion().equals("1.1")?user:(configurationObj.getApiVersion().equals("2")?user.substring(0, user.length()-2):null));
		insightsGenerics = new InsightsGenerics();	
		User userInfo = User.builder().build();
	    userInfo.setUsername("DeleteSegment" + System.currentTimeMillis());
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
		Thread.sleep(20000);
		negativeExpectedValuesMap = insightsGenerics.readNegativeExpectedValuesForUserSegment();
		}
	
	@Test(enabled = true, dataProvider = "feeder")
	@Source("\\TestData\\CSVFiles\\Insights\\DeleteUserSegment.csv")
	public void testDeleteUserSegment(String testCaseId,String testCaseDescription,String income,String incomeCurrency,String incomeRange,String city,
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
		if(createUserSegmentResponse.getStatusCode()!=com.yodlee.yodleeApi.constants.HttpStatus.CREATED) {
			Assert.fail();
		}
		String segmentId = createUserSegmentResponse.jsonPath().getString("id");		
		httpParams.setHttpMethod("DELETE");
		httpParams.setContentType("application/json");
		LinkedHashMap<String, Object> pathParam =new LinkedHashMap<>(); 
		pathParam.put("segmentId", segmentId);
		httpParams.setPathParams(pathParam);
		Response deleteUserSegmentResponse = insightUtils.createUserSegments(httpParams, null);
		if(deleteUserSegmentResponse.getStatusCode()!=expStatusCode) {
			Assert.fail();
		}
		httpParams.setHttpMethod("GET");
		httpParams.setContentType("application/json");
		httpParams.setPathParams(null);
		Response getUserSegmentResponse = insightUtils.createUserSegments(httpParams, null);
		String responseString = getUserSegmentResponse.asString();
		if(deleteUserSegment)
		  insightsDBUtils.deleteGeneratedInsights(Long.parseLong(userId.toString()),envCobrand);		
		
	}	
	
	
	@AfterClass(alwaysRun = true)
	public void unRegisteredUser() {
		userUtils.unRegisterUser(sessionObj);		
	}

}
