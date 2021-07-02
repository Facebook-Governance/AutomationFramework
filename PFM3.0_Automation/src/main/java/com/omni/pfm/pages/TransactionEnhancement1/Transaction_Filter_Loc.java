/**
* Copyright (c) 2018 Yodlee Inc. All Rights Reserved.
*
* This software is the confidential and proprietary information of
* Yodlee, Inc. Use is subject to license terms.
*
* @author Renuka K
*/
package com.omni.pfm.pages.TransactionEnhancement1;

import java.text.DateFormatSymbols;
import java.util.ArrayList;
import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.omni.pfm.PageProcessor.PageParser;
import com.omni.pfm.utility.PropsUtil;
import com.omni.pfm.utility.SeleniumUtil;

public class Transaction_Filter_Loc extends SeleniumUtil {
	static Logger logger = LoggerFactory.getLogger(Transaction_Filter_Loc.class);
	public WebDriver d = null;
	static String pageName = "Transaction"; // Page Name
	static String frameName = null;
	public static final By tranCatFltSearchedHLC = getByObject(pageName, frameName, "tranCatFltSearchedHLC");
	public static final By tranCatFltSearchedMLC = getByObject(pageName, frameName, "tranCatFltSearchedMLC");

	public Transaction_Filter_Loc(WebDriver d) {
		this.d = d;
	}

	public void filterIcon() {
		if (PropsUtil.getEnvPropertyValue("MOBILEORWEB").equalsIgnoreCase("MOBILE")) {
			getVisibileWebElement(d, "filter", pageName, frameName);
		}
	}

	public WebElement transAccinTransFin() {
		return getWebElement(d, "transAccinTransFin", pageName, frameName);
	}

	public void slideIcon() {
		if (PropsUtil.getEnvPropertyValue("MOBILEORWEB").equalsIgnoreCase("MOBILE")) {
			getVisibileWebElement(d, "backBtn", pageName, frameName);
		}
	}

	public WebElement applyButton() {
		return getVisibileWebElement(d, "applybutton", pageName, frameName);
	}

	public WebElement filterByText() {
		return getVisibileWebElement(d, "filterbytxt", pageName, frameName);
	}

	public WebElement resetText() {
		return getVisibileWebElement(d, "resettxt", pageName, frameName);
	}

	public WebElement allAccountsText() {
		return getVisibileWebElement(d, "allaccountstxt", pageName, frameName);
	}

	public WebElement threeMonthsText() {
		return getVisibileWebElement(d, "threemonthstxt", pageName, frameName);
	}

	public WebElement threeMonthsMaximizeIcon() {
		fluentWait(d);
		if (PropsUtil.getEnvPropertyValue("MOBILEORWEB").equalsIgnoreCase("WEB")) {
			WebElement threeMonthsMaximizeIcon = getVisibileWebElement(d, "maximizeicon2", pageName, frameName);
			return threeMonthsMaximizeIcon;
		} else {
			return getVisibileWebElement(d, "duration", pageName, frameName);
		}
	}

	public WebElement allCategoriesText() {
		return getVisibileWebElement(d, "AllCategoriesTxt", pageName, frameName);
	}

	public WebElement allCategoriesmaximizeIcon() {
		fluentWait(d);
		if (PropsUtil.getEnvPropertyValue("MOBILEORWEB").equalsIgnoreCase("WEB")) {
			return getVisibileWebElement(d, "catFilter", pageName, frameName);
		} else {
			waitForPageToLoad();
			return getVisibileWebElement(d, "catFilter1", pageName, frameName);
		}
	}

	public WebElement tagsText() {
		return getVisibileWebElement(d, "tagsFilter", pageName, frameName);
	}


	public WebElement tagMaximizeIcon() {
		fluentWait(d);
		if (PropsUtil.getEnvPropertyValue("MOBILEORWEB").equalsIgnoreCase("WEB")) {
			 WebElement tagMaximizeIcon = getVisibileWebElement(d, "maximizeicon4", pageName, frameName);
			return tagMaximizeIcon;
		} else {
			return getVisibileWebElement(d, "tagsFilter", pageName, frameName);
		}
	}

	public WebElement amountRangeText() {
		return getVisibileWebElement(d, "amountrangeTxt", pageName, frameName);
	}

