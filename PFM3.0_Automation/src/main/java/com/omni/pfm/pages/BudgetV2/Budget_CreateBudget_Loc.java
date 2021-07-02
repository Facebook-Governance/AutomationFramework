/**
* Copyright (c) 2018 Yodlee Inc. All Rights Reserved.
*
* This software is the confidential and proprietary information of
* Yodlee, Inc. Use is subject to license terms.
*
* @author Renuka K
*/
package com.omni.pfm.pages.BudgetV2;

import static org.testng.Assert.fail;

import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.omni.pfm.PageProcessor.PageParser;
import com.omni.pfm.config.Config;
import com.omni.pfm.utility.PropsUtil;
import com.omni.pfm.utility.SeleniumUtil;

import net.bytebuddy.asm.Advice.This;

public class Budget_CreateBudget_Loc extends SeleniumUtil {
	final WebDriver d;
	static Logger log = LoggerFactory.getLogger(Budget_CreateBudget_Loc.class);
	final static String pageName="Budget";
	final static String frameName=null;
	public static final By addCategoryCloseIcon = getByObject(pageName, frameName, "budget_Steps2_AddCategoryCloseIcon");
	public static final By popupHeader = getByObject(pageName, frameName, "budget_Steps2_PopupHeader");
	public static final By doneButton = getByObject(pageName, frameName, "budget_Steps2_DoneButton");
	public static final By selectAllAccountsCheckbox = getByObject(pageName, frameName, "budget_AllAccounts_checkbox");
	public static final By goalSectionInBudgetCategories = getByObject(pageName, frameName, "goalsCategoryInBudgetSection");
	public static final By budgetUnableToCreateGoals = getByObject(pageName, frameName, "budgetUnableToCreateGoalsMessage");
	public static final By createBudgetButtonInBudgetPage = getByObject(pageName, frameName, "createABudgetIconInBudgetPage");
	public static final By budgetGroupNameDropdown = getByObject(pageName, frameName, "BudgetGroupName");

	public Budget_CreateBudget_Loc(WebDriver d) {
		this.d = d;
	}
	

	public WebElement budget_NoDataHeader() {
		return getVisibileWebElement(d, "budget_NoDataHeader", pageName, frameName);
	}

	public WebElement budget_NoDataDescription() {
		return getVisibileWebElement(d, "budget_NoDataDescription", pageName, frameName);
	}

	public WebElement budget_Step1_FinappHeader() {// Budget finapp header locator in step1
		return getVisibileWebElement(d, "budget_Step1_FinappHeader", pageName, frameName);
	}

	public WebElement budget_Step1_Instruction() {
		return getVisibileWebElement(d, "budget_Step1_Instruction", pageName, frameName);
	}

	public WebElement budget_Step1_InstructionInfo() {
		return getVisibileWebElement(d, "budget_Step1_InstructionInfo", pageName, frameName);
	}

	public WebElement budget_Getstart_Step1Name() {
		return getVisibileWebElement(d, "budget_Getstart_Step1Name", pageName, frameName);
	}

	public WebElement budget_Getstart_Step2Name() {
		return getVisibileWebElement(d, "budget_Getstart_Step2Name", pageName, frameName);
	}

	public WebElement budget_Getstart_Step1Icon() {
		return getVisibileWebElement(d, "budget_Getstart_Step1Icon", pageName, frameName);
	}

	public WebElement budget_Getstart_Step2Icon() {
		return getVisibileWebElement(d, "budget_Getstart_Step2Icon", pageName, frameName);
	}

	public WebElement budget_Getstart_Step1Info() {
		return getVisibileWebElement(d, "budget_Getstart_Step1Info", pageName, frameName);
	}

	public WebElement budget_Getstart_Step2Info() {
		return getVisibileWebElement(d, "budget_Getstart_Step2Info", pageName, frameName);
	}

	public WebElement budget_Getstart_InfoMesssage() {
		return getVisibileWebElement(d, "budget_Getstart_InfoMesssage", pageName, frameName);
	}

	public WebElement budget_Getstart_LinkAccountButton() {
		return getVisibileWebElement(d, "budget_Getstart_LinkAccountButton", pageName, frameName);
	}

	public WebElement budget_Steps2_linkAccountMobile() {
		return getVisibileWebElement(d, "budget_Steps2_linkAccountMobile", pageName, frameName);
	}

	public WebElement budget_Getstart_GetStartButton() {
		return getVisibileWebElement(d, "budget_Getstart_GetStartButton", pageName, frameName);
	}

	public List<WebElement> budget_StepsNameList() {// budget steps name locator eg. 1.select account 2.setup budget
		return getWebElements(d, "budget_StepsNameList", pageName, frameName);
	}

	public List<WebElement> budget_StepsNameListMobile() {// budget steps name locator eg. 1.select account 2.setup
															// budget
		return getWebElements(d, "budget_StepsNameListMobile", pageName, frameName);
	}

	public WebElement budget_ActiveStepsName() {// active step name
		return getWebElement(d, "budget_ActiveStepsName", pageName, frameName);
	}

	public WebElement budget_ActiveStepsNameMobile() {// active step name
		return getWebElement(d, "budget_ActiveStepsNameMobile", pageName, frameName);
	}

	public WebElement budget_Steps1_AccountInfoMessage() {// info message locator in step 1 (Account selection step)
		return getVisibileWebElement(d, "budget_Steps1_AccountInfoMessage", pageName, frameName);
	}

