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

public class Budget_Dashboard_Test extends TestBase {
	private static final Logger logger = LoggerFactory.getLogger(Budget_Dashboard_Test.class);

	AccountAddition accAddition = new AccountAddition();
	Budget_Summary_EditPopup_Loc budget_Edit;
	Budget_CreateBudget_Loc budgetCreateLoc;
	Budget_Summary_TableRevamp_Loc bud_TableRevamp;
	Budget_BudgetSummary_Loc budgetSummary;
	Budget_Summary_GroupDropdown_Loc budget_Gdd;

	@BeforeClass(alwaysRun = true)
	public void init() throws Exception {
		doInitialization("Budget");
		budget_Gdd = new Budget_Summary_GroupDropdown_Loc(d);
		budgetCreateLoc = new Budget_CreateBudget_Loc(d);
		budget_Edit = new Budget_Summary_EditPopup_Loc(d);
		bud_TableRevamp = new Budget_Summary_TableRevamp_Loc(d);
		budgetSummary = new Budget_BudgetSummary_Loc(d);

	}

	@Test(description = "Verify Login Happens Successfully", groups = {
			"DesktopBrowsers" }, priority = 1, enabled = true)
	public void login() throws Exception {

		LoginPage.loginMain(d, loginParameter);
		SeleniumUtil.waitForPageToLoad();
		d.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		accAddition.linkAccount();
		accAddition.addAggregatedAccount("bbudget.site16441.5", "site16441.5");
		PageParser.forceNavigate("DashboardPage", d);
		SeleniumUtil.waitForPageToLoad();
		logger.info("Account Addition happened successfully");
		PageParser.forceNavigate("Budget", d);
		SeleniumUtil.waitForPageToLoad();

	}

	@Test(description = "AT-110655:Verify that when no Budget Created by user, the Widget must shown the Create Budget Button and Welcome message", groups = {
			"DesktopBrowsers" }, priority = 2, enabled = true)
	public void verifyNoBudgetMsgOnDashBudgetWidget() throws Exception {

		PageParser.forceNavigate("DashboardPage", d);
		SeleniumUtil.refresh(d);
		SeleniumUtil.refresh(d);
		SeleniumUtil.waitForPageToLoad();
		Assert.assertEquals(bud_TableRevamp.BudWidgetNoDataMessage().getText(),
				PropsUtil.getDataPropertyValue("NoDataMessageOnBudgetWidget").trim());
		Assert.assertTrue(bud_TableRevamp.BudWidgetCreateButton().isDisplayed(),
				"Verify Create Button is displayed on Widget");
		PageParser.forceNavigate("Budget", d);
		budgetCreateLoc.createBudgetGroup(PropsUtil.getDataPropertyValue("Budget_GroupName1"));

	}

	@Test(description = "AT-110661:Max we should be showing the 5 budget, if there is any more extra Budgets we show a message as \"To see budget for all account groups\" and Go To Full view button in the Widget", groups = {
			"DesktopBrowsers" }, priority = 3, enabled = true)
	public void createBudgets() throws Exception {

		SeleniumUtil.click(bud_TableRevamp.CreateBudgetBtn());
		SeleniumUtil.click(budget_Gdd.Group_name());
		SeleniumUtil.waitForPageToLoad();
		budget_Gdd.Group_name().sendKeys("My BudgetGroup A");
		SeleniumUtil.click(budget_Gdd.NextBtn());
		SeleniumUtil.waitForPageToLoad();
		SeleniumUtil.click(budget_Gdd.DoneBtn());

		SeleniumUtil.click(bud_TableRevamp.CreateBudgetBtn());
		SeleniumUtil.click(budget_Gdd.Group_name());
		SeleniumUtil.waitForPageToLoad();
		budget_Gdd.Group_name().sendKeys("My BudgetGroup C");
		SeleniumUtil.click(budget_Gdd.NextBtn());
		SeleniumUtil.waitForPageToLoad();
		SeleniumUtil.click(budget_Gdd.DoneBtn());

		SeleniumUtil.click(bud_TableRevamp.CreateBudgetBtn());
		SeleniumUtil.click(budget_Gdd.Group_name());
		SeleniumUtil.waitForPageToLoad();
		budget_Gdd.Group_name().sendKeys("My BudgetGroup B");
		SeleniumUtil.click(budget_Gdd.NextBtn());
		SeleniumUtil.waitForPageToLoad();
		SeleniumUtil.click(budget_Gdd.DoneBtn());

		SeleniumUtil.click(bud_TableRevamp.CreateBudgetBtn());
		SeleniumUtil.click(budget_Gdd.Group_name());
		SeleniumUtil.waitForPageToLoad();
		budget_Gdd.Group_name().sendKeys("My BudgetGroup D");
		SeleniumUtil.click(budget_Gdd.NextBtn());
		SeleniumUtil.waitForPageToLoad();
		SeleniumUtil.click(budget_Gdd.DoneBtn());

		SeleniumUtil.click(bud_TableRevamp.CreateBudgetBtn());
		SeleniumUtil.click(budget_Gdd.Group_name());
		SeleniumUtil.waitForPageToLoad();
		budget_Gdd.Group_name().sendKeys("My BudgetGroup F");
		SeleniumUtil.click(budget_Gdd.NextBtn());
		SeleniumUtil.waitForPageToLoad();
		SeleniumUtil.click(budget_Gdd.DoneBtn());

		SeleniumUtil.click(bud_TableRevamp.CreateBudgetBtn());
		SeleniumUtil.click(budget_Gdd.Group_name());
		SeleniumUtil.waitForPageToLoad();
		budget_Gdd.Group_name().sendKeys("My BudgetGroup E");
		SeleniumUtil.click(budget_Gdd.NextBtn());
		SeleniumUtil.waitForPageToLoad();
		SeleniumUtil.click(budget_Gdd.DoneBtn());

		Assert.assertTrue(budget_Gdd.CreateBudgetHeader().isDisplayed(), "Create Budget Header is displayed");

	}

