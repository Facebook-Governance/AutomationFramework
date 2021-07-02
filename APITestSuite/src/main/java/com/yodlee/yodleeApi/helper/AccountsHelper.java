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

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.apache.commons.configuration.HierarchicalConfiguration;
import org.apache.commons.lang.StringUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.testng.Assert;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonPrimitive;
import com.yodlee.yodleeApi.common.Configuration;
import com.yodlee.yodleeApi.constants.Constants;
import com.yodlee.yodleeApi.interfaces.Session;
import com.yodlee.yodleeApi.pojo.AdditionalDataSet;
import com.yodlee.yodleeApi.pojo.HttpMethodParameters;
import com.yodlee.yodleeApi.sdg.ProcessSdg;
import com.yodlee.yodleeApi.sdg.RequestSequence;
import com.yodlee.yodleeApi.utils.apiUtils.AccountUtils;
import com.yodlee.yodleeApi.utils.commonUtils.CommonUtils;

import io.restassured.response.Response;

/**
 * Helper class for Manual and Aggregated account related Yodlee API's. It
 * contains methods to create query/path/body params and other related methods.
 * 
 * @author Soujanya Kokala and Rahul Kumar
 *
 */
public class AccountsHelper {

	/**
	 * This method is used to check whether the value from CSV file is empty or NUL
	 * and return the same.
	 * 
	 * @param valueFromCSV
	 *            value that need to checked against null/empty.
	 * @return
	 */

	public Long providerAccountId;
	List<String> accountIdList = new ArrayList<>();

	public String getFilterValue(String valueFromCSV) {

		if (Constants.EMPTY.equalsIgnoreCase(valueFromCSV)) {
			return Constants.EMPTY_STRING;
		} else if (Constants.NULL.equalsIgnoreCase(valueFromCSV)) {
			return null;
		}

		return valueFromCSV;
	}

	public String getProviderAccountId(String valueFromCSV) {
		AccountsHelper accountsHelper = new AccountsHelper();

		if (Constants.VALID.equals(valueFromCSV)) {
			return String.valueOf(accountsHelper.providerAccountId);
		} else if (Constants.MAX_COUNT.equals(valueFromCSV)) {
			return Constants.MAX_LENGTH_LONG_ID;
		}

		return getFilterValue(valueFromCSV);

	}

	/**
	 * This method should be used in NON-SDG only to generate QueryParams
	 * for @API-GET /accounts
	 * 
	 * @param status
	 * @param container
	 * @param providerAccountId
	 * @param accountId
	 * @param accountReconType
	 * @param include
	 * @param requestId
	 * @return queryParams
	 */
	public Map<String, Object> createQueryParamsForGetAccounts(String status, String container,
			String providerAccountId, String accountId, String accountReconType, String include, String requestId) {

		Map<String, Object> queryParam = new LinkedHashMap<>();

		if (status != null) {
			queryParam.put(Constants.STATUS_FILTER, status);
		}
		if (container != null) {
			queryParam.put(Constants.CONTAINER_FILTER, container);
		}
		if (providerAccountId != null) {
			queryParam.put(Constants.PROVIDER_ACCOUNT_ID_FILTER, providerAccountId);
		}
		if (accountId != null) {
			queryParam.put(Constants.ACCOUNT_ID_FILTER, accountId);
		}
		if (accountReconType != null) {
			queryParam.put(Constants.ACCOUNT_RECON_TYPE_FILTER, accountReconType);
		}
		if (include != null) {
			queryParam.put(Constants.INCLUDE_FILTER, include);
		}
		if (requestId != null) {
			queryParam.put("requestId", requestId);
		}

		return queryParam;
	}
	
	
	/**
	 * @param accountId
	 * @return
	 * @throws JSONException
	 */
	public Long getAccountId(HashMap<String, Long> accountsMap ,String container, Session obj) throws JSONException {
		System.out.println("account id in the response" + container);
		System.out.println("accountsMap::" + container);
		Long account = null;
		if (container.equalsIgnoreCase("OTHER_USER")) {
			account = accountsMap.get("bank");
			return (account) - 25;
		}
		if (container.equalsIgnoreCase("CARD") || container.equalsIgnoreCase("CREDITCARD")) {
			account = accountsMap.get("creditCard");
			return (account);
		}
		if (container.equalsIgnoreCase("BANK")) {
			account = accountsMap.get("bank");
			System.out.println("accounts map" + accountsMap);
			return (account);
		}
		if (container.equalsIgnoreCase("INVESTMENT")) {
			account = accountsMap.get("investment");
			return (account);
		}
		if (container.equalsIgnoreCase("INSURANCE")) {
			account = accountsMap.get("insurance");
			return (account);
		}
		if (container.equalsIgnoreCase("LOANS")) {
			account = accountsMap.get("loans");
			return (account);
		}
		if (container.equalsIgnoreCase("INVALID")) {
			return 123234L;
		}
		return account;

	}

