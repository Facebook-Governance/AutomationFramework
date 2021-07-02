
/**
* Copyright (c) 2018 Yodlee Inc. All Rights Reserved.
*
* This software is the confidential and proprietary information of
* Yodlee, Inc. Use is subject to license terms.
*
* @author Renuka K
*/
package com.omni.pfm.Accounts;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.omni.pfm.PageProcessor.PageParser;
import com.omni.pfm.pages.AccountAddition.AccountAddition;
import com.omni.pfm.pages.AccountSettings.AccountsSetting_GlobalSettings_Loc;
import com.omni.pfm.pages.Dashboard.Account_Loc;
import com.omni.pfm.pages.Login.LoginPage;
import com.omni.pfm.testBase.TestBase;
import com.omni.pfm.utility.PropsUtil;
import com.omni.pfm.utility.SeleniumUtil;

public class Account_ViewByFI_LOCType_Test extends TestBase {
	Logger logger = LoggerFactory.getLogger(Account_ViewByFI_LOCType_Test.class);
	AccountsViewByFI_Loc viewByFI;
	AccountAddition accountAddition;
	SeleniumUtil util;
	Accounts_AccountSettingPopUp_Loc accountSetting;
	AccountsLandingPage_Loc accountLanding;
	Accounts_ViewByAccountType_Loc accountTypeView;
	Accounts_ViewByGroup_Loc groupView;
	Account_Loc accountDashBoard;
	Accounts_DeleteAccount_Loc deleetAccount;
	AccountsSetting_GlobalSettings_Loc globelAcctSetting;

	@BeforeClass()
	public void init() throws Exception {
		doInitialization("Accounts");

		TestBase.tc = TestBase.extent.startTest("Login", "Login");
		TestBase.test.appendChild(TestBase.tc);

		viewByFI = new AccountsViewByFI_Loc(d);
		accountAddition = new AccountAddition();
		accountSetting = new Accounts_AccountSettingPopUp_Loc(d);
		util = new SeleniumUtil();
		accountLanding = new AccountsLandingPage_Loc(d);
		accountTypeView = new Accounts_ViewByAccountType_Loc(d);
		accountDashBoard = new Account_Loc(d);
		groupView = new Accounts_ViewByGroup_Loc(d);
		deleetAccount = new Accounts_DeleteAccount_Loc(d);
		globelAcctSetting = new AccountsSetting_GlobalSettings_Loc(d);

		LoginPage.loginMain(d, loginParameter);
		accountAddition.linkAccount();
		accountAddition.addAggregatedAccount1(PropsUtil.getDataPropertyValue("LOC_LinkAccountName"),
				PropsUtil.getDataPropertyValue("LOC_LinkAccountPAssword"));
		groupView.refreshPage();

	}

	@Test(description = "AT-96994,AT-97004,AT-97015:Verify Accounts container in FI View", groups = {
			"DesktopBrowsers,sanity" }, priority = 1, enabled = true)
	public void verifyAccountConainer_FI() {
		logger.info("1.navigate to Accounts FInapp 2.verify accounts container name in FI view");
		PageParser.forceNavigate("AccountsPage", d);
		SeleniumUtil.waitForPageToLoad(7000);
		
		try {
			viewByFI.accountslayout().isDisplayed();
		}catch(Throwable e) {
			PageParser.forceNavigate("AccountsPage", d);
			SeleniumUtil.waitForPageToLoad(3000);
		}
		
		Assert.assertEquals(
				viewByFI.AccountsContainerName()
						.get(Integer.parseInt(PropsUtil.getDataPropertyValue("LOC_AccountFI_AccountContainerIndex")))
						.getText().trim(),
				PropsUtil.getDataPropertyValue("LOC_AccountFI_AccountContainerName"),
				"account container name Dag site is not displayed");

	}

	@Test(description = "AT-96994,AT-97004,AT-97015:verify the Loan container name in FI View", groups = {
			"DesktopBrowsers,sanity" }, priority = 2, dependsOnMethods = "verifyAccountConainer_FI", enabled = true)
	public void verifyLocAccountTypeHeader_FI() {
		logger.info("verify all 3 Type LOC account Name in FI View");
		Assert.assertEquals(viewByFI
				.AccountFI_AccountsTypeName(PropsUtil.getDataPropertyValue("LOC_AccountFI_AccountLoanContainerIndex"))
				.getText().trim(), PropsUtil.getDataPropertyValue("LOC_AccountFI_AccountLoanContainerName"),
				"Loan container is not displayed");
	}

	@Test(description = "AT-96994,AT-97004,AT-97015:verify the Loan Accounts name(LOC) in FI View", groups = {
			"DesktopBrowsers,sanity" }, priority = 3, dependsOnMethods = "verifyLocAccountTypeHeader_FI", enabled = true)
	public void verifyLocAccountTypeName_FI() {
		logger.info("1.verify all 3 Type LOC account Name");
		util.assertExpectedActualList(
				util.getWebElementsValue(viewByFI.AccountFI_AccountType_AccountName(
						PropsUtil.getDataPropertyValue("LOC_AccountFI_AccountLoanContainerIndex"))),
				PropsUtil.getDataPropertyValue("LOC_AccountFI_AccountLoanContainerAccountName"),
				"all 3 type LOC accounts name is not displayed");
	}

	@Test(description = "AT-96994,AT-97004,AT-97015:verify the Loan Accounts Number(LOC) in FI View", groups = {
			"DesktopBrowsers,sanity" }, priority = 4, dependsOnMethods = "verifyLocAccountTypeHeader_FI", enabled = true)
	public void verifyLocAccountTypeNum_FI() {
		logger.info("1.verify all 3 Type LOC account Number");
		if (appFlag.equals("app") || appFlag.equals("emulator")) {
			util.assertExpectedActualList(
					util.getWebElementsValue(viewByFI.AccountFI_AccountType_AccountNumMobile(
							PropsUtil.getDataPropertyValue("LOC_AccountFI_AccountLoanContainerIndex"))),
					PropsUtil.getDataPropertyValue("LOC_AccountFI_AccountLoanContainerAccountNum"),
					"all 3 type LOC accounts Number is not displayed");
		} else {
			util.assertExpectedActualList(viewByFI.returnAccountNumbers(
							PropsUtil.getDataPropertyValue("LOC_AccountFI_AccountLoanContainerIndex")),
					PropsUtil.getDataPropertyValue("LOC_AccountFI_AccountLoanContainerAccountNum"),
					"all 3 type LOC accounts Number is not displayed");
		}
	}

	@Test(description = "AT-96994,AT-97004,AT-97015:verify the Loan Accounts Balance(LOC) in FI View", groups = {
			"DesktopBrowsers,sanity" }, priority = 5, dependsOnMethods = "verifyLocAccountTypeHeader_FI", enabled = true)
	public void verifyLocAccountTypeBal_FI() {
		logger.info("1.verify all 3 Type LOC account Balance");
		util.assertExpectedActualAmountList(
				util.getWebElementsValue(viewByFI.AccountFI_AccountType_AccountBal(
						PropsUtil.getDataPropertyValue("LOC_AccountFI_AccountLoanContainerIndex"))),
				PropsUtil.getDataPropertyValue("LOC_AccountFI_AccountLoanContainerAccountBal"),
				"all 3 type LOC accounts Balance is not displayed");
	}

