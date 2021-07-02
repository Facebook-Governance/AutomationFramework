package com.omni.pfm.Integration;
/**
* Copyright (c) 2019 Yodlee Inc. All Rights Reserved. 
*
* This software is the confidential and proprietary information of
* Yodlee, Inc. Use is subject to license terms.
*
* @author mallikan
*/ 
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.omni.pfm.Accounts.AccountsViewByGroup_Loc;
import com.omni.pfm.Accounts.Accounts_DeleteAccount_Loc;
import com.omni.pfm.Accounts.Accounts_ViewByGroup_Loc;
import com.omni.pfm.PageProcessor.PageParser;
import com.omni.pfm.pages.AccountAddition.AccountAddition;
import com.omni.pfm.pages.AccountSettings.AccountGroup_Settings_Loc;
import com.omni.pfm.pages.BudgetEnhancement.Budget_Income_And_Bill_Summery_Loc;
import com.omni.pfm.pages.BudgetEnhancement.Create_Budget_Loc;
import com.omni.pfm.pages.CashFlow.CashFlow_LandingPage_Loc;
import com.omni.pfm.pages.CashFlow.CashFlow_Table_Loc;
import com.omni.pfm.pages.Dashboard.AccountSharing_Loc;
import com.omni.pfm.pages.Dashboard.Account_Loc;
import com.omni.pfm.pages.Dashboard.Budget_Loc;
import com.omni.pfm.pages.Dashboard.CashFlow_Loc;
import com.omni.pfm.pages.Dashboard.DashBoard_Transaction_Widget_Loc;
import com.omni.pfm.pages.Dashboard.DashBoradInvestmentLoc;
import com.omni.pfm.pages.Dashboard.NetworthLoc;
import com.omni.pfm.pages.Dashboard.Spending_Loc;
import com.omni.pfm.pages.Expense_IncomeAnalysis.ExpLandingPage_Loc;
import com.omni.pfm.pages.Expense_IncomeAnalysis.Expense_Income_Trend_Loc;
import com.omni.pfm.pages.InvestmentHoldings.InvestmentHoldings_Loc;
import com.omni.pfm.pages.Login.LoginPage;
import com.omni.pfm.pages.Networth.NetWorth_Loc;
import com.omni.pfm.pages.TransactionEnhancement1.Add_Manual_Transaction_Loc;
import com.omni.pfm.pages.TransactionEnhancement1.Transaction_AccountDropDown_Loc;
import com.omni.pfm.pages.TransactionEnhancement1.Transaction_Filter_Loc;
import com.omni.pfm.testBase.TestBase;
import com.omni.pfm.utility.PropsUtil;
import com.omni.pfm.utility.SeleniumUtil;

public class Integration_Transaction_GroupFilterStickyNess extends TestBase {

	ExpLandingPage_Loc expLandingPage;
	Transaction_Filter_Loc filter;
	AccountAddition accountAddition;
	Expense_Income_Trend_Loc expenseTrend;
	SeleniumUtil util;
	Transaction_AccountDropDown_Loc txnAcct;
	Add_Manual_Transaction_Loc add_Manual;
	Accounts_ViewByGroup_Loc groupView;
	Create_Budget_Loc createBudget;
	Budget_Income_And_Bill_Summery_Loc budgetSummery;
	CashFlow_LandingPage_Loc cashFlowLandingPage;
	AccountGroup_Settings_Loc accountGroupSetting;
	Accounts_DeleteAccount_Loc deleetAccount;
	NetWorth_Loc netWorth;
	InvestmentHoldings_Loc investmentHoldings;
	CashFlow_Loc DashCF;
	CashFlow_LandingPage_Loc LandingPage;
	Spending_Loc spending;
	NetworthLoc Networth;
	DashBoradInvestmentLoc dashBoardInvestment;
	Budget_Loc budget;
	Transaction_AccountDropDown_Loc trnxAcntFilter;
	AccountsViewByGroup_Loc viewByGroup;
	CashFlow_Table_Loc table;
	Account_Loc account;
	DashBoard_Transaction_Widget_Loc TxnDBWidgetLoc;
	AccountSharing_Loc accountSharingLoc;

	@BeforeClass(alwaysRun = true)

