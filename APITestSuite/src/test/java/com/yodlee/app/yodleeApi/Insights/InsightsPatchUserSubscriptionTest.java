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

import com.yodlee.insights.yodleeApi.utils.InsightsHelper;
import com.yodlee.insights.yodleeApi.utils.InsightsProperties;
import com.yodlee.insights.yodleeApi.utils.TestInfo;
import com.google.gson.JsonArray;
import com.yodlee.insights.yodleeApi.utils.AutomationTestResults;
import com.yodlee.insights.yodleeApi.utils.FailureReason;
import com.yodlee.insights.yodleeApi.utils.InsightsDBUtils;
import com.yodlee.insights.yodleeApi.utils.InsightsGenerics;
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
import com.yodlee.yodleeApi.utils.apiUtils.InsightUtils;
import com.yodlee.yodleeApi.utils.commonUtils.CommonUtils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.restassured.response.Response;

public class InsightsPatchUserSubscriptionTest  extends Base{


	public static Long providerAccountId = null;
	protected Logger logger = LoggerFactory.getLogger(InsightsGetUserSubscriptionsTest.class);
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
	TaskUtils taskUtils = null;
	InsightUtils insightUtils = new InsightUtils();
	InsightsHelper insightsHelper = new InsightsHelper();
	HashMap<String,ArrayList<String>> negativeExpectedValuesMap;
	static Map<String ,String>  testSummary = new HashMap<String,String>();
	String executedInsight = null;
	String  jwtUserAuthToken = null,jwtCobAuthToken=null;
	InsightsGenerics insightsGenerics = new InsightsGenerics();
	static FailureReason failureReason = null;
	String cobrandUser="",storyId="",buildNo="240",loginUser="";
	AutomationTestResults automationTestResults = new AutomationTestResults();
	static Map<String ,String>  notificationsTestSummary = new HashMap<String,String>();

	HttpMethodParameters httpCobrandParams= null;
	List<TestInfo> testsInfoList = null;
	JwtHelper jwtHelper = new JwtHelper();
	InsightsDBUtils insightsDBUtils = new InsightsDBUtils();
	Response cobSubscriptionResponse = null;
	Response userSubscriptionResponse = null;
	HashMap<String, Object> userQueryParam =new HashMap<>();
	String envCobrand=null;
	String envUser=null;
	User userInfo = null;
	String patchUserSubscriptionBodyParam = null;
	InsightsProperties prop = new InsightsProperties();

	@BeforeClass(alwaysRun = true)
	public void setUp() throws Exception {
		System.out.println("initiated GET User subscription API execution");
		negativeExpectedValuesMap = insightsGenerics.readNegativeExpectedValues();
		userInfo = User.builder().build();
		userInfo.setUsername("GETUser" + System.currentTimeMillis());
		loginUser=userInfo.getUsername();
		userInfo.setPassword("TEST@123");
		sessionObj = EnvSession.builder().cobSession(configurationObj.getCobrandSessionObj().getCobSession())
				.path(configurationObj.getCobrandSessionObj().getPath()).build();
		userHelper.getUserSession(userInfo, sessionObj);
		cobrandUser=prop.readPropertiesFile().getProperty("cobrandUser");
	}

	@BeforeMethod(firstTimeOnly=true)
	public void initializations() throws Exception {
		envCobrand=prop.readPropertiesFile().getProperty("envCobrand");
		envUser=prop.readPropertiesFile().getProperty("envUser");
		jwtCobAuthToken = "Bearer ".concat(jwtHelper.getJWTToken(cobrandUser,envCobrand));
		jwtUserAuthToken = "Bearer ".concat(jwtHelper.getJWTToken(userInfo.getUsername(),envUser));
		testSummary = new HashMap<String,String>();
		failureReason = new  FailureReason();
		testsInfoList = new ArrayList<TestInfo>();
		httpCobrandParams=insightsHelper.getHttpParams("cobrandSubscriptionRequest", null, null, jwtCobAuthToken,null,null,null,null);
		cobSubscriptionResponse = insightUtils.getCobrandSubscriptions(httpCobrandParams,null);
		Assert.assertEquals(cobSubscriptionResponse.getStatusCode(), 200);
	}


	@Test(enabled = true, dataProvider = "feeder")
	@Source("\\TestData\\CSVFiles\\Insights\\LowBalWarning\\PatchUserSubscriptionLowBalWarning.csv")
	public void testPatchUserSubscriptionLowBalanceWarning(String testCaseId ,String testCaseDescription,String insightNameKey,String testNegativeScenario,String triggerType,String insightName,
			String insightTitle,String container,String cobrandId,String keys,String ruleExpression,String description,String applicableEntity,
			String entityParametersCount,String configMeta,String entityParameterName,String totalThresholds,String id,String frequency,String isSubscribed,String duration,String insightType,
			String threshold_Name_Value_Type,int expectedStatus,String testEnabled) throws ParseException, InterruptedException, JoseException, IOException {


		commontUtils.isTCEnabled(testEnabled, testCaseId);
		logger.info("Testcase--> "+testCaseId+ "->"+"  Validate GET "+insightNameKey +"  User Subscription ,"+testCaseDescription);
		automationTestResults.setInsights("PATCH_USERSUB_LowBalWarn");
		updateUserSubsctions(testCaseId ,testCaseDescription,insightNameKey,testNegativeScenario,triggerType,insightName,
				insightTitle,container,cobrandId,keys,ruleExpression,description,applicableEntity,
				entityParametersCount,configMeta,entityParameterName,totalThresholds,id,frequency,isSubscribed,duration,insightType,
				threshold_Name_Value_Type,expectedStatus,testEnabled);
	}

	@Test(enabled = true, dataProvider = "feeder")
	@Source("\\TestData\\CSVFiles\\Insights\\GoalSavingsOffTrack\\PatchUserSubscriptionGoalOffTrack.csv")
	public void testPatchUserSubscriptionGoalOffTrack(String testCaseId ,String testCaseDescription,String insightNameKey,String testNegativeScenario,String triggerType,String insightName,
			String insightTitle,String container,String cobrandId,String keys,String ruleExpression,String description,String applicableEntity,
			String entityParametersCount,String configMeta,String entityParameterName,String totalThresholds,String id,String frequency,String isSubscribed,String duration,String insightType,
			String threshold_Name_Value_Type,int expectedStatus,String testEnabled) throws ParseException, InterruptedException, JoseException, IOException {


		commontUtils.isTCEnabled(testEnabled, testCaseId);
		logger.info("Testcase--> "+testCaseId+ "->"+"  Validate GET "+insightNameKey +"  User Subscription ,"+testCaseDescription);
		automationTestResults.setInsights("PATCH_USERSUB_GoalOffTrack");
		updateUserSubsctions(testCaseId ,testCaseDescription,insightNameKey,testNegativeScenario,triggerType,insightName,
				insightTitle,container,cobrandId,keys,ruleExpression,description,applicableEntity,
				entityParametersCount,configMeta,entityParameterName,totalThresholds,id,frequency,isSubscribed,duration,insightType,
				threshold_Name_Value_Type,expectedStatus,testEnabled);
	}


	@Test(enabled = true, dataProvider = "feeder")
	@Source("\\TestData\\CSVFiles\\Insights\\ChangeInNetworth\\PatchUserSubscriptionNetworthChange.csv")
	public void testPatchUserSubscriptionNetworthChange(String testCaseId ,String testCaseDescription,String insightNameKey,String testNegativeScenario,String triggerType,String insightName,
			String insightTitle,String container,String cobrandId,String keys,String ruleExpression,String description,String applicableEntity,
			String entityParametersCount,String configMeta,String entityParameterName,String totalThresholds,String id,String frequency,String isSubscribed,String duration,String insightType,
			String threshold_Name_Value_Type,int expectedStatus,String testEnabled) throws ParseException, InterruptedException, JoseException, IOException {

		commontUtils.isTCEnabled(testEnabled, testCaseId);
		logger.info("Testcase--> "+testCaseId+ "->"+"  Validate GET "+insightNameKey +"  User Subscription ,"+testCaseDescription);
		automationTestResults.setInsights("PATCH_USERSUB_NetworthChange");
		updateUserSubsctions(testCaseId ,testCaseDescription,insightNameKey,testNegativeScenario,triggerType,insightName,
				insightTitle,container,cobrandId,keys,ruleExpression,description,applicableEntity,
				entityParametersCount,configMeta,entityParameterName,totalThresholds,id,frequency,isSubscribed,duration,insightType,
				threshold_Name_Value_Type,expectedStatus,testEnabled);
	}

	@Test(enabled = true, dataProvider = "feeder")
	@Source("\\TestData\\CSVFiles\\Insights\\SalDeposited\\PatchUserSubscriptionSalaryDeposited.csv")
	public void testPatchUserSubscriptionSalaryDeposited(String testCaseId ,String testCaseDescription,String insightNameKey,String testNegativeScenario,String triggerType,String insightName,
			String insightTitle,String container,String cobrandId,String keys,String ruleExpression,String description,String applicableEntity,
			String entityParametersCount,String configMeta,String entityParameterName,String totalThresholds,String id,String frequency,String isSubscribed,String duration,String insightType,
			String threshold_Name_Value_Type,int expectedStatus,String testEnabled) throws ParseException, InterruptedException, JoseException, IOException {

		commontUtils.isTCEnabled(testEnabled, testCaseId);
		logger.info("Testcase--> "+testCaseId+ "->"+"  Validate GET "+insightNameKey +"  User Subscription ,"+testCaseDescription);
		automationTestResults.setInsights("PATCH_USERSUB_SalaryDeposited");
		updateUserSubsctions(testCaseId ,testCaseDescription,insightNameKey,testNegativeScenario,triggerType,insightName,
				insightTitle,container,cobrandId,keys,ruleExpression,description,applicableEntity,
				entityParametersCount,configMeta,entityParameterName,totalThresholds,id,frequency,isSubscribed,duration,insightType,
				threshold_Name_Value_Type,expectedStatus,testEnabled);
	}

	@Test(enabled = true, dataProvider = "feeder")
	@Source("\\TestData\\CSVFiles\\Insights\\CameinUnderBudget\\PatchUserSubscriptionCameinUnderBudget.csv")
	public void testPatchUserSubscriptionCameinUnderBudget(String testCaseId ,String testCaseDescription,String insightNameKey,String testNegativeScenario,String triggerType,String insightName,
			String insightTitle,String container,String cobrandId,String keys,String ruleExpression,String description,String applicableEntity,
			String entityParametersCount,String configMeta,String entityParameterName,String totalThresholds,String id,String frequency,String isSubscribed,String duration,String insightType,
			String threshold_Name_Value_Type,int expectedStatus,String testEnabled) throws ParseException, InterruptedException, JoseException, IOException {

		commontUtils.isTCEnabled(testEnabled, testCaseId);
		logger.info("Testcase--> "+testCaseId+ "->"+"  Validate GET "+insightNameKey +"  User Subscription ,"+testCaseDescription);
		automationTestResults.setInsights("PATCH_USERSUB_CameinUnderBudget");
		updateUserSubsctions(testCaseId ,testCaseDescription,insightNameKey,testNegativeScenario,triggerType,insightName,
				insightTitle,container,cobrandId,keys,ruleExpression,description,applicableEntity,
				entityParametersCount,configMeta,entityParameterName,totalThresholds,id,frequency,isSubscribed,duration,insightType,
				threshold_Name_Value_Type,expectedStatus,testEnabled);
	}

	@Test(enabled = true, dataProvider = "feeder")
	@Source("\\TestData\\CSVFiles\\Insights\\CreditLimitWarning1\\PatchUserSubscriptionCreditLimitWarning.csv")
	public void testPatchUserSubscriptionCreditLimitWarning(String testCaseId ,String testCaseDescription,String insightNameKey,String testNegativeScenario,String triggerType,String insightName,
			String insightTitle,String container,String cobrandId,String keys,String ruleExpression,String description,String applicableEntity,
			String entityParametersCount,String configMeta,String entityParameterName,String totalThresholds,String id,String frequency,String isSubscribed,String duration,String insightType,
			String threshold_Name_Value_Type,int expectedStatus,String testEnabled) throws ParseException, InterruptedException, JoseException, IOException {

		commontUtils.isTCEnabled(testEnabled, testCaseId);
		logger.info("Testcase--> "+testCaseId+ "->"+"  Validate GET "+insightNameKey +"  User Subscription ,"+testCaseDescription);
		automationTestResults.setInsights("PATCH_USERSUB_CreditLimitWarning");
		updateUserSubsctions(testCaseId ,testCaseDescription,insightNameKey,testNegativeScenario,triggerType,insightName,
				insightTitle,container,cobrandId,keys,ruleExpression,description,applicableEntity,
				entityParametersCount,configMeta,entityParameterName,totalThresholds,id,frequency,isSubscribed,duration,insightType,
				threshold_Name_Value_Type,expectedStatus,testEnabled);
	}

