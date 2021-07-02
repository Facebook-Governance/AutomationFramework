/*******************************************************************************
 * Copyright (c) 2018 Yodlee Inc. All Rights Reserved.
 * 
 * This software is the confidential and proprietary information of
 * Yodlee, Inc. Use is subject to license terms.
 * 
 * @author
 ******************************************************************************/
package com.omni.pfm.Cashflow;

import java.awt.AWTException;

import org.json.JSONException;
import org.openqa.selenium.WebElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.*;

import com.omni.pfm.Accounts.Accounts_ViewByGroup_Loc;
import com.omni.pfm.PageProcessor.PageParser;
import com.omni.pfm.pages.AccountAddition.AccountAddition;
import com.omni.pfm.pages.CashFlow.CashFlow_LandingPage_Loc;
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
public class AccountSharing_Cashflow_Changes_Test extends TestBase {
	private static final Logger logger = LoggerFactory.getLogger(AccountSharing_Cashflow_Changes_Test.class);
	AccountAddition accountAddition;
	LoginPage_SAML3 SAML;
	LoginPage login;
	CashFlow_LandingPage_Loc cashflow;
	ManageSharing_Loc sharing;
	public static String advRefId = null, investorName = null, advisorName = null;
	Accounts_ViewByGroup_Loc group;

	@BeforeClass(alwaysRun = true)
	public void init() throws Exception {
		doInitialization("Cashflow Account Sharing Changes");
		TestBase.tc = TestBase.extent.startTest("Login", "Login");
		TestBase.test.appendChild(TestBase.tc);

		accountAddition = new AccountAddition();
		SAML = new LoginPage_SAML3();
		login = new LoginPage();
		cashflow = new CashFlow_LandingPage_Loc(d);
		sharing = new ManageSharing_Loc(d);
		group = new Accounts_ViewByGroup_Loc(d);

	}

	/**
	 * Create an advisor
	 * 
	 * @throws AWTException
	 * @throws InterruptedException
	 * @throws JSONException
	 */
	@Test(description = "Create an advisor for above investor Name..", priority = 1)
	public void createAdvisor1() {

		advRefId = SAML.getRefrenceId();
		logger.info(advRefId);
		investorName = SAML.getInvestorUserName();
		logger.info(investorName);

		logger.info(">>>>> Creating an advisor with extra params segmentName=advisor&IID=Investorname.. ");
		advisorName = SAML.createAdvisor2(d, investorName, advRefId, "10003600");
		SeleniumUtil.waitForPageToLoad();
		logger.info(advisorName);

		Assert.assertNotNull(advisorName, ".. Advisor creation failded.");

	}

	/**
	 * Add aggregated account to advisor
	 * 
	 * @throws AWTException
	 * @throws InterruptedException
	 */
	@Test(description = "Create an advisor for above investor Name..", priority = 2, dependsOnMethods = {
			"createAdvisor1" })
	public void addAccountToAdvisor() throws AWTException, InterruptedException {
		accountAddition.linkAccountFastlink();
		SeleniumUtil.waitForPageToLoad(2000);

		accountAddition.addAggregatedAccount("svb_y.site16441.1", "site16441.1");
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

		logger.info(">>>>> Creating an investor and adding pre-populated accounts..");
		SAML.createInvestorWithPrepop(d, advisorName, investorName);
		SeleniumUtil.waitForPageToLoad();

	}

	@Test(description = "Verify Cashflow No account Screen", priority = 4, dependsOnMethods = {
			"createInvestor1" }, enabled = false)
	public void verifyCashflowNoAccountScreen() throws Exception {
		logger.info(">>>>> Navigating to cashflow screen..");
		PageParser.forceNavigate("CashFlow", d);
		SeleniumUtil.waitForPageToLoad();
		boolean noAccount = true;
		try {
			Assert.assertTrue(cashflow.no_data_screen_cf().isDisplayed());
			noAccount = false;
		} catch (Exception e) {
			logger.error(">>>>> No Accounts Are there for you..");
		}

		Assert.assertTrue(noAccount, "==> Accounts Present..");
	}

