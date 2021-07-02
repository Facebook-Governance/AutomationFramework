/*******************************************************************************
 * Copyright (c) 2018 Yodlee Inc. All Rights Reserved.
 * 
 * This software is the confidential and proprietary information of
 * Yodlee, Inc. Use is subject to license terms.
 * 
 * @author
 ******************************************************************************/
package com.omni.pfm.pages.SFG;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;

/**
 * @author rshrivastav
 *
 */

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.omni.pfm.PageProcessor.PageParser;
import com.omni.pfm.utility.SeleniumUtil;

public class SFG_LandingPage_Loc extends SeleniumUtil {

	private static final Logger logger = LoggerFactory.getLogger(SFG_LandingPage_Loc.class);
	public static WebDriver d = null;
	public static final By sfgGoalInputBox = getByObject("SFG", null, "GoalNameInput");
	public static final By oneTimeFundingTextBox = getByObject("SFG", null, "OntimeFundingTextBox");

	public SFG_LandingPage_Loc(WebDriver driver) {
		d = driver;
	}

	public WebElement mobileBackToHomePageLink() {
		return d.findElement(By.xpath("//div[contains(@id,'sfgGoalSetupSection')]/div[@id='sfgDeleteGoalCnr']/a/span"));
	}

	public WebElement SfgHeader() {
		return getWebElement(d, "SfgHeader", "SFG", null);
	}

	public WebElement GoalCategory() {
		return getWebElement(d, "GoalCategory", "SFG", null);
	}

	public WebElement MyGoalsTab() {
		return getWebElement(d, "MyGoalsTab", "SFG", null);
	}

	public WebElement newGoalbutton() {
		return getWebElement(d, "newGoalbutton", "SFG", null);
	}

	public WebElement backToNewGoal() {
		return getWebElement(d, "backToNewGoal", "SFG", null);
	}

	public WebElement backToNewGoalIcon() {
		return getWebElement(d, "backToNewGoalIcon", "SFG", null);
	}

	public WebElement curentlyActiveStep() {
		return getWebElement(d, "curentlyActiveStep", "SFG", null);
	}

	public List<WebElement> all3GoalSteps() {
		return getWebElements(d, "all3GoalSteps", "SFG", null);
	}

	public WebElement goalNameFieldLable() {
		return getWebElement(d, "goalNameFieldLable", "SFG", null);
	}

	public WebElement GoalNameInput() {
		return getWebElement(d, "GoalNameInput", "SFG", null);
	}

	public WebElement GoalNameErrors() {
		return getWebElement(d, "GoalNameErrors", "SFG", null);
	}

	public WebElement GoalAmountInput() {
		return getWebElement(d, "GoalAmountInput", "SFG", null);
	}

	public WebElement GoalAmountErrors() {
		return getVisibileWebElement(d, "GoalAmountErrors", "SFG", null);
	}

	public void amountValidation(String goalAmount) {
		GoalAmountInput().clear();
		GoalAmountInput().sendKeys(goalAmount);
		GoalAmountInput().sendKeys(Keys.TAB);

	}

	public WebElement GoalAmtFrqBtnInput() {
		return getWebElement(d, "GoalAmtFrqBtnInput", "SFG", null);
	}

	public WebElement frequencyRadioButtonText() {
		return getWebElement(d, "frequencyRadioButtonText", "SFG", null);
	}

	public WebElement TagateRadioButtonText() {
		return getWebElement(d, "TagateRadioButtonText", "SFG", null);
	}

	public WebElement GoalAmtFrqInput() {
		return getWebElement(d, "GoalAmtFrqInput", "SFG", null);
	}

	public WebElement FrqAmountErrors() {
		return getWebElement(d, "FrqAmountErrors", "SFG", null);
	}

	public void frequencyAmountValidation(String frequencyAmount) {
		GoalAmtFrqInput().clear();
		GoalAmtFrqInput().sendKeys(frequencyAmount);
		GoalAmtFrqInput().sendKeys(Keys.TAB);
	}

	public WebElement helpsybol() {
		return getVisibileWebElement(d, "helpsybol", "SFG", null);
	}

	public WebElement helpMessage() {
		return getVisibileWebElement(d, "helpMessage", "SFG", null);
	}

	public WebElement GoalTargetBtnInput() {
		return getWebElement(d, "GoalTargetBtnInput", "SFG", null);
	}

	public WebElement GoalTargetInput() {
		return getVisibileWebElement(d, "GoalTargetInput", "SFG", null);
	}

	public WebElement TargetDateErrors() {
		return getVisibileWebElement(d, "TargetDateErrors", "SFG", null);
	}

	public void targateDateValidation(String targatDate) {
		GoalTargetInput().clear();
		GoalTargetInput().sendKeys(targatDate);
		GoalTargetInput().sendKeys(Keys.TAB);
	}

	public WebElement GoalStartInput() {
		return getWebElement(d, "GoalStartInput", "SFG", null);
	}

	public WebElement StartDateErrors() {
		return getVisibileWebElement(d, "StartDateErrors", "SFG", null);
	}

	public void startDateValidation(String startDate) {
		GoalStartInput().clear();
		GoalStartInput().sendKeys(startDate);
		GoalStartInput().sendKeys(Keys.TAB);
	}

	public WebElement FrqDropDownIcon() {
		return getVisibileWebElement(d, "FrqDropDownIcon", "SFG", null);
	}

	public List<WebElement> allFrequency() {

		return getWebElements(d, "allFrequency", "SFG", null);

	}

	public WebElement NextBtnStep1() {
		return getWebElement(d, "NextBtnStep1", "SFG", null);
	}

	public List<WebElement> SFGhighLevlcategories() {
		return getWebElements(d, "SFGhighLevlcategories", "SFG", null);
	}

	public List<WebElement> getSubCatText() {
		return getWebElements(d, "getSubCatText", "SFG", null);
	}

	public WebElement startGoalGetStartButton() {
		return getWebElement(d, "startGoalGetStartButton", "SFG", null);
	}

	public WebElement SFGCreateGoalButton() {
		return getWebElement(d, "SFGCreateGoalButton", "SFG", null);
	}

	public WebElement frequencyDropdown1() {
		return getWebElement(d, "frequencyDropdown1", "SFG", null);
	}

	public void step2LandingPage(String goalName, String goalAmount, int frequency, String FrequencyAmount) {
		try {
			if (startGoalGetStartButton().isDisplayed()) {
				click(startGoalGetStartButton());
			}
		} catch (Exception e) {
			click(SFGCreateGoalButton());
		}
		waitForPageToLoad(3000);
		click(SFGhighLevlcategories().get(0));
		waitForPageToLoad(3000);
		click(getSubCatText().get(0));
		waitForPageToLoad(3000);
		click(sfgGoalInputBox);
		d.findElement(sfgGoalInputBox).clear();
		d.findElement(sfgGoalInputBox).sendKeys(goalName);
		waitForPageToLoad(1000);
		click(GoalAmountInput());
		GoalAmountInput().clear();
		GoalAmountInput().sendKeys(goalAmount);
		waitForPageToLoad(1000);
		click(GoalAmtFrqBtnInput());
		GoalAmtFrqInput().sendKeys(FrequencyAmount);
		click(frequencyDropdown1());
		waitForPageToLoad(1000);
		click(allFrequency().get(frequency));
		waitForPageToLoad(1000);
		click(NextBtnStep1());
	}

