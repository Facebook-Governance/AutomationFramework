/**
* Copyright (c) 2018 Yodlee Inc. All Rights Reserved.
*
* This software is the confidential and proprietary information of
* Yodlee, Inc. Use is subject to license terms.
*
* @author Renuka K
*/
package com.omni.pfm.pages.TransactionEnhancement1;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

import com.omni.pfm.PageProcessor.PageParser;
import com.omni.pfm.utility.PropsUtil;
import com.omni.pfm.utility.SeleniumUtil;

public class Transaction_Layout_Loc extends SeleniumUtil {
	public WebDriver d = null;
	static String pageName = "Transaction"; // Page Name
	static String frameName = null;
	public static final By layoutNote = getByObject(pageName, frameName, "LayoutNoteIcon");
	public static final By projectedHeader = getByObject(pageName, frameName, "proheader");
	public static final By pendingTransaction = getByObject(pageName, frameName, "pending");

	// pending
	public Transaction_Layout_Loc(WebDriver d) {
		this.d = d;
	}

	public WebElement projectedHeader() {
		return getVisibileWebElement(d, "proheader", pageName, frameName);
	}

	public List<WebElement> projectedHeaderList() {
		return getWebElements(d, "proheader", pageName, frameName);
	}

	public WebElement pendingPostedHeader() {
		return getVisibileWebElement(d, "pendingheader", pageName, frameName);
	}

	public WebElement PostedTransactiondate() {
		return getVisibileWebElement(d, "PostedtransDate", pageName, frameName);
	}

	public List<WebElement> dateGroupOfPostedtransaction(String date) {
		return d.findElements(By.xpath(
				"//div[@class='transaction-type-wrap']/div[2]/div[@class='transaction-date-wrap']/div/div[contains(text(),'"
						+ date + "')]/following-sibling::div[position()=2]/div/div[1]"));
	}

	public List<WebElement> dateGroupOfProjectedtransaction(String date) {
		return d.findElements(By.xpath(
				"//div[@class='transaction-type-wrap']/div[1]/div[@class='transaction-date-wrap']/div/div[contains(text(),'"
						+ date + "')]/following-sibling::div[position()=2]/div/div[1]"));
	}

	public WebElement projectedTransactiondate() {
		return getVisibileWebElement(d, "projectedtransDate", pageName, frameName);
	}

	public List<WebElement> projected30dayTran() {
		return getWebElements(d, "pro30day", pageName, frameName);
	}

	public List<WebElement> allPostedDate() {
		return getWebElements(d, "allposteddate", pageName, frameName);
	}

	public WebElement monthFilter() {
		return getVisibileWebElement(d, "monthfilt", pageName, frameName);
	}

	public WebElement linkAccount() {
		return getVisibileWebElement(d, "linkacc", pageName, frameName);
	}

	public List<WebElement> monthFileterList() {
		return getWebElements(d, "mothfiletlist", pageName, frameName);
	}

	public WebElement noTransactionData() {
		return getVisibileWebElement(d, "nodate", pageName, frameName);
	}

	public WebElement fromDate() {
		return getVisibileWebElement(d, "fromdate", pageName, frameName);
	}

	public WebElement toDate() {
		return getVisibileWebElement(d, "todate", pageName, frameName);
	}

	public WebElement updateFilter() {
		return getVisibileWebElement(d, "upt", pageName, frameName);
	}

	public WebElement splitTransaction() {
		return getVisibileWebElement(d, "split", pageName, frameName);
	}

	public List<WebElement> pendingTransaction() {
		return getWebElements(d, "pending", pageName, frameName);
	}

	public WebElement customeDate() {
		return getVisibileWebElement(d, "cust", pageName, frameName);
	}

	public List<WebElement> listOfCustomeFilter() {
		return getWebElements(d, "datefilter", pageName, frameName);
	}

	public WebElement fromDateFilter() {
		return getVisibileWebElement(d, "frombox_Tx", pageName, frameName);
	}

	public WebElement toDateFilter() {

		return getVisibileWebElement(d, "to", pageName, frameName);
	}

