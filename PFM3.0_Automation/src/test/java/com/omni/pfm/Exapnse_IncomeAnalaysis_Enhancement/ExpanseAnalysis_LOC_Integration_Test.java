/**
* Copyright (c) 2018 Yodlee Inc. All Rights Reserved.
*
* This software is the confidential and proprietary information of
* Yodlee, Inc. Use is subject to license terms.
*
* @author Renuka K
*/
package com.omni.pfm.Exapnse_IncomeAnalaysis_Enhancement;

import java.util.List;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.omni.pfm.Accounts.Accounts_ViewByGroup_Loc;
import com.omni.pfm.PageProcessor.PageParser;
import com.omni.pfm.config.Config;
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

public class ExpanseAnalysis_LOC_Integration_Test extends TestBase {

	ExpLandingPage_Loc expLandingPage;
	Transaction_Filter_Loc filter;
	AccountAddition accAddition;
	Expense_Income_Trend_Loc expenseTrend;
	SeleniumUtil util;
	Transaction_AccountDropDown_Loc txnAcct;
	Add_Manual_Transaction_Loc add_Manual;
	Accounts_ViewByGroup_Loc groupView;

	@BeforeClass(alwaysRun = true)

	public void init() throws Exception {
		doInitialization("Expense Analysis");

		expLandingPage = new ExpLandingPage_Loc(d);
		groupView = new Accounts_ViewByGroup_Loc(d);
		accAddition = new AccountAddition();
		expenseTrend = new Expense_Income_Trend_Loc(d);
		util = new SeleniumUtil();
		txnAcct = new Transaction_AccountDropDown_Loc(d);
		add_Manual = new Add_Manual_Transaction_Loc(d);
		filter = new Transaction_Filter_Loc(d);
		LoginPage.loginMain(d, loginParameter);
		SeleniumUtil.waitForPageToLoad();
		accAddition.linkAccount();
		accAddition.addAggregatedAccount(PropsUtil.getDataPropertyValue("EA_LOC_DagSiteUserName"),
				PropsUtil.getDataPropertyValue("EA_LOC_DagSitePassword"));
		expenseTrend.pageRefresh();
		expenseTrend.navigateToEA();
		expenseTrend.FTUE();
		SeleniumUtil.waitForPageToLoad();
		this.addManualReundTxn();
		SeleniumUtil.waitFor(3);
	}

	@Test(description = "AT-97369:verify default trends LOC account data", priority = 1)
	public void verifydefaultTrendMonth() {
		String avgMsg = expenseTrend.getAvgAmountMessage(
				Integer.parseInt(PropsUtil.getDataPropertyValue("EA_Trend_DefaultTF_AvgAmountStart")),
				Integer.parseInt(PropsUtil.getDataPropertyValue("EA_Trend_DefaultTF_AvgAmountEnd")),
				PropsUtil.getDataPropertyValue("EA_Default6MonthAvgAmountMessage1"),
				PropsUtil.getDataPropertyValue("EA_LOC_Default6MonthAvgAmountMessage2"));
		/*
		 * List<String> ExpectedDiffPerValue = expenseTrend.totalAmountPer(
		 * util.getWebElementsValue(expenseTrend.EIATrendMonthAmount()),
		 * expenseTrend.EIATrendAvg().getText(),
		 * Integer.parseInt(PropsUtil.getDataPropertyValue(
		 * "EA_Trend_DefaultTF_getAvgAmount"))); List<String> actualDiffPerValue =
		 * util.getWebElementsValue(expenseTrend.EIATrendMonthAmountPer());
		 */

		util.assertExpectedActualList(util.getWebElementsValue(expenseTrend.EIATrendMonthList()),
				expenseTrend.getMonthsValues(
						Integer.parseInt(PropsUtil.getDataPropertyValue("EA_Trend_DefaultTF_getAvgAmount"))),
				"past 6 month name is not displayed in MMMM formate");
		util.assertExpectedActualAmountList(util.getWebElementsValue(expenseTrend.EIATrendMonthAmount()),
				PropsUtil.getDataPropertyValue("EA_LOC_Default6MonthAmount_List"),
				"all 6 month expense amount is not displayed");
		/*
		 * util.assertExpectedActualList(actualDiffPerValue, ExpectedDiffPerValue,
		 * "Total dif from avg and % values is not displayed");
		 */
		Assert.assertEquals(expenseTrend.EIATrendAvg().getText(), avgMsg, "lass 3 month avg is not displayed");

		if (Config.appFlag.equals("web") || Config.appFlag.equals("false")) {
			Assert.assertTrue(expenseTrend.EIATrendTopTxnCatgeory().size() == Integer
					.parseInt(PropsUtil.getDataPropertyValue("EA_12MonthTimeFilter_ToptxnSize")));
			util.assertExpectedActualAmountList(util.getWebElementsValue(expenseTrend.EIATrendTopTxnAmount()),
					PropsUtil.getDataPropertyValue("EA_LOC_12MonthTimeFilter_ToptxnAmount"),
					"top 5 txn amount is not displayed");
		}
	}

	@Test(description = "AT-97365:verify all accounts ", priority = 2) // , dependsOnMethods = "verifydefaultTrendMonth"
	public void verifyAcctDDownAllAcctsName() {
		expenseTrend.clickEIAccountDropDown();
		SeleniumUtil.waitForPageToLoad();
		util.assertExpectedActualList(util.getWebElementsValue(expenseTrend.EIAllAccountList()),
				PropsUtil.getDataPropertyValue("EA_LOC_AllAccountS"),
				"verify all added expense accounts are not displayed accounts dropdown");

	}

	@Test(description = "AT-97366:verify LOc account data", priority = 3) // , dependsOnMethods =
																			// "verifyAcctDDownAllAcctsName"

	public void verifyLOCAccountSelected() {
		expenseTrend.clickEIAllAccountCheckBox();
		SeleniumUtil.waitForPageToLoad();
		expenseTrend.selectAccount(PropsUtil.getDataPropertyValue("EA_LOC_SelectCashAccount"));
		if (expenseTrend.isSelectAcc_Donebtn()) {
			SeleniumUtil.click(expenseTrend.SelectAcc_Donebtn());
		}
		SeleniumUtil.waitForPageToLoad();
		String avgMsg = expenseTrend.getAvgAmountMessage(
				Integer.parseInt(PropsUtil.getDataPropertyValue("EA_Trend_DefaultTF_AvgAmountStart")),
				Integer.parseInt(PropsUtil.getDataPropertyValue("EA_Trend_DefaultTF_AvgAmountEnd")),
				PropsUtil.getDataPropertyValue("EA_Default6MonthAvgAmountMessage1"),
				PropsUtil.getDataPropertyValue("EA_LOC_AvgAmountMessage2"));
		List<String> ExpectedDiffPerValue = expenseTrend.totalAmountPerIncome1(
				util.getWebElementsValue(expenseTrend.EIATrendMonthAmount()), expenseTrend.EIATrendAvg().getText(),
				Integer.parseInt(PropsUtil.getDataPropertyValue("EA_Trend_DefaultTF_getAvgAmount")));
		List<String> actualDiffPerValue = util.getWebElementsValue(expenseTrend.EIATrendMonthAmountPer());

		int top5txnSize = expenseTrend.EIATrendTopTxnAccount().size();
		int toptxnCount = 0;
		for (int i = 0; i < top5txnSize; i++) {
			if (expenseTrend.EIATrendTopTxnAccount().get(i).getText().trim()
					.equals(PropsUtil.getDataPropertyValue("EA_LOC_ToptxnAccount"))) {
				toptxnCount = toptxnCount + 1;
			}
		}
		util.assertExpectedActualList(util.getWebElementsValue(expenseTrend.EIATrendMonthList()),
				expenseTrend.getMonthsValues(
						Integer.parseInt(PropsUtil.getDataPropertyValue("EA_Trend_DefaultTF_getAvgAmount"))),
				"past 6 month name is not displayed in MMMM formate");
		util.assertExpectedActualAmountList(util.getWebElementsValue(expenseTrend.EIATrendMonthAmount()),
				PropsUtil.getDataPropertyValue("EA_LOC_6monthData"), "all 6 month expense amount is not displayed");
		util.assertExpectedActualList(actualDiffPerValue, ExpectedDiffPerValue,
				"Total dif from avg and % values is not displayed");
		Assert.assertEquals(expenseTrend.EIATrendAvg().getText(), avgMsg, "lass 3 month avg is not displayed");
		if (Config.appFlag.equals("web") || Config.appFlag.equals("false")) {

			Assert.assertTrue(expenseTrend.EIATrendTopTxnCatgeory().size() == Integer
					.parseInt(PropsUtil.getDataPropertyValue("EA_12MonthTimeFilter_ToptxnSize")));
			Assert.assertTrue(
					toptxnCount == Integer.parseInt(PropsUtil.getDataPropertyValue("EA_12MonthTimeFilter_ToptxnSize")));
			util.assertExpectedActualAmountList(util.getWebElementsValue(expenseTrend.EIATrendTopTxnAmount()),
					PropsUtil.getDataPropertyValue("EA_LOC_ToptxnAmount"), "top 5 txn amount is not displayed");
		}
	}

	@Test(description = "AT-97366:verify HELOC account data", priority = 4) // , dependsOnMethods =
																			// "verifyLOCAccountSelected"
	public void verifyHELOCAccountSelected() {
		expenseTrend.selectMultiAccount(PropsUtil.getDataPropertyValue("EA_HELOC_SelectCashAccount"));
		SeleniumUtil.waitForPageToLoad(3000);
		String avgMsg = expenseTrend.getAvgAmountMessage(
				Integer.parseInt(PropsUtil.getDataPropertyValue("EA_Trend_DefaultTF_AvgAmountStart")),
				Integer.parseInt(PropsUtil.getDataPropertyValue("EA_Trend_DefaultTF_AvgAmountEnd")),
				PropsUtil.getDataPropertyValue("EA_Default6MonthAvgAmountMessage1"),
				PropsUtil.getDataPropertyValue("EA_HELOC_AvgAmountMessage2"));
		List<String> ExpectedDiffPerValue = expenseTrend.totalAmountPer(
				util.getWebElementsValue(expenseTrend.EIATrendMonthAmount()), expenseTrend.EIATrendAvg().getText(),
				Integer.parseInt(PropsUtil.getDataPropertyValue("EA_Trend_DefaultTF_getAvgAmount")));
		List<String> actualDiffPerValue = util.getWebElementsValue(expenseTrend.EIATrendMonthAmountPer());
		int top5txnSize = expenseTrend.EIATrendTopTxnAccount().size();
		int toptxnCount = 0;
		for (int i = 0; i < top5txnSize; i++) {
			if (expenseTrend.EIATrendTopTxnAccount().get(i).getText().trim()
					.equals(PropsUtil.getDataPropertyValue("EA_HELOC_ToptxnAccount"))) {
				toptxnCount = toptxnCount + 1;
			}
		}
		util.assertExpectedActualList(util.getWebElementsValue(expenseTrend.EIATrendMonthList()),
				expenseTrend.getMonthsValues(
						Integer.parseInt(PropsUtil.getDataPropertyValue("EA_Trend_DefaultTF_getAvgAmount"))),
				"past 6 month name is not displayed in MMMM formate");
		util.assertExpectedActualAmountList(util.getWebElementsValue(expenseTrend.EIATrendMonthAmount()),
				PropsUtil.getDataPropertyValue("EA_HELOC_6monthData"), "all 6 month expense amount is not displayed");
		util.assertExpectedActualList(actualDiffPerValue, ExpectedDiffPerValue,
				"Total dif from avg and % values is not displayed");
		Assert.assertEquals(expenseTrend.EIATrendAvg().getText(), avgMsg, "lass 3 month avg is not displayed");
		if (Config.appFlag.equals("web") || Config.appFlag.equals("false")) {

			Assert.assertTrue(expenseTrend.EIATrendTopTxnCatgeory().size() == Integer
					.parseInt(PropsUtil.getDataPropertyValue("EA_12MonthTimeFilter_ToptxnSize")));
			Assert.assertTrue(
					toptxnCount == Integer.parseInt(PropsUtil.getDataPropertyValue("EA_12MonthTimeFilter_ToptxnSize")));

			util.assertExpectedActualAmountList(util.getWebElementsValue(expenseTrend.EIATrendTopTxnAmount()),
					PropsUtil.getDataPropertyValue("EA_HELOC_ToptxnAmount"), "top 5 txn amount is not displayed");
		}
	}

