/**
* Copyright (c) 2018 Yodlee Inc. All Rights Reserved.
*
* This software is the confidential and proprietary information of
* Yodlee, Inc. Use is subject to license terms.
*
* @author Renuka K
*/
package com.omni.pfm.pages.TransactionEnhancement1;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.omni.pfm.utility.SeleniumUtil;

public class Transaction_MarkAsPaid_Loc extends SeleniumUtil{
	static Logger logger = LoggerFactory.getLogger(Transaction_Filter_Loc.class);
	public WebDriver d = null;
	static String pageName = "Transaction";
	static String frameName = null;
	public static final By markAsPaidPopup = getByObject(pageName, frameName, "MarkAsPaidPopUp");
	public static final By markAsPaidButton = getByObject(pageName, frameName, "MarkAsPaid");
	//"MarkAsPaidPopUp", pageName, frameName

	public Transaction_MarkAsPaid_Loc(WebDriver d) {
		this.d = d;
	}

	public List<WebElement> MarkAsPaid() {
		return getWebElements(d, "MarkAsPaid", pageName, frameName);
	}

	public List<WebElement> TranOverDue() {
		return getWebElements(d, "TranOverDue", pageName, frameName);
	}

	public List<WebElement> MarkAsPaidPopUp() {
		return getWebElements(d, "MarkAsPaidPopUp", pageName, frameName);
	}

	public WebElement MarkAsPaidPopUpMessage() {
		return getWebElement(d, "MarkAsPaidPopUpMessage", pageName, frameName);
	}

	public WebElement MarkAsPaidPopUpCheckBox() {
		return getWebElement(d, "MarkAMarkAsPaidPopUpCheckBoxsPaid", pageName, frameName);
	}

	public WebElement MarkAsPaidPopUpCheckBoxMesg() {
		return getWebElement(d, "MarkAsPaidPopUpCheckBoxMesg", pageName, frameName);
	}

	public WebElement MarkAsPaidPopUpcancel() {
		return getWebElement(d, "MarkAsPaidPopUpcancel", pageName, frameName);
	}

	public WebElement MarkAsPaidPopUpPaid() {
		return getWebElement(d, "MarkAsPaidPopUpPaid", pageName, frameName);
	}

	public WebElement MarkAsPaidPopUpClose() {
		return getWebElement(d, "MarkAsPaidPopUpClose", pageName, frameName);
	}

}
