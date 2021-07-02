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

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.omni.pfm.Accounts.Accounts_ViewByGroup_Loc;
import com.omni.pfm.PageProcessor.PageParser;
import com.omni.pfm.pages.AccountAddition.AccountAddition;
import com.omni.pfm.pages.Login.LoginPage;
import com.omni.pfm.pages.TransactionEnhancement1.AddToCalendar_Transaction_Loc;
import com.omni.pfm.pages.TransactionEnhancement1.Add_Manual_Transaction_Loc;
import com.omni.pfm.pages.TransactionEnhancement1.Aggregated_Transaction_Details_Loc;
import com.omni.pfm.pages.TransactionEnhancement1.Series_Recurence_Transaction_Loc;
import com.omni.pfm.pages.TransactionEnhancement1.Transaction_AccountDropDown_Loc;
import com.omni.pfm.pages.TransactionEnhancement1.Transaction_AccountSharing_ReadOnlyFeature_loc;
import com.omni.pfm.pages.TransactionEnhancement1.Transaction_Account_Integration_Loc;
import com.omni.pfm.pages.TransactionEnhancement1.Transaction_Categorization_Rules_Loc;
import com.omni.pfm.pages.TransactionEnhancement1.Transaction_DashBoard_Integration_Loc;
import com.omni.pfm.pages.TransactionEnhancement1.Transaction_Filter_Loc;
import com.omni.pfm.pages.TransactionEnhancement1.Transaction_Layout_Loc;
import com.omni.pfm.pages.TransactionEnhancement1.Transaction_Search_Loc;
import com.omni.pfm.pages.TransactionEnhancement1.Transaction_Split_Locator;
import com.omni.pfm.testBase.TestBase;
import com.omni.pfm.utility.PropsUtil;
import com.omni.pfm.utility.SeleniumUtil;

public class Transaction_LOC_Test extends TestBase {
	Transaction_AccountDropDown_Loc accountFilter;
	AccountAddition addAccount;
	Transaction_Filter_Loc txnFilter;
	SeleniumUtil util;
	Transaction_Search_Loc search;
	Transaction_Account_Integration_Loc txnAccountIntg;
	Transaction_AccountSharing_ReadOnlyFeature_loc readOnly;
	Add_Manual_Transaction_Loc add_manual_transaction;
	Aggregated_Transaction_Details_Loc aggTxn;
	Transaction_Split_Locator splitTxn;
	Accounts_ViewByGroup_Loc groupView;
	Series_Recurence_Transaction_Loc seriesTxn;
	AddToCalendar_Transaction_Loc addToCal;
	Transaction_Categorization_Rules_Loc rule;
	Transaction_DashBoard_Integration_Loc txnDashBoard;
	Transaction_Layout_Loc layout;

	@BeforeClass

	public void testInit() throws Exception {

		doInitialization("Transaction Login");

		TestBase.tc = TestBase.extent.startTest("Transaction", "Login");
		TestBase.test.appendChild(TestBase.tc);
		accountFilter = new Transaction_AccountDropDown_Loc(d);
		addAccount = new AccountAddition();
		util = new SeleniumUtil();
		txnFilter = new Transaction_Filter_Loc(d);
		search = new Transaction_Search_Loc(d);
		txnAccountIntg = new Transaction_Account_Integration_Loc(d);
		readOnly = new Transaction_AccountSharing_ReadOnlyFeature_loc(d);
		add_manual_transaction = new Add_Manual_Transaction_Loc(d);
		aggTxn = new Aggregated_Transaction_Details_Loc(d);
		splitTxn = new Transaction_Split_Locator(d);
		groupView = new Accounts_ViewByGroup_Loc(d);
		seriesTxn = new Series_Recurence_Transaction_Loc(d);
		addToCal = new AddToCalendar_Transaction_Loc(d);
		rule = new Transaction_Categorization_Rules_Loc(d);
		layout = new Transaction_Layout_Loc(d);
		txnDashBoard = new Transaction_DashBoard_Integration_Loc(d);
		LoginPage.loginMain(d, loginParameter);
	}

	@Test(description = "AT-96958:verify account is dded successfully", priority = 1)
	public void verifyAccountAdded() throws AWTException, InterruptedException {
		// AT-96958 Verify that user is able to add the LOC accounts successfully
		addAccount.linkAccount();
		Assert.assertTrue(addAccount.addAggregatedAccount(PropsUtil.getDataPropertyValue("Txn_LOC_UserName"),
				PropsUtil.getDataPropertyValue("Txn_LOC_Password")), "account is not added successfully");

	}

	@Test(description = "AT-96958:verify all accounts in transaction account dropdown", priority = 2, dependsOnMethods = "verifyAccountAdded")
	public void verifyAllLOCAccount() {
		accountFilter.refreshFinapp();
		PageParser.forceNavigate("Transaction", d);
		accountFilter.clickAccountFilter();
		if (PropsUtil.getEnvPropertyValue("cnf_SDG").equalsIgnoreCase("OFF")) {
			util.assertExpectedActualList(util.getWebElementsValue(txnFilter.transactionFilterAccontName()),
					PropsUtil.getDataPropertyValue("Txn_LOC_AccountFilterAccountList_SDGOFF"), "verify all accounts");
		} else {
			util.assertExpectedActualList(util.getWebElementsValue(txnFilter.transactionFilterAccontName()),
					PropsUtil.getDataPropertyValue("Txn_LOC_AccountFilterAccountList"), "verify all accounts");
		}
	}

	@Test(description = "AT-96967:verify loc account type transaction", priority = 3, dependsOnMethods = "verifyAllLOCAccount")
	public void verifyLOCAccountTxn() {
		// AT-96967:Verify on selecting an indivdual LOC account from the account
		// dropdown the Transaction related to the LOC account must be shown in the page
		accountFilter.clickAllAccount();
		accountFilter.selectAccFilterOneAccount(PropsUtil.getDataPropertyValue("Txn_LOC_AccountNum"));
		SeleniumUtil.waitForPageToLoad();
		accountFilter.expandProjectedTXn();
		boolean LocAccountTxn = false;
		int txnSize = accountFilter.allTransactionAccount().size();
		for (int i = 0; i < txnSize; i++) {
			if (accountFilter.allTransactionAccount().get(i).getText().trim().replaceAll("\n", " ")
					.equals(PropsUtil.getDataPropertyValue("Txn_LOc_txnAccountName").trim())) {
				LocAccountTxn = true;

			} else {
				LocAccountTxn = false;
				break;
			}
		}
		Assert.assertTrue(LocAccountTxn,
				"advisor shared full view details accounts groups transaction are not displaying");
		Assert.assertEquals(accountFilter.accountFilterLink().getText().trim(),
				PropsUtil.getDataPropertyValue("Txn_LOc_AccountFilterName"), "Loc account is not selected");

	}

	@Test(description = "AT-96968:verify Loc account type account details", priority = 4, dependsOnMethods = "verifyLOCAccountTxn")
	public void verifyLOCAccountDetailsHeader() {
		SeleniumUtil.click(txnAccountIntg.expand());
		Assert.assertEquals(txnAccountIntg.loanDueDate().getText().trim(),
				add_manual_transaction.targateMonth(
						Integer.parseInt(PropsUtil.getDataPropertyValue("Txn_LOc_txnAccountHeaderDueDate"))),
				"loan due date is not displyed");
		Assert.assertEquals(txnAccountIntg.loanholdername().getText().trim(),
				PropsUtil.getDataPropertyValue("Txn_LOC_txnAccountHeaderAcctHolder"),
				"loan holder name is not displayed");
		Assert.assertEquals(txnAccountIntg.loanDescription().getText().trim(),
				PropsUtil.getDataPropertyValue("Txn_LOC_txnAccountHeaderDecription"),
				"Loan Decription is not displayed");
		Assert.assertEquals(txnAccountIntg.loanDueAmount().getText().trim(),
				PropsUtil.getDataPropertyValue("Txn_LOC_txnAccountHeaderAmountDue"),
				"Loan due amount is not displayed");
		Assert.assertEquals(txnAccountIntg.loanprincipal().getText().trim(),
				PropsUtil.getDataPropertyValue("Txn_LOC_txnAccountHeaderPrincipleAmount"),
				"loan principal amount is not displayed");
	}

	@Test(description = "AT-96969:verify Loc Account type deposit txn", priority = 5, dependsOnMethods = "verifyLOCAccountTxn")
	public void verifyLOCAccountsDepostTxn() {
		boolean allAccountType = false;
		// int allTransactionSize=accountDropDown.allTransactionDescription().size();
		SeleniumUtil.click(txnFilter.transactionTypeDeposit());
		SeleniumUtil.waitForPageToLoad();
		for (int i = 0; i < 6; i++) {
			SeleniumUtil.click(accountFilter.allTransactionDescription().get(i));
			if (readOnly.transactionDetailsReadonlyAccountLabel().getText().trim()
					.equals(PropsUtil.getDataPropertyValue("Txn_LOC_txnCreditedTo"))) {
				allAccountType = true;
			} else {
				allAccountType = false;
				break;
			}
		}

		Assert.assertTrue(allAccountType,
				"credited from  transaction type is not displayed when select desposit Transaction type");
	}

	@Test(description = "AT-96970:verify LOC account type Withdrawal txn", priority = 6, dependsOnMethods = "verifyLOCAccountTxn")
	public void verifyLOCAccountsWithDrawalTxn() {
		SeleniumUtil.click(txnFilter.transactionTypeWithDrawal());
		SeleniumUtil.waitForPageToLoad();
		boolean allAccountType = false;
		for (int i = 0; i < 20; i++) {
			SeleniumUtil.click(accountFilter.allTransactionDescription().get(i));
			if (readOnly.transactionDetailsReadonlyAccountLabel().getText().trim()
					.equals(PropsUtil.getDataPropertyValue("Txn_LOC_txnDebitedFrom"))) {
				allAccountType = true;
			} else {
				allAccountType = false;
				break;
			}
		}

		Assert.assertTrue(allAccountType,
				"debited from  transaction type is not displayed when select withdrawal Transaction type");
	}

