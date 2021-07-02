/**
* Copyright (c) 2018 Yodlee Inc. All Rights Reserved.
*
* This software is the confidential and proprietary information of
* Yodlee, Inc. Use is subject to license terms.
*
* @author renukak
*/

package com.omni.pfm.pages.Expense_IncomeAnalysis;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.omni.pfm.PageProcessor.PageParser;
import com.omni.pfm.utility.CommonUtils;
import com.omni.pfm.utility.SeleniumUtil;

public class Expense_Income_Trend_Loc extends SeleniumUtil {

	static Logger logger = LoggerFactory.getLogger(Expense_Income_Trend_Loc.class);
	public static WebDriver d = null;
	static WebElement we;
	String pageName = "Expense";
	String frameName = null;
	By accountGroup = getByObject(pageName, frameName, "EIAccountDropDown");

	public Expense_Income_Trend_Loc(WebDriver d) {
		Expense_Income_Trend_Loc.d = d;
	}

	public WebElement EIALinkAccount() {
		// link account element
		return getWebElement(d, "EIALinkAccount", pageName, frameName);
	}

	public boolean isEIALinkAccount() {
		// link account element
		return PageParser.isElementPresent("LinkAnAccBtn", pageName, frameName);
	}

	public WebElement EIALinkAccount1() {
		// link account element
		return getWebElement(d, "LinkAnAccBtn", pageName, frameName);
	}

	public WebElement EIANoDataLinkAccount() {
		// no data page link account element
		return getWebElement(d, "EIANoDataLinkAccount", pageName, frameName);
	}

	public WebElement EIAselectAccts() {
		return getWebElement(d, "EIAselectAccts", pageName, frameName);
	}

	public List<WebElement> EIAselectAcctsList() {
		return getWebElements(d, "EIAselectAcctsList", pageName, frameName);
	}

	public WebElement EIAccountDropDown() {
		// accounts drop down element
		return getWebElement(d, "EIAccountDropDown", pageName, frameName);
	}

	public WebElement EIAllAccountCheckBox() {
		// accounts check box element
		return getWebElement(d, "EIAllAccountCheckBox", pageName, frameName);
	}

	public WebElement EIAllAccountLabel() {
		// all accounts lable element
		return getWebElement(d, "EIAllAccountLabel", pageName, frameName);
	}

	public WebElement EIAllAccountName(String accountName) {
		// account name element
		String acc = getVisibileElementXPath(d, pageName, frameName, "EIAllAccountName");
		acc = acc.replaceAll("AccName", accountName);
		return d.findElement(By.xpath(acc));

	}

	public List<WebElement> EIAllAccountList() {
		// get list og accounts name element
		return getWebElements(d, "EIAllAccountList", pageName, frameName);
	}

	public List<WebElement> EIAllAccountCheckBoxList() {
		// account name check box
		return getWebElements(d, "EIAllAccountCheckBoxList", pageName, frameName);
	}

	public WebElement EIANoDataHeader() {
		// no data page header element
		return getWebElement(d, "EIANoDataHeader", pageName, frameName);
	}

	public WebElement EIANoDataDescription() {
		// no data page description element
		return getWebElement(d, "EIANoDataDescription", pageName, frameName);
	}

	public WebElement EIATimeFilterDropDown() {
		// time filter drop down element
		return getWebElement(d, "EIATimeFilterDropDown", pageName, frameName);
	}

	public WebElement EIATimeFilterName(String filter) {
		// time filter name
		String filtrename = getVisibileElementXPath(d, pageName, frameName, "EIATimeFilterName");
		filtrename = filtrename.replaceAll("DateFilterName", filter);
		return d.findElement(By.xpath(filtrename));
	}

	public WebElement EIATimeFilterDropDownLabel() {
		// time filter dropdown lable element
		return getWebElement(d, "EIATimeFilterDropDownLabel", pageName, frameName);
	}

	public WebElement EIATimeFilterDropDownDate() {
		// time filter dropdown date lable element
		return getWebElement(d, "EIATimeFilterDropDownDate", pageName, frameName);
	}

	public List<WebElement> EIATrendMonthList() {
		// legends table months element
		return getWebElements(d, "EIATrendMonthList", pageName, frameName);
	}

	public List<WebElement> EIATrendMonthAmount() {
		// legends table amount element
		return getWebElements(d, "EIATrendMonthAmount", pageName, frameName);
	}

	public List<WebElement> EIATrendMonthAmountPer() {
		// legend table amount percenatge element
		return getWebElements(d, "EIATrendMonthAmountPer", pageName, frameName);
	}

	public List<WebElement> EIATrendMonthAmountPer2() {
		// legend table amount percenatge element
		return getWebElements(d, "EIATrendMonthAmountPer2", pageName, frameName);
	}

	public List<WebElement> EIATrendMonthAmountPer3() {
		// legend table amount percenatge element
		return getWebElements(d, "EIATrendMonthAmountPer3", pageName, frameName);
	}

	public WebElement EIATrendDeclimMsg() {
		// trend desclime message element
		return getWebElement(d, "EIATrendDeclimMsg", pageName, frameName);
	}

	public WebElement EIATrendAvg() {
		// last 3 month avg amount element
		return getWebElement(d, "EIATrendAvg", pageName, frameName);
	}

	public List<WebElement> EIATrendAvgList() {
		// last 3 month avg amount element
		return getWebElements(d, "EIATrendAvg", pageName, frameName);
	}

	public List<WebElement> EIATrendChartXaxis() {
		// chart x- axix element
		return getWebElements(d, "EIATrendChartXaxis", pageName, frameName);
	}

	public WebElement EIATrendChartYaxis() {
		// chart =y axix element
		return getWebElement(d, "EIATrendChartYaxis", pageName, frameName);
	}

	public WebElement EIATrendTopTxnHeader() {
		// trend top 5 txn header element
		return getWebElement(d, "EIATrendTopTxnHeader", pageName, frameName);
	}

	public WebElement EIAMTLink() {// add manual txn link element
		return getWebElement(d, "EIAMTLink", pageName, frameName);
	}

	public WebElement EIAMoreLink() {
		// more link button element
		return getWebElement(d, "EIAMoreLink", pageName, frameName);
	}

	public WebElement EIAAccountLink() {// link account button element
		return getWebElement(d, "EIAAccountLink", pageName, frameName);
	}

	public WebElement EIAFeatureTure() {// Feture ture eleement
		return getWebElement(d, "EIAFeatureTure", pageName, frameName);
	}

	public WebElement EIAPrint() {
		// print element
		return getWebElement(d, "EIAPrint", pageName, frameName);
	}

	public WebElement EIAAlert() {
		// alert button element
		return getWebElement(d, "EIAAlert", pageName, frameName);
	}

	public List<WebElement> EIAAlertList() {
		// alert button list element
		return getWebElements(d, "EIAAlert", pageName, frameName);
	}

	public List<WebElement> EIAAlertPopUpHeader() {
		// alert popup header element
		return getWebElements(d, "EIAAlertPopUpHeader", pageName, frameName);
	}

	public List<WebElement> EIAAlertHeader() {
		// alert header element
		return getWebElements(d, "EIAAlertHeader", pageName, frameName);
	}

	public WebElement EIAAlertPopUpClose() {
		// alert close element
		return getWebElement(d, "EIAAlertPopUpClose", pageName, frameName);
	}

	public WebElement EIAAlertDescription1() {
		// alert description element
		return getWebElement(d, "EIAAlertDescription1", pageName, frameName);
	}

	public WebElement EIAAlertDescription2() {
		// alert description element
		return getWebElement(d, "EIAAlertDescription2", pageName, frameName);
	}

	public WebElement EIAAlertSummeryDropDown() {// alert summery drop down element
		By obj = getByObject(pageName, frameName, "EIAAlertSummeryDropDown");
		waitUntilElementIsVisible(obj, 60);
		return getWebElement(d, "EIAAlertSummeryDropDown", pageName, frameName);
	}

	public WebElement EIAAlertSummertValue(String summeryValue) {
		// alert summery value
		String summnery = getVisibileElementXPath(d, pageName, frameName, "EIAAlertSummertValue");
		summnery = summnery.replaceAll("alertSum", summeryValue);
		return d.findElement(By.xpath(summnery));
	}

