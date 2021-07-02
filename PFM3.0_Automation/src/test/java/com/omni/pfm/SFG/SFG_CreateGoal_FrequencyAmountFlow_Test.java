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
import java.awt.Toolkit;
import java.awt.datatransfer.StringSelection;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.Keys;
import org.openqa.selenium.interactions.Actions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.accessibility.examples.testng.webdriver.test.RunAccessibilityTest;
import com.omni.pfm.PageProcessor.PageParser;
import com.omni.pfm.pages.SFG.SFG_CreateGoal_GetStarted_Loc;
import com.omni.pfm.pages.SFG.SFG_LandingPage_Loc;
import com.omni.pfm.pages.SFG.SFG_createGoalEdit_Loc;
import com.omni.pfm.testBase.TestBase;
import com.omni.pfm.utility.PropsUtil;
import com.omni.pfm.utility.SeleniumUtil;

public class SFG_CreateGoal_FrequencyAmountFlow_Test extends TestBase {
	private static final Logger logger = LoggerFactory.getLogger(SFG_CreateGoal_FrequencyAmountFlow_Test.class);

	SFG_LandingPage_Loc SFG;
	SFG_createGoalEdit_Loc goalEdit;
	SFG_CreateGoal_GetStarted_Loc getStart;
	String sfgOngoingFundingValue = null;
	String AdditionalFundingValue = null;

	@BeforeClass(alwaysRun = true)
	public void testInit() throws InterruptedException {
		doInitialization("SFG_CreateGoal_FrequencyAmountFlow");
		SFG = new SFG_LandingPage_Loc(d);
		goalEdit = new SFG_createGoalEdit_Loc(d);
		getStart = new SFG_CreateGoal_GetStarted_Loc(d);
		d.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
	}

	@Test(description = "L1-40781:Verify Login Happens Successfully", groups = { "DesktopBrowsers" }, priority = 1)
	public void Login() throws Exception {
		SeleniumUtil.refresh(d);
		SeleniumUtil.waitForPageToLoad(2000);
		PageParser.forceNavigate("SFG", d);
	}

	@Test(description = " AT-16682,AT-16756,AT-16893,AT-16905,AT-58645: Verify that the User should have an option to go back to the 'New Goals' screen.   ", dependsOnMethods = {
			"Login" }, groups = { "DesktopBrowsers" }, priority = 2)
	public void verifyBackTonewGaolText() throws InterruptedException {
		logger.info(
				"AT-16756::Clicking on 'Create a Custom Goal' on the 'NEW GOALS' should land user on create Custom Goal page.");
		logger.info("AT-16893:Each predefined goal cards should be clickable.");
		logger.info(
				"AT-16905:Clicking on any Cards shall go to Step 1, to fill details in Create Goals Form. if user clicks on any other card apart from emergency savings, it should bring up the form filling page for the custom goal.");
		logger.info("AT-58645:On Clicking on any LLC, the user should be navigated to Step 1");
		if (SeleniumUtil.isDisplayed(SFG_createGoalEdit_Loc.goalGetStarted, 4)) {
			SeleniumUtil.click(goalEdit.startGoalGetStartButton());
		} else {
			SeleniumUtil.click(goalEdit.SFGCreateGoalButton());
		}

		SeleniumUtil.waitForPageToLoad(3000);
		logger.info("back to new goal link text");
		SeleniumUtil.click(getStart.SFGhighLevlcategories().get(0));
		SeleniumUtil.waitForPageToLoad(3000);
		SeleniumUtil.click(getStart.getSubCatText().get(0));
		SeleniumUtil.waitForPageToLoad(3000);
		if (PageParser.isElementPresent("closeButton", "SFG", null)) {
			Assert.assertTrue(SFG.verifyClose().isDisplayed());
		} else {
			Assert.assertEquals(SFG.backToNewGoal().getText(), PropsUtil.getDataPropertyValue("BacktoNewGoalText"));
		}
	}

	@Test(description = "  AT-16682: Verify that the User should have an option to go back to the 'New Goals' screen.   ", dependsOnMethods = {
			"verifyBackTonewGaolText" }, groups = { "DesktopBrowsers" }, priority = 3)
	public void verifyBackTonewGaolTextIcon() throws InterruptedException {
		logger.info("back to new goal link icon");
		if (PageParser.isElementPresent("closeButtonIcon", "SFG", null)) {
			Assert.assertTrue(SFG.verifyCloseButtonIcon().isDisplayed());
		} else {
			Assert.assertTrue(SFG.backToNewGoalIcon().isDisplayed());
		}
	}

	@Test(description = "AT-16900,AT-19599::Header shall be Displayed as Save for a Goal", dependsOnMethods = {
			"verifyBackTonewGaolText" }, groups = { "DesktopBrowsers" }, priority = 4)
	public void verifySaveForGoalHeader() throws InterruptedException {
		logger.info("Save for goals header ");
		logger.info("AT-19599:The steppers should be under the 'Save for a Goal' header.");
		Assert.assertEquals(SFG.GoalHeaderInsteps().getText(), PropsUtil.getDataPropertyValue("sfgCreateAgoalHeader"));
	}

	@Test(description = "verify current selected step when you clikc on low level categories ", dependsOnMethods = {
			"verifyBackTonewGaolText" }, groups = { "DesktopBrowsers" }, priority = 5)
	public void verifyCurrentlyActiveStep() {
		logger.info("Currently selected header when  click on subcategory");
		Assert.assertEquals(SFG.curentlyActiveStep().getText(), PropsUtil.getDataPropertyValue("curentlyActiveStep"));
	}

	@Test(description = "AT-19629,AT-19630,AT-19631:The step 1 name should be 'Set Up Goal'", dependsOnMethods = {
			"verifyBackTonewGaolText" }, groups = { "DesktopBrowsers" }, priority = 6)
	public void verifyAll3Steps() {
		logger.info("verify all Goal Step name");
		logger.info("AT-19630:the step 2 name should be 'Fund Goal'");
		logger.info("AT-19631:The Step 3 name should be 'View Summary'");
		String expectedsteps[] = PropsUtil.getDataPropertyValue("all3GoalSteps").split(",");
		for (int i = 0; i < SFG.all3GoalSteps().size(); i++) {
			Assert.assertEquals(
					SFG.all3GoalSteps().get(i).getText()
							.replaceAll(PropsUtil.getDataPropertyValue("curentlyActivetext"), "").trim(),
					expectedsteps[i]);
		}
	}

	@Test(description = "AT-19600:Steppers names should not be clickable", dependsOnMethods = {
			"verifyBackTonewGaolText" }, groups = { "DesktopBrowsers" }, priority = 7)
	public void verifyAll3StepsClick() {
		logger.info("verify all Goal Step name");
		for (int i = 0; i < SFG.all3GoalSteps().size(); i++) {
			SeleniumUtil.click(SFG.all3GoalSteps().get(i));
		}
	}

	@Test(description = " AT-SFG: 1 :Verify Predefined Goal Cards :Validate Landing Page Buttons/Tabs   ", dependsOnMethods = {
			"verifyAll3StepsClick" }, groups = { "DesktopBrowsers" }, priority = 8)
	public void verifynameFieldlable() {
		logger.info("validate goal name field lable");
		Assert.assertEquals(SFG.goalNameFieldLable().getText(), PropsUtil.getDataPropertyValue("GoalNameFieldLable"));
	}

	@Test(description = "AT-16767::For any goal name or Predefined Goal, there should one default image.", dependsOnMethods = {
			"verifyAll3StepsClick" }, groups = { "DesktopBrowsers" }, priority = 9)
	public void verifyNamefieldValue() {
		logger.info("verify default goal name");
		Assert.assertEquals(SFG.GoalNameInput().getAttribute("value"),
				PropsUtil.getDataPropertyValue("DefaultGoalName"));
	}

	@Test(description = "AT-16767::For any goal name or Predefined Goal, there should one default image.", dependsOnMethods = {
			"verifyAll3StepsClick" }, groups = { "DesktopBrowsers" }, priority = 10)
	public void verifyGoalcard() {
		logger.info("verify goalcard in step1");
		Assert.assertTrue(SFG.SFGViewSummeryGoalCard().isDisplayed());
	}

	@Test(description = "AT-16759,AT-16760,AT-16761,AT-17524::Upon clicking next or removing any data from field , it shall highlight field and turn it to Red (for Errors validation)", dependsOnMethods = {
			"verifyAll3StepsClick" }, groups = { "DesktopBrowsers" }, priority = 11)
	public void verifyEmptyNamefieldError() {
		logger.info("AT-16760 Create Custom Goal page should have certain required Fields.");
		logger.info("In Create Custom Goal Section, Goal Names should not be EMPTY, while creating any Goal.");
		logger.info("verify eeror message for empty goal name");
		logger.info("Goal Name should not be empty, It shall show following Message:  Enter Goal Name");
		SFG.GoalNameInput().clear();
		SFG.GoalNameInput().sendKeys(Keys.TAB);
		Assert.assertEquals(SFG.GoalNameErrors().getText(), PropsUtil.getDataPropertyValue("EmptyGoalNameError"));
	}

	@Test(description = "AT-16764,AT-16765::Speacial Characters are not allowed in Goal name.  ", dependsOnMethods = {
			"verifyAll3StepsClick" }, groups = { "DesktopBrowsers" }, priority = 12)
	public void verifySpecialCharNameFieldError() {
		logger.info(
				"AT-16765 Error Message on giving specia characters in Name shall be . ~^&-+}{'><() characters are not allowed");
		logger.info("verify goal name error message for Special char ");
		SFG.GoalNameInput().clear();
		// Press CTRL+V
		StringSelection s = new StringSelection(PropsUtil.getDataPropertyValue("SpecialCharAtGoalName"));
		Toolkit.getDefaultToolkit().getSystemClipboard().setContents(s, null);
		SeleniumUtil.scrollToWebElementMiddle(d, SFG.GoalNameInput());
		SeleniumUtil.click(SFG_LandingPage_Loc.sfgGoalInputBox);
		Actions act = new Actions(d);
		act.moveToElement(d.findElement(SFG_LandingPage_Loc.sfgGoalInputBox))
				.click(d.findElement(SFG_LandingPage_Loc.sfgGoalInputBox)).sendKeys(Keys.CONTROL + "v").build()
				.perform();
		d.findElement(SFG_LandingPage_Loc.sfgGoalInputBox).sendKeys(Keys.TAB);
		Assert.assertEquals(SFG.GoalNameErrors().getText(), "~^&-+}{'><\"()/\\ characters are not allowed");
	}

	@Test(description = "AT-17523::Verify Goal name should not exceed 30 characters.", dependsOnMethods = {
			"verifyAll3StepsClick" }, groups = { "DesktopBrowsers" }, priority = 13)
	public void verifymax30CharValidtion() {
		logger.info("verify goal name filed allow to enter max 30 char ");
		SFG.GoalNameInput().clear();
		SFG.GoalNameInput().sendKeys(PropsUtil.getDataPropertyValue("LongGoalName"));
		String text = SFG.GoalNameInput().getAttribute("value");
		if (text.length() != 30) {
			Assert.assertTrue(false);
		}

	}

	@Test(description = "AT-16774,AT-17525::Goal amount filed is mandatory one, it can not be left empty", dependsOnMethods = {
			"verifyAll3StepsClick" }, groups = { "DesktopBrowsers" }, priority = 14)
	public void verifyAmountFieldEmptyErrorMessage() {
		logger.info("verify Amount field for empty goal amount");
		logger.info(
				"AT-17525:Goal Amount should not be empty, It shall display following Error Message:   Enter Goal Amount");
		SFG.GoalAmountInput().clear();
		SFG.GoalAmountInput().sendKeys(Keys.TAB);
		Assert.assertEquals(SFG.GoalAmountErrors().getText(),
				PropsUtil.getDataPropertyValue("AmountFieldEmptyErrorMessage"));
	}

	@Test(description = "AT-16775,AT-16778,AT-17526::Only numeric value should be allowed in Goal Amount.", dependsOnMethods = {
			"verifyAll3StepsClick" }, groups = { "DesktopBrowsers" }, priority = 15)
	public void verifyAmountFieldInvalidAmount() {
		logger.info("verify goal amount for invalid goal name");
		logger.info("When invalid amount is entered, show inline error : “Invalid Amount”");
		logger.info(
				"Goal amount should be a NonZero, Positive number. Following error message shall be displayed when 0 (Zero) is provided: Goal amount should be greater than zero");
		SFG.amountValidation(PropsUtil.getDataPropertyValue("InvalidGoalAmount"));
		Assert.assertEquals(SFG.GoalAmountErrors().getText(), PropsUtil.getDataPropertyValue("withoutGoalAmount"));
	}

	@Test(description = " AT-SFG: 1 :Verify Predefined Goal Cards :Validate Landing Page Buttons/Tabs   ", dependsOnMethods = {
			"verifyAll3StepsClick" }, groups = { "DesktopBrowsers" }, priority = 16)
	public void verifyAmountFieldZeroAmount() {
		logger.info("verify goal amount for zero value");
		SFG.amountValidation(PropsUtil.getDataPropertyValue("GoalAmountZero"));
		Assert.assertEquals(SFG.GoalAmountErrors().getText(), PropsUtil.getDataPropertyValue("amountWithZero"));
	}

	@Test(description = " AT-SFG: 1 :Verify Predefined Goal Cards :Validate Landing Page Buttons/Tabs   ", dependsOnMethods = {
			"verifyAll3StepsClick" }, groups = { "DesktopBrowsers" }, priority = 17)
	public void verifyAmountFieldMaxDigits() {
		logger.info("verify goal amount with max 11 digit");
		SFG.amountValidation(PropsUtil.getDataPropertyValue("AmountMaxDigit"));
		String amt1 = (SFG.GoalAmountInput().getAttribute("value").replaceAll(",", "")).replaceAll(".00", "");

		if (amt1.length() != 11) {
			Assert.assertTrue(false);
		}
	}

	@Test(description = "AT-16780,AT-16780,AT-16781:Amount format should be formatted/changed to Preferred currency/settings.", dependsOnMethods = {
			"verifyAll3StepsClick" }, groups = { "DesktopBrowsers" }, priority = 18)
	public void verifyAmountFieldFormated() {
		logger.info("AT-16780 Amount format should be formatted/changed to Preferred currency/settings.");
		logger.info("AT-16781:Currency format should be formatted/changed to Preferred currency/settings.");
		Assert.assertEquals(SFG.GoalAmountInput().getAttribute("value"),
				PropsUtil.getDataPropertyValue("AmountMaxDigitFormated"));
	}

	@Test(description = "AT-196152 decimal values should be allowed after the point for amount fields", dependsOnMethods = {
			"verifyAll3StepsClick" }, groups = { "DesktopBrowsers" }, priority = 19)
	public void verifyAmountField2DecimalDigit() {
		boolean decimalvalue = false;

		if (StringUtils.substringAfter(SFG.GoalAmountInput().getAttribute("value"), ".").length() == 2) {
			decimalvalue = true;
		}
		Assert.assertTrue(decimalvalue);
	}

