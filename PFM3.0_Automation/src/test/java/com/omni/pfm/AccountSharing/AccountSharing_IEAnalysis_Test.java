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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import com.omni.pfm.PageProcessor.PageParser;
import com.omni.pfm.pages.AccountAddition.AccountAddition;
import com.omni.pfm.pages.Login.LoginPage_SAML3;
import com.omni.pfm.pages.TransactionEnhancement1.Add_Manual_Transaction_Loc;
import com.omni.pfm.rest.ysl;
import com.omni.pfm.testBase.TestBase;
import com.omni.pfm.utility.PropsUtil;
import com.omni.pfm.utility.SeleniumUtil;


public class AccountSharing_IEAnalysis_Test extends TestBase{
	Logger logger=LoggerFactory.getLogger(AccountSharing_IEDropDown_Test.class);
	LoginPage_SAML3 SAMlLogin;
	public static String advisorRef;
	public static String investorName;
	public static String advisorName;
	AccountAddition accountAdd;
	AccountSharing_Loc accountSharing;
	Add_Manual_Transaction_Loc addManualTransaction;

	@BeforeClass()
	public void init() {
		doInitialization("Accounts");
		TestBase.tc = TestBase.extent.startTest("Login", "Login");
		TestBase.test.appendChild(TestBase.tc);
		SAMlLogin=new LoginPage_SAML3();
		accountAdd=new AccountAddition();
		accountSharing=new AccountSharing_Loc(d);
		addManualTransaction=new Add_Manual_Transaction_Loc(d);
	}

	@Test(description="creating advisor", priority=1,enabled=true)
	public void creatingAdvisor() {
		advisorRef=SAMlLogin.getRefrenceId();
		advisorName=SAMlLogin.createAdvisor2(d, investorName, advisorRef,"10003700");
	}

	@Test(description="creating investor", priority=2,enabled=true)
	public void creatingAndLoginToInvestor() {

		investorName=SAMlLogin.getInvestorUserName();
		SAMlLogin.createInvestor2(d, advisorName, investorName, null, null);
	}

	@Test(description="aggregating accounts to investor.", priority=3,enabled=true)
	public void loginToInvestor() throws InterruptedException, AWTException {

		SeleniumUtil.waitForPageToLoad();
		accountAdd.linkAccount();
		accountAdd.addAggregatedAccount("ihjuly1.site16441.1", "site16441.1");
		PageParser.forceNavigate("ManageSharing", d);
		SeleniumUtil.waitForPageToLoad();
	}
	
	@Test(description="sharing accounts with balance only access with advisor.", priority=4,enabled=true)
	public void sharingAcntWithAdvisor() {

		accountSharing.sharingWithBalanceOnly();
		SeleniumUtil.waitForPageToLoad(3000);
		SeleniumUtil.click(accountSharing.AccountShareSave());
	}

	
	@Test(description="AT-71819,AT-71823:Verify the below message when user do not have any aggregated account and only have view balance shared account in Expense analysis in investor view.", priority=5,enabled=true)
	public void loginToAdvisor() {
		SAMlLogin.login2(d, advisorName, investorName,"10003700");
		SeleniumUtil.waitForPageToLoad(3000);
		PageParser.forceNavigate("Expense", d);
	    SeleniumUtil.waitForPageToLoad(3000);
	
	    logger.info("Verify that Expense Analysis should not include data shared by Investor or Advisor with Balance only access when user has aggregated accounts for both investor and advisor view.");
	    Assert.assertEquals(accountSharing.accessNotGrantedMsg().getText().trim(), PropsUtil.getDataPropertyValue("AccessNotGrantedMsg").trim());
	    Assert.assertEquals(accountSharing.accessNotGrantedDiscription().getText().trim(), PropsUtil.getDataPropertyValue("AccessNotGrantedDiscription").trim());
	}
	
	@Test(description="Login To Investor", priority=6,enabled=true)
	public void reloginToInvestor() {
	
		SAMlLogin.login2(d, investorName, null,"10003700");
		SeleniumUtil.waitForPageToLoad(3000);
		PageParser.forceNavigate("Expense", d);
	    SeleniumUtil.waitForPageToLoad(3000);
	    accountSharing.IncomeExpenseFtueCompletion();
	}
	
