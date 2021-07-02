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

public class Accounts_ViewByFI_Test extends TestBase {
	Logger logger = LoggerFactory.getLogger(Accounts_ViewByFI_Test.class);
	AccountsViewByFI_Loc viewByFI;
	AccountsLandingPage_Loc acntLandingPage;
	AccountAddition accountAddition;

	@BeforeClass()
	public void init() throws AWTException, InterruptedException {
		doInitialization("Accounts");

		TestBase.tc = TestBase.extent.startTest("Login", "Login");
		TestBase.test.appendChild(TestBase.tc);
		viewByFI = new AccountsViewByFI_Loc(d);
		acntLandingPage = new AccountsLandingPage_Loc(d);
		accountAddition = new AccountAddition();
	}

	@Test(description = "login to the user", groups = { "DesktopBrowsers,sanity" }, priority = 1, enabled = true)
	public void login() throws Exception {
		// LoginPage.loginMain(d, loginParameter);
		d.navigate().refresh();
		SeleniumUtil.waitForPageToLoad();
	}

	@Test(description = "ACCT-10_02: Adding Manual Accounts", groups = {
			"DesktopBrowsers,sanity" }, priority = 2, dependsOnMethods = "login", enabled = true)
	public void manualAccountAddition() throws Exception {
		accountAddition.addManualAccount("Other Assets", "MyAccountother_assets", null, "100011", null, null, null,
				null);
		SeleniumUtil.waitForPageToLoad();
		accountAddition.addManualAccount("Other Liabilities", "MyAccountother_liabilities", "TestLiability", "100012",
				null, null, null, null);
		PageParser.forceNavigate("AccountsPage", d);
		SeleniumUtil.waitForPageToLoad();
	}

	@Test(description = "AT-83936:Verify that whenever the user loads the Accounts then by default user should be on the FI view ", groups = {
			"DesktopBrowsers", "sanity", "Smoke" }, priority = 3, dependsOnMethods = "login", enabled = true)
	public void DefaultFIView() throws InterruptedException {
		
		try {
			acntLandingPage.accountslayout().isDisplayed();
		}catch(Throwable e) {
			PageParser.forceNavigate("AccountsPage", d);
			SeleniumUtil.waitForPageToLoad(3000);
		}
		
		Assert.assertTrue(acntLandingPage.AccountsViewList().get(0).isEnabled());
	}

	@Test(description = "AT-83941,AT-84240:Verify FI VIew high level containers", groups = {
			"DesktopBrowsers,sanity" }, priority = 4, dependsOnMethods = "login", enabled = true)
	public void verifyOrderOfHighLevelContainer() throws InterruptedException {

		acntLandingPage.NameOfHighLevelContainers("NameOfHighLevelContainersInFI");
		Assert.assertEquals(acntLandingPage.AccountsHighLevelContainers().size(),
				Integer.parseInt(PropsUtil.getDataPropertyValue("NumberOfHighLevelContainersInFI").trim()));
	}

	@Test(description = "AT-93005,AT-83962,AT-83965,AT-84241,AT-83937,AT-83947,AT-84243,AT-84245,AT-84056:The view should be in the following order Cash, Cards, Investment, Loan, Real estate, Bills, Rewards, manual accounts", groups = {
			"DesktopBrowsers", "sanity",
			"Smoke" }, priority = 5, dependsOnMethods = "manualAccountAddition", enabled = true)
	public void verifyOrderOfAcntContainer() throws InterruptedException {

		viewByFI.verifyOrderOfAcntContainer("NameOfAcntLevelContainerInFI");
	}

	@Test(description = "AT-83966,AT-83942:Accounts under each Account Type are alphabetically sorted", groups = {
			"DesktopBrowsers", "sanity",
			"Smoke" }, priority = 6, dependsOnMethods = "manualAccountAddition", enabled = true)
	public void verifyOrderOfAcntUnderAcntContainer() throws InterruptedException {

		viewByFI.verifyOrderOfAcntUnderAcntContainer("NameOfAcntUnderAcntContainer");
	}

	@Test(description = "AT-84442,AT-84432,AT-83972,AT-84450:verifying cash and card should display total balance at account level.", groups = {
			"DesktopBrowsers", "sanity", "Smoke" }, priority = 7, dependsOnMethods = "login", enabled = false)
	public void verifyAcntLevelBalnce() throws InterruptedException {

		Assert.assertEquals(viewByFI.getTotalBalanceLocator("1").getText().trim(),
				PropsUtil.getDataPropertyValue("TotalCashContainerBalance").trim());

		boolean isShown = false;
		try {
			if (viewByFI.getTotalBalanceLocator("2").isDisplayed()) {
				isShown = true;
			}
		} catch (Exception e) {

		}
		Assert.assertFalse(isShown);
	}

