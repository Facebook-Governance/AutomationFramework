/**
* Copyright (c) 2018 Yodlee Inc. All Rights Reserved.
*
* This software is the confidential and proprietary information of
* Yodlee, Inc. Use is subject to license terms.
*
* @author Renuka K
*/
package com.omni.pfm.TransactionEnhancement1;

import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.accessibility.examples.testng.webdriver.test.RunAccessibilityTest;
import com.omni.pfm.PageProcessor.PageParser;
import com.omni.pfm.config.Config;
import com.omni.pfm.pages.AccountAddition.AccountAddition;
import com.omni.pfm.pages.Login.L1NodeLogin;
import com.omni.pfm.pages.Login.LoginPage;
import com.omni.pfm.pages.TransactionEnhancement1.AddToCalendar_Transaction_Loc;
import com.omni.pfm.pages.TransactionEnhancement1.Add_Manual_Transaction_Loc;
import com.omni.pfm.pages.TransactionEnhancement1.Transaction_AccountDropDown_Loc;
import com.omni.pfm.pages.TransactionEnhancement1.Transaction_Account_Integration_Loc;
import com.omni.pfm.pages.TransactionEnhancement1.Transaction_Tag_Loc;
import com.omni.pfm.testBase.TestBase;
import com.omni.pfm.utility.PropsUtil;
import com.omni.pfm.utility.SeleniumUtil;
public class Transaction_Account_Integration_Test extends TestBase{
	
		

		AddToCalendar_Transaction_Loc AddToCalendar;
		Transaction_Account_Integration_Loc acct_Tran;
		AccountAddition accountAdd;
		Add_Manual_Transaction_Loc addManual;
		WebDriver webDriver = null;
	    private static final Logger logger = LoggerFactory.getLogger(Transaction_Account_Integration_Test.class);
		public static String userName="";
	    Transaction_AccountDropDown_Loc accountDropDown;
	    Transaction_Tag_Loc tag;

		
		@BeforeClass(alwaysRun = true)

		public void testInit() throws InterruptedException {

			doInitialization("Transaction Tag");
             TestBase.tc = TestBase.extent.startTest("Trans tags", "Login");
           TestBase.test.appendChild(TestBase.tc);
            AddToCalendar=new AddToCalendar_Transaction_Loc(d);
            acct_Tran=new Transaction_Account_Integration_Loc(d);
            accountAdd=new AccountAddition();
            addManual=new  Add_Manual_Transaction_Loc(d); 
            accountDropDown=new Transaction_AccountDropDown_Loc(d);
            tag=new Transaction_Tag_Loc(d);
            d.navigate().refresh();
  		    SeleniumUtil.waitForPageToLoad(5000);

     	 PageParser.forceNavigate("AccountsPage", d);
  		    SeleniumUtil.waitForPageToLoad(5000);
		}
		
		@Test(description = "AT-68820,AT-68821:verfy cash account deatils", priority = 2)

		public void verifyCashaccount() throws Exception {
			//Verify after user click on the below account row it should navigate to transaction finapp. Bank Card Loans Investment

			/*SeleniumUtil.click(acct_Tran.Accountrows().get(0));
			SeleniumUtil.waitForPageToLoad(10000);*/
			acct_Tran.clickAccountRow(PropsUtil.getDataPropertyValue("Accounts_txn_SavingAccountName"));
		//	SeleniumUtil.click(acct_Tran.expand());
			Assert.assertEquals(acct_Tran.bankAccHolderName().getText(),PropsUtil.getDataPropertyValue("AccountBankHolderName"));
			Assert.assertEquals(acct_Tran.bankaccCurrentBalance().getText(), PropsUtil.getDataPropertyValue("AccountBankCurrentBalance"));
			Assert.assertEquals(acct_Tran.bankaccInterest().getText(), PropsUtil.getDataPropertyValue("AccountBankInterestRate"));
			
			SeleniumUtil.click(acct_Tran.expand());
			SeleniumUtil.waitForPageToLoad(5000);
			//Verify the below a attributes displayed in the details section for the below mentioned acocunt types (CD and unknown) : Maturity Date Term Maturity Amount

			Assert.assertEquals(acct_Tran.bankManturityDate().getText(), PropsUtil.getDataPropertyValue("AccountBankMaturityDate"));
			Assert.assertEquals(acct_Tran.bankAccTerm().getText(), PropsUtil.getDataPropertyValue("AccountBankTerm"));
			Assert.assertEquals(acct_Tran.bankAccMaturityAmount().getText(), PropsUtil.getDataPropertyValue("AccountMatuityAmount"));
			Assert.assertEquals(acct_Tran.bankAccAvailableBal().getText(), PropsUtil.getDataPropertyValue("AccountAvailableBalance"));
			
			
		}
		@Test(description = "AT-68821:verify selectecd accounts in transaction account drop down", priority = 2,dependsOnMethods="verifyCashaccount")

