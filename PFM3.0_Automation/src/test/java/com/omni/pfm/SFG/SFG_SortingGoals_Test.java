/*******************************************************************************
 * Copyright (c) 2018 Yodlee Inc. All Rights Reserved.
 * 
 * This software is the confidential and proprietary information of
 * Yodlee, Inc. Use is subject to license terms.
 * 
 * @author sbhat1
 ******************************************************************************/
package com.omni.pfm.SFG;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.omni.pfm.PageProcessor.PageParser;
import com.omni.pfm.pages.AccountAddition.AccountAddition;
import com.omni.pfm.pages.Login.LoginPage;
import com.omni.pfm.pages.SFG.SortingGoals_Loc;
import com.omni.pfm.testBase.TestBase;
import com.omni.pfm.utility.PropsUtil;
import com.omni.pfm.utility.SeleniumUtil;

public class SFG_SortingGoals_Test extends TestBase {
	Logger logger = LoggerFactory.getLogger(SFG_SortingGoals_Test.class);
	SortingGoals_Loc SortingGoals;
	LoginPage login;
	AccountAddition accountAdd;

	@BeforeClass(alwaysRun = true)
	public void init() throws Exception {
		doInitialization("SFG Sorting Goals");
		login = new LoginPage();
		SortingGoals = new SortingGoals_Loc(d);
		accountAdd = new AccountAddition();

		/**
		 * Logging in and adding accounts
		 */
		LoginPage.loginMain(d, loginParameter);
		SeleniumUtil.waitForPageToLoad();
		accountAdd.linkAccount();
		accountAdd.addAggregatedAccount("renuka21.site16441.2", "site16441.2");
		SeleniumUtil.waitForPageToLoad();

	}

	@Test(description = "Creating 3 draft goal.", priority = 1, enabled = true)
	public void creatingDraftGoal() {
		SeleniumUtil.refresh(d);
		PageParser.forceNavigate("SFG", d);
		SeleniumUtil.waitForPageToLoad();

		SeleniumUtil.click(SortingGoals.startGoalGetStartButton());
		Assert.assertTrue(SortingGoals.step2LandingPage("GOALNAME52", "1300", 2, "100"));
		SeleniumUtil.click(SortingGoals.createGoalTab());

		Assert.assertTrue(SortingGoals.step2LandingPage("GOALNAME53", "1300", 2, "100"));
		SeleniumUtil.click(SortingGoals.createGoalTab());

		Assert.assertTrue(SortingGoals.step2LandingPage("GOALNAME54", "1300", 2, "100"));
	}

	@Test(description = "Verifying created draft goal should come under draft and showing count of created draft goal.", dependsOnMethods = {
			"creatingDraftGoal" }, priority = 2, enabled = true)
	public void verifyingCreatedDraftGoal() {
		Assert.assertEquals(SortingGoals.draftGoalCount().getText().trim(),
				PropsUtil.getDataPropertyValue("DraftHeaderCount").trim());
	}

	@Test(description = "verify view more button should not be there with three draft goals.", priority = 3, dependsOnMethods = {
			"creatingDraftGoal" }, enabled = true)
	public void verifyViewMoreVisibilityWith3DraftGoals() {
		Assert.assertTrue(SortingGoals.draftViewMoreButton().size() == 0);
	}

	@Test(description = "Verify Sequencing of added draft goals", priority = 4, dependsOnMethods = {
			"creatingDraftGoal" }, enabled = true)
	public void verifyDraftSequencing() {
		String expected[] = PropsUtil.getDataPropertyValue("DraftGoalSequence").split(",");
		for (int i = 0; i < SortingGoals.SFGDraftGoalNames().size(); i++) {
			String actual = SortingGoals.SFGDraftGoalNames().get(i).getText().trim();
			Assert.assertEquals(actual, expected[i]);
		}
	}

	@Test(description = "Creating one more draft goal", priority = 5, dependsOnMethods = {
			"creatingDraftGoal" }, enabled = true)
	public void creatingDraftGoal2() {
		SeleniumUtil.click(SortingGoals.createGoalTab());
		Assert.assertTrue(SortingGoals.step2LandingPage("GOALNAME55", "1300", 2, "100"));
		SeleniumUtil.waitForPageToLoad(2000);
	}

	@Test(description = "verify view more button should not be there with three draft goals.", priority = 6, dependsOnMethods = {
			"creatingDraftGoal2" }, enabled = true)
	public void verifyViewMoreVisibilityWith4Goals() {
		String expectedValue = PropsUtil.getDataPropertyValue("ActualViewMoreSize").trim();
		int expected = Integer.parseInt(expectedValue);
		int actual = SortingGoals.draftViewMoreButton().size();
		Assert.assertEquals(actual, expected);
	}

