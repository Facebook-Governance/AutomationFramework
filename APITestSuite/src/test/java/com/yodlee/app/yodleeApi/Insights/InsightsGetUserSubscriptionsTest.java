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

public class InsightsGetUserSubscriptionsTest extends Base {
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
	HashMap<String, ArrayList<String>> negativeExpectedValuesMap;
	static Map<String, String> testSummary = new HashMap<String, String>();
	String executedInsight = null;
	String jwtUserAuthToken = null, jwtCobAuthToken = null;
	InsightsGenerics insightsGenerics = new InsightsGenerics();
	static FailureReason failureReason = null;
	String cobrandUser = "", storyId = "", buildNo = "240", loginUser = "";
	AutomationTestResults automationTestResults = new AutomationTestResults();
	static Map<String, String> notificationsTestSummary = new HashMap<String, String>();
	List<TestInfo> testsInfoList = null;
	JwtHelper jwtHelper = new JwtHelper();
	InsightsDBUtils insightsDBUtils = new InsightsDBUtils();
	Response cobSubscriptionResponse = null;
	Response userSubscriptionResponse = null;
	HashMap<String, Object> userQueryParam = new HashMap<>();
	String envCobrand = null;
	String envUser = null;
	User userInfo = null;
	InsightsProperties prop = new InsightsProperties();

	@BeforeClass(alwaysRun = true)
	public void setUp() throws Exception {
		System.out.println("initiated GET User subscription API execution");
		userInfo = User.builder().build();
		userInfo.setUsername("GETUser" + System.currentTimeMillis());
		loginUser = userInfo.getUsername();
		userInfo.setPassword("TEST@123");
		sessionObj = EnvSession.builder().cobSession(configurationObj.getCobrandSessionObj().getCobSession())
				.path(configurationObj.getCobrandSessionObj().getPath()).build();
		userHelper.getUserSession(userInfo, sessionObj);
		cobrandUser = prop.readPropertiesFile().getProperty("cobrandUser");
	}

	@BeforeMethod(firstTimeOnly = true)
	public void initializations() throws Exception {
		envCobrand = prop.readPropertiesFile().getProperty("envCobrand");
		envUser = prop.readPropertiesFile().getProperty("envUser");
		jwtCobAuthToken = "Bearer ".concat(jwtHelper.getJWTToken(cobrandUser, envCobrand));
		jwtUserAuthToken = "Bearer ".concat(jwtHelper.getJWTToken(userInfo.getUsername(), envUser));
		testSummary = new HashMap<String, String>();
		failureReason = new FailureReason();
		testsInfoList = new ArrayList<TestInfo>();
		HttpMethodParameters httpParams = insightsHelper.getHttpParams("cobrandSubscriptionRequest", null, null,
				jwtCobAuthToken, null, null, null, null);
		cobSubscriptionResponse = insightUtils.getCobrandSubscriptions(httpParams, null);
		Assert.assertEquals(cobSubscriptionResponse.getStatusCode(), 200);
	}

	@Test(enabled = true, dataProvider = "feeder")
	@Source("\\TestData\\CSVFiles\\Insights\\LowBalWarning\\GetUserSubscriptionLowBalWarning.csv")
	public void testGetUserSubscriptionLowBalWarning(String testCaseId, String testCaseDescription, String triggerType,
			String InsightKey, String insightName, String title, String containerSupported, String validateKeys,
			String description, String applicableEntity, String levels, String isSubscribed, String frequency,
			String thresholdDetails, String checkRuleExpression, String configMetaAndentityParameter,
			int expectedStatus, String tcEnabled) throws ParseException, InterruptedException, IOException {
		boolean testExecutionStatus = false;
		commontUtils.isTCEnabled(tcEnabled, testCaseId);
		logger.info("Testcase--> " + testCaseId + "->" + "  Validate GET " + InsightKey + "  User Subscription ,"
				+ testCaseDescription);
		automationTestResults.setInsights("USER_SUB_LowBalWarn");
		automationTestResults.setBuildNo(buildNo);
		executedInsight = InsightKey;
		JsonArray keysArray = insightsGenerics.getInsightKeys("GETPATCHLowBalanceWarning");
		int numberOfKeys = insightsGenerics.getTotalNumberOfKeys(keysArray, validateKeys);
		storyId = testCaseId.substring(0, 7);
		TestInfo testInfo = new TestInfo();
		testInfo.setTestCaseId(testCaseId);
		testInfo.setTestDescription(testCaseDescription);
		HttpMethodParameters httpUserParams = insightsHelper.getHttpParams("userSubscriptionRequest", null, null,
				jwtUserAuthToken, null, null, null, null);
		userQueryParam.put("name", InsightKey);
		httpUserParams.setQueryParams(userQueryParam);
		userSubscriptionResponse = insightUtils.getUserSubscription(httpUserParams, null);
		Assert.assertEquals(userSubscriptionResponse.getStatusCode(), 200);
		String testStatus = insightsGenerics.getUserSubscriptionResponseVerification(cobSubscriptionResponse,
				userSubscriptionResponse, testExecutionStatus, testSummary, testCaseId, testCaseDescription,
				triggerType, InsightKey, insightName, title, containerSupported, keysArray,
				String.valueOf(numberOfKeys), description, applicableEntity, levels, isSubscribed, frequency,
				thresholdDetails, checkRuleExpression, configMetaAndentityParameter, expectedStatus, failureReason,
				validateKeys, testInfo);
		notificationsSummary(Boolean.valueOf(testStatus), testInfo, testCaseId, insightName, testCaseDescription);
		if (!Boolean.valueOf(testStatus))
			Assert.fail();

	}

	@Test(enabled = true, dataProvider = "feeder")
	@Source("\\TestData\\CSVFiles\\Insights\\GoalSavingsOffTrack\\GetUserSubscriptionGoalOffTrack.csv")
	public void testGetUserSubscriptionGoalOffTrack(String testCaseId, String testCaseDescription, String triggerType,
			String InsightKey, String insightName, String title, String containerSupported, String validateKeys,
			String totalNumberOfKeys, String description, String applicableEntity, String levels, String isSubscribed,
			String frequency, String thresholdDetails, String checkRuleExpression, String configMetaAndentityParameter,
			int expectedStatus, String tcEnabled) throws ParseException, InterruptedException, IOException {

		boolean testExecutionStatus = false;
		commontUtils.isTCEnabled(tcEnabled, testCaseId);
		logger.info("Testcase--> " + testCaseId + "->" + "  Validate GET " + InsightKey + "  User Subscription ,"
				+ testCaseDescription);
		automationTestResults.setInsights("USER_SUB_GoalOffTrack");
		automationTestResults.setBuildNo(buildNo);
		executedInsight = InsightKey;
		JsonArray keysArray = insightsGenerics.getInsightKeys("GETPATCHGoalOffTrack");
		int numberOfKeys = insightsGenerics.getTotalNumberOfKeys(keysArray, validateKeys);
		storyId = testCaseId.substring(0, 7);
		TestInfo testInfo = new TestInfo();
		testInfo.setTestCaseId(testCaseId);
		testInfo.setTestDescription(testCaseDescription);
		HttpMethodParameters httpUserParams = insightsHelper.getHttpParams("userSubscriptionRequest", null, null,
				jwtUserAuthToken, null, null, null, null);
		userQueryParam.put("name", InsightKey);
		httpUserParams.setQueryParams(userQueryParam);
		userSubscriptionResponse = insightUtils.getUserSubscription(httpUserParams, null);
		Assert.assertEquals(userSubscriptionResponse.getStatusCode(), 200);
		String testStatus = insightsGenerics.getUserSubscriptionResponseVerification(cobSubscriptionResponse,
				userSubscriptionResponse, testExecutionStatus, testSummary, testCaseId, testCaseDescription,
				triggerType, InsightKey, insightName, title, containerSupported, keysArray,
				String.valueOf(numberOfKeys), description, applicableEntity, levels, isSubscribed, frequency,
				thresholdDetails, checkRuleExpression, configMetaAndentityParameter, expectedStatus, failureReason,
				validateKeys, testInfo);
		notificationsSummary(Boolean.valueOf(testStatus), testInfo, testCaseId, insightName, testCaseDescription);
		if (!Boolean.valueOf(testStatus))
			Assert.fail();

	}

	@Test(enabled = true, dataProvider = "feeder")
	@Source("\\TestData\\CSVFiles\\Insights\\ChangeInNetworth\\GetUserSubscriptionNetworthChange.csv")
	public void testGetUserSubscriptionNetworthChange(String testCaseId, String testCaseDescription, String triggerType,
			String InsightKey, String insightName, String title, String containerSupported, String validateKeys,
			String totalNumberOfKeys, String description, String applicableEntity, String levels, String isSubscribed,
			String frequency, String thresholdDetails, String checkRuleExpression, String configMetaAndentityParameter,
			int expectedStatus, String tcEnabled) throws ParseException, InterruptedException, IOException {
		boolean testExecutionStatus = false;
		commontUtils.isTCEnabled(tcEnabled, testCaseId);
		logger.info("Testcase--> " + testCaseId + "->" + "  Validate GET " + InsightKey + "  User Subscription ,"
				+ testCaseDescription);
		automationTestResults.setInsights("USER_SUB_NetworthChange");
		automationTestResults.setBuildNo(buildNo);
		executedInsight = InsightKey;
		JsonArray keysArray = insightsGenerics.getInsightKeys("GETPATCHNetworthChange");
		int numberOfKeys = insightsGenerics.getTotalNumberOfKeys(keysArray, validateKeys);
		storyId = testCaseId.substring(0, 7);
		TestInfo testInfo = new TestInfo();
		testInfo.setTestCaseId(testCaseId);
		testInfo.setTestDescription(testCaseDescription);
		HttpMethodParameters httpUserParams = insightsHelper.getHttpParams("userSubscriptionRequest", null, null,
				jwtUserAuthToken, null, null, null, null);
		userQueryParam.put("name", InsightKey);
		httpUserParams.setQueryParams(userQueryParam);
		userSubscriptionResponse = insightUtils.getUserSubscription(httpUserParams, null);
		Assert.assertEquals(userSubscriptionResponse.getStatusCode(), 200);
		String testStatus = insightsGenerics.getUserSubscriptionResponseVerification(cobSubscriptionResponse,
				userSubscriptionResponse, testExecutionStatus, testSummary, testCaseId, testCaseDescription,
				triggerType, InsightKey, insightName, title, containerSupported, keysArray,
				String.valueOf(numberOfKeys), description, applicableEntity, levels, isSubscribed, frequency,
				thresholdDetails, checkRuleExpression, configMetaAndentityParameter, expectedStatus, failureReason,
				validateKeys, testInfo);
		notificationsSummary(Boolean.valueOf(testStatus), testInfo, testCaseId, insightName, testCaseDescription);
		if (!Boolean.valueOf(testStatus))
			Assert.fail();
	}

	@Test(enabled = true, dataProvider = "feeder")
	@Source("\\TestData\\CSVFiles\\Insights\\SalDeposited\\GetUserSubscriptionSalaryDeposited.csv")
	public void testGetUserSubscriptionSalaryDeposited(String testCaseId, String testCaseDescription,
			String triggerType, String InsightKey, String insightName, String title, String containerSupported,
			String validateKeys, String totalNumberOfKeys, String description, String applicableEntity, String levels,
			String isSubscribed, String frequency, String thresholdDetails, String checkRuleExpression,
			String configMetaAndentityParameter, int expectedStatus, String tcEnabled)
			throws ParseException, InterruptedException, IOException {
		boolean testExecutionStatus = false;
		commontUtils.isTCEnabled(tcEnabled, testCaseId);
		logger.info("Testcase--> " + testCaseId + "->" + "  Validate GET " + InsightKey + "  User Subscription ,"
				+ testCaseDescription);
		automationTestResults.setInsights("USER_SUB_SalaryDeposited");
		automationTestResults.setBuildNo(buildNo);
		executedInsight = InsightKey;
		JsonArray keysArray = insightsGenerics.getInsightKeys("GETPATCHSalaryDeposited");
		int numberOfKeys = insightsGenerics.getTotalNumberOfKeys(keysArray, validateKeys);
		storyId = testCaseId.substring(0, 7);
		TestInfo testInfo = new TestInfo();
		testInfo.setTestCaseId(testCaseId);
		testInfo.setTestDescription(testCaseDescription);
		HttpMethodParameters httpUserParams = insightsHelper.getHttpParams("userSubscriptionRequest", null, null,
				jwtUserAuthToken, null, null, null, null);
		userQueryParam.put("name", InsightKey);
		httpUserParams.setQueryParams(userQueryParam);
		userSubscriptionResponse = insightUtils.getUserSubscription(httpUserParams, null);
		Assert.assertEquals(userSubscriptionResponse.getStatusCode(), 200);
		String testStatus = insightsGenerics.getUserSubscriptionResponseVerification(cobSubscriptionResponse,
				userSubscriptionResponse, testExecutionStatus, testSummary, testCaseId, testCaseDescription,
				triggerType, InsightKey, insightName, title, containerSupported, keysArray,
				String.valueOf(numberOfKeys), description, applicableEntity, levels, isSubscribed, frequency,
				thresholdDetails, checkRuleExpression, configMetaAndentityParameter, expectedStatus, failureReason,
				validateKeys, testInfo);
		notificationsSummary(Boolean.valueOf(testStatus), testInfo, testCaseId, insightName, testCaseDescription);
		if (!Boolean.valueOf(testStatus))
			Assert.fail();
	}

	@Test(enabled = true, dataProvider = "feeder")
	@Source("\\TestData\\CSVFiles\\Insights\\CameinUnderBudget\\GetUserSubscriptionCameinUnderBudget.csv")
	public void testGetUserSubscriptionCameinUnderBudget(String testCaseId, String testCaseDescription,
			String triggerType, String InsightKey, String insightName, String title, String containerSupported,
			String validateKeys, String totalNumberOfKeys, String description, String applicableEntity, String levels,
			String isSubscribed, String frequency, String thresholdDetails, String checkRuleExpression,
			String configMetaAndentityParameter, int expectedStatus, String tcEnabled)
			throws ParseException, InterruptedException, IOException {
		boolean testExecutionStatus = false;
		commontUtils.isTCEnabled(tcEnabled, testCaseId);
		logger.info("Testcase--> " + testCaseId + "->" + "  Validate GET " + InsightKey + "  User Subscription ,"
				+ testCaseDescription);
		automationTestResults.setInsights("USER_SUB_CameinUnderBudget");
		automationTestResults.setBuildNo(buildNo);
		executedInsight = InsightKey;
		JsonArray keysArray = insightsGenerics.getInsightKeys("GETPATCHCameinUnderBudget");
		int numberOfKeys = insightsGenerics.getTotalNumberOfKeys(keysArray, validateKeys);
		storyId = testCaseId.substring(0, 7);
		TestInfo testInfo = new TestInfo();
		testInfo.setTestCaseId(testCaseId);
		testInfo.setTestDescription(testCaseDescription);
		HttpMethodParameters httpUserParams = insightsHelper.getHttpParams("userSubscriptionRequest", null, null,
				jwtUserAuthToken, null, null, null, null);
		userQueryParam.put("name", InsightKey);
		httpUserParams.setQueryParams(userQueryParam);
		userSubscriptionResponse = insightUtils.getUserSubscription(httpUserParams, null);
		Assert.assertEquals(userSubscriptionResponse.getStatusCode(), 200);
		String testStatus = insightsGenerics.getUserSubscriptionResponseVerification(cobSubscriptionResponse,
				userSubscriptionResponse, testExecutionStatus, testSummary, testCaseId, testCaseDescription,
				triggerType, InsightKey, insightName, title, containerSupported, keysArray,
				String.valueOf(numberOfKeys), description, applicableEntity, levels, isSubscribed, frequency,
				thresholdDetails, checkRuleExpression, configMetaAndentityParameter, expectedStatus, failureReason,
				validateKeys, testInfo);
		notificationsSummary(Boolean.valueOf(testStatus), testInfo, testCaseId, insightName, testCaseDescription);
		if (!Boolean.valueOf(testStatus))
			Assert.fail();
	}

	@Test(enabled = true, dataProvider = "feeder")
	@Source("\\TestData\\CSVFiles\\Insights\\ExpensesApproachingBudget\\GetUserSubscriptionExpensesApproachingBudget.csv")
	public void testGetUserSubscriptionExpensesApproachingBudget(String testCaseId, String testCaseDescription,
			String triggerType, String InsightKey, String insightName, String title, String containerSupported,
			String validateKeys, String totalNumberOfKeys, String description, String applicableEntity, String levels,
			String isSubscribed, String frequency, String thresholdDetails, String checkRuleExpression,
			String configMetaAndentityParameter, int expectedStatus, String tcEnabled)
			throws ParseException, InterruptedException, IOException {
		boolean testExecutionStatus = false;
		commontUtils.isTCEnabled(tcEnabled, testCaseId);
		logger.info("Testcase--> " + testCaseId + "->" + "  Validate GET " + InsightKey + "  User Subscription ,"
				+ testCaseDescription);
		automationTestResults.setInsights("USER_SUB_ExpensesApproachingBudget");
		automationTestResults.setBuildNo(buildNo);
		executedInsight = InsightKey;
		JsonArray keysArray = insightsGenerics.getInsightKeys("GETPATCHExpensesApproachingBudget");
		int numberOfKeys = insightsGenerics.getTotalNumberOfKeys(keysArray, validateKeys);
		storyId = testCaseId.substring(0, 7);
		TestInfo testInfo = new TestInfo();
		testInfo.setTestCaseId(testCaseId);
		testInfo.setTestDescription(testCaseDescription);
		HttpMethodParameters httpUserParams = insightsHelper.getHttpParams("userSubscriptionRequest", null, null,
				jwtUserAuthToken, null, null, null, null);
		userQueryParam.put("name", InsightKey);
		httpUserParams.setQueryParams(userQueryParam);
		userSubscriptionResponse = insightUtils.getUserSubscription(httpUserParams, null);
		Assert.assertEquals(userSubscriptionResponse.getStatusCode(), 200);
		String testStatus = insightsGenerics.getUserSubscriptionResponseVerification(cobSubscriptionResponse,
				userSubscriptionResponse, testExecutionStatus, testSummary, testCaseId, testCaseDescription,
				triggerType, InsightKey, insightName, title, containerSupported, keysArray,
				String.valueOf(numberOfKeys), description, applicableEntity, levels, isSubscribed, frequency,
				thresholdDetails, checkRuleExpression, configMetaAndentityParameter, expectedStatus, failureReason,
				validateKeys, testInfo);
		notificationsSummary(Boolean.valueOf(testStatus), testInfo, testCaseId, insightName, testCaseDescription);
		if (!Boolean.valueOf(testStatus))
			Assert.fail(failureReason.getFailureReason());
	}

