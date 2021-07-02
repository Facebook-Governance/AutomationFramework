/**
* Copyright (c) 2018 Yodlee Inc. All Rights Reserved.
*
* This software is the confidential and proprietary information of
* Yodlee, Inc. Use is subject to license terms.
*
* @author Ashwin PM
*/
package com.omni.pfm.pages.TransactionEnhancement1;

import java.util.List;
import java.util.Objects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.omni.pfm.PageProcessor.PageParser;
import com.omni.pfm.utility.SeleniumUtil;

public class NoEditCategoryForInstance_Loc extends SeleniumUtil {
	static Logger logger = LoggerFactory.getLogger(NoEditCategoryForInstance_Loc.class);
	public WebDriver d = null;

	public NoEditCategoryForInstance_Loc(WebDriver d) {
		this.d = d;
	}

	public void clickOnProjectedTransaction(String transactionDescription) {
		waitUntilSpinnerWheelIsInvisible();
		By projectedTransactionHeader = getByObject("Transaction", null, "projectedTransactionsHeader");
		if (isDisplayed(projectedTransactionHeader, 3)) {
			click(projectedTransactionHeader);
			waitUntilSpinnerWheelIsInvisible();
			waitFor(1);
			click(projectedTransactionHeader);
			waitUntilSpinnerWheelIsInvisible();
			waitFor(1);
			if (!Objects.isNull(getAttribute(projectedTransactionHeader, "aria-expanded"))) {
				if (getAttribute(projectedTransactionHeader, "aria-expanded").equals("false")) {
					click(projectedTransactionHeader);
				}
			}
		}
		String dummXpath = getVisibileElementXPath(d, "Transaction", null,
				"GenUpcomingTransactionDescription_TRANSENH");
		dummXpath = dummXpath.replaceAll("dummyText", transactionDescription);
		click(By.xpath(dummXpath));
	}

	public void clickOnProjectedTransactionmobile(String transactionDescription) {
		String dummXpath = getVisibileElementXPath(d, "Transaction", null,
				"GenUpcomingTransactionDescription_TRANSENH_mobile");
		dummXpath = dummXpath.replaceAll("dummyText", transactionDescription);
		click(By.xpath(dummXpath));
	}

	public void clickOnPostedTransaction(String transactionDescription) {
		String dummXpath = getVisibileElementXPath(d, "Transaction", null, "GenPostedTransactionDescription_TRANSENH");
		dummXpath = dummXpath.replaceAll("dummyText", transactionDescription);
		d.findElement(By.xpath(dummXpath)).click();
		waitForPageToLoad(2000);
	}

	public void clickOnPostedTransactionmobile(String transactionDescription) {
		String dummXpath = getVisibileElementXPath(d, "Transaction", null,
				"GenPostedTransactionDescription_TRANSENH_mobile");
		dummXpath = dummXpath.replaceAll("dummyText", transactionDescription);
		d.findElement(By.xpath(dummXpath)).click();
		waitForPageToLoad(2000);
	}

	public WebElement addNewTransactionButton() {
		return getVisibileWebElement(d, "addTransactionButton_TRANSENH", "Transaction", null);
	}

	public WebElement addNewTransactionButtonMobile() {
		return getVisibileWebElement(d, "MobileaddManualLink_AMT", "Transaction", null);
	}

	public WebElement mobileMoreButton() {
		return getVisibileWebElement(d, "mobilemoreBtn", "Transaction", null);
	}

	public boolean isAddNewTransactionButtonMobile() {
		return PageParser.isElementPresent("MobileaddManualLink_AMT", "Transaction", null);
	}

	public void addMannualTransaction(String amount, String description, String transactionType,
			String debitedFromAccName, String frequency, String scheduledDate, String endDateOptional,
			String category) {
		typeTransAmount(amount);
		typeDescription(description);
		selectTransactionType(transactionType);
		selectDebitedFrom(debitedFromAccName);
		selectFrequency(frequency);
		typeScheduledDate(scheduledDate);
		if (endDateOptional != null) {
			typeEndDate(endDateOptional);
			waitForPageToLoad(2000);
		}
		selectCategory(category);
		clickAddTransaction();
		waitUntilSpinnerWheelIsInvisible();
	}

	private void clickAddTransaction() {
		click(getVisibileWebElement(d, "addButton_AddTransPopUp_TRANSENH", "Transaction", null));
	}

	private void typeTransAmount(String amount) {
		getVisibileWebElement(d, "amtField_AddTransPopUp_TRANSENH", "Transaction", null).sendKeys(amount);
	}

	private void typeDescription(String description) {
		getVisibileWebElement(d, "descField_AddTransPopUp_TRANSENH", "Transaction", null).sendKeys(description);
	}

