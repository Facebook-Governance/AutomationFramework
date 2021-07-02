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
import com.omni.pfm.pages.BudgetV2.Budget_Summary_GoalSetting_Loc;
import com.omni.pfm.pages.BudgetV2.Budget_Summary_GroupDropdown_Loc;
import com.omni.pfm.pages.BudgetV2.Budget_Summary_TableRevamp_Loc;
import com.omni.pfm.pages.Login.LoginPage;
import com.omni.pfm.pages.SFG.SFG_CreateGoal_GetStarted_Loc;
import com.omni.pfm.pages.SFG.SFG_CustomGoalEdit_Loc;
import com.omni.pfm.pages.SFG.SFG_createGoalEdit_Loc;
import com.omni.pfm.testBase.TestBase;
import com.omni.pfm.utility.PropsUtil;
import com.omni.pfm.utility.SeleniumUtil;

public class Budget_Summary_GoalSetting_Test extends TestBase {
	private static final Logger logger = LoggerFactory.getLogger(Budget_Summary_GoalSetting_Test.class);

	AccountAddition accAddition = new AccountAddition();
	Budget_Summary_EditPopup_Loc budget_Edit;
	SFG_CreateGoal_GetStarted_Loc getStart;
	SFG_createGoalEdit_Loc goalEdit;
	LoginPage login;
	AccountAddition accountAdd;
	SFG_CustomGoalEdit_Loc customGoalEdit;
	Budget_CreateBudget_Loc budgetCreateLoc;
	Budget_Summary_GoalSetting_Loc budgetGoal;
	Budget_Summary_GroupDropdown_Loc budget_Gdd;
	Budget_Summary_TableRevamp_Loc bud_TableRevamp;
	Budget_BudgetSummary_Loc budgetSummary;

	@BeforeClass(alwaysRun = true)
	public void init() throws Exception {
		doInitialization("Budget");
		budget_Edit = new Budget_Summary_EditPopup_Loc(d);
		getStart = new SFG_CreateGoal_GetStarted_Loc(d);
		goalEdit = new SFG_createGoalEdit_Loc(d);
		doInitialization("SFG_CreateGoal_GetStarted");
		customGoalEdit = new SFG_CustomGoalEdit_Loc(d);
		budgetCreateLoc = new Budget_CreateBudget_Loc(d);
		budgetGoal = new Budget_Summary_GoalSetting_Loc(d);
		budget_Gdd = new Budget_Summary_GroupDropdown_Loc(d);
		bud_TableRevamp = new Budget_Summary_TableRevamp_Loc(d);
		budgetSummary = new Budget_BudgetSummary_Loc(d);
		login = new LoginPage();
		accountAdd = new AccountAddition();
		d.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
	}

	@Test(description = "Verify Login Happens Successfully", groups = {
			"DesktopBrowsers" }, priority = 1, enabled = true)
	public void login() throws Exception {
		LoginPage.loginMain(d, loginParameter);
		accAddition.linkAccount();
		accAddition.addAggregatedAccount("budget1_v1.site16441.10", "site16441.10");
	}

	@Test(description = "AT-116130:All the goals should be selected by default.", groups = {
			"DesktopBrowsers,sanity" }, priority = 2, dependsOnMethods = { "login" }, enabled = true)
	public void verifyGoalTitle() {
		PageParser.forceNavigate("Budget", d);
		SeleniumUtil.click(budgetGoal.budgetStartBtn());
		SeleniumUtil.click(budget_Gdd.Group_name());
		budget_Gdd.Group_name().sendKeys("My BudgetGroup A");
		SeleniumUtil.click(budget_Gdd.NextBtn());
		SeleniumUtil.assertContains(PropsUtil.getDataPropertyValue("Goal_Container"),
				budgetGoal.goalTabinBudgetCreatePage().getText(), "");
	}

	@Test(description = "AT-116115:If the user does not have any goals, the user should see the following message \"You do not have any goals currently. Create and link your goals after creating the budget.", groups = {
			"DesktopBrowsers,sanity" }, priority = 3, dependsOnMethods = { "login" }, enabled = true)
	public void NoGoalMessage() {
		SeleniumUtil.click(budgetGoal.goalTabinBudgetCreatePage());
		Assert.assertEquals(budgetGoal.noGoalinBudgetMsg().getText(),
				PropsUtil.getDataPropertyValue("NoGoalMessageinCreateBudgetPage"));
	}

	@Test(description = "AT-116107:AT-116107,AT-116122:Verifying landing page and creating first goal", groups = {
			"DesktopBrowsers,sanity" }, priority = 4, dependsOnMethods = { "NoGoalMessage" }, enabled = true)
	public void creatingBudget() {
		SeleniumUtil.click(budget_Gdd.DoneBtn());
		if (Config.appFlag.equalsIgnoreCase("app") || Config.appFlag.equalsIgnoreCase("emulator")) {
			SeleniumUtil.click(budget_Edit.viewDetailsBtn());
		}
		Assert.assertEquals(budgetGoal.createNewGoalLink().getText(),
				PropsUtil.getDataPropertyValue("CreateNewGoalLink"));
	}

