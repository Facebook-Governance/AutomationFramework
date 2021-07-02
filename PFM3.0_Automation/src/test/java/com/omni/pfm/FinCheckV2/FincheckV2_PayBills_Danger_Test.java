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
import com.omni.pfm.pages.FincheckV2.FincheckV2_Pay_Bills_On_Time_Loc;
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
public class FincheckV2_PayBills_Danger_Test extends TestBase {
	private static final Logger logger = LoggerFactory.getLogger(FincheckV2_PayBills_Danger_Test.class);
	FincheckV2_GetStarted_Loc v2GetStartd;
	AccountAddition accountAddition;
	FincheckV2_Pay_Bills_On_Time_Loc pay;
	FincheckV2_Spend_Less_Than_You_Earn_Loc stir;


	@BeforeClass(alwaysRun = true)
	public void doInit() throws Exception {
		doInitialization("Fincheck V2 Intialization");
		v2GetStartd = new FincheckV2_GetStarted_Loc(d);
		accountAddition = new AccountAddition();
		pay = new FincheckV2_Pay_Bills_On_Time_Loc(d);
		stir = new FincheckV2_Spend_Less_Than_You_Earn_Loc(d);

		LoginPage.loginMain(d, loginParameter);

		accountAddition.linkAccount();
		accountAddition.addAggregatedAccount1(PropsUtil.getDataPropertyValue("finV2.billPay.danger.DagSite"),
				PropsUtil.getDataPropertyValue("finV2.billPay.danger.DagPassword"));
		d.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
	}

	@Test(description = "Navigate to Indicator page after FTUE", priority = 1)
	public void navigateToIndicatorPage() {
		logger.info(">>>>> Completing FTUE and Onboarding");
		v2GetStartd.quickOnboarding(PropsUtil.getDataPropertyValue("finV2.birthYear.1990"),
				PropsUtil.getDataPropertyValue("finV2.income.15k"), 2, 4);
		PageParser.forceNavigate("FincheckV2", d);
		SeleniumUtil.waitForPageToLoad();
		SeleniumUtil.click(pay.getIndicatorFromDashboard());
		SeleniumUtil.waitForPageToLoad();
		if(stir.ismobileCloseBtnPresent()) {
			SeleniumUtil.click(stir.mobileCloseBtn());
			SeleniumUtil.waitForPageToLoad();
			SeleniumUtil.click(stir.getIndicatorFromDashboard());
		}
		if(appFlag.equalsIgnoreCase("web")||appFlag.equalsIgnoreCase("false")) 
		{
		Assert.assertEquals(pay.getIndicatorTitle().getText().trim(),
				PropsUtil.getDataPropertyValue("finV2.Indicator2.Titile"), "*** Header mismatch.");
	}
	}
	@Test(description = "AT-96854:Verify the score details text present", priority = 2, dependsOnMethods = "navigateToIndicatorPage")
	public void verifyScoreDetailsText() {
		logger.info(">>>>> Verifying score details text");
	
		Assert.assertEquals(pay.getScoreDetailsText().getText().trim(),
				PropsUtil.getDataPropertyValue("finV2.Indicator2.danger.scoreDetailsText"), "*** Details mismatch.");
	}
	@Test(description = "Verify the back link is present on the indicator page", priority = 3, dependsOnMethods = "navigateToIndicatorPage")
	public void verifyBackLink() {
		logger.info(">>>>> Verifyng the back link");
		if(appFlag.equalsIgnoreCase("web")||appFlag.equalsIgnoreCase("false")) 
		{
		Assert.assertTrue(pay.getBackLink().isDisplayed(), "*** Back Link not present");
	}
	}
	@Test(description = "AT-96855:Verify the sugestion Text Present below the healthbar", priority = 4, dependsOnMethods = "navigateToIndicatorPage")
	public void verifySugestionText() {
		logger.info(">>>>> Verifying sugestion text");
		Assert.assertEquals(pay.getSugestionText().getText().trim(),
				PropsUtil.getDataPropertyValue("finV2.Indicator2.danger.sugestionText").trim(),
				"*** Sugestion text mismatch.");
	}

	@Test(description = "AT-96856:Verify the health bar is displayed in indicator page", priority = 5, dependsOnMethods = "navigateToIndicatorPage")
	public void verifyHealthBar() {
		logger.info(">>>>> Verifying healthbar");
		Assert.assertTrue(pay.getHealthBar().isDisplayed(), "*** Healthbar not displayed.");

	}

	@Test(description = "Verify the score details section header", priority = 7, dependsOnMethods = "navigateToIndicatorPage")
	public void verifyScoreDetailHeader() {
		logger.info(">>>>>Verifying the score details header is displayed");
		Assert.assertEquals(pay.getScoreDetailsHeader().getText().trim(),
				PropsUtil.getDataPropertyValue("finV2.Indicator.scoreDetailsHeader"), "*** Header mismatch.");

	}

	@Test(description = "AT-96857:Verify Overdue Bills", priority = 8, dependsOnMethods = "navigateToIndicatorPage")
	public void verifyUpcomingBillsText() {
		logger.info(">>>>>Verifying the score details header is displayed");
		Assert.assertEquals(pay.getOverdueBillsText().getText().trim(),
				PropsUtil.getDataPropertyValue("finV2.Indicator2.overdueBillText").trim(), "*** Bill text mismatch.");
		Assert.assertEquals(pay.getOverdueBillsValue().getText().trim(),
				PropsUtil.getDataPropertyValue("finV2.Indicator2.danger.overdueBillValue"), "*** Bill value mismatch.");
	}

	@Test(description = "AT-96858:Verify About score section", priority = 9, dependsOnMethods = "navigateToIndicatorPage")
	public void verifyAboutScoreSection() {
		logger.info(">>>>> Verifying About score section");
		Assert.assertEquals(pay.getAboutTheScoreText().getText().trim(),
				PropsUtil.getDataPropertyValue("finV2.Indicator.aboutScoreText"), "*** About Score Text mismatch.");
	}

	@Test(description = "AT-96859:Verify the take action header present", priority = 10, dependsOnMethods = "navigateToIndicatorPage")
	public void getTakeActionHeader() {
		logger.info(">>>>> verifying the take action header");
		Assert.assertEquals(pay.getTakeActionsHeader().getText().trim(),
				PropsUtil.getDataPropertyValue("finV2.Indicator.takeActionHeaderText"),
				"*** Take Action text mismatch.");
	}

	@Test(description = "AT-96860:Verify the take action description", priority = 11, dependsOnMethods = "navigateToIndicatorPage")
	public void verifyTakeActionDescription() {
		logger.info(">>>>> Verifying the take action description for Indicator 1");
		Assert.assertEquals(pay.getTakeActionDescription().getText().trim(),
				PropsUtil.getDataPropertyValue("finV2.Indicator2.takeActionsDescription"), "*** Description mismatch.");
	}

	@Test(description = "AT-96861:Verify the action button is displayed", priority = 12, dependsOnMethods = "navigateToIndicatorPage")
	public void verifyActionButton() {
		logger.info(">>>>> Verifying Action button");
		Assert.assertTrue(pay.getActionButton().isDisplayed(), "*** Action button not.");
	}
}
