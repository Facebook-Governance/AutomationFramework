package com.omni.pfm.FinCheckV2;

/**
* Copyright (c) 2018 Yodlee Inc. All Rights Reserved. 
*
* This software is the confidential and proprietary information of
* Yodlee, Inc. Use is subject to license terms.
*
* @author sbhat1
*/
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.omni.pfm.PageProcessor.PageParser;
import com.omni.pfm.pages.AccountAddition.AccountAddition;
import com.omni.pfm.pages.FincheckV2.FincheckV2_Emergency_Savings_Loc;
import com.omni.pfm.pages.FincheckV2.FincheckV2_GetStarted_Loc;
import com.omni.pfm.pages.FincheckV2.FincheckV2_Spend_Less_Than_You_Earn_Loc;
import com.omni.pfm.pages.Login.LoginPage;
import com.omni.pfm.testBase.TestBase;
import com.omni.pfm.utility.PropsUtil;
import com.omni.pfm.utility.SeleniumUtil;

public class FincheckV2_EmergencySavings_Healthy_Test extends TestBase {
	private static final Logger logger = LoggerFactory.getLogger(FincheckV2_EmergencySavings_Healthy_Test.class);
	FincheckV2_GetStarted_Loc v2GetStartd;
	AccountAddition accountAddition;
	FincheckV2_Emergency_Savings_Loc emergency;
	FincheckV2_Spend_Less_Than_You_Earn_Loc stir;

	@BeforeClass(alwaysRun = true)
	public void doInit() throws Exception {
		doInitialization("Fincheck V2 Intialization");
		v2GetStartd = new FincheckV2_GetStarted_Loc(d);
		accountAddition = new AccountAddition();
		emergency = new FincheckV2_Emergency_Savings_Loc(d);
		stir = new FincheckV2_Spend_Less_Than_You_Earn_Loc(d);
		LoginPage.loginMain(d, loginParameter);

		accountAddition.linkAccount();
		accountAddition.addAggregatedAccount1(PropsUtil.getDataPropertyValue("finV2.emergencySavingsHealthyDagsite"),
				PropsUtil.getDataPropertyValue("finV2.emergencySavingsHealthyDagPassword"));
		d.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
	}

	@Test(description = "Navigate to Indicator page after FTUE", priority = 1)
	public void navigateToIndicatorPage() {
		logger.info(">>>>> Completing FTUE and Onboarding");
		v2GetStartd.quickOnboarding(PropsUtil.getDataPropertyValue("finV2.birthYear.1990"),
				PropsUtil.getDataPropertyValue("finV2.income.15k"), 2, 4);

		PageParser.forceNavigate("FincheckV2", d);
		SeleniumUtil.waitForPageToLoad();
		SeleniumUtil.click(emergency.getIndicatorFromDashboard());
		if(stir.ismobileCloseBtnPresent()) {
			SeleniumUtil.click(stir.mobileCloseBtn());
			SeleniumUtil.waitForPageToLoad();
			SeleniumUtil.click(stir.getIndicatorFromDashboard());
		}
		SeleniumUtil.waitForPageToLoad();
		if(appFlag.equalsIgnoreCase("web")||appFlag.equalsIgnoreCase("false")) 
		{
		Assert.assertEquals(emergency.getIndicatorTitle().getText().trim(),
				PropsUtil.getDataPropertyValue("finV2.Indicator3.Titile"), "*** Indicator title mismatch.");
	}
	}
	@Test(description = "AT-96891:Verify the score details text present", priority = 2, dependsOnMethods = "navigateToIndicatorPage")
	public void verifyScoreDetailsText() {
		logger.info(">>>>> Verifying score details text");
	
		Assert.assertEquals(emergency.getScoreDetailsText().getText().trim(),
				PropsUtil.getDataPropertyValue("finV2.Indicator3.healthy.scoreDetailsText"),
				"Score details text mismatch.");
	}
	
	@Test(description = "AT-96892:Verify the back link is present on the indicator page", priority = 3, dependsOnMethods = "navigateToIndicatorPage")
	public void verifyBackLink() {
		logger.info(">>>>> Verifyng the back link");
		if(appFlag.equalsIgnoreCase("web")||appFlag.equalsIgnoreCase("false")) 
		{
		Assert.assertTrue(emergency.getBackLink().isDisplayed(), "*** Back Link not present");
	}
	}
	@Test(description = "AT-96893:Verify the sugestion Text Present below the healthbar", priority = 4, dependsOnMethods = "navigateToIndicatorPage")
	public void verifySugestionText() {
		logger.info(">>>>> Verifying sugestion text");
		Assert.assertEquals(emergency.getSugestionText().getText().trim(),
				PropsUtil.getDataPropertyValue("finV2.Indicator3.healthy.sugestionText").trim(),
				"Sugestion text mismatch.");
	}