	@Test(enabled = true, dataProvider = "feeder")
	@Source("\\TestData\\CSVFiles\\Insights\\LargeDepositNotice\\GetUserSubscriptionLargeDepositNotice.csv")
	public void testGetUserSubscriptionLargeDepositNotice(String testCaseId, String testCaseDescription,
			String triggerType, String InsightKey, String insightName, String title, String containerSupported,
			String validateKeys, String description, String applicableEntity, String levels, String isSubscribed,
			String frequency, String thresholdDetails, String checkRuleExpression, String configMetaAndentityParameter,
			int expectedStatus, String tcEnabled) throws ParseException, InterruptedException, IOException {
		boolean testExecutionStatus = false;
		commontUtils.isTCEnabled(tcEnabled, testCaseId);
		logger.info("Testcase--> " + testCaseId + "->" + "  Validate GET " + InsightKey + "  User Subscription ,"
				+ testCaseDescription);
		automationTestResults.setInsights("USER_SUB_LargeDepositNotice");
		automationTestResults.setBuildNo(buildNo);
		executedInsight = InsightKey;
		JsonArray keysArray = insightsGenerics.getInsightKeys("GETPATCHLargeDepositNotice");
		int numberOfKeys = insightsGenerics.getTotalNumberOfKeys(keysArray, validateKeys);
		storyId = testCaseId.substring(0, 7);
		TestInfo testInfo = new TestInfo();
		testInfo.setTestCaseId(testCaseId);
		testInfo.setTestDescription(testCaseDescription);
		HttpMethodParameters httpUserParams = insightsHelper.getHttpParams("userSubscriptionRequest", null, null,
				jwtUserAuthToken, null, null, null, null);
		userQueryParam.put("name", InsightKey);
		httpUserParams.setQueryParams(userQueryParam);
		userSubscriptionResponse = insightUtils.getUserSubscription(httpUserParams, null);
		Assert.assertEquals(userSubscriptionResponse.getStatusCode(), 200);
		String testStatus = insightsGenerics.getUserSubscriptionResponseVerification(cobSubscriptionResponse,
				userSubscriptionResponse, testExecutionStatus, testSummary, testCaseId, testCaseDescription,
				triggerType, InsightKey, insightName, title, containerSupported, keysArray,
				String.valueOf(numberOfKeys), description, applicableEntity, levels, isSubscribed, frequency,
				thresholdDetails, checkRuleExpression, configMetaAndentityParameter, expectedStatus, failureReason,
				validateKeys, testInfo);
		notificationsSummary(Boolean.valueOf(testStatus), testInfo, testCaseId, insightName, testCaseDescription);
		if (!Boolean.valueOf(testStatus))
			Assert.fail();
	}

	@Test(enabled = true, dataProvider = "feeder")
	@Source("\\TestData\\CSVFiles\\Insights\\CreditLimitWarning1\\GetUserSubscriptionCreditLimitWarning.csv")
	public void testGetUserSubscriptionCreditLimitWarning(String testCaseId, String testCaseDescription,
			String triggerType, String InsightKey, String insightName, String title, String containerSupported,
			String validateKeys, String totalNumberOfKeys, String description, String applicableEntity, String levels,
			String isSubscribed, String frequency, String thresholdDetails, String checkRuleExpression,
			String configMetaAndentityParameter, int expectedStatus, String tcEnabled)
			throws ParseException, InterruptedException, IOException {
		boolean testExecutionStatus = false;
		commontUtils.isTCEnabled(tcEnabled, testCaseId);
		logger.info("Testcase--> " + testCaseId + "->" + "  Validate GET " + InsightKey + "  User Subscription ,"
				+ testCaseDescription);
		automationTestResults.setInsights("USER_SUB_CreditLimitWarning");
		automationTestResults.setBuildNo(buildNo);
		executedInsight = InsightKey;
		JsonArray keysArray = insightsGenerics.getInsightKeys("GETPATCHCreditLimitWarning");
		int numberOfKeys = insightsGenerics.getTotalNumberOfKeys(keysArray, validateKeys);
		storyId = testCaseId.substring(0, 7);
		TestInfo testInfo = new TestInfo();
		testInfo.setTestCaseId(testCaseId);
		testInfo.setTestDescription(testCaseDescription);
		HttpMethodParameters httpUserParams = insightsHelper.getHttpParams("userSubscriptionRequest", null, null,
				jwtUserAuthToken, null, null, null, null);
		userQueryParam.put("name", InsightKey);
		httpUserParams.setQueryParams(userQueryParam);
		userSubscriptionResponse = insightUtils.getUserSubscription(httpUserParams, null);
		Assert.assertEquals(userSubscriptionResponse.getStatusCode(), 200);
		String testStatus = insightsGenerics.getUserSubscriptionResponseVerification(cobSubscriptionResponse,
				userSubscriptionResponse, testExecutionStatus, testSummary, testCaseId, testCaseDescription,
				triggerType, InsightKey, insightName, title, containerSupported, keysArray,
				String.valueOf(numberOfKeys), description, applicableEntity, levels, isSubscribed, frequency,
				thresholdDetails, checkRuleExpression, configMetaAndentityParameter, expectedStatus, failureReason,
				validateKeys, testInfo);
		notificationsSummary(Boolean.valueOf(testStatus), testInfo, testCaseId, insightName, testCaseDescription);
		if (!Boolean.valueOf(testStatus))
			Assert.fail(failureReason.getFailureReason());
	}

	@Test(enabled = true, dataProvider = "feeder")
	@Source("\\TestData\\CSVFiles\\Insights\\CreditUtilization1\\GetUserSubscriptionCreditUtilization.csv")
	public void testGetUserSubscriptionCreditUtilization(String testCaseId, String testCaseDescription,
			String triggerType, String InsightKey, String insightName, String title, String containerSupported,
			String validateKeys, String totalNumberOfKeys, String description, String applicableEntity, String levels,
			String isSubscribed, String frequency, String thresholdDetails, String checkRuleExpression,
			String configMetaAndentityParameter, int expectedStatus, String tcEnabled)
			throws ParseException, InterruptedException, IOException {
		boolean testExecutionStatus = false;
		commontUtils.isTCEnabled(tcEnabled, testCaseId);
		logger.info("Testcase--> " + testCaseId + "->" + "  Validate GET " + InsightKey + "  User Subscription ,"
				+ testCaseDescription);
		automationTestResults.setInsights("USER_SUB_CreditUtilization");
		automationTestResults.setBuildNo(buildNo);
		executedInsight = InsightKey;
		JsonArray keysArray = insightsGenerics.getInsightKeys("GETPATCHCreditUtilization");
		int numberOfKeys = insightsGenerics.getTotalNumberOfKeys(keysArray, validateKeys);
		storyId = testCaseId.substring(0, 7);
		TestInfo testInfo = new TestInfo();
		testInfo.setTestCaseId(testCaseId);
		testInfo.setTestDescription(testCaseDescription);
		HttpMethodParameters httpUserParams = insightsHelper.getHttpParams("userSubscriptionRequest", null, null,
				jwtUserAuthToken, null, null, null, null);
		userQueryParam.put("name", InsightKey);
		httpUserParams.setQueryParams(userQueryParam);
		userSubscriptionResponse = insightUtils.getUserSubscription(httpUserParams, null);
		Assert.assertEquals(userSubscriptionResponse.getStatusCode(), 200);
		String testStatus = insightsGenerics.getUserSubscriptionResponseVerification(cobSubscriptionResponse,
				userSubscriptionResponse, testExecutionStatus, testSummary, testCaseId, testCaseDescription,
				triggerType, InsightKey, insightName, title, containerSupported, keysArray,
				String.valueOf(numberOfKeys), description, applicableEntity, levels, isSubscribed, frequency,
				thresholdDetails, checkRuleExpression, configMetaAndentityParameter, expectedStatus, failureReason,
				validateKeys, testInfo);
		notificationsSummary(Boolean.valueOf(testStatus), testInfo, testCaseId, insightName, testCaseDescription);
		if (!Boolean.valueOf(testStatus))
			Assert.fail(failureReason.getFailureReason());
	}

	@Test(enabled = true, dataProvider = "feeder")
	@Source("\\TestData\\CSVFiles\\Insights\\CardStatementDueReminder\\GetUserSubscriptionCardStatementDueReminder.csv")
	public void testGetUserSubscriptionCardStatementDueReminder(String testCaseId, String testCaseDescription,
			String triggerType, String InsightKey, String insightName, String title, String containerSupported,
			String validateKeys, String totalNumberOfKeys, String description, String applicableEntity, String levels,
			String isSubscribed, String frequency, String thresholdDetails, String checkRuleExpression,
			String configMetaAndentityParameter, int expectedStatus, String tcEnabled)
			throws ParseException, InterruptedException, IOException {
		boolean testExecutionStatus = false;
		commontUtils.isTCEnabled(tcEnabled, testCaseId);
		logger.info("Testcase--> " + testCaseId + "->" + "  Validate GET " + InsightKey + "  User Subscription ,"
				+ testCaseDescription);
		automationTestResults.setInsights("USER_SUB_CardStatementDueReminder");
		automationTestResults.setBuildNo(buildNo);
		executedInsight = InsightKey;
		JsonArray keysArray = insightsGenerics.getInsightKeys("GETPATCHCardStatementDueReminder");
		int numberOfKeys = insightsGenerics.getTotalNumberOfKeys(keysArray, validateKeys);
		storyId = testCaseId.substring(0, 7);
		TestInfo testInfo = new TestInfo();
		testInfo.setTestCaseId(testCaseId);
		testInfo.setTestDescription(testCaseDescription);
		HttpMethodParameters httpUserParams = insightsHelper.getHttpParams("userSubscriptionRequest", null, null,
				jwtUserAuthToken, null, null, null, null);
		userQueryParam.put("name", InsightKey);
		httpUserParams.setQueryParams(userQueryParam);
		userSubscriptionResponse = insightUtils.getUserSubscription(httpUserParams, null);
		Assert.assertEquals(userSubscriptionResponse.getStatusCode(), 200);
		String testStatus = insightsGenerics.getUserSubscriptionResponseVerification(cobSubscriptionResponse,
				userSubscriptionResponse, testExecutionStatus, testSummary, testCaseId, testCaseDescription,
				triggerType, InsightKey, insightName, title, containerSupported, keysArray,
				String.valueOf(numberOfKeys), description, applicableEntity, levels, isSubscribed, frequency,
				thresholdDetails, checkRuleExpression, configMetaAndentityParameter, expectedStatus, failureReason,
				validateKeys, testInfo);
		notificationsSummary(Boolean.valueOf(testStatus), testInfo, testCaseId, insightName, testCaseDescription);
		if (!Boolean.valueOf(testStatus))
			Assert.fail(failureReason.getFailureReason());
	}

	@Test(enabled = true, dataProvider = "feeder")
	@Source("\\TestData\\CSVFiles\\Insights\\ChangeInLiability\\GetUserSubscriptionLiabilityChange.csv")
	public void testGetUserSubscriptionLiabilityChange(String testCaseId, String testCaseDescription,
			String triggerType, String InsightKey, String insightName, String title, String containerSupported,
			String validateKeys, String totalNumberOfKeys, String description, String applicableEntity, String levels,
			String isSubscribed, String frequency, String thresholdDetails, String checkRuleExpression,
			String configMetaAndentityParameter, int expectedStatus, String tcEnabled)
			throws ParseException, InterruptedException, IOException {
		boolean testExecutionStatus = false;
		commontUtils.isTCEnabled(tcEnabled, testCaseId);
		logger.info("Testcase--> " + testCaseId + "->" + "  Validate GET " + InsightKey + "  User Subscription ,"
				+ testCaseDescription);
		automationTestResults.setInsights("USER_SUB_LiabilityChange");
		automationTestResults.setBuildNo(buildNo);
		executedInsight = InsightKey;
		JsonArray keysArray = insightsGenerics.getInsightKeys("GETPATCHLiabilityChange");
		int numberOfKeys = insightsGenerics.getTotalNumberOfKeys(keysArray, validateKeys);
		storyId = testCaseId.substring(0, 7);
		TestInfo testInfo = new TestInfo();
		testInfo.setTestCaseId(testCaseId);
		testInfo.setTestDescription(testCaseDescription);
		HttpMethodParameters httpUserParams = insightsHelper.getHttpParams("userSubscriptionRequest", null, null,
				jwtUserAuthToken, null, null, null, null);
		userQueryParam.put("name", InsightKey);
		httpUserParams.setQueryParams(userQueryParam);
		userSubscriptionResponse = insightUtils.getUserSubscription(httpUserParams, null);
		Assert.assertEquals(userSubscriptionResponse.getStatusCode(), 200);
		String testStatus = insightsGenerics.getUserSubscriptionResponseVerification(cobSubscriptionResponse,
				userSubscriptionResponse, testExecutionStatus, testSummary, testCaseId, testCaseDescription,
				triggerType, InsightKey, insightName, title, containerSupported, keysArray,
				String.valueOf(numberOfKeys), description, applicableEntity, levels, isSubscribed, frequency,
				thresholdDetails, checkRuleExpression, configMetaAndentityParameter, expectedStatus, failureReason,
				validateKeys, testInfo);
		notificationsSummary(Boolean.valueOf(testStatus), testInfo, testCaseId, insightName, testCaseDescription);
		if (!Boolean.valueOf(testStatus))
			Assert.fail();
	}

	@Test(enabled = true, dataProvider = "feeder")
	@Source("\\TestData\\CSVFiles\\Insights\\ChangeInAsset\\GetUserSubscriptionChangeInAsset.csv")
	public void testGetUserSubscriptionAssetChange(String testCaseId, String testCaseDescription, String triggerType,
			String InsightKey, String insightName, String title, String containerSupported, String validateKeys,
			String totalNumberOfKeys, String description, String applicableEntity, String levels, String isSubscribed,
			String frequency, String thresholdDetails, String checkRuleExpression, String configMetaAndentityParameter,
			int expectedStatus, String tcEnabled) throws ParseException, InterruptedException, IOException {
		boolean testExecutionStatus = false;
		commontUtils.isTCEnabled(tcEnabled, testCaseId);
		logger.info("Testcase--> " + testCaseId + "->" + "  Validate GET " + InsightKey + "  User Subscription ,"
				+ testCaseDescription);
		automationTestResults.setInsights("USER_SUB_AssetChange");
		automationTestResults.setBuildNo(buildNo);
		executedInsight = InsightKey;
		JsonArray keysArray = insightsGenerics.getInsightKeys("GETPATCHAssetChange");
		int numberOfKeys = insightsGenerics.getTotalNumberOfKeys(keysArray, validateKeys);
		storyId = testCaseId.substring(0, 7);
		TestInfo testInfo = new TestInfo();
		testInfo.setTestCaseId(testCaseId);
		testInfo.setTestDescription(testCaseDescription);
		HttpMethodParameters httpUserParams = insightsHelper.getHttpParams("userSubscriptionRequest", null, null,
				jwtUserAuthToken, null, null, null, null);
		userQueryParam.put("name", InsightKey);
		httpUserParams.setQueryParams(userQueryParam);
		userSubscriptionResponse = insightUtils.getUserSubscription(httpUserParams, null);
		Assert.assertEquals(userSubscriptionResponse.getStatusCode(), 200);
		String testStatus = insightsGenerics.getUserSubscriptionResponseVerification(cobSubscriptionResponse,
				userSubscriptionResponse, testExecutionStatus, testSummary, testCaseId, testCaseDescription,
				triggerType, InsightKey, insightName, title, containerSupported, keysArray,
				String.valueOf(numberOfKeys), description, applicableEntity, levels, isSubscribed, frequency,
				thresholdDetails, checkRuleExpression, configMetaAndentityParameter, expectedStatus, failureReason,
				validateKeys, testInfo);
		notificationsSummary(Boolean.valueOf(testStatus), testInfo, testCaseId, insightName, testCaseDescription);
		if (!Boolean.valueOf(testStatus))
			Assert.fail();
	}

	/*
	 * @Test(enabled = true, dataProvider = "feeder")
	 * 
	 * @Source(
	 * "\\TestData\\CSVFiles\\Insights\\CardStatementAvailable\\GetUserSubscriptionCardStatementAvailable.csv")
	 * public void testGetUserSubscriptionCardStatementAvailable(String
	 * testCaseId,String testCaseDescription,String triggerType,String
	 * InsightKey,String insightName,String title, String containerSupported,String
	 * keys,String totalNumberOfKeys ,String description,String
	 * applicableEntity,String levels,String isSubscribed,String frequency, String
	 * thresholdDetails,String checkRuleExpression,String
	 * configMetaAndentityParameter,int expectedStatus,String tcEnabled) throws
	 * ParseException, InterruptedException { boolean testExecutionStatus = false;
	 * commontUtils.isTCEnabled(tcEnabled, testCaseId);
	 * logger.info("Testcase--> "+testCaseId+ "->"+"  Validate GET "+InsightKey
	 * +"  User Subscription ,"+testCaseDescription); executedInsight = InsightKey;
	 * HttpMethodParameters httpCobParams = HttpMethodParameters.builder().build();
	 * httpCobParams.setHeader("Authorization", jwtCobAuthToken); Response
	 * cobSubscriptionResponse =
	 * insightUtils.getCobrandSubscriptions(httpCobParams);
	 * Assert.assertEquals(cobSubscriptionResponse.getStatusCode(), expectedStatus);
	 * HttpMethodParameters httpUserParams = HttpMethodParameters.builder().build();
	 * httpUserParams.setHeader("Authorization", jwtUserAuthToken); Response
	 * userSubscriptionResponse = insightUtils.getUserSubscription(httpUserParams);
	 * //insightsGenerics.getUserSubscriptionResponseVerification(
	 * cobSubscriptionResponse,userSubscriptionResponse,testExecutionStatus,
	 * testSummary,testCaseId, testCaseDescription, triggerType, InsightKey,
	 * insightName, title, containerSupported,keys,totalNumberOfKeys,description,
	 * applicableEntity, levels, isSubscribed,
	 * frequency,thresholdDetails,checkRuleExpression,configMetaAndentityParameter,
	 * expectedStatus,failureReason);
	 * 
	 * }
	 */

