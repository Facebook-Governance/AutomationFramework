/*******************************************************************************
 * Copyright (c) 2018 Yodlee Inc. All Rights Reserved.
 * 
 * This software is the confidential and proprietary information of
 * Yodlee, Inc. Use is subject to license terms.
 * 
 * @author
 ******************************************************************************/
package com.omni.pfm.pages.BudgetEnhancement;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.omni.pfm.PageProcessor.PageParser;
import com.omni.pfm.utility.PropsUtil;
import com.omni.pfm.utility.SeleniumUtil;

public class Create_Budget_Loc {

	static Logger logger = LoggerFactory.getLogger(Create_Budget_Loc.class);

	private WebDriver d = null;

	private static String pageName = "Budget";

	private static String frameName = null;

	public Create_Budget_Loc(WebDriver d) {
		this.d = d;
	}

	public void refreshFinapp() {
		for (int i = 0; i < 4; i++) {
			SeleniumUtil.refresh(d);
		}
	}

	public WebElement CreateBudgetHeader() {

		return SeleniumUtil.getVisibileWebElement(d, "CreateBudgetHeader_CB", pageName, frameName);
	}

	public WebElement CreateBudgetPlusIcon() {
		return SeleniumUtil.findElement(d, "CreateBudgetPlusIcon_CB", pageName, frameName, 600);// getVisibileWebElement(d,
																								// "CreateBudgetPlusIcon_CB",
																								// pageName, frameName);
	}

	public List<WebElement> AlertSettingsSubheader() {

		return SeleniumUtil.getWebElements(d, "AlertSettingsSubheader_CB", pageName, frameName);

	}

	public List<WebElement> AlertSettingsDescription() {

		return SeleniumUtil.getWebElements(d, "AlertSettingsDescription_CB", pageName, frameName);

	}

	public WebElement QAMonthlyText() {

		return SeleniumUtil.getVisibileWebElement(d, "QAMonthlyText_CB", pageName, frameName);

	}

	public WebElement QAMonthlyDropDown() {

		return SeleniumUtil.getVisibileWebElement(d, "QAMonthlyDropDown_CB", pageName, frameName);

	}

	public List<WebElement> QAMonthlyList() {

		return SeleniumUtil.getWebElements(d, "QAMonthlyList_CB", pageName, frameName);

	}

	public WebElement TickMark() {

		return SeleniumUtil.getVisibileWebElement(d, "TickMark_CB", pageName, frameName);

	}

	public List<WebElement> ToggleButton() {

		return SeleniumUtil.getWebElements(d, "ToggleButton_CB", pageName, frameName);

	}

	public WebElement CancelButton() {

		return SeleniumUtil.getVisibileWebElement(d, "CancelButton_CB", pageName, frameName);

	}

	public WebElement SaveChangesButton() {
		return SeleniumUtil.findElement(d, "SaveChangesButton_CB", pageName, frameName, 5);// getVisibileWebElement(d,
																							// "SaveChangesButton_CB",
																							// pageName, frameName);
	}

	public WebElement SelectBudgetDesc() {
		return SeleniumUtil.findElement(d, "SelectBudgetDesc_CB", pageName, frameName, 5);// getVisibileWebElement(d,
																							// "SelectBudgetDesc_CB",
																							// pageName, frameName);
	}

	public WebElement SelectExistingGroupText() {

		return SeleniumUtil.getVisibileWebElement(d, "SelectExistingGroupText_CB", pageName, frameName);

	}

	public WebElement BudgetDropDown() {

		return SeleniumUtil.getVisibileWebElement(d, "BudgetDropDown_CB", pageName, frameName);

	}

	public WebElement DropDownIcon() {

		return SeleniumUtil.getVisibileWebElement(d, "DropDownIcon_CB", pageName, frameName);

	}

	public WebElement PlusIcon() {

		return SeleniumUtil.getVisibileWebElement(d, "PlusIcon_CB", pageName, frameName);

	}

	public WebElement InfoIcon() {
		return SeleniumUtil.findElement(d, "InfoIcon_CB", pageName, frameName, 5);// getVisibileWebElement(d,
																					// "InfoIcon_CB", pageName,
																					// frameName);
	}

	public WebElement InfoDescription() {

		return SeleniumUtil.getVisibileWebElement(d, "InfoDescription_CB", pageName, frameName);

	}

	public WebElement CloseIcon() {

		return SeleniumUtil.getVisibileWebElement(d, "CloseIcon_CB", pageName, frameName);

	}

	public WebElement ORText() {

		return SeleniumUtil.getVisibileWebElement(d, "ORText_CB", pageName, frameName);

	}