	@Test(description = "AT-16779,AT-17069:Pre-populated goal currency for Total Goal Amount should be preferred currency, since user has not selected any accounts as of now.", dependsOnMethods = {
			"verifyAll3StepsClick" }, groups = { "DesktopBrowsers" }, priority = 20)
	public void verifyTargateAmountFieldCurrencySymbool() {
		logger.info("AT-17069:Currrency Symbol should come inside Amount Text box");
		Assert.assertEquals(SFG.SFGCurrencySymbolInTrargateAmount().getText(),
				PropsUtil.getDataPropertyValue("SFGTargateAmountCurrency"));
	}

	@Test(description = "AT-19608:The radio buttons should give a choice between 'Target date' or ' How much I can save'", dependsOnMethods = {
			"verifyAll3StepsClick" }, groups = { "DesktopBrowsers" }, priority = 21)
	public void verifyTagatedateRadioButtonText() {
		logger.info("verify targatedate aadio button text");
		Assert.assertEquals(SFG.TagateRadioButtonText().getText(),
				PropsUtil.getDataPropertyValue("TargateRadioButtonText"));
	}

	@Test(description = " AT-19608:The radio buttons should give a choice between 'Target date' or ' How much I can save'", dependsOnMethods = {
			"verifyAll3StepsClick" }, groups = { "DesktopBrowsers" }, priority = 22)
	public void verifyFrequencyRadioButtonText() {
		logger.info("verify frequency radio button text");
		Assert.assertEquals(SFG.frequencyRadioButtonText().getText(),
				PropsUtil.getDataPropertyValue("FrequencyRaioButtontext"));
	}

	@Test(description = "verify next button in step1", dependsOnMethods = { "verifyAll3StepsClick" }, groups = {
			"DesktopBrowsers" }, priority = 23)
	public void verifyNextButton() {
		logger.info("verify next button");
		Assert.assertTrue(SFG.NextBtnStep1().isDisplayed());

	}

	@Test(description = "AT-16787,AT-19623::When none of the 2 options has been selected then Cursor should be under first field and error message shall be displayed with respect to field.", dependsOnMethods = {
			"verifyAll3StepsClick" }, groups = { "DesktopBrowsers" }, priority = 24)
	public void verifyTwoRadioButtonErroemessage() {
		logger.info("verify error message unselecting targate and frequency radio button");
		logger.info(
				"AT-19623:If the user does not select a radio button, show this error message : Select one of the two options to proceed");
		SeleniumUtil.click(SFG.NextBtnStep1());
		Assert.assertEquals(SFG.twoOptionError1().getText(), PropsUtil.getDataPropertyValue("twoOptionError1"));
	}

	@Test(description = "AT-16786,AT-16796,AT-16797,AT-19609,AT-19611::Unless one of the 2 options (target date or amount/frequecy) is selected and the values are entered, the submit action on this screen should be disabled.", dependsOnMethods = {
			"verifyTwoRadioButtonErroemessage" }, groups = { "DesktopBrowsers" }, priority = 25)
	public void verifyFrequencyField() {
		logger.info("AT-16796:Option to check Amount and Frequency should be mandatory if user chooses to");
		logger.info("AT-16797:Amount filed is mandatory one, it can not be left empty");
		logger.info("AT-19609:On selecting any of the radio buttons on step 1, show the corresponding field.");
		logger.info(
				"AT-19611:If the user selects 'I can save', he should be able to enter an amount and select a frequency.");
		SFG.amountValidation(PropsUtil.getDataPropertyValue("GoalAmount"));
		SeleniumUtil.click(SFG.GoalAmtFrqBtnInput());
		SFG.frequencyAmountValidation(PropsUtil.getDataPropertyValue("InvalidGoalAmount"));
		Assert.assertEquals(SFG.FrqAmountErrors().getText(), PropsUtil.getDataPropertyValue("WithouFrequencyAmount"));
	}

	@Test(description = "verify frequenct error  message for zero amount", dependsOnMethods = {
			"verifyFrequencyField" }, groups = { "DesktopBrowsers" }, priority = 26)
	public void verifyFrequencyFieldZeroValue() {
		logger.info("verify frequenct error  message for zero amount");
		SFG.frequencyAmountValidation(PropsUtil.getDataPropertyValue("GoalAmountZero"));

		Assert.assertEquals(SFG.FrqAmountErrors().getText(), PropsUtil.getDataPropertyValue("FrqamountWithZero"));
	}

	@Test(description = "verify frequenct error  message for morethan goal name", dependsOnMethods = {
			"verifyFrequencyField" }, groups = { "DesktopBrowsers" }, priority = 27)
	public void verifyFrequencyFieldMorethanGoalAmount() {
		logger.info("verify frequenct error  message for morethan goal name");
		SFG.frequencyAmountValidation(PropsUtil.getDataPropertyValue("FrqAmountMoreThanGoalAmount"));
		Assert.assertEquals(SFG.FrqAmountErrors().getText(),
				PropsUtil.getDataPropertyValue("FrqAmtMsgMoreThanGoalAmount"));
	}

	@Test(description = "AT-17572:Verify that All validations on frequency amount field is same as in Step 1.", dependsOnMethods = {
			"verifyFrequencyField" }, groups = { "DesktopBrowsers" }, priority = 28)
	public void verifyFrequencyFieldMaxDigit() {
		logger.info("verify frequenct error  message for max 11 digit");
		SFG.frequencyAmountValidation(PropsUtil.getDataPropertyValue("AmountMaxDigit"));
		String amt = (SFG.GoalAmtFrqInput().getAttribute("value").replaceAll(",", "")).replaceAll(".00", "");
		if (amt.length() != 11) {
			Assert.assertTrue(false);
		}
	}

	@Test(description = "AT-16803,AT-16804:Amount format should be formatted/changed to Preferred currency/settings.", dependsOnMethods = {
			"verifyFrequencyField" }, groups = { "DesktopBrowsers" }, priority = 29)
	public void verifyFrequencyAmountFieldFormated() {

		logger.info("AT-16804:Currency format should be formatted/changed to Preferred currency/settings.");
		Assert.assertEquals(SFG.GoalAmtFrqInput().getAttribute("value"),
				PropsUtil.getDataPropertyValue("AmountMaxDigitFormated"));
	}

	@Test(description = "AT-196152 decimal values should be allowed after the point for amount fields", dependsOnMethods = {
			"verifyFrequencyField" }, groups = { "DesktopBrowsers" }, priority = 30)
	public void verifyFrequencyAmountField2DecimalDigit() {
		boolean decimalvalue = false;

		if (StringUtils.substringAfter(SFG.GoalAmtFrqInput().getAttribute("value"), ".").length() == 2) {
			decimalvalue = true;
		}
		Assert.assertTrue(decimalvalue);
	}

	@Test(description = "AT-16802:Pre-populated goal currency for Total Goal Amount should be preferred currency, since user has not selected any accounts as of now.", dependsOnMethods = {
			"verifyTwoRadioButtonErroemessage" }, groups = { "DesktopBrowsers" }, priority = 31)
	public void verifyFrequencyAmountFieldCurrencySymbool() {

		Assert.assertEquals(SFG.SFGCurrencySymbolInFrequencyAmount().getText(),
				PropsUtil.getDataPropertyValue("SFGTargateAmountCurrency"));
	}

	@Test(description = "AT-16813::While selecting Frequency we shall get inline notifier message as  We recommend saving when you get paid ", dependsOnMethods = {
			"verifyTwoRadioButtonErroemessage" }, groups = { "DesktopBrowsers" }, priority = 32)
	public void verifyFrequencyInfoIconAndmessage() {
		logger.info("verify frequenct help icon and message");
		SFG.frequencyAmountValidation(PropsUtil.getDataPropertyValue("PerFrqGoalAmount"));

		Assert.assertTrue(SFG.helpsybol().isDisplayed());
		Assert.assertEquals(SFG.helpMessage().getText(), PropsUtil.getDataPropertyValue("SFGhelpMessage"));
	}

	@Test(description = "verify targate date goast text", dependsOnMethods = { "Login" }, groups = {
			"verifyTwoRadioButtonErroemessage" }, priority = 33)
	public void verifyTargetDateGoastText() {
		logger.info("verify targate date goast text");
		SeleniumUtil.click(SFG.GoalTargetBtnInput());
		if (PageParser.isElementPresent("goalTargetInputForMobile", "SFG", null)) {
			SeleniumUtil.click(SFG.GoalTargetInput());
		}
		SFG.GoalTargetInput().click();
		SeleniumUtil.waitForPageToLoad(100);
		String date = SFG.GoalTargetInput().getAttribute("placeholder");
		Assert.assertEquals(date, PropsUtil.getDataPropertyValue("ghostTextDateField"));
	}

	@Test(description = "AT-16788,AT-19613::Mandatory field if user chooses the target date option.", dependsOnMethods = {
			"verifyTwoRadioButtonErroemessage" }, groups = { "DesktopBrowsers" }, priority = 34)
	public void verifyTargateDateWithoutDate() {
		logger.info("verify targate date for empty value");
		logger.info(
				"AT-17529:Goal Target Date field should not be kept empty, It shall display following error message:  Enter Target Date");
		logger.info("AT-19613:The 'Target Date' field should be editable.");
		SFG.GoalTargetInput().clear();

		SFG.GoalTargetInput().sendKeys(Keys.TAB);

		Assert.assertEquals(SFG.TargetDateErrors().getText(), PropsUtil.getDataPropertyValue("WithoutDate"));
	}

	@Test(description = "AT-16793,AT-19610::While providing wrong date format user should get inline error message as Invalid Date", dependsOnMethods = {
			"verifyTwoRadioButtonErroemessage" }, groups = { "DesktopBrowsers" }, priority = 35)
	public void verifyTargateDateWithInvalidDate() {
		logger.info("AT-19610:If user selects target date, he should be allowed to enter a target date.");
		logger.info("verify targate date for invalid  value");
		SFG.targateDateValidation(PropsUtil.getDataPropertyValue("InvalidDateInput"));

		Assert.assertEquals(SFG.TargetDateErrors().getText(), PropsUtil.getDataPropertyValue("invalidDate"));
	}

	@Test(description = "AT-17070,AT-19617:Verify that the calendar works correctly.", dependsOnMethods = {
			"verifyTwoRadioButtonErroemessage" }, groups = { "DesktopBrowsers" }, priority = 36)
	public void verifyTargateDateWithStartDate() {
		logger.info("verify targate date for targate date equal to start date");
		logger.info("AT-19617:The error message should be Target date should be before <Max Target Date>");
		logger.info("AT-17070:Verify that the calendar works correctly.");
		SFG.targateDateValidation(SFG.targateDateSelect(0));
		Assert.assertEquals(SFG.TargetDateErrors().getText(),
				PropsUtil.getDataPropertyValue("TargateDateErrorMessage") + SFG.targateDateSelectMMM(1));
	}

	@Test(description = "verify satrt date goast text", dependsOnMethods = {
			"verifyTwoRadioButtonErroemessage" }, groups = { "DesktopBrowsers" }, priority = 37)
	public void verifyGoalStartDate() {
		logger.info("verify start date Ghost text");
		SFG.targateDateValidation(SFG.targateDateSelect(2));
		SFG.GoalStartInput().click();
		SeleniumUtil.waitFor(2);
		String date = SFG.GoalStartInput().getAttribute("placeholder");
		System.out.println(date);
		Assert.assertEquals(date, PropsUtil.getDataPropertyValue("ghostTextDateField"));
	}

	@Test(description = "AT-16790,AT-16791,AT-16792::The date field can be auto-populated after selecting from calendar or user can enter manual dates as well.", dependsOnMethods = {
			"verifyTwoRadioButtonErroemessage" }, groups = { "DesktopBrowsers" }, priority = 38)
	public void verifyGoalEndDateUpdated() {
		logger.info("AT-16791:Target Date should be a future date only.");
		logger.info("AT-16792:User can also provide input manually for Target Date in Step 1");
		Assert.assertEquals(SFG.GoalTargetInput().getAttribute("value"), SFG.targateDateSelect(2));
	}

	@Test(description = "AT-17528::Goal Start Date field should not be kept empty, It shall display following error message:  Enter Start Date", dependsOnMethods = {
			"verifyTwoRadioButtonErroemessage" }, groups = { "DesktopBrowsers" }, priority = 39)
	public void verifyGoalStartDateWithOutDate() {
		logger.info("verify start date for empty value");
		SFG.GoalStartInput().click();

		SFG.GoalStartInput().clear();
		SFG.GoalStartInput().sendKeys(Keys.TAB);

		Assert.assertEquals(SFG.StartDateErrors().getText(), PropsUtil.getDataPropertyValue("WithoutStartDate"));
	}

	@Test(description = "verify start date for invalid value", dependsOnMethods = {
			"verifyTwoRadioButtonErroemessage" }, groups = { "DesktopBrowsers" }, priority = 40)
	public void verifyGoalStartDateWithInvalidDate() {
		logger.info("verify start date for invalid value");
		SFG.startDateValidation(PropsUtil.getDataPropertyValue("InvalidDateInput"));
		SeleniumUtil.waitForPageToLoad();
		Assert.assertEquals(SFG.StartDateErrors().getText(), PropsUtil.getDataPropertyValue("invalidStartDate"));
	}

	@Test(description = "verify start date for greater than goal targate date", dependsOnMethods = {
			"verifyTwoRadioButtonErroemessage" }, groups = { "DesktopBrowsers" }, priority = 41)
	public void verifyGoalStartDateGreaterTahnTargateDate() {
		logger.info("verify start date for greater than goal targate date");
		SFG.startDateValidation(SFG.targateDateSelect(2));
		SeleniumUtil.waitForPageToLoad();
		Assert.assertEquals(SFG.StartDateErrors().getText(), PropsUtil.getDataPropertyValue("startDateBeforeTarget"));
	}

	@Test(description = "verify frequency dropdown", dependsOnMethods = {
			"verifyTwoRadioButtonErroemessage" }, groups = { "DesktopBrowsers" }, priority = 42)
	public void frequncyField() throws InterruptedException {
		logger.info("verify frequency dropdown");
		SeleniumUtil.click(SFG.GoalAmtFrqBtnInput());
		Assert.assertTrue(SFG.FrqDropDownIcon().isDisplayed());
	}

	@Test(description = "AT-16805,AT-16808:Once user selects this field, Frequncy Dropdown should open up.", dependsOnMethods = {
			"verifyTwoRadioButtonErroemessage" }, groups = { "DesktopBrowsers" }, priority = 43)
	public void VerifyAllFrequency() throws InterruptedException {
		logger.info("verify all frequency");
		logger.info(
				"Default list of frequencies to be displayed are :  Frequencies to be shown on the UI:                   1. Every Week                   2. Every Other Friday                   3. Every Two Weeks                   4. Every Month                   5. Every Two Months                   6. Every Three Months");
		String expected[] = PropsUtil.getDataPropertyValue("SFGFrequncyValues").split(",");

		SeleniumUtil.click(SFG.frequencyDropdown1());
		for (int i = 0; i < SFG.allFrequency().size(); i++) {

			Assert.assertEquals(SFG.allFrequency().get(i).getText(), expected[i]);
		}

	}

	@Test(description = "verify selected frequency", dependsOnMethods = {
			"verifyTwoRadioButtonErroemessage" }, groups = { "DesktopBrowsers" }, priority = 44)
	public void verifySelectedFrequency() throws InterruptedException {
		logger.info("verify selected frequency");
		String expectedFrq = SFG.allFrequency().get(0).getText();
		SeleniumUtil.click(SFG.allFrequency().get(0));
		SeleniumUtil.waitForPageToLoad(1000);
		Assert.assertEquals(SFG.frequencyDropdown1().getText(), expectedFrq);

	}