	@Test(enabled = true, dataProvider = "feeder")
	@Source("\\TestData\\CSVFiles\\Insights\\upcomingBills\\GetUserSubscriptionUpcomingBills.csv")
	public void testGetUserUpcomingBills(String testCaseId, String testCaseDescription, String triggerType,
			String InsightKey, String insightName, String title, String containerSupported, String validateKeys,
			String totalNumberOfKeys, String description, String applicableEntity, String levels, String isSubscribed,
			String frequency, String thresholdDetails, String checkRuleExpression, String configMetaAndentityParameter,
			int expectedStatus, String tcEnabled) throws ParseException, InterruptedException, IOException {
		boolean testExecutionStatus = false;
		commontUtils.isTCEnabled(tcEnabled, testCaseId);
		logger.info("Testcase--> " + testCaseId + "->" + "  Validate GET " + InsightKey + "  User Subscription ,"
				+ testCaseDescription);
		automationTestResults.setInsights("USER_SUB_UpcomingBills");
		automationTestResults.setBuildNo(buildNo);
		executedInsight = InsightKey;
		JsonArray keysArray = insightsGenerics.getInsightKeys("UpcomingBills");
		int numberOfKeys = insightsGenerics.getTotalNumberOfKeys(keysArray, validateKeys);
		storyId = testCaseId.substring(0, 7);
		TestInfo testInfo = new TestInfo();
		testInfo.setTestCaseId(testCaseId);
		testInfo.setTestDescription(testCaseDescription);
		HttpMethodParameters httpUserParams = insightsHelper.getHttpParams("userSubscriptionRequest", null, null,
				jwtUserAuthToken, null, null, null, null);
		userQueryParam.put("name", InsightKey);
		httpUserParams.setQueryParams(userQueryParam);
		userSubscriptionResponse = insightUtils.getUserSubscription(httpUserParams, null);
		Assert.assertEquals(userSubscriptionResponse.getStatusCode(), 200);
		String testStatus = insightsGenerics.getUserSubscriptionResponseVerification(cobSubscriptionResponse,
				userSubscriptionResponse, testExecutionStatus, testSummary, testCaseId, testCaseDescription,
				triggerType, InsightKey, insightName, title, containerSupported, keysArray,
				String.valueOf(numberOfKeys), description, applicableEntity, levels, isSubscribed, frequency,
				thresholdDetails, checkRuleExpression, configMetaAndentityParameter, expectedStatus, failureReason,
				validateKeys, testInfo);
		notificationsSummary(Boolean.valueOf(testStatus), testInfo, testCaseId, insightName, testCaseDescription);
		if (!Boolean.valueOf(testStatus))
			Assert.fail();

	}

	@Test(enabled = true, dataProvider = "feeder")
	@Source("\\TestData\\CSVFiles\\Insights\\upcomingSubscription\\GetUserSubscriptionUpcomingSubscription.csv")
	public void testGetUserUpcomingSubscription(String testCaseId, String testCaseDescription, String triggerType,
			String InsightKey, String insightName, String title, String containerSupported, String validateKeys,
			String totalNumberOfKeys, String description, String applicableEntity, String levels, String isSubscribed,
			String frequency, String thresholdDetails, String checkRuleExpression, String configMetaAndentityParameter,
			int expectedStatus, String tcEnabled) throws ParseException, InterruptedException, IOException {
		boolean testExecutionStatus = false;
		commontUtils.isTCEnabled(tcEnabled, testCaseId);
		logger.info("Testcase--> " + testCaseId + "->" + "  Validate GET " + InsightKey + "  User Subscription ,"
				+ testCaseDescription);
		automationTestResults.setInsights("USER_SUB_UpcomingSubscription");
		automationTestResults.setBuildNo(buildNo);
		executedInsight = InsightKey;
		JsonArray keysArray = insightsGenerics.getInsightKeys("UpcomingSubscription");
		int numberOfKeys = insightsGenerics.getTotalNumberOfKeys(keysArray, validateKeys);
		storyId = testCaseId.substring(0, 7);
		TestInfo testInfo = new TestInfo();
		testInfo.setTestCaseId(testCaseId);
		testInfo.setTestDescription(testCaseDescription);
		HttpMethodParameters httpUserParams = insightsHelper.getHttpParams("userSubscriptionRequest", null, null,
				jwtUserAuthToken, null, null, null, null);
		userQueryParam.put("name", InsightKey);
		httpUserParams.setQueryParams(userQueryParam);
		userSubscriptionResponse = insightUtils.getUserSubscription(httpUserParams, null);
		Assert.assertEquals(userSubscriptionResponse.getStatusCode(), 200);
		String testStatus = insightsGenerics.getUserSubscriptionResponseVerification(cobSubscriptionResponse,
				userSubscriptionResponse, testExecutionStatus, testSummary, testCaseId, testCaseDescription,
				triggerType, InsightKey, insightName, title, containerSupported, keysArray,
				String.valueOf(numberOfKeys), description, applicableEntity, levels, isSubscribed, frequency,
				thresholdDetails, checkRuleExpression, configMetaAndentityParameter, expectedStatus, failureReason,
				validateKeys, testInfo);
		notificationsSummary(Boolean.valueOf(testStatus), testInfo, testCaseId, insightName, testCaseDescription);
		if (!Boolean.valueOf(testStatus))
			Assert.fail();

	}

	@Test(enabled = true, dataProvider = "feeder")
	@Source("\\TestData\\CSVFiles\\Insights\\NewBillEventDetected\\GetUserSubscriptionNewBill.csv")
	public void testGetUserNewBill(String testCaseId, String testCaseDescription, String triggerType, String InsightKey,
			String insightName, String title, String containerSupported, String validateKeys, String totalNumberOfKeys,
			String description, String applicableEntity, String levels, String isSubscribed, String frequency,
			String insightType, String thresholdDetails, String checkRuleExpression,
			String configMetaAndentityParameter, int expectedStatus, String tcEnabled)
			throws ParseException, InterruptedException, IOException {
		boolean testExecutionStatus = false;
		commontUtils.isTCEnabled(tcEnabled, testCaseId);
		logger.info("Testcase--> " + testCaseId + "->" + "  Validate GET " + InsightKey + "  User Subscription ,"
				+ testCaseDescription);
		automationTestResults.setInsights("USER_SUB_NewBill");
		automationTestResults.setBuildNo(buildNo);
		executedInsight = InsightKey;
		JsonArray keysArray = insightsGenerics.getInsightKeys("NewBill");
		int numberOfKeys = insightsGenerics.getTotalNumberOfKeys(keysArray, validateKeys);
		storyId = testCaseId.substring(0, 7);
		TestInfo testInfo = new TestInfo();
		testInfo.setTestCaseId(testCaseId);
		testInfo.setTestDescription(testCaseDescription);
		HttpMethodParameters httpUserParams = insightsHelper.getHttpParams("userSubscriptionRequest", null, null,
				jwtUserAuthToken, null, null, null, null);
		userQueryParam.put("name", InsightKey);
		httpUserParams.setQueryParams(userQueryParam);
		userSubscriptionResponse = insightUtils.getUserSubscription(httpUserParams, null);
		Assert.assertEquals(userSubscriptionResponse.getStatusCode(), 200);
		String testStatus = insightsGenerics.getUserSubscriptionResponseVerification(cobSubscriptionResponse,
				userSubscriptionResponse, testExecutionStatus, testSummary, testCaseId, testCaseDescription,
				triggerType, InsightKey, insightName, title, containerSupported, keysArray,
				String.valueOf(numberOfKeys), description, applicableEntity, levels, isSubscribed, frequency,
				thresholdDetails, checkRuleExpression, configMetaAndentityParameter, expectedStatus, failureReason,
				validateKeys, testInfo);
		notificationsSummary(Boolean.valueOf(testStatus), testInfo, testCaseId, insightName, testCaseDescription);
		if (!Boolean.valueOf(testStatus))
			Assert.fail();

	}

	@Test(enabled = true, dataProvider = "feeder")
	@Source("\\TestData\\CSVFiles\\Insights\\NewSubscriptionEventDetected\\GetUserSubscriptionNewSubscription.csv")
	public void testGetUserNewSubscription(String testCaseId, String testCaseDescription, String triggerType,
			String InsightKey, String insightName, String title, String containerSupported, String validateKeys,
			String totalNumberOfKeys, String description, String applicableEntity, String levels, String isSubscribed,
			String frequency, String insightType, String thresholdDetails, String checkRuleExpression,
			String configMetaAndentityParameter, int expectedStatus, String tcEnabled)
			throws ParseException, InterruptedException, IOException {
		boolean testExecutionStatus = false;
		commontUtils.isTCEnabled(tcEnabled, testCaseId);
		logger.info("Testcase--> " + testCaseId + "->" + "  Validate GET " + InsightKey + "  User Subscription ,"
				+ testCaseDescription);
		automationTestResults.setInsights("USER_SUB_NewSubscription");
		automationTestResults.setBuildNo(buildNo);
		executedInsight = InsightKey;
		JsonArray keysArray = insightsGenerics.getInsightKeys("NewSubscription");
		int numberOfKeys = insightsGenerics.getTotalNumberOfKeys(keysArray, validateKeys);
		storyId = testCaseId.substring(0, 7);
		TestInfo testInfo = new TestInfo();
		testInfo.setTestCaseId(testCaseId);
		testInfo.setTestDescription(testCaseDescription);
		HttpMethodParameters httpUserParams = insightsHelper.getHttpParams("userSubscriptionRequest", null, null,
				jwtUserAuthToken, null, null, null, null);
		userQueryParam.put("name", InsightKey);
		httpUserParams.setQueryParams(userQueryParam);
		userSubscriptionResponse = insightUtils.getUserSubscription(httpUserParams, null);
		Assert.assertEquals(userSubscriptionResponse.getStatusCode(), 200);
		String testStatus = insightsGenerics.getUserSubscriptionResponseVerification(cobSubscriptionResponse,
				userSubscriptionResponse, testExecutionStatus, testSummary, testCaseId, testCaseDescription,
				triggerType, InsightKey, insightName, title, containerSupported, keysArray,
				String.valueOf(numberOfKeys), description, applicableEntity, levels, isSubscribed, frequency,
				thresholdDetails, checkRuleExpression, configMetaAndentityParameter, expectedStatus, failureReason,
				validateKeys, testInfo);
		notificationsSummary(Boolean.valueOf(testStatus), testInfo, testCaseId, insightName, testCaseDescription);
		if (!Boolean.valueOf(testStatus))
			Assert.fail();

	}

	@Test(enabled = true, dataProvider = "feeder")
	@Source("\\TestData\\CSVFiles\\Insights\\BillNotPaid\\GetUserSubscriptionBillNotPaid.csv")
	public void testGetUserSubscriptionBillNotPaid(String testCaseId, String testCaseDescription, String triggerType,
			String InsightKey, String insightName, String title, String containerSupported, String validateKeys,
			String totalNumberOfKeys, String description, String applicableEntity, String levels, String isSubscribed,
			String frequency, String thresholdDetails, String checkRuleExpression, String configMetaAndentityParameter,
			int expectedStatus, String tcEnabled) throws ParseException, InterruptedException, IOException {
		boolean testExecutionStatus = false;
		commontUtils.isTCEnabled(tcEnabled, testCaseId);
		logger.info("Testcase--> " + testCaseId + "->" + "  Validate GET " + InsightKey + "  User Subscription ,"
				+ testCaseDescription);
		automationTestResults.setInsights("USER_SUB_BillNotPaid");
		automationTestResults.setBuildNo(buildNo);
		executedInsight = InsightKey;
		JsonArray keysArray = insightsGenerics.getInsightKeys("BillNotPaid");
		int numberOfKeys = insightsGenerics.getTotalNumberOfKeys(keysArray, validateKeys);
		storyId = testCaseId.substring(0, 7);
		TestInfo testInfo = new TestInfo();
		testInfo.setTestCaseId(testCaseId);
		testInfo.setTestDescription(testCaseDescription);
		HttpMethodParameters httpUserParams = insightsHelper.getHttpParams("userSubscriptionRequest", null, null,
				jwtUserAuthToken, null, null, null, null);
		userQueryParam.put("name", InsightKey);
		httpUserParams.setQueryParams(userQueryParam);
		userSubscriptionResponse = insightUtils.getUserSubscription(httpUserParams, null);
		Assert.assertEquals(userSubscriptionResponse.getStatusCode(), 200);
		String testStatus = insightsGenerics.getUserSubscriptionResponseVerification(cobSubscriptionResponse,
				userSubscriptionResponse, testExecutionStatus, testSummary, testCaseId, testCaseDescription,
				triggerType, InsightKey, insightName, title, containerSupported, keysArray,
				String.valueOf(numberOfKeys), description, applicableEntity, levels, isSubscribed, frequency,
				thresholdDetails, checkRuleExpression, configMetaAndentityParameter, expectedStatus, failureReason,
				validateKeys, testInfo);
		notificationsSummary(Boolean.valueOf(testStatus), testInfo, testCaseId, insightName, testCaseDescription);
		if (!Boolean.valueOf(testStatus))
			Assert.fail();
	}

	@Test(enabled = true, dataProvider = "feeder")
	@Source("\\TestData\\CSVFiles\\Insights\\FinancialFees\\GetUserSubscriptionFinancialFees.csv")
	public void testGetUserSubscriptionFinancialFees(String testCaseId, String testCaseDescription, String triggerType,
			String InsightKey, String insightName, String title, String containerSupported, String validateKeys,
			String description, String applicableEntity, String levels, String isSubscribed, String frequency,
			String thresholdDetails, String checkRuleExpression, String configMetaAndentityParameter,
			String expectedStatus, String tcEnabled) throws ParseException, InterruptedException, IOException {
		boolean testExecutionStatus = false;
		int numberOfKeys = 0;
		commontUtils.isTCEnabled(tcEnabled, testCaseId);
		logger.info("Testcase--> " + testCaseId + "->" + "  Validate GET " + InsightKey + "  User Subscription ,"
				+ testCaseDescription);
		automationTestResults.setInsights("USER_SUB_FinancialFees");
		automationTestResults.setBuildNo(buildNo);
		executedInsight = InsightKey;
		JsonArray insightKeysArray = insightsGenerics.getInsightKeysforFinancialFees(insightName, "PATCH");
		JsonArray pbKeysArray = insightsGenerics.getInsightsKeysforPBPATCH(insightName);
		if (validateKeys.equalsIgnoreCase("view"))
			numberOfKeys = insightsGenerics.getTotalNumberofFinancialFeesInsightKeys(validateKeys, insightName,
					"PATCH");
		else
			numberOfKeys = insightsGenerics.getTotalNumberofPBInsihgtKeys(validateKeys, insightName, "PATCH");
		insightKeysArray.addAll(pbKeysArray);
		storyId = testCaseId.substring(0, 7);
		TestInfo testInfo = new TestInfo();
		testInfo.setTestCaseId(testCaseId);
		testInfo.setTestDescription(testCaseDescription);
		HttpMethodParameters httpUserParams = insightsHelper.getHttpParams("userSubscriptionRequest", null, null,
				jwtUserAuthToken, null, null, null, null);
		userSubscriptionResponse = insightUtils.getUserSubscription(httpUserParams, null);
		Assert.assertEquals(userSubscriptionResponse.getStatusCode(), 200);
		String testStatus = insightsGenerics.getUserSubscriptionResponseVerification(cobSubscriptionResponse,
				userSubscriptionResponse, testExecutionStatus, testSummary, testCaseId, testCaseDescription,
				triggerType, InsightKey, insightName, title, containerSupported, insightKeysArray,
				String.valueOf(numberOfKeys), description, applicableEntity, levels, isSubscribed, frequency,
				thresholdDetails, checkRuleExpression, configMetaAndentityParameter, Integer.parseInt(expectedStatus),
				failureReason, validateKeys, testInfo);
		notificationsSummary(Boolean.valueOf(testStatus), testInfo, testCaseId, insightName, testCaseDescription);
		if (!Boolean.valueOf(testStatus))
			Assert.fail();
	}

	@Test(enabled = true, dataProvider = "feeder")
	@Source("\\TestData\\CSVFiles\\Insights\\SpendingbyCategory\\GetUserSubscriptionSpendingbyCategory.csv")
	public void testGetUserSubscriptionSpendingbyCategory(String testCaseId, String testCaseDescription,
			String triggerType, String InsightKey, String insightName, String title, String containerSupported,
			String validateKeys, String description, String applicableEntity, String levels, String isSubscribed,
			String frequency, String thresholdDetails, String checkRuleExpression, String configMetaAndentityParameter,
			String expectedStatus, String tcEnabled) throws ParseException, InterruptedException, IOException {
		boolean testExecutionStatus = false;
		int numberOfKeys = 0;
		commontUtils.isTCEnabled(tcEnabled, testCaseId);
		logger.info("Testcase--> " + testCaseId + "->" + "  Validate GET " + InsightKey + "  User Subscription ,"
				+ testCaseDescription);
		automationTestResults.setInsights("USER_SUB_SpendingbyCategory");
		automationTestResults.setBuildNo(buildNo);
		executedInsight = InsightKey;
		JsonArray insightKeysArray = insightsGenerics.getInsightKeysforFinancialFees(insightName, "PATCH");
		JsonArray pbKeysArray = insightsGenerics.getInsightsKeysforPBPATCH(insightName);
		if (validateKeys.equalsIgnoreCase("view"))
			numberOfKeys = insightsGenerics.getTotalNumberofFinancialFeesInsightKeys(validateKeys, insightName,
					"PATCH");
		else
			numberOfKeys = insightsGenerics.getTotalNumberofPBInsihgtKeys(validateKeys, insightName, "PATCH");
		insightKeysArray.addAll(pbKeysArray);
		storyId = testCaseId.substring(0, 7);
		TestInfo testInfo = new TestInfo();
		testInfo.setTestCaseId(testCaseId);
		testInfo.setTestDescription(testCaseDescription);
		HttpMethodParameters httpUserParams = insightsHelper.getHttpParams("userSubscriptionRequest", null, null,
				jwtUserAuthToken, null, null, null, null);
		userSubscriptionResponse = insightUtils.getUserSubscription(httpUserParams, null);
		Assert.assertEquals(userSubscriptionResponse.getStatusCode(), 200);
		String testStatus = insightsGenerics.getUserSubscriptionResponseVerification(cobSubscriptionResponse,
				userSubscriptionResponse, testExecutionStatus, testSummary, testCaseId, testCaseDescription,
				triggerType, InsightKey, insightName, title, containerSupported, insightKeysArray,
				String.valueOf(numberOfKeys), description, applicableEntity, levels, isSubscribed, frequency,
				thresholdDetails, checkRuleExpression, configMetaAndentityParameter, Integer.parseInt(expectedStatus),
				failureReason, validateKeys, testInfo);
		notificationsSummary(Boolean.valueOf(testStatus), testInfo, testCaseId, insightName, testCaseDescription);
		if (!Boolean.valueOf(testStatus))
			Assert.fail();
	}

