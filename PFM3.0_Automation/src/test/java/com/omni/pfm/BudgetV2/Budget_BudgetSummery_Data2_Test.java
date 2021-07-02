/**
* Copyright (c) 2018 Yodlee Inc. All Rights Reserved.
*
* This software is the confidential and proprietary information of
* Yodlee, Inc. Use is subject to license terms.
*
* @author Renuka K
*/
package com.omni.pfm.BudgetV2;

import java.util.concurrent.TimeUnit;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.omni.pfm.PageProcessor.PageParser;
import com.omni.pfm.config.Config;
import com.omni.pfm.pages.AccountAddition.AccountAddition;
import com.omni.pfm.pages.BudgetV2.Budget_BudgetSummary_Loc;
import com.omni.pfm.pages.BudgetV2.Budget_CreateBudget_Loc;
import com.omni.pfm.pages.Login.LoginPage;
import com.omni.pfm.pages.TransactionEnhancement1.Add_Manual_Transaction_Loc;
import com.omni.pfm.testBase.TestBase;
import com.omni.pfm.utility.PropsUtil;
import com.omni.pfm.utility.SeleniumUtil;

public class Budget_BudgetSummery_Data2_Test extends TestBase {
	private static final Logger logger = LoggerFactory.getLogger(Budget_BudgetSummery_Data1_Test.class);
	AccountAddition accountAddition;
	Budget_BudgetSummary_Loc budgetSummary;
	Budget_CreateBudget_Loc budgetCreateBudgetLoc;
	Add_Manual_Transaction_Loc addManualTransaction;

	@BeforeClass(alwaysRun = true)
	public void doInit() throws Exception {
		doInitialization("Budget Summary TestData2");
		accountAddition = new AccountAddition();
		budgetSummary = new Budget_BudgetSummary_Loc(d);
		budgetCreateBudgetLoc = new Budget_CreateBudget_Loc(d);
		addManualTransaction = new Add_Manual_Transaction_Loc(d);
		LoginPage.loginMain(d, loginParameter);
		SeleniumUtil.waitForPageToLoad();
		d.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		accountAddition.linkAccount();
		Assert.assertTrue(
				accountAddition.addAggregatedAccount(PropsUtil.getDataPropertyValue("Budget_BudgetSummery_Data2_Site"),
						PropsUtil.getDataPropertyValue("Budget_BudgetSummery_Data2_Password")));

		PageParser.forceNavigate("Budget", d);
		SeleniumUtil.waitForPageToLoad();
		budgetCreateBudgetLoc.createBudgetGroup(PropsUtil.getDataPropertyValue("Budget_MonthlymaintaineceBudget"));

	}

