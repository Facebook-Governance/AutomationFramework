/**
* Copyright (c) 2019 Yodlee Inc. All Rights Reserved. 
*
* This software is the confidential and proprietary information of
* Yodlee, Inc. Use is subject to license terms.
*
* @author mallikan
*/

package com.omni.pfm.Integration;

import java.util.concurrent.TimeUnit;

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

public class Integration_EIA_CF_Budget_IH_NW_GroupFilterStickyNess extends TestBase {

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
		doInitialization("Account Group Stickyness");

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
		accountSharingLoc = new AccountSharing_Loc(d);
		LoginPage.loginMain(d, loginParameter);
		accountAddition.linkAccount();
		accountAddition.addAggregatedAccount(PropsUtil.getDataPropertyValue("Stickyness_LinkAccountName"),
				PropsUtil.getDataPropertyValue("Stickyness_LinkAccountPAssword"));
		d.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
	}

	@Test(description = "AT-101666:verify group sticky ness when group is selected from EA", priority = 1)
	public void selectGroup_EA() {
		PageParser.forceNavigate("AccountsPage", d);
		groupView.clikcCreatgroup();
		groupView.createGroup(PropsUtil.getDataPropertyValue("Stickyness_GroupPopupGroupName"),
				PropsUtil.getDataPropertyValue("Stickness_GroupPopupGroupAccount").split(","));
		groupView.createGroupMore(PropsUtil.getDataPropertyValue("Stickness_GroupNames").split(","),
				PropsUtil.getDataPropertyValue("Stickness_GroupNamesAccounts").split(","));
		expenseTrend.navigateToEA();
		expenseTrend.FTUE();
		expenseTrend.clickGroup(PropsUtil.getDataPropertyValue("Stickyness_GroupPopupGroupName"));
		if (expenseTrend.isExpense_done_mob_Present()) {
			SeleniumUtil.click(expenseTrend.Expense_done_mob());
		}
		Assert.assertEquals(expenseTrend.EIAccountDropDown().getText().trim(),
				PropsUtil.getDataPropertyValue("Stickyness_GroupPopupGroupName"), "selected group is not displayed");
	}

	@Test(description = "AT-101666:verify group sticky ness in IA when group is selected from EA", priority = 2, dependsOnMethods = "selectGroup_EA")
	public void verifyGroup_EAtoIA() {
		if (expenseTrend.isExpense_done_mob_Present()) {
			SeleniumUtil.click(expenseTrend.Expense_done_mob());
		}
		expenseTrend.clickIncome();
		Assert.assertEquals(expenseTrend.EIAccountDropDown().getText().trim(),
				PropsUtil.getDataPropertyValue("Stickyness_GroupPopupGroupName"),
				"selected group is not displayed in IA");
	}

	@Test(description = "AT-101658:verify group sticky ness in CF when group is selected from EA", priority = 3, dependsOnMethods = "selectGroup_EA")
	public void verifyGroup_EAtoCF() {
		PageParser.forceNavigate("CashFlow", d);
		cashFlowLandingPage.FTUE();
		Assert.assertEquals(cashFlowLandingPage.CFAccountDropDown().getText().trim(),
				PropsUtil.getDataPropertyValue("Stickyness_GroupPopupGroupName"),
				"selected group is not displayed in CF");
	}

	@Test(description = "AT-101658:verify group sticky ness in Budget when group is selected from EA", priority = 4, dependsOnMethods = "selectGroup_EA")
	public void verifyGroup_EAtoBudget() {
		PageParser.forceNavigate("Budget", d);
		createBudget.creatBudget(PropsUtil.getDataPropertyValue("Budget_Loc_CreateBudgetIncomeAmount"),
				PropsUtil.getDataPropertyValue("Budget_Loc_CreateBudgetCategoryAmount"));

		budgetSummery.clickcreateBudget();
		budgetSummery.SelectAccountGroup(PropsUtil.getDataPropertyValue("Stickyness_GroupPopupGroupName"));
		createBudget.creatAccountGroupBudget(PropsUtil.getDataPropertyValue("Budget_Loc_CreateBudgetIncomeAmount"),
				PropsUtil.getDataPropertyValue("Budget_Loc_CreateBudgetCategoryAmount"));
		PageParser.forceNavigate("Budget", d);
		Assert.assertEquals(budgetSummery.BudgetSummery_MultiBudgetGroupDropDown().getAttribute("value").trim(),
				PropsUtil.getDataPropertyValue("Stickyness_GroupPopupGroupName"),
				"Selected group is not displayed Budget");
	}

	@Test(description = "AT-101656:verify group sticky ness in NW when group is selected from EA", priority = 5, dependsOnMethods = "selectGroup_EA")
	public void verifyGroup_EAtoNW() {
		PageParser.forceNavigate("NetWorth", d);
		netWorth.completeFTUEFlow();
		Assert.assertEquals(netWorth.allAccountsDD().getText().trim(),
				PropsUtil.getDataPropertyValue("Stickyness_GroupPopupGroupName"),
				"selected group is not displayed in NW");
	}

	@Test(description = "AT-101670,AT-101658:verify group sticky ness is not applied for in valid account in IH when group is selected from EA", priority = 6, dependsOnMethods = "selectGroup_EA")
	public void verifyGroup_EAtoIH() {
		PageParser.forceNavigate("InvestmentHoldings", d);
		SeleniumUtil.click(investmentHoldings.accountDropDown());
		Assert.assertEquals(investmentHoldings.accountDropDown().getText().trim(),
				PropsUtil.getDataPropertyValue("Stickyness_notValidAccount"), "selected group is not displayed in CF");
	}

	@Test(description = "AT-101657:verify group sticky ness when group is selected from EA", priority = 7, dependsOnMethods = "selectGroup_EA")
	public void verifyGroup_ToEA() {
		expenseTrend.navigateToEA();
		Assert.assertEquals(expenseTrend.EIAccountDropDown().getText().trim(),
				PropsUtil.getDataPropertyValue("Stickyness_GroupPopupGroupName"),
				"selected group is not displayed in EA account drop down");
	}

	@Test(description = "AT-101669:verify group sticky ness when group is selected from Budget", priority = 8, dependsOnMethods = "verifyGroup_EAtoBudget")
	public void selectedGroup_Budget() {
		PageParser.forceNavigate("Budget", d);
		SeleniumUtil.click(budgetSummery.BudgetSummery_MultiBudgetGroupDropDown());
		budgetSummery.selectBudgetGroup(PropsUtil.getDataPropertyValue("StickeyNessBudget_BudgetGroup"));
		Assert.assertEquals(budgetSummery.BudgetSummery_MultiBudgetGroupDropDown().getAttribute("value").trim(),
				PropsUtil.getDataPropertyValue("StickeyNessBudget_BudgetGroup"),
				"Selected group is not displayed in budget group dropdown");
	}

	@Test(description = "AT-101657:verify group sticky ness in EAwhen group is selected from Budget", priority = 9, dependsOnMethods = "selectedGroup_Budget")
	public void verifyGroup_BudgetToEA() {
		expenseTrend.navigateToEA();
		Assert.assertEquals(expenseTrend.EIAccountDropDown().getText().trim(),
				PropsUtil.getDataPropertyValue("StickeyNessBudget_BudgetGroup"),
				"Selected group is not dislayed EA account dropdown");
	}

	@Test(description = "AT-101662:verify group sticky ness in IA when group is selected from Budget", priority = 10, dependsOnMethods = "verifyGroup_BudgetToEA")
	public void verifyGroup_BudgetToIA() {
		expenseTrend.clickIncome();
		Assert.assertEquals(expenseTrend.EIAccountDropDown().getText().trim(),
				PropsUtil.getDataPropertyValue("StickeyNessBudget_BudgetGroup"),
				"Selected group is not displayed in IA account dropdown");

	}

	@Test(description = "AT-101665:verify group sticky ness in CF when group is selected from Budget", priority = 11, dependsOnMethods = "selectedGroup_Budget")
	public void verifyGroup_BudgetToCashFlow() {
		PageParser.forceNavigate("CashFlow", d);
		Assert.assertEquals(cashFlowLandingPage.CFAccountDropDown().getText().trim(),
				PropsUtil.getDataPropertyValue("StickeyNessBudget_BudgetGroup"),
				"Selected group is not displayed in CashFlow");

	}

	@Test(description = "AT-101659:verify group sticky ness in NW when group is selected from Budget", priority = 12, dependsOnMethods = "selectedGroup_Budget")
	public void verifyGroup_BudgetToNW() {
		PageParser.forceNavigate("NetWorth", d);
		SeleniumUtil.click(netWorth.allAccountsDD());
		Assert.assertEquals(netWorth.allAccountsDD().getText().trim(),
				PropsUtil.getDataPropertyValue("StickeyNessBudget_BudgetGroup"),
				"selected group is not displayed in NW");
	}

	@Test(description = "AT-101660:verify group sticky ness in IH when group is selected from Budget", priority = 13, dependsOnMethods = "selectedGroup_Budget")
	public void verifyGroup_BudgetIH() {
		PageParser.forceNavigate("InvestmentHoldings", d);
		SeleniumUtil.click(investmentHoldings.accountDropDown());
		Assert.assertEquals(investmentHoldings.accountDropDown().getText().trim(),
				PropsUtil.getDataPropertyValue("Stickyness_notValidAccount"), "selected group is  displayed in IH");
	}

	@Test(description = "AT-101660:verify group sticky ness when group is selected from Budget", priority = 14, dependsOnMethods = "selectedGroup_Budget")
	public void verifyGroup_ToBudget() {
		PageParser.forceNavigate("Budget", d);
		Assert.assertEquals(budgetSummery.BudgetSummery_MultiBudgetGroupDropDown().getAttribute("value").trim(),
				PropsUtil.getDataPropertyValue("StickeyNessBudget_BudgetGroup"),
				"Selected group is not displayed in budget group dropdown");
	}

	@Test(description = "AT-101661:verify group sticky ness when group is selected from IA", priority = 15, dependsOnMethods = "verifyGroup_EAtoBudget")
	public void selectGroup_IA() {
		expenseTrend.navigateToEA();
		expenseTrend.clickIncome();
		expenseTrend.clickGroup(PropsUtil.getDataPropertyValue("Budget_GroupPopupGroupName"));
		if (expenseTrend.isExpense_done_mob_Present()) {
			SeleniumUtil.click(expenseTrend.Expense_done_mob());
		}
		Assert.assertEquals(expenseTrend.EIAccountDropDown().getText().trim(),
				PropsUtil.getDataPropertyValue("Budget_GroupPopupGroupName"),
				"Selected group is not displayed in IA account dropdown");
	}

	@Test(description = "AT-101662:verify group sticky ness in EA when group is selected from IA", priority = 16, dependsOnMethods = "selectGroup_IA")
	public void verifyGroup_IAToEA() {
		expenseTrend.navigateToEA();
		Assert.assertEquals(expenseTrend.EIAccountDropDown().getText().trim(),
				PropsUtil.getDataPropertyValue("Budget_GroupPopupGroupName"),
				"selected group is not displayed in EA account dropdown");
	}

	@Test(description = "AT-101665:verify group sticky ness in CF when group is selected from IA", priority = 17, dependsOnMethods = "selectGroup_IA")
	public void verifyGroup_IAToCashFlow() {
		PageParser.forceNavigate("CashFlow", d);
		Assert.assertEquals(cashFlowLandingPage.CFAccountDropDown().getText().trim(),
				PropsUtil.getDataPropertyValue("Budget_GroupPopupGroupName"),
				"Selected group is not Displayed in  cashflow account dropdown");
	}

	@Test(description = "AT-101657:verify group sticky ness in Budget when group is selected from IA", priority = 18, dependsOnMethods = "selectGroup_IA")
	public void verifyGroup_IAtoBudget() {
		PageParser.forceNavigate("Budget", d);
		Assert.assertEquals(budgetSummery.BudgetSummery_MultiBudgetGroupDropDown().getAttribute("value").trim(),
				PropsUtil.getDataPropertyValue("Budget_GroupPopupGroupName"),
				"selected group is not displayed in budget group dropdown");
	}

	@Test(description = "AT-101661:verify group sticky ness in NW when group is selected from IA", priority = 19, dependsOnMethods = "selectGroup_IA")
	public void verifyGroup_IAtoNW() {
		PageParser.forceNavigate("NetWorth", d);
		Assert.assertEquals(netWorth.allAccountsDD().getText().trim(),
				PropsUtil.getDataPropertyValue("Budget_GroupPopupGroupName"), "selected group is not displayed in NW");
	}

	@Test(description = "AT-101662:verify group sticky ness in IH when group is selected from IA", priority = 20, dependsOnMethods = "selectGroup_IA")
	public void verifyGroup_IAtoIH() {
		PageParser.forceNavigate("InvestmentHoldings", d);
		Assert.assertEquals(investmentHoldings.accountDropDown().getText().trim(),
				PropsUtil.getDataPropertyValue("Stickyness_notValidAccount"), "selected group is not displayed in IH");
	}

	@Test(description = "AT-101658:verify group sticky ness when group is selected from EA", priority = 21, dependsOnMethods = "selectGroup_EA")
	public void select_AllAccount_Group_EA() {

		PageParser.forceNavigate("CashFlow", d);
		cashFlowLandingPage.clickaccountDropDown();
		SeleniumUtil.click(cashFlowLandingPage.CFAllAccountCheckBox());
		Assert.assertEquals(cashFlowLandingPage.CFAccountDropDown().getText().trim(),
				PropsUtil.getDataPropertyValue("Stickyness_AllCashFlowAccounts"),
				"selected group is not dispalyed in cashflow account dropdown");
		PageParser.forceNavigate("NetWorth", d);
		Assert.assertEquals(netWorth.allAccountsDD().getText().trim(),
				PropsUtil.getDataPropertyValue("Stickyness_AllAccounts_NW"), "selected group is not displayed in NW");
	}

	@Test(description = "AT-101665:verify group sticky ness  when group is selected from CF", priority = 22, dependsOnMethods = "verifyGroup_EAtoBudget")
	public void selectGroup_CashFlow() {
		PageParser.forceNavigate("CashFlow", d);
		cashFlowLandingPage.clickaccountDropDown();
		cashFlowLandingPage.clickCFGroup(PropsUtil.getDataPropertyValue("Stickness_validAccountGroupName"));
		SeleniumUtil.waitForPageToLoad();
		Assert.assertEquals(cashFlowLandingPage.CFAccountDropDown().getText().trim(),
				PropsUtil.getDataPropertyValue("Stickness_validAccountGroupName"),
				"selected group is not dispalyed in cashflow account dropdown");
	}

	@Test(description = "AT-101665:verify group sticky ness  when group is selected from CF", priority = 23, dependsOnMethods = "selectGroup_CashFlow")
	public void verifyGroup_CFToEA() {
		expenseTrend.navigateToEA();
		SeleniumUtil.waitForPageToLoad();
		Assert.assertEquals(expenseTrend.EIAccountDropDown().getText(),
				PropsUtil.getDataPropertyValue("Stickness_validAccountGroupName"),
				"selected group is not displayed in EA");
	}

	@Test(description = "AT-101665:verify group sticky ness in EA when group is selected from CF", priority = 24, dependsOnMethods = "selectGroup_CashFlow")
	public void verifyGroup_CFToIA() {
		expenseTrend.clickIncome();
		Assert.assertEquals(expenseTrend.EIAccountDropDown().getText().trim(),
				PropsUtil.getDataPropertyValue("Stickness_validAccountGroupName"),
				"Selected group is not displayed in IA");
	}

	@Test(description = "AT-101665:verify group sticky ness in Budget when group is selected from CF", priority = 25, dependsOnMethods = "selectGroup_CashFlow")
	public void verifyGroup_CFtoBudget() {
		PageParser.forceNavigate("Budget", d);
		Assert.assertEquals(budgetSummery.BudgetSummery_MultiBudgetGroupDropDown().getAttribute("value").trim(),
				PropsUtil.getDataPropertyValue("Stickyness_GroupPopupGroupName"),
				"Selected group is not displayed in budget");
	}

	@Test(description = "AT-101663:verify group sticky ness in NW when group is selected from CF", priority = 26, dependsOnMethods = "selectGroup_CashFlow")
	public void verifyGroup_CFtoNW() {
		PageParser.forceNavigate("NetWorth", d);
		Assert.assertEquals(netWorth.allAccountsDD().getText().trim(),
				PropsUtil.getDataPropertyValue("Stickness_validAccountGroupName"),
				"selected group is not displayed in NW");

	}

	@Test(description = "AT-101664:verify group sticky ness in IH when group is selected from CF", priority = 27, dependsOnMethods = "selectGroup_CashFlow")
	public void verifyGroup_CFtoIH() {
		PageParser.forceNavigate("InvestmentHoldings", d);
		SeleniumUtil.click(investmentHoldings.accountDropDown());
		Assert.assertEquals(investmentHoldings.accountDropDown().getText().trim(),
				PropsUtil.getDataPropertyValue("Stickness_validAccountGroupName"), "selected group is displayed in IH");
	}

	@Test(description = "AT-101665:verify group sticky ness is not applied CF when group is selected from Dashboard widget", priority = 28, dependsOnMethods = "selectGroup_CashFlow")
	public void selectGroup_DasboardWidgettoCF() {
		PageParser.forceNavigate("DashboardPage", d);
		SeleniumUtil.click(DashCF.CashFlowWidgetCard());
		Assert.assertEquals(LandingPage.AllCashFlowAcc().getText(), PropsUtil.getDataPropertyValue("AllCashFlowAcc"));
	}

	@Test(description = "AT-101666:verify group sticky ness is not applied EA when group is selected from Dashboard widget", priority = 29, dependsOnMethods = "selectGroup_DasboardWidgettoCF")
	public void selectGroup_DasboardWidgettoEA() {
		PageParser.forceNavigate("DashboardPage", d);
		SeleniumUtil.click(spending.EIAspendingWidgetCard());
		SeleniumUtil.waitForPageToLoad();
		Assert.assertEquals(expenseTrend.EIAccountDropDown().getText(),
				PropsUtil.getDataPropertyValue("StiackyNess_AllEAAccountLable"),
				"selected group is not displayed in EA");
	}

	@Test(description = "AT-101667:verify group sticky ness is not applied NW when group is selected from Dashboard widget", priority = 30, dependsOnMethods = "selectGroup_DasboardWidgettoEA")
	public void selectGroup_DasboardWidgettoNW() {
		PageParser.forceNavigate("DashboardPage", d);
		SeleniumUtil.click(Networth.NetworthCard());
		SeleniumUtil.waitForPageToLoad();
		Assert.assertEquals(netWorth.allAccountsDD().getText().trim(),
				PropsUtil.getDataPropertyValue("NWAllAccountHeaderDropdown"), "selected group is not displayed in NW");
	}

	@Test(description = "AT-101668:verify group sticky ness is not applied in IH when group is selected from Dashboard widget", priority = 31, dependsOnMethods = "selectGroup_DasboardWidgettoNW")
	public void selectGroup_DasboardWidgettoIH() {
		PageParser.forceNavigate("DashboardPage", d);
		SeleniumUtil.click(dashBoardInvestment.InvestmentCard());
		Assert.assertEquals(investmentHoldings.accountDropDown().getText().trim(),
				PropsUtil.getDataPropertyValue("IH_AccDropdown"), "selected group is not displayed in IH");
	}

	@Test(description = "AT-101669:verify group sticky ness is applied in Budget when group is selected from Dashboard widget", priority = 32, dependsOnMethods = "selectGroup_DasboardWidgettoIH")
	public void verifyGroup_DasboardBudgetWidgettoBudgetFinapp() {
		PageParser.forceNavigate("DashboardPage", d);
		for (int i = 0; i < budget.DashboardBudgetGroup().size(); i++) {
			String actual = budget.DashboardBudgetGroup().get(i).getText().trim();
			if (actual.equals(PropsUtil.getDataPropertyValue("BudgetGroup"))) {
				SeleniumUtil.click(budget.DashboardBudgetWidget());
				Assert.assertEquals(budgetSummery.BudgetSummery_MultiBudgetGroupDropDown().getText().trim(), actual,
						"selected group is not displayed in Budget");
			}
		}
	}

	@Test(description = "AT-104573: verify group sticky ness when group is selected from EA to Transaction finapp", dependsOnMethods = "selectGroup_DasboardWidgettoIH", priority = 33)
	public void selectGroup_EAToTransaction() {
		expenseTrend.navigateToEA();
		expenseTrend.clickEIAccountDropDown();
		expenseTrend.clickGroup(PropsUtil.getDataPropertyValue("Stickyness_GroupPopupGroupName"));
		SeleniumUtil.waitForPageToLoad();
		if (expenseTrend.isExpense_done_mob_Present()) {

			SeleniumUtil.click(expenseTrend.Expense_done_mob());
		}
		Assert.assertEquals(expenseTrend.EIAccountDropDown().getText().trim(),
				PropsUtil.getDataPropertyValue("Stickyness_GroupPopupGroupName"),
				"Selected group is not dislayed EA account dropdown");

		PageParser.forceNavigate("Transaction", d);
		if (expenseTrend.isExpense_done_mob_Present()) {
			SeleniumUtil.click(expenseTrend.Expense_done_mob());
		}
		if (trnxAcntFilter.isMobile_Trans_filterbtn_Present()) {

			SeleniumUtil.click(trnxAcntFilter.Trans_filterbtn_mob());
		}
		SeleniumUtil.waitForPageToLoad();
		System.out.println("Transaction page:" + trnxAcntFilter.AllAccType_tran().getText());

		Assert.assertEquals(trnxAcntFilter.AllAccType_tran().getText().trim(),
				PropsUtil.getDataPropertyValue("Stickyness_GroupPopupGroupName"), "selected group is not displayed");
	}

	@Test(description = "AT-104574:verify group sticky ness when group is selected from CF to Transaction finapp", dependsOnMethods = "selectGroup_EAToTransaction", priority = 34)
	public void verifyGroup_CFtoTransaction() {
		PageParser.forceNavigate("CashFlow", d);
		Assert.assertEquals(cashFlowLandingPage.CFAccountDropDown().getText().trim(),
				PropsUtil.getDataPropertyValue("Stickyness_GroupPopupGroupName"),
				"selected group is not dispalyed in cashflow account dropdown");

		PageParser.forceNavigate("Transaction", d);
		if (trnxAcntFilter.isMobile_Trans_filterbtn_Present()) {

			SeleniumUtil.click(trnxAcntFilter.Trans_filterbtn_mob());
		}
		SeleniumUtil.waitForPageToLoad();

		Assert.assertEquals(trnxAcntFilter.AllAccType_tran().getText().trim(),
				PropsUtil.getDataPropertyValue("Stickyness_GroupPopupGroupName"),
				"selected group is not displayed in Transaction Account Group");

	}

	@Test(description = "AT-104575:verify group sticky ness when group is selected from Budget to Transaction finapp", dependsOnMethods = "selectGroup_DasboardWidgettoIH", priority = 35)
	public void verifyGroup_BudgetToTransaction() {
		PageParser.forceNavigate("Budget", d);
		SeleniumUtil.click(budgetSummery.BudgetSummery_MultiBudgetGroupDropDown());
		SeleniumUtil.waitForPageToLoad();
		budgetSummery.selectBudgetGroup(PropsUtil.getDataPropertyValue("StickeyNessBudget_BudgetGroup"));
		PageParser.forceNavigate("Transaction", d);
		if (trnxAcntFilter.isMobile_Trans_filterbtn_Present()) {
			SeleniumUtil.click(trnxAcntFilter.Trans_filterbtn_mob());
		}
		SeleniumUtil.waitForPageToLoad();
		Assert.assertEquals(trnxAcntFilter.AllAccType_tran().getText().trim(),
				PropsUtil.getDataPropertyValue("StickeyNessBudget_BudgetGroup"),
				"Selected group is not displayed in Transaction Account Group");
	}

	@Test(description = "AT-104576:verify group sticky ness when group is selected from NW to Transaction finapp", dependsOnMethods = "verifyGroup_BudgetToTransaction", priority = 36)
	public void verifyGroup_NWtoTransaction() {
		PageParser.forceNavigate("NetWorth", d);
		Assert.assertEquals(netWorth.allAccountsDD().getText().trim(),
				PropsUtil.getDataPropertyValue("StickeyNessBudget_BudgetGroup"),
				"selected group is not displayed in NW");
		PageParser.forceNavigate("Transaction", d);
		if (trnxAcntFilter.isMobile_Trans_filterbtn_Present()) {

			SeleniumUtil.click(trnxAcntFilter.Trans_filterbtn_mob());
			SeleniumUtil.waitForPageToLoad();
		}
		Assert.assertEquals(trnxAcntFilter.AllAccType_tran().getText().trim(),
				PropsUtil.getDataPropertyValue("StickeyNessBudget_BudgetGroup"),
				"Selected group is not displayed in Transaction Account Group");

	}

	@Test(description = "AT-104577:verify group sticky ness when group is selected from IH to Transaction finapp", dependsOnMethods = "verifyGroup_NWtoTransaction", priority = 37)
	public void verifyGroup_TransactiontoIH() {
		PageParser.forceNavigate("Transaction", d);
		SeleniumUtil.click(trnxAcntFilter.accountFilterLink());
		SeleniumUtil.click(trnxAcntFilter.allAccountCheckBox());
		SeleniumUtil.waitForPageToLoad();
		SeleniumUtil.click(trnxAcntFilter.AllAccType_closeBtn());
		if (trnxAcntFilter.isMobile_Trans_filterbtn_Present()) {

			SeleniumUtil.click(trnxAcntFilter.Trans_filterbtn_mob());
			SeleniumUtil.waitForPageToLoad();
		}
		Assert.assertEquals(trnxAcntFilter.AllAccType_tran().getText().trim(),
				PropsUtil.getDataPropertyValue("Stickyness_notValidAccount"),
				"Selected group is  displayed in Transaction Account Group");

		PageParser.forceNavigate("InvestmentHoldings", d);
		investmentHoldings.clickIHccountDropDown();
		SeleniumUtil.waitForPageToLoad();
		if (investmentHoldings.isMobile_GroupLink_Present()) {
			SeleniumUtil.click(investmentHoldings.IHGroupLink());
		}
		investmentHoldings.clickGroup(PropsUtil.getDataPropertyValue("Stickyness_notValidAccount"));
		Assert.assertEquals(investmentHoldings.accountDropDown().getText().trim(),
				PropsUtil.getDataPropertyValue("Stickyness_notValidAccount"), "selected group is not displayed in IH");

	}

	@Test(description = "AT-104579:verify group sticky ness is not applied when an account is selected from Accounts Widget to Transaction finapp", dependsOnMethods = "selectGroup_EAToTransaction", priority = 38)

	public void verifyGroup_Dashboard_AccountstoTransaction() {

		PageParser.forceNavigate("DashboardPage", d);
		if (accountSharingLoc.isDash_ExpandAll_mob_Present()) {
			SeleniumUtil.click(accountSharingLoc.Dashboard_ExpandAll());
			SeleniumUtil.waitForPageToLoad();
		}
		SeleniumUtil.click(accountSharingLoc.accountName().get(0));
		SeleniumUtil.waitForPageToLoad();
		if (accountSharingLoc.isDash_AccountName_Mob_Present()) {
			Assert.assertTrue(accountSharingLoc.Dashboard_AccountName_mob().getText()
					.contains(PropsUtil.getDataPropertyValue("Mob_Stickyness_GroupinAccounts")));
		} else
			Assert.assertEquals(trnxAcntFilter.AllAccType_tran().getText().trim(),
					PropsUtil.getDataPropertyValue("Stickyness_GroupinAccounts"),
					"Selected group is not displayed in Transaction Account Group");

	}

	@Test(description = "AT-104578:verify group sticky ness is not applied and 'All Accounts' should be dispalyed in Transaction,when click on Transaction Widget in dashboard finapp", dependsOnMethods = "selectGroup_EAToTransaction", priority = 39)
	public void Dashboard_TransactionWidget() {

		PageParser.forceNavigate("DashboardPage", d);
		SeleniumUtil.waitForPageToLoad();
		SeleniumUtil.click(TxnDBWidgetLoc.dashboardWidgetTxnDesc().get(0));
		SeleniumUtil.waitForPageToLoad();
		if (trnxAcntFilter.isMobile_Trans_filterbtn_Present()) {

			SeleniumUtil.click(trnxAcntFilter.Trans_filterbtn_mob());
			SeleniumUtil.waitForPageToLoad();
		}
		Assert.assertEquals(trnxAcntFilter.AllAccType_tran().getText().trim(),
				PropsUtil.getDataPropertyValue("Stickyness_notValidAccount"),
				"Selected group is not displayed in Transaction Account Group");

	}

	@Test(description = "AT-104578:verify group sticky ness is not applied and 'All Accounts' should be dispalyed in Transaction,when click on Transaction Widget in dashboard finapp", dependsOnMethods = "selectGroup_EAToTransaction", priority = 40)
	public void Transaction_Group_Stickiness() {

		PageParser.forceNavigate("Transaction", d);
		SeleniumUtil.waitForPageToLoad();
		if (trnxAcntFilter.isMobile_Trans_filterbtn_Present()) {

			SeleniumUtil.click(trnxAcntFilter.Trans_filterbtn_mob());
			SeleniumUtil.waitForPageToLoad();
		}
		if (trnxAcntFilter.isMobile_Trans_ClickonGroup_Present()) {

			SeleniumUtil.click(trnxAcntFilter.Trasn_clickonGroup_mob());
		}
		trnxAcntFilter.clickGroup(PropsUtil.getDataPropertyValue("Stickness_validAccountGroupName"));
		Assert.assertEquals(trnxAcntFilter.AllAccType_tran().getText().trim(),
				PropsUtil.getDataPropertyValue("Stickness_validAccountGroupName"),
				"Selected group is not displayed in Transaction Account Group");
		expenseTrend.navigateToEA();
		SeleniumUtil.waitForPageToLoad();
		Assert.assertEquals(expenseTrend.EIAccountDropDown().getText(),
				PropsUtil.getDataPropertyValue("Stickness_validAccountGroupName"),
				"selected group is not displayed in EA");
	}
}
