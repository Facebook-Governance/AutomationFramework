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

public class Budget_BudgetSummery_Data3_Test extends TestBase {
	private static final Logger logger = LoggerFactory.getLogger(Budget_BudgetSummery_Data1_Test.class);
	AccountAddition accountAddition;
	Budget_BudgetSummary_Loc budgetSummary;
	Budget_CreateBudget_Loc budgetCreateBudgetLoc;

	@BeforeClass(alwaysRun = true)
	public void doInit() throws Exception {
		doInitialization("Budget Summary TestData3");
		accountAddition = new AccountAddition();
		budgetSummary = new Budget_BudgetSummary_Loc(d);

		budgetCreateBudgetLoc = new Budget_CreateBudget_Loc(d);

		LoginPage.loginMain(d, loginParameter);
		SeleniumUtil.waitForPageToLoad();
		d.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

		accountAddition.linkAccount();
		Assert.assertTrue(
				accountAddition.addAggregatedAccount(PropsUtil.getDataPropertyValue("Budget_BudgetSummery_Data3_Site"),
						PropsUtil.getDataPropertyValue("Budget_BudgetSummery_Data3_Password")));

		PageParser.forceNavigate("Budget", d);
		SeleniumUtil.waitForPageToLoad();
		budgetCreateBudgetLoc.createBudgetGroup(PropsUtil.getDataPropertyValue("Budget_EducationBudget"));
	}

	@Test(description = "AT-108739,AT-108741:Verify Income label in income progress bar", priority = 18)
	public void verifyIncomeproBarDetails3() {

		String incomeLabel = budgetSummary.budget_Summery_IncomePgrLbl().getText().trim();
		logger.info("verify income label in Income progressbar" + incomeLabel);
		String incomeProBarMesg = budgetSummary.budget_Summery_IncomePgrMessage().getText().trim();
		logger.info("verify income earned message in Income progressbar" + incomeProBarMesg);
		String incomeEraned = budgetSummary.budget_Summery_IncomePgrIncomeValue().getText().trim();
		logger.info("verify earned income value in Income progressbar" + incomeEraned);
		String incomeBudgeted = budgetSummary.budget_Summery_IncomePgrBudgetedValue().getText().trim();
		logger.info("verify budgeted income in Income progressbar" + incomeBudgeted);
		Assert.assertEquals(incomeLabel, PropsUtil.getDataPropertyValue("Budget_EducationSummeryProIncomeLbl"),
				"income label is not displayed in income prgress par");
		if (Config.appFlag.equalsIgnoreCase("app") || Config.appFlag.equalsIgnoreCase("emulator")) {
			Assert.assertEquals(incomeProBarMesg + " " + incomeEraned + " " + incomeBudgeted,
					PropsUtil.getDataPropertyValue("Budget_EducationSummeryProIncomeInfoMesgMobile"),
					"income earned and budgeted income is not displayed");
			// Assert.assertEquals(budgetSummary.budget_Summery_IncomeColor().getCssValue("width"),
			// PropsUtil.getDataPropertyValue("Budget_EducationSummeryProIncomeWidthMobile"),
			// "width is not dispalyed");
			// Assert.assertEquals(budgetSummary.budget_Summery_IncomeRemain().getCssValue("width"),
			// PropsUtil.getDataPropertyValue("Budget_EducationSummeryProIncomeRemainWidthMobile"),
			// "width is not dispalyed");

		} else {
			Assert.assertEquals(incomeProBarMesg + " " + incomeEraned + " " + incomeBudgeted,
					PropsUtil.getDataPropertyValue("Budget_EducationSummeryProIncomeInfoMesg"),
					"income earned and budgeted income is not displayed");
			// Assert.assertTrue(budgetSummary.budget_Summery_IncomeColor().getCssValue("width").contains(PropsUtil.getDataPropertyValue("Budget_EducationSummeryProIncomeWidth")),
			// "width is not dispalyed");
			// Assert.assertTrue(budgetSummary.budget_Summery_IncomeRemain().getCssValue("width").contains(PropsUtil.getDataPropertyValue("Budget_EducationSummeryProIncomeRemainWidth")),
			// "width is not dispalyed");

		}
		Assert.assertEquals(budgetSummary.budget_Summery_IncomeColor().getCssValue("background-color"),
				PropsUtil.getDataPropertyValue("Budget_EducationSummeryProIncomeColor"),
				"background color is not dispalyed");
		Assert.assertEquals(budgetSummary.budget_Summery_IncomeRemain().getCssValue("background-color"),
				PropsUtil.getDataPropertyValue("Budget_EducationSummeryProIncomeRemainColor"),
				"background color is not dispalyed");
		Assert.assertTrue(budgetSummary.budget_Summery_IncomeColor().getAttribute("style").contains("100%"),
				"width should be 100%");
		SeleniumUtil.assertContains("100%", budgetSummary.budget_Summery_IncomeRemain().getAttribute("style"),
				"width should be 100%");
	}

