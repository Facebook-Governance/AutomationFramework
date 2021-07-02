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

public class Budget_BudgetSummery_Data1_Test extends TestBase {
	private static final Logger logger = LoggerFactory.getLogger(Budget_BudgetSummery_Data1_Test.class);
	AccountAddition accountAddition;
	Budget_BudgetSummary_Loc budgetSummary;
	Budget_CreateBudget_Loc budgetCreateBudgetLoc;
	Add_Manual_Transaction_Loc addManualTransaction;

	@BeforeClass(alwaysRun = true)
	public void doInit() throws Exception {
		doInitialization("Budget Summary TestData1");
		accountAddition = new AccountAddition();
		budgetSummary = new Budget_BudgetSummary_Loc(d);
		budgetCreateBudgetLoc = new Budget_CreateBudget_Loc(d);
		addManualTransaction = new Add_Manual_Transaction_Loc(d);

		LoginPage.loginMain(d, loginParameter);
		SeleniumUtil.waitForPageToLoad();
		d.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		accountAddition.linkAccount();
		Assert.assertTrue(
				accountAddition.addAggregatedAccount(PropsUtil.getDataPropertyValue("Budget_BudgetSummery_Data1_Site"),
						PropsUtil.getDataPropertyValue("Budget_BudgetSummery_Data1_Password")));
		PageParser.forceNavigate("Budget", d);
		SeleniumUtil.waitForPageToLoad();
		budgetCreateBudgetLoc.createBudgetGroup(PropsUtil.getDataPropertyValue("Budget_HouseHoldingBudget"));
	}

	@Test(description = "AT-108720,AT-108725,AT-108732,AT-108735:Verify Income label in income progress bar", priority = 1)
	public void verifyIncomeproBarDetails() {

		String incomeLabel = budgetSummary.budget_Summery_IncomePgrLbl().getText().trim();
		logger.info("verify income label in Income progressbar" + incomeLabel);
		String incomeProBarMesg = budgetSummary.budget_Summery_IncomePgrMessage().getText().trim();
		logger.info("verify income earned message in Income progressbar" + incomeProBarMesg);
		String incomeEraned = budgetSummary.budget_Summery_IncomePgrIncomeValue().getText().trim();
		logger.info("verify earned income value in Income progressbar" + incomeEraned);
		String incomeBudgeted = budgetSummary.budget_Summery_IncomePgrBudgetedValue().getText().trim();
		logger.info("verify budgeted income in Income progressbar" + incomeBudgeted);
		Assert.assertEquals(incomeLabel, PropsUtil.getDataPropertyValue("Budget_HouseHoldingSummeryProIncomeLbl"),
				"income label is not displayed in income prgress par");
		if (Config.appFlag.equalsIgnoreCase("app") || Config.appFlag.equalsIgnoreCase("emulator")) {
			Assert.assertEquals(incomeProBarMesg + " " + incomeEraned + " " + incomeBudgeted,
					PropsUtil.getDataPropertyValue("Budget_HouseHoldingSummeryProIncomeInfoMesgMobile"),
					"income earned and budgeted income is not displayed");
		} else {
			Assert.assertEquals(incomeProBarMesg + " " + incomeEraned + " " + incomeBudgeted,
					PropsUtil.getDataPropertyValue("Budget_HouseHoldingSummeryProIncomeInfoMesg"),
					"income earned and budgeted income is not displayed");
		}
		Assert.assertEquals(budgetSummary.budget_Summery_IncomeColor().getCssValue("background-color"),
				PropsUtil.getDataPropertyValue("Budget_HouseHoldingSummeryProIncomeColor"),
				"background color is not dispalyed");
		Assert.assertEquals(budgetSummary.budget_Summery_IncomeRemain().getCssValue("background-color"),
				PropsUtil.getDataPropertyValue("Budget_HouseHoldingSummeryProIncomeRemainColor"),
				"background color is not dispalyed");
		Assert.assertTrue(budgetSummary.budget_Summery_IncomeColor().getAttribute("style").contains("100%"),
				"width should be 100%");
		Assert.assertTrue(budgetSummary.budget_Summery_IncomeRemain().getAttribute("style").contains("100%"),
				"width should be 100%");
	}

