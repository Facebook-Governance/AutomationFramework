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
package com.yodlee.insights.yodleeApi.utils;

/**
 * Helper class for board suite
 * 
 * @author kemparaja
 *
 */
public class DagCredentials {
	
	private String dagUser = null;
	private String dagPasswrod = null;
	public String getDagUser() {
		return dagUser;
	}
	public void setDagUser(String dagUser) {
		this.dagUser = dagUser;
	}
	public String getDagPasswrod() {
		return dagPasswrod;
	}
	public void setDagPasswrod(String dagPasswrod) {
		this.dagPasswrod = dagPasswrod;
	}

}
