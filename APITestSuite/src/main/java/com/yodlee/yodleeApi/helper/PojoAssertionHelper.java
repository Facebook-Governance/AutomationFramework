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

import org.json.JSONArray;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.yodlee.yodleeApi.assertionPojo.Bank;
import com.yodlee.yodleeApi.assertionPojo.Bill;
import com.yodlee.yodleeApi.assertionPojo.CreditCard;
import com.yodlee.yodleeApi.assertionPojo.GetAccounts;
import com.yodlee.yodleeApi.assertionPojo.Insurance;
import com.yodlee.yodleeApi.assertionPojo.Investment;
import com.yodlee.yodleeApi.assertionPojo.Loan;
import com.yodlee.yodleeApi.assertionPojo.Reward;
import com.yodlee.yodleeApi.assertions.PojoAssertionUtil;

public class PojoAssertionHelper {

	protected Logger logger = LoggerFactory.getLogger(PojoAssertionHelper.class);


	public GetAccounts loadGetAccounts(String container, GetAccounts getAccountsPojo, JSONArray accountArray) {

		PojoAssertionUtil pojoAssertionUtil = new PojoAssertionUtil();
		
		for (int i = 0; i < accountArray.length(); i++) {
			String tempContainer=container;
			if(container.equalsIgnoreCase("EMPTY") || container.isEmpty()) {
				tempContainer= accountArray.getJSONObject(i).getString("CONTAINER");
			}
			
			System.out.println("container in loadGetAccounts::"+container);
			if (tempContainer.equalsIgnoreCase("bank")) {
				Bank bankPojo = new Bank();
				getAccountsPojo.setAccount(
						(Bank) pojoAssertionUtil.loadJsonObject2Pojo(accountArray.getJSONObject(i), bankPojo));
			} else if (tempContainer.equalsIgnoreCase("loan")) {
				Loan loanPojo = new Loan();
				getAccountsPojo.setAccount(
						(Loan) pojoAssertionUtil.loadJsonObject2Pojo(accountArray.getJSONObject(i), loanPojo));
			} else if (tempContainer.equalsIgnoreCase("reward")) {
				Reward rewardPojo = new Reward();
				getAccountsPojo.setAccount(
						(Reward) pojoAssertionUtil.loadJsonObject2Pojo(accountArray.getJSONObject(i), rewardPojo));
			} else if (tempContainer.equalsIgnoreCase("investment")) {
				Investment investmentPojo = new Investment();
				getAccountsPojo.setAccount((Investment) pojoAssertionUtil
						.loadJsonObject2Pojo(accountArray.getJSONObject(i), investmentPojo));
			} else if (tempContainer.equalsIgnoreCase("insurance")) {
				Insurance insurancePojo = new Insurance();
				getAccountsPojo.setAccount((Insurance) pojoAssertionUtil
						.loadJsonObject2Pojo(accountArray.getJSONObject(i), insurancePojo));
			} else if (tempContainer.equalsIgnoreCase("creditCard")) {
				CreditCard creditCardPojo = new CreditCard();
				getAccountsPojo.setAccount((CreditCard) pojoAssertionUtil
						.loadJsonObject2Pojo(accountArray.getJSONObject(i), creditCardPojo));
			} else if (tempContainer.equalsIgnoreCase("bill")) {
				Bill billPojo = new Bill();
				getAccountsPojo.setAccount(
						(Bill) pojoAssertionUtil.loadJsonObject2Pojo(accountArray.getJSONObject(i), billPojo));
			}
		}
		return getAccountsPojo;
	}

}
