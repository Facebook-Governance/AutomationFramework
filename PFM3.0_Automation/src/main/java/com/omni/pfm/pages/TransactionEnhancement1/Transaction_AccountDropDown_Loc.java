/**
* Copyright (c) 2018 Yodlee Inc. All Rights Reserved.
*
* This software is the confidential and proprietary information of
* Yodlee, Inc. Use is subject to license terms.
*
* @author Renuka K
*/
package com.omni.pfm.pages.TransactionEnhancement1;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.omni.pfm.PageProcessor.PageParser;
import com.omni.pfm.utility.CommonUtils;
import com.omni.pfm.utility.SeleniumUtil;

public class Transaction_AccountDropDown_Loc extends SeleniumUtil {
	static Logger logger = LoggerFactory.getLogger(Add_Manual_Transaction_Loc.class);
	public static WebDriver d = null;
	static WebElement we;
	static String pageName = "Transaction";
	WebDriverWait wait;

	public static final By showMoreButton = getByObject(pageName, null, "showmoretransaction");

	public Transaction_AccountDropDown_Loc(WebDriver d) {
		this.d = d;
		wait = new WebDriverWait(d, 100);
	}

	public List<WebElement> AdvisorName() {
		return getWebElements(d, "AdvisorName", "ManageSharing", null);
	}

	public List<WebElement> SharedAccounts() {
		return getWebElements(d, "SharedAccounts", "ManageSharing", null);
	}

	public List<WebElement> unSharedAccounts() {
		return getWebElements(d, "unSharedAccounts", "ManageSharing", null);
	}

	public List<WebElement> accShareDropdownList(int accountRow) {
		String abC = getVisibileElementXPath(d, "ManageSharing", null, "accShareDropdownList");
		abC = abC.replaceAll("accountRow", Integer.toString(accountRow));
		return d.findElements(By.xpath(abC));
	}

	public List<WebElement> unShareAccountDropdownList(int accountRow) {
		String abC = getVisibileElementXPath(d, "ManageSharing", null, "unShareAccountDropdownList");
		abC = abC.replaceAll("accountRow", Integer.toString(accountRow));
		return d.findElements(By.xpath(abC));
	}

	public List<WebElement> accShareDropdown() {
		return getWebElements(d, "accShareDropdown", "ManageSharing", null);
	}

	public List<WebElement> accShareDropdownValue(int accountRow) {
		// return getWebElements(d, "accShareDropdownValue",
		// "ManageSharing", null);
		String abC = getVisibileElementXPath(d, "ManageSharing", null, "accShareDropdownValue");
		abC = abC.replaceAll("accountRow", Integer.toString(accountRow));
		return d.findElements(By.xpath(abC));
	}

	public WebElement AccountShareSave() {
		return getWebElement(d, "AccountShareSave", "ManageSharing", null);
	}

	public WebElement accountFilterLink() {
		return getWebElement(d, "accountFilterLink", pageName, null);
	}

	public List<WebElement> allAccountLable() {
		return getWebElements(d, "allAccountLable", pageName, null);
	}

	public WebElement allAccountCheckBox() {
		return getWebElement(d, "allAccountCheckBox", pageName, null);
	}

	public WebElement AccountFilterAccountName(String accountNameORNum) {
		String xpath = getVisibileElementXPath(d, pageName, null, "AccountFilterAccountName");
		xpath = xpath.replaceAll("AccountNameNum", accountNameORNum);
		return d.findElement(By.xpath(xpath));
	}

	public List<WebElement> myOrAdvisorSharedAccountLable() {
		return getWebElements(d, "myOrAdvisorSharedAccountLable", pageName, null);
	}

	public List<WebElement> myOrAdvisorSharedAccountCheckBox() {
		return getWebElements(d, "myOrAdvisorSharedAccountCheckBox", pageName, null);
	}

	public List<WebElement> InstituteName() {
		return getWebElements(d, "InstituteName", pageName, null);
	}

	public List<WebElement> myAccountListCheckBox() {
		return getWebElements(d, "myAccountListCheckBox", pageName, null);
	}

	public List<WebElement> myAccountListAccountName() {
		return getWebElements(d, "myAccountListAccountName", pageName, null);
	}

	public List<WebElement> InvestorAdvisorAccountListCheckBox() {
		return getWebElements(d, "InvestorAdvisorAccountListCheckBox", pageName, null);
	}

	public List<WebElement> InvestorAdvisorAccountListAccountName() {
		return getWebElements(d, "InvestorAdvisorAccountListAccountName", pageName, null);
	}

	public List<WebElement> onlyInvestorAdvisorAccountListAccountName() {
		return getWebElements(d, "onlyInvestorAdvisorAccountListAccountName", pageName, null);
	}

