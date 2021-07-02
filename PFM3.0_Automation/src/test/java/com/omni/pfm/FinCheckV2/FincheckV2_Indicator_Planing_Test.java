package com.omni.pfm.FinCheckV2;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.omni.pfm.PageProcessor.PageParser;
import com.omni.pfm.pages.AccountAddition.AccountAddition;
import com.omni.pfm.pages.FincheckV2.FincheckV2_GetStarted_Loc;
import com.omni.pfm.pages.FincheckV2.FincheckV2_Planning_Loc;
import com.omni.pfm.pages.Login.LoginPage;
import com.omni.pfm.testBase.TestBase;
import com.omni.pfm.utility.FincheckUtil;
import com.omni.pfm.utility.PropsUtil;
import com.omni.pfm.utility.SeleniumUtil;

/**
 * Copyright (c) 2018 Yodlee Inc. All Rights Reserved.
 *
 * This software is the confidential and proprietary information of Yodlee, Inc.
 * Use is subject to license terms.
 *
 * @author sbhat1
 */
public class FincheckV2_Indicator_Planing_Test extends TestBase {

	private static final Logger logger = LoggerFactory.getLogger(FincheckV2_Indicator_Planing_Test.class);
	FincheckV2_Planning_Loc v2planningloc;
	AccountAddition accountAddition;
	FincheckV2_GetStarted_Loc onboard;
	FincheckUtil util;
	String platformName = null;

	@BeforeClass(alwaysRun = true)
	public void doInit() throws Exception {
		doInitialization("Fincheck V2 Intialization");
		v2planningloc = new FincheckV2_Planning_Loc(d);
		accountAddition = new AccountAddition();
		onboard = new FincheckV2_GetStarted_Loc(d);
		util = new FincheckUtil(d);

		LoginPage.loginMain(d, loginParameter);
		SeleniumUtil.waitForPageToLoad();
		accountAddition.linkAccount();
		accountAddition.addAggregatedAccount(PropsUtil.getDataPropertyValue("finV2.creditScore.DagSite"),
				PropsUtil.getDataPropertyValue("finV2.creditScore.DagPassword"));
		d.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

	}

	@Test(description = "Finishing FTUE", priority = 1)
	public void quickOnboarding() {
		PageParser.forceNavigate("FincheckV2", d);
		SeleniumUtil.waitForPageToLoad();
		onboard.quickOnboarding(PropsUtil.getDataPropertyValue("finV2.birthYear1988"),
				PropsUtil.getDataPropertyValue("finV2.annualIncome12k"), 2, 4);
		SeleniumUtil.waitForPageToLoad();
		SeleniumUtil.click(v2planningloc.planningIndicatoroverview());

		if (v2planningloc.isfinV2_mobile_creditind_closeicon_Present()) {
			SeleniumUtil.click(v2planningloc.mobileCloseicon());

			SeleniumUtil.click(v2planningloc.planningIndicatoroverview());
			SeleniumUtil.waitForPageToLoad();
		}
	}

	@Test(description = "AT-95150:erifying the TakeAction section  planning indicator", dependsOnMethods = {
			"quickOnboarding" }, priority = 2)
	public void takeActionsection() {
		logger.info(">>>>> verifying Take Action");
		Assert.assertEquals(v2planningloc.getTakeActionHeader().getText().trim(),
				PropsUtil.getDataPropertyValue("finv2.planning.takeactionheader"), "*** Header mismatch.");

	}

	@Test(description = "AT-95177:Verifying the TakeAction Description in  planning indicator", dependsOnMethods = {
			"quickOnboarding" }, priority = 3)
	public void takeActiondesc1() {

		logger.info(">>>>> verifying Take Action description");
		Assert.assertEquals(v2planningloc.getTakeActiondesc1().getText().trim(),
				PropsUtil.getDataPropertyValue("finv2.planning.takeactiondesc1"), "*** Description mismatch.");

	}

