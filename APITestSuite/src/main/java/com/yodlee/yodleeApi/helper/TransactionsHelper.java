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

package com.yodlee.yodleeApi.helper;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.aop.ThrowsAdvice;
import org.testng.Assert;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.yodlee.yodleeApi.common.Configuration;
import com.yodlee.yodleeApi.interfaces.Session;
import com.yodlee.yodleeApi.pojo.CreateTransactionsRequest;
import com.yodlee.yodleeApi.pojo.EnvSession;
import com.yodlee.yodleeApi.pojo.HttpMethodParameters;
import com.yodlee.yodleeApi.utils.apiUtils.AccountUtils;
import com.yodlee.yodleeApi.utils.apiUtils.TransactionUtils;
import com.yodlee.yodleeApi.utils.commonUtils.CommonUtils;

import io.restassured.response.Response;

/**
 * Helper class for Transactions related API's
 * 
 * @author Rahul Kumar and Partha Sham
 *
 */
public class TransactionsHelper {

	JSONArray trxnCategoryRulesResponse;
	Long userDefinedRuleId;

	/**
	 * This method is used to get "ruleId"
	 * 
	 * @param testCaseId
	 * @param userSessionToken
	 * @return
	 * @throws JSONException
	 */
	public Long getCategorizationRuleId(int testCaseId, Session sessionObj) throws JSONException {

		TransactionUtils transactionUtils = new TransactionUtils();
		Response response = transactionUtils.getTranxCategorizationRule(sessionObj);
		trxnCategoryRulesResponse = (JSONArray) new JSONArray(response.asString());
		System.out.println("Transaction Categorization Rule Response:- " + trxnCategoryRulesResponse.toString());
		JSONObject userDefinedRule = trxnCategoryRulesResponse.getJSONObject(testCaseId);
		System.out.println(userDefinedRule.toString());
		userDefinedRuleId = userDefinedRule.getLong("userDefinedRuleId");
		System.out.println(userDefinedRuleId);
		return userDefinedRuleId;
	}

	/**
	 * @author Partha sham
	 * @param sessionObj
	 * @return
	 * @throws JSONException
	 */
	public Long getCategorizationRuleId(Session sessionObj) throws JSONException {
		TransactionUtils transactionUtils = new TransactionUtils();

		Response response = transactionUtils.getTranxCategorizationRule(sessionObj);
		JSONArray trxnCategoryRulesResponse = (JSONArray) new JSONArray(response.asString());
		System.out.println("Transaction Categorization Rule Size - " + trxnCategoryRulesResponse.length());
		System.out.println("Transaction Categorization Rule Response:- " + trxnCategoryRulesResponse.toString());

		JSONObject userDefinedRule = trxnCategoryRulesResponse.getJSONObject(trxnCategoryRulesResponse.length() - 1);
		System.out.println(userDefinedRule.toString());
		Long userDefinedRuleId = userDefinedRule.getLong("userDefinedRuleId");
		System.out.println(userDefinedRuleId);
		return userDefinedRuleId;
	}

	/**
	 * Used by API-PUT /transactions/{transactionId} for JSON creation
	 * 
	 * @param categorySource
	 * @param containerType
	 * @param categoryId
	 * @param desc
	 * @param memo
	 * @return
	 * @throws JSONException
	 */
	public String createInputforUpdateTransaction(String categorySource, String containerType, int categoryId,
			String consumer, String memo) throws JSONException {
		JSONObject transObj = new JSONObject();
		JSONObject transDetails = new JSONObject();
		transDetails.put("categorySource", categorySource);
		transDetails.put("container", containerType);
		transDetails.put("categoryId", categoryId);
		transDetails.put("memo", memo);
		JSONObject descObj = new JSONObject();
		descObj.put("consumer", consumer);
		transDetails.put("description", descObj);
		transObj.put("transaction", transDetails);
		String transUpdatejson = transObj.toString();
		System.out.println("Transaction update json " + transUpdatejson);
		return transUpdatejson;
	}

	/**
	 * This method is used by @API-PUT /transactions/categories for form body params
	 * 
	 * @param sourceKeyName
	 * @param sourceKeyValue
	 * @param categoryNameKey
	 * @param categoryNameValue
	 * @param idKey
	 * @param idValue
	 * @return
	 * @throws JSONException
	 * @throws InterruptedException
	 */
	public String updateCategoryObject(String sourceKeyName, String sourceKeyValue, String categoryNameKey,
			String categoryNameValue, String idKey, Integer idValue) throws JSONException, InterruptedException {
		
		LinkedHashMap<String, Object> jsonOrder = new LinkedHashMap<String, Object>();
		jsonOrder.put(idKey, idValue);
		jsonOrder.put(sourceKeyName, sourceKeyValue);
		jsonOrder.put(categoryNameKey, categoryNameValue);
		Gson gson = new Gson();
		String json = gson.toJson(jsonOrder);
		System.out.println(json);
		return json;
	}

