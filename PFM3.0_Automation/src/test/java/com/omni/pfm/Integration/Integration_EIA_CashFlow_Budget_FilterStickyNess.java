/**
* Copyright (c) 2019 Yodlee Inc. All Rights Reserved. 
*
* This software is the confidential and proprietary information of
* Yodlee, Inc. Use is subject to license terms.
*
* @author sbhat1
*/
package com.omni.pfm.Integration;

import java.awt.AWTException;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.omni.pfm.Accounts.Accounts_DeleteAccount_Loc;
import com.omni.pfm.Accounts.Accounts_ViewByGroup_Loc;
import com.omni.pfm.PageProcessor.PageParser;
import com.omni.pfm.pages.AccountAddition.AccountAddition;
import com.omni.pfm.pages.AccountSettings.AccountGroup_Settings_Loc;
import com.omni.pfm.pages.BudgetEnhancement.Budget_Income_And_Bill_Summery_Loc;
import com.omni.pfm.pages.BudgetEnhancement.Create_Budget_Loc;
import com.omni.pfm.pages.CashFlow.CashFlow_LandingPage_Loc;
import com.omni.pfm.pages.Expense_IncomeAnalysis.ExpLandingPage_Loc;
import com.omni.pfm.pages.Expense_IncomeAnalysis.Expense_Income_Trend_Loc;
import com.omni.pfm.pages.Login.LoginPage;
import com.omni.pfm.pages.TransactionEnhancement1.Add_Manual_Transaction_Loc;
import com.omni.pfm.pages.TransactionEnhancement1.Transaction_AccountDropDown_Loc;
import com.omni.pfm.pages.TransactionEnhancement1.Transaction_Filter_Loc;
import com.omni.pfm.testBase.TestBase;
import com.omni.pfm.utility.PropsUtil;
import com.omni.pfm.utility.SeleniumUtil;

/**
 * @author renukak
 *
 */
public class Integration_EIA_CashFlow_Budget_FilterStickyNess extends TestBase{
	ExpLandingPage_Loc expLandingPage;
	Transaction_Filter_Loc filter;
	AccountAddition 		accountAddition;
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
	@BeforeClass(alwaysRun=true)

	public void init() throws Exception
	{
		doInitialization("Expense Analysis");

		expLandingPage = new ExpLandingPage_Loc(d);

		accountAddition = new AccountAddition();
		expenseTrend=new  Expense_Income_Trend_Loc(d);
		util=new SeleniumUtil();
		txnAcct=new Transaction_AccountDropDown_Loc(d);
		add_Manual=new Add_Manual_Transaction_Loc(d);
		groupView=new Accounts_ViewByGroup_Loc(d);
		filter=new Transaction_Filter_Loc(d);
		createBudget=new Create_Budget_Loc(d);
		budgetSummery=new Budget_Income_And_Bill_Summery_Loc(d);
		cashFlowLandingPage=new CashFlow_LandingPage_Loc(d);
		accountGroupSetting=new AccountGroup_Settings_Loc(d);
		deleetAccount=new Accounts_DeleteAccount_Loc(d);
		LoginPage.loginMain(d, loginParameter);
		SeleniumUtil.waitForPageToLoad();
		accountAddition.linkAccount();
		accountAddition.addAggregatedAccount(PropsUtil.getDataPropertyValue("Stickyness_LinkAccountName"), PropsUtil.getDataPropertyValue("Stickyness_LinkAccountPAssword"));
	
		
	}
	
	@Test(description="AT-96788,AT-96796,AT-96802:verify group sticky ness when group is selected from EA",priority=1)
	public void selectGroup_EA()
	{
		PageParser.forceNavigate("AccountsPage", d);
		SeleniumUtil.waitForPageToLoad();
		groupView.clikcCreatgroup();
		groupView.createGroup(PropsUtil.getDataPropertyValue("Stickyness_GroupPopupGroupName"), PropsUtil.getDataPropertyValue("Stickness_GroupPopupGroupAccount").split(","));
		groupView.createGroupMore(PropsUtil.getDataPropertyValue("Stickness_GroupNames").split(","), 
				PropsUtil.getDataPropertyValue("Stickness_GroupNamesAccounts").split(","));
		SeleniumUtil.waitForPageToLoad();
		expenseTrend.navigateToEA();
		expenseTrend.FTUE();
		expenseTrend.clickEIAccountDropDown();
		expenseTrend.clickGroup(PropsUtil.getDataPropertyValue("Stickyness_GroupPopupGroupName"));
		Assert.assertEquals(expenseTrend.EIAccountDropDown().getText().trim(), PropsUtil.getDataPropertyValue("Stickyness_GroupPopupGroupName"), "selected group is not displayed");
	}
	@Test(description="AT-96788:verify group sticky ness in IA when group is selected from EA",priority=2,dependsOnMethods="selectGroup_EA")
	public void verifyGroup_EAtoIA()
	{
		expenseTrend.clickIncome();
		Assert.assertEquals(expenseTrend.EIAccountDropDown().getText().trim(), PropsUtil.getDataPropertyValue("Stickyness_GroupPopupGroupName"), "selected group is not displayed in IA");

	}
	@Test(description="AT-96788,AT-96789:verify group sticky ness in CF when group is selected from EA",priority=3,dependsOnMethods="selectGroup_EA")
	public void verifyGroup_EAtoCF()
	{
		PageParser.forceNavigate("CashFlow", d);
		SeleniumUtil.waitForPageToLoad();
		cashFlowLandingPage.FTUE();
		Assert.assertEquals(cashFlowLandingPage.CFAccountDropDown().getText().trim(), PropsUtil.getDataPropertyValue("Stickyness_GroupPopupGroupName"), "selected group is not displayed in CF");

	}
	@Test(description="AT-96788:verify group sticky ness in Budget when group is selected from EA",priority=4,dependsOnMethods="selectGroup_EA")
	public void verifyGroup_EAtoBudget()
	{
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
		    Assert.assertEquals(budgetSummery.BudgetSummery_MultiBudgetGroupDropDown().getAttribute("value").trim(),
		    		PropsUtil.getDataPropertyValue("Stickyness_GroupPopupGroupName"), "Selected group is not displayed Budget");
}
	@Test(description="AT-96788:verify group sticky ness when group is selected from EA",priority=5,dependsOnMethods="selectGroup_EA")
	public void verifyGroup_ToEA()
	{
		expenseTrend.navigateToEA();
	
		Assert.assertEquals(expenseTrend.EIAccountDropDown().getText().trim(), PropsUtil.getDataPropertyValue("Stickyness_GroupPopupGroupName"), "selected group is not displayed in EA account drop down");

	}
	@Test(description="AT-96786:verify group sticky ness when group is selected from Budget",priority=6,dependsOnMethods="verifyGroup_EAtoBudget")
	public void selectedGroup_Budget()
	{
		    PageParser.forceNavigate("Budget", d);
			SeleniumUtil.waitForPageToLoad();
		   SeleniumUtil.click(budgetSummery.BudgetSummery_MultiBudgetGroupDropDown());
		   budgetSummery.selectBudgetGroup(PropsUtil.getDataPropertyValue("StickeyNessBudget_BudgetGroup"));
		   Assert.assertEquals(budgetSummery.BudgetSummery_MultiBudgetGroupDropDown().getAttribute("value").trim(), 
				   PropsUtil.getDataPropertyValue("StickeyNessBudget_BudgetGroup"), "Selected group is not displayed in budget group dropdown");

	}
	
