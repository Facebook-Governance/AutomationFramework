/**
* Copyright (c) 2018 Yodlee Inc. All Rights Reserved.
*
* This software is the confidential and proprietary information of
* Yodlee, Inc. Use is subject to license terms.
*
* @author Renuka K
*/
package com.omni.pfm.pages.TransactionEnhancement1;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.omni.pfm.utility.SeleniumUtil;

public class Transaction_CreditCard_Loc {

	static Logger logger = LoggerFactory.getLogger(Transaction_CreditCard_Loc.class);
	public WebDriver d = null;
	static String pageName = "Transaction"; // Page Name
	static String frameName = null;

	public Transaction_CreditCard_Loc(WebDriver d) {
		this.d = d;
	}

	public List<WebElement> PendingStranctionList() {
		return SeleniumUtil.getWebElements(d, "pendingtr1", pageName, frameName);

	}

	public WebElement catgoryField() {
		return SeleniumUtil.getVisibileWebElement(d, "catfield", pageName, frameName);
	}

	public WebElement biller() {
		return SeleniumUtil.getVisibileWebElement(d, "bill", pageName, frameName);
	}

	public WebElement accAssocciateButton() {
		return SeleniumUtil.getVisibileWebElement(d, "accAssoc", pageName, frameName);
	}

	public WebElement accAssocMessage() {
		return SeleniumUtil.getVisibileWebElement(d, "accAssocMessage", pageName, frameName);
	}

	public WebElement poupHeader() {
		return SeleniumUtil.getVisibileWebElement(d, "pop", pageName, frameName);
	}

	public List<WebElement> poupHeaderList() {
		return SeleniumUtil.getWebElements(d, "pop", pageName, frameName);
	}

	public WebElement accociateAccBilletLink() {
		return SeleniumUtil.getVisibileWebElement(d, "accociateAccBilletLink", pageName, frameName);
	}

	public WebElement Closepopup() {
		return SeleniumUtil.getVisibileWebElement(d, "cl", pageName, frameName);
	}

	public List<WebElement> billerLink() {
		return SeleniumUtil.getWebElements(d, "billerLk", pageName, frameName);
	}

	public WebElement associateCardName(String cardName)

	{
		String xpath = SeleniumUtil.getVisibileElementXPath(d, pageName, frameName, "associateCardName");
		xpath = xpath.replaceAll("CreditCard", cardName);
		return d.findElement(By.xpath(xpath));
	}

	public WebElement associateUsualPayment() {
		return SeleniumUtil.getVisibileWebElement(d, "associateUsualPayment", pageName, frameName);
	}

	public WebElement associateOtherError() {
		return SeleniumUtil.getVisibileWebElement(d, "associateOtherError", pageName, frameName);
	}

	public WebElement associateexistingBill() {
		return SeleniumUtil.getVisibileWebElement(d, "associateexistingBill", pageName, frameName);
	}

	public WebElement LinkAccount() {
		return SeleniumUtil.getVisibileWebElement(d, "linkAcc", pageName, frameName);
	}

	public WebElement associateBackButton() {
		return SeleniumUtil.getVisibileWebElement(d, "associateBackButton", pageName, frameName);
	}

	public WebElement associateHowInfo() {
		return SeleniumUtil.getVisibileWebElement(d, "associateHowInfo", pageName, frameName);
	}

	public WebElement fromAccount() {
		return SeleniumUtil.getVisibileWebElement(d, "fromAcc", pageName, frameName);
	}

	public WebElement fromLabel() {
		return SeleniumUtil.getVisibileWebElement(d, "fromlb", pageName, frameName);
	}

	public WebElement FICard() {
		return SeleniumUtil.getVisibileWebElement(d, "fi", pageName, frameName);
	}

	public WebElement satament() {
		return SeleniumUtil.getVisibileWebElement(d, "Statement", pageName, frameName);
	}

	public WebElement currentBalance() {
		return SeleniumUtil.getWebElement(d, "crbal", pageName, frameName);
	}

