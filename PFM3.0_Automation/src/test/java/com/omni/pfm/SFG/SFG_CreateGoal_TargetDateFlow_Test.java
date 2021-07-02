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

import org.openqa.selenium.Keys;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.accessibility.examples.testng.webdriver.test.RunAccessibilityTest;
import com.omni.pfm.PageProcessor.PageParser;
import com.omni.pfm.pages.Login.LoginPage;
import com.omni.pfm.pages.SFG.SFG_LandingPage_Loc;
import com.omni.pfm.testBase.TestBase;
import com.omni.pfm.utility.PropsUtil;
import com.omni.pfm.utility.SeleniumUtil;

public class SFG_CreateGoal_TargetDateFlow_Test extends TestBase {

	SFG_LandingPage_Loc SFG;

	private static final Logger logger = LoggerFactory.getLogger(SFG_CreateGoal_FrqAmount_FundTransferFlow_Test.class);

	@BeforeClass(alwaysRun = true)
	public void testInit() throws InterruptedException {
		SFG = new SFG_LandingPage_Loc(d);
		doInitialization("SFG_CreateGoal_TargetDateFlow");
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

	@Test(description = "Verify that if the target date is given,the formula to calculate the planned funding per each allocation is ((Total Amount - Allocated Amount) / ((target date - start date)/frequency))", dependsOnMethods = {
			"Login" }, groups = { "DesktopBrowsers" }, priority = 2)
	public void verifyTagateDatevaluesinSteps2() throws InterruptedException {
		logger.info("verify saving values in steps two after create first steps with targate date");
		logger.info(
				"AT-16785:Mandatory Field: User should either be able to give a target date based on which application should calculate the planned funding or give a specific amount to be saved at a defined frequency.");
		logger.info(
				"AT-17628:Verify that if the user allocates an amount that is greater than the goal amount, the system should show the user a pop up asking whether he wants to modify the end date or save it as it is.");
		SFG.step2LandingPageWithTagateDate(PropsUtil.getDataPropertyValue("SFGGoalName8"),
				PropsUtil.getDataPropertyValue("SFGGoalAmount2"), SFG.targateDateSelect(30));
		SeleniumUtil.waitForPageToLoad(3000);
		Assert.assertEquals(SFG.SFGTargateSaveLabel().getText() + " " + SFG.SFGTargateSaveValue().getText(),
				PropsUtil.getDataPropertyValue("SFGTargateDateValueInStep2"));
	}

	@Test(description = "", dependsOnMethods = { "Login" }, groups = { "DesktopBrowsers" }, priority = 3)
	public void verifyTagatedateDefaultFrequency() throws InterruptedException {
		logger.info("select targate date in step1 and check dafault frequency in step2");
		Assert.assertEquals(SFG.SFGTargateFrequencytext().getText(),
				PropsUtil.getDataPropertyValue("SFGTargateDateDefaultFrequency"));

	}

	@Test(description = "", dependsOnMethods = { "Login" }, groups = { "DesktopBrowsers" }, priority = 4)
	public void verifyTagatedateFrequencyDropDownclick() throws InterruptedException {
		logger.info("select targate date in step1 and click frequency dropdown in step2 ");
		SeleniumUtil.click(SFG.SFGTargateFrequencytext());
		Assert.assertTrue(SFG.SFGTargateFrequencytextList().get(0).isDisplayed());

	}

	@Test(description = "", dependsOnMethods = { "verifyTagatedateFrequencyDropDownclick" }, groups = { "DesktopBrowsers" }, priority = 5)
	public void verifyTagatedateFrequencyDropDownIconclick() throws InterruptedException {
		SeleniumUtil.click(SFG.SFGTargateFrequencytext());
		SeleniumUtil.click(SFG.SFGTargateFrequencyIcon());
		SeleniumUtil.waitForPageToLoad();
		Assert.assertTrue(SFG.SFGTargateFrequencytextList().get(0).isDisplayed());

	}

	@Test(description = "", dependsOnMethods = { "verifyTagatedateFrequencyDropDownIconclick" }, groups = { "DesktopBrowsers" }, priority = 6)
	public void verifyTagatedatesaveValuesAfterChangeFrequency() throws InterruptedException {
		SeleniumUtil.click(SFG.SFGTargateFrequencytextList().get(0));
		SeleniumUtil.waitForPageToLoad(2000);
		Assert.assertEquals(SFG.SFGTargateSaveLabel().getText() + " " + SFG.SFGTargateSaveValue().getText(),
				PropsUtil.getDataPropertyValue("SFGTargateDateValueAfterChangeFreqInStep2"));
	}

	@Test(description = "", dependsOnMethods = { "verifyTagatedatesaveValuesAfterChangeFrequency" }, groups = { "DesktopBrowsers" }, priority = 7)
	public void verifyTagatedatesaveValuesAfterAddOntimeFunding() throws InterruptedException {
		SFG.OntimeFundingTextBox().sendKeys(PropsUtil.getDataPropertyValue("SFGTargateDateValueOneTimeFunding"));
		SFG.OntimeFundingTextBox().sendKeys(Keys.TAB);
		Assert.assertEquals(SFG.SFGTargateSaveLabel().getText() + " " + SFG.SFGTargateSaveValue().getText(),
				PropsUtil.getDataPropertyValue("SFGTargateDateValueAfterChangeOnTimeFunding"));

	}

	@Test(description = "", dependsOnMethods = { "verifyTagatedatesaveValuesAfterAddOntimeFunding" }, groups = { "DesktopBrowsers" }, priority = 8)
	public void vrifyOnetimeAmountMessage() throws InterruptedException {
		logger.info("verify Enter Amount in textbox");
		SeleniumUtil.click(SFG.AccountdropDownField());
		SeleniumUtil.waitForPageToLoad(1000);
		SeleniumUtil.click(SFG.MultiAccountToggel());
		SeleniumUtil.waitForPageToLoad(1000);
		SeleniumUtil.click(SFG.selectAccountCheckBox(1));
		SeleniumUtil.waitForPageToLoad(1000);
		SFG.AccountPopUPonTimeFundingTextBox().get(1).clear();
		SFG.AccountPopUPonTimeFundingTextBox().get(1).sendKeys(PropsUtil.getDataPropertyValue("SFGOnetimeFunding3"));
		SFG.AccountPopUPonTimeFundingTextBox().get(1).sendKeys(Keys.TAB);

		Assert.assertEquals(SFG.AccountsOneTimeFundingAmountMessage().getText(),
				PropsUtil.getDataPropertyValue("SFGAccountOnetimeFundingMessage"));
	}

	@Test(description = "", dependsOnMethods = { "vrifyOnetimeAmountMessage" }, groups = { "DesktopBrowsers" }, priority = 9)
	public void vrifySaveAccount() throws InterruptedException {
		logger.info("verify Account dropdown filed value");
		SeleniumUtil.click(SFG.AccountPopUpSave());
		SeleniumUtil.waitForPageToLoad(3000);
		Assert.assertEquals(SFG.AccountdropDownField().getAttribute("value"),
				PropsUtil.getDataPropertyValue("SFG2accountSelected"));
	}

	@Test(description = "", dependsOnMethods = { "vrifySaveAccount" }, groups = { "DesktopBrowsers" }, priority = 10)
	public void vrifyOnetimeFundingamountFromAccount() throws InterruptedException {
		logger.info("verify all selected account one time funding rule");
		Assert.assertEquals(
				SFG.AccountAllocatedText().get(0).getText() + " " + SFG.AccountAllocatedAmount().get(0).getText() + " "
						+ SFG.AccountAllocatedAmountFrom().get(0).getText() + " "
						+ SFG.AccountAllocatedAmountFromAccount().get(0).getText(),
				PropsUtil.getDataPropertyValue("SFGFirstAccounuOneTimeFunding"));
		Assert.assertEquals(
				SFG.AccountAllocatedText().get(1).getText() + " " + SFG.AccountAllocatedAmount().get(1).getText() + " "
						+ SFG.AccountAllocatedAmountFrom().get(1).getText() + " "
						+ SFG.AccountAllocatedAmountFromAccount().get(1).getText(),
				PropsUtil.getDataPropertyValue("SFGSecondAccounuOneTimeFunding"));
	}

	@Test(description = "", dependsOnMethods = { "Login" }, groups = { "DesktopBrowsers" }, priority = 11)
	public void vrifySaveValueAfterAddingaccountonetimeFunding() throws InterruptedException {
		logger.info("verify Save value After adding onetime fundding rule in account popup");
		Assert.assertEquals(SFG.SFGTargateSaveLabel().getText() + " " + SFG.SFGTargateSaveValue().getText(),
				PropsUtil.getDataPropertyValue("SFGTargateDateValueAfterChangeOnTimeFundingInAccountpopUp"));

	}

	@Test(description = "", dependsOnMethods = { "Login" }, groups = { "DesktopBrowsers" }, priority = 12)
	public void vrifyCreateGoalWithTargAteDate() throws InterruptedException {
		SeleniumUtil.click(SFG.NextBtnStep1());
		SeleniumUtil.waitForPageToLoad(3000);
		SeleniumUtil.click(SFG.SFGFundingSaveButton());
		SeleniumUtil.waitForPageToLoad(3000);
		SeleniumUtil.click(SFG.NextBtnStep1());
		boolean expectedGoalName = false;
		for (int i = 0; i < SFG.SFGInprogressGoalNames().size(); i++) {
			if (SFG.SFGInprogressGoalNames().get(i).getText().trim()
					.equals(PropsUtil.getDataPropertyValue("SFGGoalName8").trim())) {
				expectedGoalName = true;
				break;
			}

		}
		Assert.assertTrue(expectedGoalName);
	}

	@Test(description = "", dependsOnMethods = { "Login" }, groups = { "DesktopBrowsers" }, priority = 13)
	public void vrifyCreatedInprogressGoalEndDate() throws InterruptedException {
		logger.info("verify create inprogress goal Enda date ");
		String expectedEndDate = null;
		for (int i = 0; i < SFG.SFGInprogressGoalNames().size(); i++) {
			if (SFG.SFGInprogressGoalNames().get(i).getText().trim()
					.equals(PropsUtil.getDataPropertyValue("SFGGoalName8").trim())) {
				expectedEndDate = SFG.SFGInprogressGoalEndDate().get(i).getText();
				break;
			}

		}
		Assert.assertEquals(expectedEndDate,
				PropsUtil.getDataPropertyValue("SFGInprogressGoalByText") + " " + SFG.targateDateSelectMMMMD(30));
	}

	@Test(description = "", dependsOnMethods = { "Login" }, groups = { "DesktopBrowsers" }, priority = 14)
	public void vrifyCreatedInprogressGoalPgrogressBar() throws InterruptedException {

		logger.info("verify Created inprogress goal progress bar value ");
		String expectedProgressBarValue = null;
		for (int i = 0; i < SFG.SFGInprogressGoalNames().size(); i++) {
			if (SFG.SFGInprogressGoalNames().get(i).getText().trim()
					.equals(PropsUtil.getDataPropertyValue("SFGGoalName8").trim())) {
				expectedProgressBarValue = SFG.SFGInprogressGoalProgressBar().get(i).getAttribute("style");
				break;
			}

		}
		if(appFlag.equals("app") || appFlag.equals("emulator")) {
			Assert.assertEquals(expectedProgressBarValue,
					PropsUtil.getDataPropertyValue("SFGInprogressGoalProgressBarStyle_mobile"));
		}else {
			Assert.assertEquals(expectedProgressBarValue,
					PropsUtil.getDataPropertyValue("SFGInprogressGoalProgressBarStyle"));
		}
		
	}

	@Test(description = "", dependsOnMethods = { "Login" }, groups = { "DesktopBrowsers" }, priority = 15)
	public void vrifyCreatedInprogressGoalOneTimeFundingAmount() throws InterruptedException {
		logger.info("verify created inprogress goal one time funding amount");
		String expectedOnetimeFundingValue = null;
		for (int i = 0; i < SFG.SFGInprogressGoalNames().size(); i++) {
			if (SFG.SFGInprogressGoalNames().get(i).getText().trim()
					.equals(PropsUtil.getDataPropertyValue("SFGGoalName8").trim())) {
				expectedOnetimeFundingValue = SFG.SFGInprogressGoalOneTimeFundingValue().get(i).getText();
				break;
			}

		}

		Assert.assertEquals(expectedOnetimeFundingValue,
				PropsUtil.getDataPropertyValue("SFGInprogressGoalOnetimeFundingAmount"));
	}

	@Test(description = "", dependsOnMethods = { "Login" }, groups = { "DesktopBrowsers" }, priority = 16)
	public void vrifyCreatedInprogressGoalOfText() throws InterruptedException {
		logger.info("verify created inprogress goal Of text ");
		String expectedOftext = null;
		for (int i = 0; i < SFG.SFGInprogressGoalNames().size(); i++) {
			if (SFG.SFGInprogressGoalNames().get(i).getText().trim()
					.equals(PropsUtil.getDataPropertyValue("SFGGoalName8").trim())) {
				expectedOftext = SFG.SFGInprogressGoalOfText().get(i).getText();
				break;
			}

		}

		Assert.assertEquals(expectedOftext, PropsUtil.getDataPropertyValue("SFGInprogressGoalOfText"));
	}

	@Test(description = "", dependsOnMethods = { "Login" }, groups = { "DesktopBrowsers" }, priority = 17)
	public void vrifyCreatedInprogressGoalTargateAmount() throws InterruptedException {
		logger.info("verify created inprogress goal Targate amount ");
		String expectedTargateAmount = null;
		for (int i = 0; i < SFG.SFGInprogressGoalNames().size(); i++) {
			if (SFG.SFGInprogressGoalNames().get(i).getText().trim()
					.equals(PropsUtil.getDataPropertyValue("SFGGoalName8").trim())) {
				expectedTargateAmount = SFG.SFGInprogressGoalTargateAmount().get(i).getText();
				break;
			}

		}

		Assert.assertEquals(expectedTargateAmount, PropsUtil.getDataPropertyValue("SFGInprogressGoalTargateAmount2"));
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