	public WebElement EIAAlertSave() {
		// alert save element
		return getWebElement(d, "EIAAlertSave", pageName, frameName);
	}

	public WebElement EIAAlertAmountLink() {// alert amount link element
		return getWebElement(d, "EIAAlertAmountLink", pageName, frameName);
	}

	public WebElement EIAAlertAmountField() {// alert amount field element
		return getWebElement(d, "EIAAlertAmountField", pageName, frameName);
	}

	public List<WebElement> EIATrendTopTxnDate() {// trend to txn date header
		return getWebElements(d, "EIATrendTopTxnDate", pageName, frameName);
	}

	public By EIATrendTopTxnDateByObject() {// trend to txn amount element
		return getByObject(pageName, frameName, "EIATrendTopTxnAmount");
	}

	public List<WebElement> EIATrendTopTxnDescriprion() {// trend top txn description element
		return getWebElements(d, "EIATrendTopTxnDescriprion", pageName, frameName);
	}

	public By EIATrendTopTxnDescriprionByObject() {// trend to txn amount element
		return getByObject(pageName, frameName, "EIATrendTopTxnAmount");
	}

	public List<WebElement> EIATrendTopTxnAccount() {// trend top txn amount element
		return getWebElements(d, "EIATrendTopTxnAccount", pageName, frameName);
	}

	public By EIATrendTopTxnAccountByObject() {// trend to txn amount element
		return getByObject(pageName, frameName, "EIATrendTopTxnAmount");
	}

	public List<WebElement> EIATrendTopTxnCatgeory() {// trend top txn category element
		return getWebElements(d, "EIATrendTopTxnCatgeory", pageName, frameName);
	}

	public By EIATrendTopTxnCatgeoryByObject() {// trend to txn amount element
		return getByObject(pageName, frameName, "EIATrendTopTxnAmount");
	}

	public List<WebElement> EIATrendTopTxnAmount() {// trend to txn amount element
		return getWebElements(d, "EIATrendTopTxnAmount", pageName, frameName);
	}

	public By EIATrendTopTxnAmountByObject() {// trend to txn amount element
		return getByObject(pageName, frameName, "EIATrendTopTxnAmount");
	}

	public WebElement EIABackToEIA() {
		// back to expense analysis element
		return getWebElement(d, "EIABackToEIA", pageName, frameName);
	}

	public WebElement EIARefundPrint() {
		// refund page print element
		return getWebElement(d, "EIARefundPrint", pageName, frameName);
	}

	public WebElement EIAHeader() {// expense analysis header
		return getWebElement(d, "EIAHeader", pageName, frameName);
	}

	public WebElement EIAByCategory() {// expense by category element
		return getWebElement(d, "EIAByCategory", pageName, frameName);
	}

	public WebElement EIAHLCCatCharHeader() {// donut chart header
		return getWebElement(d, "EIAHLCCatCharHeader", pageName, frameName);
	}

	public WebElement EIAHLCCatCharValue() {// donut chart values
		return getWebElement(d, "EIAHLCCatCharValue", pageName, frameName);
	}

	public WebElement EIAHLCCatAllCategoryLable() {// all category included catgeory lable element
		return getWebElement(d, "EIAHLCCatAllCategoryLable", pageName, frameName);
	}

	public WebElement EIAHLCCatFilterCatlable() {// catgeory filter link element
		return getWebElement(d, "EIAHLCCatFilterCatlable", pageName, frameName);
	}

	public List<WebElement> EIAHLCCatIcon() {
		// category icon element
		return getWebElements(d, "EIAHLCCatIcon", pageName, frameName);
	}

	public List<WebElement> EIAHLCCatPerList() {
		// category percentage element
		return getWebElements(d, "EIAHLCCatPerList", pageName, frameName);
	}

	public List<WebElement> EIAHLCCatAmountList() {
		// category amount list element
		return getWebElements(d, "EIAHLCCatAmountList", pageName, frameName);
	}

	public WebElement EIAHLCCatRefundMesg() {
		// refund/adjustment message
		return getWebElement(d, "EIAHLCCatRefundMesg", pageName, frameName);
	}

	public WebElement EIAHLCCatRefundAmount() {
		// refund/adjustment amount
		return getWebElement(d, "EIAHLCCatRefundAmount", pageName, frameName);
	}

	public WebElement EIAHLCCatRefundViewDetails() {
		// refund/adjustment view details link element

		return getWebElement(d, "EIAHLCCatRefundViewDetails", pageName, frameName);
	}

	public List<WebElement> EIAHLCCatRefundMesgList() {
		// refund/adjustment message list
		return getWebElements(d, "EIAHLCCatRefundMesg", pageName, frameName);
	}

	public List<WebElement> EIAHLCCatRefundAmountList() {
		// refund/adjustment amount list element
		return getWebElements(d, "EIAHLCCatRefundAmount", pageName, frameName);
	}

	public List<WebElement> EIAHLCCatRefundViewDetailsList() {
		// refund/adjustment view details link lsit element
		return getWebElements(d, "EIAHLCCatRefundViewDetails", pageName, frameName);
	}

	public WebElement EIAHLCCatUnCatTxnHeader() {
		// uncategorized txn header element
		return getWebElement(d, "EIAHLCCatUnCatTxnHeader", pageName, frameName);
	}

	public List<WebElement> EIAHLCCatUnCatTxnHeaderList() {
		// uncategorized txn header list element
		return getWebElements(d, "EIAHLCCatUnCatTxnHeader", pageName, frameName);
	}

	public WebElement EIAHLCCatUnCatTxnDateHeader() {// uncategorized txn date header element
		return getWebElement(d, "EIAHLCCatUnCatTxnDateHeader", pageName, frameName);
	}

	public List<WebElement> EIAHLCCatUnCatTxnDateHeaderList() {
		// uncategorized txn date header list
		return getWebElements(d, "EIAHLCCatUnCatTxnDateHeader", pageName, frameName);
	}

	public List<WebElement> EIAHLCCatUnCatTxnDescription() {
		// uncategorized txn description list element
		return getWebElements(d, "EIAHLCCatUnCatTxnDescription", pageName, frameName);
	}

	public List<WebElement> EIAHLCCatUnCatTxnAccount() {// uncategorized txn account list element
		return getWebElements(d, "EIAHLCCatUnCatTxnAccount", pageName, frameName);
	}

	public List<WebElement> EIAHLCCatUnCatTxnCat() {
		// uncategorized txn category list element
		return getWebElements(d, "EIAHLCCatUnCatTxnCat", pageName, frameName);
	}

	public List<WebElement> EIAHLCCatUnCatTxnAmount() {
		// uncategorized txn amount list element
		return getWebElements(d, "EIAHLCCatUnCatTxnAmount", pageName, frameName);
	}

	public List<WebElement> EIAHLCCatCheckBox() {// category select check box
		return getWebElements(d, "EIAHLCCatCheckBox", pageName, frameName);
	}

	public WebElement EIAHLCCatCancel() {// category filter cancel button
		return getWebElement(d, "EIAHLCCatCancel", pageName, frameName);
	}

	public WebElement EIAHLCCatDone() {// category filter done button
		return getWebElement(d, "EIAHLCCatDone", pageName, frameName);
	}

	public WebElement EIAIncomeLink() {
		// income analysis link element
		return getWebElement(d, "EIAIncomeLink", pageName, frameName);
	}

	public WebElement EIAExpenseLink() {
		// expense analysis link element
		return getWebElement(d, "EIAExpenseLink", pageName, frameName);
	}

	public WebElement EIADropDown() {
		// EIA drop element
		return getWebElement(d, "EIADropDown", pageName, frameName);
	}

	public WebElement EIALinkAccountPopUP() {// link an account pop up header element
		return getWebElement(d, "EIALinkAccountPopUP", pageName, frameName);
	}

	public WebElement EIAHLCCatName(String HLCName) {
		// category name element
		String name = getVisibileElementXPath(d, pageName, frameName, "EIAHLCCatName");
		name = name.replaceAll("HLCName", HLCName);
		return d.findElement(By.xpath(name));
	}

	public List<WebElement> EIAHLCCatNameList() {// catgeory name list element
		return getWebElements(d, "EIAHLCCatNameList", pageName, frameName);
	}

