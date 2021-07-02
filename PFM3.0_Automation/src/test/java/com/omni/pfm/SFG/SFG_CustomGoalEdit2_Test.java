/*******************************************************************************
 * Copyright (c) 2018 Yodlee Inc. All Rights Reserved.
 * 
 * This software is the confidential and proprietary information of
 * Yodlee, Inc. Use is subject to license terms.
 * 
 * @author sjain10
 ******************************************************************************/
package com.omni.pfm.SFG;

import org.openqa.selenium.Keys;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.omni.pfm.PageProcessor.PageParser;
import com.omni.pfm.pages.Login.LoginPage;
import com.omni.pfm.pages.SFG.SFG_CustomGoalEdit_Loc;
import com.omni.pfm.testBase.TestBase;
import com.omni.pfm.utility.PropsUtil;
import com.omni.pfm.utility.SeleniumUtil;

public class SFG_CustomGoalEdit2_Test extends TestBase {
	Logger logger = LoggerFactory.getLogger(SFG_CustomGoalEdit1_Test.class);
	SFG_CustomGoalEdit_Loc customGoalEdit;
	LoginPage login;

	@BeforeClass(alwaysRun = true)
	public void init() throws Exception {
		doInitialization("SFG Custom Goal Edit Two");
		login = new LoginPage();
		customGoalEdit = new SFG_CustomGoalEdit_Loc(d);
	}

	@Test(description = "creating user and adding account.", groups = {
			"DesktopBrowsers,sanity" }, priority = 0, enabled = true)
	public void login() throws Exception {

		d.navigate().refresh();
		PageParser.forceNavigate("SFG", d);
		SeleniumUtil.waitForPageToLoad();
	}

	@Test(description = "The use should get an option to edit his primary goal details when he clicks on the 'Edit Goal' option.", groups = {
			"DesktopBrowsers,sanity" }, dependsOnMethods = { "login" }, priority = 1, enabled = true)
	public void verifyAcntsPresentInGoal() {
		int size = customGoalEdit.allInprogressGoal().size();
		for (int i = 0; i < size; i++) {
			logger.info(customGoalEdit.allInprogressGoal().get(i).getText());
			if (customGoalEdit.allInprogressGoal().get(i).getText().equals("GOALNAME50")) {
				SeleniumUtil.click(customGoalEdit.allInprogressGoal().get(i));
				break;
			}

		}
		Assert.assertTrue(customGoalEdit.editgoalbutton().isDisplayed());
	}

	@Test(description = "verifying i am done saving for this goal popUp header.", dependsOnMethods = {
			"verifyAcntsPresentInGoal" }, groups = { "DesktopBrowsers,sanity" }, priority = 2, enabled = true)
	public void verifyDoneSavingPopUp() {
		SeleniumUtil.click(customGoalEdit.saveGoalPopUp());
		SeleniumUtil.waitForPageToLoad();// added page wait here
		Assert.assertEquals(customGoalEdit.SaveHeader().getText().trim(),
				PropsUtil.getDataPropertyValue("SavingTargetHeader").trim());
		Assert.assertEquals(customGoalEdit.saveGoalPopUpGoalStatusHeader().getText().trim(),
				PropsUtil.getDataPropertyValue("GoalStatusHeader").trim());
	}

	@Test(description = "verifying i am done saving popUp left side values.", dependsOnMethods = {
			"verifyDoneSavingPopUp" }, groups = { "DesktopBrowsers,sanity" }, priority = 3, enabled = true)
	public void verifyDoneSavingLeftSide() {

		String expected[] = PropsUtil.getDataPropertyValue("GoalStatusPopUpLeftValues").split(",");
		System.out.println(customGoalEdit.saveGoalPopUpLeft().size());
		for (int i = 0; i < customGoalEdit.saveGoalPopUpLeft().size(); i++) {
			String actual = customGoalEdit.saveGoalPopUpLeft().get(i).getText().trim();
			Assert.assertEquals(actual, expected[i]);
		}
	}

