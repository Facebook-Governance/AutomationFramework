/**
* Copyright (c) 2019 Yodlee Inc. All Rights Reserved. 
*
* This software is the confidential and proprietary information of
* Yodlee, Inc. Use is subject to license terms.
*
* @author sbhat1
*/
package com.omni.pfm.BudgetEnhancement;


import java.util.concurrent.TimeUnit;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.omni.pfm.PageProcessor.PageParser;
import com.omni.pfm.config.Config;
import com.omni.pfm.pages.AccountAddition.AccountAddition;
import com.omni.pfm.pages.Login.LoginPage;

import com.omni.pfm.pages.TransactionEnhancement1.Aggregated_Transaction_Details_Loc;

import com.omni.pfm.pages.TransactionEnhancement1.Transaction_Budget_Integration_Loc;
import com.omni.pfm.pages.TransactionEnhancement1.Transaction_Categorization_Rules_Loc;

import com.omni.pfm.testBase.TestBase;
import com.omni.pfm.utility.PropsUtil;
import com.omni.pfm.utility.SeleniumUtil;

public class Budget_CreateRule_Integration_Test extends TestBase {
	
	Aggregated_Transaction_Details_Loc aggre_Tranc_details;
    Transaction_Budget_Integration_Loc  budget;
	int noOfwithrow;
	AccountAddition accountAdd;
	LoginPage login;
	Transaction_Categorization_Rules_Loc rule;
	public static String userName=null;
	
	@BeforeClass(alwaysRun = true)
	public void testInit() throws Exception {
	doInitialization("Add Manual Transaction");

	TestBase.tc = TestBase.extent.startTest("Login", "Login");
	TestBase.test.appendChild(TestBase.tc);
	accountAdd=new AccountAddition();
	aggre_Tranc_details = new Aggregated_Transaction_Details_Loc(d);
	budget = new Transaction_Budget_Integration_Loc(d);
	rule=new Transaction_Categorization_Rules_Loc(d);
    login=new LoginPage();
  
    d.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
    LoginPage.loginMain(d,loginParameter);
   // userName= config.userName;
    SeleniumUtil.waitForPageToLoad(4000);
   accountAdd.linkAccount();
   accountAdd.addAggregatedAccount("TransactionFilter1.site16441.4", "site16441.4");
   SeleniumUtil.waitForPageToLoad(4000);
    PageParser.forceNavigate("Budget", d);
    SeleniumUtil.waitForPageToLoad();
	budget.creatBudget();
	SeleniumUtil.waitForPageToLoad();
}