	public List<WebElement> onlyInvestorAdvisorAccountListAccountNumber() {
		return getWebElements(d, "onlyInvestorAdvisorAccountListAccountNumber", pageName, null);
	}

	public List<WebElement> AccountGroupTab_AccountSharingTransaction() {
		return getWebElements(d, "AccountGroupTab_AccountSharingTransaction", pageName, null);
	}

	public WebElement Grouplink_AccountSharingTransaction() {
		return getWebElement(d, "Grouplink_AccountSharingTransaction", pageName, null);
	}

	public WebElement GroupNameTextBox_AccountSharingTransaction() {
		return getWebElement(d, "GroupNameTextBox_AccountSharingTransaction", pageName, null);
	}

	public List<WebElement> GroupAccount_AccountSharingTransaction() {
		return getWebElements(d, "GroupAccount_AccountSharingTransaction", pageName, null);
	}

	public WebElement GroupSumbit_AccountSharingTransaction() {
		return getWebElement(d, "GroupSumbit_AccountSharingTransaction", pageName, null);
	}

	public WebElement more() {
		return getWebElement(d, "more", pageName, null);
	}

	public WebElement GroupSumbit_AccountSharingTransaction1() {
		return getWebElement(d, "GroupSumbit_AccountSharingTransaction1", pageName, null);
	}

	public void creategroupWithAdvisorAggregatedAccount(String groupName) {
		click(AccountGroupTab_AccountSharingTransaction().get(2));
		click(Grouplink_AccountSharingTransaction());
		GroupNameTextBox_AccountSharingTransaction().sendKeys(groupName);
		click(GroupAccount_AccountSharingTransaction().get(1));
		click(GroupAccount_AccountSharingTransaction().get(2));
		click(GroupSumbit_AccountSharingTransaction());
	}

	public void creategroupWithinvestorSharedFullViewDetailsAccount(String groupName) {
		click(AccountGroupTab_AccountSharingTransaction().get(2));
		waitForPageToLoad();
		click(more());
		waitForPageToLoad(2000);
		click(GroupSumbit_AccountSharingTransaction1());
		waitForPageToLoad();
		GroupNameTextBox_AccountSharingTransaction().sendKeys(groupName);
		click(GroupAccount_AccountSharingTransaction().get(0));
		waitForPageToLoad(1000);
		click(GroupAccount_AccountSharingTransaction().get(3));
		waitForPageToLoad(1000);
		click(GroupSumbit_AccountSharingTransaction());
		waitForPageToLoad(3000);
	}

	public void creategroupWithinvestorSharedBalanceDetailsAccount(String groupName) {
		click(AccountGroupTab_AccountSharingTransaction().get(2));
		waitForPageToLoad();
		click(more());
		waitForPageToLoad(2000);
		click(GroupSumbit_AccountSharingTransaction1());
		waitForPageToLoad(3000);
		GroupNameTextBox_AccountSharingTransaction().sendKeys(groupName);
		click(GroupAccount_AccountSharingTransaction().get(6));
		waitForPageToLoad(1000);
		click(GroupSumbit_AccountSharingTransaction());
		waitForPageToLoad(1000);
	}

	public void creategroupWithinvestorSharedFullViewBalanceDetailsAccount(String groupName) {
		click(AccountGroupTab_AccountSharingTransaction().get(2));
		waitForPageToLoad();
		click(more());
		waitForPageToLoad(2000);
		click(GroupSumbit_AccountSharingTransaction1());
		waitForPageToLoad(1000);
		GroupNameTextBox_AccountSharingTransaction().sendKeys(groupName);
		waitForPageToLoad(1000);
		click(GroupAccount_AccountSharingTransaction().get(6));
		waitForPageToLoad(1000);
		click(GroupAccount_AccountSharingTransaction().get(3));
		waitForPageToLoad(1000);
		click(GroupSumbit_AccountSharingTransaction());
		waitForPageToLoad(1000);
	}

	public void creategroupWithInvestorAggregatedAccount(String groupName) {
		click(AccountGroupTab_AccountSharingTransaction().get(2));
		waitForPageToLoad(3000);
		click(Grouplink_AccountSharingTransaction());
		waitForPageToLoad(3000);
		GroupNameTextBox_AccountSharingTransaction().sendKeys(groupName);
		click(GroupAccount_AccountSharingTransaction().get(0));
		waitForPageToLoad(1000);
		click(GroupAccount_AccountSharingTransaction().get(3));
		waitForPageToLoad(1000);
		click(GroupSumbit_AccountSharingTransaction());
		waitForPageToLoad(3000);
	}

