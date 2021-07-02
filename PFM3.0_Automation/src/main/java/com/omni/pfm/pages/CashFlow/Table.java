/*******************************************************************************
 * Copyright (c) 2018 Yodlee Inc. All Rights Reserved.
 * 
 * This software is the confidential and proprietary information of
 * Yodlee, Inc. Use is subject to license terms.
 * 
 * @author
 ******************************************************************************/
package com.omni.pfm.pages.CashFlow;

/*
* 
* @autor -Pallavee modified by Ashwin
* 
* 
*/

import java.text.DateFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.omni.pfm.utility.SeleniumUtil;


/**
 * Copyright (c) 2018 Yodlee Inc. All Rights Reserved.
 *
 * This software is the confidential and proprietary information of Yodlee, Inc.
 * Use is subject to license terms.
 *
 * @author Shivaprasad
 */

public class Table {

	static Logger logger = LoggerFactory.getLogger(Table.class);
	public WebDriver d =null;
	static WebElement we;
	
	public Table(WebDriver d)
	{
		this.d=d;
		
	}

	/*private final String netTransferHeader = ".//*[@id='cf-table-container']/div/table/tbody";
	@FindBy(how = How.XPATH, using = netTransferHeader)*/
	public WebElement NetTransferHeader() {
		return SeleniumUtil.getVisibileWebElement(d, "NetTransferHeader_T", "CashFlow", null);
	}

	/*private final String col = ".transactionLink>span";
	@FindBy(how = How.CSS, using = col)*/
	public WebElement Col() {
		return SeleniumUtil.getVisibileWebElement(d, "Col_T", "CashFlow", null);
	}
	

	/*private final String header = ".//*[@id='cf-table-container']/div/table/thead";
	@FindBy(how = How.XPATH, using = header)*/
	public WebElement Header() {
		return SeleniumUtil.getVisibileWebElement(d, "Header_T", "CashFlow", null);
	}
	

	/*private final String avgMontlyNetCashFLow = ".//*[@id='cf-summary-content']/div[2]/div[1]/div[2]";
	@FindBy(how = How.XPATH, using = avgMontlyNetCashFLow)*/
	public WebElement AvgMontlyNetCashFLow() {
		return SeleniumUtil.getVisibileWebElement(d, "AvgMontlyNetCashFLow_T", "CashFlow", null);
	}
	

	/*private final String timeFIlter = "//a[contains(@class,'duration-dropdown')]";
	@FindBy(how = How.XPATH, using = timeFIlter)*/
	public WebElement TimeFIlter() {
		return SeleniumUtil.getVisibileWebElement(d, "TimeFIlter_T", "CashFlow", null);
	}
	
	

	/*private final String netCashFLowAmt = ".//*[@id='cf-summary-content']/div[2]/div[2]/div[2]";
	@FindBy(how = How.XPATH, using = netCashFLowAmt)*/
	public WebElement NetCashFLowAmt() {
		return SeleniumUtil.getVisibileWebElement(d, "NetCashFLowAmt_T", "CashFlow", null);
	}
	
	

	/*private final String currentMonth = ".//*[@id='cf-table-container']/div/table/tbody/tr[1]/td[1]";
	@FindBy(how = How.XPATH, using = currentMonth)*/
	public WebElement CurrentMonth() {
		return SeleniumUtil.getVisibileWebElement(d, "CurrentMonth_T", "CashFlow", null);
	}
	
	

	/*private final String dateDropDown = ".//*[@id='cf-table-container']/div/table/thead/tr/th[1]";
	@FindBy(how = How.XPATH, using = dateDropDown)*/
	public WebElement DateDropDown() {
		return SeleniumUtil.getVisibileWebElement(d, "DateDropDown_T", "CashFlow", null);
	}
	
	

	/*private final String highChart = ".//*[@class='highcharts-legend-item’][1]/text/tspan";
	@FindBy(how = How.XPATH, using = highChart)*/
	public WebElement HighChart() {
		return SeleniumUtil.getVisibileWebElement(d, "HighChart_T", "CashFlow", null);
	}
	
	

