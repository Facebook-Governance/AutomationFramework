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

import org.openqa.selenium.Keys;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.google.inject.Key;
import com.omni.pfm.PageProcessor.PageParser;
import com.omni.pfm.pages.AccountAddition.AccountAddition;
import com.omni.pfm.pages.Login.LoginPage;
import com.omni.pfm.testBase.TestBase;
import com.omni.pfm.utility.FinAppUtil;
import com.omni.pfm.utility.PropsUtil;
import com.omni.pfm.utility.SeleniumUtil;

public class Accounts_ManualAccountsSettingPopUp_Test extends TestBase {
	Logger logger = LoggerFactory.getLogger(Accounts_AccountSettingPopUp_Test.class);

	LoginPage login;
	AccountAddition accountAdd;
	Accounts_AccountSettingPopUp_Loc accountSettiingsPopup;
	InvestmentFormatChange_Loc AccountsLandingPage;
	Accounts_ManualAccountSettingPopUp_Loc manualAcntSettingPopup;
	FinAppUtil finappUtil;

	@BeforeClass()
	public void init() throws Exception {
		doInitialization("Accounts");

		TestBase.tc = TestBase.extent.startTest("Login", "Login");
		TestBase.test.appendChild(TestBase.tc);
		login = new LoginPage();
		accountAdd = new AccountAddition();
		accountSettiingsPopup = new Accounts_AccountSettingPopUp_Loc(d);
		AccountsLandingPage = new InvestmentFormatChange_Loc(d);
		manualAcntSettingPopup = new Accounts_ManualAccountSettingPopUp_Loc(d);
		finappUtil = new FinAppUtil(d);
	}

	@Test(description = "ACCT-01_01: creating user and adding account.", groups = {
			"DesktopBrowsers,sanity" }, priority = 1, enabled = true)
	public void login() throws Exception {
		SeleniumUtil.refresh(d);
		PageParser.forceNavigate("AccountsPage", d);
	}

	@Test(description = "AT-93045,AT-93048,AT-93046,AT-93047:Verify left side labels in Account settings pop up for manual accounts user", groups = {
			"Regression" }, priority = 2, dependsOnMethods = "login", enabled = true)
	public void verifyLeftLabelsForManualAcnt() {
		logger.info(
				"verify account setting popup for manual account: Account name, Account type  NickName(optional), Account Number (Optional) Categorize all transaction to (optional)Tag all transactions as(optional), Balance Currency, Current Balance, URL(Optional), Username (Optional), Password(Optional),show Account in Account Summary, Delete account link and Save change button should be displayed.");
		finappUtil.intitutionViewClickOnAccountSettings("PayTM Wallet", "");

		try {
			manualAcntSettingPopup.accountslayout().isDisplayed();
		}catch(Throwable e) {
			PageParser.forceNavigate("AccountsPage", d);
			SeleniumUtil.waitForPageToLoad(3000);
		}

		
		Assert.assertEquals(manualAcntSettingPopup.ManualAcntNameLbl().getText().trim(),
				PropsUtil.getDataPropertyValue("ManualAcntNameLbl").trim());
		Assert.assertEquals(manualAcntSettingPopup.ManualAcntAcntTypeLbl().getText().trim(),
				PropsUtil.getDataPropertyValue("ManualAcntAcntTypeLbl").trim());
		Assert.assertEquals(manualAcntSettingPopup.ManualAcntNickNameLbl().getText().trim(),
				PropsUtil.getDataPropertyValue("ManualAcntNickNameLbl").trim());
		Assert.assertEquals(manualAcntSettingPopup.ManualAcntNumberLbl().getText().trim(),
				PropsUtil.getDataPropertyValue("ManualAcntNumberLbl").trim());
		Assert.assertEquals(manualAcntSettingPopup.ManualCatAllTransactionLbl().getText().trim(),
				PropsUtil.getDataPropertyValue("ManualCatAllTransactionLbl").trim());
		Assert.assertEquals(manualAcntSettingPopup.ManualAcntTagAllTransactnLbl().getText().trim(),
				PropsUtil.getDataPropertyValue("ManualAcntTagAllTransactnLbl").trim());
	}

	@Test(description = "AT-93048,AT-93067,AT-93017:Verify left side labels in Account settings pop up for manual accounts user", groups = {
			"Regression" }, priority = 3, dependsOnMethods = "verifyLeftLabelsForManualAcnt", enabled = true)
	public void verifyLeftLabelsForManualAcnt2() {

		logger.info(
				"verify account setting popup for manual account: Account name, Account type  NickName(optional), Account Number (Optional) Categorize all transaction to (optional)Tag all transactions as(optional), Balance Currency, Current Balance, URL(Optional), Username (Optional), Password(Optional),show Account in Account Summary, Delete account link and Save change button should be displayed.");

		Assert.assertEquals(manualAcntSettingPopup.ManualAcntBalCurrencyLbl().getText().trim(),
				PropsUtil.getDataPropertyValue("ManualAcntBalCurrencyLbl").trim());
		Assert.assertEquals(manualAcntSettingPopup.ManualAcntCurrentBalLbl().getText().trim(),
				PropsUtil.getDataPropertyValue("ManualAcntCurrentBalLbl").trim());
		Assert.assertEquals(manualAcntSettingPopup.ManualAcntURLLbl().getText().trim(),
				PropsUtil.getDataPropertyValue("ManualAcntURLLbl").trim());
		Assert.assertEquals(manualAcntSettingPopup.ManualAcntUserNameLbl().getText().trim(),
				PropsUtil.getDataPropertyValue("ManualAcntUserNameLbl").trim());
		Assert.assertEquals(manualAcntSettingPopup.ManualAcntPasswordLbl().getText().trim(),
				PropsUtil.getDataPropertyValue("ManualAcntPasswordLbl").trim());
		Assert.assertEquals(manualAcntSettingPopup.ManualAcntShowAcntInSummaryLbl().getText().trim(),
				PropsUtil.getDataPropertyValue("ManualAcntShowAcntInSummaryLbl").trim());
	}

	@Test(description = "AT-93048,AT-93095,AT-93105:Verify left side labels in Account settings pop up for manual accounts user", groups = {
			"Regression" }, priority = 4, dependsOnMethods = "verifyLeftLabelsForManualAcnt", enabled = true)
	public void verifyAllBtnsForManualAcnt() {

		Assert.assertTrue(manualAcntSettingPopup.ManualAcntDeleteBtn().isDisplayed());
		Assert.assertTrue(manualAcntSettingPopup.ManualAcntShowAcntInSummaryToggle().isDisplayed());
		Assert.assertTrue(manualAcntSettingPopup.ManualAcntSaveChanges().isDisplayed());
	}

	@Test(description = "AT-93050,AT-93045,AT-93046,AT-93047,AT-93018:Verify left side labels in Account settings pop up for manual rewards accounts user", groups = {
			"Regression" }, priority = 5, dependsOnMethods = "verifyLeftLabelsForManualAcnt", enabled = true)
	public void verifyLeftLabelsForRewardsManualAcnt() {

		accountSettiingsPopup.closePopup();
		logger.info(
				"verify account setting popup for manual account: Account name, Account type  NickName(optional), Account Number (Optional) Categorize all transaction to (optional)Tag all transactions as(optional), Balance Currency, Current Balance, URL(Optional), Username (Optional), Password(Optional),show Account in Account Summary, Delete account link and Save change button should be displayed.");
		finappUtil.intitutionViewClickOnAccountSettings("Citrus Points", "");

		Assert.assertEquals(manualAcntSettingPopup.ManualAcntNameLbl().getText().trim(),
				PropsUtil.getDataPropertyValue("ManualAcntNameLbl").trim());
		Assert.assertEquals(manualAcntSettingPopup.ManualAcntAcntTypeLbl().getText().trim(),
				PropsUtil.getDataPropertyValue("ManualAcntAcntTypeLbl").trim());
		Assert.assertEquals(manualAcntSettingPopup.ManualAcntNickNameLbl().getText().trim(),
				PropsUtil.getDataPropertyValue("ManualAcntNickNameLbl").trim());
		Assert.assertEquals(manualAcntSettingPopup.ManualAcntMemoLbl().getText().trim(),
				PropsUtil.getDataPropertyValue("ManualAcntMemoLbl").trim());
		Assert.assertEquals(manualAcntSettingPopup.ManualAcntNumberLbl().getText().trim(),
				PropsUtil.getDataPropertyValue("ManualAcntNumberLbl").trim());

	}

