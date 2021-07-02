/*******************************************************************************
 * Copyright (c) 2018 Yodlee Inc. All Rights Reserved.
 * 
 * This software is the confidential and proprietary information of
 * Yodlee, Inc. Use is subject to license terms.
 * 
 * @author sbhat1
 ******************************************************************************/
package com.omni.pfm.SFG;

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
import com.omni.pfm.pages.Login.L1NodeLogin;
import com.omni.pfm.pages.Login.LoginPage;
import com.omni.pfm.pages.SFG.SFG_CreateGoal_GetStarted_Loc;
import com.omni.pfm.pages.SFG.SFG_LandingPage_Loc;
import com.omni.pfm.pages.SFG.SFG_createGoalEdit_Loc;
import com.omni.pfm.testBase.TestBase;
import com.omni.pfm.utility.PropsUtil;
import com.omni.pfm.utility.SeleniumUtil;

public class SFG_DashBoard_Integeration extends TestBase {
	private static final Logger logger = LoggerFactory.getLogger(SFG_DashBoard_Integeration.class);

	SFG_LandingPage_Loc SFG;
	SFG_createGoalEdit_Loc goalEdit;
	SFG_CreateGoal_GetStarted_Loc getStart;
	AccountAddition accountAdd;

	@BeforeClass(alwaysRun = true)
	public void testInit() throws Exception {
		doInitialization("SFG_DashBoard_Integeration");
		SFG = new SFG_LandingPage_Loc(d);

		goalEdit = new SFG_createGoalEdit_Loc(d);
		getStart = new SFG_CreateGoal_GetStarted_Loc(d);
		new L1NodeLogin();
		accountAdd = new AccountAddition();
		d.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);

		LoginPage.loginMain(d, loginParameter);
		SeleniumUtil.refresh(d);
		SeleniumUtil.waitForPageToLoad(2000);

