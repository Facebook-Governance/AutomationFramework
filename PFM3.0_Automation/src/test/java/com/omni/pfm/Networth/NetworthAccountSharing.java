/*******************************************************************************
 * Copyright (c) 2018 Yodlee Inc. All Rights Reserved.
 * 
 * This software is the confidential and proprietary information of
 * Yodlee, Inc. Use is subject to license terms.
 * 
 * @author
 ******************************************************************************/
package com.omni.pfm.Networth;

import java.awt.AWTException;

import org.json.JSONException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.omni.pfm.PageProcessor.PageParser;
import com.omni.pfm.pages.AccountAddition.AccountAddition;
import com.omni.pfm.pages.Login.LoginPage_SAML3;
import com.omni.pfm.pages.Networth.NetWorth_Loc;
import com.omni.pfm.rest.ysl;
import com.omni.pfm.testBase.TestBase;
import com.omni.pfm.utility.PropsUtil;
import com.omni.pfm.utility.SeleniumUtil;

public class NetworthAccountSharing extends TestBase {
	Logger logger = LoggerFactory.getLogger(NetworthAccountSharing.class);
	LoginPage_SAML3 SAMlLogin;
	public static String advisorRef;
	public static String investorName;
	public static String advisorName;
	AccountAddition accountAdd;
	NetWorth_Loc accountSharing;

	@BeforeClass(alwaysRun=true)
	public void init() {
		doInitialization("Networth");
		TestBase.tc = TestBase.extent.startTest("Login", "Login");
		TestBase.test.appendChild(TestBase.tc);
		SAMlLogin = new LoginPage_SAML3();
		accountAdd = new AccountAddition();
		accountSharing = new NetWorth_Loc(d);
	}

	@Test(description = " Creating Advisor through SAML", priority = 1, enabled = true)
	public void creatingAdvisor() {
		/*
		 * Creating/Registering a Advisor through SAML
		 * 
		 */	
		advisorRef = SAMlLogin.getRefrenceId();
		advisorName = SAMlLogin.createAdvisor2(d, investorName, advisorRef,"10003600");
		System.out.println("Advisor Name:" + advisorName);
		SeleniumUtil.waitForPageToLoad();
	}

	@Test(description = " Aggregating Accounts in Advisor.", priority = 2, enabled = true)
	public void addAcntToAdvisor() throws InterruptedException, AWTException {
		
		/*		Aggregating accounts through Fastlink in advisor
		*/	
		SeleniumUtil.waitForPageToLoad();

		accountAdd.linkAccountFastlink();
		accountAdd.addAggregatedAccount("sarv10.site16441.1", "site16441.1");
		SeleniumUtil.waitForPageToLoad();
	}

	@Test(description = "AT-69104, Aggregating Accounts in Advisor.", priority = 3, enabled = true)
	public void verifyNwInAdvisor() throws InterruptedException, AWTException {

		/*		Validating Networth, Assets, Liabilities value after account 
		 * addition as this account should be in My Account section after account sharing		
		*/	
		SAMlLogin.login(d, advisorName, investorName, "10003700");
		accountSharing.forceNavigateToNetWorth();

		SeleniumUtil.waitForPageToLoad();
		/*SeleniumUtil.click(accountSharing.continueButton_NW());
		SeleniumUtil.click(accountSharing.seeMyNetWorthButton());*/
		SeleniumUtil.click(accountSharing.goToMyNetworthButton());
		SeleniumUtil.waitForPageToLoad(2000);
		
		accountSharing.verifyMyAccountNwAdv();

		SeleniumUtil.waitForPageToLoad();
	}

	@Test(description = " Creating Investor through SAML", priority = 4, enabled = true)
	public void creatingAndLoginToInvestor() {

		/*
		 * Creating/Registering a Investor through SAML
		 * 
		 */
		investorName = SAMlLogin.getInvestorUserName();
		SAMlLogin.createInvestor(d, advisorName, investorName, null, null);
	}

	@Test(description = " Aggregating accounts in Investor and Sharing account to Advisor.", priority = 5, enabled = true)
	public void addAccountInInvestor() throws InterruptedException, AWTException {

		/*		Aggregating accounts through Fastlink in Investor
		*/	
		accountAdd.linkAccount();
		SeleniumUtil.waitForPageToLoad(2000);
		accountAdd.addAggregatedAccount("AcntTest2.site16441.2", "site16441.2");

	}

	@Test(description = "AT-69103,AT-69104 Aggregating Accounts in Advisor.", priority = 6, enabled = true)
	public void verifyNwInInvestor() throws InterruptedException, AWTException {

		/*		Validating Networth, Assets, Liabilities value after account 
		 * addition as this account should be in My Account section after account sharing		
		*/	
		accountSharing.forceNavigateToNetWorth();
		SeleniumUtil.waitForPageToLoad();

		SeleniumUtil.click(accountSharing.continueButton_NW());
		SeleniumUtil.click(accountSharing.seeMyNetWorthButton());
		accountSharing.verifyMyAccountNwInv();

		SeleniumUtil.waitForPageToLoad();
	}

