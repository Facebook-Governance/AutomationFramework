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
import com.omni.pfm.testBase.TestBase;
import com.omni.pfm.utility.PropsUtil;
import com.omni.pfm.utility.SeleniumUtil;

public class Budget_BudgetSummery_Data8_Test extends TestBase {
	private static final Logger logger = LoggerFactory.getLogger(Budget_BudgetSummery_Data1_Test.class);
	AccountAddition accountAddition;
	Budget_BudgetSummary_Loc budgetSummary;
	Budget_CreateBudget_Loc budgetCreateBudgetLoc;

	@BeforeClass(alwaysRun = true)
	public void doInit() throws Exception {
		doInitialization("Budget Summary TestData8");
		accountAddition = new AccountAddition();
		budgetSummary = new Budget_BudgetSummary_Loc(d);
		budgetCreateBudgetLoc = new Budget_CreateBudget_Loc(d);
		LoginPage.loginMain(d, loginParameter);
		SeleniumUtil.waitForPageToLoad();
		d.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		accountAddition.linkAccount();
		Assert.assertTrue(
				accountAddition.addAggregatedAccount(PropsUtil.getDataPropertyValue("Budget_BudgetSummery_Data8_Site"),
						PropsUtil.getDataPropertyValue("Budget_BudgetSummery_Data8_Password")));
		PageParser.forceNavigate("Budget", d);
		SeleniumUtil.waitForPageToLoad();
		budgetCreateBudgetLoc.createBudgetGroup(PropsUtil.getDataPropertyValue("Budget_BikeBudget"));
	}

	@Test(description = "AT-108743,AT-108749:Verify Income label in income progress bar", priority = 43)
	public void verifyIncomeproBarDetails8() {

		String incomeLabel = budgetSummary.budget_Summery_IncomePgrLbl().getText().trim();
		logger.info("verify income label in Income progressbar" + incomeLabel);
		String incomeProBarMesg = budgetSummary.budget_Summery_IncomePgrMessage().getText().trim();
		logger.info("verify income earned message in Income progressbar" + incomeProBarMesg);
		String incomeEraned = budgetSummary.budget_Summery_IncomePgrIncomeValue().getText().trim();
		logger.info("verify earned income value in Income progressbar" + incomeEraned);
		String incomeBudgeted = budgetSummary.budget_Summery_IncomePgrBudgetedValue().getText().trim();
		logger.info("verify budgeted income in Income progressbar" + incomeBudgeted);
		Assert.assertEquals(incomeLabel, PropsUtil.getDataPropertyValue("Budget_BikeSummeryProIncomeLbl"),
				"income label is not displayed in income prgress par");
		if (Config.appFlag.equalsIgnoreCase("app") || Config.appFlag.equalsIgnoreCase("emulator")) {
			Assert.assertEquals(incomeProBarMesg + " " + incomeEraned + " " + incomeBudgeted,
					PropsUtil.getDataPropertyValue("Budget_BikeSummeryProIncomeInfoMesgMobile"),
					"income earned and budgeted income is not displayed");
			// Assert.assertEquals(budgetSummary.budget_Summery_IncomeColor().getCssValue("width"),
			// PropsUtil.getDataPropertyValue("Budget_BikeSummeryProIncomeWidthMobile"),
			// "width is not dispalyed");
			// Assert.assertEquals(budgetSummary.budget_Summery_IncomeRemain().getCssValue("width"),
			// PropsUtil.getDataPropertyValue("Budget_BikeSummeryProIncomeRemainWidthMobile"),
			// "width is not dispalyed");

		} else {
			Assert.assertEquals(incomeProBarMesg + " " + incomeEraned + " " + incomeBudgeted,
					PropsUtil.getDataPropertyValue("Budget_BikeSummeryProIncomeInfoMesg"),
					"income earned and budgeted income is not displayed");
			// Assert.assertTrue(budgetSummary.budget_Summery_IncomeColor().getCssValue("width").contains(PropsUtil.getDataPropertyValue("Budget_BikeSummeryProIncomeWidth")),
			// "width is not dispalyed");
			// Assert.assertTrue(budgetSummary.budget_Summery_IncomeRemain().getCssValue("width").contains(PropsUtil.getDataPropertyValue("Budget_BikeSummeryProIncomeRemainWidth")),
			// "width is not dispalyed");

		}
		Assert.assertEquals(budgetSummary.budget_Summery_IncomeColor().getCssValue("background-color"),
				PropsUtil.getDataPropertyValue("Budget_BikeSummeryProIncomeColor"),
				"background color is not dispalyed");
		Assert.assertEquals(budgetSummary.budget_Summery_IncomeRemain().getCssValue("background-color"),
				PropsUtil.getDataPropertyValue("Budget_BikeSummeryProIncomeRemainColor"),
				"background color is not dispalyed");
		// Assert.assertTrue(budgetSummary.budget_Summery_IncomeColor().getAttribute("style").contains("84%"),"width
		// should be 100%");
		SeleniumUtil.assertContains("100%", budgetSummary.budget_Summery_IncomeRemain().getAttribute("style"),
				"width should be 100%");
	}

