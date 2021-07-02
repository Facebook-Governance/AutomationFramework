/*******************************************************************************
 * Copyright (c) 2018 Yodlee Inc. All Rights Reserved.
 * 
 * This software is the confidential and proprietary information of
 * Yodlee, Inc. Use is subject to license terms.
 * 
 * @author
 ******************************************************************************/
package com.omni.pfm.pages.SFG;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.omni.pfm.utility.SeleniumUtil;

public class SFG_CustomGoalEdit_Loc extends SeleniumUtil {
	static Logger logger = LoggerFactory.getLogger(SFG_CustomGoalEdit_Loc.class);
	public WebDriver d = null;
	static String frameName = null;

	public SFG_CustomGoalEdit_Loc(WebDriver d) {
		this.d = d;
	}

	public WebElement goalProgressTag() {
		return getVisibileWebElement(d, "goalProgressTag", "SFG", null);
	}

	public WebElement newGoalbutton() {
		return getWebElement(d, "newGoalbutton", "SFG", null);
	}

	public List<WebElement> allInprogressGoal() {
		return getWebElements(d, "InProgressGoalList", "SFG", null);
	}

	public List<WebElement> SFGhighLevlcategories() {
		return getWebElements(d, "SFGhighLevlcategories", "SFG", null);
	}

	public List<WebElement> getSubCatText() {
		return getWebElements(d, "getSubCatText", "SFG", null);
	}

	public WebElement GoalNameInput() {
		return getWebElement(d, "GoalNameInput", "SFG", null);
	}

	public WebElement GoalAmountInput() {
		return getWebElement(d, "GoalAmountInput", "SFG", null);
	}

	public WebElement GoalAmtFrqBtnInput() {
		return getWebElement(d, "GoalAmtFrqBtnInput", "SFG", null);
	}

	public WebElement frequencyRadioButtonText() {
		return getWebElement(d, "frequencyRadioButtonText", "SFG", null);
	}

	public WebElement GoalAmtFrqInput() {
		return getWebElement(d, "GoalAmtFrqInput", "SFG", null);
	}

	public WebElement frequencyDropdown1() {
		return getWebElement(d, "frequencyDropdown1", "SFG", null);
	}

	public List<WebElement> allFrequency() {
		return getWebElements(d, "allFrequency", "SFG", null);
	}

	public WebElement nextbuttonClick() {
		return getWebElement(d, "nextbuttonClick", "SFG", null);
	}

	public WebElement closeIconinPopup() {
		return getWebElement(d, "closeIconinPopup", "SFG", null);
	}

	public WebElement QuitwithoutSaving() {
		return getWebElement(d, "QuitwithoutSaving", "SFG", null);
	}

	public List<WebElement> setValueInAccounts(int j, int k, int m) {
		return d.findElements(By.xpath(
				"//div[contains(@class,'accountsSummaryList')]/div//div[contains(@class,'account-type-group-container')]/div[contains(@class,'accounts-row')]//input[contains(@class,'y-range-slider-text-input')]"));
	}

	public WebElement AccountDropDown1() {
		return getWebElement(d, "AccountDropDown1", "SFG", null);
	}

	public WebElement MultiAccountSwitch() {
		return getWebElement(d, "MultiAccountSwitch", "SFG", null);
	}

	public List<WebElement> checkBoxList() {
		return getWebElements(d, "checkBoxList", "SFG", null);
	}

	public WebElement selectAccount(Integer index) {
		return checkBoxList().get(2 * index);

	}

	public WebElement unSelectAccount(Integer index) {
		Integer a = 2 * index + 1;
		System.out.println(a);
		return checkBoxList().get(2 * index + 1);

	}

	public List<WebElement> amountBoxList() {
		return getWebElements(d, "amountBoxList", "SFG", null);
	}

	public WebElement SaveAccountsBtn() {
		return getWebElement(d, "SaveAccountsBtn", "SFG", null);
	}

	public WebElement saveTransferButton1() {
		return getWebElement(d, "SFGFundingSaveButton", "SFG", null);
	}