	@Test(description = "AT-97366:Verify SBLOC account data", priority = 5) // , dependsOnMethods =
																			// "verifyHELOCAccountSelected"
	public void verifySBLOCAccountSelected() {
		expenseTrend.selectMultiAccount(PropsUtil.getDataPropertyValue("EA_SBLOC_SelectCashAccount"));
//		if(expenseTrend.isSelectAcc_Donebtn()) {
//			SeleniumUtil.click(expenseTrend.SelectAcc_Donebtn());
//			SeleniumUtil.waitForPageToLoad();
//			}
		SeleniumUtil.waitForPageToLoad();
		String avgMsg = expenseTrend.getAvgAmountMessage(
				Integer.parseInt(PropsUtil.getDataPropertyValue("EA_Trend_DefaultTF_AvgAmountStart")),
				Integer.parseInt(PropsUtil.getDataPropertyValue("EA_Trend_DefaultTF_AvgAmountEnd")),
				PropsUtil.getDataPropertyValue("EA_Default6MonthAvgAmountMessage1"),
				PropsUtil.getDataPropertyValue("EA_SBLOC_AvgAmountMessage2"));
		SeleniumUtil.waitFor(5);
		List<String> ExpectedDiffPerValue = expenseTrend.totalAmountPerIncome1(
				util.getWebElementsValue(expenseTrend.EIATrendMonthAmount()), expenseTrend.EIATrendAvg().getText(),
				Integer.parseInt(PropsUtil.getDataPropertyValue("EA_Trend_DefaultTF_getAvgAmount")));
		List<String> actualDiffPerValue = util.getWebElementsValue(expenseTrend.EIATrendMonthAmountPer());
		int top5txnSize = expenseTrend.EIATrendTopTxnAccount().size();
		int toptxnCount = 0;
		for (int i = 0; i < top5txnSize; i++) {
			if (expenseTrend.EIATrendTopTxnAccount().get(i).getText().trim()
					.equals(PropsUtil.getDataPropertyValue("EA_SBLOC_ToptxnAccount"))) {
				toptxnCount = toptxnCount + 1;
			}

		}
		util.assertExpectedActualList(util.getWebElementsValue(expenseTrend.EIATrendMonthList()),
				expenseTrend.getMonthsValues(
						Integer.parseInt(PropsUtil.getDataPropertyValue("EA_Trend_DefaultTF_getAvgAmount"))),
				"past 6 month name is not displayed in MMMM formate");
		util.assertExpectedActualAmountList(util.getWebElementsValue(expenseTrend.EIATrendMonthAmount()),
				PropsUtil.getDataPropertyValue("EA_SBLOC_6monthData"), "all 6 month expense amount is not displayed");
		util.assertExpectedActualList(actualDiffPerValue, ExpectedDiffPerValue,
				"Total dif from avg and % values is not displayed");
		Assert.assertEquals(expenseTrend.EIATrendAvg().getText(), avgMsg, "lass 3 month avg is not displayed");
		if (Config.appFlag.equals("web") || Config.appFlag.equals("false")) {

			Assert.assertTrue(expenseTrend.EIATrendTopTxnCatgeory().size() == Integer
					.parseInt(PropsUtil.getDataPropertyValue("EA_12MonthTimeFilter_ToptxnSize")));
			util.assertExpectedActualAmountList(util.getWebElementsValue(expenseTrend.EIATrendTopTxnAmount()),
					PropsUtil.getDataPropertyValue("EA_SBLOC_ToptxnAmount"), "top 5 txn amount is not displayed");
		}
	}

	@Test(description = "AT-97366:verify time filter 3 month data", priority = 6)

	public void TimeFilter3month() {
		expenseTrend.navigateToEA();
		SeleniumUtil.waitForPageToLoad();
		expenseTrend.selectTimeFilter(PropsUtil.getDataPropertyValue("EA_3monthsTimeFilter"));
		SeleniumUtil.waitForPageToLoad(8000);
		String avgMsg = expenseTrend.getAvgAmountMessage(
				Integer.parseInt(PropsUtil.getDataPropertyValue("EA_Trend_DefaultTF_AvgAmountStart")),
				Integer.parseInt(PropsUtil.getDataPropertyValue("EA_Trend_DefaultTF_AvgAmountEnd")),
				PropsUtil.getDataPropertyValue("EA_Default6MonthAvgAmountMessage1"),
				PropsUtil.getDataPropertyValue("EA_LOCTF_AvgAmountMessage2"));
		List<String> ExpectedDiffPerValue = expenseTrend.totalAmountPer(
				util.getWebElementsValue(expenseTrend.EIATrendMonthAmount()), expenseTrend.EIATrendAvg().getText(),
				Integer.parseInt(PropsUtil.getDataPropertyValue("EA_Trend_3monthTF_getAvgAmount")));
		List<String> actualDiffPerValue = util.getWebElementsValue(expenseTrend.EIATrendMonthAmountPer());
		util.assertExpectedActualList(util.getWebElementsValue(expenseTrend.EIATrendMonthList()),
				expenseTrend.getMonthsValues(
						Integer.parseInt(PropsUtil.getDataPropertyValue("EA_Trend_3monthTF_getAvgAmount"))),
				"past 6 month name is not displayed in MMMM formate");
		util.assertExpectedActualAmountList(util.getWebElementsValue(expenseTrend.EIATrendMonthAmount()),
				PropsUtil.getDataPropertyValue("EA_LOCTF_6monthData"), "all 6 month expense amount is not displayed");
		util.assertExpectedActualList(actualDiffPerValue, ExpectedDiffPerValue,
				"Total dif from avg and % values is not displayed");
		Assert.assertEquals(expenseTrend.EIATrendAvg().getText(), avgMsg, "lass 3 month avg is not displayed");
		if (Config.appFlag.equals("web") || Config.appFlag.equals("false")) {

			Assert.assertTrue(expenseTrend.EIATrendTopTxnCatgeory().size() == Integer
					.parseInt(PropsUtil.getDataPropertyValue("EA_12MonthTimeFilter_ToptxnSize")));
			util.assertExpectedActualAmountList(util.getWebElementsValue(expenseTrend.EIATrendTopTxnAmount()),
					PropsUtil.getDataPropertyValue("EA_LOCTF_ToptxnAmount"), "top 5 txn amount is not displayed");
		}
	}

	@Test(description = "AT-97367:verify LOC account group filter data", priority = 7)
	public void groupFilter() throws Exception {
		d.navigate().refresh();
		SeleniumUtil.waitForPageToLoad();
		PageParser.forceNavigate("AccountsPage", d);
		SeleniumUtil.waitForPageToLoad(10000);
		groupView.clikcCreatgroup();
		groupView.createGroup(PropsUtil.getDataPropertyValue("EA_LOC_GroupName"),
				PropsUtil.getDataPropertyValue("EA_LOC_GroupAccount").split(","));
		SeleniumUtil.waitForPageToLoad();
		expenseTrend.navigateToEA();
		SeleniumUtil.waitFor(2);
		expenseTrend.clickGroup(PropsUtil.getDataPropertyValue("EA_LOC_GroupName"));
		if (expenseTrend.isSelectAcc_Donebtn()) {
			SeleniumUtil.click(expenseTrend.SelectAcc_Donebtn());
		}
		SeleniumUtil.waitForPageToLoad();
		String avgMsg = expenseTrend.getAvgAmountMessage(
				Integer.parseInt(PropsUtil.getDataPropertyValue("EA_Trend_DefaultTF_AvgAmountStart")),
				Integer.parseInt(PropsUtil.getDataPropertyValue("EA_Trend_DefaultTF_AvgAmountEnd")),
				PropsUtil.getDataPropertyValue("EA_Default6MonthAvgAmountMessage1"),
				PropsUtil.getDataPropertyValue("EA_LOCGroup_AvgAmountMessage2"));
		List<String> ExpectedDiffPerValue = expenseTrend.totalAmountPer(
				util.getWebElementsValue(expenseTrend.EIATrendMonthAmount()), expenseTrend.EIATrendAvg().getText(),
				Integer.parseInt(PropsUtil.getDataPropertyValue("EA_Trend_DefaultTF_getAvgAmount")));
		List<String> actualDiffPerValue = util.getWebElementsValue(expenseTrend.EIATrendMonthAmountPer());
		util.assertExpectedActualList(util.getWebElementsValue(expenseTrend.EIATrendMonthList()),
				expenseTrend.getMonthsValues(
						Integer.parseInt(PropsUtil.getDataPropertyValue("EA_Trend_DefaultTF_getAvgAmount"))),
				"past 6 month name is not displayed in MMMM formate");
		util.assertExpectedActualAmountList(util.getWebElementsValue(expenseTrend.EIATrendMonthAmount()),
				PropsUtil.getDataPropertyValue("EA_LOCGroup_6monthData"),
				"all 6 month expense amount is not displayed");
		util.assertExpectedActualList(actualDiffPerValue, ExpectedDiffPerValue,
				"Total dif from avg and % values is not displayed");
		Assert.assertEquals(expenseTrend.EIATrendAvg().getText(), avgMsg, "lass 3 month avg is not displayed");
		if (Config.appFlag.equals("web") || Config.appFlag.equals("false")) {

			Assert.assertTrue(expenseTrend.EIATrendTopTxnCatgeory().size() == Integer
					.parseInt(PropsUtil.getDataPropertyValue("EA_12MonthTimeFilter_ToptxnSize")));
			util.assertExpectedActualAmountList(util.getWebElementsValue(expenseTrend.EIATrendTopTxnAmount()),
					PropsUtil.getDataPropertyValue("EA_LOCGroup_ToptxnAmount"), "top 5 txn amount is not displayed");
		}
	}

