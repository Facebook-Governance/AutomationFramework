/*******************************************************************************
 * Copyright (c) 2018 Yodlee Inc. All Rights Reserved. 
 * 
 * This software is the confidential and proprietary information of
 * Yodlee, Inc. Use is subject to license terms.
 * 
 * @author sbhat1
 ******************************************************************************/
package com.omni.pfm.SFG;

import java.awt.AWTException;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.accessibility.examples.testng.webdriver.test.RunAccessibilityTest;
import com.omni.pfm.PageProcessor.PageParser;
import com.omni.pfm.TransactionEnhancement1.Transaction_Layout_Test;
import com.omni.pfm.pages.AccountAddition.AccountAddition;
import com.omni.pfm.pages.Login.LoginPage;
import com.omni.pfm.pages.SFG.SFG_CreateGoal_GetStarted_Loc;
import com.omni.pfm.pages.SFG.SFG_createGoalEdit_Loc;
import com.omni.pfm.testBase.TestBase;
import com.omni.pfm.utility.PropsUtil;
import com.omni.pfm.utility.SeleniumUtil;

public class SFG_CreateGoal_GetStarted extends TestBase {

	SFG_createGoalEdit_Loc goalEdit;
	SFG_CreateGoal_GetStarted_Loc getStart;

	String Subcat = null;
	LoginPage login;
	AccountAddition accountAdd;
	private static final Logger logger = LoggerFactory.getLogger(Transaction_Layout_Test.class);

	@BeforeClass(alwaysRun = true)
	public void testInit() throws Exception {
		doInitialization("SFG_CreateGoal_GetStarted");
		goalEdit = new SFG_createGoalEdit_Loc(d);
		getStart = new SFG_CreateGoal_GetStarted_Loc(d);
		login = new LoginPage();
		accountAdd = new AccountAddition();
		d.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);