	@Test(description = "AT-93050,AT-93017:Verify left side labels in Account settings pop up for manual rewards accounts user", groups = {
			"Regression" }, priority = 6, dependsOnMethods = "verifyLeftLabelsForRewardsManualAcnt", enabled = true)
	public void verifyLeftLabelsForRewardsManualAcnt2() {

		logger.info(
				"verify account setting popup for manual rewards account: Account name, Account type  NickName(optional), Account Number (Optional) Categorize all transaction to (optional)Tag all transactions as(optional), Balance Currency, Current Balance, URL(Optional), Username (Optional), Password(Optional),show Account in Account Summary, Delete account link and Save change button should be displayed.");

		Assert.assertEquals(manualAcntSettingPopup.ManualAcntUnitsLbl().getText().trim(),
				PropsUtil.getDataPropertyValue("ManualAcntUnitsLbl").trim());
		Assert.assertEquals(manualAcntSettingPopup.ManualAcntCurrentBalLbl().getText().trim(),
				PropsUtil.getDataPropertyValue("ManualAcntCurrentBalLbl").trim());
		Assert.assertEquals(manualAcntSettingPopup.ManualAcntURLLbl().getText().trim(),
				PropsUtil.getDataPropertyValue("ManualAcntURLLbl").trim());
		Assert.assertEquals(manualAcntSettingPopup.ManualAcntUserNameLbl().getText().trim(),
				PropsUtil.getDataPropertyValue("ManualAcntUserNameLbl").trim());
		Assert.assertEquals(manualAcntSettingPopup.ManualAcntPasswordLbl().getText().trim(),
				PropsUtil.getDataPropertyValue("ManualAcntPasswordLbl").trim());
		Assert.assertEquals(manualAcntSettingPopup.ManualAcntShowAcntInSummaryLbl().getText().trim(),
				PropsUtil.getDataPropertyValue("ManualAcntShowAcntInSummaryLbl").trim());
	}

	@Test(description = "AT-93050,AT-93095,AT-93105:Verify left side labels in Account settings pop up for manual rewards accounts user", groups = {
			"Regression" }, priority = 7, dependsOnMethods = "verifyLeftLabelsForRewardsManualAcnt", enabled = true)
	public void verifyAllBtnsForRewardsManualAcnt() {
		Assert.assertTrue(manualAcntSettingPopup.ManualAcntDeleteBtn().isDisplayed());
		Assert.assertTrue(manualAcntSettingPopup.ManualAcntShowAcntInSummaryToggle().isDisplayed());
		Assert.assertTrue(manualAcntSettingPopup.ManualAcntSaveChanges().isDisplayed());
	}

	@Test(description = "AT-93045,AT-93051,AT-93046,AT-93047:Verify left side labels in Account settings pop up for manual assets accounts user", groups = {
			"Regression" }, priority = 8, dependsOnMethods = "verifyLeftLabelsForRewardsManualAcnt", enabled = true)
	public void verifyLeftLabelsForAssertsManualAcnt() {

		accountSettiingsPopup.closePopup();
		finappUtil.intitutionViewClickOnAccountSettings("Audi Q7", "");
		logger.info(
				"verify account setting popup for manual account: Account name, Account type  NickName(optional), Account Number (Optional) Categorize all transaction to (optional)Tag all transactions as(optional), Balance Currency, Current Balance, URL(Optional), Username (Optional), Password(Optional),show Account in Account Summary, Delete account link and Save change button should be displayed.");

		Assert.assertEquals(manualAcntSettingPopup.ManualAcntNameLbl().getText().trim(),
				PropsUtil.getDataPropertyValue("ManualAcntNameLbl").trim());
		Assert.assertEquals(manualAcntSettingPopup.ManualAcntAcntTypeLbl().getText().trim(),
				PropsUtil.getDataPropertyValue("ManualAcntAcntTypeLbl").trim());
		Assert.assertEquals(manualAcntSettingPopup.ManualAcntNickNameLbl().getText().trim(),
				PropsUtil.getDataPropertyValue("ManualAcntNickNameLbl").trim());
		Assert.assertEquals(manualAcntSettingPopup.ManualAcntMemoLbl().getText().trim(),
				PropsUtil.getDataPropertyValue("ManualAcntMemoLbl").trim());
	}

	@Test(description = "AT-93051,AT-93017:Verify left side labels in Account settings pop up for manual assets accounts user", groups = {
			"Regression" }, priority = 9, dependsOnMethods = "verifyLeftLabelsForAssertsManualAcnt", enabled = true)
	public void verifyLeftLabelsForAssetsManualAcnt2() {

		logger.info(
				"verify account setting popup for manual account: Account name, Account type  NickName(optional), Account Number (Optional) Categorize all transaction to (optional)Tag all transactions as(optional), Balance Currency, Current Balance, URL(Optional), Username (Optional), Password(Optional),show Account in Account Summary, Delete account link and Save change button should be displayed.");

		Assert.assertEquals(manualAcntSettingPopup.ManualAcntBalCurrencyLbl().getText().trim(),
				PropsUtil.getDataPropertyValue("ManualAcntBalCurrencyLbl").trim());
		Assert.assertEquals(manualAcntSettingPopup.ManualAcntCurrentBalLbl().getText().trim(),
				PropsUtil.getDataPropertyValue("ManualAcntCurrentBalLbl").trim());
		Assert.assertEquals(manualAcntSettingPopup.ManualAcntURLLbl().getText().trim(),
				PropsUtil.getDataPropertyValue("ManualAcntURLLbl").trim());
		Assert.assertEquals(manualAcntSettingPopup.ManualAcntUserNameLbl().getText().trim(),
				PropsUtil.getDataPropertyValue("ManualAcntUserNameLbl").trim());
		Assert.assertEquals(manualAcntSettingPopup.ManualAcntPasswordLbl().getText().trim(),
				PropsUtil.getDataPropertyValue("ManualAcntPasswordLbl").trim());
		Assert.assertEquals(manualAcntSettingPopup.ManualAcntShowAcntInSummaryLbl().getText().trim(),
				PropsUtil.getDataPropertyValue("ManualAcntShowAcntInSummaryLbl").trim());
	}

	@Test(description = "AT-93051,AT-93095,AT-93105:Verify left side labels in Account settings pop up for manual assets accounts user", groups = {
			"Regression" }, priority = 10, dependsOnMethods = "verifyLeftLabelsForAssertsManualAcnt", enabled = true)
	public void verifyAllBtnsForAssetsManualAcnt() {
		Assert.assertTrue(manualAcntSettingPopup.ManualAcntDeleteBtn().isDisplayed());
		Assert.assertTrue(manualAcntSettingPopup.ManualAcntShowAcntInSummaryToggle().isDisplayed());
		Assert.assertTrue(manualAcntSettingPopup.ManualAcntSaveChanges().isDisplayed());
	}

	@Test(description = "AT-93045,AT-93051,AT-93046,AT-93047:Verify left side labels in Account settings pop up for manual liability accounts user", groups = {
			"Regression" }, priority = 11, dependsOnMethods = "verifyLeftLabelsForAssertsManualAcnt", enabled = true)
	public void verifyLeftLabelsForliabilityManualAcnt() {
		accountSettiingsPopup.closePopup();
		logger.info(
				"verify account setting popup for manual account: Account name, Account type  NickName(optional), Account Number (Optional) Categorize all transaction to (optional)Tag all transactions as(optional), Balance Currency, Current Balance, URL(Optional), Username (Optional), Password(Optional),show Account in Account Summary, Delete account link and Save change button should be displayed.");
		finappUtil.intitutionViewClickOnAccountSettings("Swins Liabilities", "");

		Assert.assertEquals(manualAcntSettingPopup.ManualAcntNameLbl().getText().trim(),
				PropsUtil.getDataPropertyValue("ManualAcntNameLbl").trim());
		Assert.assertEquals(manualAcntSettingPopup.ManualAcntAcntTypeLbl().getText().trim(),
				PropsUtil.getDataPropertyValue("ManualAcntAcntTypeLbl").trim());
		Assert.assertEquals(manualAcntSettingPopup.ManualAcntNickNameLbl().getText().trim(),
				PropsUtil.getDataPropertyValue("ManualAcntNickNameLbl").trim());
		Assert.assertEquals(manualAcntSettingPopup.ManualAcntMemoLbl().getText().trim(),
				PropsUtil.getDataPropertyValue("ManualAcntMemoLbl").trim());
	}

	@Test(description = "AT-93051,AT-93017:Verify left side labels in Account settings pop up for manual liability accounts user", groups = {
			"Regression" }, priority = 12, dependsOnMethods = "verifyLeftLabelsForliabilityManualAcnt", enabled = true)
	public void verifyLeftLabelsForliabilityManualAcnt2() {

		logger.info(
				"verify account setting popup for manual account: Account name, Account type  NickName(optional), Account Number (Optional) Categorize all transaction to (optional)Tag all transactions as(optional), Balance Currency, Current Balance, URL(Optional), Username (Optional), Password(Optional),show Account in Account Summary, Delete account link and Save change button should be displayed.");

		Assert.assertEquals(manualAcntSettingPopup.ManualAcntBalCurrencyLbl().getText().trim(),
				PropsUtil.getDataPropertyValue("ManualAcntBalCurrencyLbl").trim());
		Assert.assertEquals(manualAcntSettingPopup.ManualAcntCurrentBalLbl().getText().trim(),
				PropsUtil.getDataPropertyValue("ManualAcntCurrentBalLbl").trim());
		Assert.assertEquals(manualAcntSettingPopup.ManualAcntURLLbl().getText().trim(),
				PropsUtil.getDataPropertyValue("ManualAcntURLLbl").trim());
		Assert.assertEquals(manualAcntSettingPopup.ManualAcntUserNameLbl().getText().trim(),
				PropsUtil.getDataPropertyValue("ManualAcntUserNameLbl").trim());
		Assert.assertEquals(manualAcntSettingPopup.ManualAcntPasswordLbl().getText().trim(),
				PropsUtil.getDataPropertyValue("ManualAcntPasswordLbl").trim());
		Assert.assertEquals(manualAcntSettingPopup.ManualAcntShowAcntInSummaryLbl().getText().trim(),
				PropsUtil.getDataPropertyValue("ManualAcntShowAcntInSummaryLbl").trim());
	}