	@Test(description = "AT-73332,AT-73360:Add Account to Investor", priority = 5, dependsOnMethods = { "createInvestor1" })
	public void addAccountToInvestor() throws AWTException, InterruptedException {
		logger.info(">>>>> Adding account to investor");
		accountAddition.linkAccount();
		SeleniumUtil.waitForPageToLoad(2000);
		/* accountAddition.addContainerAccount("dagbank", "bhat.bank1", "bank1"); */
		accountAddition.addAggregatedAccount("bhat.bank1", "bank1");
		SeleniumUtil.waitForPageToLoad();

	}

	@Test(description = "Navigate to Manage Sharing Page and verify the advisor details", priority = 6, dependsOnMethods = {
			"createInvestor1" })
	public void verifyAdvisorAssigned() throws Exception {
		logger.info(">>>>> Navigating to Manage Sharing..");
		PageParser.forceNavigate("ManageSharing", d);
		SeleniumUtil.waitForPageToLoad();

		Assert.assertTrue(cashflow.get_sidebar_value().isDisplayed(), "..... Advisor details not found..");
	}

	@Test(description = "Giving permission to advisor ", priority = 7, dependsOnMethods = {
			"createInvestor1", "verifyAdvisorAssigned" })
	public void givePermission_FullView() throws Exception {
		logger.info(">>>>> Giving Permission to accounts..");
		for (int i = 1; i < sharing.dropdownHeader().size(); i++) {
			SeleniumUtil.click(sharing.dropdownHeader().get(i));
			SeleniumUtil.waitForPageToLoad(2000);

			SeleniumUtil.click(sharing.getDropdownValue(i + 1).get(0));
		}

		Assert.assertTrue(sharing.getSaveChanges().isDisplayed(), "..... Save Changes button is not displayed");
	}

	@Test(description = "Save the permissions given by investor", priority = 8, dependsOnMethods = {
			"createInvestor1", "givePermission_FullView" })
	public void savePermissionChanges() throws Exception {
		logger.info(">>>>> Verifying Save Changes..");

		SeleniumUtil.waitForPageToLoad(4000);
		SeleniumUtil.click(sharing.getSaveChanges());

		Assert.assertEquals(sharing.finappHeader().getText().trim(),
				PropsUtil.getDataPropertyValue("ManageSharing_Finapp_Header"),
				"..... Finapp headers are not matching.");
	}

	@Test(description = "AT-73362:Login As advisor and check the accounts are displayed and cretate a group", priority = 9)
	public void verifyCreatingGroups() throws Exception {
		cashflow.loginAsAdvisor(advisorName, investorName, "10003700");
		PageParser.forceNavigate("CashFlow", d);
		SeleniumUtil.waitForPageToLoad();

		cashflow.ftue_continue();

		logger.info(">>>>> Navigating to Acounts Page");
		PageParser.forceNavigate("AccountsPage", d);
		SeleniumUtil.waitForPageToLoad();

		SeleniumUtil.click(group.groupType());
		SeleniumUtil.waitForPageToLoad(2000);
		SeleniumUtil.click(group.getCreateGroup());
		group.typeGroupName(PropsUtil.getDataPropertyValue("GroupName1").trim());
		SeleniumUtil.waitForPageToLoad(1000);
		group.selectCheckBoxAll();
		SeleniumUtil.click(group.createUpdateGroupBtm());

	}

	@Test(description = "AT-73369:Creating Group 2.", priority = 10, dependsOnMethods = {
			"verifyCreatingGroups" }, enabled = true)
	public void createGroup2() throws Exception {

		// SeleniumUtil.click(group.moreButon());
		SeleniumUtil.click(group.CreateBtn1());
		SeleniumUtil.waitForPageToLoad(1000);
		group.typeGroupName(PropsUtil.getDataPropertyValue("GroupName2").trim());
		group.selectCheckBoxAll();
		SeleniumUtil.click(group.createUpdateGroupBtm());
		SeleniumUtil.waitForPageToLoad(1000);

	}