	@Test(description = "AT-95177:Verifying the TakeAction Description in  planning indicator", dependsOnMethods = {
			"quickOnboarding" }, priority = 4)
	public void takeActiondesc2() {

		logger.info(">>>>> verifying Take Action description CALCULATOR");

		Assert.assertEquals(v2planningloc.getTakeActiondesc2().get(0).getText().trim(),
				PropsUtil.getDataPropertyValue("finv2.planning.takeactiondesc2"), "*** Description mismatch.");
		Assert.assertEquals(v2planningloc.getTakeActiondesc2().get(1).getText().trim(),
				PropsUtil.getDataPropertyValue("finv2.planning.takeactiondesc2"), "*** Description mismatch.");

	}

	@Test(description = "AT-95177:Verifying the TakeAction calculation title Description in  planning indicator", dependsOnMethods = {
			"quickOnboarding" }, priority = 5)
	public void takeActioncaltitle() {

		logger.info(">>>>> verifying Take Action description Paying Debt vs. Investing");
		Assert.assertEquals(v2planningloc.getTakeactioncaltitle().get(0).getText().trim(),
				PropsUtil.getDataPropertyValue("finv2.planning.takeactiondesc3"), "*** Description mismatch.");
		Assert.assertEquals(v2planningloc.getTakeactioncaltitle().get(1).getText().trim(),
				PropsUtil.getDataPropertyValue("finv2.planning.takeactiondesc5"), "*** Description mismatch.");
	}

	@Test(description = "AT-95177:Verifying the TakeAction Description in  planning indicator", dependsOnMethods = {
			"quickOnboarding" }, priority = 6)
	public void takeActioncaldesc() {

		logger.info(">>>>> verifying Take Action description Paying Debt vs. Investing description");
		Assert.assertEquals(v2planningloc.getTakeactioncaldesc().get(0).getText().trim(),
				PropsUtil.getDataPropertyValue("finv2.planning.takeactiondesc4"), "*** Description mismatch.");
		Assert.assertEquals(v2planningloc.getTakeactioncaldesc().get(1).getText().trim(),
				PropsUtil.getDataPropertyValue("finv2.planning.takeactiondesc6"), "*** Description mismatch.");
	}

	@Test(description = "AT-95178:Verifying the SFG button displayed in the planning indicator", dependsOnMethods = {
			"quickOnboarding" }, priority = 7)
	public void saveForgoalbtn() {
		logger.info(">>>>> verifying SFG button");
		Assert.assertTrue(v2planningloc.planningSaveforgoalbtn().isDisplayed(), "*** Description mismatch.");

	}

	@Test(description = "AT-95169:Verifying the More Resources button displayed in the planning indicator", dependsOnMethods = {
			"quickOnboarding" }, priority = 8)
	public void verifyMoreResourceBtn() {

		logger.info(">>>>> verifying More Resources button");
		Assert.assertTrue(v2planningloc.planningMoreresourcesbtn().isDisplayed(), "*** More resource not displayed.");

	}

	@Test(description = "AT-95179,AT-95180:Verifying the Back to Fincheck link displayed in SFG FinApp", dependsOnMethods = {
			"quickOnboarding" }, priority = 9)
	public void verifyBackTofinchecklink() {

		logger.info(">>>>> verifying Back to Fincheck link in SFG FinApp");

		SeleniumUtil.click(v2planningloc.planningSaveforgoalbtn());
		if (v2planningloc.isfinV2_mobile_planningind_backicon()) {
			v2planningloc.mobileBackicon().isDisplayed();
		} else {
			SeleniumUtil.waitForPageToLoad();
			Assert.assertTrue(v2planningloc.planningBacktofinchecklink().isDisplayed(), "*** Back Link mismatch.");

		}

	}

	@Test(description = "AT-95181:Verifying the FinApp navigates to Planning indicator indicator", dependsOnMethods = {
			"quickOnboarding" }, priority = 10)
	public void navigatesBacktoplanningindicator() {

		logger.info(">>>>> verifying Finapp naivgates back to planning indicator");

		if (v2planningloc.isfinV2_mobile_planningind_backicon()) {
			SeleniumUtil.click(v2planningloc.mobileBackicon());
		} else {

			SeleniumUtil.click(v2planningloc.planningBacktofinchecklink());

			Assert.assertEquals(v2planningloc.planningIndicatorheadertext().getText().trim(),
					PropsUtil.getDataPropertyValue("finv2.planning.headertext"), "*** Header mismatch.");
		}
	}

