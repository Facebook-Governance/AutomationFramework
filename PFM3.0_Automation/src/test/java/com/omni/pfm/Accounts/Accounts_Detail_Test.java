/**
* Copyright (c) 2019 Yodlee Inc. All Rights Reserved. 
*
* This software is the confidential and proprietary information of
* Yodlee, Inc. Use is subject to license terms.
*
* @author sbhat1
*/
package com.omni.pfm.Accounts;

import java.awt.AWTException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import com.omni.pfm.PageProcessor.PageParser;
import com.omni.pfm.testBase.TestBase;
import com.omni.pfm.utility.PropsUtil;
import com.omni.pfm.utility.SeleniumUtil;

public class Accounts_Detail_Test extends TestBase {
	Logger logger = LoggerFactory.getLogger(Accounts_GroupValidation_Test.class);
	Accounts_Details_Loc acntDetails;
	AccountsLandingPage_Loc acntLandingPage;
	Account_Go_To_Site_Loc accountsGoToSite;

	@BeforeClass()
	public void init() throws AWTException, InterruptedException {
		doInitialization("Accounts");

		TestBase.tc = TestBase.extent.startTest("Login", "Login");
		TestBase.test.appendChild(TestBase.tc);
		acntDetails = new Accounts_Details_Loc(d);
		acntLandingPage = new AccountsLandingPage_Loc(d);
		accountsGoToSite = new Account_Go_To_Site_Loc(d);
	}

	@Test(description = "login to the user", groups = { "DesktopBrowsers,sanity" }, priority = 1, enabled = true)
	public void login() throws Exception {
		d.navigate().refresh();
		SeleniumUtil.waitForPageToLoad();
		PageParser.forceNavigate("AccountsPage", d);
		SeleniumUtil.waitForPageToLoad();
	}

	@Test(description = "AT-84015,AT-84250,AT-84014,AT-84249:Verify that user should be navigated to Transactions finapp after clicking any account row level.", groups = {
			"DesktopBrowsers,sanity" }, priority = 2, dependsOnMethods = "login", enabled = true)
	public void verifyTransctionWindowTrending() throws InterruptedException {

		SeleniumUtil.waitForPageToLoad(3000);
		
		try {
			acntLandingPage.accountslayout().isDisplayed();
		}catch(Throwable e) {
			PageParser.forceNavigate("AccountsPage", d);
			SeleniumUtil.waitForPageToLoad(3000);
		}
		
		SeleniumUtil.click(acntDetails.containerAccounts("1", "1", "1"));
		SeleniumUtil.waitForPageToLoad(2000);
		Assert.assertEquals(acntLandingPage.AccountsPageHeader().getText(),
				PropsUtil.getDataPropertyValue("TransactionTitle"));
	}

	@Test(description = "AT-84019,AT-84252,AT-84017,AT-84016,AT-84251,AT-84018,AT-84253:verify account details on transaction page.", groups = {
			"DesktopBrowsers,sanity" }, priority = 3, dependsOnMethods = "verifyTransctionWindowTrending", enabled = true)
	public void verifyAcntsDetailsInTxn() {

		acntDetails.verifyAcntDetailsInTxn("acntDetailsInTransactn");
		SeleniumUtil.waitForPageToLoad();
		Assert.assertEquals(acntDetails.acntDetails2InTransactn().get(0).getText().trim(),
				PropsUtil.getDataPropertyValue("AcntTypeName").trim());
		Assert.assertTrue(acntDetails.isUpdatedInfoDisplayed());
	}

	@Test(description = "AT-84039,AT-84274,AT-83932:verify back button functionlaity", groups = {
			"DesktopBrowsers,sanity" }, priority = 4, dependsOnMethods = "verifyTransctionWindowTrending", enabled = true)
	public void verifyBackBtnFunctionality() {
		try {
			SeleniumUtil.waitForPageToLoad(3000);
			acntDetails.clickingOnBackToAcntsBtn();
			Assert.assertEquals(acntLandingPage.AccountsPageHeader().getText(),
					PropsUtil.getDataPropertyValue("Accounts_Header1").trim());
		} catch (Exception e) {
			acntDetails.clickingOnBackToAcntsBtn();
			Assert.assertEquals(acntLandingPage.AccountsPageHeader().getText(),
					PropsUtil.getDataPropertyValue("Accounts_Header1").trim());
		}
	}

	@Test(description = "AT-84015,AT-84250,AT-84014,AT-84249,AT-84018,AT-84253:Verify that user should be navigated to Transactions finapp after clicking any account row level.", groups = {
			"DesktopBrowsers,sanity" }, priority = 5, dependsOnMethods = "verifyBackBtnFunctionality", enabled = true)
	public void verifyTransctionWindowforCard() throws InterruptedException {

		SeleniumUtil.click(acntDetails.containerAccounts("1", "2", "1"));
		SeleniumUtil.waitForPageToLoad();
		Assert.assertEquals(acntLandingPage.AccountsPageHeader().getText(),
				PropsUtil.getDataPropertyValue("TransactionTitle"));
	}

