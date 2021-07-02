/**
* Copyright (c) 2018 Yodlee Inc. All Rights Reserved.
*
* This software is the confidential and proprietary information of
* Yodlee, Inc. Use is subject to license terms.
*
* @author Renuka K
*/
package com.omni.pfm.pages.BudgetV2;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.omni.pfm.config.Config;
import com.omni.pfm.utility.SeleniumUtil;

public class Budget_BudgetSummary_Loc extends SeleniumUtil {
	WebDriver d;
	static String pageName="Budget";
	static String frameName=null;
	public static final By durationDropDown = getByObject(pageName, frameName, "durationDropDown");
	public Budget_BudgetSummary_Loc(WebDriver d) {
		this.d = d;
	}

	public List<WebElement> budget_Summary_CategorySectionCatgeoryName(String categorySectionName) {
		String xpath = getVisibileElementXPath(d, pageName, frameName,
				"budget_Summary_CategorySectionCatgeoryName");
		xpath = xpath.replace("categorySectionName", categorySectionName);
		return d.findElements(By.xpath(xpath));

	}

	public List<WebElement> budget_Summary_CategorySectionCatgeoryBudgetedAmt(String categorySectionName) {
		String xpath = getVisibileElementXPath(d, pageName, frameName,
				"budget_Summary_CategorySectionCatgeoryBudgetedAmt");
		xpath = xpath.replace("categorySectionName", categorySectionName);
		return d.findElements(By.xpath(xpath));

	}

	public List<WebElement> budget_Summary_CategorySectionCatgeorySpentAmt(String categorySectionName) {
		String xpath = getVisibileElementXPath(d, pageName, frameName,
				"budget_Summary_CategorySectionCatgeorySpentAmt");
		xpath = xpath.replace("categorySectionName", categorySectionName);
		return d.findElements(By.xpath(xpath));

	}

	public List<WebElement> budget_Summary_CategorySectionCatgeoryLeftAmt(String categorySectionName) {
		String xpath = getVisibileElementXPath(d, pageName, frameName,
				"budget_Summary_CategorySectionCatgeoryLeftAmt");
		xpath = xpath.replace("categorySectionName", categorySectionName);
		return d.findElements(By.xpath(xpath));

	}

	public WebElement budget_Summary_CategorySectionAddCategoryIcon(String categorySectionName) {
		String xpath = getVisibileElementXPath(d, pageName, frameName,
				"budget_Summary_CategorySectionAddCategoryIcon");
		xpath = xpath.replace("categorySectionName", categorySectionName);
		return d.findElement(By.xpath(xpath));

	}

	public WebElement budget_Summary_CategorySectionAddCategoryLabel(String categorySectionName) {
		String xpath = getVisibileElementXPath(d, pageName, frameName,
				"budget_Summary_CategorySectionAddCategoryLabel");
		xpath = xpath.replace("categorySectionName", categorySectionName);
		return d.findElement(By.xpath(xpath));

	}

	public WebElement budget_Summary_CategorySectionAddCategoryhide(String categorySectionName) {
		String xpath = getVisibileElementXPath(d, pageName, frameName,
				"budget_Summary_CategorySectionAddCategoryhide");
		xpath = xpath.replace("categorySectionName", categorySectionName);
		return d.findElement(By.xpath(xpath));

	}

	public List<WebElement> budget_Summary_CategorySectionAccordian(String categorySectionName) {
		String xpath = getVisibileElementXPath(d, pageName, frameName,
				"budget_Summary_CategorySectionAccordian");
		xpath = xpath.replace("categorySectionName", categorySectionName);
		return d.findElements(By.xpath(xpath));

	}

	public WebElement budget_Summery_AddCategoryerorrMesg() {
		return getVisibileWebElement(d, "budget_Summery_AddCategoryerorrMesg", pageName, frameName);
	}

	public WebElement budget_Summery_DeleteCategoryButton() {
		return getVisibileWebElement(d, "budget_Summery_DeleteCategoryButton", pageName, frameName);
	}

	public WebElement budget_Summery_DeleteCategoryPopUpButton2() {
		return getVisibileWebElement(d, "budget_Summery_DeleteCategoryPopUpButton2", pageName, frameName);
	}

	public WebElement budget_Summery_Viewdetails() {
		return getVisibileWebElement(d, "budget_Summery_Viewdetails", pageName, frameName);
	}