	public WebElement budget_Steps1_AccountGroupDropDown() {
		// account group dropdown locator in step1(Account selection step)
		return getVisibileWebElement(d, "budget_Steps1_AccountGroupDropDown", pageName, frameName);
	}

	public WebElement budget_Steps1_AccountGroupDropDownIcon() {
		// account group dropdown icon locator in step1(Account selection step)
		return getVisibileWebElement(d, "budget_Steps1_AccountGroupDropDownIcon", pageName, frameName);
	}

	public WebElement budget_Steps1_BudgetNameField() {
		// group name input field locator in step1
		return getWebElement(d, "budget_Steps1_BudgetNameField", pageName, frameName);
	}

	public WebElement budget_Steps1_BudgetNameFieldErrorMsg() {
		// group name input field error message locator in step1
		return getVisibileWebElement(d, "budget_Steps1_BudgetNameFieldErrorMsg", pageName, frameName);
	}

	public WebElement budget_Steps1_BudgetGroupName() {

		return getVisibileWebElement(d, "budget_Steps1_BudgetGroupName", pageName, frameName);
	}

	public WebElement budget_Steps1_AccountContainerName(String accountContainerName) {
		// get locator for account container name based on parameterizing container name
		String xpath = getVisibileElementXPath(d, pageName, frameName,
				"budget_Steps1_AccountContainerName");
		xpath = xpath.replaceAll("accountContainerName", accountContainerName);
		return d.findElement(By.xpath(xpath));
	}

	public List<WebElement> budget_Steps1_AccountContainerNameList() {// locator for get all account container name at a
																		// time
		return getWebElements(d, "budget_Steps1_AccountContainerNameList", pageName, frameName);
	}

	public List<WebElement> budget_Steps1_AccountInstitutionName(String accountContainerName) {// get the institution
																								// name based on
																								// container
		String xpath = getVisibileElementXPath(d, pageName, frameName,
				"budget_Steps1_AccountInstitutionName");
		xpath = xpath.replace("accountContainerName", accountContainerName);
		return d.findElements(By.xpath(xpath));

	}

	public List<WebElement> budget_Steps1_AccountNumber(String accountContainerName) {// get the account number based on
																						// container
		String xpath = getVisibileElementXPath(d, pageName, frameName, "budget_Steps1_AccountNumber");
		xpath = xpath.replace("accountContainerName", accountContainerName);
		return d.findElements(By.xpath(xpath));

	}

	public List<WebElement> budget_Steps1_AccountInstitutionNameList() {// locator for get all account institution name
																		// at a time
		return getWebElements(d, "budget_Steps1_AccountInstitutionNameList", pageName, frameName);
	}

	public List<WebElement> budget_Steps1_AccountNumberList() {// locator for get all account number at a time
		return getWebElements(d, "budget_Steps1_AccountContainerNameList", pageName, frameName);
	}

	public List<WebElement> budget_Steps1_AccountCheckBox(String accountContainerName) {// get the account selection
																						// check box based on container
		String xpath = getVisibileElementXPath(d, pageName, frameName, "budget_Steps1_AccountCheckBox");
		xpath = xpath.replace("accountContainerName", accountContainerName);
		return d.findElements(By.xpath(xpath));

	}

	public WebElement budget_Steps1_AccountExpand(String accountContainerName) {// get the account expand locator based
																				// on container
		String xpath = getVisibileElementXPath(d, pageName, frameName, "budget_Steps1_AccountExpand");
		xpath = xpath.replace("accountContainerName", accountContainerName);
		return d.findElement(By.xpath(xpath));

	}

	public WebElement budget_Steps1_NextButton() {// step1 next button locator
		return getVisibileWebElement(d, "budget_Steps1_NextButton", pageName, frameName);
	}

	public WebElement budget_Steps1_EditButton() {
		// edit budegt button in step1
		return getVisibileWebElement(d, "budget_Steps1_EditButton", pageName, frameName);

	}

	public WebElement budget_Steps1_EditLabel() {
		// edit budegt label in step1
		return getVisibileWebElement(d, "budget_Steps1_EditLabel", pageName, frameName);

	}

	public WebElement budget_Steps2_EditMobile() {
		// edit budegt label in step1
		return getVisibileWebElement(d, "budget_Steps2_EditMobile", pageName, frameName);

	}

	public WebElement budget_Steps1_SuccessMessage() {
		// budegt success message label in step1
		return getVisibileWebElement(d, "budget_Steps1_SuccessMessage", pageName, frameName);

	}

	public WebElement budget_Steps1_GrpDDEmptIcon() {

		return getVisibileWebElement(d, "budget_Steps1_GrpDDEmptIcon", pageName, frameName);

	}

	public WebElement budget_Steps1_GrpDDEmptHeader() {

		return getVisibileWebElement(d, "budget_Steps1_GrpDDEmptHeader", pageName, frameName);

	}

	public WebElement budget_Steps1_GrpDDEmptDesc() {

		return getVisibileWebElement(d, "budget_Steps1_GrpDDEmptDesc", pageName, frameName);

	}

	public WebElement budget_Steps1_GrpDDCreate() {

		return getVisibileWebElement(d, "budget_Steps1_GrpDDCreate", pageName, frameName);

	}

	public WebElement budget_Steps1_GrpDDCreategroup() {

		return getVisibileWebElement(d, "budget_Steps1_GrpDDCreategroup", pageName, frameName);

	}

