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
import com.omni.pfm.utility.FincheckUtil;
import com.omni.pfm.utility.PropsUtil;
import com.omni.pfm.utility.SeleniumUtil;

public class FincheckV2_DebtToIncome_Healthy_Test extends TestBase {
//demo
	private static final Logger logger = LoggerFactory.getLogger(FincheckV2_DebtToIncome_Healthy_Test.class);
	FincheckV2_GetStarted_Loc v2GetStartd;
	AccountAddition accountAddition;
	FincheckV2_Debt_To_Income_Loc deb;
	FincheckV2_Spend_Less_Than_You_Earn_Loc stir;

	FincheckUtil util;

	@BeforeClass(alwaysRun = true)
	public void doInit() throws Exception {
		doInitialization("Fincheck V2 Intialization");
		v2GetStartd = new FincheckV2_GetStarted_Loc(d);
		accountAddition = new AccountAddition();
		deb = new FincheckV2_Debt_To_Income_Loc(d);
		stir = new FincheckV2_Spend_Less_Than_You_Earn_Loc(d);

		util = new FincheckUtil(d);

		LoginPage.loginMain(d, loginParameter);

		accountAddition.linkAccount();
		accountAddition.addAggregatedAccount1(PropsUtil.getDataPropertyValue("finV2.debtToIncomeDagSite"),
				PropsUtil.getDataPropertyValue("finV2.debtToIncomeDagPassword"));
		d.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
	}

	@Test(description = "AT-95222:Navigate to Indicator page after FTUE", priority = 1)
	public void navigateToIndicatorPage() {
		logger.info(">>>>> Completing FTUE and Onboarding");
		v2GetStartd.quickOnboarding(PropsUtil.getDataPropertyValue("finV2.birthYear1980"),
				PropsUtil.getDataPropertyValue("finV2.annualIncome49K"), 2, 4);

		PageParser.forceNavigate("FincheckV2", d);
		SeleniumUtil.waitForPageToLoad();
		SeleniumUtil.click(deb.getIndicatorFromDashboard());
		if (stir.ismobileCloseBtnPresent()) {
			SeleniumUtil.click(stir.mobileCloseBtn());
			SeleniumUtil.waitForPageToLoad();
			SeleniumUtil.click(stir.getIndicatorFromDashboard());
		}
		SeleniumUtil.waitForPageToLoad();
		if (appFlag.equalsIgnoreCase("web") || appFlag.equalsIgnoreCase("false")) {
			Assert.assertEquals(deb.getIndicatorTitle().getText().trim(),
					PropsUtil.getDataPropertyValue("finV2.Indicator5.Titile"));
		}
	}

	@Test(description = "AT-95237:Verify the score details text present", priority = 2, dependsOnMethods = "navigateToIndicatorPage")
	public void verifyScoreDetailsText() {
		logger.info(">>>>> Verifying score details text");
		Assert.assertEquals(deb.getScoreDetailsText().getText().trim(),
				PropsUtil.getDataPropertyValue("finV2.Indicator5.healthy.scoreDetailsText"),
				"***** Score details text mismatch.");
	}

	@Test(description = "AT-95228:Verify the back link is present on the indicator page", priority = 3, dependsOnMethods = "navigateToIndicatorPage")
	public void verifyBackLink() {
		logger.info(">>>>> Verifyng the back link");
		if (appFlag.equalsIgnoreCase("web") || appFlag.equalsIgnoreCase("false")) {
			Assert.assertTrue(deb.getBackLink().isDisplayed(), "***** Back Link not present");
		}
	}

	@Test(description = "AT-95229:Verify the sugestion Text Present below the healthbar", priority = 4, dependsOnMethods = "navigateToIndicatorPage")
	public void verifySugestionText() {
		logger.info(">>>>> Verifying sugestion text");
		Assert.assertEquals(deb.getSugestionText().getText().trim(),
				PropsUtil.getDataPropertyValue("finV2.Indicator5.healthy.sugestionText").trim(),
				"*** Healthy Sugestion text mismatch.");
	}

	@Test(description = "AT-95230:Verify the health bar is displayed in indicator page", priority = 5, dependsOnMethods = "navigateToIndicatorPage")
	public void verifyHealthBar() {
		logger.info(">>>>> Verifying healthbar");
		Assert.assertTrue(deb.getHealthBar().isDisplayed(), "*** Health Bar not displayed");

	}

	@Test(description = "AT-95231:Verify the score details section header", priority = 7, dependsOnMethods = "navigateToIndicatorPage")
	public void verifyScoreDetailHeader() {
		logger.info(">>>>>Verifying the score details header is displayed");
		Assert.assertEquals(deb.getScoreDetailsHeader().getText().trim(),
				PropsUtil.getDataPropertyValue("finV2.Indicator.scoreDetailsHeader"),
				"*** Score details header text mismatch.");
	}

