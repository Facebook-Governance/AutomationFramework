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
* This software is the confidential and proprietary information of
* Yodlee, Inc. Use is subject to license terms.
*
* @author sbhat1
*/
public class FincheckV2_PayBills_Healthy_Test extends TestBase {
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
		accountAddition.addAggregatedAccount1(PropsUtil.getDataPropertyValue("finV2.billPay.healthy.DagSite"),
				PropsUtil.getDataPropertyValue("finV2.billPay.healthy.DagPassword"));
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
		if(stir.ismobileCloseBtnPresent()) {
			SeleniumUtil.click(stir.mobileCloseBtn());
			SeleniumUtil.waitForPageToLoad();
			SeleniumUtil.click(stir.getIndicatorFromDashboard());
		}
		SeleniumUtil.waitForPageToLoad();
		if(appFlag.equalsIgnoreCase("web")||appFlag.equalsIgnoreCase("false")) 
		{
		Assert.assertEquals(pay.getIndicatorTitle().getText().trim(),
				PropsUtil.getDataPropertyValue("finV2.Indicator2.Titile"),"*** Header mismatch.");
	}
	}
	@Test(description = "AT-96862:Verify the score details text present", priority = 2, dependsOnMethods = "navigateToIndicatorPage")
	public void verifyScoreDetailsText() {
		logger.info(">>>>> Verifying score details text");
	
		Assert.assertEquals(pay.getScoreDetailsText().getText().trim(),
				PropsUtil.getDataPropertyValue("finV2.Indicator2.healthy.scoreDetailsText"),"*** Score details mismatch.");
	}
	@Test(description = "AT-96863:Verify the back link is present on the indicator page", priority = 3, dependsOnMethods = "navigateToIndicatorPage")
	public void verifyBackLink() {
		logger.info(">>>>> Verifyng the back link");
		if(appFlag.equalsIgnoreCase("web")||appFlag.equalsIgnoreCase("false")) 
		{
		Assert.assertTrue(pay.getBackLink().isDisplayed(), "***Back Link not present");
	}
	}
	@Test(description = "AT-96864:Verify the sugestion Text Present below the healthbar", priority = 4, dependsOnMethods = "navigateToIndicatorPage")
	public void verifySugestionText() {
		logger.info(">>>>> Verifying sugestion text");
		Assert.assertEquals(pay.getSugestionText().getText().trim(),
				PropsUtil.getDataPropertyValue("finV2.Indicator2.healthy.sugestionText").trim(),"*** Sugestion text mismatch.");
	}

	@Test(description = "AT-96865:Verify the health bar is displayed in indicator page", priority = 5, dependsOnMethods = "navigateToIndicatorPage")
	public void verifyHealthBar() {
		logger.info(">>>>> Verifying healthbar");
		Assert.assertTrue(pay.getHealthBar().isDisplayed(),"*** Health bar not displayed.");

	}

	@Test(description = "AT-96866:Verify the score details section header", priority = 7, dependsOnMethods = "navigateToIndicatorPage")
	public void verifyScoreDetailHeader() {
		logger.info(">>>>>Verifying the score details header is displayed");
		Assert.assertEquals(pay.getScoreDetailsHeader().getText().trim(),
				PropsUtil.getDataPropertyValue("finV2.Indicator.scoreDetailsHeader"),"*** Score details Header mismatch.");
	}

	@Test(description = "AT-96867:Verify the upcoming bills text", priority = 7, dependsOnMethods = "navigateToIndicatorPage")
	public void verifyUpcomingBillsText() {
		logger.info(">>>>>Verifying the score details header is displayed");
		Assert.assertEquals(pay.getUpcomingBillsText().getText().trim(),
				PropsUtil.getDataPropertyValue("finV2.Indicator2.upcomingBillsText"),"*** Bill text mismatch.");
	}

	@Test(description = "AT-96868:Verify About score section", priority = 11, dependsOnMethods = "navigateToIndicatorPage")
	public void verifyAboutScoreSection() {
		logger.info(">>>>> Verifying About score section");
		Assert.assertEquals(pay.getAboutTheScoreText().getText().trim(),
				PropsUtil.getDataPropertyValue("finV2.Indicator.aboutScoreText"),"*** About Score text mismatch.");
	}

	@Test(description = "AT-96869:Verify the take action header present", priority = 12, dependsOnMethods = "navigateToIndicatorPage")
	public void getTakeActionHeader() {
		logger.info(">>>>> verifying the take action header");
		Assert.assertEquals(pay.getTakeActionsHeader().getText().trim(),
				PropsUtil.getDataPropertyValue("finV2.Indicator.takeActionHeaderText"),"*** take action Header mismatch.");
	}

	@Test(description = "AT-96870:Verify the take action description", priority = 13, dependsOnMethods = "navigateToIndicatorPage")
	public void verifyTakeActionDescription() {
		logger.info(">>>>> Verifying the take action description for Indicator 1");
		Assert.assertEquals(pay.getTakeActionDescription().getText().trim(),
				PropsUtil.getDataPropertyValue("finV2.Indicator2.takeActionsDescription"),"*** Description mismatch.");
	}

	@Test(description = "Verify the action button is displayed", priority = 14, dependsOnMethods = "navigateToIndicatorPage")
	public void verifyActionButton() {
		logger.info(">>>>> Verifying Action button");
		Assert.assertTrue(pay.getActionButton().isDisplayed(),"*** Button not displayed.");
	}
}