	@Test(description = " Sharing Accounts to Advisors with Balance/Full view access.", priority = 7, enabled = true)
	public void sharingAcntWithAdvisor() {

		/*		Sharing accounts from Investor to Advisor with Full View and Balance only access	
		*/	
		PageParser.forceNavigate("ManageSharing", d);
		SeleniumUtil.waitForPageToLoad();

		accountSharing.investmentAndInsuranceFullAccessShare();
		accountSharing.otherAcntFullAccessShare();
		accountSharing.shareWithViewBalncAccess();
		SeleniumUtil.click(accountSharing.AccountShareSave());
	}

	@Test(description = "Sharing Accounts to Investor with Balance/Full view access.", priority = 8, enabled = true)
	public void advisorToInvesorSharing() throws InterruptedException, AWTException, JSONException {
		/*		Sharing accounts from Advisor to Investor with Full View and Balance only access using YSL API's.	
		*/
		
		String samlResponse = LoginPage_SAML3.loginResponse(d, advisorName, null);
		ysl.shareAdvisorAccount(samlResponse, advisorName, investorName);
	}

	@Test(description = " Login NetWorth and Complete FTUE Flow for Advisor", priority = 9, enabled = true)
	public void loginToAdvisor() {
		/*		Login to Advisor after sharing accounts.	
		*/
		SAMlLogin.login(d, advisorName, investorName,"10003700");
		accountSharing.forceNavigateToNetWorth();
		SeleniumUtil.waitForPageToLoad();

	}

	@Test(description = "AT-69101, AT-69102,AT-69105   :The dropdown should show the advisor all his eligible accounts and the accounts shared by the investor.", priority = 10, enabled = true)
	public void verifyingAdvisorDropDown() {

		/*		Validating Networth Dropdown headers for shared accounts and My Account labels after account sharing.
		*/
		SeleniumUtil.click(accountSharing.allAccountsDropDown());

		logger.info("AT-67579: My accounts' should be shown on the drop down.");
		logger.info(
				"AT-67561:The 'Accounts Drop Down' should be split into 'All Accounts and Investor Shared Accounts'.");

		Assert.assertEquals(accountSharing.allAccountsHeader().getText().trim(),
				PropsUtil.getDataPropertyValue("NWAllAccountHeader").trim());
	}

	@Test(description = "AT-69105,AT-69106,AT-69107:  The dropdown should show the advisor all his eligible accounts and the accounts shared by the investor.", priority = 11, enabled = true)
	public void verifyAdvisorSubHeaders() {
		/*		Validating Networth Dropdown Sub Headers for shared accounts and My Account labels after account sharing.
		*/
		accountSharing.verifylevel2SubHeadersForAdv();
		accountSharing.verifyLevel3SubHeaders();

	}

	@Test(description = "AT-69108:The dropdown should show the advisor only the accounts shared by the investor with 'Full Access'.", priority = 12, enabled = true)
	public void verifyingInvestorsharedAcntCount() {

		/*		Validating no of account shared and are appearing in Networth Finapp
		 */
		accountSharing.sharedAcntCount();
	}

	@Test(description = "AT-69111,AT-69112,AT-69122,AT-69123:  Verify the assests and liabilites amount in NW summary section.", groups = {
			"DesktopBrowsers" }, priority = 13, enabled = true)
	public void nwAmountVerification() throws Exception {
		SeleniumUtil.refresh(d);

		/*		Validating Networth after sharing in Advisor.
		 */
		accountSharing.forceNavigateToNetWorth();

		accountSharing.verifyNetWorthAdv();

		SeleniumUtil.waitForPageToLoad();
	}

	@Test(description = "AT-69111,AT-69124:  Verify the assests and liabilites amount in NW summary section for My Accounts Only.", groups = {
			"DesktopBrowsers" }, priority = 14, enabled = true)
	public void nwAmountVerificationWithAccSharingAdv() throws Exception {
		SeleniumUtil.refresh(d);
		accountSharing.forceNavigateToNetWorth();
		
		/*		Validating Networth after sharing in Advisor for My Accounts only.
		 */
		SeleniumUtil.click(accountSharing.allAccountsDropDown());
		SeleniumUtil.click(accountSharing.allAccountsCheckBox());
		SeleniumUtil.click(accountSharing.myAccountsCheckBox());
		SeleniumUtil.click(accountSharing.allAccountsDropDown());
		SeleniumUtil.waitForPageToLoad();

		accountSharing.verifyMyAccountNwAdv();

	}