	@Test(description="AT-71827,AT-71840:Verify that income analysis graph should display only my accounts data when user has selected my accounts checkbox for investor view.", priority=7,enabled=true)
	public void validatingInvExpenseMonthlyAmount() {
		accountSharing.validatecurrentMonthAmount("InvMonthlyAmountValue1");
		accountSharing.verifyInvMonthlyAmountValues("InvMonthlyAmountValue"); 
	}
	
	@Test(description="verifying three months average value in investor", priority=8,enabled=true)
	public void validatingInvExpenseAvgValue() {
		accountSharing.verifyLast3MonthAverageValue(); 	
	}
	
	@Test(description="Verify that Top expense of the month for expense analysis should display only my accounts data when user has selected my accounts checkbox for investor view.", priority=9,enabled=true)
	public void validateInvTopExpenseData() {
		accountSharing.verifyInvTopFiveExpenseLabel();
	}
	
	@Test(description="verify HLC and MLC included with investor own's account.", priority=10,enabled=true)
	public void verifyingInvAllCategoriesIncluded() {
		
		SeleniumUtil.click(accountSharing.selectMonth().get(0));
		SeleniumUtil.waitForPageToLoad(3000);
		accountSharing.verifyInvAllCategoriesIncluded();
	}
	
	@Test(description="adding accounts to advisor" ,priority=11,enabled=true)
	public void reloginToAdvisor() throws AWTException, InterruptedException {
	SAMlLogin.login2(d, advisorName, investorName,"10003600");
	SeleniumUtil.waitForPageToLoad();
	accountSharing.getStartedFL();
	accountAdd.addAggregatedAccount("ihjuly1.site16441.2", "site16441.2");
	SeleniumUtil.waitForPageToLoad();
	}
	
	
	@Test(description="AT-71827,AT-71840:Verify that income analysis graph should display only my accounts data when user has selected my accounts checkbox for advisor view.", priority=12,enabled=true)
	public void validatingAdvExpenseMonthlyAmount() {
	
		SAMlLogin.login2(d, advisorName,null,"10003700");
		PageParser.forceNavigate("Expense", d);
	    SeleniumUtil.waitForPageToLoad(3000);
		
		accountSharing.validatecurrentMonthAmount("InvMonthlyAmountValue2");
		accountSharing.verifyAdvMonthlyAmountValues(); 
	}
	
	@Test(description="verify 3 months average value for advisor", priority=13,enabled=true)
	public void validatingAdvExpenseAvgValue() {
		accountSharing.verifyLast3MonthAverageValue(); 	
	}
	
	@Test(description="AT-71833:Verify that Top expense of the month for expense analysis should display only my accounts data when user has selected my accounts checkbox for advisor view.", priority=14,enabled=true)
	public void validateAdvTopExpenseData() {
		accountSharing.verifyAdvTopFiveExpenseLabel();
	}
	
	@Test(description="verify all HLC and MLCof advisor with own account", priority=15,enabled=true)
	public void verifyingAdvAllCategoriesIncluded() {
		
		SeleniumUtil.click(accountSharing.selectMonth().get(0));
	    SeleniumUtil.waitForPageToLoad(3000);
		accountSharing.verifyAdvAllCategoriesIncluded();
	}
	
	
	@Test(description="AT-71871,AT-71872:Verify user can edit HLC and able to see the edited changes in Expense analysis for both ivestor and advisor view.", priority=16,enabled=true)
	public void addingManualTransactionToAdvisor() {
		
		PageParser.forceNavigate("Transaction", d);
		SeleniumUtil.waitForPageToLoad(3000);
		SeleniumUtil.click(addManualTransaction.addManualIcon());
		addManualTransaction.createTransactionwithdeposit("20000", "Testing", 0, -1, 5, 4, 1,0);
	}
	

	@Test(description="sharing accounts advisor to investor", priority=17,enabled=true)
	public void advisorToInvesorSharing() throws InterruptedException, AWTException, JSONException {
		
		String samlResponse= LoginPage_SAML3.loginResponse(d,advisorName,null);
		ysl.shareAdvisorAccount(samlResponse, advisorName, investorName);	
			
	}
	
