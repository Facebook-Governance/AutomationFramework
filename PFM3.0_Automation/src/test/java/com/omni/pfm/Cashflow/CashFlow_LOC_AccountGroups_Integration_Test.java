/*******************************************************************************
 * Copyright (c) 2018 Yodlee Inc. All Rights Reserved.
 *
 * This software is the confidential and proprietary information of Yodlee, Inc.
 * Use is subject to license terms.
 *
 * @author Shivaprasad Bhat
 ******************************************************************************/
package com.omni.pfm.Cashflow;

import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.accessibility.examples.testng.webdriver.test.RunAccessibilityTest;
import com.omni.pfm.Accounts.Accounts_ViewByGroup_Loc;
import com.omni.pfm.PageProcessor.PageParser;
import com.omni.pfm.pages.AccountAddition.AccountAddition;
import com.omni.pfm.pages.CashFlow.*;
import com.omni.pfm.pages.Login.LoginPage;
import com.omni.pfm.pages.TransactionEnhancement1.Transaction_AccountDropDown_Loc;
import com.omni.pfm.pages.TransactionEnhancement1.Transaction_Cashflow_Integration_Loc;
import com.omni.pfm.testBase.TestBase;

import com.omni.pfm.utility.PropsUtil;
import com.omni.pfm.utility.SeleniumUtil;

public class CashFlow_LOC_AccountGroups_Integration_Test extends TestBase {

	static Logger logger = LoggerFactory.getLogger(CashFlow_LOC_AccountGroups_Integration_Test.class);
	Transaction_AccountDropDown_Loc accountFilter;
	Transaction_Cashflow_Integration_Loc txnCF;
	CashFlow_LandingPage_Loc LandingPage;
	AccountAddition accAddition;
	SeleniumUtil util;
	Accounts_ViewByGroup_Loc groupView;

	@BeforeTest(alwaysRun = true)
	public void testInit() throws Exception {
		doInitialization("Cashflow Landing Page Test");
		LandingPage = new CashFlow_LandingPage_Loc(d);
		accAddition = new AccountAddition();
		util = new SeleniumUtil();
		groupView = new Accounts_ViewByGroup_Loc(d);
		txnCF = new Transaction_Cashflow_Integration_Loc(d);
		accountFilter = new Transaction_AccountDropDown_Loc(d);
		LoginPage.loginMain(d, TestBase.loginParameter);
		SeleniumUtil.waitForPageToLoad();

		logger.info("==> Adding account without any transactions!");
		accAddition.linkAccount();
		SeleniumUtil.waitForPageToLoad();
		Assert.assertTrue(accAddition.addAggregatedAccount(PropsUtil.getDataPropertyValue("CF_LOC_DagSite"),
				PropsUtil.getDataPropertyValue("CF_LOC_DagSitePassword")));
		SeleniumUtil.waitForPageToLoad();
		groupView.refreshPage();
		SeleniumUtil.waitForPageToLoad();
		PageParser.forceNavigate("CashFlow", d);
		SeleniumUtil.waitForPageToLoad(3000);
		LandingPage.FTUE();

	}
	
	@Test(priority = 0)
	public void dummyForDependsOnMethods() {
		
	}

