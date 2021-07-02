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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.accessibility.examples.testng.webdriver.test.RunAccessibilityTest;
import com.omni.pfm.PageProcessor.PageParser;
import com.omni.pfm.pages.SFG.SFG_LandingPage_Loc;
import com.omni.pfm.testBase.TestBase;
import com.omni.pfm.utility.PropsUtil;
import com.omni.pfm.utility.SeleniumUtil;

public class SFG_CreateGoal_FrqAmount_FundTransferFlow_Test extends TestBase {

	SFG_LandingPage_Loc SFG;

	private static final Logger logger = LoggerFactory.getLogger(SFG_CreateGoal_FrqAmount_FundTransferFlow_Test.class);

	@BeforeClass(alwaysRun = true)
	public void testInit() throws InterruptedException {
		doInitialization("SFG_CreateGoal_FrqAmount_FundTransferFlow");
		SFG = new SFG_LandingPage_Loc(d);
		d.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
	}

	@Test(description = "Verify Login Happens Successfully", groups = { "DesktopBrowsers" }, priority = 1)
	public void Login() throws Exception {
		SeleniumUtil.refresh(d);
		PageParser.forceNavigate("SFG", d);
		SFG.clikcgoalWithoutSave();
	}

	@Test(description = "Verify that if the user modifies the system populated ongoing amount and clicks the next button, the system should bring up the pop up where user can revise either the goal amount or end date or save as is.", dependsOnMethods = {
			"Login" }, groups = { "DesktopBrowsers" }, priority = 2)
	public void vrifyFrequencyGoalConfirmationPopUpHeader() throws InterruptedException {
		logger.info("verify frequency goal confirmation PopUpHeader");
		logger.info(
				"AT-17602:Verify that even when the entire target amount is not planned for, user should have an option to kick start the goal so that App can track the progress of the goal.");
		logger.info("AT-17609:Validate the pending allocation popup content.");
		SFG.CreateGoalStep3FrequencyGoal(PropsUtil.getDataPropertyValue("SFGGoalName6"),
				PropsUtil.getDataPropertyValue("SFGGoalAmount1"), 0,
				PropsUtil.getDataPropertyValue("SFGFrequencyAmount1"),
				PropsUtil.getDataPropertyValue("SFGOnetimeFunding2"),
				PropsUtil.getDataPropertyValue("SFGFundingAmountValueUpdate2"));
		Assert.assertEquals(SFG.SFGAmountFlowConfirmationPupUpHeader().getText(),
				PropsUtil.getDataPropertyValue("SFGFrequencyGoalPopUpHeader"));
	}

	@Test(description = "Validate in pending allocation screen when allocated amount is less than goal amount", dependsOnMethods = {
			"Login" }, groups = { "DesktopBrowsers" }, priority = 3)
	public void vrifyFrequencyGoalConfirmationPopCloseIcon() throws InterruptedException {
		logger.info("verify frequency goal confirmation PopUp Close icon");
		Assert.assertTrue(SFG.SFGAmountFlowConfirmationPupUpCloseIcon().isDisplayed());
	}

	@Test(description = "Verify popup information", dependsOnMethods = { "Login" }, groups = {
			"DesktopBrowsers" }, priority = 4)
	public void vrifyFrequencyGoalConfirmationPopInfo1() throws InterruptedException {
		logger.info("verify frequency goal confirmation PopUp first info message");
		Assert.assertEquals(SFG.SFGAmountFlowConfirmationPupUpInfo1().getText().trim(),
				PropsUtil.getDataPropertyValue("SFGFrequencyGoalPopUpInfo1"));
	}

	@Test(description = "Verify popup information", dependsOnMethods = { "Login" }, groups = {
			"DesktopBrowsers" }, priority = 5)
	public void vrifyFrequencyGoalConfirmationPopInfo2() throws InterruptedException {
		logger.info("verify frequency goal confirmation PopUp second info message");
		Assert.assertEquals(SFG.SFGAmountFlowConfirmationPupUpInfo2().getText().trim(),
				PropsUtil.getDataPropertyValue("SFGFrequencyGoalPopUpInfo2"));
	}

	@Test(description = "Verify Radio Button", dependsOnMethods = { "Login" }, groups = {
			"DesktopBrowsers" }, priority = 6)
	public void vrifyFrequencyGoalConfirmationPopRadioButton1Selected() throws InterruptedException {
		logger.info("verify frequency goal confirmation PopUp first radioButton by default selected");
		Assert.assertTrue((SFG.SFGAmountFlowConfirmationPupUpRadioButton1().getAttribute("class").trim()
				.contains(PropsUtil.getDataPropertyValue("SFGFrequencyGoalPopUpRadioButton1Selected"))));
	}