	@Test(description = "AT-96972:vreify loc account type Txn row detials", priority = 7, dependsOnMethods = "verifyLOCAccountTxn")
	public void verifyLOCAccountTxnRowDetails() {
		String description = null;
		String category = null;
		String account = null;
		String cateName = null;
		int txnsize = add_manual_transaction.getAllPostedAmount1().size();
		SeleniumUtil.click(txnFilter.transactionTypeAll());
		SeleniumUtil.waitForPageToLoad();

		if (PropsUtil.getEnvPropertyValue("CategoryList43").equalsIgnoreCase("yes")) {
			cateName = PropsUtil.getDataPropertyValue("Txn_LOC_txnCatgeory_43");
		} else {
			cateName = PropsUtil.getDataPropertyValue("Txn_LOC_txnCatgeory");
		}

		for (int i = 0; i < txnsize; i++) {
			System.out.println(PropsUtil.getDataPropertyValue("Txn_LOC_txnAmount"));
			System.out.println(add_manual_transaction.getAllPostedAmount1().get(i).getText());
			if (add_manual_transaction.getAllPostedAmount1().get(i).getText()
					.equals(PropsUtil.getDataPropertyValue("Txn_LOC_txnAmount"))) {
				description = add_manual_transaction.getAlldescription1().get(i).getText().trim();
				category = add_manual_transaction.getAllPostedCat1().get(i).getText().trim();
				account = add_manual_transaction.getAllPostedAcount1().get(i).getText().trim().replaceAll("\n", " ");
				SeleniumUtil.click(aggTxn.collapsIcon().get(i));
				break;

			}
		}

		Assert.assertEquals(description, PropsUtil.getDataPropertyValue("Txn_LOC_txnDecription"),
				"transaction description is not dsiplaying");
		Assert.assertEquals(category, cateName, "transaction Category is not displaying");
		Assert.assertEquals(account, PropsUtil.getDataPropertyValue("Txn_LOc_txnAccountName"),
				"transaction Accounts is not displaying");

	}

	@Test(description = "AT-96972:verify Loc Account type details", priority = 8, dependsOnMethods = "verifyLOCAccountTxnRowDetails")
	public void verifyLOCAccountTxnDetails() {
		String cateName = null;
		if (PropsUtil.getEnvPropertyValue("CategoryList43").equalsIgnoreCase("yes")) {
			cateName = PropsUtil.getDataPropertyValue("Txn_LOC_txnCatgeory_43");
		} else {
			cateName = PropsUtil.getDataPropertyValue("Txn_LOC_txnCatgeory");
		}
		Assert.assertEquals(aggTxn.Amount().getText(), PropsUtil.getDataPropertyValue("Txn_LOC_txnAmount"),
				"transaction amount is not displayed");
		Assert.assertEquals(aggTxn.descField().getAttribute("value"),
				PropsUtil.getDataPropertyValue("Txn_LOC_txnDecription"), "txn description is not displyed");
		Assert.assertEquals(readOnly.transactionDetailsReadonlyStDescriptionValue().getText(),
				PropsUtil.getDataPropertyValue("Txn_LOC_txnSTDecription"),
				"txn statement description is not displayed");
		Assert.assertEquals(aggTxn.tansactiondate().getText(),
				add_manual_transaction
						.targateDate1(Integer.parseInt(PropsUtil.getDataPropertyValue("Txn_LOC_txnScheduledDate"))),
				"txn scheduled date not displayed");
		Assert.assertEquals(aggTxn.catgoryField().getText(), cateName, "txn catgeory name is not displayed");

	}

	@Test(description = "AT-96990:verify  Loc account add to calendar", priority = 9, dependsOnMethods = "verifyLOCAccountTxnDetails")
	public void verifyLOCAccountAddToCal() {
		Assert.assertTrue(
				addToCal.addcalLinkList().size() == Integer
						.parseInt(PropsUtil.getDataPropertyValue("Txn_LOC_AddCAlLink")),
				"add to calendar is dipslayed");
	}

	@Test(description = "AT-96990:verify LOC account type Create rule option", priority = 10, dependsOnMethods = "verifyLOCAccountTxnDetails")
	public void verifyLOCAccountCreateRule() {
		SeleniumUtil.click(aggTxn.catdropDownIcon());
		SeleniumUtil.click(aggTxn.catgoryList(4, 1));

		Assert.assertEquals(aggTxn.catChanegMessage().getText(), PropsUtil.getDataPropertyValue("AggCatChangeMessage"),
				"cretae rule is not displayed");
		Assert.assertTrue(aggTxn.createRule().isDisplayed(), "create rule icon is not displayed");
	}

	@Test(description = "AT-96981:verify Loc Account txn split", priority = 11, dependsOnMethods = "verifyLOCAccountTxnDetails")
	public void verifyLOCAccountTxnSplit() {

		splitTxn.splitTxn();
		Assert.assertEquals(layout.LayoutSplitIcon().size(),
				Integer.parseInt(PropsUtil.getDataPropertyValue("Txn_LOC_txnSplitSize")), "transaction is not splited");
	}

	@Test(description = "AT-96982:verify group transaction for loc account type", priority = 12, dependsOnMethods = "verifyAccountAdded")
	public void verifyLOCAccountGroupTxn() {
		PageParser.forceNavigate("AccountsPage", d);
		SeleniumUtil.waitForPageToLoad();
		groupView.clikcCreatgroup();
		groupView.createGroup(PropsUtil.getDataPropertyValue("Txn_LOC_GroupName"),
				PropsUtil.getDataPropertyValue("Txn_LOC_GroupAccount").split(","));
		PageParser.forceNavigate("Transaction", d);
		SeleniumUtil.waitForPageToLoad();
		accountFilter.clickAccountFilter();
		SeleniumUtil.click(accountFilter.transactionAccountDropDownAccountGroupLink()
				.get(Integer.parseInt(PropsUtil.getDataPropertyValue("Txn_LOC_groupFilterLink"))));
		SeleniumUtil.click(
				accountFilter.transactionAccountDropDownGroupName(PropsUtil.getDataPropertyValue("Txn_LOC_GroupName")));
		SeleniumUtil.waitForPageToLoad();
		accountFilter.expandProjectedTXn();
		boolean LocAccountTxn = false;
		int txnsize = accountFilter.allTransactionAccount().size();
		for (int i = 0; i < txnsize; i++) {
			if (accountFilter.allTransactionAccount().get(i).getText().trim().replaceAll("\n", " ")
					.equals(PropsUtil.getDataPropertyValue("Txn_LOc_txnAccountName").trim())) {
				LocAccountTxn = true;

			} else {
				LocAccountTxn = false;
				break;
			}
		}
		Assert.assertTrue(LocAccountTxn, "group account transaction not displayed");

	}

	@Test(description = "AT-96983:verify manual txn for LOc account Type", priority = 13, dependsOnMethods = "verifyLOCAccountGroupTxn")
	public void verifyLOCAccountManualTxn() {
		String cateName = null;
		if (PropsUtil.getEnvPropertyValue("CategoryList43").equalsIgnoreCase("yes")) {
			cateName = PropsUtil.getDataPropertyValue("Txn_LOC_MTMLCCat1_43");
		} else {
			cateName = PropsUtil.getDataPropertyValue("Txn_LOC_MTMLCCat1");
		}
		add_manual_transaction.clickMTLink();
		SeleniumUtil.waitForPageToLoad();
		add_manual_transaction.createOneTimeTransaction(PropsUtil.getDataPropertyValue("Txn_LOC_MTAmount1"),
				PropsUtil.getDataPropertyValue("Txn_LOC_MTDescription1"),
				PropsUtil.getDataPropertyValue("Txn_LOC_MTTxnType1"),
				PropsUtil.getDataPropertyValue("Txn_LOC_MTAccount1"),
				PropsUtil.getDataPropertyValue("Txn_LOC_MTfrequency1"),
				Integer.parseInt(PropsUtil.getDataPropertyValue("Txn_LOC_MTDate1")), cateName,
				PropsUtil.getDataPropertyValue("Txn_LOC_MTNote1"), PropsUtil.getDataPropertyValue("Txn_LOC_MTCheck"));
		layout.serachTransaction(
				add_manual_transaction
						.targateDate1(Integer.parseInt(PropsUtil.getDataPropertyValue("Txn_LOC_MTDate1"))),
				add_manual_transaction
						.targateDate1(Integer.parseInt(PropsUtil.getDataPropertyValue("Txn_LOC_MTDate1"))));
		SeleniumUtil.waitForPageToLoad();
		SeleniumUtil.waitFor(2);
		Assert.assertEquals(seriesTxn.getAlldescription1()
				.get(Integer.parseInt(PropsUtil.getDataPropertyValue("Txn_LOC_MTAddedTxnIndex"))).getText().trim(),
				PropsUtil.getDataPropertyValue("Txn_LOC_MTDescription1"), "transaction description is not dsiplaying");
		Assert.assertEquals(seriesTxn.getAllcat1()
				.get(Integer.parseInt(PropsUtil.getDataPropertyValue("Txn_LOC_MTAddedTxnIndex"))).getText().trim(),
				cateName, "transaction Category is not displaying");
		Assert.assertEquals(
				seriesTxn.getAllaccount1()
						.get(Integer.parseInt(PropsUtil.getDataPropertyValue("Txn_LOC_MTAddedTxnIndex"))).getText()
						.trim().replaceAll("\n", " "),
				PropsUtil.getDataPropertyValue("Txn_LOC_MTRowAccount"), "transaction Accounts is not displaying");

	}

	@Test(description = "AT-96983:verify LOc account transaction edited", priority = 14, dependsOnMethods = "verifyLOCAccountManualTxn")
	public void verifyLOCAccountManualTxnEdit() {
		seriesTxn.editOneTimeProjectedTxn(Integer.parseInt(PropsUtil.getDataPropertyValue("Txn_LOC_MTAddedTxnIndex")),
				PropsUtil.getDataPropertyValue("Txn_LOC_MTeditAmount"),
				add_manual_transaction
						.targateDate1(Integer.parseInt(PropsUtil.getDataPropertyValue("Txn_LOC_MTEditDate"))),
				PropsUtil.getDataPropertyValue("Txn_LOC_MTEditDescription"),
				PropsUtil.getDataPropertyValue("Txn_LOC_MTEditNote"));
		layout.serachTransaction(
				add_manual_transaction
						.targateDate1(Integer.parseInt(PropsUtil.getDataPropertyValue("Txn_LOC_MTEditDate"))),
				add_manual_transaction
						.targateDate1(Integer.parseInt(PropsUtil.getDataPropertyValue("Txn_LOC_MTEditDate"))));
		SeleniumUtil.waitForPageToLoad();
		SeleniumUtil.click(seriesTxn.getAllAmount1()
				.get(Integer.parseInt(PropsUtil.getDataPropertyValue("Txn_LOC_MTAddedTxnIndex"))));
		SeleniumUtil.waitForPageToLoad();
		SeleniumUtil.click(seriesTxn.shoeMore());
		SeleniumUtil.waitForPageToLoad();
		Assert.assertEquals(seriesTxn.amountField().getAttribute("value").trim(),
				PropsUtil.getDataPropertyValue("Txn_LOC_MTDettailsUpdtaedAmount"),
				"transaction amount is not displayed");
		Assert.assertEquals(seriesTxn.DescriptionField().getAttribute("value").trim(),
				PropsUtil.getDataPropertyValue("Txn_LOC_MTEditDescription"), "txn description is not displayed");
		Assert.assertEquals(seriesTxn.Scheduledate().getAttribute("value").trim(),
				add_manual_transaction
						.targateDate1(Integer.parseInt(PropsUtil.getDataPropertyValue("Txn_LOC_MTEditDate"))),
				"txn scheduled date is not displayed");
		Assert.assertEquals(seriesTxn.noteField().getAttribute("value").trim(),
				PropsUtil.getDataPropertyValue("Txn_LOC_MTEditNote"), "txn note is not displayed");
	}

