/**
* Copyright (c) 2018 Yodlee Inc. All Rights Reserved.
*
* This software is the confidential and proprietary information of
* Yodlee, Inc. Use is subject to license terms.
*
* @author sakshi Jain
*/

package com.omni.pfm.AccountSharing;

import java.awt.AWTException;

import org.json.JSONException;
import org.junit.Assert;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.omni.pfm.Accounts.AccountsViewByFI_Loc;
import com.omni.pfm.Accounts.Accounts_ViewByGroup_Loc;
import com.omni.pfm.PageProcessor.PageParser;
import com.omni.pfm.pages.AccountAddition.AccountAddition;
import com.omni.pfm.pages.BudgetEnhancement.Budget_Income_And_Bill_Summery_Loc;
import com.omni.pfm.pages.BudgetEnhancement.Budget_Landing_Page_Loc;
import com.omni.pfm.pages.BudgetEnhancement.Budget_Select_Accounts_Loc;
import com.omni.pfm.pages.Login.LoginPage_SAML3;
import com.omni.pfm.pages.TransactionEnhancement1.Add_Manual_Transaction_Loc;
import com.omni.pfm.pages.TransactionEnhancement1.Transaction_AddAndEditTags_Loc;
import com.omni.pfm.rest.ysl;
import com.omni.pfm.testBase.TestBase;
import com.omni.pfm.utility.PropsUtil;
import com.omni.pfm.utility.SeleniumUtil;

public class AccountSharing_Budget_Test extends TestBase{
	Logger logger=LoggerFactory.getLogger(AccountSharing_Budget_Test.class); 
	LoginPage_SAML3 SAMlLogin;
	public static String advisorRef;
	public static String investorName;
	public static String advisorName;
	AccountAddition accountAdd;
	AccountSharing_Loc accountSharing;
	Budget_Landing_Page_Loc budget_LandingPage;
	Budget_Select_Accounts_Loc Budget_Screen2;
	Transaction_AddAndEditTags_Loc editingTransac;
	Budget_Income_And_Bill_Summery_Loc BudgetLastPage;
	Add_Manual_Transaction_Loc addManualTransac;
	Accounts_ViewByGroup_Loc groupView;
	SeleniumUtil util;
	AccountsViewByFI_Loc viewByFI;

	@BeforeClass()
	public void init() {
		doInitialization("Accounts");
		TestBase.tc = TestBase.extent.startTest("Login", "Login");
		TestBase.test.appendChild(TestBase.tc);
		SAMlLogin=new LoginPage_SAML3();
		accountAdd=new AccountAddition();
		accountSharing=new AccountSharing_Loc(d);
		budget_LandingPage =new Budget_Landing_Page_Loc(d);
		Budget_Screen2=new Budget_Select_Accounts_Loc(d);
		editingTransac= new Transaction_AddAndEditTags_Loc(d);
		BudgetLastPage= new Budget_Income_And_Bill_Summery_Loc(d);
		addManualTransac=new Add_Manual_Transaction_Loc(d);
		groupView=new Accounts_ViewByGroup_Loc(d);
		util=new SeleniumUtil();
		viewByFI=new AccountsViewByFI_Loc(d);
	}

	@Test(description="creating Advisor", priority=1,enabled=true)
	public void creatingAdvisor() {
		advisorRef=SAMlLogin.getRefrenceId();
		advisorName=SAMlLogin.createAdvisor2(d, investorName, advisorRef,"10003600");
		System.out.println("Advisor Name:" +advisorName);
		SeleniumUtil.waitForPageToLoad();
	}

	@Test(description="AT-67567:The investor should be allowed to aggregate his accounts.",dependsOnMethods="creatingAdvisor", priority=2,enabled=true)
	public void addAcntToAdvisor() throws InterruptedException, AWTException {

		accountSharing.getStartedFL();
		accountAdd.addAggregatedAccount1("sarv10.site16441.2", "site16441.2");
		SeleniumUtil.waitForPageToLoad();
	}

	@Test(description="creating investor", priority=3,dependsOnMethods="creatingAdvisor",enabled=true)
	public void creatingAndLoginToInvestor() {

		investorName=SAMlLogin.getInvestorUserName();
		SAMlLogin.createInvestor2(d, advisorName, investorName, null, null);
	}

	@Test(description="AT-67567:The investor should be allowed to aggregate his accounts.", priority=4,dependsOnMethods= {"creatingAdvisor","creatingAndLoginToInvestor"},enabled=true)
	public void advisorToInvesorSharing() throws InterruptedException, AWTException, JSONException {

		String samlResponse= LoginPage_SAML3.loginResponse(d,advisorName,null);
		ysl.shareAdvisorAccount(samlResponse, advisorName, investorName);
	}