	public WebElement EIAHLCFTHeader(String FTNo) {
		// future ture header element
		String FT = getVisibileElementXPath(d, pageName, frameName, "EIAHLCFTHeader");
		FT = FT.replaceAll("FT", FTNo);
		return d.findElement(By.xpath(FT));
	}

	public List<WebElement> EIAHLCFTHeaderList(String FTNo) {
		// future ture header list element
		String FT = getVisibileElementXPath(d, pageName, frameName, "EIAHLCFTHeader");
		FT = FT.replaceAll("FT", FTNo);
		return d.findElements(By.xpath(FT));
	}

	public WebElement EIAHLCFTMesg1(String FTNo) {
		// future ture message1 element
		String FT = getVisibileElementXPath(d, pageName, frameName, "EIAHLCFTMesg1");
		FT = FT.replaceAll("FT", FTNo);
		return d.findElement(By.xpath(FT));
	}

	public WebElement EIAHLCFTMesg2(String FTNo) {
		// future ture message2 element
		String FT = getVisibileElementXPath(d, pageName, frameName, "EIAHLCFTMesg2");
		FT = FT.replaceAll("FT", FTNo);
		return d.findElement(By.xpath(FT));
	}

	public WebElement EIAHLCFTButton1(String FTNo) {
		// future ture back button element
		String FT = getVisibileElementXPath(d, pageName, frameName, "EIAHLCFTButton1");
		FT = FT.replaceAll("FT", FTNo);
		return d.findElement(By.xpath(FT));
	}

	public WebElement EIAHLCFTButton2(String FTNo) {
		// future ture next button element
		String FT = getVisibileElementXPath(d, pageName, frameName, "EIAHLCFTButton2");
		FT = FT.replaceAll("FT", FTNo);
		return d.findElement(By.xpath(FT));
	}

	public WebElement EIAHLCFTCloseButton(String FTNo) {
		// future ture close button element
		String FT = getVisibileElementXPath(d, pageName, frameName, "EIAHLCFTCloseButton");
		FT = FT.replaceAll("FT", FTNo);
		return d.findElement(By.xpath(FT));
	}

	public List<WebElement> EIAMLCCatPerList() {
		// Mlc category percentage
		return getWebElements(d, "EIAMLCCatPerList", pageName, frameName);
	}

	public List<WebElement> EIAMLCCatAmountList() {// MLC catgeory amount list element
		return getWebElements(d, "EIAMLCCatAmountList", pageName, frameName);
	}

	public WebElement EIAMLCCatName(String MLCName) {
		// MLC Category name element
		String name = getVisibileElementXPath(d, pageName, frameName, "EIAMLCCatName");
		name = name.replaceAll("HLCName", MLCName);
		return d.findElement(By.xpath(name));
	}

	public List<WebElement> EIAMLCCatNameList() {
		// MLC catgeory list element
		return getWebElements(d, "EIAMLCCatNameList", pageName, frameName);
	}

	public WebElement EIAMLCTxnAmount(String Amount) {
		// MLC txn amount element
		String name = getVisibileElementXPath(d, pageName, frameName, "EIAMLCTxnAmount");
		name = name.replace("amt", Amount);
		return d.findElement(By.xpath(name));
	}

	public WebElement EIAMLCTxnDescriptionField() {
		// MLC txn Description list
		return getWebElement(d, "EIAMLCTxnDescriptionField", pageName, frameName);
	}

	public WebElement EIAMLCTxnCatDropDown() {
		// MLC txn catgeory dropdown
		return getWebElement(d, "EIAMLCTxnCatDropDown", pageName, frameName);
	}

	public WebElement EIAHeaderIcon() {
		// expense header icon element
		return getWebElement(d, "EIAHeaderIcon", pageName, frameName);
	}

	public WebElement EIAExpenseOption() {
		// expense option in dropdown
		return getWebElement(d, "EIAExpenseOption", pageName, frameName);
	}

	public WebElement EIAIncomeOption() {
		// Icome option in dropdown
		return getWebElement(d, "EIAIncomeOption", pageName, frameName);
	}

	public WebElement EIAMLCTxnSubCat(String subcat) {// MLC txn sub catgeory element
		String name = getVisibileElementXPath(d, pageName, frameName, "EIAMLCTxnSubCat");
		name = name.replaceAll("subCat", subcat);
		return d.findElement(By.xpath(name));
	}

	public List<WebElement> EIAMLCSubCatgList(String MLCRow) {
		// MLC txn subcategory list
		String name = getVisibileElementXPath(d, pageName, frameName, "EIAMLCSubCatgList");
		name = name.replaceAll("SubCatList", MLCRow);
		return d.findElements(By.xpath(name));
	}

	public List<WebElement> EIAMLCSubCatgList1() {
		// MLC txn subcategory list
		String name = getVisibileElementXPath(d, pageName, frameName, "EIAMLCSubCatgList1");
		return d.findElements(By.xpath(name));
	}

	public List<WebElement> EIAMLCSubCatgPerList(String MLCRow) {
		// MLC sub catgeory percentage
		String name = getVisibileElementXPath(d, pageName, frameName, "EIAMLCSubCatgPerList");
		name = name.replaceAll("SubCatList", MLCRow);
		return d.findElements(By.xpath(name));
	}

	public List<WebElement> EIAMLCSubCatgAmountList(String MLCRow) {
		// MLC subcategory Amount list
		String name = getVisibileElementXPath(d, pageName, frameName, "EIAMLCSubCatgAmountList");
		name = name.replaceAll("SubCatList", MLCRow);
		return d.findElements(By.xpath(name));
	}

	public List<WebElement> EIAMLCSubCatgAmountList1() {
		// MLC subcategory Amount list
		String name = getVisibileElementXPath(d, pageName, frameName, "EIAMLCSubCatgAmountList1");
		return d.findElements(By.xpath(name));
	}

	public WebElement EIAMLCTxnDropDownCatList(String MLC) {
		// MLC txn catgeory dropdown liste
		String name = getVisibileElementXPath(d, pageName, frameName, "EIAMLCTxnDropDownCatList");
		name = name.replaceAll("MLC", MLC);
		return d.findElement(By.xpath(name));
	}

	public WebElement EIAMLCDonutBack() {
		// Donut back to expense element
		return getWebElement(d, "EIAMLCDonutBack", pageName, frameName);
	}

	public WebElement EIAMLCTrendPopUpClose() {
		// trend pop close element
		return getWebElement(d, "EIAMLCTrendPopUpClose", pageName, frameName);
	}

	public WebElement EIAMLCTrendPopUpHeader() {
		// trend popup header element
		return getWebElement(d, "EIAMLCTrendPopUpHeader", pageName, frameName);
	}

	public List<WebElement> EIAMLCTrendPopUpHeaderList() {
		// trend popup headre list
		return getWebElements(d, "EIAMLCTrendPopUpHeader", pageName, frameName);
	}

	public WebElement EIAMLCTrendLink() {
		// trend Link in MLC page
		return getWebElement(d, "EIAMLCTrendLink", pageName, frameName);
	}

	public WebElement EIAFTUEHeader() {
		// FTUE header
		return getWebElement(d, "EIAFTUEHeader", pageName, frameName);
	}

	public WebElement EIAFTUEMessage1() {
		// FTUE message1
		return getWebElement(d, "EIAFTUEMessage1", pageName, frameName);
	}

	public WebElement EIAFTUEMessage2() {
		// FTUE message2
		return getWebElement(d, "EIAFTUEMessage2", pageName, frameName);
	}

	public WebElement EIAFTUEContinue() {
		// FTUE continue
		return getWebElement(d, "EIAFTUEContinue", pageName, frameName);
	}

	public WebElement EIAFTUELinkAccount() {
		// FTUE link account
		return getWebElement(d, "EIAFTUELinkAccount", pageName, frameName);
	}

	public WebElement EIAFTUEGoto() {// FTUE goto
		return getWebElement(d, "EIAFTUEGoto", pageName, frameName);
	}

	public WebElement EIAMLCTxnHeader() {
		// FTUE txn header
		return getWebElement(d, "EIAMLCTxnHeader", pageName, frameName);
	}

