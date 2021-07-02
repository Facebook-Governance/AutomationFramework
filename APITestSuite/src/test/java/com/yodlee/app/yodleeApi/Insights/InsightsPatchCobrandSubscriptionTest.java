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

import com.google.gson.JsonArray;
import com.yodlee.insights.yodleeApi.utils.AutomationTestResults;
import com.yodlee.insights.yodleeApi.utils.FailureReason;
import com.yodlee.insights.yodleeApi.utils.InsightsDBUtils;
import com.yodlee.insights.yodleeApi.utils.InsightsGenerics;
import com.yodlee.insights.yodleeApi.utils.InsightsHelper;
import com.yodlee.insights.yodleeApi.utils.InsightsProperties;
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
import com.yodlee.yodleeApi.utils.apiUtils.InsightUtils;
import com.yodlee.yodleeApi.utils.commonUtils.CommonUtils;

import io.restassured.response.Response;


public class InsightsPatchCobrandSubscriptionTest extends Base {

	protected Logger logger = LoggerFactory.getLogger(InsightsPatchCobrandSubscriptionTest.class);
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
	InsightsHelper insightsHelper = new InsightsHelper();
	User userInfo = User.builder().build();
	String jwtCobAuthToken = null;
	String jwtUserAuthToken = null;
	Map<String ,String>  testSummary = null;
	InsightsGenerics insightsGenerics = new InsightsGenerics();
	static FailureReason failureReason = null;
	HashMap<String,ArrayList<String>> negativeExpectedValuesMap;
	String executedInsight = null;
	String envCobrand=null;
	String envUser=null;
	InsightsProperties prop = new InsightsProperties();
	String cobrandUser = null;
	boolean triggerOnce = false;
	Response cobSubscriptionResponse = null,getCobSubscriptionResponseBeforPatch=null;
	List<TestInfo> testsInfoList = null;
	static Map<String ,String>  notificationsTestSummary = new HashMap<String,String>();
	AutomationTestResults automationTestResults = new AutomationTestResults();
	InsightsDBUtils insightsDBUtils = new InsightsDBUtils();
	String storyId = null,buildNo="271";

	@BeforeClass(alwaysRun = true)
	public void setUp() throws Exception {
		System.out.println("initiated PATCH cobrand subscription API execution");
	    jwtCobAuthToken = "Bearer " + jwtHelper.getJWTToken("msampath@yodlee.com","wellnessCobrand");
	    jwtUserAuthToken = "Bearer " + jwtHelper.getJWTToken(userInfo.getUsername(),"wellnessUser");
		envCobrand=prop.readPropertiesFile().getProperty("envCobrand");
		negativeExpectedValuesMap = insightsGenerics.readNegativeExpectedValues();
		cobrandUser=prop.readPropertiesFile().getProperty("cobrandUser");
	}

	@BeforeMethod(firstTimeOnly=true)
	public void initializations() throws Exception {
		jwtCobAuthToken = jwtHelper.getJWTToken(cobrandUser,envCobrand);
		jwtCobAuthToken = "Bearer ".concat(jwtCobAuthToken);
		testSummary = new HashMap<String,String>();
		failureReason = new  FailureReason();
		testsInfoList = new ArrayList<TestInfo>();
		HttpMethodParameters httpParams=insightsHelper.getHttpParams("cobrandSubscriptionRequest", null, null, jwtCobAuthToken,null,null,null,null);
		getCobSubscriptionResponseBeforPatch = insightUtils.getCobrandSubscriptions(httpParams,null);
	}

	@Test(enabled = true, dataProvider = "feeder")
	@Source("\\TestData\\CSVFiles\\Insights\\LowBalWarning\\PatchCobSubscriptionForLowBalWarning.csv")
	public void testPatchCobrandSubscriptionForLowBalanceWarning(String testCaseId ,String testCaseDescription,String insightNameKey,String testNegativeScenario,String insightNameEdit,
			String insightTitle,String container,String cobrandId,String keys,String ruleExpression,String description,String applicableEntity,
			String entityParametersCount,String congigMeta,String entityParameterName,String totalThresholds,String id,String frequency,String isSubscribed,String duration,String insightType,
			String threshold_Name_Value_Type,int expectedStatus,String testEnabled) throws ParseException, InterruptedException, JoseException, IOException {

		commontUtils.isTCEnabled(testEnabled, testCaseId);
		logger.info("Testcase--> "+testCaseId+ "->"+"  Validate PATCH "+insightNameKey +"  Cobrand Subscription ,"+testCaseDescription);
		automationTestResults.setInsights("PATCH_SUB_LowBalWarn");
		automationTestResults.setBuildNo(buildNo);
		storyId = testCaseId.substring(0,  7);
		TestInfo testInfo = new TestInfo();
		testInfo.setTestCaseId(testCaseId);
		testInfo.setTestDescription(testCaseDescription);
		executedInsight=insightNameKey;
		boolean testExecutionStatus = false;
		HttpMethodParameters cobHttpParams = insightsGenerics.updateCobrandSubscriptionProcessing(jwtCobAuthToken,insightNameKey, testNegativeScenario, insightNameEdit, insightTitle, container,
				cobrandId, keys, ruleExpression, description, applicableEntity,	entityParametersCount, entityParameterName, totalThresholds,id,
				frequency, isSubscribed, duration, insightType,threshold_Name_Value_Type);
		Response patchCobrandSubscriptionResponse = insightUtils.patchCobrandSubscriptions(cobHttpParams,null);
		Response getCobSubscriptionResponseAfterPatch = insightUtils.getCobrandSubscriptions(cobHttpParams,null);
		String testStatus = insightsGenerics.updateCobrandSubscriptionResponseVerificationPostPatch(testCaseId,testExecutionStatus,testSummary,patchCobrandSubscriptionResponse,negativeExpectedValuesMap,testNegativeScenario, testCaseDescription, getCobSubscriptionResponseAfterPatch, insightNameKey, frequency ,duration,insightType,insightTitle, entityParameterName, isSubscribed, threshold_Name_Value_Type,expectedStatus,failureReason,testInfo);
		notificationsSummary(Boolean.valueOf(testStatus),testInfo,testCaseId,insightNameKey,testCaseDescription);
		if(!Boolean.valueOf(testStatus))
			Assert.fail();


	}

	@Test(enabled = true, dataProvider = "feeder")
	@Source("\\TestData\\CSVFiles\\Insights\\GoalSavingsOffTrack\\PatchCobSubscriptionGoalOffTrack.csv")
	public void testPatchCobrandSubscriptionForGoalOffTrack(String testCaseId ,String testCaseDescription,String insightNameKey,String testNegativeScenario,String insightNameEdit,
			String insightTitle,String container,String cobrandId,String keys,String ruleExpression,String description,String applicableEntity,
			String entityParametersCount,String congigMeta,String entityParameterName,String totalThresholds,String id,String frequency,String isSubscribed,String duration,String insightType,
			String threshold_Name_Value_Type,int expectedStatus,String testEnabled) throws ParseException, InterruptedException, JoseException, IOException {

		commontUtils.isTCEnabled(testEnabled, testCaseId);
		logger.info("Testcase--> "+testCaseId+ "->"+"  Validate PATCH "+insightNameKey +"  Cobrand Subscription ,"+testCaseDescription);
		automationTestResults.setInsights("PATCH_SUB_GoalOffTrack");
		automationTestResults.setBuildNo(buildNo);
		storyId = testCaseId.substring(0,  7);
		TestInfo testInfo = new TestInfo();
		testInfo.setTestCaseId(testCaseId);
		testInfo.setTestDescription(testCaseDescription);
		executedInsight=insightNameKey;
		boolean testExecutionStatus = false;
		HttpMethodParameters cobHttpParams = insightsGenerics.updateCobrandSubscriptionProcessing(jwtCobAuthToken,insightNameKey, testNegativeScenario, insightNameEdit, insightTitle, container,
				cobrandId, keys, ruleExpression, description, applicableEntity,	entityParametersCount, entityParameterName, totalThresholds,id,
				frequency, isSubscribed, duration, insightType,threshold_Name_Value_Type);
		Response patchCobrandSubscriptionResponse = insightUtils.patchCobrandSubscriptions(cobHttpParams,null);
		Response getCobSubscriptionResponseAfterPatch = insightUtils.getCobrandSubscriptions(cobHttpParams,null);
		String testStatus = insightsGenerics.updateCobrandSubscriptionResponseVerificationPostPatch(testCaseId,testExecutionStatus,testSummary,patchCobrandSubscriptionResponse,negativeExpectedValuesMap,testNegativeScenario, testCaseDescription, getCobSubscriptionResponseAfterPatch, insightNameKey, frequency ,duration,insightType,insightTitle, entityParameterName, isSubscribed, threshold_Name_Value_Type,expectedStatus,failureReason,testInfo);
		notificationsSummary(Boolean.valueOf(testStatus),testInfo,testCaseId,insightNameKey,testCaseDescription);
		if(!Boolean.valueOf(testStatus))
			Assert.fail();

	}
	@Test(enabled = true, dataProvider = "feeder")
	@Source("\\TestData\\CSVFiles\\Insights\\ChangeInNetworth\\PatchCobSubscriptionNetworthChange.csv")
	public void testPatchCobrandSubscriptionForNetworthChange(String testCaseId ,String testCaseDescription,String insightNameKey,String testNegativeScenario,String insightNameEdit,
			String insightTitle,String container,String cobrandId,String keys,String ruleExpression,String description,String applicableEntity,
			String entityParametersCount,String congigMeta,String entityParameterName,String totalThresholds,String id,String frequency,String isSubscribed,String duration,String insightType,
			String threshold_Name_Value_Type,int expectedStatus,String testEnabled) throws ParseException, InterruptedException, JoseException, IOException {

		commontUtils.isTCEnabled(testEnabled, testCaseId);
		logger.info("Testcase--> "+testCaseId+ "->"+"  Validate PATCH "+insightNameKey +"  Cobrand Subscription ,"+testCaseDescription);
		automationTestResults.setInsights("PATCH_COBSUB_NetworthChange");
		automationTestResults.setBuildNo(buildNo);
		storyId = testCaseId.substring(0,  7);
		TestInfo testInfo = new TestInfo();
		testInfo.setTestCaseId(testCaseId);
		testInfo.setTestDescription(testCaseDescription);
		executedInsight=insightNameKey;
		boolean testExecutionStatus = false;
		HttpMethodParameters cobHttpParams = insightsGenerics.updateCobrandSubscriptionProcessing(jwtCobAuthToken,insightNameKey, testNegativeScenario, insightNameEdit, insightTitle, container,
				cobrandId, keys, ruleExpression, description, applicableEntity,	entityParametersCount, entityParameterName, totalThresholds,id,
				frequency, isSubscribed, duration, insightType,threshold_Name_Value_Type);
		Response patchCobrandSubscriptionResponse = insightUtils.patchCobrandSubscriptions(cobHttpParams,null);
		Response getCobSubscriptionResponseAfterPatch = insightUtils.getCobrandSubscriptions(cobHttpParams,null);
		String testStatus = insightsGenerics.updateCobrandSubscriptionResponseVerificationPostPatch(testCaseId,testExecutionStatus,testSummary,patchCobrandSubscriptionResponse,negativeExpectedValuesMap,testNegativeScenario, testCaseDescription, getCobSubscriptionResponseAfterPatch, insightNameKey, frequency ,duration,insightType,insightTitle, entityParameterName, isSubscribed, threshold_Name_Value_Type,expectedStatus,failureReason,testInfo);
		notificationsSummary(Boolean.valueOf(testStatus),testInfo,testCaseId,insightNameKey,testCaseDescription);
		if(!Boolean.valueOf(testStatus))
			Assert.fail();
	}

	@Test(enabled = true, dataProvider = "feeder")
	@Source("\\TestData\\CSVFiles\\Insights\\SalDeposited\\PatchCobSubscriptionForSalaryDeposited.csv")
	public void testPatchCobrandSubscriptionForSalaryDeposited(String testCaseId ,String testCaseDescription,String insightNameKey,String testNegativeScenario,String insightNameEdit,
			String insightTitle,String container,String cobrandId,String keys,String ruleExpression,String description,String applicableEntity,
			String entityParametersCount,String congigMeta,String entityParameterName,String totalThresholds,String id,String frequency,String isSubscribed,String duration,String insightType,
			String threshold_Name_Value_Type,int expectedStatus,String testEnabled) throws ParseException, InterruptedException, JoseException, IOException {

		commontUtils.isTCEnabled(testEnabled, testCaseId);
		logger.info("Testcase--> "+testCaseId+ "->"+"  Validate PATCH "+insightNameKey +"  Cobrand Subscription ,"+testCaseDescription);
		automationTestResults.setInsights("PATCH_COBSUB_SalaryDeposited");
		automationTestResults.setBuildNo(buildNo);
		storyId = testCaseId.substring(0,  7);
		TestInfo testInfo = new TestInfo();
		testInfo.setTestCaseId(testCaseId);
		testInfo.setTestDescription(testCaseDescription);
		executedInsight=insightNameKey;
		boolean testExecutionStatus = false;
		HttpMethodParameters cobHttpParams = insightsGenerics.updateCobrandSubscriptionProcessing(jwtCobAuthToken,insightNameKey, testNegativeScenario, insightNameEdit, insightTitle, container,
				cobrandId, keys, ruleExpression, description, applicableEntity,	entityParametersCount, entityParameterName, totalThresholds,id,
				frequency, isSubscribed, duration, insightType,threshold_Name_Value_Type);

		Response patchCobrandSubscriptionResponse = insightUtils.patchCobrandSubscriptions(cobHttpParams,null);
		Response getCobSubscriptionResponseAfterPatch = insightUtils.getCobrandSubscriptions(cobHttpParams,null);
		String testStatus = insightsGenerics.updateCobrandSubscriptionResponseVerificationPostPatch(testCaseId,testExecutionStatus,testSummary,patchCobrandSubscriptionResponse,negativeExpectedValuesMap,testNegativeScenario, testCaseDescription, getCobSubscriptionResponseAfterPatch, insightNameKey, frequency ,duration,insightType,insightTitle, entityParameterName, isSubscribed, threshold_Name_Value_Type,expectedStatus,failureReason,testInfo);
		notificationsSummary(Boolean.valueOf(testStatus),testInfo,testCaseId,insightNameKey,testCaseDescription);
		if(!Boolean.valueOf(testStatus))
			Assert.fail();

	}
	@Test(enabled = true, dataProvider = "feeder")
	@Source("\\TestData\\CSVFiles\\Insights\\CameinUnderBudget\\PatchCobSubscriptionCameinUnderBudget.csv")
	public void testPatchCobrandSubscriptionForCameinUnderBudget(String testCaseId ,String testCaseDescription,String insightNameKey,String testNegativeScenario,String insightNameEdit,
			String insightTitle,String container,String cobrandId,String keys,String ruleExpression,String description,String applicableEntity,
			String entityParametersCount,String congigMeta,String entityParameterName,String totalThresholds,String id,String frequency,String isSubscribed,String duration,String insightType,
			String threshold_Name_Value_Type,int expectedStatus,String testEnabled) throws ParseException, InterruptedException, JoseException, IOException {

		commontUtils.isTCEnabled(testEnabled, testCaseId);
		logger.info("Testcase--> "+testCaseId+ "->"+"  Validate PATCH "+insightNameKey +"  Cobrand Subscription ,"+testCaseDescription);
		automationTestResults.setInsights("PATCH_COBSUB_CameinUnderBudget");
		automationTestResults.setBuildNo(buildNo);
		storyId = testCaseId.substring(0,  7);
		TestInfo testInfo = new TestInfo();
		testInfo.setTestCaseId(testCaseId);
		testInfo.setTestDescription(testCaseDescription);
		executedInsight=insightNameKey;
		boolean testExecutionStatus = false;
		HttpMethodParameters cobHttpParams = insightsGenerics.updateCobrandSubscriptionProcessing(jwtCobAuthToken,insightNameKey, testNegativeScenario, insightNameEdit, insightTitle, container,
				cobrandId, keys, ruleExpression, description, applicableEntity,	entityParametersCount, entityParameterName, totalThresholds,id,
				frequency, isSubscribed, duration, insightType,threshold_Name_Value_Type);
		Response patchCobrandSubscriptionResponse = insightUtils.patchCobrandSubscriptions(cobHttpParams,null);
		Response getCobSubscriptionResponseAfterPatch = insightUtils.getCobrandSubscriptions(cobHttpParams,null);
		String testStatus = insightsGenerics.updateCobrandSubscriptionResponseVerificationPostPatch(testCaseId,testExecutionStatus,testSummary,patchCobrandSubscriptionResponse,negativeExpectedValuesMap,testNegativeScenario, testCaseDescription, getCobSubscriptionResponseAfterPatch, insightNameKey, frequency ,duration,insightType,insightTitle, entityParameterName, isSubscribed, threshold_Name_Value_Type,expectedStatus,failureReason,testInfo);
		notificationsSummary(Boolean.valueOf(testStatus),testInfo,testCaseId,insightNameKey,testCaseDescription);
		if(!Boolean.valueOf(testStatus))
			Assert.fail();

	}
	@Test(enabled = true, dataProvider = "feeder")
	@Source("\\TestData\\CSVFiles\\Insights\\LargeDepositNotice\\PatchCobSubscriptionLargeDepositNotice.csv")
	public void testPatchCobrandSubscriptionForLargeDepositNotice(String testCaseId ,String testCaseDescription,String insightNameKey,String testNegativeScenario,String insightNameEdit,
			String insightTitle,String container,String cobrandId,String keys,String ruleExpression,String description,String applicableEntity,
			String entityParametersCount,String congigMeta,String entityParameterName,String totalThresholds,String id,String frequency,String isSubscribed,String duration,String insightType,
			String threshold_Name_Value_Type,int expectedStatus,String testEnabled) throws ParseException, InterruptedException, JoseException, IOException {

		commontUtils.isTCEnabled(testEnabled, testCaseId);
		logger.info("Testcase--> "+testCaseId+ "->"+"  Validate PATCH "+insightNameKey +"  Cobrand Subscription ,"+testCaseDescription);
		automationTestResults.setInsights("PATCH_COBSUB_LargeDepositNotice");
		automationTestResults.setBuildNo(buildNo);
		storyId = testCaseId.substring(0,  7);
		TestInfo testInfo = new TestInfo();
		testInfo.setTestCaseId(testCaseId);
		testInfo.setTestDescription(testCaseDescription);
		executedInsight=insightNameKey;
		boolean testExecutionStatus = false;
		HttpMethodParameters cobHttpParams = insightsGenerics.updateCobrandSubscriptionProcessing(jwtCobAuthToken,insightNameKey, testNegativeScenario, insightNameEdit, insightTitle, container,
				cobrandId, keys, ruleExpression, description, applicableEntity,	entityParametersCount, entityParameterName, totalThresholds,id,
				frequency, isSubscribed, duration, insightType,threshold_Name_Value_Type);
		Response patchCobrandSubscriptionResponse = insightUtils.patchCobrandSubscriptions(cobHttpParams,null);
		Response getCobSubscriptionResponseAfterPatch = insightUtils.getCobrandSubscriptions(cobHttpParams,null);
		String testStatus = insightsGenerics.updateCobrandSubscriptionResponseVerificationPostPatch(testCaseId,testExecutionStatus,testSummary,patchCobrandSubscriptionResponse,negativeExpectedValuesMap,testNegativeScenario, testCaseDescription, getCobSubscriptionResponseAfterPatch, insightNameKey, frequency ,duration,insightType,insightTitle, entityParameterName, isSubscribed, threshold_Name_Value_Type,expectedStatus,failureReason,testInfo);
		notificationsSummary(Boolean.valueOf(testStatus),testInfo,testCaseId,insightNameKey,testCaseDescription);
		if(!Boolean.valueOf(testStatus))
			Assert.fail(failureReason.getFailureReason());

	}
	@Test(enabled = true, dataProvider = "feeder")
	@Source("\\TestData\\CSVFiles\\Insights\\CreditLimitWarning1\\PatchCobSubscriptionForCreditLimitWarning.csv")
	public void testPatchCobrandSubscriptionForCreditLimitWarning(String testCaseId ,String testCaseDescription,String insightNameKey,String testNegativeScenario,String insightNameEdit,
			String insightTitle,String container,String cobrandId,String keys,String ruleExpression,String description,String applicableEntity,
			String entityParametersCount,String congigMeta,String entityParameterName,String totalThresholds,String id,String frequency,String isSubscribed,String duration,String insightType,
			String threshold_Name_Value_Type,int expectedStatus,String testEnabled) throws ParseException, InterruptedException, JoseException, IOException {

		commontUtils.isTCEnabled(testEnabled, testCaseId);
		logger.info("Testcase--> "+testCaseId+ "->"+"  Validate PATCH "+insightNameKey +"  Cobrand Subscription ,"+testCaseDescription);
		automationTestResults.setInsights("PATCH_SUB_CreditLimitWarning");
		automationTestResults.setBuildNo(buildNo);
		storyId = testCaseId.substring(0,  7);
		TestInfo testInfo = new TestInfo();
		testInfo.setTestCaseId(testCaseId);
		testInfo.setTestDescription(testCaseDescription);
		executedInsight=insightNameKey;
		boolean testExecutionStatus = false;
		HttpMethodParameters cobHttpParams = insightsGenerics.updateCobrandSubscriptionProcessing(jwtCobAuthToken,insightNameKey, testNegativeScenario, insightNameEdit, insightTitle, container,
				cobrandId, keys, ruleExpression, description, applicableEntity,	entityParametersCount, entityParameterName, totalThresholds,id,
				frequency, isSubscribed, duration, insightType,threshold_Name_Value_Type);
		Response patchCobrandSubscriptionResponse = insightUtils.patchCobrandSubscriptions(cobHttpParams,null);
		Response getCobSubscriptionResponseAfterPatch = insightUtils.getCobrandSubscriptions(cobHttpParams,null);
		String testStatus = insightsGenerics.updateCobrandSubscriptionResponseVerificationPostPatch(testCaseId,testExecutionStatus,testSummary,patchCobrandSubscriptionResponse,negativeExpectedValuesMap,testNegativeScenario, testCaseDescription, getCobSubscriptionResponseAfterPatch, insightNameKey, frequency ,duration,insightType,insightTitle, entityParameterName, isSubscribed, threshold_Name_Value_Type,expectedStatus,failureReason,testInfo);
		notificationsSummary(Boolean.valueOf(testStatus),testInfo,testCaseId,insightNameKey,testCaseDescription);
		if(!Boolean.valueOf(testStatus))
			Assert.fail();

	}

