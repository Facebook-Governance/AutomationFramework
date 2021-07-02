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
package com.yodlee.insights.yodleeApi.utils.v2;

import static org.testng.Assert.assertFalse;
import io.restassured.response.Response;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Random;
import java.util.regex.Pattern;

import org.json.JSONObject;
import org.json.simple.JSONArray;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.yodlee.DBHelper;
import com.yodlee.insights.views.pojo.InvokerPayload;
import com.yodlee.insights.views.pojo.Payload;
import com.yodlee.insights.views.pojo.RuntimeParams;
import com.yodlee.yodleeApi.common.Configuration;
import com.yodlee.yodleeApi.helper.BoardHelper;
import com.yodlee.yodleeApi.helper.DbHelper;
import com.yodlee.yodleeApi.pojo.EnvSession;
import com.yodlee.yodleeApi.pojo.HttpMethodParameters;
import com.yodlee.yodleeApi.utils.apiUtils.AccountUtils;
import com.yodlee.yodleeApi.utils.apiUtils.BoardUtils;
import com.yodlee.yodleeApi.utils.apiUtils.GroupUtils;
import com.yodlee.yodleeApi.utils.apiUtils.InvokerUtils;
import com.yodlee.insights.yodleeApi.utils.InsightsProperties;
import com.yodlee.insights.yodleeApi.utils.InsightsDBUtils;

public class InsightsHelper {

	protected Logger logger = LoggerFactory.getLogger(InsightsHelper.class);
	JsonParser jsonParser = new JsonParser();
	String[] validateAttributes;
	Response getCobSubResponse, getUserSubResponse;
	ArrayList<String> levelsList;
	String patchCobEditInsightNameTcs = "AT-7010";
	static Map<String, String> editedDetailsMap = null;
	ObjectMapper mapper = new ObjectMapper();
	DBHelper dbHelper = new DBHelper(); 
	DbHelper dbUtil = new DbHelper();
	boolean notificationInsightsStatus = true;
	AccountUtils accountUtils = new AccountUtils();
	GroupUtils groupUtils = new GroupUtils();
	InsightsProperties prop = new InsightsProperties();
	String DEVELOPER_URL = null;
	String INVOKER_URL = null;
	String TASK_BASE_URI=null;
	InvokerUtils invokerUtils = new InvokerUtils();
	BoardUtils boardUtils = new BoardUtils();
	BoardHelper boardHelper = new BoardHelper();
	private Pattern pattern = Pattern.compile("\\d+");

	// GET - Cobrand Subscription - Insights Verification - keep
	public boolean verifyCobrandSubscriptionResponse(Response cobSubscriptionResponse, String testCaseId,
			String testCaseDescription, String triggerType, String insightKey, String insightName, String title,
			String containerSupported, JsonArray keys, String totalNumberOfKeys, String description,
			String applicableEntity, String levels, String isSubscribed, String frequency, String thresholdDetails,
			String checkRuleExpression, FailureReason failureReason, String validateKeys, TestInfo testInfo) {
		levelsList = new ArrayList<String>();
		levelsList.add("account");
		levelsList.add("view");
		levelsList.add("accountGroup");
		levelsList.add("goal");
		getCobSubResponse = cobSubscriptionResponse;
		boolean insightExists = false, testExecutionStatus = true;
		JsonObject responseObj = (JsonObject) jsonParser.parse(getCobSubResponse.asString());
		JsonArray cobrandSubscriptionsArray = responseObj.getAsJsonArray("cobrandSubscription");
		for (int i = 0; i < cobrandSubscriptionsArray.size(); i++) {
			JsonObject insightObj = (JsonObject) cobrandSubscriptionsArray.get(i);
			if (insightObj.get("name").getAsString().equals(insightKey)) {// Insight name Verification
				// testInfo.setResponse(insightObj.toString());
				testExecutionStatus = verifyInsightDetailsOfCobrandSubscription(insightObj, triggerType, insightKey,
						insightName, title, containerSupported, keys, totalNumberOfKeys, description, applicableEntity,
						levels, isSubscribed, frequency, thresholdDetails, checkRuleExpression, failureReason,
						validateKeys, testInfo);
				insightExists = true;
				break;
			}
		}
		if (!insightExists) {
			Assert.fail("Test Failed for " + insightKey
					+ " Insight name is Doesn't exists in the Get cobrand subscription API Response");
		}
		if (!testExecutionStatus) {
			logger.info("TEST FAILED testCaseId =" + testCaseId + "and testCase Description = " + testCaseDescription);
		}
		levelsList.clear();
		return testExecutionStatus;
	}

	// GET - User Subscription - Insights Verification - keep

	public boolean verifyUserSubscriptionResponse(Response cobSubscriptionResponse, Response userSubscriptionResponse,
			String testCaseId, String testCaseDescription, String triggerType, String insightKey, String insightName,
			String title, String containerSupported, JsonArray keys, String totalNumberOfKeys, String description,
			String applicableEntity, String levels, String isSubscribed, String frequency, String thresholdDetails,
			String checkRuleExpression, String configMetaAndentityParameter, FailureReason failureReason,
			String validateKeys, TestInfo testInfo) {
		levelsList = new ArrayList<String>();
		levelsList.add("account");
		levelsList.add("view");
		levelsList.add("accountGroup");
		levelsList.add("goal");
		getCobSubResponse = cobSubscriptionResponse;
		boolean insightExists = false, insightPresentInUser = false, insightPresentInCobrand = false,
				testExecutionStatus = true;
		JsonObject cobResponseObj = (JsonObject) jsonParser.parse(getCobSubResponse.asString());
		JsonArray cobrandSubscriptionsArray = cobResponseObj.getAsJsonArray("cobrandSubscription");
		JsonObject userResponseObj = (JsonObject) jsonParser.parse(userSubscriptionResponse.asString());
		JsonArray userSubscriptionsArray = userResponseObj.getAsJsonArray("userSubscription");
		JsonObject insightObj, insightObjInCob = null, insightObjInUser = null;
		for (int i = 0; i < cobrandSubscriptionsArray.size(); i++) {
			insightObjInCob = (JsonObject) cobrandSubscriptionsArray.get(i);
			if (insightObjInCob.get("name").getAsString().equals(insightKey)) {// Insight name Verification
				insightPresentInCobrand = true;
				break;
			}
		}
		for (int i = 0; i < userSubscriptionsArray.size(); i++) {
			insightObjInUser = (JsonObject) userSubscriptionsArray.get(i);
			if (insightObjInUser.get("name").getAsString().equals(insightKey)) {// Insight name Verification
				insightPresentInUser = true;
				break;
			}
		}
		if (insightPresentInCobrand && insightPresentInUser) {
			testExecutionStatus = verifyInsightDetailsOfUserSubscription(insightObjInCob, insightObjInUser, triggerType,
					insightKey, insightName, title, containerSupported, keys, totalNumberOfKeys, description,
					applicableEntity, levels, isSubscribed, frequency, thresholdDetails, checkRuleExpression,
					configMetaAndentityParameter, failureReason, validateKeys, testInfo);
		} else {
			if (!insightPresentInCobrand) {
				Assert.fail("Test Failed for " + insightKey
						+ " Insight name is Doesn't exists in the Get cobrand subscription API Response");
			}
			if (!insightPresentInUser) {
				Assert.fail("Test Failed for " + insightKey
						+ " Insight name is Doesn't exists in the Get User subscription API Response");
			}
		}
		if (!testExecutionStatus) {
			logger.info("TEST FAILED testCaseId =" + testCaseId + "and testCase Description = " + testCaseDescription);
		}
		levelsList.clear();
		return testExecutionStatus;
	}

	public void verifyCobrandSubscriptionResponsePostPatch(Response cobSubscriptionResponse,
			String[] verifyAttributes) {
		levelsList = new ArrayList<String>();
		levelsList.add("account");
		levelsList.add("board");
		levelsList.add("accountGroup");
		levelsList.add("goal");
		validateAttributes = verifyAttributes;
		getCobSubResponse = cobSubscriptionResponse;
		boolean insightExists = false;
		JsonObject responseObj = (JsonObject) jsonParser.parse(getCobSubResponse.asString());
		JsonArray cobrandSubscriptionsArray = responseObj.getAsJsonArray("cobrandSubscriptions");
		for (int i = 0; i < cobrandSubscriptionsArray.size(); i++) {
			JsonObject insightObj = (JsonObject) cobrandSubscriptionsArray.get(i);
			if (insightObj.get("name").getAsString().equals(validateAttributes[0])) {// Insight name Verification
				// verifyInsightDetailsOfCobrandSubscription(validateAttributes, insightObj);
				insightExists = true;
				break;
			}
		}
		if (!insightExists) {
			Assert.fail("Test Failed for " + validateAttributes[0]
					+ " Insight name is Doesn't exists in the Get cobrand subscription API Response");
		}
		levelsList.clear();
	}

	// Verify Notification API response

