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
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.Date;

import com.google.gson.JsonArray;
import com.google.gson.JsonParser;
import com.yodlee.insights.views.pojo.CommonEntity;
import com.yodlee.insights.yodleeApi.utils.AutomationTestResults;
import com.yodlee.yodleeApi.helper.BoardHelper;
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
import com.yodlee.yodleeApi.utils.apiUtils.TaskUtils;
import com.yodlee.yodleeApi.utils.apiUtils.UserUtils;
import com.yodlee.yodleeApi.utils.apiUtils.InsightUtils;
import com.yodlee.yodleeApi.utils.apiUtils.InvokerUtils;
import com.yodlee.yodleeApi.utils.commonUtils.CommonUtils;

import io.restassured.response.Response;

public class TestGetInsightsForFinancialFees extends Base {

	public static Long providerAccountId = null;
	String loginName, password;
	String userSession = "";
	EnvSession sessionObj = null;
	Configuration configurationObj = Configuration.getInstance();
	AutomationTestResults automationTestResults = new AutomationTestResults();
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
	JsonParser jsonParser = new JsonParser();
	BoardUtils boardUtils = new BoardUtils();
	InsightsLevelVerifications insightsLevelVerifications = new InsightsLevelVerifications();
	HashMap<String,ArrayList<String>> negativeExpectedValuesMap;
	String loginUser = null;
	String jwtUserAuthToken = null;
	String jwtCobAuthToken = "";
	List<TestInfo> testsInfoList;
	String insightExpectedKeys = null;
	FailureReason failureReason = new  FailureReason();
	static Map<String ,String>  notificationsTestSummary = new HashMap<String,String>();
	HashMap<String,String> entitiyIdsMap = new HashMap<String,String>();
	Number userId = null;
	User userInfo = null;
	InsightsDBUtils insightsDBUtils = new InsightsDBUtils();
	HashMap<String,String> boardIdList = new HashMap<String,String>();
	BoardHelper boardHelper = new BoardHelper();
	protected Logger logger = LoggerFactory.getLogger(TestGetInsightsForBillNotPaid.class);
	String envCobrand=null;
	String envUser=null;
	InsightsProperties prop = new InsightsProperties();
	CommonEntity commonEntity = new CommonEntity();

	@BeforeClass(alwaysRun = true)
	public void setUp() throws Exception {
		System.out.println("initiated execution");
		envCobrand=prop.readPropertiesFile().getProperty("envCobrand");
		envUser=prop.readPropertiesFile().getProperty("envUser");
		insightsDBUtils.deleteAllUserDetails(envCobrand);
		insightsDBUtils.deleteAllJobDTO(envCobrand);
		insightsGenerics = new InsightsGenerics();
	}

	@BeforeMethod(firstTimeOnly=true)
	public void initializations() throws Exception {
		failureReason = new  FailureReason();		
		testsInfoList = new ArrayList<TestInfo>();
		automationTestResults.setInsights("FinancialFees");
		automationTestResults.setBuildNo("297");
		automationTestResults.setCodeCoveragePercent("85");
		automationTestResults.setStoryId("B-60433");
	}