	@Test(enabled = true, dataProvider = "feeder")
	@Source("\\TestData\\CSVFiles\\Insights\\LargeDepositNotice\\PatchUserSubscriptionLargeDepositNotice.csv")
	public void testPatchUserSubscriptionLargeDepositNotice(String testCaseId ,String testCaseDescription,String insightNameKey,String testNegativeScenario,String triggerType,String insightName,
			String insightTitle,String container,String cobrandId,String keys,String ruleExpression,String description,String applicableEntity,
			String entityParametersCount,String configMeta,String entityParameterName,String totalThresholds,String id,String frequency,String isSubscribed,String duration,String insightType,
			String threshold_Name_Value_Type,int expectedStatus,String testEnabled) throws ParseException, InterruptedException, JoseException, IOException {

		commontUtils.isTCEnabled(testEnabled, testCaseId);
		logger.info("Testcase--> "+testCaseId+ "->"+"  Validate GET "+insightNameKey +"  User Subscription ,"+testCaseDescription);
		automationTestResults.setInsights("PATCH_USERSUB_LargeDepositNotice");
		updateUserSubsctions(testCaseId ,testCaseDescription,insightNameKey,testNegativeScenario,triggerType,insightName,
				insightTitle,container,cobrandId,keys,ruleExpression,description,applicableEntity,
				entityParametersCount,configMeta,entityParameterName,totalThresholds,id,frequency,isSubscribed,duration,insightType,
				threshold_Name_Value_Type,expectedStatus,testEnabled);
	}
	@Test(enabled = true, dataProvider = "feeder")
	@Source("\\TestData\\CSVFiles\\Insights\\CardStatementDueReminder\\PatchUserSubscriptionCardStatementDueReminder.csv")
	public void testPatchUserSubscriptionCardStatementDueReminder(String testCaseId ,String testCaseDescription,String insightNameKey,String testNegativeScenario,String triggerType,String insightName,
			String insightTitle,String container,String cobrandId,String keys,String ruleExpression,String description,String applicableEntity,
			String entityParametersCount,String configMeta,String entityParameterName,String totalThresholds,String id,String frequency,String isSubscribed,String duration,String insightType,
			String threshold_Name_Value_Type,int expectedStatus,String testEnabled) throws ParseException, InterruptedException, JoseException, IOException {

		commontUtils.isTCEnabled(testEnabled, testCaseId);
		logger.info("Testcase--> "+testCaseId+ "->"+"  Validate GET "+insightNameKey +"  User Subscription ,"+testCaseDescription);
		automationTestResults.setInsights("PATCH_USERSUB_CardStatementDueReminder");
		updateUserSubsctions(testCaseId ,testCaseDescription,insightNameKey,testNegativeScenario,triggerType,insightName,
				insightTitle,container,cobrandId,keys,ruleExpression,description,applicableEntity,
				entityParametersCount,configMeta,entityParameterName,totalThresholds,id,frequency,isSubscribed,duration,insightType,
				threshold_Name_Value_Type,expectedStatus,testEnabled);
	}


	@Test(enabled = true, dataProvider = "feeder")
	@Source("\\TestData\\CSVFiles\\Insights\\ChangeInLiability\\PatchUserSubscriptionLiabilityChange.csv")
	public void testPatchUserSubscriptionLiabilityChange(String testCaseId ,String testCaseDescription,String insightNameKey,String testNegativeScenario,String triggerType,String insightName,
			String insightTitle,String container,String cobrandId,String keys,String ruleExpression,String description,String applicableEntity,
			String entityParametersCount,String configMeta,String entityParameterName,String totalThresholds,String id,String frequency,String isSubscribed,String duration,String insightType,
			String threshold_Name_Value_Type,int expectedStatus,String testEnabled) throws ParseException, InterruptedException, JoseException, IOException {

		commontUtils.isTCEnabled(testEnabled, testCaseId);
		logger.info("Testcase--> "+testCaseId+ "->"+"  Validate GET "+insightNameKey +"  User Subscription ,"+testCaseDescription);
		automationTestResults.setInsights("PATCH_USERSUB_LiabilityChange");
		updateUserSubsctions(testCaseId ,testCaseDescription,insightNameKey,testNegativeScenario,triggerType,insightName,
				insightTitle,container,cobrandId,keys,ruleExpression,description,applicableEntity,
				entityParametersCount,configMeta,entityParameterName,totalThresholds,id,frequency,isSubscribed,duration,insightType,
				threshold_Name_Value_Type,expectedStatus,testEnabled);
	}
	/*@Test(enabled = true, dataProvider = "feeder")
	@Source("\\TestData\\CSVFiles\\Insights\\CardStatementAvailable\\PatchUserSubscriptionCardStatementAvailable.csv")
	public void testPatchUserSubscriptionCardStatementAvailable(String testCaseId ,String testCaseDescription,String insightNameKey,String testNegativeScenario,String triggerType,String insightName,
			String insightTitle,String container,String cobrandId,String keys,String ruleExpression,String description,String applicableEntity,
			String entityParametersCount,String configMeta,String entityParameterName,String totalThresholds,String id,String frequency,String isSubscribed,String duration,String insightType,
			String threshold_Name_Value_Type,int expectedStatus,String testEnabled) throws ParseException, InterruptedException, JoseException {

		commontUtils.isTCEnabled(testEnabled, testCaseId);
		boolean testExecutionStatus = false;
		executedInsight=insightNameKey;
		logger.info("Testcase--> "+testCaseId+ "->"+"  Validate PATCH "+insightNameKey +"  User Subscription ,"+testCaseDescription);
		HttpMethodParameters cobHttpParams = HttpMethodParameters.builder().build();
		HttpMethodParameters userHttpParams = HttpMethodParameters.builder().build();
		cobHttpParams.setHeader("Authorization", jwtCobAuthToken);
		userHttpParams.setHeader("Authorization", jwtUserAuthToken);
		String patchUserSubscriptionBodyParam;
		patchUserSubscriptionBodyParam = insightsHelper.constructPatchUserSubscriptionRequest(insightNameKey, testNegativeScenario, insightName, insightTitle, container,
				cobrandId, keys, ruleExpression, description, applicableEntity,	entityParametersCount, configMeta,entityParameterName, totalThresholds,id,
				frequency, isSubscribed, duration, insightType,threshold_Name_Value_Type,triggerType);
		userHttpParams.setBodyParams(patchUserSubscriptionBodyParam);
		Response patchUserSubscriptionResponse = insightUtils.patchUserSubscriptions(userHttpParams);
		Response cobSubscriptionResponse = insightUtils.getCobrandSubscriptions(cobHttpParams);
		Response userSubscriptionResponse = insightUtils.getUserSubscription(userHttpParams);
		if(patchUserSubscriptionResponse.getStatusCode() == expectedStatus) {
			testExecutionStatus = true;
		}
		insightsGenerics.updateUserSubscriptionResponseVerificationPostPatch(testCaseId,testExecutionStatus,testSummary,testCaseDescription,negativeExpectedValuesMap,testNegativeScenario,patchUserSubscriptionResponse, cobSubscriptionResponse, userSubscriptionResponse, insightNameKey, frequency,duration, insightTitle, entityParameterName, isSubscribed, threshold_Name_Value_Type,expectedStatus,failureReason);

	}*/


	@Test(enabled = true, dataProvider = "feeder")
	@Source("\\TestData\\CSVFiles\\Insights\\CreditUtilization1\\PatchUserSubscriptionCreditUtilization.csv")
	public void testPatchUserSubscriptionCreditUtilization(String testCaseId ,String testCaseDescription,String insightNameKey,String testNegativeScenario,String triggerType,String insightName,
			String insightTitle,String container,String cobrandId,String keys,String ruleExpression,String description,String applicableEntity,
			String entityParametersCount,String configMeta,String entityParameterName,String totalThresholds,String id,String frequency,String isSubscribed,String duration,String insightType,
			String threshold_Name_Value_Type,int expectedStatus,String testEnabled) throws Exception {

		commontUtils.isTCEnabled(testEnabled, testCaseId);
		logger.info("Testcase--> "+testCaseId+ "->"+"  Validate GET "+insightNameKey +"  User Subscription ,"+testCaseDescription);
		automationTestResults.setInsights("PATCH_USERSUB_CreditUtilization");
		updateUserSubsctions(testCaseId ,testCaseDescription,insightNameKey,testNegativeScenario,triggerType,insightName,
				insightTitle,container,cobrandId,keys,ruleExpression,description,applicableEntity,
				entityParametersCount,configMeta,entityParameterName,totalThresholds,id,frequency,isSubscribed,duration,insightType,
				threshold_Name_Value_Type,expectedStatus,testEnabled);
	}

	/*@Test(enabled = true, dataProvider = "feeder")
	@Source("\\TestData\\CSVFiles\\Insights\\LoanMaturityNotice\\PatchUserSubscriptionLoanMaturityNotice.csv")
	public void testPatchUserSubscriptionLoanMaturityNotice(String testCaseId ,String testCaseDescription,String insightNameKey,String testNegativeScenario,String triggerType,String insightName,
			String insightTitle,String container,String cobrandId,String keys,String ruleExpression,String description,String applicableEntity,
			String entityParametersCount,String configMeta,String entityParameterName,String totalThresholds,String id,String frequency,String isSubscribed,String duration,String insightType,
			String threshold_Name_Value_Type,int expectedStatus,String testEnabled) throws ParseException, InterruptedException, JoseException {

		commontUtils.isTCEnabled(testEnabled, testCaseId);
		boolean testExecutionStatus = false;
		executedInsight=insightNameKey;
		logger.info("Testcase--> "+testCaseId+ "->"+"  Validate PATCH "+insightNameKey +"  User Subscription ,"+testCaseDescription);
		HttpMethodParameters cobHttpParams = HttpMethodParameters.builder().build();
		HttpMethodParameters userHttpParams = HttpMethodParameters.builder().build();
		cobHttpParams.setHeader("Authorization", jwtCobAuthToken);
		userHttpParams.setHeader("Authorization", jwtUserAuthToken);
		String patchUserSubscriptionBodyParam;
		patchUserSubscriptionBodyParam = insightsHelper.constructPatchUserSubscriptionRequest(insightNameKey, testNegativeScenario, insightName, insightTitle, container,
				cobrandId, keys, ruleExpression, description, applicableEntity,	entityParametersCount, configMeta,entityParameterName, totalThresholds,id,
				frequency, isSubscribed, duration, insightType,threshold_Name_Value_Type,triggerType);
		userHttpParams.setBodyParams(patchUserSubscriptionBodyParam);
		Response patchUserSubscriptionResponse = insightUtils.patchUserSubscriptions(userHttpParams);
		Response cobSubscriptionResponse = insightUtils.getCobrandSubscriptions(cobHttpParams);
		Response userSubscriptionResponse = insightUtils.getUserSubscription(userHttpParams);
		if(patchUserSubscriptionResponse.getStatusCode() == expectedStatus) {
			testExecutionStatus = true;
		}
		insightsGenerics.updateUserSubscriptionResponseVerificationPostPatch(testCaseId,testExecutionStatus,testSummary,testCaseDescription,negativeExpectedValuesMap,testNegativeScenario,patchUserSubscriptionResponse, cobSubscriptionResponse, userSubscriptionResponse, insightNameKey, frequency,duration, insightTitle, entityParameterName, isSubscribed, threshold_Name_Value_Type,expectedStatus,failureReason);

	}*/
	/*@Test(enabled = true, dataProvider = "feeder")
	@Source("\\TestData\\CSVFiles\\Insights\\InvestmentMaturityNotice\\PatchUserSubscriptionInvestmentMaturityNotice.csv")
	public void testPatchUserSubscriptionInvestmentMaturityNotice(String testCaseId ,String testCaseDescription,String insightNameKey,String testNegativeScenario,String triggerType,String insightName,
			String insightTitle,String container,String cobrandId,String keys,String ruleExpression,String description,String applicableEntity,
			String entityParametersCount,String configMeta,String entityParameterName,String totalThresholds,String id,String frequency,String isSubscribed,String duration,String insightType,
			String threshold_Name_Value_Type,int expectedStatus,String testEnabled) throws ParseException, InterruptedException, JoseException {

		commontUtils.isTCEnabled(testEnabled, testCaseId);
		boolean testExecutionStatus = false;
		executedInsight=insightNameKey;
		logger.info("Testcase--> "+testCaseId+ "->"+"  Validate PATCH "+insightNameKey +"  User Subscription ,"+testCaseDescription);
		HttpMethodParameters cobHttpParams = HttpMethodParameters.builder().build();
		HttpMethodParameters userHttpParams = HttpMethodParameters.builder().build();
		cobHttpParams.setHeader("Authorization", jwtCobAuthToken);
		userHttpParams.setHeader("Authorization", jwtUserAuthToken);
		String patchUserSubscriptionBodyParam;
		patchUserSubscriptionBodyParam = insightsHelper.constructPatchUserSubscriptionRequest(insightNameKey, testNegativeScenario, insightName, insightTitle, container,
				cobrandId, keys, ruleExpression, description, applicableEntity,	entityParametersCount, configMeta,entityParameterName, totalThresholds,id,
				frequency, isSubscribed, duration, insightType,threshold_Name_Value_Type,triggerType);
		userHttpParams.setBodyParams(patchUserSubscriptionBodyParam);
		Response patchUserSubscriptionResponse = insightUtils.patchUserSubscriptions(userHttpParams);
		Response cobSubscriptionResponse = insightUtils.getCobrandSubscriptions(cobHttpParams);
		Response userSubscriptionResponse = insightUtils.getUserSubscription(userHttpParams);
		if(patchUserSubscriptionResponse.getStatusCode() == expectedStatus) {
			testExecutionStatus = true;
		}
		insightsGenerics.updateUserSubscriptionResponseVerificationPostPatch(testCaseId,testExecutionStatus,testSummary,testCaseDescription,negativeExpectedValuesMap,testNegativeScenario,patchUserSubscriptionResponse, cobSubscriptionResponse, userSubscriptionResponse, insightNameKey, frequency,duration, insightTitle, entityParameterName, isSubscribed, threshold_Name_Value_Type,expectedStatus,failureReason);

	}*/
	@Test(enabled = true, dataProvider = "feeder")
	@Source("\\TestData\\CSVFiles\\Insights\\ChangeInAsset\\PatchUserSubscriptionChangeInAsset.csv")
	public void testPatchUserSubscriptionAssetChange(String testCaseId ,String testCaseDescription,String insightNameKey,String testNegativeScenario,String triggerType,String insightName,
			String insightTitle,String container,String cobrandId,String keys,String ruleExpression,String description,String applicableEntity,
			String entityParametersCount,String configMeta,String entityParameterName,String totalThresholds,String id,String frequency,String isSubscribed,String duration,String insightType,
			String threshold_Name_Value_Type,int expectedStatus,String testEnabled) throws ParseException, InterruptedException, JoseException, IOException {

		commontUtils.isTCEnabled(testEnabled, testCaseId);
		logger.info("Testcase--> "+testCaseId+ "->"+"  Validate GET "+insightNameKey +"  User Subscription ,"+testCaseDescription);
		automationTestResults.setInsights("PATCH_USERSUB_AssetChange");
		updateUserSubsctions(testCaseId ,testCaseDescription,insightNameKey,testNegativeScenario,triggerType,insightName,
				insightTitle,container,cobrandId,keys,ruleExpression,description,applicableEntity,
				entityParametersCount,configMeta,entityParameterName,totalThresholds,id,frequency,isSubscribed,duration,insightType,
				threshold_Name_Value_Type,expectedStatus,testEnabled);
	}

