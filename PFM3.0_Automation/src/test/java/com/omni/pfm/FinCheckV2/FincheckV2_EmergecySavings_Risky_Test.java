package com.omni.pfm.FinCheckV2;

/**
* Copyright (c) 2018 Yodlee Inc. All Rights Reserved. 
*
* This software is the confidential and proprietary information of
* Yodlee, Inc. Use is subject to license terms.
*
* @author sbhat1
*/
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
import com.omni.pfm.pages.FincheckV2.FincheckV2_Emergency_Savings_Loc;
import com.omni.pfm.pages.FincheckV2.FincheckV2_GetStarted_Loc;
import com.omni.pfm.pages.FincheckV2.FincheckV2_Spend_Less_Than_You_Earn_Loc;
import com.omni.pfm.pages.Login.LoginPage;
import com.omni.pfm.testBase.TestBase;
import com.omni.pfm.utility.PropsUtil;
import com.omni.pfm.utility.SeleniumUtil;

public class FincheckV2_EmergecySavings_Risky_Test extends TestBase {
	private static final Logger logger = LoggerFactory.getLogger(FincheckV2_EmergecySavings_Risky_Test.class);
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
		stir = new 	FincheckV2_Spend_Less_Than_You_Earn_Loc(d);


		LoginPage.loginMain(d, loginParameter);

		accountAddition.linkAccount();
		accountAddition.addAggregatedAccount1(PropsUtil.getDataPropertyValue("finV2.emergencySavingsRiskyDagsite"),
				PropsUtil.getDataPropertyValue("finV2.emergencySavingsRiskyDagPassword"));
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
	@Test(description = "AT-96820:Verify the score details text present", priority = 2, dependsOnMethods = "navigateToIndicatorPage")
	public void verifyScoreDetailsText() {
		logger.info(">>>>> Verifying score details text");
		Assert.assertEquals(emergency.getScoreDetailsText().getText().trim(),
				PropsUtil.getDataPropertyValue("finV2.Indicator3.risky.scoreDetailsText"),
				"*** Score details text mismatch.");
	}

	@Test(description = "AT-96823:Verify the back link is present on the indicator page", priority = 3, dependsOnMethods = "navigateToIndicatorPage")
	public void verifyBackLink() {
		logger.info(">>>>> Verifyng the back link");
		if(appFlag.equalsIgnoreCase("web")||appFlag.equalsIgnoreCase("false")) 
		{
		Assert.assertTrue(emergency.getBackLink().isDisplayed(), "*** Back Link not present");
	}
	}
	@Test(description = "AT-96822:Verify the sugestion Text Present below the healthbar", priority = 4, dependsOnMethods = "navigateToIndicatorPage")
	public void verifySugestionText() {
		logger.info(">>>>> Verifying sugestion text");
		Assert.assertEquals(emergency.getSugestionText().getText().trim(),
				PropsUtil.getDataPropertyValue("finV2.Indicator3.risky.sugestionText").trim(),
				"*** Sugestion text mimatch.");
	}

	@Test(description = "AT-96824:Verify the health bar is displayed in indicator page", priority = 5, dependsOnMethods = "navigateToIndicatorPage")
	public void verifyHealthBar() {
		logger.info(">>>>> Verifying healthbar");
		Assert.assertTrue(emergency.getHealthBar().isDisplayed(), "*** Healthbar is not displayed.");

	}

	@Test(description = "AT-96825:Verify the score details section header", priority = 7, dependsOnMethods = "navigateToIndicatorPage")
	public void verifyScoreDetailHeader() {
		logger.info(">>>>>Verifying the score details header is displayed");
		Assert.assertEquals(emergency.getScoreDetailsHeader().getText().trim(),
				PropsUtil.getDataPropertyValue("finV2.Indicator.scoreDetailsHeader"),
				"*** Score details header text mismatch.");
	}

	@Test(description = "AT-96826:Verify average spending in score details section", priority = 9, dependsOnMethods = "navigateToIndicatorPage")
	public void verifyAverageSpending() {
		logger.info("Verifying Average Income Text and Value");
		Assert.assertEquals(emergency.getAverageMonthlySpendingText().getText().trim(),
				PropsUtil.getDataPropertyValue("finV2.Indicator3.averageSpendingText"),
				"*** average spending text mismatch.");
		Assert.assertEquals(emergency.getAverageMonthlySpendingValue().getText().trim(),
				PropsUtil.getDataPropertyValue("finV2.Indicator3.risky.averageSpendingValue"),
				"*** average spending value mismatch.");

	}