	@Test(enabled = true, dataProvider = "feeder")
	@Source("\\TestData\\CSVFiles\\Insights\\TopMerchants\\GetUserSubscriptionTopMerchants.csv")
	public void testGetUserSubscriptionTopMerchants(String testCaseId, String testCaseDescription, String triggerType,
			String InsightKey, String insightName, String title, String containerSupported, String validateKeys,
			String description, String applicableEntity, String levels, String isSubscribed, String frequency,
			String thresholdDetails, String checkRuleExpression, String configMetaAndentityParameter,
			String expectedStatus, String tcEnabled) throws ParseException, InterruptedException, IOException {
		boolean testExecutionStatus = false;
		int numberOfKeys = 0;
		commontUtils.isTCEnabled(tcEnabled, testCaseId);
		logger.info("Testcase--> " + testCaseId + "->" + "  Validate GET " + InsightKey + "  User Subscription ,"
				+ testCaseDescription);
		automationTestResults.setInsights("USER_SUB_TopMerchants");
		automationTestResults.setBuildNo(buildNo);
		executedInsight = InsightKey;
		JsonArray insightKeysArray = insightsGenerics.getInsightKeysforFinancialFees(insightName, "PATCH");
		JsonArray pbKeysArray = insightsGenerics.getInsightsKeysforPBPATCH(insightName);
		if (validateKeys.equalsIgnoreCase("view"))
			numberOfKeys = insightsGenerics.getTotalNumberofFinancialFeesInsightKeys(validateKeys, insightName,
					"PATCH");
		else
			numberOfKeys = insightsGenerics.getTotalNumberofPBInsihgtKeys(validateKeys, insightName, "PATCH");
		insightKeysArray.addAll(pbKeysArray);
		storyId = testCaseId.substring(0, 7);
		TestInfo testInfo = new TestInfo();
		testInfo.setTestCaseId(testCaseId);
		testInfo.setTestDescription(testCaseDescription);
		HttpMethodParameters httpUserParams = insightsHelper.getHttpParams("userSubscriptionRequest", null, null,
				jwtUserAuthToken, null, null, null, null);
		userSubscriptionResponse = insightUtils.getUserSubscription(httpUserParams, null);
		Assert.assertEquals(userSubscriptionResponse.getStatusCode(), 200);
		String testStatus = insightsGenerics.getUserSubscriptionResponseVerification(cobSubscriptionResponse,
				userSubscriptionResponse, testExecutionStatus, testSummary, testCaseId, testCaseDescription,
				triggerType, InsightKey, insightName, title, containerSupported, insightKeysArray,
				String.valueOf(numberOfKeys), description, applicableEntity, levels, isSubscribed, frequency,
				thresholdDetails, checkRuleExpression, configMetaAndentityParameter, Integer.parseInt(expectedStatus),
				failureReason, validateKeys, testInfo);
		notificationsSummary(Boolean.valueOf(testStatus), testInfo, testCaseId, insightName, testCaseDescription);
		if (!Boolean.valueOf(testStatus))
			Assert.fail();
	}

	@Test(enabled = true, dataProvider = "feeder")
	@Source("\\TestData\\CSVFiles\\Insights\\IncomeExpenseOverview\\GetUserSubscriptionIncomeExpenseOverview.csv")
	public void testGetUserIncomeExpenseOverview(String testCaseId, String testCaseDescription, String triggerType,
			String InsightKey, String insightName, String title, String containerSupported, String validateKeys,
			String totalNumberOfKeys, String description, String applicableEntity, String levels, String isSubscribed,
			String frequency, String duration, String insightType, String thresholdDetails, String checkRuleExpression,
			String configMetaAndentityParameter, int expectedStatus, String tcEnabled)
			throws ParseException, InterruptedException, IOException {
		boolean testExecutionStatus = false;
		commontUtils.isTCEnabled(tcEnabled, testCaseId);
		logger.info("Testcase--> " + testCaseId + "->" + "  Validate GET " + InsightKey + "  User Subscription ,"
				+ testCaseDescription);
		automationTestResults.setInsights("USER_SUB_IncomeExpenseOverview");
		automationTestResults.setBuildNo(buildNo);
		executedInsight = InsightKey;
		JsonArray keysArray = insightsGenerics.getInsightKeys("IncomeExpenseOverview");
		int numberOfKeys = insightsGenerics.getTotalNumberOfKeys(keysArray, validateKeys);
		storyId = testCaseId.substring(0, 7);
		TestInfo testInfo = new TestInfo();
		testInfo.setTestCaseId(testCaseId);
		testInfo.setTestDescription(testCaseDescription);
		HttpMethodParameters httpUserParams = insightsHelper.getHttpParams("userSubscriptionRequest", null, null,
				jwtUserAuthToken, null, null, null, null);
		userQueryParam.put("name", InsightKey);
		httpUserParams.setQueryParams(userQueryParam);
		userSubscriptionResponse = insightUtils.getUserSubscription(httpUserParams, null);
		Assert.assertEquals(userSubscriptionResponse.getStatusCode(), 200);
		String testStatus = insightsGenerics.getUserSubscriptionResponseVerification(cobSubscriptionResponse,
				userSubscriptionResponse, testExecutionStatus, testSummary, testCaseId, testCaseDescription,
				triggerType, InsightKey, insightName, title, containerSupported, keysArray,
				String.valueOf(numberOfKeys), description, applicableEntity, levels, isSubscribed, frequency,
				thresholdDetails, checkRuleExpression, configMetaAndentityParameter, expectedStatus, failureReason,
				validateKeys, testInfo);
		notificationsSummary(Boolean.valueOf(testStatus), testInfo, testCaseId, insightName, testCaseDescription);
		if (!Boolean.valueOf(testStatus))
			Assert.fail();
	}

	@Test(enabled = true, dataProvider = "feeder")
	@Source("\\TestData\\CSVFiles\\Insights\\BudgetProgress\\GetUserSubscriptionBudgetProgress.csv")
	public void testGetUserBudgetProgress(String testCaseId, String testCaseDescription, String triggerType,
			String InsightKey, String insightName, String title, String containerSupported, String validateKeys,
			String totalNumberOfKeys, String description, String applicableEntity, String levels, String isSubscribed,
			String frequency, String insightType, String thresholdDetails, String checkRuleExpression,
			String configMetaAndentityParameter, int expectedStatus, String tcEnabled)
			throws ParseException, InterruptedException, IOException {
		boolean testExecutionStatus = false;
		commontUtils.isTCEnabled(tcEnabled, testCaseId);
		logger.info("Testcase--> " + testCaseId + "->" + "  Validate GET " + InsightKey + "  User Subscription ,"
				+ testCaseDescription);
		automationTestResults.setInsights("USER_SUB_BudgetProgress");
		automationTestResults.setBuildNo(buildNo);
		executedInsight = InsightKey;
		JsonArray keysArray = insightsGenerics.getInsightKeys("BudgetProgress");
		int numberOfKeys = insightsGenerics.getTotalNumberOfKeys(keysArray, validateKeys);
		storyId = testCaseId.substring(0, 7);
		TestInfo testInfo = new TestInfo();
		testInfo.setTestCaseId(testCaseId);
		testInfo.setTestDescription(testCaseDescription);
		HttpMethodParameters httpUserParams = insightsHelper.getHttpParams("userSubscriptionRequest", null, null,
				jwtUserAuthToken, null, null, null, null);
		userQueryParam.put("name", InsightKey);
		httpUserParams.setQueryParams(userQueryParam);
		userSubscriptionResponse = insightUtils.getUserSubscription(httpUserParams, null);
		Assert.assertEquals(userSubscriptionResponse.getStatusCode(), 200);
		String testStatus = insightsGenerics.getUserSubscriptionResponseVerification(cobSubscriptionResponse,
				userSubscriptionResponse, testExecutionStatus, testSummary, testCaseId, testCaseDescription,
				triggerType, InsightKey, insightName, title, containerSupported, keysArray,
				String.valueOf(numberOfKeys), description, applicableEntity, levels, isSubscribed, frequency,
				thresholdDetails, checkRuleExpression, configMetaAndentityParameter, expectedStatus, failureReason,
				validateKeys, testInfo);
		notificationsSummary(Boolean.valueOf(testStatus), testInfo, testCaseId, insightName, testCaseDescription);
		if (!Boolean.valueOf(testStatus))
			Assert.fail();
	}

	@Test(enabled = true, dataProvider = "feeder")
	@Source("\\TestData\\CSVFiles\\Insights\\RefundDeposit\\GetUserSubscriptionRefundDeposit.csv")
	public void testGetUserRefundDeposit(String testCaseId, String testCaseDescription, String triggerType,
			String InsightKey, String insightName, String title, String containerSupported, String validateKeys,
			String totalNumberOfKeys, String description, String applicableEntity, String levels, String isSubscribed,
			String frequency, String thresholdDetails, String checkRuleExpression, String configMetaAndentityParameter,
			int expectedStatus, String tcEnabled) throws ParseException, InterruptedException, IOException {
		boolean testExecutionStatus = false;
		commontUtils.isTCEnabled(tcEnabled, testCaseId);
		logger.info("Testcase--> " + testCaseId + "->" + "  Validate GET " + InsightKey + "  User Subscription ,"
				+ testCaseDescription);
		automationTestResults.setInsights("USER_SUB_RefundDeposit");
		automationTestResults.setBuildNo(buildNo);
		executedInsight = InsightKey;
		JsonArray keysArray = insightsGenerics.getInsightKeys("RefundDepositNotice");
		int numberOfKeys = insightsGenerics.getTotalNumberOfKeys(keysArray, validateKeys);
		storyId = testCaseId.substring(0, 7);
		TestInfo testInfo = new TestInfo();
		testInfo.setTestCaseId(testCaseId);
		testInfo.setTestDescription(testCaseDescription);
		HttpMethodParameters httpUserParams = insightsHelper.getHttpParams("userSubscriptionRequest", null, null,
				jwtUserAuthToken, null, null, null, null);
		userQueryParam.put("name", InsightKey);
		httpUserParams.setQueryParams(userQueryParam);
		userSubscriptionResponse = insightUtils.getUserSubscription(httpUserParams, null);
		Assert.assertEquals(userSubscriptionResponse.getStatusCode(), 200);
		String testStatus = insightsGenerics.getUserSubscriptionResponseVerification(cobSubscriptionResponse,
				userSubscriptionResponse, testExecutionStatus, testSummary, testCaseId, testCaseDescription,
				triggerType, InsightKey, insightName, title, containerSupported, keysArray,
				String.valueOf(numberOfKeys), description, applicableEntity, levels, isSubscribed, frequency,
				thresholdDetails, checkRuleExpression, configMetaAndentityParameter, expectedStatus, failureReason,
				validateKeys, testInfo);
		notificationsSummary(Boolean.valueOf(testStatus), testInfo, testCaseId, insightName, testCaseDescription);
		if (!Boolean.valueOf(testStatus))
			Assert.fail();

	}

	@Test(enabled = true, dataProvider = "feeder")
	@Source("\\TestData\\CSVFiles\\Insights\\MostFrequentPurchases\\GetUserSubscriptionMostFrequentPurchases.csv")
	public void testGetUserMostFrequentPurchases(String testCaseId, String testCaseDescription, String triggerType,
			String InsightKey, String insightName, String title, String containerSupported, String validateKeys,
			String totalNumberOfKeys, String description, String applicableEntity, String levels, String isSubscribed,
			String frequency, String duration, String thresholdDetails, String checkRuleExpression,
			String configMetaAndentityParameter, int expectedStatus, String tcEnabled)
			throws ParseException, InterruptedException, IOException {
		boolean testExecutionStatus = false;
		commontUtils.isTCEnabled(tcEnabled, testCaseId);
		logger.info("Testcase--> " + testCaseId + "->" + "  Validate GET " + InsightKey + "  User Subscription ,"
				+ testCaseDescription);
		automationTestResults.setInsights("USER_SUB_MostFrequentPurchases");
		automationTestResults.setBuildNo(buildNo);
		executedInsight = InsightKey;
		JsonArray keysArray = insightsGenerics.getInsightKeys("MostFrequentPurchases");
		int numberOfKeys = insightsGenerics.getTotalNumberOfKeys(keysArray, validateKeys);
		storyId = testCaseId.substring(0, 7);
		TestInfo testInfo = new TestInfo();
		testInfo.setTestCaseId(testCaseId);
		testInfo.setTestDescription(testCaseDescription);
		HttpMethodParameters httpUserParams = insightsHelper.getHttpParams("userSubscriptionRequest", null, null,
				jwtUserAuthToken, null, null, null, null);
		userQueryParam.put("name", InsightKey);
		httpUserParams.setQueryParams(userQueryParam);
		userSubscriptionResponse = insightUtils.getUserSubscription(httpUserParams, null);
		Assert.assertEquals(userSubscriptionResponse.getStatusCode(), 200);
		String testStatus = insightsGenerics.getUserSubscriptionResponseVerification(cobSubscriptionResponse,
				userSubscriptionResponse, testExecutionStatus, testSummary, testCaseId, testCaseDescription,
				triggerType, InsightKey, insightName, title, containerSupported, keysArray,
				String.valueOf(numberOfKeys), description, applicableEntity, levels, isSubscribed, frequency,
				thresholdDetails, checkRuleExpression, configMetaAndentityParameter, expectedStatus, failureReason,
				validateKeys, testInfo);
		notificationsSummary(Boolean.valueOf(testStatus), testInfo, testCaseId, insightName, testCaseDescription);
		if (!Boolean.valueOf(testStatus))
			Assert.fail();
	}

	@Test(enabled = true, dataProvider = "feeder")
	@Source("\\TestData\\CSVFiles\\Insights\\ProjectedLowBalance\\GetUserSubscriptionProjectedLowBalance.csv")
	public void testGetUserSubscriptionProjectedLowBalance(String testCaseId, String testCaseDescription,
			String triggerType, String InsightKey, String insightName, String title, String containerSupported,
			String validateKeys, String description, String applicableEntity, String levels, String isSubscribed,
			String frequency, String thresholdDetails, String checkRuleExpression, String configMetaAndentityParameter,
			int expectedStatus, String tcEnabled) throws ParseException, InterruptedException, IOException {
		boolean testExecutionStatus = false;
		commontUtils.isTCEnabled(tcEnabled, testCaseId);
		logger.info("Testcase--> " + testCaseId + "->" + "  Validate GET " + InsightKey + "  User Subscription ,"
				+ testCaseDescription);
		automationTestResults.setInsights("USER_SUB_ProjectedLowBalance");
		automationTestResults.setBuildNo(buildNo);
		executedInsight = InsightKey;
		JsonArray keysArray = insightsGenerics.getInsightKeys("ProjectedLowBalance");
		int numberOfKeys = insightsGenerics.getTotalNumberOfKeys(keysArray, validateKeys);
		storyId = testCaseId.substring(0, 7);
		TestInfo testInfo = new TestInfo();
		testInfo.setTestCaseId(testCaseId);
		testInfo.setTestDescription(testCaseDescription);
		HttpMethodParameters httpUserParams = insightsHelper.getHttpParams("userSubscriptionRequest", null, null,
				jwtUserAuthToken, null, null, null, null);
		userQueryParam.put("name", InsightKey);
		httpUserParams.setQueryParams(userQueryParam);
		userSubscriptionResponse = insightUtils.getUserSubscription(httpUserParams, null);
		Assert.assertEquals(userSubscriptionResponse.getStatusCode(), 200);
		String testStatus = insightsGenerics.getUserSubscriptionResponseVerification(cobSubscriptionResponse,
				userSubscriptionResponse, testExecutionStatus, testSummary, testCaseId, testCaseDescription,
				triggerType, InsightKey, insightName, title, containerSupported, keysArray,
				String.valueOf(numberOfKeys), description, applicableEntity, levels, isSubscribed, frequency,
				thresholdDetails, checkRuleExpression, configMetaAndentityParameter, expectedStatus, failureReason,
				validateKeys, testInfo);
		notificationsSummary(Boolean.valueOf(testStatus), testInfo, testCaseId, insightName, testCaseDescription);
		if (!Boolean.valueOf(testStatus))
			Assert.fail();
	}

	@Test(enabled = true, dataProvider = "feeder")
	@Source("\\TestData\\CSVFiles\\Insights\\CategorySpendingExceeded\\GetUserSubscriptionCategorySpendingExceeded.csv")
	public void testGetUserSubscriptionCategorySpendingExceeded(String testCaseId, String testCaseDescription,
			String triggerType, String InsightKey, String insightName, String title, String containerSupported,
			String validateKeys, String description, String applicableEntity, String levels, String isSubscribed,
			String frequency, String thresholdDetails, String checkRuleExpression, String configMetaAndentityParameter,
			int expectedStatus, String tcEnabled) throws ParseException, InterruptedException, IOException {
		boolean testExecutionStatus = false;
		commontUtils.isTCEnabled(tcEnabled, testCaseId);
		logger.info("Testcase--> " + testCaseId + "->" + "  Validate GET " + InsightKey + "  User Subscription ,"
				+ testCaseDescription);
		automationTestResults.setInsights("USER_SUB_CategorySpendingExceeded");
		automationTestResults.setBuildNo(buildNo);
		executedInsight = InsightKey;
		JsonArray keysArray = insightsGenerics.getInsightKeys("CategorySpendingExceeded");
		int numberOfKeys = insightsGenerics.getTotalNumberOfKeys(keysArray, validateKeys);
		storyId = testCaseId.substring(0, 7);
		TestInfo testInfo = new TestInfo();
		testInfo.setTestCaseId(testCaseId);
		testInfo.setTestDescription(testCaseDescription);
		HttpMethodParameters httpUserParams = insightsHelper.getHttpParams("userSubscriptionRequest", null, null,
				jwtUserAuthToken, null, null, null, null);
		userQueryParam.put("name", InsightKey);
		httpUserParams.setQueryParams(userQueryParam);
		userSubscriptionResponse = insightUtils.getUserSubscription(httpUserParams, null);
		Assert.assertEquals(userSubscriptionResponse.getStatusCode(), 200);
		String testStatus = insightsGenerics.getUserSubscriptionResponseVerification(cobSubscriptionResponse,
				userSubscriptionResponse, testExecutionStatus, testSummary, testCaseId, testCaseDescription,
				triggerType, InsightKey, insightName, title, containerSupported, keysArray,
				String.valueOf(numberOfKeys), description, applicableEntity, levels, isSubscribed, frequency,
				thresholdDetails, checkRuleExpression, configMetaAndentityParameter, expectedStatus, failureReason,
				validateKeys, testInfo);
		notificationsSummary(Boolean.valueOf(testStatus), testInfo, testCaseId, insightName, testCaseDescription);
		if (!Boolean.valueOf(testStatus))
			Assert.fail();

	}