	public boolean verifyLowBalanceWarningNotificationsResponse(Response userInsightsResponse, String insightName,
			String triggerType, String dataLevels, String entityParameterName, String thresholdNameValueType,
			String expectedNumberOfInsights, int numberOfKeys, FailureReason failureReason, String patchAccountIds) {

		if (Integer.parseInt(expectedNumberOfInsights) == 0) {
			String insightResponse = userInsightsResponse.asString();
			boolean insightsNotAvailable = (insightResponse.equals("{}")) ? true : false;
			if (!insightsNotAvailable) {
				failNotificationTest(insightName,
						"Expected  ZERO insights triggered but found more than ZERO Insights ", failureReason);
			}
		} else {
			JsonObject notificationResponseObject = (JsonObject) jsonParser.parse(userInsightsResponse.asString());
			JsonArray notificationArray = notificationResponseObject.getAsJsonArray("notification");
			int actualInsights = (notificationArray.get(0).getAsJsonObject().getAsJsonObject("data")
					.getAsJsonObject("model").getAsJsonArray("keys").size() / numberOfKeys);
			if (Integer.parseInt(expectedNumberOfInsights) != actualInsights) {
				failNotificationTest(
						insightName, "Expected insights are not generated , Expected = "
								+ Integer.parseInt(expectedNumberOfInsights) + "  Actual = " + actualInsights,
						failureReason);
			} else {
				for (int i = 0; i < 1; i++) {
					JsonObject insightObj = (JsonObject) notificationArray.get(i);
					if (!insightObj.get("name").getAsString().equals(insightName))
						failNotificationTest(insightName, "insightName", failureReason);
					if (!insightObj.get("triggerType").getAsString().equals(triggerType))
						failNotificationTest(insightName, "triggerType", failureReason);
					verifyDateTimeStamp(insightName, insightObj.get("asOfDate").getAsString());
					JsonObject dataEntity = insightObj.getAsJsonObject("data");
					JsonObject levelObject = dataEntity.getAsJsonObject("level");
					if (!levelObject.get("type").getAsString().equals(dataLevels))
						failNotificationTest(insightName, "leveltype", failureReason);
					JsonObject thresholdObject = levelObject.getAsJsonObject("threshold");
					try {
						thresholdObject.get("name");
						thresholdObject.get("value");
						thresholdObject.get("type");
					} catch (Exception e) {
						failNotificationTest(insightName, "threshold Object keys are missing", failureReason);
					}
					if (!thresholdObject.get("name").getAsString().equals("BALANCE")
							&& !thresholdObject.get("type").getAsString().equals("AMOUNT")) {
						failNotificationTest(insightName, "name OR type in the threshold objects are incorrect",
								failureReason);
					}

					String[] thresholdDetails = thresholdNameValueType.split(",");
					int thresholdValue = Integer.parseInt(thresholdDetails[1]);

					int actualThresholdAmount = Integer.parseInt(thresholdObject.get("value").getAsString());
					if (actualThresholdAmount != thresholdValue) {
						failNotificationTest(insightName,
								"ThresholdAmount is not matching in notifications response expected threshold is = "
										+ thresholdValue + " but found actual = " + actualThresholdAmount,
								failureReason);
					}

					JsonObject modelObject = dataEntity.getAsJsonObject("model");
					JsonArray keysArray = modelObject.getAsJsonArray("keys");
					int count = 0;
					for (int j = 0; j < Integer.parseInt(expectedNumberOfInsights); j++) {
						try {
							boolean checkKeys = keysArray.get(count).toString()
									.substring(1, keysArray.get(count).toString().length() - 1)
									.equals("account[" + j + "].providerName");
							count++;
							if (checkKeys) {
								checkKeys = keysArray.get(count).toString()
										.substring(1, keysArray.get(count).toString().length() - 1)
										.equals("account[" + j + "].id");
								count++;
							}
							if (checkKeys) {
								checkKeys = keysArray.get(count).toString()
										.substring(1, keysArray.get(count).toString().length() - 1)
										.equals("account[" + j + "].accountType");
								count++;
							}
							if (checkKeys) {
								checkKeys = keysArray.get(count).toString()
										.substring(1, keysArray.get(count).toString().length() - 1)
										.equals("account[" + j + "].accountNumber");
								count++;
							}
							if (checkKeys) {
								checkKeys = keysArray.get(count).toString()
										.substring(1, keysArray.get(count).toString().length() - 1)
										.equals("account[" + j + "].accountName");
								count++;
							}
							if (checkKeys) {
								checkKeys = keysArray.get(count).toString()
										.substring(1, keysArray.get(count).toString().length() - 1)
										.equals("account[" + j + "].availableBalance.amount");
								count++;
							}
							if (checkKeys) {
								checkKeys = keysArray.get(count).toString()
										.substring(1, keysArray.get(count).toString().length() - 1)
										.equals("account[" + j + "].availableBalance.currency");
								count++;
							}
							if (!checkKeys) {
								failNotificationTest(insightName,
										"one OR more keys in the notifications response is/are not present / incorrect",
										failureReason);
							}
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
					JsonArray valuesArray = modelObject.getAsJsonArray("values");
					int availableBalanceInitialPos = 5, totalKeysValues = 7;
					if (patchAccountIds.isEmpty()) {
						for (int j = 0; j < Integer.parseInt(expectedNumberOfInsights); j++) {
							try {
								// String actualInsightValue = valuesArray.get((j + 1) *
								// availableBalanceInitialPos).toString().substring(1,valuesArray.get((j + 1) *
								// availableBalanceInitialPos).toString().length() - 3);
								String actualInsightValue = valuesArray.get(availableBalanceInitialPos).toString()
										.substring(1,
												valuesArray.get(availableBalanceInitialPos).toString().length() - 3);
								availableBalanceInitialPos += totalKeysValues;
								int actualValInInsight = Integer.parseInt(actualInsightValue);
								if (actualValInInsight > thresholdValue) {
									failNotificationTest(insightName, "Expected value must be less than "
											+ thresholdValue + "But found actual value " + actualValInInsight,
											failureReason);
								}
							} catch (Exception e) {
								failNotificationTest(insightName,
										"Order of Values presenting in the values[] array of notifications response are Incorrect/Inconsistent between Insights",
										failureReason);
							}
						}
					}
				}
			}
		}
		return notificationInsightsStatus;
	}

	public void verifyDateTimeStamp(String insightName, String notificationDate) {

		Date currentDate = new Date();
		Date insightGeneratedDate = null;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		try {
			insightGeneratedDate = sdf.parse(notificationDate);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		if (!sdf.format(insightGeneratedDate).equals(sdf.format(currentDate))) {
			logger.info("Insight generated date is not matching with current Date");
			Assert.fail("Test FAILED ### " + insightName);
		}

	}

	// GET cobrand subscription - Insight level verification
	public boolean verifyInsightDetailsOfCobrandSubscription(JsonObject insightObj, String triggerType,
			String insightKey, String insightName, String title, String containerSupported, JsonArray keys,
			String totNumberOfKeys, String description, String applicableEntity, String levels, String isSubscribed,
			String frequency, String thresholdDetails, String checkRuleExpressionPresent, FailureReason failureReason,
			String validateKeys, TestInfo testInfo) {
		boolean testExecutionStatus = true;
		// JsonArray containers = insightObj.getAsJsonArray("container");
		if (!triggerType.isEmpty()) {
			testInfo.setResponse(insightObj.get("triggerType").getAsString());
			testExecutionStatus = insightObj.get("triggerType").getAsString().equals(triggerType) ? true
					: failTest(insightKey, triggerType, insightObj.get("triggerType").getAsString(), failureReason);
		}
		if (!insightName.isEmpty()) {
			testInfo.setResponse(insightObj.get("name").getAsString());
			testExecutionStatus = insightObj.get("name").getAsString().equals(insightName) ? true
					: failTest(insightKey, insightName, insightObj.get("name").getAsString(), failureReason);
		}
		if (!title.isEmpty()) {
			testInfo.setResponse(insightObj.get("title").getAsString());
			testExecutionStatus = insightObj.get("title").getAsString().equals(title) ? true
					: failTest(insightKey, title, insightObj.get("title").getAsString(), failureReason);
		}

		if (!checkRuleExpressionPresent.isEmpty()) {
			try {
				insightObj.get("ruleExpression").getAsString();
				testExecutionStatus = failTest(insightKey, "Rule Expression Must not be present is subscriptions",
						insightObj.get("ruleExpression").getAsString(), failureReason);
			} catch (Exception e) {
				testInfo.setResponse("ruleExpression is Hidden");
				e.printStackTrace();
			}
		}
		if (!containerSupported.isEmpty()) {
			String[] expectedContainers = containerSupported.split(",");
			JsonArray containerArray = insightObj.getAsJsonArray("container");
			testInfo.setResponse(containerArray.toString());
			for (int i = 0; i < expectedContainers.length; i++) {
				if (expectedContainers.length == containerArray.size()) {
					testExecutionStatus = containerSupported.contains(containerArray.get(i).getAsString()) ? true
							: failTest(insightKey, expectedContainers[i], containerArray.get(i).getAsString(),
									failureReason);
				}
			}
		}

		JsonArray keysArray = insightObj.getAsJsonArray("entityConfiguration");
		if (keys.size() > 0 && !validateKeys.isEmpty()) {
			StringBuilder expectedKeys = new StringBuilder();
			for (int i = 0; i < keys.size(); i++) {
				JsonObject entityKeyObject = (JsonObject) keys.get(i);
				if (entityKeyObject.keySet().toString().contains(validateKeys)) {
					String[] keyArray = entityKeyObject.get(entityKeyObject.keySet().toString().substring(1,
							entityKeyObject.keySet().toString().length() - 1)).getAsString().split(",");
					for (int keys1 = 1; keys1 < keyArray.length; keys1++) {
						expectedKeys.append(keyArray[0] + "." + keyArray[keys1]);
					}
				}
			}

			if (validateKeys.equalsIgnoreCase("Account") && (insightName.equalsIgnoreCase("FinancialFees")
					|| insightName.equalsIgnoreCase("SpendingbyCategory"))) {
				for (int i = 0; i < keys.size(); i++) {
					JsonObject entityKeyObject = (JsonObject) keys.get(i);
					if (entityKeyObject.keySet().toString().contains("PB")) {
						String[] keyArray = entityKeyObject.get(entityKeyObject.keySet().toString().substring(1,
								entityKeyObject.keySet().toString().length() - 1)).getAsString().split(",");
						for (int keys1 = 1; keys1 < keyArray.length; keys1++) {
							expectedKeys.append(keyArray[0] + "." + keyArray[keys1]);
						}
						break;
					}
				}
			}

			StringBuilder actualKeys = new StringBuilder();
			JsonObject entityObject = null;
			for (int m = 0; m < keysArray.size(); m++) {
				entityObject = (JsonObject) keysArray.get(m);
				if (entityObject.get("entityName").getAsString().equalsIgnoreCase(validateKeys))
					break;
			}
			JsonArray actaulskeysArray = entityObject.getAsJsonArray("keys");
			testInfo.setResponse(actaulskeysArray.toString());
			for (int index1 = 0; index1 < actaulskeysArray.size(); index1++) {
				actualKeys.append(actaulskeysArray.get(index1).getAsString());
			}

			if (actaulskeysArray.size() != Integer.parseInt(totNumberOfKeys)) {
				failTest(insightKey, totNumberOfKeys, Integer.toString(actaulskeysArray.size()), failureReason);
			}
			// expected and actual keys check
			if (!expectedKeys.toString().equals(actualKeys.toString())) {
				testExecutionStatus = failTest(insightKey, expectedKeys.toString(), actualKeys.toString(),
						failureReason);
				System.err.println("Both(Keys) Are not same");
			}
		}

		if (!description.isEmpty()) {
			testInfo.setResponse(insightObj.get("description").getAsString());
			testExecutionStatus = insightObj.get("description").getAsString().equals(description) ? true
					: failTest(insightKey, description, insightObj.get("description").getAsString(), failureReason);
		}
		if (!applicableEntity.isEmpty() && levels.isEmpty()) {
			String[] expectedApplicableEntities = applicableEntity.split(",");
			JsonArray applicableEntityArray = insightObj.getAsJsonArray("applicableEntity");
			testInfo.setResponse(applicableEntityArray.toString());
			for (int i = 0; i < expectedApplicableEntities.length; i++) {
				if (expectedApplicableEntities.length == applicableEntityArray.size()) {
					testExecutionStatus = applicableEntityArray.get(i).getAsString()
							.equals(expectedApplicableEntities[i]) ? true
									: failTest(insightKey, expectedApplicableEntities[i],
											applicableEntityArray.get(i).getAsString(), failureReason);
				}
			}
		}

		if (!levels.isEmpty()) {
			String[] allLevels = levels.split(",");
			JsonObject entityParameterObject = insightObj.getAsJsonObject("entityParameter");
			testInfo.setResponse(entityParameterObject.toString());
			for (int i = 0; i < allLevels.length; i++) {
				try {
					entityParameterObject.getAsJsonArray(allLevels[i]);
				} catch (Exception e) {
					testExecutionStatus = failTest(insightKey, allLevels[i], allLevels[i] + " Level is Not Found",
							failureReason);
				}
			}

		}
		if (!frequency.isEmpty() && !levels.isEmpty()) {
			JsonObject entityParameterObject = insightObj.getAsJsonObject("entityParameter");
			String[] expectedLevels = levels.split(",");
			for (int i = 0; i < expectedLevels.length; i++) {
				JsonArray levelsArr = entityParameterObject.getAsJsonArray(expectedLevels[i]);
				// check if isSubscription = false
				JsonObject levelObject = (JsonObject) levelsArr.get(0);
				testInfo.setResponse(levelObject.get("frequency").getAsString());
				testExecutionStatus = levelObject.get("frequency").getAsString().equals(frequency) ? true
						: failTest(insightKey, frequency, levelObject.get("frequency").getAsString(), failureReason);
			}
		}
		if (!isSubscribed.isEmpty() && !levels.isEmpty()) {
			JsonObject entityParameterObject = insightObj.getAsJsonObject("entityParameter");
			testInfo.setResponse(insightObj.toString());
			String[] expectedLevels = levels.split(",");
			for (int i = 0; i < expectedLevels.length; i++) {
				JsonArray levelsArr = entityParameterObject.getAsJsonArray(expectedLevels[i]);
				// check if isSubscription = false
				JsonObject levelObject = (JsonObject) levelsArr.get(0);
				boolean actualSubscriptionValue = levelObject.get("isSubscribed").getAsBoolean();
				if (actualSubscriptionValue != Boolean.parseBoolean(isSubscribed)) {
					testExecutionStatus = actualSubscriptionValue == Boolean.parseBoolean(isSubscribed) ? true
							: failTest(insightKey, isSubscribed, levelObject.get("isSubscribed").getAsString(),
									failureReason);
				}
			}
		}
		if (!thresholdDetails.isEmpty() && !levels.isEmpty()) {
			JsonObject entityParameterObject = insightObj.getAsJsonObject("entityParameter");
			String[] expectedLevels = levels.split(",");
			for (int i = 0; i < expectedLevels.length; i++) {
				JsonArray levelsArr = entityParameterObject.getAsJsonArray(expectedLevels[i]);
				// check if isSubscription = false
				JsonObject levelObject = (JsonObject) levelsArr.get(0);
				JsonArray thresholdArr = levelObject.getAsJsonArray("threshold");
				testInfo.setResponse(thresholdArr.toString());
				int totEntityThresholds = thresholdDetails.split("\\s+").length;
				if (expectedLevels.length == totEntityThresholds)
					totEntityThresholds = 1;
				for (int index = 0; index < totEntityThresholds; index++) {
					String[] thresholdValues = thresholdDetails.split("\\s+")[index].split(",");
					JsonObject thresholdObj = (JsonObject) thresholdArr.get(index);
					testExecutionStatus = thresholdObj.get("name").getAsString().equals(thresholdValues[0]) ? true
							: failTest(insightKey, thresholdValues[0], thresholdObj.get("name").getAsString(),
									failureReason);
					if (testExecutionStatus)
						testExecutionStatus = (int) Double.parseDouble(
								thresholdObj.get("value").getAsString()) == Integer.parseInt(thresholdValues[1])
										? true
										: failTest(insightKey,
												"Threshold value is " + Integer.parseInt(thresholdValues[1]) + " But ",
												thresholdObj.get("value").getAsString(), failureReason);
					if (testExecutionStatus)
						testExecutionStatus = thresholdObj.get("type").getAsString().equals(thresholdValues[2]) ? true
								: failTest(insightKey, thresholdValues[2], thresholdObj.get("type").getAsString(),
										failureReason);
				}
			}
		}
		return testExecutionStatus;
	}

	// GET - User subscription details verifications - keep
	public boolean verifyInsightDetailsOfUserSubscription(JsonObject insightObjInCob, JsonObject insightObjInUser,
			String triggerType, String insightKey, String insightName, String title, String containerSupported,
			JsonArray keys, String totNumberOfKeys, String description, String applicableEntity, String levels,
			String isSubscribed, String frequency, String thresholdDetails, String checkRuleExpressionPresent,
			String configMetaAndentityParameter, FailureReason failureReason, String validateKeys, TestInfo testInfo) {
		boolean testExecutionStatus = true;
		// JsonArray containers = insightObj.getAsJsonArray("container");
		if (!triggerType.isEmpty()) {
			testExecutionStatus = insightObjInUser.get("triggerType").getAsString()
					.equals(insightObjInCob.get("triggerType").getAsString()) ? true
							: failTest(insightKey, insightObjInCob.get("triggerType").getAsString(),
									insightObjInUser.get("triggerType").getAsString(), failureReason);
		}
		if (!insightName.isEmpty()) {
			testExecutionStatus = insightObjInUser.get("name").getAsString()
					.equals(insightObjInCob.get("name").getAsString()) ? true
							: failTest(insightKey, insightObjInCob.get("name").getAsString(),
									insightObjInUser.get("name").getAsString(), failureReason);
		}
		if (!title.isEmpty()) {
			testExecutionStatus = insightObjInUser.get("title").getAsString()
					.equals(insightObjInCob.get("title").getAsString()) ? true
							: failTest(insightKey, insightObjInCob.get("title").getAsString(),
									insightObjInUser.get("title").getAsString(), failureReason);
		}

		if (!checkRuleExpressionPresent.isEmpty()) {
			try {
				insightObjInUser.get("ruleExpression").getAsString();
				testExecutionStatus = failTest(insightKey, "Rule Expression Must not be present is subscriptions",
						insightObjInUser.get("ruleExpression").getAsString(), failureReason);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		if (!containerSupported.isEmpty()) {
			JsonArray expectedContainersInCobArray = insightObjInCob.getAsJsonArray("container");
			JsonArray actualContainerInUserArray = insightObjInUser.getAsJsonArray("container");
			for (int i = 0; i < expectedContainersInCobArray.size(); i++) {
				if (expectedContainersInCobArray.size() == actualContainerInUserArray.size()) {
					testExecutionStatus = expectedContainersInCobArray.toString()
							.contains(actualContainerInUserArray.get(i).getAsString()) ? true
									: failTest(insightKey, expectedContainersInCobArray.get(i).getAsString(),
											actualContainerInUserArray.get(i).getAsString(), failureReason);
				} else {
					testExecutionStatus = expectedContainersInCobArray.size() == actualContainerInUserArray.size()
							? true
							: failTest(insightKey, "Expected Containers size =" + expectedContainersInCobArray.size(),
									"Actual Containers size =" + actualContainerInUserArray.size(), failureReason);
				}
			}
		}

		JsonArray keysArray = insightObjInUser.getAsJsonArray("entityConfiguration");
		if (keys.size() > 0 && !validateKeys.isEmpty()) {
			StringBuilder expectedKeys = new StringBuilder();
			for (int i = 0; i < keys.size(); i++) {
				JsonObject entityKeyObject = (JsonObject) keys.get(i);
				if (entityKeyObject.keySet().toString().contains(validateKeys)) {
					String[] keyArray = entityKeyObject.get(entityKeyObject.keySet().toString().substring(1,
							entityKeyObject.keySet().toString().length() - 1)).getAsString().split(",");
					for (int keys1 = 1; keys1 < keyArray.length; keys1++) {
						expectedKeys.append(keyArray[0] + "." + keyArray[keys1]);
					}
				}
			}

			if (validateKeys.equalsIgnoreCase("Account") && insightName.equalsIgnoreCase("FinancialFees")) {
				for (int i = 0; i < keys.size(); i++) {
					JsonObject entityKeyObject = (JsonObject) keys.get(i);
					if (entityKeyObject.keySet().toString().contains("PB")) {
						String[] keyArray = entityKeyObject.get(entityKeyObject.keySet().toString().substring(1,
								entityKeyObject.keySet().toString().length() - 1)).getAsString().split(",");
						for (int keys1 = 1; keys1 < keyArray.length; keys1++) {
							expectedKeys.append(keyArray[0] + "." + keyArray[keys1]);
						}
						break;
					}
				}
			}

			StringBuilder actualKeys = new StringBuilder();
			JsonObject entityObject = null;
			for (int m = 0; m < keysArray.size(); m++) {
				entityObject = (JsonObject) keysArray.get(m);
				if (entityObject.get("entityName").getAsString().equalsIgnoreCase(validateKeys))
					break;
			}
			JsonArray actaulskeysArray = entityObject.getAsJsonArray("keys");
			testInfo.setResponse(actaulskeysArray.toString());
			for (int index1 = 0; index1 < actaulskeysArray.size(); index1++) {
				actualKeys.append(actaulskeysArray.get(index1).getAsString());
			}

			if (actaulskeysArray.size() != Integer.parseInt(totNumberOfKeys)) {
				failTest(insightKey, totNumberOfKeys, Integer.toString(actaulskeysArray.size()), failureReason);
			}
			// expected and actual keys check
			if (!expectedKeys.toString().equals(actualKeys.toString())) {
				testExecutionStatus = failTest(insightKey, expectedKeys.toString(), actualKeys.toString(),
						failureReason);
				System.err.println("Both(Keys) Are not same");
			}
		}
		if (!description.isEmpty()) {
			testExecutionStatus = insightObjInUser.get("description").getAsString()
					.equals(insightObjInCob.get("description").getAsString()) ? true
							: failTest(insightKey, insightObjInCob.get("description").getAsString(),
									insightObjInUser.get("description").getAsString(), failureReason);
		}
		if (!applicableEntity.isEmpty() && levels.isEmpty()) {
			JsonArray cobApplicableEntityArr = insightObjInCob.getAsJsonArray("applicableEntity");
			JsonArray userApplicableEntityArr = insightObjInUser.getAsJsonArray("applicableEntity");
			for (int i = 0; i < cobApplicableEntityArr.size(); i++) {
				if (cobApplicableEntityArr.size() == userApplicableEntityArr.size()) {
					testExecutionStatus = userApplicableEntityArr.get(i).getAsString()
							.equals(cobApplicableEntityArr.get(i).getAsString()) ? true
									: failTest(insightKey, cobApplicableEntityArr.get(i).getAsString(),
											userApplicableEntityArr.get(i).getAsString(), failureReason);
				}
			}
		}

		if (!levels.isEmpty()) {
			String[] allLevels = levels.split(",");
			JsonObject entityParameterCobObject = insightObjInCob.getAsJsonObject("entityParameter");
			JsonObject configMetaUserObject = insightObjInUser.getAsJsonObject("configMeta");
			// for(int i = 0;i<entityParameterCobObject.size();i++)
			for (int i = 0; i < allLevels.length; i++) {
				try {
					entityParameterCobObject.getAsJsonArray(allLevels[i]);
					configMetaUserObject.getAsJsonArray(allLevels[i]);
				} catch (Exception e) {
					testExecutionStatus = failTest(insightKey, allLevels[i],
							allLevels[i] + " Level is Not Found in User Subscription", failureReason);
				}
			}
		}
		if (!frequency.isEmpty() && !levels.isEmpty()) {
			JsonObject entityParameterCobObject = insightObjInCob.getAsJsonObject("entityParameter");
			JsonObject configMetaUserObject = insightObjInUser.getAsJsonObject("configMeta");
			String[] expectedLevels = levels.split(",");
			for (int i = 0; i < expectedLevels.length; i++) {
				JsonArray levelsCobArr = entityParameterCobObject.getAsJsonArray(expectedLevels[i]);
				// check if isSubscription = false
				JsonObject cobLevelObject = (JsonObject) levelsCobArr.get(0);
				JsonArray levelsUserArr = configMetaUserObject.getAsJsonArray(expectedLevels[i]);
				// check if isSubscription = false
				JsonObject ueseLevelObject = (JsonObject) levelsUserArr.get(0);
				testExecutionStatus = ueseLevelObject.get("frequency").getAsString()
						.equals(cobLevelObject.get("frequency").getAsString()) ? true
								: failTest(insightKey, cobLevelObject.get("frequency").getAsString(),
										ueseLevelObject.get("frequency").getAsString(), failureReason);
			}
		}
		if (!isSubscribed.isEmpty() && !levels.isEmpty()) {
			JsonObject entityParameterCobObject = insightObjInCob.getAsJsonObject("entityParameter");
			JsonObject configMetaUserObject = insightObjInUser.getAsJsonObject("configMeta");
			String[] expectedLevels = levels.split(",");
			for (int i = 0; i < expectedLevels.length; i++) {
				JsonArray cobLevelsArr = entityParameterCobObject.getAsJsonArray(expectedLevels[i]);
				// check if isSubscription = false
				JsonObject cobLevelObject = (JsonObject) cobLevelsArr.get(0);
				JsonArray userLevelsArr = configMetaUserObject.getAsJsonArray(expectedLevels[i]);
				// check if isSubscription = false
				JsonObject userLevelObject = (JsonObject) userLevelsArr.get(0);
				testExecutionStatus = userLevelObject.get("isSubscribed").getAsBoolean() == cobLevelObject
						.get("isSubscribed").getAsBoolean() ? true
								: failTest(insightKey, cobLevelObject.get("isSubscribed").getAsString(),
										userLevelObject.get("isSubscribed").getAsString(), failureReason);
			}
		}
		if (!thresholdDetails.isEmpty() && !levels.isEmpty()) {
			JsonObject entityParameterCobObject = insightObjInCob.getAsJsonObject("entityParameter");
			JsonObject configMetaUserObject = insightObjInUser.getAsJsonObject("configMeta");
			String[] expectedLevels = levels.split(",");
			for (int i = 0; i < expectedLevels.length; i++) {
				JsonArray cobLevelsArr = entityParameterCobObject.getAsJsonArray(expectedLevels[i]);
				// check if isSubscription = false
				JsonObject cobLevelObject = (JsonObject) cobLevelsArr.get(0);
				JsonArray cobThresholdArr = cobLevelObject.getAsJsonArray("threshold");

				JsonArray userLevelsArr = configMetaUserObject.getAsJsonArray(expectedLevels[i]);
				// check if isSubscription = false
				JsonObject userLevelObject = (JsonObject) userLevelsArr.get(0);
				JsonArray userThresholdArr = userLevelObject.getAsJsonArray("threshold");

				String[] thresholdValues = thresholdDetails.split(",");
				for (int j = 0; j < userThresholdArr.size(); j++) {
					JsonObject cobThresholdObj = (JsonObject) cobThresholdArr.get(j);
					JsonObject userThresholdObj = (JsonObject) userThresholdArr.get(j);

					testExecutionStatus = userThresholdObj.get("name").getAsString()
							.equals(cobThresholdObj.get("name").getAsString()) ? true
									: failTest(insightKey, cobThresholdObj.get("name").getAsString(),
											userThresholdObj.get("name").getAsString(), failureReason);
					if (testExecutionStatus)
						testExecutionStatus = (int) Double
								.parseDouble(userThresholdObj.get("value").getAsString()) == (int) Double
										.parseDouble(cobThresholdObj.get("value").getAsString()) ? true
												: failTest(insightKey, cobThresholdObj.get("value").getAsString(),
														userThresholdObj.get("value").getAsString(), failureReason);
					if (testExecutionStatus)
						testExecutionStatus = userThresholdObj.get("type").getAsString()
								.equals(cobThresholdObj.get("type").getAsString()) ? true
										: failTest(insightKey, cobThresholdObj.get("type").getAsString(),
												userThresholdObj.get("type").getAsString(), failureReason);
				}
			}
		}

		if (!configMetaAndentityParameter.isEmpty()) {

			JsonObject entityParameterUserObject = insightObjInCob.getAsJsonObject("entityParameter");
			JsonObject configMetaUserObject = insightObjInUser.getAsJsonObject("configMeta");
			try {
				JsonNode entityParameterUserNode = mapper.readTree(entityParameterUserObject.getAsString());
				JsonNode configMetaUserNode = mapper.readTree(configMetaUserObject.getAsString());
				testExecutionStatus = entityParameterUserObject.equals(configMetaUserObject) ? true
						: failTest(insightKey, entityParameterUserObject.getAsString(),
								configMetaUserObject.getAsString(), failureReason);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		return testExecutionStatus;
	}

	public String processResponseToEditSubscriptionDetails(Response getCobrandSubscriptionResponse,
			String subscribeToInsight, boolean isSubscribedValue, String subscriptionLevel) {
		JsonObject responseObj = (JsonObject) jsonParser.parse(getCobrandSubscriptionResponse.asString());
		JsonArray cobrandSubscriptionsArray = responseObj.getAsJsonArray("cobrandSubscriptions");
		JsonArray editCobrandSubscriptionsArray = new JsonArray();
		int totalSubscriptions = cobrandSubscriptionsArray.size();
		for (int i = 0; i < totalSubscriptions; i++) {
			JsonObject insightObj = (JsonObject) cobrandSubscriptionsArray.get(i);
			System.out.println("InsightName" + insightObj.get("name").getAsString());
			String[] subscriptionLevels = subscriptionLevel.split(",");
			if (insightObj.get("name").getAsString().equals(subscribeToInsight.trim())) {// Insight name check
				JsonObject entityParameterObj = (JsonObject) insightObj.get("entityParameter");
				for (int j = 0; j < subscriptionLevels.length; j++) {
					JsonArray subscriptionLevelArray = entityParameterObj.getAsJsonArray(subscriptionLevels[j]);
					JsonObject subscriptionLevelArrayObjects = (JsonObject) subscriptionLevelArray.get(0);
					insightObj.remove("_id");
					subscriptionLevelArrayObjects.remove("isSubscribed");
					subscriptionLevelArrayObjects.addProperty("isSubscribed", isSubscribedValue);

				}
				editCobrandSubscriptionsArray.add(insightObj);
				break;
			}

		}
		responseObj.add("cobrandSubscriptions", editCobrandSubscriptionsArray);
		return responseObj.toString();
	}

	// correct code
	// update cobrand subscription
	// id,triggerType,insightName,insightTitle,container,cobrandId,keys,ruleExpressin,description,applicableEntity,entityParameters,
	// frequency,duration,isSubscribed,totalThresholds,subscriptionLevels,thresholdName,thresholdValue,thresholdType
	public String constructPatchCobrandSubscriptionRequest(Response getCobrandSubscriptionResponse,
			String[] editCobrandSubscriptionProperties, String testCaseId) {
		String[] cobSubscriptionObjectKeys = { "_id", "triggerType", "name", "title", "container", "cobrandId", "keys",
				"ruleExpression", "description", "applicableEntity", "entityParameter" };
		String insightName = editCobrandSubscriptionProperties[2].trim();
		JsonObject cobrandInsightObj = null;
		JsonObject cobrandEntityParameterObj = null;
		JsonArray editCobrandSubscriptionsArray = new JsonArray();
		JsonObject patchCobrandSubscritionObject = new JsonObject();
		int totalThresholds = 0;
		JsonObject getCobrandSubscriptionResponseObj = (JsonObject) jsonParser
				.parse(getCobrandSubscriptionResponse.asString());
		JsonArray cobrandSubscriptionsArray = getCobrandSubscriptionResponseObj.getAsJsonArray("cobrandSubscriptions");
		for (int i = 0; i < cobrandSubscriptionsArray.size(); i++) {
			cobrandInsightObj = (JsonObject) cobrandSubscriptionsArray.get(i);
			if (cobrandInsightObj.get("name").getAsString().equals(insightName)) {// Insight name check
				for (int j = 0; j < editCobrandSubscriptionProperties.length; j++) {
					boolean foundEditProperty = false, entityParameterEdited = false;
					if (!editCobrandSubscriptionProperties[j].isEmpty() && j != 2) { // edit non null attribute from the
																						// input CSV file , check j!=2
																						// skip editing insightName
						// cobrandInsightObj.remove(cobSubscriptionObjectKeys[j]);
						if (cobSubscriptionObjectKeys[j].equals("cobrandId")) {
							int cobrandId = Integer.parseInt(editCobrandSubscriptionProperties[j]);
							cobrandInsightObj.remove(cobSubscriptionObjectKeys[j]);
							cobrandInsightObj.addProperty(cobSubscriptionObjectKeys[j], cobrandId);
						} else if (cobSubscriptionObjectKeys[j].equals("title")) {
							cobrandInsightObj.remove(cobSubscriptionObjectKeys[j]);
							cobrandInsightObj.addProperty(cobSubscriptionObjectKeys[j],
									editCobrandSubscriptionProperties[j]);
						} else if (cobSubscriptionObjectKeys[j].equals("keys")) {
							cobrandInsightObj.remove(cobSubscriptionObjectKeys[j]);
							JsonArray containerArray = new JsonArray();
							containerArray.add(editCobrandSubscriptionProperties[6]);
							cobrandInsightObj.add(cobSubscriptionObjectKeys[j], containerArray);
						} else if (cobSubscriptionObjectKeys[j].equals("container")) {
							cobrandInsightObj.remove(cobSubscriptionObjectKeys[j]);
							JsonArray containerArray = new JsonArray();
							containerArray.add(editCobrandSubscriptionProperties[4]);
							cobrandInsightObj.add(cobSubscriptionObjectKeys[j], containerArray);
						} else if (!editCobrandSubscriptionProperties[19].isEmpty()) {
							entityParameterEdited = true;
							if (editCobrandSubscriptionProperties[19].equals("DELETE")) {
								cobrandEntityParameterObj = new JsonObject();
							}

							if (editCobrandSubscriptionProperties[19].equals("REPLACE")) {
								cobrandEntityParameterObj = (JsonObject) cobrandInsightObj.get("entityParameter");
								String[] subscriptionLevels = editCobrandSubscriptionProperties[15].toString()
										.split(",");
								JsonArray subscriptionLevelArray = cobrandEntityParameterObj
										.getAsJsonArray(subscriptionLevels[0]);
								JsonObject subscriptionLevelArrayObjects = (JsonObject) subscriptionLevelArray.get(0);
								subscriptionLevelArrayObjects.add(editCobrandSubscriptionProperties[20],
										subscriptionLevelArrayObjects);
							}
							if (editCobrandSubscriptionProperties[19].equals("ADD")) {
								cobrandEntityParameterObj = (JsonObject) cobrandInsightObj.get("entityParameter");
								String[] subscriptionLevels = editCobrandSubscriptionProperties[15].toString()
										.split(",");
								JsonArray subscriptionLevelArray = cobrandEntityParameterObj
										.getAsJsonArray(subscriptionLevels[0]);
								JsonObject subscriptionLevelArrayObjects = (JsonObject) subscriptionLevelArray.get(0);
								subscriptionLevelArrayObjects.add(editCobrandSubscriptionProperties[20],
										subscriptionLevelArrayObjects);
							}
						} else if (cobSubscriptionObjectKeys[j].equals("entityParameter")) {
							cobrandEntityParameterObj = (JsonObject) cobrandInsightObj.get("entityParameter");
							cobrandInsightObj.remove(cobSubscriptionObjectKeys[j]);
							String[] subscriptionLevels = editCobrandSubscriptionProperties[15].toString().split(",");
							for (int k = 0; k < subscriptionLevels.length; k++) {
								JsonArray subscriptionLevelArray = cobrandEntityParameterObj
										.getAsJsonArray(subscriptionLevels[k]);
								JsonObject subscriptionLevelArrayObjects = (JsonObject) subscriptionLevelArray.get(k);
								subscriptionLevelArrayObjects.remove("isSubscribed");
								if (!editCobrandSubscriptionProperties[13].equals("NULL")) {
									boolean subscribedValue = Boolean
											.parseBoolean(editCobrandSubscriptionProperties[13]);
									subscriptionLevelArrayObjects.addProperty("isSubscribed", subscribedValue);
								}
								if (!editCobrandSubscriptionProperties[11].isEmpty())// add frequency - invalid
									subscriptionLevelArrayObjects.addProperty("frequency",
											editCobrandSubscriptionProperties[11]);
								if (!editCobrandSubscriptionProperties[12].isEmpty())// add duration - invalid
									subscriptionLevelArrayObjects.addProperty("duration",
											editCobrandSubscriptionProperties[12]);
								boolean editThreshold = Boolean.parseBoolean(editCobrandSubscriptionProperties[14]);
								if (editThreshold) {
									JsonArray thresholdArray = subscriptionLevelArrayObjects
											.getAsJsonArray("threshold");
									JsonObject thresholdLevelObj = (JsonObject) thresholdArray.get(0);
									if (!editCobrandSubscriptionProperties[18].isEmpty()) {
										thresholdLevelObj.remove("type");
										thresholdLevelObj.addProperty("type", editCobrandSubscriptionProperties[18]);
									}
									if (!editCobrandSubscriptionProperties[17].isEmpty()) {
										thresholdLevelObj.remove("value");
										thresholdLevelObj.addProperty("value", editCobrandSubscriptionProperties[17]);
									}
									if (!editCobrandSubscriptionProperties[16].isEmpty()) {
										thresholdLevelObj.remove("name");
										thresholdLevelObj.addProperty("name", editCobrandSubscriptionProperties[16]);
									}

								}
								entityParameterEdited = true;
							}

						}
						foundEditProperty = true;
					} else {
						if (cobSubscriptionObjectKeys[j].equals("name")
								&& patchCobEditInsightNameTcs.contains(testCaseId)) {
							cobrandInsightObj.remove(cobSubscriptionObjectKeys[j]);
							cobrandInsightObj.addProperty(cobSubscriptionObjectKeys[j],
									editCobrandSubscriptionProperties[j] + "EditInsightName");
							foundEditProperty = true;
						}
					}

					if (foundEditProperty) {
						if (entityParameterEdited) {
							cobrandInsightObj.add("entityParameter", cobrandEntityParameterObj);
						}
						break;
					}
				}
				break;
			}

		}
		editCobrandSubscriptionsArray.add(cobrandInsightObj);
		patchCobrandSubscritionObject.add("cobrandSubscriptions", editCobrandSubscriptionsArray);
		return patchCobrandSubscritionObject.toString();
	}

	// Latest - Patch user subscription construct payload
	// user
	public String constructPatchUserSubscriptionRequest(String insightNameKey, String objectId, String insightNameEdit,
			String insightTitle, String container, String cobrandId, String keys, String ruleExpression,
			String description, String applicableEntity, String entityParametersCnt, String editConfigMeta,
			String entityParameterName, String totThresholds, String id, String frequency, String isSubscribed,
			String duration, String insightType, String threshold_Name_Value_Type, String triggerType) {

		editedDetailsMap = new HashMap<String, String>();
		JSONObject patchUserSubscriptionPayload = new JSONObject(); // {}
		JSONArray userSubscriptionsArray = new JSONArray(); // { userSubscriptions[] }
		JSONObject userSubscriptionsArrayObject = new JSONObject(); // { userSubscriptions[0] }
		JSONArray containerArray = new JSONArray(); // container[]
		JSONArray keysArray = new JSONArray(); // keys[]
		JSONArray applicableEntityArray = new JSONArray(); // applicableEntity[]
		int entityParametersCount = 0, totalThresholds = 0;
		if (!entityParametersCnt.isEmpty())
			entityParametersCount = Integer.parseInt(entityParametersCnt);
		if (!totThresholds.isEmpty())
			totalThresholds = Integer.parseInt(totThresholds);

		/*
		 * if(!objectId.isEmpty()) { userSubscriptionsArrayObject.put("_id", objectId);
		 * editedDetailsMap.put("_id", objectId); }
		 */
		if (!triggerType.isEmpty()) {
			userSubscriptionsArrayObject.put("triggerType", triggerType);
			editedDetailsMap.put("triggerType", triggerType);
		}
		if (!insightNameKey.isEmpty()) {
			userSubscriptionsArrayObject.put("name", insightNameKey);
			editedDetailsMap.put("name", insightNameKey);
		}
		if (!insightNameEdit.isEmpty()) {
			userSubscriptionsArrayObject.put("name", insightNameEdit);
			editedDetailsMap.put("name", insightNameEdit);
		}

		if (!insightTitle.isEmpty()) {
			userSubscriptionsArrayObject.put("title", insightTitle);
			editedDetailsMap.put("title", insightTitle);
		}
		if (!container.isEmpty()) {
			String[] containers = container.split("\\s+");
			for (String cont : containers) {
				containerArray.add(cont);
			}
			userSubscriptionsArrayObject.put("container", containerArray);
			editedDetailsMap.put("container", containerArray.toString());
		}
		if (!cobrandId.isEmpty()) {
			userSubscriptionsArrayObject.put("cobrandId", cobrandId);
			editedDetailsMap.put("cobrandId", cobrandId);
		}
		if (!keys.isEmpty()) {

			String[] keysArr = keys.split("\\s+");
			for (String key : keysArr) {
				keysArray.add(key);
			}
			userSubscriptionsArrayObject.put("keys", keysArray);
			editedDetailsMap.put("keys", keysArray.toString());
		}
		if (!ruleExpression.isEmpty()) {
			userSubscriptionsArrayObject.put("ruleExpression", ruleExpression);
			editedDetailsMap.put("ruleExpression", ruleExpression);
		}
		if (!description.isEmpty()) {
			userSubscriptionsArrayObject.put("description", description);
			editedDetailsMap.put("description", description);
		}
		if (!applicableEntity.isEmpty()) {
			String[] applicableEntityArr = applicableEntity.split("\\s+");
			for (String aplicableEntity : applicableEntityArr) {
				applicableEntityArray.add(aplicableEntity);
			}
			userSubscriptionsArrayObject.put("applicableEntity", applicableEntityArray);
			editedDetailsMap.put("applicableEntity", applicableEntityArray.toString());
		}

		if (entityParametersCount > 0) {
			// more than one level account , goal etc
			// String id,String frequency,String isSubscribed,String duration,String
			// insightType,String threshold_Name_Value_Type

			JSONObject entityParameterObject = new JSONObject(); // entityParameter{ }
			JSONArray entityParameterApplicableLevelArray = null, entityParameterApplicableLevelBoardArray = null;
			int thresholdIndex = 0;
			entityParameterApplicableLevelBoardArray = new JSONArray();
			JSONObject entityParameterApplicableLevelArrayObj = null;// entityParameter{ account[0] }
			for (int i = 0; i < entityParametersCount; i++) {
				entityParameterApplicableLevelArray = new JSONArray();
				entityParameterApplicableLevelArrayObj = new JSONObject(); // entityParameter{ account[0] }
				if (!id.isEmpty()) {
					String ids[] = id.split("\\s+");
					entityParameterApplicableLevelArrayObj.put("id", ids[i]);
				}

				if (!frequency.isEmpty()) {
					String frequencies[] = frequency.split("\\s+");
					entityParameterApplicableLevelArrayObj.put("frequency", frequencies[i]);
				}

				if (!isSubscribed.isEmpty()) {
					String isSubscribedVals[] = isSubscribed.split("\\s+");
					boolean isSubscribedVal = Boolean.parseBoolean(isSubscribedVals[i]);
					entityParameterApplicableLevelArrayObj.put("isSubscribed", isSubscribedVal);
				}
				if (!duration.isEmpty()) {
					String[] durations = duration.split("\\s+");
					entityParameterApplicableLevelArrayObj.put("duration", durations[i]);
				}

				if (!insightType.isEmpty()) {
					String[] insightTypes = insightType.split("\\s+");
					entityParameterApplicableLevelArrayObj.put("insightType", insightTypes[i]);
				}

				String[] enitityParametersNames = entityParameterName.split("\\s+");
				if (totalThresholds == entityParametersCount) {
					// for(int j=0;j<totalThresholds;j++) {
					JSONArray thresholdArray = new JSONArray();
					JSONObject thresholdArrayObject = new JSONObject();
					if (!threshold_Name_Value_Type.isEmpty()) {
						String[] individualThresholdValues = threshold_Name_Value_Type.split("\\s+");
						String[] thresholdNameValueType = individualThresholdValues[i].split(",");
						thresholdArrayObject.put("name", thresholdNameValueType[0]);
						thresholdArrayObject.put("value", thresholdNameValueType[1]);
						thresholdArrayObject.put("type", thresholdNameValueType[2]);
						thresholdArray.add(thresholdArrayObject);
						entityParameterApplicableLevelArrayObj.put("threshold", thresholdArray);
					}
					entityParameterApplicableLevelArray.add(entityParameterApplicableLevelArrayObj);// entityParameter{	// account[0] }																								
					entityParameterObject.put(enitityParametersNames[i], entityParameterApplicableLevelArray);
					// }

				}
				
	

				
				// n entities and m thresholds
				
				if (totalThresholds - entityParametersCount == entityParametersCount) {// one goal OR account having 2 or more thresholds
					JSONObject thresholdArrayObject = null;
					JSONArray thresholdArray = new JSONArray();					
						String[] individualThresholdValues = threshold_Name_Value_Type.split("\\s+");						
						while (thresholdIndex < (individualThresholdValues.length/(entityParametersCount-i))) {
							thresholdArrayObject = new JSONObject();
							String[] thresholdNameValueType = individualThresholdValues[thresholdIndex].split(",");
							thresholdArrayObject.put("name", thresholdNameValueType[0]);
							thresholdArrayObject.put("value", thresholdNameValueType[1]);
							thresholdArrayObject.put("type", thresholdNameValueType[2]);
							thresholdArray.add(thresholdArrayObject);
							thresholdIndex++;
						}								
					entityParameterApplicableLevelArrayObj.put("threshold", thresholdArray);
					entityParameterApplicableLevelArray.add(entityParameterApplicableLevelArrayObj);// entityParameter{ // account[0] }																									
					entityParameterObject.put(enitityParametersNames[i], entityParameterApplicableLevelArray);
				}
				
				if (entityParametersCount - totalThresholds >= 1) {// having two levels account and board
					JSONObject thresholdArrayObject = null;
					JSONArray thresholdArray = new JSONArray();
					for (int k = 0; k < totalThresholds; k++) {
						thresholdArrayObject = new JSONObject();
						if (!threshold_Name_Value_Type.isEmpty()) {
							String[] individualThresholdValues = threshold_Name_Value_Type.split("\\s+");
							String[] thresholdNameValueType = individualThresholdValues[i].split(",");
							thresholdArrayObject.put("name", thresholdNameValueType[0]);
							thresholdArrayObject.put("value", thresholdNameValueType[1]);
							thresholdArrayObject.put("type", thresholdNameValueType[2]);
							thresholdArray.add(thresholdArrayObject);
						}
					}

					if (enitityParametersNames[0].equals(enitityParametersNames[i])) {
						if (totalThresholds != 0)
							entityParameterApplicableLevelArrayObj.put("threshold", thresholdArray);
						entityParameterApplicableLevelArray.add(entityParameterApplicableLevelArrayObj);// entityParameter{// account[0] }																										
						entityParameterObject.put(enitityParametersNames[0], entityParameterApplicableLevelArray);
					} else {
						if (totalThresholds != 0)
							entityParameterApplicableLevelArrayObj.put("threshold", thresholdArray);
						entityParameterApplicableLevelBoardArray.add(entityParameterApplicableLevelArrayObj);// entityParameter{// account[0]// }																												
						entityParameterObject.put(enitityParametersNames[i], entityParameterApplicableLevelBoardArray);
					}
				}

			}
			
		
			
			editedDetailsMap.put("entityParameter", entityParameterObject.toString());
			if (!editConfigMeta.isEmpty()) {
				userSubscriptionsArrayObject.put("configMeta", entityParameterObject);
			} else {
				userSubscriptionsArrayObject.put("entityParameter", entityParameterObject);
			}
		}
		userSubscriptionsArray.add(userSubscriptionsArrayObject);

		patchUserSubscriptionPayload.put("userSubscription", userSubscriptionsArray);

		return patchUserSubscriptionPayload.toString();
	}

	public String constructInvokerPayLoad(String memId, String insightName, String envUser) {
		JSONObject invokerObject = new JSONObject();
		if (envUser.equals("senseUser"))
			invokerObject.put("cobrandId", 10000004);
		if (envUser.equals("wellnessUser"))
			invokerObject.put("cobrandId", 10011373);
		invokerObject.put("memId", Long.parseLong(memId));
		/*
		 * invokerObject.put("insightName", insightName);
		 * invokerObject.put("insightType", "all");
		 */
		return invokerObject.toString();
	}

	public String constructInvokerPayLoad(HashMap<String, String> entityIdsMap, String memId, String insightName,String envUser,String container,ArrayList<Long> cacheItemsList) {
		Gson gson = new Gson();
		EncryptionUtil encryptionUtil = new EncryptionUtil();
		InvokerPayload invokerPayLoad = new InvokerPayload();
		List<Payload> payloadList = new ArrayList<Payload>();
		Payload payload = null;	
		RuntimeParams runtimeParameters = null;
		if (envUser.equals("senseUser"))
			invokerPayLoad.setCobrandId(10000004L);
		if (envUser.equals("wellnessUser"))
			invokerPayLoad.setCobrandId(10011373L);
		invokerPayLoad.setMemId(Long.parseLong(memId));		
		invokerPayLoad.setExecutionConfiguration(invokerPayLoad.getExecutionConfiguration());
		invokerPayLoad.setSecurityToken(encryptionUtil.getEncryptedValue(new StringBuilder(memId).append(",").append(invokerPayLoad.getCobrandId()).toString()));		
		ArrayList<Long> itemAccountsList = InsightsGenerics.getItemAccountsByContainer(VisibleAllOver.getInstance().getEnvSession(),container.toLowerCase());		
		for(int i = 0; i<cacheItemsList.size();i++) {
			runtimeParameters = new RuntimeParams();
			runtimeParameters.setCacheItemId(cacheItemsList.get(i).toString());
			runtimeParameters.setContainer(container);
			runtimeParameters.setMemId(memId);	
			invokerPayLoad.setRuntimeParameters(runtimeParameters);
			if (itemAccountsList != null) {						
				for(int j=0;j<itemAccountsList.size();j++) {
					payload = new Payload();
					Long itemAccount = itemAccountsList.get(j);
					payload.setItemAccountId(itemAccount);
					payloadList.add(payload);
				}
				invokerPayLoad.setPayload(payloadList);
			}
		}				
		String invokerRequestPayload = gson.toJson(invokerPayLoad);
		return invokerRequestPayload;
	}


	public String constructUpdateCredentialsPayload(String userName, String password) {
		JSONObject updateCredsObject = new JSONObject();
		JSONObject loginFormObject = new JSONObject();
		JSONArray rowArray = new JSONArray();
		JSONObject rowUserFieldObject = new JSONObject();
		JSONObject rowPwdFieldObject = new JSONObject();
		JSONArray fieldUserArray = new JSONArray();
		JSONArray fieldPwdArray = new JSONArray();
		JSONObject userObject = new JSONObject();
		JSONObject pwdObject = new JSONObject();
		userObject.put("id", 65499);
		userObject.put("value", userName);
		pwdObject.put("id", 65500);
		pwdObject.put("value", password);
		fieldUserArray.add(userObject);
		fieldPwdArray.add(pwdObject);
		rowUserFieldObject.put("field", fieldUserArray);
		rowPwdFieldObject.put("field", fieldPwdArray);
		rowArray.add(rowUserFieldObject);
		rowArray.add(rowPwdFieldObject);
		loginFormObject.put("row", rowArray);
		updateCredsObject.put("loginForm", loginFormObject);
		// updateCredsObject.
		return updateCredsObject.toString();
	}

	public String constructSmartInsightsTriggerPayLoad(String memId, String insightName) {
		JSONObject smartInsightTriggerPayload = new JSONObject();
		smartInsightTriggerPayload.put("cobrandId", "10000004");
		smartInsightTriggerPayload.put("memId", memId);
		smartInsightTriggerPayload.put("insightName", insightName);
		smartInsightTriggerPayload.put("id", "5e69ef162081290296c270ff");
		return smartInsightTriggerPayload.toString();
	}

	// Latest - Patch Cobrand subscription construct payload
	// cobrand
	public String constructPatchCobrandSubscriptionRequest(String insightNameKey, String objectId,
			String insightNameEdit, String insightTitle, String container, String cobrandId, String keys,
			String ruleExpression, String description, String applicableEntity, String entityParametersCnt,
			String entityParameterName, String totThresholds, String id, String frequency, String isSubscribed,
			String duration, String insightType, String threshold_Name_Value_Type) {

		editedDetailsMap = new HashMap<String, String>();
		JSONObject patchCobrandSubscriptionPayload = new JSONObject(); // {}
		JSONArray cobrandSubscriptionsArray = new JSONArray(); // { userSubscriptions[] }
		JSONObject cobrandSubscriptionsArrayObject = new JSONObject(); // { userSubscriptions[0] }
		JSONArray containerArray = new JSONArray(); // container[]
		JSONArray keysArray = new JSONArray(); // keys[]
		JSONArray applicableEntityArray = new JSONArray(); // applicableEntity[]
		int entityParametersCount = 0, totalThresholds = 0;
		if (!entityParametersCnt.isEmpty())
			entityParametersCount = Integer.parseInt(entityParametersCnt);
		if (!totThresholds.isEmpty())
			totalThresholds = Integer.parseInt(totThresholds);

		/*
		 * if(!objectId.isEmpty()) { cobrandSubscriptionsArrayObject.put("_id",
		 * objectId); editedDetailsMap.put("_id", objectId); }
		 */
		if (!insightNameKey.isEmpty()) {
			cobrandSubscriptionsArrayObject.put("name", insightNameKey);
			editedDetailsMap.put("name", insightNameKey);
		}
		if (!insightNameEdit.isEmpty()) {
			cobrandSubscriptionsArrayObject.put("name", insightNameEdit);
			editedDetailsMap.put("name", insightNameEdit);
		}

		if (!insightTitle.isEmpty()) {
			cobrandSubscriptionsArrayObject.put("title", insightTitle);
			editedDetailsMap.put("title", insightTitle);
		}
		if (!container.isEmpty()) {
			String[] containers = container.split("\\s+");
			for (String cont : containers) {
				containerArray.add(cont);
			}
			cobrandSubscriptionsArrayObject.put("container", containerArray);
			editedDetailsMap.put("container", containerArray.toString());
		}
		if (!cobrandId.isEmpty()) {
			cobrandSubscriptionsArrayObject.put("cobrandId", cobrandId);
			editedDetailsMap.put("cobrandId", cobrandId);
		}
		if (!keys.isEmpty()) {

			String[] keysArr = keys.split("\\s+");
			for (String key : keysArr) {
				keysArray.add(key);
			}
			cobrandSubscriptionsArrayObject.put("keys", keysArray);
			editedDetailsMap.put("keys", keysArray.toString());
		}
		if (!ruleExpression.isEmpty()) {
			cobrandSubscriptionsArrayObject.put("ruleExpression", ruleExpression);
			editedDetailsMap.put("ruleExpression", ruleExpression);
		}
		if (!description.isEmpty()) {
			cobrandSubscriptionsArrayObject.put("description", description);
			editedDetailsMap.put("description", description);
		}
		if (!applicableEntity.isEmpty()) {
			String[] applicableEntityArr = applicableEntity.split("\\s+");
			for (String aplicableEntity : applicableEntityArr) {
				applicableEntityArray.add(aplicableEntity);
			}
			cobrandSubscriptionsArrayObject.put("applicableEntity", applicableEntityArray);
			editedDetailsMap.put("applicableEntity", applicableEntityArray.toString());
		}

		if (entityParametersCount > 0) {
			// more than one level account , goal etc
			// String id,String frequency,String isSubscribed,String duration,String
			// insightType,String threshold_Name_Value_Type

			JSONObject entityParameterObject = new JSONObject(); // entityParameter{ }
			JSONArray entityParameterApplicableLevelArray = null;
			JSONObject entityParameterApplicableLevelArrayObj = null;// entityParameter{ account[0] }
			for (int i = 0; i < entityParametersCount; i++) {
				entityParameterApplicableLevelArray = new JSONArray();
				entityParameterApplicableLevelArrayObj = new JSONObject(); // entityParameter{ account[0] }
				if (!id.isEmpty()) {
					String ids[] = id.split("\\s+");
					entityParameterApplicableLevelArrayObj.put("id", ids[i]);
				}

				if (!frequency.isEmpty()) {
					String frequencies[] = frequency.split("\\s+");
					entityParameterApplicableLevelArrayObj.put("frequency", frequencies[i]);
				}

				if (!isSubscribed.isEmpty()) {
					String isSubscribedVals[] = isSubscribed.split("\\s+");
					boolean isSubscribedVal = Boolean.parseBoolean(isSubscribedVals[i]);
					entityParameterApplicableLevelArrayObj.put("isSubscribed", isSubscribedVal);
				}
				if (!duration.isEmpty()) {
					String[] durations = duration.split("\\s+");
					entityParameterApplicableLevelArrayObj.put("duration", durations[i]);
				}

				if (!insightType.isEmpty()) {
					String[] insightTypes = insightType.split("\\s+");
					entityParameterApplicableLevelArrayObj.put("insightType", insightTypes[i]);
				}

				String[] enitityParametersNames = entityParameterName.split("\\s+");
				if (totalThresholds == entityParametersCount) {
					// for(int j=0;j<totalThresholds;j++) {
					JSONArray thresholdArray = new JSONArray();
					JSONObject thresholdArrayObject = new JSONObject();
					if (!threshold_Name_Value_Type.isEmpty()) {
						String[] individualThresholdValues = threshold_Name_Value_Type.split("\\s+");
						String[] thresholdNameValueType = individualThresholdValues[i].split(",");
						thresholdArrayObject.put("name", thresholdNameValueType[0]);
						thresholdArrayObject.put("value", thresholdNameValueType[1]);
						thresholdArrayObject.put("type", thresholdNameValueType[2]);
						thresholdArray.add(thresholdArrayObject);
						entityParameterApplicableLevelArrayObj.put("threshold", thresholdArray);

					}
					entityParameterApplicableLevelArray.add(entityParameterApplicableLevelArrayObj);// entityParameter{
																									// account[0] }
					entityParameterObject.put(enitityParametersNames[i], entityParameterApplicableLevelArray);
					// }

				}
				if (totalThresholds - entityParametersCount == 1) {// one goal OR account having 2 or more thresholds
					JSONObject thresholdArrayObject = null;
					JSONArray thresholdArray = new JSONArray();
					for (int k = 0; k < entityParametersCount; k++) {
						String[] individualThresholdValues = threshold_Name_Value_Type.split("\\s+");
						for (int index = 0; index < individualThresholdValues.length; index++) {
							thresholdArrayObject = new JSONObject();
							String[] thresholdNameValueType = individualThresholdValues[index].split(",");
							thresholdArrayObject.put("name", thresholdNameValueType[0]);
							thresholdArrayObject.put("value", thresholdNameValueType[1]);
							thresholdArrayObject.put("type", thresholdNameValueType[2]);
							thresholdArray.add(thresholdArrayObject);
						}
					}
					entityParameterApplicableLevelArrayObj.put("threshold", thresholdArray);
					entityParameterApplicableLevelArray.add(entityParameterApplicableLevelArrayObj);// entityParameter{
																									// account[0] }
					entityParameterObject.put(enitityParametersNames[i], entityParameterApplicableLevelArray);
				}
			
				// account and view is having 2 threshold each
				int numOfThresholdsPerEntity = (totalThresholds/entityParametersCount);
				if (numOfThresholdsPerEntity == 2 && entityParametersCount == 2) {
					JSONObject thresholdArrayObject = null;
					JSONArray thresholdArray = new JSONArray();
					
						String[] individualThresholdValues = threshold_Name_Value_Type.split("\\s+");
						int thresholds = (i+1)*2-2+numOfThresholdsPerEntity;
						for (int index = (i+1)*2-2; index < thresholds; index++) {
							thresholdArrayObject = new JSONObject();
							String[] thresholdNameValueType = individualThresholdValues[index].split(",");
							thresholdArrayObject.put("name", thresholdNameValueType[0]);
							thresholdArrayObject.put("value", thresholdNameValueType[1]);
							thresholdArrayObject.put("type", thresholdNameValueType[2]);
							thresholdArray.add(thresholdArrayObject);
						}
					
					entityParameterApplicableLevelArrayObj.put("threshold", thresholdArray);
					entityParameterApplicableLevelArray.add(entityParameterApplicableLevelArrayObj);// entityParameter{
																									// account[0] }
					entityParameterObject.put(enitityParametersNames[i], entityParameterApplicableLevelArray);
				}

				if (entityParametersCount - totalThresholds == 1) {// having two levels account and board
					JSONObject thresholdArrayObject = null;
					JSONArray thresholdArray = new JSONArray();
					for (int k = 0; k < totalThresholds; k++) {
						thresholdArrayObject = new JSONObject();
						if (!threshold_Name_Value_Type.isEmpty()) {
							String[] individualThresholdValues = threshold_Name_Value_Type.split("\\s+");
							String[] thresholdNameValueType = individualThresholdValues[i].split(",");
							thresholdArrayObject.put("name", thresholdNameValueType[0]);
							thresholdArrayObject.put("value", thresholdNameValueType[1]);
							thresholdArrayObject.put("type", thresholdNameValueType[2]);
							thresholdArray.add(thresholdArrayObject);
						}
					}
					if (totalThresholds != 0) {
						entityParameterApplicableLevelArrayObj.put("threshold", thresholdArray);
					}
					entityParameterApplicableLevelArray.add(entityParameterApplicableLevelArrayObj);// entityParameter{
																									// account[0] }
					entityParameterObject.put(enitityParametersNames[i], entityParameterApplicableLevelArray);
				}

				if (totThresholds.isEmpty() && entityParametersCount > 0) {
					entityParameterApplicableLevelArray.add(entityParameterApplicableLevelArrayObj);// entityParameter{
																									// account[0] }
					entityParameterObject.put(enitityParametersNames[i], entityParameterApplicableLevelArray);
				}
			}
			editedDetailsMap.put("entityParameter", entityParameterObject.toString());
			cobrandSubscriptionsArrayObject.put("entityParameter", entityParameterObject);
		}
		cobrandSubscriptionsArray.add(cobrandSubscriptionsArrayObject);
		patchCobrandSubscriptionPayload.put("cobrandSubscription", cobrandSubscriptionsArray);

		return patchCobrandSubscriptionPayload.toString();
	}

	// Verification after User Patch for editable attributes such as
	// isSubscribed,title,and threshold values
	public boolean verifyUserSubscriptionResponsePostPatch(String testCaseId, String testCaseDescription,
			Response cobSubscriptionResponse, Response userSubscriptionResponse, String insightKey, String frequency,
			String duration, String insightTitle, String subscriptionLevels, String isSubscribed,
			String thresholdDetails, FailureReason failureReason) {

		boolean insightExists = false, insightPresentInUser = false, insightPresentInCobrand = false,
				testExecutionStatus = true;
		JsonObject cobResponseObj = (JsonObject) jsonParser.parse(cobSubscriptionResponse.asString());
		JsonArray cobrandSubscriptionsArray = cobResponseObj.getAsJsonArray("cobrandSubscription");
		JsonObject userResponseObj = (JsonObject) jsonParser.parse(userSubscriptionResponse.asString());
		JsonArray userSubscriptionsArray = userResponseObj.getAsJsonArray("userSubscription");
		JsonObject insightObj, insightObjInCob = null, insightObjInUser = null;
		for (int i = 0; i < cobrandSubscriptionsArray.size(); i++) {
			insightObjInCob = (JsonObject) cobrandSubscriptionsArray.get(i);
			if (insightObjInCob.get("name").getAsString().equals(insightKey)) {// Insight name Verification
				insightPresentInCobrand = true;
				break;
			}
		}
		for (int i = 0; i < userSubscriptionsArray.size(); i++) {
			insightObjInUser = (JsonObject) userSubscriptionsArray.get(i);
			if (insightObjInUser.get("name").getAsString().equals(insightKey)) {// Insight name Verification
				insightPresentInUser = true;
				break;
			}
		}
		if (insightPresentInCobrand && insightPresentInUser) {
			testExecutionStatus = verifyInsightDetailsOfUserSubscriptionPostPatch(insightObjInUser, insightKey,
					insightTitle, frequency, duration, isSubscribed, subscriptionLevels, thresholdDetails,
					failureReason);
		} else {
			if (!insightPresentInCobrand) {
				Assert.fail("Test Failed for " + insightKey
						+ " Insight name is Doesn't exists in the Get cobrand subscription API Response");
			}
			if (!insightPresentInUser) {
				Assert.fail("Test Failed for " + insightKey
						+ " Insight name is Doesn't exists in the Get User subscription API Response");
			}
		}
		if (!testExecutionStatus) {
			logger.info("TEST FAILED testCaseId =" + testCaseId + "and testCase Description = " + testCaseDescription);
		}

		return testExecutionStatus;

	}

	// Verification after Cobrand Patch for editable attributes such as
	// isSubscribed,title,and threshold values
	public boolean verifyCobrandSubscriptionResponsePostPatch(String testCaseId, String testCaseDescription,
			Response cobSubscriptionResponse, String insightKey, String frequency, String duration, String insightType,
			String insightTitle, String subscriptionLevels, String isSubscribed, String thresholdDetails,
			FailureReason failureReason) {

		boolean insightExists = false, insightPresentInUser = false, insightPresentInCobrand = false,
				testExecutionStatus = true;
		JsonObject cobResponseObj = (JsonObject) jsonParser.parse(cobSubscriptionResponse.asString());
		JsonArray cobrandSubscriptionsArray = cobResponseObj.getAsJsonArray("cobrandSubscription");
		JsonObject insightObj, insightObjInCob = null, insightObjInUser = null;
		for (int i = 0; i < cobrandSubscriptionsArray.size(); i++) {
			insightObjInCob = (JsonObject) cobrandSubscriptionsArray.get(i);
			if (insightObjInCob.get("name").getAsString().equals(insightKey)) {// Insight name Verification
				insightPresentInCobrand = true;
				break;
			}
		}
		if (insightPresentInCobrand) {
			testExecutionStatus = verifyInsightDetailsOfCobrandSubscriptionPostPatch(insightObjInCob, insightKey,
					insightTitle, frequency, duration, isSubscribed, subscriptionLevels, thresholdDetails,
					failureReason);
		} else {
			if (!insightPresentInCobrand) {
				logger.info("Test Failed for " + insightKey
						+ " Insight name is Doesn't exists in the Get cobrand subscription API Response");
				// Assert.assertEquals("Insight Name Must be present in cobrand subscription",
				// "Insight name is Doesn't exists in the Get cobrand subscription API
				// Response");
				assertFalse(true, "Insight name is Doesn't exists in the Get cobrand subscription API Response");
			}
		}
		if (!testExecutionStatus) {
			logger.info("TEST FAILED testCaseId =" + testCaseId + "and testCase Description = " + testCaseDescription);
		}

		return testExecutionStatus;

	}

	// Verification after Patch for non editable attributes - compare against
	// cobrand subscription
	public boolean verifyUserSubscriptionResponsePostPatch(String testCaseId, String testCaseDescription,
			Response cobSubscriptionResponse, Response userSubscriptionResponse, String insightKey,
			String subscriptionLevels, FailureReason failureReason) {
		boolean insightExists = false, insightPresentInUser = false, insightPresentInCobrand = false,
				testExecutionStatus = true;
		JsonObject cobResponseObj = (JsonObject) jsonParser.parse(cobSubscriptionResponse.asString());
		JsonArray cobrandSubscriptionsArray = cobResponseObj.getAsJsonArray("cobrandSubscription");
		JsonObject userResponseObj = (JsonObject) jsonParser.parse(userSubscriptionResponse.asString());
		JsonArray userSubscriptionsArray = userResponseObj.getAsJsonArray("userSubscription");
		JsonObject insightObj, insightObjInCob = null, insightObjInUser = null;
		for (int i = 0; i < cobrandSubscriptionsArray.size(); i++) {
			insightObjInCob = (JsonObject) cobrandSubscriptionsArray.get(i);
			if (insightObjInCob.get("name").getAsString().equals(insightKey)) {// Insight name Verification
				insightPresentInCobrand = true;
				break;
			}
		}
		for (int i = 0; i < userSubscriptionsArray.size(); i++) {
			insightObjInUser = (JsonObject) userSubscriptionsArray.get(i);
			if (insightObjInUser.get("name").getAsString().equals(insightKey)) {// Insight name Verification
				insightPresentInUser = true;
				break;
			}
		}
		if (insightPresentInCobrand && insightPresentInUser) {
			testExecutionStatus = verifyInsightDetailsOfUserSubscriptionPostPatch(insightObjInCob, insightObjInUser,
					insightKey, subscriptionLevels, failureReason);
		} else {
			if (!insightPresentInCobrand) {
				Assert.fail("Test Failed for " + insightKey
						+ " Insight name is Doesn't exists in the Get cobrand subscription API Response");
			}
			if (!insightPresentInUser) {
				Assert.fail("Test Failed for " + insightKey
						+ " Insight name is Doesn't exists in the Get User subscription API Response");
			}
		}
		if (!testExecutionStatus) {
			logger.info("TEST FAILED testCaseId =" + testCaseId + "and testCase Description = " + testCaseDescription);
		}

		return testExecutionStatus;

	}

	// Verification after Cobrand Patch for non editable attributes - compare
	// against cobrand subscription
	public boolean verifyCobrandSubscriptionResponsePostPatch(String testCaseId, String testCaseDescription,
			Response cobSubscriptionResponse, String insightKey, String subscriptionLevels,
			FailureReason failureReason) {
		boolean insightExists = false, insightPresentInUser = false, insightPresentInCobrand = false,
				testExecutionStatus = true;
		JsonObject cobResponseObj = (JsonObject) jsonParser.parse(cobSubscriptionResponse.asString());
		JsonArray cobrandSubscriptionsArray = cobResponseObj.getAsJsonArray("cobrandSubscription");

		JsonObject insightObj, insightObjInCob = null, insightObjInUser = null;
		for (int i = 0; i < cobrandSubscriptionsArray.size(); i++) {
			insightObjInCob = (JsonObject) cobrandSubscriptionsArray.get(i);
			if (insightObjInCob.get("name").getAsString().equals(insightKey)) {// Insight name Verification
				insightPresentInCobrand = true;
				break;
			}
		}

		if (insightPresentInCobrand) {
			testExecutionStatus = verifyInsightDetailsOfUserSubscriptionPostPatch(insightObjInCob, insightObjInUser,
					insightKey, subscriptionLevels, failureReason);
		} else {
			if (!insightPresentInCobrand) {
				Assert.fail("Test Failed for " + insightKey
						+ " Insight name is Doesn't exists in the Get cobrand subscription API Response");
			}
			if (!insightPresentInUser) {
				Assert.fail("Test Failed for " + insightKey
						+ " Insight name is Doesn't exists in the Get User subscription API Response");
			}
		}
		if (!testExecutionStatus) {
			logger.info("TEST FAILED testCaseId =" + testCaseId + "and testCase Description = " + testCaseDescription);
		}

		return testExecutionStatus;

	}

	public boolean verifyInsightDetailsOfUserSubscriptionPostPatch(JsonObject insightObjInCob,
			JsonObject insightObjInUser, String insightKey, String subscriptionLevels, FailureReason failureReason) {
		boolean testExecutionStatus = true;
		// JsonArray containers = insightObj.getAsJsonArray("container");
		if (testExecutionStatus) {
			testExecutionStatus = insightObjInUser.get("triggerType").getAsString()
					.equals(insightObjInCob.get("triggerType").getAsString()) ? true
							: failTest(insightKey, insightObjInCob.get("triggerType").getAsString(),
									insightObjInUser.get("triggerType").getAsString(), failureReason);
		}
		if (testExecutionStatus) {
			testExecutionStatus = insightObjInUser.get("name").getAsString()
					.equals(insightObjInCob.get("name").getAsString()) ? true
							: failTest(insightKey, insightObjInCob.get("name").getAsString(),
									insightObjInUser.get("name").getAsString(), failureReason);
		}

		if (testExecutionStatus) {
			try {
				insightObjInUser.get("ruleExpression").getAsString();
				testExecutionStatus = failTest(insightKey, "Rule Expression Must not be present is User Subscriptions",
						insightObjInUser.get("ruleExpression").getAsString(), failureReason);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		if (testExecutionStatus) {
			JsonArray expectedContainersInCobArray = insightObjInCob.getAsJsonArray("container");
			JsonArray actualContainerInUserArray = insightObjInUser.getAsJsonArray("container");
			for (int i = 0; i < expectedContainersInCobArray.size(); i++) {
				if (expectedContainersInCobArray.size() == actualContainerInUserArray.size()) {
					testExecutionStatus = expectedContainersInCobArray.toString()
							.contains(actualContainerInUserArray.get(i).getAsString()) ? true
									: failTest(insightKey, expectedContainersInCobArray.get(i).getAsString(),
											actualContainerInUserArray.get(i).getAsString(), failureReason);
				} else {
					testExecutionStatus = expectedContainersInCobArray.size() == actualContainerInUserArray.size()
							? true
							: failTest(insightKey, "Expected Containers size =" + expectedContainersInCobArray.size(),
									"Actual Containers size =" + actualContainerInUserArray.size(), failureReason);
				}
			}
		}
		if (testExecutionStatus) {
			JsonArray keysInCobrandArr = insightObjInCob.getAsJsonArray("keys");
			JsonArray keysInUserArr = insightObjInUser.getAsJsonArray("keys");
			for (int i = 0; i < keysInCobrandArr.size(); i++) {
				if (keysInCobrandArr.size() == keysInUserArr.size()) {
					try {
						testExecutionStatus = keysInCobrandArr.get(i).equals(keysInUserArr.get(i)) ? true
								: failTest(insightKey, keysInCobrandArr.get(0).getAsString(),
										keysInUserArr.get(i).getAsString(), failureReason);
					} catch (Exception e) {
						testExecutionStatus = failTest(insightKey, keysInCobrandArr.get(0).toString(), null,
								failureReason);
					}
				}
			}
		}
		if (testExecutionStatus) {

			int totalKeys = insightObjInCob.getAsJsonArray("keys").size();
			JsonArray keysUserArray = insightObjInUser.getAsJsonArray("keys");
			testExecutionStatus = keysUserArray.size() == totalKeys ? true
					: failTest(insightKey, Integer.toString(totalKeys), Integer.toString(keysUserArray.size()),
							failureReason);
		}
		if (testExecutionStatus) {
			testExecutionStatus = insightObjInUser.get("description").getAsString()
					.equals(insightObjInCob.get("description").getAsString()) ? true
							: failTest(insightKey, insightObjInCob.get("description").getAsString(),
									insightObjInUser.get("description").getAsString(), failureReason);
		}
		if (testExecutionStatus) {
			JsonArray cobApplicableEntityArr = insightObjInCob.getAsJsonArray("applicableEntity");
			JsonArray userApplicableEntityArr = insightObjInUser.getAsJsonArray("applicableEntity");
			for (int i = 0; i < cobApplicableEntityArr.size(); i++) {
				if (cobApplicableEntityArr.size() == userApplicableEntityArr.size()) {
					testExecutionStatus = userApplicableEntityArr.get(i).getAsString()
							.equals(cobApplicableEntityArr.get(i).getAsString()) ? true
									: failTest(insightKey, cobApplicableEntityArr.get(i).getAsString(),
											userApplicableEntityArr.get(i).getAsString(), failureReason);
				}
			}
		}

		if (testExecutionStatus) {
			String[] allSubscriptionLevels = subscriptionLevels.split(",");
			JsonObject entityParameterCobObject = insightObjInCob.getAsJsonObject("entityParameter");
			JsonObject configMetaUserObject = insightObjInUser.getAsJsonObject("configMeta");
			for (int i = 0; i < entityParameterCobObject.size(); i++) {
				try {
					entityParameterCobObject.getAsJsonArray(allSubscriptionLevels[i]);
					configMetaUserObject.getAsJsonArray(allSubscriptionLevels[i]);
				} catch (Exception e) {
					testExecutionStatus = failTest(insightKey, allSubscriptionLevels[i],
							allSubscriptionLevels[i] + " Level is Not Found in User Subscription", failureReason);
				}
			}

		}

		return testExecutionStatus;
	}

	// POST PATCH - cobrand subscription verification - INSIGHT Level
	public boolean verifyInsightDetailsOfCobrandSubscriptionPostPatch(JsonObject insightCobObj, String insightKey,
			String title, String frequency, String duration, String isSubscribed, String levels,
			String thresholdDetails, FailureReason failureReason) {
		boolean testExecutionStatus = true;
		// JsonArray containers = insightObj.getAsJsonArray("container");
		if (!title.isEmpty()) {
			testExecutionStatus = insightCobObj.get("title").getAsString().equals(title) ? true
					: failTest(insightKey, title, insightCobObj.get("title").getAsString(), failureReason);
		}

		if (!levels.isEmpty()) {
			String[] allLevels = levels.split("\\s+");
			JsonObject entityParameterObject = insightCobObj.getAsJsonObject("entityParameter");
			for (int i = 0; i < allLevels.length; i++) {
				try {
					entityParameterObject.getAsJsonArray(allLevels[i]);
				} catch (Exception e) {
					testExecutionStatus = failTest(insightKey, allLevels[i], allLevels[i] + " Level is Not Found",
							failureReason);
				}
			}

		}
		if (!frequency.isEmpty() && !levels.isEmpty()) {
			JsonObject entityParameterObject = insightCobObj.getAsJsonObject("entityParameter");
			String[] expectedLevels = levels.split("\\s+");
			for (int i = 0; i < expectedLevels.length; i++) {
				JsonArray levelsArr = entityParameterObject.getAsJsonArray(expectedLevels[i]);
				// check if isSubscription = false
				JsonObject levelObject = (JsonObject) levelsArr.get(0);
				testExecutionStatus = levelObject.get("frequency").getAsString().equals(frequency) ? true
						: failTest(insightKey, frequency, levelObject.get("frequency").getAsString(), failureReason);
			}
		}
		if (!isSubscribed.isEmpty() && !levels.isEmpty()) {
			JsonObject entityParameterObject = insightCobObj.getAsJsonObject("entityParameter");
			String[] expectedLevels = levels.split("\\s+");
			String[] subscriptionsVals = isSubscribed.split("\\s+");
			for (int i = 0; i < expectedLevels.length; i++) {
				JsonArray levelsArr = entityParameterObject.getAsJsonArray(expectedLevels[i]);
				// check if isSubscription = false
				JsonObject levelObject = (JsonObject) levelsArr.get(0);
				testExecutionStatus = levelObject.get("isSubscribed").getAsBoolean() == Boolean
						.parseBoolean(subscriptionsVals[i]) ? true
								: failTest(insightKey, isSubscribed, levelObject.get("isSubscribed").getAsString(),
										failureReason);
			}
		}
		if (!thresholdDetails.isEmpty() && !levels.isEmpty()) {
							
			JsonObject entityParameterObject = insightCobObj.getAsJsonObject("entityParameter");
			String[] expectedLevels = levels.split("\\s+");
			String[] thresholdVals = thresholdDetails.split("\\s+");

			int numOfThresholdPerEntity  = (thresholdVals.length/expectedLevels.length);

			for (int i = 0; i < expectedLevels.length; i++) {
				JsonArray levelsArr = entityParameterObject.getAsJsonArray(expectedLevels[i]);
				// check if isSubscription = false
				JsonObject levelObject = (JsonObject) levelsArr.get(0);
				JsonArray thresholdArr = levelObject.getAsJsonArray("threshold");
				int index=0;
				for (int j = (i+1)*numOfThresholdPerEntity-numOfThresholdPerEntity; j < (i+1)*numOfThresholdPerEntity; j++) {
					JsonObject thresholdObj = (JsonObject) thresholdArr.get(index);
					String[] thresholdValues = thresholdVals[j].split(",");
					index++;
					testExecutionStatus = thresholdObj.get("name").getAsString().equals(thresholdValues[0]) ? true
							: failTest(insightKey, thresholdValues[0], thresholdObj.get("name").getAsString(),
									failureReason);
					System.out.println((int) Double.parseDouble(
								thresholdObj.get("value").getAsString()) );
					if (testExecutionStatus)
						testExecutionStatus = (int) Double.parseDouble(
								thresholdObj.get("value").getAsString()) == Integer.parseInt(thresholdValues[1])
										? true
										: failTest(insightKey,
												"Expected Threshold value should be matching with PATCH threshold values",
												thresholdObj.get("value").getAsString(), failureReason);
					if (testExecutionStatus)
						testExecutionStatus = thresholdObj.get("type").getAsString().equals(thresholdValues[2]) ? true
								: failTest(insightKey, thresholdValues[2], thresholdObj.get("type").getAsString(),
										failureReason);
				}
			}
		}
		return testExecutionStatus;
	}

	// POST PATCH - user subscription verification - INSIGHT Level
	public boolean verifyInsightDetailsOfUserSubscriptionPostPatch(JsonObject insightObjInUser, String insightKey,
			String insightTitle, String frequency, String duration, String isSubscribed, String subscriptionLevels,
			String thresholdDetails, FailureReason failureReason) {
		boolean testExecutionStatus = true;

		if (!insightTitle.isEmpty()) {
			testExecutionStatus = insightObjInUser.get("title").getAsString().equals(insightTitle) ? true
					: failTest(insightKey, insightTitle, insightObjInUser.get("title").getAsString(), failureReason);
		}
		if (!frequency.isEmpty() && !subscriptionLevels.isEmpty()) {
			JsonObject entityParameterObject = insightObjInUser.getAsJsonObject("entityParameter");
			String[] expectedLevels = subscriptionLevels.split("\\s+");
			String[] frequencies = frequency.split("\\s+");
			for (int i = 0; i < expectedLevels.length; i++) {
				JsonArray levelsArr = entityParameterObject.getAsJsonArray(expectedLevels[i]);
				// check if isSubscription = false
				JsonObject levelObject = (JsonObject) levelsArr.get(0);
				testExecutionStatus = levelObject.get("frequency").getAsString().equals(frequencies[i]) ? true
						: failTest(insightKey, frequencies[i], levelObject.get("frequency").getAsString(),
								failureReason);
			}
		}

		if (!isSubscribed.isEmpty() && !subscriptionLevels.isEmpty()) {
			JsonObject entityParameterObject = insightObjInUser.getAsJsonObject("entityParameter");
			String[] expectedLevels = subscriptionLevels.split("\\s+");
			String[] subscriptionsVals = isSubscribed.split("\\s+");
			for (int i = 0; i < expectedLevels.length; i++) {
				JsonArray levelsArr = entityParameterObject.getAsJsonArray(expectedLevels[i]);
				// check if isSubscription = false
				JsonObject levelObject = (JsonObject) levelsArr.get(0);
				testExecutionStatus = levelObject.get("isSubscribed").getAsBoolean() == Boolean
						.parseBoolean(subscriptionsVals[i]) ? true
								: failTest(insightKey, subscriptionsVals[i],
										levelObject.get("isSubscribed").getAsString(), failureReason);
			}
		}

		if (!duration.isEmpty() && !subscriptionLevels.isEmpty()) {
			JsonObject entityParameterObject = insightObjInUser.getAsJsonObject("entityParameter");
			String[] expectedLevels = subscriptionLevels.split("\\s+");
			String[] durations = duration.split("\\s+");
			for (int i = 0; i < expectedLevels.length; i++) {
				JsonArray levelsArr = entityParameterObject.getAsJsonArray(expectedLevels[i]);
				// check if isSubscription = false
				JsonObject levelObject = (JsonObject) levelsArr.get(0);
				testExecutionStatus = levelObject.get("duration").getAsString().equals(durations[i]) ? true
						: failTest(insightKey, durations[i], levelObject.get("duration").getAsString(), failureReason);
			}
		}
		if (!thresholdDetails.isEmpty() && !subscriptionLevels.isEmpty()) {
			JsonObject entityParameterObject = insightObjInUser.getAsJsonObject("entityParameter");
			String[] expectedLevels = subscriptionLevels.split("\\s+");
			for (int i = 0; i < expectedLevels.length; i++) {
				JsonArray levelsArr = entityParameterObject.getAsJsonArray(expectedLevels[i]);
				// check if isSubscription = false
				JsonObject levelObject = (JsonObject) levelsArr.get(0);
				JsonArray thresholdArr = levelObject.getAsJsonArray("threshold");
				String[] thresholdVals = thresholdDetails.split("\\s+");
				for (int j = 0; j < thresholdArr.size(); j++) {
					JsonObject thresholdObj = (JsonObject) thresholdArr.get(j);
					String[] thresholdValues = thresholdVals[j].split(",");
					testExecutionStatus = thresholdObj.get("name").getAsString().equals(thresholdValues[0]) ? true
							: failTest(insightKey, thresholdValues[0], thresholdObj.get("name").getAsString(),
									failureReason);
					if (testExecutionStatus)
						testExecutionStatus = (int) Double.parseDouble(
								thresholdObj.get("value").getAsString()) == (int) Double.parseDouble(thresholdValues[1])
										? true
										: failTest(insightKey, thresholdValues[1],
												thresholdObj.get("value").getAsString(), failureReason);
					if (testExecutionStatus)
						testExecutionStatus = thresholdObj.get("type").getAsString().equals(thresholdValues[2]) ? true
								: failTest(insightKey, thresholdValues[2], thresholdObj.get("type").getAsString(),
										failureReason);
				}
			}
		}
		return testExecutionStatus;
	}

	public String getMemIdOfUser(String loginUser) {
		DBHelper dbHelper = new DBHelper();
		Connection conn = dbHelper.getDBConnection();
		Statement st = null;
		ResultSet rs = null;
		String getMemIdFromMemTableQuery = "select *from mem where login_name = '" + loginUser + "'";
		logger.info("Query to get memId = " + getMemIdFromMemTableQuery);
		int memId = 0;
		try {
			st = conn.createStatement();
			rs = st.executeQuery(getMemIdFromMemTableQuery);
			while (rs.next()) {
				memId = rs.getInt("MEM_ID");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			dbHelper.closeConnectionStatementResultSet(conn, st, rs);
		}
		return Integer.toString(memId);
	}

	public void verifyEditedPatchUserSubscriptionDetails() {

		editedDetailsMap.forEach((key, value) -> System.out.println("[Key] : " + key + " [Value] : " + value));

	}

	public TestInfo printSummaryOfTests(Map<String, String> testSummary, String testDescription, TestInfo testInfo) {

		int passedCount = 0, failedCount = 0;
		for (int i = 0; i < 6; i++) {
			System.out.println();
		}

		logger.info("Test Summary Of " + testDescription);

		System.out.println();
		for (Map.Entry<String, String> entry : testSummary.entrySet()) {
			if (entry.getValue().equalsIgnoreCase("FAILED")) {
				System.err.println("Testcase Info -->" + entry.getKey() + ", TestResult =>" + entry.getValue());
				failedCount++;
			} else if (entry.getValue().equalsIgnoreCase("PASSED")) {
				System.out.println("Testcase Info -->" + entry.getKey() + ", TestResult =>" + entry.getValue());
				passedCount++;
			} else {
				System.err.println("Testcase Info -->" + entry.getKey() + ", TestResult =>" + entry.getValue());
			}
		}
		for (int i = 0; i < 3; i++) {
			System.out.println();
		}
		testInfo.setTestsPassed(String.valueOf(passedCount));
		testInfo.setTestsFailed(String.valueOf(failedCount));
		System.out.println("Total Tests Executed = " + (passedCount + failedCount) + "  Passed = " + passedCount
				+ "  Failed = " + failedCount);
		System.out.println();
		return testInfo;
	}

	public void failTest(String insightName, String expectedAttribute) {
		logger.info("Test FAILED for insight " + insightName + " reason attribute $$$ " + expectedAttribute
				+ "$$$values doesn't match");
	}

	public void failNotificationTest(String insightName, String expectedAttribute, FailureReason failureReason) {
		logger.info("Test FAILED for insight " + insightName + " reason attribute $$$ " + expectedAttribute
				+ " values doesn't match");
		failureReason.setFailureReason(expectedAttribute);
		notificationInsightsStatus = false;

	}

	public boolean failTest(String insightName, String expectedValue, String actualValue, FailureReason failureReason) {
		logger.info("Test FAILED for insight " + insightName + "   Expected = " + expectedValue + "   Actual = "
				+ actualValue);
		failureReason.setFailureReason("Expected " + expectedValue + ", Actual " + actualValue);
		return false;
	}

	public void deleteNotifications(Response userInsightsResponse, int insightsCount, InsightsDBUtils insightsDBUtils) throws IOException {
		if (insightsCount > 0) {
			JsonArray notificationArray = ((JsonObject) jsonParser.parse(userInsightsResponse.asString()))
					.getAsJsonArray("notification");
			for (int i = 0; i < notificationArray.size(); i++) {
				String notificationDocumentId = ((JsonObject) notificationArray.get(i)).get("id").getAsString();
				insightsDBUtils.deleteDocument(notificationDocumentId);
			}
		}
	}

	public String createAccountGroupJsonRequest(String accountGroupName,HashMap<String, String> entityIdsMap) {
		Random randomNum = new Random();
		JSONObject accountGroupObject = new JSONObject();
		JSONArray groupArray = new JSONArray();
		JSONObject groupArrayObject = new JSONObject();
		JSONArray accountsArray = new JSONArray();
		JSONObject accountIdObject = new JSONObject();
		accountIdObject.put("id", entityIdsMap.get("BankCheckingAccount"));
		accountsArray.add(accountIdObject);
		groupArrayObject.put("name", accountGroupName);
		groupArrayObject.put("account", accountsArray);
		groupArray.add(groupArrayObject);
		accountGroupObject.put("group", groupArray);
		return accountGroupObject.toString();
	}
	
	public String createAccountGroupJsonRequest(String accountGroupName,HashMap<String, String> entityIdsMap,String accountName) {
		Random randomNum = new Random();
		JSONObject accountGroupObject = new JSONObject();
		JSONArray groupArray = new JSONArray();
		JSONObject groupArrayObject = new JSONObject();
		JSONArray accountsArray = new JSONArray();
		JSONObject accountIdObject = new JSONObject();
		accountIdObject.put("id", entityIdsMap.get(accountName));
		accountsArray.add(accountIdObject);
		groupArrayObject.put("name", accountGroupName);
		groupArrayObject.put("account", accountsArray);
		groupArray.add(groupArrayObject);
		accountGroupObject.put("group", groupArray);
		return accountGroupObject.toString();
	}

	public String createAccountGroup(String accountGroupName,EnvSession sessionObj, HashMap<String, String> entityIdsMap) {

		// create Account Group
		String accountGroupId;
		HttpMethodParameters httpMethodParameters = HttpMethodParameters.builder().build();
		String createGroupParam = createAccountGroupJsonRequest(accountGroupName,entityIdsMap);
		httpMethodParameters.setBodyParams(createGroupParam);
		Response addAccountGroupResponse = groupUtils.createGroup(httpMethodParameters, sessionObj);
		accountGroupId = ((JsonObject) jsonParser.parse(addAccountGroupResponse.asString()).getAsJsonObject())
				.getAsJsonObject("group").get("id").toString();

		return accountGroupId;
	}
	
	public String createAccountGroup(String accountGroupName,EnvSession sessionObj, HashMap<String, String> entityIdsMap,String accountName) {

		// create Account Group
		String accountGroupId;
		HttpMethodParameters httpMethodParameters = HttpMethodParameters.builder().build();
		String createGroupParam = createAccountGroupJsonRequest(accountGroupName,entityIdsMap,accountName);
		httpMethodParameters.setBodyParams(createGroupParam);
		Response addAccountGroupResponse = groupUtils.createGroup(httpMethodParameters, sessionObj);
		accountGroupId = ((JsonObject) jsonParser.parse(addAccountGroupResponse.asString()).getAsJsonObject())
				.getAsJsonObject("group").get("id").toString();

		return accountGroupId;
	}


	public HttpMethodParameters getHttpParams(String requestType, String loginUser, String insightName, String jwtToken,
			String envUser, String entityKey, String entityValue, HashMap<String, String> entityIdsMap)
			throws IOException {
		Date date = new Date();
		Configuration configurationObj = Configuration.getInstance();
		String apiVersion = configurationObj.getApiVersion();
		String environment = configurationObj.getEnv();
		String todaysDate= new SimpleDateFormat("yyyy-MM-dd").format(date);
		HttpMethodParameters httpParams = HttpMethodParameters.builder().build();
		if(environment.equalsIgnoreCase("NonDocker")) {
			DEVELOPER_URL = prop.readPropertiesFile().getProperty("NON_DOCKER_DEVELOPER_URL");
			INVOKER_URL = prop.readPropertiesFile().getProperty("NON_DOCKER_INVOKER_URL");
			TASK_BASE_URI = prop.readPropertiesFile().getProperty("NON_DOCKER_TASK_BASE_URI");
		}if(environment.equalsIgnoreCase("Docker")) {
			DEVELOPER_URL = prop.readPropertiesFile().getProperty("DOCKER_DEVELOPER_URL");
			INVOKER_URL = prop.readPropertiesFile().getProperty("DOCKER_INVOKER_URL");
			TASK_BASE_URI = prop.readPropertiesFile().getProperty("NON_DOCKER_TASK_BASE_URI");
		}
		HashMap<String, Object> notificationsQueryParam = new HashMap<>();
		HashMap<String, String> addHeaders = new HashMap<String, String>();
		List<String> additionalBaseUriList = new ArrayList<String>();
		if (requestType.equals("invokerRequest")) {
			String loginUserId =getMemIdOfUser(loginUser);
			HashMap<String,ArrayList<Long>> containersCacheItemsMap = dbUtil.getContainersCacheItemIds(loginUserId);				
		   
			for (Map.Entry<String,ArrayList<Long>> entry : containersCacheItemsMap.entrySet()) {
				String container = entry.getKey();				
				ArrayList<Long> cacheItemsList = entry.getValue();
				String invokerPayLoad = constructInvokerPayLoad(entityIdsMap, loginUserId, insightName,envUser,container,cacheItemsList);
				addHeaders = new HashMap<String, String>();
				addHeaders.put("Content-Type", "application/json");
				httpParams.setAddHeaders(addHeaders);
				httpParams.setHeader("Authorization", jwtToken);
				additionalBaseUriList.add(INVOKER_URL);
				httpParams.setAdditionalBaseURIs(additionalBaseUriList);
				httpParams.setBodyParams(invokerPayLoad);
				Response postInvokerResponse = invokerUtils.callInvoker(httpParams,null);
				Assert.assertEquals(postInvokerResponse.getStatusCode(), Integer.parseInt("201"));
				additionalBaseUriList.clear();
			}

		}
		if (requestType.equals("createViewRequest")) {
			httpParams.setHeader("Authorization", jwtToken);
			additionalBaseUriList.add(DEVELOPER_URL);
			httpParams.setAdditionalBaseURIs(additionalBaseUriList);
		}
		if (requestType.equals("notificationsRequest")) {
			notificationsQueryParam.put("name", insightName);
			if (entityKey != null)
				notificationsQueryParam.put(entityKey, entityValue);
			notificationsQueryParam.put("fromDate", todaysDate);
			notificationsQueryParam.put("toDate", todaysDate);
			addHeaders.put("Authorization", jwtToken);
			httpParams.setAddHeaders(addHeaders);
			httpParams.setQueryParams(notificationsQueryParam);
			additionalBaseUriList = new ArrayList<String>();
			additionalBaseUriList.add(DEVELOPER_URL);
			httpParams.setAdditionalBaseURIs(additionalBaseUriList);
		}
		if (requestType.equals("userSubscriptionRequest")) {
			httpParams.setHeader("Authorization", jwtToken);
			additionalBaseUriList.add(DEVELOPER_URL);
			httpParams.setAdditionalBaseURIs(additionalBaseUriList);
		}
		if (requestType.equals("cobrandSubscriptionRequest")) {
			httpParams.setHeader("Authorization", jwtToken);
			additionalBaseUriList.add(DEVELOPER_URL);
			httpParams.setAdditionalBaseURIs(additionalBaseUriList);
		}
		if (requestType.equals("getUserSubscriptionRequest")) {
			notificationsQueryParam.put("name", insightName);
			httpParams.setQueryParams(notificationsQueryParam);
			httpParams.setHeader("Authorization", jwtToken);
			additionalBaseUriList.add(DEVELOPER_URL);
			httpParams.setAdditionalBaseURIs(additionalBaseUriList);
		}
		if (requestType.equals("categoriesMerchantsRequest")) {
			httpParams.setHeader("Authorization", jwtToken);
			additionalBaseUriList.add(TASK_BASE_URI);
			httpParams.setAdditionalBaseURIs(additionalBaseUriList);
		}
		return httpParams;
	}

	public String getBoardId(HashMap<String, String> entityIdsMap, String noOfBoards, String jwtToken)
			throws IOException {
		String viewIdResponse = "";
		for (int i = 0; i < Integer.parseInt(noOfBoards); i++) {
			HttpMethodParameters httpParams = HttpMethodParameters.builder().build();
			String boardRequest = "";
			if (i == 0) {
				boardRequest = boardHelper.createBoardJsonBody("Insight View", "Insight View", "INCOME", 1000.00, "USD",
						"MONTHLY_RECURRING", "Board For Amount range", "0,-8,0", "0,2,0", 10.00, 10000.00, "EMPTY");
			} else if (i == 1) {
				boardRequest = boardHelper.createBoardJsonBody("Insight View", "Insight View", "INCOME", 1000.00, "USD",
						"MONTHLY_RECURRING", "Board For Amount range", "0,-8,0", "0,2,0", 10.00, 10000.00, "BILLERS");
			} else if (i == 2) {
				boardRequest = boardHelper.createBoardJsonBody("Insight View", "Insight View", "INCOME", 1000.00, "USD",
						"MONTHLY_RECURRING", "Board For Amount range", "0,-8,0", "0,2,0", 10.00, 10000.00,
						"SUBSCRIPTION");
			}
			httpParams = getHttpParams("createViewRequest", null, null, jwtToken, null, null, null, null);
			httpParams.setBodyParams(boardRequest);
			Response response = boardUtils.createBoardForInisghts(httpParams, null);

			viewIdResponse = response.jsonPath().get("view[0].id");
			entityIdsMap.put("ViewId", viewIdResponse);
		}
		return viewIdResponse;
	}

	public String getBoardId(String jwtToken, String bodyRequest) throws IOException {
		String viewIdResponse = "";
		HttpMethodParameters httpParams = HttpMethodParameters.builder().build();
		httpParams = getHttpParams("createViewRequest", null, null, jwtToken, null, null, null, null);
		httpParams.setBodyParams(bodyRequest);
		Response response = boardUtils.createBoardForInisghts(httpParams, null);
		viewIdResponse = response.jsonPath().get("view[0].id");
		return viewIdResponse;
	}
	
	
}