	@Test(description = " AT-16670,AT-16672,AT-16673,AT-16674,AT-16675::Verify that the Start date is auto-filled with the next frequency date.", dependsOnMethods = {
			"verifyTwoRadioButtonErroemessage" }, groups = { "DesktopBrowsers" }, priority = 45)
	public void verifyGoalStartDateDefaultvalue() {
		logger.info(
				"AT-16672:Verify that if the selected start date is Every Week then the start date will be the next business date after the current date.");
		logger.info(
				"AT-16673:Verify that if the selected start date is Every Two Weeks then the start date will be the next business date after the current date.");
		logger.info(
				"AT-16674:Verify that if the selected start date is Every Two Months then the start date will be the next business date after the current date.");
		logger.info(
				"AT-16675:Verify that if the selected start date is Every Three Months then the start date will be the next business date after the current date.");

		for (int i = 0; i < SFG.allFrequency().size(); i++) {
			SeleniumUtil.click(SFG.frequencyDropdown1());
			SeleniumUtil.click(SFG.allFrequency().get(i));
			SeleniumUtil.waitForPageToLoad(2000);
			Assert.assertEquals(SFG.GoalStartInput().getAttribute("value"), SFG.targateDateSelect(2));

		}

	}

	@Test(description = "AT-16671::Verify that the user can modify the start date to his choice of date.", dependsOnMethods = {
			"verifyTwoRadioButtonErroemessage" }, groups = { "DesktopBrowsers" }, priority = 46)
	public void verifyGoalStartDateChange() {
		SFG.GoalStartInput().clear();
		SFG.GoalStartInput().sendKeys(SFG.targateDateSelect(4));
		Assert.assertEquals(SFG.GoalStartInput().getAttribute("value"), SFG.targateDateSelect(4));

	}

	@Test(description = "AT-17071::Verify that the next button should get activated if the user enters data in all the required fields.", dependsOnMethods = {
			"verifyTwoRadioButtonErroemessage" }, groups = { "DesktopBrowsers" }, priority = 47)
	public void step2LandingPage() throws InterruptedException {
		SeleniumUtil.refresh(d);
		SeleniumUtil.waitForPageToLoad(2000);
		PageParser.forceNavigate("SFG", d);
		SFG.step2LandingPage(PropsUtil.getDataPropertyValue("SFGGoalName1"),
				PropsUtil.getDataPropertyValue("SFGGoalAmount1"), 0,
				PropsUtil.getDataPropertyValue("SFGFrequencyAmount1"));
		SeleniumUtil.waitForPageToLoad(2000);
		String actual = SFG.curentlyActiveStep().getText();
		String expected = PropsUtil.getDataPropertyValue("curentlyActiveStep2");
		if (!actual.contains(expected)) {
			Assert.fail("Currently active step text is not as expected. Expected :: '" + expected + "' and actual :: '"
					+ actual + "'");
		}
	}

	@Test(description = "Verify step2 back to new Goal button", dependsOnMethods = { "step2LandingPage" }, groups = {
			"DesktopBrowsers" }, priority = 48)
	public void BacktoNewGoalButtonInSTep2() throws InterruptedException {
		logger.info("Verify step2 back to new Goal button");
		if (PageParser.isElementPresent("closeButton", "SFG", null)) {
			Assert.assertTrue(SFG.verifyClose().isDisplayed());
		}

		else {
			Assert.assertEquals(SFG.backToNewGoal().getText(), PropsUtil.getDataPropertyValue("BacktoNewGoalText"));
		}
	}

	@Test(description = "AT-17558::Verify that the label for account dropdown should be Save money in account(s)", dependsOnMethods = {
			"step2LandingPage" }, groups = { "DesktopBrowsers" }, priority = 49)
	public void validateAccountFieldLable() throws InterruptedException {
		logger.info("verify account field lable");
		Assert.assertEquals(SFG.AccountfieldLable().getText(), PropsUtil.getDataPropertyValue("SFGAccountfieldLable"));
	}

	@Test(description = "verify account field drop down icon", dependsOnMethods = { "step2LandingPage" }, groups = {
			"DesktopBrowsers" }, priority = 50)
	public void verifyAccountDropdownIcon() throws InterruptedException {
		logger.info("verify account field drop down icon");
		Assert.assertTrue(SFG.AccountdropDownIcon().isDisplayed());
	}

	@Test(description = "verify account field dropdown value", dependsOnMethods = { "step2LandingPage" }, groups = {
			"DesktopBrowsers" }, priority = 51)
	public void verifyAccountDropdownValue() throws InterruptedException {
		logger.info("verify account field dropdown value");
		Assert.assertEquals(SFG.AccountdropDownField().getAttribute("value"),
				PropsUtil.getDataPropertyValue("SFGDefauldAccountValue"));
	}

	@Test(description = "verify ongoing funding rule lables", dependsOnMethods = { "step2LandingPage" }, groups = {
			"DesktopBrowsers" }, priority = 52)
	public void verifyOnGoingFundingLable() throws InterruptedException {
		logger.info("verify ongoing funding rule lables");
		Assert.assertEquals(SFG.OneTimeFunddingLable().getText(),
				PropsUtil.getDataPropertyValue("SFGOnGoingFuddingLable"));
	}

	@Test(description = "verify one time allocation amount allocate text", dependsOnMethods = {
			"step2LandingPage" }, groups = { "DesktopBrowsers" }, priority = 53)
	public void verifyOnGoingFundingAllocateText() throws InterruptedException {
		Assert.assertEquals(SFG.OneTimeAlocateText().getText(), PropsUtil.getDataPropertyValue("SFGAllocateText"));
	}

	@Test(description = "verify one time allocation amount post text", dependsOnMethods = {
			"step2LandingPage" }, groups = { "DesktopBrowsers" }, priority = 54)
	public void verifyOnGoingFundingPostText() throws InterruptedException {
		System.out.println(SFG.OntimeFundingPostText().getText());
		Assert.assertEquals(SFG.OntimeFundingPostText().getText(),
				PropsUtil.getDataPropertyValue("SFGOntimeFundingPostText"));
	}

	@Test(description = "verify one time allocation amount optional text", dependsOnMethods = {
			"step2LandingPage" }, groups = { "DesktopBrowsers" }, priority = 55)
	public void verifyOnGoingFundingOptionalText() throws InterruptedException {
		Assert.assertEquals(SFG.OntimeFundingOptionalText().getText(),
				PropsUtil.getDataPropertyValue("SFGOptionalText"));
	}

	@Test(description = "AT-16678,AT-17532,AT-17565,AT-17566,AT-17568:Verify that If the total allocation amount given by the user is equal to the target goal amount, the system should show the following message:   Planned funding amount is equal to total goal amount. You will meet your goal with this allocation.", dependsOnMethods = {
			"step2LandingPage" }, groups = { "DesktopBrowsers" }, priority = 56)
	public void verifyOnGoingFundingAmountEqualsgoalAmount() throws InterruptedException {
		SFG.OntimeFundingTextBox().clear();
		logger.info("enter on time funding rule amount equals goal amount");
		logger.info(
				"AT-17532:If One time allocation amount is euqal to Target Goal Amount, It shall display following Error Message:   Your funding amount is equal to total goal amount. You will meet your goal with this allocation.");
		logger.info(
				"AT-17565:Verify that if the total allocation amount given by the user is equal to the target goal amount, show an information message.");
		logger.info(
				"AT-17566:Verify that if the total allocation amount given by the user is equal to the target goal amount, funding options should get diasabled.");
		logger.info(
				"AT-17567:Verify that if the total allocation amount given by the user is equal to the target goal amount, the next button should remain enabled.");
		logger.info(
				"AT-17568Verify that if the one time allocation amount for an account given by the user is greater than available balance amount, show an error message.");
		logger.info(
				"AT-17569:Verify that if the one time allocation amount for an account given by the user is greater than available balance amount, funding options should get disabled.");
		SFG.OntimeFundingTextBox().sendKeys(PropsUtil.getDataPropertyValue("SFGGoalAmount1"));
		SFG.OntimeFundingTextBox().sendKeys(Keys.TAB);
		// SeleniumUtil.click(SFG.NextBtnStep1());
		Assert.assertEquals(SFG.OntimeFundingAmountEqualAmountMessage().getText(),
				PropsUtil.getDataPropertyValue("OntimeFundingAmountEqualAmountMessage"));
	}

	@Test(description = "AT-16677,AT-17531,AT-17561,AT-17562,AT-17563:Verify that If the total one time allocation amount given by the user is greater than target goal amount, the system should show the following message: Planned funding amount is greater than total goal amount. You might want to revise your funding amount or your goal amount.", dependsOnMethods = {
			"step2LandingPage" }, groups = { "DesktopBrowsers" }, priority = 57)
	public void verifyOnGoingFundingAmountGreatergoalAmount() throws InterruptedException {
		SFG.OntimeFundingTextBox().clear();
		logger.info("enter on time funding rule amount equals goal amount");
		logger.info(
				"AT-17531:One time allocation amount should not be Greater than Target Goal Amount. It shall dispplay following error Message:  Your funding amount is greater than total goal amount. You will need to revise your funding amount to proceed.");
		logger.info(
				"AT-17561::Verify that if the total one time allocation amount given by the user is greater than target goal amount, the system should show an error message.");
		logger.info(
				"AT-17562:Verify that if the total one time allocation amount given by the user is greater than target goal amount, the funding options should get disabled.");
		logger.info(
				"AT-17563:Verify that if the total one time allocation amount given by the user is greater than target goal amount, the back button will remain enabled.");
		SFG.OntimeFundingTextBox().sendKeys(PropsUtil.getDataPropertyValue("SFGOntimeFundingAmount1"));
		SFG.OntimeFundingTextBox().sendKeys(Keys.TAB);
		SeleniumUtil.click(SFG.NextBtnStep1());
		Assert.assertEquals(SFG.OntimeFundingErrorMessage().getText(),
				PropsUtil.getDataPropertyValue("OntimeFundingErrorMessage"));
	}

	@Test(description = "AT-16669,AT-17539::Verify that the allocation amount should, by default, be pre-filled to 0.", dependsOnMethods = {
			"step2LandingPage" }, groups = { "DesktopBrowsers" }, priority = 59)
	public void verifyOnGoingFundingAmountDefaultValue() throws InterruptedException {
		logger.info("AT-17539:Allocation amount should be As a default, pre-fill with zero (0).");
		Assert.assertEquals(SFG.OntimeFundingTextBox().getAttribute("placeholder"),
				PropsUtil.getDataPropertyValue("SFGOnetimeFundingdefaultvalue"));
	}

	@Test(description = "AT-16663,AT-16666,AT-17536,AT-17542,AT-18129::Verify that the allocation amount should only allow numeric and absolute values.", dependsOnMethods = {
			"step2LandingPage" }, groups = { "DesktopBrowsers" }, priority = 60)
	public void verifyOnGoingFundingAmountAlphaNumeric() throws InterruptedException {
		logger.info("one time funding amount alphanumeric validation");
		logger.info(
				"AT-16666-Verify that if an invalid allocation amount is entered, the system should show an inline error Invalid Amount.");
		logger.info(
				"AT-17536:When invalid amount in Allocation Amount is entered, show inline error : “Invalid Amount”");
		logger.info(
				"AT-17542:Amount copy and paste with invalid characters should throw invalid amount message 'Invalid Amount'.");
		logger.info(
				"AT-18129:Verify that if the user enters an invalid earmarked amount, all the fields should get disabled.");
		StringSelection s = new StringSelection(PropsUtil.getDataPropertyValue("SFGOngoingFundingSpecialChar"));
		Toolkit.getDefaultToolkit().getSystemClipboard().setContents(s, null);
		SeleniumUtil.click(SFG_LandingPage_Loc.oneTimeFundingTextBox);
		d.findElement(SFG_LandingPage_Loc.oneTimeFundingTextBox).clear();
		Actions act = new Actions(d);
		act.moveToElement(d.findElement(SFG_LandingPage_Loc.oneTimeFundingTextBox))
				.click(d.findElement(SFG_LandingPage_Loc.oneTimeFundingTextBox)).sendKeys(Keys.CONTROL + "v").build()
				.perform();
		SFG.OntimeFundingTextBox().clear();
		SFG.OntimeFundingTextBox().sendKeys(Keys.TAB);
		SeleniumUtil.waitForPageToLoad(1000);
		Assert.assertEquals(SFG.OntimeFundingErrorMessage().getText(),
				PropsUtil.getDataPropertyValue("SFGOngoingFundingSpecialCharMessage"));
	}

	@Test(description = "AT-16664,AT-16665,AT-17534,AT-17535,AT-17552,AT-17570,AT-17571::Verify that in the allocation amount, the value cannot exceed available balance.", dependsOnMethods = {
			"step2LandingPage" }, groups = { "DesktopBrowsers" }, priority = 61)
	public void verifyOnGoingFundingAmountGreaterThanAvailableBalance() throws InterruptedException {
		logger.info(
				"AT-16665-Verify that if the allocation amount exceeds available balance, the system should show an inline error : Amount cannot exceed available balance.");
		logger.info(
				"AT-17534:Allocation amount should follow below validations which are as per a#43)%&$*!mount standardization: Value cannot exceed free available balance");
		logger.info(
				"AT-17535:When the amount entered in Allocation Amount, exceeds free available balance, show inline error : “Amount should be less than or equal to available balance”");
		logger.info(
				"AT-17552:Verify that if the user provides a value as a one time funding from a valid account, show an inline error message that there is not enough free available balance to allocate towards this goal.");
		logger.info(
				"AT-17570:Verify that if the one time allocation amount for an account given by the user is greater than available balance amount, the back button should be enabled.");
		logger.info(
				"AT-17571:Verify that if the one time allocation amount for an account given by the user is greater than available balance amount, the next button should be disabled  until user fixes the initial funding amount.");
		SFG.OntimeFundingTextBox().clear();
		SFG.OntimeFundingTextBox()
				.sendKeys(PropsUtil.getDataPropertyValue("SFGEditOnGoungFundingAmountGreaterThanAvailableBalance"));
		SFG.OntimeFundingTextBox().sendKeys(Keys.TAB);
		SeleniumUtil.waitForPageToLoad();
		Assert.assertEquals(SFG.OntimeFundingErrorMessage().getText(),
				PropsUtil.getDataPropertyValue("SFGEditOnGoungFundingAmountGreaterThanAvailableBalanceMessage"));
	}

	@Test(description = "AT-16668,AT-17538,AT-19612::Verify that the allocation amount should be formatted to preferred settings when tabbed out.", dependsOnMethods = {
			"step2LandingPage" }, groups = { "DesktopBrowsers" }, priority = 62)
	public void verifyOnGoingFundingAmountTabOutValue() throws InterruptedException {
		logger.info("AT-17538:In Allocation Amount, Amount should be formatted to preferred settings when tabbed out.");
		logger.info("AT-19612:The ongoing funding and the one time funding should be reformatted.step 2");
		Assert.assertEquals(SFG.OntimeFundingTextBox().getAttribute("value"),
				PropsUtil.getDataPropertyValue("SFGOntimeFundingtabOutValue"));
	}

