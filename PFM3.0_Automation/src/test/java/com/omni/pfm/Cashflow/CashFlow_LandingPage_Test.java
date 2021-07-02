/*******************************************************************************
 * Copyright (c) 2018 Yodlee Inc. All Rights Reserved.
 *
 * This software is the confidential and proprietary information of Yodlee, Inc.
 * Use is subject to license terms.
 *
 * @author Shivaprasad Bhat
 ******************************************************************************/
package com.omni.pfm.Cashflow;

import java.awt.AWTException;
import java.text.DecimalFormat;
import java.util.Calendar;
import java.util.NoSuchElementException;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import org.openqa.selenium.By;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.accessibility.examples.testng.webdriver.test.RunAccessibilityTest;
import com.omni.pfm.PageProcessor.PageParser;
import com.omni.pfm.pages.AccountAddition.AccountAddition;
import com.omni.pfm.pages.CashFlow.*;
import com.omni.pfm.pages.Login.LoginPage;
import com.omni.pfm.testBase.TestBase;
import com.omni.pfm.utility.DateUtil;
import com.omni.pfm.utility.PropsUtil;
import com.omni.pfm.utility.SeleniumUtil;

public class CashFlow_LandingPage_Test extends TestBase {
	static Logger logger = LoggerFactory.getLogger(CashFlow_LandingPage_Test.class);
	CashFlow_LandingPage_Loc LandingPage;
	CashFlow_Table_Loc table;
	AccountAddition accAddition;
	public double Finalsum;
	public float sum, negativeAmtSum, positiveAmtSum, sum1, negativeAmtSum1, positiveAmtSum1;
	public float add, add1;
	public static String curMonth;

	@BeforeTest(alwaysRun = true)
	public void testInit() throws Exception {
		doInitialization("Cashflow Landing Page Test");
		LandingPage = new CashFlow_LandingPage_Loc(d);
		table = new CashFlow_Table_Loc(d);
		accAddition = new AccountAddition();
		LoginPage.loginMain(d, TestBase.loginParameter);
		SeleniumUtil.waitForPageToLoad();
		logger.info("==> Adding account without any transactions!");
		accAddition.linkAccount();
		SeleniumUtil.waitForPageToLoad();
		Assert.assertTrue(accAddition.addAggregatedAccount(PropsUtil.getDataPropertyValue("dagSite1_CF"),
				PropsUtil.getDataPropertyValue("dagPassword1_CF")));
		SeleniumUtil.waitForPageToLoad();
		PageParser.forceNavigate("CashFlow", d);
		SeleniumUtil.waitForPageToLoad(3000);
	}

	@Test(description = "AT-69774,AT-69843:Cash Flow Header should be in title case.", priority = 0)
	public void verifyCashFlowHeader() throws Exception {
		String cashFlowHeader = LandingPage.CashFlowHeader().getText().trim();
		Assert.assertTrue(LandingPage.CashFlowHeader().isDisplayed());
		Assert.assertEquals(cashFlowHeader, "Cash Flow");
	}

	// Commenting the below tests as FTUE doesnt popup when no data
	@Test(description = "AT-69755,AT-69756 : User should be able to view the coach marks in the cash flow finapp for the first time user experience.", priority = 1)
	public void verifyFirstTimeUserExperience1() throws InterruptedException {
		/*
		 * logger.info("==>Checking the first time user experience for new user");
		 * Assert.assertEquals(LandingPage.WelcomeLabel().getText(),PropsUtil.
		 * getDataPropertyValue("WelcomeLabel_CashFlow"));
		 * Assert.assertTrue(LandingPage.PopUpBlock().isDisplayed());
		 */
	}

	@Test(description = "AT-69757 : Verify that Link All your Cash Accounts Pop up should be displayed as per wireframes once user clicks on the continue button on the Welcome popup", dependsOnMethods = {
			"verifyFirstTimeUserExperience1" }, priority = 2)
	public void verifyFirstTimeUserExperience2() throws InterruptedException {
		/*
		 * SeleniumUtil.click(LandingPage.ContinueBtn());
		 * SeleniumUtil.waitForPageToLoad(2000);
		 * Assert.assertTrue(LandingPage.PopUpBlock().isDisplayed());
		 * Assert.assertTrue(LandingPage.LinkAccBtn1().isDisplayed());
		 */
	}