	@Test(description = "AT-93051,AT-93095,AT-93105:Verify left side labels in Account settings pop up for manual liability accounts user", groups = {
			"Regression" }, priority = 13, dependsOnMethods = "verifyLeftLabelsForliabilityManualAcnt", enabled = true)
	public void verifyAllBtnsForliabilityManualAcnt() {
		Assert.assertTrue(manualAcntSettingPopup.ManualAcntDeleteBtn().isDisplayed());
		Assert.assertTrue(manualAcntSettingPopup.ManualAcntShowAcntInSummaryToggle().isDisplayed());
		Assert.assertTrue(manualAcntSettingPopup.ManualAcntSaveChanges().isDisplayed());
	}

	@Test(description = "AT-93045,AT-93052,AT-93046,AT-93047:Verify left side labels in Account settings pop up for manual credit accounts user", groups = {
			"Regression" }, priority = 14, dependsOnMethods = "verifyLeftLabelsForliabilityManualAcnt", enabled = true)
	public void verifyLeftLabelsForManualCreditAcnt() {
		accountSettiingsPopup.closePopup();
		logger.info(
				"verify account setting popup for manual account: Account name, Account type  NickName(optional), Account Number (Optional) Categorize all transaction to (optional)Tag all transactions as(optional), Balance Currency, Current Balance, URL(Optional), Username (Optional), Password(Optional),show Account in Account Summary, Delete account link and Save change button should be displayed.");
		finappUtil.intitutionViewClickOnAccountSettings("HDFC Platinum", "");

		Assert.assertEquals(manualAcntSettingPopup.ManualAcntNameLbl().getText().trim(),
				PropsUtil.getDataPropertyValue("ManualAcntNameLbl").trim());
		Assert.assertEquals(manualAcntSettingPopup.ManualAcntAcntTypeLbl().getText().trim(),
				PropsUtil.getDataPropertyValue("ManualAcntAcntTypeLbl").trim());
		Assert.assertEquals(manualAcntSettingPopup.ManualAcntNickNameLbl().getText().trim(),
				PropsUtil.getDataPropertyValue("ManualAcntNickNameLbl").trim());
		Assert.assertEquals(manualAcntSettingPopup.ManualAcntMemoLbl().getText().trim(),
				PropsUtil.getDataPropertyValue("ManualAcntMemoLbl").trim());
		Assert.assertEquals(manualAcntSettingPopup.ManualAcntNumberLbl().getText().trim(),
				PropsUtil.getDataPropertyValue("ManualAcntNumberLbl").trim());
		Assert.assertEquals(manualAcntSettingPopup.ManualAcntAmntDueLbl().getText().trim(),
				PropsUtil.getDataPropertyValue("ManualAcntAmntDueLbl").trim());
		Assert.assertEquals(manualAcntSettingPopup.ManualAcntFrequencyLbl().getText().trim(),
				PropsUtil.getDataPropertyValue("ManualAcntFrequencyLbl").trim());
		Assert.assertEquals(manualAcntSettingPopup.ManualAcntNextDueLbl().getText().trim(),
				PropsUtil.getDataPropertyValue("ManualAcntNextDueLbl").trim());

	}

	@Test(description = "AT-93052,AT-93067,AT-93017:Verify left side labels in Account settings pop up for manual credit accounts user", groups = {
			"Regression" }, priority = 15, dependsOnMethods = "verifyLeftLabelsForManualCreditAcnt", enabled = true)
	public void verifyLeftLabelsForManualCreditAcnt2() {

		logger.info(
				"verify account setting popup for manual account: Account name, Account type  NickName(optional), Account Number (Optional) Categorize all transaction to (optional)Tag all transactions as(optional), Balance Currency, Current Balance, URL(Optional), Username (Optional), Password(Optional),show Account in Account Summary, Delete account link and Save change button should be displayed.");

		// Assert.assertEquals(manualAcntSettingPopup.ManualAcntTagAllTransactnLbl(),
		// PropsUtil.getDataPropertyValue("ManualAcntTagAllTransactnLbl").trim());
		Assert.assertEquals(manualAcntSettingPopup.ManualCatAllTransactionLbl().getText().trim(),
				PropsUtil.getDataPropertyValue("ManualCatAllTransactionLbl").trim());
		Assert.assertEquals(manualAcntSettingPopup.ManualAcntBalCurrencyLbl().getText().trim(),
				PropsUtil.getDataPropertyValue("ManualAcntBalCurrencyLbl").trim());
		Assert.assertEquals(manualAcntSettingPopup.ManualAcntCurrentBalLbl().getText().trim(),
				PropsUtil.getDataPropertyValue("ManualAcntCurrentBalLbl").trim());
		Assert.assertEquals(manualAcntSettingPopup.ManualAcntURLLbl().getText().trim(),
				PropsUtil.getDataPropertyValue("ManualAcntURLLbl").trim());
		Assert.assertEquals(manualAcntSettingPopup.ManualAcntUserNameLbl().getText().trim(),
				PropsUtil.getDataPropertyValue("ManualAcntUserNameLbl").trim());
		Assert.assertEquals(manualAcntSettingPopup.ManualAcntPasswordLbl().getText().trim(),
				PropsUtil.getDataPropertyValue("ManualAcntPasswordLbl").trim());
		Assert.assertEquals(manualAcntSettingPopup.ManualAcntShowAcntInSummaryLbl().getText().trim(),
				PropsUtil.getDataPropertyValue("ManualAcntShowAcntInSummaryLbl").trim());
	}

	@Test(description = "AT-93052,AT-93095,AT-93105:Verify left side labels in Account settings pop up for manual credit accounts user", groups = {
			"Regression" }, priority = 16, dependsOnMethods = "verifyLeftLabelsForManualCreditAcnt", enabled = true)
	public void verifyAllBtnsForManualCreditAcnt() {
		Assert.assertTrue(manualAcntSettingPopup.ManualAcntDeleteBtn().isDisplayed());
		Assert.assertTrue(manualAcntSettingPopup.ManualAcntShowAcntInSummaryToggle().isDisplayed());
		Assert.assertTrue(manualAcntSettingPopup.ManualAcntSaveChanges().isDisplayed());
	}

	@Test(description = "AT-93045,AT-93053,AT-93046,AT-93047:Verify left side labels in Account settings pop up for manual loan accounts user", groups = {
			"Regression" }, priority = 17, dependsOnMethods = "verifyLeftLabelsForManualCreditAcnt", enabled = true)
	public void verifyLeftLabelsForManualLoanAcnt() {
		accountSettiingsPopup.closePopup();
		logger.info(
				"verify account setting popup for manual account: Account name, Account type  NickName(optional), Account Number (Optional) Categorize all transaction to (optional)Tag all transactions as(optional), Balance Currency, Current Balance, URL(Optional), Username (Optional), Password(Optional),show Account in Account Summary, Delete account link and Save change button should be displayed.");
		finappUtil.intitutionViewClickOnAccountSettings("PNB Housing", "");

		Assert.assertEquals(manualAcntSettingPopup.ManualAcntNameLbl().getText().trim(),
				PropsUtil.getDataPropertyValue("ManualAcntNameLbl").trim());
		Assert.assertEquals(manualAcntSettingPopup.ManualAcntAcntTypeLbl().getText().trim(),
				PropsUtil.getDataPropertyValue("ManualAcntAcntTypeLbl").trim());
		Assert.assertEquals(manualAcntSettingPopup.ManualAcntNickNameLbl().getText().trim(),
				PropsUtil.getDataPropertyValue("ManualAcntNickNameLbl").trim());
		Assert.assertEquals(manualAcntSettingPopup.ManualAcntMemoLbl().getText().trim(),
				PropsUtil.getDataPropertyValue("ManualAcntMemoLbl").trim());
		Assert.assertEquals(manualAcntSettingPopup.ManualAcntNumberLbl().getText().trim(),
				PropsUtil.getDataPropertyValue("ManualAcntNumberLbl").trim());
		Assert.assertEquals(manualAcntSettingPopup.ManualAcntAmntDueLbl().getText().trim(),
				PropsUtil.getDataPropertyValue("ManualAcntAmntDueLbl").trim());
		Assert.assertEquals(manualAcntSettingPopup.ManualAcntFrequencyLbl().getText().trim(),
				PropsUtil.getDataPropertyValue("ManualAcntFrequencyLbl").trim());
		Assert.assertEquals(manualAcntSettingPopup.ManualAcntNextDueLbl().getText().trim(),
				PropsUtil.getDataPropertyValue("ManualAcntNextDueLbl").trim());
	}

