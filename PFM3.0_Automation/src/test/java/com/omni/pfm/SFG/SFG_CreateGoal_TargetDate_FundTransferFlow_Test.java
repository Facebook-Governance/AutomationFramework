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

public class SFG_CreateGoal_TargetDate_FundTransferFlow_Test extends TestBase {

	SFG_LandingPage_Loc SFG;

	private static final Logger logger = LoggerFactory.getLogger(SFG_CreateGoal_FrqAmount_FundTransferFlow_Test.class);

	@BeforeClass(alwaysRun = true)
	public void testInit() throws InterruptedException {
		doInitialization("SFG_CreateGoal_TargetDate_FundTransferFlow");
		SFG = new SFG_LandingPage_Loc(d);
		d.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
	}

	@Test(description = "Verify Login Happens Successfully", groups = { "DesktopBrowsers" }, priority = 1)
	public void Login() throws Exception {
		SeleniumUtil.refresh(d);
		SeleniumUtil.waitForPageToLoad(2000);
		PageParser.forceNavigate("SFG", d);
		SeleniumUtil.waitForPageToLoad(5000);
		SFG.clikcgoalWithoutSave();
	}

	@Test(description = "Verify Target Date Goal Confirmation header", dependsOnMethods = { "Login" }, groups = { "DesktopBrowsers" }, priority = 2)
	public void vrifyTargetDateGoalConfirmationPopUpHeader() throws InterruptedException {
		logger.info("verify frequency goal confirmation PopUpHeader");

		SFG.CreateGoalWithTargateDate(PropsUtil.getDataPropertyValue("SFGGoalName9"),
				PropsUtil.getDataPropertyValue("SFGGoalAmount3"), SFG.targateDateSelect(30),
				PropsUtil.getDataPropertyValue("SFGOnetimeFunding4"),
				PropsUtil.getDataPropertyValue("SFGFundingAmountValueUpdate3"));
		Assert.assertEquals(SFG.SFGAmountFlowConfirmationPupUpHeader().getText(),
				PropsUtil.getDataPropertyValue("SFGFrequencyGoalPopUpHeader"));
	}

	@Test(description = "Verify the popup close icon", dependsOnMethods = { "vrifyTargetDateGoalConfirmationPopUpHeader" }, groups = {
			"DesktopBrowsers" }, priority = 3)
	public void vrifyTargetDateGoalConfirmationPopCloseIcon() throws InterruptedException {
		logger.info("verify frequency goal confirmation PopUp Close icon");
		Assert.assertTrue(SFG.SFGAmountFlowConfirmationPupUpCloseIcon().isDisplayed());
	}

	@Test(description = "Verify popup info", dependsOnMethods = { "vrifyTargetDateGoalConfirmationPopUpHeader" }, groups = {
			"DesktopBrowsers" }, priority = 4)
	public void vrifyTargetDateGoalConfirmationPopInfo1() throws InterruptedException {
		logger.info("verify frequency goal confirmation PopUp first info message");
		Assert.assertEquals(SFG.SFGAmountFlowConfirmationPupUpInfo1().getText().trim(),
				PropsUtil.getDataPropertyValue("SFGFrequencyGoalPopUpInfo1"));
	}

	@Test(description = "Verify Popup info", dependsOnMethods = { "vrifyTargetDateGoalConfirmationPopUpHeader" }, groups = {
			"DesktopBrowsers" }, priority = 5)
	public void vrifyTargetDateGoalConfirmationPopInfo2() throws InterruptedException {
		logger.info("verify frequency goal confirmation PopUp second info message");
		Assert.assertEquals(SFG.SFGAmountFlowConfirmationPupUpInfo2().getText().trim(),
				PropsUtil.getDataPropertyValue("SFGFrequencyGoalPopUpInfo2"));
	}

	@Test(description = "Verify radio button selected", dependsOnMethods = { "vrifyTargetDateGoalConfirmationPopUpHeader" }, groups = {
			"DesktopBrowsers" }, priority = 6)
	public void vrifyTargetDateGoalConfirmationPopRadioButton1Selected() throws InterruptedException {
		logger.info("verify frequency goal confirmation PopUp first radioButton by default selected");
		Assert.assertTrue((SFG.SFGAmountFlowConfirmationPupUpRadioButton1().getAttribute("class").trim()
				.contains(PropsUtil.getDataPropertyValue("SFGFrequencyGoalPopUpRadioButton1Selected"))));
	}