	@Test(description = "AT-108737,AT-108732:Verify Income label in income progress bar", priority = 11)
	public void verifyIncomeproBarDetails1() {

		String incomeLabel = budgetSummary.budget_Summery_IncomePgrLbl().getText().trim();
		logger.info("verify income label in Income progressbar" + incomeLabel);
		String incomeProBarMesg = budgetSummary.budget_Summery_IncomePgrMessage().getText().trim();
		logger.info("verify income earned message in Income progressbar" + incomeProBarMesg);
		String incomeEraned = budgetSummary.budget_Summery_IncomePgrIncomeValue().getText().trim();
		logger.info("verify earned income value in Income progressbar" + incomeEraned);
		String incomeBudgeted = budgetSummary.budget_Summery_IncomePgrBudgetedValue().getText().trim();
		logger.info("verify budgeted income in Income progressbar" + incomeBudgeted);
		Assert.assertEquals(incomeLabel, PropsUtil.getDataPropertyValue("Budget_MonthlymaintaineceSummeryProIncomeLbl"),
				"income label is not displayed in income prgress par");
		if (Config.appFlag.equalsIgnoreCase("app") || Config.appFlag.equalsIgnoreCase("emulator")) {
			Assert.assertEquals(incomeProBarMesg + " " + incomeEraned + " " + incomeBudgeted,
					PropsUtil.getDataPropertyValue("Budget_MonthlymaintaineceSummeryProIncomeInfoMesgMobile"),
					"income earned and budgeted income is not displayed");
			// Assert.assertEquals(budgetSummary.budget_Summery_IncomeColor().getCssValue("width"),
			// PropsUtil.getDataPropertyValue("Budget_MonthlymaintaineceSummeryProIncomeWidthMobile"),
			// "width is not dispalyed");
			// Assert.assertEquals(budgetSummary.budget_Summery_IncomeRemain().getCssValue("width"),
			// PropsUtil.getDataPropertyValue("Budget_MonthlymaintaineceSummeryProIncomeRemainWidthMobile"),
			// "width is not dispalyed");

		} else {
			Assert.assertEquals(incomeProBarMesg + " " + incomeEraned + " " + incomeBudgeted,
					PropsUtil.getDataPropertyValue("Budget_MonthlymaintaineceSummeryProIncomeInfoMesg"),
					"income earned and budgeted income is not displayed");
			// Assert.assertTrue(budgetSummary.budget_Summery_IncomeColor().getCssValue("width").contains(PropsUtil.getDataPropertyValue("Budget_MonthlymaintaineceSummeryProIncomeWidth")),
			// "width is not dispalyed");
			// Assert.assertTrue(budgetSummary.budget_Summery_IncomeRemain().getCssValue("width").contains(PropsUtil.getDataPropertyValue("Budget_MonthlymaintaineceSummeryProIncomeRemainWidth")),
			// "width is not dispalyed");

		}
		Assert.assertEquals(budgetSummary.budget_Summery_IncomeColor().getCssValue("background-color"),
				PropsUtil.getDataPropertyValue("Budget_MonthlymaintaineceSummeryProIncomeColor"),
				"background color is not dispalyed");
		Assert.assertEquals(budgetSummary.budget_Summery_IncomeRemain().getCssValue("background-color"),
				PropsUtil.getDataPropertyValue("Budget_MonthlymaintaineceSummeryProIncomeRemainColor"),
				"background color is not dispalyed");
		Assert.assertTrue(budgetSummary.budget_Summery_IncomeColor().getAttribute("style").contains("100%"),
				"width should be 100%");
		// Assert.assertTrue(budgetSummary.budget_Summery_IncomeRemain().getAttribute("style").contains("89.68%"),"width
		// should be 90%");
	}

