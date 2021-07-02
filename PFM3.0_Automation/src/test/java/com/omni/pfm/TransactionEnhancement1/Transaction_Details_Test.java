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
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.Keys;
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
import com.omni.pfm.pages.TransactionEnhancement1.Aggregated_Transaction_Details_Loc;
import com.omni.pfm.pages.TransactionEnhancement1.DownloadTrans_Loc;
import com.omni.pfm.pages.TransactionEnhancement1.Series_Recurence_Transaction_Loc;
import com.omni.pfm.pages.TransactionEnhancement1.Transaction_AccountDropDown_Loc;
import com.omni.pfm.pages.TransactionEnhancement1.Transaction_AccountSharing_ReadOnlyFeature_loc;
import com.omni.pfm.pages.TransactionEnhancement1.Transaction_Categorization_Rules_Loc;
import com.omni.pfm.pages.TransactionEnhancement1.Transaction_Details_Loc;
import com.omni.pfm.pages.TransactionEnhancement1.Transaction_Layout_Loc;
import com.omni.pfm.pages.TransactionEnhancement1.Transaction_Projected_Balance_Loc;
import com.omni.pfm.pages.TransactionEnhancement1.Transaction_Split_Locator;
import com.omni.pfm.testBase.TestBase;
import com.omni.pfm.utility.PropsUtil;
import com.omni.pfm.utility.SeleniumUtil;

public class Transaction_Details_Test extends TestBase {
	private static final Logger logger = LoggerFactory.getLogger(Transaction_Details_Test.class);
	AccountAddition accountAdd;
	Transaction_AccountDropDown_Loc accountDropDown;
	Transaction_AccountSharing_ReadOnlyFeature_loc readOnly;
	Add_Manual_Transaction_Loc add_Manual;
	Transaction_Layout_Loc layout;
	Transaction_Details_Loc details;
	DownloadTrans_Loc dowlnoad;
	Transaction_Projected_Balance_Loc balance;
	Aggregated_Transaction_Details_Loc aggregatedTransaction;
	Series_Recurence_Transaction_Loc seriesTransaction;
	Transaction_Categorization_Rules_Loc rule;
	AddToCalendar_Transaction_Loc addToCalander;
	Transaction_Split_Locator split;

	@BeforeClass(alwaysRun = true)
	public void testInit() throws InterruptedException {
		doInitialization("Transaction Details");
		TestBase.tc = TestBase.extent.startTest("Trans tags", "Login");
		TestBase.test.appendChild(TestBase.tc);
		accountAdd = new AccountAddition();
		accountDropDown = new Transaction_AccountDropDown_Loc(d);
		readOnly = new Transaction_AccountSharing_ReadOnlyFeature_loc(d);
		add_Manual = new Add_Manual_Transaction_Loc(d);
		layout = new Transaction_Layout_Loc(d);
		details = new Transaction_Details_Loc(d);
		dowlnoad = new DownloadTrans_Loc(d);
		balance = new Transaction_Projected_Balance_Loc(d);
		aggregatedTransaction = new Aggregated_Transaction_Details_Loc(d);
		seriesTransaction = new Series_Recurence_Transaction_Loc(d);
		rule = new Transaction_Categorization_Rules_Loc(d);
		addToCalander = new AddToCalendar_Transaction_Loc(d);
		split = new Transaction_Split_Locator(d);
		d.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
	}

	@Test(description = "AT-68200,AT-68220", priority = 0)
	public void loginAndLinkAnAccount() throws Exception {
		LoginPage.loginMain(d, loginParameter);
		accountAdd.linkAccount();
		accountAdd.addAggregatedAccount("TransactionDetails.site16441.2", "site16441.2");
		PageParser.forceNavigate("Transaction", d);
		Assert.assertEquals(details.transactionHeader().getText().trim(),
				PropsUtil.getDataPropertyValue("TransactionHeader"), "Transaction header should be Transactions");
	}

	@Test(description = "AT-68386,AT-68418,AT-68910:verify aggregated transaction create rule", priority = 1)
	public void verifyaggCreateRule() throws AWTException, InterruptedException {
		PageParser.forceNavigate("Transaction", d);
		for (int i = 0; i < 5; i++) {
			accountDropDown.clickOnShowMoreButton();
		}
		for (int i = 0; i < aggregatedTransaction.PendingStranctionList().size(); i++) {
			if (add_Manual.getAllPostedAmount1().get(i).getText()
					.equals(PropsUtil.getDataPropertyValue("TransactionDetailsCreateRules1"))) {
				SeleniumUtil.click(add_Manual.getAllPostedAmount1().get(i));
				SeleniumUtil.waitUntilSpinnerWheelIsInvisible();
				SeleniumUtil.waitFor(1);
				break;
			}
		}
		SeleniumUtil.click(aggregatedTransaction.catgoryField());
		SeleniumUtil.click(aggregatedTransaction.catgoryList(4, 1));
		SeleniumUtil.click(aggregatedTransaction.createRule());
		aggregatedTransaction.TransactionCatRuleAmount().clear();
		aggregatedTransaction.TransactionCatRuleAmount()
				.sendKeys(PropsUtil.getDataPropertyValue("TransactionDetailsCreateRules1UpdateAmount"));
		SeleniumUtil.waitFor(1);
		SeleniumUtil.click(aggregatedTransaction.TransactionCatRuleCategoryLink());
		SeleniumUtil.waitFor(1);
		SeleniumUtil.click(aggregatedTransaction.TransactionCatRuleCategoryList(8, 8));
		SeleniumUtil.waitFor(1);
		SeleniumUtil.click(aggregatedTransaction.ruleSave());
		Assert.assertEquals(aggregatedTransaction.SucessMessage().getText(),
				PropsUtil.getDataPropertyValue("AggRuleSucessMessage"));
		SeleniumUtil.click(seriesTransaction.updateTransaction());
	}

	@Test(description = "AT-68421:verify aggregated transaction details screen cancel after create the rule", priority = 2)
	public void verifyaggCreateRulecancelTransaction() throws AWTException, InterruptedException {
		for (int i = 0; i < aggregatedTransaction.PendingStranctionList().size(); i++) {
			if (add_Manual.getAllPostedAmount1().get(i).getText()
					.equals(PropsUtil.getDataPropertyValue("TransactionDetailsCreateRules2"))) {
				SeleniumUtil.click(add_Manual.getAllPostedAmount1().get(i));
				SeleniumUtil.waitUntilSpinnerWheelIsInvisible();
				SeleniumUtil.waitFor(1);
				break;
			}
		}
		SeleniumUtil.click(aggregatedTransaction.catgoryField());
		SeleniumUtil.click(aggregatedTransaction.catgoryList(4, 1));
		SeleniumUtil.click(aggregatedTransaction.createRule());
		aggregatedTransaction.TransactionCatRuleAmount().clear();
		aggregatedTransaction.TransactionCatRuleAmount()
				.sendKeys(PropsUtil.getDataPropertyValue("TransactionDetailsCreateRules1UpdateAmount2"));
		SeleniumUtil.waitFor(1);
		SeleniumUtil.click(aggregatedTransaction.TransactionCatRuleCategoryLink());
		SeleniumUtil.waitFor(1);
		SeleniumUtil.click(aggregatedTransaction.TransactionCatRuleCategoryList(8, 7));
		SeleniumUtil.waitFor(1);
		SeleniumUtil.click(aggregatedTransaction.ruleSave());
		String sucessMessage = aggregatedTransaction.SucessMessage().getText();
		SeleniumUtil.click(seriesTransaction.canceltransaction());
		Assert.assertEquals(sucessMessage, PropsUtil.getDataPropertyValue("AggRuleSucessMessage"));
	}