	/**
	 * This method is used to generate bodyParams for create Manual Account
	 * i.e. @API-POST /accounts
	 * 
	 * @param accountType
	 *            it is account type
	 * @param accountName
	 *            it is account name
	 * @param nickname
	 *            it is nick name
	 * @param accountNumber
	 *            it is the account number
	 * @param amount
	 *            it is the amount
	 * @param currency
	 *            it is currency
	 * @param includeInNetWorth
	 *            it is includeInNetWorth
	 * @param memo
	 *            it is memo
	 * @return
	 * @throws JSONException
	 */
	public String createBodyParamsForAddingManualAccount(String accountType, String accountName, String nickname,
			String accountNumber, Double amount, String currency, String includeInNetWorth, String memo)
			throws JSONException {
		JSONObject accountObj = new JSONObject();
		accountObj.put("accountType", accountType);
		accountObj.put("accountName", accountName);
		accountObj.put("nickname", nickname);
		accountObj.put("accountNumber", accountNumber);

		accountObj.put("includeInNetWorth", includeInNetWorth);
		accountObj.put("memo", memo);

		JSONObject balanceObj = new JSONObject();
		balanceObj.put("amount", amount);
		balanceObj.put("currency", currency);

		accountObj.put("balance", balanceObj);
		JSONObject manualAccObj = new JSONObject();
		manualAccObj.put("account", accountObj);

		String createManualActBodyParms = manualAccObj.toString();
		System.out.println("createManualActBodyParms params formed is:: " + createManualActBodyParms);

		return createManualActBodyParms;
	}

	/**
	 * This method is used to generate bodyParams for updating Manual Account
	 * i.e. @API: PUT /accounts/{accountId}
	 * 
	 * "isEbillEnrolled" field is applicable only for bill accounts.
	 * 
	 * @param nickname
	 *            it is nick name
	 * @param accountStatus
	 *            it is account status
	 * @param includeInNetWorth
	 *            it is includeInNetWorth value
	 * @param memo
	 *            it is memo
	 * @param isEbillEnrolled
	 *            it is isEbillEnrolled for bill accounts only
	 * @return
	 */
	public String createBodyParamsForUpdateManualAccnt(String nickname, String accountStatus, String includeInNetWorth,
			String memo, String isEbillEnrolled) {

		JSONObject accountObj = new JSONObject();
		JSONObject accountUpdateParamObj = new JSONObject();

		if (nickname != null && !nickname.isEmpty()) {
			accountUpdateParamObj.put("nickname", nickname);
		}
		if (accountStatus != null && !accountStatus.isEmpty()) {
			accountUpdateParamObj.put("accountStatus", accountStatus);
		}
		if (includeInNetWorth != null && !includeInNetWorth.isEmpty()) {
			accountUpdateParamObj.put("includeInNetWorth", includeInNetWorth);
		}
		if (memo != null && !memo.isEmpty()) {
			accountUpdateParamObj.put("memo", memo);
		}
		if (isEbillEnrolled != null && !isEbillEnrolled.isEmpty()) {
			accountUpdateParamObj.put("isEbillEnrolled", isEbillEnrolled);
		}

		accountObj.put("account", accountUpdateParamObj);
		System.out.println("body param formed is:: " + accountUpdateParamObj);

		return accountObj.toString();
	}