	@Test(description = "AT-96827:Verify current Coverage in score details section", priority = 10, dependsOnMethods = "navigateToIndicatorPage")
	public void verifyCurrentCovergae() {
		logger.info("Verifying Average Income Text and Value");
		Assert.assertEquals(emergency.getCurrentCoverageText().getText().trim(),
				PropsUtil.getDataPropertyValue("finV2.Indicator3.currentCoverageText"),
				"*** Current coverage text mismatch.");
		Assert.assertEquals(emergency.getCurrentCoverageValue().getText().trim(),
				PropsUtil.getDataPropertyValue("finV2.Indicator3.risky.currentCoverageValue"),
				"*** Current coverage value mismatch.");

	}

	@Test(description = "AT-96828:Verify About score section", priority = 11, dependsOnMethods = "navigateToIndicatorPage")
	public void verifyAboutScoreSection() {
		logger.info(">>>>> Verifying About score section");
		Assert.assertEquals(emergency.getAboutTheScoreText().getText().trim(),
				PropsUtil.getDataPropertyValue("finV2.Indicator.aboutScoreText"), "*** About score text mismatch.");
	}

	@Test(description = "AT-96829:Verify the take action header present", priority = 12, dependsOnMethods = "navigateToIndicatorPage")
	public void getTakeActionHeader() {
		logger.info(">>>>> verifying the take action header");
		Assert.assertEquals(emergency.getTakeActionsHeader().getText().trim(),
				PropsUtil.getDataPropertyValue("finV2.Indicator.takeActionHeaderText"),
				"*** Take action header mismatch.");
	}

	@Test(description = "AT-96830:Verify the take action description", priority = 13, dependsOnMethods = "navigateToIndicatorPage")
	public void verifyTakeActionDescription() {
		logger.info(">>>>> Verifying the take action description for Indicator 1");
		Assert.assertEquals(emergency.getTakeActionDescription().getText().trim(),
				PropsUtil.getDataPropertyValue("finV2.Indicator3.takeActionsDescription"),
				"*** Take action description mismatch.");
	}

	@Test(description = "AT-96831:Verify the action button is displayed", priority = 14, dependsOnMethods = "navigateToIndicatorPage")
	public void verifyActionButton() {
		logger.info(">>>>> Verifying Action button");
		Assert.assertTrue(emergency.getActionButton().isDisplayed(), "*** Action button not displayed.");
	}

	@Test(description = "AT-96832:Verify Clicking on About Score will navigate to about score page", priority = 15, dependsOnMethods = "navigateToIndicatorPage")
	public void verifyAboutScoreNavigation() {
		logger.info(">>>>> Clicking on About Score Link");
		SeleniumUtil.click(emergency.getAboutTheScoreText());
		SeleniumUtil.waitForPageToLoad(3000);
		Assert.assertTrue(emergency.getAboutScoreStatusHeader().isDisplayed(),
				"***** Fialed to navigate to About Score Page");
	}

	@Test(description = "AT-96833:Verify Why this is important text is displayed", priority = 16, dependsOnMethods = "verifyAboutScoreNavigation")
	public void verifyWhyImpText() {
		logger.info("Verifying why this is important text");
		Assert.assertEquals(emergency.getWhyImportantText().getText().trim(),
				PropsUtil.getDataPropertyValue("finV2.aboutScore.WhyImportantText"),
				"*** Why this is important text mismatch.");
	}

	@Test(description = "Verify the why this is important section is closed by default.", priority = 17, dependsOnMethods = "verifyAboutScoreNavigation")
	public void verifyWhyImpSectionClosed() {
		logger.info("Verifying why this is important section is closed by default");
		Assert.assertTrue(
				emergency.getWhyImportantText().getAttribute("class")
						.contains(PropsUtil.getDataPropertyValue("finV2.aboutScore.closedClass")),
				"*** Section not closed by default.");
	}

	@Test(description = "AT-96834:Verify cliking on the text will open the content", priority = 18, dependsOnMethods = "verifyAboutScoreNavigation")
	public void verifyWhyTextClickAction() {
		logger.info(">>>>> Clicking on why this is important text");
		SeleniumUtil.click(emergency.getWhyImportantText());
		SeleniumUtil.waitForPageToLoad(1000);
		Assert.assertTrue(emergency.getWhyImportantText().getAttribute("class")
				.contains(PropsUtil.getDataPropertyValue("finV2.aboutScore.openedClass")), "*** Section not opened.");

	}

	@Test(description = "AT-96835:Verify description text in why this is important section", priority = 19, dependsOnMethods = {
			"verifyAboutScoreNavigation" })
	public void verifyWhyImpDescription() {
		logger.info(">>>>> Verifying description text");
		Assert.assertEquals(emergency.getWhyImportantDescriptionText().getText().trim(),
				PropsUtil.getDataPropertyValue("finV2.Indicator3.aboutScore.WhyImportantDescText"),
				"*** Why important text mismatch.");
	}

