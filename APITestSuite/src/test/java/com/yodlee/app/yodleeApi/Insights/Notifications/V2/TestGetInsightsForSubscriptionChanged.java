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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.databene.benerator.anno.Source;
import org.eclipse.jetty.http.HttpStatus;
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
import com.yodlee.insights.yodleeApi.utils.BoardHelper;
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
import com.yodlee.yodleeApi.utils.apiUtils.InsightUtils;
import com.yodlee.yodleeApi.utils.apiUtils.InsightsUtilV1;
import com.yodlee.yodleeApi.utils.apiUtils.InvokerUtils;
import com.yodlee.yodleeApi.utils.apiUtils.ProviderAccountUtils;
import com.yodlee.yodleeApi.utils.apiUtils.TaskUtils;
import com.yodlee.yodleeApi.utils.apiUtils.UserUtils;
import com.yodlee.yodleeApi.utils.commonUtils.CommonUtils;

import io.restassured.response.Response;

public class TestGetInsightsForSubscriptionChanged extends Base {
	public static Long providerAccountId = null;
	protected Logger logger = LoggerFactory.getLogger(TestGetInsightsForSubscriptionChanged.class);
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
	HashMap<String,ArrayList> itemAccountsToTxnsMap = new HashMap<String,ArrayList>();	
	String buildNo="421",codeCoverage="80";
	CommonEntity commonEntity = new CommonEntity();
	BoardHelper boardHelper = new BoardHelper();
	InsightsCommons insightsCommons = new InsightsCommons();
	Response userInsightsResponse = null;
	User userInfo = User.builder().build();
	NotifiedInsightsDataValidation validateInsights = null;
	String viewEntitySchema = "SubscriptionChangedViewEntitySchema.json";
	String accountEntitySchema = "SubscriptionChangedAccountEntitySchema.json";

	@BeforeClass(alwaysRun = true)
	public void setUp() throws Exception {
		System.out.println("initiated execution");	
		insightsGenerics = new InsightsGenerics();
		insightsDBUtils = new InsightsDBUtils();
		String cobrand = prop.readPropertiesFile().getProperty("envCobrand");
		cobrandUser =  prop.readPropertiesFile().getProperty("cobrandUser");
		envCobrand = (configurationObj.getApiVersion().equals("1.1")?cobrand:(configurationObj.getApiVersion().equals("2")?cobrand.substring(0, cobrand.length()-2):null));		
		String user=prop.readPropertiesFile().getProperty("envUser");	
		envUser = (configurationObj.getApiVersion().equals("1.1")?user:(configurationObj.getApiVersion().equals("2")?user.substring(0, user.length()-2):null));
		User userInfo = User.builder().build();
	    userInfo.setUsername("SubscriptionAmtChangedV2" + System.currentTimeMillis());
		loginUser = userInfo.getUsername();
		automationTestResults.setUserName(loginUser);
		System.out.println("User ----> "+userInfo.getUsername());
		userInfo.setPassword("TEST@123");
		sessionObj = EnvSession.builder().cobSession(configurationObj.getCobrandSessionObj().getCobSession())
				.path(configurationObj.getCobrandSessionObj().getPath()).build();
		userHelper.getUserSession(userInfo, sessionObj);					
		userId = insightsGenerics.getMemId(sessionObj);	
	}

