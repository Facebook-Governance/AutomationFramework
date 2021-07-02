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

import com.omni.pfm.pages.AccountAddition.AccountAddition;
import com.omni.pfm.pages.InvestmentHoldings.InvestmentHoldings_Loc;
import com.omni.pfm.pages.Login.LoginPage;
import com.omni.pfm.pages.Login.LoginPage_SAML3;
import com.omni.pfm.pages.ManageSharing.ManageSharing_Loc;
import com.omni.pfm.rest.ysl;
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
public class AccountSharing_InvestmentHoldings_Advisor_Test extends TestBase {
	private static final Logger logger = LoggerFactory.getLogger(AccountSharing_InvestmentHoldings_Advisor_Test.class);
	public static String advRefId, investorName, advisorName;
	AccountAddition accountAddition;
	LoginPage login;
	LoginPage_SAML3 SAML;
	ManageSharing_Loc sharing;
	InvestmentHoldings_Loc inv;

	@BeforeClass(alwaysRun = true)
	public void init() throws Exception {
		doInitialization("Accounts Sharing");
		TestBase.tc = TestBase.extent.startTest("Login", "Login");
		TestBase.test.appendChild(TestBase.tc);

		accountAddition = new AccountAddition();
		login = new LoginPage();
		SAML = new LoginPage_SAML3();
		sharing = new ManageSharing_Loc(d);
		inv = new InvestmentHoldings_Loc(d);

	}

	/**
	 * Create an Advisor and add aggregated account
	 * 
	 * @throws AWTException
	 * @throws InterruptedException
	 * @throws JSONException
	 */

	@Test(description = "Create an advisor for above investor Name..", priority = 1)
	public void createAdvisor1() throws AWTException, InterruptedException, JSONException {

		advRefId = inv.getAdvisorReferanceID();
		investorName = inv.getInvestorName();

		logger.info(">>>>> Creating an advisor with extra params segmentName=advisor&IID=Investorname.. ");
		advisorName = SAML.createAdvisor(d, investorName, advRefId, "10003600");
		SeleniumUtil.waitForPageToLoad();

		Assert.assertNotNull(advisorName, ".. Advisor Creation failed..");

	}

	@Test(description = "Create an advisor for above investor Name..", priority = 2, dependsOnMethods = {
			"createAdvisor1" })
	public void addAccountToAdvisor() throws AWTException, InterruptedException {
		accountAddition.linkAccountFastlink();
		SeleniumUtil.waitForPageToLoad(2000);

		accountAddition.addAggregatedAccount("ihauto.Investment1", "Investment1");
		SeleniumUtil.waitForPageToLoad();

	}

	/**
	 * Create an investor
	 * 
	 * @throws AWTException
	 * @throws InterruptedException
	 */
	@Test(description = "Create an investor for above advisor Name..", priority = 3, dependsOnMethods = {
			"createAdvisor1" })
	public void createInvestor1() throws AWTException, InterruptedException {
		boolean expectedInvestor = false;

		logger.info(">>>>> Creating an investor and adding pre-populated accounts..");
		SAML.createInvestorWithPrepop(d, advisorName, investorName);
		SeleniumUtil.waitForPageToLoad();

		expectedInvestor = true;

		Assert.assertTrue(expectedInvestor);

	}

	/**
	 * Share advisor account to investor using YSL API
	 * 
	 * @throws AWTException
	 * @throws InterruptedException
	 * @throws JSONException
	 */
	@Test(description = "ASIH_01_04:Create an advisor for above investor Name..", priority = 4, dependsOnMethods = {
			"createAdvisor1" })
	public void shareAdvisorAccount() throws AWTException, InterruptedException, JSONException {
		logger.info(">>>>> Sharing  advisor accounts to investor..");
		String samlResponse = SAML.loginResponse(d, advisorName, investorName);
		ysl.shareAdvisorAccount(samlResponse, advisorName, investorName);
	}

	@Test(description = "AT-73119,AT-73121:Verify Account Shared By Advisor..", priority = 5)
	public void investorLogin() throws Exception {
		SAML.login(d, investorName, null, "10003700");
		SeleniumUtil.waitForPageToLoad();

		Assert.assertTrue(inv.invData().isDisplayed(), ">>>>> Investor Acount is not displaying.");

	}
}
