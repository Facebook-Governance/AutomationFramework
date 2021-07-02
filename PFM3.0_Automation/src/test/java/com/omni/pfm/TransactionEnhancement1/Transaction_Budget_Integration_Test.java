/**
* Copyright (c) 2018 Yodlee Inc. All Rights Reserved.
*
* This software is the confidential and proprietary information of
* Yodlee, Inc. Use is subject to license terms.
*
* @author Renuka K
*/
package com.omni.pfm.TransactionEnhancement1;

import static org.testng.Assert.fail;

import java.awt.AWTException;
import java.text.ParseException;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.accessibility.examples.testng.webdriver.test.RunAccessibilityTest;
import com.omni.pfm.PageProcessor.PageParser;
import com.omni.pfm.pages.AccountAddition.AccountAddition;
import com.omni.pfm.pages.Login.LoginPage;
import com.omni.pfm.pages.TransactionEnhancement1.AddToCalendar_Transaction_Loc;
import com.omni.pfm.pages.TransactionEnhancement1.Add_Manual_Transaction_Loc;
import com.omni.pfm.pages.TransactionEnhancement1.Aggregated_Transaction_Details_Loc;
import com.omni.pfm.pages.TransactionEnhancement1.Series_Recurence_Transaction_Loc;
import com.omni.pfm.pages.TransactionEnhancement1.Transaction_AccountDropDown_Loc;
import com.omni.pfm.pages.TransactionEnhancement1.Transaction_Attachment_Loc;
import com.omni.pfm.pages.TransactionEnhancement1.Transaction_Budget_Integration_Loc;
import com.omni.pfm.pages.TransactionEnhancement1.Transaction_Layout_Loc;
import com.omni.pfm.pages.TransactionEnhancement1.Transaction_Split_Locator;
import com.omni.pfm.testBase.TestBase;
import com.omni.pfm.utility.PropsUtil;
import com.omni.pfm.utility.SeleniumUtil;

public class Transaction_Budget_Integration_Test extends TestBase {

	AddToCalendar_Transaction_Loc AddToCalendar;
	Aggregated_Transaction_Details_Loc aggre_Tranc_details;
	Transaction_Budget_Integration_Loc budget;
	Transaction_Split_Locator split;
	Add_Manual_Transaction_Loc manual;
	Transaction_Layout_Loc layout;
	Transaction_Attachment_Loc attachment;
	Series_Recurence_Transaction_Loc series_Transaction;
	int noOfwithrow;
	AccountAddition accountAdd;
	LoginPage login;
	Transaction_AccountDropDown_Loc accountDropDown;
	public static String userName = null;

	@BeforeClass(alwaysRun = true)

	public void testInit() throws Exception {
		doInitialization("Add Manual Transaction");

		TestBase.tc = TestBase.extent.startTest("Login", "Login");
		TestBase.test.appendChild(TestBase.tc);

		AddToCalendar = new AddToCalendar_Transaction_Loc(d);

		series_Transaction = new Series_Recurence_Transaction_Loc(d);
		accountAdd = new AccountAddition();
		aggre_Tranc_details = new Aggregated_Transaction_Details_Loc(d);
		budget = new Transaction_Budget_Integration_Loc(d);
		manual = new Add_Manual_Transaction_Loc(d);
		split = new Transaction_Split_Locator(d);
		layout = new Transaction_Layout_Loc(d);
		attachment = new Transaction_Attachment_Loc(d);
		login = new LoginPage();
		accountDropDown = new Transaction_AccountDropDown_Loc(d);
		d.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);

		LoginPage.loginMain(d, loginParameter);
		userName = config.userName;