	/*private final String table = "cf-summary-content";
	@FindBy(how = How.ID, using = table)*/
	public WebElement Table1() {
		return SeleniumUtil.getVisibileWebElement(d, "Table_T", "CashFlow", null);
	}
	
	

	public WebElement TimeFilter(int n) {
		
		//return webDriver.findElements(By.cssSelector(".ellipsis.name.text.text-css-level1")).get(String.valueOf(n).trim());

		String abC = SeleniumUtil.getVisibileElementXPath (d, "CashFlow", null, "TimeFilter_T");
		
		return d.findElements(By.cssSelector(abC)).get(n);
		
		//return SeleniumUtil.getVisibileWebElement(d, "TimeFilter_T", "Transaction", null);
	}

	public List<WebElement> getCategoryDropDownOption1() {

		String abC = SeleniumUtil.getVisibileElementXPath (d, "CashFlow", null, "getCategoryDropDownOption1_T");
		
		return d.findElements(By.xpath(abC));
		
		//return webDriver.findElements(By.xpath(".//*[@id='cf-table-container']/div/table/tbody/tr/td[5]"));
	}

	public WebElement getCategoryDropDownOption(int i) {

		String abC = SeleniumUtil.getVisibileElementXPath (d, "CashFlow", null, "getCategoryDropDownOption_T");
		
		abC.replaceAll("var1", String.valueOf(i).trim());
		
		return d.findElement(By.xpath(abC));
		
		//return webDriver.findElement(By.xpath(".//*[@id='cf-table-container']/div/table/tbody/tr[" + i + "]/td[5]"));
	}

	public List<WebElement> getRows(WebElement table) {

		
		return table.findElements(By.tagName("tr"));

	}

	public List<WebElement> getHeaders(WebElement table) {

		return table.findElements(By.tagName("th"));

	}

	public WebElement getCategory(int n) {

		String abC = SeleniumUtil.getVisibileElementXPath (d, "CashFlow", null, "getCategory_T");
		
		abC.replaceAll("var1", String.valueOf(n).trim());
		
		return d.findElement(By.xpath(abC));
		
		//return webDriver.findElement(By.xpath(".//*[@id='cf-table-container']/div/table/tbody/tr[3]/td[" + n + "]/a"));

	}

	public WebElement getCategory1(int n) {

		String abC = SeleniumUtil.getVisibileElementXPath (d, "CashFlow", null, "getCategory1_T");
		
		abC.replaceAll("var1", String.valueOf(n).trim());
		
		return d.findElement(By.xpath(abC));
		
		//return webDriver.findElement(By.xpath(".//*[@id='cf-table-container']/div/table/tbody/tr[2]/td[" + n + "]/a"));

	}

	public WebElement getCategory2(int n) {

		String abC = SeleniumUtil.getVisibileElementXPath (d, "CashFlow", null, "getCategory2_T");
		
		abC.replaceAll("var1", String.valueOf(n).trim());
		
		return d.findElement(By.xpath(abC));
		
		//return webDriver.findElement(By.xpath(".//*[@id='cf-table-container']/div/table/tbody/tr[4]/td[" + n + "]/a"));

	}

	public List<WebElement> getColumns(WebElement row) {
		return row.findElements(By.tagName("td"));

	}

	public WebElement getNetCashFLow(int n) {
		
		String abC = SeleniumUtil.getVisibileElementXPath (d, "CashFlow", null, "getNetCashFLow_T");
		
		abC.replaceAll("var1", String.valueOf(n).trim());
		
		return d.findElement(By.xpath(abC));
		
		//return webDriver.findElement(By.xpath(".//*[@id='cf-table-container']/div/table/tbody/tr[" + n + "]/td[5]/span"));

	}

	public WebElement getCol(int n) {
		
		String abC = SeleniumUtil.getVisibileElementXPath (d, "CashFlow", null, "getCol_T");
		
		abC.replaceAll("var1", String.valueOf(n).trim());
		
		return d.findElement(By.xpath(abC));
		
		//return webDriver.findElement(By.xpath(".//*[@id='cf-table-container']/div/table/tbody/tr[" + n + "]/td[4]/a"));

	}