	@Test(description = "AT-108750:Verify Spending label in spending progress bar", priority = 44, dependsOnMethods = "verifyIncomeproBarDetails8")
	public void verifySpendingproBarDetails8() {
		String spendingLabel = budgetSummary.budget_Summery_SpendingPgrLbl().getText().trim();
		logger.info("verify Spending label" + spendingLabel);
		String spendingProBarMesg = budgetSummary.budget_Summery_SpendingPgrMessage().getText().trim();
		logger.info(" verify spending message in spending pogressbar" + spendingProBarMesg);
		String spendingValue = budgetSummary.budget_Summery_SpendingPgrSpendValue().getText().trim();
		logger.info("verify spending value" + spendingValue);
		String budgtedSpendValue = budgetSummary.budget_Summery_SpendingPgrBudgetedValue().getText().trim();
		logger.info("verofy total budgeted spend value" + budgtedSpendValue);
		Assert.assertEquals(spendingLabel, PropsUtil.getDataPropertyValue("Budget_BikeSummeryProSpendingLbl"),
				"spending label is not displayed in spending progressbar");
		if (Config.appFlag.equalsIgnoreCase("app") || Config.appFlag.equalsIgnoreCase("emulator")) {
			Assert.assertEquals(spendingProBarMesg + " " + spendingValue + " " + budgtedSpendValue,
					PropsUtil.getDataPropertyValue("Budget_BikeSummeryProSpendingInfoMesgMobile"),
					"spend and budgeted spend is not displayed");
			Assert.assertEquals(budgetSummary.budget_Summery_SpendColor().getCssValue("width"),
					PropsUtil.getDataPropertyValue("Budget_BikeSummeryProSpendingWidthMobile"),
					"width is not dispalyed");
			Assert.assertEquals(budgetSummary.budget_Summery_SpendRemain().getCssValue("width"),
					PropsUtil.getDataPropertyValue("Budget_BikeSummeryProSpendingRemainWidthMobile"),
					"width is not dispalyed");

		} else {
			Assert.assertEquals(spendingProBarMesg + " " + spendingValue + " " + budgtedSpendValue,
					PropsUtil.getDataPropertyValue("Budget_BikeSummeryProSpendingInfoMesg"),
					"spend and budgeted spend is not displayed");
			SeleniumUtil.assertContains(PropsUtil.getDataPropertyValue("Budget_BikeSummeryProSpendingWidth"),
					budgetSummary.budget_Summery_SpendColor().getCssValue("width"), "width is not dispalyed");
			// Assert.assertTrue(budgetSummary.budget_Summery_SpendRemain().getCssValue("width").contains(PropsUtil.getDataPropertyValue("Budget_BikeSummeryProSpendingRemainWidth")),
			// "width is not dispalyed");

		}
		Assert.assertEquals(budgetSummary.budget_Summery_SpendColor().getCssValue("background-color"),
				PropsUtil.getDataPropertyValue("Budget_BikeSummeryProSpendingColor"),
				"background color is not dispalyed");
		Assert.assertEquals(budgetSummary.budget_Summery_SpendRemain().getCssValue("background-color"),
				PropsUtil.getDataPropertyValue("Budget_BikeSummeryProSpendingRemainColor"),
				"background color is not dispalyed");
		Assert.assertTrue(budgetSummary.budget_Summery_SpendColor().getAttribute("style").contains("100%"),
				"width should be 100%");
		// Assert.assertTrue(budgetSummary.budget_Summery_SpendRemain().getAttribute("style").contains("84%"),"width
		// should be 100%");
	}