	public void step2LandingPageWithTagateDate(String goalName, String goalAmount, String targetDate) {
		try {
			if (startGoalGetStartButton().isDisplayed()) {
				click(startGoalGetStartButton());
			}
		} catch (Exception e) {
			click(SFGCreateGoalButton());
		}
		waitForPageToLoad(3000);
		click(SFGhighLevlcategories().get(0));
		waitForPageToLoad(3000);
		click(getSubCatText().get(0));
		waitForPageToLoad(3000);
		click(sfgGoalInputBox);
		d.findElement(sfgGoalInputBox).clear();
		d.findElement(sfgGoalInputBox).sendKeys(goalName);
		waitForPageToLoad(1000);
		click(GoalAmountInput());
		GoalAmountInput().clear();
		GoalAmountInput().sendKeys(goalAmount);
		waitForPageToLoad(1000);
		click(GoalTargetBtnInput());
		targateDateValidation(targetDate);
		waitForPageToLoad(1000);
		click(NextBtnStep1());
	}

	public void CreateGoalStep3FrequencyGoal(String goalName, String goalAmount, int frequency, String FrequencyAmount,
			String onetimeFunding, String recuringAmountUpdate) {
		try {
			if (startGoalGetStartButton().isDisplayed()) {
				click(startGoalGetStartButton());
			}
		} catch (Exception e) {
			click(SFGCreateGoalButton());
		}
		waitForPageToLoad(3000);
		click(SFGhighLevlcategories().get(0));
		waitForPageToLoad(3000);
		click(getSubCatText().get(0));
		waitForPageToLoad(3000);
		click(sfgGoalInputBox);
		d.findElement(sfgGoalInputBox).clear();
		d.findElement(sfgGoalInputBox).sendKeys(goalName);
		waitForPageToLoad(1000);
		click(GoalAmountInput());
		GoalAmountInput().clear();
		GoalAmountInput().sendKeys(goalAmount);
		waitForPageToLoad(1000);
		click(GoalAmtFrqBtnInput());
		GoalAmtFrqInput().sendKeys(FrequencyAmount);
		click(frequencyDropdown1());
		waitForPageToLoad(1000);
		click(allFrequency().get(frequency));
		waitForPageToLoad(1000);
		click(NextBtnStep1());
		waitForPageToLoad(1000);
		click(AccountdropDownField());
		waitForPageToLoad(1000);

		click(MultiAccountToggel());
		waitForPageToLoad(1000);
		click(unSelecAccountCheckBox(0));
		waitForPageToLoad(1000);
		click(selectAccountCheckBox(1));
		AccountPopUPonTimeFundingTextBox().get(0).clear();
		AccountPopUPonTimeFundingTextBox().get(0).sendKeys(onetimeFunding);
		AccountPopUPonTimeFundingTextBox().get(1).clear();
		AccountPopUPonTimeFundingTextBox().get(1).sendKeys(onetimeFunding);
		AccountPopUPonTimeFundingTextBox().get(1).sendKeys(Keys.TAB);
		click(AccountPopUpSave());
		waitForPageToLoad(3000);
		click(NextBtnStep1());
		waitForPageToLoad(3000);
		SFGAmountValue().clear();
		SFGAmountValue().sendKeys(recuringAmountUpdate);
		SFGAmountValue().sendKeys(Keys.TAB);
		click(SFGFundingSaveButton());
		waitForPageToLoad(3000);
		click(NextBtnStep1());

	}

	public void CreateGoalWithTargateDate(String goalName, String goalAmount, String targateDate, String onetimeFunding,
			String recuringAmountUpdate) {
		try {
			if (startGoalGetStartButton().isDisplayed()) {
				click(startGoalGetStartButton());
			}
		} catch (Exception e) {
			click(SFGCreateGoalButton());
		}
		waitForPageToLoad(3000);
		click(SFGhighLevlcategories().get(0));
		waitForPageToLoad(3000);
		click(getSubCatText().get(0));
		waitForPageToLoad(3000);
		click(sfgGoalInputBox);
		d.findElement(sfgGoalInputBox).clear();
		d.findElement(sfgGoalInputBox).sendKeys(goalName);
		waitForPageToLoad(1000);
		click(GoalAmountInput());
		GoalAmountInput().clear();
		GoalAmountInput().sendKeys(goalAmount);
		waitForPageToLoad(1000);
		click(GoalTargetBtnInput());
		targateDateValidation(targateDate);
		waitForPageToLoad(1000);
		click(NextBtnStep1());
		waitForPageToLoad(4000);
		click(AccountdropDownField());
		waitForPageToLoad(20000);
		click(MultiAccountToggel());
		waitForPageToLoad(1000);
		click(unSelecAccountCheckBox(0));
		waitForPageToLoad(1000);
		click(selectAccountCheckBox(1));
		AccountPopUPonTimeFundingTextBox().get(0).clear();
		AccountPopUPonTimeFundingTextBox().get(0).sendKeys(onetimeFunding);
		AccountPopUPonTimeFundingTextBox().get(1).clear();
		AccountPopUPonTimeFundingTextBox().get(1).sendKeys(onetimeFunding);
		AccountPopUPonTimeFundingTextBox().get(1).sendKeys(Keys.TAB);
		click(AccountPopUpSave());
		waitForPageToLoad(3000);
		click(NextBtnStep1());
		SFGAmountValue().clear();
		SFGAmountValue().sendKeys(recuringAmountUpdate);
		SFGAmountValue().sendKeys(Keys.TAB);
		click(SFGFundingSaveButton());
		waitForPageToLoad(3000);
		click(NextBtnStep1());

	}

	public void CreateGoalWithFrequencyAmount(String goalName, String goalAmount, int frequency, String FrequencyAmount,
			String onetimeFunding, String recuringAmountUpdate) {
		try {
			if (startGoalGetStartButton().isDisplayed()) {
				click(startGoalGetStartButton());
			}
		} catch (Exception e) {
			click(SFGCreateGoalButton());
		}
		waitForPageToLoad(3000);
		click(SFGhighLevlcategories().get(0));
		waitForPageToLoad(3000);
		click(getSubCatText().get(0));
		waitForPageToLoad(3000);
		click(sfgGoalInputBox);
		d.findElement(sfgGoalInputBox).clear();
		d.findElement(sfgGoalInputBox).sendKeys(goalName);
		waitForPageToLoad(1000);
		click(GoalAmountInput());
		GoalAmountInput().clear();
		GoalAmountInput().sendKeys(goalAmount);
		waitForPageToLoad(1000);
		click(GoalAmtFrqBtnInput());
		GoalAmtFrqInput().sendKeys(FrequencyAmount);
		click(frequencyDropdown1());
		waitForPageToLoad(1000);
		click(allFrequency().get(frequency));
		waitForPageToLoad(1000);
		click(NextBtnStep1());
		waitForPageToLoad(3000);
		click(AccountdropDownField());
		waitForPageToLoad(1000);
		click(MultiAccountToggel());
		waitForPageToLoad(1000);
		click(unSelecAccountCheckBox(0));
		waitForPageToLoad(1000);
		click(selectAccountCheckBox(1));
		AccountPopUPonTimeFundingTextBox().get(0).clear();
		AccountPopUPonTimeFundingTextBox().get(0).sendKeys(onetimeFunding);
		AccountPopUPonTimeFundingTextBox().get(1).clear();
		AccountPopUPonTimeFundingTextBox().get(1).sendKeys(onetimeFunding);
		AccountPopUPonTimeFundingTextBox().get(1).sendKeys(Keys.TAB);
		click(AccountPopUpSave());
		waitForPageToLoad(3000);
		click(NextBtnStep1());
		waitForPageToLoad(3000);
		SFGAmountValue().clear();
		SFGAmountValue().sendKeys(recuringAmountUpdate);
		SFGAmountValue().sendKeys(Keys.TAB);
		waitForPageToLoad(3000);
		click(SFGFundingSaveButton());
		waitForPageToLoad(3000);
		click(NextBtnStep1());
		waitForPageToLoad(3000);

		click(SFGAmountFlowConfirmationPupUpRadioButton2());
		waitForPageToLoad(3000);

		click(SFGAmountFlowConfirmationPupUpSaveButton());
		waitForPageToLoad(3000);
		// click(NextBtnStep1());
		waitForPageToLoad(3000);

	}