	public WebElement minimunBalance() {
		return SeleniumUtil.getWebElement(d, "minBal", pageName, frameName);
	}

	public WebElement other() {
		return SeleniumUtil.getWebElement(d, "othr", pageName, frameName);
	}

	public WebElement firstradioButtonSelected() {
		return SeleniumUtil.getVisibileWebElement(d, "firtbt", pageName, frameName);
	}

	public WebElement otherField() {
		return SeleniumUtil.getVisibileWebElement(d, "otherfld", pageName, frameName);
	}

	public WebElement statmentSelected() {
		return SeleniumUtil.getVisibileWebElement(d, "satselect", pageName, frameName);
	}

	public WebElement CurrentSelected() {
		return SeleniumUtil.getWebElement(d, "curSelect", pageName, frameName);
	}

	public WebElement minselected() {
		return SeleniumUtil.getWebElement(d, "minSelect", pageName, frameName);
	}

	public WebElement otherSelected() {
		return SeleniumUtil.getVisibileWebElement(d, "othrSelect", pageName, frameName);
	}

	public WebElement FooterMessage() {
		return SeleniumUtil.getVisibileWebElement(d, "cardFootermsg", pageName, frameName);
	}

	public WebElement SecondPopUpBack() {
		return SeleniumUtil.getVisibileWebElement(d, "seconback", pageName, frameName);
	}

	public WebElement SecondPopUpclose() {
		return SeleniumUtil.getVisibileWebElement(d, "seconclose", pageName, frameName);
	}

	public WebElement cancel() {
		return SeleniumUtil.getVisibileWebElement(d, "cnl", pageName, frameName);
	}

	public WebElement save() {
		return SeleniumUtil.getVisibileWebElement(d, "sv", pageName, frameName);
	}

	public WebElement AssociateSucesssmesgae() {
		return SeleniumUtil.getVisibileWebElement(d, "AssociateSucesssmesgae", pageName, frameName);
	}

	public WebElement message() {
		return SeleniumUtil.getVisibileWebElement(d, "sucess", pageName, frameName);
	}

	public WebElement asscoaiteDetailsHeader() {
		return SeleniumUtil.getVisibileWebElement(d, "asscoaiteDetailsHeader", pageName, frameName);
	}

	public WebElement asscoaiteDetailsCardSite() {
		return SeleniumUtil.getWebElement(d, "asscoaiteDetailsCardSite", pageName, frameName);
	}

	public WebElement asscoaiteDetailsTraAccount() {
		return SeleniumUtil.getVisibileWebElement(d, "asscoaiteDetailsTraAccount", pageName, frameName);
	}

	public WebElement asscoaiteDetailsCardAccount() {
		return SeleniumUtil.getVisibileWebElement(d, "asscoaiteDetailsCardAccount", pageName, frameName);
	}

	public WebElement asscoaiteDetailsCardSchDate() {
		return SeleniumUtil.getVisibileWebElement(d, "asscoaiteDetailsCardSchDate", pageName, frameName);
	}

	public WebElement asscoaiteDetailsTraAccountList(String accountName) {
		String xpath = SeleniumUtil.getVisibileElementXPath(d, pageName, frameName, "asscoaiteDetailsTraAccountList");
		xpath = xpath.replaceAll("bankaccount", accountName);
		return d.findElement(By.xpath(xpath));
	}

	public List<WebElement> updatedamount() {
		return SeleniumUtil.getWebElements(d, "amount", pageName, frameName);
	}

	public List<WebElement> UpdatedcardName() {

		return SeleniumUtil.getWebElements(d, "updtname", pageName, frameName);

	}

	public String getAtributeVAlue(String atributevalue) {
		JavascriptExecutor e = (JavascriptExecutor) d;

		return (String) e.executeScript(String.format("return $('#%s').val();", atributevalue));

	}

	public List<WebElement> projectedTransaction() {
		return SeleniumUtil.getWebElements(d, "projectedtra", pageName, frameName);
	}

