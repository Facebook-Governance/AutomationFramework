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
package com.yodlee.insights.yodleeApi.utils;

import java.io.Serializable;
import java.util.Date;

import javax.validation.Valid;
import javax.validation.constraints.AssertFalse;
import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;


public class UserSegment{

	
	private Double age;
	private Boolean homeOwnership;	
	private String lifeStage;	
	private String incomeRange;	
	private Money income;
	private UserGeoLocation geoLocation;	
	private Money netWorth;
	public Double getAge() {
		return age;
	}
	public void setAge(Double age) {
		this.age = age;
	}
	public Boolean getHomeOwnership() {
		return homeOwnership;
	}
	public void setHomeOwnership(Boolean homeOwnership) {
		this.homeOwnership = homeOwnership;
	}
	public String getLifeStage() {
		return lifeStage;
	}
	public void setLifeStage(String lifeStage) {
		this.lifeStage = lifeStage;
	}
	public String getIncomeRange() {
		return incomeRange;
	}
	public void setIncomeRange(String incomeRange) {
		this.incomeRange = incomeRange;
	}
	public Money getIncome() {
		return income;
	}
	public void setIncome(Money income) {
		this.income = income;
	}
	public UserGeoLocation getGeoLocation() {
		return geoLocation;
	}
	public void setGeoLocation(UserGeoLocation geoLocation) {
		this.geoLocation = geoLocation;
	}
	public Money getNetWorth() {
		return netWorth;
	}
	public void setNetWorth(Money netWorth) {
		this.netWorth = netWorth;
	}

}