	public WebElement amountRangeMaximizeIcon() {
		fluentWait(d);
		if (PropsUtil.getEnvPropertyValue("MOBILEORWEB").equalsIgnoreCase("WEB")) {
			WebElement amountRangeMaximizeIcon = getVisibileWebElement(d, "maximizeicon5", pageName, frameName);
			return amountRangeMaximizeIcon;
		} else {
			return getVisibileWebElement(d, "amtRangeFilter", pageName, frameName);
		}
	}

	public WebElement transactionTypeText() {
		return getVisibileWebElement(d, "transactionTypeText", pageName, frameName);
	}

	public WebElement transactionTypeDropDownBox() {
		return getVisibileWebElement(d, "transactiontypebx", pageName, frameName);
	}

	public WebElement tickmarkTransactionTypeDropDown() {
		return getVisibileWebElement(d, "tickmark", pageName, frameName);
	}

	public WebElement transactionTypeMaximizeIcon() {
		fluentWait(d);
		if (PropsUtil.getEnvPropertyValue("MOBILEORWEB").equalsIgnoreCase("MOBILE")) {
			WebElement transactionTypeMaximizeIcon = getVisibileWebElement(d, "maximizeicon6", pageName, frameName);
			return transactionTypeMaximizeIcon;
		} else {
			return getVisibileWebElement(d, "txtTypeFilter", pageName, frameName);
		}
	}

	public List<WebElement> amountFromAndToTextBox() {
		return getWebElements(d, "amountfromtoTextbox", pageName, frameName);
	}

	public WebElement amountFromTextBox() {
		return getVisibileWebElement(d, "amountfromTextbox", pageName, frameName);
	}

	public WebElement amountToTextBox() {
		return getVisibileWebElement(d, "amounttoTextbox", pageName, frameName);
	}

	public WebElement amountRangeSideMaximizeIcon() {
		return getVisibileWebElement(d, "amountRangeSideMaximizeIcn", pageName, frameName);
	}

	public List<WebElement> allAccountsAndGroupRadioButton() {
		return getWebElements(d, "allAccountsAndGroupRadioBtn", pageName, frameName);
	}

	public WebElement allAccountsBox() {
		if (PropsUtil.getEnvPropertyValue("MOBILEORWEB").equalsIgnoreCase("WEB")) {
			WebElement allAccountsBox = getVisibileWebElement(d, "allaccountsbx", pageName, frameName);
			return allAccountsBox;
		} else {
			return getVisibileWebElement(d, "accTab", pageName, frameName);
		}
	}

	public WebElement allAccountsCheckBox() {
		return getVisibileWebElement(d, "allaccountscheckbx", pageName, frameName);
	}

	public WebElement creditAccountCheckBox() {
		return getVisibileWebElement(d, "creditaccountcheckbx", pageName, frameName);
	}

	public WebElement subAccountCheckBox() {
		return getVisibileWebElement(d, "subaccountcheckbx", pageName, frameName);
	}

	public String allCategoriesDropDownText() {
		return getVisibileWebElement(d, "allCat", pageName, frameName).getText();
	}

	public boolean checkAllCategorySelected() {
		List<WebElement> l = getWebElements(d, "allCat1", pageName, frameName);
		for (int i = 0; i < l.size(); i++) {
			if (l.get(i).getAttribute("aria-checked").equalsIgnoreCase("true")) {
				continue;
			} else {
				return false;
			}
		}
		return true;
	}

	public boolean checkAllCategoriesUnSelected() {
		List<WebElement> l = getWebElements(d, "catFilter2", pageName, frameName);
		for (int i = 0; i < l.size(); i++) {
			if (l.get(i).getAttribute("aria-checked").equalsIgnoreCase("false")) {
				continue;
			} else {
				return false;
			}
		}
		return true;
	}

	public boolean checkMasterLevelCategoriesSelected() {
		WebElement element = getVisibileWebElement(d, "catFiltContent", pageName, frameName);
		if (element.getAttribute("aria-checked").equalsIgnoreCase("true")) {
			return true;
		} else {
			return false;
		}
	}

	public boolean checkHighLevelCategoriesSelected() {
		WebElement element = getVisibileWebElement(d, "catFiltContent1", pageName, frameName);
		if (element.getAttribute("aria-checked").equalsIgnoreCase("true")) {
			return true;
		} else {
			return false;
		}
	}

