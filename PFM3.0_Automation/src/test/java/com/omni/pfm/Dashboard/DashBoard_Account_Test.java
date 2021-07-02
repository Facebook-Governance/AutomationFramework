/*******************************************************************************
 * Copyright (c) 2018 Yodlee Inc. All Rights Reserved.
 * 
 * This software is the confidential and proprietary information of
 * Yodlee, Inc. Use is subject to license terms.
 * 
 * @author
 ******************************************************************************/
package com.omni.pfm.Dashboard;

import java.util.List;
import java.util.concurrent.TimeUnit;
import org.openqa.selenium.WebElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.accessibility.examples.testng.webdriver.test.RunAccessibilityTest;
import com.omni.pfm.PageProcessor.PageParser;
import com.omni.pfm.pages.AccountAddition.AccountAddition;
import com.omni.pfm.pages.Dashboard.*;
import com.omni.pfm.pages.Login.*;
import com.omni.pfm.testBase.TestBase;
import com.omni.pfm.utility.PropsUtil;
import com.omni.pfm.utility.SeleniumUtil;

public class DashBoard_Account_Test extends TestBase {
	Account_Loc account;
	Spending_Loc spending;
	AccountAddition accAddition = new AccountAddition();
	private static final Logger logger = LoggerFactory.getLogger(DashBoard_Account_Test.class);
	public static float sum = 0;
	public static float total = 0;
	public static float tot = 0;

	@BeforeClass(alwaysRun = true)
	public void testInit() throws Exception {
		doInitialization("Dashboard-Accounts");
		account = new Account_Loc(d);
		spending = new Spending_Loc(d);
		d.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
	}

	@Test(description = "AT-76866,AT-76865,AT-76990:Verify login happenes successfully.", priority = 1, groups = {
			"Desktop" }, enabled = true)
	public void login() throws Exception {
		LoginPage.loginMain(d, loginParameter);
		accAddition.linkAccount();
		accAddition.addAggregatedAccount("shivaprasad_bhat.site16441.1", "site16441.1");
		SeleniumUtil.refresh(d);
		logger.info("****************Navidgated to Dashboard************");
	}

	@Test(description = "Verify DashBoard_Header,account header,By_Accounts_Type_Text in account widget,Link an account button.", priority = 2, groups = {
			"Desktop" }, enabled = true)
	public void verifyHeader() throws Exception {
		Assert.assertEquals(account.dashBoardHeader().getText(), PropsUtil.getDataPropertyValue("DashBoard_Header"));
		Assert.assertEquals(account.accountsHeader().getText(), PropsUtil.getDataPropertyValue("Accounts_Header1"));
		Assert.assertEquals(account.ByAccountTypeText().getText(),
				PropsUtil.getDataPropertyValue("By_Accounts_Type_Text"));
		Assert.assertTrue(account.linkAnotherAccountButton().isDisplayed());
		logger.info(
				"verified DashBoard_Header,account header,By_Accounts_Type_Text in account widget,Link an account button");
	}

	@Test(description = "AT-76989:Verify login happenes successfully.", priority = 3, groups = {
			"Desktop" }, enabled = false)
	public void VerifyLinkAnotherAccountButton() throws Exception {
		Assert.assertEquals(account.continueNonIQBankText().getText(),
				PropsUtil.getDataPropertyValue("Continue_To_Link_NonIQBank_Text"));
		logger.info(
				"verify Continue to link your Non-IQ Bank accounts and view your full financial picture in one place. text.");
	}

	@Test(description = "AT-76996:Verify in account type block dropdown is appearing or not..", priority = 4, groups = {
			"Desktop" }, enabled = true)
	public void VerifyDropDownIcon() throws Exception {
		Assert.assertTrue(account.DropDownIcon().isDisplayed());
		logger.info("verified account collapse icon is displaying or not in account widget.");
		logger.info(" verify all account type is displaying in proper order or not in account widget.");
		String[] list = PropsUtil.getDataPropertyValue("Account_Type1").split(PropsUtil.getDataPropertyValue("comma"));
		List<WebElement> s = account.accountType();
		for (int i = 0; i < s.size(); i++) {
			String text = s.get(i).getText();
			Assert.assertEquals(text, list[i]);
		}
		Assert.assertTrue(account.dropdown().isDisplayed(), "A");
		logger.info("verified in account type block dropdown is appearing or not.");
	}

