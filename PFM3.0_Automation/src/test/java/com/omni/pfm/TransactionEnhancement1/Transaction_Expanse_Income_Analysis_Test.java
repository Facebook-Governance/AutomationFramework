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
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.accessibility.examples.testng.webdriver.test.RunAccessibilityTest;
import com.omni.pfm.PageProcessor.PageParser;
import com.omni.pfm.pages.AccountAddition.AccountAddition;
import com.omni.pfm.pages.TransactionEnhancement1.Add_Manual_Transaction_Loc;
import com.omni.pfm.pages.TransactionEnhancement1.Aggregated_Transaction_Details_Loc;
import com.omni.pfm.pages.TransactionEnhancement1.Transaction_AccountDropDown_Loc;
import com.omni.pfm.pages.TransactionEnhancement1.Transaction_Attachment_Loc;
import com.omni.pfm.pages.TransactionEnhancement1.Transaction_Budget_Integration_Loc;
import com.omni.pfm.pages.TransactionEnhancement1.Transaction_Expanse_Income_Analysis_Loc;
import com.omni.pfm.pages.TransactionEnhancement1.Transaction_Split_Locator;
import com.omni.pfm.testBase.TestBase;
import com.omni.pfm.utility.PropsUtil;
import com.omni.pfm.utility.SeleniumUtil;

public class Transaction_Expanse_Income_Analysis_Test extends TestBase {
	private static final Logger logger = LoggerFactory.getLogger(AddToCalendar_Transaction_Test.class);
	Transaction_Expanse_Income_Analysis_Loc expanseIncome;
	Aggregated_Transaction_Details_Loc Aggre_Tranc_details;
	Transaction_Budget_Integration_Loc budget;
	Add_Manual_Transaction_Loc manual;
	Transaction_Attachment_Loc attachment;
	Transaction_Split_Locator split;
	AccountAddition accountAdd;
	Transaction_AccountDropDown_Loc accountDropDown;

	@BeforeClass(alwaysRun = true)
	public void testInit() throws InterruptedException {
		doInitialization("Expanse income Transaction Integration");
		TestBase.tc = TestBase.extent.startTest("Login", "Login");
		TestBase.test.appendChild(TestBase.tc);
		expanseIncome = new Transaction_Expanse_Income_Analysis_Loc(d);
		Aggre_Tranc_details = new Aggregated_Transaction_Details_Loc(d);
		budget = new Transaction_Budget_Integration_Loc(d);
		manual = new Add_Manual_Transaction_Loc(d);
		attachment = new Transaction_Attachment_Loc(d);
		split = new Transaction_Split_Locator(d);
		accountAdd = new AccountAddition();
		accountDropDown = new Transaction_AccountDropDown_Loc(d);
	}

	@Test(description = "AT-68811:verify transaction description in expense finapp", priority = 2, groups = { "Smoke" })
	public void verifyExpenseDescription() throws Exception {
		SeleniumUtil.refresh(d);
		PageParser.forceNavigate("Expense", d);
		expanseIncome.clickThisMonthTransaction();
		expanseIncome.clickEATxn(0, PropsUtil.getDataPropertyValue("Txn_EA_HLCCAtegory"),
				PropsUtil.getDataPropertyValue("TranExpansesearch"));
		expanseIncome.editExpanseTransaction(PropsUtil.getDataPropertyValue("TranExpanseDescriptionUpdate"), 7, 1,
				PropsUtil.getDataPropertyValue("TranExpanseNoteUpdate"),
				PropsUtil.getDataPropertyValue("TranExpanseTagUpdate"));
		PageParser.forceNavigate("Transaction", d);
		for (int i = 0; i < manual.getAllPostedAmount1().size(); i++) {
			if (manual.getAllPostedAmount1().get(i).getText().trim()
					.equals(PropsUtil.getDataPropertyValue("TranExpansesearch"))) {
				SeleniumUtil.click(manual.getAlldescription1().get(i));
				break;
			}
		}
		Assert.assertEquals(Aggre_Tranc_details.descField().getAttribute("value"),
				PropsUtil.getDataPropertyValue("TranExpanseDescriptionUpdate"));
	}

