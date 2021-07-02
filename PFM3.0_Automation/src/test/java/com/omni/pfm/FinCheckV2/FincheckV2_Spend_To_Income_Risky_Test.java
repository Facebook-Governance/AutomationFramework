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
public class FincheckV2_Spend_To_Income_Risky_Test extends TestBase {
	private static final Logger logger = LoggerFactory.getLogger(FincheckV2_Spend_To_Income_Risky_Test.class);
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
		accountAddition.addAggregatedAccount(PropsUtil.getDataPropertyValue("finV2.spend.risky.DagSite"),
				PropsUtil.getDataPropertyValue("finV2.spend.riksy.DagPassword"));
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
				PropsUtil.getDataPropertyValue("finV2.Indicator1.Titile"), "*** Header text mismatch.");
	}
	}
	@Test(description = "Verify the score details text present", priority = 2, dependsOnMethods = "navigateToIndicatorPage")
	public void verifyScoreDetailsText() {
		logger.info(">>>>> Verifying score details text");
		Assert.assertEquals(stir.getScoreDetailsText().getText().trim(),
				PropsUtil.getDataPropertyValue("finV2.Indicator1.risky.scoreDetailsText"),
				"*** Score details text mismatch.");
	}

	@Test(description = "Verify the back link is present on the indicator page", priority = 3, dependsOnMethods = "navigateToIndicatorPage")
	public void verifyBackLink() {
		logger.info(">>>>> Verifyng the back link");
		
		if(appFlag.equalsIgnoreCase("web")||appFlag.equalsIgnoreCase("false")) 
		{
		Assert.assertTrue(stir.getBackLink().isDisplayed(), "**** Back Link not present");
	}
	}
	@Test(description = "Verify the sugestion Text Present below the healthbar", priority = 4, dependsOnMethods = "navigateToIndicatorPage")
	public void verifySugestionText() {
		logger.info(">>>>> Verifying sugestion text");
		Assert.assertEquals(stir.getSugestionText().getText().trim(),
				PropsUtil.getDataPropertyValue("finV2.Indicator1.risky.sugestionText"), "*** Sugestion text mismatch.");
	}

	@Test(description = "Verify the health bar is displayed in indicator page", priority = 5, dependsOnMethods = "navigateToIndicatorPage")
	public void verifyHealthBar() {
		logger.info(">>>>> Verifying healthbar");
		Assert.assertTrue(stir.getHealthBar().isDisplayed(), "*** Health bar text mismatch.");

	}

	@Test(description = "Verify the score details section header", priority = 7, dependsOnMethods = "navigateToIndicatorPage")
	public void verifyScoreDetailHeader() {
		logger.info(">>>>>Verifying the score details header is displayed");
		Assert.assertEquals(stir.getScoreDetailsHeader().getText().trim(),
				PropsUtil.getDataPropertyValue("finV2.Indicator.scoreDetailsHeader"),
				"*** Score details text mismatch.");
	}

	@Test(description = "Verify average Income in score details section", priority = 8, dependsOnMethods = "navigateToIndicatorPage")
	public void verifyAverageIncomeSection() {
		logger.info("Verifying Average Income Text and Value");
		Assert.assertEquals(stir.getAverageIncomeText().getText().trim(),
				PropsUtil.getDataPropertyValue("finV2.Indicator.averageIncomeText"), "*** Income text mismatch.");
		Assert.assertEquals(stir.getAverageIncomeValue().getText().trim(),
				PropsUtil.getDataPropertyValue("finV2.Indicator1.risky.averageIncomeValue"),
				"*** Income value text mismatch.");

	}

	@Test(description = "Verify average soending in score details section", priority = 9, dependsOnMethods = "navigateToIndicatorPage")
	public void verifyAverageSpending() {
		logger.info("Verifying Average Income Text and Value");
		Assert.assertEquals(stir.getAverageSpendingText().getText().trim(),
				PropsUtil.getDataPropertyValue("finV2.Indicator.averageSpendingText"), "*** Spending text mismatch.");
		Assert.assertEquals(stir.getAverageSpendingValue().getText().trim(),
				PropsUtil.getDataPropertyValue("finV2.Indicator1.risky.averageSpendingValue"),
				"*** Spending value mismatch.");

	}

	@Test(description = "Verify About score section", priority = 10, dependsOnMethods = "navigateToIndicatorPage")
	public void verifyAboutScoreSection() {
		logger.info(">>>>> Verifying About score section");
		Assert.assertEquals(stir.getAboutTheScoreText().getText().trim(),
				PropsUtil.getDataPropertyValue("finV2.Indicator.aboutScoreText"), "*** About score text mismatch.");
	}

	@Test(description = "Verify the take action header present", priority = 12, dependsOnMethods = "navigateToIndicatorPage")
	public void getTakeActionHeader() {
		logger.info(">>>>> verifying the take action header");
		Assert.assertEquals(stir.getTakeActionsHeader().getText().trim(),
				PropsUtil.getDataPropertyValue("finV2.Indicator.takeActionHeaderText"),
				"*** take action text mismatch.");
	}

	@Test(description = "Verify the take action description", priority = 13, dependsOnMethods = "navigateToIndicatorPage")
	public void verifyTakeActionDescription() {
		logger.info(">>>>> Verifying the take action description for Indicator 1");
		Assert.assertEquals(stir.getTakeActionDescription().getText().trim(),
				PropsUtil.getDataPropertyValue("finV2.Indicator1.takeActionsDescription"),
				"*** Description text mismatch.");
	}

	@Test(description = "Verify the action button is displayed", priority = 14, dependsOnMethods = "navigateToIndicatorPage")
	public void verifyActionButton() {
		logger.info(">>>>> Verifying Action button");
		Assert.assertTrue(stir.getActionButton().isDisplayed(), "*** Action button not displayed.");	
	}
	
	@Test(description = "Verify Clicking on About Score will navigate to about score page", priority = 15, dependsOnMethods = "navigateToIndicatorPage")
	public void verifyAboutScoreNavigation() {
		logger.info(">>>>> Clicking on About Score Link");
		SeleniumUtil.click(stir.getAboutTheScoreText());
		SeleniumUtil.waitForPageToLoad(3000);
		Assert.assertTrue(stir.getAboutScoreStatusHeader().isDisplayed(),
				"***** Fialed to navigate to About Score Page");
	}

	@Test(description = "Verify Why this is important text is displayed", priority = 16, dependsOnMethods = "verifyAboutScoreNavigation")
	public void verifyWhyImpText() {
		logger.info("Verifying why this is important text");
		Assert.assertEquals(stir.getWhyImportantText().getText().trim(),
				PropsUtil.getDataPropertyValue("finV2.aboutScore.WhyImportantText"), "*** Why imp text mismatch.");
	}

	@Test(description = "Verify the why this is important section is closed by default.", priority = 17, dependsOnMethods = "verifyAboutScoreNavigation")
	public void verifyWhyImpSectionClosed() {
		logger.info("Verifying why this is important section is closed by default");
	Assert.assertTrue(stir.getWhyImportantText().getAttribute("class")
				.contains(PropsUtil.getDataPropertyValue("finV2.aboutScore.closedClass")), "*** Section not closed");
	}

	@Test(description = "Verify cliking on the text will open the content", priority = 18, dependsOnMethods = "verifyAboutScoreNavigation")
	public void verifyWhyTextClickAction() {
		logger.info(">>>>> Clicking on why this is important text");
		SeleniumUtil.click(stir.getWhyImportantText());
		SeleniumUtil.waitForPageToLoad(1000);
		Assert.assertTrue(stir.getWhyImportantText().getAttribute("class")
				.contains(PropsUtil.getDataPropertyValue("finV2.aboutScore.openedClass")), "*** Section not opened.");

	}

	@Test(description = "Verify description text in why this is important section", priority = 19, dependsOnMethods = {
			"verifyAboutScoreNavigation" })
	public void verifyWhyImpDescription() {
		logger.info(">>>>> Verifying description text");
		Assert.assertEquals(stir.getWhyImportantDescriptionText().getText().trim(),
				PropsUtil.getDataPropertyValue("finV2.Indicator1.aboutScore.WhyImportantDescText"),
				"*** Description text mismatch.");
	}

	@Test(description = "Verify How this is Calculated text", priority = 20, dependsOnMethods = "verifyAboutScoreNavigation")
	public void verifyHowThisText() {
		logger.info(">>>>> Verifying How This is Calulated text");
		Assert.assertEquals(stir.getHowThisHeader().getText().trim(),
				PropsUtil.getDataPropertyValue("finV2.aboutScore.howThisIsCalculatedText"),
				"*** How this score text mismatch.");
	}

	@Test(description = "Verify how this is calculated section is opened by default", priority = 21, dependsOnMethods = "verifyAboutScoreNavigation")
	public void verifyHowThisSectionOpened() {
		logger.info(">>>>> Verifying how this section");
		Assert.assertTrue(stir.getHowThisHeader().getAttribute("class").contains(
				PropsUtil.getDataPropertyValue("finV2.aboutScore.openedClass")), "*** Spending text mismatch.");
	}

	@Test(description = "Verify how this is calculated text", priority = 22, dependsOnMethods = "verifyAboutScoreNavigation")
	public void verifyHowThisDescriptionText() {
		logger.info(">>>>> Verifying description text");
		Assert.assertTrue(
				stir.getHowThisDescriptionText().getText().trim()
						.contains(PropsUtil.getDataPropertyValue("finV2.Indicator1.aboutScore.HowCalculatedDescText")),
				"*** Description text mismatch.");
	}

	@Test(description = "Verify See more link", priority = 23, dependsOnMethods = "verifyAboutScoreNavigation")
	public void verifySeeMoreLink() {
		logger.info(">>>>> Verifying See More Link");
		Assert.assertTrue(stir.getSeeMoreLink().isDisplayed(), "***** See more link not displayed");
	}

	@Test(description = "Verify See less link presents when see more link opens", priority = 24, dependsOnMethods = {
			"verifyAboutScoreNavigation" })
	public void verifySeeLessLink() {
		logger.info(">>>>> Clickin on See more link");
		SeleniumUtil.click(stir.getSeeMoreLink());
		SeleniumUtil.waitForPageToLoad(1000);
		Assert.assertTrue(stir.getSeeLessLink().isDisplayed(), "*** See less link mismatch.");
	}

	@Test(description = "Verify the account names present", priority = 25, dependsOnMethods = "verifyAboutScoreNavigation")
	public void verifyAccountNames() {
		logger.info(">>>>> Verifying account names");
		String[] accountNames = new String[3];
		List<WebElement> listNames = stir.getAccountNames();
		accountNames = PropsUtil.getDataPropertyValue("finV2.Indicator1.aboutScore.accountList").split(",");
		for (int n = 0; n < listNames.size(); n++) {
			Assert.assertEquals(listNames.get(n).getText().trim(), accountNames[n], "*** Accounts name text mismatch.");
		}
	}

	@Test(description = "Verify the account numbers present", priority = 26, dependsOnMethods = "verifyAboutScoreNavigation")
	public void verifyAccountNumbers() {
		logger.info(">>>>> Verifying account names");
		String[] accountNum = new String[3];
		List<WebElement> listNum = stir.getAccountNum();
		accountNum = PropsUtil.getDataPropertyValue("finV2.Indicator1.aboutScore.accountNum").split(",");
		for (int n = 0; n < listNum.size(); n++) {
			Assert.assertEquals(listNum.get(n).getText().trim(), accountNum[n], "*** Account number text mismatch.");
		}
	}

	@Test(description = "Verify All the three accounts are checked by default", priority = 27, dependsOnMethods = "verifyAboutScoreNavigation")
	public void verifyDefaultAccountsType() {
		logger.info(">>>>> Verifying default accounts are checked");
		for (int i = 0; i < stir.getCheckBoxes().size(); i++) {
			Assert.assertTrue(stir.getCheckBoxes().get(i).getAttribute("aria-checked").contains("true"),
					"*** Checkbox not checked.");
		}
	}

	@Test(description = "Verify Save Changes button", priority = 28, dependsOnMethods = "verifyAboutScoreNavigation")
	public void verifySaveChangesButton() {
		logger.info("Deselecting 2nd account forcefully");
		SeleniumUtil.click(stir.getCheckBoxes().get(1));
		SeleniumUtil.waitForPageToLoad(1000);
		Assert.assertTrue(stir.getSaveChangesButton().isDisplayed(), "*** Save changes button not displayed.");
	}

	@Test(description = "Verify Save Changes button will open popup to save", priority = 29, dependsOnMethods = "verifySaveChangesButton")
	public void verifySavePopup() {
		logger.info("Clicking on Save Changes Button");
		SeleniumUtil.click(stir.getSaveChangesButton());
		SeleniumUtil.waitForPageToLoad(1000);
		Assert.assertTrue(stir.getPopupSave().isDisplayed(), "***** Save Popup not displayedA");
	}

	@Test(description = "Verify Saving changes will reflect in Indicator Page", priority = 30, dependsOnMethods = "verifySavePopup")
	public void verifySavingChanges() {
		logger.info(">>>>> Clicking on popup save button");
		SeleniumUtil.click(stir.getPopupSave());
		SeleniumUtil.waitForPageToLoad(3000);
		Assert.assertEquals(stir.getScoreDetailsText().getText().trim(),
				PropsUtil.getDataPropertyValue("finV2.Indicator1.aboutScore.statusTextIndicatorPage"),
				"*** Status text mismatch.");
	}
}