	public WebElement getAvgNetCashFLow(int n, int i) {

		String abC = SeleniumUtil.getVisibileElementXPath(d, "CashFlow", null, "getAvgNetCashFLow_T");
		
		abC.replaceAll("var1", String.valueOf(n).trim());
		abC.replaceAll("var2", String.valueOf(i).trim());
		
		return d.findElement(By.xpath(abC));
		
		//return webDriver.findElement(By.xpath(".//*[@id='cf-summary-content']/div[2]/div[" + n + "]/div[" + i + "]"));

	}

	public WebElement getSummaryCol(int n, int i) {

		String abC = SeleniumUtil.getVisibileElementXPath (d, "CashFlow", null, "getSummaryCol_T");
		
		abC.replaceAll("var1", String.valueOf(n).trim());
		abC.replaceAll("var2", String.valueOf(i).trim());
		
		return d.findElement(By.xpath(abC));
		
		//return webDriver.findElement(By.xpath(".//*[@id='cf-summary-content']/div[2]/div[" + n + "]/div[" + i + "]"));
	}

	public int month1;
	public static int currentyear;
	public String c;

	public void get_9MonthData() {

		Calendar now = Calendar.getInstance();
		System.out.println("Current Month : " + (now.get(Calendar.MONTH) + 1) + "-" + now.get(Calendar.YEAR));

		// substract months from current date using Calendar.add method
		now = Calendar.getInstance();
		now.add(Calendar.MONTH, -9);
		System.out.println("date before 9 months : " + (now.get(Calendar.MONTH) + 1) + "-" + now.get(Calendar.YEAR));
		month1 = now.get(Calendar.MONTH);
		currentyear = now.get(Calendar.YEAR);

	}

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

	/*private final String tableCashFlow = ".//*[@id='cf-summary-content']";
	@FindBy(how = How.XPATH, using = tableCashFlow)*/
	public WebElement tableCashFlow1() {
		return SeleniumUtil.getVisibileWebElement(d, "tableCashFlow1_T", "CashFlow", null);
	}
	
	

	/*private final String cashFlowDetail = ".//*[@id='show-details-link']/span[2]";
	@FindBy(how = How.XPATH, using = cashFlowDetail)*/
	public WebElement cashFlowDetail1() {
		return SeleniumUtil.getVisibileWebElement(d, "cashFlowDetail1_T", "CashFlow", null);
	}
	
	

	// private final String hideDetails=".//*[@id='show-details-link']/span";
	/*private final String hideDetails = ".//*[@id='show-details-link']";
	@FindBy(how = How.XPATH, using = hideDetails)*/
	public WebElement hideDetails1() {
		return SeleniumUtil.getVisibileWebElement(d, "hideDetails1_T", "CashFlow", null);
	}
	
	

	/*private final String hidden = ".//*[@id='cf-table-container']/div/table";
	@FindBy(how = How.XPATH, using = hidden)*/
	public WebElement hidden1() {
		return SeleniumUtil.getVisibileWebElement(d, "hidden1_T", "CashFlow", null);
	}
	
	

	/*private final String summarySection = ".//*[@id='cf-summary-content']/div[1]";
	@FindBy(how = How.XPATH, using = summarySection)*/
	public WebElement summarySection1() {
		return SeleniumUtil.getVisibileWebElement(d, "summarySection1_T", "CashFlow", null);
	}
	

	/*private final String avergeMonthyNetCashFlow = ".//*[@id='cf-summary-content']/div[2]/div[1]/div[1]";
	@FindBy(how = How.XPATH, using = avergeMonthyNetCashFlow)*/
	public WebElement avergeMonthyNetCashFlow1() {
		return SeleniumUtil.getVisibileWebElement(d, "avergeMonthyNetCashFlow1_T", "CashFlow", null);
	}
	
	

