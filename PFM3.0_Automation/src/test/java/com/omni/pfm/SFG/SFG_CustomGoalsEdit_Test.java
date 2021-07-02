/*******************************************************************************
 * Copyright (c) 2018 Yodlee Inc. All Rights Reserved.
 * 
 * This software is the confidential and proprietary information of
 * Yodlee, Inc. Use is subject to license terms.
 * 
 * @author
 ******************************************************************************/
package com.omni.pfm.SFG;

import java.util.concurrent.TimeUnit;

import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.accessibility.examples.testng.webdriver.test.RunAccessibilityTest;
import com.omni.pfm.PageProcessor.PageParser;
import com.omni.pfm.pages.SFG.SFG_LandingPage_Loc;
import com.omni.pfm.pages.SFG.SFG_createGoalEdit_Loc;
import com.omni.pfm.testBase.TestBase;
import com.omni.pfm.utility.PropsUtil;
import com.omni.pfm.utility.SeleniumUtil;

public class SFG_CustomGoalsEdit_Test extends TestBase {
	SFG_createGoalEdit_Loc goalEdit;
	SFG_LandingPage_Loc SFG;

	@BeforeClass(alwaysRun = true)
	public void testInit() throws InterruptedException {
		doInitialization("SFG_CustomGoalsEdit_Test");
		goalEdit = new SFG_createGoalEdit_Loc(d);
		SFG = new SFG_LandingPage_Loc(d);
		d.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
	}

	@Test(description = "Verify Login Happens Successfully", groups = { "DesktopBrowsers" }, priority = 1)
	public void Login() throws Exception {
		SeleniumUtil.refresh(d);
		SeleniumUtil.waitForPageToLoad(2000);
		PageParser.forceNavigate("SFG", d);
		SeleniumUtil.waitForPageToLoad(5000);
		SFG.clikcgoalWithoutSave();
	}

	@Test(description = "Verify Creating goal", dependsOnMethods = { "Login" }, groups = {
			"DesktopBrowsers" }, priority = 2)
	public void verifyInprogressGoalLadingPage() throws InterruptedException {
		SFG.CreateGoalWithFrequencyAmount(PropsUtil.getDataPropertyValue("SFGGoalName11"),
				PropsUtil.getDataPropertyValue("SFGGoalAmount4"), 0,
				PropsUtil.getDataPropertyValue("SFGFrequencyAmount2"),
				PropsUtil.getDataPropertyValue("SFGOnetimeFunding5"),
				PropsUtil.getDataPropertyValue("SFGFundingAmountValueUpdate4"));
		SeleniumUtil.waitForPageToLoad(3000);
		for (int i = 0; i < SFG.SFGInprogressGoalNames().size(); i++) {
			if (SFG.SFGInprogressGoalNames().get(i).getText().trim()
					.equals(PropsUtil.getDataPropertyValue("SFGGoalName11").trim())) {

				SeleniumUtil.click(SFG.SFGInprogressGoalNames().get(i));
				break;
			}
		}
		if (PageParser.isElementPresent("backButtonForMobile", "SFG", null)) {
			Assert.assertTrue(SFG.verifyBackButton().isDisplayed());
		} else {
			Assert.assertEquals(SFG.backToNewGoal().getText(), PropsUtil.getDataPropertyValue("SFGBackToGoal"));
		}
	}

	@Test(description = "Verify Back to nee goal Icon", dependsOnMethods = { "verifyInprogressGoalLadingPage" }, groups = {
			"DesktopBrowsers" }, priority = 3)
	public void verifyInprogressGoalBackToGoalIcon() throws InterruptedException {
		Assert.assertTrue(SFG.backToNewGoalIcon().isDisplayed());
	}

	@Test(description = "Verify goal headers", dependsOnMethods = { "verifyInprogressGoalLadingPage" }, groups = {
			"DesktopBrowsers" }, priority = 4)
	public void verifyInprogressGoalHeader() throws InterruptedException {
		Assert.assertEquals(SFG.GoalHeaderInsteps().getText(),
				PropsUtil.getDataPropertyValue("sfgInprogressGoalLandingHeader"));
	}

	@Test(description = "Verify edit current status label", dependsOnMethods = { "verifyInprogressGoalLadingPage" }, groups = {
			"DesktopBrowsers" }, priority = 5)
	public void verifyEditGoalCurentStausLabel() throws InterruptedException {
		Assert.assertEquals(SFG.SFGEditCurrentStauslabel().getText(),
				PropsUtil.getDataPropertyValue("SFGEditCurrentStatusLabel"));
	}

	@Test(description = "Verify current status details label", dependsOnMethods = { "verifyInprogressGoalLadingPage" }, groups = {
			"DesktopBrowsers" }, priority = 6)
	public void verifyEditGoalCurentStausDetailsLabel() throws InterruptedException {
		String expected[] = PropsUtil.getDataPropertyValue("SFGEditCurrentStatusDetailsLabel").split(",");
		for (int i = 0; i < SFG.SFGEditCurrentStausDetailslabel().size(); i++) {
			Assert.assertEquals(SFG.SFGEditCurrentStausDetailslabel().get(i).getText(), expected[i]);
		}

	}