	@Test(enabled = true, dataProvider = "feeder")
	@Source("\\TestData\\CSVFiles\\Insights\\CardStatementDueReminder\\PatchCobSubscriptionForCardStatementDueReminder.csv")
	public void testPatchCobrandSubscriptionForCardStatementDueReminder(String testCaseId ,String testCaseDescription,String insightNameKey,String testNegativeScenario,String insightNameEdit,
			String insightTitle,String container,String cobrandId,String keys,String ruleExpression,String description,String applicableEntity,
			String entityParametersCount,String congigMeta,String entityParameterName,String totalThresholds,String id,String frequency,String isSubscribed,String duration,String insightType,
			String threshold_Name_Value_Type,int expectedStatus,String testEnabled) throws ParseException, InterruptedException, JoseException, IOException {

		commontUtils.isTCEnabled(testEnabled, testCaseId);
		logger.info("Testcase--> "+testCaseId+ "->"+"  Validate PATCH "+insightNameKey +"  Cobrand Subscription ,"+testCaseDescription);
		automationTestResults.setInsights("PATCH_SUB_CardStatementDueReminder");
		automationTestResults.setBuildNo(buildNo);
		storyId = testCaseId.substring(0,  7);
		TestInfo testInfo = new TestInfo();
		testInfo.setTestCaseId(testCaseId);
		testInfo.setTestDescription(testCaseDescription);
		executedInsight=insightNameKey;
		boolean testExecutionStatus = false;
		HttpMethodParameters cobHttpParams = insightsGenerics.updateCobrandSubscriptionProcessing(jwtCobAuthToken,insightNameKey, testNegativeScenario, insightNameEdit, insightTitle, container,
				cobrandId, keys, ruleExpression, description, applicableEntity,	entityParametersCount, entityParameterName, totalThresholds,id,
				frequency, isSubscribed, duration, insightType,threshold_Name_Value_Type);
		Response patchCobrandSubscriptionResponse = insightUtils.patchCobrandSubscriptions(cobHttpParams,null);
		Response getCobSubscriptionResponseAfterPatch = insightUtils.getCobrandSubscriptions(cobHttpParams,null);
		String testStatus = insightsGenerics.updateCobrandSubscriptionResponseVerificationPostPatch(testCaseId,testExecutionStatus,testSummary,patchCobrandSubscriptionResponse,negativeExpectedValuesMap,testNegativeScenario, testCaseDescription, getCobSubscriptionResponseAfterPatch, insightNameKey, frequency ,duration,insightType,insightTitle, entityParameterName, isSubscribed, threshold_Name_Value_Type,expectedStatus,failureReason,testInfo);
		notificationsSummary(Boolean.valueOf(testStatus),testInfo,testCaseId,insightNameKey,testCaseDescription);
		if(!Boolean.valueOf(testStatus))
			Assert.fail();
	}




	@Test(enabled = true, dataProvider = "feeder")
	@Source("\\TestData\\CSVFiles\\Insights\\ChangeInAsset\\PatchCobSubscriptionAssetChange.csv")
	public void testPatchCobrandSubscriptionForAssetChange(String testCaseId ,String testCaseDescription,String insightNameKey,String testNegativeScenario,String insightNameEdit,
			String insightTitle,String container,String cobrandId,String validateKeys,String ruleExpression,String description,String applicableEntity,
			String entityParametersCount,String congigMeta,String entityParameterName,String totalThresholds,String id,String frequency,String isSubscribed,String duration,String insightType,
			String threshold_Name_Value_Type,int expectedStatus,String testEnabled) throws ParseException, InterruptedException, JoseException, IOException {
		commontUtils.isTCEnabled(testEnabled, testCaseId);
		logger.info("Testcase--> "+testCaseId+ "->"+"  Validate PATCH "+insightNameKey +"  Cobrand Subscription ,"+testCaseDescription);
		automationTestResults.setInsights("PATCH_SUB_AssetChange");
		automationTestResults.setBuildNo(buildNo);
		storyId = testCaseId.substring(0,  7);
		TestInfo testInfo = new TestInfo();
		testInfo.setTestCaseId(testCaseId);
		testInfo.setTestDescription(testCaseDescription);
		executedInsight=insightNameKey;
		boolean testExecutionStatus = false;				
		HttpMethodParameters cobHttpParams = insightsGenerics.updateCobrandSubscriptionProcessing(jwtCobAuthToken,insightNameKey, testNegativeScenario, insightNameEdit, insightTitle, container,
				cobrandId, validateKeys, ruleExpression, description, applicableEntity,	entityParametersCount, entityParameterName, totalThresholds,id, 
				frequency, isSubscribed, duration, insightType,threshold_Name_Value_Type);
		Response patchCobrandSubscriptionResponse = insightUtils.patchCobrandSubscriptions(cobHttpParams,null);
		Response getCobSubscriptionResponseAfterPatch = insightUtils.getCobrandSubscriptions(cobHttpParams,null);		
		String testStatus = insightsGenerics.updateCobrandSubscriptionResponseVerificationPostPatch(testCaseId,testExecutionStatus,testSummary,patchCobrandSubscriptionResponse,negativeExpectedValuesMap,testNegativeScenario, testCaseDescription, getCobSubscriptionResponseAfterPatch, insightNameKey, frequency ,duration,insightType,insightTitle, entityParameterName, isSubscribed, threshold_Name_Value_Type,expectedStatus,failureReason,testInfo);
		notificationsSummary(Boolean.valueOf(testStatus),testInfo,testCaseId,insightNameKey,testCaseDescription);		
		if(!Boolean.valueOf(testStatus))
			Assert.fail();	
	}

	@Test(enabled = true, dataProvider = "feeder")
	@Source("\\TestData\\CSVFiles\\Insights\\ChangeInLiability\\PatchCobSubscriptionLiabilityChange.csv")
	public void testPatchCobrandSubscriptionForLiabilityChange(String testCaseId ,String testCaseDescription,String insightNameKey,String testNegativeScenario,String insightNameEdit,
			String insightTitle,String container,String cobrandId,String validateKeys,String ruleExpression,String description,String applicableEntity,
			String entityParametersCount,String congigMeta,String entityParameterName,String totalThresholds,String id,String frequency,String isSubscribed,String duration,String insightType,
			String threshold_Name_Value_Type,int expectedStatus,String testEnabled) throws ParseException, InterruptedException, JoseException, IOException {


		commontUtils.isTCEnabled(testEnabled, testCaseId);
		logger.info("Testcase--> "+testCaseId+ "->"+"  Validate PATCH "+insightNameKey +"  Cobrand Subscription ,"+testCaseDescription);
		automationTestResults.setInsights("PATCH_SUB_LiabilityChange");
		automationTestResults.setBuildNo(buildNo);
		storyId = testCaseId.substring(0,  7);
		TestInfo testInfo = new TestInfo();
		testInfo.setTestCaseId(testCaseId);
		testInfo.setTestDescription(testCaseDescription);
		executedInsight=insightNameKey;
		boolean testExecutionStatus = false;				
		HttpMethodParameters cobHttpParams = insightsGenerics.updateCobrandSubscriptionProcessing(jwtCobAuthToken,insightNameKey, testNegativeScenario, insightNameEdit, insightTitle, container,
				cobrandId, validateKeys, ruleExpression, description, applicableEntity,	entityParametersCount, entityParameterName, totalThresholds,id, 
				frequency, isSubscribed, duration, insightType,threshold_Name_Value_Type);
		Response patchCobrandSubscriptionResponse = insightUtils.patchCobrandSubscriptions(cobHttpParams,null);
		Response getCobSubscriptionResponseAfterPatch = insightUtils.getCobrandSubscriptions(cobHttpParams,null);		
		String testStatus = insightsGenerics.updateCobrandSubscriptionResponseVerificationPostPatch(testCaseId,testExecutionStatus,testSummary,patchCobrandSubscriptionResponse,negativeExpectedValuesMap,testNegativeScenario, testCaseDescription, getCobSubscriptionResponseAfterPatch, insightNameKey, frequency ,duration,insightType,insightTitle, entityParameterName, isSubscribed, threshold_Name_Value_Type,expectedStatus,failureReason,testInfo);
		notificationsSummary(Boolean.valueOf(testStatus),testInfo,testCaseId,insightNameKey,testCaseDescription);		
		if(!Boolean.valueOf(testStatus))
			Assert.fail();	
	}

	@Test(enabled = true, dataProvider = "feeder")
	@Source("\\TestData\\CSVFiles\\Insights\\CreditUtilization1\\PatchCobSubscriptionCreditUtilization.csv")
	public void testPatchCobrandSubscriptionForCreditUtilization(String testCaseId ,String testCaseDescription,String insightNameKey,String testNegativeScenario,String insightNameEdit,
			String insightTitle,String container,String cobrandId,String keys,String ruleExpression,String description,String applicableEntity,
			String entityParametersCount,String congigMeta,String entityParameterName,String totalThresholds,String id,String frequency,String isSubscribed,String duration,String insightType,
			String threshold_Name_Value_Type,int expectedStatus,String testEnabled) throws Exception {

		commontUtils.isTCEnabled(testEnabled, testCaseId);
		logger.info("Testcase--> "+testCaseId+ "->"+"  Validate PATCH "+insightNameKey +"  Cobrand Subscription ,"+testCaseDescription);
		automationTestResults.setInsights("PATCH_SUB_CreditUtilization");
		automationTestResults.setBuildNo(buildNo);
		storyId = testCaseId.substring(0,  7);
		TestInfo testInfo = new TestInfo();
		testInfo.setTestCaseId(testCaseId);
		testInfo.setTestDescription(testCaseDescription);
		executedInsight=insightNameKey;
		boolean testExecutionStatus = false;
		HttpMethodParameters cobHttpParams = insightsGenerics.updateCobrandSubscriptionProcessing(jwtCobAuthToken,insightNameKey, testNegativeScenario, insightNameEdit, insightTitle, container,
				cobrandId, keys, ruleExpression, description, applicableEntity,	entityParametersCount, entityParameterName, totalThresholds,id,
				frequency, isSubscribed, duration, insightType,threshold_Name_Value_Type);
		Response patchCobrandSubscriptionResponse = insightUtils.patchCobrandSubscriptions(cobHttpParams,null);
		Response getCobSubscriptionResponseAfterPatch = insightUtils.getCobrandSubscriptions(cobHttpParams,null);
		String testStatus = insightsGenerics.updateCobrandSubscriptionResponseVerificationPostPatch(testCaseId,testExecutionStatus,testSummary,patchCobrandSubscriptionResponse,negativeExpectedValuesMap,testNegativeScenario, testCaseDescription, getCobSubscriptionResponseAfterPatch, insightNameKey, frequency ,duration,insightType,insightTitle, entityParameterName, isSubscribed, threshold_Name_Value_Type,expectedStatus,failureReason,testInfo);
		notificationsSummary(Boolean.valueOf(testStatus),testInfo,testCaseId,insightNameKey,testCaseDescription);
		if(!Boolean.valueOf(testStatus))
			Assert.fail();	
		
	}

	@Test(enabled = true, dataProvider = "feeder")
	@Source("\\TestData\\CSVFiles\\Insights\\ExpensesApproachingBudget\\PatchCobSubscriptionForExpensesApproachingBudget.csv")
	public void testPatchCobrandSubscriptionForExpensesApproachingBudget(String testCaseId ,String testCaseDescription,String insightNameKey,String testNegativeScenario,String insightNameEdit,
			String insightTitle,String container,String cobrandId,String keys,String ruleExpression,String description,String applicableEntity,
			String entityParametersCount,String congigMeta,String entityParameterName,String totalThresholds,String id,String frequency,String isSubscribed,String duration,String insightType,
			String threshold_Name_Value_Type,int expectedStatus,String testEnabled) throws Exception {

		commontUtils.isTCEnabled(testEnabled, testCaseId);
		logger.info("Testcase--> "+testCaseId+ "->"+"  Validate PATCH "+insightNameKey +"  Cobrand Subscription ,"+testCaseDescription);
		automationTestResults.setInsights("PATCH_SUB_ExpensesApproachingBudget");
		automationTestResults.setBuildNo(buildNo);
		storyId = testCaseId.substring(0,  7);
		TestInfo testInfo = new TestInfo();
		testInfo.setTestCaseId(testCaseId);
		testInfo.setTestDescription(testCaseDescription);
		executedInsight=insightNameKey;
		boolean testExecutionStatus = false;
		HttpMethodParameters cobHttpParams = insightsGenerics.updateCobrandSubscriptionProcessing(jwtCobAuthToken,insightNameKey, testNegativeScenario, insightNameEdit, insightTitle, container,
				cobrandId, keys, ruleExpression, description, applicableEntity,	entityParametersCount, entityParameterName, totalThresholds,id,
				frequency, isSubscribed, duration, insightType,threshold_Name_Value_Type);
		Response patchCobrandSubscriptionResponse = insightUtils.patchCobrandSubscriptions(cobHttpParams,null);
		Response getCobSubscriptionResponseAfterPatch = insightUtils.getCobrandSubscriptions(cobHttpParams,null);
		String testStatus = insightsGenerics.updateCobrandSubscriptionResponseVerificationPostPatch(testCaseId,testExecutionStatus,testSummary,patchCobrandSubscriptionResponse,negativeExpectedValuesMap,testNegativeScenario, testCaseDescription, getCobSubscriptionResponseAfterPatch, insightNameKey, frequency ,duration,insightType,insightTitle, entityParameterName, isSubscribed, threshold_Name_Value_Type,expectedStatus,failureReason,testInfo);
		notificationsSummary(Boolean.valueOf(testStatus),testInfo,testCaseId,insightNameKey,testCaseDescription);
		if(!Boolean.valueOf(testStatus))
			Assert.fail(failureReason.getFailureReason());
		
	}

	@Test(enabled = true, dataProvider = "feeder")
	@Source("\\TestData\\CSVFiles\\Insights\\ExpenseExceededBudget\\PatchCobSubscriptionForExpenseExceededBudget.csv")
	public void testPatchCobrandSubscriptionForExpenseExceedeBudget(String testCaseId ,String testCaseDescription,String insightNameKey,String testNegativeScenario,String insightNameEdit,
			String insightTitle,String container,String cobrandId,String keys,String ruleExpression,String description,String applicableEntity,
			String entityParametersCount,String congigMeta,String entityParameterName,String totalThresholds,String id,String frequency,String isSubscribed,String duration,String insightType,
			String threshold_Name_Value_Type,int expectedStatus,String testEnabled) throws ParseException, InterruptedException, JoseException {

		commontUtils.isTCEnabled(testEnabled, testCaseId);
		executedInsight=insightNameKey;
		boolean testExecutionStatus = false;
		logger.info("Testcase--> "+testCaseId+ "->"+"  Validate PATCH "+insightNameKey +"  Cobrand Subscription ,"+testCaseDescription);
		/*HttpMethodParameters cobHttpParams = insightsGenerics.updateCobrandSubscriptionProcessing(jwtCobAuthToken,insightNameKey, testNegativeScenario, insightNameEdit, insightTitle, container,
				cobrandId, keys, ruleExpression, description, applicableEntity,	entityParametersCount, entityParameterName, totalThresholds,id,
				frequency, isSubscribed, duration, insightType,threshold_Name_Value_Type);
		Response getCobSubscriptionResponseBeforPatch = insightUtils.getCobrandSubscriptions(cobHttpParams);
		Response patchCobrandSubscriptionResponse = insightUtils.patchCobrandSubscriptions(cobHttpParams);
		Response getCobSubscriptionResponseAfterPatch = insightUtils.getCobrandSubscriptions(cobHttpParams);
		// verify GET user subscription response for only editable attributes
		insightsGenerics.updateCobrandSubscriptionResponseVerificationPostPatch(testCaseId,testExecutionStatus,testSummary,patchCobrandSubscriptionResponse,negativeExpectedValuesMap,testNegativeScenario, testCaseDescription, getCobSubscriptionResponseAfterPatch, insightNameKey, frequency ,duration,insightType,insightTitle, entityParameterName, isSubscribed, threshold_Name_Value_Type,expectedStatus,failureReason);
	*/}