	@Test(enabled = true, dataProvider = "feeder")
	@Source("\\TestData\\CSVFiles\\Insights\\ProjectedInsufficientBalance\\GetUserSubscriptionProjectedInsufficientBalance.csv")
	public void testGetUserSubscriptionProjectedInsufficientBalance(String testCaseId, String testCaseDescription,
			String triggerType, String InsightKey, String insightName, String title, String containerSupported,
			String validateKeys, String totalNumberOfKeys, String description, String applicableEntity, String levels,
			String isSubscribed, String frequency, String thresholdDetails, String checkRuleExpression,
			String configMetaAndentityParameter, int expectedStatus, String tcEnabled)
			throws ParseException, InterruptedException, IOException {
		boolean testExecutionStatus = false;
		commontUtils.isTCEnabled(tcEnabled, testCaseId);
		logger.info("Testcase--> " + testCaseId + "->" + "  Validate GET " + InsightKey + "  User Subscription ,"
				+ testCaseDescription);
		automationTestResults.setInsights("USER_SUB_ProjectedInsufficientBalance");
		automationTestResults.setBuildNo(buildNo);
		executedInsight = InsightKey;
		JsonArray keysArray = insightsGenerics.getInsightKeys("ProjectedInsufficientBalance");
		int numberOfKeys = insightsGenerics.getTotalNumberOfKeys(keysArray, validateKeys);
		storyId = testCaseId.substring(0, 7);
		TestInfo testInfo = new TestInfo();
		testInfo.setTestCaseId(testCaseId);
		testInfo.setTestDescription(testCaseDescription);
		HttpMethodParameters httpUserParams = insightsHelper.getHttpParams("userSubscriptionRequest", null, null,
				jwtUserAuthToken, null, null, null, null);
		userQueryParam.put("name", InsightKey);
		httpUserParams.setQueryParams(userQueryParam);
		userSubscriptionResponse = insightUtils.getUserSubscription(httpUserParams, null);
		Assert.assertEquals(userSubscriptionResponse.getStatusCode(), 200);
		String testStatus = insightsGenerics.getUserSubscriptionResponseVerification(cobSubscriptionResponse,
				userSubscriptionResponse, testExecutionStatus, testSummary, testCaseId, testCaseDescription,
				triggerType, InsightKey, insightName, title, containerSupported, keysArray,
				String.valueOf(numberOfKeys), description, applicableEntity, levels, isSubscribed, frequency,
				thresholdDetails, checkRuleExpression, configMetaAndentityParameter, expectedStatus, failureReason,
				validateKeys, testInfo);
		notificationsSummary(Boolean.valueOf(testStatus), testInfo, testCaseId, insightName, testCaseDescription);
		if (!Boolean.valueOf(testStatus))
			Assert.fail();
	}

	@Test(enabled = true, dataProvider = "feeder")
	@Source("\\TestData\\CSVFiles\\Insights\\LargePurchase\\GetUserSubscriptionLargePurchase.csv")
	public void testGetUserLargePurchase(String testCaseId, String testCaseDescription, String triggerType,
			String InsightKey, String insightName, String title, String containerSupported, String validateKeys,
			String totalNumberOfKeys, String description, String applicableEntity, String levels, String isSubscribed,
			String frequency, String thresholdDetails, String checkRuleExpression, String configMetaAndentityParameter,
			int expectedStatus, String tcEnabled) throws ParseException, InterruptedException, IOException {
		boolean testExecutionStatus = false;
		commontUtils.isTCEnabled(tcEnabled, testCaseId);
		logger.info("Testcase--> " + testCaseId + "->" + "  Validate GET " + InsightKey + "  User Subscription ,"
				+ testCaseDescription);
		automationTestResults.setInsights("USER_SUB_LargePurchase");
		automationTestResults.setBuildNo(buildNo);
		executedInsight = InsightKey;
		JsonArray keysArray = insightsGenerics.getInsightKeys("LargePurchase");
		int numberOfKeys = insightsGenerics.getTotalNumberOfKeys(keysArray, validateKeys);
		storyId = testCaseId.substring(0, 7);
		TestInfo testInfo = new TestInfo();
		testInfo.setTestCaseId(testCaseId);
		testInfo.setTestDescription(testCaseDescription);
		HttpMethodParameters httpUserParams = insightsHelper.getHttpParams("userSubscriptionRequest", null, null,
				jwtUserAuthToken, null, null, null, null);
		userQueryParam.put("name", InsightKey);
		httpUserParams.setQueryParams(userQueryParam);
		userSubscriptionResponse = insightUtils.getUserSubscription(httpUserParams, null);
		Assert.assertEquals(userSubscriptionResponse.getStatusCode(), 200);
		String testStatus = insightsGenerics.getUserSubscriptionResponseVerification(cobSubscriptionResponse,
				userSubscriptionResponse, testExecutionStatus, testSummary, testCaseId, testCaseDescription,
				triggerType, InsightKey, insightName, title, containerSupported, keysArray,
				String.valueOf(numberOfKeys), description, applicableEntity, levels, isSubscribed, frequency,
				thresholdDetails, checkRuleExpression, configMetaAndentityParameter, expectedStatus, failureReason,
				validateKeys, testInfo);
		notificationsSummary(Boolean.valueOf(testStatus), testInfo, testCaseId, insightName, testCaseDescription);
		if (!Boolean.valueOf(testStatus))
			Assert.fail();
	}

	@Test(enabled = true, dataProvider = "feeder")
	@Source("\\TestData\\CSVFiles\\Insights\\CardStatementAvailable\\GetUserSubscriptionCardStatementAvailable.csv")
	public void testGetUserCardStatementAvailable(String testCaseId, String testCaseDescription, String triggerType,
			String InsightKey, String insightName, String title, String containerSupported, String validateKeys,
			String totalNumberOfKeys, String description, String applicableEntity, String levels, String isSubscribed,
			String frequency, String thresholdDetails, String checkRuleExpression, String configMetaAndentityParameter,
			int expectedStatus, String tcEnabled) throws ParseException, InterruptedException, IOException {
		boolean testExecutionStatus = false;
		commontUtils.isTCEnabled(tcEnabled, testCaseId);
		logger.info("Testcase--> " + testCaseId + "->" + "  Validate GET " + InsightKey + "  User Subscription ,"
				+ testCaseDescription);
		automationTestResults.setInsights("USER_SUB_CardStatementAvailable");
		automationTestResults.setBuildNo(buildNo);
		executedInsight = InsightKey;
		JsonArray keysArray = insightsGenerics.getInsightKeys("CardStatementAvailable");
		int numberOfKeys = insightsGenerics.getTotalNumberOfKeys(keysArray, validateKeys);
		storyId = testCaseId.substring(0, 7);
		TestInfo testInfo = new TestInfo();
		testInfo.setTestCaseId(testCaseId);
		testInfo.setTestDescription(testCaseDescription);
		HttpMethodParameters httpUserParams = insightsHelper.getHttpParams("userSubscriptionRequest", null, null,
				jwtUserAuthToken, null, null, null, null);
		userQueryParam.put("name", InsightKey);
		httpUserParams.setQueryParams(userQueryParam);
		userSubscriptionResponse = insightUtils.getUserSubscription(httpUserParams, null);
		Assert.assertEquals(userSubscriptionResponse.getStatusCode(), 200);
		String testStatus = insightsGenerics.getUserSubscriptionResponseVerification(cobSubscriptionResponse,
				userSubscriptionResponse, testExecutionStatus, testSummary, testCaseId, testCaseDescription,
				triggerType, InsightKey, insightName, title, containerSupported, keysArray,
				String.valueOf(numberOfKeys), description, applicableEntity, levels, isSubscribed, frequency,
				thresholdDetails, checkRuleExpression, configMetaAndentityParameter, expectedStatus, failureReason,
				validateKeys, testInfo);
		notificationsSummary(Boolean.valueOf(testStatus), testInfo, testCaseId, insightName, testCaseDescription);
		if (!Boolean.valueOf(testStatus))
			Assert.fail();

	}

	@Test(enabled = true, dataProvider = "feeder")
	@Source("\\TestData\\CSVFiles\\Insights\\PropertyValueChange\\GetUserSubscriptionPropertyValueChange.csv")
	public void testGetUserSubscriptionPropertyValueChange(String testCaseId, String testCaseDescription,
			String triggerType, String InsightKey, String insightName, String title, String containerSupported,
			String validateKeys, String totalNumberOfKeys, String description, String applicableEntity, String levels,
			String isSubscribed, String frequency, String thresholdDetails, String checkRuleExpression,
			String configMetaAndentityParameter, int expectedStatus, String tcEnabled)
			throws ParseException, InterruptedException, IOException {
		boolean testExecutionStatus = false;
		commontUtils.isTCEnabled(tcEnabled, testCaseId);
		logger.info("Testcase--> " + testCaseId + "->" + "  Validate GET " + InsightKey + "  User Subscription ,"
				+ testCaseDescription);
		automationTestResults.setInsights("USER_SUB_PropertyValueChange");
		automationTestResults.setBuildNo(buildNo);
		executedInsight = InsightKey;
		JsonArray keysArray = insightsGenerics.getInsightKeys("PropertyValueChange");
		int numberOfKeys = insightsGenerics.getTotalNumberOfKeys(keysArray, validateKeys);
		storyId = testCaseId.substring(0, 7);
		TestInfo testInfo = new TestInfo();
		testInfo.setTestCaseId(testCaseId);
		testInfo.setTestDescription(testCaseDescription);
		HttpMethodParameters httpUserParams = insightsHelper.getHttpParams("userSubscriptionRequest", null, null,
				jwtUserAuthToken, null, null, null, null);
		userQueryParam.put("name", InsightKey);
		httpUserParams.setQueryParams(userQueryParam);
		userSubscriptionResponse = insightUtils.getUserSubscription(httpUserParams, null);
		Assert.assertEquals(userSubscriptionResponse.getStatusCode(), 200);
		String testStatus = insightsGenerics.getUserSubscriptionResponseVerification(cobSubscriptionResponse,
				userSubscriptionResponse, testExecutionStatus, testSummary, testCaseId, testCaseDescription,
				triggerType, InsightKey, insightName, title, containerSupported, keysArray,
				String.valueOf(numberOfKeys), description, applicableEntity, levels, isSubscribed, frequency,
				thresholdDetails, checkRuleExpression, configMetaAndentityParameter, expectedStatus, failureReason,
				validateKeys, testInfo);
		notificationsSummary(Boolean.valueOf(testStatus), testInfo, testCaseId, insightName, testCaseDescription);
		if (!Boolean.valueOf(testStatus))
			Assert.fail();
	}

	@Test(enabled = true, dataProvider = "feeder")
	@Source("\\TestData\\CSVFiles\\Insights\\BillPaid\\GetUserSubscriptionBillPaid.csv")
	public void testGetUserSubscriptionBillPaid(String testCaseId, String testCaseDescription, String triggerType,
			String InsightKey, String insightName, String title, String containerSupported, String validateKeys,
			String totalNumberOfKeys, String description, String applicableEntity, String levels, String isSubscribed,
			String frequency, String insightType, String thresholdDetails, String checkRuleExpression,
			String configMetaAndentityParameter, int expectedStatus, String tcEnabled)
			throws ParseException, InterruptedException, IOException {
		boolean testExecutionStatus = false;
		commontUtils.isTCEnabled(tcEnabled, testCaseId);
		logger.info("Testcase--> " + testCaseId + "->" + "  Validate GET " + InsightKey + "  User Subscription ,"
				+ testCaseDescription);
		automationTestResults.setInsights("USER_SUB_BillPaid");

		automationTestResults.setBuildNo(buildNo);
		executedInsight = InsightKey;
		JsonArray keysArray = insightsGenerics.getInsightKeys("GETPatchBillPaid");
		int numberOfKeys = insightsGenerics.getTotalNumberOfKeys(keysArray, validateKeys);
		storyId = testCaseId.substring(0, 7);
		TestInfo testInfo = new TestInfo();
		testInfo.setTestCaseId(testCaseId);
		testInfo.setTestDescription(testCaseDescription);
		HttpMethodParameters httpUserParams = insightsHelper.getHttpParams("userSubscriptionRequest", null, null,
				jwtUserAuthToken, null, null, null, null);
		userQueryParam.put("name", InsightKey);
		httpUserParams.setQueryParams(userQueryParam);
		userSubscriptionResponse = insightUtils.getUserSubscription(httpUserParams, null);
		Assert.assertEquals(userSubscriptionResponse.getStatusCode(), 200);
		String testStatus = insightsGenerics.getUserSubscriptionResponseVerification(cobSubscriptionResponse,
				userSubscriptionResponse, testExecutionStatus, testSummary, testCaseId, testCaseDescription,
				triggerType, InsightKey, insightName, title, containerSupported, keysArray,
				String.valueOf(numberOfKeys), description, applicableEntity, levels, isSubscribed, frequency,
				thresholdDetails, checkRuleExpression, configMetaAndentityParameter, expectedStatus, failureReason,
				validateKeys, testInfo);
		notificationsSummary(Boolean.valueOf(testStatus), testInfo, testCaseId, insightName, testCaseDescription);
		if (!Boolean.valueOf(testStatus))
			Assert.fail();

	}

	@Test(enabled = true, dataProvider = "feeder")
	@Source("\\TestData\\CSVFiles\\Insights\\SubscriptionPaid\\GetUserSubscriptionSubscriptionPaid.csv")
	public void testGetUserSubscriptionSubscriptionPaid(String testCaseId, String testCaseDescription,
			String triggerType, String InsightKey, String insightName, String title, String containerSupported,
			String validateKeys, String totalNumberOfKeys, String description, String applicableEntity, String levels,
			String isSubscribed, String frequency, String insightType, String thresholdDetails,
			String checkRuleExpression, String configMetaAndentityParameter, int expectedStatus, String tcEnabled)
			throws ParseException, InterruptedException, IOException {
		boolean testExecutionStatus = false;
		commontUtils.isTCEnabled(tcEnabled, testCaseId);
		logger.info("Testcase--> " + testCaseId + "->" + "  Validate GET " + InsightKey + "  User Subscription ,"
				+ testCaseDescription);
		automationTestResults.setInsights("USER_SUB_SubscriptionPaid");
		automationTestResults.setBuildNo(buildNo);
		executedInsight = InsightKey;
		JsonArray keysArray = insightsGenerics.getInsightKeys("GETPatchSubscriptionPaid");
		int numberOfKeys = insightsGenerics.getTotalNumberOfKeys(keysArray, validateKeys);
		storyId = testCaseId.substring(0, 7);
		TestInfo testInfo = new TestInfo();
		testInfo.setTestCaseId(testCaseId);
		testInfo.setTestDescription(testCaseDescription);
		HttpMethodParameters httpUserParams = insightsHelper.getHttpParams("userSubscriptionRequest", null, null,
				jwtUserAuthToken, null, null, null, null);
		userQueryParam.put("name", InsightKey);
		httpUserParams.setQueryParams(userQueryParam);
		userSubscriptionResponse = insightUtils.getUserSubscription(httpUserParams, null);
		Assert.assertEquals(userSubscriptionResponse.getStatusCode(), 200);
		String testStatus = insightsGenerics.getUserSubscriptionResponseVerification(cobSubscriptionResponse,
				userSubscriptionResponse, testExecutionStatus, testSummary, testCaseId, testCaseDescription,
				triggerType, InsightKey, insightName, title, containerSupported, keysArray,
				String.valueOf(numberOfKeys), description, applicableEntity, levels, isSubscribed, frequency,
				thresholdDetails, checkRuleExpression, configMetaAndentityParameter, expectedStatus, failureReason,
				validateKeys, testInfo);
		notificationsSummary(Boolean.valueOf(testStatus), testInfo, testCaseId, insightName, testCaseDescription);
		if (!Boolean.valueOf(testStatus))
			Assert.fail();

	}

	@Test(enabled = true, dataProvider = "feeder")
	@Source("\\TestData\\CSVFiles\\Insights\\BillChanged\\GetUserSubscriptionBillChanged.csv")
	public void testGetUserSubscriptionBillChanged(String testCaseId, String testCaseDescription, String triggerType,
			String InsightKey, String insightName, String title, String containerSupported, String validateKeys,
			String totalNumberOfKeys, String description, String applicableEntity, String levels, String isSubscribed,
			String frequency, String thresholdDetails, String checkRuleExpression, String configMetaAndentityParameter,
			int expectedStatus, String tcEnabled) throws ParseException, InterruptedException, IOException {
		boolean testExecutionStatus = false;
		commontUtils.isTCEnabled(tcEnabled, testCaseId);
		logger.info("Testcase--> " + testCaseId + "->" + "  Validate GET " + InsightKey + "  User Subscription ,"
				+ testCaseDescription);
		automationTestResults.setInsights("USER_SUB_BillChanged");
		automationTestResults.setBuildNo(buildNo);
		executedInsight = InsightKey;
		JsonArray keysArray = insightsGenerics.getInsightKeys("BillChanged");
		int numberOfKeys = insightsGenerics.getTotalNumberOfKeys(keysArray, validateKeys);
		storyId = testCaseId.substring(0, 7);
		TestInfo testInfo = new TestInfo();
		testInfo.setTestCaseId(testCaseId);
		testInfo.setTestDescription(testCaseDescription);
		HttpMethodParameters httpUserParams = insightsHelper.getHttpParams("userSubscriptionRequest", null, null,
				jwtUserAuthToken, null, null, null, null);
		userQueryParam.put("name", InsightKey);
		httpUserParams.setQueryParams(userQueryParam);
		userSubscriptionResponse = insightUtils.getUserSubscription(httpUserParams, null);
		Assert.assertEquals(userSubscriptionResponse.getStatusCode(), 200);
		String testStatus = insightsGenerics.getUserSubscriptionResponseVerification(cobSubscriptionResponse,
				userSubscriptionResponse, testExecutionStatus, testSummary, testCaseId, testCaseDescription,
				triggerType, InsightKey, insightName, title, containerSupported, keysArray,
				String.valueOf(numberOfKeys), description, applicableEntity, levels, isSubscribed, frequency,
				thresholdDetails, checkRuleExpression, configMetaAndentityParameter, expectedStatus, failureReason,
				validateKeys, testInfo);
		notificationsSummary(Boolean.valueOf(testStatus), testInfo, testCaseId, insightName, testCaseDescription);
		if (!Boolean.valueOf(testStatus))
			Assert.fail();
	}