	@Test(description = "AT-96994,AT-97004,AT-97015:verify the Loan Accounts NickName(LOC) in FI View", groups = {
			"DesktopBrowsers,sanity" }, priority = 6, dependsOnMethods = "verifyLocAccountTypeHeader_FI", enabled = true)
	public void verifyLocAccountTypeNickName() {
		SeleniumUtil.waitFor(3);
		logger.info("1.verify  LOC account nick Name");
		viewByFI.clickAccountSetting(PropsUtil.getDataPropertyValue("LOC_AccountFI_AccountLoanContainerIndex"),
				PropsUtil.getDataPropertyValue("LOC_AccountFI_AccountLoanContainerAccountRow"),
				PropsUtil.getDataPropertyValue("LOC_AccountFI_AccountLoanContainerMoreMenu"));
		accountSetting.nickName(PropsUtil.getDataPropertyValue("LOC_AccountFI_AccountLoanContainerAccountNickName"));
		SeleniumUtil.waitForPageToLoad();

		Assert.assertEquals(
				viewByFI.returnNickName(PropsUtil.getDataPropertyValue("LOC_AccountFI_AccountLoanContainerIndex")),
				PropsUtil.getDataPropertyValue("LOC_AccountFI_AccountLoanContainerAccountNickName"),
				" LOC account nick Name is not displayed");
	}

	@Test(description = "AT-97007,AT-97003:verify accounts type header in Account Type view", groups = {
			"DesktopBrowsers,sanity" }, priority = 7, dependsOnMethods = "verifyAccountConainer_FI", enabled = true)
	public void verifyLocAccountContainerHeader_TypeView() {
		SeleniumUtil.click(accountLanding.AccountsViewList()
				.get(Integer.parseInt(PropsUtil.getDataPropertyValue("LOC_AccountType_Index"))));
		SeleniumUtil.waitForPageToLoad();
		Assert.assertEquals(
				accountTypeView
						.AccountTypeView_AccountsTypeName(
								PropsUtil.getDataPropertyValue("LOC_AccountType__AccountContainerIndex"))
						.getText().trim(),
				PropsUtil.getDataPropertyValue("LOC_AccountType_AccountContainerName"),
				"LOC accounts type header is not displayed");

	}

	@Test(description = "AT-97004,AT-97009,AT-97010,AT-97011,AT-97012,AT-97008:verify home equiety account name in Account Type view", groups = {
			"DesktopBrowsers,sanity" }, priority = 8, dependsOnMethods = "verifyAccountConainer_FI", enabled = true)
	public void verifyHELOCAccountName_TypeView() {
		Assert.assertEquals(
				accountTypeView.AccountTypeView_AccountName(
						PropsUtil.getDataPropertyValue("LOC_AccountType__AccountContainerIndex"),
						PropsUtil.getDataPropertyValue("HELOC_AccountType_AccountIndex")).getText().trim(),
				PropsUtil.getDataPropertyValue("HELOC_AccountType_AccountName"),
				"home equiety account name is not displayed");
		Assert.assertEquals(
				accountTypeView.AccountTypeView_AccountSiteName(
						PropsUtil.getDataPropertyValue("LOC_AccountType__AccountContainerIndex"),
						PropsUtil.getDataPropertyValue("HELOC_AccountType_AccountIndex")).getText().trim(),
				PropsUtil.getDataPropertyValue("HELOC_AccountType_AccountSiteName"),
				"home equiety account name is not displayed");
		if (appFlag.equals("app") || appFlag.equals("emulator")) {
			Assert.assertEquals(
					accountTypeView.AccountTypeView_AccountNumMobile(
							PropsUtil.getDataPropertyValue("LOC_AccountType__AccountContainerIndex"),
							PropsUtil.getDataPropertyValue("HELOC_AccountType_AccountIndex")).getText().trim(),
					PropsUtil.getDataPropertyValue("HELOC_AccountType_AccountNumber"),
					"home equiety account name is not displayed");
		} else {
			Assert.assertEquals(
					accountTypeView.returnAccountNumber(
							PropsUtil.getDataPropertyValue("LOC_AccountType__AccountContainerIndex"),
							PropsUtil.getDataPropertyValue("HELOC_AccountType_AccountIndex")),
					PropsUtil.getDataPropertyValue("HELOC_AccountType_AccountNumber"),
					"home equiety account name is not displayed");
		}
		Assert.assertEquals(
				accountTypeView.AccountTypeView_AccountBal(
						PropsUtil.getDataPropertyValue("LOC_AccountType__AccountContainerIndex"),
						PropsUtil.getDataPropertyValue("HELOC_AccountType_AccountIndex")).getText().trim(),
				PropsUtil.getDataPropertyValue("HELOC_AccountType_AccountBalance"),
				"home equiety account name is not displayed");
		Assert.assertEquals(
				accountTypeView.AccountTypeView_AccountNickName(
						PropsUtil.getDataPropertyValue("LOC_AccountType__AccountContainerIndex"),
						PropsUtil.getDataPropertyValue("HELOC_AccountType_AccountIndex")).getText().trim(),
				PropsUtil.getDataPropertyValue("LOC_AccountFI_AccountLoanContainerAccountNickName"),
				"home equiety account name is not displayed");

	}

	@Test(description = "AT-97009,AT-97010,AT-97011,AT-97012,AT-97008:verify Line of credit account name in Account Type view", groups = {
			"DesktopBrowsers,sanity" }, priority = 9, dependsOnMethods = "verifyAccountConainer_FI", enabled = true)
	public void verifyLOCAccountName_TypeView() {
		Assert.assertEquals(
				accountTypeView.AccountTypeView_AccountName(
						PropsUtil.getDataPropertyValue("LOC_AccountType__AccountContainerIndex"),
						PropsUtil.getDataPropertyValue("LOC_AccountType_AccountIndex")).getText().trim(),
				PropsUtil.getDataPropertyValue("LOC_AccountType_AccountName"),
				"Line of credit account name is not displayed");
		Assert.assertEquals(
				accountTypeView.AccountTypeView_AccountSiteName(
						PropsUtil.getDataPropertyValue("LOC_AccountType__AccountContainerIndex"),
						PropsUtil.getDataPropertyValue("LOC_AccountType_AccountIndex")).getText().trim(),
				PropsUtil.getDataPropertyValue("LOC_AccountType_AccountSiteName"),
				"Line of credit account name is not displayed");
		if (appFlag.equals("app") || appFlag.equals("emulator")) {
			Assert.assertEquals(
					accountTypeView.AccountTypeView_AccountNumMobile(
							PropsUtil.getDataPropertyValue("LOC_AccountType__AccountContainerIndex"),
							PropsUtil.getDataPropertyValue("LOC_AccountType_AccountIndex")).getText().trim(),
					PropsUtil.getDataPropertyValue("LOC_AccountType_AccountNumber"),
					"Line of credit account name is not displayed");
		} else {
			Assert.assertEquals(
					accountTypeView.returnAccountNumber(
							PropsUtil.getDataPropertyValue("LOC_AccountType__AccountContainerIndex"),
							PropsUtil.getDataPropertyValue("LOC_AccountType_AccountIndex")),
					PropsUtil.getDataPropertyValue("LOC_AccountType_AccountNumber"),
					"Line of credit account name is not displayed");
		}
		Assert.assertEquals(
				accountTypeView.AccountTypeView_AccountBal(
						PropsUtil.getDataPropertyValue("LOC_AccountType__AccountContainerIndex"),
						PropsUtil.getDataPropertyValue("LOC_AccountType_AccountIndex")).getText().trim(),
				PropsUtil.getDataPropertyValue("LOC_AccountType_AccountBalance"),
				"Line of credit account name is not displayed");

	}

