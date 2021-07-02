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
import com.omni.pfm.pages.FincheckV2.FincheckV2_Debt_To_Income_Loc;
import com.omni.pfm.pages.FincheckV2.FincheckV2_GetStarted_Loc;
import com.omni.pfm.pages.FincheckV2.FincheckV2_Spend_Less_Than_You_Earn_Loc;
import com.omni.pfm.pages.Login.LoginPage;
import com.omni.pfm.testBase.TestBase;
import com.omni.pfm.utility.PropsUtil;
import com.omni.pfm.utility.SeleniumUtil;

public class FincheckV2_DebtToIncome_Danger_Test extends TestBase {
	private static final Logger logger = LoggerFactory.getLogger(FincheckV2_DebtToIncome_Danger_Test.class);
	FincheckV2_GetStarted_Loc v2GetStartd;
	AccountAddition accountAddition;
	FincheckV2_Debt_To_Income_Loc deb;
	FincheckV2_Spend_Less_Than_You_Earn_Loc stir;


	@BeforeClass(alwaysRun = true)
	public void doInit() throws Exception {
		doInitialization("Fincheck V2 Intialization");
		v2GetStartd = new FincheckV2_GetStarted_Loc(d);
		accountAddition = new AccountAddition();
		deb = new FincheckV2_Debt_To_Income_Loc(d);
		stir = new FincheckV2_Spend_Less_Than_You_Earn_Loc(d);

		LoginPage.loginMain(d, loginParameter);

		accountAddition.linkAccount();

		accountAddition.addAggregatedAccount1(PropsUtil.getDataPropertyValue("finV2.debtToIncomeDagSite"),
				PropsUtil.getDataPropertyValue("finV2.debtToIncomeDagPassword"));

		d.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
	}

	@Test(description = "Navigate to Indicator page after FTUE", priority = 0)
	public void navigateToIndicatorPage() {
		logger.info(">>>>> Completing FTUE and Onboarding");
		v2GetStartd.quickOnboarding(PropsUtil.getDataPropertyValue("finV2.birthYear1980"),
				PropsUtil.getDataPropertyValue("finV2.annualIncome3K"), 2, 4);

		PageParser.forceNavigate("FincheckV2", d);
		SeleniumUtil.waitForPageToLoad();
		SeleniumUtil.click(deb.getIndicatorFromDashboard());
		if(stir.ismobileCloseBtnPresent()) {
			SeleniumUtil.click(stir.mobileCloseBtn());
			SeleniumUtil.waitForPageToLoad();
			SeleniumUtil.click(stir.getIndicatorFromDashboard());
		}
		SeleniumUtil.waitForPageToLoad();
		if(appFlag.equalsIgnoreCase("web")||appFlag.equalsIgnoreCase("false")) 
		{
		Assert.assertEquals(deb.getIndicatorTitle().getText().trim(),
				PropsUtil.getDataPropertyValue("finV2.Indicator5.Titile"), "***** Indicator title did not match");
	}
	}

	@Test(description = "AT-95192:Verify the score details text present", priority = 2, dependsOnMethods = "navigateToIndicatorPage")
	public void verifyScoreDetailsText() {
		logger.info(">>>>> Verifying score details text");
		Assert.assertEquals(deb.getScoreDetailsText().getText().trim(),
				PropsUtil.getDataPropertyValue("finV2.Indicator5.danger.scoreDetailsText"),
				"***** Score details text mismatched.");
	}

	@Test(description = "AT-95193:Verify the back link is present on the indicator page", priority = 3, dependsOnMethods = "navigateToIndicatorPage")
	public void verifyBackLink() {
		logger.info(">>>>> Verifyng the back link");
		if(appFlag.equalsIgnoreCase("web")||appFlag.equalsIgnoreCase("false")) 
		{
		Assert.assertTrue(deb.getBackLink().isDisplayed(), "*****Back Link not present");
	}
	}
	@Test(description = "AT-95194:Verify the sugestion Text Present below the healthbar", priority = 4, dependsOnMethods = "navigateToIndicatorPage")
	public void verifySugestionText() {
		logger.info(">>>>> Verifying sugestion text");
		Assert.assertEquals(deb.getSugestionText().getText().trim(),
				PropsUtil.getDataPropertyValue("finV2.Indicator5.risky.sugestionText").trim(),
				"***** Sugestion text is not matching.");
	}