	public WebElement EIAGroupName(String group) {
		// group name element
		String name = getVisibileElementXPath(d, pageName, frameName, "EIAGroupName");
		name = name.replaceAll("expensegroup", group);
		return d.findElement(By.xpath(name));
	}

	public WebElement EIAGroupLink() {
		// group link in account dropdown
		return getWebElement(d, "EIAGroupLink", pageName, frameName);
	}

	public WebElement EIADropDownAccountLink() {
		// group link in account dropdown
		return getWebElement(d, "EIADropDownAccountLink", pageName, frameName);
	}

	public WebElement EIAMLCTxnSave() {
		// MLC txn save button element
		return getWebElement(d, "EIAMLCTxnSave", pageName, frameName);
	}

	public List<WebElement> EIAMLCTxnPopUpCatList() {
		// MLC txn popup category list
		return getWebElements(d, "EIAMLCTxnPopUpCatList", pageName, frameName);
	}

	public List<WebElement> EIAMLCTxnPopUpAccountList() {
		// MLC txn popup category list
		return getWebElements(d, "EIAMLCTxnPopUpAccountList", pageName, frameName);
	}

	public List<WebElement> EIAMLCHeaderTxnCatList() {
		// MLC txn header catgeory list
		return getWebElements(d, "EIAMLCHeaderTxnCatList", pageName, frameName);
	}

	public List<WebElement> EIAMLCHeaderTxnAccountList() {
		// MLC txn header catgeory list
		return getWebElements(d, "EIAMLCHeaderTxnAccountList", pageName, frameName);
	}

	public WebElement EIAMLCTxnPopUpHeader() {
//	MLC txn popup header
		return getWebElement(d, "EIAMLCTxnPopUpHeader", pageName, frameName);
	}

	public List<WebElement> EIARefundTXnDescription() {
		// refund txndescription
		return getWebElements(d, "EIARefundTXnDescription", pageName, frameName);
	}

	public List<WebElement> EIARefundTXnAccount() {
		// refund txndescription
		return getWebElements(d, "EIARefundTXnAccount", pageName, frameName);
	}

	public List<WebElement> EIARefundTXnCat() {
		// refund txn category list
		return getWebElements(d, "EIARefundTXnCat", pageName, frameName);
	}

	public WebElement EIARefundTXnShowMore() {
		// refund txn show more option
		return getWebElement(d, "EIARefundTXnShowMore", pageName, frameName);
	}

	public WebElement EIARefundTXnDelete() {
		// refund txn delete element
		return getWebElement(d, "EIARefundTXnDelete", pageName, frameName);
	}

	public WebElement EIARefundTXnDeletePopUP() {
		// refund delete popup button element
		return getWebElement(d, "EIARefundTXnDeletePopUP", pageName, frameName);
	}

	public List<String> getMonthsValues(int months) {
		// get the select time filter month value
		List<String> mth = new ArrayList<String>();
		int j = 0;
		for (int i = 0; i < months; i++) {
			if (getMonthMMMM(j).equals("December") || getMonthMMMM(j).equals("January")) {
				mth.add(getMonthMMMM(j) + " " + '\'' + getYearYY(j));
				j = j - 1;
			} else {
				mth.add(getMonthMMMM(j));
				j = j - 1;
			}
		}
		return mth;
	}

	public String getMonthMMMM(int month) {
		// get months MMMM formate
		Calendar c = Calendar.getInstance();
		c.add(Calendar.MONTH, month);
		System.out.println(new SimpleDateFormat("MMMM").format(c.getTime()));
		return new SimpleDateFormat("MMMM").format(c.getTime());
	}

	public void clickEIAccountDropDown() {
		// clikc account drop down
		waitForPageToLoad();
		click(EIAccountDropDown());
		waitForPageToLoad(3000);

	}

	public void clickEIAllAccountCheckBox() {
		// click accounts check box
		click(EIAllAccountCheckBox());
		waitForPageToLoad();
	}

	public List<Double> monthEvgAmount(List<String> monthamt, String avgAmunt, int numMonth) {
		// get the selected time filters avg amount
		List<Double> perAmount = new ArrayList<Double>();

		String avg = StringUtils.substringAfter(avgAmunt, ":");
		avg = avg.replaceAll("[$\\,]", "");
		double avgamt = Double.parseDouble(avg);
		for (int i = 1; i < numMonth; i++) {
			double amut = Double.parseDouble(monthamt.get(i).replaceAll("[$\\,]", ""));
			perAmount.add(avgamt - amut);
		}
		return perAmount;
	}

	public List<String> totalAmountPer(List<String> monthamt, String avgAmunt, int numMonth) {
		// get the selected time filter amount and percentage of the amount
		List<String> totalValue = new ArrayList<String>();
		NumberFormat formatter = new DecimalFormat("#0.00");
		NumberFormat formatter1 = new DecimalFormat("#,###.00");

		List<Double> perAmount = monthEvgAmount(monthamt, avgAmunt, numMonth);
		String avg = StringUtils.substringAfter(avgAmunt, ":");
		avg = avg.replaceAll("[$\\,]", "");
		double avgamt = Double.parseDouble(avg);
		for (int i = 0; i < numMonth - 1; i++) {
			double diffvalue = perAmount.get(i);
			double totalper = Double.parseDouble(formatter.format(diffvalue * 100));
			totalper = avgamt == 0 ? 0.0 : Double.parseDouble(formatter.format(totalper / avgamt));
			totalValue.add("$" + formatter1.format(diffvalue).replaceAll("-", "") + " "
					+ Integer.toString((int) Math.round(totalper)).replaceAll("-", "") + "%");
		}
		return totalValue;
	}

	public List<String> totalAmountPerIncome(List<String> monthamt, String avgAmunt, int numMonth) {
		// get the selected time filter amount and percentage of the amount in income
		// analsis
		List<String> totalValue = new ArrayList<String>();
		NumberFormat formatter = new DecimalFormat("#0.00");
		NumberFormat formatter1 = new DecimalFormat("#,###.00");

		List<Double> perAmount = monthEvgAmount(monthamt, avgAmunt, numMonth);
		String avg = StringUtils.substringAfter(avgAmunt, ":");
		avg = avg.replaceAll("[$\\,]", "");
		double avgamt = Double.parseDouble(avg);
		for (int i = 0; i < numMonth - 1; i++) {
			double diffvalue = perAmount.get(i);
			double totalper = Double.parseDouble(formatter.format(diffvalue * 100));
			totalper = Double.parseDouble(formatter.format(totalper / avgamt));
			if (totalper < 1.00) {
				totalValue.add("$" + formatter1.format(diffvalue).replaceAll("-", "") + " " + "< 1%");
			} else {
				totalValue.add("$" + formatter1.format(diffvalue).replaceAll("-", "") + " "
						+ Integer.toString((int) Math.round(totalper)).replaceAll("-", "") + "%");

			}
		}
		return totalValue;
	}