	@Test(description = "AT-97005,AT-97009,AT-97010,AT-97011,AT-97012,AT-97008:verify Securities Backed account name in Account Type view", groups = {
			"DesktopBrowsers,sanity" }, priority = 10, dependsOnMethods = "verifyAccountConainer_FI", enabled = true)
	public void verifySBLOCAccountName_TypeView() {
		Assert.assertEquals(
				accountTypeView.AccountTypeView_AccountName(
						PropsUtil.getDataPropertyValue("LOC_AccountType__AccountContainerIndex"),
						PropsUtil.getDataPropertyValue("SBLOC_AccountType_AccountIndex")).getText().trim(),
				PropsUtil.getDataPropertyValue("SBLOC_AccountType_AccountName"),
				"Securities Backed account name is not displayed");
		Assert.assertEquals(
				accountTypeView.AccountTypeView_AccountSiteName(
						PropsUtil.getDataPropertyValue("LOC_AccountType__AccountContainerIndex"),
						PropsUtil.getDataPropertyValue("SBLOC_AccountType_AccountIndex")).getText().trim(),
				PropsUtil.getDataPropertyValue("SBLOC_AccountType_AccountSiteName"),
				"Securities Backed account name is not displayed");
		if (appFlag.equals("app") || appFlag.equals("emulator")) {
			Assert.assertEquals(
					accountTypeView.AccountTypeView_AccountNumMobile(
							PropsUtil.getDataPropertyValue("LOC_AccountType__AccountContainerIndex"),
							PropsUtil.getDataPropertyValue("SBLOC_AccountType_AccountIndex")).getText().trim(),
					PropsUtil.getDataPropertyValue("SBLOC_AccountType_AccountNumber"),
					"Securities Backed account name is not displayed");
		} else {
			Assert.assertEquals(
					accountTypeView.returnAccountNumber(
							PropsUtil.getDataPropertyValue("LOC_AccountType__AccountContainerIndex"),
							PropsUtil.getDataPropertyValue("SBLOC_AccountType_AccountIndex")),
					PropsUtil.getDataPropertyValue("SBLOC_AccountType_AccountNumber"),
					"Securities Backed account name is not displayed");
		}
		Assert.assertEquals(
				accountTypeView.AccountTypeView_AccountBal(
						PropsUtil.getDataPropertyValue("LOC_AccountType__AccountContainerIndex"),
						PropsUtil.getDataPropertyValue("SBLOC_AccountType_AccountIndex")).getText().trim(),
				PropsUtil.getDataPropertyValue("SBLOC_AccountType_AccountBalance"),
				"Securities Backed account name is not displayed");

	}

	@Test(description = "AT-97016,AT-97017,AT-97018,AT-97020:verify Securities Backed account name in Account Type view", groups = {
			"DesktopBrowsers,sanity" }, priority = 11, dependsOnMethods = "verifyAccountConainer_FI", enabled = true)
	public void verifyAllAccount_GroupView() {
		groupView.clikcCreatgroup();
		SeleniumUtil.waitForPageToLoad();
		int accountLength = groupView.AccountGroupTypeView_GrpAccName().size();
		int Loc = 0;
		int HELoc = 0;
		int SBLoc = 0;

		for (int i = 0; i < accountLength; i++) {
			System.out.println(groupView.AccountGroupTypeView_GrpAccName().get(i).getText());
			if (groupView.AccountGroupTypeView_GrpAccName().get(i).getText().trim()
					.equals(PropsUtil.getDataPropertyValue("HeLOC_GroupView_GroupPopupAccountName").trim())) {
				HELoc = HELoc + 1;
			} else if (groupView.AccountGroupTypeView_GrpAccName().get(i).getText().trim()
					.equals(PropsUtil.getDataPropertyValue("LOC_GroupView_GroupPopupAccountName").trim().trim())) {
				Loc = Loc + 1;
			} else if (groupView.AccountGroupTypeView_GrpAccName().get(i).getText().trim()
					.equals(PropsUtil.getDataPropertyValue("SBLOC_GroupView_GroupPopupAccountName").trim())) {
				SBLoc = SBLoc + 1;
			}
		}

		Assert.assertEquals(HELoc,
				Integer.parseInt(PropsUtil.getDataPropertyValue("HeLOC_GroupView_GroupPopupAccountcount")), "");
		Assert.assertEquals(Loc,
				Integer.parseInt(PropsUtil.getDataPropertyValue("LOC_GroupView_GroupPopupAccountcount")), "");
		Assert.assertEquals(SBLoc,
				Integer.parseInt(PropsUtil.getDataPropertyValue("SBLOC_GroupView_GroupPopupAccountcount")), "");
	}

	@Test(description = "AT-97016,AT-97017,AT-97018,AT-97020:verify accounts type header in Account Type view", groups = {
			"DesktopBrowsers,sanity" }, priority = 12, dependsOnMethods = "verifyAccountConainer_FI", enabled = true)
	public void verifyLocAccountContainerHeader_GroupView() {
		groupView.createGroup(PropsUtil.getDataPropertyValue("GroupView_GroupPopupGroupName"),
				PropsUtil.getDataPropertyValue("GroupView_GroupPopupGroupAccount").split(","));
		Assert.assertEquals(
				groupView.AccountGroupTypeView_GrpNameHeader(PropsUtil.getDataPropertyValue("GroupView_GroupIndex"))
						.getText().trim(),
				PropsUtil.getDataPropertyValue("GroupView_GroupPopupGroupName"),
				"LOC accounts type header is not displayed");

	}

	@Test(description = "AT-97016,AT-97017,AT-97018,AT-97019,AT-97020,AT-97021:verify home equiety account name in Account Type view", groups = {
			"DesktopBrowsers,sanity" }, priority = 13, dependsOnMethods = "verifyAccountConainer_FI", enabled = true)
	public void verifyHELOCAccountName_GroupView() {
		Assert.assertEquals(
				groupView.AccountGroupTypeView_AccountName(PropsUtil.getDataPropertyValue("GroupView_GroupIndex"),
						PropsUtil.getDataPropertyValue("HELOC_GroupType_AccountIndex")).getText().trim(),
				PropsUtil.getDataPropertyValue("HELOC_GroupType_AccountName"),
				"home equiety account name is not displayed");
		Assert.assertEquals(
				groupView.AccountGroupTypeView_AccountSite(PropsUtil.getDataPropertyValue("GroupView_GroupIndex"),
						PropsUtil.getDataPropertyValue("HELOC_GroupType_AccountIndex")).getText().trim(),
				PropsUtil.getDataPropertyValue("HELOC_GroupType_AccountSiteName"),
				"home equiety account name is not displayed");
		if (appFlag.equals("app") || appFlag.equals("emulator")) {
			Assert.assertEquals(
					groupView.AccountGroupTypeView_AccountNumMobile(
							PropsUtil.getDataPropertyValue("GroupView_GroupIndex"),
							PropsUtil.getDataPropertyValue("HELOC_GroupType_AccountIndex")).getText().trim(),
					PropsUtil.getDataPropertyValue("HELOC_GroupType_AccountNumber"),
					"home equiety account name is not displayed");
		} else {
			Assert.assertEquals(
					groupView.returnAccountNumber(PropsUtil.getDataPropertyValue("GroupView_GroupIndex"),
							PropsUtil.getDataPropertyValue("HELOC_GroupType_AccountIndex")),
					PropsUtil.getDataPropertyValue("HELOC_GroupType_AccountNumber"),
					"home equiety account name is not displayed");
		}
		Assert.assertEquals(
				groupView.AccountGroupTypeView_AccountBal(PropsUtil.getDataPropertyValue("GroupView_GroupIndex"),
						PropsUtil.getDataPropertyValue("HELOC_GroupType_AccountIndex")).getText().trim(),
				PropsUtil.getDataPropertyValue("HELOC_GroupType_AccountBalance"),
				"home equiety account name is not displayed");
		Assert.assertEquals(
				groupView.AccountGroupTypeView_AccountNickName(PropsUtil.getDataPropertyValue("GroupView_GroupIndex"),
						PropsUtil.getDataPropertyValue("HELOC_GroupType_AccountIndex")).getText().trim(),
				PropsUtil.getDataPropertyValue("LOC_AccountFI_AccountLoanContainerAccountNickName"),
				"home equiety account name is not displayed");

	}

