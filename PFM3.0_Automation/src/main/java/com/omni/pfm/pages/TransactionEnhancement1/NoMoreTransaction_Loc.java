/**
* Copyright (c) 2019 Yodlee Inc. All Rights Reserved. 
*
* This software is the confidential and proprietary information of
* Yodlee, Inc. Use is subject to license terms.
*
* @author sbhat1
*/
package com.omni.pfm.pages.TransactionEnhancement1;

import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.omni.pfm.utility.SeleniumUtil;

public class NoMoreTransaction_Loc 
{
	static Logger logger = LoggerFactory.getLogger(NoMoreTransaction_Loc.class);
	public WebDriver d = null;
	static String pageName = "Transaction"; 
	static String frameName = null;
	public NoMoreTransaction_Loc(WebDriver d) {
		this.d = d;
	}
	public WebElement LoadMoreButton()
	{
		return SeleniumUtil.getWebElement(d, "LoadMoreButton", pageName, frameName);
	}
	public WebElement NoMoreTransactionText()
	{
		return SeleniumUtil.getWebElement(d, "NoMoreTransactionText", pageName, frameName);
	}
	public WebElement AcctDropdown()
	{
		return SeleniumUtil.getWebElement(d, "AcctDropdown", pageName, frameName);
	}
	public WebElement AllAccounts()
	{
		return SeleniumUtil.getWebElement(d, "AllAccounts", pageName, frameName);
	}
	public WebElement SelectAcct()
	{
		return SeleniumUtil.getWebElement(d, "SelectAcct", pageName, frameName);
	}
	public WebElement TimeFilter()
	{
		return SeleniumUtil.getWebElement(d, "TimeFilter", pageName, frameName);
	}
	public WebElement ThisMonth()
	{
		return SeleniumUtil.getWebElement(d, "ThisMonth", pageName, frameName);
	}
	public WebElement Categorydropdown()
	{
		return SeleniumUtil.getWebElement(d, "Categorydropdown", pageName, frameName);
	}
	public WebElement Allcategory()
	{
		return SeleniumUtil.getWebElement(d, "Allcategory", pageName, frameName);
	}
	public WebElement Selectcategory()
	{
		return SeleniumUtil.getWebElement(d, "Selectcategory", pageName, frameName);
	}

  		
	public WebElement TransactionCard()
	{
		return SeleniumUtil.getWebElement(d, "transactionCard", pageName, frameName);
	}
	public WebElement Dashboard()
	{
		return SeleniumUtil.getWebElement(d, "dashboard", pageName, frameName);
	}
	public WebElement Accounts()
	{
		return SeleniumUtil.getWebElement(d, "Accounts", pageName, frameName);
	}
	public WebElement AccountRow()
	{
		return SeleniumUtil.getWebElement(d, "AccountRow", pageName, frameName);
	}
	public WebElement moveToTop()
	{
		return SeleniumUtil.getWebElement(d, "moveToTop", pageName, frameName);
	}
}
