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
 * Helper class for board suite
 * 
 * @author Mahadev A N
 *
 */

import java.nio.charset.StandardCharsets;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.Security;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.jose4j.jws.AlgorithmIdentifiers;
import org.jose4j.jws.JsonWebSignature;
import org.jose4j.jwt.JwtClaims;
import org.jose4j.jwt.NumericDate;
import org.jose4j.lang.JoseException;
import org.json.JSONArray;
import org.json.JSONObject;

import com.jcabi.log.Logger;

public class BoardHelper {

	public  String createBoardJsonBody(String name,String description,
			String type_rule,double amount,String currency,String budgetType,String ruleName,
			String fromDate,String toDate,double fromAmount,double toAmount,String merchantType) 
	{
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

		ruleObj.put("fromDate",getOffsetDate(fromDate));
		ruleObj.put("toDate",getOffsetDate(toDate));
		ruleObj.put("type", type_rule);

		JSONObject elementObject = new JSONObject();

		//fromAmount,toAmount
		JSONObject fromToAmount = new JSONObject();
		fromToAmount.put("fromAmount", fromAmount);
		fromToAmount.put("toAmount", toAmount);
		fromToAmount.put("currency", currency);

		JSONArray merchantTypeArray = new JSONArray();
		merchantTypeArray.put(merchantType);
		if(!merchantType.contains("EMPTY")){
			elementObject.put("merchantType",merchantTypeArray);
		}
		elementObject.put("amountRange", fromToAmount);

		ruleObj.put("include",elementObject);
		ruleArray.put(ruleObj);

		nameObj.put("rule", ruleArray);
		nameArray.put(nameObj);
		mainBoardObj.put("view", nameArray);
		String createBoardJson = mainBoardObj.toString();
		System.out.println(createBoardJson);
		return createBoardJson;
	}

	public  String createViewJsonBody(String name,String description,
			String rule_type,double amount,String currency,String budgetType,String ruleName,
			String fromDate,String toDate,double fromAmount,double toAmount,String merchantType,String merchantName,String[] itemAccountId,String categoryId,String[] transactionId) 
	{
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
		JSONObject elementObject = new JSONObject();

		JSONArray ruleArray = new JSONArray();
		ruleObj.put("name", ruleName);
		ruleObj.put("type", rule_type);

		if(!(transactionId.length!=0))
		{
			ruleObj.put("fromDate",getOffsetDate(fromDate));
			ruleObj.put("toDate",getOffsetDate(toDate));

			if (fromAmount != 0.0 && toAmount !=0.0)
			{
				JSONObject fromToAmount = new JSONObject();
				fromToAmount.put("fromAmount", fromAmount);
				fromToAmount.put("toAmount", toAmount);
				fromToAmount.put("currency", currency);
				elementObject.put("amountRange", fromToAmount);
			}

			if(!merchantType.isEmpty())
			{
				JSONArray merchantTypeArray = new JSONArray();
				String[] inputmerchantTypeArray = merchantType.split(",");

				for(int i=0;i<inputmerchantTypeArray.length;i++)
					merchantTypeArray.put(inputmerchantTypeArray[i]);
				elementObject.put("merchantType", merchantTypeArray);
			}

			if(!merchantName.isEmpty())
			{
				JSONArray merchantNameArray = new JSONArray();
				String[] inputmerchantNameArray = merchantName.split(",");

				for(int i=0;i<inputmerchantNameArray.length;i++)
					merchantNameArray.put(inputmerchantNameArray[i]);
				elementObject.put("merchantName", merchantNameArray);
			}

			if(itemAccountId.length!=0) {
				Integer[] inputitemAccountIdArray = new Integer[itemAccountId.length];
				for(int i=0;i<itemAccountId.length;i++)
				{
					try
					{
						inputitemAccountIdArray[i] = Integer.parseInt(itemAccountId[i].trim());
					}catch(NumberFormatException e)
					{
						Logger.info("Create View", "No Item Account ID patched during create view");
						System.out.println("No Item Account ID patched during create view");
					}
				}
				JSONArray itemAccountIdArray = new JSONArray();
				for(int i=0;i<inputitemAccountIdArray.length;i++)
					itemAccountIdArray.put(inputitemAccountIdArray[i]);
				elementObject.put("acountId", itemAccountIdArray);
			}

			if(!categoryId.isEmpty()) {
				String[] categoryIdStringArray = categoryId.split(",");
				Integer[] inputCategoryIdArray = new Integer[categoryIdStringArray.length];
				for(int i=0;i<categoryIdStringArray.length;i++)
				{
					try
					{
						inputCategoryIdArray[i] = Integer.parseInt(categoryIdStringArray[i].trim());
					}catch(NumberFormatException e)
					{
						Logger.info("Create View", "No Category ID found during create view");
						System.out.println("No Category ID found during create view");
					}
				}
				JSONArray categoryIdJSONArray = new JSONArray();
				for(int i=0;i<inputCategoryIdArray.length;i++)
					categoryIdJSONArray.put(inputCategoryIdArray[i]);
				elementObject.put("categoryId", categoryIdJSONArray);
			}
		}
		else
		{
			Integer[] inputtransactionIdArray = new Integer[transactionId.length];
			for(int i=0;i<transactionId.length;i++)
			{
				try
				{
					inputtransactionIdArray[i] = Integer.parseInt(transactionId[i].trim());
				}catch(NumberFormatException e)
				{
					Logger.info("Create View", "No Transaction ID found for this Account");
					System.out.println("No Transaction ID found for this Account");
				}
			}
			JSONArray transactionIdArray = new JSONArray();
			for(int i=0;i<inputtransactionIdArray.length;i++)
				transactionIdArray.put(inputtransactionIdArray[i]);
			elementObject.put("transactionId", transactionIdArray);
		}

		ruleObj.put("include",elementObject);
		ruleArray.put(ruleObj);

		nameObj.put("rule", ruleArray);
		nameArray.put(nameObj);
		mainBoardObj.put("view", nameArray);
		String createBoardJson = mainBoardObj.toString();
		System.out.println(createBoardJson);
		return createBoardJson;
	}