	@Test(description = "AT-97016,AT-97017,AT-97018,AT-97019,AT-97020,AT-97021:verify Line of credit account name in Account Type view", groups = {
			"DesktopBrowsers,sanity" }, priority = 14, dependsOnMethods = "verifyAccountConainer_FI", enabled = true)
	public void verifyLOCAccountName_GroupView() {
		Assert.assertEquals(
				groupView.AccountGroupTypeView_AccountName(PropsUtil.getDataPropertyValue("GroupView_GroupIndex"),
						PropsUtil.getDataPropertyValue("LOC_GroupType_AccountIndex")).getText().trim(),
				PropsUtil.getDataPropertyValue("LOC_GroupType_AccountName"),
				"home equiety account name is not displayed");
		Assert.assertEquals(
				groupView.AccountGroupTypeView_AccountSite(PropsUtil.getDataPropertyValue("GroupView_GroupIndex"),
						PropsUtil.getDataPropertyValue("LOC_GroupType_AccountIndex")).getText().trim(),
				PropsUtil.getDataPropertyValue("LOC_GroupType_AccountSiteName"),
				"home equiety account name is not displayed");
		if (appFlag.equals("app") || appFlag.equals("emulator")) {
			Assert.assertEquals(
					groupView.AccountGroupTypeView_AccountNumMobile(
							PropsUtil.getDataPropertyValue("GroupView_GroupIndex"),
							PropsUtil.getDataPropertyValue("LOC_GroupType_AccountIndex")).getText().trim(),
					PropsUtil.getDataPropertyValue("LOC_GroupType_AccountNumber"),
					"home equiety account name is not displayed");
		} else {
			Assert.assertEquals(
					groupView.returnAccountNumber(PropsUtil.getDataPropertyValue("GroupView_GroupIndex"),
							PropsUtil.getDataPropertyValue("LOC_GroupType_AccountIndex")),
					PropsUtil.getDataPropertyValue("LOC_GroupType_AccountNumber"),
					"home equiety account name is not displayed");
		}
		Assert.assertEquals(
				groupView.AccountGroupTypeView_AccountBal(PropsUtil.getDataPropertyValue("GroupView_GroupIndex"),
						PropsUtil.getDataPropertyValue("LOC_GroupType_AccountIndex")).getText().trim(),
				PropsUtil.getDataPropertyValue("LOC_GroupType_AccountBalance"),
				"home equiety account name is not displayed");
	}

	@Test(description = "AT-97016,AT-97017,AT-97018,AT-97019,AT-97020,AT-97021:verify Securities Backed account name in Account Type view", groups = {
			"DesktopBrowsers,sanity" }, priority = 15, dependsOnMethods = "verifyAccountConainer_FI", enabled = true)
	public void verifySBLOCAccountName_GroupView() {
		Assert.assertEquals(
				groupView.AccountGroupTypeView_AccountName(PropsUtil.getDataPropertyValue("GroupView_GroupIndex"),
						PropsUtil.getDataPropertyValue("SBLOC_GroupType_AccountIndex")).getText().trim(),
				PropsUtil.getDataPropertyValue("SBLOC_GroupType_AccountName"),
				"home equiety account name is not displayed");
		Assert.assertEquals(
				groupView.AccountGroupTypeView_AccountSite(PropsUtil.getDataPropertyValue("GroupView_GroupIndex"),
						PropsUtil.getDataPropertyValue("SBLOC_GroupType_AccountIndex")).getText().trim(),
				PropsUtil.getDataPropertyValue("SBLOC_GroupType_AccountSiteName"),
				"home equiety account name is not displayed");
		if (appFlag.equals("app") || appFlag.equals("emulator")) {
			Assert.assertEquals(
					groupView.AccountGroupTypeView_AccountNumMobile(
							PropsUtil.getDataPropertyValue("GroupView_GroupIndex"),
							PropsUtil.getDataPropertyValue("SBLOC_GroupType_AccountIndex")).getText().trim(),
					PropsUtil.getDataPropertyValue("SBLOC_GroupType_AccountNumber"),
					"home equiety account name is not displayed");
		} else {
			Assert.assertEquals(
					groupView.returnAccountNumber(PropsUtil.getDataPropertyValue("GroupView_GroupIndex"),
							PropsUtil.getDataPropertyValue("SBLOC_GroupType_AccountIndex")),
					PropsUtil.getDataPropertyValue("SBLOC_GroupType_AccountNumber"),
					"home equiety account name is not displayed");
		}
		Assert.assertEquals(
				groupView.AccountGroupTypeView_AccountBal(PropsUtil.getDataPropertyValue("GroupView_GroupIndex"),
						PropsUtil.getDataPropertyValue("SBLOC_GroupType_AccountIndex")).getText().trim(),
				PropsUtil.getDataPropertyValue("SBLOC_GroupType_AccountBalance"),
				"home equiety account name is not displayed");
	}

	@Test(description = "AT-96995:verify Securities Backed account name in Account Type view", groups = {
			"DesktopBrowsers,sanity" }, priority = 16, dependsOnMethods = "verifyAccountConainer_FI", enabled = true)
	public void verifyAccountType_DashBoard() {
		PageParser.forceNavigate("DashboardPage", d);
		SeleniumUtil.waitForPageToLoad();
		Assert.assertEquals(
				accountDashBoard.DashBoard_AccountType(PropsUtil.getDataPropertyValue("LOC_DashBoard_AccountContainer"))
						.getText().trim(),
				PropsUtil.getDataPropertyValue("LOC_DashBoard_AccountContainerName"),
				"Loan container type is not displayed in dashBoard");
	}

	@Test(description = "AT-96995,AT-96996,AT-96997,AT-96998,AT-96999,AT-97000:verify Securities Backed account name in Account Type view", groups = {
			"DesktopBrowsers,sanity" }, priority = 17, dependsOnMethods = "verifyAccountConainer_FI", enabled = true)
	public void verifyLOCAccountDetails_DashBoard() {
		if (appFlag.equals("app") || appFlag.equals("emulator")) {
			WebElement x = d.findElement(By.xpath("(//*[@id='expand-collapse-link'])[4]"));
			SeleniumUtil.click(x);
			SeleniumUtil.waitForPageToLoad();
		}
		util.assertExpectedActualList(
				util.getWebElementsValue(accountDashBoard
						.DashBoard_AccountSiteName(PropsUtil.getDataPropertyValue("LOC_DashBoard_AccountContainer"))),
				PropsUtil.getDataPropertyValue("LOC_DashBoard_AccountSiteName"),
				"Loc account type is not displayed in dashboard");
		util.assertExpectedActualList(
				util.getWebElementsValue(accountDashBoard
						.DashBoard_AccountName(PropsUtil.getDataPropertyValue("LOC_DashBoard_AccountContainer"))),
				PropsUtil.getDataPropertyValue("LOC_DashBoard_AccountName"),
				"He loc account type is not displayed in dashboard");
		util.assertExpectedActualAmountList(
				util.getWebElementsValue(accountDashBoard
						.DashBoard_AccountBal(PropsUtil.getDataPropertyValue("LOC_DashBoard_AccountContainer"))),
				PropsUtil.getDataPropertyValue("LOC_DashBoard_AccountBal"),
				"SbLoc account type is not displyed in dahboard");
	}

