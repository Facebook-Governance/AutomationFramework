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
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.yodlee.insights.yodleeApi.utils.AutomationTestResults;
import com.yodlee.insights.yodleeApi.utils.BoardHelper;
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
import com.yodlee.insights.views.pojo.*;
import io.restassured.response.Response;

public class TestGetInsightsForSubscriptionPaid extends Base {
	public static Long providerAccountId = null;
	protected Logger logger = LoggerFactory.getLogger(TestGetInsightsForBillPaid.class);
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
	InsightsGenerics insightsGenerics = new InsightsGenerics();
	InsightsLevelVerifications insightsLevelVerifications = new InsightsLevelVerifications();
	HashMap<String, ArrayList<String>> negativeExpectedValuesMap;
	String loginUser = null;
	String jwtUserAuthToken = null;
	FailureReason failureReason = new FailureReason();
	static Map<String, String> notificationsTestSummary = new HashMap<String, String>();
	HashMap<String, String> entityIdsMap = new HashMap<String, String>();
	HashMap<String, ArrayList> itemAccountsToTxnsMap = new HashMap<String, ArrayList>();
	Number userId = null;
	List<TestInfo> testsInfoList;
	String insightExpectedKeys = null;

	InsightsDBUtils insightsDBUtils = new InsightsDBUtils();
	AutomationTestResults automationTestResults = new AutomationTestResults();
	BoardUtils boardUtils = new BoardUtils();
	BoardHelper boardHelper = new BoardHelper();
	String jwtCobAuthToken = "";
	InsightsProperties prop = new InsightsProperties();
	String envCobrand = null;
	String envUser = null;
	String cobrandUser = null;
	CommonEntity commonEntity = new CommonEntity();

