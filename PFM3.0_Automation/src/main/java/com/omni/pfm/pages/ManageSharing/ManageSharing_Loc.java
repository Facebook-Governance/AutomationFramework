/*******************************************************************************
 * Copyright (c) 2018 Yodlee Inc. All Rights Reserved.
 * 
 * This software is the confidential and proprietary information of
 * Yodlee, Inc. Use is subject to license terms.
 * 
 * @author
 ******************************************************************************/
package com.omni.pfm.pages.ManageSharing;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.omni.pfm.utility.SeleniumUtil;
/**
 * Copyright (c) 2018 Yodlee Inc. All Rights Reserved.
 *
 * This software is the confidential and proprietary information of Yodlee, Inc.
 * Use is subject to license terms.
 *
 * @author Shivaprasad
 */
public class ManageSharing_Loc {
	public WebDriver d;
	String pageName, frameName;

	public ManageSharing_Loc(WebDriver d) {
		this.d = d;
		frameName = null;
	}

	public WebElement finappHeader() {
		return SeleniumUtil.getWebElement(d, "finappHeader", "ManageSharing", frameName);
	}

	public List<WebElement> sidebar_lbl_Values() {
		return SeleniumUtil.getWebElements(d, "sidebar_lbl_Values", "ManageSharing", frameName);
	}

	public List<WebElement> sharingTypes() {
		return SeleniumUtil.getWebElements(d, "sharingTypes", "ManageSharing", frameName);
	}

	public List<WebElement> dropdownHeader() {
		return SeleniumUtil.getWebElements(d, "dropdownHeader", "ManageSharing", frameName);
	}

	public List<WebElement> dropdownValues() {
		return SeleniumUtil.getWebElements(d, "dropdownValues", "ManageSharing", frameName);
	}

	public WebElement sharedByMe() {
		return SeleniumUtil.getWebElement(d, "sharedByMe_Container", "ManageSharing", frameName);
	}

	public WebElement sharedToMe() {
		return SeleniumUtil.getWebElement(d, "sharedToMe_Container", "ManageSharing", frameName);
	}

	public WebElement getSaveChanges() {
		return SeleniumUtil.getWebElement(d, "saveChanges_ManageSharing", "ManageSharing", frameName);
	}

	public List<WebElement> getDropdownValue(int var_i) {

		String webElement = SeleniumUtil.getVisibileElementXPath(d, "ManageSharing", frameName, "dropdownValuesList");
		webElement = webElement.replaceAll("var_i", Integer.toString(var_i));

		return d.findElements(By.xpath(webElement));

	}
	
	public List<WebElement> getDropdownValue1(int var_i) {

		String webElement = SeleniumUtil.getVisibileElementXPath(d, "ManageSharing", frameName, "popupValues_ACC");
		webElement = webElement.replaceAll("var_i", Integer.toString(var_i));

		return d.findElements(By.xpath(webElement));

	}
	
	public WebElement closeButton_P() {
		return SeleniumUtil.getWebElement(d, "closeButton_P", "ManageSharing", frameName);
	}
	
	public List<WebElement> moreBtn_Accounts() {
		return SeleniumUtil.getWebElements(d, "moreBtn_Accounts", "ManageSharing", frameName);
	}

	public List<WebElement> sharingAccList() {
		return SeleniumUtil.getWebElements(d, "sharingAccList", "ManageSharing", frameName);
	}
	
	public List<WebElement> accountsDagBank() {
		return SeleniumUtil.getWebElements(d, "accountsDagBank", "ManageSharing", frameName);
	}
	
}
