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
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.omni.pfm.utility.SeleniumUtil;

public class Budget_Landing_Page_Loc {

	static Logger logger = LoggerFactory.getLogger(Budget_Landing_Page_Loc.class);
	public WebDriver d = null;
	static WebElement we;

	public Budget_Landing_Page_Loc(WebDriver d) {
		this.d = d;
	}

	public List<WebElement> myRecurringInputBox() {
		return d.findElements(By.xpath("//div[@id='categoryList']//div//div//div//div//div[4]//input"));
	}

	public List<WebElement> billCategories() {
		return d.findElements(By.xpath("//div[@id='categoryList']//div//div//div//div//div//span[2]//label"));
	}

	public List<WebElement> aggregatedAccounts() {
		return d.findElements(By.xpath("//span[@class='account-name']//label"));

	}

	public void selectCategoryByNumber(int highLevelCategoryNum, int masterLevelCategoryNum) {
		WebElement element = d
				.findElement(By.xpath("//div[contains(@class,'multisection-dropdown outline-none1')]/ul/li/ul/li["
						+ highLevelCategoryNum + "]/ul/li[" + masterLevelCategoryNum + "]"));
		System.out.println("Category Name at :: " + highLevelCategoryNum + ":" + masterLevelCategoryNum + " ========="
				+ element.getText());
		SeleniumUtil.waitForPageToLoad(1000);
		/**
		 * @author sswain1 Added below scroll functionality to easily locate element
		 */
		SeleniumUtil.scrollElementIntoView(d, element, true);
		SeleniumUtil.click(element);
	}

	// div[contains(@class,'multisection-dropdown outline-none1')]/ul/li/ul/li["+
	// highLevelCategoryNum + "]/ul/li[" + masterLevelCategoryNum + "]/div/span[1]
	public WebElement groceryCategory() {
		return SeleniumUtil.getWebElement(d, "groceryCategory", "Budget", null);
	}

	public void typeFlexibleSpendingCategoriesValues(String value) {

		SeleniumUtil.waitForPageToLoad();
		/*
		 * d.findElement(By.id("cat-amt-edit")).clear();
		 * d.findElement(By.id("cat-amt-edit")).sendKeys(value);
		 */
		/**
		 * @author sswain1 Implementing SeleniumUtil.getVisibileWebElement instead of
		 *         hard coded xpath as commented above. This ease adding different xpath
		 *         for the same element both web and mobile
		 */
		WebElement categoryAmountTextBox = SeleniumUtil.findElement(d, "categoryAmounttextBox", "Budget", null, 5);// getVisibileWebElement(d,
																													// "categoryAmounttextBox",
																													// "Budget",
																													// null);
		categoryAmountTextBox.clear();
		categoryAmountTextBox.sendKeys(value);
	}

	public WebElement settingTb() {
		return d.findElement(By.id("drilldown6"));

	}

	public WebElement category() {
		/**
		 * @author sswain1 Implementing SeleniumUtil.getVisibileWebElement instead of
		 *         hard coded xpath as commented above. This element's xpath is same in
		 *         both web and mobile
		 */
		return SeleniumUtil.getVisibileWebElement(d, "firstSearchedCategory", "Budget", null);
		// return d.findElement(By.xpath("//h3[@id='li-0-1-0-h']"));
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

	public WebElement myIncomeText() {
		return SeleniumUtil.getVisibileWebElement(d, "myIncomeText", "Budget", null);
	}

	public WebElement myRecurringBillText() {
		return SeleniumUtil.getVisibileWebElement(d, "myRecurringBillText", "Budget", null);
	}

	public WebElement flexibleSpendingText() {
		return SeleniumUtil.getVisibileWebElement(d, "flexibleSpendingText", "Budget", null);
	}

	public WebElement basedMonthDataText() {
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

		return SeleniumUtil.findElement(d, "addiconStep2elementForBill", "Budget", null, 5);// getVisibileWebElement(d,
																							// "addiconStep2elementForBill",
																							// "Budget", null);

	}

	public WebElement addSpendingButton() {
		return SeleniumUtil.getVisibileWebElement(d, "addSpendingButton", "Budget", null);
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

	public WebElement selectCategoryDropDown() {
		return SeleniumUtil.findElement(d, "selectCategoryDropDown", "Budget", null, 5);// getVisibileWebElement(d,
																						// "selectCategoryDropDown",
																						// "Budget", null);
	}

	public WebElement backButton() {
		return SeleniumUtil.getVisibileWebElement(d, "backButton", "Budget", null);
	}

	public WebElement incomeNextButton() {
		return SeleniumUtil.getWebElement(d, "incomeNextButton", "Budget", null);
	}

	public WebElement editincome() {
		/*
		 * By by = SeleniumUtil.getByObject("Budget", null, "editincome"); return
		 * SeleniumUtil.findElement(d, by, 120);
		 */// getVisibileWebElement(d, "editincome", "Budget",
			// null);//waitForElementVisible(d, by, 60);
		/**
		 * @author sswain1
		 */
		return SeleniumUtil.findElement(d, "editincome", "Budget", null, 30);
	}

	public WebElement editflexicon() {
		return SeleniumUtil.getVisibileWebElement(d, "editflexicon", "Budget", null);
	}

	public WebElement editincometext() {
		/**
		 * @author sswain1
		 */
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

	/**
	 * @author sswain1
	 * @return
	 */
	public WebElement waitUntilAddButtonDisplayed(int timeOutInSecs) {
		By by = SeleniumUtil.getByObject("Budget", null, "addButton");
		return SeleniumUtil.findElement(d, by, timeOutInSecs);// waitUntilElementVisible(d, by, timeOutInSecs);
	}

	public WebElement myIncomeEditIcon() {
		return SeleniumUtil.getVisibileWebElement(d, "myIncomeEditIcon", "Budget", null);
	}

	public WebElement myIncomeEditIcon1() {
		return SeleniumUtil.getVisibileWebElement(d, "myIncomeEditIcon1", "Budget", null);
	}

	public WebElement totalRecurringBillText() {
		/**
		 * @author sswain1 Implemented Dynamic wait for this element.
		 */
		By by = SeleniumUtil.getByObject("Budget", null, "totalRecurringBillText");
		return SeleniumUtil.findElement(d, by, 120);
		// return SeleniumUtil.getWebElement(d, "totalRecurringBillText", "Budget",
		// null);
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
		return SeleniumUtil.findElement(d, "editAmountInputBox", "Budget", null, 5);// getVisibileWebElement(d,
																					// "editAmountInputBox", "Budget",
																					// null);
	}

	/**
	 * @author sswain1 This method searches the required category and click on that.
	 */
	public void searchCategory(String category) {

		SeleniumUtil.getVisibileWebElement(d, "searchCategorytextBox", "Budget", null).sendKeys(category);
	}

	public WebElement addiconStep2element() {
		return SeleniumUtil.getVisibileWebElement(d, "addiconStep2element", "Budget", null);
	}

	public WebElement myIncomeEditIconmob() {
		return SeleniumUtil.getVisibileWebElement(d, "myIncomeEditIconmob", "Budget", null);
	}

}