	@Test(description = "AT-69758 : User should not be exited out of the first step i.e., from Welcome Screen", dependsOnMethods = {
			"verifyFirstTimeUserExperience2" }, priority = 3)
	public void verifyFirstTimeUserExperience3() throws InterruptedException {
		/*
		 * SeleniumUtil.click(LandingPage.GoToMyCashFlowBtn());
		 * SeleniumUtil.waitForPageToLoad(2000);
		 * Assert.assertTrue(LandingPage.CashFlowHeader().isDisplayed());
		 */
	}

	@Test(description = "AT-69781,AT-69782 : When there is no data in the cash flow finapp, only the Link Account button should be displayed.", priority = 4)
	public void verifyNoDataScreenAndLinkAccBtn() throws InterruptedException, AWTException {
		SeleniumUtil.refresh(d);
		SeleniumUtil.waitForPageToLoad(5000);
		PageParser.forceNavigate("CashFlow", d);
		SeleniumUtil.waitForPageToLoad(10000);
		logger.info("==> Check the no data message on screen and add the new account");
		Assert.assertTrue(LandingPage.NoDataMsg().isDisplayed());
	}

	@Test(description = "Adding account with transactions for the previous months.", priority = 5)
	public void addAccWithTxns() throws InterruptedException, AWTException {
		SeleniumUtil.waitForPageToLoad(5000);
		accAddition.linkAccount();
		SeleniumUtil.waitForPageToLoad();
		Assert.assertTrue(accAddition.addAggregatedAccount("swin.site16441.2", "site16441.2"),
				"Acc addition unsucccesful!");
		SeleniumUtil.waitForPageToLoad();
		PageParser.forceNavigate("CashFlow", d);
		SeleniumUtil.waitForPageToLoad(5000);
		logger.info("==>Verifying the chart block");
		Assert.assertTrue(LandingPage.ChartBlock().isDisplayed());
	}

	@Test(description = "AT-69760 : The More button should be clickable", dependsOnMethods = {
			"addAccWithTxns" }, priority = 6)
	public void verifyMoreIcon() throws InterruptedException {
		logger.info("==>Verify the more button and click");
		SeleniumUtil.waitForPageToLoad(3000);
		Assert.assertTrue(LandingPage.moreIcon().isDisplayed());
	}

	@Test(description = "AT-69762 : The Feature tour button should be available in order to launch sequenced coach marks (appearing one at a time) that walk the user through important components of the application.", dependsOnMethods = {
			"verifyMoreIcon" }, priority = 7)
	public void verifyFeatureTour() {
		logger.info("==> Performing a feature tour");
		SeleniumUtil.click(LandingPage.moreIcon());
		SeleniumUtil.waitForPageToLoad(3000);
		Assert.assertTrue(LandingPage.featureTour().isDisplayed());
	}

	@Test(description = "AT-69763,AT-69766,AT-69769,AT-69770,AT-69771 : Verify coachmark flow.", dependsOnMethods = {
			"verifyFeatureTour" }, priority = 8)
	public void verifyFeatureTour1() {
		SeleniumUtil.click(LandingPage.featureTour());
		SeleniumUtil.waitForPageToLoad(3000);
		Assert.assertEquals(LandingPage.linkAllAcc().getText(), PropsUtil.getDataPropertyValue("CoachmarkHeading1"));
	}

	@Test(description = "AT-69772: verify 'Feature Tour' button in coachmark", dependsOnMethods = {
			"verifyFeatureTour1" }, priority = 9)
	public void featureTour2() {
		SeleniumUtil.click(LandingPage.featureTourNext());
		SeleniumUtil.waitForPageToLoad(3000);
		Assert.assertEquals(LandingPage.interactiveChart().getText(),
				PropsUtil.getDataPropertyValue("CoachmarkHeading2"));
	}

	@Test(description = "AT-69772 : Verify coachmark flow.", dependsOnMethods = { "featureTour2" }, priority = 10)
	public void featureTour3() {
		SeleniumUtil.click(LandingPage.featureTourNext());
		SeleniumUtil.waitForPageToLoad(3000);
		Assert.assertEquals(LandingPage.cashflowDetails().getText(),
				PropsUtil.getDataPropertyValue("CoachmarkHeading3"));
	}

	@Test(description = "AT-69772 : Verify coachmark flow.", dependsOnMethods = { "featureTour3" }, priority = 11)
	public void featureTourNext() {
		logger.info("==> Verifying the home screen objects");
		SeleniumUtil.click(LandingPage.featureTourNext());
		SeleniumUtil.waitForPageToLoad(3000);
		Assert.assertTrue(LandingPage.CashFlowHeader().isDisplayed());
	}