	public WebElement budget_Summery_CloseCatgeoryDDMObile() {
		return getVisibileWebElement(d, "budget_Summery_CloseCatgeoryDDMObile", pageName, frameName);
	}

	public void deleteCategory() {
		click(this.budget_Summery_DeleteCategoryButton());
		waitForPageToLoad();
		click(this.budget_Summery_DeleteCategoryPopUpButton2());
		waitForPageToLoad();
	}

	public WebElement budget_Summery_GroupDropDown() {
		return getVisibileWebElement(d, "budget_Summery_GroupDropDown", pageName, frameName);
	}

	public WebElement budget_Summery_GroupDropDownIcon() {
		return getVisibileWebElement(d, "budget_Summery_GroupDropDownIcon", pageName, frameName);
	}

	public WebElement budget_Summery_TimeFilter() {
		return getVisibileWebElement(d, "budget_Summery_TimeFilter", pageName, frameName);
	}

	public WebElement budget_Summery_TimeFilterIcon() {
		return getVisibileWebElement(d, "budget_Summery_TimeFilterIcon", pageName, frameName);
	}

	public WebElement budget_Summery_TimeFilterDate() {
		return getVisibileWebElement(d, "budget_Summery_TimeFilterDate", pageName, frameName);
	}

	public WebElement budget_Summery_IncomePgrLbl() {
		return getVisibileWebElement(d, "budget_Summery_IncomePgrLbl", pageName, frameName);
	}

	public WebElement budget_Summery_IncomePgrMessage() {
		return getVisibileWebElement(d, "budget_Summery_IncomePgrMessage", pageName, frameName);
	}

	public WebElement budget_Summery_IncomePgrIncomeValue() {
		return getVisibileWebElement(d, "budget_Summery_IncomePgrIncomeValue", pageName, frameName);
	}

	public WebElement budget_Summery_IncomePgrBudgetedValue() {
		return getVisibileWebElement(d, "budget_Summery_IncomePgrBudgetedValue", pageName, frameName);
	}

	public WebElement budget_Summery_SpendingPgrLbl() {
		return getVisibileWebElement(d, "budget_Summery_SpendingPgrLbl", pageName, frameName);
	}

	public WebElement budget_Summery_SpendingPgrMessage() {
		return getVisibileWebElement(d, "budget_Summery_SpendingPgrMessage", pageName, frameName);
	}

	public WebElement budget_Summery_SpendingPgrSpendValue() {
		return getVisibileWebElement(d, "budget_Summery_SpendingPgrSpendValue", pageName, frameName);
	}

	public WebElement budget_Summery_SpendingPgrBudgetedValue() {
		return getVisibileWebElement(d, "budget_Summery_SpendingPgrBudgetedValue", pageName, frameName);
	}

	public WebElement budget_Summery_ActualIncome_CalLbl() {
		return getVisibileWebElement(d, "budget_Summery_ActualIncome_CalLbl", pageName, frameName);
	}

	public WebElement budget_Summery_ActualIncome_CalValuel() {
		return getVisibileWebElement(d, "budget_Summery_ActualIncome_CalValuel", pageName, frameName);
	}

	public WebElement budget_Summery_ActualIncome_CalProBar() {
		return getVisibileWebElement(d, "budget_Summery_ActualIncome_CalProBar", pageName, frameName);
	}

	public WebElement budget_Summery_ActualIncome_CalProBar1() {
		return getVisibileWebElement(d, "budget_Summery_ActualIncome_CalProBar1", pageName, frameName);
	}

	public WebElement budget_Summery_ActualIncome_CalProBar1Mobile() {
		return getVisibileWebElement(d, "budget_Summery_ActualIncome_CalProBar1Mobile", pageName,
				frameName);
	}

	public WebElement budget_Summery_ActualIncome_CalLblMobile() {
		return getVisibileWebElement(d, "budget_Summery_ActualIncome_CalLblMobile", pageName, frameName);
	}

	public WebElement budget_Summery_ActualIncome_CalValuelMobile() {
		return getVisibileWebElement(d, "budget_Summery_ActualIncome_CalValuelMobile", pageName,
				frameName);
	}

	public WebElement budget_Summery_ActualIncome_CalProBarMobile() {
		return getVisibileWebElement(d, "budget_Summery_ActualIncome_CalProBarMobile", pageName,
				frameName);
	}

	public WebElement budget_Summery_ActualSpend_CalLbl() {
		return getVisibileWebElement(d, "budget_Summery_ActualSpend_CalLbl", pageName, frameName);
	}

