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
import com.yodlee.insights.yodleeApi.utils.InsightsLevelVerifications;
import com.yodlee.insights.yodleeApi.utils.InsightsProperties;
import com.yodlee.insights.yodleeApi.utils.TestInfo;
import com.yodlee.yodleeApi.assertions.JsonAssertionUtil;
import com.yodlee.yodleeApi.common.Base;
import com.yodlee.yodleeApi.common.Configuration;
import org.eclipse.jetty.http.HttpStatus;
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
import com.yodlee.yodleeApi.utils.apiUtils.InvokerUtils;
import com.yodlee.yodleeApi.utils.commonUtils.CommonUtils;
import io.restassured.response.Response;

public class TestGetInsightsForPropertyValueChange extends Base {
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
	InvokerUtils invokerUtils = new InvokerUtils();
	InsightsHelper insightsHelper = new InsightsHelper();
	InsightsGenerics insightsGenerics = null;
	InsightsLevelVerifications insightsLevelVerifications = new InsightsLevelVerifications();
	HashMap<String, ArrayList<String>> negativeExpectedValuesMap;
	String loginUser = null;
	String jwtUserAuthToken = null;
	FailureReason failureReason = new FailureReason();
	static Map<String, String> notificationsTestSummary = new HashMap<String, String>();
	HashMap<String, String> entityIdsMap = new HashMap<String, String>();
	Number userId = null;
	List<TestInfo> testsInfoList;
	String insightExpectedKeys = null;
	InsightsDBUtils insightsDBUtils = new InsightsDBUtils();
	AutomationTestResults automationTestResults = new AutomationTestResults();
	BoardUtils boardUtils = new BoardUtils();
	String jwtCobAuthToken = "";
	protected Logger logger = LoggerFactory.getLogger(TestGetInsightsForPropertyValueChange.class);
	InsightsProperties prop = new InsightsProperties();
	String envCobrand = null;
	String envUser = null;
	String NormalizedWeekTime = "1584002130";

	@BeforeClass(alwaysRun = true)
	public void setUp() throws Exception {
		System.out.println("initiated execution");
		envCobrand = prop.readPropertiesFile().getProperty("envCobrand");
		envUser = prop.readPropertiesFile().getProperty("envUser");
		insightsDBUtils.deleteAllUserDetails(envCobrand);
		insightsDBUtils.deleteAllJobDTO(envCobrand);
		insightsGenerics = new InsightsGenerics();
		envCobrand = prop.readPropertiesFile().getProperty("envCobrand");
		envUser = prop.readPropertiesFile().getProperty("envUser");

	}

	@BeforeMethod(firstTimeOnly = true)
	public void initializations() throws Exception {
		failureReason = new FailureReason();
		testsInfoList = new ArrayList<TestInfo>();
		automationTestResults.setInsights("PropertyValueChange");
		automationTestResults.setBuildNo("168");
		automationTestResults.setCodeCoveragePercent("78");
		automationTestResults.setStoryId("B-XXXXX");

	}