	public void verifyChartTitleAndDateRange(String text) {
		fluentWait(d);
		String time = getVisibileWebElement(d, "durationText", pageName, frameName).getText();
		System.out.print("option selected time filter: " + text + " || Actual: " + time);
	}

	static String getMonthForInt(int num) {
		String month = null;
		DateFormatSymbols dfs = new DateFormatSymbols();
		String[] months = dfs.getMonths();
		if (num >= 0 && num <= 11) {
			month = months[num];
		}
		return month;
	}

	public WebElement thisMonthBox() {
		return getVisibileWebElement(d, "thisMonthBx", pageName, frameName);
	}

	public void verifyChatDateRange(String text) {
		fluentWait(d);
		String time = getVisibileWebElement(d, "durationFilter", pageName, frameName).getText();
		System.out.print("option selected time filter: " + text + " || Actual: " + time);
	}

	static String getMonthInt(int num) {
		String month = null;
		DateFormatSymbols dfs = new DateFormatSymbols();
		String[] months = dfs.getMonths();
		if (num >= 0 && num <= 11) {
			month = months[num];
		}
		return month;
	}

	public WebElement customStartDate() {
		return getVisibileWebElement(d, "customStartDt", pageName, frameName);
	}

	public WebElement customStartDateCalender() {
		return getVisibileWebElement(d, "customStartDtCalender", pageName, frameName);
	}

	public WebElement customEndDate() {
		return getVisibileWebElement(d, "customEndDt", pageName, frameName);
	}

	public WebElement updateButton() {
		return getVisibileWebElement(d, "upadatebtn", pageName, frameName);
	}

	public WebElement tagDropDownBox() {
		return getVisibileWebElement(d, "tagdropdownbx", pageName, frameName);
	}

	public WebElement vacationBox() {
		return getVisibileWebElement(d, "vacationbx", pageName, frameName);
	}

	public WebElement deleteIcon() {
		return getVisibileWebElement(d, "deleteicon", pageName, frameName);
	}

	public WebElement customDatePopUpCloseIcon() {
		return getVisibileWebElement(d, "customdatepopupcloseicon", pageName, frameName);
	}

	public WebElement groupAccountName() {
		return getVisibileWebElement(d, "groupaccountname", pageName, frameName);
	}

	public WebElement groupRadioButton() {
		return getVisibileWebElement(d, "groupradiobtn", pageName, frameName);
	}

	public WebElement allCategoriesCheckBox() {
		return getVisibileWebElement(d, "allcategoriescheckbx", pageName, frameName);
	}

	public WebElement highLevelCategoryCheckBox() {
		return getVisibileWebElement(d, "highlevelcategorycheckbx", pageName, frameName);
	}

	public WebElement masterLevelCategoryCheckBox() {
		return getVisibileWebElement(d, "masterlevelcategorycheckbx", pageName, frameName);
	}

	public List<WebElement> dateRangeBox() {
		return getWebElements(d, "daterangebx", pageName, frameName);
	}

	public WebElement withdrawAmountLabel() {
		return getVisibileWebElement(d, "withdrawamountlabel", pageName, frameName);
	}

	public WebElement depositAmountLabel() {
		return getVisibileWebElement(d, "depositamountlabel", pageName, frameName);
	}

	public void selectTransactionTypeByNumber(String typeNum) {
		getVisibileWebElement(d, "typeNum", pageName, frameName);
	}

	public List<WebElement> transactionType() {
		return getWebElements(d, "transactiontype", pageName, frameName);
	}

	public WebElement transactionFilterPopUpOpen() {
		return getVisibileWebElement(d, "transactionFilterPopUpOpen", pageName, frameName);
	}

	public WebElement transactionFilterPopUpHeader() {
		return getVisibileWebElement(d, "transactionFilterPopUpHeader", pageName, frameName);
	}

	public WebElement transactionFilterPopUpHeader1() {
		return getVisibileWebElement(d, "transactionFilterPopUpHeader1", pageName, frameName);
	}

	public WebElement transactionFilterPopUpClose() {
		return getVisibileWebElement(d, "transactionFilterPopUpClose", pageName, frameName);
	}

	public WebElement transactionFilterGroupPopUpClose() {
		return getVisibileWebElement(d, "transactionFilterGroupPopUpClose", pageName, frameName);
	}

	public List<WebElement> transactionFilterAccontCheckBox() {
		return getWebElements(d, "transactionFilterAccontCheckBox", pageName, frameName);
	}