	@Test(description = "verifying i am done saving popUp right side values.", groups = {
			"DesktopBrowsers,sanity" }, dependsOnMethods = { "verifyDoneSavingPopUp" }, priority = 4, enabled = true)
	public void verifyDoneSavingRightSide() {

		String expected[] = PropsUtil.getDataPropertyValue("GoalStatusPopUpRightValues").split(":");
		for (int i = 0; i < customGoalEdit.saveGoalPopUpRight().size(); i++) {
			String actual = customGoalEdit.saveGoalPopUpRight().get(i).getText().trim();
			Assert.assertEquals(actual, expected[i]);
		}
	}

	@Test(description = "verifying i am done saving popUp Para1 and Para2", dependsOnMethods = {
			"verifyDoneSavingPopUp" }, groups = { "DesktopBrowsers,sanity" }, priority = 5, enabled = true)
	public void verifyDoneSavingPopUpPara() {

		Assert.assertEquals(customGoalEdit.saveGoalPopUpPara1().getText().trim(),
				PropsUtil.getDataPropertyValue("GoalStatusPopUpPara1").trim());
		Assert.assertEquals(customGoalEdit.saveGoalPopUpPara2().getText().trim(),
				PropsUtil.getDataPropertyValue("GoalStatusPopUpPara2").trim());
	}

	@Test(description = "verifying i am done saving popUp Para1 and Para2", dependsOnMethods = {
			"verifyDoneSavingPopUp" }, groups = { "DesktopBrowsers,sanity" }, priority = 6, enabled = true)
	public void verifyDoneSavingRB1Label() {

		Assert.assertEquals(customGoalEdit.RB1label().getText().trim(),
				PropsUtil.getDataPropertyValue("RB1Label").trim());
		Assert.assertEquals(customGoalEdit.RB2label().getText().trim(),
				PropsUtil.getDataPropertyValue("RB2Label").trim());
	}

	@Test(description = "verifying i am done saving popUp RB1 should be checked by default.", dependsOnMethods = {
			"verifyDoneSavingPopUp" }, groups = { "DesktopBrowsers,sanity" }, priority = 7, enabled = true)
	public void verifyDoneSavingRB1CheckedStatus() {

		Assert.assertTrue(customGoalEdit.RB1label().getAttribute("class")
				.contains(PropsUtil.getDataPropertyValue("CheckedStatus").trim()));
	}

	@Test(description = "verifying i am done saving popUp cancel and confirm button should be visible.", dependsOnMethods = {
			"verifyDoneSavingPopUp" }, groups = { "DesktopBrowsers,sanity" }, priority = 8, enabled = true)
	public void verifyDoneSavingCancelAndConfirmBtn() {
		Assert.assertTrue(customGoalEdit.SaveGoalCancelButton().isDisplayed());
		Assert.assertTrue(customGoalEdit.SaveGoalConfirmButton().isDisplayed());
	}

	@Test(description = "verifying Goal Summary Delete popup.", dependsOnMethods = {
			"verifyDoneSavingPopUp" }, groups = { "DesktopBrowsers,sanity" }, priority = 9, enabled = true)
	public void verifyGoalSumm1ryDeletepopUP() {
		SeleniumUtil.click(customGoalEdit.SaveGoalCancelButton());
		SeleniumUtil.waitForPageToLoad(2000);
		SeleniumUtil.click(customGoalEdit.deleteGoalButton());
		Assert.assertEquals(customGoalEdit.SaveHeader().getText().trim(),
				PropsUtil.getDataPropertyValue("DeletePopupHeader").trim());
	}

