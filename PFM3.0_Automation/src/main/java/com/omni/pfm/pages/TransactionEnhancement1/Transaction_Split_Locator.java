/**
* Copyright (c) 2018 Yodlee Inc. All Rights Reserved.
*
* This software is the confidential and proprietary information of
* Yodlee, Inc. Use is subject to license terms.
*
* @author Renuka K
*/
package com.omni.pfm.pages.TransactionEnhancement1;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.omni.pfm.utility.SeleniumUtil;
/*
import OmniAuto.pages.Accounts.Page;
import OmniAuto.util.Util;*/

public class Transaction_Split_Locator {
	static Logger logger = LoggerFactory.getLogger(Transaction_Split_Locator.class);
	public WebDriver d = null;
	static String pageName = "Transaction"; // Page Name
	static String frameName = null;

	public Transaction_Split_Locator(WebDriver d) {
		this.d = d;
	}

	public WebElement SplitIcon() {
		return SeleniumUtil.getWebElement(d, "splitTran", pageName, frameName);
	}

	public List<WebElement> SplitIconList() {
		return SeleniumUtil.getWebElements(d, "SplitIconList", pageName, frameName);
	}

	public WebElement SplitLink() {
		return SeleniumUtil.getWebElement(d, "splitLink", pageName, frameName);
	}

	public List<WebElement> splitedtransaction() {
		return SeleniumUtil.getWebElements(d, "splited", pageName, frameName);
	}

	public WebElement popupHeader() {
		return SeleniumUtil.getWebElement(d, "header_tst", pageName, frameName);
	}

	public WebElement Mobileheader_tst() {
		return SeleniumUtil.getWebElement(d, "Mobileheader_tst", pageName, frameName);
	}

	public WebElement OrginalRow() {
		return SeleniumUtil.getVisibileWebElement(d, "org", pageName, frameName);
	}

	public WebElement splitedrow() {
		return SeleniumUtil.getVisibileWebElement(d, "splitrw", pageName, frameName);
	}

	public WebElement splitedrowdate() {
		return SeleniumUtil.getVisibileWebElement(d, "rowSplited", pageName, frameName);
	}

	public WebElement splitedrowDesc() {
		return SeleniumUtil.getVisibileWebElement(d, "splitedrowDesc", pageName, frameName);
	}

	public WebElement splitedrowAmount() {
		return SeleniumUtil.getVisibileWebElement(d, "splitedrowAmount", pageName, frameName);
	}

	public WebElement splitedrowdate1() {
		return SeleniumUtil.getVisibileWebElement(d, "splitedrowdate1", pageName, frameName);
	}

	public WebElement splitedrowDesc1() {
		return SeleniumUtil.getVisibileWebElement(d, "splitedrowDesc1", pageName, frameName);
	}

	public WebElement splitedrowAmount1() {
		return SeleniumUtil.getVisibileWebElement(d, "splitedrowAmount1", pageName, frameName);
	}

	public WebElement datecolum() {
		return SeleniumUtil.getVisibileWebElement(d, "date", pageName, frameName);
	}

	public WebElement descriptioncolumn() {
		return SeleniumUtil.getVisibileWebElement(d, "desc", pageName, frameName);
	}

	public WebElement catcolumn() {
		return SeleniumUtil.getVisibileWebElement(d, "cat_tst", pageName, frameName);
	}

	public WebElement amountcolumn() {
		return SeleniumUtil.getVisibileWebElement(d, "amount_tst", pageName, frameName);
	}

	public WebElement descrotionfield() {

		return SeleniumUtil.getWebElement(d, "descfld", pageName, frameName);
	}

	public WebElement catField() {
		return SeleniumUtil.getVisibileWebElement(d, "catfield_tst", pageName, frameName);
	}

	public List<WebElement> splitTxn_HLC_Search() {
		return SeleniumUtil.getWebElements(d, "splitTxn_HLC_Search", pageName, frameName);
	}

	public List<WebElement> splitTxn_MLC_Search() {
		return SeleniumUtil.getWebElements(d, "splitTxn_MLC_Search", pageName, frameName);
	}

	public List<WebElement> listCat(int HLC, int MLC) {
		String s = SeleniumUtil.getVisibileElementXPath(d, pageName, frameName, "splitTr_tst");
		s = s.replaceAll("HLC", Integer.toString(HLC));
		s = s.replaceAll("MLC", Integer.toString(MLC));

		return d.findElements(By.xpath(s));
	}

	public List<WebElement> TicksplitTr_tst(int HLC, int MLC) {
		String s = SeleniumUtil.getVisibileElementXPath(d, pageName, frameName, "TicksplitTr_tst");
		s = s.replaceAll("HLC", Integer.toString(HLC));
		s = s.replaceAll("MLC", Integer.toString(MLC));

		return d.findElements(By.xpath(s));
	}

	public WebElement SplieddateField() {
		return SeleniumUtil.getVisibileWebElement(d, "datefld", pageName, frameName);
	}

	public WebElement Spliteddescfield() {
		return SeleniumUtil.getVisibileWebElement(d, "sptdescfld", pageName, frameName);
	}