	public void init() throws Exception {
		doInitialization("Expense Analysis");

		expLandingPage = new ExpLandingPage_Loc(d);

		accountAddition = new AccountAddition();
		expenseTrend = new Expense_Income_Trend_Loc(d);
		util = new SeleniumUtil();
		txnAcct = new Transaction_AccountDropDown_Loc(d);
		add_Manual = new Add_Manual_Transaction_Loc(d);
		groupView = new Accounts_ViewByGroup_Loc(d);
		filter = new Transaction_Filter_Loc(d);
		createBudget = new Create_Budget_Loc(d);
		budgetSummery = new Budget_Income_And_Bill_Summery_Loc(d);
		cashFlowLandingPage = new CashFlow_LandingPage_Loc(d);
		accountGroupSetting = new AccountGroup_Settings_Loc(d);
		deleetAccount = new Accounts_DeleteAccount_Loc(d);
		netWorth = new NetWorth_Loc(d);
		investmentHoldings = new InvestmentHoldings_Loc(d);
		dashBoardInvestment = new DashBoradInvestmentLoc(d);
		accountSharingLoc = new AccountSharing_Loc(d);

		DashCF = new CashFlow_Loc(d);
		LandingPage = new CashFlow_LandingPage_Loc(d);
		spending = new Spending_Loc(d);
		Networth = new NetworthLoc(d);
		budget = new Budget_Loc(d);
		trnxAcntFilter = new Transaction_AccountDropDown_Loc(d);
		viewByGroup = new AccountsViewByGroup_Loc(d);
		table = new CashFlow_Table_Loc(d);
		account = new Account_Loc(d);
		TxnDBWidgetLoc = new DashBoard_Transaction_Widget_Loc(d);

		LoginPage.loginMain(d, loginParameter);
		SeleniumUtil.waitForPageToLoad();

		accountAddition.linkAccount();
		accountAddition.addAggregatedAccount(PropsUtil.getDataPropertyValue("Stickyness_LinkAccountName"),
				PropsUtil.getDataPropertyValue("Stickyness_LinkAccountPAssword"));

	}

	@Test(description = "AT-104573: verify group sticky ness when group is selected from EA to Transaction finapp", priority = 1)
	public void selectGroup_EAToTransaction() {
		PageParser.forceNavigate("AccountsPage", d);
		SeleniumUtil.waitForPageToLoad();
		groupView.clikcCreatgroup();
		groupView.createGroup(PropsUtil.getDataPropertyValue("Stickyness_GroupPopupGroupName"),
				PropsUtil.getDataPropertyValue("Stickness_GroupPopupGroupAccount").split(","));
		groupView.createGroupMore(PropsUtil.getDataPropertyValue("Stickness_GroupNames").split(","),
				PropsUtil.getDataPropertyValue("Stickness_GroupNamesAccounts").split(","));
		SeleniumUtil.waitForPageToLoad();
		expenseTrend.navigateToEA();
		expenseTrend.FTUE();
		expenseTrend.clickEIAccountDropDown();
		expenseTrend.clickGroup(PropsUtil.getDataPropertyValue("Stickyness_GroupPopupGroupName"));

		PageParser.forceNavigate("Transaction", d);
		SeleniumUtil.waitForPageToLoad();
		System.out.println("Transaction page:" + trnxAcntFilter.AllAccType_tran().getText());

		Assert.assertEquals(trnxAcntFilter.AllAccType_tran().getText().trim(),
				PropsUtil.getDataPropertyValue("Stickyness_GroupPopupGroupName"), "selected group is not displayed");
	}

	@Test(description = "AT-104574:verify group sticky ness when group is selected from CF to Transaction finapp", dependsOnMethods = "selectGroup_EAToTransaction", priority = 2)
	public void verifyGroup_CFtoTransaction() {
		PageParser.forceNavigate("CashFlow", d);
		SeleniumUtil.waitForPageToLoad();
		cashFlowLandingPage.FTUE();
		Assert.assertEquals(cashFlowLandingPage.CFAccountDropDown().getText().trim(),
				PropsUtil.getDataPropertyValue("Stickyness_GroupPopupGroupName"),
				"selected group is not displayed in CF");

		SeleniumUtil.click(table.getCategory(2));
		SeleniumUtil.waitForPageToLoad();
		Assert.assertTrue(LandingPage.TransactionTitle().isDisplayed());

		SeleniumUtil.waitForPageToLoad();
		System.out.println("Transaction page:" + trnxAcntFilter.AllAccType_tran().getText());

		Assert.assertEquals(trnxAcntFilter.AllAccType_tran().getText().trim(),
				PropsUtil.getDataPropertyValue("Stickyness_GroupPopupGroupName"),
				"selected group is not displayed in Transaction Account Group");

	}