	@Test(description = "AT-108738:Verify Spending label in spending progress bar", priority = 12, dependsOnMethods = "verifyIncomeproBarDetails1")
	public void verifySpendingproBarDetails1() {
		String spendingLabel = budgetSummary.budget_Summery_SpendingPgrLbl().getText().trim();
		logger.info("verify Spending label" + spendingLabel);
		String spendingProBarMesg = budgetSummary.budget_Summery_SpendingPgrMessage().getText().trim();
		logger.info(" verify spending message in spending pogressbar" + spendingProBarMesg);
		String spendingValue = budgetSummary.budget_Summery_SpendingPgrSpendValue().getText().trim();
		logger.info("verify spending value" + spendingValue);
		String budgtedSpendValue = budgetSummary.budget_Summery_SpendingPgrBudgetedValue().getText().trim();
		logger.info("verofy total budgeted spend value" + budgtedSpendValue);
		Assert.assertEquals(spendingLabel,
				PropsUtil.getDataPropertyValue("Budget_MonthlymaintaineceSummeryProSpendingLbl"),
				"spending label is not displayed in spending progressbar");
		if (Config.appFlag.equalsIgnoreCase("app") || Config.appFlag.equalsIgnoreCase("emulator")) {
			Assert.assertEquals(spendingProBarMesg + " " + spendingValue + " " + budgtedSpendValue,
					PropsUtil.getDataPropertyValue("Budget_MonthlymaintaineceSummeryProSpendingInfoMesgMobile"),
					"spend and budgeted spend is not displayed");
			// Assert.assertEquals(budgetSummary.budget_Summery_SpendColor().getCssValue("width"),
			// PropsUtil.getDataPropertyValue("Budget_MonthlymaintaineceSummeryProSpendingWidthMobile"),
			// "width is not dispalyed");
			// Assert.assertEquals(budgetSummary.budget_Summery_SpendRemain().getCssValue("width"),
			// PropsUtil.getDataPropertyValue("Budget_MonthlymaintaineceSummeryProSpendingRemainWidthMobile"),
			// "width is not dispalyed");

		} else {
			Assert.assertEquals(spendingProBarMesg + " " + spendingValue + " " + budgtedSpendValue,
					PropsUtil.getDataPropertyValue("Budget_MonthlymaintaineceSummeryProSpendingInfoMesg"),
					"spend and budgeted spend is not displayed");
			// Assert.assertTrue(budgetSummary.budget_Summery_SpendColor().getCssValue("width").contains(
			// PropsUtil.getDataPropertyValue("Budget_MonthlymaintaineceSummeryProSpendingWidth")),
			// "width is not dispalyed");
			// Assert.assertTrue(budgetSummary.budget_Summery_SpendRemain().getCssValue("width").contains(PropsUtil.getDataPropertyValue("Budget_MonthlymaintaineceSummeryProSpendingRemainWidth")),
			// "width is not dispalyed");

		}
		Assert.assertEquals(budgetSummary.budget_Summery_SpendColor().getCssValue("background-color"),
				PropsUtil.getDataPropertyValue("Budget_MonthlymaintaineceSummeryProSpendingColor"),
				"background color is not dispalyed");
		Assert.assertEquals(budgetSummary.budget_Summery_SpendRemain().getCssValue("background-color"),
				PropsUtil.getDataPropertyValue("Budget_MonthlymaintaineceSummeryProSpendingRemainColor"),
				"background color is not dispalyed");
		SeleniumUtil.assertContains("57%", budgetSummary.budget_Summery_SpendColor().getAttribute("style"),
				"width should be 57%");
		SeleniumUtil.assertContains("99.24%", budgetSummary.budget_Summery_SpendRemain().getAttribute("style"),
				"Sopending remaining width bar should be 99 %");
	}

	@Test(description = "AT-108723:Verify budgeted income in legends", priority = 13, dependsOnMethods = "verifyIncomeproBarDetails1")
	public void verifyBudgetedIncome1() {
		String budgetedIncomelabel = null;
		String budgetedIncomeValue = null;
		String proBarColor = null;
		if (Config.appFlag.equalsIgnoreCase("app") || Config.appFlag.equalsIgnoreCase("emulator")) {
			budgetedIncomelabel = budgetSummary.budget_Summery_ActualIncome_CalLblMobile().getText().trim();
			logger.info("BudgetedIncome label" + budgetedIncomelabel);
			budgetedIncomeValue = budgetSummary.budget_Summery_ActualIncome_CalValuelMobile().getText().trim();
			logger.info("budgeted income value" + budgetedIncomeValue);
			proBarColor = budgetSummary.budget_Summery_ActualIncome_CalProBar1Mobile().getCssValue("background-color");
		} else {
			budgetedIncomelabel = budgetSummary.budget_Summery_ActualIncome_CalLbl().getText().trim();
			logger.info("BudgetedIncome label" + budgetedIncomelabel);
			budgetedIncomeValue = budgetSummary.budget_Summery_ActualIncome_CalValuel().getText().trim();
			logger.info("budgeted income value" + budgetedIncomeValue);
			proBarColor = budgetSummary.budget_Summery_ActualIncome_CalProBar1().getCssValue("background-color");
		}

		String incomeBudgeted = StringUtils
				.substringAfter(budgetSummary.budget_Summery_IncomePgrBudgetedValue().getText().trim(), "of").trim();
		logger.info("verify budgeted income in Income progressbar" + incomeBudgeted);
		Assert.assertEquals(budgetedIncomelabel,
				PropsUtil.getDataPropertyValue("Budget_MonthlymaintaineceSummeryLegendActualIncomeLbl").trim(),
				"budgeted income label is not displayed");
		Assert.assertEquals(budgetedIncomeValue.trim(), incomeBudgeted, "budgeted income value is not displayed");
		Assert.assertEquals(proBarColor,
				PropsUtil.getDataPropertyValue("Budget_MonthlymaintaineceSummeryLegendActualIncomeLblcolor"), "");

	}