	@Test(enabled = true, dataProvider = "feeder")
	@Source("\\TestData\\CSVFiles\\Insights\\upcomingBills\\PatchCobSubscriptionForUpcomingBills.csv")
	public void testPatchCobrandSubscriptionForUpcomingBills(String testCaseId ,String testCaseDescription,String insightNameKey,String testNegativeScenario,String insightNameEdit,
			String insightTitle,String container,String cobrandId,String validateKeys,String ruleExpression,String description,String applicableEntity,
			String entityParametersCount,String congigMeta,String entityParameterName,String totalThresholds,String id,String frequency,String isSubscribed,String duration,String insightType,
			String threshold_Name_Value_Type,int expectedStatus,String testEnabled) throws ParseException, InterruptedException, JoseException,IOException {

		commontUtils.isTCEnabled(testEnabled, testCaseId);
		logger.info("Testcase--> "+testCaseId+ "->"+"  Validate PATCH "+insightNameKey +"  Cobrand Subscription ,"+testCaseDescription);
		automationTestResults.setInsights("PATCH_SUB_UpcomingBills");
		automationTestResults.setBuildNo(buildNo);
		storyId = testCaseId.substring(0,  7);
		TestInfo testInfo = new TestInfo();
		testInfo.setTestCaseId(testCaseId);
		testInfo.setTestDescription(testCaseDescription);
		executedInsight=insightNameKey;
		boolean testExecutionStatus = false;
		HttpMethodParameters cobHttpParams = insightsGenerics.updateCobrandSubscriptionProcessing(jwtCobAuthToken,insightNameKey, testNegativeScenario, insightNameEdit, insightTitle, container,
				cobrandId, validateKeys, ruleExpression, description, applicableEntity,	entityParametersCount, entityParameterName, totalThresholds,id,
				frequency, isSubscribed, duration, insightType,threshold_Name_Value_Type);
		Response patchCobrandSubscriptionResponse = insightUtils.patchCobrandSubscriptions(cobHttpParams,null);
		Response getCobSubscriptionResponseAfterPatch = insightUtils.getCobrandSubscriptions(cobHttpParams,null);
		String testStatus = insightsGenerics.updateCobrandSubscriptionResponseVerificationPostPatch(testCaseId,testExecutionStatus,testSummary,patchCobrandSubscriptionResponse,negativeExpectedValuesMap,testNegativeScenario, testCaseDescription, getCobSubscriptionResponseAfterPatch, insightNameKey, frequency ,duration,insightType,insightTitle, entityParameterName, isSubscribed, threshold_Name_Value_Type,expectedStatus,failureReason,testInfo);
		notificationsSummary(Boolean.valueOf(testStatus),testInfo,testCaseId,insightNameKey,testCaseDescription);
		if(!Boolean.valueOf(testStatus))
			Assert.fail();
	}

	@Test(enabled = true, dataProvider = "feeder")
	@Source("\\TestData\\CSVFiles\\Insights\\upcomingSubscription\\PatchCobSubscriptionForUpcomingSubscription.csv")
	public void testPatchCobrandSubscriptionForUpcomingSubcription(String testCaseId ,String testCaseDescription,String insightNameKey,String testNegativeScenario,String insightNameEdit,
			String insightTitle,String container,String cobrandId,String validateKeys,String ruleExpression,String description,String applicableEntity,
			String entityParametersCount,String congigMeta,String entityParameterName,String totalThresholds,String id,String frequency,String isSubscribed,String duration,String insightType,
			String threshold_Name_Value_Type,int expectedStatus,String testEnabled) throws ParseException, InterruptedException, JoseException,IOException {

		commontUtils.isTCEnabled(testEnabled, testCaseId);
		logger.info("Testcase--> "+testCaseId+ "->"+"  Validate PATCH "+insightNameKey +"  Cobrand Subscription ,"+testCaseDescription);
		automationTestResults.setInsights("PATCH_SUB_UpcomingSubcription");
		automationTestResults.setBuildNo(buildNo);
		storyId = testCaseId.substring(0,  7);
		TestInfo testInfo = new TestInfo();
		testInfo.setTestCaseId(testCaseId);
		testInfo.setTestDescription(testCaseDescription);
		executedInsight=insightNameKey;
		boolean testExecutionStatus = false;
		HttpMethodParameters cobHttpParams = insightsGenerics.updateCobrandSubscriptionProcessing(jwtCobAuthToken,insightNameKey, testNegativeScenario, insightNameEdit, insightTitle, container,
				cobrandId, validateKeys, ruleExpression, description, applicableEntity,	entityParametersCount, entityParameterName, totalThresholds,id,
				frequency, isSubscribed, duration, insightType,threshold_Name_Value_Type);
		Response patchCobrandSubscriptionResponse = insightUtils.patchCobrandSubscriptions(cobHttpParams,null);
		Response getCobSubscriptionResponseAfterPatch = insightUtils.getCobrandSubscriptions(cobHttpParams,null);
		String testStatus = insightsGenerics.updateCobrandSubscriptionResponseVerificationPostPatch(testCaseId,testExecutionStatus,testSummary,patchCobrandSubscriptionResponse,negativeExpectedValuesMap,testNegativeScenario, testCaseDescription, getCobSubscriptionResponseAfterPatch, insightNameKey, frequency ,duration,insightType,insightTitle, entityParameterName, isSubscribed, threshold_Name_Value_Type,expectedStatus,failureReason,testInfo);
		notificationsSummary(Boolean.valueOf(testStatus),testInfo,testCaseId,insightNameKey,testCaseDescription);
		if(!Boolean.valueOf(testStatus))
			Assert.fail();
	}

	@Test(enabled = true, dataProvider = "feeder")
	@Source("\\TestData\\CSVFiles\\Insights\\NewBillEventDetected\\PatchCobSubscriptionForNewBill.csv")
	public void testPatchCobrandSubscriptionForNewBillEventDetected(String testCaseId ,String testCaseDescription,String insightNameKey,String testNegativeScenario,String insightNameEdit,
			String insightTitle,String container,String cobrandId,String validateKeys,String ruleExpression,String description,String applicableEntity,
			String entityParametersCount,String congigMeta,String entityParameterName,String totalThresholds,String id,String frequency,String isSubscribed,String duration,String insightType,
			String threshold_Name_Value_Type,int expectedStatus,String testEnabled) throws ParseException, InterruptedException, JoseException,IOException {

		commontUtils.isTCEnabled(testEnabled, testCaseId);
		logger.info("Testcase--> "+testCaseId+ "->"+"  Validate PATCH "+insightNameKey +"  Cobrand Subscription ,"+testCaseDescription);
		automationTestResults.setInsights("PATCH_SUB_NewBill");
		automationTestResults.setBuildNo(buildNo);
		storyId = testCaseId.substring(0,  7);
		TestInfo testInfo = new TestInfo();
		testInfo.setTestCaseId(testCaseId);
		testInfo.setTestDescription(testCaseDescription);
		executedInsight=insightNameKey;
		boolean testExecutionStatus = false;
		HttpMethodParameters cobHttpParams = insightsGenerics.updateCobrandSubscriptionProcessing(jwtCobAuthToken,insightNameKey, testNegativeScenario, insightNameEdit, insightTitle, container,
				cobrandId, validateKeys, ruleExpression, description, applicableEntity,	entityParametersCount, entityParameterName, totalThresholds,id,
				frequency, isSubscribed, duration, insightType,threshold_Name_Value_Type);
		Response patchCobrandSubscriptionResponse = insightUtils.patchCobrandSubscriptions(cobHttpParams,null);
		Response getCobSubscriptionResponseAfterPatch = insightUtils.getCobrandSubscriptions(cobHttpParams,null);
		String testStatus = insightsGenerics.updateCobrandSubscriptionResponseVerificationPostPatch(testCaseId,testExecutionStatus,testSummary,patchCobrandSubscriptionResponse,negativeExpectedValuesMap,testNegativeScenario, testCaseDescription, getCobSubscriptionResponseAfterPatch, insightNameKey, frequency ,duration,insightType,insightTitle, entityParameterName, isSubscribed, threshold_Name_Value_Type,expectedStatus,failureReason,testInfo);
		notificationsSummary(Boolean.valueOf(testStatus),testInfo,testCaseId,insightNameKey,testCaseDescription);
		if(!Boolean.valueOf(testStatus))
			Assert.fail();
	}

	@Test(enabled = true, dataProvider = "feeder")
	@Source("\\TestData\\CSVFiles\\Insights\\NewSubscriptionEventDetected\\PatchCobSubscriptionForNewSubscription.csv")
	public void testPatchCobrandSubscriptionForNewSubscriptionEventDetected(String testCaseId ,String testCaseDescription,String insightNameKey,String testNegativeScenario,String insightNameEdit,
			String insightTitle,String container,String cobrandId,String validateKeys,String ruleExpression,String description,String applicableEntity,
			String entityParametersCount,String congigMeta,String entityParameterName,String totalThresholds,String id,String frequency,String isSubscribed,String duration,String insightType,
			String threshold_Name_Value_Type,int expectedStatus,String testEnabled) throws ParseException, InterruptedException, JoseException,IOException {

		commontUtils.isTCEnabled(testEnabled, testCaseId);
		logger.info("Testcase--> "+testCaseId+ "->"+"  Validate PATCH "+insightNameKey +"  Cobrand Subscription ,"+testCaseDescription);
		automationTestResults.setInsights("PATCH_SUB_NewSubscription");
		automationTestResults.setBuildNo(buildNo);
		storyId = testCaseId.substring(0,  7);
		TestInfo testInfo = new TestInfo();
		testInfo.setTestCaseId(testCaseId);
		testInfo.setTestDescription(testCaseDescription);
		executedInsight=insightNameKey;
		boolean testExecutionStatus = false;
		HttpMethodParameters cobHttpParams = insightsGenerics.updateCobrandSubscriptionProcessing(jwtCobAuthToken,insightNameKey, testNegativeScenario, insightNameEdit, insightTitle, container,
				cobrandId, validateKeys, ruleExpression, description, applicableEntity,	entityParametersCount, entityParameterName, totalThresholds,id,
				frequency, isSubscribed, duration, insightType,threshold_Name_Value_Type);
		Response patchCobrandSubscriptionResponse = insightUtils.patchCobrandSubscriptions(cobHttpParams,null);
		Response getCobSubscriptionResponseAfterPatch = insightUtils.getCobrandSubscriptions(cobHttpParams,null);
		String testStatus = insightsGenerics.updateCobrandSubscriptionResponseVerificationPostPatch(testCaseId,testExecutionStatus,testSummary,patchCobrandSubscriptionResponse,negativeExpectedValuesMap,testNegativeScenario, testCaseDescription, getCobSubscriptionResponseAfterPatch, insightNameKey, frequency ,duration,insightType,insightTitle, entityParameterName, isSubscribed, threshold_Name_Value_Type,expectedStatus,failureReason,testInfo);
		notificationsSummary(Boolean.valueOf(testStatus),testInfo,testCaseId,insightNameKey,testCaseDescription);
		if(!Boolean.valueOf(testStatus))
			Assert.fail();
	}

	@Test(enabled = true, dataProvider = "feeder")
	@Source("\\TestData\\CSVFiles\\Insights\\BillNotPaid\\PatchCobSubscriptionForBillNotPaid.csv")
	public void testPatchCobrandSubscriptionForBillNotPaid(String testCaseId ,String testCaseDescription,String insightNameKey,String testNegativeScenario,String insightNameEdit,
			String insightTitle,String container,String cobrandId,String validateKeys,String ruleExpression,String description,String applicableEntity,
			String entityParametersCount,String congigMeta,String entityParameterName,String totalThresholds,String id,String frequency,String isSubscribed,String duration,String insightType,
			String threshold_Name_Value_Type,int expectedStatus,String testEnabled) throws ParseException, InterruptedException, JoseException,IOException {

		commontUtils.isTCEnabled(testEnabled, testCaseId);
		logger.info("Testcase--> "+testCaseId+ "->"+"  Validate PATCH "+insightNameKey +"  Cobrand Subscription ,"+testCaseDescription);
		automationTestResults.setInsights("PATCH_SUB_BillNotPaid");
		automationTestResults.setBuildNo(buildNo);
		storyId = testCaseId.substring(0,  7);
		TestInfo testInfo = new TestInfo();
		testInfo.setTestCaseId(testCaseId);
		testInfo.setTestDescription(testCaseDescription);
		executedInsight=insightNameKey;
		boolean testExecutionStatus = false;
		HttpMethodParameters cobHttpParams = insightsGenerics.updateCobrandSubscriptionProcessing(jwtCobAuthToken,insightNameKey, testNegativeScenario, insightNameEdit, insightTitle, container,
				cobrandId, validateKeys, ruleExpression, description, applicableEntity,	entityParametersCount, entityParameterName, totalThresholds,id,
				frequency, isSubscribed, duration, insightType,threshold_Name_Value_Type);
		Response patchCobrandSubscriptionResponse = insightUtils.patchCobrandSubscriptions(cobHttpParams,null);
		Response getCobSubscriptionResponseAfterPatch = insightUtils.getCobrandSubscriptions(cobHttpParams,null);
		String testStatus = insightsGenerics.updateCobrandSubscriptionResponseVerificationPostPatch(testCaseId,testExecutionStatus,testSummary,patchCobrandSubscriptionResponse,negativeExpectedValuesMap,testNegativeScenario, testCaseDescription, getCobSubscriptionResponseAfterPatch, insightNameKey, frequency ,duration,insightType,insightTitle, entityParameterName, isSubscribed, threshold_Name_Value_Type,expectedStatus,failureReason,testInfo);
		notificationsSummary(Boolean.valueOf(testStatus),testInfo,testCaseId,insightNameKey,testCaseDescription);
		if(!Boolean.valueOf(testStatus))
			Assert.fail();
	}

	@Test(enabled = true, dataProvider = "feeder")
	@Source("\\TestData\\CSVFiles\\Insights\\FinancialFees\\PatchCobSubscriptionFinancialFees.csv")
	public void testPatchCobrandSubscriptionForFinancialFees(String testCaseId ,String testCaseDescription,String insightNameKey,String testNegativeScenario,String insightNameEdit,
			String insightTitle,String container,String cobrandId,String validateKeys,String ruleExpression,String description,String applicableEntity,
			String entityParametersCount,String congigMeta,String entityParameterName,String totalThresholds,String id,String frequency,String isSubscribed,String duration,String insightType,
			String threshold_Name_Value_Type,int expectedStatus,String testEnabled) throws ParseException, InterruptedException, JoseException, IOException {
		commontUtils.isTCEnabled(testEnabled, testCaseId);
		logger.info("Testcase--> "+testCaseId+ "->"+"  Validate PATCH "+insightNameKey +"  Cobrand Subscription ,"+testCaseDescription);
		automationTestResults.setInsights("PATCH_SUB_FinancialFees");
		automationTestResults.setBuildNo(buildNo);
		storyId = testCaseId.substring(0,  7);
		TestInfo testInfo = new TestInfo();
		testInfo.setTestCaseId(testCaseId);
		testInfo.setTestDescription(testCaseDescription);
		executedInsight=insightNameKey;
		boolean testExecutionStatus = false;
		HttpMethodParameters cobHttpParams = insightsGenerics.updateCobrandSubscriptionProcessing(jwtCobAuthToken,insightNameKey, testNegativeScenario, insightNameEdit, insightTitle, container,
				cobrandId, validateKeys, ruleExpression, description, applicableEntity,	entityParametersCount, entityParameterName, totalThresholds,id,
				frequency, isSubscribed, duration, insightType,threshold_Name_Value_Type);
		Response patchCobrandSubscriptionResponse = insightUtils.patchCobrandSubscriptions(cobHttpParams,null);
		Response getCobSubscriptionResponseAfterPatch = insightUtils.getCobrandSubscriptions(cobHttpParams,null);
		String testStatus = insightsGenerics.updateCobrandSubscriptionResponseVerificationPostPatch(testCaseId,testExecutionStatus,testSummary,patchCobrandSubscriptionResponse,negativeExpectedValuesMap,testNegativeScenario, testCaseDescription, getCobSubscriptionResponseAfterPatch, insightNameKey, frequency ,duration,insightType,insightTitle, entityParameterName, isSubscribed, threshold_Name_Value_Type,expectedStatus,failureReason,testInfo);
		notificationsSummary(Boolean.valueOf(testStatus),testInfo,testCaseId,insightNameKey,testCaseDescription);
		if(!Boolean.valueOf(testStatus))
			Assert.fail();
	}
	
	@Test(enabled = true, dataProvider = "feeder")
	@Source("\\TestData\\CSVFiles\\Insights\\SpendingbyCategory\\PatchCobSubscriptionSpendingbyCategory.csv")
	public void testPatchCobrandSubscriptionForSpendingbyCategory(String testCaseId ,String testCaseDescription,String insightNameKey,String testNegativeScenario,String insightNameEdit,
			String insightTitle,String container,String cobrandId,String validateKeys,String ruleExpression,String description,String applicableEntity,
			String entityParametersCount,String congigMeta,String entityParameterName,String totalThresholds,String id,String frequency,String isSubscribed,String duration,String insightType,
			String threshold_Name_Value_Type,int expectedStatus,String testEnabled) throws ParseException, InterruptedException, JoseException, IOException {
		commontUtils.isTCEnabled(testEnabled, testCaseId);
		logger.info("Testcase--> "+testCaseId+ "->"+"  Validate PATCH "+insightNameKey +"  Cobrand Subscription ,"+testCaseDescription);
		automationTestResults.setInsights("PATCH_SUB_SpendingbyCategory");
		automationTestResults.setBuildNo(buildNo);
		storyId = testCaseId.substring(0,  7);
		TestInfo testInfo = new TestInfo();
		testInfo.setTestCaseId(testCaseId);
		testInfo.setTestDescription(testCaseDescription);
		executedInsight=insightNameKey;
		boolean testExecutionStatus = false;				
		HttpMethodParameters cobHttpParams = insightsGenerics.updateCobrandSubscriptionProcessing(jwtCobAuthToken,insightNameKey, testNegativeScenario, insightNameEdit, insightTitle, container,
				cobrandId, validateKeys, ruleExpression, description, applicableEntity,	entityParametersCount, entityParameterName, totalThresholds,id, 
				frequency, isSubscribed, duration, insightType,threshold_Name_Value_Type);
		Response patchCobrandSubscriptionResponse = insightUtils.patchCobrandSubscriptions(cobHttpParams,null);
		Response getCobSubscriptionResponseAfterPatch = insightUtils.getCobrandSubscriptions(cobHttpParams,null);		
		String testStatus = insightsGenerics.updateCobrandSubscriptionResponseVerificationPostPatch(testCaseId,testExecutionStatus,testSummary,patchCobrandSubscriptionResponse,negativeExpectedValuesMap,testNegativeScenario, testCaseDescription, getCobSubscriptionResponseAfterPatch, insightNameKey, frequency ,duration,insightType,insightTitle, entityParameterName, isSubscribed, threshold_Name_Value_Type,expectedStatus,failureReason,testInfo);
		notificationsSummary(Boolean.valueOf(testStatus),testInfo,testCaseId,insightNameKey,testCaseDescription);		
		if(!Boolean.valueOf(testStatus))
			Assert.fail();
	}
	