	@Test(description = "AT-96974,AT-96978:Verify LOC Account type projected txn", priority = 15, dependsOnMethods = "verifyLOCAccountManualTxnEdit")
	public void verifyLOCAccountProjTxn() {
		PageParser.forceNavigate("Transaction", d);
		SeleniumUtil.waitForPageToLoad();

		add_manual_transaction.clickMTLink();
		add_manual_transaction.createTransactionWithRecuralldetails(PropsUtil.getDataPropertyValue("Txn_LOC_MRTAmount"),
				PropsUtil.getDataPropertyValue("Txn_LOC_MRTdescription"),
				PropsUtil.getDataPropertyValue("Txn_LOC_MRTAccount"),
				PropsUtil.getDataPropertyValue("Txn_LOC_MRTFrequency"),
				Integer.parseInt(PropsUtil.getDataPropertyValue("Txn_LOC_MRTScheduledDate")),
				Integer.parseInt(PropsUtil.getDataPropertyValue("Txn_LOC_MRTEndDate")),
				PropsUtil.getDataPropertyValue("Txn_LOC_MRTHLC"), PropsUtil.getDataPropertyValue("Txn_LOC_MRTNote"));

		layout.serachTransaction(
				add_manual_transaction.targateDate1(
						Integer.parseInt(PropsUtil.getDataPropertyValue("Txn_LOC_ProjectedTScheduledDate"))),
				add_manual_transaction.targateDate1(
						Integer.parseInt(PropsUtil.getDataPropertyValue("Txn_LOC_ProjectedTScheduledDate"))));
		SeleniumUtil.waitForPageToLoad();
		SeleniumUtil.waitFor(3);
		SeleniumUtil.click(seriesTxn.projectedStranctionList()
				.get(Integer.parseInt(PropsUtil.getDataPropertyValue("Txn_LOC_MTAddedTxnIndex"))));
		seriesTxn.editOneTimeProjectedTxn(PropsUtil.getDataPropertyValue("Txn_LOC_MRTInstanceUpdateAmount"),
				PropsUtil.getDataPropertyValue("Txn_LOC_MRTInstanceUpdateDescription"),
				PropsUtil.getDataPropertyValue("Txn_LOC_MRTInstanceUpdateNote"));
		SeleniumUtil.click(seriesTxn.projectedStranctionList()
				.get(Integer.parseInt(PropsUtil.getDataPropertyValue("Txn_LOC_MTAddedTxnIndex"))));
		SeleniumUtil.waitForPageToLoad();
		SeleniumUtil.click(seriesTxn.shoeMore());
		SeleniumUtil.waitForPageToLoad();
		Assert.assertEquals(seriesTxn.amountField().getAttribute("value").trim(),
				PropsUtil.getDataPropertyValue("Txn_LOC_MRTInstanceDetailsUpdateAmount"),
				"txn amount is not displayed");
		Assert.assertEquals(seriesTxn.DescriptionField().getAttribute("value").trim(),
				PropsUtil.getDataPropertyValue("Txn_LOC_MRTInstanceUpdateDescription"),
				"txn description is not displayed");
		Assert.assertEquals(seriesTxn.noteField().getAttribute("value").trim(),
				PropsUtil.getDataPropertyValue("Txn_LOC_MRTInstanceUpdateNote"), "txn note is not displayed");

	}

	@Test(description = "AT-96974,AT-96979:Loc Account projected transaction edited", priority = 16, dependsOnMethods = "verifyLOCAccountProjTxn")
	public void verifyLOCAccountProjTxn2() {

		seriesTxn.editSeriesTransaction(Integer.parseInt(PropsUtil.getDataPropertyValue("Txn_LOC_MTAddedTxnIndex")),
				PropsUtil.getDataPropertyValue("Txn_LOC_MRTSeriesUpdateAmount"),
				PropsUtil.getDataPropertyValue("Txn_LOC_MRTSeriesUpdateNote"),
				PropsUtil.getDataPropertyValue("Txn_LOC_MRTSeriesUpdateDescription"));
		layout.serachTransaction(
				add_manual_transaction.targateDate1(
						Integer.parseInt(PropsUtil.getDataPropertyValue("Txn_LOC_ProjectedTScheduledDate2"))),
				add_manual_transaction.targateDate1(
						Integer.parseInt(PropsUtil.getDataPropertyValue("Txn_LOC_ProjectedTScheduledDate2"))));
		SeleniumUtil.waitForPageToLoad();
		SeleniumUtil.click(seriesTxn.projectedStranctionList()
				.get(Integer.parseInt(PropsUtil.getDataPropertyValue("Txn_LOC_MTAddedTxnIndex"))));
		SeleniumUtil.waitForPageToLoad();

		Assert.assertEquals(seriesTxn.amountField().getAttribute("value").trim(),
				PropsUtil.getDataPropertyValue("Txn_LOC_MRTSeriesDetailsUpdateAmount"), "txn amount is not displayed");
		Assert.assertEquals(seriesTxn.DescriptionField().getAttribute("value").trim(),
				PropsUtil.getDataPropertyValue("Txn_LOC_MRTSeriesUpdateDescription"),
				"txn description is not displayed");

	}

	@Test(description = "AT-96958:Verify HELoc account transaction", priority = 18, dependsOnMethods = "verifyAccountAdded")
	public void verifyHELOCAccountTxn() {
		d.navigate().refresh();
		SeleniumUtil.waitForPageToLoad();
		PageParser.forceNavigate("Transaction", d);
		SeleniumUtil.waitForPageToLoad();
		accountFilter.clickAccountFilter();
		SeleniumUtil.click(accountFilter.transactionAccountDropDownAccountGroupLink()
				.get(Integer.parseInt(PropsUtil.getDataPropertyValue("Txn_LOC_AccountFilterLink"))));
		// accountFilter.clickAllAccount();
		accountFilter.selectAccFilterOneAccount(PropsUtil.getDataPropertyValue("Txn_HELOC_AccountNum"));
		SeleniumUtil.waitForPageToLoad();
		accountFilter.expandProjectedTXn();
		boolean LocAccountTxn = false;
		int txnSize = accountFilter.allTransactionAccount().size();
		for (int i = 0; i < txnSize; i++) {
			if (accountFilter.allTransactionAccount().get(i).getText().trim().replaceAll("\n", " ")
					.equals(PropsUtil.getDataPropertyValue("Txn_HELOc_txnAccountName").trim())) {
				LocAccountTxn = true;

			} else {
				LocAccountTxn = false;
				break;
			}
		}
		Assert.assertTrue(LocAccountTxn,
				"advisor shared full view details accounts groups transaction are not displaying");
		Assert.assertEquals(accountFilter.accountFilterLink().getText().trim(),
				PropsUtil.getDataPropertyValue("Txn_HELOc_AccountFilterName"), "Loc account is not selected");

	}

	@Test(description = "AT-96968:verify Heloc account details", priority = 19, dependsOnMethods = "verifyHELOCAccountTxn")
	public void verifyHELOCAccountDetailsHeader() {
		SeleniumUtil.click(txnAccountIntg.expand());
		Assert.assertEquals(txnAccountIntg.loanDueDate().getText().trim(),
				add_manual_transaction.targateMonth(
						Integer.parseInt(PropsUtil.getDataPropertyValue("Txn_HELOc_txnAccountHeaderDueDate"))),
				"loan account due date is not displayed");
		Assert.assertEquals(txnAccountIntg.loanholdername().getText().trim(),
				PropsUtil.getDataPropertyValue("Txn_HELOC_txnAccountHeaderAcctHolder"),
				"Loan account holder name is not displayed");
		Assert.assertEquals(txnAccountIntg.loanDescription().getText().trim(),
				PropsUtil.getDataPropertyValue("Txn_HELOC_txnAccountHeaderDecription"),
				"Loan account description is not displayed");
		Assert.assertEquals(txnAccountIntg.loanDueAmount().getText().trim(),
				PropsUtil.getDataPropertyValue("Txn_HELOC_txnAccountHeaderAmountDue"),
				"Loan account due amount is not displayed");
		Assert.assertEquals(txnAccountIntg.loanprincipal().getText().trim(),
				PropsUtil.getDataPropertyValue("Txn_HELOC_txnAccountHeaderPrincipleAmount"),
				"Loan account loan principal amount is not displayed");
	}

	@Test(description = "AT-96969:verify Heloc Account deposit transaction", priority = 20, dependsOnMethods = "verifyHELOCAccountTxn")
	public void verifyHELOCAccountsDepostTxn() {
		boolean allAccountType = false;

		SeleniumUtil.click(txnFilter.transactionTypeDeposit());
		SeleniumUtil.waitForPageToLoad();
		int txnsize = accountFilter.allTransactionDescription().size();
		for (int i = 0; i < txnsize; i++) {
			SeleniumUtil.click(accountFilter.allTransactionDescription().get(i));
			if (readOnly.transactionDetailsReadonlyAccountLabel().getText().trim()
					.equals(PropsUtil.getDataPropertyValue("Txn_LOC_txnCreditedTo"))) {
				allAccountType = true;
			} else {
				allAccountType = false;
				break;
			}
		}

		Assert.assertTrue(allAccountType,
				"Credited to  transaction type is not displayed when select Deposit Transaction type");

	}

	@Test(description = "AT-96970:verify HEloc Account withdrawals transaction", priority = 21, dependsOnMethods = "verifyHELOCAccountTxn")
	public void verifyHELOCAccountsWithDrawalTxn() {
		SeleniumUtil.click(txnFilter.transactionTypeWithDrawal());
		SeleniumUtil.waitForPageToLoad();
		int txnsize = accountFilter.allTransactionDescription().size();
		boolean allAccountType = false;
		for (int i = 0; i < txnsize; i++) {
			SeleniumUtil.click(accountFilter.allTransactionDescription().get(i));
			if (readOnly.transactionDetailsReadonlyAccountLabel().getText().trim()
					.equals(PropsUtil.getDataPropertyValue("Txn_LOC_txnDebitedFrom"))) {
				allAccountType = true;
			} else {
				allAccountType = false;
				break;
			}
		}

		Assert.assertTrue(allAccountType,
				"debited from  transaction type is not displayed displayed when select withdrawal Transaction type");
	}

