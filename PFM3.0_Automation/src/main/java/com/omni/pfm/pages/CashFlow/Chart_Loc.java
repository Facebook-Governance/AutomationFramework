/*******************************************************************************
 * Copyright (c) 2018 Yodlee Inc. All Rights Reserved.
 * 
 * This software is the confidential and proprietary information of
 * Yodlee, Inc. Use is subject to license terms.
 * 
 * @author
 ******************************************************************************/
package com.omni.pfm.pages.CashFlow;

import java.util.List; 

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import org.openqa.selenium.interactions.Actions;

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
public class Chart_Loc {

	static Logger logger = LoggerFactory.getLogger(Chart_Loc.class);
	public WebDriver d = null;
	static String pageName = "CashFlow"; // Page Name
	static String frameName = null;

	public Chart_Loc(WebDriver d) {
		this.d = d;
	}

	public List<WebElement> cashInflowOutFLow(int n) {

		// returns the cashInFlow/Cash Outflow

		return d.findElements(By.xpath(".//*[@id='cf-table-container']/div/table/tbody/tr/td[" + n + "]/a"));
		/*String s = SeleniumUtil.getVisibileElementXPath(d, "cashOut", pageName, frameName);
		s.replaceAll("VAR_i", Integer.toString(n));
		return d.findElements(By.xpath(s));*/

	}

	public void clickOnCurrentMonthChart() {

		// Method will click on the current month on chart

		/*WebElement svgObject = d
				.findElement(By.xpath("//*[@class='highcharts-series highcharts-tracker'][1]//*[name()='rect']"));*/
		WebElement svgObject = SeleniumUtil.getWebElement(d, "hightrack", pageName, frameName);

		Actions builder = new Actions(d);

		builder.click(svgObject).build().perform();

	}

	public List<WebElement> cashInFlowOutFlowChart() {

		// clicks on CashInFlow on the chart if value of i=3

		// Clicks on CashOutFlow on the chart if value of i=4

		/*return d.findElements(
				By.xpath("//*[@class='highcharts-series highcharts-tracker'][" + i + "]//*[name()='rect']"));*/
		return SeleniumUtil.getWebElements(d, "hcSeries1", pageName, frameName);
		/*String s = SeleniumUtil.getVisibileElementXPath(d, "hcSeries", pageName, frameName);
		s.replaceAll("VAR_i", Integer.toString(i));
		return d.findElements(By.xpath(s));*/
	}

	public List<WebElement> cashNetFlowChart() {

		// n is the total number of dots on the chart

		/*return d
				.findElements(By.xpath("//*[@class='highcharts-markers highcharts-tracker'][2]//*[name()='path']"));*/
		//WebElement svgObject = SeleniumUtil.getWebElements(d, "hightrack", pageName, frameName);
		return SeleniumUtil.getWebElements(d, "marker", pageName, frameName);
	}

	/*private final String AccDropDown = ".//*[@id='accountsFilterText']";
	@FindBy(how = How.XPATH, using = AccDropDown)*/
	public WebElement accDropDown() {
		return SeleniumUtil.getVisibileWebElement(d, "AccDropDown", pageName, frameName);
	}

	/*private final String atmWithdrawal = "//div[@id='postedTxnCnr']//dd[1]//a//div//div[2]";
	@FindBy(how = How.XPATH, using = atmWithdrawal)*/
	public WebElement AtmWithdrawal() {
		return SeleniumUtil.getWebElement(d, "atmWithdrawal", pageName, frameName);
	}

	/*private final String BackTocashlFlow = "//span[text()='Back to Cash Flow']";
	@FindBy(how = How.XPATH, using = BackTocashlFlow)*/
	public WebElement backTocashlFlow() {
		return SeleniumUtil.getVisibileWebElement(d, "BackTocashlFlow", pageName, frameName);
	}

	/*private final String ToolTipForcasted = "//span[contains(text(),'Forecasted Cash')]";
	@FindBy(how = How.XPATH, using = ToolTipForcasted)*/
	public WebElement toolTipForcasted() {
		return SeleniumUtil.getWebElement(d, "ToolTipForcasted", pageName, frameName);
	}

