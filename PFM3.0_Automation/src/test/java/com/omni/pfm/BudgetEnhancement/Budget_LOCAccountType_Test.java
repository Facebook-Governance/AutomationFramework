/**
* Copyright (c) 2018 Yodlee Inc. All Rights Reserved.
*
* This software is the confidential and proprietary information of
* Yodlee, Inc. Use is subject to license terms.
*
* @author Renuka K
*/
package com.omni.pfm.BudgetEnhancement;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.omni.pfm.Accounts.Accounts_ViewByGroup_Loc;
import com.omni.pfm.PageProcessor.PageParser;
import com.omni.pfm.pages.AccountAddition.AccountAddition;
import com.omni.pfm.pages.AccountSettings.AccountGroup_Settings_Loc;
import com.omni.pfm.pages.BudgetEnhancement.Budget_Income_And_Bill_Summery_Loc;
import com.omni.pfm.pages.BudgetEnhancement.Create_Budget_Loc;
import com.omni.pfm.pages.Login.LoginPage;
import com.omni.pfm.testBase.TestBase;
import com.omni.pfm.utility.PropsUtil;
import com.omni.pfm.utility.SeleniumUtil;

public class Budget_LOCAccountType_Test extends TestBase{
	Logger logger =LoggerFactory.getLogger(Budget_LOCAccountType_Test.class);
	Create_Budget_Loc createBudget;
	AccountAddition accountAddition;
	SeleniumUtil util;
	Accounts_ViewByGroup_Loc groupView;
	Budget_Income_And_Bill_Summery_Loc budgetSummery;
	AccountGroup_Settings_Loc accGroupSeting;
	@BeforeClass()
	public void init() throws Exception
	{
		doInitialization("Accounts");

		TestBase.tc = TestBase.extent.startTest("Login", "Login");
		TestBase.test.appendChild(TestBase.tc);

		createBudget = new Create_Budget_Loc(d);
		accountAddition=new AccountAddition();
	    util=new SeleniumUtil();
	    groupView=new Accounts_ViewByGroup_Loc(d);
	    budgetSummery=new Budget_Income_And_Bill_Summery_Loc(d);
	    accGroupSeting=new AccountGroup_Settings_Loc(d);
		LoginPage.loginMain(d, loginParameter);
		accountAddition.linkAccount();
		accountAddition.addAggregatedAccount(PropsUtil.getDataPropertyValue("Budget_LOC_LinkAccountName"), PropsUtil.getDataPropertyValue("Budget_LOC_LinkAccountPAssword"));
		groupView.refreshPage();
		SeleniumUtil.waitForPageToLoad();
		PageParser.forceNavigate("AccountsPage", d);
		SeleniumUtil.waitForPageToLoad();
		PageParser.forceNavigate("Budget", d);
		SeleniumUtil.waitForPageToLoad();
	}
	@Test(description="AT-96654,AT-96655:Verify Accounts container ", groups = {"DesktopBrowsers,sanity" }, priority = 1, enabled = true)
	public void verifyAccountConainer()
	{
		createBudget.clickgetStart();
		SeleniumUtil.waitForPageToLoad();
		util.assertExpectedActualList(util.getWebElementsValue(createBudget.AcntsContainerLabel()), 
				PropsUtil.getDataPropertyValue("Budget_Loc_AllAccountype"), "all eligible account types are not displayed");
	}
	@Test(description="AT-96654,AT-96655,AT-96656:Verify Accounts name ", groups = {
	"DesktopBrowsers,sanity" }, priority = 2, enabled = true,dependsOnMethods="verifyAccountConainer")
	public void verifyAccountName()
	{
		
		util.assertExpectedActualList(util.getWebElementsValue(createBudget.Budget_Step1_AcntsName()), 
				PropsUtil.getDataPropertyValue("Budget_Loc_AllAccountName"), "all eligible Account name not displayed");
	}
	@Test(description="AT-96654,AT-96655:Verify Accounts number ", groups = {
	"DesktopBrowsers,sanity" }, priority = 3, enabled = true,dependsOnMethods="verifyAccountConainer")
	public void verifyAccountNum()
	{
		String accountnum[]=PropsUtil.getDataPropertyValue("Budget_Loc_AllAccountNum").split(",");
		for(int i=0; i<accountnum.length;i++)
		{
		util.isAnyMatchInList(util.getWebElementsValue(createBudget.Budget_Step1_AcntsNum()), 
				accountnum[i]);
		}
	}
	@Test(description="AT-96657:Verify LOc,SBLOC,HELoc account unselected by dafault ", groups = {
	"DesktopBrowsers,sanity" }, priority = 4, enabled = true,dependsOnMethods="verifyAccountConainer")
	public void verifyLOCAccountUnselected()
	{
	    List<String> enabled=new ArrayList<String>();
		for(int i=createBudget.Budget_Step1_AcntsCheckBox().size()-3;i<createBudget.Budget_Step1_AcntsCheckBox().size();i++)
		{
			enabled.add(createBudget.Budget_Step1_AcntsCheckBox().get(i).getAttribute("class"));
		}
		 util.assertContainsFalse(enabled, PropsUtil.getDataPropertyValue("Budget_Loc_AllAccountSelected"));
	}
	@Test(description="AT-96667:Verify all engible accounts groups displaying gbudget group dropdown ", groups = {
	"DesktopBrowsers,sanity" }, priority = 5, enabled = true)
	public void verifyLOCAccount()
	{
		PageParser.forceNavigate("AccountsPage", d);
		SeleniumUtil.waitForPageToLoad();
		groupView.clikcCreatgroup();
		groupView.createGroup(PropsUtil.getDataPropertyValue("Budget_GroupPopupGroupName"), PropsUtil.getDataPropertyValue("Budget_GroupPopupGroupAccount").split(","));
		groupView.createGroupMore(PropsUtil.getDataPropertyValue("Budget_Loc_GroupNames").split(","), 
				PropsUtil.getDataPropertyValue("Budget_Loc_GroupNamesAccounts").split(","));
		SeleniumUtil.waitForPageToLoad();
	    PageParser.forceNavigate("Budget", d);
		createBudget.creatBudget(PropsUtil.getDataPropertyValue("Budget_Loc_CreateBudgetIncomeAmount"), 
				PropsUtil.getDataPropertyValue("Budget_Loc_CreateBudgetCategoryAmount"));
	    
		SeleniumUtil.waitForPageToLoad();
	    budgetSummery.clickcreateBudget();
	    SeleniumUtil.waitForPageToLoad();
	    budgetSummery.clickGroupDropDown();
	    SeleniumUtil.waitForPageToLoad();
	    util.assertExpectedActualList(util.getWebElementsValue(budgetSummery.BudgetSummery_BudgetGroupDropDownValueList()), 
	    		PropsUtil.getDataPropertyValue("BudgetSummery_Loc_AccountGroups"), "all LOc account is not dispalyed");
	    
	}
	
