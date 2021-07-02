/**
* Copyright (c) 2019 Yodlee Inc. All Rights Reserved. 
*
* This software is the confidential and proprietary information of
* Yodlee, Inc. Use is subject to license terms.
*
* @author mallikan
*/
package com.omni.pfm.pages.BudgetV2;

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

public class Budget_Summary_EditPopup_Loc extends SeleniumUtil {

	public static WebDriver d;
	static String frameName=null;
	static String pageName= "Budget";
	static WebElement we;
	static Logger logger = LoggerFactory.getLogger(Budget_Summary_EditPopup_Loc.class);
	public static final By incomeDeleteButton = getByObject(pageName, null, "incomeEditDeleteBtn_BS");
	public static final By incomeEditButton = getByObject(pageName, null, "IncomeEditBox_BS");

	public Budget_Summary_EditPopup_Loc(WebDriver d) {
		this.d = d;
	}

	public WebElement linkAccount() {
		return getVisibileWebElement(d, "LinkAccountBtn_BS", pageName, frameName);
	}

	public WebElement GetStartedBtn() {
		return getVisibileWebElement(d, "getStartedButton_BS", pageName, frameName);
	}

	public WebElement BudgetSummary() {
		return getWebElement(d, "BudgetSummaryEditBox_BS", pageName, frameName);
	}

	public WebElement createGroupDD() {
		return getWebElement(d, "createGroupDD_BS", pageName, frameName);
	}

	public WebElement createGroupBtn() {
		return getWebElement(d, "createGroupBtn_BS", pageName, frameName);
	}

	public WebElement Group_name() {
		return getWebElement(d, "Group_name_BS", pageName, frameName);
	}

	public WebElement NextBtn() {
		return getWebElement(d, "NextBtn_BS", pageName, frameName);
	}

	public WebElement DoneBtn() {
		return getWebElement(d, "DoneBtn_BS", pageName, frameName);
	}

	public WebElement EditSumHeader() {
		return getWebElement(d, "EditSumHeader_BS", pageName, frameName);
	}

	public WebElement cancelBtn() {
		waitForPageToLoad(2000);
		return getWebElement(d, "cancelBtn_BS", pageName, frameName);
	}

	public List<WebElement> EditTextBox() {
		return getWebElements(d, "BudgetSummaryEditBox_BS", pageName, frameName);
	}

	public WebElement AmountField() {
		return getWebElement(d, "AmtField_BS", pageName, frameName);
	}

	public WebElement VerifySpent() {
		return getWebElement(d, "VerifySpent_BS", pageName, frameName);
	}

	public WebElement Income() {
		return getWebElement(d, "VerifyIncome_BS", pageName, frameName);
	}

	public WebElement EarnedText() {
		return getWebElement(d, "VerifyEarnedText_BS", pageName, frameName);
	}

	public WebElement OverText() {
		return getWebElement(d, "VerifyOverText_BS", pageName, frameName);
	}

	public WebElement LeftText() {
		return getWebElement(d, "VerifyLeft_BS", pageName, frameName);
	}

	public WebElement EditTextClse() {
		return getWebElement(d, "EditTextBoxClseIcon_BS", pageName, frameName);
	}

	public WebElement ApplyBtn() {
		return getWebElement(d, "ApplyBtn_BS", pageName, frameName);
	}

	public List<WebElement> AmtField_Color() {

		return getWebElements(d, "AmtField_Color", pageName, frameName);
	}

	public WebElement verifyEditGraph() {
		return getWebElement(d, "Edit_Graph", pageName, frameName);
	}

	public List<WebElement> verifyEditGraph_BudgetBar() {
		return getWebElements(d, "EditGraph_BudgetBar", pageName, frameName);
	}

	public List<WebElement> verifyEditGraph_ActualSpentBar() {
		return getWebElements(d, "EditGraph_ActualSpentBar", pageName, frameName);
	}

	public WebElement verifyEditGraph_AvgSpendingText() {
		return getWebElement(d, "EditGraph_AvgSpendingText", pageName, frameName);
	}

	public WebElement verifyEditGraph_AvgSpendingLine() {
		return getWebElement(d, "EditGraph_AvgSpendingLine", pageName, frameName);
	}