	/**
	 * Used by @API: GET /transactions for construction of Query params.
	 * 
	 * @param fromDate
	 * @param toDate
	 * @param container
	 * @param baseType
	 * @param keyword
	 * @param accountId
	 * @param seriesId
	 * @param sourceType
	 * @param type
	 * @param categoryId
	 * @param categoryType
	 * @param skip
	 * @param top
	 * @return
	 * @throws Exception
	 */
	public LinkedHashMap<String, String> createInputForGetAllTransactions(String fromDate, String toDate,
			String container, String baseType, String keyword, String accountId, String seriesId, String sourceType,
			String type, String categoryId, String categoryType, String skip, String top) throws Exception {

		CommonUtils commonUtils = new CommonUtils();
		LinkedHashMap<String, String> urlParams = new LinkedHashMap<String, String>();

		urlParams.put("fromDate", commonUtils.getOffsetDate(fromDate));
		urlParams.put("toDate", commonUtils.getOffsetDate(toDate));
		urlParams.put("container", container);
		urlParams.put("baseType", baseType);
		urlParams.put("keyword", keyword);
		if (accountId != null && accountId.length() > 0)
			urlParams.put("accountId", String.valueOf(accountId));
		if (seriesId != null && seriesId.length() > 0)
			urlParams.put("recurringEventId", getRecurringEventId(seriesId));
		urlParams.put("sourceType", sourceType);
		urlParams.put("categoryId", categoryId);
		urlParams.put("type", type);
		urlParams.put("categoryType", categoryType);
		urlParams.put("skip", skip);
		urlParams.put("top", top);

		return urlParams;
	}

	/**
	 * @param seriesId
	 *            contains values like VALID,INAVLID,OTHER_USER etc.
	 * @return
	 */
	public String getRecurringEventId(String seriesId) {

		String manualId = null;
		if (seriesId == null) {
			return null;
		} else if (seriesId.equalsIgnoreCase("VALID")) {
			return manualId;
		} else if (seriesId.equalsIgnoreCase("INVALID")) {
			return "10000";
		} else if (seriesId.equalsIgnoreCase("OTHER_USER")) {
			 return "100000";
		}
		return null;
	}

	/**
	 * Returns the recurring event id from API response if it is not passed in the
	 * CSV
	 *
	 * @param getRecurringResponse
	 * @param recurringEventId
	 * @return
	 * @throws Exception
	 */
	public String getRecurringEventId(Response getRecurringResponse, String recurringEventId) throws Exception {

		if (recurringEventId.equalsIgnoreCase("VALID")) {
			JSONObject response = new JSONObject(getRecurringResponse.asString());
			JSONArray account = response.getJSONArray("account");
			Integer id;
			if (account.getJSONObject(0).has("recurringEvents")) {
				id = getRecurringResponse.jsonPath().get("account[0].recurringEvents[0].id");
			} else {
				id = getRecurringResponse.jsonPath().get("account[1].recurringEvents[0].id");
			}
			return id.toString();
		} else if (recurringEventId.equalsIgnoreCase("OTHER_USER")) {
			JSONObject response = new JSONObject(getRecurringResponse.asString());
			JSONArray account = response.getJSONArray("account");
			Integer id;
			if (account.getJSONObject(0).has("recurringEvents")) {
				id = getRecurringResponse.jsonPath().get("account[0].recurringEvents[0].id");
			} else {
				id = getRecurringResponse.jsonPath().get("account[1].recurringEvents[0].id");
			}
			id = id - 100;
			return id.toString();
		} else if (recurringEventId.equalsIgnoreCase("INVALID")) {
			Integer id = 123466;
			return id.toString();
		} else if (recurringEventId.equalsIgnoreCase("NULL")) {
			return null;
		} else {
			return recurringEventId;
		}
	}

	public CreateRecurringEventRequest createRecurringTxnRequest(Double amount, String currency, String consumer,
			String baseType, int categoryId, String categorySource, String startDate, String memo, String accountId,
			String checkNumber, String frequency, String endDate, String transactionType, Session session)
			throws Exception {

		AccountsHelper accountsHelper = new AccountsHelper();
		TransactionsHelper transactionsHelper = new TransactionsHelper();
		CommonUtils commonUtils = new CommonUtils();
		System.out.println("Account id from ::" + accountId);
		Long account = null;
		if (!accountId.equalsIgnoreCase("NULL")) 
			account = accountsHelper.getAccountId(accountId, session);
		
		CreateRecurringEventRequest crr = new CreateRecurringEventRequest();
		crr.setAccountId(account);
		crr.setAmount(amount);
		crr.setBaseType(baseType);
		if (categorySource.equalsIgnoreCase("USER")) {
			crr.setCategoryId(transactionsHelper.createAndGetUserCategory(session, "Custom ", "29"));
		} else {
			crr.setCategoryId(categoryId);
		}
		crr.setCategorySource(categorySource);
		crr.setConsumer(consumer);
		crr.setCurrency(currency);
		crr.setStartDate(commonUtils.getOffsetDate(startDate));
		crr.setEndDate(commonUtils.getOffsetDate(endDate));
		crr.setFrequency(frequency);
		crr.setMemo(memo);
		crr.setTransactionType(transactionType);
		return crr;
	}