	@Test(description = "AT-196152 decimal values should be allowed after the point for amount fields", dependsOnMethods = {
			"step2LandingPage" }, groups = { "DesktopBrowsers" }, priority = 63)
	public void verifyOneTimeFundingAmountField2DecimalDigit() {
		boolean decimalvalue = false;

		if (StringUtils.substringAfter(SFG.GoalAmountInput().getAttribute("value"), ".").length() == 2) {
			decimalvalue = true;
		}
		Assert.assertTrue(decimalvalue);
	}

	@Test(description = "verify recurring on going funding lable", dependsOnMethods = { "step2LandingPage" }, groups = {
			"DesktopBrowsers" }, priority = 64)
	public void verifyRecurOnGoingFundingLabel() throws InterruptedException {
		logger.info("recurring on going funding lable");
		SFG.OntimeFundingTextBox().clear();
		SFG.OntimeFundingTextBox().sendKeys(Keys.TAB);
		Assert.assertEquals(SFG.recurOnGoingFundingHeader().getText(),
				PropsUtil.getDataPropertyValue("recurOnGoingFundingHeader"));
	}

	@Test(description = "AT-16680,AT-16785:Verify that if the planned amount per frequency is given, the system should calculate the projected date based on the target goal amount using the formula - (Target Goal Amount/allocation amount per frequency = N number of frequency periods in future that will take to save this target goal amount)", dependsOnMethods = {
			"step2LandingPage" }, groups = { "DesktopBrowsers" }, priority = 65)
	public void verifyRecurOnGoingFundingAmount() throws InterruptedException {
		logger.info("recurring on going funding amount with frequency");
		logger.info(
				"AT-16785:Mandatory Field: User should either be able to give a target date based on which application should calculate the planned funding or give a specific amount to be saved at a defined frequency.");
		Assert.assertEquals(SFG.recurOnGoingFundingValue().getText(),
				"$" + PropsUtil.getDataPropertyValue("SFGFrequencyAmount1") + ".00 "
						+ PropsUtil.getDataPropertyValue("SFGFRequency1"));
	}

	@Test(description = "AT-17576,AT-17579:Verify that if the user has more than one savings account, the ghost text should become Select account(s)", dependsOnMethods = {
			"step2LandingPage" }, groups = { "DesktopBrowsers" }, priority = 66)
	public void verifyAccountPopUpHeader() throws InterruptedException {
		logger.info("Step 2 account popup header");
		logger.info("AT-17579:Verify that label for account popup header has to be Select Account(s)");
		SeleniumUtil.click(SFG.AccountdropDownField());
		SeleniumUtil.waitForPageToLoad(1000);
		Assert.assertEquals(SFG.AcconuPopUpHeader().getText(), PropsUtil.getDataPropertyValue("SFGAcconuPopUpHeader"));
	}

	@Test(description = "Step 2 account popup -Save in more than one account", dependsOnMethods = {
			"step2LandingPage" }, groups = { "DesktopBrowsers" }, priority = 67)
	public void verifysaveMorethanAccountLabel() throws InterruptedException {
		logger.info("Step 2 account popup -Save in more than one account");
		Assert.assertEquals(SFG.saveMoreThanAccountLabel().getText(),
				PropsUtil.getDataPropertyValue("SFGSaveMoreThanOneAccountLabel"));

	}

	@Test(description = "verity toggle button", dependsOnMethods = { "step2LandingPage" }, groups = {
			"DesktopBrowsers" }, priority = 68)
	public void verifyMaultiAccountToggle() throws InterruptedException {
		logger.info("verity toggle button");
		Assert.assertTrue(SFG.MultiAccountToggel().isDisplayed());
	}

	@Test(description = "The currency for your goal must be the same as the funds in the account.", dependsOnMethods = {
			"step2LandingPage" }, groups = { "DesktopBrowsers" }, priority = 69)
	public void verifCurrencyInfoMessage() throws InterruptedException {
		logger.info("The currency for your goal must be the same as the funds in the account.");
		Assert.assertEquals(SFG.currencuInfoMessage().getText(),
				PropsUtil.getDataPropertyValue("SFGcurrencuInfoMessage"));

	}

	@Test(description = "AT-16695::Verify the account name format should be   <Account Type>           <FI Name> | <Account Name/Nickname> (show nickname if nickname is available)           <Account Number> (<Account Balance>) on the select accounts pop up", dependsOnMethods = {
			"step2LandingPage" }, groups = { "DesktopBrowsers" }, priority = 70)
	public void verifallAccount() throws InterruptedException {
		logger.info("supoort only checking saving and investment account");
		String expectedAccount[] = PropsUtil.getDataPropertyValue("SFGAllAccount").split("/");

		ArrayList<String> actual = new ArrayList<String>();
		actual.add(SFG.unSelectAccount(0).getText());
		actual.add(SFG.selectAccount(1).getText());
		actual.add(SFG.selectAccount(2).getText());

		SeleniumUtil.waitForPageToLoad(1000);

		for (int i = 0; i < actual.size(); i++) {
			System.out.println(actual.get(i));
			Assert.assertEquals(actual.get(i).trim(), expectedAccount[i].trim());
		}

	}

	@Test(description = "availablebalance should be same as Account balance in account name and decimal should be .00", dependsOnMethods = {
			"step2LandingPage" }, groups = { "DesktopBrowsers" }, priority = 71)
	public void verifallAvailableBalance() throws InterruptedException {
		logger.info("availablebalance should be same as Account balance in account name and decimal should be .00");
		ArrayList<String> actual = new ArrayList<String>();
		actual.add(SFG.unselectedAvailableBalance(0).getText());
		actual.add(SFG.selectedAvailableBalance(1).getText());
		actual.add(SFG.selectedAvailableBalance(2).getText());
		String expectedAvailableBalance[] = PropsUtil.getDataPropertyValue("SFGAvailableBalance").split("/");
		for (int i = 0; i < actual.size(); i++) {
			System.out.println(actual.get(i));
			Assert.assertEquals(actual.get(i).trim(), expectedAvailableBalance[i].trim());
		}
	}

	@Test(description = "verify Allocate text for All Account", dependsOnMethods = { "step2LandingPage" }, groups = {
			"DesktopBrowsers" }, priority = 72)
	public void AccountPopUPonTimeFundingPretext() throws InterruptedException {
		logger.info("verify Allocate text for All Account");

		Assert.assertEquals(SFG.AccountPopUPonTimeFundingpretext().get(0).getText().trim(),
				PropsUtil.getDataPropertyValue("SFGAllocateText"));

	}

	@Test(description = "verify Post text for All Account", dependsOnMethods = { "step2LandingPage" }, groups = {
			"DesktopBrowsers" }, priority = 73)
	public void AccountPopUPonTimeFundingPosttext() throws InterruptedException {
		logger.info("verify Post text for All Account");

		Assert.assertEquals(SFG.AccountPopUPonTimeFundingPosttext().get(0).getText().trim(),
				PropsUtil.getDataPropertyValue("SFGOntimeFundingPostText"));

	}

	@Test(description = "verify optional text for All Account", dependsOnMethods = { "step2LandingPage" }, groups = {
			"DesktopBrowsers" }, priority = 74)
	public void AccountPopUPonTimeFundingOptionaltext() throws InterruptedException {
		logger.info("verify optional text for All Account");

		Assert.assertEquals(SFG.AccountPopUPonTimeFundingOptionaltext().get(0).getText().trim(),
				PropsUtil.getDataPropertyValue("SFGOptionalText"));

	}

	@Test(description = "verify one time funding amount equals Goal Amount amount", dependsOnMethods = {
			"step2LandingPage" }, groups = { "DesktopBrowsers" }, priority = 75)
	public void AccountPopUPonTimeFundingequalsAmount() throws InterruptedException {
		logger.info("verify one time funding amount equals Goal Amount amount");

		SFG.AccountPopUPonTimeFundingTextBox().get(0).clear();
		SFG.AccountPopUPonTimeFundingTextBox().get(0).sendKeys(PropsUtil.getDataPropertyValue("SFGGoalAmount1"));
		SFG.AccountPopUPonTimeFundingTextBox().get(0).sendKeys(Keys.TAB);
		Assert.assertEquals(SFG.AccountPopUPonTimeFundingEqualsMessage().get(0).getText().trim(),
				PropsUtil.getDataPropertyValue("OntimeFundingAmountEqualAmountMessage"));

	}

	@Test(description = "AT-17594:Verify that total allocated amount of checked accounts should not be greater than goal amount.", dependsOnMethods = {
			"step2LandingPage" }, groups = { "DesktopBrowsers" }, priority = 76)
	public void AccountPopUPonTimeFundingErrorMessage() throws InterruptedException {
		logger.info("verify one time funding amount greater than Goal Amount amount");

		SFG.AccountPopUPonTimeFundingTextBox().get(0).clear();
		SFG.AccountPopUPonTimeFundingTextBox().get(0)
				.sendKeys(PropsUtil.getDataPropertyValue("SFGOntimeFundingAmount1"));
		SFG.AccountPopUPonTimeFundingTextBox().get(0).sendKeys(Keys.TAB);
		Assert.assertEquals(SFG.AccountPopUPonTimeFundingErrorMessage().get(0).getText().trim(),
				PropsUtil.getDataPropertyValue("OntimeFundingErrorMessage"));

	}

	@Test(description = "AT-17540,AT-17593::When earmarked value is greater than available balance, show error message: Amount should be less than or equal to available balance", dependsOnMethods = {
			"step2LandingPage" }, groups = { "DesktopBrowsers" }, priority = 77)
	public void verifyAccountOneFundingAmountGreaterThanAvailableBalance() throws InterruptedException {
		logger.info(
				"AT-17593:Verify that allocated amount at any account row should not be greater than available balance, irrespective of other accounts.");
		SFG.AccountPopUPonTimeFundingTextBox().get(0).clear();
		SFG.AccountPopUPonTimeFundingTextBox().get(0)
				.sendKeys(PropsUtil.getDataPropertyValue("SFGEditOnGoungFundingAmountGreaterThanAvailableBalance"));
		SFG.AccountPopUPonTimeFundingTextBox().get(0).sendKeys(Keys.TAB);

		Assert.assertEquals(SFG.AccountPopUPonTimeFundingErrorMessage().get(0).getText().trim(),
				PropsUtil.getDataPropertyValue("SFGEditOnGoungFundingAmountGreaterThanAvailableBalanceMessage"));
	}

	@Test(description = "verify account popup close icon", dependsOnMethods = { "step2LandingPage" }, groups = {
			"DesktopBrowsers" }, priority = 78)
	public void vrifyAccountPopUpCloseIcon() throws InterruptedException {
		logger.info("verify account popup close icon");
		Assert.assertTrue(SFG.AccountPopUpclose().isDisplayed());
	}

	@Test(description = "verify account save button", dependsOnMethods = { "step2LandingPage" }, groups = {
			"DesktopBrowsers" }, priority = 79)
	public void vrifyAccountPopUpSaveButton() throws InterruptedException {
		logger.info("verify account save button ");
		Assert.assertTrue(SFG.AccountPopUpSave().isDisplayed());

	}

	@Test(description = "Verify Link An account button", dependsOnMethods = { "step2LandingPage" }, groups = {
			"DesktopBrowsers" }, priority = 80)
	public void vrifyAccountPopUpCancel() throws InterruptedException {
		logger.info("Verify Link An account button");
		Assert.assertTrue(SFG.SFGAccountPopUpCancel().isDisplayed());

	}

	@Test(description = "verify close accouny Popup", dependsOnMethods = { "step2LandingPage" }, groups = {
			"DesktopBrowsers" }, priority = 81)
	public void vrifyCloseAccountpopup() throws InterruptedException {
		logger.info("verify close accouny Popup");
		SeleniumUtil.click(SFG.AccountPopUpclose());
		Assert.assertTrue(SFG.isElementPresent(SFG.AcconuPopUpHeader()));
	}

	@Test(description = "AT-17584:Verify that by default, the first applicable account should be in opened state.", dependsOnMethods = {
			"step2LandingPage" }, groups = { "DesktopBrowsers" }, priority = 82)
	public void vrifyAfterToggleOn() throws InterruptedException {
		logger.info("verify toggle button on and selected first account check box ");
		logger.info("Verify that by default, the first applicable account should be in opened state.");
		SeleniumUtil.click(SFG.AccountdropDownField());
		SeleniumUtil.waitForPageToLoad(1000);
		SeleniumUtil.click(SFG.MultiAccountToggel());
		SeleniumUtil.click(SFG.unSelecAccountCheckBox(0));
		System.out.println(SFG.unSelecAccountCheckBox(0).getAttribute("class"));
		Assert.assertTrue(SFG.unSelecAccountCheckBox(0).getAttribute("class")
				.contains(PropsUtil.getDataPropertyValue("SFGAccountCheckBoxSelected")));

	}

	@Test(description = "AT-17583,AT-17585,AT-17586,AT-17588,AT-17590:Verify that the account(s) selection in case of multiple accounts is done by checking the checkbox for the account.", dependsOnMethods = {
			"step2LandingPage" }, groups = { "DesktopBrowsers" }, priority = 83)
	public void vrifyAbletoSelectmutlipleAccount() throws InterruptedException {
		logger.info("verify userableTo select multipleaccount");
		logger.info("AT-17585:Verify that the user can expand any account from the select account drop down.");
		logger.info("AT-17586:Verify that account selection is done by expanding an account.");
		logger.info(
				"AT-17588:Verify that when user turns on the toggle for multiple account option, the expanded account will be enhanced to show a checkbox with checked state after which a user can expand any one of the unchecked account and provide the allocation values.");
		logger.info(
				"Verify that selecting any other account as destination account can be done using the checkbox as checked. This can be done in expanded or collapsed state.");
		SeleniumUtil.click(SFG.selectAccountCheckBox(1));
		Assert.assertTrue(SFG.unSelecAccountCheckBox(0).getAttribute("class")
				.contains(PropsUtil.getDataPropertyValue("SFGAccountCheckBoxSelected")));
		Assert.assertTrue(SFG.selectAccountCheckBox(1).getAttribute("class")
				.contains(PropsUtil.getDataPropertyValue("SFGAccountCheckBoxSelected")));

	}

	@Test(description = "AT-17556::Verify that the earmarked text box has the currency symbol inside the amount field.", dependsOnMethods = {
			"step2LandingPage" }, groups = { "DesktopBrowsers" }, priority = 84)
	public void vrifyCurrencySymbolInErmakedField() throws InterruptedException {
		Assert.assertEquals(SFG.SFGEmarkedCurrencySymbol().getText(),
				PropsUtil.getDataPropertyValue("SFGTargateAmountCurrency"));

	}

	@Test(description = "AT-17557,AT-17577::Verify that the ghost text inside the earmark amount box for single account on the step 2 screen 1 should be <free available balance> max by default.", dependsOnMethods = {
			"step2LandingPage" }, groups = { "DesktopBrowsers" }, priority = 85)
	public void vrifyGostTextInErmakedField() throws InterruptedException {
		logger.info(
				"AT-17577:Verify that earmark amount box should be blank by default in the select accounts pop up.");
		Assert.assertEquals(SFG.AccountPopUPonTimeFundingTextBox().get(0).getAttribute("placeholder"),
				PropsUtil.getDataPropertyValue("SFGOnetimeFundingdefaultvalue"));

	}