	@Test(description = "AT-95302:Verifying the FinApp navigates to Planning indicator indicator", dependsOnMethods = {
			"quickOnboarding" }, priority = 11)
	public void verifyStatusbartext() {
		PageParser.forceNavigate("FincheckV2", d);
		SeleniumUtil.waitForPageToLoad();
		By planningIndicator = SeleniumUtil.getByObject("FincheckV2", null, "finV2-planningindicator");
		SeleniumUtil.click(planningIndicator);
		SeleniumUtil.waitFor(3);
		SeleniumUtil.waitForPageToLoad();
		logger.info(">>>>> verifying the status bar text in planning indicator");
		Assert.assertEquals(v2planningloc.planningStatusbartext().getText().trim(),
				PropsUtil.getDataPropertyValue("finv2.planning.statusbartext"), "*** Status text mismatch.");

	}

	@Test(description = "AT-95898:Verifying the score details header in planning indicator", dependsOnMethods = {
			"quickOnboarding" }, priority = 12)
	public void verifyScoresection() {

		logger.info(">>>>> verifying the status section in  planning indicator");
		Assert.assertEquals(v2planningloc.planningIndicatorscoresection().getText().trim(),
				PropsUtil.getDataPropertyValue("finv2.planning.scoredetailsheader"), "*** Header mismatch.");

	}

	@Test(description = "AT-95921:Verify the text and section\"Update Your Planning info\" , \"Why financial plannig is important\" displayed in the indicator 8 score details section", dependsOnMethods = {
			"quickOnboarding" }, priority = 13)
	public void verifyScoresectionDesc() {

		logger.info(">>>>> verifying the score details sections in planning indicator");
		Assert.assertEquals(v2planningloc.planningIndicatorscoresection1().getText().trim(),
				PropsUtil.getDataPropertyValue("finv2.planning.scoredetailssec1"), "*** Details 1 mismatch.");

		Assert.assertEquals(v2planningloc.planningIndicatorscoresection2().getText().trim(),
				PropsUtil.getDataPropertyValue("finv2.planning.scoredetailssec2"), "*** Details 2 mismatch.");

	}

	@Test(description = "AT-95958:Verify the financial calculator text in header", dependsOnMethods = {
			"quickOnboarding" }, priority = 14)
	public void verifyFincalCalculatorHeaderText() {

		logger.info(">>>>> verifying the Financial calculator header text");
		SeleniumUtil.click(v2planningloc.planningMoreresourcesbtn());
		SeleniumUtil.waitForPageToLoad(3000);
		Assert.assertEquals(v2planningloc.planningIndicatorfincalheader().getText().trim(),
				PropsUtil.getDataPropertyValue("finv2.planning.financialcalheader"), "*** Header mismatch.");

	}

	@Test(description = "AT-95958:Verify the financial calculator section", dependsOnMethods = {
			"quickOnboarding" }, priority = 15)
	public void verifyFincalsec() {

		logger.info(">>>>> verifying the investing with your HSA section");

		Assert.assertEquals(v2planningloc.planningIndicatorfincaldesc3().getText().trim(),
				PropsUtil.getDataPropertyValue("finv2.planning.financialcaldesc1"), "*** Description mismatch.");
		Assert.assertEquals(v2planningloc.planningIndicatorfincaldesc2().getText().trim(),
				PropsUtil.getDataPropertyValue("finv2.planning.financialcaldesc2"), "*** Description mismatch.");

	}

	@Test(description = "AT-95957:Verify the back link in Financial calculator page", dependsOnMethods = {
			"quickOnboarding" }, priority = 16)
	public void verifyFincalbacklink() {

		logger.info(">>>>> verifying the Backlink in the header");

		if (v2planningloc.isfinV2_mobile_planningind_backicon1()) {
			v2planningloc.mobileBackicon().isDisplayed();
		} else {

			Assert.assertTrue(v2planningloc.planningBacktofinchecklink().isDisplayed(), "*** Back Link not displayed.");
		}

	}

	@Test(description = "AT-95955:Verify the > link in calculation page", dependsOnMethods = {
			"quickOnboarding" }, priority = 17)
	public void verifyFincalarrowlink() {

		logger.info(">>>>> verifying the Backlink in the header");

		Assert.assertTrue(v2planningloc.planningIndicatorfincalarrowlink().isDisplayed(), "*** Arrow mismatch.");

	}

