/**
* Copyright (c) 2018 Yodlee Inc. All Rights Reserved.
*
* This software is the confidential and proprietary information of
* Yodlee, Inc. Use is subject to license terms.
*
* @author Renuka K
*/
package com.omni.pfm.TransactionEnhancement1;

import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.omni.pfm.PageProcessor.PageParser;
import com.omni.pfm.pages.AccountAddition.AccountAddition;
import com.omni.pfm.pages.TransactionEnhancement1.Transaction_AccountDropDown_Loc;
import com.omni.pfm.pages.TransactionEnhancement1.Transaction_AccountSharing_ReadOnlyFeature_loc;
import com.omni.pfm.pages.TransactionEnhancement1.Transaction_Filter_Loc;
import com.omni.pfm.pages.TransactionEnhancement1.Transaction_Search_Loc;
import com.omni.pfm.pages.TransactionEnhancement1.Transaction_Tag_Loc;
import com.omni.pfm.testBase.TestBase;
import com.omni.pfm.utility.PropsUtil;
import com.omni.pfm.utility.SeleniumUtil;

public class Transaction_Account_Filter_Test extends TestBase {
	private static final Logger logger = LoggerFactory.getLogger(Transaction_Account_Filter_Test.class);

	Transaction_Filter_Loc filter;
	AccountAddition accountAdd;
	Transaction_AccountDropDown_Loc accountDropDown;
	Transaction_Search_Loc search;
	Transaction_AccountSharing_ReadOnlyFeature_loc readOnly;
	Transaction_Tag_Loc tag;
	SeleniumUtil util;

	@BeforeClass(alwaysRun = true)
	public void testInit() throws InterruptedException {
		doInitialization("Transaction Tag");
		TestBase.tc = TestBase.extent.startTest("Trans tags", "Login");
		TestBase.test.appendChild(TestBase.tc);
		accountAdd = new AccountAddition();
		filter = new Transaction_Filter_Loc(d);
		accountDropDown = new Transaction_AccountDropDown_Loc(d);
		search = new Transaction_Search_Loc(d);
		readOnly = new Transaction_AccountSharing_ReadOnlyFeature_loc(d);
		tag = new Transaction_Tag_Loc(d);
		util = new SeleniumUtil();
		d.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
		PageParser.forceNavigate("Transaction", d);
	}

	@Test(description = "AT-68623:verify account filter tab", priority = 1)
	public void verifyAccontFilterLabel() {
		Assert.assertEquals(accountDropDown.accountFilterLink().getText().trim(),
				PropsUtil.getDataPropertyValue("allAccountFilterLable"));
	}

	@Test(description = "AT-68624,AT-68628,AT-68618:verify account filter popup", priority = 2)
	public void verifyAccontFilterPopUp() {
		accountDropDown.clickAccountFilter();
		SeleniumUtil.waitForPageToLoad(1000);
		Assert.assertTrue(
				filter.transactionFilterPopUpOpen().getAttribute("class")
						.contains(PropsUtil.getDataPropertyValue("TransactionFilterPopUpOpen")),
				"class staus should change to open when clikc on account filter");
		Assert.assertEquals(filter.transactionFilterPopUpHeader1().getText().trim(),
				PropsUtil.getDataPropertyValue("TransactionAccountFilterHeader"),
				"verify accounts filter header should displayed");
		Assert.assertTrue(filter.transactionFilterGroupPopUpClose().isDisplayed());
	}

	@Test(description = "AT-68629:verify all accounts check box is selected", priority = 3, dependsOnMethods = "verifyAccontFilterPopUp")
	public void verifyAllAccountCheckBoxSelected() {

		Assert.assertEquals(accountDropDown.allAccountCheckBox().getAttribute("aria-checked"),
				PropsUtil.getDataPropertyValue("TransactionAccountFilterselectedCheckBox"),
				"by default all account check should be selected");
		Assert.assertEquals(accountDropDown.allAccountLable().get(0).getText().trim(),
				PropsUtil.getDataPropertyValue("TransactionAccountFilterAllAccountLabel"),
				"ALL ACCOUNTS text should be displayed");

	}

	@Test(description = "AT-68627:verify all accounts are selected", priority = 4, dependsOnMethods = "verifyAccontFilterPopUp")
	public void verifyAccountCheckBoxSelected() {
		boolean checkBoxselected = false;
		checkBoxselected = util.assertTrue(
				util.getWebElementsAttributeName(filter.transactionFilterAccontCheckBox(), "aria-checked"),
				PropsUtil.getDataPropertyValue("TransactionAccountFilterselectedCheckBox"));
		Assert.assertTrue(checkBoxselected);
	}

	@Test(description = "AT-68625:verify all accounts name", priority = 5, dependsOnMethods = "verifyAccontFilterPopUp")
	public void verifyAccountName() {
		util.assertExpectedActualList(util.getWebElementsValue(filter.transactionFilterAccontName()),
				PropsUtil.getDataPropertyValue("TransactionAccountFilterAllAccountName"), "verify all acconts");
	}

