/*******************************************************************************
 * Copyright (c) 2018 Yodlee Inc. All Rights Reserved.
 * 
 * This software is the confidential and proprietary information of
 * Yodlee, Inc. Use is subject to license terms.
 * 
 * @author
 ******************************************************************************/
package com.omni.pfm.InvestmentHoldings;

import java.awt.AWTException;

import org.json.JSONException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.*;

import com.omni.pfm.PageProcessor.PageParser;
import com.omni.pfm.pages.AccountAddition.AccountAddition;
import com.omni.pfm.pages.InvestmentHoldings.InvestmentHoldings_Loc;
import com.omni.pfm.pages.Login.LoginPage;
import com.omni.pfm.pages.Login.LoginPage_SAML3;
import com.omni.pfm.pages.ManageSharing.ManageSharing_Loc;
import com.omni.pfm.testBase.TestBase;
import com.omni.pfm.utility.SeleniumUtil;

/**
 * Copyright (c) 2018 Yodlee Inc. All Rights Reserved.
 *
 * This software is the confidential and proprietary information of Yodlee, Inc.
 * Use is subject to license terms.
 *
 * @author Shivaprasad
 */
public class AccountSharing_InvestmentHoldings_Test extends TestBase {
	private static final Logger logger = LoggerFactory.getLogger(AccountSharing_InvestmentHoldings_Test.class);
	public static String advRefId, investorName, advisorName;
	AccountAddition accountAddition;
	LoginPage login;
	LoginPage_SAML3 SAML;
	ManageSharing_Loc sharing;
	InvestmentHoldings_Loc inv;

	@BeforeClass(alwaysRun = true)
	public void init() throws Exception {
		doInitialization("Investment Holdings - Account Sharing");
		TestBase.tc = TestBase.extent.startTest("Login", "Login");
		TestBase.test.appendChild(TestBase.tc);

		login = new LoginPage();
		SAML = new LoginPage_SAML3();
		accountAddition = new AccountAddition();
		inv = new InvestmentHoldings_Loc(d);
		sharing = new ManageSharing_Loc(d);
	}

	/**
	 * Create advisor
	 * 
	 * @throws AWTException
	 * @throws InterruptedException
	 * @throws JSONException
	 */

	@Test(description = "Create an advisor for above investor Name..", priority = 1)
	public void createAdvisor() throws AWTException, InterruptedException, JSONException {

		advRefId = inv.getAdvisorReferanceID();
		investorName = inv.getInvestorName();

		logger.info(">>>>> Creating an advisor with extra params segmentName=advisor&IID=Investorname.. ");
		advisorName = SAML.createAdvisor(d, investorName, advRefId, "10003600");
		SeleniumUtil.waitForPageToLoad();

		Assert.assertNotNull(advisorName, " ... Advisor Creation failed..");

	}

	/**
	 * Create investor and add aggregaated acounts
	 * 
	 * @throws AWTException
	 * @throws InterruptedException
	 */

	@Test(description = "Create an investor for above advisor Name..", priority = 2, dependsOnMethods = {
			"createAdvisor" })
	public void createInvestor() throws AWTException, InterruptedException {
		boolean expectedInvestor = false;

		logger.info(">>>>> Creating an investor and adding pre-populated accounts..");
		SAML.createInvestorWithPrepop(d, advisorName, investorName);
		SeleniumUtil.waitForPageToLoad();

		expectedInvestor = true;

		Assert.assertTrue(expectedInvestor);

	}

	@Test(description = "Add Account To the investor", priority = 3,dependsOnMethods= {"createInvestor"})
	public void addInvestroAccounts() throws AWTException, InterruptedException {
		logger.info(">>>>> Adding an account to the investor");
		accountAddition.linkAccount();
		SeleniumUtil.waitForPageToLoad();
		accountAddition.addAggregatedAccount("bhat.Investment2", "Investment2");

	}

	/**
	 * Give permission to user aggregated account Giving Balance only
	 * 
	 * @throws Exception
	 */
	@Test(description = "Give permission to the advisor..", priority = 4)
	public void givePermission() throws Exception {
		PageParser.forceNavigate("ManageSharing", d);
		SeleniumUtil.waitForPageToLoad(7000);

		logger.info(">>>>> Giving access permissions to the advisor..");
		for (int i = 1; i < 2; i++) {
			SeleniumUtil.click(sharing.dropdownHeader().get(i));
			SeleniumUtil.waitForPageToLoad(2000);

			SeleniumUtil.click(sharing.getDropdownValue(i + 1).get(1));
			SeleniumUtil.waitForPageToLoad(2000);
		}
		Assert.assertTrue(sharing.getSaveChanges().isDisplayed(), ">>>>> Save Changes button not enabled..");

	}

	/**
	 * Login as advisor to view the investor shared account
	 * 
	 * @throws Exception
	 */

	@Test(description = "Verify the advisor login..", priority = 6)
	public void loginAsAdvisor1() throws Exception {
		SeleniumUtil.click(sharing.getSaveChanges());
		SeleniumUtil.waitForPageToLoad();

		logger.info(">>>> Logging in as advisor to view the changes..");
		SAML.login(d, advisorName, investorName, "10003700");
		SeleniumUtil.waitForPageToLoad();

		Assert.assertTrue(inv.finappHeader().isDisplayed(), ">>>> Login failed..");

	}