	@Test(description = "AT-97370:verify HLC page LOC account data", priority = 8)
	public void VerifyHLCData() throws Exception {
		expenseTrend.navigateToEA();
		SeleniumUtil.waitForPageToLoad();
		expenseTrend.clickLegendMonth(Integer.parseInt(PropsUtil.getDataPropertyValue("EA_TrendTOHLC_MonthList")));
		SeleniumUtil.waitForPageToLoad(8000);
		if (Config.appFlag.equals("app") || Config.appFlag.equals("emulator")) {
			SeleniumUtil.click(expenseTrend.UncatAccTxn());
		}
		int uncatTxnSize = expenseTrend.EIAHLCCatUnCatTxnAccount().size();
		int LOCTxnccount = 0;
		int HELOCtxnCount = 0;
		int SBLOCTxncount = 0;
		for (int i = 0; i < uncatTxnSize; i++) {
			System.out.println(expenseTrend.EIAHLCCatUnCatTxnAccount().get(i).getText());
			if (expenseTrend.EIAHLCCatUnCatTxnAccount().get(i).getText().trim().replaceAll("\n", " ")
					.equals(PropsUtil.getDataPropertyValue("EA_LOC_UncatTxnAccount"))) {
				LOCTxnccount = LOCTxnccount + 1;
			} else if (expenseTrend.EIAHLCCatUnCatTxnAccount().get(i).getText().trim().replaceAll("\n", " ")
					.equals(PropsUtil.getDataPropertyValue("EA_HELOC_UncatTxnAccount"))) {
				HELOCtxnCount = HELOCtxnCount + 1;
			} else if (expenseTrend.EIAHLCCatUnCatTxnAccount().get(i).getText().trim().replaceAll("\n", " ")
					.equals(PropsUtil.getDataPropertyValue("EA_SBLOC_UncatTxnAccount"))) {
				SBLOCTxncount = SBLOCTxncount + 1;
			}
		}
		Assert.assertTrue(LOCTxnccount >= Integer.parseInt(PropsUtil.getDataPropertyValue("EA_LOC_UncatTxnSize")));
		Assert.assertTrue(HELOCtxnCount >= Integer.parseInt(PropsUtil.getDataPropertyValue("EA_LOC_UncatTxnSize")));
		Assert.assertTrue(SBLOCTxncount >= Integer.parseInt(PropsUtil.getDataPropertyValue("EA_LOC_UncatTxnSize")));
		SeleniumUtil.waitForPageToLoad();
		if (expenseTrend.isEIAcloseTransPopup()) {
			SeleniumUtil.click(expenseTrend.EIAcloseTransPopup());
		}
		Assert.assertEquals(expenseTrend.EIAHLCCatCharValue().getText().trim(),
				PropsUtil.getDataPropertyValue("EA_AllAccountHLC_TotalExpenseValue1"),
				"total expense amount is not displayed");
		util.assertExpectedActualList(util.getWebElementsValue(expenseTrend.EIAHLCCatNameList()),
				PropsUtil.getDataPropertyValue("EA_AllAccountHLC_CatName"),
				"all 11 HLc category name is not displayed");
		util.assertExpectedActualAmountList(util.getWebElementsValue(expenseTrend.EIAHLCCatPerList()),
				PropsUtil.getDataPropertyValue("EA_AllAccountHLC_CatPerAmount"),
				"all HLc actegory is not displayed % amount");
		util.assertExpectedActualAmountList(util.getWebElementsValue(expenseTrend.EIAHLCCatAmountList()),
				PropsUtil.getDataPropertyValue("EA_AllAccountHLC_CatAmount"),
				"all HLc catgeory is not displayed with expense amountamount");
		Assert.assertEquals(expenseTrend.EIAHLCCatRefundAmount().getText(),
				PropsUtil.getDataPropertyValue("EA_AllAccountHLC_CatRefund"), "total HLC refund is not displayed");

	}

	@Test(description = "AT-97372:verify HLC page account drop down", priority = 10) // , dependsOnMethods =
																						// "VerifyHLCData"
	public void VerifyHLCAccountDropDown() throws Exception {
		expenseTrend.clickEIAccountDropDown();
		expenseTrend.clickAccountLink();

		util.assertExpectedActualList(util.getWebElementsValue(expenseTrend.EIAllAccountList()),
				PropsUtil.getDataPropertyValue("EA_LOC_AllAccountS"),
				"verify all added expense accounts are not displayed in accounts dropdown");
	}

	@Test(description = "AT-97372,AT-97374:verify HLC page LOC account data", priority = 11) // , dependsOnMethods =
																								// "VerifyHLCAccountDropDown"
	public void VerifyLOCAccountHLCData() throws Exception {
		// expenseTrend.clickEIAllAccountCheckBox();
		SeleniumUtil.waitForPageToLoad(5000);
		expenseTrend.selectAccount(PropsUtil.getDataPropertyValue("EA_LOC_SelectCashAccount"));
		if (expenseTrend.isSelectAcc_Donebtn()) {
			SeleniumUtil.click(expenseTrend.SelectAcc_Donebtn());
			SeleniumUtil.waitForPageToLoad();
		}

		SeleniumUtil.waitForPageToLoad(8000);
		if (Config.appFlag.equals("app") || Config.appFlag.equals("emulator")) {
			SeleniumUtil.click(expenseTrend.UncatAccTxn());
		}
		int uncatTxnSize = expenseTrend.EIAHLCCatUnCatTxnAccount().size();
		int LOCTxnccount = 0;

		for (int i = 0; i < uncatTxnSize; i++) {
			if (expenseTrend.EIAHLCCatUnCatTxnAccount().get(i).getText().trim().replaceAll("\n", " ")
					.equals(PropsUtil.getDataPropertyValue("EA_LOC_UncatTxnAccount"))) {
				LOCTxnccount = LOCTxnccount + 1;
			}
		}
		Assert.assertTrue(LOCTxnccount >= Integer.parseInt(PropsUtil.getDataPropertyValue("EA_LOC_UncatTxnSize")));
		if (expenseTrend.isEIAcloseTransPopup()) {
			SeleniumUtil.click(expenseTrend.EIAcloseTransPopup());
		}
		SeleniumUtil.waitForPageToLoad();
		Assert.assertEquals(expenseTrend.EIAHLCCatCharValue().getText().trim(),
				PropsUtil.getDataPropertyValue("EA_LOCHLC_TotalExpenseValue1"),
				"total expense amount is not displayed");
		util.assertExpectedActualList(util.getWebElementsValue(expenseTrend.EIAHLCCatNameList()),
				PropsUtil.getDataPropertyValue("EA_LOCHLC_CatName"), "all 11 HLc category name is not displayed");
		util.assertExpectedActualAmountList(util.getWebElementsValue(expenseTrend.EIAHLCCatPerList()),
				PropsUtil.getDataPropertyValue("EA_LOCHLC_CatPerAmount"), "all HLc actegory is not displayed % amount");
		util.assertExpectedActualAmountList(util.getWebElementsValue(expenseTrend.EIAHLCCatAmountList()),
				PropsUtil.getDataPropertyValue("EA_LOCHLC_CatAmount"),
				"all HLc catgeory is not displayed with expense amountamount");
		Assert.assertEquals(expenseTrend.EIAHLCCatRefundAmount().getText(),
				PropsUtil.getDataPropertyValue("EA_LOCHLC_CatRefund"), "total HLC refund is not displayed");
	}

	@Test(description = "AT-97372,AT-97374:verify HLC page HELOC account data", priority = 12) // , dependsOnMethods =
																								// "VerifyLOCAccountHLCData"
	public void VerifyHELOCAccountHLCData() throws Exception {
		expenseTrend.selectMultiAccount(PropsUtil.getDataPropertyValue("EA_HELOC_SelectCashAccount"));
		// if(expenseTrend.isSelectAcc_Donebtn()) {
		// SeleniumUtil.click(expenseTrend.SelectAcc_Donebtn());
		// SeleniumUtil.waitForPageToLoad();
		// }
		SeleniumUtil.waitForPageToLoad();
		if (Config.appFlag.equals("app") || Config.appFlag.equals("emulator")) {
			SeleniumUtil.click(expenseTrend.UncatAccTxn());
		}
		int uncatTxnSize = expenseTrend.EIAHLCCatUnCatTxnAccount().size();
		int HELOCtxnCount = 0;

		for (int i = 0; i < uncatTxnSize; i++) {
			if (expenseTrend.EIAHLCCatUnCatTxnAccount().get(i).getText().trim().replaceAll("\n", " ")
					.equals(PropsUtil.getDataPropertyValue("EA_HELOC_UncatTxnAccount"))) {
				HELOCtxnCount = HELOCtxnCount + 1;
			}
		}
		Assert.assertTrue(HELOCtxnCount >= Integer.parseInt(PropsUtil.getDataPropertyValue("EA_LOC_UncatTxnSize")));
		if (expenseTrend.isEIAcloseTransPopup()) {
			SeleniumUtil.click(expenseTrend.EIAcloseTransPopup());
		}
		Assert.assertEquals(expenseTrend.EIAHLCCatCharValue().getText().trim(),
				PropsUtil.getDataPropertyValue("EA_HELOCHLC_TotalExpenseValue1"),
				"total expense amount is not displayed");
		util.assertExpectedActualList(util.getWebElementsValue(expenseTrend.EIAHLCCatNameList()),
				PropsUtil.getDataPropertyValue("EA_HELOCHLC_CatName"), "all 11 HLc category name is not displayed");
		util.assertExpectedActualAmountList(util.getWebElementsValue(expenseTrend.EIAHLCCatPerList()),
				PropsUtil.getDataPropertyValue("EA_HELOCHLC_CatPerAmount"),
				"all HLc actegory is not displayed % amount");
		util.assertExpectedActualAmountList(util.getWebElementsValue(expenseTrend.EIAHLCCatAmountList()),
				PropsUtil.getDataPropertyValue("EA_HELOCHLC_CatAmount"),
				"all HLc catgeory is not displayed with expense amountamount");
		Assert.assertEquals(expenseTrend.EIAHLCCatRefundAmount().getText(),
				PropsUtil.getDataPropertyValue("EA_HELOCHLC_CatRefund"), "total HLC refund is not displayed");
	}

	@Test(description = "AT-97372,AT-97374:verify HLC page SBLOC account data", priority = 13) // , dependsOnMethods =
																								// "VerifyHELOCAccountHLCData"
	public void VerifySBLOCAccountHLCData() throws Exception {
		expenseTrend.selectMultiAccount(PropsUtil.getDataPropertyValue("EA_SBLOC_SelectCashAccount"));
		// if(expenseTrend.isSelectAcc_Donebtn()) {
		// SeleniumUtil.click(expenseTrend.SelectAcc_Donebtn());
		// SeleniumUtil.waitForPageToLoad();
		// }
		SeleniumUtil.waitForPageToLoad();
		if (Config.appFlag.equals("app") || Config.appFlag.equals("emulator")) {
			SeleniumUtil.click(expenseTrend.UncatAccTxn());
		}
		int uncatTxnSize = expenseTrend.EIAHLCCatUnCatTxnAccount().size();
		int SBLOCtxnCount = 0;

		for (int i = 0; i < uncatTxnSize; i++) {
			if (expenseTrend.EIAHLCCatUnCatTxnAccount().get(i).getText().trim().replaceAll("\n", " ")
					.equals(PropsUtil.getDataPropertyValue("EA_SBLOC_UncatTxnAccount"))) {
				SBLOCtxnCount = SBLOCtxnCount + 1;
			}

		}
		Assert.assertTrue(SBLOCtxnCount >= Integer.parseInt(PropsUtil.getDataPropertyValue("EA_LOC_UncatTxnSize")));
		if (expenseTrend.isEIAcloseTransPopup()) {
			SeleniumUtil.click(expenseTrend.EIAcloseTransPopup());
		}
		Assert.assertEquals(expenseTrend.EIAHLCCatCharValue().getText().trim(),
				PropsUtil.getDataPropertyValue("EA_SBLOCHLC_TotalExpenseValue1"),
				"total expense amount is not displayed");
		util.assertExpectedActualList(util.getWebElementsValue(expenseTrend.EIAHLCCatNameList()),
				PropsUtil.getDataPropertyValue("EA_SBLOCHLC_CatName"), "all 11 HLc category name is not displayed");
		util.assertExpectedActualAmountList(util.getWebElementsValue(expenseTrend.EIAHLCCatPerList()),
				PropsUtil.getDataPropertyValue("EA_SBLOCHLC_CatPerAmount"),
				"all HLc actegory is not displayed % amount");
		util.assertExpectedActualAmountList(util.getWebElementsValue(expenseTrend.EIAHLCCatAmountList()),
				PropsUtil.getDataPropertyValue("EA_SBLOCHLC_CatAmount"),
				"all HLc catgeory is not displayed with expense amountamount");

	}