		public void verifyAccountselected() throws Exception {
			
			Assert.assertEquals(accountDropDown.accountFilterLink().getText().trim(), PropsUtil.getDataPropertyValue("TransactionAccountNavigatedAccount"), "navigated accounts should be selected");
		}
		
		@Test(description = "AT-68833,AT-68832:verify select account in add manual transaction account dropdown", priority = 3,dependsOnMethods="verifyCashaccount")

		public void verifyAddTransactionAccount() throws Exception {
			addManual.clickMTLink();
			SeleniumUtil.waitForPageToLoad(2000);
			Assert.assertEquals(addManual.accountdropdown().getText().trim(), PropsUtil.getDataPropertyValue("TransactionManualAccountNavigatedAccount"), "navigated accounts should be selected in add manula transaction");
		}
		
		@Test(description = "AT-68834:verify allow to edit account in add manual transaction accounts dropdown", priority = 4,dependsOnMethods="verifyAddTransactionAccount")
		public void verifyeditManualTransactionAccounts() throws Exception {
			//addManual.clickMTLink();
			SeleniumUtil.waitForPageToLoad(2000);
			addManual.selectAccount_MT(PropsUtil.getDataPropertyValue("TransactionAccountEditManualTransactionAccount"));
			Assert.assertEquals(addManual.accountdropdown().getText().trim(), PropsUtil.getDataPropertyValue("TransactionAccountEditManualTransactionAccount"), "navigated accounts should be allow edit accounts in add mnual transaction");
		}
		@Test(description = "AT-68906,AT-68907:verify account drop after changing time filter", priority = 5,dependsOnMethods="verifyeditManualTransactionAccounts")
		public void verifyAccountTabValue() throws Exception {
			addManual.clickClose_MT();
			accountDropDown.clickTimeFilter();
			accountDropDown.selectTimeFilter(PropsUtil.getDataPropertyValue("Accounts_txn_TimeFilter"));
			SeleniumUtil.waitForPageToLoad();
			Assert.assertEquals(accountDropDown.accountFilterLink().getText().trim(), PropsUtil.getDataPropertyValue("TransactionAccountNavigatedAccount"), "navigated accounts should be selected");
           
		}
		@Test(description = "AT-68906,AT-68907,AT-68908:verify accounts drop down after rest the filter", priority = 6,dependsOnMethods="verifyAccountTabValue")
		public void verifyAccountTabValueReset() throws Exception {
			tag.clikcReset();
			SeleniumUtil.waitForPageToLoad(2000);
			Assert.assertEquals(accountDropDown.accountFilterLink().getText().trim(), PropsUtil.getDataPropertyValue("TransactionAccountNavigatedAccount"), "navigated accounts should be selected");
		    Assert.assertEquals(accountDropDown.TransactionTimeFilterLabel().getText().trim(), PropsUtil.getDataPropertyValue("TransactionLast3MonthLabel"), "3 months label is not displaying in time filter Tab");
			Assert.assertEquals(accountDropDown.categoryFilterLink().getText().trim(), PropsUtil.getDataPropertyValue("TransactionAllCategoriesFilterLink"), "all category label is not displaying");
			Assert.assertEquals(tag.tagTab().getText(),PropsUtil.getDataPropertyValue("TagSelect1"));
		}
		@Test(description = "AT-68820,AT-68824:verify card account details", priority = 7,dependsOnMethods="verifyCashaccount")

