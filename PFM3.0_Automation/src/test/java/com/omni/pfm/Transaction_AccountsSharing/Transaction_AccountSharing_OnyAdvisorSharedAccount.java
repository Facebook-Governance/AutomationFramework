/**
* Copyright (c) 2018 Yodlee Inc. All Rights Reserved.
*
* This software is the confidential and proprietary information of
* Yodlee, Inc. Use is subject to license terms.
*
* @author Renuka K
*/
package com.omni.pfm.Transaction_AccountsSharing;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.omni.pfm.PageProcessor.PageParser;
import com.omni.pfm.pages.AccountAddition.AccountAddition;
import com.omni.pfm.pages.Login.LoginPage;
import com.omni.pfm.pages.Login.LoginPage_SAML3;
import com.omni.pfm.pages.TransactionEnhancement1.Add_Manual_Transaction_Loc;
import com.omni.pfm.pages.TransactionEnhancement1.Aggregated_Transaction_Details_Loc;
import com.omni.pfm.pages.TransactionEnhancement1.DownloadTrans_Loc;
import com.omni.pfm.pages.TransactionEnhancement1.Transaction_AccountDropDown_Loc;
import com.omni.pfm.pages.TransactionEnhancement1.Transaction_Layout_Loc;
import com.omni.pfm.pages.TransactionEnhancement1.Transaction_MarkAsPaid_Loc;
import com.omni.pfm.pages.TransactionEnhancement1.Transaction_Search_Loc;
import com.omni.pfm.pages.TransactionEnhancement1.Transaction_Tag_Loc;
import com.omni.pfm.rest.ysl;
import com.omni.pfm.testBase.TestBase;
import com.omni.pfm.utility.PropsUtil;
import com.omni.pfm.utility.SeleniumUtil;

public class Transaction_AccountSharing_OnyAdvisorSharedAccount extends TestBase {
	public static String TransactionloginName;

	private static final Logger logger = LoggerFactory.getLogger(Transaction_AccountSharing_OnyAdvisorSharedAccount.class);
	LoginPage login;
	AccountAddition acccuntAdd;
	LoginPage_SAML3 SAMLlogin;
    public static String advisorUsername;
    public static String advisorUsername2;
	public static String InvesterName1;
	public static String InvesterName2;
	public static String InvesterName3;
	public static String addvisorRef1;
	public static String addvisorRef2;
	public static String addvisorRef3;
	public static String investerSharedAccountToAdvisor1;
	public static String investerSharedAccountToAdvisor2;
	public static String investerSharedAccountToAdvisor3;
	Transaction_AccountDropDown_Loc accountDrapDown;
	Add_Manual_Transaction_Loc  add_Manual;
	Transaction_Search_Loc search;
	Transaction_Layout_Loc layout;
	Aggregated_Transaction_Details_Loc aggre_Tra;
	Transaction_Tag_Loc tag;
	DownloadTrans_Loc feature;
	Transaction_MarkAsPaid_Loc markASPaid;
	
	@BeforeClass

	public void testInit() throws Exception {
		
		
		
		 doInitialization("Transaction Login");
	        
	        TestBase.tc = TestBase.extent.startTest("Transaction", "Login");
	        TestBase.test.appendChild(TestBase.tc);
	        login=new LoginPage();
	        acccuntAdd=new AccountAddition();
	        SAMLlogin=new LoginPage_SAML3();
	        accountDrapDown=new Transaction_AccountDropDown_Loc(d);
		    add_Manual=new Add_Manual_Transaction_Loc(d);
		    search=new Transaction_Search_Loc(d);
		    layout=new Transaction_Layout_Loc(d);
		    aggre_Tra=new Aggregated_Transaction_Details_Loc(d);
		    tag=new Transaction_Tag_Loc(d);
		    feature=new   DownloadTrans_Loc(d);
		    markASPaid=new Transaction_MarkAsPaid_Loc(d);
	}
	@Test(description = "AT-70785:verify Addvisor and investor created sucessfully", priority = 1)
	public void login() throws Exception
	{	
		logger.info("Get the unique reference id");
		addvisorRef1=SAMLlogin.getRefrenceId();
		logger.info("Get the unique investor Name");
		InvesterName1= SAMLlogin.getInvestorUserName();
		logger.info("create advisor with extra param segmentName=advisor&IID=Investorname ");
		
		advisorUsername= SAMLlogin.createAdvisor(d,InvesterName1 , addvisorRef1,"10003600");
		SeleniumUtil.waitForPageToLoad();
		acccuntAdd.linkAccountFastLink();
	    acccuntAdd.addAggregatedAccount("advisorinvestor1.site16441.2", "site16441.2");
	   
       
		SeleniumUtil.waitForPageToLoad(5000);
		boolean expectedInvestor=false;
		if(advisorUsername!=null)
		{
		logger.info("create investor and prepop account to investor");
		SAMLlogin.createInvestor(d, advisorUsername, InvesterName1,"TransactionFilter1.site16441.1","site16441.1");
		
		expectedInvestor=true;
		}
		
		Assert.assertTrue(expectedInvestor,"advisor and investor is not created successfully");
		
		
	}
	