	/**
	 * Creates a user defined category and returns the categoryId of the new
	 * category
	 * 
	 * @param categoryName
	 * @param parentCategoryId
	 * @return Creates a user defined category and return the category id
	 * @throws Exception
	 */

	public Integer createAndGetUserCategory(Session session, String categoryName, String parentCategoryId)
			throws Exception {

		TransactionUtils transactionUtils = new TransactionUtils();
		Configuration configurationObj = Configuration.getInstance();

		JSONObject categoryParam = new JSONObject();
		categoryParam.put("categoryName", categoryName);
		categoryParam.put("parentCategoryId", Integer.parseInt(parentCategoryId));
		LinkedHashMap<String, String> params = new LinkedHashMap<String, String>();
		params.put("categoryParam", categoryParam.toString());

		HttpMethodParameters httpMethodParameters = HttpMethodParameters.builder().build();
		httpMethodParameters.setQueryParams(params);

		transactionUtils.createTransactionCategory(httpMethodParameters, configurationObj.getCobrandSessionObj());

		Response response = transactionUtils.getTxnCategories(configurationObj.getCobrandSessionObj());
		JSONObject responseObj = new JSONObject(response.asString());
		Integer categoryId = 0;
		JSONArray categories = responseObj.getJSONArray("transactionCategory");
		for (int i = 0; i < categories.length(); i++) {
			JSONObject category = categories.getJSONObject(i);
			if (category.getString("source").equalsIgnoreCase("user")) {
				categoryId = category.getInt("id");
				return categoryId;
			}
		}
		return categoryId;
	}

	/**
	 * This method is used to get transactionId based on accountId and container
	 * passed.
	 * 
	 * @param session
	 *            it is session object
	 * @param accountId
	 *            it is account id
	 * @param container
	 *            it is container value
	 * @return
	 * @throws Exception
	 */
	 public Long getTransactionId(Session session, String accountId, String container) throws Exception {

		if (container.equalsIgnoreCase("INVALID")) {
			return 12344323L;
		}
		// Response response = getAggregatedTransactions(session, accountId, container);

		TransactionUtils transactionUtils = new TransactionUtils();
		TransactionsHelper transactionsHelper = new TransactionsHelper();
		Configuration configurationObj = Configuration.getInstance();

		LinkedHashMap<String, String> queryParams = transactionsHelper.createInputForGetAllTransactions(null, null,
				container, null, null, accountId, null, "AGGREGATED", null, null, null, null, null);

		HttpMethodParameters httpMethodParameters = HttpMethodParameters.builder().build();
		httpMethodParameters.setQueryParams(queryParams);
		Response response = transactionUtils.getAllTransactions(httpMethodParameters, session);
		System.out.println("response from get all transactions ::" + response.asString());
		if (container.equalsIgnoreCase("OTHER_USER")) {
			int i = (int) response.jsonPath().get("transaction[0].id") - 150;
			Long l = new Long(i);
			return l;
		}

		int id = (int) response.jsonPath().get("transaction[0].id");
		Long transactionId = new Long(id);
		return transactionId;
	}

	public CreateRecurringEventRequest createREWithRefId(String accountId, String refTransactionId, String frequency,
			String startDate, String endDate, Session session) throws Exception {
		TransactionsHelper transactionsHelper = new TransactionsHelper();
		AccountsHelper accountsHelper = new AccountsHelper();
		CommonUtils commonUtils = new CommonUtils();

		Long accountIds = accountsHelper.getAccountId(accountId, session);
		System.out.println("accountIds :::" + accountIds);
		Long transactionId = transactionsHelper.getTransactionId(session, accountIds.toString(), refTransactionId);
		CreateRecurringEventRequest crr = new CreateRecurringEventRequest();
		crr.setAccountId(accountIds);
		crr.setStartDate(commonUtils.getOffsetDate(startDate));
		crr.setEndDate(commonUtils.getOffsetDate(endDate));
		crr.setFrequency(frequency);
		crr.setRefTransactionId(transactionId);
		return crr;

	}
	
	public UpdateRecurringEventRequest updateRecurringTxnRequest(Double amount, String currency, String consumer,
			String baseType, int categoryId, String categorySource, String startDate, String memo, String accountId,
			String checkNumber, String frequency, String endDate, String transactionType, String recurringEventStatus,
			String userApprovalStatus, Session session) throws Exception {
		CommonUtils commonUtils = new CommonUtils();
		TransactionsHelper transactionsHelper = new TransactionsHelper();
		AccountsHelper accountsHelper = new AccountsHelper();

		Long account = null;
		if (!accountId.equalsIgnoreCase("NULL"))
			account = accountsHelper.getAccountId(accountId, session);
		UpdateRecurringEventRequest urr = new UpdateRecurringEventRequest();
		urr.setAccountId(account);
		urr.setAmount(amount);
		urr.setBaseType(baseType);
		if (categorySource.equalsIgnoreCase("USER")) {
			urr.setCategoryId(transactionsHelper.createAndGetUserCategory(session, "Custom ", "29"));
		} else {
			urr.setCategoryId(categoryId);
		}
		urr.setCategorySource(categorySource);
		urr.setConsumer(consumer);
		urr.setCurrency(currency);
		urr.setStartDate(commonUtils.getOffsetDate(startDate));
		urr.setEndDate(commonUtils.getOffsetDate(endDate));
		urr.setFrequency(frequency);
		urr.setMemo(memo);
		urr.setTransactionType(transactionType);
		urr.setRecurringEventStatus(recurringEventStatus);
		urr.setUserApprovalStatus(userApprovalStatus);
		return urr;
	}

