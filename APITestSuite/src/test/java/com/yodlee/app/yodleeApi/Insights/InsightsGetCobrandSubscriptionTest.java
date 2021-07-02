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
import com.yodlee.yodleeApi.utils.apiUtils.ProviderAccountUtils;
import com.yodlee.yodleeApi.utils.apiUtils.TaskUtils;
import com.yodlee.yodleeApi.utils.apiUtils.UserUtils;
import com.yodlee.yodleeApi.utils.apiUtils.InsightUtils;
import com.yodlee.yodleeApi.utils.commonUtils.CommonUtils;

import io.restassured.response.Response;

public class InsightsGetCobrandSubscriptionTest extends Base {

	protected Logger logger = LoggerFactory.getLogger(InsightsGetCobrandSubscriptionTest.class);
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
	InsightsDBUtils insightsDBUtils = new InsightsDBUtils();
	InsightsHelper insightsHelper = new InsightsHelper();
	InsightsGenerics insightsGenerics = new InsightsGenerics();
	HashMap<String, ArrayList<String>> negativeExpectedValuesMap;
	static Map<String, String> testSummary = new HashMap<String, String>();
	String jwtCobAuthToken = null;
	String executedInsight = null;
	AutomationTestResults automationTestResults = new AutomationTestResults();
	List<TestInfo> testsInfoList = null;
	String storyId = null, buildNo = "276";
	static FailureReason failureReason = null;
	static Map<String, String> notificationsTestSummary = new HashMap<String, String>();
	String cobrandUser = null;
	InsightsProperties prop = new InsightsProperties();
	boolean triggerOnce = false;
	Response cobSubscriptionResponse = null;
	String envCobrand = null;
	String envUser = null;

	@BeforeClass(alwaysRun = true)
	public void setUp() throws Exception {
		System.out.println("initiated GET cobrand subscription API execution");
		envCobrand = prop.readPropertiesFile().getProperty("envCobrand");
		cobrandUser = prop.readPropertiesFile().getProperty("cobrandUser");
	}

	@BeforeMethod(firstTimeOnly = true)
	public void initializations() throws Exception {
		jwtCobAuthToken = jwtHelper.getJWTToken(cobrandUser, envCobrand);
		jwtCobAuthToken = "Bearer ".concat(jwtCobAuthToken);
		testSummary = new HashMap<String, String>();
		failureReason = new FailureReason();
		testsInfoList = new ArrayList<TestInfo>();
		HttpMethodParameters httpParams = insightsHelper.getHttpParams("cobrandSubscriptionRequest", null, null,
				jwtCobAuthToken, null, null, null, null);
		cobSubscriptionResponse = insightUtils.getCobrandSubscriptions(httpParams, null);
	}

	@Test(enabled = true, dataProvider = "feeder")
	@Source("\\TestData\\CSVFiles\\Insights\\LowBalWarning\\GetCobSubscriptionLowBalWarning.csv")
	public void testGetCobrandSubscriptionForLowBalWarning(String testCaseId, String testCaseDescription,
			String triggerType, String InsightKey, String insightName, String title, String containerSupported,
			String validateKeys, String description, String applicableEntity, String levels, String isSubscribed,
			String frequency, String thresholdDetails, String checkRuleExpression, int expectedStatus, String tcEnabled)
					throws ParseException, InterruptedException {
		commontUtils.isTCEnabled(tcEnabled, testCaseId);
		automationTestResults.setInsights("COB_SUB_LowBalWarn");
		automationTestResults.setBuildNo(buildNo);
		boolean testExecutionStatus = false;
		executedInsight = InsightKey;
		logger.info("Testcase executed ---------------------------------------------->" + testCaseId);
		JsonArray keysArray = insightsGenerics.getInsightKeys("GETPATCHLowBalanceWarning");
		int numberOfKeys = insightsGenerics.getTotalNumberOfKeys(keysArray, validateKeys);
		storyId = testCaseId.substring(0, 7);
		TestInfo testInfo = new TestInfo();
		testInfo.setTestCaseId(testCaseId);
		testInfo.setTestDescription(testCaseDescription);
		String testStatus = insightsGenerics.getCobrandSubscriptionResponseVerification(cobSubscriptionResponse,
				testExecutionStatus, testSummary, testCaseId, testCaseDescription, triggerType, InsightKey, insightName,
				title, containerSupported, keysArray, String.valueOf(numberOfKeys), description, applicableEntity,
				levels, isSubscribed, frequency, thresholdDetails, checkRuleExpression, expectedStatus, failureReason,
				validateKeys, testInfo);
		notificationsSummary(Boolean.valueOf(testStatus), testInfo, testCaseId, insightName, testCaseDescription);
		if (!Boolean.valueOf(testStatus))
			Assert.fail();

	}

	@Test(enabled = true, dataProvider = "feeder")
	@Source("\\TestData\\CSVFiles\\Insights\\GoalSavingsOffTrack\\GetCobSubscriptionGoalOffTrack.csv")
	public void testGetCobrandSubscriptionForGoalOffTrack(String testCaseId, String testCaseDescription,
			String triggerType, String InsightKey, String insightName, String title, String containerSupported,
			String validateKeys, String description, String applicableEntity, String levels, String isSubscribed,
			String frequency, String thresholdDetails, String checkRuleExpression, int expectedStatus, String tcEnabled)
					throws ParseException, InterruptedException {
		commontUtils.isTCEnabled(tcEnabled, testCaseId);
		automationTestResults.setInsights("COB_SUB_GoalOffTrack");
		automationTestResults.setBuildNo(buildNo);
		boolean testExecutionStatus = false;
		executedInsight = InsightKey;
		logger.info("Testcase executed ---------------------------------------------->" + testCaseId);
		JsonArray keysArray = insightsGenerics.getInsightKeys("GETPATCHGoalOffTrack");
		int numberOfKeys = insightsGenerics.getTotalNumberOfKeys(keysArray, validateKeys);
		storyId = testCaseId.substring(0, 7);
		TestInfo testInfo = new TestInfo();
		testInfo.setTestCaseId(testCaseId);
		testInfo.setTestDescription(testCaseDescription);
		String testStatus = insightsGenerics.getCobrandSubscriptionResponseVerification(cobSubscriptionResponse,
				testExecutionStatus, testSummary, testCaseId, testCaseDescription, triggerType, InsightKey, insightName,
				title, containerSupported, keysArray, String.valueOf(numberOfKeys), description, applicableEntity,
				levels, isSubscribed, frequency, thresholdDetails, checkRuleExpression, expectedStatus, failureReason,
				validateKeys, testInfo);
		notificationsSummary(Boolean.valueOf(testStatus), testInfo, testCaseId, insightName, testCaseDescription);
		if (!Boolean.valueOf(testStatus))
			Assert.fail();
	}

	@Test(enabled = true, dataProvider = "feeder")
	@Source("\\TestData\\CSVFiles\\Insights\\ChangeInNetworth\\GetCobSubscriptionNetworthChange.csv")
	public void testGetCobrandSubscriptionForNetworthChange(String testCaseId, String testCaseDescription,
			String triggerType, String InsightKey, String insightName, String title, String containerSupported,
			String validateKeys, String description, String applicableEntity, String levels, String isSubscribed,
			String frequency, String thresholdDetails, String checkRuleExpression, int expectedStatus, String tcEnabled)
					throws ParseException, InterruptedException {
		commontUtils.isTCEnabled(tcEnabled, testCaseId);
		automationTestResults.setInsights("COB_SUB_NetworthChange");
		automationTestResults.setBuildNo(buildNo);
		boolean testExecutionStatus = false;
		executedInsight = InsightKey;
		logger.info("Testcase executed ---------------------------------------------->" + testCaseId);
		JsonArray keysArray = insightsGenerics.getInsightKeys("GETPATCHNetworthChange");
		int numberOfKeys = insightsGenerics.getTotalNumberOfKeys(keysArray, validateKeys);
		storyId = testCaseId.substring(0, 7);
		TestInfo testInfo = new TestInfo();
		testInfo.setTestCaseId(testCaseId);
		testInfo.setTestDescription(testCaseDescription);
		String testStatus = insightsGenerics.getCobrandSubscriptionResponseVerification(cobSubscriptionResponse,
				testExecutionStatus, testSummary, testCaseId, testCaseDescription, triggerType, InsightKey, insightName,
				title, containerSupported, keysArray, String.valueOf(numberOfKeys), description, applicableEntity,
				levels, isSubscribed, frequency, thresholdDetails, checkRuleExpression, expectedStatus, failureReason,
				validateKeys, testInfo);
		notificationsSummary(Boolean.valueOf(testStatus), testInfo, testCaseId, insightName, testCaseDescription);
		if (!Boolean.valueOf(testStatus))
			Assert.fail();
	}

	@Test(enabled = true, dataProvider = "feeder")
	@Source("\\TestData\\CSVFiles\\Insights\\SalDeposited\\GetCobSubscriptionSalaryDeposited.csv")
	public void testGetCobrandSubscriptionForSalaryDeposit(String testCaseId, String testCaseDescription,
			String triggerType, String InsightKey, String insightName, String title, String containerSupported,
			String validateKeys, String description, String applicableEntity, String levels, String isSubscribed,
			String frequency, String thresholdDetails, String checkRuleExpression, int expectedStatus, String tcEnabled)
					throws ParseException, InterruptedException {
		commontUtils.isTCEnabled(tcEnabled, testCaseId);
		automationTestResults.setInsights("COB_SUB_SalDeposited");
		automationTestResults.setBuildNo(buildNo);
		boolean testExecutionStatus = false;
		executedInsight = InsightKey;
		logger.info("Testcase executed ---------------------------------------------->" + testCaseId);
		JsonArray keysArray = insightsGenerics.getInsightKeys("GETPATCHSalaryDeposited");
		int numberOfKeys = insightsGenerics.getTotalNumberOfKeys(keysArray, validateKeys);
		storyId = testCaseId.substring(0, 7);
		TestInfo testInfo = new TestInfo();
		testInfo.setTestCaseId(testCaseId);
		testInfo.setTestDescription(testCaseDescription);
		String testStatus = insightsGenerics.getCobrandSubscriptionResponseVerification(cobSubscriptionResponse,
				testExecutionStatus, testSummary, testCaseId, testCaseDescription, triggerType, InsightKey, insightName,
				title, containerSupported, keysArray, String.valueOf(numberOfKeys), description, applicableEntity,
				levels, isSubscribed, frequency, thresholdDetails, checkRuleExpression, expectedStatus, failureReason,
				validateKeys, testInfo);
		notificationsSummary(Boolean.valueOf(testStatus), testInfo, testCaseId, insightName, testCaseDescription);
		if (!Boolean.valueOf(testStatus))
			Assert.fail();
	}

	@Test(enabled = true, dataProvider = "feeder")
	@Source("\\TestData\\CSVFiles\\Insights\\CameinUnderBudget\\GetCobSubscriptionCameinUnderBudget.csv")
	public void testGetCobrandSubscriptionForCameInUnderBudget(String testCaseId, String testCaseDescription,
			String triggerType, String InsightKey, String insightName, String title, String containerSupported,
			String validateKeys, String description, String applicableEntity, String levels, String isSubscribed,
			String frequency, String thresholdDetails, String checkRuleExpression, int expectedStatus, String tcEnabled)
					throws ParseException, InterruptedException {
		commontUtils.isTCEnabled(tcEnabled, testCaseId);
		automationTestResults.setInsights("COB_SUB_CaminUndBudget");
		automationTestResults.setBuildNo(buildNo);
		boolean testExecutionStatus = false;
		executedInsight = InsightKey;
		logger.info("Testcase executed ---------------------------------------------->" + testCaseId);
		JsonArray keysArray = insightsGenerics.getInsightKeys("GETPATCHCameinUnderBudget");
		int numberOfKeys = insightsGenerics.getTotalNumberOfKeys(keysArray, validateKeys);
		storyId = testCaseId.substring(0, 7);
		TestInfo testInfo = new TestInfo();
		testInfo.setTestCaseId(testCaseId);
		testInfo.setTestDescription(testCaseDescription);
		String testStatus = insightsGenerics.getCobrandSubscriptionResponseVerification(cobSubscriptionResponse,
				testExecutionStatus, testSummary, testCaseId, testCaseDescription, triggerType, InsightKey, insightName,
				title, containerSupported, keysArray, String.valueOf(numberOfKeys), description, applicableEntity,
				levels, isSubscribed, frequency, thresholdDetails, checkRuleExpression, expectedStatus, failureReason,
				validateKeys, testInfo);
		notificationsSummary(Boolean.valueOf(testStatus), testInfo, testCaseId, insightName, testCaseDescription);
		if (!Boolean.valueOf(testStatus))
			Assert.fail();
	}

	@Test(enabled = true, dataProvider = "feeder")
	@Source("\\TestData\\CSVFiles\\Insights\\ExpensesApproachingBudget\\GetCobSubscriptionExpensesApproachingBudget.csv")
	public void testGetCobrandSubscriptionForExpensesApproachingBudget(String testCaseId, String testCaseDescription,
			String triggerType, String InsightKey, String insightName, String title, String containerSupported,
			String validateKeys, String description, String applicableEntity, String levels, String isSubscribed,
			String frequency, String thresholdDetails, String checkRuleExpression, int expectedStatus, String tcEnabled)
					throws ParseException, InterruptedException {
		commontUtils.isTCEnabled(tcEnabled, testCaseId);
		automationTestResults.setInsights("COB_SUB_ExpensesApproachingBudget");
		automationTestResults.setBuildNo(buildNo);
		boolean testExecutionStatus = false;
		executedInsight = InsightKey;
		logger.info("Testcase executed ---------------------------------------------->" + testCaseId);
		JsonArray keysArray = insightsGenerics.getInsightKeys("GETPATCHExpensesApproachingBudget");
		int numberOfKeys = insightsGenerics.getTotalNumberOfKeys(keysArray, validateKeys);
		storyId = testCaseId.substring(0, 7);
		TestInfo testInfo = new TestInfo();
		testInfo.setTestCaseId(testCaseId);
		testInfo.setTestDescription(testCaseDescription);
		String testStatus = insightsGenerics.getCobrandSubscriptionResponseVerification(cobSubscriptionResponse,
				testExecutionStatus, testSummary, testCaseId, testCaseDescription, triggerType, InsightKey, insightName,
				title, containerSupported, keysArray, String.valueOf(numberOfKeys), description, applicableEntity,
				levels, isSubscribed, frequency, thresholdDetails, checkRuleExpression, expectedStatus, failureReason,
				validateKeys, testInfo);
		notificationsSummary(Boolean.valueOf(testStatus), testInfo, testCaseId, insightName, testCaseDescription);
		if (!Boolean.valueOf(testStatus))
			Assert.fail();
	}