	@Test(description = "AT-97007:verify Securities Backed account name in Account Type view", groups = {
			"DesktopBrowsers,sanity" }, priority = 18, dependsOnMethods = "verifyAccountConainer_FI", enabled = true)
	public void verifyclosedAccountOFF_FI() {

		PageParser.forceNavigate("AccountSettings", d);
		SeleniumUtil.waitForPageToLoad();
		accountSetting.closeAccount(PropsUtil.getDataPropertyValue("LOC_Setting_AccountContainerInex"),
				PropsUtil.getDataPropertyValue("LOC_Setting_AccountAccountIndex").split(","));
		PageParser.forceNavigate("AccountsPage", d);
		SeleniumUtil.waitForPageToLoad();
		
		try {
			viewByFI.accountslayout().isDisplayed();
		}catch(Throwable e) {
			PageParser.forceNavigate("AccountsPage", d);
			SeleniumUtil.waitForPageToLoad(3000);
		}
		
		boolean loanAccount = true;
		int accountsize = viewByFI.AccountsContainerName().size();
		for (int i = 0; i < accountsize; i++) {
			if (viewByFI.AccountsContainerName().get(i).getText()
					.equals(PropsUtil.getDataPropertyValue("LOC_ClosedLoanAccountContainer"))) {
				loanAccount = true;
				break;
			} else {
				loanAccount = false;
			}
		}
		Assert.assertFalse(loanAccount, "closed account is displyed");
	}

	@Test(description = "AT-97007,AT-97013:verify Securities Backed account name in Account Type view", groups = {
			"DesktopBrowsers,sanity" }, priority = 19, dependsOnMethods = "verifyAccountConainer_FI", enabled = true)
	public void verifyclosedAccountOFF_TypeView() {

		SeleniumUtil.click(accountLanding.AccountsViewList()
				.get(Integer.parseInt(PropsUtil.getDataPropertyValue("LOC_AccountType_Index"))));
		SeleniumUtil.waitForPageToLoad();
		boolean loanAccount = true;
		int accountsize = accountTypeView.AccountTypeView_AccountsTypeNameList().size();
		for (int i = 0; i < accountsize; i++) {
			if (accountTypeView.AccountTypeView_AccountsTypeNameList().get(i).getText()
					.equals(PropsUtil.getDataPropertyValue("LOC_ClosedLoanAccountContainer"))) {
				loanAccount = true;
				break;
			} else {
				loanAccount = false;
			}
		}
		Assert.assertTrue(loanAccount, "closed accounts are not displayed");
	}

	@Test(description = "AT-97007:verify Securities Backed account name in Account Type view", groups = {
			"DesktopBrowsers,sanity" }, priority = 20, dependsOnMethods = "verifyAccountConainer_FI", enabled = true)
	public void verifyclosedAccountOFF_GroupView() {
		SeleniumUtil.click(groupView.groupType());
		SeleniumUtil.waitForPageToLoad();
		Assert.assertEquals(
				groupView.AccountGroupTypeView_GrpNameHeaderList().size(), Integer
						.parseInt(PropsUtil.getDataPropertyValue("LOC_ClosedLoanAccountContainerSize")),
				"closed account group is not displayed");
	}

	@Test(description = "AT-97002:verify Securities Backed account name in Account Type view", groups = {
			"DesktopBrowsers,sanity" }, priority = 21, dependsOnMethods = "verifyAccountConainer_FI", enabled = true)
	public void verifyclosedAccountOFF_DashBoard() {

		PageParser.forceNavigate("DashboardPage", d);
		SeleniumUtil.waitForPageToLoad();
		boolean loanAccount = true;
		int accountsize = accountDashBoard.DashBoard_AccountTypeList().size();
		for (int i = 0; i < accountsize; i++) {
			if (accountDashBoard.DashBoard_AccountTypeList().get(i).getText()
					.equals(PropsUtil.getDataPropertyValue("LOC_ClosedLoanAccountContainer"))) {
				loanAccount = true;
				break;
			} else {
				loanAccount = false;
			}
		}
		Assert.assertFalse(loanAccount, "closed account is displyed");
	}

	@Test(description = "AT-97006:verify Securities Backed account name in Account Type view", groups = {
			"DesktopBrowsers,sanity" }, priority = 22, dependsOnMethods = "verifyAccountConainer_FI", enabled = true)
	public void verifyLocAccountTypeHeader_FICloseAccOn() {

		PageParser.forceNavigate("AccountSettings", d);
		SeleniumUtil.waitForPageToLoad(8000);
		if (appFlag.equals("app") || appFlag.equals("emulator")) {
			SeleniumUtil.click(accountSetting.openAcntSettingMobile());
		}
		accountSetting.closeAccountON(PropsUtil.getDataPropertyValue("LOC_Setting_AccountContainerInex"),
				PropsUtil.getDataPropertyValue("LOC_Setting_AccountAccountIndex").split(","));
		SeleniumUtil.refresh(d);
		PageParser.forceNavigate("AccountsPage", d);
		SeleniumUtil.waitForPageToLoad();
		SeleniumUtil.waitFor(5);
		
		try {
			viewByFI.accountslayout().isDisplayed();
		}catch(Throwable e) {
			PageParser.forceNavigate("AccountsPage", d);
			SeleniumUtil.waitForPageToLoad(3000);
		}
		
		
		logger.info("verify all 3 Type LOC account Name in FI View");
		Assert.assertEquals(viewByFI
				.AccountFI_AccountsTypeName(PropsUtil.getDataPropertyValue("LOC_AccountFI_AccountLoanContainerIndex"))
				.getText().trim(), PropsUtil.getDataPropertyValue("LOC_AccountFI_AccountLoanContainerName"),
				"Loan container is not displayed");
	}

	@Test(description = "AT-97006:verify the Loan Accounts name(LOC) in FI View", groups = {
			"DesktopBrowsers,sanity" }, priority = 23, dependsOnMethods = "verifyLocAccountTypeHeader_FI", enabled = true)
	public void verifyLocAccountTypeName_FICloseAccOn() {
		SeleniumUtil.refresh(d);
		PageParser.forceNavigate("AccountsPage", d);
		SeleniumUtil.waitForPageToLoad();
		
		try {
			viewByFI.accountslayout().isDisplayed();
		}catch(Throwable e) {
			PageParser.forceNavigate("AccountsPage", d);
			SeleniumUtil.waitForPageToLoad(3000);
		}
		
		
		logger.info("1.verify all 3 Type LOC account Name");
		util.assertExpectedActualList(
				util.getWebElementsValue(viewByFI.AccountFI_AccountType_AccountName(
						PropsUtil.getDataPropertyValue("LOC_AccountFI_AccountLoanContainerIndex"))),
				PropsUtil.getDataPropertyValue("LOC_AccountFI_AccountLoanContainerAccountName"),
				"all 3 type LOC accounts name is not displayed");
	}

