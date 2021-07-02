/*******************************************************************************
 * Copyright (c) 2018 Yodlee Inc. All Rights Reserved.
 * 
 * This software is the confidential and proprietary information of
 * Yodlee, Inc. Use is subject to license terms.
 * 
 * @author
 ******************************************************************************/
package com.omni.pfm.Dashboard;

import java.awt.AWTException; 
import java.util.List;

import org.json.JSONException;
import org.openqa.selenium.WebElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.omni.pfm.pages.AccountAddition.AccountAddition;
import com.omni.pfm.pages.Dashboard.AccountSharing_Loc;
import com.omni.pfm.pages.Dashboard.Account_Loc;
import com.omni.pfm.pages.Dashboard.DashBoradInvestmentLoc;
import com.omni.pfm.pages.Dashboard.NetworthLoc;
import com.omni.pfm.pages.Dashboard.Spending_Loc;
import com.omni.pfm.pages.Login.LoginPage;
import com.omni.pfm.pages.Login.LoginPage_SAML3;
import com.omni.pfm.pages.ManageSharing.ManageSharing_Loc;
import com.omni.pfm.rest.ysl;
import com.omni.pfm.testBase.TestBase;
import com.omni.pfm.utility.PropsUtil;
import com.omni.pfm.utility.SeleniumUtil;

/**
 * Copyright (c) 2018 Yodlee Inc. All Rights Reserved.
 *
 * This software is the confidential and proprietary information of Yodlee, Inc.
 * Use is subject to license terms.
 *
 * @author Shivaprasad
 */


public class AccountSharing_Dashboard_Advisor_Sharing_Test extends TestBase {
			
	private static final Logger logger = LoggerFactory.getLogger(AccountSharing_Dashboard_Changes_Test.class);
	AccountSharing_Loc share;
	AccountAddition accountAddition;
	LoginPage login;
	LoginPage_SAML3 SAML;
	public static String advRefId = null, investorName = null, advisorName = null;
	Account_Loc account;
	ManageSharing_Loc sharing;

	Spending_Loc spending;
	NetworthLoc networth;
	DashBoradInvestmentLoc dashBoardInvestment;

	@BeforeClass(alwaysRun = true)
	public void init() throws Exception {
		doInitialization("Dashboard Account Sharing");
		TestBase.tc = TestBase.extent.startTest("Login", "Login");
		TestBase.test.appendChild(TestBase.tc);

		accountAddition = new AccountAddition();
		share = new AccountSharing_Loc(d);
		login = new LoginPage();
		SAML = new LoginPage_SAML3();
		account = new Account_Loc(d);
		sharing = new ManageSharing_Loc(d);
		spending = new Spending_Loc(d);
		networth = new NetworthLoc(d);
		dashBoardInvestment = new DashBoradInvestmentLoc(d);

	}
	/**
	 *  Create and advisor Add an aggregated account to him Share that account to the  investor using YSL API
	 *  
	 * @throws AWTException
	 * @throws InterruptedException
	 * @throws JSONException
	 */

	@Test(description = "Create an advisor for above investor Name..", priority = 1)
	public void createAdvisor1() throws AWTException, InterruptedException, JSONException {

		advRefId = share.getAdvisorReferanceID();
		investorName = share.getInvestorName();

		logger.info(">>>>> Creating an advisor with extra params segmentName=advisor&IID=Investorname.. ");
		advisorName = SAML.createAdvisor(d, investorName, advRefId, "10003600");
		SeleniumUtil.waitForPageToLoad();

		Assert.assertNotNull(advisorName,".. Advisor creation failed.");

	}

	@Test(description = "Create an advisor for above investor Name..", priority = 2, dependsOnMethods = {
			"createAdvisor1" })
	public void addAccountToAdvisor() throws AWTException, InterruptedException {
		accountAddition.linkAccountFastlink();
		SeleniumUtil.waitForPageToLoad(2000);

		accountAddition.addAggregatedAccount("svb_y.site16441.1", "site16441.1");
		SeleniumUtil.waitForPageToLoad();

	}

	/**
	 * Cretae an investor
	 * 
	 * @throws AWTException
	 * @throws InterruptedException
	 */
	@Test(description = "Create an investor for above advisor Name..", priority = 3, dependsOnMethods = {
			"createAdvisor1" })
	public void createInvestor1() throws AWTException, InterruptedException {
		boolean expectedInvestor = false;
		if (advisorName != null) {
			logger.info(">>>>> Creating an investor and adding pre-populated accounts..");
			SAML.createInvestor(d, advisorName, investorName, "svb_y.site16441.1", "site16441.1");
			SeleniumUtil.waitForPageToLoad();

			expectedInvestor = true;
		}

		Assert.assertTrue(expectedInvestor);

	}

	@Test(description = "Create an advisor for above investor Name..", priority = 4, dependsOnMethods = {
			"createAdvisor1" })
	public void shareAdvisorAccount() throws AWTException, InterruptedException, JSONException {
		logger.info(">>>>> Sharing  advisor accounts to investor..");
		String samlResponse = SAML.loginResponse(d, advisorName, investorName);
		ysl.shareAdvisorAccount(samlResponse, advisorName, investorName);
	}

	/**
	 * Login as Investor and view the account shared
	 * 
	 * 
	 * @throws Exception
	 */
	@Test(description = "AT-73202Verify Account Shared By Advisor..", priority = 5, dependsOnMethods = {
			"createInvestor1" })
	public void investorLogin1() throws Exception {
		SAML.login(d, investorName, null, "10003700");
		SeleniumUtil.waitForPageToLoad();

		Assert.assertTrue(share.finappHeader().isDisplayed(), ">>>>> Investor Acount is not displaying.");

	}