	public String step2LandingPageWithDefaultName(String goalAmount, int frequency, String FrequencyAmount) {
		try {
			if (startGoalGetStartButton().isDisplayed()) {
				click(startGoalGetStartButton());
			}
		} catch (Exception e) {
			click(SFGCreateGoalButton());
		}
		waitForPageToLoad(3000);
		click(SFGhighLevlcategories().get(0));
		waitForPageToLoad(3000);
		click(getSubCatText().get(0));
		waitForPageToLoad(3000);
		String goalName = d.findElement(sfgGoalInputBox).getAttribute("value");
		click(GoalAmountInput());
		GoalAmountInput().clear();
		GoalAmountInput().sendKeys(goalAmount);
		waitForPageToLoad(1000);
		click(GoalAmtFrqBtnInput());
		GoalAmtFrqInput().sendKeys(FrequencyAmount);
		click(frequencyDropdown1());
		waitForPageToLoad(1000);
		click(allFrequency().get(frequency));
		waitForPageToLoad(1000);
		click(NextBtnStep1());
		return goalName;
	}

	public void CreateFundingRuleWithFrequency(String goalName, String goalAmount, int frequency,
			String FrequencyAmount, String onetimeFunding, String recuringAmountUpdate) {
		try {
			if (startGoalGetStartButton().isDisplayed()) {
				click(startGoalGetStartButton());
			}
		} catch (Exception e) {
			click(SFGCreateGoalButton());
		}
		waitForPageToLoad(3000);
		click(SFGhighLevlcategories().get(0));
		waitForPageToLoad(3000);
		click(getSubCatText().get(0));
		waitForPageToLoad(3000);
		click(sfgGoalInputBox);
		d.findElement(sfgGoalInputBox).clear();
		d.findElement(sfgGoalInputBox).sendKeys(goalName);
		waitForPageToLoad(1000);
		click(GoalAmountInput());
		GoalAmountInput().clear();
		GoalAmountInput().sendKeys(goalAmount);
		waitForPageToLoad(1000);
		click(GoalAmtFrqBtnInput());
		GoalAmtFrqInput().sendKeys(FrequencyAmount);
		click(frequencyDropdown1());
		waitForPageToLoad(1000);
		click(allFrequency().get(frequency));
		waitForPageToLoad(1000);
		click(NextBtnStep1());
		waitForPageToLoad(1000);
		click(AccountdropDownField());
		waitForPageToLoad(1000);

		click(MultiAccountToggel());
		waitForPageToLoad(1000);
		click(unSelecAccountCheckBox(0));
		waitForPageToLoad(1000);
		click(selectAccountCheckBox(1));
		AccountPopUPonTimeFundingTextBox().get(0).clear();
		AccountPopUPonTimeFundingTextBox().get(0).sendKeys(onetimeFunding);
		AccountPopUPonTimeFundingTextBox().get(1).clear();
		AccountPopUPonTimeFundingTextBox().get(1).sendKeys(onetimeFunding);
		AccountPopUPonTimeFundingTextBox().get(1).sendKeys(Keys.TAB);
		click(AccountPopUpSave());
		waitForPageToLoad(3000);
		click(NextBtnStep1());
		waitForPageToLoad(1000);
		SFGAmountValue().clear();
		SFGAmountValue().sendKeys(recuringAmountUpdate);
		SFGAmountValue().sendKeys(Keys.TAB);
		click(SFGFundingSaveButton());
		waitForPageToLoad(1000);
		click(SFGOnAdditionFundingIcon());
		waitForPageToLoad(500);
		click(FundingPageAccountDropdownIcon());
		waitForPageToLoad(1000);
		click(SFGListOfAccount().get(1));
		waitForPageToLoad(1000);
		click(SFGFundingSaveButton());
		waitForPageToLoad(1000);

	}

	public void step2LandingPageFTUE(String goalName, String goalAmount, int frequency, String FrequencyAmount) {
		click(startGoalGetStartButton());
		waitForPageToLoad(3000);
		click(SFGhighLevlcategories().get(0));
		waitForPageToLoad(3000);
		click(getSubCatText().get(0));
		waitForPageToLoad(3000);
		click(sfgGoalInputBox);
		d.findElement(sfgGoalInputBox).clear();
		d.findElement(sfgGoalInputBox).sendKeys(goalName);
		waitForPageToLoad(1000);
		click(GoalAmountInput());
		GoalAmountInput().clear();
		GoalAmountInput().sendKeys(goalAmount);
		waitForPageToLoad(1000);
		click(GoalAmtFrqBtnInput());
		GoalAmtFrqInput().sendKeys(FrequencyAmount);
		click(frequencyDropdown1());
		waitForPageToLoad(1000);
		click(allFrequency().get(frequency));
		waitForPageToLoad(1000);
		click(NextBtnStep1());
	}

	public void clikcgoalWithoutSave() {
		try {
			click(SFgDraftGoalQuiteWithOutsaving());
			waitForPageToLoad(5000);
		} catch (Exception e) {

		}
	}

	public void createPeogressGoalFromStep2(String onetimeFunding) {
		click(NextBtnStep1());
		click(AccountdropDownField());
		waitForPageToLoad(4000);

		click(MultiAccountToggel());
		waitForPageToLoad(1000);
		click(unSelecAccountCheckBox(0));
		waitForPageToLoad(1000);
		click(selectAccountCheckBox(1));
		AccountPopUPonTimeFundingTextBox().get(0).clear();
		AccountPopUPonTimeFundingTextBox().get(0).sendKeys(onetimeFunding);
		AccountPopUPonTimeFundingTextBox().get(1).clear();
		AccountPopUPonTimeFundingTextBox().get(1).sendKeys(onetimeFunding);
		AccountPopUPonTimeFundingTextBox().get(1).sendKeys(Keys.TAB);
		click(AccountPopUpSave());
		waitForPageToLoad(3000);
		click(NextBtnStep1());
		click(SFGFundingSaveButton());
		waitForPageToLoad(1000);
		click(NextBtnStep1());
	}