	/*private final String totalNetFlow = ".//*[@id='cf-summary-content']/div[2]/div[2]/div[1]";
	@FindBy(how = How.XPATH, using = totalNetFlow)*/
	public WebElement totalNetFlow1() {
		return SeleniumUtil.getVisibileWebElement(d, "totalNetFlow1_T", "CashFlow", null);
	}
	
	

	/*private final String totalCashFlow = ".//*[@id='cf-summary-content']/div[2]/div[2]/div[2]";
	@FindBy(how = How.XPATH, using = totalCashFlow)*/
	public WebElement totalCashFlow1() {
		return SeleniumUtil.getVisibileWebElement(d, "totalCashFlow1_T", "CashFlow", null);
	}
	
	

	/*private final String cashFilter = "(//div[@id='g-duration-filter']//span[2])[1]";
	@FindBy(how = How.XPATH, using = cashFilter)*/
	public WebElement cashFilter1() {
		return SeleniumUtil.getVisibileWebElement(d, "cashFilter1_T", "CashFlow", null);
	}
	
	

	/*private final String netCashFlow = ".//*[@id='highcharts-16']/svg/g[6]/g/g/g[1]/text/tspan";
	@FindBy(how = How.XPATH, using = netCashFlow)*/
	public WebElement netCashFlow1() {
		return SeleniumUtil.getVisibileWebElement(d, "netCashFlow1_T", "CashFlow", null);
	}
	
	

	/*private final String monthStart = ".//*[@id='chartHeader']/div/span[1]";
	@FindBy(how = How.XPATH, using = monthStart)*/
	public WebElement monthStart1() {
		return SeleniumUtil.getVisibileWebElement(d, "monthStart1_T", "CashFlow", null);
	}
	
	

	/*private final String monthEnd = ".//*[@id='chartHeader']/div/span[2]";
	@FindBy(how = How.XPATH, using = monthEnd)*/
	public WebElement monthEnd1() {
		return SeleniumUtil.getVisibileWebElement(d, "monthEnd1_T", "CashFlow", null);
	}
	
	

	public WebElement cashFilterList() {
		return SeleniumUtil.getVisibileWebElement(d, "cashFilterList_T", "CashFlow", null);
		//return webDriver.findElements(By.cssSelector(".ellipsis.name.text.text-css-level1")).get(4);
	}