	/*private final String ToolTipcloseBtn = "tooltipCloseBtn";
	@FindBy(how = How.ID, using = ToolTipcloseBtn)*/
	public WebElement toolTipcloseBtn() {
		return SeleniumUtil.getVisibileWebElement(d, "tooltipCloseBtn", pageName, frameName);
	}

	/*private final String ToolTipHeader = "//a[@title='View Income Analysis']";
	@FindBy(how = How.XPATH, using = ToolTipHeader)*/
	public WebElement toolTipHeader() {
		return SeleniumUtil.getVisibileWebElement(d, "ToolTipHeader", pageName, frameName);
	}

	/*private final String netCashFLowHeader = "tooltipHeader";
	@FindBy(how = How.ID, using = netCashFLowHeader)*/
	public WebElement NetCashFLowHeader() {
		return SeleniumUtil.getVisibileWebElement(d, "netCashFLowHeader", pageName, frameName);
	}

	/*private final String netCashFLowAMountToolTip = "//div[@class='valueText valueTextInHeader']";
	@FindBy(how = How.XPATH, using = netCashFLowAMountToolTip)*/
	public WebElement NetCashFLowAMountToolTip() {
		return SeleniumUtil.getVisibileWebElement(d, "netCashFLowAMountToolTip", pageName, frameName);
	}

	/*private final String expenseHeader = "//tr[1]";
	@FindBy(how = How.XPATH, using = expenseHeader)*/
	public WebElement ExpenseHeader() {
		return SeleniumUtil.getVisibileWebElement(d, "expenseHeader", pageName, frameName);
	}

	/*private final String TransferInHeader = "//span[text()='Transfer In']";
	@FindBy(how = How.XPATH, using = TransferInHeader)*/
	public WebElement transferInHeader() {
		return SeleniumUtil.getVisibileWebElement(d, "TransferInHeader", pageName, frameName);
	}

	/*private final String TransferOutHeader = "//span[text()='Transfer Out']";
	@FindBy(how = How.XPATH, using = TransferOutHeader)*/
	public WebElement transferOutHeader() {
		return SeleniumUtil.getVisibileWebElement(d, "TransferOutHeader", pageName, frameName);
	}

	/*private final String TotalInFlow = "//span[text()='Total Inflow']";
	@FindBy(how = How.XPATH, using = TotalInFlow)*/
	public WebElement totalInFlow() {
		return SeleniumUtil.getVisibileWebElement(d, "TotalInFlow", pageName, frameName);
	}

	/*private final String TotalOutFlow = "//span[text()='Total Outflow']";
	@FindBy(how = How.XPATH, using = TotalOutFlow)*/
	public WebElement totalOutFlow() {
		return SeleniumUtil.getVisibileWebElement(d, "TotalOutFlow", pageName, frameName);
	}

	/*private final String TransactionLink = ".//*[@id='transactionLink']";
	@FindBy(how = How.XPATH, using = TransactionLink)*/
	public WebElement transactionLink() {
		return SeleniumUtil.getVisibileWebElement(d, "TransactionLink", pageName, frameName);
	}

	/*private final String IncomeText = "//h2[contains(text(),'Income By Category')]";
	@FindBy(how = How.XPATH, using = IncomeText)*/
	public WebElement incomeText() {
		return SeleniumUtil.getVisibileWebElement(d, "IncomeText", pageName, frameName);
	}

//	private final String expenseText = "//h2[contains(text(),'Expenses By Category')]";
//	@FindBy(how = How.XPATH, using = expenseText)
	public WebElement ExpenseText() {
		return SeleniumUtil.getVisibileWebElement(d, "expenseText", pageName, frameName);
	}

	/*private final String BackToCashFlow = ".//*[@id='back-to-text']";
	@FindBy(how = How.XPATH, using = BackToCashFlow)*/
	public WebElement backToCashFlow() {
		return SeleniumUtil.getVisibileWebElement(d, "BackToCashFlow", pageName, frameName);
	}

