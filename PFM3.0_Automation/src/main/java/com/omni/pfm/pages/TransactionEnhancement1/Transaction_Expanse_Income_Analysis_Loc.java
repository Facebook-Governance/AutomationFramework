/**
* Copyright (c) 2018 Yodlee Inc. All Rights Reserved.
*
* This software is the confidential and proprietary information of
* Yodlee, Inc. Use is subject to license terms.
*
* @author Renuka K
*/
package com.omni.pfm.pages.TransactionEnhancement1;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.omni.pfm.PageProcessor.PageParser;
import com.omni.pfm.utility.SeleniumUtil;

public class Transaction_Expanse_Income_Analysis_Loc {
	static Logger logger = LoggerFactory.getLogger(Add_Manual_Transaction_Loc.class);
	public static WebDriver d = null;
	static WebElement we;
	String TransactionPage = "Transaction";

	public Transaction_Expanse_Income_Analysis_Loc(WebDriver d) {
		this.d = d;
	}

	public List<WebElement> pendingtr() {
		return SeleniumUtil.getWebElements(d, "pendingtr", TransactionPage, null);
	}

	public WebElement continuebt() {
		return SeleniumUtil.getWebElement(d, "continuebt", TransactionPage, null);
	}

	public WebElement goToAnalysis() {
		return SeleniumUtil.getWebElement(d, "goToAnalysis", TransactionPage, null);
	}

	public WebElement exp() {
		return SeleniumUtil.getWebElement(d, "expAnalysisDropDown", TransactionPage, null);
	}

	public WebElement incomeAys() {
		return SeleniumUtil.getWebElement(d, "newincomeAys", TransactionPage, null);
	}

	public List<WebElement> listmoth() {
		return SeleniumUtil.getWebElements(d, "listmoth", TransactionPage, null);
	}

	public WebElement cat() {
		return SeleniumUtil.getWebElement(d, "cat", TransactionPage, null);
	}

	public List<WebElement> excatAmount() {
		return SeleniumUtil.getWebElements(d, "excatAmount", TransactionPage, null);
	}

	public List<WebElement> excatList() {
		return SeleniumUtil.getWebElements(d, "excatList", TransactionPage, null);
	}

	public List<WebElement> totaltranamount1() {
		return SeleniumUtil.getWebElements(d, "totaltranamount1", "Expense", null);
	}

	public List<WebElement> totaltranamount() {
		return SeleniumUtil.getWebElements(d, "totaltranamount", "Expense", null);
	}

	public boolean istotaltranamount() {
		return PageParser.isElementPresent("totaltranamount1", "Expense", null);
	}

	public List<WebElement> tranamount1() {
		return SeleniumUtil.getWebElements(d, "tranamount1", TransactionPage, null);
	}

	public List<WebElement> tranDescription() {
		return SeleniumUtil.getWebElements(d, "tranDescription", TransactionPage, null);
	}

	public List<WebElement> expanseAttachment() {
		return SeleniumUtil.getWebElements(d, "expanseAttachment", TransactionPage, null);
	}

	public List<WebElement> expanseSplitIcon() {
		return SeleniumUtil.getWebElements(d, "expanseSplitIcon", TransactionPage, null);
	}

	public WebElement catgoryList(int MLC, int HLC) {
		String abC = SeleniumUtil.getVisibileElementXPath(d, "Transaction", null, "incomCat");
		abC = abC.replaceAll("MLC", Integer.toString(MLC));
		System.out.println(abC);
		abC = abC.replaceAll("HLC", Integer.toString(HLC));
		System.out.println(abC);
		return d.findElement(By.xpath(abC));
	}

	public WebElement incomeCatDropDown() {
		return SeleniumUtil.getWebElement(d, "incomeCatDropDown", TransactionPage, null);
	}

	public WebElement expanseSplit() {
		return SeleniumUtil.getWebElement(d, "expanseSplit", TransactionPage, null);
	}