	@Test(description = "verifying Goal Summary Delete popup sub headers", dependsOnMethods = {
			"verifyGoalSumm1ryDeletepopUP" }, groups = { "DesktopBrowsers,sanity" }, priority = 10, enabled = true)
	public void verifyGoalSumm1ryDeletepopUpSubHeader() {

		Assert.assertEquals(customGoalEdit.deleteGoalPara1().getText().trim(),
				PropsUtil.getDataPropertyValue("DeletePopUpPara1").trim());
		Assert.assertEquals(customGoalEdit.deleteGoalPara2().getText().trim(),
				PropsUtil.getDataPropertyValue("DeletePopUpPara2").trim());
	}

	@Test(description = "verifying delete pop up delete and cancel button", dependsOnMethods = {
			"verifyGoalSumm1ryDeletepopUP" }, groups = { "DesktopBrowsers,sanity" }, priority = 11, enabled = true)
	public void verifyDeleteAndCancelButton() {
		Assert.assertTrue(customGoalEdit.SFGDeletPopupDeletButton().isDisplayed());
		Assert.assertTrue(customGoalEdit.SFGDeletPopupCancelButton().isDisplayed());
		SeleniumUtil.click(customGoalEdit.SFGDeletPopupCancelButton());
	}

	@Test(description = "verifying editing of goal name should reflect in image bar.", dependsOnMethods = {
			"verifyDeleteAndCancelButton" }, groups = { "DesktopBrowsers,sanity" }, priority = 12, enabled = true)
	public void verifyEditingGoalName() {
		SeleniumUtil.click(customGoalEdit.editgoalbutton());
		SeleniumUtil.waitForPageToLoad(2000);
		SeleniumUtil.click(customGoalEdit.goalnametextbox());
		customGoalEdit.goalnametextbox().clear();

		customGoalEdit.goalnametextbox().sendKeys(PropsUtil.getDataPropertyValue("UpdatingNewGoalName"));
		SeleniumUtil.waitForPageToLoad(3000);
		customGoalEdit.goalnametextbox().sendKeys(Keys.TAB);
		SeleniumUtil.waitForPageToLoad(3000);
		Assert.assertEquals(customGoalEdit.imageBarGoalName().getText().trim(),
				PropsUtil.getDataPropertyValue("UpdatingNewGoalName").trim());
	}

	@Test(description = "verifying editing of goal amount should reflect in image bar.", dependsOnMethods = {
			"verifyEditingGoalName" }, groups = { "DesktopBrowsers,sanity" }, priority = 13, enabled = true)
	public void verifyEditingGoalAmount() {
		SeleniumUtil.click(customGoalEdit.amountfield1());
		customGoalEdit.amountfield1().clear();
		customGoalEdit.amountfield1().sendKeys(PropsUtil.getDataPropertyValue("UpdatingNewGoalAmount"));
		customGoalEdit.amountfield1().sendKeys(Keys.TAB);
		Assert.assertTrue(customGoalEdit.imageBarGoalAmount().getText().trim()
				.contains(PropsUtil.getDataPropertyValue("UpdatedGoalAmountInImageBar").trim()));
	}

	@Test(description = "verifying editing of goal date should reflect in image bar.", dependsOnMethods = {
			"verifyEditingGoalAmount" }, groups = { "DesktopBrowsers,sanity" }, priority = 14, enabled = true)
	public void verifyEditingGoalDate() {

		SeleniumUtil.click(customGoalEdit.dateField());
		customGoalEdit.dateField().clear();
		customGoalEdit.dateField().sendKeys(PropsUtil.getDataPropertyValue("UpdatingNewGoalDate"));
		customGoalEdit.dateField().sendKeys(Keys.TAB);

		logger.info(customGoalEdit.imageBarGoalDate().getText().trim());
		logger.info(PropsUtil.getDataPropertyValue("UpdatingNewGoalDateInProgressBar").trim());
		Assert.assertTrue(customGoalEdit.imageBarGoalDate().getText().trim()
				.contains(PropsUtil.getDataPropertyValue("UpdatingNewGoalDateInProgressBar").trim()));
	}

