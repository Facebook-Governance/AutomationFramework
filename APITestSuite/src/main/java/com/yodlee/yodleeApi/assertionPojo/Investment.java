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
public class Investment extends Account{

	@JsonProperty("401kLoan")
	private Money load_401k;
	
	@JsonProperty("401kLoan")
	public Money getLoad_401k() {
		return load_401k;
	}

	@JsonProperty("401kLoan")
	public void setLoad_401k(Money load_401k) {
		this.load_401k = load_401k;
	}
	
	@JsonProperty("annuityBalance")
	private Money annuityBalance ;
	
	
	@JsonProperty("annuityBalance")
	public Money getAnnuityBalance() {
		return annuityBalance;
	}

	@JsonProperty("annuityBalance")
	public void setAnnuityBalance(Money annuityBalance) {
		this.annuityBalance = annuityBalance;
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
	
	@JsonProperty("cash")
	private Money cash;
	
	@JsonProperty("cash")
	public Money getCash() {
		return cash;
	}

	@JsonProperty("cash")
	public void setCash(Money cash) {
		this.cash = cash;
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
	
	@JsonProperty("marginBalance")
	private Money marginBalance;
	
	@JsonProperty("marginBalance")
	public Money getMarginBalance() {
		return marginBalance;
	}

	@JsonProperty("marginBalance")
	public void setMarginBalance(Money marginBalance) {
		this.marginBalance = marginBalance;
	}
	
	@JsonProperty("moneyMarketBalance")
	private Money moneyMarketBalance;
	
	@JsonProperty("moneyMarketBalance")
	public Money getMoneyMarketBalance() {
		return moneyMarketBalance;
	}
	@JsonProperty("moneyMarketBalance")
	public void setMoneyMarketBalance(Money moneyMarketBalance) {
		this.moneyMarketBalance = moneyMarketBalance;
	}
	
	
	@JsonProperty("totalUnvestedBalance")
	private Money totalUnvestedBalance;
	
	@JsonProperty("totalUnvestedBalance")
	public Money getTotalUnvestedBalance() {
		return totalUnvestedBalance;
	}

	@JsonProperty("totalUnvestedBalance")
	public void setTotalUnvestedBalance(Money totalUnvestedBalance) {
		this.totalUnvestedBalance = totalUnvestedBalance;
	}
	
	
	@JsonProperty("totalVestedBalance")
	private Money totalVestedBalance ;
	
	@JsonProperty("totalVestedBalance")
	public Money getTotalVestedBalance() {
		return totalVestedBalance;
	}

	@JsonProperty("totalVestedBalance")
	public void setTotalVestedBalance(Money totalVestedBalance) {
		this.totalVestedBalance = totalVestedBalance;
	}
	
	@JsonProperty("shortBalance")
	private Money shortBalance ;
	
	@JsonProperty("shortBalance")
	public Money getShortBalance() {
		return shortBalance;
	}

	@JsonProperty("shortBalance")
	public void setShortBalance(Money shortBalance) {
		this.shortBalance = shortBalance;
	}
	
	@JsonProperty("lastEmployeeContributionAmount")
	private Money lastEmployeeContributionAmount;
	
	@JsonProperty("lastEmployeeContributionAmount")
	public Money getLastEmployeeContributionAmount() {
		return lastEmployeeContributionAmount;
	}

	@JsonProperty("lastEmployeeContributionAmount")
	public void setLastEmployeeContributionAmount(Money lastEmployeeContributionAmount) {
		this.lastEmployeeContributionAmount = lastEmployeeContributionAmount;
	}

	@JsonProperty("lastEmployeeContributionDate")
	private Date lastEmployeeContributionDate;

	@JsonProperty("lastEmployeeContributionDate")
	public Date getLastEmployeeContributionDate() {
		return lastEmployeeContributionDate;
	}

	@JsonProperty("lastEmployeeContributionDate")
	public void setLastEmployeeContributionDate(Date lastEmployeeContributionDate) {
		this.lastEmployeeContributionDate = lastEmployeeContributionDate;
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
		private Long[] associatedProviderAccountId ;

		@JsonProperty("associatedProviderAccountId")
		public Long[] getAssociatedProviderAccountId() {
			return associatedProviderAccountId;
		}

		@JsonProperty("associatedProviderAccountId")
		public void setAssociatedProviderAccountId(Long[] associatedProviderAccountId) {
			this.associatedProviderAccountId = associatedProviderAccountId;
		}
		
		@JsonProperty("availableLoan")
		private Money availableLoan;

		@JsonProperty("availableLoan")
		public Money getAvailableLoan() {
			return availableLoan;
		}

		@JsonProperty("availableLoan")
		public void setAvailableLoan(Money availableLoan) {
			this.availableLoan = availableLoan;
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
		
		@JsonProperty("investmentPlan")
		private InvestmentPlan[] investmentPlan;

		@JsonProperty("investmentPlan")
		public InvestmentPlan[] getInvestmentPlan() {
			return investmentPlan;
		}

		@JsonProperty("investmentPlan")
		public void setInvestmentPlan(InvestmentPlan[] investmentPlan) {
			this.investmentPlan = investmentPlan;
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