	@Test(description = "AT-76975,AT-76976:Verify  all amounts in account widget for bank container", priority = 5, groups = {
			"Desktop" }, enabled = true)
	public void verifyTotalCashAmount() throws Exception {

		if (PageParser.isElementPresent("accountDropdown", "DashboardPage", null)) {
			SeleniumUtil.click(account.accountDropdown());
		}

		logger.info("verify all amounts in account widget for bank container");
		List<WebElement> l = account.CashAmount();
		for (int i = 0; i < l.size(); i++) {
			float total = SeleniumUtil.parseAmount(l.get(i).getText());
			sum = sum + total;
		}

		String amount = account.Amount().getText();
		float totalsum = SeleniumUtil.parseAmount(amount);
		Assert.assertEquals(Math.round(sum), Math.round(totalsum));
	}

	@Test(description = "Verify account type in accounts finapp on dashboard page", priority = 6, groups = {
			"Desktop" }, enabled = true)
	public void verifyAccountType() throws Exception {
		if (PageParser.isElementPresent("expand_All", "DashboardPage", null)) {
			SeleniumUtil.click(account.expand_All());
			SeleniumUtil.waitForPageToLoad(2000);
		}
		String[] list = PropsUtil.getDataPropertyValue("Account_Type_Dashboard")
				.split(PropsUtil.getDataPropertyValue("comma"));
		List<WebElement> l = account.accountType1();
		for (int i = 0; i < l.size(); i++) {
			String text = l.get(i).getText().trim();
			Assert.assertEquals(text, list[i]);
			logger.info("verified account type in accounts finapp on dashboard page");
		}
	}

	@Test(description = "AT-76984:verify account names are displaying or not", priority = 7, groups = {
			"Desktop" }, enabled = true)
	public void VerifyAccountName() throws Exception {

		logger.info("verify account names are displaying or not");
		String[] list = PropsUtil.getDataPropertyValue("Account_Name_Dashboard")
				.split(PropsUtil.getDataPropertyValue("comma"));
		List<WebElement> l = account.accountName();
		for (int i = 0; i < l.size(); i++) {
			String text = l.get(i).getText();
			Assert.assertEquals(text, list[i]);
		}
	}

	@Test(description = "AT-76987:Verify after clicking on view all account button it should navigate to accounts finapp.", priority = 8, groups = {
			"Desktop" }, enabled = true)
	public void verifyViewAllAccounts() throws Exception {

		logger.info("verify view all accounts button is displaying or not.");
		Assert.assertTrue(account.viewAllAccountButton().isDisplayed());
		SeleniumUtil.click(account.viewAllAccountButton());
		SeleniumUtil.waitForPageToLoad();

		logger.info("Verify after clicking on view all account button it should navigate to accounts finapp.");

		Assert.assertTrue(account.accountsheader().isDisplayed());
		SeleniumUtil.click(spending.BackToDashBoard());
		SeleniumUtil.waitForPageToLoad();

	}

	// Updated By MRQA for deeplinking cobrands where there is no header displayed.
	@Test(description = "AT-76993,AT-76997:Verify after clicking on view all account button it should navigate to accounts finapp.", priority = 8, groups = {
			"Desktop" }, enabled = true)
	public void verifyViewAllAccountsdeeplink() throws Exception {

		logger.info("verify view all accounts button is displaying or not.");
		Assert.assertTrue(account.viewAllAccountButton().isDisplayed());
		SeleniumUtil.click(account.viewAllAccountButton());
		SeleniumUtil.waitForPageToLoad();

		logger.info("Verify after clicking on view all account button it should navigate to accounts finapp.");

		SeleniumUtil.click(spending.BackToDashBoard());
		SeleniumUtil.waitForPageToLoad();

	}

	@Test(description = "AT-76868:Verify all amounts in account widget", priority = 9, groups = {
			"Desktop" }, enabled = true)
	public void verifyTotalAmount() throws Exception {
		if (PageParser.isElementPresent("expand_All", "DashboardPage", null)) {
			SeleniumUtil.click(account.expand_All());
			SeleniumUtil.waitForPageToLoad(2000);
		}
		List<WebElement> l = account.CashAmount();
		sum = 0;
		for (int i = 0; i < l.size(); i++) {
			String amt = l.get(i).getText();
			float total1 = SeleniumUtil.parseAmount(amt);
			sum = sum + total1;
		}
		SeleniumUtil.click(account.viewAllAccountButton());
		SeleniumUtil.waitForPageToLoad();

		List<WebElement> s = account.Amount1();
		total = 0;
		for (int j = 0; j < s.size(); j++) {
			String amount = s.get(j).getText();
			float totalsum = SeleniumUtil.parseAmount(amount);
			total = total + totalsum;
		}
		Assert.assertEquals(sum, total);
		SeleniumUtil.refresh(d);
		logger.info(" verified all amounts in account widget");
	}

	@AfterClass
	public void checkAccessibility() {
		try {
			RunAccessibilityTest rat = new RunAccessibilityTest(this.getClass().getName());
			rat.testAccessibility(d);

		} catch (Exception e) {

		}
	}

}
