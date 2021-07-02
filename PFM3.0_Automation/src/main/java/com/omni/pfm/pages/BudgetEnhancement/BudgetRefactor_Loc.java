/**
* Copyright (c) 2019 Yodlee Inc. All Rights Reserved. 
*
* This software is the confidential and proprietary information of
* Yodlee, Inc. Use is subject to license terms.
*
* @author sbhat1
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

public class BudgetRefactor_Loc {
	public static WebDriver d;
	String frameName, pageName;
	static WebElement we;
	static Logger logger = LoggerFactory.getLogger(BudgetRefactor_Loc.class);

	public BudgetRefactor_Loc(WebDriver d) {
		this.d = d;
		pageName = "Budget";
	}

	public List<WebElement> flexibleSpendingCategories() {
		return SeleniumUtil.getWebElements(d, "flexiblespendingcategories", pageName, frameName);
	}

	public WebElement flexibleSpendingEditIcon() {
		return SeleniumUtil.getVisibileWebElement(d, "flexiblespendingediticon", pageName, frameName);
	}

	public WebElement flexibleSpendingAddIcon() {
		return SeleniumUtil.getVisibileWebElement(d, "flexiblespendingAddicon", pageName, frameName);
	}

	public WebElement AutomativeExpenseCategories() {
		return SeleniumUtil.getVisibileWebElement(d, "automativeexpense", pageName, frameName);
	}

	public List<WebElement> flexibleSpendingInputBox() {
		return SeleniumUtil.getWebElements(d, "flexiblespendingInputBox", pageName, frameName);
	}

	public WebElement checkAllCheckBox() {
		return SeleniumUtil.getVisibileWebElement(d, "checkallcheckbox", pageName, frameName);
	}

	public WebElement flexibleSpendingCheckBox() {
		return SeleniumUtil.getVisibileWebElement(d, "flexiblespendingcheckbox", pageName, frameName);
	}

	public WebElement groceriesCheckBox() {
		return SeleniumUtil.getWebElement(d, "groceriescheckbox", pageName, frameName);
	}

	public WebElement flexibleSpendingBasedOnMonthText() {
		return SeleniumUtil.getWebElement(d, "flexiblespendingbasedonmonthtxt", pageName, frameName);
	}

	public WebElement AutomativeCategory() {
		return SeleniumUtil.getVisibileWebElement(d, "automativeexpensecategory", pageName, frameName);
	}

	public WebElement doneButton() {
		return SeleniumUtil.findElement(d, "nextBtn", pageName, frameName, 5);
	}

	public WebElement createdBudgetheader() {
		return SeleniumUtil.getVisibileWebElement(d, "header_BDG", pageName, frameName);
	}

	public WebElement totalFlexibleSpendingText() {
		return SeleniumUtil.getVisibileWebElement(d, "totalflexiblespendingtxt", pageName, frameName);
	}

	public WebElement budgetExceedsText() {
		return SeleniumUtil.getVisibileWebElement(d, "budgetexceedstxt", pageName, frameName);
	}

	public WebElement considerBudgetingText() {
		return SeleniumUtil.getVisibileWebElement(d, "considerbudgetingtxt", pageName, frameName);
	}

	public WebElement moreBtn() {
		return SeleniumUtil.getVisibileWebElement(d, "moreIcon_BIABS", pageName, frameName);
	}

	public WebElement addFlexibleSpending() {
		return SeleniumUtil.getVisibileWebElement(d, "addFlexibleSpending", pageName, frameName);
	}

	public WebElement addCategory() {
		return SeleniumUtil.getVisibileWebElement(d, "addCategory", pageName, frameName);
	}

	public WebElement addGiftsCategory() {
		return SeleniumUtil.getVisibileWebElement(d, "addGiftsCategory", pageName, frameName);
	}

	public WebElement addAmount() {
		return SeleniumUtil.getVisibileWebElement(d, "addAmount", pageName, frameName);
	}

	public WebElement addBudgetPopUp_Save() {
		return SeleniumUtil.getVisibileWebElement(d, "addBudgetPopUp_Save", pageName, frameName);
	}

	public WebElement verifyGifts() {
		return SeleniumUtil.getVisibileWebElement(d, "verifyGifts", pageName, frameName);
	}

	public WebElement nextBtn() {
		return SeleniumUtil.getVisibileWebElement(d, "nextBtn", pageName, frameName);
	}

	public WebElement closeIcon(int n) {

		return d.findElement(By.xpath("(//a[@title='Close popover'])[" + n + "]"));
	}

	/**
	 * @author sswain1 This method returns close element in the Flexible category
	 *         add popup
	 * @return WebElement
	 */
	public WebElement closeFlexibleCategoryPopUp() {
		return SeleniumUtil.getVisibileWebElement(d, "CancelButton_CB", pageName, frameName);
	}

	/**
	 * @author sswain1 This method returns close element present in
	 *         AddFlexiblecategory Screen
	 * @return
	 */
	public WebElement closeAddFlexibleCategory() {
		return SeleniumUtil.getVisibileWebElement(d, "closeAddFlexCategory", pageName, frameName);
	}

	public boolean isAddFlexCategoryClosePresent() {
		return PageParser.isElementPresent("closeAddFlexCategory", pageName, frameName);
	}

	public WebElement CreateBudgetHeader() {
		return SeleniumUtil.getVisibileWebElement(d, "title", pageName, frameName);
	}

	public WebElement rentAndMortageCheckBox() {
		return SeleniumUtil.getVisibileWebElement(d, "rentandmortagecheckbx", pageName, frameName);
	}

	public WebElement amountDisabled() {
		return SeleniumUtil.getVisibileWebElement(d, "amtdisabled", pageName, frameName);
	}

	public WebElement myIncomeText() {
		return SeleniumUtil.getVisibileWebElement(d, "myincometxt", pageName, frameName);
	}

	public WebElement basedMonthDataText() {
		return SeleniumUtil.getVisibileWebElement(d, "basedmonthtxt", pageName, frameName);
	}

	public List<WebElement> billCategories() {
		return SeleniumUtil.getWebElements(d, "billcategories", pageName, frameName);
	}

	public WebElement myIncomeEditIcon() {
		return SeleniumUtil.getWebElement(d, "myincomeediticon", pageName, frameName);
	}

	public WebElement mobilemyincomeeditIcon() {
		return SeleniumUtil.getVisibileWebElement(d, "mobilemyincomeediticon", pageName, frameName);
	}

	public List<WebElement> CheckBox() {
		return SeleniumUtil.getWebElements(d, "checkbx", pageName, frameName);
	}

	public List<WebElement> CategoryAmount() {
		return SeleniumUtil.getWebElements(d, "basedmonthdatatxt", pageName, frameName);
	}

	public WebElement diableamountamount1() {
		return SeleniumUtil.getVisibileWebElement(d, "diableamountamount", pageName, frameName);
	}

	public void typeCategoryValues(String categoryName, String value) {
		SeleniumUtil.waitForPageToLoad(1000);
		d.findElement(By.xpath("//input[contains(@id,'cat-amt') and contains(@aria-label,'" + categoryName + "')]"))
				.clear();
		SeleniumUtil.waitForPageToLoad(1000);
		d.findElement(By.xpath("//input[contains(@id,'cat-amt') and contains(@aria-label,'" + categoryName + "')]"))
				.sendKeys(value);
	}

	public List<WebElement> editCatAmount() {
		return SeleniumUtil.getWebElements(d, "EditactAmount", pageName, frameName);
	}

	public WebElement CategoryValues() {
		return SeleniumUtil.getVisibileWebElement(d, "CategoryValues1", pageName, frameName);
	}

	public WebElement RecuringBillamountamount1() {
		return SeleniumUtil.getVisibileWebElement(d, "RecuringBillamountamount", pageName, frameName);
	}

	public WebElement RecuringBillamountamount3() {
		return SeleniumUtil.getVisibileWebElement(d, "RecuringBillamountamount2", pageName, frameName);
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
		return SeleniumUtil.findElement(d, by, 10);
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

	/**
	 * 
	 * @param str
	 * @return formatted string
	 */
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

	/**
	 * Get the current month
	 * 
	 * @return current month
	 */
	public String currentMonth() {
		Calendar c = Calendar.getInstance();
		int month = c.get(Calendar.MONTH);
		int year = c.get(Calendar.YEAR);
		String yrs = " " + year;
		String date = returnMonth(month) + yrs;
		return date;

	}

	/**
	 * Method to return month
	 * 
	 * @param month
	 * @return this month name
	 */
	public static String returnMonth(int month) {
		String[] monthNames = PropsUtil.getDataPropertyValue("Budget_MonthNames")
				.split(PropsUtil.getDataPropertyValue("comma"));
		return monthNames[month];

	}

	public List<WebElement> myRecurringInputBox() {
		return d.findElements(By.xpath("//div[@id='categoryList']//div//div//div//div//div[4]//input"));
	}

	public List<WebElement> billCategories1() {
		return d.findElements(By.xpath("//div[@id='categoryList']//div//div//div//div//div//span[2]//label"));
	}

	public List<WebElement> aggregatedAccounts() {
		return d.findElements(By.xpath("//span[@class='account-name']//label"));

	}

	/**
	 * Method to click on the category by HLC and MLC
	 * 
	 * @param HLC
	 * @param MLC
	 * @author sbhat1
	 * @return category
	 */
	public WebElement getCategory(String HLC, String MLC) {
		String getXpath = SeleniumUtil.getVisibileElementXPath(d, pageName, frameName, "BudgetcategoryDropdown");
		getXpath = getXpath.replaceAll("HLC", HLC);
		getXpath = getXpath.replaceAll("MLC", MLC);

		return d.findElement(By.xpath(getXpath));
	}

	public void selectCategoryByNumber(int highLevelCategoryNum, int masterLevelCategoryNum) {
		SeleniumUtil.click(getCategory("4", "1"));

	}

	public WebElement groceryCategory() {
		return SeleniumUtil.getWebElement(d, "groceryCategory", "Budget", null);
	}

	/**
	 * Method to enter the value to the amount textbox
	 * 
	 * @param incomeValue
	 */
	public void typeFlexibleSpendingCategoriesValues(String incomeValue) {

		SeleniumUtil.waitForPageToLoad();
		WebElement categoryAmountTextBox = SeleniumUtil.getVisibileWebElement(d, "input_search_budget", pageName,
				frameName);

		categoryAmountTextBox.clear();
		categoryAmountTextBox.sendKeys(incomeValue);
	}

	public WebElement settingTb() {
		return d.findElement(By.id("drilldown6"));

	}

	public WebElement category() {
		return SeleniumUtil.getVisibileWebElement(d, "firstSearchedCategory", "Budget", null);
	}

	public WebElement AccsettingTb() {
		return d.findElement(By.id("10003659"));
	}

	public WebElement delSite() {
		return d.findElement(By.xpath("//span[text()='Delete Site']"));
	}

	public WebElement confirmDel() {
		return d.findElement(By.id("deleteconfirmcheck"));
	}

	public WebElement confirmDelBtn() {
		return d.findElement(By.xpath("//a[text()='delete']"));
	}

	public WebElement noData() {
		return d.findElement(By.id("no-data-container"));
	}

	public List<WebElement> start() {
		return d.findElements(By.xpath("//a[text()='get started']"));
	}

	public WebElement titleLabel() {
		return SeleniumUtil.getVisibileWebElement(d, "titleLabel", "Budget", null);
	}

	public WebElement welcomeNote() {
		return SeleniumUtil.getVisibileWebElement(d, "welcomeNote", "Budget", null);
	}

	public WebElement helpText() {
		return SeleniumUtil.getVisibileWebElement(d, "helpText", "Budget", null);
	}

	public WebElement getStartedButton() {
		return SeleniumUtil.getVisibileWebElement(d, "getStartedButton", "Budget", null);
	}

	public WebElement selectTheAccountsText() {
		return SeleniumUtil.getVisibileWebElement(d, "selectTheAccountsText", "Budget", null);
	}

	public WebElement linkAccountButton() {
		return SeleniumUtil.getVisibileWebElement(d, "linkAccountButton", "Budget", null);
	}

	public WebElement linkAccountText() {
		return SeleniumUtil.getVisibileWebElement(d, "linkAccountText", "Budget", null);
	}

	public WebElement fastLinkCloseIcon() {
		return SeleniumUtil.getVisibileWebElement(d, "fastLinkCloseIcon", "Budget", null);
	}

	public WebElement checkingSubHeader() {
		return SeleniumUtil.getVisibileWebElement(d, "checkingSubHeader", "Budget", null);
	}

	public WebElement coackMarkIcon() {
		return SeleniumUtil.getVisibileWebElement(d, "coackMarkIcon", "Budget", null);
	}

	public WebElement okButton() {
		return SeleniumUtil.getVisibileWebElement(d, "okButton", "Budget", null);
	}

	public WebElement creditCardsSubHeader() {
		return SeleniumUtil.getVisibileWebElement(d, "creditCardsSubHeader", "Budget", null);
	}

	public WebElement savingsAndInvestmentSubHeader() {
		return SeleniumUtil.getVisibileWebElement(d, "savingsAndInvestmentSubHeader", "Budget", null);
	}

	public WebElement investmentCheckBox() {
		return SeleniumUtil.getVisibileWebElement(d, "investmentCheckBox", "Budget", null);
	}

	public WebElement investmentAccount() {
		return SeleniumUtil.getVisibileWebElement(d, "investmentAccount", "Budget", null);
	}

	public WebElement whyNeedAccountsText() {
		return SeleniumUtil.getVisibileWebElement(d, "whyNeedAccountsText", "Budget", null);
	}

	public WebElement nextButton() {
		return SeleniumUtil.getVisibileWebElement(d, "nextButton", "Budget", null);
	}

	public WebElement nextButton1() {
		return SeleniumUtil.getVisibileWebElement(d, "nextButton1", "Budget", null);
	}

	public WebElement myIncomeText1() {
		return SeleniumUtil.getVisibileWebElement(d, "myIncomeText", "Budget", null);
	}

	public WebElement myRecurringBillText() {
		return SeleniumUtil.getVisibileWebElement(d, "myRecurringBillText", "Budget", null);
	}

	public WebElement flexibleSpendingText() {
		return SeleniumUtil.getVisibileWebElement(d, "flexibleSpendingText", "Budget", null);
	}

	public WebElement basedMonthDataText1() {
		return SeleniumUtil.getVisibileWebElement(d, "basedMonthDataText", "Budget", null);
	}

	public WebElement noteText() {
		return SeleniumUtil.getVisibileWebElement(d, "noteText", "Budget", null);
	}

	public WebElement addiconStep1element() {
		return SeleniumUtil.getVisibileWebElement(d, "addiconStep1element", "Budget", null);
	}

	public WebElement mobileaddiconStep1element() {
		return SeleniumUtil.getVisibileWebElement(d, "mobileaddiconStep1element", "Budget", null);
	}

	public WebElement addiconStep2eleforBills() {

		return SeleniumUtil.findElement(d, "addiconStep2elementForBill", "Budget", null, 5);
	}

	/**
	 * Add Icon present in Step 2 - When clicked, user can add a new category
	 * 
	 * @return addIcon
	 */
	public WebElement addiconStep2element() {
		return SeleniumUtil.getVisibileWebElement(d, "addiconStep2element", "Budget", null);
	}

	public WebElement mobileaddiconStep2element() {
		return SeleniumUtil.getVisibileWebElement(d, "mobileaddiconStep2element", "Budget", null);
	}

	public WebElement selectCategoryTextBox() {
		return SeleniumUtil.getVisibileWebElement(d, "selectCategoryTextBox", "Budget", null);
	}

	public WebElement selectCategoryTextBox1() {
		return SeleniumUtil.getVisibileWebElement(d, "selectCategoryTextBox1", "Budget", null);
	}

	public WebElement mobileselectCategoryTextBox() {
		return SeleniumUtil.getVisibileWebElement(d, "mobileselectCategoryTextBox", "Budget", null);
	}

	/**
	 * Clicks on the category dropdown
	 * 
	 * @return category dropdown
	 */
	public WebElement selectCategoryDropDown() {
		return SeleniumUtil.findElement(d, "selectCategoryDropDown", "Budget", null, 5);
	}

	public WebElement backButton() {
		return SeleniumUtil.getVisibileWebElement(d, "backButton", "Budget", null);
	}

	public WebElement incomeNextButton() {
		return SeleniumUtil.getWebElement(d, "incomeNextButton", "Budget", null);
	}

	public WebElement editincome() {
		return SeleniumUtil.findElement(d, "editincome", "Budget", null, 30);
	}

	public WebElement editflexicon() {
		return SeleniumUtil.getVisibileWebElement(d, "editflexicon", "Budget", null);
	}

	public WebElement editincometext() {
		return SeleniumUtil.findElement(d, "editincometext", "Budget", null, 30);// SeleniumUtil.getVisibileWebElement(d,
																					// "editincometext", "Budget",
																					// null);
	}

	public WebElement mobileincomeNextButton() {
		return SeleniumUtil.getVisibileWebElement(d, "mobileincomeNextButton", "Budget", null);
	}

	public WebElement cancelButton() {
		return SeleniumUtil.findElement(d, "cancelButton", "Budget", null, 5);// getVisibileWebElement(d,
																				// "cancelButton", "Budget", null);
	}

	public WebElement addButton() {
		return SeleniumUtil.findElement(d, "addButton", "Budget", null, 3);// getVisibileWebElement(d, "addButton",
																			// "Budget", null);
	}

	public WebElement waitUntilAddButtonDisplayed(int timeOutInSecs) {
		By by = SeleniumUtil.getByObject("Budget", null, "addButton");
		return SeleniumUtil.findElement(d, by, timeOutInSecs);// waitUntilElementVisible(d, by, timeOutInSecs);
	}

	public WebElement myIncomeEditIcon2() {
		return SeleniumUtil.getVisibileWebElement(d, "myIncomeEditIcon", "Budget", null);
	}

	public WebElement myIncomeEditIcon1() {
		return SeleniumUtil.getVisibileWebElement(d, "myIncomeEditIcon1", "Budget", null);
	}

	public WebElement totalRecurringBillText() {
		By by = SeleniumUtil.getByObject("Budget", null, "totalRecurringBillText");
		return SeleniumUtil.findElement(d, by, 120);
	}

	public WebElement totalRecurringAmount() {
		return SeleniumUtil.getVisibileWebElement(d, "totalRecurringAmount", "Budget", null);
	}

	public WebElement myRecurringEditIcon() {
		return SeleniumUtil.getWebElement(d, "myRecurringEditIcon", "Budget", null);
	}

	public WebElement mobilemyRecurringEditIcon() {
		return SeleniumUtil.getVisibileWebElement(d, "mobilemyRecurringEditIcon", "Budget", null);
	}

	public WebElement incomeAndBillInputBox() {
		return SeleniumUtil.getVisibileWebElement(d, "incomeAndBillInputBox", "Budget", null);
	}

	public WebElement incomeCancelButton() {
		return SeleniumUtil.getVisibileWebElement(d, "incomeCancelButton", "Budget", null);
	}

	public WebElement incomeSaveButton() {
		return SeleniumUtil.getVisibileWebElement(d, "incomeSaveButton", "Budget", null);
	}

	public WebElement MobileincomeSaveButton() {
		return SeleniumUtil.getVisibileWebElement(d, "MobileincomeSaveButton", "Budget", null);
	}

	public WebElement SaveButton() {
		return SeleniumUtil.getVisibileWebElement(d, "SaveButton_Budg", "Budget", null);
	}

	public WebElement MobileSaveButton() {
		return SeleniumUtil.getVisibileWebElement(d, "MobileSaveButton", "Budget", null);
	}

	public WebElement checksCategory() {
		return SeleniumUtil.getVisibileWebElement(d, "checksCategory", "Budget", null);
	}

	public WebElement editAmountInputBox() {
		return SeleniumUtil.findElement(d, "editAmountInputBox", "Budget", null, 5);
	}

	public void searchCategory(String category) {
		SeleniumUtil.getVisibileWebElement(d, "searchCategorytextBox", "Budget", null).sendKeys(category);
	}

	public WebElement BudgetHeader() {
		return SeleniumUtil.getVisibileWebElement(d, "BudgetHeader_BNA", pageName, frameName);
	}

	public WebElement mobileBudgetHeader() {
		return SeleniumUtil.getVisibileWebElement(d, "mobileBudgetHeader_BNA", pageName, frameName);
	}

	public WebElement LinkAccountButton_BNA() {
		return SeleniumUtil.getVisibileWebElement(d, "LinkAccountButton_BNA", pageName, frameName);
	}

	public WebElement LinkAccountHeader() {
		return SeleniumUtil.getVisibileWebElement(d, "LinkAccountHeader_BNA", pageName, frameName);
	}

	public WebElement Description() {
		return SeleniumUtil.getVisibileWebElement(d, "Description_BNA", pageName, frameName);
	}

	public List<WebElement> Steps() {
		return SeleniumUtil.getWebElements(d, "Steps_BNA", pageName, frameName);
	}

	public WebElement mobilestep1text() {
		return SeleniumUtil.getVisibileWebElement(d, "mobilestep1text_BNA", pageName, frameName);
	}

	public WebElement mobilestep2text() {
		return SeleniumUtil.getVisibileWebElement(d, "mobilestep2text_BNA", pageName, frameName);
	}

	public WebElement mobilestep3text() {
		return SeleniumUtil.getVisibileWebElement(d, "mobilestep3text_BNA", pageName, frameName);
	}

	public WebElement mobilestep1iconview() {
		return SeleniumUtil.getVisibileWebElement(d, "mobilestep1iconview_BNA", pageName, frameName);
	}

	public List<WebElement> mobilestep2iconview() {
		return SeleniumUtil.getWebElements(d, "mobilestep2iconview_BNA", pageName, frameName);
	}

	public WebElement mobilestep3iconview() {
		return SeleniumUtil.getVisibileWebElement(d, "mobilestep3iconview_BNA", pageName, frameName);
	}

	public List<WebElement> StepsIcon() {
		return SeleniumUtil.getWebElements(d, "StepsIcon_BNA", pageName, frameName);
	}

	public WebElement Travel_trninBud() {
		return SeleniumUtil.getVisibileWebElement(d, "Travel_trninBud", pageName, frameName);
	}

	public WebElement Travel_Acc() {
		return SeleniumUtil.getVisibileWebElement(d, "Travel_Acc", pageName, frameName);
	}

	public WebElement GetStartedButton() {
		return SeleniumUtil.getVisibileWebElement(d, "GetStartedButton_BNA", pageName, frameName);
	}

	public WebElement budget_Acc_container() {
		return SeleniumUtil.getVisibileWebElement(d, "budget_Acc_container", pageName, frameName);
	}

	public WebElement MobileGetStartedButton() {
		return SeleniumUtil.getVisibileWebElement(d, "MobileGetStartedButton_BNA", pageName, frameName);
	}

	public WebElement FooterText() {
		return SeleniumUtil.getVisibileWebElement(d, "FooterText_BNA", pageName, frameName);
	}

	public WebElement MobileFooterText() {
		return SeleniumUtil.getVisibileWebElement(d, "MobileFooterText_BNA", pageName, frameName);
	}

	public WebElement ThreeTabs() {
		return SeleniumUtil.getWebElement(d, "ThreeTabs_BNA", pageName, frameName);
	}

	public List<WebElement> MobileThreeTabs() {
		return SeleniumUtil.getWebElements(d, "MobileThreeTabs_BNA", pageName, frameName);
	}

	public List<WebElement> FooterTextList() {
		return SeleniumUtil.getWebElements(d, "FooterTextList_BNA", pageName, frameName);
	}

	public WebElement FooterLogo() {
		return SeleniumUtil.getVisibileWebElement(d, "FooterLogo_BNA", pageName, frameName);
	}

	public WebElement FooterLogo1() {
		return SeleniumUtil.getVisibileWebElement(d, "FooterLogo1_BNA", pageName, frameName);
	}

	public WebElement alldoneButton() {
		return SeleniumUtil.getVisibileWebElement(d, "alldoneButton_BNA", pageName, frameName);
	}

	public WebElement Description_BSA() {
		return SeleniumUtil.getVisibileWebElement(d, "Description_BSA", pageName, frameName);
	}

	public WebElement SelectAccountsHeader() {
		return SeleniumUtil.getVisibileWebElement(d, "SelectAccountsHeader_BSA", pageName, frameName);
	}

	public List<WebElement> AccTypeSubHeader() {
		return SeleniumUtil.getWebElements(d, "AccTypeSubHeader_BSA", pageName, frameName);
	}

	public List<WebElement> SiteName() {
		return SeleniumUtil.getWebElements(d, "SiteName_BSA", pageName, frameName);
	}

	public List<WebElement> AccountName() {
		return SeleniumUtil.getWebElements(d, "AccountName_BSA", pageName, frameName);
	}

	public List<WebElement> InfoIcon() {
		return SeleniumUtil.getWebElements(d, "InfoIcon_BSA", pageName, frameName);
	}

	public WebElement CoachmarkHeader() {
		return SeleniumUtil.getVisibileWebElement(d, "CoachmarkHeader_BSA", pageName, frameName);
	}

	public WebElement CoachmarkDescription() {
		return SeleniumUtil.getVisibileWebElement(d, "CoachmarkDescription_BSA", pageName, frameName);
	}

	public WebElement OKButton() {
		return SeleniumUtil.getVisibileWebElement(d, "OKButton_BSA", pageName, frameName);
	}

	public WebElement NextButton() {
		return SeleniumUtil.getVisibileWebElement(d, "NextButton_BSA1", pageName, frameName);
	}

	public WebElement header() {
		return SeleniumUtil.getVisibileWebElement(d, "header", pageName, frameName);
	}

	public WebElement NextButton2() {
		return SeleniumUtil.getVisibileWebElement(d, "NextButton_BSA", pageName, frameName);
	}

	public WebElement NextButton1() {
		return SeleniumUtil.getWebElement(d, "NextButtonText_CB", pageName, frameName);
	}

	public WebElement MobileNextButton() {
		return SeleniumUtil.getVisibileWebElement(d, "MobileNextButton_BSA", pageName, frameName);
	}

	public WebElement BackButton() {
		return SeleniumUtil.getVisibileWebElement(d, "BackButton_BSA", pageName, frameName);
	}

	public WebElement MobileBackButton() {
		return SeleniumUtil.getVisibileWebElement(d, "MobileBackButton_BSA", pageName, frameName);
	}

	public List<WebElement> CheckBox_BSA() {
		return SeleniumUtil.getWebElements(d, "CheckBox_BSA", pageName, frameName);
	}

	public WebElement SuccessMessage() {
		return SeleniumUtil.getVisibileWebElement(d, "SuccessMessage", pageName, frameName);
	}

	public List<WebElement> notificationMessage() {
		return d.findElements(By.xpath("//div[@id='notificationsSettingTypes']/div/div/div/div[2]"));
	}

	public List<WebElement> notificationOptions() {
		return d.findElements(
				By.xpath("//div[@id='notificationsSettingTypes']/div/div/div/div[contains(text(),'Alert:')]"));
	}

	public List<WebElement> BudgetAccountList() {
		return d.findElements(By
				.xpath("//div[@id='budgetsetup-body']//div[1]/div[contains(@class,'section')]//div[@role='heading']"));
	}

	public WebElement budgetSettingsHeader() {
		return SeleniumUtil.getVisibileWebElement(d, "budgetSettingsHeader", "Budget", null);
	}

	public WebElement sendMeNotificationText() {
		return SeleniumUtil.getVisibileWebElement(d, "sendMeNotificationText", "Budget", null);
	}

	public WebElement linkAccountButton_BS() {
		return SeleniumUtil.getVisibileWebElement(d, "linkAccountButton", "Budget", null);
	}

	public WebElement linkAccountBelowText() {
		return SeleniumUtil.getVisibileWebElement(d, "linkAccountBelowText", "Budget", null);
	}

	public WebElement linkAccountBelowTex() {
		return SeleniumUtil.getVisibileWebElement(d, "linkAccountBelowTex", "Budget", null);
	}

	public WebElement AccountsLabel() {
		return SeleniumUtil.getVisibileWebElement(d, "AccountsLabel", "Budget", null);
	}

	public WebElement linkAccountButton1() {
		return SeleniumUtil.getVisibileWebElement(d, "linkAccountButton1", "Budget", null);
	}

	public WebElement BudgetAccountDescription() {
		return SeleniumUtil.getVisibileWebElement(d, "BudgetAccountDescription", "Budget", null);
	}

	public WebElement WhyLinkThoseBudgetText() {
		return SeleniumUtil.getVisibileWebElement(d, "WhyLinkThoseBudgetText", "Budget", null);
	}

	public WebElement EditAllBudgetText() {
		return SeleniumUtil.getVisibileWebElement(d, "EditAllBudgetText", "Budget", null);
	}

	public WebElement FlexibleSpendingAddIcon() {
		return SeleniumUtil.getVisibileWebElement(d, "FlexibleSpendingAddIcon", "Budget", null);
	}

	public WebElement SaveChangesButton() {
		return SeleniumUtil.getVisibileWebElement(d, "SaveChangesButton", "Budget", null);
	}

	public WebElement SuccessMessage_BS() {
		return SeleniumUtil.getVisibileWebElement(d, "SuccessMessage", "Budget", null);
	}

	public WebElement BackToBudgetText() {
		return SeleniumUtil.getVisibileWebElement(d, "BackToBudgetText", "Budget", null);
	}

	public WebElement CreateBudgetHeader_CB() {
		return SeleniumUtil.getVisibileWebElement(d, "CreateBudgetHeader_CB", pageName, frameName);
	}

	public boolean IsCreateBudgetHeaderPresent() {
		return PageParser.isElementPresent("CreateBudgetHeader_CB", pageName, frameName);
	}

	public WebElement MobileCreateBudgetHeader() {
		return SeleniumUtil.getVisibileWebElement(d, "MobileCreateBudgetHeader_CB", pageName, frameName);
	}

	public WebElement CreateBudgetPlusIcon() {
		return SeleniumUtil.findElement(d, "CreateBudgetPlusIcon_CB", pageName, frameName, 600);
	}

	public WebElement AlertSettingsText() {

		return SeleniumUtil.getVisibileWebElement(d, "AlertSettingsText_CB", pageName, frameName);

	}

	public WebElement AlertSettingsIcon() {

		return SeleniumUtil.getVisibileWebElement(d, "AlertSettingsIcon_CB", pageName, frameName);

	}

	public WebElement AlertSettingsHeader() {
		return SeleniumUtil.findElement(d, "AlertSettingsHeader_CB", pageName, frameName, 5);

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

	public WebElement SaveChangesButton_CB() {
		return SeleniumUtil.findElement(d, "SaveChangesButton_CB", pageName, frameName, 5);
	}

	public WebElement MobleSaveChangesButton() {
		return SeleniumUtil.getVisibileWebElement(d, "MobleSaveChangesButton_CB", pageName, frameName);
	}

	public WebElement SelectBudgetDesc() {
		return SeleniumUtil.findElement(d, "SelectBudgetDesc_CB", pageName, frameName, 5);
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

	public WebElement InfoIcon_CB() {
		return SeleniumUtil.findElement(d, "InfoIcon_CB", pageName, frameName, 5);
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
		return SeleniumUtil.findElement(d, "NoGroupIcon_CB", pageName, frameName, 5);
	}

	public WebElement NoAccountGroupText() {
		return SeleniumUtil.getVisibileWebElement(d, "NoAccountGroupText_CB", pageName, frameName);
	}

	public WebElement NoAccountGroupDesc() {
		return SeleniumUtil.getVisibileWebElement(d, "NoAccountGroupDesc_CB", pageName, frameName);
	}

	public WebElement NoAccountGroupButton() {
		return SeleniumUtil.findElement(d, "NoAccountGroupButton_CB", pageName, frameName, 5);
	}

	public WebElement CreateAccountGroupHeader() {
		return SeleniumUtil.findElement(d, "CreateAccountGroupHeader_CB", pageName, frameName, 5);
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
		return SeleniumUtil.findElement(d, "GroupInputBox_CB", pageName, frameName, 5);
	}

	public List<WebElement> CheckBox_CB() {
		return SeleniumUtil.getWebElements(d, "CheckBox_CB", pageName, frameName);
	}

	public WebElement CreateGroupButton() {
		return SeleniumUtil.getVisibileWebElement(d, "CreateGroupButton_CB", pageName, frameName);
	}

	public WebElement PFMTestingText() {
		return SeleniumUtil.findElement(d, "PFMTestingText_CB", pageName, frameName, 10);
	}

	public List<WebElement> AccountTypeHeading() {
		return SeleniumUtil.getWebElements(d, "AccountTypeHeading_CB", pageName, frameName);
	}

	public WebElement InfoIcon1() {
		return SeleniumUtil.findElement(d, "InfoIcon1_CB", pageName, frameName, 5);
	}

	public WebElement InfoText() {

		return SeleniumUtil.getVisibileWebElement(d, "InfoText_CB", pageName, frameName);
	}

	public boolean IsInfoTextPresent() {
		return PageParser.isElementPresent("InfoText_CB", pageName, frameName);
	}

	public WebElement InfoHeader() {
		return SeleniumUtil.getVisibileWebElement(d, "InfoHeader_CB", pageName, frameName);
	}

	public boolean IsInfoHeaderPresent() {
		return PageParser.isElementPresent("InfoHeader_CB", pageName, frameName);
	}

	public WebElement InfoDescription1() {
		return SeleniumUtil.findElement(d, "InfoDescription1_CB", pageName, frameName, 5);
	}

	public List<WebElement> newRowAdded() {
		return SeleniumUtil.getWebElements(d, "budget_row", pageName, frameName);
	}

	public WebElement budgetIncome1() {
		return SeleniumUtil.getWebElement(d, "budget_income1", pageName, frameName);
	}

	public WebElement budgetIncome2() {
		return SeleniumUtil.getWebElement(d, "budget_income2", pageName, frameName);
	}

	public WebElement budgetIncome3() {
		return SeleniumUtil.getWebElement(d, "budget_income3", pageName, frameName);
	}

	public WebElement CloseIcon1() {
		return SeleniumUtil.getVisibileWebElement(d, "CloseIcon1_CB", pageName, frameName);
	}

	public WebElement BackButton_CB() {
		return SeleniumUtil.getVisibileWebElement(d, "BackButton_CB", pageName, frameName);
	}

	public WebElement NextButtonText() {
		return SeleniumUtil.findElement(d, "NextButtonText_CB", pageName, frameName, 5);
	}

	public WebElement NextButtonIcon() {
		return SeleniumUtil.getVisibileWebElement(d, "NextButtonIcon_CB", pageName, frameName);
	}
}