	@Test(description = "AT-93053,AT-93067,AT-93017:Verify left side labels in Account settings pop up for manual loan accounts user", groups = {
			"Regression" }, priority = 18, dependsOnMethods = "verifyLeftLabelsForManualLoanAcnt", enabled = true)
	public void verifyLeftLabelsForManualLoanAcnt2() {

		logger.info(
				"verify account setting popup for manual account: Account name, Account type  NickName(optional), Account Number (Optional) Categorize all transaction to (optional)Tag all transactions as(optional), Balance Currency, Current Balance, URL(Optional), Username (Optional), Password(Optional),show Account in Account Summary, Delete account link and Save change button should be displayed.");

		Assert.assertEquals(manualAcntSettingPopup.ManualAcntTagAllTransactnLbl().getText().trim(),
				PropsUtil.getDataPropertyValue("ManualAcntTagAllTransactnLbl").trim());
		Assert.assertEquals(manualAcntSettingPopup.ManualCatAllTransactionLbl().getText().trim(),
				PropsUtil.getDataPropertyValue("ManualCatAllTransactionLbl").trim());
		Assert.assertEquals(manualAcntSettingPopup.ManualAcntBalCurrencyLbl().getText().trim(),
				PropsUtil.getDataPropertyValue("ManualAcntBalCurrencyLbl").trim());
		Assert.assertEquals(manualAcntSettingPopup.ManualAcntCurrentBalLbl().getText().trim(),
				PropsUtil.getDataPropertyValue("ManualAcntCurrentBalLbl").trim());
		Assert.assertEquals(manualAcntSettingPopup.ManualAcntURLLbl().getText().trim(),
				PropsUtil.getDataPropertyValue("ManualAcntURLLbl").trim());
		Assert.assertEquals(manualAcntSettingPopup.ManualAcntUserNameLbl().getText().trim(),
				PropsUtil.getDataPropertyValue("ManualAcntUserNameLbl").trim());
		Assert.assertEquals(manualAcntSettingPopup.ManualAcntPasswordLbl().getText().trim(),
				PropsUtil.getDataPropertyValue("ManualAcntPasswordLbl").trim());
		Assert.assertEquals(manualAcntSettingPopup.ManualAcntShowAcntInSummaryLbl().getText().trim(),
				PropsUtil.getDataPropertyValue("ManualAcntShowAcntInSummaryLbl").trim());
	}

	@Test(description = "AT-93053,AT-93095,AT-93105:Verify left side labels in Account settings pop up for manual loan accounts user", groups = {
			"Regression" }, priority = 19, dependsOnMethods = "verifyLeftLabelsForManualLoanAcnt", enabled = true)
	public void verifyAllBtnsForManualLoanAcnt() {
		Assert.assertTrue(manualAcntSettingPopup.ManualAcntDeleteBtn().isDisplayed());
		Assert.assertTrue(manualAcntSettingPopup.ManualAcntShowAcntInSummaryToggle().isDisplayed());
		Assert.assertTrue(manualAcntSettingPopup.ManualAcntSaveChanges().isDisplayed());
	}

	@Test(description = "AT-93045,AT-93054,AT-93046,AT-93047:Verify left side labels in Account settings pop up for manual Mortgage accounts user", groups = {
			"Regression" }, priority = 20, dependsOnMethods = "verifyLeftLabelsForManualLoanAcnt", enabled = true)
	public void verifyLeftLabelsForManualMortgageAcnt() {
		accountSettiingsPopup.closePopup();
		logger.info(
				"verify account setting popup for manual account: Account name, Account type  NickName(optional), Account Number (Optional) Categorize all transaction to (optional)Tag all transactions as(optional), Balance Currency, Current Balance, URL(Optional), Username (Optional), Password(Optional),show Account in Account Summary, Delete account link and Save change button should be displayed.");
		finappUtil.intitutionViewClickOnAccountSettings("USB Gage", "");

		Assert.assertEquals(manualAcntSettingPopup.ManualAcntNameLbl().getText().trim(),
				PropsUtil.getDataPropertyValue("ManualAcntNameLbl").trim());
		Assert.assertEquals(manualAcntSettingPopup.ManualAcntAcntTypeLbl().getText().trim(),
				PropsUtil.getDataPropertyValue("ManualAcntAcntTypeLbl").trim());
		Assert.assertEquals(manualAcntSettingPopup.ManualAcntNickNameLbl().getText().trim(),
				PropsUtil.getDataPropertyValue("ManualAcntNickNameLbl").trim());
		Assert.assertEquals(manualAcntSettingPopup.ManualAcntMemoLbl().getText().trim(),
				PropsUtil.getDataPropertyValue("ManualAcntMemoLbl").trim());
		Assert.assertEquals(manualAcntSettingPopup.ManualAcntNumberLbl().getText().trim(),
				PropsUtil.getDataPropertyValue("ManualAcntNumberLbl").trim());
		Assert.assertEquals(manualAcntSettingPopup.ManualAcntAmntDueLbl().getText().trim(),
				PropsUtil.getDataPropertyValue("ManualAcntAmntDueLbl").trim());
		Assert.assertEquals(manualAcntSettingPopup.ManualAcntFrequencyLbl().getText().trim(),
				PropsUtil.getDataPropertyValue("ManualAcntFrequencyLbl").trim());
		Assert.assertEquals(manualAcntSettingPopup.ManualAcntNextDueLbl().getText().trim(),
				PropsUtil.getDataPropertyValue("ManualAcntNextDueLbl").trim());
	}

	@Test(description = "AT-93054,AT-93067,AT-93017:Verify left side labels in Account settings pop up for manual Mortgage accounts user", groups = {
			"Regression" }, priority = 21, dependsOnMethods = "verifyLeftLabelsForManualMortgageAcnt", enabled = true)
	public void verifyLeftLabelsForManualMortgageAcnt2() {

		logger.info(
				"verify account setting popup for manual account: Account name, Account type  NickName(optional), Account Number (Optional) Categorize all transaction to (optional)Tag all transactions as(optional), Balance Currency, Current Balance, URL(Optional), Username (Optional), Password(Optional),show Account in Account Summary, Delete account link and Save change button should be displayed.");

		Assert.assertEquals(manualAcntSettingPopup.ManualAcntTagAllTransactnLbl().getText().trim(),
				PropsUtil.getDataPropertyValue("ManualAcntTagAllTransactnLbl").trim());
		Assert.assertEquals(manualAcntSettingPopup.ManualCatAllTransactionLbl().getText().trim(),
				PropsUtil.getDataPropertyValue("ManualCatAllTransactionLbl").trim());
		Assert.assertEquals(manualAcntSettingPopup.ManualAcntBalCurrencyLbl().getText().trim(),
				PropsUtil.getDataPropertyValue("ManualAcntBalCurrencyLbl").trim());
		Assert.assertEquals(manualAcntSettingPopup.ManualAcntCurrentBalLbl().getText().trim(),
				PropsUtil.getDataPropertyValue("ManualAcntCurrentBalLbl").trim());
		Assert.assertEquals(manualAcntSettingPopup.ManualAcntURLLbl().getText().trim(),
				PropsUtil.getDataPropertyValue("ManualAcntURLLbl").trim());
		Assert.assertEquals(manualAcntSettingPopup.ManualAcntUserNameLbl().getText().trim(),
				PropsUtil.getDataPropertyValue("ManualAcntUserNameLbl").trim());
		Assert.assertEquals(manualAcntSettingPopup.ManualAcntPasswordLbl().getText().trim(),
				PropsUtil.getDataPropertyValue("ManualAcntPasswordLbl").trim());
		Assert.assertEquals(manualAcntSettingPopup.ManualAcntShowAcntInSummaryLbl().getText().trim(),
				PropsUtil.getDataPropertyValue("ManualAcntShowAcntInSummaryLbl").trim());
	}

	@Test(description = "AT-93054,AT-93095,AT-93105:Verify left side labels in Account settings pop up for manual Mortgage accounts user", groups = {
			"Regression" }, priority = 22, dependsOnMethods = "verifyLeftLabelsForManualMortgageAcnt", enabled = true)
	public void verifyAllBtnsForManualMortgageAcnt() {
		Assert.assertTrue(manualAcntSettingPopup.ManualAcntDeleteBtn().isDisplayed());
		Assert.assertTrue(manualAcntSettingPopup.ManualAcntShowAcntInSummaryToggle().isDisplayed());
		Assert.assertTrue(manualAcntSettingPopup.ManualAcntSaveChanges().isDisplayed());
	}

	@Test(description = "AT-93049,AT-93045,AT-93046,AT-93047:Verify left side labels in Account settings pop up for manual Investment accounts user", groups = {
			"Regression" }, priority = 23, dependsOnMethods = "verifyLeftLabelsForManualMortgageAcnt", enabled = true)
	public void verifyLeftLabelsForManualInvestmentAcnt() {

		accountSettiingsPopup.closePopup();
		logger.info(
				"verify account setting popup for manual account: Account name, Account type  NickName(optional), Account Number (Optional) Categorize all transaction to (optional)Tag all transactions as(optional), Balance Currency, Current Balance, URL(Optional), Username (Optional), Password(Optional),show Account in Account Summary, Delete account link and Save change button should be displayed.");
		finappUtil.intitutionViewClickOnAccountSettings("Zerodha Fund", "");

		Assert.assertEquals(manualAcntSettingPopup.ManualAcntNameLbl().getText().trim(),
				PropsUtil.getDataPropertyValue("ManualAcntNameLbl").trim());
		Assert.assertEquals(manualAcntSettingPopup.ManualAcntAcntTypeLbl().getText().trim(),
				PropsUtil.getDataPropertyValue("ManualAcntAcntTypeLbl").trim());
		Assert.assertEquals(manualAcntSettingPopup.ManualAcntNickNameLbl().getText().trim(),
				PropsUtil.getDataPropertyValue("ManualAcntNickNameLbl").trim());
		Assert.assertEquals(manualAcntSettingPopup.ManualAcntMemoLbl().getText().trim(),
				PropsUtil.getDataPropertyValue("ManualAcntMemoLbl").trim());
		Assert.assertEquals(manualAcntSettingPopup.ManualAcntNumberLbl().getText().trim(),
				PropsUtil.getDataPropertyValue("ManualAcntNumberLbl").trim());
	}

