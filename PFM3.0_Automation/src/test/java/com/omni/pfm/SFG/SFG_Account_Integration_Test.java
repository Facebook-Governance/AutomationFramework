/*******************************************************************************
 * Copyright (c) 2018 Yodlee Inc. All Rights Reserved.
 * 
 * This software is the confidential and proprietary information of
 * Yodlee, Inc. Use is subject to license terms.
 * 
 * @author
 ******************************************************************************/
package com.omni.pfm.SFG;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.Keys;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.omni.pfm.PageProcessor.PageParser;
import com.omni.pfm.pages.AccountAddition.AccountAddition;
import com.omni.pfm.pages.Login.L1NodeLogin;
import com.omni.pfm.pages.Login.LoginPage;
import com.omni.pfm.pages.SFG.SFG_CreateGoal_GetStarted_Loc;
import com.omni.pfm.pages.SFG.SFG_LandingPage_Loc;
import com.omni.pfm.pages.SFG.SFG_createGoalEdit_Loc;
import com.omni.pfm.testBase.TestBase;
import com.omni.pfm.utility.PropsUtil;
import com.omni.pfm.utility.SeleniumUtil;

public class SFG_Account_Integration_Test extends TestBase {
	private static final Logger logger = LoggerFactory.getLogger(SFG_CreateGoal_FrequencyAmountFlow_Test.class);

	SFG_LandingPage_Loc SFG;
	SFG_createGoalEdit_Loc goalEdit;
	SFG_CreateGoal_GetStarted_Loc getStart;
	AccountAddition accountAdd;

	@BeforeClass(alwaysRun = true)
	public void testInit() throws Exception {
		doInitialization("SFG_Account_Integration");
		SFG = new SFG_LandingPage_Loc(d);

		goalEdit = new SFG_createGoalEdit_Loc(d);
		getStart = new SFG_CreateGoal_GetStarted_Loc(d);
		new L1NodeLogin();
		accountAdd = new AccountAddition();
		d.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);

