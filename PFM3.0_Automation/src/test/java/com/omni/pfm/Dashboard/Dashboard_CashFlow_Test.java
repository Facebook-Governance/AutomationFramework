/*******************************************************************************
 * Copyright (c) 2018 Yodlee Inc. All Rights Reserved.
 * 
 * This software is the confidential and proprietary information of
 * Yodlee, Inc. Use is subject to license terms.
 * 
 * @author
 ******************************************************************************/
package com.omni.pfm.Dashboard;

import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.omni.pfm.PageProcessor.PageParser;
import com.omni.pfm.pages.AccountAddition.AccountAddition;
import com.omni.pfm.pages.Dashboard.CashFlow_Loc;
import com.omni.pfm.pages.Login.L1NodeLogin;
import com.omni.pfm.pages.Login.LoginPage;
import com.omni.pfm.testBase.TestBase;
import com.omni.pfm.utility.DateUtil;
import com.omni.pfm.utility.PropsUtil;
import com.omni.pfm.utility.SeleniumUtil;

public class Dashboard_CashFlow_Test extends TestBase {

	Logger logger = LoggerFactory.getLogger(Dashboard_CashFlow_Test.class);

	CashFlow_Loc DashCF;
	LoginPage NoAccLogin;
	AccountAddition accountAdd;

	@BeforeClass()
	public void init() throws Exception {
		doInitialization("Dashboard - CashFlow");
		NoAccLogin = new LoginPage();
		accountAdd = new AccountAddition();
		DashCF = new CashFlow_Loc(d);

	}

	@Test(description = "Verify that if there are no valid accounts are available then No Accounts screen should be displayed", groups = {
			"DesktopBrowsers,sanity" }, priority = 1, enabled = true)

	public void LoginWithNoAccAndChkCFWHeader() throws Exception {
		LoginPage.loginMain(d, loginParameter);
		accountAdd.linkAccount();
		accountAdd.addContainerAccount("DagBills", "pfmprodcf.Bills1", "Bills1");
		SeleniumUtil.waitForPageToLoad();
		logger.info("No Accounts Message is displayed in CashFlow Widget Card" + DashCF.NoAccsinCFW_CF().getText());
		Assert.assertEquals(DashCF.NoAccsinCFW_CF().getText(), PropsUtil.getDataPropertyValue("NoAccsMsgInCFW"),
				"No Data Message is Not Equal");
	}

	@Test(description = "Verify that No Accounts Screen should be displayed with Link Account Button, once clicking on it user taken to Fast Link Floater", groups = {
			"DesktopBrowsers,sanity" }, priority = 2, enabled = true)
	public void VerifyLinkAccButton() {
		SeleniumUtil.refresh(d);
		SeleniumUtil.waitForPageToLoad();
		logger.info("Linked Account Button is displyaing in CashFlow Widget" + DashCF.LinkAccbtn_CF().getText());
		Assert.assertEquals(DashCF.LinkAccbtn_CF().getText(), PropsUtil.getDataPropertyValue("linkaccinCFW"),
				"Link Btn is not available");
	}

	@Test(description = "Verify that after adding the accounts from CF Widget, all widgets should be refreshed on Dashboard Finapp", groups = {
			"DesktopBrowsers,sanity" }, priority = 3, enabled = true)
	public void AddAccUsingLinkBtn() throws Exception {

		Assert.assertTrue(DashCF.LinkAccbtn_CF().isDisplayed());
		logger.info("LINK ACCOUNT Button is displayed");
		SeleniumUtil.click(DashCF.LinkAccbtn_CF());
		SeleniumUtil.waitForPageToLoad(4000);
		LoginPage.loginMain(d, loginParameter);
		accountAdd.linkAccount();
		accountAdd.addContainerAccount("DagBank", "pfmprodcf.bank5", "bank5");

	}

	@Test(description = "Verify that if there are no data points then no data screen should be displayed in the CF Widget", groups = {
			"DesktopBrowsers,sanity" }, priority = 4, enabled = true)