	public WebElement budget_Steps2_GrpDDCancel() {

		return getWebElement(d, "budget_Steps2_GrpDDCancel", pageName, frameName);

	}

	public WebElement budget_Steps2_GrpDDDone() {

		return getVisibileWebElement(d, "budget_Steps2_GrpDDDone", pageName, frameName);

	}

	public WebElement budget_Steps1_GrpDDOpen() {

		return getWebElement(d, "budget_Steps1_GrpDDOpen", pageName, frameName);

	}

	public WebElement budget_Steps1_LinAccountClose() {

		return getWebElement(d, "budget_Steps1_LinAccountClose", pageName, frameName);

	}

	public WebElement budget_Steps1_LinAccountDone() {

		return getWebElement(d, "budget_Steps1_LinAccountDone", pageName, frameName);

	}

	public List<WebElement> budget_Steps1_GrpDDName() {

		return getWebElements(d, "budget_Steps1_GrpDDName", pageName, frameName);

	}

	public WebElement budget_Steps2_BudgetName() {
		// budget name locator in budget step2
		return getVisibileWebElement(d, "budget_Steps2_BudgetName", pageName, frameName);
	}

	public WebElement budget_Steps2_InstructionMobile() {

		return getVisibileWebElement(d, "budget_Steps2_InstructionMobile", pageName, frameName);
	}

	public WebElement budget_Steps2_InstructionInfoMobile() {

		return getVisibileWebElement(d, "budget_Steps2_InstructionInfoMobile", pageName, frameName);
	}

	public WebElement budget_Steps2_BudgetInfoMessage() {
		// budget last 3month info message
		return getVisibileWebElement(d, "budget_Steps2_BudgetInfoMessage", pageName, frameName);
	}

	public WebElement budget_Steps2_LeftOverLabel() {
		// budget category section total left over label
		return getVisibileWebElement(d, "budget_Steps2_LeftOverLabel", pageName, frameName);
	}

	public WebElement budget_Steps2_LeftOverAmount() {
		// budget total left over amount
		return getVisibileWebElement(d, "budget_Steps2_LeftOverAmount", pageName, frameName);

	}

	public WebElement budget_Steps2_LeftOverLabelMobile() {
		// budget total left over amount
		return getVisibileWebElement(d, "budget_Steps2_LeftOverLabelMobile", pageName, frameName);

	}

	public WebElement budget_Steps2_LeftOverAmountMobile() {
		// budget total left over amount
		return getWebElement(d, "budget_Steps2_LeftOverAmountMobile", pageName, frameName);

	}

	public List<WebElement> budget_Steps2_CategoryTypeLabel() {
		// list of category section
		return getWebElements(d, "budget_Steps2_CategoryTypeLabel", pageName, frameName);

	}

	public WebElement budget_Steps2_CategoryTypeAmount(String categorySectionName) {
		// get total catgory section amount locator based on HLC (income,bills,flexibel
		// spending)
		String xpath = getVisibileElementXPath(d, pageName, frameName, "budget_Steps2_CategoryTypeAmount");
		xpath = xpath.replace("categorySectionName", categorySectionName);
		waitForPageToLoad(1000);
		return d.findElement(By.xpath(xpath));

	}

	public WebElement budget_Steps2_CategoryTypeExpand(String categorySectionName) {
		// get total catgory section expand locator based on HLC (income,bills,flexibel
		// spending)
		String xpath = getVisibileElementXPath(d, pageName, frameName, "budget_Steps2_CategoryTypeExpand");
		xpath = xpath.replace("categorySectionName", categorySectionName);
		return d.findElement(By.xpath(xpath));
	}

	public WebElement budget_Steps2_CategorySelectAllCheckBox(String categorySectionName) {
		// get all catgory section locator based on HLC (income,bills,flexibel spending)
		String xpath = getVisibileElementXPath(d, pageName, frameName,
				"budget_Steps2_CategorySelectAllCheckBox");
		xpath = xpath.replace("categorySectionName", categorySectionName);
		return d.findElement(By.xpath(xpath));
	}

	public WebElement budget_Steps2_CategorySelectAllLabel(String categorySectionName) {
		// get all catgory section label locator based on HLC (income,bills,flexibel
		// spending)
		String xpath = getVisibileElementXPath(d, pageName, frameName,
				"budget_Steps2_CategorySelectAllLabel");
		xpath = xpath.replace("categorySectionName", categorySectionName);
		return d.findElement(By.xpath(xpath));
	}

	public WebElement budget_Steps2_CategorySelectAllLabelMobile(String categorySectionName) {
		// get all catgory section label locator based on HLC (income,bills,flexibel
		// spending)
		String xpath = getVisibileElementXPath(d, pageName, frameName,
				"budget_Steps2_CategorySelectAllLabelMobile");
		xpath = xpath.replace("categorySectionName", categorySectionName);
		return d.findElement(By.xpath(xpath));
	}

	public WebElement budget_Steps2_CategoryMonthyAvgLabel(String categorySectionName) {
		// get catgory 3 month avg label section label locator based on HLC
		// (income,bills,flexibel spending)
		String xpath = getVisibileElementXPath(d, pageName, frameName,
				"budget_Steps2_CategoryMonthyAvgLabel");
		xpath = xpath.replace("categorySectionName", categorySectionName);
		return d.findElement(By.xpath(xpath));
	}