	@Test(description = "AT-84019,AT-84252,AT-84016,AT-84251:AT-84017,AT-84018,AT-84253:verify card account details on transaction page.", groups = {
			"DesktopBrowsers,sanity" }, priority = 6, dependsOnMethods = "verifyTransctionWindowforCard", enabled = true)
	public void verifyAcntsDetailsInTxnForCard() {

		acntDetails.verifyAcntDetailsInTxn("CardAcntDetailsInTxn");
		Assert.assertEquals(acntDetails.acntDetails2InTransactn().get(0).getText().trim(),
				PropsUtil.getDataPropertyValue("CardAcntTypeName").trim());
		Assert.assertTrue(acntDetails.isUpdatedInfoDisplayed());
	}

	@Test(description = "AT-84015,AT-84250,AT-84014,AT-84249:Verify that user should be navigated to Transactions finapp after clicking any account row level.", groups = {
			"DesktopBrowsers,sanity" }, priority = 7, dependsOnMethods = "verifyTransctionWindowforCard", enabled = true)
	public void verifyTransctionWindowforInv() throws InterruptedException {

		acntDetails.clickingOnBackToAcntsBtn();
		SeleniumUtil.waitForPageToLoad();
		SeleniumUtil.click(acntDetails.containerAccounts("1", "3", "1"));
		SeleniumUtil.waitForPageToLoad(2000);
		Assert.assertEquals(acntLandingPage.AccountsPageHeader().getText(),
				PropsUtil.getDataPropertyValue("TransactionTitle"));
	}

	@Test(description = "AT-84019,AT-84252,AT-84017,AT-84016,AT-84251,AT-84018,AT-84253:verify investment account details on transaction page.", groups = {
			"DesktopBrowsers,sanity" }, priority = 8, dependsOnMethods = "verifyTransctionWindowforInv", enabled = true)
	public void verifyAcntsDetailsInTxnForInv() {
		Assert.assertEquals(acntDetails.acntDetails2InTransactn().get(0).getText().trim(),
				PropsUtil.getDataPropertyValue("InvAcntTypeName").trim());
		Assert.assertTrue(acntDetails.isUpdatedInfoDisplayed());
	}

	@Test(description = "AT-84015,AT-84250,AT-84014,AT-84249:Verify that user should be navigated to Transactions finapp after clicking any account row level.", groups = {
			"DesktopBrowsers,sanity" }, priority = 9, dependsOnMethods = "verifyTransctionWindowforInv", enabled = true)
	public void verifyTransctionWindowforLoan() throws InterruptedException {

		acntDetails.clickingOnBackToAcntsBtn();
		SeleniumUtil.waitForPageToLoad();
		SeleniumUtil.click(acntDetails.containerAccounts("1", "4", "1"));
		SeleniumUtil.waitForPageToLoad(2000);
		Assert.assertEquals(acntLandingPage.AccountsPageHeader().getText(),
				PropsUtil.getDataPropertyValue("TransactionTitle"));
	}

	@Test(description = "AT-84019,AT-84252,AT-84017,AT-84016,AT-84251,AT-84018,AT-84253:verify loan account details on transaction page.", groups = {
			"DesktopBrowsers,sanity" }, priority = 10, dependsOnMethods = "verifyTransctionWindowforLoan", enabled = true)
	public void verifyAcntsDetailsInTxnForLoan() {

		acntDetails.verifyAcntDetailsInTxn("LoanAcntDetailsInTxn");
		SeleniumUtil.waitForPageToLoad(3000);
		Assert.assertEquals(acntDetails.acntDetails2InTransactn().get(0).getText().trim(),
				PropsUtil.getDataPropertyValue("LoanAcntType").trim());
		Assert.assertTrue(acntDetails.isUpdatedInfoDisplayed());
	}

	@Test(description = "AT-84015,AT-84250,AT-84014,AT-84249:Verify that user should be navigated to Transactions finapp after clicking any account row level.", groups = {
			"DesktopBrowsers,sanity" }, priority = 11, dependsOnMethods = "verifyTransctionWindowforLoan", enabled = true)
	public void verifyTransctionWindowforInsurance() throws InterruptedException {

		acntDetails.clickingOnBackToAcntsBtn();
		SeleniumUtil.click(acntDetails.containerAccounts("1", "5", "1"));
		SeleniumUtil.waitForPageToLoad(2000);
		Assert.assertEquals(acntLandingPage.AccountsPageHeader().getText(),
				PropsUtil.getDataPropertyValue("TransactionTitle"));
	}

	@Test(description = "AT-84019,AT-84252,AT-84017,AT-84016,AT-84251,AT-84018,AT-84253:verify insurance account details on transaction page.", groups = {
			"DesktopBrowsers,sanity" }, priority = 12, dependsOnMethods = "verifyTransctionWindowforInsurance", enabled = true)
	public void verifyAcntsDetailsInTxnForInsurance() {

		acntDetails.verifyAcntDetailsInTxn("InsuranceAcntDetailsInTxn");
		Assert.assertEquals(acntDetails.acntDetails2InTransactn().get(0).getText().trim(),
				PropsUtil.getDataPropertyValue("InsuranceAcntTypeName").trim());
		Assert.assertTrue(acntDetails.isUpdatedInfoDisplayed());
	}

