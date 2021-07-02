/*******************************************************************************
 * Copyright (c) 2018 Yodlee Inc. All Rights Reserved.
 * 
 * This software is the confidential and proprietary information of
 * Yodlee, Inc. Use is subject to license terms.
 * 
 * @author
 ******************************************************************************/
package com.omni.pfm.SFG;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.datatransfer.StringSelection;
import java.awt.event.KeyEvent;

import org.openqa.selenium.Keys;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.omni.pfm.AccountAddition.YCOM_Regression_AccAndGroupCreation;
import com.omni.pfm.PageProcessor.PageParser;
import com.omni.pfm.pages.AccountAddition.AccountAddition;
import com.omni.pfm.pages.Login.LoginPage;
import com.omni.pfm.pages.SFG.SFG_CustomGoalEdit_Loc;
import com.omni.pfm.testBase.TestBase;
import com.omni.pfm.utility.PropsUtil;
import com.omni.pfm.utility.SeleniumUtil;

public class SFG_CustomGoalEdit1_Test extends TestBase {
	Logger logger = LoggerFactory.getLogger(SFG_CustomGoalEdit1_Test.class);
	YCOM_Regression_AccAndGroupCreation userRegAccAddUtil;
	LoginPage login;
	AccountAddition accountAdd;
	SFG_CustomGoalEdit_Loc customGoalEdit;

	@BeforeClass(alwaysRun = true)
	public void init() throws Exception {
		doInitialization("SFG Custom Goal Edit One");
		login = new LoginPage();
		accountAdd = new AccountAddition();
		customGoalEdit = new SFG_CustomGoalEdit_Loc(d);

		LoginPage.loginMain(d, loginParameter);
		SeleniumUtil.waitForPageToLoad();
		accountAdd.linkAccount();
		accountAdd.addAggregatedAccount("SFGsite.site16441.1", "site16441.1");
		d.navigate().refresh();
		PageParser.forceNavigate("SFG", d);
		SeleniumUtil.waitForPageToLoad();
	}

	@Test(description = "Verifying landing page and creating first goal", groups = {
			"DesktopBrowsers,sanity" }, priority = 1, enabled = true)
	public void creatingGoalFirst() {
		boolean getStartedStatus = customGoalEdit.getStartedTab().isDisplayed();
		SeleniumUtil.click(customGoalEdit.getStartedTab());
		SeleniumUtil.waitForPageToLoad(3000);

		customGoalEdit.createGoalwithMultipleAccount("GOALNAME50", "1300", "100", "100");
		Assert.assertTrue(getStartedStatus);
	}

	@Test(description = "creating 2 goals", dependsOnMethods = { "creatingGoalFirst" }, groups = {
			"DesktopBrowsers,sanity" }, priority = 2, enabled = true)
	public void creatingGoalSecond() {
		SeleniumUtil.click(customGoalEdit.createGoalTab());
		customGoalEdit.createGoalwithMultipleAccount("GOALNAME51", "1300", "100", "100");

		SeleniumUtil.waitForPageToLoad(2000);
		int numberOfAddedGoal = customGoalEdit.allInprogressGoal().size();
		Assert.assertEquals(numberOfAddedGoal, 2);
	}

	@Test(description = "The use should get an option to edit his primary goal details when he clicks on the 'Edit Goal' option.", groups = {
			"DesktopBrowsers,sanity" }, dependsOnMethods = { "creatingGoalSecond" }, priority = 3, enabled = true)
	public void verifyAcntsPresentInGoal() {
		for (int i = 0; i < customGoalEdit.allInprogressGoal().size(); i++) {
			System.out.println(customGoalEdit.allInprogressGoal().get(i).getText());
			if (customGoalEdit.allInprogressGoal().get(i).getText().equals("GOALNAME50")) {
				SeleniumUtil.click(customGoalEdit.allInprogressGoal().get(i));
				break;
			}

		}
		Assert.assertTrue(customGoalEdit.editgoalbutton().isDisplayed());
	}

	@Test(description = "verify added goal Accounts while editing the goal", groups = {
			"DesktopBrowsers,sanity" }, dependsOnMethods = { "verifyAcntsPresentInGoal" }, priority = 4, enabled = true)
	public void verifyAddedAcnts() {
		SeleniumUtil.click(customGoalEdit.editgoalbutton());
		SeleniumUtil.waitForPageToLoad(3000);
		Assert.assertEquals(customGoalEdit.goalAccount().get(0).getText().trim(),
				PropsUtil.getDataPropertyValue("goalaccouns1").trim());
		Assert.assertEquals(customGoalEdit.goalAccount().get(1).getText().trim(),
				PropsUtil.getDataPropertyValue("goalaccouns2").trim());
	}

	@Test(description = "verify added goal value and name while editing the goal", groups = {
			"DesktopBrowsers,sanity" }, dependsOnMethods = { "verifyAddedAcnts" }, priority = 5, enabled = true)
	public void verifyGoalNameAndValue() {
		Assert.assertEquals(customGoalEdit.goalnametextbox().getAttribute("value"),
				PropsUtil.getDataPropertyValue("OldGoalname"));

		// AT-23602 The primary goal details should include "Goal amount"
		Assert.assertEquals(customGoalEdit.amountfield1().getAttribute("value"),
				PropsUtil.getDataPropertyValue("oldgoalamount"));
	}

