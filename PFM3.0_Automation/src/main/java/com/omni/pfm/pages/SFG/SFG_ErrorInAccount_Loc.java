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

public class SFG_ErrorInAccount_Loc {
	Logger logger=LoggerFactory.getLogger(SFG_ErrorInAccount_Loc.class);
	public WebDriver d=null;
	static String frameName = null;

	public SFG_ErrorInAccount_Loc(WebDriver d) {
		this.d=d;
	}
	public List<WebElement> SettingContainerList() {
		return SeleniumUtil.getWebElements(d, "SettingContainerList", "SFG", null);
	}
	public List<WebElement> accountSettingIcon() {
		return SeleniumUtil.getWebElements(d, "accountSettingIcon", "SFG", null);
	}
	public WebElement closeActnOption() {
		return SeleniumUtil.getWebElement(d, "closeActnOption", "SFG", null);
	}
	public WebElement closeAccountButton() {
		return SeleniumUtil.getWebElement(d, "closeAccountButton", "SFG", null);
	}
	public List<WebElement> goalInprogressErrorMsg() {
		return SeleniumUtil.getWebElements(d, "goalInprogressErrorMsg", "SFG", null);
	}
	public WebElement createGoalTab() {
		return SeleniumUtil.getVisibileWebElement(d, "createGoalTab", "SFG", null);
	}
	public List<WebElement> allInprogressGoal()
	{
		return SeleniumUtil.getWebElements(d, "InProgressGoalList", "SFG", null);
	}
	public WebElement editgoalbutton() {
		return SeleniumUtil.getWebElement(d, "editgoalbutton", "SFG", null);
	}
	public WebElement oneTimeFundingditButton() {
		return SeleniumUtil.getWebElement(d, "oneTimeFundingditButton", "SFG", null);
	}
	public WebElement amountInputBox() {
		return SeleniumUtil.getWebElement(d, "amountInputBox", "SFG", null);
	}
	public WebElement EditPopupDoneButton() {
		return SeleniumUtil.getWebElement(d, "EditPopupDoneButton", "SFG", null);
	}
	public WebElement NextBtnStep1() {
		return SeleniumUtil.getWebElement(d, "NextBtnStep1", "SFG", null);
	}
	public WebElement RadioButton3() {
		return SeleniumUtil.getWebElement(d, "RadioButton3", "SFG", null);
	}
	public WebElement SavepopUpAddFunds() {
		return SeleniumUtil.getWebElement(d, "SavepopUpAddFunds", "SFG", null);
	}
	public WebElement offtrackMsg() {
		return SeleniumUtil.getWebElement(d, "offtrackMsg", "SFG", null);
	}
	public WebElement InprogressOfftrackMsg() {
		return SeleniumUtil.getWebElement(d, "InprogressOfftrackMsg", "SFG", null);
	}
	public WebElement viewMoreButtonForInprogress() {
		return SeleniumUtil.getWebElement(d, "viewMoreButtonForInprogress", "SFG", null);
	}
	public List<WebElement> DashboardGoalColor() {
		return SeleniumUtil.getWebElements(d, "DashboardGoalColor", "SFG", null);
	}
	public WebElement offTrackErrorMsg1() {
		return SeleniumUtil.getWebElement(d, "offTrackErrorMsg1", "SFG", null);
	}
	public WebElement offTrackErrorMsg2() {
		return SeleniumUtil.getWebElement(d, "offTrackErrorMsg2", "SFG", null);
	}
	public WebElement offTrackErrorMsg3() {
		return SeleniumUtil.getWebElement(d, "offTrackErrorMsg3", "SFG", null);
	}
	
	
	
	
	
	
	
	
	
	
	

}