	@Test(description = "AT-73202:Verify Account Widget", priority = 6, dependsOnMethods = { "investorLogin1" })
	public void verifyAccountWidget() throws Exception {
		logger.info(">>>>> Verifying Account Widget..");
		SeleniumUtil.waitForPageToLoad();
		Assert.assertEquals(account.accountsHeader().getText(), PropsUtil.getDataPropertyValue("Accounts_Header1"),
				"Account Widget not present..");
	}

	@Test(description = "AT-73210:Verify after clicking on view all account button it should navigate to accounts finapp.", priority = 7, groups = {
			"Desktop" }, enabled = true, dependsOnMethods = { "investorLogin1" })
	public void verifyViewAllAccounts() throws Exception {

		logger.info(">>>>> Verify view all accounts button is displaying or not.");
		boolean allAccountButton = account.viewAllAccountButton().isDisplayed();

		SeleniumUtil.click(account.viewAllAccountButton());
		SeleniumUtil.waitForPageToLoad();

		Assert.assertTrue(allAccountButton, ">>>>> View All Accout button is not displayed..");

	}

	@Test(description = "AT-73211:Verify Account Navigation", priority = 8, dependsOnMethods = { "investorLogin1" })
	public void verifyAccountsPage() throws Exception {
		logger.info(">>>>> Verify after clicking on view all account button it should navigate to accounts finapp.");

		boolean accountHeader = account.accountsheader().isDisplayed();

		SeleniumUtil.refresh(d);
		SeleniumUtil.waitForPageToLoad();
		Assert.assertTrue(accountHeader, ">>>>> Accounts Header is not displayed..");
	}

	@Test(description = "AT-73212:Verify Accounts Click will redirect to Transaction Page", priority = 9, dependsOnMethods = {
			"investorLogin1" })
	public void verifyNavigationToTransaction() throws Exception {
		List<WebElement> l = account.accountName();
		for (int i = 0; i < l.size(); i++) {
			SeleniumUtil.click(l.get(i));
			SeleniumUtil.waitForPageToLoad();
			Assert.assertEquals(share.finappHeader().getText(), "Transactions");
		}
	}

	@Test(description = "AT-73219:Verify Transaction Widget", priority = 10, dependsOnMethods = { "investorLogin1" })
	public void verifyTransactionWidget() throws Exception {
		logger.info(">>>>> Verifying the transaction widget..");
		Assert.assertTrue(share.transactionWidgetHeader().isDisplayed(),
				">>>>> transaction Widget nod displaying in Dashboard");

	}

	@Test(description = "AT-73219:Verify Transaction Data is Displayed..", priority = 11, dependsOnMethods = {
			"investorLogin1" })
	public void verifyTransactionData() throws Exception {
		logger.info(">>>>> Verifying Transactions are displaying in the widget..");
		Assert.assertTrue(share.transactionDetails().isDisplayed(), ">>>>> Account Transactions are not displaying.");
	}

	@Test(description = "AT-73219:Verify Individual Transactions are displaying under card", priority = 12, dependsOnMethods = {
			"investorLogin1" })
	public void verifyTransactionIndividual() throws Exception {
		logger.info(">>>>> Verifying individual transactions..");
		for (int i = 0; i < share.individualTransactions().size(); i++) {
			logger.info(share.individualTransactions().get(i).getText());
		}
	}

	@Test(description = "AT-73219:Verify Accounts Widget", priority = 13, dependsOnMethods = { "investorLogin1" })
	public void verifyAccWidget() {
		logger.info(">>>>> Verifying account widget");
		SeleniumUtil.refresh(d);
		SeleniumUtil.waitForPageToLoad(5000);
		Assert.assertEquals(account.accountsHeader().getText(), PropsUtil.getDataPropertyValue("Accounts_Header1"),
				"Account Widget not present..");

	}

	@Test(description = "AT-73226:Verify Spending Widget..", priority = 14, dependsOnMethods = { "investorLogin1" })
	public void verifySpendingWidgetHeader() throws Exception {
		logger.info(">>>>>Verifying spending header in spending widget");
		Assert.assertEquals(spending.spendingHeader().getText(), PropsUtil.getDataPropertyValue("Spending_Header"));

	}

	@Test(description = "AT-73228:Verify Spending Month Bar", priority = 15, dependsOnMethods = { "investorLogin1" })
	public void verifySpendingMonthBar() throws Exception {
		logger.info(">>>>>Verifyin 6M and 1M bar in spending widget");
		Assert.assertEquals(spending.monthBar().getText(), PropsUtil.getDataPropertyValue("MonthBar_Text"));

	}

	@Test(description = "AT-73229:Verify month to date text", priority = 16, dependsOnMethods = { "investorLogin1" })
	public void verifyMonthToDate() throws Exception {
		logger.info(">>>>>Verifying month to date text ");
		Assert.assertEquals(spending.monthToDateText().getText(), PropsUtil.getDataPropertyValue("Month_To_Date_Text"));

	}

	@Test(description = "AT-73234,AT-73235:Verify networth Widget", priority = 17, dependsOnMethods = { "investorLogin1" })
	public void verifyNetworthCard() throws Exception {
		logger.info(">>>>> Verifying Networth Card..");
		Assert.assertTrue(networth.NetworthCard().isDisplayed(), ">>>>> Networth Widget Not Displayed in Dashboard..");
	}

}
