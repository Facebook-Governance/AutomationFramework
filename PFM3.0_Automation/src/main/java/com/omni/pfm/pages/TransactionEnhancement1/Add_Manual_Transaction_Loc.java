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
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;

import com.omni.pfm.PageProcessor.PageParser;
import com.omni.pfm.config.Config;
import com.omni.pfm.utility.PropsUtil;
import com.omni.pfm.utility.SeleniumUtil;

public class Add_Manual_Transaction_Loc extends SeleniumUtil {

	static Logger logger = LoggerFactory.getLogger(Add_Manual_Transaction_Loc.class);
	public static WebDriver d = null;
	static WebElement we;

	public static final By tags = getByObject("Transaction", null, "unlimittag");
	public static final By cancelManualTransaction = getByObject("Transaction", null, "cancel_AMT");
	public static final By addmanualTransactionCloseButton = getByObject("Transaction", null, "AddManualclose");
	public static final By amountError = getByObject("Transaction", null, "amountErr_AMT");
	public static final By descriptionError = getByObject("Transaction", null, "descErr_AMT");
	public static final By scheduleError = getByObject("Transaction", null, "scheduleErr_AMT");
	public static final By symbolError = getByObject("Transaction", null, "symbolErr_AMT");
	public static final By priceError = getByObject("Transaction", null, "priceErr_AMT");
	public static final By quantityError = getByObject("Transaction", null, "quantityErr_AMT");
	public static final By accountDropDownInAddManualTransactionPopup = getByObject("Transaction", null, "accountDropDownInAddManualTransactionPopup");

	// quantityErr_AMT
	public Add_Manual_Transaction_Loc(WebDriver d) {
		this.d = d;
	}

	public WebElement tag() {
		return getVisibileWebElement(d, "tag_AMT", "Transaction", null);
	}

	public WebElement addManualIcon() {
		return getWebElement(d, "addManualIcon_AMT", "Transaction", null);
	}

	public WebElement MobileaddManualIcon_AMT() {
		return getWebElement(d, "MobileaddManualIcon_AMT", "Transaction", null);
	}

	public boolean isMobileaddManualIcon_AMTPresent() {
		return PageParser.isElementPresent("MobileaddManualIcon_AMT", "Transaction", null);
	}

	public WebElement MobileMore() {
		return getVisibileWebElement(d, "mobilemoreBtn", "Transaction", null);
	}

	public boolean isMoreBtnPresent() {
		return PageParser.isElementPresent("mobilemoreBtn", "Transaction", null);
	}

	public List<WebElement> ListofTrans() {
		return getWebElements(d, "ListofTrans", "Transaction", null);
	}

	public WebElement AllAcc_TravelClick() {
		return getWebElement(d, "AllAcc_TravelClick", "Transaction", null);
	}

	public WebElement AllCat_tran() {
		return getWebElement(d, "AllCat_tran", "Transaction", null);
	}

	public WebElement AllAcc_Checbox() {
		return getWebElement(d, "AllAcc_Checbox", "Transaction", null);
	}

	public List<WebElement> tranRowsinTran() {
		return getWebElements(d, "tranRowsinTran", "Transaction", null);
	}

	public WebElement withdrawinTrantype() {
		return getWebElement(d, "withdrawinTrantype", "Transaction", null);
	}

	public List<WebElement> SystemGenTrans() {
		return getWebElements(d, "SystemGenTrans", "Transaction", null);
	}

	public WebElement mobileTransactionIcon() {
		return getVisibileWebElement(d, "mobletra", "Transaction", null);
	}

	public void clickAddManualTransaction() {
		click(MobileMore());
		click(MobileaddManualIcon_AMT());
	}

	public WebElement addManualLink() {
		return getVisibileWebElement(d, "addManualLink_AMT", "Transaction", null);
	}

	public WebElement MobileaddManualLink_AMT() {
		return getVisibileWebElement(d, "MobileaddManualLink_AMT", "Transaction", null);
	}

	public boolean isMobileaddManualLink_AMTPresent() {
		return PageParser.isElementPresent("MobileaddManualLink_AMT", "Transaction", null);
	}

	public WebElement addAccount() {
		return getVisibileWebElement(d, "addAccountSearchClass", "Transaction", null);
	}

	public WebElement addManualHeader() {
		return getVisibileWebElement(d, "addManualHeader_AMT", "Transaction", null);
	}

	public WebElement currencyDroDown() {
		return getVisibileWebElement(d, "currencyDroDown_AMT", "Transaction", null);
	}

	public WebElement currencyValue() {
		return getVisibileWebElement(d, "currencyValue_AMT", "Transaction", null);
	}

	public WebElement TransactionType() {
		return getVisibileWebElement(d, "TransactionType_AMT", "Transaction", null);
	}

	public WebElement frequencytext() {
		return getVisibileWebElement(d, "frequencytext_AMT", "Transaction", null);
	}

	public WebElement frequencyDropDown() {
		return getVisibileWebElement(d, "frequencyDropDown_AMT", "Transaction", null);
	}

	public WebElement Schedule() {
		return getVisibileWebElement(d, "Schedule_AMT", "Transaction", null);
	}

	public WebElement calendIcon() {
		return getVisibileWebElement(d, "calendIcon_AMT", "Transaction", null);
	}

	public void clickScheduledDateIcon() {
		click(calendIcon());
	}

	public boolean ScheduledDatePopup() {
		clickScheduledDateIcon();
		boolean calendPopUpvalue = visibleWeElement(calendPopUp()).isDisplayed();
		return calendPopUpvalue;
	}

	public WebElement calendPopUp() {
		return getVisibileWebElement(d, "calendPopUp_AMT", "Transaction", null);
	}

	public List<WebElement> FrequencyList() {
		return getWebElements(d, "FrequencyList_AMT", "Transaction", null);
	}

	public List<WebElement> getAllCatAdd() {
		return getWebElements(d, "getAllCatAdd", "Transaction", null);
	}

	public WebElement endDate() {
		return getVisibileWebElement(d, "endDate_AMT", "Transaction", null);
	}

	public WebElement endDateIcon() {
		return getVisibileWebElement(d, "endDateIcon_AMT", "Transaction", null);
	}

	public WebElement infoIcon() {
		return getVisibileWebElement(d, "infoIcon_AMT", "Transaction", null);
	}

	public WebElement infoMessage() {
		return getVisibileWebElement(d, "infoMessage_AMT", "Transaction", null);
	}

	public WebElement pluseIcon() {
		return getVisibileWebElement(d, "pluseIcon_AMT", "Transaction", null);
	}

	public WebElement AddManulTransactionTag() {
		return getVisibileWebElement(d, "AddManulTransactionTag", "Transaction", null);
	}

	public WebElement showMoreDetails() {
		return getVisibileWebElement(d, "showMoreDetails_AMT", "Transaction", null);
	}

	public void clikcShowMore() {
		click(visibleWeElement(showMoreDetails()));
		waitFor(1);
	}

	public WebElement SFCshowMoreDetails() {
		return getWebElement(d, "SFCshowMoreDetails", "Transaction", null);
	}

	public WebElement note() {
		return getVisibileWebElement(d, "note_AMT", "Transaction", null);
	}

	public WebElement check() {
		return getVisibileWebElement(d, "check_AMT", "Transaction", null);
	}

	public WebElement checkList() {
		return getWebElement(d, "nocheck_AMT", "Transaction", null);
	}

	public WebElement amount() {
		return getVisibileWebElement(d, "amount_AMT", "Transaction", null);
	}

	public WebElement cancel() {
		return getVisibileWebElement(d, "cancel_AMT", "Transaction", null);
	}

	public List<WebElement> cancelList() {
		return getWebElements(d, "cancel_AMT", "Transaction", null);
	}

	public WebElement close() {
		return getVisibileWebElement(d, "AddManualclose", "Transaction", null);
	}

	/*
	 * public List<WebElement> closelist() { return getWebElements(d,
	 * "AddManualclose", "Transaction", null); }
	 */