	/*@Test(enabled = true, dataProvider = "feeder")
	@Source("\\TestData\\CSVFiles\\Insights\\LargePurchase\\PatchUserSubscriptionLargePurchase.csv")
	public void testPatchUserSubscriptionLargePurchase(String testCaseId ,String testCaseDescription,String insightNameKey,String testNegativeScenario,String triggerType,String insightName,
			String insightTitle,String container,String cobrandId,String keys,String ruleExpression,String description,String applicableEntity,
			String entityParametersCount,String configMeta,String entityParameterName,String totalThresholds,String id,String frequency,String isSubscribed,String duration,String insightType,
			String threshold_Name_Value_Type,int expectedStatus,String testEnabled) throws ParseException, InterruptedException, JoseException {

		commontUtils.isTCEnabled(testEnabled, testCaseId);
		boolean testExecutionStatus = false;
		executedInsight=insightNameKey;
		logger.info("Testcase--> "+testCaseId+ "->"+"  Validate PATCH "+insightNameKey +"  User Subscription ,"+testCaseDescription);
		HttpMethodParameters cobHttpParams = HttpMethodParameters.builder().build();
		HttpMethodParameters userHttpParams = HttpMethodParameters.builder().build();
		cobHttpParams.setHeader("Authorization", jwtCobAuthToken);
		userHttpParams.setHeader("Authorization", jwtUserAuthToken);
		String patchUserSubscriptionBodyParam;
		patchUserSubscriptionBodyParam = insightsHelper.constructPatchUserSubscriptionRequest(insightNameKey, testNegativeScenario, insightName, insightTitle, container,
				cobrandId, keys, ruleExpression, description, applicableEntity,	entityParametersCount, configMeta,entityParameterName, totalThresholds,id,
				frequency, isSubscribed, duration, insightType,threshold_Name_Value_Type,triggerType);
		userHttpParams.setBodyParams(patchUserSubscriptionBodyParam);
		Response patchUserSubscriptionResponse = insightUtils.patchUserSubscriptions(userHttpParams);
		Response cobSubscriptionResponse = insightUtils.getCobrandSubscriptions(cobHttpParams);
		Response userSubscriptionResponse = insightUtils.getUserSubscription(userHttpParams);
		if(patchUserSubscriptionResponse.getStatusCode() == expectedStatus) {
			testExecutionStatus = true;
		}
		insightsGenerics.updateUserSubscriptionResponseVerificationPostPatch(testCaseId,testExecutionStatus,testSummary,testCaseDescription,negativeExpectedValuesMap,testNegativeScenario,patchUserSubscriptionResponse, cobSubscriptionResponse, userSubscriptionResponse, insightNameKey, frequency,duration, insightTitle, entityParameterName, isSubscribed, threshold_Name_Value_Type,expectedStatus,failureReason);

	}*/

	/*@Test(enabled = true, dataProvider = "feeder")
	@Source("\\TestData\\CSVFiles\\Insights\\ExpenseExceededBudget\\PatchUserSubscriptionExpenseExceededBudget.csv")
	public void testPatchUserSubscriptionExpenseExceededBudget(String testCaseId ,String testCaseDescription,String insightNameKey,String testNegativeScenario,String triggerType,String insightName,
			String insightTitle,String container,String cobrandId,String keys,String ruleExpression,String description,String applicableEntity,
			String entityParametersCount,String configMeta,String entityParameterName,String totalThresholds,String id,String frequency,String isSubscribed,String duration,String insightType,
			String threshold_Name_Value_Type,int expectedStatus,String testEnabled) throws ParseException, InterruptedException, JoseException {

		commontUtils.isTCEnabled(testEnabled, testCaseId);
		boolean testExecutionStatus = false;
		executedInsight=insightNameKey;
		logger.info("Testcase--> "+testCaseId+ "->"+"  Validate PATCH "+insightNameKey +"  User Subscription ,"+testCaseDescription);
		HttpMethodParameters cobHttpParams = HttpMethodParameters.builder().build();
		HttpMethodParameters userHttpParams = HttpMethodParameters.builder().build();
		cobHttpParams.setHeader("Authorization", jwtCobAuthToken);
		userHttpParams.setHeader("Authorization", jwtUserAuthToken);
		String patchUserSubscriptionBodyParam;
		patchUserSubscriptionBodyParam = insightsHelper.constructPatchUserSubscriptionRequest(insightNameKey, testNegativeScenario, insightName, insightTitle, container,
				cobrandId, keys, ruleExpression, description, applicableEntity,	entityParametersCount, configMeta,entityParameterName, totalThresholds,id,
				frequency, isSubscribed, duration, insightType,threshold_Name_Value_Type,triggerType);
		userHttpParams.setBodyParams(patchUserSubscriptionBodyParam);
		Response patchUserSubscriptionResponse = insightUtils.patchUserSubscriptions(userHttpParams);
		Response cobSubscriptionResponse = insightUtils.getCobrandSubscriptions(cobHttpParams);
		Response userSubscriptionResponse = insightUtils.getUserSubscription(userHttpParams);
		if(patchUserSubscriptionResponse.getStatusCode() == expectedStatus) {
			testExecutionStatus = true;
		}
		insightsGenerics.updateUserSubscriptionResponseVerificationPostPatch(testCaseId,testExecutionStatus,testSummary,testCaseDescription,negativeExpectedValuesMap,testNegativeScenario,patchUserSubscriptionResponse, cobSubscriptionResponse, userSubscriptionResponse, insightNameKey, frequency,duration, insightTitle, entityParameterName, isSubscribed, threshold_Name_Value_Type,expectedStatus,failureReason);
	}*/
	
	@Test(enabled = true, dataProvider = "feeder")
	@Source("\\TestData\\CSVFiles\\Insights\\ExpensesApproachingBudget\\PatchUserSubscriptionExpensesApproachingBudget.csv")
	public void testPatchUserSubscriptionExpensesApproachingBudget(String testCaseId ,String testCaseDescription,String insightNameKey,String testNegativeScenario,String triggerType,String insightName,
			String insightTitle,String container,String cobrandId,String keys,String ruleExpression,String description,String applicableEntity,
			String entityParametersCount,String configMeta,String entityParameterName,String totalThresholds,String id,String frequency,String isSubscribed,String duration,String insightType,
			String threshold_Name_Value_Type,int expectedStatus,String testEnabled) throws ParseException, InterruptedException, JoseException, IOException {

		commontUtils.isTCEnabled(testEnabled, testCaseId);
		logger.info("Testcase--> "+testCaseId+ "->"+"  Validate GET "+insightNameKey +"  User Subscription ,"+testCaseDescription);
		automationTestResults.setInsights("PATCH_USERSUB_ExpensesApproachingBudget");
		updateUserSubsctions(testCaseId ,testCaseDescription,insightNameKey,testNegativeScenario,triggerType,insightName,
				insightTitle,container,cobrandId,keys,ruleExpression,description,applicableEntity,
				entityParametersCount,configMeta,entityParameterName,totalThresholds,id,frequency,isSubscribed,duration,insightType,
				threshold_Name_Value_Type,expectedStatus,testEnabled);	}

	@Test(enabled = true, dataProvider = "feeder")
	@Source("\\TestData\\CSVFiles\\Insights\\upcomingSubscription\\PatchUserSubscriptionUpcomingSubscription.csv")
	public void testPatchUserSubscriptionUpcomingSubscription(String testCaseId ,String testCaseDescription,String insightNameKey,String testNegativeScenario,String triggerType,String insightName,
			String insightTitle,String container,String cobrandId,String keys,String ruleExpression,String description,String applicableEntity,
			String entityParametersCount,String configMeta,String entityParameterName,String totalThresholds,String id,String frequency,String isSubscribed,String duration,String insightType,
			String threshold_Name_Value_Type,int expectedStatus,String testEnabled) throws ParseException, InterruptedException, JoseException,IOException {


		commontUtils.isTCEnabled(testEnabled, testCaseId);
		logger.info("Testcase--> "+testCaseId+ "->"+"  Validate GET "+insightNameKey +"  User Subscription ,"+testCaseDescription);
		automationTestResults.setInsights("PATCH_USERSUB_UpcomingSubscription");
		updateUserSubsctions(testCaseId ,testCaseDescription,insightNameKey,testNegativeScenario,triggerType,insightName,
				insightTitle,container,cobrandId,keys,ruleExpression,description,applicableEntity,
				entityParametersCount,configMeta,entityParameterName,totalThresholds,id,frequency,isSubscribed,duration,insightType,
				threshold_Name_Value_Type,expectedStatus,testEnabled);
	}

