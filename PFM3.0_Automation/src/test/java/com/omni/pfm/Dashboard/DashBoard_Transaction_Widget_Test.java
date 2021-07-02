/**
* Copyright (c) 2018 Yodlee Inc. All Rights Reserved.
*
* This software is the confidential and proprietary information of
* Yodlee, Inc. Use is subject to license terms.
*
* @author Ashwin PM
*/


package com.omni.pfm.Dashboard;

import java.awt.AWTException; 
import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.WebElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.omni.pfm.PageProcessor.PageParser;
import com.omni.pfm.pages.AccountAddition.AccountAddition;
import com.omni.pfm.pages.Dashboard.DashBoard_Transaction_Widget_Loc;
import com.omni.pfm.pages.Login.LoginPage;
import com.omni.pfm.pages.TransactionEnhancement1.NoEditCategoryForInstance_Loc;
import com.omni.pfm.testBase.TestBase;
import com.omni.pfm.utility.FinAppUtil;
import com.omni.pfm.utility.SeleniumUtil;

public class DashBoard_Transaction_Widget_Test extends TestBase {

	private static final Logger logger = LoggerFactory.getLogger(DashBoard_Transaction_Widget_Test.class);
	
	AccountAddition accAddition;
	NoEditCategoryForInstance_Loc NoEditCatLoc;
	DashBoard_Transaction_Widget_Loc TxnDBWidgetLoc;
	FinAppUtil finApp;
	
	List<String> lastFiveTransactions = new ArrayList<>();
	List<String> lastFiveBalances = new ArrayList<>();

	@BeforeClass(alwaysRun = true)

	public void testInit() throws Exception {

		doInitialization("No Edit Category for Instance of a Transaction");

		TestBase.tc = TestBase.extent.startTest("Login", "Login");
		TestBase.test.appendChild(TestBase.tc);
		
		accAddition = new AccountAddition();
		TxnDBWidgetLoc = new DashBoard_Transaction_Widget_Loc(d);
		NoEditCatLoc = new NoEditCategoryForInstance_Loc(d);
		finApp = new FinAppUtil(d);
		
		LoginPage.loginMain(d,loginParameter); 
		SeleniumUtil.waitForPageToLoad();
		
	}
	
	@Test(description = "Adding account with no transactions.", priority = 1)
	public void addNoTxnsAccount() throws AWTException, InterruptedException {
		
		logger.info("Add Dag Site");
		
		accAddition.linkAccount();
		SeleniumUtil.waitForPageToLoad();
		
		Assert.assertTrue(accAddition.addAggregatedAccount("TxnWidgetTest.site16441.1","site16441.1"));
       
	}
	
	@Test(description = "AT-77013 : Verify the below message displayed if there no transactions available in the last 3 months for the all the customer Accounts. 'There are no transactions to display'", dependsOnMethods= {"addNoTxnsAccount"}, priority = 2)
	public void verifyNoTransactionsMsg() {
		
		String noTxnMsg = TxnDBWidgetLoc.txnFinAppNoTxnsMessage().getText().trim();
		
		Assert.assertTrue(noTxnMsg.contains("There are no transactions to display"));
       
	}
	
	@Test(description = "Adding account with 3 transactions.", priority = 3)
	public void add3TxnsAccount() throws AWTException, InterruptedException {
		
		logger.info("Add Dag Site");
		
		accAddition.linkAccount();
		SeleniumUtil.waitForPageToLoad();
		
		Assert.assertTrue(accAddition.addAggregatedAccount("TxnWidgetTest.site16441.2","site16441.2"));
       
	}
	