	@Test(enabled = true, dataProvider = "feeder")
	@Source("\\TestData\\CSVFiles\\Insights\\LargeDepositNotice\\GetCobSubscriptionLargeDepositNotice.csv")
	public void testGetCobrandSubscriptionForLargeDepositNotice(String testCaseId, String testCaseDescription,
			String triggerType, String InsightKey, String insightName, String title, String containerSupported,
			String validateKeys, String description, String applicableEntity, String levels, String isSubscribed,
			String frequency, String thresholdDetails, String checkRuleExpression, String insightType,
			int expectedStatus, String tcEnabled) throws ParseException, InterruptedException {
		commontUtils.isTCEnabled(tcEnabled, testCaseId);
		automationTestResults.setInsights("COB_SUB_LargeDepositNotice");
		automationTestResults.setBuildNo(buildNo);
		boolean testExecutionStatus = false;
		executedInsight = InsightKey;
		logger.info("Testcase executed ---------------------------------------------->" + testCaseId);
		JsonArray keysArray = insightsGenerics.getInsightKeys("GETPATCHLargeDepositNotice");
		int numberOfKeys = insightsGenerics.getTotalNumberOfKeys(keysArray, validateKeys);
		storyId = testCaseId.substring(0, 7);
		TestInfo testInfo = new TestInfo();
		testInfo.setTestCaseId(testCaseId);
		testInfo.setTestDescription(testCaseDescription);
		String testStatus = insightsGenerics.getCobrandSubscriptionResponseVerification(cobSubscriptionResponse,
				testExecutionStatus, testSummary, testCaseId, testCaseDescription, triggerType, InsightKey, insightName,
				title, containerSupported, keysArray, String.valueOf(numberOfKeys), description, applicableEntity,
				levels, isSubscribed, frequency, thresholdDetails, checkRuleExpression, expectedStatus, failureReason,
				validateKeys, testInfo);
		notificationsSummary(Boolean.valueOf(testStatus), testInfo, testCaseId, insightName, testCaseDescription);
		if (!Boolean.valueOf(testStatus))
			Assert.fail(failureReason.getFailureReason());
	}

	@Test(enabled = true, dataProvider = "feeder")
	@Source("\\TestData\\CSVFiles\\Insights\\CreditLimitWarning1\\GetCobSubscriptionCreditLimitWarning.csv")
	public void testGetCobrandSubscriptionForCreditLimitWarning(String testCaseId, String testCaseDescription,
			String triggerType, String InsightKey, String insightName, String title, String containerSupported,
			String validateKeys, String description, String applicableEntity, String levels, String isSubscribed,
			String frequency, String thresholdDetails, String checkRuleExpression, int expectedStatus, String tcEnabled)
					throws ParseException, InterruptedException {
		commontUtils.isTCEnabled(tcEnabled, testCaseId);
		automationTestResults.setInsights("COB_SUB_CreditLimitWarning");
		automationTestResults.setBuildNo(buildNo);
		boolean testExecutionStatus = false;
		executedInsight = InsightKey;
		logger.info("Testcase executed ---------------------------------------------->" + testCaseId);
		JsonArray keysArray = insightsGenerics.getInsightKeys("GETPATCHCreditLimitWarning");
		int numberOfKeys = insightsGenerics.getTotalNumberOfKeys(keysArray, validateKeys);
		storyId = testCaseId.substring(0, 7);
		TestInfo testInfo = new TestInfo();
		testInfo.setTestCaseId(testCaseId);
		testInfo.setTestDescription(testCaseDescription);
		String testStatus = insightsGenerics.getCobrandSubscriptionResponseVerification(cobSubscriptionResponse,
				testExecutionStatus, testSummary, testCaseId, testCaseDescription, triggerType, InsightKey, insightName,
				title, containerSupported, keysArray, String.valueOf(numberOfKeys), description, applicableEntity,
				levels, isSubscribed, frequency, thresholdDetails, checkRuleExpression, expectedStatus, failureReason,
				validateKeys, testInfo);
		notificationsSummary(Boolean.valueOf(testStatus), testInfo, testCaseId, insightName, testCaseDescription);
		if (!Boolean.valueOf(testStatus))
			Assert.fail(failureReason.getFailureReason());
	}

	@Test(enabled = true, dataProvider = "feeder")
	@Source("\\TestData\\CSVFiles\\Insights\\CardStatementDueReminder\\GetCobSubscriptionCardStatementDueReminder.csv")
	public void testGetCobrandSubscriptionForCardStatementDueReminder(String testCaseId, String testCaseDescription,
			String triggerType, String InsightKey, String insightName, String title, String containerSupported,
			String validateKeys, String description, String applicableEntity, String levels, String isSubscribed,
			String frequency, String thresholdDetails, String checkRuleExpression, int expectedStatus, String tcEnabled)
					throws ParseException, InterruptedException {
		commontUtils.isTCEnabled(tcEnabled, testCaseId);
		automationTestResults.setInsights("COB_SUB_CardStatementDueReminder");
		automationTestResults.setBuildNo(buildNo);
		boolean testExecutionStatus = false;
		executedInsight = InsightKey;
		logger.info("Testcase executed ---------------------------------------------->" + testCaseId);
		JsonArray keysArray = insightsGenerics.getInsightKeys("GETPATCHCardStatementDueReminder");
		int numberOfKeys = insightsGenerics.getTotalNumberOfKeys(keysArray, validateKeys);
		storyId = testCaseId.substring(0, 7);
		TestInfo testInfo = new TestInfo();
		testInfo.setTestCaseId(testCaseId);
		testInfo.setTestDescription(testCaseDescription);
		String testStatus = insightsGenerics.getCobrandSubscriptionResponseVerification(cobSubscriptionResponse,
				testExecutionStatus, testSummary, testCaseId, testCaseDescription, triggerType, InsightKey, insightName,
				title, containerSupported, keysArray, String.valueOf(numberOfKeys), description, applicableEntity,
				levels, isSubscribed, frequency, thresholdDetails, checkRuleExpression, expectedStatus, failureReason,
				validateKeys, testInfo);
		notificationsSummary(Boolean.valueOf(testStatus), testInfo, testCaseId, insightName, testCaseDescription);
		if (!Boolean.valueOf(testStatus))
			Assert.fail(failureReason.getFailureReason());
	}

	@Test(enabled = true, dataProvider = "feeder")
	@Source("\\TestData\\CSVFiles\\Insights\\ChangeInLiability\\GetCobSubscriptionLiabilityChange.csv")
	public void testGetCobrandSubscriptionForLiabilityChange(String testCaseId, String testCaseDescription,
			String triggerType, String InsightKey, String insightName, String title, String containerSupported,
			String validateKeys, String description, String applicableEntity, String levels, String isSubscribed,
			String frequency, String thresholdDetails, String checkRuleExpression, int expectedStatus, String tcEnabled)
					throws ParseException, InterruptedException {
		commontUtils.isTCEnabled(tcEnabled, testCaseId);
		automationTestResults.setInsights("COB_SUB_LiabilityChange");
		automationTestResults.setBuildNo(buildNo);
		boolean testExecutionStatus = false;
		executedInsight = InsightKey;
		logger.info("Testcase executed ---------------------------------------------->" + testCaseId);
		JsonArray keysArray = insightsGenerics.getInsightKeys("GETPATCHLiabilityChange");
		int numberOfKeys = insightsGenerics.getTotalNumberOfKeys(keysArray, validateKeys);
		storyId = testCaseId.substring(0, 7);
		TestInfo testInfo = new TestInfo();
		testInfo.setTestCaseId(testCaseId);
		testInfo.setTestDescription(testCaseDescription);
		String testStatus = insightsGenerics.getCobrandSubscriptionResponseVerification(cobSubscriptionResponse,
				testExecutionStatus, testSummary, testCaseId, testCaseDescription, triggerType, InsightKey, insightName,
				title, containerSupported, keysArray, String.valueOf(numberOfKeys), description, applicableEntity,
				levels, isSubscribed, frequency, thresholdDetails, checkRuleExpression, expectedStatus, failureReason,
				validateKeys, testInfo);
		notificationsSummary(Boolean.valueOf(testStatus), testInfo, testCaseId, insightName, testCaseDescription);
		if (!Boolean.valueOf(testStatus))
			Assert.fail(failureReason.getFailureReason());
	}

	@Test(enabled = true, dataProvider = "feeder")
	@Source("\\TestData\\CSVFiles\\Insights\\ChangeInAsset\\GetCobSubscriptionAssetChange.csv")
	public void testGetCobrandSubscriptionForAssetChange(String testCaseId, String testCaseDescription,
			String triggerType, String InsightKey, String insightName, String title, String containerSupported,
			String validateKeys, String description, String applicableEntity, String levels, String isSubscribed,
			String frequency, String thresholdDetails, String checkRuleExpression, int expectedStatus, String tcEnabled)
					throws ParseException, InterruptedException {
		commontUtils.isTCEnabled(tcEnabled, testCaseId);
		automationTestResults.setInsights("COB_SUB_AssetChange");
		automationTestResults.setBuildNo(buildNo);
		boolean testExecutionStatus = false;
		executedInsight = InsightKey;
		logger.info("Testcase executed ---------------------------------------------->" + testCaseId);
		JsonArray keysArray = insightsGenerics.getInsightKeys("GETPATCHAssetChange");
		int numberOfKeys = insightsGenerics.getTotalNumberOfKeys(keysArray, validateKeys);
		storyId = testCaseId.substring(0, 7);
		TestInfo testInfo = new TestInfo();
		testInfo.setTestCaseId(testCaseId);
		testInfo.setTestDescription(testCaseDescription);
		String testStatus = insightsGenerics.getCobrandSubscriptionResponseVerification(cobSubscriptionResponse,
				testExecutionStatus, testSummary, testCaseId, testCaseDescription, triggerType, InsightKey, insightName,
				title, containerSupported, keysArray, String.valueOf(numberOfKeys), description, applicableEntity,
				levels, isSubscribed, frequency, thresholdDetails, checkRuleExpression, expectedStatus, failureReason,
				validateKeys, testInfo);
		notificationsSummary(Boolean.valueOf(testStatus), testInfo, testCaseId, insightName, testCaseDescription);
		if (!Boolean.valueOf(testStatus))
			Assert.fail(failureReason.getFailureReason());
	}

	@Test(enabled = true, dataProvider = "feeder")
	@Source("\\TestData\\CSVFiles\\Insights\\CreditUtilization1\\GetCobSubscriptionCreditUtilization.csv")
	public void testGetCobrandSubscriptionForCreditUtilization(String testCaseId, String testCaseDescription,
			String triggerType, String InsightKey, String insightName, String title, String containerSupported,
			String validateKeys, String description, String applicableEntity, String levels, String isSubscribed,
			String frequency, String thresholdDetails, String checkRuleExpression, int expectedStatus, String tcEnabled)
					throws ParseException, InterruptedException {
		commontUtils.isTCEnabled(tcEnabled, testCaseId);
		automationTestResults.setInsights("COB_SUB_CreditUtilization");
		automationTestResults.setBuildNo(buildNo);
		boolean testExecutionStatus = false;
		executedInsight = InsightKey;
		logger.info("Testcase executed ---------------------------------------------->" + testCaseId);
		JsonArray keysArray = insightsGenerics.getInsightKeys("GETPATCHCreditUtilization");
		int numberOfKeys = insightsGenerics.getTotalNumberOfKeys(keysArray, validateKeys);
		storyId = testCaseId.substring(0, 7);
		TestInfo testInfo = new TestInfo();
		testInfo.setTestCaseId(testCaseId);
		testInfo.setTestDescription(testCaseDescription);
		String testStatus = insightsGenerics.getCobrandSubscriptionResponseVerification(cobSubscriptionResponse,
				testExecutionStatus, testSummary, testCaseId, testCaseDescription, triggerType, InsightKey, insightName,
				title, containerSupported, keysArray, String.valueOf(numberOfKeys), description, applicableEntity,
				levels, isSubscribed, frequency, thresholdDetails, checkRuleExpression, expectedStatus, failureReason,
				validateKeys, testInfo);
		notificationsSummary(Boolean.valueOf(testStatus), testInfo, testCaseId, insightName, testCaseDescription);
		if (!Boolean.valueOf(testStatus))
			Assert.fail(failureReason.getFailureReason());
	}

	@Test(enabled = true, dataProvider = "feeder")
	@Source("\\TestData\\CSVFiles\\Insights\\LoanMaturityNotice\\GetCobSubscriptionLoanMaturityNotice.csv")
	public void testGetCobrandSubscriptionForLoanMaturityNotice(String testCaseId, String testCaseDescription,
			String triggerType, String InsightKey, String insightName, String title, String containerSupported,
			String validateKeys, String description, String applicableEntity, String levels, String isSubscribed,
			String frequency, String thresholdDetails, String checkRuleExpression, int expectedStatus, String tcEnabled)
					throws ParseException, InterruptedException {
		commontUtils.isTCEnabled(tcEnabled, testCaseId);
		JsonArray keysArray = insightsGenerics.getInsightKeys(insightName);
		int numberOfKeys = insightsGenerics.getTotalNumberOfKeys(keysArray, validateKeys);
		boolean testExecutionStatus = false;
		executedInsight = InsightKey;
		logger.info("Testcase executed ---------------------------------------------->" + testCaseId);
		// insightsGenerics.getCobrandSubscriptionResponseVerification(cobSubscriptionResponse,
		// testExecutionStatus, testSummary, testCaseId, testCaseDescription,
		// triggerType, InsightKey, insightName, title, containerSupported,keysArray,
		// String.valueOf(numberOfKeys), description, applicableEntity, levels,
		// isSubscribed, frequency, thresholdDetails, checkRuleExpression,
		// expectedStatus,failureReason,validateKeys);

	}