	@Test(description = "AT-69772 : Cash Flow Header should be in title case.", dependsOnMethods = {
			"addAccWithTxns" }, priority = 12)
	public void verifyHeader() {
		PageParser.forceNavigate("CashFlow", d);
		SeleniumUtil.waitForPageToLoad(5000);
		logger.info("Home page Title is:" + LandingPage.CashFlowHeader().getText());
		Assert.assertEquals(LandingPage.CashFlowHeader().getText(), PropsUtil.getDataPropertyValue("CashFlowHeader"));
	}

	@Test(description = "AT-69775 : Cash Flow Finapp header should consists of Print, More and Link Account Button.", dependsOnMethods = {
			"addAccWithTxns", "verifyHeader" }, priority = 13)
	public void verifyPrintAndLinkAccBtn() {
		logger.info("==> User interface verification");
		if (PageParser.isElementPresent("moreIcon_CLP_mob", "CashFlow", null)) {
			SeleniumUtil.click(LandingPage.moreIcon_CLP_mob());
			SeleniumUtil.waitForPageToLoad(1000);
		}
		Assert.assertTrue(LandingPage.LinkAccBtn().isDisplayed());
		Assert.assertEquals(LandingPage.LinkAccBtn().getText(), PropsUtil.getDataPropertyValue("LinkAccBtn"));
		if (PageParser.isElementPresent("PrintIcon_CLP", "CashFlow", null)) {
			Assert.assertTrue(LandingPage.PrintIcon().isDisplayed());
			Assert.assertTrue(LandingPage.PrintText().isDisplayed());
			Assert.assertEquals(LandingPage.PrintText().getText(), PropsUtil.getDataPropertyValue("PrintText"));
		}
	}

	@Test(description = "AT-69789 : By default All Cash Flow Accounts should be selected and displayed under All Cash Flow Accounts Filter.", dependsOnMethods = {
			"addAccWithTxns", "verifyHeader" }, priority = 14)
	public void verifyAllCashFlowAccDD() {
		logger.info("==> Verifying the accounts dropdown");
		Assert.assertTrue(LandingPage.AllCashFlowAcc().isDisplayed());
		logger.info("==> " + LandingPage.AllCashFlowAcc().getText());
		Assert.assertEquals(LandingPage.AllCashFlowAcc().getText(), PropsUtil.getDataPropertyValue("AllCashFlowAcc"));
	}

	@Test(description = "AT-69819 : User should also be able to view a status bar of the current proportion of the total cash inflow against the total cash outflow.", dependsOnMethods = {
			"addAccWithTxns", "verifyHeader" }, priority = 16)
	public void verifyStatusBarAreDisplayed() throws InterruptedException {
		logger.info("==> Verify the status bars");
		Assert.assertTrue(LandingPage.statusBar(1).isDisplayed());
		Assert.assertTrue(LandingPage.statusBar(2).isDisplayed());
	}

	@Test(description = "AT-69823 : Current Net Cash Flow Amount should not be clickable", dependsOnMethods = {
			"addAccWithTxns", "verifyHeader" }, priority = 17)
	public void verifyNetCashflowNotClickable() {
		SeleniumUtil.waitForPageToLoad(2000);
		SeleniumUtil.click(LandingPage.CurrentNetCashFlowAmt());
		SeleniumUtil.waitForPageToLoad();
		By transactionTitle = SeleniumUtil.getByObject("CashFlow", null,"TransactionTitle_CLP");
		Assert.assertFalse(SeleniumUtil.isDisplayed(transactionTitle, 3),"Transaction title is still displayed");
	}

	@Test(description = "AT-69802 : On selecting This Month under time filter, the Cash Flow chart should dislplay the current month data points with the total of current month", dependsOnMethods = {
			"addAccWithTxns", "verifyHeader" }, priority = 18)
	public void verifyCurrentNetCashflow() {
		logger.info("==> Currency notation");
		Assert.assertTrue(LandingPage.CurrentNetCashFlowAmt().isDisplayed());
		SeleniumUtil.waitForPageToLoad(2000);
	}