	@Test(description = "AT-93049,AT-93067,AT-93017:Verify left side labels in Account settings pop up for manual Investment accounts user", groups = {
			"Regression" }, priority = 24, dependsOnMethods = "verifyLeftLabelsForManualInvestmentAcnt", enabled = true)
	public void verifyLeftLabelsForManualInvestmentAcnt2() {

		logger.info(
				"verify account setting popup for manual account: Account name, Account type  NickName(optional), Account Number (Optional) Categorize all transaction to (optional)Tag all transactions as(optional), Balance Currency, Current Balance, URL(Optional), Username (Optional), Password(Optional),show Account in Account Summary, Delete account link and Save change button should be displayed.");

		Assert.assertEquals(manualAcntSettingPopup.ManualCatAllTransactionLbl().getText().trim(),
				PropsUtil.getDataPropertyValue("ManualCatAllTransactionLbl").trim());
		Assert.assertEquals(manualAcntSettingPopup.ManualAcntBalCurrencyLbl().getText().trim(),
				PropsUtil.getDataPropertyValue("ManualAcntBalCurrencyLbl").trim());
		Assert.assertEquals(manualAcntSettingPopup.ManualAcntCurrentBalLbl().getText().trim(),
				PropsUtil.getDataPropertyValue("ManualAcntCurrentBalLbl").trim());
		Assert.assertEquals(manualAcntSettingPopup.ManualAcntURLLbl().getText().trim(),
				PropsUtil.getDataPropertyValue("ManualAcntURLLbl").trim());
		Assert.assertEquals(manualAcntSettingPopup.ManualAcntUserNameLbl().getText().trim(),
				PropsUtil.getDataPropertyValue("ManualAcntUserNameLbl").trim());
		Assert.assertEquals(manualAcntSettingPopup.ManualAcntPasswordLbl().getText().trim(),
				PropsUtil.getDataPropertyValue("ManualAcntPasswordLbl").trim());
		Assert.assertEquals(manualAcntSettingPopup.ManualAcntShowAcntInSummaryLbl().getText().trim(),
				PropsUtil.getDataPropertyValue("ManualAcntShowAcntInSummaryLbl").trim());
	}

	@Test(description = "AT-93049,AT-93095,AT-93105:Verify left side labels in Account settings pop up for manual Investment accounts user", groups = {
			"Regression" }, priority = 25, dependsOnMethods = "verifyLeftLabelsForManualInvestmentAcnt", enabled = true)
	public void verifyAllBtnsForManualInvestmentAcnt() {
		Assert.assertTrue(manualAcntSettingPopup.ManualAcntDeleteBtn().isDisplayed());
		Assert.assertTrue(manualAcntSettingPopup.ManualAcntShowAcntInSummaryToggle().isDisplayed());
		Assert.assertTrue(manualAcntSettingPopup.ManualAcntSaveChanges().isDisplayed());
	}

	@Test(description = "AT-93045,AT-93055,AT-93046,AT-93047,AT-93018:Verify left side labels in Account settings pop up for manual Bills accounts user", groups = {
			"Regression" }, priority = 26, dependsOnMethods = "verifyLeftLabelsForManualInvestmentAcnt", enabled = true)
	public void verifyLeftLabelsForManualBillsAcnt() {

		accountSettiingsPopup.closePopup();
		logger.info(
				"verify account setting popup for manual account: Account name, Account type  NickName(optional), Account Number (Optional) Categorize all transaction to (optional)Tag all transactions as(optional), Balance Currency, Current Balance, URL(Optional), Username (Optional), Password(Optional),show Account in Account Summary, Delete account link and Save change button should be displayed.");
		finappUtil.intitutionViewClickOnAccountSettings("BESCOM", "");

		Assert.assertEquals(manualAcntSettingPopup.ManualAcntNameLbl().getText().trim(),
				PropsUtil.getDataPropertyValue("ManualAcntNameLbl").trim());
		Assert.assertEquals(manualAcntSettingPopup.ManualAcntAcntTypeLbl().getText().trim(),
				PropsUtil.getDataPropertyValue("ManualAcntAcntTypeLbl").trim());
		Assert.assertEquals(manualAcntSettingPopup.ManualAcntNickNameLbl().getText().trim(),
				PropsUtil.getDataPropertyValue("ManualAcntNickNameLbl").trim());
		Assert.assertEquals(manualAcntSettingPopup.ManualAcntMemoLbl().getText().trim(),
				PropsUtil.getDataPropertyValue("ManualAcntMemoLbl").trim());
		Assert.assertEquals(manualAcntSettingPopup.ManualAcntNumberLbl().getText().trim(),
				PropsUtil.getDataPropertyValue("ManualAcntNumberLbl").trim());
	}

	@Test(description = "AT-93055:Verify left side labels in Account settings pop up for manual Bills accounts user", groups = {
			"Regression" }, priority = 27, dependsOnMethods = "verifyLeftLabelsForManualBillsAcnt", enabled = true)
	public void verifyLeftLabelsForManualBillsAcnt2() {

		logger.info(
				"verify account setting popup for manual account: Account name, Account type  NickName(optional), Account Number (Optional) Categorize all transaction to (optional)Tag all transactions as(optional), Balance Currency, Current Balance, URL(Optional), Username (Optional), Password(Optional),show Account in Account Summary, Delete account link and Save change button should be displayed.");

		Assert.assertEquals(manualAcntSettingPopup.ManualAcntAmntDueLbl().getText().trim(),
				PropsUtil.getDataPropertyValue("ManualAcntAmntDueLbl").trim());
		Assert.assertEquals(manualAcntSettingPopup.ManualAcntFrequencyLbl().getText().trim(),
				PropsUtil.getDataPropertyValue("ManualAcntFrequencyLbl").trim());
		Assert.assertEquals(manualAcntSettingPopup.ManualAcntNextDueLbl().getText().trim(),
				PropsUtil.getDataPropertyValue("ManualAcntNextDueLbl").trim());
		Assert.assertEquals(manualAcntSettingPopup.ManualAcntBalCurrencyLbl().getText().trim(),
				PropsUtil.getDataPropertyValue("ManualAcntBalCurrencyLbl").trim());
		Assert.assertEquals(manualAcntSettingPopup.ManualAcntURLLbl().getText().trim(),
				PropsUtil.getDataPropertyValue("ManualAcntURLLbl").trim());
		Assert.assertEquals(manualAcntSettingPopup.ManualAcntUserNameLbl().getText().trim(),
				PropsUtil.getDataPropertyValue("ManualAcntUserNameLbl").trim());
		Assert.assertEquals(manualAcntSettingPopup.ManualAcntPasswordLbl().getText().trim(),
				PropsUtil.getDataPropertyValue("ManualAcntPasswordLbl").trim());
		Assert.assertEquals(manualAcntSettingPopup.ManualAcntShowAcntInSummaryLbl().getText().trim(),
				PropsUtil.getDataPropertyValue("ManualAcntShowAcntInSummaryLbl").trim());
	}

	@Test(description = "AT-93055,AT-93095,AT-93105:Verify left side labels in Account settings pop up for manual Bills accounts user", groups = {
			"Regression" }, priority = 28, dependsOnMethods = "verifyLeftLabelsForManualBillsAcnt", enabled = true)
	public void verifyAllBtnsForManualBillsAcnt() {
		Assert.assertTrue(manualAcntSettingPopup.ManualAcntDeleteBtn().isDisplayed());
		Assert.assertTrue(manualAcntSettingPopup.ManualAcntShowAcntInSummaryToggle().isDisplayed());
		Assert.assertTrue(manualAcntSettingPopup.ManualAcntSaveChanges().isDisplayed());
	}

	@Test(description = "AT-93045,AT-93056,AT-93046,AT-93047:Verify left side labels in Account settings pop up for manual Insurance accounts user", groups = {
			"Regression" }, priority = 29, dependsOnMethods = "verifyLeftLabelsForManualBillsAcnt", enabled = true)
	public void verifyLeftLabelsForManualInsuranceAcnt() {

		accountSettiingsPopup.closePopup();
		SeleniumUtil.waitForPageToLoad();
		logger.info(
				"verify account setting popup for manual account: Account name, Account type  NickName(optional), Account Number (Optional) Categorize all transaction to (optional)Tag all transactions as(optional), Balance Currency, Current Balance, URL(Optional), Username (Optional), Password(Optional),show Account in Account Summary, Delete account link and Save change button should be displayed.");
		finappUtil.intitutionViewClickOnAccountSettings("ICICI Lombard", "");

		Assert.assertEquals(manualAcntSettingPopup.ManualAcntNameLbl().getText().trim(),
				PropsUtil.getDataPropertyValue("ManualAcntNameLbl").trim());
		Assert.assertEquals(manualAcntSettingPopup.ManualAcntAcntTypeLbl().getText().trim(),
				PropsUtil.getDataPropertyValue("ManualAcntAcntTypeLbl").trim());
		Assert.assertEquals(manualAcntSettingPopup.ManualAcntNickNameLbl().getText().trim(),
				PropsUtil.getDataPropertyValue("ManualAcntNickNameLbl").trim());
		Assert.assertEquals(manualAcntSettingPopup.ManualAcntMemoLbl().getText().trim(),
				PropsUtil.getDataPropertyValue("ManualAcntMemoLbl").trim());
		Assert.assertEquals(manualAcntSettingPopup.ManualAcntNumberLbl().getText().trim(),
				PropsUtil.getDataPropertyValue("ManualAcntNumberLbl").trim());
		Assert.assertEquals(manualAcntSettingPopup.ManualAcntAmntDueLbl().getText().trim(),
				PropsUtil.getDataPropertyValue("ManualAcntAmntDueLbl").trim());
		Assert.assertEquals(manualAcntSettingPopup.ManualAcntFrequencyLbl().getText().trim(),
				PropsUtil.getDataPropertyValue("ManualAcntFrequencyLbl").trim());
		Assert.assertEquals(manualAcntSettingPopup.ManualAcntNextDueLbl().getText().trim(),
				PropsUtil.getDataPropertyValue("ManualAcntNextDueLbl").trim());
	}