	public WebElement budget_Steps2_CategoryMonthyBudgetLabel(String categorySectionName) {
		// get catgory monthly budgeted label section label locator based on HLC
		// (income,bills,flexibel spending)
		String xpath = getVisibileElementXPath(d, pageName, frameName,
				"budget_Steps2_CategoryMonthyBudgetLabel");
		xpath = xpath.replace("categorySectionName", categorySectionName);
		return d.findElement(By.xpath(xpath));
	}

	public WebElement budget_Steps2_CategoryMonthyBudgetLabelMobile(String categorySectionName) {
		// get catgory monthly budgeted label section label locator based on HLC
		// (income,bills,flexibel spending)
		String xpath = getVisibileElementXPath(d, pageName, frameName,
				"budget_Steps2_CategoryMonthyBudgetLabelMobile");
		xpath = xpath.replace("categorySectionName", categorySectionName);
		return d.findElement(By.xpath(xpath));
	}

	public List<WebElement> budget_Steps2_CategorySelectCheckBox(String categorySectionName) {// all category check box
																								// locator
		String xpath = getVisibileElementXPath(d, pageName, frameName,
				"budget_Steps2_CategorySelectCheckBox");
		xpath = xpath.replace("categorySectionName", categorySectionName);
		return d.findElements(By.xpath(xpath));

	}

	public List<WebElement> budget_Steps2_CategoryName(String categorySectionName) {// all category name locator
		String xpath = getVisibileElementXPath(d, pageName, frameName, "budget_Steps2_CategoryName");
		xpath = xpath.replace("categorySectionName", categorySectionName);
		return d.findElements(By.xpath(xpath));

	}

	public List<WebElement> budget_Steps2_CategoryMonthlyAgAmount(String categorySectionName) {// all category 3 month
																								// avg amount locator
		String xpath = getVisibileElementXPath(d, pageName, frameName,
				"budget_Steps2_CategoryMonthlyAgAmount");
		xpath = xpath.replace("categorySectionName", categorySectionName);
		return d.findElements(By.xpath(xpath));

	}

	public List<WebElement> budget_Steps2_CategoryMonthlyBudgetCurr(String categorySectionName) {// all category monthly
																									// budgeted currency
																									// locator
		String xpath = getVisibileElementXPath(d, pageName, frameName,
				"budget_Steps2_CategoryMonthlyBudgetCurr");
		xpath = xpath.replace("categorySectionName", categorySectionName);
		return d.findElements(By.xpath(xpath));

	}

	public List<WebElement> budget_Steps2_CategoryMonthlyBudgetAmt(String categorySectionName) {// all category monthly
																								// budgeted amount
																								// locator
		String xpath = getVisibileElementXPath(d, pageName, frameName,
				"budget_Steps2_CategoryMonthlyBudgetAmt");
		xpath = xpath.replace("categorySectionName", categorySectionName);
		return d.findElements(By.xpath(xpath));

	}

	public WebElement budget_Steps2_CategoryMonthlyBudgetAmtMobile() {
		// list of category section
		return getWebElement(d, "budget_Steps2_CategoryMonthlyBudgetAmtMobile", pageName, frameName);

	}

	public List<WebElement> budget_Steps2_CategoryIcon(String categorySectionName) {// all category icon locator
		String xpath = getVisibileElementXPath(d, pageName, frameName, "budget_Steps2_CategoryIcon");
		xpath = xpath.replace("categorySectionName", categorySectionName);
		return d.findElements(By.xpath(xpath));

	}

	public List<WebElement> budget_Steps2_CategoryDetailsExpand(String categorySectionName) {// all category expand
																								// locator
		String xpath = getVisibileElementXPath(d, pageName, frameName,
				"budget_Steps2_CategoryDetailsExpand");
		xpath = xpath.replace("categorySectionName", categorySectionName);
		return d.findElements(By.xpath(xpath));

	}

	public WebElement budget_Steps2_BackButton() {
		// back button in step2
		return getVisibileWebElement(d, "budget_Steps2_BackButton", pageName, frameName);

	}

	public WebElement budget_Steps2_BackButtonMobile() {
		return getVisibileWebElement(d, "budget_Steps2_BackButtonMobile", pageName, frameName);
	}

	public boolean isbudget_Steps2_BackButtonMobile() {
		return PageParser.isElementPresent("budget_Steps2_BackButtonMobile", pageName, null);
	}

	public WebElement budget_Steps2_DoneButton() {
		// done button in step2
		return getWebElement(d, "budget_Steps2_DoneButton", pageName, frameName);

	}

	public WebElement budget_Steps2_AddCategoryDD() {
		return getVisibileWebElement(d, "budget_Steps2_AddCategoryDD", pageName, frameName);

	}

	public WebElement budget_Steps2_AddCategoryDDIcon() {
		return getVisibileWebElement(d, "budget_Steps2_AddCategoryDDIcon", pageName, frameName);

	}
	
	public static By budget_Steps2_AddCategoryIcon() {
		return getByObject(pageName, frameName, "budget_Steps2_AddCategoryDDIcon");
	}

	public WebElement budget_Steps2_AddCategoryCurrencyIcon() {
		return getVisibileWebElement(d, "budget_Steps2_AddCategoryCurrencyIcon", pageName, frameName);

	}

	public WebElement budget_Steps2_AddCategoryCurrencyIconMobile() {
		return getWebElement(d, "budget_Steps2_AddCategoryCurrencyIconMobile", pageName, frameName);

	}

	public WebElement budget_Steps2_AddCategoryAmount() {
		return getVisibileWebElement(d, "budget_Steps2_AddCategoryAmount", pageName, frameName);

	}

