/**
* Copyright (c) 2018 Yodlee Inc. All Rights Reserved.
*
* This software is the confidential and proprietary information of
* Yodlee, Inc. Use is subject to license terms.
*
* @author sakshi Jain
*/
package com.omni.pfm.Accounts;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import com.accessibility.examples.testng.webdriver.test.RunAccessibilityTest;
import com.omni.pfm.PageProcessor.PageParser;
import com.omni.pfm.pages.AccountAddition.AccountAddition;
import com.omni.pfm.pages.Login.LoginPage;
import com.omni.pfm.testBase.TestBase;
import com.omni.pfm.utility.PropsUtil;
import com.omni.pfm.utility.SeleniumUtil;

public class Accounts_InvestmentAcntTypeClassification_Test extends TestBase {
	Logger logger = LoggerFactory.getLogger(Accounts_InvestmentAcntTypeClassification_Test.class);

	AccountsViewByGroup_Loc viewByGroup;
	AccountsViewByFI_Loc viewByFI;
	AccountsLandingPage_Loc acntLandingPage;
	AccountsViewByAcntType_Loc viewByAcntType;

	LoginPage login;
	AccountAddition accountAdd;

	@BeforeClass()
	public void init() throws Exception {
		doInitialization("Accounts");

		TestBase.tc = TestBase.extent.startTest("Login", "Login");
		TestBase.test.appendChild(TestBase.tc);
		login = new LoginPage();
		accountAdd = new AccountAddition();
		viewByGroup = new AccountsViewByGroup_Loc(d);
		viewByFI = new AccountsViewByFI_Loc(d);
		viewByAcntType = new AccountsViewByAcntType_Loc(d);
		acntLandingPage = new AccountsLandingPage_Loc(d);

	}

	@Test(description = "creating user and adding account.", groups = {
			"DesktopBrowsers,sanity" }, priority = 1, enabled = true)
	public void login() throws Exception {
		LoginPage.loginMain(d, loginParameter);
		SeleniumUtil.waitForPageToLoad();
		accountAdd.linkAccount();
		accountAdd.addAggregatedAccount("AcntTest2.site16441.2", "site16441.2");
		PageParser.forceNavigate("AccountsPage", d);
		SeleniumUtil.waitForPageToLoad();
	}

	@Test(description = "AT-84247,AT-84237: Link An Account Should be displayed", groups = {
			"DesktopBrowsers,sanity" }, dependsOnMethods = "login", priority = 2, enabled = true)
	public void verifyLinkAnAcntBtn() {
		SeleniumUtil.waitForPageToLoad();
		
		try {
			acntLandingPage.accountslayout().isDisplayed();
		}catch(Throwable e) {
			PageParser.forceNavigate("AccountsPage", d);
			SeleniumUtil.waitForPageToLoad(3000);
		}
		
		acntLandingPage.verifySideBarValues();
		if (acntLandingPage.isAccountsLinkAnAcntBtnMobile()) {
			Assert.assertTrue(acntLandingPage.isLinkAnAcntBtnMobileDisplayed());
		} else {
			Assert.assertTrue(acntLandingPage.isLinkAnAcntBtnDisplayed());
		}

	}

	@Test(description = "AT-84358,AT-84359: Investments type of accounts must be present in FI view.", groups = {
			"DesktopBrowsers,sanity" }, dependsOnMethods = "login", priority = 3, enabled = true)
	public void verifyInvstAcntFIView() {

		String actual = viewByFI.AccountsContainerAcntType().get(2).getText().toLowerCase().trim();
		String expected = PropsUtil.getDataPropertyValue("Investment_Text").toLowerCase().trim();
		Assert.assertEquals(actual, expected);
	}

	@Test(description = "AT-84487,AT-84488:Verify the more option available in Account Fi view should display 'Create Group', 'Feature Tour' and 'Settings options'", priority = 4, groups = {
			"Desktop" }, dependsOnMethods = "login", enabled = true)
	public void verifyOptionsUnderMoreInFIView() {
		acntLandingPage.clickOnMoreBtn();
		SeleniumUtil.waitForPageToLoad(3000);
		if (appFlag.equals("app") || appFlag.equals("emulator")) {
			Assert.assertTrue(acntLandingPage.verifyVisibleOptionsUnderMoreMobile("VisibleOptionsUnderMoreBtnMobile"));
		} else {
			Assert.assertTrue(acntLandingPage.verifyVisibleOptionsUnderMore("VisibleOptionsUnderMoreBtn"));
		}
		acntLandingPage.clickOnMoreBtn();
		SeleniumUtil.waitForPageToLoad();
	}

