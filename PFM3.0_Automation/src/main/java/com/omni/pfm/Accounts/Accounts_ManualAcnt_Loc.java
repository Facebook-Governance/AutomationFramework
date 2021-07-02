/**
* Copyright (c) 2018 Yodlee Inc. All Rights Reserved.
*
* This software is the confidential and proprietary information of
* Yodlee, Inc. Use is subject to license terms.
*
* @author sakshi Jain
*/
package com.omni.pfm.Accounts;

import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.omni.pfm.utility.SeleniumUtil;

public class Accounts_ManualAcnt_Loc {
	Logger logger=LoggerFactory.getLogger(Accounts_ManualAcnt_Loc.class);
	public WebDriver d=null;
	static String pageName = "AccountsPage";
	static String frameName=null;
	
	public Accounts_ManualAcnt_Loc(WebDriver d) {
    this.d=d;
	}
	
	public List<WebElement> manualContainerHeader() {
		return SeleniumUtil.getWebElements(d, "manualContainer", pageName, frameName);
	}
	public WebElement RefreshAll() {
		return SeleniumUtil.getVisibileWebElement(d, "RefreshText", pageName, null);
	}
	public List<WebElement> accountLevelRefresh() {
		return SeleniumUtil.getWebElements(d, "accountLevelRefresh", pageName, null);
	}
	public WebElement ManualText() {
		return SeleniumUtil.getVisibileWebElement(d, "manualHeading", pageName, null);
	}
	public WebElement ManualTab() {
		return SeleniumUtil.getVisibileWebElement(d, "ManualTab", pageName, null);
	}
	public List<WebElement> EditCredentials() {
		return SeleniumUtil.getWebElements(d, "EditCredentials", "AccountSettings", null);
	}
	
	
	
	

	
}