	@Test(enabled = true, dataProvider = "feeder")
	@Source("\\TestData\\CSVFiles\\Insights\\TopMerchants\\PatchCobSubscriptionTopMerchants.csv")
	public void testPatchCobrandSubscriptionForTopMerchants(String testCaseId ,String testCaseDescription,String insightNameKey,String testNegativeScenario,String insightNameEdit,
			String insightTitle,String container,String cobrandId,String validateKeys,String ruleExpression,String description,String applicableEntity,
			String entityParametersCount,String congigMeta,String entityParameterName,String totalThresholds,String id,String frequency,String isSubscribed,String duration,String insightType,
			String threshold_Name_Value_Type,int expectedStatus,String testEnabled) throws ParseException, InterruptedException, JoseException, IOException {
		commontUtils.isTCEnabled(testEnabled, testCaseId);
		logger.info("Testcase--> "+testCaseId+ "->"+"  Validate PATCH "+insightNameKey +"  Cobrand Subscription ,"+testCaseDescription);
		automationTestResults.setInsights("PATCH_SUB_TopMerchants");
		automationTestResults.setBuildNo(buildNo);
		storyId = testCaseId.substring(0,  7);
		TestInfo testInfo = new TestInfo();
		testInfo.setTestCaseId(testCaseId);
		testInfo.setTestDescription(testCaseDescription);
		executedInsight=insightNameKey;
		boolean testExecutionStatus = false;				
		HttpMethodParameters cobHttpParams = insightsGenerics.updateCobrandSubscriptionProcessing(jwtCobAuthToken,insightNameKey, testNegativeScenario, insightNameEdit, insightTitle, container,
				cobrandId, validateKeys, ruleExpression, description, applicableEntity,	entityParametersCount, entityParameterName, totalThresholds,id, 
				frequency, isSubscribed, duration, insightType,threshold_Name_Value_Type);
		Response patchCobrandSubscriptionResponse = insightUtils.patchCobrandSubscriptions(cobHttpParams,null);
		Response getCobSubscriptionResponseAfterPatch = insightUtils.getCobrandSubscriptions(cobHttpParams,null);		
		String testStatus = insightsGenerics.updateCobrandSubscriptionResponseVerificationPostPatch(testCaseId,testExecutionStatus,testSummary,patchCobrandSubscriptionResponse,negativeExpectedValuesMap,testNegativeScenario, testCaseDescription, getCobSubscriptionResponseAfterPatch, insightNameKey, frequency ,duration,insightType,insightTitle, entityParameterName, isSubscribed, threshold_Name_Value_Type,expectedStatus,failureReason,testInfo);
		notificationsSummary(Boolean.valueOf(testStatus),testInfo,testCaseId,insightNameKey,testCaseDescription);		
		if(!Boolean.valueOf(testStatus))
			Assert.fail();
	}
	
	@Test(enabled = true, dataProvider = "feeder")
	@Source("\\TestData\\CSVFiles\\Insights\\IncomeExpenseOverview\\PatchCobSubscriptionForIncomeExpenseOverview.csv")
	public void testPatchCobrandSubscriptionForIncomeExpenseOverview(String testCaseId ,String testCaseDescription,String insightNameKey,String testNegativeScenario,String insightNameEdit,
			String insightTitle,String container,String cobrandId,String validateKeys,String ruleExpression,String description,String applicableEntity,
			String entityParametersCount,String congigMeta,String entityParameterName,String totalThresholds,String id,String frequency,String isSubscribed,String duration,String insightType,
			String threshold_Name_Value_Type,int expectedStatus,String testEnabled) throws ParseException, InterruptedException, JoseException,IOException {

		commontUtils.isTCEnabled(testEnabled, testCaseId);
		logger.info("Testcase--> "+testCaseId+ "->"+"  Validate PATCH "+insightNameKey +"  Cobrand Subscription ,"+testCaseDescription);
		automationTestResults.setInsights("PATCH_IncomeExpenseOverview");
		automationTestResults.setBuildNo(buildNo);
		storyId = testCaseId.substring(0,  7);
		TestInfo testInfo = new TestInfo();
		testInfo.setTestCaseId(testCaseId);
		testInfo.setTestDescription(testCaseDescription);
		executedInsight=insightNameKey;
		boolean testExecutionStatus = false;				
		HttpMethodParameters cobHttpParams = insightsGenerics.updateCobrandSubscriptionProcessing(jwtCobAuthToken,insightNameKey, testNegativeScenario, insightNameEdit, insightTitle, container,
				cobrandId, validateKeys, ruleExpression, description, applicableEntity,	entityParametersCount, entityParameterName, totalThresholds,id, 
				frequency, isSubscribed, duration, insightType,threshold_Name_Value_Type);
		Response patchCobrandSubscriptionResponse = insightUtils.patchCobrandSubscriptions(cobHttpParams,null);
	    Response getCobSubscriptionResponseAfterPatch = insightUtils.getCobrandSubscriptions(cobHttpParams,null);		
		String testStatus = insightsGenerics.updateCobrandSubscriptionResponseVerificationPostPatch(testCaseId,testExecutionStatus,testSummary,patchCobrandSubscriptionResponse,negativeExpectedValuesMap,testNegativeScenario, testCaseDescription, getCobSubscriptionResponseAfterPatch, insightNameKey, frequency ,duration,insightType,insightTitle, entityParameterName, isSubscribed, threshold_Name_Value_Type,expectedStatus,failureReason,testInfo);
		notificationsSummary(Boolean.valueOf(testStatus),testInfo,testCaseId,insightNameKey,testCaseDescription);		
		if(!Boolean.valueOf(testStatus))
			Assert.fail();
	}
	
	@Test(enabled = true, dataProvider = "feeder")
	@Source("\\TestData\\CSVFiles\\Insights\\BudgetProgress\\PatchCobSubscriptionForBudgetProgress.csv")
	public void testPatchCobrandSubscriptionForBudgetProgress(String testCaseId ,String testCaseDescription,String insightNameKey,String testNegativeScenario,String insightNameEdit,
			String insightTitle,String container,String cobrandId,String validateKeys,String ruleExpression,String description,String applicableEntity,
			String entityParametersCount,String congigMeta,String entityParameterName,String totalThresholds,String id,String frequency,String isSubscribed,String duration,String insightType,
			String threshold_Name_Value_Type,int expectedStatus,String testEnabled) throws ParseException, InterruptedException, JoseException,IOException {

		commontUtils.isTCEnabled(testEnabled, testCaseId);
		logger.info("Testcase--> "+testCaseId+ "->"+"  Validate PATCH "+insightNameKey +"  Cobrand Subscription ,"+testCaseDescription);
		automationTestResults.setInsights("PATCH_BudgetProgress");
		automationTestResults.setBuildNo(buildNo);
		storyId = testCaseId.substring(0,  7);
		TestInfo testInfo = new TestInfo();
		testInfo.setTestCaseId(testCaseId);
		testInfo.setTestDescription(testCaseDescription);
		executedInsight=insightNameKey;
		boolean testExecutionStatus = false;				
		HttpMethodParameters cobHttpParams = insightsGenerics.updateCobrandSubscriptionProcessing(jwtCobAuthToken,insightNameKey, testNegativeScenario, insightNameEdit, insightTitle, container,
				cobrandId, validateKeys, ruleExpression, description, applicableEntity,	entityParametersCount, entityParameterName, totalThresholds,id, 
				frequency, isSubscribed, duration, insightType,threshold_Name_Value_Type);
		Response patchCobrandSubscriptionResponse = insightUtils.patchCobrandSubscriptions(cobHttpParams,null);
	    Response getCobSubscriptionResponseAfterPatch = insightUtils.getCobrandSubscriptions(cobHttpParams,null);		
		String testStatus = insightsGenerics.updateCobrandSubscriptionResponseVerificationPostPatch(testCaseId,testExecutionStatus,testSummary,patchCobrandSubscriptionResponse,negativeExpectedValuesMap,testNegativeScenario, testCaseDescription, getCobSubscriptionResponseAfterPatch, insightNameKey, frequency ,duration,insightType,insightTitle, entityParameterName, isSubscribed, threshold_Name_Value_Type,expectedStatus,failureReason,testInfo);
		notificationsSummary(Boolean.valueOf(testStatus),testInfo,testCaseId,insightNameKey,testCaseDescription);		
		if(!Boolean.valueOf(testStatus))
			Assert.fail();
	}
	
	@Test(enabled = true, dataProvider = "feeder")
	@Source("\\TestData\\CSVFiles\\Insights\\RefundDeposit\\PatchCobSubscriptionForRefundDeposit.csv")
	public void testPatchCobrandSubscriptionForRefundDeposit(String testCaseId ,String testCaseDescription,String insightNameKey,String testNegativeScenario,String insightNameEdit,
			String insightTitle,String container,String cobrandId,String validateKeys,String ruleExpression,String description,String applicableEntity,
			String entityParametersCount,String congigMeta,String entityParameterName,String totalThresholds,String id,String frequency,String isSubscribed,String duration,String insightType,
			String threshold_Name_Value_Type,int expectedStatus,String testEnabled) throws ParseException, InterruptedException, JoseException,IOException {

		commontUtils.isTCEnabled(testEnabled, testCaseId);
		logger.info("Testcase--> "+testCaseId+ "->"+"  Validate PATCH "+insightNameKey +"  Cobrand Subscription ,"+testCaseDescription);
		automationTestResults.setInsights("PATCH_SUB_RefundDeposit");
		automationTestResults.setBuildNo(buildNo);
		storyId = testCaseId.substring(0,  7);
		TestInfo testInfo = new TestInfo();
		testInfo.setTestCaseId(testCaseId);
		testInfo.setTestDescription(testCaseDescription);
		executedInsight=insightNameKey;
		boolean testExecutionStatus = false;				
		HttpMethodParameters cobHttpParams = insightsGenerics.updateCobrandSubscriptionProcessing(jwtCobAuthToken,insightNameKey, testNegativeScenario, insightNameEdit, insightTitle, container,
				cobrandId, validateKeys, ruleExpression, description, applicableEntity,	entityParametersCount, entityParameterName, totalThresholds,id, 
				frequency, isSubscribed, duration, insightType,threshold_Name_Value_Type);
		Response patchCobrandSubscriptionResponse = insightUtils.patchCobrandSubscriptions(cobHttpParams,null);
		Response getCobSubscriptionResponseAfterPatch = insightUtils.getCobrandSubscriptions(cobHttpParams,null);		
		String testStatus = insightsGenerics.updateCobrandSubscriptionResponseVerificationPostPatch(testCaseId,testExecutionStatus,testSummary,patchCobrandSubscriptionResponse,negativeExpectedValuesMap,testNegativeScenario, testCaseDescription, getCobSubscriptionResponseAfterPatch, insightNameKey, frequency ,duration,insightType,insightTitle, entityParameterName, isSubscribed, threshold_Name_Value_Type,expectedStatus,failureReason,testInfo);
		notificationsSummary(Boolean.valueOf(testStatus),testInfo,testCaseId,insightNameKey,testCaseDescription);		
		if(!Boolean.valueOf(testStatus))
			Assert.fail();
	}
	@Test(enabled = true, dataProvider = "feeder")
	@Source("\\TestData\\CSVFiles\\Insights\\MostFrequentPurchases\\PatchCobSubscriptionForMostFrequentPurchases.csv")
	public void testPatchCobrandSubscriptionForMostFrequentPurchases(String testCaseId ,String testCaseDescription,String insightNameKey,String testNegativeScenario,String insightNameEdit,
			String insightTitle,String container,String cobrandId,String validateKeys,String ruleExpression,String description,String applicableEntity,
			String entityParametersCount,String congigMeta,String entityParameterName,String totalThresholds,String id,String frequency,String isSubscribed,String duration,String insightType,
			String threshold_Name_Value_Type,int expectedStatus,String testEnabled) throws ParseException, InterruptedException, JoseException,IOException {

		commontUtils.isTCEnabled(testEnabled, testCaseId);
		logger.info("Testcase--> "+testCaseId+ "->"+"  Validate PATCH "+insightNameKey +"  Cobrand Subscription ,"+testCaseDescription);
		automationTestResults.setInsights("PATCH_SUB_MostFrequentPurchases");
		automationTestResults.setBuildNo(buildNo);
		storyId = testCaseId.substring(0,  7);
		TestInfo testInfo = new TestInfo();
		testInfo.setTestCaseId(testCaseId);
		executedInsight=insightNameKey;
		boolean testExecutionStatus = false;				
		HttpMethodParameters cobHttpParams = insightsGenerics.updateCobrandSubscriptionProcessing(jwtCobAuthToken,insightNameKey, testNegativeScenario, insightNameEdit, insightTitle, container,
				cobrandId, validateKeys, ruleExpression, description, applicableEntity,	entityParametersCount, entityParameterName, totalThresholds,id, 
				frequency, isSubscribed, duration, insightType,threshold_Name_Value_Type);
		Response patchCobrandSubscriptionResponse = insightUtils.patchCobrandSubscriptions(cobHttpParams,null);
		Response getCobSubscriptionResponseAfterPatch = insightUtils.getCobrandSubscriptions(cobHttpParams,null);		
		String testStatus = insightsGenerics.updateCobrandSubscriptionResponseVerificationPostPatch(testCaseId,testExecutionStatus,testSummary,patchCobrandSubscriptionResponse,negativeExpectedValuesMap,testNegativeScenario, testCaseDescription, getCobSubscriptionResponseAfterPatch, insightNameKey, frequency ,duration,insightType,insightTitle, entityParameterName, isSubscribed, threshold_Name_Value_Type,expectedStatus,failureReason,testInfo);
		notificationsSummary(Boolean.valueOf(testStatus),testInfo,testCaseId,insightNameKey,testCaseDescription);		
		if(!Boolean.valueOf(testStatus))
			Assert.fail();
	}
	@Test(enabled = true, dataProvider = "feeder")
	@Source("\\TestData\\CSVFiles\\Insights\\ProjectedLowBalance\\PatchCobSubscriptionForProjectedLowBalance.csv")
	public void testPatchCobrandSubscriptionForProjectedLowBalance(String testCaseId ,String testCaseDescription,String insightNameKey,String testNegativeScenario,String insightNameEdit,
			String insightTitle,String container,String cobrandId,String keys,String ruleExpression,String description,String applicableEntity,
			String entityParametersCount,String congigMeta,String entityParameterName,String totalThresholds,String id,String frequency,String isSubscribed,String duration,String insightType,
			String threshold_Name_Value_Type,int expectedStatus,String testEnabled) throws ParseException, InterruptedException, JoseException, IOException {

		commontUtils.isTCEnabled(testEnabled, testCaseId);
		logger.info("Testcase--> "+testCaseId+ "->"+"  Validate PATCH "+insightNameKey +"  Cobrand Subscription ,"+testCaseDescription);
		automationTestResults.setInsights("PATCH_SUB_ProjectedLowBalance");
		automationTestResults.setBuildNo(buildNo);
		storyId = testCaseId.substring(0,  7);
		TestInfo testInfo = new TestInfo();
		testInfo.setTestCaseId(testCaseId);
		testInfo.setTestDescription(testCaseDescription);
		executedInsight=insightNameKey;
		boolean testExecutionStatus = false;				
		HttpMethodParameters cobHttpParams = insightsGenerics.updateCobrandSubscriptionProcessing(jwtCobAuthToken,insightNameKey, testNegativeScenario, insightNameEdit, insightTitle, container,
				cobrandId, keys, ruleExpression, description, applicableEntity,	entityParametersCount, entityParameterName, totalThresholds,id, 
				frequency, isSubscribed, duration, insightType,threshold_Name_Value_Type);
		Response patchCobrandSubscriptionResponse = insightUtils.patchCobrandSubscriptions(cobHttpParams,null);
		Response getCobSubscriptionResponseAfterPatch = insightUtils.getCobrandSubscriptions(cobHttpParams,null);		
		String testStatus = insightsGenerics.updateCobrandSubscriptionResponseVerificationPostPatch(testCaseId,testExecutionStatus,testSummary,patchCobrandSubscriptionResponse,negativeExpectedValuesMap,testNegativeScenario, testCaseDescription, getCobSubscriptionResponseAfterPatch, insightNameKey, frequency ,duration,insightType,insightTitle, entityParameterName, isSubscribed, threshold_Name_Value_Type,expectedStatus,failureReason,testInfo);
		notificationsSummary(Boolean.valueOf(testStatus),testInfo,testCaseId,insightNameKey,testCaseDescription);	
		if(!Boolean.valueOf(testStatus))
			Assert.fail();
	}
	
	@Test(enabled = true, dataProvider = "feeder")
	@Source("\\TestData\\CSVFiles\\Insights\\CategorySpendingExceeded\\PatchCobSubscriptionForCategorySpendingExceeded.csv")
	public void testPatchCobrandSubscriptionForCategorySpendingExceeded(String testCaseId ,String testCaseDescription,String insightNameKey,String testNegativeScenario,String insightNameEdit,
			String insightTitle,String container,String cobrandId,String keys,String ruleExpression,String description,String applicableEntity,
			String entityParametersCount,String congigMeta,String entityParameterName,String totalThresholds,String id,String frequency,String isSubscribed,String duration,String insightType,
			String threshold_Name_Value_Type,int expectedStatus,String testEnabled) throws ParseException, InterruptedException, JoseException, IOException {

		commontUtils.isTCEnabled(testEnabled, testCaseId);
		logger.info("Testcase--> "+testCaseId+ "->"+"  Validate PATCH "+insightNameKey +"  Cobrand Subscription ,"+testCaseDescription);
		automationTestResults.setInsights("PATCH_SUB_CategorySpendingExceeded");
		automationTestResults.setBuildNo(buildNo);
		storyId = testCaseId.substring(0,  7);
		TestInfo testInfo = new TestInfo();
		testInfo.setTestCaseId(testCaseId);
		testInfo.setTestDescription(testCaseDescription);
		executedInsight=insightNameKey;
		boolean testExecutionStatus = false;				
		HttpMethodParameters cobHttpParams = insightsGenerics.updateCobrandSubscriptionProcessing(jwtCobAuthToken,insightNameKey, testNegativeScenario, insightNameEdit, insightTitle, container,
				cobrandId, keys, ruleExpression, description, applicableEntity,	entityParametersCount, entityParameterName, totalThresholds,id, 
				frequency, isSubscribed, duration, insightType,threshold_Name_Value_Type);
		Response patchCobrandSubscriptionResponse = insightUtils.patchCobrandSubscriptions(cobHttpParams,null);
		Response getCobSubscriptionResponseAfterPatch = insightUtils.getCobrandSubscriptions(cobHttpParams,null);		
		String testStatus = insightsGenerics.updateCobrandSubscriptionResponseVerificationPostPatch(testCaseId,testExecutionStatus,testSummary,patchCobrandSubscriptionResponse,negativeExpectedValuesMap,testNegativeScenario, testCaseDescription, getCobSubscriptionResponseAfterPatch, insightNameKey, frequency ,duration,insightType,insightTitle, entityParameterName, isSubscribed, threshold_Name_Value_Type,expectedStatus,failureReason,testInfo);
		notificationsSummary(Boolean.valueOf(testStatus),testInfo,testCaseId,insightNameKey,testCaseDescription);	
		if(!Boolean.valueOf(testStatus))
			Assert.fail();
	}
	