	@Test(description = "AT-97372,AT-97375:verify MLC page LOC account data", priority = 14)
	public void VerifyLOCAccountMLCData() throws Exception {
		expenseTrend.navigateToEA();
		SeleniumUtil.waitForPageToLoad();
		expenseTrend.clickEAHLC(PropsUtil.getDataPropertyValue("EA_LOCMLC_MLC_HLC1"),
				Integer.parseInt(PropsUtil.getDataPropertyValue("EA_TrendTOHLC_MonthList")));
		if (Config.appFlag.equals("app") || Config.appFlag.equals("emulator")) {
			SeleniumUtil.click(expenseTrend.UncatAccTxn());
		}
		int LOCTxnccount = 0;
		int HELOCtxnCount = 0;
		int SBLOCTxncount = 0;
		int uncatTxnSize = expenseTrend.EIAMLCHeaderTxnAccountList().size();
		for (int i = 0; i < uncatTxnSize; i++) {
			if (expenseTrend.EIAMLCHeaderTxnAccountList().get(i).getText().trim().replaceAll("\n", " ")
					.equals(PropsUtil.getDataPropertyValue("EA_LOC_UncatTxnAccount"))) {
				LOCTxnccount = LOCTxnccount + 1;
			} else if (expenseTrend.EIAMLCHeaderTxnAccountList().get(i).getText().trim().replaceAll("\n", " ")
					.equals(PropsUtil.getDataPropertyValue("EA_HELOC_UncatTxnAccount"))) {
				HELOCtxnCount = HELOCtxnCount + 1;
			} else if (expenseTrend.EIAMLCHeaderTxnAccountList().get(i).getText().trim().replaceAll("\n", " ")
					.equals(PropsUtil.getDataPropertyValue("EA_SBLOC_UncatTxnAccount"))) {
				SBLOCTxncount = SBLOCTxncount + 1;
			}
		}
		Assert.assertTrue(LOCTxnccount >= Integer.parseInt(PropsUtil.getDataPropertyValue("EA_LOC_UncatTxnSize1")));
		Assert.assertTrue(LOCTxnccount >= Integer.parseInt(PropsUtil.getDataPropertyValue("EA_LOC_UncatTxnSize1")));
		Assert.assertTrue(LOCTxnccount >= Integer.parseInt(PropsUtil.getDataPropertyValue("EA_LOC_UncatTxnSize1")));
		if (expenseTrend.isEIAcloseTransPopup()) {
			SeleniumUtil.click(expenseTrend.EIAcloseTransPopup());
		}
		Assert.assertEquals(expenseTrend.EIAHLCCatCharValue().getText().trim(),
				PropsUtil.getDataPropertyValue("EA_LOCMLC_HLCSpendValue"), "total expense amount is not displayed");
		util.assertExpectedActualList(util.getWebElementsValue(expenseTrend.EIAMLCCatNameList()),
				PropsUtil.getDataPropertyValue("EA_LOCMLC_List1"), "all 11 HLc category name is not displayed");
		util.assertExpectedActualAmountList(util.getWebElementsValue(expenseTrend.EIAMLCCatPerList()),
				PropsUtil.getDataPropertyValue("EA_LOCMLC_PerList1"), "all HLc category is not displayed % amount");
		util.assertExpectedActualAmountList(util.getWebElementsValue(expenseTrend.EIAMLCCatAmountList()),
				PropsUtil.getDataPropertyValue("EA_LOCMLC_AmountList1"),
				"all HLc category is not displayed with expense amountamount");

	}

	@Test(description = "AT-97373:verify Trend popup LOC account data", priority = 15) // , dependsOnMethods =
																						// "VerifyLOCAccountMLCData"
	public void VerifyLOCAccountTrendPopup() throws Exception {
		expenseTrend.clickTrendPopUp();
		SeleniumUtil.waitForPageToLoad();
		util.assertExpectedActualAmountList(util.getWebElementsValue(expenseTrend.EIATrendMonthAmount()),
				PropsUtil.getDataPropertyValue("EA_LOCMLC_TrendPopUpAmount"),
				"all 6 month expense amount is not displayed");

		String avgMsg = expenseTrend.getAvgAmountMessage(
				Integer.parseInt(PropsUtil.getDataPropertyValue("EA_Trend_DefaultTF_AvgAmountStart")),
				Integer.parseInt(PropsUtil.getDataPropertyValue("EA_Trend_DefaultTF_AvgAmountEnd")),
				PropsUtil.getDataPropertyValue("EA_Default6MonthAvgAmountMessage1"),
				PropsUtil.getDataPropertyValue("EA_LOCMLC_TrendPopUpAvgMesg"));
		Assert.assertEquals(expenseTrend.EIATrendAvg().getText(), avgMsg, "lass 3 month avg is not displayed");
	}

	@Test(description = "AT-97373:verify txn popup LOC Account data", priority = 16)
	public void VerifyLOCAccountTxnPopup() throws Exception {
		SeleniumUtil.click(expenseTrend.EIAMLCTrendPopUpClose());
		expenseTrend.navigateToEA();
		SeleniumUtil.waitForPageToLoad(5000);
		expenseTrend.clickEAHLC(PropsUtil.getDataPropertyValue("EA_LOCMLC_MLC_HLC2"),
				Integer.parseInt(PropsUtil.getDataPropertyValue("EA_TrendTOHLC_MonthList")));
		SeleniumUtil.waitForPageToLoad();
		SeleniumUtil.click(expenseTrend.EIAMLCCatName(PropsUtil.getDataPropertyValue("EA_LOCMLC_MLC1")));
		SeleniumUtil.waitForPageToLoad();
		if (Config.appFlag.equals("app") || Config.appFlag.equals("emulator")) {
			SeleniumUtil.click(expenseTrend.UncatAccTxn());
		}
		int txnSize = expenseTrend.EIAMLCTxnPopUpAccountList().size();
		int LOCTxnccount = 0;
		int HELOCtxnCount = 0;
		int SBLOCTxncount = 0;

		for (int i = 0; i < txnSize; i++) {
			if (expenseTrend.EIAMLCTxnPopUpAccountList().get(i).getText().trim().replaceAll("\n", " ")
					.equals(PropsUtil.getDataPropertyValue("EA_LOC_UncatTxnAccount"))) {
				LOCTxnccount = LOCTxnccount + 1;
			} else if (expenseTrend.EIAMLCTxnPopUpAccountList().get(i).getText().trim().replaceAll("\n", " ")
					.equals(PropsUtil.getDataPropertyValue("EA_HELOC_UncatTxnAccount"))) {
				HELOCtxnCount = HELOCtxnCount + 1;
			} else if (expenseTrend.EIAMLCTxnPopUpAccountList().get(i).getText().trim().replaceAll("\n", " ")
					.equals(PropsUtil.getDataPropertyValue("EA_SBLOC_UncatTxnAccount"))) {
				SBLOCTxncount = SBLOCTxncount + 1;
			}
		}

		Assert.assertTrue(LOCTxnccount >= Integer.parseInt(PropsUtil.getDataPropertyValue("EA_LOC_UncatTxnSize")));
		Assert.assertTrue(LOCTxnccount >= Integer.parseInt(PropsUtil.getDataPropertyValue("EA_LOC_UncatTxnSize")));
		Assert.assertTrue(LOCTxnccount >= Integer.parseInt(PropsUtil.getDataPropertyValue("EA_LOC_UncatTxnSize")));
		if (expenseTrend.isEIAcloseTransPopup()) {
			SeleniumUtil.click(expenseTrend.EIAcloseTransPopup());
		}
	}

	@Test(description = "AT-97373:verify refund page LOC account data", priority = 17) // , dependsOnMethods =
																						// "VerifyLOCAccountTrendPopup"
	public void VerifyLOCAccountRefundTxnTxn() throws Exception {
		SeleniumUtil.click(expenseTrend.EIAHLCCatRefundAmount());
		if (Config.appFlag.equals("app") || Config.appFlag.equals("emulator")) {
			SeleniumUtil.click(expenseTrend.UncatAccTxn());
		}
		int txnSize = expenseTrend.EIARefundTXnAccount().size();
		int LOCTxnccount = 0;
		int HELOCtxnCount = 0;
		int SBLOCTxncount = 0;

		for (int i = 0; i < txnSize; i++) {
			if (expenseTrend.EIARefundTXnAccount().get(i).getText().trim().replaceAll("\n", " ")
					.equals(PropsUtil.getDataPropertyValue("EA_LOC_UncatTxnAccount"))) {
				LOCTxnccount = LOCTxnccount + 1;
			} else if (expenseTrend.EIARefundTXnAccount().get(i).getText().trim().replaceAll("\n", " ")
					.equals(PropsUtil.getDataPropertyValue("EA_HELOC_UncatTxnAccount"))) {
				HELOCtxnCount = HELOCtxnCount + 1;
			} else if (expenseTrend.EIARefundTXnAccount().get(i).getText().trim().replaceAll("\n", " ")
					.equals(PropsUtil.getDataPropertyValue("EA_SBLOC_UncatTxnAccount"))) {
				SBLOCTxncount = SBLOCTxncount + 1;
			}
		}

		Assert.assertTrue(LOCTxnccount >= Integer.parseInt(PropsUtil.getDataPropertyValue("EA_LOC_UncatTxnSize")));
		Assert.assertTrue(LOCTxnccount >= Integer.parseInt(PropsUtil.getDataPropertyValue("EA_LOC_UncatTxnSize")));
		Assert.assertTrue(LOCTxnccount >= Integer.parseInt(PropsUtil.getDataPropertyValue("EA_LOC_UncatTxnSize")));
		if (expenseTrend.isEIAcloseTransPopup()) {
			SeleniumUtil.click(expenseTrend.EIAcloseTransPopup());
		}
	}

	@Test(description = "AT-97369,AT-97377,AT-97382:verify  default month data income", priority = 18)

	public void verifydefaultTrendMonth_IA() {
		expenseTrend.navigateToEA();
		SeleniumUtil.waitForPageToLoad(4000);
		expenseTrend.selectIncome();
		SeleniumUtil.waitForPageToLoad(4000);
		this.addManualAdjTxn();
		String avgMsg = expenseTrend.getAvgAmountMessage(
				Integer.parseInt(PropsUtil.getDataPropertyValue("EA_Trend_DefaultTF_AvgAmountStart")),
				Integer.parseInt(PropsUtil.getDataPropertyValue("EA_Trend_DefaultTF_AvgAmountEnd")),
				PropsUtil.getDataPropertyValue("EA_Default6MonthAvgAmountMessage1"),
				PropsUtil.getDataPropertyValue("IA_LOC_Default6MonthAvgAmountMessage2"));
		List<String> ExpectedDiffPerValue = expenseTrend.totalAmountPerIncome1(
				util.getWebElementsValue(expenseTrend.EIATrendMonthAmount()), expenseTrend.EIATrendAvg().getText(),
				Integer.parseInt(PropsUtil.getDataPropertyValue("EA_Trend_DefaultTF_getAvgAmount")));
		List<String> actualDiffPerValue = util.getWebElementsValue(expenseTrend.EIATrendMonthAmountPer());

		util.assertExpectedActualList(util.getWebElementsValue(expenseTrend.EIATrendMonthList()),
				expenseTrend.getMonthsValues(
						Integer.parseInt(PropsUtil.getDataPropertyValue("EA_Trend_DefaultTF_getAvgAmount"))),
				"past 6 month name is not displayed in MMMM formate");
		util.assertExpectedActualAmountList(util.getWebElementsValue(expenseTrend.EIATrendMonthAmount()),
				PropsUtil.getDataPropertyValue("IA_LOC_Default6MonthAmount_List"),
				"all 6 month expense amount is not displayed");
		util.assertExpectedActualList(actualDiffPerValue, ExpectedDiffPerValue,
				"Total dif from avg and % values is not displayed");
		Assert.assertEquals(expenseTrend.EIATrendAvg().getText(), avgMsg, "lass 3 month avg is not displayed");
		if (Config.appFlag.equals("web") || Config.appFlag.equals("false")) {

			Assert.assertTrue(expenseTrend.EIATrendTopTxnCatgeory().size() == Integer
					.parseInt(PropsUtil.getDataPropertyValue("EA_12MonthTimeFilter_ToptxnSize")));
			util.assertExpectedActualAmountList(util.getWebElementsValue(expenseTrend.EIATrendTopTxnAmount()),
					PropsUtil.getDataPropertyValue("IA_LOC_12MonthTimeFilter_ToptxnAmount"),
					"top 5 txn amount is not displayed");
		}
	}