	@Test(description = "AT-83979,AT-84432:For the Cash container the correct account type should be displayed", groups = {
			"DesktopBrowsers", "sanity", "Smoke" }, priority = 8, dependsOnMethods = "login", enabled = true)
	public void getAcntUnderCash() throws InterruptedException {

		viewByFI.verifyAcntsUnderContainer("bank", "AcntsUnderCash");
	}

	@Test(description = "AT-83979,AT-84433:For the card container the correct account type should be displayed", groups = {
			"DesktopBrowsers", "sanity", "Smoke" }, priority = 9, dependsOnMethods = "login", enabled = true)
	public void getAcntUnderCards() throws InterruptedException {

		viewByFI.verifyAcntsUnderContainer("credits", "AcntsUnderCard");
	}

	@Test(description = "AT-83979,AT-84435:For the investment container the correct account type should be displayed", groups = {
			"DesktopBrowsers", "sanity", "Smoke" }, priority = 10, dependsOnMethods = "login", enabled = true)
	public void getAcntUnderInvestment() throws InterruptedException {

		SeleniumUtil.waitForPageToLoad();
		viewByFI.verifyAcntsUnderContainer("stocks", "AcntsUnderInvestment");
	}

	@Test(description = "AT-83979,AT-84434:For the loans container the correct account type should be displayed", groups = {
			"DesktopBrowsers", "sanity", "Smoke" }, priority = 11, dependsOnMethods = "login", enabled = true)
	public void getAcntUnderLoans() throws InterruptedException {

		viewByFI.verifyAcntsUnderContainer("loans", "AcntsUnderLoans");
	}

	@Test(description = "AT-83979,AT-84439:For the Insurance container the correct account type should be displayed", groups = {
			"DesktopBrowsers", "sanity", "Smoke" }, priority = 9, dependsOnMethods = "login", enabled = true)
	public void getAcntUnderInsurance() throws InterruptedException {

		viewByFI.verifyAcntsUnderContainer("insurance", "AcntsUnderInsurance");
	}

	@Test(description = "AT-83979,AT-84436:For the bills container the correct account type should be displayed", groups = {
			"DesktopBrowsers", "sanity", "Smoke" }, priority = 12, dependsOnMethods = "login", enabled = true)
	public void getAcntUnderBills() throws InterruptedException {

		viewByFI.verifyAcntsUnderContainer("bills", "AcntsUnderBills");
	}

	@Test(description = "AT-83979,AT-84437:For the Rewards container the correct account type should be displayed", groups = {
			"DesktopBrowsers", "sanity", "Smoke" }, priority = 13, dependsOnMethods = "login", enabled = true)
	public void getAcntUnderRewards() throws InterruptedException {

		viewByFI.verifyAcntsUnderContainer("miles", "AcntsUnderRewards");
	}

	@Test(description = "AT-83979,AT-84438,AT-84448:For the RealEstate container the correct account type should be displayed", groups = {
			"DesktopBrowsers", "sanity", "Smoke" }, priority = 14, dependsOnMethods = "login", enabled = true)
	public void getAcntUnderRealEstate() throws InterruptedException {

		viewByFI.verifyManualAcntsUnderContainer("realestate", "AcntsUnderRealEstate");
		Assert.assertEquals(viewByFI.RealEstatesAcntBalance().getText().trim(),
				PropsUtil.getDataPropertyValue("RealEstateBalnace").trim());
	}

	@Test(description = "AT-84131,AT-84138:For the manual cash container the correct account type should be displayed", groups = {
			"DesktopBrowsers", "sanity", "Smoke" }, priority = 15, dependsOnMethods = "login", enabled = true)
	public void getAcntUnderManualBank() throws InterruptedException {

		viewByFI.verifyManualBankAcntsUnderContainer("AcntsUnderManualCash");
	}

	@Test(description = "AT-84131,AT-84143,AT-84035,AT-84440:For the liabilities container the correct account type should be displayed", groups = {
			"DesktopBrowsers", "sanity",
			"Smoke" }, priority = 16, dependsOnMethods = "manualAccountAddition", enabled = true)
	public void getAcntUnderLiability() throws InterruptedException {

		viewByFI.verifyManualAcntsUnderContainer("other_liabilities", "AcntsUnderliabilities");
		Assert.assertTrue(viewByFI.nickNameForLiability().isDisplayed());
	}