	@Test(enabled = true, dataProvider = "feeder")
	@Source("\\TestData\\CSVFiles\\Insights\\InvestmentMaturityNotice\\GetCobSubscriptionInvestmentMaturityNotice.csv")
	public void testGetCobrandSubscriptionForInvestmentMaturityNotice(String testCaseId, String testCaseDescription,
			String triggerType, String InsightKey, String insightName, String title, String containerSupported,
			String validateKeys, String description, String applicableEntity, String levels, String isSubscribed,
			String frequency, String thresholdDetails, String checkRuleExpression, int expectedStatus, String tcEnabled)
					throws ParseException, InterruptedException {
		commontUtils.isTCEnabled(tcEnabled, testCaseId);
		JsonArray keysArray = insightsGenerics.getInsightKeys(insightName);
		int numberOfKeys = insightsGenerics.getTotalNumberOfKeys(keysArray, validateKeys);
		boolean testExecutionStatus = false;
		executedInsight = InsightKey;
		logger.info("Testcase executed ---------------------------------------------->" + testCaseId);
		// insightsGenerics.getCobrandSubscriptionResponseVerification(cobSubscriptionResponse,
		// testExecutionStatus, testSummary, testCaseId, testCaseDescription,
		// triggerType, InsightKey, insightName, title, containerSupported,keysArray,
		// String.valueOf(numberOfKeys), description, applicableEntity, levels,
		// isSubscribed, frequency, thresholdDetails, checkRuleExpression,
		// expectedStatus,failureReason,validateKeys);

	}

	@Test(enabled = true, dataProvider = "feeder")
	@Source("\\TestData\\CSVFiles\\Insights\\FinancialFees\\GetCobSubscriptionFinancialFees.csv")
	public void testGetCobrandSubscriptionForFinancialFees(String testCaseId, String testCaseDescription,
			String triggerType, String InsightKey, String insightName, String title, String containerSupported,
			String validateKeys, String description, String applicableEntity, String levels, String isSubscribed,
			String frequency, String thresholdDetails, String checkRuleExpression, int expectedStatus, String tcEnabled)
					throws ParseException, InterruptedException {
		int numberOfKeys = 0;
		commontUtils.isTCEnabled(tcEnabled, testCaseId);
		automationTestResults.setInsights("GET_FinancialFees");
		automationTestResults.setBuildNo(buildNo);
		boolean testExecutionStatus = false;
		executedInsight = InsightKey;
		logger.info("Testcase executed ---------------------------------------------->" + testCaseId);
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
		String testStatus = insightsGenerics.getCobrandSubscriptionResponseVerification(cobSubscriptionResponse,
				testExecutionStatus, testSummary, testCaseId, testCaseDescription, triggerType, InsightKey, insightName,
				title, containerSupported, insightKeysArray, String.valueOf(numberOfKeys), description,
				applicableEntity, levels, isSubscribed, frequency, thresholdDetails, checkRuleExpression,
				expectedStatus, failureReason, validateKeys, testInfo);
		testInfo.setTestStatus(testStatus);
		String insightResponse = insightsGenerics.getSpecificInsightObjectResponse(cobSubscriptionResponse,
				executedInsight);
		testInfo.setResponse(insightResponse);
		testInfo.setReasonForFailure(failureReason.getFailureReason());
		testsInfoList.add(testInfo);
		if (testStatus.equals("FAILED")) {
			Assert.fail(testCaseId + " " + " Test case is failed");
			System.out.println("Failure Reason: " + failureReason);
			Assert.fail();
		}
	}

	@Test(enabled = true, dataProvider = "feeder")
	@Source("\\TestData\\CSVFiles\\Insights\\SpendingbyCategory\\GetCobSubscriptionSpendingbyCategory.csv")
	public void testGetCobrandSubscriptionForSpendingbyCategory(String testCaseId, String testCaseDescription,
			String triggerType, String InsightKey, String insightName, String title, String containerSupported,
			String validateKeys, String description, String applicableEntity, String levels, String isSubscribed,
			String frequency, String thresholdDetails, String checkRuleExpression, int expectedStatus, String tcEnabled)
					throws ParseException, InterruptedException {
		int numberOfKeys = 0;
		commontUtils.isTCEnabled(tcEnabled, testCaseId);
		automationTestResults.setInsights("GET_SpendingbyCategory");
		automationTestResults.setBuildNo(buildNo);
		boolean testExecutionStatus = false;
		executedInsight = InsightKey;
		logger.info("Testcase executed ---------------------------------------------->" + testCaseId);
		JsonArray insightKeysArray = insightsGenerics.getInsightKeysforFinancialFees(insightName, "PATCH");
		JsonArray pbKeysArray = insightsGenerics.getInsightsKeysforPBPATCH(insightName);
		if (validateKeys.equalsIgnoreCase("view"))
			numberOfKeys = insightsGenerics.getTotalNumberofFinancialFeesInsightKeys(validateKeys, insightName,
					"PATCH");
		else
			numberOfKeys = insightsGenerics.getTotalNumberofPBInsihgtKeys(validateKeys, insightName, "PATCH");
		insightKeysArray.addAll(pbKeysArray);
		// storyId = testCaseId.substring(0, 7);
		TestInfo testInfo = new TestInfo();
		testInfo.setTestCaseId(testCaseId);
		testInfo.setTestDescription(testCaseDescription);
		String testStatus = insightsGenerics.getCobrandSubscriptionResponseVerification(cobSubscriptionResponse,
				testExecutionStatus, testSummary, testCaseId, testCaseDescription, triggerType, InsightKey, insightName,
				title, containerSupported, insightKeysArray, String.valueOf(numberOfKeys), description,
				applicableEntity, levels, isSubscribed, frequency, thresholdDetails, checkRuleExpression,
				expectedStatus, failureReason, validateKeys, testInfo);
		testInfo.setTestStatus(testStatus);
		String insightResponse = insightsGenerics.getSpecificInsightObjectResponse(cobSubscriptionResponse,
				executedInsight);
		// testInfo.setResponse(insightResponse);
		testInfo.setReasonForFailure(failureReason.getFailureReason());
		testsInfoList.add(testInfo);
		if (testStatus.equals("FAILED")) {
			Assert.fail(testCaseId + " " + " Test case is failed");
			System.out.println("Failure Reason: " + failureReason);
			Assert.fail();
		}
	}

	@Test(enabled = true, dataProvider = "feeder")
	@Source("\\TestData\\CSVFiles\\Insights\\TopMerchants\\GetCobSubscriptionTopMerchants.csv")
	public void testGetCobrandSubscriptionForTopMerchants(String testCaseId, String testCaseDescription,
			String triggerType, String InsightKey, String insightName, String title, String containerSupported,
			String validateKeys, String description, String applicableEntity, String levels, String isSubscribed,
			String frequency, String thresholdDetails, String checkRuleExpression, int expectedStatus, String tcEnabled)
					throws ParseException, InterruptedException {
		int numberOfKeys = 0;
		commontUtils.isTCEnabled(tcEnabled, testCaseId);
		automationTestResults.setInsights("GET_TopMerchants");
		automationTestResults.setBuildNo(buildNo);
		boolean testExecutionStatus = false;
		executedInsight = InsightKey;
		logger.info("Testcase executed ---------------------------------------------->" + testCaseId);
		JsonArray insightKeysArray = insightsGenerics.getInsightKeysforFinancialFees(insightName, "PATCH");
		JsonArray pbKeysArray = insightsGenerics.getInsightsKeysforPBPATCH(insightName);
		if (validateKeys.equalsIgnoreCase("view"))
			numberOfKeys = insightsGenerics.getTotalNumberofFinancialFeesInsightKeys(validateKeys, insightName,
					"PATCH");
		else
			numberOfKeys = insightsGenerics.getTotalNumberofPBInsihgtKeys(validateKeys, insightName, "PATCH");
		insightKeysArray.addAll(pbKeysArray);
		// storyId = testCaseId.substring(0, 7);
		TestInfo testInfo = new TestInfo();
		testInfo.setTestCaseId(testCaseId);
		testInfo.setTestDescription(testCaseDescription);
		String testStatus = insightsGenerics.getCobrandSubscriptionResponseVerification(cobSubscriptionResponse,
				testExecutionStatus, testSummary, testCaseId, testCaseDescription, triggerType, InsightKey, insightName,
				title, containerSupported, insightKeysArray, String.valueOf(numberOfKeys), description,
				applicableEntity, levels, isSubscribed, frequency, thresholdDetails, checkRuleExpression,
				expectedStatus, failureReason, validateKeys, testInfo);
		testInfo.setTestStatus(testStatus);
		String insightResponse = insightsGenerics.getSpecificInsightObjectResponse(cobSubscriptionResponse,
				executedInsight);
		// testInfo.setResponse(insightResponse);
		testInfo.setReasonForFailure(failureReason.getFailureReason());
		testsInfoList.add(testInfo);
		if (testStatus.equals("FAILED")) {
			Assert.fail(testCaseId + " " + " Test case is failed");
			System.out.println("Failure Reason: " + failureReason);
			Assert.fail();
		}
	}

	@Test(enabled = true, dataProvider = "feeder")
	@Source("\\TestData\\CSVFiles\\Insights\\upcomingBills\\GetCobSubscriptionUpcomingBills.csv")
	public void testGetCobrandSubscriptionForUpcomingBills(String testCaseId, String testCaseDescription,
			String triggerType, String InsightKey, String insightName, String title, String containerSupported,
			String validateKeys, String totalNumberOfKeys, String description, String applicableEntity, String levels,
			String isSubscribed, String frequency, String thresholdDetails, String checkRuleExpression,
			int expectedStatus, String tcEnabled) throws ParseException, InterruptedException {
		commontUtils.isTCEnabled(tcEnabled, testCaseId);
		automationTestResults.setInsights("GET_UpcomingBills");
		automationTestResults.setBuildNo(buildNo);
		boolean testExecutionStatus = false;
		executedInsight = InsightKey;
		logger.info("Testcase executed ---------------------------------------------->" + testCaseId);
		JsonArray keysArray = insightsGenerics.getInsightKeys("UpcomingBills");
		int numberOfKeys = insightsGenerics.getTotalNumberOfKeys(keysArray, validateKeys);
		storyId = testCaseId.substring(0, 7);
		TestInfo testInfo = new TestInfo();
		testInfo.setTestCaseId(testCaseId);
		testInfo.setTestDescription(testCaseDescription);
		String testStatus = insightsGenerics.getCobrandSubscriptionResponseVerification(cobSubscriptionResponse,
				testExecutionStatus, testSummary, testCaseId, testCaseDescription, triggerType, InsightKey, insightName,
				title, containerSupported, keysArray, String.valueOf(numberOfKeys), description, applicableEntity,
				levels, isSubscribed, frequency, thresholdDetails, checkRuleExpression, expectedStatus, failureReason,
				validateKeys, testInfo);
		notificationsSummary(Boolean.valueOf(testStatus), testInfo, testCaseId, insightName, testCaseDescription);
		if (!Boolean.valueOf(testStatus))
			Assert.fail();

	}

	@Test(enabled = true, dataProvider = "feeder")
	@Source("\\TestData\\CSVFiles\\Insights\\upcomingSubscription\\GetCobSubscriptionUpcomingSubscription.csv")
	public void testGetCobrandSubscriptionForUpcomingSubscription(String testCaseId, String testCaseDescription,
			String triggerType, String InsightKey, String insightName, String title, String containerSupported,
			String validateKeys, String totalNumberOfKeys, String description, String applicableEntity, String levels,
			String isSubscribed, String frequency, String thresholdDetails, String checkRuleExpression,
			int expectedStatus, String tcEnabled) throws ParseException, InterruptedException {
		commontUtils.isTCEnabled(tcEnabled, testCaseId);
		automationTestResults.setInsights("GET_UpcomingSubscription");
		automationTestResults.setBuildNo(buildNo);
		boolean testExecutionStatus = false;
		executedInsight = InsightKey;
		logger.info("Testcase executed ---------------------------------------------->" + testCaseId);
		JsonArray keysArray = insightsGenerics.getInsightKeys("UpcomingSubscription");
		int numberOfKeys = insightsGenerics.getTotalNumberOfKeys(keysArray, validateKeys);
		storyId = testCaseId.substring(0, 7);
		TestInfo testInfo = new TestInfo();
		testInfo.setTestCaseId(testCaseId);
		testInfo.setTestDescription(testCaseDescription);
		String testStatus = insightsGenerics.getCobrandSubscriptionResponseVerification(cobSubscriptionResponse,
				testExecutionStatus, testSummary, testCaseId, testCaseDescription, triggerType, InsightKey, insightName,
				title, containerSupported, keysArray, String.valueOf(numberOfKeys), description, applicableEntity,
				levels, isSubscribed, frequency, thresholdDetails, checkRuleExpression, expectedStatus, failureReason,
				validateKeys, testInfo);
		notificationsSummary(Boolean.valueOf(testStatus), testInfo, testCaseId, insightName, testCaseDescription);
		if (!Boolean.valueOf(testStatus))
			Assert.fail();

	}

	@Test(enabled = true, dataProvider = "feeder")
	@Source("\\TestData\\CSVFiles\\Insights\\NewBillEventDetected\\GetCobSubscriptionNewBillEventDetected.csv")
	public void testGetCobrandSubscriptionForNewBillEventDetected(String testCaseId, String testCaseDescription,
			String triggerType, String InsightKey, String insightName, String title, String containerSupported,
			String validateKeys, String totalNumberOfKeys, String description, String applicableEntity, String levels,
			String isSubscribed, String frequency, String thresholdDetails, String checkRuleExpression,
			int expectedStatus, String tcEnabled) throws ParseException, InterruptedException {
		commontUtils.isTCEnabled(tcEnabled, testCaseId);
		automationTestResults.setInsights("GET_NewBill");
		automationTestResults.setBuildNo(buildNo);
		boolean testExecutionStatus = false;
		executedInsight = InsightKey;
		logger.info("Testcase executed ---------------------------------------------->" + testCaseId);
		JsonArray keysArray = insightsGenerics.getInsightKeys("NewBill");
		int numberOfKeys = insightsGenerics.getTotalNumberOfKeys(keysArray, validateKeys);
		storyId = testCaseId.substring(0, 7);
		TestInfo testInfo = new TestInfo();
		testInfo.setTestCaseId(testCaseId);
		testInfo.setTestDescription(testCaseDescription);
		String testStatus = insightsGenerics.getCobrandSubscriptionResponseVerification(cobSubscriptionResponse,
				testExecutionStatus, testSummary, testCaseId, testCaseDescription, triggerType, InsightKey, insightName,
				title, containerSupported, keysArray, String.valueOf(numberOfKeys), description, applicableEntity,
				levels, isSubscribed, frequency, thresholdDetails, checkRuleExpression, expectedStatus, failureReason,
				validateKeys, testInfo);
		notificationsSummary(Boolean.valueOf(testStatus), testInfo, testCaseId, insightName, testCaseDescription);
		if (!Boolean.valueOf(testStatus))
			Assert.fail();

	}