	public WebElement verifyEditGraph_Legend() {
		return getWebElement(d, "EditGraph_Legend", pageName, frameName);
	}

	public WebElement verifyEditGraph_BudgetText() {
		return getWebElement(d, "EditGraph_BudgetText", pageName, frameName);
	}

	public WebElement verifyEditGraph_ActualText() {
		return getWebElement(d, "EditGraph_ActualText", pageName, frameName);
	}

	public WebElement verifyEditGraph_PreferredCurrency() {
		return getWebElement(d, "EditGraph_PreferredCurrency", pageName, frameName);
	}

	public WebElement verifyBudgetCategory_ExpandIcon() {
		return getWebElement(d, "BudgetCategory_ExpandIcon", pageName, frameName);
	}

	public List<WebElement> verifyEditGraph_XAxisMonths() {
		return getWebElements(d, "EditGraph_XAxisMonths", pageName, frameName);
	}

	public List<WebElement> verifyIncome_EditGraphBar() {
		return getWebElements(d, "Income_EditGraphBar", pageName, frameName);
	}

	public List<WebElement> verifyEditGraph_FlexibleSpendingCategory() {
		return getWebElements(d, "EditGraph_FlexibleSpendingCategory", pageName, frameName);
	}

	public WebElement verifyEditGraphDeleteBtn() {
		waitForPageToLoad(2000);

		return getWebElement(d, "EditGraphDeleteBtn", pageName, frameName);
	}

	public WebElement verifyDeleteBdgtCategoryBtn() {
		waitForPageToLoad(5000);

		return getWebElement(d, "DeleteBdgtCategoryBtn", pageName, frameName);
	}

	public WebElement Budget_Edit_errorMessage() {
		return getWebElement(d, "Budget_Edit_errormessage", pageName, frameName);
	}

	public boolean verifyGraphIsClickable() {
		boolean status = true;
		WebElement graph = verifyEditGraph_AvgSpendingLine();
		try {
			graph.click();
		} catch (Exception e) {
			status = false;
		}
		return status;
	}

	public List<WebElement> editGraph_BillsCategory() {

		return getWebElements(d, "EditGraph_BillsCategory", pageName, null);
	}

	public WebElement lastCategoryDeleteMsg() {

		return getWebElement(d, "LastCategoryDeleteMsg", pageName, null);
	}

	public WebElement budCategoryDeleteOkBtn() {

		return getWebElement(d, "BudCategoryDeleteOkBtn", pageName, null);
	}

	public WebElement HamburgerMenu_mob() {
		// TODO Auto-generated method stub

		waitForPageToLoad(5000);
		return getWebElement(d, "HamburgerMenu_BS_mob", pageName, null);

	}

	public boolean isExpense_HamburgerMenu_mob_Present() {
		return PageParser.isElementPresent("HamburgerMenu_BS_mob", pageName, null);
	}

	public boolean verifyViewDetailsBtn_mob() {
		return PageParser.isElementPresent("viewDetailsBtn_mob", pageName, null);
	}

	public WebElement viewDetailsBtn() {
		return getWebElement(d, "viewDetailsBtn_mob", pageName, null);
	}

	public boolean verifyBackBtn_mob() {
		return PageParser.isElementPresent("backBtn_mob", pageName, null);
	}

	public WebElement backBtnMob() {
		return getWebElement(d, "backBtn_mob", pageName, null);
	}

	public WebElement deleteBtn_mob() {
		return getWebElement(d, "deleteBtn_BS_mob", pageName, null);
	}

	public boolean clickDeleteBtn_mob() {
		return PageParser.isElementPresent("deleteBtn_BS_mob", pageName, null);
	}

	public WebElement moreBtn_mob() {
		return getWebElement(d, "moreBtn_BS_mob", pageName, null);
	}

	public boolean clickmoreBtn_mob() {
		return PageParser.isElementPresent("moreBtn_BS_mob", pageName, null);
	}

	// Added by Mallika for Budget-Bugfix-Principal
	public WebElement avgPast3MonthsLegend() {
		return getWebElement(d, "avgPast3MonthsLegend_BS", pageName, null);
	}

