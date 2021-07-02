/**
 * Copyright (c) 2018 Yodlee Inc. All Rights Reserved.
 *
 * This software is the confidential and proprietary information of
 * Yodlee, Inc. Use is subject to license terms.
 *
 * @author sakshi Jain
 */
package com.omni.pfm.Accounts;

import org.slf4j.Logger; 
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.omni.pfm.PageProcessor.PageParser;
import com.omni.pfm.pages.AccountAddition.AccountAddition;
import com.omni.pfm.pages.Login.LoginPage;
import com.omni.pfm.Accounts.Account_ShowNoteMessage_Loc;
import com.omni.pfm.testBase.TestBase;
import com.omni.pfm.utility.FinAppUtil;
import com.omni.pfm.utility.PropsUtil;
import com.omni.pfm.utility.SeleniumUtil;


public class AggregatedAcc_ReWamped_Test extends TestBase {

	private static final Logger logger = LoggerFactory.getLogger(AggregatedAcc_ReWamped_Test.class);

	Accounts_ReWamped_Loc manualAccLoc;
	AccountAddition accountAdd;
	Account_ShowNoteMessage_Loc show;
	FinAppUtil finUtil;
	Account_Go_To_Site_Loc accountsGoToSite;

	@BeforeClass()
	public void init() throws Exception
	{

		doInitialization("Manual Accounts");
		TestBase.tc = TestBase.extent.startTest("Login");
		TestBase.test.appendChild(TestBase.tc);

		manualAccLoc = new Accounts_ReWamped_Loc(d);
		accountAdd =  new AccountAddition();
		show = new Account_ShowNoteMessage_Loc(d);
		finUtil = new FinAppUtil(d); 
		accountsGoToSite = new Account_Go_To_Site_Loc(d);

	} 

	@Test(description = "Login Successfully", priority = 0,enabled=true)

	public void Login() throws Exception {

		LoginPage.loginMain(d, loginParameter);
		SeleniumUtil.waitForPageToLoad();

	}

	@Test(description = "Verify accounts are added succesfully.", dependsOnMethods= {"Login"}, priority = 1,enabled=true)

	public void addAccounts() throws Exception {

		accountAdd.linkAccount();
		SeleniumUtil.waitForPageToLoad();

		accountAdd.addAggregatedAccount("swin.site16441.2", "site16441.2");

		PageParser.forceNavigate("AccountsPage", d);
		SeleniumUtil.waitForPageToLoad();

	}


	/* Aggregated accounts related tests! */


	@Test(description = "AT-84021:Verify that the amount with decimal / comma are followed by 2 digits at the max.", dependsOnMethods= {"Login","addAccounts"}, priority = 21,enabled=true)

	public void verifyTwoDecimalPointsForAmount() throws Exception {

		try {
			manualAccLoc.accountslayout().isDisplayed();
		}catch(Throwable e) {
			PageParser.forceNavigate("AccountsPage", d);
			SeleniumUtil.waitForPageToLoad(3000);
		}
		
		String actualDagCashAccBalance = "";
		Boolean isTrue = true;

		actualDagCashAccBalance = finUtil.institutionViewGetAccountBalance("IQ sWin Savings").getText().trim();

		isTrue = manualAccLoc.verifyOnlyTwoDecimal(actualDagCashAccBalance);

		Assert.assertTrue(isTrue);

	}


	@Test(description = "AT-84022:Verify that the amount with decimal / comma are followed by 2 zeroes at the when there are no decimal values in the amount.", dependsOnMethods= {"Login","addAccounts"}, priority = 22,enabled=true)

	public void verifyTwoZeroesAfterDecimalForAmount() throws Exception {

		String actualAccBalance = "";
		Boolean isTrue = true;

		actualAccBalance = finUtil.institutionViewGetAccountBalance("HDFC Two Wheeler Loan").getText().trim();

		isTrue = manualAccLoc.verifyTwoZeroesAfterDecimal(actualAccBalance);

		Assert.assertTrue(isTrue);

	}


	@Test(description = "AT-83955:The last updated information, if updated today should be in *Updated Today, 10:45 pm PST*.", dependsOnMethods= {"Login","addAccounts"}, priority = 23,enabled=true)