	public WebElement NoGroupIcon() {
		return SeleniumUtil.findElement(d, "NoGroupIcon_CB", pageName, frameName, 5);// getVisibileWebElement(d,
																						// "NoGroupIcon_CB", pageName,
																						// frameName);
	}

	public WebElement NoAccountGroupText() {

		return SeleniumUtil.getVisibileWebElement(d, "NoAccountGroupText_CB", pageName, frameName);

	}

	public WebElement NoAccountGroupDesc() {

		return SeleniumUtil.getVisibileWebElement(d, "NoAccountGroupDesc_CB", pageName, frameName);

	}

	public WebElement NoAccountGroupButton() {
		return SeleniumUtil.findElement(d, "NoAccountGroupButton_CB", pageName, frameName, 5);// getVisibileWebElement(d,
																								// "NoAccountGroupButton_CB",
																								// pageName, frameName);
	}

	public WebElement CreateAccountGroupHeader() {
		return SeleniumUtil.findElement(d, "CreateAccountGroupHeader_CB", pageName, frameName, 5);// getVisibileWebElement(d,
																									// "CreateAccountGroupHeader_CB",
																									// pageName,
																									// frameName);
	}

	public WebElement CreateAccountGroupDesc() {

		return SeleniumUtil.getVisibileWebElement(d, "CreateAccountGroupDesc_CB", pageName, frameName);

	}

	public WebElement GroupNameText() {

		return SeleniumUtil.getVisibileWebElement(d, "GroupNameText_CB", pageName, frameName);

	}

	public WebElement AccountToIncludeText() {

		return SeleniumUtil.getVisibileWebElement(d, "AccountToIncludeText_CB", pageName, frameName);

	}

	public WebElement GroupInputBox() {
		return SeleniumUtil.findElement(d, "GroupInputBox_CB", pageName, frameName, 5);// getVisibileWebElement(d,
																						// "GroupInputBox_CB", pageName,
																						// frameName);
	}

	public List<WebElement> CheckBox() {

		return SeleniumUtil.getWebElements(d, "CheckBox_CB", pageName, frameName);

	}

	public WebElement CreateGroupButton() {

		return SeleniumUtil.getVisibileWebElement(d, "CreateGroupButton_CB", pageName, frameName);

	}

	public WebElement InfoIcon1() {
		return SeleniumUtil.findElement(d, "InfoIcon1_CB", pageName, frameName, 5);// getVisibileWebElement(d,
																					// "InfoIcon1_CB", pageName,
																					// frameName);
	}

	public WebElement InfoText() {

		return SeleniumUtil.getVisibileWebElement(d, "InfoText_CB", pageName, frameName);
	}

	/**
	 * @author sswain1 This method verifies if the element is present for mobile or
	 *         not.
	 * @return
	 */

	public boolean IsInfoTextPresent() {

		return PageParser.isElementPresent("InfoText_CB", pageName, frameName);

	}

	public WebElement InfoHeader() {

		return SeleniumUtil.getVisibileWebElement(d, "InfoHeader_CB", pageName, frameName);
	}

	/**
	 * @author sswain1 This method verifies if the element is present for mobile or
	 *         not.
	 * @return
	 */

	public boolean IsInfoHeaderPresent() {

		return PageParser.isElementPresent("InfoHeader_CB", pageName, frameName);

	}

	public WebElement InfoDescription1() {
		return SeleniumUtil.findElement(d, "InfoDescription1_CB", pageName, frameName, 5);// getVisibileWebElement(d,
																							// "InfoDescription1_CB",
																							// pageName, frameName);
	}

	public List<WebElement> newRowAdded() {

		return SeleniumUtil.getWebElements(d, "budget_row", pageName, frameName);

	}

	public WebElement CloseIcon1() {

		return SeleniumUtil.getVisibileWebElement(d, "CloseIcon1_CB", pageName, frameName);

	}

	public WebElement NextButtonText() {
		return SeleniumUtil.findElement(d, "NextButtonText_CB", pageName, frameName, 5);
	}

	public WebElement NextButtonIcon() {

		return SeleniumUtil.getVisibileWebElement(d, "NextButtonIcon_CB", pageName, frameName);

	}

	public WebElement bgtGetStart() {

		return SeleniumUtil.getVisibileWebElement(d, "bgtGetStart", pageName, frameName);

	}

	public WebElement bgtNextButton() {

		return SeleniumUtil.getWebElement(d, "bgtNextButton", pageName, frameName);

	}

	public WebElement bgtNextButton2() {

		return SeleniumUtil.getWebElement(d, "bgtNextButton2", pageName, frameName);

	}

	public void clickgetStart() {
		SeleniumUtil.click(bgtGetStart());
	}