	@Test(description = "AT-84129,AT-84458:Manual account in FI view should be displayed in the format: Manual, Acct Type, Acct Name & Acc Number", groups = {
			"DesktopBrowsers", "sanity",
			"Smoke" }, priority = 17, dependsOnMethods = "manualAccountAddition", enabled = true)
	public void verifyManualAcntFormat() throws InterruptedException {

		viewByFI.verifyManualAcntFormat();
		logger.info("Amount should not get ellipsified");
		Assert.assertEquals(viewByFI.manualAcntBalance().getText().trim(),
				PropsUtil.getDataPropertyValue("ManualAcntBalance").trim());
	}

	@Test(description = "AT-84446,AT-84447,AT-84146,AT-83974,AT-83971:Rewards points should displays at account level for the Rewards Accounts", groups = {
			"DesktopBrowsers", "sanity", "Smoke" }, priority = 18, dependsOnMethods = "login", enabled = true)
	public void verifyRewardAcntBalance() throws InterruptedException {

		Assert.assertEquals(viewByFI.RewardAcntBalance().getText().trim(),
				PropsUtil.getDataPropertyValue("RewardBalance").trim());
		Assert.assertTrue(viewByFI.BillsDueInfo().isDisplayed());
	}

	@Test(description = "AT-83928,AT-83929,AT-84002: Verify Refresh All button and setting button under more.", priority = 19, groups = {
			"Desktop" }, dependsOnMethods = "login", enabled = true)
	public void verifyOptionsUnderMoreInFIView() {
		acntLandingPage.clickOnMoreBtn();
		Assert.assertTrue(acntLandingPage.VisibleOptionsUnderMoreBtn().get(1).isDisplayed());
		Assert.assertEquals(acntLandingPage.RefreshAllButton().getText().trim(),
				PropsUtil.getDataPropertyValue("RefreshAllBtn").trim());
	}

	@Test(description = "AT-83951,AT-84144,AT-83949:Refresh icon should come for non PrePopulated held accounts", priority = 20, groups = {
			"Desktop" }, dependsOnMethods = "login", enabled = true)
	public void verifyRefreshAtSiteLevel() {

		Assert.assertTrue(acntLandingPage.RefreshAtSiteLevel().get(0).isDisplayed());
		Assert.assertEquals(acntLandingPage.RefreshAtSiteLevel().size(),
				Integer.parseInt(PropsUtil.getDataPropertyValue("RefreshAtSiteLevel").trim()));
	}

	@Test(description = "AT-84036:verify if nick name is not there then it should not show nick name.", priority = 21, groups = {
			"Desktop" }, dependsOnMethods = "manualAccountAddition", enabled = true)
	public void verifyNickNameNotPresent() {

		boolean isShown = false;
		try {
			if (viewByFI.nickNameForAsset().isDisplayed()) {
				isShown = true;
			}
		} catch (Exception e) {

		}
		Assert.assertFalse(isShown);
	}

	@Test(description = "AT-83978,AT-84477:All the containers are should be in Expanded View by default in Account type view.", groups = {
			"DesktopBrowsers,sanity" }, priority = 22, dependsOnMethods = "login", enabled = true)
	public void VerifyGroupsAreInExpandedForm() {

		acntLandingPage.verifyGroupsAreInExpandedForm();
	}

	@Test(description = "AT-84476,AT-83959,AT-83976,AT-84038:When you click on minimize button the entire site should collapse", groups = {
			"DesktopBrowsers" }, priority = 23, dependsOnMethods = "login", enabled = true)
	public void VerifyUserCanMinimize() throws InterruptedException {

		acntLandingPage.verifyUserCanMinimise();
	}

	@Test(description = "AT-83960,AT-84025,AT-83977,AT-84273:When you click on maximize button the entire site should expand", groups = {
			"DesktopBrowsers" }, priority = 24, dependsOnMethods = "login", enabled = true)
	public void VerifyUserCanMaximise() throws InterruptedException {

		acntLandingPage.verifyUserCanMaximise();
	}