	public List<String> totalAmountPerIncome1(List<String> monthamt, String avgAmunt, int numMonth) {
		// get the selected time filter amount and percentage of the amount in income
		// analsis
		List<String> totalValue = new ArrayList<String>();
		NumberFormat formatter = new DecimalFormat("#0.00");
		NumberFormat formatter1 = new DecimalFormat("#,###.00");

		List<Double> perAmount = monthEvgAmount(monthamt, avgAmunt, numMonth);
		String avg = StringUtils.substringAfter(avgAmunt, ":");
		avg = avg.replaceAll("[$\\,]", "");
		double avgamt = Double.parseDouble(avg);
		for (int i = 0; i < numMonth - 1; i++) {
			double diffvalue = perAmount.get(i);
			double totalper = Math.abs(Double.parseDouble(formatter.format(diffvalue * 100)));
			totalper = avgamt == 0 ? 0.0 : Double.parseDouble(formatter.format(totalper / avgamt));
			if (totalper < 1.00 && totalper != 0.00) {
				totalValue.add("$" + formatter.format(diffvalue).replaceAll("-", "") + " " + "< 1%");
			} else if (totalper == 0.00) {
				totalValue.add("$0.00 < 1%");
			}

			else {
				totalValue.add("$" + formatter1.format(diffvalue).replaceAll("-", "") + " "
						+ Integer.toString((int) Math.round(totalper)).replaceAll("-", "") + "%");

			}
		}
		return totalValue;
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

	public String getYear(int year) {
		// get the specified year in YY format
		Calendar c = Calendar.getInstance();
		c.add(Calendar.YEAR, year);
		System.out.println(new SimpleDateFormat("yy").format(c.getTime()));
		return new SimpleDateFormat("yy").format(c.getTime());
	}

	public String getAvgAmountMessage(int startmonth, int endMonth, String startMessage, String endMessage) {
		// get last 3 month avg amount and message
		String message;
		if (getYearYY(startmonth).equals(getYearYY(endMonth))) {
			message = startMessage + getMonthMMM(endMonth).toUpperCase() + " " + "-" + " "
					+ getMonthMMM(startmonth).toUpperCase() + " " + '\'' + getYearYY(startmonth) + endMessage;
		} else {
			message = startMessage + getMonthMMM(endMonth).toUpperCase() + " " + '\'' + getYearYY(endMonth) + " " + "-"
					+ " " + getMonthMMM(startmonth).toUpperCase() + " " + '\'' + getYearYY(startmonth) + endMessage;
		}

		return message;
	}

	public List<String> xAixValue(int month) {
		// get the selected time filter x axix avalue
		List<String> monthList = new ArrayList<String>();
		int j = 0;
		for (int i = 0; i < month; i++) {
			if (getMonthMMM(j).equals("Dec") || getMonthMMM(j).equals("Jan")) {
				monthList.add(getMonthMMM(j).toUpperCase() + " " + '\'' + getYearYY(j).toUpperCase());

				j = j - 1;
			} else {
				monthList.add(getMonthMMM(j).toUpperCase());

				j = j - 1;
			}

		}
		return monthList;
	}
	
	public List<String> xAixValueWithoutSpace(int month) {
		// get the selected time filter x axix avalue
		List<String> monthList = new ArrayList<String>();
		int j = 0;
		for (int i = 0; i < month; i++) {
			if (getMonthMMM(j).equals("Dec") || getMonthMMM(j).equals("Jan")) {
				monthList.add(getMonthMMM(j).toUpperCase() + '\'' + getYearYY(j).toUpperCase());

				j = j - 1;
			} else {
				monthList.add(getMonthMMM(j).toUpperCase());

				j = j - 1;
			}

		}
		return monthList;
	}

	public void selectAccount(String accName) {
		// select account from accounts filter
		click(EIAllAccountName(accName));

	}

	public void clickSummeryDropDown() {
		// select account summery from alert popup
		click(EIAAlertSummeryDropDown());
	}

	public void selectSummery(String summery) {
		// update the alert summery
		click(EIAAlertSummertValue(summery));
		click(EIAAlertSave());
	}

	public void updataAlertAmount(String amount) {
		// update alert amount
		click(EIAMoreLink());
		click(EIAAlert());
		click(EIAAlertAmountLink());
		EIAAlertAmountField().sendKeys(amount);
		click(EIAAlertSave());
	}

	public void clickAlert() {
		// click on alert link
		click(EIAMoreLink());
		waitForPageToLoad();
		click(EIAAlert());

	}

	public void clikcFT() {
		// click on Future ture link
		click(EIAMoreLink());
		click(EIAFeatureTure());
	}

	public void selectTimeFilter(String filter) {
		// selecte the time filter
		click(EIATimeFilterDropDown());
		waitForPageToLoad(2000);
		click(EIATimeFilterName(filter));
		waitUntilSpinnerWheelIsInvisible();
	}

	public List<String> yearMonths(int year, int noMonth) {
		// get the month for specified year
		SimpleDateFormat format2 = new SimpleDateFormat("MMMM");
		SimpleDateFormat format1 = new SimpleDateFormat("yy");

		List<String> months = new ArrayList<String>();
		for (int i = noMonth - 1; i >= 0; i--) {
			Calendar mincld = Calendar.getInstance();
			mincld.set(Calendar.MONTH, i);
			mincld.add(Calendar.YEAR, year);
			// String mth=format1.format(mincld.getTime());
			if (format2.format(mincld.getTime()).equals("December")
					|| format2.format(mincld.getTime()).equals("January")) {
				months.add(format2.format(mincld.getTime()) + " " + '\'' + format1.format(mincld.getTime()));
			} else {
				months.add(format2.format(mincld.getTime()));

			}
		}
		return months;
	}

	public List<Double> monthEvgAmountLastYear(List<String> monthamt, String avgAmunt, int numMonth) {
		// get the last year avg amount
		List<Double> perAmount = new ArrayList<Double>();

		String avg = StringUtils.substringAfter(avgAmunt, ":");
		avg = avg.replaceAll("[$\\,]", "");
		double avgamt = Double.parseDouble(avg);
		for (int i = 0; i < numMonth; i++) {
			double amut = Double.parseDouble(monthamt.get(i).replaceAll("[$\\,]", ""));
			perAmount.add(avgamt - amut);
		}
		return perAmount;
	}

	public List<String> totalAmountPerLastYear(List<String> monthamt, String avgAmunt, int numMonth) {// get the last
																										// year time
																										// filter amount
																										// and amount
																										// percentage
		List<String> totalValue = new ArrayList<String>();
		NumberFormat formatter = new DecimalFormat("#0.00");
		NumberFormat formatter1 = new DecimalFormat("#,###.00");

		List<Double> perAmount = monthEvgAmountLastYear(monthamt, avgAmunt, numMonth);
		String avg = StringUtils.substringAfter(avgAmunt, ":");
		avg = avg.replaceAll("[$\\,]", "");
		double avgamt = Double.parseDouble(avg);
		for (int i = 0; i < numMonth; i++) {
			double diffvalue = perAmount.get(i);
			double totalper = Double.parseDouble(formatter.format(diffvalue * 100));
			totalper = Double.parseDouble(formatter.format(totalper / avgamt));
			totalValue.add("$" + formatter1.format(diffvalue).replaceAll("-", "") + " "
					+ Integer.toString((int) Math.round(totalper)).replaceAll("-", "") + "%");
		}
		return totalValue;
	}

	public List<String> totalAmountPerLastYearIncome(List<String> monthamt, String avgAmunt, int numMonth) {
		// get the income analysis last year time filter amount and percentgae amount
		List<String> totalValue = new ArrayList<String>();
		NumberFormat formatter = new DecimalFormat("#0.00");
		NumberFormat formatter1 = new DecimalFormat("#,###.00");

		List<Double> perAmount = monthEvgAmountLastYear(monthamt, avgAmunt, numMonth);
		String avg = StringUtils.substringAfter(avgAmunt, ":");
		avg = avg.replaceAll("[$\\,]", "");
		double avgamt = Double.parseDouble(avg);
		for (int i = 0; i < numMonth; i++) {
			double diffvalue = perAmount.get(i);
			double totalper = Double.parseDouble(formatter.format(diffvalue * 100));
			totalper = Double.parseDouble(formatter.format(totalper / avgamt));
			if (totalper < 1.00) {
				totalValue.add("$" + formatter1.format(diffvalue).replaceAll("-", "") + " " + "< 1%");
			} else {
				totalValue.add("$" + formatter1.format(diffvalue).replaceAll("-", "") + " "
						+ Integer.toString((int) Math.round(totalper)).replaceAll("-", "") + "%");
			}
		}
		return totalValue;
	}

	public List<String> LastYearXaxisMonthChart(int year, int noMonth) {
		// last year time filter x axix value
		SimpleDateFormat format2 = new SimpleDateFormat("MMM");
		SimpleDateFormat format1 = new SimpleDateFormat("yy");

		List<String> months = new ArrayList<String>();
		for (int i = 0; i < noMonth; i++) {
			Calendar mincld = Calendar.getInstance();
			mincld.set(Calendar.MONTH, i);
			mincld.add(Calendar.YEAR, year);
			// String mth=format1.format(mincld.getTime());
			if (format2.format(mincld.getTime()).equals("Dec") || format2.format(mincld.getTime()).equals("Jan")) {
				months.add(format2.format(mincld.getTime()).toUpperCase() + "" + '\''
						+ format1.format(mincld.getTime()).toUpperCase());
			} else {
				months.add(format2.format(mincld.getTime()).toUpperCase());

			}
		}
		return months;
	}

	public String getDateDD(int date) {
		// get the date in DD formte
		Calendar c = Calendar.getInstance();
		c.add(Calendar.DATE, date);
		System.out.println(new SimpleDateFormat("dd").format(c.getTime()));
		return new SimpleDateFormat("dd").format(c.getTime());
	}

	public String getToTxnMonth(int date, int month) {
		// get the date month value in dd mmm formate
		return getDateDD(date) + " " + getMonthMMM(month).toUpperCase();

	}

	public boolean verifytopTxnAmountOrder(List<String> amount) {
		// verify top 5 txn in descending order
		boolean txnAmountOrder = false;
		List<Double> amountorder = new ArrayList<Double>();
		for (String amt : amount) {
			amountorder.add(Double.parseDouble(amt.replaceAll("[$\\,]", "")));
		}
		for (int i = 0; i < amountorder.size(); i++) {
			for (int j = i + 1; j < amountorder.size(); j++) {
				if (amountorder.get(i) > amountorder.get(j)) {
					txnAmountOrder = true;
				} else {
					txnAmountOrder = false;
				}
			}

		}
		return txnAmountOrder;
	}

	public int getMonthMM(int date) {
		// get the months in mm formate
		Calendar c = Calendar.getInstance();
		c.add(Calendar.DATE, date);
		System.out.println(new SimpleDateFormat("MM").format(c.getTime()));
		return Integer.parseInt(new SimpleDateFormat("MM").format(c.getTime()));
	}

	public String getYearMonthDate(int year, int month) {
		// get the date of MM/dd/yyyy format when set the month year
		Calendar c = Calendar.getInstance();

		c.set(Calendar.MONTH, month);
		c.add(Calendar.YEAR, year);
		System.out.println(new SimpleDateFormat("MM/dd/yyyy").format(c.getTime()));
		return new SimpleDateFormat("MM/dd/yyyy").format(c.getTime());
	}

	public String getMonthsMM(int month) {
		// get the date of mm/dd/yyyy format when set the only month
		Calendar c = Calendar.getInstance();

		c.add(Calendar.MONTH, month);
		System.out.println(new SimpleDateFormat("MM/dd/yyyy").format(c.getTime()));
		return new SimpleDateFormat("MM/dd/yyyy").format(c.getTime());
	}

	public void clickEIAMTLink() {
		waitForPageToLoad();
		waitFor(3);
		// click on add manual txn
		click(EIAMTLink());
		waitForPageToLoad(2000);
	}

	public void clickLegendMonth(int month) {
		// click pn legends table row
		click(EIATrendMonthList().get(month));
		waitForPageToLoad(7000);
	}

	public void clickEIABackToEIA() {
		// click on back to expense analysis
		click(EIABackToEIA());
		waitForPageToLoad();
	}

	public void clickMonth(String filterName) {
// click on specified time filter 
		click(EIATimeFilterDropDown());
		selectTimeFilter(filterName);
		waitForPageToLoad();
		click(EIATrendMonthList().get(0));
		waitForPageToLoad();
	}

	public void clicTimeFilter(String filterName) {
//click specified filter
		click(EIATimeFilterDropDown());
		selectTimeFilter(filterName);
		waitForPageToLoad();

	}

	public String getMonthMMMMDD(int date) {
		// get the date in mmmm dd,yyyy format
		Calendar c = Calendar.getInstance();
		c.add(Calendar.DATE, date);
		System.out.println(new SimpleDateFormat("MMMM dd, yyyy").format(c.getTime()));
		return new SimpleDateFormat("MMMM dd, yyyy").format(c.getTime());
	}

	public void clickEIAHLCCatFilterCatlable() {
		// click on category filter
		click(EIAHLCCatFilterCatlable());
		waitForPageToLoad();
	}

	public void clickEIAHLCCatDone() {
		// click on catgeory filter done button
		click(EIAHLCCatDone());
	}

	public void clickIncome() {
		// navigated to income analysis finaap
		click(EIADropDown());
		click(EIAIncomeLink());
		waitForPageToLoad();

	}

	public void clickEAHLC(String HLC, int legend) {
		// click on specified HLC in category page
		click(EIATrendMonthList().get(legend));
		waitForPageToLoad(3000);
		click(EIAHLCCatName(HLC));
		waitForPageToLoad(3000);
	}

	public void clickEAHLC(String HLC) {
		// Select HLC in HLC page
		click(EIAHLCCatName(HLC));
		waitForPageToLoad();
	}

	public void updateTxn(String[] subCat, String[] decription, String[] amount, String catName) {
		// updated HLC txn in HLC page
		for (int i = 0; i < subCat.length; i++) {
			click(EIAMLCCatName(catName));
			waitForPageToLoad(5000);
			click(EIAMLCTxnAmount(amount[i]));
			waitForPageToLoad(8000);
			EIAMLCTxnDescriptionField().clear();
			waitForPageToLoad(9000);
			EIAMLCTxnDescriptionField().sendKeys(decription[i]);
			click(EIAMLCTxnCatDropDown());
			waitForPageToLoad(8000);
			click(EIAMLCTxnSubCat(subCat[i]));
			waitForPageToLoad(6000);
			click(EIAMLCTxnSave());
			waitForPageToLoad(5000);
		}
	}

	public void clickTrendPopUp() {
		// click on trend popup in MLC page
		click(EIAMoreLink());
		waitForPageToLoad(500);
		click(EIAMLCTrendLink());
		waitForPageToLoad(2000);
	}

	public WebElement EIASpendingTab() {
//	spending tb
		return getWebElement(d, "EIASpendingTab", pageName, frameName);
	}

	public WebElement EIAExpenseTab() {
//	expense tab
		return getWebElement(d, "EIAExpenseTab", pageName, frameName);
	}

	public void navigateToEA() {
		waitForPageToLoad();
		// navigate to new expense income page
		click(EIASpendingTab());
		click(EIAExpenseTab());
		waitUntilSpinnerWheelIsInvisible();
	}

	public void pageRefresh() {
		// page refresh to get added data to load
		for (int i = 0; i < 5; i++) {
			d.navigate().refresh();
			waitUntilSpinnerWheelIsInvisible();
		}
		waitForPageToLoad(2000);
	}

	public void clickMore() {
		// click on more button
		click(EIAMoreLink());
		waitForPageToLoad(500);
	}

	public void clickGroup(String group) {
		// click on group filter
		clickEIAccountDropDown();
		if (this.isMobile_GroupLink_Present()) {
			click(Mobile_GroupLink());
			waitForPageToLoad(3000);
		}
		click(EIAGroupName(group));
		click(EIAGroupLink());
		EIAGroupName(group);
		waitUntilSpinnerWheelIsInvisible();
	}

	public boolean isgroups_mob_AMTPresent() {
		return PageParser.isElementPresent("groups_mob", "Expense", null);
	}

	public void clickAccountLink() {
		click(this.EIADropDownAccountLink());
	}

	public void selectIncome() {
		// select the come analysis option
		click(EIAHeaderIcon());
		waitForPageToLoad(500);
		click(EIAIncomeOption());
		waitForPageToLoad();
	}

	public void selectExpense() {
		// select the expense option
		click(EIAHeaderIcon());
		waitForPageToLoad(500);
		click(EIAExpenseOption());
		waitForPageToLoad();
	}

	public void FTUE() {
		// FTUE skip method
		click(EIAFTUEContinue());
		click(EIAFTUEGoto());
	}

	public void changeCat(String MLCRow, String updatedMLC, String[] amount) {
		// update catgeory in refund or adjustment page
		int txnsize = amount.length;
		for (int i = 0; i < txnsize; i++) {
			click(EIAMLCCatName(MLCRow));
			waitForPageToLoad();
			click(EIAMLCTxnAmount(amount[i]));
			waitForPageToLoad(500);
			click(EIAMLCTxnCatDropDown());
			waitForPageToLoad(500);
			click(EIAMLCTxnDropDownCatList(updatedMLC));
			waitForPageToLoad(500);

			click(EIAMLCTxnSave());
			waitForPageToLoad();

		}

	}

	public void clickViewDetails() {
		// click on view details of MLC page
		click(EIAHLCCatRefundViewDetails());
		waitForPageToLoad();
	}

	public void updateRefundTxn(String MLC) {
		// update refund txn in refund page
		click(EIARefundTXnCat().get(0));
		waitForPageToLoad(500);
		click(EIAMLCTxnCatDropDown());
		waitForPageToLoad(500);
		click(EIAMLCTxnDropDownCatList(MLC));
		waitForPageToLoad(500);

		click(EIAMLCTxnSave());
		waitForPageToLoad();
	}

	public void deleteallProjectedtransaction() {
		// delete transaction refund page
		for (int i = 0; i < 2; i++) {
			click(EIARefundTXnCat().get(0));
			waitForPageToLoad(2000);
			click(EIARefundTXnShowMore());
			waitForPageToLoad(2000);
			click(EIARefundTXnDelete());
			waitForPageToLoad(2000);
			click(EIARefundTXnDeletePopUP());
			waitForPageToLoad(5000);

		}

	}

	public void selectMultiAccount(String accounts) {
		String[] acct = accounts.split(",");
		for (int i = 0; i < acct.length; i++) {
			this.selectAccount(acct[i]);
		}
	}

	public List<WebElement> AccountGroupTab() {
		return getWebElements(d, "AccountGroupTab", pageName, frameName);
	}

	public void openAccountsDropdown() {
		if (getAttribute(accountGroup, "aria-expanded").equalsIgnoreCase("false")) {
			click(accountGroup);
		}
	}

	public void clickOnGroupsTabUnderAcntDropdown() {
		openAccountsDropdown();
		waitUntilSpinnerWheelIsInvisible();
		click(AccountGroupTab().get(1));
	}

	public boolean verifyAccountsGroupNameLabel(String groupName) {
		List<WebElement> l = getWebElements(d, "AccountsGroupNameLabel", pageName, frameName);
		boolean status = CommonUtils.assertEqualsListElements(groupName, l);
		return status;
	}

	public List<WebElement> getAccountsUnderGroup(String IndexOfGroup) {

		String xpath = getVisibileElementXPath(d, pageName, frameName, "AccountsUnderGroup");
		xpath = xpath.replaceAll("GroupIndex", IndexOfGroup);
		return d.findElements(By.xpath(xpath));
	}

	public void navigateToIncomeAnalysis() {
		click(EIADropDown());
		click(EIAIncomeLink());
		waitForPageToLoad(3000);

	}

	public List<WebElement> manageCreateGroup() {
		return getWebElements(d, "manageCreateGroup", "AccountGroups", frameName);
	}

	public boolean verifyManageCreateGroups(String propValue) {
		List<WebElement> l = getWebElements(d, "manageCreateGroup", "AccountGroups", frameName);
		boolean status = CommonUtils.assertEqualsListElements(propValue, l);
		return status;
	}

	public void clickingOnManageGroupBtn() {
		waitUntilSpinnerWheelIsInvisible();
		if (isDisplayed(accountGroup, 1)) {
			openAccountsDropdown();
		}
		click(manageCreateGroup().get(0));
	}

	public void clickingOnAddGroupBtn() {
		waitUntilSpinnerWheelIsInvisible();
		if (isDisplayed(accountGroup, 1)) {
			openAccountsDropdown();
		}
		click(manageCreateGroup().get(1));
	}

	public boolean verifyGreyedOutAcnt(String propValue) {
		List<WebElement> l = getWebElements(d, "greyedOutAcntsUnderGroupDD", "AccountGroups", frameName);
		boolean status = CommonUtils.assertContainsListElements(propValue, l);
		return status;
	}

	public int verifyAllAcntsUnderGroupSection(String indexOfGroup) {
		List<WebElement> list = getAccountsUnderGroup(indexOfGroup);
		return list.size();
	}

	public List<WebElement> getAccountsUnderGroupInCF(String IndexOfGroup) {
		String xpath = getVisibileElementXPath(d, pageName, frameName, "AccountsUnderGroupCashFlow");
		xpath = xpath.replaceAll("GroupIndex", IndexOfGroup);
		return d.findElements(By.xpath(xpath));
	}

	public int verifyAllAcntsUnderGroupSectionInCF(String indexOfGroup) {
		List<WebElement> list = getAccountsUnderGroupInCF(indexOfGroup);
		return list.size();
	}

	public boolean isMobile_GroupLink_Present() {
		return PageParser.isElementPresent("Group_mob", "Expense", null);

	}

	private WebElement Mobile_GroupLink() {
		return getWebElement(d, "Group_mob", "Expense", null);
	}

	public boolean isMobile_ClickonGroup_Present() {
		return PageParser.isElementPresent("EIAGroupName", "Expense", null);

	}

	public WebElement Expense_done_mob() {
		return getWebElement(d, "Expense_done_mob", "Expense", null);

	}

	public boolean isExpense_done_mob_Present() {
		return PageParser.isElementPresent("Expense_done_mob", "Expense", null);
	}

	public List<String> xAixValue1(int month) {
		// get the selected time filter x axix avalue
		List<String> monthList = new ArrayList<String>();
		int j = 0;
		for (int i = 0; i < month; i++) {
			if (getMonthMMM(j).equals("Dec") || getMonthMMM(j).equals("Jan")) {
				monthList.add(getMonthMMM(j).toUpperCase() + " " + '\'' + getYearYY(j).toUpperCase());

				j = j - 1;
			} else {
				monthList.add(getMonthMMM(j).toUpperCase());

				j = j - 1;
			}

		}
		return monthList;
	}

	public boolean isSelectAcc_Donebtn() {
		return PageParser.isElementPresent("SelectAcc_Donebtn", "Expense", null);
	}

	public WebElement SelectAcc_Donebtn() {
		return getWebElement(d, "SelectAcc_Donebtn", "Expense", null);
	}

	public boolean ismoreBtn_mob_Present() {
		return PageParser.isElementPresent("moreBtn_mob", "AccountsPage", null);
	}

	public WebElement moreBtn_mob() {
		return getVisibileWebElement(d, "moreBtn_mob", "AccountsPage", null);
	}

	public WebElement EIAMTLink_mob() {
		return getVisibileWebElement(d, "EIAMTLink_mob", "Expense", null);
	}

	public WebElement EIALinkAccount_mob() {
		return getVisibileWebElement(d, "EIALinkAcc_Mob", "Expense", null);
	}

	public boolean isbacktoEIAicon() {
		return PageParser.isElementPresent("backbuttonmobile", pageName, null);
	}

	public WebElement backtoEIAicon() {
		// back to expense analysis element
		return getWebElement(d, "backbuttonmobile", pageName, frameName);
	}

	public WebElement EIAUncatgmob() {
		return getWebElement(d, "EIAUncatgmob", pageName, frameName);
	}

	public WebElement EIAHLCCatUnCatTxnHeader_mob() {
		return getWebElement(d, "EIAHLCCatUnCatTxnHeader_mob", pageName, frameName);
	}

	public WebElement EAclickuncatgtransc() {
		return getWebElement(d, "EAclickuncatgtransc", pageName, frameName);
	}

	public boolean isEAclckbacktouncatgicon() {
		return PageParser.isElementPresent("EAclckbacktouncatgicon", pageName, null);
	}

	public WebElement EAclckbacktouncatgicon() {
		return getWebElement(d, "EAclckbacktouncatgicon", pageName, frameName);
	}

	public WebElement EAcloseUnctgTransPopup() {
		return getWebElement(d, "EAcloseUnctgTransPopup", pageName, frameName);

	}

	public boolean isEIAcloseTransPopup() {
		return PageParser.isElementPresent("EIAcloseTransPopup", pageName, frameName);

	}

	public WebElement EIAcloseTransPopup() {
		return getWebElement(d, "EIAcloseTransPopup", pageName, frameName);

	}

	public WebElement EIATransbtn_mob() {
		return getWebElement(d, "EIATransbtn_mob", pageName, frameName);
	}

	public WebElement EIALinkAcc_Mob() {
		return getWebElement(d, "EIALinkAcc_Mob", pageName, frameName);

	}

	public boolean EIATopTransc() {
		return PageParser.isElementPresent("EIATopTransc", pageName, frameName);

	}

	public WebElement EIABackToEIA1() {
		// back to expense analysis element
		return getWebElement(d, "EIABackToEIA_mob", pageName, frameName);
	}

	public WebElement UncatAccTxn() {
		return getWebElement(d, "UncatAccTxn", pageName, frameName);
	}

//Added by Mallika for EAIA-Transaction Update Locators starts here
	public WebElement transMonth_EIA() {
		return getWebElement(d, "transMonth_EIA", pageName, frameName);
	}

	public WebElement firstCateg_EIA() {
		waitForPageToLoad();
		return getWebElement(d, "firstCateg_EIA", pageName, frameName);
	}

	public WebElement secondCateg_EIA() {
		waitForPageToLoad();
		return getWebElement(d, "secondCateg_EIA", pageName, frameName);
	}

	public WebElement thirdCateg_EIA() {
		waitForPageToLoad();
		return getWebElement(d, "firstCateg_EIA", pageName, frameName);
	}

	public WebElement fourthCateg_EIA() {
		waitForPageToLoad();
		return getWebElement(d, "firstCateg_EIA", pageName, frameName);
	}

	public WebElement fifthCateg_EIA() {
		waitForPageToLoad();
		return getWebElement(d, "firstCateg_EIA", pageName, frameName);
	}

	public WebElement firstTransDetails_EIA() {
		waitForPageToLoad(8000);
		return getWebElement(d, "firstTransDetails_EIA", pageName, frameName);
	}

	public WebElement firstTransDetails_EIA_Mob() {
		return getWebElement(d, "firstTransDetails_EIA_Mob", pageName, frameName);
	}

	public WebElement popUpBackBtn_EIA_Mob() {
		waitForPageToLoad();
		return getWebElement(d, "popUpBackBtn_EIA_Mob", pageName, frameName);
	}

	public WebElement manualTransDetails_EIA() {
		waitForPageToLoad();
		return getWebElement(d, "manualTransDetails_EIA", pageName, frameName);
	}

	public WebElement popUpCloseBtn_EIA_Mob() {
		waitForPageToLoad();
		return getWebElement(d, "popUpCloseBtn_EIA_Mob", pageName, frameName);
	}

	public WebElement secondTransDetails_EIA() {
		waitForPageToLoad();
		return getWebElement(d, "secondTransDetails_EIA", pageName, frameName);
	}

	public List<WebElement> transDetails_EIA() {
		waitForPageToLoad();
		return getWebElements(d, "transDetails_EIA", pageName, frameName);
	}

	public WebElement pieChartFirstUpdatedTransDesc_EIA() {
		return getWebElement(d, "pieChartFirstUpdatedTransDesc_EIA", pageName, frameName);
	}

	public WebElement updateTransDesc_EIA() {
		waitForPageToLoad();
		return getWebElement(d, "updateTransDesc_EIA", pageName, frameName);
	}

	public WebElement saveButton_EIA() {
		waitForPageToLoad();
		return getWebElement(d, "saveButton_EIA", pageName, frameName);
	}

	public WebElement firstUpdatedTransDesc_EIA() {
		return getWebElement(d, "firstUpdatedTransDesc_EIA", pageName, frameName);
	}

	public WebElement secondUpdatedTransDesc_EIA() {
		return getWebElement(d, "secondUpdatedTransDesc_EIA", pageName, frameName);
	}

	public WebElement thirdTransDetails_EIA() {
		return getWebElement(d, "thirdTransDetails_EIA", pageName, frameName);
	}

	public WebElement fourthTransDetails_EIA() {
		return getWebElement(d, "fourthTransDetails_EIA", pageName, frameName);
	}

	public WebElement updateTransTag_EIA() {
		return getWebElement(d, "updateTransTag_EIA", pageName, frameName);
	}

	public WebElement tagNameInputSearch_EIA() {
		return getWebElement(d, "tagNameInputSearch_EIA", pageName, frameName);
	}

	public WebElement addedTagName_EIA() {
		waitForPageToLoad();
		return getWebElement(d, "addedTagName_EIA", pageName, frameName);
	}

	public WebElement selectTagNameFromInputSearch_EIA() {
		return getWebElement(d, "selectTagNameFromInputSearch_EIA", pageName, frameName);
	}

	public WebElement dropdownTagname_EIA() {
		return getWebElement(d, "dropdownTagname_EIA", pageName, frameName);
	}

	public WebElement showMoreOptionsBtn_EIA() {
		waitForPageToLoad();
		return getWebElement(d, "showMoreOptionsBtn_EIA", pageName, frameName);
	}

	public WebElement EIALinkAccount_EIA() {
		waitForPageToLoad();
		return getWebElement(d, "EIALinkAccount", pageName, frameName);
	}

	public WebElement backButton_EIA_Mob() {
		waitForPageToLoad(3000);
		return getWebElement(d, "backButton_EIA_Mob", pageName, frameName);
	}

	public WebElement backToEA_EIA() {
		waitForPageToLoad();
		return getWebElement(d, "backToEA_EIA", pageName, frameName);
	}

	public WebElement pieChartNoteIcon_EIA() {
		waitForPageToLoad();
		return getWebElement(d, "pieChartNoteIcon_EIA", pageName, frameName);
	}

	public WebElement updatedTransDesc_EIA_Mob() {
		waitForPageToLoad();
		return getWebElement(d, "updatedTransDesc_EIA_Mob", pageName, frameName);
	}

	public WebElement updateTransNote_EIA() {
		return getWebElement(d, "updateTransNote_EIA", pageName, frameName);
	}

	public WebElement splitTransLink_EIA() {
		waitForPageToLoad(8000);
		return getWebElement(d, "splitTransLink_EIA", pageName, frameName);
	}

	public WebElement splitFirstCat_EIA() {
		return getWebElement(d, "splitFirstCat_EIA", pageName, frameName);
	}

	public WebElement splitTranIcon_EIA() {
		waitForPageToLoad();
		return getWebElement(d, "splitTranIcon_EIA", pageName, frameName);
	}

	public WebElement category_EIA() {
		return getWebElement(d, "category_EIA", pageName, frameName);
	}

	public WebElement categoryDropDown_EIA() {
		return getWebElement(d, "categoryDropDown_EIA", pageName, frameName);
	}

	public WebElement updatedTransCategory_EIA() {
		waitForPageToLoad(8000);
		return getWebElement(d, "updatedTransCategory_EIA", pageName, frameName);
	}

	public WebElement pieUpdatedTransCategory_EIA() {
		waitForPageToLoad(8000);
		return getWebElement(d, "pieUpdatedTransCategory_EIA", pageName, frameName);
	}

	public WebElement splitTranSaveBtn_EIA() {
		waitForPageToLoad();
		return getWebElement(d, "splitTranSaveBtn_EIA", pageName, frameName);
	}

	public WebElement addedTagIcon_EIA() {
		waitForPageToLoad();
		return getWebElement(d, "addedTagIcon_EIA", pageName, frameName);
	}

	public WebElement pieChartFirstCategDetails_EIA() {
		waitForPageToLoad();
		return getWebElement(d, "pieChartFirstCategDetails_EIA", pageName, frameName);
	}

	public WebElement pieChartThirdCategDetails_EIA() {
		return getWebElement(d, "pieChartThirdCategDetails_EIA", pageName, frameName);
	}

	public WebElement pieUpdatedTagIcon_EIA() {
		return getWebElement(d, "pieUpdatedTagIcon_EIA", pageName, frameName);
	}

	public WebElement pieChartFirstUpdatedTransDesc_EIA_Mob() {
		return getWebElement(d, "pieChartFirstUpdatedTransDesc_EIA_Mob", pageName, frameName);
	}

	public WebElement pieChartFirstUpdatedTransIcon_EIA_Mob() {
		return getWebElement(d, "pieChartFirstUpdatedTransIcon_EIA_Mob", pageName, frameName);
	}

	public WebElement pieCategoryDropDown_EIA() {
		return getWebElement(d, "pieCategoryDropDown_EIA", pageName, frameName);
	}

	public WebElement manualAccUpdateAmount_EIA() {
		return getWebElement(d, "manualAccUpdateAmount_EIA", pageName, frameName);
	}

	public WebElement manualAccUpdatedAmountVerify_EIA() {
		return getWebElement(d, "manualAccUpdatedAmountVerify_EIA", pageName, frameName);
	}
//Added by Mallika for EAIA-Transaction Update Loccators ends here

}