	/**
	 * Create a sub category of a system category
	 * 
	 * @param categoryName
	 *            Category name of the new category to be created
	 * @param parentCategoryId
	 *            CategoryId of a system category
	 * @return Returns the create response
	 * @throws NumberFormatException
	 * @throws JSONException
	 */
	public Response createCategory(Session obj, String categoryName, String parentCategoryId)
			throws NumberFormatException, JSONException {
		TransactionUtils transactionUtils = new TransactionUtils();
		Configuration configurationObj = Configuration.getInstance();
		JSONObject categoryParam = new JSONObject();
		categoryParam.put("categoryName", categoryName);
		categoryParam.put("parentCategoryId", Integer.parseInt(parentCategoryId));
		LinkedHashMap<String, String> params = new LinkedHashMap<String, String>();
		params.put("categoryParam", categoryParam.toString());

		HttpMethodParameters httpMethodParameters = HttpMethodParameters.builder().build();
		httpMethodParameters.setQueryParams(params);

		/*EnvSession session = new EnvSession(configurationObj.getCobrandSessionObj().getCobSession(),
				configurationObj.getCobrandSessionObj().getPath());*/
		
		EnvSession session = EnvSession.builder().cobSession(configurationObj.getCobrandSessionObj().getCobSession()).path(configurationObj.getCobrandSessionObj().getPath()).build();

		Response response = transactionUtils.createTransactionCategory(httpMethodParameters, session);

		return response;
	}

	public Response getCategory(Session session) {

		Configuration configurationObj = Configuration.getInstance();

		TransactionUtils transactionUtils = new TransactionUtils();
		Response response = transactionUtils.getTxnCategories(configurationObj.getCobrandSessionObj());
		return response;
	}

	/**
	 * 
	 * 
	 * PUT /transaction/:id API
	 * 
	 * @param transactionId
	 *            Id of the transaction to be updated
	 * 
	 */
	public void markAsPaid(Response response, Session commonEnvSession) throws JSONException {

		JSONObject responseObj = new JSONObject(response.asString());
		JSONObject transaction = responseObj.getJSONArray("transaction").getJSONObject(0);
		transaction.remove("reconStatus");
		transaction.put("reconStatus", "USER_RECONCILED_AGAINST_CASH");
		Integer transactionId1 = transaction.getInt("id");
		HttpMethodParameters httpParams = HttpMethodParameters.builder().build();
		httpParams.setBodyParams(transaction.toString());

		Map<String, Object> updateTransactionPathParam = new HashMap<String, Object>();
		updateTransactionPathParam.put("transactionId", transactionId1);
		httpParams.setPathParams(updateTransactionPathParam);
		TransactionUtils transactionUtils = new TransactionUtils();
		transactionUtils.updateTransaction(httpParams, Configuration.getInstance().getCobrandSessionObj());

	}

	public int getDiff(Date d) throws java.text.ParseException {
		// Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date date = sdf.parse(sdf.format(new Date()));
		System.out.println(d.toString() + "....." + date.toString());
		float diffInDays = (float) ((d.getTime() - date.getTime()) / (1000 * 60 * 60 * 24));
		System.out.println(diffInDays);
		int i = (int) diffInDays;
		return i;

	}

	/**
	 * writing content of a response to a file
	 * 
	 * 
	 * @param response
	 * @param resFile
	 * @param resFilePath
	 * @throws Exception
	 */
	public void writeGetBalances(Response response, String resFile, String resFilePath) throws Exception {

		if (response.asString().contains("ErrorMessage")) {
			return;
		}
		JSONObject responseObject = new JSONObject(response.asString());
		if (response.asString().contains("referenceCode")) {
			responseObject.remove("referenceCode");
			responseObject.put("referenceCode", "${json-unit.ignore}");
		} else {
			JSONArray accounts = responseObject.getJSONArray("account");
			int size = accounts.length();

			for (int i = 0; i < size; i++) {
				JSONObject account = accounts.getJSONObject(i);
				account.remove("id");
				account.put("id", "${json-unit.ignore}");
				JSONArray balances = account.getJSONArray("balances");
				int balanceSize = balances.length();
				for (int j = 0; j < balanceSize; j++) {
					JSONObject json = balances.getJSONObject(j);
					if (json.has("date")) {
						DateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
						String str = json.getString("date");
						Date d = format1.parse(str);
						json.remove("date");
						json.put("date", getDiff(d));
					}
				}
			}
			System.out.println(responseObject.toString());
			writeFile(resFile, resFilePath, toPrettyFormat(responseObject.toString()));
		}
		System.out.println("Done");

	}

