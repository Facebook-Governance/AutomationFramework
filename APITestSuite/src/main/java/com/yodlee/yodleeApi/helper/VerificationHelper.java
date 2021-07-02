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

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import com.yodlee.DBHelper;
import com.yodlee.yodleeApi.common.Configuration;
import com.yodlee.yodleeApi.common.Initializer;
import com.yodlee.yodleeApi.database.DbQueries;
import com.yodlee.yodleeApi.interfaces.Session;
import com.yodlee.yodleeApi.utils.apiUtils.AccountUtils;

import io.restassured.response.Response;
import service.DBUtility;

public class VerificationHelper {

	/**
	 * Used by @API: PUT /verification to form body params as JSON.
	 * 
	 * @param verificationType
	 * @param accountId
	 * @param providerAccountId
	 * @param firstAmountValue
	 * @param firstCurrency
	 * @param firstBasetype
	 * @param secondAmountValue
	 * @param secondCurrency
	 * @param secondBasetype
	 * @param thirdAmountValue
	 * @param thirdCurrency
	 * @param thirdBasetype
	 * @param sessionObj
	 * @return
	 * @throws NumberFormatException
	 * @throws SQLException
	 */
	public JSONObject createJsonForCdvVerificationCompletion(String verificationType, String accountId,
			String providerAccountId, String firstAmountValue, String firstCurrency, String firstBasetype,
			String secondAmountValue, String secondCurrency, String secondBasetype, String thirdAmountValue,
			String thirdCurrency, String thirdBasetype, Session sessionObj) throws NumberFormatException, SQLException {

		AccountUtils accountUtils = new AccountUtils();
		DbQueries dbQueries = new DbQueries();

		if (accountId.equalsIgnoreCase("null")) {

			Initializer.switchToDiffy();
			Response response = accountUtils.getAllAccounts(sessionObj);

			Initializer.switchToBase();
			accountId = response.jsonPath().getString("account[0].id");
		}
		if (providerAccountId.equalsIgnoreCase("null")) {

			Initializer.switchToDiffy();

			Response response = accountUtils.getAllAccounts(sessionObj);

			response.then().log().all();
			Initializer.switchToBase();
			providerAccountId = response.jsonPath().getString("account[0].providerAccountId");
		}

		List<Double> item = null;
		DBHelper dbHelper = new DBHelper();
		if (firstAmountValue.equalsIgnoreCase("db")) {
			item = dbHelper.getAmountFromDB(Long.parseLong(accountId));
			firstAmountValue = String.valueOf(item.get(0));
		}
		if (firstAmountValue.equalsIgnoreCase("db")) {
		}
		if (secondAmountValue.equalsIgnoreCase("db")) {
			secondAmountValue = String.valueOf(item.get(1));
		}

		if (thirdAmountValue.equalsIgnoreCase("db")) {
			thirdAmountValue = String.valueOf(item.get(2));
		}

		JSONObject verifications = new JSONObject();
		JSONObject verification = new JSONObject();
		JSONObject firstAmount = new JSONObject();
		JSONObject secondAmount = new JSONObject();
		JSONObject thirdAmount = new JSONObject();
		JSONArray verificationInputArray = new JSONArray();

		JSONObject firstTransactionRow = new JSONObject();

		firstTransactionRow.put("amount", Double.parseDouble(firstAmountValue));
		firstTransactionRow.put("currency", firstCurrency);
		firstAmount.put("amount", firstTransactionRow);
		firstAmount.put("baseType", firstBasetype);
		verificationInputArray.put(firstAmount);

		JSONObject secondTransactionRow = new JSONObject();
		secondTransactionRow.put("amount", Double.parseDouble(secondAmountValue));
		secondTransactionRow.put("currency", secondCurrency);
		secondAmount.put("amount", secondTransactionRow);
		secondAmount.put("baseType", secondBasetype);
		verificationInputArray.put(secondAmount);

		JSONObject thirdTransactionRow = new JSONObject();
		thirdTransactionRow.put("amount", Double.parseDouble(thirdAmountValue));
		thirdTransactionRow.put("currency", thirdCurrency);
		thirdAmount.put("amount", thirdTransactionRow);
		thirdAmount.put("baseType", thirdBasetype);
		verificationInputArray.put(thirdAmount);

		verification.put("accountId", Long.parseLong(accountId));
		verification.put("providerAccountId", Long.parseLong(providerAccountId));
		verification.put("verificationType", verificationType);
		verification.put("transaction", verificationInputArray);

		verifications.put("verification", verification);

		return verifications;
	}

	/**
	 * Used by all verification API's for construction of query params.
	 * 
	 * @param accountId
	 * @param providerAccountId
	 * @param verificationType
	 * @return
	 * @throws Exception
	 */
	public LinkedHashMap<String, String> createQueryParamsForAllVerification(String accountId, String providerAccountId,
			String verificationType) throws Exception {

		Initializer.switchToDiffy();
		LinkedHashMap<String, String> getVerificationQueryParams = new LinkedHashMap<String, String>();
		getVerificationQueryParams.put("accountId", accountId);
		getVerificationQueryParams.put("providerAccountId", providerAccountId);
		getVerificationQueryParams.put("verificationType", verificationType);

		return getVerificationQueryParams;
	}

	/**
	 * Used by @API: POST /verification (MS_Verification) to create body params.
	 * 
	 * @param accountId
	 * @param providerAccountId
	 * @param verificationType
	 * @return
	 */
	public JSONObject createBodyParamsForMSVerification(String accountId, String providerAccountId,
			String verificationType) {
		JSONObject obj = new JSONObject();
		JSONObject verification = new JSONObject();
		if (accountId != null) {
			verification.put("accountId", Long.parseLong(accountId));
		} else if (providerAccountId != null) {
			verification.put("providerAccountId", Long.parseLong(providerAccountId));
		}
		verification.put("verificationType", verificationType);
		obj.put("verification", verification);

		return obj;
	}

}
