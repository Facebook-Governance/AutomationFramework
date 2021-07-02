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
import com.omni.pfm.pages.TransactionEnhancement1.Transaction_Search_Loc;
import com.omni.pfm.pages.TransactionEnhancement1.Transaction_Tag_Loc;
import com.omni.pfm.rest.ysl;
import com.omni.pfm.testBase.TestBase;
import com.omni.pfm.utility.PropsUtil;
import com.omni.pfm.utility.SeleniumUtil;

public class Transaction_AccountSharing_TransactionAccountDropDown extends TestBase {
	public static String TransactionloginName;

	private static final Logger logger = LoggerFactory.getLogger(Transaction_AccountSharing_TransactionAccountDropDown.class);
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
	Transaction_AccountSharing_TransactionAccountFilter accountFilter;
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
	}
	@Test(description = "AT-62058", priority = 1)
	public void verifyAllAccountsLabel_AdvisorView() throws Exception
	{
	   logger.info("");
	    SAMLlogin.login(d, accountFilter.advisorUsername, accountFilter.InvesterName1,"10003700");
		
		   
		PageParser.forceNavigate("Transaction", d);
		SeleniumUtil.waitForPageToLoad();
		SeleniumUtil.click(accountDrapDown.accountFilterLink());
		Assert.assertEquals(accountDrapDown.allAccountLable().get(0).getText().trim(), PropsUtil.getDataPropertyValue("allAccountLable"), "all accounts should be displayed when advisor had both aggregated and shared accounts");
	}
	@Test(description = "AT-62058", priority = 2)
	public void verifyAllAccountsCheckBox_AdvisorView() throws Exception
	{
		
		Assert.assertTrue(accountDrapDown.allAccountCheckBox().isDisplayed(),"my accounts check box should displayed");
	}
	@Test(description = "", priority = 3)
	public void verifyMyAccountsLabel_AdvisorView() throws Exception
	{
		Assert.assertEquals(accountDrapDown.myOrAdvisorSharedAccountLable().get(0).getText().trim(), PropsUtil.getDataPropertyValue("TransactionAdvisor1MyaccountText"), "my accounts should be displayed when advisor had both aggregated and shared accounts");

	}
	
	@Test(description = "", priority = 4)
	public void verifyMyAccountsCheckBox_AdvisorView() throws Exception
	{
		Assert.assertTrue(accountDrapDown.myOrAdvisorSharedAccountCheckBox().get(0).isDisplayed(),"my accounts check box should displayed");
	}
	@Test(description = "AT-62059", priority = 5)
	public void verifyInvesterSharedLabel_AdvisorView() throws Exception
	{
		Assert.assertEquals(accountDrapDown.myOrAdvisorSharedAccountLable().get(1).getText().trim(), PropsUtil.getDataPropertyValue("TransactionInvesterSharedAccount"), "investor shared accounts should be displayed when advisor had both aggregated and shared accounts");

	}
	@Test(description = "AT-62059", priority = 6)
	public void verifyInvesterSharedCheckBox_AdvisorView() throws Exception
	{
		Assert.assertTrue(accountDrapDown.myOrAdvisorSharedAccountCheckBox().get(1).isDisplayed(),"investor accounts check box should displayed");

	}
	
	@Test(description = "AT-62063,AT-62064", priority = 7)
	public void verifyInvesterSharedAccounts_AdvisorView() throws Exception
	{
		logger.info("if not 4 account ,there should be issue with balance only account is displaying or un shared accounts are displaying");
		String expectedAccount[]=PropsUtil.getDataPropertyValue("InvestorSharedAccountInAdvisorView").split(",");
		int expectedAccountAccont=0;
		for(int i=0;i<accountDrapDown.InvestorAdvisorAccountListAccountName().size();i++)
		{
			if(accountDrapDown.InvestorAdvisorAccountListAccountName().get(i).getText().trim().replaceAll("\n"," ").equals(expectedAccount[i]));
			{
				expectedAccountAccont=expectedAccountAccont+1;
			}
		}
		Assert.assertEquals(expectedAccountAccont,Integer.parseInt( PropsUtil.getDataPropertyValue("InvestorExpectedAccountAccount")), "expected Account exactly  4");
	}
	@Test(description = "AT-62062", priority = 9)
	public void verifyAggregatedAccounts_AdvisorView() throws Exception
	{
		
		
		String expectedAccount[]=PropsUtil.getDataPropertyValue("AdvisorAggregatedAccount").split(",");
		int expectedAccountAccont=0;
		for(int i=0;i<accountDrapDown.myAccountListAccountName().size();i++)
		{
			if(accountDrapDown.myAccountListAccountName().get(i).getText().trim().replaceAll("\n"," ").equals(expectedAccount[i]));
			{
				expectedAccountAccont=expectedAccountAccont+1;
			}
		}
		Assert.assertEquals(expectedAccountAccont,Integer.parseInt( PropsUtil.getDataPropertyValue("AdvisorAggregatedExpectedAccountAccount")), "expected Account exactly  6");
	}
	@Test(description = "AT-62062", priority = 10)
	public void verifyBalanceOnlyAccount_AdvisorView() throws Exception
	{
		
		
		
		boolean expectedAccountAccont=false;
		for(int i=0;i<accountDrapDown.myAccountListAccountName().size();i++)
		{
			if(!accountDrapDown.myAccountListAccountName().get(i).getText().trim().replaceAll("\n"," ").equals(PropsUtil.getDataPropertyValue("InvestorBalanceOnlyAccount")))
			{
				expectedAccountAccont=true;
			}
			else{
				expectedAccountAccont=false;
				break;		
			}
		}
		
		 Assert.assertTrue(expectedAccountAccont,"balance only account displaying in advisor accountDropdown");
}
	
	@Test(description = "AT-62058", priority = 11)
	public void verifyAllAccountsLabel_InvesterView() throws Exception
	{
		
		SAMLlogin.login(d,accountFilter.InvesterName1,null,"10003700");
		PageParser.forceNavigate("Transaction", d);
		SeleniumUtil.waitForPageToLoad();
		SeleniumUtil.click(accountDrapDown.accountFilterLink());
		Assert.assertEquals(accountDrapDown.allAccountLable().get(0).getText().trim(), PropsUtil.getDataPropertyValue("allAccountLable"), "all accounts should be displayed when advisor had both aggregated and shared accounts");
	}
	@Test(description = "AT-62058", priority = 12)
	public void verifyAllAccountsCheckBox_InvesterView() throws Exception
	{
		Assert.assertTrue(accountDrapDown.allAccountCheckBox().isDisplayed(),"my accounts check box should displayed");

	}
	@Test(description = "", priority = 13)
	public void verifyMyAccountsLabel_InvesterView() throws Exception
	{
		Assert.assertEquals(accountDrapDown.myOrAdvisorSharedAccountLable().get(0).getText().trim(), PropsUtil.getDataPropertyValue("TransactionAdvisor1MyaccountText"), "my accounts should be displayed when advisor had both aggregated and shared accounts");

	}
	
	@Test(description = "", priority = 14)
	public void verifyMyAccountsCheckBox_InvesterView() throws Exception
	{
		Assert.assertTrue(accountDrapDown.myOrAdvisorSharedAccountCheckBox().get(0).isDisplayed(),"my accounts check box should displayed");

	}
	@Test(description = "AT-62065", priority = 15)
	public void verifyAdvisorSharedLabel_InvesterView() throws Exception
	{
		Assert.assertEquals(accountDrapDown.myOrAdvisorSharedAccountLable().get(1).getText().trim(), PropsUtil.getDataPropertyValue("TransactionAdvisorSharedAccount"), "investor shared accounts should be displayed when advisor had both aggregated and shared accounts");

	}
	@Test(description = "AT-62065", priority = 16)
	public void verifyAdvisorSharedCheckbox_InvesterView() throws Exception
	{
		Assert.assertTrue(accountDrapDown.myOrAdvisorSharedAccountCheckBox().get(1).isDisplayed(),"investor accounts check box should displayed");

	}
	@Test(description = "AT-62066", priority = 17)
	public void verifyAdvisorSharedAccounts_InvesterView() throws Exception
	{
		
		
		String expectedAccount[]=PropsUtil.getDataPropertyValue("AdvisorAggregatedAccount").split(",");
		int expectedAccountAccont=0;
		for(int i=0;i<accountDrapDown.InvestorAdvisorAccountListAccountName().size();i++)
		{
			if(accountDrapDown.InvestorAdvisorAccountListAccountName().get(i).getText().trim().replaceAll("\n"," ").equals(expectedAccount[i]));
			{
				expectedAccountAccont=expectedAccountAccont+1;
			}
		}
		Assert.assertEquals(expectedAccountAccont,Integer.parseInt( PropsUtil.getDataPropertyValue("AdvisorAggregatedExpectedAccountAccount")), "expected Account exactly  6");
	}
	@Test(description = "AT-62062", priority = 18)
	public void verifyAggregatedAccounts_InvesterView() throws Exception
	{
       
		String expectedAccount[]=PropsUtil.getDataPropertyValue("AdvisorAggregatedAccount").split(",");
		int expectedAccountAccont=0;
		for(int i=0;i<accountDrapDown.myAccountListAccountName().size();i++)
		{
			if(accountDrapDown.myAccountListAccountName().get(i).getText().trim().replaceAll("\n"," ").equals(expectedAccount[i]));
			{
				expectedAccountAccont=expectedAccountAccont+1;
			}
		}
		Assert.assertEquals(expectedAccountAccont,Integer.parseInt( PropsUtil.getDataPropertyValue("InvestorAggregatedAccountAccount")), "expected Account exactly  5");
	}
}

