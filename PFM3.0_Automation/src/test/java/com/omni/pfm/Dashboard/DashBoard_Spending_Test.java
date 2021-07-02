/*******************************************************************************
 * Copyright (c) 2018 Yodlee Inc. All Rights Reserved.
 * 
 * This software is the confidential and proprietary information of
 * Yodlee, Inc. Use is subject to license terms.
 * 
 * @author
 ******************************************************************************/
package com.omni.pfm.Dashboard;

import static org.junit.Assert.assertEquals;
import static org.testng.Assert.assertEquals;

import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.accessibility.examples.testng.webdriver.test.RunAccessibilityTest;
import com.omni.pfm.PageProcessor.PageParser;
import com.omni.pfm.config.Config;
import com.omni.pfm.pages.AccountAddition.AccountAddition;
import com.omni.pfm.pages.Dashboard.Spending_Loc;
import com.omni.pfm.pages.Expense_IncomeAnalysis.Expense_Income_Trend_Loc;
import com.omni.pfm.pages.Login.LoginPage;
import com.omni.pfm.pages.TransactionEnhancement1.Add_Manual_Transaction_Loc;
import com.omni.pfm.pages.TransactionEnhancement1.Transaction_AccountDropDown_Loc;
import com.omni.pfm.testBase.TestBase;
import com.omni.pfm.utility.PropsUtil;
import com.omni.pfm.utility.SeleniumUtil;

public class DashBoard_Spending_Test extends TestBase {