	@Test(description = "Verify second radio button", dependsOnMethods = { "Login" }, groups = {
			"DesktopBrowsers" }, priority = 7)
	public void vrifyFrequencyGoalConfirmationPopRadioButton2Selected() throws InterruptedException {
		logger.info("verify frequency goal confirmation PopUp second radioButton by default selected");
		SeleniumUtil.click(SFG.SFGAmountFlowConfirmationPupUpRadioButton2());
		Assert.assertTrue((SFG.SFGAmountFlowConfirmationPupUpRadioButton2().getAttribute("class").trim()
				.contains(PropsUtil.getDataPropertyValue("SFGFrequencyGoalPopUpRadioButton1Selected"))));

	}

	@Test(description = "Verify radio button selected", dependsOnMethods = {
			"vrifyFrequencyGoalConfirmationPopRadioButton2Selected" }, groups = { "DesktopBrowsers" }, priority = 8)
	public void vrifyFrequencyGoalConfirmationPopRadioButton1UnSelected() throws InterruptedException {
		logger.info("verify frequency goal confirmation PopUp second radioButton by default selected");
		SeleniumUtil.click(SFG.SFGAmountFlowConfirmationPupUpRadioButton2());
		Assert.assertFalse((SFG.SFGAmountFlowConfirmationPupUpRadioButton1().getAttribute("class").trim()
				.contains(PropsUtil.getDataPropertyValue("SFGFrequencyGoalPopUpRadioButton1Selected"))));

	}

	@Test(description = "Verify radio button value", dependsOnMethods = {
			"vrifyFrequencyGoalConfirmationPopRadioButton1UnSelected" }, groups = { "DesktopBrowsers" }, priority = 9)
	public void vrifyFrequencyGoalConfirmationPopRadioButton1Value() throws InterruptedException {
		logger.info("verify frequency goal confirmation PopUp first radioButton value");
		Assert.assertEquals(SFG.SFGAmountFlowConfirmationPupUpRadioButton1().getText().trim(),
				PropsUtil.getDataPropertyValue("SFGFrequencyGoalPopUpRadioButton1value1") + " "
						+ SFG.targateDateSelectMMM(78) + " "
						+ PropsUtil.getDataPropertyValue("SFGFrequencyGoalPopUpRadioButton1value2") + " "
						+ SFG.targateDateSelectMMM(85));
	}

	@Test(description = "Verify radio button value", dependsOnMethods = {
			"vrifyFrequencyGoalConfirmationPopRadioButton1UnSelected" }, groups = { "DesktopBrowsers" }, priority = 10)
	public void vrifyFrequencyGoalConfirmationPopRadioButton2Value() throws InterruptedException {
		logger.info("verify frequency goal confirmation PopUp second radioButton value");
		Assert.assertEquals(SFG.SFGAmountFlowConfirmationPupUpRadioButton2().getText().trim(),
				PropsUtil.getDataPropertyValue("SFGFrequencyGoalPopUpRadioButton2value1"));
	}

	@Test(description = "Verify cancle button", dependsOnMethods = {
			"vrifyFrequencyGoalConfirmationPopRadioButton1UnSelected" }, groups = { "DesktopBrowsers" }, priority = 11)
	public void vrifyFrequencyGoalConfirmationPopCancelButton() throws InterruptedException {
		logger.info("verify frequency goal confirmation PopUp cancel Button");
		Assert.assertTrue(SFG.SFGAmountFlowConfirmationPupUpCancelButton().isDisplayed());
	}

	@Test(description = "Verify save button", dependsOnMethods = {
			"vrifyFrequencyGoalConfirmationPopRadioButton1UnSelected" }, groups = { "DesktopBrowsers" }, priority = 12)
	public void vrifyFrequencyGoalConfirmationPopSaveButton() throws InterruptedException {
		logger.info("verify frequency goal confirmation PopUp Save Button");
		Assert.assertTrue(SFG.SFGAmountFlowConfirmationPupUpSaveButton().isDisplayed());
	}

	@Test(description = "Verify close icon", dependsOnMethods = {
			"vrifyFrequencyGoalConfirmationPopRadioButton1UnSelected" }, groups = { "DesktopBrowsers" }, priority = 13)
	public void vrifyFrequencyGoalConfirmationPopCloseIconClick() throws InterruptedException {
		logger.info("verify frequency goal confirmation PopUp Close icon");
		SeleniumUtil.click(SFG.SFGAmountFlowConfirmationPupUpCloseIcon());
		SeleniumUtil.waitForPageToLoad(4000);
		Assert.assertTrue(SFG.isElementPresent(SFG.SFGAmountFlowConfirmationPupUpHeader()));
	}

	@Test(description = "Verify cancle button click", dependsOnMethods = {
			"vrifyFrequencyGoalConfirmationPopCloseIconClick" }, groups = { "DesktopBrowsers" }, priority = 14)
	public void vrifyFrequencyGoalConfirmationPopCancelButtonClick() throws InterruptedException {
		logger.info("verify frequency goal confirmation PopUp cancel Button click");
		SeleniumUtil.click(SFG.NextBtnStep1());
		SeleniumUtil.waitForPageToLoad(4000);
		SeleniumUtil.click(SFG.SFGAmountFlowConfirmationPupUpCancelButton());
		SeleniumUtil.waitForPageToLoad(4000);
		Assert.assertTrue(SFG.isElementPresent(SFG.SFGAmountFlowConfirmationPupUpHeader()));
	}

