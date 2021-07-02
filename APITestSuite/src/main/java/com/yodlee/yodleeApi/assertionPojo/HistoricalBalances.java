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

package com.yodlee.yodleeApi.assertionPojo;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonProperty;

public class HistoricalBalances {

	@JsonProperty("date")
	private Date date ;
	
	@JsonProperty("asOfDate")
	private Date asOfDate ;
	
	@JsonProperty("balance")
	private Money balance ;
	
	@JsonProperty("isAsset")
	private Boolean isAsset ;
	
	@JsonProperty("dataSourceType")
	private String dataSourceType ;
	
	@JsonProperty("date")
	public Date getDate() {
		return date;
	}

	@JsonProperty("date")
	public void setDate(Date date) {
		this.date = date;
	}

	@JsonProperty("asOfDate")
	public Date getAsOfDate() {
		return asOfDate;
	}

	@JsonProperty("asOfDate")
	public void setAsOfDate(Date asOfDate) {
		this.asOfDate = asOfDate;
	}

	@JsonProperty("balance")
	public Money getBalance() {
		return balance;
	}

	@JsonProperty("balance")
	public void setBalance(Money balance) {
		this.balance = balance;
	}

	@JsonProperty("isAsset")
	public Boolean getIsAsset() {
		return isAsset;
	}

	@JsonProperty("isAsset")
	public void setIsAsset(Boolean isAsset) {
		this.isAsset = isAsset;
	}

	@JsonProperty("dataSourceType")
	public String getDataSourceType() {
		return dataSourceType;
	}

	@JsonProperty("dataSourceType")
	public void setDataSourceType(String dataSourceType) {
		this.dataSourceType = dataSourceType;
	}

	
	
}