	public WebElement incomeShowMore() {
		return SeleniumUtil.getWebElement(d, "incomeShowMore", TransactionPage, null);
	}

	public WebElement incomeNote() {
		return SeleniumUtil.getWebElement(d, "incomeNote", TransactionPage, null);
	}

	public WebElement incomeTag() {
		return SeleniumUtil.getWebElement(d, "incomeTag", TransactionPage, null);
	}

	public WebElement incomeTagPluse() {
		return SeleniumUtil.getWebElement(d, "incomeTagPluse", TransactionPage, null);
	}

	public WebElement addtra() {
		return SeleniumUtil.getWebElement(d, "addtra", TransactionPage, null);
	}

	public WebElement incomeTranSave() {
		return SeleniumUtil.getWebElement(d, "incomeTranSave", TransactionPage, null);
	}

	public WebElement incomdesc() {
		return SeleniumUtil.getWebElement(d, "incomdesc", TransactionPage, null);
	}

	public List<WebElement> incometran() {
		return SeleniumUtil.getWebElements(d, "incometran", TransactionPage, null);
	}

	public List<WebElement> getAllIncomeAccount() {
		return SeleniumUtil.getWebElements(d, "getAllIncomeAccount", TransactionPage, null);
	}

	public List<WebElement> getAllIncomeCat() {
		return SeleniumUtil.getWebElements(d, "getAllIncomeCat", TransactionPage, null);
	}

	public List<WebElement> getAllIncomedescription() {
		return SeleniumUtil.getWebElements(d, "getAllIncomedescription", TransactionPage, null);
	}

	public List<WebElement> getAllIncomeAmount() {
		return SeleniumUtil.getWebElements(d, "getAllIncomeAmount", TransactionPage, null);
	}

	public String DateInMMMMFormate(int date) {
		SimpleDateFormat formatter = new SimpleDateFormat("MMMM dd, yyyy ");
		Calendar c = Calendar.getInstance();
		c.add(Calendar.DATE, date);
		System.out.println(formatter.format(c.getTime()));
		return formatter.format(c.getTime());
	}

	public void clickThisMonthTransaction() {
		WebDriverWait wait = new WebDriverWait(d, 10);
		WebElement element = wait.until(ExpectedConditions.visibilityOf(continuebt()));
		SeleniumUtil.click(element);
		WebElement element1 = wait.until(ExpectedConditions.visibilityOf(goToAnalysis()));
		SeleniumUtil.click(element1);
	}

	public void clickGoToAnalysis() {
		WebDriverWait wait = new WebDriverWait(d, 10);
		wait.until(ExpectedConditions.visibilityOf(goToAnalysis()));
		SeleniumUtil.click(goToAnalysis());
		// wait.until(ExpectedConditions.invisibilityOf(goToAnalysis()));
	}

	public void clickMonthRow(int monthRow) {
		WebDriverWait wait = new WebDriverWait(d, 50);
		wait.until(ExpectedConditions.visibilityOf(listmoth().get(monthRow)));
		SeleniumUtil.click(listmoth().get(monthRow));
		wait.until(ExpectedConditions.invisibilityOf(listmoth().get(monthRow)));
	}

	public void clickEATxn1(int monthRow, int categoryRow) {
		WebDriverWait wait = new WebDriverWait(d, 10);
		WebElement element = wait.until(ExpectedConditions.visibilityOf(listmoth().get(monthRow)));
		SeleniumUtil.click(element);
		WebElement element2 = wait.until(ExpectedConditions.visibilityOf(excatAmount().get(categoryRow)));
		SeleniumUtil.click(element2);
		SeleniumUtil.waitForPageToLoad();
	}