	@Test(description="AT-85117:verify create rule icon",priority=1)
	public void verifyCreateRuleIcon()
	{		
		if(Config.appFlag.equals("app")||Config.appFlag.equals("emulator"))
		{
			SeleniumUtil.click(aggre_Tranc_details.butgtviewdetailsbtn());
			SeleniumUtil.waitForPageToLoad();
			budget.clickBugtTxnRow(PropsUtil.getDataPropertyValue("Budget_Catgory_CategoryName"), 0);
			SeleniumUtil.waitForPageToLoad();
			aggre_Tranc_details.changeCategoories(PropsUtil.getDataPropertyValue("Budget_Transaction_ChangeCat1"));
			Assert.assertTrue(aggre_Tranc_details.createRuleIcon_TD().isDisplayed());
		}else {
		budget.clickBugtTxnRow(PropsUtil.getDataPropertyValue("Budget_Catgory_CategoryName"), 0);
		aggre_Tranc_details.changeCategoories(PropsUtil.getDataPropertyValue("Budget_Transaction_ChangeCat1"));
		Assert.assertTrue(aggre_Tranc_details.createRuleIcon_TD().isDisplayed());
	}
	}
	@Test(description="AT-85117:verify create rule link",priority=2,dependsOnMethods="verifyCreateRuleIcon")
	public void verifyCreateRuleText()
	{
		Assert.assertEquals(aggre_Tranc_details.createRuleText_TD().getText(),PropsUtil.getDataPropertyValue("buget_CreateRule_Link"), "verify create rule text is displaying");
	}
	@Test(description="AT-85118:verify create rule popUp",priority=3,dependsOnMethods="verifyCreateRuleIcon")
	public void verifyCreateRuleHeader()
	{
		aggre_Tranc_details.clickCreatRule();
		Assert.assertEquals(aggre_Tranc_details.createRuleHeader_TD().getText(),PropsUtil.getDataPropertyValue("buget_CreateRule_Header"), "verify create rule text is displaying");
	}
	@Test(description="AT-85121:verify cancel create rule",priority=4,dependsOnMethods="verifyCreateRuleHeader")
	public void verifyCreateRuleCancel()
	{
		if(Config.appFlag.equals("app")||Config.appFlag.equals("emulator"))
		{
			aggre_Tranc_details.clickCancelCreatRulemob();
			SeleniumUtil.waitForPageToLoad(5000);
			Assert.assertTrue(aggre_Tranc_details.txnDetailsHeaderList_TD().size()==1, "verify Transaction Details rules header is displat text is displaying");
	      //  Assert.assertEquals(aggre_Tranc_details.txnDetailsHeader_TD().getText(),PropsUtil.getDataPropertyValue("buget_Transaction_Header"), "verify Transaction Details rules header is displat text is displaying");
			 Assert.assertEquals(aggre_Tranc_details.txnDetailsHeader_TD().getText(),PropsUtil.getDataPropertyValue("buget_Transaction_Headermob"), "verify Transaction Details rules header is displat text is displaying");
		}else {
        aggre_Tranc_details.clickCancelCreatRule(); 
		Assert.assertTrue(aggre_Tranc_details.txnDetailsHeaderList_TD().size()==1, "verify Transaction Details rules header is displat text is displaying");
        Assert.assertEquals(aggre_Tranc_details.txnDetailsHeader_TD().getText(),PropsUtil.getDataPropertyValue("buget_Transaction_Header"), "verify Transaction Details rules header is displat text is displaying");
	}}
	@Test(description="AT-85120:verify back to transaction details",priority=5,dependsOnMethods="verifyCreateRuleCancel")
	public void verifyCreateRuleBack()
	{
		if(Config.appFlag.equals("app")||Config.appFlag.equals("emulator"))
		{
			aggre_Tranc_details.clickCreatRule();
	        aggre_Tranc_details.clickBackCreatRule();
			Assert.assertTrue(aggre_Tranc_details.txnDetailsHeaderList_TD().size()==1, "verify Transaction Details rules header is displat text is displaying");
			 Assert.assertEquals(aggre_Tranc_details.txnDetailsHeader_TD().getText(),PropsUtil.getDataPropertyValue("buget_Transaction_Headermob"), "verify Transaction Details rules header is displat text is displaying");
		}else {
		aggre_Tranc_details.clickCreatRule();
        aggre_Tranc_details.clickBackCreatRule();
		Assert.assertTrue(aggre_Tranc_details.txnDetailsHeaderList_TD().size()==1, "verify Transaction Details rules header is displat text is displaying");
        Assert.assertEquals(aggre_Tranc_details.txnDetailsHeader_TD().getText(),PropsUtil.getDataPropertyValue("buget_Transaction_Header"), "verify Transaction Details rules header is displat text is displaying");
		}
	}
	@Test(description="AT-85119:verify create rule popup",priority=6,dependsOnMethods="verifyCreateRuleBack")
	public void verifyCreateRuleClose()
	{  if(Config.appFlag.equals("app")||Config.appFlag.equals("emulator"))
	{
		aggre_Tranc_details.clickCreatRule();
		aggre_Tranc_details.clickCloseCreatRule();
		Assert.assertTrue(aggre_Tranc_details.txnDetailsHeaderList_TD().size()==1, "verify Transaction Details rules header is displat text is displaying");
		 Assert.assertEquals(aggre_Tranc_details.txnDetailsHeader_TD().getText(),PropsUtil.getDataPropertyValue("buget_Transaction_Headermob"), "verify Transaction Details rules header is displat text is displaying");
	}else { 
		aggre_Tranc_details.clickCreatRule();
		aggre_Tranc_details.clickCloseCreatRule();
		Assert.assertTrue(aggre_Tranc_details.txnDetailsHeaderList_TD().size()==0, "verify Transaction Details rules header is displat text is displaying");
		Assert.assertTrue(aggre_Tranc_details.AggregatedCreatRulepopUpList().size()==0, "verify Transaction Details rules header is displat text is displaying");
	}
	}
	
	
	