	@Test(description = "AT-97006:verify the Loan Accounts Number(LOC) in FI View", groups = {
			"DesktopBrowsers,sanity" }, priority = 24, dependsOnMethods = "verifyLocAccountTypeHeader_FI", enabled = true)
	public void verifyLocAccountTypeNum_FICloseAccOn() {
		logger.info("1.verify all 3 Type LOC account Number");
		if (appFlag.equals("app") || appFlag.equals("emulator")) {
			util.assertActualListIgnoreCase(
					util.getWebElementsValue(viewByFI.AccountFI_AccountType_AccountNumMobile(
							PropsUtil.getDataPropertyValue("LOC_AccountFI_AccountLoanContainerIndex"))),
					PropsUtil.getDataPropertyValue("Closed_LOC_AccountFI_AccountLoanContainerAccountNum").trim()
							.split(","),
					"all 3 type LOC accounts Number is not displayed");
		} else {
			util.assertActualListIgnoreCase(
					viewByFI.returnAccountNumbers(
							PropsUtil.getDataPropertyValue("LOC_AccountFI_AccountLoanContainerIndex")),
					PropsUtil.getDataPropertyValue("Closed_LOC_AccountFI_AccountLoanContainerAccountNum").trim()
							.split(","),
					"all 3 type LOC accounts Number is not displayed");
		}
	}

	@Test(description = "AT-97012:verify accounts type header in Account Type view", groups = {
			"DesktopBrowsers,sanity" }, priority = 25, dependsOnMethods = "verifyAccountConainer_FI", enabled = true)
	public void verifyLocAccountContainerHeader_TypeViewCloseAccOn() {
		SeleniumUtil.click(accountLanding.AccountsViewList()
				.get(Integer.parseInt(PropsUtil.getDataPropertyValue("LOC_AccountType_Index"))));
		SeleniumUtil.waitForPageToLoad();
		Assert.assertEquals(
				accountTypeView
						.AccountTypeView_AccountsTypeName(
								PropsUtil.getDataPropertyValue("LOC_AccountType__AccountContainerIndex"))
						.getText().trim(),
				PropsUtil.getDataPropertyValue("LOC_AccountType_AccountContainerName"),
				"LOC accounts type header is not displayed");

	}

	@Test(description = "AT-97012:verify home equiety account name in Account Type view", groups = {
			"DesktopBrowsers,sanity" }, priority = 26, dependsOnMethods = "verifyAccountConainer_FI", enabled = true)
	public void verifyHELOCAccountName_TypeViewCloseAccOn() {
		Assert.assertEquals(
				accountTypeView.AccountTypeView_AccountName(
						PropsUtil.getDataPropertyValue("LOC_AccountType__AccountContainerIndex"),
						PropsUtil.getDataPropertyValue("HELOC_AccountType_AccountIndex")).getText().trim(),
				PropsUtil.getDataPropertyValue("HELOC_AccountType_AccountName"),
				"home equiety account name is not displayed");
		Assert.assertEquals(
				accountTypeView.AccountTypeView_AccountSiteName(
						PropsUtil.getDataPropertyValue("LOC_AccountType__AccountContainerIndex"),
						PropsUtil.getDataPropertyValue("HELOC_AccountType_AccountIndex")).getText().trim(),
				PropsUtil.getDataPropertyValue("HELOC_AccountType_AccountSiteName"),
				"home equiety account name is not displayed");
		if (appFlag.equals("app") || appFlag.equals("emulator")) {
			Assert.assertEquals(
					accountTypeView.AccountTypeView_AccountNumMobile(
							PropsUtil.getDataPropertyValue("LOC_AccountType__AccountContainerIndex"),
							PropsUtil.getDataPropertyValue("HELOC_AccountType_AccountIndex")).getText().trim(),
					PropsUtil.getDataPropertyValue("Close_HELOC_AccountType_AccountNumber"),
					"home equiety account name is not displayed");
		} else {
			Assert.assertEquals(
					accountTypeView.returnAccountNumber(
							PropsUtil.getDataPropertyValue("LOC_AccountType__AccountContainerIndex"),
							PropsUtil.getDataPropertyValue("HELOC_AccountType_AccountIndex")),
					PropsUtil.getDataPropertyValue("Close_HELOC_AccountType_AccountNumber"),
					"home equiety account name is not displayed");
		}
		Assert.assertEquals(
				accountTypeView.AccountTypeView_AccountNickName(
						PropsUtil.getDataPropertyValue("LOC_AccountType__AccountContainerIndex"),
						PropsUtil.getDataPropertyValue("HELOC_AccountType_AccountIndex")).getText().trim(),
				PropsUtil.getDataPropertyValue("LOC_AccountFI_AccountLoanContainerAccountNickName"),
				"home equiety account name is not displayed");

	}

	@Test(description = "AT-97012:verify Line of credit account name in Account Type view", groups = {
			"DesktopBrowsers,sanity" }, priority = 27, dependsOnMethods = "verifyAccountConainer_FI", enabled = true)
	public void verifyLOCAccountName_TypeViewCloseAccOn() {
		Assert.assertEquals(
				accountTypeView.AccountTypeView_AccountName(
						PropsUtil.getDataPropertyValue("LOC_AccountType__AccountContainerIndex"),
						PropsUtil.getDataPropertyValue("LOC_AccountType_AccountIndex")).getText().trim(),
				PropsUtil.getDataPropertyValue("LOC_AccountType_AccountName"),
				"Line of credit account name is not displayed");
		Assert.assertEquals(
				accountTypeView.AccountTypeView_AccountSiteName(
						PropsUtil.getDataPropertyValue("LOC_AccountType__AccountContainerIndex"),
						PropsUtil.getDataPropertyValue("LOC_AccountType_AccountIndex")).getText().trim(),
				PropsUtil.getDataPropertyValue("LOC_AccountType_AccountSiteName"),
				"Line of credit account name is not displayed");
		if (appFlag.equals("app") || appFlag.equals("emulator")) {
			Assert.assertEquals(
					accountTypeView.AccountTypeView_AccountNumMobile(
							PropsUtil.getDataPropertyValue("LOC_AccountType__AccountContainerIndex"),
							PropsUtil.getDataPropertyValue("LOC_AccountType_AccountIndex")).getText().trim(),
					PropsUtil.getDataPropertyValue("Close_LOC_AccountType_AccountNumber"),
					"Line of credit account name is not displayed");
		} else {
			Assert.assertEquals(
					accountTypeView.returnAccountNumber(
							PropsUtil.getDataPropertyValue("LOC_AccountType__AccountContainerIndex"),
							PropsUtil.getDataPropertyValue("LOC_AccountType_AccountIndex")),
					PropsUtil.getDataPropertyValue("Close_LOC_AccountType_AccountNumber"),
					"Line of credit account name is not displayed");
		}
	}

	@Test(description = "AT-97012:verify Securities Backed account name in Account Type view", groups = {
			"DesktopBrowsers,sanity" }, priority = 28, dependsOnMethods = "verifyAccountConainer_FI", enabled = true)
	public void verifySBLOCAccountName_TypeViewCloseAccOn() {
		Assert.assertEquals(
				accountTypeView.AccountTypeView_AccountName(
						PropsUtil.getDataPropertyValue("LOC_AccountType__AccountContainerIndex"),
						PropsUtil.getDataPropertyValue("SBLOC_AccountType_AccountIndex")).getText().trim(),
				PropsUtil.getDataPropertyValue("SBLOC_AccountType_AccountName"),
				"Securities Backed account name is not displayed");
		Assert.assertEquals(
				accountTypeView.AccountTypeView_AccountSiteName(
						PropsUtil.getDataPropertyValue("LOC_AccountType__AccountContainerIndex"),
						PropsUtil.getDataPropertyValue("SBLOC_AccountType_AccountIndex")).getText().trim(),
				PropsUtil.getDataPropertyValue("SBLOC_AccountType_AccountSiteName"),
				"Securities Backed account name is not displayed");
		if (appFlag.equals("app") || appFlag.equals("emulator")) {
			Assert.assertEquals(
					accountTypeView.AccountTypeView_AccountNumMobile(
							PropsUtil.getDataPropertyValue("LOC_AccountType__AccountContainerIndex"),
							PropsUtil.getDataPropertyValue("SBLOC_AccountType_AccountIndex")).getText().trim(),
					PropsUtil.getDataPropertyValue("Close_SBLOC_AccountType_AccountNumber"),
					"Securities Backed account name is not displayed");
		} else {
			Assert.assertEquals(
					accountTypeView.returnAccountNumber(
							PropsUtil.getDataPropertyValue("LOC_AccountType__AccountContainerIndex"),
							PropsUtil.getDataPropertyValue("SBLOC_AccountType_AccountIndex")),
					PropsUtil.getDataPropertyValue("Close_SBLOC_AccountType_AccountNumber"),
					"Securities Backed account name is not displayed");
		}
	}