	public WebElement description() {
		return getVisibileWebElement(d, "description_AMT", "Transaction", null);
	}

	public WebElement accountdropdown() {
		return getVisibileWebElement(d, "accountdropdown_AMT", "Transaction", null);
	}

	public WebElement catsearchField() {
		return getVisibileWebElement(d, "catsearchField_AMT", "Transaction", null);
	}

	public List<WebElement> CatSearched() {
		return getWebElements(d, "CatSearched_AMT", "Transaction", null);
	}

	public List<WebElement> ListAccount(int container) {

		String abC = getVisibileElementXPath(d, "Transaction", null, "ListAccount_AMT");
		abC = abC.replaceAll("container", Integer.toString(container));

		return d.findElements(By.xpath(abC));

	}

	public List<WebElement> AccountSharingAccount_AMT() {

		return getWebElements(d, "AccountSharingAccount_AMT", "Transaction", null);

	}

	public WebElement ListAccountMobile() {

		return getWebElement(d, "MobileListAccount_AMT", "Transaction", null);

	}

	public WebElement upDateFilter() {
		return getVisibileWebElement(d, "updt", "Transaction", null);
	}

	public WebElement AggregatedCheck() {
		return getWebElement(d, "AggregatedCheck", "Transaction", null);
	}

	public WebElement AddManualAccount() {
		return getWebElement(d, "AddManualAccount", "Transaction", null);
	}

	public List<WebElement> ListAccountNew() {
		List<WebElement> we = getWebElements(d, "selectAccount", "Transaction", null);
		return we;
	}

	public List<WebElement> accountForTrans() {
		return getWebElements(d, "selectCreditAccount", "Transaction", null);

	}

	public String targateDate1(int futureDate) {
		Calendar c = Calendar.getInstance();
		c.add(Calendar.DATE, futureDate);
		System.out.println(new SimpleDateFormat("MM/dd/yyyy").format(c.getTime()));
		return new SimpleDateFormat("MM/dd/yyyy").format(c.getTime());
	}

	public String targateMonth(int futureMonth) {
		Calendar c = Calendar.getInstance();
		c.add(Calendar.MONTH, futureMonth);
		System.out.println(new SimpleDateFormat("MM/dd/yyyy").format(c.getTime()));
		return new SimpleDateFormat("MM/dd/yyyy").format(c.getTime());
	}

	public WebElement catgorie() {
		return getVisibileWebElement(d, "catgorie_AMT", "Transaction", null);
	}

	public WebElement MobileFilterIcon() {
		return getVisibileWebElement(d, "MobileFilterIcon", "Transaction", null);
	}

	public boolean isMobileFilterIcon() {
		return PageParser.isElementPresent("MobileFilterIcon", "Transaction", null);
	}

	public WebElement DateSelectiondoneMobile() {
		return getVisibileWebElement(d, "MobileSerachDoneButton", "Transaction", null);
	}

	public WebElement MobileApplyButton() {
		return getVisibileWebElement(d, "MobileApplyButton", "Transaction", null);
	}

	public WebElement catgoryList(int MLC, int HLC) {

		String abC = getVisibileElementXPath(d, "Transaction", null, "newcatgoryList_AMT");
		abC = abC.replaceAll("MLC", Integer.toString(MLC));
		System.out.println(abC);
		abC = abC.replaceAll("HLC", Integer.toString(HLC));
		System.out.println(abC);

		return d.findElement(By.xpath(abC));

	}

	public WebElement newcatgoryList_AMT(int MLC, int HLC) {

		String abC = getVisibileElementXPath(d, "Transaction", null, "newcatgoryList_AMT");
		abC = abC.replaceAll("MLC", Integer.toString(MLC));
		System.out.println(abC);
		abC = abC.replaceAll("HLC", Integer.toString(HLC));
		System.out.println(abC);

		return d.findElement(By.xpath(abC));

	}

	public List<WebElement> catgoryList2() {
		return getWebElements(d, "categorySelect2", "Transaction", null);

	}

	public List<WebElement> selectCategory() {
		return getWebElements(d, "selectCategoryForTrans", "Transaction", null);

	}

	public WebElement AddManualTransactTagMobile() {
		return getVisibileWebElement(d, "AddManualTransactTagMobile", "Transaction", null);
	}

	public boolean isAddManualTransactTagMobile() {
		return PageParser.isElementPresent("AddManualTransactTagMobile", "Transaction", null);
	}

	public WebElement AddManualTransactTag() {
		return getVisibileWebElement(d, "AddManualTransactTag", "Transaction", null);
	}

	public void tagSearchMT(String tag) {
		AddManualTransactTag().clear();
		AddManualTransactTag().sendKeys(tag);
	}

	public WebElement AddManualTransactTagCreate() {
		return getWebElement(d, "AddManualTransactTagCreate", "Transaction", null);
	}

	public WebElement AddManualTransactTagCreateMobile() {
		return getWebElement(d, "AddManualTransactTagCreateMobile", "Transaction", null);
	}

	public WebElement MobiletagBack() {
		return getWebElement(d, "MobiletagBack", "Transaction", null);
	}

	public List<WebElement> tagList() {
		return getWebElements(d, "tagList_AMT", "Transaction", null);
	}

	public List<WebElement> AddToCaltagList() {
		return getWebElements(d, "TransactionAddToCalTag", "Transaction", null);
	}

	public WebElement add() {
		return getWebElement(d, "add_AMT", "Transaction", null);
	}

	public WebElement UpdateSFCTransaction() {
		return getWebElement(d, "UpdateSFCTransaction", "FinancialForecast", null);
	}

	public WebElement TransactionAdded() {
		return getVisibileWebElement(d, "TransactionAdded_AMT", "Transaction", null);
	}

	public WebElement amountErr() {
		return getVisibileWebElement(d, "amountErr_AMT", "Transaction", null);
	}

	public List<WebElement> amountErrList() {
		return getWebElements(d, "amountErr_AMT", "Transaction", null);
	}

	public WebElement descErr() {
		return getWebElement(d, "descErr_AMT", "Transaction", null);
	}

	public List<WebElement> descErrList() {
		return getWebElements(d, "descErr_AMT", "Transaction", null);
	}

	public WebElement accountErr() {
		return getWebElement(d, "accountErr_AMT", "Transaction", null);
	}

	public List<WebElement> accountErrList() {
		return getWebElements(d, "accountErr_AMT", "Transaction", null);
	}

	public WebElement scheduleErr() {
		return getWebElement(d, "scheduleErr_AMT", "Transaction", null);
	}

	public List<WebElement> scheduleErrList() {
		return getWebElements(d, "scheduleErr_AMT", "Transaction", null);
	}

	public WebElement catgoryErr() {
		return getWebElement(d, "catgoryErr_AMT", "Transaction", null);
	}

	public List<WebElement> catgoryErrList() {
		return getWebElements(d, "catgoryErr_AMT", "Transaction", null);
	}

	public List<WebElement> TtransactionList() {
		return getWebElements(d, "TtransactionList_AMT", "Transaction", null);
	}

	public WebElement symbolErr() {
		return getVisibileWebElement(d, "symbolErr_AMT", "Transaction", null);
	}

	public List<WebElement> symbolErrList() {
		return getWebElements(d, "symbolErr_AMT", "Transaction", null);
	}

	public WebElement priceErr() {
		return getVisibileWebElement(d, "priceErr_AMT", "Transaction", null);
	}

	public List<WebElement> priceErrList() {
		return getWebElements(d, "priceErr_AMT", "Transaction", null);
	}

	public WebElement quantityErr() {
		return getVisibileWebElement(d, "quantityErr_AMT", "Transaction", null);
	}

	public List<WebElement> quantityErrList() {
		return getWebElements(d, "quantityErr_AMT", "Transaction", null);
	}

	public WebElement hordingErr() {
		return getWebElement(d, "hordingErr_AMT", "Transaction", null);
	}

	public List<WebElement> hordingErrList() {
		return getWebElements(d, "hordingErr_AMT", "Transaction", null);
	}