	@Test(description = "AT-84042,AT-84275,AT-84040:verify account detail popup for bill types account", groups = {
			"DesktopBrowsers,sanity" }, priority = 13, dependsOnMethods = "verifyTransctionWindowforInsurance", enabled = true)
	public void verifyAcntDetailforBills() throws InterruptedException {

		acntDetails.clickingOnBackToAcntsBtn();
		SeleniumUtil.waitForPageToLoad(3000);
		SeleniumUtil.click(acntDetails.containerAccounts("1", "6", "1"));
		SeleniumUtil.waitForPageToLoad(2000);
		Assert.assertTrue(acntDetails.AcntDetailPopupCrossIcon().isDisplayed());
		Assert.assertEquals(acntDetails.AccountDetailPopupHeader().get(0).getText().trim(),
				PropsUtil.getDataPropertyValue("AccountDetailpopHeader").trim());
	}

	@Test(description = "AT-84041,AT-84276:Verify the All the bills and rewards accounts attributes displayed in the Accounts details floater", groups = {
			"DesktopBrowsers,sanity" }, priority = 14, dependsOnMethods = "verifyAcntDetailforBills", enabled = true)
	public void verifyingAcntDetail() throws InterruptedException {
		acntDetails.verifyingAcntDetailRightSide("verifyingAcntDetailRightSide");
		Assert.assertTrue(acntDetails.acntDetailPopupAcntNumber().get(2).isDisplayed());
		Assert.assertEquals(acntDetails.acntDetailPopupAcntNumber().get(0).getText().trim(),
				PropsUtil.getDataPropertyValue("acntDetailPopupAcntNumber").trim());
	}

	@Test(description = "AT-84041,AT-84276:Verify the All the bills and rewards accounts attributes displayed in the Accounts details floater", groups = {
			"DesktopBrowsers,sanity" }, priority = 15, dependsOnMethods = "verifyAcntDetailforBills", enabled = true)
	public void verifyingBillsAcntDetailContent() throws InterruptedException {
		if (accountsGoToSite.isexpandCollapseArrow_Mob()) {
			SeleniumUtil.click(accountsGoToSite.expandCollapseArrow_Mob());
		} else {
			acntDetails.clickingOnExpandCollapseBtn();
		}
		SeleniumUtil.waitForPageToLoad(2000);
		acntDetails.verifyAcntDetailsPopupContent("AcntDetailPopupContent");
	}

	@Test(description = "AT-84277,AT-84275,AT-84040:verify account detail popup for bill types account", groups = {
			"DesktopBrowsers,sanity" }, priority = 16, dependsOnMethods = "login", enabled = true)
	public void verifyAcntDetailforRewards() throws InterruptedException {

		acntDetails.clickingOnCrossIcon();
		SeleniumUtil.waitForPageToLoad(2000);
		SeleniumUtil.click(acntDetails.containerAccounts("1", "7", "1"));
		SeleniumUtil.waitForPageToLoad(2000);
		Assert.assertTrue(acntDetails.AcntDetailPopupCrossIcon().isDisplayed());
		Assert.assertEquals(acntDetails.AccountDetailPopupHeader().get(0).getText().trim(),
				PropsUtil.getDataPropertyValue("AccountDetailpopHeader").trim());
	}

	@Test(description = "AT-84041,AT-84276:Verify the All the bills and rewards accounts attributes displayed in the Accounts details floater", groups = {
			"DesktopBrowsers,sanity" }, priority = 17, dependsOnMethods = "verifyAcntDetailforRewards", enabled = true)
	public void verifyingRewardsAcntDetail() throws InterruptedException {
		acntDetails.verifyingAcntDetailRightSide("verifyingAcntDetailForReward");
		Assert.assertTrue(acntDetails.acntDetailPopupAcntNumber().get(2).isDisplayed());
		Assert.assertEquals(acntDetails.acntDetailPopupAcntNumber().get(0).getText().trim(),
				PropsUtil.getDataPropertyValue("acntDetailPopupAcntNumberReward").trim());
	}

	@Test(description = "AT-84041,AT-84276:Verify the All the bills and rewards accounts attributes displayed in the Accounts details floater", groups = {
			"DesktopBrowsers,sanity" }, priority = 18, dependsOnMethods = "verifyAcntDetailforRewards", enabled = true)
	public void verifyingAcntDetailContent() throws InterruptedException {
		if (accountsGoToSite.isexpandCollapseArrow_Mob()) {
			SeleniumUtil.click(accountsGoToSite.expandCollapseArrow_Mob());
		} else {
			acntDetails.clickingOnExpandCollapseBtn();
		}
		SeleniumUtil.waitForPageToLoad(2000);
		acntDetails.verifyAcntDetailsPopupContent("AcntDetailPopupContentReward");
	}
	//${Acc.Name}
}