	@Test(description = "Verify radio button 2", dependsOnMethods = { "vrifyTargetDateGoalConfirmationPopUpHeader" }, groups = {
			"DesktopBrowsers" }, priority = 7)
	public void vrifyTargetDateGoalConfirmationPopRadioButton2Selected() throws InterruptedException {
		logger.info("verify frequency goal confirmation PopUp second radioButton by default selected");
		SeleniumUtil.click(SFG.SFGAmountFlowConfirmationPupUpRadioButton2());
		Assert.assertTrue((SFG.SFGAmountFlowConfirmationPupUpRadioButton2().getAttribute("class").trim()
				.contains(PropsUtil.getDataPropertyValue("SFGFrequencyGoalPopUpRadioButton1Selected"))));

	}

	@Test(description = "Verify radio button", dependsOnMethods = { "vrifyTargetDateGoalConfirmationPopRadioButton2Selected" }, groups = {
			"DesktopBrowsers" }, priority = 8)
	public void vrifyTargetDateGoalConfirmationPopRadioButton1UnSelected() throws InterruptedException {
		logger.info("verify frequency goal confirmation PopUp second radioButton by default selected");
		SeleniumUtil.click(SFG.SFGAmountFlowConfirmationPupUpRadioButton2());
		Assert.assertFalse((SFG.SFGAmountFlowConfirmationPupUpRadioButton1().getAttribute("class").trim()
				.contains(PropsUtil.getDataPropertyValue("SFGFrequencyGoalPopUpRadioButton1Selected"))));

	}

	@Test(description = "Verify radio button value", dependsOnMethods = {
			"vrifyTargetDateGoalConfirmationPopRadioButton1UnSelected" }, groups = { "DesktopBrowsers" }, priority = 9)
	public void vrifyTargetDateGoalConfirmationPopRadioButton1Value() throws InterruptedException {
		logger.info("verify frequency goal confirmation PopUp first radioButton value");
		Assert.assertEquals(SFG.SFGAmountFlowConfirmationPupUpRadioButton1().getText().trim(),
				PropsUtil.getDataPropertyValue("SFGFrequencyGoalPopUpRadioButton1value1") + " "
						+ SFG.targateDateSelectMMM(30) + " "
						+ PropsUtil.getDataPropertyValue("SFGFrequencyGoalPopUpRadioButton1value2") + " "
						+ SFG.targateDateSelectMMM(43));
	}

	@Test(description = "Verify radio button", dependsOnMethods = {
			"vrifyTargetDateGoalConfirmationPopRadioButton1UnSelected" }, groups = { "DesktopBrowsers" }, priority = 10)
	public void vrifyTargetDateConfirmationPopRadioButton2Value() throws InterruptedException {
		logger.info("verify frequency goal confirmation PopUp second radioButton value");
		Assert.assertEquals(SFG.SFGAmountFlowConfirmationPupUpRadioButton2().getText().trim(),
				PropsUtil.getDataPropertyValue("SFGFrequencyGoalPopUpRadioButton2value1"));
	}

	@Test(description = "Verify popup cancle button", dependsOnMethods = {
			"vrifyTargetDateGoalConfirmationPopRadioButton1UnSelected" }, groups = { "DesktopBrowsers" }, priority = 11)
	public void vrifyTargetDateConfirmationPopCancelButton() throws InterruptedException {
		logger.info("verify frequency goal confirmation PopUp cancel Button");
		Assert.assertTrue(SFG.SFGAmountFlowConfirmationPupUpCancelButton().isDisplayed());
	}

	@Test(description = "Verify save button", dependsOnMethods = {
			"vrifyTargetDateGoalConfirmationPopRadioButton1UnSelected" }, groups = { "DesktopBrowsers" }, priority = 12)
	public void vrifyTargetDateGoalConfirmationPopSaveButton() throws InterruptedException {
		logger.info("verify frequency goal confirmation PopUp Save Button");
		Assert.assertTrue(SFG.SFGAmountFlowConfirmationPupUpSaveButton().isDisplayed());
	}

	@Test(description = "Verify popup close icon", dependsOnMethods = {
			"vrifyTargetDateGoalConfirmationPopRadioButton1UnSelected" }, groups = { "DesktopBrowsers" }, priority = 13)
	public void vrifyTargetDateGoalConfirmationPopCloseIconClick() throws InterruptedException {
		logger.info("verify frequency goal confirmation PopUp Close icon");
		SeleniumUtil.click(SFG.SFGAmountFlowConfirmationPupUpCloseIcon());
		SeleniumUtil.waitForPageToLoad(4000);
		Assert.assertTrue(SFG.isElementPresent(SFG.SFGAmountFlowConfirmationPupUpHeader()));
	}

