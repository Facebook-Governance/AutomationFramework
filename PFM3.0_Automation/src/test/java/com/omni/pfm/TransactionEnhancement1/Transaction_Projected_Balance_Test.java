/**
* Copyright (c) 2018 Yodlee Inc. All Rights Reserved.
*
* This software is the confidential and proprietary information of
* Yodlee, Inc. Use is subject to license terms.
*
* @author Renuka K
*/
package com.omni.pfm.TransactionEnhancement1;

import java.awt.AWTException;
import java.text.ParseException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.omni.pfm.PageProcessor.PageParser;
import com.omni.pfm.pages.AccountAddition.AccountAddition;
import com.omni.pfm.pages.Login.LoginPage;
import com.omni.pfm.pages.Login.LoginPage_SAML3;
import com.omni.pfm.pages.TransactionEnhancement1.Add_Manual_Transaction_Loc;
import com.omni.pfm.pages.TransactionEnhancement1.Series_Recurence_Transaction_Loc;
import com.omni.pfm.pages.TransactionEnhancement1.Transaction_AccountDropDown_Loc;
import com.omni.pfm.pages.TransactionEnhancement1.Transaction_AccountSharing_ReadOnlyFeature_loc;
import com.omni.pfm.pages.TransactionEnhancement1.Transaction_Layout_Loc;
import com.omni.pfm.pages.TransactionEnhancement1.Transaction_Projected_Balance_Loc;
import com.omni.pfm.testBase.TestBase;
import com.omni.pfm.utility.PropsUtil;
import com.omni.pfm.utility.SeleniumUtil;

public class Transaction_Projected_Balance_Test extends TestBase {
	private static final Logger logger = LoggerFactory.getLogger(Transaction_MarkAsPaid_Transfer_Categories_Test.class);
	AccountAddition accountAdd;
	Transaction_Projected_Balance_Loc projectedBalance;
	Transaction_AccountDropDown_Loc accountDropDown;
	Transaction_AccountSharing_ReadOnlyFeature_loc readOnly;
	Transaction_Layout_Loc layout;
	Add_Manual_Transaction_Loc add_Manual_Transaction;
	Series_Recurence_Transaction_Loc seriesTransaction;
	LoginPage_SAML3 saml;

	@BeforeClass(alwaysRun = true)

	public void testInit() throws Exception {
		doInitialization("Transaction Search");
		accountAdd = new AccountAddition();
		projectedBalance = new Transaction_Projected_Balance_Loc(d);
		accountDropDown = new Transaction_AccountDropDown_Loc(d);
		readOnly = new Transaction_AccountSharing_ReadOnlyFeature_loc(d);
		layout = new Transaction_Layout_Loc(d);
		add_Manual_Transaction = new Add_Manual_Transaction_Loc(d);
		seriesTransaction = new Series_Recurence_Transaction_Loc(d);
		saml = new LoginPage_SAML3();
		// saml.login(d, saml.getInvestorUserName(), null, "10003700");
		LoginPage.loginMain(d, loginParameter);
		SeleniumUtil.waitForPageToLoad();
		accountAdd.linkAccount();
		accountAdd.addAggregatedAccount("BEA.site16441.1", "site16441.1");

	}

	@Test(description = "verify account filter should not displayed without applying account filter", priority = 2, groups = {
			"Smoke" })
	public void projectedBalanceShouldNotDisplayed_WithoutAccountFilter() {
		PageParser.forceNavigate("Transaction", d);
		SeleniumUtil.waitForPageToLoad();
		logger.info("project balance should not displayed with out apply Account filter");
		boolean projectedBalanceFalse = false;
		if (projectedBalance.projectedBalanceAmount().size() == 0) {
			projectedBalanceFalse = true;
		}
		boolean projectedBalanceLabel = false;
		if (projectedBalance.projectedBalanceLabel().size() == 0) {
			projectedBalanceLabel = true;
		}

		Assert.assertTrue(projectedBalanceFalse, "projected Balance should not dsiplayed");
		Assert.assertTrue(projectedBalanceLabel, "pojected label should not dsiplayed");
	}

