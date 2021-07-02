/**
* Copyright (c) 2018 Yodlee Inc. All Rights Reserved.
*
* This software is the confidential and proprietary information of
* Yodlee, Inc. Use is subject to license terms.
*
* @author Renuka K
*/
package com.omni.pfm.BudgetEnhancement;

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
import com.omni.pfm.pages.TransactionEnhancement1.Add_Manual_Transaction_Loc;
import com.omni.pfm.pages.TransactionEnhancement1.Transaction_Budget_Integration_Loc;
import com.omni.pfm.testBase.TestBase;
import com.omni.pfm.utility.PropsUtil;
import com.omni.pfm.utility.SeleniumUtil;

public class Budget_LOCAccount_Integration_Test extends TestBase {
	Logger logger =LoggerFactory.getLogger(Budget_LOCAccount_Integration_Test.class);
	Create_Budget_Loc createBudget;
	AccountAddition accountAddition;
	SeleniumUtil util;
	Accounts_ViewByGroup_Loc groupView;
	Budget_Income_And_Bill_Summery_Loc budgetSummery;
	AccountGroup_Settings_Loc accGroupSeting;
	Transaction_Budget_Integration_Loc txnBudget;
	Add_Manual_Transaction_Loc add_Manual;
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
	    add_Manual=new Add_Manual_Transaction_Loc(d);
	    LoginPage.loginMain(d, loginParameter);
		accountAddition.linkAccount();
		
		accountAddition.addAggregatedAccount(PropsUtil.getDataPropertyValue("Budget_LOC_LinkAccountName"), PropsUtil.getDataPropertyValue("Budget_LOC_LinkAccountPAssword"));