	public List<WebElement> budgetCategoryEditBox() {
		return getWebElements(d, "budgetCategoryEditBox_BS", pageName, null);
	}

	public WebElement budgetCategoryCloseIcon() {
		return getWebElement(d, "nudgetCategoryCloseIcon_BS", pageName, null);
	}

	public List<WebElement> budgetCategories_List() {
		return getWebElements(d, "budgetCategories_List_BS", pageName, null);
	}

	public List<WebElement> budgetTrendsBarChartMonthLabels() {
		return getWebElements(d, "budgetTrendsBarChartMonthLabels_BS", pageName, null);
	}

	public WebElement budgetNoDataScreen() {
		return getWebElement(d, "budgetNoDataScreen_BS", pageName, null);
	}

	public WebElement budgetIncomeContainer() {
		return getWebElement(d, "budgetIncomeContainer_BS", pageName, null);
	}

	public void  clickOnIncomeEditBox() {
		click(incomeEditButton);
	}

	public void clickOnIncomeDeleteButton() {
		click(incomeDeleteButton);
	}

	public WebElement incomeEditDeleteMessage() {
		return getWebElement(d, "incomeEditDeleteMessage_BS", pageName, null);
	}

	public WebElement incomeEditPopUpOkBtn() {
		return getWebElement(d, "incomeEditPopUpOkBtn_BS", pageName, null);
	}

	public WebElement spendingBarChartDisplay() {
		return getWebElement(d, "spendingBarChartDisplay_BS", pageName, null);
	}

	public boolean isBudgetCurrentMonthTransactionDisplayed() {
		return isDisplayed(getByObject(pageName, frameName, "budgetCurrentMonthTrans_BS"), 10);
	}

	public List<WebElement> budgetCurrentMonthChart() {
		return getWebElements(d, "budgetCurrentMonthChart_BS", pageName, null);
	}

	public WebElement groupNameErrorInfo() {
		return getWebElement(d, "groupNameErrorInfo_BS", pageName, null);
	}

	public WebElement selectAllAccountsBtn() {
		return getWebElement(d, "selectAllAccountsBtn_BS", pageName, null);
	}

	public WebElement accountUnselectErrorMsg() {
		return getWebElement(d, "accountUnselectErrorMsg_BS", pageName, null);
	}

	public WebElement createGrpButton() {
		return getWebElement(d, "createGrpButton_BS", pageName, null);
	}

	public WebElement CreateBudgetBtn() {
		return getWebElement(d, "createBudget_Btn_BS", pageName, frameName);
	}

	public WebElement GroupDropdown() {
		return getWebElement(d, "GroupDropdown_BS", pageName, frameName);
	}

	public WebElement incomeEditDeleteMessage_Mob() {
		return getWebElement(d, "incomeEditDeleteMessage_BS_Mob", pageName, frameName);
	}

	public WebElement budgetBackBtn_Mob() {
		return getWebElement(d, "budgetBackBtn_BS_Mob", pageName, frameName);
	}

	public WebElement budgetTimeFilter_Mob() {
		return getWebElement(d, "budgetTimeFilter_BS_Mob", pageName, frameName);
	}

	public List<WebElement> timeFilterDuration_Mob() {
		return getWebElements(d, "timeFilterSixmonth_BS_Mob", pageName, frameName);
	}

	public WebElement timeFilterTableView_Mob() {
		return getWebElement(d, "timeFilterTableView_BS_Mob", pageName, frameName);
	}

	public WebElement moreBtnBudgetCreate_Mob() {
		return getWebElement(d, "moreBtnBudgetCreate_BS_Mob", pageName, frameName);
	}

	public WebElement lastMonthBarChartHightlight() {
		return getWebElement(d, "lastMonthBarChartHightlight_BS", pageName, frameName);
	}

	public WebElement lastMonthHightlightBarName() {
		return getWebElement(d, "lastMonthHightlightBarName_BS", pageName, frameName);
	}

	public WebElement lastMonthTransactionMonthName() {
		return getWebElement(d, "lastMonthTransactionMonthName_BS", pageName, frameName);
	}

}
