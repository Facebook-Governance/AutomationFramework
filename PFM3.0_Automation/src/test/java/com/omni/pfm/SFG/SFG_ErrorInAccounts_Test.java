/*******************************************************************************
 * Copyright (c) 2018 Yodlee Inc. All Rights Reserved.
 * 
 * This software is the confidential and proprietary information of
 * Yodlee, Inc. Use is subject to license terms.
 * 
 * @author sbhat1
 ******************************************************************************/
package com.omni.pfm.SFG;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import com.omni.pfm.PageProcessor.PageParser;
import com.omni.pfm.pages.Login.LoginPage;
import com.omni.pfm.pages.SFG.SFG_ErrorInAccount_Loc;
import com.omni.pfm.pages.SFG.SortingGoals_Loc;
import com.omni.pfm.testBase.TestBase;
import com.omni.pfm.utility.PropsUtil;
import com.omni.pfm.utility.SeleniumUtil;

public class SFG_ErrorInAccounts_Test extends TestBase {
	Logger logger = LoggerFactory.getLogger(SFG_ErrorInAccounts_Test.class);

	SFG_ErrorInAccount_Loc errorInAccounts;
	LoginPage login;
	SortingGoals_Loc SortingGoals;

	@BeforeClass()
	public void init() throws Exception {
		doInitialization("SFG: Error In Accounts");
		login = new LoginPage();
		errorInAccounts = new SFG_ErrorInAccount_Loc(d);
		SortingGoals = new SortingGoals_Loc(d);
	}

	@Test(description = "creating user and adding account.", priority = 0, enabled = true)
	public void login() throws Exception {
		PageParser.forceNavigate("SFG", d);
		SeleniumUtil.waitForPageToLoad();
	}

	@Test(description = "closing account linked with inprogress goal", dependsOnMethods = {
			"login" }, priority = 1, enabled = true)
	public void closingLinkedAcnt() throws Exception {
		SeleniumUtil.refresh(d);
		SeleniumUtil.waitForPageToLoad();
		PageParser.forceNavigate("AccountSettings", d);
		SeleniumUtil.waitForPageToLoad(2000);
		SeleniumUtil.click(errorInAccounts.SettingContainerList().get(0));

		SeleniumUtil.click(errorInAccounts.accountSettingIcon().get(0));
		SeleniumUtil.waitForPageToLoad(2000);
		boolean status = errorInAccounts.closeActnOption().isDisplayed();
		SeleniumUtil.click(errorInAccounts.closeActnOption());
		SeleniumUtil.waitForPageToLoad(2000);
		Assert.assertTrue(status);
	}

	@Test(description = "verifying error msg in goals linked with closed account", dependsOnMethods = {
			"closingLinkedAcnt" }, priority = 2, enabled = true)
	public void verifyingErrorMsg() throws Exception {
		SeleniumUtil.waitForPageToLoad(6000);
		PageParser.forceNavigate("SFG", d);
		SeleniumUtil.waitForPageToLoad();

		for (int i = 0; i < errorInAccounts.goalInprogressErrorMsg().size(); i++) {
			Assert.assertEquals(errorInAccounts.goalInprogressErrorMsg().get(i).getText().trim(),
					PropsUtil.getDataPropertyValue("SfgErrorMsg").trim());
		}
	}

	@Test(description = "Creating New Goal and making it as offtrack goal", dependsOnMethods = {
			"verifyingErrorMsg" }, priority = 3, enabled = true)
	public void creatingOffTrackGoal() throws Exception {
		SeleniumUtil.click(errorInAccounts.createGoalTab());
		SeleniumUtil.waitForPageToLoad(2000);
		Assert.assertTrue(SortingGoals.createCompletedGoalwithSingleAcnt("GOALNAME71", "1300", "100", "2"));
		SeleniumUtil.waitForPageToLoad(2000);
	}

