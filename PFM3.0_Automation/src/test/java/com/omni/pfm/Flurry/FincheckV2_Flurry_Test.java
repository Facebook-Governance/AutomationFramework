package com.omni.pfm.Flurry;
/**
* Copyright (c) 2019 Yodlee Inc. All Rights Reserved.  
*
* This software is the confidential and proprietary information of
* Yodlee, Inc. Use is subject to license terms.
*
* @author sbhat1
*/
import static org.testng.Assert.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.WebElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.*;

import com.omni.pfm.PageProcessor.PageParser;
import com.omni.pfm.pages.AccountAddition.AccountAddition;
import com.omni.pfm.pages.FincheckV2.FincheckV2_Onboarding_Loc;
import com.omni.pfm.pages.Flurry.FincheckV2_Flury_Loc;
import com.omni.pfm.pages.Login.LoginPage;
import com.omni.pfm.testBase.TestBase;
import com.omni.pfm.utility.FlurryUtil;
import com.omni.pfm.utility.PropsUtil;
import com.omni.pfm.utility.SeleniumUtil;

public class FincheckV2_Flurry_Test extends TestBase {
	public static final Logger logger = LoggerFactory.getLogger(FincheckV2_Flurry_Test.class);
	FincheckV2_Flury_Loc flurry;
	AccountAddition accountAddition;
	FlurryUtil util;
	FincheckV2_Onboarding_Loc onboard;

	@BeforeClass(alwaysRun = true)
	public void doInit() throws Exception {
		doInitialization("Fincheck V2 Flurry Automation");
		flurry = new FincheckV2_Flury_Loc(d);
		accountAddition = new AccountAddition();
		util = new FlurryUtil();
		onboard = new FincheckV2_Onboarding_Loc(d);

		LoginPage.loginMain(d, loginParameter);
		SeleniumUtil.waitForPageToLoad();
		if (PropsUtil.getEnvPropertyValue("cnf_newUserLogin").equals("yes")) {
			accountAddition.linkAccount();
			accountAddition.addAggregatedAccount("shivaprasad_bhat.site16441.1", "site16441.1");
		}

	}

	@Test(description = "Navigate to Fincheck App", priority = 2)
	public void navigateToFinchcek() throws Exception {
		SeleniumUtil.refresh(d);
		SeleniumUtil.waitForPageToLoad();

		// Clear the buttons
		flurry.startFlurryTool();
		flurry.clearAllTags();
		logger.info(">>>>> Navigating to Fincheck");
		PageParser.forceNavigate("FincheckV2", d);
		SeleniumUtil.waitForPageToLoad();
		assertTrue(flurry.getWelcomeHeader().isDisplayed(), "*** Failed to navigate to Fincheck V2");

	}

	@Test(description = "Click On Get Started Button", priority = 3, dependsOnMethods = "navigateToFinchcek")
	public void clickOnGetStartedButton() {
		flurry.clearAllTags();
		logger.info(">>>>> Clicking on Get Started Button");
		SeleniumUtil.click(flurry.getStartedButton());
		assertTrue(flurry.getStep1Desc().isDisplayed());

	}

	@Test(description = "Click On Link More Button", priority = 4, dependsOnMethods = "clickOnGetStartedButton")
	public void clickOnLinkMoreButton() {
		logger.info(">>>>> Clicking on Link more accounts button");
		SeleniumUtil.click(flurry.getLinkMoreButton());
		assertTrue(flurry.getLinkAccountTabs().isDisplayed(), "*** FL Floater not displayed.");
	}

	@Test(description = "Click on Close Icon", priority = 5, dependsOnMethods = "clickOnLinkMoreButton")
	public void clickOnCloseIcon() {
		logger.info(">>>>> Clicking on Close Icon");
		SeleniumUtil.click(flurry.getLinkMoreCloseButton());
		assertTrue(flurry.getStep1Desc().isDisplayed(), "*** Step 1 Description not displayed");

	}

	@Test(description = "Click on Next Step", priority = 6, dependsOnMethods = "clickOnCloseIcon")
	public void clickContineButton() {
		logger.info(">>>>> Clicking on Continue Button");
		SeleniumUtil.click(flurry.getStep1ContinueButton());
		assertTrue(flurry.getStep2Desc().isDisplayed(), "*** Step 2 Description not matched.");
	}

	@Test(description = "Select the option in personal inforrmation screen", priority = 7, dependsOnMethods = "clickContineButton")
	public void finishPersonalInfoEntry() {
		logger.info(">>>>> Entering the yob dropdown");
		SeleniumUtil.click(flurry.getYOBDropdown());
		flurry.selectYearOfBirth("1990");

		logger.info(">>>>> Entering income");
		SeleniumUtil.click(flurry.getAnualHouseholdTextbox());
		flurry.getAnualHouseholdTextbox().sendKeys("10000");

		logger.info(">>>>> Entering Credit Score");
		SeleniumUtil.click(flurry.getCreditScoreDD());
		flurry.selectCreditScore(2);

		logger.info(">>>>> Clicking on Next Button");
		SeleniumUtil.click(flurry.getStep2NextButton());

		assertTrue(flurry.getStep3Desc().isDisplayed(), "*** Step 3 Description not present on DOM");
	}

