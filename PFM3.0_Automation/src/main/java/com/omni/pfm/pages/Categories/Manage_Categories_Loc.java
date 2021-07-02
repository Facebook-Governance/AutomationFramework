/*******************************************************************************
 * Copyright (c) 2018 Yodlee Inc. All Rights Reserved.
 * 
 * This software is the confidential and proprietary information of
 * Yodlee, Inc. Use is subject to license terms.
 * 
 * @author
 ******************************************************************************/
package com.omni.pfm.pages.Categories;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.omni.pfm.PageProcessor.PageParser;
import com.omni.pfm.utility.SeleniumUtil;

public class Manage_Categories_Loc {

	private static WebDriver d;
	private static final Logger logger = LoggerFactory.getLogger(Manage_Categories_Loc.class);
	static final String pageName = "Categories";
	static final String frameName = null;

	public Manage_Categories_Loc(WebDriver d) {
		Manage_Categories_Loc.d = d;
	}

	public void navigateToCategories() {
		PageParser.navigateToPage(pageName, d);
		logger.info("Navigated to Categories Page");
	}

	public void forceNavigateToCategories() {
		PageParser.forceNavigate(pageName, d);
	}

	public String getSubCategoryTextMLC() {
		WebElement el = SeleniumUtil.getVisibileWebElement(d, "getSubCategoryTextMLC_MC", pageName, frameName);
		return el.getText();
	}

	public WebElement header() {
		return SeleniumUtil.getVisibileWebElement(d, "header_MC", "Categories", null);
	}

	public String getHeaderText() {
		String val = null;
		WebElement header = null;

		header = SeleniumUtil.waitUntilElementPresent(d, "css", "h2[class*='title']", 10);

		if (header == null)
			header = SeleniumUtil.getVisibileWebElement(d, "getHeaderText_MC", pageName, frameName);
		if (header != null)
			val = header.getText().trim();

		return val;
	}

	public String getCategoriesMessage() {
		return SeleniumUtil.getVisibileWebElement(d, "getCategoriesMessage_2_MC", pageName, frameName).getText().trim();
	}

	public WebElement leftSelectionSection() {
		return SeleniumUtil.getVisibileWebElement(d, "leftSelectionSection_MC", "Categories", null);
	}

	public boolean isMobileMenu() {
		// return PageParser.isElementPresent("mibileMenubar", "Categories", null);
		return PageParser.isElementPresent("mobileMenubar", pageName, frameName);
	}

	public WebElement topMobileMenubar() {
		return SeleniumUtil.getVisibileWebElement(d, "mobileMenubar", "Categories", null);
	}

	public String[] getCategoryGroupByOrder() {
		List<WebElement> l = SeleniumUtil.getWebElements(d, "getCategoryGroupByOrder_1_MC", pageName, null);

		String names[] = new String[l.size()];

		for (int i = 0; i < l.size(); i++) {
			names[i] = l.get(i).getText();
		}
		return names;
	}

	public WebElement isExpenseSelected() {
		return SeleniumUtil.getVisibileWebElement(d, "isExpenseSelected_MC", pageName, null);
	}

	public String[] getHLC() {
		String val = null;
		List<WebElement> l = SeleniumUtil.getWebElements(d, "getHLC_MC", pageName, null);

		String hlcs[] = new String[l.size()];
		for (int i = 0; i < l.size(); i++) {
			hlcs[i] = l.get(i).getText().trim();
			val = val + "," + hlcs[i];
		}
		return hlcs;
	}

	public String[] getMLCs() {
		String val = null;
		List<WebElement> l = SeleniumUtil.getWebElements(d, "getMLCs_MC", pageName, frameName);

		String mlcs[] = new String[l.size()];
		for (int i = 0; i < l.size(); i++) {
			mlcs[i] = l.get(i).getText().trim();
			val = val + "," + mlcs[i];
		}
		logger.info("THe getMLCs" + val);

		return mlcs;
	}

	public void selectSideBarByNumber(int i) {
		SeleniumUtil.waitForPageToLoad(2000);
		SeleniumUtil.click(d.findElement(By.xpath("//div[@class='top-level-category-row'][" + i + "]/li//a")));
		SeleniumUtil.waitForPageToLoad(2000);
	}

	public WebElement backToCategories() {
		return SeleniumUtil.getVisibileWebElement(d, "backToCategories", pageName, frameName);
	}

	public WebElement textCategory() {
		return SeleniumUtil.getVisibileWebElement(d, "textCategory", pageName, frameName);
	}

	public void clickHighLevelCategoryByNum(int i) {
		SeleniumUtil.click(By.xpath(
				"//div[@class='categories-container']//div[@class='high-level-categories-container'][" + i + "]/a"));
	}

