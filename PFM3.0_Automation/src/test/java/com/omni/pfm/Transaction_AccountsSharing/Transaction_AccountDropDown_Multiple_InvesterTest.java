/*******************************************************************************
 * Copyright (c) 2018 Yodlee Inc. All Rights Reserved.
 * 
 * This software is the confidential and proprietary information of
 * Yodlee, Inc. Use is subject to license terms.
 * 
 * @author
 ******************************************************************************/
package com.omni.pfm.Transaction_AccountsSharing;

import java.awt.AWTException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.omni.pfm.PageProcessor.PageParser;
import com.omni.pfm.pages.AccountAddition.AccountAddition;
import com.omni.pfm.pages.Login.LoginPage;
import com.omni.pfm.pages.Login.LoginPage_SAML3;
import com.omni.pfm.pages.TransactionEnhancement1.Transaction_AccountDropDown_Loc;
import com.omni.pfm.rest.ysl;
import com.omni.pfm.testBase.TestBase;
import com.omni.pfm.utility.PropsUtil;
import com.omni.pfm.utility.SeleniumUtil;

public class Transaction_AccountDropDown_Multiple_InvesterTest extends TestBase {
	public static String TransactionloginName;

	private static final Logger logger = LoggerFactory.getLogger(Transaction_AccountDropDown_Multiple_InvesterTest.class);
	LoginPage login;
	AccountAddition acccuntAdd;
	LoginPage_SAML3 SAMLlogin;
    public static String advisorUsername;
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
	@BeforeClass

	public void testInit() throws Exception {
		
		
		
		 doInitialization("Transaction Login");
	        
	        TestBase.tc = TestBase.extent.startTest("Transaction", "Login");
	        TestBase.test.appendChild(TestBase.tc);
	        login=new LoginPage();
	        acccuntAdd=new AccountAddition();
	        SAMLlogin=new LoginPage_SAML3();
	        accountDrapDown=new Transaction_AccountDropDown_Loc(d);
	        createInvestorAdvser();
	        invester1SharedAccount();
	        invester2SharedAccount();
	        advisor1ToInvestor2SharedAccount();
	        advisor1ToInvestor1SharedAccount();
	}
	
	public void createInvestorAdvser() throws Exception
	{	
		addvisorRef1=SAMLlogin.getRefrenceId();
		advisorUsername= SAMLlogin.createAdvisor(d, null, addvisorRef1,"10003600");
		acccuntAdd.linkAccountFastLink();
		acccuntAdd.addAggregatedAccount("advisorinvestor3.site16441.2", "site16441.2");
		InvesterName1= SAMLlogin.getInvestorUserName();
		SeleniumUtil.waitForPageToLoad(500);
		InvesterName2= SAMLlogin.getInvestorUserName();
		SeleniumUtil.waitForPageToLoad(500);
		InvesterName3= SAMLlogin.getInvestorUserName();
		SeleniumUtil.waitForPageToLoad(500);
		SAMLlogin.createInvestor(d, advisorUsername, InvesterName1,"invester1.site16441.2","site16441.2");
		acccuntAdd.linkAccount();
		acccuntAdd.addAggregatedAccount("advisorinvestor3.site16441.1", "site16441.1");
		SeleniumUtil.waitForPageToLoad(7000);
		SAMLlogin.createInvestor(d, advisorUsername, InvesterName2,"investor2.site16441.2","site16441.2");
		acccuntAdd.linkAccount();
		acccuntAdd.addAggregatedAccount("TransactionFilter1.site16441.2", "site16441.2");
		SeleniumUtil.waitForPageToLoad(7000);
		}
	
	
	public void invester1SharedAccount() throws Exception
	{	
	SAMLlogin.login(d, InvesterName1,null,"10003700");
		
		logger.info("login to investor");
	
	SeleniumUtil.waitForPageToLoad(7000);
    PageParser.forceNavigate("ManageSharing", d);
    SeleniumUtil.waitForPageToLoad(5000);
    logger.info("click on advisor name in manage sharing");
    for(int i=0;i<accountDrapDown.AdvisorName().size();i++)
    {
    	if(accountDrapDown.AdvisorName().get(i).getText().contains(addvisorRef1))
    	{
    		SeleniumUtil.click(accountDrapDown.AdvisorName().get(i));
    		break;
    	}
    }
  
 

	for (int i = 0; i <3; i++) {
		SeleniumUtil.click(accountDrapDown.accShareDropdown().get(i));
		SeleniumUtil.click(accountDrapDown.accShareDropdownValue(i+1).get(0));
		
	}
    SeleniumUtil.click(accountDrapDown.accShareDropdown().get(4));
    SeleniumUtil.click(accountDrapDown.accShareDropdownValue(5).get(1));

	SeleniumUtil.waitForPageToLoad(5000);
	SeleniumUtil.click(accountDrapDown.AccountShareSave());
	SeleniumUtil.waitForPageToLoad(5000);
	logger.info("share the accounts to selected advisor");
	Assert.assertEquals(accountDrapDown.accShareDropdown().get(0).getText(),
			PropsUtil.getDataPropertyValue("AccountSharedDropdownValueView"), "accounts is not shared to advisor");
	
		
	}

