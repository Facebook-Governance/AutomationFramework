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
import com.omni.pfm.testBase.TestBase;
import com.omni.pfm.utility.PropsUtil;
import com.omni.pfm.utility.SeleniumUtil;

public class Budget_BudgetSummery_Data4_Test extends TestBase {
	private static final Logger logger = LoggerFactory.getLogger(Budget_BudgetSummery_Data1_Test.class);
	AccountAddition accountAddition;
	Budget_BudgetSummary_Loc budgetSummary;
	Budget_CreateBudget_Loc budgetCreateBudgetLoc;

	@BeforeClass(alwaysRun = true)
	public void doInit() throws Exception {
		doInitialization("Budget Summary TestData4");
		accountAddition = new AccountAddition();
		budgetSummary = new Budget_BudgetSummary_Loc(d);
		budgetCreateBudgetLoc = new Budget_CreateBudget_Loc(d);
		LoginPage.loginMain(d, loginParameter);
		SeleniumUtil.waitForPageToLoad();
		d.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		accountAddition.linkAccount();
		Assert.assertTrue(
				accountAddition.addAggregatedAccount(PropsUtil.getDataPropertyValue("Budget_BudgetSummery_Data4_Site"),
						PropsUtil.getDataPropertyValue("Budget_BudgetSummery_Data4_Password")));

		PageParser.forceNavigate("Budget", d);
		SeleniumUtil.waitForPageToLoad();
		budgetCreateBudgetLoc.createBudgetGroup(PropsUtil.getDataPropertyValue("Budget_GroceryBudget"));
	}

	@Test(description = "AT-108744:Verify Income label in income progress bar", priority = 23)
	public void verifyIncomeproBarDetails4() {

		String incomeLabel = budgetSummary.budget_Summery_IncomePgrLbl().getText().trim();
		logger.info("verify income label in Income progressbar" + incomeLabel);
		String incomeProBarMesg = budgetSummary.budget_Summery_IncomePgrMessage().getText().trim();
		logger.info("verify income earned message in Income progressbar" + incomeProBarMesg);
		String incomeEraned = budgetSummary.budget_Summery_IncomePgrIncomeValue().getText().trim();
		logger.info("verify earned income value in Income progressbar" + incomeEraned);
		String incomeBudgeted = budgetSummary.budget_Summery_IncomePgrBudgetedValue().getText().trim();
		logger.info("verify budgeted income in Income progressbar" + incomeBudgeted);
		Assert.assertEquals(incomeLabel, PropsUtil.getDataPropertyValue("Budget_GrocerySummeryProIncomeLbl"),
				"income label is not displayed in income prgress par");
		if (Config.appFlag.equalsIgnoreCase("app") || Config.appFlag.equalsIgnoreCase("emulator")) {
			Assert.assertEquals(incomeProBarMesg + " " + incomeEraned + " " + incomeBudgeted,
					PropsUtil.getDataPropertyValue("Budget_GrocerySummeryProIncomeInfoMesgMobile"),
					"income earned and budgeted income is not displayed");
			// Assert.assertEquals(budgetSummary.budget_Summery_IncomeColor().getCssValue("width"),
			// PropsUtil.getDataPropertyValue("Budget_GrocerySummeryProIncomeWidthMobile"),
			// "width is not dispalyed");
			// Assert.assertEquals(budgetSummary.budget_Summery_IncomeRemain().getCssValue("width"),
			// PropsUtil.getDataPropertyValue("Budget_GrocerySummeryProIncomeRemainWidthMobile"),
			// "width is not dispalyed");

		} else {
			Assert.assertEquals(incomeProBarMesg + " " + incomeEraned + " " + incomeBudgeted,
					PropsUtil.getDataPropertyValue("Budget_GrocerySummeryProIncomeInfoMesg"),
					"income earned and budgeted income is not displayed");
			// Assert.assertTrue(budgetSummary.budget_Summery_IncomeColor().getCssValue("width").contains(PropsUtil.getDataPropertyValue("Budget_GrocerySummeryProIncomeWidth")),
			// "width is not dispalyed");
			// Assert.assertTrue(budgetSummary.budget_Summery_IncomeRemain().getCssValue("width").contains(PropsUtil.getDataPropertyValue("Budget_GrocerySummeryProIncomeRemainWidth")),
			// "width is not dispalyed");

		}
		Assert.assertEquals(budgetSummary.budget_Summery_IncomeColor().getCssValue("background-color"),
				PropsUtil.getDataPropertyValue("Budget_GrocerySummeryProIncomeColor"),
				"background color is not dispalyed");
		Assert.assertEquals(budgetSummary.budget_Summery_IncomeRemain().getCssValue("background-color"),
				PropsUtil.getDataPropertyValue("Budget_GrocerySummeryProIncomeRemainColor"),
				"background color is not dispalyed");
		SeleniumUtil.assertContains("99%", budgetSummary.budget_Summery_IncomeColor().getAttribute("style"),
				"width should be 100%");
		// Assert.assertTrue(budgetSummary.budget_Summery_IncomeRemain().getAttribute("style").contains("85.87%"),"width
		// should be 100%");
	}