	public WebElement editgoalbutton() {
		return getVisibileWebElement(d, "editgoalbutton", "SFG", null);
	}

	public List<WebElement> goalAccount() {
		return getWebElements(d, "goalAccountList", "SFG", null);
	}

	public WebElement goalnametextbox() {
		return getVisibileWebElement(d, "goalnametextbox", "SFG", null);
	}

	public WebElement amountfield1() {
		return getVisibileWebElement(d, "amountfield1", "SFG", null);
	}

	public WebElement goalNameErrorMsg() {
		return getVisibileWebElement(d, "goalNameErrorMsg", "SFG", null);
	}

	public WebElement goalAmountFieldErrorMsg() {
		return getVisibileWebElement(d, "goalAmountFieldErrorMsg", "SFG", null);
	}

	public String targateDate1(int futureDate) {
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

	public WebElement dateField() {
		return getVisibileWebElement(d, "dateField", "SFG", null);
	}

	public WebElement dateFieldErrorMsg() {
		return getVisibileWebElement(d, "dateFieldErrorMsg", "SFG", null);
	}

	public WebElement onGoingFundingChange() {
		return getVisibileWebElement(d, "onGoingFundingChange", "SFG", null);
	}

	public WebElement SFGAmountValue() {
		return getVisibileWebElement(d, "SFGAmountValue", "SFG", null);
	}

	public List<WebElement> StartEndFreqLabels() {
		return getWebElements(d, "StartEndFreqLabels", "SFG", null);
	}

	public List<WebElement> StartEndFreqValues() {
		return getWebElements(d, "StartEndFreqValues", "SFG", null);
	}

	public WebElement SFGFundingSaveButton() {
		return getVisibileWebElement(d, "SFGFundingSaveButton", "SFG", null);
	}

	public WebElement SFGFundingCancelButton() {
		return getVisibileWebElement(d, "SFGFundingCancelButton", "SFG", null);
	}

	public WebElement FundingPageAccount() {
		return getVisibileWebElement(d, "FundingPageAccount", "SFG", null);
	}

	public List<WebElement> insufficientFundingError() {
		return getWebElements(d, "insufficientFundingError", "SFG", null);
	}

	public List<WebElement> insufficientFundingIcon() {
		return getWebElements(d, "insufficientFundingIcon", "SFG", null);
	}

	public List<WebElement> insufficientFundingMsg() {
		return getWebElements(d, "insufficientFundingMsg", "SFG", null);
	}

	public WebElement deleteFunding() {
		return getVisibileWebElement(d, "deleteFunding", "SFG", null);
	}

	public WebElement ongoingFreqLabel() {
		return getVisibileWebElement(d, "ongoingFreqLabel", "SFG", null);
	}

	public WebElement ongoingStartLabel() {
		return getVisibileWebElement(d, "ongoingStartLabel", "SFG", null);
	}

	public WebElement ongoingEndLabel() {
		return getVisibileWebElement(d, "ongoingEndLabel", "SFG", null);
	}

	public WebElement ongoingFreqValue() {
		return getVisibileWebElement(d, "ongoingFreqValue", "SFG", null);
	}

	public WebElement ongoingStartValue() {
		return getVisibileWebElement(d, "ongoingStartValue", "SFG", null);
	}

	public WebElement ongoingEndDateValue() {
		return getVisibileWebElement(d, "ongoingEndDateValue", "SFG", null);
	}

	public WebElement addFunding() {
		return getVisibileWebElement(d, "addFunding", "SFG", null);
	}

	public WebElement SFGBackButton() {
		return getVisibileWebElement(d, "SFGBackButton", "SFG", null);
	}

	public WebElement SaveHeader() {
		return getVisibileWebElement(d, "SaveHeader", "SFG", null);
	}

	public WebElement SaveHeaderPara1() {
		return getVisibileWebElement(d, "SaveHeaderPara1", "SFG", null);
	}

	public WebElement SaveHeaderPara2() {
		return getVisibileWebElement(d, "SaveHeaderPara2", "SFG", null);
	}

	public List<WebElement> SaveOptionsList() {
		return getWebElements(d, "SaveOptionsList", "SFG", null);
	}

	public WebElement SavepopUpAddFunds() {
		return getVisibileWebElement(d, "SavepopUpAddFunds", "SFG", null);
	}

	public WebElement SavepopUpCancelButton() {
		return getVisibileWebElement(d, "SavepopUpCancelButton", "SFG", null);
	}
	
	public WebElement Crossicon() {
		return getVisibileWebElement(d, "CrossIconButton", "SFG", null);
	}

	public WebElement getStartedTab() {
		return getVisibileWebElement(d, "getStartedTab", "SFG", null);
	}

	public WebElement createGoalTab() {
		return getVisibileWebElement(d, "createGoalTab", "SFG", null);
	}

	public WebElement AcntSelectedTickMark() {
		return getVisibileWebElement(d, "AcntSelectedTickMark", "SFG", null);
	}

	public WebElement account2Select() {
		return getVisibileWebElement(d, "account2Select", "SFG", null);
	}

	public List<WebElement> afterEditScreenRightValue() {
		return getWebElements(d, "afterEditScreenRightValue", "SFG", null);
	}

	public WebElement NextBtnStep1() {
		return getWebElement(d, "NextBtnStep1", "SFG", null);
	}

	public WebElement arrowIcon() {
		return getWebElement(d, "arrowIcon", "SFG", null);
	}

	public List<WebElement> deletePopupHeader() {
		return getWebElements(d, "deletePopupHeader", "SFG", null);
	}

	public WebElement SFGFundingDeletePopupCancelButton() {
		return getWebElement(d, "SFGFundingDeletePopupCancelButton", "SFG", null);
	}

	public WebElement SFGFundingDeletePopupDeleteButton() {
		return getWebElement(d, "SFGFundingDeletePopupDeleteButton", "SFG", null);
	}

	public WebElement editArrow() {
		return getWebElement(d, "editArrow", "SFG", null);
	}

	public List<WebElement> acntNameLabel() {
		return getWebElements(d, "acntNameLabel", "SFG", null);
	}

	public List<WebElement> BalanceAvailable() {
		return getWebElements(d, "BalanceAvailable", "SFG", null);
	}

	public List<WebElement> removeAmountButton() {
		return getWebElements(d, "removeAmountButton", "SFG", null);
	}

	public List<WebElement> amountInputBox() {
		return getWebElements(d, "amountInputBox", "SFG", null);
	}

	public List<WebElement> viewDetails() {
		return getWebElements(d, "viewDetails", "SFG", null);
	}

	public WebElement BalanceLabel() {
		return getWebElement(d, "BalanceLabel", "SFG", null);
	}

	public List<WebElement> goalNameLabel() {
		return getWebElements(d, "goalNameLabel", "SFG", null);
	}

	public List<WebElement> goalAmountLabel() {
		return getWebElements(d, "goalAmountLabel", "SFG", null);
	}
	
	public WebElement EditedGoalAmountValue() {
		return getWebElement(d, "EditedGoalAmountValue", "SFG", null);
	}
	

	public WebElement ListViewLabel() {
		return getWebElement(d, "ListViewLabel", "SFG", null);
	}

	public WebElement saveSFGAccountsDetails() {
		return getWebElement(d, "saveSFGAccountsDetails", "SFG", null);
	}

	public WebElement cancelAccountDetails() {
		return getWebElement(d, "cancelAccountDetails", "SFG", null);
	}

	public WebElement AllocationProgressBar() {
		return getWebElement(d, "AllocationProgressBar", "SFG", null);
	}

	public WebElement AllocationProgressBarLabel() {
		return getWebElement(d, "AllocationProgressBarLabel", "SFG", null);
	}

	public WebElement EditPopupDoneButton() {
		return getWebElement(d, "EditPopupDoneButton", "SFG", null);
	}

	public List<WebElement> EditPopupSavingAmount() {
		return getWebElements(d, "EditPopupSavingAmount", "SFG", null);
	}

	public WebElement EditPopupAddAccount() {
		return getWebElement(d, "EditPopupAddAccount", "SFG", null);
	}

	public WebElement addAccountPopupCurrencyLabel() {
		return getWebElement(d, "addAccountPopupCurrencyLabel", "SFG", null);
	}

	public List<WebElement> addAccountPopupCurrencyLabelValue() {
		return getWebElements(d, "addAccountPopupCurrencyLabelValue", "SFG", null);
	}

	public WebElement AccountPopUpSave() {
		return getWebElement(d, "AccountPopUpSave", "SFG", null);
	}

	public WebElement addedAcntName() {
		return getWebElement(d, "addedAcntName", "SFG", null);
	}

	public List<WebElement> afterSavingEditScreenRightValue() {
		return getWebElements(d, "afterSavingEditScreenRightValue", "SFG", null);
	}

	public WebElement saveGoalPopUp() {
		return getWebElement(d, "saveGoalPopUp", "SFG", null);
	}

	public WebElement SaveAsItIs() {
		return getWebElement(d, "SaveAsItIs", "SFG", null);
	}

	public List<WebElement> saveGoalPopUpLeft() {
		return getWebElements(d, "saveGoalPopUpLeft", "SFG", null);
	}

	public List<WebElement> saveGoalPopUpRight() {
		return getWebElements(d, "saveGoalPopUpRight", "SFG", null);
	}

	public WebElement saveGoalPopUpGoalStatusHeader() {
		return getWebElement(d, "saveGoalPopUpGoalStatusHeader", "SFG", null);
	}

	public WebElement saveGoalPopUpPara1() {
		return getWebElement(d, "saveGoalPopUpPara1", "SFG", null);
	}

	public WebElement saveGoalPopUpPara2() {
		return getWebElement(d, "saveGoalPopUpPara2", "SFG", null);
	}

	public WebElement RB1label() {
		return getWebElement(d, "RB1label", "SFG", null);
	}

	public WebElement RB2label() {
		return getWebElement(d, "RB2label", "SFG", null);
	}

	public WebElement SaveGoalCancelButton() {
		return getWebElement(d, "SaveGoalCancelButton", "SFG", null);
	}

	public WebElement SaveGoalConfirmButton() {
		return getWebElement(d, "SaveGoalConfirmButton", "SFG", null);
	}

	public WebElement deleteGoalButton() {
		return getWebElement(d, "deleteGoalButton", "SFG", null);
	}

	public WebElement deleteGoalPara1() {
		return getWebElement(d, "deleteGoalPara1", "SFG", null);
	}

	public WebElement deleteGoalPara2() {
		return getWebElement(d, "deleteGoalPara2", "SFG", null);
	}

	public WebElement SFGDeletPopupDeletButton() {
		return getWebElement(d, "SFGDraftGoalDeletPopupDeletButton", "SFG", null);
	}

	public WebElement SFGDeletPopupCancelButton() {
		return getWebElement(d, "SFGDraftGoalDeletPopupCancelButton", "SFG", null);
	}

	public WebElement imageBarGoalName() {
		return getWebElement(d, "imageBarGoalName", "SFG", null);
	}

	public WebElement imageBarGoalAmount() {
		return getWebElement(d, "imageBarGoalAmount", "SFG", null);
	}

	public WebElement imageBarGoalDate() {
		return getWebElement(d, "imageBarGoalDate", "SFG", null);
	}

	public WebElement SavingSucessMsg() {
		WebDriverWait wait = new WebDriverWait(d, 10);
		return wait.until(
				ExpectedConditions.elementToBeClickable(getWebElement(d, "SavingSucessMsg", "SFG", null)));
	}

	public WebElement AcntSummaryGoalStatusHeader() {
		return getWebElement(d, "recurOnGoingFundingHeader", "SFG", null);
	}

	public List<WebElement> SFGEditCurrentStausDetailslabel() {
		return getWebElements(d, "SFGEditCurrentStausDetailslabel", "SFG", null);
	}

	public List<WebElement> SFGEditCurrentStausDetailsValue() {
		return getWebElements(d, "SFGEditCurrentStausDetailsValue", "SFG", null);
	}

	public WebElement GoalSummaryFooterLine() {
		return getWebElement(d, "GoalSummaryFooterLine", "SFG", null);
	}

	public WebElement markAsCompleteConfirmText() {
		return getWebElement(d, "markAsCompleteConfirmText", "SFG", null);
	}

	public List<WebElement> markAsCompleteLeftLabel() {
		return getWebElements(d, "markAsCompleteLeftLabel", "SFG", null);
	}

	public List<WebElement> markAsCompleteRightLabel() {
		return getWebElements(d, "markAsCompleteRightLabel", "SFG", null);
	}

	public WebElement VictoryTickMarkIcon() {
		return getWebElement(d, "VictoryTickMarkIcon", "SFG", null);
	}

	public WebElement VictoryText() {
		return getWebElement(d, "VictoryText", "SFG", null);
	}

	public WebElement VictoryText2() {
		return getWebElement(d, "VictoryText2", "SFG", null);
	}

	public WebElement startNewGoalTab() {
		return getWebElement(d, "startNewGoalTab", "SFG", null);
	}

	public WebElement SFGcategoryHeading() {
		return getWebElement(d, "SFGcategoryHeading", "SFG", null);
	}

	public WebElement backToNewGoal() {
		return getWebElement(d, "categoriesLevelBackBtn", "SFG", null);
	}

	public List<WebElement> SFGIVictoryGoalNames() {
		return getWebElements(d, "SFGIVictoryGoalNames", "SFG", null);
	}

	public WebElement account_balance_SFG() {
		return getWebElement(d, "account_balance_SFG", "SFG", null);
	}

	public void createGoalwithMultipleAccount(String goalName, String goalAmount, String Goalfrequency,
			String oneTimeAmount) {
		// click(newGoalbutton());
		click(SFGhighLevlcategories().get(0));
		waitForPageToLoad(3000);
		click(getSubCatText().get(0));
		waitForPageToLoad(3000);
		click(GoalNameInput());
		GoalNameInput().clear();
		GoalNameInput().sendKeys(goalName);
		waitForPageToLoad(2000);
		click(GoalAmountInput());
		GoalAmountInput().clear();
		GoalAmountInput().sendKeys(goalAmount);
		waitForPageToLoad(2000);

		click(GoalAmtFrqBtnInput());
		GoalAmtFrqInput().sendKeys(Goalfrequency);
		click(frequencyDropdown1());
		waitForPageToLoad(2000);

		click(allFrequency().get(2));

		waitForPageToLoad(3000);
		click(nextbuttonClick());

		click(AccountDropDown1());
		waitForPageToLoad(3000);
		click(MultiAccountSwitch());
		waitForPageToLoad();

		click(unSelectAccount(0));
		click(amountBoxList().get(0));
		amountBoxList().get(0).sendKeys("50");
		amountBoxList().get(0).sendKeys(Keys.TAB);
		waitFor(1);
		click(selectAccount(1));
		click(amountBoxList().get(1));
		scrollToWebElementMiddle(d, amountBoxList().get(1));
		waitFor(3);
		amountBoxList().get(1).clear();
		amountBoxList().get(1).sendKeys("50");
		amountBoxList().get(1).sendKeys(Keys.TAB);

		click(SaveAccountsBtn());
		waitForPageToLoad();
		click(nextbuttonClick());
		click(saveTransferButton1());
		waitForPageToLoad();
		click(nextbuttonClick());
	}

	public WebElement verifyClose() {
		return getWebElement(d, "closeEditButton", "SFG", null);
	}

	public WebElement verifySaveChangesBtn() {
		return getWebElement(d, "saveChangesButtonForMobile", "SFG", null);
	}

}