	/**
	 * This method is used by API:GET /accounts/historicalBalances to form its query
	 * params.
	 * 
	 * @param accountId
	 *            ACCOUNT_ID_FILTER
	 * @param includeCF
	 *            INCLUDE_CF_FILTER
	 * @param fromDate
	 *            FROM_DATE_FILTER
	 * @param toDate
	 *            TO_DATE_FILTER
	 * @param interval
	 *            INTERVAL_FILTER
	 * @param accountReconType
	 *            ACCOUNT_RECON_TYPE_FILTER
	 * @param skip
	 *            SKIP_FILTER
	 * @param top
	 *            TOP_FILTER
	 * 
	 * @return
	 */
	public Map<String, Object> createQueryParamsForGetHistoricalBalance(String accountId, String includeCF,
			String fromDate, String toDate, String interval, String accountReconType, String skip, String top) {

		CommonUtils commonUtils = new CommonUtils();
		Map<String, Object> queryParam = new LinkedHashMap<>();
		if (accountId != null && !accountId.isEmpty()) {
			queryParam.put(Constants.ACCOUNT_ID_FILTER, accountId);
		}
		if (includeCF != null && !includeCF.isEmpty()) {
			queryParam.put(Constants.INCLUDE_CF_FILTER, includeCF);
		}
		if (fromDate != null && !fromDate.isEmpty()) {
			queryParam.put(Constants.FROM_DATE_FILTER, commonUtils.getOffsetDate(fromDate));
		}
		if (toDate != null && !toDate.isEmpty()) {
			queryParam.put(Constants.TO_DATE_FILTER, commonUtils.getOffsetDate(toDate));
		}
		if (interval != null && !interval.isEmpty()) {
			queryParam.put(Constants.INTERVAL_FILTER, interval);
		}
		if (accountReconType != null && !accountReconType.isEmpty()) {
			queryParam.put(Constants.ACCOUNT_RECON_TYPE_FILTER, accountReconType);
		}
		if (skip != null && !skip.isEmpty()) {
			queryParam.put(Constants.SKIP_FILTER, skip);
		}
		if (top != null && !top.isEmpty()) {
			queryParam.put(Constants.TOP_FILTER, top);
		}

		return queryParam;
	}

	/**
	 * This method can be used in both SDG and NON-SDG, if query param has to be
	 * formed with only one parameter.
	 * 
	 * @param Firstkey
	 *            it is a key
	 * @param Firstvalue
	 *            it is a value that needs to be assigned for that key
	 * @return
	 */
	public LinkedHashMap<String, Object> passOneQueryParam(String Firstkey, Object Firstvalue) {
		LinkedHashMap<String, Object> queryParam = new LinkedHashMap<String, Object>();
		queryParam.put(Firstkey, Firstvalue);

		return queryParam;
	}

	/**
	 * This method can be used in both SDG and NON-SDG, if path param has to be
	 * formed with only one parameter.
	 * 
	 * @param Firstkey
	 *            it is a key
	 * @param Firstvalue
	 *            it is a value that needs to be assigned for that key
	 * @return
	 */
	public HttpMethodParameters createPathParam(String Firstkey, Object Firstvalue) {
		LinkedHashMap<String, Object> pathParam = new LinkedHashMap<String, Object>();
		pathParam.put(Firstkey, Firstvalue);

		HttpMethodParameters httpParams = HttpMethodParameters.builder().build();
		httpParams.setPathParams(pathParam);
		return httpParams;
	}