	@Test(enabled = true, dataProvider = "feeder")
	@Source("\\TestData\\CSVFiles\\Insights\\NewBillEventDetected\\PatchUserSubscriptionNewBill.csv")
	public void testPatchUserSubscriptionNewBill(String testCaseId ,String testCaseDescription,String insightNameKey,String testNegativeScenario,String triggerType,String insightName,
			String insightTitle,String container,String cobrandId,String keys,String ruleExpression,String description,String applicableEntity,
			String entityParametersCount,String configMeta,String entityParameterName,String totalThresholds,String id,String frequency,String isSubscribed,String duration,String insightType,
			String threshold_Name_Value_Type,int expectedStatus,String testEnabled) throws ParseException, InterruptedException, JoseException,IOException {

		commontUtils.isTCEnabled(testEnabled, testCaseId);
		logger.info("Testcase--> "+testCaseId+ "->"+"  Validate GET "+insightNameKey +"  User Subscription ,"+testCaseDescription);
		automationTestResults.setInsights("PATCH_USERSUB_NewBill");
		updateUserSubsctions(testCaseId ,testCaseDescription,insightNameKey,testNegativeScenario,triggerType,insightName,
				insightTitle,container,cobrandId,keys,ruleExpression,description,applicableEntity,
				entityParametersCount,configMeta,entityParameterName,totalThresholds,id,frequency,isSubscribed,duration,insightType,
				threshold_Name_Value_Type,expectedStatus,testEnabled);
	}
	@Test(enabled = true, dataProvider = "feeder")
	@Source("\\TestData\\CSVFiles\\Insights\\NewSubscriptionEventDetected\\PatchUserSubscriptionNewSubscription.csv")
	public void testPatchUserSubscriptionNewSubscription(String testCaseId ,String testCaseDescription,String insightNameKey,String testNegativeScenario,String triggerType,String insightName,
			String insightTitle,String container,String cobrandId,String keys,String ruleExpression,String description,String applicableEntity,
			String entityParametersCount,String configMeta,String entityParameterName,String totalThresholds,String id,String frequency,String isSubscribed,String duration,String insightType,
			String threshold_Name_Value_Type,int expectedStatus,String testEnabled) throws ParseException, InterruptedException, JoseException,IOException {


		commontUtils.isTCEnabled(testEnabled, testCaseId);
		logger.info("Testcase--> "+testCaseId+ "->"+"  Validate GET "+insightNameKey +"  User Subscription ,"+testCaseDescription);
		automationTestResults.setInsights("PATCH_USERSUB_NewSubscription");
		updateUserSubsctions(testCaseId ,testCaseDescription,insightNameKey,testNegativeScenario,triggerType,insightName,
				insightTitle,container,cobrandId,keys,ruleExpression,description,applicableEntity,
				entityParametersCount,configMeta,entityParameterName,totalThresholds,id,frequency,isSubscribed,duration,insightType,
				threshold_Name_Value_Type,expectedStatus,testEnabled);
	}

	@Test(enabled = true, dataProvider = "feeder")
	@Source("\\TestData\\CSVFiles\\Insights\\BillNotPaid\\PatchUserSubscriptionBillNotPaid.csv")
	public void testPatchUserSubscriptionBillNotPaid(String testCaseId ,String testCaseDescription,String insightNameKey,String testNegativeScenario,String triggerType,String insightName,
			String insightTitle,String container,String cobrandId,String keys,String ruleExpression,String description,String applicableEntity,
			String entityParametersCount,String configMeta,String entityParameterName,String totalThresholds,String id,String frequency,String isSubscribed,String duration,String insightType,
			String threshold_Name_Value_Type,int expectedStatus,String testEnabled) throws ParseException, InterruptedException, JoseException,IOException {

		commontUtils.isTCEnabled(testEnabled, testCaseId);
		logger.info("Testcase--> "+testCaseId+ "->"+"  Validate GET "+insightNameKey +"  User Subscription ,"+testCaseDescription);
		automationTestResults.setInsights("PATCH_USERSUB_BillNotPaid");
		updateUserSubsctions(testCaseId ,testCaseDescription,insightNameKey,testNegativeScenario,triggerType,insightName,
				insightTitle,container,cobrandId,keys,ruleExpression,description,applicableEntity,
				entityParametersCount,configMeta,entityParameterName,totalThresholds,id,frequency,isSubscribed,duration,insightType,
				threshold_Name_Value_Type,expectedStatus,testEnabled);
	}

	@Test(enabled = true, dataProvider = "feeder")
	@Source("\\TestData\\CSVFiles\\Insights\\upcomingBills\\PatchUserSubscriptionUpcomingBills.csv")
	public void testPatchUserSubscriptionUpcomingBills(String testCaseId ,String testCaseDescription,String insightNameKey,String testNegativeScenario,String triggerType,String insightName,
			String insightTitle,String container,String cobrandId,String keys,String ruleExpression,String description,String applicableEntity,
			String entityParametersCount,String configMeta,String entityParameterName,String totalThresholds,String id,String frequency,String isSubscribed,String duration,String insightType,
			String threshold_Name_Value_Type,int expectedStatus,String testEnabled) throws ParseException, InterruptedException, JoseException,IOException {
		commontUtils.isTCEnabled(testEnabled, testCaseId);
		logger.info("Testcase--> "+testCaseId+ "->"+"  Validate GET "+insightNameKey +"  User Subscription ,"+testCaseDescription);
		automationTestResults.setInsights("PATCH_USERSUB_UpcomingBills");
		updateUserSubsctions(testCaseId ,testCaseDescription,insightNameKey,testNegativeScenario,triggerType,insightName,
				insightTitle,container,cobrandId,keys,ruleExpression,description,applicableEntity,
				entityParametersCount,configMeta,entityParameterName,totalThresholds,id,frequency,isSubscribed,duration,insightType,
				threshold_Name_Value_Type,expectedStatus,testEnabled);
	}


	@Test(enabled = true, dataProvider = "feeder")
	@Source("\\TestData\\CSVFiles\\Insights\\FinancialFees\\PatchUserSubscriptionFinancialFees.csv")
	public void testPatchUserSubscriptionFinancialFees(String testCaseId ,String testCaseDescription,String insightNameKey,String testNegativeScenario,String triggerType,String insightName,
			String insightTitle,String container,String cobrandId,String keys,String ruleExpression,String description,String applicableEntity,
			String entityParametersCount,String configMeta,String entityParameterName,String totalThresholds,String id,String frequency,String isSubscribed,String duration,String insightType,
			String threshold_Name_Value_Type,int expectedStatus,String testEnabled) throws ParseException, InterruptedException, JoseException, IOException {

		commontUtils.isTCEnabled(testEnabled, testCaseId);
		logger.info("Testcase--> "+testCaseId+ "->"+"  Validate GET "+insightNameKey +"  User Subscription ,"+testCaseDescription);
		automationTestResults.setInsights("PATCH_USERSUB_FinancialFees");
		updateUserSubsctions(testCaseId ,testCaseDescription,insightNameKey,testNegativeScenario,triggerType,insightName,
				insightTitle,container,cobrandId,keys,ruleExpression,description,applicableEntity,
				entityParametersCount,configMeta,entityParameterName,totalThresholds,id,frequency,isSubscribed,duration,insightType,
				threshold_Name_Value_Type,expectedStatus,testEnabled);
	}

	@Test(enabled = true, dataProvider = "feeder")
	@Source("\\TestData\\CSVFiles\\Insights\\SpendingbyCategory\\PatchUserSubscriptionSpendingbyCategory.csv")
	public void testPatchUserSubscriptionSpendingbyCategory(String testCaseId ,String testCaseDescription,String insightNameKey,String testNegativeScenario,String triggerType,String insightName,
			String insightTitle,String container,String cobrandId,String keys,String ruleExpression,String description,String applicableEntity,
			String entityParametersCount,String configMeta,String entityParameterName,String totalThresholds,String id,String frequency,String isSubscribed,String duration,String insightType,
			String threshold_Name_Value_Type,int expectedStatus,String testEnabled) throws ParseException, InterruptedException, JoseException, IOException {

		commontUtils.isTCEnabled(testEnabled, testCaseId);
		logger.info("Testcase--> "+testCaseId+ "->"+"  Validate GET "+insightNameKey +"  User Subscription ,"+testCaseDescription);
		automationTestResults.setInsights("PATCH_USERSUB_SpendingbyCategory");
		updateUserSubsctions(testCaseId ,testCaseDescription,insightNameKey,testNegativeScenario,triggerType,insightName,
				insightTitle,container,cobrandId,keys,ruleExpression,description,applicableEntity,
				entityParametersCount,configMeta,entityParameterName,totalThresholds,id,frequency,isSubscribed,duration,insightType,
				threshold_Name_Value_Type,expectedStatus,testEnabled);
	}
	
	@Test(enabled = true, dataProvider = "feeder")
	@Source("\\TestData\\CSVFiles\\Insights\\TopMerchants\\PatchUserSubscriptionTopMerchants.csv")
	public void testPatchUserSubscriptionTopMerchants(String testCaseId ,String testCaseDescription,String insightNameKey,String testNegativeScenario,String triggerType,String insightName,
			String insightTitle,String container,String cobrandId,String keys,String ruleExpression,String description,String applicableEntity,
			String entityParametersCount,String configMeta,String entityParameterName,String totalThresholds,String id,String frequency,String isSubscribed,String duration,String insightType,
			String threshold_Name_Value_Type,int expectedStatus,String testEnabled) throws ParseException, InterruptedException, JoseException, IOException {

		commontUtils.isTCEnabled(testEnabled, testCaseId);
		logger.info("Testcase--> "+testCaseId+ "->"+"  Validate GET "+insightNameKey +"  User Subscription ,"+testCaseDescription);
		automationTestResults.setInsights("PATCH_USERSUB_TopMerchants");
		updateUserSubsctions(testCaseId ,testCaseDescription,insightNameKey,testNegativeScenario,triggerType,insightName,
				insightTitle,container,cobrandId,keys,ruleExpression,description,applicableEntity,
				entityParametersCount,configMeta,entityParameterName,totalThresholds,id,frequency,isSubscribed,duration,insightType,
				threshold_Name_Value_Type,expectedStatus,testEnabled);
	}