	@Test(description = "AT-95956:Verify the Ever FI open in new tab", dependsOnMethods = {
			"quickOnboarding" }, priority = 18)
	public void verifyFincalnavigation() {
		String handler = d.getWindowHandle();

		logger.info(">>>>> verifying the FinApp navigates in new tab");
		SeleniumUtil.click(v2planningloc.getCalculatorsList().get(0));
		SeleniumUtil.waitForPageToLoad();
		SeleniumUtil.click(v2planningloc.getEverFIContinueButton());
		SeleniumUtil.waitForPageToLoad(10000);
		d.switchTo().window(handler);
	}

	@Test(description = "AT-96415:Verify the Planning text in settings screen", dependsOnMethods = {
			"quickOnboarding" }, priority = 19)
	public void verifyPlanningtextinsettingsscreen() {
		logger.info(">>>>> verifying the Planning text in settings header");
		SeleniumUtil.click(v2planningloc.planningBacktofinchecklink());

		SeleniumUtil.click(v2planningloc.planningIndicatorscoresection1());

		Assert.assertEquals(v2planningloc.planningSettingsheader().getText().trim(),
				PropsUtil.getDataPropertyValue("finv2.planning.planningsettingsheader"), "*** Header mismatch.");
	}

	@Test(description = "AT-96416:Verify the Planning goals header text", dependsOnMethods = {
			"quickOnboarding" }, priority = 20)
	public void verifyPlanninghabitssec() {

		logger.info(">>>>> Verify the Planning goals header text");

		Assert.assertEquals(v2planningloc.planningSettingssubheader().getText().trim(),
				PropsUtil.getDataPropertyValue("finv2.planning.planninghabitsheader"), "*** Header mismatch.");
	}

	@Test(description = "AT-96417:Verify the Planning options dispayed in the settings screen", dependsOnMethods = {
			"quickOnboarding" }, priority = 21)
	public void verifyPlanningoptionssetscreen() {

		String planning[] = PropsUtil.getDataPropertyValue("finv2.planning.planningoptions").split(",");
		logger.info(">>>>> Verify the Planning options in settings screen");

		for (int i = 0; i < 5; i++) {

			Assert.assertEquals(v2planningloc.planningOptionssetscreen().get(i).getText().trim(), planning[i].trim(),
					"*** Planning option mismatch.");
		}
	}

	@Test(description = "AT-96418:Verify the user selected planning options FTUE selected by default in planning settings screen", dependsOnMethods = {
			"quickOnboarding" }, priority = 22)
	public void verifyPlanningoptionselected() {

		logger.info(">>>>> Verify the Planning option selected in the settings screen");

		String area = v2planningloc.planningOptionssetscreenrdobtn().get(4).getAttribute("aria-checked");
		String expected = "true";
		Assert.assertEquals(area, expected, "*** A mismatch.");

	}

	@Test(description = "AT-96419:Verify the user able to select the plannning options", dependsOnMethods = {
			"quickOnboarding" }, priority = 23)
	public void verifyPlanningoptionselection() {

		logger.info(">>>>>Verify the user able to select the plannning options");

		SeleniumUtil.click(v2planningloc.planningOptionssetscreen().get(1));

	}

	@Test(description = "AT-96420:Verify the planning goal section displayed", dependsOnMethods = {
			"quickOnboarding" }, priority = 24)
	public void verifyPlaningGoalSection() {

		logger.info(">>>>>Verify the user able to select the plannning options");

		SeleniumUtil.click(v2planningloc.planningOptionssetscreen().get(1));
		Assert.assertEquals(v2planningloc.planningSettingsgoalsheader().getText().trim(),
				PropsUtil.getDataPropertyValue("finv2.planning.planninggoalsec"), "*** Goals Text mismatch.");
	}

	@Test(description = "AT-96421:Verify the Planning goals options dispayed in the settings screen", dependsOnMethods = {
			"quickOnboarding" }, priority = 25)
	public void verifyPlanninGoalsScreen() {

		String planninggoals[] = PropsUtil.getDataPropertyValue("finv2.planning.planninggoaloptions").split("@");
		logger.info(">>>>> Verify the Planning goals options in settings screen");

		for (int i = 1; i < 10; i++) {

			Assert.assertEquals(v2planningloc.planningGoalsoptions().get(i).getText().trim(), planninggoals[i].trim(),
					"*** Description mismatch.");
		}
	}