	@Test(description = "AT-97365:verify all accounts", priority = 19) // , dependsOnMethods =
																		// "verifydefaultTrendMonth_IA"

	public void verifyAcctDDownAllAcctsName_IA() {
		expenseTrend.clickEIAccountDropDown();
		util.assertExpectedActualList(util.getWebElementsValue(expenseTrend.EIAllAccountList()),
				PropsUtil.getDataPropertyValue("IA_LOC_AllAccountS"),
				"verify all added expense accounts are not displayed accounts dropdown");

	}

	@Test(description = "AT-97366:verify LOC account income data", priority = 20) // , dependsOnMethods =
																					// "verifyAcctDDownAllAcctsName_IA"

	public void verifyLOCAccountSelected_IA() {
		expenseTrend.clickEIAllAccountCheckBox();
		expenseTrend.selectAccount(PropsUtil.getDataPropertyValue("IA_LOC_SelectCashAccount"));
		SeleniumUtil.waitForPageToLoad();
		String avgMsg = expenseTrend.getAvgAmountMessage(
				Integer.parseInt(PropsUtil.getDataPropertyValue("EA_Trend_DefaultTF_AvgAmountStart")),
				Integer.parseInt(PropsUtil.getDataPropertyValue("EA_Trend_DefaultTF_AvgAmountEnd")),
				PropsUtil.getDataPropertyValue("EA_Default6MonthAvgAmountMessage1"),
				PropsUtil.getDataPropertyValue("IA_LOC_AvgAmountMessage2"));
		List<String> ExpectedDiffPerValue = expenseTrend.totalAmountPerIncome1(
				util.getWebElementsValue(expenseTrend.EIATrendMonthAmount()), expenseTrend.EIATrendAvg().getText(),
				Integer.parseInt(PropsUtil.getDataPropertyValue("EA_Trend_DefaultTF_getAvgAmount")));
		List<String> actualDiffPerValue = util.getWebElementsValue(expenseTrend.EIATrendMonthAmountPer());

		int top5txnSize = expenseTrend.EIATrendTopTxnAccount().size();
		int toptxnCount = 0;
		for (int i = 0; i < top5txnSize; i++) {
			if (expenseTrend.EIATrendTopTxnAccount().get(i).getText().trim()
					.equals(PropsUtil.getDataPropertyValue("IA_LOC_ToptxnAccount"))) {
				toptxnCount = toptxnCount + 1;
			}

		}
		util.assertExpectedActualList(util.getWebElementsValue(expenseTrend.EIATrendMonthList()),
				expenseTrend.getMonthsValues(
						Integer.parseInt(PropsUtil.getDataPropertyValue("EA_Trend_DefaultTF_getAvgAmount"))),
				"past 6 month name is not displayed in MMMM formate");
		util.assertExpectedActualAmountList(util.getWebElementsValue(expenseTrend.EIATrendMonthAmount()),
				PropsUtil.getDataPropertyValue("IA_LOC_6monthData"), "all 6 month expense amount is not displayed");
		util.assertExpectedActualList(actualDiffPerValue, ExpectedDiffPerValue,
				"Total dif from avg and % values is not displayed");
		Assert.assertEquals(expenseTrend.EIATrendAvg().getText(), avgMsg, "lass 3 month avg is not displayed");
		if (Config.appFlag.equals("web") || Config.appFlag.equals("false")) {

			Assert.assertTrue(expenseTrend.EIATrendTopTxnCatgeory().size() == Integer
					.parseInt(PropsUtil.getDataPropertyValue("IA_LOC_12MonthTimeFilter_ToptxnSize")));
			Assert.assertTrue(toptxnCount == Integer
					.parseInt(PropsUtil.getDataPropertyValue("IA_LOC_12MonthTimeFilter_ToptxnSize")));
			util.assertExpectedActualAmountList(util.getWebElementsValue(expenseTrend.EIATrendTopTxnAmount()),
					PropsUtil.getDataPropertyValue("IA_LOC_ToptxnAmount"), "top 5 txn amount is not displayed");
		}
	}

	@Test(description = "AT-97366:verify HELOC account income data", priority = 21) // , dependsOnMethods =
																					// "verifyLOCAccountSelected_IA"
	public void verifyHELOCAccountSelected_IA() {
		expenseTrend.selectMultiAccount(PropsUtil.getDataPropertyValue("IA_HELOC_SelectCashAccount"));
		if (expenseTrend.isSelectAcc_Donebtn()) {
			SeleniumUtil.click(expenseTrend.SelectAcc_Donebtn());
			SeleniumUtil.waitForPageToLoad();
		}
		SeleniumUtil.waitForPageToLoad();
		String avgMsg = expenseTrend.getAvgAmountMessage(
				Integer.parseInt(PropsUtil.getDataPropertyValue("EA_Trend_DefaultTF_AvgAmountStart")),
				Integer.parseInt(PropsUtil.getDataPropertyValue("EA_Trend_DefaultTF_AvgAmountEnd")),
				PropsUtil.getDataPropertyValue("EA_Default6MonthAvgAmountMessage1"),
				PropsUtil.getDataPropertyValue("IA_HELOC_AvgAmountMessage2"));
		List<String> ExpectedDiffPerValue = expenseTrend.totalAmountPerIncome1(
				util.getWebElementsValue(expenseTrend.EIATrendMonthAmount()), expenseTrend.EIATrendAvg().getText(),
				Integer.parseInt(PropsUtil.getDataPropertyValue("EA_Trend_DefaultTF_getAvgAmount")));
		List<String> actualDiffPerValue = util.getWebElementsValue(expenseTrend.EIATrendMonthAmountPer());
		int top5txnSize = expenseTrend.EIATrendTopTxnAccount().size();
		int toptxnCount = 0;
		for (int i = 0; i < top5txnSize; i++) {
			if (expenseTrend.EIATrendTopTxnAccount().get(i).getText().trim()
					.equals(PropsUtil.getDataPropertyValue("IA_HELOC_ToptxnAccount"))) {
				toptxnCount = toptxnCount + 1;
			}

		}
		util.assertExpectedActualList(util.getWebElementsValue(expenseTrend.EIATrendMonthList()),
				expenseTrend.getMonthsValues(
						Integer.parseInt(PropsUtil.getDataPropertyValue("EA_Trend_DefaultTF_getAvgAmount"))),
				"past 6 month name is not displayed in MMMM formate");
		util.assertExpectedActualAmountList(util.getWebElementsValue(expenseTrend.EIATrendMonthAmount()),
				PropsUtil.getDataPropertyValue("IA_HELOC_6monthData"), "all 6 month expense amount is not displayed");
		util.assertExpectedActualList(actualDiffPerValue, ExpectedDiffPerValue,
				"Total dif from avg and % values is not displayed");
		Assert.assertEquals(expenseTrend.EIATrendAvg().getText(), avgMsg, "lass 3 month avg is not displayed");
		if (Config.appFlag.equals("web") || Config.appFlag.equals("false")) {

			Assert.assertTrue(expenseTrend.EIATrendTopTxnCatgeory().size() == Integer
					.parseInt(PropsUtil.getDataPropertyValue("IA_LOC_12MonthTimeFilter_ToptxnSize")));
			Assert.assertTrue(toptxnCount == Integer
					.parseInt(PropsUtil.getDataPropertyValue("IA_LOC_12MonthTimeFilter_ToptxnSize")));

			util.assertExpectedActualAmountList(util.getWebElementsValue(expenseTrend.EIATrendTopTxnAmount()),
					PropsUtil.getDataPropertyValue("IA_HELOC_ToptxnAmount"), "top 5 txn amount is not displayed");
		}
	}

	@Test(description = "AT-97366:verify SBLOC account Income data", priority = 22) // , dependsOnMethods =
																					// "verifyHELOCAccountSelected_IA"
	public void verifySBLOCAccountSelected_IA() {
		expenseTrend.selectMultiAccount(PropsUtil.getDataPropertyValue("IA_SBLOC_SelectCashAccount"));
		if (expenseTrend.isSelectAcc_Donebtn()) {
			SeleniumUtil.click(expenseTrend.SelectAcc_Donebtn());
			SeleniumUtil.waitForPageToLoad();
		}
		SeleniumUtil.waitForPageToLoad();
		String avgMsg = expenseTrend.getAvgAmountMessage(
				Integer.parseInt(PropsUtil.getDataPropertyValue("EA_Trend_DefaultTF_AvgAmountStart")),
				Integer.parseInt(PropsUtil.getDataPropertyValue("EA_Trend_DefaultTF_AvgAmountEnd")),
				PropsUtil.getDataPropertyValue("EA_Default6MonthAvgAmountMessage1"),
				PropsUtil.getDataPropertyValue("IA_SBLOC_AvgAmountMessage2"));
		List<String> ExpectedDiffPerValue = expenseTrend.totalAmountPerIncome1(
				util.getWebElementsValue(expenseTrend.EIATrendMonthAmount()), expenseTrend.EIATrendAvg().getText(),
				Integer.parseInt(PropsUtil.getDataPropertyValue("EA_Trend_DefaultTF_getAvgAmount")));
		List<String> actualDiffPerValue = util.getWebElementsValue(expenseTrend.EIATrendMonthAmountPer());
		int top5txnSize = expenseTrend.EIATrendTopTxnAccount().size();
		int toptxnCount = 0;
		for (int i = 0; i < top5txnSize; i++) {
			if (expenseTrend.EIATrendTopTxnAccount().get(i).getText().trim()
					.equals(PropsUtil.getDataPropertyValue("IA_SBLOC_ToptxnAccount"))) {
				toptxnCount = toptxnCount + 1;
			}

		}
		util.assertExpectedActualList(util.getWebElementsValue(expenseTrend.EIATrendMonthList()),
				expenseTrend.getMonthsValues(
						Integer.parseInt(PropsUtil.getDataPropertyValue("EA_Trend_DefaultTF_getAvgAmount"))),
				"past 6 month name is not displayed in MMMM formate");
		util.assertExpectedActualAmountList(util.getWebElementsValue(expenseTrend.EIATrendMonthAmount()),
				PropsUtil.getDataPropertyValue("IA_SBLOC_6monthData"), "all 6 month expense amount is not displayed");
		util.assertExpectedActualList(actualDiffPerValue, ExpectedDiffPerValue,
				"Total dif from avg and % values is not displayed");
		Assert.assertEquals(expenseTrend.EIATrendAvg().getText(), avgMsg, "lass 3 month avg is not displayed");
		if (Config.appFlag.equals("web") || Config.appFlag.equals("false")) {

			Assert.assertTrue(expenseTrend.EIATrendTopTxnCatgeory().size() == Integer
					.parseInt(PropsUtil.getDataPropertyValue("IA_LOC_12MonthTimeFilter_ToptxnSize")));
			util.assertExpectedActualAmountList(util.getWebElementsValue(expenseTrend.EIATrendTopTxnAmount()),
					PropsUtil.getDataPropertyValue("IA_SBLOC_ToptxnAmount"), "top 5 txn amount is not displayed");
		}
	}

	@Test(description = "AT-97366:verify 3 month time filter income data", priority = 23)

