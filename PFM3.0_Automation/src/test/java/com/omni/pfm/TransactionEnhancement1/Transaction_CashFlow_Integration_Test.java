/**
* Copyright (c) 2018 Yodlee Inc. All Rights Reserved.
*
* This software is the confidential and proprietary information of
* Yodlee, Inc. Use is subject to license terms.
*
* @author Renuka K
*/
package com.omni.pfm.TransactionEnhancement1;

import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import com.omni.pfm.PageProcessor.PageParser;
import com.omni.pfm.pages.AccountAddition.AccountAddition;
import com.omni.pfm.pages.Login.LoginPage;
import com.omni.pfm.pages.TransactionEnhancement1.AddToCalendar_Transaction_Loc;
import com.omni.pfm.pages.TransactionEnhancement1.Add_Manual_Transaction_Loc;
import com.omni.pfm.pages.TransactionEnhancement1.Transaction_AccountDropDown_Loc;
import com.omni.pfm.pages.TransactionEnhancement1.Transaction_AccountSharing_ReadOnlyFeature_loc;
import com.omni.pfm.pages.TransactionEnhancement1.Transaction_Account_Integration_Loc;
import com.omni.pfm.pages.TransactionEnhancement1.Transaction_Cashflow_Integration_Loc;
import com.omni.pfm.pages.TransactionEnhancement1.Transaction_Details_Loc;
import com.omni.pfm.pages.TransactionEnhancement1.Transaction_Filter_Loc;
import com.omni.pfm.pages.TransactionEnhancement1.Transaction_Layout_Loc;
import com.omni.pfm.pages.TransactionEnhancement1.Transaction_Search_Loc;
import com.omni.pfm.pages.TransactionEnhancement1.Transaction_Tag_Loc;
import com.omni.pfm.testBase.TestBase;
import com.omni.pfm.utility.PropsUtil;
import com.omni.pfm.utility.SeleniumUtil;

public class Transaction_CashFlow_Integration_Test extends TestBase {
	Transaction_Details_Loc details;
	AddToCalendar_Transaction_Loc AddToCalendar;
	Transaction_Account_Integration_Loc acct_Tran;
	AccountAddition accountAdd;
	Add_Manual_Transaction_Loc addManual;
	WebDriver webDriver = null;
	private static final Logger logger = LoggerFactory.getLogger(Transaction_CashFlow_Integration_Test.class);
	public static String userName = "";
	Transaction_AccountDropDown_Loc accountDropDown;
	Transaction_Search_Loc search;
	Transaction_Tag_Loc tag;
	Transaction_Cashflow_Integration_Loc cashFlow;
	Transaction_AccountSharing_ReadOnlyFeature_loc readOnly;
	Transaction_Filter_Loc filter;
	Transaction_Layout_Loc layout;

	@BeforeClass(alwaysRun = true)
	public void testInit() throws Exception {
		doInitialization("Transaction Tag");
		TestBase.tc = TestBase.extent.startTest("Trans tags", "Login");
		TestBase.test.appendChild(TestBase.tc);
		AddToCalendar = new AddToCalendar_Transaction_Loc(d);
		acct_Tran = new Transaction_Account_Integration_Loc(d);
		accountAdd = new AccountAddition();
		addManual = new Add_Manual_Transaction_Loc(d);
		accountDropDown = new Transaction_AccountDropDown_Loc(d);
		search = new Transaction_Search_Loc(d);
		tag = new Transaction_Tag_Loc(d);
		cashFlow = new Transaction_Cashflow_Integration_Loc(d);
		readOnly = new Transaction_AccountSharing_ReadOnlyFeature_loc(d);
		filter = new Transaction_Filter_Loc(d);
		details = new Transaction_Details_Loc(d);
		layout = new Transaction_Layout_Loc(d);
		LoginPage.loginMain(d, loginParameter);
		accountAdd.linkAccount();
		accountAdd.addAggregatedAccount("cashFlowInteg.site16441.1", "site16441.1");
	}

	@Test(description = "AT-68839:verify cash in flow amount", priority = 1)
	public void verifyDepositType() throws Exception {
		PageParser.forceNavigate("CashFlow", d);
		cashFlow.cashFlowFTUE();
		double cashInflow = Double.parseDouble(cashFlow.getcshaflowAmoount(1, 2).getText().replaceAll("[$\\,]", ""));
		// SeleniumUtil.click(cashFlow.getcshaflowAmoount(1, 2));
		cashFlow.clickCashFlowAmount(1, 2);
		int transactionList = accountDropDown.allTransactionAmount().size();
		double acount = 0;
		for (int i = 0; i < transactionList; i++) {
			acount = acount + Double
					.parseDouble(accountDropDown.allTransactionAmount().get(i).getText().replaceAll("[$\\,]", ""));
		}
		Assert.assertEquals(cashInflow, acount);
	}