	public WebElement budget_Steps2_AddCategoryAddButton() {
		return getVisibileWebElement(d, "budget_Steps2_AddCategoryAddButton", pageName, frameName);

	}

	public WebElement budget_Steps2_AddCategoryCloseIcon() {
		return getWebElement(d, "budget_Steps2_AddCategoryCloseIcon", pageName, frameName);

	}

	public List<WebElement> budget_Steps2_AddCategoryCloseIconList() {
		return getWebElements(d, "budget_Steps2_AddCategoryCloseIcon", pageName, frameName);

	}

	public WebElement budget_Steps2_AddCategoryCloseIconMobile() {
		return getWebElement(d, "budget_Steps2_AddCategoryCloseIconMobile", pageName, frameName);

	}

	public List<WebElement> budget_Steps2_AddCategoryCloseIconMobileList() {
		return getWebElements(d, "budget_Steps2_AddCategoryCloseIconMobile", pageName, frameName);

	}

	public WebElement budget_Steps2_legend3monthAvg() {
		return getVisibileWebElement(d, "budget_Steps2_legend3monthAvg", pageName, frameName);

	}

	public WebElement budget_Steps2_legendcatTypeLabel() {
		return getVisibileWebElement(d, "budget_Steps2_legendcatTypeLabel", pageName, frameName);

	}

	public WebElement budget_Steps2_legendcatTypeLabelcolor() {
		return getVisibileWebElement(d, "budget_Steps2_legendcatTypeLabelcolor", pageName, frameName);

	}

	public WebElement budget_Steps2_AddCategoryIcon(String categorySectionName) {
		String xpath = getVisibileElementXPath(d, pageName, frameName, "budget_Steps2_AddCategoryIcon");
		xpath = xpath.replace("categorySectionName", categorySectionName);
		return d.findElement(By.xpath(xpath));

	}

	public WebElement budget_Steps2_AddCategoryLbl(String categorySectionName) {
		String xpath = getVisibileElementXPath(d, pageName, frameName, "budget_Steps2_AddCategoryLbl");
		xpath = xpath.replace("categorySectionName", categorySectionName);
		return d.findElement(By.xpath(xpath));

	}

	public WebElement budget_Steps2_AddCategoryHide(String categorySectionName) {
		String xpath = getVisibileElementXPath(d, pageName, frameName, "budget_Steps2_AddCategoryHide");
		xpath = xpath.replace("categorySectionName", categorySectionName);
		return d.findElement(By.xpath(xpath));

	}

	public List<WebElement> budget_Steps2_AddCategoryHidelist(String categorySectionName) {
		String xpath = getVisibileElementXPath(d, pageName, frameName, "budget_Steps2_AddCategoryHide");
		xpath = xpath.replace("categorySectionName", categorySectionName);
		return d.findElements(By.xpath(xpath));

	}

	public WebElement budget_Steps2_addCatDdCategoryName(String categoryname) {
		String xpath = getVisibileElementXPath(d, pageName, frameName,
				"budget_Steps2_addCatDdCategoryName");
		xpath = xpath.replace("categoryname", categoryname);
		return d.findElement(By.xpath(xpath));

	}

	public List<WebElement> budget_Steps2_addCatDdCategoryNameSearch() {

		return getWebElements(d, "budget_Steps2_addCatDdCategoryNameSearch", pageName, frameName);
	}

	public WebElement budget_Steps2_addCatDdCategoryNameSearchField() {
		return getVisibileWebElement(d, "budget_Steps2_addCatDdCategoryNameSearchField", pageName,
				frameName);

	}

	public WebElement budget_Steps2_AddCategoryDisapear(String categorySectionName) {
		String xpath = getVisibileElementXPath(d, pageName, frameName,
				"budget_Steps2_AddCategoryDisapear");
		xpath = xpath.replace("categorySectionName", categorySectionName);
		return d.findElement(By.xpath(xpath));

	}

	public WebElement budget_Steps2_AddCategoryDisapear1(String categorySectionName) {
		String xpath = getVisibileElementXPath(d, pageName, frameName,
				"budget_Steps2_AddCategoryDisapear1");
		xpath = xpath.replace("categorySectionName", categorySectionName);
		return d.findElement(By.xpath(xpath));

	}

	public List<WebElement> budget_Steps2_BdgAmtErrMsg(String categorySectionName) {
		String xpath = getVisibileElementXPath(d, pageName, frameName, "budget_Steps2_BdgAmtErrMsg");
		xpath = xpath.replace("categorySectionName", categorySectionName);
		return d.findElements(By.xpath(xpath));

	}

	public WebElement budget_Steps2_BdgAmtErrMsgMobile() {
		// list of category section
		return getWebElement(d, "budget_Steps2_BdgAmtErrMsgMobile", pageName, frameName);

	}

	public List<WebElement> budget_Steps2_legendXaxis() {

		return getWebElements(d, "budget_Steps2_legendXaxis", pageName, frameName);

	}

	public List<WebElement> budget_Steps2_legendMonthLinkMobile() {

		return getWebElements(d, "budget_Steps2_legendMonthLinkMobile", pageName, frameName);

	}

	public List<WebElement> budget_Steps2_legendYaxis() {

		return getWebElements(d, "budget_Steps2_legendYaxis", pageName, frameName);

	}

	public List<WebElement> budget_Steps2_addCatDdCategoryList() {

		return getWebElements(d, "budget_Steps2_addCatDdCategoryList", pageName, frameName);

	}