	public  String addRuleName(String TestCaseId,String TestCase,String name,String description,
			String type_rule,String type,String amount,String currency,String budgetType,String ruleName,String fromDate,String toDate,
			String merchantName,Long categoryId,String merchantTypeBiller,String merchantTypeSub,Long transactionId,String accountId) 
	{
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
		if(!ruleName.contains("EMPTY")){
			ruleObj.put("name", ruleName);
		}
		ruleObj.put("fromDate", getOffsetDate(fromDate));
		ruleObj.put("toDate",getOffsetDate(toDate));
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
		if(!merchantTypeSub.contains("EMPTY")){
			merchantTypeArray.put(merchantTypeSub);
		}
		elementObject.put("merchantName",merchantArray);
		elementObject.put("categoryId",categoryArray);
		elementObject.put("accountId",accountArray);
		elementObject.put("merchantType",merchantTypeArray);
		ruleObj.put("include",elementObject);

		ruleArray.put(ruleObj);

		/*	JSONObject elementObj = new JSONObject();
		JSONObject ruleTest = new JSONObject();
		ruleTest.put("type", type);
		JSONArray transArray = new JSONArray();
		transArray.put(transactionId);
		elementObj.put("transactionId",transArray);
		ruleTest.put("include",elementObj);
		ruleArray.put(ruleTest);*/
		nameObj.put("rule", ruleArray);
		nameArray.put(nameObj);

		mainBoardObj.put("board", nameArray);
		String createBoardJson = mainBoardObj.toString();
		System.out.println(createBoardJson);
		return createBoardJson;
	}

	public  String addMerchantType(String TestCaseId,String TestCase,String name,String description,
			String type_rule,String type,String amount,String currency,String budgetType,String ruleName,String fromDate,String toDate,
			String merchantName,Long categoryId,String merchantTypeBiller,String merchantTypeSub,Long transactionId,String accountId) 
	{
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
		if(!ruleName.contains("EMPTY")){
			ruleObj.put("name", ruleName);
		}
		ruleObj.put("fromDate", getOffsetDate(fromDate));
		ruleObj.put("toDate",getOffsetDate(toDate));
		ruleObj.put("type", type_rule);

		JSONObject elementObject = new JSONObject();
		JSONArray merchantArray = new JSONArray();
		merchantArray.put(merchantName);
		JSONArray categoryArray = new JSONArray();
		categoryArray.put(categoryId);
		JSONArray accountArray = new JSONArray();
		accountArray.put(accountId);
		JSONArray merchantTypeArray = new JSONArray();
		if(!merchantTypeBiller.contains("EMPTY")){
			merchantTypeArray.put(merchantTypeBiller);
		}
		if(!merchantTypeSub.contains("EMPTY")){
			merchantTypeArray.put(merchantTypeSub);
		}
		if(!merchantName.contains("EMPTY")){
			elementObject.put("merchantName",merchantArray);
		}
		if(!(name.contains("MERCHANTTYPE_TRANSACTION_ID") || (name.contains("ONLY_MERCHANT_TYPE")))){
			elementObject.put("categoryId",categoryArray);
		}
		if(!(name.contains("MERCHANTTYPE_TRANSACTION_ID") || (name.contains("ONLY_MERCHANT_TYPE")))){
			elementObject.put("accountId",accountArray);
		}

		elementObject.put("merchantType",merchantTypeArray);
		ruleObj.put("include",elementObject);

		if(name.contains("MERCHANTTYPE_TRANSACTION_ID")){
			JSONArray transArray = new JSONArray();
			transArray.put(transactionId);
			elementObject.put("transactionId",transArray);
			ruleObj.put("include",elementObject);
			ruleArray.put(ruleObj);
		}else{
			ruleArray.put(ruleObj);
		}
		nameObj.put("rule", ruleArray);
		nameArray.put(nameObj);
		mainBoardObj.put("board", nameArray);
		String createBoardJson = mainBoardObj.toString();
		System.out.println(createBoardJson);
		return createBoardJson;
	}