	public String toPrettyFormat(String jsonString) {
		JsonParser parser = new JsonParser();
		JsonObject json = parser.parse(jsonString).getAsJsonObject();
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		String prettyJson = gson.toJson(json);
		return prettyJson;
	}

	public static void writeFile(String fileName, String filePath, String content) throws IOException {
		File file;
		file = new File(filePath + "/" + fileName);
		if (!file.exists()) {
			// Create the file if it does not exist
			file.createNewFile();
		} else {
			// Do not do anything if the file exists
			return;
		}
		FileWriter fw = new FileWriter(file.getAbsoluteFile());
		BufferedWriter bw = new BufferedWriter(fw);
		bw.write(content);
		bw.close();
	}

	/**
	 * Creates a json using the fields of the request object and returns as a string
	 * 
	 * @return Returns the json string for the POST /transactions request
	 * @throws JSONException
	 */
	public String getJsonString() throws JSONException {
		JSONObject manualTxnObj = new JSONObject();
		JSONObject manualTransactionsObject = new JSONObject();
		JSONObject amountObj = new JSONObject();
		Double amount = null;
		String currency = null;
		String consumer = null;
		String baseType = null;
		long categoryId = 0;
		Long accountId = null;
		String date = null;
		String categorySource = null;
		String memo = null;
		Long refTransactionId = null;
		String checkNumber = null;
		String transactionType = null;

		amountObj.put("amount", amount);
		amountObj.put("currency", currency);
		JSONObject descriptionObj = new JSONObject();
		descriptionObj.put("consumer", consumer);
		manualTxnObj.put("baseType", baseType);
		if (categoryId > 0) {
			manualTxnObj.put("categoryId", categoryId);
		}
		manualTxnObj.put("categorySource", categorySource);
		manualTxnObj.put("date", date);
		manualTxnObj.put("memo", memo);
		manualTxnObj.put("accountId", accountId);
		manualTxnObj.put("refTransactionId", refTransactionId);
		manualTxnObj.put("checkNumber", checkNumber);
		manualTxnObj.put("description", descriptionObj);
		manualTxnObj.put("amount", amountObj);
		manualTxnObj.put("transactionType", transactionType);
		manualTransactionsObject.put("transaction", manualTxnObj);
		String manualTxnObjectJSON = manualTransactionsObject.toString();
		System.out.println("manualTxnObjectJSON is : " + manualTxnObjectJSON);
		return manualTxnObjectJSON;
	}

	/**
	 * This method creates a httpParams by creating json for sub-category
	 * 
	 * @param categoryName
	 * @param parentCategoryId
	 * @return
	 * @throws NumberFormatException
	 * @throws JSONException
	 */
	public HttpMethodParameters createCategoryParams(String categoryName, String parentCategoryId)
			throws NumberFormatException, JSONException {

		JSONObject categoryParam = new JSONObject();
		categoryParam.put("categoryName", categoryName);
		categoryParam.put("parentCategoryId", Integer.parseInt(parentCategoryId));

		HttpMethodParameters httpMethodParameters = HttpMethodParameters.builder().build();
		httpMethodParameters.setBodyParams(categoryParam.toString());

		return httpMethodParameters;
	}

	public CreateTransactionsRequest createManualTxnObject(Double amount, String currency, String consumer,
			String baseType, int categoryId, String categorySource, String date, String memo, String accountId,
			String checkNumber, String transactionType, Session commonEnvSession) throws Exception {

		AccountsHelper accountsHelper = new AccountsHelper();
		Long account = accountsHelper.getAccountId(accountId, commonEnvSession);
		CreateTransactionsRequest crateTransactionRequest = CreateTransactionsRequest.builder().build();
		CommonUtils commonUtils = new CommonUtils();
		TransactionsHelper transactionsHelper = new TransactionsHelper();

		if (account != null)
			crateTransactionRequest.setAccountId(account);
		crateTransactionRequest.setAmount(amount);
		crateTransactionRequest.setBaseType(baseType);
		if (categorySource.equalsIgnoreCase("USER")) {
			crateTransactionRequest
					.setCategoryId(transactionsHelper.createAndGetUserCategory(commonEnvSession, "Custom ", "29"));
		} else {
			crateTransactionRequest.setCategoryId(categoryId);
		}
		crateTransactionRequest.setCategorySource(categorySource);
		crateTransactionRequest.setConsumer(consumer);
		crateTransactionRequest.setCurrency(currency);
		crateTransactionRequest.setDate(commonUtils.getOffsetDate(date));
		crateTransactionRequest.setMemo(memo);
		crateTransactionRequest.setTransactionType(transactionType);
		return crateTransactionRequest;
	}