	public WebElement lotHoldingErr() {
		return getWebElement(d, "lotHoldingErr_AMT", "Transaction", null);
	}

	public List<WebElement> lotHoldingErrList() {
		return getWebElements(d, "lotHoldingErr_AMT", "Transaction", null);
	}

	public WebElement currencyV() {
		return getVisibileWebElement(d, "currencyV_AMT", "Transaction", null);
	}

	public WebElement mobilecurrencyV() {
		return getVisibileWebElement(d, "mobilecurryV", "Transaction", null);
	}

	public List<WebElement> currencyLIst() {
		return getWebElements(d, "currencyLIst_AMT", "Transaction", null);
	}

	public List<WebElement> CurrencyList() {
		return getWebElements(d, "CurrencyList", "Transaction", null);
	}

	public WebElement TransacV() {
		return getVisibileWebElement(d, "TransacV_AMT", "Transaction", null);
	}

	public WebElement mobileTransacV() {
		return getVisibileWebElement(d, "mobiletranV", "Transaction", null);
	}

	public WebElement accountV() {
		return getVisibileWebElement(d, "accountV_AMT", "Transaction", null);
	}

	public WebElement mobileaccountV() {
		return getVisibileWebElement(d, "mobileacctV", "Transaction", null);
	}

	public WebElement frequecyV() {
		return getVisibileWebElement(d, "frequecyV_AMT", "Transaction", null);
	}

	public WebElement mobilefrequecyV() {
		return getVisibileWebElement(d, "mobilefreqV", "Transaction", null);
	}

	public List<WebElement> Sub_Cat_int() {
		return getWebElements(d, "Sub_Cat_int", "Transaction", null);
	}

	public WebElement categoryV() {
		return getVisibileWebElement(d, "categoryV_AMT", "Transaction", null);
	}

	public WebElement mobilecategoryV() {
		return getVisibileWebElement(d, "mobilecatV", "Transaction", null);
	}

	public WebElement tagErr() {
		return getVisibileWebElement(d, "tagErr_AMT", "Transaction", null);
	}

	public List<WebElement> unlimittag() {
		return getWebElements(d, "unlimittag", "Transaction", null);
	}

	public void createtag(String tags) {
		click(AddManulTransactionTag());
		AddManualTransactTag().clear();
		AddManualTransactTag().sendKeys(tags);
		click(AddManualTransactTagCreate());
	}

	public void createtagnew(String tags) {
		AddManualTransactTag().clear();
		AddManualTransactTag().sendKeys(tags);
		click(AddManualTransactTagCreate());
	}

	public void enterValueToTagField(String tags) {
		click(AddManulTransactionTag());
		AddManualTransactTag().clear();
		AddManualTransactTag().sendKeys(tags);
	}

	public void enterValueToTag(String tags) {

		AddManualTransactTag().clear();
		AddManualTransactTag().sendKeys(tags);
	}

	public WebElement Plusedisable() {
		return getVisibileWebElement(d, "Plusedisable_AMT", "Transaction", null);
	}

	public WebElement checkErr() {
		return getVisibileWebElement(d, "checkErr_AMT", "Transaction", null);
	}

	public WebElement symbolTicket() {
		return getVisibileWebElement(d, "symbolTicket_AMT", "Transaction", null);
	}

	public WebElement CUSIP() {
		return getVisibileWebElement(d, "CUSIP_AMT", "Transaction", null);
	}

	public WebElement price() {
		return getVisibileWebElement(d, "price_AMT", "Transaction", null);
	}

	public WebElement quantity() {
		return getVisibileWebElement(d, "quantity_AMT", "Transaction", null);
	}

	public WebElement enddateerr() {
		return getVisibileWebElement(d, "enddateerr_AMT", "Transaction", null);
	}

	public List<WebElement> projectedDate() {
		return getWebElements(d, "projectedDate_AMT", "Transaction", null);
	}

	public WebElement accountdropDown() {
		return getVisibileWebElement(d, "accountdropDown_AMT", "Transaction", null);
	}

	public WebElement InvestmentType() {
		return getVisibileWebElement(d, "InvestmentType_AMT", "Transaction", null);
	}

	public List<WebElement> investmentList() {
		return getWebElements(d, "investmentList_AMT", "Transaction", null);
	}

	public WebElement Holdingtype() {
		return getVisibileWebElement(d, "Holdingtype_AMT", "Transaction", null);
	}

	public List<WebElement> listOfHolding() {
		return getWebElements(d, "listOfHolding_AMT", "Transaction", null);
	}

	public WebElement lot() {
		return getVisibileWebElement(d, "lot_AMT", "Transaction", null);
	}

	public WebElement tagIcon() {
		return getVisibileWebElement(d, "tagIcon_AMT", "Transaction", null);
	}

	public WebElement noteIocn() {
		return getVisibileWebElement(d, "noteIocn_AMT", "Transaction", null);
	}

	public List<WebElement> lotList() {
		return getWebElements(d, "lotList_AMT", "Transaction", null);
	}

	public List<WebElement> ListofCustomeTag() {
		return getWebElements(d, "ListofCustomeTag_AMT", "Transaction", null);
	}

	public List<WebElement> getAlldescription(String date) {

		String abC = getVisibileElementXPath(d, "Transaction", null, "getAlldescription_AMT");

		abC.replaceAll("varOne", date);

		return d.findElements(By.xpath(abC));

	}

	public List<WebElement> getAllcat(String date) {

		String abC = getVisibileElementXPath(d, "Transaction", null, "getAllcat_AMT");

		abC.replaceAll("varOne", date);

		return d.findElements(By.xpath(abC));

	}

	public List<WebElement> getAllAmount(String date) {

		String abC = getVisibileElementXPath(d, "Transaction", null, "getAllAmount_AMT");

		abC.replaceAll("varOne", date);

		return d.findElements(By.xpath(abC));

	}

	public List<WebElement> getAllaccount(String date) {

		String abC = getVisibileElementXPath(d, "Transaction", null, "getAllaccount_AMT");

		abC.replaceAll("varOne", date);

		return d.findElements(By.xpath(abC));

	}

	public List<WebElement> getAllNo(String date) {

		String abC = getVisibileElementXPath(d, "Transaction", null, "getAllNo_AMT");

		abC.replaceAll("varOne", date);

		return d.findElements(By.xpath(abC));

	}

	public List<WebElement> getAllFeq(String date) {

		String abC = getVisibileElementXPath(d, "Transaction", null, "getAllFeq_AMT");

		abC.replaceAll("varOne", date);

		return d.findElements(By.xpath(abC));

	}

	public List<WebElement> getAllPosteddescription(String date) {

		String abC = getVisibileElementXPath(d, "Transaction", null, "getAllPosteddescription_AMT");
		return d.findElements(By.xpath(abC));

	}

	public List<WebElement> getAllPostedCat(String date) {

		String abC = getVisibileElementXPath(d, "Transaction", null, "getAllPostedCat_AMT");

		abC.replaceAll("varOne", date);

		return d.findElements(By.xpath(abC));

	}

	public List<WebElement> getAllPostedAmount(String date) {

		String abC = getVisibileElementXPath(d, "Transaction", null, "getAllPostedAmount_AMT");

		abC.replaceAll("varOne", date);

		return d.findElements(By.xpath(abC));

	}

	public List<WebElement> getAllPostedAcount(String date) {

		String abC = getVisibileElementXPath(d, "Transaction", null, "getAllPostedAcount_AMT");

		abC.replaceAll("varOne", date);

		return d.findElements(By.xpath(abC));

	}

	public List<WebElement> getAllPostedAcountNo(String date) {

		String abC = getVisibileElementXPath(d, "Transaction", null, "getAllPostedAcountNo_AMT");

		abC.replaceAll("varOne", date);

		return d.findElements(By.xpath(abC));

	}

	public List<WebElement> getAllPostedCat1() {

		String abC = getVisibileElementXPath(d, "Transaction", null, "getAllPostedCat1_AMT");

		return d.findElements(By.xpath(abC));

	}