	@Test(enabled = true, dataProvider = "feeder")
	@Source("\\TestData\\CSVFiles\\Insights\\FinancialFees\\GetFinancialFeesInsight.csv")
	public void testGetNotificationsForFinancialFees(String testCaseId,String testCaseDescription,String insightName,String supportedContainers,String inputContainers,
			String triggerType,String insightType,long providerId, String dagUserName, String dagUserPassword,String entityParamsCount,String entityParameterName,String numberOfInsights,
			String editSubscription,String isCobrandSubscribed,String isUserSubscribed,String duration,String entityDetails,String accountStatus,String noOfViews,String viewName, String viewDescription,
			String viewRuleType, String budgetAmount,String currency, String budgetType,String ruleName,String fromDate,String toDate,String fromAmount,String toAmount,String merchantType,
			String merchantName,String categoryId,String numberOfTransactions,String validateKeys,String expectedStatus,String tcEnabled) throws ParseException, InterruptedException,IOException {

		HttpMethodParameters httpParams = HttpMethodParameters.builder().build();
		int expNumOfKeys =0;
		StringBuffer entityIds = new StringBuffer();
		String patchCobSubscriptionBodyParam = null;
		insightsDBUtils.deleteAllUserDetails(envCobrand);
		commontUtils.isTCEnabled(tcEnabled, testCaseId);
		logger.info("Testcase Execution Started ---------------------------------------------->"+testCaseId);
		System.out.println("Testcase Execution Started ---------------------------------------------->"+testCaseId);
		boolean notificationTestStatus = true;
		addAccount(providerId,dagUserName,dagUserPassword,inputContainers);	
		JsonArray keysArray = insightsGenerics.getInsightKeysforFinancialFees(insightName,duration);
		if(duration.equals("THIS_MONTH") || validateKeys.equalsIgnoreCase("view"))
			expNumOfKeys = insightsGenerics.getTotalNumberofFinancialFeesInsightKeys(validateKeys, insightName, duration);
		else
			expNumOfKeys = insightsGenerics.getTotalNumberofPBInsihgtKeys(validateKeys,insightName,duration);
		TestInfo testInfo = new TestInfo();
		StringBuilder validations = new StringBuilder();		
		testInfo.setTestCaseId(testCaseId);

		if (Boolean.parseBoolean(editSubscription)) {
			if (!isCobrandSubscribed.isEmpty()) {		
				try {
					patchCobSubscriptionBodyParam = insightsHelper.constructPatchCobrandSubscriptionRequest(insightName, "", "", "", "", "", "", "", "", "", entityParamsCount, entityParameterName, "1", "", "",isCobrandSubscribed, "", "", "");
					httpParams=insightsHelper.getHttpParams("cobrandSubscriptionRequest", null, null, jwtCobAuthToken,null,null,null,null);
					httpParams.setBodyParams(patchCobSubscriptionBodyParam);
					Response patchCobSubscriptionResponse = null;
					int attempts=0;
					do {
						Thread.sleep(3000);
						patchCobSubscriptionResponse = insightUtils.patchCobrandSubscriptions(httpParams,null);
					}while(patchCobSubscriptionResponse.getStatusCode()!=HttpStatus.NO_CONTENT_204 && ++attempts<=4);						
					if(patchCobSubscriptionResponse.getStatusCode()!=HttpStatus.NO_CONTENT_204 ){
						logger.info("Cobrand subscription update FAILED");	
						notificationTestStatus=false;
						failureReason.setFailureReason("Cobrand subscription update FAILED");
					}
				}catch(Exception e) {
					e.printStackTrace();
				}
			}
			if(entityParameterName.equalsIgnoreCase("view"))
			{
				httpParams=insightsHelper.getHttpParams("userSubscriptionRequest", null, null, jwtUserAuthToken,null,null,null,null);
				patchCobSubscriptionBodyParam = insightsHelper.constructPatchUserSubscriptionRequest(insightName, "", "", "", "", "", "", "", "", "", entityParamsCount,"", "account", "1", "", "", "False", "", "", "","");
				httpParams.setBodyParams(patchCobSubscriptionBodyParam);	
				Response patchUserSubscriptionResponse = insightUtils.patchUserSubscriptions(httpParams,null);
				if (patchUserSubscriptionResponse.getStatusCode() != HttpStatus.NO_CONTENT_204) {
					logger.info("Account entity is already false.");	
				}
			}
			if (!isUserSubscribed.isEmpty()) {    
				String patchUserSubscriptionBodyParam = null;
				if(!entityDetails.isEmpty()) {
					String[] entityItems = entityDetails.split("\\s+");
					for(int i=0;i<entityItems.length;i++) {
						String itemAccountId = entitiyIdsMap.get(entityItems[i]);
						if(i!=entityItems.length-1) {
							entityIds.append(itemAccountId);
							entityIds.append("   ");
						}
						else {
							entityIds.append(itemAccountId);
						}
					}
					if(entityParameterName.equalsIgnoreCase("view"))
						patchUserSubscriptionBodyParam = insightsHelper.constructPatchUserSubscriptionRequest(insightName, "", "", "", "", "", "", "", "", "", entityParamsCount,"", entityParameterName, "1", "", "", isUserSubscribed, "", "", "","");
					else
						patchUserSubscriptionBodyParam = insightsHelper.constructPatchUserSubscriptionRequest(insightName, "", "", "", "", "", "", "", "", "", entityParamsCount,"", entityParameterName, "1", entityIds.toString(), "", isUserSubscribed, "", "", "","");
				}
				else {
					patchUserSubscriptionBodyParam = insightsHelper.constructPatchUserSubscriptionRequest(insightName, "", "", "", "", "", "", "", "", "", entityParamsCount,"", entityParameterName, "1", "", "", isUserSubscribed, duration, "", "","");
				}
				httpParams=insightsHelper.getHttpParams("userSubscriptionRequest", null, null, jwtUserAuthToken,null,null,null,null);
				httpParams.setBodyParams(patchUserSubscriptionBodyParam);	
				Response patchUserSubscriptionResponse = insightUtils.patchUserSubscriptions(httpParams,null);

				if (patchUserSubscriptionResponse.getStatusCode() != HttpStatus.NO_CONTENT_204) {
					logger.info("User subscription update FAILED");	
					failureReason.setFailureReason("User subscription update FAILED");
					notificationTestStatus=false;
				}
			}
		}

		if (!accountStatus.isEmpty()) {
			Response accountStatusUpdateResponse=null;
			String itemAccountIds = entityIds.toString();
			String[] itemAccountIdArray = itemAccountIds.split("\\s+");
			String[] itemAccountid= new String[itemAccountIdArray.length];
			for(int i=0;i<itemAccountIdArray.length;i++)
				itemAccountid[i]=itemAccountIdArray[i];
			if(itemAccountid.length>1)
				accountStatusUpdateResponse = insightsGenerics.updateAccountStatus(sessionObj, accountStatus, itemAccountid[1]);
			else
				accountStatusUpdateResponse = insightsGenerics.updateAccountStatus(sessionObj, accountStatus, itemAccountid[0]);
			if (accountStatusUpdateResponse.getStatusCode() != HttpStatus.NO_CONTENT_204) {
				logger.info("User subscription update FAILED");	
				failureReason.setFailureReason("User subscription update FAILED");
			}
		}

		if(entityParameterName.contains("view")){
			String[] itemAccountIdArray = {};
			String[] transactionIdArray = {};
			if(!entityDetails.isEmpty() && !numberOfTransactions.isEmpty())
			{
				itemAccountIdArray = entityIds.toString().split("\\s+");
				System.out.println("Length:" + itemAccountIdArray.length);
				transactionIdArray = insightsGenerics.getTransactionIds(sessionObj, null,itemAccountIdArray,Integer.parseInt(numberOfTransactions));
			}
			String viewId= insightsGenerics.prapareDataForViewEntityParams(jwtUserAuthToken, viewName, viewDescription, viewRuleType, Double.parseDouble(budgetAmount), currency, budgetType, ruleName, fromDate,toDate, Double.parseDouble(fromAmount),Double.parseDouble(toAmount),merchantType,merchantName,itemAccountIdArray,categoryId,transactionIdArray);
			entitiyIdsMap.put("View", viewId);
		}

		//Response postInvokerResponse = null;
		try {
			httpParams=insightsHelper.getHttpParams("invokerRequest", loginUser, insightName, jwtUserAuthToken,envUser,null,null,entitiyIdsMap);
			//postInvokerResponse = invokerUtils.callInvoker(httpParams,null);
		}catch(Exception e) {
			e.printStackTrace();
		}
		Thread.sleep(15000);
		//Assert.assertEquals(postInvokerResponse.getStatusCode(), Integer.parseInt(expectedStatus));
		Response userInsightsResponse = null;
		httpParams=insightsHelper.getHttpParams("notificationsRequest", null, insightName, jwtUserAuthToken,null,null,null,null);

		for (int i=0;i<10;i++)
		{
			Thread.sleep(5000);
			userInsightsResponse = insightUtils.getNotificationsV0(httpParams,null);
			if(!(userInsightsResponse.asString().equals("{}")))
			{
				insightsDBUtils.deleteUserDetail(Long.parseLong(userId.toString()),envCobrand);
				insightsDBUtils.deleteGeneratedInsights(Long.parseLong(userId.toString()),envCobrand);
				break;
			}
			if (i == 9 && !(numberOfInsights.equals("0"))){
				testInfo.setTestStatus("FAILED");
				testInfo.setReasonForFailure("There is no " + insightName + " insight generated in the given scheduled time");
				notificationsTestSummary.put(testCaseId+"_"+insightName+"_"+testCaseDescription , "FAILED");
				notificationsTestSummary.put(testCaseId+"_"+insightName+"_"+"Failed Reason ### ", testInfo.getReasonForFailure());
				testsInfoList.add(testInfo);
				Assert.fail("TEST FAILED");
			}
		}
		Assert.assertEquals(userInsightsResponse.getStatusCode(), HttpStatus.OK_200);
		logger.info("Status Verified "+userInsightsResponse+" OK , Verifying other attributes of Insight ->"+insightName);
		if(notificationTestStatus) {
			notificationTestStatus = insightsLevelVerifications.verifyFinancialFeesInsightsResponse(userInsightsResponse,insightName,"SCHEDULE",entityParameterName.toUpperCase(),entityParameterName,"",numberOfInsights,expNumOfKeys,failureReason,keysArray,entitiyIdsMap,validateKeys,duration);
		}
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

	public void addAccount(long providerId,String dagUserName,String dagUserPassword,String inputContainers)
	{
		try
		{
			Response response = null;
			if(!dagUserName.isEmpty()) {
				userInfo = User.builder().build();
				userInfo.setUsername("FFees" + System.currentTimeMillis());
				loginUser = userInfo.getUsername();
				System.out.println("User ----> "+userInfo.getUsername());
				userInfo.setPassword("TEST@123");
				sessionObj = EnvSession.builder().cobSession(configurationObj.getCobrandSessionObj().getCobSession())
						.path(configurationObj.getCobrandSessionObj().getPath()).build();
				userHelper.getUserSession(userInfo, sessionObj);
				commonEntity.setEnvSession(sessionObj);
				VisibleAllOver.setInstance(commonEntity);
				response = providerAccountUtils.addProviderAccountStrict(providerId, "fieldarray",dagUserName,dagUserPassword,sessionObj);
				providerAccountId = response.jsonPath().getLong(JSONPaths.PROVIDER_ACC_ID);
			}
			System.out.println("ProviderAccountId1::::===" + providerAccountId);
			jwtCobAuthToken = "Bearer ".concat(jwtHelper.getJWTToken("msampath@yodlee.com",envCobrand));	
			jwtUserAuthToken = "Bearer ".concat(jwtHelper.getJWTToken(userInfo.getUsername(),envUser));	
			userId = insightsGenerics.getMemId(sessionObj);
			System.out.println("jwtCobAuthToken = " +jwtCobAuthToken);
			System.out.println("JWTUserAuthToken for " + userInfo.getUsername()  + " =  " +jwtUserAuthToken);

			String[] inputContainerArray = inputContainers.split(",");
			for (String inputContainer:inputContainerArray)
			{		
				entitiyIdsMap = insightsGenerics.getItemAccounts(sessionObj,inputContainer);
			}
			entitiyIdsMap.put("InvalidId", "999999");
		}catch(Exception e)
		{
			e.printStackTrace();
		}
	}

	@AfterMethod(lastTimeOnly = true)
	public void prepareAutomationReport() throws Exception {
		TestInfo testInfo = new TestInfo();
		testInfo = insightsHelper.printSummaryOfTests(notificationsTestSummary,"GET  FinancialFees Insight",testInfo);		
		automationTestResults.setExecutionDate(new Date().toString());
		automationTestResults.setTestInformation(testsInfoList);
		automationTestResults.setUserName(loginUser);
		automationTestResults.setTestsPassPercentage(automationTestResults.getTestsPassPercentage(testInfo));
		insightsDBUtils.addTestResults(automationTestResults);
		System.out.println("Insight Test Execution is completed");
	}

	@AfterClass(alwaysRun = true)
	public void unRegisteredUser() {
		try {
			insightsDBUtils.deleteAllUserDetails(envCobrand);
			userUtils.unRegisterUser(sessionObj);
		}catch (Exception e) {
			e.printStackTrace();

		}
	}
}
