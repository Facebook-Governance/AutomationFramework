/*******************************************************************************
 * Copyright (c) 2020 Yodlee Inc. All Rights Reserved.
 * 
 * This software is the confidential and proprietary information of
 * Yodlee, Inc. Use is subject to license terms.
 * 
 * @author kongara.sravan
 ******************************************************************************/
package com.omni.pfm.USOB;

import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.omni.pfm.PageProcessor.PageParser;
import com.omni.pfm.pages.AccountAddition.AccountAddition;
import com.omni.pfm.pages.AccountSettings.AccountsSetting_GlobalSettings_Loc;
import com.omni.pfm.pages.Login.LoginPage;
import com.omni.pfm.testBase.TestBase;
import com.omni.pfm.utility.PropsUtil;
import com.omni.pfm.utility.SeleniumUtil;

public class USOB_Test extends TestBase {
	Logger logger = LoggerFactory.getLogger(USOB_Test.class);
	static final String accountNameNotMigrated = "Dag Site Multilevel";
	LoginPage login;
	AccountAddition accountAdd;
	AccountsSetting_GlobalSettings_Loc accSettings;
	USOB_Main usob;

	@BeforeClass()
	public void init() throws Exception {
		doInitialization("USOB Testing");

		TestBase.tc = TestBase.extent.startTest("Login", "Login");
		TestBase.test.appendChild(TestBase.tc);
		login = new LoginPage();
		d.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
		accSettings = new AccountsSetting_GlobalSettings_Loc(d);
		usob = new USOB_Main(d);
	}

	@Test(description = "Login to the environment", groups = { "DesktopBrowsers,sanity" }, priority = 0, enabled = true)
	public void login() {
		// Login with the user who have accounts to be migrated to open banking 
		Assert.assertTrue(LoginPage.loginWithOldAccount(PropsUtil.getDataPropertyValue("USOB.login.username"),
				PropsUtil.getDataPropertyValue("USOB.login.password")));
		SeleniumUtil.waitForPageToLoad();
		SeleniumUtil.refresh(d);
	}

	@Test(description = "AT-137036: Verify migration banner is displayed in the dashboard", groups = {
			"DesktopBrowsers,sanity" }, priority = 1, dependsOnMethods = "login")
	public void verifyOBMigrationBannerIsDisplayed() {
		SeleniumUtil.waitForPageToLoad();
		if (!usob.isMigrationBannerDisplayedInDashboard()) {
			Assert.fail("OB migration banner is not displayed in the dashboard");
		}
	}

	@Test(description = "AT-137036:Verify the text displayed on the dashboard OB migration banner", groups = {
			"DesktopBrowsers,sanity" }, priority = 2, dependsOnMethods = "verifyOBMigrationBannerIsDisplayed")
	public void verifyTextDisplayedOnTheOBMigrationBanner() {
		String expectedBannerText = PropsUtil.getDataPropertyValue("OBMigrationBannerText");
		String actualBannerText = usob.returnTheTextDisplayedOnOBMigartionBanner();

		if (!actualBannerText.equals(expectedBannerText)) {
			Assert.fail("OB migration banner is not displayed as expected. Actual text :: \"" + actualBannerText
					+ "\" and Expected Text :: \"" + expectedBannerText + "\"");
		}
	}

	@Test(description = "AT-137038:When clicked on the OB migration banner check whether it is navigating to account settings page", groups = {
			"DesktopBrowsers,sanity" }, priority = 3, dependsOnMethods = "verifyOBMigrationBannerIsDisplayed")
	public void verifyNavigationWhenClickedOnTheOBMigrationBanner() {
		usob.clickOnOBMigrationBanner();
		SeleniumUtil.waitForPageToLoad();
		String presentFinappHeader = usob.returnFinappHeader();
		if (!presentFinappHeader.trim().equals("Account Settings")) {
			Assert.fail("Navigation to acount settings page after clicking on OB migration banner is not happening");
		}
	}

	@Test(description = "AT-137038:When clicked on the OB migration banner check whether it is navigating to account settings page", groups = {
			"DesktopBrowsers,sanity" }, priority = 4, dependsOnMethods = "verifyOBMigrationBannerIsDisplayed")
	public void verifyErrorContainerDisplayedForAccountInAccountSettingsPage() {
		usob.clickOnGivenAccountInAccountSettingsPage(accountNameNotMigrated);
		if (!usob.isErrorContainerDisplayed()) {
			Assert.fail(
					"Error container is not displayed by default after navigating to account settings page  for acccount :: Dag Site Multilevel");
		}
	}

	@Test(description = "AT-137035:Verify disabled icon in the for the account in accounts page", groups = {
			"DesktopBrowsers,sanity" }, priority = 5, dependsOnMethods = "login")
	public void verifyDisabledIconIsDisplayedInAccountsPage() {
		PageParser.forceNavigate("AccountsPage", d);
		if (!usob.isOBMigrationIconDisplayedForGivenAccountInAccountsPage(accountNameNotMigrated)) {
			Assert.fail("OB Migration icon is not displayed for the account :: " + accountNameNotMigrated
					+ " in accounts page");
		}
	}

	@Test(description = "AT-137038:Verify error meesage in the popup for the account in accounts page", groups = {
			"DesktopBrowsers,sanity" }, priority = 6, dependsOnMethods = "login")
	public void verifyErrorMessageInAccountsPageForNotMigratedAccounts() {
		PageParser.forceNavigate("AccountsPage", d);
		usob.clickOnOBMigrationIconForGivenAccount(accountNameNotMigrated);
		String actualText = usob.returnMessageDisplayedInErrorContainerOfMigrationPopup();
		String expectedText = PropsUtil.getDataPropertyValue("OBMigrationPopupErrorMessage");
		Assert.assertEquals(actualText, expectedText, "OB Migration popup error message is not as expected.");
		logger.info("Actual text :: " + actualText);
	}

	@Test(description = "AT-137038:Verify disabled icon in the for the account in accounts settings page", groups = {
			"DesktopBrowsers,sanity" }, priority = 7, dependsOnMethods = "login")
	public void verifyErrorMessageInAccountsSettingsPageForNotMigratedAccounts() {
		PageParser.forceNavigate("AccountSettings", d);
		usob.clickOnGivenAccountInAccountSettingsPage(accountNameNotMigrated);
		SeleniumUtil.waitForPageToLoad();
		usob.clickOnOBMigrationIconInAccountSettingsPage();
		String actualText = usob.returnMessageDisplayedInErrorContainerOfMigrationPopup();
		String expectedText = PropsUtil.getDataPropertyValue("OBMigrationPopupErrorMessage");
		Assert.assertEquals(actualText, expectedText,
				"OB Migration popup error message is not as expected in account settings page.");
		logger.info("Actual text :: " + actualText);
	}
}