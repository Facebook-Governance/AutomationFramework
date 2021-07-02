/*******************************************************************************
 * Copyright (c) 2018 Yodlee Inc. All Rights Reserved.
 * 
 * This software is the confidential and proprietary information of
 * Yodlee, Inc. Use is subject to license terms.
 * 
 * @author
 ******************************************************************************/
package com.omni.pfm.pages.Dashboard;

import java.awt.AWTException; 
import java.util.List;

import org.json.JSONException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;

import com.omni.pfm.PageProcessor.PageParser;
import com.omni.pfm.pages.Login.LoginPage;
import com.omni.pfm.pages.Login.LoginPage_SAML3;
import com.omni.pfm.utility.SeleniumUtil;
/**
 * Copyright (c) 2018 Yodlee Inc. All Rights Reserved.
 *
 * This software is the confidential and proprietary information of Yodlee, Inc.
 * Use is subject to license terms.
 *
 * @author Shivaprasad
 */
public class AccountSharing_Loc {
	private static final Logger logger = LoggerFactory.getLogger(AccountSharing_Loc.class);
	WebDriver d;
	String pageName, frameName;
	public static String advRefId, investorName, advisorName;
	LoginPage login;
	LoginPage_SAML3 SAML;
	Account_Loc account;

	public AccountSharing_Loc(WebDriver d) {
		this.d = d;
		pageName = "DashboardPage";
		frameName = null;
		SAML = new LoginPage_SAML3();
		account = new Account_Loc(d);
	}

	public WebElement finappHeader() {
		return SeleniumUtil.getVisibileWebElement(d, "finapHeader", "DashboardPage", frameName);
	}

	public String getAdvisorReferanceID() throws AWTException, InterruptedException, JSONException {
		logger.info(">>>>> Getting a unique referance id..");
		boolean advId = false;
		advRefId = SAML.getRefrenceId();

		if (advRefId != null) {
			advId = true;
		}

		Assert.assertTrue(advId, ">>>>> Advisor referance Id not created.");
		return advRefId;

	}

	public String getInvestorName() {
		boolean invName = false;
		logger.info(">>>>> Getting a unique investor name..");
		investorName = SAML.getInvestorUserName();
		if (investorName != null) {
			invName = true;
		}

		Assert.assertTrue(invName, ">>>>> Advisor referance Id not created.");
		return investorName;

	}

	public WebElement transactionWidgetHeader() {
		return SeleniumUtil.getWebElement(d, "transactionWidgetHeader", pageName, frameName);

	}

	public WebElement transactionDetails() {
		return SeleniumUtil.getWebElement(d, "transactionDetails", pageName, frameName);

	}

	public List<WebElement> individualTransactions() {
		return SeleniumUtil.getWebElements(d, "individualTransactions", pageName, frameName);
	}

	public List<WebElement> accountName() {
		return SeleniumUtil.getWebElements(d, "accounts_details_dashboard_widget", pageName, frameName);
		

	}

//Added by Mallika for Group Stickyness 
	
	public WebElement Dashboard_ExpandAll() {
		return SeleniumUtil.getWebElement(d, "expand_All", "DashboardPage", null);

	}
	public boolean isDash_ExpandAll_mob_Present() {
		// TODO Auto-generated method stub
		return PageParser.isElementPresent("expand_All", "DashboardPage", null);

	}
	public boolean isDash_AccountName_Mob_Present() {
		// TODO Auto-generated method stub
		return PageParser.isElementPresent("Dashboard_AccountName_mob", "AccountsPage", null);

	}
	public WebElement Dashboard_AccountName_mob() {
		return SeleniumUtil.getWebElement(d, "Dashboard_AccountName_mob", "AccountsPage", null);

	}



}
