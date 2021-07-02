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
public class Loan extends Account{
	
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
	
	@JsonProperty("interestPaidYTD")
	private Money interestPaidYTD ;
	
	@JsonProperty("interestPaidYTD")
	public Money getInterestPaidYTD() {
		return interestPaidYTD;
	}

	@JsonProperty("interestPaidYTD")
	public void setInterestPaidYTD(Money interestPaidYTD) {
		this.interestPaidYTD = interestPaidYTD;
	}
	
	@JsonProperty("interestPaidLastYear")
	private Money interestPaidLastYear ;
	
	@JsonProperty("interestPaidLastYear")
	public Money getInterestPaidLastYear() {
		return interestPaidLastYear;
	}

	@JsonProperty("interestPaidLastYear")
	public void setInterestPaidLastYear(Money interestPaidLastYear) {
		this.interestPaidLastYear = interestPaidLastYear;
	}
	
	@JsonProperty("interestRateType")
	private String interestRateType;
	

	@JsonProperty("interestRateType")
	public String getInterestRateType() {
		return interestRateType;
	}

	@JsonProperty("interestRateType")
	public void setInterestRateType(String interestRateType) {
		this.interestRateType = interestRateType;
	}
	
	
	@JsonProperty("collateral")
	private String collateral;
	
	@JsonProperty("collateral")
	public String getCollateral() {
		return collateral;
	}

	@JsonProperty("collateral")
	public void setCollateral(String collateral) {
		this.collateral = collateral;
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
	
	@JsonProperty("interestRate")
	private Double interestRate ;
	
	@JsonProperty("interestRate")
	public Double getInterestRate() {
		return interestRate;
	}

	@JsonProperty("interestRate")
	public void setInterestRate(Double interestRate) {
		this.interestRate = interestRate;
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

	
	@JsonProperty("maturityDate")
	private Date maturityDate ;
	
	
	@JsonProperty("maturityDate")
	public Date getMaturityDate() {
		return maturityDate;
	}

	@JsonProperty("maturityDate")
	public void setMaturityDate(Date maturityDate) {
		this.maturityDate = maturityDate;
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
	
	
	@JsonProperty("escrowBalance")
	private Money escrowBalance ;
	
	@JsonProperty("escrowBalance")
	public Money getEscrowBalance() {
		return escrowBalance;
	}

	@JsonProperty("escrowBalance")
	public void setEscrowBalance(Money escrowBalance) {
		this.escrowBalance = escrowBalance;
	}
	

	
	@JsonProperty("originalLoanAmount")
	private Money originalLoanAmount ;
	

	@JsonProperty("originalLoanAmount")
	public Money getOriginalLoanAmount() {
		return originalLoanAmount;
	}

	@JsonProperty("originalLoanAmount")
	public void setOriginalLoanAmount(Money originalLoanAmount) {
		this.originalLoanAmount = originalLoanAmount;
	}
	
	
	@JsonProperty("principalBalance")
	private Money principalBalance ;
	
	@JsonProperty("principalBalance")
	public Money getPrincipalBalance() {
		return principalBalance;
	}

	@JsonProperty("principalBalance")
	public void setPrincipalBalance(Money principalBalance) {
		this.principalBalance = principalBalance;
	}
	
	@JsonProperty("recurringPayment")
	private Money recurringPayment ;
	
	@JsonProperty("recurringPayment")
	public Money getRecurringPayment() {
		return recurringPayment;
	}

	@JsonProperty("recurringPayment")
	public void setRecurringPayment(Money recurringPayment) {
		this.recurringPayment = recurringPayment;
	}
	
	//This should be term type but temporarity String type
	@JsonProperty("term")
	private String term ;

	@JsonProperty("term")
	public String getTerm() {
		return term;
	}

	@JsonProperty("term")
	public void setTerm(String term) {
		this.term = term;
	}
	
	@JsonProperty("totalCreditLimit")
	private Money totalCreditLimit ;
	
	@JsonProperty("totalCreditLimit")
	public Money getTotalCreditLimit() {
		return totalCreditLimit;
	}

	@JsonProperty("totalCreditLimit")
	public void setTotalCreditLimit(Money totalCreditLimit) {
		this.totalCreditLimit = totalCreditLimit;
	}

	@JsonProperty("originationDate")
	private Date originationDate ;
	
	@JsonProperty("originationDate")
	public Date getOriginationDate() {
		return originationDate;
	}

	@JsonProperty("originationDate")
	public void setOriginationDate(Date originationDate) {
		this.originationDate = originationDate;
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
