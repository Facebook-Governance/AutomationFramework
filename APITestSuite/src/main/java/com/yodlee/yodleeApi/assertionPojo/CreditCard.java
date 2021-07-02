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
public class CreditCard extends Account{

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
	
	
	@JsonProperty("apr")
	private Double apr ;
	
	@JsonProperty("apr")
	public Double getApr() {
		return apr;
	}

	@JsonProperty("apr")
	public void setApr(Double apr) {
		this.apr = apr;
	}
	
	@JsonProperty("availableCash")
	private Money availableCash ;
	
	@JsonProperty("availableCash")
	public Money getAvailableCash() {
		return availableCash;
	}

	@JsonProperty("availableCash")
	public void setAvailableCash(Money availableCash) {
		this.availableCash = availableCash;
	}

	
	@JsonProperty("availableCredit")
	private Money availableCredit ;
	
	@JsonProperty("availableCredit")
	public Money getAvailableCredit() {
		return availableCredit;
	}

	@JsonProperty("availableCredit")
	public void setAvailableCredit(Money availableCredit) {
		this.availableCredit = availableCredit;
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
	
	@JsonProperty("classification")
	private String classification;
	
	@JsonProperty("classification")
	public String getClassification() {
		return classification;
	}

	@JsonProperty("classification")
	public void setClassification(String classification) {
		this.classification = classification;
	}
	
	@JsonProperty("userClassification")
	private String userClassification;
	
	@JsonProperty("userClassification")
	public String getUserClassification() {
		return userClassification;
	}

	@JsonProperty("userClassification")
	public void setUserClassification(String userClassification) {
		this.userClassification = userClassification;
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
	
	@JsonProperty("runningBalance")
	private Money runningBalance ;
	
	@JsonProperty("runningBalance")
	public Money getRunningBalance() {
		return runningBalance;
	}

	@JsonProperty("runningBalance")
	public void setRunningBalance(Money runningBalance) {
		this.runningBalance = runningBalance;
	}

	@JsonProperty("totalCashLimit")
	private Money totalCashLimit ;
	
	@JsonProperty("totalCashLimit")
	public Money getTotalCashLimit() {
		return totalCashLimit;
	}

	@JsonProperty("totalCashLimit")
	public void setTotalCashLimit(Money totalCashLimit) {
		this.totalCashLimit = totalCashLimit;
	}

	@JsonProperty("totalCreditLine")
	private Money totalCreditLine ;
	
	@JsonProperty("totalCreditLine")
	public Money getTotalCreditLine() {
		return totalCreditLine;
	}

	@JsonProperty("totalCreditLine")
	public void setTotalCreditLine(Money totalCreditLine) {
		this.totalCreditLine = totalCreditLine;
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
