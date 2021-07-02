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

public class RewardBalance {

	@JsonProperty("description")
	private String description ;
	
	@JsonProperty("balance")
	private String balance  ;
	
	@JsonProperty("units")
	private String units  ;
	
	@JsonProperty("balanceType")
	private String balanceType  ;
	
	@JsonProperty("expiryDate")
	private Date expiryDate ; 
	
	@JsonProperty("balanceToLevel")
	private String balanceToLevel ; 
	
	@JsonProperty("balanceToReward")
	private String balanceToReward ; 
	
	@JsonProperty("description")
	public String getDescription() {
		return description;
	}

	@JsonProperty("description")
	public void setDescription(String description) {
		this.description = description;
	}

	@JsonProperty("balance")
	public String getBalance() {
		return balance;
	}

	@JsonProperty("balance")
	public void setBalance(String balance) {
		this.balance = balance;
	}

	@JsonProperty("units")
	public String getUnits() {
		return units;
	}

	@JsonProperty("units")
	public void setUnits(String units) {
		this.units = units;
	}

	@JsonProperty("balanceType")
	public String getBalanceType() {
		return balanceType;
	}

	@JsonProperty("balanceType")
	public void setBalanceType(String balanceType) {
		this.balanceType = balanceType;
	}

	@JsonProperty("expiryDate")
	public Date getExpiryDate() {
		return expiryDate;
	}

	@JsonProperty("expiryDate")
	public void setExpiryDate(Date expiryDate) {
		this.expiryDate = expiryDate;
	}

	@JsonProperty("balanceToLevel")
	public String getBalanceToLevel() {
		return balanceToLevel;
	}

	@JsonProperty("balanceToLevel")
	public void setBalanceToLevel(String balanceToLevel) {
		this.balanceToLevel = balanceToLevel;
	}

	@JsonProperty("balanceToReward")
	public String getBalanceToReward() {
		return balanceToReward;
	}

	@JsonProperty("balanceToReward")
	public void setBalanceToReward(String balanceToReward) {
		this.balanceToReward = balanceToReward;
	}

	
}