	/**
	 * @param accountId
	 * @return
	 * @throws JSONException
	 */
	public Long getAccountId(String container, Session obj) throws JSONException {
		System.out.println("account id in the response" + container);
		HashMap<String, Long> accountsMap = getAccounts(obj);
		Long account = null;
		if (container.equalsIgnoreCase("OTHER_USER")) {
			account = accountsMap.get("bank");
			return (account) - 25;
		}
		if (container.equalsIgnoreCase("CARD") || container.equalsIgnoreCase("CREDITCARD")) {
			account = accountsMap.get("creditCard");
			return (account);
		}
		if (container.equalsIgnoreCase("BANK")) {
			account = accountsMap.get("bank");
			System.out.println("accounts map" + accountsMap);
			return (account);
		}
		if (container.equalsIgnoreCase("INVESTMENT")) {
			account = accountsMap.get("investment");
			return (account);
		}
		if (container.equalsIgnoreCase("INSURANCE")) {
			account = accountsMap.get("insurance");
			return (account);
		}
		if (container.equalsIgnoreCase("LOANS")) {
			account = accountsMap.get("loans");
			return (account);
		}
		if (container.equalsIgnoreCase("INVALID")) {
			return 123234L;
		}
		return account;

	}

	/**
	 * @param obj
	 * @return
	 * @throws JSONException
	 */
	public HashMap<String, Long> getAccounts(Session obj) throws JSONException {
		AccountUtils accountUtils = new AccountUtils();
		Response response = accountUtils.getAllAccounts(obj);
		System.out.println("response::::::::"+response.asString());
		JSONObject account = new JSONObject(response.asString());
		HashMap<String, Long> accountsMap = new HashMap<String, Long>();
		JSONArray accountsArray = account.getJSONArray("account");
		for (int i = 0; i < accountsArray.length(); i++) {
			accountsMap.put(accountsArray.getJSONObject(i).getString("CONTAINER"),
					accountsArray.getJSONObject(i).getLong("id"));

		}
		return accountsMap;
	}

	/**
	 * @param accountType
	 * @param accountName
	 * @param nickname
	 * @param accountNumber
	 * @param loginName
	 * @param password
	 * @param includeInNetWorth
	 * @param memo
	 * @param url
	 * @param amount
	 * @param currency
	 * @return
	 * @throws JSONException
	 */
	public String createManualActObjForBankAndInvContainer(String accountType, String accountName, String nickname,
			String accountNumber, String loginName, String password, String accountStatus, String includeInNetWorth, String memo, String url,
			double amount, String currency) throws JSONException {

		JSONObject accountObj = new JSONObject();
		accountObj.put("accountType", accountType);
		accountObj.put("accountName", accountName);
		accountObj.put("nickname", nickname);
		accountObj.put("accountNumber", accountNumber);
		if(accountStatus!=null) {
		accountObj.put("accountStatus", accountStatus);
		}
		accountObj.put("loginName", loginName);
		accountObj.put("password", password);
		accountObj.put("includeInNetWorth", includeInNetWorth);
		accountObj.put("memo", memo);
		accountObj.put("url", url);
		JSONObject balanceObj = new JSONObject();
		balanceObj.put("amount", amount);
		balanceObj.put("currency", currency);
		accountObj.put("balance", balanceObj);
		JSONObject manualAccObj = new JSONObject();
		manualAccObj.put("account", accountObj);
		String ManualActObjForBankAndInvContainer = manualAccObj.toString();
		System.out.println(
				"Manual transaction Object for bank and investment container is " + ManualActObjForBankAndInvContainer);
		return ManualActObjForBankAndInvContainer;
	}