	@Test(description = "AT-69877 : User should be navigated to Transactions Finapp and should be shown only income transactions for that month.", dependsOnMethods = {
			"addAccWithTxns", "verifyHeader" }, priority = 19)
	public void verifyCategoryIn() {
		// commentinf below as its data dependent
		/*
		 * SeleniumUtil.click(table.getCategory1(2)); SeleniumUtil.waitForPageToLoad();
		 * String category = LandingPage.Transcategory().get(0).getText();
		 * logger.info(category);
		 * Assert.assertEquals(LandingPage.Transcategory().get(0).getText(),
		 * PropsUtil.getDataPropertyValue("Category3"));
		 * SeleniumUtil.waitForPageToLoad(2000);
		 * SeleniumUtil.click(LandingPage.BackBtn());
		 * SeleniumUtil.waitForPageToLoad(2000);
		 */
	}

	@Test(description = "AT-69878 : User should be navigated to Transactions Finapp and should be shown only Expense transactions for that month", dependsOnMethods = {
			"addAccWithTxns" }, priority = 20)
	public void verifyCategoryOut() {
		// commentinf below as its data dependent
		/*
		 * PageParser.forceNavigate("CashFlow", d); SeleniumUtil.waitForPageToLoad();
		 * SeleniumUtil.click(table.getCategory(2)); SeleniumUtil.waitForPageToLoad();
		 * String category1 = LandingPage.Transcategory().get(1).getText();
		 * logger.info(category1); SeleniumUtil.waitForPageToLoad(2000);
		 * Assert.assertEquals(LandingPage.Transcategory().get(1).getText(),
		 * PropsUtil.getDataPropertyValue("Category3"));
		 * SeleniumUtil.waitForPageToLoad(1000);
		 * SeleniumUtil.click(LandingPage.BackBtn());
		 * SeleniumUtil.waitForPageToLoad(2000);
		 */
	}

	@Test(description = "AT-69787 : When user expands the Cash Flow Accounts and Time filter dropdowns should be displayed in inverted 'V' symbols.", dependsOnMethods = {
			"addAccWithTxns" }, priority = 22)
	public void verifyDropDownGroup() throws InterruptedException {
		PageParser.forceNavigate("CashFlow", d);
		SeleniumUtil.waitForPageToLoad(5000);
		SeleniumUtil.click(LandingPage.TimeFilterDropdown());
		SeleniumUtil.waitForPageToLoad(3000);
		Assert.assertTrue(LandingPage.TimeFilterDropdown().isDisplayed());
	}

	@Test(description = "AT-69797 : Tick mark should be displayed for the selection of the dropdowns of Cash Flow Accounts and Time filters", dependsOnMethods = {
			"addAccWithTxns", "verifyDropDownGroup" }, priority = 23)
	public void verifyDropDownTickMark() throws InterruptedException {
		// Verify that tick mark should be displayed for the selection of the dropdowns
		// of Cash Flow Accounts and Time filters
		SeleniumUtil.waitForPageToLoad(3000);
		if (LandingPage.istimeFilterCloseMobile()) {
			Assert.assertTrue(LandingPage.TickMark_mob().isDisplayed());
			SeleniumUtil.click(LandingPage.timeFilterCloseMobile());
			SeleniumUtil.click(LandingPage.AllCashFlowAccDropDownIcon());
		} else {
			SeleniumUtil.click(LandingPage.AllCashFlowAccDropDownIcon());
			Assert.assertTrue(LandingPage.TickMark().isDisplayed());
		}
		SeleniumUtil.waitForPageToLoad(3000);
		SeleniumUtil.waitForPageToLoad(3000);
		Assert.assertTrue(LandingPage.AllCashFlowAccDropDownIcon().isDisplayed());
	}

	@Test(description = "AT-69818 : Net cash flow amount should be displayed based on the user selected time filters cash flow data.", dependsOnMethods = {
			"addAccWithTxns", "verifyHeader" }, priority = 24)
	public void verifyNetCashFlowIsDisplayed() throws InterruptedException {
		if (PageParser.isElementPresent("allAccounts_DD_mob", "CashFlow", null)) {
			SeleniumUtil.click(LandingPage.allAccounts_DD_mob());
			SeleniumUtil.waitForPageToLoad(3000);
		}
		Assert.assertTrue(LandingPage.CurrentNetCashFlowAmt().isDisplayed());
	}