	@Test(description = "AT-17561AT-17562,AT-17578,AT-19619:Verify that if the total one time allocation amount given by the user is greater than target goal amount, the system should show an error message.", dependsOnMethods = {
			"step2LandingPage" }, groups = { "DesktopBrowsers" }, priority = 86)
	public void vrifyTotalOnetimeAmountGreaterThanAMountMessage() throws InterruptedException {
		logger.info("verify Enter Amount in textbox");
		logger.info(
				"AT-17562 Verify that if the total one time allocation amount given by the user is greater than target goal amount, the funding options should get disabled.");
		logger.info(
				"AT-17578:Verify that  Minimum earmaked amount can be zero and maximum can be the free available balance.");
		logger.info("AT-19619:Error message when we allocate an amount greater than the goal amount");
		logger.info(
				"AT-19620Show error on the accounts pop up , if the user enters an amount exceeding the goal amount");
		logger.info(
				"AT-19621If the user enters an amount exceeding the goal amount, disable the submit button until the user corrects the amount.");
		SFG.AccountPopUPonTimeFundingTextBox().get(0).clear();
		SFG.AccountPopUPonTimeFundingTextBox().get(0).sendKeys(PropsUtil.getDataPropertyValue("SFGOnetimeFunding6"));
		SFG.AccountPopUPonTimeFundingTextBox().get(1).clear();
		SFG.AccountPopUPonTimeFundingTextBox().get(1).sendKeys(PropsUtil.getDataPropertyValue("SFGOnetimeFunding7"));
		SFG.AccountPopUPonTimeFundingTextBox().get(1).sendKeys(Keys.TAB);
		SeleniumUtil.click(SFG.AccountPopUpSave());
		Assert.assertEquals(SFG.AccountsOneTimeFundingAmountMessage().getText(),
				PropsUtil.getDataPropertyValue("SFGAccountMorethanTargateAmountMessage"));
	}

	@Test(description = "AT-17587,AT-18132:Verify that the user can provide any allocation amount, which won't be reset in any case in the select account drop down.", dependsOnMethods = {
			"step2LandingPage" }, groups = { "DesktopBrowsers" }, priority = 87)
	public void vrifyOnetimeAmountMessage() throws InterruptedException {
		logger.info("verify Enter Amount in textbox");
		logger.info(
				"AT-18132:Verify that the earmarked amount that is entered by the user remains the same on tab out.");
		SFG.AccountPopUPonTimeFundingTextBox().get(0).clear();
		SFG.AccountPopUPonTimeFundingTextBox().get(0).sendKeys(PropsUtil.getDataPropertyValue("SFGOnetimeFunding"));
		SFG.AccountPopUPonTimeFundingTextBox().get(1).clear();
		SFG.AccountPopUPonTimeFundingTextBox().get(1).sendKeys(PropsUtil.getDataPropertyValue("SFGOnetimeFunding"));
		SFG.AccountPopUPonTimeFundingTextBox().get(1).sendKeys(Keys.TAB);

		Assert.assertEquals(SFG.AccountsOneTimeFundingAmountMessage().getText(),
				PropsUtil.getDataPropertyValue("SFGAccountOnetimeFundingMessage"));
	}

	@Test(description = "AT-17272,AT-17563,AT-17575,AT-18128,AT-18190,AT-19622:Verify that if a user selects an account and clicks the done button, and then clicks the select account drop down, the expanded account should be the account that the user had saved.", dependsOnMethods = {
			"step2LandingPage" }, groups = { "DesktopBrowsers" }, priority = 88)
	public void vrifySaveAccount() throws InterruptedException {
		logger.info("verify Account dropdown filed value");
		logger.info(
				"AT-17563Verify that if the total one time allocation amount given by the user is greater than target goal amount, the back button will remain enabled.");
		logger.info(
				"AT-17575 Verify that if the user has more than 1 savings account, randomly fill it with one of it.");
		logger.info("AT-18128:Verify that if the user clicks the done button, it should close the pop up.");
		logger.info("AT-18190:Verify that if the user edits an account, the done button should be clickable");
		logger.info(
				"AT-19622:The next button should always be enabled. If the user has errors on a field and clicks next, focus should go to the first field with errors.");
		SeleniumUtil.click(SFG.AccountPopUpSave());
		SeleniumUtil.waitForPageToLoad(3000);
		Assert.assertEquals(SFG.AccountdropDownField().getAttribute("value"),
				PropsUtil.getDataPropertyValue("SFG2accountSelected"));
	}

	@Test(description = "AT-17591,AT-17592:Verify that  If selecting an expanded account on the account row, then the row should not get collapsed.", dependsOnMethods = {
			"step2LandingPage" }, groups = { "DesktopBrowsers" }, priority = 89)
	public void vrifySaveAccountIsExpanded() throws InterruptedException {
		logger.info(
				"AT-17592:Verify that if the user selects an account that is collapsed, the account should expand.");
		SeleniumUtil.click(SFG.AccountdropDownField());
		SeleniumUtil.waitForPageToLoad(1000);
		Assert.assertTrue(SFG.unSelecAccountCheckBox(0).isDisplayed());
		Assert.assertTrue(SFG.unSelecAccountCheckBox(1).isDisplayed());
	}

	@Test(description = "AT-17581:Verify that the final amount given for selected account should be honored.", dependsOnMethods = {
			"step2LandingPage" }, groups = { "DesktopBrowsers" }, priority = 90)
	public void vrifyOnetimeFundingamountFromAccount() throws InterruptedException {
		logger.info("verify all selected account one time funding rule");
		SeleniumUtil.click(SFG.AccountPopUpclose());
		Assert.assertEquals(
				SFG.AccountAllocatedText().get(0).getText() + " " + SFG.AccountAllocatedAmount().get(0).getText() + " "
						+ SFG.AccountAllocatedAmountFrom().get(0).getText() + " "
						+ SFG.AccountAllocatedAmountFromAccount().get(0).getText(),
				PropsUtil.getDataPropertyValue("SFGFirstAccounuOneTimeFunding"));
		Assert.assertEquals(
				SFG.AccountAllocatedText().get(1).getText() + " " + SFG.AccountAllocatedAmount().get(1).getText() + " "
						+ SFG.AccountAllocatedAmountFrom().get(1).getText() + " "
						+ SFG.AccountAllocatedAmountFromAccount().get(1).getText(),
				PropsUtil.getDataPropertyValue("SFGSecondAccounuOneTimeFunding"));
	}

	@Test(description = "verify changed recurring on going funding amount with frequency", dependsOnMethods = {
			"step2LandingPage" }, groups = { "DesktopBrowsers" }, priority = 91)
	public void vrifyChangedRecurrenceAmount() throws InterruptedException {
		logger.info("verify changed recurring on going funding amount with frequency");
		Assert.assertEquals(SFG.recurOnGoingFundingValue().getText(),
				PropsUtil.getDataPropertyValue("SFGChangedRecurrenceFunding"));
	}

	@Test(description = "verify next button in step2", dependsOnMethods = { "step2LandingPage" }, groups = {
			"DesktopBrowsers" }, priority = 92)
	public void vrifyStep2NextButton() throws InterruptedException {
		logger.info("verify next button in step2");
		Assert.assertTrue(SFG.NextBtnStep1().isDisplayed());

	}

	@Test(description = "verify Back button in Step2", dependsOnMethods = { "step2LandingPage" }, groups = {
			"DesktopBrowsers" }, priority = 93)
	public void vrifyStep2BackButton() throws InterruptedException {
		logger.info("verify Back button in Step2");
		if (PageParser.isElementPresent("closeButton", "SFG", null)) {
			Assert.assertTrue(SFG.verifyClose().isDisplayed());
		}

		else {
			Assert.assertTrue(SFG.SFGBackButton().isDisplayed());
		}

	}

	@Test(description = "AT-17554,AT-17564::Verify that when the toggle for Multi Account is ON and user selects only one account, let the user proceed to next step.", dependsOnMethods = {
			"step2LandingPage" }, groups = { "DesktopBrowsers" }, priority = 94)
	public void vrifySaveFundingLabel() throws InterruptedException {
		logger.info("Save in lable in save funding page");
		logger.info(
				"AT-17564:Verify that if the total one time allocation amount given by the user is greater than target goal amount, the next button should get disabled until the user fixes the initial funding amount.");
		SeleniumUtil.click(SFG.NextBtnStep1());
		Assert.assertEquals(SFG.SaveFundingLabel().getText(), PropsUtil.getDataPropertyValue("SFGSaveFunding"));

	}

	@Test(description = "verify selected Account in FundingPage", dependsOnMethods = {
			"vrifySaveFundingLabel" }, groups = { "DesktopBrowsers" }, priority = 95)
	public void vrifyFundingPageAccountDropdown() throws InterruptedException {
		logger.info("verify selected Account in FundingPage");
		Assert.assertEquals(SFG.FundingPageAccount().getAttribute("value"),
				PropsUtil.getDataPropertyValue("SFGDefauldAccountValue"));
	}

	@Test(description = "verify list of account in funding page", dependsOnMethods = {
			"vrifySaveFundingLabel" }, groups = { "DesktopBrowsers" }, priority = 96)
	public void vrifyListOfaccount() throws InterruptedException {
		logger.info("verify list of account");
		SeleniumUtil.click(SFG.FundingPageAccountDropdownIcon());
		SeleniumUtil.waitForPageToLoad(1000);
		String expectedAccount[] = PropsUtil.getDataPropertyValue("SFGFundingDropDownAccount").split("/");
		for (int i = 0; i < SFG.SFGListOfAccount().size(); i++) {

			Assert.assertEquals(SFG.SFGListOfAccount().get(i).getText(), expectedAccount[i]);
		}

	}

	@Test(description = "verify selected Account in funding page", dependsOnMethods = {
			"vrifySaveFundingLabel" }, groups = { "DesktopBrowsers" }, priority = 97)
	public void vrifySelectedaccount() throws InterruptedException {
		logger.info("verify selected Account");
		String expAccount = SFG.SFGListOfAccount().get(1).getText();
		SeleniumUtil.click(SFG.SFGListOfAccount().get(1));
		Assert.assertEquals(SFG.FundingPageAccount().getAttribute("value"), expAccount);
	}

	@Test(description = "verify Amount field label page", dependsOnMethods = { "vrifySaveFundingLabel" }, groups = {
			"DesktopBrowsers" }, priority = 98)
	public void vrifyAmountFieldLabel() throws InterruptedException {
		logger.info("verify Amount field label");
		Assert.assertEquals(SFG.SFGAmountLabel().getText(), PropsUtil.getDataPropertyValue("SFGFundingAmountlabel"));
	}

	@Test(description = "AT-16749,AT-17611:Verify that Selecting the option to add an ongoing funding rule for FT  should take user to the FT flow with pre-filled values where user can choose to modify the details as applicable for FT in the respective steps", dependsOnMethods = {
			"vrifySaveFundingLabel" }, groups = { "DesktopBrowsers" }, priority = 99)
	public void vrifyAmountFieldValue() throws InterruptedException {
		logger.info("verify Amount field label");
		logger.info(
				"AT-17611:Verify that selecting the option to add an ongoing funding allocation rule should take user to the Ongoing Allocation flow with pre-filled values where user can choose to modify the details as applicable for ongoing allocation in the respective steps.");
		Assert.assertEquals(SFG.SFGAmountValue().getAttribute("value"),
				PropsUtil.getDataPropertyValue("SFGFundingAmountValue"));
	}

	@Test(description = "AT-SFG 52", dependsOnMethods = { "vrifySaveFundingLabel" }, groups = {
			"DesktopBrowsers" }, priority = 100)
	public void vrifyemptyFundingAmount() throws InterruptedException {
		SFG.SFGAmountValue().clear();
		SFG.SFGAmountValue().sendKeys(Keys.TAB);
		Assert.assertEquals(SFG.SFGFundingAmountErrorMessage().getText(),
				PropsUtil.getDataPropertyValue("WithouFrequencyAmount"));
	}

	@Test(description = "AT-17545::Verify that the user should be able to edit the system populated ongoing funding amount.", dependsOnMethods = {
			"vrifySaveFundingLabel" }, groups = { "DesktopBrowsers" }, priority = 101)
	public void vrifyUpdateAmountFieldValue() throws InterruptedException {
		logger.info("verify Amount field label");
		SFG.SFGAmountValue().clear();
		SFG.SFGAmountValue().sendKeys(PropsUtil.getDataPropertyValue("SFGFundingAmountValueUpdate"));
		SFG.SFGAmountValue().sendKeys(Keys.TAB);
		Assert.assertEquals(SFG.SFGAmountValue().getAttribute("value"),
				PropsUtil.getDataPropertyValue("SFGFundingAmountValueUpdate") + ".00");
	}

	@Test(description = "verify funding page Frequency label", dependsOnMethods = {
			"vrifySaveFundingLabel" }, groups = { "DesktopBrowsers" }, priority = 102)
	public void vrifyFrequencyLabel() throws InterruptedException {
		logger.info("verify funding page Frequency label ");
		Assert.assertEquals(SFG.SFGFrequencyLabel().getText(), PropsUtil.getDataPropertyValue("SFGFrequencyLabel"));
	}

	@Test(description = "verify funding page  frequency value", dependsOnMethods = {
			"vrifySaveFundingLabel" }, groups = { "DesktopBrowsers" }, priority = 103)
	public void vrifyFrequencyFieldValue() throws InterruptedException {
		logger.info("verify funding page  frequency value");
		Assert.assertEquals(SFG.SFGFrequencyValue().getText(), PropsUtil.getDataPropertyValue("SFGFrequencyValue"));
	}

	@Test(description = "verify funding page  Start date label", dependsOnMethods = {
			"vrifySaveFundingLabel" }, groups = { "DesktopBrowsers" }, priority = 104)
	public void vrifyStartDateLabel() throws InterruptedException {
		logger.info("verify funding page  Start date label");
		Assert.assertEquals(SFG.SFGStartDateLabel().getText(), PropsUtil.getDataPropertyValue("SFGStartDateLabel"));
	}

	@Test(description = "verify funding page  Start date value", dependsOnMethods = {
			"vrifySaveFundingLabel" }, groups = { "DesktopBrowsers" }, priority = 105)
	public void vrifyStartDateValue() throws InterruptedException {
		logger.info("verify funding page  Start date value");
		Assert.assertEquals(SFG.SFGStartDateValue().getText(), SFG.targateDateSelectMMM(1));
	}

	@Test(description = "verify funding page  end date label", dependsOnMethods = {
			"vrifySaveFundingLabel" }, groups = { "DesktopBrowsers" }, priority = 106)
	public void vrifyEndDatelabel() throws InterruptedException {
		logger.info("verify funding page  end date label");
		Assert.assertEquals(SFG.SFGEndDateLabel().getText(), PropsUtil.getDataPropertyValue("SFGEndDateLabel"));
	}

	@Test(description = "verify funding page end date value", dependsOnMethods = { "vrifySaveFundingLabel" }, groups = {
			"DesktopBrowsers" }, priority = 107)
	public void vrifyEndDateValue() throws InterruptedException {
		logger.info("verify funding page end date value");
		Assert.assertEquals(SFG.SFGEndDateValue().getText(), SFG.targateDateSelectMMM(78));
	}