	@Test(enabled = true, dataProvider = "feeder")
	@Source("\\TestData\\CSVFiles\\Insights\\NewSubscriptionEventDetected\\GetCobSubscriptionNewSubscriptionEventDetected.csv")
	public void testGetCobrandSubscriptionForNewSubscriptionEventDetected(String testCaseId, String testCaseDescription,
			String triggerType, String InsightKey, String insightName, String title, String containerSupported,
			String validateKeys, String totalNumberOfKeys, String description, String applicableEntity, String levels,
			String isSubscribed, String frequency, String thresholdDetails, String checkRuleExpression,
			int expectedStatus, String tcEnabled) throws ParseException, InterruptedException {
		commontUtils.isTCEnabled(tcEnabled, testCaseId);
		automationTestResults.setInsights("GET_NewSubscription");
		automationTestResults.setBuildNo(buildNo);
		boolean testExecutionStatus = false;
		executedInsight = InsightKey;
		logger.info("Testcase executed ---------------------------------------------->" + testCaseId);
		JsonArray keysArray = insightsGenerics.getInsightKeys("NewSubscription");
		int numberOfKeys = insightsGenerics.getTotalNumberOfKeys(keysArray, validateKeys);
		storyId = testCaseId.substring(0, 7);
		TestInfo testInfo = new TestInfo();
		testInfo.setTestCaseId(testCaseId);
		testInfo.setTestDescription(testCaseDescription);
		String testStatus = insightsGenerics.getCobrandSubscriptionResponseVerification(cobSubscriptionResponse,
				testExecutionStatus, testSummary, testCaseId, testCaseDescription, triggerType, InsightKey, insightName,
				title, containerSupported, keysArray, String.valueOf(numberOfKeys), description, applicableEntity,
				levels, isSubscribed, frequency, thresholdDetails, checkRuleExpression, expectedStatus, failureReason,
				validateKeys, testInfo);
		notificationsSummary(Boolean.valueOf(testStatus), testInfo, testCaseId, insightName, testCaseDescription);
		if (!Boolean.valueOf(testStatus))
			Assert.fail();

	}

	@Test(enabled = true, dataProvider = "feeder")
	@Source("\\TestData\\CSVFiles\\Insights\\BillNotPaid\\GetCobSubscriptionBillNotPaid.csv")
	public void testGetCobrandSubscriptionForBillNotPaid(String testCaseId, String testCaseDescription,
			String triggerType, String InsightKey, String insightName, String title, String containerSupported,
			String validateKeys, String totalNumberOfKeys, String description, String applicableEntity, String levels,
			String isSubscribed, String frequency, String thresholdDetails, String checkRuleExpression,
			int expectedStatus, String tcEnabled) throws ParseException, InterruptedException {
		commontUtils.isTCEnabled(tcEnabled, testCaseId);
		automationTestResults.setInsights("GET_BillNotPaid");
		automationTestResults.setBuildNo(buildNo);
		boolean testExecutionStatus = false;
		executedInsight = InsightKey;
		logger.info("Testcase executed ---------------------------------------------->" + testCaseId);
		JsonArray keysArray = insightsGenerics.getInsightKeys("BillNotPaid");
		int numberOfKeys = insightsGenerics.getTotalNumberOfKeys(keysArray, validateKeys);
		storyId = testCaseId.substring(0, 7);
		TestInfo testInfo = new TestInfo();
		testInfo.setTestCaseId(testCaseId);
		testInfo.setTestDescription(testCaseDescription);
		String testStatus = insightsGenerics.getCobrandSubscriptionResponseVerification(cobSubscriptionResponse,
				testExecutionStatus, testSummary, testCaseId, testCaseDescription, triggerType, InsightKey, insightName,
				title, containerSupported, keysArray, String.valueOf(numberOfKeys), description, applicableEntity,
				levels, isSubscribed, frequency, thresholdDetails, checkRuleExpression, expectedStatus, failureReason,
				validateKeys, testInfo);
		notificationsSummary(Boolean.valueOf(testStatus), testInfo, testCaseId, insightName, testCaseDescription);
		if (!Boolean.valueOf(testStatus))
			Assert.fail();

	}

	@Test(enabled = true, dataProvider = "feeder")
	@Source("\\TestData\\CSVFiles\\Insights\\IncomeExpenseOverview\\GetCobSubscriptionIncomeExpenseOverview.csv")
	public void testGetCobrandSubscriptionForIncomeExpenseOverview(String testCaseId, String testCaseDescription,
			String triggerType, String InsightKey, String insightName, String title, String containerSupported,
			String validateKeys, String totalNumberOfKeys, String description, String applicableEntity, String levels,
			String isSubscribed, String insightType, String frequency, String duration, String thresholdDetails,
			String checkRuleExpression, int expectedStatus, String tcEnabled)
					throws ParseException, InterruptedException {
		commontUtils.isTCEnabled(tcEnabled, testCaseId);
		automationTestResults.setInsights("GET_IncomeExpenseOverview");
		automationTestResults.setBuildNo(buildNo);
		boolean testExecutionStatus = false;
		executedInsight = InsightKey;
		logger.info("Testcase executed ---------------------------------------------->" + testCaseId);
		JsonArray keysArray = insightsGenerics.getInsightKeys("IncomeExpenseOverview");
		int numberOfKeys = insightsGenerics.getTotalNumberOfKeys(keysArray, validateKeys);
		storyId = testCaseId.substring(0, 7);
		TestInfo testInfo = new TestInfo();
		testInfo.setTestCaseId(testCaseId);
		testInfo.setTestDescription(testCaseDescription);
		String testStatus = insightsGenerics.getCobrandSubscriptionResponseVerification(cobSubscriptionResponse,
				testExecutionStatus, testSummary, testCaseId, testCaseDescription, triggerType, InsightKey, insightName,
				title, containerSupported, keysArray, String.valueOf(numberOfKeys), description, applicableEntity,
				levels, isSubscribed, frequency, thresholdDetails, checkRuleExpression, expectedStatus, failureReason,
				validateKeys, testInfo);
		notificationsSummary(Boolean.valueOf(testStatus), testInfo, testCaseId, insightName, testCaseDescription);
		if (!Boolean.valueOf(testStatus))
			Assert.fail();

	}

	@Test(enabled = true, dataProvider = "feeder")
	@Source("\\TestData\\CSVFiles\\Insights\\BudgetProgress\\GetCobSubscriptionBudgetProgress.csv")
	public void testGetCobrandSubscriptionForBudgetProgress(String testCaseId, String testCaseDescription,
			String triggerType, String InsightKey, String insightName, String title, String containerSupported,
			String validateKeys, String totalNumberOfKeys, String description, String applicableEntity, String levels,
			String isSubscribed, String insightType, String frequency, String thresholdDetails,
			String checkRuleExpression, int expectedStatus, String tcEnabled)
					throws ParseException, InterruptedException {
		commontUtils.isTCEnabled(tcEnabled, testCaseId);
		automationTestResults.setInsights("GET_BudgetProgress");
		automationTestResults.setBuildNo(buildNo);
		boolean testExecutionStatus = false;
		executedInsight = InsightKey;
		logger.info("Testcase executed ---------------------------------------------->" + testCaseId);
		JsonArray keysArray = insightsGenerics.getInsightKeys("BudgetProgress");
		int numberOfKeys = insightsGenerics.getTotalNumberOfKeys(keysArray, validateKeys);
		storyId = testCaseId.substring(0, 7);
		TestInfo testInfo = new TestInfo();
		testInfo.setTestCaseId(testCaseId);
		testInfo.setTestDescription(testCaseDescription);
		String testStatus = insightsGenerics.getCobrandSubscriptionResponseVerification(cobSubscriptionResponse,
				testExecutionStatus, testSummary, testCaseId, testCaseDescription, triggerType, InsightKey, insightName,
				title, containerSupported, keysArray, String.valueOf(numberOfKeys), description, applicableEntity,
				levels, isSubscribed, frequency, thresholdDetails, checkRuleExpression, expectedStatus, failureReason,
				validateKeys, testInfo);
		notificationsSummary(Boolean.valueOf(testStatus), testInfo, testCaseId, insightName, testCaseDescription);
		if (!Boolean.valueOf(testStatus))
			Assert.fail();
	}

	@Test(enabled = true, dataProvider = "feeder")
	@Source("\\TestData\\CSVFiles\\Insights\\RefundDeposit\\GetCobSubscriptionRefundDeposit.csv")
	public void testGetCobrandSubscriptionForRefundDeposit(String testCaseId, String testCaseDescription,
			String triggerType, String InsightKey, String insightName, String title, String containerSupported,
			String validateKeys, String totalNumberOfKeys, String description, String applicableEntity, String levels,
			String isSubscribed, String frequency, String thresholdDetails, String checkRuleExpression,
			int expectedStatus, String tcEnabled) throws ParseException, InterruptedException {
		commontUtils.isTCEnabled(tcEnabled, testCaseId);
		automationTestResults.setInsights("GET_RefundDeposit");
		automationTestResults.setBuildNo(buildNo);
		boolean testExecutionStatus = false;
		executedInsight = InsightKey;
		logger.info("Testcase executed ---------------------------------------------->" + testCaseId);
		JsonArray keysArray = insightsGenerics.getInsightKeys("RefundDepositNotice");
		int numberOfKeys = insightsGenerics.getTotalNumberOfKeys(keysArray, validateKeys);
		storyId = testCaseId.substring(0, 7);
		TestInfo testInfo = new TestInfo();
		testInfo.setTestCaseId(testCaseId);
		testInfo.setTestDescription(testCaseDescription);
		String testStatus = insightsGenerics.getCobrandSubscriptionResponseVerification(cobSubscriptionResponse,
				testExecutionStatus, testSummary, testCaseId, testCaseDescription, triggerType, InsightKey, insightName,
				title, containerSupported, keysArray, String.valueOf(numberOfKeys), description, applicableEntity,
				levels, isSubscribed, frequency, thresholdDetails, checkRuleExpression, expectedStatus, failureReason,
				validateKeys, testInfo);
		notificationsSummary(Boolean.valueOf(testStatus), testInfo, testCaseId, insightName, testCaseDescription);
		if (!Boolean.valueOf(testStatus))
			Assert.fail();
	}

	@Test(enabled = true, dataProvider = "feeder")
	@Source("\\TestData\\CSVFiles\\Insights\\MostFrequentPurchases\\GetCobSubscriptionMostFrequentPurchases.csv")
	public void testGetCobrandSubscriptionForMostFrequentPurchases(String testCaseId, String testCaseDescription,
			String triggerType, String InsightKey, String insightName, String title, String containerSupported,
			String validateKeys, String totalNumberOfKeys, String description, String applicableEntity, String levels,
			String isSubscribed, String frequency, String duration, String thresholdDetails, String checkRuleExpression,
			int expectedStatus, String tcEnabled) throws ParseException, InterruptedException {
		commontUtils.isTCEnabled(tcEnabled, testCaseId);
		automationTestResults.setInsights("GET_MostFrequentPurchases");
		automationTestResults.setBuildNo(buildNo);
		boolean testExecutionStatus = false;
		executedInsight = InsightKey;
		logger.info("Testcase executed ---------------------------------------------->" + testCaseId);
		JsonArray keysArray = insightsGenerics.getInsightKeys("MostFrequentPurchases");
		int numberOfKeys = insightsGenerics.getTotalNumberOfKeys(keysArray, validateKeys);
		storyId = testCaseId.substring(0, 7);
		TestInfo testInfo = new TestInfo();
		testInfo.setTestCaseId(testCaseId);
		testInfo.setTestDescription(testCaseDescription);
		String testStatus = insightsGenerics.getCobrandSubscriptionResponseVerification(cobSubscriptionResponse,
				testExecutionStatus, testSummary, testCaseId, testCaseDescription, triggerType, InsightKey, insightName,
				title, containerSupported, keysArray, String.valueOf(numberOfKeys), description, applicableEntity,
				levels, isSubscribed, frequency, thresholdDetails, checkRuleExpression, expectedStatus, failureReason,
				validateKeys, testInfo);
		notificationsSummary(Boolean.valueOf(testStatus), testInfo, testCaseId, insightName, testCaseDescription);
		if (!Boolean.valueOf(testStatus))
			Assert.fail();
	}

	@Test(enabled = true, dataProvider = "feeder")
	@Source("\\TestData\\CSVFiles\\Insights\\CategorySpendingExceeded\\GetCobSubscriptionCategorySpendingExceeded.csv")
	public void testGetCobrandSubscriptionForCategorySpendingExceeded(String testCaseId, String testCaseDescription,
			String triggerType, String InsightKey, String insightName, String title, String containerSupported,
			String validateKeys, String totalNumberOfKeys, String description, String applicableEntity, String levels,
			String isSubscribed, String frequency, String thresholdDetails, String checkRuleExpression,
			int expectedStatus, String tcEnabled) throws ParseException, InterruptedException {
		commontUtils.isTCEnabled(tcEnabled, testCaseId);
		automationTestResults.setInsights("GET_CategorySpendingExceeded");
		automationTestResults.setBuildNo(buildNo);
		boolean testExecutionStatus = false;
		executedInsight = InsightKey;
		logger.info("Testcase executed ---------------------------------------------->" + testCaseId);
		JsonArray keysArray = insightsGenerics.getInsightKeys("CategorySpendingExceeded");
		int numberOfKeys = insightsGenerics.getTotalNumberOfKeys(keysArray, validateKeys);
		storyId = testCaseId.substring(0, 7);
		TestInfo testInfo = new TestInfo();
		testInfo.setTestCaseId(testCaseId);
		testInfo.setTestDescription(testCaseDescription);
		String testStatus = insightsGenerics.getCobrandSubscriptionResponseVerification(cobSubscriptionResponse,
				testExecutionStatus, testSummary, testCaseId, testCaseDescription, triggerType, InsightKey, insightName,
				title, containerSupported, keysArray, String.valueOf(numberOfKeys), description, applicableEntity,
				levels, isSubscribed, frequency, thresholdDetails, checkRuleExpression, expectedStatus, failureReason,
				validateKeys, testInfo);
		notificationsSummary(Boolean.valueOf(testStatus), testInfo, testCaseId, insightName, testCaseDescription);
		if (!Boolean.valueOf(testStatus))
			Assert.fail();
	}