	@Test(description = "creating 3 inprogress goal.", priority = 7, dependsOnMethods = {
			"creatingDraftGoal2" }, enabled = true)
	public void creatingCompleteGoal() {

		SeleniumUtil.click(SortingGoals.createGoalTab());
		SeleniumUtil.waitForPageToLoad(2000);
		boolean goalOne = SortingGoals.createCompletedGoalwithSingleAcnt("GOALNAME56", "1300", "100", "2");

		SeleniumUtil.click(SortingGoals.createGoalTab());
		SeleniumUtil.waitForPageToLoad(2000);
		boolean goalTwo = SortingGoals.createCompletedGoalwithSingleAcnt("GOALNAME57", "1300", "100", "2");

		SeleniumUtil.click(SortingGoals.createGoalTab());
		SeleniumUtil.waitForPageToLoad(2000);
		boolean goalThree = SortingGoals.createCompletedGoalwithSingleAcnt("GOALNAME58", "1300", "100", "2");
		SeleniumUtil.waitForPageToLoad();
		SortingGoals.makingGoalAsCompleted();

		Assert.assertTrue(goalOne);
		Assert.assertTrue(goalTwo);
		Assert.assertTrue(goalThree);
	}

	@Test(description = "verifying completed goal should come under completed and showing count of completed goal.", dependsOnMethods = {
			"creatingCompleteGoal" }, priority = 8, enabled = true)
	public void verifyingCompletedGoal() {
		SeleniumUtil.waitForPageToLoad(1000);
		Assert.assertEquals(SortingGoals.CompletedGoalCount().getText().trim(),
				PropsUtil.getDataPropertyValue("CompletedHeaderCount").trim());
	}

	@Test(description = "verify view more button should not be there with three completed goals.", priority = 9, dependsOnMethods = {
			"creatingCompleteGoal" }, enabled = true)
	public void verifyViewMoreVisibilityWith3CompletedGoals() {
		Assert.assertTrue(SortingGoals.viewMoreButtonForCompleted().size() == 0);
	}

	@Test(description = "verify Sequencing of added completed goals", priority = 10, dependsOnMethods = {
			"creatingCompleteGoal" }, enabled = true)
	public void verifySequencing() {
		String expected[] = PropsUtil.getDataPropertyValue("CompletedGoalSequence").split(",");
		for (int i = 0; i < SortingGoals.SFGCompletedGoalNames().size(); i++) {
			String actual = SortingGoals.SFGCompletedGoalNames().get(i).getText().trim();
			Assert.assertEquals(actual, expected[i]);
		}
	}

	@Test(description = "creating 3 inprogress goal.", dependsOnMethods = {
			"creatingCompleteGoal" }, priority = 11, enabled = true)
	public void creatingCompletedGoal2() {

		SeleniumUtil.click(SortingGoals.createGoalTab());
		SeleniumUtil.waitForPageToLoad(2000);
		Assert.assertTrue(SortingGoals.createCompletedGoalwithSingleAcnt("GOALNAME59", "1300", "100", "2"));
		SeleniumUtil.waitForPageToLoad();
		SortingGoals.makingGoalAsCompleted();
	}

	@Test(description = "verify view more button should not be there with three draft goals.", dependsOnMethods = {
			"creatingCompletedGoal2" }, priority = 12, enabled = true)
	public void verifyViewMoreVisibilityWith4CompletedGoals() {
		String expectedValue = PropsUtil.getDataPropertyValue("ActualViewMoreSize").trim();
		int expected = Integer.parseInt(expectedValue);
		int actual = SortingGoals.viewMoreButtonForCompleted().size();
		Assert.assertEquals(actual, expected);
	}

	@Test(description = "creating 3 victory goals", dependsOnMethods = {
			"creatingCompletedGoal2" }, priority = 13, enabled = true)
	public void creatingVictoryGoals() {
		SeleniumUtil.waitForPageToLoad(1000);
		SeleniumUtil.click(SortingGoals.createGoalTab());
		SeleniumUtil.waitForPageToLoad(1000);
		Assert.assertTrue(SortingGoals.createCompletedGoalwithSingleAcnt("GOALNAME60", "1300", "100", "2"));

		SeleniumUtil.click(SortingGoals.createGoalTab());
		SeleniumUtil.waitForPageToLoad(1000);
		Assert.assertTrue(SortingGoals.createCompletedGoalwithSingleAcnt("GOALNAME61", "1300", "100", "2"));

		SeleniumUtil.click(SortingGoals.createGoalTab());
		SeleniumUtil.waitForPageToLoad(1000);
		Assert.assertTrue(SortingGoals.createCompletedGoalwithSingleAcnt("GOALNAME62", "1300", "100", "2"));
		SeleniumUtil.waitForPageToLoad();
		SortingGoals.makingGoalAsVictory();
	}