	@Test(description="AT-71838:Verify that the Month level data for income analysis should include shared accounts data point when user has selected all accounts checkbox in investor view.", priority=18,enabled=true)
	public void validatingInvExpenseMonthlyAmountPostSharing() {
	    SAMlLogin.login2(d, investorName,null,"10003700");

		PageParser.forceNavigate("Expense", d);
	    SeleniumUtil.waitForPageToLoad(3000);
		
		accountSharing.validatecurrentMonthAmount("InvMonthlyAmountValue3");
		accountSharing.verifyInvMonthlyAmountValues("MonthlyAmountValueAfterSharing"); 
	}
	
	@Test(description="AT-71837:Verify that the Top Expense of the month for Expense analysis should include shared accounts when user has selected all accounts checkbox in Investor view.", priority=19,enabled=true)
	public void validateInvTopFiveExpenseAfterSharing() {
		accountSharing.TopFiveExpenseLabelPostSharing();
	}
	
	@Test(description="Verify all HLC and MLC included After sharing in Adviosr view", priority=20,enabled=true)
	public void verifyingInvAllCategoriesIncludedPostSharing() {
		
		SeleniumUtil.click(accountSharing.selectMonth().get(0));
		SeleniumUtil.waitForPageToLoad(3000);
		accountSharing.verifyInvAllCategoriesIncludedAfterSharing();
	}
	
	@Test(description="sharing accounts with full access to advisor.", priority=21)
	public void sharingAllAcntWithAdvisor() {
		PageParser.forceNavigate("ManageSharing", d);
		SeleniumUtil.waitForPageToLoad();
		
		accountSharing.SharingAllAcntsFullAccess();
		SeleniumUtil.waitForPageToLoad(3000);
		SeleniumUtil.click(accountSharing.AccountShareSave());
	}
	
	
	@Test(description="AT-71831:Verify that expense analysis graph should include all shared accounts data points when user has selected all accounts checkbox in advisor view.", priority=22,enabled=true)
	public void reLoginToAdvisor() {
	   SAMlLogin.login2(d, advisorName, investorName, "10003700");
		PageParser.forceNavigate("Expense", d);
	    SeleniumUtil.waitForPageToLoad();
	}
	
	@Test(description="AT-71838:Verify that the Month level data for income analysis should include shared accounts data point when user has selected all accounts checkbox in advisor view.", priority=23,enabled=true)
	public void validatingAdvExpenseMonthlyAmountPostSharing() {
		accountSharing.validatecurrentMonthAmount1();
		accountSharing.verifyInvMonthlyAmountValues("MonthlyAmountValueAfterSharing"); 
	}
	
	@Test(description="AT-71837:Verify that the Top Expense of the month for Expense analysis should include shared accounts when user has selected all accounts checkbox in advisor view.", priority=24,enabled=true)
	public void validateAdvTopFiveExpenseAfterSharing() {
		accountSharing.TopFiveExpenseLabelPostSharing();
	}
	
	@Test(description="AT-71848,AT-71850:Verify that all the shared accounts transaction data with category (HLC) should be included in expense by category page for advisor view.", priority=25,enabled=true)
	public void verifyingAdvAllCategoriesIncludedPostSharing() {
		
		SeleniumUtil.click(accountSharing.selectMonth().get(0));
		SeleniumUtil.waitForPageToLoad(3000);
		accountSharing.verifyInvAllCategoriesIncludedAfterSharing();
	}
	
	@Test(description="AT-71819,AT-71823:Verify the below message when user do not have any aggregated account and only have view balance shared account in Expense analysis in investor view.", priority=26,enabled=true)
	public void unsharingAcnt() {
		SAMlLogin.login2(d, investorName, null,"10003700");
		PageParser.forceNavigate("ManageSharing", d);
		SeleniumUtil.waitForPageToLoad();
		
		accountSharing.unsharingAcnt();
		SeleniumUtil.waitForPageToLoad(3000);
		SeleniumUtil.click(accountSharing.AccountShareSave());
	}
	
	@Test(description="AT-71819,AT-71823:Verify the below message when user do not have any aggregated account and only have view balance shared account in Expense analysis in investor view.", priority=27,enabled=true)
	public void verifyingDataInAdv() {
		SAMlLogin.login2(d, advisorName, investorName,"10003700");
		PageParser.forceNavigate("Expense", d);
		SeleniumUtil.waitForPageToLoad(3000);
		accountSharing.verifyInvMonthlyAmountValues("MonthlyAmountValueAfterUnSharing"); 
	}
	
	

}