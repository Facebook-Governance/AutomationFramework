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



public class ExpanseAnalysis_UnCatgeorizedExpense_Test extends TestBase {
	ExpLandingPage_Loc expLandingPage;
	Transaction_Filter_Loc filter;
	AccountAddition accAddition;
	Expense_Income_Trend_Loc expenseTrend;
	SeleniumUtil util;
	Transaction_AccountDropDown_Loc txnAcct;
	Add_Manual_Transaction_Loc add_Manual;
	@BeforeClass(alwaysRun=true)

	public void init() throws Exception
	{
		doInitialization("Expense Analysis");

		expLandingPage = new ExpLandingPage_Loc(d);

		accAddition = new AccountAddition();
		expenseTrend=new  Expense_Income_Trend_Loc(d);
		util=new SeleniumUtil();
		txnAcct=new Transaction_AccountDropDown_Loc(d);
		add_Manual=new Add_Manual_Transaction_Loc(d);
		filter=new Transaction_Filter_Loc(d);
		LoginPage.loginMain(d, loginParameter);
		SeleniumUtil.waitForPageToLoad();
		accAddition.linkAccount();
		Assert.assertTrue(accAddition.addAggregatedAccount(PropsUtil.getDataPropertyValue("EIA_HLC_NoUncatgeorizedSite"), 
				PropsUtil.getDataPropertyValue("EIA_HLC_NoUncatgeorizedPassword")));
		expenseTrend.pageRefresh();
		expenseTrend.navigateToEA();
		expenseTrend.FTUE();
	}
	
	
	@Test(description="AT-92181:verify no accounts page in Eexpanse Page",priority=1)
	public void verifyTopTxn()
	{
		if(Config.appFlag.equals("web")||Config.appFlag.equals("false")){
		util.assertActualList(util.getWebElementsValue(expenseTrend.EIATrendTopTxnDate()),expenseTrend.getToTxnMonth(
				Integer.parseInt(PropsUtil.getDataPropertyValue("EA_TrendTopTxnDateHeader")), Integer.parseInt(PropsUtil.getDataPropertyValue("EA_TrendTopTxnDateHeader"))), "date header is not displayed");
		Assert.assertTrue(expenseTrend.EIATrendTopTxnDescriprion().size()==Integer.parseInt(PropsUtil.getDataPropertyValue("EA_TrendTopTxnSize")), 
				"only 3 transaction is not displayed");
		Assert.assertTrue(expenseTrend.EIATrendTopTxnAccount().size()==Integer.parseInt(PropsUtil.getDataPropertyValue("EA_TrendTopTxnSize")), 
				"only 3 transaction is not displayed");
		Assert.assertTrue(expenseTrend.EIATrendTopTxnCatgeory().size()==Integer.parseInt(PropsUtil.getDataPropertyValue("EA_TrendTopTxnSize")), 
				"only 3 transaction is not displayed");
		Assert.assertTrue(expenseTrend.EIATrendTopTxnAmount().size()==Integer.parseInt(PropsUtil.getDataPropertyValue("EA_TrendTopTxnSize")), 
				"only 3 transaction is not displayed");
	}}
   @Test(description="AT-92266:verify no accounts page in Eexpanse Page",priority=2)
	public void verifyHLCNoUncatTxn()
	{		
		expenseTrend.clickLegendMonth(Integer.parseInt(PropsUtil.getDataPropertyValue("EA_TrendTOHLC_MonthList")));

		Assert.assertTrue(expenseTrend.EIAHLCCatUnCatTxnHeaderList().get(Integer.parseInt(PropsUtil.getDataPropertyValue("EA_HLC_UnCatgorizedTXnSize"))).getText().equals(""), 
				"uncatgeory txn header is not displayed");
		Assert.assertTrue(expenseTrend.EIAHLCCatUnCatTxnDateHeaderList().size()==Integer.parseInt(PropsUtil.getDataPropertyValue("EA_HLC_UnCatgorizedTXnSize")),
				"uncategorized txn date header");
		Assert.assertTrue(expenseTrend.EIAHLCCatUnCatTxnCat().size()==Integer.parseInt(PropsUtil.getDataPropertyValue("EA_HLC_UnCatgorizedTXnSize")),
				"uncategorized txn date header is not displayed");
		
	}
	@Test(description="AT-93503:verify no accounts page in Eexpanse Page",priority=3,dependsOnMethods="verifyHLCNoUncatTxn")
	public void verifyFTHide()
	{
		Assert.assertTrue(expenseTrend.EIAFeatureTure().getAttribute("class").contains("hide"));
	}
	@Test(description="AT-92181:verify no accounts page in Eexpanse Page",priority=4)
	public void verifyTopTxn_IA()
	{
		expenseTrend.navigateToEA();
		expenseTrend.selectIncome();
		if(Config.appFlag.equals("web")||Config.appFlag.equals("false")){
		util.assertActualList(util.getWebElementsValue(expenseTrend.EIATrendTopTxnDate()),expenseTrend.getToTxnMonth(
				Integer.parseInt(PropsUtil.getDataPropertyValue("EA_TrendTopTxnDateHeader")), Integer.parseInt(PropsUtil.getDataPropertyValue("EA_TrendTopTxnDateHeader"))), 
				"date header is not displayed");
		Assert.assertTrue(expenseTrend.EIATrendTopTxnDescriprion().size()==Integer.parseInt(PropsUtil.getDataPropertyValue("IA_TrendTopTxnSize")), 
				"only 3 transaction is not displayed");
		Assert.assertTrue(expenseTrend.EIATrendTopTxnAccount().size()==Integer.parseInt(PropsUtil.getDataPropertyValue("IA_TrendTopTxnSize")), 
				"only 3 transaction is not displayed");
		Assert.assertTrue(expenseTrend.EIATrendTopTxnCatgeory().size()==Integer.parseInt(PropsUtil.getDataPropertyValue("IA_TrendTopTxnSize")), 
				"only 3 transaction is not displayed");
		Assert.assertTrue(expenseTrend.EIATrendTopTxnAmount().size()==Integer.parseInt(PropsUtil.getDataPropertyValue("IA_TrendTopTxnSize")), 
				"only 3 transaction is not displayed");
	}}
	
