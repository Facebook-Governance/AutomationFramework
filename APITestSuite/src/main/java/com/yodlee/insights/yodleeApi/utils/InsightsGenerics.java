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
package com.yodlee.insights.yodleeApi.utils;

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
import java.util.Map.Entry;
import java.util.Random;
import java.util.Set;

import org.apache.commons.lang.RandomStringUtils;
import org.eclipse.jetty.http.HttpStatus;
import org.json.JSONObject;
import org.json.simple.JSONArray;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonIOException;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;
import com.yodlee.DBHelper;
import com.yodlee.insights.subscriptions.pojo.EntityDetail;
import com.yodlee.insights.subscriptions.pojo.Subscription;
import com.yodlee.insights.subscriptions.pojo.Subscriptions;
import com.yodlee.insights.views.pojo.Account;
import com.yodlee.insights.views.pojo.Address;
import com.yodlee.insights.views.pojo.CommonEntity;
import com.yodlee.insights.views.pojo.HomeValue;
import com.yodlee.insights.views.pojo.RealEstateAccount;
import com.yodlee.yodleeApi.constants.Constants;
import com.yodlee.yodleeApi.helper.AccountsHelper;
import com.yodlee.yodleeApi.helper.BoardHelper;
import com.yodlee.yodleeApi.helper.BudgetHelper;
import com.yodlee.yodleeApi.helper.DbHelper;
import com.yodlee.yodleeApi.helper.SaveForGoalHelper;
import com.yodlee.yodleeApi.pojo.EnvSession;
import com.yodlee.yodleeApi.pojo.HttpMethodParameters;
import com.yodlee.yodleeApi.utils.apiUtils.AccountUtils;
import com.yodlee.yodleeApi.utils.apiUtils.GroupUtils;
import com.yodlee.yodleeApi.utils.apiUtils.InsightUtils;
import com.yodlee.yodleeApi.utils.apiUtils.InsightsUtilV1;
import com.yodlee.yodleeApi.utils.apiUtils.SFGUtils;
import com.yodlee.yodleeApi.utils.apiUtils.TransactionUtils;
import com.yodlee.yodleeApi.utils.apiUtils.UserUtils;

import io.restassured.response.Response;
import junit.framework.Assert;

public class InsightsGenerics {

	AccountUtils accountUtils = new AccountUtils();
	TransactionUtils transactionUtils = new TransactionUtils();
	HashMap<String, String> itemAccountsList = new HashMap<String, String>();
	HashMap<String, ArrayList> itemAccountsToTxnsMap = new HashMap<String, ArrayList>();
	HashMap<String, String> transactionList = new HashMap<String, String>();
	JsonParser jsonParser = new JsonParser();
	InsightsHelper insightsHelper = new InsightsHelper();
	UserUtils userUtils = new UserUtils();
	SFGUtils sfgUtils = new SFGUtils();
	SaveForGoalHelper saveForGoalHelper = new SaveForGoalHelper();
	Date curentDate = new Date();
	DBHelper dbUtils = new DBHelper();
	DbHelper dbHelper = new DbHelper();
	GroupUtils groupUtils = new GroupUtils();
	BudgetHelper budgetHelper = new BudgetHelper();
	InsightsUtilV1 insightsUtilsV2 = new InsightsUtilV1();
	JsonObject inisghtsKeysObjects = null;
	BoardHelper boardHelper = new BoardHelper();
	AccountsHelper accountsHelper = new AccountsHelper();
	LocalDateTime today = LocalDateTime.now();
	InsightUtils insightUtils = new InsightUtils();
	DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd");
	String insightsEntites = "account,goal,view,accoungGroup";
	List<String> supportingEntiesList = new ArrayList<String>(Arrays.asList(insightsEntites.split(",")));

	String[] negativeTestScenarios = { "editNotSupportedEntity", "EditThresholdTypeInvalidAMOUNT",
			"EditThresholdNameInvalidMINIMUM", "EditThresholdAmountRangeMinAndMax", "EditDescription", "EditContainer",
			"EditTriggerType", "EditApplicableEntity", "EditInsightType", "negTestScenario55", "negTestScenario54",
			"negTestScenario53", "negTestScenario52", "negTestScenario51", "negTestScenario50", "negTestScenario49",
			"negTestScenario48", "negTestScenario47", "negTestScenario46", "negTestScenario45", "negTestScenario44",
			"negTestScenario43", "negTestScenario42", "negTestScenario41", "negTestScenario39", "negTestScenario38",
			"negTestScenario37", "negTestScenario36", "negTestScenario35", "negTestScenario33", "negTestScenario34",
			"negTestScenario29", "negTestScenario32", "negTestScenario30", "negTestScenario31", "negTestScenario21",
			"negTestScenario27", "negTestScenario28", "negTestScenario25", "negTestScenario24", "negTestScenario23",
			"negTestScenario22", "negTestScenario9", "negTestScenario8", "negTestScenario10", "negTestScenario17",
			"negTestScenario19", "negTestScenario18", "negTestScenario13", "negTestScenario11", "negTestScenario14",
			"negTestScenario16", "negTestScenario6", "negTestScenario7", "EditInsightType" };
	List<String> negativeScenarioList = Arrays.asList(negativeTestScenarios);