	/**
	 * @param accountType
	 * @param accountName
	 * @param nickname
	 * @param accountNumber
	 * @param loginName
	 * @param password
	 * @param frequency
	 * @param nextDueDate
	 * @param memo
	 * @param url
	 * @param includeInNetWorth
	 * @param amount
	 * @param currency
	 * @param dueAmount
	 * @param dueCurrency
	 * @return
	 * @throws JSONException
	 */
	public String createManualActObjForCardAndLoanContainer(String accountType, String accountName, String nickname,
			String accountNumber, String loginName, String password, String frequency, String nextDueDate, String memo,
			String url, String includeInNetWorth, Double amount, String currency, Double dueAmount, String dueCurrency)
			throws JSONException {
		JSONObject accountObj = new JSONObject();
		accountObj.put("accountType", accountType);
		accountObj.put("accountName", accountName);
		accountObj.put("nickname", nickname);
		accountObj.put("accountNumber", accountNumber);
		accountObj.put("loginName", loginName);
		accountObj.put("password", password);
		accountObj.put("frequency", frequency);
		accountObj.put("dueDate", nextDueDate);
		accountObj.put("memo", memo);
		accountObj.put("url", url);
		accountObj.put("includeInNetWorth", includeInNetWorth);
		JSONObject dueAmountObj = new JSONObject();
		dueAmountObj.put("amount", dueAmount);
		dueAmountObj.put("currency", dueCurrency);
		accountObj.put("amountDue", dueAmountObj);
		JSONObject balanceObj = new JSONObject();
		balanceObj.put("amount", amount);
		balanceObj.put("currency", currency);
		accountObj.put("balance", balanceObj);
		JSONObject manualAccObj = new JSONObject();
		manualAccObj.put("account", accountObj);
		String ManualActObjForCardAndLoanContainer = manualAccObj.toString();
		System.out.println(
				"Manual transaction Object for card and loan container is " + ManualActObjForCardAndLoanContainer);
		return ManualActObjForCardAndLoanContainer;
	}

	public JsonArray parseGetRecurringEventResponse(String getRecurringResponse) {

		JsonArray reArray1 = null;
		JsonArray reArray2 = new JsonArray();

		try {

			JsonParser jsonParser = new JsonParser();
			JsonObject jsonObj = (JsonObject) jsonParser.parse(getRecurringResponse);
			JsonArray jsonArr = jsonObj.getAsJsonArray("account");

			if (getRecurringResponse.contains("MANUAL")) {
				JsonObject reObj1 = (JsonObject) jsonArr.get(1);
				reArray1 = reObj1.getAsJsonArray("recurringEvents");

			}
			System.out.println("reArray1::::::" + reArray1);
			if (getRecurringResponse.contains("SYSTEM_PREDICTED")) {
				JsonObject reObj1 = (JsonObject) jsonArr.get(1);
				reArray1 = reObj1.getAsJsonArray("recurringEvents");
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		for (int i = 0; i < 4; i++) {
			reArray2.add(reArray1.get(i));
		}
		return reArray2;
	}

	public String updateRecurringEventListObject(String updateMultipleREventsBodyParam, long timestamp,
			String sourceType) {

		List<JsonObject> jsonObjList = new ArrayList<JsonObject>();
		Date date = new Date();
		String startDate = new SimpleDateFormat("yyyy-MM-dd").format(date);
		String endDate = "2017-12-30";
		int count = 0;
		try {

			JsonParser jsonParser = new JsonParser();
			JsonObject jo = (JsonObject) jsonParser.parse(updateMultipleREventsBodyParam);
			JsonObject account = jo.getAsJsonObject("account");
			JsonArray jsonArr = jo.getAsJsonObject("account").getAsJsonArray("recurringEvents");
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
					json.addProperty("endDate", endDate);
				}
				json.getAsJsonObject("description").remove("consumer");
				json.getAsJsonObject("description").addProperty("consumer", "editing_" + timestamp);
				json.addProperty("categorySource", "SYSTEM");
				jsonObjList.add(json);
			}

			JsonObject requestObj = new JsonObject();
			JsonObject accountObj = new JsonObject();
			JsonArray jsonArray = jo.getAsJsonObject("account").getAsJsonArray("recurringEvents");
			accountObj.add("recurringEvents", jsonArray);
			requestObj.add("account", accountObj);

			updateMultipleREventsBodyParam = requestObj.toString();
			
			System.out.println("updateMultipleREventsBodyParam::::::"+updateMultipleREventsBodyParam);

		} catch (Exception e) {
			e.printStackTrace();
		}

		return updateMultipleREventsBodyParam;
	}