	@Test(description = "AT-93056,AT-93017:Verify left side labels in Account settings pop up for manual Insurance accounts user", groups = {
			"Regression" }, priority = 30, dependsOnMethods = "verifyLeftLabelsForManualInsuranceAcnt", enabled = true)
	public void verifyLeftLabelsForManualInsuranceAcnt2() {

		logger.info(
				"verify account setting popup for manual account: Account name, Account type  NickName(optional), Account Number (Optional) Categorize all transaction to (optional)Tag all transactions as(optional), Balance Currency, Current Balance, URL(Optional), Username (Optional), Password(Optional),show Account in Account Summary, Delete account link and Save change button should be displayed.");
		Assert.assertEquals(manualAcntSettingPopup.ManualAcntBalCurrencyLbl().getText().trim(),
				PropsUtil.getDataPropertyValue("ManualAcntBalCurrencyLbl").trim());
		Assert.assertEquals(manualAcntSettingPopup.ManualAcntCurrentBalLbl().getText().trim(),
				PropsUtil.getDataPropertyValue("ManualAcntCurrentBalLbl").trim());
		Assert.assertEquals(manualAcntSettingPopup.ManualIsAssetLiabilityLbl().getText().trim(),
				PropsUtil.getDataPropertyValue("ManualIsAssetLiabilityLbl").trim());
		Assert.assertEquals(manualAcntSettingPopup.ManualAcntURLLbl().getText().trim(),
				PropsUtil.getDataPropertyValue("ManualAcntURLLbl").trim());
		Assert.assertEquals(manualAcntSettingPopup.ManualAcntUserNameLbl().getText().trim(),
				PropsUtil.getDataPropertyValue("ManualAcntUserNameLbl").trim());
		Assert.assertEquals(manualAcntSettingPopup.ManualAcntPasswordLbl().getText().trim(),
				PropsUtil.getDataPropertyValue("ManualAcntPasswordLbl").trim());
		Assert.assertEquals(manualAcntSettingPopup.ManualAcntShowAcntInSummaryLbl().getText().trim(),
				PropsUtil.getDataPropertyValue("ManualAcntShowAcntInSummaryLbl").trim());
	}

	@Test(description = "AT-93056,AT-93095,AT-93105:Verify left side labels in Account settings pop up for manual Insurance accounts user", groups = {
			"Regression" }, priority = 31, dependsOnMethods = "verifyLeftLabelsForManualInsuranceAcnt", enabled = true)
	public void verifyAllBtnsForManualInsuranceAcnt() {
		Assert.assertTrue(manualAcntSettingPopup.ManualAcntDeleteBtn().isDisplayed());
		Assert.assertTrue(manualAcntSettingPopup.ManualAcntShowAcntInSummaryToggle().isDisplayed());
		Assert.assertTrue(manualAcntSettingPopup.ManualAcntSaveChanges().isDisplayed());
	}

	// ###################Verify Manual Account setting popup Functionality
	// ###########################

	@Test(description = "AT-93061:Ensure user updated changes 'ADDING NICK NAME' should display in account summary page.", groups = {
			"Regression" }, priority = 32, dependsOnMethods = "login", enabled = true)
	public void creatingNickNameAndVerifying() {
		logger.info(
				"Verify a editable Text Field should be present corresponding to the Account Nickname(Optional) field.");
		accountSettiingsPopup.closePopup();

		// d.navigate().refresh();// added because content was blank
		SeleniumUtil.waitForPageToLoad();
		PageParser.forceNavigate("AccountsPage", d);

		SeleniumUtil.waitForPageToLoad(3000);
		
		try {
			manualAcntSettingPopup.accountslayout().isDisplayed();
		}catch(Throwable e) {
			PageParser.forceNavigate("AccountsPage", d);
			SeleniumUtil.waitForPageToLoad(3000);
		}

		
		finappUtil.intitutionViewClickOnAccountSettings("PayTM Wallet", "");
		SeleniumUtil.waitForPageToLoad(3000);

		manualAcntSettingPopup.ManualAcntNickNameTxtBox().clear();
		manualAcntSettingPopup.ManualAcntNickNameTxtBox()
				.sendKeys(PropsUtil.getDataPropertyValue("NIckNameValue").trim());
		SeleniumUtil.click(manualAcntSettingPopup.ManualAcntSaveChanges());
		SeleniumUtil.waitForPageToLoad(1000);
		SeleniumUtil.waitUntilToastMessageIsDisappeared();
		Assert.assertEquals(accountSettiingsPopup.addedNickNameInAS().get(0).getText().trim(),
				PropsUtil.getDataPropertyValue("NIckNameValue").trim());
	}

	@Test(description = "AT-93060,AT-93063,AT-93110:Ensure user updated changes 'UPDTAING ACNT NUMBER' should display in account summary page.", groups = {
			"Regression" }, priority = 33, dependsOnMethods = "login", enabled = true)
	public void updatingAndVerifyingAcntNumber() {
		finappUtil.intitutionViewClickOnAccountSettings("PayTM Wallet", "");
		SeleniumUtil.waitForPageToLoad(3000);

		logger.info(
				"Verify a editable Text Field should be present corresponding to the Account Number(Optional) field.");
		manualAcntSettingPopup.ManualAcntNumberTxtBox().clear();
		manualAcntSettingPopup.ManualAcntNumberTxtBox()
				.sendKeys(PropsUtil.getDataPropertyValue("ManuallyAddedAcntNumber").trim());

		SeleniumUtil.click(manualAcntSettingPopup.ManualAcntSaveChanges());
		SeleniumUtil.waitUntilToastMessageIsDisplayed();
		SeleniumUtil.waitForPageToLoad(3000);
		SeleniumUtil.waitForElementVisible(d, "acntNumbers", "AccountsPage", null);
		Assert.assertEquals(accountSettiingsPopup.acntNumbers().get(0).getText().trim(),
				PropsUtil.getDataPropertyValue("UpdatedAcntNumber").trim()); // x-1111
	}

	@Test(description = "AT-93110:Ensure user updated changes 'UPDTAING ACNT NUMBER' should display in account summary page.", groups = {
			"Regression" }, priority = 34, dependsOnMethods = "login", enabled = true)
	public void updatingAndVerifyingCurntBalnc() {

		finappUtil.intitutionViewClickOnAccountSettings("PayTM Wallet", "");
		SeleniumUtil.waitForPageToLoad(3000);

		logger.info("Verify a editable number field should be present corresponding to the Current Balance field.");
		manualAcntSettingPopup.ManualAcntCurrentBalTxtBox().clear();
		SeleniumUtil.click(manualAcntSettingPopup.ManualAcntCurrentBalTxtBox());
		manualAcntSettingPopup.ManualAcntCurrentBalTxtBox()
				.sendKeys(PropsUtil.getDataPropertyValue("updateddCurrentBalanceValue"));

		SeleniumUtil.click(manualAcntSettingPopup.ManualAcntSaveChanges());
		SeleniumUtil.waitUntilToastMessageIsDisplayed();
		SeleniumUtil.waitForPageToLoad(3000);
		SeleniumUtil.waitForElementVisible(d, "currentBalanceAmt", "AccountsPage", null);
		Assert.assertEquals(accountSettiingsPopup.currentBalanceAmt().get(0).getText().trim(),
				PropsUtil.getDataPropertyValue("updateddCurrentBalance").trim()); // $10,001.00
	}

	@Test(description = "AT-93057,AT-93058,AT-93088,AT-93089:Verify the Maximum allowed is 50 characters in the editable Text Field present corresponding to the Account Name field.", groups = {
			"Regression" }, priority = 35, dependsOnMethods = "login", enabled = true)
	public void verifyAcntNameMaxLimit() {
		finappUtil.intitutionViewClickOnAccountSettings("PayTM Wallet", "");
		manualAcntSettingPopup.ManualAcntNameTxtBox().clear();
		manualAcntSettingPopup.ManualAcntNameTxtBox()
				.sendKeys(PropsUtil.getDataPropertyValue("AcntNameMaxValue").trim());
		int getTextLength = manualAcntSettingPopup.ManualAcntNameTxtBox().getAttribute("value").length();

		int expectedMaxLength = Integer
				.parseInt(manualAcntSettingPopup.ManualAcntNameTxtBox().getAttribute("maxlength").trim());
		Assert.assertEquals(expectedMaxLength, getTextLength);
	}

