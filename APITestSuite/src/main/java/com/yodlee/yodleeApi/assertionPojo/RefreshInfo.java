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

import com.fasterxml.jackson.annotation.JsonProperty;

public class RefreshInfo {
	
	@JsonProperty("statusCode")
	private Integer statusCode;
	
	@JsonProperty("statusCode")
	public Integer getStatusCode() {
		return statusCode;
	}

	@JsonProperty("statusCode")
	public void setStatusCode(Integer statusCode) {
		this.statusCode = statusCode;
	}

	@JsonProperty("statusMessage")
	private String statusMessage;
	
	@JsonProperty("statusMessage")
	public String getStatusMessage() {
		return statusMessage;
	}

	@JsonProperty("statusMessage")
	public void setStatusMessage(String statusMessage) {
		this.statusMessage = statusMessage;
	}

	@JsonProperty("nextRefreshScheduled")
	private String nextRefreshScheduled;
	
	@JsonProperty("nextRefreshScheduled")
	public String getNextRefreshScheduled() {
		return nextRefreshScheduled;
	}

	@JsonProperty("nextRefreshScheduled")
	public void setNextRefreshScheduled(String nextRefreshScheduled) {
		this.nextRefreshScheduled = nextRefreshScheduled;
	}

	@JsonProperty("lastRefreshed")
	private String lastRefreshed;
	
	@JsonProperty("lastRefreshed")
	public String getLastRefreshed() {
		return lastRefreshed;
	}

	@JsonProperty("lastRefreshed")
	public void setLastRefreshed(String lastRefreshed) {
		this.lastRefreshed = lastRefreshed;
	}

	@JsonProperty("lastRefreshAttempt")
	private String lastRefreshAttempt;
	
	@JsonProperty("lastRefreshAttempt")
	public String getLastRefreshAttempt() {
		return lastRefreshAttempt;
	}

	@JsonProperty("lastRefreshAttempt")
	public void setLastRefreshAttempt(String lastRefreshAttempt) {
		this.lastRefreshAttempt = lastRefreshAttempt;
	}
	
}
