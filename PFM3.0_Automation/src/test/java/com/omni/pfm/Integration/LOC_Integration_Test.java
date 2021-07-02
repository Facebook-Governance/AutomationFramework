package com.omni.pfm.Integration;
 
import java.util.concurrent.TimeUnit;

/**
* Copyright (c) 2018 Yodlee Inc. All Rights Reserved.  
*
* This software is the confidential and proprietary information of
* Yodlee, Inc. Use is subject to license terms.
*
* @author mallikan
*/ 
 



import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.omni.pfm.PageProcessor.PageParser;
import com.omni.pfm.pages.AccountAddition.AccountAddition;
import com.omni.pfm.pages.AccountSettings.AccountsSetting_GlobalSettings_Loc;
import com.omni.pfm.pages.AccountSettings.AggregatedAccount_Settings_Loc;
import com.omni.pfm.pages.Expense_IncomeAnalysis.ExpLandingPage_Loc;
import com.omni.pfm.pages.Expense_IncomeAnalysis.Expense_Income_Trend_Loc;
import com.omni.pfm.pages.InvestmentHoldings.InvestmentHoldings_Loc;
import com.omni.pfm.pages.Login.LoginPage;
import com.omni.pfm.pages.Networth.NetWorth_Loc;
import com.omni.pfm.testBase.TestBase;
import com.omni.pfm.utility.PropsUtil;
import com.omni.pfm.utility.SeleniumUtil;

public class LOC_Integration_Test extends TestBase {
	NetWorth_Loc netWorth;
	InvestmentHoldings_Loc investmentHoldings;
	AccountAddition accountAdd;
	Expense_Income_Trend_Loc accountDropDown;
	ExpLandingPage_Loc ExpLandingPage;
	AccountsSetting_GlobalSettings_Loc globalSetting;
	AggregatedAccount_Settings_Loc settings;

	@BeforeClass()
	public void init() throws Exception {

		doInitialization("Networth");
		netWorth = new NetWorth_Loc(d);
		accountAdd = new AccountAddition();
		accountDropDown = new Expense_Income_Trend_Loc(d);
		investmentHoldings = new InvestmentHoldings_Loc(d);
		ExpLandingPage = new ExpLandingPage_Loc(d);
		globalSetting = new AccountsSetting_GlobalSettings_Loc(d);
		settings = new AggregatedAccount_Settings_Loc(d);
		d.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
	}

	@Test(description = "login to the user", groups = { "DesktopBrowsers,sanity" }, priority = 1, enabled = true)
	public void login() throws Exception {
		
		LoginPage.loginMain(d, loginParameter);
		accountAdd.linkAccount();
		accountAdd.addAggregatedAccount("CMA_LOC.site16441.1", "site16441.1");

	}

	@Test(description = "AT-98600:Verify LOC & CMA accounts are displaying in Networth Finapp", priority = 2,dependsOnMethods = "login", enabled = true)
	public void verify_LOC_CMA_NetworthFinapp() {

		PageParser.forceNavigate("NetWorth", d);
		netWorth.completeFTUEFlow();

		SeleniumUtil.click(investmentHoldings.accountDropDown());
		SeleniumUtil.click(accountDropDown.AccountGroupTab().get(0));

		Assert.assertTrue(netWorth.verifyAccountsUnderAcntsDropDown("expected_LOC_Networth_container"),
				"Error ; LOC and CMA accounts are not displaying under accounts dropdown");

	}

	@Test(description = "AT-98600:Verify CMA account is displaying in Investmet Holdings Finapp", priority = 3,dependsOnMethods = "login", enabled = true)
	public void Verify_CMA_InvestmentHoldings() throws Exception {

		PageParser.forceNavigate("InvestmentHoldings", d);

		investmentHoldings.completeFtueFlow();
		SeleniumUtil.click(investmentHoldings.accountDropDown());

		Assert.assertTrue(netWorth.verifyAccountsUnderAcntsDropDown("expected_CMA_IH"),
				"Error ; CMA accounts are not displaying under accounts dropdown");

	}

	@Test(description = "AT-98600:Verify LOC & CMA accounts are displaying in Expense Analysis Finapp", priority = 4,dependsOnMethods = "login", enabled = true)
	public void verify_CMA_LOC_ExpenseAnalysis() {

		PageParser.forceNavigate("Expense", d);
		SeleniumUtil.click(ExpLandingPage.continueButton());
		SeleniumUtil.click(ExpLandingPage.gotoAnalysisBtn());

		accountDropDown.clickEIAccountDropDown();
		Assert.assertTrue(netWorth.verifyAccountsUnderAcntsDropDown("expected_LOC_EIA_container"),
				"Error ; LOC and CMA accounts are not displaying under accounts dropdown");
	}

	@Test(description = "AT-98600:Verify LOC & CMA accounts are displaying in Income Analysis Finapp", priority = 5,dependsOnMethods = "login", enabled = true)
	public void verify_CMA_LOC_IncomeAnalysis() {
		
		accountDropDown.navigateToIncomeAnalysis();
		accountDropDown.clickEIAccountDropDown();
		Assert.assertTrue(netWorth.verifyAccountsUnderAcntsDropDown("expected_LOC_EIA_container"),
				"Error ; LOC and CMA accounts are not displaying under accounts dropdown");
	}

	@Test(description = "AT-98600:Verify Alert settings pop up for Investment Holding in AertSetting  Finapp ", priority = 6,dependsOnMethods = "login", enabled = true)
	public void verifyAlertSettings_IH() {

		PageParser.forceNavigate("AlertSetting", d);
		SeleniumUtil.click(settings.Alert_IH());

		String alertBalIncrease = investmentHoldings.alertBalance().get(0).getText();
		Assert.assertEquals(alertBalIncrease, PropsUtil.getDataPropertyValue("IH_alertBalIncrease"));
	}

	@Test(description = "AT-98600:Verify LOC & CMA accounts are displaying under Accounts in Settings Finapp", priority = 7,dependsOnMethods = "login", enabled = true)
	public void verifyAlertSettings_Accounts() {
		SeleniumUtil.click(settings.Alert_Accounts());
		Assert.assertTrue(settings.verifyAccountsinAlerts("AlertAccounts_CMA_LOC_Accounts"),
				"Error ; LOC and CMA accounts are not displaying under accounts dropdown");

	}
}