	/**
	 * @author partha sham
	 * @param categoryId
	 * @param priority
	 * @param descriptionOperation
	 * @param descriptionValue
	 * @param amountOperation
	 * @param amountValue
	 */
	public String createRuleObject(int categoryId, int priority, String descriptionOperation,
			String descriptionValue, String amountOperation, String amountValue, String source) throws JSONException {

		JSONObject ruleObj = new JSONObject();
		JSONObject ruleClause1 = new JSONObject();
		ruleClause1.put("field", "description");
		if (descriptionOperation != null && descriptionOperation.length() > 0) {
			ruleClause1.put("operation", descriptionOperation);
		}
		if (descriptionValue != null && descriptionValue.length() > 0) {
			ruleClause1.put("value", descriptionValue);
		}
		JSONObject ruleClause2 = new JSONObject();
		ruleClause2.put("field", "amount");
		if (amountOperation != null && amountOperation.length() > 0) {
			ruleClause2.put("operation", amountOperation);
		}
		if (amountValue != null && amountValue.length() > 0) {
			ruleClause2.put("value", amountValue);
		}
		JSONArray RowArray1 = new JSONArray(); // we created an array ruleClause which containe's ruleClause1 and
												// ruleClause2 json object.
		RowArray1.put(ruleClause1);
		RowArray1.put(ruleClause2);
		JSONObject row1 = new JSONObject();
		if (categoryId>0) {
			row1.put("categoryId", categoryId);
		}
		if (priority > 0) {
			row1.put("priority", priority);
		}
		if (source != null && source.length() > 0) {
			row1.put("source", source);
		}
		
		ruleObj.put("rule", row1);
		row1.put("ruleClause", RowArray1); // We added the above array to the ruleClause.
		String json = ruleObj.toString(); // We are converting json object to string
		System.out.println("JSON is : " + json);
		return json;
	}

	/**
	 * @author partha sham Calls GET /transactions to fetch only system predicted
	 *         transactions
	 * @param container
	 *            Container of the transaction required
	 * @return Returns the transaction id of the system transactions
	 */
	public Integer getSystemTransactionId(EnvSession envSession, String container) {
		TransactionUtils transactionUtils = new TransactionUtils();
		Configuration configuration = Configuration.getInstance();
		HashMap<String, String> urlParams = new HashMap<String, String>();
		urlParams.put("accountId", "");
		urlParams.put("container", container);
		urlParams.put("sourceType", "SYSTEM_PREDICTED");
		HttpMethodParameters queryParams = HttpMethodParameters.builder().build();
		queryParams.setQueryParams(urlParams);

		if (container.equalsIgnoreCase("INVALID")) {
			return 123;
		}
		if (container.equalsIgnoreCase("NULL")) {
			return null;
		}
		if (container.equalsIgnoreCase("OTHER_USER")) {
			try {
				Response response = transactionUtils.getAllTransactions(queryParams, envSession);
				int id = (int) response.jsonPath().get("transactions[0].id") - 100;
				return id;
			} catch (Exception e) {
				System.out.println(e.getStackTrace());
				Assert.fail("Not able to get the system transaction id" + e.getMessage());
			}
		} else {
			try {
				Response response = transactionUtils.getAllTransactions(queryParams, envSession);
				System.out.println("response::::::::"+response.asString());
				JSONObject responseObj = new JSONObject(response.asString());
				JSONArray transactions = responseObj.getJSONArray("transaction");
				for (int i = 0; i < transactions.length(); i++) {
					if (transactions.getJSONObject(i).getString("sourceType").equals("SYSTEM_PREDICTED")) {
						return transactions.getJSONObject(i).getInt("id");
					}
				}
				return response.jsonPath().get("transaction[0].id");
			} catch (Exception e) {
				e.printStackTrace();
				Assert.fail("Not able to get the system transaction id" + e.getMessage());
			}
		}
		return null;

	}

	/**
	 * This method creates a httpParams by creating path param
	 * 
	 * @author partha sham
	 * @param key
	 * @param value
	 * @return
	 */
	public HttpMethodParameters createPathParam(String key, String value) {

		LinkedHashMap<String, Object> pathParam = new LinkedHashMap<>();
		pathParam.put(key, value);
		HttpMethodParameters httpMethodParameters = HttpMethodParameters.builder().build();
		httpMethodParameters.setPathParams(pathParam);

		return httpMethodParameters;
	}

	/**
	 * @author partha sham
	 * @param categoryNameValue
	 * @param parentCategoryIdValue
	 * @return
	 * @throws JSONException
	 * @throws InterruptedException
	 */
	public String createCategoryObj(String categoryNameKey, String categoryNameValue, String parentCategoryIdKey, int parentCategoryIdValue)
			throws JSONException, InterruptedException {
		LinkedHashMap<String, Object> jsonOrder = new LinkedHashMap<String, Object>();
		jsonOrder.put(categoryNameKey, categoryNameValue);
		jsonOrder.put(parentCategoryIdKey, parentCategoryIdValue);
		Gson gson = new Gson();
		String json = gson.toJson(jsonOrder);
		return json;
	}

	/**
	 * @author partha sham This method helps to fetch user category id from response
	 * @param response
	 * @return userCategoryID
	 */
	public int getUserCategoryID(Response response) {

		String strResponse = response.asString();
		JSONObject jsonObj = new JSONObject(strResponse);
		JSONArray jsonArray = jsonObj.getJSONArray("transactionCategory");
		int userCategoryID = 0;
		for (int i = 0; i < jsonArray.length(); i++) {
			JSONObject json = jsonArray.getJSONObject(i);
			Iterator<String> keys = json.keys();

			while (keys.hasNext()) {
				String key = keys.next();
				if (key.equalsIgnoreCase("source") && String.valueOf(json.get(key)).equalsIgnoreCase("USER")) {
					userCategoryID = json.getInt("id");
				}
			}
		}
		return userCategoryID;
	}

