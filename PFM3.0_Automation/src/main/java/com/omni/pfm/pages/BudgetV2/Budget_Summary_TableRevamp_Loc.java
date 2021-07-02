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

public class Budget_Summary_TableRevamp_Loc extends SeleniumUtil {

	public static WebDriver d;
	static String frameName=null;
	static String pageName="Budget";
	static WebElement we;
	static Logger logger = LoggerFactory.getLogger(Budget_Summary_TableRevamp_Loc.class);
	public static By budgetWidgetArrow = getByObject(pageName, frameName, "BudgetWidgetArrow_BS");
	public Budget_Summary_TableRevamp_Loc(WebDriver d) {
		this.d = d;
	}

	public WebElement verifyWantsHeader() {
		return getWebElement(d, "WantsHeader_BS", pageName, frameName);
	}

	public WebElement verifyNeedsHeader() {
		return getWebElement(d, "NeedsHeader_BS", pageName, frameName);
	}

	public WebElement verifyIncomeHeader() {
		return getWebElement(d, "IncomeHeader_BS", pageName, frameName);
	}

	public WebElement CurrentMonth() {
		return getWebElement(d, "CurrentMonth_BS", pageName, frameName);
	}

	public List<WebElement> BudgetedAmt() {
		return getWebElements(d, "BudgetedAmt_BS", pageName, frameName);
	}

	public List<WebElement> SpentOrEarnedAmt() {
		return getWebElements(d, "SpentOrEarnedAmt_BS", pageName, frameName);
	}

	public List<WebElement> CategoryProgressBar() {
		return getWebElements(d, "CategoryProgressBar_BS", pageName, frameName);
	}

	public List<WebElement> CategoryIcon() {
		return getWebElements(d, "CategoryIcon_BS", pageName, frameName);
	}

	public List<WebElement> BudgetCategoriesLists() {
		return getWebElements(d, "BudgetCategories_List_BS", pageName, frameName);
	}

	public WebElement CategoryInAccCategPage() {
		return getWebElement(d, "CategoryInAccCategPage_BS", pageName, frameName);
	}

	public WebElement SubCategoryInAccCategPage() {
		return getWebElement(d, "SubCategoryInAccCategPage_BS", pageName, frameName);
	}

	public WebElement EditCategoryInAccCategPage() {
		return getWebElement(d, "EditCategoryInAccCategPage_BS", pageName, frameName);
	}

	public WebElement SaveEditCatNameChanges() {
		return getWebElement(d, "SaveEditCatNameChanges_BS", pageName, frameName);
	}

	public WebElement BudgetAmtColumn() {
		return getWebElement(d, "BudgetAmtColumn_BS", pageName, frameName);
	}

	public List<WebElement> FlexiSpendAmtColumnList() {
		return getWebElements(d, "FlexiSpendAmtColumnList_BS", pageName, frameName);
	}

	public WebElement EditBudgetAmtField() {
		return getWebElement(d, "EditBudgetAmtField_BS", pageName, frameName);
	}

	public List<WebElement> BudgetAmtColumnList() {
		waitForPageToLoad(3000);

		return getWebElements(d, "BudgetAmtColumnList_BS", pageName, frameName);
	}

	public WebElement FlexiSpendAmtColumnSpentAmtList() {
		return getWebElement(d, "FlexiSpendAmtColumnSpentAmtList_BS", pageName, frameName);
	}

	public WebElement FlexiSpendAmtColumnLeftOverAmtList() {
		return getWebElement(d, "FlexiSpendAmtColumnLeftOverAmtList_BS", pageName, frameName);
	}

	public WebElement VerifyNeedsHeader() {
		return getWebElement(d, "VerifyNeedsHeader_BS", pageName, frameName);
	}

	public WebElement VerifyWantsHeader() {
		return getWebElement(d, "VerifyWantsHeader_BS", pageName, frameName);
	}

	public WebElement WantsColumsText() {
		return getWebElement(d, "WantsColumsText_BS", pageName, frameName);
	}

	public WebElement NeedsColumsText() {
		return getWebElement(d, "NeedsColumsText_BS", pageName, frameName);
	}

	public List<WebElement> VerifyAccordianIcon() {
		return getWebElements(d, "AccornianIcon_BS", pageName, frameName);
	}

