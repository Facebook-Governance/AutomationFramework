/*******************************************************************************
 * Copyright (c) 2018 Yodlee Inc. All Rights Reserved.
 * 
 * This software is the confidential and proprietary information of
 * Yodlee, Inc. Use is subject to license terms.
 * 
 * @author
 ******************************************************************************/
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

public class Budget_Income_And_Bill_Summery_Loc {

	static Logger logger = LoggerFactory.getLogger(Budget_Income_And_Bill_Summery_Loc.class);
	public WebDriver d = null;
	static WebElement we;

	String pageName = "Budget";
	String frameName = null;

	public Budget_Income_And_Bill_Summery_Loc(WebDriver d) {
		this.d = d;
	}

	public WebElement budgetHeader() {
		return SeleniumUtil.getVisibileWebElement(d, "budgetHeader_BIABS", pageName, frameName);
	}

	public WebElement settingsIcon() {
		return SeleniumUtil.getVisibileWebElement(d, "settingsIcon_BIABS", pageName, frameName);
	}

	public WebElement settingsHeader() {
		return SeleniumUtil.getVisibileWebElement(d, "settingsHeader_BIABS", pageName, frameName);
	}

	public WebElement moreIcon() {
		return SeleniumUtil.getVisibileWebElement(d, "moreIcon_BIABS", pageName, frameName);
	}

	public WebElement featureTourIcon() {
		return SeleniumUtil.getVisibileWebElement(d, "featureTourIcon_BIABS", pageName, frameName);
	}

	public WebElement featureTourText() {
		return SeleniumUtil.getVisibileWebElement(d, "featureTourText_BIABS", pageName, frameName);
	}

	public WebElement BudgetAndAccountsGroups() {
		return SeleniumUtil.getVisibileWebElement(d, "BudgetAndAccountsGroups_BIABS", pageName, frameName);
	}

	public WebElement overviewText() {
		return SeleniumUtil.getVisibileWebElement(d, "overviewText_BIABS", pageName, frameName);
	}

	public WebElement BudgetCreatedText() {
		return SeleniumUtil.getVisibileWebElement(d, "BudgetCreatedText_BIABS", pageName, frameName);
	}

	public WebElement seeYourActualText() {
		return SeleniumUtil.getVisibileWebElement(d, "seeYourActualText_BIABS", pageName, frameName);
	}

	public WebElement notificationFeatureTourText() {
		return SeleniumUtil.getVisibileWebElement(d, "NotificationText_BIABS", pageName, frameName);
	}

	public WebElement featureTourCloseIcon() {
		return SeleniumUtil.getVisibileWebElement(d, "featureTourCloseIcon_BIABS", pageName, frameName);
	}

	public WebElement featureTourCloseIcon1() {
		return SeleniumUtil.getVisibileWebElement(d, "featureTourCloseIcon1_BIABS", pageName, frameName);
	}

	public WebElement featureTourNextButton(int n) {

		return d.findElement(By.xpath("(//a[contains(@class,'joyride-next-tip')])[" + n + "]"));
	}

	public WebElement featureTourBackButton() {

		return d.findElement(By.xpath("//a[contains(@class,'joyride-prev-tip')]"));
	}

	/**
	 * @author sswain1 To check whether featureTourBackButton element is present in
	 *         Page.XML
	 * @return
	 */
	public boolean isFeatureTourBackButtonPresent() {
		return PageParser.isElementPresent("featureTourBackButton", pageName, frameName);
	}

	public WebElement budgetDetailsText() {
		return SeleniumUtil.getVisibileWebElement(d, "budgetDetailsText_BIABS", pageName, frameName);
	}

	public WebElement seeYourBudgetText() {
		return SeleniumUtil.getVisibileWebElement(d, "seeYourBudgetText_BIABS", pageName, frameName);
	}

	public WebElement budgetDetailsNextButton() {
		return SeleniumUtil.getVisibileWebElement(d, "budgetDetailsNextButton_BIABS", pageName, frameName);
	}

	public WebElement budgetDetailsBackButton(int n) {

		return d.findElement(By.xpath("(//a[contains(@class,'joyride-prev-tip')])[" + n + "]"));
	}

