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
public class FincheckV2_Spend_To_Income_Danger_Test extends TestBase {
	private static final Logger logger = LoggerFactory.getLogger(FincheckV2_Spend_To_Income_Danger_Test.class);
	FincheckV2_GetStarted_Loc v2GetStartd;
	AccountAddition accountAddition;
	FincheckV2_Spend_Less_Than_You_Earn_Loc stir;

	@BeforeClass(alwaysRun = true)
	public void doInit() throws Exception {
		doInitialization("Fincheck V2 Intialization");
		v2GetStartd = new FincheckV2_GetStarted_Loc(d);
		accountAddition = new AccountAddition();
		stir = new FincheckV2_Spend_Less_Than_You_Earn_Loc(d);

		LoginPage.loginMain(d, loginParameter);
		SeleniumUtil.waitForPageToLoad();
		accountAddition.linkAccount();
		accountAddition.addAggregatedAccount(PropsUtil.getDataPropertyValue("finV2.spend.danger.DagSite"),
				PropsUtil.getDataPropertyValue("finV2.spend.danger.DagPassword"));
		d.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
	}

	@Test(description = "Navigate to Indicator page after FTUE", priority = 1)
	public void navigateToIndicatorPage() {
		logger.info(">>>>> Completing FTUE and Onboarding");

		v2GetStartd.quickOnboarding(PropsUtil.getDataPropertyValue("finV2.birthYear.1990"),
				PropsUtil.getDataPropertyValue("finV2.income.15k"), 2, 4);
		PageParser.forceNavigate("FincheckV2", d);
		SeleniumUtil.waitForPageToLoad();
		SeleniumUtil.click(stir.getIndicatorFromDashboard());
		if(stir.ismobileCloseBtnPresent()) {
			SeleniumUtil.click(stir.mobileCloseBtn());
			SeleniumUtil.waitForPageToLoad();
			SeleniumUtil.click(stir.getIndicatorFromDashboard());
		}
	
		SeleniumUtil.waitForPageToLoad();
		
		if(appFlag.equalsIgnoreCase("web")||appFlag.equalsIgnoreCase("false")) 
		{
		Assert.assertEquals(stir.getIndicatorTitle().getText().trim(),
				PropsUtil.getDataPropertyValue("finV2.Indicator1.Titile"), "*** Header mismatch.");
	}
	}
		
	@Test(description = "AT-96355:Verify the score details text present", priority = 2, dependsOnMethods = "navigateToIndicatorPage")
	public void verifyScoreDetailsText() {
		logger.info(">>>>> Verifying score details text");
		Assert.assertEquals(stir.getScoreDetailsText().getText().trim(),
				PropsUtil.getDataPropertyValue("finV2.Indicator1.danger.scoreDetailsText"),
				"*** Score details mismatch.");
	}

	@Test(description = "AT-96356:Verify the back link is present on the indicator page", priority = 3, dependsOnMethods = "navigateToIndicatorPage")
	public void verifyBackLink() {
		logger.info(">>>>> Verifyng the back link");
		if(appFlag.equalsIgnoreCase("web")||appFlag.equalsIgnoreCase("false")) 
		{
		Assert.assertTrue(stir.getBackLink().isDisplayed(), "*** Back Link not present");
	}
	}
	@Test(description = "AT-96357:Verify the sugestion Text Present below the healthbar", priority = 4, dependsOnMethods = "navigateToIndicatorPage")
	public void verifySugestionText() {
		logger.info(">>>>> Verifying sugestion text");
		Assert.assertEquals(stir.getSugestionText().getText().trim(),
				PropsUtil.getDataPropertyValue("finV2.Indicator1.danger.sugestionText"),
				"*** Sugestion text mismatch.");
	}

	@Test(description = "AT-96358:Verify the health bar is displayed in indicator page", priority = 5, dependsOnMethods = "navigateToIndicatorPage")
	public void verifyHealthBar() {
		logger.info(">>>>> Verifying healthbar");
		Assert.assertTrue(stir.getHealthBar().isDisplayed(), "*** Health bar not displayed.");

	}

	@Test(description = "AT-96359:Verify the score details section header", priority = 7, dependsOnMethods = "navigateToIndicatorPage")
	public void verifyScoreDetailHeader() {
		logger.info(">>>>>Verifying the score details header is displayed");
		Assert.assertEquals(stir.getScoreDetailsHeader().getText().trim(),
				PropsUtil.getDataPropertyValue("finV2.Indicator.scoreDetailsHeader"), "*** Header mismatch.");
	}

	@Test(description = "AT-96360:Verify average Income in score details section", priority = 8, dependsOnMethods = "navigateToIndicatorPage")
	public void verifyAverageIncomeSection() {
		logger.info("Verifying Average Income Text and Value");
		Assert.assertEquals(stir.getAverageIncomeText().getText().trim(),
				PropsUtil.getDataPropertyValue("finV2.Indicator.averageIncomeText"), "*** Text mismatch.");
		Assert.assertEquals(stir.getAverageIncomeValue().getText().trim(),
				PropsUtil.getDataPropertyValue("finV2.Indicator1.danger.averageIncomeValue"), "*** Value mismatch.");

	}

	@Test(description = "AT-96361:Verify average soending in score details section", priority = 9, dependsOnMethods = "navigateToIndicatorPage")
	public void verifyAverageSpending() {
		logger.info("Verifying Average Income Text and Value");
		Assert.assertEquals(stir.getAverageSpendingText().getText().trim(),
				PropsUtil.getDataPropertyValue("finV2.Indicator.averageSpendingText"), "*** Spending text mismatch.");
		Assert.assertEquals(stir.getAverageSpendingValue().getText().trim(),
				PropsUtil.getDataPropertyValue("finV2.Indicator1.danger.averageSpendingValue"),
				"*** Spending value mismatch.");

	}

	@Test(description = "AT-96362:Verify About score section", priority = 10, dependsOnMethods = "navigateToIndicatorPage")
	public void verifyAboutScoreSection() {
		logger.info(">>>>> Verifying About score section");
		Assert.assertEquals(stir.getAboutTheScoreText().getText().trim(),
				PropsUtil.getDataPropertyValue("finV2.Indicator.aboutScoreText"), "*** About score text mismatch.");
	}

	@Test(description = "AT-96364:Verify the take action header present", priority = 12, dependsOnMethods = "navigateToIndicatorPage")
	public void getTakeActionHeader() {
		logger.info(">>>>> verifying the take action header");
		Assert.assertEquals(stir.getTakeActionsHeader().getText().trim(),
				PropsUtil.getDataPropertyValue("finV2.Indicator.takeActionHeaderText"), "*** Take action mismatch.");
	}

	@Test(description = "AT-96363:Verify the take action description", priority = 13, dependsOnMethods = "navigateToIndicatorPage")
	public void verifyTakeActionDescription() {
		logger.info(">>>>> Verifying the take action description for Indicator 1");
		Assert.assertEquals(stir.getTakeActionDescription().getText().trim(),
				PropsUtil.getDataPropertyValue("finV2.Indicator1.takeActionsDescription"), "*** Description mismatch.");
	}

	@Test(description = "AT-96365:Verify the action button is displayed", priority = 14, dependsOnMethods = "navigateToIndicatorPage")
	public void verifyActionButton() {
		logger.info(">>>>> Verifying Action button");
		
	
		Assert.assertTrue(stir.getActionButton().isDisplayed(), "*** Action button not displayed.");
	}
}