	@Test(description = "verifying victory goal should come under victory and showing count of victory goal.", dependsOnMethods = {
			"creatingVictoryGoals" }, priority = 14, enabled = true)
	public void verifyingVictoryGoal() {
		Assert.assertEquals(SortingGoals.VictoryGoalCount().getText().trim(),
				PropsUtil.getDataPropertyValue("VictoryHeaderCount").trim());
	}

	@Test(description = "verify view more button should not be there with three victory goals.", dependsOnMethods = {
			"creatingVictoryGoals" }, priority = 15, enabled = true)
	public void verifyViewMoreVisibilityWith3VictoryGoals() {
		Assert.assertTrue(SortingGoals.viewMoreButtonForVictory().size() == 0);
	}

	@Test(description = "verify Sequencing of added victory goals", dependsOnMethods = {
			"creatingVictoryGoals" }, priority = 16, enabled = true)
	public void verifyVictoyGoalSequencing() {
		String expected[] = PropsUtil.getDataPropertyValue("VictoryGoalSequence").split(",");
		for (int i = 0; i < SortingGoals.SFGIVictoryGoalNames().size(); i++) {
			String actual = SortingGoals.SFGIVictoryGoalNames().get(i).getText().trim();
			Assert.assertEquals(actual, expected[i]);
		}
	}

	@Test(description = "creating 1 completed goal.", dependsOnMethods = {
			"creatingVictoryGoals" }, priority = 17, enabled = true)
	public void creatingVictoryGoal2() {
		SeleniumUtil.click(SortingGoals.createGoalTab());
		SeleniumUtil.waitForPageToLoad(2000);
		Assert.assertTrue(SortingGoals.createCompletedGoalwithSingleAcnt("GOALNAME63", "1300", "100", "2"));
		SeleniumUtil.waitForPageToLoad();
		SortingGoals.makingGoalAsVictory();
	}

	@Test(description = "verify view more button should not be there with three draft goals.", dependsOnMethods = {
			"creatingVictoryGoal2" }, priority = 18, enabled = true)
	public void verifyViewMoreVisibilityWith4VictoryGoals() {
		String expectedValue = PropsUtil.getDataPropertyValue("ActualViewMoreSize").trim();
		int expected = Integer.parseInt(expectedValue);
		int actual = SortingGoals.viewMoreButtonForVictory().size();
		Assert.assertEquals(actual, expected);
	}

	@Test(description = "Verifying Congrates msg for victory Goals", dependsOnMethods = {
			"creatingVictoryGoal2" }, priority = 19, enabled = true)
	public void verifyCongratesMgForVictory() {
		Assert.assertEquals(SortingGoals.VictorCongratesMsg().getText().trim(),
				PropsUtil.getDataPropertyValue("VictoryCongratesMsg").trim());
	}

	@Test(description = "Verifying Congrates msg for completed Goals", dependsOnMethods = {
			"creatingVictoryGoal2" }, priority = 20, enabled = true)
	public void verifyCongratesMgForCompleted() {
		Assert.assertEquals(SortingGoals.CompletedCongratesMsg().getText().trim(),
				PropsUtil.getDataPropertyValue("CompletedCongratsMsg").trim());
	}

	@Test(description = "creating 3 improgress goals", dependsOnMethods = {
			"creatingVictoryGoal2" }, priority = 21, enabled = true)
	public void creatingInprogressGoal() {

		SeleniumUtil.waitForPageToLoad(1000);
		SeleniumUtil.click(SortingGoals.createGoalTab());
		SeleniumUtil.waitForPageToLoad(1000);
		Assert.assertTrue(SortingGoals.createCompletedGoalwithSingleAcnt("GOALNAME64", "600", "100", "2"));

		SeleniumUtil.click(SortingGoals.createGoalTab());
		SeleniumUtil.waitForPageToLoad(1000);
		Assert.assertTrue(SortingGoals.createCompletedGoalwithSingleAcnt("GOALNAME65", "700", "100", "2"));

		SeleniumUtil.click(SortingGoals.createGoalTab());
		SeleniumUtil.waitForPageToLoad(1000);
		Assert.assertTrue(SortingGoals.createCompletedGoalwithSingleAcnt("GOALNAME66", "800", "100", "2"));

		SeleniumUtil.click(SortingGoals.createGoalTab());
		SeleniumUtil.waitForPageToLoad(1000);
		Assert.assertTrue(SortingGoals.createCompletedGoalwithSingleAcnt("GOALNAME67", "900", "100", "2"));

		SeleniumUtil.click(SortingGoals.createGoalTab());
		SeleniumUtil.waitForPageToLoad(1000);
		Assert.assertTrue(SortingGoals.createCompletedGoalwithSingleAcnt("GOALNAME68", "1000", "100", "2"));

		SeleniumUtil.click(SortingGoals.createGoalTab());
		SeleniumUtil.waitForPageToLoad(1000);
		Assert.assertTrue(SortingGoals.createCompletedGoalwithSingleAcnt("GOALNAME69", "1100", "100", "2"));
		SeleniumUtil.waitForPageToLoad(3000);
	}

