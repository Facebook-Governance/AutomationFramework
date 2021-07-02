/**
* Copyright (c) 2018 Yodlee Inc. All Rights Reserved.
*
* This software is the confidential and proprietary information of
* Yodlee, Inc. Use is subject to license terms.
*
* @author Renuka K
*/
package com.omni.pfm.pages.TransactionEnhancement1;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.omni.pfm.PageProcessor.PageParser;
import com.omni.pfm.utility.SeleniumUtil;

public class AddToCalendar_Transaction_Loc extends SeleniumUtil {

	public static WebDriver d = null;
	WebDriverWait wait;
	public static final By frequencyDropdown = getByObject("Transaction", null, "Frequency_ATC_Dropdown");
	public static final By addCalendarLink = getByObject("Transaction", null, "addcalLink_ATC");
	public static final By popupHeaders = getByObject("Transaction", null, "PopupHeader_ATC");

	public AddToCalendar_Transaction_Loc(WebDriver d) {
		this.d = d;
		wait = new WebDriverWait(d, 60);
	}

	public List<WebElement> PendingStranctionList() {
		return getWebElements(d, "PendingStranctionList_ATC", "Transaction", null);
	}

	public WebElement addcalLink() {
		return getVisibileWebElement(d, "addcalLink_ATC", "Transaction", null);
	}

	public List<WebElement> addcalLinkList() {
		return getWebElements(d, "addcalLink_ATC", "Transaction", null);
	}

	public WebElement addcalIcon() {
		return getVisibileWebElement(d, "addcalIcon_ATC", "Transaction", null);
	}

	public WebElement PopupHeader() {
		return getVisibileWebElement(d, "PopupHeader_ATC", "Transaction", null);
	}

	public List<WebElement> MobilePopupHeader_ATC() {
		return getWebElements(d, "MobilePopupHeader_ATC", "Transaction", null);
	}

	public boolean isMobilePopupHeaderPresent() {
		return PageParser.isElementPresent("MobilePopupHeader_ATC", "Transaction", null);
	}

	public List<WebElement> PopupHeaderList() {
		return getWebElements(d, "PopupHeader_ATC", "Transaction", null);
	}

	public WebElement InfoMessage() {
		return getVisibileWebElement(d, "InfoMessage_ATC", "Transaction", null);
	}

	public List<WebElement> ProjectedtranctionList() {
		return getWebElements(d, "ProjectedtranctionList_ATC", "Transaction", null);
	}

	public WebElement close() {
		return getVisibileWebElement(d, "close_ATC", "Transaction", null);
	}

	public WebElement amountField() {
		return getVisibileWebElement(d, "amountField_ATC", "Transaction", null);
	}

	public WebElement description() {
		return getVisibileWebElement(d, "description_ATC", "Transaction", null);
	}

	public WebElement bankName() {
		return getVisibileWebElement(d, "bankName_ATC", "Transaction", null);
	}

	public WebElement Frequency() {
		return getVisibileWebElement(d, "Frequency_ATC", "Transaction", null);
	}

	public WebElement ScheduledDate() {
		return getVisibileWebElement(d, "ScheduledDate_ATC", "Transaction", null);
	}

	public WebElement endDate() {
		return getVisibileWebElement(d, "endDate_ATC", "Transaction", null);
	}

	public List<WebElement> frequencyList() {
		return getWebElements(d, "frequencyList_ATC", "Transaction", null);
	}

	public List<WebElement> tickfrequencyList_ATC() {
		return getWebElements(d, "tickfrequencyList_ATC", "Transaction", null);
	}

	public WebElement addCal() {
		return getVisibileWebElement(d, "addCal_ATC", "Transaction", null);
	}

	public WebElement cancel() {
		return getVisibileWebElement(d, "cancel_ATC", "Transaction", null);
	}

	public WebElement amountErorr() {
		return getVisibileWebElement(d, "amountErorr", "Transaction", null);
	}

	public WebElement descError() {
		return getVisibileWebElement(d, "descError_ATC", "Transaction", null);
	}

	public WebElement scheduleErorr() {
		return getVisibileWebElement(d, "scheduleErorr_ATC", "Transaction", null);
	}

	public WebElement endDateError() {
		return getWebElement(d, "endDateError_ATC", "Transaction", null);
	}

	public WebElement tagErorr() {
		return getVisibileWebElement(d, "tagErorr_ATC", "Transaction", null);
	}

	public WebElement ShowMore() {
		return getVisibileWebElement(d, "ShowMore_ATC", "Transaction", null);
	}