	@Test(description = "AT-97601,AT-97598,AT-97603,AT-97604,AT-97607,AT-97608,AT-97609,AT-97610,AT-97611:verify Chart cashflow when select all account", priority = 1)
	public void verifyChartTotalCF() throws Exception {
		logger.info("All account option should be selected  Account dropdown");
		if (LandingPage.CFTimeFilterDropDown().getText()
				.contains(PropsUtil.getDataPropertyValue("EA_3monthsTimeFilter"))) {
			LandingPage.selectTimeFilter(PropsUtil.getDataPropertyValue("CFDefaultTimeFilter"));
			SeleniumUtil.waitForPageToLoad(6000);
		}
		Assert.assertEquals(LandingPage.CFnetCashflowDataLable().getText().trim(),
				PropsUtil.getDataPropertyValue("CF_LOC_ChartNetCFLable"), "cash flow net data lable is not displayed");
		Assert.assertEquals(LandingPage.CFnetCashflowData().getText().trim(),
				PropsUtil.getDataPropertyValue("CF_LOC_ChartNetCFValue"), "net casahflow data is not displayed");
		Assert.assertEquals(LandingPage.CFinflowDataLable().getText().trim(),
				PropsUtil.getDataPropertyValue("CF_LOC_ChartInCFLable"), "cash inflow lable is not displayed");
		Assert.assertEquals(LandingPage.CFinflowData().getText().trim(),
				PropsUtil.getDataPropertyValue("CF_LOC_ChartInCFValue"), "cash inflow data is not displayed");
		Assert.assertEquals(LandingPage.CFoutflowDataLable().getText().trim(),
				PropsUtil.getDataPropertyValue("CF_LOC_ChartOutCFLable"), "cash outflow lable is not displayed");
		Assert.assertEquals(LandingPage.CFoutflowData().getText().trim(),
				PropsUtil.getDataPropertyValue("CF_LOC_ChartOutCFValue"), "cash outflow data is not displayed");

	}

	@Test(description = "AT-97601,AT-97598,AT-97607,AT-97608,AT-97609,AT-97610,AT-97611:verify summery average and summery details", priority = 2, dependsOnMethods = "verifyChartTotalCF")
	public void verifySummeryAndDetailsCF() throws Exception {
		logger.info("cashflow summery average and details ");
		util.assertExpectedActualAmountList(util.getWebElementsValue(LandingPage.CFsummaryAverage()),
				PropsUtil.getDataPropertyValue("CF_LOC_CFSummary"), "summery average is not displayed");
		util.assertExpectedActualAmountList(util.getWebElementsValue(LandingPage.CFsummaryDetails()),
				PropsUtil.getDataPropertyValue("CF_LOC_CFDetails"), "summery details is not displayed");
	}

	@Test(description = "AT-97601,AT-97598,AT-97607,AT-97608,AT-97609,AT-97610,AT-97611:verify table cashflow details", priority = 3, dependsOnMethods = "verifyChartTotalCF")
	public void verifyTableCF() throws Exception {
		logger.info("casflow table details");
		util.assertExpectedActualAmountList(
				util.getWebElementsValue(
						LandingPage.CFTableColoumnLink(PropsUtil.getDataPropertyValue("CF_LOC_CFTableInFlowRow"))),
				PropsUtil.getDataPropertyValue("CF_LOC_CFTableInFlow"), "cashflow table inflow data is not displayed");
		util.assertExpectedActualAmountList(
				util.getWebElementsValue(
						LandingPage.CFTableColoumnLink(PropsUtil.getDataPropertyValue("CF_LOC_CFTableOutFlowRow"))),
				PropsUtil.getDataPropertyValue("CF_LOC_CFTableOutFlow"),
				"cashflow table outflow data is not displayed");
		util.assertExpectedActualAmountList(
				util.getWebElementsValue(LandingPage
						.CFTableColoumnLink(PropsUtil.getDataPropertyValue("CF_LOC_CFTableNetTransferFlowRow"))),
				PropsUtil.getDataPropertyValue("CF_LOC_CFTableNetTransferFlow"),
				"cashflow table net transfer data is not displayed");
		util.assertExpectedActualAmountList(
				util.getWebElementsValue(LandingPage
						.CFTableColoumnAmountLable(PropsUtil.getDataPropertyValue("CF_LOC_CFTableNetFlowRow"))),
				PropsUtil.getDataPropertyValue("CF_LOC_CFTableNetFlow"),
				"cashflow table total netflow is not displayed");
	}

	@Test(description = "AT-97599:verify Loc account group is displaying", priority = 4, dependsOnMethods = "dummyForDependsOnMethods")
	public void verifyLocAccountsGroup() throws Exception {
		logger.info("LOC accounts group");
		PageParser.forceNavigate("AccountsPage", d);
		SeleniumUtil.waitForPageToLoad();
		groupView.clikcCreatgroup();
		groupView.createGroup(PropsUtil.getDataPropertyValue("CF_GroupPopupGroupName"),
				PropsUtil.getDataPropertyValue("CF_GroupPopupGroupAccount").split(","));
		PageParser.forceNavigate("CashFlow", d);
		SeleniumUtil.waitForPageToLoad(3000);
		LandingPage.clickaccountDropDown();

		SeleniumUtil.waitForPageToLoad();
		util.assertExpectedActualList(util.getWebElementsValue(LandingPage.CFGroupNameList()),
				PropsUtil.getDataPropertyValue("CF_GroupPopupGroupName"), "Loc account group is not displayed");
	}