	public List<WebElement> transactionFilterAccontName() {
		return getWebElements(d, "transactionFilterAccontName", pageName, frameName);
	}

	public List<WebElement> transactionGroupFilterTickMark() {
		return getWebElements(d, "transactionGroupFilterTickMark", pageName, frameName);
	}

	public List<WebElement> transactionCategoryFilterAllMLCLabel() {
		return getWebElements(d, "transactionCategoryFilterAllMLCLabel", pageName, frameName);
	}

	public WebElement transactionCategoryFilterAllCategoriesCheckBox() {
		return getVisibileWebElement(d, "transactionCategoryFilterAllCategoriesCheckBox", pageName, frameName);
	}

	public WebElement transactionCategoryFilterAllCategoriesLabel() {
		return getVisibileWebElement(d, "transactionCategoryFilterAllCategoriesLabel", pageName, frameName);
	}

	public List<WebElement> transactionCategoryFilterAllHLCCheckBox() {
		return getWebElements(d, "transactionCategoryFilterAllHLCCheckBox", pageName, frameName);
	}

	public List<WebElement> transactionCategoryFilterAllMLCCheckBox() {
		return getWebElements(d, "transactionCategoryFilterAllMLCCheckBox", pageName, frameName);
	}

	public List<WebElement> transactionCategoryFilterAllHLCLabel() {
		return getWebElements(d, "transactionCategoryFilterAllHLCLabel", pageName, frameName);
	}

	public List<WebElement> transactionCategoryFilterAllMLCHLCCheckBox(int MLC) {
		String abC = getVisibileElementXPath(d, "Transaction", null, "transactionCategoryFilterAllMLCHLCCheckBox");
		abC = abC.replaceAll("MLC", Integer.toString(MLC));
		System.out.println(abC);
		return d.findElements(By.xpath(abC));
	}

	public List<WebElement> transactionCategoryFilterAllMLCHLCCheckBox(int MLC, int HLC) {
		String abC = getVisibileElementXPath(d, "Transaction", null, "transactionCategoryFilterAllMLCHLCCheckBox");
		abC = abC.replaceAll("MLC", Integer.toString(MLC));
		System.out.println(abC);
		abC = abC.replaceAll("HLC", Integer.toString(HLC));
		System.out.println(abC);
		return d.findElements(By.xpath(abC));
	}

	public WebElement transactionCategoryFilterSubCategoryCheckBox(int MLC, int HLC) {
		String abC = getVisibileElementXPath(d, "Transaction", null, "transactionCategoryFilterSubCategoryCheckBox");
		abC = abC.replaceAll("MLC", Integer.toString(MLC));
		System.out.println(abC);
		abC = abC.replaceAll("HLC", Integer.toString(HLC));
		System.out.println(abC);
		return d.findElement(By.xpath(abC));
	}

	public WebElement transactionCategoryFilterSubCategoryLabel(int MLC, int HLC) {
		String abC = getVisibileElementXPath(d, "Transaction", null, "transactionCategoryFilterSubCategoryLabel");
		abC = abC.replaceAll("MLC", Integer.toString(MLC));
		System.out.println(abC);
		abC = abC.replaceAll("HLC", Integer.toString(HLC));
		System.out.println(abC);
		return d.findElement(By.xpath(abC));
	}

	public WebElement transactionCategoryFilterCategorySearcheField() {
		return getVisibileWebElement(d, "transactionCategoryFilterCategorySearcheField", pageName, frameName);
	}

	public WebElement tranCatFltSearchTextClose() {
		return getVisibileWebElement(d, "tranCatFltSearchTextClose", pageName, frameName);
	}

	public void searchCategory(String searchValue) {
		transactionCategoryFilterCategorySearcheField().clear();
		transactionCategoryFilterCategorySearcheField().sendKeys(searchValue);
	}

	public WebElement searchCategoryMobile() {
		return getVisibileWebElement(d, "transactionCategoryFilterCategorySearchMobile", pageName, frameName);
	}

	public WebElement transactionCategoryFilterCategorySearched(int MLC) {
		String abC = getVisibileElementXPath(d, "Transaction", null, "transactionCategoryFilterCategorySearched");
		abC = abC.replaceAll("MLC", Integer.toString(MLC));
		System.out.println(abC);
		return d.findElement(By.xpath(abC));
	}