	@Test(description = "Saving the goal after editing.", dependsOnMethods = { "verifyEditingGoalDate" }, groups = {
			"DesktopBrowsers,sanity" }, priority = 15, enabled = true)
	public void savingGoalAfterVerifyingEdit() {
		if (PageParser.isElementPresent("closeEditButton", "SFG", null)) {
			SeleniumUtil.click(customGoalEdit.verifyClose());
		} else {
			SeleniumUtil.click(customGoalEdit.SFGBackButton());
		}
		SeleniumUtil.waitForPageToLoad(2000);
		SeleniumUtil.click(customGoalEdit.saveGoalPopUp());
		SeleniumUtil.click(customGoalEdit.SaveGoalConfirmButton());
		SeleniumUtil.waitForPageToLoad(1000);
		Assert.assertTrue(customGoalEdit.SavingSucessMsg().isDisplayed());
	}

	@Test(description = "verifying goal summary header after saving goal", dependsOnMethods = {
			"savingGoalAfterVerifyingEdit" }, groups = { "DesktopBrowsers,sanity" }, priority = 16, enabled = true)
	public void verifyGoalSummaryLandingPage() {

		Assert.assertTrue(customGoalEdit.AcntSummaryGoalStatusHeader().getText().trim()
				.contains(PropsUtil.getDataPropertyValue("GoalSummaryHeader").trim()));
	}

	@Test(description = "verifying goal summary left side labels after saving goal", dependsOnMethods = {
			"savingGoalAfterVerifyingEdit" }, groups = { "DesktopBrowsers,sanity" }, priority = 17, enabled = true)
	public void verifyGoalSummaryLeftLabels() {
		String expected[] = PropsUtil.getDataPropertyValue("GoalSummaryLeftLabels").split(",");
		for (int i = 0; i < customGoalEdit.SFGEditCurrentStausDetailslabel().size(); i++) {
			String actual = customGoalEdit.SFGEditCurrentStausDetailslabel().get(i).getText().trim();
			Assert.assertEquals(actual, expected[i]);
		}
	}

	@Test(description = "verifying goal summary right side value after saving goal", dependsOnMethods = {
			"savingGoalAfterVerifyingEdit" }, groups = { "DesktopBrowsers,sanity" }, priority = 18, enabled = true)
	public void verifyGoalSummaryRightValues() {
		String expected[] = PropsUtil.getDataPropertyValue("GoalSummaryRightLabelValue").split(":");
		for (int i = 0; i < 2; i++) {
			String actual = customGoalEdit.SFGEditCurrentStausDetailsValue().get(i).getText().trim();
			Assert.assertEquals(actual, expected[i]);
		}
	}

	@Test(description = "verifying goal summary third right side value after saving goal", dependsOnMethods = {
			"savingGoalAfterVerifyingEdit" }, groups = { "DesktopBrowsers,sanity" }, priority = 19, enabled = true)
	public void verifyGoalSummaryThirdRightValues() {
		String actual = customGoalEdit.SFGEditCurrentStausDetailsValue().get(2).getText().trim();
		String expected = customGoalEdit.targateDateSelectMMM(0);
		Assert.assertEquals(actual, expected);
	}
	// commented below code as there is same code in verifyMarkAsCompletePopup()
	// this method
	/*
	 * @Test(description = "verifying footer line And mark As complete button",
	 * dependsOnMethods = { "savingGoalAfterVerifyingEdit" }, groups = {
	 * "DesktopBrowsers,sanity" }, priority = 19, enabled = true) public void
	 * verifyGoalSummaryFooter() {
	 * 
	 * Assert.assertEquals(customGoalEdit.GoalSummaryFooterLine().getText().trim(),
	 * PropsUtil.getDataPropertyValue("GoalSummaryFooterLine").trim());
	 * Assert.assertTrue(customGoalEdit.NextBtnStep1().isDisplayed());
	 * 
	 * }
	 */