	public void VerifyNoDataScreen() {
		PageParser.forceNavigate("DashboardPage", d);
		SeleniumUtil.waitForPageToLoad(3000);
		logger.info("There is no Data points displayed in CashFlow Widget");
		Assert.assertTrue(DashCF.NoDataMsgCFwidget_CF().isDisplayed(), "There is no Data points displayed in CashFlow");
	}

	@Test(description = "Verify that no data screen should be displayed with message", groups = {
			"DesktopBrowsers,sanity" }, priority = 5, enabled = true)
	public void VerifyNoDataDisplayMsg() {

		PageParser.forceNavigate("DashboardPage", d);
		SeleniumUtil.waitForElement(null, 3000);
		logger.info("No Data Message is displayed in the CashFlow Widget" + DashCF.NoDataMsgCFwidget_CF().getText());
		Assert.assertTrue(
				DashCF.NoDataMsgCFwidget_CF().getText().contains(PropsUtil.getDataPropertyValue("NoDataMsgInCFW")),
				"Some data is present");
	}

	@Test(description = "Verify that once user clicks on the Visit the App Button then he should be taken to Full view of the Cash Flow Finapp", groups = {
			"DesktopBrowsers,sanity" }, priority = 6, enabled = true)
	public void VerifyVistTheAppBtn() {

		logger.info("Visit the App Button Message:" + DashCF.VisttheAppBtn_CF().getText());
		SeleniumUtil.waitForElement(null, 3000);
		Assert.assertTrue(DashCF.VisttheAppBtn_CF().isDisplayed(), "Visit the App button is not present");

	}

	@Test(description = "Verify that if 3 months data is not available then available last data point(i.e.,n-1 months) should be displayed in the CF Widget", groups = {
			"DesktopBrowsers,sanity" }, priority = 7, enabled = true)
	public void VerifyOneMonthData() throws Exception {
		LoginPage.loginMain(d, loginParameter);
		accountAdd.linkAccount();
		accountAdd.addContainerAccount("DagBank", "testfincheck.bank5", "bank5");
		logger.info("One Month Data Displayed Info is :" + DashCF.OneMonthData_CF().get(0).getText());
		Assert.assertTrue(DashCF.OneMonthData_CF().get(0).isDisplayed(), "One month data is not dislplayed");

	}

	@Test(description = "Verify that current/any one month data point is available then the average of monthly net cash flow message should not be displayed in the CF Widget", groups = {
			"DesktopBrowsers,sanity" }, priority = 8, enabled = true)
	public void VerifyOneMonthDatainCFW() throws Exception {

		int actual = DashCF.OneMonthData_CF().size();
		int expected = Integer.parseInt(PropsUtil.getDataPropertyValue("OneMonthValue"));
		logger.info("One Month Data Displayed Info is :" + DashCF.OneMonthData_CF().get(0).getText());
		logger.info("Count of the month is :" + DashCF.OneMonthData_CF().get(0).getText());
		Assert.assertEquals(actual, expected, "Count of month is not present");
	}

	@Test(description = "Verify that if 6 months data is not available then 3 months data should be displayed in the CF Widget", groups = {
			"DesktopBrowsers,sanity" }, priority = 9, enabled = true)

	public void VerifyThreeMonthsData() throws Exception {

		LoginPage.loginMain(d, loginParameter);
		accountAdd.linkAccount();
		accountAdd.addContainerAccount("DagBank", "pfmprodcf.bank4", "bank4");

		logger.info("Three Months Data Displayed Info is :" + DashCF.ThreeMonthsData_CF().getText());
		Assert.assertTrue(DashCF.ThreeMonthsData_CF().isDisplayed(), "Three months Data is not present");
	}

	@Test(description = "creating user and adding account.", groups = {
			"DesktopBrowsers,sanity" }, priority = 10, enabled = true)

	public void login() throws Exception {
		SeleniumUtil.waitForPageToLoad();
		LoginPage.loginMain(d, loginParameter);
		accountAdd.linkAccount();
		accountAdd.addContainerAccount("DagBank", "pfmcf.bank4", "bank4");
		SeleniumUtil.waitForPageToLoad();
	}

	@Test(description = "Verify that title of the widget should be Cash Flow", groups = {
			"DesktopBrowsers,sanity" }, priority = 11, enabled = true)

