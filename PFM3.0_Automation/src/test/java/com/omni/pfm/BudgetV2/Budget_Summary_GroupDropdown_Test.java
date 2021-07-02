/**
* Copyright (c) 2019 Yodlee Inc. All Rights Reserved. 
*
* This software is the confidential and proprietary information of
* Yodlee, Inc. Use is subject to license terms.
*
* @author mallikan
*/
package com.omni.pfm.BudgetV2;

import java.util.List;

import org.openqa.selenium.WebElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.omni.pfm.PageProcessor.PageParser;
import com.omni.pfm.config.Config;
import com.omni.pfm.pages.AccountAddition.AccountAddition;
import com.omni.pfm.pages.BudgetV2.Budget_Summary_GroupDropdown_Loc;
import com.omni.pfm.pages.Login.LoginPage;
import com.omni.pfm.testBase.TestBase;
import com.omni.pfm.utility.SeleniumUtil;

public class Budget_Summary_GroupDropdown_Test extends TestBase {
	private static final Logger logger = LoggerFactory.getLogger(Budget_Summary_GroupDropdown_Test.class);

	AccountAddition accAddition = new AccountAddition();
	Budget_Summary_GroupDropdown_Loc budget_Gdd;

	@BeforeClass(alwaysRun = true)
	public void inti() throws Exception {
		doInitialization("Budget");
		budget_Gdd = new Budget_Summary_GroupDropdown_Loc(d);

	}

	@Test(description = "AT-109649:Verify Login Happens Successfully", groups = { "DesktopBrowsers" }, priority = 1)
	public void login() throws Exception {
		LoginPage.loginMain(d, loginParameter);

		logger.info(">>>>> Adding a dag site to PFM 3.0..");
		accAddition.linkAccount();
		accAddition.addAggregatedAccount("bbudget.site16441.3", "site16441.3");

		logger.info(">>>>> Navigating to Budget..");
		PageParser.forceNavigate("Budget", d);
		SeleniumUtil.waitForPageToLoad();
	}

	@Test(description = "AT-109649:Verify in Budget FinApp page Create Group is shown as a dropdown for the first time user ", groups = {
			"DesktopBrowsers" }, priority = 2, dependsOnMethods = { "login" }, enabled = true)
	public void verifyCreateGroup() throws Exception {

		logger.info(">>>>> Click on  the GetStarted button");
		SeleniumUtil.click(budget_Gdd.GetStartedBtn());

		logger.info(">>>>> Verifying the Create Group button is displayed");
		SeleniumUtil.click(budget_Gdd.createGroupDD());

		SeleniumUtil.click(budget_Gdd.createGroupBtn());
		SeleniumUtil.click(budget_Gdd.Group_name());
		SeleniumUtil.waitForPageToLoad();

		budget_Gdd.Group_name().sendKeys("My Budget12");
		SeleniumUtil.click(budget_Gdd.NextBtn());
		SeleniumUtil.waitForPageToLoad();

		SeleniumUtil.click(budget_Gdd.DoneBtn());
		Assert.assertTrue(budget_Gdd.CreateBudgetHeader().isDisplayed(), "Create Budget Header is displayed");
	}

	@Test(description = "AT-109649:Verify that at the bottom Manage Group text and icon is available in the Budget Group dropdown", groups = {
			"DesktopBrowsers" }, priority = 3, dependsOnMethods = { "verifyCreateGroup" }, enabled = true)
	public void verifyManageGroups() throws Exception {
		SeleniumUtil.click(budget_Gdd.GroupDropdown());
		SeleniumUtil.waitForPageToLoad();
		Assert.assertTrue(budget_Gdd.ManageGroupBtn().isDisplayed(), "Manage Group Btn is not displayed");
	}

	@Test(description = "AT-109642,AT-109648:Verify that Budget Group dropdown in summary page, shows only the group name for which Budget is created", groups = {
			"DesktopBrowsers" }, priority = 4, dependsOnMethods = { "verifyCreateGroup" }, enabled = true)
	public void verifySelectedGroupName() throws Exception {

		logger.info(">>>>Verifying created group name is displayed");
		Assert.assertTrue(budget_Gdd.GroupDropdown().isDisplayed(), "Selected group name is not displayed");
	}