	@Test(enabled = true, dataProvider = "feeder")
	@Source("\\TestData\\CSVFiles\\Insights\\IncomeExpenseOverview\\PatchUserSubscriptionIncomeExpenseOverview.csv")
	public void testPatchUserSubscriptionIncomeExpenseOverview(String testCaseId ,String testCaseDescription,String insightNameKey,String testNegativeScenario,String triggerType,String insightName,
			String insightTitle,String container,String cobrandId,String keys,String ruleExpression,String description,String applicableEntity,
			String entityParametersCount,String configMeta,String entityParameterName,String totalThresholds,String id,String frequency,String isSubscribed,String duration,String insightType,
			String threshold_Name_Value_Type,int expectedStatus,String testEnabled) throws ParseException, InterruptedException, JoseException,IOException {
		commontUtils.isTCEnabled(testEnabled, testCaseId);
		logger.info("Testcase--> "+testCaseId+ "->"+"  Validate GET "+insightNameKey +"  User Subscription ,"+testCaseDescription);
		automationTestResults.setInsights("PATCH_USERSUB_IncomeExpenseOverview");
		updateUserSubsctions(testCaseId ,testCaseDescription,insightNameKey,testNegativeScenario,triggerType,insightName,
				insightTitle,container,cobrandId,keys,ruleExpression,description,applicableEntity,
				entityParametersCount,configMeta,entityParameterName,totalThresholds,id,frequency,isSubscribed,duration,insightType,
				threshold_Name_Value_Type,expectedStatus,testEnabled);		
	}
	@Test(enabled = true, dataProvider = "feeder")
	@Source("\\TestData\\CSVFiles\\Insights\\BudgetProgress\\PatchUserSubscriptionBudgetProgress.csv")
	public void testPatchUserSubscriptionBudgetProgress(String testCaseId ,String testCaseDescription,String insightNameKey,String testNegativeScenario,String triggerType,String insightName,
			String insightTitle,String container,String cobrandId,String keys,String ruleExpression,String description,String applicableEntity,
			String entityParametersCount,String configMeta,String entityParameterName,String totalThresholds,String id,String frequency,String isSubscribed,String duration,String insightType,
			String threshold_Name_Value_Type,int expectedStatus,String testEnabled) throws ParseException, InterruptedException, JoseException,IOException {
		commontUtils.isTCEnabled(testEnabled, testCaseId);
		logger.info("Testcase--> "+testCaseId+ "->"+"  Validate GET "+insightNameKey +"  User Subscription ,"+testCaseDescription);
		automationTestResults.setInsights("PATCH_USERSUB_BudgetProgress");
		updateUserSubsctions(testCaseId ,testCaseDescription,insightNameKey,testNegativeScenario,triggerType,insightName,
				insightTitle,container,cobrandId,keys,ruleExpression,description,applicableEntity,
				entityParametersCount,configMeta,entityParameterName,totalThresholds,id,frequency,isSubscribed,duration,insightType,
				threshold_Name_Value_Type,expectedStatus,testEnabled);		
	}
	@Test(enabled = true, dataProvider = "feeder")
	@Source("\\TestData\\CSVFiles\\Insights\\RefundDeposit\\PatchUserSubscriptionRefundDeposit.csv")
	public void testPatchUserSubscriptionRefundDeposit(String testCaseId ,String testCaseDescription,String insightNameKey,String testNegativeScenario,String triggerType,String insightName,
			String insightTitle,String container,String cobrandId,String keys,String ruleExpression,String description,String applicableEntity,
			String entityParametersCount,String configMeta,String entityParameterName,String totalThresholds,String id,String frequency,String isSubscribed,String duration,String insightType,
			String threshold_Name_Value_Type,int expectedStatus,String testEnabled) throws ParseException, InterruptedException, JoseException,IOException {
		commontUtils.isTCEnabled(testEnabled, testCaseId);
		logger.info("Testcase--> "+testCaseId+ "->"+"  Validate GET "+insightNameKey +"  User Subscription ,"+testCaseDescription);
		automationTestResults.setInsights("PATCH_USERSUB_RefundDeposit");
		updateUserSubsctions(testCaseId ,testCaseDescription,insightNameKey,testNegativeScenario,triggerType,insightName,
				insightTitle,container,cobrandId,keys,ruleExpression,description,applicableEntity,
				entityParametersCount,configMeta,entityParameterName,totalThresholds,id,frequency,isSubscribed,duration,insightType,
				threshold_Name_Value_Type,expectedStatus,testEnabled);                      
	}
	@Test(enabled = true, dataProvider = "feeder")
	@Source("\\TestData\\CSVFiles\\Insights\\MostFrequentPurchases\\PatchUserSubscriptionMostFrequentPurchases.csv")
	public void testPatchUserSubscriptionMostFrequentPurchases(String testCaseId ,String testCaseDescription,String insightNameKey,String testNegativeScenario,String triggerType,String insightName,
			String insightTitle,String container,String cobrandId,String keys,String ruleExpression,String description,String applicableEntity,
			String entityParametersCount,String configMeta,String entityParameterName,String totalThresholds,String id,String frequency,String isSubscribed,String duration,String insightType,
			String threshold_Name_Value_Type,int expectedStatus,String testEnabled) throws ParseException, InterruptedException, JoseException,IOException {
		commontUtils.isTCEnabled(testEnabled, testCaseId);
		logger.info("Testcase--> "+testCaseId+ "->"+"  Validate GET "+insightNameKey +"  User Subscription ,"+testCaseDescription);
		automationTestResults.setInsights("PATCH_USERSUB_MostFrequentPurchases");
		updateUserSubsctions(testCaseId ,testCaseDescription,insightNameKey,testNegativeScenario,triggerType,insightName,
				insightTitle,container,cobrandId,keys,ruleExpression,description,applicableEntity,
				entityParametersCount,configMeta,entityParameterName,totalThresholds,id,frequency,isSubscribed,duration,insightType,
				threshold_Name_Value_Type,expectedStatus,testEnabled);                      
	}
	@Test(enabled = true, dataProvider = "feeder")
	@Source("\\TestData\\CSVFiles\\Insights\\ProjectedLowBalance\\PatchUserSubscriptionProjectedLowBalance.csv")
	public void testPatchUserSubscriptionProjectedLowBalance(String testCaseId ,String testCaseDescription,String insightNameKey,String testNegativeScenario,String triggerType,String insightName,
			String insightTitle,String container,String cobrandId,String keys,String ruleExpression,String description,String applicableEntity,
			String entityParametersCount,String configMeta,String entityParameterName,String totalThresholds,String id,String frequency,String isSubscribed,String duration,String insightType,
			String threshold_Name_Value_Type,int expectedStatus,String testEnabled) throws ParseException, InterruptedException, JoseException, IOException {

		commontUtils.isTCEnabled(testEnabled, testCaseId);
		logger.info("Testcase--> "+testCaseId+ "->"+"  Validate GET "+insightNameKey +"  User Subscription ,"+testCaseDescription);
		automationTestResults.setInsights("PATCH_USERSUB_ProjectedLowBalance");
		updateUserSubsctions(testCaseId ,testCaseDescription,insightNameKey,testNegativeScenario,triggerType,insightName,
				insightTitle,container,cobrandId,keys,ruleExpression,description,applicableEntity,
				entityParametersCount,configMeta,entityParameterName,totalThresholds,id,frequency,isSubscribed,duration,insightType,
				threshold_Name_Value_Type,expectedStatus,testEnabled);		
	}
	public void updateUserSubsctions(String testCaseId ,String testCaseDescription,String insightNameKey,String testNegativeScenario,String triggerType,String insightName,
			String insightTitle,String container,String cobrandId,String keys,String ruleExpression,String description,String applicableEntity,
			String entityParametersCount,String configMeta,String entityParameterName,String totalThresholds,String id,String frequency,String isSubscribed,String duration,String insightType,
			String threshold_Name_Value_Type,int expectedStatus,String testEnabled) throws IOException {
		boolean testExecutionStatus = false;
		automationTestResults.setBuildNo(buildNo);
		executedInsight=insightNameKey;
		storyId = testCaseId.substring(0,  7);
		TestInfo testInfo = new TestInfo();
		testInfo.setTestCaseId(testCaseId);
		testInfo.setTestDescription(testCaseDescription);
		HttpMethodParameters httpUserParams=insightsHelper.getHttpParams("userSubscriptionRequest", null, null, jwtUserAuthToken,null,null,null,null);
		patchUserSubscriptionBodyParam = insightsHelper.constructPatchUserSubscriptionRequest(insightNameKey, testNegativeScenario, insightName, insightTitle, container,
				cobrandId, keys, ruleExpression, description, applicableEntity,	entityParametersCount, configMeta,entityParameterName, totalThresholds,id,
				frequency, isSubscribed, duration, insightType,threshold_Name_Value_Type,triggerType);
		httpUserParams.setBodyParams(patchUserSubscriptionBodyParam);
		Response patchUserSubscriptionResponse = insightUtils.patchUserSubscriptions(httpUserParams,null);
		httpUserParams.setBodyParams(null);
		Response cobSubscriptionResponse = insightUtils.getCobrandSubscriptions(httpCobrandParams,null);
		//userQueryParam.put("name", insightNameKey);
		//httpUserParams.setQueryParams(userQueryParam);
		Response userSubscriptionResponse = insightUtils.getUserSubscription(httpUserParams,null);
		if(patchUserSubscriptionResponse.getStatusCode() == expectedStatus) {
			testExecutionStatus = true;
		}
		String testStatus = insightsGenerics.updateUserSubscriptionResponseVerificationPostPatch(testCaseId,testExecutionStatus,testSummary,testCaseDescription,negativeExpectedValuesMap,testNegativeScenario,patchUserSubscriptionResponse, cobSubscriptionResponse, userSubscriptionResponse, insightNameKey, frequency,duration, insightTitle, entityParameterName, isSubscribed, threshold_Name_Value_Type,expectedStatus,failureReason);
		notificationsSummary(Boolean.valueOf(testStatus),testInfo,testCaseId,insightName,testCaseDescription);
		if(!Boolean.valueOf(testStatus))
			Assert.fail("Test FAILED -->"+failureReason.getFailureReason());
	}
	@Test(enabled = true, dataProvider = "feeder")
	@Source("\\TestData\\CSVFiles\\Insights\\CategorySpendingExceeded\\PatchUserSubscriptionCategorySpendingExceeded.csv")
	public void testPatchUserSubscriptionCategorySpendingExceeded(String testCaseId ,String testCaseDescription,String insightNameKey,String testNegativeScenario,String triggerType,String insightName,
			String insightTitle,String container,String cobrandId,String keys,String ruleExpression,String description,String applicableEntity,
			String entityParametersCount,String configMeta,String entityParameterName,String totalThresholds,String id,String frequency,String isSubscribed,String duration,String insightType,
			String threshold_Name_Value_Type,int expectedStatus,String testEnabled) throws ParseException, InterruptedException, JoseException, IOException {

		commontUtils.isTCEnabled(testEnabled, testCaseId);
		logger.info("Testcase--> "+testCaseId+ "->"+"  Validate GET "+insightNameKey +"  User Subscription ,"+testCaseDescription);
		automationTestResults.setInsights("PATCH_USERSUB_CategorySpendingExceeded");
		updateUserSubsctions(testCaseId ,testCaseDescription,insightNameKey,testNegativeScenario,triggerType,insightName,
				insightTitle,container,cobrandId,keys,ruleExpression,description,applicableEntity,
				entityParametersCount,configMeta,entityParameterName,totalThresholds,id,frequency,isSubscribed,duration,insightType,
				threshold_Name_Value_Type,expectedStatus,testEnabled);		
	}
	@Test(enabled = true, dataProvider = "feeder")
	@Source("\\TestData\\CSVFiles\\Insights\\ProjectedInsufficientBalance\\PatchUserSubscriptionProjectedInsufficientBalance.csv")
	public void testPatchUserSubscriptionProjectedInsufficientBalance(String testCaseId ,String testCaseDescription,String insightNameKey,String testNegativeScenario,String triggerType,String insightName,
			String insightTitle,String container,String cobrandId,String keys,String ruleExpression,String description,String applicableEntity,
			String entityParametersCount,String configMeta,String entityParameterName,String totalThresholds,String id,String frequency,String isSubscribed,String duration,String insightType,
			String threshold_Name_Value_Type,int expectedStatus,String testEnabled) throws ParseException, InterruptedException, JoseException,IOException {

		commontUtils.isTCEnabled(testEnabled, testCaseId);
		logger.info("Testcase--> "+testCaseId+ "->"+"  Validate GET "+insightNameKey +"  User Subscription ,"+testCaseDescription);
		automationTestResults.setInsights("PATCH_USERSUB_ProjectedInsufficientBalance");
		updateUserSubsctions(testCaseId ,testCaseDescription,insightNameKey,testNegativeScenario,triggerType,insightName,
				insightTitle,container,cobrandId,keys,ruleExpression,description,applicableEntity,
				entityParametersCount,configMeta,entityParameterName,totalThresholds,id,frequency,isSubscribed,duration,insightType,
				threshold_Name_Value_Type,expectedStatus,testEnabled);
	}

	@Test(enabled = true, dataProvider = "feeder")
	@Source("\\TestData\\CSVFiles\\Insights\\LargePurchase\\PatchUserSubscriptionLargePurchase.csv")
	public void testPatchUserSubscriptionLargePurchase(String testCaseId ,String testCaseDescription,String insightNameKey,String testNegativeScenario,String triggerType,String insightName,
			String insightTitle,String container,String cobrandId,String keys,String ruleExpression,String description,String applicableEntity,
			String entityParametersCount,String configMeta,String entityParameterName,String totalThresholds,String id,String frequency,String isSubscribed,String duration,String insightType,
			String threshold_Name_Value_Type,int expectedStatus,String testEnabled) throws ParseException, InterruptedException, JoseException,IOException {
		commontUtils.isTCEnabled(testEnabled, testCaseId);
		logger.info("Testcase--> "+testCaseId+ "->"+"  Validate GET "+insightNameKey +"  User Subscription ,"+testCaseDescription);
		automationTestResults.setInsights("PATCH_USERSUB_LargePurchase");
		updateUserSubsctions(testCaseId ,testCaseDescription,insightNameKey,testNegativeScenario,triggerType,insightName,
				insightTitle,container,cobrandId,keys,ruleExpression,description,applicableEntity,
				entityParametersCount,configMeta,entityParameterName,totalThresholds,id,frequency,isSubscribed,duration,insightType,
				threshold_Name_Value_Type,expectedStatus,testEnabled);                      
	}
	
	@Test(enabled = true, dataProvider = "feeder")
	@Source("\\TestData\\CSVFiles\\Insights\\CardStatementAvailable\\PatchUserSubscriptionCardStatementAvailable.csv")
	public void testPatchUserSubscriptionCardStatementAvailable(String testCaseId ,String testCaseDescription,String insightNameKey,String testNegativeScenario,String triggerType,String insightName,
			String insightTitle,String container,String cobrandId,String keys,String ruleExpression,String description,String applicableEntity,
			String entityParametersCount,String configMeta,String entityParameterName,String totalThresholds,String id,String frequency,String isSubscribed,String duration,String insightType,
			String threshold_Name_Value_Type,int expectedStatus,String testEnabled) throws ParseException, InterruptedException, JoseException,IOException {
		commontUtils.isTCEnabled(testEnabled, testCaseId);
		logger.info("Testcase--> "+testCaseId+ "->"+"  Validate GET "+insightNameKey +"  User Subscription ,"+testCaseDescription);
		automationTestResults.setInsights("PATCH_USERSUB_CardStatementAvailable");
		updateUserSubsctions(testCaseId ,testCaseDescription,insightNameKey,testNegativeScenario,triggerType,insightName,
				insightTitle,container,cobrandId,keys,ruleExpression,description,applicableEntity,
				entityParametersCount,configMeta,entityParameterName,totalThresholds,id,frequency,isSubscribed,duration,insightType,
				threshold_Name_Value_Type,expectedStatus,testEnabled);                      
	}
	
