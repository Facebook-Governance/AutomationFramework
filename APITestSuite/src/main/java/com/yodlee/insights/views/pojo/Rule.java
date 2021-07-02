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
/**
 * Pojo class 
 * 
 * @author kemparaja
 *
 */
public class Rule {
	
	private String name;
	private String fromDate;
	private String toDate;
	private String type;
	private Include include;
	private Exclude exclude;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getFromDate() {
		return fromDate;
	}
	public void setFromDate(String fromDate) {
		this.fromDate = fromDate;
	}
	public String getToDate() {
		return toDate;
	}
	public void setToDate(String toDate) {
		this.toDate = toDate;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public Include getInclude() {
		return include;
	}
	public void setInclude(Include include) {
		this.include = include;
	}
	public Exclude getExclude() {
		return exclude;
	}
	public void setExclude(Exclude exclude) {
		this.exclude = exclude;
	}

}
