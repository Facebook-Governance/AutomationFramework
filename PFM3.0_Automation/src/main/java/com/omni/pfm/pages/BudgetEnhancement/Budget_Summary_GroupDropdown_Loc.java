/**
* Copyright (c) 2019 Yodlee Inc. All Rights Reserved. 
*
* This software is the confidential and proprietary information of
* Yodlee, Inc. Use is subject to license terms.
*
* @author mallikan
*/
package com.omni.pfm.pages.BudgetEnhancement;

import java.util.Calendar;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.omni.pfm.PageProcessor.PageParser;
import com.omni.pfm.utility.PropsUtil;
import com.omni.pfm.utility.SeleniumUtil;

public class Budget_Summary_GroupDropdown_Loc {

	public static WebDriver d;
	String frameName, pageName;
	static WebElement we;
	static Logger logger = LoggerFactory.getLogger(Budget_Summary_GroupDropdown_Loc.class);

	public Budget_Summary_GroupDropdown_Loc(WebDriver d) {
		this.d = d;
		pageName = "Budget";
	}


	public WebElement linkAccount() {
		return SeleniumUtil.getVisibileWebElement(d, "LinkAccountBtn_BS", pageName, frameName);
	}
	
	public WebElement GetStartedBtn() {
		return SeleniumUtil.getVisibileWebElement(d, "getStartedButton_BS", pageName, frameName);
	}
		
	public WebElement GroupDropdown() {
		return SeleniumUtil.getVisibileWebElement(d, "GroupDropdown_BS", pageName, frameName);
	}
	public WebElement createGroupDD() {
		return SeleniumUtil.getWebElement(d, "createGroupDD_BS", pageName, frameName);
	}
	
	public WebElement createGroupBtn() {
		return SeleniumUtil.getWebElement(d, "createGroupBtn_BS", pageName, frameName);
	}
	public WebElement ManageGroupBtn() {
		return SeleniumUtil.getVisibileWebElement(d, "ManageGroupBtn_BS", pageName, frameName);
	}
	
	public WebElement SelectedGroupName() {
		return SeleniumUtil.getVisibileWebElement(d, "SelectedGroupname_BS", pageName, frameName);
	}
	public WebElement EditGroupIcon() {
		SeleniumUtil.waitForPageToLoad(2000);
		return SeleniumUtil.getVisibileWebElement(d, "EditGroupIcon_BS", pageName, frameName);
	}
	
	public WebElement EditGroupName1() {
		return SeleniumUtil.getVisibileWebElement(d, "EditGroupName_BS1", pageName, frameName);
	}
	public WebElement EditGroupDropDown() {
		return SeleniumUtil.getVisibileWebElement(d, "EditGroupDropDown_BS", pageName, frameName);
	}
	
	public WebElement SelectAllAccountsDD() {
		return SeleniumUtil.getVisibileWebElement(d, "SelectAllAccountsDD_BS", pageName, frameName);
	}
	public WebElement SaveChangesBtn() {
		return SeleniumUtil.getVisibileWebElement(d, "SaveChangesBtn_BS", pageName, frameName);
	}
	public WebElement BacktoBudget() {
		
		return SeleniumUtil.getVisibileWebElement(d, "BacktoBudget_BS", pageName, frameName);
	}

	public WebElement CreateGrpinAccGrp() {
		return SeleniumUtil.getVisibileWebElement(d, "CreateGrpinAccGrpPage_BS", pageName, frameName);
	}
	public WebElement BudgetListinDD() {
		return SeleniumUtil.getWebElement(d, "BudgetNameListinDD_BS", pageName, frameName);
	}
	public WebElement backBtnMob()
	{
		return SeleniumUtil.getWebElement(d, "backBtn_mob", pageName, null);
	}
	
	public WebElement MoreBtn() {
		SeleniumUtil.waitForPageToLoad();
		return SeleniumUtil.getWebElement(d, "MoreBtn_BS", pageName, frameName);
	}
	public WebElement DeleteBudgetBtn() {
		SeleniumUtil.waitForPageToLoad();
		return SeleniumUtil.getWebElement(d, "DeleteBudgetBtn_BS", pageName, frameName);

	}
	public WebElement Group_name() {
		return SeleniumUtil.getWebElement(d, "Group_name_BS", pageName, frameName);
	}
	public WebElement NextBtn() {
		return SeleniumUtil.getWebElement(d, "NextBtn_BS", pageName, frameName);
	}
	public WebElement DoneBtn() {
		return SeleniumUtil.getWebElement(d, "DoneBtn_BS", pageName, frameName);
	}
	public WebElement DeleteConfirm() {
		SeleniumUtil.waitForPageToLoad();
		return SeleniumUtil.getWebElement(d, "DeleteConfirm_BS", pageName, frameName);
	}
	
	public WebElement CreateBudgetHeader() {
		SeleniumUtil.waitForPageToLoad();
		return SeleniumUtil.getWebElement(d, "CreateBudgetHeader_BS", pageName, frameName);
	}
	
	
	public List<WebElement> verifyGreyedOutAccountsInDD()
	{
		return SeleniumUtil.getWebElements(d, "greyedOutAccountsInDD", pageName, frameName);
	}
	
	public List<WebElement> validAccountsInBdgtDD()
	{
		return SeleniumUtil.getWebElements(d, "validAccountsInBdgtDD", pageName, frameName);
	}
	
	public WebElement backToBdgtFromAcctGroups_BS_mob()
	{
		
		return SeleniumUtil.getWebElement(d, "backToBdgtFromAcctGroups_BS_mob", pageName, frameName);
	}
	
	public WebElement bdgtAccGpDDDoneBtn_BS_mob()
	{
		return SeleniumUtil.getWebElement(d, "bdgtAccGpDDDoneBtn_BS_mob", pageName, frameName);
	}
	
	
}
