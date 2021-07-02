/**
* Copyright (c) 2018 Yodlee Inc. All Rights Reserved.
*
* This software is the confidential and proprietary information of
* Yodlee, Inc. Use is subject to license terms.
*
* @author Renuka K
*/
package com.omni.pfm.TransactionEnhancement1;



import java.util.List;

import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.accessibility.examples.testng.webdriver.test.RunAccessibilityTest;
import com.omni.pfm.pages.Login.LoginPage;
import com.omni.pfm.pages.Login.LoginPage_SAML1;
import com.omni.pfm.pages.TransactionEnhancement1.Aggregated_Transaction_Details_Loc;
import com.omni.pfm.pages.TransactionEnhancement1.Transaction_DashBoard_Integration_Loc;
import com.omni.pfm.pages.TransactionEnhancement1.Transaction_Filter_Loc;
import com.omni.pfm.pages.TransactionEnhancement1.Transaction_Layout_Loc;
import com.omni.pfm.pages.TransactionEnhancement1.Transaction_Tag_Loc;
import com.omni.pfm.testBase.TestBase;
import com.omni.pfm.utility.PropsUtil;
import com.omni.pfm.utility.SeleniumUtil;



public class Transaction_DashBoard_Integration_test extends TestBase{
	public Transaction_Filter_Loc transaction_Filter;
	public  Transaction_Layout_Loc Layout;
		
		public Aggregated_Transaction_Details_Loc aggre;
		WebDriver webDriver = null;
		public Transaction_DashBoard_Integration_Loc DashBoard;
		private static final Logger logger = LoggerFactory.getLogger(Transaction_DashBoard_Integration_test.class);
		public static String userName="";
		Transaction_Tag_Loc Tag;
		SeleniumUtil util;
		@BeforeClass(alwaysRun = true)
	public void testInit() throws InterruptedException {
         doInitialization("Transaction Tag");
         TestBase.tc = TestBase.extent.startTest("Trans tags", "Login");
         TestBase.test.appendChild(TestBase.tc);
         DashBoard=new Transaction_DashBoard_Integration_Loc(d);
         Layout=new Transaction_Layout_Loc(d);
         aggre=new Aggregated_Transaction_Details_Loc(d);
         Tag=new Transaction_Tag_Loc(d);
         util=new SeleniumUtil();
         d.navigate().refresh();
	    SeleniumUtil.waitForPageToLoad(15000);
		

	}
	
	
	@Test(description = "AT-68860,AT-68861:verify accounts name is selected in Transaction account dropdown", priority = 2)

	public void clickCashAccount() throws Exception {
//Verify when user clicks on a transaction from Dashboard, it should navigate to Transactions
if(DashBoard.isDashBoardTransactionExpandIconPresent()){
		SeleniumUtil.click(DashBoard.DashBoardTransactionExpandIcon());}
		
		DashBoard.clickAccountRow(PropsUtil.getDataPropertyValue("DashBoard_Account_Cash"));
	if(Layout.isMobileFilterIconPresent())
		{ SeleniumUtil.click(Layout.MobileFilterIcon());
		SeleniumUtil.waitForPageToLoad(2000);  }
		Assert.assertTrue(DashBoard.transactionAcc().getText().contains(PropsUtil.getDataPropertyValue("DashBoardAccount")));
		}
	@Test(description = "AT-68855:verify back to dashboard icon is dipslying", priority = 3,dependsOnMethods="clickCashAccount")
    public void backToDashBoardIcon() throws Exception {
	if(Tag.isMobileFilterBackButtonPresent()){
		SeleniumUtil.click(Tag.MobileFilterBackButton()); }
		Assert.assertTrue(DashBoard.backToDashBoardIcon().isDisplayed());
	}
	@Test(description = "AT-68855:verify Back dashboard tetx is displaying", priority = 4,dependsOnMethods="clickCashAccount")
    public void backToDashBoardText() throws Exception {

		Assert.assertEquals(DashBoard.backToDashBoardText().getText(), PropsUtil.getDataPropertyValue("dashBoardBack"));
	}
	@Test(description = "AT-68856:verify navigated to dash board when click on backtoDashboard link", priority = 5,dependsOnMethods="clickCashAccount")

	public void backToDashBoardHeader() throws Exception {
		DashBoard.clickbackTodashBoard();
		Assert.assertEquals(DashBoard.dashBoardHeader().getText().trim(), PropsUtil.getDataPropertyValue("DashBoard_Header"));
		
	}
	
	@Test(description = "AT-68857:verify Transaction header displaying with today date in date header", priority = 6,dependsOnMethods="backToDashBoardHeader")
    public void recentTransaction() throws Exception {
     
		Assert.assertTrue(DashBoard.recentTransactionHeader().getText().replaceAll("Today,", "").trim().contains(DashBoard.CurrentdateFormate().trim()));
		}
	