	@Test(description = "AT-68630,AT-68230:verify all accounts check box unselected", priority = 6, dependsOnMethods = "verifyAccontFilterPopUp")
	public void verifyAccountCheckBoxUnSelected() {
		boolean checkBoxselected = false;
		accountDropDown.clickAllAccount();
		checkBoxselected = util.assertTrue(
				util.getWebElementsAttributeName(filter.transactionFilterAccontCheckBox(), "aria-checked"),
				PropsUtil.getDataPropertyValue("TransactionAccountFilterUnselectedCheckBox"));
		String noTransaction = accountDropDown.transactionNoDataMessage1().getText().trim();

		Assert.assertTrue(checkBoxselected);
		Assert.assertEquals(noTransaction, PropsUtil.getDataPropertyValue("TransactionNodataMessage"),
				"No Transaction is not displaying when unselect all account check box");

	}

	@Test(description = "AT-68630:verify all accounts are selected", priority = 7, dependsOnMethods = "verifyAccountCheckBoxUnSelected")
	public void verifyAccountCheckBoxUnSelectAndSelect() {

		boolean checkBoxselected = false;
		accountDropDown.clickAllAccount();
		checkBoxselected = util.assertTrue(
				util.getWebElementsAttributeName(filter.transactionFilterAccontCheckBox(), "aria-checked"),
				PropsUtil.getDataPropertyValue("TransactionAccountFilterselectedCheckBox"));
		Assert.assertTrue(checkBoxselected);
	}

	@Test(description = "AT-68626:verify group filter header", priority = 8)
	public void verifygroupFilterHeader() {
		PageParser.forceNavigate("AccountsPage", d);
		accountDropDown.creategroupToverifyTransaction(PropsUtil.getDataPropertyValue("TransactionGroup"));
		accountDropDown.creategroupToverifyTransaction1(PropsUtil.getDataPropertyValue("TransactionGroup1"));
		PageParser.forceNavigate("Transaction", d);
		accountDropDown.clickAccountFilter();
		Assert.assertEquals(accountDropDown.transactionAccountDropDownAccountGroupLink().get(1).getText().trim(),
				PropsUtil.getDataPropertyValue("TransactionGroupHeader"), "group should be displayed");
	}

	@Test(description = "AT-68626:verify all group", priority = 9, dependsOnMethods = "verifygroupFilterHeader")
	public void verifygroupDisplayingInGroupFilter() {
		SeleniumUtil.click(accountDropDown.transactionAccountDropDownAccountGroupLink().get(1));
		util.assertExpectedActualList(util.getWebElementsValue(accountDropDown.transactionAccountDropDownGroupLink()),
				PropsUtil.getDataPropertyValue("TransactionGroupList"), "verify all group");
	}

	@Test(description = "AT-68631,AT-68637:verify selected groups in accounts tab", priority = 10, dependsOnMethods = "verifygroupFilterHeader")
	public void verifySelectedGroupDisplayingAccountTab() {
		accountDropDown.clickGroup(PropsUtil.getDataPropertyValue("TransactionGroup"));
		Assert.assertEquals(accountDropDown.accountFilterLink().getText().trim(),
				PropsUtil.getDataPropertyValue("TransactionGroup"), "verify group is displaying account filter tab");
	}

	@Test(description = "AT-68631:verify selected group transaction", priority = 11, dependsOnMethods = "verifySelectedGroupDisplayingAccountTab")
	public void verifySelectedGroupTransactionsDisplaying() {
		if (accountDropDown.allTransactionAccount().get(0).getText().equals("")) {
			SeleniumUtil.click(search.ProjectedExapdIcon());
			SeleniumUtil.waitForPageToLoad(2000);
		}

		boolean advisorAggregatedAccountsgroupTransaction = false;
		logger.info("verifying advisor accounts groups transaction should displayed");
		for (int i = 0; i < accountDropDown.allTransactionAccount().size(); i++) {
			if (accountDropDown.allTransactionAccount().get(i).getText().trim().replaceAll("\n", " ")
					.equals(PropsUtil.getDataPropertyValue("TransactionGroupAccount1").trim())
					|| accountDropDown.allTransactionAccount().get(i).getText().trim().replaceAll("\n", " ")
							.equals(PropsUtil.getDataPropertyValue("TransactionGroupAccount2").trim())) {
				advisorAggregatedAccountsgroupTransaction = true;

			} else {
				advisorAggregatedAccountsgroupTransaction = false;
				break;
			}
		}
		Assert.assertTrue(advisorAggregatedAccountsgroupTransaction,
				"advisor shared full view details accounts groups transaction are not displaying");
	}

	@Test(description = "AT-68634:verifyaccounts filter after selecting group", priority = 12, dependsOnMethods = "verifySelectedGroupDisplayingAccountTab")
	public void verifyAccountCheckBoxAfterApplyGroupFilter() {
		accountDropDown.clickAccountFilter();

		SeleniumUtil.click(accountDropDown.transactionAccountDropDownAccountGroupLink().get(0));
		boolean checkBoxselected = false;
		int accountList = filter.transactionFilterAccontCheckBox().size();
		for (int i = 0; i < accountList; i++) {
			if (filter.transactionFilterAccontCheckBox().get(i).getAttribute("class")
					.contains(PropsUtil.getDataPropertyValue("TransactionGroupSelectTick"))) {
				checkBoxselected = false;
			} else {
				checkBoxselected = true;
				break;
			}
		}
		Assert.assertTrue(checkBoxselected);
	}

