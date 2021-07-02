/**
* Copyright (c) 2018 Yodlee Inc. All Rights Reserved.
*
* This software is the confidential and proprietary information of
* Yodlee, Inc. Use is subject to license terms.
*
* @author Basavaraj 
*/
package com.omni.pfm.pages.BudgetV2;

import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.omni.pfm.utility.SeleniumUtil;

public class New_Budget_App_Loc {
	String frameName, pageName;
	static Logger logger = LoggerFactory.getLogger(New_Budget_App_Loc.class);
	public WebDriver d = null;
	static WebElement we;

	public New_Budget_App_Loc(WebDriver d) {
		this.d = d;
		pageName = 	"Budget";
		frameName = null;
	}
	
	public WebElement NewBudgetPageName() {
		return SeleniumUtil.getWebElement(d, "BudgetPageTitle", pageName, frameName);
	}
	
	public WebElement GetStartedBtn() {
		return SeleniumUtil.getWebElement(d, "GetStarted_button", pageName, frameName);
	}
	
	public WebElement EnterName() {
		return SeleniumUtil.getWebElement(d, "Enter_Name", pageName, frameName);
	}
	
	public WebElement NextBtn() {
		return SeleniumUtil.getWebElement(d, "Next_button", pageName, frameName);
	}
	
	public WebElement DoneBtn() {
		return SeleniumUtil.getWebElement(d, "Done_button", pageName, frameName);
	}
	
	public WebElement BudgetSummaryPage() {
		return SeleniumUtil.getWebElement(d, "BudgetSummaryTitle", pageName, frameName);
	}
	
	public WebElement MoreOption() {
		return SeleniumUtil.getWebElement(d, "MoreOptionLink", pageName, frameName);
	}
	
	public WebElement DeleteBudget() {
		return SeleniumUtil.getWebElement(d, "DeleteButton", pageName, frameName);
	}
	
	public WebElement PopupHeader() {
		return SeleniumUtil.getWebElement(d, "DeletePopupHeader", pageName, frameName);
	}
	
	public List <WebElement> PopupHeaderList() {
		return SeleniumUtil.getWebElements(d, "DeletePopupHeader", pageName, frameName);
	}
	
	public WebElement PopupBodyMessage1() {
		return SeleniumUtil.getWebElement(d, "PopupBodyText1", pageName, frameName);
	}
	
	public WebElement PopupBodyMessage2() {
		return SeleniumUtil.getWebElement(d, "PopupBodyText2", pageName, frameName);
	}
	public WebElement checkCancelText() {
		return SeleniumUtil.getWebElement(d, "CancelText", pageName, frameName);
	}
	
	public WebElement closeIcone() {
		return SeleniumUtil.getWebElement(d, "CloseIcon", pageName, frameName);
	}
	
	public WebElement CheckCancelButton() {
		return SeleniumUtil.getWebElement(d, "CancelButtonInPopup", pageName, frameName);
	}
	
	public WebElement checkDeleteText() {
		return SeleniumUtil.getWebElement(d, "DeleteText", pageName, frameName);
	}
	
	public WebElement CheckDeleteButton() {
		return SeleniumUtil.getWebElement(d, "DeleteButtonInPopup", pageName, frameName);
	}
	
	public WebElement GroupDropdown() {
		return SeleniumUtil.getWebElement(d, "GroupDropDownList", pageName, frameName);
	}
	
	public WebElement CreatedGroupName() {
		return SeleniumUtil.getWebElement(d, "CurrentGroupName", pageName, frameName);
	}
	
	public WebElement CreateBudgetHeader() {
		return SeleniumUtil.getWebElement(d, "CreateBudgetPageHeader", pageName, frameName);
	}
	
	//remove this once the FTUE but is fixed 
	public WebElement Step1String() {
		return SeleniumUtil.getWebElement(d, "Step1String1", pageName, frameName);
	}
	
	public WebElement GroupName() {
		return SeleniumUtil.getWebElement(d, "BudgetGroupName", pageName, frameName);
	}
	
	public WebElement SettingsDropdown() {
		return SeleniumUtil.getWebElement(d, "SettingDropdown", pageName, frameName);
	}
	
	public WebElement AccountGroupName1() {
		return SeleniumUtil.getWebElement(d, "AccountGroupName", pageName, frameName);
	}
	
	public WebElement CheckGroupName() {
		return SeleniumUtil.getWebElement(d, "CorrectGroupName", pageName, frameName);
	}
	
	public WebElement BudgetDeletedTostMessage() {
		return SeleniumUtil.getWebElement(d, "BudgetDeletedTost", pageName, frameName);
	}
	
}
