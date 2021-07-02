/**
* Copyright (c) 2018 Yodlee Inc. All Rights Reserved.
*
* This software is the confidential and proprietary information of
* Yodlee, Inc. Use is subject to license terms.
*
* @author Renuka K
*/
package com.omni.pfm.pages.TransactionEnhancement1;

import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.omni.pfm.utility.SeleniumUtil;

public class Transaction_Details_Loc {
	static Logger logger = LoggerFactory.getLogger(Transaction_DashBoard_Integration_Loc.class);
	public WebDriver d = null;
	static String pageName = "Transaction"; // Page Name
	static String frameName = null;

	public Transaction_Details_Loc(WebDriver d) {
		this.d = d;
	}
public WebElement transactionLinkAccount()
{
	return SeleniumUtil.getWebElement(d, "transactionLinkAccount", pageName, frameName);
}

public WebElement transactionNoDataMessageHeader()
{
	return SeleniumUtil.getWebElement(d, "transactionNoDataMessageHeader", pageName, frameName);
}

public WebElement transactionNoDataMessage()
{
	return SeleniumUtil.getWebElement(d, "transactionNoDataMessage", pageName, frameName);
}

public WebElement transactionHeader()
{
	return SeleniumUtil.getWebElement(d, "transactionHeader", pageName, frameName);
}
public WebElement showmoretransactionHide()
{
	return SeleniumUtil.getWebElement(d, "showmoretransactionHide", pageName, frameName);
}
public WebElement transctionnoMoreTransaction()
{
	return SeleniumUtil.getWebElement(d, "transctionnoMoreTransaction", pageName, frameName);
}

public List<WebElement> transctionnoDetailsExpanded()
{
	return SeleniumUtil.getWebElements(d, "transctionnoDetailsExpanded", pageName, frameName);
}
}