	@Test(enabled = true, dataProvider = "feeder")
	@Source("\\TestData\\CSVFiles\\Insights\\ProjectedInsufficientBalance\\PatchCobSubscriptionForProjectedInsufficientBalance.csv")
	public void testPatchCobrandSubscriptionForProjectedInsufficientBalance(String testCaseId ,String testCaseDescription,String insightNameKey,String testNegativeScenario,String insightNameEdit,
			String insightTitle,String container,String cobrandId,String validateKeys,String ruleExpression,String description,String applicableEntity,
			String entityParametersCount,String congigMeta,String entityParameterName,String totalThresholds,String id,String frequency,String isSubscribed,String duration,String insightType,
			String threshold_Name_Value_Type,int expectedStatus,String testEnabled) throws ParseException, InterruptedException, JoseException,IOException {

		commontUtils.isTCEnabled(testEnabled, testCaseId);
		logger.info("Testcase--> "+testCaseId+ "->"+"  Validate PATCH "+insightNameKey +"  Cobrand Subscription ,"+testCaseDescription);
		automationTestResults.setInsights("PATCH_SUB_ProjectedInsufficientBalance");
		automationTestResults.setBuildNo(buildNo);
		storyId = testCaseId.substring(0,  7);
		TestInfo testInfo = new TestInfo();
		testInfo.setTestCaseId(testCaseId);
		testInfo.setTestDescription(testCaseDescription);
		executedInsight=insightNameKey;
		boolean testExecutionStatus = false;
		HttpMethodParameters cobHttpParams = insightsGenerics.updateCobrandSubscriptionProcessing(jwtCobAuthToken,insightNameKey, testNegativeScenario, insightNameEdit, insightTitle, container,
				cobrandId, validateKeys, ruleExpression, description, applicableEntity,	entityParametersCount, entityParameterName, totalThresholds,id,
				frequency, isSubscribed, duration, insightType,threshold_Name_Value_Type);
		Response patchCobrandSubscriptionResponse = insightUtils.patchCobrandSubscriptions(cobHttpParams,null);
		Response getCobSubscriptionResponseAfterPatch = insightUtils.getCobrandSubscriptions(cobHttpParams,null);
		String testStatus = insightsGenerics.updateCobrandSubscriptionResponseVerificationPostPatch(testCaseId,testExecutionStatus,testSummary,patchCobrandSubscriptionResponse,negativeExpectedValuesMap,testNegativeScenario, testCaseDescription, getCobSubscriptionResponseAfterPatch, insightNameKey, frequency ,duration,insightType,insightTitle, entityParameterName, isSubscribed, threshold_Name_Value_Type,expectedStatus,failureReason,testInfo);
		notificationsSummary(Boolean.valueOf(testStatus),testInfo,testCaseId,insightNameKey,testCaseDescription);
		if(!Boolean.valueOf(testStatus))
			Assert.fail();
	}
	@Test(enabled = true, dataProvider = "feeder")
	@Source("\\TestData\\CSVFiles\\Insights\\LargePurchase\\PatchCobSubscriptionForLargePurchase.csv")
	public void testPatchCobrandSubscriptionForLargePurchase(String testCaseId ,String testCaseDescription,String insightNameKey,String testNegativeScenario,String insightNameEdit,
			String insightTitle,String container,String cobrandId,String validateKeys,String ruleExpression,String description,String applicableEntity,
			String entityParametersCount,String congigMeta,String entityParameterName,String totalThresholds,String id,String frequency,String isSubscribed,String duration,String insightType,
			String threshold_Name_Value_Type,int expectedStatus,String testEnabled) throws ParseException, InterruptedException, JoseException,IOException {

		commontUtils.isTCEnabled(testEnabled, testCaseId);
		logger.info("Testcase--> "+testCaseId+ "->"+"  Validate PATCH "+insightNameKey +"  Cobrand Subscription ,"+testCaseDescription);
		automationTestResults.setInsights("PATCH_SUB_LargePurchase");
		automationTestResults.setBuildNo(buildNo);
		storyId = testCaseId.substring(0,  7);
		TestInfo testInfo = new TestInfo();
		testInfo.setTestCaseId(testCaseId);
		testInfo.setTestDescription(testCaseDescription);
		executedInsight=insightNameKey;
		boolean testExecutionStatus = false;				
		HttpMethodParameters cobHttpParams = insightsGenerics.updateCobrandSubscriptionProcessing(jwtCobAuthToken,insightNameKey, testNegativeScenario, insightNameEdit, insightTitle, container,
				cobrandId, validateKeys, ruleExpression, description, applicableEntity,	entityParametersCount, entityParameterName, totalThresholds,id, 
				frequency, isSubscribed, duration, insightType,threshold_Name_Value_Type);
		Response patchCobrandSubscriptionResponse = insightUtils.patchCobrandSubscriptions(cobHttpParams,null);
		Response getCobSubscriptionResponseAfterPatch = insightUtils.getCobrandSubscriptions(cobHttpParams,null);		
		String testStatus = insightsGenerics.updateCobrandSubscriptionResponseVerificationPostPatch(testCaseId,testExecutionStatus,testSummary,patchCobrandSubscriptionResponse,negativeExpectedValuesMap,testNegativeScenario, testCaseDescription, getCobSubscriptionResponseAfterPatch, insightNameKey, frequency ,duration,insightType,insightTitle, entityParameterName, isSubscribed, threshold_Name_Value_Type,expectedStatus,failureReason,testInfo);
		notificationsSummary(Boolean.valueOf(testStatus),testInfo,testCaseId,insightNameKey,testCaseDescription);		
		if(!Boolean.valueOf(testStatus))
			Assert.fail();
	}
	@Test(enabled = true, dataProvider = "feeder")
	@Source("\\TestData\\CSVFiles\\Insights\\CardStatementAvailable\\PatchCobSubscriptionForCardStatementAvailable.csv")
	public void testPatchCobrandSubscriptionForCardStatementAvailable(String testCaseId ,String testCaseDescription,String insightNameKey,String testNegativeScenario,String insightNameEdit,
			String insightTitle,String container,String cobrandId,String validateKeys,String ruleExpression,String description,String applicableEntity,
			String entityParametersCount,String congigMeta,String entityParameterName,String totalThresholds,String id,String frequency,String isSubscribed,String duration,String insightType,
			String threshold_Name_Value_Type,int expectedStatus,String testEnabled) throws ParseException, InterruptedException, JoseException,IOException {

		commontUtils.isTCEnabled(testEnabled, testCaseId);
		logger.info("Testcase--> "+testCaseId+ "->"+"  Validate PATCH "+insightNameKey +"  Cobrand Subscription ,"+testCaseDescription);
		automationTestResults.setInsights("PATCH_SUB_CardStatementAvailable");
		automationTestResults.setBuildNo(buildNo);
		storyId = testCaseId.substring(0,  7);
		TestInfo testInfo = new TestInfo();
		testInfo.setTestCaseId(testCaseId);
		testInfo.setTestDescription(testCaseDescription);
		executedInsight=insightNameKey;
		boolean testExecutionStatus = false;				
		HttpMethodParameters cobHttpParams = insightsGenerics.updateCobrandSubscriptionProcessing(jwtCobAuthToken,insightNameKey, testNegativeScenario, insightNameEdit, insightTitle, container,
				cobrandId, validateKeys, ruleExpression, description, applicableEntity,	entityParametersCount, entityParameterName, totalThresholds,id, 
				frequency, isSubscribed, duration, insightType,threshold_Name_Value_Type);
		Response patchCobrandSubscriptionResponse = insightUtils.patchCobrandSubscriptions(cobHttpParams,null);
		Response getCobSubscriptionResponseAfterPatch = insightUtils.getCobrandSubscriptions(cobHttpParams,null);		
		String testStatus = insightsGenerics.updateCobrandSubscriptionResponseVerificationPostPatch(testCaseId,testExecutionStatus,testSummary,patchCobrandSubscriptionResponse,negativeExpectedValuesMap,testNegativeScenario, testCaseDescription, getCobSubscriptionResponseAfterPatch, insightNameKey, frequency ,duration,insightType,insightTitle, entityParameterName, isSubscribed, threshold_Name_Value_Type,expectedStatus,failureReason,testInfo);
		notificationsSummary(Boolean.valueOf(testStatus),testInfo,testCaseId,insightNameKey,testCaseDescription);		
		if(!Boolean.valueOf(testStatus))
			Assert.fail();
	}
	
	@Test(enabled = true, dataProvider = "feeder")
	@Source("\\TestData\\CSVFiles\\Insights\\PropertyValueChange\\PatchCobSubscriptionPropertyValueChange.csv")
	public void testPatchCobrandSubscriptionForPropertyValueChange(String testCaseId ,String testCaseDescription,String insightNameKey,String testNegativeScenario,String insightNameEdit,
			String insightTitle,String container,String cobrandId,String keys,String ruleExpression,String description,String applicableEntity,
			String entityParametersCount,String congigMeta,String entityParameterName,String totalThresholds,String id,String frequency,String isSubscribed,String duration,String insightType,
			String threshold_Name_Value_Type,int expectedStatus,String testEnabled) throws ParseException, InterruptedException, JoseException, IOException {

		commontUtils.isTCEnabled(testEnabled, testCaseId);
		logger.info("Testcase--> "+testCaseId+ "->"+"  Validate PATCH "+insightNameKey +"  Cobrand Subscription ,"+testCaseDescription);
		automationTestResults.setInsights("PATCH_COBSUB_PropertyValueChange");
		automationTestResults.setBuildNo(buildNo);
		storyId = testCaseId.substring(0,  7);
		TestInfo testInfo = new TestInfo();
		testInfo.setTestCaseId(testCaseId);
		testInfo.setTestDescription(testCaseDescription);
		executedInsight=insightNameKey;
		boolean testExecutionStatus = false;
		HttpMethodParameters cobHttpParams = insightsGenerics.updateCobrandSubscriptionProcessing(jwtCobAuthToken,insightNameKey, testNegativeScenario, insightNameEdit, insightTitle, container,
				cobrandId, keys, ruleExpression, description, applicableEntity,	entityParametersCount, entityParameterName, totalThresholds,id,
				frequency, isSubscribed, duration, insightType,threshold_Name_Value_Type);
		Response patchCobrandSubscriptionResponse = insightUtils.patchCobrandSubscriptions(cobHttpParams,null);
		Response getCobSubscriptionResponseAfterPatch = insightUtils.getCobrandSubscriptions(cobHttpParams,null);
		String testStatus = insightsGenerics.updateCobrandSubscriptionResponseVerificationPostPatch(testCaseId,testExecutionStatus,testSummary,patchCobrandSubscriptionResponse,negativeExpectedValuesMap,testNegativeScenario, testCaseDescription, getCobSubscriptionResponseAfterPatch, insightNameKey, frequency ,duration,insightType,insightTitle, entityParameterName, isSubscribed, threshold_Name_Value_Type,expectedStatus,failureReason,testInfo);
		notificationsSummary(Boolean.valueOf(testStatus),testInfo,testCaseId,insightNameKey,testCaseDescription);
		if(!Boolean.valueOf(testStatus))
			Assert.fail();
	}
	
	@Test(enabled = true, dataProvider = "feeder")
	@Source("\\TestData\\CSVFiles\\Insights\\BillPaid\\PatchCobSubscriptionForBillPaid.csv")
	public void testPatchCobrandSubscriptionForBillPaid(String testCaseId ,String testCaseDescription,String insightNameKey,String testNegativeScenario,String insightNameEdit,
			String insightTitle,String container,String cobrandId,String validateKeys,String ruleExpression,String description,String applicableEntity,
			String entityParametersCount,String congigMeta,String entityParameterName,String totalThresholds,String id,String frequency,String isSubscribed,String duration,String insightType,
			String threshold_Name_Value_Type,int expectedStatus,String testEnabled) throws ParseException, InterruptedException, JoseException,IOException {

		commontUtils.isTCEnabled(testEnabled, testCaseId);
		logger.info("Testcase--> "+testCaseId+ "->"+"  Validate PATCH "+insightNameKey +"  Cobrand Subscription ,"+testCaseDescription);
		automationTestResults.setInsights("PATCH_SUB_BillPaid");
		automationTestResults.setBuildNo(buildNo);
		storyId = testCaseId.substring(0,  7);
		TestInfo testInfo = new TestInfo();
		testInfo.setTestCaseId(testCaseId);
		testInfo.setTestDescription(testCaseDescription);
		executedInsight=insightNameKey;
		boolean testExecutionStatus = false;
		HttpMethodParameters cobHttpParams = insightsGenerics.updateCobrandSubscriptionProcessing(jwtCobAuthToken,insightNameKey, testNegativeScenario, insightNameEdit, insightTitle, container,
				cobrandId, validateKeys, ruleExpression, description, applicableEntity,	entityParametersCount, entityParameterName, totalThresholds,id,
				frequency, isSubscribed, duration, insightType,threshold_Name_Value_Type);
		Response patchCobrandSubscriptionResponse = insightUtils.patchCobrandSubscriptions(cobHttpParams,null);
		Response getCobSubscriptionResponseAfterPatch = insightUtils.getCobrandSubscriptions(cobHttpParams,null);
		String testStatus = insightsGenerics.updateCobrandSubscriptionResponseVerificationPostPatch(testCaseId,testExecutionStatus,testSummary,patchCobrandSubscriptionResponse,negativeExpectedValuesMap,testNegativeScenario, testCaseDescription, getCobSubscriptionResponseAfterPatch, insightNameKey, frequency ,duration,insightType,insightTitle, entityParameterName, isSubscribed, threshold_Name_Value_Type,expectedStatus,failureReason,testInfo);
		notificationsSummary(Boolean.valueOf(testStatus),testInfo,testCaseId,insightNameKey,testCaseDescription);
		if(!Boolean.valueOf(testStatus))
			Assert.fail();
	}
	@Test(enabled = true, dataProvider = "feeder")
	@Source("\\TestData\\CSVFiles\\Insights\\SubscriptionPaid\\PatchCobSubscriptionForSubscriptionPaid.csv")
	public void testPatchCobrandSubscriptionForSubscriptionPaid(String testCaseId ,String testCaseDescription,String insightNameKey,String testNegativeScenario,String insightNameEdit,
			String insightTitle,String container,String cobrandId,String validateKeys,String ruleExpression,String description,String applicableEntity,
			String entityParametersCount,String congigMeta,String entityParameterName,String totalThresholds,String id,String frequency,String isSubscribed,String duration,String insightType,
			String threshold_Name_Value_Type,int expectedStatus,String testEnabled) throws ParseException, InterruptedException, JoseException,IOException {

		commontUtils.isTCEnabled(testEnabled, testCaseId);
		logger.info("Testcase--> "+testCaseId+ "->"+"  Validate PATCH "+insightNameKey +"  Cobrand Subscription ,"+testCaseDescription);
		automationTestResults.setInsights("PATCH_SUB_SubscriptionPaid");
		automationTestResults.setBuildNo(buildNo);
		storyId = testCaseId.substring(0,  7);
		TestInfo testInfo = new TestInfo();
		testInfo.setTestCaseId(testCaseId);
		testInfo.setTestDescription(testCaseDescription);
		executedInsight=insightNameKey;
		boolean testExecutionStatus = false;
		HttpMethodParameters cobHttpParams = insightsGenerics.updateCobrandSubscriptionProcessing(jwtCobAuthToken,insightNameKey, testNegativeScenario, insightNameEdit, insightTitle, container,
				cobrandId, validateKeys, ruleExpression, description, applicableEntity,	entityParametersCount, entityParameterName, totalThresholds,id,
				frequency, isSubscribed, duration, insightType,threshold_Name_Value_Type);
		Response patchCobrandSubscriptionResponse = insightUtils.patchCobrandSubscriptions(cobHttpParams,null);
		Response getCobSubscriptionResponseAfterPatch = insightUtils.getCobrandSubscriptions(cobHttpParams,null);
		String testStatus = insightsGenerics.updateCobrandSubscriptionResponseVerificationPostPatch(testCaseId,testExecutionStatus,testSummary,patchCobrandSubscriptionResponse,negativeExpectedValuesMap,testNegativeScenario, testCaseDescription, getCobSubscriptionResponseAfterPatch, insightNameKey, frequency ,duration,insightType,insightTitle, entityParameterName, isSubscribed, threshold_Name_Value_Type,expectedStatus,failureReason,testInfo);
		notificationsSummary(Boolean.valueOf(testStatus),testInfo,testCaseId,insightNameKey,testCaseDescription);
		if(!Boolean.valueOf(testStatus))
			Assert.fail();
	} 
	
	@Test(enabled = true, dataProvider = "feeder")
	@Source("\\TestData\\CSVFiles\\Insights\\BillChanged\\PatchCobSubscriptionForBillChanged.csv")
	public void testPatchCobrandSubscriptionForBillChanged(String testCaseId, String testCaseDescription,
			String insightNameKey, String testNegativeScenario, String insightNameEdit, String insightTitle,
			String container, String cobrandId, String validateKeys, String ruleExpression, String description,
			String applicableEntity, String entityParametersCount, String congigMeta, String entityParameterName,
			String totalThresholds, String id, String frequency, String isSubscribed, String duration,
			String insightType, String threshold_Name_Value_Type, int expectedStatus, String testEnabled)
			throws ParseException, InterruptedException, JoseException, IOException {
		commontUtils.isTCEnabled(testEnabled, testCaseId);
		logger.info("Testcase--> " + testCaseId + "->" + "  Validate PATCH " + insightNameKey
				+ "  Cobrand Subscription ," + testCaseDescription);
		automationTestResults.setInsights("PATCH_SUB_BillChanged");
		automationTestResults.setBuildNo(buildNo);
		storyId = testCaseId.substring(0, 7);
		TestInfo testInfo = new TestInfo();
		testInfo.setTestCaseId(testCaseId);
		testInfo.setTestDescription(testCaseDescription);
		executedInsight = insightNameKey;
		boolean testExecutionStatus = false;
		HttpMethodParameters cobHttpParams = insightsGenerics.updateCobrandSubscriptionProcessing(jwtCobAuthToken,
				insightNameKey, testNegativeScenario, insightNameEdit, insightTitle, container, cobrandId, validateKeys,
				ruleExpression, description, applicableEntity, entityParametersCount, entityParameterName,
				totalThresholds, id, frequency, isSubscribed, duration, insightType, threshold_Name_Value_Type);
		Response patchCobrandSubscriptionResponse = insightUtils.patchCobrandSubscriptions(cobHttpParams, null);
		Response getCobSubscriptionResponseAfterPatch = insightUtils.getCobrandSubscriptions(cobHttpParams, null);
		String testStatus = insightsGenerics.updateCobrandSubscriptionResponseVerificationPostPatch(testCaseId,
				testExecutionStatus, testSummary, patchCobrandSubscriptionResponse, negativeExpectedValuesMap,
				testNegativeScenario, testCaseDescription, getCobSubscriptionResponseAfterPatch, insightNameKey,
				frequency, duration, insightType, insightTitle, entityParameterName, isSubscribed,
				threshold_Name_Value_Type, expectedStatus, failureReason, testInfo);
		notificationsSummary(Boolean.valueOf(testStatus), testInfo, testCaseId, insightNameKey, testCaseDescription);
		if (!Boolean.valueOf(testStatus))
			Assert.fail();
	}