	@Test(description = "AT-68291:verify transaction tag in expense finapp", priority = 3)
	public void editExpanseTransactionVerifyInTransaction2() throws ParseException {
		Assert.assertTrue(Aggre_Tranc_details.AggregatedListTag().get(0).getText()
				.equalsIgnoreCase(PropsUtil.getDataPropertyValue("TranExpanseTagUpdate")));
	}

	@Test(description = "verify transaction note in expense finapp", priority = 4)
	public void editExpanseTransactionVerifyInTransaction3() throws ParseException {
		SeleniumUtil.click(Aggre_Tranc_details.ShowMore());
		SeleniumUtil.waitForPageToLoad(2000);
		Assert.assertEquals(Aggre_Tranc_details.note().getText(),
				PropsUtil.getDataPropertyValue("TranExpanseNoteUpdate"));
	}

	@Test(description = "AT-68812:verify transaction decription in transaction finap when transatcion updated in expense finapp", priority = 5)
	public void verifyeditedExpanseDescription() throws ParseException, AWTException {
		Aggre_Tranc_details.EditTransactionIntRansactionPageExpanse();
		SeleniumUtil.refresh(d);
		PageParser.forceNavigate("Expense", d);
		expanseIncome.clickEATxn(0, PropsUtil.getDataPropertyValue("Txn_EA_HLCCAtegory"),
				PropsUtil.getDataPropertyValue("Tranincomesearch3"));
		Assert.assertEquals(expanseIncome.incomdesc().getAttribute("value"),
				PropsUtil.getDataPropertyValue("TraBudgetDescValue1"));
	}

	@Test(description = "AT-68812:verify transaction tag in transaction finap when transatcion updated in expense finapp", priority = 6)
	public void verifyeditedExpanseTag() throws ParseException, AWTException {
		Assert.assertTrue(Aggre_Tranc_details.AggregatedListTag().get(0).getText()
				.equalsIgnoreCase(PropsUtil.getDataPropertyValue("TranBudgetTag1")));
	}

	@Test(description = "AT-68812:verify transaction note in transaction finap when transatcion updated in expense finapp", priority = 7)
	public void verifyeditedExpanseNote() throws ParseException, AWTException {
		SeleniumUtil.click(Aggre_Tranc_details.ShowMore());
		SeleniumUtil.waitForPageToLoad(2000);
		Assert.assertEquals(Aggre_Tranc_details.note().getText(), PropsUtil.getDataPropertyValue("TranBudgetNote1"));
	}

	@Test(description = "AT-68812:verify transaction attcahment in transaction finap when transatcion updated in expense finapp", priority = 8)
	public void verifyeditedExpanseAttachment() throws ParseException, AWTException {
		Assert.assertTrue(expanseIncome.expanseAttachment().get(0).isDisplayed());
	}

	@Test(description = "AT-68812:verify transaction split in transaction finap when transatcion updated in expense finapp", priority = 9)
	public void verifyeditedExpanseSplit() throws ParseException, AWTException {
		Assert.assertEquals(expanseIncome.expanseSplit().getText(),
				PropsUtil.getDataPropertyValue("ExpanseManageSplitText"));
	}

	@Test(description = "AT-68812:verify plited transaction in transaction finap when transatcion updated in expense finapp", priority = 10)
	public void verifyeditedExpanseSplitedTransaction() throws ParseException, AWTException {
		System.out.println(expanseIncome.expanseSplitIcon().size());
		if (expanseIncome.expanseSplitIcon().size() != 2) {
			Assert.assertTrue(false);
		}
	}