	@Test(description = "AT-97012:verify accounts type header in Account Type view", groups = {
			"DesktopBrowsers,sanity" }, priority = 30, dependsOnMethods = "verifyAccountConainer_FI", enabled = true)
	public void verifyLocAccountContainerHeader_GroupViewCloseAccOn() {
		SeleniumUtil.click(groupView.groupType());
		SeleniumUtil.waitForPageToLoad();
		Assert.assertEquals(
				groupView.AccountGroupTypeView_GrpNameHeader(PropsUtil.getDataPropertyValue("GroupView_GroupIndex"))
						.getText().trim(),
				PropsUtil.getDataPropertyValue("GroupView_GroupPopupGroupName"),
				"LOC accounts type header is not displayed");

	}

	@Test(description = "AT-97012:verify home equiety account name in Account Type view", groups = {
			"DesktopBrowsers,sanity" }, priority = 31, dependsOnMethods = "verifyAccountConainer_FI", enabled = true)
	public void verifyHELOCAccountName_GroupViewCloseAccOn() {
		Assert.assertEquals(
				groupView.AccountGroupTypeView_AccountName(PropsUtil.getDataPropertyValue("GroupView_GroupIndex"),
						PropsUtil.getDataPropertyValue("HELOC_GroupType_AccountIndex")).getText().trim(),
				PropsUtil.getDataPropertyValue("HELOC_GroupType_AccountName"),
				"home equiety account name is not displayed");
		Assert.assertEquals(
				groupView.AccountGroupTypeView_AccountSite(PropsUtil.getDataPropertyValue("GroupView_GroupIndex"),
						PropsUtil.getDataPropertyValue("HELOC_GroupType_AccountIndex")).getText().trim(),
				PropsUtil.getDataPropertyValue("HELOC_GroupType_AccountSiteName"),
				"home equiety account name is not displayed");
		if (appFlag.equals("app") || appFlag.equals("emulator")) {
			Assert.assertEquals(
					groupView.AccountGroupTypeView_AccountNumMobile(
							PropsUtil.getDataPropertyValue("GroupView_GroupIndex"),
							PropsUtil.getDataPropertyValue("HELOC_GroupType_AccountIndex")).getText().trim(),
					PropsUtil.getDataPropertyValue("Close_HELOC_GroupType_AccountNumber"),
					"home equiety account name is not displayed");
		} else {
			Assert.assertEquals(
					groupView.returnAccountNumber(PropsUtil.getDataPropertyValue("GroupView_GroupIndex"),
							PropsUtil.getDataPropertyValue("HELOC_GroupType_AccountIndex")),
					PropsUtil.getDataPropertyValue("Close_HELOC_GroupType_AccountNumber"),
					"home equiety account name is not displayed");
		}
		Assert.assertEquals(
				groupView.AccountGroupTypeView_AccountNickName(PropsUtil.getDataPropertyValue("GroupView_GroupIndex"),
						PropsUtil.getDataPropertyValue("HELOC_GroupType_AccountIndex")).getText().trim(),
				PropsUtil.getDataPropertyValue("LOC_AccountFI_AccountLoanContainerAccountNickName"),
				"home equiety account name is not displayed");

	}

	@Test(description = "AT-97012:verify Line of credit account name in Account Type view", groups = {
			"DesktopBrowsers,sanity" }, priority = 32, dependsOnMethods = "verifyAccountConainer_FI", enabled = true)
	public void verifyLOCAccountName_GroupViewCloseAccOn() {
		Assert.assertEquals(
				groupView.AccountGroupTypeView_AccountName(PropsUtil.getDataPropertyValue("GroupView_GroupIndex"),
						PropsUtil.getDataPropertyValue("LOC_GroupType_AccountIndex")).getText().trim(),
				PropsUtil.getDataPropertyValue("LOC_GroupType_AccountName"),
				"home equiety account name is not displayed");
		Assert.assertEquals(
				groupView.AccountGroupTypeView_AccountSite(PropsUtil.getDataPropertyValue("GroupView_GroupIndex"),
						PropsUtil.getDataPropertyValue("LOC_GroupType_AccountIndex")).getText().trim(),
				PropsUtil.getDataPropertyValue("LOC_GroupType_AccountSiteName"),
				"home equiety account name is not displayed");
		if (appFlag.equals("app") || appFlag.equals("emulator")) {
			Assert.assertEquals(
					groupView.AccountGroupTypeView_AccountNumMobile(
							PropsUtil.getDataPropertyValue("GroupView_GroupIndex"),
							PropsUtil.getDataPropertyValue("LOC_GroupType_AccountIndex")).getText().trim(),
					PropsUtil.getDataPropertyValue("Close_LOC_GroupType_AccountNumber"),
					"home equiety account name is not displayed");
		} else {
			Assert.assertEquals(
					groupView.returnAccountNumber(PropsUtil.getDataPropertyValue("GroupView_GroupIndex"),
							PropsUtil.getDataPropertyValue("LOC_GroupType_AccountIndex")),
					PropsUtil.getDataPropertyValue("Close_LOC_GroupType_AccountNumber"),
					"home equiety account name is not displayed");
		}

	}

	@Test(description = "AT-97012:verify Securities Backed account name in Account Type view", groups = {
			"DesktopBrowsers,sanity" }, priority = 33, dependsOnMethods = "verifyAccountConainer_FI", enabled = true)
	public void verifySBLOCAccountName_GroupViewCloseAccOn() {
		Assert.assertEquals(
				groupView.AccountGroupTypeView_AccountName(PropsUtil.getDataPropertyValue("GroupView_GroupIndex"),
						PropsUtil.getDataPropertyValue("SBLOC_GroupType_AccountIndex")).getText().trim(),
				PropsUtil.getDataPropertyValue("SBLOC_GroupType_AccountName"),
				"home equiety account name is not displayed");
		Assert.assertEquals(
				groupView.AccountGroupTypeView_AccountSite(PropsUtil.getDataPropertyValue("GroupView_GroupIndex"),
						PropsUtil.getDataPropertyValue("SBLOC_GroupType_AccountIndex")).getText().trim(),
				PropsUtil.getDataPropertyValue("SBLOC_GroupType_AccountSiteName"),
				"home equiety account name is not displayed");
		if (appFlag.equals("app") || appFlag.equals("emulator")) {
			Assert.assertEquals(
					groupView.AccountGroupTypeView_AccountNumMobile(
							PropsUtil.getDataPropertyValue("GroupView_GroupIndex"),
							PropsUtil.getDataPropertyValue("SBLOC_GroupType_AccountIndex")).getText().trim(),
					PropsUtil.getDataPropertyValue("Close_SBLOC_GroupType_AccountNumber"),
					"home equiety account name is not displayed");
		} else {
			Assert.assertEquals(
					groupView.returnAccountNumber(PropsUtil.getDataPropertyValue("GroupView_GroupIndex"),
							PropsUtil.getDataPropertyValue("SBLOC_GroupType_AccountIndex")),
					PropsUtil.getDataPropertyValue("Close_SBLOC_GroupType_AccountNumber"),
					"home equiety account name is not displayed");
		}
	}