	@Test(enabled = true, dataProvider = "feeder")
	@Source("\\TestData\\CSVFiles\\Insights\\SubscriptionChanged\\GetUserSubscriptionSubscriptionChanged.csv")
	public void testGetUserSubscriptionSubscriptionChanged(String testCaseId, String testCaseDescription,
			String triggerType, String InsightKey, String insightName, String title, String containerSupported,
			String validateKeys, String totalNumberOfKeys, String description, String applicableEntity, String levels,
			String isSubscribed, String frequency, String thresholdDetails, String checkRuleExpression,
			String configMetaAndentityParameter, int expectedStatus, String tcEnabled)
			throws ParseException, InterruptedException, IOException {
		boolean testExecutionStatus = false;
		commontUtils.isTCEnabled(tcEnabled, testCaseId);
		logger.info("Testcase--> " + testCaseId + "->" + "  Validate GET " + InsightKey + "  User Subscription ,"
				+ testCaseDescription);
		automationTestResults.setInsights("USER_SUB_SubscriptionChanged");
		automationTestResults.setBuildNo(buildNo);
		executedInsight = InsightKey;
		JsonArray keysArray = insightsGenerics.getInsightKeys("SubscriptionChanged");
		int numberOfKeys = insightsGenerics.getTotalNumberOfKeys(keysArray, validateKeys);
		storyId = testCaseId.substring(0, 7);
		TestInfo testInfo = new TestInfo();
		testInfo.setTestCaseId(testCaseId);
		testInfo.setTestDescription(testCaseDescription);
		HttpMethodParameters httpUserParams = insightsHelper.getHttpParams("userSubscriptionRequest", null, null,
				jwtUserAuthToken, null, null, null, null);
		userQueryParam.put("name", InsightKey);
		httpUserParams.setQueryParams(userQueryParam);
		userSubscriptionResponse = insightUtils.getUserSubscription(httpUserParams, null);
		Assert.assertEquals(userSubscriptionResponse.getStatusCode(), 200);
		String testStatus = insightsGenerics.getUserSubscriptionResponseVerification(cobSubscriptionResponse,
				userSubscriptionResponse, testExecutionStatus, testSummary, testCaseId, testCaseDescription,
				triggerType, InsightKey, insightName, title, containerSupported, keysArray,
				String.valueOf(numberOfKeys), description, applicableEntity, levels, isSubscribed, frequency,
				thresholdDetails, checkRuleExpression, configMetaAndentityParameter, expectedStatus, failureReason,
				validateKeys, testInfo);
		notificationsSummary(Boolean.valueOf(testStatus), testInfo, testCaseId, insightName, testCaseDescription);
		if (!Boolean.valueOf(testStatus))
			Assert.fail();
	}

	@Test(enabled = true, dataProvider = "feeder")
	@Source("\\TestData\\CSVFiles\\Insights\\BalanceAboveThreshold\\GetUserSubscriptionBalanceAboveThreshold.csv")
	public void testGetUserSubscriptionBalanceAboveThreshold(String testCaseId, String testCaseDescription,
			String triggerType, String InsightKey, String insightName, String title, String containerSupported,
			String validateKeys, String description, String applicableEntity, String levels, String isSubscribed,
			String frequency, String thresholdDetails, String checkRuleExpression, String configMetaAndentityParameter,
			int expectedStatus, String tcEnabled) throws ParseException, InterruptedException, IOException {
		boolean testExecutionStatus = false;
		commontUtils.isTCEnabled(tcEnabled, testCaseId);
		logger.info("Testcase--> " + testCaseId + "->" + "  Validate GET " + InsightKey + "  User Subscription ,"
				+ testCaseDescription);
		automationTestResults.setInsights("USER_SUB_BalanceAboveThreshold");
		automationTestResults.setBuildNo(buildNo);
		executedInsight = InsightKey;
		JsonArray keysArray = insightsGenerics.getInsightKeys("GETPATCHBalanceAboveThreshold");
		int numberOfKeys = insightsGenerics.getTotalNumberOfKeys(keysArray, validateKeys);
		storyId = testCaseId.substring(0, 7);
		TestInfo testInfo = new TestInfo();
		testInfo.setTestCaseId(testCaseId);
		testInfo.setTestDescription(testCaseDescription);
		HttpMethodParameters httpUserParams = insightsHelper.getHttpParams("userSubscriptionRequest", null, null,
				jwtUserAuthToken, null, null, null, null);
		userQueryParam.put("name", InsightKey);
		httpUserParams.setQueryParams(userQueryParam);
		userSubscriptionResponse = insightUtils.getUserSubscription(httpUserParams, null);
		Assert.assertEquals(userSubscriptionResponse.getStatusCode(), 200);
		String testStatus = insightsGenerics.getUserSubscriptionResponseVerification(cobSubscriptionResponse,
				userSubscriptionResponse, testExecutionStatus, testSummary, testCaseId, testCaseDescription,
				triggerType, InsightKey, insightName, title, containerSupported, keysArray,
				String.valueOf(numberOfKeys), description, applicableEntity, levels, isSubscribed, frequency,
				thresholdDetails, checkRuleExpression, configMetaAndentityParameter, expectedStatus, failureReason,
				validateKeys, testInfo);
		notificationsSummary(Boolean.valueOf(testStatus), testInfo, testCaseId, insightName, testCaseDescription);
		if (!Boolean.valueOf(testStatus))
			Assert.fail();

	}

	@Test(enabled = true, dataProvider = "feeder")
	@Source("\\TestData\\CSVFiles\\Insights\\SalaryChanged\\GetUserSubscriptionSalaryChanged.csv")
	public void testGetUserSubscriptionSalaryChanged(String testCaseId, String testCaseDescription, String triggerType,
			String InsightKey, String insightName, String title, String containerSupported, String validateKeys,
			String totalNumberOfKeys, String description, String applicableEntity, String levels, String isSubscribed,
			String frequency, String thresholdDetails, String checkRuleExpression, String configMetaAndentityParameter,
			int expectedStatus, String tcEnabled) throws ParseException, InterruptedException, IOException {
		boolean testExecutionStatus = false;
		commontUtils.isTCEnabled(tcEnabled, testCaseId);
		logger.info("Testcase--> " + testCaseId + "->" + "  Validate GET " + InsightKey + "  User Subscription ,"
				+ testCaseDescription);
		automationTestResults.setInsights("USER_SUB_SalaryChanged");
		automationTestResults.setBuildNo(buildNo);
		executedInsight = InsightKey;
		JsonArray keysArray = insightsGenerics.getInsightKeys("SalaryChanged");
		int numberOfKeys = insightsGenerics.getTotalNumberOfKeys(keysArray, validateKeys);
		storyId = testCaseId.substring(0, 7);
		TestInfo testInfo = new TestInfo();
		testInfo.setTestCaseId(testCaseId);
		testInfo.setTestDescription(testCaseDescription);
		HttpMethodParameters httpUserParams = insightsHelper.getHttpParams("userSubscriptionRequest", null, null,
				jwtUserAuthToken, null, null, null, null);
		userQueryParam.put("name", InsightKey);
		httpUserParams.setQueryParams(userQueryParam);
		userSubscriptionResponse = insightUtils.getUserSubscription(httpUserParams, null);
		Assert.assertEquals(userSubscriptionResponse.getStatusCode(), 200);
		String testStatus = insightsGenerics.getUserSubscriptionResponseVerification(cobSubscriptionResponse,
				userSubscriptionResponse, testExecutionStatus, testSummary, testCaseId, testCaseDescription,
				triggerType, InsightKey, insightName, title, containerSupported, keysArray,
				String.valueOf(numberOfKeys), description, applicableEntity, levels, isSubscribed, frequency,
				thresholdDetails, checkRuleExpression, configMetaAndentityParameter, expectedStatus, failureReason,
				validateKeys, testInfo);
		notificationsSummary(Boolean.valueOf(testStatus), testInfo, testCaseId, insightName, testCaseDescription);
		if (!Boolean.valueOf(testStatus))
			Assert.fail();
	}

	@Test(enabled = true, dataProvider = "feeder")
	@Source("\\TestData\\CSVFiles\\Insights\\RecurringDepositLate\\GetUserSubscriptionRecurringDepositLate.csv")
	public void testGetUserSubscriptionRecurringDepositLate(String testCaseId, String testCaseDescription,
			String triggerType, String InsightKey, String insightName, String title, String containerSupported,
			String validateKeys, String totalNumberOfKeys, String description, String applicableEntity, String levels,
			String isSubscribed, String frequency, String thresholdDetails, String checkRuleExpression,
			String configMetaAndentityParameter, int expectedStatus, String tcEnabled)
			throws ParseException, InterruptedException, IOException {
		boolean testExecutionStatus = false;
		commontUtils.isTCEnabled(tcEnabled, testCaseId);
		logger.info("Testcase--> " + testCaseId + "->" + "  Validate GET " + InsightKey + "  User Subscription ,"
				+ testCaseDescription);
		automationTestResults.setInsights("RecurringDepositLate");
		automationTestResults.setBuildNo(buildNo);
		executedInsight = InsightKey;
		JsonArray keysArray = insightsGenerics.getInsightKeys("RecurringDepositLate");
		int numberOfKeys = insightsGenerics.getTotalNumberOfKeys(keysArray, validateKeys);
		storyId = testCaseId.substring(0, 7);
		TestInfo testInfo = new TestInfo();
		testInfo.setTestCaseId(testCaseId);
		testInfo.setTestDescription(testCaseDescription);
		HttpMethodParameters httpUserParams = insightsHelper.getHttpParams("userSubscriptionRequest", null, null,
				jwtUserAuthToken, null, null, null, null);
		userQueryParam.put("name", InsightKey);
		httpUserParams.setQueryParams(userQueryParam);
		userSubscriptionResponse = insightUtils.getUserSubscription(httpUserParams, null);
		Assert.assertEquals(userSubscriptionResponse.getStatusCode(), 200);
		String testStatus = insightsGenerics.getUserSubscriptionResponseVerification(cobSubscriptionResponse,
				userSubscriptionResponse, testExecutionStatus, testSummary, testCaseId, testCaseDescription,
				triggerType, InsightKey, insightName, title, containerSupported, keysArray,
				String.valueOf(numberOfKeys), description, applicableEntity, levels, isSubscribed, frequency,
				thresholdDetails, checkRuleExpression, configMetaAndentityParameter, expectedStatus, failureReason,
				validateKeys, testInfo);
		notificationsSummary(Boolean.valueOf(testStatus), testInfo, testCaseId, insightName, testCaseDescription);
		if (!Boolean.valueOf(testStatus))
			Assert.fail();
	}

	@Test(enabled = true, dataProvider = "feeder")
	@Source("\\TestData\\CSVFiles\\Insights\\LoanMaturityNotice\\GetUserSubscriptionLoanMaturityNotice.csv")
	public void testGetUserSubscriptionLoanMaturityNotice(String testCaseId, String testCaseDescription,
			String triggerType, String InsightKey, String insightName, String title, String containerSupported,
			String validateKeys, String totalNumberOfKeys, String description, String applicableEntity, String levels,
			String isSubscribed, String frequency, String thresholdDetails, String checkRuleExpression,
			String configMetaAndentityParameter, int expectedStatus, String tcEnabled)
			throws ParseException, InterruptedException, IOException {
		boolean testExecutionStatus = false;
		commontUtils.isTCEnabled(tcEnabled, testCaseId);
		logger.info("Testcase--> " + testCaseId + "->" + "  Validate GET " + InsightKey + "  User Subscription ,"
				+ testCaseDescription);
		automationTestResults.setInsights("LoanMaturityNotice");
		automationTestResults.setBuildNo(buildNo);
		executedInsight = InsightKey;
		JsonArray keysArray = insightsGenerics.getInsightKeys("LoanMaturityNotice");
		int numberOfKeys = insightsGenerics.getTotalNumberOfKeys(keysArray, validateKeys);
		storyId = testCaseId.substring(0, 7);
		TestInfo testInfo = new TestInfo();
		testInfo.setTestCaseId(testCaseId);
		testInfo.setTestDescription(testCaseDescription);
		HttpMethodParameters httpUserParams = insightsHelper.getHttpParams("userSubscriptionRequest", null, null,
				jwtUserAuthToken, null, null, null, null);
		userQueryParam.put("name", InsightKey);
		httpUserParams.setQueryParams(userQueryParam);
		userSubscriptionResponse = insightUtils.getUserSubscription(httpUserParams, null);
		Assert.assertEquals(userSubscriptionResponse.getStatusCode(), 200);
		String testStatus = insightsGenerics.getUserSubscriptionResponseVerification(cobSubscriptionResponse,
				userSubscriptionResponse, testExecutionStatus, testSummary, testCaseId, testCaseDescription,
				triggerType, InsightKey, insightName, title, containerSupported, keysArray,
				String.valueOf(numberOfKeys), description, applicableEntity, levels, isSubscribed, frequency,
				thresholdDetails, checkRuleExpression, configMetaAndentityParameter, expectedStatus, failureReason,
				validateKeys, testInfo);
		notificationsSummary(Boolean.valueOf(testStatus), testInfo, testCaseId, insightName, testCaseDescription);
		if (!Boolean.valueOf(testStatus))
			Assert.fail();
	}

	@Test(enabled = true, dataProvider = "feeder")
	@Source("\\TestData\\CSVFiles\\Insights\\InvestmentMaturityNotice\\GetUserSubscriptionInvestmentMaturityNotice.csv")
	public void testGetUserSubscriptionInvestmentMaturityNotice(String testCaseId, String testCaseDescription,
			String triggerType, String InsightKey, String insightName, String title, String containerSupported,
			String validateKeys, String totalNumberOfKeys, String description, String applicableEntity, String levels,
			String isSubscribed, String frequency, String thresholdDetails, String checkRuleExpression,
			String configMetaAndentityParameter, int expectedStatus, String tcEnabled)
			throws ParseException, InterruptedException, IOException {
		boolean testExecutionStatus = false;
		commontUtils.isTCEnabled(tcEnabled, testCaseId);
		logger.info("Testcase--> " + testCaseId + "->" + "  Validate GET " + InsightKey + "  User Subscription ,"
				+ testCaseDescription);
		automationTestResults.setInsights("InvestmentMaturityNotice");
		automationTestResults.setBuildNo(buildNo);
		executedInsight = InsightKey;
		JsonArray keysArray = insightsGenerics.getInsightKeys("InvestmentMaturityNotice");
		int numberOfKeys = insightsGenerics.getTotalNumberOfKeys(keysArray, validateKeys);
		storyId = testCaseId.substring(0, 7);
		TestInfo testInfo = new TestInfo();
		testInfo.setTestCaseId(testCaseId);
		testInfo.setTestDescription(testCaseDescription);
		HttpMethodParameters httpUserParams = insightsHelper.getHttpParams("userSubscriptionRequest", null, null,
				jwtUserAuthToken, null, null, null, null);
		userQueryParam.put("name", InsightKey);
		httpUserParams.setQueryParams(userQueryParam);
		userSubscriptionResponse = insightUtils.getUserSubscription(httpUserParams, null);
		Assert.assertEquals(userSubscriptionResponse.getStatusCode(), 200);
		String testStatus = insightsGenerics.getUserSubscriptionResponseVerification(cobSubscriptionResponse,
				userSubscriptionResponse, testExecutionStatus, testSummary, testCaseId, testCaseDescription,
				triggerType, InsightKey, insightName, title, containerSupported, keysArray,
				String.valueOf(numberOfKeys), description, applicableEntity, levels, isSubscribed, frequency,
				thresholdDetails, checkRuleExpression, configMetaAndentityParameter, expectedStatus, failureReason,
				validateKeys, testInfo);
		notificationsSummary(Boolean.valueOf(testStatus), testInfo, testCaseId, insightName, testCaseDescription);
		if (!Boolean.valueOf(testStatus))
			Assert.fail();
	}

	@Test(enabled = true, dataProvider = "feeder")
	@Source("\\TestData\\CSVFiles\\Insights\\IncomeThreshold\\GetUserSubscriptionIncomeThreshold.csv")
	public void testGetUserIncomeThreshold(String testCaseId, String testCaseDescription, String triggerType,
			String InsightKey, String insightName, String title, String containerSupported, String validateKeys,
			String totalNumberOfKeys, String description, String applicableEntity, String levels, String isSubscribed,
			String frequency, String duration, String thresholdDetails, String checkRuleExpression,
			String configMetaAndentityParameter, int expectedStatus, String tcEnabled)
			throws ParseException, InterruptedException, IOException {
		boolean testExecutionStatus = false;
		commontUtils.isTCEnabled(tcEnabled, testCaseId);
		logger.info("Testcase--> " + testCaseId + "->" + "  Validate GET " + InsightKey + "  User Subscription ,"
				+ testCaseDescription);
		automationTestResults.setInsights("IncomeThreshold");
		automationTestResults.setBuildNo(buildNo);
		executedInsight = InsightKey;
		JsonArray keysArray = insightsGenerics.getInsightKeys("IncomeThreshold");
		int numberOfKeys = insightsGenerics.getTotalNumberOfKeys(keysArray, validateKeys);
		storyId = testCaseId.substring(0, 7);
		TestInfo testInfo = new TestInfo();
		testInfo.setTestCaseId(testCaseId);
		testInfo.setTestDescription(testCaseDescription);
		HttpMethodParameters httpUserParams = insightsHelper.getHttpParams("userSubscriptionRequest", null, null,
				jwtUserAuthToken, null, null, null, null);
		userQueryParam.put("name", InsightKey);
		httpUserParams.setQueryParams(userQueryParam);
		userSubscriptionResponse = insightUtils.getUserSubscription(httpUserParams, null);
		Assert.assertEquals(userSubscriptionResponse.getStatusCode(), 200);
		String testStatus = insightsGenerics.getUserSubscriptionResponseVerification(cobSubscriptionResponse,
				userSubscriptionResponse, testExecutionStatus, testSummary, testCaseId, testCaseDescription,
				triggerType, InsightKey, insightName, title, containerSupported, keysArray,
				String.valueOf(numberOfKeys), description, applicableEntity, levels, isSubscribed, frequency,
				thresholdDetails, checkRuleExpression, configMetaAndentityParameter, expectedStatus, failureReason,
				validateKeys, testInfo);
		notificationsSummary(Boolean.valueOf(testStatus), testInfo, testCaseId, insightName, testCaseDescription);
		if (!Boolean.valueOf(testStatus))
			Assert.fail();
	}

