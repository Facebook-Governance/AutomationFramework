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

public class Name {

	
	@JsonProperty("first")
	private String first;
	
	@JsonProperty("last")
	private String last;
	
	@JsonProperty("middle")
	private String middle;
	
	@JsonProperty("displayed")
	private String displayed;
	
	@JsonProperty("fullName")
	private String fullName;
	
	
	@JsonProperty("first")
	public String getFirst() {
		return first;
	}

	@JsonProperty("first")
	public void setFirst(String first) {
		this.first = first;
	}
	@JsonProperty("last")
	public String getLast() {
		return last;
	}

	@JsonProperty("last")
	public void setLast(String last) {
		this.last = last;
	}

	@JsonProperty("middle")
	public String getMiddle() {
		return middle;
	}

	@JsonProperty("middle")
	public void setMiddle(String middle) {
		this.middle = middle;
	}
	@JsonProperty("displayed")
	public String getDisplayed() {
		return displayed;
	}
	@JsonProperty("displayed")
	public void setDisplayed(String displayed) {
		this.displayed = displayed;
	}

	@JsonProperty("fullName")
	public String getFullName() {
		return fullName;
	}

	@JsonProperty("fullName")
	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

}