	public void clickEATxn(int monthRow, int categoryRow, int txnRow) {
		WebDriverWait wait = new WebDriverWait(d, 10);
		WebElement element = wait.until(ExpectedConditions.visibilityOf(listmoth().get(monthRow)));
		SeleniumUtil.click(element);
		WebElement element2 = wait.until(ExpectedConditions.visibilityOf(excatAmount().get(categoryRow)));
		SeleniumUtil.click(element2);
		WebElement element3 = wait.until(ExpectedConditions.visibilityOf(tranamount1().get(txnRow)));
		SeleniumUtil.click(element3);
	}

	public void clickIAMonthRow(int monthRow) {
		WebDriverWait wait = new WebDriverWait(d, 10);
		SeleniumUtil.click(exp());
		SeleniumUtil.scrollElementIntoView(d, incomeAys(), true);
		SeleniumUtil.click(incomeAys());
		wait.until(ExpectedConditions.visibilityOf(listmoth().get(monthRow)));
		SeleniumUtil.click(listmoth().get(monthRow));
	}

	public void clickIAMonthRow1(int monthRow) {
		WebDriverWait wait = new WebDriverWait(d, 10);
		SeleniumUtil.click(exp());
		SeleniumUtil.waitForPageToLoad(1000);
		SeleniumUtil.scrollElementIntoView(d, incomeAys(), true);
		SeleniumUtil.click(incomeAys());
		wait.until(ExpectedConditions.visibilityOf(listmoth().get(monthRow)));
		SeleniumUtil.click(listmoth().get(monthRow));
	}

	public void clickIATxn1(int monthRow, int categoryRow) {
		WebDriverWait wait = new WebDriverWait(d, 20);
		WebElement element = wait.until(ExpectedConditions.visibilityOf(exp()));
		SeleniumUtil.click(element);
		SeleniumUtil.waitForPageToLoad(1000);
		WebElement element1 = wait.until(ExpectedConditions.visibilityOf(incomeAys()));
		SeleniumUtil.scrollElementIntoView(d, element1, true);
		SeleniumUtil.click(element1);
		SeleniumUtil.waitForPageToLoad(3000);
		WebElement element2 = wait.until(ExpectedConditions.visibilityOf(listmoth().get(monthRow)));
		SeleniumUtil.click(element2);
		SeleniumUtil.waitForPageToLoad(500);
		WebElement element3 = wait.until(ExpectedConditions.visibilityOf(excatAmount().get(categoryRow)));
		SeleniumUtil.click(element3);
		SeleniumUtil.waitForPageToLoad(500);
	}

	public void clickIATxn(int monthRow, int categoryRow, int txnRow) {
		WebDriverWait wait = new WebDriverWait(d, 20);
		WebElement element = wait.until(ExpectedConditions.visibilityOf(exp()));
		SeleniumUtil.click(element);
		SeleniumUtil.waitForPageToLoad(1000);
		WebElement element1 = wait.until(ExpectedConditions.visibilityOf(incomeAys()));
		SeleniumUtil.scrollElementIntoView(d, element1, true);
		SeleniumUtil.click(element1);
		SeleniumUtil.waitForPageToLoad();
		SeleniumUtil.waitFor(5);
		WebElement element2 = wait.until(ExpectedConditions.visibilityOf(listmoth().get(monthRow)));
		SeleniumUtil.click(element2);
		SeleniumUtil.waitForPageToLoad();
		SeleniumUtil.waitFor(5);
		WebElement element3 = wait.until(ExpectedConditions.visibilityOf(excatAmount().get(categoryRow)));
		SeleniumUtil.click(element3);
		SeleniumUtil.waitForPageToLoad();
		SeleniumUtil.waitFor(5);
		WebElement element4 = wait.until(ExpectedConditions.visibilityOf(tranamount1().get(txnRow)));
		SeleniumUtil.click(element4);
	}