	@Test(description = "AT-73367,AT-73368:Verify Groups Name created by advisor", priority = 11, dependsOnMethods = {
			"createGroup2" })
	public void verifyGroupNames() throws Exception {
		logger.info(">>>>> Navigating to Cashflow.");
		PageParser.forceNavigate("CashFlow", d);
		SeleniumUtil.waitForPageToLoad();

		SeleniumUtil.click(cashflow.account_dropdown());
		SeleniumUtil.waitForPageToLoad(2000);

		String groupNamesExpected[] = PropsUtil.getDataPropertyValue("dropdown_groups_as").split(",");

		for (int i = 0; i < cashflow.groups_names().size(); i++) {
			Assert.assertEquals(cashflow.groups_names().get(i).getText().trim(), groupNamesExpected[i].trim(),
					".... groupName mismatched.");

		}
	}

	@Test(description = "AT-73335:Verify Forecst is not displayed for advisor", priority = 12)
	public void verifyNoForecast() throws Exception {
		logger.info(">>>>> Verifying to get forecast checkbox");
		boolean forecast = true;
		try {
			WebElement we = cashflow.checkbox_forecast();
		} catch (Exception e) {
			logger.error("No Forecast for advisor.");
			forecast = false;
		}
		Assert.assertTrue(forecast, ".. Forecast is displayed for advisor. Validate Manually");
	}

	@Test(description = "AT-73366:Giving permission to advisor ", priority = 13, dependsOnMethods = {
			"createInvestor1", "verifyAdvisorAssigned" })
	public void givePermission_FullView2() throws Exception {
		logger.info(">>>>> Logging in as Investor");
		SAML.login2(d, investorName, null, "10003700");
		SeleniumUtil.waitForPageToLoad();

		PageParser.forceNavigate("ManageSharing", d);
		SeleniumUtil.waitForPageToLoad();

		logger.info(">>>>> Giving Permission to accounts..");
		for (int i = 1; i < sharing.dropdownHeader().size(); i++) {
			SeleniumUtil.click(sharing.dropdownHeader().get(i));
			SeleniumUtil.waitForPageToLoad(2000);

			SeleniumUtil.click(sharing.getDropdownValue(i + 1).get(1));
		}

		Assert.assertTrue(sharing.getSaveChanges().isDisplayed(), "..... Save Changes button is not enabled");
	}

	@Test(description = "Save the permissions given by investor", priority = 14, dependsOnMethods = {
			"createInvestor1", "givePermission_FullView" })
	public void savePermissionChanges2() throws Exception {
		logger.info(">>>>> Verifying Save Changes..");

		SeleniumUtil.waitForPageToLoad(4000);
		SeleniumUtil.click(sharing.getSaveChanges());

		Assert.assertEquals(sharing.finappHeader().getText().trim(),
				PropsUtil.getDataPropertyValue("ManageSharing_Finapp_Header"),
				"..... Finapp headers are not matching.");
	}

	@Test(description = "Login as advisor", priority = 15)
	public void loginAsAdvisor_BalanceOnly() throws Exception {
		cashflow.loginAsAdvisor(advisorName, investorName, "10003700");
		SeleniumUtil.waitForPageToLoad();

		PageParser.forceNavigate("CashFlow", d);
		SeleniumUtil.waitForPageToLoad();

		Assert.assertTrue(cashflow.no_access_screen().isDisplayed(), ".....No Access Screen is not displayed..");
	}

	/**
	 * Create advisor
	 * 
	 * Advisor shared accounts to investor scenario.
	 * 
	 * @throws Exception
	 */

	@Test(description = "Create an advisor", priority = 16)
	public void createAdvisor2() throws Exception {
		advRefId = SAML.getRefrenceId();
		logger.info(advRefId);
		investorName = SAML.getInvestorUserName();
		logger.info(investorName);

		logger.info(">>>>> Creating an advisor with extra params segmentName=advisor&IID=Investorname.. ");
		advisorName = SAML.createAdvisor2(d, investorName, advRefId, "10003600");
		SeleniumUtil.waitForPageToLoad();
		logger.info(advisorName);

		boolean advCreation = false;

		if (advisorName != null) {
			advCreation = true;
		}

		Assert.assertTrue(advCreation, ">>>>> Advisor Creation failed..");
	}

	@Test(description = "Add aggregated account to advisor", priority = 17, dependsOnMethods = {
			"createAdvisor2" })
	public void addAccountToAdvisor2() throws AWTException, InterruptedException {
		accountAddition.linkAccountFastlink();
		SeleniumUtil.waitForPageToLoad(2000);

		accountAddition.addAggregatedAccount("bhat.bank1", "bank1");
		SeleniumUtil.waitForPageToLoad();
	}