	public void TimeFilter3month_IA() {
		expenseTrend.navigateToEA();
		expenseTrend.selectIncome();
		expenseTrend.selectTimeFilter(PropsUtil.getDataPropertyValue("IA_3monthsTimeFilter"));
		SeleniumUtil.waitForPageToLoad();
		String avgMsg = expenseTrend.getAvgAmountMessage(
				Integer.parseInt(PropsUtil.getDataPropertyValue("EA_Trend_DefaultTF_AvgAmountStart")),
				Integer.parseInt(PropsUtil.getDataPropertyValue("EA_Trend_DefaultTF_AvgAmountEnd")),
				PropsUtil.getDataPropertyValue("EA_Default6MonthAvgAmountMessage1"),
				PropsUtil.getDataPropertyValue("IA_LOCTF_AvgAmountMessage2"));
		List<String> ExpectedDiffPerValue = expenseTrend.totalAmountPerIncome1(
				util.getWebElementsValue(expenseTrend.EIATrendMonthAmount()), expenseTrend.EIATrendAvg().getText(),
				Integer.parseInt(PropsUtil.getDataPropertyValue("EA_Trend_3monthTF_getAvgAmount")));
		List<String> actualDiffPerValue = util.getWebElementsValue(expenseTrend.EIATrendMonthAmountPer());
		util.assertExpectedActualList(util.getWebElementsValue(expenseTrend.EIATrendMonthList()),
				expenseTrend.getMonthsValues(
						Integer.parseInt(PropsUtil.getDataPropertyValue("EA_Trend_3monthTF_getAvgAmount"))),
				"past 6 month name is not displayed in MMMM formate");
		util.assertExpectedActualAmountList(util.getWebElementsValue(expenseTrend.EIATrendMonthAmount()),
				PropsUtil.getDataPropertyValue("IA_LOCTF_6monthData"), "all 6 month expense amount is not displayed");
		util.assertExpectedActualList(actualDiffPerValue, ExpectedDiffPerValue,
				"Total dif from avg and % values is not displayed");
		Assert.assertEquals(expenseTrend.EIATrendAvg().getText(), avgMsg, "lass 3 month avg is not displayed");
		if (Config.appFlag.equals("web") || Config.appFlag.equals("false")) {

			Assert.assertTrue(expenseTrend.EIATrendTopTxnCatgeory().size() == Integer
					.parseInt(PropsUtil.getDataPropertyValue("EA_12MonthTimeFilter_ToptxnSize")));
			util.assertExpectedActualAmountList(util.getWebElementsValue(expenseTrend.EIATrendTopTxnAmount()),
					PropsUtil.getDataPropertyValue("IA_LOCTF_ToptxnAmount"), "top 5 txn amount is not displayed");
		}
	}

	@Test(description = "AT-97367:verify Loc account group income data", priority = 24) // , dependsOnMethods =
																						// "groupFilter"
	public void groupFilter_IA() throws Exception {

		expenseTrend.navigateToEA();
		expenseTrend.selectIncome();
		SeleniumUtil.waitForPageToLoad();
		expenseTrend.clickGroup(PropsUtil.getDataPropertyValue("IA_LOC_GroupName"));
		if (expenseTrend.isSelectAcc_Donebtn()) {
			SeleniumUtil.click(expenseTrend.SelectAcc_Donebtn());
		}
		SeleniumUtil.waitForPageToLoad();
		String avgMsg = expenseTrend.getAvgAmountMessage(
				Integer.parseInt(PropsUtil.getDataPropertyValue("EA_Trend_DefaultTF_AvgAmountStart")),
				Integer.parseInt(PropsUtil.getDataPropertyValue("EA_Trend_DefaultTF_AvgAmountEnd")),
				PropsUtil.getDataPropertyValue("EA_Default6MonthAvgAmountMessage1"),
				PropsUtil.getDataPropertyValue("IA_LOCGroup_AvgAmountMessage2"));
		List<String> ExpectedDiffPerValue = expenseTrend.totalAmountPerIncome1(
				util.getWebElementsValue(expenseTrend.EIATrendMonthAmount()), expenseTrend.EIATrendAvg().getText(),
				Integer.parseInt(PropsUtil.getDataPropertyValue("EA_Trend_DefaultTF_getAvgAmount")));
		List<String> actualDiffPerValue = util.getWebElementsValue(expenseTrend.EIATrendMonthAmountPer());
		util.assertExpectedActualList(util.getWebElementsValue(expenseTrend.EIATrendMonthList()),
				expenseTrend.getMonthsValues(
						Integer.parseInt(PropsUtil.getDataPropertyValue("EA_Trend_DefaultTF_getAvgAmount"))),
				"past 6 month name is not displayed in MMMM formate");
		util.assertExpectedActualAmountList(util.getWebElementsValue(expenseTrend.EIATrendMonthAmount()),
				PropsUtil.getDataPropertyValue("IA_LOCGroup_6monthData"),
				"all 6 month expense amount is not displayed");
		util.assertExpectedActualList(actualDiffPerValue, ExpectedDiffPerValue,
				"Total dif from avg and % values is not displayed");
		Assert.assertEquals(expenseTrend.EIATrendAvg().getText(), avgMsg, "lass 3 month avg is not displayed");
		if (Config.appFlag.equals("web") || Config.appFlag.equals("false")) {

			Assert.assertTrue(expenseTrend.EIATrendTopTxnCatgeory().size() == Integer
					.parseInt(PropsUtil.getDataPropertyValue("EA_12MonthTimeFilter_ToptxnSize")));
			util.assertExpectedActualAmountList(util.getWebElementsValue(expenseTrend.EIATrendTopTxnAmount()),
					PropsUtil.getDataPropertyValue("IA_LOCGroup_ToptxnAmount"), "top 5 txn amount is not displayed");
		}
	}

	@Test(description = "AT-97378:verify HLC page loc account income data", priority = 25)
	public void VerifyHLCData_IA() throws Exception {
		expenseTrend.navigateToEA();
		expenseTrend.selectIncome();
		SeleniumUtil.waitForPageToLoad(5000);
		expenseTrend.EIAccountDropDown();
		expenseTrend.clickAccountLink();
		expenseTrend.clickEIAllAccountCheckBox();
		SeleniumUtil.waitForPageToLoad();
		expenseTrend.clickLegendMonth(Integer.parseInt(PropsUtil.getDataPropertyValue("EA_TrendTOHLC_MonthList")));
		if (Config.appFlag.equals("app") || Config.appFlag.equals("emulator")) {
			SeleniumUtil.click(expenseTrend.UncatAccTxn());
		}
		int uncatTxnSize = expenseTrend.EIAHLCCatUnCatTxnAccount().size();
		int LOCTxnccount = 0;
		int HELOCtxnCount = 0;
		int SBLOCTxncount = 0;
		for (int i = 0; i < uncatTxnSize; i++) {
			System.out.println(expenseTrend.EIAHLCCatUnCatTxnAccount().get(i).getText());
			if (expenseTrend.EIAHLCCatUnCatTxnAccount().get(i).getText().trim().replaceAll("\n", " ")
					.equals(PropsUtil.getDataPropertyValue("IA_LOC_UncatTxnAccount"))) {
				LOCTxnccount = LOCTxnccount + 1;
			} else if (expenseTrend.EIAHLCCatUnCatTxnAccount().get(i).getText().trim().replaceAll("\n", " ")
					.equals(PropsUtil.getDataPropertyValue("IA_HELOC_UncatTxnAccount"))) {
				HELOCtxnCount = HELOCtxnCount + 1;
			} else if (expenseTrend.EIAHLCCatUnCatTxnAccount().get(i).getText().trim().replaceAll("\n", " ")
					.equals(PropsUtil.getDataPropertyValue("IA_SBLOC_UncatTxnAccount"))) {
				SBLOCTxncount = SBLOCTxncount + 1;
			}
		}
		Assert.assertTrue(LOCTxnccount >= Integer.parseInt(PropsUtil.getDataPropertyValue("IA_LOC_UncatTxnSize")));
		Assert.assertTrue(HELOCtxnCount >= Integer.parseInt(PropsUtil.getDataPropertyValue("IA_LOC_UncatTxnSize")));
		Assert.assertTrue(SBLOCTxncount >= Integer.parseInt(PropsUtil.getDataPropertyValue("IA_LOC_UncatTxnSize")));
		if (expenseTrend.isEIAcloseTransPopup()) {
			SeleniumUtil.click(expenseTrend.EIAcloseTransPopup());

		}

		Assert.assertEquals(expenseTrend.EIAHLCCatCharValue().getText().trim(),
				PropsUtil.getDataPropertyValue("IA_AllAccountHLC_TotalExpenseValue1"),
				"total expense amount is not displayed");
		util.assertExpectedActualList(util.getWebElementsValue(expenseTrend.EIAHLCCatNameList()),
				PropsUtil.getDataPropertyValue("IA_AllAccountHLC_CatName"),
				"all 11 HLc category name is not displayed");
		util.assertExpectedActualAmountList(util.getWebElementsValue(expenseTrend.EIAHLCCatPerList()),
				PropsUtil.getDataPropertyValue("IA_AllAccountHLC_CatPerAmount"),
				"all HLc actegory is not displayed % amount");
		util.assertExpectedActualAmountList(util.getWebElementsValue(expenseTrend.EIAHLCCatAmountList()),
				PropsUtil.getDataPropertyValue("IA_AllAccountHLC_CatAmount"),
				"all HLc catgeory is not displayed with expense amountamount");
		Assert.assertEquals(expenseTrend.EIAHLCCatRefundAmount().getText(),
				PropsUtil.getDataPropertyValue("IA_AllAccountHLC_CatRefund"), "total HLC refund is not displayed");

	}

	@Test(description = "AT-97378:verify HLC page all account", priority = 26) // , dependsOnMethods =
																				// "VerifyHLCData_IA"
	public void VerifyHLCAccountDropDown_IA() throws Exception {
		expenseTrend.clickEIAccountDropDown();
		util.assertExpectedActualList(util.getWebElementsValue(expenseTrend.EIAllAccountList()),
				PropsUtil.getDataPropertyValue("IA_LOC_AllAccountS"),
				"verify all added expense accounts are not displayed in accounts dropdown");
	}

	@Test(description = "AT-97383:verify HLC page loc account income data", priority = 27) // , dependsOnMethods =
																							// "VerifyHLCAccountDropDown_IA"
	public void VerifyLOCAccountHLCData_IA() throws Exception {
		expenseTrend.clickEIAllAccountCheckBox();
		SeleniumUtil.waitForPageToLoad();
		expenseTrend.selectAccount(PropsUtil.getDataPropertyValue("IA_LOC_SelectCashAccount"));
		if (Config.appFlag.equals("app") || Config.appFlag.equals("emulator")) {
			SeleniumUtil.click(expenseTrend.UncatAccTxn());
		}
		SeleniumUtil.waitForPageToLoad();
		int uncatTxnSize = expenseTrend.EIAHLCCatUnCatTxnAccount().size();
		int LOCTxnccount = 0;

		for (int i = 0; i < uncatTxnSize; i++) {
			if (expenseTrend.EIAHLCCatUnCatTxnAccount().get(i).getText().trim().replaceAll("\n", " ")
					.equals(PropsUtil.getDataPropertyValue("IA_LOC_UncatTxnAccount"))) {
				LOCTxnccount = LOCTxnccount + 1;
			}

		}
		Assert.assertTrue(LOCTxnccount >= Integer.parseInt(PropsUtil.getDataPropertyValue("IA_LOC_UncatTxnSize")));
		if (expenseTrend.isEIAcloseTransPopup()) {
			SeleniumUtil.click(expenseTrend.EIAcloseTransPopup());
		}

		Assert.assertEquals(expenseTrend.EIAHLCCatCharValue().getText().trim(),
				PropsUtil.getDataPropertyValue("IA_LOCHLC_TotalExpenseValue1"),
				"total expense amount is not displayed");
		util.assertExpectedActualList(util.getWebElementsValue(expenseTrend.EIAHLCCatNameList()),
				PropsUtil.getDataPropertyValue("IA_LOCHLC_CatName"), "all 11 HLc category name is not displayed");
		util.assertExpectedActualAmountList(util.getWebElementsValue(expenseTrend.EIAHLCCatPerList()),
				PropsUtil.getDataPropertyValue("IA_LOCHLC_CatPerAmount"), "all HLc actegory is not displayed % amount");
		util.assertExpectedActualAmountList(util.getWebElementsValue(expenseTrend.EIAHLCCatAmountList()),
				PropsUtil.getDataPropertyValue("IA_LOCHLC_CatAmount"),
				"all HLc catgeory is not displayed with expense amountamount");
		Assert.assertEquals(expenseTrend.EIAHLCCatRefundAmount().getText(),
				PropsUtil.getDataPropertyValue("IA_LOCHLC_CatRefund"), "total HLC refund is not displayed");

	}

