package com.omni.pfm.FinCheckV2;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebElement;
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
public class FincheckV2_RetirementSavings_Healthy_Test extends TestBase {
	private static final Logger logger = LoggerFactory.getLogger(FincheckV2_RetirementSavings_Healthy_Test.class);
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
		SeleniumUtil.waitForPageToLoad();
		accountAddition.linkAccount();
		accountAddition.addAggregatedAccount1(PropsUtil.getDataPropertyValue("finV2.retire.danger.DagSite"),
				PropsUtil.getDataPropertyValue("finV2.retire.danger.DagPassword"));
		d.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
	}

	@Test(description = "Navigate to Indicator page after FTUE", priority = 1)
	public void navigateToIndicatorPage() {
		logger.info(">>>>> Completing FTUE and Onboarding");
		v2GetStartd.quickOnboarding(PropsUtil.getDataPropertyValue("finV2.birthYear1980"),
				PropsUtil.getDataPropertyValue("finV2.annualIncome49K"), 2, 4);
		PageParser.forceNavigate("FincheckV2", d);
		SeleniumUtil.waitForPageToLoad();
		SeleniumUtil.click(ret.getIndicatorFromDashboard());
		if(stir.ismobileCloseBtnPresent()) {
			SeleniumUtil.click(stir.mobileCloseBtn());
			SeleniumUtil.waitForPageToLoad();
			SeleniumUtil.click(stir.getIndicatorFromDashboard());
		}
		SeleniumUtil.waitForPageToLoad();
		SeleniumUtil.waitForPageToLoad(2500);
		if(appFlag.equalsIgnoreCase("web")||appFlag.equalsIgnoreCase("false")) 
		{
		Assert.assertEquals(ret.getIndicatorTitle().getText().trim(),
				PropsUtil.getDataPropertyValue("finV2.Indicator4.Titile"),"*** Header mismatch.");
	}
	}
	@Test(description = "AT-96318:Verify the score details text present", priority = 2, dependsOnMethods = "navigateToIndicatorPage")
	public void verifyScoreDetailsText() {
		logger.info(">>>>> Verifying score details text");
		Assert.assertEquals(ret.getScoreDetailsText().getText().trim(),
				PropsUtil.getDataPropertyValue("finV2.Indicator4.scoreDetailsText"),"*** Score text mismatch.");
	}

	@Test(description = "AT-96319:Verify the back link is present on the indicator page", priority = 3, dependsOnMethods = "navigateToIndicatorPage")
	public void verifyBackLink() {
		logger.info(">>>>> Verifyng the back link");
		if(appFlag.equalsIgnoreCase("web")||appFlag.equalsIgnoreCase("false")) 
		{
		Assert.assertTrue(ret.getBackLink().isDisplayed(), "*** Back Link not present");
	}
	}
	@Test(description = "AT-96320Verify the sugestion Text Present below the healthbar", priority = 4, dependsOnMethods = "navigateToIndicatorPage")
	public void verifySugestionText() {
		logger.info(">>>>> Verifying sugestion text");
		Assert.assertEquals(ret.getSugestionText().getText().trim(),
				PropsUtil.getDataPropertyValue("finV2.Indicator4.healthy.sugestionText").trim(),"*** Sugestion text mismatch.");
	}

	@Test(description = "AT-96321:Verify the health bar is displayed in indicator page", priority = 5, dependsOnMethods = "navigateToIndicatorPage")
	public void verifyHealthBar() {
		logger.info(">>>>> Verifying healthbar");
		Assert.assertTrue(ret.getHealthBar().isDisplayed(),"*** Healthbar not displayed.");

	}

	@Test(description = "AT-96322:Verify the score details section header", priority = 7, dependsOnMethods = "navigateToIndicatorPage")
	public void verifyScoreDetailHeader() {
		logger.info(">>>>>Verifying the score details header is displayed");
		Assert.assertEquals(ret.getScoreDetailsHeader().getText().trim(),
				PropsUtil.getDataPropertyValue("finV2.Indicator.scoreDetailsHeader"),"*** Header mismatch.");
	}

	@Test(description = "AT-96323:Verify Annual Gross Income Sections", priority = 8, dependsOnMethods = "navigateToIndicatorPage")
	public void verifyAnualGrossIncome() {
		logger.info(">>>>>Verifying the score details header is displayed");
		Assert.assertEquals(ret.getAnnualGrossIncomeText().getText().trim(),
				PropsUtil.getDataPropertyValue("finV2.Indicator4.annualGrossIncomeText"),"*** Annual Gross income text mismatch.");
		Assert.assertEquals(ret.getAnnualGrossIncomeValue().getText().trim(),
				PropsUtil.getDataPropertyValue("finV2.Indicator4.healthy.annualGrossIncomeValue"),"*** Annual income value mismatch.");
	}

	@Test(description = "AT-96324:Verify Suggested Savings Sections", priority = 9, dependsOnMethods = "navigateToIndicatorPage")
	public void verifySuggestedSavings() {
		logger.info(">>>>>Verifying the score details header is displayed");
		Assert.assertEquals(ret.getSuggestedSavingsText().getText().trim(),
				PropsUtil.getDataPropertyValue("finV2.Indicator4.suggestedSavingsText"),"*** Sugested savings text mismatch.");
		Assert.assertEquals(ret.getSuggestedSavingsValue().getText().trim(),
				PropsUtil.getDataPropertyValue("finV2.Indicator4.healthy.suggestedSavingsValue"),"*** Suggested savings value mismatch.");
	}

	@Test(description = "AT-96325:erify About score section", priority = 11, dependsOnMethods = "navigateToIndicatorPage")
	public void verifyAboutScoreSection() {
		logger.info(">>>>> Verifying About score section");
		Assert.assertEquals(ret.getAboutTheScoreText().getText().trim(),
				PropsUtil.getDataPropertyValue("finV2.Indicator.aboutScoreText"),"*** About score text mismatch.");
	}

	@Test(description = "AT-96326:Verify the take action header present", priority = 12, dependsOnMethods = "navigateToIndicatorPage")
	public void getTakeActionHeader() {
		logger.info(">>>>> verifying the take action header");
		Assert.assertEquals(ret.getTakeActionsHeader().getText().trim(),
				PropsUtil.getDataPropertyValue("finV2.Indicator.takeActionHeaderText"),"*** Take action text mismatch.");
	}

	@Test(description = "AT-96327:Verify the action button is displayed", priority = 14, dependsOnMethods = "navigateToIndicatorPage")
	public void verifyActionButton() {
		logger.info(">>>>> Verifying Action button");
		Assert.assertTrue(ret.getActionButton().isDisplayed(),"*** action button not displayed.");
	}

	@Test(description = "AT-96328:Verify the click on Monthly Gross Income will navigate to the annual household edit page", priority = 15, dependsOnMethods = "navigateToIndicatorPage")
	public void verifyMonthlyIncomeClick() {
		logger.info(">>>> Clicking on Monthly Gross Income");
		SeleniumUtil.click(ret.getAnnualGrossIncomeText());
		SeleniumUtil.waitForPageToLoad(3000);

		Assert.assertEquals(ret.getAnnualIncomeAppTitle().getText(),
				PropsUtil.getDataPropertyValue("finV2.Indicator5.annualGrossIncomePageHeader"),"*** Page Header mismatch.");

	}

	@Test(description = "AT-96329:Verify the personal Information Description", priority = 16, dependsOnMethods = "verifyMonthlyIncomeClick")
	public void verifyThePersonalInformationDescription() {
		logger.info(">>>>> Verifying the description");
		Assert.assertEquals(ret.getPersonalInfoDesc().getText().trim(),
				PropsUtil.getDataPropertyValue("finV2.Indicator5.personalInfoDescription"),"*** Personal Info Header mismatch.");
	}

	@Test(description = "AT-9630:Verify the description is present for the annual income info", priority = 17, dependsOnMethods = "verifyMonthlyIncomeClick")
	public void verifyAnnualIncomeBreakup() {
		logger.info(">>>>> verify the annual income breakup");
		Assert.assertEquals(ret.getAnnualIncomeBreakup().getText().trim(),
				PropsUtil.getDataPropertyValue("finV2.Indictor5.healthy.annualIncomeBreakupText"),"*** Annual Income Breakup text mismatch.");
	}

	@Test(description = "AT-96331:Verify the cancle button is displayed", priority = 18, dependsOnMethods = "verifyMonthlyIncomeClick")
	public void verifyCancleButton() {
		logger.info(">>>> Verifying the cancle button");
		Assert.assertTrue(ret.getCancleButton().isDisplayed(),"*** Cancle button not dislayed.");
	}

	@Test(description = "AT-96332:Verify the update button is displayed", priority = 19, dependsOnMethods = "verifyMonthlyIncomeClick")
	public void verifyUpdateButton() {
		logger.info(">>>> Verifying the cancle button");
		Assert.assertTrue(ret.getUpdateButton().isDisplayed(),"*** Update button not displayed.");
	}

	@Test(description = "AT-96333:Verify the changing value in annual income will change the breakup", priority = 20, dependsOnMethods = "verifyMonthlyIncomeClick")
	public void verifyBreakupChange() {
		logger.info(">>>>> Verify the change in breakup value");
		SeleniumUtil.click(ret.getAnnualIncomeTextbox());
		ret.getAnnualIncomeTextbox().clear();
		SeleniumUtil.sendKeys(ret.getAnnualIncomeTextbox(), "6000");

		SeleniumUtil.click(ret.getPersonalInfoDesc());

		Assert.assertEquals(ret.getAnnualIncomeBreakup().getText().trim(),
				PropsUtil.getDataPropertyValue("finV2.Indicator5.risky.annualIncomeBreakupText"),"*** Breakup text mismatch.");

	}

	@Test(description = "AT-96334:Verify the cancle button click will exit from the page and changes wont be saved", priority = 21, dependsOnMethods = "verifyMonthlyIncomeClick")
	public void verifyCancelButtonAction() {
		logger.info(">>>>> Clicking the cancle button");
		SeleniumUtil.click(ret.getCancleButton());

		Assert.assertEquals(ret.getScoreDetailsText().getText().trim(),
				PropsUtil.getDataPropertyValue("finV2.Indicator4.scoreDetailsText"),"*** Description mismatch.");

	}

	@Test(description = "AT-96335:Verify the click on suggested savings will navigate to Age vs Savings Table", priority = 22)
	public void verifySuggestedSavingsAction() {
		logger.info(">>>>> Clicking on Suggested Savings");
		SeleniumUtil.click(ret.getSuggestedSavingsText());
		SeleniumUtil.waitForPageToLoad(3000);

		Assert.assertEquals(ret.getIndicatorTitle().getText().trim(),
				PropsUtil.getDataPropertyValue("finV2.Indicator4.Titile"),"*** Header mismatch.");

	}

	@Test(description = "AT-96336:Verify the About Score Link on the screen", priority = 23, dependsOnMethods = "verifySuggestedSavingsAction")
	public void verifyAboutScoreLink() {
		logger.info(">>>>> Verifying about score link");
		Assert.assertTrue(ret.getAboutScoreLink().isDisplayed(), "***** About Score Link not present");

	}

	@Test(description = "AT-96337:Verify Back link is present to navigate back to Indicator page", priority = 24, dependsOnMethods = "verifySuggestedSavingsAction")
	public void verifyBackLinkToIndicator() {
		logger.info(">>>>> Verifying back link");
		Assert.assertTrue(ret.getBackButton().isDisplayed(), "***** Back Link not displayed");

	}

	@Test(description = "AT-96338:Verify the table values", priority = 25, dependsOnMethods = "verifySuggestedSavingsAction")
	public void verifyTableHeader_Age() {
		logger.info(">>>>> Verifying the table header Age is displayed");
		Assert.assertEquals(ret.getTableHeader_Age().getText().trim(),
				PropsUtil.getDataPropertyValue("finV2.Indicator4.tableHeader.Age"),"*** Age mismatch.");
	}

	@Test(description = "AT-96339:Verify the table values", priority = 26, dependsOnMethods = "verifySuggestedSavingsAction")
	public void verifyTableHeader_Multiplies() {
		logger.info(">>>>> Verifying the table header Age is displayed");
		Assert.assertEquals(ret.getTableHeader_Multiplier().getText().trim(),
				PropsUtil.getDataPropertyValue("finV2.Indicator4.tableHeader.Multiplier"),"*** Multiplier mismatch.");
	}

	@Test(description = "AT-96340:Verify the table values", priority = 27, dependsOnMethods = "verifySuggestedSavingsAction")
	public void verifyTableHeader_TargetSavings() {
		logger.info(">>>>> Verifying the table header Age is displayed");
		Assert.assertEquals(ret.getTableHeader_TargetSavings().getText().trim(),
				PropsUtil.getDataPropertyValue("finV2.Indicator4.tableHeader.TargetSavings"),"*** Target Savings mismatch.");
	}

	@Test(description = "AT-96341:Verify the ages under the table", priority = 28, dependsOnMethods = "verifySuggestedSavingsAction")
	public void verifyAges() {
		logger.info(">>>>> Verifying ages");
		List<WebElement> agesListElement = ret.getAgesList();
		String agesString[] = PropsUtil.getDataPropertyValue("finV2.Indicator4.healthy.AgesList").split(",");

		for (int age = 0; age < agesString.length; age++) {
			Assert.assertEquals(agesListElement.get(age).getText().trim(), agesString[age],"*** Age mismatch.");
		}
	}

	@Test(description = "AT-96343:Verify the multipliers in the table", priority = 29, dependsOnMethods = "verifySuggestedSavingsAction")
	public void verifyMultipliers() {
		logger.info(">>>>> Verifying multipliers");
		List<WebElement> mList = ret.getMultiplierList();
		String mString[] = PropsUtil.getDataPropertyValue("finV2.Indicator4.healthy.MultiplierList").split(",");

		for (int m = 0; m < mString.length; m++) {
			Assert.assertEquals(mList.get(m).getText().trim(), mString[m],"*** multiplier mismatch.");
		}
	}

	@Test(description = "AT-96342:Verify the Target Savings amounts", priority = 30, dependsOnMethods = "verifySuggestedSavingsAction")
	public void verifyTargetSavingsAmount() {
		logger.info(">>>>> Verifying Target Savings Amount");
		List<WebElement> tList = ret.getTargetSavingsList();
		String tString[] = PropsUtil.getDataPropertyValue("finV2.Indicator4.healthy.TargetSavingsList").split(" ");

		for (int t = 0; t < tString.length; t++) {
			Assert.assertEquals(tList.get(t).getText().trim(), tString[t],"*** Target savings mismatch.");
		}
	}

	@Test(description = "AT-96344:Verify Back button navigates back to Indicator Page", priority = 31, dependsOnMethods = "verifySuggestedSavingsAction")
	public void verifyBackLinkActions() {
		logger.info(">>>>> Clicking on Back link");
		SeleniumUtil.click(ret.getBackButton());

		Assert.assertEquals(ret.getScoreDetailsText().getText().trim(),
				PropsUtil.getDataPropertyValue("finV2.Indicator4.scoreDetailsText"),"*** Details tetx mismatch.");

	}
}
