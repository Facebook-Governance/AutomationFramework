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
import com.omni.pfm.pages.FincheckV2.FincheckV2_Retirement_Savings_Loc;
import com.omni.pfm.pages.FincheckV2.FincheckV2_Spend_Less_Than_You_Earn_Loc;
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
public class FincheckV2_RetirementSavings_Risky_Test extends TestBase {
	private static final Logger logger = LoggerFactory.getLogger(FincheckV2_RetirementSavings_Risky_Test.class);
	FincheckV2_GetStarted_Loc v2GetStartd;
	AccountAddition accountAddition;
	FincheckV2_Retirement_Savings_Loc ret;
	FincheckV2_Spend_Less_Than_You_Earn_Loc stir;


	@BeforeClass(alwaysRun = true)
	public void doInit() throws Exception {
		doInitialization("Fincheck V2 Intialization");
		v2GetStartd = new FincheckV2_GetStarted_Loc(d);
		accountAddition = new AccountAddition();
		ret = new FincheckV2_Retirement_Savings_Loc(d);
		stir = new FincheckV2_Spend_Less_Than_You_Earn_Loc(d);


		LoginPage.loginMain(d, loginParameter);

		accountAddition.linkAccount();
		accountAddition.addAggregatedAccount(PropsUtil.getDataPropertyValue("finV2.retire.danger.DagSite"),
				PropsUtil.getDataPropertyValue("finV2.retire.danger.DagPassword"));
		d.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
	}

	@Test(description = "Navigate to Indicator page after FTUE", priority = 1)
	public void navigateToIndicatorPage() {
		logger.info(">>>>> Completing FTUE and Onboarding");
		v2GetStartd.quickOnboarding(PropsUtil.getDataPropertyValue("finV2.birthYear1966"),
				PropsUtil.getDataPropertyValue("finV2.annualIncome49K"), 2, 4);
		PageParser.forceNavigate("FincheckV2", d);
		SeleniumUtil.waitForPageToLoad();
		SeleniumUtil.click(ret.getIndicatorFromDashboard());
		SeleniumUtil.waitForPageToLoad();
		if(appFlag.equalsIgnoreCase("web")||appFlag.equalsIgnoreCase("false")) 
		{
		Assert.assertEquals(ret.getIndicatorTitle().getText().trim(),
				PropsUtil.getDataPropertyValue("finV2.Indicator4.Titile"));
	}
	}
	@Test(description = "AT-96345:Verify the score details text present", priority = 2, dependsOnMethods = "navigateToIndicatorPage")
	public void verifyScoreDetailsText() {
		logger.info(">>>>> Verifying score details text");
		Assert.assertEquals(ret.getScoreDetailsText().getText().trim(),
				PropsUtil.getDataPropertyValue("finV2.Indicator4.scoreDetailsText"), "*** Header mismatch.");
	}

	@Test(description = "AT-96346:Verify the back link is present on the indicator page", priority = 3, dependsOnMethods = "navigateToIndicatorPage")
	public void verifyBackLink() {
		logger.info(">>>>> Verifyng the back link");
		if(appFlag.equalsIgnoreCase("web")||appFlag.equalsIgnoreCase("false")) {
		Assert.assertTrue(ret.getBackLink().isDisplayed(), "*** Back Link not present");
	}
	}
	@Test(description = "AT-96347:Verify the sugestion Text Present below the healthbar", priority = 4, dependsOnMethods = "navigateToIndicatorPage")
	public void verifySugestionText() {
		logger.info(">>>>> Verifying sugestion text");
		Assert.assertEquals(ret.getSugestionText().getText().trim(),
				PropsUtil.getDataPropertyValue("finV2.Indicator4.risky.sugestionText").trim(),
				"*** Sugestion text mismatch.");
	}

	@Test(description = "AT-96348:Verify the health bar is displayed in indicator page", priority = 5, dependsOnMethods = "navigateToIndicatorPage")
	public void verifyHealthBar() {
		logger.info(">>>>> Verifying healthbar");
		Assert.assertTrue(ret.getHealthBar().isDisplayed(), "*** Healthbar not displayed.");

	}

	@Test(description = "AT-96349:Verify the score details section header", priority = 7, dependsOnMethods = "navigateToIndicatorPage")
	public void verifyScoreDetailHeader() {
		logger.info(">>>>>Verifying the score details header is displayed");
		Assert.assertEquals(ret.getScoreDetailsHeader().getText().trim(),
				PropsUtil.getDataPropertyValue("finV2.Indicator.scoreDetailsHeader"), "*** Header mismatch.");
	}

	@Test(description = "AT-96350:Verify Annual Gross Income Sections", priority = 8, dependsOnMethods = "navigateToIndicatorPage")
	public void verifyAnualGrossIncome() {
		logger.info(">>>>>Verifying the score details header is displayed");
		Assert.assertEquals(ret.getAnnualGrossIncomeText().getText().trim(),
				PropsUtil.getDataPropertyValue("finV2.Indicator4.annualGrossIncomeText"), "*** income text mismatch.");
		Assert.assertEquals(ret.getAnnualGrossIncomeValue().getText().trim(),
				PropsUtil.getDataPropertyValue("finV2.Indicator4.risky.annualGrossIncomeValue"),
				"*** income value mismatch.");
	}

	@Test(description = "AT-96351:Verify Suggested Savings Sections", priority = 9, dependsOnMethods = "navigateToIndicatorPage")
	public void verifySuggestedSavings() {
		logger.info(">>>>>Verifying the score details header is displayed");
		Assert.assertEquals(ret.getSuggestedSavingsText().getText().trim(),
				PropsUtil.getDataPropertyValue("finV2.Indicator4.suggestedSavingsText"), "*** savings text mismatch.");
		Assert.assertEquals(ret.getSuggestedSavingsValue().getText().trim(),
				PropsUtil.getDataPropertyValue("finV2.Indicator4.risky.suggestedSavingsValue"),
				"*** savings value mismatch.");
	}

	@Test(description = "AT-96352:Verify About score section", priority = 11, dependsOnMethods = "navigateToIndicatorPage")
	public void verifyAboutScoreSection() {
		logger.info(">>>>> Verifying About score section");
		Assert.assertEquals(ret.getAboutTheScoreText().getText().trim(),
				PropsUtil.getDataPropertyValue("finV2.Indicator.aboutScoreText"), "*** About Score text mismatch.");
	}

	@Test(description = "AT-96353:Verify the take action header present", priority = 12, dependsOnMethods = "navigateToIndicatorPage")
	public void getTakeActionHeader() {
		logger.info(">>>>> verifying the take action header");
		Assert.assertEquals(ret.getTakeActionsHeader().getText().trim(),
				PropsUtil.getDataPropertyValue("finV2.Indicator.takeActionHeaderText"), "*** Header mismatch.");
	}

	@Test(description = "AT-96354:Verify the action button is displayed", priority = 14, dependsOnMethods = "navigateToIndicatorPage")
	public void verifyActionButton() {
		logger.info(">>>>> Verifying Action button");
		Assert.assertTrue(ret.getActionButton().isDisplayed(), "*** Action button not displayed.");
	}
}