	public List<WebElement> tranCatFltSearchedHLC() {
		return getWebElements(d, "tranCatFltSearchedHLC", pageName, frameName);
	}

	public List<WebElement> tranCatFltSearchedHLCHL() {
		return getWebElements(d, "tranCatFltSearchedHLCHL", pageName, frameName);
	}

	public List<WebElement> tranCatFltSearchedMLCHL() {
		return getWebElements(d, "tranCatFltSearchedMLCHL", pageName, frameName);
	}

	public List<WebElement> tranCatFltSearchedMLC() {
		return getWebElements(d, "tranCatFltSearchedMLC", pageName, frameName);
	}

	public List<WebElement> tranCatFltSearchedSubCat() {
		return getWebElements(d, "tranCatFltSearchedSubCat", pageName, frameName);
	}

	public List<WebElement> tranCatFltSearchedSubCatHL() {
		return getWebElements(d, "tranCatFltSearchedSubCatHL", pageName, frameName);
	}

	public WebElement tranCatFltNocatAvailabel() {
		return getVisibileWebElement(d, "tranCatFltNocatAvailabel", pageName, frameName);
	}

	public WebElement transactionCategoryFilterCategorySearcheNoCategories() {
		return getVisibileWebElement(d, "transactionCategoryFilterCategorySearcheNoCategories", pageName, frameName);
	}

	public WebElement transactionCategoryFilterCategorySearcheClose() {
		return getVisibileWebElement(d, "transactionCategoryFilterCategorySearcheClose", pageName, frameName);
	}

	public List<WebElement> transactionDateFilterTickMark() {
		return getWebElements(d, "transactionDateFilterTickMark", pageName, frameName);
	}

	public List<WebElement> addManualHeader_AMT() {
		return getWebElements(d, "addManualHeader_AMT", pageName, frameName);
	}

	public WebElement transactionCustomDateFilterStartDateLabel() {
		return getVisibileWebElement(d, "transactionCustomDateFilterStartDateLabel", pageName, frameName);
	}

	public WebElement transactionCustomDateFilterEndDateLabel() {
		return getVisibileWebElement(d, "transactionCustomDateFilterEndDateLabel", pageName, frameName);
	}

	public WebElement transactionCustomDateFilterUpdate() {
		return getVisibileWebElement(d, "transactionCustomDateFilterUpdate", pageName, frameName);
	}

	public List<WebElement> transactionCustomDateFilterCalanderIcon() {
		return getWebElements(d, "transactionCustomDateFilterCalanderIcon", pageName, frameName);
	}

	public WebElement transactionCustomDateFilterStartDate() {
		return getWebElement(d, "transactionCustomDateFilterStartDate", pageName, frameName);
	}

	public WebElement transactionCustomDateFilterEndDate() {
		return getWebElement(d, "transactionCustomDateFilterEndDate", pageName, frameName);
	}

	public WebElement transactionCustomDateFilterErrorIcon() {
		return getWebElement(d, "transactionCustomDateFilterErrorIcon", pageName, frameName);
	}

	public WebElement transactionCustomDateFilterErrorMsg1() {
		return getWebElement(d, "transactionCustomDateFilterErrorMsg1", pageName, frameName);
	}

	public WebElement transactionCustomDateFilterErrorMsg2() {
		return getWebElement(d, "transactionCustomDateFilterErrorMsg2", pageName, frameName);
	}

	public WebElement transactionCustomDateFilterClose() {
		return getWebElement(d, "transactionCustomDateFilterClose", pageName, frameName);
	}

	public WebElement transactionFilterByText() {
		return getWebElement(d, "transactionFilterByText", pageName, frameName);
	}

	public List<WebElement> transactionFilterByTextList() {
		return getWebElements(d, "transactionFilterByText", pageName, frameName);
	}

	public WebElement transactionTypeAll() {
		return getWebElement(d, "transactionTypeAll", pageName, frameName);
	}

	public List<WebElement> transactionTypeAllList() {
		return getWebElements(d, "transactionTypeAll", pageName, frameName);
	}

	public WebElement transactionTypeDeposit() {
		return getWebElement(d, "transactionTypeDeposit", pageName, frameName);
	}

	public List<WebElement> transactionTypeDepositList() {
		return getWebElements(d, "transactionTypeDeposit", pageName, frameName);
	}

	public WebElement transactionTypeWithDrawal() {
		return getWebElement(d, "transactionTypeWithDrawal", pageName, frameName);
	}

