/**
* Copyright (c) 2019 Yodlee Inc. All Rights Reserved.  
*
* This software is the confidential and proprietary information of
* Yodlee, Inc. Use is subject to license terms.
*
* @author sbhat1
*/
package com.omni.pfm.pages.FincheckV2;

import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.omni.pfm.PageProcessor.PageParser;
import com.omni.pfm.utility.SeleniumUtil;

public class FincheckV2_Settings_Loc {
	WebDriver d;
	String pageName, frameName;

	public FincheckV2_Settings_Loc(WebDriver d) {
		this.d = d;
		pageName = "FincheckV2";
		frameName = null;
	}

	public WebElement getSettingsLink() {
		return SeleniumUtil.getWebElement(d, "finV2-settings-link-dashboard", pageName, frameName);
	}

	public WebElement getSettingsPageHeader() {
		return SeleniumUtil.getWebElement(d, "settings-header-v2", pageName, frameName);
	}

	public WebElement getAvailableAccountHeader() {
		return SeleniumUtil.getWebElement(d, "finV2-settngs-available-acc-header", pageName, frameName);
	}

	public List<WebElement> getAvailableAccTypes() {
		return SeleniumUtil.getWebElements(d, "finV2-settings-available-acc-det", pageName, frameName);
	}

	public WebElement getEditAccountButton() {
		return SeleniumUtil.getWebElement(d, "finV2-settings-edit-account-button", pageName, frameName);
	}

	public WebElement getAccountSettingsPageHeader() {
		return SeleniumUtil.getWebElement(d, "finV2-settings-accounts-settings-header", pageName, frameName);
	}
	
	public WebElement getBackLink() {
		return SeleniumUtil.getWebElement(d, "finV2-settings-back-to-fi-link", pageName, frameName);
	}
	
	public boolean isfinV2_mobile_settings_backicon_Present()
	{
		return PageParser.isElementPresent("finV2-mobile-settings-mobilebackicon", pageName, null);
	}
	

	   public WebElement mobileBacklink() {

		return SeleniumUtil.getWebElement(d, "finV2-mobile-settings-mobilebackicon", pageName, frameName);
		
		
	   }
		
	

}
