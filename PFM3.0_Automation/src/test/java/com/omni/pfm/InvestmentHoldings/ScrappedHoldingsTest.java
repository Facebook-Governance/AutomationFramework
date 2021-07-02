/*******************************************************************************
 * Copyright (c) 2020 Yodlee Inc. All Rights Reserved.
 * 
 * This software is the confidential and proprietary information of
 * Yodlee, Inc. Use is subject to license terms.
 * 
 * @author kongara.sravan
 ******************************************************************************/
package com.omni.pfm.InvestmentHoldings;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.omni.pfm.Accounts.Accounts_Details_Loc;
import com.omni.pfm.PageProcessor.PageParser;
import com.omni.pfm.pages.AccountAddition.AccountAddition;
import com.omni.pfm.pages.InvestmentHoldings.InvestmentHoldings_Loc;
import com.omni.pfm.pages.Login.LoginPage;
import com.omni.pfm.pages.Networth.NetWorth_Loc;
import com.omni.pfm.testBase.TestBase;
import com.omni.pfm.utility.SeleniumUtil;

public class ScrappedHoldingsTest extends TestBase {
	private static final Logger logger = LoggerFactory.getLogger(ScrappedHoldingsTest.class);
	AccountAddition accountAdditionPage;
	LoginPage login;
	Accounts_Details_Loc accDetailsPage;
	InvestmentHoldings_Loc investmentHoldingsPage;
	NetWorth_Loc networthPage;
	String accountsWindowHandle, networthWindowHandle, investmentHoldingsWindowhandle;

	@BeforeClass(alwaysRun = true)
	public void init() throws Exception {
		try {
			doInitialization("Holdings Test");
			TestBase.tc = TestBase.extent.startTest("Holdings Login", "Login for testing holdings");
			TestBase.test.appendChild(TestBase.tc);
			accountAdditionPage = new AccountAddition();
			investmentHoldingsPage = new InvestmentHoldings_Loc(d);
			accDetailsPage = new Accounts_Details_Loc(d);
			networthPage = new NetWorth_Loc(d);
			login = new LoginPage();
		} catch (Exception e) {
			logger.error("Unable to initialize holdings test due to :: " + e.getMessage());
			Assert.fail("Unable to initialize holdings test due to :: " + e.getMessage());
		}
	}

	@Test(priority = 0, description = "Create new User and login to the Portal")
	public void loginForScrappedHoldings() {
		try {
			login.loginMain(d, loginParameter);
			accountAdditionPage.linkAccount();
			Assert.assertTrue(accountAdditionPage.addAggregatedAccount("srvncntr.Investment3", "Investment3"),
					"Account addition is not working for account srvncntr.Investment3");
			PageParser.forceNavigate("AccountsPage", d);
			accountsWindowHandle = d.getWindowHandle();
			networthWindowHandle = SeleniumUtil.duplicateTabAndReturnWindowhandle();
			networthPage.forceNavigateToNetWorth();
			networthPage.expandInvestments();
			investmentHoldingsWindowhandle = SeleniumUtil.duplicateTabAndReturnWindowhandle();
			PageParser.forceNavigate("InvestmentHoldings", d);
			investmentHoldingsPage.completeFtueFlow();
		} catch (Exception e) {
			logger.error("Unable to login for holdings test due to :: " + e.getMessage());
			Assert.fail("Unable to login for holdings test due to :: " + e.getMessage());
		}
	}

