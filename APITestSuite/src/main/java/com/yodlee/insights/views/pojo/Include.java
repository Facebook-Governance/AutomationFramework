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
package com.yodlee.insights.views.pojo;
/**
 * Pojo class 
 * 
 * @author kemparaja
 *
 */

import java.util.List;

public class Include {

	private List<String> merchantName;
	private List<Integer> categoryId;
	private List<Integer> accountId;
	private List<Integer> transactionId;
	private List<String> city;
	private List<String> state;
	private AmountRange amountRange;
	private List<String> merchantType;
	
	public List<String> getMerchantName() {
		return merchantName;
	}
	public void setMerchantName(List<String> merchantName) {
		this.merchantName = merchantName;
	}
	public List<Integer> getCategoryId() {
		return categoryId;
	}
	public void setCategoryId(List<Integer> categoryId) {
		this.categoryId = categoryId;
	}
	public List<Integer> getAccountId() {
		return accountId;
	}
	public void setAccountId(List<Integer> accountId) {
		this.accountId = accountId;
	}
	public List<Integer> getTransactionId() {
		return transactionId;
	}
	public void setTransactionId(List<Integer> transactionId) {
		this.transactionId = transactionId;
	}
	public AmountRange getAmountRange() {
		return amountRange;
	}
	public void setAmountRange(AmountRange amountRange) {
		this.amountRange = amountRange;
	}
	public List<String> getMerchantType() {
		return merchantType;
	}
	public void setMerchantType(List<String> merchantType) {
		this.merchantType = merchantType;
	}
	public List<String> getCity() {
		return city;
	}
	public void setCity(List<String> city) {
		this.city = city;
	}
	public List<String> getState() {
		return state;
	}
	public void setState(List<String> state) {
		this.state = state;
	}
	
	
}