	@Test(description="AT-96786:verify group sticky ness in EAwhen group is selected from Budget",priority=7,dependsOnMethods="selectedGroup_Budget")
	public void verifyGroup_BudgetToEA()
	{
		expenseTrend.navigateToEA();
		
		Assert.assertEquals(expenseTrend.EIAccountDropDown().getText().trim(), PropsUtil.getDataPropertyValue("StickeyNessBudget_BudgetGroup"),
				"Selected group is not dislayed EA account dropdown");

	}
	@Test(description="AT-96786:verify group sticky ness in IA when group is selected from Budget",priority=8,dependsOnMethods="verifyGroup_BudgetToEA")
	public void verifyGroup_BudgetToIA()
	{
		expenseTrend.clickIncome();
		Assert.assertEquals(expenseTrend.EIAccountDropDown().getText().trim(), PropsUtil.getDataPropertyValue("StickeyNessBudget_BudgetGroup"),
				"Selected group is not displayed in IA account dropdown");

	}
	@Test(description="AT-96786,AT-96787:verify group sticky ness in CF when group is selected from Budget",priority=9,dependsOnMethods="selectedGroup_Budget")
	public void verifyGroup_BudgetToCashFlow()
	{
		PageParser.forceNavigate("CashFlow", d);
		SeleniumUtil.waitForPageToLoad();
		Assert.assertEquals(cashFlowLandingPage.CFAccountDropDown().getText().trim(), 
				PropsUtil.getDataPropertyValue("StickeyNessBudget_BudgetGroup"), "Selected group is not displayed in CashFlow");

	}
	@Test(description="AT-96786:verify group sticky ness when group is selected from Budget",priority=10,dependsOnMethods="selectedGroup_Budget")
	public void verifyGroup_ToBudget()
	{
		    PageParser.forceNavigate("Budget", d);
			SeleniumUtil.waitForPageToLoad();
		   
		    Assert.assertEquals(budgetSummery.BudgetSummery_MultiBudgetGroupDropDown().getAttribute("value").trim(), 
		    		PropsUtil.getDataPropertyValue("StickeyNessBudget_BudgetGroup"), "Selected group is not displayed in budget group dropdown");

	}
	@Test(description="AT-96788,AT-96796,AT-96802:verify group sticky ness when group is selected from IA",priority=11,dependsOnMethods="verifyGroup_EAtoBudget")
	public void selectGroup_IA()
	{
		expenseTrend.navigateToEA();
		expenseTrend.clickIncome();
		expenseTrend.clickEIAccountDropDown();
		expenseTrend.clickGroup(PropsUtil.getDataPropertyValue("Budget_GroupPopupGroupName"));
		Assert.assertEquals(expenseTrend.EIAccountDropDown().getText().trim(), PropsUtil.getDataPropertyValue("Budget_GroupPopupGroupName"),
       "Selected group is not displayed in IA account dropdown");

	}
	@Test(description="AT-96788,AT-96796,AT-96802:verify group sticky ness in EA when group is selected from IA",priority=12,dependsOnMethods="selectGroup_IA")
	public void verifyGroup_IAToEA()
	{
		expenseTrend.navigateToEA();
		
		Assert.assertEquals(expenseTrend.EIAccountDropDown().getText().trim(), PropsUtil.getDataPropertyValue("Budget_GroupPopupGroupName"), 
				"selected group is not displayed in EA account dropdown");

	}
	@Test(description="AT-96788,AT-96796,AT-96802:verify group sticky ness in CF when group is selected from IA",priority=13,dependsOnMethods="selectGroup_IA")
	public void verifyGroup_IAToCashFlow()
	{
		PageParser.forceNavigate("CashFlow", d);
		SeleniumUtil.waitForPageToLoad();
		Assert.assertEquals(cashFlowLandingPage.CFAccountDropDown().getText().trim(), PropsUtil.getDataPropertyValue("Budget_GroupPopupGroupName"),
				"Selected group is not Displayed in  cashflow account dropdown");

	}
	@Test(description="AT-96788,AT-96796,AT-96802:verify group sticky ness in Budget when group is selected from IA",priority=14,dependsOnMethods="selectGroup_IA")
	public void verifyGroup_IAtoBudget()
	{
		    PageParser.forceNavigate("Budget", d);
			SeleniumUtil.waitForPageToLoad();
		   
		    Assert.assertEquals(budgetSummery.BudgetSummery_MultiBudgetGroupDropDown().getAttribute("value").trim(), 
		    		PropsUtil.getDataPropertyValue("Budget_GroupPopupGroupName"), "selected group is not displayed in budget group dropdown");

	}
	@Test(description="AT-96790:verify group sticky ness  when group is selected from CF",priority=15,dependsOnMethods="verifyGroup_EAtoBudget")
	public void selectGroup_CashFlow()
	{   PageParser.forceNavigate("CashFlow", d);
		SeleniumUtil.waitForPageToLoad();
		cashFlowLandingPage.clickaccountDropDown();
		cashFlowLandingPage.clickGroup(PropsUtil.getDataPropertyValue("StickeyNessBudget_BudgetGroup"));
		SeleniumUtil.waitForPageToLoad();
		Assert.assertEquals(cashFlowLandingPage.CFAccountDropDown().getText().trim(), 
				PropsUtil.getDataPropertyValue("StickeyNessBudget_BudgetGroup"), "selected group is not dispalyed in cashflow account dropdown");

	}
	@Test(description="AT-96790:verify group sticky ness  when group is selected from CF",priority=16,dependsOnMethods="selectGroup_CashFlow")
	public void verifyGroup_CFToEA()
	{
		expenseTrend.navigateToEA();
		SeleniumUtil.waitForPageToLoad();
		Assert.assertEquals(expenseTrend.EIAccountDropDown().getText(), PropsUtil.getDataPropertyValue("StickeyNessBudget_BudgetGroup"),
				"selected group is not displayed in EA");

	}
	@Test(description="AT-96790:verify group sticky ness in EA when group is selected from CF",priority=17,dependsOnMethods="verifyGroup_CFToEA")
	public void verifyGroup_CFToIA()
	{
		expenseTrend.clickIncome();
		Assert.assertEquals(expenseTrend.EIAccountDropDown().getText().trim(), PropsUtil.getDataPropertyValue("StickeyNessBudget_BudgetGroup"), 
				"Selected group is not displayed in IA");

	}
	@Test(description="AT-96790:verify group sticky ness in Budget when group is selected from CF",priority=18,dependsOnMethods="verifyGroup_CFToEA")
	public void verifyGroup_CFtoBudget()
	{
		    PageParser.forceNavigate("Budget", d);
			SeleniumUtil.waitForPageToLoad();
		 
		    Assert.assertEquals(budgetSummery.BudgetSummery_MultiBudgetGroupDropDown().getAttribute("value").trim(),
PropsUtil.getDataPropertyValue("StickeyNessBudget_BudgetGroup"), "Selected group is not displayed in budget");
            
	}
	@Test(description="AT-96793,AT-96805:verify Account sticky ness  when account is selected from EA",priority=19)
	public void selectAccount_EA()
	{
		
		expenseTrend.navigateToEA();
		expenseTrend.clickEIAccountDropDown();
		expenseTrend.clickAccountLink();
		SeleniumUtil.waitForPageToLoad();
		expenseTrend.selectAccount(PropsUtil.getDataPropertyValue("StickyNessEA_Account"));
		SeleniumUtil.waitForPageToLoad();
		Assert.assertEquals(expenseTrend.EIAccountDropDown().getText().trim(), PropsUtil.getDataPropertyValue("StickyNessEA_Account"), 
				"Selected account is not displayed in EA");
	}
	@Test(description="AT-96793,AT-96807:verify Account sticky ness in IA when account is selected from EA",priority=20,dependsOnMethods="selectAccount_EA")
	public void verifyAccount_EAToIA()
	{
		
		expenseTrend.clickIncome();

		Assert.assertEquals(expenseTrend.EIAccountDropDown().getText().trim(), PropsUtil.getDataPropertyValue("StickyNessEA_Account"), "Selected account is not displayed in IA account dropdown");
	}
	@Test(description="AT-96793,AT-96807:verify Account sticky ness in CF when account is selected from EA",priority=21,dependsOnMethods="selectAccount_EA")
	public void verifyAccount_EAToCF()
	{
		
		PageParser.forceNavigate("CashFlow", d);
		SeleniumUtil.waitForPageToLoad();
		Assert.assertEquals(cashFlowLandingPage.CFAccountDropDown().getText().trim(), 
				PropsUtil.getDataPropertyValue("StickyNessEA_Account"), "Selected account is not displayed CF account dropdown");
	}
	@Test(description="AT-96793:verify group sticky ness inBudget when account is selected from EA",priority=22,dependsOnMethods="selectAccount_EA")
	public void verifyAccount_EAToBudget()
	{
		PageParser.forceNavigate("Budget", d);
		SeleniumUtil.waitForPageToLoad();
		expenseTrend.navigateToEA();
		
		SeleniumUtil.waitForPageToLoad();
		Assert.assertEquals(expenseTrend.EIAccountDropDown().getText().trim(),
   PropsUtil.getDataPropertyValue("StickyNessEA_Account"), "Selected group is changed in budget when accont is selected in EA");
	
	}
	@Test(description="AT-96793:verify Account sticky ness  when account is selected from IA",priority=23,dependsOnMethods="selectAccount_EA")
	public void selectAccount_IA()
	{
		expenseTrend.navigateToEA();
		expenseTrend.clickIncome();
		expenseTrend.clickEIAccountDropDown();
		expenseTrend.clickAccountLink();
		SeleniumUtil.waitForPageToLoad();
	    expenseTrend.clickEIAllAccountCheckBox();
	    SeleniumUtil.waitForPageToLoad();
	    expenseTrend.clickEIAllAccountCheckBox();
	    SeleniumUtil.waitForPageToLoad();
		expenseTrend.selectAccount(PropsUtil.getDataPropertyValue("StickyNessIA_Account"));
		 SeleniumUtil.waitForPageToLoad();
		Assert.assertEquals(expenseTrend.EIAccountDropDown().getText().trim(),
				PropsUtil.getDataPropertyValue("StickyNessIA_Account"), "Selected account is not displayed in IA");

	}
	@Test(description="AT-96793:verify Account sticky ness in EA when account is selected from IA",priority=24,dependsOnMethods="selectAccount_IA")
	public void verifyAccount_IAToEA()
	{
		expenseTrend.navigateToEA();
		Assert.assertEquals(expenseTrend.EIAccountDropDown().getText().trim(), PropsUtil.getDataPropertyValue("StickyNessIA_Account"), 
				"Selected account is not displayed in EA");
	}
	@Test(description="AT-96793:verify Account sticky ness in CF when account is selected from IA",priority=25,dependsOnMethods="selectAccount_IA")
	public void verifyAccount_IAtoCF()
	{
		
		PageParser.forceNavigate("CashFlow", d);
		SeleniumUtil.waitForPageToLoad();
		Assert.assertEquals(cashFlowLandingPage.CFAccountDropDown().getText().trim(), PropsUtil.getDataPropertyValue("StickyNessIA_Account"), 
				"Selected account is not displayed in CF");
	}
	@Test(description="AT-96793:verify group sticky ness in Budget when account is selected from IA",priority=26,dependsOnMethods="selectAccount_IA")
	public void verifyAccount_IAToBudget()
	{
		PageParser.forceNavigate("Budget", d);
		SeleniumUtil.waitForPageToLoad();
		expenseTrend.navigateToEA();
		SeleniumUtil.waitForPageToLoad();
		expenseTrend.clickIncome();
		SeleniumUtil.waitForPageToLoad();
		Assert.assertEquals(expenseTrend.EIAccountDropDown().getText().trim(), PropsUtil.getDataPropertyValue("StickyNessIA_Account"), 
				"Selected group is changed in budget when accont is selected in IA");
	
	}
	@Test(description="AT-96792:verify Account sticky ness  when account is selected from CF",priority=27,dependsOnMethods="selectAccount_IA")
	public void selectAccount_CF()
	{
		
		PageParser.forceNavigate("CashFlow", d);
		SeleniumUtil.waitForPageToLoad();
		cashFlowLandingPage.clickaccountDropDown();
		cashFlowLandingPage.clickAccountLink();
		SeleniumUtil.waitForPageToLoad();
		SeleniumUtil.click(cashFlowLandingPage.CFAllAccountCheckBox());
		SeleniumUtil.waitForPageToLoad();
		cashFlowLandingPage.clickaccountDropDown();
		SeleniumUtil.click(cashFlowLandingPage.CFAllAccountCheckBox());
		SeleniumUtil.waitForPageToLoad();
		SeleniumUtil.click(cashFlowLandingPage.CFAllAccountName(PropsUtil.getDataPropertyValue("StickyNessEA_Account")));
		SeleniumUtil.waitForPageToLoad();
		Assert.assertEquals(cashFlowLandingPage.CFAccountDropDown().getText().trim(), PropsUtil.getDataPropertyValue("StickyNessEA_Account"),
        "Selected account is not displayed in CF");

	}
	@Test(description="AT-96792:verify Account sticky ness in EA when account is selected from CF",priority=28,dependsOnMethods="selectAccount_CF")
	public void verifyAccount_CFToEA()
	{
		expenseTrend.navigateToEA();
		Assert.assertEquals(expenseTrend.EIAccountDropDown().getText().trim(), PropsUtil.getDataPropertyValue("StickyNessEA_Account"),
				"Selected account is not displayed in EA");
	}
	@Test(description="AT-96792:verify Account sticky ness in IA when account is selected from CF",priority=29,dependsOnMethods="selectAccount_CF")
	public void verifyAccount_CFtoIA()
	{
		expenseTrend.clickIncome();
		Assert.assertEquals(expenseTrend.EIAccountDropDown().getText().trim(), PropsUtil.getDataPropertyValue("StickyNessEA_Account"),
				"Selected account is not displayed in IA");

		
	}
	@Test(description="AT-96792:verify group sticky ness in budget when account is selected from CF",priority=30,dependsOnMethods="selectAccount_CF")
	public void verifyAccount_CFToBudget()
	{
		PageParser.forceNavigate("Budget", d);
		SeleniumUtil.waitForPageToLoad();
		PageParser.forceNavigate("CashFlow", d);
		SeleniumUtil.waitForPageToLoad();
		Assert.assertEquals(cashFlowLandingPage.CFAccountDropDown().getText().trim(), PropsUtil.getDataPropertyValue("StickyNessEA_Account"),
				"Selected group is changed in budget when accont is selected in Cf");

	}

