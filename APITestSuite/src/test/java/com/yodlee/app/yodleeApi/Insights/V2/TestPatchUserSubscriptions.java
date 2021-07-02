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
package com.yodlee.app.yodleeApi.Insights.V2;


import static org.testng.Assert.assertEquals;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.databene.benerator.anno.Source;
import org.jose4j.lang.JoseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.yodlee.app.yodleeApi.Insights.Notifications.V2.TestGetInsightsForLowBalanceWarning;
import com.yodlee.insights.views.pojo.CommonEntity;
import com.yodlee.insights.yodleeApi.utils.AutomationTestResults;
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
import com.yodlee.insights.yodleeApi.utils.v2.InsightsSubscriptionsVerification;
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


public class TestPatchUserSubscriptions extends Base {
	public static Long providerAccountId = null;

	protected Logger logger = LoggerFactory.getLogger(TestPatchUserSubscriptions.class);
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
	JsonParser jsonParser = new JsonParser();
	InsightUtils insightUtils = new InsightUtils();
	InvokerUtils invokerUtils = new InvokerUtils();
	InsightsUtilV1 insightsUtilsV2 = new InsightsUtilV1();
	InsightsHelper insightsHelper = new InsightsHelper();
	InsightsGenerics insightsGenerics = new InsightsGenerics();
	InsightsLevelVerifications insightsLevelVerifications = new InsightsLevelVerifications();
	HashMap<String,ArrayList<String>> negativeExpectedValuesMap;
	String loginUser = null;
	String  jwtUserAuthToken = null;
	FailureReason failureReason = new  FailureReason();
	static Map<String ,String>  notificationsTestSummary = new HashMap<String,String>();
	HashMap<String,String> entityIdsMap = new HashMap<String,String>();	
	HashMap<String,HashMap<String,List>> transactionsIdsMap = new HashMap<String,HashMap<String,List>>();	
	Number userId = null;
	List<TestInfo> testsInfoList;
	String insightExpectedKeys = null;
	InsightsDBUtils insightsDBUtils = null;
	AutomationTestResults automationTestResults = new AutomationTestResults();
	BoardUtils boardUtils = new BoardUtils();	
	String jwtCobAuthToken = "";
	InsightsProperties prop = new InsightsProperties();	
	String envCobrand=null;
	String envUser=null;
	String cobrandUser=null;
	String buildNo="421",codeCoverage="80";
	Response userInsightsResponse = null;
	CommonEntity commonEntity = new CommonEntity();
	InsightsCommons insightsCommons = new InsightsCommons();
	NotifiedInsightsDataValidation validateInsights = null;	
	HttpMethodParameters httpParams = null;
	InsightsSubscriptionsVerification subscriptionsVerification = new InsightsSubscriptionsVerification();
	Response getUserSubscriptionResponseBeforePatch = null;

	@BeforeClass(alwaysRun = true)
	public void setUp() throws Exception {
		System.out.println("initiated PATCH User subscription API execution");		
		 prop = new InsightsProperties();
		String cobrand = prop.readPropertiesFile().getProperty("envCobrand");
		cobrandUser = prop.readPropertiesFile().getProperty("cobrandUser");
		envCobrand = (configurationObj.getApiVersion().equals("1.1") ? cobrand: (configurationObj.getApiVersion().equals("2") ? cobrand.substring(0, cobrand.length() - 2) : null));
		String user = prop.readPropertiesFile().getProperty("envUser");
		envUser = (configurationObj.getApiVersion().equals("1.1") ? user: (configurationObj.getApiVersion().equals("2") ? user.substring(0, user.length() - 2) : null));
		User userInfo = User.builder().build();
		userInfo.setUsername("GetUserSub" + System.currentTimeMillis());
		// userInfo.setUsername("ldnuser888"); //sense user and wellness
		loginUser = userInfo.getUsername();
		System.out.println("User ----> " + userInfo.getUsername());
		userInfo.setPassword("TEST@123");
		sessionObj = EnvSession.builder().cobSession(configurationObj.getCobrandSessionObj().getCobSession())
				.path(configurationObj.getCobrandSessionObj().getPath()).build();
		userHelper.getUserSession(userInfo, sessionObj);
		// userHelper.getUserSession(userInfo.getUsername(),userInfo.getPassword(),sessionObj)		
		entityIdsMap.put("accountEntiy", "13332687");
		entityIdsMap.put("viewEntity", "5fd86d39480b14531bc40bc7");
		entityIdsMap.put("NA", "SKIP");
		negativeExpectedValuesMap = insightsGenerics.getNegativeScenarioErrorCodesAndMessages();
	}

	
	public void initializations() throws Exception {
		jwtCobAuthToken = "Bearer ".concat(jwtHelper.getJWTToken(cobrandUser, envCobrand));
		jwtUserAuthToken = "Bearer ".concat(jwtHelper.getJWTToken(loginUser, envUser));	
		Thread.sleep(10000);		
		httpParams=insightsHelper.getHttpParams("userSubscriptionRequest", null, null, jwtUserAuthToken,null,null,null,null);		
		httpParams.setHttpMethod("GET");
		httpParams.setContentType("application/json");
		getUserSubscriptionResponseBeforePatch = insightsUtilsV2.getUserSubscriptions(httpParams, null);
	}