	public void verifyCFHeaderTitle() {
		logger.info("Dashboard Widget Cash Flow is displayed  : " + DashCF.CashFlowHeader().getText());
		Assert.assertEquals(DashCF.CashFlowHeader().getText(), PropsUtil.getDataPropertyValue("CashFlowHeader"),
				"Dashboard Widget Cash Flow is not displayed ");

	}

	@Test(description = "Verify that Cash Flow widget should be placed below the SFG widget on Dashboard Finapp", groups = {
			"DesktopBrowsers,sanity" }, dependsOnMethods = "login", priority = 12, enabled = true)

	public void VerifyCFafterSFG() {
		boolean flag = false;
		for (int i = 0; i < DashCF.WidgetList_CF().size(); i++) {
			logger.info("CASH FLOW Widg is present after SFG" + DashCF.WidgetList_CF().get(i).getText());
			if (DashCF.WidgetList_CF().get(i).getText().equals(PropsUtil.getDataPropertyValue("SFGHeader_Dashboard"))) {
				logger.info("Next is CASH FLOW " + DashCF.WidgetList_CF().get(i + 1).getText());
				logger.info("CashFlow is present after SFG");
				flag = true;
			}
		}
		Assert.assertTrue(flag);
	}

	@Test(description = "Verify that Cash Flow Performance Graph for a pre-defined period(N-Months) should be displayed in the Cash Flow Widget", groups = {
			"DesktopBrowsers,sanity" }, priority = 13, enabled = true)

	public void VerifyMonthsinGraph() {

		logger.info("All 6 months Details in Graph" + DashCF.MonthListinGraph_CF().get(1).getText());
		Assert.assertTrue(DashCF.MonthListinGraph_CF().get(1).isDisplayed(), "6 months details are not present");
	}

	@Test(description = "Verify that only monthly data points should be shown in the Cash Flow Widget", groups = {
			"DesktopBrowsers,sanity" }, priority = 14, enabled = true)
	public void VerifyMonthsPointsinGraph() {

		logger.info("Months List Points is present in the Graph");
		Assert.assertEquals(true, DashCF.MonthListPointsinGraph_CF().isEnabled(), "Months List Points is not present");
	}

	@Test(description = "Verify that title of the widget should be Cash Flow", groups = {
			"DesktopBrowsers,sanity" }, priority = 15, enabled = true)

	public void VerifyLast6MonthsinGraph() {

		if (PageParser.isElementPresent("Last6MonthsPointsinGraph_CFforMobile", "DashboardPage", null)) {
			logger.info("Month 1 Point is present in the Graph");

			Assert.assertEquals(true, DashCF.Last6MonthsPointsinGraph_CF().get(0).isDisplayed());
			logger.info("Month 2 Point is present in the Graph");

			Assert.assertEquals(true, DashCF.Last6MonthsPointsinGraph_CF().get(1).isDisplayed());
			logger.info("Month 3 Point is present in the Graph");

			Assert.assertEquals(true, DashCF.Last6MonthsPointsinGraph_CF().get(2).isDisplayed());
		} else {
			logger.info("Month 1 Point is present in the Graph");

			Assert.assertEquals(true, DashCF.Last6MonthsPointsinGraph_CF().get(0).isDisplayed());
			logger.info("Month 2 Point is present in the Graph");

			Assert.assertEquals(true, DashCF.Last6MonthsPointsinGraph_CF().get(1).isDisplayed());
			logger.info("Month 3 Point is present in the Graph");

			Assert.assertEquals(true, DashCF.Last6MonthsPointsinGraph_CF().get(2).isDisplayed());
			logger.info("Month 4 Point is present in the Graph");

			Assert.assertEquals(true, DashCF.Last6MonthsPointsinGraph_CF().get(3).isDisplayed());

			logger.info("Month 5 Point is present in the Graph");

			Assert.assertEquals(true, DashCF.Last6MonthsPointsinGraph_CF().get(4).isDisplayed());

			logger.info("Month 6 Point is present in the Graph");

			Assert.assertEquals(true, DashCF.Last6MonthsPointsinGraph_CF().get(5).isDisplayed());

		}
	}