	@Test(description = "AT-96970:verify HEloc Account Transaction row details", priority = 22, dependsOnMethods = "verifyHELOCAccountTxn")
	public void verifyHELOCAccountTxnRowDetails() {
		String description = null;
		;
		String category = null;
		String account = null;
		String cateName = null;
		SeleniumUtil.click(txnFilter.transactionTypeAll());
		SeleniumUtil.waitForPageToLoad();

		if (PropsUtil.getEnvPropertyValue("CategoryList43").equalsIgnoreCase("yes")) {
			cateName = PropsUtil.getDataPropertyValue("Txn_HELOC_txnCatgeory_43");
		} else {
			cateName = PropsUtil.getDataPropertyValue("Txn_HELOC_txnCatgeory");
		}
		int txnsize = add_manual_transaction.getAllPostedAmount1().size();

		for (int i = 0; i < txnsize; i++) {

			if (add_manual_transaction.getAllPostedAmount1().get(i).getText()
					.equals(PropsUtil.getDataPropertyValue("Txn_HELOC_txnAmount"))) {
				description = add_manual_transaction.getAlldescription1().get(i).getText().trim();
				category = add_manual_transaction.getAllPostedCat1().get(i).getText().trim();
				account = add_manual_transaction.getAllPostedAcount1().get(i).getText().trim().replaceAll("\n", " ");
				SeleniumUtil.click(aggTxn.collapsIcon().get(i));
				break;

			}
		}

		Assert.assertEquals(description, PropsUtil.getDataPropertyValue("Txn_HELOC_txnDecription"),
				"transaction description is not dsiplaying");
		Assert.assertEquals(category, cateName, "transaction Category is not displaying");
		Assert.assertEquals(account, PropsUtil.getDataPropertyValue("Txn_HELOc_txnAccountName"),
				"transaction Accounts is not displaying");

	}

	@Test(description = "AT-96972:Verify HELoc account transaction details", priority = 23, dependsOnMethods = "verifyHELOCAccountTxnRowDetails")
	public void verifyHELOCAccountTxnDetails() {
		String cateName = null;
		if (PropsUtil.getEnvPropertyValue("CategoryList43").equalsIgnoreCase("yes")) {
			cateName = PropsUtil.getDataPropertyValue("Txn_HELOC_txnCatgeory_43");
		} else {
			cateName = PropsUtil.getDataPropertyValue("Txn_HELOC_txnCatgeory");
		}
		Assert.assertEquals(aggTxn.Amount().getText(), PropsUtil.getDataPropertyValue("Txn_HELOC_txnAmount"),
				"txn amount is not displayed");
		Assert.assertEquals(aggTxn.descField().getAttribute("value"),
				PropsUtil.getDataPropertyValue("Txn_HELOC_txnDecription"), "txn description is not displayed");
		Assert.assertEquals(readOnly.transactionDetailsReadonlyStDescriptionValue().getText(),
				PropsUtil.getDataPropertyValue("Txn_HELOC_txnSTDecription"),
				"txn statement description is not displayed");
		Assert.assertEquals(aggTxn.tansactiondate().getText(),
				add_manual_transaction
						.targateDate1(Integer.parseInt(PropsUtil.getDataPropertyValue("Txn_HELOC_txnScheduledDate"))),
				"txn Scheduled date is not displayed");
		Assert.assertEquals(aggTxn.catgoryField().getText(), cateName, "txn category is not displayed");

	}

	@Test(description = "AT-96990:verify HELOc account add cal link", priority = 24, dependsOnMethods = "verifyHELOCAccountTxnDetails")
	public void verifyHELOCAccountAddToCal() {

		Assert.assertTrue(
				addToCal.addcalLinkList().size() == Integer
						.parseInt(PropsUtil.getDataPropertyValue("Txn_HELOC_AddCAlLink")),
				"add calendar link is dispalyed");
	}

	@Test(description = "AT-96990:verify HElocAccount create rule option", priority = 25, dependsOnMethods = "verifyHELOCAccountTxnDetails")
	public void verifyHELOCAccountCreateRule() {
		SeleniumUtil.click(aggTxn.catdropDownIcon());
		SeleniumUtil.click(aggTxn.catgoryList(4, 1));

		Assert.assertEquals(aggTxn.catChanegMessage().getText(), PropsUtil.getDataPropertyValue("AggCatChangeMessage"),
				"create rule message is not displayed");
		Assert.assertTrue(aggTxn.createRule().isDisplayed(), "create rule icon is not displayed");
	}

	@Test(description = "AT-96981:verify HELoc Account transaction split", priority = 26, dependsOnMethods = "verifyHELOCAccountTxnDetails")
	public void verifyHELOCAccountTxnSplit() {
		splitTxn.splitTxn();
		SeleniumUtil.waitUntilToastMessageIsDisappeared();
		Assert.assertEquals(layout.LayoutSplitIcon().size(),
				Integer.parseInt(PropsUtil.getDataPropertyValue("Txn_HELOC_txnSplitSize")), "txn is not splited");
	}

	@Test(description = "AT-96982:verify HEloc Account group transaction", priority = 27, dependsOnMethods = "verifyAccountAdded")
	public void verifyHELOCAccountGroupTxn() {
		PageParser.forceNavigate("AccountsPage", d);
		SeleniumUtil.waitForPageToLoad();
		SeleniumUtil.click(groupView.groupType());
		SeleniumUtil.waitForPageToLoad();
		groupView.createGroupMore(PropsUtil.getDataPropertyValue("Txn_HELOC_GroupNameList").split(","),
				PropsUtil.getDataPropertyValue("Txn_HELOC_GroupAccountList").split(","));

		SeleniumUtil.waitForPageToLoad();
		PageParser.forceNavigate("Transaction", d);
		SeleniumUtil.waitForPageToLoad();
		accountFilter.clickAccountFilter();
		SeleniumUtil.click(accountFilter.transactionAccountDropDownAccountGroupLink()
				.get(Integer.parseInt(PropsUtil.getDataPropertyValue("Txn_HELOC_groupFilterLink"))));
		SeleniumUtil.click(accountFilter
				.transactionAccountDropDownGroupName(PropsUtil.getDataPropertyValue("Txn_HELOC_GroupName")));
		SeleniumUtil.waitForPageToLoad();
		accountFilter.expandProjectedTXn();
		boolean LocAccountTxn = false;
		int txnsize = accountFilter.allTransactionAccount().size();
		for (int i = 0; i < txnsize; i++) {
			if (accountFilter.allTransactionAccount().get(i).getText().trim().replaceAll("\n", " ")
					.equals(PropsUtil.getDataPropertyValue("Txn_HELOc_txnAccountName").trim())) {
				LocAccountTxn = true;

			} else {
				LocAccountTxn = false;
				break;
			}
		}
		Assert.assertTrue(LocAccountTxn, "group transaction is not displayed");

	}

	@Test(description = "AT-96983:verify HELOc Account manual transaction", priority = 28, dependsOnMethods = "verifyHELOCAccountGroupTxn")
	public void verifyHELOCAccountManualTxn() {
		String cateName = null;
		if (PropsUtil.getEnvPropertyValue("CategoryList43").equalsIgnoreCase("yes")) {
			cateName = PropsUtil.getDataPropertyValue("Txn_HELOC_MTMLCCat1_43");
		} else {
			cateName = PropsUtil.getDataPropertyValue("Txn_HELOC_MTMLCCat1");
		}
		add_manual_transaction.clickMTLink();
		add_manual_transaction.createOneTimeTransaction(PropsUtil.getDataPropertyValue("Txn_HELOC_MTAmount1"),
				PropsUtil.getDataPropertyValue("Txn_HELOC_MTDescription1"),
				PropsUtil.getDataPropertyValue("Txn_HELOC_MTTxnType1"),
				PropsUtil.getDataPropertyValue("Txn_HELOC_MTAccount1"),
				PropsUtil.getDataPropertyValue("Txn_HELOC_MTfrequency1"),
				Integer.parseInt(PropsUtil.getDataPropertyValue("Txn_HELOC_MTDate1")), cateName,
				PropsUtil.getDataPropertyValue("Txn_HELOC_MTNote1"),
				PropsUtil.getDataPropertyValue("Txn_HELOC_MTCheck"));
		layout.serachTransaction(
				add_manual_transaction
						.targateDate1(Integer.parseInt(PropsUtil.getDataPropertyValue("Txn_HELOC_MTDate1"))),
				add_manual_transaction
						.targateDate1(Integer.parseInt(PropsUtil.getDataPropertyValue("Txn_HELOC_MTDate1"))));
		SeleniumUtil.waitForPageToLoad();
		SeleniumUtil.waitFor(2);
		Assert.assertEquals(seriesTxn.getAlldescription1()
				.get(Integer.parseInt(PropsUtil.getDataPropertyValue("Txn_HELOC_MTAddedTxnIndex"))).getText().trim(),
				PropsUtil.getDataPropertyValue("Txn_HELOC_MTDescription1"),
				"transaction description is not dsiplaying");
		Assert.assertEquals(seriesTxn.getAllcat1()
				.get(Integer.parseInt(PropsUtil.getDataPropertyValue("Txn_HELOC_MTAddedTxnIndex"))).getText().trim(),
				cateName, "transaction Category is not displaying");
		Assert.assertEquals(
				seriesTxn.getAllaccount1()
						.get(Integer.parseInt(PropsUtil.getDataPropertyValue("Txn_HELOC_MTAddedTxnIndex"))).getText()
						.trim().replaceAll("\n", " "),
				PropsUtil.getDataPropertyValue("Txn_HELOC_MTRowAccount"), "transaction Accounts is not displaying");

	}

	@Test(description = "AT-96983;verify HEloc Account edit transaction", priority = 29, dependsOnMethods = "verifyHELOCAccountManualTxn")
	public void verifyHELOCAccountManualTxnEdit() {

		seriesTxn.editOneTimeProjectedTxn(Integer.parseInt(PropsUtil.getDataPropertyValue("Txn_HELOC_MTAddedTxnIndex")),
				PropsUtil.getDataPropertyValue("Txn_HELOC_MTeditAmount"),
				add_manual_transaction
						.targateDate1(Integer.parseInt(PropsUtil.getDataPropertyValue("Txn_HELOC_MTEditDate"))),
				PropsUtil.getDataPropertyValue("Txn_HELOC_MTEditDescription"),
				PropsUtil.getDataPropertyValue("Txn_HELOC_MTEditNote"));
		layout.serachTransaction(
				add_manual_transaction
						.targateDate1(Integer.parseInt(PropsUtil.getDataPropertyValue("Txn_HELOC_MTEditDate"))),
				add_manual_transaction
						.targateDate1(Integer.parseInt(PropsUtil.getDataPropertyValue("Txn_HELOC_MTEditDate"))));
		SeleniumUtil.waitForPageToLoad();
		SeleniumUtil.click(seriesTxn.getAllAmount1()
				.get(Integer.parseInt(PropsUtil.getDataPropertyValue("Txn_HELOC_MTAddedTxnIndex"))));
		SeleniumUtil.waitForPageToLoad();
		SeleniumUtil.click(seriesTxn.shoeMore());
		SeleniumUtil.waitForPageToLoad();
		Assert.assertEquals(seriesTxn.amountField().getAttribute("value").trim(),
				PropsUtil.getDataPropertyValue("Txn_HELOC_MTDetailsUpdatedAmount"), "txn amount not displayed");
		Assert.assertEquals(seriesTxn.DescriptionField().getAttribute("value").trim(),
				PropsUtil.getDataPropertyValue("Txn_HELOC_MTEditDescription"), "txn description is not displayed");
		Assert.assertEquals(seriesTxn.Scheduledate().getAttribute("value").trim(),
				add_manual_transaction
						.targateDate1(Integer.parseInt(PropsUtil.getDataPropertyValue("Txn_HELOC_MTEditDate"))),
				" txn scheduled date is not displayed");
		Assert.assertEquals(seriesTxn.noteField().getAttribute("value").trim(),
				PropsUtil.getDataPropertyValue("Txn_HELOC_MTEditNote"), "txn note field is not displayed");

	}

