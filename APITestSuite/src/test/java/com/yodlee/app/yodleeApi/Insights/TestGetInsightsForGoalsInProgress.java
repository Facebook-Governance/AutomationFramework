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
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import org.databene.benerator.anno.Source;
import org.eclipse.jetty.http.HttpStatus;
import org.jose4j.lang.JoseException;
import org.slf4j.Logger;
import com.google.gson.JsonParser;
import org.slf4j.LoggerFactory;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
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

import com.yodlee.insights.views.pojo.CommonEntity;
import com.yodlee.insights.yodleeApi.utils.*;

import io.restassured.response.Response;
import junit.framework.Assert;

public class TestGetInsightsForGoalsInProgress extends Base {
	
	protected Logger logger = LoggerFactory.getLogger(TestGetInsightsForGoalsInProgress.class);
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
	SFGUtils sfgUtils = new SFGUtils();		
	JsonParser jsonParser = new JsonParser();
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
	HashMap<String,String> goalsList = null;
	Number userId = null;
	List<TestInfo> testsInfoList;
	Response userInsightsResponse = null;
	String insightExpectedKeys = null;
	InsightsDBUtils insightsDBUtils = new InsightsDBUtils();
	AutomationTestResults automationTestResults = new AutomationTestResults();
	BoardUtils boardUtils = new BoardUtils();	
	String jwtCobAuthToken = null;			
	InsightsProperties prop = new InsightsProperties();	
	String envCobrand=null;
	String envUser=null;
	CommonEntity commonEntity = new CommonEntity();
	InsightsCommons insightsCommons = new InsightsCommons();
	NotifiedInsightsDataValidation validateInsights = null;
	ArrayList<String> goalList = new ArrayList<String>();
	
	
	@BeforeClass(alwaysRun = true)
	public void setUp() throws Exception {
		System.out.println("initiated execution");
		insightsGenerics = new InsightsGenerics();
		envCobrand=prop.readPropertiesFile().getProperty("envCobrand");
		envUser=prop.readPropertiesFile().getProperty("envUser");		
		User userInfo = User.builder().build();
	    userInfo.setUsername("GoalProgress" + System.currentTimeMillis());
	    //userInfo.setUsername("goaluser329");
		loginUser = userInfo.getUsername();
		System.out.println("User ----> "+userInfo.getUsername());
		userInfo.setPassword("TEST@123");
		sessionObj = EnvSession.builder().cobSession(configurationObj.getCobrandSessionObj().getCobSession())
				.path(configurationObj.getCobrandSessionObj().getPath()).build();
		userHelper.getUserSession(userInfo, sessionObj);	
		//userHelper.getUserSession(userInfo.getUsername(),userInfo.getPassword(),sessionObj)	;	
		commonEntity.setEnvSession(sessionObj);
		VisibleAllOver.setInstance(commonEntity);
		long providerId = 16441;
		providerAccountId = providerId;
		Response response = providerAccountUtils.addProviderAccountStrict(providerId, "fieldarray",
		"goalofftrack.site16441.1", "site16441.1", sessionObj);
		providerAccountId = response.jsonPath().getLong(JSONPaths.PROVIDER_ACC_ID);
		System.out.println("providerAccountId1::::===" + providerAccountId);
		jwtCobAuthToken = jwtHelper.getJWTToken("msampath@yodlee.com",envCobrand);	
		jwtCobAuthToken = "Bearer ".concat(jwtCobAuthToken);
		jwtUserAuthToken = jwtHelper.getJWTToken(userInfo.getUsername(),envUser);	
		jwtUserAuthToken = "Bearer ".concat(jwtUserAuthToken);
		userId = insightsGenerics.getMemId(sessionObj);
		System.out.println("jwtCobAuthToken = " +jwtCobAuthToken);
		System.out.println("JWTauthToken =  " +jwtUserAuthToken);
		entityIdsMap = insightsGenerics.getItemAccounts(sessionObj,"BANK");		
		entityIdsMap.put("InvalidId", "999999");
		entityIdsMap.put("NA", "SKIP");
		commonEntity.setEntityIdsMap(entityIdsMap);
		insightsDBUtils.deleteGeneratedInsights(Long.parseLong(userId.toString()),envCobrand);
		Thread.sleep(40000);
	}
	