	public List<WebElement> getAllPostedAmount1() {

		String abC = getVisibileElementXPath(d, "Transaction", null, "getAllPostedAmount1_AMT");

		return d.findElements(By.xpath(abC));

	}

	public List<WebElement> getAllPostedAcount1() {

		String abC = getVisibileElementXPath(d, "Transaction", null, "getAllPostedAcount1_AMT");

		return d.findElements(By.xpath(abC));

	}

	public List<WebElement> getAlldescription1() {

		String abC = getVisibileElementXPath(d, "Transaction", null, "getAlldescription1_AMT");

		return d.findElements(By.xpath(abC));

	}

	public List<WebElement> getAllManualTransactionCat() {
		return getWebElements(d, "getAllCatAMT", "Transaction", null);
	}

	public void getAllCat() {
		String catg[] = PropsUtil.getDataPropertyValue("catg").split(",");
		catgorie().click();
		List<String> cat = new ArrayList<String>();

		for (int i = 0; i < getAllManualTransactionCat().size(); i++) {

			System.out.println(getAllManualTransactionCat().get(i).getText());
			System.out.println(catg[i]);
			cat.add(getAllManualTransactionCat().get(i).getText());
		}

	}

	public static Logger logger1 = LoggerFactory.getLogger(Add_Manual_Transaction_Loc.class);

	public WebElement Proj_saveBtn() {
		return getVisibileWebElement(d, "Proj_saveBtn", "Transaction", null);
	}

	public WebElement Proj_sche_date() {
		return getVisibileWebElement(d, "Proj_sche_date", "Transaction", null);
	}

	public List<WebElement> MarkasPaidBtn_proj() {
		return getWebElements(d, "MarkasPaidBtn_proj", "Transaction", null);
	}

	public List<WebElement> Project_accounts() {
		return getWebElements(d, "Project_accounts", "Transaction", null);
	}

	public WebElement ProjectedExapdIcon() {
		return getVisibileWebElement(d, "ProjectedExapdIcon", "Transaction", null);
	}

	public List<WebElement> ProjectedExapdIconList() {
		return getWebElements(d, "ProjectedExapdIcon", "Transaction", null);
	}

	public void getAllCategories() {
		String categories[] = PropsUtil.getDataPropertyValue("catg").split(",");
		catgorie().click();

		List<WebElement> catValues = getWebElements(d, "selectCategoryForTrans", "Transaction", null);

		String val = null;
		for (int i = 0; i < catValues.size(); i++) {

			if (catValues.get(i) != null) {
				String actual = null;
				try {
					actual = catValues.get(i).getAttribute("title");
				} catch (Exception er) {
					actual = null;
				}
				if (actual != null) {
					val = val + "," + actual;
					logger1.info("The category value [{}]", val);
					System.out.println(actual);
					System.out.println(categories[i]);
					Assert.assertTrue(actual.contains(categories[i]));
				}
			}

		}

	}

	public String DateInMMMMFormate(int date) {

		SimpleDateFormat formatter = new SimpleDateFormat("MMMM dd, yyyy ");
		Calendar c = Calendar.getInstance();
		c.add(Calendar.DATE, date);
		System.out.println(formatter.format(c.getTime()));
		return formatter.format(c.getTime());
	}

	public void createTransactionWithNote() {
		amount().sendKeys(PropsUtil.getDataPropertyValue("Amount1"));
		description().sendKeys(PropsUtil.getDataPropertyValue("description1"));
		accountdropdown().click();
		ListAccount(1).get(0).click();
		Schedule().sendKeys(targateDate1(0));
		catgorie().click();
		catgoryList(1, 1).click();
		click(AddManulTransactionTag());
		tagList().get(0).click();
		showMoreDetails().click();
		note().sendKeys("note");
		check().sendKeys("123456");
		add().click();

	}

	public void createTransactionWithOutNote(String Amount, String Desc, int account, int sche, int MLC, int HCL) {
		amount().sendKeys(Amount);
		amount().sendKeys(Keys.TAB);
		waitForPageToLoad();
		description().sendKeys(Desc);
		click(accountdropdown());
		// accountdropdown.click();
		waitForPageToLoad(5000);
		if (Config.getInstance().getEnvironment().contains("BBT")) {

			click(ListAccount(0).get(account));
		} else {
			click(ListAccount(1).get(account));
			// click(accountForTrans().get(account));
		}

		// ListAccount(1).get(account).click();
		Schedule().sendKeys(targateDate1(sche));
		click(catgorie());
		waitForPageToLoad();
		// catgorie.click();
		if (Config.getInstance().getEnvironment().contains("BBT")) {
			click(catgoryList(MLC, HCL));
		} else {
			click(catgoryList(MLC, HCL));
		}
		waitForPageToLoad(2000);
		// catgoryList(MLC, HCL).click();
		click(add());
		// add.click();
	}

	public void createTransactionWithOutNote(String amount, String Desc, String account, int sche, String MLC) {
		WebDriverWait wait = new WebDriverWait(d, 30);
		WebElement amt = wait.until(ExpectedConditions.visibilityOf(amount()));
		click(amt);
		amt.sendKeys(amount);
		click(description());
		description().sendKeys(Desc);
		click(accountdropdown());
		WebElement acc = wait.until(ExpectedConditions.visibilityOf(MTAccount(account)));
		click(acc);
		click(Schedule());
		Schedule().sendKeys(targateDate1(sche));
		click(catgorie());
		WebElement cat = wait.until(ExpectedConditions.visibilityOf(MTCatMLCCategory(MLC)));
		click(cat);
		click(add());
	}

	public String addManualTxnSucessMessage(String amount, String Desc, String account, int sche, String MLC,
			String NoteName, String check) {

		WebDriverWait wait = new WebDriverWait(d, 30);
		WebElement amt = wait.until(ExpectedConditions.visibilityOf(amount()));
		click(amt);
		amt.sendKeys(amount);
		click(description());
		description().sendKeys(Desc);
		click(accountdropdown());
		WebElement acc = wait.until(ExpectedConditions.visibilityOf(MTAccount(account)));
		click(acc);
		waitForPageToLoad(1000);
		click(Schedule());
		Schedule().sendKeys(targateDate1(sche));
		click(catgorie());
		WebElement cat = wait.until(ExpectedConditions.visibilityOf(MTCatMLCCategory(MLC)));
		click(cat);
		click(AddManulTransactionTag());
		click(tagList().get(0));
		click(showMoreDetails());
		waitForPageToLoad(1000);
		click(note());
		note().sendKeys(NoteName);
		check().clear();
		check().sendKeys(check);
		click(add());
		waitUntilSpinnerWheelIsInvisible();
		return TransactionAdded().getText();
	}

	public void enterAllMandatortField(String amount, String Desc, String account, int sche, String MLC,
			String NoteName, String check) {
		WebDriverWait wait = new WebDriverWait(d, 30);
		WebElement amt = wait.until(ExpectedConditions.visibilityOf(amount()));
		click(amt);
		amt.sendKeys(amount);
		click(description());
		description().sendKeys(Desc);
		click(accountdropdown());
		WebElement acc = wait.until(ExpectedConditions.visibilityOf(MTAccount(account)));
		click(acc);
		click(Schedule());
		Schedule().sendKeys(targateDate1(sche));
		click(catgorie());
		WebElement cat = wait.until(ExpectedConditions.visibilityOf(MTCatMLCCategory(MLC)));
		click(cat);
		click(AddManulTransactionTag());
		click(tagList().get(0));
		click(showMoreDetails());
		waitForPageToLoad(1000);
		click(note());
		note().sendKeys(NoteName);
		check().clear();
		check().sendKeys(check);
	}