	public void clickIATxn(int monthRow, int categoryRow) {
		WebDriverWait wait = new WebDriverWait(d, 20);
		WebElement element = wait.until(ExpectedConditions.visibilityOf(exp()));
		SeleniumUtil.click(element);
		SeleniumUtil.waitForPageToLoad(1000);
		WebElement element1 = wait.until(ExpectedConditions.visibilityOf(incomeAys()));
		SeleniumUtil.scrollElementIntoView(d, element1, true);
		SeleniumUtil.click(element1);
		SeleniumUtil.waitForPageToLoad();
		SeleniumUtil.waitFor(3);
		WebElement element2 = wait.until(ExpectedConditions.visibilityOf(listmoth().get(monthRow)));
		SeleniumUtil.click(element2);
		SeleniumUtil.waitForPageToLoad();
		WebElement element3 = wait.until(ExpectedConditions.visibilityOf(excatAmount().get(categoryRow)));
		SeleniumUtil.click(element3);
		SeleniumUtil.waitForPageToLoad(500);
	}

	public WebElement TransactionTagLink() {
		return SeleniumUtil.getWebElement(d, "TransactionTagLink", "Transaction", null);
	}

	public WebElement TransactionTagField() {
		return SeleniumUtil.getWebElement(d, "TransactionTagField", "Transaction", null);
	}

	public WebElement TransactionTagCreate() {
		return SeleniumUtil.getWebElement(d, "TransactionTagCreate", "Transaction", null);
	}

	public List<WebElement> TransactionDefualtTagList() {
		return SeleniumUtil.getWebElements(d, "TransactionDefualtTagList", "Transaction", null);
	}

	public void editExpanseTransaction(String description, int HLC, int MLC, String note, String tag) {
		SeleniumUtil.waitForPageToLoad();
		incomdesc().clear();
		incomdesc().sendKeys(description);
		SeleniumUtil.click(TransactionTagLink());
		TransactionTagField().sendKeys(tag);
		SeleniumUtil.click(TransactionTagCreate());
		SeleniumUtil.waitForPageToLoad(1000);
		SeleniumUtil.click(incomeShowMore());
		SeleniumUtil.waitForPageToLoad(1000);
		incomeNote().clear();
		incomeNote().sendKeys(note);
		SeleniumUtil.click(incomeTranSave());
	}

	public WebElement EAIAHLCCatName(String catName) {
		String name = SeleniumUtil.getVisibileElementXPath(d, "Transaction", null, "EAIAHLCCatName");
		name = name.replaceAll("HLCCatName", catName);
		return d.findElement(By.xpath(name));
	}

	public WebElement EAIAHLCTxnAmount(String amount) {
		String amt = SeleniumUtil.getVisibileElementXPath(d, "Transaction", null, "EAIAHLCTxnAmount");
		amt = amt.replace("EAIAAmount", amount);
		return d.findElement(By.xpath(amt));
	}

	public void clickEATxn(int monthRow, String categoryRow, String txnRow) {
		WebDriverWait wait = new WebDriverWait(d, 10);
		WebElement element = wait.until(ExpectedConditions.visibilityOf(listmoth().get(monthRow)));
		SeleniumUtil.click(element);
		WebElement element2 = wait.until(ExpectedConditions.visibilityOf(EAIAHLCCatName(categoryRow)));
		SeleniumUtil.click(element2);
		WebElement element3 = wait.until(ExpectedConditions.visibilityOf(EAIAHLCTxnAmount(txnRow)));
		SeleniumUtil.click(element3);
	}

	public void clickEATxn(String categoryRow, String txnRow) {
		WebDriverWait wait = new WebDriverWait(d, 10);
		WebElement element2 = wait.until(ExpectedConditions.visibilityOf(EAIAHLCCatName(categoryRow)));
		SeleniumUtil.click(element2);
		WebElement element3 = wait.until(ExpectedConditions.visibilityOf(EAIAHLCTxnAmount(txnRow)));
		SeleniumUtil.click(element3);
	}
		
	public void clickOnHLCInEAOfGivenIndex(int index) {
		By hlc = SeleniumUtil.getByObject("Expense", null, "EIAHLCCatNameList");
		d.findElements(hlc).get(index).click();
		SeleniumUtil.waitForPageToLoad();
	}
}