	@Test(description = "AT-108723:Verify spening value in legends", priority = 14, dependsOnMethods = "verifyIncomeproBarDetails1")
	public void verifyActualSpending1() {
		String actualSpendingLabel = null;
		String actualSpendingValue = null;
		String proBarColor = null;
		if (Config.appFlag.equalsIgnoreCase("app") || Config.appFlag.equalsIgnoreCase("emulator")) {
			actualSpendingLabel = budgetSummary.budget_Summery_ActualSpend_CalLblMobile().getText().trim();
			logger.info("verify actual spending label" + actualSpendingLabel);
			actualSpendingValue = budgetSummary.budget_Summery_ActualSpend_CalValuelMobile().getText().trim();
			logger.info("verify actual spending value" + actualSpendingValue);
			proBarColor = budgetSummary.budget_Summery_ActualSpend_CalProBarMobile().getCssValue("background-color");
		} else {
			actualSpendingLabel = budgetSummary.budget_Summery_ActualSpend_CalLbl().getText().trim();
			logger.info("verify actual spending label" + actualSpendingLabel);
			actualSpendingValue = budgetSummary.budget_Summery_ActualSpend_CalValuel().getText().trim();
			logger.info("verify actual spending value" + actualSpendingValue);
			proBarColor = budgetSummary.budget_Summery_ActualSpend_CalProBar().getCssValue("background-color");
		}
		String spendingValue = budgetSummary.budget_Summery_SpendingPgrSpendValue().getText().trim();
		logger.info("verify spending value" + spendingValue);
		Assert.assertEquals(actualSpendingLabel,
				PropsUtil.getDataPropertyValue("Budget_MonthlymaintaineceSummeryLegendActualSpendingLbl"),
				"actual spending value is not displayed");
		Assert.assertEquals(actualSpendingValue, spendingValue, "actual spending is not displayed");
		Assert.assertEquals(proBarColor,
				PropsUtil.getDataPropertyValue("Budget_MonthlymaintaineceSummeryLegendActualSpendingLblcolor"), "");

	}

	@Test(description = "AT-108723:Verify left over value in legends", priority = 15, dependsOnMethods = "verifyIncomeproBarDetails1")
	public void verifyLeftOverSpending1() {
		if (Config.appFlag.equalsIgnoreCase("app") || Config.appFlag.equalsIgnoreCase("emulator")) {
			Assert.assertEquals(budgetSummary.budget_Summery_LeftOver_CalLblMobile().getText().trim(),
					PropsUtil.getDataPropertyValue("Budget_MonthlymaintaineceSummeryLegendLeftOverLbl"),
					"left over label is not dsplayed");
			Assert.assertEquals(budgetSummary.budget_Summery_LeftOver_CalValuelMobile().getText().replace("$", ""),
					budgetSummary.calculateLeftOver().toString(), "left over value is not calculating properly");
			Assert.assertEquals(budgetSummary.budget_Summery_LeftOver_CalValuelMobile().getCssValue("color"),
					PropsUtil.getDataPropertyValue("Budget_HouseHoldingSummeryLegendLeftOverLblCOlor"));

		} else {
			Assert.assertTrue(budgetSummary.budget_Summery_ActualSpend_EqualIcon().isDisplayed());
			Assert.assertEquals(budgetSummary.budget_Summery_LeftOver_CalLbl().getText().trim(),
					PropsUtil.getDataPropertyValue("Budget_MonthlymaintaineceSummeryLegendLeftOverLbl"),
					"left over label is not dsplayed");
			Assert.assertEquals(budgetSummary.budget_Summery_LeftOver_CalValuel().getText().replace("$", ""),
					budgetSummary.calculateLeftOver().toString(), "left over value is not calculating properly");
			Assert.assertEquals(budgetSummary.budget_Summery_LeftOver_CalValuel().getCssValue("color"),
					PropsUtil.getDataPropertyValue("Budget_HouseHoldingSummeryLegendLeftOverLblCOlor"));
		}
	}