	@Test(description = "AT-95301:Verify the health bar is displayed in indicator page", priority = 5, dependsOnMethods = "navigateToIndicatorPage")
	public void verifyHealthBar() {
		logger.info(">>>>> Verifying healthbar");
		Assert.assertTrue(deb.getHealthBar().isDisplayed(), "***** Health bar is not displayed.");

	}

	@Test(description = "AT-95302:Verify the score details section header", priority = 7, dependsOnMethods = "navigateToIndicatorPage")
	public void verifyScoreDetailHeader() {
		logger.info(">>>>>Verifying the score details header is displayed");
		Assert.assertEquals(deb.getScoreDetailsHeader().getText().trim(),
				PropsUtil.getDataPropertyValue("finV2.Indicator.scoreDetailsHeader"),
				"***** Score details header text is not matching.");
	}

	@Test(description = "AT-95303:Verify Monthly Gross Income Sections", priority = 8, dependsOnMethods = "navigateToIndicatorPage")
	public void verifyAnualGrossIncome() {
		logger.info(">>>>>Verifying Total Monthly Debt Sections");
		Assert.assertEquals(deb.getMonthlyIncomeText().getText().trim(),
				PropsUtil.getDataPropertyValue("finV2.Indicator5.monthlyIncomeText"));
		Assert.assertEquals(deb.getMonthlyIncomeValue().getText().trim(),
				PropsUtil.getDataPropertyValue("finV2.Indicator5.danger.monthlyIncomeValue"),
				"***** Monthly Income value is not matching");
	}

	@Test(description = "AT-96889:Verify Suggested Savings Sections", priority = 9, dependsOnMethods = "navigateToIndicatorPage")
	public void verifySuggestedSavings() {
		logger.info(">>>>>Verifyinf the total montly debt sections");
		Assert.assertEquals(deb.getTotalMonthlyDebtValue().getText().trim(),
				PropsUtil.getDataPropertyValue("finV2.Indicator5.danger.monthlyDebtValue"),
				"***** Monthly debt value is  not matching");
		Assert.assertEquals(deb.getTotalMontlyDebtText().getText().trim(),
				PropsUtil.getDataPropertyValue("finV2.Indocator5.monthlyDebtText"), "***** Monthly debt text mismatch");
	}

	@Test(description = "AT-96890:Verify About score section", priority = 11, dependsOnMethods = "navigateToIndicatorPage")
	public void verifyAboutScoreSection() {
		logger.info(">>>>> Verifying About score section");
		Assert.assertEquals(deb.getAboutTheScoreText().getText().trim(),
				PropsUtil.getDataPropertyValue("finV2.Indicator.aboutScoreText"), "***** Bout Score text mismatch.");
	}

	@Test(description = "AT-96891:Verify the take action header present", priority = 12, dependsOnMethods = "navigateToIndicatorPage")
	public void getTakeActionHeader() {
		logger.info(">>>>> verifying the take action header");
		Assert.assertEquals(deb.getTakeActionsHeader().getText().trim(),
				PropsUtil.getDataPropertyValue("finV2.Indicator.takeActionHeaderText"),
				"***** Take actions heade is not matching");
	}

	@Test(description = "AT-96892Verify the action button is displayed", priority = 14, dependsOnMethods = "navigateToIndicatorPage")
	public void verifyActionButton() {
		logger.info(">>>>> Verifying Action button");
		Assert.assertTrue(deb.getActionButton().isDisplayed(), "***** Action button not present");
	}
}
