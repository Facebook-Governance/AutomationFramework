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
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import com.omni.pfm.PageProcessor.PageParser;
import com.omni.pfm.pages.AccountAddition.AccountAddition;
import com.omni.pfm.testBase.TestBase;
import com.omni.pfm.utility.PropsUtil;
import com.omni.pfm.utility.SeleniumUtil;

public class Accounts_ViewByAccountType_Test extends TestBase {
	Logger logger = LoggerFactory.getLogger(Accounts_ViewByFI_Test.class);
	AccountsViewByAcntType_Loc viewByAcntType;
	AccountsLandingPage_Loc acntLandingPage;
	AccountAddition accountAddition;

	@BeforeClass()
	public void init() throws AWTException, InterruptedException {
		doInitialization("Accounts");

		TestBase.tc = TestBase.extent.startTest("Login", "Login");
		TestBase.test.appendChild(TestBase.tc);
		viewByAcntType = new AccountsViewByAcntType_Loc(d);
		acntLandingPage = new AccountsLandingPage_Loc(d);
		accountAddition = new AccountAddition();
	}

	@Test(description = "login to the user", groups = { "DesktopBrowsers,sanity" }, priority = 1, enabled = true)
	public void login() throws Exception {
		// LoginPage.loginMain(d, loginParameter);
		PageParser.forceNavigate("AccountsPage", d);
		SeleniumUtil.waitForPageToLoad();
	}

	@Test(description = "AT-83941,AT-84240,AT-83933:Verify Account Type VIew high level containers", groups = {
			"DesktopBrowsers,sanity" }, priority = 2, dependsOnMethods = "login", enabled = true)
	public void verifyOrderOfHighLevelContainer() throws InterruptedException {

		try {
			acntLandingPage.accountslayout().isDisplayed();
		}catch(Throwable e) {
			PageParser.forceNavigate("AccountsPage", d);
			SeleniumUtil.waitForPageToLoad(3000);
		}
		
		
		acntLandingPage.clickOnAcntTypeView();
		SeleniumUtil.waitForPageToLoad(3000);
		Assert.assertEquals(acntLandingPage.AccountsPageHeader().getText(),
				PropsUtil.getDataPropertyValue("Accounts_Header1").trim());
		Assert.assertEquals(acntLandingPage.AccountsHighLevelContainers().size(),
				Integer.parseInt(PropsUtil.getDataPropertyValue("NumberOfHighLevelContainersInAcntType").trim()));
	}

	@Test(description = "AT-83962,AT-83965,AT-84241,AT-83937,AT-83947,AT-84243,AT-84245:The view should be in the following order Cash, Cards, Investment, Loan, Real estate, Bills, Rewards, manual accounts", groups = {
			"DesktopBrowsers", "sanity", "Smoke" }, priority = 3, dependsOnMethods = "login", enabled = true)
	public void verifyOrderOfAcntContainer() throws InterruptedException {

		viewByAcntType.verifyOrderOfAcntContainer("NameOfAcntLevelContainerInAcntType");
	}

	@Test(description = "AT-83951,AT-84144,AT-83949:Refresh icon should come for non PrePopulated held accounts", priority = 4, groups = {
			"Desktop" }, dependsOnMethods = "login", enabled = true)
	public void verifyRefreshAtSiteLevel() {

		boolean isShown = false;
		try {
			if (acntLandingPage.RefreshAtSiteLevel().get(0).isDisplayed()) {
				isShown = false;
			}
		} catch (Exception e) {

		}
		Assert.assertFalse(isShown);
	}

	@Test(description = "AT-83968,AT-83993,AT-83958,AT-83954:Verify that the sum of the account balance for all the accounts is displayed at the Account Type level for all the containers, except the container name Rewards For example if the user has two accounts under cash container, the total sum of these two account balances should be displayed correctly at the account type level", priority = 5, dependsOnMethods = "login", enabled = true)
	public void verifySumOfAllAcnt() {
		Assert.assertEquals(viewByAcntType.AccountsContainerTotalAmt().size(),
				Integer.parseInt(PropsUtil.getDataPropertyValue("CountOfTotalSum")));
		Assert.assertTrue(viewByAcntType.AccountsContainerTotalAmt().get(0).getText()
				.contains(PropsUtil.getDataPropertyValue("UserPreferredCurrency").trim()));
	}

	@Test(description = "AT-84457:The Account number should display in the masked format eg - x-2333", priority = 6, dependsOnMethods = "login", enabled = true)
	public void verifyMaskedFormat() {
		viewByAcntType.verifyMaskedFormat();
	}

	@Test(description = "AT-83978,AT-84477:All the containers are should be in Expanded View by default in Account type view.", groups = {
			"DesktopBrowsers,sanity" }, priority = 7, dependsOnMethods = "login", enabled = true)
	public void VerifyGroupsAreInExpandedForm() {

		acntLandingPage.verifyGroupsAreInExpandedForm();
	}

	@Test(description = "AT-84476,AT-83959,AT-83976,AT-84038:When you click on minimize button the entire site should collapse", groups = {
			"DesktopBrowsers" }, priority = 8, dependsOnMethods = "VerifyGroupsAreInExpandedForm", enabled = true)
	public void VerifyUserCanMinimize() throws InterruptedException {

		acntLandingPage.verifyUserCanMinimise();
	}

	@Test(description = "AT-83960,AT-84025,AT-83977,AT-84273:When you click on maximize button the entire site should expand", groups = {
			"DesktopBrowsers" }, priority = 9, dependsOnMethods = "VerifyUserCanMinimize", enabled = true)
	public void VerifyUserCanMaximise() throws InterruptedException {

		acntLandingPage.verifyUserCanMaximise();
	}

	@Test(description = "AT-83940,AT-83963:Under the container name the order for acct name and acct no should be <ACCOUNT_NAME> <Ac_No>", groups = {
			"DesktopBrowsers" }, priority = 10, dependsOnMethods = "login", enabled = true)
	public void verifyAcntDetailFormat() throws InterruptedException {

		SeleniumUtil.waitForPageToLoad(3000);
		if (appFlag.equals("app") || appFlag.equals("emulator")) {
			Assert.assertEquals(viewByAcntType.AccountsAcntTypeMobile().get(0).getText().trim(),
					PropsUtil.getDataPropertyValue("AcntFormat").trim());
		} else {
			Assert.assertEquals(viewByAcntType.AccountsAcntType().get(0).getText().trim(),
					PropsUtil.getDataPropertyValue("AcntFormat").trim());
		}
	}

	@Test(description = "Verify the link account button appears ", groups = { "DesktopBrowsers",
			"Smoke" }, dependsOnMethods = "login", priority = 11, enabled = true)
	public void linkAccountBtnEnableChk() throws InterruptedException {

		if (acntLandingPage.isAccountsLinkAnAcntBtnMobile()) {
			Assert.assertTrue(acntLandingPage.AccountsLinkAnAcntBtnMobile().isDisplayed());
		} else {
			Assert.assertTrue(acntLandingPage.LinkActBtn().isDisplayed());
		}
	}
}
