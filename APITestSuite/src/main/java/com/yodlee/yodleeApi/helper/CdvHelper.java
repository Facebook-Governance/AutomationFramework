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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class CdvHelper {

	
	/**
	 * { "providerAccount": { "account": [{ "accountNumber": 232432,
	 * "accountName": "XYZ Bank", (mandatory) "accountType": "SAVINGS",
	 * "bankTransferCode": [{ "id": "123456789", "type": "ROUTING_NUMBER" }],
	 * "verification": { "type": "CHALLENGE_DEPOSIT" } }] } }
	 */
	public static String createJsonForCDVInitiation(String accountNumber,
			String accountName, String accountType, String bankTransferId,
			String bankTransferType, String verificationType)
			throws JSONException {

		JSONObject providerAccountsObject = new JSONObject();
		JSONObject providerAccounts = new JSONObject();

		JSONArray accountArray = new JSONArray();
		JSONObject account = new JSONObject();
		account.put("accountNumber", accountNumber);
		account.put("accountName", accountName);
		account.put("accountType", accountType);

		JSONArray bankTransferCodeArray = new JSONArray();
		JSONObject bankTransferCode = new JSONObject();
		bankTransferCode.put("id", bankTransferId);
		bankTransferCode.put("type", bankTransferType);
		bankTransferCodeArray.put(bankTransferCode);

		account.put("bankTransferCode", bankTransferCodeArray);

		JSONObject verification = new JSONObject();
		verification.put("type", verificationType);

		account.put("verification", verification);
		accountArray.put(account);

		providerAccounts.put("account", accountArray);
		providerAccountsObject.put("providerAccount", providerAccounts);
		System.out.println("BODY PARAM FOR CDV INITIATION : \n\n\n"
				+ providerAccountsObject.toString(10));
		return providerAccountsObject.toString();
	}

	public static String createJsonForCDVInitiation(String accountNumberKey,
			String accountNumberValue, String accountNameKey,
			String accountNameValue, String accountTypeKey,
			String accountTypeValue, String bankTransferIdKey,
			String bankTransferIdValue, String bankTransferTypeKey,
			String bankTransferTypeValue, String verificationTypeKey,
			String verificationTypeValue) throws JSONException {

		JSONObject providerAccountsObject = new JSONObject();
		JSONObject providerAccounts = new JSONObject();

		JSONArray accountArray = new JSONArray();
		JSONObject account = new JSONObject();
		account.put(accountNumberKey, accountNumberValue);
		account.put(accountNameKey, accountNameValue);
		account.put(accountTypeKey, accountTypeValue);

		JSONArray bankTransferCodeArray = new JSONArray();
		JSONObject bankTransferCode = new JSONObject();
		bankTransferCode.put(bankTransferIdKey, bankTransferIdValue);
		bankTransferCode.put(bankTransferTypeKey, bankTransferTypeValue);
		bankTransferCodeArray.put(bankTransferCode);

		account.put("bankTransferCode", bankTransferCodeArray);

		JSONObject verification = new JSONObject();
		verification.put(verificationTypeKey, verificationTypeValue);

		account.put("verification", verification);
		accountArray.put(account);

		providerAccounts.put("account", accountArray);
		providerAccountsObject.put("providerAccount", providerAccounts);
		System.out.println("BODY PARAM FOR CDV INITIATION : \n\n\n"
				+ providerAccountsObject.toString(10));
		return providerAccountsObject.toString();
	}

	public static String createJsonForCDVInitiation(String accountNumberKey,
			String accountNumberValue, String accountNameKey,
			String accountNameValue, String accountTypeKey,
			String accountTypeValue, String bankTransferIdKey,
			String bankTransferIdValue, String bankTransferTypeKey,
			String bankTransferTypeValue, String verificationTypeKey,
			String verificationTypeValue, boolean accountNumberFieldEnabled,
			boolean accountNameFieldEnabled, boolean accountTypeFieldEnabled,
			boolean bankTransferIdFieldEnabled,
			boolean bankTransferTypeFieldEnabled,
			boolean VerificationTypeFieldEnabled) throws JSONException {

		JSONObject providerAccountsObject = new JSONObject();
		JSONObject providerAccounts = new JSONObject();

		JSONArray accountArray = new JSONArray();
		JSONObject account = new JSONObject();

		if (accountNumberFieldEnabled == true) {
			account.put(accountNumberKey, accountNumberValue);
		}

		if (accountNameFieldEnabled == true) {
			account.put(accountNameKey, accountNameValue);
		}

		if (accountTypeFieldEnabled == true) {
			account.put(accountTypeKey, accountTypeValue);
		}

		JSONArray bankTransferCodeArray = new JSONArray();
		JSONObject bankTransferCode = new JSONObject();

		if (bankTransferIdFieldEnabled == true) {
			bankTransferCode.put(bankTransferIdKey, bankTransferIdValue);
		}

		if (bankTransferTypeFieldEnabled == true) {
			bankTransferCode.put(bankTransferTypeKey, bankTransferTypeValue);
		}

		bankTransferCodeArray.put(bankTransferCode);

		account.put("bankTransferCode", bankTransferCodeArray);

		JSONObject verification = new JSONObject();

		if (VerificationTypeFieldEnabled == true) {
			verification.put(verificationTypeKey, verificationTypeValue);
		}

		account.put("verification", verification);
		accountArray.put(account);

		providerAccounts.put("account", accountArray);
		providerAccountsObject.put("providerAccount", providerAccounts);
		System.out.println("BODY PARAM FOR CDV INITIATION : \n\n\n"
				+ providerAccountsObject.toString(10));
		return providerAccountsObject.toString();
	}


}