	public WebElement cashFlowDetails(int i) {
		
		String abC = SeleniumUtil.getVisibileElementXPath (d, "CashFlow", null, "cashFlowDetails_T");
		
		abC.replaceAll("var1", String.valueOf(i).trim());
		
		return d.findElement(By.xpath(abC));
		
		//return webDriver.findElement(By.xpath(".//*[@id='cf-table-container']/div/table/thead/tr/th[" + i + "]"));
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

	public String getMonthName1(int monthNumber) {

		String a1 = new DateFormatSymbols().getMonths()[monthNumber];
		System.out.println("the month is" + a1);
		String b1 = a1.substring(0, 3).toUpperCase();

		String c = b1 + " " + year1;
		System.out.println(c);
		return c;
	}

	public WebElement date() {
		return d.findElement(By.xpath(".//*[@id='cf-summary-content']/div[2]"))
				.findElement(By.xpath(".//*[@id='chartHeader']/div/span[1]"));

	}

	public WebElement arrow() {
		return d.findElement(By.xpath("cashFlowDetail")).findElement(By.xpath(".//*[@id='cf-table-container']/div/table/thead/tr/th[1]/span"));

	}

	public void dateRange(int n, String i) {
		// Select on From Date to open calender
		
		SeleniumUtil.getVisibileWebElement(d, "dateRange_T", "CashFlow", null).click();
		
		//webDriver.findElement(By.xpath(".//*[@id='customStartDate']/input")).click();
		
		SeleniumUtil.getVisibileWebElement(d, "dateRange_T", "CashFlow", null).click();
		
		//webDriver.findElement(By.xpath(".//*[@id='customStartDate']/input")).click();
		SeleniumUtil.waitForPageToLoad(2500);

		// Select the past one yr date
		// webDriver.findElement(By.xpath(".//*[@id='cashFlow-custom-start-dateCnr']/div/div[1]/div[1]/div[1]/div[1]/span[2]")).click();
		// SeleniumUtil.waitForPageToLoad(2500);

		// Select the past date
		
		//webDriver.findElement(By.xpath(".//*[@id='customStartDate']/input")).sendKeys(String.valueOf(i).trim());
		
		SeleniumUtil.getVisibileWebElement(d, "dateRange_T", "CashFlow", null).sendKeys(String.valueOf(i).trim());
		
		SeleniumUtil.waitForPageToLoad(2500);

		// Click on To date to open calender
		//webDriver.findElement(By.xpath(".//*[@id='customEndDate']/input")).click();
		
		SeleniumUtil.getVisibileWebElement(d, "dateRange_T", "CashFlow", null).click();
		SeleniumUtil.waitForPageToLoad(3000);

		// Select todays date
		d.findElement(By.id("cashFlow-custom-end-dateCnr")).findElement(By.id("day" + n + "")).click();
		SeleniumUtil.waitForPageToLoad(2500);
		// Click on update
		//webDriver.findElement(By.xpath(".//*[@id='customfilterPopupId']/div[3]/div[2]/a")).click();
		
		SeleniumUtil.getVisibileWebElement(d, "dateRange1_T", "CashFlow", null).click();
		
	}

	/*private final String col1 = "//td[@data-label='Net Transfers']//a[1]";
	@FindBy(how = How.XPATH, using = col1)*/
	public WebElement Col1() {
		return SeleniumUtil.getVisibileWebElement(d, "Col1_T", "CashFlow", null);
	}
	

	public void selectStartDate(int tableCol, int selectNumber) {

		//webDriver.findElement(By.xpath("//div[contains(@id,'StartDate')]")).click();
		
		
		SeleniumUtil.getVisibileWebElement(d, "selectStartDate_T", "CashFlow", null).click();
		
		// SeleniumUtil.waitForPageToLoad();
		// webDriver.findElement(By.xpath("//div[contains(@class,'custom-start')]//div[@id='prev-year']")).click();
		SeleniumUtil.waitForPageToLoad();
		
		String abC = SeleniumUtil.getVisibileElementXPath (d, "CashFlow", null, "selectStartDate1_T");
		
		abC.replaceAll("var1", String.valueOf(tableCol).trim());
		
		abC.replaceAll("var1", String.valueOf(selectNumber).trim());
		
		d.findElement(By.xpath(abC)).click();
		
		//webDriver.findElement(By.xpath("(//div[@class='custom-start-dateCnr']//div[@class='js-row']/div[" + tableCol + "])[" + selectNumber + "]")).click();

		// Get Past one yr date
		/*
		 * Calendar cal=Calendar.getInstance(); cal.setTime(cal.getTime());
		 * cal.add(Calendar.DATE, -String.valueOf(i).trim()); SimpleDateFormat sdf=new
		 * SimpleDateFormat("MM/dd/YYYY"); return sdf.format(cal.getTime());
		 */
	}

	public void selectEnddate() { // int tableCol ,int selectNumber

		SeleniumUtil.getVisibileWebElement(d, "selectEnddate1_T", "CashFlow", null).click();
		
		//webDriver.findElement(By.xpath("//input[contains(@class,'end-date')]")).click();
		// SeleniumUtil.waitForPageToLoad();
		// webDriver.findElement(By.xpath("//div[contains(@class,'end-date')]//div[@id='prev-year']")).click();
		
		SeleniumUtil.fluentWait(d);
		
		// webDriver.findElement(By.xpath("(//div[@class='custom-end-dateCnr']//div[@class='js-row']/div["+tableCol+"])["+selectNumber+"]")).click();
		//webDriver.findElement(By.xpath("//div[@class='custom-end-dateCnr']//div[@class='js-row']//div[contains(@class,'today')]")).click();
	
		SeleniumUtil.getVisibileWebElement(d, "selectEnddate2_T", "CashFlow", null).click();
	}

}
