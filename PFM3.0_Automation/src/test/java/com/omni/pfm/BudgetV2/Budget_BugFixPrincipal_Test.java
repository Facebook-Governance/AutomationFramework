/**
* Copyright (c) 2019 Yodlee Inc. All Rights Reserved. 
*
* This software is the confidential and proprietary information of
* Yodlee, Inc. Use is subject to license terms.
*
* @author mallikan
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
import com.omni.pfm.pages.BudgetV2.Budget_Summary_EditPopup_Loc;
import com.omni.pfm.pages.BudgetV2.Budget_Summary_GroupDropdown_Loc;
import com.omni.pfm.pages.BudgetV2.Budget_Summary_TableRevamp_Loc;
import com.omni.pfm.pages.Login.LoginPage;
import com.omni.pfm.testBase.TestBase;
import com.omni.pfm.utility.PropsUtil;
import com.omni.pfm.utility.SeleniumUtil;

public class Budget_BugFixPrincipal_Test extends TestBase {
	private static final Logger logger = LoggerFactory.getLogger(Budget_BugFixPrincipal_Test.class);
	AccountAddition accAddition = new AccountAddition();
	Budget_Summary_EditPopup_Loc budget_Edit;
	Budget_CreateBudget_Loc budgetCreateLoc;
	Budget_Summary_TableRevamp_Loc bud_TableRevamp;
	Budget_BudgetSummary_Loc budgetSummary;
	Budget_Summary_GroupDropdown_Loc budget_Gdd;

	@BeforeClass(alwaysRun = true)
	public void init() throws Exception {
		doInitialization("Budget");
		budgetCreateLoc = new Budget_CreateBudget_Loc(d);
		budget_Edit = new Budget_Summary_EditPopup_Loc(d);
		bud_TableRevamp = new Budget_Summary_TableRevamp_Loc(d);
		budgetSummary = new Budget_BudgetSummary_Loc(d);
		budget_Gdd = new Budget_Summary_GroupDropdown_Loc(d);
	}

	@Test(description = "AT-128126:Verify Login Happens Successfully", groups = {
			"DesktopBrowsers" }, priority = 1, enabled = true)
	public void login() throws Exception {
		LoginPage.loginMain(d, loginParameter);
		d.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		accAddition.linkAccount();
		Assert.assertTrue(accAddition.addAggregatedAccount("bbudget.site16441.5", "site16441.5"),"Account addition for username :: \"bbudget.site16441.5\" and password :: \"site16441.5\" is not working.");
		PageParser.forceNavigate("Budget", d);
		logger.info(">>>>> Click on  the GetStarted button");
		Assert.assertTrue(budgetCreateLoc.createBudgetGroup(PropsUtil.getDataPropertyValue("Budget_HouseHoldingBudget")),"Budget creation with group is not happening.");
	}

	@Test(description = "AT-128126:Verify that while deleting the budget in Income category under Income container'Minimum one income category is required to create/edit budget.' info should be displayed in that popup.", groups = {
			"DesktopBrowsers" }, priority = 2, dependsOnMethods = { "login" }, enabled = true)
	public void deleteIncomeInfoMessage() throws Exception {
		if (Config.appFlag.equals("app") || Config.appFlag.equals("emulator")) {
			PageParser.forceNavigate("Budget", d);
			SeleniumUtil.click(bud_TableRevamp.viewDetailsBtn());
		}
		budget_Edit.clickOnIncomeEditBox();
		budget_Edit.clickOnIncomeDeleteButton();
		logger.info("Message:." + budget_Edit.incomeEditDeleteMessage().getText());
		if (Config.appFlag.equals("app") || Config.appFlag.equals("emulator")) {
			Assert.assertEquals(budget_Edit.incomeEditDeleteMessage_Mob().getText(),
					PropsUtil.getDataPropertyValue("IncomeCat_Delete_message"), "Not same message");
		}
		if (Config.appFlag.equals("web") || Config.appFlag.equals("false")) {
			SeleniumUtil.assertContains(PropsUtil.getDataPropertyValue("IncomeCat_Delete_message"), budget_Edit.incomeEditDeleteMessage().getText(), "Income category delete message is not as expected");
		}
	}

	@Test(description = "AT-128118:Verify that if the user select 'This Month' in Time filter then click on any category for edit budget,", groups = {
			"DesktopBrowsers" }, priority = 3, dependsOnMethods = { "login" }, enabled = true)
	public void verifyThisMonthAvg() throws Exception {
		SeleniumUtil.click(budget_Edit.incomeEditPopUpOkBtn());
		if (Config.appFlag.equals("app") || Config.appFlag.equals("emulator")) {
			SeleniumUtil.click(budget_Edit.budgetBackBtn_Mob());
			SeleniumUtil.click(bud_TableRevamp.viewDetailsBtn());
		}
		SeleniumUtil.click(budget_Edit.EditTextBox().get(0));
		Assert.assertTrue(budget_Edit.avgPast3MonthsLegend().getText()
				.contains(PropsUtil.getDataPropertyValue("Budget_AvgPast3MonthsLegend")));
	}

	@Test(description = "AT-128119:Verify that if the user select 6 months in TimeFilter and click on any category ", groups = {
			"DesktopBrowsers" }, priority = 4, dependsOnMethods = { "login" }, enabled = true)
	public void verifySixMonthsAvg() throws Exception {
		if (Config.appFlag.equalsIgnoreCase("emulator")) {
			SeleniumUtil.click(budget_Edit.budgetBackBtn_Mob());
			SeleniumUtil.click(budget_Edit.budgetTimeFilter_Mob());
			SeleniumUtil.click(budget_Edit.timeFilterDuration_Mob().get(5));
			SeleniumUtil.click(budget_Edit.timeFilterTableView_Mob());
			SeleniumUtil.click(bud_TableRevamp.viewDetailsBtn());
			SeleniumUtil.click(budget_Edit.budgetCategories_List().get(1));
			Assert.assertTrue(budget_Edit.avgPast3MonthsLegend().getText()
					.contains(PropsUtil.getDataPropertyValue("Budget_AvgPast3MonthsLegend")));
		}
		if (Config.appFlag.equals("web") || Config.appFlag.equals("false")) {
			SeleniumUtil.click(budgetSummary
					.budget_Summery_CustomedateFilterName(PropsUtil.getDataPropertyValue("Budget_Summery6MonthLabel")));
			SeleniumUtil.waitUntilSpinnerWheelIsInvisible();
			Assert.assertEquals(budgetSummary.budget_Summery_TimeFilter().getText().trim(),
					PropsUtil.getDataPropertyValue("Budget_Summery6MonthLabel"), "time filter label is not displayed");
			SeleniumUtil.waitFor(2);
			SeleniumUtil.click(budget_Edit.budgetCategories_List().get(1));
			SeleniumUtil.assertContains(PropsUtil.getDataPropertyValue("Budget_AvgPast3MonthsLegend"),
					budget_Edit.avgPast3MonthsLegend().getText(), "Avg 3 months legend text is not as expected.");
		}
	}

	@Test(description = "AT-128120,AT-128123:Verify that if the user select 'This Year' in TimeFilter and click on any category ", groups = {
			"DesktopBrowsers" }, priority = 5, dependsOnMethods = { "login" }, enabled = true)
	public void verifyThisYearAvg() throws Exception {
		if (Config.appFlag.equals("web") || Config.appFlag.equals("false")) {
			SeleniumUtil.click(budgetSummary.budget_Summery_CustomedateFilterName(
					PropsUtil.getDataPropertyValue("Budget_SummeryThisYearLabel")));
			SeleniumUtil.waitForPageToLoad();
			SeleniumUtil.click(budget_Edit.budgetCategories_List().get(1));
			int barlength = budget_Edit.budgetTrendsBarChartMonthLabels().size();
			if (barlength > 3) {
				SeleniumUtil.assertContains(PropsUtil.getDataPropertyValue("Budget_AvgPast3MonthsLegend"),
						budget_Edit.avgPast3MonthsLegend().getText(), "Avg 3 months legend text is not as expected.");
			} else
				logger.info("This Year has less than 3 months");
		}
	}

	@Test(description = "AT-128121:Verify that if the user select '12 Months' in TimeFilter and click on any category ", groups = {
			"DesktopBrowsers" }, priority = 6, dependsOnMethods = { "login" }, enabled = true)
	public void verify12MonthsAvg() throws Exception {
		if (Config.appFlag.equals("web") || Config.appFlag.equals("false")) {
			SeleniumUtil.click(budgetSummary.budget_Summery_CustomedateFilterName(
					PropsUtil.getDataPropertyValue("Budget_Summery12MonthLabel")));
			SeleniumUtil.waitForPageToLoad();
			Assert.assertEquals(budgetSummary.budget_Summery_TimeFilter().getText().trim(),
					PropsUtil.getDataPropertyValue("Budget_Summery12MonthLabel"), "time filter label is not displayed");
			SeleniumUtil.waitForPageToLoad();
			SeleniumUtil.click(budget_Edit.budgetCategories_List().get(1));
			SeleniumUtil.waitForPageToLoad();
			SeleniumUtil.assertContains(PropsUtil.getDataPropertyValue("Budget_AvgPast3MonthsLegend"),
					budget_Edit.avgPast3MonthsLegend().getText(), "Avg 3 months legend text is not as expected.");
		}
	}

	@Test(description = "AT-128124:Verify that previous months bar chart is displayed if the income or spending there.", groups = {
			"DesktopBrowsers" }, priority = 7, dependsOnMethods = { "login" }, enabled = true)
	public void sixMonthBarChartIsPresent() throws Exception {
		if (Config.appFlag.equalsIgnoreCase("emulator")) {
			SeleniumUtil.click(budget_Edit.budgetBackBtn_Mob());
			SeleniumUtil.click(budget_Edit.budgetTimeFilter_Mob());
			SeleniumUtil.click(budget_Edit.timeFilterDuration_Mob().get(5));
			SeleniumUtil.click(budget_Edit.timeFilterTableView_Mob());
			SeleniumUtil.click(bud_TableRevamp.viewDetailsBtn());
			SeleniumUtil.click(budget_Edit.budgetCategories_List().get(1));
			SeleniumUtil.assertContains(budget_Edit.avgPast3MonthsLegend().getText(),
					PropsUtil.getDataPropertyValue("Budget_AvgPast3MonthsLegend"),
					"Avg 3 months legend text is not as expected.");
		}
		if (Config.appFlag.equals("web") || Config.appFlag.equals("false")) {
			SeleniumUtil.click(budgetSummary
					.budget_Summery_CustomedateFilterName(PropsUtil.getDataPropertyValue("Budget_Summery3MonthLabel")));
			SeleniumUtil.waitUntilSpinnerWheelIsInvisible();
			SeleniumUtil.click(budget_Edit.budgetCategories_List().get(1));
			Assert.assertFalse(budget_Edit.isBudgetCurrentMonthTransactionDisplayed(),
					"Budget current month transaction is not displayed");
		}
	}

	@Test(description = "AT-128117:Verify that if the user select 3 months or shorter duration in TimeFilter and click on any category under 'Wants','Needs' or 'Income' then Average spnding or Earning legend should hide.", groups = {
			"DesktopBrowsers" }, priority = 8, dependsOnMethods = { "login" }, enabled = true)
	public void verifyThreeMonthAvg() throws Exception {
		if (Config.appFlag.equals("app") || Config.appFlag.equals("emulator")) {
			SeleniumUtil.click(budgetSummary
					.budget_Summery_CustomedateFilterName(PropsUtil.getDataPropertyValue("Budget_Summery3MonthLabel")));
			SeleniumUtil.click(budget_Edit.budgetCategories_List().get(1));
			Assert.assertFalse(
					budget_Edit.avgPast3MonthsLegend().getText()
							.contains(PropsUtil.getDataPropertyValue("Budget_AvgPast3MonthsLegend")),
					"3 months legend text is displayed");
		}
	}

	@Test(description = "AT-128130:Verify if the user create budget without selecting the accounts then the error message should be displayed as \'At least one account has to be selected for Budgeting.'", groups = {
			"DesktopBrowsers" }, priority = 9, dependsOnMethods = { "login" }, enabled = true)
	public void infoMessageCreateBudget() throws Exception {
		if (Config.appFlag.equalsIgnoreCase("emulator")) {
			SeleniumUtil.click(budget_Edit.budgetBackBtn_Mob());
			SeleniumUtil.click(budget_Edit.moreBtnBudgetCreate_Mob());
		}
		SeleniumUtil.click(budget_Edit.CreateBudgetBtn());
		SeleniumUtil.click(budget_Edit.GroupDropdown());
		SeleniumUtil.click(budget_Edit.createGrpButton());
		SeleniumUtil.click(budget_Gdd.Group_name());
		budget_Gdd.Group_name().sendKeys("My");
		Assert.assertEquals(budget_Edit.groupNameErrorInfo().getText(),
				PropsUtil.getDataPropertyValue("Budget_GroupNameErrorMessage"), "Group name error is not displayed");
	}

	@Test(description = "AT-128131:Verify if the user create budget and entering less than 3 characters as budget group name then the error message should be displayed", groups = {
			"DesktopBrowsers" }, priority = 10, dependsOnMethods = { "infoMessageCreateBudget" }, enabled = true)
	public void accountUnselectErrorMessage() throws Exception {
		budget_Gdd.Group_name().sendKeys("Group4");
		SeleniumUtil.click(budget_Edit.selectAllAccountsBtn());
		SeleniumUtil.click(budget_Edit.selectAllAccountsBtn());
		Assert.assertEquals(budget_Edit.accountUnselectErrorMsg().getText(),
				PropsUtil.getDataPropertyValue("AccountUnselectErrorMessage"),
				"Group name un-select error message is not displayed");
	}
}