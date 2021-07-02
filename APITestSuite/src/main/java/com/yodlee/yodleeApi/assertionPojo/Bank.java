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

public class Bank extends Account{

	@JsonProperty("annualPercentageYield")
	private Double annualPercentageYield;

	@JsonProperty("annualPercentageYield")
	public Double getAnnualPercentageYield() {
		return annualPercentageYield;
	}

	@JsonProperty("annualPercentageYield")
	public void setAnnualPercentageYield(Double annualPercentageYield) {
		this.annualPercentageYield = annualPercentageYield;
	}

	@JsonProperty("bankTransferCode")
	private BankTransferCode[] bankTransferCode;

	@JsonProperty("bankTransferCode")
	public BankTransferCode[] getBankTransferCode() {
		return bankTransferCode;
	}

	@JsonProperty("bankTransferCode")
	public void setBankTransferCode(BankTransferCode[] bankTransferCode) {
		this.bankTransferCode = bankTransferCode;
	}

	@JsonProperty("availableBalance")
	private Money availableBalance;

	@JsonProperty("availableBalance")
	public Money getAvailableBalance() {
		return availableBalance;
	}

	@JsonProperty("availableBalance")
	public void setAvailableBalance(Money availableBalance) {
		this.availableBalance = availableBalance;
	}

	@JsonProperty("balance")
	private Money balance;

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

	@JsonProperty("currentBalance")
	private Money currentBalance;

	@JsonProperty("currentBalance")
	public Money getCurrentBalance() {
		return currentBalance;
	}

	@JsonProperty("currentBalance")
	public void setCurrentBalance(Money currentBalance) {
		this.currentBalance = currentBalance;
	}

	@JsonProperty("interestRate")
	private Double interestRate;

	@JsonProperty("interestRate")
	public Double getInterestRate() {
		return interestRate;
	}

	@JsonProperty("interestRate")
	public void setInterestRate(Double interestRate) {
		this.interestRate = interestRate;
	}

	

	@JsonProperty("maturityAmount")
	private Money maturityAmount;

	@JsonProperty("maturityAmount")
	public Money getMaturityAmount() {
		return maturityAmount;
	}

	@JsonProperty("maturityAmount")
	public void setMaturityAmount(Money maturityAmount) {
		this.maturityAmount = maturityAmount;
	}

	@JsonProperty("maturityDate")
	private Date maturityDate;

	@JsonProperty("maturityDate")
	public Date getMaturityDate() {
		return maturityDate;
	}

	@JsonProperty("maturityDate")
	public void setMaturityDate(Date maturityDate) {
		this.maturityDate = maturityDate;
	}

	// This should be term type but temporarity String type
	@JsonProperty("term")
	private String term;

	@JsonProperty("term")
	public String getTerm() {
		return term;
	}

	@JsonProperty("term")
	public void setTerm(String term) {
		this.term = term;
	}

	@JsonProperty("overDraftLimit")
	private Money overDraftLimit;

	@JsonProperty("overDraftLimit")
	public Money getOverDraftLimit() {
		return overDraftLimit;
	}

	@JsonProperty("overDraftLimit")
	public void setOverDraftLimit(Money overDraftLimit) {
		this.overDraftLimit = overDraftLimit;
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
	
	@JsonProperty("associatedProviderAccountId")
	private Long[] associatedProviderAccountId;

	@JsonProperty("associatedProviderAccountId")
	public Long[] getAssociatedProviderAccountId() {
		return associatedProviderAccountId;
	}

	@JsonProperty("associatedProviderAccountId")
	public void setAssociatedProviderAccountId(Long[] associatedProviderAccountId) {
		this.associatedProviderAccountId = associatedProviderAccountId;
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