	@Test(description = "AT-110661:Max we should be showing the 5 budget, if there is any more extra Budgets we show a message as \"To see budget for all account groups\" and Go To Full view button in the Widget", groups = {
			"DesktopBrowsers" }, priority = 4, enabled = true)
	public void verifyBudgetsNameInAlbhabeticalOrderInWidget() throws Exception {

		String expectedOrder = PropsUtil.getDataPropertyValue("BudgetOrdersinAlphabetical");
		if (SeleniumUtil.isDisplayed(bud_TableRevamp.budgetWidgetArrow, 5)) {
			Assert.assertTrue(expectedOrder.contains(bud_TableRevamp.DashboardWidget_BudgetName().getText()));
		}
	}

	@Test(description = "AT-110658:Verify that Carosule icon is available in the Budget Widget for the user to see the other Budget groups created successfully and can move right or left based on the availablility", groups = {
			"DesktopBrowsers" }, priority = 5, enabled = true)
	public void navigateToDashboard() throws Exception {

		PageParser.forceNavigate("DashboardPage", d);
		SeleniumUtil.refresh(d);
		SeleniumUtil.refresh(d);
		SeleniumUtil.waitForPageToLoad();

		if (SeleniumUtil.isDisplayed(bud_TableRevamp.budgetWidgetArrow, 5)) {

			SeleniumUtil.click(bud_TableRevamp.BudgetWidgetArrow());

		}
		Assert.assertTrue(bud_TableRevamp.DashboardWidget_BudgetHeader().isDisplayed(),
				"Verify that Budget Widget on Dashboard page");

	}

	@Test(description = "AT-110659:Verify that when user click on the Budget Widget on respective budget user must land to the same budget name in the Budget Summary page.", groups = {
			"DesktopBrowsers" }, priority = 6, enabled = true)
	public void verifySameBudgetNameOnDashboardWidgetAndBudgetFinap() throws Exception {

		PageParser.forceNavigate("DashboardPage", d);
		SeleniumUtil.waitForPageToLoad();
		String BudgetNameInWidget = bud_TableRevamp.DashboardWidget_BudgetName().getText();
		PageParser.forceNavigate("Budget", d);
		SeleniumUtil.waitForPageToLoad();
		String BudgetNameinBudgetFinapp = bud_TableRevamp.GroupDropdownBudgetName().getText();
		Assert.assertEquals(BudgetNameInWidget, BudgetNameinBudgetFinapp);
	}

	@Test(description = "AT-110660:Verify that deleted Budget must not be shown in the Widget", groups = {
			"DesktopBrowsers" }, priority = 7, enabled = true)
	public void verifyDeletedBudgetOnDashboardWidget() throws Exception {

		logger.info("Deleting Budget Name in Budget page");
		SeleniumUtil.click(bud_TableRevamp.BackToDashboard());
		SeleniumUtil.click(bud_TableRevamp.BudgetWidget());
		SeleniumUtil.waitForPageToLoad();
		SeleniumUtil.click(budget_Gdd.MoreBtn());
		SeleniumUtil.click(budget_Gdd.DeleteBudgetBtn());
		SeleniumUtil.click(budget_Gdd.DeleteConfirm());
		SeleniumUtil.waitForPageToLoad();

		logger.info("Verifying Budget Name in Budget widget in Dashboard page");

		if (SeleniumUtil.isDisplayed(bud_TableRevamp.budgetWidgetArrow, 5)) {

			Assert.assertFalse(bud_TableRevamp.DashboardWidget_BudgetName().isDisplayed());

		}

	}

}