	@Test(description = "AT-68910:verify series transaction create rule", priority = 3)
	public void verifySeriesCreateRule() throws AWTException, InterruptedException {
		layout.serachTransaction(add_Manual.targateDate1(0), add_Manual.targateDate1(7));
		int transactionSize = readOnly.getAllAmount1().size();
		for (int i = 0; i < transactionSize; i++) {
			if (readOnly.getAllAmount1().get(i).getText().trim()
					.equals(PropsUtil.getDataPropertyValue("TransactionDetailsCreateRules3"))) {
				SeleniumUtil.click(readOnly.getAllAmount1().get(i));
				SeleniumUtil.waitUntilSpinnerWheelIsInvisible();
				SeleniumUtil.waitFor(1);
				break;
			}
		}
		SeleniumUtil.click(aggregatedTransaction.catgoryField());
		Assert.assertTrue(aggregatedTransaction.catgoryList(4, 1).isDisplayed());
		SeleniumUtil.click(aggregatedTransaction.createRule());
		aggregatedTransaction.TransactionCatRuleAmount()
				.sendKeys(PropsUtil.getDataPropertyValue("TransactionDetailsCreateRules1UpdateAmount3"));
		SeleniumUtil.waitFor(1);
		SeleniumUtil.click(aggregatedTransaction.TransactionCatRuleCategoryLink());
		SeleniumUtil.waitFor(1);
		SeleniumUtil.click(aggregatedTransaction.TransactionCatRuleCategoryList(5, 3));
		SeleniumUtil.waitFor(1);
		SeleniumUtil.click(seriesTransaction.createRuleButton());
		Assert.assertEquals(seriesTransaction.sucessMessage().getText(),
				PropsUtil.getDataPropertyValue("AggRuleSucessMessage"));
	}

	@Test(description = "AT-68216,AT-68215:verify download transaction link", priority = 4)
	public void verifyDownloadTransactionLink() {
		SeleniumUtil.click(dowlnoad.morebutton());
		Assert.assertTrue(dowlnoad.downloadIcon().isDisplayed(), "transaction download icon should displayed");
		Assert.assertEquals(dowlnoad.downloadLabel().getText().trim(),
				PropsUtil.getDataPropertyValue("TransactionDownloadLabel"),
				"transaction download label should displayed");
	}

	@Test(description = "AT-68216:verify meneage category link", priority = 5)
	public void verifyManageCategoriesLink() {
		Assert.assertTrue(dowlnoad.manage().isDisplayed(), "transaction manage categories icon should displayed");
		Assert.assertEquals(dowlnoad.manageLabel().getText().trim(),
				PropsUtil.getDataPropertyValue("TransactionManageCategoriesLabel"),
				"manage categories links should be displayed");
	}

	@Test(description = "AT-68216,AT-68217:verify print transaction link", priority = 6)
	public void verifyTransactionPrintLink() {
		Assert.assertTrue(dowlnoad.TransactionPrintIcon().isDisplayed(), "transaction print icon should displayed");
		Assert.assertEquals(dowlnoad.TransactionPrintlabel().getText().trim(),
				PropsUtil.getDataPropertyValue("TransactionPrintLabel"), "print  links should be displayed");
	}

	@Test(description = "AT-68205:verify transaction amount sign", priority = 7)
	public void verifyTransactionShouldNotDisplayedSign() {
		PageParser.forceNavigate("Transaction", d);
		accountDropDown.clickOnShowMoreButton();
		SeleniumUtil.waitUntilSpinnerWheelIsInvisible();
		if (accountDropDown.allTransactionAmount().get(0).getText() == null) {
			SeleniumUtil.click(readOnly.ProjectedExapdIcon());
		}
		boolean amountsignfalse = false;
		SeleniumUtil.waitFor(2);
		int transactionCount = accountDropDown.allTransactionAmount().size();
		for (int i = 0; i < transactionCount; i++) {
			if (accountDropDown.allTransactionAmount().get(i).getText().trim()
					.contains(PropsUtil.getDataPropertyValue("TransactionAmountpositiveSign"))
					|| accountDropDown.allTransactionAmount().get(i).getText().trim()
							.contains(PropsUtil.getDataPropertyValue("TransactionAmountNegativeSign"))) {
				amountsignfalse = false;
			} else {
				amountsignfalse = true;
			}
		}
		Assert.assertTrue(amountsignfalse, "amount should displayed with +ve and -ve sign");
	}

	@Test(description = "AT-68211:verify todat date text in posted transaction section date header", priority = 8)
	public void verifyTodaysTransactionDisplayedTodayText() throws ParseException {
		Assert.assertEquals(accountDropDown.TransactionPostedTransactionDateHeader().get(0).getText().trim(),
				PropsUtil.getDataPropertyValue("TransactionPostedTransactionTodayText") + " "
						+ balance.getDateMMMMDDYYYy(0),
				"todays transaction date header should displayed with todays text and date mmmm dd,yy");
	}

	@Test(description = "AT-68213:verify posted section when no pending transaction", priority = 9)
	public void verifyPostedpendingTransactionWhenNoPendingTransaction() throws ParseException {
		Assert.assertEquals(layout.pendingPostedHeader().getText().trim(),
				PropsUtil.getDataPropertyValue("TransactionPostedTransactionHeader"),
				"posted pending transaction header should displayed when there is no pending transactiob");
	}

	@Test(description = "AT-68235:verify load more transaction link", priority = 10)
	public void verifyLoadMoreLinkShouldDisplayed() throws ParseException {
		PageParser.forceNavigate("Transaction", d);
		if (accountDropDown.allTransactionAmount().get(0).getText() == null) {
			SeleniumUtil.click(readOnly.ProjectedExapdIcon());
		}
		Assert.assertTrue(SeleniumUtil.isDisplayed(Transaction_AccountDropDown_Loc.showMoreButton, 5),
				"load more transaction link should displayed after 25 transaction ");
	}

	@Test(description = "AT-68236:loaded transaction after click on load more link", priority = 11)
	public void verifyLoadedTransactionAfterclickLoadMoreLink() throws ParseException {
		accountDropDown.clickOnShowMoreButton();
		SeleniumUtil.waitUntilSpinnerWheelIsInvisible();
		boolean transactionLoaded = false;
		if (accountDropDown.allTransactionAmount().get(0).getText() == null) {
			SeleniumUtil.click(add_Manual.ProjectedExapdIcon());
		}
		if (accountDropDown.allTransactionAmount().size() > 25) {
			transactionLoaded = true;
		}
		Assert.assertTrue(transactionLoaded);
	}

