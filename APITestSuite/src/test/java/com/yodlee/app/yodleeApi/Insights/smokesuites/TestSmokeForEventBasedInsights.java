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
package com.yodlee.app.yodleeApi.Insights.smokesuites;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.databene.benerator.anno.Source;
import org.jose4j.lang.JoseException;
import org.eclipse.jetty.http.HttpStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.google.gson.JsonArray;
import com.yodlee.insights.views.pojo.CommonEntity;
import com.yodlee.insights.yodleeApi.utils.AutomationTestResults;
import com.yodlee.insights.yodleeApi.utils.BoardHelper;
import com.yodlee.insights.yodleeApi.utils.FailureReason;
import com.yodlee.insights.yodleeApi.utils.InsightsDBUtils;
import com.yodlee.insights.yodleeApi.utils.InsightsGenerics;
import com.yodlee.insights.yodleeApi.utils.InsightsHelper;
import com.yodlee.insights.yodleeApi.utils.InsightsLevelVerifications;
import com.yodlee.insights.yodleeApi.utils.InsightsProperties;
import com.yodlee.insights.yodleeApi.utils.TestInfo;
import com.yodlee.insights.yodleeApi.utils.VisibleAllOver;
import com.yodlee.yodleeApi.assertions.JsonAssertionUtil;
import com.yodlee.yodleeApi.common.Base;
import com.yodlee.yodleeApi.common.Configuration;
import com.yodlee.yodleeApi.constants.JSONPaths;
import com.yodlee.yodleeApi.helper.JwtHelper;
import com.yodlee.yodleeApi.helper.SaveForGoalHelper;
import com.yodlee.yodleeApi.helper.UserHelper;
import com.yodlee.yodleeApi.pojo.EnvSession;
import com.yodlee.yodleeApi.pojo.HttpMethodParameters;
import com.yodlee.yodleeApi.pojo.User;
import com.yodlee.yodleeApi.utils.apiUtils.BoardUtils;
import com.yodlee.yodleeApi.utils.apiUtils.ProviderAccountUtils;
import com.yodlee.yodleeApi.utils.apiUtils.ProviderUtils;
import com.yodlee.yodleeApi.utils.apiUtils.SFGUtils;
import com.yodlee.yodleeApi.utils.apiUtils.TaskUtils;
import com.yodlee.yodleeApi.utils.apiUtils.UserUtils;
import com.yodlee.yodleeApi.utils.apiUtils.InsightUtils;
import com.yodlee.yodleeApi.utils.apiUtils.InvokerUtils;
import com.yodlee.yodleeApi.utils.commonUtils.CommonUtils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.restassured.response.Response;
import junit.framework.Assert;

public class TestSmokeForEventBasedInsights extends Base {
	public static Long providerAccountId = null;

	protected Logger logger = LoggerFactory.getLogger(TestSmokeForRefreshBasedInsights.class);
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
	BoardHelper boardHelper = new BoardHelper();
	CommonEntity commonEntity = new CommonEntity();
	
	@BeforeClass(alwaysRun = true)
	public void setUp() throws Exception {
		System.out.println("Initiated execution for setUp");
		insightsGenerics = new InsightsGenerics();
		insightsDBUtils = new InsightsDBUtils();
		envCobrand = prop.readPropertiesFile().getProperty("envCobrand");
		envUser = prop.readPropertiesFile().getProperty("envUser");
		sessionObj = EnvSession.builder().cobSession(configurationObj.getCobrandSessionObj().getCobSession())
				.path(configurationObj.getCobrandSessionObj().getPath()).build();
		cobrandUser = prop.readPropertiesFile().getProperty("cobrandUser");
		jwtCobAuthToken = jwtHelper.getJWTToken(cobrandUser, envCobrand);
		jwtCobAuthToken = "Bearer ".concat(jwtCobAuthToken);

	}
	
	public void addAccounts(String dagUpdateCredentials) throws Exception {
		System.out.println("initiated execution addAccounts");
		User userInfo = User.builder().build();
		// update credentials
		HttpMethodParameters httpParams = HttpMethodParameters.builder().build();
		LinkedHashMap<String, Object> queryParam = new LinkedHashMap<>();
		queryParam.put("providerAccountIds", providerAccountId);
		httpParams.setQueryParams(queryParam);
		String updateCredentialsPayload = insightsHelper.constructUpdateCredentialsPayload(dagUpdateCredentials.split(",")[0].toString(), dagUpdateCredentials.split(",")[1].toString());
		httpParams.setBodyParams(updateCredentialsPayload);
		Response response = providerAccountUtils.updateProviderAccount(httpParams, sessionObj);
		providerAccountUtils.checkRefreshStatusUntilActAddition(sessionObj, String.valueOf(providerAccountId)); 
		Thread.sleep(20000);
		entityIdsMap = insightsGenerics.getItemAccounts(sessionObj, "BANK");
		entityIdsMap.put("InvalidId", "123456");
	}
	@BeforeMethod(firstTimeOnly=true)
	public void initializations() throws Exception {		
		failureReason = new  FailureReason();		
		testsInfoList = new ArrayList<TestInfo>();
		automationTestResults.setInsights("SmokeTest");
		automationTestResults.setBuildNo("302");
		automationTestResults.setCodeCoveragePercent("78");
		automationTestResults.setStoryId("EVENT Insights");
	}