	public WebElement AccornianForCurrMonth() {
		return getWebElement(d, "AccornianForCurrMonth_BS", pageName, frameName);
	}

	public WebElement Budget_Edit_errorMessage() {
		return getWebElement(d, "Budget_Edit_errormessage", pageName, frameName);
	}

	public WebElement AddNewWantsCat() {
		return getWebElement(d, "AddNewWantsCat_BS", pageName, frameName);
	}

	public WebElement DefaultCatName() {
		return getWebElement(d, "DefaultCatName_BS", pageName, frameName);
	}

	public List<WebElement> NewSpendingCatListinDDList() {
		return getWebElements(d, "NewSpendingCatListinDDList_BS", pageName, frameName);
	}

	public WebElement SpendingCatListinDDIcon() {
		return getWebElement(d, "SpendingCatListinDDIcon_BS", pageName, frameName);
	}

	public WebElement AddNewCatAmtTextField() {
		return getWebElement(d, "AddNewCatAmtTextField_BS", pageName, frameName);
	}

	public WebElement AddNewAddBtn() {
		return getWebElement(d, "AddNewAddBtn_BS", pageName, frameName);
	}

	public WebElement AddCatErrorMsg() {
		return getWebElement(d, "AddCatErrorMsg_BS", pageName, frameName);
	}

	public WebElement AddCatCloseIcon() {
		return getWebElement(d, "AddCatCloseIcon_BS", pageName, frameName);
	}

	public WebElement verifyEditGraph_AvgSpendingText() {
		return getWebElement(d, "EditGraph_AvgSpendingText", pageName, frameName);
	}

	public WebElement TransRowWrap() {
		return getWebElement(d, "TransRowWrap_BS", pageName, frameName);
	}

	public WebElement TransRowWrapEditTextField() {
		return getWebElement(d, "TransRowWrapEditTextField_BS", pageName, frameName);
	}

	public WebElement TransRowWrapEditText_SaveBtn() {
		return getWebElement(d, "TransRowWrapEditText_SaveBtn_BS", pageName, frameName);
	}

	public WebElement AddNewIncomeCat() {
		return getWebElement(d, "AddNewIncomeCat_BS", pageName, frameName);
	}

	public WebElement IncomeCatListinDDIcon() {
		return getWebElement(d, "IncomeCatListinDDIcon_BS", pageName, frameName);
	}

	public WebElement IncomeCatListinDDFirstCategory() {
		return getWebElement(d, "IncomeCatListinDDFirstCategory_BS", pageName, frameName);
	}

	public WebElement EditSumHeader() {
		return getWebElement(d, "EditSumHeader_BS", pageName, frameName);
	}

	public WebElement ApplyBtn() {
		return getWebElement(d, "ApplyBtn_BS", pageName, frameName);
	}

	public WebElement AvgEarningPast3monthsTextinBarchart() {
		return getWebElement(d, "AvgEarningPast3monthsTextinBarchart_BS", pageName, frameName);
	}

	public WebElement EditGraph_Legend() {
		return getWebElement(d, "EditGraph_Legend", pageName, frameName);
	}

	public WebElement EditTextBoxClseIcon() {
		waitForPageToLoad(5000);

		return getWebElement(d, "EditTextBoxClseIcon_BS", pageName, frameName);
	}

	public WebElement IncomeNewlyAddedCateg() {
		return getWebElement(d, "IncomeNewlyAddedCateg_BS", pageName, frameName);
	}

	public WebElement BudCategoryDeleteConfirmBtn() {
		return getWebElement(d, "BudCategoryDeleteConfirmBtn_BS", pageName, frameName);
	}

	public WebElement BudCategoryDeletedToastMessage() {
		return getWebElement(d, "BudCategoryDeletedToastMessage_BS", pageName, frameName);
	}

	public WebElement BudCategoryDeleteMessageInHeader() {
		return getWebElement(d, "BudCategoryDeleteMessageInHeader_BS", pageName, frameName);
	}

	public WebElement BudCategoryDeleteMessageInHeader_mob() {
		return getWebElement(d, "BudCategoryDeleteMessageInHeader_BS_mob", pageName, frameName);
	}

	public WebElement BudCategoryDeletePopupCloseIcon() {
		return getWebElement(d, "BudCategoryDeletePopupCloseIcon_BS", pageName, frameName);
	}

