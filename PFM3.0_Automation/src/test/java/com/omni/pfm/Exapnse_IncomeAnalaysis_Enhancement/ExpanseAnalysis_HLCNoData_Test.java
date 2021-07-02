/**
* Copyright (c) 2018 Yodlee Inc. All Rights Reserved.
*
* This software is the confidential and proprietary information of
* Yodlee, Inc. Use is subject to license terms.
*
* @author Renuka K
*/
package com.omni.pfm.Exapnse_IncomeAnalaysis_Enhancement;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.omni.pfm.pages.AccountAddition.AccountAddition;
import com.omni.pfm.pages.Expense_IncomeAnalysis.ExpLandingPage_Loc;
import com.omni.pfm.pages.Expense_IncomeAnalysis.Expense_Income_Trend_Loc;
import com.omni.pfm.pages.Login.LoginPage;
import com.omni.pfm.pages.TransactionEnhancement1.Add_Manual_Transaction_Loc;
import com.omni.pfm.pages.TransactionEnhancement1.Transaction_AccountDropDown_Loc;
import com.omni.pfm.pages.TransactionEnhancement1.Transaction_Filter_Loc;
import com.omni.pfm.testBase.TestBase;
import com.omni.pfm.utility.PropsUtil;
import com.omni.pfm.utility.SeleniumUtil;

public class ExpanseAnalysis_HLCNoData_Test extends TestBase {

	ExpLandingPage_Loc expLandingPage;
	Transaction_Filter_Loc filter;
	AccountAddition accAddition;
	Expense_Income_Trend_Loc expenseTrend;
	SeleniumUtil util;
	Transaction_AccountDropDown_Loc txnAcct;
	Add_Manual_Transaction_Loc add_Manual;

	@BeforeClass(alwaysRun = true)

	public void init() throws Exception {
		doInitialization("Expense Analysis");

		expLandingPage = new ExpLandingPage_Loc(d);

		accAddition = new AccountAddition();
		expenseTrend = new Expense_Income_Trend_Loc(d);
		util = new SeleniumUtil();
		txnAcct = new Transaction_AccountDropDown_Loc(d);
		add_Manual = new Add_Manual_Transaction_Loc(d);
		filter = new Transaction_Filter_Loc(d);
		LoginPage.loginMain(d, loginParameter);
		SeleniumUtil.waitForPageToLoad();
		accAddition.linkAccount();
		Assert.assertTrue(accAddition.addAggregatedAccount(PropsUtil.getDataPropertyValue("EIA_HLC_NoDataSite"),
				PropsUtil.getDataPropertyValue("EIA_HLC_NoDataPassword")));
		expenseTrend.navigateToEA();
		expenseTrend.FTUE();
	}

	@Test(description = "AT-92182:verify no accounts page in Expense Page", priority = 1)
	public void verifyNoTxn_EA() {

		Assert.assertEquals(expenseTrend.EIATrendTopTxnDate().size(),
				Integer.parseInt(PropsUtil.getDataPropertyValue("EA_TrendNoTopTxnSize")),
				"uncatgeory txn header should not be displayed");
		Assert.assertEquals(expenseTrend.EIATrendTopTxnDescriprion().size(),
				Integer.parseInt(PropsUtil.getDataPropertyValue("EA_TrendNoTopTxnSize")),
				"uncategorized txn decription is displayed");
		Assert.assertEquals(expenseTrend.EIATrendTopTxnAccount().size(),
				Integer.parseInt(PropsUtil.getDataPropertyValue("EA_TrendNoTopTxnSize")),
				"uncategorized txn Account is displayed");
		Assert.assertEquals(expenseTrend.EIATrendTopTxnCatgeory().size(),
				Integer.parseInt(PropsUtil.getDataPropertyValue("EA_TrendNoTopTxnSize")),
				"uncategorized txn Category is displayed");
		Assert.assertFalse(SeleniumUtil.isDisplayed(expenseTrend.EIATrendTopTxnAmountByObject(), 2),
				"uncategorized txn Amount is displayed");

	}