		public void verifyCardaccount() throws Exception {
			acct_Tran.clickBackToAccount();

			/*SeleniumUtil.click(acct_Tran.Accountrows().get(2));
			
			SeleniumUtil.waitForPageToLoad(5000);*/
			acct_Tran.clickAccountRow(PropsUtil.getDataPropertyValue("Accounts_txn_CardAccountName"));

		//SeleniumUtil.click(acct_Tran.expand());
			Assert.assertEquals(acct_Tran.cardDueDate().getText(),PropsUtil.getDataPropertyValue("AccountCardDueDate"));
			Assert.assertEquals(acct_Tran.cardAccountHolder().getText(), PropsUtil.getDataPropertyValue("AcccountCardAccountHolder"));
			Assert.assertEquals(acct_Tran.cardAsOfDate().getText(), PropsUtil.getDataPropertyValue("AccountCardsAsofDate"));
			
			acct_Tran.clickExpandAccDetails();
			
			Assert.assertEquals(acct_Tran.cardDueAmount().getText(), PropsUtil.getDataPropertyValue("AccountCardAmountDue"));
			Assert.assertEquals(acct_Tran.cardminpayment().getText(), PropsUtil.getDataPropertyValue("AccountCardMinPatment"));
			Assert.assertEquals(acct_Tran.cardTotalCreditLimt().getText(), PropsUtil.getDataPropertyValue("AccountCardToTalCredit"));
			Assert.assertEquals(acct_Tran.cardAvailabelCredit().getText(), PropsUtil.getDataPropertyValue("AccountCardAvailableCredit"));
			Assert.assertEquals(acct_Tran.cardtotalcashLimit().getText(), PropsUtil.getDataPropertyValue("AccountCardTotalCash"));
			Assert.assertEquals(acct_Tran.cardavailablecash().getText(), PropsUtil.getDataPropertyValue("AccountCardAvailableCash"));
			Assert.assertEquals(acct_Tran.cardLastPayment().get(0).getText(), PropsUtil.getDataPropertyValue("AccountCardLatPayment"));
			Assert.assertEquals(acct_Tran.cardLastPaymentDate().getText(),PropsUtil.getDataPropertyValue("AccountCardLatPaymentdate"));
			Assert.assertEquals(acct_Tran.cardAnualPer().getText(), PropsUtil.getDataPropertyValue("AccountCardAnualPer"));
			
			
			
		}
		@Test(description = "AT-68820,AT-68826:verify investmnent account details", priority = 8,dependsOnMethods="verifyCardaccount")

		public void verifyInvestmentaccount() throws Exception {
			acct_Tran.clickBackToAccount();

			/*//Util.click(acct_Tran.Accountrows.get(4));
			SeleniumUtil.click(acct_Tran.investrow());
			
			SeleniumUtil.waitForPageToLoad(5000);*/
			acct_Tran.clickAccountRow(PropsUtil.getDataPropertyValue("Accounts_txn_invstAccountName"));

			//SeleniumUtil.click(acct_Tran.expand());
			Assert.assertEquals(acct_Tran.investmentTotalBalance().getText(), PropsUtil.getDataPropertyValue("AccountInvestmentBalance"));
			Assert.assertEquals(acct_Tran.investmentHolderName().getText(), PropsUtil.getDataPropertyValue("AccountInvestmentAccountHolder"));
			Assert.assertEquals(acct_Tran.investmentAsofNow().getText(), PropsUtil.getDataPropertyValue("AccountInvestmentAsOfDate"));
			
		}
		
		@Test(description = "AT-68820,AT-68825:verify insurence account details", priority = 9,dependsOnMethods="verifyInvestmentaccount")

		public void verifyInsurenceaccount() throws Exception {
			acct_Tran.clickBackToAccount();
			/*SeleniumUtil.click(acct_Tran.Accountrows().get(6));
			//SeleniumUtil.click(acct_Tran.insurencerow());
			SeleniumUtil.waitForPageToLoad(10000);*/
			//SeleniumUtil.click(acct_Tran.expand());
			acct_Tran.clickAccountRow(PropsUtil.getDataPropertyValue("Accounts_txn_InsuAccountName"));
			Assert.assertEquals(acct_Tran.insurencePolicyNumber().getText(), PropsUtil.getDataPropertyValue("AccountInvestmentPolicyNumber"));
			//Assert.assertTrue(acct_Tran.insurenceLastPremiumAmount.getText().trim().contains( PropertyLoader.getProperty("Transaction_Enhancement", "AccountInvestmentPrimiumAmount")));
			Assert.assertTrue( PropsUtil.getDataPropertyValue("AccountInvestmentPrimiumAmount").contains( acct_Tran.insurenceLastPremiumAmount().getText().trim()));
			
		}
		