	/*private final String ClickOnDateOnChart = "//*[@id='chartHeader']/div/span[1]";
	@FindBy(how = How.XPATH, using = ClickOnDateOnChart)*/
	public WebElement clickOnDateOnChart() {
		return SeleniumUtil.getVisibileWebElement(d, "ClickOnDateOnChart", pageName, frameName);
	}

	/*private final String durationtext = "durationText";
	@FindBy(how = How.ID, using = durationtext)*/
	public WebElement durationText() {
		return SeleniumUtil.getVisibileWebElement(d, "durationText", pageName, frameName);
	}

	/*private final String customstartdate = ".start-date";
	@FindBy(how = How.CSS, using = customstartdate)*/
	public WebElement customStartDate() {
		return SeleniumUtil.getVisibileWebElement(d, "customstartdate", pageName, frameName);
	}

	/*private final String customenddate = ".end-date";
	@FindBy(how = How.CSS, using = customenddate)*/
	public WebElement customEndDate() {
		return SeleniumUtil.getVisibileWebElement(d, "customenddate", pageName, frameName);
	}

	/*private final String daterange = "//div[@id='chartHeader']/div/span";
	@FindBy(how = How.XPATH, using = daterange)*/
	public WebElement dateRange() {
		return SeleniumUtil.getVisibileWebElement(d, "daterange", pageName, frameName);
	}

	/*private final String cashflowamt = ".transactionLink";
	@FindBy(how = How.CSS, using = cashflowamt)*/
	public WebElement cashflowAmt() {
		return SeleniumUtil.getVisibileWebElement(d, "cashflowamt", pageName, frameName);
	}

	/*private final String transactionamt = "//div[contains(@class,'row clearfix row-height')]/div[2]/span";
	@FindBy(how = How.XPATH, using = transactionamt)*/
	public WebElement transactionAmount() {
		return SeleniumUtil.getVisibileWebElement(d, "transactionamt", pageName, frameName);
	}

	/*private final String backbtn = "back-to-cashflow-text";
	@FindBy(how = How.ID, using = backbtn)*/
	public WebElement backButton() {
		return SeleniumUtil.getVisibileWebElement(d, "backbtn", pageName, frameName);
	}

	/*private final String legends = "//div[@class='highcharts-legend-item']/span/span/label";
	@FindBy(how = How.XPATH, using = legends)*/
	public List<WebElement> legendsText(){
		return SeleniumUtil.getWebElements(d, "legends", pageName, frameName);
	}

	/*private final String checkbx = "//div[@class='highcharts-legend-item']/span/span/span/span";

	@FindBy(how = How.XPATH, using = checkbx)*/
	public List<WebElement> checkBox(){
		return SeleniumUtil.getWebElements(d, "checkbx", pageName, frameName);
	}
	
	/*private final String transactionAmt = "//div[contains(@id,'cf-table-container')]//table//tbody/tr[2]/td[2]/a";
	@FindBy(how = How.XPATH, using = transactionAmt)*/
	public WebElement TransactionAmt() {
		return SeleniumUtil.getVisibileWebElement(d, "transactionAmt", pageName, frameName);
	}
	
	public WebElement VerifyCheckBox(int checkboxNum) {

		//return d.findElement(By.xpath("//div[@class='highcharts-legend-item'][" + checkboxNum + "]/span/span/span"));
		String s = SeleniumUtil.getVisibileElementXPath(d, "hcSeries", pageName, frameName);
		s.replaceAll("VAR_c", Integer.toString(checkboxNum));
		return (WebElement) d.findElements(By.xpath(s));

	}

/*	private final String cashinflow = "//*[@class='highcharts-series highcharts-tracker'][3]//*[name()='rect']";
	@FindBy(how = How.ID, using = cashinflow)*/
	public WebElement cashInFlow() {
		return SeleniumUtil.getVisibileWebElement(d, "cashinflow", pageName, frameName);
	}

	/*private final String cashoutflow = "//*[@class='highcharts-series highcharts-tracker'][4]//*[name()='rect']";
	@FindBy(how = How.ID, using = cashoutflow)*/
	public WebElement cashOutFlow() {
		return SeleniumUtil.getVisibileWebElement(d, "cashoutflow", pageName, frameName);
	}

}