	@Test(description = "AT-104575:verify group sticky ness when group is selected from Budget to Transaction finapp", dependsOnMethods = "verifyGroup_CFtoTransaction", priority = 3)
	public void verifyGroup_BudgetToTransaction() {
		PageParser.forceNavigate("Budget", d);
		SeleniumUtil.waitForPageToLoad();
		createBudget.creatBudget(PropsUtil.getDataPropertyValue("Budget_Loc_CreateBudgetIncomeAmount"),
				PropsUtil.getDataPropertyValue("Budget_Loc_CreateBudgetCategoryAmount"));

		SeleniumUtil.waitForPageToLoad();
		budgetSummery.clickcreateBudget();
		SeleniumUtil.waitForPageToLoad();
		budgetSummery.SelectAccountGroup(PropsUtil.getDataPropertyValue("Stickyness_GroupPopupGroupName"));

		createBudget.creatAccountGroupBudget(PropsUtil.getDataPropertyValue("Budget_Loc_CreateBudgetIncomeAmount"),
				PropsUtil.getDataPropertyValue("Budget_Loc_CreateBudgetCategoryAmount"));
		SeleniumUtil.waitForPageToLoad();
		PageParser.forceNavigate("Budget", d);
		SeleniumUtil.waitForPageToLoad();

		PageParser.forceNavigate("Transaction", d);
		SeleniumUtil.waitForPageToLoad();

		Assert.assertEquals(trnxAcntFilter.AllAccType_tran().getText().trim(),
				PropsUtil.getDataPropertyValue("Stickyness_GroupPopupGroupName"),
				"Selected group is not displayed in Transaction Account Group");
	}

	@Test(description = "AT-104576:verify group sticky ness when group is selected from NW to Transaction finapp", dependsOnMethods = "verifyGroup_BudgetToTransaction", priority = 4)
	public void verifyGroup_NWtoTransaction() {
		PageParser.forceNavigate("NetWorth", d);
		SeleniumUtil.waitForPageToLoad();
		SeleniumUtil.click(netWorth.allAccountsDD());
		Assert.assertEquals(netWorth.allAccountsDD().getText().trim(),
				PropsUtil.getDataPropertyValue("Stickyness_GroupPopupGroupName"),
				"selected group is not displayed in NW");

		PageParser.forceNavigate("Transaction", d);
		SeleniumUtil.waitForPageToLoad();

		Assert.assertEquals(trnxAcntFilter.AllAccType_tran().getText().trim(),
				PropsUtil.getDataPropertyValue("Stickyness_GroupPopupGroupName"),
				"Selected group is not displayed in Transaction Account Group");

	}

	@Test(description = "AT-104577:verify group sticky ness when group is selected from IH to Transaction finapp", dependsOnMethods = "verifyGroup_NWtoTransaction", priority = 5)
	public void verifyGroup_IHtoTransaction() {
		PageParser.forceNavigate("InvestmentHoldings", d);
		SeleniumUtil.click(investmentHoldings.accountDropDown());

		Assert.assertEquals(investmentHoldings.accountDropDown().getText().trim(),
				PropsUtil.getDataPropertyValue("Stickyness_notValidAccount"), "selected group is not displayed in IH");

		PageParser.forceNavigate("Transaction", d);
		SeleniumUtil.waitForPageToLoad();

		Assert.assertEquals(trnxAcntFilter.AllAccType_tran().getText().trim(),
				PropsUtil.getDataPropertyValue("Stickyness_GroupPopupGroupName"),
				"Selected group is not displayed in Transaction Account Group");

	}

	@Test(description = "AT-104579:verify group sticky ness is not applied when an account is selected from Accounts Widget to Transaction finapp", dependsOnMethods = "verifyGroup_IHtoTransaction", priority = 6)

	public void verifyGroup_Dashboard_AccountstoTransaction() {

		PageParser.forceNavigate("DashboardPage", d);
		SeleniumUtil.waitForPageToLoad();
		SeleniumUtil.click(accountSharingLoc.accountName().get(0));
		SeleniumUtil.waitForPageToLoad();

		Assert.assertEquals(trnxAcntFilter.AllAccType_tran().getText().trim(),
				PropsUtil.getDataPropertyValue("Stickyness_GroupinAccounts"),
				"Selected group is not displayed in Transaction Account Group");
	}

	@Test(description = "AT-104578:verify group sticky ness is not applied and 'All Accounts' should be dispalyed in Transaction,when click on Transaction Widget in dashboard finapp", dependsOnMethods = "verifyGroup_Dashboard_AccountstoTransaction", priority = 7)
	public void Dashboard_TransactionWidget() {

		PageParser.forceNavigate("DashboardPage", d);
		SeleniumUtil.waitForPageToLoad();
		SeleniumUtil.click(TxnDBWidgetLoc.dashboardWidgetTxnDesc().get(0));
		SeleniumUtil.waitForPageToLoad();

		Assert.assertEquals(trnxAcntFilter.AllAccType_tran().getText().trim(),
				PropsUtil.getDataPropertyValue("Stickyness_notValidAccount"),
				"Selected group is not displayed in Transaction Account Group");

	}

}