	@Test(description="AT-85124,AT-85122:verify create rule sucess message ",priority=7,dependsOnMethods="verifyCreateRuleClose")
	public void verifyCreateRuleSucessMessage() throws Exception
	{
		//LoginPage.loginMain(d, loginParameter);
        budget.clickBugtTxnRow(0);
		aggre_Tranc_details.changeCategoories(PropsUtil.getDataPropertyValue("Budget_Transaction_ChangeCat1"));
        aggre_Tranc_details.clickCreatRule();
        aggre_Tranc_details.CreatRuleExactly(PropsUtil.getDataPropertyValue("Budget_CreateRule_Description1"), 
        	PropsUtil.getDataPropertyValue("Budget_CreateRule_Amount1"),
        	PropsUtil.getDataPropertyValue("Budget_CreateRule_Exactly"),PropsUtil.getDataPropertyValue("Budget_Transaction_ChangeCat1"), false);
		SeleniumUtil.waitForPageToLoad(500);
        Assert.assertEquals(aggre_Tranc_details.createRuleSucessMesaage_TD().getText(),PropsUtil.getDataPropertyValue("AggRuleSucessMessage"), "verify create rule text is displaying");
        Assert.assertEquals(aggre_Tranc_details.txnDetailsHeader_TD().getText(),PropsUtil.getDataPropertyValue("Transaction_EditTransaction_PopUpHeader"), "verify Transaction Details rules header is displat text is displaying");
        
	}
	@Test(description="AT-85124,AT-85122:verify already existing message on create rule popup",priority=8,dependsOnMethods="verifyCreateRuleSucessMessage")
	public void verifyAlreadyExist() throws Exception
	{
        aggre_Tranc_details.clickCreatRule();
        aggre_Tranc_details.CreatRuleExactly(PropsUtil.getDataPropertyValue("Budget_CreateRule_Description1"), 
        	PropsUtil.getDataPropertyValue("Budget_CreateRule_Amount1"),
        	PropsUtil.getDataPropertyValue("Budget_CreateRule_Exactly"),PropsUtil.getDataPropertyValue("Budget_Transaction_ChangeCat1"), false);
		SeleniumUtil.waitForPageToLoad(500);
        Assert.assertEquals(aggre_Tranc_details.createRuleAlreadyExistMsg().getText().trim(),PropsUtil.getDataPropertyValue("Budget_CreateRule_AlreadyExistMessage"),
        		"verify verifyalready exist message");
        
	}
	@Test(description="AT-85124,AT-85122:create rule with between drpdown option",priority=9,dependsOnMethods="verifyAlreadyExist")
	public void verifyCreateRuleWithBetween()
	{
		
		aggre_Tranc_details.clickCloseCreatRule();
		budget.clickBugtTxnRow(PropsUtil.getDataPropertyValue("Budget_Catgory_CategoryName2"), 0);
		aggre_Tranc_details.changeCategoories(PropsUtil.getDataPropertyValue("Budget_Transaction_ChangeCat2"));
        aggre_Tranc_details.clickCreatRule();
        aggre_Tranc_details.CreatRuleBetween(PropsUtil.getDataPropertyValue("Budget_CreateRule_Description2"), 
        		PropsUtil.getDataPropertyValue("Budget_CreateRule_AmountFrom1"),
        		PropsUtil.getDataPropertyValue("Budget_CreateRule_AmountTo1"),
        		PropsUtil.getDataPropertyValue("Budget_CreateRule_Between"),
        		PropsUtil.getDataPropertyValue("Budget_Transaction_ChangeCat2"), false);
        SeleniumUtil.waitForPageToLoad(500);
        Assert.assertEquals(aggre_Tranc_details.createRuleSucessMesaage_TD().getText(),PropsUtil.getDataPropertyValue("AggRuleSucessMessage"), "verify create rule text is displaying");
        Assert.assertEquals(aggre_Tranc_details.txnDetailsHeader_TD().getText(),PropsUtil.getDataPropertyValue("Transaction_EditTransaction_PopUpHeader"), "verify Transaction Details rules header is displat text is displaying");
      
	}
	@Test(description="AT-85124,AT-85122:create rule with past transaction check box",priority=10,dependsOnMethods="verifyCreateRuleWithBetween")
	public void verifyCreateRuleWithPastTxn()
	{
		aggre_Tranc_details.clickCloseCreatRule();
		budget.clickBugtTxnRow(PropsUtil.getDataPropertyValue("Budget_Catgory_CategoryName3"), 0);
		aggre_Tranc_details.changeCategoories(PropsUtil.getDataPropertyValue("Budget_Transaction_ChangeCat3"));
        aggre_Tranc_details.clickCreatRule();
        aggre_Tranc_details.CreatRuleExactly(PropsUtil.getDataPropertyValue("Budget_CreateRule_Description3"), 
            	PropsUtil.getDataPropertyValue("Budget_CreateRule_Amount2"),
            	PropsUtil.getDataPropertyValue("Budget_CreateRule_Exactly"),PropsUtil.getDataPropertyValue("Budget_Transaction_ChangeCat3"), true);
        SeleniumUtil.waitForPageToLoad(500);
        Assert.assertEquals(aggre_Tranc_details.createRuleSucessMesaage_TD().getText(),PropsUtil.getDataPropertyValue("AggRuleSucessMessage"), "verify create rule text is displaying");
        Assert.assertEquals(aggre_Tranc_details.txnDetailsHeader_TD().getText(),PropsUtil.getDataPropertyValue("Transaction_EditTransaction_PopUpHeader"), "verify Transaction Details rules header is displat text is displaying");
      
	}
	
