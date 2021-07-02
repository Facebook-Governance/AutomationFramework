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
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.databene.benerator.anno.Source;
import org.eclipse.jetty.http.HttpStatus;
import org.jose4j.lang.JoseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.yodlee.insights.subscriptions.pojo.Entity;
import com.yodlee.insights.views.pojo.CommonEntity;
import com.yodlee.insights.yodleeApi.utils.AutomationTestResults;
import com.yodlee.insights.yodleeApi.utils.ExpectedResultValues;
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
import com.yodlee.yodleeApi.utils.apiUtils.InsightsUtilV1;
import com.yodlee.yodleeApi.utils.apiUtils.ProviderAccountUtils;
import com.yodlee.yodleeApi.utils.apiUtils.SFGUtils;
import com.yodlee.yodleeApi.utils.apiUtils.TaskUtils;
import com.yodlee.yodleeApi.utils.apiUtils.UserUtils;
import com.yodlee.yodleeApi.utils.apiUtils.BoardUtils;
import com.yodlee.yodleeApi.utils.apiUtils.InsightUtils;
import com.yodlee.yodleeApi.utils.apiUtils.InvokerUtils;
import com.yodlee.yodleeApi.utils.commonUtils.CommonUtils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.restassured.response.Response;
import junit.framework.Assert;

public class TestGetInsightsForZeroCardInterestPaid extends Base {
	public static Long providerAccountId = null;

	protected Logger logger = LoggerFactory.getLogger(TestGetInsightsForZeroCardInterestPaid.class);
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
	String buildNo="421",codeCoverage="80";
	Response userInsightsResponse = null;
	CommonEntity commonEntity = new CommonEntity();
	InsightsCommons insightsCommons = new InsightsCommons();
	NotifiedInsightsDataValidation validateInsights = null;
	InsightsUtilV1 insightsUtilsV2 = new InsightsUtilV1();
	HashMap<String,ArrayList> itemAccountsToTxnsMap = new HashMap<String,ArrayList>();	
	String accountEntitySchema = "ZeroCardInterestPaidAccountEntitySchema.json";