	public void invester2SharedAccount() throws Exception
	{	
	SAMLlogin.login(d, InvesterName2,null,"10003700");
    logger.info("login to investor");
	
	SeleniumUtil.waitForPageToLoad(7000);
    PageParser.forceNavigate("ManageSharing", d);
    SeleniumUtil.waitForPageToLoad(5000);
    logger.info("click on advisor name in manage sharing");
    for(int i=0;i<accountDrapDown.AdvisorName().size();i++)
    {
    	if(accountDrapDown.AdvisorName().get(i).getText().contains(addvisorRef1))
    	{
    		SeleniumUtil.click(accountDrapDown.AdvisorName().get(i));
    		break;
    	}
    }
  
 

	for (int i = 0; i <3; i++) {
		SeleniumUtil.click(accountDrapDown.accShareDropdown().get(i));
		SeleniumUtil.click(accountDrapDown.accShareDropdownValue(i+1).get(0));
		
	}
    SeleniumUtil.click(accountDrapDown.accShareDropdown().get(4));
    SeleniumUtil.click(accountDrapDown.accShareDropdownValue(5).get(1));

	SeleniumUtil.waitForPageToLoad(5000);
	SeleniumUtil.click(accountDrapDown.AccountShareSave());
	SeleniumUtil.waitForPageToLoad(5000);
	logger.info("share the accounts to selected advisor");
	Assert.assertEquals(accountDrapDown.accShareDropdown().get(0).getText(),
			PropsUtil.getDataPropertyValue("AccountSharedDropdownValueView"), "accounts is not shared to advisor");
		
	}
	
	
	public void advisor1ToInvestor2SharedAccount() throws Exception
	{//we have add after api automation
		
		logger.info("share the accounts to investor");
		logger.info("login to advisor with extra param segmentName=advisor&IID=Investorname ");
		String samlResponse = LoginPage_SAML3.loginResponse(d, advisorUsername, null);
		ysl.shareAdvisorAccount(samlResponse, advisorUsername, InvesterName2);
		
	}
	public void advisor1ToInvestor1SharedAccount() throws Exception
	{//we have add after api automation
		SAMLlogin.login(d, advisorUsername,null,"10003600");
		acccuntAdd.linkAccountFastLink();
		acccuntAdd.addAggregatedAccount("renuka21.site16441.2", "site16441.2");
		logger.info("share the accounts to investor");
		logger.info("login to advisor with extra param segmentName=advisor&IID=Investorname ");
		String samlResponse = LoginPage_SAML3.loginResponse(d, advisorUsername, null);
		ysl.shareAdvisorAccount(samlResponse, advisorUsername, InvesterName1);
		System.out.println(advisorUsername);
		System.out.println(InvesterName1);
		System.out.println(InvesterName2);
		
	}

	
	@Test(description = "verify Addvisor is added sucessfully", priority = 6)
	  void verifyAdvisorHasOnlyInvesterSharedAccount() throws InterruptedException, AWTException
	  {
		SAMLlogin.login(d, advisorUsername,InvesterName1,"10003700");
		SeleniumUtil.waitForPageToLoad(7000);
		PageParser.forceNavigate("Transaction", d);
		SeleniumUtil.waitForPageToLoad(7000);
		SeleniumUtil.click(accountDrapDown.accountFilterLink());
		boolean sharedaccountName=false;
		for(int i=0;i<accountDrapDown.InvestorAdvisorAccountListAccountName().size();i++)
		{
			System.out.println(accountDrapDown.InvestorAdvisorAccountListAccountName().get(i).getText().trim().replaceAll("\n", " "));
			if(accountDrapDown.InvestorAdvisorAccountListAccountName().get(i).getText().trim().replaceAll("\n", " ").equals(PropsUtil.getDataPropertyValue("MutlipleAdvisorInvestor1").trim())){
				sharedaccountName=true;
			}
		}
		Assert.assertTrue(sharedaccountName);

	  }
	@Test(description = "verify Addvisor is added sucessfully", priority = 7)
	  void verifyAdvisorHasInvesterSharedAccountAndAdvisorAccount() throws InterruptedException, AWTException
	  {
		
	
	
	
	Assert.assertEquals(accountDrapDown.allAccountLable().get(0).getText().trim(),PropsUtil.getDataPropertyValue("allAccountLable"),"when invester as no shared account only allacount optio nshould displyed");
	
	
  }
@Test(description = "verify Addvisor is added sucessfully", priority = 8)
  void verifyAdvisor3MyaccountText() throws InterruptedException, AWTException
  {
	
	Assert.assertEquals(accountDrapDown.myOrAdvisorSharedAccountLable().get(0).getText().trim(),PropsUtil.getDataPropertyValue("TransactionAdvisor1MyaccountText"),"when invester as no shared account only allacount optio nshould displyed");

  }

@Test(description = "verify Addvisor is added sucessfully", priority = 9)
  void verifyAdvisor3InvesterAccountText() throws InterruptedException, AWTException
  {
	
	Assert.assertEquals(accountDrapDown.myOrAdvisorSharedAccountLable().get(1).getText().trim(),PropsUtil.getDataPropertyValue("TransactionInvesterSharedAccount"),"when invester as no shared account only allacount optio nshould displyed");

  }

}
