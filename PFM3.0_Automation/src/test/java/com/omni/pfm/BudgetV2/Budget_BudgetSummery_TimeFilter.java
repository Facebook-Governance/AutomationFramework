/**
* Copyright (c) 2018 Yodlee Inc. All Rights Reserved.
*
* This software is the confidential and proprietary information of
* Yodlee, Inc. Use is subject to license terms.
*
* @author Renuka K
*/
package com.omni.pfm.BudgetV2;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.omni.pfm.PageProcessor.PageParser;
import com.omni.pfm.config.Config;
import com.omni.pfm.pages.AccountAddition.AccountAddition;
import com.omni.pfm.pages.BudgetV2.Budget_BudgetSummary_Loc;
import com.omni.pfm.pages.BudgetV2.Budget_CreateBudget_Loc;
import com.omni.pfm.pages.Login.L1NodeLogin;
import com.omni.pfm.pages.Login.LoginPage;
import com.omni.pfm.pages.TransactionEnhancement1.Add_Manual_Transaction_Loc;
import com.omni.pfm.pages.TransactionEnhancement1.Transaction_Filter_Loc;
import com.omni.pfm.testBase.TestBase;
import com.omni.pfm.utility.DBUtil;
import com.omni.pfm.utility.PropsUtil;
import com.omni.pfm.utility.SeleniumUtil;

public class Budget_BudgetSummery_TimeFilter extends TestBase {
	private static final Logger logger = LoggerFactory.getLogger(Budget_BudgetSummery_TimeFilter.class);
	AccountAddition accountAddition;
	Budget_BudgetSummary_Loc budgetSummary;
	Budget_CreateBudget_Loc budgetCreateBudgetLoc;
	Add_Manual_Transaction_Loc addManualTransaction;
	Transaction_Filter_Loc filter;
	String userName = null;
	SeleniumUtil util;
	Connection con;

	@BeforeClass(alwaysRun = true)
	public void doInit() throws Exception {
		doInitialization("Budget Time Filter And Trend");
		accountAddition = new AccountAddition();
		budgetSummary = new Budget_BudgetSummary_Loc(d);
		budgetCreateBudgetLoc = new Budget_CreateBudget_Loc(d);
		addManualTransaction = new Add_Manual_Transaction_Loc(d);
		filter = new Transaction_Filter_Loc(d);
		util = new SeleniumUtil();

		LoginPage.loginMain(d, loginParameter);
		SeleniumUtil.waitForPageToLoad();
		userName = L1NodeLogin.userName;
		System.out.println(userName);
		d.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		accountAddition.linkAccount();
		accountAddition.addAggregatedAccount(PropsUtil.getDataPropertyValue("Budget_BudgetSummery_Data13_Site"),
				PropsUtil.getDataPropertyValue("Budget_BudgetSummery_Data13_Password"));
		PageParser.forceNavigate("Budget", d);
		SeleniumUtil.waitForPageToLoad();
		budgetCreateBudgetLoc.createBudgetGroup(PropsUtil.getDataPropertyValue("Budget_HouseHoldingBudget"));
		con = DBUtil.getDBConnections(PropsUtil.getEnvPropertyValue("DBHost"), PropsUtil.getEnvPropertyValue("DBPort"),
				PropsUtil.getEnvPropertyValue("DBServiceName"), PropsUtil.getEnvPropertyValue("DBType"),
				PropsUtil.getEnvPropertyValue("DBEnv"));
		DBUtil.goalHistoryUpdate(con, userName);

	}

	@Test(description = "AT-110205,AT-110207,AT-110208: Verify all the drop down options displayed in the filter", priority = 1)
	public void verifyAllTimeFilter() throws ClassNotFoundException, SQLException {
		PageParser.forceNavigate("Budget", d);
		SeleniumUtil.waitForPageToLoad();
		SeleniumUtil.click(budgetSummary.budget_Summery_TimeFilterIcon());

		if (Config.appFlag.equalsIgnoreCase("app") || Config.appFlag.equalsIgnoreCase("emulator")) {
			util.assertExpectedActualList(
					util.getWebElementsValue(budgetSummary.budget_Summery_CustomedateFilterList()),
					PropsUtil.getDataPropertyValue("Budget_SummeryAllTimeFilterLabelsMobile"),
					"all time filter is not displayed");
		} else {
			util.assertExpectedActualList(
					util.getWebElementsValue(budgetSummary.budget_Summery_CustomedateFilterList()),
					PropsUtil.getDataPropertyValue("Budget_SummeryAllTimeFilterLabels"),
					"all time filter is not displayed");

		}
	}

	@Test(description = "AT-110209,AT-110215,AT-110149: Verify last month data", priority = 2, dependsOnMethods = {
			"verifyAllTimeFilter" })
	public void verifylastmonthsSelection() throws ClassNotFoundException, SQLException {
		SeleniumUtil.click(budgetSummary.budget_Summery_TimeFilterIcon());
		SeleniumUtil.click(budgetSummary
				.budget_Summery_CustomedateFilterName(PropsUtil.getDataPropertyValue("Budget_SummeryThisMonthLabel")));

		SeleniumUtil.waitForPageToLoad();
		Assert.assertEquals(budgetSummary.budget_Summery_TimeFilter().getText().trim(),
				PropsUtil.getDataPropertyValue("Budget_SummeryThisMonthLabel"), "time filter label is not displayed");
		Assert.assertEquals(budgetSummary.budget_Summery_TimeFilterDate().getText().trim(),
				budgetSummary.getdateMMMMYYYY(0), "time filter date value is not displayed");

	}