	@Test(enabled = true, dataProvider = "feeder")
	@Source("\\TestData\\CSVFiles\\Insights\\PropertyValueChange\\GetPropertyValueChange.csv")
	public void testGetNotificationsForPropertyValueChange(String testCaseId, String testCaseDescription,
			String insightName, String supportedContainers, String accountType, String triggerType, String insightType,
			String entityParameterCount, String entityParameterName, String thresholdNameValueType, String frequency,
			String numberOfInsights, String editSubscription, String isCobrandSubscribed, String isUserSubscribed,
			String patchAccountIds, String firstDataPointAmount, int firstDPdaysInPast, String firstDateOfDataPoint,
			String secondDataPointAmount, int secondDPdaysInPast, String secondDateOfDataPoint, String validateKeys,
			String expectedStatus, String tcEnabled) throws ParseException, InterruptedException, IOException {

		commontUtils.isTCEnabled(tcEnabled, testCaseId);
		logger.info("Testcase executed ---------------------------------------------->" + testCaseId);
		User userInfo = User.builder().build();
		userInfo.setUsername("PVC" + System.currentTimeMillis());
		loginUser = userInfo.getUsername();
		System.out.println("User ----> " + userInfo.getUsername());
		Thread.sleep(1000);
		userInfo.setPassword("Yodlee@123");
		sessionObj = EnvSession.builder().cobSession(configurationObj.getCobrandSessionObj().getCobSession())
				.path(configurationObj.getCobrandSessionObj().getPath()).build();
		userHelper.getUserSession(userInfo, sessionObj);
		jwtCobAuthToken = jwtHelper.getJWTToken("msampath@yodlee.com", envCobrand);
		jwtCobAuthToken = "Bearer ".concat(jwtCobAuthToken);
		System.out.println("jwtCobAuthToken = " + jwtCobAuthToken);
		jwtUserAuthToken = jwtHelper.getJWTToken(userInfo.getUsername(), envUser);
		jwtUserAuthToken = "Bearer ".concat(jwtUserAuthToken);
		userId = insightsGenerics.getMemId(sessionObj);
		System.out.println("JWTauthToken =  " + jwtUserAuthToken);

		insightsGenerics.addRealEstateAccount(sessionObj, accountType.toUpperCase());
		HttpMethodParameters httpParams = HttpMethodParameters.builder().build();
		boolean notificationTestStatus = true;
		JsonArray keysArray = insightsGenerics.getInsightKeys(insightName);
		int numberOfKeys = insightsGenerics.getTotalNumberOfKeys(keysArray, validateKeys);
		TestInfo testInfo = new TestInfo();
		StringBuilder validations = new StringBuilder();
		automationTestResults.setStoryId(testCaseId.substring(0, 7));
		testInfo.setTestCaseId(testCaseId);
		testInfo.setTestDescription(testCaseDescription);
		if (Boolean.parseBoolean(editSubscription)) {
			if (!isCobrandSubscribed.isEmpty()) {
				try {
					String patchCobSubscriptionBodyParam = insightsHelper.constructPatchCobrandSubscriptionRequest(
							insightName, "", "", "", "", "", "", "", "", "", entityParameterCount, entityParameterName,
							"2", "", frequency, isCobrandSubscribed, "", "", thresholdNameValueType);
					httpParams = insightsHelper.getHttpParams("cobrandSubscriptionRequest", null, null, jwtCobAuthToken,
							null, null, null, entityIdsMap);
					httpParams.setBodyParams(patchCobSubscriptionBodyParam);
					Response patchCobSubscriptionResponse = null;
					int attempts = 0;
					do {
						Thread.sleep(2000);
						patchCobSubscriptionResponse = insightUtils.patchCobrandSubscriptions(httpParams, null);
					} while (patchCobSubscriptionResponse.getStatusCode() != HttpStatus.NO_CONTENT_204
							&& ++attempts <= 4);
					if (patchCobSubscriptionResponse.getStatusCode() != HttpStatus.NO_CONTENT_204) {
						logger.info("Cobrand subscription update FAILED");
						notificationTestStatus = false;
						failureReason.setFailureReason("Cobrand subscription update FAILED");
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			if (!isUserSubscribed.isEmpty()) {
				String patchUserSubscriptionBodyParam = null;
				if (!patchAccountIds.isEmpty()) {
					String[] itemAccounts = patchAccountIds.split("\\s+");
					StringBuffer itemAccountIds = new StringBuffer();
					for (int i = 0; i < itemAccounts.length; i++) {
						String itemAccountId = entityIdsMap.get(itemAccounts[i]);
						if (i != itemAccounts.length - 1) {
							itemAccountIds.append(itemAccountId);
							itemAccountIds.append("   ");
						} else {
							itemAccountIds.append(itemAccountId);
						}
					}
					patchUserSubscriptionBodyParam = insightsHelper.constructPatchUserSubscriptionRequest(insightName,
							"", "", "", "", "", "", "", "", "", entityParameterCount, "", entityParameterName, "2", "",
							frequency, isUserSubscribed, "", "", thresholdNameValueType, "");
				} else {
					patchUserSubscriptionBodyParam = insightsHelper.constructPatchUserSubscriptionRequest(insightName,
							"", "", "", "", "", "", "", "", "", entityParameterCount, "", entityParameterName, "2", "",
							frequency, isUserSubscribed, "", "", thresholdNameValueType, "");
				}
				httpParams = insightsHelper.getHttpParams("userSubscriptionRequest", null, null, jwtUserAuthToken, null,
						null, null, entityIdsMap);
				httpParams.setBodyParams(patchUserSubscriptionBodyParam);
				Response patchUserSubscriptionResponse = insightUtils.patchUserSubscriptions(httpParams, null);
				if (patchUserSubscriptionResponse.getStatusCode() != HttpStatus.NO_CONTENT_204) {
					logger.info("User subscription update FAILED");
					failureReason.setFailureReason("User subscription update FAILED");
					notificationTestStatus = false;
				}
			}
		}

		if (frequency.toUpperCase().contains("MONTHLY")) {

			InsightsDBUtils.monthlyReadAndUpdateDataPointTable(loginUser, firstDataPointAmount, firstDPdaysInPast,
					firstDateOfDataPoint);
		} else if (frequency.toUpperCase().contains("WEEKLY")) {
			InsightsDBUtils.weeklyReadAndUpdateDataPointTable(loginUser, firstDataPointAmount, firstDPdaysInPast,
					firstDateOfDataPoint);
		}

		if (frequency.toUpperCase().contains("MONTHLY")) {
			InsightsDBUtils.monthlyReadAndUpdateDataPointTable(loginUser, secondDataPointAmount, secondDPdaysInPast,
					secondDateOfDataPoint);
		} else if (frequency.toUpperCase().contains("WEEKLY")) {
			InsightsDBUtils.weeklyReadAndUpdateDataPointTable(loginUser, secondDataPointAmount, secondDPdaysInPast,
					secondDateOfDataPoint);
		}

		long providerId = 16441;
		providerAccountId = providerId;

		providerAccountUtils.addProviderAccountStrict(providerId, "fieldarray", "BnS2.bank1", "bank1", sessionObj);
		Thread.sleep(10000);

		Response userInsightsResponse = null;
		httpParams = insightsHelper.getHttpParams("notificationsRequest", null, insightName, jwtUserAuthToken, null,
				null, null, null);

		for (int i = 0; i < 15; i++) {
			Thread.sleep(5000);
			userInsightsResponse = insightUtils.getNotificationsV0(httpParams, null);

			if (!(userInsightsResponse.asString().equals("{}"))) {

				break;
			}

			if (i == 9 && !(numberOfInsights.equals("0"))) {
				testInfo.setTestStatus("FAILED");
				testInfo.setReasonForFailure(
						"There is no " + insightName + " insight generated in the given scheduled time");
				notificationsTestSummary.put(testCaseId + "_" + insightName + "_" + testCaseDescription, "FAILED");
				notificationsTestSummary.put(testCaseId + "_" + insightName + "_" + "Failed Reason ### ",
						testInfo.getReasonForFailure());
				testsInfoList.add(testInfo);
				Assert.fail("TEST FAILED");
			}
		}

		/*
		 * Assert.assertEquals(postInvokerResponse.getStatusCode(),
		 * Integer.parseInt(expectedStatus));
		 */ int expectedNumberOfInsights = Integer.parseInt(numberOfInsights);
		httpParams = insightsHelper.getHttpParams("notificationsRequest", null, insightName, jwtUserAuthToken, null,
				null, null, entityIdsMap);
		userInsightsResponse = insightUtils.getNotificationsV0(httpParams, null);
		Assert.assertEquals(userInsightsResponse.getStatusCode(), HttpStatus.OK_200);
		testInfo.setResponse(userInsightsResponse.asString());
		if (notificationTestStatus) {
			if (expectedNumberOfInsights > 1) {
				if (!patchAccountIds.isEmpty()) {
					String[] entityIdsList = patchAccountIds.split("\\s+");
					for (int i = 0; i < expectedNumberOfInsights; i++) {
						httpParams = insightsHelper.getHttpParams("notificationsRequest", null, insightName,
								jwtUserAuthToken, null, "accountId",
								entityIdsMap.get(entityIdsList[entityIdsList.length - (i + 1)]), entityIdsMap);
						userInsightsResponse = insightUtils.getNotificationsV0(httpParams, null);
						String threshold = thresholdNameValueType
								.split("\\s+")[thresholdNameValueType.split("\\s+").length - (i + 1)];
						notificationTestStatus = insightsLevelVerifications.verifyAllInsightsResponse(
								userInsightsResponse, insightName, triggerType, entityParameterName.toUpperCase(),
								entityParameterName, threshold, numberOfInsights, numberOfKeys, failureReason,
								keysArray, entityIdsMap, validateKeys);
					}
				} else {
					notificationTestStatus = insightsLevelVerifications.verifyAllInsightsResponse(userInsightsResponse,
							insightName, triggerType, entityParameterName.toUpperCase(), entityParameterName,
							thresholdNameValueType, numberOfInsights, numberOfKeys, failureReason, keysArray,
							entityIdsMap, validateKeys);
				}

			} else {
				notificationTestStatus = insightsLevelVerifications.verifyAllInsightsResponse(userInsightsResponse,
						insightName, triggerType, entityParameterName.toUpperCase(), entityParameterName,
						thresholdNameValueType, numberOfInsights, numberOfKeys, failureReason, keysArray, entityIdsMap,
						validateKeys);
			}

		}
		insightsDBUtils.deleteGeneratedInsights(Long.parseLong(userId.toString()), envCobrand);
		testInfo.setValidations(validations.toString());
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
			Assert.fail("TEST FAILED");
		}
	}

	@AfterMethod(lastTimeOnly = true)
	public void prepareAutomationReport() throws Exception {
		TestInfo testInfo = new TestInfo();
		insightsDBUtils.deleteAllUserDetails(envCobrand);
		testInfo = insightsHelper.printSummaryOfTests(notificationsTestSummary, "GET  PropertyValueChange Insight",
				testInfo);
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
			/* userUtils.unRegisterUser(sessionObj); */
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