	@Test(description = "AT-108721,AT-108726,AT-108736:Verify Spending label progress bar", priority = 2, dependsOnMethods = "verifyIncomeproBarDetails")
	public void verifySpendingproBarDetails() {
		String spendingLabel = budgetSummary.budget_Summery_SpendingPgrLbl().getText().trim();
		logger.info("verify Spending label" + spendingLabel);
		String spendingProBarMesg = budgetSummary.budget_Summery_SpendingPgrMessage().getText().trim();
		logger.info(" verify spending message in spending pogressbar" + spendingProBarMesg);
		String spendingValue = budgetSummary.budget_Summery_SpendingPgrSpendValue().getText().trim();
		logger.info("verify spending value" + spendingValue);
		String budgtedSpendValue = budgetSummary.budget_Summery_SpendingPgrBudgetedValue().getText().trim();
		logger.info("verofy total budgeted spend value" + budgtedSpendValue);
		Assert.assertEquals(spendingLabel, PropsUtil.getDataPropertyValue("Budget_HouseHoldingSummeryProSpendingLbl"),
				"spending label is not displayed in spending progressbar");
		if (Config.appFlag.equalsIgnoreCase("app") || Config.appFlag.equalsIgnoreCase("emulator")) {
			Assert.assertEquals(spendingProBarMesg + " " + spendingValue + " " + budgtedSpendValue,
					PropsUtil.getDataPropertyValue("Budget_HouseHoldingSummeryProSpendingInfoMesgMobile"),
					"spend and budgeted spend is not displayed");
		} else {
			Assert.assertEquals(spendingProBarMesg + " " + spendingValue + " " + budgtedSpendValue,
					PropsUtil.getDataPropertyValue("Budget_HouseHoldingSummeryProSpendingInfoMesg"),
					"spend and budgeted spend is not displayed");
		}
		Assert.assertEquals(budgetSummary.budget_Summery_SpendRemain().getCssValue("background-color"),
				PropsUtil.getDataPropertyValue("Budget_HouseHoldingSummeryProSpendingRemainColor"),
				"background color is not dispalyed");
		Assert.assertTrue(budgetSummary.budget_Summery_SpendColor().getAttribute("style").contains("64%"),
				"width should be 100%");
		Assert.assertTrue(budgetSummary.budget_Summery_SpendRemain().getAttribute("style").contains("100%"),
				"width should be 100%");
	}

	@Test(description = "AT-108722:Verify budgeted income value", priority = 3, dependsOnMethods = "verifyIncomeproBarDetails")
	public void verifyBudgetedIncome() {
		String budgetedIncomelabel = null;
		String budgetedIncomeValue = null;
		String proBarColor = null;
		if (Config.appFlag.equalsIgnoreCase("app") || Config.appFlag.equalsIgnoreCase("emulator")) {
			budgetedIncomelabel = budgetSummary.budget_Summery_ActualIncome_CalLblMobile().getText().trim();
			logger.info("BudgetedIncome label" + budgetedIncomelabel);
			budgetedIncomeValue = budgetSummary.budget_Summery_ActualIncome_CalValuelMobile().getText().trim();
			logger.info("budgeted income value" + budgetedIncomeValue);
			proBarColor = budgetSummary.budget_Summery_ActualIncome_CalProBarMobile().getCssValue("background-color");

		} else {
			budgetedIncomelabel = budgetSummary.budget_Summery_ActualIncome_CalLbl().getText().trim();
			logger.info("BudgetedIncome label" + budgetedIncomelabel);
			budgetedIncomeValue = budgetSummary.budget_Summery_ActualIncome_CalValuel().getText().trim();
			logger.info("budgeted income value" + budgetedIncomeValue);
			proBarColor = budgetSummary.budget_Summery_ActualIncome_CalProBar().getCssValue("background-color");
		}
		String incomeEarned = budgetSummary.budget_Summery_IncomePgrIncomeValue().getText().trim();
		logger.info("verify budgeted income in Income progressbar" + incomeEarned);
		Assert.assertEquals(budgetedIncomelabel,
				PropsUtil.getDataPropertyValue("Budget_HouseHoldingSummeryLegendActualIncomeLbl"),
				"budgeted income label is not displayed");
		Assert.assertEquals(budgetedIncomeValue, incomeEarned, "budgeted income value is not displayed");
		Assert.assertEquals(proBarColor,
				PropsUtil.getDataPropertyValue("Budget_HouseHoldingSummeryLegendActualIncomeLblColor"), "");
	}