	public WebElement settingsText() {
		return SeleniumUtil.getVisibileWebElement(d, "settingsText_BIABS", pageName, frameName);
	}

	public WebElement manageAccountsText() {
		return SeleniumUtil.getVisibileWebElement(d, "manageAccountsText_BIABS", pageName, frameName);
	}

	public WebElement settingsBackButton() {
		return SeleniumUtil.getVisibileWebElement(d, "settingsBackButton_BIABS", pageName, frameName);
	}

	public WebElement settingsNextButton() {
		return SeleniumUtil.getVisibileWebElement(d, "settingsNextButton_BIABS", pageName, frameName);
	}

	public WebElement monthText() {
		return SeleniumUtil.getVisibileWebElement(d, "monthText_BIABS", pageName, frameName);
	}

	public WebElement incomeHeader() {
		return SeleniumUtil.getVisibileWebElement(d, "incomeHeader_BIABS", pageName, frameName);
	}

	public WebElement incomeAndSpendingHeader() {
		return SeleniumUtil.getVisibileWebElement(d, "incomeAndSpendingHeader_BIABS", pageName, frameName);
	}

	/**
	 * @author sswain1
	 * @param categoryName
	 * @return
	 */
	public boolean IsIncomeAndSpendingHeaderPresent() {
		return PageParser.isElementPresent("incomeAndSpendingHeader_BIABS", pageName, frameName);
	}

	public String getLeftAmountByCategoryName(String categoryName) {
		String amount = d.findElement(By.xpath("//div[@id='progressView']//span[contains(text(),'" + categoryName
				+ "')]//following::span[contains(@class,'amount')][1]")).getText();
		return amount;
	}

	public boolean verifyLeftAmountTextIsDisplayed(String categoryName) {
		return d.findElement(By.xpath("//div[@id='progressView']//span[contains(text(),'" + categoryName
				+ "')]//following::span[contains(text(),'Left') or contains (text(),'left')][1]")).isDisplayed();

	}

	public WebElement billAndSpendingHeader() {
		return SeleniumUtil.getVisibileWebElement(d, "billAndSpendingHeader_BIABS", pageName, frameName);
	}

	public WebElement viewAllAccountsButton() {
		return SeleniumUtil.getVisibileWebElement(d, "viewAllAccountsButton_BIABS", pageName, frameName);
	}

	public String getActualAmountByCategory(String categoryName) {
		String amount = d.findElement(By.xpath("//div[@id='progressView']//span[contains(text(),'" + categoryName
				+ "')]//following::span[contains(@class,'amount')][2]")).getText();
		return amount;
	}

	public boolean verifyActualAmountTextIsDisplayed(String categoryName) {
		return d.findElement(By.xpath("//div[@id='progressView']//span[contains(text(),'" + categoryName
				+ "')]//following::span[contains(text(),'Actual') or contains (text(),'actual')][1]")).isDisplayed();
	}

	public String getBudgetedAmountByCategory(String categoryName) {
		String amount = d.findElement(By.xpath("//div[@id='progressView']//span[contains(text(),'" + categoryName
				+ "')]//following::span[contains(@class,'amount')][3]")).getText();
		return amount;
	}

	public boolean verifyBudgetAmountTextIsDisplayed(String categoryName) {
		return d.findElement(By.xpath("//div[@id='progressView']//span[contains(text(),'" + categoryName
				+ "')]//following::span[contains(text(),'Budgeted') or contains (text(),'budgeted')][1]"))
				.isDisplayed();

	}

	public WebElement notificationHeader() {
		return SeleniumUtil.getVisibileWebElement(d, "notificationHeader_BIABS", pageName, frameName);
	}

	public WebElement howImDoingText() {
		return SeleniumUtil.getVisibileWebElement(d, "howImDoingText_BIABS", pageName, frameName);
	}

	public WebElement budgetAmountText() {
		return SeleniumUtil.getVisibileWebElement(d, "budgetAmountText_BIABS", pageName, frameName);
	}

