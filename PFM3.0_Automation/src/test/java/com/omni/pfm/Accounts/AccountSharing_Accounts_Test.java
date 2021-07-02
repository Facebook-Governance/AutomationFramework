/*******************************************************************************
 * Copyright (c) 2018 Yodlee Inc. All Rights Reserved.
 * 
 * This software is the confidential and proprietary information of
 * Yodlee, Inc. Use is subject to license terms.
 * 
 * @author
 ******************************************************************************/
package com.omni.pfm.Accounts;


import java.awt.AWTException;

import org.json.JSONException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.omni.pfm.Accounts.Accounts_LandingPage_Loc;
import com.omni.pfm.Accounts.Accounts_ViewByGroup_Loc;
import com.omni.pfm.PageProcessor.PageParser;
import com.omni.pfm.pages.AccountAddition.AccountAddition;
import com.omni.pfm.pages.Dashboard.Account_Loc;
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

public class AccountSharing_Accounts_Test extends TestBase {
	private static final Logger logger = LoggerFactory.getLogger(AccountSharing_Accounts_Test.class);
	AccountAddition accountAddition;
	Accounts_LandingPage_Loc accounts;
	LoginPage login;
	LoginPage_SAML3 SAML;
	public static String advRefId = null, investorName = null, advisorName = null;
	String AccountName, AccountPassword;
	Account_Loc account;
	ManageSharing_Loc sharing;
	Accounts_ViewByGroup_Loc group;

	@BeforeClass(alwaysRun = true)
	public void init() throws Exception {
		doInitialization("Account Sharing - Accounts");
		TestBase.tc = TestBase.extent.startTest("Login", "Login");
		TestBase.test.appendChild(TestBase.tc);

		accounts = new Accounts_LandingPage_Loc(d);
		accountAddition = new AccountAddition();
		SAML = new LoginPage_SAML3();
		login = new LoginPage();
		sharing = new ManageSharing_Loc(d);
		group = new Accounts_ViewByGroup_Loc(d);

	}

	/**
	 * Create advisor with Reference ID
	 * 
	 * @throws Exception
	 */

	@Test(description = " Create an advisor with investor linked", priority = 0)
	public void createAdvisor() throws Exception {
		
		logger.info(">>>>> Getting advisor referance ID");
		advRefId = SAML.getRefrenceId();

		logger.info(">>>>> Getting investor Name");
		investorName = SAML.getInvestorUserName();

		logger.info(">>>>> Creating an advisor.");
		advisorName = SAML.createAdvisor2(d, investorName, advRefId, PropsUtil.getDataPropertyValue("fastlink_App_ID"));

		logger.info(">>>>> Verifying advisor creation.");

		Assert.assertNotNull(advisorName, "==> Advisor Creation failed..");

	}

	/**
	 * Create an investor and add accounts to the accoun
	 * 
	 * @throws Exception
	 */

	@Test(description = "Create an investor..", priority = 1, dependsOnMethods = { "createAdvisor" })
	public void createInvestor() throws Exception {

		AccountName = PropsUtil.getDataPropertyValue("prepop_site_username");
		AccountPassword = PropsUtil.getDataPropertyValue("prepop_site_password");

		logger.info(">>>>> Creating an investor..");
		SAML.createInvestorWithPrepop(d, advisorName, investorName);

	}

	@Test(description = " Add a dag bank to investor", priority = 2, dependsOnMethods = { "createInvestor" })
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
		PageParser.forceNavigate("AccountsPage", d);
		SeleniumUtil.waitForPageToLoad();