	@Test(enabled = true, dataProvider = "feeder")
	@Source("\\TestData\\CSVFiles\\Insights\\ProjectedInsufficientBalance\\GetCobSubscriptionProjectedInsufficientBalance.csv")
	public void testGetCobrandSubscriptionForProjectedInsufficientBalance(String testCaseId, String testCaseDescription,
			String triggerType, String InsightKey, String insightName, String title, String containerSupported,
			String validateKeys, String totalNumberOfKeys, String description, String applicableEntity, String levels,
			String isSubscribed, String frequency, String thresholdDetails, String checkRuleExpression,
			int expectedStatus, String tcEnabled) throws ParseException, InterruptedException {
		commontUtils.isTCEnabled(tcEnabled, testCaseId);
		automationTestResults.setInsights("GET_ProjectedInsufficientBalance");
		automationTestResults.setBuildNo(buildNo);
		boolean testExecutionStatus = false;
		executedInsight = InsightKey;
		logger.info("Testcase executed ---------------------------------------------->" + testCaseId);
		JsonArray keysArray = insightsGenerics.getInsightKeys("ProjectedInsufficientBalance");
		int numberOfKeys = insightsGenerics.getTotalNumberOfKeys(keysArray, validateKeys);
		storyId = testCaseId.substring(0, 7);
		TestInfo testInfo = new TestInfo();
		testInfo.setTestCaseId(testCaseId);
		testInfo.setTestDescription(testCaseDescription);
		String testStatus = insightsGenerics.getCobrandSubscriptionResponseVerification(cobSubscriptionResponse,
				testExecutionStatus, testSummary, testCaseId, testCaseDescription, triggerType, InsightKey, insightName,
				title, containerSupported, keysArray, String.valueOf(numberOfKeys), description, applicableEntity,
				levels, isSubscribed, frequency, thresholdDetails, checkRuleExpression, expectedStatus, failureReason,
				validateKeys, testInfo);
		notificationsSummary(Boolean.valueOf(testStatus), testInfo, testCaseId, insightName, testCaseDescription);
		if (!Boolean.valueOf(testStatus))
			Assert.fail();

	}

	@Test(enabled = true, dataProvider = "feeder")
	@Source("\\TestData\\CSVFiles\\Insights\\ProjectedLowBalance\\GetCobSubscriptionProjectedLowBalance.csv")
	public void testGetCobrandSubscriptionForProjectedLowBalance(String testCaseId, String testCaseDescription,
			String triggerType, String InsightKey, String insightName, String title, String containerSupported,
			String validateKeys, String totalNumberOfKeys, String description, String applicableEntity, String levels,
			String isSubscribed, String frequency, String thresholdDetails, String checkRuleExpression,
			int expectedStatus, String tcEnabled) throws ParseException, InterruptedException {
		commontUtils.isTCEnabled(tcEnabled, testCaseId);
		automationTestResults.setInsights("GET_ProjectedLowBalance");
		automationTestResults.setBuildNo(buildNo);
		boolean testExecutionStatus = false;
		executedInsight = InsightKey;
		logger.info("Testcase executed ---------------------------------------------->" + testCaseId);
		JsonArray keysArray = insightsGenerics.getInsightKeys("ProjectedLowBalance");
		int numberOfKeys = insightsGenerics.getTotalNumberOfKeys(keysArray, validateKeys);
		storyId = testCaseId.substring(0, 7);
		TestInfo testInfo = new TestInfo();
		testInfo.setTestCaseId(testCaseId);
		testInfo.setTestDescription(testCaseDescription);
		String testStatus = insightsGenerics.getCobrandSubscriptionResponseVerification(cobSubscriptionResponse,
				testExecutionStatus, testSummary, testCaseId, testCaseDescription, triggerType, InsightKey, insightName,
				title, containerSupported, keysArray, String.valueOf(numberOfKeys), description, applicableEntity,
				levels, isSubscribed, frequency, thresholdDetails, checkRuleExpression, expectedStatus, failureReason,
				validateKeys, testInfo);
		notificationsSummary(Boolean.valueOf(testStatus), testInfo, testCaseId, insightName, testCaseDescription);
		if (!Boolean.valueOf(testStatus))
			Assert.fail();
	}

	@Test(enabled = true, dataProvider = "feeder")
	@Source("\\TestData\\CSVFiles\\Insights\\LargePurchase\\GetCobSubscriptionLargePurchase.csv")
	public void testGetCobrandSubscriptionForLargePurchase(String testCaseId, String testCaseDescription,
			String triggerType, String InsightKey, String insightName, String title, String containerSupported,
			String validateKeys, String totalNumberOfKeys, String description, String applicableEntity, String levels,
			String isSubscribed, String frequency, String thresholdDetails, String checkRuleExpression,
			int expectedStatus, String tcEnabled) throws ParseException, InterruptedException {
		commontUtils.isTCEnabled(tcEnabled, testCaseId);
		automationTestResults.setInsights("GET_LargePurchase");
		automationTestResults.setBuildNo(buildNo);
		boolean testExecutionStatus = false;
		executedInsight = InsightKey;
		logger.info("Testcase executed ---------------------------------------------->" + testCaseId);
		JsonArray keysArray = insightsGenerics.getInsightKeys("LargePurchase");
		int numberOfKeys = insightsGenerics.getTotalNumberOfKeys(keysArray, validateKeys);
		storyId = testCaseId.substring(0, 7);
		TestInfo testInfo = new TestInfo();
		testInfo.setTestCaseId(testCaseId);
		testInfo.setTestDescription(testCaseDescription);
		String testStatus = insightsGenerics.getCobrandSubscriptionResponseVerification(cobSubscriptionResponse,
				testExecutionStatus, testSummary, testCaseId, testCaseDescription, triggerType, InsightKey, insightName,
				title, containerSupported, keysArray, String.valueOf(numberOfKeys), description, applicableEntity,
				levels, isSubscribed, frequency, thresholdDetails, checkRuleExpression, expectedStatus, failureReason,
				validateKeys, testInfo);
		notificationsSummary(Boolean.valueOf(testStatus), testInfo, testCaseId, insightName, testCaseDescription);
		if (!Boolean.valueOf(testStatus))
			Assert.fail();
	}

	@Test(enabled = true, dataProvider = "feeder")
	@Source("\\TestData\\CSVFiles\\Insights\\CardStatementAvailable\\GetCobSubscriptionCardStatementAvailable.csv")
	public void testGetCobrandSubscriptionForCardStatementAvailable(String testCaseId, String testCaseDescription,
			String triggerType, String InsightKey, String insightName, String title, String containerSupported,
			String validateKeys, String totalNumberOfKeys, String description, String applicableEntity, String levels,
			String isSubscribed, String frequency, String thresholdDetails, String checkRuleExpression,
			int expectedStatus, String tcEnabled) throws ParseException, InterruptedException {
		commontUtils.isTCEnabled(tcEnabled, testCaseId);
		automationTestResults.setInsights("GET_CardStatementAvailable");
		automationTestResults.setBuildNo(buildNo);
		boolean testExecutionStatus = false;
		executedInsight = InsightKey;
		logger.info("Testcase executed ---------------------------------------------->" + testCaseId);
		JsonArray keysArray = insightsGenerics.getInsightKeys("CardStatementAvailable");
		int numberOfKeys = insightsGenerics.getTotalNumberOfKeys(keysArray, validateKeys);
		storyId = testCaseId.substring(0, 7);
		TestInfo testInfo = new TestInfo();
		testInfo.setTestCaseId(testCaseId);
		testInfo.setTestDescription(testCaseDescription);
		String testStatus = insightsGenerics.getCobrandSubscriptionResponseVerification(cobSubscriptionResponse,
				testExecutionStatus, testSummary, testCaseId, testCaseDescription, triggerType, InsightKey, insightName,
				title, containerSupported, keysArray, String.valueOf(numberOfKeys), description, applicableEntity,
				levels, isSubscribed, frequency, thresholdDetails, checkRuleExpression, expectedStatus, failureReason,
				validateKeys, testInfo);
		notificationsSummary(Boolean.valueOf(testStatus), testInfo, testCaseId, insightName, testCaseDescription);
		if (!Boolean.valueOf(testStatus))
			Assert.fail();
	}

	@Test(enabled = true, dataProvider = "feeder")
	@Source("\\TestData\\CSVFiles\\Insights\\PropertyValueChange\\GetCobSubscriptionPropertyValueChange.csv")
	public void testGetCobrandSubscriptionForPropertyValueChange(String testCaseId, String testCaseDescription,
			String triggerType, String InsightKey, String insightName, String title, String containerSupported,
			String keys, String validateKeys, String description, String applicableEntity, String levels,
			String isSubscribed, String frequency, String thresholdDetails, String checkRuleExpression,
			int expectedStatus, String tcEnabled) throws ParseException, InterruptedException {
		commontUtils.isTCEnabled(tcEnabled, testCaseId);
		automationTestResults.setInsights("COB_SUB_PropertyValueChange");
		automationTestResults.setBuildNo(buildNo);
		boolean testExecutionStatus = false;
		executedInsight = InsightKey;
		logger.info("Testcase executed ---------------------------------------------->" + testCaseId);
		JsonArray keysArray = insightsGenerics.getInsightKeys("PropertyValueChange");
		int numberOfKeys = insightsGenerics.getTotalNumberOfKeys(keysArray, validateKeys);
		storyId = testCaseId.substring(0, 7);
		TestInfo testInfo = new TestInfo();
		testInfo.setTestCaseId(testCaseId);
		testInfo.setTestDescription(testCaseDescription);
		String testStatus = insightsGenerics.getCobrandSubscriptionResponseVerification(cobSubscriptionResponse,
				testExecutionStatus, testSummary, testCaseId, testCaseDescription, triggerType, InsightKey, insightName,
				title, containerSupported, keysArray, String.valueOf(numberOfKeys), description, applicableEntity,
				levels, isSubscribed, frequency, thresholdDetails, checkRuleExpression, expectedStatus, failureReason,
				validateKeys, testInfo);
		notificationsSummary(Boolean.valueOf(testStatus), testInfo, testCaseId, insightName, testCaseDescription);
		if (!Boolean.valueOf(testStatus))
			Assert.fail();
	}

	@Test(enabled = true, dataProvider = "feeder")
	@Source("\\TestData\\CSVFiles\\Insights\\BillPaid\\GetCobSubscriptionBillPaid.csv")
	public void testGetCobrandSubscriptionForBillPaid(String testCaseId, String testCaseDescription, String triggerType,
			String InsightKey, String insightName, String title, String containerSupported, String validateKeys,
			String totalNumberOfKeys, String description, String applicableEntity, String levels, String isSubscribed,
			String frequency, String thresholdDetails, String checkRuleExpression, int expectedStatus, String tcEnabled)
					throws ParseException, InterruptedException {
		commontUtils.isTCEnabled(tcEnabled, testCaseId);
		automationTestResults.setInsights("GET_BillPaid");
		automationTestResults.setBuildNo(buildNo);
		boolean testExecutionStatus = false;
		executedInsight = InsightKey;
		logger.info("Testcase executed ---------------------------------------------->" + testCaseId);
		JsonArray keysArray = insightsGenerics.getInsightKeys("GETPatchBillPaid");
		int numberOfKeys = insightsGenerics.getTotalNumberOfKeys(keysArray, validateKeys);
		storyId = testCaseId.substring(0, 7);
		TestInfo testInfo = new TestInfo();
		testInfo.setTestCaseId(testCaseId);
		testInfo.setTestDescription(testCaseDescription);
		String testStatus = insightsGenerics.getCobrandSubscriptionResponseVerification(cobSubscriptionResponse,
				testExecutionStatus, testSummary, testCaseId, testCaseDescription, triggerType, InsightKey, insightName,
				title, containerSupported, keysArray, String.valueOf(numberOfKeys), description, applicableEntity,
				levels, isSubscribed, frequency, thresholdDetails, checkRuleExpression, expectedStatus, failureReason,
				validateKeys, testInfo);
		notificationsSummary(Boolean.valueOf(testStatus), testInfo, testCaseId, insightName, testCaseDescription);
		if (!Boolean.valueOf(testStatus))
			Assert.fail();

	}

	@Test(enabled = true, dataProvider = "feeder")
	@Source("\\TestData\\CSVFiles\\Insights\\SubscriptionPaid\\GetCobSubscriptionSubscriptionPaid.csv")
	public void testGetCobrandSubscriptionForSubscriptionPaid(String testCaseId, String testCaseDescription,
			String triggerType, String InsightKey, String insightName, String title, String containerSupported,
			String validateKeys, String totalNumberOfKeys, String description, String applicableEntity, String levels,
			String isSubscribed, String frequency, String thresholdDetails, String checkRuleExpression,
			int expectedStatus, String tcEnabled) throws ParseException, InterruptedException {
		commontUtils.isTCEnabled(tcEnabled, testCaseId);
		automationTestResults.setInsights("GET_SubscriptionPaid");
		automationTestResults.setBuildNo(buildNo);
		boolean testExecutionStatus = false;
		executedInsight = InsightKey;
		logger.info("Testcase executed ---------------------------------------------->" + testCaseId);
		JsonArray keysArray = insightsGenerics.getInsightKeys("GETPatchSubscriptionPaid");
		int numberOfKeys = insightsGenerics.getTotalNumberOfKeys(keysArray, validateKeys);
		storyId = testCaseId.substring(0, 7);
		TestInfo testInfo = new TestInfo();
		testInfo.setTestCaseId(testCaseId);
		testInfo.setTestDescription(testCaseDescription);
		String testStatus = insightsGenerics.getCobrandSubscriptionResponseVerification(cobSubscriptionResponse,
				testExecutionStatus, testSummary, testCaseId, testCaseDescription, triggerType, InsightKey, insightName,
				title, containerSupported, keysArray, String.valueOf(numberOfKeys), description, applicableEntity,
				levels, isSubscribed, frequency, thresholdDetails, checkRuleExpression, expectedStatus, failureReason,
				validateKeys, testInfo);
		notificationsSummary(Boolean.valueOf(testStatus), testInfo, testCaseId, insightName, testCaseDescription);
		if (!Boolean.valueOf(testStatus))
			Assert.fail();

	}