	@Test(description = "AT-96974,AT-96978:verify HEloc Account peojected txn", priority = 30, dependsOnMethods = "verifyHELOCAccountManualTxnEdit")
	public void verifyHELOCAccountProjTxn() {
		PageParser.forceNavigate("Transaction", d);
		SeleniumUtil.waitForPageToLoad();
		add_manual_transaction.clickMTLink();
		add_manual_transaction.createTransactionWithRecuralldetails(
				PropsUtil.getDataPropertyValue("Txn_HELOC_MRTAmount"),
				PropsUtil.getDataPropertyValue("Txn_HELOC_MRTdescription"),
				PropsUtil.getDataPropertyValue("Txn_HELOC_MRTAccount"),
				PropsUtil.getDataPropertyValue("Txn_HELOC_MRTFrequency"),
				Integer.parseInt(PropsUtil.getDataPropertyValue("Txn_HELOC_MRTScheduledDate")),
				Integer.parseInt(PropsUtil.getDataPropertyValue("Txn_HELOC_MRTEndDate")),
				PropsUtil.getDataPropertyValue("Txn_HELOC_MRTHLC"),
				PropsUtil.getDataPropertyValue("Txn_HELOC_MRTNote"));

		layout.serachTransaction(
				add_manual_transaction.targateDate1(
						Integer.parseInt(PropsUtil.getDataPropertyValue("Txn_HELOC_projectedScheduledDate"))),
				add_manual_transaction.targateDate1(
						Integer.parseInt(PropsUtil.getDataPropertyValue("Txn_HELOC_projectedScheduledDate"))));
		SeleniumUtil.waitForPageToLoad();
		SeleniumUtil.click(seriesTxn.projectedStranctionList()
				.get(Integer.parseInt(PropsUtil.getDataPropertyValue("Txn_HELOC_MTAddedTxnIndex"))));
		accountFilter.expandProjectedTXn();
		seriesTxn.editOneTimeProjectedTxn(PropsUtil.getDataPropertyValue("Txn_HELOC_MRTInstanceUpdateAmount"),
				PropsUtil.getDataPropertyValue("Txn_HELOC_MRTInstanceUpdateDescription"),
				PropsUtil.getDataPropertyValue("Txn_HELOC_MRTInstanceUpdateNote"));
		SeleniumUtil.click(seriesTxn.projectedStranctionList()
				.get(Integer.parseInt(PropsUtil.getDataPropertyValue("Txn_HELOC_MTAddedTxnIndex"))));
		SeleniumUtil.waitForPageToLoad();
		SeleniumUtil.click(seriesTxn.shoeMore());
		SeleniumUtil.waitForPageToLoad();
		Assert.assertEquals(seriesTxn.amountField().getAttribute("value").trim(),
				PropsUtil.getDataPropertyValue("Txn_HELOC_MRTInstanceDetailsUpdateAmount"), "txn amount not displayed");
		Assert.assertEquals(seriesTxn.DescriptionField().getAttribute("value").trim(),
				PropsUtil.getDataPropertyValue("Txn_HELOC_MRTInstanceUpdateDescription"),
				"txn description is not displayed");
		Assert.assertEquals(seriesTxn.noteField().getAttribute("value").trim(),
				PropsUtil.getDataPropertyValue("Txn_HELOC_MRTInstanceUpdateNote"), "txn note field is not displayed");
	}

	@Test(description = "AT-96974,AT-96979:verify HEloc Account projected txn edit", priority = 31, dependsOnMethods = "verifyHELOCAccountProjTxn")
	public void verifyHELOCAccountProjTxn2() {
		seriesTxn.editSeriesTransaction(Integer.parseInt(PropsUtil.getDataPropertyValue("Txn_HELOC_MTAddedTxnIndex")),
				PropsUtil.getDataPropertyValue("Txn_HELOC_MRTSeriesUpdateAmount"),
				PropsUtil.getDataPropertyValue("Txn_HELOC_MRTSeriesUpdateNote"),
				PropsUtil.getDataPropertyValue("Txn_HELOC_MRTSeriesUpdateDescription"));
		SeleniumUtil.waitForPageToLoad();
		layout.serachTransaction(
				add_manual_transaction.targateDate1(
						Integer.parseInt(PropsUtil.getDataPropertyValue("Txn_HELOC_projectedScheduledDate2"))),
				add_manual_transaction.targateDate1(
						Integer.parseInt(PropsUtil.getDataPropertyValue("Txn_HELOC_projectedScheduledDate2"))));
		SeleniumUtil.waitForPageToLoad();
		SeleniumUtil.click(seriesTxn.projectedStranctionList()
				.get(Integer.parseInt(PropsUtil.getDataPropertyValue("Txn_HELOC_MTAddedTxnIndex"))));
		SeleniumUtil.waitForPageToLoad();

		Assert.assertEquals(seriesTxn.amountField().getAttribute("value").trim(),
				PropsUtil.getDataPropertyValue("Txn_HELOC_MRTSeriesDetailsUpdateAmount"), "txn amount not displayed");
		Assert.assertEquals(seriesTxn.DescriptionField().getAttribute("value").trim(),
				PropsUtil.getDataPropertyValue("Txn_HELOC_MRTSeriesUpdateDescription"),
				"txn description is not displayed");

	}

	@Test(description = "AT-96958:verify SBloc Account transaction", priority = 32, dependsOnMethods = "verifyAccountAdded")
	public void verifySBLOCAccountTxn() {
		d.navigate().refresh();
		SeleniumUtil.waitForPageToLoad();
		PageParser.forceNavigate("Transaction", d);
		SeleniumUtil.waitForPageToLoad();
		accountFilter.clickAccountFilter();
		SeleniumUtil.click(accountFilter.transactionAccountDropDownAccountGroupLink()
				.get(Integer.parseInt(PropsUtil.getDataPropertyValue("Txn_LOC_AccountFilterLink"))));
		// accountFilter.clickAllAccount();
		accountFilter.selectAccFilterOneAccount(PropsUtil.getDataPropertyValue("Txn_SBLOC_AccountNum"));
		SeleniumUtil.waitForPageToLoad();
		SeleniumUtil.waitFor(3);
		accountFilter.expandProjectedTXn();
		boolean LocAccountTxn = false;
		int txnSize = accountFilter.allTransactionAccount().size();
		for (int i = 0; i < txnSize; i++) {
			if (accountFilter.allTransactionAccount().get(i).getText().trim().replaceAll("\n", " ")
					.equals(PropsUtil.getDataPropertyValue("Txn_SBLOc_txnAccountName").trim())) {
				LocAccountTxn = true;

			} else {
				LocAccountTxn = false;
				break;
			}
		}
		Assert.assertTrue(LocAccountTxn, " all SBloc account txn is not displayed");
		if (PropsUtil.getEnvPropertyValue("cnf_SDG").equalsIgnoreCase("OFF")) {
			Assert.assertEquals(accountFilter.accountFilterLink().getText().trim(),
					PropsUtil.getDataPropertyValue("Txn_SBLOc_AccountFilterNameSDGOFF"), "Loc account is not selected");

		} else {
			Assert.assertEquals(accountFilter.accountFilterLink().getText().trim(),
					PropsUtil.getDataPropertyValue("Txn_SBLOc_AccountFilterName"), "Loc account is not selected");
		}
	}

	@Test(description = "AT-96968:verify SBlocAccount account details", priority = 33, dependsOnMethods = "verifySBLOCAccountTxn")
	public void verifySBLOCAccountDetailsHeader() {
		SeleniumUtil.click(txnAccountIntg.expand());
		Assert.assertEquals(txnAccountIntg.loanDueDate().getText().trim(),
				add_manual_transaction.targateMonth(
						Integer.parseInt(PropsUtil.getDataPropertyValue("Txn_SBLOc_txnAccountHeaderDueDate"))),
				"loan account due date is not displayed");
		Assert.assertEquals(txnAccountIntg.loanholdername().getText().trim(),
				PropsUtil.getDataPropertyValue("Txn_SBLOC_txnAccountHeaderAcctHolder"),
				"loan account holder name is not displayed");
		Assert.assertEquals(txnAccountIntg.loanDescription().getText().trim(),
				PropsUtil.getDataPropertyValue("Txn_SBLOC_txnAccountHeaderDecription"),
				"loan account description name is not displayed");
		Assert.assertEquals(txnAccountIntg.loanDueAmount().getText().trim(),
				PropsUtil.getDataPropertyValue("Txn_SBLOC_txnAccountHeaderAmountDue"),
				"loan account due amount is not displayed");
		Assert.assertEquals(txnAccountIntg.loanprincipal().getText().trim(),
				PropsUtil.getDataPropertyValue("Txn_SBLOC_txnAccountHeaderPrincipleAmount"),
				"loan account principal amount is not displayed");
	}

	@Test(description = "AT-96969:verify SBLocAccount Deposit Transaction", priority = 34, dependsOnMethods = "verifySBLOCAccountTxn")
	public void verifySBLOCAccountsDepostTxn() {
		boolean allAccountType = false;

		SeleniumUtil.click(txnFilter.transactionTypeDeposit());
		SeleniumUtil.waitForPageToLoad();
		int txnsize = accountFilter.allTransactionDescription().size();
		for (int i = 0; i < txnsize; i++) {
			SeleniumUtil.click(accountFilter.allTransactionDescription().get(i));
			if (readOnly.transactionDetailsReadonlyAccountLabel().getText().trim()
					.equals(PropsUtil.getDataPropertyValue("Txn_SBLOC_txnCreditedTo"))) {
				allAccountType = true;
			} else {
				allAccountType = false;
				break;
			}
		}

		Assert.assertTrue(allAccountType,
				"creadited to  transaction type should displayed when select deposit Transaction type");

	}

	@Test(description = "AT-96970:Verify SBLocAccount withdrawals transaction", priority = 35, dependsOnMethods = "verifySBLOCAccountTxn")
	public void verifySBLOCAccountsWithDrawalTxn() {
		SeleniumUtil.click(txnFilter.transactionTypeWithDrawal());
		SeleniumUtil.waitForPageToLoad();
		boolean allAccountType = false;
		int txnsize = accountFilter.allTransactionDescription().size();
		for (int i = 0; i < txnsize; i++) {
			SeleniumUtil.click(accountFilter.allTransactionDescription().get(i));
			if (readOnly.transactionDetailsReadonlyAccountLabel().getText().trim()
					.equals(PropsUtil.getDataPropertyValue("Txn_SBLOC_txnDebitedFrom"))) {
				allAccountType = true;
			} else {
				allAccountType = false;
				break;
			}
		}

		Assert.assertTrue(allAccountType,
				"debited from  transaction type should displayed when select withdrawal Transaction type");
	}