	public Map<String, Object> createQueryParamsForGetBalances(Session session, String accountId, String fromDate,
			String toDate, String interval) {
		CommonUtils commonUtils = new CommonUtils();
		Map<String, Object> queryParam = new HashMap<>();
		if (accountId != null && accountId.length() > 0)
			queryParam.put("accountId", getAccountId(accountId, session).toString());
		queryParam.put("fromDate", commonUtils.getOffsetDate(fromDate));
		queryParam.put("toDate", commonUtils.getOffsetDate(toDate));
		queryParam.put("interval", interval);
		return queryParam;
	}

	static String workingDir = System.getProperty("user.dir");
	private static String FILEPATH = workingDir + "\\src\\test\\resources\\TestData\\JSONFiles\\Recurring";

	public String getRecurringEventJson(String fileName) {
		System.out.println("FILEPATH:" + FILEPATH);
		File file = new File(FILEPATH + "\\" + fileName);
		String jsonString = null;

		try {
			jsonString = org.apache.commons.io.FileUtils.readFileToString(file, "UTF-8");
		} catch (IOException e) {

			e.printStackTrace();
		}
		return jsonString;
	}

	public Map<String, Object> createQueryParamsForMultipleAccounts(Session commonEnvSession, String account1,
			String account2, String fromDate, String toDate, String interval) throws Exception {
		CommonUtils commonUtils = new CommonUtils();
		Map<String, Object> queryParam = new HashMap<>();
		String bankAccount = getAccountId(account1, commonEnvSession).toString();
		String cardAccount = getAccountId(account2, commonEnvSession).toString();
		String accountId = bankAccount + "," + cardAccount;
		queryParam.put("accountId", accountId);
		queryParam.put("fromDate", commonUtils.getOffsetDate(fromDate));
		queryParam.put("toDate", commonUtils.getOffsetDate(toDate));
		queryParam.put("interval", interval);

		return queryParam;
	}

	public String createJsonForUpdateAggregatedAccount(String nickName, String accountStatus, String includeNetworth,
			String memo) throws JSONException {

		JSONObject parentObject = new JSONObject();
		JSONObject accountEntity = new JSONObject();
		accountEntity.put("nickname", nickName);
		accountEntity.put("accountStatus", accountStatus);
		if (includeNetworth != null && includeNetworth != "")
			accountEntity.put("includeInNetWorth", includeNetworth);
		accountEntity.put("memo", memo);

		parentObject.put("account", accountEntity);

		return parentObject.toString();
	}

	public String createManualActObjForBillContainer(String accountType, String accountName, String nickname,
			String accountNumber, String loginName, String password, String frequency, String nextDueDate, String memo,
			String url, Double amount, String currency) throws JSONException {
		JSONObject accountObj = new JSONObject();
		accountObj.put("accountType", accountType);
		accountObj.put("accountName", accountName);
		accountObj.put("nickname", nickname);
		accountObj.put("accountNumber", accountNumber);
		accountObj.put("loginName", loginName);
		accountObj.put("password", password);
		accountObj.put("frequency", frequency);
		accountObj.put("dueDate", nextDueDate);
		accountObj.put("memo", memo);
		accountObj.put("url", url);
		JSONObject dueAmountObj = new JSONObject();
		dueAmountObj.put("amount", amount);
		dueAmountObj.put("currency", currency);
		accountObj.put("amountDue", dueAmountObj);
		JSONObject manualAccObj = new JSONObject();
		manualAccObj.put("account", accountObj);
		String ManualActObjForBillContainer = manualAccObj.toString();
		System.out.println("Manual transaction Object for bill container is " + ManualActObjForBillContainer);
		return ManualActObjForBillContainer;
	}
	