	@Test(description = "AT-68237,AT-68238:verify no more transaction after click on load  more link", priority = 12)
	public void verifyNoMoreTransaction() throws ParseException {
		try {
			if (SeleniumUtil.isDisplayed(accountDropDown.showMoreButton, 1)) {
				for (int i = 0; i < 45; i++) {
					accountDropDown.clickOnShowMoreButton();
					SeleniumUtil.waitUntilSpinnerWheelIsInvisible();
					if (!SeleniumUtil.isDisplayed(accountDropDown.showMoreButton, 1)) {
						break;
					}
				}
			}
		} catch (Exception e) {
		}
		Assert.assertEquals(details.transctionnoMoreTransaction().getText().trim(),
				PropsUtil.getDataPropertyValue("TransactionNoMoreTransaction"), "no more transaction should displayed");
		Assert.assertTrue(
				details.showmoretransactionHide().getAttribute("class")
						.contains(PropsUtil.getDataPropertyValue("TransactionLoadMoreHide")),
				"load more transaction should noy displayed no when no more transaction");
	}

	@Test(description = "AT-68301,AT-68302:verify add calendar link for card account transaction", priority = 13)
	public void verifyAddToCalanderDispolayingForCardAccount() throws AWTException, InterruptedException {
		for (int i = 0; i < aggregatedTransaction.PendingStranctionList().size(); i++) {
			if (add_Manual.getAllPostedAmount1().get(i).getText()
					.equals(PropsUtil.getDataPropertyValue("TransactionDetailsCardAccountCreateRule"))) {
				SeleniumUtil.click(add_Manual.getAllPostedAmount1().get(i));
				SeleniumUtil.waitUntilSpinnerWheelIsInvisible();
				SeleniumUtil.waitFor(1);
				break;
			}
		}
		Assert.assertTrue(addToCalander.addcalLink().isDisplayed());
		Assert.assertTrue(addToCalander.addcalIcon().isDisplayed());
	}

	@Test(description = "AT-68270:verify create rule should not displayed with out changing category ", priority = 14)
	public void verifyCreateRuleShouldNotdisplayed() throws ParseException {
		Assert.assertTrue(!SeleniumUtil.isDisplayed(aggregatedTransaction.createRule, 2),
				"Verify that Create rule should not be displayed if user don't change the category");
	}

	@Test(description = "AT-68276:verify aggregated transaction tag gost text", priority = 15)
	public void verifyAggregatedTagGoastText() throws ParseException {
		SeleniumUtil.click(aggregatedTransaction.TransactionTagLink());
		Assert.assertEquals(aggregatedTransaction.TransactionTagField().getAttribute("placeholder"),
				PropsUtil.getDataPropertyValue("TransactionTagGoastText"),
				"Add Tags... goast text should be displayed");
	}

	@Test(description = "AT-68278:verify aggregated transaction ta min 3 char error message", priority = 16)
	public void verifyAggregatedDetailsMin3charErrorTag() throws ParseException {
		aggregatedTransaction.TransactionTagField()
				.sendKeys(PropsUtil.getDataPropertyValue("TransactionAggegateddetailsTag1"));
		aggregatedTransaction.TransactionTagField().sendKeys(Keys.ENTER);
		Assert.assertEquals(aggregatedTransaction.transctionnoDetailsTagError().getText().trim(),
				PropsUtil.getDataPropertyValue("TransactionAggegateddetails3mincharError"),
				"At least 3 characters are required message should displayed");
	}

	@Test(description = "AT-68496,AT-69025:verify check field should not displayed for  aggregated transaction", priority = 17)
	public void verifyAggregatedDetailsCheck() throws ParseException {
		SeleniumUtil.click(aggregatedTransaction.ShowMore());
		Assert.assertTrue(!SeleniumUtil.isDisplayed(seriesTransaction.checkField, 2),
				"check field should not displayed");
	}

	@Test(description = "AT-68498,AT-68499:verify aggregated transaction note max char validation", priority = 18)
	public void verifyAggregatedDetailsNote150() throws ParseException {
		aggregatedTransaction.note().sendKeys(PropsUtil.getDataPropertyValue("TransactionAgreggatedDetailsNote150"));
		boolean noteField50Char = false;
		if (aggregatedTransaction.note().getAttribute("value").length() == 150) {
			noteField50Char = true;
		}
		Assert.assertTrue(noteField50Char, "note field should allow 150 character should not displayed");
	}

	@Test(description = "AT-68289:one  transaction details screen should be expendaded", priority = 19)
	public void verifyOneTransactionDetailsexpanded() throws ParseException {
		SeleniumUtil.click(add_Manual.getAllPostedAmount1().get(0));
		SeleniumUtil.waitForPageToLoad(1000);
		SeleniumUtil.click(add_Manual.getAllPostedAmount1().get(1));
		SeleniumUtil.waitForPageToLoad(1000);
		Assert.assertFalse(readOnly.transactionDetailsColapsed().get(0).getAttribute("class").contains("active"));
		Assert.assertTrue(readOnly.transactionDetailsColapsed().get(1).getAttribute("class").contains("active"));
	}

	@Test(description = "AT-68383:verify one instance transaction should bve updated", priority = 20)
	public void verifyOneIntanceTransactionUpdate() throws ParseException, AWTException {
		layout.serachTransaction(add_Manual.targateDate1(0), add_Manual.targateDate1(7));
		readOnly.editPeojectedinstanceTransactionDetails(0,
				PropsUtil.getDataPropertyValue("TransactionInstanceDetailsAmount1"),
				PropsUtil.getDataPropertyValue("TransactionInstanceDetailsTag1"),
				PropsUtil.getDataPropertyValue("TransactionInstanceDetailsNote1"),
				PropsUtil.getDataPropertyValue("TransactionInstanceDetailsCheck1"),
				PropsUtil.getDataPropertyValue("TransactionReadOnlyAttachmentPath"),
				PropsUtil.getDataPropertyValue("TransactionInstanceDetailsSearchAmount1"), 1, 1);
		int transactionSize = readOnly.getAllAmount1().size();
		for (int i = 0; i < transactionSize; i++) {
			if (readOnly.getAllAmount1().get(i).getText().trim()
					.equals(PropsUtil.getDataPropertyValue("TransactionInstanceDetailsSearchAmount2"))) {
				SeleniumUtil.click(readOnly.getAllAmount1().get(i));
				SeleniumUtil.waitUntilSpinnerWheelIsInvisible();
				SeleniumUtil.waitFor(1);
				break;
			}
		}
		String amount = readOnly.transactionDetailsAmount().getAttribute("value");
		String category = seriesTransaction.catdropdown().getText().trim();
		String tag = aggregatedTransaction.AggregatedListTag().get(0).getText();
		SeleniumUtil.click(aggregatedTransaction.ShowMore());
		String note = readOnly.transactionDetailsNote().getAttribute("value");
		boolean attachment = readOnly.transactionDetailsReadonlyAttachmentImage().get(0).isDisplayed();
		Assert.assertEquals(amount, PropsUtil.getDataPropertyValue("TransactionInstanceDetailsSearchAmount22"),
				"verify updated  amount");
		Assert.assertEquals(category, PropsUtil.getDataPropertyValue("TransactionInstanceDetailsCategory1"),
				"verify updated category ");
		Assert.assertEquals(tag, PropsUtil.getDataPropertyValue("TransactionInstanceDetailsTag1"),
				"verify updated tag ");
		Assert.assertEquals(note, PropsUtil.getDataPropertyValue("TransactionInstanceDetailsNote1"),
				"verify updated  note");
		Assert.assertTrue(attachment, "added attachment should displayed");
	}