	@Test(description = "AT-68859: verify 5 transactions are displayinh", priority = 7,dependsOnMethods="backToDashBoardHeader")
    public void recent5Transaction() throws Exception {
		List<String> actualtxn=util.getWebElementsValue(DashBoard.dashBoardTxnsAmount());
		String txnrow[]=PropsUtil.getDataPropertyValue("DashBoard_Txn_Rows").split("/");
				for(String row:txnrow)
				{
		        util.isAnyMatchInList(actualtxn,row);
				}
		Assert.assertTrue(DashBoard.recentTransactionRow().size()==5);
	}
	@Test(description = "AT-68858:verfiy pending transactions are not displaying in dashboard", priority = 8,dependsOnMethods="backToDashBoardHeader")
    public void pendingTxn() throws Exception {
		List<String> actualtxn=util.getWebElementsValue(DashBoard.dashBoardTxnsAmount());
		Assert.assertFalse(util.assertFalse(actualtxn, PropsUtil.getDataPropertyValue("DashBoard_Pending_Txn1")));
		Assert.assertFalse(util.assertFalse(actualtxn, PropsUtil.getDataPropertyValue("DashBoard_Pending_Txn2")));
	}
	@Test(description = "AT-68854,AT-68855: verift navigating to Transaction when click on Transaction widget", priority = 9,dependsOnMethods="backToDashBoardHeader")

	public void txnWidget() throws Exception {
       //Verify when user clicks on a transaction from Dashboard, it should navigate to Transactions
		DashBoard.clickTxnWidget();
		Assert.assertTrue(DashBoard.backToDashBoardIcon().isDisplayed());
		Assert.assertEquals(DashBoard.backToDashBoardText().getText(), PropsUtil.getDataPropertyValue("dashBoardBack"));

		}
	@Test(description = "AT-68856:verify navigate to dash board when click on back to dashboard link", priority = 10,dependsOnMethods="txnWidget")
	public void txnWidgetbackToDashBoardHeader() throws Exception {
		DashBoard.clickbackTodashBoard();
		Assert.assertEquals(DashBoard.dashBoardHeader().getText().trim(), PropsUtil.getDataPropertyValue("DashBoard_Header"));
		
	}
	@Test(description = "AT-68860:verify navigate to transaction when click on card account", priority = 13,dependsOnMethods="txnWidgetbackToDashBoardHeader")

	public void clikcCardAccount() throws Exception {
		
		DashBoard.clickAccountRow(PropsUtil.getDataPropertyValue("DashBoard_Account_Card"));
		Assert.assertTrue(DashBoard.backToDashBoardIcon().isDisplayed());
		Assert.assertEquals(DashBoard.backToDashBoardText().getText(), PropsUtil.getDataPropertyValue("dashBoardBack"));

	}
	
	@Test(description = "AT-68860:verify naviting to transaction when  click on Invetsment accounts", priority = 14,dependsOnMethods="clikcCardAccount")

	public void clikcInvestmentAccount() throws Exception {
	
		DashBoard.clickbackTodashBoard();
		DashBoard.clickAccountRow(PropsUtil.getDataPropertyValue("DashBoard_Account_Investment"));
		Assert.assertTrue(DashBoard.backToDashBoardIcon().isDisplayed());
		Assert.assertEquals(DashBoard.backToDashBoardText().getText(), PropsUtil.getDataPropertyValue("dashBoardBack"));

	}
	@Test(description = "AT-68860: verify navigating to Transaction when click on loan account", priority = 15,dependsOnMethods="clikcInvestmentAccount")

	public void clikcLoanAccount() throws Exception {
		
		DashBoard.clickbackTodashBoard();
		DashBoard.clickAccountRow(PropsUtil.getDataPropertyValue("DashBoard_Account_Loan"));
		Assert.assertTrue(DashBoard.backToDashBoardIcon().isDisplayed());
		Assert.assertEquals(DashBoard.backToDashBoardText().getText(), PropsUtil.getDataPropertyValue("dashBoardBack"));

	}
	
	@Test(description = "AT-68860:verify navigating to Transaction when click on insurence account ", priority = 16,dependsOnMethods="clikcLoanAccount")

	public void clikcInsurenceAccount() throws Exception {

		DashBoard.clickbackTodashBoard();
		DashBoard.clickAccountRow(PropsUtil.getDataPropertyValue("DashBoard_Account_Insurence"));
		Assert.assertTrue(DashBoard.backToDashBoardIcon().isDisplayed());
		Assert.assertEquals(DashBoard.backToDashBoardText().getText(), PropsUtil.getDataPropertyValue("dashBoardBack"));

	
	}
	@AfterClass
    public void checkAccessibility() {
           try {
                  RunAccessibilityTest rat = new RunAccessibilityTest(this.getClass().getName());
                  rat.testAccessibility(d);
                  
           } catch (Exception e) {

           }
    }
	
}