	public void CreateGoalWithallStepsFrequencyGoal(String goalName, String goalAmount, int frequency,
			String FrequencyAmount, String onetimeFunding, String recuringAmountUpdate) {
		By sfgGoalGetStarted = getByObject("SFG", null, "startGoalGetStartButton");
		if(isDisplayed(sfgGoalGetStarted, 5)) {
			click(sfgGoalGetStarted);
		} else {
			click(SFGCreateGoalButton());
		}
		waitForPageToLoad(3000);
		click(SFGhighLevlcategories().get(0));
		waitForPageToLoad(3000);
		click(getSubCatText().get(0));
		waitForPageToLoad(3000);
		click(sfgGoalInputBox);
		d.findElement(sfgGoalInputBox).clear();
		d.findElement(sfgGoalInputBox).sendKeys(goalName);
		waitForPageToLoad(1000);
		click(GoalAmountInput());
		GoalAmountInput().clear();
		GoalAmountInput().sendKeys(goalAmount);
		waitForPageToLoad(1000);
		click(GoalAmtFrqBtnInput());
		GoalAmtFrqInput().sendKeys(FrequencyAmount);
		click(frequencyDropdown1());
		waitForPageToLoad(1000);
		click(allFrequency().get(frequency));
		waitForPageToLoad(1000);
		click(NextBtnStep1());
		waitForPageToLoad(3000);
		click(AccountdropDownField());
		waitForPageToLoad(2000);

		click(MultiAccountToggel());
		waitForPageToLoad(1000);
		click(unSelecAccountCheckBox(0));
		waitForPageToLoad(1000);
		click(selectAccountCheckBox(1));
		AccountPopUPonTimeFundingTextBox().get(0).clear();
		AccountPopUPonTimeFundingTextBox().get(0).sendKeys(onetimeFunding);
		AccountPopUPonTimeFundingTextBox().get(1).clear();
		AccountPopUPonTimeFundingTextBox().get(1).sendKeys(onetimeFunding);
		AccountPopUPonTimeFundingTextBox().get(1).sendKeys(Keys.TAB);
		click(AccountPopUpSave());
		waitForPageToLoad(3000);
		click(NextBtnStep1());
		SFGAmountValue().clear();
		waitForPageToLoad(3000);
		SFGAmountValue().sendKeys(recuringAmountUpdate);
		SFGAmountValue().sendKeys(Keys.TAB);
		click(SFGFundingSaveButton());
		waitForPageToLoad(3000);
		click(NextBtnStep1());
		waitForPageToLoad(5000);
		click(SFGAmountFlowConfirmationPupUpRadioButton2());
		waitForPageToLoad(2000);
		click(SFGAmountFlowConfirmationPupUpSaveButton());
		waitForPageToLoad(2000);

	}

	public WebElement AccountfieldLable() {
		return getWebElement(d, "AccountfieldLable", "SFG", null);
	}

	public WebElement AccountdropDownField() {
		return getWebElement(d, "AccountdropDownField", "SFG", null);
	}

	public WebElement AccountdropDownIcon() {
		return getWebElement(d, "AccountdropDownIcon", "SFG", null);
	}

	public WebElement OneTimeFunddingLable() {
		return getWebElement(d, "OneTimeFunddingLable", "SFG", null);
	}

	public WebElement OneTimeAlocateText() {
		return getWebElement(d, "OneTimeAlocateText", "SFG", null);
	}

	public WebElement OntimeFundingPostText() {
		return getWebElement(d, "OntimeFundingPostText", "SFG", null);
	}

	public WebElement OntimeFundingOptionalText() {
		return getWebElement(d, "OntimeFundingOptionalText", "SFG", null);
	}

	public WebElement OntimeFundingTextBox() {
		return getWebElement(d, "OntimeFundingTextBox", "SFG", null);
	}

	public WebElement OntimeFundingAmountEqualAmountMessage() {
		return getWebElement(d, "OntimeFundingAmountEqualAmountMessage", "SFG", null);
	}

	public WebElement OntimeFundingErrorMessage() {
		return getWebElement(d, "OntimeFundingErrorMessage", "SFG", null);
	}

	public WebElement recurOnGoingFundingHeader() {
		return getWebElement(d, "recurOnGoingFundingHeader", "SFG", null);
	}

	public WebElement recurOnGoingFundingValue() {
		return getWebElement(d, "recurOnGoingFundingValue", "SFG", null);
	}

	public WebElement AcconuPopUpHeader() {
		return getWebElement(d, "AcconuPopUpHeader", "SFG", null);
	}

	public WebElement saveMoreThanAccountLabel() {
		return getWebElement(d, "saveMoreThanAccountLabel", "SFG", null);
	}

	public WebElement MultiAccountToggel() {
		return getWebElement(d, "MultiAccountToggel", "SFG", null);
	}

	public WebElement currencuInfoMessage() {
		return getWebElement(d, "currencuInfoMessage", "SFG", null);
	}

	public WebElement GoalHeaderInsteps() {
		return getWebElement(d, "GoalHeaderInsteps", "SFG", null);
	}

	public List<WebElement> SfgallAccountname() {

		return getWebElements(d, "SfgallAccountname", "SFG", null);

	}

	public WebElement selectAccount(Integer index) {
		return SfgallAccountname().get(2 * index);

	}

	public WebElement unSelectAccount(Integer index) {
		Integer a = 2 * index + 1;
		System.out.println(a);
		return SfgallAccountname().get(2 * index + 1);

	}

	public List<WebElement> SfgallAccountnameUnselected() {
		return getWebElements(d, "SfgallAccountnameUnselected", "SFG", null);
	}

	public List<WebElement> allAccountAvailablebalace() {
		return getWebElements(d, "allAccountAvailablebalace", "SFG", null);
	}

	public WebElement selectedAvailableBalance(Integer index) {
		return allAccountAvailablebalace().get(2 * index);

	}

	public WebElement unselectedAvailableBalance(Integer index) {
		Integer a = 2 * index + 1;
		System.out.println(a);
		return allAccountAvailablebalace().get(2 * index + 1);

	}

	public List<WebElement> AccountPopUPonTimeFundingpretext() {
		return getWebElements(d, "AccountPopUPonTimeFundingpretext", "SFG", null);
	}

	public List<WebElement> AccountPopUPonTimeFundingPosttext() {
		return getWebElements(d, "AccountPopUPonTimeFundingPosttext", "SFG", null);
	}

	public List<WebElement> AccountPopUPonTimeFundingOptionaltext() {
		return getWebElements(d, "AccountPopUPonTimeFundingOptionaltext", "SFG", null);
	}

	public List<WebElement> AccountPopUPonTimeFundingTextBox() {
		return getWebElements(d, "AccountPopUPonTimeFundingTextBox", "SFG", null);
	}

	public List<WebElement> AccountPopUPonTimeFundingEqualsMessage() {
		return getWebElements(d, "AccountPopUPonTimeFundingEqualsMessage", "SFG", null);
	}

	public List<WebElement> AccountPopUPonTimeFundingErrorMessage() {
		return getWebElements(d, "AccountPopUPonTimeFundingErrorMessage", "SFG", null);
	}

	public List<WebElement> SelectedAccountCheckBox() {
		return getWebElements(d, "SelectedAccountCheckBox", "SFG", null);
	}

	public WebElement selectAccountCheckBox(Integer index) {
		return SelectedAccountCheckBox().get(2 * index);

	}

	public WebElement unSelecAccountCheckBox(Integer index) {
		Integer a = 2 * index + 1;
		System.out.println(a);
		return SelectedAccountCheckBox().get(2 * index + 1);

	}

	public List<WebElement> unselectedAccountCheckBox() {
		return getWebElements(d, "unselectedAccountCheckBox", "SFG", null);
	}

	public WebElement AccountsOneTimeFundingAmountMessage() {
		return getWebElement(d, "AccountsOneTimeFundingAmountMessage", "SFG", null);
	}

	public WebElement AccountPopUpclose() {
		return getWebElement(d, "AccountPopUpclose", "SFG", null);
	}

	public WebElement AccountPopUpSave() {
		return getWebElement(d, "AccountPopUpSave", "SFG", null);
	}

	public WebElement SFGAccountPopUpCancel() {
		return getWebElement(d, "SFGAccountPopUpCancel", "SFG", null);
	}

	public WebElement SFGBackButton() {
		return getWebElement(d, "SFGBackButton", "SFG", null);
	}

	public boolean isElementPresent(List<WebElement> elements) {
		if (elements == null) {
			return true;
		} else {
			return false;
		}

	}

	public boolean isElementPresent(WebElement elements) {
		if (elements == null) {
			return true;
		} else {
			return false;
		}

	}

	public List<WebElement> AccountAllocatedText() {
		return getWebElements(d, "AccountAllocatedText", "SFG", null);
	}