	@Test(description = "AT-108740,AT-108742:Verify Spending label in spending progress bar", priority = 19, dependsOnMethods = "verifyIncomeproBarDetails3")
	public void verifySpendingproBarDetails3() {
		String spendingLabel = budgetSummary.budget_Summery_SpendingPgrLbl().getText().trim();
		logger.info("verify Spending label" + spendingLabel);
		String spendingProBarMesg = budgetSummary.budget_Summery_SpendingPgrMessage().getText().trim();
		logger.info(" verify spending message in spending pogressbar" + spendingProBarMesg);
		String spendingValue = budgetSummary.budget_Summery_SpendingPgrSpendValue().getText().trim();
		logger.info("verify spending value" + spendingValue);
		String budgtedSpendValue = budgetSummary.budget_Summery_SpendingPgrBudgetedValue().getText().trim();
		logger.info("verofy total budgeted spend value" + budgtedSpendValue);
		Assert.assertEquals(spendingLabel, PropsUtil.getDataPropertyValue("Budget_EducationSummeryProSpendingLbl"),
				"spending label is not displayed in spending progressbar");
		if (Config.appFlag.equalsIgnoreCase("app") || Config.appFlag.equalsIgnoreCase("emulator")) {
			Assert.assertEquals(spendingProBarMesg + " " + spendingValue + " " + budgtedSpendValue,
					PropsUtil.getDataPropertyValue("Budget_EducationSummeryProSpendingInfoMesgMobile"),
					"spend and budgeted spend is not displayed");
			// Assert.assertEquals(budgetSummary.budget_Summery_SpendColor().getCssValue("width").contains(
			// PropsUtil.getDataPropertyValue("Budget_EducationSummeryProSpendingWidthMobile")),
			// "width is not dispalyed");
			// Assert.assertEquals(budgetSummary.budget_Summery_SpendRemain().getCssValue("width").contains(PropsUtil.getDataPropertyValue("Budget_EducationSummeryProSpendingRemainWidthMobile")),
			// "width is not dispalyed");

		} else {
			Assert.assertEquals(spendingProBarMesg + " " + spendingValue + " " + budgtedSpendValue,
					PropsUtil.getDataPropertyValue("Budget_EducationSummeryProSpendingInfoMesg"),
					"spend and budgeted spend is not displayed");
			// Assert.assertTrue(budgetSummary.budget_Summery_SpendColor().getCssValue("width").contains(PropsUtil.getDataPropertyValue("Budget_EducationSummeryProSpendingWidth")),
			// "width is not dispalyed");
			// Assert.assertTrue(budgetSummary.budget_Summery_SpendRemain().getCssValue("width").contains(PropsUtil.getDataPropertyValue("Budget_EducationSummeryProSpendingRemainWidth")),
			// "width is not dispalyed");

		}
		Assert.assertEquals(budgetSummary.budget_Summery_SpendColor().getCssValue("background-color"),
				PropsUtil.getDataPropertyValue("Budget_EducationSummeryProSpendingColor"),
				"background color is not dispalyed");
		Assert.assertEquals(budgetSummary.budget_Summery_SpendRemain().getCssValue("background-color"),
				PropsUtil.getDataPropertyValue("Budget_EducationSummeryProSpendingRemainColor"),
				"background color is not dispalyed");
		// Assert.assertTrue(budgetSummary.budget_Summery_SpendColor().getAttribute("style").contains("55%"),"width
		// should be 100%");
		Assert.assertTrue(budgetSummary.budget_Summery_SpendRemain().getAttribute("style").contains("99.24%"),
				"width should be 100%");
	}