	@Test(description="AT-96668,AT-97031:Verify selected group eligible account container name is displayed ", groups = {
	"DesktopBrowsers,sanity" }, priority = 6, enabled = true,dependsOnMethods="verifyLOCAccount")
	public void verifyAccountConainer_GroupAccount()
	{
		SeleniumUtil.click(budgetSummery.BudgetSummery_BudgetGroupDropDownValueName(PropsUtil.getDataPropertyValue("Budget_Loc_validAccountGroupName")));
		util.assertExpectedActualList(util.getWebElementsValue(createBudget.AcntsContainerLabel()),
				PropsUtil.getDataPropertyValue("BudgetSummery_Loc_AllAccountype"), "all container is not displayed in groups");
	}
	@Test(description="AT-96668,AT-97031:Verify Verify selected group eligible account name is displayed", groups = {
	"DesktopBrowsers,sanity" }, priority = 7, enabled = true,dependsOnMethods="verifyAccountConainer_GroupAccount")
	public void verifyAccountName_GroupAccount()
	{
		
		util.assertExpectedActualList(util.getWebElementsValue(createBudget.Budget_Step1_AcntsName()), 
				PropsUtil.getDataPropertyValue("BudgetSummery_Loc_AllAccountName"), "Account name is not displyed in group");
	}
	@Test(description="AT-96668,AT-97031:Verify selected group eligible account num is displayed ", groups = {
	"DesktopBrowsers,sanity" }, priority = 8, enabled = true,dependsOnMethods="verifyAccountConainer_GroupAccount")
	public void verifyAccountNum_GroupAccount()
	{
		util.assertExpectedActualList(util.getWebElementsValue(createBudget.Budget_Step1_AcntsNum()), 
				PropsUtil.getDataPropertyValue("BudgetSummery_Loc_AllAccountNum"), "all account number is not displyed in group");
	}
	@Test(description="AT-96677:Verify deleted group is not displayed in budget ", groups = {
	"DesktopBrowsers,sanity" }, priority = 9, enabled = true,dependsOnMethods="verifyLOCAccount")
	public void verifyDeletedGroup()
	{
		PageParser.forceNavigate("AccountGroups", d);
		SeleniumUtil.waitForPageToLoad();
		accGroupSeting.clickOnGroup(PropsUtil.getDataPropertyValue("Budget_Loc_validAccountGroupName"));
		SeleniumUtil.waitForPageToLoad();
		accGroupSeting.deleteAccountGroup();
	    PageParser.forceNavigate("Budget", d);
		SeleniumUtil.waitForPageToLoad();
	    budgetSummery.clickcreateBudget();
		SeleniumUtil.waitForPageToLoad();
		budgetSummery.clickGroupDropDown();
		SeleniumUtil.waitForPageToLoad();
		boolean groupnotPresent=false;
		int groupsize=budgetSummery.BudgetSummery_BudgetGroupDropDownValueList().size();
		for(int i=0;i<groupsize;i++)
		{
			if(budgetSummery.BudgetSummery_BudgetGroupDropDownValueList().get(i).getText().trim().equals(PropsUtil.getDataPropertyValue("Budget_Loc_validAccountGroupName")))
			{
				groupnotPresent=false;
				break;
			}
			else
			{
				groupnotPresent=true;;
			}
		}
		Assert.assertTrue(groupnotPresent,"deleted group is displayed");
	}
	@Test(description="AT-96670:Verify Accounts group popUp ", groups = {
	"DesktopBrowsers,sanity" }, priority = 10, enabled = true,dependsOnMethods="verifyDeletedGroup")
	public void verifyGroupPopUpAccounts()
	{
		SeleniumUtil.click(budgetSummery.BudgetSummery_CreateGroup());
		SeleniumUtil.waitForPageToLoad();
		util.assertExpectedActualList(util.getWebElementsValue(groupView.AccountGroupTypeView_GrpAccName()), PropsUtil.getDataPropertyValue("BudgetSummery_Loc_CreateGroupPopUpAllAccounts"), "all account name is not displayed");
	}
}