	public List<WebElement> AccountAllocatedAmount() {
		return getWebElements(d, "AccountAllocatedAmount", "SFG", null);
	}

	public List<WebElement> AccountAllocatedAmountFrom() {
		return getWebElements(d, "AccountAllocatedAmountFrom", "SFG", null);
	}

	public List<WebElement> AccountAllocatedAmountFromAccount() {
		return getWebElements(d, "AccountAllocatedAmountFromAccount", "SFG", null);
	}

	public WebElement SaveFundingLabel() {
		return getWebElement(d, "SaveFundingLabel", "SFG", null);
	}

	public WebElement FundingPageAccount() {
		return getWebElement(d, "FundingPageAccount", "SFG", null);
	}

	public WebElement FundingPageAccountDropdownIcon() {
		return getWebElement(d, "FundingPageAccountDropdownIcon", "SFG", null);
	}

	public List<WebElement> SFGListOfAccount() {
		return getWebElements(d, "SFGListOfAccount", "SFG", null);
	}

	/*
	 * public WebElement selectAccount(Integer index) { return
	 * SFGListOfAccount().get(2*index);
	 * 
	 * }
	 * 
	 * public WebElement unSelectAccount(Integer index) { Integer a=2*index+1;
	 * System.out.println(a); return SFGListOfAccount().get(2*index+1);
	 * 
	 * }
	 */
	public WebElement twoOptionError1() {
		return getWebElement(d, "twoOptionError1", "SFG", null);
	}

	public WebElement SFGAmountLabel() {
		return getWebElement(d, "SFGAmountLabel", "SFG", null);
	}

	public WebElement SFGAmountValue() {
		return getWebElement(d, "SFGAmountValue", "SFG", null);
	}

	public WebElement SFGFrequencyLabel() {
		return getWebElement(d, "SFGFrequencyLabel", "SFG", null);
	}

	public WebElement SFGFrequencyValue() {
		return getWebElement(d, "SFGFrequencyValue", "SFG", null);
	}

	public WebElement SFGStartDateLabel() {
		return getWebElement(d, "SFGStartDateLabel", "SFG", null);
	}

	public WebElement SFGStartDateValue() {
		return getWebElement(d, "SFGStartDateValue", "SFG", null);
	}

	public WebElement SFGEndDateLabel() {
		return getWebElement(d, "SFGEndDateLabel", "SFG", null);
	}

	public WebElement SFGEndDateValue() {
		return getWebElement(d, "SFGEndDateValue", "SFG", null);
	}

	public WebElement SFGFundingCancelButton() {
		return getWebElement(d, "SFGFundingCancelButton", "SFG", null);
	}

	public WebElement SFGFundingSaveButton() {
		return getWebElement(d, "SFGFundingSaveButton", "SFG", null);
	}

	public List<WebElement> SFGOnGoingFundingLeftLabel() {
		return getWebElements(d, "SFGOnGoingFundingLeftLabel", "SFG", null);
	}

	public List<WebElement> SFGOnGoingFundingrightvalue() {
		return getWebElements(d, "SFGOnGoingFundingLightvalue", "SFG", null);
	}

	public WebElement SFGOnAdditionFundingErrorIcon() {
		return getWebElement(d, "SFGOnAdditionFundingErrorIcon", "SFG", null);
	}

	public WebElement SFGOnAdditionFundingErrorMessage() {
		return getWebElement(d, "SFGOnAdditionFundingErrorMessage", "SFG", null);
	}

	public WebElement SFGOnAdditionFundingIcon() {
		return getWebElement(d, "SFGOnAdditionFundingIcon", "SFG", null);
	}

	public WebElement SFGOnAdditionFundingIconLabel() {
		return getWebElement(d, "SFGOnAdditionFundingIconLabel", "SFG", null);
	}

	public List<WebElement> SFGOnAdditionFundingRow() {
		return getWebElements(d, "SFGOnAdditionFundingRow", "SFG", null);
	}

	public WebElement SFGOnOngoingFundingDeleteButton() {
		return getWebElement(d, "SFGOnOngoingFundingDeleteButton", "SFG", null);
	}

	public WebElement SFGFundingDeletePopUp() {
		return getWebElement(d, "SFGFundingDeletePopUp", "SFG", null);
	}

	public WebElement SFGFundingDeletwPopUpDeleteInfoMessage() {
		return getWebElement(d, "SFGFundingDeletwPopUpDeleteInfoMessage", "SFG", null);
	}

	public WebElement SFGFundingDeletwPopUpCancelInfoMessage() {
		return getWebElement(d, "SFGFundingDeletwPopUpCancelInfoMessage", "SFG", null);
	}

	public WebElement SFGFundingDeletePopupCloseIcon() {
		return getWebElement(d, "SFGFundingDeletePopupCloseIcon", "SFG", null);
	}

	public WebElement SFGFundingDeletePopupCancelButton() {
		return getWebElement(d, "SFGFundingDeletePopupCancelButton", "SFG", null);
	}

	public WebElement SFGFundingDeletePopupDeleteButton() {
		return getWebElement(d, "SFGFundingDeletePopupDeleteButton", "SFG", null);
	}

	public WebElement SFGStartMyGoalButton() {
		return getWebElement(d, "SFGStartMyGoalButton", "SFG", null);
	}

	public WebElement SFGViewSummeryGoalName() {
		return getWebElement(d, "SFGViewSummeryGoalName", "SFG", null);
	}

	public WebElement SFGViewSummeryGoalCard() {
		return getWebElement(d, "SFGViewSummeryGoalCard", "SFG", null);
	}

	public WebElement SFGViewSummeryGoalTargateDate() {
		return getWebElement(d, "SFGViewSummeryGoalTargateDate", "SFG", null);
	}

	public WebElement SFGViewSummeryGoalSavedAmount() {
		return getWebElement(d, "SFGViewSummeryGoalSavedAmount", "SFG", null);
	}

	public WebElement SFGViewSummeryGoalTargateAmount() {
		return getWebElement(d, "SFGViewSummeryGoalTargateAmount", "SFG", null);
	}

	public WebElement SFGViewSummeryGoalOneTimefundinglabel() {
		return getWebElement(d, "SFGViewSummeryGoalOneTimefundinglabel", "SFG", null);
	}

	public WebElement SFGViewSummeryGoalRecurringfundinglabel() {
		return getWebElement(d, "SFGViewSummeryGoalRecurringfundinglabel", "SFG", null);
	}

	public WebElement SFGInprogressGoalTab() {
		return getWebElement(d, "SFGInprogressGoalTab", "SFG", null);
	}

	public List<WebElement> SFGInprogressGoalNames() {
		return getWebElements(d, "SFGInprogressGoalNames", "SFG", null);
	}

	public List<WebElement> SFGInprogressGoalCards() {
		return getWebElements(d, "SFGInprogressGoalCards", "SFG", null);
	}

	public List<WebElement> SFGInprogressGoalEndDate() {
		return getWebElements(d, "SFGInprogressGoalEndDate", "SFG", null);
	}

	public List<WebElement> SFGInprogressGoalProgressBar() {
		return getWebElements(d, "SFGInprogressGoalProgressBar", "SFG", null);
	}

	public List<WebElement> SFGInprogressGoalOneTimeFundingValue() {
		return getWebElements(d, "SFGInprogressGoalOneTimeFundingValue", "SFG", null);
	}

	public List<WebElement> SFGInprogressGoalOfText() {
		return getWebElements(d, "SFGInprogressGoalOfText", "SFG", null);
	}