	@Test(description="AT-72805:When the user launches budget for the first time, it should not show him the desert screen if he has valid linked accounts.", dependsOnMethods="creatingAndLoginToInvestor",priority=5,enabled=true)
	public void BudgetStep1() throws InterruptedException, AWTException{

		SAMlLogin.login2(d,investorName,null, "10003700");
		PageParser.forceNavigate("Budget", d);
		SeleniumUtil.waitForPageToLoad();

		SeleniumUtil.click(budget_LandingPage.getStartedButton());
		SeleniumUtil.click(Budget_Screen2.NextButton2());
		Assert.assertTrue(Budget_Screen2.SuccessMessage().isDisplayed());	
	}

	@Test(description=" AT-72808,AT-72809:The user should be allowed to create a budget if he has his own accounts or shared accounts or both.",dependsOnMethods="BudgetStep1", priority=6,enabled=true)
	public void BudgetStep2() throws InterruptedException, AWTException{

		SeleniumUtil.waitForPageToLoad(3000);
		SeleniumUtil.click(Budget_Screen2.NextButton());
		SeleniumUtil.waitForPageToLoad(2000);
		SeleniumUtil.click(Budget_Screen2.NextButton());
		Assert.assertTrue(Budget_Screen2.header().isDisplayed());
	}


	@Test(description="Edited shared transaction details should display to investor.",dependsOnMethods="creatingAdvisor", priority=7,enabled=true)
	public void loginToAdv() throws InterruptedException, AWTException{

		SAMlLogin.login2(d, advisorName, investorName, "10003700");
		PageParser.forceNavigate("Transaction", d);
		SeleniumUtil.waitForPageToLoad();
		editingTransac.editingExistingTranDiscription();
	}

	@Test(description="AT-72815:If the user adds a transaction to an account, it should reflect on the budget.",dependsOnMethods="loginToAdv", priority=8,enabled=true)
	public void AddingNewTransaction() throws InterruptedException, AWTException{
		PageParser.forceNavigate("Transaction", d);
		SeleniumUtil.waitForPageToLoad(3000);
		SeleniumUtil.click(addManualTransac.addManualIcon());
		addManualTransac.createTransactionwithdeposit("2000", "Trip", 5, -1, 5, 4, 1,0);
	}


	@Test(description="AT-72814:If the advisor closes his shared accounts, it should get deleted from the Investor's budget.",dependsOnMethods="loginToAdv", priority=9,enabled=true)
	public void closingAnAcnt() throws InterruptedException, AWTException{
		PageParser.forceNavigate("AccountSettings", d);
		SeleniumUtil.waitForPageToLoad();

		SeleniumUtil.click(accountSharing.settingAtAcntLevel().get(3));
		SeleniumUtil.waitForPageToLoad(2000);
		SeleniumUtil.click(accountSharing.closeActnOption());
		SeleniumUtil.waitForPageToLoad();
		SeleniumUtil.click(accountSharing.clsAccConfirmBtn());
		SeleniumUtil.waitForPageToLoad();
	}


	@Test(description="AT-72811:The user should be allowed to delete any account from his group.",dependsOnMethods="loginToAdv", priority=10,enabled=true)
	public void deletingAnAcnt() throws InterruptedException, AWTException{

		SeleniumUtil.click(accountSharing.settingAtAcntLevel().get(4));
		SeleniumUtil.waitForPageToLoad(3000);
		accountSharing.deletingAcnt();
		SeleniumUtil.waitForPageToLoad(2000);
		Assert.assertTrue(editingTransac.editedSuccessMsg().isDisplayed());
	}


	@Test(description="AT-72815:If the advisor deletes his shared accounts, it should get deleted from the Investor's budget.",dependsOnMethods="creatingAndLoginToInvestor", priority=11,enabled=true)
	public void loginToInv() throws InterruptedException, AWTException{

		SAMlLogin.login2(d,investorName,null, "10003700");
		SeleniumUtil.waitForPageToLoad();
		PageParser.forceNavigate("AccountsPage", d);
		SeleniumUtil.waitForPageToLoad();
		Assert.assertTrue(accountSharing.ClosedAcntSymbol().isDisplayed());
		 util.assertExpectedActualList(viewByFI.returnAccountNumbers(PropsUtil.getDataPropertyValue("AcntGroupsAmountSize")),
				   PropsUtil.getDataPropertyValue("ClosedAcntName"), "all 3 type LOC accounts Number is not displayed");

		  util.assertTrue(util.getWebElementsValue(viewByFI.AccountFI_AccountType_AccountBal(PropsUtil.getDataPropertyValue("AcntGroupsAmountSize"))),
				   PropsUtil.getDataPropertyValue("ClosedAcntBalance"));
	}

	@Test(description="AT-72817:If the user edits a category, it should reflect on the user's budget.",dependsOnMethods="loginToInv", priority=12,enabled=true)
	public void verifyEditedCategory() throws InterruptedException, AWTException{
		PageParser.forceNavigate("Budget", d);
		SeleniumUtil.waitForPageToLoad();

		SeleniumUtil.click(BudgetLastPage.getCategory().get(0));
		Assert.assertEquals(BudgetLastPage.transactionName().get(0).getText().trim(), PropsUtil.getDataPropertyValue("NewAddedTransaction").trim());
	}
}