	public List<WebElement> transactionamount() {
		return SeleniumUtil.getWebElements(d, "tranamount", pageName, frameName);
	}

	public WebElement projectEditHowMsg() {
		return SeleniumUtil.getVisibileWebElement(d, "projeditHow", pageName, frameName);
	}

	public WebElement projectEditCard() {
		return SeleniumUtil.getWebElement(d, "projeditcard", pageName, frameName);
	}

	public WebElement projectEditCardFrom() {
		return SeleniumUtil.getVisibileWebElement(d, "projeditcardfrom", pageName, frameName);
	}

	public List<WebElement> proListAccount() {
		return SeleniumUtil.getWebElements(d, "proListacc", pageName, frameName);
	}

	public WebElement projectedStament() {
		return SeleniumUtil.getVisibileWebElement(d, "prostm", pageName, frameName);
	}

	public WebElement projectedCurrencecbalance() {
		return SeleniumUtil.getVisibileWebElement(d, "projectedCur", pageName, frameName);
	}

	public WebElement projectedMinimumBalance() {
		return SeleniumUtil.getWebElement(d, "projectedmin", pageName, frameName);
	}

	public WebElement projectedOther() {
		return SeleniumUtil.getVisibileWebElement(d, "projectedOthr", pageName, frameName);
	}

	public WebElement projectedFirstRadioSelected() {
		return SeleniumUtil.getVisibileWebElement(d, "projectedFirst", pageName, frameName);
	}

	public WebElement projectedFotterMsg() {
		return SeleniumUtil.getVisibileWebElement(d, "projectedFt", pageName, frameName);
	}

	public WebElement projectedotherField() {
		return SeleniumUtil.getVisibileWebElement(d, "projectedoth", pageName, frameName);
	}

	public WebElement asscoaiteDetailsUsePay() {
		return SeleniumUtil.getVisibileWebElement(d, "asscoaiteDetailsUsePay", pageName, frameName);
	}

	public WebElement projectedotherRadiselected() {
		return SeleniumUtil.getVisibileWebElement(d, "projother", pageName, frameName);
	}

	public WebElement projectedCurrentSelected() {
		return SeleniumUtil.getVisibileWebElement(d, "procurr", pageName, frameName);
	}

	public WebElement projectedMinSelected() {
		return SeleniumUtil.getVisibileWebElement(d, "promin", pageName, frameName);
	}

	public WebElement proStatement() {
		return SeleniumUtil.getVisibileWebElement(d, "proStatement", pageName, frameName);
	}

	public WebElement proOtherSelected() {
		return SeleniumUtil.getVisibileWebElement(d, "proOtherSelected", pageName, frameName);
	}

	public WebElement projectedcancel() {
		return SeleniumUtil.getVisibileWebElement(d, "procl", pageName, frameName);
	}

	public WebElement projectedSave() {
		return SeleniumUtil.getVisibileWebElement(d, "prosave", pageName, frameName);
	}

	public WebElement projectedMessage() {
		return SeleniumUtil.getVisibileWebElement(d, "projectmsg", pageName, frameName);
	}

	public WebElement AccountName() {
		return SeleniumUtil.getVisibileWebElement(d, "accName", pageName, frameName);
	}

	public WebElement linkAccountclose() {
		return SeleniumUtil.getVisibileWebElement(d, "linkaccClose", pageName, frameName);
	}

	public int getOnlyCurrentdate(String dateinput) throws ParseException {
		// String input = "11/09/2017";
		DateFormat inputFormatter = new SimpleDateFormat("MM/dd/yyyy");
		Date date = inputFormatter.parse(dateinput);
		// your date
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		int year = cal.get(Calendar.YEAR);
		int month = cal.get(Calendar.MONTH);
		int day = cal.get(Calendar.DAY_OF_MONTH);
		System.out.println(day);
		return day;
	}

	public void clickCard(String cardName) {
		SeleniumUtil.click(this.associateCardName(cardName));
	}
}