	@Test(description = "AT-96970:Verify SBLOc transaction row details", priority = 36, dependsOnMethods = "verifySBLOCAccountTxn")
	public void verifySBLOCAccountTxnRowDetails() {
		String cateName = null;
		String description = null;
		;
		String category = null;
		String account = null;
		SeleniumUtil.click(txnFilter.transactionTypeAll());
		SeleniumUtil.waitForPageToLoad();

		if (PropsUtil.getEnvPropertyValue("CategoryList43").equalsIgnoreCase("yes")) {
			cateName = PropsUtil.getDataPropertyValue("Txn_SBLOC_txnCatgeory_43");
		} else {
			cateName = PropsUtil.getDataPropertyValue("Txn_SBLOC_txnCatgeory");
		}

		int txnsize = add_manual_transaction.getAllPostedAmount1().size();
		for (int i = 0; i < txnsize; i++) {
			System.out.println(PropsUtil.getDataPropertyValue("Txn_SBLOC_txnAmount"));
			System.out.println(add_manual_transaction.getAllPostedAmount1().get(i).getText());
			if (add_manual_transaction.getAllPostedAmount1().get(i).getText()
					.equals(PropsUtil.getDataPropertyValue("Txn_SBLOC_txnAmount"))) {
				description = add_manual_transaction.getAlldescription1().get(i).getText().trim();
				category = add_manual_transaction.getAllPostedCat1().get(i).getText().trim();
				account = add_manual_transaction.getAllPostedAcount1().get(i).getText().trim().replaceAll("\n", " ");
				SeleniumUtil.click(aggTxn.collapsIcon().get(i));
				break;

			}
		}

		Assert.assertEquals(description, PropsUtil.getDataPropertyValue("Txn_SBLOC_txnDecription"),
				"transaction description is not dsiplaying");
		Assert.assertEquals(category, cateName, "transaction Category is not displaying");
		Assert.assertEquals(account, PropsUtil.getDataPropertyValue("Txn_SBLOc_txnAccountName"),
				"transaction Accounts is not displaying");

	}

	@Test(description = "AT-96972:verify SBLoc Account transaction details", priority = 37, dependsOnMethods = "verifySBLOCAccountTxnRowDetails")
	public void verifySBLOCAccountTxnDetails() {
		String cateName = null;
		if (PropsUtil.getEnvPropertyValue("CategoryList43").equalsIgnoreCase("yes")) {
			cateName = PropsUtil.getDataPropertyValue("Txn_SBLOC_txnCatgeory_43");
		} else {
			cateName = PropsUtil.getDataPropertyValue("Txn_SBLOC_txnCatgeory");
		}
		Assert.assertEquals(aggTxn.Amount().getText(), PropsUtil.getDataPropertyValue("Txn_SBLOC_txnAmount"),
				"txn amount is not displayed");
		Assert.assertEquals(aggTxn.descField().getAttribute("value"),
				PropsUtil.getDataPropertyValue("Txn_SBLOC_txnDecription"), "txn description is not displayed");
		Assert.assertEquals(readOnly.transactionDetailsReadonlyStDescriptionValue().getText(),
				PropsUtil.getDataPropertyValue("Txn_SBLOC_txnSTDecription"),
				"txn statment description is not displayed");
		Assert.assertEquals(aggTxn.tansactiondate().getText(),
				add_manual_transaction
						.targateDate1(Integer.parseInt(PropsUtil.getDataPropertyValue("Txn_SBLOC_txnScheduledDate"))),
				"txn scheduled date is not displayed");
		Assert.assertEquals(aggTxn.catgoryField().getText(), cateName, "txn category name is not displayed");

	}

	@Test(description = "AT-96990:verify SBloc account add calendar option", priority = 38, dependsOnMethods = "verifySBLOCAccountTxnDetails")
	public void verifySBLOCAccountAddToCal() {

		Assert.assertTrue(addToCal.addcalLinkList().size() == Integer
				.parseInt(PropsUtil.getDataPropertyValue("Txn_SBLOC_AddCAlLink")), "add calendar is displayed");
	}

	@Test(description = "AT-96990:verify create rule", priority = 39, dependsOnMethods = "verifySBLOCAccountTxnDetails")
	public void verifySBLOCAccountCreateRule() {
		SeleniumUtil.click(aggTxn.catdropDownIcon());
		SeleniumUtil.click(aggTxn.catgoryList(4, 1));

		Assert.assertEquals(aggTxn.catChanegMessage().getText(), PropsUtil.getDataPropertyValue("AggCatChangeMessage"),
				"create rule message is not displayed");
		Assert.assertTrue(aggTxn.createRule().isDisplayed(), "create rule icon is not displayed");
	}

	@Test(description = "AT-96981:verify SBloc Account transaction split", priority = 40, dependsOnMethods = "verifySBLOCAccountTxnDetails")
	public void verifySBLOCAccountTxnSplit() {

		splitTxn.splitTxn();
		Assert.assertEquals(layout.LayoutSplitIcon().size(),
				Integer.parseInt(PropsUtil.getDataPropertyValue("Txn_SBLOC_txnSplitSize")), "txn is not splited");
	}

	@Test(description = "AT-96982:verify SBlocAccount group transaction", priority = 41, dependsOnMethods = "verifyAccountAdded")
	public void verifySBLOCAccountGroupTxn() {
		PageParser.forceNavigate("AccountsPage", d);
		SeleniumUtil.waitForPageToLoad();
		SeleniumUtil.click(groupView.groupType());
		SeleniumUtil.waitForPageToLoad();
		groupView.createGroupMore(PropsUtil.getDataPropertyValue("Txn_SBLOC_GroupNameList").split(","),
				PropsUtil.getDataPropertyValue("Txn_SBLOC_GroupAccountList").split(","));
		SeleniumUtil.waitForPageToLoad();
		PageParser.forceNavigate("Transaction", d);
		SeleniumUtil.waitForPageToLoad();
		accountFilter.clickAccountFilter();
		SeleniumUtil.click(accountFilter.transactionAccountDropDownAccountGroupLink()
				.get(Integer.parseInt(PropsUtil.getDataPropertyValue("Txn_SBLOC_groupFilterLink"))));
		SeleniumUtil.click(accountFilter
				.transactionAccountDropDownGroupName(PropsUtil.getDataPropertyValue("Txn_SBLOC_GroupName")));
		SeleniumUtil.waitForPageToLoad();
		accountFilter.expandProjectedTXn();
		boolean LocAccountTxn = false;
		int txnsize = accountFilter.allTransactionAccount().size();
		for (int i = 0; i < txnsize; i++) {
			if (accountFilter.allTransactionAccount().get(i).getText().trim().replaceAll("\n", " ")
					.equals(PropsUtil.getDataPropertyValue("Txn_SBLOc_txnAccountName").trim())) {
				LocAccountTxn = true;

			} else {
				LocAccountTxn = false;
				break;
			}
		}
		Assert.assertTrue(LocAccountTxn, "group account transaction is not displayed");

	}

	@Test(description = "AT-96983:verify SBlocAccount manual txn", priority = 42, dependsOnMethods = "verifySBLOCAccountGroupTxn")
	public void verifySBLOCAccountManualTxn() {
		String cateName = null;
		if (PropsUtil.getEnvPropertyValue("CategoryList43").equalsIgnoreCase("yes")) {
			cateName = PropsUtil.getDataPropertyValue("Txn_SBLOC_MTMLCCat1_43");
		} else {
			cateName = PropsUtil.getDataPropertyValue("Txn_SBLOC_MTMLCCat1");
		}
		add_manual_transaction.clickMTLink();
		add_manual_transaction.createOneTimeTransaction(PropsUtil.getDataPropertyValue("Txn_SBLOC_MTAmount1"),
				PropsUtil.getDataPropertyValue("Txn_SBLOC_MTDescription1"),
				PropsUtil.getDataPropertyValue("Txn_SBLOC_MTTxnType1"),
				PropsUtil.getDataPropertyValue("Txn_SBLOC_MTAccount1"),
				PropsUtil.getDataPropertyValue("Txn_SBLOC_MTfrequency1"),
				Integer.parseInt(PropsUtil.getDataPropertyValue("Txn_SBLOC_MTDate1")), cateName,
				PropsUtil.getDataPropertyValue("Txn_SBLOC_MTNote1"),
				PropsUtil.getDataPropertyValue("Txn_SBLOC_MTCheck"));
		layout.serachTransaction(
				add_manual_transaction
						.targateDate1(Integer.parseInt(PropsUtil.getDataPropertyValue("Txn_SBLOC_MTDate1"))),
				add_manual_transaction
						.targateDate1(Integer.parseInt(PropsUtil.getDataPropertyValue("Txn_SBLOC_MTDate1"))));
		SeleniumUtil.waitForPageToLoad();

		Assert.assertEquals(seriesTxn.getAlldescription1()
				.get(Integer.parseInt(PropsUtil.getDataPropertyValue("Txn_SBLOC_MTAddedTxnIndex"))).getText().trim(),
				PropsUtil.getDataPropertyValue("Txn_SBLOC_MTDescription1"),
				"transaction description is not dsiplaying");
		Assert.assertEquals(seriesTxn.getAllcat1()
				.get(Integer.parseInt(PropsUtil.getDataPropertyValue("Txn_SBLOC_MTAddedTxnIndex"))).getText().trim(),
				cateName, "transaction Category is not displaying");
		Assert.assertEquals(
				seriesTxn.getAllaccount1()
						.get(Integer.parseInt(PropsUtil.getDataPropertyValue("Txn_SBLOC_MTAddedTxnIndex"))).getText()
						.trim().replaceAll("\n", " "),
				PropsUtil.getDataPropertyValue("Txn_SBLOC_MTRowAccount"), "transaction Accounts is not displaying");

	}

	@Test(description = "AT-96975,AT-96984:verify SB loc account manual txn edit", priority = 43, dependsOnMethods = "verifySBLOCAccountManualTxn")
	public void verifySBLOCAccountManualTxnEdit() {

		seriesTxn.editOneTimeProjectedTxn(Integer.parseInt(PropsUtil.getDataPropertyValue("Txn_SBLOC_MTAddedTxnIndex")),
				PropsUtil.getDataPropertyValue("Txn_SBLOC_MTeditAmount"),
				add_manual_transaction
						.targateDate1(Integer.parseInt(PropsUtil.getDataPropertyValue("Txn_SBLOC_MTEditDate"))),
				PropsUtil.getDataPropertyValue("Txn_SBLOC_MTEditDescription"),
				PropsUtil.getDataPropertyValue("Txn_SBLOC_MTEditNote"));
		layout.serachTransaction(
				add_manual_transaction
						.targateDate1(Integer.parseInt(PropsUtil.getDataPropertyValue("Txn_SBLOC_MTEditDate"))),
				add_manual_transaction
						.targateDate1(Integer.parseInt(PropsUtil.getDataPropertyValue("Txn_SBLOC_MTEditDate"))));
		SeleniumUtil.waitForPageToLoad();
		SeleniumUtil.click(seriesTxn.getAllAmount1()
				.get(Integer.parseInt(PropsUtil.getDataPropertyValue("Txn_SBLOC_MTAddedTxnIndex"))));
		SeleniumUtil.waitForPageToLoad();
		SeleniumUtil.click(seriesTxn.shoeMore());
		SeleniumUtil.waitForPageToLoad();
		Assert.assertEquals(seriesTxn.amountField().getAttribute("value").trim(),
				PropsUtil.getDataPropertyValue("Txn_SBLOC_MTDettailsUpdtaedAmount"), "txn amount is not displayed");
		Assert.assertEquals(seriesTxn.DescriptionField().getAttribute("value").trim(),
				PropsUtil.getDataPropertyValue("Txn_SBLOC_MTEditDescription"), "txn description is not displayed");
		Assert.assertEquals(seriesTxn.Scheduledate().getAttribute("value").trim(),
				add_manual_transaction
						.targateDate1(Integer.parseInt(PropsUtil.getDataPropertyValue("Txn_SBLOC_MTEditDate"))),
				"txn scheduled date is not displayed");
		Assert.assertEquals(seriesTxn.noteField().getAttribute("value").trim(),
				PropsUtil.getDataPropertyValue("Txn_SBLOC_MTEditNote"), "txn note is not displayed");

	}

