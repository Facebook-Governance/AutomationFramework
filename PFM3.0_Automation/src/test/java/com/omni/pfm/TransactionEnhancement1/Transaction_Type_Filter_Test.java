/**
* Copyright (c) 2018 Yodlee Inc. All Rights Reserved.
*
* This software is the confidential and proprietary information of
* Yodlee, Inc. Use is subject to license terms.
*
* @author Renuka K
*/
package com.omni.pfm.TransactionEnhancement1;

import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.omni.pfm.PageProcessor.PageParser;
import com.omni.pfm.pages.AccountAddition.AccountAddition;
import com.omni.pfm.pages.TransactionEnhancement1.Transaction_AccountDropDown_Loc;
import com.omni.pfm.pages.TransactionEnhancement1.Transaction_AccountSharing_ReadOnlyFeature_loc;
import com.omni.pfm.pages.TransactionEnhancement1.Transaction_Filter_Loc;
import com.omni.pfm.pages.TransactionEnhancement1.Transaction_Search_Loc;
import com.omni.pfm.pages.TransactionEnhancement1.Transaction_Tag_Loc;
import com.omni.pfm.testBase.TestBase;
import com.omni.pfm.utility.PropsUtil;
import com.omni.pfm.utility.SeleniumUtil;


public class Transaction_Type_Filter_Test extends TestBase {

	
	private static final Logger logger = LoggerFactory.getLogger(Transaction_Type_Filter_Test.class);
	
	Transaction_Filter_Loc filter;
	AccountAddition accountAdd;
    Transaction_AccountDropDown_Loc accountDropDown;
    Transaction_Search_Loc search;
    Transaction_AccountSharing_ReadOnlyFeature_loc readOnly;
    Transaction_Tag_Loc tag;
	@BeforeClass(alwaysRun = true)
	public void testInit() throws InterruptedException {
		 doInitialization("Transaction Tag");
		 TestBase.tc = TestBase.extent.startTest("Trans tags", "Login");
	     TestBase.test.appendChild(TestBase.tc);
		 accountAdd=new AccountAddition();
		 filter=new Transaction_Filter_Loc(d);
		 accountDropDown=new Transaction_AccountDropDown_Loc(d);
		 search=new Transaction_Search_Loc(d);
		 readOnly=new Transaction_AccountSharing_ReadOnlyFeature_loc(d);
		 tag=new Transaction_Tag_Loc(d);
		 d.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
          PageParser.forceNavigate("Transaction", d);
          SeleniumUtil.waitForPageToLoad();
	}
	
	@Test(description="AT-68617:verify filter by text",priority=1)
	public void  verifyFilterByText()
	{
		Assert.assertEquals(filter.transactionFilterByText().getText().trim(), PropsUtil.getDataPropertyValue("transactionFilterByText"), "verify  filter text is displaying ");

	}
	@Test(description="AT-68617:verify reset button should not displayed by default",priority=2)
	public void  verifyrestButton()
	{
		System.out.println(tag.reset().getAttribute("class"));
		Assert.assertTrue(tag.reset().getAttribute("class").contains(PropsUtil.getDataPropertyValue("TransactionCategoriesFilterCategoryHide")),"reset button should not displayed without Applied filter");
	}
	
	@Test(description="AT-68623:verify transaction type text",priority=3)
	public void  verifyTransactionTypeText()
	{
		Assert.assertEquals(filter.transactionTypeText().getText().trim(), PropsUtil.getDataPropertyValue("transactionTypeText"), "verify transaction type text is displaying ");
	}
	@Test(description="AT-68692:verify all transaction type filter",priority=4)
	public void  verifyTxnTypeFilterAllType()
	{
		Assert.assertEquals(filter.transactionTypeAll().getText().trim(), PropsUtil.getDataPropertyValue("transactionTypeAll"), "verify transaction type all text is displaying ");
		Assert.assertEquals(filter.transactionTypeDeposit().getText().trim(), PropsUtil.getDataPropertyValue("transactionTypeDeposit"), "verify transaction type deposit text is displaying ");
		Assert.assertEquals(filter.transactionTypeWithDrawal().getText().trim(), PropsUtil.getDataPropertyValue("transactionTypeWithdrawal"), "verify transaction type Withdrawal text is displaying ");

	}
	
	@Test(description="AT-68693:verify all option should selected by default",priority=5)
	public void  verifyTypeAllSelectedDafault()
	{
		Assert.assertEquals(filter.transactionTypeAllActive().getAttribute("class"), PropsUtil.getDataPropertyValue("transactionTypeSelected"), "verify transaction type all is selected by default ");

	}
	