	@Test(description = "verify transaction description in income finapp", priority = 11, groups = { "Smoke" })
	public void verifIncomeDescrption() throws Exception {
		SeleniumUtil.refresh(d);
		PageParser.forceNavigate("Expense", d);
		expanseIncome.clickIAMonthRow(0);
		String cateName = null;
		if (PropsUtil.getEnvPropertyValue("CategoryList43").equalsIgnoreCase("yes")) {
			cateName = PropsUtil.getDataPropertyValue("Txn_IA_HLCCategory_43");
		} else {
			cateName = PropsUtil.getDataPropertyValue("Txn_IA_HLCCategory");
		}
		expanseIncome.clickEATxn(cateName, PropsUtil.getDataPropertyValue("Tranincomesearch"));
		expanseIncome.editExpanseTransaction(PropsUtil.getDataPropertyValue("IncomeTranDescriptionUpdate"), 5, 1,
				PropsUtil.getDataPropertyValue("TranExpanseNoteUpdate"),
				PropsUtil.getDataPropertyValue("TranExpanseTagUpdate"));
		PageParser.forceNavigate("Transaction", d);
		for (int i = 0; i < manual.getAllPostedAmount1().size(); i++) {
			if (manual.getAllPostedAmount1().get(i).getText().trim()
					.equals(PropsUtil.getDataPropertyValue("Tranincomesearch"))) {
				SeleniumUtil.click(manual.getAlldescription1().get(i));
				break;
			}
		}
		Assert.assertEquals(Aggre_Tranc_details.descField().getAttribute("value"),
				PropsUtil.getDataPropertyValue("IncomeTranDescriptionUpdate"));
	}

	@Test(description = "verify transaction tag in income finapp", priority = 12)
	public void editIncomeTransactionVerifyInTransaction2() throws ParseException {
		Assert.assertTrue(Aggre_Tranc_details.AggregatedListTag().get(0).getText()
				.equalsIgnoreCase(PropsUtil.getDataPropertyValue("TranExpanseTagUpdate")));
	}

	@Test(description = "verify transaction note in income finapp", priority = 13)
	public void editIncomeTransactionVerifyInTransaction3() throws ParseException {
		SeleniumUtil.click(Aggre_Tranc_details.ShowMore());
		SeleniumUtil.waitForPageToLoad(2000);
		Assert.assertEquals(Aggre_Tranc_details.note().getText(),
				PropsUtil.getDataPropertyValue("TranExpanseNoteUpdate"));
	}

	@Test(description = "AT-68812:verify transaction description in transaction finapp when transaction updated in income finapp", priority = 14)
	public void verifyeditedIncomeDescription() throws ParseException, AWTException {
		Aggre_Tranc_details.EditIncomeTransactionInTransactionPage();
		SeleniumUtil.refresh(d);
		PageParser.forceNavigate("Expense", d);
		expanseIncome.clickIAMonthRow(0);
		String cateName = null;
		if (PropsUtil.getEnvPropertyValue("CategoryList43").equalsIgnoreCase("yes")) {
			cateName = PropsUtil.getDataPropertyValue("Txn_IA_HLCCategory_43");
		} else {
			cateName = PropsUtil.getDataPropertyValue("Txn_IA_HLCCategory");
		}
		expanseIncome.clickEATxn(cateName, PropsUtil.getDataPropertyValue("Tranincomesearch4"));
		Assert.assertEquals(expanseIncome.incomdesc().getAttribute("value"),
				PropsUtil.getDataPropertyValue("TraBudgetDescValue2"));
	}

	@Test(description = "AT-68812:verify transaction tag in transaction finapp when transaction updated in income finapp", priority = 15)
	public void verifyeditedIncomeTag() throws ParseException, AWTException {
		Assert.assertTrue(Aggre_Tranc_details.AggregatedListTag().get(0).getText()
				.equalsIgnoreCase(PropsUtil.getDataPropertyValue("TranBudgetTag1")));
	}

	@Test(description = "AT-68812:verify transaction note in transaction finapp when transaction updated in income finapp", priority = 16)
	public void verifyeditedIncomeNote() throws ParseException, AWTException {
		SeleniumUtil.click(Aggre_Tranc_details.ShowMore());
		SeleniumUtil.waitForPageToLoad(2000);
		Assert.assertEquals(Aggre_Tranc_details.note().getText(), PropsUtil.getDataPropertyValue("TranBudgetNote1"));
	}

	@Test(description = "AT-68812;verify transaction attachment in transaction finapp when transaction updated in income finapp", priority = 17)
	public void verifyeditedIncomeAttachment() throws ParseException, AWTException {
		Assert.assertTrue(expanseIncome.expanseAttachment().get(0).isDisplayed());
	}

	@Test(description = "AT-68812:verify transaction split in transaction finapp when transaction updated in income finapp", priority = 18)
	public void verifyeditedIncomeSplit() throws ParseException, AWTException {
		Assert.assertEquals(expanseIncome.expanseSplit().getText(),
				PropsUtil.getDataPropertyValue("ExpanseManageSplitText"));
	}