	@Test(description = "AT-84306,AT-92894:The following options must be available after clicking the ellipsification of Bank account  Go to Site,Account Setting,Alert Setting,View Trend ", groups = {
			"DesktopBrowsers" }, priority = 25, dependsOnMethods = "login", enabled = true)
	public void VerifyCashMoreBtnOptions() throws InterruptedException {
		viewByFI.clickingOnMoreBtn("TESTDATA1");
		viewByFI.verifyManualAcntFormat("TESTDATA1", "OptionsUnderMoreBtn");
	}

	@Test(description = "AT-84306,AT-92894:The following options must be available after clicking the ellipsification of Card account  Go to Site,Account Setting,Alert Setting,View Trend ", groups = {
			"DesktopBrowsers" }, priority = 26, dependsOnMethods = "login", enabled = true)
	public void VerifyCardMoreBtnOptions() throws InterruptedException {
		viewByFI.clickingOnMoreBtn("Super CD Plus");
		viewByFI.verifyManualAcntFormat("Super CD Plus", "OptionsUnderMoreBtn");
	}

	@Test(description = "AT-84306,AT-92894:The following options must be available after clicking the ellipsification of Investment account  Go to Site,Account Setting,Alert Setting,View Trend ", groups = {
			"DesktopBrowsers" }, priority = 27, dependsOnMethods = "login", enabled = true)
	public void VerifyInvMoreBtnOptions() throws InterruptedException {
		viewByFI.clickingOnMoreBtn("Investment Account 401a");
		viewByFI.verifyManualAcntFormat("Investment Account 401a", "OptionsUnderInvMoreBtn");
	}

	@Test(description = "AT-84306,AT-92894:The following options must be available after clicking the ellipsification of Loan account  Go to Site,Account Setting,Alert Setting,View Trend ", groups = {
			"DesktopBrowsers" }, priority = 28, dependsOnMethods = "login", enabled = true)
	public void VerifyLoanMoreBtnOptions() throws InterruptedException {
		viewByFI.clickingOnMoreBtn("LINE OF CREDIT");
		viewByFI.verifyManualAcntFormat("LINE OF CREDIT", "OptionsUnderMoreBtn");
	}

	@Test(description = "AT-84306,AT-92894:The following options must be available after clicking the ellipsification of Bill account  Go to Site,Account Setting,Alert Setting,View Trend ", groups = {
			"DesktopBrowsers" }, priority = 29, dependsOnMethods = "login", enabled = true)
	public void VerifyBillsMoreBtnOptions() throws InterruptedException {
		viewByFI.clickingOnMoreBtn("DAG BILLING");
		viewByFI.verifyManualAcntFormat("DAG BILLING", "OptionsUnderBillAndRewardMoreBtn");
	}

	@Test(description = "AT-84306,AT-92894:The following options must be available after clicking the ellipsification of insurance account  Go to Site,Account Setting,Alert Setting,View Trend ", groups = {
			"DesktopBrowsers" }, priority = 30, dependsOnMethods = "login", enabled = true)
	public void VerifyInsuranceMoreBtnOptions() throws InterruptedException {
		viewByFI.clickingOnMoreBtn("DAG INSURANCE");
		viewByFI.verifyManualAcntFormat("DAG INSURANCE", "OptionsUnderMoreBtn");
	}

	@Test(description = "AT-84306,AT-92894:The following options must be available after clicking the ellipsification of rewards account  Go to Site,Account Setting,Alert Setting,View Trend ", groups = {
			"DesktopBrowsers" }, priority = 31, dependsOnMethods = "login", enabled = true)
	public void VerifyRewardsMoreBtnOptions() throws InterruptedException {
		viewByFI.clickingOnMoreBtn("YODLEE");
		viewByFI.verifyManualAcntFormat("YODLEE", "OptionsUnderBillAndRewardMoreBtn");
	}

	@Test(description = "AT-84306,AT-92894:The following options must be available after clicking the ellipsification of manual account  Go to Site,Account Setting,Alert Setting,View Trend ", groups = {
			"DesktopBrowsers" }, priority = 32, dependsOnMethods = "login", enabled = true)
	public void VerifyManualMoreBtnOptions() throws InterruptedException {
		viewByFI.clickingOnMoreBtn("MyAccountbank");
		viewByFI.verifyManualAcntFormat("MyAccountbank", "OptionsUnderManualMoreBtn");
	}

	@Test(description = "Verify the link account button appears ", groups = { "DesktopBrowsers",
			"Smoke" }, dependsOnMethods = "login", priority = 33, enabled = true)
	public void linkAccountBtnEnableChk() throws InterruptedException {

		Assert.assertTrue(acntLandingPage.LinkActBtn().isDisplayed());
	}

}