	@Test(description = "AT-96422:Verify the user selected planning future goals options FTUE selected by default in planning settings screen", dependsOnMethods = {
			"quickOnboarding" }, priority = 26)
	public void verifyCheckBox() {

		logger.info(">>>>> Verify the Planning future goals option selected in the settings screen");

		String area = v2planningloc.planningGoalsoptionschkbox().get(1).getAttribute("aria-checked");
		String expected = "false";
		Assert.assertEquals(area, expected, "*** Check box checked.");

	}

	@Test(description = "AT-96423:Verify planning goals options should not reflect for the last 2 planning options", dependsOnMethods = {
			"quickOnboarding" }, priority = 27)
	public void verifyPlanningfutgoaloptionsnotdisplayed() {

		logger.info(">>>>> Verify the Planning future goals option selected in the settings screen");

		SeleniumUtil.click(v2planningloc.planningOptionssetscreen().get(3));
		Assert.assertEquals(v2planningloc.getAllRadioButton().get(3).getAttribute("aria-checked"), "true");

	}

	@Test(description = "AT-96424:Verify cancel and update btn displayed in the settings screen", dependsOnMethods = {
			"quickOnboarding" }, priority = 28)
	public void verifyCancelbtn() {

		logger.info(">>>>> Verify the cancel btn");

		Assert.assertTrue(v2planningloc.planningSettingcancelbtn().isDisplayed());

	}

	@Test(description = "AT-96424:Verify cancel and update btn displayed in the settings screen", dependsOnMethods = {
			"quickOnboarding" }, priority = 29)
	public void verifyUpdatebtn() {

		logger.info(">>>>> Verify the update btn");

		Assert.assertTrue(v2planningloc.planningSettingupdatebtn().isDisplayed(), "*** Update button nod displayed.");

	}

	@Test(description = "AT-96428:Verify back link displayed in settings screen", dependsOnMethods = {
			"quickOnboarding" }, priority = 30)
	public void verifyBacklinkinsettingsscreen() {
		logger.info(">>>>> Verify the backlink displyed in settings screen");

		if (v2planningloc.isfinV2_mobile_planningind_backicon2()) {
			v2planningloc.mobileBackicon2().isDisplayed();
		} else {

			Assert.assertTrue(v2planningloc.planningSettingsbacklink().isDisplayed(), "*** Back Link not displayed.");
		}
	}

	@Test(description = "AT-96427:Verify finapp naivgates back to indicatotr screen after click o back link", dependsOnMethods = {
			"quickOnboarding" }, priority = 31)
	public void verifyBacklinknavigation() {

		logger.info(">>>>> Verify the backlink navigation");

		if (v2planningloc.isfinV2_mobile_planningind_backicon2()) {
			SeleniumUtil.click(v2planningloc.mobileBackicon2());
		} else {
			SeleniumUtil.click(v2planningloc.planningSettingsbacklink());
			Assert.assertEquals(v2planningloc.planningIndicatorheadertext().getText().trim(),
					PropsUtil.getDataPropertyValue("finv2.planning.headertext"), "*** Header text mismatch.");
		}
	}

	@Test(description = "AT-96425:Verify finapp naivgates back to indicatotr screen after click o update btn", dependsOnMethods = {
			"quickOnboarding" }, priority = 32)
	public void verifyFinappnavigationafterclickonupdatebtn() {

		logger.info(">>>>> Verify the update btn navigation");
		SeleniumUtil.waitForPageToLoad();
		SeleniumUtil.click(v2planningloc.planningIndicatorscoresection1());
		SeleniumUtil.waitForPageToLoad();
		SeleniumUtil.click(v2planningloc.planningSettingupdatebtn(), "*** Description mismatch.");

		if (v2planningloc.isfinV2_mobile_creditind_closeicon_Present()) {
			v2planningloc.mobileCloseicon().isDisplayed();
		} else {

			Assert.assertEquals(v2planningloc.planningIndicatorheadertext().getText().trim(),
					PropsUtil.getDataPropertyValue("finv2.planning.headertext"), "*** Header mismatch.");
		}
	}

}