	@Test(description = "verify funding page  cancel button", dependsOnMethods = { "vrifySaveFundingLabel" }, groups = {
			"DesktopBrowsers" }, priority = 108)
	public void vrifyFundingCancelButton() throws InterruptedException {
		logger.info("verify funding page  cancel button");
		if (PageParser.isElementPresent("closeButton", "SFG", null)) {
			Assert.assertTrue(SFG.verifyClose().isDisplayed());
		} else {
			Assert.assertTrue(SFG.SFGFundingCancelButton().isDisplayed());
		}
	}

	@Test(description = "verify funding page save button", dependsOnMethods = { "vrifySaveFundingLabel" }, groups = {
			"DesktopBrowsers" }, priority = 109)
	public void vrifyFundingSaveButton() throws InterruptedException {
		logger.info("verify funding page save button");
		if (PageParser.isElementPresent("SFGFundingSaveButtonForMobile", "SFG", null)) {
			Assert.assertTrue(SFG.verifySFGFundingSaveButton().isDisplayed());
		} else {
			Assert.assertTrue(SFG.SFGFundingSaveButton().isDisplayed());
		}
	}

	@Test(description = "if cancel the funding Amount Should not be updating in recurring amount", dependsOnMethods = {
			"vrifySaveFundingLabel" }, groups = { "DesktopBrowsers" }, priority = 110)
	public void vrifyFundingCancleButtonClick() throws InterruptedException {
		logger.info("if cancel the funding Amount Should not be updating in recurring amount");
		SeleniumUtil.click(SFG.SFGFundingCancelButton());
		SeleniumUtil.waitForPageToLoad(1000);
		Assert.assertEquals(SFG.recurOnGoingFundingValue().getText(),
				PropsUtil.getDataPropertyValue("SFGChangedRecurrenceFunding"));
	}

	@Test(description = "reduce Recuring funding and check the funding Left side label Details", dependsOnMethods = {
			"vrifySaveFundingLabel" }, groups = { "DesktopBrowsers" }, priority = 111)
	public void vrifyReducedRecuringFundingAllLabel() throws InterruptedException {
		logger.info("reduce Recuring funding and check the funding Left side label Details");
		SeleniumUtil.click(SFG.NextBtnStep1());
		SFG.SFGAmountValue().clear();
		SFG.SFGAmountValue().sendKeys(PropsUtil.getDataPropertyValue("SFGFundingAmountValueUpdate1"));
		SFG.SFGAmountValue().sendKeys(Keys.TAB);
		SeleniumUtil.waitForPageToLoad(1000);
		if (PageParser.isElementPresent("SFGFundingSaveButtonForMobile", "SFG", null)) {
			SeleniumUtil.click(SFG.verifySFGFundingSaveButton());
		} else {
			SeleniumUtil.click(SFG.SFGFundingSaveButton());
		}
		SeleniumUtil.waitForPageToLoad(1000);
		String expectedLabel[] = PropsUtil.getDataPropertyValue("SFGOngoingFundingLeftLabel").split(",");
		for (int i = 0; i < SFG.SFGOnGoingFundingLeftLabel().size(); i++) {
			Assert.assertEquals(SFG.SFGOnGoingFundingLeftLabel().get(i).getText(), expectedLabel[i]);
		}

	}

	@Test(description = "reduce Recuring funding and check the start date value", dependsOnMethods = {
			"vrifyReducedRecuringFundingAllLabel" }, groups = { "DesktopBrowsers" }, priority = 112)
	public void vrifyReducedRecuringFundingStartDateValue() throws InterruptedException {
		Assert.assertEquals(SFG.SFGOnGoingFundingrightvalue().get(0).getText(), SFG.targateDateSelectMMM(1));
	}

	@Test(description = "reduce Recuring funding and check the end date value", dependsOnMethods = {
			"vrifyReducedRecuringFundingAllLabel" }, groups = { "DesktopBrowsers" }, priority = 113)
	public void vrifyReducedRecuringFundingEndDateValue() throws InterruptedException {
		Assert.assertEquals(SFG.SFGOnGoingFundingrightvalue().get(1).getText(), SFG.targateDateSelectMMM(78));
	}

	@Test(description = "reduce Recuring funding and check the frequency value", dependsOnMethods = {
			"vrifyReducedRecuringFundingAllLabel" }, groups = { "DesktopBrowsers" }, priority = 114)
	public void vrifyReducedRecuringFundingFrequencyValue() throws InterruptedException {
		Assert.assertEquals(SFG.SFGOnGoingFundingrightvalue().get(2).getText(),
				PropsUtil.getDataPropertyValue("SFGFrequencyValue"));
	}

	@Test(description = "reduce Recuring funding and check the account name value", dependsOnMethods = {
			"vrifyReducedRecuringFundingAllLabel" }, groups = { "DesktopBrowsers" }, priority = 115)
	public void vrifyReducedRecuringFundingAccountValue() throws InterruptedException {
		Assert.assertEquals(SFG.SFGOnGoingFundingrightvalue().get(3).getText(),
				PropsUtil.getDataPropertyValue("SFGOnGoingFundingAccountName"));
	}

	@Test(description = "reduce Recuring funding and check the amount value", dependsOnMethods = {
			"vrifyReducedRecuringFundingAllLabel" }, groups = { "DesktopBrowsers" }, priority = 116)
	public void vrifyReducedRecuringFundingAmountValue() throws InterruptedException {
		sfgOngoingFundingValue = SFG.SFGOnGoingFundingrightvalue().get(4).getText();
		Assert.assertEquals(SFG.SFGOnGoingFundingrightvalue().get(4).getText(),
				"$" + PropsUtil.getDataPropertyValue("SFGFundingAmountValueUpdate1") + ".00");
	}

	@Test(description = "AT-17598:Verify that If there is any amount that has not been planned for yet, then that additional amount should be mentioned for the user to take it forward to create a funding rule for that.", dependsOnMethods = {
			"vrifyReducedRecuringFundingAllLabel" }, groups = { "DesktopBrowsers" }, priority = 117)
	public void vrifyAdditionFundingErrorIcon() throws InterruptedException {
		logger.info("addition funding error icon");
		Assert.assertTrue(SFG.SFGOnAdditionFundingErrorIcon().isDisplayed());
	}

	@Test(description = "AT-16748,AT-17599::Verify that if the initial ongoing funding rule is FT and user did not plan for the entire amount then the goal summary should have an option to add an ongoing funding rule for FT from the goal summary screen", dependsOnMethods = {
			"vrifyReducedRecuringFundingAllLabel" }, groups = { "DesktopBrowsers" }, priority = 118)
	public void vrifyAdditionFundingErrorMessage() throws InterruptedException {
		logger.info("addition funding error info Message");
		logger.info(
				"here  reduced 1 $ from Recurring Funding Amount. message amount  is depends on How much amount reduing in Recuring funding ");
		logger.info(
				"AT-17599:Verify that If there is any pending amount to be planned for, user should get an option to add a funding rule based on what the previous ongoing funding rule is.");
		Assert.assertEquals(SFG.SFGOnAdditionFundingErrorMessage().getText().trim(),
				PropsUtil.getDataPropertyValue("SFGAddFundingErrorMessage"));
	}

	@Test(description = "AT-16746::Verify that if the initial ongoing funding rule is allocation and user did not plan for the entire amount then the goal summary page should have an option to add an ongoing funding allocation rule from the goal summary screen.", dependsOnMethods = {
			"vrifyReducedRecuringFundingAllLabel" }, groups = { "DesktopBrowsers" }, priority = 119)
	public void vrifyAddtionalFundingIcon() throws InterruptedException {
		logger.info("addition funding  icon");
		Assert.assertTrue(SFG.SFGOnAdditionFundingIcon().isDisplayed());
	}

	@Test(description = "AT-16746::Verify that if the initial ongoing funding rule is allocation and user did not plan for the entire amount then the goal summary page should have an option to add an ongoing funding allocation rule from the goal summary screen.", dependsOnMethods = {
			"vrifyReducedRecuringFundingAllLabel" }, groups = { "DesktopBrowsers" }, priority = 120)
	public void vrifyAddtionalFundingIconLabel() throws InterruptedException {
		logger.info("addition funding Label");

		Assert.assertEquals(SFG.SFGOnAdditionFundingIconLabel().getText().trim(),
				PropsUtil.getDataPropertyValue("SFGAddFundingLabel"));
	}

	@Test(description = "AT-16747,AT-17600::Verify that selecting the option to add an ongoing funding should take user to the Ongoing Allocation flow with pre-filled values where user can choose to modify the details as applicable for ongoing allocation in the respective steps.", dependsOnMethods = {
			"vrifyReducedRecuringFundingAllLabel" }, groups = { "DesktopBrowsers" }, priority = 121)
	public void vrifyAddtionalFundingAmount() throws InterruptedException {
		logger.info("verify Addition Funding Amount");
		logger.info("here  reduced 1 $ from Recurring Funding Amount. so addition funding Amount Should be $1");
		logger.info(
				"AT-17600:Verify that if the initial ongoing funding rule is allocation and user did not plan for the entire amount then the goal summary should have an option to add an ongoing funding allocation rule from the goal summary screen.");
		SeleniumUtil.click(SFG.SFGOnAdditionFundingIcon());
		SeleniumUtil.waitForPageToLoad(500);
		AdditionalFundingValue = SFG.SFGAmountValue().getAttribute("value");
		Assert.assertEquals(SFG.SFGAmountValue().getAttribute("value"),
				PropsUtil.getDataPropertyValue("SFGAddFundingValue") + ".00");
	}

	@Test(description = "AT-16744,AT-16745::Verify that if there is any amount that has not been planned for yet, then that additional amount should be mentioned for the user to take it forward to create a funding rule for that.", groups = {
			"DesktopBrowsers" }, priority = 122)
	public void vrifyAddedAdditionalFundingAmount() throws InterruptedException {
		logger.info("verify added additional funding value");
		logger.info("added additional funding avlue should add to ongoing Funding amount ");
		logger.info(
				"AT-16745::Verify that if there is any pending amount to be planned for, user should get an option to add a funding rule be it ongoing allocation or funds transfer based on what the previous ongoing funding rule is.");
		if (PageParser.isElementPresent("SFGFundingSaveButtonForMobile", "SFG", null)) {
			SeleniumUtil.click(SFG.verifySFGFundingSaveButton());
		} else {
			SeleniumUtil.click(SFG.SFGFundingSaveButton());
		}
		SeleniumUtil.waitForPageToLoad(500);
		System.out.println(sfgOngoingFundingValue.trim().replace("$", ""));
		System.out.println(AdditionalFundingValue.trim());
		Double expectedOngoingAmount = Double.parseDouble(sfgOngoingFundingValue.trim().replace("$", ""))
				+ Double.parseDouble(AdditionalFundingValue);
		Assert.assertEquals(
				Double.parseDouble(SFG.SFGOnGoingFundingrightvalue().get(4).getText().replaceAll("\\$", "")),
				expectedOngoingAmount);

	}

	@Test(description = "after added addition funding amount  add funding option should not be available", dependsOnMethods = {
			"vrifyReducedRecuringFundingAllLabel" }, groups = { "DesktopBrowsers" }, priority = 123)
	public void vrifyAddedAdditionalFundingLabelAddedFundingAmount() throws InterruptedException {
		logger.info("after added addition funding amount  add funding option should not be available");
		Assert.assertTrue(SFG.isElementPresent(SFG.SFGOnAdditionFundingIconLabel()));

	}

	@Test(description = "delete funding rule option ", dependsOnMethods = {
			"vrifyReducedRecuringFundingAllLabel" }, groups = { "DesktopBrowsers" }, priority = 124)
	public void vrifyGoingfundingDeleteButton() throws InterruptedException {
		logger.info("delete funding rule option ");

		SeleniumUtil.click(SFG.SFGOnAdditionFundingRow().get(0));
		SeleniumUtil.waitForPageToLoad(1000);
		Assert.assertTrue(SFG.SFGOnOngoingFundingDeleteButton().isDisplayed());

	}

	@Test(description = "delete funding rule Pupup header", dependsOnMethods = {
			"vrifyReducedRecuringFundingAllLabel" }, groups = { "DesktopBrowsers" }, priority = 125)
	public void vrifyDeleteFundingRulePopup() throws InterruptedException {
		logger.info("delete funding rule Pupup header");
		SeleniumUtil.click(SFG.SFGOnOngoingFundingDeleteButton());
		SeleniumUtil.waitForPageToLoad(500);
		Assert.assertEquals(SFG.SFGFundingDeletePopUp().getText(),
				PropsUtil.getDataPropertyValue("SFGDeleteFundingPopUpHeaderText"));
	}

	@Test(description = "delete funding rule Pupup delete info message", dependsOnMethods = {
			"vrifyReducedRecuringFundingAllLabel" }, groups = { "DesktopBrowsers" }, priority = 126)
	public void vrifyDeleteFundingRuleDeletInfoMessage() throws InterruptedException {
		logger.info("delete funding rule Pupup delete info message");

		Assert.assertEquals(SFG.SFGFundingDeletwPopUpDeleteInfoMessage().getText(),
				PropsUtil.getDataPropertyValue("SFGDeleteFundingPopUpDeleteInfoMessage"));
	}

	@Test(description = "delete funding rule Pupup delete info message", dependsOnMethods = {
			"vrifyReducedRecuringFundingAllLabel" }, groups = { "DesktopBrowsers" }, priority = 127)
	public void vrifyDeleteFundingRuleCancelInfoMessage() throws InterruptedException {
		logger.info("delete funding rule Pupup delete info message");

		Assert.assertEquals(SFG.SFGFundingDeletwPopUpCancelInfoMessage().getText(),
				PropsUtil.getDataPropertyValue("SFGDeleteFundingPopUpCancelInfoMessage"));
	}

	@Test(description = "delete funding Close icon ", dependsOnMethods = {
			"vrifyReducedRecuringFundingAllLabel" }, groups = { "DesktopBrowsers" }, priority = 128)
	public void vrifyfundingDeleteCloseIcon() throws InterruptedException {
		logger.info("delete funding Close icon ");

		Assert.assertTrue(SFG.SFGFundingDeletePopupCloseIcon().isDisplayed());

	}

	@Test(description = "delete funding cancel button ", dependsOnMethods = {
			"vrifyReducedRecuringFundingAllLabel" }, groups = { "DesktopBrowsers" }, priority = 129)
	public void vrifyfundingDeleteCancelButton() throws InterruptedException {
		logger.info("delete funding cancel button ");

		Assert.assertTrue(SFG.SFGFundingDeletePopupCancelButton().isDisplayed());

	}

	@Test(description = "delete funding delete Button ", dependsOnMethods = {
			"vrifyReducedRecuringFundingAllLabel" }, groups = { "DesktopBrowsers" }, priority = 130)
	public void vrifyfundingDeletePopUpDeleteButton() throws InterruptedException {
		logger.info("delete funding delete Button ");

		Assert.assertTrue(SFG.SFGFundingDeletePopupDeleteButton().isDisplayed());

	}