	public WebElement budget_Summery_ActualSpend_CalValuel() {
		return getVisibileWebElement(d, "budget_Summery_ActualSpend_CalValuel", pageName, frameName);
	}

	public WebElement budget_Summery_ActualSpend_CalProBar() {
		return getVisibileWebElement(d, "budget_Summery_ActualSpend_CalProBar", pageName, frameName);
	}

	public WebElement budget_Summery_ActualSpend_CalLblMobile() {
		return getVisibileWebElement(d, "budget_Summery_ActualSpend_CalLblMobile", pageName, frameName);
	}

	public WebElement budget_Summery_ActualSpend_CalValuelMobile() {
		return getVisibileWebElement(d, "budget_Summery_ActualSpend_CalValuelMobile", pageName, frameName);
	}

	public WebElement budget_Summery_ActualSpend_CalProBarMobile() {
		return getVisibileWebElement(d, "budget_Summery_ActualSpend_CalProBarMobile", pageName, frameName);
	}

	public WebElement budget_Summery_LeftOver_CalLbl() {
		return getVisibileWebElement(d, "budget_Summery_LeftOver_CalLbl", pageName, frameName);
	}

	public WebElement budget_Summery_LeftOver_CalValuel() {
		return getVisibileWebElement(d, "budget_Summery_LeftOver_CalValuel", pageName, frameName);
	}

	public WebElement budget_Summery_LeftOver_CalLblMobile() {
		return getVisibileWebElement(d, "budget_Summery_LeftOver_CalLblMobile", pageName, frameName);
	}

	public WebElement budget_Summery_LeftOver_CalValuelMobile() {
		return getVisibileWebElement(d, "budget_Summery_LeftOver_CalValuelMobile", pageName, frameName);
	}

	public WebElement budget_Summery_ActualSpend_MinusIcon() {
		return getVisibileWebElement(d, "budget_Summery_ActualSpend_MinusIcon", pageName, frameName);
	}

	public WebElement budget_Summery_ActualSpend_EqualIcon() {
		return getVisibileWebElement(d, "budget_Summery_ActualSpend_EqualIcon", pageName, frameName);
	}

	public WebElement budget_Summery_IncomeColor() {
		return getVisibileWebElement(d, "budget_Summery_IncomeColor", pageName, frameName);
	}

	public WebElement budget_Summery_IncomeRemain() {
		return getVisibileWebElement(d, "budget_Summery_IncomeRemain", pageName, frameName);
	}

	public WebElement budget_Summery_SpendColor() {
		return getVisibileWebElement(d, "budget_Summery_SpendColor", pageName, frameName);
	}

	public WebElement budget_Summery_SpendRemain() {
		return getVisibileWebElement(d, "budget_Summery_SpendRemain", pageName, frameName);
	}

	public WebElement budget_Summery_ProgressbarDateHeader() {
		return getVisibileWebElement(d, "budget_Summery_ProgressbarDateHeader", pageName, frameName);
	}

	public String calculateLeftOver() {
		Locale locale = Locale.ENGLISH;
		NumberFormat nf = NumberFormat.getNumberInstance(locale);
		DecimalFormat df2 = new DecimalFormat("#.##");
		nf.setMinimumFractionDigits(2);
		// nf.setMaximumFractionDigits(2);
		Double budgetedIncome = 0.00;
		Double actualSpend = 0.00;
		if (Config.appFlag.equalsIgnoreCase("app") || Config.appFlag.equalsIgnoreCase("emulator")) {
			budgetedIncome = Double.parseDouble(
					this.budget_Summery_ActualIncome_CalValuelMobile().getText().trim().replaceAll("[$\\,]", ""));
			actualSpend = Double.parseDouble(
					this.budget_Summery_ActualSpend_CalValuelMobile().getText().trim().replaceAll("[$\\,]", ""));
		} else {
			budgetedIncome = Double.parseDouble(
					this.budget_Summery_ActualIncome_CalValuel().getText().trim().replaceAll("[$\\,]", ""));
			actualSpend = Double
					.parseDouble(this.budget_Summery_ActualSpend_CalValuel().getText().trim().replaceAll("[$\\,]", ""));
		}
		Double leftover = budgetedIncome - actualSpend;

		return nf.format(leftover);
	}

	public String getdateMMMMYYYY(int month) {
		Calendar c = Calendar.getInstance();
		c.add(Calendar.MONTH, month);
		System.out.println(new SimpleDateFormat("MMMM yyyy").format(c.getTime()));
		return new SimpleDateFormat("MMMM yyyy").format(c.getTime());
	}