	@Test(description = "AT-69113,AT-69114: Verify the assests and liabilites amount in NW summary section for Shared Accounts Only.", groups = {
			"DesktopBrowsers" }, priority = 15, enabled = true)
	public void nwAmountVerificationWithAccSharingAdv2() throws Exception {

		/*		Validating Networth after sharing in Advisor for Shared Accounts only.
		 */
		accountSharing.forceNavigateToNetWorth();
		SeleniumUtil.click(accountSharing.allAccountsDropDown());

		SeleniumUtil.click(accountSharing.allAccountsCheckBox());
		SeleniumUtil.click(accountSharing.sharedAccountsCheckBox());
		SeleniumUtil.click(accountSharing.allAccountsDropDown());
		SeleniumUtil.waitForPageToLoad();

		accountSharing.verifySharedAccountNwAdv();

	}

	@Test(description = "AT-69111, AT-69112,AT-69120:  Verifying accounts dropdown in investor view and The dropdown: Which should show the advisor all his eligible accounts and the accounts shared by the investor.", priority = 16, enabled = true)
	public void verifyingInvestorAcntDropDown() {
		
		SAMlLogin.login(d, investorName, null,"10003700");
		/*		Login to Investor after sharing accounts.	
		*/
		accountSharing.forceNavigateToNetWorth();
		SeleniumUtil.waitForPageToLoad();
		/*		Validating Networth Dropdown headers in Investor for shared accounts and My Account labels after account sharing.
		*/
		
		SeleniumUtil.click(accountSharing.allAccountsDropDown());

		logger.info(" My accounts' should be shown on the drop down.");
		logger.info(
				"The 'Accounts Drop Down' should be split into 'All Expense Accounts and Investor Shared Accounts'.");

		Assert.assertEquals(accountSharing.allAccountsHeader().getText().trim(),
				PropsUtil.getDataPropertyValue("NWAllAccountHeader").trim());
	}

	@Test(description = "AT-69109,AT-69110:   The dropdown should show the advisor all his eligible accounts and the accounts shared by the investor.", priority = 17, enabled = true)
	public void verifyInvestorSubHeaders() {
		/*		Validating Networth Dropdown Sub Headers  in Investor for shared accounts and My Account labels after account sharing.
		*/
		accountSharing.verifylevel2SubHeadersForInv();
		accountSharing.verifyLevel3SubHeaders();
		SeleniumUtil.click(accountSharing.allAccountsDropDown());

	}

	@Test(description = "AT-69106,AT-69107,AT-69119,AT-69120 : Verify the assests and liabilites amount in NW summary section.", groups = {
			"DesktopBrowsers" }, priority = 18, enabled = true)
	public void nwAmountVerificationForInv() throws Exception {
		/*		Validating Networth after sharing in Investor
		 */
		accountSharing.verifyNetWorthInv();

		SeleniumUtil.waitForPageToLoad();
	}

	@Test(description = "AT-69113,AT-69125:  Verify the assests and liabilites amount in NW summary section for My Accounts Only.", groups = {
			"DesktopBrowsers" }, priority = 19, enabled = true)
	public void nwAmountVerificationWithAccSharingInv() throws Exception {
		SeleniumUtil.refresh(d);
		accountSharing.forceNavigateToNetWorth();
		/*		Validating Networth after sharing in Investor for My Accounts only.
		 */

		SeleniumUtil.click(accountSharing.allAccountsDropDown());
		SeleniumUtil.click(accountSharing.myAccountsCheckBox());
		SeleniumUtil.click(accountSharing.allAccountsDropDown());

		SeleniumUtil.waitForPageToLoad();
		accountSharing.verifySharedAccountNwValuesInv();

	}

	@Test(description = "AT-69117,AT-69118:  Verify the assests and liabilites amount in NW summary section for Shared Accounts Only.", groups = {
			"DesktopBrowsers" }, priority = 20, enabled = true)
	public void nwAmountVerificationWithAccSharingInv2() throws Exception {
		/*		Validating Networth after sharing in Investor for Shared Accounts only.
		 */
		accountSharing.forceNavigateToNetWorth();
		SeleniumUtil.click(accountSharing.allAccountsDropDown());
		SeleniumUtil.click(accountSharing.allAccountsCheckBox());
		SeleniumUtil.click(accountSharing.sharedAccountsCheckBox());
		SeleniumUtil.click(accountSharing.allAccountsDropDown());

		SeleniumUtil.waitForPageToLoad();

		accountSharing.verifySharedAccountNwValuesInv();
	}

	@Test(description = "AT-69111,AT-69112,AT-69121,AT-69126 : Verify that total net worth is the sum of Assets-liabilities. ", groups = {
			"DesktopBrowsers" }, priority = 21, enabled = true)
	public void checkAmountDetialsForAdv() {
		/*		Validating Networth after sharing in Investor with networth Calculations.
		 */
		double assets = accountSharing.getAssetsAmount();
		double liability = accountSharing.getLiabilityAmount();
		double nwAmount = accountSharing.getNWAmount();

		Assert.assertEquals(nwAmount, assets - liability);
	}

}