	public void clickOnEarnedIncomeTab(int i) {
		SeleniumUtil.waitForPageToLoad();
		WebElement highLevel = d.findElement(By.xpath(
				"//div[@class='categories-container']//div[@class='high-level-categories-container'][" + i + "]/a"));
		SeleniumUtil.click(highLevel);
	}

	public void clickHighLevelCategoryByName(String name) {
		SeleniumUtil.waitForPageToLoad(2000);
		SeleniumUtil.click(
				d.findElement(By.xpath("//div[contains(@class,'highlevel')]/p[contains(text(),'" + name + "')]")));
		SeleniumUtil.waitForPageToLoad(2000);
	}

	public boolean isEditedHLCategoryNameExist(String name) {
		boolean b = d.findElement(By.xpath("//div[contains(@class,'highlevel')]/p[contains(text(),'" + name + "')]"))
				.isDisplayed();
		return b;
	}

	public String getHighLevelCategoryName(int i) {
		return d.findElement(By.xpath(
				"//div[@class='categories-container']//div[@class='high-level-categories-container'][" + i + "]/a"))
				.getText().trim();
	}

	public WebElement showAsText() {
		return SeleniumUtil.getVisibileWebElement(d, "showAsText_MC", "Categories", null);
	}

	public WebElement editHLCatInputField() {
		return SeleniumUtil.getVisibileWebElement(d, "editHLCatInputField_MC", "Categories", null);
	}

	public By saveChangesBtn() {
		return SeleniumUtil.getByObject("Categories", null, "saveChangesBtn");
	}

	public WebElement cancelButton() {
		return SeleniumUtil.getVisibileWebElement(d, "cancelButton", "Categories", null);
	}

	public WebElement backButton() {
		return SeleniumUtil.getVisibileWebElement(d, "backButton", "Categories", null);
	}

	public WebElement cancelButtonInMLC() {
		return SeleniumUtil.getVisibileWebElement(d, "cancelButtonInMLC_MC", "Categories", null);
	}

	public WebElement saveChangesButtonInMLC() {
		return SeleniumUtil.getVisibileWebElement(d, "saveChangesButtonInMLC_MC", "Categories", null);
	}

	public WebElement saveChangesButton() {
		return SeleniumUtil.getVisibileWebElement(d, "saveChangesButton_MC", "Categories", null);
	}

	public WebElement duplicateCatNameErr() {
		return SeleniumUtil.getVisibileWebElement(d, "duplicateCatNameError_MC", "Categories", null);
	}

	public WebElement duplicateCatNameError() {
		return SeleniumUtil.getVisibileWebElement(d, "duplicateCatNameErrorML_MC", "Categories", null);
	}

	public WebElement Cat_HeaderText() {
		return SeleniumUtil.getWebElement(d, "Cat_headerText", "Categories", null); // special char
	}

	public boolean isAlphabetical(List<String> ll) {
		String previous = ""; // empty string
		for (final String current : ll) {
			if (current.compareTo(previous) < 0)
				return false;

			previous = current;
		}
		return true;
	}

	public void clickMLC(int hlcNum, int mlcNum) {
		SeleniumUtil.click(By.xpath("//div[@class='high-level-categories-container'][" + hlcNum
				+ "]//div[@class='master-level-categories-container'][" + mlcNum + "]"));

		SeleniumUtil.waitForPageToLoad(2000);
	}

	public String getMLCName(int hlcNum, int mlcNum) {
		return d.findElement(By.xpath("//div[@class='high-level-categories-container'][" + hlcNum
				+ "]//div[@class='master-level-categories-container'][" + mlcNum
				+ "]//div[contains(@class,'masterlevel')]")).getText().trim();
	}

	/*
	 * public WebElement getMLCName_Mob() { return
	 * SeleniumUtil.getVisibileWebElement(d, "getMLCName", pageName, frameName); }
	 */
	public String getShowAsHostText() {
		WebElement ele = SeleniumUtil.getVisibileWebElement(d, "getShowAsGhostText_MC", pageName, frameName);
		return ele.getAttribute("value");
	}

	public WebElement getSubCatOne() {
		return SeleniumUtil.getVisibileWebElement(d, "getSubCatOne", pageName, frameName);
	}

	public String getOriginalName() {
		return SeleniumUtil.getVisibileWebElement(d, "getOriginalName_MC", pageName, frameName).getText().trim();
	}

	public WebElement originalNameMLCText() {
		return SeleniumUtil.getVisibileWebElement(d, "originalNameMLCText_MC", "Categories", null);
	}

	public WebElement originalNameMLCText_Mob() {
		return SeleniumUtil.getVisibileWebElement(d, "originalNameMLCText_MC_Mob", "Categories", null);
	}

