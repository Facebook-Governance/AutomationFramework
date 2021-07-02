/*******************************************************************************
 * Copyright (c) 2018 Yodlee Inc. All Rights Reserved.
 * 
 * This software is the confidential and proprietary information of
 * Yodlee, Inc. Use is subject to license terms.
 * 
 * @author
 ******************************************************************************/
package com.omni.pfm.Dashboard;

import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import com.omni.pfm.pages.Dashboard.*;
import com.omni.pfm.pages.Login.LoginPage_SAML1;
import com.omni.pfm.testBase.TestBase;
import com.omni.pfm.utility.PropsUtil;
import com.omni.pfm.utility.SeleniumUtil;

public class Dashboard_Integration_Test extends TestBase {

	private static final Logger logger = LoggerFactory.getLogger(Dashboard_Integration_Test.class);
	Account_Loc account;
	public static float sum = 0;
	public static float total = 0;
	public static float tot = 0;
	Spending_Loc spending;
	Integration_Loc integration;

	@BeforeClass(alwaysRun = true)
	public void testInit() throws Exception {
		doInitialization("Dashboard-integration");
		integration = new Integration_Loc(d);
		account = new Account_Loc(d);
		spending = new Spending_Loc(d);
		d.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
	}

	@Test(description = "Verify login happenes successfully.", priority = 1, groups = { "Desktop" })
	public void login() throws Exception {
		LoginPage_SAML1.loginExistingUser(d, "PFM1514375151677", "Password#");
		SeleniumUtil.refresh(d);
		SeleniumUtil.waitForPageToLoad();
		logger.info("****************Navidgated to Dashboard************");
	}

	@Test(description = "Verify Subheader,link account button, create budget text,budget description", priority = 2, groups = {
			"Desktop" }, enabled = true)
	public void verifyInvestmentAccount() throws Exception {

		Assert.assertEquals(integration.HoldingHeader().getText(), PropsUtil.getDataPropertyValue("Holding_Header"));
		Assert.assertEquals(integration.SubHeader().getText(), PropsUtil.getDataPropertyValue("Subheader"));
		Assert.assertEquals(integration.Header1().getText(),
				PropsUtil.getDataPropertyValue("Budget_Link_Account_Header"));
		Assert.assertEquals(integration.Description().getText(),
				PropsUtil.getDataPropertyValue("Budget_Link_Account_Description"));
		Assert.assertEquals(integration.LinkAccountText().getText(),
				PropsUtil.getDataPropertyValue("Budget_Link_Account_Button"));
		logger.info(
				"**********************verified Subheader,link account button, create budget text,budget description********************");
	}

	@Test(description = "Delete the credit card account", priority = 3, groups = {
			"Desktop" }, enabled = false)
	public void verifySpendingWidget() throws Exception {

		// SeleniumUtil.click(spending.OneMonthText());
		// Assert.assertEquals(integration.NoDataHeader().getText(),PropsUtil.getDataPropertyValue("Spending_No_Data_Description"));
		// Assert.assertEquals(integration.NoDataDescription().getText(),PropsUtil.getDataPropertyValue("Spending_No_Data_Description1"));

		SeleniumUtil.click(account.viewAllAccountButton());
		SeleniumUtil.waitForPageToLoad();
		SeleniumUtil.click(integration.MoreIcon());
		SeleniumUtil.click(integration.AccountSettingsText());
		SeleniumUtil.waitForPageToLoad();
		SeleniumUtil.click(integration.dagBank_Accounts());
		SeleniumUtil.click(integration.DeleteIcon());
		SeleniumUtil.waitForPageToLoad();
		SeleniumUtil.click(integration.CheckBox());
		SeleniumUtil.click(integration.DeleteButton());
		SeleniumUtil.waitForPageToLoad(10000);
		logger.info("**********************Deleted the credit card account********************");
	}

	@Test(description = "Verify the Budget LinkAccount Header,Transaction No Data Description ,Budget Link Account Button", priority = 4, groups = {
			"Desktop" }, enabled = true)
	public void verifyTransactionWidget() throws Exception {

		SeleniumUtil.refresh(d);
		SeleniumUtil.waitForPageToLoad();
		Assert.assertEquals(integration.TransactionLinkAccountHeader().getText(),
				PropsUtil.getDataPropertyValue("Budget_Link_Account_Header"));
		Assert.assertEquals(integration.TransactionDescription().getText(),
				PropsUtil.getDataPropertyValue("Transaction_No_Data_Description"));
		Assert.assertTrue(integration.TransactionLinkAccBtn().isDisplayed());
		Assert.assertEquals(integration.TransactionLinkAccBtn().getText(),
				PropsUtil.getDataPropertyValue("Budget_Link_Account_Button1"));
		logger.info(
				"**********************Verified the Budget_Link_Account_Header,Transaction_No_Data_Description ,Budget_Link_Account_Button1********************");

	}

	@Test(description = "Verify the Budget LinkAccount Header,Transaction No Data Description", priority = 5, groups = {
			"Desktop" }, enabled = true)
	public void verifySpendingLinkAccount() throws Exception {

		Assert.assertEquals(integration.SpendingLinkAccHeader().getText(),
				PropsUtil.getDataPropertyValue("Budget_Link_Account_Header"));
		Assert.assertEquals(integration.SpendingLinkAccDescription().getText(),
				PropsUtil.getDataPropertyValue("Spending_Link_Account_Description"));
		Assert.assertTrue(integration.SpendingLinkAccBtn().isDisplayed());
		Assert.assertEquals(integration.SpendingLinkAccBtn().getText(),
				PropsUtil.getDataPropertyValue("Budget_Link_Account_Button1"));

	}
	
}