	@Test(description = "AT-68383:verify series transaction next instance should not updated when only one instance in updated", priority = 21)
	public void verifyrecurencrTransactionShouldNotupdate() throws ParseException, AWTException {
		layout.serachTransaction(add_Manual.targateDate1(0), add_Manual.targateDate1(14));
		int transactionSize = readOnly.getAllAmount1().size();
		boolean nextSeries = false;
		for (int i = 0; i < transactionSize; i++) {
			if (readOnly.getAllAmount1().get(i).getText().trim()
					.equals(PropsUtil.getDataPropertyValue("TransactionInstanceDetailsSearchAmount1"))) {
				SeleniumUtil.click(readOnly.getAllAmount1().get(i));
				SeleniumUtil.waitUntilSpinnerWheelIsInvisible();
				SeleniumUtil.waitFor(1);
				nextSeries = true;
				break;
			}
		}
		// SeleniumUtil.click(readOnly.getAllAmount1().get(transactionSize-2));
		String amount = readOnly.transactionDetailsAmount().getAttribute("value");
		String category = seriesTransaction.catdropdown().getText().trim();
		int tag = aggregatedTransaction.AggregatedListTag().size();
		SeleniumUtil.click(aggregatedTransaction.ShowMore());
		String note = readOnly.transactionDetailsNote().getAttribute("value");
		Assert.assertTrue(nextSeries, "next series transaction");
		Assert.assertEquals(amount, PropsUtil.getDataPropertyValue("TransactionInstanceDetailsSearchAmount111"),
				"verify updated  amount");
		Assert.assertEquals(category, PropsUtil.getDataPropertyValue("TransactionRecurrenceDetailsCategory"),
				"verify updated category ");
		Assert.assertEquals(tag, 0, "verify updated tag ");
		Assert.assertEquals(note, "", "verify updated  note");
	}

	@Test(description = "AT-68384,AT-68426:verify series transaction updated", priority = 22)
	public void verifySeriesTransactionUpdate() throws ParseException, AWTException {
		layout.serachTransaction(add_Manual.targateDate1(0), add_Manual.targateDate1(7));
		readOnly.editPeojectedSeriesTransactionDetails(0,
				PropsUtil.getDataPropertyValue("TransactionSeriesDetailsUpdateAmount"),
				PropsUtil.getDataPropertyValue("TransactionSeriesDetailsUpdateTag"),
				PropsUtil.getDataPropertyValue("TransactionSeriesDetailsUpdateNote"),
				PropsUtil.getDataPropertyValue("TransactionSeriesDetailsSearchAmount"), 1, 1);
		int transactionSize = readOnly.getAllAmount1().size();
		for (int i = 0; i < transactionSize; i++) {
			if (readOnly.getAllAmount1().get(i).getText().trim()
					.equals(PropsUtil.getDataPropertyValue("TransactionSeriesDetailsUpdateAmountSearch"))) {
				SeleniumUtil.click(readOnly.getAllAmount1().get(i));
				SeleniumUtil.waitUntilSpinnerWheelIsInvisible();
				SeleniumUtil.waitFor(1);
				break;
			}
		}
		// SeleniumUtil.click(readOnly.getAllAmount1().get(transactionSize-1));
		SeleniumUtil.click(readOnly.allradioButtonText());
		String amount = readOnly.transactionDetailsAmount().getAttribute("value");
		String category = seriesTransaction.catdropdown().getText().trim();
		String tag = aggregatedTransaction.AggregatedListTag().get(0).getText();
		SeleniumUtil.click(aggregatedTransaction.ShowMore());
		String note = readOnly.transactionDetailsNote().getAttribute("value");
		Assert.assertEquals(amount, PropsUtil.getDataPropertyValue("TransactionSeriesDetailsUpdateAmountSearch111"),
				"verify updated  amount");
		Assert.assertEquals(category, PropsUtil.getDataPropertyValue("TransactionSeriesDetailsUpdatedCategory1"),
				"verify updated category ");
		Assert.assertEquals(tag, PropsUtil.getDataPropertyValue("TransactionSeriesDetailsUpdateTag"),
				"verify updated tag ");
		Assert.assertEquals(note, PropsUtil.getDataPropertyValue("TransactionSeriesDetailsUpdateNote"),
				"verify updated  note");
	}

	@Test(description = "AT-68401:verify series transaction updated scheduled date", priority = 23)
	public void verifySeriesTransaction1SceduledDate() throws ParseException, AWTException {
		Assert.assertEquals(seriesTransaction.Scheduledate().getAttribute("value").trim(), add_Manual.targateDate1(7),
				"first series transaction date should displayed");
	}

	@Test(description = "AT-68384,AT-68426:verify series transaction1 is updated ", priority = 24)
	public void verifySeriesTransactionUpdate1() throws ParseException, AWTException {
		layout.serachTransaction(add_Manual.targateDate1(0), add_Manual.targateDate1(14));
		int transactionSize = readOnly.getAllAmount1().size();
		for (int i = 0; i < transactionSize; i++) {
			if (readOnly.getAllAmount1().get(i).getText().trim()
					.equals(PropsUtil.getDataPropertyValue("TransactionSeriesDetailsUpdateAmountSearch"))) {
				SeleniumUtil.click(readOnly.getAllAmount1().get(i));
				SeleniumUtil.waitUntilSpinnerWheelIsInvisible();
				SeleniumUtil.waitFor(1);
				break;
			}
		}
		// SeleniumUtil.click(readOnly.getAllAmount1().get(transactionSize-2));
		SeleniumUtil.click(readOnly.allradioButtonText());
		String amount = readOnly.transactionDetailsAmount().getAttribute("value");
		String category = seriesTransaction.catdropdown().getText().trim();
		String tag = aggregatedTransaction.AggregatedListTag().get(0).getText();
		SeleniumUtil.click(aggregatedTransaction.ShowMore());
		String note = readOnly.transactionDetailsNote().getAttribute("value");
		Assert.assertEquals(amount, PropsUtil.getDataPropertyValue("TransactionSeriesDetailsUpdateAmountSearch111"),
				"verify updated  amount");
		Assert.assertEquals(category, PropsUtil.getDataPropertyValue("TransactionSeriesDetailsUpdatedCategory1"),
				"verify updated category ");
		Assert.assertEquals(tag, PropsUtil.getDataPropertyValue("TransactionSeriesDetailsUpdateTag"),
				"verify updated tag ");
		Assert.assertEquals(note, PropsUtil.getDataPropertyValue("TransactionSeriesDetailsUpdateNote"),
				"verify updated  note");
	}