	@Test(description = "AT-108733:verify negative amount is displaying progress bar", priority = 16, dependsOnMethods = "verifyIncomeproBarDetails1")
	public void verifyRefundTypeTxn1() {
		PageParser.forceNavigate("Transaction", d);
		SeleniumUtil.waitForPageToLoad();
		String IA_MTCategory8 = null;
		if (PropsUtil.getEnvPropertyValue("CategoryList43").equalsIgnoreCase("yes")) {

			IA_MTCategory8 = PropsUtil.getDataPropertyValue("Budget_MonthlymaintaineceSummerydebitTxnCategory_43");

		} else {

			IA_MTCategory8 = PropsUtil.getDataPropertyValue("Budget_MonthlymaintaineceSummerydebitTxnCategory");

		}
		if (addManualTransaction.isMoreBtnPresent()) {
			SeleniumUtil.click(addManualTransaction.MobileMore());
			SeleniumUtil.click(addManualTransaction.MobileaddManualIcon_AMT());
		} else {
			addManualTransaction.clickMTLink();
		}
		SeleniumUtil.waitForPageToLoad();
		addManualTransaction.createOneTimeTransaction(
				PropsUtil.getDataPropertyValue("Budget_MonthlymaintaineceSummerydebitTxnAmount"),
				PropsUtil.getDataPropertyValue("Budget_MonthlymaintaineceSummerydebitTxnDesc"),
				PropsUtil.getDataPropertyValue("Budget_MonthlymaintaineceSummerydebitTxnType"),
				PropsUtil.getDataPropertyValue("Budget_MonthlymaintaineceSummerydebitTxnAccount"),
				PropsUtil.getDataPropertyValue("Budget_MonthlymaintaineceSummerydebitTxnFrequency"),
				Integer.parseInt(PropsUtil.getDataPropertyValue("Budget_MonthlymaintaineceSummerydebitTxnschdate")),
				IA_MTCategory8, PropsUtil.getDataPropertyValue("Budget_MonthlymaintaineceSummerydebitTxnNote"),
				PropsUtil.getDataPropertyValue("Budget_MonthlymaintaineceSummerydebitTxnCheck"));
		SeleniumUtil.waitForPageToLoad(15000);
		if (addManualTransaction.isMoreBtnPresent()) {
			SeleniumUtil.click(addManualTransaction.MobileMore());
			SeleniumUtil.click(addManualTransaction.MobileaddManualIcon_AMT());
		} else {
			addManualTransaction.clickMTLink();
		}
		SeleniumUtil.waitForPageToLoad();
		addManualTransaction.createOneTimeTransaction(
				PropsUtil.getDataPropertyValue("Budget_MonthlymaintaineceSummeryCreditTxnAmount"),
				PropsUtil.getDataPropertyValue("Budget_MonthlymaintaineceSummeryCreditTxnDesc"),
				PropsUtil.getDataPropertyValue("Budget_MonthlymaintaineceSummeryCreditTxnType"),
				PropsUtil.getDataPropertyValue("Budget_MonthlymaintaineceSummeryCreditTxnAccount"),
				PropsUtil.getDataPropertyValue("Budget_MonthlymaintaineceSummeryCreditTxnFrequency"),
				Integer.parseInt(PropsUtil.getDataPropertyValue("Budget_MonthlymaintaineceSummeryCreditTxnschdate")),
				PropsUtil.getDataPropertyValue("Budget_MonthlymaintaineceSummeryCreditTxnCategory"),
				PropsUtil.getDataPropertyValue("Budget_MonthlymaintaineceSummeryCreditTxnNote"),
				PropsUtil.getDataPropertyValue("Budget_MonthlymaintaineceSummeryCreditxnCheck"));
		SeleniumUtil.waitForPageToLoad(15000);
		PageParser.forceNavigate("Budget", d);
		SeleniumUtil.waitForPageToLoad();
		String spendingProBarMesg = budgetSummary.budget_Summery_SpendingPgrMessage().getText().trim();
		logger.info(" verify spending message in spending pogressbar" + spendingProBarMesg);
		String spendingValue = budgetSummary.budget_Summery_SpendingPgrSpendValue().getText().trim();
		logger.info("verify spending value" + spendingValue);
		String budgtedSpendValue = budgetSummary.budget_Summery_SpendingPgrBudgetedValue().getText().trim();
		logger.info("verofy total budgeted spend value" + budgtedSpendValue);
		if (Config.appFlag.equalsIgnoreCase("app") || Config.appFlag.equalsIgnoreCase("emulator")) {
			Assert.assertEquals(spendingProBarMesg + " " + spendingValue + " " + budgtedSpendValue,
					PropsUtil.getDataPropertyValue("Budget_MonthlymaintaineceSummeryProIncomeInfoMesgEqualMobile"),
					"spend and budgeted spend is not displayed");

		} else {
			Assert.assertEquals(spendingProBarMesg + " " + spendingValue + " " + budgtedSpendValue,
					PropsUtil.getDataPropertyValue("Budget_MonthlymaintaineceSummeryProIncomeInfoMesgEqual"),
					"spend and budgeted spend is not displayed");
		}
		Assert.assertTrue(budgetSummary.budget_Summery_IncomeColor().getAttribute("style").contains("100%"),
				"width should be 57%");
		// Assert.assertTrue(budgetSummary.budget_Summery_IncomeRemain().getAttribute("style").contains("100%"),"width
		// should be 99%");
		Assert.assertEquals(budgetSummary.budget_Summery_IncomeColor().getCssValue("background-color"),
				PropsUtil.getDataPropertyValue("Budget_MonthlymaintaineceSummeryProIncomeColor"),
				"background color is not dispalyed");
		Assert.assertEquals(budgetSummary.budget_Summery_IncomeRemain().getCssValue("background-color"),
				PropsUtil.getDataPropertyValue("Budget_MonthlymaintaineceSummeryProIncomeRemainColor"),
				"background color is not dispalyed");

	}