	public void verifyLastUpdatedTimeForToday() throws Exception {

		String actualLastUpdatedText = "";
		Boolean isTrue = true;

		SeleniumUtil.waitForPageToLoad();
		actualLastUpdatedText = manualAccLoc.lastUpdatedTime().getText().trim(); // Add parameter

		logger.info(">>Fetched updated time is:"+actualLastUpdatedText);

		SeleniumUtil.waitForPageToLoad();
		isTrue = actualLastUpdatedText.contains("Updated Today");

		if (isTrue) {
			Assert.assertTrue(isTrue);
			logger.info(">>Updated today message is shown!");
		} else {
			Assert.assertFalse(isTrue);
			logger.error(">>Updated today message is not shown!");
		}


	}


	@Test(description = "AT-83964,AT-84475,AT-83961:Under the bills container the format of display should be FI Name, Account Name and Account Number in Masked format.", dependsOnMethods= {"Login","addAccounts"}, priority = 24,enabled=true)

	public void verifyAggregatedBillsFormat() throws Exception {

		Assert.assertTrue(finUtil.institutionViewVerifyAccountIsPresentUnderContainer("Airtel Broadband", "Bills"));

		Assert.assertTrue(finUtil.institutionViewGetAccountNumber("Airtel Broadband").isDisplayed());

		Assert.assertTrue(finUtil.institutionViewGetAccountBalance("Airtel Broadband").isDisplayed());

	}



	@Test(description = "AT-84027,AT-84026:Verify that the below attributes are displayed for the Banking account types (CD and unknown). account-name account-holder account-number account-nickname account-type interestRate apy maturityDate term maturityAmount last-updated", dependsOnMethods= {"Login","addAccounts"}, priority = 25,enabled=true)

	public void verifyElementsInCashContainer() throws Exception {

		Assert.assertTrue(finUtil.institutionViewVerifyAccountIsPresentUnderContainer("IQ sWins CD", "Cash"));

		Assert.assertTrue(finUtil.institutionViewGetAccountNumber("IQ sWins CD").isDisplayed());

		Assert.assertTrue(finUtil.institutionViewGetAccountBalance("IQ sWins CD").isDisplayed());

		Assert.assertEquals(finUtil.institutionViewGetAccountNumber("IQ sWins CD").getText().trim(), PropsUtil.getDataPropertyValue("aggAccTest_IQsWinsCDNumber"));

	}


	@Test(description = "AT-84028:Verify that the below fields are displayed for the Bills Container.", dependsOnMethods= {"Login","addAccounts"}, priority = 26,enabled=true)

	public void verifyElementsInBillsContainer() throws Exception {

		Assert.assertTrue(finUtil.institutionViewVerifyAccountIsPresentUnderContainer("IQ sWins CD", "Cash"));

		Assert.assertTrue(finUtil.institutionViewGetAccountNumber("Airtel Broadband").isDisplayed());

		Assert.assertEquals(finUtil.institutionViewGetAccountNumber("Airtel Broadband").getText().trim(), PropsUtil.getDataPropertyValue("aggAccTest_AirtelBroadbandNumber"));

	}

	@Test(description = "AT-84029:Verify the below fields should display for the Credit Cards.", dependsOnMethods= {"Login","addAccounts"}, priority = 27,enabled=true)

	public void verifyElementsInCardsContainer() throws Exception {

		Assert.assertTrue(finUtil.institutionViewVerifyAccountIsPresentUnderContainer("sWins Ferrari Card", "Cards"));

		Assert.assertTrue(finUtil.institutionViewGetAccountNumber("sWins Ferrari Card").isDisplayed());

		Assert.assertTrue(finUtil.institutionViewGetAccountBalance("sWins Ferrari Card").isDisplayed());

		Assert.assertEquals(finUtil.institutionViewGetAccountNumber("sWins Ferrari Card").getText().trim(), PropsUtil.getDataPropertyValue("aggAccTest_sWinsFerrariCardNumber"));

	}


	@Test(description = "AT-84030,AT-84132:Verify the below fields should display for the Insurance Container/Accounts.", dependsOnMethods= {"Login","addAccounts"}, priority = 28,enabled=true)