	public WebElement ShowLessHide_ATC() {
		return getWebElement(d, "ShowLessHide_ATC", "Transaction", null);
	}

	public WebElement NoteField() {
		return getVisibileWebElement(d, "NoteField_ATC", "Transaction", null);
	}

	public WebElement checkField() {
		return getWebElement(d, "checkField_ATC", "Transaction", null);
	}

	public WebElement sucessMessage() {
		return getVisibileWebElement(d, "sucessMessage_ATC", "Transaction", null);
	}

	public List<WebElement> ProjectedExapdIconList() {
		return getWebElements(d, "ProjectedExapdIcon", "Transaction", null);
	}

	public String getAtributeVAlue(String atributevalue) {
		JavascriptExecutor e = (JavascriptExecutor) d;

		return (String) e.executeScript(String.format("return $('#%s').val();", atributevalue));

	}

	public String DateFormate(int date) {

		SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
		Calendar c = Calendar.getInstance();
		c.add(Calendar.DATE, date);
		System.out.println(formatter.format(c.getTime()));
		return formatter.format(c.getTime());
	}

	public boolean isElementPresent(WebElement elements) {
		if (elements == null) {
			return true;
		} else {
			return false;
		}

	}

	public void clickPostedTxn(int txnRow) {
		click(PendingStranctionList().get(txnRow));
		wait.until(ExpectedConditions.visibilityOf(addcalLink()));
	}

	public void clickAddToCal() {
		click(addcalLink());
		wait.until(ExpectedConditions.visibilityOf(PopupHeader()));
	}

	public boolean addToCalProTxn() {
		boolean expected = false;
		By projectedExpandIcon = getByObject("Transaction", null, "ProjectedExapdIcon");
		By calendar = getByObject("Transaction", null, "addcalLink_ATC");
		if (getElementCount(projectedExpandIcon) == 0) {
			expected = false;
		} else {
			click(ProjectedExapdIconList().get(0));
			wait.until(ExpectedConditions.visibilityOf(ProjectedtranctionList().get(0)));
			click(ProjectedtranctionList().get(0));
			if (getElementCount(calendar) == 0) {
				expected = true;
			}
		}
		return expected;
	}

	public void changeFrequency(int frequency) {
		openFrequencyDropDown();
		wait.until(ExpectedConditions.visibilityOf(frequencyList().get(frequency)));
		click(frequencyList().get(frequency));
		waitUntilSpinnerWheelIsInvisible();
		waitFor(1);
	}

	public void openFrequencyDropDown() {
		if (getAttribute(frequencyDropdown, "aria-expanded").equals("false"))
			click(Frequency());
	}

	public void clickFrequency() {
		click(Frequency());
	}

	public void clickClose() {
		click(close());
	}

	public void allFieldErrorMessage() {

		amountField().clear();
		description().clear();
		ScheduledDate().clear();
		endDate().clear();
		click(addCal());
		waitForPageToLoad(3000);
	}

	public void clickCancel() {
		click(cancel());

	}

	public void enterAmountFieldValue(String amount) {
		amountField().clear();
		amountField().sendKeys(amount);
		amountField().sendKeys(Keys.TAB);
	}

	public void enterDescriptionFieldValue(String description) {
		description().clear();
		waitForPageToLoad();
		description().sendKeys(description);
		description().sendKeys(Keys.TAB);
	}

	public boolean despFieldMaxVal() {
		boolean maxValue = false;
		if (description().getAttribute("value").length() == 130) {
			maxValue = true;
		}
		return maxValue;

	}

	public void enterScheduleDateFieldValue(String date) {
		ScheduledDate().clear();
		ScheduledDate().sendKeys(date);
		ScheduledDate().sendKeys(Keys.TAB);
	}

	public void enterEndDateFieldValue(String date) {
		endDate().clear();
		endDate().sendKeys(date);
		endDate().sendKeys(Keys.TAB);
	}

	public void clickShowMore() {
		click(ShowMore());
	}

	public void enterNoteFieldValue(String note) {
		NoteField().clear();
		NoteField().sendKeys(note);
	}

	public boolean noteFieldMAxValidation() {
		boolean actualnote = false;
		if (NoteField().getAttribute("value").length() == 150) {
			actualnote = true;
		}
		return actualnote;
	}

	public void enterCheckFieldValue(String check) {
		checkField().clear();
		checkField().sendKeys(check);

	}

	public boolean checkFieldMAxValidation() {
		boolean actualCheck = false;
		if (checkField().getAttribute("value").length() == 20) {
			actualCheck = true;
		}
		return actualCheck;
	}

}