	@Test(description = "AT-97602:verify select group account net cashflow", priority = 6, dependsOnMethods = "verifyLocAccountsGroup")
	public void selectTheCreatedLocGroupInCashFlow() {
		logger.info("account group cashflow ");
		LandingPage.clickCFGroup(PropsUtil.getDataPropertyValue("CF_GroupPopupGroupName"));
		SeleniumUtil.waitForPageToLoad();
		if (LandingPage.CFTimeFilterDropDown().getText()
				.contains(PropsUtil.getDataPropertyValue("EA_3monthsTimeFilter"))) {
			LandingPage.selectTimeFilter(PropsUtil.getDataPropertyValue("CFDefaultTimeFilter"));
			SeleniumUtil.waitForPageToLoad(6000);
		}
	}

	@Test(description = "AT-97602:verify select group account net cashflow", priority = 7, dependsOnMethods = "selectTheCreatedLocGroupInCashFlow")
	public void verifySelectLocAccountsGroup() throws Exception {
		Assert.assertEquals(LandingPage.CFnetCashflowData().getText().trim(),
				PropsUtil.getDataPropertyValue("CF_LOCgroup_ChartNetCFValue"),
				"select group accounts net cash flow data is not displayed");

		Assert.assertEquals(LandingPage.CFinflowData().getText().trim(),
				PropsUtil.getDataPropertyValue("CF_LOCgroup_ChartInCFValue"),
				"selected group accounts cash inflow data is not displayed");

		Assert.assertEquals(LandingPage.CFoutflowData().getText().trim(),
				PropsUtil.getDataPropertyValue("CF_LOCgroup_ChartOutCFValue"),
				"selected group accounts cashout flow data is not displayed");
	}

	@Test(description = "AT-97602:verify group accounts summery average and details", priority = 8, dependsOnMethods = "selectTheCreatedLocGroupInCashFlow")
	public void verifyGroupSummeryAndDetailsCF() throws Exception {
		logger.info("account group cashflow average and details");
		util.assertExpectedActualAmountList(util.getWebElementsValue(LandingPage.CFsummaryAverage()),
				PropsUtil.getDataPropertyValue("CF_LOCgroup_CFSummery"),
				"selected group accounts summery average data is not dispalyed");
		util.assertExpectedActualAmountList(util.getWebElementsValue(LandingPage.CFsummaryDetails()),
				PropsUtil.getDataPropertyValue("CF_LOCgroup_CFDetails"),
				"selected group accounts summery details data is not displayed");
	}

	@Test(description = "AT-97602:verify group accounttable cash flow", priority = 9, dependsOnMethods = "selectTheCreatedLocGroupInCashFlow")
	public void verifyGroupTableCF() throws Exception {

		logger.info("account groups cashflow table details");
		util.assertExpectedActualAmountList(
				util.getWebElementsValue(
						LandingPage.CFTableColoumnLink(PropsUtil.getDataPropertyValue("CF_LOC_CFTableInFlowRow"))),
				PropsUtil.getDataPropertyValue("CF_LOCgroup_CFTableInFlow"),
				"selected group accounts cashfloe table inflow data is not displayed");
		util.assertExpectedActualAmountList(
				util.getWebElementsValue(
						LandingPage.CFTableColoumnLink(PropsUtil.getDataPropertyValue("CF_LOC_CFTableOutFlowRow"))),
				PropsUtil.getDataPropertyValue("CF_LOCgroup_CFTableOutFlow"),
				"selected group accounts cashfloe table outflow data is not displayed");
		util.assertExpectedActualAmountList(
				util.getWebElementsValue(LandingPage
						.CFTableColoumnLink(PropsUtil.getDataPropertyValue("CF_LOC_CFTableNetTransferFlowRow"))),
				PropsUtil.getDataPropertyValue("CF_LOCgroup_CFTableNetTransferFlow"),
				"selected group accounts cashfloe table net transfer data is not displayed");
		util.assertExpectedActualAmountList(
				util.getWebElementsValue(LandingPage
						.CFTableColoumnAmountLable(PropsUtil.getDataPropertyValue("CF_LOC_CFTableNetFlowRow"))),
				PropsUtil.getDataPropertyValue("CF_LOCgroup_CFTableNetFlow"),
				"selected group accounts cashfloe table netflow data is not displayed");
	}