	@Test(description = "AT-96974,AT-96978:verify SB loc Projected txn", priority = 44, dependsOnMethods = "verifySBLOCAccountManualTxnEdit")
	public void verifySBLOCAccountProjTxn() {
		PageParser.forceNavigate("Transaction", d);
		SeleniumUtil.waitForPageToLoad();
		add_manual_transaction.clickMTLink();
		add_manual_transaction.createTransactionWithRecuralldetails(
				PropsUtil.getDataPropertyValue("Txn_SBLOC_MRTAmount"),
				PropsUtil.getDataPropertyValue("Txn_SBLOC_MRTdescription"),
				PropsUtil.getDataPropertyValue("Txn_SBLOC_MRTAccount"),
				PropsUtil.getDataPropertyValue("Txn_SBLOC_MRTFrequency"),
				Integer.parseInt(PropsUtil.getDataPropertyValue("Txn_SBLOC_MRTScheduledDate")),
				Integer.parseInt(PropsUtil.getDataPropertyValue("Txn_SBLOC_MRTEndDate")),
				PropsUtil.getDataPropertyValue("Txn_SBLOC_MRTHLC"),
				PropsUtil.getDataPropertyValue("Txn_SBLOC_MRTNote"));

		layout.serachTransaction(
				add_manual_transaction
						.targateDate1(Integer.parseInt(PropsUtil.getDataPropertyValue("Txn_SBLOC_MRTScheduledDate"))),
				add_manual_transaction
						.targateDate1(Integer.parseInt(PropsUtil.getDataPropertyValue("Txn_SBLOC_MRTScheduledDate"))));
		SeleniumUtil.waitForPageToLoad();
		SeleniumUtil.click(seriesTxn.projectedStranctionList()
				.get(Integer.parseInt(PropsUtil.getDataPropertyValue("Txn_SBLOC_MTAddedTxnIndex"))));
		seriesTxn.editOneTimeProjectedTxn(PropsUtil.getDataPropertyValue("Txn_SBLOC_MRTInstanceUpdateAmount"),
				PropsUtil.getDataPropertyValue("Txn_SBLOC_MRTInstanceUpdateDescription"),
				PropsUtil.getDataPropertyValue("Txn_SBLOC_MRTInstanceUpdateNote"));
		SeleniumUtil.click(seriesTxn.projectedStranctionList()
				.get(Integer.parseInt(PropsUtil.getDataPropertyValue("Txn_SBLOC_MTAddedTxnIndex"))));
		SeleniumUtil.waitForPageToLoad();
		SeleniumUtil.click(seriesTxn.shoeMore());
		SeleniumUtil.waitForPageToLoad();
		Assert.assertEquals(seriesTxn.amountField().getAttribute("value").trim(),
				PropsUtil.getDataPropertyValue("Txn_SBLOC_MRTInstanceDetailsUpdateAmount"),
				"txn amount is not displayed");
		Assert.assertEquals(seriesTxn.DescriptionField().getAttribute("value").trim(),
				PropsUtil.getDataPropertyValue("Txn_SBLOC_MRTInstanceUpdateDescription"),
				"txn description is not displayed");
		Assert.assertEquals(seriesTxn.noteField().getAttribute("value").trim(),
				PropsUtil.getDataPropertyValue("Txn_SBLOC_MRTInstanceUpdateNote"), "txn note is not displayed");
	}

	@Test(description = "AT-96974,AT-96979:verify SB LOC account projected transaction edit", priority = 45, dependsOnMethods = "verifySBLOCAccountProjTxn")
	public void verifySBLOCAccountProjTxn2() {
		seriesTxn.editSeriesTransaction(Integer.parseInt(PropsUtil.getDataPropertyValue("Txn_SBLOC_MTAddedTxnIndex")),
				PropsUtil.getDataPropertyValue("Txn_SBLOC_MRTSeriesUpdateAmount"),
				PropsUtil.getDataPropertyValue("Txn_SBLOC_MRTSeriesUpdateNote"),
				PropsUtil.getDataPropertyValue("Txn_SBLOC_MRTSeriesUpdateDescription"));
		layout.serachTransaction(
				add_manual_transaction.targateDate1(
						Integer.parseInt(PropsUtil.getDataPropertyValue("Txn_SBLOC_projectedScheduledDate"))),
				add_manual_transaction.targateDate1(
						Integer.parseInt(PropsUtil.getDataPropertyValue("Txn_SBLOC_projectedScheduledDate"))));
		SeleniumUtil.waitForPageToLoad();
		SeleniumUtil.click(seriesTxn.projectedStranctionList()
				.get(Integer.parseInt(PropsUtil.getDataPropertyValue("Txn_SBLOC_MTAddedTxnIndex"))));
		SeleniumUtil.waitForPageToLoad();

		Assert.assertEquals(seriesTxn.amountField().getAttribute("value").trim(),
				PropsUtil.getDataPropertyValue("Txn_SBLOC_MRTSeriesDetailsUpdateAmount"),
				"txn amount is not dispalyed");
		Assert.assertEquals(seriesTxn.DescriptionField().getAttribute("value").trim(),
				PropsUtil.getDataPropertyValue("Txn_SBLOC_MRTSeriesUpdateDescription"),
				"txn description is not dispalyed");

	}

	@Test(description = "AT-96992,AT-96993:verify all LOC account type categorization integration", priority = 49, dependsOnMethods = "verifyAccountAdded")
	public void verifyLOCAccountUnCatTxn_CatRule() {
		PageParser.forceNavigate("Transaction", d);
		SeleniumUtil.waitForPageToLoad();
		add_manual_transaction.clickMTLink();
		SeleniumUtil.waitForPageToLoad(4000);
		add_manual_transaction.createTransaction(PropsUtil.getDataPropertyValue("Txn_Categorization_LOC_MTAmount"),
				PropsUtil.getDataPropertyValue("Txn_Categorization_LOC_MTDescription"),
				PropsUtil.getDataPropertyValue("Txn_Categorization_LOC_MTAccount"),
				Integer.parseInt(PropsUtil.getDataPropertyValue("Txn_Categorization_LOC_ScheduledDate")),
				PropsUtil.getDataPropertyValue("Txn_Categorization_LOC_MTCategory"),
				PropsUtil.getDataPropertyValue("Txn_Categorization_LOC_Frequency"));
		SeleniumUtil.waitForPageToLoad(1000);
		add_manual_transaction.clickMTLink();
		SeleniumUtil.waitForPageToLoad(4000);
		add_manual_transaction.createTransaction(PropsUtil.getDataPropertyValue("Txn_Categorization_HELOC_MTAmount"),
				PropsUtil.getDataPropertyValue("Txn_Categorization_HELOC_MTDescription"),
				PropsUtil.getDataPropertyValue("Txn_Categorization_HELOC_MTAccount"),
				Integer.parseInt(PropsUtil.getDataPropertyValue("Txn_Categorization_HELOC_ScheduledDate")),
				PropsUtil.getDataPropertyValue("Txn_Categorization_HELOC_MTCategory"),
				PropsUtil.getDataPropertyValue("Txn_Categorization_HELOC_Frequency"));
		SeleniumUtil.waitForPageToLoad(1000);
		add_manual_transaction.clickMTLink();
		SeleniumUtil.waitForPageToLoad(4000);
		add_manual_transaction.createTransaction(PropsUtil.getDataPropertyValue("Txn_Categorization_SBLOC_MTAmount"),
				PropsUtil.getDataPropertyValue("Txn_Categorization_SBLOC_MTDescription"),
				PropsUtil.getDataPropertyValue("Txn_Categorization_SBLOC_MTAccount"),
				Integer.parseInt(PropsUtil.getDataPropertyValue("Txn_Categorization_SBLOC_ScheduledDate")),
				PropsUtil.getDataPropertyValue("Txn_Categorization_SBLOC_MTCategory"),
				PropsUtil.getDataPropertyValue("Txn_Categorization_SBLOC_Frequency"));
		SeleniumUtil.waitForPageToLoad(1000);

		PageParser.forceNavigate("CategorzationRules", d);
		rule.clickUncatTxn();
		SeleniumUtil.waitForPageToLoad();
		int accountLOC = 0;
		int accountHE = 0;
		int accountSB = 0;
		int txnsize = accountFilter.getAllPostedAccount_AMT1().size();
		for (int i = 0; i < txnsize; i++) {
			if (accountFilter.getAllPostedAccount_AMT1().get(i).getText().trim().replaceAll("\n", " ")
					.equals(PropsUtil.getDataPropertyValue("Txn_LOc_txnAccountName"))) {
				accountLOC = accountLOC + 1;
			}

			if (accountFilter.getAllPostedAccount_AMT1().get(i).getText().trim().replaceAll("\n", " ")
					.equals(PropsUtil.getDataPropertyValue("Txn_HELOc_txnAccountName"))) {
				accountHE = accountHE + 1;
			}

			if (accountFilter.getAllPostedAccount_AMT1().get(i).getText().trim().replaceAll("\n", " ")
					.equals(PropsUtil.getDataPropertyValue("Txn_SBLOc_txnAccountName"))) {
				accountSB = accountSB + 1;
			}

		}

		Assert.assertTrue(accountLOC >= Integer.parseInt(PropsUtil.getDataPropertyValue("Txn_CF_LOCAccountTxnCount")),
				"loc account txn is not displayed");
		Assert.assertTrue(accountHE >= Integer.parseInt(PropsUtil.getDataPropertyValue("Txn_CF_LOCAccountTxnCount")),
				"HEloc account txn not displayed");
		Assert.assertTrue(accountSB >= Integer.parseInt(PropsUtil.getDataPropertyValue("Txn_CF_LOCAccountTxnCount")),
				"SBloc account txn not displayed");

	}

