/**
* Copyright (c) 2018 Yodlee Inc. All Rights Reserved.
*
* This software is the confidential and proprietary information of
* Yodlee, Inc. Use is subject to license terms.
*
* @author Renuka K
*/
package com.omni.pfm.Exapnse_IncomeAnalaysis_Enhancement;

import java.awt.AWTException;
import java.util.ArrayList;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

public class ExpanseAnalysis_Trend_Test extends TestBase {

	ExpLandingPage_Loc expLandingPage;
	Transaction_Filter_Loc filter;
	AccountAddition accAddition;
	Expense_Income_Trend_Loc expenseTrend;
	SeleniumUtil util;
	Transaction_AccountDropDown_Loc txnAcct;
	Add_Manual_Transaction_Loc add_Manual;
	Accounts_ViewByGroup_Loc groupView;
	Logger log = LoggerFactory.getLogger(this.getClass().getSimpleName());

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
		expenseTrend.navigateToEA();
	}

	@Test(description = "AT-92139,AT-92140,AT-92141:verify no accounts page in Eexpanse Page", priority = 1)
	public void noAccountScreen() {

		Assert.assertEquals(expenseTrend.EIANoDataLinkAccount().getText(),
				PropsUtil.getDataPropertyValue("EA_LinkAnAccount"), " link an account button is not displayed");
		Assert.assertEquals(expLandingPage.AccessNotGrantedMsg().getText(),
				PropsUtil.getDataPropertyValue("EA_NoDataHeader"), " no data header is not displayed");
		Assert.assertEquals(expLandingPage.AccessNotGrantedDiscription().getText(),
				PropsUtil.getDataPropertyValue("EA_NoDataAccountDescription"), "no data description is not displayed");
	}

	@Test(description = "AT-92142,AT-92143:verify accounts is added successfully", priority = 2, dependsOnMethods = "noAccountScreen")
	public void addAccounts() throws AWTException, InterruptedException {
		accAddition.linkAccount();
		Assert.assertTrue(
				accAddition.addAggregatedAccount(PropsUtil.getDataPropertyValue("EIA_LandingPage_DagSite"),
						PropsUtil.getDataPropertyValue("EIA_LandingPage_DagSite_Password")),
				"account is not added successfully");
	}

	@Test(description = "AT-92144,AT-92146,AT-92148:verify FTUE popUp is Displaying", priority = 3, dependsOnMethods = "addAccounts")
	public void navigateToExpase() throws AWTException, InterruptedException {
		expenseTrend.pageRefresh();
		expenseTrend.navigateToEA();
		SeleniumUtil.waitForPageToLoad();
		Assert.assertEquals(expenseTrend.EIAFTUEHeader().getText(), PropsUtil.getDataPropertyValue("EA_FTUEHeader1"),
				"FTUE header is not displayed");
		Assert.assertEquals(expenseTrend.EIAFTUEMessage1().getText(), PropsUtil.getDataPropertyValue("EA_FTUEMessage1"),
				"FTUE Message is not displayed");
		Assert.assertEquals(expenseTrend.EIAFTUEMessage2().getText(), PropsUtil.getDataPropertyValue("EA_FTUEMessage2"),
				"FTUE MessageFTUe Message is not displayed");
		Assert.assertEquals(expenseTrend.EIAFTUEContinue().getText(), PropsUtil.getDataPropertyValue("EA_FTUEContinue"),
				"FTUE footer is not displayed");
	}

	@Test(description = "AT-92149,AT-92148:verify FTUE go to analysis", priority = 4, dependsOnMethods = "navigateToExpase")
	public void FTUE() throws AWTException, InterruptedException {
		SeleniumUtil.click(expenseTrend.EIAFTUEContinue());
		SeleniumUtil.waitForPageToLoad(1000);
		Assert.assertEquals(expenseTrend.EIAFTUEHeader().getText(), PropsUtil.getDataPropertyValue("EA_FTUE2Header"),
				"FTUE header is not displayed");
		Assert.assertEquals(expenseTrend.EIAFTUEMessage1().getText(),
				PropsUtil.getDataPropertyValue("EA_FTUE2Message1"), "FTUE messaeg is not displayed");
		Assert.assertEquals(expenseTrend.EIAFTUELinkAccount().getText(),
				PropsUtil.getDataPropertyValue("EA_FTUE2LinkAccount"), "FTUE messaeg is not displayed");
		Assert.assertEquals(expenseTrend.EIAFTUEGoto().getText(), PropsUtil.getDataPropertyValue("EA_FTUE2GoTo"),
				"FTUE messaeg");

	}

	@Test(description = "AT-92124,AT-92150,AT-92152:verify Account dropdown is displaying", priority = 5, dependsOnMethods = "FTUE")

	public void verifyAccountDropDown() {
		SeleniumUtil.click(expenseTrend.EIAFTUEGoto());
		SeleniumUtil.waitForPageToLoad();
		addManualReundTxn();
		Assert.assertEquals(expenseTrend.EIAccountDropDown().getText().trim(),
				PropsUtil.getDataPropertyValue("EA_AllAccountDropDown"),
				"verify account dropwn is not displayed with all expense accounts lable");
	}

	@Test(description = "AT-92125,AT-92184:verify  time filter is displaying", priority = 6, dependsOnMethods = "verifyAccountDropDown")

	public void verifydefaultTimeFilter() {
		Assert.assertTrue(expenseTrend.EIATimeFilterDropDown().isDisplayed(), "time filter is not displayed");
		Assert.assertEquals(expenseTrend.EIATimeFilterDropDownLabel().getText(),
				PropsUtil.getDataPropertyValue("EA_DefaultTimeFilter"), "6 months label is not displayed");
		Assert.assertEquals(expenseTrend.EIATimeFilterDropDownDate().getText().trim(),
				txnAcct.monthStartDate(
						Integer.parseInt(PropsUtil.getDataPropertyValue("EA_Trend_DefaultTF_EndMonthSize")),
						"MM/dd/yyy")
						+ " " + "-" + " "
						+ add_Manual.targateDate1(
								Integer.parseInt(PropsUtil.getDataPropertyValue("EA_Trend_DefaultTF_StartMonthSize"))),
				"6 th month start date and current date is not displayed");

	}

	@Test(description = "AT-92169:past 6 month name should be displayed in MMMM formate", priority = 7, dependsOnMethods = "verifydefaultTimeFilter")

	public void verifydefaultTrendMonth() {
		util.assertExpectedActualList(util.getWebElementsValue(expenseTrend.EIATrendMonthList()),
				expenseTrend.getMonthsValues(6), "past 6 month name is not displayed in MMMM formate");
	}

	@Test(description = "AT-92135,AT-92169:all 6 month expense amount", priority = 8, dependsOnMethods = "verifydefaultTimeFilter")

	public void verifydefaultTrendMonthAmt() {

		util.assertExpectedActualAmountList(util.getWebElementsValue(expenseTrend.EIATrendMonthAmount()),
				PropsUtil.getDataPropertyValue("EA_Default6MonthAmount_List1"),
				"all 6 month expense amount is not displayed");

	}

	@Test(description = "AT-92183:verify  time filter percentage amount is displaying", priority = 9, dependsOnMethods = "verifydefaultTimeFilter")
	public void verifydefaultTrendMonthPerAmt() {
		List<String> ExpectedDiffPerValue = expenseTrend.totalAmountPer(
				util.getWebElementsValue(expenseTrend.EIATrendMonthAmount()), expenseTrend.EIATrendAvg().getText(),
				Integer.parseInt(PropsUtil.getDataPropertyValue("EA_Trend_DefaultTF_getAvgAmount")));
		List<String> actualDiffPerValue = util.getWebElementsValue(expenseTrend.EIATrendMonthAmountPer());
		util.assertExpectedActualList(actualDiffPerValue, ExpectedDiffPerValue,
				"Total dif from avg and % values is not displayed");
	}

	@Test(description = "AT-92170,AT-92171:verify color of the percentage amount", priority = 10, dependsOnMethods = "verifydefaultTimeFilter")
	public void verifyColorOfPer() {
		Assert.assertEquals(
				expenseTrend.EIATrendMonthAmountPer2()
						.get(Integer.parseInt(PropsUtil.getDataPropertyValue("EA_Trend_DefaultTF_GreenColor")))
						.getAttribute("class"),
				PropsUtil.getDataPropertyValue("EA_PerAmountGreen"), "class is not contains green");
		Assert.assertEquals(
				expenseTrend.EIATrendMonthAmountPer2()
						.get(Integer.parseInt(PropsUtil.getDataPropertyValue("EA_Trend_DefaultTF_RedColor")))
						.getAttribute("class"),
				PropsUtil.getDataPropertyValue("EA_PerAmountred"), "class is not contains red");
	}

	@Test(description = "AT-92128:verify  time filter avg amount messsage is  displaying", priority = 11, dependsOnMethods = "verifydefaultTimeFilter")

	public void verifydefaultTrendMonthAvgamt() {
		String avgMsg = expenseTrend.getAvgAmountMessage(
				Integer.parseInt(PropsUtil.getDataPropertyValue("EA_Trend_DefaultTF_AvgAmountStart")),
				Integer.parseInt(PropsUtil.getDataPropertyValue("EA_Trend_DefaultTF_AvgAmountEnd")),
				PropsUtil.getDataPropertyValue("EA_Default6MonthAvgAmountMessage1"),
				PropsUtil.getDataPropertyValue("EA_Default6MonthAvgAmountMessage2"));
		Assert.assertEquals(expenseTrend.EIATrendAvg().getText(), avgMsg, "lass 3 month avg is not displayed");
	}

	@Test(description = "AT-92167:verify  time filter is displaying", priority = 12, dependsOnMethods = "verifydefaultTimeFilter")
	public void verifydefaultTrendChartMonth() {
		if (Config.appFlag.equals("web") || Config.appFlag.equals("false")) {
			List<String> actualValue = new ArrayList<String>();
			actualValue = util.getWebElementsValue(expenseTrend.EIATrendChartXaxis());
			List<String> expectedValue = new ArrayList<String>();
			expectedValue = expenseTrend
					.xAixValue(Integer.parseInt(PropsUtil.getDataPropertyValue("EA_Trend_DefaultTF_getAvgAmount")));
			util.assertExpectedActualList(actualValue, util.revereseList(expectedValue),
					"verify all 6 month xaxis avalue is not displayed ");
		}
	}

	@Test(description = "AT-92136,AT-92137,AT-92180,AT-92185:verify  time filter top 5 txnis displaying", priority = 13, dependsOnMethods = "verifydefaultTimeFilter")

	public void verifydefaultTrendTxn() {
		if (Config.appFlag.equals("web") || Config.appFlag.equals("false")) {
			Assert.assertTrue(
					expenseTrend.verifytopTxnAmountOrder(util.getWebElementsValue(expenseTrend.EIATrendTopTxnAmount())),
					"all transaction is not displayed in descending order ");
			util.assertActualList(util.getWebElementsValue(expenseTrend.EIATrendTopTxnDate()),
					expenseTrend.getToTxnMonth(
							Integer.parseInt(PropsUtil.getDataPropertyValue("EA_Trend_DefaultTF_Top5Txn")),
							Integer.parseInt(PropsUtil.getDataPropertyValue("EA_Trend_DefaultTF_Top5Txn"))),
					"top 5 txn date is not displayed");
			util.assertExpectedActualList(util.getWebElementsValue(expenseTrend.EIATrendTopTxnDescriprion()),
					PropsUtil.getDataPropertyValue("EA_12MonthTimeFilter_Toptxndesc"),
					"top 5 txn decription is not displayed");
			util.assertExpectedActualList(util.getWebElementsValue(expenseTrend.EIATrendTopTxnAccount()),
					PropsUtil.getDataPropertyValue("EA_12MonthTimeFilter_ToptxnAccount"),
					"top 5 txn account is not displayed");

			Assert.assertTrue(expenseTrend.EIATrendTopTxnCatgeory().size() == Integer
					.parseInt(PropsUtil.getDataPropertyValue("EA_12MonthTimeFilter_ToptxnSize")));
			util.assertExpectedActualAmountList(util.getWebElementsValue(expenseTrend.EIATrendTopTxnAmount()),
					PropsUtil.getDataPropertyValue("EA_12MonthTimeFilter_ToptxnAmount"),
					"top 5 txn amount is not displayed");
		}
	}

	@Test(description = "AT-92124:verify all expense accounts option is available when click on expese all accounts dropdown ", priority = 14, dependsOnMethods = "verifyAccountDropDown")

	public void verifyAcctDDownAllAcctLabel() {
		expenseTrend.clickEIAccountDropDown();
		Assert.assertEquals(expenseTrend.EIAllAccountLabel().getText().trim(),
				PropsUtil.getDataPropertyValue("EA_AllExpeneAccounts"),
				"verify all expense accounts option is not available when click on expese all accounts dropdown ");
		Assert.assertEquals(expenseTrend.EIAllAccountCheckBox().getAttribute("aria-checked"),
				PropsUtil.getDataPropertyValue("EA_AllAccountSelected"),
				"verify all expense accounts option is not available when click on expese all accounts dropdown ");
	}

	@Test(description = "AT-92124:verify all added expense accounts are displaying accounts dropdown", priority = 15, dependsOnMethods = "verifyAcctDDownAllAcctLabel")

	public void verifyAcctDDownAllAcctsName() {
		util.assertExpectedActualList(util.getWebElementsValue(expenseTrend.EIAllAccountList()),
				PropsUtil.getDataPropertyValue("EA_AllAccountS"),
				"verify all added expense accounts are not displayed accounts dropdown");
		util.assertActualList(util.getWebElementsAttributeName(expenseTrend.EIAllAccountCheckBoxList(), "aria-checked"),
				PropsUtil.getDataPropertyValue("EA_AllAccountSelected"), "verify all accounts are not selected ");
	}

	@Test(description = "AT-92124verify all accounts check box unselected", priority = 16) // ,,dependsOnMethods="verifyAcctDDownAllAcctsName"
	public void verifyUnselectAcctDDownAllAcct() {
		expenseTrend.clickEIAllAccountCheckBox();
		SeleniumUtil.waitForPageToLoad(4000);
		Assert.assertEquals(expenseTrend.EIAllAccountCheckBox().getAttribute("aria-checked"),
				PropsUtil.getDataPropertyValue("EA_AllAccountUnSelected"),
				"verify all expense accounts option is not available when clikc on expese all accounts dropdown ");
		util.assertActualList(util.getWebElementsAttributeName(expenseTrend.EIAllAccountCheckBoxList(), "aria-checked"),
				PropsUtil.getDataPropertyValue("EA_AllAccountUnSelected"), "verify all accounts are not selected ");
		if (expenseTrend.isSelectAcc_Donebtn()) {
			SeleniumUtil.click(expenseTrend.SelectAcc_Donebtn());
		}
		Assert.assertEquals(expenseTrend.EIANoDataHeader().getText().trim(),
				PropsUtil.getDataPropertyValue("EA_NoDataHeaderLable"), "no data header is not displayed");
		Assert.assertEquals(expenseTrend.EIANoDataDescription().getText().trim(),
				PropsUtil.getDataPropertyValue("EA_NoDataDescription"), "no data description is not displayed");
		Assert.assertEquals(expenseTrend.EIAccountDropDown().getText().trim(),
				PropsUtil.getDataPropertyValue("EA_NoaccountSelectValue"),
				"verify account dropdown is not displayed with all expense accounts lable");
	}

	@Test(description = "AT-92124:verify Account dropdown is displaying", priority = 17, dependsOnMethods = "verifyAccountDropDown")

	public void verifyOneAccountSelected() {

		expenseTrend.selectAccount(PropsUtil.getDataPropertyValue("EA_SelectCashAccount"));
		// if(expenseTrend.isSelectAcc_Donebtn()) {
		// SeleniumUtil.click(expenseTrend.SelectAcc_Donebtn());
		// }
		SeleniumUtil.waitForPageToLoad(4000);
		Assert.assertEquals(expenseTrend.EIAccountDropDown().getText().trim(),
				PropsUtil.getDataPropertyValue("EA_SelectCashAccount"),
				"selected account is not displayed in account drop down");
	}

	@Test(description = "AT-92124:verify Account dropdown is displaying", priority = 18, dependsOnMethods = "verifyOneAccountSelected")

	public void verifyMonthList_OneCashAcc() {
		util.assertExpectedActualList(util.getWebElementsValue(expenseTrend.EIATrendMonthList()),
				expenseTrend.getMonthsValues(
						Integer.parseInt(PropsUtil.getDataPropertyValue("EA_Trend_DefaultTF_getAvgAmount"))),
				"past 6 month name is not displayed in MMMM formate");
	}

	@Test(description = "AT-92135:verify select accounts 6 month amount is displaying", priority = 19, dependsOnMethods = "verifyOneAccountSelected")

	public void verifydefaultTrendMonthAmt_OneCashAcc() {

		util.assertExpectedActualAmountList(util.getWebElementsValue(expenseTrend.EIATrendMonthAmount()),
				PropsUtil.getDataPropertyValue("EA_SelectCashAccountAmount"),
				"all 6 month expense amount is not displayed");

	}

	@Test(description = "AT-92169,AT-92183:verify  selected accounts percentage amount is displaying", priority = 20, dependsOnMethods = "verifyOneAccountSelected")

	public void verifydefaultTrendMonthPerAmt_OneCashAcc() {
		List<String> ExpectedDiffPerValue = expenseTrend.totalAmountPer(
				util.getWebElementsValue(expenseTrend.EIATrendMonthAmount()), expenseTrend.EIATrendAvg().getText(),
				Integer.parseInt(PropsUtil.getDataPropertyValue("EA_Trend_DefaultTF_getAvgAmount")));
		List<String> actualDiffPerValue = util.getWebElementsValue(expenseTrend.EIATrendMonthAmountPer());
		util.assertExpectedActualList(actualDiffPerValue, ExpectedDiffPerValue,
				"Total dif from avg and % values is not displayed");
	}

	@Test(description = "AT-92128:verify  verify selected account avg amount is displaying", priority = 21, dependsOnMethods = "verifyOneAccountSelected")

	public void verifydefaultTrendMonthAvgamt_OneCashAcc() {
		String avgMsg = expenseTrend.getAvgAmountMessage(
				Integer.parseInt(PropsUtil.getDataPropertyValue("EA_Trend_DefaultTF_AvgAmountStart")),
				Integer.parseInt(PropsUtil.getDataPropertyValue("EA_Trend_DefaultTF_AvgAmountEnd")),
				PropsUtil.getDataPropertyValue("EA_Default6MonthAvgAmountMessage1"),
				PropsUtil.getDataPropertyValue("EA_SelectCashAccountAvgAmount"));
		Assert.assertEquals(expenseTrend.EIATrendAvg().getText(), avgMsg, "lass 3 month avg is not displayed");
	}

	@Test(description = "AT-92167:verify selected account x value is displaying", priority = 22, dependsOnMethods = "verifyOneAccountSelected")
	public void verifydefaultTrendChartMonth_OneCashAcc() {
		if (Config.appFlag.equals("web") || Config.appFlag.equals("false")) {
			List<String> actualValue = new ArrayList<String>();
			actualValue = util.getWebElementsValue(expenseTrend.EIATrendChartXaxis());
			List<String> expectedValue = new ArrayList<String>();
			expectedValue = expenseTrend
					.xAixValue1(Integer.parseInt(PropsUtil.getDataPropertyValue("EA_Trend_DefaultTF_getAvgAmount")));
			util.assertExpectedActualList(actualValue, util.revereseList(expectedValue),
					"verify all 6 month xaxis avalue is not displayed");
		}
	}

	@Test(description = "AT-92136,AT-92137,AT-92180:verify selected accounts top txn is displaying", priority = 23, dependsOnMethods = "verifyOneAccountSelected")

	public void verifyTopTransaction_OneCashAcc() {
		if (Config.appFlag.equals("web") || Config.appFlag.equals("false")) {
			Assert.assertTrue(
					expenseTrend.verifytopTxnAmountOrder(util.getWebElementsValue(expenseTrend.EIATrendTopTxnAmount())),
					"top 5 txn is not displayed in descending order");
			util.assertActualList(util.getWebElementsValue(expenseTrend.EIATrendTopTxnDate()),
					expenseTrend.getToTxnMonth(
							Integer.parseInt(PropsUtil.getDataPropertyValue("EA_Trend_DefaultTF_Top5Txn")),
							Integer.parseInt(PropsUtil.getDataPropertyValue("EA_Trend_DefaultTF_Top5Txn"))),
					"transaction date header is not displayed");
			util.assertExpectedActualList(util.getWebElementsValue(expenseTrend.EIATrendTopTxnDescriprion()),
					PropsUtil.getDataPropertyValue("EA_SelectCashAccountTxnDescription"),
					" top 5 txn decription is not displayed");
			util.assertExpectedActualList(util.getWebElementsValue(expenseTrend.EIATrendTopTxnAccount()),
					PropsUtil.getDataPropertyValue("EA_SelectCashAccountTxnAcct"),
					"top 5 txn account is not displayed");

			Assert.assertTrue(expenseTrend.EIATrendTopTxnCatgeory().size() == Integer
					.parseInt(PropsUtil.getDataPropertyValue("EA_12MonthTimeFilter_ToptxnSize")));

			util.assertExpectedActualAmountList(util.getWebElementsValue(expenseTrend.EIATrendTopTxnAmount()),
					PropsUtil.getDataPropertyValue("EA_SelectCashAccountTxnAmount"),
					"top 5 txn amount is not displayed");
		}
	}

	@Test(description = "AT-92124:verify selected account lable in account drop down", priority = 24, dependsOnMethods = "verifyAccountDropDown")
	public void verifyAccountSelected_MultAcc() {
		expenseTrend.selectAccount(PropsUtil.getDataPropertyValue("EA_SelectCashAccount2"));
		SeleniumUtil.waitForPageToLoad();

		Assert.assertEquals(expenseTrend.EIAccountDropDown().getText().trim(),
				PropsUtil.getDataPropertyValue("EA_MultiAccount"),
				"selected account is not displayed in account drop down");
	}

	@Test(description = "AT-92135:verifyselected account 6 month lable", priority = 25, dependsOnMethods = "verifyAccountSelected_MultAcc")

	public void verifyMonthList_MultAcc() {
		util.assertExpectedActualList(util.getWebElementsValue(expenseTrend.EIATrendMonthList()),
				expenseTrend.getMonthsValues(
						Integer.parseInt(PropsUtil.getDataPropertyValue("EA_Trend_DefaultTF_getAvgAmount"))),
				"past 6 month name is not displayed in MMMM formate");
	}

	@Test(description = "AT-92124:verify selected account 6 month amount", priority = 26, dependsOnMethods = "verifyAccountSelected_MultAcc")

	public void verifydefaultTrendMonthAmt_MultAcc() {

		util.assertExpectedActualAmountList(util.getWebElementsValue(expenseTrend.EIATrendMonthAmount()),
				PropsUtil.getDataPropertyValue("EA_Default6MonthAmount_List"),
				"all 6 month expense amount is not displayed");

	}

	@Test(description = "AT-92169,AT-92183:verify selected account percentage amount", priority = 27, dependsOnMethods = "verifyAccountSelected_MultAcc")

	public void verifydefaultTrendMonthPerAmt_MultAcc() {
		List<String> ExpectedDiffPerValue = expenseTrend.totalAmountPer(
				util.getWebElementsValue(expenseTrend.EIATrendMonthAmount()), expenseTrend.EIATrendAvg().getText(),
				Integer.parseInt(PropsUtil.getDataPropertyValue("EA_Trend_DefaultTF_getAvgAmount")));
		List<String> actualDiffPerValue = util.getWebElementsValue(expenseTrend.EIATrendMonthAmountPer());
		util.assertExpectedActualList(actualDiffPerValue, ExpectedDiffPerValue,
				"Total dif from avg and % values is not displayed");
	}

	@Test(description = "AT-92128:verify  selected account last 3 month avg", priority = 28, dependsOnMethods = "verifyAccountSelected_MultAcc")

	public void verifydefaultTrendMonthAvgamt_MultAcc() {
		String avgMsg = expenseTrend.getAvgAmountMessage(
				Integer.parseInt(PropsUtil.getDataPropertyValue("EA_Trend_DefaultTF_AvgAmountStart")),
				Integer.parseInt(PropsUtil.getDataPropertyValue("EA_Trend_DefaultTF_AvgAmountEnd")),
				PropsUtil.getDataPropertyValue("EA_Default6MonthAvgAmountMessage1"),
				PropsUtil.getDataPropertyValue("EA_MultiAccountAvgAmount"));
		Assert.assertEquals(expenseTrend.EIATrendAvg().getText(), avgMsg, "lass 3 month avg is not displayed");
	}

	@Test(description = "AT-92167:verify selected account x axix avalue", priority = 29, dependsOnMethods = "verifyAccountSelected_MultAcc")
	public void verifydefaultTrendChartMonth_MultAcc() {
		if (Config.appFlag.equals("web") || Config.appFlag.equals("false")) {
			List<String> actualValue = new ArrayList<String>();
			actualValue = util.getWebElementsValue(expenseTrend.EIATrendChartXaxis());
			List<String> expectedValue = new ArrayList<String>();
			expectedValue = expenseTrend
					.xAixValue(Integer.parseInt(PropsUtil.getDataPropertyValue("EA_Trend_DefaultTF_getAvgAmount")));
			util.assertExpectedActualList(actualValue, util.revereseList(expectedValue),
					"verify all 6 month xaxis avalue is not displayed");

		}
	}

	@Test(description = "AT-92136,AT-92137,AT-92180:verify selected account top 5 txn", priority = 30, dependsOnMethods = "verifyAccountSelected_MultAcc")

	public void verifyTopTransaction_MultAcc() {
		if (Config.appFlag.equals("web") || Config.appFlag.equals("false")) {
			Assert.assertTrue(
					expenseTrend.verifytopTxnAmountOrder(util.getWebElementsValue(expenseTrend.EIATrendTopTxnAmount())),
					"top 5 txn is not displayed in  descending order");
			util.assertActualList(util.getWebElementsValue(expenseTrend.EIATrendTopTxnDate()),
					expenseTrend.getToTxnMonth(
							Integer.parseInt(PropsUtil.getDataPropertyValue("EA_Trend_DefaultTF_Top5Txn")),
							Integer.parseInt(PropsUtil.getDataPropertyValue("EA_Trend_DefaultTF_Top5Txn"))),
					"top 5 txn header is not displayed");
			util.assertExpectedActualList(util.getWebElementsValue(expenseTrend.EIATrendTopTxnDescriprion()),
					PropsUtil.getDataPropertyValue("EA_12MonthTimeFilter_Toptxndesc"),
					"top 5 txn description is not displayed");
			util.assertExpectedActualList(util.getWebElementsValue(expenseTrend.EIATrendTopTxnAccount()),
					PropsUtil.getDataPropertyValue("EA_12MonthTimeFilter_ToptxnAccount"),
					"top 5 txn account is not displayed");

			Assert.assertTrue(expenseTrend.EIATrendTopTxnCatgeory().size() == Integer
					.parseInt(PropsUtil.getDataPropertyValue("EA_12MonthTimeFilter_ToptxnSize")));

			util.assertExpectedActualAmountList(util.getWebElementsValue(expenseTrend.EIATrendTopTxnAmount()),
					PropsUtil.getDataPropertyValue("EA_12MonthTimeFilter_ToptxnAmount"),
					"top 5 txn amount is not displayed");
		}
	}

	@Test(description = "AT-92125:verify 3 month time filter dropdown value", priority = 31, dependsOnMethods = "verifyAccountDropDown")

	public void TimeFilter3month() {
		expenseTrend.navigateToEA();
		SeleniumUtil.waitForPageToLoad(8000);
		expenseTrend.selectTimeFilter(PropsUtil.getDataPropertyValue("EA_3monthsTimeFilter"));
		SeleniumUtil.waitForPageToLoad(10000);
		Assert.assertTrue(expenseTrend.EIATimeFilterDropDown().isDisplayed(), "time filter is not displayed");
		Assert.assertEquals(expenseTrend.EIATimeFilterDropDownLabel().getText(),
				PropsUtil.getDataPropertyValue("EA_3monthsTimeFilter"), "6 months label is not displayed");
		Assert.assertEquals(expenseTrend.EIATimeFilterDropDownDate().getText().trim(),
				txnAcct.monthStartDate(
						Integer.parseInt(PropsUtil.getDataPropertyValue("EA_Trend_3monthTF_EndMonthSize")), "MM/dd/yyy")
						+ " " + "-" + " "
						+ add_Manual.targateDate1(
								Integer.parseInt(PropsUtil.getDataPropertyValue("EA_Trend_3monthTF_StartMonthSizd"))),
				"6 th month start date and current date is not displayed");

	}

	@Test(description = "AT-92169:verify 3 month Time filter legend montha", priority = 32, dependsOnMethods = "TimeFilter3month")

	public void TimeFilter3MonthTrend() {
		util.assertExpectedActualList(util.getWebElementsValue(expenseTrend.EIATrendMonthList()),
				expenseTrend.getMonthsValues(
						Integer.parseInt(PropsUtil.getDataPropertyValue("EA_Trend_3monthTF_getAvgAmount"))),
				"past 6 month name is not displayed in MMMM formate");
	}

	@Test(description = "AT-92169:3 month time filter 3 month legent amount", priority = 33, dependsOnMethods = "TimeFilter3month")

	public void TimeFilter3MonthAmt() {

		util.assertExpectedActualAmountList(util.getWebElementsValue(expenseTrend.EIATrendMonthAmount()),
				PropsUtil.getDataPropertyValue("EA_3monthsTimeFilter_Amount"),
				"all 6 month expense amount is not displayed");

	}

	@Test(description = "AT-92169,AT-92183:3 month time filter 3 month legent percentage", priority = 34, dependsOnMethods = "TimeFilter3month")

	public void TimeFilter3MonthPerAmt() {
		List<String> ExpectedDiffPerValue = expenseTrend.totalAmountPer(
				util.getWebElementsValue(expenseTrend.EIATrendMonthAmount()), expenseTrend.EIATrendAvg().getText(),
				Integer.parseInt(PropsUtil.getDataPropertyValue("EA_Trend_3monthTF_getAvgAmount")));
		List<String> actualDiffPerValue = util.getWebElementsValue(expenseTrend.EIATrendMonthAmountPer());
		util.assertExpectedActualList(actualDiffPerValue, ExpectedDiffPerValue,
				"Total dif from avg and % values is not displayed");
	}

	@Test(description = "AT-92128:3 month time filter last 3 month avg amount", priority = 35, dependsOnMethods = "TimeFilter3month")

	public void TimeFilter3MonthAvgamt() {
		String avgMsg = expenseTrend.getAvgAmountMessage(
				Integer.parseInt(PropsUtil.getDataPropertyValue("EA_Trend_DefaultTF_AvgAmountStart")),
				Integer.parseInt(PropsUtil.getDataPropertyValue("EA_Trend_DefaultTF_AvgAmountEnd")),
				PropsUtil.getDataPropertyValue("EA_Default6MonthAvgAmountMessage1"),
				PropsUtil.getDataPropertyValue("EA_Default6MonthAvgAmountMessage2"));
		Assert.assertEquals(expenseTrend.EIATrendAvg().getText(), avgMsg, "lass 3 month avg is not displayed");
	}

	@Test(description = "AT-92167:verify  time filter is displaying", priority = 36, dependsOnMethods = "TimeFilter3month")
	public void TimeFilter3MonthChartMonth() {
		List<String> actualValue = new ArrayList<String>();
		actualValue = util.getWebElementsValue(expenseTrend.EIATrendChartXaxis());
		List<String> expectedValue = new ArrayList<String>();
		expectedValue = expenseTrend
				.xAixValue(Integer.parseInt(PropsUtil.getDataPropertyValue("EA_Trend_3monthTF_getAvgAmount")));
		util.assertExpectedActualList(actualValue, util.revereseList(expectedValue),
				"verify all 3 month xaxis avalue is not displayed");
	}

	@Test(description = "AT-92136,AT-92137,AT-92180:verify 3 month time filter top 5 transaction ", priority = 37, dependsOnMethods = "TimeFilter3month")

	public void TimeFilter3MonthTrendTxn() {
		if (Config.appFlag.equals("web") || Config.appFlag.equals("false")) {
			Assert.assertTrue(
					expenseTrend.verifytopTxnAmountOrder(util.getWebElementsValue(expenseTrend.EIATrendTopTxnAmount())),
					"top 5 txn is not displayed in descending order");
			util.assertActualList(util.getWebElementsValue(expenseTrend.EIATrendTopTxnDate()),
					expenseTrend.getToTxnMonth(
							Integer.parseInt(PropsUtil.getDataPropertyValue("EA_Trend_DefaultTF_Top5Txn")),
							Integer.parseInt(PropsUtil.getDataPropertyValue("EA_Trend_DefaultTF_Top5Txn"))),
					"top 5 txn date header is not displayed");
			util.assertExpectedActualList(util.getWebElementsValue(expenseTrend.EIATrendTopTxnDescriprion()),
					PropsUtil.getDataPropertyValue("EA_12MonthTimeFilter_Toptxndesc"),
					"top 5 txn description is not displayed");
			util.assertExpectedActualList(util.getWebElementsValue(expenseTrend.EIATrendTopTxnAccount()),
					PropsUtil.getDataPropertyValue("EA_12MonthTimeFilter_ToptxnAccount"),
					"top 5 txn account is not displayed");

			Assert.assertTrue(expenseTrend.EIATrendTopTxnCatgeory().size() == Integer
					.parseInt(PropsUtil.getDataPropertyValue("EA_12MonthTimeFilter_ToptxnSize")));

			util.assertExpectedActualAmountList(util.getWebElementsValue(expenseTrend.EIATrendTopTxnAmount()),
					PropsUtil.getDataPropertyValue("EA_12MonthTimeFilter_ToptxnAmount"),
					"top 5 txn amount is not displayed");
		}
	}

	@Test(description = "AT-92169:This year time filter dropdown value", priority = 38, dependsOnMethods = "verifyAccountDropDown")
	public void TimeFilterthisYear() {
		expenseTrend.selectTimeFilter(PropsUtil.getDataPropertyValue("EA_ThisYearTmeFilter"));
		SeleniumUtil.waitForPageToLoad(8000);
		Assert.assertTrue(expenseTrend.EIATimeFilterDropDown().isDisplayed(), "time filter is not displayed");
		Assert.assertEquals(expenseTrend.EIATimeFilterDropDownLabel().getText(),
				PropsUtil.getDataPropertyValue("EA_ThisYearTmeFilter"), "this year label is not displayed");
		Assert.assertEquals(expenseTrend.EIATimeFilterDropDownDate().getText().trim(),
				txnAcct.yearStartDate(Integer.parseInt(PropsUtil.getDataPropertyValue("EA_Trend_TYTF_EndMonthSize")),
						"MM/dd/yyy")
						+ " " + "-" + " "
						+ add_Manual.targateDate1(
								Integer.parseInt(PropsUtil.getDataPropertyValue("EA_Trend_TYTF_StartMonthSizd"))),
				"this year start date and current date is not displayed");

	}

	@Test(description = "AT-92169:this year time filter Trend month value", priority = 39, dependsOnMethods = "TimeFilterthisYear")

	public void TimeFilterthisYearTrend() {
		util.assertExpectedActualList(util.getWebElementsValue(expenseTrend.EIATrendMonthList()),
				expenseTrend.getMonthsValues(expenseTrend
						.getMonthMM(Integer.parseInt(PropsUtil.getDataPropertyValue("EA_Trend_TYTF_StartMonthSizd")))),
				"this year name is not displayed in MMMM formate");
	}

	@Test(description = "AT-92169,AT-92183:this year time filter Trend month amount", priority = 41, dependsOnMethods = "TimeFilterthisYear")

	public void TimeFilterthisYearPerAmt() {
		List<String> ExpectedDiffPerValue = expenseTrend.totalAmountPer(
				util.getWebElementsValue(expenseTrend.EIATrendMonthAmount()), expenseTrend.EIATrendAvg().getText(),
				expenseTrend
						.getMonthMM(Integer.parseInt(PropsUtil.getDataPropertyValue("EA_Trend_TYTF_StartMonthSizd"))));
		List<String> actualDiffPerValue = util.getWebElementsValue(expenseTrend.EIATrendMonthAmountPer());
		util.assertExpectedActualList(actualDiffPerValue, ExpectedDiffPerValue,
				"Total dif from avg and % values is not displayed");
	}

	@Test(description = "AT-92128:this year time filter Trend month percentage amount", priority = 42, dependsOnMethods = "TimeFilterthisYear")

	public void TimeFilterthisYear3MonthAvgamt() {
		String avgMsg = expenseTrend.getAvgAmountMessage(
				Integer.parseInt(PropsUtil.getDataPropertyValue("EA_Trend_DefaultTF_AvgAmountStart")),
				Integer.parseInt(PropsUtil.getDataPropertyValue("EA_Trend_DefaultTF_AvgAmountEnd")),
				PropsUtil.getDataPropertyValue("EA_Default6MonthAvgAmountMessage1"),
				PropsUtil.getDataPropertyValue("EA_Default6MonthAvgAmountMessage2"));
		Assert.assertEquals(expenseTrend.EIATrendAvg().getText(), avgMsg, "lass 3 month avg is not displayed");
	}

	@Test(description = "AT-92167:this year time filter x axix avalue", priority = 43, dependsOnMethods = "TimeFilterthisYear")
	public void TimeFilterthisYearChartMonth() {
		if (Config.appFlag.equals("web") || Config.appFlag.equals("false")) {
			List<String> actualValue = new ArrayList<String>();
			actualValue = util.getWebElementsValue(expenseTrend.EIATrendChartXaxis());
			List<String> expectedValue = new ArrayList<String>();
			expectedValue = expenseTrend.xAixValue1(expenseTrend
					.getMonthMM(Integer.parseInt(PropsUtil.getDataPropertyValue("EA_Trend_TYTF_StartMonthSizd"))));
			util.assertExpectedActualList(actualValue, util.revereseList(expectedValue),
					"verify all this year month xaxis avalue is not displayed");

		}
	}

	@Test(description = "AT-92136,AT-92137,AT-92180:this year time filter top 5 txn", priority = 44, dependsOnMethods = "TimeFilterthisYear")

	public void TimeFilterthisYearTrendTxn() {
		if (Config.appFlag.equals("web") || Config.appFlag.equals("false")) {
			Assert.assertTrue(expenseTrend
					.verifytopTxnAmountOrder(util.getWebElementsValue(expenseTrend.EIATrendTopTxnAmount())));
			util.assertActualList(util.getWebElementsValue(expenseTrend.EIATrendTopTxnDate()),
					expenseTrend.getToTxnMonth(
							Integer.parseInt(PropsUtil.getDataPropertyValue("EA_Trend_DefaultTF_Top5Txn")),
							Integer.parseInt(PropsUtil.getDataPropertyValue("EA_Trend_DefaultTF_Top5Txn"))),
					"verify transaction date header is not displayed");
			util.assertExpectedActualList(util.getWebElementsValue(expenseTrend.EIATrendTopTxnDescriprion()),
					PropsUtil.getDataPropertyValue("EA_12MonthTimeFilter_Toptxndesc"),
					"verify top 5 txn description is not displayed");
			util.assertExpectedActualList(util.getWebElementsValue(expenseTrend.EIATrendTopTxnAccount()),
					PropsUtil.getDataPropertyValue("EA_12MonthTimeFilter_ToptxnAccount"),
					"verify top 5 txn amount is not displayed");
			util.assertExpectedActualList(util.getWebElementsValue(expenseTrend.EIATrendTopTxnCatgeory()),
					PropsUtil.getDataPropertyValue("EA_12MonthTimeFilter_ToptxnCategory"),
					"verify top 5 txn catgeory is not displayed");
			util.assertExpectedActualAmountList(util.getWebElementsValue(expenseTrend.EIATrendTopTxnAmount()),
					PropsUtil.getDataPropertyValue("EA_12MonthTimeFilter_ToptxnAmount"),
					"verify top 5 txn amount is not displayed");
		}
	}

	@Test(description = "AT-92125:12  month time filter dropdown value", priority = 45, dependsOnMethods = "verifyAccountDropDown")

	public void TimeFilter12Month() {
		expenseTrend.selectTimeFilter(PropsUtil.getDataPropertyValue("EA_12MonthTimeFilter"));
		SeleniumUtil.waitForPageToLoad(10000);
		Assert.assertTrue(expenseTrend.EIATimeFilterDropDown().isDisplayed(), "time filter is not displayed");
		Assert.assertEquals(expenseTrend.EIATimeFilterDropDownLabel().getText(),
				PropsUtil.getDataPropertyValue("EA_12MonthTimeFilter"), "12 months label is not displayed");
		Assert.assertEquals(expenseTrend.EIATimeFilterDropDownDate().getText().trim(),
				txnAcct.monthStartDate(Integer.parseInt(PropsUtil.getDataPropertyValue("EA_Trend_12TF_EndMonthSize")),
						"MM/dd/yyy")
						+ " " + "-" + " "
						+ add_Manual.targateDate1(
								Integer.parseInt(PropsUtil.getDataPropertyValue("EA_Trend_12TF_StartMonthSize"))),
				"12 month start date and current date is not displayed");

	}

	@Test(description = "AT-92169:12  month time filter legend month name", priority = 46, dependsOnMethods = "TimeFilter12Month")

	public void TimeFilter12MonthTrend() {
		util.assertExpectedActualList(util.getWebElementsValue(expenseTrend.EIATrendMonthList()),
				expenseTrend.getMonthsValues(
						Integer.parseInt(PropsUtil.getDataPropertyValue("EA_Trend_12TF_getAvgAmount"))),
				"12 months name is not displayed in MMMM formate");
	}

	@Test(description = "AT-92169:12 month time filter legend month Amount", priority = 47, dependsOnMethods = "TimeFilter12Month")

	public void TimeFilter12MonthAmt() {

		util.assertExpectedActualAmountList(util.getWebElementsValue(expenseTrend.EIATrendMonthAmount()),
				PropsUtil.getDataPropertyValue("EA_12MonthTimeFilter_Amount"),
				"all 6 month expense amount is not displayed");

	}

	@Test(description = "AT-92169,AT-92183:12 month time filter legend month percentage amount", priority = 48, dependsOnMethods = "TimeFilter12Month")

	public void TimeFilter12MonthPerAmt() {
		List<String> ExpectedDiffPerValue = expenseTrend.totalAmountPer(
				util.getWebElementsValue(expenseTrend.EIATrendMonthAmount()), expenseTrend.EIATrendAvg().getText(),
				Integer.parseInt(PropsUtil.getDataPropertyValue("EA_Trend_12TF_getAvgAmount")));
		List<String> actualDiffPerValue = util.getWebElementsValue(expenseTrend.EIATrendMonthAmountPer());
		util.assertExpectedActualList(actualDiffPerValue, ExpectedDiffPerValue,
				"Total diff from avg and % values is not displayed");
	}

	@Test(description = "AT-92128:12 month time filter legend Avg amount", priority = 49, dependsOnMethods = "TimeFilter12Month")

	public void TimeFilter12MonthAvgamt() {
		String avgMsg = expenseTrend.getAvgAmountMessage(
				Integer.parseInt(PropsUtil.getDataPropertyValue("EA_Trend_DefaultTF_AvgAmountStart")),
				Integer.parseInt(PropsUtil.getDataPropertyValue("EA_Trend_DefaultTF_AvgAmountEnd")),
				PropsUtil.getDataPropertyValue("EA_Default6MonthAvgAmountMessage1"),
				PropsUtil.getDataPropertyValue("EA_Default6MonthAvgAmountMessage2"));
		Assert.assertEquals(expenseTrend.EIATrendAvg().getText(), avgMsg, "lass 3 month avg is not displayed");
	}

	@Test(description = "AT-92167:12 month time filter x-axix values", priority = 50, dependsOnMethods = "TimeFilter12Month")
	public void TimeFilter12MonthChartMonth() {
		if (Config.appFlag.equals("web") || Config.appFlag.equals("false")) {
			List<String> actualValue = new ArrayList<String>();
			actualValue = util.getWebElementsValue(expenseTrend.EIATrendChartXaxis());
			List<String> expectedValue = new ArrayList<String>();
			expectedValue = expenseTrend.xAixValueWithoutSpace(
					Integer.parseInt(PropsUtil.getDataPropertyValue("EA_Trend_12TF_getAvgAmount")));
			util.assertExpectedActualList(actualValue, util.revereseList(expectedValue),
					"12 months time filter x-axix value is not displayed");
		}
	}

	@Test(description = "AT-92136,AT-92137,AT-92180:verify 12 months top 5 txn", priority = 51, dependsOnMethods = "TimeFilter12Month")

	public void TimeFilter12MonthTrendTxn() {
		if (Config.appFlag.equals("web") || Config.appFlag.equals("false")) {
			Assert.assertTrue(expenseTrend
					.verifytopTxnAmountOrder(util.getWebElementsValue(expenseTrend.EIATrendTopTxnAmount())));
			util.assertActualList(util.getWebElementsValue(expenseTrend.EIATrendTopTxnDate()),
					expenseTrend.getToTxnMonth(
							Integer.parseInt(PropsUtil.getDataPropertyValue("EA_Trend_DefaultTF_Top5Txn")),
							Integer.parseInt(PropsUtil.getDataPropertyValue("EA_Trend_DefaultTF_Top5Txn"))),
					"top 5 txn date header is not displayed");
			util.assertExpectedActualList(util.getWebElementsValue(expenseTrend.EIATrendTopTxnDescriprion()),
					PropsUtil.getDataPropertyValue("EA_12MonthTimeFilter_Toptxndesc"),
					"top 5 txn description is not displayed");
			util.assertExpectedActualList(util.getWebElementsValue(expenseTrend.EIATrendTopTxnAccount()),
					PropsUtil.getDataPropertyValue("EA_12MonthTimeFilter_ToptxnAccount"),
					"top 5 txn account is not displayed");

			Assert.assertTrue(expenseTrend.EIATrendTopTxnCatgeory().size() == Integer
					.parseInt(PropsUtil.getDataPropertyValue("EA_12MonthTimeFilter_ToptxnSize")));

			util.assertExpectedActualAmountList(util.getWebElementsValue(expenseTrend.EIATrendTopTxnAmount()),
					PropsUtil.getDataPropertyValue("EA_12MonthTimeFilter_ToptxnAmount"),
					"top 5 txn Amount is not displayed");
		}
	}

	// last year
	@Test(description = "AT-92125:verify last year timr filter dropdown value", priority = 52, dependsOnMethods = "verifyAccountDropDown")

	public void TimeFilterLastYear() {
		expenseTrend.selectTimeFilter(PropsUtil.getDataPropertyValue("EA_LastYearTmeFilter"));
		SeleniumUtil.waitForPageToLoad(10000);
		Assert.assertTrue(expenseTrend.EIATimeFilterDropDown().isDisplayed(), "time filter is not displayed");
		Assert.assertEquals(expenseTrend.EIATimeFilterDropDownLabel().getText(),
				PropsUtil.getDataPropertyValue("EA_LastYearTmeFilter"), "last year label is not displayed");
		Assert.assertEquals(expenseTrend.EIATimeFilterDropDownDate().getText().trim(),
				txnAcct.yearStartDate(Integer.parseInt(PropsUtil.getDataPropertyValue("EA_Trend_LYTF_EndMonthSize")),
						"MM/dd/yyy")
						+ " " + "-" + " "
						+ txnAcct.yearEndDate(
								Integer.parseInt(PropsUtil.getDataPropertyValue("EA_Trend_LYTF_StartMonthSize")),
								"MM/dd/yyy"),
				"last yeartime filter start date and current date is not displayed");

	}

	@Test(description = "AT-92169:last year time filter trend months", priority = 53, dependsOnMethods = "TimeFilterLastYear")

	public void TimeFilterLastYearTrend() {
		util.assertExpectedActualList(util.getWebElementsValue(expenseTrend.EIATrendMonthList()),
				expenseTrend.yearMonths(Integer.parseInt(PropsUtil.getDataPropertyValue("EA_Trend_LYTF_getAvgAmount")),
						Integer.parseInt(PropsUtil.getDataPropertyValue("EA_Trend_LYTF_getAvgAmount2"))),
				"past 6 month name is not displayed in MMMM formate");
	}

	@Test(description = "AT-92169:last year time filter trend month amount", priority = 54, dependsOnMethods = "TimeFilterLastYear")

	public void TimeFilterLastYearAmt() {

		util.assertExpectedActualAmountList(util.getWebElementsValue(expenseTrend.EIATrendMonthAmount()),
				PropsUtil.getDataPropertyValue("EA_LastYearTimeFilter_Amount"),
				"all 6 month expense amount is not displayed");

	}

	@Test(description = "AT-92169,AT-92183:last year time filter trend percentage amount", priority = 55, dependsOnMethods = "TimeFilterLastYear")

	public void TimeFilterLastYearPerAmt() {
		List<String> ExpectedDiffPerValue = expenseTrend.totalAmountPerLastYear(
				util.getWebElementsValue(expenseTrend.EIATrendMonthAmount()), expenseTrend.EIATrendAvg().getText(),
				Integer.parseInt(PropsUtil.getDataPropertyValue("EA_Trend_LYTF_getAvgAmount2")));
		List<String> actualDiffPerValue = util.getWebElementsValue(expenseTrend.EIATrendMonthAmountPer());
		util.assertExpectedActualList(actualDiffPerValue, ExpectedDiffPerValue,
				"Total dif from avg and % values is not displayed");
	}

	@Test(description = "AT-92128:last year time filter avg amount", priority = 56, dependsOnMethods = "TimeFilterLastYear")

	public void TimeFilterLastYearAvgamt() {
		String avgMsg = expenseTrend.getAvgAmountMessage(
				Integer.parseInt(PropsUtil.getDataPropertyValue("EA_Trend_DefaultTF_AvgAmountStart")),
				Integer.parseInt(PropsUtil.getDataPropertyValue("EA_Trend_DefaultTF_AvgAmountEnd")),
				PropsUtil.getDataPropertyValue("EA_Default6MonthAvgAmountMessage1"),
				PropsUtil.getDataPropertyValue("EA_Default6MonthAvgAmountMessage2"));
		Assert.assertEquals(expenseTrend.EIATrendAvg().getText(), avgMsg, "last 3 month avg is not displayedd");
	}

	@Test(description = "AT-92167:last year time filter x -axix value", priority = 57, dependsOnMethods = "TimeFilterLastYear")
	public void TimeFilterLastYearChartMonth() {
		if (Config.appFlag.equals("web") || Config.appFlag.equals("false")) {
			List<String> actualValue = new ArrayList<String>();
			actualValue = util.getWebElementsValue(expenseTrend.EIATrendChartXaxis());
			List<String> expectedValue = new ArrayList<String>();
			expectedValue = expenseTrend.LastYearXaxisMonthChart(
					Integer.parseInt(PropsUtil.getDataPropertyValue("EA_Trend_LYTF_getAvgAmount")),
					Integer.parseInt(PropsUtil.getDataPropertyValue("EA_Trend_LYTF_getAvgAmount2")));
			util.assertExpectedActualList(actualValue, expectedValue, "verify last year  xaxis value is not displayed");
		}
	}

	@Test(description = "AT-92125:custom date time filter dropdown value", priority = 59, dependsOnMethods = "verifyAccountDropDown")
	public void TimeFilterCustomDate() {
		expenseTrend.selectTimeFilter(PropsUtil.getDataPropertyValue("EA_CustomDateTmeFilter"));
		SeleniumUtil.waitForPageToLoad();
		filter.selectCustomDate(
				expenseTrend
						.getMonthsMM(Integer.parseInt(PropsUtil.getDataPropertyValue("EA_Trend_CDTF_EndMonthSize"))),
				expenseTrend
						.getMonthsMM(Integer.parseInt(PropsUtil.getDataPropertyValue("EA_Trend_CDTF_StartMonthSize"))));
		SeleniumUtil.waitForPageToLoad();
		Assert.assertTrue(expenseTrend.EIATimeFilterDropDown().isDisplayed(), "time filter is not displayed");
		Assert.assertEquals(expenseTrend.EIATimeFilterDropDownLabel().getText(),
				PropsUtil.getDataPropertyValue("EA_CustomDateTmeFilter"), "custom date label is not displayed");
		Assert.assertEquals(expenseTrend.EIATimeFilterDropDownDate().getText().trim(),
				expenseTrend.getMonthsMM(Integer.parseInt(PropsUtil.getDataPropertyValue("EA_Trend_CDTF_EndMonthSize")))
						+ " " + "-" + " "
						+ expenseTrend.getMonthsMM(
								Integer.parseInt(PropsUtil.getDataPropertyValue("EA_Trend_CDTF_StartMonthSize"))),
				"custome date time filter start date and current date is not displayed");

	}

	@Test(description = "AT-92169:verify custom date trend months", priority = 60, dependsOnMethods = "TimeFilterCustomDate")

	public void TimeFilterCustomDateTrend() {
		util.assertExpectedActualList(util.getWebElementsValue(expenseTrend.EIATrendMonthList()),
				expenseTrend.getMonthsValues(
						Integer.parseInt(PropsUtil.getDataPropertyValue("EA_Trend_CDTF_getAvgAmount"))),
				"custom date time filter legend months is not displayed");
	}

	@Test(description = "AT-92169:verify custom date filter amount", priority = 61, dependsOnMethods = "TimeFilterCustomDate")

	public void TimeFilterCustomDateAmt() {

		util.assertExpectedActualAmountList(util.getWebElementsValue(expenseTrend.EIATrendMonthAmount()),
				PropsUtil.getDataPropertyValue("EA_CustomDateTmeFilterAmount"),
				"custome date filter amount is not displayed");

	}

	@Test(description = "AT-92169,AT-92183:custom date time filterpercentage amount", priority = 62, dependsOnMethods = "TimeFilterCustomDate")

	public void TimeFilterCustomDatePerAmt() {
		List<String> ExpectedDiffPerValue = expenseTrend.totalAmountPer(
				util.getWebElementsValue(expenseTrend.EIATrendMonthAmount()), expenseTrend.EIATrendAvg().getText(),
				Integer.parseInt(PropsUtil.getDataPropertyValue("EA_Trend_CDTF_getAvgAmount")));
		List<String> actualDiffPerValue = util.getWebElementsValue(expenseTrend.EIATrendMonthAmountPer());
		util.assertExpectedActualList(actualDiffPerValue, ExpectedDiffPerValue,
				"Total diff from avg and % values is not displayed");
	}

	@Test(description = "AT-92128:custom date filter avg amount ", priority = 63, dependsOnMethods = "TimeFilterCustomDate")

	public void TimeFilterCustomDateAvgamt() {
		String avgMsg = expenseTrend.getAvgAmountMessage(
				Integer.parseInt(PropsUtil.getDataPropertyValue("EA_Trend_DefaultTF_AvgAmountStart")),
				Integer.parseInt(PropsUtil.getDataPropertyValue("EA_Trend_DefaultTF_AvgAmountEnd")),
				PropsUtil.getDataPropertyValue("EA_Default6MonthAvgAmountMessage1"),
				PropsUtil.getDataPropertyValue("EA_Default6MonthAvgAmountMessage2"));
		Assert.assertEquals(expenseTrend.EIATrendAvg().getText(), avgMsg, "last 3 month avg is not displayed");
	}

	@Test(description = "AT-92167:custom date filter x axix value", priority = 64, dependsOnMethods = "TimeFilterCustomDate")
	public void TimeFilterCustomDateChartMonth() {
		List<String> actualValue = new ArrayList<String>();
		actualValue = util.getWebElementsValue(expenseTrend.EIATrendChartXaxis());
		List<String> expectedValue = new ArrayList<String>();
		expectedValue = expenseTrend
				.xAixValue1(Integer.parseInt(PropsUtil.getDataPropertyValue("EA_Trend_CDTF_getAvgAmount")));
		util.assertExpectedActualList(actualValue, util.revereseList(expectedValue),
				"custom date tome filter xaxis avalue is not displayed");
	}

	@Test(description = "AT-92136,AT-92137,AT-92180:custom date time filter top 5 txn", priority = 65, dependsOnMethods = "TimeFilterCustomDate")

	public void TimeFilterCustomDateTrendTxn() {
		if (Config.appFlag.equals("web") || Config.appFlag.equals("false")) {
			Assert.assertTrue(expenseTrend
					.verifytopTxnAmountOrder(util.getWebElementsValue(expenseTrend.EIATrendTopTxnAmount())));
			util.assertActualList(util.getWebElementsValue(expenseTrend.EIATrendTopTxnDate()),
					expenseTrend.getToTxnMonth(
							Integer.parseInt(PropsUtil.getDataPropertyValue("EA_Trend_DefaultTF_Top5Txn")),
							Integer.parseInt(PropsUtil.getDataPropertyValue("EA_Trend_DefaultTF_Top5Txn"))),
					"to 5 txn date header is not displayed");
			util.assertExpectedActualList(util.getWebElementsValue(expenseTrend.EIATrendTopTxnDescriprion()),
					PropsUtil.getDataPropertyValue("EA_12MonthTimeFilter_Toptxndesc"),
					"top 5 txn description is not displayed");
			util.assertExpectedActualList(util.getWebElementsValue(expenseTrend.EIATrendTopTxnAccount()),
					PropsUtil.getDataPropertyValue("EA_12MonthTimeFilter_ToptxnAccount"),
					"top 5 txn accounts is not displayed");

			Assert.assertTrue(expenseTrend.EIATrendTopTxnCatgeory().size() == Integer
					.parseInt(PropsUtil.getDataPropertyValue("EA_12MonthTimeFilter_ToptxnSize")));

			util.assertExpectedActualAmountList(util.getWebElementsValue(expenseTrend.EIATrendTopTxnAmount()),
					PropsUtil.getDataPropertyValue("EA_12MonthTimeFilter_ToptxnAmount"),
					"top 5 txn amount is not displayed");

		}
	}

	@Test(description = "AT-92132:custome dtae popup header", priority = 66, dependsOnMethods = "TimeFilterCustomDateTrendTxn")
	public void VerifyCustomeDatePopupHeader() throws Exception {
		expenseTrend.selectTimeFilter(PropsUtil.getDataPropertyValue("EA_CustomDateTmeFilter"));
		SeleniumUtil.waitForPageToLoad();
		if (Config.appFlag.equals("app") || Config.appFlag.equals("emulator")) {
			Assert.assertEquals(
					filter.addManualHeader_AMT_mob()
							.get(Integer.parseInt(PropsUtil.getDataPropertyValue("EA_Trend_CDTF_PopUpHeaderLength")))
							.getText().trim(),
					PropsUtil.getDataPropertyValue("TransactionCustomDateFilterHeader_mob"),
					"custom date popup is not open when you click on custome date filter");
			Assert.assertEquals(filter.TimeFilterCustomDonebtn().getText().trim(),
					PropsUtil.getDataPropertyValue("TransactionCustomDateFilterUpdateText_mob").trim(),
					"custom date update button is not displayed");
		} else {
			Assert.assertEquals(
					filter.addManualHeader_AMT()
							.get(Integer.parseInt(PropsUtil.getDataPropertyValue("EA_Trend_CDTF_PopUpHeaderLength")))
							.getText().trim(),
					PropsUtil.getDataPropertyValue("TransactionCustomDateFilterHeader"),
					"custom date popup is not open when you click on custome date filter");
			Assert.assertEquals(filter.transactionCustomDateFilterStartDateLabel().getText().trim(),
					PropsUtil.getDataPropertyValue("TransactionCustomDateFilterStartDatelabel"),
					"custom date start date label is not displayed");
			Assert.assertEquals(filter.transactionCustomDateFilterEndDateLabel().getText().trim(),
					PropsUtil.getDataPropertyValue("TransactionCustomDateFilterEnddateLabel"),
					"custom date end date label is not displayed");
			Assert.assertEquals(filter.transactionCustomDateFilterUpdate().getText().trim(),
					PropsUtil.getDataPropertyValue("TransactionCustomDateFilterUpdateText"),
					"custom date update button is not displayed");
			Assert.assertTrue(filter.transactionCustomDateFilterCalanderIcon()
					.get(Integer.parseInt(PropsUtil.getDataPropertyValue("EA_Trend_CDTF_PopUpHeaderLength")))
					.isDisplayed(), "from date calander icon is not displayed");
			Assert.assertTrue(filter.transactionCustomDateFilterCalanderIcon()
					.get(Integer.parseInt(PropsUtil.getDataPropertyValue("EA_Trend_CDTF_PopUpEndDateCalIcon")))
					.isDisplayed(), "To date calander icon is not displayed");

		}
	}

	@Test(description = "AT-92132:custome date popup date filed", priority = 67, dependsOnMethods = "VerifyCustomeDatePopupHeader")
	public void VerifyCustomeDateFormate() throws Exception {
		Assert.assertEquals(filter.transactionCustomDateFilterStartDate().getAttribute("placeholder").trim(),
				PropsUtil.getDataPropertyValue("TransactionCustomDateFilterDateFormate"),
				"custome date formate is not displayed in MM/DD/YYYY");
		Assert.assertEquals(filter.transactionCustomDateFilterEndDate().getAttribute("placeholder").trim(),
				PropsUtil.getDataPropertyValue("TransactionCustomDateFilterDateFormate"),
				"custome date formate is not displayed in MM/DD/YYYY");

	}

	@Test(description = "AT-92132:custom date filter error message", priority = 68, dependsOnMethods = "VerifyCustomeDateFormate")
	public void VerifyToDateGreaterThanFormDateErrorMessage() throws Exception {

		filter.selectCustomDate(
				add_Manual.targateDate1(
						Integer.parseInt(PropsUtil.getDataPropertyValue("EA_Trend_CDTF_PopUpStartDate1"))),
				add_Manual
						.targateDate1(Integer.parseInt(PropsUtil.getDataPropertyValue("EA_Trend_CDTF_PopUpEndDate1"))));
		Assert.assertTrue(filter.transactionCustomDateFilterErrorIcon().isDisplayed(),
				"error message icon is not displayed");
		Assert.assertEquals(filter.transactionCustomDateFilterErrorMsg1().getText().trim(),
				PropsUtil.getDataPropertyValue("TransactionCustomDateFilterInvaildMsg"),
				"invalid date message is not displayed");
		Assert.assertEquals(filter.transactionCustomDateFilterErrorMsg2().getText().trim(),
				PropsUtil.getDataPropertyValue("TransactionCustomDateFilterErrorMessage1"),
				"End date must be greater than start date message is not displayed");
	}

	@Test(description = "AT-92132:custom date filter error message", priority = 69, dependsOnMethods = "VerifyToDateGreaterThanFormDateErrorMessage")
	public void VerifyDateRange12monthErrorMessage() throws Exception {
		filter.selectCustomDate(
				add_Manual.targateDate1(
						Integer.parseInt(PropsUtil.getDataPropertyValue("EA_Trend_CDTF_PopUpStartDate2"))),
				add_Manual
						.targateDate1(Integer.parseInt(PropsUtil.getDataPropertyValue("EA_Trend_CDTF_PopUpEndDate2"))));

		Assert.assertTrue(filter.transactionCustomDateFilterErrorIcon().isDisplayed(),
				"error message icon should be displayed");
		Assert.assertEquals(filter.transactionCustomDateFilterErrorMsg1().getText().trim(),
				PropsUtil.getDataPropertyValue("TransactionCustomDateFilterInvaildMsg"),
				"invalid date message is not displayed");
		Assert.assertEquals(filter.transactionCustomDateFilterErrorMsg2().getText().trim(),
				PropsUtil.getDataPropertyValue("TransactionCustomDateFilterErrorMessag2"),
				"Selected time period can't be more than 12 months message is not displayed");
	}

	@Test(description = "AT-92132:custom date popup close ", priority = 70, dependsOnMethods = "VerifyDateRange12monthErrorMessage")
	public void VerifyCustomeDatePopUpClose() throws Exception {
		SeleniumUtil.click(filter.transactionCustomDateFilterClose());
		boolean customeDateClose = false;
		if (filter.addManualHeader_AMT().size() == Integer
				.parseInt(PropsUtil.getDataPropertyValue("EA_Trend_CDTF_PopUpHeaderLength"))) {
			customeDateClose = true;
		}
		Assert.assertTrue(customeDateClose);
	}

	@Test(description = "AT-92153,AT-92154:expense analysis all header link", priority = 71) // ,dependsOnMethods="verifyAccountDropDown"
	public void allLinkOption() throws Exception {
		if (expenseTrend.ismoreBtn_mob_Present()) {
			expenseTrend.clickMore();
			Assert.assertEquals(expenseTrend.EIAMTLink_mob().getText(), PropsUtil.getDataPropertyValue("EA_MTLable"),
					"add manula account link is not displayed");
			expenseTrend.clickMore();
			Assert.assertTrue(expenseTrend.EIALinkAccount_mob().isDisplayed());
		} else {
			Assert.assertEquals(expenseTrend.EIAMTLink().getText(), PropsUtil.getDataPropertyValue("EA_MTLable"),
					"add manula account link is not displayed");
			Assert.assertEquals(expenseTrend.EIAMoreLink().getText(), PropsUtil.getDataPropertyValue("EA_MoreLable"),
					" more link is not displayed");
			Assert.assertEquals(expenseTrend.EIAAccountLink().getText(),
					PropsUtil.getDataPropertyValue("EA_LinkAccountLable"), "link account link is not displayed");
		}
	}

	@Test(description = "AT-92155,AT-92156,AT-92157:expense analysis more link", priority = 72, dependsOnMethods = "allLinkOption")
	public void moreOptions() throws Exception {
		expenseTrend.clickMore();
		SeleniumUtil.waitForPageToLoad();
		Assert.assertEquals(expenseTrend.EIAFeatureTure().getText(),
				PropsUtil.getDataPropertyValue("EA_FeatureTureLable"), "feature link is not displayed");
		if (Config.appFlag.equals("app") || Config.appFlag.equals("emulator")) {
			SeleniumUtil.waitForPageToLoad();
		} else {
			Assert.assertEquals(expenseTrend.EIAPrint().getText(), PropsUtil.getDataPropertyValue("EA_PrintLable"),
					"print link is not displayed");
		}
		Assert.assertEquals(expenseTrend.EIAAlert().getText(), PropsUtil.getDataPropertyValue("EA_AlertSettingLable"),
				"alert link is not displayed");
	}

	@Test(description = "AT-92186:verify group filter value", priority = 73, dependsOnMethods = "verifyAccountDropDown")
	public void groupFilter() throws Exception {
		d.navigate().refresh();
		SeleniumUtil.waitForPageToLoad();
		PageParser.forceNavigate("AccountsPage", d);
		SeleniumUtil.waitForPageToLoad(10000);
		groupView.clikcCreatgroup();
		groupView.createGroup(PropsUtil.getDataPropertyValue("EA_GroupName"),
				PropsUtil.getDataPropertyValue("EA_GroupAccount").split(","));
		SeleniumUtil.waitForPageToLoad();
		expenseTrend.navigateToEA();
		SeleniumUtil.waitForPageToLoad();
		expenseTrend.clickGroup(PropsUtil.getDataPropertyValue("EA_GroupName"));
		if (expenseTrend.isSelectAcc_Donebtn()) {
			SeleniumUtil.click(expenseTrend.SelectAcc_Donebtn());
		}
		SeleniumUtil.waitForPageToLoad();
		Assert.assertEquals(expenseTrend.EIAccountDropDown().getText().trim(),
				PropsUtil.getDataPropertyValue("EA_GroupName"),
				"selected groups account is not displayed in account drop down");

	}

	@Test(description = "AT-92186:verify group time filter", priority = 74, dependsOnMethods = "groupFilter")
	public void timeFIlter_groupFilter() throws Exception {
		Assert.assertTrue(expenseTrend.EIATimeFilterDropDown().isDisplayed(), "time filter is not displayed");
		Assert.assertEquals(expenseTrend.EIATimeFilterDropDownLabel().getText(),
				PropsUtil.getDataPropertyValue("EA_DefaultTimeFilter"), "6 months label is not displayed");
		Assert.assertEquals(expenseTrend.EIATimeFilterDropDownDate().getText().trim(),
				txnAcct.monthStartDate(
						Integer.parseInt(PropsUtil.getDataPropertyValue("EA_Trend_DefaultTF_EndMonthSize")),
						"MM/dd/yyy")
						+ " " + "-" + " "
						+ add_Manual.targateDate1(
								Integer.parseInt(PropsUtil.getDataPropertyValue("EA_Trend_DefaultTF_StartMonthSize"))),
				"6 th month start date and current date is not displayed");

	}

	@Test(description = "AT-92186:verify gorup filter legend months", priority = 75, dependsOnMethods = "groupFilter")
	public void legendMonths_groupFilter() throws Exception {
		util.assertExpectedActualList(util.getWebElementsValue(expenseTrend.EIATrendMonthList()),
				expenseTrend.getMonthsValues(
						Integer.parseInt(PropsUtil.getDataPropertyValue("EA_Trend_DefaultTF_getAvgAmount"))),
				"past 6 month name is not displayed in MMMM formate");
	}

	@Test(description = "AT-92186:verify group filter amount", priority = 76, dependsOnMethods = "groupFilter")
	public void legendAmounts_groupFilter() throws Exception {
		util.assertExpectedActualAmountList(util.getWebElementsValue(expenseTrend.EIATrendMonthAmount()),
				PropsUtil.getDataPropertyValue("EA_SelectCashAccountAmount"),
				"all 6 month expense amount is not displayed");

	}

	@Test(description = "AT-92169,AT-92186:verify group filter percentage amount", priority = 77, dependsOnMethods = "groupFilter")
	public void legendPer_groupFilter() throws Exception {
		List<String> ExpectedDiffPerValue = expenseTrend.totalAmountPer(
				util.getWebElementsValue(expenseTrend.EIATrendMonthAmount()), expenseTrend.EIATrendAvg().getText(),
				Integer.parseInt(PropsUtil.getDataPropertyValue("EA_Trend_DefaultTF_getAvgAmount")));
		List<String> actualDiffPerValue = util.getWebElementsValue(expenseTrend.EIATrendMonthAmountPer());
		util.assertExpectedActualList(actualDiffPerValue, ExpectedDiffPerValue,
				"Total dif from avg and % values is not displayed");
	}

	@Test(description = "AT-92128,AT-92186,AT-92187,AT-92188:verify group filter avg amount", priority = 78, dependsOnMethods = "groupFilter")
	public void legendAvg_groupFilter() throws Exception {

		String avgMsg = expenseTrend.getAvgAmountMessage(
				Integer.parseInt(PropsUtil.getDataPropertyValue("EA_Trend_DefaultTF_AvgAmountStart")),
				Integer.parseInt(PropsUtil.getDataPropertyValue("EA_Trend_DefaultTF_AvgAmountEnd")),
				PropsUtil.getDataPropertyValue("EA_Default6MonthAvgAmountMessage1"),
				PropsUtil.getDataPropertyValue("EA_SelectCashAccountAvgAmount"));
		Assert.assertEquals(expenseTrend.EIATrendAvg().getText(), avgMsg, "lass 3 month avg is not displayed");
	}

	@Test(description = "AT-92167,AT-92186:verify group filter x axix value", priority = 79, dependsOnMethods = "groupFilter")
	public void verifyTrendChartMonth_groupFilter() {
		if (Config.appFlag.equals("web") || Config.appFlag.equals("false")) {
			List<String> actualValue = new ArrayList<String>();
			actualValue = util.getWebElementsValue(expenseTrend.EIATrendChartXaxis());
			List<String> expectedValue = new ArrayList<String>();
			expectedValue = expenseTrend
					.xAixValue1(Integer.parseInt(PropsUtil.getDataPropertyValue("EA_Trend_DefaultTF_getAvgAmount")));
			util.assertExpectedActualList(actualValue, util.revereseList(expectedValue),
					"verify all 6 month xaxis avalue is not displayed");
		}
	}

	@Test(description = "AT-92136,AT-92137,AT-92180:verify group filter top 5 txn", priority = 80, dependsOnMethods = "groupFilter")

	public void verifyTopTransaction_groupFilter() {
		if (Config.appFlag.equals("web") || Config.appFlag.equals("false")) {
			Assert.assertTrue(expenseTrend
					.verifytopTxnAmountOrder(util.getWebElementsValue(expenseTrend.EIATrendTopTxnAmount())));
			util.assertActualList(util.getWebElementsValue(expenseTrend.EIATrendTopTxnDate()),
					expenseTrend.getToTxnMonth(
							Integer.parseInt(PropsUtil.getDataPropertyValue("EA_Trend_DefaultTF_Top5Txn")),
							Integer.parseInt(PropsUtil.getDataPropertyValue("EA_Trend_DefaultTF_Top5Txn"))),
					"top 5 txn date header is not displayed");
			util.assertExpectedActualList(util.getWebElementsValue(expenseTrend.EIATrendTopTxnDescriprion()),
					PropsUtil.getDataPropertyValue("EA_SelectCashAccountTxnDescription"),
					"to 5 txn Description is not displayed");
			util.assertExpectedActualList(util.getWebElementsValue(expenseTrend.EIATrendTopTxnAccount()),
					PropsUtil.getDataPropertyValue("EA_SelectCashAccountTxnAcct"),
					"top 5 txn account is not displayed");

			Assert.assertTrue(expenseTrend.EIATrendTopTxnCatgeory().size() == Integer
					.parseInt(PropsUtil.getDataPropertyValue("EA_12MonthTimeFilter_ToptxnSize")));

			util.assertExpectedActualAmountList(util.getWebElementsValue(expenseTrend.EIATrendTopTxnAmount()),
					PropsUtil.getDataPropertyValue("EA_SelectCashAccountTxnAmount"),
					"top 5 txn amount is not displayed");
		}
	}

	@Test(description = "AT-92173,AT-92174:", priority = 81, dependsOnMethods = "verifyAccountDropDown")
	public void addMTXn() throws Exception {
		expenseTrend.navigateToEA();
		SeleniumUtil.waitForPageToLoad();
		expenseTrend.clickEIAMTLink();
		add_Manual.createOneTimeTransaction(PropsUtil.getDataPropertyValue("EA_MTAmount"),
				PropsUtil.getDataPropertyValue("EA_MTDescription"), PropsUtil.getDataPropertyValue("EA_MTTxnType"),
				PropsUtil.getDataPropertyValue("EA_MTAccounts"), PropsUtil.getDataPropertyValue("EA_MTFrequency"),
				Integer.parseInt(PropsUtil.getDataPropertyValue("EA_MTDate")),
				PropsUtil.getDataPropertyValue("EA_MTCategory"), PropsUtil.getDataPropertyValue("EA_MTNote"),
				PropsUtil.getDataPropertyValue("EA_MTCheck"));
		SeleniumUtil.waitForPageToLoad(8000);
		SeleniumUtil.waitFor(5);
		Assert.assertEquals(
				expenseTrend.EIATrendMonthAmount().get(Integer.parseInt(PropsUtil.getDataPropertyValue("EA_MTDate")))
						.getText().trim(),
				PropsUtil.getDataPropertyValue("EA_UpDatedTrendAmount"),
				"updated data is not populate in month amount");

	}

	@Test(description = "AT-92136,AT-92137,AT-92175,AT-92180:verify  updated top 5 txn is displaying", priority = 82, dependsOnMethods = "addMTXn")

	public void top5txnUpdate() {
		if (Config.appFlag.equals("web") || Config.appFlag.equals("false")) {
			Assert.assertTrue(expenseTrend
					.verifytopTxnAmountOrder(util.getWebElementsValue(expenseTrend.EIATrendTopTxnAmount())));
			util.assertActualList(util.getWebElementsValue(expenseTrend.EIATrendTopTxnDate()),
					expenseTrend.getToTxnMonth(
							Integer.parseInt(PropsUtil.getDataPropertyValue("EA_Trend_DefaultTF_Top5Txn")),
							Integer.parseInt(PropsUtil.getDataPropertyValue("EA_Trend_DefaultTF_Top5Txn"))),
					"top 5 txn date header is not displayed");
			util.assertExpectedActualList(util.getWebElementsValue(expenseTrend.EIATrendTopTxnDescriprion()),
					PropsUtil.getDataPropertyValue("EA_UpDatedTrendAmount_Toptxndesc"),
					"top 5 txn description is not displayed");
			util.assertExpectedActualList(util.getWebElementsValue(expenseTrend.EIATrendTopTxnAccount()),
					PropsUtil.getDataPropertyValue("EA_UpDatedTrendAmount_ToptxnAccount"),
					"top 5 txn account is not displayed");

			Assert.assertTrue(expenseTrend.EIATrendTopTxnCatgeory().size() == Integer
					.parseInt(PropsUtil.getDataPropertyValue("EA_12MonthTimeFilter_ToptxnSize")));

			util.assertExpectedActualAmountList(util.getWebElementsValue(expenseTrend.EIATrendTopTxnAmount()),
					PropsUtil.getDataPropertyValue("EA_UpDatedTrendAmount_ToptxnAmount"),
					"top 5 txn amount is not displayed");
		}
	}

	@Test(description = "AT-92136,AT-92137,AT-92175,AT-92180:verify legend amount  updated", priority = 83) // ,dependsOnMethods="top5txnUpdate"
	public void avgAmountUpdate() throws Exception {
		expenseTrend.clickEIAMTLink();
		add_Manual.createOneTimeTransaction(PropsUtil.getDataPropertyValue("EA_MTAmount"),
				PropsUtil.getDataPropertyValue("EA_MTDescription"), PropsUtil.getDataPropertyValue("EA_MTTxnType"),
				PropsUtil.getDataPropertyValue("EA_MTAccounts"), PropsUtil.getDataPropertyValue("EA_MTFrequency"),
				Integer.parseInt(PropsUtil.getDataPropertyValue("EA_MT2Date")),
				PropsUtil.getDataPropertyValue("EA_MTCategory"), PropsUtil.getDataPropertyValue("EA_MTNote"),
				PropsUtil.getDataPropertyValue("EA_MTCheck"));
		SeleniumUtil.waitForPageToLoad();
		SeleniumUtil.waitFor(5);
		Assert.assertEquals(expenseTrend.EIATrendMonthAmount().get(1).getText(),
				PropsUtil.getDataPropertyValue("EA_UpDatedTrendAmount2"),
				"updated data is not populate in month amount");
		String avgMsg = expenseTrend.getAvgAmountMessage(
				Integer.parseInt(PropsUtil.getDataPropertyValue("EA_Trend_DefaultTF_AvgAmountStart")),
				Integer.parseInt(PropsUtil.getDataPropertyValue("EA_Trend_DefaultTF_AvgAmountEnd")),
				PropsUtil.getDataPropertyValue("EA_Default6MonthAvgAmountMessage1"),
				PropsUtil.getDataPropertyValue("EA_UpDatedTrendAmountAvg"));
		Assert.assertEquals(expenseTrend.EIATrendAvg().getText(), avgMsg, "lass 3 month avg is not displayed");
	}

	@Test(description = "AT-92136,AT-92137,AT-92175,AT-92180:verify percentage amount updated", priority = 84, dependsOnMethods = "avgAmountUpdate")

	public void perAmtUpdated() {
		List<String> ExpectedDiffPerValue = expenseTrend.totalAmountPer(
				util.getWebElementsValue(expenseTrend.EIATrendMonthAmount()), expenseTrend.EIATrendAvg().getText(),
				Integer.parseInt(PropsUtil.getDataPropertyValue("EA_Trend_DefaultTF_getAvgAmount")));
		List<String> actualDiffPerValue = util.getWebElementsValue(expenseTrend.EIATrendMonthAmountPer());
		util.assertExpectedActualList(actualDiffPerValue, ExpectedDiffPerValue,
				"Total dif from avg and % values is not displayed");
	}

	@Test(description = "AT-92177:verify alert popup", priority = 85, dependsOnMethods = "perAmtUpdated")
	public void verifyAlertPopUp() throws Exception {
		expenseTrend.clickAlert();
		SeleniumUtil.waitForPageToLoad();

		Assert.assertEquals(
				expenseTrend.EIAAlertPopUpHeader()
						.get(Integer.parseInt(PropsUtil.getDataPropertyValue("EA_Trend_Alert_PopUpHeader"))).getText(),
				PropsUtil.getDataPropertyValue("EA_AlertSettingPopUpHeader"), "alert popupHeader is not displayed");
		util.assertExpectedActualList(util.getWebElementsValue(expenseTrend.EIAAlertHeader()),
				PropsUtil.getDataPropertyValue("EA_AlertSettingHeader"), "all two alert header is not displayed");
		Assert.assertEquals(expenseTrend.EIAAlertDescription1().getText(),
				PropsUtil.getDataPropertyValue("EA_AlertSettingSummery"), "alert1 description is not displayed");
		Assert.assertEquals(expenseTrend.EIAAlertDescription2().getText(),
				PropsUtil.getDataPropertyValue("EA_AlertSettingAmount"), "alert2 description is not displayed");
	}

	@Test(description = "AT-92178:verify alert is updated", priority = 86) // ,dependsOnMethods="verifyAlertPopUp"
	public void updateAlertSummery() throws Exception {
		expenseTrend.clickSummeryDropDown();
		SeleniumUtil.waitForPageToLoad();
		expenseTrend.selectSummery(PropsUtil.getDataPropertyValue("EA_AlertSettingMonthly"));
		expenseTrend.clickAlert();
		SeleniumUtil.waitForPageToLoad();
		String updatedSemmery = expenseTrend.EIAAlertDescription1().getText();
		SeleniumUtil.waitForPageToLoad();
		SeleniumUtil.waitForPageToLoad(1000);
		Assert.assertEquals(updatedSemmery, PropsUtil.getDataPropertyValue("EA_AlertSettingUpdatedSummery"),
				"alert1 description is not displayed");
		SeleniumUtil.click(expenseTrend.EIAAlertPopUpClose());
	}

	@Test(description = "AT-92172:verify legend message", priority = 87, dependsOnMethods = "verifyAccountDropDown")
	public void legendMsg() throws Exception {
		Assert.assertEquals(expenseTrend.EIATrendDeclimMsg().getText(),
				PropsUtil.getDataPropertyValue("EA_LegendsMessage"), "");
	}

	@Test(description = "AT-92176", priority = 88, dependsOnMethods = "verifyAccountDropDown")
	public void linkAccountPopup() throws Exception {
		SeleniumUtil.click(expenseTrend.EIAAccountLink());
		SeleniumUtil.waitForPageToLoad();
		Assert.assertTrue(expenseTrend.EIALinkAccountPopUP().isDisplayed());
	}

	@Test(description = "AT-92158,AT-92159,AT-92160:verify income analysis header", priority = 90, dependsOnMethods = "groupFilter")

	public void verifyIncomeHeader_IA() {
		expenseTrend.pageRefresh();
		SeleniumUtil.waitForPageToLoad();
		expenseTrend.navigateToEA();
		expenseTrend.selectIncome();
		if (Config.appFlag.equals("app") || Config.appFlag.equals("emulator")) {
			Assert.assertEquals(expenseTrend.EIAHeader().getText().trim(),
					PropsUtil.getDataPropertyValue("EA_IncomeHeader_mob"), "income analysis header is not displayed");
		} else {
			Assert.assertEquals(expenseTrend.EIAHeader().getText().trim(),
					PropsUtil.getDataPropertyValue("EA_IncomeHeader"), "income analysis header is not displayed");
		}
	}

	@Test(description = "AT-92160:verify Expense analysis header", priority = 91, dependsOnMethods = "verifyIncomeHeader_IA")

	public void verifyExpenseHeader_IA() {
		expenseTrend.selectExpense();
		if (Config.appFlag.equals("app") || Config.appFlag.equals("emulator")) {
			Assert.assertEquals(expenseTrend.EIAHeader().getText().trim(),
					PropsUtil.getDataPropertyValue("EA_ExpenseHeader_mob"), "income anlysis header is not displayed");
		} else {
			Assert.assertEquals(expenseTrend.EIAHeader().getText().trim(),
					PropsUtil.getDataPropertyValue("EA_ExpenseHeader"), "income anlysis header is not displayed");
		}
	}

	@Test(description = "AT-92124,AT-92150,AT-92152:verify income anlsyis account filter", priority = 92, dependsOnMethods = "verifyExpenseHeader_IA")

	public void verifyAccountDropDown_IA() {
		expenseTrend.selectIncome();
		SeleniumUtil.waitForPageToLoad();
		expenseTrend.clickEIAccountDropDown();
		SeleniumUtil.click(expenseTrend.EIAselectAcctsList()
				.get(Integer.parseInt(PropsUtil.getDataPropertyValue("EA_AccountLink"))));
		SeleniumUtil.waitForPageToLoad();
		expenseTrend.clickEIAllAccountCheckBox();
		if (expenseTrend.isSelectAcc_Donebtn()) {
			SeleniumUtil.click(expenseTrend.SelectAcc_Donebtn());
			SeleniumUtil.waitForPageToLoad();
		}
		SeleniumUtil.waitFor(10);
		Assert.assertEquals(expenseTrend.EIAccountDropDown().getText().trim(),
				PropsUtil.getDataPropertyValue("IA_AllAccountDropDown"),
				"verify account drop down is not displayed with all expense accounts lable");
	}

	@Test(description = "AT-92125,AT-92184:verify  time filter is displaying", priority = 93, dependsOnMethods = "verifyAccountDropDown_IA")

	public void verifydefaultTimeFilter_IA() {
		addManualAdjTxn();
		Assert.assertTrue(expenseTrend.EIATimeFilterDropDown().isDisplayed(), "verify  time filter is displaying");
		Assert.assertEquals(expenseTrend.EIATimeFilterDropDownLabel().getText(),
				PropsUtil.getDataPropertyValue("IA_DefaultTimeFilter"), "6 months label is not displayed");
		Assert.assertEquals(expenseTrend.EIATimeFilterDropDownDate().getText().trim(),
				txnAcct.monthStartDate(
						Integer.parseInt(PropsUtil.getDataPropertyValue("EA_Trend_DefaultTF_EndMonthSize")),
						"MM/dd/yyy")
						+ " " + "-" + " "
						+ add_Manual.targateDate1(
								Integer.parseInt(PropsUtil.getDataPropertyValue("EA_Trend_DefaultTF_StartMonthSize"))),
				"6 th month start date and current date is not displayed");

	}

	@Test(description = "AT-92169:past 6 month name should be displayed in MMMM formate", priority = 94, dependsOnMethods = "verifydefaultTimeFilter_IA")

	public void verifydefaultTrendMonth_IA() {
		util.assertExpectedActualList(util.getWebElementsValue(expenseTrend.EIATrendMonthList()),
				expenseTrend.getMonthsValues(
						Integer.parseInt(PropsUtil.getDataPropertyValue("EA_Trend_DefaultTF_getAvgAmount"))),
				"past 6 month name is not displayed in MMMM formate");
	}

	@Test(description = "AT-92135,AT-92169:all 6 month income amount", priority = 95, dependsOnMethods = "verifydefaultTimeFilter_IA")

	public void verifydefaultTrendMonthAmt_IA() {

		util.assertExpectedActualAmountList(util.getWebElementsValue(expenseTrend.EIATrendMonthAmount()),
				PropsUtil.getDataPropertyValue("IA_Default6MonthAmount_List"),
				"all 6 month income amount is not displayed");

	}

	@Test(description = "AT-92183:all 6 month income percentage amount", priority = 96, dependsOnMethods = "verifydefaultTimeFilter_IA")
	public void verifydefaultTrendMonthPerAmt_IA() {
		List<String> ExpectedDiffPerValue = expenseTrend.totalAmountPerIncome1(
				util.getWebElementsValue(expenseTrend.EIATrendMonthAmount()), expenseTrend.EIATrendAvg().getText(),
				Integer.parseInt(PropsUtil.getDataPropertyValue("EA_Trend_DefaultTF_getAvgAmount")));
		List<String> actualDiffPerValue = util.getWebElementsValue(expenseTrend.EIATrendMonthAmountPer());
		util.assertExpectedActualList(actualDiffPerValue, ExpectedDiffPerValue,
				"Total dif from avg and % values is not displayed");
	}

	@Test(description = "AT-92170,AT-92171:all 6 month income percentage amount color", priority = 97, dependsOnMethods = "verifydefaultTimeFilter_IA")
	public void verifyColorOfPer_IA() {
		Assert.assertEquals(
				expenseTrend.EIATrendMonthAmountPer2()
						.get(Integer.parseInt(PropsUtil.getDataPropertyValue("IA_Trend_DefaultTF_GreenColor")))
						.getAttribute("class"),
				PropsUtil.getDataPropertyValue("IA_PerAmountGreen"), "lesser pernceatge is no in green");
		Assert.assertEquals(
				expenseTrend.EIATrendMonthAmountPer2()
						.get(Integer.parseInt(PropsUtil.getDataPropertyValue("IA_Trend_DefaultTF_RedColor")))
						.getAttribute("class"),
				PropsUtil.getDataPropertyValue("IA_PerAmountred"), "more than 100 should be pernceatge is not in red");
	}

	@Test(description = "AT-92128:verify  time filter avg amount message", priority = 98, dependsOnMethods = "verifydefaultTimeFilter_IA")

	public void verifydefaultTrendMonthAvgamt_IA() {
		String avgMsg = expenseTrend.getAvgAmountMessage(
				Integer.parseInt(PropsUtil.getDataPropertyValue("EA_Trend_DefaultTF_AvgAmountStart")),
				Integer.parseInt(PropsUtil.getDataPropertyValue("EA_Trend_DefaultTF_AvgAmountEnd")),
				PropsUtil.getDataPropertyValue("IA_Default6MonthAvgAmountMessage1"),
				PropsUtil.getDataPropertyValue("IA_Default6MonthAvgAmountMessage2"));
		Assert.assertEquals(expenseTrend.EIATrendAvg().getText(), avgMsg, "lass 3 month avg is not displayed");
	}

	@Test(description = "AT-92167:verify  time filter  x-axis value", priority = 99, dependsOnMethods = "verifydefaultTimeFilter_IA")
	public void verifydefaultTrendChartMonth_IA() {
		log.info("Started test verifydefaultTrendChartMonth_IA");
		if (Config.appFlag.equals("web") || Config.appFlag.equals("false")) {
			List<String> actualValue = new ArrayList<String>();
			actualValue = util.getWebElementsValue(expenseTrend.EIATrendChartXaxis());
			log.info("Actual values of chart x-axis :: " + actualValue);
			List<String> expectedValue = new ArrayList<String>();
			expectedValue = expenseTrend
					.xAixValue1(Integer.parseInt(PropsUtil.getDataPropertyValue("EA_Trend_DefaultTF_getAvgAmount")));
			log.info("Expense values of chart x-axis :: " + expectedValue);
			util.assertExpectedActualList(actualValue, util.revereseList(expectedValue),
					"verify all 6 month xaxis avalue is not displayed");
		}
	}

	@Test(description = "AT-92136,AT-92137,AT-92180,AT-92185,AT-92138:verift time filter top txn", priority = 100, dependsOnMethods = "verifydefaultTimeFilter_IA")

	public void verifydefaultTrendTxn_IA() {
		if (Config.appFlag.equals("web") || Config.appFlag.equals("false")) {
			Assert.assertTrue(expenseTrend
					.verifytopTxnAmountOrder(util.getWebElementsValue(expenseTrend.EIATrendTopTxnAmount())));
			util.assertActualList(util.getWebElementsValue(expenseTrend.EIATrendTopTxnDate()),
					expenseTrend.getToTxnMonth(
							Integer.parseInt(PropsUtil.getDataPropertyValue("EA_Trend_DefaultTF_Top5Txn")),
							Integer.parseInt(PropsUtil.getDataPropertyValue("EA_Trend_DefaultTF_Top5Txn"))),
					"top 5 txn date header is not displayed");
			// util.assertExpectedActualList(util.getWebElementsValue(expenseTrend.EIATrendTopTxnDescriprion()),
			// PropsUtil.getDataPropertyValue("IA_12MonthTimeFilter_Toptxndesc"), "top 5 txn
			// Description is not displayed");
			Assert.assertTrue(expenseTrend.EIATrendTopTxnDescriprion().size() == Integer
					.parseInt(PropsUtil.getDataPropertyValue("EA_12MonthTimeFilter_ToptxnSize")));

			util.assertExpectedActualList(util.getWebElementsValue(expenseTrend.EIATrendTopTxnAccount()),
					PropsUtil.getDataPropertyValue("IA_12MonthTimeFilter_ToptxnAccount"),
					"top 5 txn accounts is not displayed");
			/*
			 * util.assertExpectedActualList(util.getWebElementsValue(expenseTrend.
			 * EIATrendTopTxnCatgeory()),
			 * PropsUtil.getDataPropertyValue("IA_12MonthTimeFilter_ToptxnCategory"),
			 * "top 5 txn category is not displayed");
			 */
			Assert.assertTrue(expenseTrend.EIATrendTopTxnCatgeory().size() == Integer
					.parseInt(PropsUtil.getDataPropertyValue("EA_12MonthTimeFilter_ToptxnSize")));

			util.assertExpectedActualAmountList(util.getWebElementsValue(expenseTrend.EIATrendTopTxnAmount()),
					PropsUtil.getDataPropertyValue("IA_12MonthTimeFilter_ToptxnAmount"),
					"top 5 txn amount is not displayed");
		}
	}

	@Test(description = "AT-92124:verify all income accounts option is available when click on expese all accounts dropdown ", priority = 101)

	public void verifyAcctDDownAllAcctLabel_IA() {
		expenseTrend.clickEIAccountDropDown();
		Assert.assertEquals(expenseTrend.EIAllAccountLabel().getText().trim(),
				PropsUtil.getDataPropertyValue("IA_AllExpeneAccounts"),
				"verify all income accounts option is not available when click on expese all accounts dropdown ");
		Assert.assertEquals(expenseTrend.EIAllAccountCheckBox().getAttribute("aria-checked"),
				PropsUtil.getDataPropertyValue("IA_AllAccountSelected"),
				"verify all income accounts option is not available when click on expese all accounts dropdown ");
	}

	@Test(description = "AT-92124:verify all added income accounts are displaying accounts dropdown", priority = 102)

	public void verifyAcctDDownAllAcctsName_IA() {
		util.assertExpectedActualList(util.getWebElementsValue(expenseTrend.EIAllAccountList()),
				PropsUtil.getDataPropertyValue("IA_AllAccountS"),
				"all added expense accounts are not displayed accounts dropdown");
		util.assertActualList(util.getWebElementsAttributeName(expenseTrend.EIAllAccountCheckBoxList(), "aria-checked"),
				PropsUtil.getDataPropertyValue("IA_AllAccountSelected"), "all accounts are not selected ");
	}

	@Test(description = "AT-92124verify all accounts check box unselected", priority = 103)
	public void verifyUnselectAcctDDownAllAcct_IA() {
		expenseTrend.clickEIAllAccountCheckBox();
		SeleniumUtil.waitForPageToLoad(4000);
		Assert.assertEquals(expenseTrend.EIAllAccountCheckBox().getAttribute("aria-checked"),
				PropsUtil.getDataPropertyValue("IA_AllAccountUnSelected"),
				" all expense accounts option is not available when click on expese all accounts dropdown ");
		util.assertActualList(util.getWebElementsAttributeName(expenseTrend.EIAllAccountCheckBoxList(), "aria-checked"),
				PropsUtil.getDataPropertyValue("IA_AllAccountUnSelected"), " all accounts are not selected ");
		if (expenseTrend.isSelectAcc_Donebtn()) {
			SeleniumUtil.click(expenseTrend.SelectAcc_Donebtn());
		}
		Assert.assertEquals(expenseTrend.EIANoDataHeader().getText().trim(),
				PropsUtil.getDataPropertyValue("IA_NoDataHeaderLable"), "no data header is not displayed");
		Assert.assertEquals(expenseTrend.EIANoDataDescription().getText().trim(),
				PropsUtil.getDataPropertyValue("IA_NoDataDescription"), "no data description is not displayed");
		Assert.assertEquals(expenseTrend.EIAccountDropDown().getText().trim(),
				PropsUtil.getDataPropertyValue("IA_NoaccountSelectValue"),
				"verify account dropdown is not displayed with all expense accounts lable");
	}

	@Test(description = "AT-92124:verify one account filter", priority = 104, dependsOnMethods = "verifyAccountDropDown_IA")

	public void verifyOneAccountSelected_IA() {
		expenseTrend.selectAccount(PropsUtil.getDataPropertyValue("IA_SelectCashAccount"));
		SeleniumUtil.waitForPageToLoad();
		Assert.assertEquals(expenseTrend.EIAccountDropDown().getText().trim(),
				PropsUtil.getDataPropertyValue("IA_SelectCashAccount"),
				"selected account is not displayed in account drop down");
	}

	@Test(description = "AT-92124:verify one account filter months value", priority = 105, dependsOnMethods = "verifyOneAccountSelected_IA")

	public void verifyMonthList_OneCashAcc_IA() {
		util.assertExpectedActualList(util.getWebElementsValue(expenseTrend.EIATrendMonthList()),
				expenseTrend.getMonthsValues(
						Integer.parseInt(PropsUtil.getDataPropertyValue("EA_Trend_DefaultTF_getAvgAmount"))),
				"past 6 month name is not displayed in MMMM formate");
	}

	@Test(description = "AT-92135:verify one account filter amounts", priority = 106, dependsOnMethods = "verifyOneAccountSelected_IA")

	public void verifydefaultTrendMonthAmt_OneCashAcc_IA() {

		util.assertExpectedActualAmountList(util.getWebElementsValue(expenseTrend.EIATrendMonthAmount()),
				PropsUtil.getDataPropertyValue("IA_SelectCashAccountAmount"),
				"all 6 month expense amount is not displayed");

	}

	@Test(description = "AT-92169,AT-92183:verify one account filter percentage amount", priority = 107, dependsOnMethods = "verifyOneAccountSelected_IA")

	public void verifydefaultTrendMonthPerAmt_OneCashAcc_IA() {
		List<String> ExpectedDiffPerValue = expenseTrend.totalAmountPerIncome1(
				util.getWebElementsValue(expenseTrend.EIATrendMonthAmount()), expenseTrend.EIATrendAvg().getText(),
				Integer.parseInt(PropsUtil.getDataPropertyValue("EA_Trend_DefaultTF_getAvgAmount")));
		List<String> actualDiffPerValue = util.getWebElementsValue(expenseTrend.EIATrendMonthAmountPer());
		util.assertExpectedActualList(actualDiffPerValue, ExpectedDiffPerValue,
				"Total dif from avg and % values is not displayed");
	}

	@Test(description = "AT-92128:verify one account filter avg amount message", priority = 108, dependsOnMethods = "verifyOneAccountSelected_IA")

	public void verifydefaultTrendMonthAvgamt_OneCashAcc_IA() {
		String avgMsg = expenseTrend.getAvgAmountMessage(
				Integer.parseInt(PropsUtil.getDataPropertyValue("EA_Trend_DefaultTF_AvgAmountStart")),
				Integer.parseInt(PropsUtil.getDataPropertyValue("EA_Trend_DefaultTF_AvgAmountEnd")),
				PropsUtil.getDataPropertyValue("IA_Default6MonthAvgAmountMessage1"),
				PropsUtil.getDataPropertyValue("IA_SelectCashAccountAvgAmount"));
		Assert.assertEquals(expenseTrend.EIATrendAvg().getText(), avgMsg, "lass 3 month avg is not displayed");
	}

	@Test(description = "AT-92167:verify one account filter x axix value", priority = 109, dependsOnMethods = "verifyOneAccountSelected_IA")
	public void verifydefaultTrendChartMonth_OneCashAcc_IA() {
		if (Config.appFlag.equals("web") || Config.appFlag.equals("false")) {
			List<String> actualValue = new ArrayList<String>();
			actualValue = util.getWebElementsValue(expenseTrend.EIATrendChartXaxis());
			List<String> expectedValue = new ArrayList<String>();
			expectedValue = expenseTrend
					.xAixValue1(Integer.parseInt(PropsUtil.getDataPropertyValue("EA_Trend_DefaultTF_getAvgAmount")));
			util.assertExpectedActualList(actualValue, util.revereseList(expectedValue),
					" all 6 month xaxis avalue is not displayed");
		}
	}

	@Test(description = "AT-92136,AT-92137,AT-92180:verify one account filter top 5 txn", priority = 110, dependsOnMethods = "verifyOneAccountSelected_IA")

	public void verifyTopTransaction_OneCashAcc_IA() {
		if (Config.appFlag.equals("web") || Config.appFlag.equals("false")) {
			Assert.assertTrue(expenseTrend
					.verifytopTxnAmountOrder(util.getWebElementsValue(expenseTrend.EIATrendTopTxnAmount())));
			util.assertActualList(util.getWebElementsValue(expenseTrend.EIATrendTopTxnDate()),
					expenseTrend.getToTxnMonth(
							Integer.parseInt(PropsUtil.getDataPropertyValue("EA_Trend_DefaultTF_Top5Txn")),
							Integer.parseInt(PropsUtil.getDataPropertyValue("EA_Trend_DefaultTF_Top5Txn"))),
					"top 5 txn date header is not displayed");
			// util.assertExpectedActualList(util.getWebElementsValue(expenseTrend.EIATrendTopTxnDescriprion()),
			// PropsUtil.getDataPropertyValue("IA_SelectCashAccountTxnDescription"), "top 5
			// txn description is not displayed");
			Assert.assertTrue(expenseTrend.EIATrendTopTxnDescriprion().size() == Integer
					.parseInt(PropsUtil.getDataPropertyValue("EA_12MonthTimeFilter_ToptxnSize")));

			util.assertExpectedActualList(util.getWebElementsValue(expenseTrend.EIATrendTopTxnAccount()),
					PropsUtil.getDataPropertyValue("IA_SelectCashAccountTxnAcct"),
					"top 5 txn account is not displayed");
			/*
			 * util.assertExpectedActualList(util.getWebElementsValue(expenseTrend.
			 * EIATrendTopTxnCatgeory()),
			 * PropsUtil.getDataPropertyValue("IA_SelectCashAccountTxnCategory"),
			 * "top 5 txn Category is not displayed");
			 */
			Assert.assertTrue(expenseTrend.EIATrendTopTxnCatgeory().size() == Integer
					.parseInt(PropsUtil.getDataPropertyValue("EA_12MonthTimeFilter_ToptxnSize")));

			util.assertExpectedActualAmountList(util.getWebElementsValue(expenseTrend.EIATrendTopTxnAmount()),
					PropsUtil.getDataPropertyValue("IA_SelectCashAccountTxnAmount"),
					"top 5 txn amount is not displayed");
		}
	}

	@Test(description = "AT-92124:verify multiple account filter", priority = 111, dependsOnMethods = "verifyTopTransaction_OneCashAcc_IA")
	public void verifyAccountSelected_MultAcc_IA() {
		expenseTrend.selectAccount(PropsUtil.getDataPropertyValue("IA_SelectCashAccount2"));
		SeleniumUtil.waitForPageToLoad();

		Assert.assertEquals(expenseTrend.EIAccountDropDown().getText().trim(),
				PropsUtil.getDataPropertyValue("IA_MultiAccount"),
				"selected account is not displayed in account drop down");
	}

	@Test(description = "AT-92135:verify multiple account filter month value", priority = 112, dependsOnMethods = "verifyAccountSelected_MultAcc_IA")

	public void verifyMonthList_MultAcc_IA() {
		util.assertExpectedActualList(util.getWebElementsValue(expenseTrend.EIATrendMonthList()),
				expenseTrend.getMonthsValues(
						Integer.parseInt(PropsUtil.getDataPropertyValue("EA_Trend_DefaultTF_getAvgAmount"))),
				"past 6 month name is not displayed in MMMM formate");
	}

	@Test(description = "AT-92124:verify multiple account filter amount", priority = 113, dependsOnMethods = "verifyAccountSelected_MultAcc_IA")

	public void verifydefaultTrendMonthAmt_MultAcc_IA() {

		util.assertExpectedActualAmountList(util.getWebElementsValue(expenseTrend.EIATrendMonthAmount()),
				PropsUtil.getDataPropertyValue("IA_Default6MonthAmount_List"),
				"all 6 month expense amount is not displayed");

	}

	@Test(description = "AT-92169,AT-92183:verify multiple account percentage amount", priority = 114, dependsOnMethods = "verifyAccountSelected_MultAcc_IA")

	public void verifydefaultTrendMonthPerAmt_MultAcc_IA() {
		List<String> ExpectedDiffPerValue = expenseTrend.totalAmountPerIncome1(
				util.getWebElementsValue(expenseTrend.EIATrendMonthAmount()), expenseTrend.EIATrendAvg().getText(),
				Integer.parseInt(PropsUtil.getDataPropertyValue("EA_Trend_DefaultTF_getAvgAmount")));
		List<String> actualDiffPerValue = util.getWebElementsValue(expenseTrend.EIATrendMonthAmountPer());
		util.assertExpectedActualList(actualDiffPerValue, ExpectedDiffPerValue,
				"Total dif from avg and % values is not displayed");
	}

	@Test(description = "AT-92128:verify multiple account filter avg amount message", priority = 115, dependsOnMethods = "verifyAccountSelected_MultAcc_IA")

	public void verifydefaultTrendMonthAvgamt_MultAcc_IA() {
		String avgMsg = expenseTrend.getAvgAmountMessage(
				Integer.parseInt(PropsUtil.getDataPropertyValue("EA_Trend_DefaultTF_AvgAmountStart")),
				Integer.parseInt(PropsUtil.getDataPropertyValue("EA_Trend_DefaultTF_AvgAmountEnd")),
				PropsUtil.getDataPropertyValue("IA_Default6MonthAvgAmountMessage1"),
				PropsUtil.getDataPropertyValue("IA_MultiAccountAvgAmount"));
		Assert.assertEquals(expenseTrend.EIATrendAvg().getText(), avgMsg, "lass 3 month avg is not displayed");
	}

	@Test(description = "AT-92167:verify multiple account filter chart x axix value", priority = 116, dependsOnMethods = "verifyAccountSelected_MultAcc_IA")
	public void verifydefaultTrendChartMonth_MultAcc_IA() {
		if (Config.appFlag.equals("web") || Config.appFlag.equals("false")) {
			List<String> actualValue = new ArrayList<String>();
			actualValue = util.getWebElementsValue(expenseTrend.EIATrendChartXaxis());
			List<String> expectedValue = new ArrayList<String>();
			expectedValue = expenseTrend
					.xAixValue(Integer.parseInt(PropsUtil.getDataPropertyValue("EA_Trend_DefaultTF_getAvgAmount")));
			util.assertExpectedActualList(actualValue, util.revereseList(expectedValue),
					"all 6 month xaxis avalue is not displayed");
		}
	}

	@Test(description = "AT-92136,AT-92137,AT-92180:verify multiple account filter top 5 txn", priority = 117, dependsOnMethods = "verifyAccountSelected_MultAcc_IA")

	public void verifyTopTransaction_MultAcc_IA() {
		if (Config.appFlag.equals("web") || Config.appFlag.equals("false")) {
			Assert.assertTrue(expenseTrend
					.verifytopTxnAmountOrder(util.getWebElementsValue(expenseTrend.EIATrendTopTxnAmount())));
			util.assertActualList(util.getWebElementsValue(expenseTrend.EIATrendTopTxnDate()),
					expenseTrend.getToTxnMonth(
							Integer.parseInt(PropsUtil.getDataPropertyValue("EA_Trend_DefaultTF_Top5Txn")),
							Integer.parseInt(PropsUtil.getDataPropertyValue("EA_Trend_DefaultTF_Top5Txn"))),
					"top 5 txn date header is not displayed");
			// util.assertExpectedActualList(util.getWebElementsValue(expenseTrend.EIATrendTopTxnDescriprion()),
			// PropsUtil.getDataPropertyValue("IA_12MonthTimeFilter_Toptxndesc"), "top 5 txn
			// description is not displayed");
			Assert.assertTrue(expenseTrend.EIATrendTopTxnDescriprion().size() == Integer
					.parseInt(PropsUtil.getDataPropertyValue("EA_12MonthTimeFilter_ToptxnSize")));

			util.assertExpectedActualList(util.getWebElementsValue(expenseTrend.EIATrendTopTxnAccount()),
					PropsUtil.getDataPropertyValue("IA_12MonthTimeFilter_ToptxnAccount"),
					"top 5 txn account is not displayed");
			/*
			 * util.assertExpectedActualList(util.getWebElementsValue(expenseTrend.
			 * EIATrendTopTxnCatgeory()),
			 * PropsUtil.getDataPropertyValue("IA_12MonthTimeFilter_ToptxnCategory"),
			 * "top 5 txn Category is not displayed");
			 */
			Assert.assertTrue(expenseTrend.EIATrendTopTxnCatgeory().size() == Integer
					.parseInt(PropsUtil.getDataPropertyValue("EA_12MonthTimeFilter_ToptxnSize")));

			util.assertExpectedActualAmountList(util.getWebElementsValue(expenseTrend.EIATrendTopTxnAmount()),
					PropsUtil.getDataPropertyValue("IA_12MonthTimeFilter_ToptxnAmount"),
					"top 5 txn amount is not displayed");
		}
	}

	@Test(description = "AT-92125:verify 3 months time filter", priority = 118, dependsOnMethods = "verifyIncomeHeader_IA")

	public void TimeFilter3month_IA() {
		expenseTrend.selectIncome();
		expenseTrend.selectTimeFilter(PropsUtil.getDataPropertyValue("IA_3monthsTimeFilter"));
		SeleniumUtil.waitForPageToLoad(8000);
		Assert.assertTrue(expenseTrend.EIATimeFilterDropDown().isDisplayed(), "time filter is not displayed");
		Assert.assertEquals(expenseTrend.EIATimeFilterDropDownLabel().getText(),
				PropsUtil.getDataPropertyValue("EA_3monthsTimeFilter"), "6 months label is not displayed");
		Assert.assertEquals(expenseTrend.EIATimeFilterDropDownDate().getText().trim(),
				txnAcct.monthStartDate(
						Integer.parseInt(PropsUtil.getDataPropertyValue("EA_Trend_3monthTF_EndMonthSize")), "MM/dd/yyy")
						+ " " + "-" + " "
						+ add_Manual.targateDate1(
								Integer.parseInt(PropsUtil.getDataPropertyValue("EA_Trend_3monthTF_StartMonthSizd"))),
				"6 th month start date and current date is not displayed");

	}

	@Test(description = "AT-92169:verify 3 months time filter months value", priority = 119, dependsOnMethods = "TimeFilter3month_IA")
	public void TimeFilter3MonthTrend_IA() {
		SeleniumUtil.waitFor(10);
		util.assertExpectedActualList(util.getWebElementsValue(expenseTrend.EIATrendMonthList()),
				expenseTrend.getMonthsValues(
						Integer.parseInt(PropsUtil.getDataPropertyValue("EA_Trend_3monthTF_getAvgAmount"))),
				"past 6 month name is not displayed in MMMM formate");
	}

	@Test(description = "AT-92169:verify 3 months time filter amount", priority = 120, dependsOnMethods = "TimeFilter3month_IA")
	public void TimeFilter3MonthAmt_IA() {
		util.assertExpectedActualAmountList(util.getWebElementsValue(expenseTrend.EIATrendMonthAmount()),
				PropsUtil.getDataPropertyValue("IA_3monthsTimeFilter_Amount"),
				"all 6 month expense amount is not displayed");

	}

	@Test(description = "AT-92169,AT-92183:verify 3 months time filter percentage amount", priority = 121, dependsOnMethods = "TimeFilter3month_IA")

	public void TimeFilter3MonthPerAmt_IA() {
		List<String> ExpectedDiffPerValue = expenseTrend.totalAmountPerIncome1(
				util.getWebElementsValue(expenseTrend.EIATrendMonthAmount()), expenseTrend.EIATrendAvg().getText(),
				Integer.parseInt(PropsUtil.getDataPropertyValue("EA_Trend_3monthTF_getAvgAmount")));
		List<String> actualDiffPerValue = util.getWebElementsValue(expenseTrend.EIATrendMonthAmountPer());
		util.assertExpectedActualList(actualDiffPerValue, ExpectedDiffPerValue,
				"Total dif from avg and % values is not displayed");
	}

	@Test(description = "AT-92128:verify 3 months time filter avg amount message", priority = 122)

	public void TimeFilter3MonthAvgamt_IA() {
		String avgMsg = expenseTrend.getAvgAmountMessage(
				Integer.parseInt(PropsUtil.getDataPropertyValue("EA_Trend_DefaultTF_AvgAmountStart")),
				Integer.parseInt(PropsUtil.getDataPropertyValue("EA_Trend_DefaultTF_AvgAmountEnd")),
				PropsUtil.getDataPropertyValue("IA_Default6MonthAvgAmountMessage1"),
				PropsUtil.getDataPropertyValue("IA_Default6MonthAvgAmountMessage2"));
		Assert.assertEquals(expenseTrend.EIATrendAvg().getText(), avgMsg, "lass 3 month avg is not displayed");
	}

	@Test(description = "AT-92167:verify 3 months time filter chart x axix value", priority = 123, dependsOnMethods = "TimeFilter3month_IA")
	public void TimeFilter3MonthChartMonth_IA() {
		List<String> actualValue = new ArrayList<String>();
		actualValue = util.getWebElementsValue(expenseTrend.EIATrendChartXaxis());
		List<String> expectedValue = new ArrayList<String>();
		expectedValue = expenseTrend
				.xAixValue(Integer.parseInt(PropsUtil.getDataPropertyValue("EA_Trend_3monthTF_getAvgAmount")));
		util.assertExpectedActualList(actualValue, util.revereseList(expectedValue),
				"all 6 month xaxis avalue is not displayed");
	}

	@Test(description = "AT-92136,AT-92137,AT-92180:verify 3 months time filter top 5 txn", priority = 124, dependsOnMethods = "TimeFilter3month_IA")

	public void TimeFilter3MonthTrendTxn_IA() {
		if (Config.appFlag.equals("web") || Config.appFlag.equals("false")) {
			Assert.assertTrue(expenseTrend
					.verifytopTxnAmountOrder(util.getWebElementsValue(expenseTrend.EIATrendTopTxnAmount())));
			util.assertActualList(util.getWebElementsValue(expenseTrend.EIATrendTopTxnDate()),
					expenseTrend.getToTxnMonth(
							Integer.parseInt(PropsUtil.getDataPropertyValue("EA_Trend_DefaultTF_Top5Txn")),
							Integer.parseInt(PropsUtil.getDataPropertyValue("EA_Trend_DefaultTF_Top5Txn"))),
					"top 5 txn date header is not displayed");
			// util.assertExpectedActualList(util.getWebElementsValue(expenseTrend.EIATrendTopTxnDescriprion()),
			// PropsUtil.getDataPropertyValue("IA_12MonthTimeFilter_Toptxndesc"), "top 5 txn
			// description is not displayed");
			Assert.assertTrue(expenseTrend.EIATrendTopTxnDescriprion().size() == Integer
					.parseInt(PropsUtil.getDataPropertyValue("EA_12MonthTimeFilter_ToptxnSize")));

			util.assertExpectedActualList(util.getWebElementsValue(expenseTrend.EIATrendTopTxnAccount()),
					PropsUtil.getDataPropertyValue("IA_12MonthTimeFilter_ToptxnAccount"),
					"top 5 txn account is not displayed");
			/*
			 * util.assertExpectedActualList(util.getWebElementsValue(expenseTrend.
			 * EIATrendTopTxnCatgeory()),
			 * PropsUtil.getDataPropertyValue("IA_12MonthTimeFilter_ToptxnCategory"), "");
			 */
			Assert.assertTrue(expenseTrend.EIATrendTopTxnCatgeory().size() == Integer
					.parseInt(PropsUtil.getDataPropertyValue("EA_12MonthTimeFilter_ToptxnSize")));

			util.assertExpectedActualAmountList(util.getWebElementsValue(expenseTrend.EIATrendTopTxnAmount()),
					PropsUtil.getDataPropertyValue("IA_12MonthTimeFilter_ToptxnAmount"),
					"top 5 txn amount is not displayed");
		}
	}

	@Test(description = "AT-92169:verify this year time filter", priority = 125, dependsOnMethods = "TimeFilter3month_IA")
	public void TimeFilterthisYear_IA() {
		expenseTrend.selectTimeFilter(PropsUtil.getDataPropertyValue("IA_ThisYearTmeFilter"));
		SeleniumUtil.waitForPageToLoad(8000);
		Assert.assertTrue(expenseTrend.EIATimeFilterDropDown().isDisplayed(), "verify  time filter is displaying");
		Assert.assertEquals(expenseTrend.EIATimeFilterDropDownLabel().getText(),
				PropsUtil.getDataPropertyValue("IA_ThisYearTmeFilter"), "6 months label should be displayed");
		Assert.assertEquals(expenseTrend.EIATimeFilterDropDownDate().getText().trim(),
				txnAcct.yearStartDate(Integer.parseInt(PropsUtil.getDataPropertyValue("EA_Trend_TYTF_EndMonthSize")),
						"MM/dd/yyy")
						+ " " + "-" + " "
						+ add_Manual.targateDate1(
								Integer.parseInt(PropsUtil.getDataPropertyValue("EA_Trend_TYTF_StartMonthSizd"))),
				"6 th month start date and current date should be displayed");

	}

	@Test(description = "AT-92169:verify this year time filter amount", priority = 126, dependsOnMethods = "TimeFilterthisYear_IA")

	public void TimeFilterthisYearTrend_IA() {
		util.assertExpectedActualList(util.getWebElementsValue(expenseTrend.EIATrendMonthList()),
				expenseTrend.getMonthsValues(expenseTrend
						.getMonthMM(Integer.parseInt(PropsUtil.getDataPropertyValue("EA_Trend_TYTF_StartMonthSizd")))),
				"past 6 month name is not displayed in MMMM formate");
	}

	@Test(description = "AT-92169,AT-92183:verify this year time filter percentage amount", priority = 127, dependsOnMethods = "TimeFilterthisYear_IA")

	public void TimeFilterthisYearPerAmt_IA() {
		List<String> ExpectedDiffPerValue = expenseTrend.totalAmountPerIncome(
				util.getWebElementsValue(expenseTrend.EIATrendMonthAmount()), expenseTrend.EIATrendAvg().getText(),
				expenseTrend
						.getMonthMM(Integer.parseInt(PropsUtil.getDataPropertyValue("EA_Trend_TYTF_StartMonthSizd"))));
		List<String> actualDiffPerValue = util.getWebElementsValue(expenseTrend.EIATrendMonthAmountPer());
		util.assertExpectedActualList(actualDiffPerValue, ExpectedDiffPerValue,
				"Total dif from avg and % values is not displayed");
	}

	@Test(description = "AT-92128:verify this year time filter top 5 txn", priority = 128, dependsOnMethods = "TimeFilterthisYear_IA")

	public void TimeFilterthisYear3MonthAvgamt_IA() {
		String avgMsg = expenseTrend.getAvgAmountMessage(
				Integer.parseInt(PropsUtil.getDataPropertyValue("EA_Trend_DefaultTF_AvgAmountStart")),
				Integer.parseInt(PropsUtil.getDataPropertyValue("EA_Trend_DefaultTF_AvgAmountEnd")),
				PropsUtil.getDataPropertyValue("IA_Default6MonthAvgAmountMessage1"),
				PropsUtil.getDataPropertyValue("IA_Default6MonthAvgAmountMessage2"));
		Assert.assertEquals(expenseTrend.EIATrendAvg().getText(), avgMsg, "lass 3 month avg is not displayed");
	}

	@Test(description = "AT-92167:verify this year time filter chart x axix value", priority = 129, dependsOnMethods = "TimeFilterthisYear_IA")
	public void TimeFilterthisYearChartMonth_IA() {
		if (Config.appFlag.equals("web") || Config.appFlag.equals("false")) {
			List<String> actualValue = new ArrayList<String>();
			actualValue = util.getWebElementsValue(expenseTrend.EIATrendChartXaxis());
			List<String> expectedValue = new ArrayList<String>();
			expectedValue = expenseTrend.xAixValue(expenseTrend
					.getMonthMM(Integer.parseInt(PropsUtil.getDataPropertyValue("EA_Trend_TYTF_StartMonthSizd"))));
			util.assertExpectedActualList(actualValue, util.revereseList(expectedValue),
					"all 6 month xaxis avalue is not displayed");
		}
	}

	@Test(description = "AT-92136,AT-92137,AT-92180:verify This year time filter top 5 txn", priority = 130, dependsOnMethods = "TimeFilterthisYear_IA")

	public void TimeFilterthisYearTrendTxn_IA() {
		if (Config.appFlag.equals("web") || Config.appFlag.equals("false")) {
			Assert.assertTrue(expenseTrend
					.verifytopTxnAmountOrder(util.getWebElementsValue(expenseTrend.EIATrendTopTxnAmount())));
			util.assertActualList(util.getWebElementsValue(expenseTrend.EIATrendTopTxnDate()),
					expenseTrend.getToTxnMonth(
							Integer.parseInt(PropsUtil.getDataPropertyValue("EA_Trend_DefaultTF_Top5Txn")),
							Integer.parseInt(PropsUtil.getDataPropertyValue("EA_Trend_DefaultTF_Top5Txn"))),
					"top 5 txn date header is not displayed");
			util.assertExpectedActualList(util.getWebElementsValue(expenseTrend.EIATrendTopTxnDescriprion()),
					PropsUtil.getDataPropertyValue("IA_12MonthTimeFilter_Toptxndesc"),
					"top 5 txn description is not displayed");
			util.assertExpectedActualList(util.getWebElementsValue(expenseTrend.EIATrendTopTxnAccount()),
					PropsUtil.getDataPropertyValue("IA_12MonthTimeFilter_ToptxnAccount"),
					"top 5 txn account is not displayed");
			/*
			 * util.assertExpectedActualList(util.getWebElementsValue(expenseTrend.
			 * EIATrendTopTxnCatgeory()),
			 * PropsUtil.getDataPropertyValue("IA_12MonthTimeFilter_ToptxnCategory"),
			 * "top 5 txn catagory is not displayed");
			 */
			Assert.assertTrue(expenseTrend.EIATrendTopTxnCatgeory().size() == Integer
					.parseInt(PropsUtil.getDataPropertyValue("EA_12MonthTimeFilter_ToptxnSize")));

			util.assertExpectedActualAmountList(util.getWebElementsValue(expenseTrend.EIATrendTopTxnAmount()),
					PropsUtil.getDataPropertyValue("IA_12MonthTimeFilter_ToptxnAmount"),
					"top 5 txn amount is not displayed");
		}
	}

	@Test(description = "AT-92125:verify 12 month  time filter", priority = 131, dependsOnMethods = "TimeFilter3month_IA")

	public void TimeFilter12Month_IA() {
		expenseTrend.selectTimeFilter(PropsUtil.getDataPropertyValue("IA_12MonthTimeFilter"));
		SeleniumUtil.waitForPageToLoad(8000);
		Assert.assertTrue(expenseTrend.EIATimeFilterDropDown().isDisplayed(), "time filter is not displayed");
		Assert.assertEquals(expenseTrend.EIATimeFilterDropDownLabel().getText(),
				PropsUtil.getDataPropertyValue("IA_12MonthTimeFilter"), "6 months label is not displayed");
		Assert.assertEquals(expenseTrend.EIATimeFilterDropDownDate().getText().trim(),
				txnAcct.monthStartDate(Integer.parseInt(PropsUtil.getDataPropertyValue("EA_Trend_12TF_EndMonthSize")),
						"MM/dd/yyy")
						+ " " + "-" + " "
						+ add_Manual.targateDate1(
								Integer.parseInt(PropsUtil.getDataPropertyValue("EA_Trend_12TF_StartMonthSize"))),
				"6 th month start date and current date is not displayed");

	}

	@Test(description = "AT-92169:verify 12 month  time filter trend months", priority = 132, dependsOnMethods = "TimeFilter12Month_IA")

	public void TimeFilter12MonthTrend_IA() {
		util.assertExpectedActualList(util.getWebElementsValue(expenseTrend.EIATrendMonthList()),
				expenseTrend.getMonthsValues(
						Integer.parseInt(PropsUtil.getDataPropertyValue("EA_Trend_12TF_getAvgAmount"))),
				"past 6 month name is not displayed in MMMM formate");
	}

	@Test(description = "AT-92169:verify 12 month  time filter  amount", priority = 133, dependsOnMethods = "TimeFilter12Month_IA")

	public void TimeFilter12MonthAmt_IA() {

		util.assertExpectedActualAmountList(util.getWebElementsValue(expenseTrend.EIATrendMonthAmount()),
				PropsUtil.getDataPropertyValue("IA_12MonthTimeFilter_Amount"),
				"all 6 month expense amount is not displayed");

	}

	@Test(description = "AT-92169,AT-92183:verify 12 month  time filter percenatage amount", priority = 134, dependsOnMethods = "TimeFilter12Month_IA")

	public void TimeFilter12MonthPerAmt_IA() {
		List<String> ExpectedDiffPerValue = expenseTrend.totalAmountPerIncome1(
				util.getWebElementsValue(expenseTrend.EIATrendMonthAmount()), expenseTrend.EIATrendAvg().getText(),
				Integer.parseInt(PropsUtil.getDataPropertyValue("EA_Trend_12TF_getAvgAmount")));
		List<String> actualDiffPerValue = util.getWebElementsValue(expenseTrend.EIATrendMonthAmountPer());
		util.assertExpectedActualList(actualDiffPerValue, ExpectedDiffPerValue,
				"Total dif from avg and % values is not displayed");
	}

	@Test(description = "AT-92128:verify 12 month  time filter avg amount message", priority = 135, dependsOnMethods = "TimeFilter12Month_IA")

	public void TimeFilter12MonthAvgamt_IA() {
		String avgMsg = expenseTrend.getAvgAmountMessage(
				Integer.parseInt(PropsUtil.getDataPropertyValue("EA_Trend_DefaultTF_AvgAmountStart")),
				Integer.parseInt(PropsUtil.getDataPropertyValue("EA_Trend_DefaultTF_AvgAmountEnd")),
				PropsUtil.getDataPropertyValue("IA_Default6MonthAvgAmountMessage1"),
				PropsUtil.getDataPropertyValue("IA_Default6MonthAvgAmountMessage2"));
		Assert.assertEquals(expenseTrend.EIATrendAvg().getText(), avgMsg, "lass 3 month avg is not displayed");
	}

	@Test(description = "AT-92167:verify 12 month  time filter chat x axix value", priority = 136, dependsOnMethods = "TimeFilter12Month_IA")
	public void TimeFilter12MonthChartMonth_IA() {
		if (Config.appFlag.equals("web") || Config.appFlag.equals("false")) {
			List<String> actualValue = new ArrayList<String>();
			actualValue = util.getWebElementsValue(expenseTrend.EIATrendChartXaxis());
			List<String> expectedValue = new ArrayList<String>();
			expectedValue = expenseTrend.xAixValueWithoutSpace(
					Integer.parseInt(PropsUtil.getDataPropertyValue("EA_Trend_12TF_getAvgAmount")));
			util.assertExpectedActualList(actualValue, util.revereseList(expectedValue),
					"all 6 month xaxis avalue is not displayed");
		}
	}

	@Test(description = "AT-92136,AT-92137,AT-92180:verify 12 month  time filter top 5 txn", priority = 137, dependsOnMethods = "TimeFilter12Month_IA")

	public void TimeFilter12MonthTrendTxn_IA() {
		if (Config.appFlag.equals("web") || Config.appFlag.equals("false")) {
			Assert.assertTrue(expenseTrend
					.verifytopTxnAmountOrder(util.getWebElementsValue(expenseTrend.EIATrendTopTxnAmount())));
			util.assertActualList(util.getWebElementsValue(expenseTrend.EIATrendTopTxnDate()),
					expenseTrend.getToTxnMonth(
							Integer.parseInt(PropsUtil.getDataPropertyValue("EA_Trend_DefaultTF_Top5Txn")),
							Integer.parseInt(PropsUtil.getDataPropertyValue("EA_Trend_DefaultTF_Top5Txn"))),
					"top 5 txn date header is not displayed");
			// util.assertExpectedActualList(util.getWebElementsValue(expenseTrend.EIATrendTopTxnDescriprion()),
			// PropsUtil.getDataPropertyValue("IA_12MonthTimeFilter_Toptxndesc"), "top 5 txn
			// description is not displayed");
			Assert.assertTrue(expenseTrend.EIATrendTopTxnDescriprion().size() == Integer
					.parseInt(PropsUtil.getDataPropertyValue("EA_12MonthTimeFilter_ToptxnSize")));

			util.assertExpectedActualList(util.getWebElementsValue(expenseTrend.EIATrendTopTxnAccount()),
					PropsUtil.getDataPropertyValue("IA_12MonthTimeFilter_ToptxnAccount"),
					"top 5 txn account is not displayed");
			/*
			 * util.assertExpectedActualList(util.getWebElementsValue(expenseTrend.
			 * EIATrendTopTxnCatgeory()),
			 * PropsUtil.getDataPropertyValue("IA_12MonthTimeFilter_ToptxnCategory"),
			 * "top 5 txn catgeory is not displayed");
			 */
			Assert.assertTrue(expenseTrend.EIATrendTopTxnCatgeory().size() == Integer
					.parseInt(PropsUtil.getDataPropertyValue("EA_12MonthTimeFilter_ToptxnSize")));

			util.assertExpectedActualAmountList(util.getWebElementsValue(expenseTrend.EIATrendTopTxnAmount()),
					PropsUtil.getDataPropertyValue("IA_12MonthTimeFilter_ToptxnAmount"),
					"top 5 txn amount is not displayed");
		}
	}

	// last year
	@Test(description = "AT-92125:verify last year time filter", priority = 138, dependsOnMethods = "TimeFilter3month_IA")

	public void TimeFilterLastYear_IA() {
		expenseTrend.selectTimeFilter(PropsUtil.getDataPropertyValue("IA_LastYearTmeFilter"));
		SeleniumUtil.waitForPageToLoad(8000);
		Assert.assertTrue(expenseTrend.EIATimeFilterDropDown().isDisplayed(), "time filter is not displayed");
		Assert.assertEquals(expenseTrend.EIATimeFilterDropDownLabel().getText(),
				PropsUtil.getDataPropertyValue("IA_LastYearTmeFilter"), "6 months label is not displayed");
		Assert.assertEquals(expenseTrend.EIATimeFilterDropDownDate().getText().trim(),
				txnAcct.yearStartDate(Integer.parseInt(PropsUtil.getDataPropertyValue("EA_Trend_LYTF_EndMonthSize")),
						"MM/dd/yyy")
						+ " " + "-" + " "
						+ txnAcct.yearEndDate(
								Integer.parseInt(PropsUtil.getDataPropertyValue("EA_Trend_LYTF_StartMonthSize")),
								"MM/dd/yyy"),
				"6 th month start date and current date is not displayed");

	}

	@Test(description = "AT-92169:verify last year time filter x axix value message", priority = 139, dependsOnMethods = "TimeFilterLastYear_IA")

	public void TimeFilterLastYearTrend_IA() {
		SeleniumUtil.waitForPageToLoad();
		SeleniumUtil.waitFor(10);
		util.assertExpectedActualList(util.getWebElementsValue(expenseTrend.EIATrendMonthList()),
				expenseTrend.yearMonths(Integer.parseInt(PropsUtil.getDataPropertyValue("EA_Trend_LYTF_getAvgAmount")),
						Integer.parseInt(PropsUtil.getDataPropertyValue("EA_Trend_LYTF_getAvgAmount2"))),
				"past 6 month name is not displayed in MMMM formate");
	}

	@Test(description = "AT-92169:verify last year time filter amount message", priority = 140, dependsOnMethods = "TimeFilterLastYear_IA")

	public void TimeFilterLastYearAmt_IA() {

		util.assertExpectedActualAmountList(util.getWebElementsValue(expenseTrend.EIATrendMonthAmount()),
				PropsUtil.getDataPropertyValue("IA_LastYearTimeFilter_Amount"),
				"all 6 month expense amount is not displayed");

	}

	@Test(description = "AT-92169,AT-92183:verify last year time filter percentage amount message", priority = 141, dependsOnMethods = "TimeFilterLastYear_IA")

	public void TimeFilterLastYearPerAmt_IA() {
		List<String> ExpectedDiffPerValue = expenseTrend.totalAmountPerIncome1(
				util.getWebElementsValue(expenseTrend.EIATrendMonthAmount()), expenseTrend.EIATrendAvg().getText(),
				Integer.parseInt(PropsUtil.getDataPropertyValue("EA_Trend_LYTF_getAvgAmount2")));
		List<String> actualDiffPerValue = util.getWebElementsValue(expenseTrend.EIATrendMonthAmountPer());
		util.assertExpectedActualList(actualDiffPerValue, ExpectedDiffPerValue,
				"Total dif from avg and % values is not displayed");
	}

	@Test(description = "AT-92128:verify last year time filter avg amount message", priority = 142, dependsOnMethods = "TimeFilterLastYear_IA")

	public void TimeFilterLastYearAvgamt_IA() {
		String avgMsg = expenseTrend.getAvgAmountMessage(
				Integer.parseInt(PropsUtil.getDataPropertyValue("EA_Trend_DefaultTF_AvgAmountStart")),
				Integer.parseInt(PropsUtil.getDataPropertyValue("EA_Trend_DefaultTF_AvgAmountEnd")),
				PropsUtil.getDataPropertyValue("IA_Default6MonthAvgAmountMessage1"),
				PropsUtil.getDataPropertyValue("IA_Default6MonthAvgAmountMessage2"));
		Assert.assertEquals(expenseTrend.EIATrendAvg().getText(), avgMsg, "lass 3 month avg is not displayed");
	}

	@Test(description = "AT-92167:verify last year time filter chart x axix value", priority = 143, dependsOnMethods = "TimeFilterLastYear_IA")
	public void TimeFilterLastYearChartMonth_IA() {
		if (Config.appFlag.equals("web") || Config.appFlag.equals("false")) {
			List<String> actualValue = new ArrayList<String>();
			actualValue = util.getWebElementsValue(expenseTrend.EIATrendChartXaxis());
			List<String> expectedValue = new ArrayList<String>();
			expectedValue = expenseTrend.LastYearXaxisMonthChart(
					Integer.parseInt(PropsUtil.getDataPropertyValue("EA_Trend_LYTF_getAvgAmount")),
					Integer.parseInt(PropsUtil.getDataPropertyValue("EA_Trend_LYTF_getAvgAmount2")));
			util.assertExpectedActualList(actualValue, expectedValue, "all 6 month xaxis avalue is not displayed");
		}
	}

	@Test(description = "AT-92125:vreify custom date filter "
			+ "MMM formate", priority = 144, dependsOnMethods = "TimeFilter3month_IA")
	public void TimeFilterCustomDate_IA() {
		expenseTrend.selectTimeFilter(PropsUtil.getDataPropertyValue("IA_CustomDateTmeFilter"));
		SeleniumUtil.waitForPageToLoad();
		filter.selectCustomDate(
				expenseTrend
						.getMonthsMM(Integer.parseInt(PropsUtil.getDataPropertyValue("EA_Trend_CDTF_EndMonthSize"))),
				expenseTrend
						.getMonthsMM(Integer.parseInt(PropsUtil.getDataPropertyValue("EA_Trend_CDTF_StartMonthSize"))));
		SeleniumUtil.waitForPageToLoad();
		Assert.assertTrue(expenseTrend.EIATimeFilterDropDown().isDisplayed(), "time filter is not displayed");
		Assert.assertEquals(expenseTrend.EIATimeFilterDropDownLabel().getText(),
				PropsUtil.getDataPropertyValue("IA_CustomDateTmeFilter"), "6 months label is not displayed");
		Assert.assertEquals(expenseTrend.EIATimeFilterDropDownDate().getText().trim(),
				expenseTrend.getMonthsMM(Integer.parseInt(PropsUtil.getDataPropertyValue("EA_Trend_CDTF_EndMonthSize")))
						+ " " + "-" + " "
						+ expenseTrend.getMonthsMM(
								Integer.parseInt(PropsUtil.getDataPropertyValue("EA_Trend_CDTF_StartMonthSize"))),
				"6 th month start date and current date should be displayed");

	}

	@Test(description = "AT-92169:verify custom date filter trend month", priority = 145, dependsOnMethods = "TimeFilterCustomDate_IA")

	public void TimeFilterCustomDateTrend_IA() {
		util.assertExpectedActualList(util.getWebElementsValue(expenseTrend.EIATrendMonthList()),
				expenseTrend.getMonthsValues(
						Integer.parseInt(PropsUtil.getDataPropertyValue("EA_Trend_CDTF_getAvgAmount"))),
				"past 6 month name is not displayed in MMMM formate");
	}

	@Test(description = "AT-92169: verify custom date filter custom date amount", priority = 146, dependsOnMethods = "TimeFilterCustomDate_IA")

	public void TimeFilterCustomDateAmt_IA() {

		util.assertExpectedActualAmountList(util.getWebElementsValue(expenseTrend.EIATrendMonthAmount()),
				PropsUtil.getDataPropertyValue("IA_CustomDateTmeFilterAmount"),
				"all 6 month expense amount is not displayed");

	}

	@Test(description = "AT-92169,AT-92183:verify custom date filter percentage amount", priority = 147, dependsOnMethods = "TimeFilterCustomDate_IA")

	public void TimeFilterCustomDatePerAmt_IA() {
		List<String> ExpectedDiffPerValue = expenseTrend.totalAmountPerIncome1(
				util.getWebElementsValue(expenseTrend.EIATrendMonthAmount()), expenseTrend.EIATrendAvg().getText(),
				Integer.parseInt(PropsUtil.getDataPropertyValue("EA_Trend_CDTF_getAvgAmount")));
		List<String> actualDiffPerValue = util.getWebElementsValue(expenseTrend.EIATrendMonthAmountPer());
		util.assertExpectedActualList(actualDiffPerValue, ExpectedDiffPerValue,
				"Total dif from avg and % values is not displayed");
	}

	@Test(description = "AT-92128:verify custome date filter avg amount message", priority = 148, dependsOnMethods = "TimeFilterCustomDate_IA")

	public void TimeFilterCustomDateAvgamt_IA() {
		String avgMsg = expenseTrend.getAvgAmountMessage(
				Integer.parseInt(PropsUtil.getDataPropertyValue("EA_Trend_DefaultTF_AvgAmountStart")),
				Integer.parseInt(PropsUtil.getDataPropertyValue("EA_Trend_DefaultTF_AvgAmountEnd")),
				PropsUtil.getDataPropertyValue("IA_Default6MonthAvgAmountMessage1"),
				PropsUtil.getDataPropertyValue("IA_Default6MonthAvgAmountMessage2"));
		Assert.assertEquals(expenseTrend.EIATrendAvg().getText(), avgMsg, "lass 3 month avg is not displayed");
	}

	@Test(description = "AT-92167:verifycustom date chart x axix value", priority = 149, dependsOnMethods = "TimeFilterCustomDate_IA")
	public void TimeFilterCustomDateChartMonth_IA() {
		if (Config.appFlag.equals("web") || Config.appFlag.equals("false")) {
			List<String> actualValue = new ArrayList<String>();
			actualValue = util.getWebElementsValue(expenseTrend.EIATrendChartXaxis());
			List<String> expectedValue = new ArrayList<String>();
			expectedValue = expenseTrend
					.xAixValue(Integer.parseInt(PropsUtil.getDataPropertyValue("EA_Trend_CDTF_getAvgAmount")));
			util.assertExpectedActualList(actualValue, util.revereseList(expectedValue),
					"verify all 6 month xaxis avalue ");
		}
	}

	@Test(description = "AT-92136,AT-92137,AT-92180:verify custome date top 5 txn", priority = 150, dependsOnMethods = "TimeFilterCustomDate_IA")

	public void TimeFilterCustomDateTrendTxn_IA() {
		if (Config.appFlag.equals("web") || Config.appFlag.equals("false")) {
			Assert.assertTrue(expenseTrend
					.verifytopTxnAmountOrder(util.getWebElementsValue(expenseTrend.EIATrendTopTxnAmount())));
			util.assertActualList(util.getWebElementsValue(expenseTrend.EIATrendTopTxnDate()),
					expenseTrend.getToTxnMonth(
							Integer.parseInt(PropsUtil.getDataPropertyValue("EA_Trend_DefaultTF_Top5Txn")),
							Integer.parseInt(PropsUtil.getDataPropertyValue("EA_Trend_DefaultTF_Top5Txn"))),
					"top 5 txn date header is not displayed");
			/*
			 * util.assertExpectedActualList(util.getWebElementsValue(expenseTrend.
			 * EIATrendTopTxnDescriprion()),
			 * PropsUtil.getDataPropertyValue("IA_12MonthTimeFilter_Toptxndesc"),
			 * "top 5 txn description is not displayed");
			 */
			Assert.assertTrue(expenseTrend.EIATrendTopTxnCatgeory().size() == Integer
					.parseInt(PropsUtil.getDataPropertyValue("EA_12MonthTimeFilter_ToptxnSize")));

			util.assertExpectedActualList(util.getWebElementsValue(expenseTrend.EIATrendTopTxnAccount()),
					PropsUtil.getDataPropertyValue("IA_12MonthTimeFilter_ToptxnAccount"),
					"top 5 txn account is not displayed");
			/*
			 * util.assertExpectedActualList(util.getWebElementsValue(expenseTrend.
			 * EIATrendTopTxnCatgeory()),
			 * PropsUtil.getDataPropertyValue("IA_12MonthTimeFilter_ToptxnCategory"),
			 * "top 5 txn category is not displayed");
			 */
			Assert.assertTrue(expenseTrend.EIATrendTopTxnCatgeory().size() == Integer
					.parseInt(PropsUtil.getDataPropertyValue("EA_12MonthTimeFilter_ToptxnSize")));

			util.assertExpectedActualAmountList(util.getWebElementsValue(expenseTrend.EIATrendTopTxnAmount()),
					PropsUtil.getDataPropertyValue("IA_12MonthTimeFilter_ToptxnAmount"),
					"top 5 txn amount is not displayed");
		}

	}

	@Test(description = "AT-92132:verify custom date time filter header", priority = 151, dependsOnMethods = "TimeFilterCustomDateTrendTxn_IA")
	public void VerifyCustomeDatePopupHeader_IA() throws Exception {
		expenseTrend.selectTimeFilter(PropsUtil.getDataPropertyValue("IA_CustomDateTmeFilter"));

		SeleniumUtil.waitForPageToLoad();
		if (Config.appFlag.equals("web") || Config.appFlag.equals("false")) {
			Assert.assertEquals(
					filter.addManualHeader_AMT()
							.get(Integer.parseInt(PropsUtil.getDataPropertyValue("EA_Trend_CDTF_PopUpHeaderLength")))
							.getText().trim(),
					PropsUtil.getDataPropertyValue("TransactionCustomDateFilterHeader"),
					"custom date popup is not open when you click on custome date filter");
			Assert.assertEquals(filter.transactionCustomDateFilterUpdate().getText().trim(),
					PropsUtil.getDataPropertyValue("TransactionCustomDateFilterUpdateText"),
					"custom date update button is not displayed");

		} else {
			Assert.assertEquals(
					filter.addManualHeader_AMT_mob()
							.get(Integer.parseInt(PropsUtil.getDataPropertyValue("EA_Trend_CDTF_PopUpHeaderLength")))
							.getText().trim(),
					PropsUtil.getDataPropertyValue("TransactionCustomDateFilterHeader_mob"),
					"custom date popup is not open when you click on custome date filter");
			Assert.assertEquals(filter.TimeFilterCustomDonebtn().getText().trim(),
					PropsUtil.getDataPropertyValue("TransactionCustomDateFilterUpdateText_mob").trim(),
					"custom date update button is not displayed");

		}
		Assert.assertEquals(filter.transactionCustomDateFilterStartDateLabel().getText().trim(),
				PropsUtil.getDataPropertyValue("TransactionCustomDateFilterStartDatelabel"),
				"custom date start date label is not displayed");

		Assert.assertEquals(filter.transactionCustomDateFilterEndDateLabel().getText().trim(),
				PropsUtil.getDataPropertyValue("TransactionCustomDateFilterEnddateLabel"),
				"custom date end date label is not displayed");
		Assert.assertTrue(filter.transactionCustomDateFilterCalanderIcon()
				.get(Integer.parseInt(PropsUtil.getDataPropertyValue("EA_Trend_CDTF_PopUpHeaderLength"))).isDisplayed(),
				"from date calander icon is not displayed");
		Assert.assertTrue(filter.transactionCustomDateFilterCalanderIcon()
				.get(Integer.parseInt(PropsUtil.getDataPropertyValue("EA_Trend_CDTF_PopUpEndDateCalIcon")))
				.isDisplayed(), "To date calander icon is not displayed");

	}

	@Test(description = "AT-92132:verify custom date format", priority = 152, dependsOnMethods = "VerifyCustomeDatePopupHeader_IA")
	public void VerifyCustomeDateFormate_IA() throws Exception {
		Assert.assertEquals(filter.transactionCustomDateFilterStartDate().getAttribute("placeholder").trim(),
				PropsUtil.getDataPropertyValue("TransactionCustomDateFilterDateFormate"),
				"custome date daet formate is not displayed in MM/DD/YYYY");
		Assert.assertEquals(filter.transactionCustomDateFilterEndDate().getAttribute("placeholder").trim(),
				PropsUtil.getDataPropertyValue("TransactionCustomDateFilterDateFormate"),
				"custome date daet formate is not displayed in MM/DD/YYYY");

	}

	@Test(description = "AT-92132:verify custom date less than error message", priority = 153, dependsOnMethods = "VerifyCustomeDateFormate_IA")
	public void VerifyToDateGreaterThanFormDateErrorMessage_IA() throws Exception {

		filter.selectCustomDate(
				add_Manual.targateDate1(
						Integer.parseInt(PropsUtil.getDataPropertyValue("EA_Trend_CDTF_PopUpStartDate1"))),
				add_Manual
						.targateDate1(Integer.parseInt(PropsUtil.getDataPropertyValue("EA_Trend_CDTF_PopUpEndDate1"))));
		Assert.assertTrue(filter.transactionCustomDateFilterErrorIcon().isDisplayed(),
				"error message icon is not displayed");
		Assert.assertEquals(filter.transactionCustomDateFilterErrorMsg1().getText().trim(),
				PropsUtil.getDataPropertyValue("TransactionCustomDateFilterInvaildMsg"),
				"invalid date message is not displayed");
		Assert.assertEquals(filter.transactionCustomDateFilterErrorMsg2().getText().trim(),
				PropsUtil.getDataPropertyValue("TransactionCustomDateFilterErrorMessage1"),
				"End date must be greater than start date message is not displayed");
	}

	@Test(description = "AT-92132:verify Custom dtae 12 month error message ", priority = 154, dependsOnMethods = "VerifyToDateGreaterThanFormDateErrorMessage_IA")
	public void VerifyDateRange12monthErrorMessage_IA() throws Exception {
		filter.selectCustomDate(
				add_Manual.targateDate1(
						Integer.parseInt(PropsUtil.getDataPropertyValue("EA_Trend_CDTF_PopUpStartDate2"))),
				add_Manual
						.targateDate1(Integer.parseInt(PropsUtil.getDataPropertyValue("EA_Trend_CDTF_PopUpEndDate2"))));

		Assert.assertTrue(filter.transactionCustomDateFilterErrorIcon().isDisplayed(),
				"error message icon is not displayed");
		Assert.assertEquals(filter.transactionCustomDateFilterErrorMsg1().getText().trim(),
				PropsUtil.getDataPropertyValue("TransactionCustomDateFilterInvaildMsg"),
				"invalid date message is not displayed");
		Assert.assertEquals(filter.transactionCustomDateFilterErrorMsg2().getText().trim(),
				PropsUtil.getDataPropertyValue("TransactionCustomDateFilterErrorMessag2"),
				"Selected time period can't be more than 12 months message is not displayed");
	}

	@Test(description = "AT-92132:verify custom dtae popup close", priority = 155, dependsOnMethods = "VerifyDateRange12monthErrorMessage_IA")
	public void VerifyCustomeDatePopUpClose_IA() throws Exception {
		SeleniumUtil.click(filter.transactionCustomDateFilterClose());
		boolean customeDateClose = false;
		if (Config.appFlag.equals("web") || Config.appFlag.equals("false")) {
			if (filter.addManualHeader_AMT().size() == Integer
					.parseInt(PropsUtil.getDataPropertyValue("EA_Trend_CDTF_PopUpHeaderLength"))) {
				customeDateClose = true;
			}
		} else {
			if (filter.addManualHeader_AMT_mob().size() == Integer
					.parseInt(PropsUtil.getDataPropertyValue("EA_Trend_CDTF_PopUpHeaderLength"))) {
				customeDateClose = true;
			}
		}
		Assert.assertTrue(customeDateClose);
	}

	@Test(description = "AT-92186:verify selected group in account drop down", priority = 158, dependsOnMethods = "verifyIncomeHeader_IA")
	public void groupFilter_IA() throws Exception {
		SeleniumUtil.refresh(d);
		SeleniumUtil.waitForPageToLoad();
		/*
		 * PageParser.forceNavigate("AccountsPage", d);
		 * SeleniumUtil.waitForPageToLoad(10000);
		 * txnAcct.createExpenseGroup(PropsUtil.getDataPropertyValue("IA_GroupName"));
		 */
		expenseTrend.navigateToEA();
		SeleniumUtil.waitForPageToLoad();
		expenseTrend.selectIncome();
		SeleniumUtil.waitForPageToLoad();
		expenseTrend.clickGroup(PropsUtil.getDataPropertyValue("EA_GroupName"));
		SeleniumUtil.waitForPageToLoad();
		if (expenseTrend.isSelectAcc_Donebtn()) {
			SeleniumUtil.click(expenseTrend.SelectAcc_Donebtn());
		}
		SeleniumUtil.waitForPageToLoad();
		Assert.assertEquals(expenseTrend.EIAccountDropDown().getText().trim(),
				PropsUtil.getDataPropertyValue("IA_GroupName"),
				"selected account is not displayed in account drop down");

	}

	@Test(description = "AT-92186:verify time filter when applied group filter", priority = 159, dependsOnMethods = "groupFilter_IA")
	public void timeFIlter_IAgroupFilter() throws Exception {
		Assert.assertTrue(expenseTrend.EIATimeFilterDropDown().isDisplayed(), "time filter is not displayed");
		Assert.assertEquals(expenseTrend.EIATimeFilterDropDownLabel().getText(),
				PropsUtil.getDataPropertyValue("IA_DefaultTimeFilter"), "6 months label is not displayed");
		Assert.assertEquals(expenseTrend.EIATimeFilterDropDownDate().getText().trim(),
				txnAcct.monthStartDate(
						Integer.parseInt(PropsUtil.getDataPropertyValue("EA_Trend_DefaultTF_EndMonthSize")),
						"MM/dd/yyy")
						+ " " + "-" + " "
						+ add_Manual.targateDate1(
								Integer.parseInt(PropsUtil.getDataPropertyValue("EA_Trend_DefaultTF_StartMonthSize"))),
				"6 th month start date and current date is not displayed");

	}

	@Test(description = "AT-92186:verify group filter months", priority = 160, dependsOnMethods = "groupFilter_IA")
	public void legendMonths_IAgroupFilter() throws Exception {
		util.assertExpectedActualList(util.getWebElementsValue(expenseTrend.EIATrendMonthList()),
				expenseTrend.getMonthsValues(
						Integer.parseInt(PropsUtil.getDataPropertyValue("EA_Trend_DefaultTF_getAvgAmount"))),
				"past 6 month name is not displayed in MMMM formate");
	}

	@Test(description = "AT-92186:verify group filter amounts", priority = 161, dependsOnMethods = "groupFilter_IA")
	public void legendAmounts_IAgroupFilter() throws Exception {
		util.assertExpectedActualAmountList(util.getWebElementsValue(expenseTrend.EIATrendMonthAmount()),
				PropsUtil.getDataPropertyValue("IA_SelectCashAccountAmount"),
				"all 6 month expense amount is not displayed");
	}

	@Test(description = "AT-92169,AT-92186:verify group filter percentage amount", priority = 162, dependsOnMethods = "groupFilter_IA")
	public void legendPer_IAgroupFilter() throws Exception {
		SeleniumUtil.waitForPageToLoad();
		SeleniumUtil.waitFor(5);
		List<String> ExpectedDiffPerValue = expenseTrend.totalAmountPerIncome1(
				util.getWebElementsValue(expenseTrend.EIATrendMonthAmount()), expenseTrend.EIATrendAvg().getText(),
				Integer.parseInt(PropsUtil.getDataPropertyValue("EA_Trend_DefaultTF_getAvgAmount")));
		List<String> actualDiffPerValue = util.getWebElementsValue(expenseTrend.EIATrendMonthAmountPer());
		util.assertExpectedActualList(actualDiffPerValue, ExpectedDiffPerValue,
				"Total dif from avg and % values is not displayed");
	}

	@Test(description = "AT-92128,AT-92186,AT-92187,AT-92188:verify group filter avg amount", priority = 163, dependsOnMethods = "groupFilter_IA")
	public void legendAvg_IAgroupFilter() throws Exception {

		String avgMsg = expenseTrend.getAvgAmountMessage(
				Integer.parseInt(PropsUtil.getDataPropertyValue("EA_Trend_DefaultTF_AvgAmountStart")),
				Integer.parseInt(PropsUtil.getDataPropertyValue("EA_Trend_DefaultTF_AvgAmountEnd")),
				PropsUtil.getDataPropertyValue("IA_Default6MonthAvgAmountMessage1"),
				PropsUtil.getDataPropertyValue("IA_SelectCashAccountAvgAmount"));
		Assert.assertEquals(expenseTrend.EIATrendAvg().getText(), avgMsg, "lass 3 month avg is not displayed");
	}

	@Test(description = "AT-92167,AT-92186:verify group filter trend amount", priority = 164, dependsOnMethods = "groupFilter_IA")
	public void verifyTrendChartMonth_IAgroupFilter() {
		if (Config.appFlag.equals("web") || Config.appFlag.equals("false")) {
			List<String> actualValue = new ArrayList<String>();
			actualValue = util.getWebElementsValue(expenseTrend.EIATrendChartXaxis());
			List<String> expectedValue = new ArrayList<String>();
			expectedValue = expenseTrend
					.xAixValue(Integer.parseInt(PropsUtil.getDataPropertyValue("EA_Trend_DefaultTF_getAvgAmount")));
			util.assertExpectedActualList(actualValue, util.revereseList(expectedValue),
					"all 6 month xaxis avalue is not displayed");
		}
	}

	@Test(description = "AT-92136,AT-92137,AT-92180:verify group filter top 5 txn", priority = 165, dependsOnMethods = "groupFilter_IA")

	public void verifyTopTransaction_IAgroupFilter() {
		if (Config.appFlag.equals("web") || Config.appFlag.equals("false")) {
			Assert.assertTrue(expenseTrend
					.verifytopTxnAmountOrder(util.getWebElementsValue(expenseTrend.EIATrendTopTxnAmount())));
			SeleniumUtil.waitForPageToLoad();
			SeleniumUtil.waitFor(5);
			util.assertActualList(util.getWebElementsValue(expenseTrend.EIATrendTopTxnDate()),
					expenseTrend.getToTxnMonth(
							Integer.parseInt(PropsUtil.getDataPropertyValue("EA_Trend_DefaultTF_Top5Txn")),
							Integer.parseInt(PropsUtil.getDataPropertyValue("EA_Trend_DefaultTF_Top5Txn"))),
					"top 5 txn date header is not displayed");
			// util.assertExpectedActualList(util.getWebElementsValue(expenseTrend.EIATrendTopTxnDescriprion()),
			// PropsUtil.getDataPropertyValue("IA_SelectCashAccountTxnDescription"), "top 5
			// txn description is not displayed");
			Assert.assertTrue(expenseTrend.EIATrendTopTxnCatgeory().size() == Integer
					.parseInt(PropsUtil.getDataPropertyValue("EA_12MonthTimeFilter_ToptxnSize")));

			util.assertExpectedActualList(util.getWebElementsValue(expenseTrend.EIATrendTopTxnAccount()),
					PropsUtil.getDataPropertyValue("IA_SelectCashAccountTxnAcct"),
					"top 5 txn account is not displayed");
			/*
			 * util.assertExpectedActualList(util.getWebElementsValue(expenseTrend.
			 * EIATrendTopTxnCatgeory()),
			 * PropsUtil.getDataPropertyValue("IA_SelectCashAccountTxnCategory"),
			 * "top 5 txn catgeory is not displayed");
			 */
			Assert.assertTrue(expenseTrend.EIATrendTopTxnCatgeory().size() == Integer
					.parseInt(PropsUtil.getDataPropertyValue("EA_12MonthTimeFilter_ToptxnSize")));

			util.assertExpectedActualAmountList(util.getWebElementsValue(expenseTrend.EIATrendTopTxnAmount()),
					PropsUtil.getDataPropertyValue("IA_SelectCashAccountTxnAmount"),
					"top 5 txn amount is not displayed");
		}
	}

	@Test(description = "AT-92173,AT-92174: verify trend amount is updated when add a txn", priority = 166)
	public void addMTXn_IA() throws Exception {
		expenseTrend.selectIncome();
		SeleniumUtil.waitForPageToLoad();
		expenseTrend.clickEIAMTLink();
		add_Manual.createOneTimeTransaction(PropsUtil.getDataPropertyValue("IA_MTAmount3"),
				PropsUtil.getDataPropertyValue("IA_MTDescription3"), PropsUtil.getDataPropertyValue("IA_MTTxnType3"),
				PropsUtil.getDataPropertyValue("IA_MTAccounts3"), PropsUtil.getDataPropertyValue("IA_MTFrequency3"),
				Integer.parseInt(PropsUtil.getDataPropertyValue("IA_MTDate3")),
				PropsUtil.getDataPropertyValue("IA_MTCategory3"), PropsUtil.getDataPropertyValue("IA_MTNote3"),
				PropsUtil.getDataPropertyValue("IA_MTCheck3"));
		SeleniumUtil.waitUntilToastMessageIsDisappeared();
		SeleniumUtil.waitForPageToLoad();
		SeleniumUtil.waitFor(5);
		Assert.assertEquals(expenseTrend.EIATrendMonthAmount().get(0).getText().trim(),
				PropsUtil.getDataPropertyValue("IA_UpDatedTrendAmount"),
				"updated data is not populate in month amount");

	}

	@Test(description = "AT-92136,AT-92137,AT-92175,AT-92180:verify top 5 txn updated when add the new transaction", priority = 167)

	public void top5txnUpdate_IA() {
		if (Config.appFlag.equals("web") || Config.appFlag.equals("false")) {
			Assert.assertTrue(expenseTrend
					.verifytopTxnAmountOrder(util.getWebElementsValue(expenseTrend.EIATrendTopTxnAmount())));
			util.assertActualList(util.getWebElementsValue(expenseTrend.EIATrendTopTxnDate()),
					expenseTrend.getToTxnMonth(
							Integer.parseInt(PropsUtil.getDataPropertyValue("EA_Trend_DefaultTF_Top5Txn")),
							Integer.parseInt(PropsUtil.getDataPropertyValue("EA_Trend_DefaultTF_Top5Txn"))),
					"top 5 txn date header is not displayed");
			// util.assertExpectedActualList(util.getWebElementsValue(expenseTrend.EIATrendTopTxnDescriprion()),
			// PropsUtil.getDataPropertyValue("IA_UpDatedTrendAmount_Toptxndesc"), "top 5
			// txn description is not displayed");
			Assert.assertTrue(expenseTrend.EIATrendTopTxnCatgeory().size() == Integer
					.parseInt(PropsUtil.getDataPropertyValue("EA_12MonthTimeFilter_ToptxnSize")));

			util.assertExpectedActualList(util.getWebElementsValue(expenseTrend.EIATrendTopTxnAccount()),
					PropsUtil.getDataPropertyValue("IA_UpDatedTrendAmount_ToptxnAccount"),
					"top 5 txn account is not displayed");
			/*
			 * util.assertExpectedActualList(util.getWebElementsValue(expenseTrend.
			 * EIATrendTopTxnCatgeory()),
			 * PropsUtil.getDataPropertyValue("IA_UpDatedTrendAmount_ToptxnCategory"),
			 * "top 5 txn category is not displayed");
			 */
			Assert.assertTrue(expenseTrend.EIATrendTopTxnCatgeory().size() == Integer
					.parseInt(PropsUtil.getDataPropertyValue("EA_12MonthTimeFilter_ToptxnSize")));

			util.assertExpectedActualAmountList(util.getWebElementsValue(expenseTrend.EIATrendTopTxnAmount()),
					PropsUtil.getDataPropertyValue("IA_UpDatedTrendAmount_ToptxnAmount"),
					"top 5 txn amount is not displayed");
		}
	}

	@Test(description = "AT-92136,AT-92137,AT-92175,AT-92180,AT-92133:verify Avg amount is updated", priority = 168, dependsOnMethods = "groupFilter_IA")
	public void avgAmountUpdate_IA() throws Exception {
		expenseTrend.clickEIAMTLink();
		add_Manual.createOneTimeTransaction(PropsUtil.getDataPropertyValue("IA_MTAmount4"),
				PropsUtil.getDataPropertyValue("IA_MTDescription4"), PropsUtil.getDataPropertyValue("IA_MTTxnType4"),
				PropsUtil.getDataPropertyValue("IA_MTAccounts4"), PropsUtil.getDataPropertyValue("IA_MTFrequency4"),
				Integer.parseInt(PropsUtil.getDataPropertyValue("IA_MTDate4")),
				PropsUtil.getDataPropertyValue("IA_MTCategory4"), PropsUtil.getDataPropertyValue("IA_MTNote4"),
				PropsUtil.getDataPropertyValue("IA_MTCheck4"));
		SeleniumUtil.waitForPageToLoad();

		Assert.assertEquals(expenseTrend.EIATrendMonthAmount().get(1).getText(),
				PropsUtil.getDataPropertyValue("IA_UpDatedTrendAmount2"),
				"updated data is not populate in month amount");
		String avgMsg = expenseTrend.getAvgAmountMessage(
				Integer.parseInt(PropsUtil.getDataPropertyValue("EA_Trend_DefaultTF_AvgAmountStart")),
				Integer.parseInt(PropsUtil.getDataPropertyValue("EA_Trend_DefaultTF_AvgAmountEnd")),
				PropsUtil.getDataPropertyValue("IA_Default6MonthAvgAmountMessage1"),
				PropsUtil.getDataPropertyValue("IA_UpDatedTrendAmountAvg"));
		Assert.assertEquals(expenseTrend.EIATrendAvg().getText(), avgMsg, "lass 3 month avg should is not displayed");
	}

	@Test(description = "AT-92136,AT-92137,AT-92175,AT-92180:verify updated txn data is updated in percentage amount", priority = 169, dependsOnMethods = "avgAmountUpdate_IA")

	public void perAmtUpdated_IA() {
		List<String> ExpectedDiffPerValue = expenseTrend.totalAmountPerIncome(
				util.getWebElementsValue(expenseTrend.EIATrendMonthAmount()), expenseTrend.EIATrendAvg().getText(),
				Integer.parseInt(PropsUtil.getDataPropertyValue("EA_Trend_DefaultTF_getAvgAmount")));
		List<String> actualDiffPerValue = util.getWebElementsValue(expenseTrend.EIATrendMonthAmountPer());
		util.assertExpectedActualList(actualDiffPerValue, ExpectedDiffPerValue,
				"Total dif from avg and % values is not displayed");
	}

	@Test(description = "AT-92172:verify legend message should be displayed", priority = 172, dependsOnMethods = "avgAmountUpdate_IA")
	public void legendMsg_IA() throws Exception {
		Assert.assertEquals(expenseTrend.EIATrendDeclimMsg().getText(),
				PropsUtil.getDataPropertyValue("IA_LegendsMessage"), " legend message is not displayed");
	}

	@Test(description = "AT-92130,AT-92131:avg message should not displayed", priority = 173, dependsOnMethods = "avgAmountUpdate_IA")
	public void noAvgMessage_IA() throws Exception {
		expenseTrend.selectTimeFilter(PropsUtil.getDataPropertyValue("EA_ThisMonthTmeFilter"));
		Assert.assertEquals(expenseTrend.EIATrendAvgList().size(),
				Integer.parseInt(PropsUtil.getDataPropertyValue("EA_Trend_AgvMsgSize")), "avg message is displyed");
		Assert.assertEquals(expenseTrend.EIATrendMonthAmountPer().size(),
				Integer.parseInt(PropsUtil.getDataPropertyValue("EA_Trend_AgvMsgSize")), "avg percentage is displyed");
	}

	@Test(description = "AT-93504:verify no Avg FT for weekly dataT", priority = 174, dependsOnMethods = "noAvgMessage_IA")
	public void noAvgMsgFT_IA() throws Exception {
		expenseTrend.clickMore();
		expenseTrend.clikcFT();
		Assert.assertTrue(
				expenseTrend.EIAHLCFTCloseButton(PropsUtil.getDataPropertyValue("EA_HLC_FTUEList1")).isDisplayed(),
				"close button is not displayed");
		Assert.assertEquals(expenseTrend.EIAHLCFTHeader(PropsUtil.getDataPropertyValue("EA_HLC_FTUEList1")).getText(),
				PropsUtil.getDataPropertyValue("EA_HLC_FT_UnCatHeader"), "uncategorzed txn FT header is not displayed");
		Assert.assertEquals(
				expenseTrend.EIAHLCFTMesg1(PropsUtil.getDataPropertyValue("EA_HLC_FTUEList1")).getText()
						.replaceAll("\\n", ""),
				PropsUtil.getDataPropertyValue("EA_HLC_FT_UnCatMessage1"),
				"uncategorzed txn FT message1 is not displayed");

		Assert.assertEquals(expenseTrend.EIAHLCFTButton1(PropsUtil.getDataPropertyValue("EA_HLC_FTUEList1")).getText(),
				PropsUtil.getDataPropertyValue("EA_HLC_FT_UnCatGotIt"),
				"uncategorzed txn FT got it button is not displayed");

	}

	@Test(description = "AT-92130,AT-92131:avg message should not displayed", priority = 175)
	public void noAvgMessage() throws Exception {
		expenseTrend.navigateToEA();
		SeleniumUtil.waitForPageToLoad();
		expenseTrend.selectTimeFilter(PropsUtil.getDataPropertyValue("EA_ThisMonthTmeFilter"));
		SeleniumUtil.waitForPageToLoad();
		Assert.assertEquals(expenseTrend.EIATrendAvgList().size(),
				Integer.parseInt(PropsUtil.getDataPropertyValue("EA_Trend_AgvMsgSize")), "avg message is displyed");
		Assert.assertEquals(expenseTrend.EIATrendMonthAmountPer().size(),
				Integer.parseInt(PropsUtil.getDataPropertyValue("EA_Trend_AgvMsgSize")), "avg percentage is displyed");
	}

	@Test(description = "AT-93504:verify no Avg FT for weekly data", priority = 176, dependsOnMethods = "noAvgMessage")
	public void noAvgMsgFT() throws Exception {
		expenseTrend.clickMore();
		expenseTrend.clikcFT();
		Assert.assertTrue(
				expenseTrend.EIAHLCFTCloseButton(PropsUtil.getDataPropertyValue("EA_HLC_FTUEList1")).isDisplayed(),
				"close button is not displayed");
		Assert.assertEquals(expenseTrend.EIAHLCFTHeader(PropsUtil.getDataPropertyValue("EA_HLC_FTUEList1")).getText(),
				PropsUtil.getDataPropertyValue("EA_HLC_FT_UnCatHeader"), "uncategorzed txn FT header is not displayed");
		Assert.assertEquals(
				expenseTrend.EIAHLCFTMesg1(PropsUtil.getDataPropertyValue("EA_HLC_FTUEList1")).getText()
						.replaceAll("\\n", ""),
				PropsUtil.getDataPropertyValue("EA_HLC_FT_UnCatMessage1"),
				"uncategorzed txn FT message1 is not displayed");

		Assert.assertEquals(expenseTrend.EIAHLCFTButton1(PropsUtil.getDataPropertyValue("EA_HLC_FTUEList1")).getText(),
				PropsUtil.getDataPropertyValue("EA_HLC_FT_UnCatGotIt"),
				"uncategorzed txn FT got it button is not displayed");

	}

	public void addManualAdjTxn() {
		expenseTrend.clickEIAMTLink();
		add_Manual.createOneTimeTransaction(PropsUtil.getDataPropertyValue("IA_MTAmount"),
				PropsUtil.getDataPropertyValue("IA_MTDescription"), PropsUtil.getDataPropertyValue("IA_MTTxnType"),
				PropsUtil.getDataPropertyValue("IA_MTAccounts"), PropsUtil.getDataPropertyValue("IA_MTFrequency"),
				Integer.parseInt(PropsUtil.getDataPropertyValue("IA_MTDate")),
				PropsUtil.getDataPropertyValue("IA_MTCategory"), PropsUtil.getDataPropertyValue("IA_MTNote"),
				PropsUtil.getDataPropertyValue("IA_MTCheck"));
		SeleniumUtil.waitForPageToLoad(15000);

		expenseTrend.clickEIAMTLink();
		add_Manual.createOneTimeTransaction(PropsUtil.getDataPropertyValue("IA_MTAmount1"),
				PropsUtil.getDataPropertyValue("IA_MTDescription1"), PropsUtil.getDataPropertyValue("IA_MTTxnType1"),
				PropsUtil.getDataPropertyValue("IA_MTAccounts1"), PropsUtil.getDataPropertyValue("IA_MTFrequency1"),
				Integer.parseInt(PropsUtil.getDataPropertyValue("IA_MTDate")),
				PropsUtil.getDataPropertyValue("IA_MTCategory1"), PropsUtil.getDataPropertyValue("IA_MTNote1"),
				PropsUtil.getDataPropertyValue("IA_MTCheck1"));
		SeleniumUtil.waitForPageToLoad(15000);

		expenseTrend.clickEIAMTLink();
		add_Manual.createOneTimeTransaction(PropsUtil.getDataPropertyValue("IA_MTAmount2"),
				PropsUtil.getDataPropertyValue("IA_MTDescription2"), PropsUtil.getDataPropertyValue("IA_MTTxnType2"),
				PropsUtil.getDataPropertyValue("IA_MTAccounts2"), PropsUtil.getDataPropertyValue("IA_MTFrequency2"),
				Integer.parseInt(PropsUtil.getDataPropertyValue("IA_MTDate")),
				PropsUtil.getDataPropertyValue("IA_MTCategory2"), PropsUtil.getDataPropertyValue("IA_MTNote2"),
				PropsUtil.getDataPropertyValue("IA_MTCheck2"));
		SeleniumUtil.waitForPageToLoad(15000);

	}

	public void addManualReundTxn() {
		expenseTrend.clickEIAMTLink();

		String IA_MTCategory5 = null;
		String IA_MTCategory6 = null;
		String IA_MTCategory7 = null;
		String IA_MTCategory8 = null;
		if (PropsUtil.getEnvPropertyValue("CategoryList43").equalsIgnoreCase("yes")) {
			IA_MTCategory5 = PropsUtil.getDataPropertyValue("EA_MTCategory5_43");
			IA_MTCategory6 = PropsUtil.getDataPropertyValue("EA_MTCategory6_43");
			IA_MTCategory7 = PropsUtil.getDataPropertyValue("EA_MTCategory8_43");
			IA_MTCategory8 = PropsUtil.getDataPropertyValue("EA_MTCategory9_43");

		} else {
			IA_MTCategory5 = PropsUtil.getDataPropertyValue("EA_MTCategory6");
			IA_MTCategory6 = PropsUtil.getDataPropertyValue("EA_MTCategory7");
			IA_MTCategory7 = PropsUtil.getDataPropertyValue("EA_MTCategory8");
			IA_MTCategory8 = PropsUtil.getDataPropertyValue("EA_MTCategory10");

		}
		SeleniumUtil.waitForPageToLoad(10000);
		add_Manual.createOneTimeTransaction(PropsUtil.getDataPropertyValue("EA_MTAmount6"),
				PropsUtil.getDataPropertyValue("EA_MTDescription6"), PropsUtil.getDataPropertyValue("EA_MTTxnType6"),
				PropsUtil.getDataPropertyValue("EA_MTAccounts6"), PropsUtil.getDataPropertyValue("EA_MTFrequency6"), 0,
				IA_MTCategory5, PropsUtil.getDataPropertyValue("EA_MTNote6"),
				PropsUtil.getDataPropertyValue("EA_MTCheck5"));
		SeleniumUtil.waitForPageToLoad(15000);

		expenseTrend.clickEIAMTLink();
		SeleniumUtil.waitForPageToLoad(10000);
		add_Manual.createOneTimeTransaction(PropsUtil.getDataPropertyValue("EA_MTAmount7"),
				PropsUtil.getDataPropertyValue("EA_MTDescription7"), PropsUtil.getDataPropertyValue("EA_MTTxnType7"),
				PropsUtil.getDataPropertyValue("EA_MTAccounts7"), PropsUtil.getDataPropertyValue("EA_MTFrequency7"), 0,
				IA_MTCategory6, PropsUtil.getDataPropertyValue("EA_MTNote7"),
				PropsUtil.getDataPropertyValue("EA_MTCheck7"));
		SeleniumUtil.waitForPageToLoad(15000);

		expenseTrend.clickEIAMTLink();
		SeleniumUtil.waitForPageToLoad(10000);
		add_Manual.createOneTimeTransaction(PropsUtil.getDataPropertyValue("EA_MTAmount8"),
				PropsUtil.getDataPropertyValue("EA_MTDescription8"), PropsUtil.getDataPropertyValue("EA_MTTxnType8"),
				PropsUtil.getDataPropertyValue("EA_MTAccounts8"), PropsUtil.getDataPropertyValue("EA_MTFrequency8"), 0,
				IA_MTCategory7, PropsUtil.getDataPropertyValue("EA_MTNote8"),
				PropsUtil.getDataPropertyValue("EA_MTCheck8"));
		SeleniumUtil.waitForPageToLoad(15000);

		expenseTrend.clickEIAMTLink();
		SeleniumUtil.waitForPageToLoad(10000);
		add_Manual.createOneTimeTransaction(PropsUtil.getDataPropertyValue("EA_MTAmount10"),
				PropsUtil.getDataPropertyValue("EA_MTDescription10"), PropsUtil.getDataPropertyValue("EA_MTTxnType10"),
				PropsUtil.getDataPropertyValue("EA_MTAccounts10"), PropsUtil.getDataPropertyValue("EA_MTFrequency10"),
				0, IA_MTCategory8, PropsUtil.getDataPropertyValue("EA_MTNote10"),
				PropsUtil.getDataPropertyValue("EA_MTCheck10"));
		SeleniumUtil.waitForPageToLoad(15000);

	}
}