	@Test(description = "Verify that Cash Inflow bars for each month should be displayed in the graph of Cash Flow Widget", groups = {
			"DesktopBrowsers,sanity" }, priority = 16, enabled = true)

	public void VerifyCFFinInFlowBar() throws InterruptedException {
		PageParser.forceNavigate("DashboardPage", d);

		SeleniumUtil.waitForElement(null, 4000);
		logger.info("Cash Inflow bars for each month displayed in the graph of Cash Flow Widget ");
		Assert.assertTrue(DashCF.CashFlowInflowbar().get(0).isDisplayed(),
				"Cash Inflow bars for each month is not displayed");

	}

	@Test(description = "Verify that Cash Outflow bars for each month should be displayed in the graph of Cash Flow Widget", groups = {
			"DesktopBrowsers,sanity" }, priority = 17, enabled = true)

	public void VerifyCFFinOutFlowBar() throws InterruptedException {
		PageParser.forceNavigate("DashboardPage", d);
		SeleniumUtil.waitForElement(null, 4000);
		logger.info("Cash Outflow bars for each month displayed in the graph of Cash Flow Widget ");
		Assert.assertTrue(DashCF.CashFlowOutflowbar().get(0).isDisplayed(),
				"Cash Outflow bars for each month is not displayed");
	}

	@Test(description = "Verify that Forecasted Cash Inflow bar for current month should be displayed in the graph of the Cash Flow Widget", groups = {
			"DesktopBrowsers,sanity" }, priority = 18, enabled = true)

	public void VerifyCFCashInflowKey() {

		Calendar now = Calendar.getInstance();
		YearMonth thismonth = YearMonth.now();
		DateTimeFormatter monthYearFormatter = DateTimeFormatter.ofPattern("MMM yyyy");
		String ExpectedCurMonth = thismonth.format(monthYearFormatter);
		ExpectedCurMonth = ExpectedCurMonth.toUpperCase();
		logger.info("Expected Current Month is " + ExpectedCurMonth);

		System.out.printf("Today: %s\n", thismonth.format(monthYearFormatter));
		logger.info("Current Date is : " + now.get(Calendar.DATE));

		String ActualCurMonth = DashCF.CurMonth_CF().get(1).getText();
		logger.info("Actual Current Month " + ActualCurMonth);

		Assert.assertTrue(DashCF.CashFlowInflowbar().get(0).isDisplayed());
		if ((ActualCurMonth).contains(ExpectedCurMonth)) {
			Assert.assertTrue(true);
			logger.info("Actual and Expected Current month for the Chart is verified.");
		} else if ((ExpectedCurMonth).contains(ActualCurMonth)) {
			logger.info("Actual and Expected Current month for the Chart is verified!!!!");
			Assert.assertTrue(true);
		} else {
			logger.error("Actual and Expected Current month for the Chart is not verified or not matched.");
			Assert.assertTrue(false);
		}

		Assert.assertTrue(DashCF.CashInFlowKey_CF().isEnabled(),
				"Actual and Expected Current month for the Chart is not verified");
	}

	@Test(description = "Verify that Forecasted Cash Outflow bar for current month (only if key for forecasted cash flow is switched on) should be displayed in the graph of the Cash Flow Widget", groups = {
			"DesktopBrowsers,sanity" }, priority = 19, enabled = true)

	public void VerifyCFCashOutflowKey() {

		Calendar now = Calendar.getInstance();
		logger.info("Current Year is : " + now.get(Calendar.YEAR));
		YearMonth thismonth = YearMonth.now();
		DateTimeFormatter monthYearFormatter = DateTimeFormatter.ofPattern("MMM yyyy");
		String ExpectedCurMonth = thismonth.format(monthYearFormatter);
		ExpectedCurMonth = ExpectedCurMonth.toUpperCase();
		logger.info("Expected Current Month is " + ExpectedCurMonth);
		String ActualCurMonth = DashCF.CurMonth_CF().get(1).getText();
		logger.info("Actual Current Month " + ActualCurMonth);

		Assert.assertTrue(DashCF.CashFlowOutflowbar().get(0).isDisplayed());
		if ((ActualCurMonth).contains(ExpectedCurMonth)) {
			Assert.assertTrue(true);
			logger.info("Actual and Expected Current month for the Chart is verified.");
		} else if ((ExpectedCurMonth).contains(ActualCurMonth)) {
			logger.info("Actual and Expected Current month for the Chart is verified!!!!");
			Assert.assertTrue(true);
		} else {
			logger.info("Actual and Expected Current month for the Chart is not verified or not matched.");
			Assert.assertTrue(false);
		}

		Assert.assertTrue(DashCF.CashOutFlowKey_CF().isDisplayed(),
				"Actual and Expected Current month for the Chart is not verified ");
	}