	@Test(description = "AT-93059:Verify that user should be restricted to type more tha 40 characters in the editable Text Field present corresponding to the Account Nickname field.", groups = {
			"Regression" }, priority = 36, dependsOnMethods = "verifyAcntNameMaxLimit", enabled = true)
	public void verifyNickNameMaxLimit() {
		manualAcntSettingPopup.ManualAcntNickNameTxtBox().clear();
		manualAcntSettingPopup.ManualAcntNickNameTxtBox()
				.sendKeys(PropsUtil.getDataPropertyValue("NickNameMaxValue").trim());
		int getTextLength = manualAcntSettingPopup.ManualAcntNickNameTxtBox().getAttribute("value").length();

		int expectedMaxLength = Integer
				.parseInt(manualAcntSettingPopup.ManualAcntNickNameTxtBox().getAttribute("maxlength").trim());
		Assert.assertEquals(expectedMaxLength, getTextLength);
	}

	@Test(description = "AT-93062:Verify a ghost text Nickname  should be present in the editable text field corresponding to the Account Nickname(Optional) field. ", groups = {
			"Regression" }, priority = 37, dependsOnMethods = "verifyNickNameMaxLimit", enabled = true)
	public void verifyNickNameGhostText() {
		manualAcntSettingPopup.ManualAcntNickNameTxtBox().clear();
		SeleniumUtil.waitForPageToLoad(2000);
		Assert.assertEquals(manualAcntSettingPopup.ManualAcntNickNameTxtBox().getAttribute("placeholder").trim(),
				PropsUtil.getDataPropertyValue("NickNameGhostValue").trim());
	}

	@Test(description = "AT-93064:Verify a ghost account number should be present in the editable text field corresponding to the Account Number field. ", groups = {
			"Regression" }, priority = 38, dependsOnMethods = "verifyNickNameGhostText", enabled = true)
	public void verifyAcntNumberGhostText() {
		logger.info(
				"Verify the value  corresponding to the Account Type field should be prepopulated with earlier selctection, and uneditable .");
		Assert.assertEquals(manualAcntSettingPopup.ManualAcntTypeTxtBox().getText().trim(),
				PropsUtil.getDataPropertyValue("ManualAcntTypeTxtBox").trim());
		manualAcntSettingPopup.ManualAcntNumberTxtBox().clear();
		Assert.assertEquals(manualAcntSettingPopup.ManualAcntNumberTxtBox().getAttribute("placeholder").trim(),
				PropsUtil.getDataPropertyValue("AcntNumberGhostValue").trim());
	}

	@Test(description = "AT-93071,AT-93072,AT-93073,AT-93074:Verify a ghost text Tag name should be present in the editable text field corresponding to the Tag All Transactions As (Optional) field.", groups = {
			"Regression" }, priority = 39, dependsOnMethods = "verifyAcntNumberGhostText", enabled = true)
	public void verifyTagAllTransactnGhostText() {
		logger.info("Verify that preadded tag values should be available.");
		SeleniumUtil.click(manualAcntSettingPopup.ManualAcntTagAllTransactn());
		accountSettiingsPopup.verifyTagSuggestionValues();

		logger.info("Verify a \"+\" symbal and text \"ADD\"  should be present on the button for adding the tag .");
		Assert.assertTrue(manualAcntSettingPopup.ManualAcntAddBtn().isDisplayed());
		Assert.assertEquals(manualAcntSettingPopup.ManualAcntTagAllTransactn().getAttribute("placeholder").trim(),
				PropsUtil.getDataPropertyValue("GhostTextForTagAllTransactionBox").trim());
	}

	@Test(description = "AT-93065,AT-93065:Verify the Maximum allowed is 50 characters in the editable Text Field present corresponding to the Account Number field.", groups = {
			"Regression" }, priority = 40, dependsOnMethods = "login", enabled = true)
	public void verifyAcntNumberMaxLimit() {
		manualAcntSettingPopup.ManualAcntNumberTxtBox().clear();

		logger.info(
				"	Verify that user should be restricted to type more han 50 characters in the editable Text Field present corresponding to the Account Number field.");
		manualAcntSettingPopup.ManualAcntNumberTxtBox()
				.sendKeys(PropsUtil.getDataPropertyValue("AcntNumberMaxValue").trim());
		int getTextLength = manualAcntSettingPopup.ManualAcntNumberTxtBox().getAttribute("value").length();

		int expectedMaxLength = Integer
				.parseInt(manualAcntSettingPopup.ManualAcntNumberTxtBox().getAttribute("maxlength").trim());
		Assert.assertEquals(expectedMaxLength, getTextLength);
	}

	@Test(description = "AT-93068:Verify by default None is selected in the drop down categorize all transaction to(optional) field.", groups = {
			"Regression" }, priority = 41, dependsOnMethods = "login", enabled = true)
	public void verifyCategoryHeader() throws Exception {

		logger.info(
				"Ensure use is provided with the option as a dropdown to select the category name from the list for \"Categorize all transactions to\"");
		SeleniumUtil.click(accountSettiingsPopup.byDefaultCategoryDDHeader().get(1));
		SeleniumUtil.click(manualAcntSettingPopup.ManualCatAllTransactionLbl());

		logger.info("verify By default text is 'select category'");
		Assert.assertEquals(accountSettiingsPopup.byDefaultCategoryDDHeader().get(0).getText().trim(),
				PropsUtil.getDataPropertyValue("byDefaultCategoryDDHeader").trim());
		SeleniumUtil.click(accountSettiingsPopup.closeCategory());
		SeleniumUtil.waitForPageToLoad(400);
	}

	@Test(description = "AT-93106:Verify that a option of Show Account in Account Summary with a toggle button is present and by default it should be on in the Account settings pop up.", groups = {
			"Regression" }, priority = 42, dependsOnMethods = "login", enabled = true)
	public void verifyShowAcntInSummaryToggleBtnState() {
		SeleniumUtil.waitForPageToLoad(2000);
		System.out.println(manualAcntSettingPopup.ManualAcntShowAcntInSummaryToggle1().getCssValue("background-color"));// displaying as null
																									// but toggle button
																									// is selected
		Assert.assertEquals(manualAcntSettingPopup.ManualAcntShowAcntInSummaryToggle1().getCssValue("background-color"),
				"rgba(12, 118, 190, 1)");
	}

	@Test(description = "AT-93075,AT-93076:editing and verifying current balance Field", groups = {
			"Regression" }, priority = 43, dependsOnMethods = "login", enabled = true)
	public void verifyingCurrentBalncField() {

		logger.info("Verify a editable number field should be present corresponding to the Current Balance field.");
		SeleniumUtil.click(manualAcntSettingPopup.ManualAcntCurrentBalTxtBox());

		logger.info(
				"Verify that user should be allowed to type only positive values (including zero). Only allow digits and dot (.) in the editable number field present corresponding to the Current Balance field.");
		logger.info(
				"user should be restricted to type more than 15 characters before \".\" and 4 charecters after \".\" in the editable number Field present corresponding to the Current Balance  field.");
		manualAcntSettingPopup.ManualAcntCurrentBalTxtBox().clear();
		manualAcntSettingPopup.ManualAcntCurrentBalTxtBox()
				.sendKeys(PropsUtil.getDataPropertyValue("ManualCurrentBalance").trim());
		// Assert.assertTrue(manualAcntSettingPopup.ManualCurrentBalError().isDisplayed());
		SeleniumUtil.waitForElementVisible(d, "ManualCurrentBalError", "AccountsPage", null);
		Assert.assertEquals(manualAcntSettingPopup.ManualCurrentBalError().getText(),
				PropsUtil.getDataPropertyValue("balErrorMessage").trim());
	}

	@Test(description = "editing and verifying current balance Field", groups = {
			"Regression" }, priority = 44, dependsOnMethods = "login", enabled = true)
	public void verifyingRestrictedCharacters() {

		manualAcntSettingPopup.ManualAcntCurrentBalTxtBox().clear();

		logger.info(
				"Verify that user should be allowed to type only positive values (including zero). Only allow digits and dot (.) in the editable number field present corresponding to the Current Balance field.");
		manualAcntSettingPopup.ManualAcntCurrentBalTxtBox()
				.sendKeys(PropsUtil.getDataPropertyValue("RestrictedCurrentBalValue").trim());
		Assert.assertTrue(manualAcntSettingPopup.ManualCurrentBalError().isDisplayed());
	}

	@Test(description = "AT-93086,AT-93087:Verify a editable text field should be present corresponding to the URL(Optional) field.", groups = {
			"Regression" }, priority = 45, dependsOnMethods = "login", enabled = true)
	public void verifyUrlMaxLimit() {
		manualAcntSettingPopup.ManualAcntURLTxtBox().clear();
		manualAcntSettingPopup.ManualAcntURLTxtBox().sendKeys(PropsUtil.getDataPropertyValue("ManualUrLText").trim());
		int actualMaxLimit = manualAcntSettingPopup.ManualAcntURLTxtBox().getAttribute("value").length();

		logger.info(
				"Verify the Maximum allowed is 40 characters in the editable text Field present corresponding to the URL(Optional) field and restrict user to type beyond 40 characters.");
		int expectedMaxLength = Integer
				.parseInt(manualAcntSettingPopup.ManualAcntURLTxtBox().getAttribute("maxlength").trim());
		Assert.assertEquals(actualMaxLimit, expectedMaxLength);
	}