	@Test(description="AT-96804:verify Account Sticky  ness in IA when unselect account from EA ",priority=31,dependsOnMethods="selectAccount_CF")
	public void UnselectAllAccount_EAtoIA()
	{
		
		expenseTrend.navigateToEA();
		expenseTrend.clickEIAccountDropDown();
		expenseTrend.clickAccountLink();
		SeleniumUtil.waitForPageToLoad();
	    expenseTrend.clickEIAllAccountCheckBox();
	    SeleniumUtil.waitForPageToLoad();
	    expenseTrend.navigateToEA();
		expenseTrend.clickIncome();
		Assert.assertEquals(expenseTrend.EIAccountDropDown().getText().trim(), PropsUtil.getDataPropertyValue("StiackyNess_AllIAAccountLable"), 
				"all account is not selected in IA");

	}
	@Test(description="AT-96804:verify Account Sticky  ness in CF when unselect account from EA",priority=32,dependsOnMethods="UnselectAllAccount_EAtoIA")
	public void UnselectAllAccount_EAtoCF()
	{
		
		PageParser.forceNavigate("CashFlow", d);
		SeleniumUtil.waitForPageToLoad();
		Assert.assertEquals(cashFlowLandingPage.CFAccountDropDown().getText().trim(), PropsUtil.getDataPropertyValue("StiackyNess_AllCFAccountLable"), "all account is not selected in CF");

	}
	
