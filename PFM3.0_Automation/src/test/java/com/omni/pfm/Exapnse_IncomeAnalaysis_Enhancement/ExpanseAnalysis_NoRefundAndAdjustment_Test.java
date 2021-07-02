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

import com.omni.pfm.config.Config;
import com.omni.pfm.pages.AccountAddition.AccountAddition;

import com.omni.pfm.pages.Expense_IncomeAnalysis.Expense_Income_Trend_Loc;
import com.omni.pfm.pages.Login.LoginPage;
import com.omni.pfm.pages.TransactionEnhancement1.Add_Manual_Transaction_Loc;
import com.omni.pfm.pages.TransactionEnhancement1.Transaction_AccountDropDown_Loc;
import com.omni.pfm.pages.TransactionEnhancement1.Transaction_Filter_Loc;

import com.omni.pfm.testBase.TestBase;
import com.omni.pfm.utility.PropsUtil;
import com.omni.pfm.utility.SeleniumUtil;



public class ExpanseAnalysis_NoRefundAndAdjustment_Test extends TestBase {

	
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

		

		accAddition = new AccountAddition();
		expenseTrend=new  Expense_Income_Trend_Loc(d);
		util=new SeleniumUtil();
		txnAcct=new Transaction_AccountDropDown_Loc(d);
		
		add_Manual=new Add_Manual_Transaction_Loc(d);
		filter=new Transaction_Filter_Loc(d);
		LoginPage.loginMain(d, loginParameter);
		SeleniumUtil.waitForPageToLoad();
	
		accAddition.linkAccount();
		Assert.assertTrue(accAddition.addAggregatedAccount(PropsUtil.getDataPropertyValue("EIA_HLC_NoRefundSite"),
				PropsUtil.getDataPropertyValue("EIA_HLC_NoRefundPassword")));
		expenseTrend.pageRefresh();
		expenseTrend.navigateToEA();
		SeleniumUtil.waitForPageToLoad();
		expenseTrend.FTUE();
		SeleniumUtil.waitForPageToLoad();
	}
	
	@Test(description="verify no accounts page in Eexpanse Page",priority=2)
	public void verifyHLCNoRefund()
	{
		
		expenseTrend.clickLegendMonth(Integer.parseInt(PropsUtil.getDataPropertyValue("EA_TrendTOHLC_MonthList")));
		Assert.assertTrue(expenseTrend.EIAHLCCatRefundMesgList().size()==Integer.parseInt(PropsUtil.getDataPropertyValue("EA_HLC_NoRefundSize")),"refund is not displayed");
		Assert.assertTrue(expenseTrend.EIAHLCCatRefundAmountList().size()==Integer.parseInt(PropsUtil.getDataPropertyValue("EA_HLC_NoRefundSize")),"refund is not displayed");
		Assert.assertTrue(expenseTrend.EIAHLCCatRefundViewDetailsList().size()==Integer.parseInt(PropsUtil.getDataPropertyValue("EA_HLC_NoRefundSize")),"refund is not displayed");

	}
	@Test(description="AT-93470:verify no accounts page in Eexpanse Page",priority=3,dependsOnMethods="verifyHLCNoRefund")
	public void verifyRefundDate()
	{
		addRefund();
		expenseTrend.clickViewDetails();
		Assert.assertEquals(expenseTrend.EIAHLCCatUnCatTxnDateHeaderList().get(Integer.parseInt(PropsUtil.getDataPropertyValue("EA_HLC_NoRefundDate1"))).getText().trim(),
				PropsUtil.getDataPropertyValue("EA_HLC_UnCatTxnDateHeader")+" "+
				expenseTrend.getMonthMMMMDD(Integer.parseInt(PropsUtil.getDataPropertyValue("EA_HLC_RefundtxnDate1"))),
				"uncategorized txn date header is not displayed");
		Assert.assertEquals(expenseTrend.EIAHLCCatUnCatTxnDateHeaderList().get(Integer.parseInt(PropsUtil.getDataPropertyValue("EA_HLC_NoRefundDate2"))).getText().trim(),
				expenseTrend.getMonthMMMMDD(Integer.parseInt(PropsUtil.getDataPropertyValue("EA_HLC_RefundtxnDate2"))),
				"uncategorized txn date header is not displayed");
	}
	@Test(description="AT-93485:verify no accounts page in Eexpanse Page",priority=4,dependsOnMethods="verifyRefundDate")
	public void verifyDeleteRefund()
	{
		expenseTrend.deleteallProjectedtransaction();
		if(Config.appFlag.equals("app")||Config.appFlag.equals("emulator"))
        {
        SeleniumUtil.click(expenseTrend.EIAcloseTransPopup());}

		Assert.assertEquals(expenseTrend.EIAByCategory().getText(),
				PropsUtil.getDataPropertyValue("EA_ExpenseByCategory"), "HLC Category Header is not displayed");
	}
	
	@Test(description="verify no accounts page in Eexpanse Page",priority=5)
	public void verifyHLCNoAdj()
	{
		expenseTrend.navigateToEA();
		expenseTrend.selectIncome();
		expenseTrend.clickLegendMonth(Integer.parseInt(PropsUtil.getDataPropertyValue("EA_TrendTOHLC_MonthList")));
		Assert.assertTrue(expenseTrend.EIAHLCCatRefundMesgList().size()==Integer.parseInt(PropsUtil.getDataPropertyValue("EA_HLC_NoRefundSize")),"refund is not displayed");
		Assert.assertTrue(expenseTrend.EIAHLCCatRefundAmountList().size()==Integer.parseInt(PropsUtil.getDataPropertyValue("EA_HLC_NoRefundSize")),"refund is not displayed");
		Assert.assertTrue(expenseTrend.EIAHLCCatRefundViewDetailsList().size()==Integer.parseInt(PropsUtil.getDataPropertyValue("EA_HLC_NoRefundSize")),"refund is not displayed");

	}
	@Test(description="AT-93470:verify no accounts page in Eexpanse Page",priority=6,dependsOnMethods="verifyHLCNoAdj")
	public void verifyAdjDate()
	{
		addAdj();
		expenseTrend.clickViewDetails();
		Assert.assertEquals(expenseTrend.EIAHLCCatUnCatTxnDateHeaderList().get(Integer.parseInt(PropsUtil.getDataPropertyValue("EA_HLC_NoRefundDate1"))).getText().trim(),
				PropsUtil.getDataPropertyValue("EA_HLC_UnCatTxnDateHeader")+" "+
				expenseTrend.getMonthMMMMDD(Integer.parseInt(PropsUtil.getDataPropertyValue("EA_HLC_RefundtxnDate1"))),
				"uncategorized txn date header is not displayed");
		Assert.assertEquals(expenseTrend.EIAHLCCatUnCatTxnDateHeaderList().get(Integer.parseInt(PropsUtil.getDataPropertyValue("EA_HLC_NoRefundDate2"))).getText().trim(),
				expenseTrend.getMonthMMMMDD(Integer.parseInt(PropsUtil.getDataPropertyValue("EA_HLC_RefundtxnDate2"))),
				"uncategorized txn date header is not displayed");
	}
	@Test(description="AT-93485:verify no accounts page in Eexpanse Page",priority=7,dependsOnMethods="verifyAdjDate")
	public void verifyDeleteAdj()
	{
		expenseTrend.deleteallProjectedtransaction();
		if(Config.appFlag.equals("app")||Config.appFlag.equals("emulator"))
        {
         SeleniumUtil.click(expenseTrend.EIAcloseTransPopup());}

		Assert.assertEquals(expenseTrend.EIAByCategory().getText(),
				PropsUtil.getDataPropertyValue("IA_ExpenseByCategory"), "HLC Category Header is not displayed");
	}
	