	@Test(enabled = true, dataProvider = "feeder")
	@Source("\\TestData\\CSVFiles\\Insights\\SubscriptionChanged\\GetCobSubscriptionSubscriptionChanged.csv")
	public void testGetCobrandSubscriptionForSubscriptionChanged(String testCaseId, String testCaseDescription,
			String triggerType, String InsightKey, String insightName, String title, String containerSupported,
			String validateKeys, String totalNumberOfKeys, String description, String applicableEntity, String levels,
			String isSubscribed, String frequency, String thresholdDetails, String checkRuleExpression,
			int expectedStatus, String tcEnabled) throws ParseException, InterruptedException {
		commontUtils.isTCEnabled(tcEnabled, testCaseId);
		automationTestResults.setInsights("GET_SubscriptionChanged");
		automationTestResults.setBuildNo(buildNo);
		boolean testExecutionStatus = false;
		executedInsight = InsightKey;
		logger.info("Testcase executed ---------------------------------------------->" + testCaseId);
		JsonArray keysArray = insightsGenerics.getInsightKeys("GETPatchSubscriptionChanged");
		int numberOfKeys = insightsGenerics.getTotalNumberOfKeys(keysArray, validateKeys);
		storyId = testCaseId.substring(0, 7);
		TestInfo testInfo = new TestInfo();
		testInfo.setTestCaseId(testCaseId);
		testInfo.setTestDescription(testCaseDescription);
		String testStatus = insightsGenerics.getCobrandSubscriptionResponseVerification(cobSubscriptionResponse,
				testExecutionStatus, testSummary, testCaseId, testCaseDescription, triggerType, InsightKey, insightName,
				title, containerSupported, keysArray, String.valueOf(numberOfKeys), description, applicableEntity,
				levels, isSubscribed, frequency, thresholdDetails, checkRuleExpression, expectedStatus, failureReason,
				validateKeys, testInfo);
		notificationsSummary(Boolean.valueOf(testStatus), testInfo, testCaseId, insightName, testCaseDescription);
		if (!Boolean.valueOf(testStatus))
			Assert.fail();

	}

	@Test(enabled = true, dataProvider = "feeder")
	@Source("\\TestData\\CSVFiles\\Insights\\BillChanged\\GetCobSubscriptionBillChanged.csv")
	public void testGetCobrandSubscriptionForBillChanged(String testCaseId, String testCaseDescription,
			String triggerType, String InsightKey, String insightName, String title, String containerSupported,
			String validateKeys, String totalNumberOfKeys, String description, String applicableEntity, String levels,
			String isSubscribed, String frequency, String thresholdDetails, String checkRuleExpression,
			int expectedStatus, String tcEnabled) throws ParseException, InterruptedException {
		commontUtils.isTCEnabled(tcEnabled, testCaseId);
		automationTestResults.setInsights("GET_BillChanged");
		automationTestResults.setBuildNo(buildNo);
		boolean testExecutionStatus = false;
		executedInsight = InsightKey;
		logger.info("Testcase executed ---------------------------------------------->" + testCaseId);
		JsonArray keysArray = insightsGenerics.getInsightKeys("GETPatchBillChanged");
		int numberOfKeys = insightsGenerics.getTotalNumberOfKeys(keysArray, validateKeys);
		storyId = testCaseId.substring(0, 7);
		TestInfo testInfo = new TestInfo();
		testInfo.setTestCaseId(testCaseId);
		testInfo.setTestDescription(testCaseDescription);
		String testStatus = insightsGenerics.getCobrandSubscriptionResponseVerification(cobSubscriptionResponse,
				testExecutionStatus, testSummary, testCaseId, testCaseDescription, triggerType, InsightKey, insightName,
				title, containerSupported, keysArray, String.valueOf(numberOfKeys), description, applicableEntity,
				levels, isSubscribed, frequency, thresholdDetails, checkRuleExpression, expectedStatus, failureReason,
				validateKeys, testInfo);
		notificationsSummary(Boolean.valueOf(testStatus), testInfo, testCaseId, insightName, testCaseDescription);
		if (!Boolean.valueOf(testStatus))
			Assert.fail();

	}

	@Test(enabled = true, dataProvider = "feeder")
	@Source("\\TestData\\CSVFiles\\Insights\\BalanceAboveThreshold\\GetCobSubscriptionBalanceAboveThreshold.csv")
	public void testGetCobrandSubscriptionForBalanceAboveThreshold(String testCaseId, String testCaseDescription,
			String triggerType, String InsightKey, String insightName, String title, String containerSupported,
			String validateKeys, String description, String applicableEntity, String levels, String isSubscribed,
			String frequency, String thresholdDetails, String checkRuleExpression, int expectedStatus, String tcEnabled)
					throws ParseException, InterruptedException {
		commontUtils.isTCEnabled(tcEnabled, testCaseId);
		automationTestResults.setInsights("COB_SUB_BalanceAboveThreshold");
		automationTestResults.setBuildNo(buildNo);
		boolean testExecutionStatus = false;
		executedInsight = InsightKey;
		logger.info("Testcase executed ---------------------------------------------->" + testCaseId);
		JsonArray keysArray = insightsGenerics.getInsightKeys("GETPATCHBalanceAboveThreshold");
		int numberOfKeys = insightsGenerics.getTotalNumberOfKeys(keysArray, validateKeys);
		storyId = testCaseId.substring(0, 7);
		TestInfo testInfo = new TestInfo();
		testInfo.setTestCaseId(testCaseId);
		testInfo.setTestDescription(testCaseDescription);
		String testStatus = insightsGenerics.getCobrandSubscriptionResponseVerification(cobSubscriptionResponse,
				testExecutionStatus, testSummary, testCaseId, testCaseDescription, triggerType, InsightKey, insightName,
				title, containerSupported, keysArray, String.valueOf(numberOfKeys), description, applicableEntity,
				levels, isSubscribed, frequency, thresholdDetails, checkRuleExpression, expectedStatus, failureReason,
				validateKeys, testInfo);
		notificationsSummary(Boolean.valueOf(testStatus), testInfo, testCaseId, insightName, testCaseDescription);
		if (!Boolean.valueOf(testStatus))
			Assert.fail();

	}

	@Test(enabled = true, dataProvider = "feeder")
	@Source("\\TestData\\CSVFiles\\Insights\\SalaryChanged\\GetCobSubscriptionSalaryChanged.csv")
	public void testGetCobrandSubscriptionForSalaryChanged(String testCaseId, String testCaseDescription,
			String triggerType, String InsightKey, String insightName, String title, String containerSupported,
			String keys, String validateKeys, String description, String applicableEntity, String levels,
			String isSubscribed, String frequency, String thresholdDetails, String checkRuleExpression,
			int expectedStatus, String tcEnabled) throws ParseException, InterruptedException {
		commontUtils.isTCEnabled(tcEnabled, testCaseId);
		automationTestResults.setInsights("COB_SUB_SalaryChanged");
		automationTestResults.setBuildNo(buildNo);
		boolean testExecutionStatus = false;
		executedInsight = InsightKey;
		logger.info("Testcase executed ---------------------------------------------->" + testCaseId);
		JsonArray keysArray = insightsGenerics.getInsightKeys("GETPatchSalaryChanged");
		int numberOfKeys = insightsGenerics.getTotalNumberOfKeys(keysArray, validateKeys);
		storyId = testCaseId.substring(0, 7);
		TestInfo testInfo = new TestInfo();
		testInfo.setTestCaseId(testCaseId);
		testInfo.setTestDescription(testCaseDescription);
		String testStatus = insightsGenerics.getCobrandSubscriptionResponseVerification(cobSubscriptionResponse,
				testExecutionStatus, testSummary, testCaseId, testCaseDescription, triggerType, InsightKey, insightName,
				title, containerSupported, keysArray, String.valueOf(numberOfKeys), description, applicableEntity,
				levels, isSubscribed, frequency, thresholdDetails, checkRuleExpression, expectedStatus, failureReason,
				validateKeys, testInfo);
		notificationsSummary(Boolean.valueOf(testStatus), testInfo, testCaseId, insightName, testCaseDescription);
		if (!Boolean.valueOf(testStatus))
			Assert.fail();
	}

	@Test(enabled = true, dataProvider = "feeder")
	@Source("\\TestData\\CSVFiles\\Insights\\RecurringDepositLate\\GetCobSubscriptionRecurringDepositLate.csv")
	public void testGetCobrandSubscriptionForRecurringDepositLate(String testCaseId, String testCaseDescription,
			String triggerType, String InsightKey, String insightName, String title, String containerSupported,
			String validateKeys, String totalNumberOfKeys, String description, String applicableEntity, String levels,
			String isSubscribed, String frequency, String thresholdDetails, String checkRuleExpression,
			int expectedStatus, String tcEnabled) throws ParseException, InterruptedException {
		commontUtils.isTCEnabled(tcEnabled, testCaseId);
		automationTestResults.setInsights("RecurringDepositLate");
		automationTestResults.setBuildNo(buildNo);
		boolean testExecutionStatus = false;
		executedInsight = InsightKey;
		logger.info("Testcase executed ---------------------------------------------->" + testCaseId);
		JsonArray keysArray = insightsGenerics.getInsightKeys("RecurringDepositLate");
		int numberOfKeys = insightsGenerics.getTotalNumberOfKeys(keysArray, validateKeys);
		storyId = testCaseId.substring(0, 7);
		TestInfo testInfo = new TestInfo();
		testInfo.setTestCaseId(testCaseId);
		testInfo.setTestDescription(testCaseDescription);
		String testStatus = insightsGenerics.getCobrandSubscriptionResponseVerification(cobSubscriptionResponse,
				testExecutionStatus, testSummary, testCaseId, testCaseDescription, triggerType, InsightKey, insightName,
				title, containerSupported, keysArray, String.valueOf(numberOfKeys), description, applicableEntity,
				levels, isSubscribed, frequency, thresholdDetails, checkRuleExpression, expectedStatus, failureReason,
				validateKeys, testInfo);
		notificationsSummary(Boolean.valueOf(testStatus), testInfo, testCaseId, insightName, testCaseDescription);
		if (!Boolean.valueOf(testStatus))
			Assert.fail();
	}

	@Test(enabled = true, dataProvider = "feeder")
	@Source("\\TestData\\CSVFiles\\Insights\\LoanMaturityNotice\\GetCobSubscriptionLoanMaturityNotice.csv")
	public void testGetCobrandSubscriptionForLoanMaturityNotice(String testCaseId, String testCaseDescription,
			String triggerType, String InsightKey, String insightName, String title, String containerSupported,
			String validateKeys, String totalNumberOfKeys, String description, String applicableEntity, String levels,
			String isSubscribed, String frequency, String thresholdDetails, String checkRuleExpression,
			int expectedStatus, String tcEnabled) throws ParseException, InterruptedException {
		commontUtils.isTCEnabled(tcEnabled, testCaseId);
		automationTestResults.setInsights("LoanMaturityNotice");
		automationTestResults.setBuildNo(buildNo);
		boolean testExecutionStatus = false;
		executedInsight = InsightKey;
		logger.info("Testcase executed ---------------------------------------------->" + testCaseId);
		JsonArray keysArray = insightsGenerics.getInsightKeys("LoanMaturityNotice");
		int numberOfKeys = insightsGenerics.getTotalNumberOfKeys(keysArray, validateKeys);
		storyId = testCaseId.substring(0, 7);
		TestInfo testInfo = new TestInfo();
		testInfo.setTestCaseId(testCaseId);
		testInfo.setTestDescription(testCaseDescription);
		String testStatus = insightsGenerics.getCobrandSubscriptionResponseVerification(cobSubscriptionResponse,
				testExecutionStatus, testSummary, testCaseId, testCaseDescription, triggerType, InsightKey, insightName,
				title, containerSupported, keysArray, String.valueOf(numberOfKeys), description, applicableEntity,
				levels, isSubscribed, frequency, thresholdDetails, checkRuleExpression, expectedStatus, failureReason,
				validateKeys, testInfo);
		notificationsSummary(Boolean.valueOf(testStatus), testInfo, testCaseId, insightName, testCaseDescription);
		if (!Boolean.valueOf(testStatus))
			Assert.fail();
	}

	@Test(enabled = true, dataProvider = "feeder")
	@Source("\\TestData\\CSVFiles\\Insights\\InvestmentMaturityNotice\\GetCobSubscriptionInvestmentMaturityNotice.csv")
	public void testGetCobrandSubscriptionForInvestmentMaturityNotice(String testCaseId, String testCaseDescription,
			String triggerType, String InsightKey, String insightName, String title, String containerSupported,
			String validateKeys, String totalNumberOfKeys, String description, String applicableEntity, String levels,
			String isSubscribed, String frequency, String thresholdDetails, String checkRuleExpression,
			int expectedStatus, String tcEnabled) throws ParseException, InterruptedException {
		commontUtils.isTCEnabled(tcEnabled, testCaseId);
		automationTestResults.setInsights("InvestmentMaturityNotice");
		automationTestResults.setBuildNo(buildNo);
		boolean testExecutionStatus = false;
		executedInsight = InsightKey;
		logger.info("Testcase executed ---------------------------------------------->" + testCaseId);
		JsonArray keysArray = insightsGenerics.getInsightKeys("InvestmentMaturityNotice");
		int numberOfKeys = insightsGenerics.getTotalNumberOfKeys(keysArray, validateKeys);
		storyId = testCaseId.substring(0, 7);
		TestInfo testInfo = new TestInfo();
		testInfo.setTestCaseId(testCaseId);
		testInfo.setTestDescription(testCaseDescription);
		String testStatus = insightsGenerics.getCobrandSubscriptionResponseVerification(cobSubscriptionResponse,
				testExecutionStatus, testSummary, testCaseId, testCaseDescription, triggerType, InsightKey, insightName,
				title, containerSupported, keysArray, String.valueOf(numberOfKeys), description, applicableEntity,
				levels, isSubscribed, frequency, thresholdDetails, checkRuleExpression, expectedStatus, failureReason,
				validateKeys, testInfo);
		notificationsSummary(Boolean.valueOf(testStatus), testInfo, testCaseId, insightName, testCaseDescription);
		if (!Boolean.valueOf(testStatus))
			Assert.fail();
	}

	@Test(enabled = true, dataProvider = "feeder")
	@Source("\\TestData\\CSVFiles\\Insights\\IncomeThreshold\\GetCobSubscriptionIncomeThreshold.csv")
	public void testGetCobrandSubscriptionForIncomeThreshold(String testCaseId, String testCaseDescription,
			String triggerType, String InsightKey, String insightName, String title, String containerSupported,
			String validateKeys, String totalNumberOfKeys, String description, String applicableEntity, String levels,
			String isSubscribed, String frequency, String duration, String thresholdDetails, String checkRuleExpression,
			int expectedStatus, String tcEnabled) throws ParseException, InterruptedException {
		commontUtils.isTCEnabled(tcEnabled, testCaseId);
		automationTestResults.setInsights("IncomeThreshold");
		automationTestResults.setBuildNo(buildNo);
		boolean testExecutionStatus = false;
		executedInsight = InsightKey;
		logger.info("Testcase executed ---------------------------------------------->" + testCaseId);
		JsonArray keysArray = insightsGenerics.getInsightKeys("IncomeThreshold");
		int numberOfKeys = insightsGenerics.getTotalNumberOfKeys(keysArray, validateKeys);
		storyId = testCaseId.substring(0, 7);
		TestInfo testInfo = new TestInfo();
		testInfo.setTestCaseId(testCaseId);
		testInfo.setTestDescription(testCaseDescription);
		String testStatus = insightsGenerics.getCobrandSubscriptionResponseVerification(cobSubscriptionResponse,
				testExecutionStatus, testSummary, testCaseId, testCaseDescription, triggerType, InsightKey, insightName,
				title, containerSupported, keysArray, String.valueOf(numberOfKeys), description, applicableEntity,
				levels, isSubscribed, frequency, thresholdDetails, checkRuleExpression, expectedStatus, failureReason,
				validateKeys, testInfo);
		notificationsSummary(Boolean.valueOf(testStatus), testInfo, testCaseId, insightName, testCaseDescription);
		if (!Boolean.valueOf(testStatus))
			Assert.fail();
	}

