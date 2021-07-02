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
public class Insurance extends Account{

	
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
	
	@JsonProperty("premium")
	private Money premium ;
	
	@JsonProperty("premium")
	public Money getPremium() {
		return premium;
	}

	@JsonProperty("premium")
	public void setPremium(Money premium) {
		this.premium = premium;
	}
	
	@JsonProperty("remainingBalance")
	private Money remainingBalance ; 
	
	@JsonProperty("remainingBalance")
	public Money getRemainingBalance() {
		return remainingBalance;
	}

	@JsonProperty("remainingBalance")
	public void setRemainingBalance(Money remainingBalance) {
		this.remainingBalance = remainingBalance;
	}
	
	
	
	
	@JsonProperty("policyEffectiveDate")
	private Date policyEffectiveDate ;
	
	
	@JsonProperty("policyEffectiveDate")
	public Date getPolicyEffectiveDate() {
		return policyEffectiveDate;
	}

	@JsonProperty("policyEffectiveDate")
	public void setPolicyEffectiveDate(Date policyEffectiveDate) {
		this.policyEffectiveDate = policyEffectiveDate;
	}


	@JsonProperty("policyFromDate")
	private Date policyFromDate ;
	
	@JsonProperty("policyFromDate")
	public Date getPolicyFromDate() {
		return policyFromDate;
	}

	@JsonProperty("policyFromDate")
	public void setPolicyFromDate(Date policyFromDate) {
		this.policyFromDate = policyFromDate;
	}
	
	@JsonProperty("policyToDate")
	private Date policyToDate ;
	
	

	@JsonProperty("policyToDate")
	public Date getPolicyToDate() {
		return policyToDate;
	}

	@JsonProperty("policyToDate")
	public void setPolicyToDate(Date policyToDate) {
		this.policyToDate = policyToDate;
	}

	@JsonProperty("deathBenefit")
	private Money deathBenefit ;
	
	@JsonProperty("deathBenefit")
	public Money getDeathBenefit() {
		return deathBenefit;
	}

	@JsonProperty("deathBenefit")
	public void setDeathBenefit(Money deathBenefit) {
		this.deathBenefit = deathBenefit;
	}
	
	//I need to change to term type
			@JsonProperty("policyTerm")
			private String policyTerm ;
			
			@JsonProperty("policyTerm")
			public String getPolicyTerm() {
				return policyTerm;
			}

			@JsonProperty("policyTerm")
			public void setPolicyTerm(String policyTerm) {
				this.policyTerm = policyTerm;
			}
	
	@JsonProperty("policyStatus")
	private String policyStatus ;
	
	@JsonProperty("policyStatus")
	public String getPolicyStatus() {
		return policyStatus;
	}

	@JsonProperty("policyStatus")
	public void setPolicyStatus(String policyStatus) {
		this.policyStatus = policyStatus;
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
	
	@JsonProperty("cashValue")
	private Money cashValue ;
	
	@JsonProperty("cashValue")
	public Money getCashValue() {
		return cashValue;
	}

	@JsonProperty("cashValue")
	public void setCashValue(Money cashValue) {
		this.cashValue = cashValue;
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

	@JsonProperty("expirationDate")
	private Date expirationDate ;
	
	@JsonProperty("expirationDate")
	public Date getExpirationDate() {
		return expirationDate;
	}

	@JsonProperty("expirationDate")
	public void setExpirationDate(Date expirationDate) {
		this.expirationDate = expirationDate;
	}
	
	@JsonProperty("faceAmount")
	private Money faceAmount ;
	
	@JsonProperty("faceAmount")
	public Money getFaceAmount() {
		return faceAmount;
	}

	@JsonProperty("faceAmount")
	public void setFaceAmount(Money faceAmount) {
		this.faceAmount = faceAmount;
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
	
	
	//This should be term type but temporarity String type
		@JsonProperty("premiumPaymentTerm")
		private String premiumPaymentTerm ;

		@JsonProperty("premiumPaymentTerm")
		public String getPremiumPaymentTerm() {
			return premiumPaymentTerm;
		}

		@JsonProperty("premiumPaymentTerm")
		public void setPremiumPaymentTerm(String premiumPaymentTerm) {
			this.premiumPaymentTerm = premiumPaymentTerm;
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
		
		@JsonProperty("lifeInsuranceType")
		private String lifeInsuranceType;

		@JsonProperty("lifeInsuranceType")
		public String getLifeInsuranceType() {
			return lifeInsuranceType;
		}

		@JsonProperty("lifeInsuranceType")
		public void setLifeInsuranceType(String lifeInsuranceType) {
			this.lifeInsuranceType = lifeInsuranceType;
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

