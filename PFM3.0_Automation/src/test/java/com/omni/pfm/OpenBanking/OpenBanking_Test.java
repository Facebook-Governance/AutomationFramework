/**
* Copyright (c) 2019 Yodlee Inc. All Rights Reserved.    
*
* This software is the confidential and proprietary information of
* Yodlee, Inc. Use is subject to license terms.
*
* @author sbhat1
*/
package com.omni.pfm.OpenBanking;

import static org.testng.Assert.*;

import java.util.List;

import org.openqa.selenium.WebElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.*;

import com.omni.pfm.PageProcessor.PageParser;
import com.omni.pfm.pages.AccountAddition.AccountAddition;
import com.omni.pfm.pages.Login.LoginPage;
import com.omni.pfm.pages.OpenBanking.OpenBanking_Loc;
import com.omni.pfm.testBase.TestBase;
import com.omni.pfm.utility.PropsUtil;
import com.omni.pfm.utility.SeleniumUtil;

public class OpenBanking_Test extends TestBase {
	private static final Logger logger = LoggerFactory.getLogger(OpenBanking_Test.class);

	AccountAddition accountAddition;
	OpenBanking_Loc ob;
	public List<WebElement> obIndicators;

	@BeforeClass(alwaysRun = true)
	public void init() throws Exception {
		doInitialization("Fincheck V2 Intialization");
		accountAddition = new AccountAddition();
		ob = new OpenBanking_Loc(d);

		logger.info(">>>>> Login");
		LoginPage.loginMain(d, loginParameter);
	}

	@Test(description = "AT-110422:Add Modelo bank account", priority = 1)
	public void addModuloBankAccount() {
		logger.info(">>>>> Adding a modelo bank");
		accountAddition.linkAccount();
		assertTrue(accountAddition.addModeloBankAccount(null), ".. Modelo bank not added properly");

	}

	@Test(description = "AT-110424:Verify the open banking label in desktop", priority = 2, dependsOnMethods = "addModuloBankAccount")
	public void verifyOBLabelCount_Dashboard() {
		logger.info(">>>>> Landed to dasgboard");
		logger.info(">>>>> Verifying the open banking label ");
		int totalObAccounts = Integer.parseInt(PropsUtil.getDataPropertyValue("openBanking.totalOBAccounts"));
		SeleniumUtil.waitForPageToLoad();
		obIndicators = ob.getOBIndicator();
		assertEquals(obIndicators.size(), totalObAccounts, ".. Accounts count mismatch");
	}

	@Test(description = "AT-110423:Verify the text on label", priority = 3, dependsOnMethods = { "addModuloBankAccount",
			"verifyOBLabelCount_Dashboard" })
	public void verifyDashboardLabelText() {
		logger.info(">>>> Verify the ob label");
		for (int i = 0; i < obIndicators.size(); i++) {
			assertEquals(obIndicators.get(i).getText().trim(), PropsUtil.getDataPropertyValue("openBanking.LabelText"),
					".. Label Mismatch");
		}
	}

	/**
	 * Accounts page validation
	 */

	@Test(description = "AT-110425:Validate the texts in ", priority = 4, dependsOnMethods = "addModuloBankAccount")
	public void navigateToAccounts() {
		logger.info("Navigating to Accounts Page");
		PageParser.forceNavigate("AccountsPage", d);
		SeleniumUtil.waitForPageToLoad();
		int totalObAccounts = Integer.parseInt(PropsUtil.getDataPropertyValue("openBanking.totalOBAccounts"));
		obIndicators = ob.getAccountsLabel();
		assertEquals(obIndicators.size(), totalObAccounts, ".. Accounts count mismatch");

	}

	@Test(description = "AT-110426:Validate the Open Banking label at account level", priority = 5, dependsOnMethods = "navigateToAccounts")
	public void validateAccountLabels() {
		logger.info(">>>> Verify the ob label");
		for (int i = 0; i < obIndicators.size(); i++) {
			assertEquals(obIndicators.get(i).getText().trim(), PropsUtil.getDataPropertyValue("openBanking.LabelText"),
					".. Label Text mismatch");
		}
	}

	@Test(description = "AT-110428,AT-110427:Validate the open banking label at account type level", priority = 6, dependsOnMethods = "navigateToAccounts")
	public void validateAccountTypeLabel() {
		logger.info(">>>>> Clicking on account types side bar");
		SeleniumUtil.click(ob.getAccountTypeSideBar());
		SeleniumUtil.waitForPageToLoad(3000);

		int totalObAccounts = Integer.parseInt(PropsUtil.getDataPropertyValue("openBanking.totalOBAccounts"));
		obIndicators = ob.getAccountsLabel();
		assertEquals(obIndicators.size(), totalObAccounts, ".. Account Count mismatch");
	}

	@Test(description = "AT-110429:Verify account settings level ", priority = 7, dependsOnMethods = "addModuloBankAccount")
	public void navigateToAccountsSettings() {
		logger.info(">>>>> Navigating to Account Settings page");
		PageParser.forceNavigate("AccountSettings", d);
		SeleniumUtil.waitForPageToLoad();
		int totalObAccounts = Integer.parseInt(PropsUtil.getDataPropertyValue("openBanking.totalOBAccounts"));
		obIndicators = ob.getAccountsLabel();
		assertEquals(obIndicators.size(), totalObAccounts, ".. Account count  mismatch");
	}

	@Test(description = "AT-110430:Validate the Open Banking label at account level", priority = 8, dependsOnMethods = "navigateToAccountsSettings")
	public void validateAccountSettingsLabels() {
		logger.info(">>>> Verify the ob label");
		for (int i = 0; i < obIndicators.size(); i++) {
			assertEquals(obIndicators.get(i).getText().trim(), PropsUtil.getDataPropertyValue("openBanking.LabelText"),
					".. Label mismatch");
		}
	}