	@Test(enabled = true, dataProvider = "feeder")
	@Source("\\TestData\\CSVFiles\\Insights\\SubscriptionChanged\\PatchCobSubscriptionForSubscriptionChanged.csv")
	public void testPatchCobrandSubscriptionForSubscriptionChanged(String testCaseId, String testCaseDescription,
			String insightNameKey, String testNegativeScenario, String insightNameEdit, String insightTitle,
			String container, String cobrandId, String validateKeys, String ruleExpression, String description,
			String applicableEntity, String entityParametersCount, String congigMeta, String entityParameterName,
			String totalThresholds, String id, String frequency, String isSubscribed, String duration,
			String insightType, String threshold_Name_Value_Type, int expectedStatus, String testEnabled)
			throws ParseException, InterruptedException, JoseException, IOException {

		commontUtils.isTCEnabled(testEnabled, testCaseId);
		logger.info("Testcase--> " + testCaseId + "->" + "  Validate PATCH " + insightNameKey
				+ "  Cobrand Subscription ," + testCaseDescription);
		automationTestResults.setInsights("PATCH_SUB_SubscriptionChanged");
		automationTestResults.setBuildNo(buildNo);
		storyId = testCaseId.substring(0, 7);
		TestInfo testInfo = new TestInfo();
		testInfo.setTestCaseId(testCaseId);
		testInfo.setTestDescription(testCaseDescription);
		executedInsight = insightNameKey;
		boolean testExecutionStatus = false;
		HttpMethodParameters cobHttpParams = insightsGenerics.updateCobrandSubscriptionProcessing(jwtCobAuthToken,
				insightNameKey, testNegativeScenario, insightNameEdit, insightTitle, container, cobrandId, validateKeys,
				ruleExpression, description, applicableEntity, entityParametersCount, entityParameterName,
				totalThresholds, id, frequency, isSubscribed, duration, insightType, threshold_Name_Value_Type);
		Response patchCobrandSubscriptionResponse = insightUtils.patchCobrandSubscriptions(cobHttpParams, null);
		Response getCobSubscriptionResponseAfterPatch = insightUtils.getCobrandSubscriptions(cobHttpParams, null);
		String testStatus = insightsGenerics.updateCobrandSubscriptionResponseVerificationPostPatch(testCaseId,
				testExecutionStatus, testSummary, patchCobrandSubscriptionResponse, negativeExpectedValuesMap,
				testNegativeScenario, testCaseDescription, getCobSubscriptionResponseAfterPatch, insightNameKey,
				frequency, duration, insightType, insightTitle, entityParameterName, isSubscribed,
				threshold_Name_Value_Type, expectedStatus, failureReason, testInfo);
		notificationsSummary(Boolean.valueOf(testStatus), testInfo, testCaseId, insightNameKey, testCaseDescription);
		if (!Boolean.valueOf(testStatus))
			Assert.fail();
	}
	
	@Test(enabled = true, dataProvider = "feeder")
	@Source("\\TestData\\CSVFiles\\Insights\\BalanceAboveThreshold\\PatchCobSubscriptionForBalanceAboveThreshold.csv")
	public void testPatchCobrandSubscriptionForBalanceAboveThreshold(String testCaseId ,String testCaseDescription,String insightNameKey,String testNegativeScenario,String insightNameEdit,
			String insightTitle,String container,String cobrandId,String keys,String ruleExpression,String description,String applicableEntity,
			String entityParametersCount,String congigMeta,String entityParameterName,String totalThresholds,String id,String frequency,String isSubscribed,String duration,String insightType,
			String threshold_Name_Value_Type,int expectedStatus,String testEnabled) throws ParseException, InterruptedException, JoseException, IOException {

		commontUtils.isTCEnabled(testEnabled, testCaseId);
		logger.info("Testcase--> "+testCaseId+ "->"+"  Validate PATCH "+insightNameKey +"  Cobrand Subscription ,"+testCaseDescription);
		automationTestResults.setInsights("PATCH_SUB_BalanceAboveThreshold");
		automationTestResults.setBuildNo(buildNo);
		storyId = testCaseId.substring(0,  7);
		TestInfo testInfo = new TestInfo();
		testInfo.setTestCaseId(testCaseId);
		testInfo.setTestDescription(testCaseDescription);
		executedInsight=insightNameKey;
		boolean testExecutionStatus = false;
		HttpMethodParameters cobHttpParams = insightsGenerics.updateCobrandSubscriptionProcessing(jwtCobAuthToken,insightNameKey, testNegativeScenario, insightNameEdit, insightTitle, container,
				cobrandId, keys, ruleExpression, description, applicableEntity,	entityParametersCount, entityParameterName, totalThresholds,id,
				frequency, isSubscribed, duration, insightType,threshold_Name_Value_Type);
		Response patchCobrandSubscriptionResponse = insightUtils.patchCobrandSubscriptions(cobHttpParams,null);
		Response getCobSubscriptionResponseAfterPatch = insightUtils.getCobrandSubscriptions(cobHttpParams,null);
		String testStatus = insightsGenerics.updateCobrandSubscriptionResponseVerificationPostPatch(testCaseId,testExecutionStatus,testSummary,patchCobrandSubscriptionResponse,negativeExpectedValuesMap,testNegativeScenario, testCaseDescription, getCobSubscriptionResponseAfterPatch, insightNameKey, frequency ,duration,insightType,insightTitle, entityParameterName, isSubscribed, threshold_Name_Value_Type,expectedStatus,failureReason,testInfo);
		notificationsSummary(Boolean.valueOf(testStatus),testInfo,testCaseId,insightNameKey,testCaseDescription);
		if(!Boolean.valueOf(testStatus))
			Assert.fail();


	}
	
	@Test(enabled = true, dataProvider = "feeder")
	@Source("\\TestData\\CSVFiles\\Insights\\SalaryChanged\\PatchCobSubscriptionSalaryChanged.csv")
	public void testPatchCobrandSubscriptionForSalaryChanged(String testCaseId, String testCaseDescription,
			String insightNameKey, String testNegativeScenario, String insightNameEdit, String insightTitle,
			String container, String cobrandId, String keys, String ruleExpression, String description,
			String applicableEntity, String entityParametersCount, String congigMeta, String entityParameterName,
			String totalThresholds, String id, String frequency, String isSubscribed, String duration,
			String insightType, String threshold_Name_Value_Type, int expectedStatus, String testEnabled)
			throws ParseException, InterruptedException, JoseException, IOException {

		commontUtils.isTCEnabled(testEnabled, testCaseId);
		logger.info("Testcase--> " + testCaseId + "->" + "  Validate PATCH " + insightNameKey
				+ "  Cobrand Subscription ," + testCaseDescription);
		automationTestResults.setInsights("PATCH_COBSUB_SalaryChanged");
		automationTestResults.setBuildNo(buildNo);
		storyId = testCaseId.substring(0, 7);
		TestInfo testInfo = new TestInfo();
		testInfo.setTestCaseId(testCaseId);
		testInfo.setTestDescription(testCaseDescription);
		executedInsight = insightNameKey;
		boolean testExecutionStatus = false;
		HttpMethodParameters cobHttpParams = insightsGenerics.updateCobrandSubscriptionProcessing(jwtCobAuthToken,
				insightNameKey, testNegativeScenario, insightNameEdit, insightTitle, container, cobrandId, keys,
				ruleExpression, description, applicableEntity, entityParametersCount, entityParameterName,
				totalThresholds, id, frequency, isSubscribed, duration, insightType, threshold_Name_Value_Type);
		Response patchCobrandSubscriptionResponse = insightUtils.patchCobrandSubscriptions(cobHttpParams, null);
		Response getCobSubscriptionResponseAfterPatch = insightUtils.getCobrandSubscriptions(cobHttpParams, null);
		String testStatus = insightsGenerics.updateCobrandSubscriptionResponseVerificationPostPatch(testCaseId,
				testExecutionStatus, testSummary, patchCobrandSubscriptionResponse, negativeExpectedValuesMap,
				testNegativeScenario, testCaseDescription, getCobSubscriptionResponseAfterPatch, insightNameKey,
				frequency, duration, insightType, insightTitle, entityParameterName, isSubscribed,
				threshold_Name_Value_Type, expectedStatus, failureReason, testInfo);
		notificationsSummary(Boolean.valueOf(testStatus), testInfo, testCaseId, insightNameKey, testCaseDescription);
		if (!Boolean.valueOf(testStatus))
			Assert.fail();
	}

	@Test(enabled = true, dataProvider = "feeder")
	@Source("\\TestData\\CSVFiles\\Insights\\RecurringDepositLate\\PatchCobSubscriptionForRecurringDepositLate.csv")
	public void testPatchCobrandSubscriptionForRecurringDepositLate(String testCaseId ,String testCaseDescription,String insightNameKey,String testNegativeScenario,String insightNameEdit,
			String insightTitle,String container,String cobrandId,String validateKeys,String ruleExpression,String description,String applicableEntity,
			String entityParametersCount,String congigMeta,String entityParameterName,String totalThresholds,String id,String frequency,String isSubscribed,String duration,String insightType,
			String threshold_Name_Value_Type,int expectedStatus,String testEnabled) throws ParseException, InterruptedException, JoseException,IOException {

		commontUtils.isTCEnabled(testEnabled, testCaseId);
		logger.info("Testcase--> "+testCaseId+ "->"+"  Validate PATCH "+insightNameKey +"  Cobrand Subscription ,"+testCaseDescription);
		automationTestResults.setInsights("RecurringDepositLate");
		automationTestResults.setBuildNo(buildNo);
		storyId = testCaseId.substring(0,  7);
		TestInfo testInfo = new TestInfo();
		testInfo.setTestCaseId(testCaseId);
		testInfo.setTestDescription(testCaseDescription);
		executedInsight=insightNameKey;
		boolean testExecutionStatus = false;
		HttpMethodParameters cobHttpParams = insightsGenerics.updateCobrandSubscriptionProcessing(jwtCobAuthToken,insightNameKey, testNegativeScenario, insightNameEdit, insightTitle, container,
				cobrandId, validateKeys, ruleExpression, description, applicableEntity,	entityParametersCount, entityParameterName, totalThresholds,id,
				frequency, isSubscribed, duration, insightType,threshold_Name_Value_Type);
		Response patchCobrandSubscriptionResponse = insightUtils.patchCobrandSubscriptions(cobHttpParams,null);
		Response getCobSubscriptionResponseAfterPatch = insightUtils.getCobrandSubscriptions(cobHttpParams,null);
		String testStatus = insightsGenerics.updateCobrandSubscriptionResponseVerificationPostPatch(testCaseId,testExecutionStatus,testSummary,patchCobrandSubscriptionResponse,negativeExpectedValuesMap,testNegativeScenario, testCaseDescription, getCobSubscriptionResponseAfterPatch, insightNameKey, frequency ,duration,insightType,insightTitle, entityParameterName, isSubscribed, threshold_Name_Value_Type,expectedStatus,failureReason,testInfo);
		notificationsSummary(Boolean.valueOf(testStatus),testInfo,testCaseId,insightNameKey,testCaseDescription);
		if(!Boolean.valueOf(testStatus))
			Assert.fail();
	}
	
	@Test(enabled = true, dataProvider = "feeder")
	@Source("\\TestData\\CSVFiles\\Insights\\LoanMaturityNotice\\PatchCobSubscriptionForLoanMaturityNotice.csv")
	public void testPatchCobrandSubscriptionForLoanMaturityNotice(String testCaseId ,String testCaseDescription,String insightNameKey,String testNegativeScenario,String insightNameEdit,
			String insightTitle,String container,String cobrandId,String validateKeys,String ruleExpression,String description,String applicableEntity,
			String entityParametersCount,String congigMeta,String entityParameterName,String totalThresholds,String id,String frequency,String isSubscribed,String duration,String insightType,
			String threshold_Name_Value_Type,int expectedStatus,String testEnabled) throws ParseException, InterruptedException, JoseException,IOException {

		commontUtils.isTCEnabled(testEnabled, testCaseId);
		logger.info("Testcase--> "+testCaseId+ "->"+"  Validate PATCH "+insightNameKey +"  Cobrand Subscription ,"+testCaseDescription);
		automationTestResults.setInsights("LoanMaturityNotice");
		automationTestResults.setBuildNo(buildNo);
		storyId = testCaseId.substring(0,  7);
		TestInfo testInfo = new TestInfo();
		testInfo.setTestCaseId(testCaseId);
		testInfo.setTestDescription(testCaseDescription);
		executedInsight=insightNameKey;
		boolean testExecutionStatus = false;
		HttpMethodParameters cobHttpParams = insightsGenerics.updateCobrandSubscriptionProcessing(jwtCobAuthToken,insightNameKey, testNegativeScenario, insightNameEdit, insightTitle, container,
				cobrandId, validateKeys, ruleExpression, description, applicableEntity,	entityParametersCount, entityParameterName, totalThresholds,id,
				frequency, isSubscribed, duration, insightType,threshold_Name_Value_Type);
		Response patchCobrandSubscriptionResponse = insightUtils.patchCobrandSubscriptions(cobHttpParams,null);
		Response getCobSubscriptionResponseAfterPatch = insightUtils.getCobrandSubscriptions(cobHttpParams,null);
		String testStatus = insightsGenerics.updateCobrandSubscriptionResponseVerificationPostPatch(testCaseId,testExecutionStatus,testSummary,patchCobrandSubscriptionResponse,negativeExpectedValuesMap,testNegativeScenario, testCaseDescription, getCobSubscriptionResponseAfterPatch, insightNameKey, frequency ,duration,insightType,insightTitle, entityParameterName, isSubscribed, threshold_Name_Value_Type,expectedStatus,failureReason,testInfo);
		notificationsSummary(Boolean.valueOf(testStatus),testInfo,testCaseId,insightNameKey,testCaseDescription);
		if(!Boolean.valueOf(testStatus))
			Assert.fail();
	}
	@Test(enabled = true, dataProvider = "feeder")
	@Source("\\TestData\\CSVFiles\\Insights\\InvestmentMaturityNotice\\PatchCobSubscriptionForInvestmentMaturityNotice.csv")
	public void testPatchCobrandSubscriptionForInvestmentMaturityNotice(String testCaseId ,String testCaseDescription,String insightNameKey,String testNegativeScenario,String insightNameEdit,
			String insightTitle,String container,String cobrandId,String validateKeys,String ruleExpression,String description,String applicableEntity,
			String entityParametersCount,String congigMeta,String entityParameterName,String totalThresholds,String id,String frequency,String isSubscribed,String duration,String insightType,
			String threshold_Name_Value_Type,int expectedStatus,String testEnabled) throws ParseException, InterruptedException, JoseException,IOException {

		commontUtils.isTCEnabled(testEnabled, testCaseId);
		logger.info("Testcase--> "+testCaseId+ "->"+"  Validate PATCH "+insightNameKey +"  Cobrand Subscription ,"+testCaseDescription);
		automationTestResults.setInsights("InvestmentMaturityNotice");
		automationTestResults.setBuildNo(buildNo);
		storyId = testCaseId.substring(0,  7);
		TestInfo testInfo = new TestInfo();
		testInfo.setTestCaseId(testCaseId);
		testInfo.setTestDescription(testCaseDescription);
		executedInsight=insightNameKey;
		boolean testExecutionStatus = false;
		HttpMethodParameters cobHttpParams = insightsGenerics.updateCobrandSubscriptionProcessing(jwtCobAuthToken,insightNameKey, testNegativeScenario, insightNameEdit, insightTitle, container,
				cobrandId, validateKeys, ruleExpression, description, applicableEntity,	entityParametersCount, entityParameterName, totalThresholds,id,
				frequency, isSubscribed, duration, insightType,threshold_Name_Value_Type);
		Response patchCobrandSubscriptionResponse = insightUtils.patchCobrandSubscriptions(cobHttpParams,null);
		Response getCobSubscriptionResponseAfterPatch = insightUtils.getCobrandSubscriptions(cobHttpParams,null);
		String testStatus = insightsGenerics.updateCobrandSubscriptionResponseVerificationPostPatch(testCaseId,testExecutionStatus,testSummary,patchCobrandSubscriptionResponse,negativeExpectedValuesMap,testNegativeScenario, testCaseDescription, getCobSubscriptionResponseAfterPatch, insightNameKey, frequency ,duration,insightType,insightTitle, entityParameterName, isSubscribed, threshold_Name_Value_Type,expectedStatus,failureReason,testInfo);
		notificationsSummary(Boolean.valueOf(testStatus),testInfo,testCaseId,insightNameKey,testCaseDescription);
		if(!Boolean.valueOf(testStatus))
			Assert.fail();
	}
	
	@Test(enabled = true, dataProvider = "feeder")
	@Source("\\TestData\\CSVFiles\\Insights\\IncomeThreshold\\PatchCobSubscriptionForIncomeThreshold.csv")
	public void testPatchCobrandSubscriptionForIncomeThreshold(String testCaseId ,String testCaseDescription,String insightNameKey,String testNegativeScenario,String insightNameEdit,
			String insightTitle,String container,String cobrandId,String validateKeys,String ruleExpression,String description,String applicableEntity,
			String entityParametersCount,String congigMeta,String entityParameterName,String totalThresholds,String id,String frequency,String isSubscribed,String duration,String insightType,
			String threshold_Name_Value_Type,int expectedStatus,String testEnabled) throws ParseException, InterruptedException, JoseException,IOException {

		commontUtils.isTCEnabled(testEnabled, testCaseId);
		logger.info("Testcase--> "+testCaseId+ "->"+"  Validate PATCH "+insightNameKey +"  Cobrand Subscription ,"+testCaseDescription);
		automationTestResults.setInsights("IncomeThreshold");
		automationTestResults.setBuildNo(buildNo);
		storyId = testCaseId.substring(0,  7);
		TestInfo testInfo = new TestInfo();
		testInfo.setTestCaseId(testCaseId);
		testInfo.setTestDescription(testCaseDescription);
		executedInsight=insightNameKey;
		boolean testExecutionStatus = false;				
		HttpMethodParameters cobHttpParams = insightsGenerics.updateCobrandSubscriptionProcessing(jwtCobAuthToken,insightNameKey, testNegativeScenario, insightNameEdit, insightTitle, container,
				cobrandId, validateKeys, ruleExpression, description, applicableEntity,	entityParametersCount, entityParameterName, totalThresholds,id, 
				frequency, isSubscribed, duration, insightType,threshold_Name_Value_Type);
		Response patchCobrandSubscriptionResponse = insightUtils.patchCobrandSubscriptions(cobHttpParams,null);
		Response getCobSubscriptionResponseAfterPatch = insightUtils.getCobrandSubscriptions(cobHttpParams,null);		
		String testStatus = insightsGenerics.updateCobrandSubscriptionResponseVerificationPostPatch(testCaseId,testExecutionStatus,testSummary,patchCobrandSubscriptionResponse,negativeExpectedValuesMap,testNegativeScenario, testCaseDescription, getCobSubscriptionResponseAfterPatch, insightNameKey, frequency ,duration,insightType,insightTitle, entityParameterName, isSubscribed, threshold_Name_Value_Type,expectedStatus,failureReason,testInfo);
		notificationsSummary(Boolean.valueOf(testStatus),testInfo,testCaseId,insightNameKey,testCaseDescription);		
		if(!Boolean.valueOf(testStatus))
			Assert.fail();
	}
	