	public void creategroupWithAdvisorSharedAccount(String groupName) {
		click(AccountGroupTab_AccountSharingTransaction().get(2));
		waitForPageToLoad();
		click(more());
		waitForPageToLoad(3000);
		click(GroupSumbit_AccountSharingTransaction1());
		waitForPageToLoad(1000);
		GroupNameTextBox_AccountSharingTransaction().sendKeys(groupName);
		click(GroupAccount_AccountSharingTransaction().get(1));
		waitForPageToLoad(1000);
		click(GroupAccount_AccountSharingTransaction().get(2));
		waitForPageToLoad(1000);
		click(GroupSumbit_AccountSharingTransaction());
	}

	public void creategroupWithAdvisorSharedAccountAndInvestorAccount(String groupName) {
		click(AccountGroupTab_AccountSharingTransaction().get(2));
		waitForPageToLoad();
		click(more());
		waitForPageToLoad(3000);
		click(GroupSumbit_AccountSharingTransaction1());
		waitForPageToLoad(1000);
		GroupNameTextBox_AccountSharingTransaction().sendKeys(groupName);
		click(GroupAccount_AccountSharingTransaction().get(0));
		waitForPageToLoad(1000);
		click(GroupAccount_AccountSharingTransaction().get(1));
		waitForPageToLoad(1000);
		click(GroupSumbit_AccountSharingTransaction());
	}

	public List<WebElement> transactionAccountDropDownAccountGroupLink() {
		return getWebElements(d, "transactionAccountDropDownAccountGroupLink", pageName, null);
	}

	public List<WebElement> transactionAccountDropDownGroupLink() {
		return getWebElements(d, "transactionAccountDropDownGroupLink", pageName, null);
	}

	public WebElement transactionAccountDropDownGroupName(String groupName) {
		String group = getVisibileElementXPath(d, pageName, null, "transactionAccountDropDownGroupName");
		group = group.replaceAll("GroupName", groupName);
		return d.findElement(By.xpath(group));
	}

	public List<WebElement> allTransactionAccount() {
		return getWebElements(d, "allTransactionAccount", pageName, null);
	}

	public WebElement AccountSharingAddManual() {
		return getWebElement(d, "AccountSharingAddManual", pageName, null);
	}

	public List<WebElement> getAllPostedAccount_AMT1() {
		return getWebElements(d, "getAllPostedAccount_AMT1", pageName, null);
	}

	public WebElement TransactionNodataScreen() {
		return getWebElement(d, "TransactionNodataScreen", pageName, null);
	}

	public WebElement transactionNoDataMessage1() {
		return getWebElement(d, "transactionNoDataMessage1", pageName, null);
	}

	public WebElement TransactionTimeFilter() {
		return getWebElement(d, "TransactionTimeFilter", pageName, null);
	}

	public WebElement TransactionTimeFilterLabel() {
		return getWebElement(d, "TransactionTimeFilterLabel", pageName, null);
	}

	public WebElement TransactionTimeFilterDate() {
		return getWebElement(d, "TransactionTimeFilterDate", pageName, null);
	}

	public List<WebElement> TransactionPostedTransactionDateHeader() {
		return getWebElements(d, "TransactionPostedTransactionDateHeader", pageName, null);
	}

	public List<WebElement> datefilter() {
		return getWebElements(d, "datefilter", pageName, null);
	}

	public WebElement datefilter_Name(String datefilter) {
		String date = getVisibileElementXPath(d, pageName, null, "datefilter_Name");
		date = date.replaceAll("TimeFilterOption", datefilter);
		return d.findElement(By.xpath(date));
	}

	public WebElement categoryFilterLink() {
		return getWebElement(d, "categoryFilterLink", pageName, null);
	}

	/* added for mobile */
	public boolean isFilterMobile() {
		return PageParser.isElementPresent("MobileFilterIcon", pageName, null);
	}

	public WebElement clickFilter() {
		return getWebElement(d, "MobileFilterIcon", pageName, null);
	}

	/* ends */
	public WebElement allCategoryCheckBox() {
		return getWebElement(d, "allCategoryCheckBox", pageName, null);
	}

	public List<WebElement> HLCCategoryCheckBox() {
		return getWebElements(d, "HLCCategoryCheckBox", pageName, null);
	}

	public List<WebElement> HLCCategoryLabel() {
		return getWebElements(d, "HLCCategoryLabel", pageName, null);
	}

	public List<WebElement> allTransactionCategory() {
		return getWebElements(d, "allTransactionCategory", pageName, null);
	}

	public List<WebElement> MLCCategoryCheckBox() {
		return getWebElements(d, "MLCCategoryCheckBox", pageName, null);
	}

	public List<WebElement> MLCCategoryLabel() {
		return getWebElements(d, "MLCCategoryLabel", pageName, null);
	}

	public List<WebElement> TransactionTypeFilter() {
		return getWebElements(d, "TransactionTypeFilter", pageName, null);
	}