	@Test(description = "AT-73703:verify projected balance should be displayed when selected one bank account", priority = 3, groups = {
			"Smoke" })
	public void projectedBalanceShouldDisplayed1_WhenSelectOneBankAccount() throws ParseException {
		SeleniumUtil.click(accountDropDown.accountFilterLink());
		SeleniumUtil.waitForPageToLoad(1000);
		SeleniumUtil.click(accountDropDown.allAccountCheckBox());
		SeleniumUtil.waitForPageToLoad();
		System.out.println(projectedBalance.accountdropdownAllAccount().size());
		int accountlistSize = projectedBalance.accountdropdownAllAccount().size();
		for (int i = 0; i < accountlistSize; i++) {
			System.out.println(
					projectedBalance.accountdropdownAllAccount().get(i).getText().trim().replaceAll("\n", " "));
			System.out.println(PropsUtil.getDataPropertyValue("TransactionProjectedBalanceBankAccount"));
			if (projectedBalance.accountdropdownAllAccount().get(i).getText().trim().replaceAll("\n", " ")
					.equals(PropsUtil.getDataPropertyValue("TransactionProjectedBalanceBankAccount").trim())) {
				SeleniumUtil.click(projectedBalance.accountdropdownAllAccount().get(i));
				SeleniumUtil.waitForPageToLoad();

				break;
			}
		}
		try {
			SeleniumUtil.click(readOnly.ProjectedExapdIcon());
		} catch (Exception e) {

		}
		boolean expectedprojectedAmount = false;
		if (projectedBalance.projectedBalanceAmount().size() == projectedBalance.projectedDateHeader().size()) {
			expectedprojectedAmount = true;
		}
		for (int i = 0; i < projectedBalance.projectedBalanceLabel().size(); i++) {
			Assert.assertTrue(projectedBalance.projectedBalanceAmount().get(i).isDisplayed());
			Assert.assertTrue(projectedBalance.projectedBalanceLabel().get(i).isDisplayed());
		}

		Assert.assertTrue(expectedprojectedAmount);
	}

	@Test(description = "AT-73710,AT-73711,AT-73705:verify projected balance after click on load more button", priority = 5, groups = {
			"Smoke" })
	public void projectedBalanceAfterClickOnLoadMore() throws ParseException {

		SeleniumUtil.click(accountDropDown.accountFilterLink());

		SeleniumUtil.waitForPageToLoad();

		int accountlistSize = projectedBalance.accountdropdownAllAccount().size();

		for (int i = 0; i < accountlistSize; i++) {
			System.out.println(
					projectedBalance.accountdropdownAllAccount().get(i).getText().trim().replaceAll("\n", " "));
			System.out.println(PropsUtil.getDataPropertyValue("TransactionProjectedBalanceBankAccount1").trim());
			if (projectedBalance.accountdropdownAllAccount().get(i).getText().trim().replaceAll("\n", " ")
					.equals(PropsUtil.getDataPropertyValue("TransactionProjectedBalanceBankAccount1").trim())) {
				SeleniumUtil.click(projectedBalance.accountdropdownAllAccount().get(i));
				SeleniumUtil.waitForPageToLoad(10000);
				break;
			}
		}

		// accountDropDown.clickOnShowMoreButton();

		boolean expectedprojectedAmount = false;
		if (projectedBalance.projectedBalanceAmount().size() == projectedBalance.projectedDateHeader().size()) {
			expectedprojectedAmount = true;
		}
		for (int i = 0; i < projectedBalance.projectedBalanceLabel().size(); i++) {
			Assert.assertTrue(projectedBalance.projectedBalanceAmount().get(i).isDisplayed());
			Assert.assertTrue(projectedBalance.projectedBalanceLabel().get(i).isDisplayed());
		}

		Assert.assertTrue(expectedprojectedAmount);
	}

	@Test(description = "AT-73704:verify projected balance when clic one card account", priority = 7, groups = {
			"Smoke" })
	public void projectedBalanceShouldDisplayed1_WhenSelectOneCardkAccount() throws ParseException {

		PageParser.forceNavigate("Transaction", d);
		SeleniumUtil.waitForPageToLoad();
		SeleniumUtil.click(accountDropDown.accountFilterLink());
		SeleniumUtil.waitForPageToLoad(1000);
		SeleniumUtil.click(accountDropDown.allAccountCheckBox());
		SeleniumUtil.waitForPageToLoad();

		int accountlistSize = projectedBalance.accountdropdownAllAccount().size();
		for (int i = 0; i < accountlistSize; i++) {
			if (projectedBalance.accountdropdownAllAccount().get(i).getText().trim().replaceAll("\n", " ")
					.equals(PropsUtil.getDataPropertyValue("TransactionProjectedBalanceBankAccount2").trim())) {
				SeleniumUtil.click(projectedBalance.accountdropdownAllAccount().get(i));
				SeleniumUtil.waitForPageToLoad();

				break;
			}
		}
		try {
			SeleniumUtil.click(readOnly.ProjectedExapdIcon());
		} catch (Exception e) {

		}
		boolean expectedprojectedAmount = false;
		if (projectedBalance.projectedBalanceAmount().size() == projectedBalance.projectedDateHeader().size()) {
			expectedprojectedAmount = true;
		}
		for (int i = 0; i < projectedBalance.projectedBalanceLabel().size(); i++) {
			Assert.assertTrue(projectedBalance.projectedBalanceAmount().get(i).isDisplayed());
			Assert.assertTrue(projectedBalance.projectedBalanceLabel().get(i).isDisplayed());
		}

		Assert.assertTrue(expectedprojectedAmount);
	}