	@Test(enabled = true, dataProvider = "feeder")
	@Source("\\TestData\\CSVFiles\\Insights\\PropertyValueChange\\PatchUserSubscriptionPropertyValueChange.csv")
	public void testPatchUserSubscriptionPropertyValueChange(String testCaseId ,String testCaseDescription,String insightNameKey,String testNegativeScenario,String triggerType,String insightName,
			String insightTitle,String container,String cobrandId,String keys,String ruleExpression,String description,String applicableEntity,
			String entityParametersCount,String configMeta,String entityParameterName,String totalThresholds,String id,String frequency,String isSubscribed,String duration,String insightType,
			String threshold_Name_Value_Type,int expectedStatus,String testEnabled) throws ParseException, InterruptedException, JoseException, IOException {

		commontUtils.isTCEnabled(testEnabled, testCaseId);
		logger.info("Testcase--> "+testCaseId+ "->"+"  Validate GET "+insightNameKey +"  User Subscription ,"+testCaseDescription);
		automationTestResults.setInsights("PATCH_USERSUB_PropertyValueChange");
		updateUserSubsctions(testCaseId ,testCaseDescription,insightNameKey,testNegativeScenario,triggerType,insightName,
				insightTitle,container,cobrandId,keys,ruleExpression,description,applicableEntity,
				entityParametersCount,configMeta,entityParameterName,totalThresholds,id,frequency,isSubscribed,duration,insightType,
				threshold_Name_Value_Type,expectedStatus,testEnabled);
	}
	
	@Test(enabled = true, dataProvider = "feeder")
	@Source("\\TestData\\CSVFiles\\Insights\\BillPaid\\PatchUserSubscriptionBillPaid.csv")
	public void testPatchUserSubscriptionBillPaid(String testCaseId ,String testCaseDescription,String insightNameKey,String testNegativeScenario,String triggerType,String insightName,
			String insightTitle,String container,String cobrandId,String keys,String ruleExpression,String description,String applicableEntity,
			String entityParametersCount,String configMeta,String entityParameterName,String totalThresholds,String id,String frequency,String isSubscribed,String duration,String insightType,
			String threshold_Name_Value_Type,int expectedStatus,String testEnabled) throws ParseException, InterruptedException, JoseException,IOException {

		commontUtils.isTCEnabled(testEnabled, testCaseId);
		logger.info("Testcase--> "+testCaseId+ "->"+"  Validate GET "+insightNameKey +"  User Subscription ,"+testCaseDescription);
		automationTestResults.setInsights("PATCH_USERSUB_BillPaid");
		updateUserSubsctions(testCaseId ,testCaseDescription,insightNameKey,testNegativeScenario,triggerType,insightName,
				insightTitle,container,cobrandId,keys,ruleExpression,description,applicableEntity,
				entityParametersCount,configMeta,entityParameterName,totalThresholds,id,frequency,isSubscribed,duration,insightType,
				threshold_Name_Value_Type,expectedStatus,testEnabled);
	}
	@Test(enabled = true, dataProvider = "feeder")
	@Source("\\TestData\\CSVFiles\\Insights\\SubscriptionPaid\\PatchUserSubscriptionSubscriptionPaid.csv")
	public void testPatchUserSubscriptionSubscriptionPaid(String testCaseId ,String testCaseDescription,String insightNameKey,String testNegativeScenario,String triggerType,String insightName,
			String insightTitle,String container,String cobrandId,String keys,String ruleExpression,String description,String applicableEntity,
			String entityParametersCount,String configMeta,String entityParameterName,String totalThresholds,String id,String frequency,String isSubscribed,String duration,String insightType,
			String threshold_Name_Value_Type,int expectedStatus,String testEnabled) throws ParseException, InterruptedException, JoseException,IOException {

		commontUtils.isTCEnabled(testEnabled, testCaseId);
		logger.info("Testcase--> "+testCaseId+ "->"+"  Validate GET "+insightNameKey +"  User Subscription ,"+testCaseDescription);
		automationTestResults.setInsights("PATCH_USERSUB_SubscriptionPaid");
		updateUserSubsctions(testCaseId ,testCaseDescription,insightNameKey,testNegativeScenario,triggerType,insightName,
				insightTitle,container,cobrandId,keys,ruleExpression,description,applicableEntity,
				entityParametersCount,configMeta,entityParameterName,totalThresholds,id,frequency,isSubscribed,duration,insightType,
				threshold_Name_Value_Type,expectedStatus,testEnabled);
	}
	
	@Test(enabled = true, dataProvider = "feeder")
	@Source("\\TestData\\CSVFiles\\Insights\\BillChanged\\PatchUserSubscriptionBillChanged.csv")
	public void testPatchUserSubscriptionBillChanged(String testCaseId, String testCaseDescription,
			String insightNameKey, String testNegativeScenario, String triggerType, String insightName,
			String insightTitle, String container, String cobrandId, String keys, String ruleExpression,
			String description, String applicableEntity, String entityParametersCount, String configMeta,
			String entityParameterName, String totalThresholds, String id, String frequency, String isSubscribed,
			String duration, String insightType, String threshold_Name_Value_Type, int expectedStatus,
			String testEnabled) throws ParseException, InterruptedException, JoseException, IOException {

		commontUtils.isTCEnabled(testEnabled, testCaseId);
		logger.info("Testcase--> " + testCaseId + "->" + "  Validate GET " + insightNameKey + "  User Subscription ,"
				+ testCaseDescription);
		automationTestResults.setInsights("PATCH_USERSUB_BillChanged");
		updateUserSubsctions(testCaseId, testCaseDescription, insightNameKey, testNegativeScenario, triggerType,
				insightName, insightTitle, container, cobrandId, keys, ruleExpression, description, applicableEntity,
				entityParametersCount, configMeta, entityParameterName, totalThresholds, id, frequency, isSubscribed,
				duration, insightType, threshold_Name_Value_Type, expectedStatus, testEnabled);
	}

	@Test(enabled = true, dataProvider = "feeder")
	@Source("\\TestData\\CSVFiles\\Insights\\SubscriptionChanged\\PatchUserSubscriptionSubscriptionChanged.csv")
	public void testPatchUserSubscriptionSubscriptionChanged(String testCaseId, String testCaseDescription,
			String insightNameKey, String testNegativeScenario, String triggerType, String insightName,
			String insightTitle, String container, String cobrandId, String keys, String ruleExpression,
			String description, String applicableEntity, String entityParametersCount, String configMeta,
			String entityParameterName, String totalThresholds, String id, String frequency, String isSubscribed,
			String duration, String insightType, String threshold_Name_Value_Type, int expectedStatus,
			String testEnabled) throws ParseException, InterruptedException, JoseException, IOException {

		commontUtils.isTCEnabled(testEnabled, testCaseId);
		logger.info("Testcase--> " + testCaseId + "->" + "  Validate GET " + insightNameKey + "  User Subscription ,"
				+ testCaseDescription);
		automationTestResults.setInsights("PATCH_USERSUB_SubscriptionChanged");
		updateUserSubsctions(testCaseId, testCaseDescription, insightNameKey, testNegativeScenario, triggerType,
				insightName, insightTitle, container, cobrandId, keys, ruleExpression, description, applicableEntity,
				entityParametersCount, configMeta, entityParameterName, totalThresholds, id, frequency, isSubscribed,
				duration, insightType, threshold_Name_Value_Type, expectedStatus, testEnabled);
	}
	
	@Test(enabled = true, dataProvider = "feeder")
	@Source("\\TestData\\CSVFiles\\Insights\\BalanceAboveThreshold\\PatchUserSubscriptionBalanceAboveThreshold.csv")
	public void testPatchUserSubscriptionBalanceAboveThreshold(String testCaseId ,String testCaseDescription,String insightNameKey,String testNegativeScenario,String triggerType,String insightName,
			String insightTitle,String container,String cobrandId,String keys,String ruleExpression,String description,String applicableEntity,
			String entityParametersCount,String configMeta,String entityParameterName,String totalThresholds,String id,String frequency,String isSubscribed,String duration,String insightType,
			String threshold_Name_Value_Type,int expectedStatus,String testEnabled) throws ParseException, InterruptedException, JoseException, IOException {


		commontUtils.isTCEnabled(testEnabled, testCaseId);
		logger.info("Testcase--> "+testCaseId+ "->"+"  Validate GET "+insightNameKey +"  User Subscription ,"+testCaseDescription);
		automationTestResults.setInsights("PATCH_USERSUB_BalanceAboveThreshold");
		updateUserSubsctions(testCaseId ,testCaseDescription,insightNameKey,testNegativeScenario,triggerType,insightName,
				insightTitle,container,cobrandId,keys,ruleExpression,description,applicableEntity,
				entityParametersCount,configMeta,entityParameterName,totalThresholds,id,frequency,isSubscribed,duration,insightType,
				threshold_Name_Value_Type,expectedStatus,testEnabled);
	}
	
	@Test(enabled = true, dataProvider = "feeder")
	@Source("\\TestData\\CSVFiles\\Insights\\SalaryChanged\\PatchUserSubscriptionSalaryChanged.csv")
	public void testPatchUserSubscriptionSalaryChanged(String testCaseId, String testCaseDescription,
			String insightNameKey, String testNegativeScenario, String triggerType, String insightName,
			String insightTitle, String container, String cobrandId, String keys, String ruleExpression,
			String description, String applicableEntity, String entityParametersCount, String configMeta,
			String entityParameterName, String totalThresholds, String id, String frequency, String isSubscribed,
			String duration, String insightType, String threshold_Name_Value_Type, int expectedStatus,
			String testEnabled) throws ParseException, InterruptedException, JoseException, IOException {

		commontUtils.isTCEnabled(testEnabled, testCaseId);
		logger.info("Testcase--> " + testCaseId + "->" + "  Validate GET " + insightNameKey + "  User Subscription ,"
				+ testCaseDescription);
		automationTestResults.setInsights("PATCH_USERSUB_SalaryChanged");
		updateUserSubsctions(testCaseId, testCaseDescription, insightNameKey, testNegativeScenario, triggerType,
				insightName, insightTitle, container, cobrandId, keys, ruleExpression, description, applicableEntity,
				entityParametersCount, configMeta, entityParameterName, totalThresholds, id, frequency, isSubscribed,
				duration, insightType, threshold_Name_Value_Type, expectedStatus, testEnabled);
	}

	@Test(enabled = true, dataProvider = "feeder")
	@Source("\\TestData\\CSVFiles\\Insights\\RecurringDepositLate\\PatchUserSubscriptionRecurringDepositLate.csv")
	public void testPatchUserSubscriptionRecurringDepositLate(String testCaseId ,String testCaseDescription,String insightNameKey,String testNegativeScenario,String triggerType,String insightName,
			String insightTitle,String container,String cobrandId,String keys,String ruleExpression,String description,String applicableEntity,
			String entityParametersCount,String configMeta,String entityParameterName,String totalThresholds,String id,String frequency,String isSubscribed,String duration,String insightType,
			String threshold_Name_Value_Type,int expectedStatus,String testEnabled) throws ParseException, InterruptedException, JoseException,IOException {

		commontUtils.isTCEnabled(testEnabled, testCaseId);
		logger.info("Testcase--> "+testCaseId+ "->"+"  Validate GET "+insightNameKey +"  User Subscription ,"+testCaseDescription);
		automationTestResults.setInsights("RecurringDepositLate");
		updateUserSubsctions(testCaseId ,testCaseDescription,insightNameKey,testNegativeScenario,triggerType,insightName,
				insightTitle,container,cobrandId,keys,ruleExpression,description,applicableEntity,
				entityParametersCount,configMeta,entityParameterName,totalThresholds,id,frequency,isSubscribed,duration,insightType,
				threshold_Name_Value_Type,expectedStatus,testEnabled);
	}
	
	@Test(enabled = true, dataProvider = "feeder")
	@Source("\\TestData\\CSVFiles\\Insights\\LoanMaturityNotice\\PatchUserSubscriptionLoanMaturityNotice.csv")
	public void testPatchUserSubscriptionLoanMaturityNotice(String testCaseId ,String testCaseDescription,String insightNameKey,String testNegativeScenario,String triggerType,String insightName,
			String insightTitle,String container,String cobrandId,String keys,String ruleExpression,String description,String applicableEntity,
			String entityParametersCount,String configMeta,String entityParameterName,String totalThresholds,String id,String frequency,String isSubscribed,String duration,String insightType,
			String threshold_Name_Value_Type,int expectedStatus,String testEnabled) throws ParseException, InterruptedException, JoseException,IOException {

		commontUtils.isTCEnabled(testEnabled, testCaseId);
		logger.info("Testcase--> "+testCaseId+ "->"+"  Validate GET "+insightNameKey +"  User Subscription ,"+testCaseDescription);
		automationTestResults.setInsights("LoanMaturityNotice");
		updateUserSubsctions(testCaseId ,testCaseDescription,insightNameKey,testNegativeScenario,triggerType,insightName,
				insightTitle,container,cobrandId,keys,ruleExpression,description,applicableEntity,
				entityParametersCount,configMeta,entityParameterName,totalThresholds,id,frequency,isSubscribed,duration,insightType,
				threshold_Name_Value_Type,expectedStatus,testEnabled);
	}
	