	public WebElement budgetDetailsHeader() {
		return SeleniumUtil.getVisibileWebElement(d, "budgetDetailsHeader_BIABS", pageName, frameName);
	}

	public WebElement sortByText() {
		return SeleniumUtil.getVisibileWebElement(d, "sortByText_BIABS", pageName, frameName);
	}

	public WebElement dropDown() {
		return SeleniumUtil.getVisibileWebElement(d, "dropDown_BIABS", pageName, frameName);
	}

	public void clickAddIconByNumber(int budgetTypeNumber) {
		WebElement element = d.findElement(
				By.xpath("//div[@id='budget-list']/div[" + budgetTypeNumber + "]/div[@id='budgetDetails']//a"));
		element.click();

	}

	public boolean isBudgetDetailsHeaderPresent() {
		return PageParser.isElementPresent("budgetDetailsHeader_BIABS", pageName, null);
	}

	/**
	 * @author sswain1
	 * @return
	 */
	public boolean isClickFlexiAddIconByNumber() {
		return PageParser.isElementPresent("flexiAddbuton", pageName, frameName);

	}

	/**
	 * @author sswain1
	 * @return
	 */
	public WebElement flexiSpendingAddIcon() {
		By by = SeleniumUtil.getByObject(pageName, null, "FlexiSpendingAddIcon");
		return SeleniumUtil.findElement(d, by, 5);// waitUntilElementVisible(d, flexiSpendObject, 2);
	}

	public WebElement viewDetailButtonInBudget() {
		return SeleniumUtil.getVisibileWebElement(d, "viewDetailButton", pageName, frameName);
	}

	public boolean isviewDetailButtonPresent() {
		return PageParser.isElementPresent("viewDetailButton", pageName, null);
	}

	public void clickAddIconByBudgetType(String budgetType) {
		WebElement element = (WebElement) d.findElements(
				By.xpath("//div[@id='budget-list']//span[contains(text(),'" + budgetType + "')]//following::a[1]"));
		element.click();

	}

	public WebElement addBudgetHeader() {
		return SeleniumUtil.getVisibileWebElement(d, "addBudgetHeader_BIABS", pageName, frameName);
	}

	public WebElement addBudgetNameText() {
		return SeleniumUtil.getVisibileWebElement(d, "addBudgetNameText_BIABS", pageName, frameName);
	}

	public WebElement addBudgetNameTextBox() {
		return SeleniumUtil.getVisibileWebElement(d, "addBudgetNameTextBox_BIABS", pageName, frameName);
	}

	public WebElement addBudgetNameTextBox1() {
		return SeleniumUtil.getVisibileWebElement(d, "addBudgetNameTextBox_BIABS1", pageName, frameName);
	}

	public WebElement addBudgetDropDown() {
		return SeleniumUtil.getVisibileWebElement(d, "addBudgetDropDown_BIABS", pageName, frameName);
	}

	public WebElement amountperMonthText() {
		By by = SeleniumUtil.getByObject(pageName, null, "amountperMonthText_BIABS");
		return SeleniumUtil.findElement(d, by, 5);// waitUntilElementVisible(d, by, 5);
	}

	public WebElement lastThreeMonthText() {
		return SeleniumUtil.getVisibileWebElement(d, "lastThreeMonthText_BIABS", pageName, frameName);
	}

	public WebElement amountPerMonthInputBox() {
		return SeleniumUtil.getVisibileWebElement(d, "amountPerMonthInputBox_BIABS", pageName, frameName);
	}

	public WebElement addBudgetSaveButton() {
		return SeleniumUtil.getVisibileWebElement(d, "addBudgetSaveButton_BIABS", pageName, frameName);
	}

	public WebElement addBudgetSaveButton1() {
		return SeleniumUtil.getVisibileWebElement(d, "addBudgetSaveButton_BIABS1", pageName, frameName);
	}

	public WebElement leftToSpendMaximizeIcon() {
		By by = SeleniumUtil.getByObject(pageName, null, "leftToSpendMaximizeIcon_BIABS");
		return SeleniumUtil.findElement(d, by, 10);// waitUntilElementVisible(d, by, 10);
	}