	private void selectTransactionType(String transactionType) {
		String dropDownXpath = getVisibileElementXPath(d, "Transaction", null,
				"transTypeDropDown_AddTransPopUp_TRANSENH");
		d.findElement(By.xpath(dropDownXpath)).click();
		waitForPageToLoad(2000);
		String dropDownOptionXpath = getVisibileElementXPath(d, "Transaction", null,
				"transTypeDropDownOption_AddTransPopUp_TRANSENH");
		dropDownOptionXpath = dropDownOptionXpath.replaceAll("dummyText", transactionType);
		d.findElement(By.xpath(dropDownOptionXpath)).click();
		waitForPageToLoad(2000);
	}

	private void selectDebitedFrom(String debitedFromAccName) {
		String dropDownXpath = getVisibileElementXPath(d, "Transaction", null,
				"debitedFromDropDown_AddTransPopUp_TRANSENH");
		d.findElement(By.xpath(dropDownXpath)).click();
		waitForPageToLoad(2000);
		String dropDownOptionXpath = getVisibileElementXPath(d, "Transaction", null,
				"debitedFromDropDownOption_AddTransPopUp_TRANSENH");
		dropDownOptionXpath = dropDownOptionXpath.replaceAll("dummyText", debitedFromAccName);
		d.findElement(By.xpath(dropDownOptionXpath)).click();
		waitForPageToLoad(2000);
	}

	private void selectFrequency(String frequency) {
		String dropDownXpath = getVisibileElementXPath(d, "Transaction", null, "freqDropDown_AddTransPopUp_TRANSENH");
		d.findElement(By.xpath(dropDownXpath)).click();
		waitForPageToLoad(2000);
		String dropDownOptionXpath = getVisibileElementXPath(d, "Transaction", null,
				"freqDropDownOption_AddTransPopUp_TRANSENH");
		dropDownOptionXpath = dropDownOptionXpath.replaceAll("dummyText", frequency);
		d.findElement(By.xpath(dropDownOptionXpath)).click();
	}

	private void typeScheduledDate(String scheduledDate) {
		getVisibileWebElement(d, "scheduledDateField_AddTransPopUp_TRANSENH", "Transaction", null)
				.sendKeys(scheduledDate);
		waitForPageToLoad(2000);
	}

	private void typeEndDate(String endDate) {
		getVisibileWebElement(d, "endDateField_AddTransPopUp_TRANSENH", "Transaction", null).sendKeys(endDate);
	}

	private void selectCategory(String category) {
		String dropDownXpath = getVisibileElementXPath(d, "Transaction", null,
				"categoryDropDown_AddTransPopUp_TRANSENH");
		d.findElement(By.xpath(dropDownXpath)).click();
		waitForPageToLoad(2000);
		String dropDownOptionXpath = getVisibileElementXPath(d, "Transaction", null,
				"categoryDropDownOption_AddTransPopUp_TRANSENH");
		dropDownOptionXpath = dropDownOptionXpath.replaceAll("dummyText", category);
		waitForPageToLoad(2000);
		// d.findElement(By.xpath(dropDownOptionXpath)).click();
		click(d.findElement(By.xpath(dropDownOptionXpath)));
		waitForPageToLoad(3000);
	}

	/* Add new Transaction related ends */
	public WebElement categoryText() {
		return getVisibileWebElement(d, "CategoryDropDownText_TRANSENH", "Transaction", null);
	}

	public WebElement categoryDropDown() {
		return getVisibileWebElement(d, "CategoryDropDownDropDown_TRANSENH", "Transaction", null);
	}

	public WebElement projectedTransactionsTab() {
		return getVisibileWebElement(d, "projectedTransactionsTAB_TRANSENH", "Transaction", null);
	}

	public WebElement transactionDetailsCancelBtn() {
		return getVisibileWebElement(d, "transactionDetailsCANCEL_TRANSENH", "Transaction", null);
	}

	public WebElement allTransInSeriesChkBox() {
		return getVisibileWebElement(d, "allTransInSeriesChkBox_TRANSENH", "Transaction", null);
	}

	public WebElement loadMoreTransactionBtn() {
		return getVisibileWebElement(d, "loadMoreTransactionButton_TRANSENH", "Transaction", null);
	}

	/* Expense Analysis related */
	public WebElement expCoachMarkContinueBtn() {
		return getVisibileWebElement(d, "expCoachMarkContinueBtn_TRANSENH", "Transaction", null);
	}

	public WebElement expCoachMarkGoToAnalysisBtn() {
		return getVisibileWebElement(d, "expCoachMarkGoToAnalysisBtn_TRANSENH", "Transaction", null);
	}

	public List<WebElement> expLegends() {
		return getWebElements(d, "expLegends_TRANSENH", "Transaction", null);
	}

	public void clickOnHighLevelCategory(String highLevelCategoryName) {
		String xpath = getVisibileElementXPath(d, "Transaction", null, "genHighLevelCategory_TRANSENH");
		xpath = xpath.replaceAll("dummyText", highLevelCategoryName);
		click(d.findElement(By.xpath(xpath)));
		waitForPageToLoad(2000);
	}