	@Test(description = "AT-73704:verify projected balance should displayed for posted transaction", priority = 8, groups = {
			"Smoke" })
	public void TotalAmountShouldNotDisplayedDorPostedTransaction() throws ParseException {

		boolean projectedBalanceFalse = false;
		if (projectedBalance.postedBalanceAmount().size() == 0) {
			projectedBalanceFalse = true;
		}
		boolean projectedBalanceLabel = false;
		if (projectedBalance.postedBalanceLabel().size() == 0) {
			projectedBalanceLabel = true;
		}

		Assert.assertTrue(projectedBalanceFalse, "projected Balance should not dsiplayed");
		Assert.assertTrue(projectedBalanceLabel, "pojected label should not dsiplayed");

	}

	@Test(description = "AT-73708:verify projected balance when selected more than one card account", priority = 9, groups = {
			"Smoke" })
	public void projectedBalanceShouldDisplayed_WhenSelectmoreThanOneCardAccount() throws ParseException {
		SeleniumUtil.click(accountDropDown.accountFilterLink());
		SeleniumUtil.waitForPageToLoad(1000);

		int accountlistSize = projectedBalance.accountdropdownAllAccount().size();
		for (int i = 0; i < accountlistSize; i++) {
			if (projectedBalance.accountdropdownAllAccount().get(i).getText().trim().replaceAll("\n", " ")
					.equals(PropsUtil.getDataPropertyValue("TransactionProjectedBalanceBankAccount3").trim())) {
				SeleniumUtil.click(projectedBalance.accountdropdownAllAccount().get(i));
				break;
			}
		}
		SeleniumUtil.waitForPageToLoad(10000);
		boolean expectedprojectedAmount = false;
		if (projectedBalance.projectedBalanceAmount().size() == projectedBalance.projectedDateHeader().size()) {
			expectedprojectedAmount = true;
		}
		for (int i = 0; i < projectedBalance.projectedBalanceLabel().size(); i++) {
			Assert.assertTrue(projectedBalance.projectedBalanceAmount().get(i).isDisplayed());
			Assert.assertTrue(projectedBalance.projectedBalanceLabel().get(i).isDisplayed());
		}

		Assert.assertTrue(expectedprojectedAmount);
	}

	@Test(description = "AT-73707:verify projected balance should not displayed when selected card bank account both", priority = 12, groups = {
			"Smoke" })
	public void projectedBalanceShouldNotDisplayed1_WhenSelectCardAndBankAccount() throws ParseException {
		PageParser.forceNavigate("Transaction", d);
		SeleniumUtil.waitForPageToLoad();
		SeleniumUtil.click(accountDropDown.accountFilterLink());
		SeleniumUtil.waitForPageToLoad(1000);
		SeleniumUtil.click(accountDropDown.allAccountCheckBox());
		SeleniumUtil.waitForPageToLoad();

		int accountlistSize = projectedBalance.accountdropdownAllAccount().size();
		for (int i = 0; i < accountlistSize; i++) {
			if (projectedBalance.accountdropdownAllAccount().get(i).getText().replaceAll("\n", " ")
					.equals(PropsUtil.getDataPropertyValue("TransactionProjectedBalanceBankAccount2"))
					|| projectedBalance.accountdropdownAllAccount().get(i).getText().replaceAll("\n", " ")
							.equals(PropsUtil.getDataPropertyValue("TransactionProjectedBalanceBankAccount"))) {
				SeleniumUtil.click(projectedBalance.accountdropdownAllAccount().get(i));
				SeleniumUtil.waitForPageToLoad(1000);
			}
		}
		SeleniumUtil.waitForPageToLoad(7000);
		try {
			SeleniumUtil.click(readOnly.ProjectedExapdIcon());
		} catch (Exception e) {

		}
		boolean projectedBalanceFalse = false;
		if (projectedBalance.projectedBalanceAmount().size() == 0) {
			projectedBalanceFalse = true;
		}
		boolean projectedBalanceLabel = false;
		if (projectedBalance.projectedBalanceLabel().size() == 0) {
			projectedBalanceLabel = true;
		}

		Assert.assertTrue(projectedBalanceFalse, "projected Balance should not dsiplayed");
		Assert.assertTrue(projectedBalanceLabel, "pojected label should not dsiplayed");

	}