	@Test(description = "AT-84488:Verify the more option available in Account Type view should display 'Create Group', 'Feature Tour' and 'Settings options'", priority = 5, groups = {
			"Desktop" }, dependsOnMethods = "login", enabled = true)
	public void verifyOptionsUnderMoreInTypeView() {
		acntLandingPage.clickOnAcntTypeView();
		SeleniumUtil.waitForPageToLoad();

		acntLandingPage.clickOnMoreBtn();
		if (appFlag.equals("app") || appFlag.equals("emulator")) {
			Assert.assertTrue(acntLandingPage.verifyVisibleOptionsUnderMoreMobile("VisibleOptionsUnderMoreBtnMobile"));
		} else {
			Assert.assertTrue(acntLandingPage.verifyVisibleOptionsUnderMore("VisibleOptionsUnderMoreBtn"));
		}
		acntLandingPage.clickOnMoreBtn();
		SeleniumUtil.waitForPageToLoad();
	}

	@Test(description = "AT-84360,AT-84238: Investments type of accounts must be present in Account type view.", groups = {
			"DesktopBrowsers,sanity" }, dependsOnMethods = "login", priority = 6, enabled = true)
	public void verifyInvstAcntInTypeView() {

		SeleniumUtil.waitForPageToLoad(2000);
		String actual = viewByFI.AccountsContainerName().get(2).getText().toLowerCase().trim();
		String expected = PropsUtil.getDataPropertyValue("Investment_Text").toLowerCase().trim();
		Assert.assertEquals(actual, expected);

	}

	@Test(description = "AT-84361,AT-84471 :Under Investment container the Sub headers must be one of the mapped values i.e (Investment, Retirement, Charitable, Education, Other) .", groups = {
			"DesktopBrowsers,sanity" }, dependsOnMethods = "verifyInvstAcntInTypeView", priority = 7, enabled = true)
	public void verifySubHeadersUnderInvstAcnt() {

		// Verify that under Investment container the Sub headers should be present
		// i.e (Charitable, Education, Investment, Other, Retirement)in account type
		// view.

		Assert.assertTrue(viewByAcntType.verifyingInvSubHeader());
	}

	@Test(description = "AT-84367 :Verify that the below account types should come under 'Charitable' group : 'Charitable Remainder Trust','Charitable Lead Trust','Charitable Gift Account','Church Account'.", groups = {
			"Regression" }, priority = 8, dependsOnMethods = "verifyInvstAcntInTypeView", enabled = true)
	public void verifyCategoryUnderCharitable() {

		// verifying sub-accounts under charitable sub container in account type view.

		String charitableheader = PropsUtil.getDataPropertyValue("Charitablecategoryheader");
		String charitable = PropsUtil.getDataPropertyValue("Charitablecategory");
		viewByAcntType.VerifyCataegories("CharitableAcc", charitableheader, charitable);
	}

	@Test(description = "AT-84366 :Verify the sub Accounts of retirement should come under retirement sub container", groups = {
			"Regression" }, priority = 9, dependsOnMethods = "verifyInvstAcntInTypeView", enabled = true)
	public void verifySubRetirementsAccounts() {

		// Verifying sub accounts of retirement sub-container in account type view.

		String Retirementheader = PropsUtil.getDataPropertyValue("Retirementcategoryheader");
		String Retirement = PropsUtil.getDataPropertyValue("Retirementcategory");
		viewByAcntType.VerifyCataegories("RetirementAcc", Retirementheader, Retirement);

	}

	@Test(description = "AT-84368: Verify that the below account types should come under 'Education' group: 'Educational','Educational Savings Plan (529)','Registered Education Savings Plan (RESP)','Coverdell Education Savings Account (ESA)'.", groups = {
			"Regression" }, priority = 10, dependsOnMethods = "verifyInvstAcntInTypeView", enabled = true)
	public void verifySubEducationAccounts() {

		// Verifying sub-accounts under Educational sub container in account type view.

		String Educationheader = PropsUtil.getDataPropertyValue("Educationcategoryheader");
		String Education = PropsUtil.getDataPropertyValue("Educationcategory");

		viewByAcntType.VerifyCataegories("EducationAcc", Educationheader, Education);

	}

