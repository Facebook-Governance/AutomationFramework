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
import java.util.Calendar;
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
import com.yodlee.yodleeApi.utils.apiUtils.InsightUtils;
import com.yodlee.yodleeApi.utils.apiUtils.InsightsUtilV1;
import com.yodlee.yodleeApi.utils.apiUtils.InvokerUtils;
import com.yodlee.yodleeApi.utils.apiUtils.ProviderAccountUtils;
import com.yodlee.yodleeApi.utils.apiUtils.TaskUtils;
import com.yodlee.yodleeApi.utils.apiUtils.UserUtils;
import com.yodlee.yodleeApi.utils.commonUtils.CommonUtils;

import io.restassured.response.Response;

public class TestGetInsightsForProjectedInsufficientBalance extends Base {
	public static Long providerAccountId = null;

	protected Logger logger = LoggerFactory.getLogger(TestGetInsightsForProjectedInsufficientBalance.class);
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
	InsightsGenerics insightsGenerics;
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
	InsightsDBUtils insightsDBUtils;
	AutomationTestResults automationTestResults = new AutomationTestResults();
	BoardUtils boardUtils = new BoardUtils();
	BoardHelper boardHelper = new BoardHelper();
	String jwtCobAuthToken = "";
	InsightsProperties prop = new InsightsProperties();
	String envCobrand = null;
	String envUser = null;
	Response userInsightsResponse = null;
	String cobrandUser = null;
	CommonEntity commonEntity = new CommonEntity();
	JsonParser jsonParser = new JsonParser();
	InsightsCommons insightsCommons = new InsightsCommons();
	NotifiedInsightsDataValidation validateInsights = null;
	InsightsUtilV1 insightsUtilsV2 = new InsightsUtilV1();
	String viewEntitySchema = "";
	String accountEntitySchema = "ProjectedInsufficientBalanceAccountEntitySchema.json";

	public void setUp() throws Exception {
		System.out.println("initiated execution");
		insightsGenerics = new InsightsGenerics();
		insightsDBUtils = new InsightsDBUtils();
		String cobrand = prop.readPropertiesFile().getProperty("envCobrand");
		cobrandUser = prop.readPropertiesFile().getProperty("cobrandUser");
		envCobrand = (configurationObj.getApiVersion().equals("1.1") ? cobrand
				: (configurationObj.getApiVersion().equals("2") ? cobrand.substring(0, cobrand.length() - 2) : null));
		String user = prop.readPropertiesFile().getProperty("envUser");
		envUser = (configurationObj.getApiVersion().equals("1.1") ? user
				: (configurationObj.getApiVersion().equals("2") ? user.substring(0, user.length() - 2) : null));
		jwtCobAuthToken = jwtHelper.getJWTToken(cobrandUser,envCobrand);	
		jwtCobAuthToken = "Bearer ".concat(jwtCobAuthToken);
		insightsDBUtils.disableCustomerSubscriptions(envCobrand,jwtCobAuthToken,null);
		User userInfo = User.builder().build();
		userInfo.setUsername("IncomeThresholdV2" + System.currentTimeMillis());
		// userInfo.setUsername("ldnuser1899"); //sense user and wellness
		loginUser = userInfo.getUsername();
		System.out.println("User ----> " + userInfo.getUsername());
		userInfo.setPassword("TEST@123");
		sessionObj = EnvSession.builder().cobSession(configurationObj.getCobrandSessionObj().getCobSession())
				.path(configurationObj.getCobrandSessionObj().getPath()).build();
		userHelper.getUserSession(userInfo, sessionObj);
		// userHelper.getUserSession(userInfo.getUsername(),userInfo.getPassword(),sessionObj)
		// ;
		commonEntity.setEnvSession(sessionObj);
		VisibleAllOver.setInstance(commonEntity);
		long providerId = 16441;
		providerAccountId = providerId;
		Response response = providerAccountUtils.addProviderAccountStrict(providerId, "fieldarray",
				"ProjLowBalance.bank7", "bank7", sessionObj);
		providerAccountId = response.jsonPath().getLong(JSONPaths.PROVIDER_ACC_ID);
		System.out.println("providerAccountId1::::===" + providerAccountId);
		jwtUserAuthToken = jwtHelper.getJWTToken(userInfo.getUsername(), envUser);
		jwtUserAuthToken = "Bearer ".concat(jwtUserAuthToken);
		commonEntity.setUserJwtToken(jwtUserAuthToken);
		userId = insightsGenerics.getMemId(sessionObj);
		System.out.println("jwtCobAuthToken = " + jwtCobAuthToken);
		System.out.println("JWTauthToken =  " + jwtUserAuthToken);
		entityIdsMap = insightsGenerics.getItemAccounts(sessionObj, "BANK");
		entityIdsMap.put("InvalidId", "999999");
		entityIdsMap.put("NA", "SKIP");
		commonEntity.setEntityIdsMap(entityIdsMap);
		itemAccountsToTxnsMap = insightsGenerics.getTransactionsMapWithItemAccounts(sessionObj);
		commonEntity.setItemAccountsToTxnsMap(itemAccountsToTxnsMap);
		insightsDBUtils.deleteGeneratedInsights(Long.parseLong(userId.toString()), envCobrand);
		insightsDBUtils.deleteAllUserDetails(envCobrand);
	}