	/**
	 * @author partha sham
	 * @param containerKey
	 * @param container
	 * @param baseTypeKey
	 * @param baseType
	 * @param categoryTypeKey
	 * @param categoryType
	 * @param keywordKey
	 * @param keyword
	 * @param categoryKey
	 * @param category
	 * @param categoryIdKey
	 * @param categoryIds
	 * @param fromDateKey
	 * @param fromDate
	 * @param toDateKey
	 * @param toDate
	 * @param highLevelCategoryIdKey
	 * @param highLevelCategoryId
	 * @param typeKey
	 * @param type
	 * @param accountReconTypeKey
	 * @param accountReconType
	 * @param skipKey
	 * @param skip
	 * @param topKey
	 * @param top
	 * @param accountIdKey
	 * @param accountIds
	 * @return
	 */
	public LinkedHashMap<String, String> createMapForTxns(String containerKey, String container, String baseTypeKey,
			String baseType, String categoryTypeKey, String categoryType, String keywordKey, String keyword,
			String categoryKey, String category, String categoryIdKey, String categoryIds, String fromDateKey,
			String fromDate, String toDateKey, String toDate, String highLevelCategoryIdKey, String highLevelCategoryId,
			String typeKey, String type, String accountReconTypeKey, String accountReconType, String skipKey,
			String skip, String topKey, String top, String accountIdKey, Set<Long> accountIds) {

		LinkedHashMap<String, String> params = new LinkedHashMap<String, String>();
		if (containerKey != null && containerKey != "")
			params.put(containerKey, container);
		if (baseTypeKey != null && baseTypeKey != "")
			params.put(baseTypeKey, baseType);
		if (keywordKey != null && keywordKey != "")
			params.put(keywordKey, keyword);
		if (fromDateKey != null && fromDateKey != "")
			params.put(fromDateKey, fromDate);
		if (toDateKey != null && toDateKey != "")
			params.put(toDateKey, toDate);
		if (accountReconTypeKey != null && accountReconTypeKey != "")
			params.put(accountReconTypeKey, accountReconType);
		if (categoryIdKey != null && categoryIdKey != "")
			params.put(categoryIdKey, categoryIds);
		if (highLevelCategoryIdKey != null && highLevelCategoryIdKey != "")
			params.put(highLevelCategoryIdKey, highLevelCategoryId);
		if (typeKey != null && typeKey != "")
			params.put(typeKey, type);
		if (categoryTypeKey != null && categoryTypeKey != "")
			params.put(categoryTypeKey, categoryType);
		if (categoryKey != null && categoryKey != "")
			params.put(categoryKey, category);
		if (accountIdKey != null && accountIdKey != "")
			params.put(accountIdKey, accountIdsAsString(accountIds));
		if (skipKey != null && skipKey != "")
			params.put(skipKey, skip);
		if (topKey != null && topKey != "")
			params.put(topKey, top);

		return params;
	}