	@Test(description = "Verify that the entire widget should be clickable then the user is redirected to Cash Flow Finapp", groups = {
			"DesktopBrowsers,sanity" }, priority = 20, enabled = true)

	public void VerifyCFFinBacktoDashboard() {

		SeleniumUtil.click(DashCF.CashFlowWidgetCard());
		SeleniumUtil.waitForElement(null, 5000);
		String backtoDashboard = null;
		if (DashCF.CashFlowFinVerify_CF().equals(null)) {
			logger.info("CashFlow Page is having no data");
		} else {
			logger.info("CashFlow Page is having data");
			logger.info("Back to Dashboard Message is present in CashFlow Finapp is :"
					+ DashCF.CashFlowBacktoDashboard().getText());
			backtoDashboard = DashCF.CashFlowBacktoDashboard().getText();
			Assert.assertTrue(DashCF.CashFlowBacktoDashboard().isDisplayed()); // for Mobile
			SeleniumUtil.click(DashCF.CashFlowBacktoDashboard());
			SeleniumUtil.waitForElement(null, 4000);
		}

		if (DashCF.CashFlowWidgetCard().equals(null)) {
			logger.info("CashFlowWidget is not having Current Month data");
		} else {
			logger.info("CashFlowWidget is having Current Month data" + DashCF.MonthListinGraph_CF().get(1).getText());

		}
		if (PageParser.isElementPresent("WidgetCard_CFMobile", "DashboardPage", null)) {
			Assert.assertTrue(DashCF.CashFlowWidgetCard().isDisplayed());
		} else {
			Assert.assertTrue(DashCF.CashFlowWidgetCard().isDisplayed());
			Assert.assertEquals(backtoDashboard, PropsUtil.getDataPropertyValue("CFBacktoDashboard"),
					"CashFlowWidget is not having Current Month data");
		}
	}

	@Test(description = "Verify that If the forecast data is available, then current month's and forecast data should be shown as normal bars the CF widget.", groups = {
			"DesktopBrowsers,sanity" }, priority = 21, enabled = true)

	public void VerifyForecasteBarColorinCurMonth() {

		String ActualForeCastbarcolor = DashCF.ForecastBarColor_CF().getCssValue("color");
		logger.info("Actual forecast Bar color in Graph is " + DashCF.ForecastBarColor_CF().getCssValue("color"));
		String ExpectedForecastcolor = PropsUtil.getDataPropertyValue("ForeCastcolorGrey");
		logger.info("Forecast Bar color in Graph is " + ExpectedForecastcolor);
		if (DashCF.ForecastBarColor_CF().isDisplayed()) {
			logger.info(
					"ForeCaste Data is available for Current Month in Chart" + DashCF.CurMonth_CF().get(0).getText());
		} else {
			logger.info("ForeCaste Data is not available for Current Month in Chart");
		}
		Assert.assertEquals(ActualForeCastbarcolor, ExpectedForecastcolor,
				"ForeCaste Data is not available for Current Month");
	}

	@Test(description = "Verify that Cash Inflow, Cash Outflow and Forecasted Cash Inflow and Forecasted Cash Outflow bars should be displayed in Blue, Red and Grey colours.", groups = {
			"DesktopBrowsers,sanity" }, priority = 22, enabled = true)