	@Test(description = "AT-69788: All Cash Flow Accounts and Account Groups should be displayed in the All Cash Flow Accounts dropdown filter", dependsOnMethods = {
			"addAccWithTxns", "verifyHeader" }, priority = 25)
	public void verifyAccountGroups() {
		SeleniumUtil.click(LandingPage.AllCashFlowAccDropDownIcon());
		SeleniumUtil.waitForPageToLoad(3000);
		String ExpectedList[] = PropsUtil.getDataPropertyValue("ExpectedGroupList").split(",");
		int actualSize = LandingPage.groups().size();
		for (int i = 0; i < actualSize; i++) {
			String actualText = LandingPage.groups().get(i).getText().trim();
			Assert.assertEquals(actualText, ExpectedList[i]);
		}
		if (LandingPage.isaccclosebtn()) {
			SeleniumUtil.click(LandingPage.accclosebtn());
			SeleniumUtil.waitForPageToLoad(10000);
		}
	}

	@Test(description = "AT-69801 : Verify that the time filter drop down has the following Filters: This Month, Last Month, 3 Months, 6 Months, This Year, 12 Months, Last Year and Custom Dates", dependsOnMethods = {
			"addAccWithTxns", "verifyHeader" }, priority = 26)
	public void verifyTimeFilter() {
		String s = PropsUtil.getDataPropertyValue("time_filter_options");
		String expectedValues[] = s.split(",");
		LandingPage.verifyTimeFilterValues(expectedValues);
		for (int i = 1; i <= 4; i++) {
			SeleniumUtil.click(LandingPage.TimeFilterDropdown());
			SeleniumUtil.waitForPageToLoad(8000);
			LandingPage.selectValueFromTimeFilter(i);
			String text = LandingPage.toDate(i).getText();
			String actualDate = LandingPage.fromDate().getText();
			switch (text) {
			// Verify that MONTH to DATE time filter should display the Weekly Data Points
			// i.e., if less than 15 days of data
			case "This Month":
				String expectedDate = "";
				SeleniumUtil.waitForPageToLoad(3000);
				int i2 = Calendar.getInstance().get(Calendar.MONTH);
				if (i2 == 3 || i2 == 5 || i2 == 8 || i2 == 10) {
					expectedDate = "0" + (i2 + 1) + "/01/2017 - " + "0" + (i2 + 1) + "/30/2017";
				} else if (i2 == 2) {
					expectedDate = "0" + (i2 + 1) + "/01/2017 - " + "0" + (i2 + 1) + "/28/2017";
				} else {
					expectedDate = "0" + (i2 + 1) + "/01/2017 - " + "0" + (i2 + 1) + "/31/2017";
				}
				Assert.assertEquals(actualDate, expectedDate);
				break;
			// Verify that LAST MONTH filter should display the Weekly Data Points but based
			// on the calendar month as a start date
			case "Last Month":
				expectedDate = "";
				SeleniumUtil.waitForPageToLoad();
				i2 = Calendar.getInstance().get(Calendar.MONTH);
				if (i2 == 3 || i2 == 5 || i2 == 8 || i2 == 10) {
					expectedDate = "0" + (i2) + "/01/2017 - " + "0" + (i2) + "/31/2017";
				} else if (i2 == 2) {
					expectedDate = "0" + (i2) + "/01/2017 - " + "0" + (i2) + "/28/2017";
				} else {
					expectedDate = "0" + (i2) + "/01/2017 - " + "0" + (i2) + "/30/2017";
				}
				Assert.assertEquals(actualDate, expectedDate);
				break;
			// Verify that 3 MONTHS filter should display the Monthly datapoints with total
			// of three months shown – current month (actuals & forecast), two past months.
			case "3 Months":
				expectedDate = "";
				SeleniumUtil.waitForPageToLoad();
				i2 = Calendar.getInstance().get(Calendar.MONTH);
				if (i2 == 3 || i2 == 5 || i2 == 8 || i2 == 10) {
					expectedDate = "0" + (i2 - 1) + "/01/2017 - " + "0" + (i2 + 1) + "/30/2017";
				} else if (i2 == 2) {
					expectedDate = "0" + (i2 - 1) + "/01/2017 - " + "0" + (i2 + 1) + "/28/2017";
				} else {
					expectedDate = "0" + (i2 - 1) + "/01/2017 - " + "0" + (i2 + 1) + "/31/2017";
				}
				Assert.assertEquals(actualDate, expectedDate);
				break;
			// Verify that 6 MONTHS filter should display the Monthly datapoints with total
			// of six months shown – current month plus past two five months.
			case "6 Months":
				expectedDate = "";
				SeleniumUtil.waitForPageToLoad();
				i2 = Calendar.getInstance().get(Calendar.MONTH);
				if (i2 == 3 || i2 == 5 || i2 == 8 || i2 == 10) {
					expectedDate = (i2 - 4) + "/01/2016 - " + "0" + (i2 + 1) + "/30/2016";
				} else if (i2 == 2) {
					expectedDate = (i2 - 4) + "/01/2017 - " + "0" + (i2 + 1) + "/28/2016";
				} else {
					expectedDate = (i2 - 4) + "/01/2017 - " + "0" + (i2 + 1) + "/31/2016";
				}
				Assert.assertEquals(actualDate, expectedDate);
				break;
			case "Last Year":
				expectedDate = "";
				SeleniumUtil.waitForPageToLoad();
				i2 = Calendar.getInstance().get(Calendar.MONTH);
				if (i2 == 3 || i2 == 5 || i2 == 8 || i2 == 10) {
					expectedDate = "0" + (i2 - 4) + "/01/2017 - " + "0" + (i2 + 1) + "/30/2017";
				} else if (i2 == 2) {
					expectedDate = "0" + (i2 - 4) + "/01/2017 - " + "0" + (i2 + 1) + "/28/2017";
				} else {
					expectedDate = "0" + (i2 - 4) + "/01/2017 - " + "0" + (i2 + 1) + "/31/2017";
				}
				Assert.assertEquals(actualDate, expectedDate);
				break;
			}
		}
	}

