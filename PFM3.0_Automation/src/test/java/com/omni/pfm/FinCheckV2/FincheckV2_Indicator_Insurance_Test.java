package com.omni.pfm.FinCheckV2;

import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.omni.pfm.PageProcessor.PageParser;
import com.omni.pfm.pages.AccountAddition.AccountAddition;
import com.omni.pfm.pages.FincheckV2.FincheckV2_GetStarted_Loc;
import com.omni.pfm.pages.FincheckV2.FincheckV2_Insurance_Loc;
import com.omni.pfm.pages.Login.LoginPage;
import com.omni.pfm.testBase.TestBase;
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
public class FincheckV2_Indicator_Insurance_Test extends TestBase {

	private static final Logger logger = LoggerFactory.getLogger(FincheckV2_Indicator_Insurance_Test.class);
	FincheckV2_Insurance_Loc v2InsuranceScore;
	AccountAddition accountAddition;
	FincheckV2_GetStarted_Loc onboard;
	String platformName = null;

	@BeforeClass(alwaysRun = true)
	public void doInit() throws Exception {
		doInitialization("Fincheck V2 Intialization");
		v2InsuranceScore = new FincheckV2_Insurance_Loc(d);
		accountAddition = new AccountAddition();
		onboard = new FincheckV2_GetStarted_Loc(d);

		LoginPage.loginMain(d, loginParameter);
		SeleniumUtil.waitForPageToLoad();
		accountAddition.linkAccount();
		accountAddition.addAggregatedAccount1(PropsUtil.getDataPropertyValue("finV2.creditScore.DagSite"),
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
		SeleniumUtil.click(v2InsuranceScore.insuranceIndicatoroverview());
		if (v2InsuranceScore.isfinV2_mobile_creditind_closeicon_Present()) {
			SeleniumUtil.click(v2InsuranceScore.mobileCloseicon());

			SeleniumUtil.waitForPageToLoad();
			SeleniumUtil.click(v2InsuranceScore.insuranceIndicatoroverview());
		}
	}

	@Test(description = "AT-95150::Verifying the TakeAction section  Insurance indicator", dependsOnMethods = {
			"quickOnboarding" }, priority = 2)
	public void takeActionsection() {

		logger.info(">>>>> verifying Take Action description");
		Assert.assertEquals(v2InsuranceScore.getTakeActionHeader().getText().trim(),
				PropsUtil.getDataPropertyValue("finv2.insurance.takeactionheader"), "*** Header mismatch.");

	}

	@Test(description = "AT-95176:Verifying the TakeAction section description in Insurance indicator", dependsOnMethods = {
			"quickOnboarding" }, priority = 3)
	public void takeActiondesc1() {

		logger.info(">>>>> verifying Take Action description");
		Assert.assertEquals(v2InsuranceScore.getTakeActiondesc1().getText().trim(),
				PropsUtil.getDataPropertyValue("finv2.insurance.takeactiondesc1"), "*** Description mismatch.");

	}

	@Test(description = "AT-95176:Verifying the TakeAction section description in Insurance indicator", dependsOnMethods = {
			"quickOnboarding" }, priority = 4)
	public void takeActiondesc2() {

		logger.info(">>>>> verifying Take Action description");
		Assert.assertEquals(v2InsuranceScore.getTakeActiondesc2().getText().trim(),
				PropsUtil.getDataPropertyValue("finv2.insurance.takeactiondesc2"), "*** Description mismatch.");

	}

	@Test(description = "AT-95176:Verifying the TakeAction section description in Insurance indicator", dependsOnMethods = {
			"quickOnboarding" }, priority = 5)
	public void takeActiondesc3() {

		logger.info(">>>>> verifying Take Action description");
		Assert.assertEquals(v2InsuranceScore.getTakeActiondesc3().getText().trim(),
				PropsUtil.getDataPropertyValue("finv2.insurance.takeactiondesc3"), "*** Description mismatch.");

	}

	@Test(description = "AT-95053:Verifying the status text if insurance is not covered", dependsOnMethods = {
			"quickOnboarding" }, priority = 6)
	public void statusScore() {

		logger.info(">>>>> verifying score details");
		Assert.assertEquals(v2InsuranceScore.insuranceScoredetails().getText().trim(),
				PropsUtil.getDataPropertyValue("finv2.insurance.scoretext").trim(), "*** Score Text mismatch.");

	}

	@Test(description = "AT-95053:Verifying the status text if insurance is not covered", dependsOnMethods = {
			"quickOnboarding" }, priority = 7)
	public void statusBartext() {

		logger.info(">>>>> verifying status bar text");
		Assert.assertEquals(v2InsuranceScore.insuranceStatusbartext().getText().trim(),
				PropsUtil.getDataPropertyValue("finv2.insurance.statusbartext"), "*** Status Text mismatch.");

	}

	@Test(description = "AT-95898:Verifying the score details section in insurance inidcator", dependsOnMethods = {
			"quickOnboarding" }, priority = 8)
	public void scoreDetailssec() {

		logger.info(">>>>> verifying the score details section in insurance inidcator");
		Assert.assertEquals(v2InsuranceScore.insuranceScoredetailssec().getText().trim(),
				PropsUtil.getDataPropertyValue("finv2.insurance.scoredetailsheader"), "*** Header mismatch.");

	}

	@Test(description = "AT-95919:Verify the text and section\"Suggested insurance plans\" , \"Updateyour insurance info\" and \"Why insurance is important\"displayed in the indicator 7 score details section", dependsOnMethods = {
			"quickOnboarding" }, priority = 9)
	public void scoreDetailssectext() {

		logger.info(">>>>> verifying the sections in score details section");
		Assert.assertEquals(v2InsuranceScore.insuranceScoredetailssec1().getText().trim(),
				PropsUtil.getDataPropertyValue("finv2.insurance.scoredetailssec1"), "*** Description mismatch.");

		Assert.assertEquals(v2InsuranceScore.insuranceScoredetailssec2().getText().trim(),
				PropsUtil.getDataPropertyValue("finv2.insurance.scoredetailssec2"), "*** Description mismatch.");

		Assert.assertEquals(v2InsuranceScore.insuranceScoredetailssec3().getText().trim(),
				PropsUtil.getDataPropertyValue("finv2.insurance.scoredetailssec3"), "*** Description mismatch.");

	}

	@Test(description = "AT-95920:Verify the count displayed in suggested insurance plans section if user has not selected the insurance", dependsOnMethods = {
			"quickOnboarding" }, priority = 10)
	public void suggestedInsurancecount() {

		logger.info(">>>>> verifying the suggested insurance count");

		Assert.assertEquals(v2InsuranceScore.insuranceSuggestedcount().getText().trim(),
				PropsUtil.getDataPropertyValue("finv2.insurance.suggestedinsurancecount"),
				"*** Suggested Count mismatch.");

	}

	@Test(description = "AT-96389:Verify the insurance coverage screen displayed", dependsOnMethods = {
			"quickOnboarding" }, priority = 11)
	public void insuranceCoveragescreen() {

		logger.info(">>>>> verifying the insurance coverage screen");
		SeleniumUtil.click(v2InsuranceScore.insuranceScoredetailssec1());
		SeleniumUtil.waitForPageToLoad();
		Assert.assertEquals(v2InsuranceScore.insuranceCoveragescreen().getText().trim(),
				PropsUtil.getDataPropertyValue("finv2.insurance.insurancecoveragescreen"),
				"*** Coverage Screen Text mismatch.");

	}

	@Test(description = "AT-96390:Verify the insurance coverage column header Type and Coverage", dependsOnMethods = {
			"quickOnboarding" }, priority = 12)
	public void insuranceCoveragecolumns() {

		logger.info(">>>>> verifying the insurance coverage column header");
		Assert.assertEquals(v2InsuranceScore.insuranceCoveragescreentype().getText().trim(),
				PropsUtil.getDataPropertyValue("finv2.insurance.insurancecoveragescreenType"),
				"*** Coverage mismatch.");
		Assert.assertEquals(v2InsuranceScore.insuranceCoveragescreencoverage().getText().trim(),
				PropsUtil.getDataPropertyValue("finv2.insurance.insurancecoveragescreencoverage"),
				"*** Coverage mismatch.");

	}

	@Test(description = "AT-96391:Verify the Life insurance Description in coverage screen", dependsOnMethods = {
			"quickOnboarding" }, priority = 13)
	public void lifeInsurancedetails() {
		logger.info(">>>>> Verify the Life insurance Description in coverage screen ");
		Assert.assertEquals(v2InsuranceScore.insuranceDetailsheader().get(0).getText().trim().replaceAll("\n", " "),
				PropsUtil.getDataPropertyValue("finv2.insurance.lifeinsurancetext"),
				"*** Life Insurance Text mismatch.");

	}

	@Test(description = "AT-96392:Verify the Health insurance Description in coverage screen", dependsOnMethods = {
			"quickOnboarding" }, priority = 14)
	public void healthInsurancedetails() {

		logger.info(">>>>> Verify the Health insurance Description in coverage screen ");
		Assert.assertEquals(v2InsuranceScore.insuranceDetailsheader().get(1).getText().trim().replaceAll("\n", " "),
				PropsUtil.getDataPropertyValue("finv2.insurance.healthinsurancetext"), "*** Header mismatch.");

	}

	@Test(description = "AT-96393:Verify the Diaability insurance Description in coverage screen", dependsOnMethods = {
			"quickOnboarding" }, priority = 15)
	public void disabilityInsurancedetails() {

		logger.info(">>>>> Verify the Disability insurance Description in coverage screen ");
		Assert.assertEquals(v2InsuranceScore.insuranceDetailsheader().get(2).getText().trim().replaceAll("\n", " "),
				PropsUtil.getDataPropertyValue("finv2.insurance.disabilithyinsurancetext"),
				"*** Insurance Text mismatch.");

	}

	@Test(description = "AT-96395:Verify the auto insurance Description in coverage screen", dependsOnMethods = {
			"quickOnboarding" }, priority = 16)
	public void autoInsurancedetails() {

		logger.info(">>>>> Verify the auto insurance Description in coverage screen ");
		Assert.assertEquals(v2InsuranceScore.insuranceDetailsheader().get(3).getText().trim().replaceAll("\n", " "),
				PropsUtil.getDataPropertyValue("finv2.insurance.autoinsurancetext"), "*** Auto Insurance mismatch.");

	}

	@Test(description = "AT-96396:Verify the Home insurance Description in coverage screen", dependsOnMethods = {
			"quickOnboarding" }, priority = 17)
	public void homeInsurancedetails() {

		logger.info(">>>>> Verify the home insurance Description in coverage screen ");
		Assert.assertEquals(v2InsuranceScore.insuranceDetailsheader().get(4).getText().trim().replaceAll("\n", " "),
				PropsUtil.getDataPropertyValue("finv2.insurance.homeinsurancetext"), "*** Home Insurance mismatch.");

	}

	@Test(description = "AT-96394:Verify the renters insurance Description in coverage screen", dependsOnMethods = {
			"quickOnboarding" }, priority = 18)
	public void rentersInsurancedetails() {

		logger.info(">>>>> Verify the renters insurance Description in coverage screen ");
		Assert.assertEquals(v2InsuranceScore.insuranceDetailsheader().get(5).getText().trim().replaceAll("\n", " "),
				PropsUtil.getDataPropertyValue("finv2.insurance.rentersinsurancetext"),
				"*** Renters insurance mismatch.");

	}

	@Test(description = "AT-96398:Verify the Cross icon in coverage screen", dependsOnMethods = {
			"quickOnboarding" }, priority = 19)
	public void insruranceCrossicon() {

		logger.info(">>>>> Verify the Cross icon in coverage screen");
		Assert.assertTrue(v2InsuranceScore.insuranceCrossicon().get(1).isDisplayed(),
				"*** Insurance Cross icon mismatch.");
	}

	@Test(description = "AT-96399:Verify the NA icon in coverage screen", dependsOnMethods = {
			"quickOnboarding" }, priority = 20)
	public void insruranceNotapplicableicon() {

		logger.info(">>>>> Verify the NA icon in coverage screen");
		Assert.assertTrue(v2InsuranceScore.insuranceNaicon().get(1).isDisplayed(), "*** NA Icon not displayed.");
	}

	@Test(description = "AT-96400:Verify update insurance button", dependsOnMethods = {
			"quickOnboarding" }, priority = 21)
	public void updateInsuranceinfobtn() {

		logger.info(">>>>> Verify update insurance button");
		Assert.assertTrue(v2InsuranceScore.updateInsurancebutton().isDisplayed(), "*** Update button not displayed");
	}

	@Test(description = "AT-96401 :Verify insurance information screen", dependsOnMethods = {
			"quickOnboarding" }, priority = 22)
	public void insuranceInformationscreen() {

		logger.info(">>>>> Verify Insurance information screen");
		SeleniumUtil.click(v2InsuranceScore.updateInsurancebutton());
		Assert.assertEquals(v2InsuranceScore.insuranceInfo().getText().trim(),
				PropsUtil.getDataPropertyValue("finv2.insurance.insuranceinfotext"),
				"*** Insurance Info text mismatch.");
	}

	@Test(description = " AT-96402 :Verify insurance household status screen", dependsOnMethods = {
			"quickOnboarding" }, priority = 23)
	public void insuranceHouseholdstatussection() {

		logger.info(">>>>> Verify Insurance information screen");

		Assert.assertEquals(v2InsuranceScore.insuranceHouseholdsection().getText().trim(),
				PropsUtil.getDataPropertyValue("finv2.insurance.insurancehouseholdtext"),
				"*** Insurance Household text mismatch.");
	}

	@Test(description = "AT-96403 : Verify the household status details/info", dependsOnMethods = {
			"quickOnboarding" }, priority = 24)
	public void insuranceHouseholdstatusinfo() {

		String householdstatus[] = PropsUtil.getDataPropertyValue("finv2.insurance.insurancehouseholdinfo").split(",");
		for (int i = 1; i < 6; i++) {
			logger.info(">>>>> verifying The household status details ");
			Assert.assertEquals(v2InsuranceScore.insuranceHouseholdvalues().get(i).getText().trim(),
					householdstatus[i].trim(), "*** Insurance household value mismatch.");
		}

	}

	@Test(description = "AT-96404 : Verify the Insurance options", dependsOnMethods = {
			"quickOnboarding" }, priority = 25)
	public void insuranceOptions() {

		String insuranceoptions[] = PropsUtil.getDataPropertyValue("finv2.insurance.insuranceoptions").split(",");
		for (int i = 1; i < 6; i++) {
			logger.info(">>>>> verifying The Insurance options");
			Assert.assertEquals(v2InsuranceScore.insuranceOptions().get(i).getText().trim(), insuranceoptions[i].trim(),
					"*** Insurance Options mismatch.");

		}

	}

	@Test(description = "AT-96405 : Verify the user selected options in FTUE displayed in the insurance screen", dependsOnMethods = {
			"quickOnboarding" }, priority = 26)
	public void savedInsuranceoptions() {

		logger.info(">>>>> verifying insurance options in insurance screen");
		String aria = v2InsuranceScore.insuranceOptionschkbox().getAttribute("aria-checked");
		String ariaExpected = "false";
		Assert.assertEquals(aria, ariaExpected, "*** Checkbox mismatch.");

	}

	@Test(description = "AT-96406 :Verify based on selection in Household Status Section options in the Insurance Section should get Enabled accordingly", dependsOnMethods = {
			"quickOnboarding" }, priority = 27)
	public void insuranceOptionsenabled() {

		logger.info(">>>>> verifying insurance options enabled based on the household status");
		SeleniumUtil.click(v2InsuranceScore.insuranceHouseholdvalues().get(5));

		String aria = v2InsuranceScore.homeownerInsuranceoptionschkbox().getAttribute("aria-checked");
		String ariaExpected = "false";
		Assert.assertEquals(aria, ariaExpected, "*** Checkbox mismatch.");

	}

	@Test(description = " AT-96407 :Verify user can able to enable/disable the toggle buttons", dependsOnMethods = {
			"quickOnboarding" }, priority = 28)
	public void householdTogglebtnenableanddisable() {

		logger.info(">>>>> Verify user can able to enable/disable the toggle buttons");
		SeleniumUtil.click(v2InsuranceScore.houseHoldtogglebuttons().get(1));
		String aria = v2InsuranceScore.houseHoldtogglebuttons().get(1).getAttribute("aria-checked");
		String ariaExpected = "true";
		Assert.assertEquals(aria, ariaExpected, "*** Toggle Button mismatch.");
	}

	@Test(description = "AT-96408 : Verify user can able to check/uncheck the insurance options", dependsOnMethods = {
			"quickOnboarding" }, priority = 29)
	public void checkUchkinsuranceoptions() {

		logger.info(">>>>> Verify user can able to check/uncheck the insurance options");
		SeleniumUtil.click(v2InsuranceScore.insuranceOptionschkbox());
		String aria = v2InsuranceScore.insuranceOptionschkbox().getAttribute("aria-checked");
		String ariaExpected = "true";
		Assert.assertEquals(aria, ariaExpected, "***Chechbox mismatch.");

	}

	@Test(description = "AT-96409 : Verify cancel button displayed in the insurance screen", dependsOnMethods = {
			"quickOnboarding" }, priority = 30)
	public void insuranceCancelbtn() {

		logger.info(">>>>> Verify cancel button displayed in the insurance screen");
		Assert.assertTrue(v2InsuranceScore.insuranceCancelbtn().isDisplayed(), "*** Insurance button not displayed.");

	}

	@Test(description = "AT-96409 : Verify update button displayed in the insurance screen", dependsOnMethods = {
			"quickOnboarding" }, priority = 31)
	public void insuranceUpdatebtn() {

		logger.info(">>>>> Verify update button displayed in the insurance screen");
		Assert.assertTrue(v2InsuranceScore.insuranceUpdatebtn().isDisplayed(), "*** Update Button not displayed.");

	}

	@Test(description = "AT-96411 : Verify Clicking cancel or update button user should navigate to the Indicator Page", dependsOnMethods = {
			"quickOnboarding" }, priority = 32)
	public void verifyNavigationToIndicatorScreen() {

		logger.info(">>>>> Verify Clicking cancel or update button user should navigate to the Indicator Page");
		SeleniumUtil.click(v2InsuranceScore.insuranceUpdatebtn());
		SeleniumUtil.waitForPageToLoad();
		Assert.assertEquals(v2InsuranceScore.insuranceCoveragescreen().getText().trim(),
				PropsUtil.getDataPropertyValue("finv2.insurance.insurancecoveragescreen"),
				"*** Insurance Coverage screen mismatch.");

	}

	@Test(description = " AT-96413 : Verify back link is displayed", dependsOnMethods = {
			"quickOnboarding" }, priority = 33)
	public void backLinkinsurancescreen() {

		logger.info(">>>>> Verify screen navigates back to ind screen ");
		Assert.assertTrue(v2InsuranceScore.insuranceCoveragescreen().isDisplayed(),
				"*** Insurance Coverage screen mismatch.");

	}

	@Test(description = " AT-96414 : Verify screen navigates back to ind screen", dependsOnMethods = {
			"quickOnboarding" }, priority = 34)
	public void navigateBacktoindscreen() {

		logger.info(">>>>> Verify screen navigates back to ind screen ");

		if (v2InsuranceScore.isfinV2_mobile_creditind_backicon()) {
			SeleniumUtil.click(v2InsuranceScore.mobileBackicon());
		} else {

			SeleniumUtil.click(v2InsuranceScore.backLink());
			Assert.assertEquals(v2InsuranceScore.navigateBacktoindscreen().getText().trim(),
					PropsUtil.getDataPropertyValue("finv2.insurance.insuranceheadertext"),
					"*** Insurance header text mismatch.");

		}
	}

}