	@Test(description = "AT-95232:Verify Monthly Gross Income Sections", priority = 8, dependsOnMethods = "navigateToIndicatorPage")
	public void verifyAnualGrossIncome() {
		logger.info(">>>>>Verifying Total Monthly Debt Sections");
		Assert.assertEquals(deb.getMonthlyIncomeText().getText().trim(),
				PropsUtil.getDataPropertyValue("finV2.Indicator5.monthlyIncomeText"),
				"*** Monthly income text mismatch.");
		Assert.assertEquals(deb.getMonthlyIncomeValue().getText().trim(),
				PropsUtil.getDataPropertyValue("finV2.Indicator5.healthy.monthlyIncomeValue"),
				"*** Monthly Income value mismatch.");
	}

	@Test(description = "AT-95233:Verify Suggested Savings Sections", priority = 9, dependsOnMethods = "navigateToIndicatorPage")
	public void verifySuggestedSavings() {
		logger.info(">>>>>Verifyinf the total montly debt sections");
		Assert.assertEquals(deb.getTotalMonthlyDebtValue().getText().trim(),
				PropsUtil.getDataPropertyValue("finV2.Indicator5.healthy.monthlyDebtValue"),
				"*** Monthly debt value mismatch.");
		Assert.assertEquals(deb.getTotalMontlyDebtText().getText().trim(),
				PropsUtil.getDataPropertyValue("finV2.Indocator5.monthlyDebtText"), "*** Monthly debt text mismatch");
	}

	@Test(description = "AT-95234:Verify About score section", priority = 11, dependsOnMethods = "navigateToIndicatorPage")
	public void verifyAboutScoreSection() {
		logger.info(">>>>> Verifying About score section");
		Assert.assertEquals(deb.getAboutTheScoreText().getText().trim(),
				PropsUtil.getDataPropertyValue("finV2.Indicator.aboutScoreText"), "*** About score text mismatch.");
	}

	@Test(description = "AT-95235:Verify the take action header present", priority = 12, dependsOnMethods = "navigateToIndicatorPage")
	public void getTakeActionHeader() {
		logger.info(">>>>> verifying the take action header");
		Assert.assertEquals(deb.getTakeActionsHeader().getText().trim(),
				PropsUtil.getDataPropertyValue("finV2.Indicator.takeActionHeaderText"),
				"*** Take action header text mismatch.");
	}

	@Test(description = "AT-95242:Verify the action button is displayed", priority = 14, dependsOnMethods = "navigateToIndicatorPage")
	public void verifyActionButton() {
		logger.info(">>>>> Verifying Action button");
		Assert.assertTrue(deb.getActionButton().isDisplayed(), "*** Action button not displayed.");
	}

	@Test(description = "AT-95243:Verify the click on Monthly Gross Income will navigate to the annual household edit page", priority = 15, dependsOnMethods = "navigateToIndicatorPage")
	public void verifyMonthlyIncomeClick() {
		logger.info(">>>> Clicking on Monthly Gross Income");
		SeleniumUtil.click(deb.getMonthlyIncomeText());
		SeleniumUtil.waitForPageToLoad(3000);

		Assert.assertEquals(deb.getAnnualIncomeAppTitle().getText(),
				PropsUtil.getDataPropertyValue("finV2.Indicator5.annualGrossIncomePageHeader"),
				"*** Annual Gross Income header not matched.");

	}

	@Test(description = "AT-95244:Verify the personal Information Description", priority = 16, dependsOnMethods = "verifyMonthlyIncomeClick")
	public void verifyThePersonalInformationDescription() {
		logger.info(">>>>> Verifying the description");
		Assert.assertEquals(deb.getPersonalInfoDesc().getText().trim(),
				PropsUtil.getDataPropertyValue("finV2.Indicator5.personalInfoDescription"),
				"*** Personal Info text mismatch.");
	}

	@Test(description = "AT-95249:Verify the description is present for the annual income info", priority = 17, dependsOnMethods = "verifyMonthlyIncomeClick")
	public void verifyAnnualIncomeBreakup() {
		logger.info(">>>>> verify the annual income breakup");
		Assert.assertEquals(deb.getAnnualIncomeBreakup().getText().trim(),
				PropsUtil.getDataPropertyValue("finV2.Indictor5.healthy.annualIncomeBreakupText"),
				"*** Annual Income breakup text mismatch.");
	}

	@Test(description = "AT-95250:Verify the cancle button is displayed", priority = 18, dependsOnMethods = "verifyMonthlyIncomeClick")
	public void verifyCancleButton() {
		logger.info(">>>> Verifying the cancle button");
		Assert.assertTrue(deb.getCancleButton().isDisplayed(), "*** Cancle button is not displayed.");
	}

	@Test(description = "AT-95251:Verify the update button is displayed", priority = 19, dependsOnMethods = "verifyMonthlyIncomeClick")
	public void verifyUpdateButton() {
		logger.info(">>>> Verifying the cancle button");
		Assert.assertTrue(deb.getUpdateButton().isDisplayed(), "*** Update button not displayed.");
	}