	public List<WebElement> budgetExcludedAcnts() {
		return SeleniumUtil.getWebElements(d, "budgetExcludedAcnts", pageName, frameName);
	}

	public void creatingExcludeAcntGroup() {
		SeleniumUtil.waitForPageToLoad(3000);
		SeleniumUtil.click(GroupInputBox());
		GroupInputBox().sendKeys(PropsUtil.getDataPropertyValue("ExcludedContainersGroup").trim());

		SeleniumUtil.click(budgetExcludedAcnts().get(0));

		SeleniumUtil.click(CreateGroupButton());
	}

	public WebElement allBudgetedNoNewGroupScreen() {

		return SeleniumUtil.getVisibileWebElement(d, "allBudgetedNoNewGroupScreen", pageName, frameName);

	}

	public void createGroupWithAllAcnt() {
		SeleniumUtil.waitForPageToLoad();
		SeleniumUtil.click(GroupInputBox());
		GroupInputBox().sendKeys(PropsUtil.getDataPropertyValue("GroupWithAllAccount").trim());
		for (int i = 0; i < CheckBox().size(); i++) {
			SeleniumUtil.click(CheckBox().get(i));
		}
		SeleniumUtil.waitForPageToLoad(2000);
		SeleniumUtil.click(CreateGroupButton());
	}

	public WebElement NoAcntsDesertScreen() {
		return SeleniumUtil.getVisibileWebElement(d, "NoAcntsDesertScreen", pageName, frameName);
	}

	public WebElement NoAcntCashContainer() {
		return SeleniumUtil.getVisibileWebElement(d, "NoAcntCashContainer", pageName, frameName);
	}

	public WebElement NoAcntCardContainer() {
		return SeleniumUtil.getVisibileWebElement(d, "NoAcntCardContainer", pageName, frameName);
	}

	public WebElement NoAcntInvestmentContainer() {
		return SeleniumUtil.getVisibileWebElement(d, "NoAcntInvestmentContainer", pageName, frameName);
	}

	public List<WebElement> AcntsContainerLabel() {
		return SeleniumUtil.getWebElements(d, "AcntsContainerLabel", pageName, frameName);
	}

	public List<WebElement> Budget_Step1_AcntsCheckBox() {
		return SeleniumUtil.getWebElements(d, "Budget_Step1_AcntsCheckBox", pageName, frameName);
	}

	public List<WebElement> Budget_Step1_AcntsName() {
		return SeleniumUtil.getWebElements(d, "Budget_Step1_AcntsName", pageName, frameName);
	}

	public List<WebElement> Budget_Step1_AcntsNum() {
		return SeleniumUtil.getWebElements(d, "Budget_Step1_AcntsNum", pageName, frameName);
	}

	public boolean isNoAccountScreenDisplayed() {
		return NoAcntsDesertScreen().isDisplayed();
	}

	public boolean isNoAcntInvestmentContainer() {
		return NoAcntInvestmentContainer().isDisplayed();
	}

	public String NoAcntCashContainerText() {
		return NoAcntCashContainer().getText().trim();
	}

	public String NoAcntCardContainerText() {
		return NoAcntCardContainer().getText().trim();
	}

	public String NoAcntInvestmentContainerText() {
		return NoAcntInvestmentContainer().getText().trim();
	}

	public void clickOnCreateGroupButton() {
		SeleniumUtil.click(CreateGroupButton());

	}

	public WebElement editincome() {
		return SeleniumUtil.getWebElement(d, "budgetEditIncome", "Transaction", null);
	}

	public WebElement editincometext() {
		return SeleniumUtil.getVisibileWebElement(d, "budgeteditincometext", "Transaction", null);
	}

	public WebElement flexibleSpendingEditIcon() {
		return SeleniumUtil.getWebElement(d, "budgetflexiblespendingediticon", "Transaction", null);
	}

	public List<WebElement> editCatAmount() {
		return SeleniumUtil.getWebElements(d, "budgetEditactAmount", "Transaction", null);
	}

	public WebElement doneButton() {
		return SeleniumUtil.getWebElement(d, "budgetnextBtn", "Transaction", null);
	}

	public WebElement createdBudgetheader() {
		return SeleniumUtil.getVisibileWebElement(d, "budgetheader_BDG", "Transaction", null);
	}

	public WebElement incomeSaveButton() {
		return SeleniumUtil.getWebElement(d, "budgetincomeSaveButton", "Transaction", null);
	}

	public WebElement Budget_Step2_IncomeAmount() {
		return SeleniumUtil.getVisibileWebElement(d, "Budget_Step2_IncomeAmount", pageName, null);
	}