public void addRefund()
{
    expenseTrend.clickEIAMTLink();

	SeleniumUtil.waitForPageToLoad();
    add_Manual.createOneTimeTransaction(PropsUtil.getDataPropertyValue("EA_MTAmount5"), 
			PropsUtil.getDataPropertyValue("EA_MTDescription5"), 
			PropsUtil.getDataPropertyValue("EA_MTTxnType5"),
			PropsUtil.getDataPropertyValue("EA_MTAccounts5"), 
			PropsUtil.getDataPropertyValue("EA_MTFrequency5"), 0, 
			PropsUtil.getDataPropertyValue("EA_MTCategory5"),
			PropsUtil.getDataPropertyValue("EA_MTNote5"), 
			PropsUtil.getDataPropertyValue("EA_MTCheck5"));
	SeleniumUtil.waitForPageToLoad();
	
    expenseTrend.clickEIAMTLink();
    SeleniumUtil.waitForPageToLoad();
    add_Manual.createOneTimeTransaction(PropsUtil.getDataPropertyValue("EA_MTAmount5"), 
			PropsUtil.getDataPropertyValue("EA_MTDescription5"), 
			PropsUtil.getDataPropertyValue("EA_MTTxnType5"),
			PropsUtil.getDataPropertyValue("EA_MTAccounts5"), 
			PropsUtil.getDataPropertyValue("EA_MTFrequency5"), -1, 
			PropsUtil.getDataPropertyValue("EA_MTCategory5"),
			PropsUtil.getDataPropertyValue("EA_MTNote5"), 
			PropsUtil.getDataPropertyValue("EA_MTCheck5"));
	SeleniumUtil.waitForPageToLoad();
}
public void addAdj()
{
    expenseTrend.clickEIAMTLink();

	SeleniumUtil.waitForPageToLoad();
    add_Manual.createOneTimeTransaction(PropsUtil.getDataPropertyValue("IA_MTAmount9"), 
			PropsUtil.getDataPropertyValue("IA_MTDescription9"), 
			PropsUtil.getDataPropertyValue("IA_MTTxnType9"),
			PropsUtil.getDataPropertyValue("IA_MTAccounts9"), 
			PropsUtil.getDataPropertyValue("IA_MTFrequency9"), 0, 
			PropsUtil.getDataPropertyValue("IA_MTCategory9"),
			PropsUtil.getDataPropertyValue("IA_MTNote9"), 
			PropsUtil.getDataPropertyValue("IA_MTCheck9"));
	SeleniumUtil.waitForPageToLoad();
    expenseTrend.clickEIAMTLink();

	SeleniumUtil.waitForPageToLoad();
    add_Manual.createOneTimeTransaction(PropsUtil.getDataPropertyValue("IA_MTAmount9"), 
			PropsUtil.getDataPropertyValue("IA_MTDescription9"), 
			PropsUtil.getDataPropertyValue("IA_MTTxnType9"),
			PropsUtil.getDataPropertyValue("IA_MTAccounts9"), 
			PropsUtil.getDataPropertyValue("IA_MTFrequency9"), -1, 
			PropsUtil.getDataPropertyValue("IA_MTCategory9"),
			PropsUtil.getDataPropertyValue("IA_MTNote9"), 
			PropsUtil.getDataPropertyValue("IA_MTCheck9"));
	SeleniumUtil.waitForPageToLoad();
	
}
}