	@Test(description = "AT-73115,AT-73116,AT-73145:Verify No Data Screen When no investment Account is Added..", priority = 7)
	public void verifyNoDataScreen() throws Exception {

		logger.info(">>>>> Navigating to Investment Holdings..");
		PageParser.forceNavigate("InvestmentHoldings", d);
		SeleniumUtil.waitForPageToLoad();

		Assert.assertTrue(inv.noDataMsg_IH().isDisplayed(), ">>>>> No Data Message is not displayed..");
	}

	@Test(description = "AT-82723:Add an investment account to the investor..", priority = 8)
	public void addInvestmentAccount() throws AWTException, InterruptedException {
		logger.info(">>>>> Logging in as Invstor..");
		SAML.login(d, investorName, null, "10003700");
		SeleniumUtil.waitForPageToLoad();

		accountAddition.linkAccount();
		SeleniumUtil.waitForPageToLoad(2000);
		//accountAddition.addContainerAccount("dagInvestment", "basucontainer2.Investment1 ", "Investment1");
		accountAddition.addAggregatedAccount("basucontainer2.Investment1", "Investment1");
		SeleniumUtil.waitForPageToLoad();

		PageParser.forceNavigate("AccountsPage", d);
		SeleniumUtil.waitForPageToLoad();

		Assert.assertEquals(inv.finappHeader().getText().trim(), "Accounts",
				">>>>> Cannot navigate to Accounts Page..");

	}

	/**
	 * Change the permission to Full view
	 * 
	 * @throws Exception
	 */
	@Test(description = "Give permission to the advisor..", priority = 9)
	public void givePermissionToInvestmentAccount() throws Exception {
		PageParser.forceNavigate("ManageSharing", d);
		SeleniumUtil.waitForPageToLoad(7000);

		logger.info(">>>>> Giving access permissions to the advisor..");

		SeleniumUtil.click(sharing.dropdownHeader().get(1));
		SeleniumUtil.waitForPageToLoad(2000);

		SeleniumUtil.click(sharing.getDropdownValue(2).get(0));
		SeleniumUtil.waitForPageToLoad(2000);

		Assert.assertTrue(sharing.getSaveChanges().isDisplayed(), ">>>>> Save Changes button not enabled..");

	}

	@Test(description = "Add an investment account to the investor..", priority = 10)
	public void loginAsAdvisor() throws AWTException, InterruptedException {
		logger.info(">>>>> Saving Changes..");
		SeleniumUtil.click(sharing.getSaveChanges(), ">>>>> Couldn't click Save Changes..");
		SeleniumUtil.waitForPageToLoad(2000);
		logger.info(">>>>> Logging in as Invstor..");
		SAML.login(d, advisorName, investorName, "10003700");
		SeleniumUtil.waitForPageToLoad();

		PageParser.forceNavigate("InvestmentHoldings", d);
		SeleniumUtil.waitForPageToLoad();

		Assert.assertEquals(inv.finappHeader().getText().trim(), "Investment Holdings");

	}

	@Test(description = "AT-73117,AT-73144:Verify The Data Points Displayed in advisor view", priority = 11)
	public void verifyDataPoint() throws Exception {

		SeleniumUtil.click(inv.ftueClose_IH());

		Assert.assertTrue(inv.dataSection_IH().isDisplayed(), ">>>>> Data Section Not Displayed..");

	}

	@Test(description = "AT-73135:Add an investment account to the investor..", priority = 12)
	public void loginAsInvestor() throws AWTException, InterruptedException {
		logger.info(">>>>> Logging in as Invstor..");
		SAML.login(d, investorName, null, "10003700");
		SeleniumUtil.waitForPageToLoad();

		PageParser.forceNavigate("AccountsPage", d);
		SeleniumUtil.waitForPageToLoad();

		Assert.assertEquals(inv.finappHeader().getText().trim(), "Accounts",
				">>>>> Cannot navigate to Accounts Page..");

	}

	@Test(description = "Give permission to the advisor..", priority = 13)
	public void givePermissionToInvestmentAccount_BalanceOnly() throws Exception {
		PageParser.forceNavigate("ManageSharing", d);
		SeleniumUtil.waitForPageToLoad(7000);

		logger.info(">>>>> Giving access permissions to the advisor..");

		SeleniumUtil.click(sharing.dropdownHeader().get(1));
		SeleniumUtil.waitForPageToLoad(2000);

		SeleniumUtil.click(sharing.getDropdownValue(2).get(1));
		SeleniumUtil.waitForPageToLoad(2000);

		Assert.assertTrue(sharing.getSaveChanges().isDisplayed(), ">>>>> Save Changes button not enabled..");

	}

	@Test(description = "Add an investment account to the investor..", priority = 14)
	public void loginAsAdvisor2() throws AWTException, InterruptedException {
		logger.info(">>>>> Saving Changes..");
		SeleniumUtil.click(sharing.getSaveChanges(), ">>>>> Couldn't click Save Changes..");
		SeleniumUtil.waitForPageToLoad(2000);
		logger.info(">>>>> Logging in as Invstor..");
		SAML.login(d, advisorName, investorName, "10003700");
		SeleniumUtil.waitForPageToLoad();

		PageParser.forceNavigate("InvestmentHoldings", d);
		SeleniumUtil.waitForPageToLoad();

		Assert.assertTrue(inv.noAcess_IH().isDisplayed(), ">>>>> No Access Granted message Not Displayed.");

	}

}