	public WebElement splitedcatField() {
		return SeleniumUtil.getVisibileWebElement(d, "sptcatfield", pageName, frameName);
	}

	public List<WebElement> listCatSplited(int HLC, int MLC) {

		String s = SeleniumUtil.getVisibileElementXPath(d, pageName, frameName, "splitTr1_tst");
		s = s.replaceAll("HLC", Integer.toString(HLC));
		s = s.replaceAll("MLC", Integer.toString(MLC));

		return d.findElements(By.xpath(s));

	}

	public WebElement OriginalAmountLabel() {
		return SeleniumUtil.getWebElement(d, "orginalamtlbl_tst", pageName, frameName);
	}

	public WebElement OriginalAmount() {
		return SeleniumUtil.getWebElement(d, "originalamt_tst", pageName, frameName);
	}

	public WebElement splitedTranDateLabel() {
		return SeleniumUtil.getVisibileWebElement(d, "spittradatelabl", pageName, frameName);
	}

	public WebElement splitedTrandescLabel() {
		return SeleniumUtil.getVisibileWebElement(d, "spittradesclabl", pageName, frameName);
	}

	public WebElement splitedTrancatLabel() {
		return SeleniumUtil.getVisibileWebElement(d, "spittracatlabl", pageName, frameName);
	}

	public WebElement splitedTranAmountLabel() {
		return SeleniumUtil.getVisibileWebElement(d, "spittraamtlabl", pageName, frameName);
	}

	public WebElement SplitAmoun1() {
		return SeleniumUtil.getVisibileWebElement(d, "Split1", pageName, frameName);
	}

	public WebElement Splitdate() {
		return SeleniumUtil.getVisibileWebElement(d, "SplitDate1", pageName, frameName);
	}

	public WebElement SplitAmount2() {
		return SeleniumUtil.getVisibileWebElement(d, "split2", pageName, frameName);
	}

	public List<WebElement> minussybmbol() {
		return SeleniumUtil.getWebElements(d, "minusSyml", pageName, frameName);
	}

	public WebElement addSplit() {
		return SeleniumUtil.getVisibileWebElement(d, "addspt", pageName, frameName);
	}

	public WebElement cancel() {
		return SeleniumUtil.getVisibileWebElement(d, "cl_tst", pageName, frameName);
	}

	public WebElement savechanges() {
		return SeleniumUtil.getVisibileWebElement(d, "save_tst", pageName, frameName);
	}

	public WebElement showmoretransaction() {
		return SeleniumUtil.getVisibileWebElement(d, "showmoretransaction", pageName, frameName);
	}

	public WebElement close() {
		return SeleniumUtil.getVisibileWebElement(d, "cls_tst", pageName, frameName);
	}

	public WebElement getllAmountFIeld(int numberRow) {

		String s = SeleniumUtil.getVisibileElementXPath(d, pageName, frameName, "splitTr2_tst");
		s = s.replaceAll("numberRow", Integer.toString(numberRow));
		return d.findElement(By.xpath(s));
	}

	public boolean addSplit(String amount, int numberRow) {
		boolean Expected = false;
		try {
			SeleniumUtil.click(addSplit());
			SeleniumUtil.waitForPageToLoad(5000);
			getllAmountFIeld(numberRow).clear();
			getllAmountFIeld(numberRow).sendKeys(amount);
			getllAmountFIeld(numberRow).sendKeys(Keys.TAB);
		} catch (Exception e) {
			Expected = true;
		}
		return Expected;
	}

	public List<WebElement> SplitList() {
		return SeleniumUtil.getWebElements(d, "listsplit", pageName, frameName);
	}

	public WebElement amounterr() {
		return SeleniumUtil.getVisibileWebElement(d, "amterr", pageName, frameName);
	}

	public List<WebElement> calendarList() {
		return SeleniumUtil.getWebElements(d, "calList", pageName, frameName);
	}

	public WebElement calendarPopUp() {
		return SeleniumUtil.getVisibileWebElement(d, "calpopup", pageName, frameName);
	}

	public WebElement DateErr() {
		return SeleniumUtil.getVisibileWebElement(d, "dateerr", pageName, frameName);
	}

	public WebElement getShowMore() {
		return SeleniumUtil.getWebElement(d, "ShowMore_ATD", "Transaction", null);
	}

	public String getAtributeVAlue(String atributevalue) {
		JavascriptExecutor e = (JavascriptExecutor) d;
		System.out.println((String) e.executeScript(String.format("return $('#%s').val();", atributevalue)));
		return (String) e.executeScript(String.format("return $('#%s').val();", atributevalue));

	}

	public void splitTxn() {
		SeleniumUtil.click(this.getShowMore());
		SeleniumUtil.waitForPageToLoad();
		SeleniumUtil.waitFor(2);
		SeleniumUtil.click(this.SplitIcon());
		SeleniumUtil.waitForPageToLoad();
		SeleniumUtil.waitFor(2);
		SeleniumUtil.click(this.savechanges());
		SeleniumUtil.waitForPageToLoad();
	}
}