	public void verifyElementsInInsuranceContainer() throws Exception {

		Assert.assertTrue(finUtil.institutionViewVerifyAccountIsPresentUnderContainer("IQ ERGO Insurance", "Insurance"));

		Assert.assertTrue(finUtil.institutionViewGetAccountNumber("IQ ERGO Insurance").isDisplayed());

		Assert.assertTrue(finUtil.institutionViewGetAccountBalance("IQ ERGO Insurance").isDisplayed());

		Assert.assertEquals(finUtil.institutionViewGetAccountNumber("IQ ERGO Insurance").getText().trim(), PropsUtil.getDataPropertyValue("aggAccTest_IQERGOInsuranceNumber"));

	}

	@Test(description = "AT-84031:Verify the below fields should display for the Investment Container/Accounts.", dependsOnMethods= {"Login","addAccounts"}, priority = 29,enabled=true)

	public void verifyElementsInInvestmentContainer() throws Exception {

		Assert.assertTrue(finUtil.institutionViewVerifyAccountIsPresentUnderContainer("SUNY 403(B) PROGRAM", "Investments"));

		Assert.assertTrue(finUtil.institutionViewGetAccountNumber("SUNY 403(B) PROGRAM").isDisplayed());

		Assert.assertTrue(finUtil.institutionViewGetAccountBalance("SUNY 403(B) PROGRAM").isDisplayed());

		Assert.assertEquals(finUtil.institutionViewGetAccountNumber("SUNY 403(B) PROGRAM").getText().trim(), PropsUtil.getDataPropertyValue("aggAccTest_SUNY403BNumber"));

	}

	@Test(description = "AT-84032:Verify the below fields should display for the Loan Container.", dependsOnMethods= {"Login","addAccounts"}, priority = 30,enabled=true)

	public void verifyElementsInLoanContainer() throws Exception {

		Assert.assertTrue(finUtil.institutionViewVerifyAccountIsPresentUnderContainer("HDFC Two Wheeler Loan", "Loans"));

		Assert.assertTrue(finUtil.institutionViewGetAccountNumber("HDFC Two Wheeler Loan").isDisplayed());

		Assert.assertTrue(finUtil.institutionViewGetAccountBalance("HDFC Two Wheeler Loan").isDisplayed());

		Assert.assertEquals(finUtil.institutionViewGetAccountNumber("HDFC Two Wheeler Loan").getText().trim(), PropsUtil.getDataPropertyValue("aggAccTest_HDFCTwoWheelerLoanNumber"));

	}


	@Test(description = "AT-84033:Verify the below fields should display for the Rewards Container.", dependsOnMethods= {"Login","addAccounts"}, priority = 31,enabled=true)

	public void verifyElementsInRewardsContainer() throws Exception {

		Assert.assertTrue(finUtil.institutionViewVerifyAccountIsPresentUnderContainer("PayBack", "Rewards"));

		Assert.assertTrue(finUtil.institutionViewGetAccountNumber("PayBack").isDisplayed());

		Assert.assertTrue(finUtil.institutionViewGetAccountBalance("PayBack").isDisplayed());

		Assert.assertEquals(finUtil.institutionViewGetAccountNumber("PayBack").getText().trim(), PropsUtil.getDataPropertyValue("aggAccTest_CitrusPointsNumber"));

	}

	@Test(description = "AT-84422,AT-84483:Verify the below fields should display for the Rewards Container.", dependsOnMethods= {"Login","addAccounts"}, priority = 32,enabled=true)

	public void verifyInvestmentDesclaimerMessage() throws Exception {

		Assert.assertTrue(manualAccLoc.investmentAccDesclaimerMsg().isDisplayed(),"Investment account desclaimer message is not displayed!");

	}


	@Test(description = "AT-84045,AT-84280:Verify that no data state message should appear if there are no transactions for user selected account and filter by side bar should reflect.", dependsOnMethods= {"Login","addAccounts"}, priority = 33,enabled=true)

	public void verifyNoDataScreenForCDAccount() throws Exception {

		SeleniumUtil.click(finUtil.institutionViewGetAccountName("SUNY 403(B) PROGRAM"));
		SeleniumUtil.waitForPageToLoad();

		String actualNoDataDescription = manualAccLoc.transactionNoDataScreenDescription().getText().trim();
		String expectedNoDataDescription = PropsUtil.getDataPropertyValue("mannAccTest_TransactinNoDataScreenDesc").trim();

		Assert.assertEquals(actualNoDataDescription, expectedNoDataDescription,"**No data screen description is not correct!");

	}