	public void CashInFlowOutFlowForecastBarColors() {

		String ActualcashInFlowbarcolor = PropsUtil.getDataPropertyValue("CashInFlowcolorBlue");
		String ExpectedCashInColor = DashCF.CashInFlowBarColor_CF().getCssValue("color");
		logger.info("Cash InFlow CheckBox color is " + ExpectedCashInColor);
		if (ActualcashInFlowbarcolor.equals(ExpectedCashInColor)) {
			logger.info("Cash InFlow Bar and CheckBox  Data Colors are same and available for Current Month in Chart");
		} else {
			logger.info(
					"Cash InFlow Bar and CheckBox  Data Colors are not same and not available for Current Month in Chart");
		}
		Assert.assertEquals(ActualcashInFlowbarcolor, ExpectedCashInColor, "Current Month data is not available");

		String ActualcashOutFlowbarcolor = PropsUtil.getDataPropertyValue("CashOutFlowcolorRed");
		String ExpectedCashOutColor = DashCF.CashOutFlowBarColor_CF().getCssValue("color");
		logger.info("Cash InFlow CheckBox color is " + ExpectedCashOutColor);
		if (ActualcashOutFlowbarcolor.equals(ExpectedCashOutColor)) {
			logger.info("Cash InFlow Bar and CheckBox  Data Colors are same and available for Current Month in Chart");
		} else {
			logger.info(
					"Cash InFlow Bar and CheckBox  Data Colors are not same and not available for Current Month in Chart");
		}
		Assert.assertEquals(ActualcashOutFlowbarcolor, ExpectedCashOutColor, "Current Month data is not available");

	}

	@Test(description = "Verify that the Months and Amounts should be displayed on  X-axis and Y-axis respectively in the graph of Cash Flow Widget", groups = {
			"DesktopBrowsers,sanity" }, priority = 23, enabled = true)

	public void MonthsinXaxisAmountinYAxis() {

		PageParser.forceNavigate("DashboardPage", d);
		SeleniumUtil.waitForElement(null, 4000);
		String months = DashCF.MonthsinXaxis_CF().getText();
		logger.info("Months in X Axis in Graph is " + months);
		String amount = DashCF.AmountinYaxis_CF().getText();
		logger.info("Amount in Y Axis in Graph is " + amount);
		Assert.assertTrue(DashCF.MonthsinXaxis_CF().isDisplayed(), "Months in X Axis in Graph is not available");
		Assert.assertTrue(DashCF.AmountinYaxis_CF().isDisplayed(), "Months in X Axis in Graph is not available");
	}

	@Test(description = "Verify that Cash Inflow, Cash Outflow, Forecasted and Net Cash Flow Legends should be displayed below the CF Graph", groups = {
			"DesktopBrowsers,sanity" }, priority = 24, enabled = true)

	public void LegendsDisplaybelowCFgraph() {
		PageParser.forceNavigate("DashboardPage", d);
		SeleniumUtil.waitForElement(null, 4000);
		String CFimage = DashCF.ChartImage_CF().getText();
		logger.info("CF chart displays" + CFimage);
		String legends = DashCF.Legends_CF().getText();
		logger.info("Legends displays" + legends);
		Assert.assertTrue(DashCF.Legends_CF().isDisplayed(), "Legends are not displaying");
	}

	@Test(description = "Verify that the message Based on the past (n-1) months, your average monthly net cash flow is $ 100 should be displayed below the graph in the Cash Flow Widget", groups = {
			"DesktopBrowsers,sanity" }, priority = 25, enabled = true)

	public void VerifyBelowCFWidgetText() {
		logger.info("After CF widget Graph displaying Text message is : " + DashCF.BelowCFWtext_CF());
		Assert.assertTrue(DashCF.BelowCFWtext_CF().isDisplayed(), "After CF widget no message ");
	}

	@Test(description = "Verify that the entire widget should be clickable then the user is redirected to Cash Flow Finapp", groups = {
			"DesktopBrowsers,sanity" }, priority = 26, enabled = true)

	public void VerifyCFWidgetisClickable() {
		PageParser.forceNavigate("DashboardPage", d);
		SeleniumUtil.waitForElement(null, 4000);
		if (DashCF.CashFlowWidgetCard() == null) {
			logger.info("CashFlow Widget is Clickable");
			SeleniumUtil.click(DashCF.CashFlowWidgetCard());
		} else {
			logger.info("CashFlow Widget is not Clickable");
		}
		Assert.assertTrue(DashCF.CashFlowWidgetCard().isDisplayed(), "CashFlow Widget is not Clickable");
	}