	@Test(description = "Verify that once the user chooses one of the options from the pop up, the system should move on to next step/screen as relevant.", dependsOnMethods = {
			"vrifyFrequencyGoalConfirmationPopCancelButtonClick" }, groups = { "DesktopBrowsers" }, priority = 15)
	public void vrifyFrequencyGoalConfirmationPopSaveRadioButton1() throws InterruptedException {
		logger.info(
				"Verify that at any point of time, if user terminates the goal in STEP 3, user should be able to access this goal from Goals In Progress screen where the UI lets user know he didn't complete it. At this stage, user should be able to resume the goal from where he left with all values preserved.");
		logger.info(
				"Verify that when user clicks 'start my goal', user should land on Goals In Progress screen viewing the summary card of the new goal.");
		SeleniumUtil.click(SFG.NextBtnStep1());
		SeleniumUtil.waitForPageToLoad(4000);
		SeleniumUtil.click(SFG.SFGAmountFlowConfirmationPupUpSaveButton());
		SeleniumUtil.waitForPageToLoad(4000);
		SeleniumUtil.click(SFG.NextBtnStep1());
		SeleniumUtil.waitForPageToLoad(4000);

		String expectedEndDate = null;
		for (int i = 0; i < SFG.SFGInprogressGoalNames().size(); i++) {
			if (SFG.SFGInprogressGoalNames().get(i).getText().trim()
					.equals(PropsUtil.getDataPropertyValue("SFGGoalName6").trim())) {
				expectedEndDate = SFG.SFGInprogressGoalEndDate().get(i).getText();
			}

		}
		Assert.assertEquals(expectedEndDate,
				PropsUtil.getDataPropertyValue("SFGInprogressGoalByText") + " " + SFG.targateDateSelectMMMMD(85));
	}

	@Test(description = "There should be a toast message if the user clicks on any radio button on the step 3 pop up", dependsOnMethods = {
			"vrifyFrequencyGoalConfirmationPopSaveRadioButton1" }, groups = { "DesktopBrowsers" }, priority = 16)
	public void vrifyCreateGoalwithAsItIsRadioButtonSucessMessage() throws InterruptedException {
		logger.info("verify frequency goal confirmation PopUpHeader");
		logger.info("AT-19636:The toast message should be shown on top of the screen and should be sticky");
		SFG.CreateGoalStep3FrequencyGoal(PropsUtil.getDataPropertyValue("SFGGoalName7"),
				PropsUtil.getDataPropertyValue("SFGGoalAmount1"), 0,
				PropsUtil.getDataPropertyValue("SFGFrequencyAmount1"),
				PropsUtil.getDataPropertyValue("SFGOnetimeFunding2"),
				PropsUtil.getDataPropertyValue("SFGFundingAmountValueUpdate2"));
		SeleniumUtil.waitForPageToLoad(5000);
		SeleniumUtil.click(SFG.SFGAmountFlowConfirmationPupUpRadioButton2());
		SeleniumUtil.waitForPageToLoad(5000);
		SeleniumUtil.click(SFG.SFGAmountFlowConfirmationPupUpSaveButton());

		SeleniumUtil.waitForPageToLoad(800);
		Assert.assertEquals(SFG.SFGGoalSaveMessage().getText(),
				PropsUtil.getDataPropertyValue("SFGIprogressGoalSuccessMessage"));
	}

	@Test(description = "Verify popup header", dependsOnMethods = {
			"vrifyCreateGoalwithAsItIsRadioButtonSucessMessage" }, groups = { "DesktopBrowsers" }, priority = 17)
	public void vrifyCreateGoalwithAsItIsRadioButton() throws InterruptedException {
		logger.info("verify frequency goal confirmation PopUpHeader");
		SeleniumUtil.waitForPageToLoad(2000);
		String expectedEndDate = null;
		for (int i = 0; i < SFG.SFGInprogressGoalNames().size(); i++) {
			if (SFG.SFGInprogressGoalNames().get(i).getText().trim()
					.equals(PropsUtil.getDataPropertyValue("SFGGoalName7").trim())) {
				System.out.println(SFG.SFGInprogressGoalNames().get(i).getText().trim());
				System.out.println(PropsUtil.getDataPropertyValue("SFGGoalName7").trim());
				expectedEndDate = SFG.SFGInprogressGoalEndDate().get(i).getText();
			}

		}
		Assert.assertEquals(expectedEndDate,
				PropsUtil.getDataPropertyValue("SFGInprogressGoalByText") + " " + SFG.targateDateSelectMMMM(78));

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
