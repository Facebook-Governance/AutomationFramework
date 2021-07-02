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

import com.omni.pfm.PageProcessor.PageParser;
import com.omni.pfm.pages.AccountAddition.AccountAddition;
import com.omni.pfm.pages.Dashboard.AccountSharing_Loc;
import com.omni.pfm.pages.Dashboard.Account_Loc;
import com.omni.pfm.pages.Dashboard.DashBoradInvestmentLoc;
import com.omni.pfm.pages.Dashboard.NetworthLoc;
import com.omni.pfm.pages.Dashboard.Spending_Loc;
import com.omni.pfm.pages.Login.LoginPage;
import com.omni.pfm.pages.Login.LoginPage_SAML3;
import com.omni.pfm.pages.ManageSharing.ManageSharing_Loc;
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

public class AccountSharing_Dashboard_Changes_Test extends TestBase {
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
	 * Create an advisor Link the investor to him Add an aggregated account to
	 * investor Share the account to him and verify advisor account reflecting with
	 * investor data
	 * 
	 * @throws AWTException
	 * @throws InterruptedException
	 * @throws JSONException
	 */
	@Test(description = "ASDSH_02_01:Login As Advisor..", priority = 1)
	public void createAdvisor1() throws AWTException, InterruptedException, JSONException {
		advRefId = share.getAdvisorReferanceID();
		investorName = share.getInvestorName();

		logger.info(">>>>> Creating an advisor with extra params segmentName=advisor&IID=Investorname.. ");
		advisorName = SAML.createAdvisor(d, investorName, advRefId, "10003700");
		SeleniumUtil.waitForPageToLoad();

		Assert.assertNotNull(advisorName, ".. Advisor creation failed.");
	}

	/**
	 * Create an investor and shre his account to the advisor
	 * 
	 * @throws Exception
	 */

	@Test(description = "Create an investor to share accounts to advisor", priority = 2)
	public void createInvestor1() throws Exception {

		logger.info(">>>>> Creating an investor and adding pre-populated accounts..");
		SAML.createInvestorWithPrepop(d, advisorName, investorName);
		SeleniumUtil.waitForPageToLoad();

	}

	@Test(description = "AT-82720:Add an account to investor", priority = 3)
	public void addAccountToInvestor() throws AWTException, InterruptedException {
		logger.info(">>>>> Adding an account to the investor");
		accountAddition.linkAccount();
		SeleniumUtil.waitForPageToLoad();
		accountAddition.addAggregatedAccount("twoxml.bank1", "bank1");
		SeleniumUtil.waitForPageToLoad();

		logger.info(">>>>> Adding Investment Account..");
		accountAddition.linkAccount();
		SeleniumUtil.waitForPageToLoad();
		accountAddition.addAggregatedAccount("basucontainer2.Investment1", "Investment1");
		SeleniumUtil.waitForPageToLoad();
	}

	@Test(description = "Give permission to the advisor..", priority = 4, dependsOnMethods = {
			"addAccountToInvestor" })
	public void givePermission() throws Exception {
		PageParser.forceNavigate("ManageSharing", d);
		SeleniumUtil.waitForPageToLoad(7000);

		logger.info(">>>>> Giving access permissions to the advisor..");
		for (int i = 1; i < 3; i++) {
			SeleniumUtil.click(sharing.dropdownHeader().get(i));
			SeleniumUtil.waitForPageToLoad(2000);

			SeleniumUtil.click(sharing.getDropdownValue(i + 1).get(0));
			SeleniumUtil.waitForPageToLoad(2000);
		}
		Assert.assertTrue(sharing.getSaveChanges().isDisplayed(), ">>>>> Save Changes button not enabled..");

	}

	@Test(description = "AT-73202,AT-73203:Verify the advisor login..", priority = 5)
	public void loginAsAdvisor1() throws Exception {
		SeleniumUtil.click(sharing.getSaveChanges());
		SeleniumUtil.waitForPageToLoad();

		logger.info(">>>> Logging in as advisor to view the changes..");
		SAML.login(d, advisorName, investorName, "10003700");
		SeleniumUtil.waitForPageToLoad();

		Assert.assertTrue(share.finappHeader().isDisplayed(), ">>>> Login failed..");

	}

	@Test(description = "AT-73203:Verify Accounts Widget", priority = 6)
	public void verifyAccWidget() throws Exception {
		logger.info(">>>>> Verifying account widget");
		SeleniumUtil.refresh(d);
		SeleniumUtil.waitForPageToLoad(5000);
		Assert.assertEquals(account.accountsHeader().getText(), PropsUtil.getDataPropertyValue("Accounts_Header1"),
				"Account Widget not present..");

	}

	@Test(description = "AT-73229:Verify Spending Widget..", priority = 7)
	public void verifySpendingWidgetHeader() throws Exception {
		logger.info(">>>>>Verifying spending header in spending widget");
		Assert.assertEquals(spending.spendingHeader().getText(), PropsUtil.getDataPropertyValue("Spending_Header"));

	}