	@Test(description="AT-85124,AT-85122:create rule with onlt description",priority=11,dependsOnMethods="verifyCreateRuleWithPastTxn")
	public void verifyCreateRuleWithOnlyDescription()
	{
		aggre_Tranc_details.clickCloseCreatRule();
		budget.clickBugtTxnRow(PropsUtil.getDataPropertyValue("Budget_Catgory_CategoryName4"), 0);
      
		aggre_Tranc_details.changeCategoories(PropsUtil.getDataPropertyValue("Budget_Transaction_ChangeCat4"));
		aggre_Tranc_details.clickCreatRule();
        aggre_Tranc_details.CreatRuleWithOnlyDescription(PropsUtil.getDataPropertyValue("Budget_CreateRule_Description4"), 
    		  PropsUtil.getDataPropertyValue("Budget_Transaction_ChangeCat4"), false);
      SeleniumUtil.waitForPageToLoad(500);
      Assert.assertEquals(aggre_Tranc_details.createRuleSucessMesaage_TD().getText(),PropsUtil.getDataPropertyValue("AggRuleSucessMessage"), "verify create rule text is displaying");
      Assert.assertEquals(aggre_Tranc_details.txnDetailsHeader_TD().getText(),PropsUtil.getDataPropertyValue("Transaction_EditTransaction_PopUpHeader"), "verify Transaction Details rules header is displat text is displaying");
    
	}
	@Test(description="AT-85124,AT-85122:create rule with only amount",priority=12,dependsOnMethods="verifyCreateRuleWithOnlyDescription")
	public void verifyCreateRuleWithOnlyAmount()
	{
		aggre_Tranc_details.clickCloseCreatRule();
		budget.clickBugtTxnRow(PropsUtil.getDataPropertyValue("Budget_Catgory_CategoryName5"), 0);
        
		aggre_Tranc_details.changeCategoories(PropsUtil.getDataPropertyValue("Budget_Transaction_ChangeCat5"));
		aggre_Tranc_details.clickCreatRule();
		aggre_Tranc_details.CreatRuleOnlyAmount(PropsUtil.getDataPropertyValue("Budget_CreateRule_Amount3"),
        		PropsUtil.getDataPropertyValue("Budget_CreateRule_Exactly"),
        		PropsUtil.getDataPropertyValue("Budget_Transaction_ChangeCat5"), false);
        SeleniumUtil.waitForPageToLoad(500);
        Assert.assertEquals(aggre_Tranc_details.createRuleSucessMesaage_TD().getText(),PropsUtil.getDataPropertyValue("AggRuleSucessMessage"), "verify create rule text is displaying");
        Assert.assertEquals(aggre_Tranc_details.txnDetailsHeader_TD().getText(),PropsUtil.getDataPropertyValue("Transaction_EditTransaction_PopUpHeader"), "verify Transaction Details rules header is displat text is displaying");
      
	}
	@Test(description="AT-85124,AT-85122:verify sub category is selected",priority=13,dependsOnMethods="verifyCreateRuleWithOnlyAmount")
	public void verifySubcategorySelected()
	{
		d.navigate().refresh();
		SeleniumUtil.waitForPageToLoad();
		PageParser.forceNavigate("Categories", d);
		 SeleniumUtil.waitForPageToLoad();
		rule.editsubCat(PropsUtil.getDataPropertyValue("Budget_CreateRule_SubCategory").split(","), 
				PropsUtil.getDataPropertyValue("Budget_CreateRule_SubCategory_HLC"), PropsUtil.getDataPropertyValue("Budget_CreateRule_SubCategory_MLC"));
		 PageParser.forceNavigate("Budget", d);
		 SeleniumUtil.waitForPageToLoad();
		 budget.clickBugtTxnRow(PropsUtil.getDataPropertyValue("Budget_Catgory_CategoryName6"), 0);
		 aggre_Tranc_details.changeCategoories(PropsUtil.getDataPropertyValue("Budget_Transaction_ChangeCat6"));
		 aggre_Tranc_details.clickCreatRule();
		 aggre_Tranc_details.SelectSuBCategory(PropsUtil.getDataPropertyValue("Budget_CreateRule_SubCategory"));
		 Assert.assertEquals(aggre_Tranc_details.TransactionCatRuleCategoryLink().getText().trim(), 
				 PropsUtil.getDataPropertyValue("Budget_CreateRule_SubCategory"),
				 "subcategory should be selected ");
	}
	@Test(description="AT-85124,AT-85122:create rule with sub category",priority=14,dependsOnMethods="verifySubcategorySelected")
	public void createRuleWithsubCat()
	{
		 aggre_Tranc_details.CreatRuleWithSubCategory(PropsUtil.getDataPropertyValue("Budget_CreateRule_SubCategory"),false);
		 SeleniumUtil.waitForPageToLoad(500);
		 Assert.assertEquals(aggre_Tranc_details.createRuleSucessMesaage_TD().getText(),PropsUtil.getDataPropertyValue("AggRuleSucessMessage"), "verify create rule text is displaying");
	     Assert.assertEquals(aggre_Tranc_details.txnDetailsHeader_TD().getText(),PropsUtil.getDataPropertyValue("Transaction_EditTransaction_PopUpHeader"), "verify Transaction Details rules header is displat text is displaying");
	      
	}
}