	@BeforeMethod(firstTimeOnly = true)
	public void initializations() throws Exception {
		failureReason = new FailureReason();
		testsInfoList = new ArrayList<TestInfo>();
		automationTestResults.setInsights("ProjectedInsufficientBalanceV2");
		automationTestResults.setBuildNo("300");
		automationTestResults.setCodeCoveragePercent("78");
		automationTestResults.setStoryId("B-63027");
	}

	@Test(enabled = true, dataProvider = "feeder")
	@Source("\\TestData\\CSVFiles\\Insights\\V2Feeds\\GetProjectedInsufficientBalance.csv")
	public void testGetNotificationsForPojectedInsufficientBalance(String testCaseId, String testCaseDescription,
			String insightName, String verifyTriggerCheck, String triggerType, String viewsAndAccountInsightsCount,
			String entityParamsCount, String entityParameterName, String thresholdNameValueType,
			String noumberOfInsights, String editSubscription, String isCobrandSubscribed, String isUserSubscribed,
			String patchEntityIds, String expectedInsightsForPatchedIds, String expectedNumberOfPatchedInsights,
			String validateKeys, String frequency, String expectedStatus, String tcEnabled) throws Exception {
		commontUtils.isTCEnabled(tcEnabled, testCaseId);
		if(testCaseId.equals("AT-148479")) {
			System.out.println("check ............");
		}
		setUp();
		logger.info("Testcase executed ---------------------------------------------->" + testCaseId);
		validateInsights = new NotifiedInsightsDataValidation();
		boolean notificationTestStatus = true, isCobSubscribed = true;
		List<Boolean> isUserPatchedAndExpectedInsight = new ArrayList<Boolean>();
		HttpMethodParameters httpParams = HttpMethodParameters.builder().build();
		ExpectedResultValues expectedResultValues = new ExpectedResultValues();
		String dataLevels = insightsGenerics.getDataLevels(entityParameterName, isCobrandSubscribed, isUserSubscribed);
		JsonArray keysArray = insightsGenerics.getInsightKeys(insightName);
		int numberOfKeys = insightsGenerics.getTotalNumberOfKeys(keysArray, validateKeys);
		if (testCaseId.equals("AT-148481"))
			System.out.println("Check..........");
		insightsDBUtils.deleteGeneratedInsights(Long.parseLong(userId.toString()), envCobrand);
		HashMap<String, Boolean> entitySubscriptionMap = getEntityIdsSubscriptionMap(commonEntity, patchEntityIds,
				expectedInsightsForPatchedIds);
		TestInfo testInfo = new TestInfo();
		StringBuilder validations = new StringBuilder();
		testInfo.setTestCaseId(testCaseId);
		testInfo.setTestDescription(testCaseDescription);
		if (Boolean.parseBoolean(editSubscription)) {
			if (!isCobrandSubscribed.isEmpty()) {
				try {
					if (!isCobrandSubscribed.equals("NA")) {
						String patchCobSubscriptionBodyParam = insightsCommons.constructSubscriptionPatchRequest(
								InsightsConstants.COB_SUBSCRIPTION_CONTEXT, insightName, entityParameterName,
								thresholdNameValueType, frequency, "", editSubscription, isCobrandSubscribed, "",
								entityIdsMap);
						httpParams = insightsHelper.getHttpParams(InsightsConstants.COB_SUB_REQUEST, null, null,
								jwtCobAuthToken, null, null, null, null);
						httpParams.setBodyParams(patchCobSubscriptionBodyParam);
						Response patchCobSubscriptionResponse = null;
						httpParams.setHttpMethod("PATCH");
						patchCobSubscriptionResponse = insightsUtilsV2.patchCustomerSubscriptions(httpParams, null);
						if (patchCobSubscriptionResponse.getStatusCode() != HttpStatus.NO_CONTENT_204) {
							logger.info(InsightsConstants.ERROR_MESSAGE_801);
							notificationTestStatus = false;
							failureReason.setFailureReason(InsightsConstants.ERROR_MESSAGE_801);
							expectedResultValues.setFailureReason(failureReason);
						}
					}
					isCobrandSubscribed = isCobrandSubscribed.equals("NA") ? "TRUE TRUE TRUE TRUE TRUE"
							: isCobrandSubscribed;
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			if (!isUserSubscribed.isEmpty()) {
				String patchUserSubscriptionBodyParam = insightsCommons.constructSubscriptionPatchRequest(
						InsightsConstants.USER_SUBSCRIPTION_CONTEXT, insightName, entityParameterName,
						thresholdNameValueType, frequency, "", editSubscription, isUserSubscribed, patchEntityIds,
						entityIdsMap);
				httpParams = insightsHelper.getHttpParams(InsightsConstants.USER_SUB_REQUEST, null, null,
						jwtUserAuthToken, null, null, null, null);
				httpParams.setBodyParams(patchUserSubscriptionBodyParam);
				Response patchUserSubscriptionResponse = null;
				httpParams.setHttpMethod("PATCH");
				patchUserSubscriptionResponse = insightsUtilsV2.patchUserSubscriptions(httpParams, null);
				if (patchUserSubscriptionResponse.getStatusCode() != HttpStatus.NO_CONTENT_204) {
					logger.info(InsightsConstants.ERROR_MESSAGE_802);
					failureReason.setFailureReason(InsightsConstants.ERROR_MESSAGE_802);
					expectedResultValues.setFailureReason(failureReason);
					notificationTestStatus = false;
				}
			}
		}
		try {
			// invoker Call
			insightsHelper.getHttpParams(InsightsConstants.INVOKER_REQUEST, loginUser, insightName, jwtUserAuthToken,
					envUser, null, null, entityIdsMap);
			waitUntilNextMinute(new Date());
		} catch (Exception e) {
			e.printStackTrace();
		}
		httpParams.setHttpMethod("GET");
		Response getUserSubscriptionResponse = insightsUtilsV2.getUserSubscriptions(httpParams, null);
		expectedResultValues = insightsGenerics.getExpectedAttributesToBeValidatedV2(testCaseId, insightName,
				triggerType, "OTHER", entityParameterName, "", Integer.parseInt(noumberOfInsights), numberOfKeys,
				getCobrandSubscriptionMap(entityParameterName, isCobrandSubscribed),
				patchEntityIds.isEmpty() ? false : true, Integer.parseInt(expectedNumberOfPatchedInsights),
				failureReason, keysArray, entityIdsMap, validateKeys, getUserSubscriptionResponse, jwtCobAuthToken);
		commonEntity.setExpectedResultValues(expectedResultValues);
		int patchedIdsInsightsGeneratedCount = 0;
		ArrayList<com.yodlee.insights.yodleeApi.utils.Entity> entities = null;
		String entityName = null, patchedEntityId = null, thresholdValue = null;

		if (expectedResultValues.isUserPatched() && expectedResultValues.getCobrandSubscribed()) {
			entities = expectedResultValues.getSubscriptions().get(InsightsConstants.USER).getEntities();
			for (int i = 0; i < entities.size() && entities.get(i).getId() != null; i++) {
				entityName = entities.get(i).getEntityType();
				patchedEntityId = entities.get(i).getId() == null ? "0000" : entities.get(i).getId();
				int expNoOfInsights = entities.get(i).isSubscribed() && entitySubscriptionMap.get(patchedEntityId) ? 1
						: 0;
				System.out.println(entities.get(i).isSubscribed());
				System.out.println(entitySubscriptionMap.get(patchedEntityId));
				patchedIdsInsightsGeneratedCount = (entities.get(i).isSubscribed()
						&& entitySubscriptionMap.get(patchedEntityId)) ? patchedIdsInsightsGeneratedCount++
								: patchedIdsInsightsGeneratedCount;
				httpParams = insightsHelper.getHttpParams(InsightsConstants.USER_NOTIFICATION_REQUEST, null,
						insightName, jwtUserAuthToken, null, "entityId", patchedEntityId, null);
				httpParams.setHttpMethod("GET");
				userInsightsResponse = insightsUtilsV2.getInsightsFeeds(httpParams, null);
				commonEntity = validateInsights.verifyGeneratedInsightsForPatchedEntitesV2(userInsightsResponse,
						expectedResultValues, commonEntity, expNoOfInsights, "", accountEntitySchema, viewEntitySchema);
				if (entities.get(i).isSubscribed() && entitySubscriptionMap.get(patchedEntityId)) {
					JsonObject notificationResponseObject = (JsonObject) jsonParser
							.parse(userInsightsResponse.asString());
					JsonArray notificationArray = notificationResponseObject.getAsJsonArray("feed");
					for (int index = 0; index < expNoOfInsights; index++) {
						try {
							insightsDBUtils.deleteDocument(
									((JsonObject) notificationArray.get(index)).get("id").getAsString());
						} catch (Exception e) {
						}
					}
				}
				if (!commonEntity.getExpectedResultValues().getTestNotificationStatus())
					break;
			}
		}
		if (commonEntity.getExpectedResultValues().getTestNotificationStatus()) {
			httpParams = insightsHelper.getHttpParams(InsightsConstants.USER_NOTIFICATION_REQUEST, null, insightName,
					jwtUserAuthToken, null, "", "", null);
			httpParams.setHttpMethod("GET");
			userInsightsResponse = insightsUtilsV2.getInsightsFeeds(httpParams, null);
			commonEntity = validateInsights.verifyGeneratedInsightsV2(userInsightsResponse, expectedResultValues,
					commonEntity, Boolean.parseBoolean(isUserSubscribed), accountEntitySchema, viewEntitySchema);
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
					failureReason.getFailureReason());
			testsInfoList.add(testInfo);
			Assert.fail(
					"Test FAILED -> " + commonEntity.getExpectedResultValues().getFailureReason().getFailureReason());
		}
	}

	

	public HashMap<String, Boolean> getEntityIdsSubscriptionMap(CommonEntity commonEntity, String patchEntityIds,
			String expectedInsightsForPatchedIds) {
		HashMap<String, Boolean> entityIdsSubscriptionMap = new HashMap<String, Boolean>();
		String[] subscriptions = expectedInsightsForPatchedIds.split("\\s+");
		int i = 0;
		for (String entityId : patchEntityIds.split("\\s+")) {
			if (!patchEntityIds.isEmpty() && subscriptions.length > 0
					&& !commonEntity.getEntityIdsMap().get(entityId).isEmpty()) {
				entityIdsSubscriptionMap.put(commonEntity.getEntityIdsMap().get(entityId),
						Boolean.parseBoolean(subscriptions[i++]));
			}
		}
		return entityIdsSubscriptionMap;
	}

	public HashMap<String, Boolean> getCobrandSubscriptionMap(String entityParameters, String cobrandSubscriptions) {
		HashMap<String, Boolean> cobrandSubscriptionMap = new HashMap<String, Boolean>();
		String[] entityParams = entityParameters.split("\\s+");
		String[] cobSubscriptions = cobrandSubscriptions.split("\\s+");
		int i = 0;
		for (String supportingEntity : entityParams) {
			cobrandSubscriptionMap.put(supportingEntity, Boolean.parseBoolean(cobSubscriptions[i++]));
		}
		// NA Case
		if (cobrandSubscriptionMap.size() == 1 && cobSubscriptions.length == 2) {
			if (cobrandSubscriptionMap.containsKey("view"))
				cobrandSubscriptionMap.put("account", true);
			if (cobrandSubscriptionMap.containsKey("account"))
				cobrandSubscriptionMap.put("view", true);
		}
		return cobrandSubscriptionMap;
	}

	@AfterClass(alwaysRun = true)
	public void unRegisteredUser() {
		userUtils.unRegisterUser(sessionObj);
	}

	private void waitUntilNextMinute(Date date) {
		try {
			Calendar c = Calendar.getInstance();
			c.setTime(date);
			c.add(Calendar.MINUTE, 1);
			Date nextMinute = c.getTime();
			while (new Date().before(nextMinute)) {
				Thread.sleep(1000);
			}
			Thread.sleep(5000);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