	public  String addCityStateAmount(String TestCaseId,String TestCase,String name,String description,
			String type_rule,String type,String amount,String currency,String budgetType,String ruleName,
			String fromDate,String toDate,String merchantName,Long categoryId,String merchantTypeBiller,
			String merchantTypeSub,Long transactionId,String accountId,String city,String state,String fromAmount,String toAmount) 
	{
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
		if(!ruleName.contains("EMPTY")){
			ruleObj.put("name", ruleName);
		}
		ruleObj.put("fromDate",getOffsetDate(fromDate));
		ruleObj.put("toDate",getOffsetDate(toDate));
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
		elementObject.put("merchantName",merchantArray);
		elementObject.put("categoryId",categoryArray);
		elementObject.put("accountId",accountArray);
		elementObject.put("merchantType",merchantTypeArray);

		//city,state
		JSONArray cityArray = new JSONArray();
		cityArray.put(city);
		elementObject.put("city",cityArray);
		JSONArray stateArray = new JSONArray();
		stateArray.put(state);
		elementObject.put("state",stateArray);

		//fromAmount,toAmount
		JSONObject fromToAmount = new JSONObject();
		System.out.println("fromAmount::"+fromAmount);
		System.out.println("toAmount::"+toAmount);

		fromToAmount.put("fromAmount", fromAmount);
		fromToAmount.put("toAmount", toAmount);
		fromToAmount.put("currency", currency);
		elementObject.put("amountRange", fromToAmount);

		ruleObj.put("include",elementObject);

		if(name.contains("MERCHANTTYPE_TRANSACTION_ID")){
			JSONArray transArray = new JSONArray();
			transArray.put(transactionId);
			elementObject.put("transactionId",transArray);
			ruleObj.put("include",elementObject);
			ruleArray.put(ruleObj);
		}else{
			ruleArray.put(ruleObj);
		}
		nameObj.put("rule", ruleArray);
		nameArray.put(nameObj);
		mainBoardObj.put("board", nameArray);
		String createBoardJson = mainBoardObj.toString();
		System.out.println(createBoardJson);
		return createBoardJson;
	}

