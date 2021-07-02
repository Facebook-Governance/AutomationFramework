/**
* Copyright (c) 2019 Yodlee Inc. All Rights Reserved.   
*
* This software is the confidential and proprietary information of
* Yodlee, Inc. Use is subject to license terms.
*
* @author sbhat1
*/
package com.omni.pfm.pages.OpenBanking;

import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.omni.pfm.utility.SeleniumUtil;
import com.omni.pfm.utility.WaitUtil;

public class OpenBanking_Loc {
	WebDriver d;
	String frameName, pageName;

	public OpenBanking_Loc(WebDriver d) {
		this.d = d;
		frameName = null;
		pageName = "OB";
	}

	public List<WebElement> getOBIndicator() {
		return WaitUtil.getVisibleWebElements(d, "ob-indicator", pageName, frameName);
	}

	public List<WebElement> getAccountsLabel() {
		return WaitUtil.getVisibleWebElements(d, "ob-label-account", pageName, frameName);
	}

	public WebElement getAccountTypeSideBar() {
		return WaitUtil.getVisibleWebElement(d, "account-type-sidebar", pageName, frameName);
	}

	public WebElement getFinappHeader() {
		return WaitUtil.getVisibleWebElement(d, "ob-finapp-header", pageName, frameName);
	}

	public WebElement getAccountInManageConsent() {
		return WaitUtil.getVisibleWebElement(d, "mc-account", pageName, frameName);
	}

	public WebElement getBacLink() {
		return WaitUtil.getVisibleWebElement(d, "mc-back-link", pageName, frameName);
	}

	public WebElement getRenewButton() {
		return WaitUtil.getVisibleWebElement(d, "renew-button", pageName, frameName);
	}

	public WebElement getManageButton() {
		return WaitUtil.getVisibleWebElement(d, "manage-button", pageName, frameName);
	}

	public WebElement getDeleteButton() {
		return WaitUtil.getVisibleWebElement(d, "delete-button", pageName, frameName);
	}

	public WebElement getTakeActionLink() {
		return WaitUtil.getVisibleWebElement(d, "take-action", pageName, frameName);
	}

	public WebElement getRenewLink() {
		return WaitUtil.getVisibleWebElement(d, "renew-link", pageName, frameName);
	}

	public WebElement getDeleteLink() {
		return WaitUtil.getVisibleWebElement(d, "delete-link", pageName, frameName);
	}

	public List<WebElement> getSettingsIcons() {
		return WaitUtil.getVisibleWebElements(d, "account-settings-icon", pageName, frameName);
	}

	public WebElement getDeleteAccountLink() {
		return WaitUtil.getVisibleWebElement(d, "delete-account-link", pageName, frameName);
	}

	public WebElement getCloseAccountLink() {
		return WaitUtil.getVisibleWebElement(d, "close-account-link", pageName, frameName);
	}

	public WebElement getDeleteSitePopup() {
		SeleniumUtil.waitForPageToLoad(2000);
		return WaitUtil.getVisibleWebElement(d, "delete-site-popup", pageName, frameName);
	}

	public WebElement getWarningMessage() {
		return WaitUtil.getVisibleWebElement(d, "warning-msg", pageName, frameName);
	}

	public WebElement getConfirmCheckbox() {
		return WaitUtil.getVisibleWebElement(d, "delete-confirm-checkbox", pageName, frameName);
	}

	public WebElement getDeleteCheckboxLabel() {
		return WaitUtil.getVisibleWebElement(d, "delete-checkbox-label", pageName, frameName);
	}

	public WebElement getPopupDeleteButton() {
		SeleniumUtil.waitForPageToLoad(1000);
		return WaitUtil.getVisibleWebElement(d, "popup-delete-button", pageName, frameName);
	}

	public WebElement getNoDataScreen() {
		return WaitUtil.getVisibleWebElement(d, "no-data-screen", pageName, frameName);
	}

}