	@Test(description = "verify no accounts page in Eexpanse Page", priority = 2, dependsOnMethods = "verifyNoTxn_EA")
	public void verifyNoData_EA() {
		expenseTrend.clickLegendMonth(Integer.parseInt(PropsUtil.getDataPropertyValue("EA_TrendTOHLC_MonthList")));
		Assert.assertEquals(expenseTrend.EIANoDataHeader().getText().trim(),
				PropsUtil.getDataPropertyValue("EA_NoDataHeaderLable"), "no data header is not displayed");
		Assert.assertEquals(expenseTrend.EIANoDataDescription().getText().trim(),
				PropsUtil.getDataPropertyValue("EA_NoDataDescription"), "no data description is not displayed");

		Assert.assertEquals(expenseTrend.EIAccountDropDown().getText().trim(),
				PropsUtil.getDataPropertyValue("EA_AllAccountDropDown"),
				"verify account dropwn is not displayed with all expense accounts lable");
		Assert.assertTrue(expenseTrend.EIATimeFilterDropDown().isDisplayed(), "verify  time filter is displaying");
		Assert.assertEquals(expenseTrend.EIATimeFilterDropDownLabel().getText(),
				PropsUtil.getDataPropertyValue("EA_ThisMonthTmeFilter"), "6 months label is not displayed");
		Assert.assertEquals(expenseTrend.EIATimeFilterDropDownDate().getText().trim(),
				txnAcct.monthStartDate(Integer.parseInt(PropsUtil.getDataPropertyValue("EA_HLC_To_Trend_TMStartDate")),
						"MM/dd/yyy")
						+ " " + "-" + " "
						+ add_Manual.targateDate1(
								Integer.parseInt(PropsUtil.getDataPropertyValue("EA_HLC_To_Trend_TMEndDate"))),
				"6 th month start date and current date is not displayed");

	}

	@Test(description = "AT-92182:verify no accounts page in Eexpanse Page", priority = 3)
	public void verifyNoTxn_IA() {
		expenseTrend.navigateToEA();
		expenseTrend.clickIncome();
		Assert.assertEquals(expenseTrend.EIATrendTopTxnDate().size(),
				Integer.parseInt(PropsUtil.getDataPropertyValue("EA_TrendNoTopTxnSize")),
				"uncatgeory txn header should not be displayed is not displayed");
		Assert.assertEquals(expenseTrend.EIATrendTopTxnDescriprion().size(),
				Integer.parseInt(PropsUtil.getDataPropertyValue("EA_TrendNoTopTxnSize")),
				"uncategorized txn date header is not displayed");
		Assert.assertEquals(expenseTrend.EIATrendTopTxnAccount().size(),
				Integer.parseInt(PropsUtil.getDataPropertyValue("EA_TrendNoTopTxnSize")),
				"uncategorized txn date header is not displayed");
		Assert.assertEquals(expenseTrend.EIATrendTopTxnCatgeory().size(),
				Integer.parseInt(PropsUtil.getDataPropertyValue("EA_TrendNoTopTxnSize")),
				"uncategorized txn date header is not displayed");
		Assert.assertEquals(expenseTrend.EIATrendTopTxnAmount().size(),
				Integer.parseInt(PropsUtil.getDataPropertyValue("EA_TrendNoTopTxnSize")),
				"uncategorized txn date headeris not displayed");

	}

	@Test(description = "verify no accounts page in Eexpanse Page", priority = 4, dependsOnMethods = "verifyNoTxn_IA")
	public void verifyNoData_IA() {

		expenseTrend.clickLegendMonth(Integer.parseInt(PropsUtil.getDataPropertyValue("EA_TrendTOHLC_MonthList")));
		Assert.assertEquals(expenseTrend.EIANoDataHeader().getText().trim(),
				PropsUtil.getDataPropertyValue("EA_NoDataHeaderLable"), "no data header is not displayed");
		Assert.assertEquals(expenseTrend.EIANoDataDescription().getText().trim(),
				PropsUtil.getDataPropertyValue("EA_NoDataDescription"), "no data description is not displayed");

		Assert.assertEquals(expenseTrend.EIAccountDropDown().getText().trim(),
				PropsUtil.getDataPropertyValue("IA_AllAccountDropDown"),
				"verify account dropwn is not displayed with all expense accounts lable");
		Assert.assertTrue(expenseTrend.EIATimeFilterDropDown().isDisplayed(), "verify  time filter is displaying");
		Assert.assertEquals(expenseTrend.EIATimeFilterDropDownLabel().getText(),
				PropsUtil.getDataPropertyValue("EA_ThisMonthTmeFilter"), "6 months label is not displayed");
		Assert.assertEquals(expenseTrend.EIATimeFilterDropDownDate().getText().trim(),
				txnAcct.monthStartDate(Integer.parseInt(PropsUtil.getDataPropertyValue("EA_HLC_To_Trend_TMStartDate")),
						"MM/dd/yyy")
						+ " " + "-" + " "
						+ add_Manual.targateDate1(
								Integer.parseInt(PropsUtil.getDataPropertyValue("EA_HLC_To_Trend_TMEndDate"))),
				"6 th month start date and current date is not displayed");
	}

}