	public String createBoardRequestBody(String name,String description,String type_rule,String type,
			String amount,String currency,String budgetType,String fromDate,
			String toDate,String merchantName,Long categoryId,Long categoryId1,Long categoryId2,Long accountId,Long transactionId,Long transactionId1) 
	{
		JSONArray nameArray = new JSONArray();
		JSONObject mainBoardObj = new JSONObject();
		JSONObject nameObj = new JSONObject();
		JSONObject budgetObj = new JSONObject();
		if(name.length()>0){
			nameObj.put("name", name);
		}
		if(description.length()>0){
			nameObj.put("description", description);
		}
		budgetObj.put("amount", amount);
		budgetObj.put("currency", currency);
		budgetObj.put("budgetType", budgetType);
		nameObj.put("budget", budgetObj);

		JSONObject ruleObj = new JSONObject();
		JSONArray ruleArray = new JSONArray();
		if(fromDate.length()>0 && toDate.length()>0){
			ruleObj.put("fromDate", getOffsetDate(fromDate));
			ruleObj.put("toDate",getOffsetDate(toDate));
		}
		if(type_rule.length()>0){
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
		elementObject.put("merchantName",merchantArray);
		elementObject.put("categoryId",categoryArray);
		elementObject.put("accountId",accountArray);

		if(name.contains("TRANSID_INRULE")){
			JSONArray transArrayRule = new JSONArray();
			transArrayRule.put(transactionId);
			transArrayRule.put(transactionId1);
			elementObject.put("transactionId",transArrayRule);
		}
		if(!name.contains("ONLY_TRANSACTIONID")){
			ruleObj.put("include",elementObject);
		}
		ruleArray.put(ruleObj);

		JSONObject elementObj = new JSONObject();
		JSONObject ruleTest = new JSONObject();
		ruleTest.put("type", type);
		JSONArray transArray = new JSONArray();
		transArray.put(transactionId);
		transArray.put(transactionId1);
		elementObj.put("transactionId",transArray);
		ruleTest.put("include",elementObj);
		ruleArray.put(ruleTest);
		if(merchantName.length()>0){
			nameObj.put("rule", ruleArray);
		}
		nameArray.put(nameObj);

		mainBoardObj.put("board", nameArray);
		String createBoardJson = mainBoardObj.toString();
		System.out.println(createBoardJson);
		return createBoardJson;
	}

	public  String updateBoardRequestBody(String name,String description,String type_rule,String type,
			String amount,String currency,String budgetType,String fromDate,
			String toDate,String merchantName,Long categoryId,Long accountId,Long transactionId) 
	{
		JSONArray nameArray = new JSONArray();
		JSONObject mainBoardObj = new JSONObject();
		JSONObject nameObj = new JSONObject();
		JSONObject budgetObj = new JSONObject();
		if(name.length()>0){
			nameObj.put("name", name);
		}

		if(description.contains("DESC_LESS_THANONE")){
			description= description.replace("DESC_LESS_THANONE", "");
			nameObj.put("description", description);
		}else if(description.length()>0){
			nameObj.put("description", description);
		}

		if(amount.contains("WITHOUT_AMOUNT")){
			amount= amount.replace("WITHOUT_AMOUNT", "");
		} else{
			budgetObj.put("amount", amount);
		}

		if(currency.contains("EMPTY_CURRENCY")){
			currency= currency.replace("EMPTY_CURRENCY", "");
			budgetObj.put("currency", currency);
		}else if(currency.contains("WITHOUT_CURRENCY")){
			currency= currency.replace("WITHOUT_CURRENCY", "");
		}
		else{
			budgetObj.put("currency", currency);
		}
		if(budgetType.length()>0){
			budgetObj.put("budgetType", budgetType);
		}
		nameObj.put("budget", budgetObj);

		JSONObject ruleObj = new JSONObject();
		JSONArray ruleArray = new JSONArray();
		if(name.contains("DIFF_DATEFORMAT")){
			ruleObj.put("fromDate", diffDateFormat(fromDate));
			ruleObj.put("toDate",diffDateFormat(toDate));
		}else if(fromDate.length()>0 && toDate.length()>0){
			ruleObj.put("fromDate", getOffsetDate(fromDate)+"T02:15:11");
			ruleObj.put("toDate",getOffsetDate(toDate)+"T02:15:11");
		}

		if(type_rule.length()>0){
			ruleObj.put("type", type_rule);
		}

		JSONObject elementObject = new JSONObject();
		JSONArray merchantArray = new JSONArray();

		if(name.contains("DUPLICATE_MERCHANT_NAME")){
			String dupMerchant[] = merchantName.split(",");
			String merchantName1=dupMerchant[0];
			String merchantName2=dupMerchant[1];
			merchantArray.put(merchantName1);
			merchantArray.put(merchantName2);
		}else{
			merchantArray.put(merchantName);
		}
		JSONArray categoryArray = new JSONArray();
		categoryArray.put(categoryId);
		JSONArray accountArray = new JSONArray();
		accountArray.put(accountId);

		if(!name.contains("accountId/categoryId/merchantName")){
			elementObject.put("merchantName",merchantArray);
		}
		elementObject.put("categoryId",categoryArray);
		elementObject.put("accountId",accountArray);

		if(name.contains("TRANSID_INRULE")){
			JSONArray transArrayRule = new JSONArray();
			transArrayRule.put(transactionId);
			elementObject.put("transactionId",transArrayRule);
		}
		ruleObj.put("include",elementObject);
		ruleArray.put(ruleObj);

		JSONObject elementObj = new JSONObject();
		JSONObject ruleTest = new JSONObject();
		ruleTest.put("type", type);
		JSONArray transArray = new JSONArray();
		transArray.put(transactionId);
		elementObj.put("transactionId",transArray);
		ruleTest.put("include",elementObj);
		ruleArray.put(ruleTest);
		if(merchantName.length()>0){
			nameObj.put("rule", ruleArray);
		}
		nameArray.put(nameObj);

		mainBoardObj.put("board", nameArray);
		String createBoardJson = mainBoardObj.toString();
		System.out.println(createBoardJson);
		return createBoardJson;
	}

	public  String createBoardJsonBodyForBoardType(String name,String description,String boardType,String type_rule,String type,
			String amount,String currency,String budgetType,String fromDate,
			String toDate,String merchantName,String categoryId,String accountId,String transactionId) 
	{
		JSONArray nameArray = new JSONArray();
		JSONObject mainBoardObj = new JSONObject();
		JSONObject nameObj = new JSONObject();
		JSONObject budgetObj = new JSONObject();
		nameObj.put("name", name);
		nameObj.put("BoardType", boardType);
		nameObj.put("description", description);
		if(!amount.contains(" ")){
			budgetObj.put("amount", amount);
		}
		if(!currency.contains(" ")){
			budgetObj.put("currency", currency);
		}
		if(!budgetType.contains(" ")){
			budgetObj.put("budgetType", budgetType);
		}
		nameObj.put("budget", budgetObj);

		JSONObject ruleObj = new JSONObject();
		JSONArray ruleArray = new JSONArray();
		ruleObj.put("fromDate", getOffsetDate(fromDate));
		ruleObj.put("toDate",getOffsetDate(toDate));
		ruleObj.put("type", type_rule);

		JSONObject elementObject = new JSONObject();
		JSONArray merchantArray = new JSONArray();
		merchantArray.put(merchantName);
		JSONArray categoryArray = new JSONArray();
		categoryArray.put(categoryId);
		JSONArray accountArray = new JSONArray();
		accountArray.put(accountId);
		elementObject.put("merchantName",merchantArray);
		elementObject.put("categoryId",categoryArray);
		elementObject.put("accountId",accountArray);
		ruleObj.put("include",elementObject);
		ruleArray.put(ruleObj);

		JSONObject elementObj = new JSONObject();
		JSONObject ruleTest = new JSONObject();
		ruleTest.put("type", type);
		JSONArray transArray = new JSONArray();
		transArray.put(transactionId);
		elementObj.put("transactionId",transArray);
		ruleTest.put("include",elementObj);
		ruleArray.put(ruleTest);
		nameObj.put("rule", ruleArray);
		nameArray.put(nameObj);

		mainBoardObj.put("board", nameArray);
		String createBoardTypeJson = mainBoardObj.toString();
		System.out.println(createBoardTypeJson);
		return createBoardTypeJson;
	}

	public  String updateBoardJsonBody(String name,String description,String boardType,String ruleId,String type_rule,String type,
			String amount,String currency,String budgetType,String fromDate,
			String toDate,String merchantName,String categoryId,String accountId,String transactionId) 
	{
		JSONArray nameArray = new JSONArray();
		JSONObject mainBoardObj = new JSONObject();
		JSONObject nameObj = new JSONObject();
		JSONObject budgetObj = new JSONObject();
		nameObj.put("name", name);
		nameObj.put("BoardType", boardType);
		nameObj.put("description", description);
		if(!amount.contains(" ")){
			budgetObj.put("amount", amount);
		}
		if(!currency.contains(" ")){
			budgetObj.put("currency", currency);
		}
		if(!budgetType.contains(" ")){
			budgetObj.put("budgetType", budgetType);
		}
		nameObj.put("budget", budgetObj);

		JSONObject ruleObj = new JSONObject();
		JSONArray ruleArray = new JSONArray();
		ruleObj.put("fromDate", getOffsetDate(fromDate));
		ruleObj.put("toDate",getOffsetDate(toDate));
		ruleObj.put("type", type_rule);

		JSONObject elementObject = new JSONObject();
		JSONArray merchantArray = new JSONArray();
		merchantArray.put(merchantName);
		JSONArray categoryArray = new JSONArray();
		categoryArray.put(categoryId);
		JSONArray accountArray = new JSONArray();
		accountArray.put(accountId);
		elementObject.put("merchantName",merchantArray);
		elementObject.put("categoryId",categoryArray);
		elementObject.put("accountId",accountArray);
		elementObject.put("ruleId",ruleId);

		ruleObj.put("include",elementObject);
		ruleArray.put(ruleObj);

		JSONObject elementObj = new JSONObject();
		JSONObject ruleTest = new JSONObject();
		ruleTest.put("type", type);
		JSONArray transArray = new JSONArray();
		transArray.put(transactionId);
		elementObj.put("transactionId",transArray);
		ruleTest.put("include",elementObj);
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
		System.out.println("dataValuedataValue::"+dataValue);
		return dataValue;
	}
}