	@Test(description = "AT-68401:verify series transaction2 is updated", priority = 26)
	public void verifySeriesTransaction2SceduledDate() throws ParseException, AWTException {
		Assert.assertEquals(seriesTransaction.Scheduledate().getAttribute("value").trim(), add_Manual.targateDate1(7),
				"first series transaction date should displayed");
	}

	@Test(description = "AT-68414:verify create rule should displayed for series when user not changed the category", priority = 28)
	public void verifySeriesTransactionCreateRule() throws ParseException, AWTException {
		boolean createRuleFalse = false;
		if (seriesTransaction.createRuleLinkList().get(0).isEnabled()) {
			createRuleFalse = true;
		}
		Assert.assertTrue(createRuleFalse,
				"Verify that Create rule should not be displayed if user don't change the category");
	}

	@Test(description = "AT-68402::verify scheduled date for second series transaction when first series transaction instance is deleted", priority = 29)
	public void verifyNextseriesTransactionScheduledDateWhenDeletFirstSeries() throws ParseException, AWTException {
		layout.serachTransaction(add_Manual.targateDate1(0), add_Manual.targateDate1(7));
		// search.serachAmount(PropsUtil.getDataPropertyValue("TransactionSeriesAmountRange5"),
		// PropsUtil.getDataPropertyValue("TransactionSeriesAmountRange6"));
		int transactionSize = readOnly.getAllAmount1().size();
		for (int i = 0; i < transactionSize; i++) {
			if (readOnly.getAllAmount1().get(i).getText().trim()
					.equals(PropsUtil.getDataPropertyValue("TransactionDeleteFirstInstance"))) {
				SeleniumUtil.click(readOnly.getAllAmount1().get(i));
				SeleniumUtil.waitUntilSpinnerWheelIsInvisible();
				SeleniumUtil.waitFor(1);
				break;
			}
		}
		// SeleniumUtil.click(readOnly.getAllAmount1().get(transactionSize-1));
		SeleniumUtil.click(aggregatedTransaction.ShowMore());
		SeleniumUtil.click(seriesTransaction.deleteTransaction());
		SeleniumUtil.click(seriesTransaction.deleteButton());
		layout.serachTransaction(add_Manual.targateDate1(14), add_Manual.targateDate1(14));
		int transactionSize1 = readOnly.getAllAmount1().size();
		for (int i = 0; i < transactionSize1; i++) {
			if (readOnly.getAllAmount1().get(i).getText().trim()
					.equals(PropsUtil.getDataPropertyValue("TransactionDeleteFirstInstance"))) {
				SeleniumUtil.click(readOnly.getAllAmount1().get(i));
				SeleniumUtil.waitUntilSpinnerWheelIsInvisible();
				SeleniumUtil.waitFor(1);
				break;
			}
		}
		SeleniumUtil.click(readOnly.getAllAmount1().get(transactionSize1 - 1));
		Assert.assertEquals(seriesTransaction.Scheduledate().getAttribute("value").trim(), add_Manual.targateDate1(14),
				"first series transaction date should displayed");
	}

	@Test(description = "AT-68613:verify aggregated transaction should delete when projected series transaction deleted", priority = 30)
	public void verifyAggrgatedTransactioWhennDeletedFirstSeries() throws ParseException, AWTException {
		PageParser.forceNavigate("Transaction", d);
		for (int i = 0; i < 5; i++) {
			accountDropDown.clickOnShowMoreButton();
			SeleniumUtil.waitUntilSpinnerWheelIsInvisible();
		}
		boolean aggregatedTransaction = false;
		int transactionSize1 = add_Manual.getAllPostedAmount1().size();
		for (int i = 0; i < transactionSize1; i++) {
			if (add_Manual.getAllPostedAmount1().get(i).getText().trim()
					.equals(PropsUtil.getDataPropertyValue("TransactionDeleteFirstInstance"))) {
				aggregatedTransaction = true;
				break;
			}
		}
		Assert.assertTrue(aggregatedTransaction,
				"agregated transaction should not delete when delete series transaction");
	}

	@Test(description = "AT-68440:verify sub category is selected for transaction", priority = 31)
	public void verifySubCategorySelected() throws ParseException, AWTException {
		rule.manageCat();
		rule.editCat(PropsUtil.getDataPropertyValue("InvestorRuleCatChange"),
				PropsUtil.getDataPropertyValue("InvestorRuleSubCatChange"), 1, 3);
		PageParser.forceNavigate("Transaction", d);
		accountDropDown.clickOnShowMoreButton();
		SeleniumUtil.waitUntilSpinnerWheelIsInvisible();
		SeleniumUtil.click(readOnly.pendingStranctionList().get(0));
		SeleniumUtil.click(readOnly.transactionDetailsCategoryLink());
		SeleniumUtil.click(readOnly.transactionDetailsSubCategoryList(5, 2));
		SeleniumUtil.click(readOnly.transactionDetailsSave());
		SeleniumUtil.click(readOnly.pendingStranctionList().get(0));
		Assert.assertEquals(aggregatedTransaction.catgoryField().getText(),
				PropsUtil.getDataPropertyValue("InvestorRuleSubCatChange"), "verify updated category ");
	}

	@Test(description = "AT-68455,AT-68456,AT-68458:verify system predicted series transaction delete popup header", priority = 32, groups = {
			"Smoke" })
	public void DeleteTransaction() throws Exception {
		layout.serachTransaction(add_Manual.targateDate1(0), add_Manual.targateDate1(7));
		SeleniumUtil.click(readOnly.getAllAmount1().get(0));
		SeleniumUtil.click(aggregatedTransaction.ShowMore());
		String deleteButtontext = seriesTransaction.deleteTransaction().getText().trim();
		SeleniumUtil.click(seriesTransaction.deleteTransaction());
		Assert.assertEquals(deleteButtontext, PropsUtil.getDataPropertyValue("TransactionSeriesDeleteText"),
				"delete transaction should displayed");
		Assert.assertEquals(seriesTransaction.rulePopUp().getText(),
				PropsUtil.getDataPropertyValue("InstdeleteHeader"));
	}

	@Test(description = "AT-68459:verify series transaction delete popup info message ", priority = 33, groups = {
			"Smoke" })
	public void DeleteTransactionPopUpMessage() throws Exception {
		Assert.assertEquals(seriesTransaction.seriesdeleteMessage().getText(),
				PropsUtil.getDataPropertyValue("InstdeleteMessage"));
	}

	@Test(description = "verify series transaction delete popup cancel", priority = 34, groups = { "Smoke" })
	public void DeleteTransactionPopUpCancel() throws Exception {
		Assert.assertTrue(seriesTransaction.Deletecancel().isDisplayed());
	}

	@Test(description = "verify series transaction delete popup confirm button", priority = 35, groups = { "Smoke" })
	public void DeleteTransactionPopUpDelete() throws Exception {
		Assert.assertTrue(seriesTransaction.deleteButton().isDisplayed());
	}

	@Test(description = "verify series transaction popup should closed when click on cancel button", priority = 36, groups = {
			"Smoke" })
	public void DeleteTransactionPopUpCancelClick() throws Exception {
		SeleniumUtil.click(seriesTransaction.Deletecancel());
		Assert.assertTrue(!SeleniumUtil.isDisplayed(seriesTransaction.rulePopup, 2));
	}