	public List<WebElement> budget_Steps2_TxnDateHeader() {

		return getWebElements(d, "budget_Steps2_TxnDateHeader", pageName, frameName);

	}

	public List<WebElement> budget_Steps2_TxnCategory() {

		return getWebElements(d, "budget_Steps2_TxnCategory", pageName, frameName);

	}

	public List<WebElement> budget_Steps2_TxnAmount() {

		return getWebElements(d, "budget_Steps2_TxnAmount", pageName, frameName);

	}

	public List<WebElement> budget_Steps2_TxnAccountName() {

		return getWebElements(d, "budget_Steps2_TxnAccountName", pageName, frameName);

	}

	public WebElement budget_Steps2_PopupHeader() {
		return getVisibileWebElement(d, "budget_Steps2_PopupHeader", pageName, frameName);

	}

	public List<WebElement> budget_Steps2_PopupHeaderList() {
		return getWebElements(d, "budget_Steps2_PopupHeader", pageName, frameName);

	}

	public WebElement budget_Steps2_PopupBody() {
		return getVisibileWebElement(d, "budget_Steps2_PopupBody", pageName, frameName);

	}

	public WebElement budget_Steps2_PopupButton() {
		return getVisibileWebElement(d, "budget_Steps2_PopupButton", pageName, frameName);

	}

	public WebElement budget_Steps2_PopupCloseIcon() {
		return getVisibileWebElement(d, "budget_Steps2_PopupCloseIcon", pageName, frameName);

	}

	public List<WebElement> budget_Steps2_legendBarClick() {

		return getWebElements(d, "budget_Steps2_legendBarClick", pageName, frameName);

	}

	public WebElement budget_Steps2_LoadMoreTxn() {
		return getVisibileWebElement(d, "budget_Steps2_LoadMoreTxn", pageName, frameName);

	}

	public WebElement budget_Steps2_SaveMyProButton() {
		return getVisibileWebElement(d, "budget_Steps2_SaveMyProButton", pageName, frameName);

	}

	public WebElement budget_Steps2_Quite() {
		return getVisibileWebElement(d, "budget_Steps2_Quite", pageName, frameName);

	}

	public WebElement budget_Steps2_TxnAmountList(String txnAmount) {
		String xpath = getVisibileElementXPath(d, pageName, frameName, "budget_Steps2_TxnAmountList");
		xpath = xpath.replace("txnAmount", txnAmount);
		return d.findElement(By.xpath(xpath));

	}

	public WebElement budget_Steps2_TxnAmountField() {
		return getVisibileWebElement(d, "budget_Steps2_TxnAmountField", pageName, frameName);

	}

	public WebElement budget_Steps2_TxnSaveChnagesButton() {
		return getVisibileWebElement(d, "budget_Steps2_TxnSaveChnagesButton", pageName, frameName);
	}

	// Common method for budget
	public static boolean sendKeys(WebElement element, String value) {
		element.clear();
		element.sendKeys(value);
		element.sendKeys(Keys.TAB);
		return true;
	}

	public void errorMessage(WebElement element) {

	}

	public void clickGetStartButton() {
		click(budget_Getstart_GetStartButton());
	}

	public void clickNextbutton() {
		click(budget_Steps1_NextButton());
	}

	public void clickBackButton() {
		click(budget_Steps2_BackButton());
	}

	public void clickEditButton() {
		click(budget_Steps1_EditButton());
	}

	public String calculateLeftOver() {
		Locale locale = Locale.ENGLISH;
		NumberFormat nf = NumberFormat.getNumberInstance(locale);
		nf.setMinimumFractionDigits(2);
		// nf.setMaximumFractionDigits(2);
		Double totalIncomeAmount = Double.parseDouble(
				budget_Steps2_CategoryTypeAmount(PropsUtil.getDataPropertyValue("Budget_Step2_IncomeInput"))
						.getText().trim().replaceAll("[$\\,]", ""));
		waitForPageToLoad();
		if (Config.appFlag.equalsIgnoreCase("app") || Config.appFlag.equalsIgnoreCase("emulator")) {
			scroolDown();
		}
		Double totalBillAmount = Double.parseDouble(
				budget_Steps2_CategoryTypeAmount(PropsUtil.getDataPropertyValue("Budget_Step2_BillsInput"))
						.getText().trim().replaceAll("[$\\,]", ""));
		Double totalFlexibleAmount = Double.parseDouble(
				budget_Steps2_CategoryTypeAmount(PropsUtil.getDataPropertyValue("Budget_Step2_FlexibleInput"))
						.getText().trim().replaceAll("[$\\,]", ""));
		Double totalspendamount = totalBillAmount + totalFlexibleAmount;
		Double leftoverAmount = totalIncomeAmount - totalspendamount;
		return nf.format(leftoverAmount);

	}

	public String calculateTotalCatAmount(String categorysectionName) {
		Locale locale = Locale.ENGLISH;
		NumberFormat nf = NumberFormat.getNumberInstance(locale);
		nf.setMinimumFractionDigits(2);
		Double totalIncomeAmount = 0.00;
		int categorysize = budget_Steps2_CategoryMonthlyBudgetAmt(categorysectionName).size();
		for (int i = 0; i < categorysize; i++) {
			System.out.println(budget_Steps2_CategoryMonthlyBudgetAmt(categorysectionName).get(i)
					.getAttribute("value").replaceAll("[$\\,]", ""));
			totalIncomeAmount = totalIncomeAmount
					+ Double.parseDouble(budget_Steps2_CategoryMonthlyBudgetAmt(categorysectionName).get(i)
							.getAttribute("value").replaceAll("[$\\,]", ""));
		}
		return nf.format(totalIncomeAmount);
	}