	public InsightsGenerics() {
		try {
			inisghtsKeysObjects = (JsonObject) jsonParser.parse(new FileReader(System.getProperty("user.dir")
					+ "\\src\\test\\resources\\TestData\\CSVFiles\\Insights\\ExpectedKeys\\ExpectedInsightsKeys.json"));
		} catch (JsonIOException | JsonSyntaxException | FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	public HashMap<String, String> getItemAccounts(EnvSession sessionObj, String container) {

		Response accountsResponse = accountUtils.getAllAccounts(sessionObj);
		JsonObject accountsResponseObj = (JsonObject) jsonParser.parse(accountsResponse.asString());
		JsonArray accountsArray = accountsResponseObj.getAsJsonArray("account");
		for (int i = 0; i < accountsArray.size(); i++) {
			JsonObject accountObj = (JsonObject) accountsArray.get(i);
			String accountName = accountObj.get("accountName").getAsString();
			String itemAccountId = accountObj.get("id").getAsString();
			itemAccountsList.put(accountName, itemAccountId);
		}
		return itemAccountsList;
	}

	public static ArrayList<Long> getItemAccountsByContainer(EnvSession sessionObj, String containerName) {
		ArrayList<Long> itemAccountsPerContainerList = new ArrayList<Long>();
		try {
			if (containerName.equalsIgnoreCase("card"))
				containerName = "creditCard";
			AccountUtils accountUtils = new AccountUtils();
			JsonParser jsonParser = new JsonParser();
			HttpMethodParameters httpParams = HttpMethodParameters.builder().build();
			HashMap<String, Object> accountsQueryParam = new HashMap<>();
			accountsQueryParam.put("container", containerName);
			httpParams.setQueryParams(accountsQueryParam);
			Response accountsResponse = accountUtils.getAccounts(httpParams, sessionObj);
			JsonObject accountsResponseObj = (JsonObject) jsonParser.parse(accountsResponse.asString());
			JsonArray accountsArray = accountsResponseObj.getAsJsonArray("account");
			for (int i = 0; i < accountsArray.size(); i++) {
				JsonObject accountObj = (JsonObject) accountsArray.get(i);
				Long itemAccountId = accountObj.get("id").getAsLong();
				itemAccountsPerContainerList.add(itemAccountId);
			}
		} catch (NullPointerException e) {
			System.out.println("Account is not avaialbe and might have been closed.");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return itemAccountsPerContainerList;
	}

	public HashMap<String, String> addRealEstateAccount(EnvSession sessionObj, String realEstateAccType) {
		HttpMethodParameters httpParams = HttpMethodParameters.builder().build();
		HashMap<String, String> addHeaders = new HashMap<String, String>();
		Gson gson = new Gson();
		RealEstateAccount realEstateAccount = new RealEstateAccount();
		Account account = new Account();
		Address address = new Address();
		HomeValue homeValue = new HomeValue();
		if (realEstateAccType.equals("SYSTEM")) {
			account.setAccountName("BNP Account");
			account.setNickName("Flood Cash");
			address.setStreet("1380 Utah St");
			address.setCity("San Francisco");
			address.setState("CA");
			address.setZip("94110");
			account.setAddress(address);
		}
		if (realEstateAccType.equals("MANUAL")) {
			account.setAccountName("Manual Real Estate Account ");
			account.setNickName("Flood Cash");
			account.setHomeValue(homeValue);
		}
		account.setValuationType(realEstateAccType);
		account.setMemo("Memo : This is " + realEstateAccType + " Generated RealEstate Account");
		realEstateAccount.setAccount(account);
		String realEstateAccPayload = gson.toJson(realEstateAccount);
		httpParams.setBodyParams(realEstateAccPayload);
		httpParams.setHeader("Api-Version", "1.1");
		Response realEstateAccResponse = accountUtils.addSmartZipAccount(httpParams, sessionObj);
		JsonObject accountsResponseObj = (JsonObject) jsonParser.parse(realEstateAccResponse.asString());
		JsonArray accountsArray = accountsResponseObj.getAsJsonArray("account");
		JsonObject accountObj = (JsonObject) accountsArray.get(0);
		System.out.println("Real Estate Account Id = " + accountObj.get("id").getAsLong());
		return itemAccountsList;
	}

	@SuppressWarnings("deprecation")
	public HashMap<String, ArrayList> getTransactionsMapWithItemAccounts(EnvSession sessionObj) {
		HttpMethodParameters transactionHttpParams = HttpMethodParameters.builder().build();
		HashMap<String, Object> txnsQueryParams = new HashMap<>();
		ArrayList<Integer> transactionsList = null;
		Response accountsResponse = accountUtils.getAllAccounts(sessionObj);
		JsonObject accountsResponseObj = (JsonObject) jsonParser.parse(accountsResponse.asString());
		JsonArray accountsArray = accountsResponseObj.getAsJsonArray("account");
		for (int i = 0; i < accountsArray.size(); i++) {
			LocalDateTime fromDate = today.minusDays(90);
			JsonObject accountObj = (JsonObject) accountsArray.get(i);
			String itemAccountId = accountObj.get("id").getAsString();
			txnsQueryParams.put("sourceType", "AGGREGATED");
			txnsQueryParams.put("accountId", itemAccountId);
			txnsQueryParams.put("fromDate", fromDate.format(format));
			txnsQueryParams.put("toDate", today.format(format));
			transactionHttpParams.setQueryParams(txnsQueryParams);
			Response transactionsResponse = transactionUtils.getAllTransactions(transactionHttpParams, sessionObj);
			JsonObject transactionsResponseObj = (JsonObject) jsonParser.parse(transactionsResponse.asString());
			JsonArray transactionsArray = transactionsResponseObj.getAsJsonArray("transaction");
			if (transactionsArray != null) {
				transactionsList = new ArrayList<Integer>();
				for (int j = 0; j < transactionsArray.size(); j++) {
					JsonObject transactionObj = (JsonObject) transactionsArray.get(j);
					Integer transactionId = transactionObj.get("id").getAsInt();
					transactionsList.add(transactionId.intValue());
				}
				itemAccountsToTxnsMap.put(itemAccountId, transactionsList);
			} else {
				itemAccountsToTxnsMap.put(itemAccountId, new ArrayList<>());
			}
		}
		return itemAccountsToTxnsMap;
	}

	public HashMap<Integer, String> parseRecommendedViews(Response recommendedViewsResponse) {
		HashMap<Integer, String> recommendedViewsMap = new HashMap<Integer, String>();
		Gson gson = new Gson();
		JsonObject recommendedViewsObject = (JsonObject) jsonParser.parse(recommendedViewsResponse.asString());
		JsonArray recommendedViewsArray = recommendedViewsObject.getAsJsonArray("recommendation");
		for (int i = 0; i < recommendedViewsArray.size(); i++) {
			JsonObject recommnededViewObj = (JsonObject) recommendedViewsArray.get(i);
			System.out.println("Recommended View Id " + recommnededViewObj.get("id").getAsInt());
			JsonArray recommendedViewObjArr = recommnededViewObj.getAsJsonArray("view");
			String viewJson = gson.toJson(recommendedViewObjArr);
			String viewsRequestPayload = "{\"view\":" + viewJson + "}";
			recommendedViewsMap.put(recommnededViewObj.get("id").getAsInt(), viewsRequestPayload);
		}
		return recommendedViewsMap;
	}

	public String[] getTransactionIds(EnvSession sessionObj, HttpMethodParameters httpParams,
			String[] itemAccountIdArray, int numberOfTransactions) {
		Set<String> settransactionIds = new HashSet<>();
		Response transactionsResponse = transactionUtils.getAllTransactions(httpParams, sessionObj);
		JsonObject transactionsResponseObj = (JsonObject) jsonParser.parse(transactionsResponse.asString());

		JsonArray transactionsArray = transactionsResponseObj.getAsJsonArray("transaction");
		for (int i = 0; i < transactionsArray.size(); i++) {
			JsonObject transactionObj = (JsonObject) transactionsArray.get(i);
			String itemAccountId = transactionObj.get("accountId").getAsString();
			String transactionId = transactionObj.get("id").getAsString();
			transactionList.put(transactionId, itemAccountId);
		}
		for (int i = 0; i < itemAccountIdArray.length; i++) {
			for (Entry<String, String> entry : transactionList.entrySet()) {
				if (entry.getValue().equals(itemAccountIdArray[i])) {
					settransactionIds.add(entry.getKey());
				}
			}
		}
		String[] inputTransactionIds = settransactionIds.toArray(new String[0]);
		String[] transactionIds = new String[numberOfTransactions];
		for (int i = 0; i < numberOfTransactions; i++)
			transactionIds[i] = inputTransactionIds[i];
		return transactionIds;
	}

	public Response updateAccountStatus(EnvSession sessionObj, String accountStatus, String itemAccountId) {
		String jsonBody = accountsHelper.createJsonForUpdateAggregatedAccount("TEST", accountStatus, "", "TEST");
		HttpMethodParameters httpParams = HttpMethodParameters.builder().build();
		httpParams.setBodyParams(jsonBody);
		Map<String, Object> pathParams = new HashMap<>();
		pathParams.put(Constants.ACCOUNT_ID_FILTER, itemAccountId);
		httpParams.setPathParams(pathParams);
		Response updateAccountResponse = accountUtils.updateManualOrAggregateAccount(httpParams, sessionObj);
		System.out.println("Response for updateAggregateAccount :::");
		return updateAccountResponse;
	}

	public HashMap<String, String> createGoals(EnvSession sessionObj, HashMap<String, String> entityIdsMap,
			String goalsName, String destinationAccounts, String goalStatus, String actualSavings, String accountStatus,
			String envCobrand) throws SQLException {
		HashMap<String, String> goalsMap = new HashMap<String, String>();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd ");
		String startDate = formatter.format(curentDate);
		startDate = startDate.concat("23:51:08");
		curentDate.setMonth(15);
		String endDate = formatter.format(curentDate);
		String[] goals = goalsName.split(",");
		String[] itemAccounts = destinationAccounts.split(",");
		String goalName = null;
		for (int i = 0; i < goals.length; i++) {
			Random randomNum = new Random();
			// goalName=goals[i].concat(String.valueOf(100 + randomNum.nextInt(1000)));
			goalName = goals[i];
			String createGoalBodyParam = null;
			if (envCobrand.equalsIgnoreCase("wellnessCobrand")) {
				createGoalBodyParam = saveForGoalHelper.createGoalJson(goalName, "10009593", startDate, endDate, "1000",
						"USD", "13000", "USD", "MONTHLY", "NOT_STARTED", "NONE", "false", "");
			}
			if (envCobrand.equalsIgnoreCase("wellnessCobrandV2")) {
				createGoalBodyParam = saveForGoalHelper.createGoalJson(goalName, "10008760", startDate, endDate, "1000",
						"USD", "13000", "USD", "MONTHLY", "NOT_STARTED", "NONE", "false", "");
			}
			if (envCobrand.equalsIgnoreCase("senseCobrand")) {
				createGoalBodyParam = saveForGoalHelper.createGoalJson(goalName, "7", startDate, endDate, "1000", "USD",
						"13000", "USD", "MONTHLY", "NOT_STARTED", "NONE", "false", "");
			}
			if (envCobrand.equalsIgnoreCase("senseCobrandV2")) {
				createGoalBodyParam = saveForGoalHelper.createGoalJson(goalName, "5", startDate, endDate, "1000", "USD",
						"13000", "USD", "MONTHLY", "NOT_STARTED", "NONE", "false", "");
			}
			HttpMethodParameters httpMethodParameters = HttpMethodParameters.builder().build();
			httpMethodParameters.setBodyParams(createGoalBodyParam);
			Response goalsResponse = sfgUtils.createGoal(httpMethodParameters, sessionObj);
			if (goalsResponse.getStatusCode() == 201) {
				System.out.println("Goal Created successfully");
			} else {
				System.err.print("Goal Creation FAILED");
				Assert.fail();
			}
			String goalId = jsonParser.parse(goalsResponse.asString()).getAsJsonObject().getAsJsonArray("goal").get(0)
					.getAsJsonObject().get("id").getAsString();
			entityIdsMap.put(goalName, goalId);
			boolean updateFlag = false;
			String changeGoalsStatus = goalStatus.split(",")[i];
			if (changeGoalsStatus.equals("COMPLETED") || changeGoalsStatus.equals("REALIZED")
					|| changeGoalsStatus.equals("IN_PROGRESS")) {
				if (changeGoalsStatus.equals("COMPLETED")) {
					saveForGoalHelper.updateGoal(goalId, sessionObj, "IN_PROGRESS");
					updateFlag = true;
				} else {
					saveForGoalHelper.updateGoal(goalId, sessionObj, goalStatus.split(",")[i]);
				}
			}

			String destAccountBodyParam = saveForGoalHelper.createDestinationAccount(
					Integer.parseInt(itemAccountsList.get(itemAccounts[i])), Integer.parseInt("1000"), "USD");
			HttpMethodParameters httpMethodParameters1 = HttpMethodParameters.builder().build();
			Map<String, Object> createDestAccntPathParam = new HashMap<>();
			createDestAccntPathParam.put("goalId", goalId);
			httpMethodParameters1.setPathParams(createDestAccntPathParam);
			httpMethodParameters1.setBodyParams(destAccountBodyParam);
			Response destAccountResponse = sfgUtils.createDestinationAccount(httpMethodParameters1, sessionObj);
			if (goalsResponse.getStatusCode() == 201) {
				System.out.println("Added Destination Account successfully");
			} else {
				System.err.print("Adding Destination Account FAILED");
				Assert.fail();
			}
			goalsMap.put(goals[i], goalId);
			if (updateFlag)
				saveForGoalHelper.updateGoal(goalId, sessionObj, "COMPLETED");
			// change value of actual savings
			String[] actualSavingsAction = actualSavings.split(",");
			{
				dbHelper.updateGoalsActualSavings(dbUtils.getDBConnection(), actualSavingsAction[i], goalId);
			}
			// update account
			if (!accountStatus.isEmpty()) {
				dbHelper.updateAccountStatus(dbUtils.getDBConnection(), accountStatus, itemAccountsList.get(goals[i]));
			}
		}
		return goalsMap;
	}

	public void deleteCreatedGoal(EnvSession sessionObj, HashMap<String, String> goalsMap, String goalsName) {

		for (int i = 0; i < goalsMap.size(); i++) {
			String userGoalId = goalsMap.get(goalsName.split(",")[i]);
			LinkedHashMap<String, Object> pathParam = new LinkedHashMap<>();
			pathParam.put("goalIdUpdate", Long.parseLong(userGoalId));
			HttpMethodParameters httpParams = HttpMethodParameters.builder().build();
			httpParams.setPathParams(pathParam);
			Response deleteResponse = sfgUtils.deleteGoal(httpParams, sessionObj);
		}
	}

	public HashMap<String, HashMap<String, Integer>> getItemAccountsForAllContainers(EnvSession sessionObj) {
		HashMap<String, HashMap<String, Integer>> itemAccountsByContainersMap = new HashMap<String, HashMap<String, Integer>>();
		HashMap<String, Integer> bankAccountNameItemAccountMap = new HashMap<String, Integer>();
		HashMap<String, Integer> cardAccountNameItemAccountMap = new HashMap<String, Integer>();
		HashMap<String, Integer> loanAccountNameItemAccountMap = new HashMap<String, Integer>();
		HashMap<String, Integer> investmentAccountNameItemAccountMap = new HashMap<String, Integer>();
		Response accountsResponse = accountUtils.getAllAccounts(sessionObj);
		JsonObject accountsResponseObj = (JsonObject) jsonParser.parse(accountsResponse.asString());
		JsonArray accountsArray = accountsResponseObj.getAsJsonArray("account");
		for (int i = 0; i < accountsArray.size(); i++) {
			JsonObject accountObj = (JsonObject) accountsArray.get(i);
			if (accountObj.get("CONTAINER").getAsString().equals("bank")) {
				bankAccountNameItemAccountMap.put(accountObj.get("accountName").getAsString(),
						accountObj.get("id").getAsInt());
			}
			if (accountObj.get("CONTAINER").getAsString().equals("creditCard")) {
				cardAccountNameItemAccountMap.put(accountObj.get("accountName").getAsString(),
						accountObj.get("id").getAsInt());
			}
			if (accountObj.get("CONTAINER").getAsString().equals("loan")) {
				loanAccountNameItemAccountMap.put(accountObj.get("accountName").getAsString(),
						accountObj.get("id").getAsInt());
			}
			if (accountObj.get("CONTAINER").getAsString().equals("investment")) {
				investmentAccountNameItemAccountMap.put(accountObj.get("accountName").getAsString(),
						accountObj.get("id").getAsInt());
			}
		}
		itemAccountsByContainersMap.put("BANK", bankAccountNameItemAccountMap);
		itemAccountsByContainersMap.put("CARD", cardAccountNameItemAccountMap);
		itemAccountsByContainersMap.put("LOAN", loanAccountNameItemAccountMap);
		itemAccountsByContainersMap.put("INVESTMENT", investmentAccountNameItemAccountMap);
		return itemAccountsByContainersMap;
	}

	public HashMap<String, ArrayList<String>> readNegativeExpectedValues() {
		HashMap<String, ArrayList<String>> negativeTestValuesMap = new HashMap<String, ArrayList<String>>();
		ArrayList<String> negativeTestList;
		try {

			JsonArray negativeTestsArr = (JsonArray) jsonParser.parse(new FileReader(System.getProperty("user.dir")
					+ "\\src\\test\\resources\\TestData\\CSVFiles\\Insights\\CobrandPatchAndUserPatchNegativeScenarios.json"));
			for (Object negativeTest : negativeTestsArr) {
				negativeTestList = new ArrayList<>();
				JsonObject negativeExpense = (JsonObject) negativeTest;
				String testCaseId = negativeExpense.get("testCaseId").getAsString();
				String statusCode = negativeExpense.get("httpStatuCode").getAsString();
				String yslErrorCode = negativeExpense.get("yslErrorCode").getAsString();
				String errorMessage = negativeExpense.get("errorMessage").getAsString();
				String negativeTestScenario = negativeExpense.get("negativeTest").getAsString();
				negativeTestList.add(statusCode);
				negativeTestList.add(yslErrorCode);
				negativeTestList.add(errorMessage);
				// negativeTestValuesMap.put(testCaseId, negativeTestList);
				negativeTestValuesMap.put(negativeTestScenario, negativeTestList);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return negativeTestValuesMap;
	}

	public HashMap<String, ArrayList<String>> getNegativeScenarioErrorCodesAndMessages() {
		HashMap<String, ArrayList<String>> negativeTestValuesMap = new HashMap<String, ArrayList<String>>();
		ArrayList<String> negativeTestList;
		try {
			JsonArray negativeTestsArr = (JsonArray) jsonParser.parse(new FileReader(System.getProperty("user.dir")
					+ "\\src\\test\\resources\\TestData\\CSVFiles\\Insights\\SubscriptionsV2\\CustomerAndUserPatchSubscriptionCommonScenrios.json"));
			for (Object negativeTest : negativeTestsArr) {
				negativeTestList = new ArrayList<>();
				JsonObject negativeExpense = (JsonObject) negativeTest;
				String statusCode = negativeExpense.get("httpStatusCode").getAsString();
				String yslErrorCode = negativeExpense.get("yslErrorCode").getAsString();
				String errorMessage = negativeExpense.get("errorMessage").getAsString();
				String negativeTestScenario = negativeExpense.get("negativeScenario").getAsString();
				negativeTestList.add(statusCode);
				negativeTestList.add(yslErrorCode);
				negativeTestList.add(errorMessage);
				// negativeTestValuesMap.put(testCaseId, negativeTestList);
				negativeTestValuesMap.put(negativeTestScenario, negativeTestList);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return negativeTestValuesMap;
	}

	public HashMap<String, ArrayList<String>> readNegativeExpectedValuesForUserSegment() {
		HashMap<String, ArrayList<String>> negativeTestValuesMap = new HashMap<String, ArrayList<String>>();
		ArrayList<String> negativeTestList;
		try {

			JsonArray negativeTestsArr = (JsonArray) jsonParser.parse(new FileReader(System.getProperty("user.dir")
					+ "\\src\\test\\resources\\TestData\\CSVFiles\\Insights\\UserSegmentNegativeScenarios.json"));
			for (Object negativeTest : negativeTestsArr) {
				negativeTestList = new ArrayList<>();
				JsonObject negativeExpense = (JsonObject) negativeTest;
				String testCaseId = negativeExpense.get("testCaseId").getAsString();
				String statusCode = negativeExpense.get("httpStatuCode").getAsString();
				String yslErrorCode = negativeExpense.get("yslErrorCode").getAsString();
				String errorMessage = negativeExpense.get("errorMessage").getAsString();
				negativeTestList.add(statusCode);
				negativeTestList.add(yslErrorCode);
				negativeTestList.add(errorMessage);
				negativeTestValuesMap.put(testCaseId, negativeTestList);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return negativeTestValuesMap;
	}

	public void deleteCreatedAccountGroup(int accountGroupId, EnvSession sessionObj) {
		HttpMethodParameters httpParams = HttpMethodParameters.builder().build();
		Map<String, Object> createAccountGroupPathParam = new HashMap<>();
		createAccountGroupPathParam.put("goalId", accountGroupId);
		httpParams.setPathParams(createAccountGroupPathParam);
		groupUtils.deleteGroup(httpParams, sessionObj);
		System.out.println("Account Group Deleted Successfully...." + accountGroupId);
	}

	public JsonArray getInsightKeys(String insightName) {
		JsonObject insightKeyObject = null;
		try {
			JsonArray insightsKeysObjectsArray = inisghtsKeysObjects.getAsJsonArray("insightsKeys");
			for (int i = 0; i < insightsKeysObjectsArray.size(); i++) {
				insightKeyObject = (JsonObject) insightsKeysObjectsArray.get(i);
				if (insightKeyObject.get("insightName").getAsString().equals(insightName))
					break;
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return insightKeyObject.getAsJsonArray("keys");
	}

	public JsonArray getInsightKeysforIncomeExpense(String insightName) {
		JsonObject insightKeyObject = null;
		try {
			JsonArray insightsKeysObjectsArray = inisghtsKeysObjects.getAsJsonArray("insightsKeys");
			for (int i = 0; i < insightsKeysObjectsArray.size(); i++) {
				insightKeyObject = (JsonObject) insightsKeysObjectsArray.get(i);
				if (insightKeyObject.get("insightName").getAsString().equals(insightName))
					break;
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return insightKeyObject.getAsJsonArray("keys");
	}

	public JsonArray getInsightKeysforProjectedLowBalance(String insightName) {
		JsonObject insightKeyObject = null;
		try {
			JsonArray insightsKeysObjectsArray = inisghtsKeysObjects.getAsJsonArray("insightsKeys");
			for (int i = 0; i < insightsKeysObjectsArray.size(); i++) {
				insightKeyObject = (JsonObject) insightsKeysObjectsArray.get(i);
				if (insightKeyObject.get("insightName").getAsString().equals(insightName))
					break;
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return insightKeyObject.getAsJsonArray("keys");
	}

	public JsonArray getInsightKeysforProjectedInsufficientBalance(String insightName) {
		JsonObject insightKeyObject = null;
		try {
			JsonArray insightsKeysObjectsArray = inisghtsKeysObjects.getAsJsonArray("insightsKeys");
			for (int i = 0; i < insightsKeysObjectsArray.size(); i++) {
				insightKeyObject = (JsonObject) insightsKeysObjectsArray.get(i);
				if (insightKeyObject.get("insightName").getAsString().equals(insightName))
					break;
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return insightKeyObject.getAsJsonArray("keys");
	}

	public JsonArray getInsightKeysforMostFrequentPurchases(String insightName) {
		JsonObject insightKeyObject = null;
		try {
			JsonArray insightsKeysObjectsArray = inisghtsKeysObjects.getAsJsonArray("insightsKeys");
			for (int i = 0; i < insightsKeysObjectsArray.size(); i++) {
				insightKeyObject = (JsonObject) insightsKeysObjectsArray.get(i);
				if (insightKeyObject.get("insightName").getAsString().equals(insightName))
					break;
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return insightKeyObject.getAsJsonArray("keys");
	}

	public JsonArray getInsightKeysforUpcomingBillsSub(String insightName) {
		JsonObject insightKeyObject = null;
		try {
			JsonArray insightsKeysObjectsArray = inisghtsKeysObjects.getAsJsonArray("insightsKeys");
			for (int i = 0; i < insightsKeysObjectsArray.size(); i++) {
				insightKeyObject = (JsonObject) insightsKeysObjectsArray.get(i);
				if (insightKeyObject.get("insightName").getAsString().equals(insightName))
					break;
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return insightKeyObject.getAsJsonArray("keys");
	}

	public JsonArray getInsightKeysforFinancialFees(String insightName, String duration) {
		JsonObject insightFinancialFeesKeyObject = null;
		JsonArray insightsKeysObjectsArray = null;
		if (duration.isEmpty())
			duration = "THREE_MONTHS";
		try {
			insightsKeysObjectsArray = inisghtsKeysObjects.getAsJsonArray("insightsKeys");
			for (int i = 0; i < insightsKeysObjectsArray.size(); i++) {
				insightFinancialFeesKeyObject = (JsonObject) insightsKeysObjectsArray.get(i);
				if (insightFinancialFeesKeyObject.get("insightName").getAsString().equals(insightName + duration))
					break;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return insightFinancialFeesKeyObject.getAsJsonArray("keys");
	}

	public JsonArray getInsightsKeysforPB(String insightName) {
		JsonObject insightFinancialFeesPBObject = null;
		JsonArray insightsPBKeysObjectsArray = null;
		try {
			insightsPBKeysObjectsArray = inisghtsKeysObjects.getAsJsonArray("insightsKeys");
			for (int i = 0; i < insightsPBKeysObjectsArray.size(); i++) {
				insightFinancialFeesPBObject = (JsonObject) insightsPBKeysObjectsArray.get(i);
				if (insightFinancialFeesPBObject.get("insightName").getAsString().equals(insightName + "PB"))
					break;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return insightFinancialFeesPBObject.getAsJsonArray("keys");
	}

	public JsonArray getInsightsKeysforPBDuration(String insightName, String duration) {
		JsonObject insightFinancialFeesPBObject = null;
		JsonArray insightsPBKeysObjectsArray = null;
		if (duration.isEmpty())
			duration = "THREE_MONTHS";
		try {
			insightsPBKeysObjectsArray = inisghtsKeysObjects.getAsJsonArray("insightsKeys");
			for (int i = 0; i < insightsPBKeysObjectsArray.size(); i++) {
				insightFinancialFeesPBObject = (JsonObject) insightsPBKeysObjectsArray.get(i);
				if (insightFinancialFeesPBObject.get("insightName").getAsString().equals(insightName + "PB" + duration))
					break;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return insightFinancialFeesPBObject.getAsJsonArray("keys");
	}

	public JsonArray getInsightsKeysforPBPATCH(String insightName) {
		JsonObject insightFinancialFeesPBObject = null;
		JsonArray insightsPBKeysObjectsArray = null;
		try {
			insightsPBKeysObjectsArray = inisghtsKeysObjects.getAsJsonArray("insightsKeys");
			for (int i = 0; i < insightsPBKeysObjectsArray.size(); i++) {
				insightFinancialFeesPBObject = (JsonObject) insightsPBKeysObjectsArray.get(i);
				if (insightFinancialFeesPBObject.get("insightName").getAsString().equals(insightName + "PBPATCH")) {
					System.out.println(insightFinancialFeesPBObject.get("insightName").getAsString());
					break;
				}

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return insightFinancialFeesPBObject.getAsJsonArray("keys");
	}

	public int getTotalNumberofFinancialFeesInsightKeys(String keys, String insightName, String duration) {
		int totalKeys = 0;
		if (duration.isEmpty())
			duration = "THREE_MONTHS";
		JsonArray insightKeysArray = getInsightKeysforFinancialFees(insightName, duration);

		for (int i = 0; i < insightKeysArray.size(); i++) {
			JsonObject keyObj = (JsonObject) insightKeysArray.get(i);
			if (keyObj.keySet().toString().contains(keys)) {
				int keyLength = (keyObj
						.get(keyObj.keySet().toString().substring(1, keyObj.keySet().toString().length() - 1))
						.getAsString().split(",")).length;
				totalKeys = totalKeys + (keyLength - 1);
			}
		}
		return totalKeys;
	}

	public int getTotalNumberofPBInsihgtKeys(String keys, String insightName, String duration) {
		JsonArray pbKeysArray = null;
		int totalKeys = 0;
		if (duration.isEmpty())
			duration = "THREE_MONTHS";
		JsonArray insightKeysArray = getInsightKeysforFinancialFees(insightName, duration);

		if (keys.equalsIgnoreCase("Account") && duration.equalsIgnoreCase("PATCH"))
			pbKeysArray = getInsightsKeysforPBPATCH(insightName);
		else if (insightName.equalsIgnoreCase("FinancialFees"))
			pbKeysArray = getInsightsKeysforPB(insightName);
		else
			pbKeysArray = getInsightsKeysforPBDuration(insightName, duration);

		for (int i = 0; i < insightKeysArray.size(); i++) {
			JsonObject keyObj = (JsonObject) insightKeysArray.get(i);
			if (keyObj.keySet().toString().contains(keys)) {
				int keyLength = (keyObj
						.get(keyObj.keySet().toString().substring(1, keyObj.keySet().toString().length() - 1))
						.getAsString().split(",")).length;
				totalKeys = totalKeys + (keyLength - 1);
			}
		}

		for (int i = 0; i < pbKeysArray.size(); i++) {
			JsonObject keyObj = (JsonObject) pbKeysArray.get(i);
			int keyLength = (keyObj
					.get(keyObj.keySet().toString().substring(1, keyObj.keySet().toString().length() - 1)).getAsString()
					.split(",")).length;
			totalKeys = totalKeys + (keyLength - 1);
		}
		return totalKeys;
	}

	public JsonArray getInsightKeys1(String insightName) {
		JsonObject insightKeyObject = null;
		try {
			JsonArray insightsKeysObjectsArray = inisghtsKeysObjects.getAsJsonArray("insightsKeys");
			for (int i = 0; i < insightsKeysObjectsArray.size(); i++) {
				insightKeyObject = (JsonObject) insightsKeysObjectsArray.get(i);
				if (insightKeyObject.get("insightName").getAsString().equals(insightName))
					break;
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return insightKeyObject.getAsJsonArray("keys");
	}

	public int getTotalNumberOfKeys1(JsonArray keysArray) {
		int totalKeys = 0;
		for (int i = 0; i < keysArray.size(); i++) {
			JsonObject keyObj = (JsonObject) keysArray.get(i);
			int keyLength = (keyObj
					.get(keyObj.keySet().toString().substring(1, keyObj.keySet().toString().length() - 1)).getAsString()
					.split(",")).length;
			totalKeys = totalKeys + (keyLength - 1);
		}
		return totalKeys;
	}

	public int getTotalNumberOfKeys(JsonArray keysArray, String keys) {
		int totalKeys = 0;
		for (int i = 0; i < keysArray.size(); i++) {
			JsonObject keyObj = (JsonObject) keysArray.get(i);
			if (keyObj.keySet().toString().contains(keys)) {
				totalKeys = (keyObj
						.get(keyObj.keySet().toString().substring(1, keyObj.keySet().toString().length() - 1))
						.getAsString().split(",")).length;
			}
		}
		return totalKeys - 1;
	}

	public Number getMemId(EnvSession session) {
		Response userResponse = userUtils.getUserDetails(session);
		Number number = ((JsonObject) jsonParser.parse(userResponse.asString())).getAsJsonObject("user").get("id")
				.getAsNumber();
		return number;
	}

	public boolean verifyInsightsPatchResponseForNegativeTests(String testCaseId, Response expenseNegativeResponse,
			HashMap<String, ArrayList<String>> expetedExpenseNegativeMap, String negativeTestScenario,
			String insightName, FailureReason failureReason) {
		boolean testExecutionStatus = true;
		JsonObject expNegResponseObj = (JsonObject) jsonParser.parse(expenseNegativeResponse.asString());
		if (expenseNegativeResponse.getStatusCode() == 400 || expenseNegativeResponse.getStatusCode() == 401
				|| expenseNegativeResponse.getStatusCode() == 500) {
			if (expenseNegativeResponse.getStatusCode() == Integer
					.parseInt(expetedExpenseNegativeMap.get(negativeTestScenario).get(0))) {
				if (!expNegResponseObj.get("errorCode").getAsString()
						.equals(expetedExpenseNegativeMap.get(negativeTestScenario).get(1))) {
					testExecutionStatus = insightsHelper.failTest(insightName,
							expetedExpenseNegativeMap.get(negativeTestScenario).get(1),
							expNegResponseObj.get("errorCode").getAsString(), failureReason);

				}
				String expectedErrorMessage = null;
				if (negativeScenarioList.contains(negativeTestScenario)) {
					expectedErrorMessage = expetedExpenseNegativeMap.get(negativeTestScenario).get(2)
							.replace("insightName", insightName);
				} else {
					expectedErrorMessage = expetedExpenseNegativeMap.get(negativeTestScenario).get(2);
				}

				if (!expNegResponseObj.get("errorMessage").getAsString().equals(expectedErrorMessage)) {
					testExecutionStatus = insightsHelper.failTest(insightName, expectedErrorMessage,
							expNegResponseObj.get("errorMessage").getAsString(), failureReason);

				}

			} else {
				testExecutionStatus = false;
				failureReason.setFailureReason("Https status doesn't match... Expected Status : "
						+ Integer.parseInt(expetedExpenseNegativeMap.get(testCaseId).get(0)) + "Actual Status : "
						+ expenseNegativeResponse.getStatusCode());
				Assert.fail(testCaseId + "  Https status doesn't match Test Failed... Expected Status : "
						+ Integer.parseInt(expetedExpenseNegativeMap.get(testCaseId).get(0)) + "Actual Status : "
						+ expenseNegativeResponse.getStatusCode());
			}
		} else if (expenseNegativeResponse.getStatusCode() == HttpStatus.OK_200) {
			if (expenseNegativeResponse.getStatusCode() == Integer
					.parseInt(expetedExpenseNegativeMap.get(testCaseId).get(0))) {
				if (!expenseNegativeResponse.asString().equals("{}")) {
					testExecutionStatus = false;
					failureReason.setFailureReason("Failed Expected response.... {} " + "Actual Response  :   "
							+ expenseNegativeResponse.asString());
					Assert.fail(testCaseId + "  Failed Expected response.... {} " + "Actual Response  :   "
							+ expenseNegativeResponse.asString());
				}

			} else {
				testExecutionStatus = false;
				failureReason.setFailureReason("Https status doesn't match Test Failed Expected Status  : "
						+ Integer.parseInt(expetedExpenseNegativeMap.get(testCaseId).get(0)) + "Actual Status :  "
						+ expenseNegativeResponse.getStatusCode());
				Assert.fail(testCaseId + "  Https status doesn't match Test Failed Expected Status  : "
						+ Integer.parseInt(expetedExpenseNegativeMap.get(testCaseId).get(0)) + "Actual Status :  "
						+ expenseNegativeResponse.getStatusCode());
			}
		} else {
			testExecutionStatus = false;
			failureReason.setFailureReason(
					" Test Failed Invalid Http status Code  : " + expenseNegativeResponse.getStatusCode());
			Assert.fail(
					testCaseId + " Test Failed Invalid Http status Code  : " + expenseNegativeResponse.getStatusCode());
		}

		return testExecutionStatus;
	}

	public HttpMethodParameters updateCobrandSubscriptionProcessing(String jwtCobAuthToken, String insightNameKey,
			String testNegativeScenario, String insightNameEdit, String insightTitle, String container,
			String cobrandId, String keys, String ruleExpression, String description, String applicableEntity,
			String entityParametersCount, String entityParameterName, String totalThresholds, String id,
			String frequency, String isSubscribed, String duration, String insightType,
			String threshold_Name_Value_Type) throws IOException {

		HttpMethodParameters cobHttpParams = insightsHelper.getHttpParams("cobrandSubscriptionRequest", null, null,
				jwtCobAuthToken, null, null, null, null);
		String patchCobrandSubscriptionBodyParam;
		patchCobrandSubscriptionBodyParam = insightsHelper.constructPatchCobrandSubscriptionRequest(insightNameKey,
				testNegativeScenario, insightNameEdit, insightTitle, container, cobrandId, keys, ruleExpression,
				description, applicableEntity, entityParametersCount, entityParameterName, totalThresholds, id,
				frequency, isSubscribed, duration, insightType, threshold_Name_Value_Type);
		cobHttpParams.setBodyParams(patchCobrandSubscriptionBodyParam);
		return cobHttpParams;
	}

	public String updateCobrandSubscriptionResponseVerificationPostPatch(String testCaseId, boolean testExecutionStatus,
			Map<String, String> testSummary, Response patchCobrandSubscriptionResponse,
			HashMap<String, ArrayList<String>> negativeExpectedValuesMap, String testNegativeScenario,
			String testCaseDescription, Response getCobSubscriptionResponseAfterPatch, String insightNameKey,
			String frequency, String duration, String insightType, String insightTitle, String entityParameterName,
			String isSubscribed, String threshold_Name_Value_Type, int expectedStatus, FailureReason failureReason,
			TestInfo testInfo) {

		String returnStatus = "";

		if (patchCobrandSubscriptionResponse.getStatusCode() != expectedStatus) {
			testExecutionStatus = false;
			if (patchCobrandSubscriptionResponse.getStatusCode() == HttpStatus.INTERNAL_SERVER_ERROR_500) {
				String errMsg = ((JsonObject) jsonParser.parse(patchCobrandSubscriptionResponse.asString())
						.getAsJsonObject()).get("errorMessage").getAsString();
				failureReason
						.setFailureReason("Expected 204 But Found " + patchCobrandSubscriptionResponse.getStatusCode());
				testInfo.setResponse(patchCobrandSubscriptionResponse.asString());
			} else {
				testInfo.setResponse(patchCobrandSubscriptionResponse.asString());
				failureReason
						.setFailureReason("Expected 204 But Found " + patchCobrandSubscriptionResponse.getStatusCode());
			}

		} else if (patchCobrandSubscriptionResponse.getStatusCode() == HttpStatus.NO_CONTENT_204) {
			testExecutionStatus = insightsHelper.verifyCobrandSubscriptionResponsePostPatch(testCaseId,
					testCaseDescription, getCobSubscriptionResponseAfterPatch, insightNameKey, frequency, duration,
					insightType, insightTitle, entityParameterName, isSubscribed, threshold_Name_Value_Type,
					failureReason);
		} else {
			testExecutionStatus = verifyInsightsPatchResponseForNegativeTests(testCaseId,
					patchCobrandSubscriptionResponse, negativeExpectedValuesMap, testNegativeScenario, insightNameKey,
					failureReason);

		}
		if (testExecutionStatus) {
			testSummary.put(testCaseId + "_" + testCaseDescription, "PASSED");
			returnStatus = "TRUE";
		} else {
			testSummary.put(testCaseId + "_" + testCaseDescription, "FAILED");
			returnStatus = "FALSE";
		}

		return returnStatus;
	}

	public String getCobrandSubscriptionResponseVerification(Response cobSubscriptionResponse,
			boolean testExecutionStatus, Map<String, String> testSummary, String testCaseId, String testCaseDescription,
			String triggerType, String InsightKey, String insightName, String title, String containerSupported,
			JsonArray keys, String totalNumberOfKeys, String description, String applicableEntity, String levels,
			String isSubscribed, String frequency, String thresholdDetails, String checkRuleExpression,
			int expectedStatus, FailureReason failureReason, String validateKeys, TestInfo testInfo) {

		String returnStatus;
		System.out.println(cobSubscriptionResponse.asString());
		if (cobSubscriptionResponse.getStatusCode() != expectedStatus) {
			testExecutionStatus = false;
		}
		testExecutionStatus = insightsHelper.verifyCobrandSubscriptionResponse(cobSubscriptionResponse, testCaseId,
				testCaseDescription, triggerType, InsightKey, insightName, title, containerSupported, keys,
				totalNumberOfKeys, description, applicableEntity, levels, isSubscribed, frequency, thresholdDetails,
				checkRuleExpression, failureReason, validateKeys, testInfo);
		if (testExecutionStatus) {
			testSummary.put(testCaseId + "_" + testCaseDescription, "PASSED");
			returnStatus = "TRUE";
		} else {
			testSummary.put(testCaseId + "_" + testCaseDescription, "FAILED");
			returnStatus = "FALSE";
			// Assert.fail();
		}
		return returnStatus;
	}

	public String getUserSubscriptionResponseVerification(Response cobSubscriptionResponse,
			Response userSubscriptionResponse, boolean testExecutionStatus, Map<String, String> testSummary,
			String testCaseId, String testCaseDescription, String triggerType, String InsightKey, String insightName,
			String title, String containerSupported, JsonArray keys, String totalNumberOfKeys, String description,
			String applicableEntity, String levels, String isSubscribed, String frequency, String thresholdDetails,
			String checkRuleExpression, String configMetaAndentityParameter, int expectedStatus,
			FailureReason failureReason, String validateKeys, TestInfo testInfo) {

		String testStatus = "";
		if (userSubscriptionResponse.getStatusCode() != expectedStatus) {
			testExecutionStatus = false;
		}
		testExecutionStatus = insightsHelper.verifyUserSubscriptionResponse(cobSubscriptionResponse,
				userSubscriptionResponse, testCaseId, testCaseDescription, triggerType, InsightKey, insightName, title,
				containerSupported, keys, totalNumberOfKeys, description, applicableEntity, levels, isSubscribed,
				frequency, thresholdDetails, checkRuleExpression, configMetaAndentityParameter, failureReason,
				validateKeys, testInfo);
		if (testExecutionStatus) {
			testSummary.put(testCaseId + "_" + testCaseDescription, "PASSED");
			testStatus = "TRUE";
		} else {
			testSummary.put(testCaseId + "_" + testCaseDescription, "FAILED");
			testStatus = "FALSE";
		}

		return testStatus;
	}

	public String updateUserSubscriptionResponseVerificationPostPatch(String testCaseId, boolean testExecutionStatus,
			Map<String, String> testSummary, String testCaseDescription,
			HashMap<String, ArrayList<String>> negativeExpectedValuesMap, String testNegativeScenario,
			Response patchUserSubscriptionResponse, Response getCobSubscriptionResponse,
			Response getUserSubscriptionResponse, String insightNameKey, String frequency, String duration,
			String insightTitle, String entityParameterName, String isSubscribed, String threshold_Name_Value_Type,
			int expectedStatus, FailureReason failureReason) {
		String returnStatus = null;
		if (testExecutionStatus) {
			if (patchUserSubscriptionResponse.getStatusCode() == 204) {
				testExecutionStatus = insightsHelper.verifyUserSubscriptionResponsePostPatch(testCaseId,
						testCaseDescription, getCobSubscriptionResponse, getUserSubscriptionResponse, insightNameKey,
						frequency, duration, insightTitle, entityParameterName, isSubscribed, threshold_Name_Value_Type,
						failureReason);
			} else {
				testExecutionStatus = verifyInsightsPatchResponseForNegativeTests(testCaseId,
						patchUserSubscriptionResponse, negativeExpectedValuesMap, testNegativeScenario, insightNameKey,
						failureReason);

			}
		}
		if (testExecutionStatus) {
			returnStatus = "TRUE";
			testSummary.put(testCaseId + "_" + testCaseDescription, "PASSED");
		} else {
			returnStatus = "FALSE";
			testSummary.put(testCaseId + "_" + testCaseDescription, "FAILED");
		}
		return returnStatus;
	}

	public String getSpecificInsightObjectResponse(Response getCobSubscriptionResponse, String insightName) {

		JsonObject subscriptionResponse = (JsonObject) jsonParser.parse(getCobSubscriptionResponse.asString());
		JsonArray subscriptionArray = subscriptionResponse.getAsJsonArray("cobrandSubscription");
		String insightResponse = null;
		for (int i = 0; i < subscriptionArray.size(); i++) {
			JsonObject subscriptionObject = (JsonObject) subscriptionArray.get(i);
			System.out.println("Insight " + subscriptionObject.get("name").getAsString());
			if (subscriptionObject.get("name").getAsString().equals(insightName)) {
				insightResponse = subscriptionObject.toString();
				break;
			}
		}
		return insightResponse;
	}

	public String getPassPercentage(Map<String, String> testSummary) {

		int passedCount = 0, failedCount = 0;
		for (Map.Entry<String, String> entry : testSummary.entrySet()) {
			if (entry.getValue().equalsIgnoreCase("FAILED")) {
				failedCount++;
			} else if (entry.getValue().equalsIgnoreCase("PASSED")) {
				passedCount++;
			} else {

			}
		}
		double passed = passedCount, failed = failedCount;
		double percentage = (passed) / (failed + passed) * 100;
		String totalPassPercentage = String.valueOf(percentage);

		return totalPassPercentage;
	}

	public String prapareDataForViewEntityParams(String jwtUserAuthToken, String name, String description,
			String rule_type, double amount, String currency, String budgetType, String ruleName, String fromDate,
			String toDate, double fromAmount, double toAmount, String merchantType, String merchantName,
			String[] itemAccountId, String categoryId, String[] transactionId) throws IOException {
		String viewId = "";
		try {
			String viewBodyRequest = boardHelper.createViewJsonBody(name, description, rule_type, amount, currency,
					budgetType, ruleName, fromDate, toDate, fromAmount, toAmount, merchantType, merchantName,
					itemAccountId, categoryId, transactionId);
			viewId = insightsHelper.getBoardId(jwtUserAuthToken, viewBodyRequest);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return viewId;
	}

	public HashMap<String, String> prepareDataForOtherThanAccountEntityParamas(EnvSession sessionObj,
			HashMap<String, String> entityIdsMap, String entityParameterName, String destItemAccounts,
			String envCobrand, String goalName, String goalStatus, String actualSavings, String accountStatus)
			throws SQLException {

		HashMap<String, String> entityNameIdMap = null;
		String entityName = entityParameterName.split("\\s+")[0];
		if (entityName.equals("goal")) {
			entityNameIdMap = createGoals(sessionObj, entityIdsMap, goalName, destItemAccounts, goalStatus,
					actualSavings, accountStatus, envCobrand);
		}
		if (entityParameterName.equals("view")) {

		}
		if (entityParameterName.equals("accountGroup")) {
			HttpMethodParameters groupJson2 = budgetHelper.createGroupJson(RandomStringUtils.randomAlphabetic(100));
			Response groupResponse1 = groupUtils.createGroup(groupJson2, sessionObj);
			String groupId2 = groupResponse1.jsonPath().getString("group.id");
		}

		return entityNameIdMap;
	}

	public String getDataLevels(String entityParameterName, String isCobrandSubscribed, String isUserSubscribed) {
		StringBuffer applicableDataLevels = new StringBuffer();
		String[] cobSubscriptionValues = isCobrandSubscribed.split("\\s+");
		String[] userSubscriptionValues = isUserSubscribed.split("\\s+");
		String[] entityParametersValues = entityParameterName.split("\\s+");
		if (!entityParameterName.isEmpty()) {
			if (!isCobrandSubscribed.isEmpty() && isUserSubscribed.isEmpty()) {
				for (int index = 0; index < cobSubscriptionValues.length; index++) {
					if (Boolean.parseBoolean(cobSubscriptionValues[index])) {
						applicableDataLevels.append(entityParametersValues[index].toUpperCase());
						applicableDataLevels.append("  ");
					}
				}
			}
			if (!isCobrandSubscribed.isEmpty() && !isUserSubscribed.isEmpty()) {
				for (int index = 0; index < cobSubscriptionValues.length; index++) {
					if (Boolean.parseBoolean(userSubscriptionValues[index])
							&& Boolean.parseBoolean(cobSubscriptionValues[index])) {
						applicableDataLevels.append(entityParametersValues[index].toUpperCase());
						applicableDataLevels.append("   ");
					}
				}
			}
			if (isCobrandSubscribed.isEmpty() && !isUserSubscribed.isEmpty()) {
				for (int index = 0; index < userSubscriptionValues.length; index++) {
					if (Boolean.parseBoolean(userSubscriptionValues[index])) {
						applicableDataLevels.append(entityParametersValues[index].toUpperCase());
						applicableDataLevels.append("   ");
					}
				}
			}
		}
		return applicableDataLevels.toString().trim();
	}

	public void setViewsPropertiesDetails(String viewRuleAccountNames, String viewRuleTransactionDetails,
			String viewRuleAmountRangeDetails, String viewRuleCategoryIdDetails, String viewRuleMerchantNameDetails,
			String viewRuleMerchantTypeDetails, CommonEntity commonEntity) {
		if (!viewRuleAccountNames.isEmpty())
			commonEntity.setViewRuleAccountNames(viewRuleAccountNames);
		if (!viewRuleTransactionDetails.isEmpty())
			commonEntity.setViewRuleTransactionDetails(viewRuleTransactionDetails);
		if (!viewRuleAmountRangeDetails.isEmpty())
			commonEntity.setViewRuleAmountRangeDetails(viewRuleAmountRangeDetails);
		if (!viewRuleCategoryIdDetails.isEmpty())
			commonEntity.setViewRuleCategoryIdDetails(viewRuleCategoryIdDetails);
		if (!viewRuleMerchantNameDetails.isEmpty())
			commonEntity.setViewRuleMerchantNameDetails(viewRuleMerchantNameDetails);
		if (!viewRuleMerchantTypeDetails.isEmpty())
			commonEntity.setViewRuleMerchantTypeDetails(viewRuleMerchantTypeDetails);
	}

	public void deleteCreatedGoalsForUser(String goals, HashMap<String, String> goalsMap,
			HashMap<String, String> entityIdsMap, EnvSession sessionObj) {
		HttpMethodParameters httpParams = HttpMethodParameters.builder().build();
		String[] goal = goals.split(",");
		for (int i = 0; i < goal.length; i++) {
			LinkedHashMap<String, Object> pathParam = new LinkedHashMap<>();
			pathParam.put("goalIdDelete", Long.parseLong(goalsMap.get(goal[i])));
			httpParams.setPathParams(pathParam);
			Response deleteResponse = sfgUtils.deleteGoal(httpParams, sessionObj);
			entityIdsMap.remove(goal[i]);
		}

	}

	public ArrayList<DagCredentials> getDagCredentialsForScheduleInsights() {
		ArrayList<DagCredentials> dagCredentialsList = new ArrayList<DagCredentials>();
		String[] dagCredentials = { "Networth_Money.site16441.1", "Networth_Money.site16441.2",
				"Networth_Money.site16441.3" };
		DagCredentials dagCreds = null;
		for (int i = 0; i < dagCredentials.length; i++) {
			dagCreds = new DagCredentials();
			dagCreds.setDagUser(dagCredentials[i]);
			dagCreds.setDagPasswrod(dagCredentials[i].substring(dagCredentials[i].indexOf('.') + 1));
			dagCredentialsList.add(dagCreds);
		}
		return dagCredentialsList;
	}

	public ExpectedResultValues getExpectedAttributesToBeValidated(String testCaseId, String insightName,
			String triggerType, String insightType, String entityParameterName, String thresholdNameValueType,
			int noumberOfInsights, int numberOfKeys, HashMap<String, Boolean> cobrandSubscriptionMap,
			boolean isUserPatched, int numberOfInsightsPostPatch, FailureReason failureReason, JsonArray keysArray,
			HashMap<String, String> entityIdsMap, String validateEntityKeys, Response userSubscriptionResponse,
			String jwtCobAuthToken) throws IOException {
		HashMap<String, Entities> subscriptionsMap = new HashMap<String, Entities>();
		ExpectedResultValues expectedResultValues = new ExpectedResultValues();

		expectedResultValues.setTestCaseId(testCaseId);
		expectedResultValues.setInsightName(insightName);
		expectedResultValues.setTriggerType(triggerType);
		expectedResultValues.setEntityParameterName(entityParameterName);
		expectedResultValues.setThresholdNameValueType(thresholdNameValueType);
		expectedResultValues.setNumberOfInsights(noumberOfInsights);
		expectedResultValues.setUserPatched(isUserPatched);
		expectedResultValues.setCobrandSubscriptionMap(cobrandSubscriptionMap);
		expectedResultValues.setNumberOfPatchedInisghts(numberOfInsightsPostPatch);
		expectedResultValues.setFailureReason(failureReason);
		expectedResultValues.setKeysArray(keysArray);
		expectedResultValues.setEntityIdsMap(entityIdsMap);
		expectedResultValues.setValidateEntityKeys(validateEntityKeys);
		boolean isCobrandSubscribed = false;
		JsonObject userResponseObject = jsonParser.parse(userSubscriptionResponse.asString()).getAsJsonObject();
		JsonArray userSubscriptionArr = null, cobrandSubscriptionArr = null;
		JsonObject insightObject = null;
		JsonObject configMetaObject = null;
		for (String entity : entityParameterName.split("\\s+")) {
			isCobrandSubscribed = isCobrandSubscribed || expectedResultValues.getCobrandSubscriptionMap().get(entity);

		}
		expectedResultValues.setCobrandSubscribed(isCobrandSubscribed);

		if (isCobrandSubscribed) {
			userSubscriptionArr = userResponseObject.getAsJsonArray("userSubscription");
			insightObject = userSubscriptionArr.get(0).getAsJsonObject();
			JsonObject entityParameterObject = insightObject.getAsJsonObject("entityParameter");
			Set<String> userSubscriptionEntities = entityParameterObject.keySet();
			Entities userEntities = new Entities();
			ArrayList<Entity> userEntityList = new ArrayList<Entity>();

			for (String entity : userSubscriptionEntities) {
				System.out.println("Supported Insight Entity " + entity);

				for (int index = 0; index < entityParameterObject.getAsJsonArray(entity).size(); index++) {
					Entity userEntity = new Entity();
					JsonObject entityObject = entityParameterObject.getAsJsonArray(entity).get(index).getAsJsonObject();
					userEntity.setEntityType(entity);
					if (entityObject.has("isSubscribed")) {
						userEntity.setSubscribed(entityObject.get("isSubscribed").getAsBoolean());
					}
					if (entityObject.has("frequency")) {
						userEntity.setFrequency(entityObject.get("frequency").getAsString());
					}
					if (entityObject.has("insightType")) {
						userEntity.setInsightType(entityObject.get("insightType").getAsString());
					}
					if (entityObject.has("duration")) {
						userEntity.setDuration(entityObject.get("duration").getAsString());
					}
					if (entityObject.has("id")) {
						userEntity.setId(entityObject.get("id").getAsString());
					}
					ArrayList<Threshold> thresholdList = new ArrayList<Threshold>();
					if (entityObject.has("threshold")) {
						JsonArray thresholdArr = entityObject.getAsJsonArray("threshold");
						Threshold threshold = null;
						for (int i = 0; i < thresholdArr.size(); i++) {
							threshold = new Threshold();
							threshold.setName(thresholdArr.get(i).getAsJsonObject().get("name").getAsString());
							threshold.setValue(thresholdArr.get(i).getAsJsonObject().get("value").getAsString());
							threshold.setType(thresholdArr.get(i).getAsJsonObject().get("type").getAsString());
							thresholdList.add(threshold);
						}
						userEntity.setThresholds(thresholdList);
					}
					userEntityList.add(userEntity);
				}
			}
			userEntities.setEntities(userEntityList);
			subscriptionsMap.put("userSubscription", userEntities);
			expectedResultValues.setSubscriptions(subscriptionsMap);
			configMetaObject = insightObject.getAsJsonObject("configMeta");
		}
		if (!isCobrandSubscribed) {
			HttpMethodParameters httpParams = insightsHelper.getHttpParams("cobrandSubscriptionRequest", null, null,
					jwtCobAuthToken, null, null, null, null);
			Response cobSubscriptionResponse = insightUtils.getCobrandSubscriptions(httpParams, null);
			JsonObject responseObject = jsonParser.parse(cobSubscriptionResponse.asString()).getAsJsonObject();
			cobrandSubscriptionArr = responseObject.getAsJsonArray("cobrandSubscription");
			for (int i = 0; i < cobrandSubscriptionArr.size(); i++) {
				insightObject = cobrandSubscriptionArr.get(i).getAsJsonObject();
				if (insightObject.get("name").getAsString().equals(expectedResultValues.getInsightName()))
					break;
			}
			configMetaObject = insightObject.getAsJsonObject("entityParameter");
		}
		Set<String> cobrandSubscriptionEntities = configMetaObject.keySet();
		Entities cobrandEntities = new Entities();
		ArrayList<Entity> cobrandEntityList = new ArrayList<Entity>();
		for (String entity : cobrandSubscriptionEntities) {
			Entity cobrandEntity = new Entity();
			System.out.println("Supported Insight Entity " + entity);
			JsonObject entityObject = configMetaObject.getAsJsonArray(entity).get(0).getAsJsonObject();
			cobrandEntity.setEntityType(entity);
			if (entityObject.has("isSubscribed")) {
				cobrandEntity.setSubscribed(entityObject.get("isSubscribed").getAsBoolean());
			}
			if (entityObject.has("frequency")) {
				cobrandEntity.setFrequency(entityObject.get("frequency").getAsString());
			}
			if (entityObject.has("insightType")) {
				cobrandEntity.setInsightType(entityObject.get("insightType").getAsString());
			}
			if (entityObject.has("duration")) {
				cobrandEntity.setDuration(entityObject.get("duration").getAsString());
			}
			ArrayList<Threshold> thresholdList = new ArrayList<Threshold>();
			if (entityObject.has("threshold")) {
				JsonArray thresholdArr = entityObject.getAsJsonArray("threshold");
				Threshold threshold = null;
				for (int i = 0; i < thresholdArr.size(); i++) {
					threshold = new Threshold();
					threshold.setName(thresholdArr.get(i).getAsJsonObject().get("name").getAsString());
					threshold.setValue(thresholdArr.get(i).getAsJsonObject().get("value").getAsString());
					threshold.setType(thresholdArr.get(i).getAsJsonObject().get("type").getAsString());
					thresholdList.add(threshold);
				}
				cobrandEntity.setThresholds(thresholdList);
			}
			cobrandEntityList.add(cobrandEntity);
		}
		cobrandEntities.setEntities(cobrandEntityList);
		subscriptionsMap.put("cobrandSubscription", cobrandEntities);
		expectedResultValues.setSubscriptions(subscriptionsMap);

		return expectedResultValues;

	}

	public ExpectedResultValues getExpectedAttributesToBeValidatedV2(String testCaseId, String insightName,
			String triggerType, String insightType, String entityParameterName, String thresholdNameValueType,
			int noumberOfInsights, int numberOfKeys, HashMap<String, Boolean> cobrandSubscriptionMap,
			boolean isUserPatched, int numberOfInsightsPostPatch, FailureReason failureReason, JsonArray keysArray,
			HashMap<String, String> entityIdsMap, String validateEntityKeys, Response userSubscriptionResponse,
			String jwtCobAuthToken) throws IOException {
		HashMap<String, Entities> subscriptionsMap = new HashMap<String, Entities>();
		ExpectedResultValues expectedResultValues = new ExpectedResultValues();

		expectedResultValues.setTestCaseId(testCaseId);
		expectedResultValues.setInsightName(insightName);
		expectedResultValues.setTriggerType(triggerType);
		expectedResultValues.setEntityParameterName(entityParameterName);
		expectedResultValues.setThresholdNameValueType(thresholdNameValueType);
		expectedResultValues.setNumberOfInsights(noumberOfInsights);
		expectedResultValues.setUserPatched(isUserPatched);
		expectedResultValues.setCobrandSubscriptionMap(cobrandSubscriptionMap);
		expectedResultValues.setNumberOfPatchedInisghts(numberOfInsightsPostPatch);
		expectedResultValues.setFailureReason(failureReason);
		expectedResultValues.setKeysArray(keysArray);
		expectedResultValues.setEntityIdsMap(entityIdsMap);
		expectedResultValues.setValidateEntityKeys(validateEntityKeys);
		boolean isCobrandSubscribed = false;
		JsonObject userResponseObject = jsonParser.parse(userSubscriptionResponse.asString()).getAsJsonObject();
		JsonArray userSubscriptionArr = null, cobrandSubscriptionArr = null;
		JsonObject insightObject = null;
		JsonArray configurationObjArray = null;
		for (String entity : entityParameterName.split("\\s+")) {
			isCobrandSubscribed = isCobrandSubscribed || expectedResultValues.getCobrandSubscriptionMap().get(entity);
			System.out.println("isCobrand Subscribed " + isCobrandSubscribed);

		}
		expectedResultValues.setCobrandSubscribed(isCobrandSubscribed);

		if (isCobrandSubscribed) {
			userSubscriptionArr = userResponseObject.getAsJsonArray("userSubscription");
			insightObject = userSubscriptionArr.get(0).getAsJsonObject();
			JsonArray userSubscriptionArray = insightObject.getAsJsonArray("userConfiguration");
			Set<String> userSubscriptionEntities = new HashSet<String>();
			Entities userEntities = new Entities();
			ArrayList<Entity> userEntityList = new ArrayList<Entity>();
			for (int j = 0; j < userSubscriptionArray.size(); j++) {
				String entity = ((JsonObject) (userSubscriptionArray.get(j))).get("entityType").getAsString();
				System.out.println("Supported Insight Entity " + userSubscriptionArray.get(j).toString());

				Entity userEntity = new Entity();
				JsonObject entityObject = ((JsonObject) (userSubscriptionArray.get(j)));
				userEntity.setEntityType(entity);
				if (entityObject.has("isSubscribed")) {
					userEntity.setSubscribed(entityObject.get("isSubscribed").getAsBoolean());
				}
				if (entityObject.has("frequency")) {
					userEntity.setFrequency(entityObject.get("frequency").getAsString());
				}
				if (entityObject.has("insightType")) {
					userEntity.setInsightType(entityObject.get("insightType").getAsString());
				}
				if (entityObject.has("duration")) {
					userEntity.setDuration(entityObject.get("duration").getAsString());
				}
				if (entityObject.has("entityId")) {
					userEntity.setId(entityObject.get("entityId").getAsString());
				}
				ArrayList<Threshold> thresholdList = new ArrayList<Threshold>();
				if (entityObject.has("threshold")) {
					JsonArray thresholdArr = entityObject.getAsJsonArray("threshold");
					Threshold threshold = null;
					for (int i = 0; i < thresholdArr.size(); i++) {
						threshold = new Threshold();
						threshold.setName(thresholdArr.get(i).getAsJsonObject().get("name").getAsString());
						threshold.setValue(thresholdArr.get(i).getAsJsonObject().get("value").getAsString());
						threshold.setType(thresholdArr.get(i).getAsJsonObject().get("type").getAsString());
						thresholdList.add(threshold);
					}
					userEntity.setThresholds(thresholdList);
				}
				userEntityList.add(userEntity);

			}
			userEntities.setEntities(userEntityList);
			subscriptionsMap.put("userSubscription", userEntities);
			expectedResultValues.setSubscriptions(subscriptionsMap);
			configurationObjArray = insightObject.getAsJsonArray("customerConfiguration");
		}
		if (!isCobrandSubscribed) {
			HttpMethodParameters httpParams = insightsHelper.getHttpParams("cobrandSubscriptionRequest", null, null,
					jwtCobAuthToken, null, null, null, null);
			httpParams.setHttpMethod("GET");
			Response cobSubscriptionResponse = insightsUtilsV2.getCustomerSubscriptions(httpParams, null);
			JsonObject responseObject = jsonParser.parse(cobSubscriptionResponse.asString()).getAsJsonObject();
			cobrandSubscriptionArr = responseObject.getAsJsonArray("customerSubscription");
			for (int i = 0; i < cobrandSubscriptionArr.size(); i++) {
				insightObject = cobrandSubscriptionArr.get(i).getAsJsonObject();
				if (insightObject.get("insightName").getAsString().equals(expectedResultValues.getInsightName()))
					break;
			}
			configurationObjArray = insightObject.getAsJsonArray("customerConfiguration");
		}
		// Set<String> cobrandSubscriptionEntities = configurationObject.keySet();
		Entities cobrandEntities = new Entities();
		ArrayList<Entity> cobrandEntityList = new ArrayList<Entity>();
		// for (String entity : cobrandSubscriptionEntities) {
		for (int k = 0; k < configurationObjArray.size(); k++) {
			String entity = ((JsonObject) configurationObjArray.get(k)).get("entityType").getAsString();
			Entity cobrandEntity = new Entity();
			System.out.println("Supported Insight Entity " + entity);
			JsonObject entityObject = ((JsonObject) configurationObjArray.get(k));
			cobrandEntity.setEntityType(entity);
			if (entityObject.has("isSubscribed")) {
				cobrandEntity.setSubscribed(entityObject.get("isSubscribed").getAsBoolean());
			}
			if (entityObject.has("frequency")) {
				cobrandEntity.setFrequency(entityObject.get("frequency").getAsString());
			}
			if (entityObject.has("insightType")) {
				cobrandEntity.setInsightType(entityObject.get("insightType").getAsString());
			}
			if (entityObject.has("duration")) {
				cobrandEntity.setDuration(entityObject.get("duration").getAsString());
			}
			ArrayList<Threshold> thresholdList = new ArrayList<Threshold>();
			if (entityObject.has("threshold")) {
				JsonArray thresholdArr = entityObject.getAsJsonArray("threshold");
				Threshold threshold = null;
				for (int i = 0; i < thresholdArr.size(); i++) {
					threshold = new Threshold();
					threshold.setName(thresholdArr.get(i).getAsJsonObject().get("name").getAsString());
					threshold.setValue(thresholdArr.get(i).getAsJsonObject().get("value").getAsString());
					threshold.setType(thresholdArr.get(i).getAsJsonObject().get("type").getAsString());
					thresholdList.add(threshold);
				}
				cobrandEntity.setThresholds(thresholdList);
			}
			cobrandEntityList.add(cobrandEntity);
		}
		cobrandEntities.setEntities(cobrandEntityList);
		subscriptionsMap.put("customerSubscription", cobrandEntities);
		expectedResultValues.setSubscriptions(subscriptionsMap);
		System.out.println("check");

		return expectedResultValues;

	}

	public String constructCobrandandUserPatchRequest(String SubscriptionCtx, String insightName,
			String entityParameterName, String thresholdNameValueType, String frequency, String duration,
			String editSubscription, String isUserSubscribed, String patchEntityIds,
			HashMap<String, String> entityIdsMap) {
		Subscriptions insightSubscriptions = new Subscriptions();
		String patchDiffEntityIds = getEntityIdsToPatch(patchEntityIds, entityIdsMap);
		ArrayList<Subscription> subscriptionsList = new ArrayList<Subscription>();
		Subscription userSubscription = new Subscription();
		Gson gson = new Gson();
		if (!insightName.isEmpty()) {
			userSubscription.setInsightName(insightName);
		}
		String[] entities = entityParameterName.split("\\s+");
		Set<String> entitiesSet = new HashSet<>(Arrays.asList(entities));
		com.yodlee.insights.subscriptions.pojo.Entity accountEntity = null;
		com.yodlee.insights.subscriptions.pojo.Entity goalEntity = null;
		com.yodlee.insights.subscriptions.pojo.Entity viewEntity = null;
		com.yodlee.insights.subscriptions.pojo.Entity accountGroupEntity = null;
		ArrayList<com.yodlee.insights.subscriptions.pojo.Entity> subscriptionEntityList = new ArrayList<com.yodlee.insights.subscriptions.pojo.Entity>();
		ArrayList<EntityDetail> entityDetailList = null;
		ArrayList<EntityDetail> accountEntityDetailList = null;
		ArrayList<EntityDetail> viewEntityDetailList = null;
		ArrayList<EntityDetail> goalEntityDetailList = null;
		ArrayList<EntityDetail> accountGroupEntityDetailList = null;
		String[] thresholds = thresholdNameValueType.split("\\s+");
		List<String> thresholdValList = new ArrayList<String>(Arrays.asList(thresholds));
		HashSet<com.yodlee.insights.subscriptions.pojo.Threshold> thresholdsList = null, accountThresholdsList = null,
				viewThresholdsList = null, goalThresholdsList = null, accountGroupThresholdsList = null;
		int thresholdIndex = 0;
		EntityDetail definedEntity = null;
		String entityName = null;
		for (int i = 0; i < entities.length; i++) {
			entityName = entities[i].toString();
			accountThresholdsList = new HashSet<com.yodlee.insights.subscriptions.pojo.Threshold>();
			viewThresholdsList = new HashSet<com.yodlee.insights.subscriptions.pojo.Threshold>();
			goalThresholdsList = new HashSet<com.yodlee.insights.subscriptions.pojo.Threshold>();
			accountGroupThresholdsList = new HashSet<com.yodlee.insights.subscriptions.pojo.Threshold>();
			if (accountEntity == null && entities[i].equals("account")) {
				accountEntityDetailList = new ArrayList<EntityDetail>();
				accountEntity = new com.yodlee.insights.subscriptions.pojo.Entity();
			}
			if (viewEntity == null && entities[i].equals("view")) {
				viewEntityDetailList = new ArrayList<EntityDetail>();
				viewEntity = new com.yodlee.insights.subscriptions.pojo.Entity();
			}
			if (goalEntity == null && entities[i].equals("goal")) {
				goalEntityDetailList = new ArrayList<EntityDetail>();
				goalEntity = new com.yodlee.insights.subscriptions.pojo.Entity();
			}
			if (accountGroupEntity == null && entities[i].equals("accountGroup")) {
				accountGroupEntityDetailList = new ArrayList<EntityDetail>();
				accountGroupEntity = new com.yodlee.insights.subscriptions.pojo.Entity();
			}
			entityDetailList = new ArrayList<EntityDetail>();
			if (supportingEntiesList.contains(entities[i])) {
				definedEntity = new EntityDetail();
				String[] durations = duration.split("\\s+");
				if (!duration.isEmpty()) {
					definedEntity.setDuration(durations[i]);
				}
				String[] frequencies = frequency.split("\\s+");
				if (!frequency.isEmpty()) {
					definedEntity.setFrequency(frequencies[i]);
				}
				String[] subscriptions = isUserSubscribed.split("\\s+");
				if (!isUserSubscribed.isEmpty()) {
					definedEntity.setSubscribed(Boolean.parseBoolean(subscriptions[i]));
				}
				String[] patchEntites = patchDiffEntityIds.split("\\s+");
				if (!patchDiffEntityIds.isEmpty()) {
					definedEntity.setId(patchEntites[i]);
				}
				com.yodlee.insights.subscriptions.pojo.Threshold entityThreshold = null;
				int thresholdLength = thresholds.length;
				if (thresholds[0].length() > 2 && thresholds.length > 0) {
					for (int j = 0; j < thresholdLength; j++) {
						entityThreshold = new com.yodlee.insights.subscriptions.pojo.Threshold();
						if (thresholdIndex < thresholdValList.size()) {
							String[] threshold = thresholdValList.get(thresholdIndex++).toString().split(",");
							entityThreshold.setName(threshold[0]);
							entityThreshold.setValue(threshold[1]);
							entityThreshold.setType(threshold[2]);
							if (entities[i].equals("account")) {
								accountThresholdsList.add(entityThreshold);
								definedEntity.setThresholds(accountThresholdsList);
							}
							if (entities[i].equals("view")) {
								viewThresholdsList.add(entityThreshold);
								definedEntity.setThresholds(viewThresholdsList);
							}
							if (entities[i].equals("goal")) {
								goalThresholdsList.add(entityThreshold);
								definedEntity.setThresholds(goalThresholdsList);
							}
							if (entities[i].equals("accountGroup")) {
								accountGroupThresholdsList.add(entityThreshold);
								definedEntity.setThresholds(accountGroupThresholdsList);
							}
							if (entities.length == thresholdValList.size()) {
								if (accountThresholdsList.size() > 0)
									accountEntityDetailList.add(definedEntity);
								if (viewThresholdsList.size() > 0)
									viewEntityDetailList.add(definedEntity);
								if (goalThresholdsList.size() > 0)
									goalEntityDetailList.add(definedEntity);
								if (accountGroupThresholdsList.size() > 0)
									accountGroupEntityDetailList.add(definedEntity);
								break;
							}
						}
						if (thresholdLength / 2 == entities.length)
							thresholdLength = 2;

					}
					if (entities.length != thresholdValList.size()) {
						if (accountThresholdsList.size() > 0)
							accountEntityDetailList.add(definedEntity);
						if (viewThresholdsList.size() > 0)
							viewEntityDetailList.add(definedEntity);
						if (goalThresholdsList.size() > 0)
							goalEntityDetailList.add(definedEntity);
						if (accountGroupThresholdsList.size() > 0)
							accountGroupEntityDetailList.add(definedEntity);
					}
				} else {
					if (entityName.equals("account")) {
						accountEntityDetailList.add(definedEntity);
					}
					if (entityName.equals("view")) {
						viewEntityDetailList.add(definedEntity);
					}
					if (entityName.equals("goal")) {
						goalEntityDetailList.add(definedEntity);
					}
					if (entityName.equals("accountGroup")) {
						accountGroupEntityDetailList.add(definedEntity);
					}

				}

			}
		}
		JSONObject accountSubscriptionsArrayObject = new JSONObject();
		JSONObject viewSubscriptionsArrayObject = new JSONObject();
		JSONObject entityParameterObject = new JSONObject();
		if (accountEntity != null) {
			accountEntity.setAccount(accountEntityDetailList);
			subscriptionEntityList.add(accountEntity);
			entityParameterObject.put("account", accountEntityDetailList);
		}
		if (viewEntity != null) {
			viewEntity.setView(viewEntityDetailList);
			subscriptionEntityList.add(viewEntity);
			entityParameterObject.put("view", viewEntityDetailList);
		}
		if (goalEntity != null) {
			goalEntity.setAccount(goalEntityDetailList);
			subscriptionEntityList.add(goalEntity);
			entityParameterObject.put("goal", goalEntityDetailList);
		}
		if (accountGroupEntity != null) {
			entityParameterObject.put("accountGroup", accountGroupEntityDetailList);
		}
		JSONObject patchSubscriptionPayload = new JSONObject(); // {}
		JSONArray subscriptionsArray = new JSONArray(); // { userSubscriptions[] }
		JSONObject subscriptionsArrayObject = new JSONObject();
		subscriptionsArrayObject.put("name", insightName);
		subscriptionsArrayObject.put("entityParameter", entityParameterObject);
		subscriptionsArray.add(subscriptionsArrayObject);
		String patchSubscriptionJson = null;
		if (SubscriptionCtx.equals("Cobrand")) {
			patchSubscriptionPayload.put("cobrandSubscription", subscriptionsArray);
			patchSubscriptionJson = patchSubscriptionPayload.toString().replace("subscribed", "isSubscribed")
					.replace("thresholds", "threshold");
			System.out.println("Cobrand Subscription Json Payload = " + patchSubscriptionJson);
		}
		if (SubscriptionCtx.equals("User")) {
			patchSubscriptionPayload.put("userSubscription", subscriptionsArray);
			patchSubscriptionJson = patchSubscriptionPayload.toString().replace("subscribed", "isSubscribed")
					.replace("thresholds", "threshold");
			System.out.println("User Subscription Json Payload = " + patchSubscriptionJson);
		}

		return patchSubscriptionJson;
	}

	public String getEntityIdsToPatch(String patchAccountIds, HashMap<String, String> entityIdsMap) {
		StringBuffer entityIds = new StringBuffer();
		if (!patchAccountIds.isEmpty()) {
			String[] itemAccounts = patchAccountIds.split("\\s+");

			for (int i = 0; i < itemAccounts.length; i++) {
				String itemAccountId = entityIdsMap.get(itemAccounts[i]);
				if (i != itemAccounts.length - 1) {
					entityIds.append(itemAccountId);
					entityIds.append("   ");
				} else {
					entityIds.append(itemAccountId);
				}
			}
		}
		return entityIds.toString();
	}
}