		@Test(description = "AT-68820,AT-68827:verify loan accounts details", priority = 10,dependsOnMethods="verifyInsurenceaccount")

		public void verifyLoanaccount() throws Exception {
			acct_Tran.clickBackToAccount();
			/*SeleniumUtil.waitForPageToLoad(5000);
			SeleniumUtil.click(acct_Tran.Accountrows().get(5));
			//SeleniumUtil.click(acct_Tran.LoanRow());
			
			SeleniumUtil.waitForPageToLoad(5000);*/
			//SeleniumUtil.click(acct_Tran.expand());
			acct_Tran.clickAccountRow(PropsUtil.getDataPropertyValue("Accounts_txn_LoanAccountName"));
			Assert.assertEquals(acct_Tran.loanDueDate().getText(), PropsUtil.getDataPropertyValue("AccountLoanDueDate"));
			Assert.assertEquals(acct_Tran.loanholdername().getText(),PropsUtil.getDataPropertyValue("AccountLoanAccountHolder"));
			Assert.assertEquals(acct_Tran.loanDueAmount().getText(), PropsUtil.getDataPropertyValue("AccountLoanBillAmount"));
			
			acct_Tran.clickExpandAccDetails();
			Assert.assertEquals(acct_Tran.loanDescription().getText(), PropsUtil.getDataPropertyValue("AccountLoanDescription"));
			Assert.assertTrue(acct_Tran.loanprincipal().getText().trim().contains( PropsUtil.getDataPropertyValue("AccountLoanPrincipalAmount")));
			//Assert.assertEquals(acct_Tran.bankAccMaturityAmount.getText(), PropertyLoader.getProperty("Transaction_Enhancement", "AccountBankHolderName"));
			
			
	}
		
		
		@Test(description = "AT-68823: verify bill account popup", priority = 11,dependsOnMethods="verifyLoanaccount")

		public void verifyBillingaccount() throws Exception {
			acct_Tran.clickBackToAccount();
			/*SeleniumUtil.click(acct_Tran.Accountrows().get(7));
			//SeleniumUtil.click(acct_Tran.bilrow());
			
			SeleniumUtil.waitForPageToLoad(5000);*/
			
			acct_Tran.clickAccountRow(PropsUtil.getDataPropertyValue("Accounts_txn_BillAccountName"));
			//SeleniumUtil.click(acct_Tran.expand());
			Assert.assertTrue(acct_Tran.billDueDate().getText().trim().contains( PropsUtil.getDataPropertyValue("AccountBillDuedate")));
			Assert.assertTrue(acct_Tran.billAccountHolder().getText().trim().contains( PropsUtil.getDataPropertyValue("AccountBillAccountHolder")));
			Assert.assertTrue(acct_Tran.billMinPayment().getText().trim().contains( PropsUtil.getDataPropertyValue("AccountBillMinPayment")));
			acct_Tran.clickExpandAccDetails();
			Assert.assertTrue(acct_Tran.billLastPayment().getText().trim().contains( PropsUtil.getDataPropertyValue("AccountBillLatPayment")));
			
			
		}
		
		
		@Test(description = "AT-68820,AT-68828,AT-68835:verify reward accounts popup", priority = 12,dependsOnMethods="verifyBillingaccount")

		public void verifyRewardaccount() throws Exception 
		{
			SeleniumUtil.click(acct_Tran.billClose());
			SeleniumUtil.waitForPageToLoad(5000);
			/*SeleniumUtil.click(acct_Tran.Accountrows().get(8));
			//SeleniumUtil.click(acct_Tran.rewardRow());
			SeleniumUtil.waitForPageToLoad(5000);*/
			//SeleniumUtil.click(acct_Tran.expand());
			acct_Tran.clickAccountRow(PropsUtil.getDataPropertyValue("Accounts_txn_RewardAccountName"));

			Assert.assertTrue(acct_Tran.rewarHolderName().getText().trim().contains( PropsUtil.getDataPropertyValue("AccountRewardHoldername")));
			Assert.assertTrue(acct_Tran.rewExpDate().getText().trim().contains( PropsUtil.getDataPropertyValue("AccountRewardEcpDate")));
		}
		

		@Test(description = "AT-68829,AT-68835:verify real estate account popup", priority = 13,dependsOnMethods="verifyRewardaccount")