	@Test(description = "delete funding Close icon", dependsOnMethods = {
			"vrifyReducedRecuringFundingAllLabel" }, groups = { "DesktopBrowsers" }, priority = 131)
	public void vrifyfundingDeleteCloseIconClick() throws InterruptedException {
		logger.info("delete funding Close icon ");
		SeleniumUtil.click(SFG.SFGFundingDeletePopupCloseIcon());

		Assert.assertTrue(SFG.isElementPresent(SFG.SFGFundingDeletwPopUpCancelInfoMessage()));

	}

	@Test(description = "delete funding cancel button click ", dependsOnMethods = {
			"vrifyReducedRecuringFundingAllLabel" }, groups = { "DesktopBrowsers" }, priority = 132)
	public void vrifyfundingDeleteCancelButtonClick() throws InterruptedException {
		logger.info("delete funding cancel button click ");
		SeleniumUtil.click(SFG.SFGOnOngoingFundingDeleteButton());
		SeleniumUtil.waitForPageToLoad(3000);
		SeleniumUtil.click(SFG.SFGFundingDeletePopupCancelButton());

		Assert.assertTrue(SFG.isElementPresent(SFG.SFGFundingDeletwPopUpCancelInfoMessage()));

	}

	@Test(description = "delete funding rule permanently", dependsOnMethods = {
			"vrifyReducedRecuringFundingAllLabel" }, groups = { "DesktopBrowsers" }, priority = 133)
	public void vrifyfundingDeletePopupDeleteButtonClick() throws InterruptedException {
		logger.info("delete funding rule permanently ");
		SeleniumUtil.click(SFG.SFGOnOngoingFundingDeleteButton());
		SeleniumUtil.waitForPageToLoad(10000);
		SeleniumUtil.click(SFG.SFGFundingDeletePopupDeleteButton());
		SeleniumUtil.waitForPageToLoad(3000);
		// Assert.assertTrue(SFG.isElementPresent(SFG.SFGOnAdditionFundingRow().get(0)));
		Assert.assertEquals(SFG.recurOnGoingFundingValue().getText(),
				PropsUtil.getDataPropertyValue("SFGChangedRecurrenceFunding"));

	}

	@Test(description = "verify start button dispplying in step3 ", dependsOnMethods = {
			"vrifyReducedRecuringFundingAllLabel" }, groups = { "DesktopBrowsers" }, priority = 134)
	public void vrifyStartGoal() throws InterruptedException {
		logger.info("StartGoal");
		SeleniumUtil.click(SFG.NextBtnStep1());
		SeleniumUtil.waitForPageToLoad(1000);
		if (PageParser.isElementPresent("SFGFundingSaveButtonForMobile", "SFG", null)) {
			SeleniumUtil.click(SFG.verifySFGFundingSaveButton());
		} else {
			SeleniumUtil.click(SFG.SFGFundingSaveButton());
		}
		SeleniumUtil.waitForPageToLoad(1000);
		Assert.assertEquals(SFG.SFGStartMyGoalButton().getText().trim(),
				PropsUtil.getDataPropertyValue("SFGStartMyGoalLable").trim());

	}

	@Test(description = "AT-16738::Verify that the 'Name of the Goal' attribute is shown on the summary view.", dependsOnMethods = {
			"vrifyStartGoal" }, groups = { "DesktopBrowsers" }, priority = 135)
	public void vrifyViewSummeryPageGoalname() throws InterruptedException {
		Assert.assertEquals(SFG.SFGViewSummeryGoalName().getText().trim(),
				PropsUtil.getDataPropertyValue("SFGGoalName1").trim());
	}

	@Test(description = "AT-16739::Verify that the 'Image of the Goal' attribute is shown on the summary view.", dependsOnMethods = {
			"vrifyStartGoal" }, groups = { "DesktopBrowsers" }, priority = 136)
	public void vrifyViewSummeryPageGoalCard() throws InterruptedException {
		Assert.assertTrue(SFG.SFGViewSummeryGoalCard().isDisplayed());
	}

	@Test(description = "AT-16742::Verify that the 'Target Date' attribute is shown on the summary view.", dependsOnMethods = {
			"vrifyStartGoal" }, groups = { "DesktopBrowsers" }, priority = 137)
	public void vrifyViewSummeryPageGoalTargateDate() throws InterruptedException {
		Assert.assertEquals(SFG.SFGViewSummeryGoalTargateDate().getText().trim(),
				PropsUtil.getDataPropertyValue("SFGInprogressGoalByText") + " " + SFG.targateDateSelectMMM(78));
	}

	@Test(description = "AT-16740::Verify that the 'Amount/% Saved so far given in wires' attribute is shown on the summary view.", dependsOnMethods = {
			"vrifyStartGoal" }, groups = { "DesktopBrowsers" }, priority = 138)
	public void vrifyViewSummeryPageGoalSavedAmount() throws InterruptedException {
		Assert.assertEquals(SFG.SFGViewSummeryGoalSavedAmount().getText().trim(),
				PropsUtil.getDataPropertyValue("SFGInprogressGoalOnetimeFundingAmount"));
	}

	@Test(description = "AT-16741::Verify that the 'Target Amount' attribute is shown on the summary view.", dependsOnMethods = {
			"vrifyStartGoal" }, groups = { "DesktopBrowsers" }, priority = 139)
	public void vrifyViewSummeryPageGoalTargateAmount() throws InterruptedException {
		Assert.assertEquals(SFG.SFGViewSummeryGoalTargateAmount().getText().trim(),
				PropsUtil.getDataPropertyValue("SFGInprogressGoalTargateAmount"));
	}

	@Test(description = "AT-16743::Verify that the funding rules are : 1.One time funding 2.Ongoing funding 3.Funds Transfer", dependsOnMethods = {
			"vrifyStartGoal" }, groups = { "DesktopBrowsers" }, priority = 140)
	public void vrifyViewSummeryPageOneTimeFundingLabel() throws InterruptedException {
		Assert.assertEquals(SFG.SFGViewSummeryGoalOneTimefundinglabel().getText().trim(),
				PropsUtil.getDataPropertyValue("SFGViewSummeryOnetimeFundingLabel"));
	}

	@Test(description = "AT-16743::Verify that the funding rules are : 1.One time funding 2.Ongoing funding 3.Funds Transfer", dependsOnMethods = {
			"vrifyStartGoal" }, groups = { "DesktopBrowsers" }, priority = 141)
	public void vrifyViewSummeryPageRecurringfundingLabel() throws InterruptedException {
		Assert.assertEquals(SFG.SFGViewSummeryGoalRecurringfundinglabel().getText().trim(),
				PropsUtil.getDataPropertyValue("SFGViewSummeryRecuringFundingLabel"));
	}

	@Test(description = "AT-16753::Verify that when the user clicks 'start my goal', the user should land on the 'Goals In Progress' screen with a success message and the summary card of the new goal.", dependsOnMethods = {
			"vrifyStartGoal" }, groups = { "DesktopBrowsers" }, priority = 142)
	public void vrifyCreateGoalSucessMessage() throws InterruptedException {
		logger.info("CreateGoalWithFrequency");
		SeleniumUtil.click(SFG.SFGStartMyGoalButton());
		SeleniumUtil.waitForPageToLoad(500);
		Assert.assertEquals(SFG.SFGGoalSaveMessage().getText(),
				PropsUtil.getDataPropertyValue("SFGIprogressGoalSuccessMessage"));
	}

	@Test(description = "verify inprogress tab is displying after inprogress goal", dependsOnMethods = {
			"vrifyCreateGoalSucessMessage" }, groups = { "DesktopBrowsers" }, priority = 143)
	public void vrifyCreateGoalWithFrequency() throws InterruptedException {
		logger.info("CreateGoalWithFrequency");
		Assert.assertEquals(SFG.SFGInprogressGoalTab().getText(),
				PropsUtil.getDataPropertyValue("SFGInprgressTabText"));

	}

	@Test(description = "AT-16738::Verify that the 'Name of the Goal' attribute is shown on the summary view.", dependsOnMethods = {
			"vrifyCreateGoalSucessMessage" }, groups = { "DesktopBrowsers" }, priority = 144)
	public void vrifyCreatedInprogressGoalName() throws InterruptedException {
		logger.info("verify created inprogress Goal Name");
		boolean expectedGoalName = false;
		for (int i = 0; i < SFG.SFGInprogressGoalNames().size(); i++) {
			if (SFG.SFGInprogressGoalNames().get(i).getText().trim()
					.equals(PropsUtil.getDataPropertyValue("SFGGoalName1").trim())) {
				expectedGoalName = true;
				break;
			}

		}
		Assert.assertTrue(expectedGoalName);
	}