	public void setUp(String credentials) throws Exception {
		
		System.out.println("initiated execution for Large Purchase Insight");	
		insightsGenerics = new InsightsGenerics();
		insightsDBUtils = new InsightsDBUtils();
		String cobrand = prop.readPropertiesFile().getProperty("envCobrand");
		cobrandUser =  prop.readPropertiesFile().getProperty("cobrandUser");
		envCobrand = (configurationObj.getApiVersion().equals("1.1")?cobrand:(configurationObj.getApiVersion().equals("2")?cobrand.substring(0, cobrand.length()-2):null));		
		String user=prop.readPropertiesFile().getProperty("envUser");	
		envUser = (configurationObj.getApiVersion().equals("1.1")?user:(configurationObj.getApiVersion().equals("2")?user.substring(0, user.length()-2):null));
		jwtCobAuthToken = jwtHelper.getJWTToken(cobrandUser,envCobrand);	
		jwtCobAuthToken = "Bearer ".concat(jwtCobAuthToken);
		insightsDBUtils.disableCustomerSubscriptions(envCobrand,jwtCobAuthToken,null);	
		User userInfo = User.builder().build();
		userInfo.setUsername("ZeroCard" + System.currentTimeMillis());
		//userInfo.setUsername("ldnuser888"); //sense user and wellness
		loginUser = userInfo.getUsername();
		System.out.println("User ----> "+userInfo.getUsername());
		userInfo.setPassword("TEST@123");
		sessionObj = EnvSession.builder().cobSession(configurationObj.getCobrandSessionObj().getCobSession())
				.path(configurationObj.getCobrandSessionObj().getPath()).build();
	    userHelper.getUserSession(userInfo, sessionObj);
	    //userHelper.getUserSession(userInfo.getUsername(),userInfo.getPassword(),sessionObj)	;	
	    commonEntity.setEnvSession(sessionObj);
		VisibleAllOver.setInstance(commonEntity);	
		jwtUserAuthToken = jwtHelper.getJWTToken(userInfo.getUsername(),envUser);	
		jwtUserAuthToken = "Bearer ".concat(jwtUserAuthToken);
		//jwtCobAuthToken = "Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzUxMiJ9.eyJzdWIiOiJtc2FtcGF0aEB5b2RsZWUuY29tIiwiaXNzIjoiYTNhNjFjMGEtYWI5Zi1lZTc5LTFmNmYtNmUwMGIzOTc0MzhjIiwiY29icmFuZElkIjoiMTAwMTEzNzMiLCJleHAiOjE2MTcxODQ0NzYsImlhdCI6MTYxNzE4Mjk3Nn0.BKDILQY9XSftyIlKrKyxuDNPVF1BE7XgxINC0e-1mhBEI0Mvghm2L7hvobljx7oj7V64zBG3iD0-gDjxGDg-Hlxm4199uNG--lYLLslrBkRJfcVOKfcWBj7BCyx9Tl2PsHszxF2tVsPAKr-z0rFtZ2maIBuX0cdUujeCZuErkCt0_DLBZJ_KxEddrDwyO9FTcIR6uzgvjDfcn2jYUlCawY8fEIDcmjezFczMN3485Tn9nKrCAM2dPPAoSLZSqQFlfXW1yHHNFxjjzkaTrarbfFRxWqj2sQHEYBkXO-4tNjrk3qCLygAhuifX_YLiPQdfQL9-JhJtkjxUvSjYsmcIoA";
		//jwtUserAuthToken= "Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzUxMiJ9.eyJzdWIiOiJsZG51c2VyODg4IiwiaXNzIjoiMDA5OGMyZWQtMjY0NWQ4NTQtYjJmYy00YjBhLWFiNGYtM2I3MDQ5NTZiNzZkIiwiZXhwIjoxNjE3MTg0NDk1LCJpYXQiOjE2MTcxODI5OTV9.VC0RMcE6Ao2qfZLkQoM5EKmivMf38q_m-xyb6WU74W7ksr_zxdJUY3MuzpoffOvjua7DBNDUVhYOFT6ikSlKgaJ8ES64Mih-JZDJg9sMiZ2lY9GSwopsyfVjzcbbKhq17UgAi1AQuVu1BqPcejk6oavRuJ6aWy8B2EYQP879AwIDJUnDsBqLdWfbW-19HnnSozE9dCXF_WX72fsEpY2uk6ip_soDIh-f54WTq9klYPcnhbRu6Ey8rfYoaipyFOWZNOLU-6jr1ALBrGLYHlVNpdTKXP8K2dTKj9JlrATFzMtqxcWhNYq-UoivaYTIivHsaQwnqXaocZ5odVDcibuyxg";
		commonEntity.setUserJwtToken(jwtUserAuthToken);
		userId = insightsGenerics.getMemId(sessionObj);
		System.out.println("jwtCobAuthToken = " +jwtCobAuthToken);
		System.out.println("JWTauthToken =  " +jwtUserAuthToken);
	}
	
	@BeforeMethod(firstTimeOnly=true)
	public void initializations() throws Exception {		
		failureReason = new  FailureReason();		
		testsInfoList = new ArrayList<TestInfo>();
		automationTestResults.setInsights("ZeroCardInterestPaid");
		automationTestResults.setBuildNo("300");
		automationTestResults.setCodeCoveragePercent("78");
		automationTestResults.setStoryId("B-66496");
	}

