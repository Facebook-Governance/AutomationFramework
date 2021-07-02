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
public class Bill extends Account{

	
	@JsonProperty("amountDue")
	private Money amountDue ;
	
	@JsonProperty("amountDue")
	public Money getAmountDue() {
		return amountDue;
	}

	@JsonProperty("amountDue")
	public void setAmountDue(Money amountDue) {
		this.amountDue = amountDue;
	}
	
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
	
	@JsonProperty("dueDate")
	private Date dueDate ;

	@JsonProperty("dueDate")
	public Date getDueDate() {
		return dueDate;
	}

	@JsonProperty("dueDate")
	public void setDueDate(Date dueDate) {
		this.dueDate = dueDate;
	}
	
	@JsonProperty("lastPayment")
	private Money lastPayment ;
	
	@JsonProperty("lastPayment")
	public Money getLastPayment() {
		return lastPayment;
	}

	@JsonProperty("lastPayment")
	public void setLastPayment(Money lastPayment) {
		this.lastPayment = lastPayment;
	}
	
	@JsonProperty("lastPaymentAmount")
	private Money lastPaymentAmount ;
	
	@JsonProperty("lastPaymentAmount")
	public Money getLastPaymentAmount() {
		return lastPaymentAmount;
	}

	@JsonProperty("lastPaymentAmount")
	public void setLastPaymentAmount(Money lastPaymentAmount) {
		this.lastPaymentAmount = lastPaymentAmount;
	}
	
	@JsonProperty("lastPaymentDate")
	private Date lastPaymentDate ;
	

	@JsonProperty("lastPaymentDate")
	public Date getLastPaymentDate() {
		return lastPaymentDate;
	}

	@JsonProperty("lastPaymentDate")
	public void setLastPaymentDate(Date lastPaymentDate) {
		this.lastPaymentDate = lastPaymentDate;
	}


	
	@JsonProperty("minimumAmountDue")
	private Money minimumAmountDue;
	
	@JsonProperty("minimumAmountDue")
	public Money getMinimumAmountDue() {
		return minimumAmountDue;
	}
	@JsonProperty("minimumAmountDue")
	public void setMinimumAmountDue(Money minimumAmountDue) {
		this.minimumAmountDue = minimumAmountDue;
	}
	
	@JsonProperty("frequency")
	private String frequency ;
	
	@JsonProperty("frequency")
	public String getFrequency() {
		return frequency;
	}

	@JsonProperty("frequency")
	public void setFrequency(String frequency) {
		this.frequency = frequency;
	}
	
	@JsonProperty("holderProfile")
	private HolderProfile[] holderProfile;

	@JsonProperty("holderProfile")
	public HolderProfile[] getHolderProfile() {
		return holderProfile;
	}

	@JsonProperty("holderProfile")
	public void setHolderProfile(HolderProfile[] holderProfile) {
		this.holderProfile = holderProfile;
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
