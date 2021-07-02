/*******************************************************************************
 *
 * * Copyright (c) 2020 Yodlee, Inc. All Rights Reserved.
 *
 * *
 *
 * * This software is the confidential and proprietary information of Yodlee, Inc.
 *
 * * Use is subject to license terms.
 *
 *******************************************************************************/

package com.yodlee.insights.views.pojo;

public class RuntimeParams {

	private String container;
	private String transactionDays = "365";
	private String cacheItemId;
	private String memId;
	public String getContainer() {
		return container;
	}
	public void setContainer(String container) {
		this.container = container;
	}
	public String getTransactionDays() {
		return transactionDays;
	}
	public void setTransactionDays(String transactionDays) {
		this.transactionDays = transactionDays;
	}
	public String getCacheItemId() {
		return cacheItemId;
	}
	public void setCacheItemId(String cacheItemId) {
		this.cacheItemId = cacheItemId;
	}
	public String getMemId() {
		return memId;
	}
	public void setMemId(String memId) {
		this.memId = memId;
	}
}