	@Test(enabled = true, dataProvider = "feeder")
	@Source("\\TestData\\CSVFiles\\Insights\\V2Feeds\\GetZeroCardInterestPaidInsight.csv")
	public void testGetNotificationsForZeroCardInterestPaid(String testCaseId,String testCaseDescription,String insightName,String triggerCheckEnabled,String triggerType,String entityParamsCount,String entityParameterName,String noumberOfInsights,String editSubscription
			,String isCobrandSubscribed,String isUserSubscribed,String patchEntityIds,String expectedInsightsForPatchedIds,String expectedNumberOfPatchedInsights,String validateKeys,String credentials,String expectedStatus,String tcEnabled) throws Exception {
		commontUtils.isTCEnabled(tcEnabled, testCaseId);
		Boolean noInsightGenerated=false;
		setUp(credentials);
		validateInsights = new NotifiedInsightsDataValidation();
		boolean notificationTestStatus = true;
		List<Boolean> isUserPatchedAndExpectedInsight=new ArrayList<Boolean>();
		for(String str:expectedInsightsForPatchedIds.split("\\s+")) 
			isUserPatchedAndExpectedInsight.add(Boolean.parseBoolean(str));
		boolean isCobSubscribed = !isCobrandSubscribed.isEmpty()?Boolean.parseBoolean(isCobrandSubscribed):false;
		HttpMethodParameters httpParams = HttpMethodParameters.builder().build();
		ExpectedResultValues expectedResultValues = new ExpectedResultValues();
		expectedResultValues.setTestNotificationStatus(true);
		logger.info("Testcase executed ---------------------------------------------->"+testCaseId);
		JsonArray keysArray = insightsGenerics.getInsightKeys(insightName);
		int numberOfKeys = insightsGenerics.getTotalNumberOfKeys(keysArray,validateKeys);
		TestInfo testInfo = new TestInfo();
		StringBuilder validations = new StringBuilder();		
		testInfo.setTestCaseId(testCaseId);
		testInfo.setTestDescription(testCaseDescription);			
		if (Boolean.parseBoolean(editSubscription)) {
			if (!isCobrandSubscribed.isEmpty()) {		
				try {					
					if(!isCobrandSubscribed.equals("NA")) {
						String patchCobSubscriptionBodyParam = insightsCommons.constructSubscriptionPatchRequest(InsightsConstants.COB_SUBSCRIPTION_CONTEXT,insightName,entityParameterName,"","","",editSubscription,isCobrandSubscribed,"",entityIdsMap);
						httpParams=insightsHelper.getHttpParams(InsightsConstants.COB_SUB_REQUEST, null, null, jwtCobAuthToken,null,null,null,null);
						httpParams.setBodyParams(patchCobSubscriptionBodyParam);
						Response patchCobSubscriptionResponse = null;		
						httpParams.setHttpMethod("PATCH");
						patchCobSubscriptionResponse = insightsUtilsV2.patchCustomerSubscriptions(httpParams, null);							
						if(patchCobSubscriptionResponse.getStatusCode()!=HttpStatus.NO_CONTENT_204){							
							logger.info(InsightsConstants.ERROR_MESSAGE_801);	
							notificationTestStatus=false;
							failureReason.setFailureReason(InsightsConstants.ERROR_MESSAGE_801);
							expectedResultValues.setFailureReason(failureReason);							
					}						 	
				 }
					isCobrandSubscribed=isCobrandSubscribed.equals("NA")?"TRUE TRUE TRUE TRUE TRUE":isCobrandSubscribed;
				}catch(Exception e) {
					e.printStackTrace();
				}
			}
		}
	
		if (Boolean.parseBoolean(editSubscription)) {
			if (!isUserSubscribed.isEmpty()) {    																
				String patchUserSubscriptionBodyParam = insightsCommons.constructSubscriptionPatchRequest(InsightsConstants.USER_SUBSCRIPTION_CONTEXT,insightName,entityParameterName,"","","",editSubscription,isUserSubscribed,patchEntityIds,entityIdsMap);
				httpParams=insightsHelper.getHttpParams(InsightsConstants.USER_SUB_REQUEST, null, null, jwtUserAuthToken,null,null,null,null);
				httpParams.setBodyParams(patchUserSubscriptionBodyParam);	
				Response patchUserSubscriptionResponse = null;
				httpParams.setHttpMethod("PATCH");
				patchUserSubscriptionResponse = insightsUtilsV2.patchUserSubscriptions(httpParams, null);						
				if (patchUserSubscriptionResponse.getStatusCode() != HttpStatus.NO_CONTENT_204) {
						logger.info(InsightsConstants.ERROR_MESSAGE_802);	
						failureReason.setFailureReason(InsightsConstants.ERROR_MESSAGE_802);
						expectedResultValues.setFailureReason(failureReason);
						notificationTestStatus=false;
					}
			}
		}
		HashMap<String,Boolean> entitySubscriptionMap=getEntityIdsSubscriptionMap(commonEntity,patchEntityIds,expectedInsightsForPatchedIds);
		long providerId = 16441;
		providerAccountId = providerId;
		Response response = providerAccountUtils.addProviderAccountStrict(providerId, "fieldarray",
		credentials.split(",")[0], credentials.split(",")[1], sessionObj);
		providerAccountId = response.jsonPath().getLong(JSONPaths.PROVIDER_ACC_ID);
		entityIdsMap = insightsGenerics.getItemAccounts(sessionObj,"BANK");
		entityIdsMap.put("InvalidId", "999999");
		entityIdsMap.put("NA", "SKIP");
		commonEntity.setEntityIdsMap(entityIdsMap);		
		itemAccountsToTxnsMap = insightsGenerics.getTransactionsMapWithItemAccounts(sessionObj);
		commonEntity.setItemAccountsToTxnsMap(itemAccountsToTxnsMap);					
		Thread.sleep(10000);
		if(!noInsightGenerated){
			commonEntity.setExpectedResultValues(expectedResultValues);
			insightsDBUtils.setTriggerCheckFlag(triggerCheckEnabled);
			try {			
				//invoker Call
				insightsHelper.getHttpParams(InsightsConstants.INVOKER_REQUEST, loginUser, insightName, jwtUserAuthToken,envUser,null,null,entityIdsMap);
				Thread.sleep(20000);			
			}catch(Exception e) {
				e.printStackTrace();
			}		
			httpParams=insightsHelper.getHttpParams(InsightsConstants.GET_USER_SUBSCRIPTION_REQUEST, null, insightName, jwtUserAuthToken,null,null,null,null);
			httpParams.setHttpMethod("GET");
			Response getUserSubscriptionResponse = insightsUtilsV2.getUserSubscriptions(httpParams, null);
			expectedResultValues = insightsGenerics.getExpectedAttributesToBeValidatedV2(testCaseId, insightName, triggerType, "OTHER", entityParameterName, "", Integer.parseInt(noumberOfInsights), numberOfKeys, 
					getCobrandSubscriptionMap(entityParameterName,isCobrandSubscribed),patchEntityIds.isEmpty()?false:true, Integer.parseInt(expectedNumberOfPatchedInsights), failureReason, keysArray, entityIdsMap, validateKeys, getUserSubscriptionResponse,jwtCobAuthToken);
			commonEntity.setExpectedResultValues(expectedResultValues);
			int patchedIdsInsightsGeneratedCount = 0;		
			ArrayList<com.yodlee.insights.yodleeApi.utils.Entity> entities = null;
			String entityName=null,patchedEntityId=null,thresholdValue=null;	
			//insightsDBUtils.setTriggerCheckFlag(triggerCheckEnabled);
			if(expectedResultValues.isUserPatched() && expectedResultValues.getCobrandSubscribed()) {	
			    entities = expectedResultValues.getSubscriptions().get(InsightsConstants.USER).getEntities();
				for(int i=0;i<entities.size() && entities.get(i).getId()!=null;i++) {					
					entityName = entities.get(i).getEntityType();
					patchedEntityId = entities.get(i).getId()==null?"0000":entities.get(i).getId();
					int expNoOfInsights =  entities.get(i).isSubscribed() && entitySubscriptionMap.get(patchedEntityId)?1:0;
					patchedIdsInsightsGeneratedCount = (entities.get(i).isSubscribed() && entitySubscriptionMap.get(patchedEntityId))?patchedIdsInsightsGeneratedCount++:patchedIdsInsightsGeneratedCount;
					thresholdValue =  entities.get(i).getThresholds().get(0).getValue();
					httpParams=insightsHelper.getHttpParams(InsightsConstants.USER_NOTIFICATION_REQUEST, null, insightName, jwtUserAuthToken,null,"entityId",patchedEntityId,null);
					httpParams.setHttpMethod("GET");
					userInsightsResponse = insightsUtilsV2.getInsightsFeeds(httpParams, null);
					commonEntity = validateInsights.verifyGeneratedInsightsForPatchedEntitesV2(userInsightsResponse,expectedResultValues,commonEntity,expNoOfInsights,thresholdValue,accountEntitySchema,"");
					if(entities.get(i).isSubscribed() && entitySubscriptionMap.get(patchedEntityId)) {
						JsonObject notificationResponseObject = (JsonObject) jsonParser.parse(userInsightsResponse.asString());
						JsonArray notificationArray = notificationResponseObject.getAsJsonArray("feed");
						for(int index=0;index<expNoOfInsights;index++) {
							 insightsDBUtils.deleteDocument(((JsonObject)notificationArray.get(index)).get("id").getAsString());	
						}					   					
					}
					if(!commonEntity.getExpectedResultValues().getTestNotificationStatus())
					    break;
				}			
		}	

			if(commonEntity.getExpectedResultValues().getTestNotificationStatus()) {
				httpParams=insightsHelper.getHttpParams(InsightsConstants.USER_NOTIFICATION_REQUEST, null, insightName, jwtUserAuthToken,null,"","",null);
				httpParams.setHttpMethod("GET");
				userInsightsResponse = insightsUtilsV2.getInsightsFeeds(httpParams, null);
				commonEntity = validateInsights.verifyGeneratedInsightsV2(userInsightsResponse,expectedResultValues,commonEntity,Boolean.parseBoolean(isUserSubscribed),accountEntitySchema,"");		
	         }
			insightsDBUtils.deleteGeneratedInsights(Long.parseLong(userId.toString()),envCobrand);
			testInfo.setValidations(validations.toString());
		} 
		if(noInsightGenerated) {
			testInfo.setTestStatus("PASSED");
			notificationsTestSummary.put(testCaseId+"_"+insightName+"_"+testCaseDescription , "PASSED");
			testsInfoList.add(testInfo);
		}
		else if(commonEntity.getExpectedResultValues().getTestNotificationStatus() || noInsightGenerated) {
			testInfo.setTestStatus("PASSED");
			notificationsTestSummary.put(testCaseId+"_"+insightName+"_"+testCaseDescription , "PASSED");
			testsInfoList.add(testInfo);
		}
		else {
			testInfo.setTestStatus("FAILED");
			testInfo.setReasonForFailure(failureReason.getFailureReason());
			notificationsTestSummary.put(testCaseId+"_"+insightName+"_"+testCaseDescription , "FAILED");
			notificationsTestSummary.put(testCaseId+"_"+insightName+"_"+"Failed Reason ### ", commonEntity.getExpectedResultValues().getFailureReason().getFailureReason());
			testsInfoList.add(testInfo);
			Assert.fail(commonEntity.getExpectedResultValues().getFailureReason().getFailureReason());
		}
		
	}	
	