	@Test(description = "AT-108740,AT-108742:Verify budgeted income in legends", priority = 20, dependsOnMethods = "verifyIncomeproBarDetails3")
	public void verifyBudgetedIncome3() {
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
				PropsUtil.getDataPropertyValue("Budget_EducationSummeryLegendActualIncomeLbl"),
				"budgeted income label is not displayed");
		Assert.assertEquals(budgetedIncomeValue, incomeEarned, "budgeted income value is not displayed");
		Assert.assertEquals(proBarColor,
				PropsUtil.getDataPropertyValue("Budget_EducationSummeryLegendActualIncomeLblColor"), "");

	}

	@Test(description = "AT-108740,AT-108742:Verify spending value in legends", priority = 21, dependsOnMethods = "verifyIncomeproBarDetails3")
	public void verifyActualSpending3() {
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
				PropsUtil.getDataPropertyValue("Budget_EducationSummeryLegendActualSpendingLbl"),
				"actual spending value is not displayed");
		Assert.assertEquals(actualSpendingValue, spendingValue, "actual spending is not displayed");
		Assert.assertEquals(proBarColor,
				PropsUtil.getDataPropertyValue("Budget_EducationSummeryLegendActualSpendingLblColor"), "");

	}

	@Test(description = "AT-108740,AT-108742:Verify left over amount in legends", priority = 22, dependsOnMethods = "verifyIncomeproBarDetails3")
	public void verifyLeftOverSpending3() {
		if (Config.appFlag.equalsIgnoreCase("app") || Config.appFlag.equalsIgnoreCase("emulator")) {
			Assert.assertEquals(budgetSummary.budget_Summery_LeftOver_CalLblMobile().getText().trim(),
					PropsUtil.getDataPropertyValue("Budget_EducationSummeryLegendLeftOverLbl"),
					"left over label is not dsplayed");
			Assert.assertEquals(budgetSummary.budget_Summery_LeftOver_CalValuelMobile().getText().replace("$", ""),
					budgetSummary.calculateLeftOver().toString(), "left over value is not calculating properly");
			Assert.assertEquals(budgetSummary.budget_Summery_LeftOver_CalValuelMobile().getCssValue("color"),
					PropsUtil.getDataPropertyValue("Budget_HouseHoldingSummeryLegendLeftOverLblCOlor"));
		} else {
			Assert.assertTrue(budgetSummary.budget_Summery_ActualSpend_EqualIcon().isDisplayed());
			Assert.assertEquals(budgetSummary.budget_Summery_LeftOver_CalLbl().getText().trim(),
					PropsUtil.getDataPropertyValue("Budget_EducationSummeryLegendLeftOverLbl"),
					"left over label is not dsplayed");
			Assert.assertEquals(budgetSummary.budget_Summery_LeftOver_CalValuel().getText().replace("$", ""),
					budgetSummary.calculateLeftOver().toString(), "left over value is not calculating properly");
			Assert.assertEquals(budgetSummary.budget_Summery_LeftOver_CalValuel().getCssValue("color"),
					PropsUtil.getDataPropertyValue("Budget_HouseHoldingSummeryLegendLeftOverLblCOlor"));
		}
	}
}