	@Test(description = "Refreshing the page to be in Cashflow.", dependsOnMethods = { "addAccWithTxns",
			"verifyHeader" }, priority = 27)
	public void navigateToCashFlow1() {
		PageParser.forceNavigate("CashFlow", d);
		SeleniumUtil.waitForPageToLoad();
	}

	@Test(description = "AT-69822 : Current Net Cash Flow amount should be displayed in blue and red colours for Positive and negative sign amount.", dependsOnMethods = {
			"navigateToCashFlow1" }, priority = 28)
	public void verfiyTotalAndAverageNetCashFlow() throws InterruptedException {
		logger.info("==> Verifying the Total Net Cashflow");
		LandingPage.selectValueFromTimeFilter(4);
		SeleniumUtil.waitForPageToLoad();
		for (int n = 1; n < table.getCategoryDropDownOption1().size() + 1; n++) {
			SeleniumUtil.waitForPageToLoad();
			// String monthlyAmt = table.getCategoryDropDownOption(n).getText(); Changed
			// this since some cobrabds will have USD and additional values Updated MR QA
			String prefix = PropsUtil.getDataPropertyValue("prefix_Currency");
			String monthlyAmt = table.getCategoryDropDownOption(n).getText().replace(prefix, "").replace(",", "")
					.replace(" ", "");
			logger.info("The amount is " + monthlyAmt);
			if (monthlyAmt.contains("-")) {
				add = SeleniumUtil.parseAmountExpense(monthlyAmt);
				float newSum = add * (-1);
				negativeAmtSum = negativeAmtSum + newSum;
				logger.info("Amount after parse -:" + negativeAmtSum);
			} else {
				add = SeleniumUtil.parseAmountExpense(monthlyAmt);
				positiveAmtSum = positiveAmtSum + add;
				logger.info("Amount after parse +:" + positiveAmtSum);
			}
			sum = positiveAmtSum + negativeAmtSum;
		}
		logger.info("Sum is:" + sum + "positive Amt Sum:" + positiveAmtSum + "negative Amt Sum:" + negativeAmtSum);
		String NetCashFLowAmt = table.NetCashFLowAmt().getText();
		if (NetCashFLowAmt.contains("-")) {
			float total = SeleniumUtil.parseAmountExpense(NetCashFLowAmt);
			float newTotal = total * (-1);
			Assert.assertEquals(newTotal, sum);
		} else {
			float total = SeleniumUtil.parseAmountExpense(NetCashFLowAmt);
			DecimalFormat decimalFormat = new DecimalFormat("##");
			String expected = decimalFormat.format(total);
			String actual = decimalFormat.format(sum);
			Assert.assertEquals(expected, actual);
		}
	}

