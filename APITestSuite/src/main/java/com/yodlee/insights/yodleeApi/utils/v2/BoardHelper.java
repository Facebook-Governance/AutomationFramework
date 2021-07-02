/*******************************************************************************
 *
 * * Copyright (c) 2019 Yodlee, Inc. All Rights Reserved.
 *
 * *
 *
 * * This software is the confidential and proprietary information of Yodlee, Inc.
 *
 * * Use is subject to license terms.
 *
 *******************************************************************************/
package com.yodlee.insights.yodleeApi.utils.v2;

import java.io.FileReader;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import org.json.JSONArray;
import org.json.JSONObject;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.yodlee.insights.views.pojo.AmountRange;
import com.yodlee.insights.views.pojo.Budget;
import com.yodlee.insights.views.pojo.BudgetAmount;
import com.yodlee.insights.views.pojo.CommonEntity;
import com.yodlee.insights.views.pojo.CreateView;
import com.yodlee.insights.views.pojo.Exclude;
import com.yodlee.insights.views.pojo.Include;
import com.yodlee.insights.views.pojo.Rule;
import com.yodlee.insights.views.pojo.View;
import com.yodlee.yodleeApi.pojo.HttpMethodParameters;
import com.yodlee.yodleeApi.utils.apiUtils.BoardUtils;

import io.restassured.response.Response;

public class BoardHelper {

	JsonObject viewsRulesObject = null;
	JsonParser jsonParser = new JsonParser();
	SimpleDateFormat formatter = null;
	InsightsHelper insightsHelper = new InsightsHelper();
	Date date = null;
	BoardUtils boardUtil = new BoardUtils();
	HashMap<String, String> entityIdsMap = null;