	@Test(description = "AT-73230:Verify Spending Month Bar", priority = 8)
	public void verifySpendingMonthBar() throws Exception {
		logger.info(">>>>>Verifyin 6M and 1M bar in spending widget");
		Assert.assertEquals(spending.monthBar().getText(), PropsUtil.getDataPropertyValue("MonthBar_Text"));

	}

	@Test(description = "AT-73229:Verify month to date text", priority = 9)
	public void verifyMonthToDate() throws Exception {
		logger.info(">>>>>Verifying month to date text ");
		Assert.assertEquals(spending.monthToDateText().getText(), PropsUtil.getDataPropertyValue("Month_To_Date_Text"));

	}

	@Test(description = "AT-73288:Verify networth Widget", priority = 10)
	public void verifyNetworthCard() throws Exception {
		logger.info(">>>>> Verifying Networth Card..");
		Assert.assertTrue(networth.NetworthCard().isDisplayed(), ">>>>> Networth Widget Not Displayed in Dashboard..");
	}

	@Test(description = "AT-73205:Verify Account Widget", priority = 11)
	public void verifyAccountWidget() throws Exception {
		logger.info(">>>>> Verifying Account Widget..");
		SeleniumUtil.waitForPageToLoad();
		Assert.assertEquals(account.accountsHeader().getText(), PropsUtil.getDataPropertyValue("Accounts_Header1"),
				"Account Widget not present..");
	}

	@Test(description = "AT-73205,AT-73209:Verify after clicking on view all account button it should navigate to accounts finapp.", priority = 12, groups = {
			"Desktop" }, enabled = true)
	public void verifyViewAllAccounts() throws Exception {

		logger.info(">>>>> Verify view all accounts button is displaying or not.");
		boolean allAccountButton = account.viewAllAccountButton().isDisplayed();

		SeleniumUtil.click(account.viewAllAccountButton());
		SeleniumUtil.waitForPageToLoad();

		Assert.assertTrue(allAccountButton, ">>>>> View All Accout button is not displayed..");

	}

	@Test(description = "AT-73209:Verify Account Navigation", priority = 13)
	public void verifyAccountsPage() throws Exception {
		logger.info(">>>>> Verify after clicking on view all account button it should navigate to accounts finapp.");

		boolean accountHeader = account.accountsheader().isDisplayed();

		SeleniumUtil.refresh(d);
		SeleniumUtil.waitForPageToLoad();
		Assert.assertTrue(accountHeader, ">>>>> Accounts Header is not displayed..");
	}

	@Test(description = "AT-73207:Verify Accounts Click will redirect to Transaction Page", priority = 14)
	public void verifyNavigationToTransaction() throws Exception {
		List<WebElement> l = share.accountName();
		for (int i = 0; i < (l.size() - 1); i++) {
			SeleniumUtil.click(l.get(i));
			SeleniumUtil.waitForPageToLoad();
			Assert.assertEquals(share.finappHeader().getText(), "Transactions");

			SeleniumUtil.refresh(d);
			SeleniumUtil.waitForPageToLoad();
		}
	}

	@Test(description = "Login As Investor..", priority = 15)
	public void loginAsInvestor2() throws Exception {
		logger.info(">>>>> Logging in as Investor..");
		SAML.login(d, investorName, null, "10003700");
		SeleniumUtil.waitForPageToLoad();

		PageParser.forceNavigate("ManageSharing", d);
		SeleniumUtil.waitForPageToLoad();

		Assert.assertTrue(sharing.finappHeader().isDisplayed(), "Failed to Navigate to finapp");

	}

	@Test(description = "Give permission to the advisor..", priority = 16)
	public void givePermission_BalanceOnly() throws Exception {

		logger.info(">>>>> Giving access permissions to the advisor..");
		for (int i = 1; i < 3; i++) {
			SeleniumUtil.click(sharing.dropdownHeader().get(i));
			SeleniumUtil.waitForPageToLoad(2000);

			SeleniumUtil.click(sharing.getDropdownValue(i + 1).get(1));
			SeleniumUtil.waitForPageToLoad(2000);
		}
		Assert.assertTrue(sharing.getSaveChanges().isDisplayed(), ">>>>> Save Changes button not enabled..");

	}

	@Test(description = "Login As Investor..", priority = 17)
	public void loginAsAdvisor2() throws Exception {
		SeleniumUtil.click(sharing.getSaveChanges());
		SeleniumUtil.waitForPageToLoad();

		logger.info(">>>>> Logging in as Investor..");
		SAML.login(d, advisorName, investorName, "10003700");
		SeleniumUtil.waitForPageToLoad();

	}

	@Test(description = "AT-73207:Verify Accounts Click will redirect to Transaction Page", priority = 18)
	public void verifyNavigationToAccounts() throws Exception {
		List<WebElement> l = share.accountName();
		for (int i = 0; i < l.size() - 1; i++) {
			SeleniumUtil.click(l.get(i));
			SeleniumUtil.waitForPageToLoad();
			Assert.assertEquals(share.finappHeader().getText(), "Accounts");

			SeleniumUtil.refresh(d);
			SeleniumUtil.waitForPageToLoad();
		}
	}

}