	public WebElement Budget_Step2_RBCatAmount() {
		return SeleniumUtil.getVisibileWebElement(d, "Budget_Step2_RBCatAmount", pageName, null);
	}

	public WebElement Budget_Step2_RBTotalAmount() {
		return SeleniumUtil.getVisibileWebElement(d, "Budget_Step2_RBTotalAmount", pageName, null);
	}

	public WebElement Budget_Step3_LeftAftRB() {
		return SeleniumUtil.getVisibileWebElement(d, "Budget_Step3_LeftAftRB", pageName, null);
	}

	public List<WebElement> Budget_Step3_CategoryList() {
		return SeleniumUtil.getWebElements(d, "Budget_Step3_CategoryList", pageName, null);
	}

	public List<WebElement> Budget_Step3_CategoryAmtList() {
		return SeleniumUtil.getWebElements(d, "Budget_Step3_CategoryAmtList", pageName, null);
	}

	public WebElement Budget_Step3_TotalFlexiAmt() {
		return SeleniumUtil.getVisibileWebElement(d, "Budget_Step3_TotalFlexiAmt", pageName, null);
	}

	public WebElement BudgetSummery_BdgIncomeAmount() {
		return SeleniumUtil.getVisibileWebElement(d, "BudgetSummery_BdgIncomeAmount", pageName, null);
	}

	public WebElement BudgetSummery_IncomeLeftAmount() {
		return SeleniumUtil.getVisibileWebElement(d, "BudgetSummery_IncomeLeftAmount", pageName, null);
	}

	public WebElement BudgetSummery_BdgIncomeActualAmount() {
		return SeleniumUtil.getVisibileWebElement(d, "BudgetSummery_BdgIncomeActualAmount", pageName, null);
	}

	public WebElement BudgetSummery_SBLeftAmount() {
		return SeleniumUtil.getVisibileWebElement(d, "BudgetSummery_SBLeftAmount", pageName, null);
	}

	public WebElement BudgetSummery_BdgSBActualAmount() {
		return SeleniumUtil.getVisibileWebElement(d, "BudgetSummery_BdgSBActualAmount", pageName, null);
	}

	public WebElement BudgetSummery_BdgSBAmount() {
		return SeleniumUtil.getVisibileWebElement(d, "BudgetSummery_BdgSBAmount", pageName, null);
	}

	public WebElement Budget_Step3_TotalLeftAmount() {
		return SeleniumUtil.getVisibileWebElement(d, "Budget_Step3_TotalLeftAmount", pageName, null);
	}

	public WebElement Budget_Step3_AddCategoryAmountField() {
		return SeleniumUtil.getVisibileWebElement(d, "Budget_Step3_AddCategoryAmountField", pageName, null);
	}

	public WebElement Budget_Step3_AddCategoryAmountSave() {
		return SeleniumUtil.getVisibileWebElement(d, "Budget_Step3_AddCategoryAmountSave", pageName, null);
	}

	public WebElement Budget_Step3_AddCategoryDropDownValue(String categoryName) {
		String xpath = SeleniumUtil.getVisibileElementXPath(d, pageName, frameName,
				"Budget_Step3_AddCategoryDropDownValue");
		xpath = xpath.replaceAll("CategoryName", categoryName);
		return d.findElement(By.xpath(xpath));
	}

	/*
	 * public WebElement Budget_Step3_AddCategory() { return
	 * SeleniumUtil.getVisibileWebElement(d, "Budget_Step3_AddCategory",pageName,
	 * null); }
	 */
	public WebElement Budget_Step3_AddCategory() {
		return SeleniumUtil.getVisibileWebElement(d, "addSpendingButton", pageName, null);
	}

	public WebElement Budget_Step3_AddCategoryDropDown() {
		return SeleniumUtil.getVisibileWebElement(d, "Budget_Step3_AddCategoryDropDown", pageName, null);
	}

	public List<WebElement> BudgetSummery_CatName() {
		return SeleniumUtil.getWebElements(d, "BudgetSummery_CatName", pageName, null);
	}

	public List<WebElement> BudgetSummery_SpendPer() {
		return SeleniumUtil.getWebElements(d, "BudgetSummery_SpendPer", pageName, null);
	}

	public List<WebElement> BudgetSummery_LeftAmt() {
		return SeleniumUtil.getWebElements(d, "BudgetSummery_LeftAmt", pageName, null);
	}

	public List<WebElement> BudgetSummery_AddBudgetCat() {
		return SeleniumUtil.getWebElements(d, "BudgetSummery_AddBudgetCat", pageName, null);
	}