	public void createTransactionWithRecur(String Amount, String Desc, int account, int freq, int sche, int enddate,
			int MLC, int HCL) {
		amount().sendKeys(Amount);
		description().sendKeys(Desc);
		accountdropdown().click();
		if (Config.getInstance().getEnvironment().toLowerCase().contains("bbt")) {
			click(accountForTrans().get(account));
		} else {
			click(ListAccount(1).get(account));
		}
		click(frequencyDropDown());
		click(FrequencyList().get(freq));
		Schedule().sendKeys(targateDate1(sche));
		endDate().clear();
		endDate().sendKeys(targateDate1(enddate));
		click(catgorie());
		if (Config.getInstance().getEnvironment().toLowerCase().contains("bbt")) {
			click(catgoryList(MLC, HCL));
		} else {
			click(catgoryList(MLC, HCL));
		}
		add().click();
		waitUntilSpinnerWheelIsInvisible();
	}

	public void createTransactionWithRecur1(String Amount, String Desc, int account, int freq, int sche, String enddate,
			int MLC, int HCL) {
		amount().sendKeys(Amount);
		description().sendKeys(Desc);
		click(accountdropdown());
		click(ListAccount(1).get(account));
		waitForPageToLoad(1000);
		click(frequencyDropDown());
		click(FrequencyList().get(freq));
		Schedule().sendKeys(targateDate1(sche));
		endDate().clear();
		endDate().sendKeys(enddate);
		click(catgorie());
		click(catgoryList(MLC, HCL));
		click(add());
		waitUntilSpinnerWheelIsInvisible();
	}

	public void createInvestmentTransaction(String Amount, String desc, int transactionType, int Container, int account,
			int date, String symbol, String CUS, String price1, String quty, int holding, int lot1) {
		click(amount());
		amount().sendKeys(Amount);
		description().sendKeys(desc);
		click(TransactionType());
		click(TtransactionList().get(transactionType));
		click(accountdropdown());
		click(ListAccount(Container).get(account));
		Schedule().sendKeys(targateDate1(date));
		symbolTicket().sendKeys(symbol);
		CUSIP().sendKeys(CUS);
		price().sendKeys(price1);
		quantity().sendKeys(quty);
		click(Holdingtype());
		click(listOfHolding().get(holding));
		click(lot());
		click(lotList().get(lot1));
		click(add());
		waitUntilSpinnerWheelIsInvisible();
	}

	public void investTxnErrorMsgDisAppear(String Amount, String desc, String account, int date, String symbol,
			String CUS, String price1, String quty, String holding, String lot1) {
		click(amount());
		amount().sendKeys(Amount);
		description().sendKeys(desc);
		click(accountdropdown());
		click(MTAccount(account));
		Schedule().sendKeys(targateDate1(date));
		symbolTicket().sendKeys(symbol);
		CUSIP().sendKeys(CUS);
		price().sendKeys(price1);
		quantity().sendKeys(quty);
		click(Holdingtype());
		click(listOfHoldingName_AMT(holding));
		click(lot());
		click(lotListName_AMT(lot1));
	}

	public WebElement MTFrquency(String frequency) {
		String frq = getVisibileElementXPath(d, "Transaction", null, "MTFrquency");
		frq = frq.replaceAll("freqType", frequency);
		return d.findElement(By.xpath(frq));

	}

	public WebElement MTAccount(String account) {
		String acc = getVisibileElementXPath(d, "Transaction", null, "MTAccount");
		acc = acc.replaceAll("acctName", account);
		return d.findElement(By.xpath(acc));

	}

	public WebElement MTTxnType(String txntype) {
		String type = getVisibileElementXPath(d, "Transaction", null, "MTTxnType");
		type = type.replaceAll("txnType", txntype);
		return d.findElement(By.xpath(type));

	}

	public WebElement MTCatMLCCategory(String MLCinput) {
		String mlc = getVisibileElementXPath(d, "Transaction", null, "MTCatMLCCategory");
		mlc = mlc.replaceAll("catName", MLCinput);
		return d.findElement(By.xpath(mlc));

	}

	public WebElement InvestmentTypeName_AMT(String invstType) {
		String mlc = getVisibileElementXPath(d, "Transaction", null, "InvestmentTypeName_AMT");
		mlc = mlc.replaceAll("InvstType", invstType);
		return d.findElement(By.xpath(mlc));

	}

	public WebElement listOfHoldingName_AMT(String holding) {
		String mlc = getVisibileElementXPath(d, "Transaction", null, "listOfHoldingName_AMT");
		mlc = mlc.replaceAll("HodingType", holding);
		return d.findElement(By.xpath(mlc));

	}

	public WebElement lotListName_AMT(String lot) {
		String lots = getVisibileElementXPath(d, "Transaction", null, "lotListName_AMT");
		lots = lots.replaceAll("LotType", lot);
		return d.findElement(By.xpath(lots));

	}

	public void createTransaction(String Amount, String Desc, int account, int sche, int MLC, int HCL,
			int transactionType) {

		amount().sendKeys(Amount);
		description().sendKeys(Desc);
		frequencyDropDown().click();
		waitForPageToLoad();
		click(FrequencyList().get(transactionType));
		accountdropdown().click();
		click(ListAccount(1).get(account));
		Schedule().sendKeys(targateDate1(sche));
		click(catgorie());
		System.out.println(catgoryList(MLC, HCL).getText());
		click(catgoryList(MLC, HCL));
		click(add());
	}

	public void createTransaction(String Amount, String Desc, String account, int sche, String MLC, String frequency) {

		WebDriverWait wait = new WebDriverWait(d, 20);
		amount().sendKeys(Amount);
		description().sendKeys(Desc);
		click(frequencyDropDown());
		WebElement fq = wait.until(ExpectedConditions.visibilityOf(MTFrquency(frequency)));
		click(fq);
		waitForPageToLoad();
		click(accountdropdown());
		waitForPageToLoad();
		WebElement acc = wait.until(ExpectedConditions.visibilityOf(MTAccount(account)));
		click(acc);
		Schedule().sendKeys(targateDate1(sche));
		click(catgorie());
		WebElement mlc = wait.until(ExpectedConditions.visibilityOf(MTCatMLCCategory(MLC)));
		click(mlc);
		click(add());
	}

	public void createTransactionwithdeposit(String Amount, String Desc, int account, int sche, int MLC, int HCL,
			int transactionType, int Frequency) {
		amount().sendKeys(Amount);
		description().sendKeys(Desc);
		click(TransactionType());
		// TransactionType().click();
		waitForPageToLoad();
		click(TtransactionList().get(transactionType));
		// TtransactionList().get(transactionType).click();
		click(frequencyDropDown());// frequencyDropDown().click();
		waitForPageToLoad();
		click(FrequencyList().get(Frequency));
		// FrequencyList().get(Frequency).click();
		waitForPageToLoad();
		click(accountdropdown());// accountdropdown().click();
		waitForPageToLoad();
		click(ListAccount(1).get(account));// ListAccount(1).get(account).click();
		Schedule().sendKeys(targateDate1(sche));
		click(catgorie());// catgorie().click();
		click(catgoryList(MLC, HCL));
		click(add());// add().click();
		waitForPageToLoad();
	}

	public void createTransactionWithAllDetails(String amt, String desc, int acct, int sch, int HLC, int MLC,
			String tags, String notes, String cqe) {
		amount().sendKeys(amt);
		description().sendKeys(desc);
		click(accountdropdown());
		waitForPageToLoad();
		if (Config.getInstance().getEnvironment().equalsIgnoreCase("BBT")) {
			click(ListAccountNew().get(1));
			waitForPageToLoad();
		} else {
			click(ListAccount(1).get(acct));
		}

		Schedule().sendKeys(targateDate1(sch));
		click(catgorie());
		waitForPageToLoad();
		if (Config.getInstance().getEnvironment().equalsIgnoreCase("BBT")) {
			click(catgoryList2().get(2));
			waitForPageToLoad();
		} else {
			click(catgoryList(HLC, MLC));
		}
		waitForPageToLoad();
		click(AddManulTransactionTag());
		AddManualTransactTag().sendKeys(tags);
		waitForPageToLoad();
		click(AddManualTransactTagCreate());
		click(showMoreDetails());
		waitForPageToLoad();
		note().sendKeys(notes);
		check().sendKeys(cqe);
		click(add());
		waitForPageToLoad();

	}