	public String monthsStartDate(int month, String dateformate) {
		SimpleDateFormat format2 = new SimpleDateFormat(dateformate);
		Calendar mincld = Calendar.getInstance();
		mincld.set(Calendar.DAY_OF_MONTH, 1); // first day of the year.
		mincld.add(Calendar.MONTH, month);

		System.out.println(format2.format(mincld.getTime()));
		return format2.format(mincld.getTime());
	}

	public String monthStringEndDate(int month, String dateformate) {
		SimpleDateFormat format2 = new SimpleDateFormat(dateformate);
		Calendar maxcld = Calendar.getInstance();
		maxcld.set(Calendar.DAY_OF_MONTH, 365);// last day of the year.
		maxcld.add(Calendar.MONTH, month);
		System.out.println(format2.format(maxcld.getTime()));
		return format2.format(maxcld.getTime());
	}

	public List<WebElement> budget_Summery_CustomedateFilterList() {
		return getWebElements(d, "budget_Summery_CustomedateFilterList", pageName, frameName);
	}

	public WebElement budget_Summery_CustomedateFilterName(String filteName) {
		openDurationDropdown();
		String xpath = getVisibileElementXPath(d, pageName, frameName,
				"budget_Summery_CustomedateFilterName");
		xpath = xpath.replace("filteName", filteName);
		return d.findElement(By.xpath(xpath));
	}

	public void openDurationDropdown() {
		String attribute = getAttribute(durationDropDown, "aria-expanded");
		if(!Objects.isNull(attribute)) {
			if(attribute.toLowerCase().equals("false")) {
				click(durationDropDown);
			}
		}
	}
	
	public List<WebElement> budget_Summery_TrendXaxix() {
		return getWebElements(d, "budget_Summery_TrendXaxix", pageName, frameName);
	}

	public List<WebElement> budget_Summery_TrendYaxix() {
		return getWebElements(d, "budget_Summery_TrendYaxix", pageName, frameName);
	}

	public List<WebElement> budget_Summery_CatTrendsverticalBar() {
		return getWebElements(d, "budget_Summery_CatTrendsverticalBar", pageName, frameName);
	}

	public List<WebElement> budget_Summery_TrendHighChart() {
		return getWebElements(d, "budget_Summery_TrendHighChart", pageName, frameName);
	}

	public WebElement budget_Summery_Trendcheckbox(String categorySection) {
		String xpath = getVisibileElementXPath(d, pageName, frameName, "budget_Summery_Trendcheckbox");
		xpath = xpath.replace("categorySection", categorySection);
		return d.findElement(By.xpath(xpath));

	}

	public List<WebElement> budget_Summery_TrendcheckboxName() {
		return getWebElements(d, "budget_Summery_TrendcheckboxName", pageName, frameName);
	}

	public WebElement budget_Summery_TrendNoncheckboxName() {
		return getVisibileWebElement(d, "budget_Summery_TrendNoncheckboxName", pageName, frameName);
	}

	// public WebElement budget_Summery_CustomedatePreviousYear()
	// {
	// return getVisibileWebElement(d,
	// "budget_Summery_CustomedatePreviousYear", pageName, frameName);
	// }
	// public WebElement budget_Summery_CustomedateNextYear()
	// {
	// return getVisibileWebElement(d,
	// "budget_Summery_CustomedateNextYear", pageName, frameName);
	// }
	public WebElement budget_Summery_CustomedateCurrentYear() {
		return getVisibileWebElement(d, "budget_Summery_CustomedateCurrentYear", pageName, frameName);
	}

	public List<WebElement> budget_Summery_CustomedateStartDateMonth() {
		return getWebElements(d, "budget_Summery_CustomedateStartDateMonth", pageName, frameName);
	}

	public List<WebElement> budget_Summery_CustomedateEndDateMonth() {
		return getWebElements(d, "budget_Summery_CustomedateEndDateMonth", pageName, frameName);
	}

	public List<WebElement> budget_Summery_CategorySection() {
		return getWebElements(d, "budget_Summery_CategorySection", pageName, frameName);
	}

	public List<WebElement> budget_Summery_CategoryDefaultFilterLabel() {
		return getWebElements(d, "budget_Summery_CategoryDefaultFilterLabel", pageName, frameName);
	}