	@Test(description = "verifying mark as complete popup header", dependsOnMethods = {
			"savingGoalAfterVerifyingEdit" }, groups = { "DesktopBrowsers,sanity" }, priority = 20, enabled = true)
	public void verifyMarkAsCompletePopup() {

		Assert.assertEquals(customGoalEdit.GoalSummaryFooterLine().getText().trim(),
				PropsUtil.getDataPropertyValue("GoalSummaryFooterLine").trim());
		Assert.assertTrue(customGoalEdit.NextBtnStep1().isDisplayed());

	}

	@Test(description = "verifying mark as complete popup header.", dependsOnMethods = {
			"savingGoalAfterVerifyingEdit" }, groups = { "DesktopBrowsers,sanity" }, priority = 21, enabled = true)
	public void verifyMarkCompletePopUp() {
		SeleniumUtil.click(customGoalEdit.NextBtnStep1());
		Assert.assertEquals(customGoalEdit.SaveHeader().getText().trim(),
				PropsUtil.getDataPropertyValue("MarkAsCompleteHeader").trim());
		Assert.assertEquals(customGoalEdit.markAsCompleteConfirmText().getText().trim(),
				PropsUtil.getDataPropertyValue("MarkAsCompleteConfirmText").trim());
	}

	@Test(description = "verifying Mark as Complete left side labels after saving goal", dependsOnMethods = {
			"verifyMarkCompletePopUp" }, groups = { "DesktopBrowsers,sanity" }, priority = 22, enabled = true)
	public void verifyMarkCompleteLeftLabels() {
		String expected[] = PropsUtil.getDataPropertyValue("markAsCompleteLeftLabel").split(",");
		for (int i = 0; i < customGoalEdit.markAsCompleteLeftLabel().size(); i++) {
			String actual = customGoalEdit.markAsCompleteLeftLabel().get(i).getText().trim();
			Assert.assertEquals(actual, expected[i]);
		}
	}

	@Test(description = "verifying  Mark as Complete right side value after saving goal", dependsOnMethods = {
			"verifyMarkCompletePopUp" }, groups = { "DesktopBrowsers,sanity" }, priority = 23, enabled = true)
	public void verifyMarkCompleteRightValues() {
		String expected[] = PropsUtil.getDataPropertyValue("markAsCompleteRightLabelValue").split(":");
		for (int i = 0; i < 2; i++) {
			String actual = customGoalEdit.markAsCompleteRightLabel().get(i).getText().trim();
			System.out.println(actual);
			System.out.println(expected[i]);
			Assert.assertEquals(actual, expected[i]);
		}
	}

	@Test(description = "verifying goal summary third right side value after saving goal", dependsOnMethods = {
			"verifyMarkCompletePopUp" }, groups = { "DesktopBrowsers,sanity" }, priority = 24, enabled = true)
	public void verifyMarkCompleteThirdRightValues() {
		String actual = customGoalEdit.SFGEditCurrentStausDetailsValue().get(2).getText().trim();
		String expected = customGoalEdit.targateDateSelectMMM(0);
		Assert.assertEquals(actual, expected);
	}

	@Test(description = "verifying mark as complete popUp cancel and confirm button should be visible.", dependsOnMethods = {
			"verifyMarkCompletePopUp" }, groups = { "DesktopBrowsers,sanity" }, priority = 25, enabled = true)
	public void verifyMarkCompletCancelAndConfirmBtn() {
		Assert.assertTrue(customGoalEdit.SaveGoalCancelButton().isDisplayed());
		Assert.assertTrue(customGoalEdit.SaveGoalConfirmButton().isDisplayed());
	}

	@Test(description = "verifying  victory landing page Headers", groups = {
			"DesktopBrowsers,sanity" }, priority = 26, enabled = true)
	public void verifyVictoryLandingPage() {
		SeleniumUtil.click(customGoalEdit.SaveGoalConfirmButton());
		Assert.assertTrue(customGoalEdit.VictoryTickMarkIcon().isDisplayed());
	}