	@Test(priority = 1, description = "AT-129384,AT-129386,AT-129388,AT-129388,AT-129389,AT-129390,AT-129394 : verify holding values", dataProvider = "differentHoldingsScenarios", dependsOnMethods = "loginForScrappedHoldings")
	public void verifyScrappedHoldingsForAccountWithDifferentScenariosInAccounts_Networth_IH_Finapps(
			String accountName) {
		try {
			d.switchTo().window(accountsWindowHandle);
			logger.info("Switched to accounts tab");
			float accountBalanceInAccountPage = Float
					.parseFloat(accDetailsPage.returnTheAmountDisplayedForGivenAccount(accountName).replace("$", "")
							.replace("*", "").replace(",", "").trim());
			logger.info("Balance of account name :: " + accountName + " in accounts finapp is :: "
					+ accountBalanceInAccountPage);
			d.switchTo().window(networthWindowHandle);
			logger.info("Switched to networth tab");
			float accountBalanceInNetworthPage = Float
					.parseFloat(networthPage.returnInvestmentBalanceForGivenAccountName(accountName).replace("$", "")
							.replace("*", "").replace(",", "").trim());
			logger.info("Balance of account name :: " + accountName + " in networth finapp is :: "
					+ accountBalanceInNetworthPage);
			d.switchTo().window(investmentHoldingsWindowhandle);
			logger.info("Switched to investment holdings tab");
			investmentHoldingsPage.selectGivenAccountUnderInvestmentHoldingsAccountDropdown(accountName);
			float accountBalanceInInvestmentholdingsPage = (float) investmentHoldingsPage
					.returnTotalInvestmentHoldingsAccountBalance();
			logger.info("Balance of account name :: " + accountName + " in investment holdings finapp is :: "
					+ accountBalanceInInvestmentholdingsPage);
			if (!(accountBalanceInAccountPage == accountBalanceInInvestmentholdingsPage
					&& accountBalanceInAccountPage == accountBalanceInNetworthPage)) {
				Assert.fail("Account balances are not matching for scenario :: " + accountName
						+ ". Balances in different finapps: Accounts ::" + accountBalanceInAccountPage
						+ ", Investment Holdings :: " + accountBalanceInInvestmentholdingsPage + " and Networth :: "
						+ accountBalanceInNetworthPage);
			}

			if (accountBalanceInAccountPage == 0) {
				Assert.fail("Account balance is displaying Zero in the Accounts finapp which is not as expected");
			}
		} catch (Exception e) {
			logger.error(
					"unable to verify holdings for scenario :: " + accountName + " due to " + e.getMessage());
			Assert.fail(
					"unable to verify holdings for scenario :: " + accountName + " due to " + e.getMessage());
		} finally {
		}
	}

	@Test(priority = 2, description = "Create new User and login to the Portal", enabled=false)
	public void loginForESOP() {
		try {
			SeleniumUtil.closeAllTheWindowsExceptTheGivenWindow(accountsWindowHandle);
			login.loginMain(d, loginParameter);
			accountAdditionPage.linkAccount();
			Assert.assertTrue(accountAdditionPage.addAggregatedAccount("srvncntr.Investment4", "Investment4"),
					"Account addition is not working for account srvncntr.Investment3");
			PageParser.forceNavigate("AccountsPage", d);
			accountsWindowHandle = d.getWindowHandle();
			networthWindowHandle = SeleniumUtil.duplicateTabAndReturnWindowhandle();
			networthPage.forceNavigateToNetWorth();
			networthPage.expandInvestments();
			investmentHoldingsWindowhandle = SeleniumUtil.duplicateTabAndReturnWindowhandle();
			PageParser.forceNavigate("InvestmentHoldings", d);
			investmentHoldingsPage.completeFtueFlow();
		} catch (Exception e) {
			logger.error("Unable to login for holdings test due to :: " + e.getMessage());
			Assert.fail("Unable to login for holdings test due to :: " + e.getMessage());
		}
	}

