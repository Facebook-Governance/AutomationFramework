/*******************************************************************************
 * Copyright (c) 2018 Yodlee Inc. All Rights Reserved.
 * 
 * This software is the confidential and proprietary information of
 * Yodlee, Inc. Use is subject to license terms.
 * 
 * @author
 ******************************************************************************/
package com.omni.pfm.AccountSharing;

import java.awt.AWTException;

import org.json.JSONException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;

import com.omni.pfm.pages.Login.LoginPage;
import com.omni.pfm.pages.Login.LoginPage_SAML3;
import com.omni.pfm.utility.SeleniumUtil;

public class AccountSharing_Investment_Holdings_Loc {
	private static final Logger logger = LoggerFactory.getLogger(AccountSharing_Investment_Holdings_Loc.class);
	public static String advRefId, investorName, advisorName;
	LoginPage login;
	LoginPage_SAML3 SAML;
	public WebDriver d;
	String pageName, frameName;

	public AccountSharing_Investment_Holdings_Loc(WebDriver d) {
		this.d = d;
		pageName = "InvestmentHoldings";
		frameName = null;
		SAML = new LoginPage_SAML3();
		login = new LoginPage();
		advRefId = null;
		investorName = null;
		advisorName = null;
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

	public WebElement finappHeader() {
		// TODO Auto-generated method stub
		return SeleniumUtil.getWebElement(d, "finappHeader_IH", pageName, frameName);
	}
	
	public WebElement noDataMsg_IH() {
		// TODO Auto-generated method stub
		return SeleniumUtil.getWebElement(d, "noDataMsg_IH", pageName, frameName);
	}
	
	
	public WebElement ftueClose_IH() {
		// TODO Auto-generated method stub
		return SeleniumUtil.getWebElement(d, "ftueClose_IH", pageName, frameName);
	}
	
	
	public WebElement dataSection_IH() {
		// Data Section Box in Investment Holdings
		return SeleniumUtil.getWebElement(d, "dataSection_IH", pageName, frameName);
	}
	
	
	public WebElement noAcess_IH() {
		// Data Section Box in Investment Holdings
		return SeleniumUtil.getWebElement(d, "noAcess_IH", pageName, frameName);
	}
	
	public WebElement invData() {
		// TODO Auto-generated method stub
		return SeleniumUtil.getWebElement(d, "finappHeader_IH", pageName, frameName);
	}
	
	
}