	public WebElement upDateFilter() {
		return getVisibileWebElement(d, "updt", pageName, frameName);
	}

	public WebElement dashboard() {
		return getVisibileWebElement(d, "board", pageName, frameName);
	}

	public List<WebElement> LayoutNoteIcon() {
		return getWebElements(d, "LayoutNoteIcon", pageName, frameName);
	}

	public List<WebElement> LayoutSplitIcon() {
		return getWebElements(d, "LayoutSplitIcon", pageName, frameName);
	}

	public WebElement LayoutTagIcon() {
		return getVisibileWebElement(d, "LayoutTagIcon", pageName, frameName);
	}

	public WebElement LayoutAttachmentIcon() {
		return getVisibileWebElement(d, "LayoutAttachmentIcon", pageName, frameName);
	}

	public Date DateInMMMMFormateParse(String date) throws ParseException {

		SimpleDateFormat formatter = new SimpleDateFormat("MMMM dd, yyyy");

		return formatter.parse(date);
	}

	public Date DateFormateParse(String date) throws ParseException {
		SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
		return formatter.parse(date);
	}

	public void serachTransaction(String fromDate, String Todate) {
		click(getByObject(pageName, null, "cust"));
		waitUntilSpinnerWheelIsInvisible();
		waitUntilElementIsVisible(getByObject(pageName, null, "datefilter"), 60);
		click(listOfCustomeFilter().get(7));
		click(getByObject(pageName, null, "frombox_Tx"));
		fromDateFilter().clear();
		fromDateFilter().sendKeys(fromDate);
		click(getByObject(pageName, null, "to"));
		toDateFilter().clear();
		toDateFilter().sendKeys(Todate);
		toDateFilter().sendKeys(Keys.TAB);
		click(upDateFilter());
		waitUntilSpinnerWheelIsInvisible();
	}

	public WebElement MobileFilterIcon() {
		return getWebElement(d, "MobileFilterIcon", "Transaction", null);
	}

	public WebElement MobileResetButton() {
		return getWebElement(d, "MobileResetButton", "Transaction", null);
	}

	public WebElement MobileSerachDoneButton() {
		return getWebElement(d, "MobileSerachDoneButton", "Transaction", null);
	}

	public WebElement MobileApplyButton() {
		return getWebElement(d, "MobileApplyButton", "Transaction", null);
	}

	public boolean isMobileApplyButtonPresent() {
		return PageParser.isElementPresent("MobileApplyButton", "Transaction", null);
	}

	public boolean isMobileFilterIconPresent() {
		return PageParser.isElementPresent("MobileFilterIcon", "Transaction", null);
	}

	public void serachTransactionMobile(String fromDate, String Todate) throws InterruptedException {
		click(MobileFilterIcon());
		try {
			click(MobileResetButton());
		} catch (Exception e) {
			System.out.println(e);
		}
		click(customeDate());
		waitUntilSpinnerWheelIsInvisible();
		click(listOfCustomeFilter().get(7));
		click(fromDateFilter());
		fromDateFilter().clear();
		fromDateFilter().sendKeys(fromDate);
		click(toDateFilter());
		toDateFilter().clear();
		toDateFilter().sendKeys(Todate);
		click(MobileSerachDoneButton());
		waitUntilSpinnerWheelIsInvisible();
		click(MobileApplyButton());
		waitUntilSpinnerWheelIsInvisible();
	}

	public Date getDateFormate(int days) {
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DAY_OF_MONTH, days);
		System.out.println(cal.getTime());
		return cal.getTime();
	}

	public void expandProjectedTransactions() {
		By projectedTransactionHeader = getByObject("Transaction", null, "projectedTransactionsHeader");
		click(projectedTransactionHeader);
		waitFor(1);
		if (!Objects.isNull(getAttribute(projectedTransactionHeader, "aria-expanded"))) {
			if (getAttribute(projectedTransactionHeader, "aria-expanded").equals("false")) {
				click(projectedTransactionHeader);
			}
		}
	}

}
