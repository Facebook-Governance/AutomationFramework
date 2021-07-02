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

import java.util.ArrayList;
import java.util.Date;
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

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.yodlee.DBHelper;
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
import com.yodlee.yodleeApi.helper.BudgetHelper;
import com.yodlee.yodleeApi.helper.DbHelper;
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

public class TestGetInsightsForSpendingByCategoryConsumerLevel extends Base {
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
	JsonParser jsonParser = new JsonParser();
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
	String jwtCobAuthToken = "";
	FailureReason failureReason;
	static Map<String, String> notificationsTestSummary = new HashMap<String, String>();
	HashMap<String, String> entityIdsMap = new HashMap<String, String>();
	Number userId = null;
	List<TestInfo> testsInfoList;
	String insightExpectedKeys = null;
	InsightsDBUtils insightsDBUtils = new InsightsDBUtils();
	BudgetHelper budgetHelper = new BudgetHelper();
	AutomationTestResults automationTestResults = new AutomationTestResults();
	BoardUtils boardUtils = new BoardUtils();
	DBHelper dbUtility = new DBHelper();
	DbHelper dbHelper = new DbHelper();
	protected Logger logger = LoggerFactory.getLogger(TestGetInsightsForCameinUnderBudget.class);
	InsightsProperties prop = new InsightsProperties();
	String envCobrand = null;
	String envUser = null;
	String cobrandUser = null;
	String emptyString = "";
	Response userInsightsResponse = null;
	CommonEntity commonEntity = new CommonEntity();
	InsightsCommons insightsCommons = new InsightsCommons();
	NotifiedInsightsDataValidation validateInsights = null;

	@BeforeClass(alwaysRun = true)
	public void setUp() throws Exception {
		System.out.println("Initiated Execution");
		insightsGenerics = new InsightsGenerics();
		envCobrand = prop.readPropertiesFile().getProperty("envCobrand");
		envUser = prop.readPropertiesFile().getProperty("envUser");
		cobrandUser = prop.readPropertiesFile().getProperty("cobrandUser");
		insightsDBUtils.deleteAllUserDetails(envCobrand);
		insightsDBUtils.deleteAllJobDTO(envCobrand);
	}

	@BeforeMethod(firstTimeOnly = true)
	public void initializations() throws Exception {
		failureReason = new FailureReason();
		testsInfoList = new ArrayList<TestInfo>();
		automationTestResults.setInsights("SpendingByCategoryConsumerLevel");
		automationTestResults.setBuildNo("223");
		automationTestResults.setCodeCoveragePercent("78");
		automationTestResults.setStoryId("B-68256");
	}