	public String EndateError(int frq, String startdate, String enddate) {
		click(frequencyDropDown());
		click(FrequencyList().get(frq));
		click(Schedule());
		Schedule().clear();
		Schedule().sendKeys(startdate);
		click(endDate());
		endDate().clear();
		endDate().sendKeys(enddate);
		endDate().sendKeys(Keys.TAB);
		return enddateerr().getText();
	}

	public String EndateErrorMobile(int frq, String startdate, String enddate) throws InterruptedException {
		click(frequencyDropDown());
		click(FrequencyList().get(frq));
		click(calendIcon());
		Schedule().clear();
		selectDate(getDateFormate(7),
				d.findElement(By.xpath("//div[@id='schedule-date-field-wrap']//div[@class='calendar popup active']")));
		click(endDate());
		endDate().clear();
		endDate().sendKeys(enddate);
		endDate().sendKeys(Keys.TAB);
		return enddateerr().getText();

	}

	public String getAtributeVAlue(String atributevalue) {
		JavascriptExecutor e = (JavascriptExecutor) d;

		return (String) e.executeScript(String.format("return $('#%s').val();", atributevalue));

	}

	public void createTransactionWithRecuralldetails(String Amount, String Desc, int account, int freq, int sche,
			int enddate, int MLC, int HCL, String NoteName) {
		click(amount());
		amount().sendKeys(Amount);
		click(description());
		description().sendKeys(Desc);
		click(accountdropdown());
		if (Config.getInstance().getEnvironment().toLowerCase().contains("bbt")) {
			click(accountForTrans().get(account));
		} else {
			click(ListAccount(1).get(account));
		}

		click(frequencyDropDown());
		click(FrequencyList().get(freq));
		click(Schedule());
		Schedule().sendKeys(targateDate1(sche));
		click(endDate());
		endDate().clear();
		endDate().sendKeys(targateDate1(enddate));
		click(catgorie());
		if (Config.getInstance().getEnvironment().toLowerCase().contains("bbt")) {
			click(catgoryList(MLC, HCL));
		} else {
			click(catgoryList(MLC, HCL));
		}
		click(showMoreDetails());
		waitForPageToLoad(1000);
		click(note());
		note().sendKeys(NoteName);
		click(add());
		waitUntilSpinnerWheelIsInvisible();
	}

	public void createTransactionWithRecuralldetails(String amount, String Desc, String account, String freq, int sche,
			int enddate, String MLC, String NoteName) {

		WebDriverWait wait = new WebDriverWait(d, 30);
		WebElement amt = wait.until(ExpectedConditions.visibilityOf(amount()));
		click(amt);
		amt.sendKeys(amount);
		click(description());
		description().sendKeys(Desc);
		click(accountdropdown());
		WebElement acc = wait.until(ExpectedConditions.visibilityOf(MTAccount(account)));
		click(acc);
		WebElement fqDrop = wait.until(ExpectedConditions.visibilityOf(frequencyDropDown()));
		click(fqDrop);
		WebElement fq = wait.until(ExpectedConditions.visibilityOf(MTFrquency(freq)));
		click(fq);
		click(Schedule());
		Schedule().sendKeys(targateDate1(sche));
		click(endDate());
		endDate().clear();
		endDate().sendKeys(targateDate1(enddate));
		click(catgorie());
		WebElement cat = wait.until(ExpectedConditions.visibilityOf(MTCatMLCCategory(MLC)));
		click(cat);
		click(showMoreDetails());
		waitForPageToLoad(1000);
		click(note());
		note().sendKeys(NoteName);
		click(add());
	}

	public List<WebElement> postedDateHeader() {
		return getWebElements(d, "postedDateHeader", "Transaction", null);
	}

	public boolean verifyallTransactionOrder() throws ParseException {

		ArrayList<Date> l2 = new ArrayList<Date>();
		for (int i = 0; i < postedDateHeader().size(); i++) {
			String newdate = postedDateHeader().get(i).getText().replace("Today,", "").trim();
			Date d1 = new SimpleDateFormat("MMMM dd, yyyy").parse(newdate);
			l2.add(d1);
		}

		boolean projectedtransaction = true;
		for (int i = 0; i < l2.size(); i++) {
			Calendar cal2 = Calendar.getInstance();
			cal2.setTime(l2.get(i));
			for (int j = i + 1; j < l2.size(); j++) {
				Calendar cal1 = Calendar.getInstance();
				cal1.setTime(l2.get(j));

				if (cal1.after(cal2)) {

					projectedtransaction = false;
					break;
				}

				if (cal1.before(cal2)) {
					System.out.println("Date1 is before Date2");
				}

				if (cal1.equals(cal2)) {
					projectedtransaction = false;
					break;
				}
			}
		}
		return projectedtransaction;
	}

	public void createTransactionWithRecurSFC(String Amount, String Desc, int container, int account, int freq,
			int sche, int enddate, int MLC, int HCL, String NoteName) {
		amount().sendKeys(Amount);
		description().sendKeys(Desc);
		accountdropdown().click();
		waitForPageToLoad();

		if (Config.getInstance().getEnvironment().toLowerCase().contains("bbt")) {
			click(accountForTrans().get(account));
		} else {
			click(ListAccount(container).get(account));

		}

		click(frequencyDropDown());
		click(FrequencyList().get(freq));
		Schedule().clear();
		Schedule().sendKeys(targateDate1(sche));
		endDate().clear();
		endDate().sendKeys(targateDate1(enddate));
		click(catgorie());
		if (Config.getInstance().getEnvironment().toLowerCase().contains("bbt")) {
			click(catgoryList(MLC, HCL));
		} else {
			click(catgoryList(MLC, HCL));
		}
		click(showMoreDetails());
		waitForPageToLoad(1000);
		note().sendKeys(NoteName);
		add().click();
	}

	public void EditTransactionWithRecurSFC(String Amount, String Desc, int container, int account, int freq, int sche,
			int enddate, int MLC, int HCL, String NoteName) {
		amount().clear();
		amount().sendKeys(Amount);
		description().clear();
		description().sendKeys(Desc);
		Schedule().clear();
		Schedule().sendKeys(targateDate1(sche));
		endDate().clear();
		endDate().sendKeys(targateDate1(enddate));
		click(catgorie());

		waitForPageToLoad();
		if (Config.getInstance().getEnvironment().toLowerCase().contains("bbt")) {

			click(catgoryList(MLC, HCL));
		} else {
			click(catgoryList(MLC, HCL));
		}

		waitForPageToLoad();
		click(SFCshowMoreDetails());
		waitForPageToLoad(1000);
		note().clear();
		note().sendKeys(NoteName);
		click(UpdateSFCTransaction());
		waitForPageToLoad(1000);
	}