	@Test(enabled = true, dataProvider = "feeder")
	@Source("\\TestData\\CSVFiles\\Insights\\ExpensesExceededBudget\\GetUserSubscExpExceededBudget.csv")
	public void testGetUserSubscriptionExpenseExceededBudget(String testCaseId, String testCaseDescription,
			String triggerType, String InsightKey, String insightName, String title, String containerSupported,
			String validateKeys, String description, String applicableEntity, String levels, String isSubscribed,
			String frequency, String thresholdDetails, String checkRuleExpression, String configMetaAndentityParameter,
			int expectedStatus, String tcEnabled) throws ParseException, InterruptedException, IOException {

		boolean testExecutionStatus = false;
		commontUtils.isTCEnabled(tcEnabled, testCaseId);
		logger.info("Testcase--> " + testCaseId + "->" + "  Validate GET " + InsightKey + "  User Subscription ,"
				+ testCaseDescription);
		automationTestResults.setInsights("ExpensesExceededBudget");
		automationTestResults.setBuildNo(buildNo);
		executedInsight = InsightKey;
		JsonArray keysArray = insightsGenerics.getInsightKeys("ExpensesExceededBudget");
		int numberOfKeys = insightsGenerics.getTotalNumberOfKeys(keysArray, validateKeys);
		storyId = testCaseId.substring(0, 7);
		TestInfo testInfo = new TestInfo();
		testInfo.setTestCaseId(testCaseId);
		testInfo.setTestDescription(testCaseDescription);
		HttpMethodParameters httpUserParams = insightsHelper.getHttpParams("userSubscriptionRequest", null, null,
				jwtUserAuthToken, null, null, null, null);
		userQueryParam.put("name", InsightKey);
		httpUserParams.setQueryParams(userQueryParam);
		userSubscriptionResponse = insightUtils.getUserSubscription(httpUserParams, null);
		Assert.assertEquals(userSubscriptionResponse.getStatusCode(), 200);
		String testStatus = insightsGenerics.getUserSubscriptionResponseVerification(cobSubscriptionResponse,
				userSubscriptionResponse, testExecutionStatus, testSummary, testCaseId, testCaseDescription,
				triggerType, InsightKey, insightName, title, containerSupported, keysArray,
				String.valueOf(numberOfKeys), description, applicableEntity, levels, isSubscribed, frequency,
				thresholdDetails, checkRuleExpression, configMetaAndentityParameter, expectedStatus, failureReason,
				validateKeys, testInfo);
		notificationsSummary(Boolean.valueOf(testStatus), testInfo, testCaseId, insightName, testCaseDescription);
		if (!Boolean.valueOf(testStatus))
			Assert.fail();
	}
	
	@Test(enabled = true, dataProvider = "feeder")
	@Source("\\TestData\\CSVFiles\\Insights\\SpendingEarningComparison\\GetUserSubscSpendingEarning.csv")
	public void testGetUserSubscriptionSpendingEarningComparison(String testCaseId, String testCaseDescription,
			String triggerType, String InsightKey, String insightName, String title, String containerSupported,
			String validateKeys, String description, String applicableEntity, String levels, String isSubscribed,
			String frequency, String thresholdDetails, String checkRuleExpression, String configMetaAndentityParameter,
			int expectedStatus, String tcEnabled) throws ParseException, InterruptedException, IOException {

		boolean testExecutionStatus = false;
		commontUtils.isTCEnabled(tcEnabled, testCaseId);
		logger.info("Testcase--> " + testCaseId + "->" + "  Validate GET " + InsightKey + "  User Subscription ,"
				+ testCaseDescription);
		automationTestResults.setInsights("SpendingEarningComparison");
		automationTestResults.setBuildNo(buildNo);
		executedInsight = InsightKey;
		JsonArray keysArray = insightsGenerics.getInsightKeys("SpendingEarningComparison");
		int numberOfKeys = insightsGenerics.getTotalNumberOfKeys(keysArray, validateKeys);
		storyId = testCaseId.substring(0, 7);
		TestInfo testInfo = new TestInfo();
		testInfo.setTestCaseId(testCaseId);
		testInfo.setTestDescription(testCaseDescription);
		HttpMethodParameters httpUserParams = insightsHelper.getHttpParams("userSubscriptionRequest", null, null,
				jwtUserAuthToken, null, null, null, null);
		userQueryParam.put("name", InsightKey);
		httpUserParams.setQueryParams(userQueryParam);
		userSubscriptionResponse = insightUtils.getUserSubscription(httpUserParams, null);
		Assert.assertEquals(userSubscriptionResponse.getStatusCode(), 200);
		String testStatus = insightsGenerics.getUserSubscriptionResponseVerification(cobSubscriptionResponse,
				userSubscriptionResponse, testExecutionStatus, testSummary, testCaseId, testCaseDescription,
				triggerType, InsightKey, insightName, title, containerSupported, keysArray,
				String.valueOf(numberOfKeys), description, applicableEntity, levels, isSubscribed, frequency,
				thresholdDetails, checkRuleExpression, configMetaAndentityParameter, expectedStatus, failureReason,
				validateKeys, testInfo);
		notificationsSummary(Boolean.valueOf(testStatus), testInfo, testCaseId, insightName, testCaseDescription);
		if (!Boolean.valueOf(testStatus))
			Assert.fail();
	}
	
	@Test(enabled = true, dataProvider = "feeder")
	@Source("\\TestData\\CSVFiles\\Insights\\EmergencySavings\\GetUserSubscEmergencySavings.csv")
	public void testGetUserSubscriptionEmergencySavings(String testCaseId, String testCaseDescription,
			String triggerType, String InsightKey, String insightName, String title, String containerSupported,
			String validateKeys, String description, String applicableEntity, String levels, String isSubscribed,
			String frequency, String thresholdDetails, String checkRuleExpression, String configMetaAndentityParameter,
			int expectedStatus, String tcEnabled) throws ParseException, InterruptedException, IOException {

		boolean testExecutionStatus = false;
		commontUtils.isTCEnabled(tcEnabled, testCaseId);
		logger.info("Testcase--> " + testCaseId + "->" + "  Validate GET " + InsightKey + "  User Subscription ,"
				+ testCaseDescription);
		automationTestResults.setInsights("EmergencySavings");
		automationTestResults.setBuildNo(buildNo);
		executedInsight = InsightKey;
		JsonArray keysArray = insightsGenerics.getInsightKeys("EmergencySavings");
		int numberOfKeys = insightsGenerics.getTotalNumberOfKeys(keysArray, validateKeys);
		storyId = testCaseId.substring(0, 7);
		TestInfo testInfo = new TestInfo();
		testInfo.setTestCaseId(testCaseId);
		testInfo.setTestDescription(testCaseDescription);
		HttpMethodParameters httpUserParams = insightsHelper.getHttpParams("userSubscriptionRequest", null, null,
				jwtUserAuthToken, null, null, null, null);
		userQueryParam.put("name", InsightKey);
		httpUserParams.setQueryParams(userQueryParam);
		userSubscriptionResponse = insightUtils.getUserSubscription(httpUserParams, null);
		Assert.assertEquals(userSubscriptionResponse.getStatusCode(), 200);
		String testStatus = insightsGenerics.getUserSubscriptionResponseVerification(cobSubscriptionResponse,
				userSubscriptionResponse, testExecutionStatus, testSummary, testCaseId, testCaseDescription,
				triggerType, InsightKey, insightName, title, containerSupported, keysArray,
				String.valueOf(numberOfKeys), description, applicableEntity, levels, isSubscribed, frequency,
				thresholdDetails, checkRuleExpression, configMetaAndentityParameter, expectedStatus, failureReason,
				validateKeys, testInfo);
		notificationsSummary(Boolean.valueOf(testStatus), testInfo, testCaseId, insightName, testCaseDescription);
		if (!Boolean.valueOf(testStatus))
			Assert.fail();
	}
	
	@Test(enabled = true, dataProvider = "feeder")
	@Source("\\TestData\\CSVFiles\\Insights\\SpendingByCategoryConsumerLevel\\GetUserSubsSpendCatAllAcct.csv")
	public void testGetUserSubscriptionSpendingByCategoryAllAccounts(String testCaseId, String testCaseDescription,
			String triggerType, String InsightKey, String insightName, String title, String containerSupported,
			String validateKeys, String description, String applicableEntity, String levels, String isSubscribed,
			String frequency, String thresholdDetails, String checkRuleExpression, String configMetaAndentityParameter,
			int expectedStatus, String tcEnabled) throws ParseException, InterruptedException, IOException {

		boolean testExecutionStatus = false;
		commontUtils.isTCEnabled(tcEnabled, testCaseId);
		logger.info("Testcase--> " + testCaseId + "->" + "  Validate GET " + InsightKey + "  User Subscription ,"
				+ testCaseDescription);
		automationTestResults.setInsights("SpendingbyCategoryAllAccounts");
		automationTestResults.setBuildNo(buildNo);
		executedInsight = InsightKey;
		JsonArray keysArray = insightsGenerics.getInsightKeys("SpendingbyCategoryAllAccounts");
		int numberOfKeys = insightsGenerics.getTotalNumberOfKeys(keysArray, validateKeys);
		storyId = testCaseId.substring(0, 7);
		TestInfo testInfo = new TestInfo();
		testInfo.setTestCaseId(testCaseId);
		testInfo.setTestDescription(testCaseDescription);
		HttpMethodParameters httpUserParams = insightsHelper.getHttpParams("userSubscriptionRequest", null, null,
				jwtUserAuthToken, null, null, null, null);
		userQueryParam.put("name", InsightKey);
		httpUserParams.setQueryParams(userQueryParam);
		userSubscriptionResponse = insightUtils.getUserSubscription(httpUserParams, null);
		Assert.assertEquals(userSubscriptionResponse.getStatusCode(), 200);
		String testStatus = insightsGenerics.getUserSubscriptionResponseVerification(cobSubscriptionResponse,
				userSubscriptionResponse, testExecutionStatus, testSummary, testCaseId, testCaseDescription,
				triggerType, InsightKey, insightName, title, containerSupported, keysArray,
				String.valueOf(numberOfKeys), description, applicableEntity, levels, isSubscribed, frequency,
				thresholdDetails, checkRuleExpression, configMetaAndentityParameter, expectedStatus, failureReason,
				validateKeys, testInfo);
		notificationsSummary(Boolean.valueOf(testStatus), testInfo, testCaseId, insightName, testCaseDescription);
		if (!Boolean.valueOf(testStatus))
			Assert.fail();
	}
	
	@Test(enabled = true, dataProvider = "feeder")
	@Source("\\TestData\\CSVFiles\\Insights\\SpendingByMerchantsConsumerLevel\\GetUserSubsSpeMerAllAcct.csv")
	public void testGetUserSubscriptionSpendingByMerchantsAllAccounts(String testCaseId, String testCaseDescription,
			String triggerType, String InsightKey, String insightName, String title, String containerSupported,
			String validateKeys, String description, String applicableEntity, String levels, String isSubscribed,
			String frequency, String thresholdDetails, String checkRuleExpression, String configMetaAndentityParameter,
			int expectedStatus, String tcEnabled) throws ParseException, InterruptedException, IOException {

		boolean testExecutionStatus = false;
		commontUtils.isTCEnabled(tcEnabled, testCaseId);
		logger.info("Testcase--> " + testCaseId + "->" + "  Validate GET " + InsightKey + "  User Subscription ,"
				+ testCaseDescription);
		automationTestResults.setInsights("SpendingbyMerchantsAllAccounts");
		automationTestResults.setBuildNo(buildNo);
		executedInsight = InsightKey;
		JsonArray keysArray = insightsGenerics.getInsightKeys("SpendingbyMerchantsAllAccounts");
		int numberOfKeys = insightsGenerics.getTotalNumberOfKeys(keysArray, validateKeys);
		storyId = testCaseId.substring(0, 7);
		TestInfo testInfo = new TestInfo();
		testInfo.setTestCaseId(testCaseId);
		testInfo.setTestDescription(testCaseDescription);
		HttpMethodParameters httpUserParams = insightsHelper.getHttpParams("userSubscriptionRequest", null, null,
				jwtUserAuthToken, null, null, null, null);
		userQueryParam.put("name", InsightKey);
		httpUserParams.setQueryParams(userQueryParam);
		userSubscriptionResponse = insightUtils.getUserSubscription(httpUserParams, null);
		Assert.assertEquals(userSubscriptionResponse.getStatusCode(), 200);
		String testStatus = insightsGenerics.getUserSubscriptionResponseVerification(cobSubscriptionResponse,
				userSubscriptionResponse, testExecutionStatus, testSummary, testCaseId, testCaseDescription,
				triggerType, InsightKey, insightName, title, containerSupported, keysArray,
				String.valueOf(numberOfKeys), description, applicableEntity, levels, isSubscribed, frequency,
				thresholdDetails, checkRuleExpression, configMetaAndentityParameter, expectedStatus, failureReason,
				validateKeys, testInfo);
		notificationsSummary(Boolean.valueOf(testStatus), testInfo, testCaseId, insightName, testCaseDescription);
		if (!Boolean.valueOf(testStatus))
			Assert.fail();
	}

	@Test(enabled = true, dataProvider = "feeder")
	@Source("\\TestData\\CSVFiles\\Insights\\AggregateCreditCardAccount\\GetUserSubscriptionAggregateCreditCardAccount.csv")
	public void testGetUserSubscriptionAggregateCreditCardAccount(String testCaseId, String testCaseDescription,
			String triggerType, String InsightKey, String insightName, String title, String containerSupported,
			String validateKeys, String description, String applicableEntity, String levels, String isSubscribed,
			String frequency, String thresholdDetails, String checkRuleExpression, String configMetaAndentityParameter,
			int expectedStatus, String tcEnabled) throws ParseException, InterruptedException, IOException {

		boolean testExecutionStatus = false;
		commontUtils.isTCEnabled(tcEnabled, testCaseId);
		logger.info("Testcase--> " + testCaseId + "->" + "  Validate GET " + InsightKey + "  User Subscription ,"
				+ testCaseDescription);
		automationTestResults.setInsights("AggregateCreditCardAccount");
		automationTestResults.setBuildNo(buildNo);
		executedInsight = InsightKey;
		JsonArray keysArray = insightsGenerics.getInsightKeys("AggregateCreditCardAccount");
		int numberOfKeys = insightsGenerics.getTotalNumberOfKeys(keysArray, validateKeys);
		storyId = testCaseId.substring(0, 7);
		TestInfo testInfo = new TestInfo();
		testInfo.setTestCaseId(testCaseId);
		testInfo.setTestDescription(testCaseDescription);
		HttpMethodParameters httpUserParams = insightsHelper.getHttpParams("userSubscriptionRequest", null, null,
				jwtUserAuthToken, null, null, null, null);
		userQueryParam.put("name", InsightKey);
		httpUserParams.setQueryParams(userQueryParam);
		userSubscriptionResponse = insightUtils.getUserSubscription(httpUserParams, null);
		Assert.assertEquals(userSubscriptionResponse.getStatusCode(), 200);
		String testStatus = insightsGenerics.getUserSubscriptionResponseVerification(cobSubscriptionResponse,
				userSubscriptionResponse, testExecutionStatus, testSummary, testCaseId, testCaseDescription,
				triggerType, InsightKey, insightName, title, containerSupported, keysArray,
				String.valueOf(numberOfKeys), description, applicableEntity, levels, isSubscribed, frequency,
				thresholdDetails, checkRuleExpression, configMetaAndentityParameter, expectedStatus, failureReason,
				validateKeys, testInfo);
		notificationsSummary(Boolean.valueOf(testStatus), testInfo, testCaseId, insightName, testCaseDescription);
		if (!Boolean.valueOf(testStatus))
			Assert.fail();
	}
	
	@Test(enabled = true, dataProvider = "feeder")
	@Source("\\TestData\\CSVFiles\\Insights\\AggregateInsuranceAccount\\GetUserSubscriptionAggregateInsuranceAccount.csv")
	public void testGetUserSubscriptionAggregateInsuranceAccount(String testCaseId, String testCaseDescription,
			String triggerType, String InsightKey, String insightName, String title, String containerSupported,
			String validateKeys, String description, String applicableEntity, String levels, String isSubscribed,
			String frequency, String thresholdDetails, String checkRuleExpression, String configMetaAndentityParameter,
			int expectedStatus, String tcEnabled) throws ParseException, InterruptedException, IOException {

		boolean testExecutionStatus = false;
		commontUtils.isTCEnabled(tcEnabled, testCaseId);
		logger.info("Testcase--> " + testCaseId + "->" + "  Validate GET " + InsightKey + "  User Subscription ,"
				+ testCaseDescription);
		automationTestResults.setInsights("AggregateInsuranceAccount");
		automationTestResults.setBuildNo(buildNo);
		executedInsight = InsightKey;
		JsonArray keysArray = insightsGenerics.getInsightKeys("AggregateInsuranceAccount");
		int numberOfKeys = insightsGenerics.getTotalNumberOfKeys(keysArray, validateKeys);
		storyId = testCaseId.substring(0, 7);
		TestInfo testInfo = new TestInfo();
		testInfo.setTestCaseId(testCaseId);
		testInfo.setTestDescription(testCaseDescription);
		HttpMethodParameters httpUserParams = insightsHelper.getHttpParams("userSubscriptionRequest", null, null,
				jwtUserAuthToken, null, null, null, null);
		userQueryParam.put("name", InsightKey);
		httpUserParams.setQueryParams(userQueryParam);
		userSubscriptionResponse = insightUtils.getUserSubscription(httpUserParams, null);
		Assert.assertEquals(userSubscriptionResponse.getStatusCode(), 200);
		String testStatus = insightsGenerics.getUserSubscriptionResponseVerification(cobSubscriptionResponse,
				userSubscriptionResponse, testExecutionStatus, testSummary, testCaseId, testCaseDescription,
				triggerType, InsightKey, insightName, title, containerSupported, keysArray,
				String.valueOf(numberOfKeys), description, applicableEntity, levels, isSubscribed, frequency,
				thresholdDetails, checkRuleExpression, configMetaAndentityParameter, expectedStatus, failureReason,
				validateKeys, testInfo);
		notificationsSummary(Boolean.valueOf(testStatus), testInfo, testCaseId, insightName, testCaseDescription);
		if (!Boolean.valueOf(testStatus))
			Assert.fail();
	}
	
	@Test(enabled = true, dataProvider = "feeder")
	@Source("\\TestData\\CSVFiles\\Insights\\AggregateMortgageAccount\\GetUserSubscriptionAggregateMortgageAccount.csv")
	public void testGetUserSubscriptionAggregateMortgageAccount(String testCaseId, String testCaseDescription,
			String triggerType, String InsightKey, String insightName, String title, String containerSupported,
			String validateKeys, String description, String applicableEntity, String levels, String isSubscribed,
			String frequency, String thresholdDetails, String checkRuleExpression, String configMetaAndentityParameter,
			int expectedStatus, String tcEnabled) throws ParseException, InterruptedException, IOException {

		boolean testExecutionStatus = false;
		commontUtils.isTCEnabled(tcEnabled, testCaseId);
		logger.info("Testcase--> " + testCaseId + "->" + "  Validate GET " + InsightKey + "  User Subscription ,"
				+ testCaseDescription);
		automationTestResults.setInsights("AggregateMortgageAccount");
		automationTestResults.setBuildNo(buildNo);
		executedInsight = InsightKey;
		JsonArray keysArray = insightsGenerics.getInsightKeys("AggregateMortgageAccount");
		int numberOfKeys = insightsGenerics.getTotalNumberOfKeys(keysArray, validateKeys);
		storyId = testCaseId.substring(0, 7);
		TestInfo testInfo = new TestInfo();
		testInfo.setTestCaseId(testCaseId);
		testInfo.setTestDescription(testCaseDescription);
		HttpMethodParameters httpUserParams = insightsHelper.getHttpParams("userSubscriptionRequest", null, null,
				jwtUserAuthToken, null, null, null, null);
		userQueryParam.put("name", InsightKey);
		httpUserParams.setQueryParams(userQueryParam);
		userSubscriptionResponse = insightUtils.getUserSubscription(httpUserParams, null);
		Assert.assertEquals(userSubscriptionResponse.getStatusCode(), 200);
		String testStatus = insightsGenerics.getUserSubscriptionResponseVerification(cobSubscriptionResponse,
				userSubscriptionResponse, testExecutionStatus, testSummary, testCaseId, testCaseDescription,
				triggerType, InsightKey, insightName, title, containerSupported, keysArray,
				String.valueOf(numberOfKeys), description, applicableEntity, levels, isSubscribed, frequency,
				thresholdDetails, checkRuleExpression, configMetaAndentityParameter, expectedStatus, failureReason,
				validateKeys, testInfo);
		notificationsSummary(Boolean.valueOf(testStatus), testInfo, testCaseId, insightName, testCaseDescription);
		if (!Boolean.valueOf(testStatus))
			Assert.fail();
	}
	