	@Test(enabled = true, dataProvider = "feeder")
	@Source("\\TestData\\CSVFiles\\Insights\\InvestmentMaturityNotice\\PatchUserSubscriptionInvestmentMaturityNotice.csv")
	public void testPatchUserSubscriptionInvestmentMaturityNotice(String testCaseId ,String testCaseDescription,String insightNameKey,String testNegativeScenario,String triggerType,String insightName,
			String insightTitle,String container,String cobrandId,String keys,String ruleExpression,String description,String applicableEntity,
			String entityParametersCount,String configMeta,String entityParameterName,String totalThresholds,String id,String frequency,String isSubscribed,String duration,String insightType,
			String threshold_Name_Value_Type,int expectedStatus,String testEnabled) throws ParseException, InterruptedException, JoseException,IOException {

		commontUtils.isTCEnabled(testEnabled, testCaseId);
		logger.info("Testcase--> "+testCaseId+ "->"+"  Validate GET "+insightNameKey +"  User Subscription ,"+testCaseDescription);
		automationTestResults.setInsights("InvestmentMaturityNotice");
		updateUserSubsctions(testCaseId ,testCaseDescription,insightNameKey,testNegativeScenario,triggerType,insightName,
				insightTitle,container,cobrandId,keys,ruleExpression,description,applicableEntity,
				entityParametersCount,configMeta,entityParameterName,totalThresholds,id,frequency,isSubscribed,duration,insightType,
				threshold_Name_Value_Type,expectedStatus,testEnabled);
	}
	
	@Test(enabled = true, dataProvider = "feeder")
	@Source("\\TestData\\CSVFiles\\Insights\\IncomeThreshold\\PatchUserSubscriptionIncomeThreshold.csv")
	public void testPatchUserSubscriptionIncomeThreshold(String testCaseId ,String testCaseDescription,String insightNameKey,String testNegativeScenario,String triggerType,String insightName,
			String insightTitle,String container,String cobrandId,String keys,String ruleExpression,String description,String applicableEntity,
			String entityParametersCount,String configMeta,String entityParameterName,String totalThresholds,String id,String frequency,String isSubscribed,String duration,String insightType,
			String threshold_Name_Value_Type,int expectedStatus,String testEnabled) throws ParseException, InterruptedException, JoseException,IOException {
		commontUtils.isTCEnabled(testEnabled, testCaseId);
		logger.info("Testcase--> "+testCaseId+ "->"+"  Validate GET "+insightNameKey +"  User Subscription ,"+testCaseDescription);
		automationTestResults.setInsights("IncomeThreshold");
		updateUserSubsctions(testCaseId ,testCaseDescription,insightNameKey,testNegativeScenario,triggerType,insightName,
				insightTitle,container,cobrandId,keys,ruleExpression,description,applicableEntity,
				entityParametersCount,configMeta,entityParameterName,totalThresholds,id,frequency,isSubscribed,duration,insightType,
				threshold_Name_Value_Type,expectedStatus,testEnabled);                      
	}
	
	@Test(enabled = true, dataProvider = "feeder")
	@Source("\\TestData\\CSVFiles\\Insights\\ExpensesExceededBudget\\PatchUserSubsExpExceededBudget.csv")
	public void testPatchUserSubscriptionExpensesExceededBudget(String testCaseId ,String testCaseDescription,String insightNameKey,String testNegativeScenario,String triggerType,String insightName,
			String insightTitle,String container,String cobrandId,String keys,String ruleExpression,String description,String applicableEntity,
			String entityParametersCount,String configMeta,String entityParameterName,String totalThresholds,String id,String frequency,String isSubscribed,String duration,String insightType,
			String threshold_Name_Value_Type,int expectedStatus,String testEnabled) throws ParseException, InterruptedException, JoseException,IOException {
		commontUtils.isTCEnabled(testEnabled, testCaseId);
		logger.info("Testcase--> "+testCaseId+ "->"+"  Validate GET "+insightNameKey +"  User Subscription ,"+testCaseDescription);
		automationTestResults.setInsights("ExpensesExceededBudget");
		updateUserSubsctions(testCaseId ,testCaseDescription,insightNameKey,testNegativeScenario,triggerType,insightName,
				insightTitle,container,cobrandId,keys,ruleExpression,description,applicableEntity,
				entityParametersCount,configMeta,entityParameterName,totalThresholds,id,frequency,isSubscribed,duration,insightType,
				threshold_Name_Value_Type,expectedStatus,testEnabled);                      
	}
	
	@Test(enabled = true, dataProvider = "feeder")
	@Source("\\TestData\\CSVFiles\\Insights\\SpendingEarningComparison\\PatchUserSubsSpendingEarning.csv")
	public void testPatchUserSubscriptionSpendingEarningComparison(String testCaseId ,String testCaseDescription,String insightNameKey,String testNegativeScenario,String triggerType,String insightName,
			String insightTitle,String container,String cobrandId,String keys,String ruleExpression,String description,String applicableEntity,
			String entityParametersCount,String configMeta,String entityParameterName,String totalThresholds,String id,String frequency,String isSubscribed,String duration,String insightType,
			String threshold_Name_Value_Type,int expectedStatus,String testEnabled) throws ParseException, InterruptedException, JoseException,IOException {
		commontUtils.isTCEnabled(testEnabled, testCaseId);
		logger.info("Testcase--> "+testCaseId+ "->"+"  Validate GET "+insightNameKey +"  User Subscription ,"+testCaseDescription);
		automationTestResults.setInsights("SpendingEarningComparison");
		updateUserSubsctions(testCaseId ,testCaseDescription,insightNameKey,testNegativeScenario,triggerType,insightName,
				insightTitle,container,cobrandId,keys,ruleExpression,description,applicableEntity,
				entityParametersCount,configMeta,entityParameterName,totalThresholds,id,frequency,isSubscribed,duration,insightType,
				threshold_Name_Value_Type,expectedStatus,testEnabled);                      
	}
	
	@Test(enabled = true, dataProvider = "feeder")
	@Source("\\TestData\\CSVFiles\\Insights\\EmergencySavings\\PatchUserSubsEmergencySavings.csv")
	public void testPatchUserSubscriptionEmergencySavings(String testCaseId ,String testCaseDescription,String insightNameKey,String testNegativeScenario,String triggerType,String insightName,
			String insightTitle,String container,String cobrandId,String keys,String ruleExpression,String description,String applicableEntity,
			String entityParametersCount,String configMeta,String entityParameterName,String totalThresholds,String id,String frequency,String isSubscribed,String duration,String insightType,
			String threshold_Name_Value_Type,int expectedStatus,String testEnabled) throws ParseException, InterruptedException, JoseException,IOException {
		commontUtils.isTCEnabled(testEnabled, testCaseId);
		logger.info("Testcase--> "+testCaseId+ "->"+"  Validate GET "+insightNameKey +"  User Subscription ,"+testCaseDescription);
		automationTestResults.setInsights("EmergencySavings");
		updateUserSubsctions(testCaseId ,testCaseDescription,insightNameKey,testNegativeScenario,triggerType,insightName,
				insightTitle,container,cobrandId,keys,ruleExpression,description,applicableEntity,
				entityParametersCount,configMeta,entityParameterName,totalThresholds,id,frequency,isSubscribed,duration,insightType,
				threshold_Name_Value_Type,expectedStatus,testEnabled);                      
	}
	
	@Test(enabled = true, dataProvider = "feeder")
	@Source("\\TestData\\CSVFiles\\Insights\\SpendingByCategoryConsumerLevel\\PatchUserSubsSpeCatAllAcct.csv")
	public void testPatchUserSubscriptionSpendingByCategoryAllAccounts(String testCaseId ,String testCaseDescription,String insightNameKey,String testNegativeScenario,String triggerType,String insightName,
			String insightTitle,String container,String cobrandId,String keys,String ruleExpression,String description,String applicableEntity,
			String entityParametersCount,String configMeta,String entityParameterName,String totalThresholds,String id,String frequency,String isSubscribed,String duration,String insightType,
			String threshold_Name_Value_Type,int expectedStatus,String testEnabled) throws ParseException, InterruptedException, JoseException,IOException {
		commontUtils.isTCEnabled(testEnabled, testCaseId);
		logger.info("Testcase--> "+testCaseId+ "->"+"  Validate GET "+insightNameKey +"  User Subscription ,"+testCaseDescription);
		automationTestResults.setInsights("SpendingByCategoryAllAccounts");
		updateUserSubsctions(testCaseId ,testCaseDescription,insightNameKey,testNegativeScenario,triggerType,insightName,
				insightTitle,container,cobrandId,keys,ruleExpression,description,applicableEntity,
				entityParametersCount,configMeta,entityParameterName,totalThresholds,id,frequency,isSubscribed,duration,insightType,
				threshold_Name_Value_Type,expectedStatus,testEnabled);                      
	}
	
	@Test(enabled = true, dataProvider = "feeder")
	@Source("\\TestData\\CSVFiles\\Insights\\SpendingByMerchantsConsumerLevel\\PatchUserSubsSpeMerAllAcc.csv")
	public void testPatchUserSubscriptionSpendingByMerchantsAllAccounts(String testCaseId ,String testCaseDescription,String insightNameKey,String testNegativeScenario,String triggerType,String insightName,
			String insightTitle,String container,String cobrandId,String keys,String ruleExpression,String description,String applicableEntity,
			String entityParametersCount,String configMeta,String entityParameterName,String totalThresholds,String id,String frequency,String isSubscribed,String duration,String insightType,
			String threshold_Name_Value_Type,int expectedStatus,String testEnabled) throws ParseException, InterruptedException, JoseException,IOException {
		commontUtils.isTCEnabled(testEnabled, testCaseId);
		logger.info("Testcase--> "+testCaseId+ "->"+"  Validate GET "+insightNameKey +"  User Subscription ,"+testCaseDescription);
		automationTestResults.setInsights("SpendingByMerchantsAllAccounts");
		updateUserSubsctions(testCaseId ,testCaseDescription,insightNameKey,testNegativeScenario,triggerType,insightName,
				insightTitle,container,cobrandId,keys,ruleExpression,description,applicableEntity,
				entityParametersCount,configMeta,entityParameterName,totalThresholds,id,frequency,isSubscribed,duration,insightType,
				threshold_Name_Value_Type,expectedStatus,testEnabled);                      
	}
	
	@Test(enabled = true, dataProvider = "feeder")
	@Source("\\TestData\\CSVFiles\\Insights\\AggregateCreditCardAccount\\PatchUserSubscriptionAggregateCreditCardAccount.csv")
	public void testPatchUserSubscriptionAggregateCreditCardAccount(String testCaseId ,String testCaseDescription,String insightNameKey,String testNegativeScenario,String triggerType,String insightName,
			String insightTitle,String container,String cobrandId,String keys,String ruleExpression,String description,String applicableEntity,
			String entityParametersCount,String configMeta,String entityParameterName,String totalThresholds,String id,String frequency,String isSubscribed,String duration,String insightType,
			String threshold_Name_Value_Type,int expectedStatus,String testEnabled) throws ParseException, InterruptedException, JoseException,IOException {
		commontUtils.isTCEnabled(testEnabled, testCaseId);
		logger.info("Testcase--> "+testCaseId+ "->"+"  Validate GET "+insightNameKey +"  User Subscription ,"+testCaseDescription);
		automationTestResults.setInsights("EmergencySavings");
		updateUserSubsctions(testCaseId ,testCaseDescription,insightNameKey,testNegativeScenario,triggerType,insightName,
				insightTitle,container,cobrandId,keys,ruleExpression,description,applicableEntity,
				entityParametersCount,configMeta,entityParameterName,totalThresholds,id,frequency,isSubscribed,duration,insightType,
				threshold_Name_Value_Type,expectedStatus,testEnabled);                      
	}
	