	/**
	 * Manage Consent page details
	 * 
	 */

	@Test(description = "AT-110431:Navigate to Manage Consent page", priority = 9, dependsOnMethods = "addModuloBankAccount")
	public void navigateToManageConsent() {
		logger.info("Navigating to Manage Consent Page");
		PageParser.forceNavigate("OB", d);
		SeleniumUtil.waitForPageToLoad();

		assertEquals(ob.getFinappHeader().getText().trim(),
				PropsUtil.getDataPropertyValue("openBanking.ManageConsentFinappHeader"),
				".. Manage Consent Finapp header mismatch");

	}

	@Test(description = "AT-110432:Verify the click on account list navigates to manage access page", priority = 10, dependsOnMethods = "navigateToManageConsent")
	public void verifyAccountClick() {
		logger.info(">>>> Clicking on account in Consent Dashboard page");
		SeleniumUtil.click(ob.getAccountInManageConsent());
		SeleniumUtil.waitForPageToLoad();

		assertEquals(ob.getFinappHeader().getText().trim(),
				PropsUtil.getDataPropertyValue("openBanking.ManageAccessFinappHeader"),
				".. Manage Access finapp  header text mismatch");
	}

	@Test(description = "AT-110433:Verify Back Link", priority = 11, dependsOnMethods = "verifyAccountClick")
	public void verifyBackLink() {
		logger.info(">>>>> verifying back link in manage access page");
		assertTrue(ob.getBacLink().isDisplayed(), ".. Back link not present");
	}

	@Test(description = "AT-108292:Verify Renew button is dispayed", priority = 12, dependsOnMethods = "verifyAccountClick")
	public void verifyRenewButton() {
		logger.info(">>>>> verifying renew button");
		assertTrue(ob.getRenewButton().isDisplayed(), ".. Renew button not displayed");

	}

	@Test(description = "AT-108293:Verify Manage Accounts Button is displayed", priority = 13, dependsOnMethods = "verifyAccountClick")
	public void verifyManageButton() {
		logger.info(">>>>> Verifying Manage accounts button");
		assertTrue(ob.getManageButton().isDisplayed(), ".. Manage Accounts button not displayed");
	}

	@Test(description = "AT-108294:Verify Delete button is displayed", priority = 14, dependsOnMethods = "verifyAccountClick")
	public void verifyDeleteButton() {
		logger.info(">>>>> verifying delete button");
		assertTrue(ob.getDeleteButton().isDisplayed(), ".. Delete Button not displayed");

	}

	@Test(description = "AT-108295:Verify 'Click here..' link is present", priority = 15, dependsOnMethods = "verifyAccountClick")
	public void verifyTakeActionLink() {
		logger.info(">>>>> Verifying Take Action Link");
		assertTrue(ob.getTakeActionLink().isDisplayed(), ".. Link not displayed");
	}

	/**
	 * Account setting changes
	 */

	@Test(description = "AT-108296:Verify account settings level ", priority = 16, dependsOnMethods = "addModuloBankAccount")
	public void verifyRenewLink() {
		logger.info(">>>>> Navigating to Account Settings page");
		PageParser.forceNavigate("AccountSettings", d);
		SeleniumUtil.waitForPageToLoad();

		assertTrue(ob.getRenewLink().isDisplayed(), ".. Renew Link not displayed");

	}

	@Test(description = "AT-108297:Verify Delete Link should be present", priority = 17, dependsOnMethods = "verifyRenewLink")
	public void verifyDeleteLink() {
		logger.info(">>>>> Verifying delete Link");
		assertTrue(ob.getDeleteLink().isDisplayed(), ".. Delete link not displayed");

	}

	@Test(description = "AT-108298:Verify delete link opens a popup", priority = 18, dependsOnMethods = "verifyRenewLink")
	public void verifyDeleteAction() {
		logger.info(">>>>> verifying delete click will launch a popup");
		SeleniumUtil.click(ob.getDeleteLink());

		assertTrue(ob.getDeleteSitePopup().isDisplayed(), ".. Delete site popup not displayed.");
	}

	@Test(description = "AT-108299:Verify the warning message", priority = 19, dependsOnMethods = "verifyDeleteAction")
	public void verifyWarningMessage() {
		logger.info(">>>>> Verifying Warning Message");
		assertEquals(ob.getWarningMessage().getText().trim(),
				PropsUtil.getDataPropertyValue("openBanking.DeleteWarningMessage"), ".. Wrong warning message found.");
	}

	@Test(description = "AT-108300:Verify the delete checkbox label", priority = 20, dependsOnMethods = "verifyDeleteAction")
	public void verifyDeleteCheckboxLabel() {
		logger.info(">>>>> Verifying delete checkbox label");
		assertEquals(ob.getDeleteCheckboxLabel().getText().trim(),
				PropsUtil.getDataPropertyValue("opneBanking.DeleteCheckboxLabel"), ".. Wrong label found.");

	}

	@Test(description = "AT-108300:Verify Delete action", priority = 21, dependsOnMethods = "verifyDeleteAction")
	public void verifyDelete() {
		logger.info(">>>>> Verify delete click");
		SeleniumUtil.click(ob.getConfirmCheckbox());

		SeleniumUtil.click(ob.getPopupDeleteButton());
		SeleniumUtil.waitForPageToLoad();

		assertTrue(ob.getNoDataScreen().isDisplayed(), ".. No accounts got deleted.");
	}
}