	@Test(description = "verify series transaction popup should closed when click on close button", priority = 37, groups = {
			"Smoke" })
	public void DeleteTransactionPopUpCloseClick() throws Exception {
		SeleniumUtil.click(seriesTransaction.deleteTransaction());
		// Series_Tranc_details.deleteTransaction().click();
		SeleniumUtil.click(seriesTransaction.deleteclose());
		Assert.assertTrue(!SeleniumUtil.isDisplayed(seriesTransaction.rulePopup, 2));
	}

	@Test(description = "AT-68460,AT-68464:verify series transaction popup header", priority = 38)
	public void DeleteSeriesTransactionHeader() throws Exception {
		SeleniumUtil.click(seriesTransaction.allradioButtonText());
		SeleniumUtil.click(seriesTransaction.deleteTransaction());
		Assert.assertEquals(seriesTransaction.rulePopUp().getText(), PropsUtil.getDataPropertyValue("deleteHeader"));
	}

	@Test(description = "AT-68460:verify delet transaction popup info message", priority = 39)
	public void DeleteSeriesTransactionMessage() throws Exception {
		Assert.assertEquals(seriesTransaction.seriesdeleteMessage().getText(),
				PropsUtil.getDataPropertyValue("SerideleteMessage"));
	}

	@Test(description = "AT-68461:verify delete transaction popup cancel", priority = 40, groups = { "Smoke" })
	public void DeleteSeriesTransactionPopUpCancel() throws Exception {
		Assert.assertTrue(seriesTransaction.Deletecancel().isDisplayed());
	}

	@Test(description = "AT-68461:verify delet transaction popup delet buuton", priority = 41, groups = { "Smoke" })
	public void DeleteSeriesTransactionPopUpDelete() throws Exception {
		Assert.assertTrue(seriesTransaction.deleteButton().isDisplayed());
	}

	@Test(description = "AT-68462:verify delete transaction pop should closed when click on cancel", priority = 42, groups = {
			"Smoke" })
	public void DeleteSeriesTransactionPopUpCancelClick() throws Exception {
		SeleniumUtil.click(seriesTransaction.Deletecancel());
		Assert.assertTrue(!SeleniumUtil.isDisplayed(seriesTransaction.rulePopup, 2));
	}

	@Test(description = "AT-68462:verify delete transaction should closed when click on close button", priority = 43, groups = {
			"Smoke" })
	public void DeleteSeriesTransactionPopUpCloseClick() throws Exception {
		SeleniumUtil.click(seriesTransaction.deleteTransaction());
		Assert.assertTrue(!SeleniumUtil.isDisplayed(seriesTransaction.rulePopup, 2));
	}

	@Test(description = "AT-68405,AT-68408,AT-68438:verify once time transaction delete", priority = 44, groups = {
			"Smoke" })
	public void VerifyOneTimeTransactionScheduledDateUpdate() throws Exception {
		PageParser.forceNavigate("Transaction", d);
		SeleniumUtil.click(add_Manual.addManualLink());
		add_Manual.createOneTimeTransaction(
				PropsUtil.getDataPropertyValue("TransactionOneTimeManualTransactionAmount1"),
				PropsUtil.getDataPropertyValue("TransactionOneTimeManualTransactionDescription1"), 1, 1, 0, 0, 1, 1,
				PropsUtil.getDataPropertyValue("TransactionOneTimeManualTransactionNote1"),
				PropsUtil.getDataPropertyValue("TransactionOneTimeManualTransactioncheck1"));
		for (int i = 0; i < 6; i++) {
			accountDropDown.clickOnShowMoreButton();
			SeleniumUtil.waitUntilSpinnerWheelIsInvisible();
		}
		int transactionSize1 = add_Manual.getAllPostedAmount1().size();
		for (int i = 0; i < transactionSize1; i++) {
			if (add_Manual.getAllPostedAmount1().get(i).getText().trim()
					.equals(PropsUtil.getDataPropertyValue("TransactionSeriesDetailsUpdatedsearchAmount"))) {
				SeleniumUtil.click(add_Manual.getAllPostedAmount1().get(i));
				SeleniumUtil.waitUntilSpinnerWheelIsInvisible();
				SeleniumUtil.waitFor(1);
				break;
			}
		}
		seriesTransaction.Scheduledate().clear();
		seriesTransaction.Scheduledate().sendKeys(add_Manual.targateDate1(2));
		SeleniumUtil.click(aggregatedTransaction.update());
		SeleniumUtil.waitUntilSpinnerWheelIsInvisible();
		SeleniumUtil.waitFor(1);
		layout.serachTransaction(add_Manual.targateDate1(0), add_Manual.targateDate1(2));
		boolean updatedOneTimeTransaction = false;
		int transactionSize = seriesTransaction.seriesTransactionAmount().size();
		for (int i = 0; i < transactionSize; i++) {
			if (seriesTransaction.seriesTransactionAmount().get(i).getText().trim()
					.equals(PropsUtil.getDataPropertyValue("TransactionSeriesDetailsUpdatedsearchAmount"))) {
				SeleniumUtil.click(seriesTransaction.seriesTransactionAmount().get(i));
				updatedOneTimeTransaction = true;
				break;
			}
		}
		Assert.assertTrue(updatedOneTimeTransaction, " transaction should update to future");
	}

	@Test(description = "AT-68405,AT-68408,AT-68438:verify one time transaction update from future date past date", priority = 45, groups = {
			"Smoke" })
	public void VerifyOneTimeTransactionScheduledDateUpdateToPastDate() throws Exception {
		seriesTransaction.Scheduledate().clear();
		seriesTransaction.Scheduledate().sendKeys(add_Manual.targateDate1(4));
		SeleniumUtil.click(aggregatedTransaction.update());
		SeleniumUtil.waitUntilSpinnerWheelIsInvisible();
		SeleniumUtil.waitFor(1);
		layout.serachTransaction(add_Manual.targateDate1(0), add_Manual.targateDate1(4));
		boolean updatedOneTimeTransaction = false;
		int transactionSize1 = seriesTransaction.seriesTransactionAmount().size();
		for (int i = 0; i < transactionSize1; i++) {
			if (seriesTransaction.seriesTransactionAmount().get(i).getText().trim()
					.equals(PropsUtil.getDataPropertyValue("TransactionSeriesDetailsUpdatedsearchAmount"))) {
				updatedOneTimeTransaction = true;
				SeleniumUtil.waitUntilSpinnerWheelIsInvisible();
				SeleniumUtil.waitFor(1);
				break;
			}
		}
		Assert.assertTrue(updatedOneTimeTransaction, " transaction should update to past date");
	}

