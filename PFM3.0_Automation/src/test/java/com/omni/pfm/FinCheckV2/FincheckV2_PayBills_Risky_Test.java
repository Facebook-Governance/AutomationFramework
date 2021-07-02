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
public class FincheckV2_PayBills_Risky_Test extends TestBase {
	private static final Logger logger = LoggerFactory.getLogger(FincheckV2_PayBills_Risky_Test.class);
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
		accountAddition.addAggregatedAccount1(PropsUtil.getDataPropertyValue("finV2.billPay.risky.DagSite"),
				PropsUtil.getDataPropertyValue("finV2.billPay.risky.DagPassword"));
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
				PropsUtil.getDataPropertyValue("finV2.Indicator2.Titile"), "*** Header mismatch.");
	}
	}
	@Test(description = "AT-96871:Verify the score details text present", priority = 2, dependsOnMethods = "navigateToIndicatorPage")
	public void verifyScoreDetailsText() {
		logger.info(">>>>> Verifying score details text");

		Assert.assertEquals(pay.getScoreDetailsText().getText().trim(),
				PropsUtil.getDataPropertyValue("finV2.Indicator2.risky.scoreDetailsText"), "*** Text mismatch.");
	}
	@Test(description = "AT-96872:Verify the back link is present on the indicator page", priority = 3, dependsOnMethods = "navigateToIndicatorPage")
	public void verifyBackLink() {
		logger.info(">>>>> Verifyng the back link");
		if(appFlag.equalsIgnoreCase("web")||appFlag.equalsIgnoreCase("false")) 
		{
		Assert.assertTrue(pay.getBackLink().isDisplayed(), "*** Back Link not present");
	}
	}
	@Test(description = "AT-96873:Verify the sugestion Text Present below the healthbar", priority = 4, dependsOnMethods = "navigateToIndicatorPage")
	public void verifySugestionText() {
		logger.info(">>>>> Verifying sugestion text");
		Assert.assertEquals(pay.getSugestionText().getText().trim(),
				PropsUtil.getDataPropertyValue("finV2.Indicator2.risky.sugestionText").trim(),
				"*** Sugestion tetx mismatch.");
	}

	@Test(description = "AT-96874:Verify the health bar is displayed in indicator page", priority = 5, dependsOnMethods = "navigateToIndicatorPage")
	public void verifyHealthBar() {
		logger.info(">>>>> Verifying healthbar");
		Assert.assertTrue(pay.getHealthBar().isDisplayed(), "*** Healthbar not displayed");

	}

	@Test(description = "AT-96875:Verify the score details section header", priority = 7, dependsOnMethods = "navigateToIndicatorPage")
	public void verifyScoreDetailHeader() {
		logger.info(">>>>>Verifying the score details header is displayed");
		Assert.assertEquals(pay.getScoreDetailsHeader().getText().trim(),
				PropsUtil.getDataPropertyValue("finV2.Indicator.scoreDetailsHeader"), "*** Header mismatch.");
	}

	@Test(description = "AT-96876:Verify Overdue Bills", priority = 8, dependsOnMethods = "navigateToIndicatorPage")
	public void verifyUpcomingBillsText() {
		logger.info(">>>>>Verifying the score details header is displayed");
		Assert.assertEquals(pay.getOverdueBillsText().getText().trim(),
				PropsUtil.getDataPropertyValue("finV2.Indicator2.overdueBillText").trim(), "*** Bill text mismatch.");
		Assert.assertEquals(pay.getOverdueBillsValue().getText().trim(),
				PropsUtil.getDataPropertyValue("finV2.Indicator2.risky.overdueBillValue"), "*** Bill value mismatch.");
	}

	@Test(description = "AT-96877:Verify About score section", priority = 9, dependsOnMethods = "navigateToIndicatorPage")
	public void verifyAboutScoreSection() {
		logger.info(">>>>> Verifying About score section");
		Assert.assertEquals(pay.getAboutTheScoreText().getText().trim(),
				PropsUtil.getDataPropertyValue("finV2.Indicator.aboutScoreText"), "*** Header mismatch.");
	}

	@Test(description = "AT-96878:Verify the take action header present", priority = 10, dependsOnMethods = "navigateToIndicatorPage")
	public void getTakeActionHeader() {
		logger.info(">>>>> verifying the take action header");
		Assert.assertEquals(pay.getTakeActionsHeader().getText().trim(),
				PropsUtil.getDataPropertyValue("finV2.Indicator.takeActionHeaderText"), "*** action Header mismatch.");
	}

	@Test(description = "AT-96879:Verify the take action description", priority = 11, dependsOnMethods = "navigateToIndicatorPage")
	public void verifyTakeActionDescription() {
		logger.info(">>>>> Verifying the take action description for Indicator 1");
		Assert.assertEquals(pay.getTakeActionDescription().getText().trim(),
				PropsUtil.getDataPropertyValue("finV2.Indicator2.takeActionsDescription"), "*** Description mismatch.");
	}

	@Test(description = "Verify the action button is displayed", priority = 12, dependsOnMethods = "navigateToIndicatorPage")
	public void verifyActionButton() {
		logger.info(">>>>> Verifying Action button");
		Assert.assertTrue(pay.getActionButton().isDisplayed(), "*** Action button not displayed.");
	}

	@Test(description = "AT-96880:Verify Clicking on About Score will navigate to about score page", priority = 15, dependsOnMethods = "navigateToIndicatorPage")
	public void verifyAboutScoreNavigation() {
		logger.info(">>>>> Clicking on About Score Link");
		SeleniumUtil.click(pay.getAboutTheScoreText());
		SeleniumUtil.waitForPageToLoad(3000);
		Assert.assertTrue(pay.getAboutScoreStatusHeader().isDisplayed(),
				"***** Fialed to navigate to About Score Page");
	}

	@Test(description = "AT-96881:Verify Why this is important text is displayed", priority = 16, dependsOnMethods = "verifyAboutScoreNavigation")
	public void verifyWhyImpText() {
		logger.info("Verifying why this is important text");
		Assert.assertEquals(pay.getWhyImportantText().getText().trim(),
				PropsUtil.getDataPropertyValue("finV2.aboutScore.WhyImportantText"), "*** Why Imp text mismatch.");
	}

	@Test(description = "AT-96882:Verify the why this is important section is closed by default.", priority = 17, dependsOnMethods = "verifyAboutScoreNavigation")
	public void verifyWhyImpSectionClosed() {
		logger.info("Verifying why this is important section is closed by default");
		Assert.assertTrue(pay.getWhyImportantText().getAttribute("class")
				.contains(PropsUtil.getDataPropertyValue("finV2.aboutScore.closedClass")), "*** Section not opend.");
	}

	@Test(description = "Verify cliking on the text will open the content", priority = 18, dependsOnMethods = "verifyAboutScoreNavigation")
	public void verifyWhyTextClickAction() {
		logger.info(">>>>> Clicking on why this is important text");
		SeleniumUtil.click(pay.getWhyImportantText());
		SeleniumUtil.waitForPageToLoad(1000);
		Assert.assertTrue(pay.getWhyImportantText().getAttribute("class")
				.contains(PropsUtil.getDataPropertyValue("finV2.aboutScore.openedClass")), "*** Class not opned.");

	}

	@Test(description = "Verify description text in why this is important section", priority = 19, dependsOnMethods = {
			"verifyAboutScoreNavigation" })
	public void verifyWhyImpDescription() {
		logger.info(">>>>> Verifying description text");
		Assert.assertEquals(pay.getWhyImportantDescriptionText().getText().trim(),
				PropsUtil.getDataPropertyValue("finV2.Indicator2.aboutScore.WhyImportantDescText"),
				"*** Description mismatch.");
	}

	@Test(description = "Verify How this is Calculated text", priority = 20, dependsOnMethods = "verifyAboutScoreNavigation")
	public void verifyHowThisText() {
		logger.info(">>>>> Verifying How This is Calulated text");
		Assert.assertEquals(pay.getHowThisHeader().getText().trim(),
				PropsUtil.getDataPropertyValue("finV2.aboutScore.howThisIsCalculatedText"),
				"*** Description mismatch.");
	}

	@Test(description = "Verify how this is calculated section is opened by default", priority = 21, dependsOnMethods = "verifyAboutScoreNavigation")
	public void verifyHowThisSectionOpened() {
		logger.info(">>>>> Verifying how this section");
		Assert.assertTrue(pay.getHowThisHeader().getAttribute("class")
				.contains(PropsUtil.getDataPropertyValue("finV2.aboutScore.openedClass")), "*** Section not opened.");
	}

	@Test(description = "Verify how this is calculated text", priority = 22, dependsOnMethods = "verifyAboutScoreNavigation")
	public void verifyHowThisDescriptionText() {
		logger.info(">>>>> Verifying description text");
		Assert.assertTrue(
				pay.getHowThisDescriptionText().getText().trim()
						.contains(PropsUtil.getDataPropertyValue("finV2.Indicator2.aboutScore.HowCalculatedDescText")),
				"*** Description mismatch.");
	}

	@Test(description = "Verify the account names present", priority = 25, dependsOnMethods = "verifyAboutScoreNavigation")
	public void verifyAccountNames() {
		logger.info(">>>>> Verifying account names");
		String[] accountNames = new String[2];
		List<WebElement> listNames = pay.getAccountNames();
		accountNames = PropsUtil.getDataPropertyValue("finV2.Indicator2.aboutScore.accountList").split(",");
		for (int n = 0; n < accountNames.length; n++) {
			Assert.assertEquals(listNames.get(n).getText().trim(), accountNames[n], "*** Account Name mismatch.");
		}
	}

	@Test(description = "Verify the account numbers present", priority = 26, dependsOnMethods = "verifyAboutScoreNavigation")
	public void verifyAccountNumbers() {
		logger.info(">>>>> Verifying account names");
		String[] accountNum = new String[2];
		List<WebElement> listNum = pay.getAccountNum();
		accountNum = PropsUtil.getDataPropertyValue("finV2.Indicator2.aboutScore.accountNum").split(",");
		for (int n = 0; n < accountNum.length; n++) {
			Assert.assertEquals(listNum.get(n).getText().trim(), accountNum[n], "*** Account Number mismatch.");
		}
	}

	@Test(description = "Verify All the three accounts are checked by default", priority = 27, dependsOnMethods = "verifyAboutScoreNavigation")
	public void verifyDefaultAccountsType() {
		logger.info(">>>>> Verifying default accounts are checked");
		for (int i = 0; i < pay.getCheckBoxes().size(); i++) {
			Assert.assertTrue(pay.getCheckBoxes().get(i).getAttribute("aria-checked").contains("true"),
					"*** Check box not checked.");
		}
	}

	@Test(description = "Verify Save Changes button", priority = 28, dependsOnMethods = "verifyAboutScoreNavigation")
	public void verifySaveChangesButton() {
		logger.info("Deselecting 2nd account forcefully");
		SeleniumUtil.click(pay.getCheckBoxes().get(1));
		SeleniumUtil.waitForPageToLoad(1000);
		Assert.assertTrue(pay.getSaveChangesButton().isDisplayed(), "*** Button not displayed.");
	}

	@Test(description = "Verify Save Changes button will open popup to save", priority = 29, dependsOnMethods = "verifySaveChangesButton")
	public void verifySavePopup() {
		logger.info("Clicking on Save Changes Button");
		SeleniumUtil.click(pay.getSaveChangesButton());
		SeleniumUtil.waitForPageToLoad(1000);
		Assert.assertTrue(pay.getPopupSave().isDisplayed(), "***** Save Popup not displayedA");
	}

	@Test(description = "Verify Saving changes will reflect in Indicator Page", priority = 30, dependsOnMethods = "verifySavePopup")
	public void verifySavingChanges() {
		logger.info(">>>>> Clicking on popup save button");
		SeleniumUtil.click(pay.getPopupSave());
		SeleniumUtil.waitForPageToLoad(3000);
		Assert.assertEquals(pay.getScoreDetailsText().getText().trim(),
				PropsUtil.getDataPropertyValue("finV2.Indicator2.aboutScore.statusTextIndicatorPage"),
				"*** Status Text mismatch.");
	}
}