	public List<WebElement> allTransactionDescription() {
		return getWebElements(d, "allTransactionDescription", pageName, null);
	}

	public List<WebElement> manage() {
		return getWebElements(d, "manage", pageName, null);
	}

	/* added for mobile */
	public boolean isfilterPopupBackMobile() {
		return PageParser.isElementPresent("filterPopupBackMobile", pageName, null);
	}

	public WebElement filterPopupBackMobile() {
		return getWebElement(d, "filterPopupBackMobile", pageName, null);
	}

	public WebElement filterBackMobile() {
		return getWebElement(d, "filterBackMobile", pageName, null);
	}

	public void showmoretransaction() {
		try {
			if (getWebElement(d, "showmoretransaction", pageName, null).isDisplayed()) {
				click(getWebElement(d, "showmoretransaction", pageName, null));
			}
			showmoretransaction();
		} catch (Exception e) {
		}
	}

	public WebElement TransactionTagLink() {
		return getWebElement(d, "TransactionTagLink", pageName, null);
	}

	public WebElement TransactionTagField() {
		return getWebElement(d, "TransactionTagField", pageName, null);
	}

	public WebElement TransactionTagCreate() {
		return getWebElement(d, "TransactionTagCreate", pageName, null);
	}

	public List<WebElement> TransactionCreatedTagList() {
		return getWebElements(d, "TransactionCreatedTagList", pageName, null);
	}

	public List<WebElement> TransactionTagList() {
		return getWebElements(d, "TransactionTagList", pageName, null);
	}

	public List<WebElement> TransactionTagList1() {
		return getWebElements(d, "TransactionTagList1", pageName, null);
	}

	public List<WebElement> allTransactionAmount() {
		return getWebElements(d, "allTransactionAmount", pageName, null);
	}

	public void createTag(String tagName) {
		click(TransactionTagLink());
		waitForPageToLoad(1000);
		TransactionTagField().sendKeys(tagName);
		click(TransactionTagCreate());
		waitForPageToLoad(1000);
	}

	public void createTag1(String tagName) {
		waitForPageToLoad(1000);
		TransactionTagField().sendKeys(tagName);
		click(TransactionTagCreate());
		waitForPageToLoad(1000);
	}

	public String monthStartDate(int month, String dateformate) {
		SimpleDateFormat format = new SimpleDateFormat(dateformate);
		Calendar cal4 = Calendar.getInstance();
		cal4.add(Calendar.MONTH, month);
		cal4.set(Calendar.DATE, 1);
		System.out.println(format.format(cal4.getTime()));
		return format.format(cal4.getTime());
	}

