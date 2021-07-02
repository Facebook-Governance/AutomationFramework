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

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.databene.benerator.anno.Source;
import org.eclipse.jetty.http.HttpStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
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
import com.yodlee.yodleeApi.utils.apiUtils.ProviderAccountUtils;
import com.yodlee.yodleeApi.utils.apiUtils.TaskUtils;
import com.yodlee.yodleeApi.utils.apiUtils.UserUtils;
import com.yodlee.yodleeApi.utils.apiUtils.BoardUtils;
import com.yodlee.yodleeApi.utils.apiUtils.InsightUtils;
import com.yodlee.yodleeApi.utils.apiUtils.InsightsUtilV1;
import com.yodlee.yodleeApi.utils.apiUtils.InvokerUtils;
import com.yodlee.yodleeApi.utils.commonUtils.CommonUtils;

import io.restassured.response.Response;

public class TestGetInsightsForMostFrequentPurchases extends Base {

	public static Long providerAccountId = null;
	protected Logger logger = LoggerFactory.getLogger(TestGetInsightsForMostFrequentPurchases.class);
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
	HashMap<String, ArrayList<String>> negativeExpectedValuesMap;
	String loginUser = null;
	String jwtUserAuthToken = null;
	FailureReason failureReason = new FailureReason();
	static Map<String, String> notificationsTestSummary = new HashMap<String, String>();
	HashMap<String, String> entityIdsMap = new HashMap<String, String>();
	Number userId = null;
	List<TestInfo> testsInfoList;
	String insightExpectedKeys = null;
	InsightsDBUtils insightsDBUtils = null;
	AutomationTestResults automationTestResults = new AutomationTestResults();
	BoardUtils boardUtils = new BoardUtils();
	String jwtCobAuthToken = "";
	InsightsProperties prop = new InsightsProperties();
	String envCobrand = null;
	String envUser = null;
	String cobrandUser = null;
	String buildNo = "421", codeCoverage = "80";
	Response userInsightsResponse = null;
	CommonEntity commonEntity = new CommonEntity();
	InsightsCommons insightsCommons = new InsightsCommons();
	BoardHelper boardHelper = new BoardHelper();
	NotifiedInsightsDataValidation validateInsights = null;
	User userInfo = null;
	String entitySchema = null;
	HashMap<String,ArrayList> itemAccountsToTxnsMap = new HashMap<String,ArrayList>();	

	@BeforeClass(alwaysRun = true)
	public void setUp() throws Exception {
		System.out.println("Initiated execution for Count of Transactions By Merchant Insight");
		insightsGenerics = new InsightsGenerics();
		insightsDBUtils = new InsightsDBUtils();
		String cobrand = prop.readPropertiesFile().getProperty("envCobrand");
		cobrandUser = prop.readPropertiesFile().getProperty("cobrandUser");
		envCobrand = (configurationObj.getApiVersion().equals("1.1") ? cobrand
				: (configurationObj.getApiVersion().equals("2") ? cobrand.substring(0, cobrand.length() - 2) : null));
		String user = prop.readPropertiesFile().getProperty("envUser");
		envUser = (configurationObj.getApiVersion().equals("1.1") ? user
				: (configurationObj.getApiVersion().equals("2") ? user.substring(0, user.length() - 2) : null));
		insightsDBUtils.disableCustomerSubscriptions(envCobrand,jwtCobAuthToken,null);
	}

	@BeforeMethod(firstTimeOnly = true)
	public void initializations() throws Exception {
		failureReason = new FailureReason();
		testsInfoList = new ArrayList<TestInfo>();
		automationTestResults.setInsights("Count of Transactions By Merchant");
		automationTestResults.setBuildNo("297");
		automationTestResults.setCodeCoveragePercent("85");
		automationTestResults.setStoryId("B-60433");
	}