		accountAdd.linkAccount();
		accountAdd.addAggregatedAccount("renuka21.site16441.2", "site16441.2");

	}

	@Test(description = "Save for a Goal' should be shown as the header.", priority = 2)
	public void verifSFGHeaderIndashBorad() throws Exception {
		SeleniumUtil.refresh(d);
		SeleniumUtil.waitForPageToLoad(7000);

		Assert.assertEquals(SFG.SFGDashBoardHeader().getText(), PropsUtil.getDataPropertyValue("SFGDashBoardHeader"));

	}

	@Test(description = "When the user has no goals, the dashboard should show the user the empty state experience.", dependsOnMethods = {
			"verifSFGHeaderIndashBorad" }, priority = 3)
	public void verifSFGDashBoardInfomeasage() throws Exception {
		Assert.assertTrue(
				SFG.SFGDashBoardMessage().getText().contains(PropsUtil.getDataPropertyValue("SFGDashBoardMessage1")));
		Assert.assertTrue(
				SFG.SFGDashBoardMessage().getText().contains(PropsUtil.getDataPropertyValue("SFGDashBoardMessage2")));

	}

	@Test(description = "If the user clicks on the Get Started button, the user should be taken to the SFG finapp.", dependsOnMethods = {
			"verifSFGHeaderIndashBorad" }, priority = 4)
	public void verifSFGDashBoardLetsGoButton() throws Exception {

		Assert.assertTrue(SFG.SFGDashBoardLetsGoButton().isDisplayed());
	}

	@Test(description = "If the user navigates to SFG from the dashboard, he should be shown the 'Return to dashboard' button.", dependsOnMethods = {
			"verifSFGHeaderIndashBorad" }, priority = 5)
	public void verifSFGDashBoardBackTodashBoard() throws Exception {
		SeleniumUtil.click(SFG.SFGDashBoardLetsGoButton());
		SeleniumUtil.waitForPageToLoad();
		if (PageParser.isElementPresent("backToDashBoardIconForMobile", "SFG", null)) {
			Assert.assertTrue(SFG.verifyBackToDashBoardIcon().isDisplayed());
		} else {
			Assert.assertTrue(SFG.SFGDashBoardBackTodashBoardIcon().isDisplayed());
			Assert.assertEquals(SFG.SFGDashBoardBackTodashBoardLink().getText(),
					PropsUtil.getDataPropertyValue("SFGDashBoardBackSFG"));
		}
	}

	@Test(description = "If the user clicks on the Get Started button and if he has no goals, he should be taken to the FTUE page.", dependsOnMethods = {
			"verifSFGDashBoardBackTodashBoard" }, priority = 6)
	public void verifSFGDashBoardBackTodashBoardInGetStart() throws Exception {
		Assert.assertTrue(goalEdit.startGoalGetStartButton().isDisplayed());
	}

	@Test(description = "verify BackTo  in Dahs Board click", dependsOnMethods = { "verifSFGDashBoardBackTodashBoard" }, priority = 7)
	public void verifSFGDashBoardBackTodashBoardClick() throws Exception {
		if (PageParser.isElementPresent("backToDashBoardIconForMobile", "SFG", null)) {
			SeleniumUtil.click(SFG.verifyBackToDashBoardIcon());
		} else {
			SeleniumUtil.click(SFG.SFGDashBoardBackTodashBoardLink());
		}
		SeleniumUtil.waitForPageToLoad();
		Assert.assertEquals(SFG.SFGDashBoardHeader().getText(), PropsUtil.getDataPropertyValue("SFGDashBoardHeader"));
	}

	@Test(description = "If the user clicks on the Get Started button and if he has goals, he should land on the 'Goals' page.", dependsOnMethods = {
			"verifSFGDashBoardBackTodashBoardClick" }, priority = 8)
	public void verifSFGDashBoardGetStartclick() throws Exception {
		SeleniumUtil.click(SFG.SFGDashBoardLetsGoButton());
		SeleniumUtil.waitForPageToLoad();
		SeleniumUtil.click(goalEdit.startGoalGetStartButton());
		SeleniumUtil.waitForPageToLoad();
		if (PageParser.isElementPresent("backToDashBoardIconForMobile", "SFG", null)) {
			Assert.assertTrue(SFG.verifyBackToDashBoardIcon().isDisplayed());
		} else {
			Assert.assertTrue(SFG.SFGDashBoardBackTodashBoardIcon().isDisplayed());
			Assert.assertEquals(SFG.SFGDashBoardBackTodashBoardLink().getText(),
					PropsUtil.getDataPropertyValue("SFGDashBoardBackSFG"));
		}

	}

	@Test(description = "verify   Category heading when you click get start button", dependsOnMethods = {
			"verifSFGDashBoardGetStartclick" }, priority = 9)
	public void verifSFGDashBoardGetStartclickLandingPage() throws Exception {
		Assert.assertEquals(goalEdit.SFGcategoryHeading().getText(),
				PropsUtil.getDataPropertyValue("SFGcategoryHeading"));
	}

	@Test(description = "verify   back to dashboard when you click get start button ", dependsOnMethods = {
			"verifSFGDashBoardGetStartclick" }, priority = 10)
	public void verifSFGDashBoardClickBackToDashBoardInCatPage() throws Exception {
		if (PageParser.isElementPresent("backToDashBoardIconForMobile", "SFG", null)) {
			SeleniumUtil.click(SFG.verifyBackToDashBoardIcon());
		} else
			SeleniumUtil.click(SFG.SFGDashBoardBackTodashBoardLink());
		SeleniumUtil.waitForPageToLoad();
		Assert.assertEquals(SFG.SFGDashBoardHeader().getText(), PropsUtil.getDataPropertyValue("SFGDashBoardHeader"));
	}

	@Test(description = "If the user has only Draft goals, the widget should show him the 'View all goals' option.", dependsOnMethods = {
			"verifSFGDashBoardClickBackToDashBoardInCatPage" }, priority = 11)
	public void verifSFGDashBoardDraftGoalverification() throws Exception {
		logger.info(
				"The text on the 'View all Goals' card should be View the rest of your active goals or simply start a new goal today!");
		logger.info(
				"If the user has valid linked accounts and has completed the FTUE flow, on clicking on the Dashboard, he should be navigated to the HLC page if he has no goals.");
		PageParser.forceNavigate("SFG", d);
		SeleniumUtil.waitForPageToLoad(7000);
		SFG.step2LandingPage(PropsUtil.getDataPropertyValue("SFGGoalName1"),
				PropsUtil.getDataPropertyValue("SFGGoalAmount1"), 0,
				PropsUtil.getDataPropertyValue("SFGFrequencyAmount1"));
		SeleniumUtil.waitForPageToLoad();
		PageParser.forceNavigate("SFG", d);
		SeleniumUtil.waitForPageToLoad();
		SeleniumUtil.click(SFG.SFgDraftGoalSaveMyProgress());
		SeleniumUtil.waitForPageToLoad();
		SeleniumUtil.refresh(d);
		SeleniumUtil.waitForPageToLoad();
		Assert.assertEquals(SFG.SFGDashBoardViewAllGoals().getText(),
				PropsUtil.getDataPropertyValue("SFGDashBoardViewAllGoals"));
		Assert.assertEquals(SFG.SFGDashBoardMessage().getText(),
				PropsUtil.getDataPropertyValue("SFGDashBoardDraftGoalMessage"));

	}

	@Test(description = "verify  progress goals should displayed when you click view all goals from Dash board ", dependsOnMethods = {
			"verifSFGDashBoardDraftGoalverification" }, priority = 12)
	public void verifSFGDashBoardClikcViewAllForDraft() throws Exception {
		SeleniumUtil.click(SFG.SFGDashBoardViewAllGoals());
		SeleniumUtil.waitForPageToLoad();
		boolean expectedGoalName = false;
		for (int i = 0; i < SFG.SFGDraftGoalNames().size(); i++) {
			if (SFG.SFGDraftGoalNames().get(i).getText().trim()
					.equals(PropsUtil.getDataPropertyValue("SFGGoalName1").trim())) {
				expectedGoalName = true;
				break;
			}

		}
		Assert.assertTrue(expectedGoalName);

	}

	@Test(description = "verify back to dash board when you click on view all goals from dash board ", dependsOnMethods = {
			"verifSFGDashBoardClikcViewAllForDraft" }, priority = 13)
	public void verifSFGDashBoardInGoalSummeryPage() throws Exception {
		if (PageParser.isElementPresent("backToDashBoardIconForMobile", "SFG", null)) {
			Assert.assertTrue(SFG.verifyBackToDashBoardIcon().isDisplayed());
		} else {
			Assert.assertTrue(SFG.SFGDashBoardBackTodashBoardIcon().isDisplayed());
			Assert.assertEquals(SFG.SFGDashBoardBackTodashBoardLink().getText(),
					PropsUtil.getDataPropertyValue("SFGDashBoardBackSFG"));
		}

	}

	@Test(description = "verify  sfg header in dash board when you click on backtodashboard", dependsOnMethods = {
			"verifSFGDashBoardClikcViewAllForDraft" }, priority = 14)
	public void verifSFGDashBoardClickBackToDashInSummeryPage() throws Exception {
		if (PageParser.isElementPresent("backToDashBoardIconForMobile", "SFG", null)) {
			SeleniumUtil.click(SFG.verifyBackToDashBoardIcon());
		} else {
			SeleniumUtil.click(SFG.SFGDashBoardBackTodashBoardLink());
		}
		SeleniumUtil.waitForPageToLoad();
		Assert.assertEquals(SFG.SFGDashBoardHeader().getText(), PropsUtil.getDataPropertyValue("SFGDashBoardHeader"));
	}

	@Test(description = "verify inprogress goal in Dash board", dependsOnMethods = { "verifSFGDashBoardClickBackToDashInSummeryPage" }, priority = 15)
	public void verifSFGDashBoardCreateInProgressGoal() throws Exception {
		PageParser.forceNavigate("SFG", d);
		SeleniumUtil.click(SFG.SFGDraftGoalNames().get(0));
		SeleniumUtil.waitForPageToLoad();
		SFG.createPeogressGoalFromStep2(PropsUtil.getDataPropertyValue("SFGOnetimeFunding"));
		SeleniumUtil.waitForPageToLoad();
		SeleniumUtil.refresh(d);
		SeleniumUtil.waitForPageToLoad();
		Assert.assertEquals(SFG.SFGDashBoardGoalName().get(0).getText().trim(),
				PropsUtil.getDataPropertyValue("SFGGoalName1").trim());

	}

	@Test(description = "verify circle icon is displying in dash baord", dependsOnMethods = { "verifSFGDashBoardCreateInProgressGoal" }, priority = 16)
	public void verifSFGDashBoardTwoCircle() throws Exception {
		Assert.assertTrue(SFG.SFGDashBoardCircleSymbol().size() == 2);
	}

	@Test(description = "verify goal summery page is displying when you click on inprogress goal in dashboard", dependsOnMethods = {
			"verifSFGDashBoardCreateInProgressGoal" }, priority = 17)
	public void verifSFGDashBoardClickOnInProgressGoal() throws Exception {
		SeleniumUtil.click(SFG.SFGDashBoardGoalName().get(0));
		SeleniumUtil.waitForPageToLoad();
		Assert.assertEquals(SFG.GoalHeaderInsteps().getText().trim(),
				PropsUtil.getDataPropertyValue("SFGDashBoardGoalSummeryText").trim());
	}

	@Test(description = "verify Backtodashboard on goal summery page when you click on inprogress goal in dashboard", dependsOnMethods = {
			"verifSFGDashBoardClickOnInProgressGoal" }, priority = 18)
	public void verifSFGDashBoardBackToDashInProgressGoalSummery() throws Exception {
		if (PageParser.isElementPresent("backToDashBoardSummaryIconForMobile", "SFG", null)) {
			Assert.assertTrue(SFG.verifyBackToDashBoardSummaryIcon().isDisplayed());
		} else {
			Assert.assertTrue(SFG.SFGDashBoardBackTodashBoardIconSummery().isDisplayed());
			Assert.assertEquals(SFG.SFGDashBoardBackTodashBoardLinkSummery().getText(),
					PropsUtil.getDataPropertyValue("SFGDashBoardBackSFG"));
		}
	}

	@Test(description = "verify SFg headerin dash board  when you click on backtodashboard in goal summery page", dependsOnMethods = {
			"verifSFGDashBoardClickOnInProgressGoal" }, priority = 19)
	public void verifSFGDashClickBoardBackToDashInProgressGoalSummery() throws Exception {
		if (PageParser.isElementPresent("backToDashBoardSummaryIconForMobile", "SFG", null)) {
			SeleniumUtil.click(SFG.verifyBackToDashBoardSummaryIcon());
		} else {
			SeleniumUtil.click(SFG.SFGDashBoardBackTodashBoardLinkSummery());
		}
		SeleniumUtil.waitForPageToLoad();
		Assert.assertEquals(SFG.SFGDashBoardHeader().getText(), PropsUtil.getDataPropertyValue("SFGDashBoardHeader"));
	}

	@Test(description = "All the cards should be clickable and should navigate to the correct page when clicked on.", dependsOnMethods = {
			"verifSFGDashClickBoardBackToDashInProgressGoalSummery" }, priority = 20)
	public void verifSFGDashViewMoreGoalsInProgressGoalSummery() throws Exception {
		logger.info("AT-58712:On clicking on a card, the user should be navigated to the Summary page.");
		SeleniumUtil.click(SFG.SFGDashBoardGoalName().get(0));
		SeleniumUtil.waitForPageToLoad();
		Assert.assertEquals(SFG.SFGBackButton().getText(), PropsUtil.getDataPropertyValue("SFGDashBoardViewAllGoals"));

	}

	@Test(description = "verify goal progress page when you clikc view all goals from goal summery page", dependsOnMethods = {
			"verifSFGDashViewMoreGoalsInProgressGoalSummery" }, priority = 21)
	public void verifSFGDashClickViewMoreGoalsInProgressGoalSummery() throws Exception {
		SeleniumUtil.click(SFG.SFGBackButton());
		SeleniumUtil.waitForPageToLoad();
		boolean expectedGoalcards = false;
		for (int i = 0; i < SFG.SFGInprogressGoalNames().size(); i++) {
			if (SFG.SFGInprogressGoalNames().get(i).getText().trim()
					.equals(PropsUtil.getDataPropertyValue("SFGGoalName1").trim())) {
				expectedGoalcards = SFG.SFGInprogressGoalCards().get(i).isDisplayed();
				break;
			}

		}
		Assert.assertTrue(expectedGoalcards);
	}

	@Test(description = "verify back to dash board should not be displayed when click view all goals from goal summery page", dependsOnMethods = {
			"verifSFGDashClickViewMoreGoalsInProgressGoalSummery" }, priority = 22)
	public void verifSFGDashVrifyBackToGoalInProgressInGoalPage() throws Exception {
		if (PageParser.isElementPresent("backToDashBoardIconForMobile", "SFG", null)) {
			Assert.assertNull(SFG.verifyBackToDashBoardIcon());
		} else {
			Assert.assertTrue(SFG.isElementPresent(SFG.SFGDashBoardBackTodashBoardLink()));
		}
	}

	@Test(description = "If the user has only In Progress goals, the widget should show him the 'View all goals' option at the end.",  priority = 23)
	public void verifSFGDashBoardNextButton() throws Exception {
		logger.info("AT-58699:The transition from one card to another should be smooth");
		SeleniumUtil.refresh(d);
		SeleniumUtil.waitForPageToLoad();
		SeleniumUtil.click(SFG.SFGDashBoardNextIcon());
		SeleniumUtil.waitForPageToLoad();
		Assert.assertEquals(SFG.SFGDashBoardViewAllGoalsInLastPAge().getText(),
				PropsUtil.getDataPropertyValue("SFGDashBoardViewAllGoals"));
		Assert.assertTrue(SFG.SFGDashBoardMessage().getText()
				.contains(PropsUtil.getDataPropertyValue("SFGDashBoardDraftGoalMessage")));

	}

	@Test(description = "On clicking on the 'View All Goals' button, the user should land on the my goals page", dependsOnMethods = {
			"verifSFGDashBoardNextButton" }, priority = 24)
	public void verifSFGDashClickViewMoreGoalsInLastPage() throws Exception {
		SeleniumUtil.click(SFG.SFGDashBoardViewAllGoalsInLastPAge());
		SeleniumUtil.waitForPageToLoad();
		boolean expectedGoalcards = false;
		for (int i = 0; i < SFG.SFGInprogressGoalNames().size(); i++) {
			if (SFG.SFGInprogressGoalNames().get(i).getText().trim()
					.equals(PropsUtil.getDataPropertyValue("SFGGoalName1").trim())) {
				expectedGoalcards = SFG.SFGInprogressGoalCards().get(i).isDisplayed();
				SeleniumUtil.click(SFG.SFGInprogressGoalNames().get(i));
				break;
			}
		}
		Assert.assertTrue(expectedGoalcards);
	}

	@Test(description = "If the user has only Completed goals, the widget should show him the 'View all goals' option.", dependsOnMethods = {
			"verifSFGDashClickViewMoreGoalsInLastPage" }, priority = 25)
	public void verifSFGDashVerifyIncomepleteGoal() throws Exception {
		logger.info(
				"AT-58708:If the user has only Completed goals, the widget should show him the 'View all goals' option. On which the text should be 'You have money tied to your completed goals. Revisit them or simply start a new goal!'");
		SeleniumUtil.waitForPageToLoad();

		SeleniumUtil.click(SFG.SFGCmpleteGoalButton());
		SeleniumUtil.waitForPageToLoad();
		SeleniumUtil.click(SFG.SFGCmpleteGoalConfirmationButtonButton());
		SeleniumUtil.waitForPageToLoad();
		SeleniumUtil.refresh(d);
		SeleniumUtil.waitForPageToLoad();
		Assert.assertEquals(SFG.SFGDashBoardMessage().getText(),
				PropsUtil.getDataPropertyValue("SFGdashBoardCompletedGoalMesage"));
		Assert.assertEquals(SFG.SFGDashBoardViewAllGoals().getText(),
				PropsUtil.getDataPropertyValue("SFGDashBoardCompleteGoalButton"));

	}

	@Test(description = "If the user has only Victory goals, he should be shown the 'Lets Go' button.", dependsOnMethods = {
			"verifSFGDashVerifyIncomepleteGoal" }, priority = 26)
	public void verifSFGDashVictoryGoal() throws Exception {
		SeleniumUtil.click(SFG.SFGDashBoardViewAllGoals());
		SeleniumUtil.waitForPageToLoad();
		SeleniumUtil.click(SFG.SFGCmpleteGoalName().get(0));
		SeleniumUtil.waitForPageToLoad();
		SeleniumUtil.click(SFG.SFGMarkAsCmpleteButton());
		SeleniumUtil.waitForPageToLoad(6000);
		SeleniumUtil.click(SFG.SFGMarkAsCmpletePopUpButton());
		SeleniumUtil.waitForPageToLoad();
		SeleniumUtil.refresh(d);
		SeleniumUtil.waitForPageToLoad();
		Assert.assertTrue(SFG.SFGDashBoardLetsGoButton().isDisplayed());

	}

	@Test(description = "On clicking on the 'Get Started' button, the user should be navigated to the FTUE flow.", dependsOnMethods = {
			"verifSFGDashVictoryGoal" }, priority = 27)
	public void verifSFGDashVerifyVictoryGoalLandingPage() throws Exception {
		SeleniumUtil.click(SFG.SFGDashBoardLetsGoButton());
		SeleniumUtil.waitForPageToLoad();
		Assert.assertTrue(goalEdit.startGoalGetStartButton().isDisplayed());

	}

	@Test(description = "The maximum number of carousals that can be shown on the widget is 6.", dependsOnMethods = {
			"verifSFGDashVerifyVictoryGoalLandingPage" }, priority = 28)
	public void verifSFGDashVerify6InInprogressGoal() throws Exception {
		logger.info("AT-58696:There should be a carousal shown on the UI");
		PageParser.forceNavigate("SFG", d);
		SeleniumUtil.waitForPageToLoad();
		SFG.CreateGoalWithallStepsFrequencyGoal(PropsUtil.getDataPropertyValue("SFGGoalName2"),
				PropsUtil.getDataPropertyValue("SFGGoalAmount1"), 0,
				PropsUtil.getDataPropertyValue("SFGFrequencyAmount1"),
				PropsUtil.getDataPropertyValue("SFGOnetimeFunding2"),
				PropsUtil.getDataPropertyValue("SFGFundingAmountValueUpdate2"));
		SeleniumUtil.waitForPageToLoad();
		SFG.CreateGoalWithallStepsFrequencyGoal(PropsUtil.getDataPropertyValue("SFGGoalName3"),
				PropsUtil.getDataPropertyValue("SFGGoalAmount1"), 0,
				PropsUtil.getDataPropertyValue("SFGFrequencyAmount1"),
				PropsUtil.getDataPropertyValue("SFGOnetimeFunding2"),
				PropsUtil.getDataPropertyValue("SFGFundingAmountValueUpdate2"));
		SeleniumUtil.waitForPageToLoad();
		SFG.CreateGoalWithallStepsFrequencyGoal(PropsUtil.getDataPropertyValue("SFGGoalName4"),
				PropsUtil.getDataPropertyValue("SFGGoalAmount1"), 0,
				PropsUtil.getDataPropertyValue("SFGFrequencyAmount1"),
				PropsUtil.getDataPropertyValue("SFGOnetimeFunding2"),
				PropsUtil.getDataPropertyValue("SFGFundingAmountValueUpdate2"));
		SeleniumUtil.waitForPageToLoad();
		SFG.CreateGoalWithallStepsFrequencyGoal(PropsUtil.getDataPropertyValue("SFGGoalName5"),
				PropsUtil.getDataPropertyValue("SFGGoalAmount1"), 0,
				PropsUtil.getDataPropertyValue("SFGFrequencyAmount1"),
				PropsUtil.getDataPropertyValue("SFGOnetimeFunding2"),
				PropsUtil.getDataPropertyValue("SFGFundingAmountValueUpdate2"));
		SeleniumUtil.waitForPageToLoad();
		SFG.CreateGoalWithallStepsFrequencyGoal(PropsUtil.getDataPropertyValue("SFGGoalName6"),
				PropsUtil.getDataPropertyValue("SFGGoalAmount1"), 0,
				PropsUtil.getDataPropertyValue("SFGFrequencyAmount1"),
				PropsUtil.getDataPropertyValue("SFGOnetimeFunding2"),
				PropsUtil.getDataPropertyValue("SFGFundingAmountValueUpdate2"));
		SeleniumUtil.waitForPageToLoad();
		SFG.CreateGoalWithallStepsFrequencyGoal(PropsUtil.getDataPropertyValue("SFGGoalName7"),
				PropsUtil.getDataPropertyValue("SFGGoalAmount1"), 0,
				PropsUtil.getDataPropertyValue("SFGFrequencyAmount1"),
				PropsUtil.getDataPropertyValue("SFGOnetimeFunding2"),
				PropsUtil.getDataPropertyValue("SFGFundingAmountValueUpdate2"));
		SeleniumUtil.waitForPageToLoad();
		SeleniumUtil.refresh(d);
		SeleniumUtil.waitForPageToLoad();
		Assert.assertTrue(SFG.SFGDashBoardCircleSymbol().size() == 6);
	}

	@Test(description = "By default, the user should be able to see a maximum of 5 goals in the same order that is seen on the Goals in Progress page..", dependsOnMethods = {
			"verifSFGDashVerify6InInprogressGoal" }, priority = 29)
	public void verifSFGDashVerify5InInprogressGoal() throws Exception {

		String expected[] = PropsUtil.getDataPropertyValue("SFGDashBoardNames").split(",");
		if(appFlag.equals("app") || appFlag.equals("emulator")) {
			List<WebElement> l = SFG.nextGoalButtonMobile();
			for (int i = 0; i < SFG.SFGDashBoardGoalName().size(); i++) {
				System.out.println(SFG.SFGDashBoardGoalName().get(i).getText().trim());
				Assert.assertEquals(SFG.SFGDashBoardGoalName().get(i).getText().trim(), expected[i]);
				if(l.get(i+1).isDisplayed()) {
					SeleniumUtil.click(l.get(i+1));
				}

			}
		} else {
			for (int i = 0; i < SFG.SFGDashBoardGoalName().size(); i++) {
				SeleniumUtil.scrollToWebElementMiddle(d, SFG.SFGDashBoardGoalName().get(i));
				System.out.println(SFG.SFGDashBoardGoalName().get(i).getText().trim());
				Assert.assertEquals(SFG.SFGDashBoardGoalName().get(i).getText().trim(), expected[i]);
				if (SFG.SFGDashBoardNextIcon().isDisplayed()) {
					SeleniumUtil.click(SFG.SFGDashBoardNextIcon());
					SeleniumUtil.waitForPageToLoad(500);
				}

			}
		} 
	}

	@Test(description = "There should be no arrow on the right side on the last card", dependsOnMethods = {
			"verifSFGDashVerify5InInprogressGoal" }, priority = 30)
	public void verifSFGDashNonextButtonIn5thStep() throws Exception {

		SeleniumUtil.click(SFG.SFGDashBoardCircleSymbol().get(4));
		SeleniumUtil.waitForPageToLoad();
		Assert.assertFalse(SFG.isElementPresent(SFG.SFGDashBoardNextIcon()));
	}

	@Test(description = "By default, on the 6th card on the carousal should give the user an option to navigate to the SFG goals in progress page.", dependsOnMethods = {
			"verifSFGDashNonextButtonIn5thStep" }, priority = 31)
	public void verifSFGDashClick6thCircle() throws Exception {
		logger.info("AT-58698:The carousal should not be clickable on mobiles and tablets.");
		logger.info(
				"AT-58700:When the user clicks on a carousal button, the transition should be the same as if he is clicking on the arrows.");

		SeleniumUtil.click(SFG.SFGDashBoardViewAllGoalsInLastPAge());
		SeleniumUtil.waitForPageToLoad();
		boolean expectedGoalcards = false;
		for (int i = 0; i < SFG.SFGInprogressGoalNames().size(); i++) {
			if (SFG.SFGInprogressGoalNames().get(i).getText().trim()
					.equals(PropsUtil.getDataPropertyValue("SFGGoalName2").trim())) {
				expectedGoalcards = SFG.SFGInprogressGoalCards().get(i).isDisplayed();
				break;
			}
		}
		Assert.assertTrue(expectedGoalcards);
	}

	@Test(description = "verify the currency change error icon when you chnaged the currency $ to indian rupees", dependsOnMethods = {
			"verifSFGDashClick6thCircle" }, priority = 32)
	public void verifCurrencyChangedIcon() throws Exception {
		SeleniumUtil.refresh(d);
		SeleniumUtil.waitForPageToLoad(3000);
		SFG.changeCurrencyValue(7);
		PageParser.forceNavigate("SFG", d);
		SeleniumUtil.waitForPageToLoad(10000);
		SeleniumUtil.click(SFG.SFGInprogressGoalNames().get(0));
		SeleniumUtil.waitForPageToLoad(1000);
		Assert.assertTrue(SFG.SFGCurrencyMissMatchIcon().isDisplayed());

	}

	@Test(description = "verify the currency change error mressage title  when you chnaged the currency $ to indian rupees", dependsOnMethods = {
			"verifCurrencyChangedIcon" }, priority = 33)
	public void verifCurrencyChangedTitle() throws Exception {
		Assert.assertEquals(SFG.SFGCurrencyMissMatchTitle().getText(),
				PropsUtil.getDataPropertyValue("SFGCurrencyChangeTitle"));

	}

	@Test(description = "verify the currency change error mressage1  when you chnaged the currency $ to indian rupees", dependsOnMethods = {
			"verifCurrencyChangedIcon" }, priority = 34)
	public void verifCurrencyChangedMessage1() throws Exception {
		Assert.assertEquals(SFG.SFGCurrencyMissMatchMessage1().getText(),
				PropsUtil.getDataPropertyValue("SFGCurrencyChangeMessage1"));

	}

	@Test(description = "verify the currency change error mressage2  when you chnaged the currency $ to indian rupees", dependsOnMethods = {
			"verifCurrencyChangedIcon" }, priority = 35)
	public void verifCurrencyChangedMessage2() throws Exception {
		Assert.assertEquals(SFG.SFGCurrencyMissMatchMessage2().getText(),
				PropsUtil.getDataPropertyValue("SFGCurrencyChangeMessage2"));

	}

	@Test(description = "If there is a currency mismatch, the dashboard should show the users the goals as they would normally. The currency mismatch screen should only be shown after the user clicks on a goal.", dependsOnMethods = {
			"verifCurrencyChangedIcon" }, priority = 36)
	public void verifCurrencyChangedIconFrinDashBoard() throws Exception {
		SeleniumUtil.refresh(d);
		SeleniumUtil.click(SFG.SFGDashBoardGoalName().get(0));
		Assert.assertTrue(SFG.SFGCurrencyMissMatchIcon().isDisplayed());
		
	}

	@Test(description = "verify Step 1 currency change error mressage title  when you chnaged the currency $ to indian rupees", dependsOnMethods = {
			"verifCurrencyChangedIconFrinDashBoard" }, priority = 37)
	public void verifCurrencyChangedTitleInstep1() throws Exception {
		PageParser.forceNavigate("SFG", d);
		SeleniumUtil.waitForPageToLoad(5000);
		try {
			if (SFG.startGoalGetStartButton().isDisplayed()) {
				SeleniumUtil.click(SFG.startGoalGetStartButton());
			}
		} catch (Exception e) {
			SeleniumUtil.click(SFG.SFGCreateGoalButton());
		}
		SeleniumUtil.waitForPageToLoad(3000);
		SeleniumUtil.click(SFG.SFGhighLevlcategories().get(0));
		SeleniumUtil.waitForPageToLoad(3000);
		SeleniumUtil.click(SFG.getSubCatText().get(0));
		SeleniumUtil.waitForPageToLoad(3000);
		Assert.assertEquals(SFG.SFGCurrencyMissMatchTitleInStep1().getText(),
				PropsUtil.getDataPropertyValue("SFGCurrencyChangeTitleSTep1"));
	}

	@Test(description = "verify Step 1 currency change error mressage when you chnaged the currency $ to indian rupees", dependsOnMethods = {
			"verifCurrencyChangedTitleInstep1" }, priority = 38)
	public void verifCurrencyChangedMessageInstep1() throws Exception {
		Assert.assertTrue(SFG.SFGCurrencyMissMatchMessage1Instep1().getText()
				.contains(PropsUtil.getDataPropertyValue("SFGCurrencyChangeMessage2Step1")));
		Assert.assertTrue(SFG.SFGCurrencyMissMatchMessage1Instep1().getText()
				.contains(PropsUtil.getDataPropertyValue("SFGCurrencyChangeMessage3Step1")));
		Assert.assertTrue(SFG.SFGCurrencyMissMatchMessage1Instep1().getText()
				.contains(PropsUtil.getDataPropertyValue("SFGCurrencyChangeMessage4Step1")));

	}

	@Test(description = "verify Step 1 currency Link an account button when you chnaged the currency $ to indian rupees", dependsOnMethods = {
			"verifCurrencyChangedTitleInstep1" }, priority = 39)
	public void verifCurrencyChangedLinkAnAccountInstep1() throws Exception {
		Assert.assertEquals(SFG.SFGCurrencyMissMatchLinkAccountButton().getText(),
				PropsUtil.getDataPropertyValue("SFGCurrecnyChangeLinkAccountButtonText"));

	}

	@Test(description = "verify  summery page  when you reverted chnaged the currency $ to indian rupees", dependsOnMethods = {
			"verifCurrencyChangedTitleInstep1" }, priority = 40)
	public void verifProgressGoalCurrencyChangedReverted() throws Exception {
		SFG.changeCurrencyValue(13);
		PageParser.forceNavigate("SFG", d);
		SeleniumUtil.waitForPageToLoad(5000);
		SeleniumUtil.click(SFG.SFGInprogressGoalNames().get(0));
		SeleniumUtil.waitForPageToLoad(1000);
		Assert.assertEquals(SFG.GoalHeaderInsteps().getText().trim(),
				PropsUtil.getDataPropertyValue("SFGDashBoardGoalSummeryText").trim());

	}

	@Test(description = "verify  Step1 goal page  when you reverted chnaged the currency $ to indian rupees", dependsOnMethods = {
			"verifProgressGoalCurrencyChangedReverted" }, priority = 41)
	public void verifCreateGoalStepCurrencyChangedReverted() throws Exception {
		PageParser.forceNavigate("SFG", d);
		SeleniumUtil.waitForPageToLoad(5000);
		try {
			if (SFG.startGoalGetStartButton().isDisplayed()) {
				SeleniumUtil.click(SFG.startGoalGetStartButton());
			}
		} catch (Exception e) {
			SeleniumUtil.click(SFG.SFGCreateGoalButton());
		}
		SeleniumUtil.waitForPageToLoad(3000);
		SeleniumUtil.click(SFG.SFGhighLevlcategories().get(0));
		SeleniumUtil.waitForPageToLoad(3000);
		SeleniumUtil.click(SFG.getSubCatText().get(0));
		SeleniumUtil.waitForPageToLoad(3000);
		Assert.assertTrue(SFG.GoalNameInput().isDisplayed());
	}
}