	@Test(description = "The use should get an option to edit his primary goal details when he clicks on the 'Edit Goal' option.", dependsOnMethods = {
			"creatingOffTrackGoal" }, priority = 4, enabled = true)
	public void verifyAcntsPresentInGoal() {
		SeleniumUtil.click(errorInAccounts.viewMoreButtonForInprogress());
		for (int i = 0; i < errorInAccounts.allInprogressGoal().size(); i++) {
			logger.info(errorInAccounts.allInprogressGoal().get(i).getText());
			if (errorInAccounts.allInprogressGoal().get(i).getText().equals("GOALNAME71")) {
				SeleniumUtil.click(errorInAccounts.allInprogressGoal().get(i));
				break;
			}
		}
		Assert.assertTrue(errorInAccounts.editgoalbutton().isDisplayed());
	}

	@Test(description = "Creating New Goal and making it as offtrack goal", dependsOnMethods = {
			"verifyAcntsPresentInGoal" }, priority = 5, enabled = true)
	public void editingGoalAndMakingAsOffTrack() throws Exception {
		SeleniumUtil.click(errorInAccounts.editgoalbutton());
		SeleniumUtil.waitForPageToLoad(1000);
		SeleniumUtil.click(errorInAccounts.oneTimeFundingditButton());
		errorInAccounts.amountInputBox().clear();
		errorInAccounts.amountInputBox().sendKeys("OfftrackAmount");
		SeleniumUtil.click(errorInAccounts.EditPopupDoneButton());
		SeleniumUtil.click(errorInAccounts.NextBtnStep1());
		SeleniumUtil.waitForPageToLoad(1000);
		SeleniumUtil.click(errorInAccounts.RadioButton3());

		SeleniumUtil.click(errorInAccounts.SavepopUpAddFunds());
		Assert.assertTrue(errorInAccounts.offtrackMsg().isDisplayed(), "Goal is not become offtrack Goal.");
	}

	@Test(description = "verifying off track msg in inprogress goal", dependsOnMethods = {
			"editingGoalAndMakingAsOffTrack" }, priority = 6, enabled = true)
	public void verifyingOfftrackErrorMsg() throws Exception {
		PageParser.forceNavigate("SFG", d);
		SeleniumUtil.waitForPageToLoad();
		SeleniumUtil.click(errorInAccounts.viewMoreButtonForInprogress());

		Assert.assertEquals(errorInAccounts.InprogressOfftrackMsg().getText().trim(),
				PropsUtil.getDataPropertyValue("offtrackErrorMsg").trim());

	}

	@Test(description = "verifying goal in error on dashboard page.", dependsOnMethods = {
			"verifyingOfftrackErrorMsg" }, priority = 7, enabled = true)
	public void verifyingGoalInErrorOnDashboard() throws Exception {
		d.navigate().refresh();
		SeleniumUtil.waitForPageToLoad();
		Assert.assertEquals(errorInAccounts.DashboardGoalColor().get(0).getCssValue("background-color").trim(),
				PropsUtil.getDataPropertyValue("DashBoardErrorScreenColor"));
	}

	@Test(description = "verifying navigation from dashboard to sfg.", dependsOnMethods = {
			"verifyingGoalInErrorOnDashboard" }, priority = 8, enabled = true)
	public void navigateDashBoardToErrorGoal() throws Exception {
		SeleniumUtil.click(errorInAccounts.DashboardGoalColor().get(0));
		Assert.assertTrue(errorInAccounts.offTrackErrorMsg1().getText().trim()
				.contains(PropsUtil.getDataPropertyValue("OffTrackErrorMsg1").trim()));
	}

	@Test(description = "verifying offTrack error msg on sfg page", dependsOnMethods = {
			"navigateDashBoardToErrorGoal" }, priority = 9, enabled = true)
	public void verifyingSFGOfftrackErrorMsg() throws Exception {
		Assert.assertEquals(errorInAccounts.offTrackErrorMsg2().getText().trim(),
				PropsUtil.getDataPropertyValue("OffTrackErrorMsg2").trim());
		Assert.assertEquals(errorInAccounts.offTrackErrorMsg3().getText().trim(),
				PropsUtil.getDataPropertyValue("OffTrackErrorMsg3").trim());
	}
}