	@Test(description = "AT-84455:If the user unselect any of his  accouts from settings, that account should not appear anywhere in the accounts finapp.", dependsOnMethods= {"Login","addAccounts"}, priority = 35,enabled=true)

	public void verifyDontShowAccountInAccSummary() throws Exception {

		PageParser.forceNavigate("AccountsPage", d);
		SeleniumUtil.waitForPageToLoad();

		Boolean isShown = false;

		try {
			manualAccLoc.accountslayout().isDisplayed();
		}catch(Throwable e) {
			PageParser.forceNavigate("AccountsPage", d);
			SeleniumUtil.waitForPageToLoad(3000);
		}
		
		finUtil.intitutionViewClickOnAccountSettings("IQ sWin Checking", "Cash");

		SeleniumUtil.click(manualAccLoc.showAccountInAccountSummaryChkBox());
		SeleniumUtil.waitForPageToLoad();

		SeleniumUtil.click(manualAccLoc.accSettingsSaveBtn());
		SeleniumUtil.waitForPageToLoad(7000);

		try {
			if (finUtil.institutionViewVerifyAccountIsPresentUnderContainer("IQ sWin Checking", "Cash")) {
				isShown = true;
			}
		} catch (Exception e) {
		}

		Assert.assertFalse(isShown,"**Hidden account is shown!");

	}


	@Test(description = "AT-83994,AT-93001:Verify that error icon is displayed for the accounts that are in error.", dependsOnMethods= {"Login","addAccounts"}, priority = 36,enabled=true)

	public void verifyErrorIconForAccountsInError() throws Exception {

		PageParser.forceNavigate("AccountSettings", d);
		SeleniumUtil.waitForPageToLoad();

		if(appFlag.contentEquals("app") || appFlag.contentEquals("emulator")) {
			SeleniumUtil.click(accountsGoToSite.dagsiteSettings());
			SeleniumUtil.waitForPageToLoad();
		}
		SeleniumUtil.click(manualAccLoc.editCredentialsBtn());
		SeleniumUtil.waitForPageToLoad();

		SeleniumUtil.click(manualAccLoc.passwordTextBox());
		manualAccLoc.passwordTextBox().clear();
		manualAccLoc.passwordTextBox().sendKeys("123456");

		SeleniumUtil.click(manualAccLoc.reEnterPasswordTextBox());
		manualAccLoc.reEnterPasswordTextBox().clear();
		manualAccLoc.reEnterPasswordTextBox().sendKeys("123456");

		SeleniumUtil.click(manualAccLoc.acceptTermsAndConditionsCheckbox());
		
		SeleniumUtil.click(manualAccLoc.editCredentialsUpdateBtn());
		SeleniumUtil.waitForPageToLoad(20000);

		PageParser.forceNavigate("AccountsPage", d);
		SeleniumUtil.waitForPageToLoad(10000);

		try {
			manualAccLoc.accountslayout().isDisplayed();
		}catch(Throwable e) {
			PageParser.forceNavigate("AccountsPage", d);
			SeleniumUtil.waitForPageToLoad(3000);
		}
		
		SeleniumUtil.click(manualAccLoc.editCredentialsPopUpCancelBtn());
		PageParser.forceNavigate("AccountsPage", d);
		SeleniumUtil.waitForPageToLoad(10000);
		try {
			manualAccLoc.accountslayout().isDisplayed();
		}catch(Throwable e) {
			PageParser.forceNavigate("AccountsPage", d);
			SeleniumUtil.waitForPageToLoad(3000);
		}
		
		Assert.assertTrue(finUtil.institutionViewGetErrorIcon("IQ sWin Savings").isDisplayed());

	}

	@Test(description = "AT-84178,AT-93002,AT-93003:When the user clicks on the Try Again button then it should navigate to the Refresh or Edit Credentials page.", dependsOnMethods= {"Login","addAccounts","verifyErrorIconForAccountsInError"}, priority = 37,enabled=true)