	public String createManualActObjForInsuranceContainer(
			String accountType, String accountName, String nickname,
			String accountNumber, String loginName, String password,
			String frequency, String nextDueDate, String memo, String url,
			String includeInNetWorth, String isAsset, Double amount,
			String currency, Double dueAmount, String dueCurrency)
			throws JSONException {
		JSONObject accountObj = new JSONObject();
		accountObj.put("accountType", accountType);
		accountObj.put("accountName", accountName);
		accountObj.put("nickname", nickname);
		accountObj.put("accountNumber", accountNumber);
		accountObj.put("loginName", loginName);
		accountObj.put("password", password);
		accountObj.put("frequency", frequency);
		accountObj.put("dueDate", nextDueDate);
		accountObj.put("memo", memo);
		accountObj.put("url", url);
		accountObj.put("includeInNetWorth", includeInNetWorth);
		accountObj.put("isAsset", isAsset);
		JSONObject dueAmountObj = new JSONObject();
		dueAmountObj.put("amount", dueAmount);
		dueAmountObj.put("currency", dueCurrency);
		accountObj.put("amountDue", dueAmountObj);
		JSONObject balanceObj = new JSONObject();
		balanceObj.put("amount", amount);
		balanceObj.put("currency", currency);
		accountObj.put("balance", balanceObj);
		JSONObject manualAccObj = new JSONObject();
		manualAccObj.put("account", accountObj);
		String ManualActObjForInsuranceContainer = manualAccObj.toString();
		System.out
				.println("Manual transaction Object for insurance container is "
						+ ManualActObjForInsuranceContainer);
		return ManualActObjForInsuranceContainer;
	}
	
	public JSONObject constructPerferenceData(String preferenceType, String otherAmount, 
			Long paymentAccountId) {
		
		System.out.println("paymentAccountId::::::"+paymentAccountId);
		JSONObject bodyParam = new JSONObject();
		JSONObject paymentPreference = new JSONObject();
		JSONObject payment = new JSONObject();
		try {
			paymentPreference.put("paymentAccountId", paymentAccountId);
			paymentPreference.put("type", preferenceType);
			if (otherAmount != null && otherAmount.length() > 0) {
				payment.put("amount", Integer.parseInt(otherAmount));
				payment.put("currency", "USD");
				paymentPreference.put("payment", payment);
			}
		} catch (Exception e) {
			Assert.fail("JSON exception in update account");
			e.printStackTrace();
		}
		JSONObject account = new JSONObject();
		account.put("paymentPref", paymentPreference);
		bodyParam.put("account", account);
		
		return bodyParam;
	}
	
		/**
	 * @author Soujanya Kokala
	 */
	public HttpMethodParameters createInputForGetAccounts(String status, String container,String providerAccountId, String accountId, String accountReconType,
			String include, String requestId) {
		Map<String, Object> queryParam = createQueryParamsForGetAccounts(getFilterValue(status),
				getFilterValue(container),providerAccountId, null, getFilterValue(accountReconType),
				null, null);

		HttpMethodParameters httpMethodParameters = HttpMethodParameters.builder().build();
		httpMethodParameters.setQueryParams(queryParam);
		return httpMethodParameters;
	}
	
	
	/**
	 * @author Soujanya Kokala
	 */
	public HttpMethodParameters createInputForGetAccountDetails(String status, String container,String providerAccountId, String accountId, String accountReconType,
			String include, String requestId) {
		Map<String, Object> queryParam = createQueryParamsForGetAccounts(null,
				container, null, null, null, null, null);

		HttpMethodParameters httpMethodParameters = HttpMethodParameters.builder().build();
		httpMethodParameters.setQueryParams(queryParam);

		Map<String, Object> pathParams = new HashMap<>();
		pathParams.put("accountId", accountId);

		httpMethodParameters.setPathParams(pathParams);
		return httpMethodParameters;
	}
	
	/**
	 * @author Soujanya Kokala
	 */
	public String accMasking(String accountNumber) {
		System.out.println("Account Number in Yodlee " + accountNumber);
		int len = accountNumber.length();
		String accNum = "";
		int i;
		for (i = 0; i < 8 - Configuration.getInstance().getMaskLength(); i++)
			accNum += Configuration.getInstance().getMaskCharacter();
		accountNumber = accountNumber.substring(len - Configuration.getInstance().getMaskLength(), len);
		accNum += accountNumber;
		System.out.println("Masked Account Number: " + accNum);
		return accNum;
	}