	@Test(enabled = true, dataProvider = "feeder")
	@Source("\\TestData\\CSVFiles\\Insights\\ExpensesExceededBudget\\PatchCobSubsExpExceededBudget.csv")
	public void testPatchCobrandSubscriptionForExpensesExceededBudget(String testCaseId ,String testCaseDescription,String insightNameKey,String testNegativeScenario,String insightNameEdit,
			String insightTitle,String container,String cobrandId,String validateKeys,String ruleExpression,String description,String applicableEntity,
			String entityParametersCount,String congigMeta,String entityParameterName,String totalThresholds,String id,String frequency,String isSubscribed,String duration,String insightType,
			String threshold_Name_Value_Type,int expectedStatus,String testEnabled) throws ParseException, InterruptedException, JoseException,IOException {

		commontUtils.isTCEnabled(testEnabled, testCaseId);
		logger.info("Testcase--> "+testCaseId+ "->"+"  Validate PATCH "+insightNameKey +"  Cobrand Subscription ,"+testCaseDescription);
		automationTestResults.setInsights("ExpensesExceededBudget");
		automationTestResults.setBuildNo(buildNo);
		storyId = testCaseId.substring(0,  7);
		TestInfo testInfo = new TestInfo();
		testInfo.setTestCaseId(testCaseId);
		testInfo.setTestDescription(testCaseDescription);
		executedInsight=insightNameKey;
		boolean testExecutionStatus = false;				
		HttpMethodParameters cobHttpParams = insightsGenerics.updateCobrandSubscriptionProcessing(jwtCobAuthToken,insightNameKey, testNegativeScenario, insightNameEdit, insightTitle, container,
				cobrandId, validateKeys, ruleExpression, description, applicableEntity,	entityParametersCount, entityParameterName, totalThresholds,id, 
				frequency, isSubscribed, duration, insightType,threshold_Name_Value_Type);
		Response patchCobrandSubscriptionResponse = insightUtils.patchCobrandSubscriptions(cobHttpParams,null);
		Response getCobSubscriptionResponseAfterPatch = insightUtils.getCobrandSubscriptions(cobHttpParams,null);		
		String testStatus = insightsGenerics.updateCobrandSubscriptionResponseVerificationPostPatch(testCaseId,testExecutionStatus,testSummary,patchCobrandSubscriptionResponse,negativeExpectedValuesMap,testNegativeScenario, testCaseDescription, getCobSubscriptionResponseAfterPatch, insightNameKey, frequency ,duration,insightType,insightTitle, entityParameterName, isSubscribed, threshold_Name_Value_Type,expectedStatus,failureReason,testInfo);
		notificationsSummary(Boolean.valueOf(testStatus),testInfo,testCaseId,insightNameKey,testCaseDescription);		
		if(!Boolean.valueOf(testStatus))
			Assert.fail();
	}
	
	@Test(enabled = true, dataProvider = "feeder")
	@Source("\\TestData\\CSVFiles\\Insights\\SpendingEarningComparison\\PatchCobSubsSpendingEarning.csv")
	public void testPatchCobrandSubscriptionForSpendingEarningComparison(String testCaseId ,String testCaseDescription,String insightNameKey,String testNegativeScenario,String insightNameEdit,
			String insightTitle,String container,String cobrandId,String validateKeys,String ruleExpression,String description,String applicableEntity,
			String entityParametersCount,String congigMeta,String entityParameterName,String totalThresholds,String id,String frequency,String isSubscribed,String duration,String insightType,
			String threshold_Name_Value_Type,int expectedStatus,String testEnabled) throws ParseException, InterruptedException, JoseException,IOException {

		commontUtils.isTCEnabled(testEnabled, testCaseId);
		logger.info("Testcase--> "+testCaseId+ "->"+"  Validate PATCH "+insightNameKey +"  Cobrand Subscription ,"+testCaseDescription);
		automationTestResults.setInsights("SpendingEarningComparison");
		automationTestResults.setBuildNo(buildNo);
		storyId = testCaseId.substring(0,  7);
		TestInfo testInfo = new TestInfo();
		testInfo.setTestCaseId(testCaseId);
		testInfo.setTestDescription(testCaseDescription);
		executedInsight=insightNameKey;
		boolean testExecutionStatus = false;				
		HttpMethodParameters cobHttpParams = insightsGenerics.updateCobrandSubscriptionProcessing(jwtCobAuthToken,insightNameKey, testNegativeScenario, insightNameEdit, insightTitle, container,
				cobrandId, validateKeys, ruleExpression, description, applicableEntity,	entityParametersCount, entityParameterName, totalThresholds,id, 
				frequency, isSubscribed, duration, insightType,threshold_Name_Value_Type);
		Response patchCobrandSubscriptionResponse = insightUtils.patchCobrandSubscriptions(cobHttpParams,null);
		Response getCobSubscriptionResponseAfterPatch = insightUtils.getCobrandSubscriptions(cobHttpParams,null);		
		String testStatus = insightsGenerics.updateCobrandSubscriptionResponseVerificationPostPatch(testCaseId,testExecutionStatus,testSummary,patchCobrandSubscriptionResponse,negativeExpectedValuesMap,testNegativeScenario, testCaseDescription, getCobSubscriptionResponseAfterPatch, insightNameKey, frequency ,duration,insightType,insightTitle, entityParameterName, isSubscribed, threshold_Name_Value_Type,expectedStatus,failureReason,testInfo);
		notificationsSummary(Boolean.valueOf(testStatus),testInfo,testCaseId,insightNameKey,testCaseDescription);		
		if(!Boolean.valueOf(testStatus))
			Assert.fail();
	}
	
	@Test(enabled = true, dataProvider = "feeder")
	@Source("\\TestData\\CSVFiles\\Insights\\EmergencySavings\\PatchCobSubsEmergencySavings.csv")
	public void testPatchCobrandSubscriptionForEmergencySavings(String testCaseId ,String testCaseDescription,String insightNameKey,String testNegativeScenario,String insightNameEdit,
			String insightTitle,String container,String cobrandId,String validateKeys,String ruleExpression,String description,String applicableEntity,
			String entityParametersCount,String congigMeta,String entityParameterName,String totalThresholds,String id,String frequency,String isSubscribed,String duration,String insightType,
			String threshold_Name_Value_Type,int expectedStatus,String testEnabled) throws ParseException, InterruptedException, JoseException,IOException {

		commontUtils.isTCEnabled(testEnabled, testCaseId);
		logger.info("Testcase--> "+testCaseId+ "->"+"  Validate PATCH "+insightNameKey +"  Cobrand Subscription ,"+testCaseDescription);
		automationTestResults.setInsights("EmergencySavings");
		automationTestResults.setBuildNo(buildNo);
		storyId = testCaseId.substring(0,  7);
		TestInfo testInfo = new TestInfo();
		testInfo.setTestCaseId(testCaseId);
		testInfo.setTestDescription(testCaseDescription);
		executedInsight=insightNameKey;
		boolean testExecutionStatus = false;				
		HttpMethodParameters cobHttpParams = insightsGenerics.updateCobrandSubscriptionProcessing(jwtCobAuthToken,insightNameKey, testNegativeScenario, insightNameEdit, insightTitle, container,
				cobrandId, validateKeys, ruleExpression, description, applicableEntity,	entityParametersCount, entityParameterName, totalThresholds,id, 
				frequency, isSubscribed, duration, insightType,threshold_Name_Value_Type);
		Response patchCobrandSubscriptionResponse = insightUtils.patchCobrandSubscriptions(cobHttpParams,null);
		Response getCobSubscriptionResponseAfterPatch = insightUtils.getCobrandSubscriptions(cobHttpParams,null);		
		String testStatus = insightsGenerics.updateCobrandSubscriptionResponseVerificationPostPatch(testCaseId,testExecutionStatus,testSummary,patchCobrandSubscriptionResponse,negativeExpectedValuesMap,testNegativeScenario, testCaseDescription, getCobSubscriptionResponseAfterPatch, insightNameKey, frequency ,duration,insightType,insightTitle, entityParameterName, isSubscribed, threshold_Name_Value_Type,expectedStatus,failureReason,testInfo);
		notificationsSummary(Boolean.valueOf(testStatus),testInfo,testCaseId,insightNameKey,testCaseDescription);		
		if(!Boolean.valueOf(testStatus))
			Assert.fail();
	}
	
	@Test(enabled = true, dataProvider = "feeder")
	@Source("\\TestData\\CSVFiles\\Insights\\SpendingByCategoryConsumerLevel\\PatchCobSubsSpeCatAllAcct.csv")
	public void testPatchCobrandSubscriptionForSpendingByCategoryAllAccounts(String testCaseId ,String testCaseDescription,String insightNameKey,String testNegativeScenario,String insightNameEdit,
			String insightTitle,String container,String cobrandId,String validateKeys,String ruleExpression,String description,String applicableEntity,
			String entityParametersCount,String congigMeta,String entityParameterName,String totalThresholds,String id,String frequency,String isSubscribed,String duration,String insightType,
			String threshold_Name_Value_Type,int expectedStatus,String testEnabled) throws ParseException, InterruptedException, JoseException,IOException {

		commontUtils.isTCEnabled(testEnabled, testCaseId);
		logger.info("Testcase--> "+testCaseId+ "->"+"  Validate PATCH "+insightNameKey +"  Cobrand Subscription ,"+testCaseDescription);
		automationTestResults.setInsights("SpendingByCategoryAllAccounts");
		automationTestResults.setBuildNo(buildNo);
		storyId = testCaseId.substring(0,  7);
		TestInfo testInfo = new TestInfo();
		testInfo.setTestCaseId(testCaseId);
		testInfo.setTestDescription(testCaseDescription);
		executedInsight=insightNameKey;
		boolean testExecutionStatus = false;				
		HttpMethodParameters cobHttpParams = insightsGenerics.updateCobrandSubscriptionProcessing(jwtCobAuthToken,insightNameKey, testNegativeScenario, insightNameEdit, insightTitle, container,
				cobrandId, validateKeys, ruleExpression, description, applicableEntity,	entityParametersCount, entityParameterName, totalThresholds,id, 
				frequency, isSubscribed, duration, insightType,threshold_Name_Value_Type);
		Response patchCobrandSubscriptionResponse = insightUtils.patchCobrandSubscriptions(cobHttpParams,null);
		Response getCobSubscriptionResponseAfterPatch = insightUtils.getCobrandSubscriptions(cobHttpParams,null);		
		String testStatus = insightsGenerics.updateCobrandSubscriptionResponseVerificationPostPatch(testCaseId,testExecutionStatus,testSummary,patchCobrandSubscriptionResponse,negativeExpectedValuesMap,testNegativeScenario, testCaseDescription, getCobSubscriptionResponseAfterPatch, insightNameKey, frequency ,duration,insightType,insightTitle, entityParameterName, isSubscribed, threshold_Name_Value_Type,expectedStatus,failureReason,testInfo);
		notificationsSummary(Boolean.valueOf(testStatus),testInfo,testCaseId,insightNameKey,testCaseDescription);		
		if(!Boolean.valueOf(testStatus))
			Assert.fail();
	}
	
	@Test(enabled = true, dataProvider = "feeder")
	@Source("\\TestData\\CSVFiles\\Insights\\SpendingByMerchantsConsumerLevel\\PatchCobSubsSpeMerAllAcct.csv")
	public void testPatchCobrandSubscriptionForSpendingByMerchantsAllAccounts(String testCaseId ,String testCaseDescription,String insightNameKey,String testNegativeScenario,String insightNameEdit,
			String insightTitle,String container,String cobrandId,String validateKeys,String ruleExpression,String description,String applicableEntity,
			String entityParametersCount,String congigMeta,String entityParameterName,String totalThresholds,String id,String frequency,String isSubscribed,String duration,String insightType,
			String threshold_Name_Value_Type,int expectedStatus,String testEnabled) throws ParseException, InterruptedException, JoseException,IOException {

		commontUtils.isTCEnabled(testEnabled, testCaseId);
		logger.info("Testcase--> "+testCaseId+ "->"+"  Validate PATCH "+insightNameKey +"  Cobrand Subscription ,"+testCaseDescription);
		automationTestResults.setInsights("SpendingByMerchantsAllAccounts");
		automationTestResults.setBuildNo(buildNo);
		storyId = testCaseId.substring(0,  7);
		TestInfo testInfo = new TestInfo();
		testInfo.setTestCaseId(testCaseId);
		testInfo.setTestDescription(testCaseDescription);
		executedInsight=insightNameKey;
		boolean testExecutionStatus = false;				
		HttpMethodParameters cobHttpParams = insightsGenerics.updateCobrandSubscriptionProcessing(jwtCobAuthToken,insightNameKey, testNegativeScenario, insightNameEdit, insightTitle, container,
				cobrandId, validateKeys, ruleExpression, description, applicableEntity,	entityParametersCount, entityParameterName, totalThresholds,id, 
				frequency, isSubscribed, duration, insightType,threshold_Name_Value_Type);
		Response patchCobrandSubscriptionResponse = insightUtils.patchCobrandSubscriptions(cobHttpParams,null);
		Response getCobSubscriptionResponseAfterPatch = insightUtils.getCobrandSubscriptions(cobHttpParams,null);		
		String testStatus = insightsGenerics.updateCobrandSubscriptionResponseVerificationPostPatch(testCaseId,testExecutionStatus,testSummary,patchCobrandSubscriptionResponse,negativeExpectedValuesMap,testNegativeScenario, testCaseDescription, getCobSubscriptionResponseAfterPatch, insightNameKey, frequency ,duration,insightType,insightTitle, entityParameterName, isSubscribed, threshold_Name_Value_Type,expectedStatus,failureReason,testInfo);
		notificationsSummary(Boolean.valueOf(testStatus),testInfo,testCaseId,insightNameKey,testCaseDescription);		
		if(!Boolean.valueOf(testStatus))
			Assert.fail();
	}
	
	@Test(enabled = true, dataProvider = "feeder")
	@Source("\\TestData\\CSVFiles\\Insights\\AggregateCreditCardAccount\\PatchCobSubscriptionForAggregateCreditCardAccount.csv")
	public void testPatchCobrandSubscriptionForAggregateCreditCardAccount(String testCaseId ,String testCaseDescription,String insightNameKey,String testNegativeScenario,String insightNameEdit,
			String insightTitle,String container,String cobrandId,String validateKeys,String ruleExpression,String description,String applicableEntity,
			String entityParametersCount,String congigMeta,String entityParameterName,String totalThresholds,String id,String frequency,String isSubscribed,String duration,String insightType,
			String threshold_Name_Value_Type,int expectedStatus,String testEnabled) throws ParseException, InterruptedException, JoseException,IOException {

		commontUtils.isTCEnabled(testEnabled, testCaseId);
		logger.info("Testcase--> "+testCaseId+ "->"+"  Validate PATCH "+insightNameKey +"  Cobrand Subscription ,"+testCaseDescription);
		automationTestResults.setInsights("AggregateCreditCardAccount");
		automationTestResults.setBuildNo(buildNo);
		storyId = testCaseId.substring(0,  7);
		TestInfo testInfo = new TestInfo();
		testInfo.setTestCaseId(testCaseId);
		testInfo.setTestDescription(testCaseDescription);
		executedInsight=insightNameKey;
		boolean testExecutionStatus = false;				
		HttpMethodParameters cobHttpParams = insightsGenerics.updateCobrandSubscriptionProcessing(jwtCobAuthToken,insightNameKey, testNegativeScenario, insightNameEdit, insightTitle, container,
				cobrandId, validateKeys, ruleExpression, description, applicableEntity,	entityParametersCount, entityParameterName, totalThresholds,id, 
				frequency, isSubscribed, duration, insightType,threshold_Name_Value_Type);
		Response patchCobrandSubscriptionResponse = insightUtils.patchCobrandSubscriptions(cobHttpParams,null);
		Response getCobSubscriptionResponseAfterPatch = insightUtils.getCobrandSubscriptions(cobHttpParams,null);		
		String testStatus = insightsGenerics.updateCobrandSubscriptionResponseVerificationPostPatch(testCaseId,testExecutionStatus,testSummary,patchCobrandSubscriptionResponse,negativeExpectedValuesMap,testNegativeScenario, testCaseDescription, getCobSubscriptionResponseAfterPatch, insightNameKey, frequency ,duration,insightType,insightTitle, entityParameterName, isSubscribed, threshold_Name_Value_Type,expectedStatus,failureReason,testInfo);
		notificationsSummary(Boolean.valueOf(testStatus),testInfo,testCaseId,insightNameKey,testCaseDescription);		
		if(!Boolean.valueOf(testStatus))
			Assert.fail();
	}
	
	@Test(enabled = true, dataProvider = "feeder")
	@Source("\\TestData\\CSVFiles\\Insights\\AggregateInsuranceAccount\\PatchCobSubscriptionForAggregateInsuranceAccount.csv")
	public void testPatchCobrandSubscriptionForAggregateInsuranceAccount(String testCaseId ,String testCaseDescription,String insightNameKey,String testNegativeScenario,String insightNameEdit,
			String insightTitle,String container,String cobrandId,String validateKeys,String ruleExpression,String description,String applicableEntity,
			String entityParametersCount,String congigMeta,String entityParameterName,String totalThresholds,String id,String frequency,String isSubscribed,String duration,String insightType,
			String threshold_Name_Value_Type,int expectedStatus,String testEnabled) throws ParseException, InterruptedException, JoseException,IOException {

		commontUtils.isTCEnabled(testEnabled, testCaseId);
		logger.info("Testcase--> "+testCaseId+ "->"+"  Validate PATCH "+insightNameKey +"  Cobrand Subscription ,"+testCaseDescription);
		automationTestResults.setInsights("AggregateInsuranceAccount");
		automationTestResults.setBuildNo(buildNo);
		storyId = testCaseId.substring(0,  7);
		TestInfo testInfo = new TestInfo();
		testInfo.setTestCaseId(testCaseId);
		testInfo.setTestDescription(testCaseDescription);
		executedInsight=insightNameKey;
		boolean testExecutionStatus = false;				
		HttpMethodParameters cobHttpParams = insightsGenerics.updateCobrandSubscriptionProcessing(jwtCobAuthToken,insightNameKey, testNegativeScenario, insightNameEdit, insightTitle, container,
				cobrandId, validateKeys, ruleExpression, description, applicableEntity,	entityParametersCount, entityParameterName, totalThresholds,id, 
				frequency, isSubscribed, duration, insightType,threshold_Name_Value_Type);
		Response patchCobrandSubscriptionResponse = insightUtils.patchCobrandSubscriptions(cobHttpParams,null);
		Response getCobSubscriptionResponseAfterPatch = insightUtils.getCobrandSubscriptions(cobHttpParams,null);		
		String testStatus = insightsGenerics.updateCobrandSubscriptionResponseVerificationPostPatch(testCaseId,testExecutionStatus,testSummary,patchCobrandSubscriptionResponse,negativeExpectedValuesMap,testNegativeScenario, testCaseDescription, getCobSubscriptionResponseAfterPatch, insightNameKey, frequency ,duration,insightType,insightTitle, entityParameterName, isSubscribed, threshold_Name_Value_Type,expectedStatus,failureReason,testInfo);
		notificationsSummary(Boolean.valueOf(testStatus),testInfo,testCaseId,insightNameKey,testCaseDescription);		
		if(!Boolean.valueOf(testStatus))
			Assert.fail();
	}
	