	public List<WebElement> budget_Summery_CategoryDefaultFilterIcon() {
		return getWebElements(d, "budget_Summery_CategoryDefaultFilterIcon", pageName, frameName);
	}

	public List<WebElement> budget_Summery_CategoryBudgetFilterLabel() {
		return getWebElements(d, "budget_Summery_CategoryBudgetFilterLabel", pageName, frameName);
	}

	public List<WebElement> budget_Summery_CategoryBudgetFilterIcon() {
		return getWebElements(d, "budget_Summery_CategoryBudgetFilterIcon", pageName, frameName);
	}

	public List<WebElement> budget_Summery_CategorySpendFilterLabel() {
		return getWebElements(d, "budget_Summery_CategorySpendFilterLabel", pageName, frameName);
	}

	public List<WebElement> budget_Summery_CategorySpendFilterIcon() {
		return getWebElements(d, "budget_Summery_CategorySpendFilterIcon", pageName, frameName);
	}

	public List<WebElement> budget_Summery_CategoryNetFilterLabel() {
		return getWebElements(d, "budget_Summery_CategoryNetFilterLabel", pageName, frameName);
	}

	public List<WebElement> budget_Summery_CategoryNetFilterIcon() {
		return getWebElements(d, "budget_Summery_CategoryNetFilterIcon", pageName, frameName);
	}

	public List<WebElement> budget_Summery_FlexibleCategoryList() {
		return getWebElements(d, "budget_Summery_FlexibleCategoryList", pageName, frameName);
	}

	public List<WebElement> budget_Summery_BillCategoryList() {
		return getWebElements(d, "budget_Summery_BillCategoryList", pageName, frameName);
	}

	public List<WebElement> budget_Summery_IncomeCategoryList() {
		return getWebElements(d, "budget_Summery_IncomeCategoryList", pageName, frameName);
	}

	public List<WebElement> budget_Summery_FlexibleCatAmountList() {
		return getWebElements(d, "budget_Summery_FlexibleCatBudgetedAmtCurr", pageName, frameName);
	}

	public List<WebElement> budget_Summery_BillCatBudgetedAmtCurr() {
		return getWebElements(d, "budget_Summery_BillCatBudgetedAmtCurr", pageName, frameName);
	}

	public List<WebElement> budget_Summery_IncomeCatBudgetedAmtCurr() {
		return getWebElements(d, "budget_Summery_IncomeCatBudgetedAmtCurr", pageName, frameName);
	}

	public List<WebElement> budget_Summery_FlexibleCatBudgetedAmountTextBox() {
		return getWebElements(d, "budget_Summery_FlexibleCatBudgetedAmountTextBox", pageName, frameName);
	}

	public List<WebElement> budget_Summery_BillCatBudgetedAmountTextBox() {
		return getWebElements(d, "budget_Summery_BillCatBudgetedAmountTextBox", pageName, frameName);
	}

	public List<WebElement> budget_Summery_IncomeCatBudgetedAmountTextBox() {
		return getWebElements(d, "budget_Summery_IncomeCatBudgetedAmountTextBox", pageName, frameName);
	}

	public List<WebElement> budget_Summery_FlexibleCatSpendAmount() {
		return getWebElements(d, "budget_Summery_FlexibleCatSpendAmount", pageName, frameName);
	}

	public List<WebElement> budget_Summery_BillCatSpendAmount() {
		return getWebElements(d, "budget_Summery_BillCatSpendAmount", pageName, frameName);
	}

	public List<WebElement> budget_Summery_IncomeCatSpendAmount() {
		return getWebElements(d, "budget_Summery_IncomeCatSpendAmount", pageName, frameName);
	}

	public List<WebElement> budget_Summery_FlexibleCatLeftAmount() {
		return getWebElements(d, "budget_Summery_FlexibleCatLeftAmount", pageName, frameName);
	}

	public List<WebElement> budget_Summery_BillCatLeftAmount() {
		return getWebElements(d, "budget_Summery_BillCatLeftAmount", pageName, frameName);
	}

	public List<WebElement> budget_Summery_IncomeCatLeftAmount() {
		return getWebElements(d, "budget_Summery_IncomeCatLeftAmount", pageName, frameName);
	}

	public List<WebElement> budget_Summery_FlexibleCatAccordion() {
		return getWebElements(d, "budget_Summery_FlexibleCatAccordion", pageName, frameName);
	}

	public List<WebElement> budget_Summery_BillCatAccordion() {
		return getWebElements(d, "budget_Summery_BillCatAccordion", pageName, frameName);
	}