	public String monthEndDateDate(int endDateMonth, String dateformate) {
		String[] monthName = { "January", "February", "March", "April", "May", "June", "July", "August", "September",
				"October", "November", "December" };
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.MONTH, endDateMonth);
		String month = monthName[cal.get(Calendar.MONTH)];
		// System.out.println("Month name: " + month);
		int i = 0;
		switch (month) {
		case "January":
			i = 31;
			break;
		case "February":
			i = 28;
			break;
		case "March":
			i = 31;
			break;
		case "April":
			i = 30;
			break;
		case "May":
			i = 31;
			break;
		case "June":
			i = 30;
			break;
		case "July":
			i = 31;
			break;
		case "August":
			i = 31;
			break;
		case "September":
			i = 30;
			break;
		case "October":
			i = 31;
			break;
		case "November":
			i = 30;
			break;
		case "December":
			i = 31;
			break;
		}
		SimpleDateFormat format1 = new SimpleDateFormat(dateformate);
		Calendar cal1 = Calendar.getInstance();
		cal1.add(Calendar.MONTH, endDateMonth);
		cal1.set(Calendar.DATE, i);
		System.out.println(format1.format(cal1.getTime()));
		return format1.format(cal1.getTime());
	}

	public String yearStartDate(int year, String dateformate) {
		SimpleDateFormat format2 = new SimpleDateFormat(dateformate);
		Calendar mincld = Calendar.getInstance();
		mincld.set(Calendar.DAY_OF_YEAR, 1); // first day of the year.
		mincld.add(Calendar.YEAR, year);
		System.out.println(format2.format(mincld.getTime()));
		return format2.format(mincld.getTime());
	}

	public String yearEndDate(int year, String dateformate) {
		SimpleDateFormat format2 = new SimpleDateFormat(dateformate);
		Calendar prevYear = Calendar.getInstance();
		prevYear.add(Calendar.YEAR, -1);
		int previousYear = prevYear.get(Calendar.YEAR);
		prevYear.set(previousYear, 11, 31, 0, 0, 0);
		logger.info(prevYear.getTime()+"");
		return format2.format(prevYear.getTime());
	}

	public boolean last3monthTransaction(int startDate, int EndDate) throws ParseException {
		click(getWebElement(d, "showmoretransaction", pageName, null));
		waitForPageToLoad();
		click(getWebElement(d, "showmoretransaction", pageName, null));
		waitForPageToLoad();
		ArrayList<Date> l2 = new ArrayList<Date>();
		for (int i = 0; i < TransactionPostedTransactionDateHeader().size(); i++) {
			Date d1 = new SimpleDateFormat("MMMM dd, yyyy")
					.parse(TransactionPostedTransactionDateHeader().get(i).getText().replaceAll("Today,", "").trim());
			l2.add(d1);
		}
		logger.info("get the last 3 month start date");
		SimpleDateFormat formate2 = new SimpleDateFormat("MMMM dd, yyyy");
		Calendar cal2 = Calendar.getInstance();
		cal2.setTime(formate2.parse(monthStartDate(startDate, "MMMM dd, yyyy")));
		logger.info("get current date");
		Calendar cal3 = Calendar.getInstance();
		cal3.add(Calendar.DATE, EndDate);
		cal3.setTime(cal3.getTime());
		boolean postedtransactionDate = true;
		for (int i = 0; i < l2.size(); i++) {
			Calendar cal1 = Calendar.getInstance();
			cal1.setTime(l2.get(i));
			System.out.println(formate2.format(cal1.getTime()));
			System.out.println(formate2.format(cal2.getTime()));
			System.out.println(formate2.format(cal3.getTime()));
			if ((cal1.after(cal2) || cal1.equals(cal2)) && (cal1.before(cal3) || cal1.equals(cal3))) {
				postedtransactionDate = true;
			} else {
				postedtransactionDate = false;
				break;
			}
		}
		return postedtransactionDate;
	}

	public boolean thismonthTransaction(int startDate, int EndDate) throws ParseException {
		ArrayList<Date> l2 = new ArrayList<Date>();
		for (int i = 0; i < TransactionPostedTransactionDateHeader().size(); i++) {
			Date d1 = new SimpleDateFormat("MMMM dd, yyyy")
					.parse(TransactionPostedTransactionDateHeader().get(i).getText().replaceAll("Today,", "").trim());
			l2.add(d1);
		}
		logger.info("get the last  month start date");
		SimpleDateFormat formate2 = new SimpleDateFormat("MMMM dd, yyyy");
		Calendar cal2 = Calendar.getInstance();
		cal2.setTime(formate2.parse(monthStartDate(startDate, "MMMM dd, yyyy")));
		logger.info("get current date");
		Calendar cal3 = Calendar.getInstance();
		cal3.add(Calendar.DATE, EndDate);
		cal3.setTime(cal3.getTime());
		boolean postedtransactionDate = true;
		for (int i = 0; i < l2.size(); i++) {
			Calendar cal1 = Calendar.getInstance();
			cal1.setTime(l2.get(i));
			if ((cal1.after(cal2) || cal1.equals(cal2)) && (cal1.before(cal3) || cal1.equals(cal3))) {
				postedtransactionDate = true;
			} else {
				postedtransactionDate = false;
				break;
			}
		}
		return postedtransactionDate;
	}

	public boolean lastmonthTransaction(int startDate, int EndDate) throws ParseException {
		ArrayList<Date> l2 = new ArrayList<Date>();
		for (int i = 0; i < TransactionPostedTransactionDateHeader().size(); i++) {
			Date d1 = new SimpleDateFormat("MMMM dd, yyyy")
					.parse(TransactionPostedTransactionDateHeader().get(i).getText().replaceAll("Today,", ""));
			l2.add(d1);
		}
		logger.info("get the last  month start date");
		SimpleDateFormat formate2 = new SimpleDateFormat("MMMM dd, yyyy");
		Calendar cal2 = Calendar.getInstance();
		cal2.setTime(formate2.parse(monthStartDate(startDate, "MMMM dd, yyyy")));
		logger.info("last month end  date");
		Calendar cal3 = Calendar.getInstance();
		cal3.setTime(formate2.parse(monthEndDateDate(EndDate, "MMMM dd, yyyy")));
		boolean postedtransactionDate = true;
		for (int i = 0; i < l2.size(); i++) {
			Calendar cal1 = Calendar.getInstance();
			cal1.setTime(l2.get(i));
			if ((cal1.after(cal2) || cal1.equals(cal2)) && (cal1.before(cal3) || cal1.equals(cal3))) {
				postedtransactionDate = true;
			} else {
				postedtransactionDate = false;
				break;
			}
		}
		return postedtransactionDate;
	}

	public boolean last6monthTransaction(int startDate, int EndDate) throws ParseException {
		click(getWebElement(d, "showmoretransaction", pageName, null));
		waitForPageToLoad();
		click(getWebElement(d, "showmoretransaction", pageName, null));
		waitForPageToLoad();
		ArrayList<Date> l2 = new ArrayList<Date>();
		for (int i = 0; i < TransactionPostedTransactionDateHeader().size(); i++) {
			Date d1 = new SimpleDateFormat("MMMM dd, yyyy")
					.parse(TransactionPostedTransactionDateHeader().get(i).getText().replaceAll("Today,", "").trim());
			l2.add(d1);
		}
		logger.info("get the last  month start date");
		SimpleDateFormat formate2 = new SimpleDateFormat("MMMM dd, yyyy");
		Calendar cal2 = Calendar.getInstance();
		cal2.setTime(formate2.parse(monthStartDate(startDate, "MMMM dd, yyyy")));
		Calendar cal3 = Calendar.getInstance();
		cal3.add(Calendar.DATE, EndDate);
		cal3.setTime(cal3.getTime());
		boolean postedtransactionDate = true;
		for (int i = 0; i < l2.size(); i++) {
			Calendar cal1 = Calendar.getInstance();
			cal1.setTime(l2.get(i));
			if ((cal1.after(cal2) || cal1.equals(cal2)) && (cal1.before(cal3) || cal1.equals(cal3))) {
				postedtransactionDate = true;
			} else {
				postedtransactionDate = false;
				break;
			}
		}
		return postedtransactionDate;
	}

	public boolean thisYearTransaction(int yearstartDate, int EndDate) throws ParseException {
		click(getWebElement(d, "showmoretransaction", pageName, null));
		waitForPageToLoad();
		click(getWebElement(d, "showmoretransaction", pageName, null));
		waitForPageToLoad();
		ArrayList<Date> l2 = new ArrayList<Date>();
		for (int i = 0; i < TransactionPostedTransactionDateHeader().size(); i++) {
			Date d1 = new SimpleDateFormat("MMMM dd, yyyy")
					.parse(TransactionPostedTransactionDateHeader().get(i).getText().replaceAll("Today,", "").trim());
			l2.add(d1);
		}
		logger.info("get the last  month start date");
		SimpleDateFormat formate2 = new SimpleDateFormat("MMMM dd, yyyy");
		Calendar cal2 = Calendar.getInstance();
		cal2.setTime(formate2.parse(yearStartDate(yearstartDate, "MMMM dd, yyyy")));
		Calendar cal3 = Calendar.getInstance();
		cal3.add(Calendar.DATE, EndDate);
		cal3.setTime(cal3.getTime());
		boolean postedtransactionDate = true;
		for (int i = 0; i < l2.size(); i++) {
			Calendar cal1 = Calendar.getInstance();
			cal1.setTime(l2.get(i));
			if ((cal1.after(cal2) || cal1.equals(cal2)) && (cal1.before(cal3) || cal1.equals(cal3))) {
				postedtransactionDate = true;
			} else {
				postedtransactionDate = false;
				break;
			}
		}
		return postedtransactionDate;
	}

	public boolean last12MonthTransaction(int startDate, int EndDate) throws ParseException {
		click(getWebElement(d, "showmoretransaction", pageName, null));
		waitForPageToLoad();
		click(getWebElement(d, "showmoretransaction", pageName, null));
		waitForPageToLoad();
		ArrayList<Date> l2 = new ArrayList<Date>();
		for (int i = 0; i < TransactionPostedTransactionDateHeader().size(); i++) {
			Date d1 = new SimpleDateFormat("MMMM dd, yyyy")
					.parse(TransactionPostedTransactionDateHeader().get(i).getText().replaceAll("Today,", "").trim());
			l2.add(d1);
		}
		logger.info("get the last  month start date");
		SimpleDateFormat formate2 = new SimpleDateFormat("MMMM dd, yyyy");
		Calendar cal2 = Calendar.getInstance();
		cal2.setTime(formate2.parse(monthStartDate(startDate, "MMMM dd, yyyy")));
		Calendar cal3 = Calendar.getInstance();
		cal3.add(Calendar.DATE, EndDate);
		cal3.setTime(cal3.getTime());
		boolean postedtransactionDate = true;
		for (int i = 0; i < l2.size(); i++) {
			Calendar cal1 = Calendar.getInstance();
			cal1.setTime(l2.get(i));
			if ((cal1.after(cal2) || cal1.equals(cal2)) && (cal1.before(cal3) || cal1.equals(cal3))) {
				postedtransactionDate = true;
			} else {
				postedtransactionDate = false;
				break;
			}
		}
		return postedtransactionDate;
	}

	public boolean lastYesrMonthTransaction(int yearstartDate, int yearEndDate) throws ParseException {
		ArrayList<Date> l2 = new ArrayList<Date>();
		for (int i = 0; i < TransactionPostedTransactionDateHeader().size(); i++) {
			Date d1 = new SimpleDateFormat("MMMM dd, yyyy")
					.parse(TransactionPostedTransactionDateHeader().get(i).getText().replaceAll("Today,", "").trim());
			l2.add(d1);
		}
		logger.info("get the last  month start date");
		SimpleDateFormat formate2 = new SimpleDateFormat("MMMM dd, yyyy");
		Calendar cal2 = Calendar.getInstance();
		cal2.setTime(formate2.parse(yearStartDate(yearstartDate, "MMMM dd, yyyy")));
		logger.info("last month end  date");
		Calendar cal3 = Calendar.getInstance();
		cal3.setTime(formate2.parse(yearEndDate(yearEndDate, "MMMM dd, yyyy")));
		boolean postedtransactionDate = true;
		for (int i = 0; i < l2.size(); i++) {
			Calendar cal1 = Calendar.getInstance();
			cal1.setTime(l2.get(i));
			if ((cal1.after(cal2) || cal1.equals(cal2)) && (cal1.before(cal3) || cal1.equals(cal3))) {
				postedtransactionDate = true;
			} else {
				postedtransactionDate = false;
				break;
			}
		}
		return postedtransactionDate;
	}

	public boolean customeDateTransaction(String tartDate, String EndDate) throws ParseException {
		click(getWebElement(d, "showmoretransaction", pageName, null));
		waitForPageToLoad();
		click(getWebElement(d, "showmoretransaction", pageName, null));
		waitForPageToLoad();
		ArrayList<Date> l2 = new ArrayList<Date>();
		for (int i = 0; i < TransactionPostedTransactionDateHeader().size(); i++) {
			Date d1 = new SimpleDateFormat("MMMM dd, yyyy")
					.parse(TransactionPostedTransactionDateHeader().get(i).getText().replaceAll("Today,", "").trim());
			l2.add(d1);
		}
		logger.info("get the last  month start date");
		SimpleDateFormat formate2 = new SimpleDateFormat("MMMM dd, yyyy");
		Calendar cal2 = Calendar.getInstance();
		System.out.println(formate2.parse(formate2.format(cal2.getTime())));
		cal2.setTime(formate2.parse(formate2.format(cal2.getTime())));
		logger.info("last month end  date");
		Calendar cal3 = Calendar.getInstance();
		System.out.println(formate2.parse(formate2.format(cal3.getTime())));
		cal3.setTime(formate2.parse(formate2.format(cal3.getTime())));
		boolean postedtransactionDate = true;
		for (int i = 0; i < l2.size(); i++) {
			Calendar cal1 = Calendar.getInstance();
			cal1.setTime(l2.get(i));
			if ((cal1.after(cal2) || cal1.equals(cal2)) && (cal1.before(cal3) || cal1.equals(cal3))) {
				postedtransactionDate = true;
			} else {
				postedtransactionDate = false;
				break;
			}
		}
		return postedtransactionDate;
	}

	public List<WebElement> myAccountListAccountCheckBox() {
		return getWebElements(d, "myAccountListAccountCheckBox", pageName, null);
	}

	public void creategroupToverifyTransaction(String groupName) {
		click(AccountGroupTab_AccountSharingTransaction().get(2));
		click(Grouplink_AccountSharingTransaction());
		GroupNameTextBox_AccountSharingTransaction().sendKeys(groupName);
		click(GroupAccount_AccountSharingTransaction().get(0));
		click(GroupAccount_AccountSharingTransaction().get(1));
		click(GroupSumbit_AccountSharingTransaction());
	}

	public void creategroupToverifyTransaction1(String groupName) {
		click(AccountGroupTab_AccountSharingTransaction().get(2));
		waitForPageToLoad(1000);
		click(more());
		waitForPageToLoad(1000);
		click(GroupSumbit_AccountSharingTransaction1());
		waitForPageToLoad(3000);
		GroupNameTextBox_AccountSharingTransaction().sendKeys(groupName);
		click(GroupAccount_AccountSharingTransaction().get(6));
		waitForPageToLoad(1000);
		click(GroupAccount_AccountSharingTransaction().get(7));
		waitForPageToLoad(1000);
		click(GroupSumbit_AccountSharingTransaction());
		waitForPageToLoad(3000);
	}

	public void createExpenseGroup(String groupName) {
		click(AccountGroupTab_AccountSharingTransaction().get(2));
		waitForPageToLoad(1000);
		click(Grouplink_AccountSharingTransaction());
		waitForPageToLoad(3000);
		GroupNameTextBox_AccountSharingTransaction().sendKeys(groupName);
		click(GroupAccount_AccountSharingTransaction().get(8));
		waitForPageToLoad(1000);
		// click(GroupAccount_AccountSharingTransaction().get(7));
		// waitForPageToLoad(1000);
		click(GroupSumbit_AccountSharingTransaction());
		waitForPageToLoad(3000);
	}

	public void clickCategoryFilter() {
		WebDriverWait wait = new WebDriverWait(d, 15);
		wait.until(ExpectedConditions.visibilityOf(categoryFilterLink()));
		click(categoryFilterLink());
		waitUntilSpinnerWheelIsInvisible();
	}

	public void clickAccountFilter() {
		wait.until(ExpectedConditions.visibilityOf(accountFilterLink()));
		click(accountFilterLink());
	}

	public void refreshFinapp() {
		for (int i = 0; i < 4; i++) {
			refresh(d);
			waitFor(2);
		}
	}

	public void clickAllAccount() {
		WebElement element = wait.until(ExpectedConditions.visibilityOf(allAccountCheckBox()));
		click(element);
	}

	public void clickAccountCheckbox(int acount) {
		WebElement element = wait.until(ExpectedConditions.visibilityOf(myAccountListAccountCheckBox().get(acount)));
		click(element);
	}

	public void selectAccFilterAccount(int account) {
		clickAccountFilter();
		clickAllAccount();
		clickAccountCheckbox(account);
	}

	public void clickTimeFilter() {
		WebElement element = wait.until(ExpectedConditions.visibilityOf(TransactionTimeFilter()));
		click(element);
	}

	public void selectTimeFilter(String datefilter) {
		WebElement element = wait.until(ExpectedConditions.visibilityOf(datefilter_Name(datefilter)));
		click(element);
	}

	public void clickGroup(String group) {
		click(transactionAccountDropDownGroupName(group));
	}

	public void selectAccFilterOneAccount(int account) {
		clickAccountCheckbox(account);
	}

	public void selectAccFilterOneAccount(String accountNameNum) {
		click(this.AccountFilterAccountName(accountNameNum));
	}

	public WebElement ProjectedExapdIcon() {
		return getWebElement(d, "ProExpandIcon", pageName, null);
	}

	public void expandProjectedTXn() {
		By projectedTransactionExpandIcon = getByObject(pageName, null, "ProExpandIcon");
		if (isDisplayed(projectedTransactionExpandIcon, 1)) {
			click(projectedTransactionExpandIcon);
		}
		SeleniumUtil.waitForPageToLoad();
	}

	public WebElement GroupsLink() {
		return getWebElement(d, "GroupsLink", pageName, null);
	}

	public void clickingOnGroupLink() {
		click(GroupsLink());
	}

	public boolean verifyAccountsGroupNameLabel(String groupName) {
		List<WebElement> l = getWebElements(d, "GroupsNameLabel", pageName, null);
		boolean status = CommonUtils.assertEqualsListElements(groupName, l);
		return status;
	}

	public boolean verifyManageCreateGroups(String propValue) {
		List<WebElement> l = getWebElements(d, "manageCreateGroup", "AccountGroups", null);
		boolean status = CommonUtils.assertEqualsListElements(propValue, l);
		return status;
	}

	// Added by Mallika for Transaction Group Stickyness starts here
	public WebElement AllAccType_tran() {
		return getWebElement(d, "AllAccType_tran", pageName, null);
	}

	public WebElement AllAccType_closeBtn() {
		return getWebElement(d, "AllAccType_closeBtn", pageName, null);
	}

	// Added by Mallika for Transaction Group Stickyness ends here
	public WebElement Trans_filterbtn_mob() {
		return getWebElement(d, "Trans_filterbtn_mob", pageName, null);
	}

	public boolean isMobile_Trans_filterbtn_Present() {
		return PageParser.isElementPresent("Trans_filterbtn_mob", pageName, null);
	}

	public boolean isMobile_Trans_ClickonGroup_Present() {
		return PageParser.isElementPresent("Trasn_clickonGroup_mob", pageName, null);
	}

	public WebElement Trasn_clickonGroup_mob() {
		return getWebElement(d, "Trasn_clickonGroup_mob", pageName, null);
	}

	public void backtoTxnDetailsMobile() {
		click(filterPopupBackMobile());
		waitForPageToLoad();
	}

	public WebElement Tagbackmobile() {
		return getWebElement(d, "Tagbackmobile", pageName, null);
	}

	public boolean isTagbackmobile() {
		return PageParser.isElementPresent("Tagbackmobile", pageName, null);
	}

	public WebElement clickTagFilter() {
		return getWebElement(d, "clickTagFilter", pageName, null);
	}

	public void clickOnShowMoreButton() {
		if (isDisplayed(showMoreButton, 1)) {
			click(showMoreButton);
			waitFor(1);
			waitUntilSpinnerWheelIsInvisible();
		}
		waitForPageToLoad();
	}
}