	@Test(description = "AT-68839:verify deposit type of transaction", priority = 2, dependsOnMethods = "verifyDepositType")
	public void verifyOnlyDepositTransaction() throws Exception {
		boolean allAccountType = false;
		int allTransactionSize = accountDropDown.allTransactionDescription().size();
		for (int i = 0; i < allTransactionSize; i++) {
			SeleniumUtil.click(accountDropDown.allTransactionDescription().get(i));
			if (readOnly.transactionDetailsReadonlyAccountLabel().getText().trim()
					.equals(PropsUtil.getDataPropertyValue("TransactionTypeCreditedTo"))) {
				allAccountType = true;
			} else {
				allAccountType = false;
				break;
			}
		}
		Assert.assertTrue(allAccountType,
				"debited from  transaction type should displayed when select all Transaction type");
	}

	@Test(description = "AT-68846:verify projected transaction should not be dsiplayed", priority = 4, dependsOnMethods = "verifyDepositType")
	public void verifyPostedTXn() throws Exception {
		Assert.assertTrue(search.verifyallPostedTransaction());
		Assert.assertTrue(!SeleniumUtil.isDisplayed(layout.projectedHeader, 2));
	}

	@Test(description = "AT-68836:verify the transaction type filter should not be displayed", priority = 5, dependsOnMethods = "verifyDepositType")
	public void verifyTransactionType() throws Exception {
		boolean transactionType = false;
		if (filter.transactionTypeAllList().size() == 0 && filter.transactionTypeDepositList().size() == 0
				&& filter.transactionTypeWithDrawalList().size() == 0) {
			transactionType = true;
		}
		Assert.assertTrue(transactionType, "Transaction type should not displayed");
	}

	@Test(description = "AT-68840:verify back to cashflow link", priority = 6, dependsOnMethods = "verifyDepositType")
	public void verifyBackToCashFlowLink() throws Exception {
		Assert.assertEquals(cashFlow.backTocashFlow_TCI().getText(), PropsUtil.getDataPropertyValue("CashFlowBack"),
				"back to cashFlow link should be displayed");
	}

	@Test(description = "AT-68849,AT-68850:verify time filter values", priority = 7, dependsOnMethods = "verifyDepositType")
	public void verifyTimeFilteTab() throws Exception {
		Assert.assertEquals(accountDropDown.TransactionTimeFilterLabel().getText(),
				PropsUtil.getDataPropertyValue("TransactionCustomeDatesLabel"),
				"this months label is not displaying in time filter Tab");
		Assert.assertEquals(accountDropDown.TransactionTimeFilterDate().getText().trim(),
				"(" + accountDropDown.monthStartDate(0, "MM/dd/yyy") + " " + "-" + " "
						+ accountDropDown.monthEndDateDate(0, "MM/dd/yyy") + ")",
				"This months date is not displaying in time filter Tab");
	}

	@Test(description = "AT-68841:navigating to cashflow when clikc on back to cashflow", priority = 8, dependsOnMethods = "verifyDepositType")
	public void verifyBackToCashFlow() throws Exception {
		cashFlow.clickBackTocashFlow();
		Assert.assertEquals(details.transactionHeader().getText().trim(),
				PropsUtil.getDataPropertyValue("TransactionCashFlowHeader"), "cashFlow header should be Transactions");
	}

	@Test(description = "AT-68843:cash out flow amount", priority = 9, dependsOnMethods = "verifyBackToCashFlow")
	public void verifyWithDrawalType() throws Exception {
		double cashInflow = Double.parseDouble(cashFlow.getcshaflowAmoount(1, 3).getText().replaceAll("[$\\,]", ""));
		cashFlow.clickCashFlowAmount(1, 3);
		int transactionList = accountDropDown.allTransactionAmount().size();
		double acount = 0;
		for (int i = 0; i < transactionList; i++) {
			acount = acount + Double
					.parseDouble(accountDropDown.allTransactionAmount().get(i).getText().replaceAll("[$\\,]", ""));
		}
		Assert.assertEquals(cashInflow, acount);
	}

	@Test(description = "AT-68843:verofy withdrawal type transaction", priority = 10, dependsOnMethods = "verifyWithDrawalType")
	public void verifyOnlyWithDrawalsTransaction() throws Exception {
		boolean allAccountType = false;
		int allTransactionSize = accountDropDown.allTransactionDescription().size();
		for (int i = 0; i < allTransactionSize; i++) {
			SeleniumUtil.click(accountDropDown.allTransactionDescription().get(i));
			if (readOnly.transactionDetailsReadonlyAccountLabel().getText().trim()
					.equals(PropsUtil.getDataPropertyValue("TransactionTypeDebitedFrom"))) {
				allAccountType = true;
			} else {
				allAccountType = false;
				break;
			}
		}
		Assert.assertTrue(allAccountType,
				"debited from  transaction type should displayed when select all Transaction type");
	}

	@Test(description = "AT-68846:verify pending transaction should be displayed", priority = 11, dependsOnMethods = "verifyWithDrawalType")
	public void verifyPendingTxn() throws Exception {
		Assert.assertTrue(!SeleniumUtil.isDisplayed(layout.pendingTransaction, 2));
	}