	@Test(description = "AT-108734:Verify negative amount is considered in budget progress bar", priority = 17, dependsOnMethods = "verifyRefundTypeTxn1")
	public void verifyAdjustTypeTxn1() {
		String incomeProBarMesg = budgetSummary.budget_Summery_IncomePgrMessage().getText().trim();
		logger.info("verify income earned message in Income progressbar" + incomeProBarMesg);
		String incomeEraned = budgetSummary.budget_Summery_IncomePgrIncomeValue().getText().trim();
		logger.info("verify earned income value in Income progressbar" + incomeEraned);
		String incomeBudgeted = budgetSummary.budget_Summery_IncomePgrBudgetedValue().getText().trim();
		logger.info("verify budgeted income in Income progressbar" + incomeBudgeted);
		if (Config.appFlag.equalsIgnoreCase("app") || Config.appFlag.equalsIgnoreCase("emulator")) {
			Assert.assertEquals(incomeProBarMesg + " " + incomeEraned + " " + incomeBudgeted,
					PropsUtil.getDataPropertyValue("Budget_MonthlymaintaineceSummeryProSpendingInfoMesgEqualMobile"),
					"income earned and budgeted income is not displayed");

		} else {
			Assert.assertEquals(incomeProBarMesg + " " + incomeEraned + " " + incomeBudgeted,
					PropsUtil.getDataPropertyValue("Budget_MonthlymaintaineceSummeryProSpendingInfoMesgEqual"),
					"income earned and budgeted income is not displayed");
		}
		Assert.assertTrue(budgetSummary.budget_Summery_SpendColor().getAttribute("style").contains("57%"),
				"width should be 57%");
		Assert.assertTrue(budgetSummary.budget_Summery_SpendRemain().getAttribute("style").contains("99.99%"),
				"width should be 99%");
		Assert.assertEquals(budgetSummary.budget_Summery_SpendColor().getCssValue("background-color"),
				PropsUtil.getDataPropertyValue("Budget_MonthlymaintaineceSummeryProSpendingColor"),
				"background color is not dispalyed");
		Assert.assertEquals(budgetSummary.budget_Summery_SpendRemain().getCssValue("background-color"),
				PropsUtil.getDataPropertyValue("Budget_MonthlymaintaineceSummeryProSpendingRemainColor"),
				"background color is not dispalyed");

	}
}