	@Test(description = "AT-108744:Verify Spending label in spending progress bar", priority = 24, dependsOnMethods = "verifyIncomeproBarDetails4")
	public void verifySpendingproBarDetails4() {
		String spendingLabel = budgetSummary.budget_Summery_SpendingPgrLbl().getText().trim();
		logger.info("verify Spending label" + spendingLabel);
		String spendingProBarMesg = budgetSummary.budget_Summery_SpendingPgrMessage().getText().trim();
		logger.info(" verify spending message in spending pogressbar" + spendingProBarMesg);
		String spendingValue = budgetSummary.budget_Summery_SpendingPgrSpendValue().getText().trim();
		logger.info("verify spending value" + spendingValue);
		String budgtedSpendValue = budgetSummary.budget_Summery_SpendingPgrBudgetedValue().getText().trim();
		logger.info("verofy total budgeted spend value" + budgtedSpendValue);
		Assert.assertEquals(spendingLabel, PropsUtil.getDataPropertyValue("Budget_GrocerySummeryProSpendingLbl"),
				"spending label is not displayed in spending progressbar");
		if (Config.appFlag.equalsIgnoreCase("app") || Config.appFlag.equalsIgnoreCase("emulator")) {
			Assert.assertEquals(spendingProBarMesg + " " + spendingValue + " " + budgtedSpendValue,
					PropsUtil.getDataPropertyValue("Budget_GrocerySummeryProSpendingInfoMesgMobile"),
					"spend and budgeted spend is not displayed");
			// Assert.assertEquals(budgetSummary.budget_Summery_SpendColor().getCssValue("width"),
			// PropsUtil.getDataPropertyValue("Budget_GrocerySummeryProSpendingWidthMobile"),
			// "width is not dispalyed");
			// Assert.assertEquals(budgetSummary.budget_Summery_SpendRemain().getCssValue("width"),
			// PropsUtil.getDataPropertyValue("Budget_GrocerynSummeryProSpendingRemainWidthMobile"),
			// "width is not dispalyed");

		} else {
			Assert.assertEquals(spendingProBarMesg + " " + spendingValue + " " + budgtedSpendValue,
					PropsUtil.getDataPropertyValue("Budget_GrocerySummeryProSpendingInfoMesg"),
					"spend and budgeted spend is not displayed");
			// Assert.assertTrue(budgetSummary.budget_Summery_SpendColor().getCssValue("width").contains(PropsUtil.getDataPropertyValue("Budget_GrocerySummeryProSpendingWidth")),
			// "width is not dispalyed");
			// Assert.assertTrue(budgetSummary.budget_Summery_SpendRemain().getCssValue("width").contains(PropsUtil.getDataPropertyValue("Budget_GrocerynSummeryProSpendingRemainWidth")),
			// "width is not dispalyed");

		}
		Assert.assertEquals(budgetSummary.budget_Summery_SpendColor().getCssValue("background-color"),
				PropsUtil.getDataPropertyValue("Budget_GrocerySummeryProSpendingColor"),
				"background color is not dispalyed");
		Assert.assertEquals(budgetSummary.budget_Summery_SpendRemain().getCssValue("background-color"),
				PropsUtil.getDataPropertyValue("Budget_GrocerySummeryProSpendingRemainColor"),
				"background color is not dispalyed");
		Assert.assertTrue(budgetSummary.budget_Summery_SpendColor().getAttribute("style").contains("100%"),
				"width should be 100%");
		Assert.assertTrue(budgetSummary.budget_Summery_SpendRemain().getAttribute("style").contains("55.02%"),
				"width should be 100%");
	}