		Assert.assertEquals(accounts.finappHeader().getText().trim(),
				PropsUtil.getDataPropertyValue("Accounts_Finapp_Header"), "==> Finapp Header not matching.");

	}

	@Test(description = " Verify Sidebar values", priority = 3, dependsOnMethods = { "createInvestor" })
	public void verifyAccountsSideBar() throws Exception {

		String sidebarvalues[] = PropsUtil.getDataPropertyValue("sidebar_values_accounts").split(",");
		logger.info(">>>>> Verifying sidebar values");
		for (int s = 0; s < accounts.getSidebarValues().size(); s++) {
			Assert.assertEquals(accounts.getSidebarValues().get(s).getText().trim(), sidebarvalues[s],
					"==> Side bar values did not match..");

		}
	}

	/**
	 * Set the permission to advisor balance only / full view
	 * 
	 * @throws Exception
	 */

	@Test(description = "Verify Manage Sharing Popup ", priority = 4, dependsOnMethods = {
			"createInvestor" })
	public void verifyManageSharingPopupOne() throws Exception {
		logger.info(">>>>> Verifying Manage Sharing Popup");
		SeleniumUtil.click(accounts.getMoreButtonAccounts().get(1));
		SeleniumUtil.waitForPageToLoad(2000);

		SeleniumUtil.click(accounts.getShareWithAdvisorValue().get(0));
		String popupHeader = accounts.popupHeader().getText().trim();

		Assert.assertEquals(popupHeader, PropsUtil.getDataPropertyValue("popupHeader_ManageSharing"));

	}

	@Test(description = "AT-61731,AT-61732,AT-61733,AT-61734: Give Permission To Account", priority = 5, dependsOnMethods = { "createInvestor",
			"verifyManageSharingPopupOne" })
	public void givePermissionToAccountOne() throws Exception {
		logger.info(">>>>> Giving Permission to the advisor..");
		SeleniumUtil.click(accounts.getAccountDropdown());
		SeleniumUtil.waitForPageToLoad(2000);

		SeleniumUtil.click(accounts.getPermissionValues().get(1));
		SeleniumUtil.waitForPageToLoad(2000);

		SeleniumUtil.click(accounts.getShareButton());
		SeleniumUtil.waitForPageToLoad(3000);

		Assert.assertTrue(accounts.getSharedByMeIcon().get(0).isDisplayed(), "==> Account Sharing Failed");
	}

	@Test(description = "Verify Manage Sharing Popup ", priority = 6, dependsOnMethods = {
			"createInvestor" })
	public void verifyManageSharingPopupTwo() throws Exception {
		logger.info(">>>>> Verifying Manage Sharing Popup");
		SeleniumUtil.click(accounts.getMoreButtonAccounts().get(2));
		SeleniumUtil.waitForPageToLoad(2000);

		SeleniumUtil.click(accounts.getShareWithAdvisorValue().get(0));
		String popupHeader = accounts.popupHeader().getText().trim();

		Assert.assertEquals(popupHeader, PropsUtil.getDataPropertyValue("popupHeader_ManageSharing"));

	}

	@Test(description = "AT-61732,AT-61733,AT-61734: Give Permission To Account", priority = 7, dependsOnMethods = { "createInvestor",
			"verifyManageSharingPopupTwo" })
	public void givePermissionToAccountTwo() throws Exception {
		logger.info(">>>>> Giving Permission to the advisor..");
		SeleniumUtil.click(accounts.getAccountDropdown());
		SeleniumUtil.waitForPageToLoad(2000);

		SeleniumUtil.click(accounts.getPermissionValues().get(1));
		SeleniumUtil.waitForPageToLoad(2000);

		SeleniumUtil.click(accounts.getShareButton());
		SeleniumUtil.waitForPageToLoad(3000);

		Assert.assertTrue(accounts.getSharedByMeIcon().get(1).isDisplayed(), "==> Account Sharing Failed");
	}

	/**
	 * Login as Advisor to view the permission given Refer shared with me icon to
	 * identify in dashboard
	 * 
	 * @throws Exception
	 */
	@Test(description = "Login as Advisor to view account details..", priority = 8)
	public void loginAsAdvisor() throws Exception {
		logger.info(">>>>> Logging in as advisor..");
		SAML.login2(d, advisorName, investorName, "10003700");
		SeleniumUtil.waitForPageToLoad();

		logger.info(">>>>> Navigating to accounts Page");
		PageParser.forceNavigate("AccountsPage", d);
		SeleniumUtil.waitForPageToLoad();

		Assert.assertEquals(accounts.finappHeader().getText().trim(),
				PropsUtil.getDataPropertyValue("Accounts_Finapp_Header"), "==> Finapp Header not matching.");
	}

	@Test(description = "AT-61747:Verify the accounts Shared by advisor", priority = 9, dependsOnMethods = {
			"loginAsAdvisor" })
	public void verifyAccountsShared() throws Exception {
		logger.info(">>>>> Verifying the accounts Shared by advisor..");
		for (int account = 0; account < accounts.getAggregatedAccountLabels().size(); account++) {
			logger.info("Verifying account" + (account + 1));
			Assert.assertTrue(accounts.getAggregatedAccountLabels().get(account).isDisplayed(),
					"==> Aggregated account Not displayed..");
		}
		logger.info(">>>>> Accounts verified..");

	}

	@Test(description = "AT-61736,AT-6173,AT-61738: Verify more dropdown values", priority = 10, dependsOnMethods = {
			"loginAsAdvisor" })
	public void verifyMoreOptions() throws Exception {
		logger.info(">>>>> Verifying More Values..");
		SeleniumUtil.click(accounts.getMoreButtonAccounts().get(0));

		String moreValues[] = PropsUtil.getDataPropertyValue("more_values_advisor_account").split(",");

		for (int more = 0; more < 2; more++) {
			logger.info(">>>>> Validating More Values");
			Assert.assertEquals(accounts.getAdvisorMoreValues().get(more).getText().trim(), moreValues[more],
					"==> Value of More Settings Did not match..");

		}
	}

	/**
	 * Verify shared with me icon
	 * 
	 * @throws Exception
	 */
	@Test(description = " Verify Sared with me icon", priority = 11, dependsOnMethods = { "loginAsAdvisor" })
	public void verifySharedWithMeIcon() throws Exception {
		logger.info(">>>>> Verifying Shared with me icon..");
		Assert.assertTrue(accounts.getSharedWithMeIcon().get(1).isDisplayed(),
				"==> Share With Me icon not displayed at the bottom..");
	}

	@Test(description = "Verify Accounts Groups and Verify No group Message", priority = 12, dependsOnMethods = {
			"loginAsAdvisor" })
	public void verifyAccountGroups() throws Exception {
		PageParser.forceNavigate("AccountsPage", d);
		SeleniumUtil.waitForPageToLoad();

		logger.info("Navigating to accounts Group..");
		SeleniumUtil.click(accounts.getSidebarValues().get(2));
	}

	/**
	 * Create account groups using the shared adn unshared accounts
	 * 
	 * @throws Exception
	 */
	@Test(description = "AT-61694,AT-61695,AT-61697:Verify User can create a group with shared and non shared accounts", priority = 13, dependsOnMethods = {
			"verifyAccountGroups" })
	public void verifyCreatingGroup_Adv() throws Exception {
		SeleniumUtil.click(group.groupType());
		SeleniumUtil.waitForPageToLoad(2000);
		SeleniumUtil.click(group.getCreateGroup());
		group.typeGroupName(PropsUtil.getDataPropertyValue("GroupName1").trim());
		SeleniumUtil.waitForPageToLoad(1000);
		group.selectCheckBoxAll();
		SeleniumUtil.click(group.createUpdateGroupBtm());
	}

	@Test(description = "AT-61694,AT-61695,AT-61696:Verify advisor can create a group with all the accounts available", priority = 14, dependsOnMethods = {
			"verifyCreatingGroup_Adv" })
	public void verifyAllAccountGroup_Adv() throws Exception {
		SeleniumUtil.click(group.CreateBtn1());
		SeleniumUtil.waitForPageToLoad(1000);
		group.typeGroupName(PropsUtil.getDataPropertyValue("GroupName2").trim());
		group.selectCheckBoxAll();
		SeleniumUtil.click(group.createUpdateGroupBtm());
		SeleniumUtil.waitForPageToLoad(1000);
	}

	/**
	 * Share advisor accounts to investor using YSL Account Sharing API
	 * 
	 * @throws JSONException
	 */

	
	@Test(description = " Create an advisor with investor linked", priority = 15)
	public void createAdvisor1() throws Exception {
		
		logger.info(">>>>> Getting advisor referance ID");
		advRefId = SAML.getRefrenceId();

		logger.info(">>>>> Getting investor Name");
		investorName = SAML.getInvestorUserName();

		logger.info(">>>>> Creating an advisor.");
		advisorName = SAML.createAdvisor2(d, investorName, advRefId, PropsUtil.getDataPropertyValue("fastlink_App_ID"));

		logger.info(">>>>> Verifying advisor creation.");

		Assert.assertNotNull(advisorName, "==> Advisor Creation failed..");

	}
	
	@Test(description = "Create an investor..", priority = 16, dependsOnMethods = { "createAdvisor" })
	public void addAcctoAdvisor() throws Exception {

		accountAddition.linkAccountFastlink();
		SeleniumUtil.waitForPageToLoad(2000);
		
		accountAddition.addAggregatedAccount("twoxml.bank1", "bank1");
		SeleniumUtil.waitForPageToLoad();

	}
	
	/**
	 * Create an investor and add accounts to the accoun
	 * 
	 * @throws Exception
	 */

	@Test(description = "Create an investor..", priority = 17, dependsOnMethods = { "createAdvisor" })
	public void createInvestor2() throws Exception {

		logger.info(">>>>> Creating an investor..");
		SAML.createInvestorWithPrepop(d, advisorName, investorName);

	}
	
	
	@Test(description = "Share advisor acounts to investor", priority = 18)
	public void shareAdvAccount() throws JSONException  {
		String samlResponse = SAML.loginResponse(d, advisorName, null);
		ysl.shareAdvisorAccount(samlResponse, advisorName, investorName);
	}

	@Test(description = "Login as Investor to view the accounts shared by advisor", priority = 19, dependsOnMethods = {
			"shareAdvAccount" })
	public void loginAsInvestor() throws Exception {
		SAML.login2(d, investorName, null, "10003700");
		SeleniumUtil.waitForPageToLoad();

		PageParser.forceNavigate("AccountsPage", d);
		SeleniumUtil.waitForPageToLoad();
		
		logger.info(">>>>> Verifying Shared with me icon..");
		Assert.assertTrue(accounts.getSharedWithMeIcon().get(1).isDisplayed(),
				"==> Share With Me icon not displayed at the bottom..");
	}

	@Test(description = "AT-61694,AT-61695,AT-61697:Verify Investor can create a group with shared and non shared accounts", priority = 20, dependsOnMethods = {
			"verifyAccountGroups" })
	public void verifyCreatingGroup_Inv() throws Exception {
		SeleniumUtil.click(group.groupType());
		SeleniumUtil.waitForPageToLoad(2000);
		SeleniumUtil.click(group.getCreateGroup());
		//SeleniumUtil.click(group.CreateBtn1());
		SeleniumUtil.waitForPageToLoad(3000);
		group.typeGroupName(PropsUtil.getDataPropertyValue("GroupName1").trim());
		SeleniumUtil.waitForPageToLoad(1000);
		group.selectCheckBoxAll();
		SeleniumUtil.click(group.createUpdateGroupBtm());
	}

	@Test(description = "AT-61694,AT-61695,AT-61696:Verify Investor can create a group with all the accounts available", priority = 21, dependsOnMethods = {
			"verifyCreatingGroup_Adv" })
	public void verifyAllAccountGroup_Inv() throws Exception {
		SeleniumUtil.click(group.CreateBtn1());
		SeleniumUtil.waitForPageToLoad(1000);
		group.typeGroupName(PropsUtil.getDataPropertyValue("GroupName2").trim());
		group.selectCheckBoxAll();
		SeleniumUtil.click(group.createUpdateGroupBtm());
		SeleniumUtil.waitForPageToLoad(1000);
	}

}