	@Test(description = "AT-69856 : Summary should include AVERAGE MONTHLY NET CASH FLOW and TOTAL NET CASH FLOW data", dependsOnMethods = {
			"navigateToCashFlow1" }, priority = 29)
	public void verfiyAverageNetCashFlow() throws InterruptedException {
		// Commenting the below as it's Data dependent
		/*
		 * logger.info("==> Verifying Average Net Cashflow Ammount"); for (int n = 2; n
		 * < table.getCategoryDropDownOption1().size() + 1; n++) {
		 * SeleniumUtil.waitForPageToLoad(); String monthlyAmt =
		 * table.getCategoryDropDownOption(n).getText(); logger.info("The amount is " +
		 * monthlyAmt); if (monthlyAmt.contains("-")) { add1 =
		 * SeleniumUtil.parseAmountExpense(monthlyAmt); float newSum = add1 * (-1);
		 * negativeAmtSum1 = negativeAmtSum1 + newSum;
		 * logger.info("Amount after parse -:" + negativeAmtSum1); } else { add1 =
		 * SeleniumUtil.parseAmountExpense(monthlyAmt); positiveAmtSum1 =
		 * positiveAmtSum1 + add1; logger.info("Amount after parse +:" +
		 * positiveAmtSum1); } sum1 = positiveAmtSum1 + negativeAmtSum1; } logger.info(
		 * "Sum is:" + sum1 + "positive Amt Sum:" + positiveAmtSum1 +
		 * "negative Amt Sum:" + negativeAmtSum1); String averageNetCashFLowAmt =
		 * table.AvgMontlyNetCashFLow().getText(); if
		 * (averageNetCashFLowAmt.contains("-")) { Float avg1 =
		 * SeleniumUtil.parseAmountExpense(table.AvgMontlyNetCashFLow().getText());
		 * float newAvg = avg1 * (-1); logger.info("==> Calculating the Average"); float
		 * avg = sum1 / (table.getCategoryDropDownOption1().size() - 1);
		 * logger.info("Actual Average is:" + avg); Assert.assertEquals(avg, newAvg); }
		 * else { logger.info("==> Calculating the Average"); float avg = sum1 /
		 * (table.getCategoryDropDownOption1().size() - 1);
		 * logger.info("Actual Average is:" + avg); Float avg1 =
		 * SeleniumUtil.parseAmountExpense(averageNetCashFLowAmt);
		 * Assert.assertEquals(avg, avg1); }
		 */
	}

	@Test(description = "", dependsOnMethods = { "navigateToCashFlow1" }, priority = 30, enabled = false)
	public void verifyTableRowHeaders() throws InterruptedException {
		logger.info("==> Verify headers with respect to expected values");
		for (int i = 0; i < 5; i++) {
			SeleniumUtil.waitForPageToLoad();
			String s = PropsUtil.getDataPropertyValue("TableHeader");
			String[] expectedValues = s.split(",");
			logger.info("Expected Value is:" + expectedValues[i]);
			logger.info("Header is " + table.getTableHeader().get(i).getText().trim());
			Assert.assertEquals(expectedValues[i], table.getTableHeader().get(i).getText().trim());
		}
		logger.info("==> Verify the date lables using method get_Month");
		String curMonth = DateUtil.get_Month();
		logger.info(table.CurrentMonth().getText());
		Assert.assertTrue(curMonth.equalsIgnoreCase(table.CurrentMonth().getText()));
		SeleniumUtil.click(table.DateDropDown());
		SeleniumUtil.waitForPageToLoad();
		logger.info("Actual month is" + table.CurrentMonth().getText());
		logger.info("expected month is" + DateUtil.get_Month());
		Assert.assertTrue(table.getDateCOlumn().get(5).getText().equalsIgnoreCase(DateUtil.get_Month()));
	}

	@Test(description = "AT-69847 : Chart and table data should load fine when user in all accounts or groups and navigated back from transaction finapp", dependsOnMethods = {
			"navigateToCashFlow1" }, priority = 31)
	public void transactionNavigation1() throws InterruptedException {
		logger.info("==> Verify the navigation to Transaction Page after clicking InFlow ammount");
		SeleniumUtil.click(table.getCategory(2));
		SeleniumUtil.waitForPageToLoad();
		Assert.assertTrue(LandingPage.TransactionTitle().isDisplayed());
		SeleniumUtil.waitForPageToLoad();
		SeleniumUtil.click(LandingPage.BackBtn());
		SeleniumUtil.waitForPageToLoad();
	}