	@Test(description = "AT-68812:verify splited transaction in transaction finapp when transaction updated in income finapp", priority = 19)
	public void verifyeditedIncomeSplitedTransaction() throws ParseException, AWTException {
		System.out.println(expanseIncome.expanseSplitIcon().size());
		if (expanseIncome.expanseSplitIcon().size() != 2) {
			Assert.assertTrue(false);
		}
	}

	@Test(description = "AT-68810:verify added expense time transaction updated in expense finapp", priority = 20)
	public void verifyAddedManualTransactionInExpanse() throws ParseException, AWTException {
		PageParser.forceNavigate("Transaction", d);
		SeleniumUtil.click(manual.addManualLink());
		SeleniumUtil.waitForPageToLoad(2000);
		if (PropsUtil.getEnvPropertyValue("CategoryList43").equalsIgnoreCase("yes")) {
			manual.createTransactionWithRecuralldetails(PropsUtil.getDataPropertyValue("TranExpanseManualAmount1"),
					PropsUtil.getDataPropertyValue("TranBudgetManualDescription"), 1, 1, 0, 7, 10, 3,
					PropsUtil.getDataPropertyValue("TranBudgetManualNote"));
		} else {
			manual.createTransactionWithRecuralldetails(PropsUtil.getDataPropertyValue("TranExpanseManualAmount1"),
					PropsUtil.getDataPropertyValue("TranBudgetManualDescription"), 1, 1, 0, 7, 10, 5,
					PropsUtil.getDataPropertyValue("TranBudgetManualNote"));
		}
		SeleniumUtil.waitForPageToLoad(2000);
		SeleniumUtil.click(manual.addManualLink());
		if (PropsUtil.getEnvPropertyValue("CategoryList43").equalsIgnoreCase("yes")) {
			manual.createTransactionWithRecuralldetails(PropsUtil.getDataPropertyValue("TranIncomeManualAmount1"),
					PropsUtil.getDataPropertyValue("TranBudgetManualDescription"), 1, 1, 0, 7, 6, 1,
					PropsUtil.getDataPropertyValue("TranBudgetManualNote"));
		} else {
			manual.createTransactionWithRecuralldetails(PropsUtil.getDataPropertyValue("TranIncomeManualAmount1"),
					PropsUtil.getDataPropertyValue("TranBudgetManualDescription"), 1, 1, 0, 7, 6, 2,
					PropsUtil.getDataPropertyValue("TranBudgetManualNote"));
		}
		PageParser.forceNavigate("Expense", d);
		SeleniumUtil.click(expanseIncome.listmoth().get(0));
		SeleniumUtil.waitForPageToLoad(10000);
		boolean expanseTrue = false;
		for (int i = 0; i < expanseIncome.tranamount1().size(); i++) {
			if (expanseIncome.tranamount1().get(i).getText()
					.equals(PropsUtil.getDataPropertyValue("TranExpanseManualAmount2"))) {
				expanseTrue = true;
			}
		}
		Assert.assertTrue(expanseTrue);
	}

	@Test(description = "AT-68810:verify added transaction is updated in income finapp", priority = 21)
	public void verifyAddedManualTransactionInIncome() throws ParseException, AWTException {
		PageParser.forceNavigate("Expense", d);
		SeleniumUtil.click(expanseIncome.exp());
		SeleniumUtil.waitForPageToLoad(1000);
		SeleniumUtil.scrollElementIntoView(d, expanseIncome.incomeAys(), true);
		SeleniumUtil.click(expanseIncome.incomeAys());
		SeleniumUtil.click(expanseIncome.listmoth().get(0));
		SeleniumUtil.click(expanseIncome.excatList().get(0));
		boolean incomeTrue = false;
		for (int i = 0; i < expanseIncome.tranamount1().size(); i++) {
			if (expanseIncome.tranamount1().get(i).getText()
					.equals(PropsUtil.getDataPropertyValue("TranIncomeManualAmount2"))) {
				incomeTrue = true;
			}
		}
		Assert.assertTrue(incomeTrue);
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
