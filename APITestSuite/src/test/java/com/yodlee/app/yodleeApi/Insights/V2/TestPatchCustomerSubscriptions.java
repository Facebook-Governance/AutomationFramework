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


public class TestPatchCustomerSubscriptions extends Base {

	public static Long providerAccountId = null;

	protected Logger logger = LoggerFactory.getLogger(TestPatchCustomerSubscriptions.class);
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
	String cobrandUser=null;
	String buildNo="421",codeCoverage="80";
	Response userInsightsResponse = null;
	InsightsCommons insightsCommons = new InsightsCommons();
	NotifiedInsightsDataValidation validateInsights = null;	
	Response customerSubscriptionResponse = null, getUserSubscriptionResponse = null,getCustomerSubscriptionResponseBeforePatch=null;
	String envCobrand = null;
	Map<String, HashMap<String, Boolean>> allTestResultsMap = new HashMap<String, HashMap<String, Boolean>>();
	String envUser = null;
	CommonEntity commonEntity = new CommonEntity();
	InsightsSubscriptionsVerification subscriptionsHelper = new InsightsSubscriptionsVerification();
	HttpMethodParameters httpParams = null;
	InsightsSubscriptionsVerification subscriptionsVerification = new InsightsSubscriptionsVerification();

	@BeforeClass(alwaysRun = true)
	public void setUp() throws Exception {
		System.out.println("initiated PATCH Customer subscription API execution");
		String cobrand = prop.readPropertiesFile().getProperty("envCobrand");
		cobrandUser =  prop.readPropertiesFile().getProperty("cobrandUser");
		envCobrand = (configurationObj.getApiVersion().equals("1.1")?cobrand:(configurationObj.getApiVersion().equals("2")?cobrand.substring(0, cobrand.length()-2):null));		
		String user=prop.readPropertiesFile().getProperty("envUser");	
		envUser = (configurationObj.getApiVersion().equals("1.1")?user:(configurationObj.getApiVersion().equals("2")?user.substring(0, user.length()-2):null));
		entityIdsMap.put("accountEntiy", "13332687");
		entityIdsMap.put("viewEntity", "5fd86d39480b14531bc40bc7");
		entityIdsMap.put("NA", "SKIP");
		negativeExpectedValuesMap = insightsGenerics.getNegativeScenarioErrorCodesAndMessages();
	}

	@BeforeMethod(firstTimeOnly=true)
	public void initializations() throws Exception {
		jwtCobAuthToken = "Bearer ".concat(jwtHelper.getJWTToken(cobrandUser,envCobrand));
		//jwtCobAuthToken = "Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzUxMiJ9.eyJzdWIiOiJtc2FtcGF0aEB5b2RsZWUuY29tIiwiaXNzIjoiYTNhNjFjMGEtYWI5Zi1lZTc5LTFmNmYtNmUwMGIzOTc0MzhjIiwiY29icmFuZElkIjoiMTAwMTEzNzMiLCJleHAiOjE2MTc2MTI1NjIsImlhdCI6MTYxNzYxMTA2Mn0.IJaMjQJP5p5cglafp06B052xcr77BrR9bCVphxPa4v_DQQCzfY2bhtJdbhRQuBb1tF_b2WeznngRTWAkH7nnASlu3IzWK09OGpHOvFJo7jv8tOsWJeDnRIAswi6pLl-9BEfwOJJthMg9Qvl3d3V3o-zINBcHubB3n7pxENZSt3QCt5vX70_apAlLmIBlYIjc8objGyMJkQKOMf-NdP2z_vZcseC7SBYBhs-5Z1RmNnrMyHSYsgqYrkSdqWgD6RoqwxapDilmepsgmOHj9Iaf88sCEQ_MSJ-GkvMXAMS4wA5mGst8gntHPryMRop-EtRQIoPxnXfLJPEfJ1m7xthNOg";
		httpParams=insightsHelper.getHttpParams("cobrandSubscriptionRequest", null, null, jwtCobAuthToken,null,null,null,null);
		Thread.sleep(20000);
		httpParams.setHttpMethod("GET");
		httpParams.setContentType("application/json");
		getCustomerSubscriptionResponseBeforePatch = insightsUtilsV2.getCustomerSubscriptions(httpParams, null);
	}

	@Test(enabled = true, dataProvider = "feeder")
	@Source("\\TestData\\CSVFiles\\Insights\\SubscriptionsV2\\CustomerSubscriptions\\PATCHCustomerSubscriptionsAllInsights.csv")	
	public void testPatchCustomerSubscriptions(String testCaseId,String testCaseDescription,String negativeTestScenario,String insightName,String insightTitle
			,String insightType,String 	triggerType,String 	containers,String 	description,String 	applicableEntities,String 	entityNames,String 	subscription
			,String thresholdNameValueType,String 	frequency,String 	duration,String 	patchEntityIds,int 	httpStatus,String 	testEnabled)
            throws ParseException, InterruptedException, JoseException, IOException {
		commontUtils.isTCEnabled(testEnabled, testCaseId);	
		
		String schemaFile=insightName+".json";
		logger.info("PATCH  Customer Subscription for Insight ..." +insightName.toUpperCase() + " And TestCaseId = "+testCaseId);			
		processPatchSubscription(testCaseId,insightName,insightTitle,insightType, 
				triggerType,containers,description,applicableEntities,entityNames,thresholdNameValueType,frequency,duration,subscription,patchEntityIds,negativeExpectedValuesMap,negativeTestScenario,httpStatus,schemaFile);
	}
	
	public void processPatchSubscription(String testCaseId,String insightName,String insightTitle,String insightType,String triggerType,String containers,
			String description,String  applicableEntities,String entityNames,String thresholdNameValueType,String frequency,String duration,String subscription,
			String patchEntityIds,HashMap<String,ArrayList<String>> negativeExpectedValuesMap,String negativeTestScenario,int expectedStatusCode,String schemaFile) {
		
		String  patchRequestPayload = insightsCommons.constructPatchSubscriptionRequestForCustomerAndUser(InsightsConstants.COB_SUBSCRIPTION_CONTEXT,insightName,insightTitle,insightType, 
									  triggerType,containers,description,applicableEntities,entityNames,thresholdNameValueType,frequency,duration,subscription,patchEntityIds,entityIdsMap,"");		
		Response getCustomerSubscriptionResponseAfterPatch=null,patchCustomerSubscriptionResponse=null;
		httpParams.setHttpMethod("PATCH");
		httpParams.setBodyParams(patchRequestPayload);
		patchCustomerSubscriptionResponse = insightsUtilsV2.patchCustomerSubscriptions(httpParams, null);
		httpParams.setHttpMethod("GET");
		getCustomerSubscriptionResponseAfterPatch = insightsUtilsV2.getCustomerSubscriptions(httpParams, null);			
		subscriptionsVerification.verifyPatchSubscriptionRequestResponse(InsightsConstants.COB_SUBSCRIPTION_CONTEXT,testCaseId,insightName,patchCustomerSubscriptionResponse, getCustomerSubscriptionResponseBeforePatch, getCustomerSubscriptionResponseAfterPatch,negativeTestScenario,negativeExpectedValuesMap,expectedStatusCode,schemaFile);
	}


	

}
