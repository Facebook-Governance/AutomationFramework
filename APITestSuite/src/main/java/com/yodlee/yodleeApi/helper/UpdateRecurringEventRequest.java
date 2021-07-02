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

import io.restassured.response.Response;
import org.json.JSONException;
import org.json.JSONObject;

public class UpdateRecurringEventRequest {
	
	private Long accountId;
	private String memo;
	private Double amount;
	private String currency;
	private String baseType;
	private String startDate;
	private String categorySource;
	private long categoryId;
	private String consumer;
	private String checkNumber;
	private String frequency;
	private String endDate;
	private String recurringEventId;
	private Long refTransactionId;
	private String transactionType;
	private String userApprovalStatus;
	private String recurringEventStatus;
	
	public String getTransactionType() {
		return transactionType;
	}
	public void setTransactionType(String transactionType) {
		this.transactionType = transactionType;
	}
	public Long getRefTransactionId() {
		return refTransactionId;
	}
	public void setRefTransactionId(Long refTransactionId) {
		this.refTransactionId = refTransactionId;
	}
	public String getStartDate() {
		return startDate;
	}
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}
	public String getFrequency() {
		return frequency;
	}
	public void setFrequency(String frequency) {
		this.frequency = frequency;
	}
	public String getEndDate() {
		return endDate;
	}
	public void setEndDate(String endDate) {
		this.endDate = endDate;
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
	public void setRecurringEventId(Response response) {
		this.recurringEventId = response.jsonPath().get("account.recurringEventId").toString();
	}
	public void setRecurringEventId(String recurringEventId) {
		this.recurringEventId = recurringEventId;
	}
	public String getRecurringEventId() {
		return recurringEventId;
	}
	public String getJsonString() throws JSONException {
		JSONObject recurringEventObj = new JSONObject();
        JSONObject accountObject = new JSONObject();
        JSONObject amountObj = new JSONObject();
        accountObject.put("id", accountId);
        recurringEventObj.put("refTransactionId", refTransactionId);
        amountObj.put("amount", amount);
        amountObj.put("currency", currency);
        JSONObject descriptionObj = new JSONObject();
        descriptionObj.put("consumer", consumer);
        recurringEventObj.put("baseType", baseType);
        recurringEventObj.put("categoryId", categoryId);
        recurringEventObj.put("categorySource", categorySource);
        recurringEventObj.put("frequency", frequency);
        recurringEventObj.put("endDate", endDate);
        recurringEventObj.put("endDate", endDate);
        recurringEventObj.put("startDate", startDate);
        recurringEventObj.put("memo", memo);
        
        recurringEventObj.put("checkNumber", checkNumber);
        recurringEventObj.put("description", descriptionObj);
        recurringEventObj.put("amount", amountObj);
        if (transactionType != null && transactionType.length() > 0)
        	recurringEventObj.put("transactionType", transactionType);
        if (recurringEventStatus != null)
        	recurringEventObj.put("recurringEventStatus", recurringEventStatus);
        if (userApprovalStatus != null)
        	recurringEventObj.put("userApprovalStatus", userApprovalStatus);
        accountObject.put("recurringEvent", recurringEventObj);
        JSONObject requestObj = new JSONObject();
        requestObj.put("account", accountObject);
        String manualTxnObjectJSON = requestObj.toString();
        
        System.out.println("manualTxnObjectJSON is : " + manualTxnObjectJSON);
        return manualTxnObjectJSON;
	}

	public String getUserApprovalStatus() {
		return userApprovalStatus;
	}
	public void setUserApprovalStatus(String userApprovalStatus) {
		this.userApprovalStatus = userApprovalStatus;
	}
	public String getRecurringEventStatus() {
		return recurringEventStatus;
	}
	public void setRecurringEventStatus(String recurringEventStatus) {
		this.recurringEventStatus = recurringEventStatus;
	}
}