	public List<WebElement> SFGInprogressGoalTargateAmount() {
		return getWebElements(d, "SFGInprogressGoalTargateAmount", "SFG", null);
	}

	public WebElement SFgDraftGoalPopUPHeader() {
		return getWebElement(d, "SFgDraftGoalPopUPHeader", "SFG", null);
	}

	public WebElement SFgDraftGoalPopUPClose() {
		return getWebElement(d, "SFgDraftGoalPopUPClose", "SFG", null);
	}

	public WebElement SFgDraftGoalQuiteMessage() {
		return getWebElement(d, "SFgDraftGoalQuiteMessage", "SFG", null);
	}

	public WebElement SFgDraftGoalCancelMessage() {
		return getWebElement(d, "SFgDraftGoalCancelMessage", "SFG", null);
	}

	public WebElement SFgDraftGoalSaveMyProgress() {
		return getWebElement(d, "SFgDraftGoalSaveMyProgress", "SFG", null);
	}

	public WebElement SFgDraftGoalQuiteWithOutsaving() {
		return getWebElement(d, "SFgDraftGoalQuiteWithOutsaving", "SFG", null);
	}

	public WebElement SFGGoalSaveMessage() {
		return getWebElement(d, "SFGGoalSaveMessage", "SFG", null);
	}

	public List<WebElement> SFGDraftGoalNames() {
		return getWebElements(d, "SFGDraftGoalNames", "SFG", null);
	}

	public List<WebElement> SFGDraftGoalCardMessage() {
		return getWebElements(d, "SFGDraftGoalCardMessage", "SFG", null);
	}

	public List<WebElement> SFGDraftGoalDeletIcon() {
		return getWebElements(d, "SFGDraftGoalDeletIcon", "SFG", null);
	}

	public WebElement SFGDraftGoalDeletPopUpHeader() {
		return getWebElement(d, "SFGDraftGoalDeletPopUpHeader", "SFG", null);
	}

	public WebElement SFGDraftGoalDeletPopupClose() {
		return getWebElement(d, "SFGDraftGoalDeletPopupClose", "SFG", null);
	}

	public WebElement SFGDraftGoalDeletPopupDeleteInfoMessage() {
		return getWebElement(d, "SFGDraftGoalDeletPopupDeleteInfoMessage", "SFG", null);
	}

	public WebElement SFGDraftGoalDeletPopupCancelInfoMessage() {
		return getWebElement(d, "SFGDraftGoalDeletPopupCancelInfoMessage", "SFG", null);
	}

	public WebElement SFGDraftGoalDeletPopupDeletButton() {
		return getWebElement(d, "SFGDraftGoalDeletPopupDeletButton", "SFG", null);
	}

	public WebElement SFGDraftGoalDeletPopupCancelButton() {
		return getWebElement(d, "SFGDraftGoalDeletPopupCancelButton", "SFG", null);
	}

	public WebElement SFGAmountFlowConfirmationPupUpHeader() {
		return getWebElement(d, "SFGAmountFlowConfirmationPupUpHeader", "SFG", null);
	}

	public WebElement SFGAmountFlowConfirmationPupUpCloseIcon() {
		return getWebElement(d, "SFGAmountFlowConfirmationPupUpCloseIcon", "SFG", null);
	}

	public WebElement SFGAmountFlowConfirmationPupUpInfo1() {
		return getWebElement(d, "SFGAmountFlowConfirmationPupUpInfo1", "SFG", null);
	}

	public WebElement SFGAmountFlowConfirmationPupUpInfo2() {
		return getWebElement(d, "SFGAmountFlowConfirmationPupUpInfo2", "SFG", null);
	}

	public WebElement SFGAmountFlowConfirmationPupUpRadioButton1() {
		return getWebElement(d, "SFGAmountFlowConfirmationPupUpRadioButton1", "SFG", null);
	}

	public WebElement SFGAmountFlowConfirmationPupUpRadioButton2() {
		return getWebElement(d, "SFGAmountFlowConfirmationPupUpRadioButton2", "SFG", null);
	}

	public WebElement SFGAmountFlowConfirmationPupUpCancelButton() {
		return getWebElement(d, "SFGAmountFlowConfirmationPupUpCancelButton", "SFG", null);
	}

	public WebElement SFGAmountFlowConfirmationPupUpSaveButton() {
		return getWebElement(d, "SFGAmountFlowConfirmationPupUpSaveButton", "SFG", null);
	}

	public WebElement SFGTargateSaveLabel() {
		return getWebElement(d, "SFGTargateSaveLabel", "SFG", null);
	}

	public WebElement SFGTargateSaveValue() {
		return getWebElement(d, "SFGTargateSaveValue", "SFG", null);
	}

	public WebElement SFGTargateFrequencytext() {
		return getWebElement(d, "SFGTargateFrequencytext", "SFG", null);
	}

	public WebElement SFGTargateFrequencyIcon() {
		return getWebElement(d, "SFGTargateFrequencyIcon", "SFG", null);
	}

	public List<WebElement> SFGTargateFrequencytextList() {
		return getWebElements(d, "SFGTargateFrequencytextList", "SFG", null);
	}

	public WebElement SFGEditCurrentStauslabel() {
		return getWebElement(d, "SFGEditCurrentStauslabel", "SFG", null);
	}

	public List<WebElement> SFGEditCurrentStausDetailslabel() {
		return getWebElements(d, "SFGEditCurrentStausDetailslabel", "SFG", null);
	}

	public List<WebElement> SFGEditCurrentStausDetailsValue() {
		return getWebElements(d, "SFGEditCurrentStausDetailsValue", "SFG", null);
	}

	public WebElement SFGEditOngoingFundinglabel() {
		return getWebElement(d, "SFGEditOngoingFundinglabel", "SFG", null);
	}

	public List<WebElement> SFGEditOngoingFundingDetailslabel() {
		return getWebElements(d, "SFGEditOngoingFundingDetailslabel", "SFG", null);
	}

	public List<WebElement> SFGEditOngoingFundingDetailsValue() {
		return getWebElements(d, "SFGEditOngoingFundingDetailsValue", "SFG", null);
	}

	public WebElement SFGCurrencySymbolInTrargateAmount() {
		return getWebElement(d, "SFGCurrencySymbolInTrargateAmount", "SFG", null);
	}

	public WebElement SFGCurrencySymbolInFrequencyAmount() {
		return getWebElement(d, "SFGCurrencySymbolInFrequencyAmount", "SFG", null);
	}

	public WebElement SFGFundingAmountErrorMessage() {
		return getWebElement(d, "SFGFundingAmountErrorMessage", "SFG", null);
	}

	public WebElement SFGEmarkedCurrencySymbol() {
		return getWebElement(d, "SFGEmarkedCurrencySymbol", "SFG", null);
	}

	public WebElement SFGGoalCardOfText() {
		return getWebElement(d, "SFGGoalCardOfText", "SFG", null);
	}

	public List<WebElement> SFGAccountViewMoreOption() {
		return getWebElements(d, "SFGAccountViewMoreOption", "SFG", null);
	}

	public WebElement SFGAccountViewMorePopupHeader() {
		return getWebElement(d, "SFGAccountViewMorePopupHeader", "SFG", null);
	}

	public WebElement SFGAccountViewMorePopupFreavailableBalance() {
		return getWebElement(d, "SFGAccountViewMorePopupFreavailableBalance", "SFG", null);
	}

	public WebElement SFGAccountViewMorePopupPrgressBar() {
		return getWebElement(d, "SFGAccountViewMorePopupPrgressBar", "SFG", null);
	}

	public WebElement SFGAccountViewMorePopupSavedValue() {
		return getWebElement(d, "SFGAccountViewMorePopupSavedValue", "SFG", null);
	}

