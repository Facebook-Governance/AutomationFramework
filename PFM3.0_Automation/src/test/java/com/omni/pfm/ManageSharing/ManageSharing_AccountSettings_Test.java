/*******************************************************************************
 * Copyright (c) 2018 Yodlee Inc. All Rights Reserved.
 * 
 * This software is the confidential and proprietary information of
 * Yodlee, Inc. Use is subject to license terms.
 * 
 * @author
 ******************************************************************************/
package com.omni.pfm.ManageSharing;

import java.awt.AWTException;
import com.omni.pfm.AccountSharing.*;
import com.omni.pfm.Accounts.Accounts_LandingPage_Loc;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.*;

import com.omni.pfm.PageProcessor.PageParser;
import com.omni.pfm.pages.AccountAddition.AccountAddition;
import com.omni.pfm.pages.Dashboard.Account_Loc;
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
public class ManageSharing_AccountSettings_Test extends TestBase {

	private static final Logger logger = LoggerFactory.getLogger(ManageSharing_AccountSettings_Test.class);
	AccountAddition accountAddition;
	Accounts_LandingPage_Loc accounts;
	LoginPage login;
	LoginPage_SAML3 SAML;
	public static String advRefId = null, investorName = null, advisorName = null;
	String AccountName, AccountPassword;
	Account_Loc account;
	ManageSharing_Loc s;

	@BeforeClass(alwaysRun = true)
	public void init() throws Exception {
		doInitialization("Manage Sharing and Account Settings.");
		TestBase.tc = TestBase.extent.startTest("Login", "Login");
		TestBase.test.appendChild(TestBase.tc);

		SAML = new LoginPage_SAML3();
		s = new ManageSharing_Loc(d);
		accountAddition = new AccountAddition();
		account = new Account_Loc(d);
		accounts = new Accounts_LandingPage_Loc(d);
		login = new LoginPage();

	}

	/**
	 * Login as investor with pre-populated accounts
	 * 
	 * @throws Exception
	 */
	@Test(description = "Login as Investor", priority = 0)
	public void investorLogin_OnlyPreop() throws Exception {
		logger.info(">>>>> Logging in as an investor with only prepop account..");
		SAML.login(d, PropsUtil.getDataPropertyValue("only_prepop_manageSharing_inv"), null, "10003700");
		SeleniumUtil.waitForPageToLoad();

		PageParser.forceNavigate("ManageSharing", d);
		SeleniumUtil.waitForPageToLoad(4000);

		Assert.assertTrue(s.finappHeader().isDisplayed(), ">>>>> Failed to navigate to manage sharing..");
	}

	@Test(description = "AT-63203:Verify the advisor shared to the user..", priority = 1)
	public void verifyAdvisor() throws Exception {
		logger.info(">>>>> Verifying the advisors assigned to the user ");

		for (int i = 0; i < s.sidebar_lbl_Values().size(); i++) {
			Assert.assertTrue(s.sidebar_lbl_Values().get(i).isDisplayed(), ">>>>> No advisors listed for the user..");
		}
	}

	@Test(description = "Verify the permission is not needed for prepop account.", priority = 2)
	public void verifyNoPermissions() throws Exception {
		logger.info(">>>>>Verifying no permission dropdown for only prepopulated account.");
		boolean flag = false;

		for (int dropDown = 0; dropDown < s.sharingAccList().size(); dropDown++) {

			SeleniumUtil.click(s.dropdownHeader().get(dropDown));

		}
		Assert.assertFalse(flag);

	}

	/**
	 * Login as advisor to view pre pop accounts
	 * 
	 * @throws Exception
	 */
	@Test(description = "AT-63206:Login As advisor and verify the accounts shared.", priority = 3)
	public void advisorLogin_OnlyPreop() throws Exception {
		logger.info(">>>>> Logging in as the advisor for the above user");

		SAML.login(d, PropsUtil.getDataPropertyValue("only_prepop_manageSharing_adv"),
				PropsUtil.getDataPropertyValue("only_prepop_manageSharing_inv"), "10003700");
		SeleniumUtil.waitForPageToLoad(7000);

		Assert.assertTrue(s.finappHeader().isDisplayed());

	}

	/**
	 * Create advisor linked with investor
	 * 
	 * @throws Exception
	 */
	@Test(description = "Create an advisor with investor linked", priority = 4)
	public void createAdvisor() throws Exception {
		boolean expectedAdvisor = false;
		logger.info(">>>>> Getting advisor referance ID");
		advRefId = SAML.getRefrenceId();

		logger.info(">>>>> Getting investor Name");
		investorName = SAML.getInvestorUserName();

		logger.info(">>>>> Creating an advisor.");
		advisorName = SAML.createAdvisor(d, investorName, advRefId, PropsUtil.getDataPropertyValue("fastlink_App_ID"));

		logger.info(">>>>> Verifying advisor creation.");

		if (advisorName != null) {
			expectedAdvisor = true;
		}

		Assert.assertTrue(expectedAdvisor, "==> Advisor Creation failed..");

	}