	public void addAccounts(String dagUserName,String dagPassWord) throws Exception {
		System.out.println("initiated execution");	
		insightsDBUtils.deleteGeneratedInsights(Long.parseLong(userId.toString()),envCobrand);
		userUtils.unRegisterUser(sessionObj);
		cobrandUser=prop.readPropertiesFile().getProperty("cobrandUser");
		insightsGenerics = new InsightsGenerics();
		insightsDBUtils = new InsightsDBUtils();
		User userInfo = User.builder().build();
	    userInfo.setUsername("SalChangedV2" + System.currentTimeMillis());
		loginUser = userInfo.getUsername();
		automationTestResults.setUserName(loginUser);
		System.out.println("User ----> "+userInfo.getUsername());
		userInfo.setPassword("TEST@123");
		sessionObj = EnvSession.builder().cobSession(configurationObj.getCobrandSessionObj().getCobSession())
				.path(configurationObj.getCobrandSessionObj().getPath()).build();
		userHelper.getUserSession(userInfo, sessionObj);					
		long providerId = 16441;
		providerAccountId = providerId;
		Response response = providerAccountUtils.addProviderAccountStrict(providerId, "fieldarray",	dagUserName, dagPassWord, sessionObj);	
		providerAccountId = response.jsonPath().getLong(JSONPaths.PROVIDER_ACC_ID);
		System.out.println("providerAccountId1::::===" + providerAccountId);		 
        userId = insightsGenerics.getMemId(sessionObj);		
		entityIdsMap = insightsGenerics.getItemAccounts(sessionObj,"BANK");		
		entityIdsMap.put("InvalidId", "999999");
		entityIdsMap.put("NA", "SKIP");
		commonEntity.setEntityIdsMap(entityIdsMap);
	}
	
	public void updateCredentials(String dagUserName,String dagPassWord) throws Exception {
		System.out.println("Updating Credentials - For Reconciliation");
		HttpMethodParameters httpParams = HttpMethodParameters.builder().build();		
		LinkedHashMap<String, Object> queryParam = new LinkedHashMap<>();
		queryParam.put("providerAccountIds", providerAccountId);
        httpParams.setQueryParams(queryParam);      
    	//update credentials
        String updateCredentialsPayload = insightsHelper.constructUpdateCredentialsPayload(dagUserName, dagPassWord);
        httpParams.setBodyParams(updateCredentialsPayload);
        Response response = providerAccountUtils.updateProviderAccount(httpParams, sessionObj);		
        providerAccountUtils.checkRefreshStatusUntilActAddition(sessionObj, String.valueOf(providerAccountId)); 
	}

	@BeforeMethod(firstTimeOnly = true)
	public void initializations() throws Exception {
		failureReason = new FailureReason();
		testsInfoList = new ArrayList<TestInfo>();
		automationTestResults.setInsights("SubChangedV2");
		automationTestResults.setBuildNo("351");
		automationTestResults.setCodeCoveragePercent("00");
		automationTestResults.setStoryId("B-XXXXX");
		}
		