	@Test(description = "The user should see an option to create a new goal.", groups = {
			"DesktopBrowsers,sanity" }, priority = 5, dependsOnMethods = { "login" }, enabled = true)
	public void creatingGoalFirst() {
		SeleniumUtil.click(budgetGoal.createNewGoalLink());
		boolean getStartedStatus = customGoalEdit.getStartedTab().isDisplayed();
		SeleniumUtil.click(customGoalEdit.getStartedTab());
		customGoalEdit.createGoalwithMultipleAccount("GOALNAME50", "1300", "100", "100");
		Assert.assertTrue(getStartedStatus, "Get started tab is not displayed");
	}

	@Test(description = "On clicking on the create a new goal button, the user should be taken to the SFG finapp.", groups = {
			"DesktopBrowsers,sanity" }, priority = 6, dependsOnMethods = { "login" }, enabled = true)
	public void creatingGoalSecond() {
		SeleniumUtil.click(customGoalEdit.createGoalTab());
		customGoalEdit.createGoalwithMultipleAccount("GOALNAME51", "1300", "100", "100");

		SeleniumUtil.waitForPageToLoad(2000);
		int numberOfAddedGoal = customGoalEdit.allInprogressGoal().size();
		Assert.assertEquals(numberOfAddedGoal, 2);
	}

	@Test(description = "AT-116120,AT-116124:Goals should be a part of the budget equation on the summary page.", groups = {
			"DesktopBrowsers,sanity" }, priority = 7, dependsOnMethods = { "login" }, enabled = true)
	public void verifyGoalinBudgetSummary() {
		if (Config.appFlag.equalsIgnoreCase("app") || Config.appFlag.equalsIgnoreCase("emulator")) {
			PageParser.forceNavigate("Budget", d);
			SeleniumUtil.click(budget_Edit.viewDetailsBtn());
			SeleniumUtil.assertContains(PropsUtil.getDataPropertyValue("Goal_Container"),
					budgetGoal.goalTitleinBudget_Mob().getText(), "");
		}
		if (Config.appFlag.equals("web") || Config.appFlag.equals("false")) {
			SeleniumUtil.click(budgetGoal.backtoBudgetinSFG());
			SeleniumUtil.assertContains(PropsUtil.getDataPropertyValue("Goal_Container"),
					budgetGoal.goalTitleinBudget().getText(), "");
		}
	}

	@Test(description = "AT-116110,AT-116125:On navigating back from SFG, if the user has created a goal with overlapping accounts with the ones in his group, he should see the goal in the list.", groups = {
			"DesktopBrowsers,sanity" }, priority = 8, dependsOnMethods = {
					"verifyGoalinBudgetSummary" }, enabled = true)
	public void verifyAddGoalBtn() {

		Assert.assertEquals(budgetGoal.budgetedGoals().getText(), PropsUtil.getDataPropertyValue("BudgetedGoals"));
		Assert.assertEquals(budgetGoal.addGoalLinkBtn().getText(), PropsUtil.getDataPropertyValue("AddGoalLinkBtn"));

	}

	@Test(description = "AT-116128:Goals should be a part of the budget equation on the summary page.", groups = {
			"DesktopBrowsers,sanity" }, priority = 9, dependsOnMethods = { "login" }, enabled = true)
	public void endAddGoal() {

		SeleniumUtil.click(budgetGoal.addGoalLinkBtn());
		SeleniumUtil.click(budgetGoal.addBtn());
		SeleniumUtil.click(budgetGoal.addGoalLinkBtn());
		SeleniumUtil.click(budgetGoal.addBtn());
		Assert.assertEquals(budgetGoal.createNewGoalLink().getText(),
				PropsUtil.getDataPropertyValue("CreateNewGoalLink"));
	}

	@Test(description = "AT-116128:On the trends page, the user should not see the 'Add a New Goal' button.", groups = {
			"DesktopBrowsers,sanity" }, priority = 10, dependsOnMethods = { "endAddGoal" }, enabled = true)
	public void moreBtn() {
		SeleniumUtil.click(budgetGoal.moreBtnGoal());
		Assert.assertEquals(budgetGoal.goToThisGoalLink().getText(),
				PropsUtil.getDataPropertyValue("GoToThisGoalLink"));
	}

	@Test(description = "AT-116123:On clicking on the create a new goal button, the user should be taken to the SFG finapp.", groups = {
			"DesktopBrowsers,sanity" }, priority = 11, dependsOnMethods = { "moreBtn" }, enabled = true)
	public void verifyGoToThisGoal() {
		SeleniumUtil.click(budgetGoal.goToThisGoalLink());
		Assert.assertEquals(budgetGoal.goalSummaryFinappHeader().getText(),
				PropsUtil.getDataPropertyValue("SFG_Finapp_Header"));
	}

	@Test(description = "AT-116113,AT-116114,AT-116119:If the user clicks on the goal, he should be taken to the goal summary page for that particular goal.", groups = {
			"DesktopBrowsers,sanity" }, priority = 12, dependsOnMethods = { "login" }, enabled = true)
	public void verifyUnLinkFromBudgetOption() {
		SeleniumUtil.click(budgetGoal.backtoBudgetinSFG());
		SeleniumUtil.click(budgetGoal.moreBtnGoal());
		Assert.assertEquals(budgetGoal.unLinkFromBudget().getText(),
				PropsUtil.getDataPropertyValue("UnLinkFromBudget"));
	}

