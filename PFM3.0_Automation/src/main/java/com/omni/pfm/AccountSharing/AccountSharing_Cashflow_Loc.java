/*******************************************************************************
 * Copyright (c) 2018 Yodlee Inc. All Rights Reserved.
 * 
 * This software is the confidential and proprietary information of
 * Yodlee, Inc. Use is subject to license terms.
 * 
 * @author
 ******************************************************************************/
package com.omni.pfm.AccountSharing;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.omni.pfm.pages.Login.LoginPage;
import com.omni.pfm.pages.Login.LoginPage_SAML3;
import com.omni.pfm.utility.SeleniumUtil;

public class AccountSharing_Cashflow_Loc {
	private static final Logger logger = LoggerFactory.getLogger(AccountSharing_Cashflow_Loc.class);
	WebDriver d;
	String pageName = null, frameName = null;
	LoginPage_SAML3 SAML;
	LoginPage login;

	public AccountSharing_Cashflow_Loc(WebDriver d) {
		this.d = d;
		pageName = "CashFlow";
		SAML = new LoginPage_SAML3();
		login = new LoginPage();
	}

	public WebElement no_data_screen_cf() {
		return SeleniumUtil.getWebElement(d, "no_data_screen_cf", pageName, frameName);
	}

	public WebElement get_sidebar_value() {
		return SeleniumUtil.getWebElement(d, "advisor_details", pageName, frameName);
	}

	public void loginAsAdvisor(String advisorName, String investorName, String finapp) {
		logger.info(">>>>> Logging in as Advisor..");
		SAML.login2(d, advisorName, investorName, finapp);
		SeleniumUtil.waitForPageToLoad();

	}

	public void ftue_continue() {
		try {
			logger.info(">>>>> Clicking on continue");
			d.findElement(By.cssSelector("#ftue-continue")).click();
			SeleniumUtil.waitForPageToLoad(3000);
			d.findElement(By.xpath("//span[text()='GO TO MY CASH FLOW']")).click();
			SeleniumUtil.waitForPageToLoad(2000);
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	public List<WebElement> groups_names() {
		return SeleniumUtil.getWebElements(d, "groups_names_cf", pageName, frameName);
	}

	public WebElement account_dropdown() {
		return SeleniumUtil.getWebElement(d, "account_dropdown_cf", pageName, frameName);
	}

	public WebElement checkbox_forecast() {
		return SeleniumUtil.getWebElements(d, "checkbox_forecast_cf", pageName, frameName).get(2);
	}
	
	public WebElement no_access_screen() {
		return SeleniumUtil.getVisibileWebElement(d, "no_access_screen_cf", pageName, frameName);
	}
}