	@Test(description = "editing and verifying Balance Currency Field", groups = {
			"Regression" }, priority = 46, dependsOnMethods = "login", enabled = true)
	public void verifyingBalncCurrencyField() {
		logger.info("Verify a dropdown-single select should be present corresponding to the Balance Currency field.");
		Assert.assertTrue(manualAcntSettingPopup.ManualAcntBalCurrencyDD().isDisplayed());

		logger.info(
				"Verify by default user selected currency(at the time of adding account) should be selected in the dropdown present corresponding to the Balance Currency field.");
		Assert.assertEquals(manualAcntSettingPopup.ManualAcntBalCurrencyDD().getText().trim(),
				PropsUtil.getDataPropertyValue("ByDefaultUserCurrency").trim());
	}

	@Test(description = "AT-93090,AT-93093:Verify a ghost textUser Name (Login) should be present in the editable text field corresponding to the User Name(Optional) field.", groups = {
			"Regression" }, priority = 47, dependsOnMethods = "login", enabled = true)
	public void verifyUserNamePasswordGhostText() {

		Assert.assertEquals(manualAcntSettingPopup.ManualAcntUserNameTxtBox().getAttribute("placeholder").trim(),
				PropsUtil.getDataPropertyValue("UserNameTextBox").trim());
		logger.info(
				"Verify a ghost textPassword should be present in the editable text field corresponding to the Password(Optional) field.");
		Assert.assertEquals(manualAcntSettingPopup.ManualAcntPasswordTxtBox().getAttribute("placeholder").trim(),
				PropsUtil.getDataPropertyValue("PasswordTxtBox").trim());
	}

	@Test(description = "AT-93092,AT-93091:Verify the Maximum allowed is 40 characters in the editable text Field present corresponding to the Password(Optional) field and restrict user to type beyond 40 characters(type in masked format).", groups = {
			"Regression" }, priority = 48, dependsOnMethods = "login", enabled = true)
	public void verifyPasswordMaxLimit() {

		manualAcntSettingPopup.ManualAcntPasswordTxtBox()
				.sendKeys(PropsUtil.getDataPropertyValue("MaximumLengthPassword").trim());
		int getTextLength = manualAcntSettingPopup.ManualAcntPasswordTxtBox().getAttribute("value").length();

		int expectedMaxLength = Integer
				.parseInt(manualAcntSettingPopup.ManualAcntPasswordTxtBox().getAttribute("maxlength").trim());
		Assert.assertEquals(expectedMaxLength, getTextLength);

		logger.info(
				"Verify a editable Text Field, show in masked format should be present corresponding to the Password(Optional) field.");
		Assert.assertEquals(manualAcntSettingPopup.ManualAcntPasswordTxtBox().getAttribute("type").trim(),
				PropsUtil.getDataPropertyValue("PasswordType").trim());
	}

	@Test(description = "AT-93096,AT-93094:Verify the following values should be available in the dropdown present corresponding to the Frequency (optional) field  :", groups = {
			"Regression" }, priority = 49, dependsOnMethods = "login", enabled = true)
	public void verifyFreqDDValues() {
		accountSettiingsPopup.closePopup();
		SeleniumUtil.waitForPageToLoad(2000);
		finappUtil.intitutionViewClickOnAccountSettings("HDFC Platinum", "");
		SeleniumUtil.waitForPageToLoad();

		SeleniumUtil.click(manualAcntSettingPopup.ManualAcntFrequencyDD());
		manualAcntSettingPopup.verifyFreqDDValues();

		logger.info(
				"Verify a dropdown-single select should be present corresponding to the Frequency (optional) field.");
		SeleniumUtil.click(manualAcntSettingPopup.freqDDList().get(1));
		SeleniumUtil.waitForPageToLoad();
		Assert.assertEquals(manualAcntSettingPopup.frequencyDDValue().getText().trim(),
				PropsUtil.getDataPropertyValue("SelectedFreqText").trim());
	}

	@Test(description = "AT-93082,AT-93083,AT-93084,AT-930835:editing and verifying amount due Field", groups = {
			"Regression" }, priority = 50, dependsOnMethods = "login", enabled = true)
	public void verifyingAmtDueField() {

		logger.info("Verify a editable number field should be present corresponding to the Current Balance field.");
		SeleniumUtil.click(manualAcntSettingPopup.ManualAcntAmntDueTxtBox());

		logger.info(
				"Verify that user should be allowed to type only positive values (including zero). Only allow digits and dot (.) in the editable number field present corresponding to the Current Balance field.");
		logger.info(
				"user should be restricted to type more than 15 characters before \".\" and 4 charecters after \".\" in the editable number Field present corresponding to the Current Balance  field.");
		manualAcntSettingPopup.ManualAcntAmntDueTxtBox().clear();
		manualAcntSettingPopup.ManualAcntAmntDueTxtBox()
				.sendKeys(PropsUtil.getDataPropertyValue("ManualCurrentBalance").trim());
		Assert.assertTrue(manualAcntSettingPopup.ManualDueAmtError().isDisplayed());// error is not displaying
	}

	@Test(description = "AT-93097,AT-93098,AT-93099,AT-93100:Verify a calendar, editable date field should be present corresponding to the Next Due Date field.", groups = {
			"Regression" }, priority = 51, dependsOnMethods = "login", enabled = true)
	public void verifyDueDateFunctionality() {

		logger.info("Verify a calendar indicator should be present corresponding to the Next Due Date field.");
		Assert.assertTrue(manualAcntSettingPopup.CalenderIcon().isDisplayed());

		manualAcntSettingPopup.ManualAcntNextDueTxtBox().sendKeys(manualAcntSettingPopup.targateDate1(-2));
		manualAcntSettingPopup.ManualAcntNextDueTxtBox().sendKeys(Keys.ENTER);
		SeleniumUtil.waitForPageToLoad(3000);
		logger.info(
				"Verify that user should not be allowed to enter Past dates,If user enters past date then show the inline error message: \"Due date has passed\"");
		SeleniumUtil.scrollElementIntoView(d, manualAcntSettingPopup.ManualPopUpError(), true);
		SeleniumUtil.waitForElementVisible(d, "ManualPopUpError", "AccountsPage", null);
		
		System.out.println(manualAcntSettingPopup.ManualPopUpError().getText());
		System.out.println(PropsUtil.getDataPropertyValue("NextDueDateErrorMsg").trim());
		Assert.assertEquals(manualAcntSettingPopup.ManualPopUpError().getText().trim(),
				PropsUtil.getDataPropertyValue("NextDueDateErrorMsg").trim());

	}

	@Test(description = "AT-93077,AT-93078,AT-93079,AT-93080,AT-93081:editing and verifying current balance Field", groups = {
			"Regression" }, priority = 52, dependsOnMethods = "login", enabled = true)
	public void verifyingRestrictedCharactersAmtDue() {

		manualAcntSettingPopup.ManualAcntAmntDueTxtBox().clear();
		logger.info(
				"Verify that user should be allowed to type only positive values (including zero). Only allow digits and dot (.) in the editable number field present corresponding to the Current Balance field.");
		manualAcntSettingPopup.ManualAcntAmntDueTxtBox()
				.sendKeys(PropsUtil.getDataPropertyValue("RestrictedCurrentBalValue").trim());
		SeleniumUtil.waitForPageToLoad(500);
		Assert.assertTrue(manualAcntSettingPopup.ManualDueAmtError().isDisplayed());
	}

	@Test(description = "AT-93101:Verify that radio buttons(Asset and Liability) should be present corresponding to the Is an Asset or a Liability field.", groups = {
			"Regression" }, priority = 53, dependsOnMethods = "login", enabled = true)
	public void verifyAssetLiabilityRB() {
		accountSettiingsPopup.closePopup();
		d.navigate().refresh();
		SeleniumUtil.waitForPageToLoad(2000);
		PageParser.forceNavigate("AccountsPage", d);
		SeleniumUtil.waitForPageToLoad(3000);
		
		try {
			manualAcntSettingPopup.accountslayout().isDisplayed();
		}catch(Throwable e) {
			PageParser.forceNavigate("AccountsPage", d);
			SeleniumUtil.waitForPageToLoad(3000);
		}

		
		finappUtil.intitutionViewClickOnAccountSettings("ICICI Lombard", "");
		SeleniumUtil.waitForPageToLoad(2000);
		Assert.assertTrue(manualAcntSettingPopup.ManualassetLabel().isDisplayed());
		Assert.assertTrue(manualAcntSettingPopup.ManualLiabilityLabel().isDisplayed());
		System.out.println(manualAcntSettingPopup.ManualassetLabel().getAttribute("class").getBytes());// check this
																										// here
		Assert.assertTrue(manualAcntSettingPopup.ManualassetLabel().getAttribute("class")
				.contains(PropsUtil.getDataPropertyValue("CheckedStatus1")));
	}

	@Test(description = "AT-93102,AT-93103:Verify that user should be able to check the other radio button i.e Liability", groups = {
			"Regression" }, priority = 54, dependsOnMethods = "login", enabled = true)
	public void verifyLiabilityRB() {

		SeleniumUtil.click(manualAcntSettingPopup.ManualLiabilityLabel());
		SeleniumUtil.waitForPageToLoad(2000);
		Assert.assertTrue(manualAcntSettingPopup.ManualLiabilityLabel().getAttribute("class")
				.contains(PropsUtil.getDataPropertyValue("CheckedStatus1")));
	}

}
