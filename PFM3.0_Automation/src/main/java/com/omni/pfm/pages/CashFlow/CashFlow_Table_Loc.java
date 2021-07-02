/*******************************************************************************
 * Copyright (c) 2018 Yodlee Inc. All Rights Reserved.
 * 
 * This software is the confidential and proprietary information of
 * Yodlee, Inc. Use is subject to license terms.
 * 
 * @author
 ******************************************************************************/
package com.omni.pfm.pages.CashFlow;

/**
 * Copyright (c) 2018 Yodlee Inc. All Rights Reserved.
 *
 * This software is the confidential and proprietary information of Yodlee, Inc.
 * Use is subject to license terms.
 *
 * @author Shivaprasad
 */
import java.text.DateFormatSymbols;
import java.util.Calendar;
import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.omni.pfm.utility.SeleniumUtil;

public class CashFlow_Table_Loc {
	static Logger logger = LoggerFactory.getLogger(CashFlow_Table_Loc.class);
	public WebDriver d = null;
	private static String pageName = "CashFlow"; // Page Name
	private static String frameName = null;

	public CashFlow_Table_Loc(WebDriver d) {
		this.d = d;
	}

	public WebElement netTransfersList() {
		return SeleniumUtil.getWebElement(d, "netTransfersList", pageName, frameName);
	}

	public WebElement NetCashFLowAmt() {
		return SeleniumUtil.getVisibileWebElement(d, "netCashFLowAmt", pageName, frameName);
	}

	public WebElement CurrentMonth() {
		return SeleniumUtil.getVisibileWebElement(d, "currentMonth", pageName, frameName);
	}

	public WebElement DateDropDown() {
		return SeleniumUtil.getVisibileWebElement(d, "dateDropDown", pageName, frameName);
	}

	public WebElement HighChart() {
		return SeleniumUtil.getVisibileWebElement(d, "highChart", pageName, frameName);
	}

	public List<WebElement> TimeFilter() {
		return SeleniumUtil.getWebElements(d, "ellipseName", pageName, frameName);
	}

	public List<WebElement> getCategoryDropDownOption1() {
		return SeleniumUtil.getWebElements(d, "tabCont", pageName, frameName);
	}

	public WebElement getCategoryDropDownOption(int i) {
		return d.findElement(By.xpath(".//*[@id='cf-table-container']/div/table/tbody/tr[" + i + "]/td[5]"));
	}

	public WebElement CashInflow_Int() {
		return SeleniumUtil.getWebElement(d, "CashInflow_Int", pageName, frameName);
	}

	public List<WebElement> getDateCOlumn() {
		// return d.findElements(By.xpath("//td[@class='dateColumn']"));
		return SeleniumUtil.getWebElements(d, "dateCol", pageName, frameName);
	}

	public WebElement getCategory(int n) {
		return d.findElement(By.xpath(".//*[@id='cf-table-container']/div/table/tbody/tr[3]/td[" + n + "]/a"));
	}

	public WebElement getSummaryCol(int n, int i) {
		return d.findElement(By.xpath(".//*[@id='cf-summary-content']/div[2]/div[" + n + "]/div[" + i + "]"));
	}

	public int month1;
	public static int currentyear;
	public String c;

	public String getMonthName(int monthNumber) {
		Calendar now = Calendar.getInstance();
		String a = new DateFormatSymbols().getMonths()[monthNumber];
		String b = a.substring(0, 3).toUpperCase();
		currentyear = now.get(Calendar.YEAR);
		c = b + " " + currentyear;
		System.out.println(c);
		return c;
	}

	// NISHA
	public WebElement tableCashFlow1() {
		return SeleniumUtil.getWebElement(d, "tableCashFlow", pageName, frameName);
	}

	public WebElement cashFlowDetail1() {
		return SeleniumUtil.getVisibileWebElement(d, "cashFlowDetail", pageName, frameName);
	}

	public WebElement hideDetails1() {
		return SeleniumUtil.getVisibileWebElement(d, "hideDetails", pageName, frameName);
	}

	public WebElement hidden1() {
		return SeleniumUtil.getWebElement(d, "hidden", pageName, frameName);
	}

	public WebElement summarySection1() {
		return SeleniumUtil.getVisibileWebElement(d, "summarySection", pageName, frameName);
	}

	public WebElement avergeMonthyNetCashFlow1() {
		return SeleniumUtil.getVisibileWebElement(d, "avergeMonthyNetCashFlow", pageName, frameName);
	}

	public WebElement totalNetFlow1() {
		return SeleniumUtil.getVisibileWebElement(d, "totalNetFlow", pageName, frameName);
	}

	public WebElement totalCashFlow1() {
		return SeleniumUtil.getVisibileWebElement(d, "totalCashFlow", pageName, frameName);
	}

	public WebElement cashFilter1() {
		return SeleniumUtil.getWebElement(d, "cashFilter", pageName, frameName);
	}

	public WebElement monthStart1() {
		return SeleniumUtil.getVisibileWebElement(d, "monthStart", pageName, frameName);
	}

	public WebElement monthEnd1() {
		return SeleniumUtil.getVisibileWebElement(d, "monthEnd", pageName, frameName);
	}

	public WebElement cashFlowDetails(int i) {
		return d.findElement(By.xpath("//*[@id='cf-table-container']/div/table/tbody/tr/td[" + i + "]"));
	}

	public int mon;
	public int mon1;
	public static int year1, year;

	public void get_LastMonth1() {
		Calendar now = Calendar.getInstance();
		System.out.println("Current Month : " + (now.get(Calendar.MONTH) + 1) + "-" + now.get(Calendar.YEAR));
		mon = now.get(Calendar.MONTH);
		// substract months from current date using Calendar.add method
		now = Calendar.getInstance();
		now.add(Calendar.MONTH, -5);
		System.out.println("date before 9 months : " + (now.get(Calendar.MONTH) + 1) + "-" + now.get(Calendar.YEAR));
		mon1 = now.get(Calendar.MONTH);
		year1 = now.get(Calendar.YEAR);
	}

	public WebElement date() {
		return d.findElement(By.xpath(".//*[@id='cf-summary-content']/div[2]//*[@id='chartHeader']/div/span[1]"));
	}

	public List<WebElement> getTableHeader() {
		return SeleniumUtil.getWebElements(d, "thaeader_CFT", pageName, frameName);
	}
}