	public WebElement expAnalIncAnalNavigationBtn() {
		return getVisibileWebElement(d, "expAnalIncAnalNavigationBtn_TRANSENH", "Transaction", null);
	}

	public WebElement incomeAnalysisOptionInNav() {
		return getVisibileWebElement(d, "incomeAnalysisOptionInNav_TRANSENH", "Transaction", null);
	}

	/* Budget related */
	public WebElement budgetGetStartedBtn() {
		return getVisibileWebElement(d, "budgetGetStartedBtn_TRANSENH", "Transaction", null);
	}

	public WebElement budgetStep1NextBtn() {
		return getVisibileWebElement(d, "budgetStep1NextBtn_TRANSENH", "Transaction", null);
	}

	public boolean isnext1budgetmobile() {
		return PageParser.isElementPresent("next1budgetmobile", "Transaction", null);
	}

	public boolean isnext2budgetmobile() {
		return PageParser.isElementPresent("next2budgetmobile", "Transaction", null);
	}

	public WebElement next1budgetmobile() {
		return getVisibileWebElement(d, "next1budgetmobile", "Transaction", null);
	}

	public WebElement next2budgetmobile() {
		return getVisibileWebElement(d, "next2budgetmobile", "Transaction", null);
	}

	public WebElement budgetStep2NextBtn() {
		return getVisibileWebElement(d, "budgetStep2NextBtn_TRANSENH", "Transaction", null);
	}

	public WebElement budgetStep3DoneBtn() {
		return getVisibileWebElement(d, "budgetStep3DoneBtn_TRANSENH", "Transaction", null);
	}

	public void clickOnFlexibleSpending(String categoryName) {
		String xpath = getVisibileElementXPath(d, "Transaction", null, "genFlexibleSpendingList_TRANSENH");
		xpath = xpath.replaceAll("dummyText", categoryName);
		click(d.findElement(By.xpath(xpath)));
	}

	public WebElement sfcStartReviewBtn() {
		return getVisibileWebElement(d, "SFCStartReviewBtn_TRANSENH", "Transaction", null);
	}

	public WebElement viewMoreDetailsMobile() {
		return getVisibileWebElement(d, "viewmoredetailsmobile", "Transaction", null);
	}

	public boolean isViewMoreDetailsMobile() {
		return PageParser.isElementPresent("viewmoredetailsmobile", "Transaction", null);
	}

	public WebElement sfcStep1AccountToSelect() {
		return getVisibileWebElement(d, "SFCStep1AccountToSelect_TRANSENH", "Transaction", null);
	}

	public WebElement sfcNextBtn() {
		return getVisibileWebElement(d, "SFCNextBtn1_TRANSENH", "Transaction", null);
	}

	public WebElement sfcNextBtnmobile() {
		return getVisibileWebElement(d, "sfcNextBtnmobile", "Transaction", null);
	}

	public boolean issfcNextBtnmobile() {
		return PageParser.isElementPresent("sfcNextBtnmobile", "Transaction", null);
	}

	public WebElement sfcEventsTab() {
		return getVisibileWebElement(d, "SFCEventsTab_TRANSENH", "Transaction", null);
	}

	public WebElement sfcEventsTabMobile() {
		return getVisibileWebElement(d, "sfcEventsTabMobile", "Transaction", null);
	}

	public boolean issfcEventsTabMobile() {
		return PageParser.isElementPresent("sfcEventsTabMobile", "Transaction", null);
	}

	public WebElement sfcAccountsDropDown() {
		return getVisibileWebElement(d, "SFCAccountsDropDown_TRANSENH", "Transaction", null);
	}

	public WebElement sfcAccountToBeSelected() {
		return getVisibileWebElement(d, "SFCAccountToBeSelected_TRANSENH", "Transaction", null);
	}

	public WebElement sfcAccountToBeSelectedChkBox() {
		return getVisibileWebElement(d, "SFCAccountToBeSelectedChkBox_TRANSENH", "Transaction", null);
	}

	public WebElement sfcAppyButton() {
		return getVisibileWebElement(d, "SFCAppyButton_TRANSENH", "Transaction", null);
	}

	public WebElement sfcCancelButton() {
		return getVisibileWebElement(d, "SFCCancelButton_TRANSENH", "Transaction", null);
	}

	public WebElement sfcEventToBeVerified() {
		return getVisibileWebElement(d, "SFCEventToBeVerified_TRANSENH", "Transaction", null);
	}

	public WebElement finCheckBillsIndicator() {
		return getVisibileWebElement(d, "FinCheckBillsIndicator_TRANSENH", "Transaction", null);
	}

	public WebElement finCheckMissedBill() {
		return getVisibileWebElement(d, "FinCheckMissedBill_TRANSENH", "Transaction", null);
	}
}