	@Test(enabled = true, dataProvider = "feeder")
	@Source("\\TestData\\CSVFiles\\Insights\\SubscriptionsV2\\UserSubscriptions\\PATCHUserSubscriptionsAllInsights.csv")	
	public void testPatchUserSubscriptions(String testCaseId,String testCaseDescription,String negativeTestScenario,String insightName,String insightTitle
			,String insightType,String 	triggerType,String 	containers,String 	description,String 	applicableEntities,String 	entityNames,String 	subscription
			,String thresholdNameValueType,String 	frequency,String 	duration,String 	patchEntityIds,int 	httpStatus,String 	testEnabled)
            throws Exception {
		commontUtils.isTCEnabled(testEnabled, testCaseId);	
		if(insightName.equals("BILL_NOT_PAIDEDIT") || insightName.equals("CHANGE_IN_NETWORTHEDIT") || insightName.equals("SUBSCRIPTION_CHANGEDEDIT")
				|| insightName.equals("LONG_TENURE_RECURRING_PAYMENTSEDIT") || insightName.equals("UNABLE_TO_REFRESH_ACCOUNTEDIT")) {
			initializations();
		}
		String schemaFile=insightName+".json";
		logger.info("PATCH  User Subscription for Insight ..." +insightName.toUpperCase() + " And TestCaseId = "+testCaseId);			
		processPatchSubscription(testCaseId,insightName,insightTitle,insightType, 
				triggerType,containers,description,applicableEntities,entityNames,thresholdNameValueType,frequency,duration,subscription,patchEntityIds,negativeExpectedValuesMap,negativeTestScenario,httpStatus,schemaFile);
	}
	
	public void processPatchSubscription(String testCaseId,String insightName,String insightTitle,String insightType,String triggerType,String containers,
			String description,String  applicableEntities,String entityNames,String thresholdNameValueType,String frequency,String duration,String subscription,
			String patchEntityIds,HashMap<String,ArrayList<String>> negativeExpectedValuesMap,String negativeTestScenario,int expectedStatusCode,String schemaFile) throws IOException {
		
		String  patchRequestPayload = insightsCommons.constructPatchSubscriptionRequestForCustomerAndUser(InsightsConstants.USER_SUBSCRIPTION_CONTEXT,insightName,insightTitle,insightType, 
									  triggerType,containers,description,applicableEntities,entityNames,thresholdNameValueType,frequency,duration,subscription,patchEntityIds,entityIdsMap,"");		
		Response getUserSubscriptionResponseAfterPatch=null,patchUserSubscriptionResponse=null;
		httpParams.setHttpMethod("PATCH");
		httpParams.setBodyParams(patchRequestPayload);
		patchUserSubscriptionResponse = insightsUtilsV2.patchUserSubscriptions(httpParams, null);
		httpParams = insightsHelper.getHttpParams(InsightsConstants.GET_USER_SUBSCRIPTION_REQUEST, null, insightName,jwtUserAuthToken, null, null, null, null);
		httpParams.setHttpMethod("GET");		
		getUserSubscriptionResponseAfterPatch = insightsUtilsV2.getUserSubscriptions(httpParams, null);			
		subscriptionsVerification.verifyPatchSubscriptionRequestResponse(InsightsConstants.USER_SUBSCRIPTION_CONTEXT,testCaseId,insightName,patchUserSubscriptionResponse, getUserSubscriptionResponseBeforePatch, getUserSubscriptionResponseAfterPatch,negativeTestScenario,negativeExpectedValuesMap,expectedStatusCode,schemaFile);
	}	

}