	@BeforeMethod(firstTimeOnly=true)
	public void initializations() throws Exception {	
		failureReason = new  FailureReason();		
		testsInfoList = new ArrayList<TestInfo>();	
		automationTestResults.setInsights("GoalInProgress");
		automationTestResults.setBuildNo("223");
	  
	}
	@AfterMethod(alwaysRun=true)
	public void clearGoals() throws Exception {	
		goalList.clear();	  
	}

	@Test(enabled = true, dataProvider = "feeder")
	@Source("\\TestData\\CSVFiles\\Insights\\GoalInProgress\\GetGoalInProgressInsight.csv")
	public void testGetNotificationsForGoalInProgress(String testCaseId,String testCaseDescription,String insightName,String supportedContainers,String triggerType,String goalName,
			String destItemAccounts,String goalStatus,String actualSavings,String accountStatus,String 	entityParameterCount,String	entityParameterName,String noumberOfInsights,
			String editSubscription,String	isCobrandSubscribed,String isUserSubscribed,String patchEntityIds,String validateKeys,String expectedStatus,String tcEnabled) throws Exception {
		commontUtils.isTCEnabled(tcEnabled, testCaseId);
		if(testCaseId.equals("AT-147349"))
			System.out.println("Check..........");
		 insightsDBUtils.deleteGeneratedInsights(Long.parseLong(userId.toString()),envCobrand);
		HashMap<String,String> otherEntityIdsMap = new HashMap<String,String>();
		goalList.addAll(Arrays.asList(goalName.split(",")));
		otherEntityIdsMap = insightsGenerics.prepareDataForOtherThanAccountEntityParamas(sessionObj,entityIdsMap,entityParameterName,destItemAccounts,envCobrand,goalName,goalStatus,actualSavings,accountStatus);
		entityIdsMap.putAll(otherEntityIdsMap);
		commonEntity.setEntityIdsMap(entityIdsMap);
		validateInsights = new NotifiedInsightsDataValidation();
		List<Boolean> isUserPatchedAndExpectedInsight=new ArrayList<Boolean>();
		for(String str:isUserSubscribed.split("\\s+")) 
			isUserPatchedAndExpectedInsight.add(Boolean.parseBoolean(str));
		boolean isCobSubscribed = !isCobrandSubscribed.isEmpty()?Boolean.parseBoolean(isCobrandSubscribed):false;
		HttpMethodParameters httpParams = HttpMethodParameters.builder().build();
		ExpectedResultValues expectedResultValues = new ExpectedResultValues();
		expectedResultValues.setTestNotificationStatus(true);
		logger.info("Testcase executed ---------------------------------------------->"+testCaseId);
		JsonArray keysArray = insightsGenerics.getInsightKeys(insightName);
		int numberOfKeys = insightsGenerics.getTotalNumberOfKeys(keysArray,validateKeys);
		HashMap<String,Boolean> entitySubscriptionMap=getEntityIdsSubscriptionMap(commonEntity,patchEntityIds,isUserSubscribed);
		TestInfo testInfo = new TestInfo();
		StringBuilder validations = new StringBuilder();		
		testInfo.setTestCaseId(testCaseId);
		testInfo.setTestDescription(testCaseDescription);			
		if (Boolean.parseBoolean(editSubscription)) {
			if (!isCobrandSubscribed.isEmpty()) {		
				try {					
					if(!isCobrandSubscribed.equals("NA")) {
						String patchCobSubscriptionBodyParam = insightsCommons.constructCobrandandUserPatchRequest(InsightsConstants.COB_SUBSCRIPTION_CONTEXT,insightName,entityParameterName,"","","",editSubscription,isCobrandSubscribed,"",entityIdsMap);
						httpParams=insightsHelper.getHttpParams(InsightsConstants.COB_SUB_REQUEST, null, null, jwtCobAuthToken,null,null,null,null);
						httpParams.setBodyParams(patchCobSubscriptionBodyParam);
						Response patchCobSubscriptionResponse = null;					
						patchCobSubscriptionResponse = insightUtils.patchCobrandSubscriptions(httpParams,null);										
						if(patchCobSubscriptionResponse.getStatusCode()!=HttpStatus.NO_CONTENT_204){
							logger.info(InsightsConstants.ERROR_MESSAGE_801);	
							expectedResultValues.setTestNotificationStatus(false);
							failureReason.setFailureReason(InsightsConstants.ERROR_MESSAGE_801);
							expectedResultValues.setFailureReason(failureReason);
					}						 	
				 }
					isCobrandSubscribed=isCobrandSubscribed.equals("NA")?"TRUE TRUE":isCobrandSubscribed;
				}catch(Exception e) {
					e.printStackTrace();
				}
			}
			if (!isUserSubscribed.isEmpty()) {    
				String patchUserSubscriptionBodyParam = insightsCommons.constructCobrandandUserPatchRequest(InsightsConstants.USER_SUBSCRIPTION_CONTEXT,insightName,entityParameterName,"","","",editSubscription,isUserSubscribed,patchEntityIds,entityIdsMap);
				httpParams=insightsHelper.getHttpParams(InsightsConstants.USER_SUB_REQUEST, null, null, jwtUserAuthToken,null,null,null,null);
				httpParams.setBodyParams(patchUserSubscriptionBodyParam);	
				Response patchUserSubscriptionResponse = insightUtils.patchUserSubscriptions(httpParams,null);						
				if (patchUserSubscriptionResponse.getStatusCode() != HttpStatus.NO_CONTENT_204) {
						logger.info(InsightsConstants.ERROR_MESSAGE_802);	
						failureReason.setFailureReason(InsightsConstants.ERROR_MESSAGE_802);
						expectedResultValues.setFailureReason(failureReason);
						expectedResultValues.setTestNotificationStatus(false);
					}
			}
		}	
		commonEntity.setExpectedResultValues(expectedResultValues);
		try {
			//invoker Call
			insightsHelper.getHttpParams(InsightsConstants.INVOKER_REQUEST, loginUser, insightName, jwtUserAuthToken,envUser,null,null,entityIdsMap);
			Thread.sleep(75000);
		}catch(Exception e) {
			e.printStackTrace();
		}		
		httpParams=insightsHelper.getHttpParams(InsightsConstants.GET_USER_SUBSCRIPTION_REQUEST, null, insightName, jwtUserAuthToken,null,null,null,null);
		Response getUserSubscriptionResponse = insightUtils.getUserSubscription(httpParams,null);
		expectedResultValues = insightsGenerics.getExpectedAttributesToBeValidated(testCaseId, insightName, triggerType, "OTHER", entityParameterName, "", Integer.parseInt(noumberOfInsights), numberOfKeys, 
				getCobrandSubscriptionMap(entityParameterName,isCobrandSubscribed),patchEntityIds.isEmpty()?false:true, Integer.parseInt("1"), failureReason, keysArray, entityIdsMap, validateKeys, getUserSubscriptionResponse,jwtCobAuthToken);
		int patchedIdsInsightsGeneratedCount = 0;
		ArrayList<com.yodlee.insights.yodleeApi.utils.Entity> entities = null;
		String entityName=null,patchedEntityId=null,thresholdValue=null;				
		if(expectedResultValues.isUserPatched() && expectedResultValues.getCobrandSubscribed()) {	
			    entities = expectedResultValues.getSubscriptions().get(InsightsConstants.USER).getEntities();
				for(int i=0;i<entities.size();i++) {					
					entityName = entities.get(i).getEntityType();
					patchedEntityId = entities.get(i).getId()==null?"0000":entities.get(i).getId();
					int expNoOfInsights =  entities.get(i).isSubscribed() && goalStatus.split(",")[i].equals("IN_PROGRESS") && entitySubscriptionMap.get(patchedEntityId)?1:0;
					patchedIdsInsightsGeneratedCount = (entities.get(i).isSubscribed() && entitySubscriptionMap.get(patchedEntityId))?patchedIdsInsightsGeneratedCount++:patchedIdsInsightsGeneratedCount;
					//thresholdValue =  entities.get(i).getThresholds().get(0).getValue();
					httpParams=insightsHelper.getHttpParams(InsightsConstants.USER_NOTIFICATION_REQUEST, null, insightName, jwtUserAuthToken,null,entityName+"Id",patchedEntityId,null);
					userInsightsResponse = insightUtils.getNotificationsV0(httpParams,null);
					commonEntity = validateInsights.verifyGeneratedInsightsForPatchedEntites(userInsightsResponse,expectedResultValues,commonEntity,expNoOfInsights,"");
					deleteCreatedGoalsForUser(goalList,entityIdsMap);
					if(entities.get(i).isSubscribed() && entitySubscriptionMap.get(patchedEntityId)) {
						JsonObject notificationResponseObject = (JsonObject) jsonParser.parse(userInsightsResponse.asString());
						JsonArray notificationArray = notificationResponseObject.getAsJsonArray("notification");
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
			userInsightsResponse = insightUtils.getNotificationsV0(httpParams,null);
			commonEntity = validateInsights.verifyGeneratedInsights(userInsightsResponse,expectedResultValues,commonEntity,Boolean.parseBoolean(isUserSubscribed));
			deleteCreatedGoalsForUser(goalList,entityIdsMap);
			 
         }
		insightsDBUtils.deleteSpecificUserScheduleNotifocations(Long.parseLong(userId.toString()),envCobrand);			
		testInfo.setValidations(validations.toString());
		if(commonEntity.getExpectedResultValues().getTestNotificationStatus()) {
			testInfo.setTestStatus("PASSED");
			notificationsTestSummary.put(testCaseId+"_"+insightName+"_"+testCaseDescription , "PASSED");
			testsInfoList.add(testInfo);
		}else {
			testInfo.setTestStatus("FAILED");
			testInfo.setReasonForFailure(failureReason.getFailureReason());
			notificationsTestSummary.put(testCaseId+"_"+insightName+"_"+testCaseDescription , "FAILED");
			notificationsTestSummary.put(testCaseId+"_"+insightName+"_"+"Failed Reason ### ", commonEntity.getExpectedResultValues().getFailureReason().getFailureReason());
			testsInfoList.add(testInfo);
			Assert.fail(commonEntity.getExpectedResultValues().getFailureReason().getFailureReason());
		}
	}	
	
	@AfterMethod(lastTimeOnly = true)
	public void prepareAutomationReport() throws Exception {
		TestInfo testInfo = new TestInfo();
		testInfo = insightsHelper.printSummaryOfTests(notificationsTestSummary,"GET  LowBalanceWarning Insight",testInfo);		
		automationTestResults.setExecutionDate(new Date().toString());
		automationTestResults.setTestInformation(testsInfoList);
		automationTestResults.setUserName(loginUser);
		automationTestResults.setTestsPassPercentage(automationTestResults.getTestsPassPercentage(testInfo));
		insightsDBUtils.addTestResults(automationTestResults);
		System.out.println("Insight Test Execution is completed");
	}
	
	//deleteCreatedGoalsForUser(goalName,entityIdsMap);
	public void deleteCreatedGoalsForUser(ArrayList<String> deleteGoalsList,HashMap<String,String> goalsMap) {
		HttpMethodParameters httpParams = HttpMethodParameters.builder().build();	
		int goalSize=deleteGoalsList.size();
		for(int i =0 ;i<goalSize;i++) {
			System.out.println("Deleting Goals......."+deleteGoalsList.get(0));
			LinkedHashMap<String, Object> pathParam =new LinkedHashMap<>(); 
			pathParam.put("goalIdDelete", Long.parseLong(goalsMap.get(deleteGoalsList.get(0))));			
			httpParams.setPathParams(pathParam);
			Response deleteResponse=sfgUtils.deleteGoal(httpParams, sessionObj);
			entityIdsMap.remove(goalList.get(0));
			deleteGoalsList.remove(0);
		}
		goalList=deleteGoalsList;
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
