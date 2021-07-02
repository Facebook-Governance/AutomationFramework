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

public class Account {

	private String accountType="REAL_ESTATE";
	private String accountName;
	private String nickName;
	private Address address;
	private String valuationType;
	private String includeInNetWorth="TRUE";
	private String memo;
	private HomeValue homeValue;
	public String getAccountType() {
		return accountType;
	}
	public void setAccountType(String accountType) {
		this.accountType = accountType;
	}
	public String getAccountName() {
		return accountName;
	}
	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}
	public String getNickName() {
		return nickName;
	}
	public void setNickName(String nickName) {
		this.nickName = nickName;
	}
	public Address getAddress() {
		return address;
	}
	public void setAddress(Address address) {
		this.address = address;
	}
	public String getValuationType() {
		return valuationType;
	}
	public void setValuationType(String valuationType) {
		this.valuationType = valuationType;
	}
	public String getIncludeInNetWorth() {
		return includeInNetWorth;
	}
	public void setIncludeInNetWorth(String includeInNetWorth) {
		this.includeInNetWorth = includeInNetWorth;
	}
	public String getMemo() {
		return memo;
	}
	public void setMemo(String memo) {
		this.memo = memo;
	}
	public HomeValue getHomeValue() {
		return homeValue;
	}
	public void setHomeValue(HomeValue homeValue) {
		this.homeValue = homeValue;
	}

}