	private static final Logger logger = LoggerFactory.getLogger(DashBoard_Spending_Test.class);
	Spending_Loc spending;
	public static float sum = 0;
    SeleniumUtil util;
    Expense_Income_Trend_Loc trend;
	Transaction_AccountDropDown_Loc txnAcct;
	Add_Manual_Transaction_Loc add_Manual;
	AccountAddition accAddition;
	@BeforeClass(alwaysRun = true)
	public void testInit() throws Exception {

		doInitialization("Dashboard-Spending");
		spending = new Spending_Loc(d);
		trend=new Expense_Income_Trend_Loc(d);
		util=new SeleniumUtil();
		txnAcct=new Transaction_AccountDropDown_Loc(d);
		add_Manual=new Add_Manual_Transaction_Loc(d);
		accAddition=new AccountAddition();
		d.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);

	}


	@Test(description = "AT-96740:Verify spending widget in dashboard", priority = 9, groups = { "Desktop", "Smoke" })
	public void verifySpendingWidget() throws Exception {
		LoginPage.loginMain(d, loginParameter);
		accAddition.linkAccount();
		Assert.assertTrue(accAddition.addAggregatedAccount(PropsUtil.getDataPropertyValue("EIADashBoard_DagSiteName"),
				PropsUtil.getDataPropertyValue("EIADashBoard_DagSitePassword")));
		trend.pageRefresh();
		
		SeleniumUtil.waitForPageToLoad();
		trend.navigateToEA();
		SeleniumUtil.waitForPageToLoad();
		trend.FTUE();
	   addManualReundTxn();
	   PageParser.forceNavigate("DashboardPage", d);
	   SeleniumUtil.waitForPageToLoad();
		Assert.assertEquals(spending.EIAspendingsWidgetHeader().getText(), PropsUtil.getDataPropertyValue("EIADashBoardHeader"),
		"spending widget header is not displayed");
		Assert.assertTrue(spending.EIAspendingTopContentCard().isDisplayed(),"widget is not displayed");
	}
	@Test(description = "AT-96741,AT-96742:Verify havigation is heppening from 6m to 1m", priority = 10, groups = { "Desktop", "Smoke" },dependsOnMethods="verifySpendingWidget")
	public void verifyNavigate6mTo1m() throws Exception {
		
		SeleniumUtil.click(spending.EIAdurationContainer().get(Integer.parseInt(PropsUtil.getDataPropertyValue("EIADashBoard1mIndex"))));
		SeleniumUtil.waitForPageToLoad();
		Assert.assertTrue(spending.EIAspendingsdonut().isDisplayed(),"donut chart is ot displayed");
		
	}
	@Test(description = "AT-96743:Verify total expense in dashboard widget", priority = 11, groups = { "Desktop", "Smoke" },dependsOnMethods="verifyNavigate6mTo1m")
	public void verifyTopExpenseLable() throws Exception {
	Assert.assertEquals(spending.EIAbarchartHeader().getText().trim(), PropsUtil.getDataPropertyValue("EIADashBoardHLCbarchartHeader"),
			"top Expenses This Month is not displayed");
	}
	@Test(description = "AT-96744:Verify MonthTo date lable", priority = 12, groups = { "Desktop", "Smoke" },dependsOnMethods="verifyNavigate6mTo1m")
	public void verifyMonthToDateLable() throws Exception {
	Assert.assertEquals(spending.EIAwidgetSummaryTitle().getText().trim(), PropsUtil.getDataPropertyValue("EIADashBoardwidgetSummaryTitle"),
			"Month To date is not displayed");
	Assert.assertEquals(spending.EIAwidgetSummaryAmount().getText().trim(), PropsUtil.getDataPropertyValue("EIADashBoardwidgetSummaryAmount"),
			"Month To date amount is not displayed");
	}
	@Test(description = "AT-96745:Verify l1 month data", priority = 13, groups = { "Desktop", "Smoke" },dependsOnMethods="verifyNavigate6mTo1m")
	public void verify1MonthData() throws Exception {
		if(Config.appFlag.equals("app")||Config.appFlag.equals("emulator"))
		{
		util.assertExpectedActualList(util.getWebElementsValue(spending.EIAcategoryname()), PropsUtil.getDataPropertyValue("EIADashBoardHLCSpendingCategory"),
				"all category is not displayed");
		util.assertExpectedActualAmountList(util.getWebElementsValue(spending.EIAspendingAmount()), PropsUtil.getDataPropertyValue("EIADashBoardHLCSpendingAmount"),
				"all category amount is not displayed");}
		else {
			util.assertExpectedActualList(util.getWebElementsValue(spending.EIAcategoryname()), PropsUtil.getDataPropertyValue("EIADashBoardHLCSpendingCategory"),
					"all category is not displayed");
			util.assertExpectedActualAmountList(util.getWebElementsValue(spending.EIAcategoryaveragePer()), PropsUtil.getDataPropertyValue("EIADashBoardHLCSpendingPerAmount"),
					"all category per amount is not displayed");
			util.assertExpectedActualAmountList(util.getWebElementsValue(spending.EIAspendingAmount()), PropsUtil.getDataPropertyValue("EIADashBoardHLCSpendingAmount"),
					"all category amount is not displayed");
			}
		}
	
	@Test(description = "AT-96746:Verify widget able to click", priority = 14, groups = { "Desktop", "Smoke" },dependsOnMethods="verifyNavigate6mTo1m")
	public void verify1MonthWidgetClick() throws Exception {
		SeleniumUtil.click(spending.EIAspendingTopContentCard());
		SeleniumUtil.waitForPageToLoad();
		if(Config.appFlag.equals("app")||Config.appFlag.equals("emulator"))
		{
			Assert.assertTrue(trend.EIABackToEIA1().isDisplayed());
		}else {
		Assert.assertEquals(trend.EIABackToEIA().getText(), PropsUtil.getDataPropertyValue("EIADashBoardBackToDashBoard"),
				"back to dashboard");
		}
		Assert.assertEquals(trend.EIAByCategory().getText(), PropsUtil.getDataPropertyValue("EA_ExpenseHeader"),
				"expense by category is not displayed");
		Assert.assertEquals(trend.EIAHLCCatCharValue().getText(), PropsUtil.getDataPropertyValue("EIADashBoardwidgetSummaryAmount"),
				"expense by category is not displayed");
		util.assertExpectedActualList(util.getWebElementsValue(trend.EIAHLCCatNameList()), 
				PropsUtil.getDataPropertyValue("EIADashBoardHLCName"), "all 11 HLc category name is not displayed");
		util.assertExpectedActualAmountList(util.getWebElementsValue(trend.EIAHLCCatPerList()),
				PropsUtil.getDataPropertyValue("EIADashBoardHLCPer"), "all HLc actegory is not displayed % amount");
		util.assertExpectedActualAmountList(util.getWebElementsValue(trend.EIAHLCCatAmountList()),
				PropsUtil.getDataPropertyValue("EIADashBoardHLCAmount"), "all HLc catgeory is not displayed with expense amountamount");
	}
	@Test(description = "AT-96747,AT-96748:Verify nagigation happening from 1 month to 6 month", priority = 15, groups = { "Desktop", "Smoke" },dependsOnMethods="verify1MonthWidgetClick")
	public void verify1MonthTo6Month() throws Exception {
		SeleniumUtil.click(trend.EIABackToEIA());
		SeleniumUtil.waitForPageToLoad();
		SeleniumUtil.click(spending.EIAdurationContainer().get(Integer.parseInt(PropsUtil.getDataPropertyValue("EIADashBoard6mIndex"))));
		Assert.assertTrue(spending.EIAspendingsBarChart().isDisplayed(),"bar chart is not dispalyed");
	}
	
	@Test(description = "AT-96747,AT-96748:Verify avg message in widget", priority = 16, groups = { "Desktop", "Smoke" },dependsOnMethods="verify1MonthTo6Month")
	public void verifyAvgMessage() throws Exception {
		String avgMsg=trend.getAvgAmountMessage(Integer.parseInt(PropsUtil.getDataPropertyValue("EA_Trend_DefaultTF_AvgAmountStart")), 
				Integer.parseInt(PropsUtil.getDataPropertyValue("EA_Trend_DefaultTF_AvgAmountEnd")),
				PropsUtil.getDataPropertyValue("EA_Default6MonthAvgAmountMessage1"),
				PropsUtil.getDataPropertyValue("EIADashBoardAvgAmount"));
		Assert.assertEquals(spending.EIAlegendAvg().getText().trim(),avgMsg,
				"expense by category is not displayed");
	}
	@Test(description = "AT-96750,AT-96751,AT-96761,AT-96762:Verify 6 month to dat value is displaying", priority = 17, groups = { "Desktop", "Smoke" },dependsOnMethods="verify1MonthTo6Month")
	public void verify6MonthToDateLable() throws Exception {
	Assert.assertEquals(spending.EIAwidgetSummaryTitle().getText().trim(), PropsUtil.getDataPropertyValue("EIADashBoardwidgetSummaryTitle"),
			"Month To date is not displayed");
	Assert.assertEquals(spending.EIAwidgetSummaryAmount().getText().trim(), PropsUtil.getDataPropertyValue("EIADashBoardwidgetSummaryAmount"),
			"Month To date amount is not displayed");
	}
	@Test(description = "AT-96754,AT-96755:Verify this month widget clickable", priority = 18, groups = { "Desktop", "Smoke" },dependsOnMethods="verify1MonthTo6Month")
	public void verify6MonthWidgetClick() throws Exception {
		SeleniumUtil.click(spending.EIAspendingAvgContentCard());
		SeleniumUtil.waitForPageToLoad();
		if(Config.appFlag.equals("app")||Config.appFlag.equals("emulator"))
		{
		Assert.assertTrue(trend.EIABackToEIA1().isDisplayed());
		}else {
			Assert.assertEquals(trend.EIABackToEIA().getText(), PropsUtil.getDataPropertyValue("EIADashBoardBackToDashBoard"),
					"back to dashboard");
		}
		Assert.assertTrue(trend.EIATimeFilterDropDown().isDisplayed(),"time filter is not displayed");
		Assert.assertEquals(trend.EIATimeFilterDropDownLabel().getText(), PropsUtil.getDataPropertyValue("EA_DefaultTimeFilter"), "6 months label is not displayed");
		Assert.assertEquals(trend.EIATimeFilterDropDownDate().getText().trim(),txnAcct.monthStartDate(Integer.parseInt(PropsUtil.getDataPropertyValue("EA_Trend_DefaultTF_EndMonthSize")), 
				"MM/dd/yyy")+" "+"-"+" "+add_Manual.targateDate1(Integer.parseInt(PropsUtil.getDataPropertyValue("EA_Trend_DefaultTF_StartMonthSize"))),
				"6 th month start date and current date is not displayed");

		
	}
	@Test(description = "AT-96757:verify invalid expense account in DashBoard ", priority = 19, groups = { "Desktop", "Smoke" })
	public void verifyInvalidAccount() throws Exception {
	LoginPage.loginMain(d, loginParameter);
	accAddition.linkAccount();
	accAddition.addAggregatedAccount(PropsUtil.getDataPropertyValue("EIADashBoard_DagSiteName1"), PropsUtil.getDataPropertyValue("EIADashBoard_DagSitePassword1"));
	SeleniumUtil.waitForPageToLoad();
	Assert.assertEquals(spending.EIAnoDataScreen_Header().getText(), PropsUtil.getDataPropertyValue("EIADashBoardNoDataHeader"),
			"back to dashboard");
	Assert.assertEquals(spending.EIAnoDataScreen_Description().getText(), PropsUtil.getDataPropertyValue("EIADashBoardNoDataInfo"),
			"back to dashboard");
	Assert.assertEquals(spending.EIAnoDataScreen_Button().getText(), PropsUtil.getDataPropertyValue("EIADashBoardLinkAccount"),
			"back to dashboard");
	}
	public void addManualReundTxn() {
		trend.clickEIAMTLink();

		String IA_MTCategory5 = null;
		String IA_MTCategory6 = null;
		String IA_MTCategory7 = null;
		String IA_MTCategory8 = null;
		if (PropsUtil.getEnvPropertyValue("CategoryList43").equalsIgnoreCase("yes")) {
			IA_MTCategory5 = PropsUtil.getDataPropertyValue("EA_MTCategory5_43");
			IA_MTCategory6 = PropsUtil.getDataPropertyValue("EA_MTCategory6_43");
			IA_MTCategory7 = PropsUtil.getDataPropertyValue("EA_MTCategory7_43");
			IA_MTCategory8 = PropsUtil.getDataPropertyValue("EA_MTCategory7_43");

		} else {
			IA_MTCategory5 = PropsUtil.getDataPropertyValue("EA_MTCategory6");
			IA_MTCategory6 = PropsUtil.getDataPropertyValue("EA_MTCategory7");
			IA_MTCategory7 = PropsUtil.getDataPropertyValue("EA_MTCategory8");
			IA_MTCategory8 = PropsUtil.getDataPropertyValue("EA_MTCategory9");

		}
		SeleniumUtil.waitForPageToLoad();
		add_Manual.createOneTimeTransaction(PropsUtil.getDataPropertyValue("EA_MTAmount6"),
				PropsUtil.getDataPropertyValue("EA_MTDescription6"), PropsUtil.getDataPropertyValue("EA_MTTxnType6"),
				PropsUtil.getDataPropertyValue("EA_MTAccounts6"), PropsUtil.getDataPropertyValue("EA_MTFrequency6"), 0,
				IA_MTCategory5, PropsUtil.getDataPropertyValue("EA_MTNote6"),
				PropsUtil.getDataPropertyValue("EA_MTCheck5"));
		SeleniumUtil.waitForPageToLoad();

		trend.clickEIAMTLink();
		SeleniumUtil.waitForPageToLoad();
		add_Manual.createOneTimeTransaction(PropsUtil.getDataPropertyValue("EA_MTAmount7"),
				PropsUtil.getDataPropertyValue("EA_MTDescription7"), PropsUtil.getDataPropertyValue("EA_MTTxnType7"),
				PropsUtil.getDataPropertyValue("EA_MTAccounts7"), PropsUtil.getDataPropertyValue("EA_MTFrequency7"), 0,
				IA_MTCategory6, PropsUtil.getDataPropertyValue("EA_MTNote7"),
				PropsUtil.getDataPropertyValue("EA_MTCheck7"));
		SeleniumUtil.waitForPageToLoad();

		trend.clickEIAMTLink();
		SeleniumUtil.waitForPageToLoad();
		add_Manual.createOneTimeTransaction(PropsUtil.getDataPropertyValue("EA_MTAmount8"),
				PropsUtil.getDataPropertyValue("EA_MTDescription8"), PropsUtil.getDataPropertyValue("EA_MTTxnType8"),
				PropsUtil.getDataPropertyValue("EA_MTAccounts8"), PropsUtil.getDataPropertyValue("EA_MTFrequency8"), 0,
				IA_MTCategory7, PropsUtil.getDataPropertyValue("EA_MTNote8"),
				PropsUtil.getDataPropertyValue("EA_MTCheck8"));
		SeleniumUtil.waitForPageToLoad();

		trend.clickEIAMTLink();
		SeleniumUtil.waitForPageToLoad();
		add_Manual.createOneTimeTransaction(PropsUtil.getDataPropertyValue("EA_MTAmount9"),
				PropsUtil.getDataPropertyValue("EA_MTDescription9"), PropsUtil.getDataPropertyValue("EA_MTTxnType9"),
				PropsUtil.getDataPropertyValue("EA_MTAccounts9"), PropsUtil.getDataPropertyValue("EA_MTFrequency9"), 0,
				IA_MTCategory8, PropsUtil.getDataPropertyValue("EA_MTNote9"),
				PropsUtil.getDataPropertyValue("EA_MTCheck9"));
		SeleniumUtil.waitForPageToLoad();

	}
	
	@AfterClass
	public void checkAccessibiSelelity() {
		try {
			RunAccessibilityTest rat = new RunAccessibilityTest(this.getClass().getName());
			rat.testAccessibility(d);

		} catch (Exception e) {

		}
	}

}