	/**
	 * @author sswain1
	 * @return
	 */
	public boolean isleftToSpendMaxIconPresent() {
		return PageParser.isElementPresent("leftToSpendMaximizeIcon_BIABS", pageName, frameName);
	}

	public WebElement budgetedAmountText() {
		return SeleniumUtil.getVisibileWebElement(d, "budgetedAmountText_BIABS", pageName, frameName);
	}

	public WebElement spentAmountText() {
		return SeleniumUtil.getVisibileWebElement(d, "spentAmountText_BIABS", pageName, frameName);
	}

	public WebElement remainingAmountText() {
		return SeleniumUtil.getVisibileWebElement(d, "remainingAmountText_BIABS", pageName, frameName);
	}

	public WebElement thisMonthTransactionText() {
		return SeleniumUtil.getVisibileWebElement(d, "thisMonthTransactionText_BIABS", pageName, frameName);
	}

	public WebElement leftToSpendEditIcon() {
		return SeleniumUtil.getVisibileWebElement(d, "leftToSpendEditIcon_BIABS", pageName, frameName);
	}

	public WebElement budgetedAmount() {
		return SeleniumUtil.getVisibileWebElement(d, "budgetedAmount_BIABS", pageName, frameName);
	}

	public WebElement spentAmount() {
		return SeleniumUtil.getVisibileWebElement(d, "spentAmount_BIABS", pageName, frameName);
	}

	public WebElement remainingAmount() {
		return SeleniumUtil.getVisibileWebElement(d, "remainingAmount_BIABS", pageName, frameName);
	}

	public List<WebElement> transactionAmount() {
		return SeleniumUtil.getWebElements(d, "transactionAmount_BIABS", pageName, frameName);
	}

	public WebElement editBudgetInputBox() {
		return SeleniumUtil.getVisibileWebElement(d, "editBudgetInputBox_BIABS", pageName, frameName);
	}

	public WebElement deleteButton() {
		return SeleniumUtil.getVisibileWebElement(d, "deleteButton_BIABS", pageName, frameName);
	}

	public WebElement saveButton() {
		return SeleniumUtil.getVisibileWebElement(d, "saveButton_BIABS", pageName, frameName);
	}

	public WebElement closeIcon() {
		return SeleniumUtil.getVisibileWebElement(d, "closeIcon_BIABS", pageName, frameName);
	}

	public WebElement spendingTrendText() {
		return SeleniumUtil.getVisibileWebElement(d, "spendingTrendText_BIABS", pageName, frameName);
	}

	public List<WebElement> spentPercentage() {
		return SeleniumUtil.getWebElements(d, "spentPercentage_BIABS", pageName, frameName);
	}

	public List<WebElement> leftAmount() {
		return SeleniumUtil.getWebElements(d, "leftAmount_BIABS", pageName, frameName);
	}

	public WebElement accountsHeader() {
		return SeleniumUtil.getVisibileWebElement(d, "accountsHeader_BIABS", pageName, frameName);
	}

	public WebElement backToBudgetIcon() {
		return SeleniumUtil.getVisibileWebElement(d, "backToBudgetIcon_BIABS", pageName, frameName);
	}

	public WebElement budgetedAmountLarget() {
		return SeleniumUtil.getVisibileWebElement(d, "budgetedAmountLarget_BIABS", pageName, frameName);
	}

	public List<WebElement> getCategory() {

		return SeleniumUtil.getWebElements(d, "getCategory_BIABS", pageName, frameName);
	}

	public WebElement saveBtnDisabled() {
		return SeleniumUtil.getVisibileWebElement(d, "saveBtnDisabled_BIABS", pageName, frameName);
	}

	public float replaceAllLiterals(String str) {

		str = str.replace("$", "");
		str = str.replace(",", "");
		str = str.replace(" ", "");
		str = str.replace("₹", "");
		System.out.println(str);
		float str1 = Float.parseFloat(str);
		str1 = Math.round(str1);
		return str1;
	}