	public WebElement transactionTypeAllActive() {
		return getWebElement(d, "transactionTypeAllActive", pageName, frameName);
	}

	public List<WebElement> transactionTypeWithDrawalList() {
		return getWebElements(d, "transactionTypeWithDrawal", pageName, frameName);
	}

	public List<WebElement> tranCatFltSearchedMLC1(String addHLC) {
		String replacedHLC = getVisibileElementXPath(d, "Transaction", null, "tranCatFltSearchedMLC1");
		replacedHLC = replacedHLC.replaceAll("addHLC", addHLC);
		System.out.println(replacedHLC);
		return d.findElements(By.xpath(replacedHLC));
	}

	public List<String> getListOfMLC(String HLC) {
		List<String> hlcNames = new ArrayList<>();
		List<WebElement> tranCatFltSearchedHLC = tranCatFltSearchedMLC1(HLC);
		for (WebElement element : tranCatFltSearchedHLC) {
			hlcNames.add(element.getText().trim());
		}
		return hlcNames;
	}

	public List<String> getListOfMLC() {
		List<String> hlcNames = new ArrayList<>();
		List<WebElement> tranCatFltSearchedHLC = tranCatFltSearchedMLC();
		for (WebElement element : tranCatFltSearchedHLC) {
			hlcNames.add(element.getText().trim());
		}
		return hlcNames;
	}

	public List<String> getListOfHighLitedMLC() {
		List<String> hlcNames = new ArrayList<>();
		List<WebElement> tranCatFltSearchedHLC = tranCatFltSearchedMLCHL();
		for (WebElement element : tranCatFltSearchedHLC) {
			hlcNames.add(element.getText().trim());
		}
		return hlcNames;
	}

	public List<String> getListOfHighLitedMLCAttibute() {
		List<String> hlcNames = new ArrayList<>();
		List<WebElement> tranCatFltSearchedHLC = tranCatFltSearchedMLCHL();
		for (WebElement element : tranCatFltSearchedHLC) {
			hlcNames.add(element.getAttribute("class").trim());
		}
		return hlcNames;
	}

	public List<String> getListOfHLC() {
		List<String> hlcNames = new ArrayList<>();
		List<WebElement> tranCatFltSearchedHLC = tranCatFltSearchedHLC();
		for (WebElement element : tranCatFltSearchedHLC) {
			hlcNames.add(element.getText().trim());
		}
		return hlcNames;
	}

	public List<String> getListOfHighLitedHLC() {
		List<String> hlcNames = new ArrayList<>();
		List<WebElement> tranCatFltSearchedHLC = tranCatFltSearchedHLCHL();
		for (WebElement element : tranCatFltSearchedHLC) {
			hlcNames.add(element.getText().trim());
		}
		return hlcNames;
	}

	public List<String> getListOfHighLitedHLCAttibute() {
		List<String> hlcNames = new ArrayList<>();
		List<WebElement> tranCatFltSearchedHLC = tranCatFltSearchedHLCHL();
		for (WebElement element : tranCatFltSearchedHLC) {
			hlcNames.add(element.getAttribute("class").trim());
		}
		return hlcNames;
	}

	public void clickAccountPopUpClose() {
		click(transactionFilterGroupPopUpClose());
		waitUntilSpinnerWheelIsInvisible();
	}

	public void selectCustomDate(String startDate, String endDate) {
		transactionCustomDateFilterStartDate().clear();
		transactionCustomDateFilterStartDate().sendKeys(startDate);
		transactionCustomDateFilterEndDate().clear();
		transactionCustomDateFilterEndDate().sendKeys(endDate);
		if (isTimeFilterCustomDonebtn()) {
			click(TimeFilterCustomDonebtn());
		} else {
			click(transactionCustomDateFilterUpdate());
		}
		waitForPageToLoad();
	}

	public boolean isTimeFilterCustomDonebtn() {
		return PageParser.isElementPresent("TimeFilterCustomDonebtn", "Expense", frameName);
	}

	public WebElement TimeFilterCustomDonebtn() {
		return getVisibileWebElement(d, "TimeFilterCustomDonebtn", "Expense", frameName);
	}

	public List<WebElement> addManualHeader_AMT_mob() {
		return getWebElements(d, "addManualHeader_AMT_mob", "Expense", frameName);
	}
}