	@Test(description="AT-96804:verify Account Sticky ness when unselect account from EA",priority=33,dependsOnMethods="UnselectAllAccount_EAtoIA")
	public void UnselectAllAccount_ToEA()
	{
		
		
	    expenseTrend.navigateToEA();
		Assert.assertEquals(expenseTrend.EIAccountDropDown().getText().trim(), PropsUtil.getDataPropertyValue("StiackyNess_AllEAAccountLable"), "all account is not selected in EA");

	}
	@Test(description="AT-96804:verify Account Sticky  ness in EA when unselect account from IA",priority=34,dependsOnMethods="UnselectAllAccount_EAtoIA")
	public void UnselectAllAccount_IAtoEA()
	{
		
		expenseTrend.navigateToEA();
		expenseTrend.clickIncome();
		expenseTrend.clickEIAccountDropDown();
		expenseTrend.clickAccountLink();
		SeleniumUtil.waitForPageToLoad();
	    expenseTrend.clickEIAllAccountCheckBox();
	    SeleniumUtil.waitForPageToLoad();
	    expenseTrend.navigateToEA();
		
		Assert.assertEquals(expenseTrend.EIAccountDropDown().getText().trim(), PropsUtil.getDataPropertyValue("StiackyNess_AllEAAccountLable"),
				"all account is not selected in EA");

	}
	@Test(description="AT-96804:verify Account Sticky  ness in CF when unselect account from IA",priority=35,dependsOnMethods="UnselectAllAccount_IAtoEA")
	public void UnselectAllAccount_IAtoCF()
	{
		
		PageParser.forceNavigate("CashFlow", d);
		SeleniumUtil.waitForPageToLoad();
		Assert.assertEquals(cashFlowLandingPage.CFAccountDropDown().getText().trim(), PropsUtil.getDataPropertyValue("StiackyNess_AllCFAccountLable"),
				"all account is not selected in CF");

	}
	