	public WebElement DeleteCatBtn() {
		return getWebElement(d, "DeleteCatBtn_BS", pageName, frameName);
	}

	public WebElement verifyEditDeleteBtn() {
		waitForPageToLoad(2000);

		return getWebElement(d, "EditGraphDeleteBtn", pageName, frameName);
	}

	public WebElement BudDeletePopupCancelBtn() {
		waitForPageToLoad(2000);

		return getWebElement(d, "BudDeletePopupCancelBtn_BS", pageName, frameName);
	}

	public WebElement MoreBtn() {
		waitForPageToLoad();
		return getWebElement(d, "MoreBtn_BS", pageName, frameName);
	}

	public WebElement DeleteBudgetBtn() {
		waitForPageToLoad();
		return getWebElement(d, "DeleteBudgetBtn_BS", pageName, frameName);

	}

	public WebElement DeleteConfirm() {
		waitForPageToLoad();
		return getWebElement(d, "DeleteConfirm_BS", pageName, frameName);
	}

	public WebElement CreateBudgetHeader() {
		waitForPageToLoad();
		return getWebElement(d, "CreateBudgetHeader_BS", pageName, frameName);
	}

	public WebElement CancelBudConfirm() {
		waitForPageToLoad();
		return getWebElement(d, "CancelBudConfirm_BS", pageName, frameName);
	}

	public WebElement BudCatDeletePopupCloseIcon() {
		waitForPageToLoad(3000);
		return getWebElement(d, "BudCatDeletePopupCloseIcon_BS", pageName, frameName);
	}

	public WebElement BackBtntoBudget_Mob() {
		return getWebElement(d, "BackBtntoBudget_Mob_BS", pageName, frameName);
	}

	public WebElement viewDetailsBtn() {
		return getWebElement(d, "viewDetailsBtn_mob", pageName, frameName);
	}

	public WebElement CloseBtn_Mob() {
		return getWebElement(d, "CloseBtn_Mob_BS", pageName, frameName);
	}

	public WebElement CloseCrossBtn_Mob() {
		return getWebElement(d, "CloseCrossBtn_Mob_BS", pageName, frameName);
	}

	public WebElement WantsNewlyAddedCateg() {
		return getWebElement(d, "WantsNewlyAddedCateg_BS", pageName, frameName);
	}

	public WebElement EditIncomeTextField() {
		return getWebElement(d, "EditIncomeTextField_BS", pageName, frameName);
	}

	public WebElement DefaultWantsDD() {
		return getWebElement(d, "DefaultWantsDD_BS", pageName, frameName);
	}

	public WebElement LastMonthTimeFilter() {
		return getWebElement(d, "LastMonthTimeFilter_BS", pageName, frameName);
	}

	public WebElement VerifyLastMonth() {
		return getWebElement(d, "VerifyLastMonth_BS", pageName, frameName);
	}

	public WebElement BudgetHeader() {
		return getWebElement(d, "BudgetHeader_BS", pageName, frameName);
	}

	public WebElement CreateBudgetBtn() {
		return getWebElement(d, "CreateBudgetBtn_BS", pageName, frameName);
	}
	
	public WebElement BudgetWidgetArrow() {
		return getWebElement(d, "BudgetWidgetArrow_BS", pageName, frameName);
	}

	public WebElement DashboardWidget_BudgetHeader() {
		return getWebElement(d, "DashboardWidget_BudgetHeader_BS", pageName, frameName);
	}

	public WebElement DashboardWidget_BudgetName() {
		return getWebElement(d, "DashboardWidget_BudgetName_BS", pageName, frameName);
	}

	public WebElement GroupDropdownBudgetName() {
		return getWebElement(d, "GroupDropdownBudgetName_BS", pageName, frameName);
	}

	public WebElement BackToDashboard() {
		return getWebElement(d, "BackToDashboard_BS", pageName, frameName);
	}

	public WebElement BudgetWidget() {
		return getWebElement(d, "BudgetWidget_BS", pageName, frameName);
	}

	public WebElement BudWidgetNoDataMessage() {
		return getWebElement(d, "BudWidgetNoDataMessage_BS", pageName, frameName);
	}

	public WebElement BudWidgetCreateButton() {
		return getWebElement(d, "BudWidgetCreateButton_BS", pageName, frameName);
	}
}