	@Test(description = "AT-97612:verify deposit type transaction", priority = 10,dependsOnMethods = "dummyForDependsOnMethods")
	public void verifyLOCAccountDepostTxn_CF() {
		PageParser.forceNavigate("CashFlow", d);
		SeleniumUtil.waitForPageToLoad();

		txnCF.clickCashFlowAmount(Integer.parseInt(PropsUtil.getDataPropertyValue("Txn_CF_LOCThisMonthRowIndex")),
				Integer.parseInt(PropsUtil.getDataPropertyValue("Txn_CF_LOCThisMonthRowInflow")));
		SeleniumUtil.waitForPageToLoad();
		int accountLOC = 0;
		int accountHE = 0;
		int accountSB = 0;
		int txnsize = accountFilter.getAllPostedAccount_AMT1().size();
		for (int i = 0; i < txnsize; i++) {
			if (accountFilter.getAllPostedAccount_AMT1().get(i).getText().trim().replaceAll("\n", " ")
					.equals(PropsUtil.getDataPropertyValue("Txn_LOc_txnAccountName"))) {
				accountLOC = accountLOC + 1;
			}

			if (accountFilter.getAllPostedAccount_AMT1().get(i).getText().trim().replaceAll("\n", " ")
					.equals(PropsUtil.getDataPropertyValue("Txn_HELOc_txnAccountName"))) {
				accountHE = accountHE + 1;
			}

			if (accountFilter.getAllPostedAccount_AMT1().get(i).getText().trim().replaceAll("\n", " ")
					.equals(PropsUtil.getDataPropertyValue("Txn_SBLOc_txnAccountName"))) {
				accountSB = accountSB + 1;
			}

		}

		Assert.assertTrue(accountLOC >= Integer.parseInt(PropsUtil.getDataPropertyValue("Txn_CF_LOCAccountTxnCount")));
		Assert.assertTrue(accountHE >= Integer.parseInt(PropsUtil.getDataPropertyValue("Txn_CF_LOCAccountTxnCount")));
		Assert.assertTrue(accountSB >= Integer.parseInt(PropsUtil.getDataPropertyValue("Txn_CF_LOCAccountTxnCount")));
	}

	@Test(description = "AT-96988,AT-97612:verify withdrawal type transaction", priority = 11, dependsOnMethods = "dummyForDependsOnMethods")
	public void verifyLOCAccountWithdrawaltTxn_CF() {
		PageParser.forceNavigate("CashFlow", d);
		SeleniumUtil.waitForPageToLoad();

		txnCF.clickCashFlowAmount(Integer.parseInt(PropsUtil.getDataPropertyValue("Txn_CF_LOCLastMonthRowIndex")),
				Integer.parseInt(PropsUtil.getDataPropertyValue("Txn_CF_LOCThisMonthRowOutflow")));
		SeleniumUtil.waitForPageToLoad();
		int txnsize = accountFilter.getAllPostedAccount_AMT1().size();
		int accountLOC = 0;
		int accountHE = 0;

		for (int i = 0; i < txnsize; i++) {
			if (accountFilter.getAllPostedAccount_AMT1().get(i).getText().trim().replaceAll("\n", " ")
					.equals(PropsUtil.getDataPropertyValue("Txn_LOc_txnAccountName"))) {
				accountLOC = accountLOC + 1;
			}

			if (accountFilter.getAllPostedAccount_AMT1().get(i).getText().trim().replaceAll("\n", " ")
					.equals(PropsUtil.getDataPropertyValue("Txn_HELOc_txnAccountName"))) {
				accountHE = accountHE + 1;
			}
		}
		Assert.assertTrue(accountLOC >= Integer.parseInt(PropsUtil.getDataPropertyValue("Txn_CF_LOCAccountTxnCount")));
		Assert.assertTrue(accountHE >= Integer.parseInt(PropsUtil.getDataPropertyValue("Txn_CF_LOCAccountTxnCount")));
	}