	@Test(description="AT-96804:verify Account Stickyness  in CF  when unselect account from IA",priority=36,dependsOnMethods="UnselectAllAccount_IAtoEA")
	public void UnselectAllAccount_ToIA()
	{
		expenseTrend.navigateToEA();
		expenseTrend.clickIncome();
	  
		Assert.assertEquals(expenseTrend.EIAccountDropDown().getText().trim(), PropsUtil.getDataPropertyValue("StiackyNess_AllIAAccountLable"),
				"all account is not selected in IA");

	}
	@Test(description="AT-96804:verify Account Sticky  ness in EA when unselect account from CF",priority=37,dependsOnMethods="UnselectAllAccount_IAtoEA")
	public void UnselectAllAccount_CFtoEA()
	{
		
		PageParser.forceNavigate("CashFlow", d);
		SeleniumUtil.waitForPageToLoad();
		SeleniumUtil.click(cashFlowLandingPage.CFAccountDropDown());
		SeleniumUtil.waitForPageToLoad();
		SeleniumUtil.click(cashFlowLandingPage.CFAllAccountCheckBox());
		expenseTrend.navigateToEA();
		Assert.assertEquals(expenseTrend.EIAccountDropDown().getText().trim(), PropsUtil.getDataPropertyValue("StiackyNess_AllEAAccountLable"),
				"all account is not selected in EA");

	}
	