	public WebElement BudgetSummery_AddBudgetCatDropDown() {
		return SeleniumUtil.getWebElement(d, "BudgetSummery_AddBudgetCatDropDown", pageName, null);
	}

	public WebElement BudgetSummery_AddBudgetCatDropDownName(String MLC) {
		String xpath = SeleniumUtil.getVisibileElementXPath(d, pageName, frameName,
				"BudgetSummery_AddBudgetCatDropDownName");
		xpath = xpath.replaceAll("CategoryName", MLC);
		return d.findElement(By.xpath(xpath));
	}

	public WebElement BudgetSummery_AddBudgetAmtField() {
		return SeleniumUtil.getWebElement(d, "BudgetSummery_AddBudgetAmtField", pageName, null);
	}

	public WebElement BudgetSummery_AddBudgetSave() {
		return SeleniumUtil.getWebElement(d, "BudgetSummery_AddBudgetSave", pageName, null);
	}

	public WebElement BudgetSummery_EditBudgetIcon() {
		return SeleniumUtil.getWebElement(d, "BudgetSummery_EditBudgetIcon", pageName, null);
	}

	public WebElement BudgetSummery_EditBudgetAmt() {
		return SeleniumUtil.getWebElement(d, "BudgetSummery_EditBudgetAmt", pageName, null);
	}

	public WebElement BudgetSummery_EditBudgetSave() {
		return SeleniumUtil.getWebElement(d, "BudgetSummery_EditBudgetSave", pageName, null);
	}

	public WebElement budgetCategoriesRow(String catName) {
		String catRow = SeleniumUtil.getVisibileElementXPath(d, "Transaction", null, "budgetCategoriesRow");
		catRow = catRow.replaceAll("catName", catName);
		return d.findElement(By.xpath(catRow));
	}

	public WebElement Budget_Step3_CategoryName(String catName) {
		String catRow = SeleniumUtil.getVisibileElementXPath(d, pageName, null, "Budget_Step3_CategoryName");
		catRow = catRow.replaceAll("CategoryName", catName);
		return d.findElement(By.xpath(catRow));
	}

	public WebElement BudgetSummery_Setting() {
		return SeleniumUtil.getWebElement(d, "BudgetSummery_Setting", pageName, null);
	}

	public WebElement BudgetSummery_SettingAddCategoryToBudget() {
		return SeleniumUtil.getWebElement(d, "BudgetSummery_SettingAddCategoryToBudget", pageName, null);
	}

	public WebElement BudgetSummery_SettingAddCategoryDropDown() {
		return SeleniumUtil.getWebElement(d, "BudgetSummery_SettingAddCategoryDropDown", pageName, null);
	}

	public WebElement BudgetSummery_SettingAddCategoryDropDownList(String catName) {
		String catRow = SeleniumUtil.getVisibileElementXPath(d, pageName, null,
				"BudgetSummery_SettingAddCategoryDropDownList");
		catRow = catRow.replaceAll("CategoryName", catName);
		return d.findElement(By.xpath(catRow));
	}

	public WebElement BudgetSummery_SettingAddCategroyAmt() {
		return SeleniumUtil.getWebElement(d, "BudgetSummery_SettingAddCategroyAmt", pageName, null);
	}

	public WebElement BudgetSummery_SettingAddCategroySave() {
		return SeleniumUtil.getWebElement(d, "BudgetSummery_SettingAddCategroySave", pageName, null);
	}

	public WebElement BudgetSummery_BackToBudget() {
		return SeleniumUtil.getWebElement(d, "BudgetSummery_BackToBudget", pageName, null);
	}

	public WebElement BudgetSummery_SttingAccountGroup() {
		return SeleniumUtil.getWebElement(d, "BudgetSummery_SttingAccountGroup", pageName, null);
	}

	public WebElement BudgetSummery_SttingAccountGroupEdit() {
		return SeleniumUtil.getWebElement(d, "BudgetSummery_SttingAccountGroupEdit", pageName, null);
	}

	public WebElement BudgetSummery_SettingSaveChangesButton() {
		return SeleniumUtil.getWebElement(d, "BudgetSummery_SettingSaveChangesButton", pageName, null);
	}