	@Test(description = "Making sure that CashFlow is loaded.", dependsOnMethods = {
			"navigateToCashFlow1" }, priority = 32)
	public void navigateToCashFlow() throws InterruptedException {
		PageParser.forceNavigate("CashFlow", d);
		SeleniumUtil.waitForPageToLoad(5000);
	}

	@Test(description = "AT-69851,AT-69853,AT-69868 : Verify arrow hides and unhides details.", dependsOnMethods = {
			"navigateToCashFlow" }, priority = 33)
	public void verifyArrowIconToHideUnhide() throws InterruptedException {
		Assert.assertTrue(table.tableCashFlow1().isDisplayed());
		for (int i = 1; i <= 5; i++) {
			SeleniumUtil.waitForPageToLoad();
			Assert.assertTrue(table.cashFlowDetails(i).isDisplayed());
		}
		SeleniumUtil.waitForPageToLoad(2000);
		Assert.assertTrue(
				table.cashFlowDetail1().getText().equalsIgnoreCase(PropsUtil.getDataPropertyValue("CashFlowDetail")));
		SeleniumUtil.waitForPageToLoad(1000);
		Assert.assertTrue(table.hideDetails1().isEnabled());
		SeleniumUtil.waitForPageToLoad(1000);
		SeleniumUtil.click(table.hideDetails1());
		SeleniumUtil.waitForPageToLoad(2000);
		Assert.assertFalse(table.hidden1().isDisplayed());
	}

	@Test(description = "AT-69856 : 'Summary' should include AVERAGE MONTHLY NET CASH FLOW and TOTAL NET CASH FLOW data", dependsOnMethods = {
			"navigateToCashFlow" }, priority = 34)
	public void verifySummarySection() throws InterruptedException {
		logger.info("==> Verify Summary section by checking all the details");
		table.summarySection1().isDisplayed();
		table.avergeMonthyNetCashFlow1().isDisplayed();
		table.totalNetFlow1().isDisplayed();
		logger.info("==> Verify that 'Summary' includes AVERAGE MONTHLY NET CASH FLOW and TOTAL NET CASH FLOW");
		Assert.assertTrue(table.getSummaryCol(1, 1).isDisplayed());
		Assert.assertTrue(table.getSummaryCol(2, 1).isDisplayed());
		Assert.assertTrue(table.getSummaryCol(1, 2).isDisplayed());
		Assert.assertTrue(table.getSummaryCol(2, 2).isDisplayed());
		String color = table.totalCashFlow1().getCssValue("color");
		logger.info("The color of totalCashFlow1 Button  " + color);
		String color1 = table.getSummaryCol(2, 2).getCssValue("color");
		logger.info("The color of TOTAL NET CASH FLOW Button should be green  " + color1);
	}

	@Test(description = "AT-69880 : Cash flow details displayed should be w.r.t. the time filter and chart.", dependsOnMethods = {
			"navigateToCashFlow" }, priority = 35)
	public void verifyCFDetailsWithTimeFilterSelection() throws InterruptedException {
		logger.info("==> Table - Verify that the cash flow details shown are w.r.t. the time filter");
		SeleniumUtil.click(table.cashFilter1());
		SeleniumUtil.waitForPageToLoad();
		SeleniumUtil.click(table.TimeFilter().get(4));
		SeleniumUtil.waitForPageToLoad();
		table.get_LastMonth1();
		// String a1 = table.getMonthName1(table.mon1);
		String b1 = table.getMonthName(table.mon);
		SeleniumUtil.waitForPageToLoad();
		logger.info("Date is:" + table.monthStart1().getText());
		Assert.assertTrue(table.monthEnd1().getText().contains(b1));
	}

	@Test(description = "AT-69888 : DATE label should not be present in Cash Flow Details in Mobile.", dependsOnMethods = {
			"navigateToCashFlow" }, priority = 36)
	public void verifyDateLabelIsAbsent() throws InterruptedException {
		logger.info("==> DATE label should not be present in Cash Flow Details in Mobile");
		Boolean isPresent = true;
		try {
			SeleniumUtil.waitForPageToLoad();
			table.date();
		} catch (NoSuchElementException e) {
			logger.info(e.toString());
			isPresent = false;
		}
		Assert.assertFalse(isPresent);
	}

	@AfterClass
	public void checkAccessibility() {
		try {
			RunAccessibilityTest rat = new RunAccessibilityTest(this.getClass().getName());
			rat.testAccessibility(d);
		} catch (Exception e) {
		}
	}
}