	@Test(description = "AT-97601,AT-97613:verify selected time filter net cashflow", priority = 13, dependsOnMethods = "dummyForDependsOnMethods")
	public void verifySelectLocTF() throws Exception {
		logger.info("account group cashflow ");

		PageParser.forceNavigate("CashFlow", d);
		SeleniumUtil.waitForPageToLoad(3000);
		SeleniumUtil.click(LandingPage.CFTimeFilterDropDown());
		SeleniumUtil.click(LandingPage.CFTimeFilterName(PropsUtil.getDataPropertyValue("CF_LOCTF_Value")));
		SeleniumUtil.waitForPageToLoad();
		Assert.assertEquals(LandingPage.CFnetCashflowData().getText().trim(),
				PropsUtil.getDataPropertyValue("CF_LOCTF_ChartNetCFValue"),
				"select time filter net cash flow data is not displayed");

		Assert.assertEquals(LandingPage.CFinflowData().getText().trim(),
				PropsUtil.getDataPropertyValue("CF_LOCTF_ChartInCFValue"),
				"selected time filter cash inflow data is not displayed");

		Assert.assertEquals(LandingPage.CFoutflowData().getText().trim(),
				PropsUtil.getDataPropertyValue("CF_LOCTF_ChartOutCFValue"),
				"selected time filter cashout flow data is not displayed");
	}

	@Test(description = "AT-97613:verify time filter summery average and details", priority = 15, dependsOnMethods = "verifySelectLocTF")
	public void verifyTFSummeryAndDetailsCF() throws Exception {
		logger.info("account group cashflow average and details");
		util.assertExpectedActualAmountList(util.getWebElementsValue(LandingPage.CFsummaryAverage()),
				PropsUtil.getDataPropertyValue("CF_LOCTF_CFSummery"),
				"selected time filter summery average data is not dispalyed");
		util.assertExpectedActualAmountList(util.getWebElementsValue(LandingPage.CFsummaryDetails()),
				PropsUtil.getDataPropertyValue("CF_LOCTF_CFDetails"),
				"selected time filter summery details data is not displayed");
	}

	@Test(description = "AT-97613:verify time filter table cash flow", priority = 16, dependsOnMethods = "verifySelectLocTF")
	public void verifyTFTableCF() throws Exception {
		logger.info("time filter cashflow table details");
		util.assertExpectedActualAmountList(
				util.getWebElementsValue(
						LandingPage.CFTableColoumnLink(PropsUtil.getDataPropertyValue("CF_LOC_CFTableInFlowRow"))),
				PropsUtil.getDataPropertyValue("CF_LOCTF_CFTableInFlow"),
				"selected time filter cashfloe table inflow data is not displayed");
		util.assertExpectedActualAmountList(
				util.getWebElementsValue(
						LandingPage.CFTableColoumnLink(PropsUtil.getDataPropertyValue("CF_LOC_CFTableOutFlowRow"))),
				PropsUtil.getDataPropertyValue("CF_LOCTF_CFTableOutFlow"),
				"selected time filter cashfloe table outflow data is not displayed");
		util.assertExpectedActualAmountList(
				util.getWebElementsValue(LandingPage
						.CFTableColoumnLink(PropsUtil.getDataPropertyValue("CF_LOC_CFTableNetTransferFlowRow"))),
				PropsUtil.getDataPropertyValue("CF_LOCTF_CFTableNetTransferFlow"),
				"selected time filter cashfloe table net transfer data is not displayed");
		util.assertExpectedActualAmountList(
				util.getWebElementsValue(LandingPage
						.CFTableColoumnAmountLable(PropsUtil.getDataPropertyValue("CF_LOC_CFTableNetFlowRow"))),
				PropsUtil.getDataPropertyValue("CF_LOCTF_CFTableNetFlow"),
				"selected time filter cashfloe table netflow data is not displayed");
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