	@Test(description = "AT-110209,AT-110157: Verify Income progress bar", priority = 3, dependsOnMethods = {
			"verifylastmonthsSelection" })
	public void verifylastmonthsIncomeProgressBar() throws ClassNotFoundException, SQLException {
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
					PropsUtil.getDataPropertyValue("Budget_HouseHoldingTrendProIncomeInfoMesgMobile"),
					"income earned and budgeted income is not displayed");
		} else {
			Assert.assertEquals(incomeProBarMesg + " " + incomeEraned + " " + incomeBudgeted,
					PropsUtil.getDataPropertyValue("Budget_HouseHoldingTrendProIncomeInfoMesg"),
					"income earned and budgeted income is not displayed");
		}
	}

	@Test(description = "AT-110209,AT-110151,AT-110158,AT-110159: Verify last month data", priority = 4, dependsOnMethods = {
			"verifylastmonthsSelection" })
	public void verifylastmonthsSpendingProgressBar() throws ClassNotFoundException, SQLException {

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
					PropsUtil.getDataPropertyValue("Budget_HouseHoldingTrendProSpendingInfoMesgMobile"),
					"spend and budgeted spend is not displayed");
			Assert.assertEquals(budgetSummary.budget_Summery_LeftOver_CalValuelMobile().getText().replace("$", ""),
					budgetSummary.calculateLeftOver().toString().replace("-", ""),
					"left over value is not calculating properly");

		} else {

			Assert.assertEquals(spendingProBarMesg + " " + spendingValue + " " + budgtedSpendValue,
					PropsUtil.getDataPropertyValue("Budget_HouseHoldingTrendProSpendingInfoMesg"),
					"spend and budgeted spend is not displayed");
			Assert.assertEquals(budgetSummary.budget_Summery_LeftOver_CalValuel().getText().replace("$", ""),
					budgetSummary.calculateLeftOver().toString().replace("-", ""),
					"left over value is not calculating properly");

		}

	}

	@Test(description = "AT-110209,AT-110171: Verify last month data", priority = 5, dependsOnMethods = {
			"verifylastmonthsSelection" })
	public void verifylastmonthsIncomeCategory() {
		if (Config.appFlag.equalsIgnoreCase("app") || Config.appFlag.equalsIgnoreCase("emulator")) {
			SeleniumUtil.click(budgetSummary.budget_Trend_ViewDetailsBtn());
		}

		util.assertExpectedActualList(
				util.getWebElementsValue(budgetSummary.budget_Summary_CategorySectionCatgeoryName(
						PropsUtil.getDataPropertyValue("Budget_Step2_IncomeInput"))),
				PropsUtil.getDataPropertyValue("Budget_SummaryLastMonthIncomeCategoryLabels"),
				"All the Income Category Labels should reflect");

		if (Config.appFlag.equalsIgnoreCase("web") || Config.appFlag.equalsIgnoreCase("false")) {

			SeleniumUtil.waitForPageToLoad();
			util.assertExpectedActualAmountList(
					util.getWebElementsValue(budgetSummary.budget_Summary_CategorySectionCatgeoryBudgetedAmt1(
							PropsUtil.getDataPropertyValue("Budget_Step2_IncomeInput"))),
					PropsUtil.getDataPropertyValue("Budget_SummaryLastMonthIncomeCategoryBudgetLabels"),
					"Budget Amount");
			util.assertExpectedActualAmountList(
					util.getWebElementsValue(budgetSummary.budget_Summary_CategorySectionCatgeorySpentAmt(
							PropsUtil.getDataPropertyValue("Budget_Step2_IncomeInput"))),
					PropsUtil.getDataPropertyValue("Budget_SummaryLastMonthIncomeCategoryEarnedLabels"),
					"Earned amount");
			Assert.assertTrue(budgetSummary.budget_Summery_CategoryTrendHighChart().size() == 0,
					"bar chart not displayed");
		}
		util.assertExpectedActualAmountList(
				util.getWebElementsValue(budgetSummary.budget_Summary_CategorySectionCatgeoryLeftAmt(
						PropsUtil.getDataPropertyValue("Budget_Step2_IncomeInput"))),
				PropsUtil.getDataPropertyValue("Budget_SummaryLastMonthIncomeCategoryLeftOverLabels"),
				"Left Over Amount");

	}

	@Test(description = "AT-110209: Verify last month data", priority = 6, dependsOnMethods = {
			"verifylastmonthsSelection" })
	public void verifylastmonthsBillsCategory() {
		util.assertExpectedActualList(
				util.getWebElementsValue(budgetSummary.budget_Summary_CategorySectionCatgeoryName(
						PropsUtil.getDataPropertyValue("Budget_Step2_BillsInput"))),
				PropsUtil.getDataPropertyValue("Budget_SummaryLastMonthBillsCategoryLabels"),
				"Bill category for last month");

		if (Config.appFlag.equalsIgnoreCase("web") || Config.appFlag.equalsIgnoreCase("false")) {

			util.assertExpectedActualAmountList(
					util.getWebElementsValue(budgetSummary.budget_Summary_CategorySectionCatgeoryBudgetedAmt1(
							PropsUtil.getDataPropertyValue("Budget_Step2_BillsInput"))),
					PropsUtil.getDataPropertyValue("Budget_SummaryLastMonthBillsBudgetLabels"),
					"Budget amount in Bills");
			util.assertExpectedActualAmountList(
					util.getWebElementsValue(budgetSummary.budget_Summary_CategorySectionCatgeorySpentAmt(
							PropsUtil.getDataPropertyValue("Budget_Step2_BillsInput"))),
					PropsUtil.getDataPropertyValue("Budget_SummaryLastMonthBillsSpentLabels"), "Spent amount in Bills");
		}
		util.assertExpectedActualAmountList(
				util.getWebElementsValue(budgetSummary.budget_Summary_CategorySectionCatgeoryLeftAmt(
						PropsUtil.getDataPropertyValue("Budget_Step2_BillsInput"))),
				PropsUtil.getDataPropertyValue("Budget_SummaryLastMonthBillsLeftOverLabels"),
				"Left over amount on Bills");

	}

	@Test(description = "AT-110209: Verify last month data", priority = 7, dependsOnMethods = {
			"verifylastmonthsSelection" })
	public void verifylastmonthsFlexibleCategory() {
		util.assertExpectedActualList(
				util.getWebElementsValue(budgetSummary.budget_Summary_CategorySectionCatgeoryName(
						PropsUtil.getDataPropertyValue("Budget_Step2_FlexibleInput"))),
				PropsUtil.getDataPropertyValue("Budget_SummaryLastMonthFlexibleSpendingCategoryLabels"),
				"Felxible Spending Category labels not Displayed");

		if (Config.appFlag.equalsIgnoreCase("web") || Config.appFlag.equalsIgnoreCase("false")) {

			util.assertExpectedActualAmountList(
					util.getWebElementsValue(budgetSummary.budget_Summary_CategorySectionCatgeoryBudgetedAmt1(
							PropsUtil.getDataPropertyValue("Budget_Step2_FlexibleInput"))),
					PropsUtil.getDataPropertyValue("Budget_SummaryLastMonthFlexibleSpendingBudgetLabels"),
					"Felxible Spending Budget labels not Displayed");
			util.assertExpectedActualAmountList(
					util.getWebElementsValue(budgetSummary.budget_Summary_CategorySectionCatgeorySpentAmt(
							PropsUtil.getDataPropertyValue("Budget_Step2_FlexibleInput"))),
					PropsUtil.getDataPropertyValue("Budget_SummaryLastMonthFlexibleSpendingSpentLabels"),
					"Felxible Spending Spent labels not Displayed");
		}
		util.assertExpectedActualAmountList(
				util.getWebElementsValue(budgetSummary.budget_Summary_CategorySectionCatgeoryLeftAmt(
						PropsUtil.getDataPropertyValue("Budget_Step2_FlexibleInput"))),
				PropsUtil.getDataPropertyValue("Budget_SummaryLastMonthFlexibleSpendingLeftOverLabels"),
				"Spending Label is not displayed");

	}

	@Test(description = "AT-110209,AT-110149, AT-110152: Verify last 3 month data", priority = 8, dependsOnMethods = {
			"verifyAllTimeFilter" })
	public void verifylast3monthsSelection() {
		if (Config.appFlag.equalsIgnoreCase("app") || Config.appFlag.equalsIgnoreCase("emulator")) {

			SeleniumUtil.click(budgetSummary.budget_Trends_BackIconMobile());
		}
		SeleniumUtil.waitForPageToLoad();
		SeleniumUtil.click(budgetSummary
				.budget_Summery_CustomedateFilterName(PropsUtil.getDataPropertyValue("Budget_Summery3MonthLabel")));
		SeleniumUtil.waitForPageToLoad();
		Assert.assertEquals(budgetSummary.budget_Summery_TimeFilter().getText().trim(),
				PropsUtil.getDataPropertyValue("Budget_Summery3MonthLabel"), "time filter label is not displayed");
		Assert.assertEquals(budgetSummary.budget_Summery_TimeFilterDate().getText().trim(),
				budgetSummary.getdateMMMYYYY(-2) + " " + "-" + " " + budgetSummary.getdateMMMYYYY(0),
				"time filter date value is not displayed");
		Assert.assertTrue(budgetSummary
				.budget_Summery_Trendcheckbox(PropsUtil.getDataPropertyValue("Budget_LegendIncomeLbl")).isDisplayed(),
				"Legend is not displayed");
		Assert.assertTrue(budgetSummary
				.budget_Summery_Trendcheckbox(PropsUtil.getDataPropertyValue("Budget_LegendSpendingLbl")).isDisplayed(),
				"Legend is not displayed");
		util.assertExpectedActualList(util.getWebElementsValue(budgetSummary.budget_Summery_TrendcheckboxName()),
				PropsUtil.getDataPropertyValue("Budget_LegendIncomeSpendingLbl"),
				"Income and spending is not displayed");
		Assert.assertEquals(budgetSummary.budget_Summery_TrendNoncheckboxName().getText().trim(),
				PropsUtil.getDataPropertyValue("Budget_LegendBudgetLbl"));
		util.assertIsDisplayed(budgetSummary.budget_Summery_TrendXaxix(), "xaxis month value is not displayed");

		util.assertIsDisplayed(budgetSummary.budget_Summery_TrendHighChart(), "bar chart is not displayed");
		// Assert.assertTrue(budgetSummary.budget_Summary_AddCategoryBtn().size()==0,"Add
		// category button should not be displayed");
		if (Config.appFlag.equalsIgnoreCase("app") || Config.appFlag.equalsIgnoreCase("emulator")) {

			Assert.assertTrue(budgetSummary.budget_Summery_TrendCardIcon().isDisplayed(),
					"TrendCard Icon is not displayed");
		}

	}

	@Test(description = "AT-110209,AT-110155,AT-110156,AT-110166,AT-110175,AT-110174: Verify last 3 month data in Mobile", priority = 9, dependsOnMethods = {
			"verifylast3monthsSelection" })
	public void verifyClickingOnCardDetailsMobile() {

		if (Config.appFlag.equalsIgnoreCase("app") || Config.appFlag.equalsIgnoreCase("emulator")) {

			SeleniumUtil.click(budgetSummary.budget_Trend_ViewDetailsBtn());
			SeleniumUtil.waitForPageToLoad();
			SeleniumUtil.click(budgetSummary.budget_Summary_CategorySectionIncomeCatgeoryName().get(0));
			SeleniumUtil.waitForPageToLoad();
			SeleniumUtil.click(budgetSummary.budget_Summary_MonthBtnMobile());

			SeleniumUtil.click(budgetSummary.budget_Trends_BackIconMobile());

			SeleniumUtil.waitForPageToLoad();

			SeleniumUtil.click(budgetSummary.budget_Summery_TrendCardIcon());

			util.assertExpectedActualList(
					util.getWebElementsValue(budgetSummary.budget_Summery_TrendMonthDetailsMobile()),
					PropsUtil.getDataPropertyValue("Budget_TrendMonthMobileDetails"),
					"Income and spending is not displayed");

			SeleniumUtil.click(budgetSummary.budget_Summery_TrendMonthDetailsMobile().get(1));

		}
	}

	@Test(description = "AT-110209,AT-110146,AT-110147,AT-110181,AT-110172,AT-110180: Verify last 3 month data", priority = 10, dependsOnMethods = {
			"verifylast3monthsSelection" })
	public void verifyCategoryDetailsIncome() {

		SeleniumUtil.click(budgetSummary.budget_Summary_CategorySectionIncomeCatgeoryName().get(0));

		util.assertIsDisplayed(budgetSummary.budget_Summery_CategoryTrendHighChart(), "bar chart is not displayed");

		Assert.assertEquals(budgetCreateBudgetLoc.budget_Steps2_legend3monthAvg().getText().trim(),
				PropsUtil.getDataPropertyValue("Budget_Trend_ActualAddCategoryLegendCategoryAvg"),
				"Trend Avg message is not displayed");
		Assert.assertEquals(budgetCreateBudgetLoc.budget_Steps2_legendcatTypeLabel().getText(),
				PropsUtil.getDataPropertyValue("Budget_Trend_BudgetAddCategoryLegendCategory"),
				"Trend Budget Category Legend is not displayed");
		Assert.assertEquals(budgetSummary.budget_Trend_CategoryLegendActualLbl().getText(),
				PropsUtil.getDataPropertyValue("Budget_Trend_ActualAddCategoryLegendCategory"),
				"Trend Actual Category Legend is not displayed");

	}

	@Test(description = "AT-110209: Verify last 3 month data", priority = 11, dependsOnMethods = {
			"verifylast3monthsSelection" }, enabled = false)
	public void verify3monthsIncomeCategory() {

		if (Config.appFlag.equalsIgnoreCase("app") || Config.appFlag.equalsIgnoreCase("emulator")) {
			SeleniumUtil.click(budgetSummary.budget_Trend_ViewDetailsBtn());
		}

		budgetCreateBudgetLoc.budget_Steps2_CategoryDetailsExpand("Budget_Step2_IncomeInput");
		util.assertExpectedActualList(
				util.getWebElementsValue(budgetSummary.budget_Summary_CategorySectionCatgeoryName(
						PropsUtil.getDataPropertyValue("Budget_Step2_IncomeInput"))),
				PropsUtil.getDataPropertyValue("Budget_Summary3MonthIncomeCategoryLabels"),
				"All the Income Category Labels should reflect");
		if (Config.appFlag.equalsIgnoreCase("web") || Config.appFlag.equalsIgnoreCase("false")) {

			util.assertExpectedActualAmountList(
					util.getWebElementsValue(budgetSummary.budget_Summary_CategorySectionCatgeoryBudgetedAmt1(
							PropsUtil.getDataPropertyValue("Budget_Step2_IncomeInput"))),
					PropsUtil.getDataPropertyValue("Budget_Summary3MonthIncomeCategoryBudgetLabels"), "Budget Amount");
			util.assertExpectedActualAmountList(
					util.getWebElementsValue(budgetSummary.budget_Summary_CategorySectionCatgeorySpentAmt(
							PropsUtil.getDataPropertyValue("Budget_Step2_IncomeInput"))),
					PropsUtil.getDataPropertyValue("Budget_Summary3MonthIncomeCategoryEarnedLabels"), "Earned amount");

		}
		util.assertExpectedActualAmountList(
				util.getWebElementsValue(budgetSummary.budget_Summary_CategorySectionCatgeoryLeftAmt(
						PropsUtil.getDataPropertyValue("Budget_Step2_IncomeInput"))),
				PropsUtil.getDataPropertyValue("Budget_Summary3MonthIncomeCategoryLeftOverLabels"), "Left Over Amount");

	}

	@Test(description = "AT-110209: Verify last 3 month data", priority = 12, dependsOnMethods = {
			"verifylast3monthsSelection" }, enabled = false)
	public void verify3monthsBillsCategory() {
		util.assertExpectedActualList(
				util.getWebElementsValue(budgetSummary.budget_Summary_CategorySectionCatgeoryName(
						PropsUtil.getDataPropertyValue("Budget_Step2_BillsInput"))),
				PropsUtil.getDataPropertyValue("Budget_Summary3MonthBillsCategoryLabels"),
				"Bill category for last month");
		if (Config.appFlag.equalsIgnoreCase("web") || Config.appFlag.equalsIgnoreCase("false")) {

			util.assertExpectedActualAmountList(
					util.getWebElementsValue(budgetSummary.budget_Summary_CategorySectionCatgeoryBudgetedAmt1(
							PropsUtil.getDataPropertyValue("Budget_Step2_BillsInput"))),
					PropsUtil.getDataPropertyValue("Budget_Summary3MonthBillsBudgetLabels"), "Budget amount in Bills");
			util.assertExpectedActualAmountList(
					util.getWebElementsValue(budgetSummary.budget_Summary_CategorySectionCatgeorySpentAmt(
							PropsUtil.getDataPropertyValue("Budget_Step2_BillsInput"))),
					PropsUtil.getDataPropertyValue("Budget_Summary3MonthBillsSpentLabels"), "Spent amount in Bills");
		}
		util.assertExpectedActualAmountList(
				util.getWebElementsValue(budgetSummary.budget_Summary_CategorySectionCatgeoryLeftAmt(
						PropsUtil.getDataPropertyValue("Budget_Step2_BillsInput"))),
				PropsUtil.getDataPropertyValue("Budget_Summary3MonthBillsLeftOverLabels"), "Left over amount on Bills");

	}

	@Test(description = "AT-110209: Verify last 3 month data", priority = 13, dependsOnMethods = {
			"verifylast3monthsSelection" }, enabled = false)
	public void verify3monthsFlexibleCategory() {

		util.assertExpectedActualList(
				util.getWebElementsValue(budgetSummary.budget_Summary_CategorySectionCatgeoryName(
						PropsUtil.getDataPropertyValue("Budget_Step2_FlexibleInput"))),
				PropsUtil.getDataPropertyValue("Budget_Summary3MonthFlexibleSpendingCategoryLabels"),
				"Flexible Spending Labels");
		if (Config.appFlag.equalsIgnoreCase("web") || Config.appFlag.equalsIgnoreCase("false")) {

			util.assertExpectedActualAmountList(
					util.getWebElementsValue(budgetSummary.budget_Summary_CategorySectionCatgeoryBudgetedAmt1(
							PropsUtil.getDataPropertyValue("Budget_Step2_FlexibleInput"))),
					PropsUtil.getDataPropertyValue("Budget_Summary3MonthFlexibleSpendingBudgetLabels"),
					"Flexible Spending Budget Amount Labels");
			util.assertExpectedActualAmountList(
					util.getWebElementsValue(budgetSummary.budget_Summary_CategorySectionCatgeorySpentAmt(
							PropsUtil.getDataPropertyValue("Budget_Step2_FlexibleInput"))),
					PropsUtil.getDataPropertyValue("Budget_Summary3MonthFlexibleSpendingSpentLabels"),
					"Flexible Spending Spent Amount Labels");
		}
		util.assertExpectedActualAmountList(
				util.getWebElementsValue(budgetSummary.budget_Summary_CategorySectionCatgeoryLeftAmt(
						PropsUtil.getDataPropertyValue("Budget_Step2_FlexibleInput"))),
				PropsUtil.getDataPropertyValue("Budget_Summary3MonthFlexibleSpendingLeftOverLabels"),
				"Flexible Spending Left Over Amount Labels");

	}

	@Test(description = "AT-110220,AT-110149,AT-110209,AT-110152: Verify last 6 month data", priority = 14, dependsOnMethods = {
			"verifyAllTimeFilter" })
	public void verifylast6monthsSelection() {
		if (Config.appFlag.equalsIgnoreCase("app") || Config.appFlag.equalsIgnoreCase("emulator")) {

			SeleniumUtil.click(budgetSummary.budget_Trends_BackIconMobile());
		}

		SeleniumUtil.click(budgetSummary
				.budget_Summery_CustomedateFilterName(PropsUtil.getDataPropertyValue("Budget_Summery6MonthLabel")));
		SeleniumUtil.waitForPageToLoad();
		Assert.assertEquals(budgetSummary.budget_Summery_TimeFilter().getText().trim(),
				PropsUtil.getDataPropertyValue("Budget_Summery6MonthLabel"), "time filter label is not displayed");
		Assert.assertEquals(budgetSummary.budget_Summery_TimeFilterDate().getText().trim(),
				budgetSummary.getdateMMMYYYY(-5) + " " + "-" + " " + budgetSummary.getdateMMMYYYY(0),
				"time filter date value is not displayed");
		Assert.assertTrue(budgetSummary
				.budget_Summery_Trendcheckbox(PropsUtil.getDataPropertyValue("Budget_LegendIncomeLbl")).isDisplayed(),
				"Legend Income Label");
		Assert.assertTrue(budgetSummary
				.budget_Summery_Trendcheckbox(PropsUtil.getDataPropertyValue("Budget_LegendSpendingLbl")).isDisplayed(),
				"Legend Spending Label");
		util.assertExpectedActualList(util.getWebElementsValue(budgetSummary.budget_Summery_TrendcheckboxName()),
				PropsUtil.getDataPropertyValue("Budget_LegendIncomeSpendingLbl"),
				"Income and spending is not displayed");
		Assert.assertEquals(budgetSummary.budget_Summery_TrendNoncheckboxName().getText().trim(),
				PropsUtil.getDataPropertyValue("Budget_LegendBudgetLbl"));
		util.assertIsDisplayed(budgetSummary.budget_Summery_TrendXaxix(), "xaxis month value is not displayed");

		util.assertIsDisplayed(budgetSummary.budget_Summery_TrendHighChart(), "bar chart is not displayed");

	}

	@Test(description = "AT-110220,AT-110209,AT-110213: Verify last 6 month data", priority = 15, dependsOnMethods = {
			"verifylast6monthsSelection" }, enabled = false)
	public void verify6monthsIncomeCategory() {
		if (Config.appFlag.equalsIgnoreCase("app") || Config.appFlag.equalsIgnoreCase("emulator")) {
			SeleniumUtil.click(budgetSummary.budget_Trend_ViewDetailsBtn());
		}
		util.assertExpectedActualList(
				util.getWebElementsValue(budgetSummary.budget_Summary_CategorySectionCatgeoryName(
						PropsUtil.getDataPropertyValue("Budget_Step2_IncomeInput"))),
				PropsUtil.getDataPropertyValue("Budget_Summary6MonthIncomeCategoryLabels"),
				"All the Income Category Labels should reflect");

		if (Config.appFlag.equalsIgnoreCase("web") || Config.appFlag.equalsIgnoreCase("false")) {

			util.assertExpectedActualAmountList(
					util.getWebElementsValue(budgetSummary.budget_Summary_CategorySectionCatgeoryBudgetedAmt1(
							PropsUtil.getDataPropertyValue("Budget_Step2_IncomeInput"))),
					PropsUtil.getDataPropertyValue("Budget_Summary6MonthIncomeCategoryBudgetLabels"), "Budget Amount");
			util.assertExpectedActualAmountList(
					util.getWebElementsValue(budgetSummary.budget_Summary_CategorySectionCatgeorySpentAmt(
							PropsUtil.getDataPropertyValue("Budget_Step2_IncomeInput"))),
					PropsUtil.getDataPropertyValue("Budget_Summary6MonthIncomeCategoryEarnedLabels"), "Earned amount");

		}
		util.assertExpectedActualAmountList(
				util.getWebElementsValue(budgetSummary.budget_Summary_CategorySectionCatgeoryLeftAmt(
						PropsUtil.getDataPropertyValue("Budget_Step2_IncomeInput"))),
				PropsUtil.getDataPropertyValue("Budget_Summary6MonthIncomeCategoryLeftOverLabels"), "Left Over Amount");

	}

	@Test(description = "AT-110220,AT-110209,AT-110179: Verify last 6 month data", priority = 16, dependsOnMethods = {
			"verifylast6monthsSelection" }, enabled = false)
	public void verify6monthsBillsCategory() {
		util.assertExpectedActualList(
				util.getWebElementsValue(budgetSummary.budget_Summary_CategorySectionCatgeoryName(
						PropsUtil.getDataPropertyValue("Budget_Step2_BillsInput"))),
				PropsUtil.getDataPropertyValue("Budget_Summary6MonthBillsCategoryLabels"),
				"Bill category for last month");
		if (Config.appFlag.equalsIgnoreCase("web") || Config.appFlag.equalsIgnoreCase("false")) {

			util.assertExpectedActualAmountList(
					util.getWebElementsValue(budgetSummary.budget_Summary_CategorySectionCatgeoryBudgetedAmt1(
							PropsUtil.getDataPropertyValue("Budget_Step2_BillsInput"))),
					PropsUtil.getDataPropertyValue("Budget_Summary6MonthBillsCategoryBudgetLabels"),
					"Budget amount in Bills");
			util.assertExpectedActualAmountList(
					util.getWebElementsValue(budgetSummary.budget_Summary_CategorySectionCatgeorySpentAmt(
							PropsUtil.getDataPropertyValue("Budget_Step2_BillsInput"))),
					PropsUtil.getDataPropertyValue("Budget_Summary6MonthBillsCategorySpentLabels"),
					"Spent amount in Bills");
		}
		util.assertExpectedActualAmountList(
				util.getWebElementsValue(budgetSummary.budget_Summary_CategorySectionCatgeoryLeftAmt(
						PropsUtil.getDataPropertyValue("Budget_Step2_BillsInput"))),
				PropsUtil.getDataPropertyValue("Budget_Summary6MonthBillsCategoryLeftOverLabels"),
				"Left over amount on Bills");

	}

	@Test(description = "AT-110220,AT-110209: Verify last 6 month data", priority = 17, dependsOnMethods = {
			"verifylast6monthsSelection" }, enabled = false)
	public void verify6monthsFlexibleCategory() {
		util.assertExpectedActualList(
				util.getWebElementsValue(budgetSummary.budget_Summary_CategorySectionCatgeoryName(
						PropsUtil.getDataPropertyValue("Budget_Step2_FlexibleInput"))),
				PropsUtil.getDataPropertyValue("Budget_Summary6MonthFlexibleSpendingCategoryLabels"),
				"6 month Spending Label");
		if (Config.appFlag.equalsIgnoreCase("web") || Config.appFlag.equalsIgnoreCase("false")) {

			util.assertExpectedActualAmountList(
					util.getWebElementsValue(budgetSummary.budget_Summary_CategorySectionCatgeoryBudgetedAmt1(
							PropsUtil.getDataPropertyValue("Budget_Step2_FlexibleInput"))),
					PropsUtil.getDataPropertyValue("Budget_Summary6MonthFlexibleSpendingCategoryBudgetLabels"),
					"6 month Spending Budget amountLabel");
			util.assertExpectedActualAmountList(
					util.getWebElementsValue(budgetSummary.budget_Summary_CategorySectionCatgeorySpentAmt(
							PropsUtil.getDataPropertyValue("Budget_Step2_FlexibleInput"))),
					PropsUtil.getDataPropertyValue("Budget_Summary6MonthFlexibleSpendingCategorySpentLabels"),
					"6 month Spending Spent amountLabel");
		}
		util.assertExpectedActualAmountList(
				util.getWebElementsValue(budgetSummary.budget_Summary_CategorySectionCatgeoryLeftAmt(
						PropsUtil.getDataPropertyValue("Budget_Step2_FlexibleInput"))),
				PropsUtil.getDataPropertyValue("Budget_Summary6MonthFlexibleSpendingCategoryLeftOverLabels"),
				"6 month Spending Left Over amountLabel");

	}

	@Test(description = "AT-110220,AT-110209,AT-110152: Verify This Year data", priority = 18)
	public void verifyThisYearmonthsSelection() {

		SeleniumUtil.waitForPageToLoad();
		if (Config.appFlag.equalsIgnoreCase("web") || Config.appFlag.equalsIgnoreCase("false")) {
			SeleniumUtil.click(budgetSummary.budget_Summery_CustomedateFilterName(
					PropsUtil.getDataPropertyValue("Budget_SummeryThisYearLabel")));
			Assert.assertEquals(budgetSummary.budget_Summery_TimeFilter().getText().trim(),
					PropsUtil.getDataPropertyValue("Budget_SummeryThisYearLabel"),
					"time filter label is not displayed");
			Assert.assertEquals(budgetSummary.budget_Summery_TimeFilterDate().getText().trim(),
					budgetSummary.getdateMMMYYYY(-6) + " " + "-" + " " + budgetSummary.getdateMMMYYYY(0),
					"time filter date value is not displayed");

			SeleniumUtil.waitForPageToLoad();
			Assert.assertTrue(
					budgetSummary.budget_Summery_Trendcheckbox(PropsUtil.getDataPropertyValue("Budget_LegendIncomeLbl"))
							.isDisplayed(),
					"Legend Income Lbl");
			Assert.assertTrue(budgetSummary
					.budget_Summery_Trendcheckbox(PropsUtil.getDataPropertyValue("Budget_LegendSpendingLbl"))
					.isDisplayed(), "Legend SPending Lbl");
			util.assertExpectedActualList(util.getWebElementsValue(budgetSummary.budget_Summery_TrendcheckboxName()),
					PropsUtil.getDataPropertyValue("Budget_LegendIncomeSpendingLbl"),
					"Income and spending is not displayed");
			Assert.assertEquals(budgetSummary.budget_Summery_TrendNoncheckboxName().getText().trim(),
					PropsUtil.getDataPropertyValue("Budget_LegendBudgetLbl"));
			util.assertIsDisplayed(budgetSummary.budget_Summery_TrendXaxix(), "xaxis month value is not displayed");

			util.assertIsDisplayed(budgetSummary.budget_Summery_TrendHighChart(), "bar chart is not displayed");

		}
	}

	@Test(description = "AT-110220,AT-110209: Verify This Year data", priority = 19, dependsOnMethods = {
			"verifyThisYearmonthsSelection" }, enabled = false)
	public void verifyThisYearsIncomeCategory() {
		if (Config.appFlag.equalsIgnoreCase("web") || Config.appFlag.equalsIgnoreCase("false")) {
			util.assertExpectedActualList(
					util.getWebElementsValue(budgetSummary.budget_Summary_CategorySectionCatgeoryName(
							PropsUtil.getDataPropertyValue("Budget_Step2_IncomeInput"))),
					PropsUtil.getDataPropertyValue("Budget_SummaryThisYearIncomeCategoryLabels"),
					"All the Income Category Labels should reflect");
			util.assertExpectedActualAmountList(
					util.getWebElementsValue(budgetSummary.budget_Summary_CategorySectionCatgeoryBudgetedAmt1(
							PropsUtil.getDataPropertyValue("Budget_Step2_IncomeInput"))),
					PropsUtil.getDataPropertyValue("Budget_SummaryThisYearIncomeCategoryBudgetLabels"),
					"Budget Amount");
			util.assertExpectedActualAmountList(
					util.getWebElementsValue(budgetSummary.budget_Summary_CategorySectionCatgeorySpentAmt(
							PropsUtil.getDataPropertyValue("Budget_Step2_IncomeInput"))),
					PropsUtil.getDataPropertyValue("Budget_SummaryThisYearIncomeCategoryEarnedLabels"),
					"Earned amount");
			util.assertExpectedActualAmountList(
					util.getWebElementsValue(budgetSummary.budget_Summary_CategorySectionCatgeoryLeftAmt(
							PropsUtil.getDataPropertyValue("Budget_Step2_IncomeInput"))),
					PropsUtil.getDataPropertyValue("Budget_SummaryThisYearIncomeCategoryLeftOverLabels"),
					"Left Over Amount");
		}
	}

	@Test(description = "AT-110220,AT-110209: Verify This Year data", priority = 20, dependsOnMethods = {
			"verifyThisYearmonthsSelection" }, enabled = false)
	public void verifyThisYearsBillsCategory() {
		if (Config.appFlag.equalsIgnoreCase("web") || Config.appFlag.equalsIgnoreCase("false")) {
			util.assertExpectedActualList(
					util.getWebElementsValue(budgetSummary.budget_Summary_CategorySectionCatgeoryName(
							PropsUtil.getDataPropertyValue("Budget_Step2_BillsInput"))),
					PropsUtil.getDataPropertyValue("Budget_SummaryThisYearBillsCategoryLabels"),
					"Bill category for last month");
			util.assertExpectedActualAmountList(
					util.getWebElementsValue(budgetSummary.budget_Summary_CategorySectionCatgeoryBudgetedAmt1(
							PropsUtil.getDataPropertyValue("Budget_Step2_BillsInput"))),
					PropsUtil.getDataPropertyValue("Budget_SummaryThisYearBillsCategoryBudgetLabels"),
					"Budget amount in Bills");
			util.assertExpectedActualAmountList(
					util.getWebElementsValue(budgetSummary.budget_Summary_CategorySectionCatgeorySpentAmt(
							PropsUtil.getDataPropertyValue("Budget_Step2_BillsInput"))),
					PropsUtil.getDataPropertyValue("Budget_SummaryThisYearBillsCategorySpentLabels"),
					"Spent amount in Bills");
			util.assertExpectedActualAmountList(
					util.getWebElementsValue(budgetSummary.budget_Summary_CategorySectionCatgeoryLeftAmt(
							PropsUtil.getDataPropertyValue("Budget_Step2_BillsInput"))),
					PropsUtil.getDataPropertyValue("Budget_SummaryThisYearBillsCategoryLeftOverLabels"),
					"Left over amount on Bills");
		}
	}

	@Test(description = "AT-110220,AT-110209: Verify This Year data", priority = 21, dependsOnMethods = {
			"verifyThisYearmonthsSelection" }, enabled = false)
	public void verifyThisYearsFlexibleCategory() {
		if (Config.appFlag.equalsIgnoreCase("web") || Config.appFlag.equalsIgnoreCase("false")) {
			util.assertExpectedActualList(
					util.getWebElementsValue(budgetSummary.budget_Summary_CategorySectionCatgeoryName(
							PropsUtil.getDataPropertyValue("Budget_Step2_FlexibleInput"))),
					PropsUtil.getDataPropertyValue("Budget_SummaryThisYearFlexibleSpendingCategoryLabels"),
					"This Year Flexible Spending Labels");
			util.assertExpectedActualAmountList(
					util.getWebElementsValue(budgetSummary.budget_Summary_CategorySectionCatgeoryBudgetedAmt1(
							PropsUtil.getDataPropertyValue("Budget_Step2_FlexibleInput"))),
					PropsUtil.getDataPropertyValue("Budget_SummaryThisYearFlexibleSpendingCategoryBudgetLabels"),
					"This Year Flexible Spending budget amount Labels");
			util.assertExpectedActualAmountList(
					util.getWebElementsValue(budgetSummary.budget_Summary_CategorySectionCatgeorySpentAmt(
							PropsUtil.getDataPropertyValue("Budget_Step2_FlexibleInput"))),
					PropsUtil.getDataPropertyValue("Budget_SummaryThisYearFlexibleSpendingCategorySpentLabels"),
					"This Year Flexible Spending Spent amount Labels");
			util.assertExpectedActualAmountList(
					util.getWebElementsValue(budgetSummary.budget_Summary_CategorySectionCatgeoryLeftAmt(
							PropsUtil.getDataPropertyValue("Budget_Step2_FlexibleInput"))),
					PropsUtil.getDataPropertyValue("Budget_SummaryThisYearFlexibleSpendingCategoryLeftOverLabels"),
					"This Year Flexible Spending Left over amount Labels");
		}
	}

	@Test(description = "AT-110206,AT-110152,AT-110173: Verify last 12 months data", priority = 22)
	public void verifylast12monthsSelection() {
		if (Config.appFlag.equalsIgnoreCase("web") || Config.appFlag.equalsIgnoreCase("false")) {
			SeleniumUtil.click(budgetSummary.budget_Summery_CustomedateFilterName(
					PropsUtil.getDataPropertyValue("Budget_Summery12MonthLabel")));
			Assert.assertEquals(budgetSummary.budget_Summery_TimeFilter().getText().trim(),
					PropsUtil.getDataPropertyValue("Budget_Summery12MonthLabel"), "time filter label is not displayed");
			Assert.assertEquals(budgetSummary.budget_Summery_TimeFilterDate().getText().trim(),
					budgetSummary.getdateMMMYYYY(-11) + " " + "-" + " " + budgetSummary.getdateMMMYYYY(0),
					"time filter date value is not displayed");

			SeleniumUtil.waitForPageToLoad();

			Assert.assertTrue(
					budgetSummary.budget_Summery_Trendcheckbox(PropsUtil.getDataPropertyValue("Budget_LegendIncomeLbl"))
							.isDisplayed(),
					"Income Legend Lbl");
			Assert.assertTrue(budgetSummary
					.budget_Summery_Trendcheckbox(PropsUtil.getDataPropertyValue("Budget_LegendSpendingLbl"))
					.isDisplayed(), "Spending Legend Lbl");
			util.assertExpectedActualList(util.getWebElementsValue(budgetSummary.budget_Summery_TrendcheckboxName()),
					PropsUtil.getDataPropertyValue("Budget_LegendIncomeSpendingLbl"),
					"Income and spending is not displayed");
			Assert.assertEquals(budgetSummary.budget_Summery_TrendNoncheckboxName().getText().trim(),
					PropsUtil.getDataPropertyValue("Budget_LegendBudgetLbl"));
			util.assertIsDisplayed(budgetSummary.budget_Summery_TrendXaxix(), "xaxis month value is not displayed");

			util.assertIsDisplayed(budgetSummary.budget_Summery_TrendHighChart(), "bar chart is not displayed");

		}
	}

	@Test(description = "AT-110206: Verify last 12 months data", priority = 23, dependsOnMethods = {
			"verifylast12monthsSelection" }, enabled = false)
	public void verify12monthsIncomeCategory() {
		if (Config.appFlag.equalsIgnoreCase("web") || Config.appFlag.equalsIgnoreCase("false")) {
			util.assertExpectedActualList(
					util.getWebElementsValue(budgetSummary.budget_Summary_CategorySectionCatgeoryName(
							PropsUtil.getDataPropertyValue("Budget_Step2_IncomeInput"))),
					PropsUtil.getDataPropertyValue("Budget_Summary12MonthIncomeCategoryLabels"),
					"All the Income Category Labels should reflect");
			util.assertExpectedActualAmountList(
					util.getWebElementsValue(budgetSummary.budget_Summary_CategorySectionCatgeoryBudgetedAmt1(
							PropsUtil.getDataPropertyValue("Budget_Step2_IncomeInput"))),
					PropsUtil.getDataPropertyValue("Budget_Summary12MonthIncomeCategoryBudgetLabels"), "Budget Amount");
			util.assertExpectedActualAmountList(
					util.getWebElementsValue(budgetSummary.budget_Summary_CategorySectionCatgeorySpentAmt(
							PropsUtil.getDataPropertyValue("Budget_Step2_IncomeInput"))),
					PropsUtil.getDataPropertyValue("Budget_Summary12MonthIncomeCategoryEarnedLabels"), "Earned amount");
			util.assertExpectedActualAmountList(
					util.getWebElementsValue(budgetSummary.budget_Summary_CategorySectionCatgeoryLeftAmt(
							PropsUtil.getDataPropertyValue("Budget_Step2_IncomeInput"))),
					PropsUtil.getDataPropertyValue("Budget_Summary12MonthIncomeCategoryLeftOverLabels"),
					"Left Over Amount");
		}
	}

	@Test(description = "AT-110206: Verify last 12 months data", priority = 24, dependsOnMethods = {
			"verifylast12monthsSelection" }, enabled = false)
	public void verify12monthsBillsCategory() {
		if (Config.appFlag.equalsIgnoreCase("web") || Config.appFlag.equalsIgnoreCase("false")) {
			util.assertExpectedActualList(
					util.getWebElementsValue(budgetSummary.budget_Summary_CategorySectionCatgeoryName(
							PropsUtil.getDataPropertyValue("Budget_Step2_BillsInput"))),
					PropsUtil.getDataPropertyValue("Budget_Summary12MonthBillsCategoryLabels"),
					"Bill category for last month");
			util.assertExpectedActualAmountList(
					util.getWebElementsValue(budgetSummary.budget_Summary_CategorySectionCatgeoryBudgetedAmt1(
							PropsUtil.getDataPropertyValue("Budget_Step2_BillsInput"))),
					PropsUtil.getDataPropertyValue("Budget_Summary12MonthBillsCategoryBudgetLabels"),
					"Budget amount in Bills");
			util.assertExpectedActualAmountList(
					util.getWebElementsValue(budgetSummary.budget_Summary_CategorySectionCatgeorySpentAmt(
							PropsUtil.getDataPropertyValue("Budget_Step2_BillsInput"))),
					PropsUtil.getDataPropertyValue("Budget_Summary12MonthBillsCategorySpentLabels"),
					"Spent amount in Bills");
			util.assertExpectedActualAmountList(
					util.getWebElementsValue(budgetSummary.budget_Summary_CategorySectionCatgeoryLeftAmt(
							PropsUtil.getDataPropertyValue("Budget_Step2_BillsInput"))),
					PropsUtil.getDataPropertyValue("Budget_Summary12MonthBillsCategoryLeftOverLabels"),
					"Left over amount on Bills");
		}
	}

	@Test(description = "AT-110206: Verify last 12 months data", priority = 25, dependsOnMethods = {
			"verifylast12monthsSelection" }, enabled = false)
	public void verify12monthsFlexibleCategory() {
		if (Config.appFlag.equalsIgnoreCase("web") || Config.appFlag.equalsIgnoreCase("false")) {

			util.assertExpectedActualList(
					util.getWebElementsValue(budgetSummary.budget_Summary_CategorySectionCatgeoryName(
							PropsUtil.getDataPropertyValue("Budget_Step2_FlexibleInput"))),
					PropsUtil.getDataPropertyValue("Budget_Summary12MonthFlexibleSpendingCategoryLabels"),
					"12 months Flexible Spending Labels");
			util.assertExpectedActualAmountList(
					util.getWebElementsValue(budgetSummary.budget_Summary_CategorySectionCatgeoryBudgetedAmt1(
							PropsUtil.getDataPropertyValue("Budget_Step2_FlexibleInput"))),
					PropsUtil.getDataPropertyValue("Budget_Summary12MonthFlexibleSpendingCategoryBudgetLabels"),
					"12 months Flexible Spending budget amount Labels");
			util.assertExpectedActualAmountList(
					util.getWebElementsValue(budgetSummary.budget_Summary_CategorySectionCatgeorySpentAmt(
							PropsUtil.getDataPropertyValue("Budget_Step2_FlexibleInput"))),
					PropsUtil.getDataPropertyValue("Budget_Summary12MonthFlexibleSpendingCategorySpentLabels"),
					"12 months Flexible Spending spent amount Labels");
			util.assertExpectedActualAmountList(
					util.getWebElementsValue(budgetSummary.budget_Summary_CategorySectionCatgeoryLeftAmt(
							PropsUtil.getDataPropertyValue("Budget_Step2_FlexibleInput"))),
					PropsUtil.getDataPropertyValue("Budget_Summary12MonthFlexibleSpendingCategoryLeftOverLabels"),
					"12 months Flexible Spending Left over amount Labels");
		}
	}

	@Test(description = "AT-110210,AT-110211: Verifying details in custom date pop up", priority = 26, dependsOnMethods = {
			"verifylast12monthsSelection" }, enabled = false)
	public void verifyCustomDatePopUp() {

		SeleniumUtil.click(budgetSummary.budget_Summery_CustomedateFilterName(
				PropsUtil.getDataPropertyValue("Budget_SummeryCustomMonthLabel")));
		SeleniumUtil.waitForPageToLoad();

		if (Config.appFlag.equalsIgnoreCase("app") || Config.appFlag.equalsIgnoreCase("emulator")) {

			Assert.assertEquals(budgetSummary.budget_Summery_DoneMobile().getText(),
					PropsUtil.getDataPropertyValue("Budget_SummeryDoneLabelMobile"), "Header is not displayed");

			Assert.assertTrue(budgetSummary.budget_Summery_BackIconMobile().isDisplayed());
			Assert.assertEquals(budgetSummary.budget_Summery_CustomHeaderMobile().getText(),
					PropsUtil.getDataPropertyValue("Budget_SummaryCustomPopUpHeader"), "Header is not displayed");
		}

		else {
			Assert.assertEquals(budgetSummary.transactionCustomDateFilterUpdate().getText().trim(),
					PropsUtil.getDataPropertyValue("Budget_SummaryCustomPopUpUpdateLbl"), "UPDATE is not displayed");
			Assert.assertEquals(filter.transactionCustomDateFilterStartDateLabel().getText(),
					PropsUtil.getDataPropertyValue("Budget_SummaryCustomPopUpFromLbl"), "From is not displayed");
			Assert.assertTrue(budgetCreateBudgetLoc.budget_Steps2_PopupCloseIcon().isDisplayed(),
					"Clsoe icon should not displayed");
			Assert.assertEquals(budgetCreateBudgetLoc.budget_Steps2_PopupHeader().getText(),
					PropsUtil.getDataPropertyValue("Budget_SummaryCustomPopUpHeader"), "Header is not displayed");

		}

		Assert.assertEquals(filter.transactionCustomDateFilterEndDateLabel().getText(),
				PropsUtil.getDataPropertyValue("Budget_SummaryCustomPopUpToLbl"), "To is not displayed");

		Assert.assertTrue(filter.transactionCustomDateFilterStartDate().isDisplayed(), "Start date is not displayed");
		Assert.assertTrue(filter.transactionCustomDateFilterEndDate().isDisplayed(), "end date is not displayed");

	}

	@Test(description = "AT-110212,AT-110218,AT-110219: Verify range should be 12 months in custom pop up", priority = 27, enabled = false)
	public void verifyCustomDatePopUp12month() {

		SeleniumUtil.click(budgetSummary.budget_Summery_CustomMonthFromButton());

		SeleniumUtil.click(budgetSummary.budget_Summery_CustomedatePreviousYear().get(0));
		SeleniumUtil.waitForPageToLoad();

		SeleniumUtil.click(budgetSummary.budget_Summery_CustomedateStartDateMonth().get(5));

		SeleniumUtil.waitForPageToLoad();

		SeleniumUtil.click(budgetSummary.budget_Summery_CustomMonthToButton());
		// SeleniumUtil.click(budgetSummary.budget_Summery_CustomedateNextYear());
		SeleniumUtil.click(budgetSummary.budget_Summery_CustomedateEndDateMonth().get(5));
		SeleniumUtil.waitForPageToLoad();

		SeleniumUtil.click(budgetSummary.transactionCustomDateFilterUpdate());

		SeleniumUtil.waitForPageToLoad();

		Assert.assertEquals(filter.transactionCustomDateFilterErrorMsg1().getText(),
				PropsUtil.getDataPropertyValue("Budget_CustomErrorMessage"), "Error Text is npt displayed");

		if (Config.appFlag.equalsIgnoreCase("app") || Config.appFlag.equalsIgnoreCase("emulator")) {

			Assert.assertEquals(filter.transactionCustomDateFilterErrorMsg2().getText(),
					PropsUtil.getDataPropertyValue("Budget_CustomErrorMessageMobile"), "Error Text is npt displayed");

		} else

		{

			Assert.assertEquals(filter.transactionCustomDateFilterErrorMsg2().getText(),
					PropsUtil.getDataPropertyValue("Budget_CustomErrorMessage1"), "Error Text is npt displayed");

		}
	}

	@Test(description = "AT-110212: Verify 12 months of data", priority = 28, dependsOnMethods = {
			"verifyCustomDatePopUp12month" }, enabled = false)
	public void verifyCustomDatePopUp12monthBills()

	{
		if (Config.appFlag.equals("web") || Config.appFlag.equals("false")) {

			SeleniumUtil.click(budgetSummary.budget_Summery_CustomMonthToButton());
			SeleniumUtil.click(budgetSummary.budget_Summery_CustomedateEndDateMonth().get(4));
			SeleniumUtil.waitForPageToLoad();

			SeleniumUtil.click(budgetSummary.transactionCustomDateFilterUpdate());

			SeleniumUtil.waitForPageToLoad();
			util.assertExpectedActualList(
					util.getWebElementsValue(budgetSummary.budget_Summary_CategorySectionCatgeoryName(
							PropsUtil.getDataPropertyValue("Budget_Step2_BillsInput"))),
					PropsUtil.getDataPropertyValue("Budget_Summary12MonthBillsCategoryLabels"),
					"Bill category for last month");
			util.assertExpectedActualAmountList(
					util.getWebElementsValue(budgetSummary.budget_Summary_CategorySectionCatgeoryBudgetedAmt1(
							PropsUtil.getDataPropertyValue("Budget_Step2_BillsInput"))),
					PropsUtil.getDataPropertyValue("Budget_Custom12MonthBillsCategoryBudgetLabels"),
					"Budget amount in Bills");
			util.assertExpectedActualAmountList(
					util.getWebElementsValue(budgetSummary.budget_Summary_CategorySectionCatgeorySpentAmt(
							PropsUtil.getDataPropertyValue("Budget_Step2_BillsInput"))),
					PropsUtil.getDataPropertyValue("Budget_Custom12MonthBillsCategorySpentLabels"),
					"Spent amount in Bills");
			util.assertExpectedActualAmountList(
					util.getWebElementsValue(budgetSummary.budget_Summary_CategorySectionCatgeoryLeftAmt(
							PropsUtil.getDataPropertyValue("Budget_Step2_BillsInput"))),
					PropsUtil.getDataPropertyValue("Budget_Custom12MonthBillsCategoryLeftOverLabels"),
					"Left over amount on Bills");
		}
	}

	@Test(description = "AT-110212: Verify 12 months of data", priority = 29, dependsOnMethods = {
			"verifyCustomDatePopUp12month" }, enabled = false)
	public void verifyCustom12monthsFlexibleCategory() {
		if (Config.appFlag.equalsIgnoreCase("web") || Config.appFlag.equalsIgnoreCase("false")) {

			util.assertExpectedActualList(
					util.getWebElementsValue(budgetSummary.budget_Summary_CategorySectionCatgeoryName(
							PropsUtil.getDataPropertyValue("Budget_Step2_FlexibleInput"))),
					PropsUtil.getDataPropertyValue("Budget_Summary12MonthFlexibleSpendingCategoryLabels"),
					"12 months Flexible Spending  Labels");
			util.assertExpectedActualAmountList(
					util.getWebElementsValue(budgetSummary.budget_Summary_CategorySectionCatgeoryBudgetedAmt1(
							PropsUtil.getDataPropertyValue("Budget_Step2_FlexibleInput"))),
					PropsUtil.getDataPropertyValue("Budget_Custom12MonthFlexibleSpendingCategoryBudgetLabels"),
					"12 months Flexible Spending budget amount Labels");
			util.assertExpectedActualAmountList(
					util.getWebElementsValue(budgetSummary.budget_Summary_CategorySectionCatgeorySpentAmt(
							PropsUtil.getDataPropertyValue("Budget_Step2_FlexibleInput"))),
					PropsUtil.getDataPropertyValue("Budget_Custom12MonthFlexibleSpendingCategorySpentLabels"),
					"12 months Flexible Spending spent amount Labels");
			util.assertExpectedActualAmountList(
					util.getWebElementsValue(budgetSummary.budget_Summary_CategorySectionCatgeoryLeftAmt(
							PropsUtil.getDataPropertyValue("Budget_Step2_FlexibleInput"))),
					PropsUtil.getDataPropertyValue("Budget_Custom12MonthFlexibleSpendingCategoryLeftOverLabels"),
					"12 months Flexible Spending Left Over amount Labels");
		}
	}

	@Test(description = "AT-110216,AT-110217: Verify error message in custom pop up", priority = 30, dependsOnMethods = {
			"verifyCustomDatePopUp12month" }, enabled = false)
	public void verifyCustomDateSelection()

	{
		if (Config.appFlag.equalsIgnoreCase("web") || Config.appFlag.equalsIgnoreCase("false")) {
			SeleniumUtil.click(budgetSummary.budget_Summery_CustomedateFilterName(
					PropsUtil.getDataPropertyValue("Budget_SummeryCustomMonthLabel")));

			SeleniumUtil.click(budgetSummary.budget_Summery_CustomMonthToButton());
			SeleniumUtil.click(budgetSummary.budget_Summery_CustomedatePreviousYear().get(1));
			SeleniumUtil.waitForPageToLoad();
			SeleniumUtil.click(budgetSummary.budget_Summery_CustomedateEndDateMonth().get(4));
			SeleniumUtil.waitForPageToLoad();
			SeleniumUtil.click(budgetSummary.transactionCustomDateFilterUpdate());

			SeleniumUtil.waitForPageToLoad();

			Assert.assertEquals(filter.transactionCustomDateFilterErrorMsg1().getText(),
					PropsUtil.getDataPropertyValue("Budget_CustomErrorMessage"), "Error Text is npt displayed");

			Assert.assertEquals(filter.transactionCustomDateFilterErrorMsg2().getText(),
					PropsUtil.getDataPropertyValue("Budget_CustomErrorMessage2"), "Error Text is npt displayed");
		}
	}

	@Test(description = "AT-110216,AT-110217: Same month selection", priority = 31, dependsOnMethods = {
			"verifyCustomDateSelection" }, enabled = false)
	public void verifyCustomSameMonthSelection() {
		if (Config.appFlag.equalsIgnoreCase("web") || Config.appFlag.equalsIgnoreCase("false")) {

			SeleniumUtil.click(budgetSummary.budget_Summery_CustomMonthFromButton());
			SeleniumUtil.click(budgetSummary.budget_Summery_CustomedateNextYear().get(0));
			SeleniumUtil.waitForPageToLoad();
			SeleniumUtil.click(budgetSummary.budget_Summery_CustomedateStartDateMonth().get(4));

			SeleniumUtil.waitForPageToLoad();

			SeleniumUtil.click(budgetSummary.budget_Summery_CustomMonthToButton());
			// SeleniumUtil.click(budgetSummary.budget_Summery_CustomedatePreviousYear().get(1));
			SeleniumUtil.click(budgetSummary.budget_Summery_CustomedateNextYear().get(1));

			SeleniumUtil.click(budgetSummary.budget_Summery_CustomedateEndDateMonth().get(4));

			SeleniumUtil.waitForPageToLoad();

			SeleniumUtil.click(filter.transactionCustomDateFilterUpdate());

			SeleniumUtil.waitForPageToLoad();
			util.assertExpectedActualList(
					util.getWebElementsValue(budgetSummary.budget_Summary_CategorySectionCatgeoryName(
							PropsUtil.getDataPropertyValue("Budget_Step2_BillsInput"))),
					PropsUtil.getDataPropertyValue("Budget_SummaryCustomMonthBillsCategoryLabels"),
					"Bill category for last month");
			util.assertExpectedActualAmountList(
					util.getWebElementsValue(budgetSummary.budget_Summary_CategorySectionCatgeoryBudgetedAmt1(
							PropsUtil.getDataPropertyValue("Budget_Step2_BillsInput"))),
					PropsUtil.getDataPropertyValue("Budget_CustomMonthBillsCategoryBudgetLabels"),
					"Budget amount in Bills");
			util.assertExpectedActualAmountList(
					util.getWebElementsValue(budgetSummary.budget_Summary_CategorySectionCatgeorySpentAmt(
							PropsUtil.getDataPropertyValue("Budget_Step2_BillsInput"))),
					PropsUtil.getDataPropertyValue("Budget_CustomMonthBillsCategorySpentLabels"),
					"Spent amount in Bills");
			util.assertExpectedActualAmountList(
					util.getWebElementsValue(budgetSummary.budget_Summary_CategorySectionCatgeoryLeftAmt(
							PropsUtil.getDataPropertyValue("Budget_Step2_BillsInput"))),
					PropsUtil.getDataPropertyValue("Budget_CustomMonthBillsCategoryLeftOverLabels"),
					"Left over amount on Bills");

		}
	}

	@Test(description = "AT-110178: Edit Transaction", priority = 32, enabled = false)
	public void verifyTxnEdited() {
		PageParser.forceNavigate("Transaction", d);
		SeleniumUtil.waitForPageToLoad();
		String IA_MTCategory8 = null;
		if (PropsUtil.getEnvPropertyValue("CategoryList43").equalsIgnoreCase("yes")) {

			IA_MTCategory8 = PropsUtil.getDataPropertyValue("Budget_Step2_MTCatgeory_43");

		} else {

			IA_MTCategory8 = PropsUtil.getDataPropertyValue("Budget_Step2_MTCatgeory");

		}
		addManualTransaction.clickMTLink();
		SeleniumUtil.waitForPageToLoad();
		addManualTransaction.createOneTimeTransaction(PropsUtil.getDataPropertyValue("Budget_Step2_MTAmount"),
				PropsUtil.getDataPropertyValue("Budget_Step2_MTDescription"),
				PropsUtil.getDataPropertyValue("Budget_Step2_MTType"),
				PropsUtil.getDataPropertyValue("Budget_Step2_MTAccount"),
				PropsUtil.getDataPropertyValue("Budget_Step2_MTFrequency"), budgetCreateBudgetLoc.getNoDaysOfMonth(-1),
				IA_MTCategory8, PropsUtil.getDataPropertyValue("Budget_MonthlymaintaineceSummerydebitTxnNote"),
				PropsUtil.getDataPropertyValue("Budget_MonthlymaintaineceSummerydebitTxnCheck"));
		SeleniumUtil.waitForPageToLoad(15000);
		PageParser.forceNavigate("Budget", d);
		SeleniumUtil.waitForPageToLoad();

		SeleniumUtil.click(budgetSummary
				.budget_Summery_CustomedateFilterName(PropsUtil.getDataPropertyValue("Budget_Summery3MonthLabel")));

		SeleniumUtil.waitForPageToLoad();

		SeleniumUtil.click(budgetSummary.budget_Summary_CategorySectionIncomeCatgeoryName().get(0));

		SeleniumUtil.waitForPageToLoad();

		budgetCreateBudgetLoc.updateTxn(PropsUtil.getDataPropertyValue("Budget_Step2_MTAmountList"),
				PropsUtil.getDataPropertyValue("Budget_Step2_MTAmountUpdated"));

		SeleniumUtil.waitForPageToLoad();
		Assert.assertEquals(budgetCreateBudgetLoc.budget_Steps2_legend3monthAvg().getText().trim(),
				PropsUtil.getDataPropertyValue("Budget_Trend_IncomeMonthlyAvg"),
				"all catgeory 3 month avg amount is not dispalyed");

	}

	@AfterClass(alwaysRun = true)
	public void closeConnection() throws SQLException {
		con.close();
	}

}