		public void verifyRalEstateAccount() throws Exception 
		{
			SeleniumUtil.click(acct_Tran.billClose());
      		    SeleniumUtil.waitForPageToLoad(5000);
			logger.info("vreify the real estate account header");
			
			accountAdd.addManualRealEstateAccount(PropsUtil.getDataPropertyValue("TransactionRealEstateAccount"), PropsUtil.getDataPropertyValue("TransactionRealEstateEstaimation"),true);
			 PageParser.forceNavigate("AccountsPage", d);
   		    SeleniumUtil.waitForPageToLoad(5000);
			acct_Tran.clickAccountRow(PropsUtil.getDataPropertyValue("Accounts_txn_REAccountName"));
			SeleniumUtil.waitForPageToLoad(5000);
			Assert.assertEquals(acct_Tran.RealEstateaccount_TAI().getText(), PropsUtil.getDataPropertyValue("TransactionRealEstateAccount"), "real account header");
			Assert.assertEquals(acct_Tran.RealEstateAmount_TAI().getText(), PropsUtil.getDataPropertyValue("TransactionRealEstateEstaimation1"), "real amount");

		}
		@Test(description = "AT-68830,AT-68835:verify liability accounts popup", priority = 14,dependsOnMethods="verifyRalEstateAccount")

		public void verifyLiabilitiesAccount() throws Exception 
		{
			SeleniumUtil.click(acct_Tran.billClose());
  		    SeleniumUtil.waitForPageToLoad(5000);
			logger.info("vreify the real estate account header");
			accountAdd.addManualAccount(PropsUtil.getDataPropertyValue("TransactionManualAccountType"), 
					PropsUtil.getDataPropertyValue("TransactionManualAccountName"), PropsUtil.getDataPropertyValue("TransactionManualAccountName"), 
                    PropsUtil.getDataPropertyValue("TransactionManualAccountBalance"), PropsUtil.getDataPropertyValue("TransactionManualAccountNumber"),
                    PropsUtil.getDataPropertyValue("TransactionManualAccountDueAmount"), addManual.targateDate1(2), "");
			 PageParser.forceNavigate("AccountsPage", d);
	   		    SeleniumUtil.waitForPageToLoad(5000);
				acct_Tran.clickAccountRow(PropsUtil.getDataPropertyValue("TransactionManualAccountName"));
				SeleniumUtil.waitForPageToLoad(5000);
				Assert.assertEquals(acct_Tran.RealEstateaccount_TAI().getText(), PropsUtil.getDataPropertyValue("TransactionManualAccountName"), "real account header");
				Assert.assertEquals(acct_Tran.RealEstateAmount_TAI().getText(), PropsUtil.getDataPropertyValue("TransactionAssetAccountBalance"), "real amount");

			
		}

      @Test(description = "AT-68831,AT-68835:verify asset account popup", priority = 15,dependsOnMethods="verifyLiabilitiesAccount")

      public void verifyAssetAccount() throws Exception 
      {
    	    SeleniumUtil.click(acct_Tran.billClose());
		    SeleniumUtil.waitForPageToLoad(5000);
	        accountAdd.addManualAccount(PropsUtil.getDataPropertyValue("TransactionAssetAccountType"), 
			PropsUtil.getDataPropertyValue("TransactionAssetAccountName"), PropsUtil.getDataPropertyValue("TransactionAssetAccountName"), 
            PropsUtil.getDataPropertyValue("TransactionManualAccountBalance"), PropsUtil.getDataPropertyValue("TransactionManualAccountNumber"),
            PropsUtil.getDataPropertyValue("TransactionManualAccountDueAmount"), addManual.targateDate1(2), "");
	        PageParser.forceNavigate("AccountsPage", d);
   		    SeleniumUtil.waitForPageToLoad(5000);
			acct_Tran.clickAccountRow(PropsUtil.getDataPropertyValue("TransactionAssetAccountName"));
			SeleniumUtil.waitForPageToLoad(5000);
			Assert.assertEquals(acct_Tran.RealEstateaccount_TAI().getText(), PropsUtil.getDataPropertyValue("TransactionAssetAccountName"), "real account header");
			Assert.assertEquals(acct_Tran.RealEstateAmount_TAI().getText(), PropsUtil.getDataPropertyValue("TransactionAssetAccountBalance"), "real amount");

		
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