	@Test(enabled = true, dataProvider = "feeder")
	@Source("\\TestData\\CSVFiles\\Insights\\AggregateMortgageAccount\\PatchCobSubscriptionForAggregateMortgageAccount.csv")
	public void testPatchCobrandSubscriptionForAggregateMortgageAccount(String testCaseId ,String testCaseDescription,String insightNameKey,String testNegativeScenario,String insightNameEdit,
			String insightTitle,String container,String cobrandId,String validateKeys,String ruleExpression,String description,String applicableEntity,
			String entityParametersCount,String congigMeta,String entityParameterName,String totalThresholds,String id,String frequency,String isSubscribed,String duration,String insightType,
			String threshold_Name_Value_Type,int expectedStatus,String testEnabled) throws ParseException, InterruptedException, JoseException,IOException {

		commontUtils.isTCEnabled(testEnabled, testCaseId);
		logger.info("Testcase--> "+testCaseId+ "->"+"  Validate PATCH "+insightNameKey +"  Cobrand Subscription ,"+testCaseDescription);
		automationTestResults.setInsights("AggregateMortgageAccount");
		automationTestResults.setBuildNo(buildNo);
		storyId = testCaseId.substring(0,  7);
		TestInfo testInfo = new TestInfo();
		testInfo.setTestCaseId(testCaseId);
		testInfo.setTestDescription(testCaseDescription);
		executedInsight=insightNameKey;
		boolean testExecutionStatus = false;				
		HttpMethodParameters cobHttpParams = insightsGenerics.updateCobrandSubscriptionProcessing(jwtCobAuthToken,insightNameKey, testNegativeScenario, insightNameEdit, insightTitle, container,
				cobrandId, validateKeys, ruleExpression, description, applicableEntity,	entityParametersCount, entityParameterName, totalThresholds,id, 
				frequency, isSubscribed, duration, insightType,threshold_Name_Value_Type);
		Response patchCobrandSubscriptionResponse = insightUtils.patchCobrandSubscriptions(cobHttpParams,null);
		Response getCobSubscriptionResponseAfterPatch = insightUtils.getCobrandSubscriptions(cobHttpParams,null);		
		String testStatus = insightsGenerics.updateCobrandSubscriptionResponseVerificationPostPatch(testCaseId,testExecutionStatus,testSummary,patchCobrandSubscriptionResponse,negativeExpectedValuesMap,testNegativeScenario, testCaseDescription, getCobSubscriptionResponseAfterPatch, insightNameKey, frequency ,duration,insightType,insightTitle, entityParameterName, isSubscribed, threshold_Name_Value_Type,expectedStatus,failureReason,testInfo);
		notificationsSummary(Boolean.valueOf(testStatus),testInfo,testCaseId,insightNameKey,testCaseDescription);		
		if(!Boolean.valueOf(testStatus))
			Assert.fail();
	}
	
	@Test(enabled = true, dataProvider = "feeder")
	@Source("\\TestData\\CSVFiles\\Insights\\AggregateRealEstateAccount\\PatchCobSubscriptionForAggregateRealEstateAccount.csv")
	public void testPatchCobrandSubscriptionForAggregateRealEstateAccount(String testCaseId ,String testCaseDescription,String insightNameKey,String testNegativeScenario,String insightNameEdit,
			String insightTitle,String container,String cobrandId,String validateKeys,String ruleExpression,String description,String applicableEntity,
			String entityParametersCount,String congigMeta,String entityParameterName,String totalThresholds,String id,String frequency,String isSubscribed,String duration,String insightType,
			String threshold_Name_Value_Type,int expectedStatus,String testEnabled) throws ParseException, InterruptedException, JoseException,IOException {

		commontUtils.isTCEnabled(testEnabled, testCaseId);
		logger.info("Testcase--> "+testCaseId+ "->"+"  Validate PATCH "+insightNameKey +"  Cobrand Subscription ,"+testCaseDescription);
		automationTestResults.setInsights("AggregateRealEstateAccount");
		automationTestResults.setBuildNo(buildNo);
		storyId = testCaseId.substring(0,  7);
		TestInfo testInfo = new TestInfo();
		testInfo.setTestCaseId(testCaseId);
		testInfo.setTestDescription(testCaseDescription);
		executedInsight=insightNameKey;
		boolean testExecutionStatus = false;				
		HttpMethodParameters cobHttpParams = insightsGenerics.updateCobrandSubscriptionProcessing(jwtCobAuthToken,insightNameKey, testNegativeScenario, insightNameEdit, insightTitle, container,
				cobrandId, validateKeys, ruleExpression, description, applicableEntity,	entityParametersCount, entityParameterName, totalThresholds,id, 
				frequency, isSubscribed, duration, insightType,threshold_Name_Value_Type);
		Response patchCobrandSubscriptionResponse = insightUtils.patchCobrandSubscriptions(cobHttpParams,null);
		Response getCobSubscriptionResponseAfterPatch = insightUtils.getCobrandSubscriptions(cobHttpParams,null);		
		String testStatus = insightsGenerics.updateCobrandSubscriptionResponseVerificationPostPatch(testCaseId,testExecutionStatus,testSummary,patchCobrandSubscriptionResponse,negativeExpectedValuesMap,testNegativeScenario, testCaseDescription, getCobSubscriptionResponseAfterPatch, insightNameKey, frequency ,duration,insightType,insightTitle, entityParameterName, isSubscribed, threshold_Name_Value_Type,expectedStatus,failureReason,testInfo);
		notificationsSummary(Boolean.valueOf(testStatus),testInfo,testCaseId,insightNameKey,testCaseDescription);		
		if(!Boolean.valueOf(testStatus))
			Assert.fail();
	}
	
	@Test(enabled = true, dataProvider = "feeder")
	@Source("\\TestData\\CSVFiles\\Insights\\ZeroCardInterestPaid\\PatchCobSubscriptionForZeroCardInterestPaid.csv")
	public void testPatchCobrandSubscriptionForZeroCardInterestPaid(String testCaseId ,String testCaseDescription,String insightNameKey,String testNegativeScenario,String insightNameEdit,
			String insightTitle,String container,String cobrandId,String validateKeys,String ruleExpression,String description,String applicableEntity,
			String entityParametersCount,String congigMeta,String entityParameterName,String totalThresholds,String id,String frequency,String isSubscribed,String duration,String insightType,
			String threshold_Name_Value_Type,int expectedStatus,String testEnabled) throws ParseException, InterruptedException, JoseException,IOException {

		commontUtils.isTCEnabled(testEnabled, testCaseId);
		logger.info("Testcase--> "+testCaseId+ "->"+"  Validate PATCH "+insightNameKey +"  Cobrand Subscription ,"+testCaseDescription);
		automationTestResults.setInsights("ZeroCardInterestPaid");
		automationTestResults.setBuildNo(buildNo);
		storyId = testCaseId.substring(0,  7);
		TestInfo testInfo = new TestInfo();
		testInfo.setTestCaseId(testCaseId);
		testInfo.setTestDescription(testCaseDescription);
		executedInsight=insightNameKey;
		boolean testExecutionStatus = false;
		HttpMethodParameters cobHttpParams = insightsGenerics.updateCobrandSubscriptionProcessing(jwtCobAuthToken,insightNameKey, testNegativeScenario, insightNameEdit, insightTitle, container,
				cobrandId, validateKeys, ruleExpression, description, applicableEntity,	entityParametersCount, entityParameterName, totalThresholds,id,
				frequency, isSubscribed, duration, insightType,threshold_Name_Value_Type);
		Response patchCobrandSubscriptionResponse = insightUtils.patchCobrandSubscriptions(cobHttpParams,null);
		Response getCobSubscriptionResponseAfterPatch = insightUtils.getCobrandSubscriptions(cobHttpParams,null);
		String testStatus = insightsGenerics.updateCobrandSubscriptionResponseVerificationPostPatch(testCaseId,testExecutionStatus,testSummary,patchCobrandSubscriptionResponse,negativeExpectedValuesMap,testNegativeScenario, testCaseDescription, getCobSubscriptionResponseAfterPatch, insightNameKey, frequency ,duration,insightType,insightTitle, entityParameterName, isSubscribed, threshold_Name_Value_Type,expectedStatus,failureReason,testInfo);
		notificationsSummary(Boolean.valueOf(testStatus),testInfo,testCaseId,insightNameKey,testCaseDescription);
		if(!Boolean.valueOf(testStatus))
			Assert.fail();
	}
	
	@Test(enabled = true, dataProvider = "feeder")
	@Source("\\TestData\\CSVFiles\\Insights\\NewSalary\\PatchCobSubscriptionForNewSalary.csv")
	public void testPatchCobrandSubscriptionForNewSalary(String testCaseId ,String testCaseDescription,String insightNameKey,String testNegativeScenario,String insightNameEdit,
			String insightTitle,String container,String cobrandId,String validateKeys,String ruleExpression,String description,String applicableEntity,
			String entityParametersCount,String congigMeta,String entityParameterName,String totalThresholds,String id,String frequency,String isSubscribed,String duration,String insightType,
			String threshold_Name_Value_Type,int expectedStatus,String testEnabled) throws ParseException, InterruptedException, JoseException,IOException {

		commontUtils.isTCEnabled(testEnabled, testCaseId);
		logger.info("Testcase--> "+testCaseId+ "->"+"  Validate PATCH "+insightNameKey +"  Cobrand Subscription ,"+testCaseDescription);
		automationTestResults.setInsights("NewSalary");
		automationTestResults.setBuildNo(buildNo);
		storyId = testCaseId.substring(0,  7);
		TestInfo testInfo = new TestInfo();
		testInfo.setTestCaseId(testCaseId);
		testInfo.setTestDescription(testCaseDescription);
		executedInsight=insightNameKey;
		boolean testExecutionStatus = false;
		HttpMethodParameters cobHttpParams = insightsGenerics.updateCobrandSubscriptionProcessing(jwtCobAuthToken,insightNameKey, testNegativeScenario, insightNameEdit, insightTitle, container,
				cobrandId, validateKeys, ruleExpression, description, applicableEntity,	entityParametersCount, entityParameterName, totalThresholds,id,
				frequency, isSubscribed, duration, insightType,threshold_Name_Value_Type);
		Response patchCobrandSubscriptionResponse = insightUtils.patchCobrandSubscriptions(cobHttpParams,null);
		Response getCobSubscriptionResponseAfterPatch = insightUtils.getCobrandSubscriptions(cobHttpParams,null);
		String testStatus = insightsGenerics.updateCobrandSubscriptionResponseVerificationPostPatch(testCaseId,testExecutionStatus,testSummary,patchCobrandSubscriptionResponse,negativeExpectedValuesMap,testNegativeScenario, testCaseDescription, getCobSubscriptionResponseAfterPatch, insightNameKey, frequency ,duration,insightType,insightTitle, entityParameterName, isSubscribed, threshold_Name_Value_Type,expectedStatus,failureReason,testInfo);
		notificationsSummary(Boolean.valueOf(testStatus),testInfo,testCaseId,insightNameKey,testCaseDescription);
		if(!Boolean.valueOf(testStatus))
			Assert.fail();
	}
	
	@Test(enabled = true, dataProvider = "feeder")
	@Source("\\TestData\\CSVFiles\\Insights\\SubscriptionNotPaid\\PatchCobSubscriptionForSubscriptionNotPaid.csv")
	public void testPatchCobrandSubscriptionForSubscriptionNotPaid(String testCaseId ,String testCaseDescription,String insightNameKey,String testNegativeScenario,String insightNameEdit,
			String insightTitle,String container,String cobrandId,String validateKeys,String ruleExpression,String description,String applicableEntity,
			String entityParametersCount,String congigMeta,String entityParameterName,String totalThresholds,String id,String frequency,String isSubscribed,String duration,String insightType,
			String threshold_Name_Value_Type,int expectedStatus,String testEnabled) throws ParseException, InterruptedException, JoseException,IOException {

		commontUtils.isTCEnabled(testEnabled, testCaseId);
		logger.info("Testcase--> "+testCaseId+ "->"+"  Validate PATCH "+insightNameKey +"  Cobrand Subscription ,"+testCaseDescription);
		automationTestResults.setInsights("SubscriptionNotPaid");
		automationTestResults.setBuildNo(buildNo);
		storyId = testCaseId.substring(0,  7);
		TestInfo testInfo = new TestInfo();
		testInfo.setTestCaseId(testCaseId);
		testInfo.setTestDescription(testCaseDescription);
		executedInsight=insightNameKey;
		boolean testExecutionStatus = false;
		HttpMethodParameters cobHttpParams = insightsGenerics.updateCobrandSubscriptionProcessing(jwtCobAuthToken,insightNameKey, testNegativeScenario, insightNameEdit, insightTitle, container,
				cobrandId, validateKeys, ruleExpression, description, applicableEntity,	entityParametersCount, entityParameterName, totalThresholds,id,
				frequency, isSubscribed, duration, insightType,threshold_Name_Value_Type);
		Response patchCobrandSubscriptionResponse = insightUtils.patchCobrandSubscriptions(cobHttpParams,null);
		Response getCobSubscriptionResponseAfterPatch = insightUtils.getCobrandSubscriptions(cobHttpParams,null);
		String testStatus = insightsGenerics.updateCobrandSubscriptionResponseVerificationPostPatch(testCaseId,testExecutionStatus,testSummary,patchCobrandSubscriptionResponse,negativeExpectedValuesMap,testNegativeScenario, testCaseDescription, getCobSubscriptionResponseAfterPatch, insightNameKey, frequency ,duration,insightType,insightTitle, entityParameterName, isSubscribed, threshold_Name_Value_Type,expectedStatus,failureReason,testInfo);
		notificationsSummary(Boolean.valueOf(testStatus),testInfo,testCaseId,insightNameKey,testCaseDescription);
		if(!Boolean.valueOf(testStatus))
			Assert.fail();
	}

	@Test(enabled = true, dataProvider = "feeder")
	@Source("\\TestData\\CSVFiles\\Insights\\AggregateSavingsCheckingAccount\\PatchCobSubscriptionForAggregateSavingsCheckingAccount.csv")
	public void testPatchCobrandSubscriptionForAggregateSavingsCheckingAccount(String testCaseId ,String testCaseDescription,String insightNameKey,String testNegativeScenario,String insightNameEdit,
			String insightTitle,String container,String cobrandId,String validateKeys,String ruleExpression,String description,String applicableEntity,
			String entityParametersCount,String congigMeta,String entityParameterName,String totalThresholds,String id,String frequency,String isSubscribed,String duration,String insightType,
			String threshold_Name_Value_Type,int expectedStatus,String testEnabled) throws ParseException, InterruptedException, JoseException,IOException {

		commontUtils.isTCEnabled(testEnabled, testCaseId);
		logger.info("Testcase--> "+testCaseId+ "->"+"  Validate PATCH "+insightNameKey +"  Cobrand Subscription ,"+testCaseDescription);
		automationTestResults.setInsights("AggregateSavingsCheckingAccount");
		automationTestResults.setBuildNo(buildNo);
		storyId = testCaseId.substring(0,  7);
		TestInfo testInfo = new TestInfo();
		testInfo.setTestCaseId(testCaseId);
		testInfo.setTestDescription(testCaseDescription);
		executedInsight=insightNameKey;
		boolean testExecutionStatus = false;				
		HttpMethodParameters cobHttpParams = insightsGenerics.updateCobrandSubscriptionProcessing(jwtCobAuthToken,insightNameKey, testNegativeScenario, insightNameEdit, insightTitle, container,
				cobrandId, validateKeys, ruleExpression, description, applicableEntity,	entityParametersCount, entityParameterName, totalThresholds,id, 
				frequency, isSubscribed, duration, insightType,threshold_Name_Value_Type);
		Response patchCobrandSubscriptionResponse = insightUtils.patchCobrandSubscriptions(cobHttpParams,null);
		Response getCobSubscriptionResponseAfterPatch = insightUtils.getCobrandSubscriptions(cobHttpParams,null);		
		String testStatus = insightsGenerics.updateCobrandSubscriptionResponseVerificationPostPatch(testCaseId,testExecutionStatus,testSummary,patchCobrandSubscriptionResponse,negativeExpectedValuesMap,testNegativeScenario, testCaseDescription, getCobSubscriptionResponseAfterPatch, insightNameKey, frequency ,duration,insightType,insightTitle, entityParameterName, isSubscribed, threshold_Name_Value_Type,expectedStatus,failureReason,testInfo);
		notificationsSummary(Boolean.valueOf(testStatus),testInfo,testCaseId,insightNameKey,testCaseDescription);		
		if(!Boolean.valueOf(testStatus))
			Assert.fail();
	}
	
	@Test(enabled = true, dataProvider = "feeder")
	@Source("\\TestData\\CSVFiles\\Insights\\AggregateInvestmentAccount\\PatchCobSubscriptionForAggregateInvestmentAccount.csv")
	public void testPatchCobrandSubscriptionForAggregateInvestmentAccount(String testCaseId ,String testCaseDescription,String insightNameKey,String testNegativeScenario,String insightNameEdit,
			String insightTitle,String container,String cobrandId,String validateKeys,String ruleExpression,String description,String applicableEntity,
			String entityParametersCount,String congigMeta,String entityParameterName,String totalThresholds,String id,String frequency,String isSubscribed,String duration,String insightType,
			String threshold_Name_Value_Type,int expectedStatus,String testEnabled) throws ParseException, InterruptedException, JoseException,IOException {

		commontUtils.isTCEnabled(testEnabled, testCaseId);
		logger.info("Testcase--> "+testCaseId+ "->"+"  Validate PATCH "+insightNameKey +"  Cobrand Subscription ,"+testCaseDescription);
		automationTestResults.setInsights("AggregateInvestmentAccount");
		automationTestResults.setBuildNo(buildNo);
		storyId = testCaseId.substring(0,  7);
		TestInfo testInfo = new TestInfo();
		testInfo.setTestCaseId(testCaseId);
		testInfo.setTestDescription(testCaseDescription);
		executedInsight=insightNameKey;
		boolean testExecutionStatus = false;				
		HttpMethodParameters cobHttpParams = insightsGenerics.updateCobrandSubscriptionProcessing(jwtCobAuthToken,insightNameKey, testNegativeScenario, insightNameEdit, insightTitle, container,
				cobrandId, validateKeys, ruleExpression, description, applicableEntity,	entityParametersCount, entityParameterName, totalThresholds,id, 
				frequency, isSubscribed, duration, insightType,threshold_Name_Value_Type);
		Response patchCobrandSubscriptionResponse = insightUtils.patchCobrandSubscriptions(cobHttpParams,null);
		Response getCobSubscriptionResponseAfterPatch = insightUtils.getCobrandSubscriptions(cobHttpParams,null);		
		String testStatus = insightsGenerics.updateCobrandSubscriptionResponseVerificationPostPatch(testCaseId,testExecutionStatus,testSummary,patchCobrandSubscriptionResponse,negativeExpectedValuesMap,testNegativeScenario, testCaseDescription, getCobSubscriptionResponseAfterPatch, insightNameKey, frequency ,duration,insightType,insightTitle, entityParameterName, isSubscribed, threshold_Name_Value_Type,expectedStatus,failureReason,testInfo);
		notificationsSummary(Boolean.valueOf(testStatus),testInfo,testCaseId,insightNameKey,testCaseDescription);		
		if(!Boolean.valueOf(testStatus))
			Assert.fail();
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

}