	@Test(description = "AT-108724,AT-108734:Verify spending values in legends", priority = 4, dependsOnMethods = "verifyIncomeproBarDetails")
	public void verifyActualSpending() {
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
				PropsUtil.getDataPropertyValue("Budget_HouseHoldingSummeryLegendActualSpendingLbl"),
				"actual spending value is not displayed");
		Assert.assertEquals(actualSpendingValue, spendingValue, "actual spending is not displayed");
		Assert.assertEquals(proBarColor,
				PropsUtil.getDataPropertyValue("Budget_HouseHoldingSummeryLegendActualSpendingLblColor"), "");

	}

	@Test(description = "AT-108727,AT-108728:Verify left over amount in legends", priority = 5, dependsOnMethods = "verifyIncomeproBarDetails")
	public void verifyLeftOverSpending() {
		if (Config.appFlag.equalsIgnoreCase("app") || Config.appFlag.equalsIgnoreCase("emulator")) {
			Assert.assertEquals(budgetSummary.budget_Summery_LeftOver_CalLblMobile().getText().trim(),
					PropsUtil.getDataPropertyValue("Budget_HouseHoldingSummeryLegendLeftOverLbl"),
					"left over label is not dsplayed");
			Assert.assertEquals(budgetSummary.budget_Summery_LeftOver_CalValuelMobile().getText().replace("$", ""),
					budgetSummary.calculateLeftOver().toString(), "left over value is not calculating properly");
			Assert.assertEquals(budgetSummary.budget_Summery_LeftOver_CalValuelMobile().getCssValue("color"),
					PropsUtil.getDataPropertyValue("Budget_HouseHoldingSummeryLegendLeftOverLblCOlor"));

		} else {
			Assert.assertTrue(budgetSummary.budget_Summery_ActualSpend_EqualIcon().isDisplayed(),
					"equal icon is not displayed");
			Assert.assertEquals(budgetSummary.budget_Summery_LeftOver_CalLbl().getText().trim(),
					PropsUtil.getDataPropertyValue("Budget_HouseHoldingSummeryLegendLeftOverLbl"),
					"left over label is not dsplayed");
			Assert.assertEquals(budgetSummary.budget_Summery_LeftOver_CalValuel().getText().replace("$", ""),
					budgetSummary.calculateLeftOver().toString(), "left over value is not calculating properly");
			Assert.assertEquals(budgetSummary.budget_Summery_LeftOver_CalValuel().getCssValue("color"),
					PropsUtil.getDataPropertyValue("Budget_HouseHoldingSummeryLegendLeftOverLblCOlor"));
		}
	}

	@Test(description = "AT-108719:verify progress bar date header is displayed", priority = 6, dependsOnMethods = "verifyIncomeproBarDetails")
	public void verifySummeryProgressBarDateHeader() {
		if (Config.appFlag.equalsIgnoreCase("web") || Config.appFlag.equalsIgnoreCase("false")) {
			Assert.assertEquals(budgetSummary.budget_Summery_ProgressbarDateHeader().getText().trim(),
					budgetSummary.getdateMMMMYYYY(Integer.parseInt(PropsUtil.getDataPropertyValue("Budget_IndexZero"))),
					"date header is not dsplayed");
		}
	}

	@Test(description = "AT-108719:verify group drop down is displayed", priority = 7, dependsOnMethods = "verifyIncomeproBarDetails")
	public void verifySummeryBudgetGroupDropdown() {
		Assert.assertEquals(budgetSummary.budget_Summery_GroupDropDown().getText().trim(),
				PropsUtil.getDataPropertyValue("Budget_HouseHoldingBudget"), "created group name should be displayed");
		Assert.assertTrue(budgetSummary.budget_Summery_GroupDropDownIcon().isDisplayed());
	}

	@Test(description = "AT-108718:verify budget time filter is displayed", priority = 8, dependsOnMethods = "verifyIncomeproBarDetails")
	public void verifySummeryBudgetTimeFilter() {
		Assert.assertEquals(budgetSummary.budget_Summery_TimeFilter().getText().trim(),
				PropsUtil.getDataPropertyValue("Budget_SummeryDefaulttimeFilterValue"),
				"time filter label is not displayed");
		Assert.assertEquals(budgetSummary.budget_Summery_TimeFilterDate().getText().trim(),
				budgetSummary.getdateMMMMYYYY(Integer.parseInt(PropsUtil.getDataPropertyValue("Budget_IndexZero"))),
				"time filter date value is not displayed");
		Assert.assertTrue(budgetSummary.budget_Summery_TimeFilterIcon().isDisplayed());
	}

	@Test(description = "AT-108758,AT-108755:refund transaction is considered for budget", priority = 9, dependsOnMethods = "verifyIncomeproBarDetails")
	public void verifyRefundTypeTxn() {
		PageParser.forceNavigate("Transaction", d);
		SeleniumUtil.waitForPageToLoad();
		String IA_MTCategory8 = null;
		if (PropsUtil.getEnvPropertyValue("CategoryList43").equalsIgnoreCase("yes")) {

			IA_MTCategory8 = PropsUtil.getDataPropertyValue("Budget_HouseHoldingSummeryRefundTxnCategory_43");

		} else {

			IA_MTCategory8 = PropsUtil.getDataPropertyValue("Budget_HouseHoldingSummeryRefundTxnCategory");

		}
		if (addManualTransaction.isMoreBtnPresent()) {
			SeleniumUtil.click(addManualTransaction.MobileMore());
			SeleniumUtil.click(addManualTransaction.MobileaddManualIcon_AMT());
		} else {
			addManualTransaction.clickMTLink();
		}
		SeleniumUtil.waitForPageToLoad();
		addManualTransaction.createOneTimeTransaction(
				PropsUtil.getDataPropertyValue("Budget_HouseHoldingSummeryRefundTxnAmount"),
				PropsUtil.getDataPropertyValue("Budget_HouseHoldingSummeryRefundTxnDesc"),
				PropsUtil.getDataPropertyValue("Budget_HouseHoldingSummeryRefundTxnType"),
				PropsUtil.getDataPropertyValue("Budget_HouseHoldingSummeryRefundTxnAccount"),
				PropsUtil.getDataPropertyValue("Budget_HouseHoldingSummeryRefundTxnFrequency"),
				Integer.parseInt(PropsUtil.getDataPropertyValue("Budget_HouseHoldingSummeryRefundTxnschdate")),
				IA_MTCategory8, PropsUtil.getDataPropertyValue("Budget_HouseHoldingSummeryRefundTxnNote"),
				PropsUtil.getDataPropertyValue("Budget_HouseHoldingSummeryRefundTxnCheck"));
		SeleniumUtil.waitForPageToLoad(15000);
		if (addManualTransaction.isMoreBtnPresent()) {
			SeleniumUtil.click(addManualTransaction.MobileMore());
			SeleniumUtil.click(addManualTransaction.MobileaddManualIcon_AMT());
		} else {
			addManualTransaction.clickMTLink();
		}
		SeleniumUtil.waitForPageToLoad();
		addManualTransaction.createOneTimeTransaction(
				PropsUtil.getDataPropertyValue("Budget_HouseHoldingSummeryAdjmtTxnAmount"),
				PropsUtil.getDataPropertyValue("Budget_HouseHoldingSummeryAdjmtTxnDesc"),
				PropsUtil.getDataPropertyValue("Budget_HouseHoldingSummeryAdjmtTxnType"),
				PropsUtil.getDataPropertyValue("Budget_HouseHoldingSummeryAdjmtTxnAccount"),
				PropsUtil.getDataPropertyValue("Budget_HouseHoldingSummeryAdjmtTxnFrequency"),
				Integer.parseInt(PropsUtil.getDataPropertyValue("Budget_HouseHoldingSummeryAdjmtTxnschdate")),
				PropsUtil.getDataPropertyValue("Budget_HouseHoldingSummeryAdjmtTxnCategory"),
				PropsUtil.getDataPropertyValue("Budget_HouseHoldingSummeryAdjmtTxnNote"),
				PropsUtil.getDataPropertyValue("Budget_HouseHoldingSummeryAdjmtTxnCheck"));
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
					PropsUtil.getDataPropertyValue("Budget_HouseHoldingSummeryProIncomeInfoMesgNegativeMobile"),
					"spend and budgeted spend is not displayed");
		} else {
			Assert.assertEquals(spendingProBarMesg + " " + spendingValue + " " + budgtedSpendValue,
					PropsUtil.getDataPropertyValue("Budget_HouseHoldingSummeryProSpendingInfoMesgNegative"),
					"spend and budgeted spend is not displayed");

		}
	}

	@Test(description = "AT-108757,AT-108756:adjustment transaction is considered for budget", priority = 10, dependsOnMethods = "verifySummeryBudgetTimeFilter")
	public void verifyAdjustTypeTxn() {
		String incomeProBarMesg = budgetSummary.budget_Summery_IncomePgrMessage().getText().trim();
		logger.info("verify income earned message in Income progressbar" + incomeProBarMesg);
		String incomeEraned = budgetSummary.budget_Summery_IncomePgrIncomeValue().getText().trim();
		logger.info("verify earned income value in Income progressbar" + incomeEraned);
		String incomeBudgeted = budgetSummary.budget_Summery_IncomePgrBudgetedValue().getText().trim();
		logger.info("verify budgeted income in Income progressbar" + incomeBudgeted);
		if (Config.appFlag.equalsIgnoreCase("app") || Config.appFlag.equalsIgnoreCase("emulator")) {
			Assert.assertEquals(incomeProBarMesg + " " + incomeEraned + " " + incomeBudgeted,
					PropsUtil.getDataPropertyValue("Budget_HouseHoldingSummeryProIncomeInfoMesgNegativeMobile"),
					"income earned and budgeted income is not displayed");

		} else {
			Assert.assertEquals(incomeProBarMesg + " " + incomeEraned + " " + incomeBudgeted,
					PropsUtil.getDataPropertyValue("Budget_HouseHoldingSummeryProIncomeInfoMesgNegative"),
					"income earned and budgeted income is not displayed");
		}
	}
}
