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

public class Address {

	@JsonProperty("address1")
	private String address1;
	
	@JsonProperty("address2")
	private String address2;
	
	@JsonProperty("address3")
	private String address3;
	
	@JsonProperty("state")
	private String state ;
	
	@JsonProperty("city")
	private String city;
	
	@JsonProperty("ZIP")
	private String ZIP ;
	
	@JsonProperty("type")
	private String type;
	
	@JsonProperty("country")
	private String country;	
	
	@JsonProperty("address1")
	public String getAddress1() {
		return address1;
	}

	@JsonProperty("address1")
	public void setAddress1(String address1) {
		this.address1 = address1;
	}

	@JsonProperty("address2")
	public String getAddress2() {
		return address2;
	}

	@JsonProperty("address2")
	public void setAddress2(String address2) {
		this.address2 = address2;
	}

	@JsonProperty("address3")
	public String getAddress3() {
		return address3;
	}

	@JsonProperty("address3")
	public void setAddress3(String address3) {
		this.address3 = address3;
	}

	@JsonProperty("state")
	public String getState() {
		return state;
	}

	@JsonProperty("state")
	public void setState(String state) {
		this.state = state;
	}

	@JsonProperty("city")
	public String getCity() {
		return city;
	}

	@JsonProperty("city")
	public void setCity(String city) {
		this.city = city;
	}

	@JsonProperty("ZIP")
	public String getZIP() {
		return ZIP;
	}

	@JsonProperty("ZIP")
	public void setZIP(String zIP) {
		ZIP = zIP;
	}

	@JsonProperty("type")
	public String getType() {
		return type;
	}

	@JsonProperty("type")
	public void setType(String type) {
		this.type = type;
	}

	
	@JsonProperty("country")
	public String getCountry() {
		return country;
	}

	@JsonProperty("country")
	public void setCountry(String country) {
		this.country = country;
	}

	
	
}