	@Test(description = "AT-109643,AT-109650:Verify that if a group is created in Account Group page but not considered for Budget then, this group name must not be shown in the dropdown of Budget summary page", groups = {
			"DesktopBrowsers" }, priority = 5, dependsOnMethods = { "verifyCreateGroup" }, enabled = true)
	public void AccGroupCreatedInAccSetNotInBudgetDD() throws Exception {

		SeleniumUtil.click(budget_Gdd.ManageGroupBtn());
		SeleniumUtil.waitForPageToLoad();
		SeleniumUtil.click(budget_Gdd.CreateGrpinAccGrp());
		SeleniumUtil.click(budget_Gdd.EditGroupDropDown());
		budget_Gdd.EditGroupDropDown().sendKeys("ABCD Group");
		SeleniumUtil.click(budget_Gdd.SelectAllAccountsDD());
		SeleniumUtil.click(budget_Gdd.SaveChangesBtn());
		SeleniumUtil.waitForPageToLoad();
		if (Config.appFlag.equals("app") || Config.appFlag.equals("emulator")) {

			SeleniumUtil.click(budget_Gdd.backToBdgtFromAcctGroups_BS_mob());
			SeleniumUtil.waitForPageToLoad(2000);
			SeleniumUtil.click(budget_Gdd.backToBdgtFromAcctGroups_BS_mob());
		} else {
			SeleniumUtil.click(budget_Gdd.BacktoBudget());
		}
		SeleniumUtil.waitForPageToLoad();
		SeleniumUtil.click(budget_Gdd.GroupDropdown());
		SeleniumUtil.waitForPageToLoad();

		String grpName = budget_Gdd.BudgetListinDD().getText().trim();
		String invalidGroup = "ABCD Group";
		if (!grpName.equalsIgnoreCase(invalidGroup)) {
			Assert.assertTrue(true);
		} else {
			Assert.assertTrue(false);
		}
	}

	@Test(description = "AT-109644:Verify that if there are Valid account and Invalid account added to a group, and considered for budget  then grey out the invalid accounts in that group in Budget Summary page", groups = {
			"DesktopBrowsers" }, priority = 6, dependsOnMethods = { "verifyCreateGroup" }, enabled = true)
	public void EditGroup() throws Exception {

		SeleniumUtil.click(budget_Gdd.ManageGroupBtn());
		SeleniumUtil.waitForPageToLoad();

		SeleniumUtil.click(budget_Gdd.EditGroupName1());
		SeleniumUtil.click(budget_Gdd.EditGroupIcon());
		SeleniumUtil.click(budget_Gdd.EditGroupDropDown());
		budget_Gdd.EditGroupDropDown().clear();
		String EditedBudgetName = "My Budget2";
		budget_Gdd.EditGroupDropDown().sendKeys(EditedBudgetName);
		SeleniumUtil.click(budget_Gdd.SelectAllAccountsDD());
		SeleniumUtil.click(budget_Gdd.SaveChangesBtn());
		SeleniumUtil.waitForPageToLoad();
		if (Config.appFlag.equals("app") || Config.appFlag.equals("emulator")) {
			SeleniumUtil.click(budget_Gdd.backToBdgtFromAcctGroups_BS_mob());
			SeleniumUtil.waitForPageToLoad(2000);
			SeleniumUtil.click(budget_Gdd.backToBdgtFromAcctGroups_BS_mob());
		} else {
			SeleniumUtil.click(budget_Gdd.BacktoBudget());
		}
		SeleniumUtil.waitForPageToLoad();
		SeleniumUtil.click(budget_Gdd.GroupDropdown());
		SeleniumUtil.waitForPageToLoad();

		List<WebElement> actualValidAccts = budget_Gdd.validAccountsInBdgtDD();
		int actualValidAcctsSize = actualValidAccts.size();
		Assert.assertEquals(actualValidAcctsSize, 7);

		List<WebElement> greyedOutAccts = budget_Gdd.verifyGreyedOutAccountsInDD();
		int actualGreyedOutAccts = greyedOutAccts.size();
		Assert.assertEquals(actualGreyedOutAccts, 7);
		Assert.assertEquals(budget_Gdd.SelectedGroupName().getText(), EditedBudgetName);
	}

	@Test(description = "AT-109666:Verify that Deleted budget group name must not be shown in the Budget Group dropdown of Budget Summary page", groups = {
			"DesktopBrowsers" }, priority = 7, dependsOnMethods = { "EditGroup" }, enabled = true)
	public void VerifyDeletedBudgetNotPresentDD() throws Exception {

		if (Config.appFlag.equals("app") || Config.appFlag.equals("emulator")) {
			SeleniumUtil.click(budget_Gdd.bdgtAccGpDDDoneBtn_BS_mob());
		}
		SeleniumUtil.click(budget_Gdd.MoreBtn());
		SeleniumUtil.click(budget_Gdd.DeleteBudgetBtn());
		SeleniumUtil.click(budget_Gdd.DeleteConfirm());
		Assert.assertTrue(budget_Gdd.CreateBudgetHeader().isDisplayed(), "Create Budget Header is displayed");
	}
}