	@Test(enabled = true, dataProvider = "feeder")
	@Source("\\TestData\\CSVFiles\\Insights\\SpendingByCategoryConsumerLevel\\GetSpendCatAllAcctsInsight.csv")
	public void testGetNotificationsForSpendingByCategoryConsumerLevel(String testCaseId, String testCaseDescription,
			String insightName, String triggerCheckEnabled, String inputContainers, String triggerType, long providerId,
			String dagUserName, String dagPassword, String accountGroupName, String entityParameterCount,
			String entityParameterName, String categoryIds, String categoryIdsBudgetAmount, String categoryTypes,
			String categoryTypesBudgetAmount, String numberOfInsights, String editSubscription,
			String isCobrandSubscribed, String isUserSubscribed, String duration, String frequency, String accountName,
			String patchEntityIds, String expectedInsightsForPatchedIds, String expectedNumberOfPatchedInsights,
			String validateKeys, String expectedStatus, String tcEnabled) throws Exception {

		commontUtils.isTCEnabled(tcEnabled, testCaseId);
		logger.info("Testcase Execution Started for ---------------------------------------------->" + testCaseId);
		addAccount(providerId, dagUserName, dagPassword, inputContainers);
		validateInsights = new NotifiedInsightsDataValidation();
		List<Boolean> isUserPatchedAndExpectedInsight = new ArrayList<Boolean>();
		for (String str : expectedInsightsForPatchedIds.split("\\s+"))
			isUserPatchedAndExpectedInsight.add(Boolean.parseBoolean(str));
		HttpMethodParameters httpParams = HttpMethodParameters.builder().build();
		ExpectedResultValues expectedResultValues = new ExpectedResultValues();
		expectedResultValues.setTestNotificationStatus(true);
		HashMap<String, String> accountGroupEntityIdsMap = new HashMap<String, String>();
		JsonArray keysArray = insightsGenerics.getInsightKeys(insightName);
		int numberOfKeys = insightsGenerics.getTotalNumberOfKeys(keysArray, validateKeys);
		TestInfo testInfo = new TestInfo();
		StringBuilder validations = new StringBuilder();
		testInfo.setTestCaseId(testCaseId);
		testInfo.setTestDescription(testCaseDescription);
		String[] accountGroupArray = accountGroupName.split(",");
		String accountGroupId = null;
		ArrayList<Integer> accountGroupList = new ArrayList<Integer>();
		if (!accountGroupName.isEmpty()) {
			for (int ind = 0; ind < accountGroupArray.length; ind++) {
				accountGroupId = insightsHelper.createAccountGroup(accountGroupArray[ind], sessionObj, entityIdsMap,
						accountName);
				accountGroupEntityIdsMap.put(accountGroupArray[ind], accountGroupId);
				accountGroupList.add(Integer.parseInt(accountGroupId));
			}
		}
		entityIdsMap.putAll(accountGroupEntityIdsMap);
		commonEntity.setEntityIdsMap(entityIdsMap);
		HashMap<String, Boolean> entitySubscriptionMap = insightsCommons.getEntityIdsSubscriptionMap(commonEntity,
				patchEntityIds, expectedInsightsForPatchedIds);
		if (Boolean.parseBoolean(editSubscription)) {
			if (!isCobrandSubscribed.isEmpty()) {
				try {
					if (!isCobrandSubscribed.equals("NA")) {
						String patchCobSubscriptionBodyParam = insightsCommons.constructCobrandandUserPatchRequest(
								InsightsConstants.COB_SUBSCRIPTION_CONTEXT, insightName, entityParameterName, "", "",
								"", editSubscription, isCobrandSubscribed, "", entityIdsMap);
						httpParams = insightsHelper.getHttpParams(InsightsConstants.COB_SUB_REQUEST, null, null,
								jwtCobAuthToken, null, null, null, null);
						httpParams.setBodyParams(patchCobSubscriptionBodyParam);
						Response patchCobSubscriptionResponse = null;
						patchCobSubscriptionResponse = insightUtils.patchCobrandSubscriptions(httpParams, null);
						if (patchCobSubscriptionResponse.getStatusCode() != HttpStatus.NO_CONTENT_204) {
							logger.info(InsightsConstants.ERROR_MESSAGE_801);
							expectedResultValues.setTestNotificationStatus(false);
							failureReason.setFailureReason(InsightsConstants.ERROR_MESSAGE_801);
							expectedResultValues.setFailureReason(failureReason);
						}
					}
					isCobrandSubscribed = isCobrandSubscribed.equals("NA") ? "TRUE TRUE TRUE TRUE"
							: isCobrandSubscribed;
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			if (!isUserSubscribed.isEmpty()) {
				String patchUserSubscriptionBodyParam = insightsCommons.constructCobrandandUserPatchRequest(
						InsightsConstants.USER_SUBSCRIPTION_CONTEXT, insightName, entityParameterName, "", "", duration,
						editSubscription, isUserSubscribed, patchEntityIds, entityIdsMap);
				httpParams = insightsHelper.getHttpParams(InsightsConstants.USER_SUB_REQUEST, null, null,
						jwtUserAuthToken, null, null, null, null);
				httpParams.setBodyParams(patchUserSubscriptionBodyParam);
				Response patchUserSubscriptionResponse = insightUtils.patchUserSubscriptions(httpParams, null);
				if (patchUserSubscriptionResponse.getStatusCode() != HttpStatus.NO_CONTENT_204) {
					logger.info(InsightsConstants.ERROR_MESSAGE_802);
					failureReason.setFailureReason(InsightsConstants.ERROR_MESSAGE_802);
					expectedResultValues.setFailureReason(failureReason);
					expectedResultValues.setTestNotificationStatus(false);
				}
			}
		}

		if (!accountGroupName.isEmpty()) {
			String[] catgoryIdsArray = categoryIds.split("\\s+");
			String[] categoryIdsBudgetAmountArray = categoryIdsBudgetAmount.split("\\s+");
			for (int ind1 = 0; ind1 < accountGroupEntityIdsMap.size(); ind1++) {
				String message = budgetHelper.createBudgetForCatTypeDataAndCatData(
						accountGroupEntityIdsMap.get(accountGroupArray[ind1]), catgoryIdsArray[ind1],
						categoryIdsBudgetAmountArray[ind1], categoryTypes, categoryTypesBudgetAmount, sessionObj);
				if (message.equals("createBudgetResponse")) {
					expectedResultValues.setTestNotificationStatus(false);
					failureReason.setFailureReason("Budget NOT Created");
				}
			}
		}
		commonEntity.setExpectedResultValues(expectedResultValues);

		try {
			// invoker Call
			insightsHelper.getHttpParams(InsightsConstants.INVOKER_REQUEST, loginUser, insightName, jwtUserAuthToken,
					envUser, null, null, entityIdsMap);
		} catch (Exception e) {
			e.printStackTrace();
		}
		httpParams = insightsHelper.getHttpParams(InsightsConstants.GET_USER_SUBSCRIPTION_REQUEST, null, insightName,
				jwtUserAuthToken, null, null, null, null);
		Response getUserSubscriptionResponse = insightUtils.getUserSubscription(httpParams, null);
		expectedResultValues = insightsGenerics.getExpectedAttributesToBeValidated(testCaseId, insightName, triggerType,
				"OTHER", entityParameterName, "", Integer.parseInt(numberOfInsights), numberOfKeys,
				insightsCommons.getCobrandSubscriptionMap(entityParameterName, isCobrandSubscribed),
				patchEntityIds.isEmpty() ? false : true, Integer.parseInt(expectedNumberOfPatchedInsights),
				failureReason, keysArray, entityIdsMap, validateKeys, getUserSubscriptionResponse, jwtCobAuthToken);
		int patchedIdsInsightsGeneratedCount = 0;
		ArrayList<com.yodlee.insights.yodleeApi.utils.Entity> entities = null;
		String entityName = null, patchedEntityId = null, thresholdValue = null;
		if (expectedResultValues.isUserPatched() && expectedResultValues.getCobrandSubscribed()) {
			entities = expectedResultValues.getSubscriptions().get(InsightsConstants.USER).getEntities();
			for (int i = 0; i < entities.size() && entities.get(i).getId() == null; i++) {
				entityName = entities.get(i).getEntityType();
				patchedEntityId = entities.get(i).getId() == null ? "0000" : entities.get(i).getId();
				int expNoOfInsights = entities.get(i).isSubscribed() && entitySubscriptionMap.get(patchedEntityId) ? 1
						: 0;
				patchedIdsInsightsGeneratedCount = (entities.get(i).isSubscribed()
						&& entitySubscriptionMap.get(patchedEntityId)) ? patchedIdsInsightsGeneratedCount++
								: patchedIdsInsightsGeneratedCount;
				httpParams = insightsHelper.getHttpParams(InsightsConstants.USER_NOTIFICATION_REQUEST, null,
						insightName, jwtUserAuthToken, null, entityName + "Id", patchedEntityId, null);

				for (int j = 0; j < 11; j++) {
					Thread.sleep(5000);
					userInsightsResponse = insightUtils.getNotificationsV0(httpParams, null);
					if (!(userInsightsResponse.asString().equals("{}"))) {
						if (triggerCheckEnabled.contains("EDIT")) {
							insightsDBUtils.setTriggerCheckFlag(triggerCheckEnabled);
							insightsDBUtils.deleteGeneratedInsights(Long.parseLong(userId.toString()), envCobrand);
							Thread.sleep(15000);
							triggerCheckEnabled = "FALSE";
							j = 0;
						} else {
							insightsDBUtils.deleteGeneratedInsights(Long.parseLong(userId.toString()), envCobrand);
							insightsDBUtils.deleteAllUserDetails(envCobrand);
							break;
						}
					}
					if (j == 9 && !(numberOfInsights.equals("0"))) {
						testInfo.setTestStatus("FAILED");
						testInfo.setReasonForFailure(
								"There is no " + insightName + " insight generated in the given scheduled time");
						notificationsTestSummary.put(testCaseId + "_" + insightName + "_" + testCaseDescription,
								"FAILED");
						notificationsTestSummary.put(testCaseId + "_" + insightName + "_" + "Failed Reason ### ",
								testInfo.getReasonForFailure());
						testsInfoList.add(testInfo);
						Assert.fail("TEST FAILED");
					}
				}

				commonEntity = validateInsights.verifyGeneratedInsightsForPatchedEntites(userInsightsResponse,
						expectedResultValues, commonEntity, expNoOfInsights, thresholdValue);
				if (entities.get(i).isSubscribed() && entitySubscriptionMap.get(patchedEntityId)) {
					JsonObject notificationResponseObject = (JsonObject) jsonParser
							.parse(userInsightsResponse.asString());
					JsonArray notificationArray = notificationResponseObject.getAsJsonArray("notification");
					for (int index = 0; index < expNoOfInsights; index++) {
						insightsDBUtils
								.deleteDocument(((JsonObject) notificationArray.get(index)).get("id").getAsString());
					}
				}
				if (!commonEntity.getExpectedResultValues().getTestNotificationStatus())
					break;
			}
		}

		if (commonEntity.getExpectedResultValues().getTestNotificationStatus()) {
			httpParams = insightsHelper.getHttpParams(InsightsConstants.USER_NOTIFICATION_REQUEST, null, insightName,
					jwtUserAuthToken, null, "", "", null);

			for (int j = 0; j < 11; j++) {
				Thread.sleep(5000);
				userInsightsResponse = insightUtils.getNotificationsV0(httpParams, null);
				if (!(userInsightsResponse.asString().equals("{}"))) {
					if (triggerCheckEnabled.contains("EDIT")) {
						insightsDBUtils.setTriggerCheckFlag(triggerCheckEnabled);
						insightsDBUtils.deleteGeneratedInsights(Long.parseLong(userId.toString()), envCobrand);
						Thread.sleep(5000);
						triggerCheckEnabled = "FALSE";
						j = 0;
					} else {
						insightsDBUtils.deleteUserDetail(Long.parseLong(userId.toString()), envCobrand);
						insightsDBUtils.deleteGeneratedInsights(Long.parseLong(userId.toString()), envCobrand);
						break;
					}
				}
				if (j == 9 && !(numberOfInsights.equals("0"))) {
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
			commonEntity = validateInsights.verifyGeneratedInsights(userInsightsResponse, expectedResultValues,
					commonEntity, Boolean.parseBoolean(isUserSubscribed));
		}
		insightsDBUtils.deleteSpecificUserScheduleNotifocations(Long.parseLong(userId.toString()), envCobrand);

		if (!accountGroupName.isEmpty()) {
			for (int j = 0; j < accountGroupArray.length; j++) {
				insightsGenerics.deleteCreatedAccountGroup(
						Integer.parseInt(commonEntity.getEntityIdsMap().get(accountGroupArray[j]).toString()),
						sessionObj);
				commonEntity.getEntityIdsMap().remove(accountGroupArray[j]);
			}
		}
		insightsDBUtils.deleteGeneratedInsights(Long.parseLong(userId.toString()), envCobrand);
		testInfo.setValidations(validations.toString());

		if (commonEntity.getExpectedResultValues().getTestNotificationStatus()) {
			testInfo.setTestStatus("PASSED");
			notificationsTestSummary.put(testCaseId + "_" + insightName + "_" + testCaseDescription, "PASSED");
			testsInfoList.add(testInfo);
		} else {
			testInfo.setTestStatus("FAILED");
			testInfo.setReasonForFailure(failureReason.getFailureReason());
			notificationsTestSummary.put(testCaseId + "_" + insightName + "_" + testCaseDescription, "FAILED");
			notificationsTestSummary.put(testCaseId + "_" + insightName + "_" + "Failed Reason ### ",
					commonEntity.getExpectedResultValues().getFailureReason().getFailureReason());
			testsInfoList.add(testInfo);
			Assert.fail(commonEntity.getExpectedResultValues().getFailureReason().getFailureReason());
		}

	}

	private void addAccount(long providerId, String dagUserName, String dagUserPassword, String inputContainers) {
		try {
			Response response = null;
			if (!dagUserName.isEmpty()) {
				User userInfo = User.builder().build();
				userInfo.setUsername("SpendingbyCategoryAllAccts" + System.currentTimeMillis());
				loginUser = userInfo.getUsername();
				System.out.println("User ----> " + userInfo.getUsername());
				userInfo.setPassword("TEST@123");
				sessionObj = EnvSession.builder().cobSession(configurationObj.getCobrandSessionObj().getCobSession())
						.path(configurationObj.getCobrandSessionObj().getPath()).build();
				userHelper.getUserSession(userInfo, sessionObj);
				commonEntity.setEnvSession(sessionObj);
				VisibleAllOver.setInstance(commonEntity);

				response = providerAccountUtils.addProviderAccountStrict(providerId, "fieldarray", dagUserName,
						dagUserPassword, sessionObj);
				providerAccountId = response.jsonPath().getLong(JSONPaths.PROVIDER_ACC_ID);
			}
			System.out.println("providerAccountId1::::===" + providerAccountId);
			userId = insightsGenerics.getMemId(sessionObj);
			jwtCobAuthToken = "Bearer ".concat(jwtHelper.getJWTToken(cobrandUser, envCobrand));
			jwtUserAuthToken = "Bearer ".concat(jwtHelper.getJWTToken(loginUser, envUser));
			System.out.println("jwtCobAuthToken = " + jwtCobAuthToken);
			System.out.println("JWTauthToken =  " + jwtUserAuthToken);
			entityIdsMap = insightsGenerics.getItemAccounts(sessionObj, "BANK");
			entityIdsMap.put("InvalidId", "999999");
			entityIdsMap.put("NA", "SKIP");
			commonEntity.setEntityIdsMap(entityIdsMap);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@AfterMethod(lastTimeOnly = true)
	public void prepareAutomationReport() throws Exception {
		TestInfo testInfo = new TestInfo();
		testInfo = insightsHelper.printSummaryOfTests(notificationsTestSummary,
				"GET SpendingByCategoryConsumerLevel Insight", testInfo);
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