	public WebElement backFrmMonthlyTransaction() {
		return SeleniumUtil.getVisibileWebElement(d, "backFromMonthlyTran", pageName, null);

	}

	public boolean isBackFrmMonthlyPresent() {
		return PageParser.isElementPresent("backFromMonthlyTran", pageName, null);
	}

	public WebElement getBackButton_BSA() {
		By by = SeleniumUtil.getByObject(pageName, null, "BackButton_BSA");
		return SeleniumUtil.waitForElementVisible(d, by, 3);
	}

	/**
	 * @author sswain1 This method to verify if locator is defined for the given
	 *         element in Pages.XML
	 * @return
	 */
	public boolean isSettingHeaderTextPresent() {
		return PageParser.isElementPresent("settingHeaderText", pageName, frameName);
	}

	public List<WebElement> transactionName() {
		return SeleniumUtil.getWebElements(d, "transactionName", pageName, frameName);
	}

	public String currentMonth() {

		Calendar c = Calendar.getInstance();

		int month = c.get(Calendar.MONTH);

		int year = c.get(Calendar.YEAR);

		String yrs = " " + year;

		String date = returnMonth(month) + yrs;

		return date;

	}

	public static String returnMonth(int month) {

		String[] monthNames = PropsUtil.getDataPropertyValue("Budget_MonthNames")
				.split(PropsUtil.getDataPropertyValue("comma"));

		return monthNames[month];

	}

	public WebElement createdBudgetHeader() {
		return SeleniumUtil.getVisibileWebElement(d, "createdBudgetHeader", pageName, frameName);
	}

	public void clickOnsettingsIcon() {
		SeleniumUtil.click(settingsIcon());
	}

	public WebElement BudgetSummery_CreateBudgetLink() {
		return SeleniumUtil.getVisibileWebElement(d, "BudgetSummery_CreateBudgetLink", pageName, frameName);
	}

	public void clickcreateBudget() {
		SeleniumUtil.click(this.BudgetSummery_CreateBudgetLink());
	}

	public WebElement BudgetSummery_BudgetGroupDropDown() {
		return SeleniumUtil.getVisibileWebElement(d, "BudgetSummery_BudgetGroupDropDown", pageName, frameName);
	}

	public WebElement BudgetSummery_MultiBudgetGroupDropDown() {
		return SeleniumUtil.getWebElement(d, "BudgetSummery_MultiBudgetGroupDropDown", pageName, frameName);
	}

	public WebElement BudgetSummery_CreateGroup() {
		return SeleniumUtil.getWebElement(d, "BudgetSummery_CreateGroup", pageName, frameName);
	}

	public void clickGroupDropDown() {
		SeleniumUtil.click(this.BudgetSummery_BudgetGroupDropDown());
	}

	public List<WebElement> BudgetSummery_BudgetGroupDropDownValueList() {
		return SeleniumUtil.getWebElements(d, "BudgetSummery_BudgetGroupDropDownValueList", pageName, frameName);
	}

	public WebElement BudgetSummery_BudgetGroupDropDownValueName(String groupName) {
		String xpath = SeleniumUtil.getVisibileElementXPath(d, pageName, frameName,
				"BudgetSummery_BudgetGroupDropDownValueName");
		xpath = xpath.replaceAll("GroupName", groupName);
		return d.findElement(By.xpath(xpath));
	}

	public WebElement BudgetSummery_BudgetDropDownValueName(String groupName) {
		String xpath = SeleniumUtil.getVisibileElementXPath(d, pageName, frameName,
				"BudgetSummery_BudgetDropDownValueName");
		xpath = xpath.replaceAll("GroupName", groupName);
		return d.findElement(By.xpath(xpath));
	}

	public void selectBudgetGroup(String groupName) {
		SeleniumUtil.click(this.BudgetSummery_MultiBudgetGroupDropDown());
		SeleniumUtil.click(this.BudgetSummery_BudgetDropDownValueName(groupName));
	}

	public void SelectAccountGroup(String groupName) {
		this.clickGroupDropDown();
		SeleniumUtil.click(this.BudgetSummery_BudgetGroupDropDownValueName(groupName));
	}
}