	@Test(description = "If the user updates the Goal name to an existing goal name, show the respective error.", groups = {
			"DesktopBrowsers,sanity" }, dependsOnMethods = { "verifyAddedAcnts" }, priority = 6, enabled = true)
	public void editingGoalNameToExisting() {
		// AT-23606 If the user updates the "Goal name" to an existing goal name, show
		// the respective error.
		SeleniumUtil.click(customGoalEdit.goalnametextbox());
		customGoalEdit.goalnametextbox().clear();

		customGoalEdit.goalnametextbox().sendKeys(PropsUtil.getDataPropertyValue("ExistingGoalName"));
		SeleniumUtil.waitForPageToLoad(3000);
		customGoalEdit.goalnametextbox().sendKeys(Keys.TAB);

		Assert.assertEquals(customGoalEdit.goalNameErrorMsg().getText().trim(),
				PropsUtil.getDataPropertyValue("ExistingGoalNameErrorMsg").trim());

	}

	@Test(description = "verify if user update the goal name with empty name, it should show respective error.", groups = {
			"DesktopBrowsers,sanity" }, dependsOnMethods = {
					"editingGoalNameToExisting" }, priority = 7, enabled = true)
	public void editingGoalNameWithEmptyGoalName() {
		// AT-23606 If the user updates the "Goal name" to an existing goal name, show
		// the respective error.
		SeleniumUtil.click(customGoalEdit.goalnametextbox());
		customGoalEdit.goalnametextbox().clear();

		customGoalEdit.goalnametextbox().sendKeys(Keys.TAB);

		Assert.assertEquals(customGoalEdit.goalNameErrorMsg().getText().trim(),
				PropsUtil.getDataPropertyValue("EmptyGoalNameErrorMsg").trim());

	}

	@Test(description = "The goal name field should be validated with special character.", groups = {
			"DesktopBrowsers,sanity" }, dependsOnMethods = {
					"editingGoalNameWithEmptyGoalName" }, priority = 8, enabled = true)
	public void editingGoalNameWithSpecialCharacter() {
		// AT-23606 If the user updates the "Goal name" to an existing goal name, show
		// the respective error.
		Robot robot = null;
		try {
			robot = new Robot();
		} catch (AWTException e) {
			e.printStackTrace();
		}

		SeleniumUtil.waitForPageToLoad(6000);

		// Copy paste Values in Goal Name
		try {
			customGoalEdit.goalnametextbox().click();
		} catch (Exception e) {
			SeleniumUtil.click(customGoalEdit.goalnametextbox());
		}
		// Press CTRL+V
		StringSelection s = new StringSelection(PropsUtil.getDataPropertyValue("SpecialCharAtGoalName").trim());
		Toolkit.getDefaultToolkit().getSystemClipboard().setContents(s, null);
		robot.keyPress(KeyEvent.VK_CONTROL);
		robot.keyPress(KeyEvent.VK_V);
		robot.keyRelease(KeyEvent.VK_V);
		robot.keyRelease(KeyEvent.VK_CONTROL);

		SeleniumUtil.waitForPageToLoad();
		customGoalEdit.goalnametextbox().sendKeys(Keys.TAB);
		Assert.assertEquals(customGoalEdit.goalNameErrorMsg().getText().trim(),
				PropsUtil.getDataPropertyValue("specialCharacterErrorMsg").trim());

	}

	@Test(description = "The goal name text field should do the validations for length and characters.", groups = {
			"DesktopBrowsers,sanity" }, dependsOnMethods = {
					"editingGoalNameWithEmptyGoalName" }, priority = 9, enabled = true)
	public void editingGoalNameWithMaxCharacter() {
		SeleniumUtil.click(customGoalEdit.goalnametextbox());
		customGoalEdit.goalnametextbox().clear();

		customGoalEdit.goalnametextbox().sendKeys(PropsUtil.getDataPropertyValue("MaximumCharacter"));
		SeleniumUtil.waitForPageToLoad();
		customGoalEdit.goalnametextbox().sendKeys(Keys.TAB);
		if (customGoalEdit.goalnametextbox().getAttribute("value").length() != 30) {
			Assert.assertTrue(false);
		}
	}

	@Test(description = "Verify Amount Field with empty text,It should show empty error msg", groups = {
			"DesktopBrowsers,sanity" }, dependsOnMethods = {
					"editingGoalNameWithMaxCharacter" }, priority = 10, enabled = true)
	public void editingGoalAmtFieldWithEmptyText() {
		SeleniumUtil.click(customGoalEdit.amountfield1());
		customGoalEdit.amountfield1().clear();

		customGoalEdit.amountfield1().sendKeys(Keys.TAB);

		Assert.assertEquals(customGoalEdit.goalAmountFieldErrorMsg().getText(),
				PropsUtil.getDataPropertyValue("EmptyGoalAmountFieldErrorMsg"));
	}