	@Test(description = "Adding manual transactions.", priority = 4)
	public void addingManualTransactions() {
		
		String scheduledDate = finApp.getFutureDate(0);
		String endDate = finApp.getFutureDate(30);
		
		PageParser.forceNavigate("Transaction", d);
		SeleniumUtil.waitForPageToLoad();
		
		SeleniumUtil.click(NoEditCatLoc.addNewTransactionButton());
		SeleniumUtil.waitForPageToLoad();
		
		NoEditCatLoc.addMannualTransaction("25000.50", "AmazonPrime Sub", "Withdrawal", "TxnWidget 3 Txns Bank", "Weekly", scheduledDate, endDate, "Entertainment");
		SeleniumUtil.waitForPageToLoad();
		
		
       
	}
	
	
	@Test(description = "AT-77004,AT-77007 : Verify that the recent transactions sections should show the top 5 posted transactions across all of Consumers accounts(Manual and Aggregated).", dependsOnMethods= {"add3TxnsAccount","addingManualTransactions"}, priority = 5)
	public void verifyMaxTop5RecentTransactionsDisplayed() {
		
		List<WebElement> transactionList = TxnDBWidgetLoc.transactionFinAppTxnDesc();
		List<WebElement> balanceList = TxnDBWidgetLoc.transactionFinAppTxnBalance();
		Boolean isManualTxnDisplayed = false;
		
		for (int i = 0; i < transactionList.size() ; i++) {
			
			String foo = transactionList.get(i).getText();
			lastFiveTransactions.add(foo);
			
			String bar = balanceList.get(i).getText();
			lastFiveBalances.add(bar);
			
		}
		
		// Verifying below all transactions in Txn finapp is also displayed in dash board widget!
		
		PageParser.forceNavigate("DashboardPage", d);
		SeleniumUtil.waitForPageToLoad();
		
		for (int i = 0; i < transactionList.size(); i++) {
			
			String bar = TxnDBWidgetLoc.dashboardWidgetTxnDesc().get(i).getText();
			Assert.assertEquals(bar, lastFiveTransactions.get(i));
			
		}
		
		// Verifying below if manually added transaction is also displayed!
		
		for (int i = 0; i < transactionList.size(); i++) {
			
			String txnDec = TxnDBWidgetLoc.dashboardWidgetTxnDesc().get(i).getText();
			if (txnDec.contains("AmazonPrime Sub")) {
				isManualTxnDisplayed = true;
				break;
			}
			
		}
		
		Assert.assertTrue(isManualTxnDisplayed,"**Manually added transaction is not displayde in the Widget!");
		
       
	}
	
	@Test(description = "AT-77005 : Verify the Projected and Pending Transactions not displayed in Recent Transaction Section.",dependsOnMethods= {"add3TxnsAccount","verifyMaxTop5RecentTransactionsDisplayed"}, priority = 6)
	public void verifyNoProjectedTxnsInDashboardWidget() {
		
		Boolean isDisplayed = false;
		
		try {
			isDisplayed = TxnDBWidgetLoc.projectedTxnContainer().isDisplayed() ? true : false;
			
		} catch (Exception e) {
			// Nothing to be done
		}
		
		Assert.assertFalse(isDisplayed,"**Projected transactions are displayed!");
       
	}
	
	@Test(description = "AT-77008 : Verify that each transaction should show Account Name and Account Number in Masked format.",dependsOnMethods= {"add3TxnsAccount"}, priority = 7)
	public void verifyMaskedAccNum() {
		
		for (int i = 0; i < TxnDBWidgetLoc.dashboardWidgetTxnAccNameAndNumber().size() ; i++) {
			Assert.assertTrue(TxnDBWidgetLoc.dashboardWidgetTxnAccNameAndNumber().get(i).getText().contains("| x-"));
		}
       
	}
	
	@Test(description = "AT-77009,AT-77010 : Verify that each transaction should show Transaction Amount.",dependsOnMethods= {"add3TxnsAccount"}, priority = 8)
	public void verifyLast5Balances() {
		
		for (int i = 0; i < TxnDBWidgetLoc.dashboardWidgetTxnAccBalance().size() ; i++) {
			
			String bar = TxnDBWidgetLoc.dashboardWidgetTxnAccBalance().get(i).getText();
			Assert.assertEquals(bar, lastFiveBalances.get(i));
			
		}
       
	}
	
	@Test(description = "AT-77011 : Verify that amount should show 2 digits after decimal.",dependsOnMethods= {"add3TxnsAccount"}, priority = 9)
	public void verify2DigitsAfterDecimal() {
		
		String balanceOne = TxnDBWidgetLoc.dashboardWidgetTxnAccBalance().get(0).getText();
		
		Assert.assertTrue(TxnDBWidgetLoc.verifyTwoDecimalsPresent(balanceOne),"**Amout contains more than 2 decimal points!");
		
	}
	
	@Test(description = "AT-77016 : Verify that tapping/Clicking anywhere on the widget/card including the View More Transactions link should take the user to Transactions FinApp.",dependsOnMethods= {"add3TxnsAccount"}, priority = 10)
	public void verifyClickOnAnyTxnNavigatesToTxn() {
		
		SeleniumUtil.click(TxnDBWidgetLoc.dashboardWidgetTxnDesc().get(0));
		SeleniumUtil.waitForPageToLoad();
		
		Assert.assertTrue(TxnDBWidgetLoc.txnFinAppHeader().isDisplayed(),"**Clicking on Txn did not navigate to Txn fin app!");
		
	}
	
	
	
	
}