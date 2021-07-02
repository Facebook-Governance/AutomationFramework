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

import java.util.ArrayList;

import com.fasterxml.jackson.annotation.JsonProperty;

public class GetAccounts{
	
	@JsonProperty("account")
	private ArrayList<Account> account=new ArrayList<Account>();

	@JsonProperty("account")
	public ArrayList<Account> getAccount() {
		return account;
	}

	@JsonProperty("account")
	public void setAccount(Account account) {
		this.account.add(account);
	}
}