	@Test(priority = 3, description = "AT-130060,AT-130061,AT-130062,AT-130063,AT-130064,AT-130065,AT-130066,AT-130067,AT-130068,AT-130069,AT-130070,AT-130071,AT-130072,AT-130073,AT-130074 : verify ESOP holding accounts balance", dataProvider = "esopScenarios", dependsOnMethods = "loginForESOP",enabled=false)
	public void verifyVestedAndUnvestedACcountbalancesWithDifferentScenariosInAccounts_Networth_IH_Finapps(
			String accountName) {
		try {
			d.switchTo().window(accountsWindowHandle);
			logger.info("Switched to accounts tab");
			float accountBalanceInAccountPage = Float
					.parseFloat(accDetailsPage.returnTheAmountDisplayedForGivenAccount(accountName).replace("$", "")
							.replace("*", "").replace(",", "").trim());
			logger.info("Balance of account name :: " + accountName + " in accounts finapp is :: "
					+ accountBalanceInAccountPage);
			d.switchTo().window(networthWindowHandle);
			logger.info("Switched to networth tab");
			float accountBalanceInNetworthPage = Float
					.parseFloat(networthPage.returnInvestmentBalanceForGivenAccountName(accountName).replace("$", "")
							.replace("*", "").replace(",", "").trim());
			logger.info("Balance of account name :: " + accountName + " in networth finapp is :: "
					+ accountBalanceInNetworthPage);
			d.switchTo().window(investmentHoldingsWindowhandle);
			logger.info("Switched to investment holdings tab");
			investmentHoldingsPage.selectGivenAccountUnderInvestmentHoldingsAccountDropdown(accountName);
			float accountBalanceInInvestmentholdingsPage = (float) investmentHoldingsPage
					.returnTotalInvestmentHoldingsAccountBalance();
			logger.info("Balance of account name :: " + accountName + " in investment holdings finapp is :: "
					+ accountBalanceInInvestmentholdingsPage);
			if (!(accountBalanceInAccountPage == accountBalanceInInvestmentholdingsPage
					&& accountBalanceInAccountPage == accountBalanceInNetworthPage)) {
				Assert.fail("Account balances are not matching for scenario :: " + accountName
						+ ". Balances in different finapps: Accounts ::" + accountBalanceInAccountPage
						+ ", Investment Holdings :: " + accountBalanceInInvestmentholdingsPage + " and Networth :: "
						+ accountBalanceInNetworthPage);
			}

		} catch (Exception e) {
			logger.error(
					"unable to verify holdings for scenario :: " + accountName + " due to " + e.getMessage());
			Assert.fail(
					"unable to verify holdings for scenario :: " + accountName + " due to " + e.getMessage());
		} finally {
		}
	}

	/* Commented as there are some corner case scenarios
	 * @DataProvider public String[][] differentHoldingsScenarios() { return new
	 * String[][] { { "IH With All NULL" }, { "IH With Value as Zero" }, {
	 * "IH With NULL Price Scenarios" }, { "IH With All Zeros" }, {
	 * "IH With Qty As ZeroScenarios" }, { "IH With Qty As Null" }, {
	 * "IH With Value as Null" }, { "IH With Zero price Scenarios" }, {
	 * "Holdings With Positive Scenarios" } }; }
	 */
	
	@DataProvider
	public String[][] differentHoldingsScenarios() {
		return new String[][] {{ "IH With Value as Zero" }, { "IH With NULL Price Scenarios" },
				{ "IH With Value as Null" }, { "IH With Zero price Scenarios" },
				{ "Holdings With Positive Scenarios" } };
	}

	@DataProvider
	public String[][] esopScenarios() {
		return new String[][] { { "Account 11- Only UnVested Null" }, { "Account 10- Only Vested Null" },
				{ "Account 9- Only UnVested Zero" }, { "Account 6- Only Vested Zero" },
				{ "Account 5- Vested and UnVested- NULL Value" }, { "Account 4- Vested and UnVested- Zero" },
				{ "Account 2- Vested and UnVested- Positive" }, { "Account 1- Vested and UnVested- Positive" },
				{ "Account 3- Vested and UnVested- Negative" } };
	}

	@AfterClass
	public void teardown() {
		try {
			SeleniumUtil.closeAllTheWindowsExceptTheGivenWindow(accountsWindowHandle);
		} catch (AssertionError | Exception e) {
			// TODO: handle exception
		}
	}
}