	public boolean createBudgetGroup(String groupName) {
		click(budget_Getstart_GetStartButton());
		waitUntilSpinnerWheelIsInvisible();
		sendKeys(budget_Steps1_BudgetNameField(), groupName);
		waitUntilSpinnerWheelIsInvisible();
		String xpath = getVisibileElementXPath(d, pageName, frameName, "budget_Steps1_AccountCheckBox");
		xpath = xpath.replace("accountContainerName", PropsUtil.getDataPropertyValue("Budget_Step1InvestmentContainerInput"));
		click(By.xpath(xpath));
		waitUntilSpinnerWheelIsInvisible();
		click(budget_Steps1_NextButton());
		waitUntilSpinnerWheelIsInvisible();
		click(budget_Steps2_DoneButton());
		waitUntilSpinnerWheelIsInvisible();
		waitUntilToastMessageIsDisplayed();
		waitUntilToastMessageIsDisappeared();
		return true;
	}

	public void createBudgetClickCreategroupButton(String groupName) {
		// click(budget_Getstart_GetStartButton());
		// waitForPageToLoad();
		// click(budget_Steps1_AccountGroupDropDown());
		click(budget_Steps1_GrpDDCreategroup());
		sendKeys(budget_Steps1_BudgetNameField(), groupName);
		click(budget_Steps1_NextButton());
		waitForPageToLoad();
		click(budget_Steps2_DoneButton());
		waitForPageToLoad();
	}

	public String getMonthMMMM(int month) {
		// get months MMMM formate
		Calendar c = Calendar.getInstance();
		c.add(Calendar.MONTH, month);
		System.out.println(new SimpleDateFormat("MMMM").format(c.getTime()));
		return new SimpleDateFormat("MMMM").format(c.getTime());
	}

	public String getMonthMMM(int month) {
		// get the month value in mmm format
		Calendar c = Calendar.getInstance();
		c.add(Calendar.MONTH, month);
		System.out.println(new SimpleDateFormat("MMM").format(c.getTime()));
		return new SimpleDateFormat("MMM").format(c.getTime());
	}

	public String getYearYY(int month) {
		// get the year in YY format
		Calendar c = Calendar.getInstance();
		c.add(Calendar.MONTH, month);
		System.out.println(new SimpleDateFormat("yy").format(c.getTime()));
		return new SimpleDateFormat("yy").format(c.getTime());
	}

	public List<String> xAixValue(int month) {
		// get the selected time filter x axix avalue
		List<String> monthList = new ArrayList<String>();
		int j = -1;
		for (int i = 1; i < month; i++) {
			if (getMonthMMMM(j).equals("Dec") || getMonthMMMM(j).equals("Jan")) {
				monthList.add(getMonthMMM(j).toUpperCase() + "" + '\'' + getYearYY(j).toUpperCase());

				j = j - 1;
			} else {
				monthList.add(getMonthMMM(j).toUpperCase());

				j = j - 1;
			}

		}
		return monthList;
	}

	public String DateInMMMMFormate(int date) {

		SimpleDateFormat formatter = new SimpleDateFormat("MMMM dd, yyyy ");
		Calendar c = Calendar.getInstance();
		c.add(Calendar.DATE, date);
		System.out.println(formatter.format(c.getTime()));
		return formatter.format(c.getTime());
	}

	public int getNoDaysOfMonth(int monthvalue) {
		String[] monthName = { "January", "February", "March", "April", "May", "June", "July", "August", "September",
				"October", "November", "December" };

		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.MONTH, monthvalue);
		String month = monthName[cal.get(Calendar.MONTH)];

		System.out.println("Month name: " + month);
		int i = 0;
		switch (month) {
		case "January":
			i = -31;
			break;
		case "February":
			i = -28;
			break;
		case "March":
			i = -31;
			break;
		case "April":
			i = -30;
			break;
		case "May":
			i = -31;
			break;
		case "June":
			i = -30;
			break;
		case "July":
			i = -31;
			break;
		case "August":
			i = -31;
			break;
		case "September":
			i = -30;
			break;
		case "October":
			i = -31;
			break;

		case "November":
			i = -30;
			break;
		case "December":
			i = -31;
			break;

		}