	@Test(enabled = true, dataProvider = "feeder")
	@Source("\\TestData\\CSVFiles\\Insights\\ExpensesExceededBudget\\GetCobSubscriptionExpExceededBudget.csv")
	public void testGetCobrandSubscriptionForExpensesExceededBudget(String testCaseId, String testCaseDescription,
			String triggerType, String InsightKey, String insightName, String title, String containerSupported,
			String validateKeys, String description, String applicableEntity, String levels, String isSubscribed,
			String frequency, String thresholdDetails, String checkRuleExpression, int expectedStatus, String tcEnabled)
					throws ParseException, InterruptedException {
		commontUtils.isTCEnabled(tcEnabled, testCaseId);
		automationTestResults.setInsights("ExpensesExceededBudget");
		automationTestResults.setBuildNo(buildNo);
		boolean testExecutionStatus = false;
		executedInsight = InsightKey;
		logger.info("Testcase executed ---------------------------------------------->" + testCaseId);
		JsonArray keysArray = insightsGenerics.getInsightKeys("ExpensesExceededBudget");
		int numberOfKeys = insightsGenerics.getTotalNumberOfKeys(keysArray, validateKeys);
		storyId = testCaseId.substring(0, 7);
		TestInfo testInfo = new TestInfo();
		testInfo.setTestCaseId(testCaseId);
		testInfo.setTestDescription(testCaseDescription);
		String testStatus = insightsGenerics.getCobrandSubscriptionResponseVerification(cobSubscriptionResponse,
				testExecutionStatus, testSummary, testCaseId, testCaseDescription, triggerType, InsightKey, insightName,
				title, containerSupported, keysArray, String.valueOf(numberOfKeys), description, applicableEntity,
				levels, isSubscribed, frequency, thresholdDetails, checkRuleExpression, expectedStatus, failureReason,
				validateKeys, testInfo);
		notificationsSummary(Boolean.valueOf(testStatus), testInfo, testCaseId, insightName, testCaseDescription);
		if (!Boolean.valueOf(testStatus))
			Assert.fail();
	}

	@Test(enabled = true, dataProvider = "feeder")
	@Source("\\TestData\\CSVFiles\\Insights\\SpendingEarningComparison\\GetCobSubSpendingEarning.csv")
	public void testGetCobrandSubscriptionForSpendingEarningComparison(String testCaseId, String testCaseDescription,
			String triggerType, String InsightKey, String insightName, String title, String containerSupported,
			String validateKeys, String description, String applicableEntity, String levels, String isSubscribed,
			String frequency, String thresholdDetails, String checkRuleExpression, int expectedStatus, String tcEnabled)
					throws ParseException, InterruptedException {
		commontUtils.isTCEnabled(tcEnabled, testCaseId);
		automationTestResults.setInsights("SpendingEarningComparison");
		automationTestResults.setBuildNo(buildNo);
		boolean testExecutionStatus = false;
		executedInsight = InsightKey;
		logger.info("Testcase executed ---------------------------------------------->" + testCaseId);
		JsonArray keysArray = insightsGenerics.getInsightKeys("SpendingEarningComparison");
		int numberOfKeys = insightsGenerics.getTotalNumberOfKeys(keysArray, validateKeys);
		storyId = testCaseId.substring(0, 7);
		TestInfo testInfo = new TestInfo();
		testInfo.setTestCaseId(testCaseId);
		testInfo.setTestDescription(testCaseDescription);
		String testStatus = insightsGenerics.getCobrandSubscriptionResponseVerification(cobSubscriptionResponse,
				testExecutionStatus, testSummary, testCaseId, testCaseDescription, triggerType, InsightKey, insightName,
				title, containerSupported, keysArray, String.valueOf(numberOfKeys), description, applicableEntity,
				levels, isSubscribed, frequency, thresholdDetails, checkRuleExpression, expectedStatus, failureReason,
				validateKeys, testInfo);
		notificationsSummary(Boolean.valueOf(testStatus), testInfo, testCaseId, insightName, testCaseDescription);
		if (!Boolean.valueOf(testStatus))
			Assert.fail();
	}

	@Test(enabled = true, dataProvider = "feeder")
	@Source("\\TestData\\CSVFiles\\Insights\\EmergencySavings\\GetCobSubEmergencySavings.csv")
	public void testGetCobrandSubscriptionForEmergencySavings(String testCaseId, String testCaseDescription,
			String triggerType, String InsightKey, String insightName, String title, String containerSupported,
			String validateKeys, String description, String applicableEntity, String levels, String isSubscribed,
			String frequency, String thresholdDetails, String checkRuleExpression, int expectedStatus, String tcEnabled)
					throws ParseException, InterruptedException {
		commontUtils.isTCEnabled(tcEnabled, testCaseId);
		automationTestResults.setInsights("EmergencySavings");
		automationTestResults.setBuildNo(buildNo);
		boolean testExecutionStatus = false;
		executedInsight = InsightKey;
		logger.info("Testcase executed ---------------------------------------------->" + testCaseId);
		JsonArray keysArray = insightsGenerics.getInsightKeys("EmergencySavings");
		int numberOfKeys = insightsGenerics.getTotalNumberOfKeys(keysArray, validateKeys);
		storyId = testCaseId.substring(0, 7);
		TestInfo testInfo = new TestInfo();
		testInfo.setTestCaseId(testCaseId);
		testInfo.setTestDescription(testCaseDescription);
		String testStatus = insightsGenerics.getCobrandSubscriptionResponseVerification(cobSubscriptionResponse,
				testExecutionStatus, testSummary, testCaseId, testCaseDescription, triggerType, InsightKey, insightName,
				title, containerSupported, keysArray, String.valueOf(numberOfKeys), description, applicableEntity,
				levels, isSubscribed, frequency, thresholdDetails, checkRuleExpression, expectedStatus, failureReason,
				validateKeys, testInfo);
		notificationsSummary(Boolean.valueOf(testStatus), testInfo, testCaseId, insightName, testCaseDescription);
		if (!Boolean.valueOf(testStatus))
			Assert.fail();
	}

	@Test(enabled = true, dataProvider = "feeder")
	@Source("\\TestData\\CSVFiles\\Insights\\AggregateCreditCardAccount\\GetCobSubscriptionAggregateCreditCardAccount.csv")
	public void testGetCobrandSubscriptionForAggregateCreditCardAccount(String testCaseId, String testCaseDescription,
			String triggerType, String InsightKey, String insightName, String title, String containerSupported,
			String validateKeys, String description, String applicableEntity, String levels, String isSubscribed,
			String frequency, String thresholdDetails, String checkRuleExpression, int expectedStatus, String tcEnabled)
					throws ParseException, InterruptedException {
		commontUtils.isTCEnabled(tcEnabled, testCaseId);
		automationTestResults.setInsights("AggregateCreditCardAccount");
		automationTestResults.setBuildNo(buildNo);
		boolean testExecutionStatus = false;
		executedInsight = InsightKey;
		logger.info("Testcase executed ---------------------------------------------->" + testCaseId);
		JsonArray keysArray = insightsGenerics.getInsightKeys("AggregateCreditCardAccount");
		int numberOfKeys = insightsGenerics.getTotalNumberOfKeys(keysArray, validateKeys);
		storyId = testCaseId.substring(0, 7);
		TestInfo testInfo = new TestInfo();
		testInfo.setTestCaseId(testCaseId);
		testInfo.setTestDescription(testCaseDescription);
		String testStatus = insightsGenerics.getCobrandSubscriptionResponseVerification(cobSubscriptionResponse,
				testExecutionStatus, testSummary, testCaseId, testCaseDescription, triggerType, InsightKey, insightName,
				title, containerSupported, keysArray, String.valueOf(numberOfKeys), description, applicableEntity,
				levels, isSubscribed, frequency, thresholdDetails, checkRuleExpression, expectedStatus, failureReason,
				validateKeys, testInfo);
		notificationsSummary(Boolean.valueOf(testStatus), testInfo, testCaseId, insightName, testCaseDescription);
		if (!Boolean.valueOf(testStatus))
			Assert.fail();
	}

	@Test(enabled = true, dataProvider = "feeder")
	@Source("\\TestData\\CSVFiles\\Insights\\AggregateInsuranceAccount\\GetCobSubscriptionAggregateInsuranceAccount.csv")
	public void testGetCobrandSubscriptionForAggregateInsuranceAccount(String testCaseId, String testCaseDescription,
			String triggerType, String InsightKey, String insightName, String title, String containerSupported,
			String validateKeys, String description, String applicableEntity, String levels, String isSubscribed,
			String frequency, String thresholdDetails, String checkRuleExpression, int expectedStatus, String tcEnabled)
					throws ParseException, InterruptedException {
		commontUtils.isTCEnabled(tcEnabled, testCaseId);
		automationTestResults.setInsights("AggregateInsuranceAccount");
		automationTestResults.setBuildNo(buildNo);
		boolean testExecutionStatus = false;
		executedInsight = InsightKey;
		logger.info("Testcase executed ---------------------------------------------->" + testCaseId);
		JsonArray keysArray = insightsGenerics.getInsightKeys("AggregateInsuranceAccount");
		int numberOfKeys = insightsGenerics.getTotalNumberOfKeys(keysArray, validateKeys);
		storyId = testCaseId.substring(0, 7);
		TestInfo testInfo = new TestInfo();
		testInfo.setTestCaseId(testCaseId);
		testInfo.setTestDescription(testCaseDescription);
		String testStatus = insightsGenerics.getCobrandSubscriptionResponseVerification(cobSubscriptionResponse,
				testExecutionStatus, testSummary, testCaseId, testCaseDescription, triggerType, InsightKey, insightName,
				title, containerSupported, keysArray, String.valueOf(numberOfKeys), description, applicableEntity,
				levels, isSubscribed, frequency, thresholdDetails, checkRuleExpression, expectedStatus, failureReason,
				validateKeys, testInfo);
		notificationsSummary(Boolean.valueOf(testStatus), testInfo, testCaseId, insightName, testCaseDescription);
		if (!Boolean.valueOf(testStatus))
			Assert.fail();
	}

	@Test(enabled = true, dataProvider = "feeder")
	@Source("\\TestData\\CSVFiles\\Insights\\AggregateRealEstateAccount\\GetCobSubscriptionAggregateRealEstateAccount.csv")
	public void testGetCobrandSubscriptionForAggregateRealEstateAccount(String testCaseId, String testCaseDescription,
			String triggerType, String InsightKey, String insightName, String title, String containerSupported,
			String validateKeys, String description, String applicableEntity, String levels, String isSubscribed,
			String frequency, String thresholdDetails, String checkRuleExpression, int expectedStatus, String tcEnabled)
					throws ParseException, InterruptedException {
		commontUtils.isTCEnabled(tcEnabled, testCaseId);
		automationTestResults.setInsights("AggregateRealEstateAccount");
		automationTestResults.setBuildNo(buildNo);
		boolean testExecutionStatus = false;
		executedInsight = InsightKey;
		logger.info("Testcase executed ---------------------------------------------->" + testCaseId);
		JsonArray keysArray = insightsGenerics.getInsightKeys("AggregateRealEstateAccount");
		int numberOfKeys = insightsGenerics.getTotalNumberOfKeys(keysArray, validateKeys);
		storyId = testCaseId.substring(0, 7);
		TestInfo testInfo = new TestInfo();
		testInfo.setTestCaseId(testCaseId);
		testInfo.setTestDescription(testCaseDescription);
		String testStatus = insightsGenerics.getCobrandSubscriptionResponseVerification(cobSubscriptionResponse,
				testExecutionStatus, testSummary, testCaseId, testCaseDescription, triggerType, InsightKey, insightName,
				title, containerSupported, keysArray, String.valueOf(numberOfKeys), description, applicableEntity,
				levels, isSubscribed, frequency, thresholdDetails, checkRuleExpression, expectedStatus, failureReason,
				validateKeys, testInfo);
		notificationsSummary(Boolean.valueOf(testStatus), testInfo, testCaseId, insightName, testCaseDescription);
		if (!Boolean.valueOf(testStatus))
			Assert.fail();
	}
	@Test(enabled = true, dataProvider = "feeder")
	@Source("\\TestData\\CSVFiles\\Insights\\AggregateMortgageAccount\\GetCobSubscriptionAggregateMortgageAccount.csv")
	public void testGetCobrandSubscriptionForAggregateMortgageAccount(String testCaseId, String testCaseDescription,
			String triggerType, String InsightKey, String insightName, String title, String containerSupported,
			String validateKeys, String description, String applicableEntity, String levels, String isSubscribed,
			String frequency, String thresholdDetails, String checkRuleExpression, int expectedStatus, String tcEnabled)
					throws ParseException, InterruptedException {
		commontUtils.isTCEnabled(tcEnabled, testCaseId);
		automationTestResults.setInsights("AggregateMortgageAccount");
		automationTestResults.setBuildNo(buildNo);
		boolean testExecutionStatus = false;
		executedInsight = InsightKey;
		logger.info("Testcase executed ---------------------------------------------->" + testCaseId);
		JsonArray keysArray = insightsGenerics.getInsightKeys("AggregateMortgageAccount");
		int numberOfKeys = insightsGenerics.getTotalNumberOfKeys(keysArray, validateKeys);
		storyId = testCaseId.substring(0, 7);
		TestInfo testInfo = new TestInfo();
		testInfo.setTestCaseId(testCaseId);
		testInfo.setTestDescription(testCaseDescription);
		String testStatus = insightsGenerics.getCobrandSubscriptionResponseVerification(cobSubscriptionResponse,
				testExecutionStatus, testSummary, testCaseId, testCaseDescription, triggerType, InsightKey, insightName,
				title, containerSupported, keysArray, String.valueOf(numberOfKeys), description, applicableEntity,
				levels, isSubscribed, frequency, thresholdDetails, checkRuleExpression, expectedStatus, failureReason,
				validateKeys, testInfo);
		notificationsSummary(Boolean.valueOf(testStatus), testInfo, testCaseId, insightName, testCaseDescription);
		if (!Boolean.valueOf(testStatus))
			Assert.fail();
	}