	@Test(description = "AT-97001:verify Securities Backed account name in Account Type view", groups = {
			"DesktopBrowsers,sanity" }, priority = 34, dependsOnMethods = "verifyAccountConainer_FI", enabled = true)
	public void verifyDeleteAccount_FI() {

		PageParser.forceNavigate("AccountSettings", d);
		SeleniumUtil.waitForPageToLoad();
		if (appFlag.equals("app") || appFlag.equals("emulator")) {
			SeleniumUtil.click(accountSetting.openAcntSettingMobile());
		}
		deleetAccount.deleteAccount(PropsUtil.getDataPropertyValue("LOC_Setting_AccountContainerInex"),
				PropsUtil.getDataPropertyValue("LOC_Setting_AccountAccountIndex").split(","));
		SeleniumUtil.waitForPageToLoad();
		PageParser.forceNavigate("AccountsPage", d);
		SeleniumUtil.waitForPageToLoad();
		
		try {
			viewByFI.accountslayout().isDisplayed();
		}catch(Throwable e) {
			PageParser.forceNavigate("AccountsPage", d);
			SeleniumUtil.waitForPageToLoad(3000);
		}
		
		
		int accountsize = viewByFI.AccountsContainerName().size();
		boolean loanAccount = true;
		for (int i = 0; i < accountsize; i++) {
			if (viewByFI.AccountsContainerName().get(i).getText()
					.equals(PropsUtil.getDataPropertyValue("LOC_ClosedLoanAccountContainer"))) {
				loanAccount = true;
				break;
			} else {
				loanAccount = false;
			}
		}
		Assert.assertFalse(loanAccount);
	}

	@Test(description = "AT-97001:verify Securities Backed account name in Account Type view", groups = {
			"DesktopBrowsers,sanity" }, priority = 35, dependsOnMethods = "verifyAccountConainer_FI", enabled = true)
	public void verifyDeleteAccount_TypeView() {

		SeleniumUtil.click(accountLanding.AccountsViewList()
				.get(Integer.parseInt(PropsUtil.getDataPropertyValue("LOC_AccountType_Index"))));
		SeleniumUtil.waitForPageToLoad();
		boolean loanAccount = true;
		int accountsize = accountTypeView.AccountTypeView_AccountsTypeNameList().size();
		for (int i = 0; i < accountsize; i++) {
			if (accountTypeView.AccountTypeView_AccountsTypeNameList().get(i).getText()
					.equals(PropsUtil.getDataPropertyValue("LOC_ClosedLoanAccountContainer"))) {
				loanAccount = true;
				break;
			} else {
				loanAccount = false;
			}
		}
		Assert.assertFalse(loanAccount);
	}

	@Test(description = "AT-97001:verify Securities Backed account name in Account Type view", groups = {
			"DesktopBrowsers,sanity" }, priority = 36, dependsOnMethods = "verifyAccountConainer_FI", enabled = true)
	public void verifyDeleteAccount_GroupView() {

		SeleniumUtil.click(groupView.groupType());
		SeleniumUtil.waitForPageToLoad();
		Assert.assertEquals(
				groupView.AccountGroupTypeView_GrpNameHeaderList().size(),Integer
						.parseInt(PropsUtil.getDataPropertyValue("LOC_ClosedLoanAccountContainerSize")),
				"deleted Account is displayed");

	}

	@Test(description = "AT-97001:verify Securities Backed account name in Account Type view", groups = {
			"DesktopBrowsers,sanity" }, priority = 37, dependsOnMethods = "verifyAccountConainer_FI", enabled = true)
	public void verifyDeleteAccount_DashBoard() {

		PageParser.forceNavigate("DashboardPage", d);
		SeleniumUtil.waitForPageToLoad();
		int accountsize = accountDashBoard.DashBoard_AccountTypeList().size();
		boolean loanAccount = true;
		for (int i = 0; i < accountsize; i++) {
			if (accountDashBoard.DashBoard_AccountTypeList().get(i).getText()
					.equals(PropsUtil.getDataPropertyValue("LOC_ClosedLoanAccountContainer"))) {
				loanAccount = true;
				break;
			} else {
				loanAccount = false;
			}
		}
		Assert.assertFalse(loanAccount);

	}

	@Test(description = "AT-97022,AT-97023:verify LOC in Account setting ", groups = {
			"DesktopBrowsers,sanity" }, priority = 48, enabled = true)
	public void verifyLOCAccounts_AccountSetting() throws Exception {
		LoginPage.loginMain(d, loginParameter);
		accountAddition.linkAccount();
		accountAddition.addAggregatedAccount(PropsUtil.getDataPropertyValue("TxnDashBoard_LOC_Container"),
				PropsUtil.getDataPropertyValue("LOC_LinkAccountName_AccountSetting"),
				PropsUtil.getDataPropertyValue("LOC_LinkAccountPAssword_AccountSetting"));
		groupView.refreshPage();
		PageParser.forceNavigate("AccountSettings", d);
		SeleniumUtil.waitForPageToLoad();
		if (appFlag.equals("app") || appFlag.equals("emulator")) {
			SeleniumUtil.click(accountSetting.openAcntSettingMobile());
		}
		util.assertExpectedActualList(util.getWebElementsValue(globelAcctSetting.AccSettingFIName()),
				PropsUtil.getDataPropertyValue("AccountSetting_LOC_FIName"),
				"FI name is not displayed in Account setting");
		util.assertExpectedActualList(util.getWebElementsValue(globelAcctSetting.AccountsContainerAcntType()),
				PropsUtil.getDataPropertyValue("AccountSetting_LOC_ContainerName"), "container name is not displayed");
		util.assertExpectedActualList(util.getWebElementsValue(globelAcctSetting.AccSettingContainerAccountName()),
				PropsUtil.getDataPropertyValue("AccountSetting_LOC_AccountName"), "loan account name is not dispalyed");
		if (appFlag.equals("app") || appFlag.equals("emulator")) {
			util.assertExpectedActualList(
					util.getWebElementsValue(globelAcctSetting.AccSettingContainerAccountNumMobile()),
					PropsUtil.getDataPropertyValue("AccountSetting_LOC_AccountNum"),
					"loan account num is not dispalyed");
		} else {
			util.assertExpectedActualList(util.getWebElementsValue(globelAcctSetting.AccSettingContainerAccountNum()),
					PropsUtil.getDataPropertyValue("AccountSetting_LOC_AccountNum"),
					"loan account num is not dispalyed");
		}
		util.assertExpectedActualList(util.getWebElementsValue(globelAcctSetting.AccSettingFIEdit()),
				PropsUtil.getDataPropertyValue("AccountSetting_LOC_EditCredential"),
				"edit credantial is not displayed");
		util.assertExpectedActualList(util.getWebElementsValue(globelAcctSetting.AccSettingFIDelete()),
				PropsUtil.getDataPropertyValue("AccountSetting_LOC_DeleteSite"), "delete option si not displayed");
		util.assertExpectedActualList(util.getWebElementsValue(globelAcctSetting.AccSettingFIGoTOSite()),
				PropsUtil.getDataPropertyValue("AccountSetting_LOC_GoToSite"), "GotoSite is not displayed");
	}
}