	public List<WebElement> budget_Summery_IncomeCatAccordion() {
		return getWebElements(d, "budget_Summery_IncomeCatAccordion", pageName, frameName);
	}

	public WebElement budget_Summary_FlexibleAddCatLink() {
		return getVisibileWebElement(d, "budget_Summary_FlexibleAddCatLink", pageName, frameName);
	}

	public WebElement budget_Summary_FlexibleAddCatLable() {
		return getVisibileWebElement(d, "budget_Summary_FlexibleAddCatLable", pageName, frameName);
	}

	public WebElement budget_Summary_FlexibleAddCatIcon() {
		return getVisibileWebElement(d, "budget_Summary_FlexibleAddCatIcon", pageName, frameName);
	}

	public WebElement budget_Summary_BillAddCatLink() {
		return getVisibileWebElement(d, "budget_Summary_BillAddCatLink", pageName, frameName);
	}

	public WebElement budget_Summary_BillAddCatLable() {
		return getVisibileWebElement(d, "budget_Summary_BillAddCatLable", pageName, frameName);
	}

	public WebElement budget_Summary_BillAddCatIcon() {
		return getVisibileWebElement(d, "budget_Summary_BillAddCatIcon", pageName, frameName);
	}

	public WebElement budget_Summary_IncomeAddCatLink() {
		return getVisibileWebElement(d, "budget_Summary_IncomeAddCatLink", pageName, frameName);
	}

	public WebElement budget_Summary_IncomeAddCatLable() {
		return getVisibileWebElement(d, "budget_Summary_IncomeAddCatLable", pageName, frameName);
	}

	public WebElement budget_Summary_IncomeAddCatIcon() {
		return getVisibileWebElement(d, "budget_Summary_IncomeAddCatIcon", pageName, frameName);
	}

	public WebElement budget_Summary_FlexibleCatDropDownLable() {
		return getVisibileWebElement(d, "budget_Summary_FlexibleCatDropDownLable", pageName, frameName);
	}

	public WebElement budget_Summary_FlexibleCatDropDownIcon() {
		return getVisibileWebElement(d, "budget_Summary_FlexibleCatDropDownIcon", pageName, frameName);
	}

	public WebElement budget_Summary_BillCatDropDownLable() {
		return getVisibileWebElement(d, "budget_Summary_BillCatDropDownLable", pageName, frameName);
	}

	public WebElement budget_Summary_BillCatDropDownIcon() {
		return getVisibileWebElement(d, "budget_Summary_BillCatDropDownIcon", pageName, frameName);
	}

	public WebElement budget_Summary_IncomeCatDropDownLable() {
		return getVisibileWebElement(d, "budget_Summary_IncomeCatDropDownLable", pageName, frameName);
	}

	public WebElement budget_Summary_IncomeCatDropDownIcon() {
		return getVisibileWebElement(d, "budget_Summary_IncomeCatDropDownIcon", pageName, frameName);
	}

	public List<WebElement> budget_Summary_FlexibleCatDropDownHLCList() {
		return getWebElements(d, "budget_Summary_FlexibleCatDropDownHLCList", pageName, frameName);
	}

	public List<WebElement> budget_Summary_BillCatDropDownHLCList() {
		return getWebElements(d, "budget_Summary_BillCatDropDownHLCList", pageName, frameName);
	}

	public List<WebElement> budget_Summary_IncomeCatDropDownHLCList() {
		return getWebElements(d, "budget_Summary_IncomeCatDropDownHLCList", pageName, frameName);
	}

	public List<WebElement> budget_Summary_FlexibleCatDropDownMLCList() {
		return getWebElements(d, "budget_Summary_FlexibleCatDropDownMLCList", pageName, frameName);
	}

	public List<WebElement> budget_Summary_BillCatDropDownMLCList() {
		return getWebElements(d, "budget_Summary_BillCatDropDownMLCList", pageName, frameName);
	}

	public List<WebElement> budget_Summary_IncomeCatDropDownMLCList() {
		return getWebElements(d, "budget_Summary_IncomeCatDropDownMLCList", pageName, frameName);
	}

	public WebElement budget_Summary_FlexibleCatDropDownMLCName(String catName) {
		String xpath = getVisibileElementXPath(d, pageName, frameName,
				"budget_Summary_FlexibleCatDropDownMLCName");
		xpath = xpath.replaceAll("catName", catName);
		return d.findElement(By.xpath(xpath));
	}

