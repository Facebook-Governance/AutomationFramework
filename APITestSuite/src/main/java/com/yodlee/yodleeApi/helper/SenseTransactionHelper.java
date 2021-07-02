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

/**
 * Helper class for sense suite
 * 
 * @author Mahadev A N
 *
 */
import io.restassured.response.Response;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;
import org.testng.Assert;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonPrimitive;
import com.yodlee.DBHelper;
import com.yodlee.yodleeApi.interfaces.Session;
import com.yodlee.yodleeApi.pojo.EnvSession;
import com.yodlee.yodleeApi.pojo.HttpMethodParameters;
import com.yodlee.yodleeApi.utils.apiUtils.TransactionUtils;
import com.yodlee.yodleeApi.utils.commonUtils.CommonUtils;

public class SenseTransactionHelper {
	
	AccountsHelper accountsHelper = new AccountsHelper();
	/**
	 * 
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
	public String createManualTxnObject(Double amount, String currency,
			String consumer, String baseType, int categoryId, String categorySource, String date, String memo,
			String accountId, String checkNumber, String transactionType) {
		JSONObject manualTxnObj = new JSONObject();
		JSONObject manualTransactionsObject = new JSONObject();
		JSONObject amountObj = new JSONObject();
		amountObj.put("amount", amount);
		amountObj.put("currency", currency);
		JSONObject descriptionObj = new JSONObject();
		descriptionObj.put("consumer", consumer);
		manualTxnObj.put("baseType", baseType);
		if (categoryId > 0) {
			manualTxnObj.put("categoryId", categoryId);
		}
		manualTxnObj.put("categorySource", categorySource);
		manualTxnObj.put("date", getOffsetDate(date));
		manualTxnObj.put("memo", memo);
		manualTxnObj.put("accountId", accountId);
		manualTxnObj.put("checkNumber", checkNumber);
		manualTxnObj.put("description", descriptionObj);
		manualTxnObj.put("amount", amountObj);
		manualTxnObj.put("transactionType", transactionType);
		manualTransactionsObject.put("manualTransaction", manualTxnObj);
		String manualTxnObjectJSON = manualTransactionsObject.toString();
		System.out.println("manualTxnObjectJSON is : " + manualTxnObjectJSON);
		return manualTxnObjectJSON;
	}
/**
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

		LinkedHashMap<String, String> urlParams = new LinkedHashMap<String, String>();

		urlParams.put("fromDate", getOffsetDate(fromDate));
		urlParams.put("toDate", getOffsetDate(toDate));
		urlParams.put("container", container);
		urlParams.put("baseType", baseType);
		urlParams.put("keyword", keyword);
		if (accountId != null && accountId.length() > 0)
			urlParams.put("accountId", String.valueOf(accountId));
		if (seriesId != null && seriesId.length() > 0)
			urlParams.put("predictedEventId", getPredictedEventId(seriesId));
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
	public String getPredictedEventId(String seriesId) {

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
	 * 
	 * @param date
	 * @return
	 */