	@Test(description = "AT-96895:Verify the health bar is displayed in indicator page", priority = 5, dependsOnMethods = "navigateToIndicatorPage")
	public void verifyHealthBar() {
		logger.info(">>>>> Verifying healthbar");
		Assert.assertTrue(emergency.getHealthBar().isDisplayed(), "*** Health bar not displayed.");

	}

	@Test(description = "AT-96894:Verify the score details section header", priority = 7, dependsOnMethods = "navigateToIndicatorPage")
	public void verifyScoreDetailHeader() {
		logger.info(">>>>>Verifying the score details header is displayed");
		Assert.assertEquals(emergency.getScoreDetailsHeader().getText().trim(),
				PropsUtil.getDataPropertyValue("finV2.Indicator.scoreDetailsHeader"),
				"*** Score details header mismatch.");
	}

	@Test(description = "AT-96896:Verify average spending in score details section", priority = 9, dependsOnMethods = "navigateToIndicatorPage")
	public void verifyAverageSpending() {
		logger.info("Verifying Average Income Text and Value");
		Assert.assertEquals(emergency.getAverageMonthlySpendingText().getText().trim(),
				PropsUtil.getDataPropertyValue("finV2.Indicator3.averageSpendingText"),
				"Avreage spending text mismatch.");
		Assert.assertEquals(emergency.getAverageMonthlySpendingValue().getText().trim(),
				PropsUtil.getDataPropertyValue("finV2.Indicator3.healthy.averageSpendingValue"),
				"*** Average spending value mismatch.");

	}

	@Test(description = "AT-96897:Verify current Coverage in score details section", priority = 10, dependsOnMethods = "navigateToIndicatorPage")
	public void verifyCurrentCovergae() {
		logger.info("Verifying Average Income Text and Value");
		Assert.assertEquals(emergency.getCurrentCoverageText().getText().trim(),
				PropsUtil.getDataPropertyValue("finV2.Indicator3.currentCoverageText"),
				"*** Current Coverage text mismatch.");
		Assert.assertEquals(emergency.getCurrentCoverageValue().getText().trim(),
				PropsUtil.getDataPropertyValue("finV2.Indicator3.healthy.currentCoverageValue"),
				"*** Current coverage vale mismatch");

	}

	@Test(description = "AT-96898:Verify About score section", priority = 11, dependsOnMethods = "navigateToIndicatorPage")
	public void verifyAboutScoreSection() {
		logger.info(">>>>> Verifying About score section");
		Assert.assertEquals(emergency.getAboutTheScoreText().getText().trim(),
				PropsUtil.getDataPropertyValue("finV2.Indicator.aboutScoreText"), "**** About score text mismatch.");
	}

	@Test(description = "AT-96899:Verify the take action header present", priority = 12, dependsOnMethods = "navigateToIndicatorPage")
	public void getTakeActionHeader() {
		logger.info(">>>>> verifying the take action header");
		Assert.assertEquals(emergency.getTakeActionsHeader().getText().trim(),
				PropsUtil.getDataPropertyValue("finV2.Indicator.takeActionHeaderText"),
				"*** take action text is not displayed.");
	}

	@Test(description = "Verify the take action description", priority = 13, dependsOnMethods = "navigateToIndicatorPage")
	public void verifyTakeActionDescription() {
		logger.info(">>>>> Verifying the take action description for Indicator 1");
		Assert.assertEquals(emergency.getTakeActionDescription().getText().trim(),
				PropsUtil.getDataPropertyValue("finV2.Indicator3.takeActionsDescription"),
				"*** Take action description not displayed.");
	}

	@Test(description = "Verify the action button is displayed", priority = 14, dependsOnMethods = "navigateToIndicatorPage")
	public void verifyActionButton() {
		logger.info(">>>>> Verifying Action button");
		Assert.assertTrue(emergency.getActionButton().isDisplayed(), "*** Action button not displayed.");
	}

}
