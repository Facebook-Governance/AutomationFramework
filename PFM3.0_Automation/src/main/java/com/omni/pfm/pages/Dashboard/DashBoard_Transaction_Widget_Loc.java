/**
* Copyright (c) 2018 Yodlee Inc. All Rights Reserved.
*
* This software is the confidential and proprietary information of
* Yodlee, Inc. Use is subject to license terms.
*
* @author Ashwin PM
*/
package com.omni.pfm.pages.Dashboard;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.omni.pfm.utility.SeleniumUtil;

public class DashBoard_Transaction_Widget_Loc {

	static Logger logger = LoggerFactory.getLogger(DashBoard_Transaction_Widget_Loc.class);
	public WebDriver d = null;

	public DashBoard_Transaction_Widget_Loc(WebDriver d) {
		this.d = d;
	}

	public List<WebElement> dashboardWidgetTxnDesc() {
		return SeleniumUtil.getWebElements(d, "TxnGenDescription_DashBoardWidget_DTW", "DashboardPage", null);
	}
	
	public List<WebElement> transactionFinAppTxnDesc() {
		return SeleniumUtil.getWebElements(d, "PostedTxnGenDescription_TxnPage_DTW", "DashboardPage", null);
	}
	
	public List<WebElement> transactionFinAppTxnBalance() {
		return SeleniumUtil.getWebElements(d, "PostedTxnGenBalance_TxnPage_DTW_DTW", "DashboardPage", null);
	}
	
	public WebElement projectedTxnContainer() {
		return SeleniumUtil.getVisibileWebElement(d, "ProjectedTxnsContWrap_DTW", "DashboardPage", null);
	}
	
	public List<WebElement> dashboardWidgetTxnAccNameAndNumber() {
		return SeleniumUtil.getWebElements(d, "TxnGenAccNameAndNumber_DashBoardWidget_DTW", "DashboardPage", null);
	}
	
	public List<WebElement> dashboardWidgetTxnAccBalance() {
		return SeleniumUtil.getWebElements(d, "TxnGenAccBalance_DashBoardWidget_DTW", "DashboardPage", null);
	}
	
	public Boolean verifyTwoDecimalsPresent(String amountToCheck) {
		
		String onlyDecimalPart = amountToCheck.substring(amountToCheck.indexOf(".")+1).trim();
		
		return onlyDecimalPart.length() == 2 ? true : false;
		
	}
	
	public WebElement txnFinAppHeader() {
		return SeleniumUtil.getVisibileWebElement(d, "TxnFinAppHeader_DTW", "DashboardPage", null);
	}
	
	public WebElement txnFinAppNoTxnsMessage() {
		return SeleniumUtil.getVisibileWebElement(d, "TxnFinAppNoTxnsMessage_DTW", "DashboardPage", null);
	}
	
	
	
	
}