	public String getMaxLimitError() {
		SeleniumUtil.waitForPageToLoad(1000);
		return SeleniumUtil.getVisibileWebElement(d, "getMaxLimitError_MC", "Categories", null).getText().trim();
	}

	public boolean isDeleteSubCategoryBtnDisplayed() {
		return SeleniumUtil.getVisibileWebElement(d, "isDeleteSubCategoryBtnDisplayed_MC", "Categories", null)
				.isDisplayed();
	}

	public void clickDeleteSubCategoryButton(int i) {
		List<WebElement> l = SeleniumUtil.getWebElements(d, "clickDeleteSubCategoryButton_MC", pageName, frameName);
		SeleniumUtil.click(l.get(i - 1));
	}

	public boolean isEditSubCategoryBtnDisplayed() {
		return SeleniumUtil.getVisibileWebElement(d, "isEditSubCategoryBtnDisplayed_MC", "Categories", null)
				.isDisplayed();
	}

	public void typeSubCategoryName(String text) {
		SeleniumUtil.getVisibileWebElement(d, "typeSubCategoryName_MC", "Categories", null).sendKeys(text);
	}

	public WebElement closeIconInDeletePopup() {
		return SeleniumUtil.getVisibileWebElement(d, "closeIconInDeletePopup_MC", "Categories", null);
	}

	public String getSubCategoryName() {
		return SeleniumUtil.getVisibileWebElement(d, "getSubCategoryName_MC", "Categories", null).getAttribute("value");
	}

	public WebElement addSubCatButtonInMLC() {
		return SeleniumUtil.getVisibileWebElement(d, "addSubCatButtonInMLC_MC", "Categories", null);
	}

	public WebElement deletePopUpMessage() {
		return SeleniumUtil.getVisibileWebElement(d, "deletePopUpMessage_MC", "Categories", null);
	}

	public WebElement deleteHeading() {
		return SeleniumUtil.getVisibileWebElement(d, "deleteHeading_MC", "Categories", null);
	}

	public WebElement deleteBtn() {
		return SeleniumUtil.getVisibileWebElement(d, "deleteBtn_MC", "Categories", null);
	}

	public WebElement deletesuccessTxt() {
		return SeleniumUtil.getVisibileWebElement(d, "deletesuccessTxt_MC", "Categories", null);
	}

	public boolean subcategoriesOptionalTextIsDisplayed() {
		SeleniumUtil.waitForPageToLoad(2000);
		boolean r1 = SeleniumUtil
				.getVisibileWebElement(d, "subcategoriesOptionalTextIsDisplayed_1_MC", "Categories", null)
				.isDisplayed();

		boolean r2 = SeleniumUtil
				.getVisibileWebElement(d, "subcategoriesOptionalTextIsDisplayed_2_MC", "Categories", null)
				.isDisplayed();

		if (r1 && r2) {
			return true;
		}
		return false;
	}

	public WebElement manage_Showas_name() {
		return SeleniumUtil.getVisibileWebElement(d, "manage_Showas_name", "Categories", null);
	}

	public WebElement editMLCatInputField() {
		return SeleniumUtil.getVisibileWebElement(d, "editMLCatInputField_MC", "Categories", null);
	}

	/*
	 * public WebElement editMLCatInputField_1() { return
	 * SeleniumUtil.getVisibileWebElement(d,"editMLCatInputField_MC_Mob",
	 * "Categories", null); }
	 */

	public WebElement subCategoryMLCInputBox() {
		return SeleniumUtil.getVisibileWebElement(d, "subCategoryMLCInputBox_MC", "Categories", null);
	}

	public List<WebElement> categoryErrorMsg() {
		return SeleniumUtil.getWebElements(d, "categoryErrorMsg_MC", "Categories", null);
	}

	public List<WebElement> editSubcat() {
		return SeleniumUtil.getWebElements(d, "editSubcat_MC", "Categories", null);
	}

	public WebElement expenseCatLink() {
		return SeleniumUtil.getVisibileWebElement(d, "expenseCatLink_MC", "Categories", null);
	}

	public WebElement subCatLink() {
		return SeleniumUtil.getVisibileWebElement(d, "subCatLink_MC", "Categories", null);
	}

	public WebElement subCategoryTxtBox() {
		return SeleniumUtil.getVisibileWebElement(d, "subCategoryTxtBox_MC", "Categories", null);
	}

	public WebElement subCat1() {
		return SeleniumUtil.getVisibileWebElement(d, "subCat1_MC", "Categories", null);
	}

	public WebElement subCat2() {
		return SeleniumUtil.getVisibileWebElement(d, "subCat2_MC", "Categories", null);
	}