	public boolean creatBudget(String incomeAmount, String categoryAmounts) {
		WebDriverWait wait = new WebDriverWait(d, 60);

		if (this.isMobilGetStartedButtonBudget_Present()) {
			SeleniumUtil.click(this.Mobile_GetStartedBtn_Budget());
		} else {
			WebElement getStartEle = wait.until(ExpectedConditions.visibilityOf(this.bgtGetStart()));
			SeleniumUtil.click(getStartEle);
		}

		for (int i = this.Budget_Step1_AcntsCheckBox().size() - 3; i < this.Budget_Step1_AcntsCheckBox().size(); i++) {
			SeleniumUtil.click(this.Budget_Step1_AcntsCheckBox().get(i));
		}

		if (this.isMobilNextButtonBudget_Present()) {
			SeleniumUtil.click(this.MobileNextBtn_Budget_mob());
		} else {
			WebElement nextBtn = wait.until(ExpectedConditions.visibilityOf(bgtNextButton()));
			SeleniumUtil.click(nextBtn);
		}
		SeleniumUtil.waitForPageToLoad();

		if (this.isMobilEditIncomeButtonBudget_Present()) {
			// SeleniumUtil.click(this.MobileEditIncomeBtn_Budget_mob());
			WebElement edit = wait.until(ExpectedConditions.visibilityOf(MobileEditIncomeBtn_Budget_mob()));
			SeleniumUtil.click(edit);
		} else {
			WebElement edit = wait.until(ExpectedConditions.visibilityOf(editincome()));
			SeleniumUtil.click(edit);
		}
		WebElement editIncomeFiled = wait.until(ExpectedConditions.visibilityOf(editincometext()));
		editIncomeFiled.clear();
		editIncomeFiled.sendKeys(incomeAmount);
		editIncomeFiled.sendKeys(Keys.TAB);
		SeleniumUtil.waitForPageToLoad(1500);
		SeleniumUtil.click(incomeSaveButton());

		if (this.isMobilNextButton2Budget_Present()) {
			SeleniumUtil.click(this.MobileNextBtn2_Budget_mob());
		} else {
			SeleniumUtil.click(bgtNextButton2());
			SeleniumUtil.waitForPageToLoad();
		}
		SeleniumUtil.click(flexibleSpendingEditIcon());
		wait.until(ExpectedConditions.visibilityOf(editCatAmount().get(0)));
		for (int i = 1; i < editCatAmount().size(); i++) {
			editCatAmount().get(i).clear();
			editCatAmount().get(i).sendKeys(categoryAmounts);
		}
		editCatAmount().get(editCatAmount().size() - 1).sendKeys(Keys.TAB);
		if (this.isMobileIncomeSaveBtn_Present()) {
			SeleniumUtil.click(this.MobileIncomeSaveBtn());
		} else {
			WebElement nxtSave = wait.until(ExpectedConditions.visibilityOf(incomeSaveButton()));
			SeleniumUtil.click(nxtSave);
		}
		// WebElement donebtn=wait.until(ExpectedConditions.visibilityOf(doneButton()));
		// SeleniumUtil.click(donebtn);
		if (this.isMobilNextButton2Budget_Present()) {
			SeleniumUtil.click(this.MobileNextBtn2_Budget_mob());
		} else {
			SeleniumUtil.click(doneButton());
			SeleniumUtil.waitForPageToLoad();
		}
		if (this.isMobileBudgetName_Present()) {
			return MobileBudgetName().isDisplayed();

		} else {
			return createdBudgetheader().isDisplayed();
		}
	}

	public void creatAccountGroupBudget(String incomeAmount, String categoryAmounts) {
		WebDriverWait wait = new WebDriverWait(d, 60);
		SeleniumUtil.click(bgtNextButton());
		SeleniumUtil.waitForPageToLoad();
		SeleniumUtil.click(editincome());
		SeleniumUtil.waitForPageToLoad();
		WebElement editIncomeFiled = wait.until(ExpectedConditions.visibilityOf(editincometext()));
		editIncomeFiled.clear();
		editIncomeFiled.sendKeys(incomeAmount);
		editIncomeFiled.sendKeys(Keys.TAB);

		if (this.isMobileIncomeSaveBtn_Present()) {
			SeleniumUtil.click(this.MobileIncomeSaveBtn());
		} else {
			WebElement incomeSave = wait.until(ExpectedConditions.visibilityOf(incomeSaveButton()));
			SeleniumUtil.click(incomeSave);
		}

		SeleniumUtil.waitForPageToLoad();

		if (this.isMobilNextButton2Budget_Present()) {
			SeleniumUtil.click(this.MobileNextBtn2_Budget_mob());
		} else {
			WebElement incomenextbtn = wait.until(ExpectedConditions.visibilityOf(bgtNextButton2()));
			SeleniumUtil.click(incomenextbtn);
			SeleniumUtil.waitForPageToLoad();
		}

		if (this.isMobileFlexibleSpendEdit_Present()) {
			SeleniumUtil.click(this.MobileFlexibleSpendEdit());
		} else {

			WebElement flexspending = wait.until(ExpectedConditions.visibilityOf(flexibleSpendingEditIcon()));
			SeleniumUtil.click(flexspending);
			SeleniumUtil.waitForPageToLoad();
		}
		wait.until(ExpectedConditions.visibilityOf(editCatAmount().get(0)));
		for (int i = 1; i < editCatAmount().size(); i++) {
			editCatAmount().get(i).clear();
			editCatAmount().get(i).sendKeys(categoryAmounts);
		}

		editCatAmount().get(editCatAmount().size() - 1).sendKeys(Keys.TAB);

		if (this.isMobileIncomeSaveBtn_Present()) {
			SeleniumUtil.click(this.MobileIncomeSaveBtn());
		} else {
			WebElement nxtSave = wait.until(ExpectedConditions.visibilityOf(incomeSaveButton()));
			SeleniumUtil.click(nxtSave);
		}

		SeleniumUtil.waitForPageToLoad();

		if (this.isMobilNextButton2Budget_Present()) {
			SeleniumUtil.click(this.MobileNextBtn2_Budget_mob());
		} else {
			WebElement donebtn = wait.until(ExpectedConditions.visibilityOf(doneButton()));
			SeleniumUtil.click(donebtn);
			SeleniumUtil.waitForPageToLoad();

		}
	}