	@Test(description = "Create an investor", priority = 18, dependsOnMethods = { "createAdvisor2" })
	public void createInvestor2() throws Exception {
		boolean expectedInvestor = false;
		if (advisorName != null) {
			logger.info(">>>>> Creating an investor and adding pre-populated accounts..");
			SAML.createInvestorWithPrepop(d, advisorName, investorName);
			SeleniumUtil.waitForPageToLoad();

			expectedInvestor = true;
		}

		Assert.assertTrue(expectedInvestor);
	}

	/**
	 * Share advisor account to investor using YSL API
	 * 
	 * @throws AWTException
	 * @throws InterruptedException
	 * @throws JSONException
	 */

	@Test(description = "AT-73334:Create an advisor for above investor Name..", priority = 19, dependsOnMethods = {
			"createAdvisor2", "createInvestor2" })
	public void shareAdvisorAccount() throws AWTException, InterruptedException, JSONException {
		logger.info(">>>>> Sharing accounts to the investor");
		String samlResponse = SAML.loginResponse(d, advisorName, investorName);
		ysl.shareAdvisorAccount(samlResponse, advisorName, investorName);
	}

	@Test(description = "AT-73346,AT-73367:Login as investor", priority = 20, dependsOnMethods = { "shareAdvisorAccount" })
	public void verifyCreatingGroups2() throws Exception {
		logger.info(">>>>> Logging in as investor");
		SAML.login2(d, investorName, null, "10003700");
		SeleniumUtil.waitForPageToLoad();

		logger.info(">>>>> Navigating to Acounts Page");
		PageParser.forceNavigate("AccountsPage", d);
		SeleniumUtil.waitForPageToLoad();

		SeleniumUtil.click(group.groupType());
		SeleniumUtil.waitForPageToLoad(2000);
		SeleniumUtil.click(group.getCreateGroup());
		group.typeGroupName(PropsUtil.getDataPropertyValue("GroupName1").trim());
		SeleniumUtil.waitForPageToLoad(1000);
		group.selectCheckBoxAll();
		SeleniumUtil.click(group.createUpdateGroupBtm());
	}

	@Test(description = "Creating Group 2.", priority = 21, dependsOnMethods = {
			"verifyCreatingGroups2" }, enabled = true)
	public void createGroup3() throws Exception {

		logger.info(">>>>> Creating group 2");
		// SeleniumUtil.click(group.moreButon());
		SeleniumUtil.click(group.CreateBtn1());
		SeleniumUtil.waitForPageToLoad(1000);
		group.typeGroupName(PropsUtil.getDataPropertyValue("GroupName2").trim());
		group.selectCheckBoxAll();
		SeleniumUtil.click(group.createUpdateGroupBtm());
		SeleniumUtil.waitForPageToLoad(1000);

	}

	@Test(description = "Verify Groups Name created by advisor", priority = 22, dependsOnMethods = {
			"createGroup3" })
	public void verifyGroupNames2() throws Exception {
		logger.info(">>>>> Navigating to Cashflow.");
		PageParser.forceNavigate("CashFlow", d);
		SeleniumUtil.waitForPageToLoad();
		cashflow.ftue_continue();

		SeleniumUtil.click(cashflow.account_dropdown());
		SeleniumUtil.waitForPageToLoad(2000);

		String groupNamesExpected[] = PropsUtil.getDataPropertyValue("dropdown_groups_as").split(",");

		for (int i = 0; i < cashflow.groups_names().size(); i++) {
			Assert.assertEquals(cashflow.groups_names().get(i).getText().trim(), groupNamesExpected[i].trim(),
					".... groupName mismatched.");

		}
	}

	@Test(description = "AT-73335,AT-73357,AT-73370:Verify Forecst is not displayed for advisor", priority = 23)
	public void verifyForecast() throws Exception {
		logger.info(">>>>> Verifying to get forecast checkbox");
		boolean forecast = false;
		try {
			WebElement we = cashflow.checkbox_forecast();
			forecast = true;
		} catch (Exception e) {
			logger.error("No Forecast for advisor.");

		}
		Assert.assertTrue(forecast, ".. Forecast is displayed for advisor. Validate Manually");
	}

}