	@Test(description = "AT-84365: Verify the sub Accounts of Investment should come under Investment sub container", groups = {
			"Regression" }, priority = 11, dependsOnMethods = "verifyInvstAcntInTypeView", enabled = true)
	public void verifySubinvestmentAccounts() {

		// Verifying Sub accounts of Investment subcontainer in account type view.

		String Subinvheader = PropsUtil.getDataPropertyValue("Subinvheader");
		String Subinv = PropsUtil.getDataPropertyValue("Subinv");
		viewByAcntType.VerifyCataegories("InvestmentAcc", Subinvheader, Subinv);

	}

	@Test(description = "AT-84363:Under Investment container the Sub headers must be one of the mapped values i.e (Investment, Retirement, Charitable, Education, Other) in Group type view .", groups = {
			"DesktopBrowsers,sanity" }, dependsOnMethods = "login", priority = 12, enabled = true)
	public void createGroupAndVerifyInvSubContainer() {

		/*
		 * 
		 * Verify that under Investment container the Sub headers should be present i.e
		 * (Charitable, Education, Investment, Other, Retirement)in account type view.
		 * 
		 */
		acntLandingPage.clickOnAcntGroupView();
		SeleniumUtil.waitForPageToLoad(4000);
		viewByGroup.clickingOnCreateGroupBtn();
		SeleniumUtil.waitForPageToLoad(3000);
		viewByGroup.creatingGroupWithAllAcnts("InvestmentGroup");
		SeleniumUtil.waitForPageToLoad(3000);
		viewByGroup.clickingOnSubmitGroupBtn();
		SeleniumUtil.waitForPageToLoad(3000);
		Assert.assertTrue(viewByGroup.verifyingInvSubHeader());
	}

	@Test(description = "AT-84362:Investments type of accounts must be present in Group view.", groups = {
			"DesktopBrowsers,sanity" }, dependsOnMethods = "createGroupAndVerifyInvSubContainer", priority = 13, enabled = true)
	public void verifyInvstAcntInGroupView() {

		/*
		 * 
		 * Creating Account group and verifying investment container visibility under
		 * Account Group type
		 * 
		 */
		String actual = viewByFI.AccountsContainerAcntType().get(4).getText().toLowerCase().trim();
		String expected = PropsUtil.getDataPropertyValue("Investment_TextGroup").toLowerCase().trim();
		Assert.assertEquals(actual, expected);
	}

	@Test(description = "In Global Settings , in Account Groups view, Verify that  'Investments' container and account types  like  'Investment', 'Retirement', 'Education' , 'Charity' and 'Other' should not be repeated.", groups = {
			"Regression" }, priority = 14, dependsOnMethods = "createGroupAndVerifyInvSubContainer", enabled = true)
	public void verifyingInvContainerInGroupview() {

		/*
		 * Verifying sub containers should not get repeated in account group under
		 * global settings.
		 */

		PageParser.forceNavigate("AccountGroups", d);
		SeleniumUtil.waitForPageToLoad(2000);
		Assert.assertTrue(viewByGroup.verifyingInvSubHeaderAccntGroup());
	}

	@Test(description = "deleting group", groups = {
			"Regression" }, dependsOnMethods = "createGroupAndVerifyInvSubContainer", priority = 15, enabled = true)
	public void deleteGroup() {
		SeleniumUtil.click(viewByGroup.AccountGroupDeleteBtn().get(0));
		viewByGroup.clickingOnDeleteGroupDeleteBtn();
	}

	// Updated By MRQA -- This is specific cobrands which is not having settings so
	// need to delete group from acc page itself.
	@Test(description = "ACCT-01_15: deleting group", groups = {
			"Regression" }, dependsOnMethods = "createGroupAndVerifyInvSubContainer", priority = 16, enabled = true)
	public void deleteGroupfromaccounts() {

		PageParser.forceNavigate("AccountsPage", d);
		SeleniumUtil.waitForPageToLoad(3000);
		
		try {
			acntLandingPage.accountslayout().isDisplayed();
		}catch(Throwable e) {
			PageParser.forceNavigate("AccountsPage", d);
			SeleniumUtil.waitForPageToLoad(3000);
		}
		
		acntLandingPage.clickOnAcntGroupView();
		SeleniumUtil.click(viewByGroup.AccountGroupDeleteBtn().get(0));
		viewByGroup.clickingOnDeleteGroupDeleteBtn();
	}

	@AfterClass
	public void checkAcc() {
		try {
			RunAccessibilityTest rat = new RunAccessibilityTest(this.getClass().getName());
			rat.testAccessibility(d);
		} catch (Exception e) {

		}
	}
}