	@Test(description = "AT-68845:verify transfer type amount", priority = 12, dependsOnMethods = "verifyWithDrawalType")
	public void verifyTransaferType() throws Exception {
		cashFlow.clickBackTocashFlow();
		double cashInflow = Double.parseDouble(cashFlow.getcshaflowAmoount(1, 4).getText().replaceAll("[$\\,\\-]", ""));
		cashFlow.clickCashFlowAmount(1, 4);
		int transactionList = accountDropDown.allTransactionAmount().size();
		double acount = 0;
		for (int i = 0; i < transactionList; i++) {
			acount = acount + Double
					.parseDouble(accountDropDown.allTransactionAmount().get(i).getText().replaceAll("[$\\,]", ""));
		}
		Assert.assertEquals(cashInflow, acount);
	}

	@Test(description = "AT-68847,AT-68902:cash in flow when added deposit type transaction", priority = 13, dependsOnMethods = "verifyTransaferType")
	public void verifyAddedDepositType() throws Exception {
		addManual.clickMTLink();
		addManual.createOneTimeTransaction(PropsUtil.getDataPropertyValue("TransactionCashFlowTransactionAmount"),
				PropsUtil.getDataPropertyValue("TransactionCashFlowTransactionDescription"),
				PropsUtil.getDataPropertyValue("TransactionCashFlowAddedDepositTxnType"),
				PropsUtil.getDataPropertyValue("TransactionCashFlowAddedDepositAccount"),
				PropsUtil.getDataPropertyValue("TransactionCashFlowAddedDepositFreq"), 0,
				PropsUtil.getDataPropertyValue("TransactionCashFlowAddedDepositCategory"),
				PropsUtil.getDataPropertyValue("TransactionCashFlowTransactionNote"),
				PropsUtil.getDataPropertyValue("TransactionCashFlowTransactionCheck"));
		addManual.clickMTLink();
		addManual.createOneTimeTransaction(PropsUtil.getDataPropertyValue("TransactionCashFlowTransactionAmount"),
				PropsUtil.getDataPropertyValue("TransactionCashFlowTransactionDescription"),
				PropsUtil.getDataPropertyValue("TransactionCashFlowAddedWithDwalTxnType"),
				PropsUtil.getDataPropertyValue("TransactionCashFlowAddedWithDwalAccount"),
				PropsUtil.getDataPropertyValue("TransactionCashFlowAddedWithDwalFreq"), 0,
				PropsUtil.getDataPropertyValue("TransactionCashFlowAddedWithDwalCategory"),
				PropsUtil.getDataPropertyValue("TransactionCashFlowTransactionNote"),
				PropsUtil.getDataPropertyValue("TransactionCashFlowTransactionCheck"));
		addManual.clickMTLink();
		addManual.createOneTimeTransaction(PropsUtil.getDataPropertyValue("TransactionCashFlowTransactionAmount"),
				PropsUtil.getDataPropertyValue("TransactionCashFlowTransactionDescription"),
				PropsUtil.getDataPropertyValue("TransactionCashFlowAddedTransferTxnType"),
				PropsUtil.getDataPropertyValue("TransactionCashFlowAddedTransferAccount"),
				PropsUtil.getDataPropertyValue("TransactionCashFlowAddedTransferFreq"), 0,
				PropsUtil.getDataPropertyValue("TransactionCashFlowAddedTransferTxnCategory"),
				PropsUtil.getDataPropertyValue("TransactionCashFlowTransactionNote"),
				PropsUtil.getDataPropertyValue("TransactionCashFlowTransactionCheck"));
		cashFlow.clickBackTocashFlow();
		Assert.assertEquals(cashFlow.getcshaflowAmoount(1, 2).getText(),
				PropsUtil.getDataPropertyValue("TransactionCashFlowAddedDepositAmount"),
				"depostit type transaction amount added to cashin flow");
	}

	@Test(description = "AT-68847,AT-68902:verify cashinflow when withdrawal type transaction added", priority = 14, dependsOnMethods = "verifyAddedDepositType")
	public void verifyAddedwithDrawalType() throws Exception {
		Assert.assertEquals(cashFlow.getcshaflowAmoount(1, 3).getText(),
				PropsUtil.getDataPropertyValue("TransactionCashFlowAddedWithDwalAmount"),
				"withdrawal ty transaction amount should added to cash out flow");
	}

	@Test(description = "AT-68848,AT-68902:verify cash transfer when Transfer type Transaction added ", priority = 15, dependsOnMethods = "verifyAddedDepositType")
	public void verifyAddedTransferType() throws Exception {
		Assert.assertEquals(cashFlow.getcshaflowAmoount(1, 4).getText(),
				PropsUtil.getDataPropertyValue("TransactionCashFlowAddedTransferAmount"),
				"transfer type of transaction amount should added to net transfer");
	}
}