	/**
	 * @author partha sham
	 * @param accountIds
	 * @return
	 */
	public static String accountIdsAsString(Set<Long> accountIds) {
		String accIds = "";
		try {
			Iterator<Long> it = accountIds.iterator();
			while (it.hasNext()) {
				accIds = accIds + it.next() + ",";
			}
			System.out.println("accIds is " + accIds);
		} catch (Exception e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
		if (!accIds.equals(""))
			accIds = accIds.substring(0, accIds.length() - 1);
		return accIds;
	}

	/**
	 * @author partha sham
	 * @param amount
	 * @param currency
	 * @param consumer
	 * @param baseType
	 * @param categoryId
	 * @param categorySource
	 * @param date
	 * @param memo
	 * @param accountId
	 * @param checkNumber
	 * @param transactionType
	 * @return
	 */
	public HashMap<String, Object> createQueryParamManualTxn(Double amount, String currency, String consumer,
			String baseType, int categoryId, String categorySource, String date, String memo, String accountId,
			String checkNumber, String transactionType) {

		CommonUtils commonUtils = new CommonUtils();
		HashMap<String, Object> mapQuery = new HashMap<>();
		mapQuery.put("amount", amount);
		mapQuery.put("currency", currency);
		mapQuery.put("consumer", consumer);
		mapQuery.put("baseType", baseType);
		mapQuery.put("categoryId", categoryId);
		mapQuery.put("categorySource", categorySource);
		mapQuery.put("date", commonUtils.getOffsetDate(date));
		mapQuery.put("memo", memo);
		mapQuery.put("accountId", accountId);
		mapQuery.put("checkNumber", checkNumber);
		mapQuery.put("transactionType", transactionType);
		return mapQuery;
	}

	public CreateRecurringEventRequest createRecurringTxnRequest(Double amount, String currency, String consumer,
			String baseType, int categoryId, String categorySource, String startDate, String memo, String accountId,
			String checkNumber, String frequency, String endDate, String transactionType, EnvSession envSession)
			throws Exception {
		CommonUtils commonUtils = new CommonUtils();
		AccountsHelper accountsHelper = new AccountsHelper();
		TransactionsHelper transactionsHelper = new TransactionsHelper();

		Long account = null;
		if (!accountId.equalsIgnoreCase("NULL")) 
			account = accountsHelper.getAccountId(accountId, envSession);
		
		CreateRecurringEventRequest crr = new CreateRecurringEventRequest();
		crr.setAccountId(account);
		crr.setAmount(amount);
		crr.setBaseType(baseType);
		if (categorySource.equalsIgnoreCase("USER")) {
			crr.setCategoryId(transactionsHelper.createAndGetUserCategory(envSession, "Custom ", "29"));
		} else {
			crr.setCategoryId(categoryId);
		}
		crr.setCategorySource(categorySource);
		crr.setConsumer(consumer);
		crr.setCurrency(currency);
		crr.setStartDate(commonUtils.getOffsetDate(startDate));
		crr.setEndDate(commonUtils.getOffsetDate(endDate));
		crr.setFrequency(frequency);
		crr.setMemo(memo);
		crr.setTransactionType(transactionType);
		return crr;
	}
	
	public CreateTransactionsRequest createTRWithRefId(String accountId, String referenceId, String date, EnvSession envSession) throws Exception {
		CommonUtils commonUtils = new CommonUtils();
		AccountsHelper accountsHelper = new AccountsHelper();
		TransactionsHelper transactionsHelper = new TransactionsHelper();
		
        Long account = accountsHelper.getAccountId(accountId, envSession);
        Long transactionId = transactionsHelper.getTransactionId(envSession,account.toString(), referenceId);
        CreateTransactionsRequest ctr = CreateTransactionsRequest.builder().build();
        ctr.setAccountId(account);
        ctr.setDate(commonUtils.getOffsetDate(date));
        ctr.setRefTransactionId(transactionId);
        return ctr;
    }
	
	public void validateUpdatedTransaction(Response response, String strFilePath, String strResFile) throws Exception {
		
		CommonUtils commonUtils = new CommonUtils();
		String expectedJsonString;
		try {
			expectedJsonString = commonUtils.readJsonFile(commonUtils.getPath(strFilePath + File.separator + strResFile));
			JSONObject expectedJson = new JSONObject(expectedJsonString);
						
			Assert.assertEquals(response.jsonPath().get("transaction.categoryType").toString().replaceAll("\\[|\\]", "").trim(), expectedJson.getJSONArray("transaction").getJSONObject(0).get("categoryType"));
			Assert.assertEquals(response.jsonPath().get("transaction.CONTAINER").toString().replaceAll("\\[|\\]", "").trim(), expectedJson.getJSONArray("transaction").getJSONObject(0).get("CONTAINER"));
			Assert.assertEquals(response.jsonPath().get("transaction.baseType").toString().replaceAll("\\[|\\]", "").trim(), expectedJson.getJSONArray("transaction").getJSONObject(0).get("baseType"));
			Assert.assertEquals(response.jsonPath().get("transaction.categorySource").toString().replaceAll("\\[|\\]", "").trim(), expectedJson.getJSONArray("transaction").getJSONObject(0).get("categorySource"));
		} catch (FileNotFoundException e) {
			throw new Exception(strResFile = "File not found in "+strFilePath);
		}
	}
	
	public String createFileNamePrefix(boolean isTDEnable, boolean isGeoLocationEnabled, boolean isSimpleDescEnabled, boolean isMergedCatEnabled) {
		 String filePrefix="";
		 boolean bool[]= {isTDEnable, isGeoLocationEnabled, isSimpleDescEnabled, isMergedCatEnabled};
	     String prefix[]= {"TD","GL","SD","MC"};
	     for(int i=0;i<4;i++) {
	    	 if(i==0 && !bool[i]) {
	    		 i+=2;
	    		 continue;
	    	 }
	    	 if(bool[i]) {
	    		 filePrefix+=prefix[i]+"-";
	    	 }
	     }
	     return filePrefix;
	 }

	/*public CreateTransactionsRequest createTRWithRefId(String accountId, String refTransactionId, String frequency,
			String startDate, String endDate, Session session) throws Exception {
		TransactionsHelper transactionsHelper = new TransactionsHelper();
		AccountsHelper accountsHelper = new AccountsHelper();
		CommonUtils commonUtils = new CommonUtils();

		Long accountIds = accountsHelper.getAccountId(accountId, session);
		System.out.println("accountIds :::" + accountIds);
		Long transactionId = transactionsHelper.getTransactionId(session, accountIds.toString(), refTransactionId);
        CreateTransactionsRequest ctr = new CreateTransactionsRequest();
		ctr.setAccountId(accountIds);
		ctr.setDate(commonUtils.getOffsetDate(startDate));
		ctr.setDate(commonUtils.getOffsetDate(endDate));
		ctr.setFrequency(frequency);
		ctr.setTransactionId(transactionId);
		return ctr;

	}
*/
}