	public WebElement SFGAccountViewMorePopupOftext() {
		return getWebElement(d, "SFGAccountViewMorePopupOftext", "SFG", null);
	}

	public WebElement SFGAccountViewMorePopupAvailableBalance() {
		return getWebElement(d, "SFGAccountViewMorePopupAvailableBalance", "SFG", null);
	}

	public WebElement SFGAccountViewMorePopupFollwingtext() {
		return getWebElement(d, "SFGAccountViewMorePopupFollwingtext", "SFG", null);
	}

	public List<WebElement> SFGAccountViewMorePopupListOfGoal() {
		return getWebElements(d, "SFGAccountViewMorePopupListOfGoal", "SFG", null);
	}

	public List<WebElement> SFGAccountViewMorePopupListOfGoalAmount() {
		return getWebElements(d, "SFGAccountViewMorePopupListOfGoalAmount", "SFG", null);
	}

	public List<WebElement> SFGAccountViewMorePopupListOfGoalDate() {
		return getWebElements(d, "SFGAccountViewMorePopupListOfGoalDate", "SFG", null);
	}

	public List<WebElement> SFGAccountViewMorePopupListOfGoalSavedValueInProgressbar() {
		return getWebElements(d, "SFGAccountViewMorePopupListOfGoalSavedValueInProgressbar", "SFG", null);
	}

	public List<WebElement> SFGAccountViewMorePopupListOfGoalSavedValueInProgressbarcss() {
		return getWebElements(d, "SFGAccountViewMorePopupListOfGoalSavedValueInProgressbarcss", "SFG", null);
	}

	public List<WebElement> SFGAccountViewMorePopupListYouHavedSavedText() {
		return getWebElements(d, "SFGAccountViewMorePopupListYouHavedSavedText", "SFG", null);
	}

	public List<WebElement> SFGAccountViewMorePopupListOfMountField() {
		return getWebElements(d, "SFGAccountViewMorePopupListOfMountField", "SFG", null);
	}

	public List<WebElement> SFGAccountViewMorePopupListOfGoalOptionalText1() {
		return getWebElements(d, "SFGAccountViewMorePopupListOfGoalOptionalText1", "SFG", null);
	}

	public List<WebElement> SFGAccountViewMorePopupListOfGoalOptionalText() {
		return getWebElements(d, "SFGAccountViewMorePopupListOfGoalOptionalText", "SFG", null);
	}

	public WebElement SFGAccountViewMorePopupCloseIcon() {
		return getWebElement(d, "SFGAccountViewMorePopupCloseIcon", "SFG", null);
	}

	public WebElement SFGAccountViewMorePopupCancelButton() {
		return getWebElement(d, "SFGAccountViewMorePopupCancelButton", "SFG", null);
	}

	public WebElement SFGAccountViewMorePopupSaveBuuton() {
		return getWebElement(d, "SFGAccountViewMorePopupSaveBuuton", "SFG", null);
	}

	public List<WebElement> SFGAccountViewMorePopupAllocationFieldErrorMessage() {
		return getWebElements(d, "SFGAccountViewMorePopupAllocationFieldErrorMessage", "SFG", null);
	}

	public List<WebElement> SFGAccountIntegrationAccountName() {
		return getWebElements(d, "SFGAccountIntegrationAccountName", "SFG", null);
	}

	public List<WebElement> SFGAccountIntegrationAccountNumber() {
		return getWebElements(d, "SFGAccountIntegrationAccountNumber", "SFG", null);
	}

	public List<WebElement> SFGAccountIntegrationAccountAccountBalance() {
		return getWebElements(d, "SFGAccountIntegrationAccountAccountBalance", "SFG", null);
	}

	public WebElement SFGDashBoardHeader() {
		return getWebElement(d, "SFGDashBoardHeader", "SFG", null);
	}

	public WebElement SFGOLBId() {
		return getWebElement(d, "SFGOLBId", "SFG", null);
	}

	public WebDriver SwitchToFrame(WebDriver d) {
		WebDriver frame = d.switchTo().frame(SFGOLBId());
		return frame;

	}

	public WebElement SFGDashBoardMessage() {
		return getWebElement(d, "SFGDashBoardMessage", "SFG", null);
	}

	public WebElement SFGDashBoardLetsGoButton() {
		return getWebElement(d, "SFGDashBoardLetsGoButton", "SFG", null);
	}

	public WebElement SFGDashBoardBackTodashBoardIcon() {
		return getWebElement(d, "SFGDashBoardBackTodashBoardIcon", "SFG", null);
	}

	public WebElement SFGDashBoardBackTodashBoardIconSummery() {
		return getWebElement(d, "SFGDashBoardBackTodashBoardIconSummery", "SFG", null);
	}

	public WebElement SFGDashBoardBackTodashBoardLink() {
		return getWebElement(d, "SFGDashBoardBackTodashBoardLink", "SFG", null);
	}

	public WebElement SFGDashBoardBackTodashBoardLinkSummery() {
		return getWebElement(d, "SFGDashBoardBackTodashBoardLinkSummery", "SFG", null);
	}

	public WebElement SFGDashBoardViewAllGoals() {
		return getWebElement(d, "SFGDashBoardViewAllGoals", "SFG", null);
	}

	public List<WebElement> SFGDashBoardGoalName() {
		return getWebElements(d, "SFGDashBoardGoalName", "SFG", null);
	}

	public List<WebElement> SFGDashBoardCircleSymbol() {

		return getWebElements(d, "SFGDashBoardCircleSymbol", "SFG", null);
	}

	public WebElement SFGDashBoardNextIcon() {
		return getWebElement(d, "SFGDashBoardNextIcon", "SFG", null);
	}

	public WebElement SFGDashBoardViewAllGoalsInLastPAge() {
		return getWebElement(d, "SFGDashBoardViewAllGoalsInLatPAge", "SFG", null);
	}

	public WebElement SFGCmpleteGoalButton() {
		return getWebElement(d, "SFGCmpleteGoalButton", "SFG", null);
	}

	public WebElement SFGCmpleteGoalConfirmationButtonButton() {
		return getWebElement(d, "SFGCmpleteGoalConfirmationButtonButton", "SFG", null);
	}

	public List<WebElement> SFGCmpleteGoalName() {

		return getWebElements(d, "SFGCmpleteGoalName", "SFG", null);
	}

	public WebElement SFGMarkAsCmpleteButton() {

		return getWebElement(d, "SFGMarkAsCmpleteButton", "SFG", null);
	}

	public WebElement SFGMarkAsCmpletePopUpButton() {

		return getWebElement(d, "SFGMarkAsCmpletePopUpButton", "SFG", null);
	}

	public WebElement SFGMyprofileLink() {

		return getWebElement(d, "SFGMyprofileLink", "SFG", null);
	}

	public List<WebElement> SFGMyprofileListpreference() {

		return getWebElements(d, "SFGMyprofileListpreference", "SFG", null);
	}

	public WebElement SFGMyprofileListpreferenceCurrencyDropDown() {

		return getWebElement(d, "SFGMyprofileListpreferenceCurrencyDropDown", "SFG", null);
	}

	public List<WebElement> SFGMyprofileListpreferenceCurrencyList() {

		return getWebElements(d, "SFGMyprofileListpreferenceCurrencyList", "SFG", null);
	}

	public WebElement SFGMyprofileSaveChanges() {

		return getWebElement(d, "SFGMyprofileSaveChanges", "SFG", null);
	}

	public WebElement SFGProfileAmountFormate() {

		return getWebElement(d, "SFGProfileAmountFormate", "SFG", null);
	}