	@Test(description = "AT-95150:Verify the changing value in annual income will change the breakup", priority = 20, dependsOnMethods = "verifyMonthlyIncomeClick")
	public void verifyBreakupChange() {
		logger.info(">>>>> Verify the change in breakup value");
		SeleniumUtil.click(deb.getAnnualIncomeTextbox());
		deb.getAnnualIncomeTextbox().clear();
		SeleniumUtil.sendKeys(deb.getAnnualIncomeTextbox(), PropsUtil.getDataPropertyValue("finV2.annualIncome.6k"));

		SeleniumUtil.click(deb.getPersonalInfoDesc());

		Assert.assertEquals(deb.getAnnualIncomeBreakup().getText().trim(),
				PropsUtil.getDataPropertyValue("finV2.Indicator5.risky.annualIncomeBreakupText"),
				"*** Annual Income breakup text mismatch.");

	}

	@Test(description = "AT-95151:Verify the cancle button click will exit from the page and changes wont be saved", priority = 21, dependsOnMethods = "verifyMonthlyIncomeClick")
	public void verifyCancelButtonAction() {
		logger.info(">>>>> Clicking the cancle button");
		SeleniumUtil.click(deb.getCancleButton());

		Assert.assertEquals(deb.getScoreDetailsText().getText().trim(),
				PropsUtil.getDataPropertyValue("finV2.Indicator5.healthy.scoreDetailsText"),
				"*** Score details text mismatch.");

	}

	@Test(description = "AT-95152:Verify clicking on Total Monthly Debt will navigate to the Current Liabilities page", priority = 22, dependsOnMethods = "verifyCancelButtonAction")
	public void verifyTotalDebtAction() {
		logger.info(">>>> Clicking on total monthly Debt link");
		SeleniumUtil.click(deb.getTotalMontlyDebtText());
		SeleniumUtil.waitForPageToLoad(3000);

		Assert.assertEquals(deb.getMonthlyLiabilitiesPageHeader().getText(),
				PropsUtil.getDataPropertyValue("finV2.Indicator5.currentLiabilitiesPageHeader"),
				"*** Monthly Liabilities page header mismatch.");
	}

	@Test(description = "AT-95153:Verify the back link is displayed", priority = 23, dependsOnMethods = "verifyTotalDebtAction")
	public void verifyBackToIndicatorPageLink() {
		logger.info(">>>> Verifying Back link");

		if (appFlag.equalsIgnoreCase("web") || appFlag.equalsIgnoreCase("false")) {
			Assert.assertTrue(deb.getBackLinkFromMorePage().isDisplayed(), "*** Back Link to Fincheck not displayed.");
		}
	}

	@Test(description = "AT-95154:Verify about score link is displayed", priority = 24, dependsOnMethods = "verifyTotalDebtAction")
	public void verifyAboutScore() {
		logger.info(">>>>> Verify the about score link");
		Assert.assertTrue(deb.getAboutScoreLink().isDisplayed(), "**** About Score Link is not displayed");

	}

	@Test(description = "AT-95155:Verify the text present as liabilities discription", priority = 25, dependsOnMethods = "verifyTotalDebtAction")
	public void verifyLiabilitiesDescText() {
		logger.info(">>>>> Verifying the liabilities description text");
		Assert.assertEquals(deb.getLiabilitiesDescription().getText().trim(),
				PropsUtil.getDataPropertyValue("finV2.Indicator5.liabilitiesDescriptionText"),
				"*** Liabilities description mismatch.");
	}

	@Test(description = "AT-95156:Verify add liability btton", priority = 26, dependsOnMethods = "verifyTotalDebtAction")
	public void verifyAddLiabilityButton() {
		logger.info(">>>>> Verifying add liability button");
		Assert.assertTrue(deb.getAddLiabilityButton().isDisplayed(), "**** Add Liability button is not displayed");
	}

	@Test(description = "AT-95157:Verify User can add a liability", priority = 27, dependsOnMethods = "verifyTotalDebtAction", enabled = false)
	public void verifyAddLiability() {
		logger.info(">>>> Adding a liability");
		SeleniumUtil.click(deb.getAddLiabilityButton());
		SeleniumUtil.waitForPageToLoad(2000);
		util.addLiability(Integer.parseInt(PropsUtil.getDataPropertyValue("finV2.liabilityValue.1k")),
				PropsUtil.getDataPropertyValue("finV2.liabilityText.TestData"));

	}

	@Test(description = "AT-95160:Verify 2 bills available are disabled", priority = 28, dependsOnMethods = "verifyTotalDebtAction")
	public void verifyDisabledBills() {
		logger.info(">>>>> verify the bills are disabled");
		Assert.assertEquals(deb.getDisabledBillsList().size(), 2, "*** Bills mismatched.");

	}

	@Test(description = "AT-95158:Verify Back button will navigate back to the indicator page", priority = 29, dependsOnMethods = "verifyTotalDebtAction")
	public void verifyBackAction() {
		logger.info(">>>> Navigating back to Indicator page");
		SeleniumUtil.click(deb.getBackLinkFromMorePage());

		SeleniumUtil.waitForPageToLoad(3000);
		Assert.assertEquals(deb.getScoreDetailsText().getText().trim(),
				PropsUtil.getDataPropertyValue("finV2.Indicator5.healthy.scoreDetailsText"),
				"*** Score details text mismatch.");
	}
}
