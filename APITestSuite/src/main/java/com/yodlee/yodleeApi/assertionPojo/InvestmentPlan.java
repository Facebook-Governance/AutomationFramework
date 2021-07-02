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
public class InvestmentPlan {

	@JsonProperty("id")
	private Long id;
	
	@JsonProperty("id")
	public Long getId() {
		return id;
	}

	@JsonProperty("id")
	public void setId(Long id) {
		this.id = id;
	}
	
	@JsonProperty("planName")
	private String planName;
	
	@JsonProperty("planName")
	public String getPlanName() {
		return planName;
	}

	@JsonProperty("planName")
	public void setPlanName(String planName) {
		this.planName = planName;
	}
	
	@JsonProperty("planNumber")
	private String planNumber;
	
	@JsonProperty("planNumber")
	public String getPlanNumber() {
		return planNumber;
	}

	@JsonProperty("planNumber")
	public void setPlanNumber(String planNumber) {
		this.planNumber = planNumber;
	}
	
	@JsonProperty("providerId")
	private String  providerId;
	
	@JsonProperty("providerId")
	public String getProviderId() {
		return providerId;
	}

	@JsonProperty("providerId")
	public void setProviderId(String providerId) {
		this.providerId = providerId;
	}
	
	@JsonProperty("providerName")
	private String providerName;
	
	@JsonProperty("providerName")
	public String getProviderName() {
		return providerName;
	}

	@JsonProperty("providerName")
	public void setProviderName(String providerName) {
		this.providerName = providerName;
	}
	
	@JsonProperty("asOfDate")
	private Date asOfDate ;
	
	@JsonProperty("asOfDate")
	public Date getAsOfDate() {
		return asOfDate;
	}

	@JsonProperty("asOfDate")
	public void setAsOfDate(Date asOfDate) {
		this.asOfDate = asOfDate;
	}
	
	@JsonProperty("returnAsOfDate")
	private Date returnAsOfDate ;
	
	@JsonProperty("returnAsOfDate")
	public Date getReturnAsOfDate() {
		return returnAsOfDate;
	}

	@JsonProperty("returnAsOfDate")
	public void setReturnAsOfDate(Date returnAsOfDate) {
		this.returnAsOfDate = returnAsOfDate;
	}

	
	@JsonProperty("lastUpdated")
	private Date lastUpdated;
	
	@JsonProperty("lastUpdated")
	public Date getLastUpdated() {
		return lastUpdated;
	}

	@JsonProperty("lastUpdated")
	public void setLastUpdated(Date lastUpdated) {
		this.lastUpdated = lastUpdated;
	}
	
	@JsonProperty("feesAsofDate")
	private Date feesAsofDate;
	
	@JsonProperty("feesAsofDate")
	public Date getFeesAsofDate() {
		return feesAsofDate;
	}

	@JsonProperty("feesAsofDate")
	public void setFeesAsofDate(Date feesAsofDate) {
		this.feesAsofDate = feesAsofDate;
	}
	
	@JsonProperty("investmentOptions")
	private InvestmentOptions[] investmentOptions;

	@JsonProperty("investmentOptions")
	public InvestmentOptions[] getInvestmentOptions() {
		return investmentOptions;
	}

	@JsonProperty("investmentOptions")
	public void setInvestmentOptions(InvestmentOptions[] investmentOptions) {
		this.investmentOptions = investmentOptions;
	}

	
}