	@Test(description = "AT-73707:verify projected balance", priority = 13, groups = { "Smoke" })
	public void loadMorePojectedTransactionProjectedAmount() throws ParseException {
		PageParser.forceNavigate("Transaction", d);
		SeleniumUtil.waitForPageToLoad();
		SeleniumUtil.click(accountDropDown.accountFilterLink());
		SeleniumUtil.waitForPageToLoad(1000);
		SeleniumUtil.click(accountDropDown.allAccountCheckBox());
		SeleniumUtil.waitForPageToLoad();

		int accountlistSize = projectedBalance.accountdropdownAllAccount().size();
		for (int i = 0; i < accountlistSize; i++) {
			if (projectedBalance.accountdropdownAllAccount().get(i).getText().replaceAll("\n", " ")
					.equals(PropsUtil.getDataPropertyValue("TransactionProjectedBalanceBankAccount"))
					|| projectedBalance.accountdropdownAllAccount().get(i).getText().replaceAll("\n", " ")
							.equals(PropsUtil.getDataPropertyValue("TransactionProjectedBalanceBankAccount1"))
					|| projectedBalance.accountdropdownAllAccount().get(i).getText().replaceAll("\n", " ")
							.equals(PropsUtil.getDataPropertyValue("TransactionProjectedBalanceBankAccount4"))) {
				SeleniumUtil.click(projectedBalance.accountdropdownAllAccount().get(i));
				SeleniumUtil.waitForPageToLoad(10000);
			}
		}
		for (int i = 0; i < 2; i++) {
			accountDropDown.clickOnShowMoreButton();
		}
		try {
			SeleniumUtil.click(readOnly.ProjectedExapdIcon());
		} catch (Exception e) {

		}
		SeleniumUtil.waitForPageToLoad(2000);
		boolean expectedprojectedAmount = false;
		if (projectedBalance.projectedBalanceAmount().size() == projectedBalance.projectedDateHeader().size()) {
			expectedprojectedAmount = true;
		}
		for (int i = 0; i < projectedBalance.projectedBalanceLabel().size(); i++) {
			Assert.assertTrue(projectedBalance.projectedBalanceAmount().get(i).isDisplayed());
			Assert.assertTrue(projectedBalance.projectedBalanceLabel().get(i).isDisplayed());
		}

		Assert.assertTrue(expectedprojectedAmount);
	}

	@Test(description = "AT-73707:verify projected balance should not displayed for more than 30 days", priority = 14, groups = {
			"Smoke" })
	public void projectedTotalAmountShouldNotDisplayedMorethan30days() throws ParseException {
		layout.serachTransaction(add_Manual_Transaction.targateDate1(35), add_Manual_Transaction.targateDate1(35));
		SeleniumUtil.waitForPageToLoad();
		boolean projectedBalanceFalse = false;
		if (projectedBalance.projectedBalanceAmount().size() == 0) {
			projectedBalanceFalse = true;
		}
		boolean projectedBalanceLabel = false;
		if (projectedBalance.projectedBalanceLabel().size() == 0) {
			projectedBalanceLabel = true;
		}

		Assert.assertTrue(projectedBalanceFalse, "projected Balance should not dsiplayed");
		Assert.assertTrue(projectedBalanceLabel, "pojected label should not dsiplayed");
	}