	public WebElement budget_Summary_BillCatDropDownMLCName(String catName) {
		String xpath = getVisibileElementXPath(d, pageName, frameName,
				"budget_Summary_BillCatDropDownMLCName");
		xpath = xpath.replaceAll("catName", catName);
		return d.findElement(By.xpath(xpath));
	}

	public WebElement budget_Summary_IncomeCatDropDownMLCName(String catName) {
		String xpath = getVisibileElementXPath(d, pageName, frameName,
				"budget_Summary_IncomeCatDropDownMLCName");
		xpath = xpath.replaceAll("catName", catName);
		return d.findElement(By.xpath(xpath));
	}

	public WebElement budget_Summary_FlexibleAddCatAmounttextBox() {
		return getVisibileWebElement(d, "budget_Summary_FlexibleAddCatAmounttextBox", pageName, frameName);
	}

	public WebElement budget_Summary_BillAddCatAmounttextBox() {
		return getVisibileWebElement(d, "budget_Summary_BillAddCatAmounttextBox", pageName, frameName);
	}

	public WebElement budget_Summary_IncomeAddCatAmounttextBox() {
		return getVisibileWebElement(d, "budget_Summary_IncomeAddCatAmounttextBox", pageName, frameName);
	}

	public WebElement budget_Summary_FlexibleAddCatAddButton() {
		return getVisibileWebElement(d, "budget_Summary_FlexibleAddCatAddButton", pageName, frameName);
	}

	public WebElement budget_Summary_BillAddCatAddButton() {
		return getVisibileWebElement(d, "budget_Summary_BillAddCatAddButton", pageName, frameName);
	}

	public WebElement budget_Summary_IncomeAddCatAddButton() {
		return getVisibileWebElement(d, "budget_Summary_IncomeAddCatAddButton", pageName, frameName);
	}

	public WebElement budget_Summary_FlexibleAddCatCloseButton() {
		return getVisibileWebElement(d, "budget_Summary_FlexibleAddCatCloseButton", pageName, frameName);
	}

	public WebElement budget_Summary_BillAddCatCloseButton() {
		return getVisibileWebElement(d, "budget_Summary_BillAddCatCloseButton", pageName, frameName);
	}

	public WebElement budget_Summary_IncomeAddCatCloseButton() {
		return getVisibileWebElement(d, "budget_Summary_IncomeAddCatCloseButton", pageName, frameName);
	}

	public String monthStartDate(int month, String dateformate) {
		SimpleDateFormat format = new SimpleDateFormat(dateformate);
		Calendar cal4 = Calendar.getInstance();
		cal4.add(Calendar.MONTH, month);
		cal4.set(Calendar.DATE, 1);
		System.out.println(format.format(cal4.getTime()));
		return format.format(cal4.getTime());
	}

	public String targateDate1(int futureDate) {
		Calendar c = Calendar.getInstance();
		c.add(Calendar.DATE, futureDate);
		System.out.println(new SimpleDateFormat("MM/dd/yyyy").format(c.getTime()));
		return new SimpleDateFormat("MM/dd/yyyy").format(c.getTime());
	}

	public String getdateMMMYYYY(int month) {
		Calendar c = Calendar.getInstance();
		c.add(Calendar.MONTH, month);
		System.out.println(new SimpleDateFormat("MMM yyyy").format(c.getTime()));
		return new SimpleDateFormat("MMM yyyy").format(c.getTime());
	}

	public List<WebElement> budget_Summary_AddCategoryBtn() {
		return getWebElements(d, "budget_Summary_BillAddCatAddBtn", pageName, frameName);
	}

	public WebElement budget_Summery_TrendCardIcon() {
		return getVisibileWebElement(d, "budget_Trend_CardIconMobile", pageName, frameName);
	}

	public WebElement budget_Trend_ViewDetailsBtn() {
		return getVisibileWebElement(d, "budget_Trend_ViewDetailsBtn", pageName, frameName);
	}

	public WebElement budget_Summary_MonthBtnMobile() {
		return getVisibileWebElement(d, "budget_Trend_MonthBtnMobile", pageName, frameName);
	}

	public WebElement budget_Trends_BackIconMobile() {
		return getVisibileWebElement(d, "budget_Trends_BackIconMobile", pageName, frameName);
	}

	public List<WebElement> budget_Summery_TrendMonthDetailsMobile() {
		return getWebElements(d, "budget_Trend_CardMobileDetails", pageName, frameName);
	}