	@Test(enabled = true, dataProvider = "feeder")
	@Source("\\TestData\\CSVFiles\\Insights\\ZeroCardInterestPaid\\GetCobSubscriptionZeroCardInterestPaid.csv")
	public void testGetCobrandSubscriptionForZeroCardInterestPaid(String testCaseId, String testCaseDescription,
			String triggerType, String InsightKey, String insightName, String title, String containerSupported,
			String validateKeys,String totalNumberOfKeys,String description, String applicableEntity, String levels, String isSubscribed,
			String frequency, String thresholdDetails, String checkRuleExpression, int expectedStatus, String tcEnabled)
					throws ParseException, InterruptedException {
		commontUtils.isTCEnabled(tcEnabled, testCaseId);
		automationTestResults.setInsights("ZeroCardInterestPaid");
		automationTestResults.setBuildNo(buildNo);
		boolean testExecutionStatus = false;
		executedInsight = InsightKey;
		logger.info("Testcase executed ---------------------------------------------->" + testCaseId);
		JsonArray keysArray = insightsGenerics.getInsightKeys("ZeroCardInterestPaid");
		int numberOfKeys = insightsGenerics.getTotalNumberOfKeys(keysArray, validateKeys);
		storyId = testCaseId.substring(0, 7);
		TestInfo testInfo = new TestInfo();
		testInfo.setTestCaseId(testCaseId);
		testInfo.setTestDescription(testCaseDescription);
		String testStatus = insightsGenerics.getCobrandSubscriptionResponseVerification(cobSubscriptionResponse,
				testExecutionStatus, testSummary, testCaseId, testCaseDescription, triggerType, InsightKey, insightName,
				title, containerSupported, keysArray, String.valueOf(numberOfKeys), description, applicableEntity,
				levels, isSubscribed, frequency, thresholdDetails, checkRuleExpression, expectedStatus, failureReason,
				validateKeys, testInfo);
		notificationsSummary(Boolean.valueOf(testStatus), testInfo, testCaseId, insightName, testCaseDescription);
		if (!Boolean.valueOf(testStatus))
			Assert.fail();
	}

	@Test(enabled = true, dataProvider = "feeder")
	@Source("\\TestData\\CSVFiles\\Insights\\NewSalary\\GetCobSubscriptionNewSalary.csv")
	public void testGetCobrandSubscriptionForNewSalary(String testCaseId, String testCaseDescription,
			String triggerType, String InsightKey, String insightName, String title, String containerSupported,
			String validateKeys, String totalNumberOfKeys, String description, String applicableEntity, String levels,
			String isSubscribed, String frequency, String thresholdDetails, String checkRuleExpression,
			int expectedStatus, String tcEnabled) throws ParseException, InterruptedException {
		commontUtils.isTCEnabled(tcEnabled, testCaseId);
		automationTestResults.setInsights("GET_NewSalary");
		automationTestResults.setBuildNo(buildNo);
		boolean testExecutionStatus = false;
		executedInsight = InsightKey;
		logger.info("Testcase executed ---------------------------------------------->" + testCaseId);
		JsonArray keysArray = insightsGenerics.getInsightKeys("NewSalary");
		int numberOfKeys = insightsGenerics.getTotalNumberOfKeys(keysArray, validateKeys);
		storyId = testCaseId.substring(0, 7);
		TestInfo testInfo = new TestInfo();
		testInfo.setTestCaseId(testCaseId);
		testInfo.setTestDescription(testCaseDescription);
		String testStatus = insightsGenerics.getCobrandSubscriptionResponseVerification(cobSubscriptionResponse,
				testExecutionStatus, testSummary, testCaseId, testCaseDescription, triggerType, InsightKey, insightName,
				title, containerSupported, keysArray, String.valueOf(numberOfKeys), description, applicableEntity,
				levels, isSubscribed, frequency, thresholdDetails, checkRuleExpression, expectedStatus, failureReason,
				validateKeys, testInfo);
		notificationsSummary(Boolean.valueOf(testStatus), testInfo, testCaseId, insightName, testCaseDescription);
		if (!Boolean.valueOf(testStatus))
			Assert.fail();

	}

	@Test(enabled = true, dataProvider = "feeder")
	@Source("\\TestData\\CSVFiles\\Insights\\SubscriptionNotPaid\\GetCobSubscriptionSubscriptionNotPaid.csv")
	public void testGetCobrandSubscriptionForSubscriptionNotPaid(String testCaseId, String testCaseDescription,
			String triggerType, String InsightKey, String insightName, String title, String containerSupported,
			String validateKeys, String totalNumberOfKeys, String description, String applicableEntity, String levels,
			String isSubscribed, String frequency, String thresholdDetails, String checkRuleExpression,
			int expectedStatus, String tcEnabled) throws ParseException, InterruptedException {
		commontUtils.isTCEnabled(tcEnabled, testCaseId);
		automationTestResults.setInsights("SubscriptionNotPaid");
		automationTestResults.setBuildNo(buildNo);
		boolean testExecutionStatus = false;
		executedInsight = InsightKey;
		logger.info("Testcase executed ---------------------------------------------->" + testCaseId);
		JsonArray keysArray = insightsGenerics.getInsightKeys("SubscriptionNotPaid");
		int numberOfKeys = insightsGenerics.getTotalNumberOfKeys(keysArray, validateKeys);
		storyId = testCaseId.substring(0, 7);
		TestInfo testInfo = new TestInfo();
		testInfo.setTestCaseId(testCaseId);
		testInfo.setTestDescription(testCaseDescription);
		String testStatus = insightsGenerics.getCobrandSubscriptionResponseVerification(cobSubscriptionResponse,
				testExecutionStatus, testSummary, testCaseId, testCaseDescription, triggerType, InsightKey, insightName,
				title, containerSupported, keysArray, String.valueOf(numberOfKeys), description, applicableEntity,
				levels, isSubscribed, frequency, thresholdDetails, checkRuleExpression, expectedStatus, failureReason,
				validateKeys, testInfo);
		notificationsSummary(Boolean.valueOf(testStatus), testInfo, testCaseId, insightName, testCaseDescription);
		if (!Boolean.valueOf(testStatus))
			Assert.fail();

	}

	@Test(enabled = true, dataProvider = "feeder")
	@Source("\\TestData\\CSVFiles\\Insights\\AggregateSavingsCheckingAccount\\GetCobSubscriptionAggregateSavingsCheckingAccount.csv")
	public void testGetCobrandSubscriptionForAggregateSavingsCheckingAccount(String testCaseId, String testCaseDescription,
			String triggerType, String InsightKey, String insightName, String title, String containerSupported,
			String validateKeys, String description, String applicableEntity, String levels, String isSubscribed,
			String frequency, String thresholdDetails, String checkRuleExpression, int expectedStatus, String tcEnabled)
					throws ParseException, InterruptedException {
		commontUtils.isTCEnabled(tcEnabled, testCaseId);
		automationTestResults.setInsights("AggregateSavingsCheckingAccount");
		automationTestResults.setBuildNo(buildNo);
		boolean testExecutionStatus = false;
		executedInsight = InsightKey;
		logger.info("Testcase executed ---------------------------------------------->" + testCaseId);
		JsonArray keysArray = insightsGenerics.getInsightKeys("AggregateSavingsCheckingAccount");
		int numberOfKeys = insightsGenerics.getTotalNumberOfKeys(keysArray, validateKeys);
		storyId = testCaseId.substring(0, 7);
		TestInfo testInfo = new TestInfo();
		testInfo.setTestCaseId(testCaseId);
		testInfo.setTestDescription(testCaseDescription);
		String testStatus = insightsGenerics.getCobrandSubscriptionResponseVerification(cobSubscriptionResponse,
				testExecutionStatus, testSummary, testCaseId, testCaseDescription, triggerType, InsightKey, insightName,
				title, containerSupported, keysArray, String.valueOf(numberOfKeys), description, applicableEntity,
				levels, isSubscribed, frequency, thresholdDetails, checkRuleExpression, expectedStatus, failureReason,
				validateKeys, testInfo);
		notificationsSummary(Boolean.valueOf(testStatus), testInfo, testCaseId, insightName, testCaseDescription);
		if (!Boolean.valueOf(testStatus))
			Assert.fail();
	}

	@Test(enabled = true, dataProvider = "feeder")
	@Source("\\TestData\\CSVFiles\\Insights\\AggregateInvestmentAccount\\GetCobSubscriptionAggregateInvestmentAccount.csv")
	public void testGetCobrandSubscriptionForAggregateInvestmentAccount(String testCaseId, String testCaseDescription,
			String triggerType, String InsightKey, String insightName, String title, String containerSupported,
			String validateKeys, String description, String applicableEntity, String levels, String isSubscribed,
			String frequency, String thresholdDetails, String checkRuleExpression, int expectedStatus, String tcEnabled)
					throws ParseException, InterruptedException {
		commontUtils.isTCEnabled(tcEnabled, testCaseId);
		automationTestResults.setInsights("AggregateInvestmentAccount");
		automationTestResults.setBuildNo(buildNo);
		boolean testExecutionStatus = false;
		executedInsight = InsightKey;
		logger.info("Testcase executed ---------------------------------------------->" + testCaseId);
		JsonArray keysArray = insightsGenerics.getInsightKeys("AggregateInvestmentAccount");
		int numberOfKeys = insightsGenerics.getTotalNumberOfKeys(keysArray, validateKeys);
		storyId = testCaseId.substring(0, 7);
		TestInfo testInfo = new TestInfo();
		testInfo.setTestCaseId(testCaseId);
		testInfo.setTestDescription(testCaseDescription);
		String testStatus = insightsGenerics.getCobrandSubscriptionResponseVerification(cobSubscriptionResponse,
				testExecutionStatus, testSummary, testCaseId, testCaseDescription, triggerType, InsightKey, insightName,
				title, containerSupported, keysArray, String.valueOf(numberOfKeys), description, applicableEntity,
				levels, isSubscribed, frequency, thresholdDetails, checkRuleExpression, expectedStatus, failureReason,
				validateKeys, testInfo);
		notificationsSummary(Boolean.valueOf(testStatus), testInfo, testCaseId, insightName, testCaseDescription);
		if (!Boolean.valueOf(testStatus))
			Assert.fail();
	}

	@Test(enabled = true, dataProvider = "feeder")
	@Source("\\TestData\\CSVFiles\\Insights\\SpendingByCategoryConsumerLevel\\GetCobSubSpendCatAllAcct.csv")
	public void testGetCobrandSubscriptionForSpendingbyCategoryAllAccounts(String testCaseId, String testCaseDescription,
			String triggerType, String InsightKey, String insightName, String title, String containerSupported,
			String validateKeys, String description, String applicableEntity, String levels, String isSubscribed,
			String frequency, String thresholdDetails, String checkRuleExpression, int expectedStatus, String tcEnabled)
					throws ParseException, InterruptedException {
		commontUtils.isTCEnabled(tcEnabled, testCaseId);
		automationTestResults.setInsights("SpendingbyCategory-AllAccounts");
		automationTestResults.setBuildNo(buildNo);
		boolean testExecutionStatus = false;
		executedInsight = InsightKey;
		logger.info("Testcase executed ---------------------------------------------->" + testCaseId);
		JsonArray keysArray = insightsGenerics.getInsightKeys("SpendingbyCategoryAllAccounts");
		int numberOfKeys = insightsGenerics.getTotalNumberOfKeys(keysArray, validateKeys);
		storyId = testCaseId.substring(0, 7);
		TestInfo testInfo = new TestInfo();
		testInfo.setTestCaseId(testCaseId);
		testInfo.setTestDescription(testCaseDescription);
		String testStatus = insightsGenerics.getCobrandSubscriptionResponseVerification(cobSubscriptionResponse,
				testExecutionStatus, testSummary, testCaseId, testCaseDescription, triggerType, InsightKey, insightName,
				title, containerSupported, keysArray, String.valueOf(numberOfKeys), description, applicableEntity,
				levels, isSubscribed, frequency, thresholdDetails, checkRuleExpression, expectedStatus, failureReason,
				validateKeys, testInfo);
		notificationsSummary(Boolean.valueOf(testStatus), testInfo, testCaseId, insightName, testCaseDescription);
		if (!Boolean.valueOf(testStatus))
			Assert.fail();
	}
	
	@Test(enabled = true, dataProvider = "feeder")
	@Source("\\TestData\\CSVFiles\\Insights\\SpendingByMerchantsConsumerLevel\\GetCobSubSpeMerAllAcct.csv")
	public void testGetCobrandSubscriptionForSpendingbyMercantsAllAccoutns(String testCaseId, String testCaseDescription,
			String triggerType, String InsightKey, String insightName, String title, String containerSupported,
			String validateKeys, String description, String applicableEntity, String levels, String isSubscribed,
			String frequency, String thresholdDetails, String checkRuleExpression, int expectedStatus, String tcEnabled)
					throws ParseException, InterruptedException {
		commontUtils.isTCEnabled(tcEnabled, testCaseId);
		automationTestResults.setInsights("SpendingbyMerchants-AllAccounts");
		automationTestResults.setBuildNo(buildNo);
		boolean testExecutionStatus = false;
		executedInsight = InsightKey;
		logger.info("Testcase executed ---------------------------------------------->" + testCaseId);
		JsonArray keysArray = insightsGenerics.getInsightKeys("SpendingbyMerchantsAllAccounts");
		int numberOfKeys = insightsGenerics.getTotalNumberOfKeys(keysArray, validateKeys);
		storyId = testCaseId.substring(0, 7);
		TestInfo testInfo = new TestInfo();
		testInfo.setTestCaseId(testCaseId);
		testInfo.setTestDescription(testCaseDescription);
		String testStatus = insightsGenerics.getCobrandSubscriptionResponseVerification(cobSubscriptionResponse,
				testExecutionStatus, testSummary, testCaseId, testCaseDescription, triggerType, InsightKey, insightName,
				title, containerSupported, keysArray, String.valueOf(numberOfKeys), description, applicableEntity,
				levels, isSubscribed, frequency, thresholdDetails, checkRuleExpression, expectedStatus, failureReason,
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
}