	@Test(description="AT-96804:verify Account Sticky ness in IA  when unselect account from CF",priority=38,dependsOnMethods="UnselectAllAccount_CFtoEA")
	public void UnselectAllAccount_CFToIA()
	{
		 expenseTrend.navigateToEA();
		expenseTrend.clickIncome();
	   
		Assert.assertEquals(expenseTrend.EIAccountDropDown().getText().trim(), PropsUtil.getDataPropertyValue("StiackyNess_AllIAAccountLable"), 
				"all account is not selected in IA");

	}
	@Test(description="AT-96804:verify Account Sticky  ness when unselect account from CF",priority=39,dependsOnMethods="UnselectAllAccount_CFtoEA")
	public void UnselectAllAccount_ToCF()
	{
		
		PageParser.forceNavigate("CashFlow", d);
		SeleniumUtil.waitForPageToLoad();
		Assert.assertEquals(cashFlowLandingPage.CFAccountDropDown().getText().trim(), PropsUtil.getDataPropertyValue("StiackyNess_AllCFAccountLable"), 
				"all account is not selected in CF");

	}
	@Test(description="AT-96801,AT-96805:verify group Sticky  ness in EA ",priority=40,dependsOnMethods="UnselectAllAccount_CFtoEA")
	public void selectAllAccount_EAToChangeGroupBudget()
	{
		expenseTrend.navigateToEA();
		expenseTrend.clickEIAccountDropDown();
		expenseTrend.clickAccountLink();
		SeleniumUtil.waitForPageToLoad();
		expenseTrend.selectAccount(PropsUtil.getDataPropertyValue("StickyNessEA_Account"));
		SeleniumUtil.waitForPageToLoad();
		PageParser.forceNavigate("Budget", d);
		SeleniumUtil.waitForPageToLoad();
	   SeleniumUtil.click(budgetSummery.BudgetSummery_MultiBudgetGroupDropDown());
	   budgetSummery.selectBudgetGroup(PropsUtil.getDataPropertyValue("StickeyNessBudget_BudgetGroup"));
	   expenseTrend.navigateToEA();
		Assert.assertEquals(expenseTrend.EIAccountDropDown().getText().trim(), PropsUtil.getDataPropertyValue("StickeyNessBudget_BudgetGroup"), "Selected group is not displayed in EA");
   }
	@Test(description="AT-96801,AT-96805:verify group Sticky  ness in IA",priority=41,dependsOnMethods="selectAllAccount_EAToChangeGroupBudget")
	public void selectAllAccount_IAToChangeGroupBudget()
	{
		
		expenseTrend.clickIncome();
	    expenseTrend.navigateToEA();
		Assert.assertEquals(expenseTrend.EIAccountDropDown().getText().trim(), PropsUtil.getDataPropertyValue("StickeyNessBudget_BudgetGroup"),
				"Selected group is not displayed in IA");

	}
	@Test(description="AT-96801,AT-96805:verify group Sticky  ness in CF",priority=42,dependsOnMethods="selectAllAccount_EAToChangeGroupBudget")
	public void selectAllAccount_CFToChangeGroupBudget()
	{
		
		PageParser.forceNavigate("CashFlow", d);
		SeleniumUtil.waitForPageToLoad();
		Assert.assertEquals(cashFlowLandingPage.CFAccountDropDown().getText().trim(), PropsUtil.getDataPropertyValue("StickeyNessBudget_BudgetGroup"), 
				"Selected group is not displayed in CF");

	}
	@Test(description="AT-96806:verify group is updtaed",priority=43)
	public void verifyUpdatedGroup_budget()
	{
		PageParser.forceNavigate("Budget", d);
		SeleniumUtil.waitForPageToLoad();
		
	    budgetSummery.clickcreateBudget();
	    SeleniumUtil.waitForPageToLoad();
	     budgetSummery.SelectAccountGroup(PropsUtil.getDataPropertyValue("Stickness_StickynessdAccountGroupName"));
	   createBudget.creatAccountGroupBudget(PropsUtil.getDataPropertyValue("Budget_Loc_CreateBudgetIncomeAmount"), 
				PropsUtil.getDataPropertyValue("Budget_Loc_CreateBudgetCategoryAmount"));
	    SeleniumUtil.waitForPageToLoad();
	  budgetSummery.selectBudgetGroup(PropsUtil.getDataPropertyValue("Stickness_StickynessdAccountGroupName"));
	  SeleniumUtil.waitForPageToLoad();
	  
	    PageParser.forceNavigate("AccountGroups", d);
		SeleniumUtil.waitForPageToLoad();
		  SeleniumUtil.click(accountGroupSetting.AccGroupSetting_GroupListName(PropsUtil.getDataPropertyValue("Stickness_StickynessdAccountGroupName")));
		    SeleniumUtil.waitForPageToLoad();
		SeleniumUtil.click(accountGroupSetting.AccountGroupEditBtn());
		SeleniumUtil.waitForPageToLoad();
		groupView.updateGroup(PropsUtil.getDataPropertyValue("Stickness_StickynessAccountUpdate").split(","));
		SeleniumUtil.waitForPageToLoad();
		PageParser.forceNavigate("Budget", d);
		SeleniumUtil.waitForPageToLoad();
	    Assert.assertEquals(budgetSummery.BudgetSummery_MultiBudgetGroupDropDown().getAttribute("value").trim(), 
	    		PropsUtil.getDataPropertyValue("Stickness_StickynessdAccountGroupName"), "Updated group is not displayed");

	}
	@Test(description="AT-96806:verify group is updated",priority=44,dependsOnMethods="verifyUpdatedGroup_budget")
	public void verifyUpdatedGroup_EA()
	{
		expenseTrend.navigateToEA();
		Assert.assertEquals(expenseTrend.EIAccountDropDown().getText().trim(), PropsUtil.getDataPropertyValue("StiackyNess_AllEAAccountLable"),
      "all account is not displayed EA");

	}
	@Test(description="AT-96806:verify group is updated",priority=45,dependsOnMethods="verifyUpdatedGroup_budget")
	public void verifyUpdatedGroup_IA()
	{
		
		expenseTrend.navigateToEA();
		expenseTrend.clickIncome();
	    Assert.assertEquals(expenseTrend.EIAccountDropDown().getText().trim(), PropsUtil.getDataPropertyValue("StiackyNess_AllIAAccountLable"),
	    		 "all account is not displayed IA");

	}
	@Test(description="AT-96806:verify group is updated",priority=46,dependsOnMethods="verifyUpdatedGroup_budget")
	public void verifyUpdatedGroup_CF()
	{
		
		PageParser.forceNavigate("CashFlow", d);
		SeleniumUtil.waitForPageToLoad();
		Assert.assertEquals(cashFlowLandingPage.CFAccountDropDown().getText().trim(), PropsUtil.getDataPropertyValue("StiackyNess_AllCFAccountLable"), 
				"all account is not displayed CF");

	}
	@Test(description="AT-96806::verify deleting group selected in account dropdown",priority=47)
	public void verifyDeleteGroup_budget()
	{
		PageParser.forceNavigate("Budget", d);
		SeleniumUtil.waitForPageToLoad();
		budgetSummery.selectBudgetGroup(PropsUtil.getDataPropertyValue("Stickyness_GroupPopupGroupName"));
		  SeleniumUtil.waitForPageToLoad();
	  
	    PageParser.forceNavigate("AccountGroups", d);
		SeleniumUtil.waitForPageToLoad();
	    SeleniumUtil.click(accountGroupSetting.AccGroupSetting_GroupListName(PropsUtil.getDataPropertyValue("Stickyness_GroupPopupGroupName")));
	    SeleniumUtil.waitForPageToLoad();
        accountGroupSetting.deleteAccountGroup();
		SeleniumUtil.waitForPageToLoad();
		
		PageParser.forceNavigate("Budget", d);
		SeleniumUtil.waitForPageToLoad();
	    Assert.assertEquals(budgetSummery.BudgetSummery_MultiBudgetGroupDropDown().getAttribute("value").trim(), 
	    		PropsUtil.getDataPropertyValue("StickeyNessBudget_BudgetGroup"), "deleted group is displayed");

	}
	@Test(description="AT-96806:verify deleting group selected in account dropdown in EA",priority=50,dependsOnMethods="verifyDeleteGroup_budget")
	public void verifyDeleteGroup_EA()
	{
		expenseTrend.navigateToEA();
		Assert.assertEquals(expenseTrend.EIAccountDropDown().getText().trim(), PropsUtil.getDataPropertyValue("StiackyNess_AllEAAccountLable"), "deleted group is displayed");

	}
	@Test(description="AT-96806:verify deleting group selected in account dropdown in IA",priority=51,dependsOnMethods="verifyDeleteGroup_budget")
	public void verifyDeleteGroup_IA()
	{
		 expenseTrend.navigateToEA();
		expenseTrend.clickIncome();
	   
		Assert.assertEquals(expenseTrend.EIAccountDropDown().getText().trim(), PropsUtil.getDataPropertyValue("StiackyNess_AllIAAccountLable"), "deleted group is displayed");

	}
	@Test(description="AT-96806:verify deleting group selected in account dropdown In CF",priority=52,dependsOnMethods="verifyDeleteGroup_budget")
	public void verifyDeleteGroup_CF()
	{
		
		PageParser.forceNavigate("CashFlow", d);
		SeleniumUtil.waitForPageToLoad();
		Assert.assertEquals(cashFlowLandingPage.CFAccountDropDown().getText().trim(), PropsUtil.getDataPropertyValue("StiackyNess_AllCFAccountLable"), "deleted group is displayed");

	}
	@Test(description="AT-96797:verify deleting account is selected in EA",priority=53)
	public void verifyDeletedAccount_EA()
	{
		
		expenseTrend.navigateToEA();
		expenseTrend.clickEIAccountDropDown();
		expenseTrend.clickAccountLink();
		SeleniumUtil.waitForPageToLoad();
		expenseTrend.selectAccount(PropsUtil.getDataPropertyValue("StickyNessEA_Account"));
		SeleniumUtil.waitForPageToLoad();
		PageParser.forceNavigate("AccountSettings", d);
		SeleniumUtil.waitForPageToLoad();
		deleetAccount.deleteAccount(PropsUtil.getDataPropertyValue("Stickness_StickynesssDeleteAccountContainer"), 
				PropsUtil.getDataPropertyValue("Stickness_StickynesssDeleteAccountcontainerAccounts").split(","));
	
		SeleniumUtil.waitForPageToLoad();
		expenseTrend.navigateToEA();
		Assert.assertEquals(expenseTrend.EIAccountDropDown().getText().trim(), PropsUtil.getDataPropertyValue("StiackyNess_AllEAAccountLable"), "deleted Account is displayed");
	}
	@Test(description="AT-96797:verify account drop down after deleting selected account in IA",priority=54,dependsOnMethods="verifyDeletedAccount_EA")
	public void verifyDeletedAccount_IA()
	{
		 expenseTrend.navigateToEA();
		expenseTrend.clickIncome();
	   
		Assert.assertEquals(expenseTrend.EIAccountDropDown().getText().trim(), PropsUtil.getDataPropertyValue("StiackyNess_AllIAAccountLable"), "deleted Account is displayed");

	}
	@Test(description="AT-96797:verify account drop down after deleting selected account in CF",priority=55,dependsOnMethods="verifyDeletedAccount_EA")
	public void verifyDeletedAccount_CF()
	{
		
		PageParser.forceNavigate("CashFlow", d);
		SeleniumUtil.waitForPageToLoad();
		Assert.assertEquals(cashFlowLandingPage.CFAccountDropDown().getText().trim(), PropsUtil.getDataPropertyValue("StiackyNess_AllCFAccountLable"), "deleted Account is displayed");

	}
	@Test(description="AT-96798,AT-96799:verify account dropdown after adding account",priority=56)
	public void verifyAddedAccount_EA() throws AWTException, InterruptedException
	{
		
		SeleniumUtil.click(cashFlowLandingPage.CFAccountDropDown());
		SeleniumUtil.waitForPageToLoad();
		SeleniumUtil.click(cashFlowLandingPage.CFAllAccountCheckBox());
		SeleniumUtil.waitForPageToLoad();
		SeleniumUtil.click(cashFlowLandingPage.CFAllAccountCheckBox());
		SeleniumUtil.waitForPageToLoad();
		accountAddition.linkAccount();
		accountAddition.addAggregatedAccount(PropsUtil.getDataPropertyValue("Stickyness_LinkAccountName1"), PropsUtil.getDataPropertyValue("Stickyness_LinkAccountPAssword1"));
		expenseTrend.navigateToEA();
		Assert.assertEquals(expenseTrend.EIAccountDropDown().getText().trim(), PropsUtil.getDataPropertyValue("StiackyNess_AllEAAccountLable"), "deleted Account is displayed");

	}
	@Test(description="AT-9679,8AT-96799,AT-96800,AT-96803:verify account dropdown after adding account",priority=57,dependsOnMethods="verifyAddedAccount_EA")
	public void  verifyAddedAccount_IA()
	{
		 expenseTrend.navigateToEA();
		expenseTrend.clickIncome();
	   
		Assert.assertEquals(expenseTrend.EIAccountDropDown().getText().trim(), PropsUtil.getDataPropertyValue("StiackyNess_AllIAAccountLable"), "All account is not selected");

	}
	@Test(description="AT-96798,AT-96799,AT-96800,AT-96803:verify account dropdown after adding account",priority=58,dependsOnMethods="verifyAddedAccount_IA")
	public void  verifyAddedAccount_CF()
	{
		
		PageParser.forceNavigate("CashFlow", d);
		SeleniumUtil.waitForPageToLoad();
		Assert.assertEquals(cashFlowLandingPage.CFAccountDropDown().getText().trim(), PropsUtil.getDataPropertyValue("StiackyNess_AllCFAccountLable"), "All account is not selected");

	}
	@Test(description="AT-96795,AT-96799,AT-96800:verify account dropdown after adding account",priority=59,dependsOnMethods="verifyAddedAccount_IA")
	public void selectTimeFilter_EA()
	{
		
		expenseTrend.navigateToEA();
		expenseTrend.selectTimeFilter(PropsUtil.getDataPropertyValue("StickyNessEA_TimeFilter"));
	    SeleniumUtil.waitForPageToLoad();
		Assert.assertEquals(expenseTrend.EIATimeFilterDropDownLabel().getText().trim(), PropsUtil.getDataPropertyValue("StickyNessEA_TimeFilter"),
				"Selected time filter is displayed EA");
	}
	