		LoginPage.loginMain(d, loginParameter);
		SeleniumUtil.waitForPageToLoad(2000);
		d.navigate().refresh();
		SeleniumUtil.waitForPageToLoad(2000);
		PageParser.forceNavigate("SFG", d);
		SeleniumUtil.waitForPageToLoad(5000);
		getStart.getStartAddAccount();
		SeleniumUtil.waitForPageToLoad(10000);
		accountAdd.addAggregatedAccount("SFCCard2.creditCard1", "creditCard1");
		SeleniumUtil.waitForPageToLoad();

	}

	@Test(description = "AT-58619:If the user has no eligible accounts linked, he should be shown the Link account screen.", groups = { "DesktopBrowsers" }, priority = 1)
	public void verifyaddingCardAccount() throws Exception {
		PageParser.forceNavigate("SFG", d);
		SeleniumUtil.waitForPageToLoad(10000);
		Assert.assertTrue(getStart.sfgLinkAccount().isDisplayed());
	}

	@Test(description = "AT-58687:If the user has accounts linked but no eligible accounts, and no goals, the system should show him the 'Link Account' option on the widget.", groups = {
			"DesktopBrowsers" }, dependsOnMethods = { "verifyaddingCardAccount" }, priority = 2)
	public void verifyNoEligibelAccountInDashBaord() throws Exception {
		logger.info(
				"AT-58687:If the user has accounts linked but no eligible accounts, and no goals, the system should show him the 'Link Account' option on the widget.");
		d.navigate().refresh();
		SeleniumUtil.waitForPageToLoad(5000);
		Assert.assertEquals(SFG.SFGDashBoardMessage().getText(),
				PropsUtil.getDataPropertyValue("SFGDashBoardNoEligibelAccountMessage"));

	}

	@Test(description = "AT-16647:Verify that only accounts that have a balance greater than 0 should be populated in the Account Popup", groups = {
			"DesktopBrowsers" }, dependsOnMethods = { "verifyNoEligibelAccountInDashBaord" }, priority = 3)
	public void verifyaddingZeroBalanceAccount() throws Exception {
		LoginPage.loginMain(d, loginParameter);
		d.navigate().refresh();
		SeleniumUtil.waitForPageToLoad(2000);
		PageParser.forceNavigate("SFG", d);
		SeleniumUtil.waitForPageToLoad(5000);
		getStart.getStartAddAccount();
		SeleniumUtil.waitForPageToLoad(10000);
		accountAdd.addAggregatedAccount("pfmprodcf.bank5", "bank5");
		PageParser.forceNavigate("SFG", d);
		SeleniumUtil.waitForPageToLoad(10000);
		SFG.step2LandingPageFTUE(PropsUtil.getDataPropertyValue("SFGGoalName1"),
				PropsUtil.getDataPropertyValue("SFGGoalAmount1"), 0,
				PropsUtil.getDataPropertyValue("SFGFrequencyAmount1"));
		Assert.assertEquals(SFG.AccountdropDownField().getAttribute("value"),
				PropsUtil.getDataPropertyValue("SFGDefauldCardAccountValue"));

	}

	@Test(description = "AT-16627,AT-16644::Verify that by default the account field should be auto-filled with the user's linked checking account if their savings accounts are not linked.", groups = {
			"DesktopBrowsers" }, dependsOnMethods = { "verifyaddingZeroBalanceAccount" }, priority = 4)
	public void verifyaddingCheckingAccount() throws Exception {
		logger.info("AT-16644:Verify that Checking Accounts are valid Account Types.");
		LoginPage.loginMain(d, loginParameter);
		d.navigate().refresh();
		SeleniumUtil.waitForPageToLoad(2000);
		PageParser.forceNavigate("SFG", d);
		SeleniumUtil.waitForPageToLoad(5000);
		getStart.getStartAddAccount();
		SeleniumUtil.waitForPageToLoad(10000);
		accountAdd.addAggregatedAccount("SFCCard2.bank1", "bank1");
		PageParser.forceNavigate("SFG", d);
		SeleniumUtil.waitForPageToLoad(10000);
		SFG.step2LandingPageFTUE(PropsUtil.getDataPropertyValue("SFGGoalName1"),
				PropsUtil.getDataPropertyValue("SFGGoalAmount1"), 0,
				PropsUtil.getDataPropertyValue("SFGFrequencyAmount1"));
		Assert.assertEquals(SFG.AccountdropDownField().getAttribute("value"),
				PropsUtil.getDataPropertyValue("SFGDefauldAccountValue"));
	}

	@Test(description = "AT-16625:Verify that by default the account field should be auto-filled with the user's Savings account if their savings accounts are linked.", groups = {
			"DesktopBrowsers" }, priority = 5)
	public void verifyaddingSavingAccount() throws Exception {
		logger.info("AT-16645:Verify that Savings Accounts are valid Account Types.");
		LoginPage.loginMain(d, loginParameter);
		d.navigate().refresh();
		SeleniumUtil.waitForPageToLoad(2000);
		PageParser.forceNavigate("SFG", d);
		SeleniumUtil.waitForPageToLoad(5000);
		getStart.getStartAddAccount();
		SeleniumUtil.waitForPageToLoad(10000);
		accountAdd.addAggregatedAccount("SFCCard2.bank2", "bank2");
		PageParser.forceNavigate("SFG", d);
		SeleniumUtil.waitForPageToLoad(10000);
		SFG.step2LandingPageFTUE(PropsUtil.getDataPropertyValue("SFGGoalName1"),
				PropsUtil.getDataPropertyValue("SFGGoalAmount1"), 0,
				PropsUtil.getDataPropertyValue("SFGFrequencyAmount1"));
		Assert.assertEquals(SFG.AccountdropDownField().getAttribute("value"),
				PropsUtil.getDataPropertyValue("SFGDefauldAccountValue"));
	}

	@Test(description = "AT-16626,AT-16643::Verify that by default the account field should be auto-filled with the user's linked investment account if their savings and checking accounts are not linked.", groups = {
			"DesktopBrowsers" }, priority = 6)
	public void verifyaddingInvestmentAccount() throws Exception {
		logger.info("AT-16643::Verify that Investment Accounts are valid Account Types.");
		LoginPage.loginMain(d, loginParameter);
		d.navigate().refresh();
		SeleniumUtil.waitForPageToLoad(2000);
		PageParser.forceNavigate("SFG", d);
		SeleniumUtil.waitForPageToLoad(5000);
		getStart.getStartAddAccount();
		SeleniumUtil.waitForPageToLoad(10000);
		accountAdd.addAggregatedAccount("psqa.Investment1", "Investment1");
		PageParser.forceNavigate("SFG", d);
		SeleniumUtil.waitForPageToLoad(10000);
		SFG.step2LandingPageFTUE(PropsUtil.getDataPropertyValue("SFGGoalName1"),
				PropsUtil.getDataPropertyValue("SFGGoalAmount1"), 0,
				PropsUtil.getDataPropertyValue("SFGFrequencyAmount1"));
		Assert.assertEquals(SFG.AccountdropDownField().getAttribute("value"),
				PropsUtil.getDataPropertyValue("SFGDefauldInvestmentAccountValue"));
	}

	@Test(description = "AT-16642:Verify that Deposit Accounts are valid Account Types.", groups = {
			"DesktopBrowsers" }, priority = 7)
	public void verifyaddingDepositAccount() throws Exception {
		LoginPage.loginMain(d, loginParameter);
		d.navigate().refresh();
		SeleniumUtil.waitForPageToLoad(2000);
		PageParser.forceNavigate("SFG", d);
		SeleniumUtil.waitForPageToLoad(5000);
		getStart.getStartAddAccount();
		SeleniumUtil.waitForPageToLoad(10000);
		accountAdd.addAggregatedAccount("SFG2.bank1", "bank1");
		PageParser.forceNavigate("SFG", d);
		SeleniumUtil.waitForPageToLoad(10000);
		SFG.step2LandingPageFTUE(PropsUtil.getDataPropertyValue("SFGGoalName1"),
				PropsUtil.getDataPropertyValue("SFGGoalAmount1"), 0,
				PropsUtil.getDataPropertyValue("SFGFrequencyAmount1"));
		Assert.assertEquals(SFG.AccountdropDownField().getAttribute("value"),
				PropsUtil.getDataPropertyValue("SFGDefauldDepositAccountValue"));
	}

	@Test(description = "AT-16625:Verify that by default the account field should be auto-filled with the user's Savings account if their savings accounts are linked.", groups = {
			"DesktopBrowsers" }, priority = 8)
	public void verifyaddingAllTypeAccount() throws Exception {
		LoginPage.loginMain(d, loginParameter);
		d.navigate().refresh();
		SeleniumUtil.waitForPageToLoad(2000);
		PageParser.forceNavigate("SFG", d);
		SeleniumUtil.waitForPageToLoad(5000);
		getStart.getStartAddAccount();
		SeleniumUtil.waitForPageToLoad(10000);
		accountAdd.addAggregatedAccount("SFCCard1.site16441.2", "site16441.2");
		PageParser.forceNavigate("SFG", d);
		SeleniumUtil.waitForPageToLoad(10000);
		Assert.assertTrue(goalEdit.startGoalGetStartButton().isDisplayed());
		SFG.step2LandingPageFTUE(PropsUtil.getDataPropertyValue("SFGGoalName1"),
				PropsUtil.getDataPropertyValue("SFGGoalAmount1"), 0,
				PropsUtil.getDataPropertyValue("SFGFrequencyAmount1"));
		Assert.assertEquals(SFG.AccountdropDownField().getAttribute("value"),
				PropsUtil.getDataPropertyValue("SFGDefauldAllTypeAccountValue"));

	}

	@Test(description = "AT-16625:Verify that by default the account field should be auto-filled with the user's Savings account if their savings accounts are linked.", groups = {
			"DesktopBrowsers" }, priority = 9)

	public void verifyverifyAccountNameAccountnumberBalance() throws Exception {
		SeleniumUtil.click(SFG.AccountdropDownField());
		SeleniumUtil.waitForPageToLoad();
		String accountName = SFG.unSelectAccount(0).getText();
		d.navigate().refresh();
		SeleniumUtil.waitForPageToLoad(10000);
		PageParser.forceNavigate("AccountsPage", d);
		SeleniumUtil.waitForPageToLoad(10000);
		logger.info(accountName);
		logger.info(SFG.SFGAccountIntegrationAccountName().get(0).getText());

		Assert.assertTrue(accountName.contains(SFG.SFGAccountIntegrationAccountName().get(1).getText().trim()));
		if (accountName.contains(SFG.SFGAccountIntegrationAccountNumber().get(1).getText().trim())) {
			Assert.assertTrue(accountName.contains(SFG.SFGAccountIntegrationAccountNumber().get(1).getText().trim()));
		}

		Assert.assertTrue(
				accountName.contains(SFG.SFGAccountIntegrationAccountAccountBalance().get(1).getText().trim()));

		/*
		 * System.out.println(accountName);
		 * System.out.println(SFG.SFGAccountIntegrationAccountName().get(0).getText());
		 * Assert.assertTrue(SFG.SFGAccountIntegrationAccountName().get(0).getText().
		 * contains(accountName));
		 * Assert.assertTrue(accountName.contains(SFG.SFGAccountIntegrationAccountNumber
		 * ().get(0).getText())); Assert.assertTrue(accountName.contains(SFG.
		 * SFGAccountIntegrationAccountAccountBalance().get(0).getText()));
		 */
	}

	@Test(description = "verify Targate amount value formate when you chnaged 111,111.00 to 1,11,111.00", groups = {
			"DesktopBrowsers" },priority = 10)

	public void verifyGoalAmountCurrencuChangedFormate() throws Exception {
		SFG.changeDateCurrencyFormate();
		SeleniumUtil.waitForPageToLoad(1000);
		PageParser.forceNavigate("SFG", d);
		SeleniumUtil.waitForPageToLoad(5000);
		try {
			SeleniumUtil.click(SFG.startGoalGetStartButton());
		} catch (Exception e) {
			SeleniumUtil.click(SFG.SFGCreateGoalButton());
		}
		SeleniumUtil.waitForPageToLoad(3000);
		SeleniumUtil.click(SFG.SFGhighLevlcategories().get(0));
		SeleniumUtil.waitForPageToLoad(3000);
		SeleniumUtil.click(SFG.getSubCatText().get(0));
		SeleniumUtil.waitForPageToLoad(3000);

		SeleniumUtil.click(SFG.GoalAmountInput());
		SFG.GoalAmountInput().clear();
		SFG.GoalAmountInput().sendKeys(PropsUtil.getDataPropertyValue("SFGChangedCurrencyFormateValue"));
		SFG.GoalAmountInput().sendKeys(Keys.TAB);
		Assert.assertEquals(SFG.GoalAmountInput().getAttribute("value"),
				PropsUtil.getDataPropertyValue("SFGChangedCurrencyFormateValueTabAout"));

	}

	@Test(description = "verify Frequency amount value formate when you chnaged 111,111.00 to 1,11,111.00", groups = {
			"DesktopBrowsers" },dependsOnMethods="verifyGoalAmountCurrencuChangedFormate", priority = 11)

	public void verifyFrequencyAmountCurrencuChangedFormate() throws Exception {
		SeleniumUtil.waitForPageToLoad(1000);
		SeleniumUtil.click(SFG.GoalAmtFrqBtnInput());
		SeleniumUtil.waitForPageToLoad(1000);

		SFG.GoalAmtFrqInput().clear();
		SFG.GoalAmtFrqInput().sendKeys(PropsUtil.getDataPropertyValue("SFGChangedCurrencyFormateValue"));
		SFG.GoalAmtFrqInput().sendKeys(Keys.TAB);
		Assert.assertEquals(SFG.GoalAmtFrqInput().getAttribute("value"),
				PropsUtil.getDataPropertyValue("SFGChangedCurrencyFormateValueTabAout"));

	}

	@Test(description = "verify Targate date value formate when you chnaged mm/dd/yyyy to dd/mm/yyyy", groups = {
			"DesktopBrowsers" }, dependsOnMethods = { "verifyFrequencyAmountCurrencuChangedFormate" }, priority = 12)

	public void verifyTargateDateChangedFormate() throws Exception {
		SeleniumUtil.click(SFG.GoalTargetBtnInput());
		SFG.targateDateValidation(SFG.targateDateSelectdd(3));
		SeleniumUtil.waitForPageToLoad(1000);
		Assert.assertEquals(SFG.GoalTargetInput().getAttribute("value"), SFG.targateDateSelectdd(3));
	}

	@Test(description = "verify Start date value formate when you chnaged mm/dd/yyyy to dd/mm/yyyy", groups = {
			"DesktopBrowsers" }, dependsOnMethods = { "verifyTargateDateChangedFormate" }, priority = 13)

	public void verifyStartDateChangedFormate() throws Exception {
		SFG.startDateValidation(SFG.targateDateSelectdd(2));
		SeleniumUtil.waitForPageToLoad(1000);
		Assert.assertEquals(SFG.GoalStartInput().getAttribute("value"), SFG.targateDateSelectdd(2));
	}

	@Test(description = "verify one time Alocation amount value formate when you chnaged 111,111.00 to 1,11,111.00", groups = {
			"DesktopBrowsers" }, priority = 14)

	public void verifyAlocatedAmountCurrencyChangedFormate() throws Exception {
		PageParser.forceNavigate("SFG", d);
		SFG.step2LandingPage(PropsUtil.getDataPropertyValue("SFGGoalName2"),
				PropsUtil.getDataPropertyValue("SFGGoalAmount1"), 0,
				PropsUtil.getDataPropertyValue("SFGFrequencyAmount1"));
		SFG.OntimeFundingTextBox().sendKeys(PropsUtil.getDataPropertyValue("SFGChangedCurrencyFormateValue"));
		SFG.OntimeFundingTextBox().sendKeys(Keys.TAB);
		Assert.assertEquals(SFG.OntimeFundingTextBox().getAttribute("value"),
				PropsUtil.getDataPropertyValue("SFGChangedCurrencyFormateValueTabAout"));

	}

	@Test(description = "verify one time Alocation amount value formate when you chnaged 111,111.00 to 1,11,111.00", groups = {
			"DesktopBrowsers" }, dependsOnMethods = { "verifyAlocatedAmountCurrencyChangedFormate" }, priority = 15)

	public void verifyLinkAnAccountMessage() throws Exception {
		SeleniumUtil.waitForPageToLoad(1000);
		SeleniumUtil.click(SFG.AccountdropDownField());
		SeleniumUtil.waitForPageToLoad(1000);
		Assert.assertTrue(SFG.SFGLinkAnAccountMessage().getText().trim()
				.contains(PropsUtil.getDataPropertyValue("SFGLinkAnAccountMessage1")));
		Assert.assertTrue(SFG.SFGLinkAnAccountMessage().getText().trim()
				.contains(PropsUtil.getDataPropertyValue("SFGLinkAnAccountMessage2")));
		Assert.assertTrue(SFG.SFGLinkAnAccountMessage().getText().trim()
				.contains(PropsUtil.getDataPropertyValue("SFGLinkAnAccountMessage2")));

	}

	@Test(description = "verify Funding amount value formate when you chnaged 111,111.00 to 1,11,111.00", groups = {
			"DesktopBrowsers" }, dependsOnMethods = { "verifyLinkAnAccountMessage" }, priority = 16)

	public void verifyFundingAmountCurrencyChangedFormate() throws Exception {
		SeleniumUtil.click(SFG.AccountPopUpclose());
		SeleniumUtil.waitForPageToLoad(3000);
		SFG.OntimeFundingTextBox().clear();
		SeleniumUtil.click(SFG.NextBtnStep1());
		SeleniumUtil.waitForPageToLoad(1000);
		SFG.SFGAmountValue().clear();
		SFG.SFGAmountValue().sendKeys(PropsUtil.getDataPropertyValue("SFGChangedCurrencyFormateValue"));
		SFG.SFGAmountValue().sendKeys(Keys.TAB);
		Assert.assertEquals(SFG.SFGAmountValue().getAttribute("value"),
				PropsUtil.getDataPropertyValue("SFGChangedCurrencyFormateValueTabAout"));

	}

}