	@Test(description = "AT-108723:Verify verify budgeted income value in legends", priority = 25, dependsOnMethods = "verifyIncomeproBarDetails4")
	public void verifyBudgetedIncome4() {
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
				PropsUtil.getDataPropertyValue("Budget_GrocerySummeryLegendActualIncomeLbl"),
				"budgeted income label is not displayed");
		Assert.assertEquals(budgetedIncomeValue, incomeBudgeted, "budgeted income value is not displayed");
		Assert.assertEquals(proBarColor,
				PropsUtil.getDataPropertyValue("Budget_GrocerySummeryLegendActualIncomeLblColor"), "");

	}

	@Test(description = "AT-108744:Verify verify spending value in legends", priority = 26, dependsOnMethods = "verifyIncomeproBarDetails4")
	public void verifyActualSpending4() {
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
				PropsUtil.getDataPropertyValue("Budget_GrocerySummeryLegendActualSpendingLbl"),
				"actual spending value is not displayed");
		Assert.assertEquals(actualSpendingValue, spendingValue, "actual spending is not displayed");
		Assert.assertEquals(proBarColor,
				PropsUtil.getDataPropertyValue("Budget_HouseHoldingSummeryLegendActualSpendingLblColor"), "");

	}

	@Test(description = "AT-108744:Verify left over value in legends", priority = 27, dependsOnMethods = "verifyIncomeproBarDetails4")
	public void verifyLeftOverSpending4() {
		if (Config.appFlag.equalsIgnoreCase("app") || Config.appFlag.equalsIgnoreCase("emulator")) {
			Assert.assertEquals(budgetSummary.budget_Summery_LeftOver_CalLblMobile().getText().trim(),
					PropsUtil.getDataPropertyValue("Budget_GrocerySummeryLegendLeftOverLbl"),
					"left over label is not dsplayed");
			Assert.assertEquals(budgetSummary.budget_Summery_LeftOver_CalValuelMobile().getText().replace("$", ""),
					budgetSummary.calculateLeftOver().toString(), "left over value is not calculating properly");
			Assert.assertEquals(budgetSummary.budget_Summery_LeftOver_CalValuelMobile().getCssValue("color"),
					PropsUtil.getDataPropertyValue("Budget_HouseHoldingSummeryLegendLeftOverLblCOlor"));

		} else {
			Assert.assertTrue(budgetSummary.budget_Summery_ActualSpend_EqualIcon().isDisplayed());
			Assert.assertEquals(budgetSummary.budget_Summery_LeftOver_CalLbl().getText().trim(),
					PropsUtil.getDataPropertyValue("Budget_GrocerySummeryLegendLeftOverLbl"),
					"left over label is not dsplayed");
			Assert.assertEquals(budgetSummary.budget_Summery_LeftOver_CalValuel().getText().replace("$", ""),
					budgetSummary.calculateLeftOver().toString(), "left over value is not calculating properly");
			Assert.assertEquals(budgetSummary.budget_Summery_LeftOver_CalValuel().getCssValue("color"),
					PropsUtil.getDataPropertyValue("Budget_HouseHoldingSummeryLegendLeftOverLblCOlor"));
		}
	}
}
