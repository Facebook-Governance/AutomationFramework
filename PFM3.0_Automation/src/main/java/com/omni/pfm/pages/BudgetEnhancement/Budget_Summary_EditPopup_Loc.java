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

public class Budget_Summary_EditPopup_Loc {

	public static WebDriver d;
	String frameName, pageName;
	static WebElement we;
	static Logger logger = LoggerFactory.getLogger(Budget_Summary_EditPopup_Loc.class);

	public Budget_Summary_EditPopup_Loc(WebDriver d) {
		this.d = d;
		pageName = "Budget";
		frameName=null;
	}

	
	public WebElement linkAccount() {
		return SeleniumUtil.getVisibileWebElement(d, "LinkAccountBtn_BS", pageName, frameName);
	}
	
	public WebElement GetStartedBtn() {
		return SeleniumUtil.getVisibileWebElement(d, "getStartedButton_BS", pageName, frameName);
	}
	
	public WebElement BudgetSummary() {
		return SeleniumUtil.getWebElement(d, "BudgetSummaryEditBox_BS", pageName, frameName);
	}
	public WebElement createGroupDD() {
		return SeleniumUtil.getWebElement(d, "createGroupDD_BS", pageName, frameName);
	}
	
	public WebElement createGroupBtn() {
		return SeleniumUtil.getWebElement(d, "createGroupBtn_BS", pageName, frameName);
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
	
	
	
	
	
	public WebElement EditSumHeader() {
		return SeleniumUtil.getWebElement(d, "EditSumHeader_BS", pageName, frameName);
	}
	

	public WebElement cancelBtn() {
		SeleniumUtil.waitForPageToLoad(2000);
		return SeleniumUtil.getWebElement(d, "cancelBtn_BS", pageName, frameName);
	}	
	
	public List<WebElement> EditTextBox() {
		return SeleniumUtil.getWebElements(d, "BudgetSummaryEditBox_BS", pageName, frameName);
	}
	
	public WebElement AmountField() {
		return SeleniumUtil.getWebElement(d, "AmtField_BS", pageName, frameName);
	}
	public WebElement VerifySpent() {
		return SeleniumUtil.getWebElement(d, "VerifySpent_BS", pageName, frameName);
	}
	
	public WebElement Income() {
		return SeleniumUtil.getWebElement(d, "VerifyIncome_BS", pageName, frameName);
	}
	public WebElement EarnedText() {
		return SeleniumUtil.getWebElement(d, "VerifyEarnedText_BS", pageName, frameName);
	}
	public WebElement OverText() {
		return SeleniumUtil.getWebElement(d, "VerifyOverText_BS", pageName, frameName);
	}
	public WebElement LeftText() {
		return SeleniumUtil.getWebElement(d, "VerifyLeft_BS", pageName, frameName);
	}
	public WebElement EditTextClse() {
		return SeleniumUtil.getWebElement(d, "EditTextBoxClseIcon_BS", pageName, frameName);
	}
	public WebElement ApplyBtn() {
		return SeleniumUtil.getWebElement(d, "ApplyBtn_BS", pageName, frameName);
	}
	
	public List<WebElement> AmtField_Color() {
		
		return SeleniumUtil.getWebElements(d, "AmtField_Color", pageName, frameName);
	}
	
	public WebElement verifyEditGraph()
	{
		return SeleniumUtil.getWebElement(d, "Edit_Graph", pageName, frameName);
	}
	
	public List<WebElement> verifyEditGraph_BudgetBar()
	{
		return SeleniumUtil.getWebElements(d, "EditGraph_BudgetBar", pageName, frameName);
	}
	
	public List<WebElement> verifyEditGraph_ActualSpentBar()
	{
		return SeleniumUtil.getWebElements(d, "EditGraph_ActualSpentBar", pageName, frameName);
	}
	
	public WebElement verifyEditGraph_AvgSpendingText()
	{
		return SeleniumUtil.getWebElement(d, "EditGraph_AvgSpendingText", pageName, frameName);
	}
	
	public WebElement verifyEditGraph_AvgSpendingLine()
	{
		return SeleniumUtil.getWebElement(d, "EditGraph_AvgSpendingLine", pageName, frameName);
	}
	
	public WebElement verifyEditGraph_Legend()
	{
		return SeleniumUtil.getWebElement(d, "EditGraph_Legend", pageName, frameName);
	}
	
	public WebElement verifyEditGraph_BudgetText()
	{
		return SeleniumUtil.getWebElement(d, "EditGraph_BudgetText", pageName, frameName);
	}
	
	public WebElement verifyEditGraph_ActualText()
	{
		return SeleniumUtil.getWebElement(d, "EditGraph_ActualText", pageName, frameName);
	}
	
	public WebElement verifyEditGraph_PreferredCurrency()
	{
		return SeleniumUtil.getWebElement(d, "EditGraph_PreferredCurrency", pageName, frameName);
	}
	
	public WebElement verifyBudgetCategory_ExpandIcon()
	{
		return SeleniumUtil.getWebElement(d, "BudgetCategory_ExpandIcon", pageName, frameName);
	}
	
	public List<WebElement> verifyEditGraph_XAxisMonths()
	{
		return SeleniumUtil.getWebElements(d, "EditGraph_XAxisMonths", pageName, frameName);
	}
	 
	public List<WebElement> verifyIncome_EditGraphBar()
	{
		return SeleniumUtil.getWebElements(d, "Income_EditGraphBar", pageName, frameName);
	}
	 
	public List<WebElement> verifyEditGraph_FlexibleSpendingCategory()
	{
		return SeleniumUtil.getWebElements(d, "EditGraph_FlexibleSpendingCategory", pageName, frameName);
	}
	
	public WebElement verifyEditGraphDeleteBtn()
	{
		SeleniumUtil.waitForPageToLoad(2000);

		return SeleniumUtil.getWebElement(d, "EditGraphDeleteBtn", pageName, frameName);
	}
	
	public WebElement verifyDeleteBdgtCategoryBtn()
	{
		SeleniumUtil.waitForPageToLoad(5000);

		return SeleniumUtil.getWebElement(d, "DeleteBdgtCategoryBtn", pageName, frameName);
	}
	
	public WebElement Budget_Edit_errorMessage()
	{
		return SeleniumUtil.getWebElement(d, "Budget_Edit_errormessage", pageName, frameName);
	}	
	
	public boolean verifyGraphIsClickable()
	{
		boolean status = true;
		WebElement graph = verifyEditGraph_AvgSpendingLine();
		try
		{
			graph.click();
		}
		catch(Exception e)
		{
			status = false;
		}
		return status;
	}
	
	public List<WebElement> editGraph_BillsCategory() {

		return SeleniumUtil.getWebElements(d, "EditGraph_BillsCategory", pageName, null);
	}
	public WebElement lastCategoryDeleteMsg() {

		return SeleniumUtil.getWebElement(d, "LastCategoryDeleteMsg", pageName, null);
	}
	public WebElement budCategoryDeleteOkBtn() {

		return SeleniumUtil.getWebElement(d, "BudCategoryDeleteOkBtn", pageName, null);
	}
	public WebElement HamburgerMenu_mob() {
		// TODO Auto-generated method stub

		SeleniumUtil.waitForPageToLoad(5000);
		return SeleniumUtil.getWebElement(d, "HamburgerMenu_BS_mob", pageName, null);

	}
	public boolean isExpense_HamburgerMenu_mob_Present()
	{
		return PageParser.isElementPresent("HamburgerMenu_BS_mob", pageName, null);
	}	
	
	public boolean verifyViewDetailsBtn_mob()
	{
		return PageParser.isElementPresent("viewDetailsBtn_mob", pageName, null);
	}
	
	public WebElement viewDetailsBtn()
	{
		return SeleniumUtil.getWebElement(d, "viewDetailsBtn_mob", pageName, null);
	}
	public boolean verifyBackBtn_mob()
	{
		return PageParser.isElementPresent("backBtn_mob", pageName, null);
	}
	
	public WebElement backBtnMob()
	{
		return SeleniumUtil.getWebElement(d, "backBtn_mob", pageName, null);
	}
	
	public WebElement deleteBtn_mob()
	{
		return SeleniumUtil.getWebElement(d, "deleteBtn_BS_mob", pageName, null);
	}
	public boolean clickDeleteBtn_mob()
	{
		return PageParser.isElementPresent("deleteBtn_BS_mob", pageName, null);
	}
	
	public WebElement moreBtn_mob()
	{
		return SeleniumUtil.getWebElement(d, "moreBtn_BS_mob", pageName, null);
	}
	public boolean clickmoreBtn_mob()
	{
		return PageParser.isElementPresent("moreBtn_BS_mob", pageName, null);
	}
}