	@Test(description = "AT-116105,AT-116106:The user should have an option to unlink his goal from the budget via the options button.", groups = {
			"DesktopBrowsers,sanity" }, priority = 13, dependsOnMethods = {
					"verifyUnLinkFromBudgetOption" }, enabled = true)
	public void verifyUnLinkFromBudget() {
		SeleniumUtil.click(budgetGoal.unLinkFromBudget());
		SeleniumUtil.click(budgetGoal.confirmPopupUnLink());
		SeleniumUtil.click(budgetGoal.moreBtnGoal());
		Assert.assertEquals(budgetGoal.addGoalLinkInMore().getText(),
				PropsUtil.getDataPropertyValue("AddGoalLinkInMore"));
	}

	@Test(description = "AT-116126,AT-116127,AT-116118:All the data points of the goal should be based on the selected time period.", groups = {
			"DesktopBrowsers,sanity" }, priority = 14, dependsOnMethods = { "login" }, enabled = true)
	public void goalCalculation() {
		Double actInc = Double.parseDouble(budgetGoal.actualIncome().getText().trim().replaceAll("[^.0-9]", ""));
		Double actSpend = Double.parseDouble(budgetGoal.actualSpending().getText().trim().replaceAll("[^.0-9]", ""));
		Double budgGoals = Double.parseDouble(budgetGoal.budgetedGoalsAmt().getText().trim().replaceAll("[^.0-9]", ""));
		Double leftAmnt = Double.parseDouble(budgetGoal.left_BS().getText().trim().replaceAll("[^.0-9]", ""));
		Double exp = actInc - actSpend - budgGoals;
		Assert.assertTrue(budgetGoal.actualSpending().isDisplayed(),"Actual spending is not displayed");
		Assert.assertTrue(budgetGoal.left_BS().isDisplayed(),"Left bs is not displayed");
		Assert.assertTrue(exp.toString().contains(leftAmnt.toString()),"Expense does not contain left amount");
	}

	@Test(description = "AT-116129:All the data points of the goal should be based on the selected time period.", groups = {
			"DesktopBrowsers,sanity" }, priority = 15, dependsOnMethods = { "login" }, enabled = true)
	public void verifyTimeFilter() {
		SeleniumUtil.click(budgetSummary
				.budget_Summery_CustomedateFilterName(PropsUtil.getDataPropertyValue("Budget_Summery3MonthLabel")));
		SeleniumUtil.waitForPageToLoad();
		Assert.assertEquals(budgetSummary.budget_Summery_TimeFilter().getText().trim(),
				PropsUtil.getDataPropertyValue("Budget_Summery3MonthLabel"), "time filter label is not displayed");
		Assert.assertEquals(budgetSummary.budget_Summery_TimeFilterDate().getText().trim(),
				budgetSummary.getdateMMMYYYY(-2) + " " + "-" + " " + budgetSummary.getdateMMMYYYY(0),
				"time filter date value is not displayed");
	}

	@Test(description = "AT-116104:On step 2 of the create budget flow, the user should see all his goals that have atleast 1 account overlapping with an account in the account group selected.", groups = {
			"DesktopBrowsers,sanity" }, priority = 16, dependsOnMethods = { "login" }, enabled = true)
	public void verifyOneAccOverlapped() {
		SeleniumUtil.click(bud_TableRevamp.CreateBudgetBtn());
		budget_Gdd.Group_name().sendKeys("My BudgetGroup C");
		Assert.assertEquals(budgetGoal.accountDetails().getText(),
				PropsUtil.getDataPropertyValue("AccountDetailsinBudget"));
	}

	@Test(description = "AT-116108,AT-116109:The user should see the frequency of the goal.", groups = {
			"DesktopBrowsers" }, priority = 17, dependsOnMethods = { "verifyOneAccOverlapped" }, enabled = true)
	public void verifyGoalFrequency() {
		if (Config.appFlag.equals("web") || Config.appFlag.equals("false")) {
			SeleniumUtil.click(budget_Gdd.NextBtn());
			SeleniumUtil.click(budget_Gdd.DoneBtn());
			PageParser.forceNavigate("Budget", d);
			SeleniumUtil.click(bud_TableRevamp.CreateBudgetBtn());
			budget_Gdd.Group_name().sendKeys("My BudgetGroup D");
			SeleniumUtil.click(budget_Gdd.NextBtn());
			SeleniumUtil.click(budgetGoal.goalTabinBudgetCreatePage());
			Assert.assertTrue(budgetGoal.selectAllCheckbox().isDisplayed(),"Select all check box is not displayed");
			Assert.assertEquals(budgetGoal.goalFrequency().getText(), PropsUtil.getDataPropertyValue("GoalFrequency"));
			Assert.assertEquals(budgetGoal.allocation().getText(), PropsUtil.getDataPropertyValue("AllocationHeader"));
		}
	}
}
