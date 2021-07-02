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

public class Dataset {

	@JsonProperty("name")
	private String name;
	
	@JsonProperty("additionalStatus")
	private String additionalStatus;
	
	@JsonProperty("updateEligibility")
	private String updateEligibility;
	
	@JsonProperty("lastUpdated")
	private Date lastUpdated;
	
	@JsonProperty("lastUpdateAttempt")
	private Date lastUpdateAttempt;
	
	@JsonProperty("name")
	public String getName() {
		return name;
	}

	@JsonProperty("name")
	public void setName(String name) {
		this.name = name;
	}

	@JsonProperty("additionalStatus")
	public String getAdditionalStatus() {
		return additionalStatus;
	}

	@JsonProperty("additionalStatus")
	public void setAdditionalStatus(String additionalStatus) {
		this.additionalStatus = additionalStatus;
	}

	@JsonProperty("updateEligibility")
	public String getUpdateEligibility() {
		return updateEligibility;
	}

	@JsonProperty("updateEligibility")
	public void setUpdateEligibility(String updateEligibility) {
		this.updateEligibility = updateEligibility;
	}

	@JsonProperty("lastUpdated")
	public Date getLastUpdated() {
		return lastUpdated;
	}

	@JsonProperty("lastUpdated")
	public void setLastUpdated(Date lastUpdated) {
		this.lastUpdated = lastUpdated;
	}

	@JsonProperty("lastUpdateAttempt")
	public Date getLastUpdateAttempt() {
		return lastUpdateAttempt;
	}

	@JsonProperty("lastUpdateAttempt")
	public void setLastUpdateAttempt(Date lastUpdateAttempt) {
		this.lastUpdateAttempt = lastUpdateAttempt;
	}

	
}