	@Test(description = "verify end should not displayed for when just this radio button is selected", priority = 46, groups = {
			"Smoke" })
	public void VerifySeriesTransactionScheduled() throws Exception {
		SeleniumUtil.click(add_Manual.addManualLink());
		add_Manual.createTransactionWithRecuralldetailsInvestorAndAdvisor(
				PropsUtil.getDataPropertyValue("TransactionSeriesManualTransactionAmount"),
				PropsUtil.getDataPropertyValue("TransactionSeriesManualTransactionDescription"), 1, 1, 0, 30, 1, 1,
				PropsUtil.getDataPropertyValue("TransactionSeriesManualTransactionTag"),
				PropsUtil.getDataPropertyValue("TransactionSeriesManualTransactionNote1"));
		layout.serachTransaction(add_Manual.targateDate1(0), add_Manual.targateDate1(7));
		int transactionSize = readOnly.getAllAmount1().size();
		for (int i = 0; i < transactionSize; i++) {
			if (readOnly.getAllAmount1().get(i).getText().trim()
					.equals(PropsUtil.getDataPropertyValue("TransactionSeriesDetailsUpdatedsearch1"))) {
				SeleniumUtil.click(readOnly.getAllAmount1().get(i));
				SeleniumUtil.waitUntilSpinnerWheelIsInvisible();
				SeleniumUtil.waitFor(1);
				break;
			}
		}
		Assert.assertTrue(seriesTransaction.enddateHide_SRT().getAttribute("style").contains("none"),
				"end date should not displayed");
	}

	@Test(description = "verify scheduled error message", priority = 47)
	public void verifySeriesTransactionSceduledDateError() throws ParseException, AWTException {
		seriesTransaction.Scheduledate().clear();
		seriesTransaction.Scheduledate().sendKeys(add_Manual.targateDate1(29));
		Assert.assertEquals(seriesTransaction.ScheduledateErr().getText(),
				PropsUtil.getDataPropertyValue("TransactionSeriesDetailsScheduledDateError"),
				"Schedule date should be between the previous");
	}

	@Test(description = "AT-68424:verify end should displayed when all radio button is selected", priority = 48, groups = {
			"Smoke" })
	public void VerifySeriesTransactionEndDate() throws Exception {
		SeleniumUtil.click(seriesTransaction.allradioButtonText());
		SeleniumUtil.waitForPageToLoad(1000);
		Assert.assertTrue(seriesTransaction.enddate_SRT().isDisplayed(), "end date should  displayed");
	}

	@Test(description = "AT-68425:verify seies transaction end date error messgae", priority = 49, groups = { "Smoke" })
	public void VerifySeriesTransactionEndDateError() throws Exception {
		SeleniumUtil.click(seriesTransaction.enddate_SRT());
		seriesTransaction.enddate_SRT().clear();
		seriesTransaction.enddate_SRT().sendKeys(add_Manual.targateDate1(-6));
		seriesTransaction.enddate_SRT().sendKeys(Keys.TAB);
		Assert.assertEquals(seriesTransaction.enddateError_SRT().getText(),
				PropsUtil.getDataPropertyValue("TransactionseriesLessEndDateError"),
				"End date should be later than the expected transaction date error should displayed");
	}

	@Test(description = "AT-68592:verify series transaction tag", priority = 50, groups = { "Smoke" })
	public void VerifySeriesTransactiontag() throws Exception {
		Assert.assertEquals(aggregatedTransaction.AggregatedListTag().get(0).getText(),
				PropsUtil.getDataPropertyValue("TransactionSeriesManualTransactionTag"));
	}

	@Test(description = "AT-68377:verify add to canlendar for splited transaction", priority = 51, groups = { "Smoke" })
	public void VerifySplitedTransactionCreateEvent() throws Exception {
		PageParser.forceNavigate("Transaction", d);
		for (int i = 0; i < 3; i++) {
			accountDropDown.clickOnShowMoreButton();
			SeleniumUtil.waitUntilSpinnerWheelIsInvisible();
		}
		SeleniumUtil.click(addToCalander.PendingStranctionList().get(3));
		SeleniumUtil.click(aggregatedTransaction.ShowMore());
		SeleniumUtil.click(split.SplitLink());
		SeleniumUtil.click(split.savechanges());
		for (int i = 0; i < 3; i++) {
			accountDropDown.clickOnShowMoreButton();
			SeleniumUtil.waitUntilSpinnerWheelIsInvisible();
		}
		SeleniumUtil.click(addToCalander.PendingStranctionList().get(3));
		SeleniumUtil.click(addToCalander.addcalLink());
		SeleniumUtil.click(addToCalander.addCal());
		boolean expected = false;
		layout.serachTransaction(seriesTransaction.DateFormate(seriesTransaction.TransactionDate(0)),
				seriesTransaction.DateFormate(seriesTransaction.TransactionDate(0)));
		if (seriesTransaction.getAllaccount1().get(0).getText() == null) {
			SeleniumUtil.click(add_Manual.ProjectedExapdIcon());
		}
		String Description = null;
		String Accountname = null;
		String catg = null;
		String Amount = null;
		String Freq = null;
		for (int i = 0; i < seriesTransaction.getAllAmount1().size(); i++) {
			if (seriesTransaction.getAllAmount1().get(i).getText()
					.equals(PropsUtil.getDataPropertyValue("TransactionSplitedAddTocalAmount"))) {
				expected = true;
				Description = seriesTransaction.getAlldescription1().get(i).getText();
				Accountname = seriesTransaction.getAllaccount1().get(i).getText().replaceAll("\n", "");
				catg = seriesTransaction.getAllcat1().get(i).getText();
				Amount = seriesTransaction.getAllAmount1().get(i).getText();
				Freq = seriesTransaction.SeriesFrrequency().get(i).getText();
				SeleniumUtil.click(seriesTransaction.getAllAmount1().get(i));
				break;
			}
		}
		Assert.assertTrue(expected);
		Assert.assertEquals(Description, PropsUtil.getDataPropertyValue("TransactionSplitedAddTocaldescription"));
		Assert.assertEquals(catg, PropsUtil.getDataPropertyValue("TransactionSplitedAddTocalCategory"));
		Assert.assertEquals(Accountname, PropsUtil.getDataPropertyValue("TransactionSplitedAddTocalAccount"));
		Assert.assertEquals(Amount, PropsUtil.getDataPropertyValue("TransactionSplitedAddTocalAmount"));
		Assert.assertTrue(Freq.trim().contains(PropsUtil.getDataPropertyValue("TransactionSplitedAddTocalFrequency")));
	}

	@Test(description = "AT-68549:verify default invetsment type for manual for transaction", priority = 52, groups = {
			"Smoke" })
	public void VerifyDefaultInvetsmentType() throws Exception {
		SeleniumUtil.click(add_Manual.addManualLink());
		SeleniumUtil.waitForPageToLoad(1000);
		SeleniumUtil.click(add_Manual.TransactionType());
		SeleniumUtil.waitForPageToLoad(500);
		SeleniumUtil.click(add_Manual.TtransactionList().get(2));
		Assert.assertEquals(add_Manual.InvestmentType().getText(),
				PropsUtil.getDataPropertyValue("TransactionInvetsmentManualTransactionType"), "default type BUY");
	}

	@Test(description = "AT-68549:verify dafault invetsment type sell", priority = 53, groups = { "Smoke" })
	public void VerifyDefaultInvetsmentTypeSell() throws Exception {
		SeleniumUtil.click(add_Manual.InvestmentType());
		SeleniumUtil.waitForPageToLoad(1000);
		SeleniumUtil.click(add_Manual.investmentList().get(1));
		Assert.assertEquals(add_Manual.InvestmentType().getText(),
				PropsUtil.getDataPropertyValue("TransactionInvetsmentManualTransactionType1"), "default type BUY");
	}