	public void selectBudgetAccount() {
		WebDriverWait wait = new WebDriverWait(d, 60);
		WebElement getStartEle = wait.until(ExpectedConditions.visibilityOf(this.bgtGetStart()));
		SeleniumUtil.click(getStartEle);
		SeleniumUtil.waitForPageToLoad();
		WebElement nextBtn = wait.until(ExpectedConditions.visibilityOf(nextButton1()));

		for (int i = this.Budget_Step1_AcntsCheckBox().size() - 3; i < this.Budget_Step1_AcntsCheckBox().size(); i++) {
			SeleniumUtil.click(this.Budget_Step1_AcntsCheckBox().get(i));
		}
		SeleniumUtil.click(nextBtn);
		SeleniumUtil.waitForPageToLoad();

	}

	private WebElement nextButton1() {
		return SeleniumUtil.getVisibileWebElement(d, "nextButton1", "Budget", null);
	}

	public void addBudgetCategory(int addbutton, String MLC, String amount) {
		SeleniumUtil.click(this.BudgetSummery_AddBudgetCat().get(addbutton));
		SeleniumUtil.waitForPageToLoad();
		SeleniumUtil.click(this.BudgetSummery_AddBudgetCatDropDown());
		SeleniumUtil.waitForPageToLoad();
		SeleniumUtil.click(this.BudgetSummery_AddBudgetCatDropDownName(MLC));
		SeleniumUtil.waitForPageToLoad();
		SeleniumUtil.click(this.BudgetSummery_AddBudgetAmtField());
		this.BudgetSummery_AddBudgetAmtField().clear();
		this.BudgetSummery_AddBudgetAmtField().sendKeys(amount);
		SeleniumUtil.waitForPageToLoad();
		SeleniumUtil.click(this.BudgetSummery_AddBudgetSave());
		SeleniumUtil.waitForPageToLoad();
	}

	public void unselectedCategorySelect(int addbutton, String MLC) {
		SeleniumUtil.click(this.BudgetSummery_AddBudgetCat().get(addbutton));
		SeleniumUtil.waitForPageToLoad();
		SeleniumUtil.click(this.BudgetSummery_AddBudgetCatDropDown());
		SeleniumUtil.waitForPageToLoad(7000);
		SeleniumUtil.click(this.BudgetSummery_AddBudgetCatDropDownName(MLC));
		SeleniumUtil.waitForPageToLoad();

	}

	public void editBudgetCatAmount(String HLC, String amount) {
		SeleniumUtil.click(this.budgetCategoriesRow(HLC));
		SeleniumUtil.waitForPageToLoad();
		SeleniumUtil.click(this.BudgetSummery_EditBudgetIcon());
		SeleniumUtil.waitForPageToLoad();
		this.BudgetSummery_EditBudgetAmt().clear();
		this.BudgetSummery_EditBudgetAmt().sendKeys(amount);
		SeleniumUtil.waitForPageToLoad();
		SeleniumUtil.click(this.BudgetSummery_EditBudgetSave());
		SeleniumUtil.waitForPageToLoad();
	}

