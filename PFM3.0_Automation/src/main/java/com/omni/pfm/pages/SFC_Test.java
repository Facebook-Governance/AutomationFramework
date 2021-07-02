/*******************************************************************************
 * Copyright (c) 2018 Yodlee Inc. All Rights Reserved.
 * 
 * This software is the confidential and proprietary information of
 * Yodlee, Inc. Use is subject to license terms.
 * 
 * @author
 ******************************************************************************/
package com.omni.pfm.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.omni.pfm.utility.SeleniumUtil;



public class SFC_Test {
	static Logger logger = LoggerFactory.getLogger(SFC_Test.class);
	public static WebDriver d =null;
	//static WebElement wd;
	
	public SFC_Test(WebDriver d)
	{
		this.d=d;
	}
	
	public static WebElement getSpending()
	{
		return SeleniumUtil.getVisibileWebElement(d, "Spending", "DashboardPage", null);
	}
	
	public static WebElement getFinancialForecast()
	{
		return SeleniumUtil.getVisibileWebElement(d, "FinancialForecast", "DashboardPage", null);
	}
	
	public static WebElement closePopup()
	{
		return SeleniumUtil.getVisibileWebElement(d, "ClosePopup", "FinancialForecast", null);
	}
	
	public static WebElement getCard()
	{
		return SeleniumUtil.getVisibileWebElement(d, "LabelCard", "FinancialForecast", null);
	}
	
	public static WebElement getCreditCardINR()
	{
		return SeleniumUtil.getVisibileWebElement(d, "CreditCardInr", "FinancialForecast", null);
	}
	
	public static WebElement getCreditCardAmt()
	{
		return SeleniumUtil.getVisibileWebElement(d, "CAmmount", "FinancialForecast", null);
	}
	
	public static WebElement getCreditCardTest()
	{
		return SeleniumUtil.getVisibileWebElement(d, "CreditTest", "FinancialForecast", null);
	}
	
	public static WebElement getCreditTestAmt()
	{
		return SeleniumUtil.getVisibileWebElement(d, "CreditTestAmt", "FinancialForecast", null);
	}
	
	public static WebElement getYodleeIRA()
	{
		return SeleniumUtil.getVisibileWebElement(d, "YodleeIRA", "FinancialForecast", null);
	}

	public static WebElement getYodleeIRAAmt()
	{
		return SeleniumUtil.getVisibileWebElement(d, "YodleeITAAmt", "FinancialForecast", null);
	}
	
	public static WebElement getDashboard()
	{
		return SeleniumUtil.getVisibileWebElement(d, "DashBoard", "DashboardPage", null);
	}
	
	public static WebElement getbankAndCredit()
	{
		return SeleniumUtil.getVisibileWebElement(d, "LabelBankAndCredit", "FinancialForecast", null);
		
	}
	public static WebElement getNextButton()
	{
		return SeleniumUtil.getVisibileWebElement(d, "NextButton", "FinancialForecast", null);
	}
	public static WebElement getNextButton2()
	{
		return SeleniumUtil.getVisibileWebElement(d, "NextButton2", "FinancialForecast", null);
	}

}
