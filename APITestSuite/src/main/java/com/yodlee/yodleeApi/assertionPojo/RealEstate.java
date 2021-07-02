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
public class RealEstate {

	
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
	
	@JsonProperty("valuationType")
	private String valuationType ;
	
	@JsonProperty("valuationType")
	public String getValuationType() {
		return valuationType;
	}

	@JsonProperty("valuationType")
	public void setValuationType(String valuationType) {
		this.valuationType = valuationType;
	}


	@JsonProperty("homeValue")
	private Money homeValue ;
	
	@JsonProperty("homeValue")
	public Money getHomeValue() {
		return homeValue;
	}

	@JsonProperty("homeValue")
	public void setHomeValue(Money homeValue) {
		this.homeValue = homeValue;
	}

	@JsonProperty("estimatedDate")
	private Date estimatedDate ;
	
	@JsonProperty("estimatedDate")
	public Date getEstimatedDate() {
		return estimatedDate;
	}

	@JsonProperty("estimatedDate")
	public void setEstimatedDate(Date estimatedDate) {
		this.estimatedDate = estimatedDate;
	}

	@JsonProperty("address")
	private Address address ;
	
	@JsonProperty("address")
	public Address getAddress() {
		return address;
	}

	@JsonProperty("address")
	public void setAddress(Address address) {
		this.address = address;
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
	
	@JsonProperty("historicalBalances")
	private HistoricalBalances[] historicalBalances;
	
	@JsonProperty("historicalBalances")
	public HistoricalBalances[] getHistoricalBalances() {
		return historicalBalances;
	}

	@JsonProperty("historicalBalances")
	public void setHistoricalBalances(HistoricalBalances[] historicalBalances) {
		this.historicalBalances = historicalBalances;
	}

}