	public void addCategoryToBudget(String categoryName, String amount) {
		SeleniumUtil.click(this.BudgetSummery_Setting());
		SeleniumUtil.waitForPageToLoad();
		SeleniumUtil.click(this.BudgetSummery_SettingAddCategoryToBudget());
		SeleniumUtil.waitForPageToLoad();
		SeleniumUtil.click(this.BudgetSummery_SettingAddCategoryDropDown());
		SeleniumUtil.waitForPageToLoad();
		SeleniumUtil.click(this.BudgetSummery_SettingAddCategoryDropDownList(categoryName));
		SeleniumUtil.waitForPageToLoad();
		this.BudgetSummery_SettingAddCategroyAmt().clear();
		this.BudgetSummery_SettingAddCategroyAmt().sendKeys(amount);
		SeleniumUtil.waitForPageToLoad();
		SeleniumUtil.click(this.BudgetSummery_SettingAddCategroySave());
		SeleniumUtil.waitForPageToLoad();
		SeleniumUtil.click(this.BudgetSummery_SettingSaveChangesButton());
		SeleniumUtil.waitForPageToLoad();
		SeleniumUtil.click(this.BudgetSummery_BackToBudget());
		SeleniumUtil.waitForPageToLoad();
	}

	public void clickAccountgroupEdit() {
		SeleniumUtil.click(this.BudgetSummery_Setting());
		SeleniumUtil.waitForPageToLoad();
		SeleniumUtil.click(this.BudgetSummery_SttingAccountGroup());
		SeleniumUtil.waitForPageToLoad();
		SeleniumUtil.click(this.BudgetSummery_SttingAccountGroupEdit());
		SeleniumUtil.waitForPageToLoad();
	}

	public void addCatgeoryStep3(String category, String categoryAmount) {
		SeleniumUtil.click(this.Budget_Step3_AddCategory());
		SeleniumUtil.waitForPageToLoad();
		SeleniumUtil.click(this.Budget_Step3_AddCategoryDropDown());
		SeleniumUtil.waitForPageToLoad();
		SeleniumUtil.click(this.Budget_Step3_AddCategoryDropDownValue(category));
		SeleniumUtil.waitForPageToLoad();
		this.Budget_Step3_AddCategoryAmountField().clear();
		this.Budget_Step3_AddCategoryAmountField().sendKeys(categoryAmount);
		SeleniumUtil.waitForPageToLoad();
		SeleniumUtil.click(this.Budget_Step3_AddCategoryAmountSave());
		SeleniumUtil.waitForPageToLoad();
	}

	// added by Mallika for Mobile and web Group Stickyness

	public WebElement Mobile_GetStartedBtn_Budget() {
		return SeleniumUtil.getWebElement(d, "GetStartedButton_Budget_mob", "Budget", null);
	}

	public boolean isMobilGetStartedButtonBudget_Present() {
		return PageParser.isElementPresent("GetStartedButton_Budget_mob", "Budget", null);
	}

	public boolean isMobilNextButtonBudget_Present() {
		return PageParser.isElementPresent("NextBtn_Budget_mob", "Budget", null);
	}

	public WebElement MobileNextBtn_Budget_mob() {
		return SeleniumUtil.getWebElement(d, "NextBtn_Budget_mob", "Budget", null);
	}

	public boolean isMobilNextButton2Budget_Present() {
		return PageParser.isElementPresent("NextBtn2_Budget_mob", "Budget", null);
	}

	public WebElement MobileNextBtn2_Budget_mob() {
		return SeleniumUtil.getWebElement(d, "NextBtn2_Budget_mob", "Budget", null);
	}

	public boolean isMobilEditIncomeButtonBudget_Present() {
		return PageParser.isElementPresent("EditIncomeBtn_Budget_mob", "Budget", null);
	}

	public WebElement MobileEditIncomeBtn_Budget_mob() {
		return SeleniumUtil.getWebElement(d, "EditIncomeBtn_Budget_mob", "Budget", null);
	}

	public boolean isMobilEditButton2Budget_Present() {
		return PageParser.isElementPresent("EditBtn2_mob", "Budget", null);
	}

	public boolean isMobileIncomeSaveBtn_Present() {
		return PageParser.isElementPresent("IncomeSavebtn_mob", "Budget", null);
	}

	public WebElement MobileIncomeSaveBtn() {
		return SeleniumUtil.getWebElement(d, "IncomeSavebtn_mob", "Budget", null);
	}

	public boolean isMobileBudgetName_Present() {
		return PageParser.isElementPresent("BudgetName_mob", "Budget", null);
	}

	public WebElement MobileBudgetName() {
		return SeleniumUtil.getWebElement(d, "BudgetName_mob", "Budget", null);
	}

	public boolean isMobileFlexibleSpendEdit_Present() {
		return PageParser.isElementPresent("FlexibleSpendingEdit_mob", "Budget", null);
	}

	public WebElement MobileFlexibleSpendEdit() {
		return SeleniumUtil.getWebElement(d, "FlexibleSpendingEdit_mob", "Budget", null);
	}

}