	// new User required container name refund7.Loans2
	@Test(description = "AT-96959,AT-96962,AT-96963:verify loc account name in dash board", priority = 50)
	public void verifyLOCAccountName_DBWidget() throws Exception {// PFM1545404278271
																	// AT-96959 Verify that once user add the LOC
																	// accounts the recent 5 transaction are reflected
																	// in the Transaction FinApp on Dashboard page
																	// AT-96962 Verify that Account name must be show
																	// for all the LOC account Transaction in the
																	// Transaction FinApp under Dashboard page
																	// AT-96963 Verify that For All the LOC account
																	// Transactions user is shown with Account number in
																	// Masked format next to the Account name in
																	// Transaction FinApp under Dashboard page
		LoginPage.loginMain(d, loginParameter);
		addAccount.linkAccount();
		/*
		 * addAccount.addAggregatedAccount1(PropsUtil.getDataPropertyValue(
		 * "Txn_DashBoard_LOC_UserName"),
		 * PropsUtil.getDataPropertyValue("TxnDashBoard_LOC_Password"));
		 */addAccount.addContainerAccount("DagLoan", PropsUtil.getDataPropertyValue("Txn_DashBoard_LOC_UserName"),
				PropsUtil.getDataPropertyValue("TxnDashBoard_LOC_Password"));

		d.navigate().refresh();
		SeleniumUtil.waitForPageToLoad(8000);
		int accountLOC = 0;
		int accountHE = 0;
		int accountSB = 0;
		int accountsize = txnDashBoard.dashBoardTxnsAccountNum().size();
		for (int i = 0; i < accountsize; i++) {

			if (txnDashBoard.dashBoardTxnsAccountNum().get(i).getText().trim().replaceAll("\n", " ")
					.equals(PropsUtil.getDataPropertyValue("TxnDashBoard_LOC_AccountName"))) {
				accountLOC = accountLOC + 1;
			}

			if (txnDashBoard.dashBoardTxnsAccountNum().get(i).getText().trim().replaceAll("\n", " ")
					.equals(PropsUtil.getDataPropertyValue("TxnDashBoard_HELOC_AccountName"))) {
				accountHE = accountHE + 1;
			}

			if (txnDashBoard.dashBoardTxnsAccountNum().get(i).getText().trim().replaceAll("\n", " ")
					.equals(PropsUtil.getDataPropertyValue("TxnDashBoard_SBLOC_AccountName"))) {
				accountSB = accountSB + 1;
			}

		}
		Assert.assertTrue(
				txnDashBoard.dashBoardTxnsAccountNum().size() == Integer
						.parseInt(PropsUtil.getDataPropertyValue("TxnDashBoard_LOC_AccountTotalTxnAccount")),
				"recent 5 txn is not dispalyed");
		Assert.assertTrue(
				accountLOC >= Integer.parseInt(PropsUtil.getDataPropertyValue("TxnDashBoard_LOC_AccountTxnAccount")),
				"loc account txn account name not displayed");
		Assert.assertTrue(
				accountHE >= Integer.parseInt(PropsUtil.getDataPropertyValue("TxnDashBoard_LOC_AccountTxnAccount")),
				"HEloc account txn account name not displayed");
		Assert.assertTrue(
				accountSB >= Integer.parseInt(PropsUtil.getDataPropertyValue("TxnDashBoard_LOC_AccountTxnAccount")),
				"SBloc account txn account name not displayed");
	}

	@Test(description = "AT-96960:verify loc account type transaction description in dashboard", priority = 51)
	public void verifyLOCAccountDescription_DBWidget() {// AT-96960 Verify in Transaction FinApp on Dashboard page,
														// Transaction description must be shown for all the LOC account
														// transaction recent transactions
		Assert.assertTrue(
				txnDashBoard.dashBoardTxnsAccountDescription().size() == Integer
						.parseInt(PropsUtil.getDataPropertyValue("TxnDashBoard_LOC_AccountTotalTxnAccount")),
				"all account description is not displayed");

	}

	@Test(description = "AT-96964:verify LOc account type transaction balance", priority = 52, dependsOnMethods = "verifyLOCAccountName_DBWidget")
	public void verifyLOCAccountBal_DBWidget() {
		// AT-96964 Verify that for all the LOC account Transactions user is shown with
		// Account balance for which is aligned to the Transaction description
		Assert.assertTrue(
				txnDashBoard.dashBoardTxnsAccountBal().size() == Integer
						.parseInt(PropsUtil.getDataPropertyValue("TxnDashBoard_LOC_AccountTotalTxnAccount")),
				"all account txn amount is not displayed");

	}

	@Test(description = "AT-96961:Verify Loc account type transaction date header", priority = 53, dependsOnMethods = "verifyLOCAccountName_DBWidget")
	public void verifyLOCAccountTxnDateHeader_DBWidget() throws ParseException {// AT-96961 Verify that in Transaction
																				// FinApp on Dashboard page, it must be
																				// sorted by date and latest date must
																				// be shown at the top.
		Assert.assertTrue(search.verifyallPostedTransaction(txnDashBoard.recentTransactionHeaderList()),
				"transaction date header not displayed");

	}

	@Test(description = "AT-96965:verify LOc account type Transaction widget", priority = 54, dependsOnMethods = "verifyLOCAccountName_DBWidget")
	public void verifyLOCAccountTxnWidgetClick_DBWidget() throws ParseException {
		// Verify that when user click on the specific Transaction in the Transaction
		// FinApp under Dashboard page user must be taken to Transaction page showing
		// all the Transaction available
		txnDashBoard.clickTxnWidget();
		SeleniumUtil.waitForPageToLoad();
		Assert.assertEquals(txnDashBoard.TransactionFinappHeader().getText().trim(),
				PropsUtil.getDataPropertyValue("TransactionHeader"), "not navigated to txn");

	}

	@Test(description = "AT-96966:verify Loc account type click in dash board", priority = 55, dependsOnMethods = "verifyLOCAccountName_DBWidget")
	public void verifyLOCAccountClick_DBWidget() throws ParseException {
		PageParser.forceNavigate("DashboardPage", d);
		SeleniumUtil.waitForPageToLoad();
		txnDashBoard.clickAccountRow(PropsUtil.getDataPropertyValue("TxnDashBoard_LOC_AccountRow"));
		Assert.assertEquals(txnDashBoard.TransactionFinappAccountDetailsHeaderName().getText().trim(),
				PropsUtil.getDataPropertyValue("TxnDashBoard_LOC_AccountRow"), "Loc account name is not displayed");
		Assert.assertEquals(accountFilter.accountFilterLink().getText().trim(),
				PropsUtil.getDataPropertyValue("Txn_LOc_AccountFilterName"), "Loc account is not selected");

	}

	@Test(description = "AT-96966:verify HELoc account type click in dash board", priority = 56, dependsOnMethods = "verifyLOCAccountName_DBWidget")
	public void verifyHELOCAccountClick_DBWidget() throws ParseException {
		PageParser.forceNavigate("DashboardPage", d);
		SeleniumUtil.waitForPageToLoad();
		txnDashBoard.clickAccountRow(PropsUtil.getDataPropertyValue("TxnDashBoard_HELOC_AccountRow"));
		SeleniumUtil.waitForPageToLoad();
		Assert.assertEquals(txnDashBoard.TransactionFinappAccountDetailsHeaderName().getText().trim(),
				PropsUtil.getDataPropertyValue("TxnDashBoard_HELOC_AccountRow"), "HELoc account name is not displayed");
		Assert.assertEquals(accountFilter.accountFilterLink().getText().trim(),
				PropsUtil.getDataPropertyValue("Txn_HELOc_AccountFilterName"), "Loc account is not selected");

	}

	@Test(description = "AT-96966:verify SBLoc account type click in dash board", priority = 57, dependsOnMethods = "verifyLOCAccountName_DBWidget")
	public void verifySBLOCAccountClick_DBWidget() throws ParseException {
		PageParser.forceNavigate("DashboardPage", d);
		SeleniumUtil.waitForPageToLoad();
		txnDashBoard.clickAccountRow(PropsUtil.getDataPropertyValue("TxnDashBoard_SBLOC_AccountRow"));
		SeleniumUtil.waitForPageToLoad();
		Assert.assertEquals(txnDashBoard.TransactionFinappAccountDetailsHeaderName().getText().trim(),
				PropsUtil.getDataPropertyValue("TxnDashBoard_SBLOC_AccountRow"), "SBLoc account name is not displayed");
		if (PropsUtil.getEnvPropertyValue("cnf_SDG").equalsIgnoreCase("OFF")) {
			Assert.assertEquals(accountFilter.accountFilterLink().getText().trim(),
					PropsUtil.getDataPropertyValue("Txn_SBLOc_AccountFilterNameSDGOFF"), "Loc account is not selected");

		} else {
			Assert.assertEquals(accountFilter.accountFilterLink().getText().trim(),
					PropsUtil.getDataPropertyValue("Txn_SBLOc_AccountFilterName"), "Loc account is not selected");
		}
	}

	@Test(description = "AT-96986:verify HELoc account type click in Account", priority = 58, dependsOnMethods = "verifyLOCAccountName_DBWidget")
	public void verifyHELOCAccountClick_Account() throws ParseException {
		PageParser.forceNavigate("AccountsPage", d);
		SeleniumUtil.waitForPageToLoad();
		txnAccountIntg.clickAccountRow(PropsUtil.getDataPropertyValue("TxnDashBoard_HELOC_AccountRow"));
		SeleniumUtil.waitForPageToLoad();
		Assert.assertEquals(txnDashBoard.TransactionFinappAccountDetailsHeaderName().getText().trim(),
				PropsUtil.getDataPropertyValue("TxnDashBoard_HELOC_AccountRow"), "HELoc account name is not displayed");
		Assert.assertEquals(accountFilter.accountFilterLink().getText().trim(),
				PropsUtil.getDataPropertyValue("Txn_HELOc_AccountFilterName"), "Loc account is not selected");

	}

	@Test(description = "AT-96986:verify SBLoc account type click in Account", priority = 59, dependsOnMethods = "verifyLOCAccountName_DBWidget")
	public void verifySBLOCAccountClick_Account() throws ParseException {
		PageParser.forceNavigate("AccountsPage", d);
		SeleniumUtil.waitForPageToLoad();
		txnAccountIntg.clickAccountRow(PropsUtil.getDataPropertyValue("TxnDashBoard_SBLOC_AccountRow"));
		Assert.assertEquals(txnDashBoard.TransactionFinappAccountDetailsHeaderName().getText().trim(),
				PropsUtil.getDataPropertyValue("TxnDashBoard_SBLOC_AccountRow"), "SBLoc account name is not displayed");
		if (PropsUtil.getEnvPropertyValue("cnf_SDG").equalsIgnoreCase("OFF")) {
			Assert.assertEquals(accountFilter.accountFilterLink().getText().trim(),
					PropsUtil.getDataPropertyValue("Txn_SBLOc_AccountFilterNameSDGOFF"), "Loc account is not selected");

		} else {

			Assert.assertEquals(accountFilter.accountFilterLink().getText().trim(),
					PropsUtil.getDataPropertyValue("Txn_SBLOc_AccountFilterName"), "Loc account is not selected");
		}
	}

}