	@BeforeClass(alwaysRun = true)
	public void setUp() throws Exception {
		System.out.println("Initiated execution for SubscriptionPaid");
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

	public void addAccounts(String dagUserName, String dagPassWord) throws Exception {
		System.out.println("initiated execution");
		User userInfo = User.builder().build();
		// update credentials
		HttpMethodParameters httpParams = HttpMethodParameters.builder().build();
		LinkedHashMap<String, Object> queryParam = new LinkedHashMap<>();
		queryParam.put("providerAccountIds", providerAccountId);
		httpParams.setQueryParams(queryParam);
		String updateCredentialsPayload = insightsHelper.constructUpdateCredentialsPayload(dagUserName, dagPassWord);
		httpParams.setBodyParams(updateCredentialsPayload);
		Response response = providerAccountUtils.updateProviderAccount(httpParams, sessionObj);
		Thread.sleep(20000);
		entityIdsMap = insightsGenerics.getItemAccounts(sessionObj, "BANK");
		entityIdsMap.put("InvalidId", "123456");
	}

	@BeforeMethod(firstTimeOnly = true)
	public void initializations() throws Exception {
		failureReason = new FailureReason();
		testsInfoList = new ArrayList<TestInfo>();
		automationTestResults.setInsights("Subscriptionpaid");
		automationTestResults.setBuildNo("351");
		automationTestResults.setCodeCoveragePercent("00");
		automationTestResults.setStoryId("B-XXXXX");
	}

	@Test(enabled = true, dataProvider = "feeder")
	@Source("\\TestData\\CSVFiles\\Insights\\SubscriptionPaid\\GetSubscriptionPaid.csv")
	public void testGetNotificationsForBillPaid(String testCaseId, String testCaseDescription, String insightName,
			String verifyTriggerCheck, String triggerType, String viewsAndAccountInsightsCount,
			String entityParamsCount, String entityParameterName, String thresholdNameValueType,
			String noumberOfInsights, String editSubscription, String isCobrandSubscribed, String isUserSubscribed,
			String patchAccountIds, String validateKeys, String viewsRules, String viewRuleAccountNames,
			String viewRuleTransactionDetails, String viewRuleAmountRangeDetails, String viewRuleCategoryIdDetails,
			String viewRuleMerchantTypeDetails, String dagUserName, String dagPassWord, String expectedStatus,
			String tcEnabled) throws ParseException, InterruptedException, IOException {
		commontUtils.isTCEnabled(tcEnabled, testCaseId);
		logger.info("Testcase executed ---------------------------------------------->" + testCaseId);
		System.out.println("initiated execution");
		User userInfo = User.builder().build();
		userInfo.setUsername("SubPaid" + System.currentTimeMillis());
		loginUser = userInfo.getUsername();
		automationTestResults.setUserName(loginUser);
		System.out.println("User ----> " + userInfo.getUsername());
		userInfo.setPassword("TEST@123");
		sessionObj = EnvSession.builder().cobSession(configurationObj.getCobrandSessionObj().getCobSession())
				.path(configurationObj.getCobrandSessionObj().getPath()).build();
		userHelper.getUserSession(userInfo, sessionObj);
		long providerId = 16441;
		providerAccountId = providerId;
		Response response = providerAccountUtils.addProviderAccountStrict(providerId, "fieldarray", "ravirs.bank78",
				"bank78", sessionObj);

		jwtUserAuthToken = jwtHelper.getJWTToken(userInfo.getUsername(), envUser);
		jwtUserAuthToken = "Bearer ".concat(jwtUserAuthToken);
		System.out.println("JWTauthToken " + jwtUserAuthToken);
		userId = insightsGenerics.getMemId(sessionObj);

		providerAccountId = response.jsonPath().getLong(JSONPaths.PROVIDER_ACC_ID);
		System.out.println("providerAccountId1::::===" + providerAccountId);

		insightsDBUtils.deleteGeneratedInsights(Long.parseLong(userId.toString()), envCobrand);
		HttpMethodParameters httpParams = HttpMethodParameters.builder().build();

		boolean notificationTestStatus = true;
		JsonArray keysArray = insightsGenerics.getInsightKeys(insightName);

		int numberOfKeys = insightsGenerics.getTotalNumberOfKeys(keysArray, validateKeys);

		String dataLevels = insightsGenerics.getDataLevels(entityParameterName, isCobrandSubscribed, isUserSubscribed);
		insightsGenerics.setViewsPropertiesDetails("", "", "", "", "", viewRuleMerchantTypeDetails, commonEntity);

		commonEntity.setUserJwtToken(jwtUserAuthToken);
		if (!viewsRules.isEmpty()) {
			List<String> numberOfviewsToBeCreated = Arrays.asList(viewsRules.split(","));
			for (Object view : numberOfviewsToBeCreated) {
				boardHelper.createViewRules((String) view, commonEntity);
			}
		}
		TestInfo testInfo = new TestInfo();
		StringBuilder validations = new StringBuilder();
		testInfo.setTestCaseId(testCaseId);
		testInfo.setTestDescription(testCaseDescription);
		if (Boolean.parseBoolean(editSubscription)) {
			if (!isCobrandSubscribed.isEmpty()) {
				try {
					String patchCobSubscriptionBodyParam = insightsHelper.constructPatchCobrandSubscriptionRequest(
							insightName, "", "", "", "", "", "", "", "", "", entityParamsCount, entityParameterName,
							"1", "", "", isCobrandSubscribed, "", "", thresholdNameValueType);
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
							"", "", "", "", "", "", "", "", "", entityParamsCount, "", entityParameterName, "1",
							itemAccountIds.toString(), "", isUserSubscribed, "", "", thresholdNameValueType, "");
				} else {
					patchUserSubscriptionBodyParam = insightsHelper.constructPatchUserSubscriptionRequest(insightName,
							"", "", "", "", "", "", "", "", "", entityParamsCount, "", entityParameterName, "1", "", "",
							isUserSubscribed, "", "", thresholdNameValueType, "");
				}
				httpParams = insightsHelper.getHttpParams("userSubscriptionRequest", null, null, jwtUserAuthToken, null,
						null, null, entityIdsMap);
				httpParams.setBodyParams(patchUserSubscriptionBodyParam);
				Response patchUserSubscriptionResponse = insightUtils.patchUserSubscriptions(httpParams, null);
				if (patchUserSubscriptionResponse.getStatusCode() != HttpStatus.NO_CONTENT_204) {
					logger.info("User subscription update FAILED");
					failureReason.setFailureReason("User subscription update FAILED");
					notificationTestStatus = true;
				}
			}
		}

		if (!dagUserName.isEmpty())
			try {
				addAccounts(dagUserName, dagPassWord);
			} catch (Exception e1) {
				e1.printStackTrace();
			}


		Thread.sleep(3000);
		Response userInsightsResponse = null;
		int expectedNumberOfInsights = Integer.parseInt(noumberOfInsights);
		httpParams = insightsHelper.getHttpParams("notificationsRequest", null, insightName, jwtUserAuthToken, null,
				null, null, entityIdsMap);
		userInsightsResponse = insightUtils.getNotificationsV0(httpParams, null);
		Assert.assertEquals(userInsightsResponse.getStatusCode(), HttpStatus.OK_200);
		testInfo.setResponse(userInsightsResponse.asString());
		String threshold = "";
		if (notificationTestStatus) {
			if (expectedNumberOfInsights > 1) {
				if (!patchAccountIds.isEmpty()) {
					String[] entityIdsList = patchAccountIds.split("\\s+");
					for (int i = 0; i < entityIdsList.length; i++) {
						httpParams = insightsHelper.getHttpParams("notificationsRequest", null, insightName,
								jwtUserAuthToken, null, entityParameterName + "Id",
								entityIdsMap.get(entityIdsList[entityIdsList.length - (i + 1)]), entityIdsMap);
						userInsightsResponse = insightUtils.getNotificationsV0(httpParams, null);
						threshold = thresholdNameValueType.split("\\s+")[thresholdNameValueType.split("\\s+").length
								- (i + 1)];
						notificationTestStatus = insightsLevelVerifications.verifyBillsSubscriptionResponse(
								userInsightsResponse, insightName, "EVENT", entityParameterName.toUpperCase(),
								entityParameterName, threshold, Integer.toString(expectedNumberOfInsights),
								numberOfKeys, failureReason, keysArray, entityIdsMap, validateKeys);
					}
				} else {
					notificationTestStatus = insightsLevelVerifications.verifyBillsSubscriptionResponse(
							userInsightsResponse, insightName, "EVENT", entityParameterName.toUpperCase(),
							entityParameterName, threshold, Integer.toString(expectedNumberOfInsights), numberOfKeys,
							failureReason, keysArray, entityIdsMap, validateKeys);
				}

			} else {
				notificationTestStatus = insightsLevelVerifications.verifyBillsSubscriptionResponse(
						userInsightsResponse, insightName, "EVENT", entityParameterName.toUpperCase(),
						entityParameterName, threshold, Integer.toString(expectedNumberOfInsights), numberOfKeys,
						failureReason, keysArray, entityIdsMap, validateKeys);
			}

		}
		insightsDBUtils.setTriggerCheckFlag(verifyTriggerCheck);
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
		testInfo = insightsHelper.printSummaryOfTests(notificationsTestSummary, "GET  SubscriptionPaid Insight",
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
		userUtils.unRegisterUser(sessionObj);
	}

}
