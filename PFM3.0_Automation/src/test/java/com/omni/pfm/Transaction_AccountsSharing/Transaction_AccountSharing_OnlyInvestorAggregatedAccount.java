/*******************************************************************************
 * Copyright (c) 2018 Yodlee Inc. All Rights Reserved.
 * 
 * This software is the confidential and proprietary information of
 * Yodlee, Inc. Use is subject to license terms.
 * 
 * @author
 ******************************************************************************/
package com.omni.pfm.Transaction_AccountsSharing;

import java.awt.print.Pageable;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.gargoylesoftware.htmlunit.Page;
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

public class Transaction_AccountSharing_OnlyInvestorAggregatedAccount extends TestBase {
	public static String TransactionloginName;

	private static final Logger logger = LoggerFactory.getLogger(Transaction_AccountSharing_OnlyInvestorAggregatedAccount.class);
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
	@Test(description = "verify Addvisor and investor created sucessfully", priority = 1)
	public void login() throws Exception
	{	
		logger.info("Get the unique reference id");
		addvisorRef1=SAMLlogin.getRefrenceId();
		logger.info("Get the unique investor Name");
		InvesterName1= SAMLlogin.getInvestorUserName();
		logger.info("create advisor with extra param segmentName=advisor&IID=Investorname ");
		advisorUsername= SAMLlogin.createAdvisor(d,InvesterName1 , addvisorRef1,"10003700");

		SeleniumUtil.waitForPageToLoad();
		
       
		SeleniumUtil.waitForPageToLoad(5000);
		boolean expectedInvestor=false;
		if(advisorUsername!=null)
		{
		logger.info("create investor and prepop account to investor");
		SAMLlogin.createInvestor(d, advisorUsername, InvesterName1,"TransactionFilter1.site16441.1","site16441.1");
		acccuntAdd.linkAccount();
	    acccuntAdd.addAggregatedAccount("advisorinvestor1.site16441.2", "site16441.2");
	   
		expectedInvestor=true;
		}
		
		Assert.assertTrue(expectedInvestor,"advisor and investor is not created successfully");
		
		
	}
	
	@Test(description = "verify Addvisor is added sucessfully", priority = 2)
	public void invester1SharedAccount() throws Exception
	{	
		logger.info("login to investor");
	//SAMLlogin.login(d, InvesterName1,null);
		//SAMLlogin.login(d, "PFMINV1524651174925",null);
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
  
 
    SeleniumUtil.click(accountDrapDown.accShareDropdown().get(0));
    SeleniumUtil.waitForPageToLoad(5000);
    SeleniumUtil.click(accountDrapDown.accShareDropdownValue(1).get(0));
    SeleniumUtil.click(accountDrapDown.accShareDropdown().get(1));
    SeleniumUtil.click(accountDrapDown.accShareDropdownValue(2).get(0));
    SeleniumUtil.click(accountDrapDown.accShareDropdown().get(2));
    SeleniumUtil.click(accountDrapDown.accShareDropdownValue(3).get(0));
    SeleniumUtil.click(accountDrapDown.accShareDropdown().get(3));
    SeleniumUtil.click(accountDrapDown.accShareDropdownValue(4).get(0));
    SeleniumUtil.click(accountDrapDown.accShareDropdown().get(4));
    SeleniumUtil.click(accountDrapDown.accShareDropdownValue(5).get(1));
   
    SeleniumUtil.waitForPageToLoad(5000);
	SeleniumUtil.click(accountDrapDown.AccountShareSave());
	SeleniumUtil.waitForPageToLoad(5000);
	logger.info("share the accounts to selected advisor");
	Assert.assertEquals(accountDrapDown.accShareDropdown().get(0).getText(),PropsUtil.getDataPropertyValue("AccountSharedDropdownValueView"),"accounts is not shared to advisor");
		
	}
	
	@Test(description = "", priority = 3)
	public void verifyAllAccountsLabel_InvesterView() throws Exception
	{
		
		PageParser.forceNavigate("Transaction", d);
		SeleniumUtil.waitForPageToLoad();
		SeleniumUtil.click(accountDrapDown.accountFilterLink());
		Assert.assertEquals(accountDrapDown.allAccountLable().get(0).getText().trim(), PropsUtil.getDataPropertyValue("allAccountLable"), "all accounts should be displayed when advisor had both aggregated and shared accounts");
	}
	@Test(description = "", priority = 4)
	public void verifyAllAccountsCheckBox_InvesterView() throws Exception
	{
		Assert.assertTrue(accountDrapDown.allAccountCheckBox().isDisplayed(),"my accounts check box should displayed");

	}
	@Test(description = "", priority = 5)
	public void verifyMyAccountAdvisorSharedLabel_InvesterView() throws Exception
	{
		
		boolean myAccountSize=false;
				if(accountDrapDown.myOrAdvisorSharedAccountLable().size()==0){
					myAccountSize=true;
				}
				
		Assert.assertTrue(myAccountSize,"my account and advisor shared accounts section should not displayed");
	}
}