	@Test(description="AT-68693:verify all type transaction",priority=6,dependsOnMethods="verifyTypeAllSelectedDafault")
	public void  verifyAllTypeTxn_AllType()
	{
		if(accountDropDown.allTransactionAccount().get(0).getText().equals(""))
		{
			SeleniumUtil.click(search.ProjectedExapdIcon());
	    	   SeleniumUtil.waitForPageToLoad(2000);
		}
     boolean allAccountType=false;
    
     for(int i= 0;i<20;i++)
     {
         SeleniumUtil.click(accountDropDown.allTransactionDescription().get(i));
    	 if(readOnly.transactionDetailsReadonlyAccountLabel().getText().trim().equals(PropsUtil.getDataPropertyValue("TransactionTypeDebitedFrom"))||
    			 readOnly.transactionDetailsReadonlyAccountLabel().getText().trim().equals(PropsUtil.getDataPropertyValue("TransactionTypeCreditedTo")))
    	 {
    		 allAccountType=true;
    	 }
    	 else{
    		 allAccountType=false;
    		 break;
    	 }
     }
     
     Assert.assertTrue(allAccountType,"debited from  and creditedTo transaction type should displayed when select all Transaction type");
	}
	@Test(description="AT-68224,AT-68225:verify deopist type transaction amount should be displayed with green",priority=7)
	public void  verifyDepositTypeTxnAmountGreen_Deposit()
	{
		SeleniumUtil.click(filter.transactionTypeDeposit());
		SeleniumUtil.waitForPageToLoad();
		boolean actualDepositType=false;
		for(int i=0;i<search.allTransactionAmount().size();i++)
		{
			System.out.println(search.allTransactionAmount().get(i).getAttribute("class").trim());
			if(search.allTransactionAmount().get(i).getAttribute("class").trim().contains("y-font-green"))
			{
				actualDepositType=true;
			}
			else
			{
				actualDepositType=false;
				break;
			}
		}
		Assert.assertTrue(actualDepositType,"all deposit amount displayed with green");
	}
	@Test(description="AT-68618:verify reset button for transaction type filter",priority=8,dependsOnMethods="verifyDepositTypeTxnAmountGreen_Deposit")
	public void  verifyResetButtonDisplaying()
	{
		Assert.assertEquals(tag.reset().getText().trim(), PropsUtil.getDataPropertyValue("transactionResetText"), "after filter reset should displayed");

	}
	@Test(description="AT-68694:verify transaction type account when select deposit type filter",priority=9,dependsOnMethods="verifyDepositTypeTxnAmountGreen_Deposit")
	public void  verifyDepositTypeTxnCreditezto_Deposit()
	{
		boolean allAccountType=false;
	     int allTransactionSize=accountDropDown.allTransactionDescription().size();
		
	     for(int i= 0;i<allTransactionSize;i++)
	     {
	    	 SeleniumUtil.click(accountDropDown.allTransactionDescription().get(i));
	    	 if(readOnly.transactionDetailsReadonlyAccountLabel().getText().trim().equals(PropsUtil.getDataPropertyValue("TransactionTypeCreditedTo"))
	    			 )
	    	 {
	    		 allAccountType=true;
	    	 }
	    	 else{
	    		 allAccountType=false;
	    		 break;
	    	 }
	     }
	     
	     Assert.assertTrue(allAccountType,"debited from  transaction type should displayed when select all Transaction type");
		
	}
	@Test(description="AT-68223,AT-68225:verify transaction amount when select withdrwal type filter",priority=10)
	public void  verifyWithDrawalTypeTxAmounthBlack_WithDrawal()
	{
		SeleniumUtil.click(filter.transactionTypeWithDrawal());
		SeleniumUtil.waitForPageToLoad();
	if(accountDropDown.allTransactionAccount().get(0).getText().equals(""))
	{
		SeleniumUtil.click(search.ProjectedExapdIcon());
    	   SeleniumUtil.waitForPageToLoad(2000);
	}
	boolean actualDepositType=false;
	logger.info("verify all deposit type transaction amount should displayed with back");
	for(int i=0;i<search.allTransactionAmount().size();i++)
	{
		if(search.allTransactionAmount().get(i).getAttribute("class").trim().contains("y-font-black"))
		{
			actualDepositType=true;
		}
		else
		{
			actualDepositType=false;
			break;
		}
	}
	
	Assert.assertTrue(actualDepositType,"all withDarw type transaction are not displaying");
	}
	
	@Test(description="AT-68695:verify withdowal transaction account type when withdrawal type filter selected",priority=11,dependsOnMethods="verifyWithDrawalTypeTxAmounthBlack_WithDrawal")
	public void  verifyWithDrawalTypeTxnDebitedFrom_Withdrawal()
	{
		boolean allAccountType=false;
	     int allTransactionSize=accountDropDown.allTransactionDescription().size();

	      for(int i= 0;i<allTransactionSize;i++)
	     {
	    	 SeleniumUtil.click(accountDropDown.allTransactionDescription().get(i));
	    	 if(readOnly.transactionDetailsReadonlyAccountLabel().getText().trim().equals(PropsUtil.getDataPropertyValue("TransactionTypeDebitedFrom"))
	    			 )
	    	 {
	    		 allAccountType=true;
	    	 }
	    	 else{
	    		 allAccountType=false;
	    		 break;
	    	 }
	     }
	     
	     Assert.assertTrue(allAccountType,"debited from  transaction type should displayed when select all Transaction type");
		
	}
	@Test(description="AT-68620:verify filter should cleare after rest the filter",priority=12,dependsOnMethods="verifyWithDrawalTypeTxnDebitedFrom_Withdrawal")
	public void  verifyResetFilter()
	{
		SeleniumUtil.click(tag.reset());
		SeleniumUtil.waitForPageToLoad();
		Assert.assertEquals(filter.transactionTypeAllActive().getAttribute("class"), PropsUtil.getDataPropertyValue("transactionTypeSelected"), "verify transaction type all is selected by default ");

		
	}
}