	//Write to mongoDB and display test execution summary on dashboard 
	@AfterMethod(lastTimeOnly = true)
	public void prepareAutomationReport() throws Exception {
		TestInfo testInfo = new TestInfo();
		testInfo = insightsHelper.printSummaryOfTests(notificationsTestSummary,"GET  AggregateInvestmentAccount Insight",testInfo);		
		automationTestResults.setExecutionDate(new Date().toString());
		automationTestResults.setTestInformation(testsInfoList);
		automationTestResults.setUserName(loginUser);
		automationTestResults.setTestsPassPercentage(automationTestResults.getTestsPassPercentage(testInfo));
		insightsDBUtils.addTestResults(automationTestResults);
		System.out.println("Insight Test Execution is completed");
	}
	
	public HashMap<String,Boolean> getEntityIdsSubscriptionMap(CommonEntity commonEntity,String patchEntityIds,String expectedInsightsForPatchedIds) {
		HashMap<String,Boolean> entityIdsSubscriptionMap = new HashMap<String,Boolean>();		
		String[] subscriptions = expectedInsightsForPatchedIds.split("\\s+");
		int i=0;
		for(String entityId:patchEntityIds.split("\\s+")) {
			if(!patchEntityIds.isEmpty() && subscriptions.length>0 && !commonEntity.getEntityIdsMap().get(entityId).isEmpty()) {
				entityIdsSubscriptionMap.put(commonEntity.getEntityIdsMap().get(entityId),Boolean.parseBoolean(subscriptions[i++]));
			}
		}		
		return entityIdsSubscriptionMap;
	}
	
	
	public HashMap<String,Boolean> getCobrandSubscriptionMap(String entityParameters,String cobrandSubscriptions) {
		HashMap<String,Boolean> cobrandSubscriptionMap = new HashMap<String,Boolean>();		
		String[] entityParams = entityParameters.split("\\s+");
		String[] cobSubscriptions = cobrandSubscriptions.split("\\s+");
		int i=0;
		for(String supportingEntity:entityParams) {
			cobrandSubscriptionMap.put(supportingEntity, Boolean.parseBoolean(cobSubscriptions[i++]));
		}		
		//NA Case
		if(cobrandSubscriptionMap.size()==1 && cobSubscriptions.length==2) {
			if(cobrandSubscriptionMap.containsKey("view"))
				cobrandSubscriptionMap.put("account", true);
			if(cobrandSubscriptionMap.containsKey("account"))
				cobrandSubscriptionMap.put("view", true);
		}
		return cobrandSubscriptionMap;
	}
	
	
	
	@AfterClass(alwaysRun = true)
	public void unRegisteredUser() {
		userUtils.unRegisterUser(sessionObj);		
	}

}