	@Test(description="AT-92266:verify no accounts page in Eexpanse Page",priority=5,dependsOnMethods="verifyTopTxn_IA")
	public void verifyHLCNoUncatTxn_IA()
	{		
		expenseTrend.clickLegendMonth(Integer.parseInt(PropsUtil.getDataPropertyValue("EA_TrendTOHLC_MonthList")));

		Assert.assertTrue(expenseTrend.EIAHLCCatUnCatTxnHeaderList().get(Integer.parseInt(PropsUtil.getDataPropertyValue("EA_HLC_UnCatgorizedTXnSize"))).getText().equals(""), 
				"uncatgeory txn header is not displayed");
		Assert.assertTrue(expenseTrend.EIAHLCCatUnCatTxnDateHeaderList().size()==Integer.parseInt(PropsUtil.getDataPropertyValue("EA_HLC_UnCatgorizedTXnSize")),
				"uncategorized txn date header is not displayed");
		Assert.assertTrue(expenseTrend.EIAHLCCatUnCatTxnCat().size()==Integer.parseInt(PropsUtil.getDataPropertyValue("EA_HLC_UnCatgorizedTXnSize")),
				"uncategorized txn date header is not displayed");
		
	}
	@Test(description="AT-93503:verify no accounts page in Eexpanse Page",priority=6,dependsOnMethods="verifyHLCNoUncatTxn_IA")
	public void verifyFTHide_IA()
	{
		Assert.assertTrue(expenseTrend.EIAFeatureTure().getAttribute("class").contains("hide"),"FT is not displayed");
	}
}