	@Test(description = "AT-96836:Verify How this is Calculated text", priority = 20, dependsOnMethods = "verifyAboutScoreNavigation")
	public void verifyHowThisText() {
		logger.info(">>>>> Verifying How This is Calulated text");
		Assert.assertEquals(emergency.getHowThisHeader().getText().trim(),
				PropsUtil.getDataPropertyValue("finV2.aboutScore.howThisIsCalculatedText"),
				"*** How this is calculated text mismatch.");
	}

	@Test(description = "AT-96837:Verify how this is calculated section is opened by default", priority = 21, dependsOnMethods = "verifyAboutScoreNavigation")
	public void verifyHowThisSectionOpened() {
		logger.info(">>>>> Verifying how this section");
		Assert.assertTrue(emergency.getHowThisHeader().getAttribute("class")
				.contains(PropsUtil.getDataPropertyValue("finV2.aboutScore.openedClass")), "*** section not opened.");
	}

	@Test(description = "AT-96838:Verify how this is calculated text", priority = 22, dependsOnMethods = "verifyAboutScoreNavigation")
	public void verifyHowThisDescriptionText() {
		logger.info(">>>>> Verifying description text");
		Assert.assertTrue(
				emergency.getHowThisDescriptionText().getText().trim()
						.contains(PropsUtil.getDataPropertyValue("finV2.Indicator3.aboutScore.HowCalculatedDescText")),
				"** Text mismatch.");
	}

	@Test(description = "AT-96839:Verify See more link", priority = 23, dependsOnMethods = "verifyAboutScoreNavigation")
	public void verifySeeMoreLink() {
		logger.info(">>>>> Verifying See More Link");
		Assert.assertTrue(emergency.getSeeMoreLink().isDisplayed(), "***** See more link not displayed");
	}

	@Test(description = "AT-96840:Verify See less link presents when see more link opens", priority = 24, dependsOnMethods = {
			"verifyAboutScoreNavigation" })
	public void verifySeeLessLink() {
		logger.info(">>>>> Clickin on See more link");
		SeleniumUtil.click(emergency.getSeeMoreLink());
		SeleniumUtil.waitForPageToLoad(1000);
		Assert.assertTrue(emergency.getSeeLessLink().isDisplayed(), "**** See less link not calculated.");
	}

	@Test(description = "AT-96841:Verify the account names present", priority = 25, dependsOnMethods = "verifyAboutScoreNavigation")
	public void verifyAccountNames() {
		logger.info(">>>>> Verifying account names");
		String[] accountNames = new String[3];
		List<WebElement> listNames = emergency.getAccountNames();
		accountNames = PropsUtil.getDataPropertyValue("finV2.Indicator3.aboutScore.accountList").split(",");
		for (int n = 0; n < listNames.size(); n++) {
			Assert.assertEquals(listNames.get(n).getText().trim(), accountNames[n], "*** Account Name mismatch.");
		}
	}

	@Test(description = "AT-96842:Verify the account numbers present", priority = 26, dependsOnMethods = "verifyAboutScoreNavigation")
	public void verifyAccountNumbers() {
		logger.info(">>>>> Verifying account names");
		String[] accountNum = new String[3];
		List<WebElement> listNum = emergency.getAccountNum();
		accountNum = PropsUtil.getDataPropertyValue("finV2.Indicator3.aboutScore.accountNum").split(",");
		for (int n = 0; n < listNum.size(); n++) {
			Assert.assertEquals(listNum.get(n).getText().trim(), accountNum[n], "*** Account Number mismatch.");
		}
	}

	@Test(description = "AT-96843:Verify All the three accounts are checked by default", priority = 27, dependsOnMethods = "verifyAboutScoreNavigation")
	public void verifyDefaultAccountsType() {
		logger.info(">>>>> Verifying default accounts are checked");
		for (int i = 0; i < emergency.getCheckBoxes().size(); i++) {
			Assert.assertTrue(emergency.getCheckBoxes().get(i).getAttribute("aria-checked").contains("true"),
					"*** Checkbox not checked by default.");
		}
	}

	@Test(description = "AT-96846:Verify back will reflect in Indicator Page", priority = 30, dependsOnMethods = "verifyAboutScoreNavigation")
	public void verifySavingChanges() {
		logger.info(">>>>> Clicking on popup save button");
		SeleniumUtil.click(emergency.getBackLinkToIndicator());
		SeleniumUtil.waitForPageToLoad(3000);
		Assert.assertEquals(emergency.getScoreDetailsText().getText().trim(),
				PropsUtil.getDataPropertyValue("finV2.Indicator3.risky.scoreDetailsText"),
				"*** Score details not matched");
	}

}