	@Test(description="AT-96795,AT-96799:verify time filter in IA when time filter selected in EA",priority=60)
	public void verifyTimeFilter_EAToIA()
	{
		
		expenseTrend.clickIncome();

		Assert.assertEquals(expenseTrend.EIATimeFilterDropDownLabel().getText().trim(), PropsUtil.getDataPropertyValue("StickyNessEA_TimeFilter"), "Selected time filter is displayed IA");
	}
	@Test(description="AT-96795,AT-96799:verify time filter in CF when time filter selected in EA",priority=61,dependsOnMethods="verifyTimeFilter_EAToIA")
	public void verifyTimeFilter_EAToCF()
	{
		
		PageParser.forceNavigate("CashFlow", d);
		SeleniumUtil.waitForPageToLoad();
		Assert.assertEquals(cashFlowLandingPage.CFTimeFilterDropDownLabel().getText().trim(),
				PropsUtil.getDataPropertyValue("StickyNessEA_TimeFilter"), "Selected time filter is displayed CF");
	}
	
	@Test(description="AT-96795,AT-96799:verify time filter when time filter selected in IS",priority=62)
	public void verifyTimeFilter_IA()
	{
		
		expenseTrend.navigateToEA();
		expenseTrend.clickIncome();
		expenseTrend.selectTimeFilter(PropsUtil.getDataPropertyValue("StickyNessIA_TimeFilter"));
		SeleniumUtil.waitForPageToLoad();
		Assert.assertEquals(expenseTrend.EIATimeFilterDropDownLabel().getText().trim(),
				PropsUtil.getDataPropertyValue("StickyNessIA_TimeFilter"), "Selected time filter is displayed IA");


	}
	@Test(description="AT-96795,AT-96799:verify time filter in EA when time filter selected in IA",priority=63,dependsOnMethods="verifyTimeFilter_IA")
	public void verifyTimeFilter_IAToEA()
	{
		expenseTrend.navigateToEA();
		Assert.assertEquals(expenseTrend.EIATimeFilterDropDownLabel().getText().trim(), 
				PropsUtil.getDataPropertyValue("StickyNessIA_TimeFilter"), "Selected time filter is displayed EA");

	}
	@Test(description="AT-96795,AT-96799:verify time filter in CF when time filter selected in IA",priority=64,dependsOnMethods="verifyTimeFilter_IA")
	public void verifyTimeFilter_IAtoCF()
	{
		
		PageParser.forceNavigate("CashFlow", d);
		SeleniumUtil.waitForPageToLoad();
		Assert.assertEquals(cashFlowLandingPage.CFTimeFilterDropDownLabel().getText().trim(), PropsUtil.getDataPropertyValue("StickyNessIA_TimeFilter"), 
				"Selected time filter is displayed CF");
	}
	@Test(description="AT-96794,AT-96799:verify time filter in CF",priority=65)
	public void verifyTimeFilter_CF()
	{
		
		PageParser.forceNavigate("CashFlow", d);
		SeleniumUtil.waitForPageToLoad();
		SeleniumUtil.click(cashFlowLandingPage.TimeFilterDropdown());
		SeleniumUtil.click(cashFlowLandingPage.CFTimeFilterName(PropsUtil.getDataPropertyValue("StickyNessEA_TimeFilter")));
		SeleniumUtil.waitForPageToLoad();
		Assert.assertEquals(cashFlowLandingPage.CFTimeFilterDropDownLabel().getText().trim(), PropsUtil.getDataPropertyValue("StickyNessEA_TimeFilter"), 
				"Selected time filter is displayed CF");

	}
	@Test(description="AT-96794,AT-96799:verify time filter in EA when time filter selected in CF",priority=66,dependsOnMethods="verifyTimeFilter_CF")
	public void verifyTimeFilter_CFToEA()
	{
		expenseTrend.navigateToEA();
		Assert.assertEquals(expenseTrend.EIATimeFilterDropDownLabel().getText().trim(), PropsUtil.getDataPropertyValue("StickyNessEA_TimeFilter"), 
				"Selected time filter is displayed EA");
	}
	@Test(description="AT-96794,AT-96799:verify time filter in IA when time filter selected in CF",priority=67,dependsOnMethods="verifyTimeFilter_CF")
	public void verifyTimeFilter_CFtoIA()
	{
		expenseTrend.clickIncome();
		Assert.assertEquals(expenseTrend.EIATimeFilterDropDownLabel().getText().trim(), PropsUtil.getDataPropertyValue("StickyNessEA_TimeFilter"), 
				"Selected time filter is displayed IA");

		
	}
}
