/*******************************************************************************
 * Copyright (c) 2018 Yodlee Inc. All Rights Reserved.
 * 
 * This software is the confidential and proprietary information of
 * Yodlee, Inc. Use is subject to license terms.
 * 
 * @author
 ******************************************************************************/
package com.omni.pfm.pages.Dashboard;

import org.openqa.selenium.WebDriver; 
import org.openqa.selenium.WebElement;
import com.omni.pfm.utility.SeleniumUtil;


public class Integration_Loc {
	
	public WebDriver d;
	public static String pageName="DashboardPage";
	public static String frameName=null;

	public Integration_Loc(WebDriver d) {
		this.d= d;
	}
	public WebElement HoldingHeader() {
		return SeleniumUtil.getWebElement(d, "header_IL", pageName, frameName);
	}
	public WebElement SubHeader() {
		return SeleniumUtil.getWebElement(d, "subheader_IL", pageName, frameName);
	}
	public WebElement Header1() {
		return SeleniumUtil.getWebElement(d, "header1_IL", pageName, frameName);
	}
	public WebElement Description() {
		return SeleniumUtil.getWebElement(d, "description_IL", pageName, frameName);
	}
	public WebElement LinkAccountButton() {
		return SeleniumUtil.getWebElement(d, "button_IL", pageName, frameName);
	}
	public WebElement NoDataHeader() {
		return SeleniumUtil.getWebElement(d, "descr_IL", pageName, frameName);
	}
	public WebElement NoDataDescription() {
		return SeleniumUtil.getWebElement(d, "descri_IL", pageName, frameName);
	}
	public WebElement MoreIcon() {
		return SeleniumUtil.getWebElement(d, "icon_IL", pageName, frameName);
	}
	public WebElement AccountSettingsText() {
		return SeleniumUtil.getWebElement(d, "text_IL", pageName, frameName);
	}

	public WebElement DeleteIcon() {
		return SeleniumUtil.getWebElement(d, "delete_IL", pageName, frameName);
	}
	public WebElement CheckBox() {
		return SeleniumUtil.getWebElement(d, "checkbx_IL", pageName, frameName);
	}
	public WebElement DeleteButton() {
		return SeleniumUtil.getWebElement(d, "buttn_IL", pageName, frameName);
	}
	public WebElement TransactionLinkAccountHeader() {
		return SeleniumUtil.getWebElement(d, "header2_IL", pageName, frameName);
	}
	public WebElement TransactionDescription() {
		return SeleniumUtil.getWebElement(d, "trans_IL", pageName, frameName);
	}
	public WebElement TransactionLinkAccBtn() {
		return SeleniumUtil.getWebElement(d, "btn_IL", pageName, frameName);
	}
	public WebElement SpendingLinkAccHeader() {
		return SeleniumUtil.getWebElement(d, "text1_IL", pageName, frameName);
	}
	public WebElement SpendingLinkAccDescription() {
		return SeleniumUtil.getWebElement(d, "desc_IL", pageName, frameName);
	}
	public WebElement SpendingLinkAccBtn() {
		return SeleniumUtil.getWebElement(d, "bttn_IL", pageName, frameName);
	}
	public WebElement InvestmentHoldingLinkAccHeader() {
		return SeleniumUtil.getWebElement(d, "text2_IL", pageName, frameName);
	}
	public WebElement InvestmentHoldingLinkAccDescription() {
		return SeleniumUtil.getWebElement(d, "description1_IL", pageName, frameName);
	}
	public WebElement InvestmentHoldingLinkAccBtn() {
		return SeleniumUtil.getWebElement(d, "btn1_IL", pageName, frameName);
	}
	public WebElement LinkAccountText() {
		return SeleniumUtil.getWebElement(d, "text4_IL", pageName, frameName);
	}
	public WebElement dagBank_Accounts() {
		return SeleniumUtil.getWebElement(d, "dagBank_Accounts", pageName, frameName);
	}

}