	@Test(description = "verifying  victory landing page Text", groups = {
			"DesktopBrowsers,sanity" }, dependsOnMethods = { "verifyMarkCompletePopUp" }, priority = 27, enabled = true)
	public void verifyVictoryLandingPage2() {
		Assert.assertEquals(customGoalEdit.VictoryText().getText().trim(),
				PropsUtil.getDataPropertyValue("VictoryTitle").trim());
		Assert.assertEquals(customGoalEdit.VictoryText2().getText().trim(),
				PropsUtil.getDataPropertyValue("VictorySuggestion").trim());
	}

	@Test(description = "verifying delete button and start new goal button visibility on page.", groups = {
			"DesktopBrowsers,sanity" }, dependsOnMethods = "verifyMarkCompletePopUp", priority = 28, enabled = true)
	public void verifyVictoryDeleteAndStartGoalButton() {
		Assert.assertTrue(customGoalEdit.deleteGoalButton().isDisplayed());
		Assert.assertTrue(customGoalEdit.startNewGoalTab().isDisplayed());
	}

	@Test(description = "verifying on clicking on Start New goal It should navigate to SFG Categories Lnading page", groups = {
			"DesktopBrowsers,sanity" }, dependsOnMethods = "verifyMarkCompletePopUp", priority = 29, enabled = true)
	public void verifyCategoriesLandingPage() {
		SeleniumUtil.click(customGoalEdit.startNewGoalTab());
		Assert.assertEquals(customGoalEdit.SFGcategoryHeading().getText().trim(),
				PropsUtil.getDataPropertyValue("GoalsCategoryLandingPageHeader").trim());

	}

	@Test(description = "verifying the Victory Goal is navigating to victory landing page", dependsOnMethods = {
			"verifyCategoriesLandingPage" }, groups = { "DesktopBrowsers,sanity" }, priority = 30, enabled = true)
	public void verifyingNavigationOfVictoryGoal() {
		SeleniumUtil.click(customGoalEdit.backToNewGoal());
		SeleniumUtil.waitForPageToLoad(3000);
		for (int i = 0; i < customGoalEdit.SFGIVictoryGoalNames().size(); i++) {
			logger.info(customGoalEdit.SFGIVictoryGoalNames().get(i).getText());
			if (customGoalEdit.SFGIVictoryGoalNames().get(i).getText().equals("GOALNAME50")) {
				SeleniumUtil.click(customGoalEdit.SFGIVictoryGoalNames().get(i));
				break;
			}

		}
		Assert.assertTrue(customGoalEdit.VictoryText().isDisplayed());
	}

	@Test(description = "deleting victory goal", groups = { "DesktopBrowsers,sanity" }, dependsOnMethods = {
			"verifyingNavigationOfVictoryGoal" }, priority = 31, enabled = true)
	public void deletingVictoryFlow() {
		SeleniumUtil.click(customGoalEdit.deleteGoalButton());
		SeleniumUtil.click(customGoalEdit.SFGFundingDeletePopupDeleteButton());

		for (int i = 0; i < customGoalEdit.allInprogressGoal().size(); i++) {
			logger.info(customGoalEdit.allInprogressGoal().get(i).getText());
			if (customGoalEdit.allInprogressGoal().get(i).getText().equals("GOALNAME51")) {
				SeleniumUtil.click(customGoalEdit.allInprogressGoal().get(i));
				break;
			}
		}
		SeleniumUtil.waitForPageToLoad(2000);
		SeleniumUtil.click(customGoalEdit.deleteGoalButton());
		SeleniumUtil.click(customGoalEdit.SFGFundingDeletePopupDeleteButton());
		SeleniumUtil.waitForPageToLoad(2000);
		Assert.assertTrue(customGoalEdit.getStartedTab().isDisplayed());

	}

}