	@Test(description = "AT-68632,AT-68633:verify multiple group selected", priority = 13, dependsOnMethods = "verifyAccountCheckBoxAfterApplyGroupFilter")
	public void verifyOnlyOneGroupSelect() {
		SeleniumUtil.click(accountDropDown.transactionAccountDropDownAccountGroupLink().get(1));
		accountDropDown.clickGroup(PropsUtil.getDataPropertyValue("TransactionGroup1"));
		accountDropDown.clickAccountFilter();

		Assert.assertFalse(filter.transactionGroupFilterTickMark().get(1).getAttribute("class")
				.contains(PropsUtil.getDataPropertyValue("TransactionGroupSelectTick")));
		Assert.assertTrue(filter.transactionGroupFilterTickMark().get(0).getAttribute("class")
				.contains(PropsUtil.getDataPropertyValue("TransactionGroupSelectTick")));

	}

	@Test(description = "AT-68630,AT-68635:verify selected accounts in accounts tab", priority = 14)
	public void verifySelectedAccountTransactionsDisplaying() {
		PageParser.forceNavigate("Transaction", d);
		accountDropDown.clickAccountFilter();
		SeleniumUtil.click(accountDropDown.transactionAccountDropDownAccountGroupLink().get(0));
		accountDropDown.clickAccountCheckbox(4);
		filter.clickAccountPopUpClose();
		if (accountDropDown.allTransactionAccount().get(0).getText().equals("")) {
			SeleniumUtil.click(search.ProjectedExapdIcon());
		}
		logger.info("verifying advisor accountsand my accounts transaction should displayed");
		for (int i = 0; i < accountDropDown.allTransactionAccount().size(); i++) {
			logger.info(accountDropDown.allTransactionAccount().get(i).getText().trim().replaceAll("\n", " "));
			logger.info(PropsUtil.getDataPropertyValue("TransactionGroupAccount2").trim());
			util.assertActualList(util.getWebElementsValue(accountDropDown.allTransactionAccount()),
					PropsUtil.getDataPropertyValue("TransactionGroupAccount2").trim(),
					"selected Accounts Transaction should be displayed");
		}
		Assert.assertEquals(accountDropDown.accountFilterLink().getText().trim(),
				PropsUtil.getDataPropertyValue("TransactionSelectedAccountFilterValue"),
				"verify accounts are displaying accountsTab");
	}

	@Test(description = "AT-68618:verify reset buttons are displaying", priority = 15, dependsOnMethods = "verifySelectedAccountTransactionsDisplaying")
	public void verifyResetButtonDisplaying() {
		Assert.assertEquals(tag.reset().getText().trim(), PropsUtil.getDataPropertyValue("transactionResetText"),
				"after filter reset should displayed");

	}

	@Test(description = "AT-68630,AT-68636,AT-68680:verify mutiple accounts transactions displaying", priority = 16, dependsOnMethods = "verifySelectedAccountTransactionsDisplaying")
	public void verifySelectedAccountsTransactionsDisplaying() {
		accountDropDown.clickAccountFilter();
		accountDropDown.selectAccFilterOneAccount(5);

		filter.clickAccountPopUpClose();
		boolean accountsTransaction = false;
		logger.info("verifying advisor accountsand my accounts transaction should displayed");
		for (int i = 0; i < accountDropDown.allTransactionAccount().size(); i++) {
			if (accountDropDown.allTransactionAccount().get(i).getText().trim().replaceAll("\n", " ")
					.equals(PropsUtil.getDataPropertyValue("TransactionGroupAccount3").trim())
					|| accountDropDown.allTransactionAccount().get(i).getText().trim().replaceAll("\n", " ")
							.equals(PropsUtil.getDataPropertyValue("TransactionGroupAccount2").trim())) {
				accountsTransaction = true;
			} else {
				accountsTransaction = false;
				break;
			}
		}
		Assert.assertTrue(accountsTransaction, "My acconnts and advisor shared accounts transaction are  displaying");
		Assert.assertEquals(accountDropDown.accountFilterLink().getText().trim(),
				PropsUtil.getDataPropertyValue("TransactionSelectedMultiAccountFilterValue"),
				"verify accounts are displaying accountsTab");
	}

	@Test(description = "AT-68620:verify accounts tab after resetting the filter", priority = 17, dependsOnMethods = "verifySelectedAccountsTransactionsDisplaying")
	public void verifyResetFilter() {
		tag.clikcReset();
		// Assert.assertEquals(accountDropDown.accountFilterLink().getText().trim(),PropsUtil.getDataPropertyValue("allAccountFilterLable"));
		Assert.assertEquals(accountDropDown.accountFilterLink().getText().trim(),
				PropsUtil.getDataPropertyValue("TransactionGroup1"));

	}
}