	@Test(description = "Verify edit current savings value", dependsOnMethods = { "verifyInprogressGoalLadingPage" }, groups = {
			"DesktopBrowsers" }, priority = 7)
	public void verifyEditGoalCurentStaussavingValue() throws InterruptedException {
		Assert.assertEquals(SFG.SFGEditCurrentStausDetailsValue().get(0).getText(),
				PropsUtil.getDataPropertyValue("SFGEditCurrentStatusSavingValue"));
	}

	@Test(description = "Verify current status goal amount value", dependsOnMethods = { "verifyInprogressGoalLadingPage" }, groups = {
			"DesktopBrowsers" }, priority = 8)
	public void verifyEditGoalCurentStausGoalAmountValue() throws InterruptedException {
		Assert.assertEquals(SFG.SFGEditCurrentStausDetailsValue().get(1).getText(),
				PropsUtil.getDataPropertyValue("SFGEditCurrentStatusGoalAmount"));
	}

	@Test(description = "Verify current status goal target values", dependsOnMethods = { "verifyInprogressGoalLadingPage" }, groups = {
			"DesktopBrowsers" }, priority = 9)
	public void verifyEditGoalCurentStausGoalTargateValue() throws InterruptedException {
		Assert.assertEquals(SFG.SFGEditCurrentStausDetailsValue().get(2).getText(), SFG.targateDateSelectMMM(78));
	}

	@Test(description = "Verify goal on going funding value", dependsOnMethods = { "verifyInprogressGoalLadingPage" }, groups = {
			"DesktopBrowsers" }, priority = 10)
	public void verifyEditGoalOnGoingFundingLabel() throws InterruptedException {
		Assert.assertEquals(SFG.SFGEditOngoingFundinglabel().getText(),
				PropsUtil.getDataPropertyValue("SFGEditOnGoungFundingLabel"));

	}

	@Test(description = "Verify ongoing details label", dependsOnMethods = { "verifyInprogressGoalLadingPage" }, groups = {
			"DesktopBrowsers" }, priority = 11)
	public void verifyEditGoalOngoingDetailsLabel() throws InterruptedException {
		String expected[] = PropsUtil.getDataPropertyValue("SFGEditOnGoungFundingDetailsLabel").split(",");
		for (int i = 0; i < SFG.SFGEditOngoingFundingDetailslabel().size(); i++) {
			Assert.assertEquals(SFG.SFGEditOngoingFundingDetailslabel().get(i).getText(), expected[i]);
		}

	}

	@Test(description = "Verify ongoing funding details", dependsOnMethods = { "verifyInprogressGoalLadingPage" }, groups = {
			"DesktopBrowsers" }, priority = 12)
	public void verifyEditGoalOngoingfundingStartDate() throws InterruptedException {
		Assert.assertEquals(SFG.SFGEditOngoingFundingDetailsValue().get(0).getText(), SFG.targateDateSelectMMM(1));
	}

	@Test(description = "Verify ongoing funding date", dependsOnMethods = { "verifyInprogressGoalLadingPage" }, groups = {
			"DesktopBrowsers" }, priority = 13)
	public void verifyEditGoalOngoingfundingendDate() throws InterruptedException {
		Assert.assertEquals(SFG.SFGEditOngoingFundingDetailsValue().get(1).getText(), SFG.targateDateSelectMMM(78));
	}

	@Test(description = "Verify frequency", dependsOnMethods = { "verifyInprogressGoalLadingPage" }, groups = {
			"DesktopBrowsers" }, priority = 14)
	public void verifyEditGoalOngoingfundingFrequency() throws InterruptedException {
		Assert.assertEquals(SFG.SFGEditOngoingFundingDetailsValue().get(2).getText(),
				PropsUtil.getDataPropertyValue("SFGEditOnGoungFundingFrequency"));
	}

	@Test(description = "Verify Save In details", dependsOnMethods = { "verifyInprogressGoalLadingPage" }, groups = {
			"DesktopBrowsers" }, priority = 15)
	public void verifyEditGoalOngoingfundingSaveIn() throws InterruptedException {
		Assert.assertEquals(SFG.SFGEditOngoingFundingDetailsValue().get(3).getText(),
				PropsUtil.getDataPropertyValue("SFGEditOnGoungFundingSaveIn"));
	}

	@Test(description = "Verify ongoing funding amount", dependsOnMethods = { "verifyInprogressGoalLadingPage" }, groups = {
			"DesktopBrowsers" }, priority = 16)
	public void verifyEditGoalOngoingfundingAmount() throws InterruptedException {
		Assert.assertEquals(SFG.SFGEditOngoingFundingDetailsValue().get(4).getText(),
				PropsUtil.getDataPropertyValue("SFGEditOnGoungFundingAmount"));
	}

	@AfterClass
	public void checkAccessibility() {
		try {
			RunAccessibilityTest rat = new RunAccessibilityTest(this.getClass().getName());
			rat.testAccessibility(d);

		} catch (Exception e) {

		}
	}

}