	public JsonArray parseGetREResponseForSystem(String getRecurrResponse){

	      JsonArray reArray1=null;
	      JsonArray reArray2 = new JsonArray();
	      try {
	            JsonParser jsonParser = new JsonParser();
	            JsonObject jo = (JsonObject)jsonParser.parse(getRecurrResponse);
	            JsonArray jsonArr =  jo.getAsJsonArray("account");
	            JsonObject reObj1 = (JsonObject)jsonArr.get(0);
	           JsonObject reObj2 = (JsonObject)jsonArr.get(1);
	            reArray1 = reObj1.getAsJsonArray("recurringEvents");             
	           reArray2 = reObj2.getAsJsonArray("recurringEvents");
	            JsonObject recurrEvtObj = (JsonObject) reArray1.get(0);
	            System.out.println("reArray1"+reArray1);
	            System.out.println("reArray2"+reArray2);
	            reArray2.add(recurrEvtObj);
	            System.out.println("reArray1 after adding ::"+reArray1);
	      }
	      catch (NullPointerException e) {
	            e.printStackTrace();
	      }
	      System.out.println("Printing the array 2::"+reArray1);
	      return reArray2;
	    }
	
	public String getAccountIdParamValue(String accountIdFromCsv) {

		String result = null;
		System.out.println("Account id from CSV : " + accountIdFromCsv);

		switch (accountIdFromCsv.toUpperCase()) {

		case "ONE":
			System.out.println("============ ONE ======================");
			result = String.valueOf(accountIdList.get(0));

			break;

		case "TWO":

			System.out.println("============ TWO ======================");
			result = StringUtils.join(
					new String[] { String.valueOf(accountIdList.get(0)), String.valueOf(accountIdList.get(1)) }, ",");

			break;

		case "THREE":

			System.out.println("============ THREE ======================");
			result = StringUtils.join(new String[] { String.valueOf(accountIdList.get(0)),
					String.valueOf(accountIdList.get(1)), String.valueOf(accountIdList.get(2)) }, ",");

			break;

		case "ALL":
			System.out.println("============ ALL ======================");
			String entireResult = StringUtils.join(new String[] { accountIdList.toString() }, ",");
			result = entireResult.replaceAll("[\\[\\]]", "").trim();
			System.out.println("============ ALL ======================" + result);
			break;

		case "INVALID":
			System.out.println("============ INVALID ======================");
			Random random = new Random();
			result = String.valueOf(random.nextLong());
			break;

		default:
			System.out.println("============ DEFAULT ======================");
			result = accountIdFromCsv;
			break;

		}

		System.out.println("Final Result : " + result);
		return result;
	}

	/**
	 * Added as part of B-36571 - Reconciliation of Pending Transaction on
	 * Apr-12-2019 This method can be used in both SDG and NON-SDG, to form the
	 * dataset
	 * 
	 * @param dataSetTemplatePath
	 * @return dataset as List<AdditionalDataSet>, which is formed based on the xml
	 *         file passed
	 * @author namitha
	 */
	public List<AdditionalDataSet> getAdditionalDataSet(String dataSetTemplatePath) {

		ProcessSdg processSdg = new ProcessSdg(dataSetTemplatePath);
		List<Object> listOfMaps = processSdg.list;
		List<AdditionalDataSet> additionalDataSets = null;
		if (!listOfMaps.isEmpty()) {
			Map<String, Map<String, Object>> xmlTagWithDetailsMap = (Map<String, Map<String, Object>>) listOfMaps
					.get(0);
			if (xmlTagWithDetailsMap.containsKey("add-provider-accounts1")) {
				Map<String, Object> requestMapDetails = xmlTagWithDetailsMap.get("add-provider-accounts1");
				System.out.println("dataList.dataset value is==" + requestMapDetails.get("dataset"));
				if (requestMapDetails.get("dataset") != null
						&& !requestMapDetails.get("dataset").toString().isEmpty()) {
					RequestSequence requestSequence = RequestSequence.getInstance();
					additionalDataSets = requestSequence
							.getDataSet((List<HierarchicalConfiguration>) requestMapDetails.get("dataset"));
				}
			}
		}
		return additionalDataSets;
	}


}