	public WebElement budget_Trend_CategoryLegendActualLbl() {
		return getVisibileWebElement(d, "budget_Trend_CategoryLegendActualLbl", pageName, frameName);
	}

	public List<WebElement> budget_Summery_CategoryTrendHighChart() {
		return getWebElements(d, "budget_Summery_CategoryDetailsBarChart", pageName, frameName);
	}

	public List<WebElement> budget_Summary_CategorySectionIncomeCatgeoryName() {
		return getWebElements(d, "budget_Summary_CategorySectionIncomeCatgeoryName", pageName, frameName);
	}

	public WebElement budget_Summery_DoneMobile() {
		return getVisibileWebElement(d, "budget_Summery_DoneButtonMobile", pageName, frameName);
	}

	public WebElement budget_Summery_BackIconMobile() {
		return getVisibileWebElement(d, "budget_Summery_BackIconMobile", pageName, frameName);
	}

	public WebElement budget_Summery_CustomHeaderMobile() {
		return getVisibileWebElement(d, "budget_Summery_CustomHeaderTextMobile", pageName, frameName);
	}

	public WebElement transactionCustomDateFilterUpdate() {
		return getWebElement(d, "transactionCustomDateFilterUpdate", "Transaction", frameName);
	}

	public WebElement budget_Summery_CustomMonthFromButton() {
		return getVisibileWebElement(d, "budget_Summary_CustomDateCalenderButton", pageName, frameName);
	}

	public List<WebElement> budget_Summery_CustomedatePreviousYear() {
		return getWebElements(d, "budget_Summery_CustomedatePreviousYear", pageName, frameName);
	}

	public WebElement budget_Summery_CustomMonthToButton() {
		return getVisibileWebElement(d, "budget_Summary_CustomDateCalenderButton1", pageName, frameName);
	}

	public List<WebElement> budget_Summery_CustomedateNextYear() {
		return getWebElements(d, "budget_Summery_CustomedateNextYear", pageName, frameName);
	}

	public WebElement budget_Trend_FeatureTour() {
		return getVisibileWebElement(d, "budget_Trend_FeatureTour", pageName, frameName);
	}

	public WebElement budget_Trend_FeatureTourDots() {
		return getVisibileWebElement(d, "budget_Trend_FeatureTourDots", pageName, frameName);
	}

	public WebElement budget_Trend_FeatureTourDots1() {
		return getVisibileWebElement(d, "budget_Trend_FeatureTourDots1", pageName, frameName);
	}

	public WebElement budget_Trend_FeatureTourDots2() {
		return getVisibileWebElement(d, "budget_Trend_FeatureTourDots2", pageName, frameName);
	}

	public WebElement budget_Trend_FeatureTourHeaderStep2Txt() {
		return getVisibileWebElement(d, "budget_Trend_FeatureTourHeaderStep2Txt", pageName, frameName);
	}

	public WebElement budget_Trend_FeatureTourTxt2() {
		return getVisibileWebElement(d, "budget_Trend_FeatureTourTxt2", pageName, frameName);
	}

	public List<WebElement> budget_Trend_FeatureTourHeaderTxt() {
		return getWebElements(d, "budget_Trend_FeatureTourHeaderTxt", pageName, frameName);
	}

	public List<WebElement> budget_Trend_FeatureTourTxt() {
		return getWebElements(d, "budget_Trend_FeatureTourTxt", pageName, frameName);
	}

	public List<WebElement> budget_Trend_CloseIcon() {
		return getWebElements(d, "budget_Trend_CloseIcon", pageName, frameName);
	}

	public List<WebElement> budget_Trend_NextButton() {
		return getWebElements(d, "budget_Trend_NextButton", pageName, frameName);
	}

	public List<WebElement> budget_Trend_BackButton() {
		return getWebElements(d, "budget_Trend_BackButton", pageName, frameName);
	}

	public WebElement budget_Summery_MoreBtn() {
		return getVisibileWebElement(d, "moreBtn_BS_mob", pageName, frameName);
	}

	public List<WebElement> budget_Summary_CategorySectionCatgeoryBudgetedAmt1(String categorySectionName) {
		String xpath = getVisibileElementXPath(d, pageName, frameName,
				"budget_Summary_CategorySectionCatgeoryBudgetedAmt1");
		xpath = xpath.replace("categorySectionName", categorySectionName);
		return d.findElements(By.xpath(xpath));

	}

}
