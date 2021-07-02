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


public class ExpanseAnalysis_NoValidAccount_Test extends TestBase {

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
		Assert.assertTrue(accAddition.addAggregatedAccount(PropsUtil.getDataPropertyValue("EIA_NoValidAccountSite"), 
				PropsUtil.getDataPropertyValue("EIA_NoValidAccountPassword")));
		expenseTrend.navigateToEA();
	}
	@Test(description="AT-92145:verify no accounts page in Eexpanse Page",priority=1)
public void noAccountScreen()
{
		if(PageParser.isElementPresent("EIALinkAcc_Mob", "Expense", null))
		{
		Assert.assertTrue(expenseTrend.EIALinkAcc_Mob().isDisplayed(),"link an account button is not displayed");
		}
		else {	
		Assert.assertEquals(expenseTrend.EIALinkAccount().getText(),PropsUtil.getDataPropertyValue("EA_LinkAnAccount"),
				" link an account button is not displayed");}
		Assert.assertEquals(expLandingPage.AccessNotGrantedMsg().getText(),PropsUtil.getDataPropertyValue("EA_NoDataHeaderLable"),
				" no data header is not displayed");
		Assert.assertEquals(expLandingPage.AccessNotGrantedDiscription().getText(),PropsUtil.getDataPropertyValue("EA_NoDataDescription"),
				"no data description is not displayed");
}
	
}