	@Test(enabled = true, dataProvider = "feeder")
	@Source("\\TestData\\CSVFiles\\Insights\\AggregateInsuranceAccount\\PatchUserSubscriptionAggregateInsuranceAccount.csv")
	public void testPatchUserSubscriptionAggregateInsuranceAccount(String testCaseId ,String testCaseDescription,String insightNameKey,String testNegativeScenario,String triggerType,String insightName,
			String insightTitle,String container,String cobrandId,String keys,String ruleExpression,String description,String applicableEntity,
			String entityParametersCount,String configMeta,String entityParameterName,String totalThresholds,String id,String frequency,String isSubscribed,String duration,String insightType,
			String threshold_Name_Value_Type,int expectedStatus,String testEnabled) throws ParseException, InterruptedException, JoseException,IOException {
		commontUtils.isTCEnabled(testEnabled, testCaseId);
		logger.info("Testcase--> "+testCaseId+ "->"+"  Validate GET "+insightNameKey +"  User Subscription ,"+testCaseDescription);
		automationTestResults.setInsights("AggregateInsuranceAccount");
		updateUserSubsctions(testCaseId ,testCaseDescription,insightNameKey,testNegativeScenario,triggerType,insightName,
				insightTitle,container,cobrandId,keys,ruleExpression,description,applicableEntity,
				entityParametersCount,configMeta,entityParameterName,totalThresholds,id,frequency,isSubscribed,duration,insightType,
				threshold_Name_Value_Type,expectedStatus,testEnabled);                      
	}
	
	@Test(enabled = true, dataProvider = "feeder")
	@Source("\\TestData\\CSVFiles\\Insights\\AggregateMortgageAccount\\PatchUserSubscriptionAggregateMortgageAccount.csv")
	public void testPatchUserSubscriptionAggregateMortgageAccount(String testCaseId ,String testCaseDescription,String insightNameKey,String testNegativeScenario,String triggerType,String insightName,
			String insightTitle,String container,String cobrandId,String keys,String ruleExpression,String description,String applicableEntity,
			String entityParametersCount,String configMeta,String entityParameterName,String totalThresholds,String id,String frequency,String isSubscribed,String duration,String insightType,
			String threshold_Name_Value_Type,int expectedStatus,String testEnabled) throws ParseException, InterruptedException, JoseException,IOException {
		commontUtils.isTCEnabled(testEnabled, testCaseId);
		logger.info("Testcase--> "+testCaseId+ "->"+"  Validate GET "+insightNameKey +"  User Subscription ,"+testCaseDescription);
		automationTestResults.setInsights("AggregateMortgageAccount");
		updateUserSubsctions(testCaseId ,testCaseDescription,insightNameKey,testNegativeScenario,triggerType,insightName,
				insightTitle,container,cobrandId,keys,ruleExpression,description,applicableEntity,
				entityParametersCount,configMeta,entityParameterName,totalThresholds,id,frequency,isSubscribed,duration,insightType,
				threshold_Name_Value_Type,expectedStatus,testEnabled);                      
	}
	@Test(enabled = true, dataProvider = "feeder")
	@Source("\\TestData\\CSVFiles\\Insights\\AggregateRealEstateAccount\\PatchUserSubscriptionAggregateRealEstateAccount.csv")
	public void testPatchUserSubscriptionAggregateRealEstateAccount(String testCaseId ,String testCaseDescription,String insightNameKey,String testNegativeScenario,String triggerType,String insightName,
			String insightTitle,String container,String cobrandId,String keys,String ruleExpression,String description,String applicableEntity,
			String entityParametersCount,String configMeta,String entityParameterName,String totalThresholds,String id,String frequency,String isSubscribed,String duration,String insightType,
			String threshold_Name_Value_Type,int expectedStatus,String testEnabled) throws ParseException, InterruptedException, JoseException,IOException {
		commontUtils.isTCEnabled(testEnabled, testCaseId);
		logger.info("Testcase--> "+testCaseId+ "->"+"  Validate GET "+insightNameKey +"  User Subscription ,"+testCaseDescription);
		automationTestResults.setInsights("AggregateRealEstateAccount");
		updateUserSubsctions(testCaseId ,testCaseDescription,insightNameKey,testNegativeScenario,triggerType,insightName,
				insightTitle,container,cobrandId,keys,ruleExpression,description,applicableEntity,
				entityParametersCount,configMeta,entityParameterName,totalThresholds,id,frequency,isSubscribed,duration,insightType,
				threshold_Name_Value_Type,expectedStatus,testEnabled);                      
	}

	@Test(enabled = true, dataProvider = "feeder")
	@Source("\\TestData\\CSVFiles\\Insights\\ZeroCardInterestPaid\\PatchUserSubscriptionZeroCardInterestPaid.csv")
	public void testPatchUserSubscriptionZeroCardInterestPaid(String testCaseId ,String testCaseDescription,String insightNameKey,String testNegativeScenario,String triggerType,String insightName,
			String insightTitle,String container,String cobrandId,String keys,String ruleExpression,String description,String applicableEntity,
			String entityParametersCount,String configMeta,String entityParameterName,String totalThresholds,String id,String frequency,String isSubscribed,String duration,String insightType,
			String threshold_Name_Value_Type,int expectedStatus,String testEnabled) throws ParseException, InterruptedException, JoseException,IOException {

		commontUtils.isTCEnabled(testEnabled, testCaseId);
		logger.info("Testcase--> "+testCaseId+ "->"+"  Validate GET "+insightNameKey +"  User Subscription ,"+testCaseDescription);
		automationTestResults.setInsights("ZeroCardInterestPaid");
		updateUserSubsctions(testCaseId ,testCaseDescription,insightNameKey,testNegativeScenario,triggerType,insightName,
				insightTitle,container,cobrandId,keys,ruleExpression,description,applicableEntity,
				entityParametersCount,configMeta,entityParameterName,totalThresholds,id,frequency,isSubscribed,duration,insightType,
				threshold_Name_Value_Type,expectedStatus,testEnabled);
	}

	@Test(enabled = true, dataProvider = "feeder")
	@Source("\\TestData\\CSVFiles\\Insights\\NewSalary\\PatchUserSubscriptionNewSalary.csv")
	public void testPatchUserSubscriptionNewSalary(String testCaseId ,String testCaseDescription,String insightNameKey,String testNegativeScenario,String triggerType,String insightName,
			String insightTitle,String container,String cobrandId,String keys,String ruleExpression,String description,String applicableEntity,
			String entityParametersCount,String configMeta,String entityParameterName,String totalThresholds,String id,String frequency,String isSubscribed,String duration,String insightType,
			String threshold_Name_Value_Type,int expectedStatus,String testEnabled) throws ParseException, InterruptedException, JoseException,IOException {

		commontUtils.isTCEnabled(testEnabled, testCaseId);
		logger.info("Testcase--> "+testCaseId+ "->"+"  Validate GET "+insightNameKey +"  User Subscription ,"+testCaseDescription);
		automationTestResults.setInsights("NewSalary");
		updateUserSubsctions(testCaseId ,testCaseDescription,insightNameKey,testNegativeScenario,triggerType,insightName,
				insightTitle,container,cobrandId,keys,ruleExpression,description,applicableEntity,
				entityParametersCount,configMeta,entityParameterName,totalThresholds,id,frequency,isSubscribed,duration,insightType,
				threshold_Name_Value_Type,expectedStatus,testEnabled);
	}
	
	@Test(enabled = true, dataProvider = "feeder")
	@Source("\\TestData\\CSVFiles\\Insights\\SubscriptionNotPaid\\PatchUserSubscriptionSubscriptionNotPaid.csv")
	public void testPatchUserSubscriptionSubscriptionNotPaid(String testCaseId ,String testCaseDescription,String insightNameKey,String testNegativeScenario,String triggerType,String insightName,
			String insightTitle,String container,String cobrandId,String keys,String ruleExpression,String description,String applicableEntity,
			String entityParametersCount,String configMeta,String entityParameterName,String totalThresholds,String id,String frequency,String isSubscribed,String duration,String insightType,
			String threshold_Name_Value_Type,int expectedStatus,String testEnabled) throws ParseException, InterruptedException, JoseException,IOException {

		commontUtils.isTCEnabled(testEnabled, testCaseId);
		logger.info("Testcase--> "+testCaseId+ "->"+"  Validate GET "+insightNameKey +"  User Subscription ,"+testCaseDescription);
		automationTestResults.setInsights("SubscriptionNotPaid");
		updateUserSubsctions(testCaseId ,testCaseDescription,insightNameKey,testNegativeScenario,triggerType,insightName,
				insightTitle,container,cobrandId,keys,ruleExpression,description,applicableEntity,
				entityParametersCount,configMeta,entityParameterName,totalThresholds,id,frequency,isSubscribed,duration,insightType,
				threshold_Name_Value_Type,expectedStatus,testEnabled);
	}
	
	@Test(enabled = true, dataProvider = "feeder")
	@Source("\\TestData\\CSVFiles\\Insights\\AggregateSavingsCheckingAccount\\PatchUserSubscriptionAggregateSavingsCheckingAccount.csv")
	public void testPatchUserSubscriptionAggregateSavingsCheckingAccount(String testCaseId ,String testCaseDescription,String insightNameKey,String testNegativeScenario,String triggerType,String insightName,
			String insightTitle,String container,String cobrandId,String keys,String ruleExpression,String description,String applicableEntity,
			String entityParametersCount,String configMeta,String entityParameterName,String totalThresholds,String id,String frequency,String isSubscribed,String duration,String insightType,
			String threshold_Name_Value_Type,int expectedStatus,String testEnabled) throws ParseException, InterruptedException, JoseException,IOException {
		commontUtils.isTCEnabled(testEnabled, testCaseId);
		logger.info("Testcase--> "+testCaseId+ "->"+"  Validate GET "+insightNameKey +"  User Subscription ,"+testCaseDescription);
		automationTestResults.setInsights("AggregateSavingsCheckingAccount");
		updateUserSubsctions(testCaseId ,testCaseDescription,insightNameKey,testNegativeScenario,triggerType,insightName,
				insightTitle,container,cobrandId,keys,ruleExpression,description,applicableEntity,
				entityParametersCount,configMeta,entityParameterName,totalThresholds,id,frequency,isSubscribed,duration,insightType,
				threshold_Name_Value_Type,expectedStatus,testEnabled);                      
	}
	
	@Test(enabled = true, dataProvider = "feeder")
	@Source("\\TestData\\CSVFiles\\Insights\\AggregateInvestmentAccount\\PatchUserSubscriptionAggregateInvestmentAccount.csv")
	public void testPatchUserSubscriptionAggregateInvestmentAccount(String testCaseId ,String testCaseDescription,String insightNameKey,String testNegativeScenario,String triggerType,String insightName,
			String insightTitle,String container,String cobrandId,String keys,String ruleExpression,String description,String applicableEntity,
			String entityParametersCount,String configMeta,String entityParameterName,String totalThresholds,String id,String frequency,String isSubscribed,String duration,String insightType,
			String threshold_Name_Value_Type,int expectedStatus,String testEnabled) throws ParseException, InterruptedException, JoseException,IOException {
		commontUtils.isTCEnabled(testEnabled, testCaseId);
		logger.info("Testcase--> "+testCaseId+ "->"+"  Validate GET "+insightNameKey +"  User Subscription ,"+testCaseDescription);
		automationTestResults.setInsights("AggregateInvestmentAccount");
		updateUserSubsctions(testCaseId ,testCaseDescription,insightNameKey,testNegativeScenario,triggerType,insightName,
				insightTitle,container,cobrandId,keys,ruleExpression,description,applicableEntity,
				entityParametersCount,configMeta,entityParameterName,totalThresholds,id,frequency,isSubscribed,duration,insightType,
				threshold_Name_Value_Type,expectedStatus,testEnabled);                      
	}
	
	@AfterMethod(lastTimeOnly=true)
	public void terminate() throws Throwable {

		TestInfo testInfo = new TestInfo();
		testInfo = insightsHelper.printSummaryOfTests(notificationsTestSummary,"GET  LowBalanceWarning Insight",testInfo);
		automationTestResults.setExecutionDate(new Date().toString());
		automationTestResults.setTestInformation(testsInfoList);
		automationTestResults.setUserName(cobrandUser);
		automationTestResults.setStoryId(storyId);
		automationTestResults.setTestsPassPercentage(automationTestResults.getTestsPassPercentage(testInfo));
		insightsDBUtils.addTestResults(automationTestResults);
		testsInfoList.clear();
		System.out.println("Insight Test Execution is completed");
	}

	public void notificationsSummary(boolean notificationTestStatus,TestInfo testInfo,String testCaseId,String insightName,String testCaseDescription) {
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
		}
	}

	@AfterClass(alwaysRun = true)
	public void unRegisteredUser() {
		userUtils.unRegisterUser(sessionObj);
	}

}
