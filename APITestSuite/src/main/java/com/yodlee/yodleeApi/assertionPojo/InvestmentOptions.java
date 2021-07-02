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
public class InvestmentOptions {
	
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
	
	@JsonProperty("cusipNumber")
	private String cusipNumber; 
	
	@JsonProperty("cusipNumber")
	public String getCusipNumber() {
		return cusipNumber;
	}

	@JsonProperty("cusipNumber")
	public void setCusipNumber(String cusipNumber) {
		this.cusipNumber = cusipNumber;
	}

	
	@JsonProperty("description")
	private String description ;
	
	@JsonProperty("description")
	public String getDescription() {
		return description;
	}

	@JsonProperty("description")
	public void setDescription(String description) {
		this.description = description;
	}

	
	@JsonProperty("fiveYearReturn")
	private Double fiveYearReturn ;

	@JsonProperty("fiveYearReturn")
	public Double getFiveYearReturn() {
		return fiveYearReturn;
	}

	@JsonProperty("fiveYearReturn")
	public void setFiveYearReturn(Double fiveYearReturn) {
		this.fiveYearReturn = fiveYearReturn;
	}
	
	@JsonProperty("holdingType")
	private String holdingType;
	
	@JsonProperty("holdingType")
	public String getHoldingType() {
		return holdingType;
	}

	@JsonProperty("holdingType")
	public void setHoldingType(String holdingType) {
		this.holdingType = holdingType;
	}
	
	@JsonProperty("isin")
	private String isin ;
	
	@JsonProperty("isin")
	public String getIsin() {
		return isin;
	}

	@JsonProperty("isin")
	public void setIsin(String isin) {
		this.isin = isin;
	}
	
	@JsonProperty("oneMonthReturn")
	private Double oneMonthReturn ;
	
	@JsonProperty("oneMonthReturn")
	public Double getOneMonthReturn() {
		return oneMonthReturn;
	}

	@JsonProperty("oneMonthReturn")
	public void setOneMonthReturn(Double oneMonthReturn) {
		this.oneMonthReturn = oneMonthReturn;
	}
	
	@JsonProperty("oneYearReturn")
	private Double oneYearReturn ;
	
	@JsonProperty("oneYearReturn")
	public Double getOneYearReturn() {
		return oneYearReturn;
	}

	@JsonProperty("oneYearReturn")
	public void setOneYearReturn(Double oneYearReturn) {
		this.oneYearReturn = oneYearReturn;
	}
	
	@JsonProperty("price")
	private Money price;
	

	@JsonProperty("price")
	public Money getPrice() {
		return price;
	}

	@JsonProperty("price")
	public void setPrice(Money price) {
		this.price = price;
	}
	
	@JsonProperty("priceAsOfDate")
	private Date priceAsOfDate ;
	
	@JsonProperty("priceAsOfDate")
	public Date getPriceAsOfDate() {
		return priceAsOfDate;
	}

	@JsonProperty("priceAsOfDate")
	public void setPriceAsOfDate(Date priceAsOfDate) {
		this.priceAsOfDate = priceAsOfDate;
	}
	
	@JsonProperty("sedol")
	private String sedol ;
	
	@JsonProperty("sedol")
	public String getSedol() {
		return sedol;
	}

	@JsonProperty("sedol")
	public void setSedol(String sedol) {
		this.sedol = sedol;
	}
	
	@JsonProperty("symbol")
	private String symbol ;
	
	@JsonProperty("symbol")
	public String getSymbol() {
		return symbol;
	}

	@JsonProperty("symbol")
	public void setSymbol(String symbol) {
		this.symbol = symbol;
	}
	
	@JsonProperty("tenYearReturn")
	private Double tenYearReturn; 
	
	@JsonProperty("tenYearReturn")
	public Double getTenYearReturn() {
		return tenYearReturn;
	}

	@JsonProperty("tenYearReturn")
	public void setTenYearReturn(Double tenYearReturn) {
		this.tenYearReturn = tenYearReturn;
	}
	
	@JsonProperty("threeMonthReturn")
	private Double threeMonthReturn; 
	
	@JsonProperty("threeMonthReturn")
	public Double getThreeMonthReturn() {
		return threeMonthReturn;
	}
	@JsonProperty("threeMonthReturn")
	public void setThreeMonthReturn(Double threeMonthReturn) {
		this.threeMonthReturn = threeMonthReturn;
	}
	
	@JsonProperty("threeYearReturn")
	private Double threeYearReturn ;
	
	
	@JsonProperty("threeYearReturn")
	public Double getThreeYearReturn() {
		return threeYearReturn;
	}

	@JsonProperty("threeYearReturn")
	public void setThreeYearReturn(Double threeYearReturn) {
		this.threeYearReturn = threeYearReturn;
	}
	
	@JsonProperty("inceptionToDateReturn")
	private Double inceptionToDateReturn ;
	
	@JsonProperty("inceptionToDateReturn")
	public Double getInceptionToDateReturn() {
		return inceptionToDateReturn;
	}
	
	@JsonProperty("inceptionToDateReturn")
	public void setInceptionToDateReturn(Double inceptionToDateReturn) {
		this.inceptionToDateReturn = inceptionToDateReturn;
	}
	
	@JsonProperty("yearToDateReturn")
	private Double yearToDateReturn ;
	
	@JsonProperty("yearToDateReturn")
	public Double getYearToDateReturn() {
		return yearToDateReturn;
	}

	@JsonProperty("yearToDateReturn")
	public void setYearToDateReturn(Double yearToDateReturn) {
		this.yearToDateReturn = yearToDateReturn;
	}
	
	@JsonProperty("inceptionDate")
	private Date inceptionDate; 
	
	@JsonProperty("inceptionDate")
	public Date getInceptionDate() {
		return inceptionDate;
	}

	@JsonProperty("inceptionDate")
	public void setInceptionDate(Date inceptionDate) {
		this.inceptionDate = inceptionDate;
	}

	
	@JsonProperty("grossExpenseRatio")
	private Double  grossExpenseRatio ;
	
	@JsonProperty("grossExpenseRatio")
	public Double getGrossExpenseRatio() {
		return grossExpenseRatio;
	}
	@JsonProperty("grossExpenseRatio")
	public void setGrossExpenseRatio(Double grossExpenseRatio) {
		this.grossExpenseRatio = grossExpenseRatio;
	}
	
	@JsonProperty("grossExpenseAmount")
	private Money grossExpenseAmount ;
	
	@JsonProperty("grossExpenseAmount")
	public Money getGrossExpenseAmount() {
		return grossExpenseAmount;
	}
	@JsonProperty("grossExpenseAmount")
	public void setGrossExpenseAmount(Money grossExpenseAmount) {
		this.grossExpenseAmount = grossExpenseAmount;
	}
	
	@JsonProperty("netExpenseRatio")
	private Double netExpenseRatio ;
	
	@JsonProperty("netExpenseRatio")
	public Double getNetExpenseRatio() {
		return netExpenseRatio;
	}

	@JsonProperty("netExpenseRatio")
	public void setNetExpenseRatio(Double netExpenseRatio) {
		this.netExpenseRatio = netExpenseRatio;
	}
	
	@JsonProperty("netExpenseAmount")
	private Money netExpenseAmount ;
	
	@JsonProperty("netExpenseAmount")
	public Money getNetExpenseAmount() {
		return netExpenseAmount;
	}
	@JsonProperty("netExpenseAmount")
	public void setNetExpenseAmount(Money netExpenseAmount) {
		this.netExpenseAmount = netExpenseAmount;
	}

}