	public List<WebElement> SFGProfileAmountFormateList() {

		return getWebElements(d, "SFGProfileAmountFormateList", "SFG", null);
	}

	public WebElement SFGProfilDateFormate() {

		return getWebElement(d, "SFGProfilDateFormate", "SFG", null);
	}

	public List<WebElement> SFGProfileDateFormateList() {

		return getWebElements(d, "SFGProfileDateFormateList", "SFG", null);
	}

	public void changeCurrencyValue(int currencyvalue) {
		click(SFGMyprofileLink());
		waitForPageToLoad(2000);
		click(SFGMyprofileListpreference().get(3));
		waitForPageToLoad(2000);
		click(SFGMyprofileListpreferenceCurrencyDropDown());
		waitForPageToLoad(2000);
		click(SFGMyprofileListpreferenceCurrencyList().get(currencyvalue));
		waitForPageToLoad(2000);
		click(SFGMyprofileSaveChanges());
		waitForPageToLoad(5000);
	}

	public void changeDateCurrencyFormate() {
		click(SFGMyprofileLink());
		waitForPageToLoad(2000);
		click(SFGMyprofileListpreference().get(3));
		waitForPageToLoad(2000);
		click(SFGProfilDateFormate());
		waitForPageToLoad(2000);
		click(SFGProfileDateFormateList().get(0));
		waitForPageToLoad(2000);
		click(SFGProfileAmountFormate());
		waitForPageToLoad(2000);
		click(SFGProfileAmountFormateList().get(1));
		waitForPageToLoad(2000);
		click(SFGMyprofileSaveChanges());
		waitForPageToLoad(5000);
	}

	public WebElement SFGCurrencyMissMatchIcon() {

		return getWebElement(d, "SFGCurrencyMissMatchIcon", "SFG", null);
	}

	public WebElement SFGCurrencyMissMatchTitle() {

		return getWebElement(d, "SFGCurrencyMissMatchTitle", "SFG", null);
	}

	public WebElement SFGCurrencyMissMatchMessage1() {

		return getWebElement(d, "SFGCurrencyMissMatchMessage1", "SFG", null);
	}

	public WebElement SFGCurrencyMissMatchMessage2() {

		return getWebElement(d, "SFGCurrencyMissMatchMessage2", "SFG", null);
	}

	public WebElement SFGCurrencyMissMatchTitleInStep1() {

		return getWebElement(d, "SFGCurrencyMissMatchTitleInStep1", "SFG", null);
	}

	public WebElement SFGCurrencyMissMatchMessage1Instep1() {

		return getWebElement(d, "SFGCurrencyMissMatchMessage1Instep1", "SFG", null);
	}

	public WebElement SFGCurrencyMissMatchLinkAccountButton() {

		return getWebElement(d, "SFGCurrencyMissMatchLinkAccountButton", "SFG", null);
	}

	public WebElement SFGLinkAnAccountMessage() {

		return getWebElement(d, "SFGLinkAnAccountMessage", "SFG", null);
	}

	public WebElement SFGOLBLinkAnAccount() {

		return getWebElement(d, "SFGOLBLinkAnAccount", "SFG", null);
	}

	public List<WebElement> SFGOLBBackButton() {

		return getWebElements(d, "SFGOLBBackButton", "SFG", null);
	}

	public List<WebElement> SFGOLBBackButtonHidden() {

		return getWebElements(d, "SFGOLBBackButtonHidden", "SFG", null);
	}

	public WebElement SFGOLBAccountDelete() {

		return getWebElement(d, "SFGOLBAccountDelete", "SFG", null);
	}

	public WebElement SFGOLBAccountDeleteCheckBox() {

		return getWebElement(d, "SFGOLBAccountDeleteCheckBox", "SFG", null);
	}

	public WebElement SFGOLBAccountDeletePupUpDete() {

		return getWebElement(d, "SFGOLBAccountDeletePupUpDete", "SFG", null);
	}

	public WebElement SFGOLBAccountErrormEssage() {

		return getWebElement(d, "SFGOLBAccountErrormEssage", "SFG", null);
	}

	public String getAtributeVAlue(String atributevalue) {
		JavascriptExecutor e = (JavascriptExecutor) d;
		// return (String) e.executeScript(String.format("return $('#%s').val();",
		// CGS1.GoalAmountInput.getAttribute("id")));
		return (String) e.executeScript(String.format("return $('#%s').val();", atributevalue));
		// System.out.println("hello"+a);
	}

	public Date convetStringToDate(String date) throws ParseException {
		DateFormat format = new SimpleDateFormat("mm/dd/yyyy");
		Date date1 = format.parse(date);
		return date1;

	}

	public String convetStringToDate(Date date) throws ParseException {
		DateFormat format = new SimpleDateFormat("mm/dd/yyyy");
		Date today = Calendar.getInstance().getTime();
		String reportDate = format.format(today);
		return reportDate;

	}

	public String targateDateSelect(int futureDate) {
		Calendar c = Calendar.getInstance();
		c.add(Calendar.DATE, futureDate);
		System.out.println(new SimpleDateFormat("MM/dd/yyyy").format(c.getTime()));
		return new SimpleDateFormat("MM/dd/yyyy").format(c.getTime());
	}

	public String targateDateSelectMMM(int futureDate) {
		Calendar c = Calendar.getInstance();
		c.add(Calendar.DATE, futureDate);
		System.out.println(new SimpleDateFormat("MMM dd, yyyy").format(c.getTime()));
		return new SimpleDateFormat("MMM dd, yyyy").format(c.getTime());
	}

	public String targateDateSelectMMMM(int futureDate) {
		Calendar c = Calendar.getInstance();
		c.add(Calendar.DATE, futureDate);
		System.out.println(new SimpleDateFormat("MMMM dd, yyyy").format(c.getTime()));
		return new SimpleDateFormat("MMMM dd, yyyy").format(c.getTime());
	}

	public String targateDateSelectMMMMD(int futureDate) {
		Calendar c = Calendar.getInstance();
		c.add(Calendar.DATE, futureDate);
		System.out.println(new SimpleDateFormat("MMMM d, yyyy").format(c.getTime()));
		return new SimpleDateFormat("MMMM d, yyyy").format(c.getTime());
	}

	public String targateDateSelectdd(int futureDate) {
		Calendar c = Calendar.getInstance();
		c.add(Calendar.DATE, futureDate);
		System.out.println(new SimpleDateFormat("DD/mm/yyyy").format(c.getTime()));
		return new SimpleDateFormat("DD/mm/yyyy").format(c.getTime());
	}

	public WebElement verifySFGFundingSaveButton() {
		return getWebElement(d, "SFGFundingSaveButtonForMobile", "SFG", null);
	}

	public WebElement verifyClose() {
		return getWebElement(d, "closeButton", "SFG", null);
	}

	public WebElement verifyCloseButtonIcon() {
		return getWebElement(d, "closeButtonIcon", "SFG", null);
	}

	public WebElement verifyBackButton() {
		return getWebElement(d, "backButtonForMobile", "SFG", null);
	}

	public WebElement verifyBackToDashBoardIcon() {
		return getWebElement(d, "backToDashBoardIconForMobile", "SFG", null);
	}

	public WebElement verifyBackToDashBoardSummaryIcon() {
		return getWebElement(d, "backToDashBoardSummaryIconForMobile", "SFG", null);
	}

	public List<WebElement> nextGoalButtonMobile() {
		return getWebElements(d, "nextGoalButtonMobile", "SFG", null);
	}

	public boolean isNextGoalMobile() {
		return PageParser.isElementPresent("nextGoalButtonMobile", "SFG", null);
	}
}