		return i;
	}

	public void updateTxn(String txnRowValue, String updateAmount) {
		click(budget_Steps2_TxnAmountList(txnRowValue));
		waitForPageToLoad();
		sendKeys(budget_Steps2_TxnAmountField(), updateAmount);
		waitForPageToLoad();
		click(budget_Steps2_TxnSaveChnagesButton());
		waitFor(2);
		waitForPageToLoad();
		waitUntilToastMessageIsDisappeared();
	}

	public void scroolDown() {
		JavascriptExecutor js = (JavascriptExecutor) d;
		js.executeScript("window.scrollBy(0,1000)");
	}

	public void scroolUp() {
		JavascriptExecutor js = (JavascriptExecutor) d;
		js.executeScript("window.scrollBy(0,-250)");
	}

	public void createGroup(String groupName) {
		click(budget_Steps1_AccountGroupDropDownIcon());
		waitForPageToLoad();
		click(budget_Steps1_GrpDDCreategroup());
		waitForPageToLoad();
		budget_Steps1_BudgetNameField().clear();
		budget_Steps1_BudgetNameField().sendKeys(groupName);
		click(budget_Steps1_NextButton());
	}

	public void editBudgetedAmount(String categorySection, String[] amount, String addCategoryAmount) {
		for (int i = 0; i < amount.length; i++) {
			// click(budget_Steps2_CategoryMonthlyBudgetAmt(categorySection).get(i));
			if (Config.appFlag.equalsIgnoreCase("app") || Config.appFlag.equalsIgnoreCase("emulator")) {
				click(budget_Steps2_CategoryDetailsExpand(categorySection).get(i));
				waitForPageToLoad();
				sendKeys(budget_Steps2_CategoryMonthlyBudgetAmtMobile(), amount[i]);
				click(budget_Steps2_AddCategoryAddButton());
				waitForPageToLoad();
			} else {
				budget_Steps2_CategoryMonthlyBudgetAmt(categorySection).get(i).clear();
				budget_Steps2_CategoryMonthlyBudgetAmt(categorySection).get(i).sendKeys(amount[i]);
			}

		}
		click(budget_Steps2_AddCategoryIcon(categorySection));
		waitForPageToLoad();
		budget_Steps2_AddCategoryAmount().clear();
		budget_Steps2_AddCategoryAmount().sendKeys(addCategoryAmount);
		click(budget_Steps2_AddCategoryAddButton());
		waitForPageToLoad();

	}
	
	public void unCheckTheSelectAllAccountsCheckbox() {
		try {
			if(getAttribute(selectAllAccountsCheckbox, "aria-checked").equals("true")) {
				click(selectAllAccountsCheckbox);
			} else {
				click(selectAllAccountsCheckbox);
				waitFor(1);
				click(selectAllAccountsCheckbox);
			}
		} catch (Exception | AssertionError e) {
			log.error("Unable to un-select all accounts checkbox due to :: " + e.getMessage());
			fail("Unable to un-select all accounts checkbox due to :: " + e.getMessage());
		}
		
	}
	
	public void selectGivenAccountInCreateBudget(String accountName) {
		try {
			String xpath ="//*[@class=\"bgt-setup-accnum\" and contains(text(),\""+accountName+"\")]/parent::*[@class=\"bgt-acc-details\"]/preceding-sibling::*[@class=\"bgt-acc-selection\"]";
			click(By.xpath(xpath));
		} catch (Exception | AssertionError e) {
			log.error("Unable to select given account :"+accountName+" due to :: " + e.getMessage());
			fail("Unable to select given account :"+accountName+" due to :: " + e.getMessage());
		}
	}
	
	public void startBudgetWithGivenGroupNameAndAccounts(String groupName, String[] accounts) {
		try {
			if(isDisplayed(createBudgetButtonInBudgetPage, 0)) {
				click(createBudgetButtonInBudgetPage);
				waitUntilSpinnerWheelIsInvisible();
			}
			click(budget_Getstart_GetStartButton());
			waitUntilSpinnerWheelIsInvisible();
			sendKeys(budget_Steps1_BudgetNameField(), groupName);
			waitUntilSpinnerWheelIsInvisible();
			unCheckTheSelectAllAccountsCheckbox();
			for(String account:accounts) {
				selectGivenAccountInCreateBudget(account);
			}
			waitUntilSpinnerWheelIsInvisible();
			click(budget_Steps1_NextButton());
			waitUntilElementIsVisible(toastMessage, 60);
			waitUntilSpinnerWheelIsInvisible();
			waitUntilToastMessageIsDisappeared();
		} catch (Exception | AssertionError e) {
			log.error("Unable to start budget due to :: " + e.getMessage());
			fail("Unable to start budget due to :: " + e.getMessage());
		}
	}
	
	public void clickOnBudgetDoneButton() {
		try {
			click(doneButton);
			log.info("Clicked on done button in budget");
			waitUntilSpinnerWheelIsInvisible();
		} catch (Exception | AssertionError e) {
			log.error("Unable to click on done button in budget due to :: " + e.getMessage());
			fail("Unable to click on done button in budget due to :: " + e.getMessage());
		}
	}
	
	public void expandGoalsCategory() {
		try {
			if(getAttribute(goalSectionInBudgetCategories, "aria-expanded").equals("false")) {
				click(goalSectionInBudgetCategories);
				log.info("Expanded goals section");
			}
		} catch (Exception | AssertionError e) {
			log.error("Unable to expand goals due to :: " +e.getMessage());
			fail("Unable to expand goals due to :: " +e.getMessage());
		}
	}
	
	public String returnMessageDisplayedInGoalsCategory() {
		try {
			return getText(budgetUnableToCreateGoals);
		} catch (Exception | AssertionError e) {
			log.error("Unable to get message from goals category in create a budget page due to :: " + e.getMessage());
			fail("Unable to get message from goals category in create a budget page due to :: " + e.getMessage());
			return "";
		}
	}
	
	
	public void openBudgetGroupDropdown() {
		try {
			if(getAttribute(budgetGroupNameDropdown, "aria-expanded").equals("false")) {
				click(budgetGroupNameDropdown);
				log.info("Expanded groups dropdown");
			}
		} catch (Exception | AssertionError e) {
			log.error("Unable to expand goals due to :: " +e.getMessage());
			fail("Unable to expand goals due to :: " +e.getMessage());
		}
	}

}