	@Test(description = "AT-73707,AT-73708:verify projected balance for manual transaction", priority = 15, groups = {
			"Smoke" })
	public void projectedTotalAmountShouldNotDisplayedManualTransactions() throws ParseException {
		PageParser.forceNavigate("Transaction", d);
		SeleniumUtil.waitForPageToLoad();
		SeleniumUtil.click(add_Manual_Transaction.addManualIcon());
		SeleniumUtil.waitForPageToLoad(2000);
		add_Manual_Transaction.createOneTimeTransaction(PropsUtil.getDataPropertyValue("ProjectedBalanceManualAmount"),
				PropsUtil.getDataPropertyValue("ProjectedBalanceManualDescription"), 1, 4, 0, 30, 1, 1,
				PropsUtil.getDataPropertyValue("ProjectedBalanceManualNote"),
				PropsUtil.getDataPropertyValue("ProjectedBalanceManualCheck"));
		SeleniumUtil.waitForPageToLoad();
		SeleniumUtil.click(accountDropDown.accountFilterLink());
		SeleniumUtil.waitForPageToLoad(1000);
		SeleniumUtil.click(accountDropDown.allAccountCheckBox());
		SeleniumUtil.waitForPageToLoad();

		int accountlistSize = projectedBalance.accountdropdownAllAccount().size();
		for (int i = 0; i < accountlistSize; i++) {
			System.out.println(
					projectedBalance.accountdropdownAllAccount().get(i).getText().trim().replaceAll("\n", " "));
			System.out.println(PropsUtil.getDataPropertyValue("TransactionProjectedBalanceBankAccount"));
			if (projectedBalance.accountdropdownAllAccount().get(i).getText().trim().replaceAll("\n", " ")
					.equals(PropsUtil.getDataPropertyValue("TransactionProjectedBalanceBankAccount").trim())) {
				SeleniumUtil.click(projectedBalance.accountdropdownAllAccount().get(i));
				SeleniumUtil.waitForPageToLoad();

				break;
			}
		}

		layout.serachTransaction(add_Manual_Transaction.targateDate1(30), add_Manual_Transaction.targateDate1(30));
		SeleniumUtil.waitForPageToLoad();
		try {
			SeleniumUtil.click(readOnly.ProjectedExapdIcon());
		} catch (Exception e) {

		}
		boolean expectedprojectedAmount = false;
		if (projectedBalance.projectedBalanceAmount().size() == projectedBalance.projectedDateHeader().size()) {
			expectedprojectedAmount = true;
		}
		for (int i = 0; i < projectedBalance.projectedBalanceLabel().size(); i++) {
			Assert.assertTrue(projectedBalance.projectedBalanceAmount().get(i).isDisplayed());
			Assert.assertTrue(projectedBalance.projectedBalanceLabel().get(i).isDisplayed());
		}

		Assert.assertTrue(expectedprojectedAmount);

	}

	@Test(description = "AT-73706:projected balance should not dipslayed for multi currency transaction", priority = 16, groups = {
			"Smoke" })
	public void verifyWithMultiCurrency() throws ParseException, AWTException, InterruptedException {
		accountAdd.linkAccount();
		accountAdd.addContainerAccount("DagBank", "balance.bank1", "bank1");
		SeleniumUtil.waitForPageToLoad();
		PageParser.forceNavigate("Transaction", d);
		SeleniumUtil.waitForPageToLoad();
		SeleniumUtil.click(accountDropDown.accountFilterLink());
		SeleniumUtil.waitForPageToLoad(1000);
		SeleniumUtil.click(accountDropDown.allAccountCheckBox());
		SeleniumUtil.waitForPageToLoad();
		int accountlistSize = projectedBalance.accountdropdownAllAccount().size();
		for (int i = 0; i < accountlistSize; i++) {
			if (projectedBalance.accountdropdownAllAccount().get(i).getText().replaceAll("\n", " ")
					.equals(PropsUtil.getDataPropertyValue("MutlipleCurrencyAccount"))
					|| projectedBalance.accountdropdownAllAccount().get(i).getText().replaceAll("\n", " ")
							.equals(PropsUtil.getDataPropertyValue("TransactionProjectedBalanceBankAccount"))) {
				SeleniumUtil.click(projectedBalance.accountdropdownAllAccount().get(i));
				SeleniumUtil.waitForPageToLoad(10000);
			}
		}
		try {
			SeleniumUtil.click(readOnly.ProjectedExapdIcon());
		} catch (Exception e) {

		}
		SeleniumUtil.waitForPageToLoad(1000);
		logger.info("project balance should not displayed with out apply Account filter");
		boolean projectedBalanceFalse = false;
		if (projectedBalance.projectedBalanceAmount().size() == 0) {
			projectedBalanceFalse = true;
		}
		boolean projectedBalanceLabel = false;
		if (projectedBalance.projectedBalanceLabel().size() == 0) {
			projectedBalanceLabel = true;
		}

		Assert.assertTrue(projectedBalanceFalse, "projected Balance should not dsiplayed");
		Assert.assertTrue(projectedBalanceLabel, "pojected label should not dsiplayed");
	}
}