	@Test(description = "AT-68550:verify all hoalding type in add manual transaction", priority = 54, groups = {
			"Smoke" })
	public void VerifyHoldingType() throws Exception {
		SeleniumUtil.click(add_Manual.Holdingtype());
		String holding[] = PropsUtil.getDataPropertyValue("TransactionInvetsmentManualHolding").split(",");
		for (int i = 0; i < add_Manual.listOfHolding().size(); i++) {
			Assert.assertEquals(add_Manual.listOfHolding().get(i).getText().trim(), holding[i],
					"all holding type should displyed");
		}
	}

	@Test(description = "AT-68552:verify all lot type in add manual transaction", priority = 55, groups = { "Smoke" })
	public void VerifyLotType() throws Exception {
		SeleniumUtil.click(add_Manual.lot());
		String holding[] = PropsUtil.getDataPropertyValue("TransactionInvetsmentManualLot").split("'");
		for (int i = 0; i < add_Manual.lotList().size(); i++) {
			Assert.assertEquals(add_Manual.lotList().get(i).getText().trim(), holding[i],
					"all holding type should displyed");
		}
	}

	@Test(description = "AT-68580:verufy all invetsment transaction all amount field error message", priority = 56, groups = {
			"Smoke" })
	public void VerifyInvestmentTypeAllAmountFieldError() throws Exception {
		add_Manual.amount().clear();
		add_Manual.amount().sendKeys(PropsUtil.getDataPropertyValue("TransactionInvestmentManualInvalidAmount"));
		add_Manual.price().clear();
		add_Manual.price().sendKeys(PropsUtil.getDataPropertyValue("TransactionInvestmentManualInvalidAmount"));
		add_Manual.quantity().clear();
		add_Manual.quantity().sendKeys(PropsUtil.getDataPropertyValue("TransactionInvestmentManualInvalidAmount"));
		add_Manual.amount().sendKeys(Keys.TAB);
		Assert.assertEquals(add_Manual.priceErr().getText(),
				PropsUtil.getDataPropertyValue("TransactionInvestmentManualInvalidAmountError"),
				"verify investment amount field error");
		Assert.assertEquals(add_Manual.amountErr().getText(),
				PropsUtil.getDataPropertyValue("TransactionInvestmentManualInvalidAmountError"),
				"verify investment amount field error");
		Assert.assertEquals(add_Manual.quantityErr().getText(),
				PropsUtil.getDataPropertyValue("TransactionInvestmentManualInvalidAmountError"),
				"verify investment amount field error");
	}

	@Test(description = "AT-68708:split transaction for supported account", priority = 57, groups = { "Smoke" })
	public void VerifySplitForCardBankInvestMentLoanInsurance() throws Exception {
		SeleniumUtil.click(add_Manual.close());
		SeleniumUtil.click(aggregatedTransaction.PendingStranctionList().get(0));
		SeleniumUtil.click(aggregatedTransaction.ShowMore());
		boolean bankAccountSplit = split.SplitLink().isDisplayed();
		SeleniumUtil.click(aggregatedTransaction.PendingStranctionList().get(1));
		SeleniumUtil.click(aggregatedTransaction.ShowMore());
		boolean cardAccountSplit = split.SplitLink().isDisplayed();
		for (int i = 0; i < aggregatedTransaction.PendingStranctionList().size(); i++) {
			if (add_Manual.getAllPostedAmount1().get(i).getText()
					.equals(PropsUtil.getDataPropertyValue("TransactionCardAccount"))) {
				SeleniumUtil.click(add_Manual.getAllPostedAmount1().get(i));
				break;
			}
		}
		SeleniumUtil.click(aggregatedTransaction.ShowMore());
		boolean InvetsmentAccountSplit = split.SplitLink().isDisplayed();
		Assert.assertTrue(bankAccountSplit);
		Assert.assertTrue(cardAccountSplit);
		Assert.assertTrue(InvetsmentAccountSplit);
	}

	@Test(description = "AT-68708:verify series transaction is updated when series transaction updated with date ", priority = 58, groups = {
			"Smoke" })
	public void VerifyUpdateSriesTransactionDateUpdated() throws Exception {
		SeleniumUtil.click(add_Manual.addManualLink());
		add_Manual.createTransactionWithRecuralldetailsInvestorAndAdvisor(
				PropsUtil.getDataPropertyValue("TransactionSeriesManualTransactionDateUpdate"),
				PropsUtil.getDataPropertyValue("TransactionSeriesManualTransactionDescription"), 1, 1, 1, 30, 1, 1,
				PropsUtil.getDataPropertyValue("TransactionSeriesManualTransactionTag"),
				PropsUtil.getDataPropertyValue("TransactionSeriesManualTransactionNote1"));
		layout.serachTransaction(add_Manual.targateDate1(0), add_Manual.targateDate1(7));
		int transactionSize = readOnly.getAllAmount1().size();
		for (int i = 0; i < transactionSize; i++) {
			if (readOnly.getAllAmount1().get(i).getText().trim()
					.equals(PropsUtil.getDataPropertyValue("TransactionSeriesManualTransactionDateUpdate1"))) {
				SeleniumUtil.click(readOnly.getAllAmount1().get(i));
				SeleniumUtil.waitUntilSpinnerWheelIsInvisible();
				SeleniumUtil.waitFor(1);
				break;
			}
		}
		SeleniumUtil.click(seriesTransaction.allradioButton());
		seriesTransaction.Scheduledate().clear();
		seriesTransaction.Scheduledate().sendKeys(add_Manual.targateDate1(8));
		seriesTransaction.enddate_SRT().clear();
		seriesTransaction.enddate_SRT().sendKeys(add_Manual.targateDate1(31));
		SeleniumUtil.click(seriesTransaction.updateTransaction());
		SeleniumUtil.click(seriesTransaction.STUpdatePopUpConfirm());
		layout.serachTransaction(add_Manual.targateDate1(0), add_Manual.targateDate1(8));
		int transactionSize1 = readOnly.getAllAmount1().size();
		boolean firstSeries = false;
		for (int i = 0; i < transactionSize1; i++) {
			if (readOnly.getAllAmount1().get(i).getText().trim()
					.equals(PropsUtil.getDataPropertyValue("TransactionSeriesManualTransactionDateUpdate1"))) {
				firstSeries = true;
				break;
			}
		}
		layout.serachTransaction(add_Manual.targateDate1(0), add_Manual.targateDate1(15));
		int transactionSize2 = readOnly.getAllAmount1().size();
		boolean secondSeries = false;
		for (int i = 0; i < transactionSize2; i++) {
			if (readOnly.getAllAmount1().get(i).getText().trim()
					.equals(PropsUtil.getDataPropertyValue("TransactionSeriesManualTransactionDateUpdate1"))) {
				secondSeries = true;
				break;
			}
		}
		Assert.assertTrue(firstSeries, "first new series");
		Assert.assertTrue(secondSeries, "second new series");
	}
}