	/**
	 * Create investor and share the account.
	 * 
	 * @throws Exception
	 */
	@Test(description = "Create an investor..", priority = 5, dependsOnMethods = { "createAdvisor" })
	public void createInvestor() throws Exception {
		boolean expectedInvestor = false;
		AccountName = PropsUtil.getDataPropertyValue("prepop_site_username");
		AccountPassword = PropsUtil.getDataPropertyValue("prepop_site_password");

		if (advisorName != null) {
			logger.info(">>>>> Creating an investor..");
			SAML.createInvestor(d, advisorName, investorName, AccountName, AccountPassword);
			expectedInvestor = true;
		}

		Assert.assertTrue(expectedInvestor, "==> Investor Creation Failed..");

	}

	@Test(description = "AT-63215:Add a dag bank to investor", priority = 6, dependsOnMethods = { "createInvestor" })
	public void addFirstAccountToInvestor() throws AWTException, InterruptedException {
		logger.info(">>>>> Navigate to Dashboard");
		SeleniumUtil.refresh(d);
		SeleniumUtil.waitForPageToLoad();

		logger.info(">>>>> Adding accounts to investor");
		accountAddition.linkAccount();
		SeleniumUtil.waitForPageToLoad(2000);
		accountAddition.addContainerAccount(PropsUtil.getDataPropertyValue("dag_account_type"),
				PropsUtil.getDataPropertyValue("dag_user_name_accounts"),
				PropsUtil.getDataPropertyValue("dag_password_accounts"));

		logger.info(">>>>> Navigating to accounts Page");
		PageParser.forceNavigate("ManageSharing", d);
		SeleniumUtil.waitForPageToLoad();

		Assert.assertEquals(accounts.finappHeader().getText().trim(),
				PropsUtil.getDataPropertyValue("ManageSharing_Finapp_Header"), "==> Finapp Header not matching.");

	}

	@Test(description = "AT-63212:Give Permission to advisor to login as user.", priority = 7)
	public void givePermissionToAll() throws Exception {
		logger.info(">>>>> Giving Permission to accounts..");
		for (int i = 1; i < s.dropdownHeader().size(); i++) {
			SeleniumUtil.click(s.dropdownHeader().get(i));
			SeleniumUtil.waitForPageToLoad(2000);

			SeleniumUtil.click(s.getDropdownValue(i + 1).get(0));
		}

		Assert.assertTrue(s.getSaveChanges().isDisplayed(), "Save Changes Button did not present..");
	}

	@Test(description = "AT-63215:Save Changes", priority = 8)
	public void verifySaveChanges() throws Exception {
		logger.info(">>>>> Verifying Save Changes..");

		SeleniumUtil.waitForPageToLoad(4000);
		SeleniumUtil.click(s.getSaveChanges());

		Assert.assertTrue(s.finappHeader().isDisplayed());
	}

	/**
	 * Login As advisor to check the shared account
	 * 
	 * @throws Exception
	 */
	@Test(description = "Login As Advisor for the obove user..", priority = 9)
	public void loginAsAdvisor_User1() throws Exception {
		logger.info(">>>>> Login as Advisor");

		SAML.login(d, advisorName, investorName, "10003700");
		SeleniumUtil.waitForPageToLoad();

		PageParser.forceNavigate("AccountsPage", d);
		SeleniumUtil.waitForPageToLoad();
	}

	@Test(description = "Verify Navigation to Transaction.", priority = 10)
	public void verifyNavigation() throws Exception {
		for (int i = 0; i < s.accountsDagBank().size(); i++) {
			SeleniumUtil.click(s.accountsDagBank().get(i));
			SeleniumUtil.waitForPageToLoad(4000);

			PageParser.forceNavigate("AccountsPage", d);
			SeleniumUtil.waitForPageToLoad(5000);
		}
	}

	@Test(description = "Verify Settings Popup", priority = 11)
	public void verifyPopupSettings() throws Exception {

		for (int i = 0; i < s.moreBtn_Accounts().size(); i++) {
			logger.info("Verify Account Settings Popup");
			SeleniumUtil.click(s.moreBtn_Accounts().get(i));
			SeleniumUtil.waitForPageToLoad();

			SeleniumUtil.click(s.getDropdownValue1(i + 1).get(0));

			SeleniumUtil.waitForPageToLoad(7000);

			SeleniumUtil.click(s.closeButton_P());
			SeleniumUtil.waitForPageToLoad(2000);

		}
	}