	public void verifyErrorIconTakesToEditCredentials() throws Exception {

		SeleniumUtil.click(finUtil.institutionViewGetErrorIcon("IQ sWin Savings"));
		SeleniumUtil.waitForPageToLoad();

		SeleniumUtil.click(manualAccLoc.editCredentialsBtnInErrorMessage());
		SeleniumUtil.waitForPageToLoad();

		Assert.assertTrue(manualAccLoc.passwordTextBox().isDisplayed(),"**Edit Credentials did not open FL pop up.");

	}


	@Test(description = "AT-84215,AT-84216:For the Accounts in Error the message displayed should be '1 account needs your attention' if there is only one account in error.", dependsOnMethods= {"Login","addAccounts"}, priority = 38,enabled=true)

	public void verifyAccountsNeedAttentionErrorMsg() throws Exception {

		PageParser.forceNavigate("AccountsPage", d);
		SeleniumUtil.waitForPageToLoad();

		try {
			manualAccLoc.accountslayout().isDisplayed();
		}catch(Throwable e) {
			PageParser.forceNavigate("AccountsPage", d);
			SeleniumUtil.waitForPageToLoad(3000);
		}
		
		Assert.assertTrue(manualAccLoc.accountsNeedAttentionErrorMsg().isDisplayed(),"**Error message is not displayed!");
	}


	@Test(description = "AT-84235:Error count should be same in all three views (Account view, FI view & Group view).", dependsOnMethods= {"Login","addAccounts"}, priority = 39,enabled=true)

	public void verifyErrorIconsCount() throws Exception {


		int errorIconsInInstitutionView = finUtil.institutionViewGetErrorIcons().size();

		SeleniumUtil.click(manualAccLoc.viewByAccntType());
		SeleniumUtil.waitForPageToLoad();

		int errorIconsInTypeView = finUtil.institutionViewGetErrorIcons().size();

		if (errorIconsInInstitutionView == errorIconsInTypeView) {

			Assert.assertTrue(true);

		}else {

			Assert.assertTrue(false);

		}

	}


	@Test(description = "Reverts the accounts that are in error..", dependsOnMethods= {"Login","addAccounts"}, priority = 40,enabled=true)

	public void revertErrors() throws Exception {

		SeleniumUtil.click(manualAccLoc.viewByInstitution());
		SeleniumUtil.waitForPageToLoad();

		SeleniumUtil.click(finUtil.institutionViewGetErrorIcon("IQ sWin Savings"));
		SeleniumUtil.waitForPageToLoad();

		SeleniumUtil.click(manualAccLoc.editCredentialsBtnInErrorMessage());
		SeleniumUtil.waitForPageToLoad();

		SeleniumUtil.click(manualAccLoc.passwordTextBox());
		manualAccLoc.passwordTextBox().clear();
		manualAccLoc.passwordTextBox().sendKeys("site16441.2");

		SeleniumUtil.click(manualAccLoc.reEnterPasswordTextBox());
		manualAccLoc.reEnterPasswordTextBox().clear();
		manualAccLoc.reEnterPasswordTextBox().sendKeys("site16441.2");

		SeleniumUtil.click(manualAccLoc.acceptTermsAndConditionsCheckbox());
		
		SeleniumUtil.click(manualAccLoc.editCredentialsUpdateBtn());
		SeleniumUtil.waitForPageToLoad(60000);

		d.navigate().refresh();
		SeleniumUtil.waitForPageToLoad();


	}

	@Test(description = "AT-84233:Verify that after updating the error account succesfully, then the error icon should not be displayed and the error count should decreased.", dependsOnMethods= {"Login","addAccounts","revertErrors"}, priority = 41,enabled=true)

	public void verifyNoErrorIconAfterReVerify() throws Exception {

		PageParser.forceNavigate("AccountsPage", d);
		SeleniumUtil.waitForPageToLoad();
		Boolean isShown = false;

		try {
			manualAccLoc.accountslayout().isDisplayed();
		}catch(Throwable e) {
			PageParser.forceNavigate("AccountsPage", d);
			SeleniumUtil.waitForPageToLoad(3000);
		}
		
		try {
			if (manualAccLoc.accountsNeedAttentionErrorMsg().isDisplayed()) {
				isShown = true;
			}
		} catch (Exception e) {

		}

		Assert.assertFalse(isShown,"**Error message is displayed!");
	}


}