	public Date getDateFormate(int days) {
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DAY_OF_MONTH, days);
		System.out.println(cal.getTime());
		return cal.getTime();
	}

	public void createTransactionWithDeffTransactionType(String Amount, String Desc, int transactionType, int account,
			int freq, int sche, int enddate, int MLC, int HCL, String NoteName) {
		click(amount());
		amount().sendKeys(Amount);
		click(description());
		description().sendKeys(Desc);
		click(accountdropdown());
		waitForPageToLoad();
		click(TransactionType());
		waitForPageToLoad(1000);
		click(TtransactionList().get(transactionType));
		click(ListAccount(1).get(account));
		waitForPageToLoad();
		click(frequencyDropDown());
		waitForPageToLoad();
		click(FrequencyList().get(freq));
		click(Schedule());
		Schedule().sendKeys(targateDate1(sche));
		waitForPageToLoad();
		click(endDate());
		endDate().clear();
		endDate().sendKeys(targateDate1(enddate));
		waitForPageToLoad();
		click(catgorie());
		waitForPageToLoad();
		click(newcatgoryList_AMT(MLC, HCL));
		waitForPageToLoad();
		click(showMoreDetails());
		waitForPageToLoad(1000);
		click(note());
		note().sendKeys(NoteName);
		waitForPageToLoad(1000);

		click(add());
	}

	public void createTransactionWithDeffTransactionType(String amount, String Desc, String transactionType,
			String account, String freq, int sche, int enddate, String MLC, String NoteName) {
		WebDriverWait wait = new WebDriverWait(d, 5);
		WebElement amt = wait.until(ExpectedConditions.visibilityOf(amount()));
		click(amt);
		amt.clear();
		amt.sendKeys(amount);
		WebElement desc = wait.until(ExpectedConditions.visibilityOf(description()));
		click(desc);
		desc.clear();
		desc.sendKeys(Desc);
		WebElement txntype = wait.until(ExpectedConditions.visibilityOf(TransactionType()));
		click(txntype);
		WebElement typevalue = wait.until(ExpectedConditions.visibilityOf(MTTxnType(transactionType)));
		click(typevalue);
		click(accountdropdown());
		WebElement acct = wait.until(ExpectedConditions.visibilityOf(MTAccount(account)));
		click(acct);
		click(frequencyDropDown());
		WebElement fq = wait.until(ExpectedConditions.visibilityOf(MTFrquency(freq)));
		click(fq);

		click(Schedule());
		Schedule().sendKeys(targateDate1(sche));
		click(endDate());
		endDate().clear();
		endDate().sendKeys(targateDate1(enddate));
		click(catgorie());
		WebElement mlc = wait.until(ExpectedConditions.visibilityOf(MTCatMLCCategory(MLC)));
		click(mlc);
		WebElement showmore = wait.until(ExpectedConditions.visibilityOf(showMoreDetails()));
		click(showmore);
		WebElement notefield = wait.until(ExpectedConditions.visibilityOf(note()));
		click(notefield);
		notefield.sendKeys(NoteName);
		click(add());
	}

	public void createOneTimeTransaction(String Amount, String Desc, int transactionType, int account, int freq,
			int sche, int MLC, int HCL, String NoteName, String check) {
		click(amount());
		amount().sendKeys(Amount);
		click(description());
		description().sendKeys(Desc);
		click(TransactionType());
		click(TtransactionList().get(transactionType));
		click(accountdropdown());
		click(ListAccount(1).get(account));
		click(frequencyDropDown());
		click(FrequencyList().get(freq));
		click(Schedule());
		Schedule().sendKeys(targateDate1(sche));
		click(catgorie());
		click(newcatgoryList_AMT(MLC, HCL));
		click(showMoreDetails());
		waitForPageToLoad(1000);
		click(note());
		note().sendKeys(NoteName);
		click(add());
	}

	public void createOneTimeTransaction(String Amount, String Desc, String transactionType, String account,
			String freq, int sche, String MLC, String NoteName, String check) {
		WebDriverWait wait = new WebDriverWait(d, 10);
		WebElement amt = wait.until(ExpectedConditions.visibilityOf(amount()));
		click(amt);
		amt.clear();
		amt.sendKeys(Amount);
		WebElement desc = wait.until(ExpectedConditions.visibilityOf(description()));
		click(desc);
		desc.clear();
		desc.sendKeys(Desc);
		WebElement txntype = wait.until(ExpectedConditions.visibilityOf(TransactionType()));
		click(txntype);
		WebElement typevalue = wait.until(ExpectedConditions.visibilityOf(MTTxnType(transactionType)));
		click(typevalue);
		waitFor(1);
		click(accountdropdown());
		openAccountDropdownInAddTransactionPopup();
		WebElement acct = wait.until(ExpectedConditions.visibilityOf(MTAccount(account)));
		click(acct);
		/*
		 * if(isaddtranscAccDrpdwnclose()) { click(addtranscAccDrpdwnclose());
		 * click(frequencyDropDown()); waitForPageToLoad(8000); WebElement
		 * fq=wait.until(ExpectedConditions.visibilityOf(MTFrquency(freq))); click(fq);
		 * } else {
		 */
		click(frequencyDropDown());
		WebElement fq = wait.until(ExpectedConditions.visibilityOf(MTFrquency(freq)));
		click(fq);
		// }
		/*
		 * if(istimeFilterCloseMobile()) { click(timeFilterCloseMobile());
		 * click(Schedule()); Schedule().sendKeys(targateDate1(sche)); } else {
		 */
		click(Schedule());
		Schedule().sendKeys(targateDate1(sche));
		// }
		click(catgorie());
		WebElement mlc = wait.until(ExpectedConditions.visibilityOf(MTCatMLCCategory(MLC)));
		click(mlc);
		WebElement showmore = wait.until(ExpectedConditions.visibilityOf(showMoreDetails()));
		click(showmore);
		WebElement notefield = wait.until(ExpectedConditions.visibilityOf(note()));
		waitFor(2);
		click(notefield);
		notefield.sendKeys(NoteName);
		click(add());
		waitUntilSpinnerWheelIsInvisible();
	}

	public void createTransactionWithRecuralldetailsInvestorAndAdvisor(String Amount, String Desc, int account,
			int freq, int sche, int enddate, int MLC, int HCL, String tag, String NoteName) {
		click(amount());
		amount().sendKeys(Amount);
		click(description());
		description().sendKeys(Desc);
		click(accountdropdown());
		if (Config.getInstance().getEnvironment().toLowerCase().contains("bbt")) {
			click(accountForTrans().get(account));
		} else {
			click(ListAccount(1).get(account));
		}
		waitForPageToLoad();
		click(frequencyDropDown());
		waitForPageToLoad();
		click(FrequencyList().get(freq));
		waitForPageToLoad();
		click(Schedule());
		Schedule().sendKeys(targateDate1(sche));
		click(endDate());
		endDate().clear();
		endDate().sendKeys(targateDate1(enddate));
		click(catgorie());
		if (Config.getInstance().getEnvironment().toLowerCase().contains("bbt")) {
			click(catgoryList(MLC, HCL));
		} else {
			click(catgoryList(MLC, HCL));
		}
		click(AddManulTransactionTag());
		if (isAddManualTransactTagMobile()) {
			AddManualTransactTagMobile().clear();
			AddManualTransactTagMobile().sendKeys(tag);
			click(AddManualTransactTagCreateMobile());
			MobiletagBack();
		} else {
			AddManualTransactTag().clear();
			AddManualTransactTag().sendKeys(tag);
			click(AddManualTransactTagCreate());
		}
		click(showMoreDetails());
		waitForPageToLoad(1000);
		click(note());
		note().sendKeys(NoteName);
		click(add());
	}

	public void createInvestmentTransactionAdviosrInvestor(String Amount, String desc, int transactionType,
			int Container, int account, int date, String symbol, String CUS, String price1, String quty, int holding,
			int lot1, String txntag, String txnNote) {
		click(amount());
		amount().sendKeys(Amount);
		description().sendKeys(desc);
		click(TransactionType());
		waitForPageToLoad(500);
		click(TtransactionList().get(transactionType));
		waitForPageToLoad();
		click(accountdropdown());
		waitForPageToLoad(5000);
		click(ListAccount(Container).get(account));
		waitForPageToLoad(5000);
		Schedule().sendKeys(targateDate1(date));
		symbolTicket().sendKeys(symbol);
		CUSIP().sendKeys(CUS);
		price().sendKeys(price1);
		quantity().sendKeys(quty);
		click(Holdingtype());
		click(listOfHolding().get(holding));
		waitForPageToLoad();
		click(lot());
		waitForPageToLoad(500);
		click(lotList().get(lot1));
		waitForPageToLoad(1000);
		click(AddManulTransactionTag());
		if (isAddManualTransactTagMobile()) {
			AddManualTransactTagMobile().clear();
			AddManualTransactTagMobile().sendKeys(txntag);
			click(AddManualTransactTagCreateMobile());
			MobiletagBack();
		} else {
			AddManualTransactTag().clear();
			AddManualTransactTag().sendKeys(txntag);
			click(AddManualTransactTagCreate());
		}
		waitForPageToLoad();
		click(showMoreDetails());
		waitForPageToLoad(1000);
		click(note());
		note().sendKeys(txnNote);
		waitForPageToLoad(1000);
		click(add());
		waitForPageToLoad(5000);

	}

	public List<WebElement> HLCcatgoryList_AMT() {
		return getWebElements(d, "HLCcatgoryList_AMT", "Transaction", null);
	}

	public void selectingCategory(int MLC, int HLC) {
		click(catgorie());
		click(catgoryList(MLC, HLC));
	}

	public WebElement Cat_input_text() {
		return getWebElement(d, "Cat_input_text", "Transaction", null);
	}

	public WebElement Cat_input() {
		return getWebElement(d, "Cat_input", "Transaction", null);
	}

	public WebElement categoryTextBox() {
		return getWebElement(d, "categoryTextBox", "Transaction", null);
	}

	public WebElement createRuleMsg() {
		return getWebElement(d, "createRuleMsg", "Transaction", null);
	}

	public WebElement createRuleBtn() {
		return getWebElement(d, "createRuleBtn", "Transaction", null);
	}

	// nov release start

	public WebElement tranMTCatSearchField() {
		return getWebElement(d, "tranMTCatSearchField", "Transaction", null);
	}

	public WebElement tranMTCatSearchTextCLose() {
		return getWebElement(d, "tranMTCatSearchTextCLose", "Transaction", null);
	}

	public List<WebElement> tranMTCatSearchedHLC() {
		return getWebElements(d, "tranMTCatSearchedHLC", "Transaction", null);
	}

	public List<WebElement> tranMTCatSearchedMLC() {
		return getWebElements(d, "tranMTCatSearchedMLC", "Transaction", null);
	}

	public List<WebElement> tranMTCatSearchedHLCHL() {
		return getWebElements(d, "tranMTCatSearchedHLCHL", "Transaction", null);
	}

	public List<WebElement> tranMTCatSearchedMLCHL() {
		return getWebElements(d, "tranMTCatSearchedMLCHL", "Transaction", null);
	}

	public WebElement tranMTCatNocatAvailabel() {
		return getWebElement(d, "tranMTCatNocatAvailabel", "Transaction", null);
	}

	public List<WebElement> tranMTCatSearchedSubCat() {
		return getWebElements(d, "tranMTCatSearchedSubCat", "Transaction", null);
	}

	public List<WebElement> tranMTCatSearchedSubCatHL() {
		return getWebElements(d, "tranMTCatSearchedSubCatHL", "Transaction", null);
	}

	public void manualTxnCategorySearch(String category) {
		WebDriverWait wait = new WebDriverWait(d, 20);
		WebElement element = wait.until(ExpectedConditions.visibilityOf(tranMTCatSearchField()));
		element.clear();
		element.sendKeys(category);
		waitFor(2);
	}

	public void tagClick() {
		click(AddManulTransactionTag());
	}

	public void clickTagInputField() {
		click(AddManualTransactTag());
	}

	public void enterTagFieldValue(String tag) {
		AddManualTransactTag().clear();
		AddManualTransactTag().sendKeys(tag);
	}

	public boolean tagMaxCharValidation() {
		boolean tagmaxValue = false;
		if (AddManualTransactTag().getAttribute("value").length() == 40) {
			tagmaxValue = true;
		}
		return tagmaxValue;
	}

	/*
	 * public boolean tagsize(int tagsize) { boolean expectedTagsize = false; if
	 * (unlimittag().size() == tagsize) { expectedTagsize = true;
	 * 
	 * } return expectedTagsize; }
	 */

	public boolean doesTagsSizeMatchWithGivenNumber(int expectedtagSize) {
		int actualTagSize = getElementCount(tags);
		logger.info("Expected Tag Size :: " + expectedtagSize + " and Actual Tag Size  :: " + actualTagSize);
		if (actualTagSize == expectedtagSize) {
			return true;
		}
		return false;
	}

	public void addUnlimitedTAg(String taglist) {
		String tags[] = taglist.split(",");
		for (String addTag : tags) {
			createtagnew(addTag);
		}
	}

	public void clickCategoryField() {
		click(catgorie());
	}

	public boolean allcategoriesValue() {
		String catKeyTrue = PropsUtil.getDataPropertyValue("CatKeyTrue");
		int catsKey1 = Integer.parseInt(catKeyTrue);

		String catKeyfalse = PropsUtil.getDataPropertyValue("CatKeyFalse");
		int catsKey2 = Integer.parseInt(catKeyfalse);
		boolean expected = false;
		clickCategoryField();

		if (getAllCatAdd().size() == catsKey1) {
			expected = true;
			//
		}

		else if (getAllCatAdd().size() == catsKey2) {
			expected = true;
		}
		return expected;
	}

	public void clickMTLink() {
		WebDriverWait wait = new WebDriverWait(d, 5);
		WebElement element = wait.until(ExpectedConditions.visibilityOf(addManualLink()));
		click(element);
		waitUntilSpinnerWheelIsInvisible();
	}

	public void clickMTCategory() {
		WebDriverWait wait = new WebDriverWait(d, 80);
		WebElement element = wait.until(ExpectedConditions.visibilityOf(catgorie()));
		click(element);
	}

	public void selectAccount_MT(String AccountName) {
		WebDriverWait wait = new WebDriverWait(d, 80);
		WebElement element = wait.until(ExpectedConditions.visibilityOf(accountdropdown()));
		click(element);
		WebElement acc = wait.until(ExpectedConditions.visibilityOf(MTAccount(AccountName)));
		click(acc);

	}

	public void clickClose_MT() {
		WebDriverWait wait = new WebDriverWait(d, 80);
		WebElement element = wait.until(ExpectedConditions.visibilityOf(close()));
		click(element);
		waitUntilSpinnerWheelIsInvisible();
	}

	public WebElement visibleWeElement(WebElement webelement) {
		WebDriverWait wait = new WebDriverWait(d, 5);
		WebElement element = wait.until(ExpectedConditions.visibilityOf(webelement));
		return element;
	}

	public void selectFrequency(String fqcy) {
		click(frequencyDropDown());
		click(visibleWeElement(MTFrquency(fqcy)));
	}

	public void selectTxnType(String type) {
		click(TransactionType());
		click(visibleWeElement(MTTxnType(type)));
	}

	public void selectInsvtType(String invstType) {
		click(InvestmentType());
		click(visibleWeElement(InvestmentTypeName_AMT(invstType)));

	}

	public void clickAddButton() {
		click(add());
	}

	public void enterValueToTextField(WebElement element, String input) {
		element.clear();
		element.sendKeys(input);
		element.sendKeys(Keys.TAB);
	}

	public List<WebElement> PostedAccs_trans() {

		return getWebElements(d, "PostedAccs_trans", "Transaction", null);
	}

	public WebElement AllAccType_tran() {
		return getWebElement(d, "AllAccType_tran", "Transaction", null);
	}

	public List<WebElement> ManualAccinTransFin() {

		return getWebElements(d, "ManualAccinTransFin", "Transaction", null);
	}

	public boolean istimeFilterCloseMobile() {
		return PageParser.isElementPresent("timeFilterClose", "Transaction", null);
	}

	public WebElement timeFilterCloseMobile() {
		return getVisibileWebElement(d, "timeFilterClose", "Transaction", null);
	}

	public boolean isaddtranscAccDrpdwnclose() {
		return PageParser.isElementPresent("addtranscAccDrpdwnclose", "Transaction", null);
	}

	public WebElement addtranscAccDrpdwnclose() {
		return getVisibileWebElement(d, "addtranscAccDrpdwnclose", "Transaction", null);
	}
	
	public void openAccountDropdownInAddTransactionPopup() {
		if(getAttribute(accountDropDownInAddManualTransactionPopup, "aria-expanded").equals("false")) {
			click(accountDropDownInAddManualTransactionPopup);
		}
	}

}