	@Test(description = "Select some values from the toggle button", priority = 8, dependsOnMethods = "finishPersonalInfoEntry")
	public void verifyHouseHoldSection() {
		logger.info(">>>>> Clicking on next button");
		SeleniumUtil.click(flurry.getStep2NextButton());

		assertTrue(flurry.getStep4Desc().isDisplayed(), "*** Failed to navigate to step 4");
	}

	@Test(description = "Select the second option of plannig", priority = 9, dependsOnMethods = "verifyHouseHoldSection")
	public void verifyPlannings() {
		logger.info(">>>>> Selectig the planning");
		SeleniumUtil.click(onboard.getPlanningRadioButtons().get(0));
		logger.info(">>>>> Clicking next");
		SeleniumUtil.click(flurry.getStep2NextButton());

		assertTrue(flurry.getStep5Desc().isDisplayed(), "*** Step 5 desciption not displayed.");
	}

	@Test(description = "Select the habbits option", priority = 10, dependsOnMethods = "verifyPlannings")
	public void verifyHabits() {
		logger.info(">>>>> Clicking Finish button");
		SeleniumUtil.click(flurry.getFinishButton());
		assertTrue(flurry.getCongratsMessage().isDisplayed(), "*** Congrats message not displayed");
	}

	@Test(description = "Verify the fincheck header", priority = 11, dependsOnMethods = "verifyHabits")
	public void verifyFincheckHeader() {
		SeleniumUtil.click(flurry.getOKLetsGoButton());
		assertTrue(flurry.fincheckHeader().isDisplayed());
	}

	@Test(description = "Indicator 1 - Spent To Income Ratio Events generation", priority = 12, dependsOnMethods = "verifyFincheckHeader")
	public void spendToIncomeRatio() {
		logger.info(">>>>> Clicking on Indicator from overview page");
		SeleniumUtil.click(flurry.getIndicator1FromDashboard());

		logger.info(">>>>> Clicking on average Income Expense link");
		SeleniumUtil.click(flurry.getAverageIELink());

		logger.info(">>>>> Clicking on Average Income value 1");
		SeleniumUtil.click(flurry.getInd1IncomeValue());

		try {
			SeleniumUtil.click(flurry.getInd1INCOMEFTUE1());
			SeleniumUtil.click(flurry.getInd1INCOMEFTUE2());

		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Not able to finish FTUE");
		}

		logger.info(">>>>> Clicking on back link");
		SeleniumUtil.click(flurry.getBackLinkToFincheck());

		SeleniumUtil.waitForPageToLoad(2000);
		logger.info(">>>>> Clicking on average expense link");
		SeleniumUtil.click(flurry.getInd1AverageExpense());

		SeleniumUtil.waitForPageToLoad(2000);
		logger.info(">>>>> Clicking on back link");
		SeleniumUtil.click(flurry.getBackLinkToFincheck());

		SeleniumUtil.waitForPageToLoad(2000);
		logger.info(">>>>> Clicking on back link");
		SeleniumUtil.click(flurry.getBackToIndicatorLink());

		logger.info(">>>>> Clicking on about score section");
		SeleniumUtil.click(flurry.getAboutScoreLink());

		logger.info(">>>>> Clicking on Why This is Important link");
		SeleniumUtil.click(flurry.getWhyImportantLink());

		logger.info(">>>> Clicking on first account checkbox");
		SeleniumUtil.click(flurry.getAccountCheckBox());
		//flurry.getAccountCheckBox().click();

		logger.info(">>>> Clicking on Save button");
		SeleniumUtil.click(flurry.getSaveChangesButton());
		
		SeleniumUtil.waitForPageToLoad(2000);
		logger.info(">>>>> Clicking on Cancle button");
		SeleniumUtil.click(flurry.getCancleButtonPopup());

		logger.info(">>>> Clicking on Save button");
		SeleniumUtil.click(flurry.getSaveChangesButton());

		SeleniumUtil.waitForPageToLoad(2000);
		logger.info(">>>>> Clicking on save button on popup");
		SeleniumUtil.click(flurry.getSaveButtonPopup());

		/*
		 * logger.info(">>>>> Clicking on Action button");
		 * SeleniumUtil.click(flurry.getActionButton());
		 * 
		 * logger.info(">>>>> Clicking on back link");
		 * SeleniumUtil.click(flurry.getBackLinkToFincheck());
		 */

	}

	@Test(description = "Test Flurry excel", priority = 7, dependsOnMethods = "clickContineButton", enabled=false)
	public void verifyFlurryKeys() throws IOException {
		ArrayList<String> expectedTags = util.readTestData(0);
		ArrayList<String> actualTags = new ArrayList<>();
		FlurryUtil.navigateToFlurryTool(d);
		SeleniumUtil.refresh(d);
		SeleniumUtil.waitForPageToLoad();
		List<WebElement> tags = flurry.getGeneratedKyes();

		for (int i = 0; i < tags.size(); i++) {
			actualTags.add(tags.get(i).getText().trim());
		}
		assertEquals(actualTags, expectedTags, "Tags not matched.");
	}
}
