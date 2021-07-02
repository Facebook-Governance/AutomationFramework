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
/**
 * Common class for request object
 * @author  Shashi Kiran (skiran1@yodlee.com)
 * @version 1.0
 * @since   2016-11-20
 */
package com.yodlee.yodleeApi.helper;

import io.restassured.response.Response;
import org.json.JSONException;
import org.json.JSONObject;

import com.yodlee.yodleeApi.common.Configuration;
import com.yodlee.yodleeApi.utils.commonUtils.CommonUtils;

public class UpdateTransactionsRequest {
	
	private Long accountId;
	private String memo;
	private Double amount;
	private String currency;
	private String baseType;
	private String date;
	private String categorySource;
	private long categoryId;
	private String consumer;
	private String checkNumber;
	private String cobSession;
	private String userSession;
	private String transactionId;
	private String reconStatus;
	public String getCobSession() {
		return cobSession;
	}
	public void setCobSession(String cobSession) {
		this.cobSession = cobSession;
	}
	public String getUserSession() {
		return userSession;
	}
	public void setUserSession(String userSession) {
		this.userSession = userSession;
	}
	public String getReconStatus() {
		return reconStatus;
	}
	public void setReconStatus(String reconStatus) {
		this.reconStatus = reconStatus;
	}
	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}

	private String sourceType;
	private String transactionType;
	
	public String getTransactionType() {
		return transactionType;
	}
	public void setTransactionType(String transactionType) {
		this.transactionType = transactionType;
	}
	public String getSourceType() {
		return sourceType;
	}
	public void setSourceType (String sourceType) {
		this.sourceType = sourceType;
	}
	public Long getAccountId() {
		return accountId;
	}
	public void setAccountId(long accountId) {
		this.accountId = accountId;
	}
	public String getMemo() {
		return memo;
	}
	public void setMemo(String memo) {
		this.memo = memo;
	}
	public Double getAmount() {
		return amount;
	}
	public void setAmount(Double amount) {
		this.amount = amount;
	}
	public String getCurrency() {
		return currency;
	}
	public void setCurrency(String currency) {
		this.currency = currency;
	}
	public String getBaseType() {
		return baseType;
	}
	public void setBaseType(String baseType) {
		this.baseType = baseType;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getCategorySource() {
		return categorySource;
	}
	public void setCategorySource(String categorySource) {
		this.categorySource = categorySource;
	}
	public long getCategoryId() {
		return categoryId;
	}
	public void setCategoryId(long categoryId) {
		this.categoryId = categoryId;
	}
	public String getConsumer() {
		return consumer;
	}
	public void setConsumer(String consumer) {
		this.consumer = consumer;
	}
	public String getCheckNumber() {
		return checkNumber;
	}
	public void setCheckNumber(String checkNumber) {
		this.checkNumber = checkNumber;
	}
	public void setTransactionId(Response response) {
		this.transactionId = response.jsonPath().get("transaction.id").toString();
	}
	public String getTransactionId() {
		return this.transactionId;
	}


	/** Creates a json using the fields of the request object and returns as a string
	 * @return Returns the json string for the POST /transactions request
	 * @throws JSONException
	 */
	public String getJsonString() throws JSONException {
		JSONObject manualTxnObj = new JSONObject();
        JSONObject manualTransactionsObject = new JSONObject();
        JSONObject amountObj = new JSONObject();
        amountObj.put("amount", amount);
        
        amountObj.put("currency", currency);
        JSONObject descriptionObj = new JSONObject();
        if (consumer != null && consumer.length() > 0)
        	descriptionObj.put("consumer", consumer);
        if (baseType != null && baseType.length() > 0)
        	manualTxnObj.put("baseType", baseType);        
        manualTxnObj.put("categoryId", categoryId);
        if (categorySource != null && categorySource.length() > 0)
        	manualTxnObj.put("categorySource", categorySource);
        manualTxnObj.put("date", date);
        if (memo != null && memo.length() > 0)
        	manualTxnObj.put("memo", memo);
        manualTxnObj.put("accountId", accountId);
        if (checkNumber != null && checkNumber.length() > 0)
        manualTxnObj.put("checkNumber", checkNumber);        
        manualTxnObj.put("description", descriptionObj);
        manualTxnObj.put("amount", amountObj);
        if (reconStatus != null && reconStatus.length() > 0)
        	manualTxnObj.put("reconStatus", reconStatus);
        if (sourceType != null && sourceType.length() > 0)
        	manualTxnObj.put("sourceType", sourceType);        
        if (transactionType != null && transactionType.length() > 0)
        	manualTxnObj.put("transactionType", transactionType);
        manualTransactionsObject.put("transaction", manualTxnObj);
        String manualTxnObjectJSON = manualTransactionsObject.toString();
        System.out.println("manualTxnObjectJSON is : " + manualTxnObjectJSON);
        return manualTxnObjectJSON;
	}
	

	
	public String createRequestObject(String container, String categoryId,
			String categorySource, String consumer, String memo, String amount, String baseType,
			String reconStatus, String checkNumber, String date, String sourceType, String transactionType, String currency) throws Exception {
		CommonUtils commonUtils= new CommonUtils();
		TransactionsHelper transactionsHelper = new TransactionsHelper();
		this.amount = Double.parseDouble(amount);
		this.baseType = baseType;
		this.memo = memo;
		this.consumer = consumer;
		this.reconStatus = reconStatus;
		this.checkNumber = checkNumber;
		this.date = commonUtils.getOffsetDate(date);
		this.categorySource = categorySource;
		this.sourceType = sourceType;
		this.transactionType = transactionType;
		this.currency = currency;
		if (categorySource.equalsIgnoreCase("USER")) {
			this.categoryId = transactionsHelper.createAndGetUserCategory( Configuration.getInstance().getCobrandSessionObj(),"Custom category", "29");
		} else {
			if (categoryId != null && categoryId.length() > 0)
				this.categoryId = Long.parseLong(categoryId);
		}	
		return getJsonString();		
	}
}