	public BoardHelper() {
		try {
			viewsRulesObject = (JsonObject) jsonParser.parse(new FileReader(System.getProperty("user.dir")
					+ "\\src\\test\\resources\\TestData\\CSVFiles\\Insights\\ViewsRules\\Rules.json"));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public String createBoardJsonBody(String name, String description, String type_rule, String amount, String currency,
			String budgetType, String ruleName, String fromDate, String toDate, String fromAmount, String toAmount,
			String merchantType) {
		JSONArray nameArray = new JSONArray();
		JSONObject mainBoardObj = new JSONObject();
		JSONObject nameObj = new JSONObject();
		JSONObject budgetObj = new JSONObject();
		JSONObject budgetAmount = new JSONObject();

		nameObj.put("name", name);
		nameObj.put("description", description);
		budgetAmount.put("amount", amount);
		budgetAmount.put("currency", currency);
		budgetObj.put("budgetAmount", budgetAmount);
		budgetObj.put("budgetType", budgetType);
		nameObj.put("budget", budgetObj);

		JSONObject ruleObj = new JSONObject();

		JSONArray ruleArray = new JSONArray();
		ruleObj.put("name", ruleName);

		ruleObj.put("fromDate", getOffsetDate(fromDate));
		ruleObj.put("toDate", getOffsetDate(toDate));
		ruleObj.put("type", type_rule);

		JSONObject elementObject = new JSONObject();

		// fromAmount,toAmount
		JSONObject fromToAmount = new JSONObject();
		fromToAmount.put("fromAmount", fromAmount);
		fromToAmount.put("toAmount", toAmount);
		fromToAmount.put("currency", currency);

		JSONArray merchantTypeArray = new JSONArray();
		merchantTypeArray.put(merchantType);
		if (!merchantType.contains("EMPTY")) {
			elementObject.put("merchantType", merchantTypeArray);
		}
		elementObject.put("amountRange", fromToAmount);

		ruleObj.put("include", elementObject);
		ruleArray.put(ruleObj);

		nameObj.put("rule", ruleArray);
		nameArray.put(nameObj);
		mainBoardObj.put("board", nameArray);
		String createBoardJson = mainBoardObj.toString();
		System.out.println(createBoardJson);
		return createBoardJson;
	}

	public String addRuleName(String TestCaseId, String TestCase, String name, String description, String type_rule,
			String type, String amount, String currency, String budgetType, String ruleName, String fromDate,
			String toDate, String merchantName, Long categoryId, String merchantTypeBiller, String merchantTypeSub,
			Long transactionId, String accountId) {
		JSONArray nameArray = new JSONArray();
		JSONObject mainBoardObj = new JSONObject();
		JSONObject nameObj = new JSONObject();
		JSONObject budgetObj = new JSONObject();
		JSONObject budgetAmount = new JSONObject();

		nameObj.put("name", name);
		nameObj.put("description", description);
		budgetAmount.put("amount", amount);
		budgetAmount.put("currency", currency);
		budgetObj.put("budgetAmount", budgetAmount);
		budgetObj.put("budgetType", budgetType);
		nameObj.put("budget", budgetObj);

		JSONObject ruleObj = new JSONObject();

		JSONArray ruleArray = new JSONArray();
		if (!ruleName.contains("EMPTY")) {
			ruleObj.put("name", ruleName);
		}
		ruleObj.put("fromDate", getOffsetDate(fromDate));
		ruleObj.put("toDate", getOffsetDate(toDate));
		ruleObj.put("type", type_rule);

		JSONObject elementObject = new JSONObject();
		JSONArray merchantArray = new JSONArray();
		merchantArray.put(merchantName);
		JSONArray categoryArray = new JSONArray();
		categoryArray.put(categoryId);
		JSONArray accountArray = new JSONArray();
		accountArray.put(accountId);
		JSONArray merchantTypeArray = new JSONArray();
		merchantTypeArray.put(merchantTypeBiller);
		if (!merchantTypeSub.contains("EMPTY")) {
			merchantTypeArray.put(merchantTypeSub);
		}
		elementObject.put("merchantName", merchantArray);
		elementObject.put("categoryId", categoryArray);
		elementObject.put("accountId", accountArray);
		elementObject.put("merchantType", merchantTypeArray);
		ruleObj.put("include", elementObject);

		ruleArray.put(ruleObj);

		/*
		 * JSONObject elementObj = new JSONObject(); JSONObject ruleTest = new
		 * JSONObject(); ruleTest.put("type", type); JSONArray transArray = new
		 * JSONArray(); transArray.put(transactionId);
		 * elementObj.put("transactionId",transArray);
		 * ruleTest.put("include",elementObj); ruleArray.put(ruleTest);
		 */
		nameObj.put("rule", ruleArray);
		nameArray.put(nameObj);

		mainBoardObj.put("board", nameArray);
		String createBoardJson = mainBoardObj.toString();
		System.out.println(createBoardJson);
		return createBoardJson;
	}

	public String addMerchantType(String TestCaseId, String TestCase, String name, String description, String type_rule,
			String type, String amount, String currency, String budgetType, String ruleName, String fromDate,
			String toDate, String merchantName, Long categoryId, String merchantTypeBiller, String merchantTypeSub,
			Long transactionId, String accountId) {
		JSONArray nameArray = new JSONArray();
		JSONObject mainBoardObj = new JSONObject();
		JSONObject nameObj = new JSONObject();
		JSONObject budgetObj = new JSONObject();
		JSONObject budgetAmount = new JSONObject();

		nameObj.put("name", name);
		nameObj.put("description", description);
		budgetAmount.put("amount", amount);
		budgetAmount.put("currency", currency);
		budgetObj.put("budgetAmount", budgetAmount);
		budgetObj.put("budgetType", budgetType);
		nameObj.put("budget", budgetObj);

		JSONObject ruleObj = new JSONObject();

		JSONArray ruleArray = new JSONArray();
		if (!ruleName.contains("EMPTY")) {
			ruleObj.put("name", ruleName);
		}
		ruleObj.put("fromDate", getOffsetDate(fromDate));
		ruleObj.put("toDate", getOffsetDate(toDate));
		ruleObj.put("type", type_rule);

		JSONObject elementObject = new JSONObject();
		JSONArray merchantArray = new JSONArray();
		merchantArray.put(merchantName);
		JSONArray categoryArray = new JSONArray();
		categoryArray.put(categoryId);
		JSONArray accountArray = new JSONArray();
		accountArray.put(accountId);
		JSONArray merchantTypeArray = new JSONArray();
		if (!merchantTypeBiller.contains("EMPTY")) {
			merchantTypeArray.put(merchantTypeBiller);
		}
		if (!merchantTypeSub.contains("EMPTY")) {
			merchantTypeArray.put(merchantTypeSub);
		}
		if (!merchantName.contains("EMPTY")) {
			elementObject.put("merchantName", merchantArray);
		}
		if (!(name.contains("MERCHANTTYPE_TRANSACTION_ID") || (name.contains("ONLY_MERCHANT_TYPE")))) {
			elementObject.put("categoryId", categoryArray);
		}
		if (!(name.contains("MERCHANTTYPE_TRANSACTION_ID") || (name.contains("ONLY_MERCHANT_TYPE")))) {
			elementObject.put("accountId", accountArray);
		}

		elementObject.put("merchantType", merchantTypeArray);
		ruleObj.put("include", elementObject);

		if (name.contains("MERCHANTTYPE_TRANSACTION_ID")) {
			JSONArray transArray = new JSONArray();
			transArray.put(transactionId);
			elementObject.put("transactionId", transArray);
			ruleObj.put("include", elementObject);
			ruleArray.put(ruleObj);
		} else {
			ruleArray.put(ruleObj);
		}
		nameObj.put("rule", ruleArray);
		nameArray.put(nameObj);
		mainBoardObj.put("board", nameArray);
		String createBoardJson = mainBoardObj.toString();
		System.out.println(createBoardJson);
		return createBoardJson;
	}

	public String addCityStateAmount(String TestCaseId, String TestCase, String name, String description,
			String type_rule, String type, String amount, String currency, String budgetType, String ruleName,
			String fromDate, String toDate, String merchantName, Long categoryId, String merchantTypeBiller,
			String merchantTypeSub, Long transactionId, String accountId, String city, String state, String fromAmount,
			String toAmount) {
		JSONArray nameArray = new JSONArray();
		JSONObject mainBoardObj = new JSONObject();
		JSONObject nameObj = new JSONObject();
		JSONObject budgetObj = new JSONObject();
		JSONObject budgetAmount = new JSONObject();

		nameObj.put("name", name);
		nameObj.put("description", description);
		budgetAmount.put("amount", amount);
		budgetAmount.put("currency", currency);
		budgetObj.put("budgetAmount", budgetAmount);
		budgetObj.put("budgetType", budgetType);
		nameObj.put("budget", budgetObj);

		JSONObject ruleObj = new JSONObject();

		JSONArray ruleArray = new JSONArray();
		if (!ruleName.contains("EMPTY")) {
			ruleObj.put("name", ruleName);
		}
		ruleObj.put("fromDate", getOffsetDate(fromDate));
		ruleObj.put("toDate", getOffsetDate(toDate));
		ruleObj.put("type", type_rule);

		JSONObject elementObject = new JSONObject();
		JSONArray merchantArray = new JSONArray();
		merchantArray.put(merchantName);
		JSONArray categoryArray = new JSONArray();
		categoryArray.put(categoryId);
		JSONArray accountArray = new JSONArray();
		accountArray.put(accountId);
		JSONArray merchantTypeArray = new JSONArray();
		merchantTypeArray.put(merchantTypeBiller);
		merchantTypeArray.put(merchantTypeSub);
		elementObject.put("merchantName", merchantArray);
		elementObject.put("categoryId", categoryArray);
		elementObject.put("accountId", accountArray);
		elementObject.put("merchantType", merchantTypeArray);

		// city,state
		JSONArray cityArray = new JSONArray();
		cityArray.put(city);
		elementObject.put("city", cityArray);
		JSONArray stateArray = new JSONArray();
		stateArray.put(state);
		elementObject.put("state", stateArray);

		// fromAmount,toAmount
		JSONObject fromToAmount = new JSONObject();
		System.out.println("fromAmount::" + fromAmount);
		System.out.println("toAmount::" + toAmount);

		fromToAmount.put("fromAmount", fromAmount);
		fromToAmount.put("toAmount", toAmount);
		fromToAmount.put("currency", currency);
		elementObject.put("amountRange", fromToAmount);

		ruleObj.put("include", elementObject);

		if (name.contains("MERCHANTTYPE_TRANSACTION_ID")) {
			JSONArray transArray = new JSONArray();
			transArray.put(transactionId);
			elementObject.put("transactionId", transArray);
			ruleObj.put("include", elementObject);
			ruleArray.put(ruleObj);
		} else {
			ruleArray.put(ruleObj);
		}
		nameObj.put("rule", ruleArray);
		nameArray.put(nameObj);
		mainBoardObj.put("board", nameArray);
		String createBoardJson = mainBoardObj.toString();
		System.out.println(createBoardJson);
		return createBoardJson;
	}

	public String createBoardRequestBody(String name, String description, String type_rule, String type, String amount,
			String currency, String budgetType, String fromDate, String toDate, String merchantName, Long categoryId,
			Long categoryId1, Long categoryId2, Long accountId, Long transactionId, Long transactionId1) {
		JSONArray nameArray = new JSONArray();
		JSONObject mainBoardObj = new JSONObject();
		JSONObject nameObj = new JSONObject();
		JSONObject budgetObj = new JSONObject();
		if (name.length() > 0) {
			nameObj.put("name", name);
		}
		if (description.length() > 0) {
			nameObj.put("description", description);
		}
		budgetObj.put("amount", amount);
		budgetObj.put("currency", currency);
		budgetObj.put("budgetType", budgetType);
		nameObj.put("budget", budgetObj);

		JSONObject ruleObj = new JSONObject();
		JSONArray ruleArray = new JSONArray();
		if (fromDate.length() > 0 && toDate.length() > 0) {
			ruleObj.put("fromDate", getOffsetDate(fromDate));
			ruleObj.put("toDate", getOffsetDate(toDate));
		}
		if (type_rule.length() > 0) {
			ruleObj.put("type", type_rule);
		}

		JSONObject elementObject = new JSONObject();
		JSONArray merchantArray = new JSONArray();
		merchantArray.put(merchantName);
		JSONArray categoryArray = new JSONArray();
		categoryArray.put(categoryId);
		categoryArray.put(categoryId1);
		categoryArray.put(categoryId2);
		JSONArray accountArray = new JSONArray();
		accountArray.put(accountId);
		elementObject.put("merchantName", merchantArray);
		elementObject.put("categoryId", categoryArray);
		elementObject.put("accountId", accountArray);

		if (name.contains("TRANSID_INRULE")) {
			JSONArray transArrayRule = new JSONArray();
			transArrayRule.put(transactionId);
			transArrayRule.put(transactionId1);
			elementObject.put("transactionId", transArrayRule);
		}
		if (!name.contains("ONLY_TRANSACTIONID")) {
			ruleObj.put("include", elementObject);
		}
		ruleArray.put(ruleObj);

		JSONObject elementObj = new JSONObject();
		JSONObject ruleTest = new JSONObject();
		ruleTest.put("type", type);
		JSONArray transArray = new JSONArray();
		transArray.put(transactionId);
		transArray.put(transactionId1);
		elementObj.put("transactionId", transArray);
		ruleTest.put("include", elementObj);
		ruleArray.put(ruleTest);
		if (merchantName.length() > 0) {
			nameObj.put("rule", ruleArray);
		}
		nameArray.put(nameObj);

		mainBoardObj.put("board", nameArray);
		String createBoardJson = mainBoardObj.toString();
		System.out.println(createBoardJson);
		return createBoardJson;
	}

	public String updateBoardRequestBody(String name, String description, String type_rule, String type, String amount,
			String currency, String budgetType, String fromDate, String toDate, String merchantName, Long categoryId,
			Long accountId, Long transactionId) {
		JSONArray nameArray = new JSONArray();
		JSONObject mainBoardObj = new JSONObject();
		JSONObject nameObj = new JSONObject();
		JSONObject budgetObj = new JSONObject();
		if (name.length() > 0) {
			nameObj.put("name", name);
		}

		if (description.contains("DESC_LESS_THANONE")) {
			description = description.replace("DESC_LESS_THANONE", "");
			nameObj.put("description", description);
		} else if (description.length() > 0) {
			nameObj.put("description", description);
		}

		if (amount.contains("WITHOUT_AMOUNT")) {
			amount = amount.replace("WITHOUT_AMOUNT", "");
		} else {
			budgetObj.put("amount", amount);
		}

		if (currency.contains("EMPTY_CURRENCY")) {
			currency = currency.replace("EMPTY_CURRENCY", "");
			budgetObj.put("currency", currency);
		} else if (currency.contains("WITHOUT_CURRENCY")) {
			currency = currency.replace("WITHOUT_CURRENCY", "");
		} else {
			budgetObj.put("currency", currency);
		}
		if (budgetType.length() > 0) {
			budgetObj.put("budgetType", budgetType);
		}
		nameObj.put("budget", budgetObj);

		JSONObject ruleObj = new JSONObject();
		JSONArray ruleArray = new JSONArray();
		if (name.contains("DIFF_DATEFORMAT")) {
			ruleObj.put("fromDate", diffDateFormat(fromDate));
			ruleObj.put("toDate", diffDateFormat(toDate));
		} else if (fromDate.length() > 0 && toDate.length() > 0) {
			ruleObj.put("fromDate", getOffsetDate(fromDate) + "T02:15:11");
			ruleObj.put("toDate", getOffsetDate(toDate) + "T02:15:11");
		}

		if (type_rule.length() > 0) {
			ruleObj.put("type", type_rule);
		}

		JSONObject elementObject = new JSONObject();
		JSONArray merchantArray = new JSONArray();

		if (name.contains("DUPLICATE_MERCHANT_NAME")) {
			String dupMerchant[] = merchantName.split(",");
			String merchantName1 = dupMerchant[0];
			String merchantName2 = dupMerchant[1];
			merchantArray.put(merchantName1);
			merchantArray.put(merchantName2);
		} else {
			merchantArray.put(merchantName);
		}
		JSONArray categoryArray = new JSONArray();
		categoryArray.put(categoryId);
		JSONArray accountArray = new JSONArray();
		accountArray.put(accountId);

		if (!name.contains("accountId/categoryId/merchantName")) {
			elementObject.put("merchantName", merchantArray);
		}
		elementObject.put("categoryId", categoryArray);
		elementObject.put("accountId", accountArray);

		if (name.contains("TRANSID_INRULE")) {
			JSONArray transArrayRule = new JSONArray();
			transArrayRule.put(transactionId);
			elementObject.put("transactionId", transArrayRule);
		}
		ruleObj.put("include", elementObject);
		ruleArray.put(ruleObj);

		JSONObject elementObj = new JSONObject();
		JSONObject ruleTest = new JSONObject();
		ruleTest.put("type", type);
		JSONArray transArray = new JSONArray();
		transArray.put(transactionId);
		elementObj.put("transactionId", transArray);
		ruleTest.put("include", elementObj);
		ruleArray.put(ruleTest);
		if (merchantName.length() > 0) {
			nameObj.put("rule", ruleArray);
		}
		nameArray.put(nameObj);

		mainBoardObj.put("board", nameArray);
		String createBoardJson = mainBoardObj.toString();
		System.out.println(createBoardJson);
		return createBoardJson;
	}

	public String createBoardJsonBodyForBoardType(String name, String description, String boardType, String type_rule,
			String type, String amount, String currency, String budgetType, String fromDate, String toDate,
			String merchantName, String categoryId, String accountId, String transactionId) {
		JSONArray nameArray = new JSONArray();
		JSONObject mainBoardObj = new JSONObject();
		JSONObject nameObj = new JSONObject();
		JSONObject budgetObj = new JSONObject();
		nameObj.put("name", name);
		nameObj.put("BoardType", boardType);
		nameObj.put("description", description);
		if (!amount.contains(" ")) {
			budgetObj.put("amount", amount);
		}
		if (!currency.contains(" ")) {
			budgetObj.put("currency", currency);
		}
		if (!budgetType.contains(" ")) {
			budgetObj.put("budgetType", budgetType);
		}
		nameObj.put("budget", budgetObj);

		JSONObject ruleObj = new JSONObject();
		JSONArray ruleArray = new JSONArray();
		ruleObj.put("fromDate", getOffsetDate(fromDate));
		ruleObj.put("toDate", getOffsetDate(toDate));
		ruleObj.put("type", type_rule);

		JSONObject elementObject = new JSONObject();
		JSONArray merchantArray = new JSONArray();
		merchantArray.put(merchantName);
		JSONArray categoryArray = new JSONArray();
		categoryArray.put(categoryId);
		JSONArray accountArray = new JSONArray();
		accountArray.put(accountId);
		elementObject.put("merchantName", merchantArray);
		elementObject.put("categoryId", categoryArray);
		elementObject.put("accountId", accountArray);
		ruleObj.put("include", elementObject);
		ruleArray.put(ruleObj);

		JSONObject elementObj = new JSONObject();
		JSONObject ruleTest = new JSONObject();
		ruleTest.put("type", type);
		JSONArray transArray = new JSONArray();
		transArray.put(transactionId);
		elementObj.put("transactionId", transArray);
		ruleTest.put("include", elementObj);
		ruleArray.put(ruleTest);
		nameObj.put("rule", ruleArray);
		nameArray.put(nameObj);

		mainBoardObj.put("board", nameArray);
		String createBoardTypeJson = mainBoardObj.toString();
		System.out.println(createBoardTypeJson);
		return createBoardTypeJson;
	}

	public String updateBoardJsonBody(String name, String description, String boardType, String ruleId,
			String type_rule, String type, String amount, String currency, String budgetType, String fromDate,
			String toDate, String merchantName, String categoryId, String accountId, String transactionId) {
		JSONArray nameArray = new JSONArray();
		JSONObject mainBoardObj = new JSONObject();
		JSONObject nameObj = new JSONObject();
		JSONObject budgetObj = new JSONObject();
		nameObj.put("name", name);
		nameObj.put("BoardType", boardType);
		nameObj.put("description", description);
		if (!amount.contains(" ")) {
			budgetObj.put("amount", amount);
		}
		if (!currency.contains(" ")) {
			budgetObj.put("currency", currency);
		}
		if (!budgetType.contains(" ")) {
			budgetObj.put("budgetType", budgetType);
		}
		nameObj.put("budget", budgetObj);

		JSONObject ruleObj = new JSONObject();
		JSONArray ruleArray = new JSONArray();
		ruleObj.put("fromDate", getOffsetDate(fromDate));
		ruleObj.put("toDate", getOffsetDate(toDate));
		ruleObj.put("type", type_rule);

		JSONObject elementObject = new JSONObject();
		JSONArray merchantArray = new JSONArray();
		merchantArray.put(merchantName);
		JSONArray categoryArray = new JSONArray();
		categoryArray.put(categoryId);
		JSONArray accountArray = new JSONArray();
		accountArray.put(accountId);
		elementObject.put("merchantName", merchantArray);
		elementObject.put("categoryId", categoryArray);
		elementObject.put("accountId", accountArray);
		elementObject.put("ruleId", ruleId);

		ruleObj.put("include", elementObject);
		ruleArray.put(ruleObj);

		JSONObject elementObj = new JSONObject();
		JSONObject ruleTest = new JSONObject();
		ruleTest.put("type", type);
		JSONArray transArray = new JSONArray();
		transArray.put(transactionId);
		elementObj.put("transactionId", transArray);
		ruleTest.put("include", elementObj);
		ruleArray.put(ruleTest);
		nameObj.put("rule", ruleArray);
		nameArray.put(nameObj);

		mainBoardObj.put("board", nameArray);
		String updateBoardJson = mainBoardObj.toString();
		System.out.println(updateBoardJson);
		return updateBoardJson;
	}

	/**
	 * @param date
	 * @return
	 */
	public String getOffsetDate(String date) {
		String dataValue = null;
		try {
			if (date != null && date.length() > 0 && date.contains(",")) {

				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
				Calendar cal = Calendar.getInstance();
				cal.setTime(new Date());
				String dateAddition[] = date.split(",");
				cal.add(Calendar.YEAR, Integer.parseInt(dateAddition[0]));
				cal.add(Calendar.MONTH, Integer.parseInt(dateAddition[1]));
				cal.add(Calendar.DATE, Integer.parseInt(dateAddition[2]));
				dataValue = sdf.format(cal.getTime());

			} else {
				return date;
			}
		} catch (Exception e) {
			dataValue = date;
		}
		return dataValue;
	}

	/**
	 * @param date
	 * @return
	 */
	public String diffDateFormat(String date) {
		String dataValue = null;
		try {
			if (date != null && date.length() > 0 && date.contains(",")) {

				SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
				Calendar cal = Calendar.getInstance();
				cal.setTime(new Date());
				String dateAddition[] = date.split(",");
				cal.add(Calendar.YEAR, Integer.parseInt(dateAddition[0]));
				cal.add(Calendar.MONTH, Integer.parseInt(dateAddition[1]));
				cal.add(Calendar.DATE, Integer.parseInt(dateAddition[2]));
				dataValue = sdf.format(cal.getTime());

			} else {
				return date;
			}
		} catch (Exception e) {
			dataValue = date;
		}
		System.out.println("dataValuedataValue::" + dataValue);
		return dataValue;
	}

	public void createViewRules(String scenario, CommonEntity commonEntity) throws IOException {
		Gson gson = new Gson();
		HttpMethodParameters httpParams = HttpMethodParameters.builder().build();
		CreateView createView = new CreateView();
		createView.setView(createView(scenario, commonEntity));
		String viewsRequestJson = gson.toJson(createView);
		System.out.println("View's Request Payload  " + viewsRequestJson);
		httpParams = insightsHelper.getHttpParams("createViewRequest", null, null, commonEntity.getUserJwtToken(), null,
				null, null, null);
		httpParams.setBodyParams(viewsRequestJson);
		Response createViewResponse = boardUtil.createBoard(httpParams, null);
		String viewId = ((JsonObject) (((JsonObject) jsonParser.parse(createViewResponse.asString()))
				.getAsJsonArray("view").get(0))).get("id").getAsString();
		if ((entityIdsMap == null)) {
			entityIdsMap = new HashMap<String, String>();
		}
		if (!(commonEntity.getEntityIdsMap() == null)) {
			entityIdsMap = commonEntity.getEntityIdsMap();
		}

		entityIdsMap.put(scenario, viewId);
		commonEntity.setEntityIdsMap(entityIdsMap);
		System.out.println("View Id --> " + viewId);

	}

	private List<View> createView(String viewScenario, CommonEntity commonEntity) {

		List<View> viewsList = new ArrayList<View>();
		View view = new View();
		Budget budget = new Budget();
		BudgetAmount budgetAmount = new BudgetAmount();
		budget.setBudgetAmount(budgetAmount);
		view.setName(viewScenario);
		view.setDescription("Creating My view");
		view.setBudget(budget);
		view.setRule(createRules(viewScenario, commonEntity));
		viewsList.add(view);
		return viewsList;
	}

	private List<Rule> createRules(String viewScenario, CommonEntity commonEntity) {
		List<Rule> rulesList = new ArrayList<Rule>();
		List<String> viewsRulesDefinitionList = getMatchingViewsRuleScenario(viewScenario, commonEntity);
		System.out.println(viewsRulesDefinitionList);
		for (int i = 0; i < viewsRulesDefinitionList.size(); i++) {
			rulesList.add(createAndGetRules(viewsRulesDefinitionList.get(i), commonEntity));
		}
		return rulesList;
	}

	private List<String> getMatchingViewsRuleScenario(String viewScenario, CommonEntity commonEntity) {
		List<String> viewsRulesList = new ArrayList<String>();
		JsonArray viewsRulesArray = viewsRulesObject.getAsJsonArray("viewsRules");
		for (int i = 0; i < viewsRulesArray.size(); i++) {
			JsonObject viewsRuleObj = (JsonObject) viewsRulesArray.get(i);
			if (viewsRuleObj.get("viewsScenario").getAsString().equals(viewScenario)) {
				System.out.println("View Scenario " + viewsRuleObj.get("ruleDescription"));
				JsonArray toBeCreatedRulesArr = viewsRuleObj.getAsJsonArray("createRules");
				for (int j = 0; j < toBeCreatedRulesArr.size(); j++) {
					String modifiedRawRule = null;
					if (commonEntity.getViewRuleAccountNames() != null
							&& toBeCreatedRulesArr.get(j).getAsString().contains("replaceAccIds")) {
						modifiedRawRule = toBeCreatedRulesArr.get(j).getAsString().replace("replaceAccIds",
								commonEntity.getViewRuleAccountNames());
					}
					if (commonEntity.getViewRuleTransactionDetails() != null
							&& toBeCreatedRulesArr.get(j).getAsString().contains("replaceTxnIds")) {
						modifiedRawRule = toBeCreatedRulesArr.get(j).getAsString().replace("replaceTxnIds",
								commonEntity.getViewRuleTransactionDetails());
					}
					if (commonEntity.getViewRuleAmountRangeDetails() != null
							&& toBeCreatedRulesArr.get(j).getAsString().contains("replaceAmountRange")) {
						modifiedRawRule = toBeCreatedRulesArr.get(j).getAsString().replace("replaceAmountRange",
								commonEntity.getViewRuleAmountRangeDetails());
					}
					if (commonEntity.getViewRuleCategoryIdDetails() != null
							&& toBeCreatedRulesArr.get(j).getAsString().contains("replaceCatIds")) {
						modifiedRawRule = toBeCreatedRulesArr.get(j).getAsString().replace("replaceCatIds",
								commonEntity.getViewRuleCategoryIdDetails());
					}
					if (commonEntity.getViewRuleMerchantNameDetails() != null
							&& toBeCreatedRulesArr.get(j).getAsString().contains("replaceMerchantNames")) {
						modifiedRawRule = toBeCreatedRulesArr.get(j).getAsString().replace("replaceMerchantNames",
								commonEntity.getViewRuleMerchantNameDetails());
					}
					if (commonEntity.getViewRuleMerchantTypeDetails() != null
							&& toBeCreatedRulesArr.get(j).getAsString().contains("replaceMerchantTypes")) {
						modifiedRawRule = toBeCreatedRulesArr.get(j).getAsString().replace("replaceMerchantTypes",
								commonEntity.getViewRuleMerchantTypeDetails());
					}
					viewsRulesList.add(modifiedRawRule);
				}
				break;
			}
		}

		return viewsRulesList;
	}

//[name:myRule1,fromDate:30,toDate:99,type:INCOME,include:merchantName::Amazon#Flipkart^categoryId::7#26^accountId::accountName1#accountName2]	
	// merchantName::Amazon#Flipkar
	// categoryId::7#26
	// accountId::accountName1#accountName2

	private Rule createAndGetRules(String ruleExpression, CommonEntity commonEntity) {

		Rule rule = new Rule();
		String[] ruleProperties = ruleExpression.split(",");
		for (int i = 0; i < ruleProperties.length; i++) {
			String ruleProperty = ruleProperties[i];
			String key = ruleProperty.split(":")[0];
			String value = ruleProperty.split(":")[1];
			if (key.equals("name")) {
				rule.setName(value);
			}
			if (key.equals("fromDate")) {
				rule.setFromDate(processDate(value));
			}
			if (key.equals("toDate")) {
				rule.setToDate(processDate(value));
			}
			if (key.equals("type")) {
				rule.setType(value);
			}
			if (key.equals("include")) {
				Include include = new Include();
				String[] includeProperties = value.split("\\s+");
				for (int j = 0; j < includeProperties.length; j++) {
					String includeProperty = includeProperties[j];
					String includeKey = includeProperty.split("_")[0];
					String includeValue = includeProperty.split("_")[1];
					if (includeKey.equals("merchantName")) {
						include.setMerchantName(Arrays.asList(includeValue.split("#")));
					}
					if (includeKey.equals("categoryId")) {
						String[] categoryIds = includeValue.split("#");
						List<Integer> categoryIdsList = new ArrayList<Integer>();
						for (String catId : categoryIds) {
							categoryIdsList.add(Integer.parseInt(catId));
						}
						include.setCategoryId(categoryIdsList);
					}
					if (includeKey.equals("accountId")) {
						String[] accountIds = includeValue.split("#");
						List<Integer> accountIdsList = new ArrayList<Integer>();
						for (String accountId : accountIds) {
							accountIdsList.add(Integer.parseInt(commonEntity.getEntityIdsMap().get(accountId)));
						}
						include.setAccountId(accountIdsList);
					}
					if (includeKey.equals("transactionId")) {
						String[] transactionIds = includeValue.split("#");
						List<Integer> transactionIdsList = new ArrayList<Integer>();
						for (String txnId : transactionIds) {
							String accountName = txnId.split("@")[0].toString();
							int txnPos = Integer.parseInt((txnId.split("@")[1].substring(3)));
							String itemAccntTxnId = commonEntity.getItemAccountsToTxnsMap()
									.get(commonEntity.getEntityIdsMap().get(accountName).toString()).get(txnPos - 1)
									.toString();
							transactionIdsList.add(Integer.parseInt(itemAccntTxnId));
						}
						include.setTransactionId(transactionIdsList);
					}
					if (includeKey.equals("merchantType")) {
						include.setMerchantType(Arrays.asList(includeValue.split("#")));
					}
					if (includeKey.equals("city")) {
						include.setCity(Arrays.asList(includeValue.split("#")));
					}
					if (includeKey.equals("state")) {
						include.setState(Arrays.asList(includeValue.split("#")));
					}
					if (includeKey.equals("amountRange")) {
						AmountRange amountRange = new AmountRange();
						amountRange.setFromAmount(Double.parseDouble(includeValue.split("#")[0]));
						amountRange.setToAmount(Double.parseDouble(includeValue.split("#")[1]));
						include.setAmountRange(amountRange);
					}

				}
				rule.setInclude(include);
			}
			if (key.equals("exclude")) {
				Exclude exclude = new Exclude();
				String[] excludeProperties = value.split("\\s+");
				for (int j = 0; j < excludeProperties.length; j++) {
					String excludeProperty = excludeProperties[j];
					String excludeKey = excludeProperty.split("_")[0];
					String excludeValue = excludeProperty.split("_")[1];
					if (excludeKey.equals("merchantName")) {
						exclude.setMerchantName(Arrays.asList(excludeValue.split("#")));
					}
					if (excludeKey.equals("categoryId")) {
						String[] categoryIds = excludeValue.split("#");
						List<Integer> categoryIdsList = new ArrayList<Integer>();
						for (String catId : categoryIds) {
							categoryIdsList.add(Integer.parseInt(catId));
						}
						exclude.setCategoryId(categoryIdsList);
					}
					if (excludeKey.equals("accountId")) {
						String[] accountIds = excludeValue.split("#");
						List<Integer> accountIdsList = new ArrayList<Integer>();
						for (String accountId : accountIds) {
							accountIdsList.add(Integer.parseInt(commonEntity.getEntityIdsMap().get(accountId)));
						}
						exclude.setAccountId(accountIdsList);
					}
					if (excludeKey.equals("transactionId")) {
						String[] transactionIds = excludeValue.split("#");
						List<Integer> transactionIdsList = new ArrayList<Integer>();
						for (String txnId : transactionIds) {
							String accountName = txnId.split("@")[0].toString();
							int txnPos = Integer.parseInt((txnId.split("@")[1].substring(3)));
							String itemAccntTxnId = commonEntity.getItemAccountsToTxnsMap()
									.get(commonEntity.getEntityIdsMap().get(accountName).toString()).get(txnPos - 1)
									.toString();
							transactionIdsList.add(Integer.parseInt(itemAccntTxnId));
						}
						exclude.setTransactionId(transactionIdsList);
					}
					if (excludeKey.equals("merchantType")) {
						exclude.setMerchantType(Arrays.asList(excludeValue.split("#")));
					}
					if (excludeKey.equals("city")) {
						exclude.setCity(Arrays.asList(excludeValue.split("#")));
					}
					if (excludeKey.equals("state")) {
						exclude.setState(Arrays.asList(excludeValue.split("#")));
					}
					if (excludeKey.equals("amountRange")) {
						AmountRange amountRange = new AmountRange();
						amountRange.setFromAmount(Double.parseDouble(excludeValue.split("#")[0]));
						amountRange.setToAmount(Double.parseDouble(excludeValue.split("#")[1]));
						exclude.setAmountRange(amountRange);
					}
				}
				rule.setExclude(exclude);
			}
		}
		return rule;
	}

	public String processDate(String viewsDate) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss", Locale.US);
		LocalDateTime today = LocalDateTime.now();
		LocalDateTime processedDate = null;
		if (viewsDate.charAt(0) == '+') {
			processedDate = today.plusDays(Long.parseLong(viewsDate.substring(1)));
		}
		if (viewsDate.charAt(0) == '-') {
			processedDate = today.minusDays(Long.parseLong(viewsDate.substring(1)));
		}
		if (!viewsDate.isEmpty() && viewsDate.matches("\\d+")) {
			processedDate = today.plusDays(Long.parseLong(viewsDate.substring(0)));
		}

		return processedDate.withNano(0).toString();
	}

	public String processDateCreatedDate(String viewsDate) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss", Locale.US);
		LocalDateTime today = LocalDateTime.now();
		LocalDateTime processedDate = null;
		if (viewsDate.charAt(0) == '+') {
			processedDate = today.plusDays(Long.parseLong(viewsDate.substring(1)));
		}
		if (viewsDate.charAt(0) == '-') {
			processedDate = today.minusDays(Long.parseLong(viewsDate.substring(1)));
		}
		if (!viewsDate.isEmpty() && viewsDate.matches("\\d+")) {
			processedDate = today.plusDays(Long.parseLong(viewsDate.substring(0)));
		}

		return processedDate.toString();
	}
}