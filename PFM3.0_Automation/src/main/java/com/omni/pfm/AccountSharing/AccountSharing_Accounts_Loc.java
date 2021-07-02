/*******************************************************************************
 * Copyright (c) 2018 Yodlee Inc. All Rights Reserved.
 * 
 * This software is the confidential and proprietary information of
 * Yodlee, Inc. Use is subject to license terms.
 * 
 * @author
 ******************************************************************************/
package com.omni.pfm.AccountSharing;

import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.omni.pfm.utility.SeleniumUtil;

public class AccountSharing_Accounts_Loc {

	WebDriver d;
	String frameName, pageName;

	public AccountSharing_Accounts_Loc(WebDriver d) {
		this.d = d;
		pageName = "AccountsPage";
		frameName = null;
	}

	public WebElement finappHeader() {
		// Finapp Header - Accounts
		return SeleniumUtil.getVisibileWebElement(d, "appHeader_Acc", pageName, frameName);
	}

	public List<WebElement> getSidebarValues() {
		return SeleniumUtil.getWebElements(d, "sidebar_values_accounts", pageName, frameName);
	}

	public List<WebElement> getAggregatedAccountLabels() {
		return SeleniumUtil.getWebElements(d, "account_label_account", pageName, frameName);
	}

	public List<WebElement> getMoreButtonAccounts() {
		return SeleniumUtil.getWebElements(d, "accounts_btn_More", pageName, frameName);
	}

	public List<WebElement> getShareWithAdvisorValue() {
		return SeleniumUtil.getWebElements(d, "sharewithadvisor_accounts", pageName, frameName);
	}

	public WebElement popupHeader() {
		return SeleniumUtil.getVisibileWebElement(d, "manage_sharing_popup_header", pageName, frameName);
	}

	public WebElement getAccountDropdown() {
		return SeleniumUtil.getVisibileWebElement(d, "account_dropdown_accounts", pageName, frameName);
	}

	public List<WebElement> getPermissionValues() {
		return SeleniumUtil.getWebElements(d, "sharing_permissions", pageName, frameName);
	}

	public WebElement getShareButton() {
		return SeleniumUtil.getVisibileWebElement(d, "share_button_accounts", pageName, frameName);
	}
	
	public List<WebElement> getSharedByMeIcon() {
		return SeleniumUtil.getWebElements(d, "shared_by_me_icon_accounts", pageName, frameName);
	}
	
	public List<WebElement> getAdvisorMoreValues() {
		return SeleniumUtil.getWebElements(d, "more_values_advisor_account", pageName, frameName);
	}
	
	public List<WebElement> getSharedWithMeIcon() {
		return SeleniumUtil.getWebElements(d, "shared_with_me_icon_accounts", pageName, frameName);
	}
}
