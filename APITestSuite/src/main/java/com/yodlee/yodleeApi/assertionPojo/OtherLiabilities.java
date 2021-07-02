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
public class OtherLiabilities {
	
	@JsonProperty("balance")
	private Money balance ;
	
	@JsonProperty("balance")
	public Money getBalance() {
		return balance;
	}

	@JsonProperty("balance")
	public void setBalance(Money balance) {
		this.balance = balance;
	}
	
	@JsonProperty("includeInNetWorth")
	private Boolean includeInNetWorth;
	
	@JsonProperty("includeInNetWorth")
	public Boolean getIncludeInNetWorth() {
		return includeInNetWorth;
	}

	@JsonProperty("includeInNetWorth")
	public void setIncludeInNetWorth(Boolean includeInNetWorth) {
		this.includeInNetWorth = includeInNetWorth;
	}
	
	@JsonProperty("memo")
	private String memo ;
	
	@JsonProperty("memo")
	public String getMemo() {
		return memo;
	}

	@JsonProperty("memo")
	public void setMemo(String memo) {
		this.memo = memo;
	}
}