	public WebElement deleteSubCategory1() {
		return SeleniumUtil.getVisibileWebElement(d, "deleteSubCategory1_MC", "Categories", null);
	}

	public WebElement deleteSubCategory2() {
		return SeleniumUtil.getVisibileWebElement(d, "deleteSubCategory2_MC", "Categories", null);
	}

	public WebElement getSubCAtegory1() throws Exception {
		return SeleniumUtil.waitUntilPresenceOfElement(d, "subCat1_MC", pageName, frameName, 10);
	}

	public WebElement getSubCAtegory2() throws Exception {
		return SeleniumUtil.waitUntilPresenceOfElement(d, "subCat2_MC", pageName, frameName, 10);
	}

	public WebElement givingCategory() {
		return SeleniumUtil.getVisibileWebElement(d, "givingCategory_MC", "Categories", null);
	}

	public WebElement givingCategoryOne() {
		return SeleniumUtil.getWebElement(d, "givingCategoryOne", "Categories", null);
	}

	public WebElement giftsCategory() {
		return SeleniumUtil.getVisibileWebElement(d, "giftsCategory_MC", "Categories", null);
	}

	public WebElement giftsCategoryEdited() {
		return SeleniumUtil.getVisibileWebElement(d, "giftsCategoryEdited_MC", "Categories", null);
	}

	public WebElement givingCategoryEdited() {
		return SeleniumUtil.getVisibileWebElement(d, "givingCategoryEdited_MC", "Categories", null);
	}

	public List<WebElement> originalName() {
		return SeleniumUtil.getWebElements(d, "OriginalName_MC", "Categories", null);
	}

	public WebElement originalNameMLc() {
		return SeleniumUtil.getVisibileWebElement(d, "OriginalNameMLc_MC", "Categories", null);
	}

	public List<WebElement> firstMasterLevelExpenseCategory() {
		return SeleniumUtil.getWebElements(d, "firstMasterLevelExpenseCategory", "Categories", null);
	}

	/*
	 * Archana
	 */

	public String getCategoriesMessage_Mobile() {
		return SeleniumUtil.getVisibileWebElement(d, "getCategoriesMessage_3_MC", pageName, frameName).getText().trim();
	}

	public WebElement topSelectionSection() {
		return SeleniumUtil.getVisibileWebElement(d, "topSelectionSection_MC", "Categories", null);
	}

	public WebElement isExpenseSelected_Mobile() {
		return SeleniumUtil.getVisibileWebElement(d, "isExpenseSelected_MC_Mob", pageName, null);
	}

	public WebElement selectIncomeTab() {
		return SeleniumUtil.getVisibileWebElement(d, "selectIncomeTab", pageName, null);
	}

	public WebElement selectTransferTab() {
		return SeleniumUtil.getVisibileWebElement(d, "selectTransferTab", pageName, null);
	}

	public WebElement selectDeferredTab() {
		return SeleniumUtil.getVisibileWebElement(d, "selectDeferredTab", pageName, null);
	}

	public void selectTopBarByNumber(int i) {

		SeleniumUtil.waitForPageToLoad();
		SeleniumUtil.click(d.findElement(By.xpath("//div[@class='top-level-category'][" + i + "]/li")));

		SeleniumUtil.waitForPageToLoad(3000);
	}

	public String[] getCategoryGroupByOrder_Mobile() {

		List<WebElement> l = SeleniumUtil.getWebElements(d, "getCategoryGroupByOrder_2_MC", pageName, null);

		String names[] = new String[l.size()];

		for (int i = 0; i < l.size(); i++) {
			names[i] = l.get(i).getText();
		}
		return names;
	}

	public void clickHighLevelCategoryByName_Mob(int i) {

		SeleniumUtil.waitForPageToLoad(2000);

		SeleniumUtil.click(d.findElement(
				By.xpath("//div[@class='categories-container']//div[@class='high-level-categories-container'][" + i
						+ "]//div[@class='master-level-categories-container']/a")));
		SeleniumUtil.waitForPageToLoad(2000);
	}

	public String getHighLevelCategoryName_Mob(int i) {

		SeleniumUtil.waitForPageToLoad();
		return d.findElement(
				By.xpath("//div[@class='categories-container']//div[@class='high-level-categories-container'][" + i
						+ "]//div[@class='master-level-categories-container']/a"))
				.getText().trim();
	}

	public WebElement duplicateCatNameError_Mob() {
		return SeleniumUtil.getVisibileWebElement(d, "duplicateCatNameError_MC_Mob", "Categories", null);
	}

	public WebElement backToCategoriesForMobile() {
		return SeleniumUtil.getWebElement(d, "backToCategoriesForMobile", pageName, frameName);
	}

}