		LoginPage.loginMain(d, loginParameter);
		PageParser.forceNavigate("SFG", d);

	}

	@Test(description = "The header on the Link account page should be 'Create a Goal'.", groups = {
			"DesktopBrowsers" }, priority = 2, enabled = true)
	public void ftueGoalPageheader() {
		logger.info("save for goal header");
		Assert.assertEquals(getStart.sfgHeader().getText(), PropsUtil.getDataPropertyValue("sfgLandingHeader"));
	}

	@Test(description = "On the link account page there should be the following text.", dependsOnMethods = {
			"ftueGoalPageheader" }, groups = { "DesktopBrowsers" }, priority = 3, enabled = true)
	public void ftueGoalNoDataMessage() {
		logger.info(" save for goal no data message ");
		Assert.assertEquals(getStart.noDataMessage1().getText().trim(),
				PropsUtil.getDataPropertyValue("SFGLandingNoDataMessage1").trim());
		Assert.assertEquals(getStart.noDataMessage2().getText().trim(),
				PropsUtil.getDataPropertyValue("SFGLandingNoDataMessage2").trim());

	}

	@Test(description = "On the link account page there should be the following text.", dependsOnMethods = {
			"ftueGoalPageheader" }, groups = { "DesktopBrowsers" }, priority = 4, enabled = true)
	public void ftueGoalNoDataMessage1() {
		logger.info(" save for goal no data message ");
		Assert.assertEquals(getStart.noDataMessage3().getText().trim(),
				PropsUtil.getDataPropertyValue("SFGLandingNoDataMessage3").trim());
	}

	@Test(description = "If the user has no accounts linked, he should be shown the Link account screen.", dependsOnMethods = {
			"ftueGoalPageheader" }, groups = { "DesktopBrowsers" }, priority = 5, enabled = true)
	public void ftueGoalLinkAccountButton() {
		logger.info("link account button");
		Assert.assertTrue(getStart.sfgLinkAccount().isDisplayed());
	}

	@Test(description = "If the user has valid linked accounts, he should land on the FTUE flow when he navigates to the finapp for the first time.", dependsOnMethods = {
			"ftueGoalPageheader" }, groups = "DesktopBrowsers", priority = 6)
	public void addAcount() throws AWTException, InterruptedException {
		logger.info("after adding account verify goal Start message");
		logger.info(
				"If the user links valid accounts, he should be navigated to the FTUE flow when he navigates to the finapp for the first time.");
		getStart.getStartAddAccount();
		accountAdd.addAggregatedAccount("renuka21.site16441.2", "site16441.2");
		PageParser.forceNavigate("SFG", d);
		Assert.assertEquals(goalEdit.startGoalMessage().getText(), PropsUtil.getDataPropertyValue("startGoalMessage"));

	}

	@Test(description = "The UI should show the user the step headers.", dependsOnMethods = {
			"addAcount" }, groups = "DesktopBrowsers", priority = 7)
	public void StartGoal3steps() throws AWTException, InterruptedException {
		logger.info("verifying all t3 steps in goal");
		String expectedStep[] = PropsUtil.getDataPropertyValue("startGoal3Steps").split(",");
		for (int i = 0; i < goalEdit.startGoal3steps().size(); i++) {
			Assert.assertEquals(goalEdit.startGoal3steps().get(i).getText(), expectedStep[i]);
		}
	}

	@Test(description = "verifying all 3 steps Images in goal", dependsOnMethods = {
			"addAcount" }, groups = "DesktopBrowsers", priority = 8)
	public void StartGoal3stepsImages() throws AWTException, InterruptedException {
		logger.info("verifying all 3 steps Images in goal");

		for (int i = 0; i < goalEdit.startGoal3stepsImages().size(); i++) {
			Assert.assertTrue(goalEdit.startGoal3stepsImages().get(i).isDisplayed());
		}
	}

	@Test(description = "The FTUE page should show the user a summary of what SFG is about.", dependsOnMethods = {
			"addAcount" }, groups = "DesktopBrowsers", priority = 9)
	public void StartGoalstep1info() throws AWTException, InterruptedException {
		logger.info("AT-58628:The text on the page should be consistent with the specs.");
		logger.info("AT-58631:The text under Step 1 should be");
		Assert.assertEquals(goalEdit.startGoal3stepsInfo().get(0).getText(),
				PropsUtil.getDataPropertyValue("StartGaolStep1info"));
	}

	@Test(description = "The text under Step 2 should be", dependsOnMethods = {
			"addAcount" }, groups = "DesktopBrowsers", priority = 10)
	public void StartGoalstep2info() throws AWTException, InterruptedException {
		Assert.assertEquals(goalEdit.startGoal3stepsInfo().get(1).getText(),
				PropsUtil.getDataPropertyValue("StartGaolStep2info"));
	}

	@Test(description = "The text under Step 3 should be", dependsOnMethods = {
			"addAcount" }, groups = "DesktopBrowsers", priority = 11)
	public void StartGoalstep3info() throws AWTException, InterruptedException {
		Assert.assertEquals(goalEdit.startGoal3stepsInfo().get(2).getText(),
				PropsUtil.getDataPropertyValue("StartGaolStep3info"));
	}

	@Test(description = "Verify get start button", dependsOnMethods = {
			"addAcount" }, groups = "DesktopBrowsers", priority = 12)
	public void getStartButton() throws AWTException, InterruptedException {
		logger.info("get start button");
		Assert.assertTrue(goalEdit.startGoalGetStartButton().isDisplayed());

	}

	@Test(description = "If the user clicks on the Get Started button, he should be taken to the HLC page.", dependsOnMethods = {
			"addAcount" }, groups = "DesktopBrowsers", priority = 13)
	public void getStartButtonClick() throws AWTException, InterruptedException {
		logger.info("AT-58641:On clicking on an HLC, the text should be shown.");
		logger.info("get start click ");
		SeleniumUtil.click(goalEdit.startGoalGetStartButton());
		SeleniumUtil.waitForPageToLoad();
		Assert.assertEquals(goalEdit.SFGcategoryHeading().getText(),
				PropsUtil.getDataPropertyValue("SFGcategoryHeading"));
	}

	@Test(description = "High Level Goal Categories should be mapped to Low Level Goal categories.", dependsOnMethods = {
			"getStartButtonClick" }, groups = "DesktopBrowsers", priority = 14)
	public void verifyHighLevelcategory() throws AWTException, InterruptedException {
		logger.info("validating all high lever categories");
		logger.info("AT-19593:Low level names are displayed on the high level cards if not selected.");
		logger.info("AT-19595:By default all high level category cards should be collapsed.");
		logger.info("AT-58639:The functionalities should remain the same on clicking on any HLC");
		logger.info("AT-58643:All the default goal names should be the same as what was there previously.");
		// SeleniumUtil.click(getStart.getStartButton());
		SeleniumUtil.waitForPageToLoad(3000);
		String expectedcategories[] = PropsUtil.getDataPropertyValue("SFGHighLevelGoal").split(",");
		for (int i = 0; i < getStart.SFGhighLevlcategories().size(); i++) {
			Assert.assertEquals(getStart.SFGhighLevlcategories().get(i).getText(), expectedcategories[i]);
		}
	}

	@Test(description = "verify high level categories sub content", dependsOnMethods = {
			"getStartButtonClick" }, groups = "DesktopBrowsers", priority = 15)
	public void verifyHighLevelcategorySubContent() throws AWTException, InterruptedException {
		logger.info("high level categories sub content");
		Assert.assertEquals(getStart.SFGhighLevlcategoriesSubContent().get(0).getText(),
				PropsUtil.getDataPropertyValue("SFGHighLevelGoalSubContent1"));
		Assert.assertEquals(getStart.SFGhighLevlcategoriesSubContent().get(1).getText(),
				PropsUtil.getDataPropertyValue("SFGHighLevelGoalSubContent2"));
		Assert.assertEquals(getStart.SFGhighLevlcategoriesSubContent().get(2).getText(),
				PropsUtil.getDataPropertyValue("SFGHighLevelGoalSubContent3"));
	}

	@Test(description = "On clicking a High level Goal, the user should only be able to see the related low level Goal categories.", dependsOnMethods = {
			"getStartButtonClick" }, groups = "DesktopBrowsers", priority = 16)
	public void verifyHighLevelcategory1SubCat() throws AWTException, InterruptedException {
		logger.info("verify sub categories for high level category1");
		logger.info("AT-58644:On clicking on an opened HLC, the HLC should collapse.");
		logger.info("AT-58651On clicking on any goal, it should behave the same as it was previously");
		logger.info("AT-58653:All the groups should be the same as they were previously");
		getStart.SFGhighLevlcategories().get(0).click();
		SeleniumUtil.waitForPageToLoad(5000);
		String expectedLowLevelcategories[] = PropsUtil.getDataPropertyValue("SFGHifhlevel1SubCat").split(",");
		for (int i = 0; i < getStart.getSubCatText().size(); i++) {
			Assert.assertEquals(getStart.getSubCatText().get(i).getText(), expectedLowLevelcategories[i]);
		}
	}

	@Test(description = "The user should have an option to go back to the high level categories.", dependsOnMethods = {
			"getStartButtonClick" }, groups = "DesktopBrowsers", priority = 17)
	public void verifyHighLevelcategory1CloseIcon() throws AWTException, InterruptedException {
		logger.info("verify close button for high level category1");
		logger.info(
				"AT-19594:When a high level card is selected the low level names within it gets replaced by a cross symbol");
		Assert.assertTrue(getStart.closeHighLevelCat().get(0).isDisplayed());
	}

	@Test(description = "Verify verify sub categories for high level category2", dependsOnMethods = {
			"getStartButtonClick" }, groups = "DesktopBrowsers", priority = 18)
	public void verifyHighLevelcategory2SubCat() throws AWTException, InterruptedException {
		logger.info("verify sub categories for high level category2");

		SeleniumUtil.click(getStart.closeHighLevelCat().get(0));
		SeleniumUtil.waitForPageToLoad(5000);
		SeleniumUtil.click(getStart.SFGhighLevlcategories().get(1));
		SeleniumUtil.waitForPageToLoad(5000);
		String expectedLowLevelcategories[] = PropsUtil.getDataPropertyValue("SFGHifhlevel2SubCat").split(",");
		for (int i = 0; i < getStart.getSubCatText().size(); i++) {
			Assert.assertEquals(getStart.getSubCatText().get(i).getText(), expectedLowLevelcategories[i]);
		}
	}

	@Test(description = "verify close icon for high level category2", dependsOnMethods = {
			"verifyHighLevelcategory2SubCat" }, groups = "DesktopBrowsers", priority = 19)
	public void verifyHighLevelcategory3CloseIcon() throws AWTException, InterruptedException {
		Assert.assertTrue(getStart.closeHighLevelCat().get(1).isDisplayed());
	}

	@Test(description = "verify sub categories for high level category3", dependsOnMethods = {
			"verifyHighLevelcategory2SubCat" }, groups = "DesktopBrowsers", priority = 20)
	public void verifyHighLevelcategory3SubCat() throws AWTException, InterruptedException {
		logger.info("verify sub categories for high level category3");

		SeleniumUtil.click(getStart.closeHighLevelCat().get(1));
		SeleniumUtil.waitForPageToLoad(6000);
		getStart.SFGhighLevlcategories().get(2).click();
		SeleniumUtil.waitForPageToLoad(5000);
		String expectedLowLevelcategories[] = PropsUtil.getDataPropertyValue("SFGHifhlevel3SubCat").split(",");
		for (int i = 0; i < getStart.getSubCatText().size(); i++) {
			Assert.assertEquals(getStart.getSubCatText().get(i).getText(), expectedLowLevelcategories[i]);
		}
	}

	@Test(description = "verify close icon for high level category3", dependsOnMethods = {
			"verifyHighLevelcategory3SubCat" }, groups = "DesktopBrowsers", priority = 21)
	public void verifyHighLevelcategory2CloseIcon() throws AWTException, InterruptedException {
		logger.info("verify sub categories for high level category2");
		Assert.assertTrue(getStart.closeHighLevelCat().get(2).isDisplayed());
	}

	@AfterClass
	public void checkAccessibility() {
		try {
			RunAccessibilityTest rat = new RunAccessibilityTest(this.getClass().getName());
			rat.testAccessibility(d);

		} catch (Exception e) {

		}
	}

}
