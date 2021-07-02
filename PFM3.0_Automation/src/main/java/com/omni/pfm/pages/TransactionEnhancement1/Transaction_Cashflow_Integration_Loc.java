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
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.omni.pfm.utility.SeleniumUtil;

public class Transaction_Cashflow_Integration_Loc extends SeleniumUtil {
	static Logger logger = LoggerFactory.getLogger(Add_Manual_Transaction_Loc.class);
	public static WebDriver d = null;
	static WebElement we;
	String TransactionPage = "Transaction";

	public Transaction_Cashflow_Integration_Loc(WebDriver d) {
		this.d = d;

	}

	public WebElement continueCashFlow_TCI() {
		return getWebElement(d, "continueCashFlow_TCI", TransactionPage, null);
	}

	public WebElement analisys_TCI() {
		return getWebElement(d, "analisys_TCI", TransactionPage, null);
	}

	public WebElement getcshaflowAmoount(int column, int row) {
		String abC = getVisibileElementXPath(d, TransactionPage, null, "getcshaflowAmoount");

		abC = abC.replaceAll("column", Integer.toString(column));
		abC = abC.replaceAll("row", Integer.toString(row));

		return d.findElement(By.xpath(abC));

	}

	public WebElement getcshaflowAmoountLink(int column, int row) {

		String abC = getVisibileElementXPath(d, TransactionPage, null, "getcshaflowAmoountLink");

		abC = abC.replaceAll("column", Integer.toString(column));
		abC = abC.replaceAll("row", Integer.toString(row));

		return d.findElement(By.xpath(abC));
	}

	public List<WebElement> dateList_TCI() {
		return getWebElements(d, "dateList_TCI", TransactionPage, null);
	}

	public List<WebElement> listamt_TCI() {
		return getWebElements(d, "listamt_TCI", TransactionPage, null);
	}

	public WebElement deposit_TCI() {
		return getWebElement(d, "deposit_TCI", TransactionPage, null);
	}

	public WebElement backTocashFlow_TCI() {
		return getWebElement(d, "backTocashFlow_TCI", TransactionPage, null);
	}

	public WebElement withDrow_TCI() {
		return getWebElement(d, "withDrow_TCI", TransactionPage, null);
	}

	public WebElement all_TCI() {
		return getWebElement(d, "all_TCI", TransactionPage, null);
	}

	public List<WebElement> depositTypeamount_TCI() {
		return getWebElements(d, "depositTypeamount_TCI", TransactionPage, null);
	}

	private final String withdrowlTypeamout = "//div[@class='transaction-row-content-wrap']/div[1]/div[3]/span[@class='y-font-black last-item']";
	@FindBy(how = How.XPATH, using = withdrowlTypeamout)
	public List<WebElement> withdrawlAmount;

	public List<WebElement> withdrawlAmount_TCI() {
		return getWebElements(d, "withdrawlAmount_TCI", TransactionPage, null);
	}

	public String getmothsMMM()

	{
		Calendar cal = Calendar.getInstance();
		System.out.println(new SimpleDateFormat("MMM").format(cal.getTime()));
		return new SimpleDateFormat("MMM").format(cal.getTime());
	}

	public String getmothsMMMM() {
		Calendar cal = Calendar.getInstance();
		System.out.println(new SimpleDateFormat("MMMM").format(cal.getTime()));
		return new SimpleDateFormat("MMMM").format(cal.getTime());
	}

	public void cashFlowFTUE() {
		WebDriverWait wait = new WebDriverWait(d, 8);
		WebElement contbutton = wait.until(ExpectedConditions.visibilityOf(continueCashFlow_TCI()));
		click(contbutton);
		WebElement analysis = wait.until(ExpectedConditions.visibilityOf(analisys_TCI()));
		click(analysis);
		waitUntilSpinnerWheelIsInvisible();
	}

	public void clickCashFlowAmount(int coloumn, int row) {
		WebDriverWait wait = new WebDriverWait(d, 8);
		WebElement amount = wait.until(ExpectedConditions.visibilityOf(getcshaflowAmoount(coloumn, row)));
		click(amount);
		waitUntilSpinnerWheelIsInvisible();
	}

	public void clickBackTocashFlow() {
		WebDriverWait wait = new WebDriverWait(d, 8);
		WebElement backToCF = wait.until(ExpectedConditions.visibilityOf(backTocashFlow_TCI()));
		click(backToCF);
		waitUntilSpinnerWheelIsInvisible();
	}

}
