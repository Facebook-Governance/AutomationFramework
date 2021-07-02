/*******************************************************************************
 * Copyright (c) 2018 Yodlee Inc. All Rights Reserved.
 * 
 * This software is the confidential and proprietary information of
 * Yodlee, Inc. Use is subject to license terms.
 * 
 * @author
 ******************************************************************************/
package com.omni.pfm.pages.SFG;

import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.omni.pfm.utility.SeleniumUtil;

public class SFG_Alerts_Loc {
	Logger logger=LoggerFactory.getLogger(SFG_Alerts_Loc.class);
	public WebDriver d=null;
	static String frameName = null;

	public SFG_Alerts_Loc(WebDriver d) {
		this.d=d;
	}
	public WebElement alertSetting() {
		return SeleniumUtil.getWebElement(d, "alertSetting", "SFG", null);
	}
	public WebElement alertSettingIcon() {
		return SeleniumUtil.getWebElement(d, "alertSettingIcon", "SFG", null);
	}
	public WebElement alertSettingHeader() {
		return SeleniumUtil.getWebElement(d, "alertSettingHeader", "SFG", null);
	}
	public List<WebElement> alertSettingGoalNameHeader() {
		return SeleniumUtil.getWebElements(d, "alertSettingGoalNameHeader", "SFG", null);
	}
	public List<WebElement> containerList() {
		return SeleniumUtil.getWebElements(d, "containerList", "SFG", null);
	}	
	public List<WebElement> alertSettingGoalsDropdown() {
		return SeleniumUtil.getWebElements(d, "alertSettingGoalsDropdown", "SFG", null);
	}
	public List<WebElement> AlertPOPupLabels() {
		return SeleniumUtil.getWebElements(d, "AlertPOPupLabels", "SFG", null);
	}
	public List<WebElement> AlertPOPupSubLabels() {
		return SeleniumUtil.getWebElements(d, "AlertPOPupSubLabels", "SFG", null);
	}
	public WebElement AlertPOPupSaveBtn() {
		return SeleniumUtil.getWebElement(d, "AlertPOPupSaveBtn", "SFG", null);
	}
	public List<WebElement> AlertsToggleButtons() {
		return SeleniumUtil.getWebElements(d, "AlertsToggleButtons", "SFG", null);
	}
	public WebElement categoriesLevelBackBtn() {
		return SeleniumUtil.getWebElement(d, "categoriesLevelBackBtn", "SFG", null);
	}
	

	public WebElement verifyClosePopUp() 
	{
		return SeleniumUtil.getWebElement(d, "closePopUp", "SFG", null);
	}
	
	public WebElement verifyBackButtonForMobile() 
	{
		return SeleniumUtil.getWebElement(d, "backButtonForMobile", "SFG", null);
	}
	
	
	


}
