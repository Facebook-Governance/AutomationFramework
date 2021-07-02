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
import com.omni.pfm.pages.FincheckV2.FincheckV2_Debt_To_Income_Loc;
import com.omni.pfm.pages.FincheckV2.FincheckV2_GetStarted_Loc;
import com.omni.pfm.pages.FincheckV2.FincheckV2_Spend_Less_Than_You_Earn_Loc;
import com.omni.pfm.pages.Login.LoginPage;
import com.omni.pfm.testBase.TestBase;
import com.omni.pfm.utility.PropsUtil;
import com.omni.pfm.utility.SeleniumUtil;

public class FincheckV2_DebtToIncome_Risky_Test extends TestBase {
	private static final Logger logger = LoggerFactory.getLogger(FincheckV2_DebtToIncome_Risky_Test.class);
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
				PropsUtil.getDataPropertyValue("finV2.annualIncome9K"), 2, 4);
		PageParser.forceNavigate("FincheckV2", d);
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
				PropsUtil.getDataPropertyValue("finV2.Indicator5.Titile"), "*** Indicator title mismatch.");
	}
	}
	@Test(description = "AT-95158:Verify the score details text present", priority = 2, dependsOnMethods = "navigateToIndicatorPage")
	public void verifyScoreDetailsText() {
		logger.info(">>>>> Verifying score details text");
		Assert.assertEquals(deb.getScoreDetailsText().getText().trim(),
				PropsUtil.getDataPropertyValue("finV2.Indicator5.risky.scoreDetailsText"),
				"*** Score Details text mismatch.");
	}

	@Test(description = "AT-95159:Verify the back link is present on the indicator page", priority = 3, dependsOnMethods = "navigateToIndicatorPage")
	public void verifyBackLink() {
		logger.info(">>>>> Verifyng the back link");
		if(appFlag.equalsIgnoreCase("web")||appFlag.equalsIgnoreCase("false")) 
		{
		Assert.assertTrue(deb.getBackLink().isDisplayed(), "*** Back Link not present");
	}}

	@Test(description = "AT-95160:Verify the sugestion Text Present below the healthbar", priority = 4, dependsOnMethods = "navigateToIndicatorPage")
	public void verifySugestionText() {
		logger.info(">>>>> Verifying sugestion text");
		Assert.assertEquals(deb.getSugestionText().getText().trim(),
				PropsUtil.getDataPropertyValue("finV2.Indicator5.risky.sugestionText").trim(), "*** Sugestion text.");
	}

	@Test(description = "AT-95167:Verify the health bar is displayed in indicator page", priority = 5, dependsOnMethods = "navigateToIndicatorPage")
	public void verifyHealthBar() {
		logger.info(">>>>> Verifying healthbar");
		Assert.assertTrue(deb.getHealthBar().isDisplayed(), "*** Health bar displayed.");

	}

	@Test(description = "AT-95162:Verify the score details section header", priority = 7, dependsOnMethods = "navigateToIndicatorPage")
	public void verifyScoreDetailHeader() {
		logger.info(">>>>>Verifying the score details header is displayed");
		Assert.assertEquals(deb.getScoreDetailsHeader().getText().trim(),
				PropsUtil.getDataPropertyValue("finV2.Indicator.scoreDetailsHeader"),
				"*** Score details header not displayed.");
	}

	@Test(description = "AT-95166:Verify Monthly Gross Income Sections", priority = 8, dependsOnMethods = "navigateToIndicatorPage")
	public void verifyAnualGrossIncome() {
		logger.info(">>>>>Verifying Total Monthly Debt Sections");
		Assert.assertEquals(deb.getMonthlyIncomeText().getText().trim(),
				PropsUtil.getDataPropertyValue("finV2.Indicator5.monthlyIncomeText"),
				"*** Monthly Income text mismatch.");
		Assert.assertEquals(deb.getMonthlyIncomeValue().getText().trim(),
				PropsUtil.getDataPropertyValue("finV2.Indicator5.risky.monthlyIncomeValue"),
				"*** Monthly Income Value Mismatch.");
	}

	@Test(description = "AT-95167:Verify Suggested Savings Sections", priority = 9, dependsOnMethods = "navigateToIndicatorPage")
	public void verifySuggestedSavings() {
		logger.info(">>>>>Verifyinf the total montly debt sections");
		Assert.assertEquals(deb.getTotalMonthlyDebtValue().getText().trim(),
				PropsUtil.getDataPropertyValue("finV2.Indicator5.risky.monthlyDebtValue"),
				"*** Monthly Debt Value mismatch.");
		Assert.assertEquals(deb.getTotalMontlyDebtText().getText().trim(),
				PropsUtil.getDataPropertyValue("finV2.Indocator5.monthlyDebtText"), "*** Monthly Debt value mismatch.");
	}

	@Test(description = "AT-95168:Verify About score section", priority = 11, dependsOnMethods = "navigateToIndicatorPage")
	public void verifyAboutScoreSection() {
		logger.info(">>>>> Verifying About score section");
		Assert.assertEquals(deb.getAboutTheScoreText().getText().trim(),
				PropsUtil.getDataPropertyValue("finV2.Indicator.aboutScoreText"), "About Score Text mismatch.");
	}

	@Test(description = "AT-95169:Verify the take action header present", priority = 12, dependsOnMethods = "navigateToIndicatorPage")
	public void getTakeActionHeader() {
		logger.info(">>>>> verifying the take action header");
		Assert.assertEquals(deb.getTakeActionsHeader().getText().trim(),
				PropsUtil.getDataPropertyValue("finV2.Indicator.takeActionHeaderText"),
				"*** Take Action header mismatch");
	}

	@Test(description = "AT-95170:Verify the action button is displayed", priority = 14, dependsOnMethods = "navigateToIndicatorPage")
	public void verifyActionButton() {
		logger.info(">>>>> Verifying Action button");
		Assert.assertTrue(deb.getActionButton().isDisplayed(), "*** Action Button not displayed.");
	}

	@Test(description = "AT-95171:Verify Clicking on About Score will navigate to about score page", priority = 15, dependsOnMethods = "navigateToIndicatorPage")
	public void verifyAboutScoreNavigation() {
		logger.info(">>>>> Clicking on About Score Link");
		SeleniumUtil.click(deb.getAboutTheScoreText());
		SeleniumUtil.waitForPageToLoad(3000);
		Assert.assertTrue(deb.getAboutScoreStatusHeader().isDisplayed(),
				"***** Fialed to navigate to About Score Page");
	}

	@Test(description = "AT-95172:Verify Why this is important text is displayed", priority = 16, dependsOnMethods = "verifyAboutScoreNavigation")
	public void verifyWhyImpText() {
		logger.info("Verifying why this is important text");
		Assert.assertEquals(deb.getWhyImportantText().getText().trim(),
				PropsUtil.getDataPropertyValue("finV2.aboutScore.WhyImportantText"),
				"*** Why Important text is not displayed.");
	}

	@Test(description = "AT-95173:Verify the why this is important section is closed by default.", priority = 17, dependsOnMethods = "verifyAboutScoreNavigation")
	public void verifyWhyImpSectionClosed() {
		logger.info("Verifying why this is important section is closed by default");
		Assert.assertTrue(
				deb.getWhyImportantText().getAttribute("class")
						.contains(PropsUtil.getDataPropertyValue("finV2.aboutScore.closedClass")),
				"*** Why Important is not closed by default.");
	}

	@Test(description = "AT-95174:Verify cliking on the text will open the content", priority = 18, dependsOnMethods = "verifyAboutScoreNavigation")
	public void verifyWhyTextClickAction() {
		logger.info(">>>>> Clicking on why this is important text");
		SeleniumUtil.click(deb.getWhyImportantText());

		Assert.assertTrue(
				deb.getWhyImportantText().getAttribute("class")
						.contains(PropsUtil.getDataPropertyValue("finV2.aboutScore.openedClass")),
				"*** Why Important text is not opened.");

	}

	@Test(description = "AT-95176:Verify description text in why this is important section", priority = 19, dependsOnMethods = {
			"verifyAboutScoreNavigation" })
	public void verifyWhyImpDescription() {
		logger.info(">>>>> Verifying description text");
		Assert.assertEquals(deb.getWhyImportantDescriptionText().getText().trim(),
				PropsUtil.getDataPropertyValue("finV2.Indicator5.aboutScore.WhyImportantDescText"),
				"*** Why Important description mismatch.");
	}

	@Test(description = "AT-95177:Verify How this is Calculated text", priority = 20, dependsOnMethods = "verifyAboutScoreNavigation")
	public void verifyHowThisText() {
		logger.info(">>>>> Verifying How This is Calulated text");
		Assert.assertEquals(deb.getHowThisHeader().getText().trim(),
				PropsUtil.getDataPropertyValue("finV2.aboutScore.howThisIsCalculatedText"),
				"*** How this is calculated text mismatch.");
	}

	@Test(description = "AT-95178:Verify how this is calculated section is opened by default", priority = 21, dependsOnMethods = "verifyAboutScoreNavigation")
	public void verifyHowThisSectionOpened() {
		logger.info(">>>>> Verifying how this section");
		Assert.assertTrue(
				deb.getHowThisHeader().getAttribute("class")
						.contains(PropsUtil.getDataPropertyValue("finV2.aboutScore.openedClass")),
				"*** How this is calulated section not opened.");
	}

	@Test(description = "AT-95179:Verify how this is calculated text", priority = 22, dependsOnMethods = "verifyAboutScoreNavigation")
	public void verifyHowThisDescriptionText() {
		logger.info(">>>>> Verifying description text");
		Assert.assertTrue(
				deb.getHowThisDescriptionText().getText().trim()
						.contains(PropsUtil.getDataPropertyValue("finV2.Indicator5.aboutScore.HowCalculatedDescText")),
				"*** Description mismatch.");
	}

	@Test(description = "AT-95180:Verify the account names present", priority = 25, dependsOnMethods = "verifyAboutScoreNavigation")
	public void verifyAccountNames() {
		logger.info(">>>>> Verifying account names");
		String[] accountNames = new String[2];
		List<WebElement> listNames = deb.getAccountNames();
		accountNames = PropsUtil.getDataPropertyValue("finV2.Indicator5.aboutScore.accountList").split(",");
		for (int n = 0; n < listNames.size(); n++) {
			Assert.assertEquals(listNames.get(n).getText().trim(), accountNames[n], "*** Account List mismatch.");
		}
	}

	@Test(description = "AT-95181:Verify the account numbers present", priority = 26, dependsOnMethods = "verifyAboutScoreNavigation")
	public void verifyAccountNumbers() {
		logger.info(">>>>> Verifying account names");
		String[] accountNum = new String[2];
		List<WebElement> listNum = deb.getAccountNum();
		accountNum = PropsUtil.getDataPropertyValue("finV2.Indicator5.aboutScore.accountNum").split(",");
		for (int n = 0; n < listNum.size(); n++) {
			Assert.assertEquals(listNum.get(n).getText().trim(), accountNum[n], "*** Account Number mismatch.");
		}
	}

	@Test(description = "AT-95283:Verify All the three accounts are checked by default", priority = 27, dependsOnMethods = "verifyAboutScoreNavigation")
	public void verifyDefaultAccountsType() {
		logger.info(">>>>> Verifying default accounts are checked");
		for (int i = 0; i < deb.getCheckBoxes().size(); i++) {
			Assert.assertTrue(deb.getCheckBoxes().get(i).getAttribute("aria-checked").contains("true"),
					"**** Aria not checked.");
		}
	}

	@Test(description = "AT-95288:Verify Save Changes button", priority = 28, dependsOnMethods = "verifyAboutScoreNavigation")
	public void verifySaveChangesButton() {
		logger.info("Deselecting 2nd account forcefully");
		SeleniumUtil.click(deb.getCheckBoxes().get(1));
		Assert.assertTrue(deb.getSaveChangesButton().isDisplayed(), "*** Save changes button not displayed.");
	}

	@Test(description = "AT-95289:Verify Save Changes button will open popup to save", priority = 29, dependsOnMethods = "verifySaveChangesButton")
	public void verifySavePopup() {
		logger.info("Clicking on Save Changes Button");
		SeleniumUtil.click(deb.getSaveChangesButton());
		SeleniumUtil.waitForPageToLoad();
		Assert.assertTrue(deb.getPopupSave().isDisplayed(), "***** Save Popup not displayed");
	}

	@Test(description = "AT-95290:Verify Saving changes will reflect in Indicator Page", priority = 30, dependsOnMethods = "verifySavePopup")
	public void verifySavingChanges() {
		logger.info(">>>>> Clicking on popup save button");
		SeleniumUtil.click(deb.getPopupSave());
		SeleniumUtil.waitForPageToLoad(3000);
		Assert.assertEquals(deb.getScoreDetailsText().getText().trim(),
				PropsUtil.getDataPropertyValue("finV2.Indicator5.aboutScore.statusTextIndicatorPage"),
				"*** Status text mismatch.");
	}
}