	@Test(description = "Login as Investor with Manual and other accounts.", priority = 12)
	public void loginAsInvestor_User2() throws Exception {
		logger.info(">>>>> Logging in as an investor with only prepop account..");
		SAML.login(d, investorName, null, "10003700");
		SeleniumUtil.waitForPageToLoad();

		PageParser.forceNavigate("ManageSharing", d);
		SeleniumUtil.waitForPageToLoad(4000);

		Assert.assertTrue(s.finappHeader().isDisplayed(), ">>>>> Failed to navigate to manage sharing..");

	}

	@Test(description = "AT-63216:Give Permission to advisor to login as user.", priority = 13)
	public void givePermissionToAll_BalanceOnly() throws Exception {
		logger.info(">>>>> Giving Permission to accounts..");
		for (int i = 0; i < 2; i++) {
			SeleniumUtil.click(s.dropdownHeader().get(i));
			SeleniumUtil.waitForPageToLoad(2000);

			SeleniumUtil.click(s.getDropdownValue(i + 1).get(1));
		}

	}

	@Test(description = "AT-63216:Save Changes", priority = 14)
	public void verifySaveChanges_BalanceOnly() throws Exception {
		logger.info(">>>>> Verifying Save Changes..");

		SeleniumUtil.waitForPageToLoad(4000);
		SeleniumUtil.click(s.getSaveChanges());

		Assert.assertTrue(s.finappHeader().isDisplayed());
	}

	@Test(description = "Login As Advisor for the obove user..", priority = 15)
	public void loginAsAdvisor_User2() throws Exception {
		logger.info(">>>>> Login as Advisor");

		SAML.login(d, advisorName, investorName, "10003700");
		SeleniumUtil.waitForPageToLoad();

		PageParser.forceNavigate("AccountsPage", d);
		SeleniumUtil.waitForPageToLoad(10000);
	}

	@Test(description = "Login as Investor with Manual and other accounts.", priority = 16)
	public void loginAsInvestor_User3() throws Exception {
		logger.info(">>>>> Logging in as an investor with only prepop account..");
		SAML.login(d, investorName, null, "10003700");
		SeleniumUtil.waitForPageToLoad();

		PageParser.forceNavigate("ManageSharing", d);
		SeleniumUtil.waitForPageToLoad(4000);

		Assert.assertTrue(s.finappHeader().isDisplayed(), ">>>>> Failed to navigate to manage sharing..");

	}

	@Test(description = "Give Permission to advisor to login as user.", priority = 17)
	public void givePermissionToAll_None() throws Exception {
		logger.info(">>>>> Giving Permission to accounts..");
		for (int i = 1; i < s.dropdownHeader().size(); i++) {
			SeleniumUtil.click(s.dropdownHeader().get(i));
			SeleniumUtil.waitForPageToLoad(2000);

			SeleniumUtil.click(s.getDropdownValue(i + 1).get(2));
		}

	}

	@Test(description = "Save Changes", priority = 18)
	public void verifySaveChanges_None() throws Exception {
		logger.info(">>>>> Verifying Save Changes..");

		SeleniumUtil.waitForPageToLoad(4000);
		SeleniumUtil.click(s.getSaveChanges());

		Assert.assertTrue(s.finappHeader().isDisplayed());
	}

	@Test(description = "Login As Advisor for the obove user..", priority = 19)
	public void loginAsAdvisor_NoAdvisor() throws Exception {
		logger.info(">>>>> Login as Advisor");

		SAML.login(d, investorName, advisorName, "10003700");
		SeleniumUtil.waitForPageToLoad();

		PageParser.forceNavigate("AccountsPage", d);
		SeleniumUtil.waitForPageToLoad(6000);

	}

	@Test(description = "Login as Investor with Manual and other accounts.", priority = 20)
	public void loginAsInvestor_NoAdvisor() throws Exception {
		logger.info(">>>>> Logging in as an investor with only prepop account..");
		SAML.login(d, "ydl111", null, "10003700");
		SeleniumUtil.waitForPageToLoad();

		PageParser.forceNavigate("ManageSharing", d);
		SeleniumUtil.waitForPageToLoad(4000);

		Assert.assertTrue(s.finappHeader().isDisplayed(), ">>>>> Failed to navigate to manage sharing..");

	}

	@Test(description = "Login as Investor with Manual and other accounts.", priority = 21)
	public void loginAsInvestor_AllSharingAvailable() throws Exception {
		logger.info(">>>>> Logging in as an investor with only prepop account..");
		SAML.login(d, "ihinv2", null, "10003700");
		SeleniumUtil.waitForPageToLoad();

		PageParser.forceNavigate("ManageSharing", d);
		SeleniumUtil.waitForPageToLoad(4000);

		Assert.assertTrue(s.finappHeader().isDisplayed(), ">>>>> Failed to navigate to manage sharing..");

	}

}