	@Test(description = "Verify that Cash Flow Finapp is loading fine for the default time period filter.", groups = {
			"DesktopBrowsers,sanity" }, priority = 27, enabled = true)

	public void VerifyTimePeriodFilter() {
		PageParser.forceNavigate("DashboardPage", d);
		SeleniumUtil.waitForElement(null, 4000);
		SeleniumUtil.click(DashCF.CashFlowWidgetCard());
		SeleniumUtil.waitForElement(null, 4000);
		logger.info("Time Period Filter is Displaying in CashFlow Finapp");
		Assert.assertTrue(DashCF.FiltersContainer_CF().isDisplayed(), "Time Period Filter is not Present");
	}

	@Test(description = "Verify that User should be able to see Back to Dashboard link in Cash Flow Finapp when navigating back from Expense/Income Analysis Finapp to Cash Flow Finapp.", groups = {
			"DesktopBrowsers,sanity" }, priority = 28, enabled = true)

	public void BackToDashExpenseIncome() {
		PageParser.forceNavigate("DashboardPage", d);
		SeleniumUtil.waitForElement(null, 4000);
		SeleniumUtil.click(DashCF.CashFlowWidgetCard());
		SeleniumUtil.waitForElement(null, 4000);
		SeleniumUtil.click(DashCF.CashInFlowBarColor_CF());
		SeleniumUtil.click(DashCF.IncomeLink_CF());
		SeleniumUtil.waitForElement(null, 4000);
		Assert.assertTrue(DashCF.BacktoCashFlowBtninSpenFin_CF().isDisplayed());
		SeleniumUtil.click(DashCF.BacktoCashFlowBtninSpenFin_CF());
		Assert.assertTrue(DashCF.BacktoCashFlowBtninSpenFin_CF().isDisplayed());
		SeleniumUtil.click(DashCF.BackToDashinCashFlowFin_CF());
		SeleniumUtil.waitForElement(null, 4000);
		Assert.assertTrue(DashCF.CashFlowWidgetCard().isDisplayed());
	}

	@Test(description = "Verify that User should be able to see Back to Dashboard link in Cash Flow Finapp when navigating back from Transaction Finapp to Cash Flow Finapp.", groups = {
			"DesktopBrowsers,sanity" }, priority = 29, enabled = true)

	public void BackToDashTransFin() {
		{
			PageParser.forceNavigate("DashboardPage", d);
			SeleniumUtil.waitForElement(null, 4000);
			logger.info("Navigating to CashFlow Finapp using CashFlow Widget");
			SeleniumUtil.click(DashCF.CashFlowWidgetCard());
			SeleniumUtil.waitForElement(null, 4000);
			SeleniumUtil.click(DashCF.CashInflowColumninCFfin_CF().get(0));
			SeleniumUtil.waitForPageToLoad(3000);
			logger.info("Back to Dashboard displaying in Transaction Finapp");
			SeleniumUtil.click(DashCF.BacktoCashFlowBtninTran_CF());
			SeleniumUtil.waitForPageToLoad(3000);

			SeleniumUtil.click(DashCF.BackToDashinCashFlowFin_CF());
			SeleniumUtil.waitForPageToLoad(4000);
			Assert.assertTrue(DashCF.CashFlowWidgetCard().isDisplayed(),
					"User not able to see Back to Dashboard link in Cash Flow Finapp");

		}
	}

	@Test(description = "Verify that account filter should not be applied for the cash flow widget", groups = {
			"DesktopBrowsers,sanity" }, priority = 30, enabled = true)

	public void AccFilterNotDisplay() {
		{
			boolean status = false;
			PageParser.forceNavigate("DashboardPage", d);
			SeleniumUtil.waitForElement(null, 4000);
			logger.info("Time Filter is not displayed in the CashFlow Widget");
			try {
				Assert.assertNull(DashCF.FiltersContainer_CF(), "We can see Account Filter on the CashFlow Widget");
				status = true;
			} catch (NullPointerException e) {
				e.printStackTrace();
				status = false;
			}
			Assert.assertTrue(status);
		}
	}
}