	@Test(enabled = true, dataProvider = "feeder")
	@Source("\\TestData\\CSVFiles\\Insights\\V2Feeds\\GetMostFrequentPurchasesInsight.csv")
	public void testGetNotificationsForMostFrequentPurchases(String testCaseId, String testCaseDescription,
			String insightName, String verifyTriggerCheck, String triggerCheckEnabled, String triggerType,
			long providerId, String dagUserName, String dagUserPassword, String inputContainers, String schema,
			String viewsAndAccountInsightsCount, String entityParamsCount, String entityParameterName,
			String thresholdNameValueType, String numberOfInsights, String editSubscription,
			String isCobrandSubscribed, String isUserSubscribed, String patchEntityIds,
			String expectedInsightsForPatchedIds, String expectedNumberOfPatchedInsights, String validateKeys,
			String viewsRules, String viewRuleAccountNames, String viewRuleTransactionDetails,
			String viewRuleAmountRangeDetails, String viewRuleCategoryIdDetails, String frequency, String duration,
			String expectedStatus, String tcEnabled) throws ParseException, InterruptedException, IOException {

		commontUtils.isTCEnabled(tcEnabled, testCaseId);
		StringBuffer entityIds = new StringBuffer();
		validateInsights = new NotifiedInsightsDataValidation();
		List<Boolean> isUserPatchedAndExpectedInsight = new ArrayList<Boolean>();
		for (String str : expectedInsightsForPatchedIds.split("\\s+"))
			isUserPatchedAndExpectedInsight.add(Boolean.parseBoolean(str));
		HttpMethodParameters httpParams = HttpMethodParameters.builder().build();
		ExpectedResultValues expectedResultValues = new ExpectedResultValues();
		expectedResultValues.setTestNotificationStatus(true);
		addAccount(providerId, dagUserName, dagUserPassword, inputContainers);
		logger.info("Testcase Execution Begins----------------------------------------->" + testCaseId);
		JsonArray keysArray = insightsGenerics.getInsightKeys(insightName);
		int numberOfKeys = insightsGenerics.getTotalNumberOfKeys(keysArray, validateKeys);
		HashMap<String, Boolean> entitySubscriptionMap = getEntityIdsSubscriptionMap(commonEntity, patchEntityIds,
				expectedInsightsForPatchedIds);
		TestInfo testInfo = new TestInfo();
		StringBuilder validations = new StringBuilder();
		testInfo.setTestCaseId(testCaseId);
		testInfo.setTestDescription(testCaseDescription);
		if (!schema.isEmpty())
			entitySchema = schema;
		if (duration.isEmpty())
			duration = "THREE_MONTHS";
		if (Boolean.parseBoolean(editSubscription)) {
			if (!isCobrandSubscribed.isEmpty()) {
				try {
					if (!isCobrandSubscribed.equals("NA")) {
						String patchCobSubscriptionBodyParam = insightsCommons.constructSubscriptionPatchRequest(
								InsightsConstants.COB_SUBSCRIPTION_CONTEXT, insightName, entityParameterName, "", frequency,
								duration, editSubscription, isCobrandSubscribed, "", entityIdsMap);
						httpParams = insightsHelper.getHttpParams(InsightsConstants.COB_SUB_REQUEST, null, null,
								jwtCobAuthToken, null, null, null, null);
						httpParams.setBodyParams(patchCobSubscriptionBodyParam);
						Response patchCobSubscriptionResponse = null;
						httpParams.setHttpMethod("PATCH");
						patchCobSubscriptionResponse = insightsUtilsV2.patchCustomerSubscriptions(httpParams, null);
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

			if (entityParameterName.equalsIgnoreCase("view")) {
				String patchUserSubscriptionBodyParam = insightsCommons.constructSubscriptionPatchRequest(
						InsightsConstants.USER_SUBSCRIPTION_CONTEXT, insightName, "account", "", frequency, duration,
						editSubscription, "False", patchEntityIds, entityIdsMap);
				httpParams = insightsHelper.getHttpParams(InsightsConstants.USER_SUB_REQUEST, null, null,
						jwtUserAuthToken, null, null, null, null);
				httpParams.setBodyParams(patchUserSubscriptionBodyParam);
				Response patchUserSubscriptionResponse = null;
				httpParams.setHttpMethod("PATCH");
				patchUserSubscriptionResponse = insightsUtilsV2.patchUserSubscriptions(httpParams, null);
				if (patchUserSubscriptionResponse.getStatusCode() != HttpStatus.NO_CONTENT_204) {
					logger.info(InsightsConstants.ERROR_MESSAGE_802);
					failureReason.setFailureReason(InsightsConstants.ERROR_MESSAGE_802);
					/*expectedResultValues.setFailureReason(failureReason);
					expectedResultValues.setTestNotificationStatus(false);*/
				}
			}

			if (!isUserSubscribed.isEmpty()) {
				String patchUserSubscriptionBodyParam = insightsCommons.constructSubscriptionPatchRequest(
						InsightsConstants.USER_SUBSCRIPTION_CONTEXT, insightName, entityParameterName, "", frequency, duration,
						editSubscription, isUserSubscribed, patchEntityIds, entityIdsMap);
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
					expectedResultValues.setTestNotificationStatus(false);
				}
			}
		}

		if (!patchEntityIds.isEmpty()) {
			String[] entityItems = patchEntityIds.split("\\s+");
			for (int i = 0; i < entityItems.length; i++) {
				String itemAccountId = entityIdsMap.get(entityItems[i]);
				if (i != entityItems.length - 1) {
					entityIds.append(itemAccountId);
					entityIds.append("   ");
				} else {
					entityIds.append(itemAccountId);
				}
			}
		}
		commonEntity.setExpectedResultValues(expectedResultValues);
		insightsDBUtils.setTriggerCheckFlag(triggerCheckEnabled);
		commonEntity.setUserJwtToken(jwtUserAuthToken);	
		insightsGenerics.setViewsPropertiesDetails(viewRuleAccountNames, viewRuleTransactionDetails,viewRuleAmountRangeDetails,viewRuleCategoryIdDetails,"","",commonEntity);

		if (!viewsRules.isEmpty()) {
			List<String> numberOfviewsToBeCreated = Arrays.asList(viewsRules.split(","));
			for (Object view : numberOfviewsToBeCreated) {
				boardHelper.createViewRules((String) view, commonEntity);
			}
		}

		try {
			// invoker Call
			insightsHelper.getHttpParams(InsightsConstants.INVOKER_REQUEST, loginUser, insightName, jwtUserAuthToken,
					envUser, null, null, entityIdsMap);
		} catch (Exception e) {
			e.printStackTrace();
		}
		httpParams = insightsHelper.getHttpParams(InsightsConstants.GET_USER_SUBSCRIPTION_REQUEST, null, insightName,
				jwtUserAuthToken, null, null, null, null);
		httpParams.setHttpMethod("GET");
		Response getUserSubscriptionResponse = insightsUtilsV2.getUserSubscriptions(httpParams, null);
		expectedResultValues = insightsGenerics.getExpectedAttributesToBeValidatedV2(testCaseId, insightName,
				triggerType, "OTHER", entityParameterName, null, Integer.parseInt(numberOfInsights), numberOfKeys,
				getCobrandSubscriptionMap(entityParameterName, isCobrandSubscribed),
				patchEntityIds.isEmpty() ? false : true, Integer.parseInt(expectedNumberOfPatchedInsights),
						failureReason, keysArray, entityIdsMap, validateKeys, getUserSubscriptionResponse, jwtCobAuthToken);
		int patchedIdsInsightsGeneratedCount = 0;
		ArrayList<com.yodlee.insights.yodleeApi.utils.Entity> entities = null;
		String entityName = null, patchedEntityId = null;
		insightsDBUtils.setTriggerCheckFlag(triggerCheckEnabled);
		if (expectedResultValues.isUserPatched() && expectedResultValues.getCobrandSubscribed()) {
			entities = expectedResultValues.getSubscriptions().get(InsightsConstants.USER).getEntities();
			System.out.println("size:" + entities.size());
			for (int i = 0; i < Integer.parseInt(entityParamsCount); i++) {
				entityName = entities.get(i).getEntityType();
				patchedEntityId = entities.get(i).getId() == null ? "0000" : entities.get(i).getId();
				int expNoOfInsights = entities.get(i).isSubscribed() && entitySubscriptionMap.get(patchedEntityId) ? 1
						: 0;
				patchedIdsInsightsGeneratedCount = (entities.get(i).isSubscribed()
						&& entitySubscriptionMap.get(patchedEntityId)) ? patchedIdsInsightsGeneratedCount++
								: patchedIdsInsightsGeneratedCount;
				httpParams = insightsHelper.getHttpParams(InsightsConstants.USER_NOTIFICATION_REQUEST, null,
						insightName, jwtUserAuthToken, null, "entityId", patchedEntityId, null);
				httpParams.setHttpMethod("GET");
				for (int j = 0; j < 10; j++) {
					Thread.sleep(5000);
					userInsightsResponse = insightsUtilsV2.getInsightsFeeds(httpParams, null);
					if (!(userInsightsResponse.asString().equals("{}"))) {
						insightsDBUtils.deleteUserDetail(Long.parseLong(userId.toString()), envCobrand);
						//insightsDBUtils.deleteGeneratedInsights(Long.parseLong(userId.toString()), envCobrand);
						break;
					}
					if (j == 9 && !(expNoOfInsights == 0)) {
						failureReason.setFailureReason("There is no Insight generated in the given scheduled time");
						expectedResultValues.setFailureReason(failureReason);
						expectedResultValues.setTestNotificationStatus(false);
					}
				}
				commonEntity = validateInsights.verifyGeneratedInsightsForPatchedEntitesV2(userInsightsResponse,
						expectedResultValues, commonEntity, expNoOfInsights, "", entitySchema, entitySchema);
				if(entities.get(i).isSubscribed() && entitySubscriptionMap.get(patchedEntityId)  && commonEntity.getExpectedResultValues().getTestNotificationStatus()) {
					JsonObject notificationResponseObject = (JsonObject) jsonParser.parse(userInsightsResponse.asString());
					JsonArray notificationArray = notificationResponseObject.getAsJsonArray("feed");
					/*for(int index=0;index<expNoOfInsights;index++) {
						 insightsDBUtils.setTriggerCheckFlag(triggerCheckEnabled);
						 insightsDBUtils.deleteDocument(((JsonObject)notificationArray.get(index)).get("id").getAsString());	
					}	*/				   					
				}
				if (!commonEntity.getExpectedResultValues().getTestNotificationStatus())
					break;
			}
		}

		if (commonEntity.getExpectedResultValues().getTestNotificationStatus()) {
			httpParams = insightsHelper.getHttpParams(InsightsConstants.USER_NOTIFICATION_REQUEST, null, insightName,
					jwtUserAuthToken, null, "", "", null);
			httpParams.setHttpMethod("GET");

			for (int i = 0; i < 10; i++) {
				Thread.sleep(6000);
				userInsightsResponse = insightsUtilsV2.getInsightsFeeds(httpParams, null);
				if (!(userInsightsResponse.asString().equals("{}"))) {
					insightsDBUtils.deleteUserDetail(Long.parseLong(userId.toString()), envCobrand);
					insightsDBUtils.deleteGeneratedInsights(Long.parseLong(userId.toString()), envCobrand);
					break;
				}
				if (i == 9 && !(numberOfInsights.equals("0"))) {
					failureReason.setFailureReason("There is no Insight generated in the given scheduled time");
					expectedResultValues.setFailureReason(failureReason);
					expectedResultValues.setTestNotificationStatus(false);
				}
			}
			commonEntity = validateInsights.verifyGeneratedInsightsV2(userInsightsResponse, expectedResultValues,
					commonEntity, Boolean.parseBoolean(isUserSubscribed), entitySchema, entitySchema);
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

	public HashMap<String, Boolean> getEntityIdsSubscriptionMap(CommonEntity commonEntity, String patchEntityIds,
			String expectedInsightsForPatchedIds) {
		HashMap<String, Boolean> entityIdsSubscriptionMap = new HashMap<String, Boolean>();
		String[] subscriptions = expectedInsightsForPatchedIds.split("\\s+");
		int i = 0;
		System.out.println("Entity Id: " + commonEntity.getEntityIdsMap());
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

	public void addAccount(long providerId, String dagUserName, String dagUserPassword, String inputContainers) {
		try {
			Response response = null;
			if (!dagUserName.isEmpty()) {
				userInfo = User.builder().build();
				userInfo.setUsername("MostFrePurchase" + System.currentTimeMillis());
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
			System.out.println("ProviderAccountId1::::===" + providerAccountId);
			jwtCobAuthToken = "Bearer ".concat(jwtHelper.getJWTToken("msampath@yodlee.com", envCobrand));
			jwtUserAuthToken = "Bearer ".concat(jwtHelper.getJWTToken(userInfo.getUsername(), envUser));
			userId = insightsGenerics.getMemId(sessionObj);
			System.out.println("jwtCobAuthToken = " + jwtCobAuthToken);
			System.out.println("JWTUserAuthToken for " + userInfo.getUsername() + " =  " + jwtUserAuthToken);
			String[] inputContainerArray = inputContainers.split(",");
			for (String inputContainer : inputContainerArray) {
				entityIdsMap = insightsGenerics.getItemAccounts(sessionObj, inputContainer);
			}
			entityIdsMap.put("InvalidId", "999999");
			commonEntity.setEntityIdsMap(entityIdsMap);
			itemAccountsToTxnsMap = insightsGenerics.getTransactionsMapWithItemAccounts(sessionObj);
			commonEntity.setItemAccountsToTxnsMap(itemAccountsToTxnsMap);			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@AfterClass(alwaysRun = true)
	public void unRegisteredUser() {
		userUtils.unRegisterUser(sessionObj);
	}
}