	@Test(description = "AT-70785:Verify the investor shared accouts Should not displayed in the manual transaction accounts dropdown in advisor view", priority = 2)
	public void advisor1SharedAccount() throws Exception
	{//we have add after api automation
		logger.info("share the accounts to investor");
		logger.info("login to advisor with extra param segmentName=advisor&IID=Investorname ");
		String samlResponse=SAMLlogin.loginResponse(d,advisorUsername,InvesterName1);
		ysl.shareAdvisorAccount(samlResponse,advisorUsername,InvesterName1);
		
	}
	
	@Test(description = "AT-70785", priority = 3)
	public void verifyMarkAspaidForIAdvisorAggregatedAccount_InAdvisorView() throws Exception
	{	SAMLlogin.login(d, advisorUsername,InvesterName1,"10003700");
		PageParser.forceNavigate("Transaction", d);
		SeleniumUtil.waitForPageToLoad(5000);
		if(accountDrapDown.allTransactionAccount().get(0).getText().equals(""))
		{
			SeleniumUtil.click(search.ProjectedExapdIcon());
	    	   SeleniumUtil.waitForPageToLoad(2000);
		}
		boolean actual=false;
		if(markASPaid.MarkAsPaid().size()==0)
		{
			actual=true;
		}
		Assert.assertTrue(actual);
	}
	@Test(description = "AT-69149", priority = 4)
	public void verifyAddManualTransactionHiddenWhenInvestorOnlySharedAcccount_InInvestorView() throws Exception
	{
	   SAMLlogin.login(d, InvesterName1,null,"10003700");
	   PageParser.forceNavigate("Transaction", d);
		SeleniumUtil.waitForPageToLoad(5000);
		Assert.assertTrue(accountDrapDown.AccountSharingAddManual().getAttribute("class").contains("hide"),"verify add manula transaction should be disabled when advisor only Shared Account");
	}
	
	@Test(description = "AT-70781", priority = 5)
	public void verifyMarkAsPaid_InInvestorView() throws Exception
	{
		PageParser.forceNavigate("Transaction", d);
		SeleniumUtil.waitForPageToLoad(5000);
		if(accountDrapDown.allTransactionAccount().get(0).getText().equals(""))
		{
			SeleniumUtil.click(search.ProjectedExapdIcon());
	    	   SeleniumUtil.waitForPageToLoad(2000);
		}
		boolean actual=false;
		if(markASPaid.MarkAsPaid().size()==0)
		{
			actual=true;
		}
		Assert.assertTrue(actual);
	}
	
	@Test(description = "AT-62060,AT-62069", priority = 6)
	public void verifyOnlyAdvisorSharedAccountLabel_InInvestorView() throws Exception
	{
		SeleniumUtil.click(accountDrapDown.accountFilterLink());
		SeleniumUtil.waitForPageToLoad(1000);
		Assert.assertEquals(accountDrapDown.allAccountLable().get(0).getText().trim(), PropsUtil.getDataPropertyValue("TransactionAdvisorSharedAccount"), "only advisor shared accounts label should displayed");
	}
	
	@Test(description = "AT-62060", priority = 7)
	public void verifyMyAccountAndAllAccountShouldNotDisplayed_InInvestorView() throws Exception
	{
		boolean myAccountSize=false;
		if(accountDrapDown.myOrAdvisorSharedAccountLable().size()==0)
		{
			myAccountSize=true;
			
		}
		Assert.assertTrue(myAccountSize,"my account and all accounts should not dispalyed when investor has only shared account ");
	}
}
