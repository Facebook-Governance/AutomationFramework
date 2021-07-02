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

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.Map.Entry;

import org.apache.commons.lang.RandomStringUtils;
import org.apache.http.params.HttpParamConfig;
import org.eclipse.jetty.http.HttpStatus;
import com.yodlee.insights.subscriptions.pojo.EntityDetail;
import com.yodlee.insights.subscriptions.pojo.Subscription;
import com.yodlee.insights.subscriptions.pojo.Subscriptions;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonIOException;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;
import com.yodlee.DBHelper;
import com.yodlee.insights.views.pojo.CommonEntity;
import com.yodlee.insights.yodleeApi.utils.FailureReason;
import com.yodlee.insights.yodleeApi.utils.InsightsHelper;
import com.yodlee.insights.yodleeApi.utils.TestInfo;
import com.yodlee.insights.views.pojo.*;
import com.yodlee.yodleeApi.constants.Constants;
import com.yodlee.yodleeApi.database.DbQueries;
import com.yodlee.yodleeApi.helper.AccountsHelper;
import com.yodlee.yodleeApi.helper.BoardHelper;
import com.yodlee.yodleeApi.helper.BudgetHelper;
import com.yodlee.yodleeApi.helper.DbHelper;
import com.yodlee.yodleeApi.helper.SaveForGoalHelper;
import com.yodlee.yodleeApi.pojo.EnvSession;
import com.yodlee.yodleeApi.pojo.HttpMethodParameters;
import com.yodlee.yodleeApi.utils.apiUtils.AccountUtils;
import com.yodlee.yodleeApi.utils.apiUtils.GroupUtils;
import com.yodlee.yodleeApi.utils.apiUtils.UserUtils;
import com.yodlee.yodleeApi.utils.apiUtils.SFGUtils;
import com.yodlee.yodleeApi.utils.apiUtils.TransactionUtils;
import com.yodlee.yodleeApi.utils.apiUtils.InsightUtils;
import io.restassured.response.Response;
import junit.framework.Assert;
import org.json.JSONObject;
import org.json.simple.JSONArray;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ValidateCustomerAndUserSubscriptions {

	protected Logger logger = LoggerFactory.getLogger(ValidateCustomerAndUserSubscriptions.class);
	AccountUtils accountUtils = new AccountUtils();
	TransactionUtils transactionUtils = new TransactionUtils();
	HashMap<String, String> itemAccountsList = new HashMap<String, String>();
	HashMap<String,ArrayList> itemAccountsToTxnsMap = new HashMap<String,ArrayList>();	
	HashMap<String, String> transactionList = new HashMap<String, String>();
	JsonParser jsonParser = new JsonParser();
	InsightsHelper insightsHelper = new InsightsHelper();
	UserUtils userUtils = new UserUtils();
	Response getCobSubResponse, getUserSubResponse;
	SFGUtils sfgUtils = new SFGUtils();
	ArrayList<String> levelsList;
	SaveForGoalHelper saveForGoalHelper = new SaveForGoalHelper();
	Date curentDate = new Date();
	boolean notificationInsightsStatus = true;
	DBHelper dbUtils = new DBHelper();
	DbHelper dbHelper = new DbHelper();
	GroupUtils groupUtils = new GroupUtils();
	BudgetHelper budgetHelper = new BudgetHelper();
	JsonObject inisghtsKeysObjects = null;
	BoardHelper boardHelper = new BoardHelper();
	AccountsHelper accountsHelper = new AccountsHelper(); 
	LocalDateTime today = LocalDateTime.now();
	InsightUtils insightUtils = new InsightUtils();
	DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd"); 
	String insightsEntites ="account,goal,view,accoungGroup";
	List<String> supportingEntiesList = new ArrayList<String>(Arrays.asList(insightsEntites.split(",")));


	String[] negativeTestScenarios = {"negTestScenario55","negTestScenario54","negTestScenario53","negTestScenario52","negTestScenario51","negTestScenario50","negTestScenario49","negTestScenario48","negTestScenario47","negTestScenario46","negTestScenario45","negTestScenario44","negTestScenario43","negTestScenario42","negTestScenario41","negTestScenario39","negTestScenario38","negTestScenario37","negTestScenario36","negTestScenario35","negTestScenario33","negTestScenario34","negTestScenario29","negTestScenario32","negTestScenario30","negTestScenario31","negTestScenario21","negTestScenario27","negTestScenario28","negTestScenario25","negTestScenario24","negTestScenario23",
			"negTestScenario22","negTestScenario9","negTestScenario8","negTestScenario10","negTestScenario17","negTestScenario19","negTestScenario18","negTestScenario13","negTestScenario11","negTestScenario14","negTestScenario16","negTestScenario6","negTestScenario7"};
	List<String> negativeScenarioList = Arrays.asList(negativeTestScenarios);

	public ValidateCustomerAndUserSubscriptions() {
		try {
			inisghtsKeysObjects = (JsonObject) jsonParser.parse(new FileReader(System.getProperty("user.dir")
					+ "\\src\\test\\resources\\TestData\\CSVFiles\\Insights\\ExpectedKeys\\ExpectedInsightsKeys.json"));
		} catch (JsonIOException | JsonSyntaxException | FileNotFoundException e) {			
			e.printStackTrace();
		}
	}

	
	public String  getCobrandSubscriptionResponseVerification(Response cobSubscriptionResponse,boolean testExecutionStatus,Map<String ,String>  testSummary, String testCaseId,String  testCaseDescription,String  triggerType,String  InsightKey,String  insightName,String  title,String  containerSupported,
			JsonArray keys,String totalNumberOfKeys,String description, String applicableEntity,String  levels,String  isSubscribed,String  frequency,String thresholdDetails,String checkRuleExpression,int expectedStatus,FailureReason failureReason,String validateKeys,TestInfo testInfo) {

		String returnStatus;
		System.out.println(cobSubscriptionResponse.asString());
		if(cobSubscriptionResponse.getStatusCode()!= expectedStatus) {
			testExecutionStatus = false;
		}
		testExecutionStatus= insightsHelper.verifyCobrandSubscriptionResponse(cobSubscriptionResponse,testCaseId, testCaseDescription, triggerType, InsightKey, insightName, title, containerSupported,keys,totalNumberOfKeys,description, applicableEntity, levels, isSubscribed, frequency,thresholdDetails,checkRuleExpression,failureReason,validateKeys,testInfo);
		if(testExecutionStatus) {
			testSummary.put(testCaseId+"_"+testCaseDescription , "PASSED");
			returnStatus="TRUE";
		}else {
			testSummary.put(testCaseId+"_"+testCaseDescription , "FAILED");
			returnStatus="FALSE";
			//Assert.fail();
		}
		return returnStatus;
	}
	
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
}