	@Test(description = "AT-97378,AT-97383:verify HLC apge heloc account income data", priority = 28) // ,
																										// dependsOnMethods
																										// =
																										// "VerifyLOCAccountHLCData_IA"
	public void VerifyHELOCAccountHLCData_IA() throws Exception {
		expenseTrend.selectMultiAccount(PropsUtil.getDataPropertyValue("IA_HELOC_SelectCashAccount"));
		if (expenseTrend.isSelectAcc_Donebtn()) {
			SeleniumUtil.click(expenseTrend.SelectAcc_Donebtn());
			SeleniumUtil.waitForPageToLoad();
		}
		if (Config.appFlag.equals("app") || Config.appFlag.equals("emulator")) {
			SeleniumUtil.click(expenseTrend.UncatAccTxn());
		}
		SeleniumUtil.waitForPageToLoad();
		int uncatTxnSize = expenseTrend.EIAHLCCatUnCatTxnAccount().size();
		int HELOCtxnCount = 0;

		for (int i = 0; i < uncatTxnSize; i++) {
			if (expenseTrend.EIAHLCCatUnCatTxnAccount().get(i).getText().trim().replaceAll("\n", " ")
					.equals(PropsUtil.getDataPropertyValue("IA_HELOC_UncatTxnAccount"))) {
				HELOCtxnCount = HELOCtxnCount + 1;
			}
		}
		Assert.assertTrue(HELOCtxnCount >= Integer.parseInt(PropsUtil.getDataPropertyValue("IA_LOC_UncatTxnSize")));
		if (expenseTrend.isEIAcloseTransPopup()) {
			SeleniumUtil.click(expenseTrend.EIAcloseTransPopup());
			SeleniumUtil.waitForPageToLoad();
		}
		Assert.assertEquals(expenseTrend.EIAHLCCatCharValue().getText().trim(),
				PropsUtil.getDataPropertyValue("IA_HELOCHLC_TotalExpenseValue1"),
				"total expense amount is not displayed");
		util.assertExpectedActualList(util.getWebElementsValue(expenseTrend.EIAHLCCatNameList()),
				PropsUtil.getDataPropertyValue("IA_HELOCHLC_CatName"), "all 11 HLc category name is not displayed");
		util.assertExpectedActualAmountList(util.getWebElementsValue(expenseTrend.EIAHLCCatPerList()),
				PropsUtil.getDataPropertyValue("IA_HELOCHLC_CatPerAmount"),
				"all HLc actegory is not displayed % amount");
		util.assertExpectedActualAmountList(util.getWebElementsValue(expenseTrend.EIAHLCCatAmountList()),
				PropsUtil.getDataPropertyValue("IA_HELOCHLC_CatAmount"),
				"all HLc catgeory is not displayed with expense amountamount");
		Assert.assertEquals(expenseTrend.EIAHLCCatRefundAmount().getText(),
				PropsUtil.getDataPropertyValue("IA_HELOCHLC_CatRefund"), "total HLC refund is not displayed");

	}

	@Test(description = "AT-97378,AT-97383:verify HLC page sbloc account income data", priority = 29) // ,
																										// dependsOnMethods
																										// =
																										// "VerifyHELOCAccountHLCData_IA"
	public void VerifySBLOCAccountHLCData_IA() throws Exception {
		expenseTrend.selectMultiAccount(PropsUtil.getDataPropertyValue("IA_SBLOC_SelectCashAccount"));
		if (expenseTrend.isSelectAcc_Donebtn()) {
			SeleniumUtil.click(expenseTrend.SelectAcc_Donebtn());
			SeleniumUtil.waitForPageToLoad();
		}
		if (Config.appFlag.equals("app") || Config.appFlag.equals("emulator")) {
			SeleniumUtil.click(expenseTrend.UncatAccTxn());
		}
		SeleniumUtil.waitForPageToLoad();
		int uncatTxnSize = expenseTrend.EIAHLCCatUnCatTxnAccount().size();
		int SBLOCtxnCount = 0;

		for (int i = 0; i < uncatTxnSize; i++) {
			if (expenseTrend.EIAHLCCatUnCatTxnAccount().get(i).getText().trim().replaceAll("\n", " ")
					.equals(PropsUtil.getDataPropertyValue("IA_SBLOC_UncatTxnAccount"))) {
				SBLOCtxnCount = SBLOCtxnCount + 1;
			}
		}
		Assert.assertTrue(SBLOCtxnCount >= Integer.parseInt(PropsUtil.getDataPropertyValue("IA_LOC_UncatTxnSize")));
		if (expenseTrend.isEIAcloseTransPopup()) {
			SeleniumUtil.click(expenseTrend.EIAcloseTransPopup());
		}

		Assert.assertEquals(expenseTrend.EIAHLCCatCharValue().getText().trim(),
				PropsUtil.getDataPropertyValue("IA_SBLOCHLC_TotalExpenseValue1"),
				"total expense amount is not displayed");
		util.assertExpectedActualList(util.getWebElementsValue(expenseTrend.EIAHLCCatNameList()),
				PropsUtil.getDataPropertyValue("IA_SBLOCHLC_CatName"), "all 11 HLc category name is not displayed");
		util.assertExpectedActualAmountList(util.getWebElementsValue(expenseTrend.EIAHLCCatPerList()),
				PropsUtil.getDataPropertyValue("IA_SBLOCHLC_CatPerAmount"),
				"all HLc actegory is not displayed % amount");
		util.assertExpectedActualAmountList(util.getWebElementsValue(expenseTrend.EIAHLCCatAmountList()),
				PropsUtil.getDataPropertyValue("IA_SBLOCHLC_CatAmount"),
				"all HLc catgeory is not displayed with expense amountamount");

	}

	@Test(description = "AT-97379,AT-97380,AT-97384:verify MLC page Loc account income data", priority = 30)
	public void VerifyLOCAccountMLCData_IA() throws Exception {
		expenseTrend.navigateToEA();
		expenseTrend.selectIncome();
		SeleniumUtil.waitForPageToLoad();
		expenseTrend.clickEAHLC(PropsUtil.getDataPropertyValue("IA_LOCMLC_MLC_HLC1"),
				Integer.parseInt(PropsUtil.getDataPropertyValue("EA_TrendTOHLC_MonthList")));
		if (Config.appFlag.equals("app") || Config.appFlag.equals("emulator")) {
			SeleniumUtil.click(expenseTrend.UncatAccTxn());
			SeleniumUtil.waitForPageToLoad();
		}
		int LOCTxnccount = 0;
		int HELOCtxnCount = 0;
		int SBLOCTxncount = 0;
		int uncatTxnSize = expenseTrend.EIAMLCHeaderTxnAccountList().size();
		for (int i = 0; i < uncatTxnSize; i++) {
			if (expenseTrend.EIAMLCHeaderTxnAccountList().get(i).getText().trim().replaceAll("\n", " ")
					.equals(PropsUtil.getDataPropertyValue("IA_LOC_UncatTxnAccount"))) {
				LOCTxnccount = LOCTxnccount + 1;
			} else if (expenseTrend.EIAMLCHeaderTxnAccountList().get(i).getText().trim().replaceAll("\n", " ")
					.equals(PropsUtil.getDataPropertyValue("IA_HELOC_UncatTxnAccount"))) {
				HELOCtxnCount = HELOCtxnCount + 1;
			} else if (expenseTrend.EIAMLCHeaderTxnAccountList().get(i).getText().trim().replaceAll("\n", " ")
					.equals(PropsUtil.getDataPropertyValue("IA_SBLOC_UncatTxnAccount"))) {
				SBLOCTxncount = SBLOCTxncount + 1;
			}
		}
		Assert.assertTrue(LOCTxnccount >= Integer.parseInt(PropsUtil.getDataPropertyValue("IA_LOC_UncatTxnSize")));
		Assert.assertTrue(LOCTxnccount >= Integer.parseInt(PropsUtil.getDataPropertyValue("IA_LOC_UncatTxnSize")));
		Assert.assertTrue(LOCTxnccount >= Integer.parseInt(PropsUtil.getDataPropertyValue("IA_LOC_UncatTxnSize")));
		if (expenseTrend.isEIAcloseTransPopup()) {
			SeleniumUtil.click(expenseTrend.EIAcloseTransPopup());
			SeleniumUtil.waitForPageToLoad();
		}
		Assert.assertEquals(expenseTrend.EIAHLCCatCharValue().getText().trim(),
				PropsUtil.getDataPropertyValue("IA_LOCMLC_HLCSpendValue"), "total expense amount is not displayed");
		util.assertExpectedActualList(util.getWebElementsValue(expenseTrend.EIAMLCCatNameList()),
				PropsUtil.getDataPropertyValue("IA_LOCMLC_List1"), "all 11 HLc category name is not displayed");
		util.assertExpectedActualAmountList(util.getWebElementsValue(expenseTrend.EIAMLCCatPerList()),
				PropsUtil.getDataPropertyValue("IA_LOCMLC_PerList1"), "all HLc category is not displayed % amount");
		util.assertExpectedActualAmountList(util.getWebElementsValue(expenseTrend.EIAMLCCatAmountList()),
				PropsUtil.getDataPropertyValue("IA_LOCMLC_AmountList1"),
				"all HLc category is not displayed with expense amountamount");

	}

	@Test(description = "AT-97381:verify trend popup loc account income data", priority = 31) // , dependsOnMethods =
																								// "VerifyLOCAccountMLCData_IA"
	public void VerifyLOCAccountTrendPopup_IA() throws Exception {
		expenseTrend.clickTrendPopUp();
		SeleniumUtil.waitForPageToLoad();
		util.assertExpectedActualAmountList(util.getWebElementsValue(expenseTrend.EIATrendMonthAmount()),
				PropsUtil.getDataPropertyValue("IA_LOCMLC_TrendPopUpAmount"),
				"all 6 month expense amount is not displayed");

		String avgMsg = expenseTrend.getAvgAmountMessage(
				Integer.parseInt(PropsUtil.getDataPropertyValue("EA_Trend_DefaultTF_AvgAmountStart")),
				Integer.parseInt(PropsUtil.getDataPropertyValue("EA_Trend_DefaultTF_AvgAmountEnd")),
				PropsUtil.getDataPropertyValue("EA_Default6MonthAvgAmountMessage1"),
				PropsUtil.getDataPropertyValue("IA_LOCMLC_TrendPopUpAvgMesg"));
		Assert.assertEquals(expenseTrend.EIATrendAvg().getText(), avgMsg, "lass 3 month avg is not displayed");
	}