	@Test(enabled = true, dataProvider = "feeder")
	@Source("\\TestData\\CSVFiles\\Insights\\InsightsSmokeTest\\GetEventBasedInsight.csv")
	public void testGetEventBasedInsights(String testCaseId,String testCaseDescription,String insightName,String supportedContainers,
			String triggerType,String insightType,String entityParamsCount,String entityParameterName,String thresholdNameValueType,String NoOfThresolds,String noumberOfInsights,String editSubscription
			,String isCobrandSubscribed,String isUserSubscribed,String dagCredentials,String dagUpdateCredentials,String validateKeys,String expectedStatus,String tcEnabled) throws Exception {
		String patchAccountIds = "";
		commontUtils.isTCEnabled(tcEnabled, testCaseId);
		logger.info("Testcase executed ---------------------------------------------->" + testCaseId);
		System.out.println("initiated execution testGetEventBasedInsights");
		User userInfo = User.builder().build();
		userInfo.setUsername("EventInsights" + System.currentTimeMillis());
		loginUser = userInfo.getUsername();
		automationTestResults.setUserName(loginUser);
		System.out.println("User ----> " + userInfo.getUsername());
		userInfo.setPassword("TEST@123");
		sessionObj = EnvSession.builder().cobSession(configurationObj.getCobrandSessionObj().getCobSession())
				.path(configurationObj.getCobrandSessionObj().getPath()).build();
		userHelper.getUserSession(userInfo, sessionObj);
		long providerId = 16441;
		jwtUserAuthToken = jwtHelper.getJWTToken(userInfo.getUsername(), envUser);
		jwtUserAuthToken = "Bearer ".concat(jwtUserAuthToken);
		System.out.println("JWTauthToken " + jwtUserAuthToken);
		userId = insightsGenerics.getMemId(sessionObj);
		HttpMethodParameters httpParams = HttpMethodParameters.builder().build();

		boolean notificationTestStatus = true;
		logger.info("Testcase executed ---------------------------------------------->"+testCaseId);
		JsonArray keysArray = insightsGenerics.getInsightKeys(insightName);
		int numberOfKeys = insightsGenerics.getTotalNumberOfKeys(keysArray,"");
		commonEntity.setUserJwtToken(jwtUserAuthToken);
		TestInfo testInfo = new TestInfo();
		StringBuilder validations = new StringBuilder();		
		testInfo.setTestCaseId(testCaseId);
		testInfo.setTestDescription(testCaseDescription);
		if (Boolean.parseBoolean(editSubscription)) {
			if (!isCobrandSubscribed.isEmpty()) {		
				try {
					String patchCobSubscriptionBodyParam = insightsHelper.constructPatchCobrandSubscriptionRequest(insightName, "", "", "", "", "", "", "", "", "", entityParamsCount, entityParameterName, NoOfThresolds, "", "",isCobrandSubscribed, "", "", thresholdNameValueType);
					httpParams=insightsHelper.getHttpParams("cobrandSubscriptionRequest", null, null, jwtCobAuthToken,null,null,null,null);
					httpParams.setBodyParams(patchCobSubscriptionBodyParam);
					Response patchCobSubscriptionResponse = null;
					int attempts=0;
					do {
						Thread.sleep(2000);
						patchCobSubscriptionResponse = insightUtils.patchCobrandSubscriptions(httpParams,null);
					}while(patchCobSubscriptionResponse.getStatusCode()!=HttpStatus.NO_CONTENT_204 && ++attempts<=4);						
					if(patchCobSubscriptionResponse.getStatusCode()!=HttpStatus.NO_CONTENT_204  ){
						logger.info("Cobrand subscription update FAILED");	
						notificationTestStatus=false;
						failureReason.setFailureReason("Cobrand subscription update FAILED");
					}
				}catch(Exception e) {
					e.printStackTrace();
				}
			}
			if (!isUserSubscribed.isEmpty()) {    
				String patchUserSubscriptionBodyParam = null;
				if(!patchAccountIds.isEmpty()) {
					String[] itemAccounts = patchAccountIds.split("\\s+");
					StringBuffer itemAccountIds = new StringBuffer();
					for(int i=0;i<itemAccounts.length;i++) {
						String itemAccountId = entityIdsMap.get(itemAccounts[i]);
						if(i!=itemAccounts.length-1) {
						   itemAccountIds.append(itemAccountId);
						   itemAccountIds.append("   ");
						}
						else {
							itemAccountIds.append(itemAccountId);
						}
					}					
				    patchUserSubscriptionBodyParam = insightsHelper.constructPatchUserSubscriptionRequest(insightName, "", "", "", "", "", "", "", "", "", entityParamsCount,"", entityParameterName, NoOfThresolds, itemAccountIds.toString(), "", isUserSubscribed, "", "", thresholdNameValueType,"");				   
				}
				else {
					patchUserSubscriptionBodyParam = insightsHelper.constructPatchUserSubscriptionRequest(insightName, "", "", "", "", "", "", "", "", "", entityParamsCount,"", entityParameterName, NoOfThresolds, "", "", isUserSubscribed, "", "", thresholdNameValueType,"");
				}
				httpParams=insightsHelper.getHttpParams("userSubscriptionRequest", null, null, jwtUserAuthToken,null,null,null,null);
				httpParams.setBodyParams(patchUserSubscriptionBodyParam);	
				Response patchUserSubscriptionResponse = insightUtils.patchUserSubscriptions(httpParams,null);						
				if (patchUserSubscriptionResponse.getStatusCode() != HttpStatus.NO_CONTENT_204 ) {
						logger.info("User subscription update FAILED");	
						failureReason.setFailureReason("User subscription update FAILED");
						notificationTestStatus=false;
					}
			}
		}
		
		providerAccountId = providerId;
		Response response = providerAccountUtils.addProviderAccountStrict(providerId, "fieldarray", dagCredentials.split(",")[0].toString(), dagCredentials.split(",")[1].toString(), sessionObj);
		providerAccountId = response.jsonPath().getLong(JSONPaths.PROVIDER_ACC_ID);
		System.out.println("providerAccountId1::::===" + providerAccountId);
		
		if(!dagUpdateCredentials.isEmpty()){
			System.out.println("not required");
			addAccounts(dagUpdateCredentials);
		}
		Thread.sleep(4000);
	      int expectedNumberOfInsights =Integer.parseInt(noumberOfInsights);
	        Response userInsightsResponse = null;
	        httpParams=insightsHelper.getHttpParams("notificationsRequest", null, insightName, jwtUserAuthToken,null,null,null,null);
			userInsightsResponse = insightUtils.getNotificationsV0(httpParams,null);
			Assert.assertEquals(userInsightsResponse.getStatusCode(), HttpStatus.OK_200);
			testInfo.setResponse(userInsightsResponse.asString());
			notificationTestStatus = insightsLevelVerifications.verifyInsightsForSmokeTest(userInsightsResponse,insightName,"EVENT",entityParameterName.toUpperCase(),entityParameterName,thresholdNameValueType,
					  noumberOfInsights,numberOfKeys,failureReason,keysArray,entityIdsMap,validateKeys);
		 insightsDBUtils.deleteGeneratedInsights(Long.parseLong(userId.toString()),envCobrand);
		 testInfo.setValidations(validations.toString());
		if(notificationTestStatus) {
			testInfo.setTestStatus("PASSED");
			notificationsTestSummary.put(testCaseId+"_"+insightName+"_"+testCaseDescription , "PASSED");
			testsInfoList.add(testInfo);
		}else {
			testInfo.setTestStatus("FAILED");
			testInfo.setReasonForFailure(failureReason.getFailureReason());
			notificationsTestSummary.put(testCaseId+"_"+insightName+"_"+testCaseDescription , "FAILED");
			notificationsTestSummary.put(testCaseId+"_"+insightName+"_"+"Failed Reason ### ", failureReason.getFailureReason());
			testsInfoList.add(testInfo);
			Assert.fail("TEST FAILED");
		}
	}	
	
	@AfterMethod(lastTimeOnly = true)
	public void prepareAutomationReport() throws Exception {
		TestInfo testInfo = new TestInfo();
		testInfo = insightsHelper.printSummaryOfTests(notificationsTestSummary,"GET  EVENT Based Insights",testInfo);		
		automationTestResults.setExecutionDate(new Date().toString());
		automationTestResults.setTestInformation(testsInfoList);
		automationTestResults.setUserName(loginUser);
		automationTestResults.setTestsPassPercentage(automationTestResults.getTestsPassPercentage(testInfo));
	    insightsDBUtils.addTestResults(automationTestResults);
		System.out.println("Insight Test Execution is completed");
	}
	
	@AfterClass(alwaysRun = true)
	public void unRegisteredUser() {
	 userUtils.unRegisterUser(sessionObj);		
	}
}
