/**
 * Copyright (c) 2018 Yodlee Inc. All Rights Reserved.
 *
 * This software is the confidential and proprietary information of
 * Yodlee, Inc. Use is subject to license terms.
 *
 * @author sakshi Jain
 */
package com.omni.pfm.Accounts;

import java.awt.AWTException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import com.accessibility.examples.testng.webdriver.test.RunAccessibilityTest;
import com.omni.pfm.PageProcessor.PageParser;
import com.omni.pfm.testBase.TestBase;
import com.omni.pfm.utility.PropsUtil;
import com.omni.pfm.utility.SeleniumUtil;

public class Accounts_EditCredentials_Test extends TestBase {
	static Logger logger = LoggerFactory.getLogger(Accounts_EditCredentials_Test.class);
	Accounts_EditCredentials_Loc editCredential;
	Account_Go_To_Site_Loc accountsGoToSite;

	@BeforeClass()
	public void init() throws AWTException, InterruptedException {
		doInitialization("Accounts");

		TestBase.tc = TestBase.extent.startTest("Login", "Login");
		TestBase.test.appendChild(TestBase.tc);

		editCredential = new Accounts_EditCredentials_Loc(d);
		accountsGoToSite = new Account_Go_To_Site_Loc(d);
	}

	@Test(description = "Navigating to account setting page.", groups = {
			"DesktopBrowsers,sanity" }, priority = 1, enabled = true)
	public void login() throws Exception {
		d.navigate().refresh();
		SeleniumUtil.waitForPageToLoad();
		PageParser.forceNavigate("AccountSettings", d);
		SeleniumUtil.waitForPageToLoad(7000);
	}

	// Updated By MRQA -- Navigating to Account Settings through more info since
	// some the Cobrands will not have account settings
	// so need to traverse via more info.
	@Test(description = "ACCT-07_01: Navigating to Account Settings through more info.", groups = {
			"DesktopBrowsers,sanity" }, priority = 1, enabled = true)
	public void moreInfoAccSet() throws Exception {
		d.navigate().refresh();
		SeleniumUtil.waitForPageToLoad();

		editCredential.moreText_LP();
		editCredential.Settings_LP().click();
		SeleniumUtil.waitForPageToLoad(7000);
	}

	@Test(description = "Verify that Edit site credentials is invoked ", groups = { "DesktopBrowsers",
			"Smoke" }, priority = 2, dependsOnMethods = "login", enabled = true)

	public void verifyEditLandingPage() throws InterruptedException {
		/*
		 * if
		 * (PropsUtil.getDataPropertyValue("MOBILEORWEB").equalsIgnoreCase("MOBILE")){
		 * String pageTitleMobile=editCredential.titleTextMob().getText().trim();
		 * Assert.assertEquals(pageTitleMobile,
		 * PropsUtil.getDataPropertyValue("PageTitle")); }else{
		 */
		String pageTitle = editCredential.TitleText().getText().trim();
		Assert.assertEquals(pageTitle, PropsUtil.getDataPropertyValue("SettingsPageTitle"));
	}

	@Test(description = "AT-75957,AT-76071:Verify that when the user click on the Edit Credentials then the popup window is displayed", groups = {
			"DesktopBrowsers", "Smoke" }, priority = 3, dependsOnMethods = "login", enabled = true)

	public void verifyEditPopUp() throws InterruptedException {
		/*
		 * if (PropsUtil.getEnvPropertyValue("MOBILEORWEB").equalsIgnoreCase("MOBILE")){
		 * editCredential.editCredentialHeaderMobile(); }else{
		 * editCredential.editCredentialHeader(); }
		 */
		if (appFlag.equals("app") || appFlag.equals("emulator")) {
			SeleniumUtil.click(accountsGoToSite.dagsiteSettings());
		}
		editCredential.editCredentialHeader();
	}

	@Test(description = "L1-35707,L1-35717:ACCT-07_04:Verify that on Clicking close button in edit credentials page user should land to previous page", groups = {
			"DesktopBrowsers", "Smoke" }, dependsOnMethods = "verifyEditPopUp", priority = 4, enabled = true)

	public void verifyUseAbleReturnParentWindow() throws InterruptedException {

		SeleniumUtil.click(editCredential.CloseEditCredentials());
		SeleniumUtil.waitForPageToLoad();
		/*
		 * if (PropsUtil.getEnvPropertyValue("MOBILEORWEB").equalsIgnoreCase("MOBILE")){
		 * String parentPageTitle=editCredential.titleTextMob().getText();
		 * Assert.assertEquals(parentPageTitle,
		 * PropsUtil.getDataPropertyValue("PageTitle")); }else{
		 */
		String parentPageTitle = editCredential.TitleText().getText();
		Assert.assertEquals(parentPageTitle, PropsUtil.getDataPropertyValue("SettingsPageTitle"));

	}

	@Test(description = "AT-76072:Verify that clicking on edit credentials will open up the edit credentials page with details", groups = {
			"DesktopBrowsers", "Smoke" }, priority = 5, dependsOnMethods = "verifyEditPopUp", enabled = true)

	public void verifyEditPopUpDetails() throws InterruptedException {
		if (PropsUtil.getEnvPropertyValue("UnifiedFlow").equalsIgnoreCase("no")) {
			editCredential.verifyEditCredentialPopup();
		} else {
			editCredential.verifyEditCredentialPopupSDG();
		}
	}

	@Test(description = "L1-35712:ACCT-07_06:Verify that Edit Credentials PopUp display Update button", groups = {
			"DesktopBrowsers", "Smoke" }, priority = 6, dependsOnMethods = "verifyEditPopUpDetails", enabled = true)

	public void verifyUpdatedButton() throws InterruptedException {
		if (PropsUtil.getEnvPropertyValue("UnifiedFlow").equalsIgnoreCase("no")) {
			Boolean button = editCredential.UpdatedButton().isDisplayed();
			Assert.assertTrue(button);
		} else {
			Boolean button = editCredential.submitButtonSDG().isDisplayed();
			SeleniumUtil.click(editCredential.EditPopUpCloseIcon());
			Assert.assertTrue(button);
		}

	}

	@Test(description = "AT-76067:Verify that edit text and icon are displayed on the home page", groups = {
			"DesktopBrowsers", "Smoke" }, dependsOnMethods = "login", priority = 7, enabled = true)

	public void verifyLandingPageDetails() throws InterruptedException {

		editCredential.verifyEditIconAndText();
	}

	@AfterClass
	public void checkAcc() {
		try {
			RunAccessibilityTest rat = new RunAccessibilityTest(this.getClass().getName());
			rat.testAccessibility(d);
		} catch (Exception e) {

		}
	}
}