		SeleniumUtil.waitForPageToLoad(4000);
		accountAdd.linkAccount();
		accountAdd.addAggregatedAccount1("BEI.site16441.3", "site16441.3");
		SeleniumUtil.waitForPageToLoad(4000);
	}

	@Test(description = "AT-68885:verify budget is created", priority = 2)

	public void createbudget() {
		PageParser.forceNavigate("Budget", d);
		Assert.assertTrue(budget.creatBudget());

	}

	@Test(description = "AT-68885,AT-68891:verify only this month transaction are displaying", priority = 3, dependsOnMethods = "createbudget")

	public void checkThisMonthTransaction() throws ParseException {
		SeleniumUtil.waitForPageToLoad();
		PageParser.forceNavigate("Budget", d);
		SeleniumUtil.waitForPageToLoad(7000);
		budget.clickBugtCategory(PropsUtil.getDataPropertyValue("Transaction_Budget_CategoryName1"));
		Assert.assertTrue(budget.CurrentmonthTransaction());

	}

	@Test(description = "AT-68885:verify transaction amount field", priority = 4, dependsOnMethods = "checkThisMonthTransaction")
	public void verifyTranAmountField() throws ParseException {
		budget.clickBugtTxnRow(PropsUtil.getDataPropertyValue("TraBudgetAmount"));
		SeleniumUtil.waitForPageToLoad();
		Assert.assertEquals(budget.budgetAmount().getText(), PropsUtil.getDataPropertyValue("TraBudgetAmount"));
		Assert.assertEquals(budget.budgetAmountLable().getText(),
				PropsUtil.getDataPropertyValue("TraBudgetAmountLable"));

	}

	@Test(description = "AT-68886:verify transaction Description filed", priority = 5, dependsOnMethods = "verifyTranAmountField")
	public void verifyTranDescriptionField() throws ParseException {
		Assert.assertEquals(budget.budgetDescription().getAttribute("value"),
				PropsUtil.getDataPropertyValue("TraBudgetDescValue"));
		Assert.assertEquals(budget.budgetDescriptionLable().getText(),
				PropsUtil.getDataPropertyValue("TraBudgetDescLable"));
	}

	@Test(description = "AT-68886:verify Transaction Simple description", priority = 6, dependsOnMethods = "verifyTranAmountField")
	public void verifyTranSimpleDescriptionField() throws ParseException {
		Assert.assertEquals(budget.budgetSimpleDescvalue().getText(),
				PropsUtil.getDataPropertyValue("TraBudgetSimpleDescValue"));
		Assert.assertEquals(budget.budgetSimpleLable().getText(),
				PropsUtil.getDataPropertyValue("TraBudgetSimpleDescLable"));
	}

	@Test(description = "AT-68886:verify transaction account field", priority = 7, dependsOnMethods = "verifyTranAmountField")
	public void verifyTranDabitedField() throws ParseException {
		Assert.assertEquals(budget.budgetDabitedValue().getText(),
				PropsUtil.getDataPropertyValue("TraBudgetDabitedValue"));
		Assert.assertEquals(budget.budgetDabitedLable().getText(),
				PropsUtil.getDataPropertyValue("TraBudgetDabitedLable"));
	}

	@Test(description = "AT-68886:verify transaction Date field", priority = 8, dependsOnMethods = "verifyTranAmountField")
	public void verifyTranDateField() throws ParseException {
		Assert.assertEquals(budget.budgetDateValue().getText(), AddToCalendar.DateFormate(0));
		Assert.assertEquals(budget.budgetDateLable().getText(), PropsUtil.getDataPropertyValue("TraBudgetDateLable"));
	}

	@Test(description = "AT-68886:verify transaction category field", priority = 9, dependsOnMethods = "verifyTranAmountField")
	public void verifyTrancatField() throws ParseException {
		Assert.assertEquals(budget.budgetCatValue().getText(), PropsUtil.getDataPropertyValue("TraBudgetCatValue"));
		Assert.assertEquals(budget.budgetcatLable().getText(), PropsUtil.getDataPropertyValue("TraBudgetCatLable"));
	}

	@Test(description = "AT-68886:verift transaction tag field", priority = 10, dependsOnMethods = "verifyTranAmountField")
	public void verifyTranTagField() throws ParseException {
		Assert.assertTrue(budget.budgetTagField().isDisplayed());
	}

	@Test(description = "AT-68886:verify transaction note field", priority = 11, dependsOnMethods = "verifyTranAmountField")
	public void verifyTranNoteField() throws ParseException {
		SeleniumUtil.click(budget.budgetShowMore());
		SeleniumUtil.waitForPageToLoad(1000);
		Assert.assertTrue(budget.detailsnoteField().isDisplayed());
	}

	@Test(description = "AT-68886:verify transaction attachment field ", priority = 12, dependsOnMethods = "verifyTranNoteField")
	public void verifyAttachment() throws ParseException {
		Assert.assertTrue(budget.budgetAttachmentLable().isDisplayed());
	}

	@Test(description = "AT-68894,AT-68892:verify transaction detele option", priority = 13, dependsOnMethods = "verifyTranNoteField")
	public void verifyDeleteOption() throws ParseException {

		boolean deleteOptionfalse = false;
		if (series_Transaction.deleteTransactionList().size() == 0) {
			deleteOptionfalse = true;
		}
		Assert.assertTrue(deleteOptionfalse);
	}

	@Test(description = "AT-68894,AT-68892:verify transaction split option", priority = 14, dependsOnMethods = "verifyTranNoteField")
	public void verifySplitOption() throws ParseException {
		boolean splitOptionfalse = false;
		if (split.SplitIconList().size() == 0) {
			splitOptionfalse = true;
		}
		Assert.assertTrue(splitOptionfalse);
	}

	@Test(description = "AT-68894,AT-68892:verify transaction add to cal filed", priority = 15, dependsOnMethods = "verifyTranAmountField")
	public void verifyAddToCalOption() throws ParseException {
		boolean addToCalandertOptionfalse = false;
		if (AddToCalendar.addcalLinkList().size() == 0) {
			addToCalandertOptionfalse = true;
		}
		Assert.assertTrue(addToCalandertOptionfalse);

	}

	@Test(description = "AT-68893: vrify edited transaction description row value ", priority = 16, dependsOnMethods = "verifyTranAmountField")
	public void txnDescriptionRowValue() throws ParseException {
		// SeleniumUtil.click(budget.transactionCustomDateFilterClose());

		budget.editBudgetTransaction();
		SeleniumUtil.waitForPageToLoad(5000);
		PageParser.forceNavigate("Transaction", d);
		SeleniumUtil.waitForPageToLoad(7000);
		String description = null;
		// layout.serachTransaction(AddToCalendar.DateFormate(0),
		// AddToCalendar.DateFormate(0));
		try {
			accountDropDown.clickOnShowMoreButton();
			SeleniumUtil.waitForPageToLoad(7000);
		} catch (Exception e) {

		}
		int TxnSize = manual.getAllPostedAmount1().size();
		for (int i = 0; i < TxnSize; i++) {
			if (manual.getAllPostedAmount1().get(i).getText().trim()
					.equals(PropsUtil.getDataPropertyValue("TranBudgeSeacrh"))) {
				description = manual.getAlldescription1().get(i).getText();
				SeleniumUtil.click(manual.getAlldescription1().get(i));
				break;
			}
		}
		SeleniumUtil.waitForPageToLoad(7000);
		Assert.assertEquals(description, PropsUtil.getDataPropertyValue("TraBudgetDescValue1"));

	}

	@Test(description = "AT-68893: verify edited transaction description field value", priority = 17, dependsOnMethods = "txnDescriptionRowValue")
	public void txnDescValue() throws ParseException {

		Assert.assertEquals(aggre_Tranc_details.descField().getAttribute("value"),
				PropsUtil.getDataPropertyValue("TraBudgetDescValue1"));
	}

	@Test(description = "AT-68893:verify edited transaction tag value", priority = 18, dependsOnMethods = "txnDescriptionRowValue")
	public void txnTagValue() throws ParseException {
		Assert.assertTrue(aggre_Tranc_details.AggregatedListTag().get(0).getText()
				.equalsIgnoreCase(PropsUtil.getDataPropertyValue("TranBudgetTag")));
	}

	@Test(description = "AT-68893:verify edited transaction note value", priority = 19, dependsOnMethods = "txnDescriptionRowValue")
	public void txnNoteValue() throws ParseException {
		SeleniumUtil.click(aggre_Tranc_details.ShowMore());
		SeleniumUtil.waitForPageToLoad(2000);
		Assert.assertEquals(aggre_Tranc_details.note().getText(), PropsUtil.getDataPropertyValue("TranBudgetNote"));
	}

	@Test(description = "AT-68888:verify splitted transaction", priority = 20, dependsOnMethods = "txnDescriptionRowValue")
	public void budgetTxnSplit() throws ParseException, AWTException {
		for (int i = 0; i < manual.getAllPostedAmount1().size(); i++) {
			if (manual.getAllPostedAmount1().get(i).getText().trim()
					.equals(PropsUtil.getDataPropertyValue("TranBudgeSeacrh2"))) {

				SeleniumUtil.click(manual.getAlldescription1().get(i));
				SeleniumUtil.waitForPageToLoad(2000);
				break;
			}
		}
		aggre_Tranc_details.EditTransactionInTransactionPage();
		SeleniumUtil.waitForPageToLoad(2000);
		PageParser.forceNavigate("Budget", d);
		SeleniumUtil.waitForPageToLoad();
		budget.clickBugtTxnRow(PropsUtil.getDataPropertyValue("Transaction_Budget_CategoryName1"), 3);
		SeleniumUtil.waitForPageToLoad(2000);
		Assert.assertTrue(budget.budgetSplitedRow().size() == 2);

	}

	@Test(description = "AT-68888:verify budget transaction description", priority = 21, dependsOnMethods = "budgetTxnSplit")
	public void budgetTxnDescription() throws ParseException, AWTException {

		Assert.assertEquals(budget.budgetDescription().getAttribute("value"),
				PropsUtil.getDataPropertyValue("TraBudgetDescValue1"));
	}

	@Test(description = "AT-68888,AT-68889:verify budget transaction Tag value", priority = 22, dependsOnMethods = "budgetTxnSplit")
	public void budgetTxnTag() throws ParseException, AWTException {
		Assert.assertTrue(aggre_Tranc_details.AggregatedListTag().get(0).getText()
				.equalsIgnoreCase(PropsUtil.getDataPropertyValue("TranBudgetTag1")));
	}

	@Test(description = "AT-68888,AT-68889,AT-68889:verify budgeted transaction note value", priority = 23, dependsOnMethods = "budgetTxnSplit")
	public void budgetTxnNote() throws ParseException, AWTException {
		SeleniumUtil.click(budget.budgetShowMore());
		SeleniumUtil.waitForPageToLoad(2000);
		Assert.assertEquals(budget.detailsnoteField().getAttribute("value"),
				PropsUtil.getDataPropertyValue("TranBudgetNote1"));
	}

	@Test(description = "AT-68888,AT-68889:verify bugeted transaction attachment file", priority = 24, dependsOnMethods = "budgetTxnNote")
	public void verifyBudgetAttachment() throws ParseException, AWTException {
		Assert.assertTrue(budget.budgetAttachedFile().isDisplayed());
	}

	@Test(description = "AT-68888,AT-68902:verify manaual transaction displaying in budget", priority = 25, dependsOnMethods = "createbudget")
	public void verifyAddedTransactionInBudget() throws ParseException, AWTException {
		d.navigate().refresh();
		SeleniumUtil.waitForPageToLoad();
		PageParser.forceNavigate("Transaction", d);
		SeleniumUtil.waitForPageToLoad();
		manual.clickMTLink();
		SeleniumUtil.waitForPageToLoad(2000);

		manual.createTransactionWithRecuralldetails(PropsUtil.getDataPropertyValue("TranBudgetManualAmount"),
				PropsUtil.getDataPropertyValue("TranBudgetManualDescription"),
				PropsUtil.getDataPropertyValue("Transaction_Budget_MT_Account"),
				PropsUtil.getDataPropertyValue("Transaction_Budget_MT_Frequency"), 0, 7,
				PropsUtil.getDataPropertyValue("Transaction_Budget_MT_Category"),
				PropsUtil.getDataPropertyValue("TranBudgetManualNote"));
		SeleniumUtil.waitForPageToLoad(1000);
		PageParser.forceNavigate("Budget", d);
		SeleniumUtil.waitForPageToLoad();
		budget.clickBugtCategory(PropsUtil.getDataPropertyValue("Transaction_Budget_CategoryName1"));
		Assert.assertEquals(budget.transactionrow().get(5).getText(),
				PropsUtil.getDataPropertyValue("TranBudgetManualAmount1"), "manula Transaction should dispalyed");
	}

	@Test(description = "AT-85031,AT-85032,AT-85037,AT-85043,AT-85045,AT-85048:verify search with start letter", priority = 97, dependsOnMethods = {
			"verifySearchHLCWithStartLetter" })
	public void bugtDtlSearchHLCWithStartLetter() throws Exception {
// search the string which is matching with HLC in Category filter
// filter.searchCategory(PropsUtil.getDataPropertyValue("TransactionCategoriesFilterCategorySearch"));
// Assert.assertEquals(filter.tranCatFltSearchedHLC.get(0).getText().trim(),PropsUtil.getDataPropertyValue("tanCatSearchedHLC1"),"searched
// MLC should dsipalyed");
		SeleniumUtil.refresh(d);
		PageParser.forceNavigate("Budget", d);
		budget.creatBudget();
		budget.clickBugtTxnRow(PropsUtil.getDataPropertyValue("Budget_Txn_CatSerach_BudgetCategory"), 0);
		aggre_Tranc_details.catgoryFieldClick();
		aggre_Tranc_details.tansactionDetailsCatSerach(
				PropsUtil.getDataPropertyValue("TransactionCategoriesFilterCategorySearch"));
		List<String> MLCName = SeleniumUtil.getWebElementsValue(aggre_Tranc_details.tranDtlCatSearchedMLC());
		List<String> HLCName = SeleniumUtil.getWebElementsValue(aggre_Tranc_details.tranDtlCatSearchedHLC());
		List<String> hilightedHLCName = SeleniumUtil.getWebElementsValue(aggre_Tranc_details.tranDtlCatSearchedHLCHL());
		List<String> hilightedHLCClassName = SeleniumUtil
				.getWebElementsAttributeName(aggre_Tranc_details.tranDtlCatSearchedMLCHL(), "class");
		SeleniumUtil.assertExpectedActualList(MLCName, PropsUtil.getDataPropertyValue("tanDetailsCatSearchedMLC1"),
				"search matches MLC should be displayed");
		SeleniumUtil.assertExpectedActualList(HLCName, PropsUtil.getDataPropertyValue("tanCatSearchedHLC1"),
				"search matches HLC should be displayed");
		SeleniumUtil.assertActualList(hilightedHLCName, PropsUtil.getDataPropertyValue("tanCatSearchedHLC1HL"),
				"search matches word should highlighed");
		SeleniumUtil.assertActualList(hilightedHLCClassName,
				PropsUtil.getDataPropertyValue("tanCatSearchedCategoryHLClass"), "class should highlighted");
	}

	@Test(description = "AT-85035:verify search with start letter", priority = 98, dependsOnMethods = {
			"bugtDtlSearchHLCWithStartLetter" })
	public void bugtDtlSearchMLCWithStartLetter() throws Exception {
		aggre_Tranc_details.tansactionDetailsCatSerach(
				PropsUtil.getDataPropertyValue("TransactionCategoriesFilterCategorySearc2"));
		List<String> MLCName = SeleniumUtil.getWebElementsValue(aggre_Tranc_details.tranDtlCatSearchedMLC());
		List<String> hilightedMLC = SeleniumUtil.getWebElementsValue(aggre_Tranc_details.tranDtlCatSearchedMLCHL());
		List<String> hilightedMLCClassName = SeleniumUtil
				.getWebElementsAttributeName(aggre_Tranc_details.tranDtlCatSearchedMLCHL(), "class");
		SeleniumUtil.assertExpectedActualList(MLCName, PropsUtil.getDataPropertyValue("tanCatSearchedMLC2"),
				"search matches MLC should be displayed");
		SeleniumUtil.assertActualList(hilightedMLC, PropsUtil.getDataPropertyValue("tanCatSearchedMLC2HL"),
				"search matches word should highlighed");
		SeleniumUtil.assertActualList(hilightedMLCClassName,
				PropsUtil.getDataPropertyValue("tanCatSearchedCategoryHLClass"), "class should highlighted");
	}

	@Test(description = "AT-85034:verify search with second word", priority = 99, dependsOnMethods = {
			"bugtDtlSearchHLCWithStartLetter" })
	public void bugtDtlSearchHLCWithsecondWord() throws Exception {
		aggre_Tranc_details.tansactionDetailsCatSerach(
				PropsUtil.getDataPropertyValue("TransactionCategoriesFilterSecondWordSearch"));
		List<String> MLCName = SeleniumUtil.getWebElementsValue(aggre_Tranc_details.tranDtlCatSearchedMLC());
		List<String> HLCName = SeleniumUtil.getWebElementsValue(aggre_Tranc_details.tranDtlCatSearchedHLC());
		List<String> hilightedHLCName = SeleniumUtil.getWebElementsValue(aggre_Tranc_details.tranDtlCatSearchedHLCHL());
		List<String> hilightedHLCClassName = SeleniumUtil
				.getWebElementsAttributeName(aggre_Tranc_details.tranDtlCatSearchedHLCHL(), "class");
		SeleniumUtil.assertExpectedActualList(MLCName,
				PropsUtil.getDataPropertyValue("TransactionCategoriesFilterSecondWordSearchMLC"),
				"search matches MLC should be displayed");
		SeleniumUtil.assertExpectedActualList(HLCName,
				PropsUtil.getDataPropertyValue("TransactionCategoriesFilterSecondWordSearchHLC"),
				"search matches HLC should be displayed");
		SeleniumUtil.assertActualList(hilightedHLCName,
				PropsUtil.getDataPropertyValue("TransactionCategoriesFilterSecondWordSearchHLText"),
				"search matches word should highlighed");
		SeleniumUtil.assertActualList(hilightedHLCClassName,
				PropsUtil.getDataPropertyValue("tanCatSearchedCategoryHLClass"), "class should highlighted");
	}

	@Test(description = "AT-85034,AT-85038,AT-85043:verify search with second word", priority = 100, dependsOnMethods = {
			"bugtDtlSearchHLCWithStartLetter" })
	public void bugtDtlSearchMLCWithsecondWord() throws Exception {
		aggre_Tranc_details.tansactionDetailsCatSerach(
				PropsUtil.getDataPropertyValue("TransactionCategoriesFilterMLCSecondWordSearch"));
		List<String> MLCName = SeleniumUtil.getWebElementsValue(aggre_Tranc_details.tranDtlCatSearchedMLC());
		List<String> HLCName = SeleniumUtil.getWebElementsValue(aggre_Tranc_details.tranDtlCatSearchedHLC());
		List<String> hilightedHLCClassName = SeleniumUtil
				.getWebElementsAttributeName(aggre_Tranc_details.tranDtlCatSearchedMLCHL(), "class");
		SeleniumUtil.assertExpectedActualList(MLCName,
				PropsUtil.getDataPropertyValue("TransactionCategoriesFilterMLCSecondWordSearchMLCValue"),
				"search matches MLC should be displayed");
		SeleniumUtil.assertExpectedActualList(HLCName,
				PropsUtil.getDataPropertyValue("TransactionCategoriesFilterMLCSecondWordSearchHLCValue"),
				"search matches HLC should be displayed");
		SeleniumUtil.assertActualList(hilightedHLCClassName,
				PropsUtil.getDataPropertyValue("tanCatSearchedCategoryHLClass"), "class should highlighted");
	}

	@Test(description = "AT-85034,AT-85038:verify search with second word", priority = 101, dependsOnMethods = {
			"bugtDtlSearchMLCWithsecondWord" })
	public void bugtDtlSearchSearchedHLMLCWith2ndWord() throws Exception {
		List<String> hilightedMLC = SeleniumUtil.getWebElementsValue(aggre_Tranc_details.tranDtlCatSearchedMLCHL());
		List<String> hilightedMLCClassName = SeleniumUtil
				.getWebElementsAttributeName(aggre_Tranc_details.tranDtlCatSearchedMLCHL(), "class");
		int seachedMLCSize = aggre_Tranc_details.tranDtlCatSearchedMLCHL().size();
		SeleniumUtil.assertActualList(hilightedMLC,
				PropsUtil.getDataPropertyValue("TransactionCategoriesFilterMLCSecondWordSearchMLCHLValue"),
				"search matches word should highlighed"); // Consulting
		SeleniumUtil.assertActualList(hilightedMLCClassName,
				PropsUtil.getDataPropertyValue("tanCatSearchedCategoryHLClass"), "class should highlighted");
		Assert.assertEquals(seachedMLCSize, 3, "hilighted Mlc size should be 3");
	}

	@Test(description = "AT-85036,AT-85033:verify search HLC with sb string", priority = 102, dependsOnMethods = {
			"bugtDtlSearchHLCWithStartLetter" })
	public void bugtDtlSearchHLCWithSubString() throws Exception {
		aggre_Tranc_details.tansactionDetailsCatSerach(
				PropsUtil.getDataPropertyValue("TransactionCategoriesFilterCategorySubStringHLC"));
		if (!(SeleniumUtil
				.getElementCount(SeleniumUtil.getByObject("Transaction", null, "tranDtlCatSearchedHLC")) == 0)) {
			fail("HLC Categories should not displayed which have searched sub String");
		}
		Assert.assertEquals(aggre_Tranc_details.tranDtlCatNocatAvailabel().getText().trim(),
				PropsUtil.getDataPropertyValue("tranCatFltNocatAvailabel"),
				"No Categories Available message should be displayed");
	}

	@Test(description = "AT-85036,AT-85033:verify Search MLC with substring", priority = 103, dependsOnMethods = {
			"bugtDtlSearchHLCWithStartLetter" })
	public void bugtDtlSearchMLCWithSubString() throws Exception {
		aggre_Tranc_details.tansactionDetailsCatSerach(
				PropsUtil.getDataPropertyValue("TransactionCategoriesFilterCategorySearch6"));
		if (!(SeleniumUtil
				.getElementCount(SeleniumUtil.getByObject("Transaction", null, "tranDtlCatSearchedHLC")) == 0)) {
			fail("HLC Categories should not displayed which have searched sub String");
		}
		if (!(SeleniumUtil
				.getElementCount(SeleniumUtil.getByObject("Transaction", null, "tranDtlCatSearchedMLC")) == 0)) {
			fail("MLC Categories should not displayed which have searched sub String");
		}
		Assert.assertEquals(aggre_Tranc_details.tranDtlCatNocatAvailabel().getText().trim(),
				PropsUtil.getDataPropertyValue("tranCatFltNocatAvailabel"),
				"No Categories Available message should be displayed");
	}

	@Test(description = "AT-85041:verify HLC with spec char", priority = 104, dependsOnMethods = {
			"bugtDtlSearchHLCWithStartLetter" })
	public void bugtDtlSearchHLCWithSpecChar() throws Exception {
		aggre_Tranc_details.tansactionDetailsCatSerach(
				PropsUtil.getDataPropertyValue("TransactionCategoriesFilterCategorySpecCharHLC"));
		List<String> HLCName = SeleniumUtil.getWebElementsValue(aggre_Tranc_details.tranDtlCatSearchedHLC());
		List<String> hilightedHLCName = SeleniumUtil.getWebElementsValue(aggre_Tranc_details.tranDtlCatSearchedMLCHL());
		List<String> hilightedHLCClassName = SeleniumUtil
				.getWebElementsAttributeName(aggre_Tranc_details.tranDtlCatSearchedMLCHL(), "class");
		SeleniumUtil.assertExpectedActualList(HLCName,
				PropsUtil.getDataPropertyValue("TransactionCategoriesFilterCategorySpecCharHLCValue"),
				"search matches HLC should be displayed");
		SeleniumUtil.assertActualList(hilightedHLCName,
				PropsUtil.getDataPropertyValue("TransactionCategoriesFilterCategorySpecCharHLC"),
				"search matches word should highlighed");
		SeleniumUtil.assertActualList(hilightedHLCClassName,
				PropsUtil.getDataPropertyValue("tanCatSearchedCategoryHLClass"), "class should highlighted");
	}

	@Test(description = "AT-85041:verify MLC wth spec char", priority = 105, dependsOnMethods = {
			"bugtDtlSearchHLCWithSpecChar" })
	public void bugtDtlMLCSearchHLCWithSpecChar() throws Exception {
		List<String> MLCName = SeleniumUtil.getWebElementsValue(aggre_Tranc_details.tranDtlCatSearchedMLC());
		SeleniumUtil.assertExpectedActualList(MLCName,
				PropsUtil.getDataPropertyValue("TtraDtlsCategorySpecCharMLCValue"),
				"search matches MLC should be displayed");
	}

	@Test(description = "AT-85041:verify search  HLC space ", priority = 106, dependsOnMethods = {
			"bugtDtlSearchHLCWithStartLetter" })
	public void bugtDtlSearchHLCWithSpace() throws Exception {
		aggre_Tranc_details.tansactionDetailsCatSerach(
				PropsUtil.getDataPropertyValue("TransactionCategoriesFilterCategorySearchWithTwoWordHLC"));
		List<String> MLCName = SeleniumUtil.getWebElementsValue(aggre_Tranc_details.tranDtlCatSearchedMLC());
		List<String> HLCName = SeleniumUtil.getWebElementsValue(aggre_Tranc_details.tranDtlCatSearchedHLC());
		List<String> hilightedHLCName = SeleniumUtil.getWebElementsValue(aggre_Tranc_details.tranDtlCatSearchedMLCHL());
		List<String> hilightedHLCClassName = SeleniumUtil
				.getWebElementsAttributeName(aggre_Tranc_details.tranDtlCatSearchedMLCHL(), "class");
		SeleniumUtil.assertExpectedActualList(MLCName,
				PropsUtil.getDataPropertyValue("TransactionCategoriesFilterCategorySearchWithTwoWordMLCValue"),
				"search matches MLC should be displayed");
		SeleniumUtil.assertExpectedActualList(HLCName,
				PropsUtil.getDataPropertyValue("TransactionCategoriesFilterCategorySearchWithTwoWordHLCValue"),
				"search matches HLC should be displayed");
		SeleniumUtil.assertActualListIgnoreCase(hilightedHLCName,
				PropsUtil.getDataPropertyValue("TransactionCategoriesFilterCategorySearchWithTwoWordHLCHL"),
				"search matches word should highlighed");
		SeleniumUtil.assertActualList(hilightedHLCClassName,
				PropsUtil.getDataPropertyValue("tanCatSearchedCategoryHLClass"), "class should highlighted");
	}

	@Test(description = "AT-85041:verify search MLC with space", priority = 107, dependsOnMethods = {
			"bugtDtlSearchHLCWithStartLetter" })
	public void bugtDtlSearchMLCWithSpace() throws Exception {
		aggre_Tranc_details.tansactionDetailsCatSerach(
				PropsUtil.getDataPropertyValue("TransactionCategoriesFilterCategorySearchWithTwoWordMLC"));
		List<String> MLCName = SeleniumUtil.getWebElementsValue(aggre_Tranc_details.tranDtlCatSearchedMLC());
		List<String> HLCName = SeleniumUtil.getWebElementsValue(aggre_Tranc_details.tranDtlCatSearchedHLC());
		List<String> hilightedMLC = SeleniumUtil.getWebElementsValue(aggre_Tranc_details.tranDtlCatSearchedMLCHL());
		List<String> hilightedMLCClassName = SeleniumUtil
				.getWebElementsAttributeName(aggre_Tranc_details.tranDtlCatSearchedMLCHL(), "class");
		SeleniumUtil.assertExpectedActualList(MLCName,
				PropsUtil.getDataPropertyValue("TransactionCategoriesFilterCategorySearchWithTwoWordMLCValue2"),
				"search matches MLC should be displayed");
		SeleniumUtil.assertExpectedActualList(HLCName,
				PropsUtil.getDataPropertyValue("TransactionCategoriesFilterCategorySearchWithTwoWordMLCValue1"),
				"search matches HLC should be displayed");
		SeleniumUtil.assertActualList(hilightedMLC,
				PropsUtil.getDataPropertyValue("TransactionCategoriesFilterCategorySearchWithTwoWordMLC"),
				"\"search matches word should highlighed");
		SeleniumUtil.assertActualList(hilightedMLCClassName,
				PropsUtil.getDataPropertyValue("tanCatSearchedCategoryHLClass"), "class should highlighted");
	}

	@Test(description = "AT-85039:search MLC with BackSlash", priority = 108, dependsOnMethods = {
			"bugtDtlSearchHLCWithStartLetter" })
	public void bugttlSearchMLCWithBackSlash() throws Exception {
		aggre_Tranc_details.tansactionDetailsCatSerach(
				PropsUtil.getDataPropertyValue("TransactionCategoriesFilterCategorySearchBackSlash"));
		List<String> MLCName = SeleniumUtil.getWebElementsValue(aggre_Tranc_details.tranDtlCatSearchedMLC());
		List<String> hilightedMLC = SeleniumUtil.getWebElementsValue(aggre_Tranc_details.tranDtlCatSearchedMLCHL());
		List<String> hilightedMLCClassName = SeleniumUtil
				.getWebElementsAttributeName(aggre_Tranc_details.tranDtlCatSearchedMLCHL(), "class");
		SeleniumUtil.assertExpectedActualList(MLCName,
				PropsUtil.getDataPropertyValue("TransactionCategoriesFilterCategorySearchBackSlashMLC"),
				"search matches MLC should be displayed");
		SeleniumUtil.assertActualList(hilightedMLC,
				PropsUtil.getDataPropertyValue("TransactionCategoriesFilterCategorySearchBackSlash"),
				"\"search matches word should highlighed");
		SeleniumUtil.assertActualList(hilightedMLCClassName,
				PropsUtil.getDataPropertyValue("tanCatSearchedCategoryHLClass"), "class should highlighted");
	}

	@Test(description = "AT-85039:verify MLC with Backslash", priority = 109, dependsOnMethods = {
			"bugttlSearchMLCWithBackSlash" })
	public void bugtDtlSearchMLCWithBackSlashHLC() throws Exception {
		List<String> HLCName = SeleniumUtil.getWebElementsValue(aggre_Tranc_details.tranDtlCatSearchedHLC());
		SeleniumUtil.assertExpectedActualList(HLCName,
				PropsUtil.getDataPropertyValue("TransactionCategoriesFilterCategorySearchBackSlashHLC"),
				"search matches HLC should be displayed");
	}

	@Test(description = "AT-85040:verify search with first letter", priority = 110, dependsOnMethods = {
			"bugtDtlSearchHLCWithStartLetter" })
	public void bugtDtlSearchWithFirstLater() throws Exception {
		aggre_Tranc_details.tansactionDetailsCatSerach(PropsUtil.getDataPropertyValue("tanCatSearchStartLeter"));
		System.out.println(aggre_Tranc_details.tranDtlCatSearchedHLCHL().size());
		Assert.assertEquals(aggre_Tranc_details.tranDtlCatSearchedHLCHL().size(), 1,
				"Hilighted element class should contain highlighter");
		Assert.assertEquals(aggre_Tranc_details.tranDtlCatSearchedMLCHL().size(), 12,
				"Hilighted element class should contain highlighter");
	}

	@Test(description = "AT-85049:verify search with AllCategories", priority = 111, dependsOnMethods = {
			"bugtDtlSearchHLCWithStartLetter" })
	public void bugtDtlSearchWithAllCategories() throws Exception {
		aggre_Tranc_details.tansactionDetailsCatSerach(
				PropsUtil.getDataPropertyValue("TransactionCategoriesFilterSearchWithAllCat"));
		Assert.assertEquals(aggre_Tranc_details.tranDtlCatNocatAvailabel().getText().trim(),
				PropsUtil.getDataPropertyValue("tranCatFltNocatAvailabel"),
				"No Categories Available message should be displayed");
	}

	@Test(description = "AT-85049:verify all category with none", priority = 112, dependsOnMethods = {
			"bugtDtlSearchHLCWithStartLetter" })
	public void bugtDtlSearchWithNone() throws Exception {
		aggre_Tranc_details.tansactionDetailsCatSerach(
				PropsUtil.getDataPropertyValue("TransactionCategoriesFilterSearchWithNone"));
		Assert.assertEquals(aggre_Tranc_details.tranDtlCatNocatAvailabel().getText().trim(),
				PropsUtil.getDataPropertyValue("tranCatFltNocatAvailabel"),
				"No Categories Available message should be displayed");
	}

	@AfterClass
	public void checkAccessibility() {
		try {
			RunAccessibilityTest rat = new RunAccessibilityTest(this.getClass().getName());
			rat.testAccessibility(d);

		} catch (Exception e) {

		}
	}
}