	@Test(description = "AT-108750:Verify budgetd income in legends", priority = 45, dependsOnMethods = "verifyIncomeproBarDetails8")
	public void verifyBudgetedIncome8() {
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
		String incomeBudgeted = budgetSummary.budget_Summery_IncomePgrIncomeValue().getText().trim();
		logger.info("verify budgeted income in Income progressbar" + incomeBudgeted);
		Assert.assertEquals(budgetedIncomelabel,
				PropsUtil.getDataPropertyValue("Budget_BikeSummeryLegendActualIncomeLbl"),
				"budgeted income label is not displayed");
		Assert.assertEquals(budgetedIncomeValue, incomeBudgeted, "budgeted income value is not displayed");
		Assert.assertEquals(proBarColor,
				PropsUtil.getDataPropertyValue("Budget_HouseHoldingSummeryLegendActualIncomeLblColor"), "");

	}

	@Test(description = "AT-108750:Verifyspending value in legends", priority = 46, dependsOnMethods = "verifyIncomeproBarDetails8")
	public void verifyActualSpending8() {
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
				PropsUtil.getDataPropertyValue("Budget_BikeSummeryLegendActualSpendingLbl"),
				"actual spending value is not displayed");
		Assert.assertEquals(actualSpendingValue, spendingValue, "actual spending is not displayed");
		Assert.assertEquals(proBarColor,
				PropsUtil.getDataPropertyValue("Budget_HouseHoldingSummeryLegendActualSpendingLblColor"), "");

	}

	@Test(description = "AT-108729,AT-108751,AT-108730:Verify left over value in legends", priority = 47, dependsOnMethods = "verifyIncomeproBarDetails8")
	public void verifyLeftOverSpending8() {
		if (Config.appFlag.equalsIgnoreCase("app") || Config.appFlag.equalsIgnoreCase("emulator")) {
			Assert.assertEquals(budgetSummary.budget_Summery_LeftOver_CalLblMobile().getText().trim(),
					PropsUtil.getDataPropertyValue("Budget_BikeSummeryLegendLeftOverLbl"),
					"left over label is not dsplayed");
			Assert.assertEquals(budgetSummary.budget_Summery_LeftOver_CalValuelMobile().getText().replace("$", ""),
					budgetSummary.calculateLeftOver().toString().replace("-", "").trim(),
					"left over value is not calculating properly");
			Assert.assertEquals(budgetSummary.budget_Summery_LeftOver_CalValuelMobile().getCssValue("color"),
					PropsUtil.getDataPropertyValue("Budget_BikeSummeryLegendLeftOverLblMobile"));

		} else {
			Assert.assertTrue(budgetSummary.budget_Summery_ActualSpend_EqualIcon().isDisplayed());
			Assert.assertEquals(budgetSummary.budget_Summery_LeftOver_CalLbl().getText().trim(),
					PropsUtil.getDataPropertyValue("Budget_BikeSummeryLegendLeftOverLbl"),
					"left over label is not dsplayed");
			Assert.assertEquals(budgetSummary.budget_Summery_LeftOver_CalValuel().getText().replace("$", ""),
					budgetSummary.calculateLeftOver().toString().replace("-", "").trim(),
					"left over value is not calculating properly");
			Assert.assertEquals(budgetSummary.budget_Summery_LeftOver_CalValuel().getCssValue("color"),
					PropsUtil.getDataPropertyValue("Budget_BikeSummeryLegendLeftOverLblMobile"));
		}
	}
}