	@Test(description = "AT-16739::Verify that the 'Image of the Goal' attribute is shown on the summary view.", dependsOnMethods = {
			"vrifyCreateGoalSucessMessage" }, groups = { "DesktopBrowsers" }, priority = 145)
	public void vrifyCreatedInprogressGoalCard() throws InterruptedException {
		logger.info("verify created inprogress Goal Name");
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

	@Test(description = "AT-16742::Verify that the 'Target Date' attribute is shown on the summary view.", dependsOnMethods = {
			"vrifyCreateGoalSucessMessage" }, groups = { "DesktopBrowsers" }, priority = 146)
	public void vrifyCreatedInprogressGoalEndDate() throws InterruptedException {
		logger.info("verify create inprogress goal Enda date ");
		String expectedEndDate = null;
		for (int i = 0; i < SFG.SFGInprogressGoalNames().size(); i++) {
			if (SFG.SFGInprogressGoalNames().get(i).getText().trim()
					.equals(PropsUtil.getDataPropertyValue("SFGGoalName1").trim())) {
				expectedEndDate = SFG.SFGInprogressGoalEndDate().get(i).getText();
				break;
			}

		}
		Assert.assertEquals(expectedEndDate,
				PropsUtil.getDataPropertyValue("SFGInprogressGoalByText") + " " + SFG.targateDateSelectMMMM(78));
	}

	@Test(description = "verify Created inprogress goal progress bar value ", dependsOnMethods = {
			"vrifyCreateGoalSucessMessage" }, groups = { "DesktopBrowsers" }, priority = 147)
	public void vrifyCreatedInprogressGoalPgrogressBar() throws InterruptedException {
		logger.info("verify Created inprogress goal progress bar value ");
		String expectedProgressBarValue = null;
		for (int i = 0; i < SFG.SFGInprogressGoalNames().size(); i++) {
			if (SFG.SFGInprogressGoalNames().get(i).getText().trim()
					.equals(PropsUtil.getDataPropertyValue("SFGGoalName1").trim())) {
				expectedProgressBarValue = SFG.SFGInprogressGoalProgressBar().get(i).getAttribute("style");
				break;
			}
		}
		Assert.assertEquals(expectedProgressBarValue,
				PropsUtil.getDataPropertyValue("SFGInprogressGoalProgressBarStyle"));
	}

	@Test(description = "AT-16740::Verify that the 'Amount/% Saved so far given in wires' attribute is shown on the summary view.", dependsOnMethods = {
			"vrifyCreateGoalSucessMessage" }, groups = { "DesktopBrowsers" }, priority = 148)
	public void vrifyCreatedInprogressGoalOneTimeFundingAmount() throws InterruptedException {
		logger.info("verify created inprogress goal one time funding amount");
		String expectedOnetimeFundingValue = null;
		for (int i = 0; i < SFG.SFGInprogressGoalNames().size(); i++) {
			if (SFG.SFGInprogressGoalNames().get(i).getText().trim()
					.equals(PropsUtil.getDataPropertyValue("SFGGoalName1").trim())) {
				expectedOnetimeFundingValue = SFG.SFGInprogressGoalOneTimeFundingValue().get(i).getText();
				break;
			}

		}

		Assert.assertEquals(expectedOnetimeFundingValue,
				PropsUtil.getDataPropertyValue("SFGInprogressGoalOnetimeFundingAmount"));
	}

	@Test(description = "verify created inprogress goal Of text", dependsOnMethods = {
			"vrifyCreateGoalSucessMessage" }, groups = { "DesktopBrowsers" }, priority = 149)
	public void vrifyCreatedInprogressGoalOfText() throws InterruptedException {
		logger.info("verify created inprogress goal Of text ");
		String expectedOftext = null;
		for (int i = 0; i < SFG.SFGInprogressGoalNames().size(); i++) {
			if (SFG.SFGInprogressGoalNames().get(i).getText().trim()
					.equals(PropsUtil.getDataPropertyValue("SFGGoalName1").trim())) {
				expectedOftext = SFG.SFGInprogressGoalOfText().get(i).getText();
				break;
			}

		}

		Assert.assertEquals(expectedOftext, PropsUtil.getDataPropertyValue("SFGInprogressGoalOfText"));
	}

	@Test(description = "AT-16741::Verify that the 'Target Amount' attribute is shown on the summary view.", dependsOnMethods = {
			"vrifyCreateGoalSucessMessage" }, groups = { "DesktopBrowsers" }, priority = 150)
	public void vrifyCreatedInprogressGoalTargateAmount() throws InterruptedException {
		logger.info("verify created inprogress goal Targate amount ");
		String expectedTargateAmount = null;
		for (int i = 0; i < SFG.SFGInprogressGoalNames().size(); i++) {
			if (SFG.SFGInprogressGoalNames().get(i).getText().trim()
					.equals(PropsUtil.getDataPropertyValue("SFGGoalName1").trim())) {
				expectedTargateAmount = SFG.SFGInprogressGoalTargateAmount().get(i).getText();
				break;
			}

		}

		Assert.assertEquals(expectedTargateAmount, PropsUtil.getDataPropertyValue("SFGInprogressGoalTargateAmount"));
	}

	@Test(description = "verify back to Step1 from step1", dependsOnMethods = {
			"vrifyCreateGoalSucessMessage" }, groups = { "DesktopBrowsers" }, priority = 151)
	public void vrifyBackFromStep2() throws InterruptedException {

		logger.info("verify back to Step1 from step1 ");

		SFG.step2LandingPage(PropsUtil.getDataPropertyValue("SFGGoalName2"),
				PropsUtil.getDataPropertyValue("SFGGoalAmount1"), 0,
				PropsUtil.getDataPropertyValue("SFGFrequencyAmount1"));
		SeleniumUtil.waitForPageToLoad(1000);
		SeleniumUtil.click(SFG.SFGBackButton());
		SeleniumUtil.waitForPageToLoad(1000);

		Assert.assertTrue(SFG.GoalNameInput().isDisplayed());
	}

	@Test(description = "verify back to Step2 from step3 ", dependsOnMethods = { "vrifyBackFromStep2" }, groups = {
			"DesktopBrowsers" }, priority = 152)
	public void vrifyBackFromStep3() throws InterruptedException {
		logger.info("verify back to Step2 from step3 ");
		SeleniumUtil.click(SFG.NextBtnStep1());
		SeleniumUtil.waitForPageToLoad(1000);
		SeleniumUtil.click(SFG.NextBtnStep1());
		SeleniumUtil.waitForPageToLoad(1000);
		if (PageParser.isElementPresent("SFGFundingSaveButtonForMobile", "SFG", null)) {
			SeleniumUtil.click(SFG.verifySFGFundingSaveButton());
		} else {
			SeleniumUtil.click(SFG.SFGFundingSaveButton());
		}
		SeleniumUtil.waitForPageToLoad(3000);
		SeleniumUtil.click(SFG.SFGBackButton());
		SeleniumUtil.waitForPageToLoad(1000);
		Assert.assertTrue(SFG.AccountdropDownField().isDisplayed());

	}

	@Test(description = "verify goal should not save when you click on quit without save", dependsOnMethods = {
			"vrifyBackFromStep3" }, groups = { "DesktopBrowsers" }, priority = 153)
	public void vrifyClickGoalTablInStep2() throws InterruptedException {
		PageParser.forceNavigate("SFG", d);
		SeleniumUtil.waitForPageToLoad(1000);
		SeleniumUtil.click(SFG.SFgDraftGoalQuiteWithOutsaving());
		SeleniumUtil.waitForPageToLoad(5000);
		boolean expectedGoalName = false;

		if (SFG.SFGDraftGoalNames().size() != 0) {
			for (int i = 0; i < SFG.SFGDraftGoalNames().size(); i++) {
				if (!SFG.SFGDraftGoalNames().get(i).getText().trim()
						.equals(PropsUtil.getDataPropertyValue("SFGGoalName2").trim())) {
					expectedGoalName = true;
					break;
				}

			}
		} else {
			expectedGoalName = true;
		}
		Assert.assertTrue(expectedGoalName);
	}

	@Test(description = "AT-17543::User should have an option to go back to the 'New Goals' screen in which case popup should come for an option to save this goal as a draft or discard changes.", dependsOnMethods = {
			"vrifyClickGoalTablInStep2" }, groups = { "DesktopBrowsers" }, priority = 154)
	public void vrifyclickBacktoNewgoalInStep2() throws InterruptedException {

		logger.info("verify daft goal popupHeader");

		SFG.step2LandingPage(PropsUtil.getDataPropertyValue("SFGGoalName3"),
				PropsUtil.getDataPropertyValue("SFGGoalAmount1"), 0,
				PropsUtil.getDataPropertyValue("SFGFrequencyAmount1"));
		SeleniumUtil.waitForPageToLoad(1000);
		if (PageParser.isElementPresent("closeButton", "SFG", null)) {
			SeleniumUtil.click(SFG.verifyClose());
		} else {
			SeleniumUtil.click(SFG.backToNewGoal());
		}
		SeleniumUtil.waitForPageToLoad(1000);
		Assert.assertEquals(SFG.SFgDraftGoalPopUPHeader().getText(),
				PropsUtil.getDataPropertyValue("SFGDraftGoalPupUpHeader"));
	}

	@Test(description = "verify draft goal close icon", dependsOnMethods = { "vrifyClickGoalTablInStep2" }, groups = {
			"DesktopBrowsers" }, priority = 155)
	public void vrifyDraftGoalPopUpCLoseIcon() throws InterruptedException {
		logger.info("verify draft goal close icon");
		Assert.assertTrue(SFG.SFgDraftGoalPopUPClose().isDisplayed());
	}

	@Test(description = "verify draft goal quit with save message", dependsOnMethods = {
			"vrifyClickGoalTablInStep2" }, groups = { "DesktopBrowsers" }, priority = 156)
	public void vrifyDraftGoalQuitSaveMessage() throws InterruptedException {
		logger.info("verify draft goal quit with save message");
		Assert.assertEquals(SFG.SFgDraftGoalQuiteMessage().getText(),
				PropsUtil.getDataPropertyValue("SFGDraftGoalWithSaveMessage"));
	}

	@Test(description = "verify draft goal quit with out save message", dependsOnMethods = {
			"vrifyClickGoalTablInStep2" }, groups = { "DesktopBrowsers" }, priority = 157)
	public void vrifyDraftGoalQuitMessage() throws InterruptedException {
		logger.info("verify draft goal quit with out save message");
		Assert.assertEquals(SFG.SFgDraftGoalCancelMessage().getText(),
				PropsUtil.getDataPropertyValue("SFGDraftGoalWithoutSaveMessage"));
	}

	@Test(description = "verify draft goal quit with save progress button label", dependsOnMethods = {
			"vrifyClickGoalTablInStep2" }, groups = { "DesktopBrowsers" }, priority = 158)
	public void vrifyDraftGoalQuitSaveButton() throws InterruptedException {
		logger.info("verify draft goal quit with save progress button label");
		Assert.assertEquals(SFG.SFgDraftGoalSaveMyProgress().getText(),
				PropsUtil.getDataPropertyValue("SFGDraftGoalWithSaveButtonLabel"));
	}

	@Test(description = "verify draft goal quit with out save button label", dependsOnMethods = {
			"vrifyClickGoalTablInStep2" }, groups = { "DesktopBrowsers" }, priority = 159)
	public void vrifyDraftGoalQuitWithoutSaveButton() throws InterruptedException {
		logger.info("verify draft goal quit with out save button label");
		Assert.assertEquals(SFG.SFgDraftGoalQuiteWithOutsaving().getText(),
				PropsUtil.getDataPropertyValue("SFGDraftGoalWithoutSaveButtonLabel"));
	}

	@Test(description = "verify draft goal close icon click ", dependsOnMethods = {
			"vrifyClickGoalTablInStep2" }, groups = { "DesktopBrowsers" }, priority = 160)
	public void vrifyDraftGoalPopUpCLoseIconClick() throws InterruptedException {
		logger.info("verify draft goal close icon click ");
		SeleniumUtil.click(SFG.SFgDraftGoalPopUPClose());
		SeleniumUtil.waitForPageToLoad(1000);
		Assert.assertTrue(SFG.isElementPresent(SFG.SFgDraftGoalPopUPHeader()));
	}

	@Test(description = "daft goal sucess message", dependsOnMethods = { "vrifyClickGoalTablInStep2" }, groups = {
			"DesktopBrowsers" }, priority = 161)
	public void vrifysavemyprogressAsDaft() throws InterruptedException {
		logger.info("daft goal sucess message");
		if (PageParser.isElementPresent("closeButton", "SFG", null)) {
			SeleniumUtil.click(SFG.verifyClose());
		} else {
			SeleniumUtil.click(SFG.backToNewGoal());
		}

		SeleniumUtil.waitForPageToLoad(1000);
		SeleniumUtil.click(SFG.SFgDraftGoalSaveMyProgress());
		SeleniumUtil.waitForPageToLoad(500);
		Assert.assertEquals(SFG.SFGGoalSaveMessage().getText(),
				PropsUtil.getDataPropertyValue("SFGDarftGoalSuccessMessage"));

	}

	@Test(description = "AT-16683:Verify that if the User goes back to the 'New Goals' screen, the goal should be saved as a draft.", dependsOnMethods = {
			"vrifysavemyprogressAsDaft" }, groups = { "DesktopBrowsers" }, priority = 162)
	public void vrifyCreatedDraftGoal() throws InterruptedException {
		logger.info("verify created Draft Goal Name");
		boolean expectedGoalName = false;
		for (int i = 0; i < SFG.SFGDraftGoalNames().size(); i++) {
			if (SFG.SFGDraftGoalNames().get(i).getText().trim()
					.equals(PropsUtil.getDataPropertyValue("SFGGoalName3").trim())) {
				expectedGoalName = true;
				break;
			}

		}
		Assert.assertTrue(expectedGoalName);
	}

	@Test(description = "verify created Draft Goal cards Message", dependsOnMethods = {
			"vrifysavemyprogressAsDaft" }, groups = { "DesktopBrowsers" }, priority = 163)
	public void vrifyCreatedDraftGoalcardMessage() throws InterruptedException {
		logger.info("verify created Draft Goal cards Message");
		String actualMessage = null;
		;
		for (int i = 0; i < SFG.SFGDraftGoalNames().size(); i++) {
			if (SFG.SFGDraftGoalNames().get(i).getText().trim()
					.equals(PropsUtil.getDataPropertyValue("SFGGoalName3").trim())) {
				actualMessage = SFG.SFGDraftGoalCardMessage().get(i).getText();
				break;
			}

		}
		Assert.assertEquals(actualMessage, PropsUtil.getDataPropertyValue("SFGDraftGoalCardMessage"));
	}

	@Test(description = "AT-17553:Verify that the user should have an option to go back to the 'New Goals' screen in which case, this goal should be saved as a draft and should be accessible from the 'Goals In Progress' screen so as to continue with it or delete it permanently.", dependsOnMethods = {
			"vrifysavemyprogressAsDaft" }, groups = { "DesktopBrowsers" }, priority = 164)
	public void vrifyDraftGoalQuiteWithoutSave() throws InterruptedException {
		logger.info("verify Draft goal should skip with out save");
		logger.info(
				"AT-17553:Verify that the user should have an option to go back to the 'New Goals' screen in which case, this goal should be saved as a draft and should be accessible from the 'Goals In Progress' screen so as to continue with it or delete it permanently.");

		SeleniumUtil.waitForPageToLoad(1000);
		SFG.step2LandingPage(PropsUtil.getDataPropertyValue("SFGGoalName4"),
				PropsUtil.getDataPropertyValue("SFGGoalAmount1"), 0,
				PropsUtil.getDataPropertyValue("SFGFrequencyAmount1"));
		SeleniumUtil.waitForPageToLoad(1000);
		if (PageParser.isElementPresent("closeButton", "SFG", null)) {
			SeleniumUtil.click(SFG.verifyClose());
		} else {
			SeleniumUtil.click(SFG.backToNewGoal());
		}
		SeleniumUtil.waitForPageToLoad(1000);
		SeleniumUtil.click(SFG.SFgDraftGoalQuiteWithOutsaving());
		SeleniumUtil.waitForPageToLoad(5000);
		logger.info("verify created Draft Goal Name");
		boolean expectedGoalName = false;
		for (int i = 0; i < SFG.SFGDraftGoalNames().size(); i++) {
			if (!SFG.SFGDraftGoalNames().get(i).getText().trim()
					.equals(PropsUtil.getDataPropertyValue("SFGGoalName4").trim())) {
				expectedGoalName = true;
				break;
			}

		}
		Assert.assertTrue(expectedGoalName);

	}

	@Test(description = "AT-16751,AT-17613::Verify that if at any point of time, if user terminates the goal in STEP 3, user should be able to access this goal from Goals In Progress screen where the UI lets user know he didn't complete it.", dependsOnMethods = {
			"vrifyDraftGoalQuiteWithoutSave" }, groups = { "DesktopBrowsers" }, priority = 165)
	public void vrifyQuiteGoalInStep3() throws InterruptedException {
		logger.info("verify Draft goal should skip with out save");
		logger.info(
				"AT-17613:Verify that if the user terminates the goal, it should be saved as a draft under the my goals tab with all values preserved.");
		SeleniumUtil.waitForPageToLoad(1000);
		SFG.step2LandingPage(PropsUtil.getDataPropertyValue("SFGGoalName5"),
				PropsUtil.getDataPropertyValue("SFGGoalAmount1"), 0,
				PropsUtil.getDataPropertyValue("SFGFrequencyAmount1"));
		SeleniumUtil.waitForPageToLoad();
		SeleniumUtil.click(SFG.NextBtnStep1());
		SeleniumUtil.waitForPageToLoad(1000);
		if (PageParser.isElementPresent("SFGFundingSaveButtonForMobile", "SFG", null)) {
			SeleniumUtil.click(SFG.verifySFGFundingSaveButton());
		} else {
			SeleniumUtil.click(SFG.SFGFundingSaveButton());
		}
		SeleniumUtil.waitForPageToLoad(1000);

		if (PageParser.isElementPresent("closeButton", "SFG", null)) {
			SeleniumUtil.click(SFG.verifyClose());
		} else {
			SeleniumUtil.click(SFG.backToNewGoal());
		}

		SeleniumUtil.waitForPageToLoad(5000);
		SeleniumUtil.click(SFG.SFgDraftGoalSaveMyProgress());
		SeleniumUtil.waitForPageToLoad(5000);
		boolean expectedGoalName = false;
		for (int i = 0; i < SFG.SFGDraftGoalNames().size(); i++) {
			if (SFG.SFGDraftGoalNames().get(i).getText().trim()
					.equals(PropsUtil.getDataPropertyValue("SFGGoalName5").trim())) {
				expectedGoalName = true;
				break;
			}

		}
		Assert.assertTrue(expectedGoalName);
	}

	@Test(description = "AT-16684,AT-16685,AT-16752,AT-16809::Verify that the saved goal draft is accessible from the 'Goals in Progress' screen.", dependsOnMethods = {
			"vrifyQuiteGoalInStep3" }, groups = { "DesktopBrowsers" }, priority = 166)
	public void vrifyDraftGoalLandingPage() throws InterruptedException {
		logger.info("verify draft goal landing page");
		logger.info("AT-16685-Verify that the Goal draft can be continued on");
		logger.info(
				"AT-16752Verify that when the user accesses his goals from the Goals in progress screen, he should be able to resume the goal from where he left with all values preserved.");
		logger.info(
				"AT-16809 User should have option to navigate back to Create Goal screen and already provided values should be auto-saved.");
		logger.info(
				"AT-17553:Verify that the user should have an option to go back to the 'New Goals' screen in which case, this goal should be saved as a draft and should be accessible from the 'Goals In Progress' screen so as to continue with it or delete it permanently.");
		for (int i = 0; i < SFG.SFGDraftGoalNames().size(); i++) {
			if (SFG.SFGDraftGoalNames().get(i).getText().trim()
					.equals(PropsUtil.getDataPropertyValue("SFGGoalName5").trim())) {

				SeleniumUtil.click(SFG.SFGDraftGoalNames().get(i));
				break;

			}
		}

		SeleniumUtil.waitForPageToLoad(3000);
		String goalname = SFG.GoalNameInput().getAttribute("value");
		String goalAmount = SFG.GoalAmountInput().getAttribute("value");
		String freqiencyAmount = SFG.GoalAmtFrqInput().getAttribute("value");
		SeleniumUtil.click(SFG.NextBtnStep1());
		SeleniumUtil.waitForPageToLoad(3000);
		Assert.assertEquals(goalname, PropsUtil.getDataPropertyValue("SFGGoalName5"));
		Assert.assertEquals(goalAmount, PropsUtil.getDataPropertyValue("SFGDraftGoalAmount"));
		Assert.assertEquals(freqiencyAmount, PropsUtil.getDataPropertyValue("SFGDraftGoalFrequencyAmount"));
		Assert.assertEquals(SFG.AccountdropDownField().getAttribute("value"),
				PropsUtil.getDataPropertyValue("SFG2accountSelected1"));

	}

	/*
	 * @Test(description =
	 * "AT-17533::Allocation amount should follow below validations which are as per amount standardization: Only  numeric and absolute values are allowed."
	 * , dependsOnMethods = { "Login" }, groups = { "DesktopBrowsers" }, priority =
	 * 58) public void verifyOnetimeFundingEmptyMessage() throws
	 * InterruptedException { SFG.OntimeFundingTextBox().clear();
	 * SFG.OntimeFundingTextBox().sendKeys(Keys.TAB);
	 * Assert.assertEquals(SFG.OntimeFundingErrorMessage().getText(),
	 * PropsUtil.getDataPropertyValue("OntimeFundingErrorMessage")); }
	 */

	@AfterClass
	public void checkAccessibility() {
		try {
			RunAccessibilityTest rat = new RunAccessibilityTest(this.getClass().getName());
			rat.testAccessibility(d);

		} catch (Exception e) {

		}
	}

}