	@Test(description = "AT-97381:verify group filter value", priority = 32)
	public void VerifyLOCAccountTxnPopup_IA() throws Exception {
		SeleniumUtil.click(expenseTrend.EIAMLCTrendPopUpClose());
		expenseTrend.navigateToEA();
		expenseTrend.selectIncome();
		SeleniumUtil.waitForPageToLoad();
		expenseTrend.clickEAHLC(PropsUtil.getDataPropertyValue("IA_LOCMLC_MLC_HLC1"),
				Integer.parseInt(PropsUtil.getDataPropertyValue("EA_TrendTOHLC_MonthList")));
		SeleniumUtil.waitForPageToLoad();
		SeleniumUtil.click(expenseTrend.EIAMLCCatName(PropsUtil.getDataPropertyValue("IA_LOCMLC_MLC1")));
		SeleniumUtil.waitForPageToLoad();
		if (Config.appFlag.equals("app") || Config.appFlag.equals("emulator")) {
			SeleniumUtil.click(expenseTrend.UncatAccTxn());
			SeleniumUtil.waitForPageToLoad();
		}
		int txnSize = expenseTrend.EIAMLCTxnPopUpAccountList().size();
		int LOCTxnccount = 0;
		int HELOCtxnCount = 0;
		int SBLOCTxncount = 0;

		for (int i = 0; i < txnSize; i++) {
			if (expenseTrend.EIAMLCTxnPopUpAccountList().get(i).getText().trim().replaceAll("\n", " ")
					.equals(PropsUtil.getDataPropertyValue("IA_LOC_UncatTxnAccount"))) {
				LOCTxnccount = LOCTxnccount + 1;
			} else if (expenseTrend.EIAMLCTxnPopUpAccountList().get(i).getText().trim().replaceAll("\n", " ")
					.equals(PropsUtil.getDataPropertyValue("IA_HELOC_UncatTxnAccount"))) {
				HELOCtxnCount = HELOCtxnCount + 1;
			} else if (expenseTrend.EIAMLCTxnPopUpAccountList().get(i).getText().trim().replaceAll("\n", " ")
					.equals(PropsUtil.getDataPropertyValue("IA_SBLOC_UncatTxnAccount"))) {
				SBLOCTxncount = SBLOCTxncount + 1;
			}
		}

		Assert.assertTrue(LOCTxnccount >= Integer.parseInt(PropsUtil.getDataPropertyValue("IA_LOC_UncatTxnSize")));
		Assert.assertTrue(LOCTxnccount >= Integer.parseInt(PropsUtil.getDataPropertyValue("IA_LOC_UncatTxnSize")));
		Assert.assertTrue(LOCTxnccount >= Integer.parseInt(PropsUtil.getDataPropertyValue("IA_LOC_UncatTxnSize")));
		if (expenseTrend.isEIAcloseTransPopup()) {
			SeleniumUtil.click(expenseTrend.EIAcloseTransPopup());
			SeleniumUtil.waitForPageToLoad();
		}

	}

	@Test(description = "AT-97381:verify refund page txn", priority = 33, dependsOnMethods = "VerifyLOCAccountTrendPopup_IA")
	public void VerifyLOCAccountRefundTxnTxn_IA() throws Exception {
		SeleniumUtil.click(expenseTrend.EIAMLCDonutBack());
		SeleniumUtil.waitForPageToLoad();
		SeleniumUtil.click(expenseTrend.EIAHLCCatRefundAmount());
		SeleniumUtil.waitForPageToLoad();
		if (Config.appFlag.equals("app") || Config.appFlag.equals("emulator")) {
			SeleniumUtil.click(expenseTrend.UncatAccTxn());
		}
		int txnSize = expenseTrend.EIARefundTXnAccount().size();
		int LOCTxnccount = 0;
		int HELOCtxnCount = 0;
		int SBLOCTxncount = 0;

		for (int i = 0; i < txnSize; i++) {
			if (expenseTrend.EIARefundTXnAccount().get(i).getText().trim().replaceAll("\n", " ")
					.equals(PropsUtil.getDataPropertyValue("IA_LOC_UncatTxnAccount"))) {
				LOCTxnccount = LOCTxnccount + 1;
			} else if (expenseTrend.EIARefundTXnAccount().get(i).getText().trim().replaceAll("\n", " ")
					.equals(PropsUtil.getDataPropertyValue("IA_HELOC_UncatTxnAccount"))) {
				HELOCtxnCount = HELOCtxnCount + 1;
			} else if (expenseTrend.EIARefundTXnAccount().get(i).getText().trim().replaceAll("\n", " ")
					.equals(PropsUtil.getDataPropertyValue("IA_SBLOC_UncatTxnAccount"))) {
				SBLOCTxncount = SBLOCTxncount + 1;
			}
		}

		Assert.assertTrue(LOCTxnccount >= Integer.parseInt(PropsUtil.getDataPropertyValue("IA_LOC_UncatTxnSize")));
		Assert.assertTrue(LOCTxnccount >= Integer.parseInt(PropsUtil.getDataPropertyValue("IA_LOC_UncatTxnSize")));
		Assert.assertTrue(LOCTxnccount >= Integer.parseInt(PropsUtil.getDataPropertyValue("IA_LOC_UncatTxnSize")));
		if (expenseTrend.isEIAcloseTransPopup()) {
			SeleniumUtil.click(expenseTrend.EIAcloseTransPopup());
			SeleniumUtil.waitForPageToLoad();
		}

	}

	public void addManualAdjTxn() {
		expenseTrend.clickEIAMTLink();
		SeleniumUtil.waitForPageToLoad();
		add_Manual.createOneTimeTransaction(PropsUtil.getDataPropertyValue("IA_LOC_MTAmount"),
				PropsUtil.getDataPropertyValue("IA_LOC_MTDescription"),
				PropsUtil.getDataPropertyValue("IA_LOC_MTTxnType"), PropsUtil.getDataPropertyValue("IA_LOC_MTAccounts"),
				PropsUtil.getDataPropertyValue("IA_LOC_MTFrequency"),
				Integer.parseInt(PropsUtil.getDataPropertyValue("IA_MTDate")),
				PropsUtil.getDataPropertyValue("IA_LOC_MTCategory"), PropsUtil.getDataPropertyValue("IA_LOC_MTNote"),
				PropsUtil.getDataPropertyValue("IA_LOC_MTCheck"));
		SeleniumUtil.waitForPageToLoad();

		expenseTrend.clickEIAMTLink();
		SeleniumUtil.waitForPageToLoad();
		add_Manual.createOneTimeTransaction(PropsUtil.getDataPropertyValue("IA_LOC_MTAmount1"),
				PropsUtil.getDataPropertyValue("IA_LOC_MTDescription1"),
				PropsUtil.getDataPropertyValue("IA_LOC_MTTxnType1"),
				PropsUtil.getDataPropertyValue("IA_LOC_MTAccounts1"),
				PropsUtil.getDataPropertyValue("IA_LOC_MTFrequency1"),
				Integer.parseInt(PropsUtil.getDataPropertyValue("IA_LOC_MTDate")),
				PropsUtil.getDataPropertyValue("IA_LOC_MTCategory1"), PropsUtil.getDataPropertyValue("IA_LOC_MTNote1"),
				PropsUtil.getDataPropertyValue("IA_LOC_MTCheck1"));
		SeleniumUtil.waitForPageToLoad();

		expenseTrend.clickEIAMTLink();
		SeleniumUtil.waitForPageToLoad();
		add_Manual.createOneTimeTransaction(PropsUtil.getDataPropertyValue("IA_LOC_MTAmount2"),
				PropsUtil.getDataPropertyValue("IA_LOC_MTDescription2"),
				PropsUtil.getDataPropertyValue("IA_LOC_MTTxnType2"),
				PropsUtil.getDataPropertyValue("IA_LOC_MTAccounts2"),
				PropsUtil.getDataPropertyValue("IA_LOC_MTFrequency2"),
				Integer.parseInt(PropsUtil.getDataPropertyValue("IA_LOC_MTDate")),
				PropsUtil.getDataPropertyValue("IA_LOC_MTCategory2"), PropsUtil.getDataPropertyValue("IA_LOC_MTNote2"),
				PropsUtil.getDataPropertyValue("IA_LOC_MTCheck2"));
		SeleniumUtil.waitForPageToLoad();

	}

	public void addManualReundTxn() {
		expenseTrend.clickEIAMTLink();
		SeleniumUtil.waitForPageToLoad();
		String IA_MTCategory5 = null;
		String IA_MTCategory6 = null;
		String IA_MTCategory7 = null;
		String IA_MTCategory8 = null;
		if (PropsUtil.getEnvPropertyValue("CategoryList43").equalsIgnoreCase("yes")) {
			IA_MTCategory5 = PropsUtil.getDataPropertyValue("EA_LOC_MTCategory5_43");
			IA_MTCategory6 = PropsUtil.getDataPropertyValue("EA_LOC_MTCategory6_43");
			IA_MTCategory7 = PropsUtil.getDataPropertyValue("EA_LOC_MTCategory8_43");
			IA_MTCategory8 = PropsUtil.getDataPropertyValue("EA_LOC_MTCategory9_43");

		} else {
			IA_MTCategory5 = PropsUtil.getDataPropertyValue("EA_LOC_MTCategory6");
			IA_MTCategory6 = PropsUtil.getDataPropertyValue("EA_LOC_MTCategory7");
			IA_MTCategory7 = PropsUtil.getDataPropertyValue("EA_LOC_MTCategory8");
			IA_MTCategory8 = PropsUtil.getDataPropertyValue("EA_LOC_MTCategory10");

		}
		SeleniumUtil.waitForPageToLoad();
		add_Manual.createOneTimeTransaction(PropsUtil.getDataPropertyValue("EA_LOC_MTAmount6"),
				PropsUtil.getDataPropertyValue("EA_LOC_MTDescription6"),
				PropsUtil.getDataPropertyValue("EA_LOC_MTTxnType6"),
				PropsUtil.getDataPropertyValue("EA_LOC_MTAccounts6"), PropsUtil.getDataPropertyValue("EA_MTFrequency6"),
				0, IA_MTCategory5, PropsUtil.getDataPropertyValue("EA_LOC_MTNote6"),
				PropsUtil.getDataPropertyValue("EA_LOC_MTCheck6"));
		SeleniumUtil.waitForPageToLoad(5000);

		expenseTrend.clickEIAMTLink();
		SeleniumUtil.waitForPageToLoad();
		add_Manual.createOneTimeTransaction(PropsUtil.getDataPropertyValue("EA_LOC_MTAmount7"),
				PropsUtil.getDataPropertyValue("EA_LOC_MTDescription7"),
				PropsUtil.getDataPropertyValue("EA_LOC_MTTxnType7"),
				PropsUtil.getDataPropertyValue("EA_LOC_MTAccounts7"),
				PropsUtil.getDataPropertyValue("EA_LOC_MTFrequency7"), 0, IA_MTCategory6,
				PropsUtil.getDataPropertyValue("EA_LOC_MTNote7"), PropsUtil.getDataPropertyValue("EA_LOC_MTCheck7"));
		SeleniumUtil.waitForPageToLoad(5000);

		expenseTrend.clickEIAMTLink();
		SeleniumUtil.waitForPageToLoad();
		add_Manual.createOneTimeTransaction(PropsUtil.getDataPropertyValue("EA_LOC_MTAmount8"),
				PropsUtil.getDataPropertyValue("EA_LOC_MTDescription8"),
				PropsUtil.getDataPropertyValue("EA_LOC_MTTxnType8"),
				PropsUtil.getDataPropertyValue("EA_LOC_MTAccounts7"),
				PropsUtil.getDataPropertyValue("EA_LOC_MTFrequency8"), 0, IA_MTCategory7,
				PropsUtil.getDataPropertyValue("EA_LOC_MTNote8"), PropsUtil.getDataPropertyValue("EA_LOC_MTCheck8"));
		SeleniumUtil.waitForPageToLoad(5000);

		expenseTrend.clickEIAMTLink();
		SeleniumUtil.waitForPageToLoad();
		add_Manual.createOneTimeTransaction(PropsUtil.getDataPropertyValue("EA_LOC_MTAmount10"),
				PropsUtil.getDataPropertyValue("EA_LOC_MTDescription10"),
				PropsUtil.getDataPropertyValue("EA_LOC_MTTxnType10"),
				PropsUtil.getDataPropertyValue("EA_LOC_MTAccounts10"),
				PropsUtil.getDataPropertyValue("EA_LOC_MTFrequency10"), 0, IA_MTCategory8,
				PropsUtil.getDataPropertyValue("EA_LOC_MTNote10"), PropsUtil.getDataPropertyValue("EA_LOC_MTCheck10"));
		SeleniumUtil.waitForPageToLoad(8000);

	}
}