	    createBudget.refreshFinapp();
		SeleniumUtil.waitForPageToLoad();
		PageParser.forceNavigate("Budget", d);
		SeleniumUtil.waitForPageToLoad();
		txnBudget=new Transaction_Budget_Integration_Loc(d);
	}
	@Test(description="AT-97025:verify income amount step2", groups = {
	"DesktopBrowsers,sanity" }, priority = 1, enabled = true)
	public void verifyIncomeAmount()
	{ 
		//Verify that in Income section the LOC related income transaction amount must be reflected correctly

		createBudget.selectBudgetAccount();
		Assert.assertEquals(createBudget.Budget_Step2_IncomeAmount().getText().trim(), 
				PropsUtil.getDataPropertyValue("Budget_Loc_Step2_IncomeAmount"), "Income amount is not displayed");
	
	}
	@Test(description="AT-97026:verify recuring catgeory amount in step", groups = {
	"DesktopBrowsers,sanity" }, priority = 2, enabled = true,dependsOnMethods="verifyIncomeAmount")
	public void verifyRecurringBillCatAmount()
	{ 
		//Verify that in Recurring Bills section LOC account related recurring bill transaction category must be shown

		Assert.assertEquals(createBudget.Budget_Step2_RBCatAmount().getText().trim(), 
				PropsUtil.getDataPropertyValue("Budget_Loc_Step2_RecBillCatAmount"), "recuring bill cat amount is not displayed");
		Assert.assertEquals(createBudget.Budget_Step2_RBTotalAmount().getText().trim(), 
				PropsUtil.getDataPropertyValue("Budget_Loc_Step2_TotalRecBillAmount"), "total bill is not displayed");
	
	}
	@Test(description="AT-97027:verify recuring bill left amount in step3", groups = {
	"DesktopBrowsers,sanity" }, priority = 3, enabled = true,dependsOnMethods="verifyRecurringBillCatAmount")
	public void verifyAftRecurringBillLeftAmt()
	{ 		//Verify that in Recurring Bills section LOC account related recurring bill transaction category must be shown
		SeleniumUtil.click(createBudget.bgtNextButton2());
		SeleniumUtil.waitForPageToLoad();
		Assert.assertEquals(createBudget.Budget_Step3_LeftAftRB().getText().trim(), 
				PropsUtil.getDataPropertyValue("Budget_Loc_Step3_TotalBugetingAmount"), "recurring bill left amount is not displayed");
		
	}
	@Test(description="AT-97027,AT-97029:verify flexi category details in step3", groups = {
	"DesktopBrowsers,sanity" }, priority = 4, enabled = true,dependsOnMethods="verifyAftRecurringBillLeftAmt")
	public void verifyFlexiCatDetails()
	{ 
		//Verify that in Flexi Spending page the LOC related data/ Transaction must be shown in the application
//Verify that all the calculation related to LOC account must be considered for 3 month Average 

		util.assertExpectedActualList(util.getWebElementsIndexValue(createBudget.Budget_Step3_CategoryList(), 1), 
				PropsUtil.getDataPropertyValue("Budget_Loc_Step3_CategoryList"), "flexi bill catgeory  is not displayed");
		util.assertExpectedActualAmountList(util.getWebElementsIndexValue(createBudget.Budget_Step3_CategoryAmtList(), 1), 
				PropsUtil.getDataPropertyValue("Budget_Loc_Step3_CategoryAmount"), "flexi bill catgeory amount  is not displayed");
		Assert.assertEquals(createBudget.Budget_Step3_TotalFlexiAmt().getText().trim(),
				PropsUtil.getDataPropertyValue("Budget_Loc_Step3_TotalFlexiAmount"), "flexi bill catgeory total amount  is not displayed");
		Assert.assertEquals(createBudget.Budget_Step3_TotalLeftAmount().getText().trim(), 
				PropsUtil.getDataPropertyValue("BBudget_Loc_Step3_TotalLeftAmount"), "flexi bill left amount  is not displayed");
		
	}
	@Test(description="AT-97028:add category in step3", groups = {
	"DesktopBrowsers,sanity" }, priority = 5, enabled = true,dependsOnMethods="verifyFlexiCatDetails")
	public void verifyAddCategoryInStep3()
	{ 
		createBudget.addCatgeoryStep3(PropsUtil.getDataPropertyValue("BBudget_Loc_Step3_AddCategory"), PropsUtil.getDataPropertyValue("BBudget_Loc_Step3_AddCatagoryAmount"));
		int catsize=createBudget.Budget_Step3_CategoryList().size();
		boolean categoryAdded=false;
		for(int i=0;i<catsize;i++)
		{
			if(createBudget.Budget_Step3_CategoryList().get(i).getText().trim().equals(PropsUtil.getDataPropertyValue("BBudget_Loc_Step3_AddCategory")));
			{
				categoryAdded=true;
			}
		}
		Assert.assertTrue(categoryAdded,"catgeory is not added");
	}
	@Test(description="AT-97033:verify Income summery in budget summery", groups = {
	"DesktopBrowsers,sanity" }, priority = 6, enabled = true,dependsOnMethods="verifyAddCategoryInStep3")
	public void verifySummeryIncomeDetails()
	{ //Verify that in Budget Summary page, the Income bar shows the Acutal Income amount and what is left income amount from the LOC accounts which are linked by the user

		SeleniumUtil.click(createBudget.Budget_Step3_CategoryName(PropsUtil.getDataPropertyValue("BBudget_Loc_Step3_UnselectingCat")));
		SeleniumUtil.click(createBudget.doneButton());
		SeleniumUtil.waitForPageToLoad();
		Assert.assertEquals(createBudget.BudgetSummery_IncomeLeftAmount().getText().trim(), 
				PropsUtil.getDataPropertyValue("BudgetSummery_Loc_IncomeLeftAmount"), "income summery left amount is not displayed");

		Assert.assertEquals(createBudget.BudgetSummery_BdgIncomeActualAmount().getText().trim(), 
				PropsUtil.getDataPropertyValue("BudgetSummery_Loc_IncomeatualAmount"), "income summery actual amount is not displayed");

		Assert.assertEquals(createBudget.BudgetSummery_BdgIncomeAmount().getText().trim(), 
				PropsUtil.getDataPropertyValue("BudgetSummery_Loc_IncomeBudgetedAmount"), "income summery budgeted amount is not displayed");

	}
	@Test(description="AT-97034:verify spending summery in budget summery", groups = {
	"DesktopBrowsers,sanity" }, priority = 7, enabled = true,dependsOnMethods="verifySummeryIncomeDetails")
	public void verifySummerySpendingDetails()
	{ //Verify that in Budget Summary page, the spending bar shows the Acutal spending amount and what is left amount after spending from the LOC accounts which are linked by the user

		Assert.assertEquals(createBudget.BudgetSummery_SBLeftAmount().getText().trim(), 
				PropsUtil.getDataPropertyValue("BudgetSummery_Loc_BSLeftAmount"), "spending summery left amount is not displayed");

		Assert.assertEquals(createBudget.BudgetSummery_BdgSBActualAmount().getText().trim(), 
				PropsUtil.getDataPropertyValue("BudgetSummery_Loc_BSActualAmount"), "spending summery actual amount is not displayed");

		Assert.assertEquals(createBudget.BudgetSummery_BdgSBAmount().getText().trim(), 
				PropsUtil.getDataPropertyValue("BudgetSummery_Loc_BSBudgetedAmount"), "spending summery budgeted amount is not displayed");

	}
	@Test(description="AT-97035,AT-97036:verify Summery category detials", groups = {
	"DesktopBrowsers,sanity" }, priority = 8, enabled = true,dependsOnMethods="verifySummeryIncomeDetails")
	public void verifySummeryCatgoryName()
	{ //Verify that in Category details page spent amount is shown corrected based on the LOC account which is linked by the user
		//Verify that %Spent amount is shown correctly based on the LOC account which is linked by the user in category details table

		util.assertExpectedActualList(
				util.getWebElementsValue(createBudget.BudgetSummery_CatName()), PropsUtil.getDataPropertyValue("BudgetSummery_Loc_BudgetCatgeoryList"), 
				"budget summery catgeory is not displayed");
		util.assertExpectedActualAmountList(util.getWebElementsValue(createBudget.BudgetSummery_SpendPer()), PropsUtil.getDataPropertyValue("BudgetSummery_Loc_BudgetCatgeorySpend"), 
				"budget summery spending percentage is not displayed");
		util.assertExpectedActualAmountList(util.getWebElementsValue(createBudget.BudgetSummery_LeftAmt()), PropsUtil.getDataPropertyValue("BudgetSummery_Loc_BudgetCatgeoryLeft"), 
				"budget summery left amount is not displayed");
	}
	@Test(description="AT-97039:verify add category in budget summery", groups = {
	"DesktopBrowsers,sanity" }, priority = 9, enabled = true,dependsOnMethods="verifySummeryIncomeDetails")
	public void verifyAddBudgetCategory()
	{
//Verify that when user already have a specific transaction for current month and later user add budget with amount for that category then budgeted amount - transaction amount will show us the left amount in the column 

		createBudget.addBudgetCategory(Integer.parseInt(PropsUtil.getDataPropertyValue("BudgetSummery_Loc_AddCategoryIndex")),
				PropsUtil.getDataPropertyValue("BudgetSummery_Loc_AddCategory"), PropsUtil.getDataPropertyValue("BudgetSummery_Loc_AddCategoryAmount"));
		
	boolean addedCat=false;
	int catSize=createBudget.BudgetSummery_CatName().size();
	for(int i=0;i<catSize;i++)
	{
		if(createBudget.BudgetSummery_CatName().get(i).getText().trim().equals(PropsUtil.getDataPropertyValue("BudgetSummery_Loc_AddCategory")))
		{
			addedCat=true;
			break;
		}
	}
	Assert.assertTrue(addedCat,"added category is not displayed");
	}
	
	@Test(description="AT-97042:verify edut bdget amount in budget summery", groups = {
	"DesktopBrowsers,sanity" }, priority = 10, enabled = true,dependsOnMethods="verifySummeryIncomeDetails")
	public void verifyEditdBudgetAmt()
	{
		//Verify that when user try to edit the budget amount and change the budget amount to a different value then Remaining amount must be calculated accordingly and once saved it must be saved correctly

		createBudget.editBudgetCatAmount(PropsUtil.getDataPropertyValue("BudgetSummery_Loc_EditCategory"),
				PropsUtil.getDataPropertyValue("BudgetSummery_Loc_EditCategoryAmount"));
		SeleniumUtil.click(createBudget.budgetCategoriesRow(PropsUtil.getDataPropertyValue("BudgetSummery_Loc_EditCategory")));
	String editPerAmt=null;
	String editSpendAmt=null;
	int catSize=createBudget.BudgetSummery_CatName().size();
	for(int i=0;i<catSize;i++)
	{
		if(createBudget.BudgetSummery_CatName().get(i).getText().trim().equals(PropsUtil.getDataPropertyValue("BudgetSummery_Loc_EditCategory")))
		{
			editPerAmt=createBudget.BudgetSummery_SpendPer().get(i).getText().trim();
			editSpendAmt=createBudget.BudgetSummery_LeftAmt().get(i).getText().trim();
			break;
		}
	}
	Assert.assertEquals(editPerAmt, 
			PropsUtil.getDataPropertyValue("BudgetSummery_Loc_EditCategorySpend"), "category spening amount is not updated");
	Assert.assertEquals(editSpendAmt, 
			PropsUtil.getDataPropertyValue("BudgetSummery_Loc_EditCategoryLeft"), "category left amount is not updated");
	
	}
	
	@Test(description="AT-97040:verify add unselected category in step 3", groups = {
	"DesktopBrowsers,sanity" }, priority = 11, enabled = true,dependsOnMethods="verifySummeryIncomeDetails")
	public void verifyAddUnselectedCat()
	{
		createBudget.unselectedCategorySelect(Integer.parseInt(PropsUtil.getDataPropertyValue("BudgetSummery_Loc_AddCategoryIndex")),
				PropsUtil.getDataPropertyValue("BudgetSummery_Loc_AddUnselectedCategory"));
		SeleniumUtil.waitForPageToLoad();
		System.out.println(createBudget.BudgetSummery_AddBudgetAmtField().getAttribute("value"));
	Assert.assertEquals(createBudget.BudgetSummery_AddBudgetAmtField().getAttribute("value"),
			PropsUtil.getDataPropertyValue("BudgetSummery_Loc_AddUnselectedCategoryAmount"), "unselected category is not updated");
	}
	@Test(description="AT-97037:verify budget amount is updated when added new transaction", groups = {
	"DesktopBrowsers,sanity" }, priority = 12, enabled = true,dependsOnMethods="verifySummeryIncomeDetails")
	public void verifyEditdBudgetamtByAddingTxn()
	{//Verify that when user click on the Category in category details page, under this month's transaction chat user must be able to edit the transaction which was saved previously and save the latest change successfully

		d.navigate().refresh();
		SeleniumUtil.waitForPageToLoad();
		PageParser.forceNavigate("Transaction", d);
		SeleniumUtil.waitForPageToLoad();
		add_Manual.clickMTLink();
		SeleniumUtil.waitForPageToLoad();
	    add_Manual.createOneTimeTransaction(PropsUtil.getDataPropertyValue("Budget_MTAmount1"), 
				PropsUtil.getDataPropertyValue("Budget_MTDescription1"), 
				PropsUtil.getDataPropertyValue("Budget_MTTxnType1"),
				PropsUtil.getDataPropertyValue("Budget_MTAccounts1"), 
				PropsUtil.getDataPropertyValue("Budget_MTFrequency1"), Integer.parseInt(PropsUtil.getDataPropertyValue("Budget_MTCsheduledDate")), 
				PropsUtil.getDataPropertyValue("Budget_MTCategory1"),
				PropsUtil.getDataPropertyValue("Budget_MTNote1"), 
				PropsUtil.getDataPropertyValue("Budget_MTCheck1"));
		SeleniumUtil.waitForPageToLoad();
		PageParser.forceNavigate("Budget", d);
		SeleniumUtil.waitForPageToLoad(7000);
		String editPerAmt=null;
		String editSpendAmt=null;
		int catSize=createBudget.BudgetSummery_CatName().size();
		for(int i=0;i<catSize;i++)
		{
			if(createBudget.BudgetSummery_CatName().get(i).getText().trim().equals(PropsUtil.getDataPropertyValue("Budget_MTCategory1")))
			{
				editPerAmt=createBudget.BudgetSummery_SpendPer().get(i).getText().trim();
				editSpendAmt=createBudget.BudgetSummery_LeftAmt().get(i).getText().trim();
				break;
			}
		}
		Assert.assertEquals(editPerAmt, 
				PropsUtil.getDataPropertyValue("BudgetSummery_Loc_AddTxnCategorySpend"), "category spening amount is not updated");
		Assert.assertEquals(editSpendAmt, 
				PropsUtil.getDataPropertyValue("BudgetSummery_Loc_AddTxnCategoryLeft"), "category left amount is not updated");
		
		
	}
	
	@Test(description="AT-97043:verify txn is updated when txn is updated", groups = {
	"DesktopBrowsers,sanity" }, priority = 13, enabled = true,dependsOnMethods="verifySummeryIncomeDetails")
	public void verifyEditdBudgetTxn()
	{//Verify that when user click on the Category in category details page, under this month's transaction chat user must be able to edit the transaction which was saved previously and save the latest change successfully
		txnBudget.clickBugtTxnRow(PropsUtil.getDataPropertyValue("Budget_EditBudgetTransaction"), 0);
		txnBudget.editBudgetTxn();
		SeleniumUtil.waitForPageToLoad();
		txnBudget.clickBugtTxnRow(0);
		SeleniumUtil.waitForPageToLoad();
		Assert.assertEquals(txnBudget.budgetDescription().getAttribute("value"), PropsUtil.getDataPropertyValue("TraBudgetDescValue1"),"txn decription is not diplsyed");

	}
	@Test(description="AT-97044:verify add category from budget setting", groups = {
	"DesktopBrowsers,sanity" }, priority = 14, enabled = true,dependsOnMethods="verifySummeryIncomeDetails")
	public void verifyAddCategoryToBudget()
	{
		//Verify that when user try to add a new category in add category screen the category must be added successfully based on the amount provided by the user while adding and it must be considered for calculation 

		d.navigate().refresh();
		SeleniumUtil.waitForPageToLoad();
		
		PageParser.forceNavigate("Budget", d);
		SeleniumUtil.waitForPageToLoad();
		createBudget.addCategoryToBudget(PropsUtil.getDataPropertyValue("BudgetSummery_Loc_Setting_AddingCategory"), PropsUtil.getDataPropertyValue("BudgetSummery_Loc_Setting_AddingCategoryAmount"));
		String editPerAmt=null;
		String editSpendAmt=null;
		int catSize=createBudget.BudgetSummery_CatName().size();
		for(int i=0;i<catSize;i++)
		{
			if(createBudget.BudgetSummery_CatName().get(i).getText().trim().equals(PropsUtil.getDataPropertyValue("BudgetSummery_Loc_Setting_AddingCategory")))
			{
				editPerAmt=createBudget.BudgetSummery_SpendPer().get(i).getText().trim();
				editSpendAmt=createBudget.BudgetSummery_LeftAmt().get(i).getText().trim();
				break;
			}
		}
		Assert.assertEquals(editPerAmt, 
				PropsUtil.getDataPropertyValue("BudgetSummery_Loc_Setting_UpdatedSpendAmount"), "added category spend amount is not displayed");
		Assert.assertEquals(editSpendAmt, 
				PropsUtil.getDataPropertyValue("BudgetSummery_Loc_Setting_UpdatedLeftAmount"), "added category left amount is not displayed");
		
		}
	@Test(description="AT-97045,AT-97046:edit budget group from budget setting", groups = {
	"DesktopBrowsers,sanity" }, priority = 15, enabled = true,dependsOnMethods="verifyAddCategoryToBudget")
	public void verifyEditAccountGroup()
	{
		//Verify that when user edit the budget group and select a different account for which there was no transaction for the budgeted categories then the budgeted category amount will remain unchanged in the budget page for all the categories. 
        //Verify that when user try to edit the budgeted group, user must be able to edit the account group successfully. 

       createBudget.clickAccountgroupEdit();
       groupView.updateGroup(PropsUtil.getDataPropertyValue("BudgetSummery_Loc_Setting_UpdatedGrouAccount").split(","));
       String []ExpectedAccount= PropsUtil.getDataPropertyValue("Budget_Loc_AccountUpdateAllAccountNum").split(",");
       for(int i=0;i<ExpectedAccount.length;i++)
       {
    	   util.isAnyMatchInList(util.getWebElementsValue(createBudget.Budget_Step1_AcntsNum()), ExpectedAccount[i]);
       }

		
	}
	@Test(description="AT-97045,AT-97046:verify income summery updated when updated budget", groups = {
	"DesktopBrowsers,sanity" }, priority = 16, enabled = true,dependsOnMethods="verifyEditAccountGroup")
	public void verifyUpdatedSummeryIncomeDetails()
	{ 
	
		SeleniumUtil.click(createBudget.BudgetSummery_BackToBudget());
		Assert.assertEquals(createBudget.BudgetSummery_IncomeLeftAmount().getText().trim(), 
				PropsUtil.getDataPropertyValue("BudgetSummery_Loc_AccountUpdatedIncomeLeftAmount"), "income summery left amount is not updated");

		Assert.assertEquals(createBudget.BudgetSummery_BdgIncomeActualAmount().getText().trim(), 
				PropsUtil.getDataPropertyValue("BudgetSummery_Loc_AccountUpdatedIncomeatualAmount"), "income summery actual amount is not updated");

		Assert.assertEquals(createBudget.BudgetSummery_BdgIncomeAmount().getText().trim(), 
				PropsUtil.getDataPropertyValue("BudgetSummery_Loc_AccountUpdatedIncomeBudgetedAmount"), "income summery budgeted amount is not updated");

	}
	@Test(description="AT-97045,AT-97046:verify Spend summery updated when updated budget", groups = {
	"DesktopBrowsers,sanity" }, priority = 17, enabled = true,dependsOnMethods="verifyUpdatedSummeryIncomeDetails")
	public void verifUpdatedySummerySpendingDetails()
	{ 
		Assert.assertEquals(createBudget.BudgetSummery_SBLeftAmount().getText().trim(), 
				PropsUtil.getDataPropertyValue("BudgetSummery_Loc_AccountUpdateBSLeftAmount"), "spending summery left amount is not updated");

		Assert.assertEquals(createBudget.BudgetSummery_BdgSBActualAmount().getText().trim(), 
				PropsUtil.getDataPropertyValue("BudgetSummery_Loc_AccountUpdateBSActualAmount"), "spending summery actual amount is not updated");

		Assert.assertEquals(createBudget.BudgetSummery_BdgSBAmount().getText().trim(), 
				PropsUtil.getDataPropertyValue("BudgetSummery_Loc_AccountUpdateBSBudgetedAmount"), "spending summery budgeted amount is not updated");
        
	}
	@Test(description="AT-97045,AT-97046:verify category summery updated when updated budget", groups = {
	"DesktopBrowsers,sanity" }, priority = 18, enabled = true,dependsOnMethods="verifyEditAccountGroup")
	public void verifyUpdatedSummeryCatgoryName()
	{ 
		util.assertExpectedActualList(
				util.getWebElementsValue(createBudget.BudgetSummery_CatName()), 
				PropsUtil.getDataPropertyValue("BudgetSummery_Loc_AccountUpdateBudgetCatgeoryList"), 
				"budget summery category is not updated");
		util.assertExpectedActualAmountList(util.getWebElementsValue(createBudget.BudgetSummery_SpendPer()),
				PropsUtil.getDataPropertyValue("BudgetSummery_Loc_AccountUpdateBudgetCatgeorySpend"), 
				"budget summery category spending is not updated");
		util.assertExpectedActualAmountList(util.getWebElementsValue(createBudget.BudgetSummery_LeftAmt()), 
				PropsUtil.getDataPropertyValue("BudgetSummery_Loc_AccountUpdateBudgetCatgeoryLeft"), 
				"budget summery category left amount is not updated");
	}
}