	@Test(description = "Verify Amount Field with negative amount,It should show empty error msg", groups = {
			"DesktopBrowsers,sanity" }, dependsOnMethods = {
					"editingGoalAmtFieldWithEmptyText" }, priority = 11, enabled = true)
	public void editingGoalAmtFieldWithNegative() {
		Robot robot = null;
		try {
			robot = new Robot();
		} catch (AWTException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		SeleniumUtil.waitForPageToLoad();

		// Copy paste Values in Goal Name
		SeleniumUtil.click(customGoalEdit.amountfield1());
		customGoalEdit.amountfield1().clear();
		// Press CTRL+V
		StringSelection s = new StringSelection(PropsUtil.getDataPropertyValue("NegativeAmount"));
		Toolkit.getDefaultToolkit().getSystemClipboard().setContents(s, null);
		robot.keyPress(KeyEvent.VK_CONTROL);
		robot.keyPress(KeyEvent.VK_V);
		robot.keyRelease(KeyEvent.VK_V);
		robot.keyRelease(KeyEvent.VK_CONTROL);
		customGoalEdit.amountfield1().sendKeys(Keys.TAB);

		Assert.assertEquals(customGoalEdit.goalAmountFieldErrorMsg().getText(),
				PropsUtil.getDataPropertyValue("NegativeGoalAmountText"));
	}

	@Test(description = "Verify Amount Field with maximum number of characters.It should show error msg", groups = {
			"DesktopBrowsers,sanity" }, dependsOnMethods = {
					"editingGoalAmtFieldWithEmptyText" }, priority = 12, enabled = true)
	public void editingGoalAmtFieldWithMaxChar() {
		SeleniumUtil.click(customGoalEdit.amountfield1());
		customGoalEdit.amountfield1().clear();

		customGoalEdit.amountfield1().sendKeys(PropsUtil.getDataPropertyValue("AmountFieldMaximumNumber"));
		if (customGoalEdit.amountfield1().getAttribute("value").length() != 11) {
			Assert.assertTrue(false);
		}
	}

	@Test(description = "Verify date Field.", dependsOnMethods = { "editingGoalAmtFieldWithMaxChar" }, groups = {
			"DesktopBrowsers,sanity" }, priority = 13, enabled = true)
	public void editingGoaldateField() {

		String actualDate = customGoalEdit.dateField().getAttribute("value").trim();
		String expectedDate = customGoalEdit.targateDate1(366);
		Assert.assertEquals(actualDate, expectedDate);
	}

	@Test(description = "Verify empty date Field.", groups = { "DesktopBrowsers,sanity" }, dependsOnMethods = {
			"editingGoalAmtFieldWithMaxChar" }, priority = 14, enabled = true)
	public void editingGoalEmptydateField() {
		customGoalEdit.dateField().clear();
		customGoalEdit.dateField().sendKeys(Keys.TAB);

		Assert.assertEquals(customGoalEdit.dateFieldErrorMsg().getText().trim(),
				PropsUtil.getDataPropertyValue("EmptyDateFieldErrorMsg").trim());

	}

	@Test(description = "Verify date Field with less date,It should show error msg", groups = {
			"DesktopBrowsers,sanity" }, dependsOnMethods = {
					"editingGoalEmptydateField" }, priority = 15, enabled = true)
	public void editingDateFieldWithLessDate() {
		customGoalEdit.dateField().clear();
		customGoalEdit.dateField().sendKeys(PropsUtil.getDataPropertyValue("LessDateInput"));
		customGoalEdit.dateField().sendKeys(Keys.TAB);
		Assert.assertTrue(customGoalEdit.dateFieldErrorMsg().getText().trim()
				.contains(PropsUtil.getDataPropertyValue("lessDateFieldErrorMsg")));
	}

	@Test(description = "Verify date Field with more than limit year values,It should show error msg", groups = {
			"DesktopBrowsers,sanity" }, dependsOnMethods = {
					"editingDateFieldWithLessDate" }, priority = 16, enabled = true)
	public void editingDateFieldWithMoreThanLimit() {

		customGoalEdit.dateField().clear();
		customGoalEdit.dateField().sendKeys(PropsUtil.getDataPropertyValue("MoreDateInput"));
		customGoalEdit.dateField().sendKeys(Keys.TAB);

		System.out.println(customGoalEdit.dateFieldErrorMsg().getText().trim());
		System.out.println(PropsUtil.getDataPropertyValue("MoreThanLimitDateFieldErrorMsg"));
		Assert.assertTrue(customGoalEdit.dateFieldErrorMsg().getText().trim()
				.contains(PropsUtil.getDataPropertyValue("MoreThanLimitDateFieldErrorMsg")));
	}

	@Test(description = "Verify date Field with invalid date values,It should show error msg", groups = {
			"DesktopBrowsers,sanity" }, dependsOnMethods = {
					"editingDateFieldWithMoreThanLimit" }, priority = 17, enabled = true)
	public void editingDateFieldWithInvalidDate() {
		customGoalEdit.dateField().clear();
		customGoalEdit.dateField().sendKeys(PropsUtil.getDataPropertyValue("InvalidDate"));
		customGoalEdit.dateField().sendKeys(Keys.TAB);

		Assert.assertTrue(customGoalEdit.dateFieldErrorMsg().getText().trim()
				.contains(PropsUtil.getDataPropertyValue("InvalidDateErrorMsg").trim()));
	}

	@Test(description = "Verify start date ,end date and frequency label", groups = {
			"DesktopBrowsers,sanity" }, dependsOnMethods = {
					"editingDateFieldWithInvalidDate" }, priority = 18, enabled = true)
	public void StartEndFreqLabels() {
		String expected[] = PropsUtil.getDataPropertyValue("LabelList").split(",");
		for (int i = 0; i < customGoalEdit.StartEndFreqLabels().size(); i++) {
			String actual = customGoalEdit.StartEndFreqLabels().get(i).getText().trim();
			Assert.assertEquals(actual, expected[i]);
		}

	}

	@Test(description = "Verify start date value.", groups = { "DesktopBrowsers,sanity" }, dependsOnMethods = {
			"editingDateFieldWithInvalidDate" }, priority = 19, enabled = true)
	public void StartValues() {
		String expectedStartDate = customGoalEdit.targateDateSelectMMM(1);
		String actualStartDate = customGoalEdit.StartEndFreqValues().get(0).getText().trim();
		Assert.assertEquals(actualStartDate, expectedStartDate);
	}

	@Test(description = "Verify end date value.", dependsOnMethods = { "editingDateFieldWithInvalidDate" }, groups = {
			"DesktopBrowsers,sanity" }, priority = 20, enabled = true)
	public void endDateValues() {
		String expectedEndDate = customGoalEdit.targateDateSelectMMM(366);
		String actualEndDate = customGoalEdit.StartEndFreqValues().get(1).getText().trim();

		Assert.assertEquals(actualEndDate, expectedEndDate);
	}

	@Test(description = "Verify frequency value.", dependsOnMethods = { "editingDateFieldWithInvalidDate" }, groups = {
			"DesktopBrowsers,sanity" }, priority = 21, enabled = true)
	public void freqValues() {
		String expectedFrequecy = customGoalEdit.StartEndFreqValues().get(2).getText().trim();
		String actualFrequecny = PropsUtil.getDataPropertyValue("ActualFrequency").trim();
		if (PageParser.isElementPresent("closeEditButton", "SFG", null)) {
			SeleniumUtil.click(customGoalEdit.verifyClose());
		} else {
			SeleniumUtil.click(customGoalEdit.SFGBackButton());
		}
		Assert.assertEquals(actualFrequecny, expectedFrequecy);
	}

	@Test(description = "Verify onGoing Funding PopUp", dependsOnMethods = { "freqValues" }, groups = {
			"DesktopBrowsers,sanity" }, priority = 22, enabled = true)
	public void onGoingFundingPopUp() {
		SeleniumUtil.click(customGoalEdit.nextbuttonClick());
		SeleniumUtil.click(customGoalEdit.onGoingFundingChange());

		String actualAcnt = customGoalEdit.FundingPageAccount().getAttribute("value").trim();
		String expectedAcnt = PropsUtil.getDataPropertyValue("ActualAcntValue").trim();

		String actualValue = customGoalEdit.SFGAmountValue().getAttribute("value").trim();
		String expectedValue = PropsUtil.getDataPropertyValue("ActualAmtValue").trim();

		Assert.assertEquals(actualAcnt, expectedAcnt);
		Assert.assertEquals(actualValue, expectedValue);
	}

	@Test(description = "Verify onGoing Funding PopUp", dependsOnMethods = { "onGoingFundingPopUp" }, groups = {
			"DesktopBrowsers,sanity" }, priority = 23, enabled = true)
	public void onGoingFundingPopUpButtons() {
		Assert.assertTrue(customGoalEdit.deleteFunding().isDisplayed());
		if (PageParser.isElementPresent("closeEditButton", "SFG", null)) {
			Assert.assertTrue(customGoalEdit.verifyClose().isDisplayed());
			Assert.assertTrue(customGoalEdit.verifySaveChangesBtn().isDisplayed());
		} else {
			Assert.assertTrue(customGoalEdit.SFGFundingCancelButton().isDisplayed());
			Assert.assertTrue(customGoalEdit.SFGFundingSaveButton().isDisplayed());
		}
	}

	@Test(description = "Verify onGoing Funding PopUp for labels", dependsOnMethods = {
			"onGoingFundingPopUp" }, groups = { "DesktopBrowsers,sanity" }, priority = 24, enabled = true)
	public void onGoingPopUpForLabels() {
		String expected[] = PropsUtil.getDataPropertyValue("LabelList2").split(",");

		Assert.assertEquals(customGoalEdit.ongoingStartLabel().getText().trim(), expected[0]);
		Assert.assertEquals(customGoalEdit.ongoingEndLabel().getText().trim(), expected[1]);
		Assert.assertEquals(customGoalEdit.ongoingFreqLabel().getText().trim(), expected[2]);

	}

	@Test(description = "Verify onGoing Funding PopUp for label values", groups = {
			"DesktopBrowsers,sanity" }, dependsOnMethods = { "onGoingFundingPopUp" }, priority = 25, enabled = true)
	public void onGoingPopUpForLabelsValues() {

		Assert.assertEquals(customGoalEdit.ongoingFreqValue().getText().trim(),
				PropsUtil.getDataPropertyValue("ActualFrequency").trim());
		Assert.assertEquals(customGoalEdit.ongoingStartValue().getText().trim(),
				customGoalEdit.targateDateSelectMMM(1));
		Assert.assertEquals(customGoalEdit.ongoingEndDateValue().getText().trim(),
				customGoalEdit.targateDateSelectMMM(366));

	}

	@Test(description = "editing onGoing Funding PopUp", groups = { "DesktopBrowsers,sanity" }, dependsOnMethods = {
			"onGoingFundingPopUp" }, priority = 26, enabled = true)
	public void editOnGoingFundingPopUp() {

		SeleniumUtil.click(customGoalEdit.SFGAmountValue());
		customGoalEdit.SFGAmountValue().clear();
		customGoalEdit.SFGAmountValue().sendKeys(PropsUtil.getDataPropertyValue("UpdatedSavinAmount"));
		if (PageParser.isElementPresent("saveChangesButtonForMobile", "SFG", null)) {
			SeleniumUtil.click(customGoalEdit.verifySaveChangesBtn());
		} else {
			SeleniumUtil.click(customGoalEdit.SFGFundingSaveButton());
		}

		Assert.assertEquals(customGoalEdit.insufficientFundingMsg().get(0).getText().trim(),
				PropsUtil.getDataPropertyValue("insufficientErrorMsg2").trim());
		Assert.assertTrue(customGoalEdit.insufficientFundingIcon().get(0).isDisplayed());
		Assert.assertEquals(customGoalEdit.insufficientFundingError().get(0).getText().trim(),
				PropsUtil.getDataPropertyValue("insufficientErrorMsg").trim());

	}

	@Test(description = "verifying add fund ,save and cancel button after editing ongoing funding. ", groups = {
			"DesktopBrowsers,sanity" }, dependsOnMethods = { "editOnGoingFundingPopUp" }, priority = 27, enabled = true)
	public void verifyAddFundPopup() {
		Assert.assertTrue(customGoalEdit.addFunding().isDisplayed());
		if (PageParser.isElementPresent("closeEditButton", "SFG", null)) {
			Assert.assertTrue(customGoalEdit.verifyClose().isDisplayed());
		} else {
			Assert.assertTrue(customGoalEdit.SFGBackButton().isDisplayed());
		}
		Assert.assertTrue(customGoalEdit.nextbuttonClick().isDisplayed());
	}

	@Test(description = "verifying save button popUp after editing ongoing funding ", groups = {
			"DesktopBrowsers,sanity" }, dependsOnMethods = { "editOnGoingFundingPopUp" }, priority = 28, enabled = true)
	public void verifySavePopUp() {
		SeleniumUtil.click(customGoalEdit.nextbuttonClick());

		Assert.assertEquals(customGoalEdit.SaveHeader().getText().trim(),
				PropsUtil.getDataPropertyValue("SavePopUpHeader").trim());
		Assert.assertEquals(customGoalEdit.SaveHeaderPara1().getText().trim(),
				PropsUtil.getDataPropertyValue("SavePopUpPara1").trim());

		Assert.assertEquals(customGoalEdit.SaveHeaderPara2().getText().trim(),
				PropsUtil.getDataPropertyValue("SavePopUpPara2").trim());
	}

	@Test(description = "verifying save button option first should be checked and verifying text. ", groups = {
			"DesktopBrowsers,sanity" }, dependsOnMethods = { "verifySavePopUp" }, priority = 29, enabled = true)
	public void verifySavePopUpOption1() {

		Assert.assertEquals(customGoalEdit.SaveOptionsList().get(0).getText().trim(),
				PropsUtil.getDataPropertyValue("SaveOption1").trim());
		Assert.assertTrue(customGoalEdit.SaveOptionsList().get(0).getAttribute("class")
				.contains(PropsUtil.getDataPropertyValue("CheckedStatus")));
	}

	@Test(description = "verifying when option first is checked then cancel and add funds should be displayed in popUP. ", groups = {
			"DesktopBrowsers,sanity" }, dependsOnMethods = { "verifySavePopUp" }, priority = 30, enabled = true)
	public void addFundsVisibility() {
		Assert.assertTrue(customGoalEdit.SavepopUpCancelButton().isDisplayed());
		Assert.assertEquals(customGoalEdit.SavepopUpAddFunds().getText().trim(),
				PropsUtil.getDataPropertyValue("AddFundsButton").trim());
	}

	@Test(description = "verifying save button option second text. ", groups = {
			"DesktopBrowsers,sanity" }, dependsOnMethods = { "verifySavePopUp" }, priority = 31, enabled = true)
	public void verifySavePopUpOption2() {
		String actual = customGoalEdit.SaveOptionsList().get(1).getText().trim();
		String getText = PropsUtil.getDataPropertyValue("SaveOption2").trim();

		String currentDate = customGoalEdit.targateDateSelectMMM(366);
		String endDate = customGoalEdit.targateDateSelectMMM(762);

		String expected = getText + " " + currentDate + " to " + endDate;
		System.out.println(expected);

		Assert.assertEquals(actual, expected);
	}

	@Test(description = "verifying when option second is checked then cancel and done should be displayed in popUP. ", groups = {
			"DesktopBrowsers,sanity" }, dependsOnMethods = { "verifySavePopUp" }, priority = 32, enabled = true)
	public void doneButtonVisibility() {
		SeleniumUtil.click(customGoalEdit.SaveOptionsList().get(1));
		Assert.assertTrue(customGoalEdit.SavepopUpCancelButton().isDisplayed());
		Assert.assertEquals(customGoalEdit.SavepopUpAddFunds().getText().trim(),
				PropsUtil.getDataPropertyValue("DoneButton").trim());
	}

	@Test(description = "verifying save button option third text. ", groups = {
			"DesktopBrowsers,sanity" }, dependsOnMethods = { "doneButtonVisibility" }, priority = 33, enabled = true)
	public void verifySavePopUpOption3() {
		Assert.assertEquals(customGoalEdit.SaveOptionsList().get(2).getText().trim(),
				PropsUtil.getDataPropertyValue("SaveOption3").trim());

	}

	@Test(description = "verifying when option third is checked then cancel and done should be displayed in popUP. ", groups = {
			"DesktopBrowsers,sanity" }, dependsOnMethods = { "doneButtonVisibility" }, priority = 34, enabled = true)
	public void doneButtonVisibility2() {
		SeleniumUtil.click(customGoalEdit.SaveOptionsList().get(2));
		Assert.assertTrue(customGoalEdit.SavepopUpCancelButton().isDisplayed());
		Assert.assertEquals(customGoalEdit.SavepopUpAddFunds().getText().trim(),
				PropsUtil.getDataPropertyValue("DoneButton").trim());
	}

	@Test(description = "verifying add Funding popUp from save button with editing in Second account. ", groups = {
			"DesktopBrowsers,sanity" }, dependsOnMethods = { "doneButtonVisibility2" }, priority = 35, enabled = true)
	public void verifyingAddFunding() {
		SeleniumUtil.click(customGoalEdit.SaveOptionsList().get(0));

		SeleniumUtil.click(customGoalEdit.SavepopUpAddFunds());
		SeleniumUtil.click(customGoalEdit.FundingPageAccount());

		Assert.assertTrue(customGoalEdit.AcntSelectedTickMark().getAttribute("class").trim()
				.contains(PropsUtil.getDataPropertyValue("AcntSelectionTickMark").trim()));
	}

	@Test(description = "adding Funding from account second and verifying default value. ", groups = {
			"DesktopBrowsers,sanity" }, dependsOnMethods = { "verifyingAddFunding" }, priority = 36, enabled = true)
	public void AddingFundingFromAcnt2() {
		SeleniumUtil.click(customGoalEdit.account2Select());
		SeleniumUtil.waitForPageToLoad(1000);
		Assert.assertEquals(customGoalEdit.SFGAmountValue().getAttribute("value").trim(),
				PropsUtil.getDataPropertyValue("FundingValueForAcnt2").trim());
	}

	@Test(description = "verifying fund for account1 should reflect in ongoing funding status ", groups = {
			"DesktopBrowsers,sanity" }, dependsOnMethods = { "AddingFundingFromAcnt2" }, priority = 37, enabled = true)
	public void fundStatusForAcnt1() {
		if (PageParser.isElementPresent("saveChangesButtonForMobile", "SFG", null)) {
			SeleniumUtil.click(customGoalEdit.verifySaveChangesBtn());
		} else {
			SeleniumUtil.click(customGoalEdit.SFGFundingSaveButton());
		}

		Assert.assertEquals(customGoalEdit.afterEditScreenRightValue().get(3).getText().trim(),
				PropsUtil.getDataPropertyValue("EditGoalAccounts1"));
		Assert.assertEquals(customGoalEdit.afterEditScreenRightValue().get(4).getText().trim(),
				PropsUtil.getDataPropertyValue("Account1Amount"));
	}

	@Test(description = "verifying fund for account2 should reflect in ongoing funding status ", groups = {
			"DesktopBrowsers,sanity" }, dependsOnMethods = { "fundStatusForAcnt1" }, priority = 38, enabled = true)
	public void fundStatusForAcnt2() {
		Assert.assertEquals(customGoalEdit.afterEditScreenRightValue().get(5).getText().trim(),
				PropsUtil.getDataPropertyValue("EditGoalAccounts2").trim());
		Assert.assertEquals(customGoalEdit.afterEditScreenRightValue().get(6).getText().trim(),
				PropsUtil.getDataPropertyValue("Account2Amount").trim());
		SeleniumUtil.click(customGoalEdit.NextBtnStep1());
	}

	@Test(description = "verifying delete fund pop up.", groups = { "DesktopBrowsers,sanity" }, dependsOnMethods = {
			"fundStatusForAcnt2" }, priority = 39, enabled = true)
	public void verifyingDeletePopUp() {
		SeleniumUtil.waitForPageToLoad();
		SeleniumUtil.click(customGoalEdit.editgoalbutton());
		SeleniumUtil.waitForPageToLoad();
		SeleniumUtil.click(customGoalEdit.arrowIcon());
		SeleniumUtil.waitForPageToLoad(1000);
		SeleniumUtil.click(customGoalEdit.deleteFunding());

		Assert.assertEquals(customGoalEdit.SaveHeader().getText().trim(),
				PropsUtil.getDataPropertyValue("DeletePopupHeader2").trim());
	}

	@Test(description = "verifying delete fund pop up static data.", groups = {
			"DesktopBrowsers,sanity" }, dependsOnMethods = { "verifyingDeletePopUp" }, priority = 40, enabled = true)
	public void verifyingDeletePopUpStaticHeaders() {

		Assert.assertEquals(customGoalEdit.deletePopupHeader().get(0).getText().trim(),
				PropsUtil.getDataPropertyValue("DeletePopupSubHedaer1").trim());
		Assert.assertEquals(customGoalEdit.deletePopupHeader().get(1).getText().trim(),
				PropsUtil.getDataPropertyValue("DeletePopupSubHedaer2").trim());
	}

	@Test(description = "verifying delete  and cancel button in delete popup.", groups = {
			"DesktopBrowsers,sanity" }, dependsOnMethods = { "verifyingDeletePopUp" }, priority = 41, enabled = true)
	public void verifyingCancelAndDeleteButton() {

		Assert.assertTrue(customGoalEdit.SFGFundingDeletePopupCancelButton().isDisplayed());
		Assert.assertTrue(customGoalEdit.SFGFundingDeletePopupDeleteButton().isDisplayed());
	}

	@Test(description = "deleting funding and verifying changes.", groups = {
			"DesktopBrowsers,sanity" }, dependsOnMethods = { "verifyingDeletePopUp" }, priority = 42, enabled = true)
	public void deletFundingAndVerifying() {

		SeleniumUtil.click(customGoalEdit.SFGFundingDeletePopupDeleteButton());
		SeleniumUtil.waitForPageToLoad(3000);
		Assert.assertEquals(customGoalEdit.insufficientFundingMsg().get(0).getText().trim(),
				PropsUtil.getDataPropertyValue("insufficientErrorMsgAfterDeleteFunding").trim());
		Assert.assertTrue(customGoalEdit.insufficientFundingIcon().get(0).isDisplayed());
		Assert.assertEquals(customGoalEdit.insufficientFundingError().get(0).getText().trim(),
				PropsUtil.getDataPropertyValue("insufficientErrorMsg").trim());
	}

	@Test(description = "verifying edit Funding popup.", groups = { "DesktopBrowsers,sanity" }, dependsOnMethods = {
			"deletFundingAndVerifying" }, priority = 43, enabled = true)
	public void verifyingEditFundingPopUp() {
		SeleniumUtil.click(customGoalEdit.editArrow());
		Assert.assertEquals(customGoalEdit.SaveHeader().getText().trim(),
				PropsUtil.getDataPropertyValue("GoalAccountHeader").trim());
	}

	@Test(description = "verifying account name label 1 in edit funding popup.", groups = {
			"DesktopBrowsers,sanity" }, dependsOnMethods = {
					"verifyingEditFundingPopUp" }, priority = 44, enabled = true)
	public void verifyingAcntNameLabel1() {
		SeleniumUtil.waitForPageToLoad();// added page wait here
		String actual = customGoalEdit.acntNameLabel().get(0).getText().trim();
		System.out.println(actual);
		String expected = PropsUtil.getDataPropertyValue("goalaccouns1").trim();
		System.out.println(expected);
		Assert.assertEquals(actual, expected);
	}

	@Test(description = "verifying account name label 2 in edit funding popup.", groups = {
			"DesktopBrowsers,sanity" }, dependsOnMethods = {
					"verifyingEditFundingPopUp" }, priority = 45, enabled = true)
	public void verifyingAcntNameLabel2() {
		String actual = customGoalEdit.acntNameLabel().get(1).getText().trim();
		String expected = PropsUtil.getDataPropertyValue("goalaccouns2").trim();
		Assert.assertEquals(actual, expected);
	}

	@Test(description = "verifying accounts available balance in edit funding popup.", groups = {
			"DesktopBrowsers,sanity" }, dependsOnMethods = {
					"verifyingEditFundingPopUp" }, priority = 46, enabled = true)
	public void verifyingAcntAvailableBalnc() {
		for (int i = 0; i < customGoalEdit.BalanceAvailable().size(); i++) {
			Assert.assertEquals(customGoalEdit.BalanceAvailable().get(i).getText().trim(),
					PropsUtil.getDataPropertyValue("BalanceAvailable").trim());
		}
	}

	@Test(description = "verifying remove icons for both the accounts in edit popup.", groups = {
			"DesktopBrowsers,sanity" }, dependsOnMethods = {
					"verifyingEditFundingPopUp" }, priority = 47, enabled = true)
	public void verifyingRemoveIcons() {
		for (int i = 0; i < customGoalEdit.removeAmountButton().size(); i++) {
			Assert.assertTrue(customGoalEdit.removeAmountButton().get(i).isDisplayed());
		}
	}

	@Test(description = "verifying one time funding amount for both the accounts in edit popup.", groups = {
			"DesktopBrowsers,sanity" }, dependsOnMethods = {
					"verifyingEditFundingPopUp" }, priority = 48, enabled = true)
	public void verifyingFundingAmount() {
		for (int i = 0; i < customGoalEdit.amountInputBox().size(); i++) {
			Assert.assertEquals(customGoalEdit.amountInputBox().get(i).getAttribute("value").trim(),
					PropsUtil.getDataPropertyValue("UpdatedSavinAmount2").trim());
		}
	}

	@Test(description = "verifying viewDetail popup.", groups = { "DesktopBrowsers,sanity" }, dependsOnMethods = {
			"verifyingEditFundingPopUp" }, priority = 49, enabled = true)
	public void verifyingViewDetailPopUp() {
		SeleniumUtil.click(customGoalEdit.viewDetails().get(0));
		Assert.assertEquals(customGoalEdit.SaveHeader().getText().trim(),
				PropsUtil.getDataPropertyValue("EditGoalAccounts1Caps").trim());
		Assert.assertEquals(customGoalEdit.BalanceLabel().getText().trim(),
				PropsUtil.getDataPropertyValue("BalanceLabel").trim());
	}

	@Test(description = "verifying added goalname labels", groups = { "DesktopBrowsers,sanity" }, dependsOnMethods = {
			"verifyingViewDetailPopUp" }, priority = 50, enabled = true)
	public void verifyingAddedGoalsLabel() {
		String[] goalname = PropsUtil.getDataPropertyValue("AddedGoalsList").split(",");
		int size = customGoalEdit.goalNameLabel().size();
		for (int i = 0; i < size; i++) {
			Assert.assertEquals(customGoalEdit.goalNameLabel().get(i).getText(), goalname[i]);
		}

	}

	@Test(description = "verifying added goal amounts", groups = { "DesktopBrowsers,sanity" }, dependsOnMethods = {
			"verifyingViewDetailPopUp" }, priority = 51, enabled = true)
	public void verifyingAddedGoalsAmount() {
		String expected[] = PropsUtil.getDataPropertyValue("AddedGoalsFundingAmountList").split(",");
		for (int i = 0; i < customGoalEdit.goalAmountLabel().size(); i++) {
			String actual = customGoalEdit.goalAmountLabel().get(i).getText().trim();
			Assert.assertEquals(actual, expected[i]);
		}
	}

	@Test(description = "verifying save and cancel button should be there.", groups = {
			"DesktopBrowsers,sanity" }, dependsOnMethods = {
					"verifyingViewDetailPopUp" }, priority = 52, enabled = true)
	public void verifyingCancelAndSaveButton() {
		Assert.assertTrue(customGoalEdit.saveSFGAccountsDetails().isDisplayed());
		Assert.assertTrue(customGoalEdit.cancelAccountDetails().isDisplayed());
	}

	@Test(description = "verifying propgress bar visibility", groups = {
			"DesktopBrowsers,sanity" }, dependsOnMethods = {
					"verifyingViewDetailPopUp" }, priority = 53, enabled = true)
	public void verifyingProgressBarVisibility() {
		Assert.assertTrue(customGoalEdit.AllocationProgressBar().isDisplayed());
	}

	@Test(description = "verifying propgress bar label visibility", groups = {
			"DesktopBrowsers,sanity" }, dependsOnMethods = {
					"verifyingViewDetailPopUp" }, priority = 54, enabled = true)
	public void verifyingProgressBarLabel() {
		Assert.assertEquals(customGoalEdit.AllocationProgressBarLabel().getText().trim(),
				PropsUtil.getDataPropertyValue("AllocationProgressBarLabel").trim());
	}

	@Test(description = "verifying ListView Label", groups = { "DesktopBrowsers,sanity" }, dependsOnMethods = {
			"verifyingViewDetailPopUp" }, priority = 55, enabled = true)
	public void verifyingListViewLabel() {
		Assert.assertEquals(customGoalEdit.ListViewLabel().getText().trim(),
				PropsUtil.getDataPropertyValue("GoalUsingAccountLabel").trim());
	}

	@Test(description = "save view detail and edit account1 funding amount and verifying it should reflect in view details amount.", groups = {
			"DesktopBrowsers,sanity" }, dependsOnMethods = {
					"verifyingViewDetailPopUp" }, priority = 56, enabled = true)
	public void verifyingEditedAmountInViewDetailPopUP() {
		SeleniumUtil.click(customGoalEdit.saveSFGAccountsDetails());
		SeleniumUtil.waitForPageToLoad(2000);
		SeleniumUtil.click(customGoalEdit.amountInputBox().get(0));
		customGoalEdit.amountInputBox().get(0).clear();
		customGoalEdit.amountInputBox().get(0).sendKeys(PropsUtil.getDataPropertyValue("SendingNewAmountValue"));
		SeleniumUtil.click(customGoalEdit.viewDetails().get(0));
		SeleniumUtil.waitForPageToLoad(1000);
		Assert.assertEquals(customGoalEdit.EditedGoalAmountValue().getText().trim(),
				PropsUtil.getDataPropertyValue("EditedAmountValue").trim());
	}

	@Test(description = "save view detail and edit account1 funding amount and verifying it should reflect on edit goal landing page.", groups = {
			"DesktopBrowsers,sanity" }, dependsOnMethods = {
					"verifyingEditedAmountInViewDetailPopUP" }, priority = 57, enabled = true)
	public void verifyingEditedAmountInEditGoalLandingPage() {
		SeleniumUtil.click(customGoalEdit.EditPopupDoneButton());
		Assert.assertEquals(customGoalEdit.EditPopupSavingAmount().get(0).getText().trim(),
				PropsUtil.getDataPropertyValue("ReflectedEditAmountStaus"));
	}

	@Test(description = "Verifying add account popup", groups = { "DesktopBrowsers,sanity" }, dependsOnMethods = {
			"verifyingEditedAmountInEditGoalLandingPage" }, priority = 58, enabled = true)
	public void verifyingAddAccountPopup() {
		SeleniumUtil.click(customGoalEdit.EditPopupAddAccount());
		Assert.assertEquals(customGoalEdit.SaveHeader().getText().trim(),
				PropsUtil.getDataPropertyValue("AddAcntPopUPHeader"));
		Assert.assertEquals(customGoalEdit.addAccountPopupCurrencyLabel().getText().trim(),
				PropsUtil.getDataPropertyValue("AddPopUpCurrencyLabel").trim());
	}

	@Test(description = "adding new account and verifying it should reflect on edit goal landing page.", groups = {
			"DesktopBrowsers,sanity" }, dependsOnMethods = {
					"verifyingAddAccountPopup" }, priority = 59, enabled = true)
	public void addingNewAcnt() {
		SeleniumUtil.click(customGoalEdit.amountInputBox().get(0));
		customGoalEdit.amountInputBox().get(0).clear();
		customGoalEdit.amountInputBox().get(0).sendKeys(PropsUtil.getDataPropertyValue("NewAddedAcntFundingValue"));

		SeleniumUtil.click(customGoalEdit.AccountPopUpSave());
		Assert.assertEquals(customGoalEdit.addAccountPopupCurrencyLabelValue().get(0).getText().trim(),
				PropsUtil.getDataPropertyValue("NewAcntFundingAmount").trim());
		Assert.assertEquals(customGoalEdit.addedAcntName().getText().trim(),
				PropsUtil.getDataPropertyValue("NewAddedAcntLabel"));
	}

	@Test(description = "saving edited goal and verify on Edit goal Landing page for account1", groups = {
			"DesktopBrowsers,sanity" }, dependsOnMethods = { "addingNewAcnt" }, priority = 60, enabled = true)
	public void saveAndVerifyEditGoalLandingPageAcnt1() {

		SeleniumUtil.click(customGoalEdit.addFunding());
		SeleniumUtil.click(customGoalEdit.FundingPageAccount());
		SeleniumUtil.click(customGoalEdit.account2Select());
		SeleniumUtil.waitForPageToLoad();
		SeleniumUtil.click(customGoalEdit.SFGFundingSaveButton());

		SeleniumUtil.waitForPageToLoad(2000);
		Assert.assertEquals(customGoalEdit.afterSavingEditScreenRightValue().get(10).getText().trim(),
				PropsUtil.getDataPropertyValue("EditGoalAccounts1"));
		Assert.assertEquals(customGoalEdit.afterSavingEditScreenRightValue().get(11).getText().trim(),
				PropsUtil.getDataPropertyValue("Account1Amount"));
		SeleniumUtil.click(customGoalEdit.nextbuttonClick());
	}

}