	@Test(enabled = true, dataProvider = "feeder")
	@Source("\\TestData\\CSVFiles\\Insights\\AggregateRealEstateAccount\\GetUserSubscriptionAggregateRealEstateAccount.csv")
	public void testGetUserSubscriptionAggregateRealEstateAccount(String testCaseId, String testCaseDescription,
			String triggerType, String InsightKey, String insightName, String title, String containerSupported,
			String validateKeys, String description, String applicableEntity, String levels, String isSubscribed,
			String frequency, String thresholdDetails, String checkRuleExpression, String configMetaAndentityParameter,
			int expectedStatus, String tcEnabled) throws ParseException, InterruptedException, IOException {

		boolean testExecutionStatus = false;
		commontUtils.isTCEnabled(tcEnabled, testCaseId);
		logger.info("Testcase--> " + testCaseId + "->" + "  Validate GET " + InsightKey + "  User Subscription ,"
				+ testCaseDescription);
		automationTestResults.setInsights("AggregateRealEstateAccount");
		automationTestResults.setBuildNo(buildNo);
		executedInsight = InsightKey;
		JsonArray keysArray = insightsGenerics.getInsightKeys("AggregateRealEstateAccount");
		int numberOfKeys = insightsGenerics.getTotalNumberOfKeys(keysArray, validateKeys);
		storyId = testCaseId.substring(0, 7);
		TestInfo testInfo = new TestInfo();
		testInfo.setTestCaseId(testCaseId);
		testInfo.setTestDescription(testCaseDescription);
		HttpMethodParameters httpUserParams = insightsHelper.getHttpParams("userSubscriptionRequest", null, null,
				jwtUserAuthToken, null, null, null, null);
		userQueryParam.put("name", InsightKey);
		httpUserParams.setQueryParams(userQueryParam);
		userSubscriptionResponse = insightUtils.getUserSubscription(httpUserParams, null);
		Assert.assertEquals(userSubscriptionResponse.getStatusCode(), 200);
		String testStatus = insightsGenerics.getUserSubscriptionResponseVerification(cobSubscriptionResponse,
				userSubscriptionResponse, testExecutionStatus, testSummary, testCaseId, testCaseDescription,
				triggerType, InsightKey, insightName, title, containerSupported, keysArray,
				String.valueOf(numberOfKeys), description, applicableEntity, levels, isSubscribed, frequency,
				thresholdDetails, checkRuleExpression, configMetaAndentityParameter, expectedStatus, failureReason,
				validateKeys, testInfo);
		notificationsSummary(Boolean.valueOf(testStatus), testInfo, testCaseId, insightName, testCaseDescription);
		if (!Boolean.valueOf(testStatus))
			Assert.fail();
	}
	
	@Test(enabled = true, dataProvider = "feeder")
	@Source("\\TestData\\CSVFiles\\Insights\\ZeroCardInterestPaid\\GetUserSubscriptionZeroCardInterestPaid.csv")
	public void testGetUserSubscriptionZeroCardInterestPaid(String testCaseId, String testCaseDescription,
			String triggerType, String InsightKey, String insightName, String title, String containerSupported,
			String validateKeys, String totalNumberOfKeys, String description, String applicableEntity, String levels,
			String isSubscribed, String frequency, String thresholdDetails, String checkRuleExpression,
			String configMetaAndentityParameter, int expectedStatus, String tcEnabled)
			throws ParseException, InterruptedException, IOException {
		boolean testExecutionStatus = false;
		commontUtils.isTCEnabled(tcEnabled, testCaseId);
		logger.info("Testcase--> " + testCaseId + "->" + "  Validate GET " + InsightKey + "  User Subscription ,"
				+ testCaseDescription);
		automationTestResults.setInsights("ZeroCardInterestPaid");
		automationTestResults.setBuildNo(buildNo);
		executedInsight = InsightKey;
		JsonArray keysArray = insightsGenerics.getInsightKeys("ZeroCardInterestPaid");
		int numberOfKeys = insightsGenerics.getTotalNumberOfKeys(keysArray, validateKeys);
		storyId = testCaseId.substring(0, 7);
		TestInfo testInfo = new TestInfo();
		testInfo.setTestCaseId(testCaseId);
		testInfo.setTestDescription(testCaseDescription);
		HttpMethodParameters httpUserParams = insightsHelper.getHttpParams("userSubscriptionRequest", null, null,
				jwtUserAuthToken, null, null, null, null);
		userQueryParam.put("name", InsightKey);
		httpUserParams.setQueryParams(userQueryParam);
		userSubscriptionResponse = insightUtils.getUserSubscription(httpUserParams, null);
		Assert.assertEquals(userSubscriptionResponse.getStatusCode(), 200);
		String testStatus = insightsGenerics.getUserSubscriptionResponseVerification(cobSubscriptionResponse,
				userSubscriptionResponse, testExecutionStatus, testSummary, testCaseId, testCaseDescription,
				triggerType, InsightKey, insightName, title, containerSupported, keysArray,
				String.valueOf(numberOfKeys), description, applicableEntity, levels, isSubscribed, frequency,
				thresholdDetails, checkRuleExpression, configMetaAndentityParameter, expectedStatus, failureReason,
				validateKeys, testInfo);
		notificationsSummary(Boolean.valueOf(testStatus), testInfo, testCaseId, insightName, testCaseDescription);
		if (!Boolean.valueOf(testStatus))
			Assert.fail();
	}
	
	@Test(enabled = true, dataProvider = "feeder")
	@Source("\\TestData\\CSVFiles\\Insights\\NewSalary\\GetUserSubscriptionNewSalary.csv")
	public void testGetUserSubscriptionNewSalary(String testCaseId, String testCaseDescription, String triggerType, String InsightKey,
			String insightName, String title, String containerSupported, String validateKeys, String totalNumberOfKeys,
			String description, String applicableEntity, String levels, String isSubscribed, String frequency,
			String insightType, String thresholdDetails, String checkRuleExpression,
			String configMetaAndentityParameter, int expectedStatus, String tcEnabled)
			throws ParseException, InterruptedException, IOException {
		boolean testExecutionStatus = false;
		commontUtils.isTCEnabled(tcEnabled, testCaseId);
		logger.info("Testcase--> " + testCaseId + "->" + "  Validate GET " + InsightKey + "  User Subscription ,"
				+ testCaseDescription);
		automationTestResults.setInsights("NewSalary");
		automationTestResults.setBuildNo(buildNo);
		executedInsight = InsightKey;
		JsonArray keysArray = insightsGenerics.getInsightKeys("NewSalary");
		int numberOfKeys = insightsGenerics.getTotalNumberOfKeys(keysArray, validateKeys);
		storyId = testCaseId.substring(0, 7);
		TestInfo testInfo = new TestInfo();
		testInfo.setTestCaseId(testCaseId);
		testInfo.setTestDescription(testCaseDescription);
		HttpMethodParameters httpUserParams = insightsHelper.getHttpParams("userSubscriptionRequest", null, null,
				jwtUserAuthToken, null, null, null, null);
		userQueryParam.put("name", InsightKey);
		httpUserParams.setQueryParams(userQueryParam);
		userSubscriptionResponse = insightUtils.getUserSubscription(httpUserParams, null);
		Assert.assertEquals(userSubscriptionResponse.getStatusCode(), 200);
		String testStatus = insightsGenerics.getUserSubscriptionResponseVerification(cobSubscriptionResponse,
				userSubscriptionResponse, testExecutionStatus, testSummary, testCaseId, testCaseDescription,
				triggerType, InsightKey, insightName, title, containerSupported, keysArray,
				String.valueOf(numberOfKeys), description, applicableEntity, levels, isSubscribed, frequency,
				thresholdDetails, checkRuleExpression, configMetaAndentityParameter, expectedStatus, failureReason,
				validateKeys, testInfo);
		notificationsSummary(Boolean.valueOf(testStatus), testInfo, testCaseId, insightName, testCaseDescription);
		if (!Boolean.valueOf(testStatus))
			Assert.fail();

	}
	
	@Test(enabled = true, dataProvider = "feeder")
	@Source("\\TestData\\CSVFiles\\Insights\\AggregateSavingsCheckingAccount\\GetUserSubscriptionAggregateSavingsCheckingAccount.csv")
	public void testGetUserSubscriptionAggregateSavingsCheckingAccount(String testCaseId, String testCaseDescription,
			String triggerType, String InsightKey, String insightName, String title, String containerSupported,
			String validateKeys, String description, String applicableEntity, String levels, String isSubscribed,
			String frequency, String thresholdDetails, String checkRuleExpression, String configMetaAndentityParameter,
			int expectedStatus, String tcEnabled) throws ParseException, InterruptedException, IOException {

		boolean testExecutionStatus = false;
		commontUtils.isTCEnabled(tcEnabled, testCaseId);
		logger.info("Testcase--> " + testCaseId + "->" + "  Validate GET " + InsightKey + "  User Subscription ,"
				+ testCaseDescription);
		automationTestResults.setInsights("AggregateSavingsCheckingAccount");
		automationTestResults.setBuildNo(buildNo);
		executedInsight = InsightKey;
		JsonArray keysArray = insightsGenerics.getInsightKeys("AggregateSavingsCheckingAccount");
		int numberOfKeys = insightsGenerics.getTotalNumberOfKeys(keysArray, validateKeys);
		storyId = testCaseId.substring(0, 7);
		TestInfo testInfo = new TestInfo();
		testInfo.setTestCaseId(testCaseId);
		testInfo.setTestDescription(testCaseDescription);
		HttpMethodParameters httpUserParams = insightsHelper.getHttpParams("userSubscriptionRequest", null, null,
				jwtUserAuthToken, null, null, null, null);
		userQueryParam.put("name", InsightKey);
		httpUserParams.setQueryParams(userQueryParam);
		userSubscriptionResponse = insightUtils.getUserSubscription(httpUserParams, null);
		Assert.assertEquals(userSubscriptionResponse.getStatusCode(), 200);
		String testStatus = insightsGenerics.getUserSubscriptionResponseVerification(cobSubscriptionResponse,
				userSubscriptionResponse, testExecutionStatus, testSummary, testCaseId, testCaseDescription,
				triggerType, InsightKey, insightName, title, containerSupported, keysArray,
				String.valueOf(numberOfKeys), description, applicableEntity, levels, isSubscribed, frequency,
				thresholdDetails, checkRuleExpression, configMetaAndentityParameter, expectedStatus, failureReason,
				validateKeys, testInfo);
		notificationsSummary(Boolean.valueOf(testStatus), testInfo, testCaseId, insightName, testCaseDescription);
		if (!Boolean.valueOf(testStatus))
			Assert.fail();
	}
	
	@Test(enabled = true, dataProvider = "feeder")
	@Source("\\TestData\\CSVFiles\\Insights\\AggregateInvestmentAccount\\GetUserSubscriptionAggregateInvestmentAccount.csv")
	public void testGetUserSubscriptionAggregateInvestmentAccount(String testCaseId, String testCaseDescription,
			String triggerType, String InsightKey, String insightName, String title, String containerSupported,
			String validateKeys, String description, String applicableEntity, String levels, String isSubscribed,
			String frequency, String thresholdDetails, String checkRuleExpression, String configMetaAndentityParameter,
			int expectedStatus, String tcEnabled) throws ParseException, InterruptedException, IOException {

		boolean testExecutionStatus = false;
		commontUtils.isTCEnabled(tcEnabled, testCaseId);
		logger.info("Testcase--> " + testCaseId + "->" + "  Validate GET " + InsightKey + "  User Subscription ,"
				+ testCaseDescription);
		automationTestResults.setInsights("AggregateInvestmentAccount");
		automationTestResults.setBuildNo(buildNo);
		executedInsight = InsightKey;
		JsonArray keysArray = insightsGenerics.getInsightKeys("AggregateInvestmentAccount");
		int numberOfKeys = insightsGenerics.getTotalNumberOfKeys(keysArray, validateKeys);
		storyId = testCaseId.substring(0, 7);
		TestInfo testInfo = new TestInfo();
		testInfo.setTestCaseId(testCaseId);
		testInfo.setTestDescription(testCaseDescription);
		HttpMethodParameters httpUserParams = insightsHelper.getHttpParams("userSubscriptionRequest", null, null,
				jwtUserAuthToken, null, null, null, null);
		userQueryParam.put("name", InsightKey);
		httpUserParams.setQueryParams(userQueryParam);
		userSubscriptionResponse = insightUtils.getUserSubscription(httpUserParams, null);
		Assert.assertEquals(userSubscriptionResponse.getStatusCode(), 200);
		String testStatus = insightsGenerics.getUserSubscriptionResponseVerification(cobSubscriptionResponse,
				userSubscriptionResponse, testExecutionStatus, testSummary, testCaseId, testCaseDescription,
				triggerType, InsightKey, insightName, title, containerSupported, keysArray,
				String.valueOf(numberOfKeys), description, applicableEntity, levels, isSubscribed, frequency,
				thresholdDetails, checkRuleExpression, configMetaAndentityParameter, expectedStatus, failureReason,
				validateKeys, testInfo);
		notificationsSummary(Boolean.valueOf(testStatus), testInfo, testCaseId, insightName, testCaseDescription);
		if (!Boolean.valueOf(testStatus))
			Assert.fail();
	}
	
	@Test(enabled = true, dataProvider = "feeder")
	@Source("\\TestData\\CSVFiles\\Insights\\SubscriptionNotPaid\\GetUserSubscriptionSubscriptionNotPaid.csv")
	public void testGetUserSubscriptionSubscriptionNotPaid(String testCaseId, String testCaseDescription, String triggerType,
			String InsightKey, String insightName, String title, String containerSupported, String validateKeys,
			String totalNumberOfKeys, String description, String applicableEntity, String levels, String isSubscribed,
			String frequency, String thresholdDetails, String checkRuleExpression, String configMetaAndentityParameter,
			int expectedStatus, String tcEnabled) throws ParseException, InterruptedException, IOException {
		boolean testExecutionStatus = false;
		commontUtils.isTCEnabled(tcEnabled, testCaseId);
		logger.info("Testcase--> " + testCaseId + "->" + "  Validate GET " + InsightKey + "  User Subscription ,"
				+ testCaseDescription);
		automationTestResults.setInsights("SubscriptionNotPaid");
		automationTestResults.setBuildNo(buildNo);
		executedInsight = InsightKey;
		JsonArray keysArray = insightsGenerics.getInsightKeys("SubscriptionNotPaid");
		int numberOfKeys = insightsGenerics.getTotalNumberOfKeys(keysArray, validateKeys);
		storyId = testCaseId.substring(0, 7);
		TestInfo testInfo = new TestInfo();
		testInfo.setTestCaseId(testCaseId);
		testInfo.setTestDescription(testCaseDescription);
		HttpMethodParameters httpUserParams = insightsHelper.getHttpParams("userSubscriptionRequest", null, null,
				jwtUserAuthToken, null, null, null, null);
		userQueryParam.put("name", InsightKey);
		httpUserParams.setQueryParams(userQueryParam);
		userSubscriptionResponse = insightUtils.getUserSubscription(httpUserParams, null);
		Assert.assertEquals(userSubscriptionResponse.getStatusCode(), 200);
		String testStatus = insightsGenerics.getUserSubscriptionResponseVerification(cobSubscriptionResponse,
				userSubscriptionResponse, testExecutionStatus, testSummary, testCaseId, testCaseDescription,
				triggerType, InsightKey, insightName, title, containerSupported, keysArray,
				String.valueOf(numberOfKeys), description, applicableEntity, levels, isSubscribed, frequency,
				thresholdDetails, checkRuleExpression, configMetaAndentityParameter, expectedStatus, failureReason,
				validateKeys, testInfo);
		notificationsSummary(Boolean.valueOf(testStatus), testInfo, testCaseId, insightName, testCaseDescription);
		if (!Boolean.valueOf(testStatus))
			Assert.fail();
	}
	
	@AfterMethod(lastTimeOnly = true)
	public void terminate() throws Throwable {

		TestInfo testInfo = new TestInfo();
		testInfo = insightsHelper.printSummaryOfTests(notificationsTestSummary, "GET  LowBalanceWarning Insight",
				testInfo);
		automationTestResults.setExecutionDate(new Date().toString());
		automationTestResults.setTestInformation(testsInfoList);
		automationTestResults.setUserName(cobrandUser);
		automationTestResults.setStoryId(storyId);
		automationTestResults.setTestsPassPercentage(automationTestResults.getTestsPassPercentage(testInfo));
		insightsDBUtils.addTestResults(automationTestResults);
		testsInfoList.clear();
		System.out.println("Insight Test Execution is completed");
	}
	
	public void notificationsSummary(boolean notificationTestStatus, TestInfo testInfo, String testCaseId,
			String insightName, String testCaseDescription) {
		if (notificationTestStatus) {
			testInfo.setTestStatus("PASSED");
			notificationsTestSummary.put(testCaseId + "_" + insightName + "_" + testCaseDescription, "PASSED");
			testsInfoList.add(testInfo);
		} else {
			testInfo.setTestStatus("FAILED");
			testInfo.setReasonForFailure(failureReason.getFailureReason());
			notificationsTestSummary.put(testCaseId + "_" + insightName + "_" + testCaseDescription, "FAILED");
			notificationsTestSummary.put(testCaseId + "_" + insightName + "_" + "Failed Reason ### ",
					failureReason.getFailureReason());
			testsInfoList.add(testInfo);
		}
	}

	@AfterClass(alwaysRun = true)
	public void unRegisteredUser() {
		userUtils.unRegisterUser(sessionObj);
	}

}