	public static String getOffsetDate(String date) {
		String dataValue = null;
		try {
			if (date != null && date.length() > 0 && date.contains(",")) {

				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
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
		TransactionUtils transactionUtils = new TransactionUtils();
		TransactionsHelper transactionsHelper = new TransactionsHelper();

		LinkedHashMap<String, String> queryParams = transactionsHelper.createInputForGetAllTransactions(null, null,
				container, null, null, null, null, "SYSTEM_PREDICTED", null, null, null, null, null);

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
/**
 * 
 * @param container
 * @param categoryId
 * @param categorySource
 * @param consumer
 * @param memo
 * @param amount
 * @param baseType
 * @param reconStatus
 * @param checkNumber
 * @param date
 * @param sourceType
 * @param transactionType
 * @param currency
 * @return
 */
	public String createManualTxnObjectUpdate(String container, int categoryId,String categorySource, String consumer, 
			String memo, String amount,String baseType, String reconStatus,
			String checkNumber, String date, String sourceType, String transactionType,String currency) {

		JSONObject manualTxnObj = new JSONObject();
		JSONObject manualTransactionsObject = new JSONObject();
		JSONObject amountObj = new JSONObject();
		amountObj.put("amount", amount);
		amountObj.put("currency", currency);
		JSONObject descriptionObj = new JSONObject();
		descriptionObj.put("consumer", consumer);
		manualTxnObj.put("baseType", baseType);
		if (categoryId > 0) {
			manualTxnObj.put("categoryId", categoryId);
		}
		manualTxnObj.put("categorySource", categorySource);
		manualTxnObj.put("date", getOffsetDate(date));
		manualTxnObj.put("memo", memo);
		manualTxnObj.put("checkNumber", checkNumber);
		manualTxnObj.put("description", descriptionObj);
		manualTxnObj.put("sourceType", sourceType);
		manualTxnObj.put("container", container);
		manualTxnObj.put("amount", amountObj);
		manualTxnObj.put("transactionType", transactionType);
		manualTransactionsObject.put("manualTransaction", manualTxnObj);
		String manualTxnObjectJSON = manualTransactionsObject.toString();
		System.out.println("createManualTxnObjectUpdate is : " + manualTxnObjectJSON);
		return manualTxnObjectJSON;
	}

	/**
	 * 
	 * @param getRecurrResponse
	 * @return
	 */
	public JsonArray parseREResponseUpdateMultiple(String getRecurrResponse) {
		JsonArray reArray1 = null;
		try {
			JsonParser jsonParser = new JsonParser();
			JsonObject jo = (JsonObject) jsonParser.parse(getRecurrResponse);
			JsonArray jsonArr = jo.getAsJsonArray("predictedEvents");
			try {
				JsonObject reObj1 = (JsonObject) jsonArr.get(0);
				if (!(reObj1.getAsJsonArray("id") == null))
					reArray1 = reObj1.getAsJsonArray("recurringEvents");
				else {
					JsonObject reObj2 = (JsonObject) jsonArr.get(1);
					reArray1 = reObj2.getAsJsonArray("recurringEvents");
				}
			} catch (Exception e) {
				e.printStackTrace();
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return reArray1;

	}
/**
 * 
 * @param container
 * @param categoryId
 * @param categorySource
 * @param consumer
 * @param memo
 * @param amount
 * @param baseType
 * @param reconStatus
 * @param checkNumber
 * @param date
 * @param sourceType
 * @param transactionType
 * @param currency
 * @return
 */
	public String createPredictedTxnObjectUpdate(String container, int categoryId,String categorySource, String consumer, 
			String memo, String amount,String baseType, String reconStatus,
			String checkNumber, String date, String sourceType, String transactionType,String currency) {

		JSONObject manualTxnObj = new JSONObject();
		JSONObject manualTransactionsObject = new JSONObject();
		JSONObject amountObj = new JSONObject();
		amountObj.put("amount", amount);
		amountObj.put("currency", currency);
		JSONObject descriptionObj = new JSONObject();
		descriptionObj.put("consumer", consumer);
		manualTxnObj.put("baseType", baseType);
		if (categoryId > 0) {
			manualTxnObj.put("categoryId", categoryId);
		}
		manualTxnObj.put("categorySource", categorySource);
		manualTxnObj.put("date", getOffsetDate(date));
		manualTxnObj.put("memo", memo);
		manualTxnObj.put("checkNumber", checkNumber);
		manualTxnObj.put("description", descriptionObj);
		manualTxnObj.put("sourceType", sourceType);
		manualTxnObj.put("container", container);
		manualTxnObj.put("amount", amountObj);
		manualTxnObj.put("transactionType", transactionType);
		manualTransactionsObject.put("predictedTransaction", manualTxnObj);
		String manualTxnObjectJSON = manualTransactionsObject.toString();
		System.out.println("createPredictedTxnObjectUpdate is : " + manualTxnObjectJSON);
		return manualTxnObjectJSON;
	}

	/**
	 * Returns the recurring event id from API response if it is not passed in the
	 * CSV
	 *
	 * @param getRecurringResponse
	 * @param recurringEventId
	 * @return
	 */
	public String getRecurringEventId(Response getRecurringResponse, String recurringEventId) {
		if (recurringEventId.equalsIgnoreCase("VALID")) {
			JSONObject response = new JSONObject(getRecurringResponse.asString());
			JSONArray account=null;
			if(getRecurringResponse.asString().contains("predictedEvents")){
				account = response.getJSONArray("predictedEvents");
			}
			Integer id;
			if (account.getJSONObject(0).has("recurringEvents")) {
				id = getRecurringResponse.jsonPath().get("account[0].recurringEvents[0].id");
			} else {
				id = getRecurringResponse.jsonPath().get("predictedEvents[0].id");
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

	public String createPredictedEventForRefId(String accountId, String refTransactionId, String endDate,
			String startDate,String frequency) {
		JSONObject manualTxnObj = new JSONObject();
		JSONObject manualTransactionsObject = new JSONObject();
		manualTxnObj.put("accountId", accountId);
		manualTxnObj.put("startDate", getOffsetDate(startDate));
		manualTxnObj.put("endDate", getOffsetDate(endDate));
		manualTxnObj.put("refTransactionId", refTransactionId);
		manualTxnObj.put("frequency", frequency);
		manualTransactionsObject.put("predictedEvent", manualTxnObj);
		String manualTxnObjectJSON = manualTransactionsObject.toString();
		System.out.println("predictedEventObjectJSON is : " + manualTxnObjectJSON);
		return manualTxnObjectJSON;
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
	public Long getAggregatedTransactionId(Session session, String accountId, String container, String startDate, String endDate) throws Exception {

		if (container.equalsIgnoreCase("INVALID")) {
			return 12344323L;
		}
		TransactionUtils transactionUtils = new TransactionUtils();
		TransactionsHelper transactionsHelper = new TransactionsHelper();		

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
/**
 * 
 * @param amount
 * @param currency
 * @param consumer
 * @param baseType
 * @param categoryId
 * @param categorySource
 * @param startDate
 * @param memo
 * @param accountId
 * @param checkNumber
 * @param frequency
 * @param endDate
 * @param transactionType
 * @return
 */
	public String createPredictedEventobject(Double amount, String currency,
			String consumer, String baseType, int categoryId, String categorySource, String startDate, String memo,
			String accountId, String checkNumber, String frequency,String endDate,String transactionType) {

		JSONObject manualTxnObj = new JSONObject();
		JSONObject manualTransactionsObject = new JSONObject();
		JSONObject amountObj = new JSONObject();
		amountObj.put("amount", amount);
		amountObj.put("currency", currency);
		JSONObject descriptionObj = new JSONObject();
		descriptionObj.put("consumer", consumer);
		manualTxnObj.put("baseType", baseType);
		if (categoryId > 0) {
			manualTxnObj.put("categoryId", categoryId);
		}
		manualTxnObj.put("categorySource", categorySource);
		manualTxnObj.put("memo", memo);
		manualTxnObj.put("accountId", accountId);
		manualTxnObj.put("checkNumber", checkNumber);
		manualTxnObj.put("description", descriptionObj);
		manualTxnObj.put("amount", amountObj);
		manualTxnObj.put("startDate", getOffsetDate(startDate));
		manualTxnObj.put("endDate", getOffsetDate(endDate));
		manualTxnObj.put("transactionType", transactionType);
		manualTxnObj.put("frequency", frequency);
		manualTransactionsObject.put("predictedEvent", manualTxnObj);
		String manualTxnObjectJSON = manualTransactionsObject.toString();
		System.out.println("createPredictedEventobject is : " + manualTxnObjectJSON);
		return manualTxnObjectJSON;
	}
/**
 * 
 * @param amount
 * @param currency
 * @param consumer
 * @param baseType
 * @param categoryId
 * @param categorySource
 * @param startDate
 * @param memo
 * @param accountId
 * @param checkNumber
 * @param frequency
 * @param endDate
 * @param transactionType
 * @param predictedEventStatus
 * @param userApprovalStatus
 * @return
 */
	public String updatePredictedEventobject(Double amount, String currency,
			String consumer, String baseType, int categoryId, String categorySource, String startDate, String memo,
			String accountId, String checkNumber, String frequency,String endDate,String transactionType,
			String predictedEventStatus,String userApprovalStatus) {

		JSONObject manualTxnObj = new JSONObject();
		JSONObject manualTransactionsObject = new JSONObject();
		JSONObject amountObj = new JSONObject();
		amountObj.put("amount", amount);
		amountObj.put("currency", currency);
		JSONObject descriptionObj = new JSONObject();
		descriptionObj.put("consumer", consumer);
		manualTxnObj.put("baseType", baseType);
		if (categoryId > 0) {
			manualTxnObj.put("categoryId", categoryId);
		}
		manualTxnObj.put("categorySource", categorySource);
		manualTxnObj.put("memo", memo);
		manualTxnObj.put("accountId", accountId);
		manualTxnObj.put("checkNumber", checkNumber);
		manualTxnObj.put("description", descriptionObj);
		manualTxnObj.put("amount", amountObj);
		manualTxnObj.put("startDate", getOffsetDate(startDate));
		manualTxnObj.put("endDate", getOffsetDate(endDate));
		manualTxnObj.put("transactionType", transactionType);
		manualTxnObj.put("frequency", frequency);
		manualTxnObj.put("predictedEventStatus", predictedEventStatus);
		manualTxnObj.put("userApprovalStatus", userApprovalStatus);
		manualTransactionsObject.put("predictedEvent", manualTxnObj);
		String manualTxnObjectJSON = manualTransactionsObject.toString();
		System.out.println("updatePredictedEventobject  : " + manualTxnObjectJSON);
		return manualTxnObjectJSON;
	}
/**
 * 
 * @param updateMultipleREventsBodyParam
 * @param timestamp
 * @param sourceType
 * @return
 */
	public String updateRecurringEventListObject(String updateMultipleREventsBodyParam, long timestamp,
			String sourceType) {

		List<JsonObject> jsonObjList = new ArrayList<JsonObject>();
		CommonUtils commonUtils = new CommonUtils();

		Date date = new Date();
		String startDate = new SimpleDateFormat("yyyy-MM-dd").format(date);
		String endDate = "0,6,0";
		try {

			JsonParser jsonParser = new JsonParser();
			JsonObject jo = (JsonObject) jsonParser.parse(updateMultipleREventsBodyParam);
			JsonArray jsonArr = jo.getAsJsonArray("predictedEvents");
			Iterator<JsonElement> itr = jsonArr.iterator();
			while (itr.hasNext()) {

				JsonObject json = (JsonObject) itr.next();

				JsonPrimitive amount = json.getAsJsonObject("amount").getAsJsonPrimitive("amount");
				json.getAsJsonObject("amount").remove("amount");
				json.getAsJsonObject("amount").addProperty("amount", amount.getAsInt() + 25);
				json.remove("startDate");
				json.addProperty("startDate", startDate);
				if (sourceType.equalsIgnoreCase("MANUAL")) {
					json.remove("endDate");
					json.addProperty("endDate", commonUtils.getOffsetDate(endDate));
				}
				json.getAsJsonObject("description").remove("consumer");
				json.getAsJsonObject("description").addProperty("consumer", "editing_" + timestamp);
				json.addProperty("categorySource", "SYSTEM");
				jsonObjList.add(json);
			}

			JsonObject requestObj = new JsonObject();
			JsonArray jsonArray = jo.getAsJsonArray("predictedEvents");
			requestObj.add("predictedEvents", jsonArray);

			updateMultipleREventsBodyParam = requestObj.toString();

			System.out.println("updateMultipleREventsBodyParam::::::"+updateMultipleREventsBodyParam);

		} catch (Exception e) {
			e.printStackTrace();
		}

		return updateMultipleREventsBodyParam;
	}
	/**
	 * @author Mahadev Calls GET /transactions to fetch only system predicted
	 *         transactions
	 * @param container
	 *            Container of the transaction required
	 * @return Returns the transaction id of the system transactions
	 *  @param envSession
	 * @param container
	 * @return
	 */
	public Integer getSystemTransactionId(EnvSession envSession, String container) {
		TransactionUtils transactionUtils = new TransactionUtils();
		HashMap<String, String> urlParams = new HashMap<String, String>();
		urlParams.put("accountId", "");
		urlParams.put("container", container);
		urlParams.put("sourceType", "SYSTEM_PREDICTED");
		urlParams.put("fromDate", "");
		urlParams.put("toDate", "");

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
				int id = (int) response.jsonPath().get("transaction[0].id");
				return id;
			} catch (Exception e) {
				e.printStackTrace();
				Assert.fail("Not able to get the system transaction id" + e.getMessage());
			}
		}
		return null;
	}
	
	public String getMemIdOfUser(String loginUser) {
		System.out.println("loginUser::"+loginUser);
		DBHelper dbHelper=new DBHelper(); 
		Connection conn=dbHelper.getDBConnection();
		Statement st = null;
		ResultSet rs = null;
		String getMemIdFromMemTableQuery = "select *from mem where login_name = '"+loginUser+"'";
		int memId = 0;
		try {
			st = conn.createStatement();
			rs =st.executeQuery(getMemIdFromMemTableQuery);
			while (rs.next()) {
				memId= rs.getInt("MEM_ID");
			} 
		}catch (SQLException e) {
			e.printStackTrace();
		}
		finally {
			dbHelper.closeConnectionStatementResultSet(conn, st, rs);
		}		
		return Integer.toString(memId);
	}
	
	public Map<String, Object> createQueryParamsForGetBalances(Session session, String accountId, String fromDate,
			String toDate, String interval) {
		CommonUtils commonUtils = new CommonUtils();
		Map<String, Object> queryParam = new HashMap<>();
		if (accountId != null && accountId.length() > 0)
			queryParam.put("accountId", accountsHelper.getAccountId(accountId, session).toString());
		
		if(interval.contains("M")){
			queryParam.put("fromDate", getOffsetDateMonthly(fromDate));
			queryParam.put("toDate", getOffsetDateMonthly(toDate));
		}else{
			queryParam.put("fromDate", commonUtils.getOffsetDate(fromDate));
			queryParam.put("toDate", commonUtils.getOffsetDate(toDate));
		}
		queryParam.put("interval", interval);
		return queryParam;
	}

	public Map<String, Object> createQueryParamsForGetBalancesFTUE(Session session, String accountId, String fromDate,
			String toDate, String interval,String preferredAccounts) {
		CommonUtils commonUtils = new CommonUtils();
		Map<String, Object> queryParam = new HashMap<>();
		if (accountId != null && accountId.length() > 0)
			queryParam.put("accountId", accountsHelper.getAccountId(accountId, session).toString());
		if(interval.contains("M")){
			queryParam.put("fromDate", getOffsetDateMonthly(fromDate));
			queryParam.put("toDate", getOffsetDateMonthly(toDate));
		}else{
			queryParam.put("fromDate", commonUtils.getOffsetDate(fromDate));
			queryParam.put("toDate", commonUtils.getOffsetDate(toDate));
		}
		queryParam.put("interval", interval);
		queryParam.put("preferredAccounts", preferredAccounts);
		return queryParam;
	}

	/**
	 * @param date
	 * @return
	 */
	public String getOffsetDateMonthly(String date) {
		String dataValue = null;
		try {
			if (date != null && date.length() > 0 && date.contains(",")) {

				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
				Calendar cal = Calendar.getInstance();
				cal.setTime(new Date());
				String dateAddition[] = date.split(",");
				cal.add(Calendar.YEAR, Integer.parseInt(dateAddition[0]));
				cal.add(Calendar.MONTH, Integer.parseInt(dateAddition[1]));
				dataValue = sdf.format(cal.getTime());

			} else {
				return date;
			}
		} catch (Exception e) {
			dataValue = date;
		}
		System.out.println("dataValue::"+dataValue);
		return dataValue;
	}
	 public void updateFTUEStatus(String userId,Long bankAccountId)
	 {

			DBHelper dbHelper=new DBHelper(); 
			Connection conn=dbHelper.getDBConnection();
			Statement st = null;
			ResultSet rs = null;

			String insertFTUEDetailsUserQuery="insert into mem_pref (MEM_PREF_ID ,mem_id,PREF_KEY_VALUE,CREATED,LAST_UPDATED,PREF_KEY_NAME,PREF_ORDER,IS_DELETED,IS_SHAREABLE,APP_ID,IS_FREE_FOR_ALL,IS_ENCRYPTED,IS_PROPERTY_BAG_PARAM,IS_PB_PARAM_WRITABLE,PB_PARAM_VALIDATION_RULE_ID) values ((select max(mem_pref_id)+1 from mem_pref),'"+userId+"','false',1507547428,1507548321,'USD-isNewUser',0,0,0,null,0,0,null,null,null)";
			String insertFTUEDetailsAccountsQuery="insert into mem_pref (MEM_PREF_ID ,mem_id,PREF_KEY_VALUE,CREATED,LAST_UPDATED,PREF_KEY_NAME,PREF_ORDER,IS_DELETED,IS_SHAREABLE,APP_ID,IS_FREE_FOR_ALL,IS_ENCRYPTED,IS_PROPERTY_BAG_PARAM,IS_PB_PARAM_WRITABLE,PB_PARAM_VALIDATION_RULE_ID) values ((select max(mem_pref_id)+1 from mem_pref),'"+userId+"','banks|"+bankAccountId+"',1507547428,1507548321,'USD-sfcUserSelectedAccountsFilterPref',0,0,0,null,0,0,null,null,null)";
			
			System.out.println(insertFTUEDetailsUserQuery);
			System.out.println(insertFTUEDetailsAccountsQuery);

			try {
				st = conn.createStatement();
				rs =st.executeQuery(insertFTUEDetailsUserQuery);
				rs =st.executeQuery(insertFTUEDetailsAccountsQuery);
			}catch (SQLException e) {
				e.printStackTrace();
			}
			finally {
				dbHelper.closeConnectionStatementResultSet(conn, st, rs);
			}
		
	 }
}