	@Test(description = "verifying inprogress goal should come under improgress goal and showing count of inprogress goal.", dependsOnMethods = {
			"creatingInprogressGoal" }, priority = 22, enabled = true)
	public void verifyingInProgressGoal() {
		Assert.assertEquals(SortingGoals.InprogressGoalCount().getText().trim(),
				PropsUtil.getDataPropertyValue("InprogressHeaderCount").trim());
	}

	@Test(description = "verify view more button should not be there with six improgress goals.", dependsOnMethods = {
			"creatingInprogressGoal" }, priority = 23, enabled = true)
	public void verifyViewMoreVisibilityWith3ImprogressGoals() {
		Assert.assertTrue(SortingGoals.viewMoreButtonForInprogress().size() == 0);
	}

	@Test(description = "verify Sequencing of added improgress goals", dependsOnMethods = {
			"creatingInprogressGoal" }, priority = 24, enabled = true)
	public void verifyInprogressGoalSequencing() {
		String expected[] = PropsUtil.getDataPropertyValue("InprogressGoalSequence").split(",");
		for (int i = 0; i < SortingGoals.allInprogressGoal().size(); i++) {
			String actual = SortingGoals.allInprogressGoal().get(i).getText().trim();
			Assert.assertEquals(actual, expected[i]);
		}
	}

	@Test(description = "creating 1 inprogress goal.", dependsOnMethods = {
			"creatingInprogressGoal" }, priority = 25, enabled = true)
	public void creatingInprogressGoal2() {

		SeleniumUtil.click(SortingGoals.createGoalTab());
		SeleniumUtil.waitForPageToLoad(1000);
		Assert.assertTrue(SortingGoals.createCompletedGoalwithSingleAcnt("GOALNAME70", "1300", "100", "2"));
		SeleniumUtil.waitForPageToLoad();
	}

	@Test(description = "verify view more button should not be there with three draft goals.", dependsOnMethods = {
			"creatingInprogressGoal2" }, priority = 26, enabled = true)
	public void verifyViewMoreVisibilityWithInprogresGoals() {
		String expectedValue = PropsUtil.getDataPropertyValue("ActualViewMoreSize").trim();
		int expected = Integer.parseInt(expectedValue);
		int actual = SortingGoals.viewMoreButtonForInprogress().size();
		Assert.assertEquals(actual, expected);
	}

	@Test(description = "verify Minimising all goals.", dependsOnMethods = {
			"creatingInprogressGoal2" }, priority = 27, enabled = true)
	public void verifyMinimizingGoal() {
		for (int i = 0; i < SortingGoals.MinMaxArrowForGoal().size(); i++) {
			SeleniumUtil.click(SortingGoals.MinMaxArrowForGoal().get(i));
			SeleniumUtil.waitForPageToLoad(1000);
			Assert.assertTrue(SortingGoals.MinMaxArrowForGoal().get(i).getAttribute("class").trim()
					.contains(PropsUtil.getDataPropertyValue("MinimizingText").trim()));
		}
	}

	@Test(description = "verify Maximizing all goals.", dependsOnMethods = {
			"verifyMinimizingGoal" }, priority = 28, enabled = true)
	public void verifyMaximizingGoal() {
		for (int i = 0; i < SortingGoals.MinMaxArrowForGoal().size(); i++) {
			SeleniumUtil.click(SortingGoals.MinMaxArrowForGoal().get(i));
			SeleniumUtil.waitForPageToLoad(1000);
			Assert.assertTrue(SortingGoals.MinMaxArrowForGoal().get(i).getAttribute("class").trim()
					.contains(PropsUtil.getDataPropertyValue("MaximizingText").trim()));
		}
	}

	@Test(description = "verify all goals progress Sequence.", priority = 29, enabled = true, dependsOnMethods = {
			"verifyMaximizingGoal" })
	public void verifyAllGoalsProgressSequence() {
		String expected[] = PropsUtil.getDataPropertyValue("AllGoalsSequenceList").split(",");
		for (int i = 0; i < SortingGoals.AllProgressBarStatus().size(); i++) {
			String actual = SortingGoals.AllProgressBarStatus().get(i).getText().trim();
			Assert.assertEquals(actual, expected[i]);
		}
	}

}