	@Test(enabled = true, dataProvider = "feeder")
	@Source("\\TestData\\CSVFiles\\Insights\\V2Feeds\\GetSubscriptionChanged.csv")
	public void testGetNotificationsForSubscriptionAmountChanged(String testCaseId,String testCaseDescription,String insightName,String triggerCheckEnabled,
			String triggerType,String entityParamsCount,String entityParameterName,String thresholdNameValueType,String noumberOfInsights,String editSubscription
			,String isCobrandSubscribed,String isUserSubscribed,String dagCredentials,String updateCredentials,String patchEntityIds,String expectedInsightsForPatchedIds,String expectedNumberOfPatchedInsights,String validateKeys,
			String viewsRulesScenario,String viewRuleAmountRangeDetails,String viewRuleCategoryIdDetails,String viewRuleMerchantTypeDetails, String expectedStatus,String tcEnabled) throws Exception {
		commontUtils.isTCEnabled(tcEnabled, testCaseId);
		if(!dagCredentials.isEmpty())
			addAccounts(dagCredentials.split(",")[0],dagCredentials.split(",")[1]);
		String cobrand = prop.readPropertiesFile().getProperty("envCobrand");
		cobrandUser =  prop.readPropertiesFile().getProperty("cobrandUser");
		envCobrand = (configurationObj.getApiVersion().equals("1.1")?cobrand:(configurationObj.getApiVersion().equals("2")?cobrand.substring(0, cobrand.length()-2):null));		
		jwtCobAuthToken = "Bearer ".concat(jwtHelper.getJWTToken(cobrandUser,envCobrand));	
		jwtUserAuthToken = "Bearer ".concat(jwtHelper.getJWTToken(loginUser,envUser));	
		commonEntity.setUserJwtToken(jwtUserAuthToken);
		Thread.sleep(40000);
		validateInsights = new NotifiedInsightsDataValidation();
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
		insightsGenerics.setViewsPropertiesDetails("", "",viewRuleAmountRangeDetails,viewRuleCategoryIdDetails,"",viewRuleMerchantTypeDetails,commonEntity);
		if(!viewsRulesScenario.isEmpty()) {
			List<String> numberOfviewsToBeCreated = Arrays.asList(viewsRulesScenario.split(","));
			for(Object view : numberOfviewsToBeCreated) {
				boardHelper.createViewRules((String)view,commonEntity);
			}				
		}	
		if (Boolean.parseBoolean(editSubscription)) {
			if (!isCobrandSubscribed.isEmpty()) {		
				try {					
					if(!isCobrandSubscribed.equals("NA")) {
						String patchCobSubscriptionBodyParam = insightsCommons.constructSubscriptionPatchRequestV2(InsightsConstants.COB_SUBSCRIPTION_CONTEXT,insightName,entityParameterName,thresholdNameValueType,"","",editSubscription,isCobrandSubscribed,"",entityIdsMap);
						httpParams=insightsHelper.getHttpParams(InsightsConstants.COB_SUB_REQUEST, null, null, jwtCobAuthToken,null,null,null,null);
						httpParams.setBodyParams(patchCobSubscriptionBodyParam);
						Response patchCobSubscriptionResponse = null;					
						httpParams.setHttpMethod("PATCH");
						patchCobSubscriptionResponse = insightsUtilsV2.patchCustomerSubscriptions(httpParams,null);										
						if(patchCobSubscriptionResponse.getStatusCode()!=HttpStatus.NO_CONTENT_204){
							logger.info(InsightsConstants.ERROR_MESSAGE_801);	
							expectedResultValues.setTestNotificationStatus(false);
							failureReason.setFailureReason(InsightsConstants.ERROR_MESSAGE_801);
							expectedResultValues.setFailureReason(failureReason);
					}						 	
				 }
					isCobrandSubscribed=isCobrandSubscribed.equals("NA")?"TRUE TRUE TRUE TRUE":isCobrandSubscribed;
				}catch(Exception e) {
					e.printStackTrace();
				}
			}
			if (!isUserSubscribed.isEmpty()) {    
				String patchUserSubscriptionBodyParam = insightsCommons.constructSubscriptionPatchRequestV2(InsightsConstants.USER_SUBSCRIPTION_CONTEXT,insightName,entityParameterName,thresholdNameValueType,"","",editSubscription,isUserSubscribed,patchEntityIds,entityIdsMap);
				httpParams=insightsHelper.getHttpParams(InsightsConstants.USER_SUB_REQUEST, null, null, jwtUserAuthToken,null,null,null,null);
				httpParams.setBodyParams(patchUserSubscriptionBodyParam);	
				Response patchUserSubscriptionResponse = null;
				httpParams.setHttpMethod("PATCH");
				patchUserSubscriptionResponse = insightsUtilsV2.patchUserSubscriptions(httpParams,null);						
				if (patchUserSubscriptionResponse.getStatusCode() != HttpStatus.NO_CONTENT_204) {
						logger.info(InsightsConstants.ERROR_MESSAGE_802);	
						failureReason.setFailureReason(InsightsConstants.ERROR_MESSAGE_802);
						expectedResultValues.setFailureReason(failureReason);
						expectedResultValues.setTestNotificationStatus(false);
					}
			}
		}	
			
		HashMap<String,Boolean> entitySubscriptionMap=insightsCommons.getEntityIdsSubscriptionMap(commonEntity,patchEntityIds,expectedInsightsForPatchedIds);
		if(!updateCredentials.isEmpty())
			updateCredentials(updateCredentials.split(",")[0],updateCredentials.split(",")[1]);
		commonEntity.setExpectedResultValues(expectedResultValues);
		insightsDBUtils.setTriggerCheckFlag(triggerCheckEnabled);
		try {			
			//invoker Call
			//insightsHelper.getHttpParams(InsightsConstants.INVOKER_REQUEST, loginUser, insightName, jwtUserAuthToken,envUser,null,null,entityIdsMap);
			//Thread.sleep(2000);			
		}catch(Exception e) {
			e.printStackTrace();
		}		
		httpParams=insightsHelper.getHttpParams(InsightsConstants.GET_USER_SUBSCRIPTION_REQUEST, null, insightName, jwtUserAuthToken,null,null,null,null);
		httpParams.setHttpMethod("GET");
		Response getUserSubscriptionResponse = insightsUtilsV2.getUserSubscriptions(httpParams,null);
		getUserSubscriptionResponse.then().log().all();
		expectedResultValues = insightsGenerics.getExpectedAttributesToBeValidatedV2(testCaseId, insightName, triggerType, "OTHER", entityParameterName, thresholdNameValueType, Integer.parseInt(noumberOfInsights), numberOfKeys, 
				insightsCommons.getCobrandSubscriptionMap(entityParameterName,isCobrandSubscribed),patchEntityIds.isEmpty()?false:true, Integer.parseInt(expectedNumberOfPatchedInsights), failureReason, keysArray, entityIdsMap, validateKeys, getUserSubscriptionResponse,jwtCobAuthToken);
		int patchedIdsInsightsGeneratedCount = 0;		
		ArrayList<com.yodlee.insights.yodleeApi.utils.Entity> entities = null;
		String patchedEntityId=null,thresholdValue=null;	
		if(testCaseId.equals("AT-143301"))
			System.out.println("Check.............");
		
		if(expectedResultValues.isUserPatched() && expectedResultValues.getCobrandSubscribed()) {	
			    entities = expectedResultValues.getSubscriptions().get(InsightsConstants.USER).getEntities();
				for(int i=0;i<entities.size() && entities.get(i).getId()!=null && entities.get(i).isSubscribed();i++) {					
					patchedEntityId = entities.get(i).getId()==null?"0000":entities.get(i).getId();
					patchedIdsInsightsGeneratedCount = (entities.get(i).isSubscribed() && entitySubscriptionMap.get(patchedEntityId))?patchedIdsInsightsGeneratedCount++:patchedIdsInsightsGeneratedCount;
					//thresholdValue =  entities.get(i).getThresholds().get(0).getValue();
					httpParams=insightsHelper.getHttpParams(InsightsConstants.USER_NOTIFICATION_REQUEST, null, insightName, jwtUserAuthToken,null,"entityId",patchedEntityId,null);
					httpParams.setHttpMethod("GET");
					userInsightsResponse = insightsUtilsV2.getInsightsFeeds(httpParams, null);
					commonEntity = validateInsights.verifyGeneratedInsightsForPatchedEntitesV2(userInsightsResponse,expectedResultValues,commonEntity,Integer.parseInt(expectedNumberOfPatchedInsights),thresholdValue,accountEntitySchema,viewEntitySchema);
					if(entities.get(i).isSubscribed() && entitySubscriptionMap.get(patchedEntityId)) {
						JsonObject notificationResponseObject = (JsonObject) jsonParser.parse(userInsightsResponse.asString());
						JsonArray notificationArray = notificationResponseObject.getAsJsonArray("feed");
						for(int index=0;index<Integer.parseInt(expectedNumberOfPatchedInsights);index++) {
							//insightsDBUtils.setTriggerCheckFlag(triggerCheckEnabled);
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
			commonEntity = validateInsights.verifyGeneratedInsightsV2(userInsightsResponse,expectedResultValues,commonEntity,Boolean.parseBoolean(isUserSubscribed),accountEntitySchema,viewEntitySchema);		
         }
		 insightsDBUtils.deleteGeneratedInsights(Long.parseLong(userId.toString()),envCobrand);
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


	@AfterClass(alwaysRun = true)
	public void unRegisteredUser() {
		userUtils.unRegisterUser(sessionObj);
	}

}