	@Test(description = "Verify cancle button click", dependsOnMethods = { "vrifyTargetDateGoalConfirmationPopCloseIconClick" }, groups = {
			"DesktopBrowsers" }, priority = 14)
	public void vrifyTargetDateGoalConfirmationPopCancelButtonClick() throws InterruptedException {
		logger.info("verify frequency goal confirmation PopUp cancel Button click");
		SeleniumUtil.click(SFG.NextBtnStep1());
		SeleniumUtil.waitForPageToLoad(4000);
		SeleniumUtil.click(SFG.SFGAmountFlowConfirmationPupUpCancelButton());
		SeleniumUtil.waitForPageToLoad(4000);
		Assert.assertTrue(SFG.isElementPresent(SFG.SFGAmountFlowConfirmationPupUpHeader()));
	}

	@Test(description = "Verify next button", dependsOnMethods = { "vrifyTargetDateGoalConfirmationPopCancelButtonClick" }, groups = {
			"DesktopBrowsers" }, priority = 15)
	public void vrifyTargetDateGoalConfirmationPopSaveRadioButton1() throws InterruptedException {
		SeleniumUtil.click(SFG.NextBtnStep1());
		SeleniumUtil.waitForPageToLoad(2000);
		SeleniumUtil.click(SFG.SFGAmountFlowConfirmationPupUpSaveButton());
		SeleniumUtil.waitForPageToLoad(2000);
		SeleniumUtil.click(SFG.NextBtnStep1());
		SeleniumUtil.waitForPageToLoad(2000);

		String expectedEndDate = null;
		for (int i = 0; i < SFG.SFGInprogressGoalNames().size(); i++) {
			if (SFG.SFGInprogressGoalNames().get(i).getText().trim()
					.equals(PropsUtil.getDataPropertyValue("SFGGoalName9").trim()))
				;
			{
				expectedEndDate = SFG.SFGInprogressGoalEndDate().get(i).getText();
			}

		}
		Assert.assertEquals(expectedEndDate,
				PropsUtil.getDataPropertyValue("SFGInprogressGoalByText") + " " + SFG.targateDateSelectMMMMD(85));
	}

	@Test(description = "Verify confirmation popup header", dependsOnMethods = { "vrifyTargetDateGoalConfirmationPopSaveRadioButton1" }, groups = {
			"DesktopBrowsers" }, priority = 16)
	public void vrifyTargetDateCreateGoalwithAsItIsRadioButton() throws InterruptedException {
		logger.info("verify frequency goal confirmation PopUpHeader");
		SFG.CreateGoalWithTargateDate(PropsUtil.getDataPropertyValue("SFGGoalName10"),
				PropsUtil.getDataPropertyValue("SFGGoalAmount3"), SFG.targateDateSelect(30),
				PropsUtil.getDataPropertyValue("SFGOnetimeFunding4"),
				PropsUtil.getDataPropertyValue("SFGFundingAmountValueUpdate3"));
		SeleniumUtil.waitForPageToLoad(2000);
		SeleniumUtil.click(SFG.SFGAmountFlowConfirmationPupUpRadioButton2());
		SeleniumUtil.waitForPageToLoad(2000);
		SeleniumUtil.click(SFG.SFGAmountFlowConfirmationPupUpSaveButton());
		SeleniumUtil.waitForPageToLoad(2000);
		String expectedEndDate = null;
		for (int i = 0; i < SFG.SFGInprogressGoalNames().size(); i++) {
			if (SFG.SFGInprogressGoalNames().get(i).getText().trim()
					.equals(PropsUtil.getDataPropertyValue("SFGGoalName10").trim())) {
				System.out.println(SFG.SFGInprogressGoalNames().get(i).getText().trim());
				System.out.println(PropsUtil.getDataPropertyValue("SFGGoalName7").trim());
				expectedEndDate = SFG.SFGInprogressGoalEndDate().get(i).getText();
			}

		}
		Assert.assertEquals(expectedEndDate,
				PropsUtil.getDataPropertyValue("SFGInprogressGoalByText") + " " + SFG.targateDateSelectMMMMD(30));

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
