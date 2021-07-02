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


public class InvestmentFormatChange extends TestBase{

	Logger logger=LoggerFactory.getLogger(InvestmentFormatChange.class);

	InvestmentFormatChange_Loc invstFormat;
	LoginPage login;
	AccountAddition accountAdd;
	Accounts_GroupValidation_Loc AccGrpValidation;
	@BeforeClass()
	public void init() throws Exception
	{
		doInitialization("Accounts");

		TestBase.tc = TestBase.extent.startTest("Login", "Login");
		TestBase.test.appendChild(TestBase.tc);
		login=new LoginPage();
		accountAdd=new AccountAddition();
		invstFormat=new InvestmentFormatChange_Loc(d);
		AccGrpValidation=new Accounts_GroupValidation_Loc(d);
	}
	@Test(description="ACCT-01_01: creating user and adding account.", groups = {
	"DesktopBrowsers,sanity" }, priority = 1, enabled = true)
	public void login() throws Exception
	{
		LoginPage.loginMain(d, loginParameter);
		SeleniumUtil.waitForPageToLoad();
		accountAdd.linkAccount();
		accountAdd.addAggregatedAccount("AcntTest2.site16441.2", "site16441.2");
		SeleniumUtil.waitForPageToLoad();
		PageParser.forceNavigate("AccountsPage", d);
		SeleniumUtil.waitForPageToLoad();	
	}

	@Test(description="AT-37890:ACCT-01_02: In FI view, Verify that 'Investments' container is available if any account type is coming under 'Investments'.", groups = {
	"DesktopBrowsers,sanity" },dependsOnMethods="login", priority = 2, enabled = true)
	public void verifyInvstAcntFIView() {

		String actual = invstFormat.investmentTxt().getText().toLowerCase().trim();
		String expected = PropsUtil.getDataPropertyValue("Investment_Text").toLowerCase().trim();
		Assert.assertEquals(actual, expected);	

	}

	@Test(description = "AT-37891:ACCT-01_03: In Account Type view, Verify that 'Investments' container is available if any account type is coming under 'Investments'.", groups = {
	"DesktopBrowsers,sanity" }, dependsOnMethods="login",priority = 3, enabled = true)
	public void verifyInvstAcnt_ATView() {


		SeleniumUtil.click(invstFormat.accountType());
		SeleniumUtil.waitForPageToLoad();
		String actual = invstFormat.investmentTxt().getText().toLowerCase().trim();
		String expected = PropsUtil.getDataPropertyValue("Investment_Text").toLowerCase().trim();

		Assert.assertEquals(actual, expected);
	}

	@Test(description = "AT-37892:ACCT-01_04 :In Account Type view, Verify that all investment accounts are displayed under one of the following account types 'Investment', 'Retirement', 'Education' , 'Charity' and 'Other' account types.", groups = {
	"DesktopBrowsers,sanity" }, dependsOnMethods="login",priority = 4, enabled = true)
	public void verifySubHeadersUnderInvstAcnt() {

		/*
		 * 
		 * Verify that under Investment container the Sub headers should be present 
		 * i.e (Charitable, Education, Investment, Other, Retirement)in account type view.
		 * 
		 */	


		SeleniumUtil.waitForPageToLoad();
		SeleniumUtil.click(invstFormat.accountType());
		SeleniumUtil.waitForPageToLoad();

		Assert.assertTrue(invstFormat.subHeader());
		//Verifying Investment header is displayed for account type view.
		Assert.assertTrue(invstFormat.getAcntText().isDisplayed());
	}

	@Test(description="AT-37893:ACCT-01_05: In Account Groups view, Verify that 'Investments' container is available if any account type is coming under 'Investments'.", groups= 
		{"DesktopBrowsers,sanity"},dependsOnMethods="login", priority = 5, enabled = true)
	public void createGroup() {

		/*
		 * 
		 * Creating Account group and verifying investment container visibility under Account Group type
		 * 
		 */

		SeleniumUtil.click(invstFormat.groupType());
		SeleniumUtil.waitForPageToLoad();
		SeleniumUtil.click(invstFormat.getCreateGroup());
		invstFormat.typeGroupName(PropsUtil.getDataPropertyValue("InvestmentGroupName").trim());
		SeleniumUtil.waitForPageToLoad();
		invstFormat.selectCheckBox();
		SeleniumUtil.click(invstFormat.createUpdateGroupBtm());

		invstFormat.verifyingInvestmentHeaderInAcntTypeView();
	}


	@Test(description ="AT-37893:ACCT-01_06 :In Account Groups view, Verify that all investments type accounts are grouped under 'Investments' container if any account is coming under 'Investment', 'Retirement', 'Education' , 'Charity' and 'Other' account types.", groups = {
	"DesktopBrowsers,sanity" }, priority = 6,dependsOnMethods="createGroup", enabled = true)
	public void verifyInvstAcnt() {

		SeleniumUtil.click(invstFormat.groupType());
		SeleniumUtil.waitForPageToLoad();
		String expectedOption[] = PropsUtil.getDataPropertyValue("ActualContainerListInGroupView").split(",");
		invstFormat.subHeaderGrpType(expectedOption);

		//Verifying Investment header is displayed for account group view.
		Assert.assertTrue(invstFormat.groupNameIsVisible());
	}

	@Test(description="ACCT-01_07: navigating to account type view under Accounts Page",groups= {
	"Regression" },priority=7,dependsOnMethods="login", enabled=true)
	public void navigatingAccountsTypeView() {


		SeleniumUtil.click(invstFormat.accountType());
		SeleniumUtil.waitForPageToLoad();

	}

	@Test(description="AT-37930:ACCT-01_08 :Verify that the below account types should come under 'Charitable' group : 'Charitable Remainder Trust','Charitable Lead Trust','Charitable Gift Account','Church Account'.",groups= {
	"Regression" }, priority = 8,dependsOnMethods="navigatingAccountsTypeView", enabled=true)
	public void verifyCategoryUnderCharitable() {

		/*
		 * verifying sub-accounts under charitable sub container in account type view.
		 */

		String charitableheader = PropsUtil.getDataPropertyValue("Charitablecategoryheader");
		String charitable = PropsUtil.getDataPropertyValue("Charitablecategory");
		invstFormat.VerifyCataegories("CharitableAcc",charitableheader,charitable);
	}

	@Test(description="AT-37926:ACCT-01_09 :Verify the sub Accounts of retirement should come under retirement sub container",groups={"Regression"}
	,priority=9,dependsOnMethods="navigatingAccountsTypeView", enabled=true)
	public void verifySubRetirementsAccounts(){

		/*
		 * Verifying sub accounts of retirement sub-container in account type view.
		 */

		String Retirementheader = PropsUtil.getDataPropertyValue("Retirementcategoryheader");
		String Retirement = PropsUtil.getDataPropertyValue("Retirementcategory");
		invstFormat.VerifyCataegories("RetirementAcc",Retirementheader,Retirement);

	}

	@Test(description="AT-37927:ACCT-01_10: Verify that the below account types should come under 'Education' group: 'Educational','Educational Savings Plan (529)','Registered Education Savings Plan (RESP)','Coverdell Education Savings Account (ESA)'.",groups={"Regression"}
	,priority=10,dependsOnMethods="navigatingAccountsTypeView", enabled=true)
	public void verifySubEducationAccounts(){

		/*
		 * Verifying sub-accounts under Educational sub container in account type view. 
		 */

		String Educationheader = PropsUtil.getDataPropertyValue("Educationcategoryheader");
		String Education = PropsUtil.getDataPropertyValue("Educationcategory");

		invstFormat.VerifyCataegories("EducationAcc",Educationheader,Education);

	}

	@Test(description="AT-37925:ACCT-01_11: Verify the sub Accounts of Investment should come under Investment sub container",groups={"Regression"}
	,priority=11 ,dependsOnMethods="navigatingAccountsTypeView", enabled=true)
	public void verifySubinvestmentAccounts(){

		/*
		 * Verifying Sub accounts of Investment subcontainer in account type view.
		 */

		String Subinvheader = PropsUtil.getDataPropertyValue("Subinvheader");
		String Subinv = PropsUtil.getDataPropertyValue("Subinv");
		invstFormat.VerifyCataegories("InvestmentAcc",Subinvheader,Subinv);

	}

	@Test(description="AT-37895:ACCT-01_12: In Account Type view, Verify that 'Investments' container is not repeated.",groups={"Regression"}
	,priority=12, dependsOnMethods="navigatingAccountsTypeView", enabled=true)
	public void verifyingContainerList() {

		/*
		 * Verifying Investment Container is not repeated in account Type view.
		 */
		navigatingAccountsTypeView();
		invstFormat.verifyContainerList();
	}

	@Test(description="AT-37897:ACCT-01_13: In FI View, Verify that 'Investments' container is not repeated.",groups={"Regression"}
	,priority=13,dependsOnMethods="login", enabled=true)
	public void verifyingContainerListInFIview() {

		/*
		 * Verifying Investment Container is not repeated in financial institution view
		 */

		String expectedList[] = PropsUtil.getDataPropertyValue("ExpectedContainerInFIView").split(",");

		SeleniumUtil.click(invstFormat.FIView());
		SeleniumUtil.waitForPageToLoad();
		invstFormat.verifyContainerListInFI(expectedList);
	}

	@Test(description="AT-38918:ACCT-01_14: In Global Settings , in Account Groups view, Verify that  'Investments' container and account types  like  'Investment', 'Retirement', 'Education' , 'Charity' and 'Other' should not be repeated.",groups={"Regression"}
	,priority=14,dependsOnMethods="createGroup", enabled=true)
	public void verifyingContainerListInGroupview() {

		/*
		 * Verifying sub containers should not get repeated in account group under global settings.
		 */

		PageParser.forceNavigate("AccountGroups", d);
		SeleniumUtil.waitForPageToLoad(4000);
		String expectedList[] = PropsUtil.getDataPropertyValue("ActualContainerListInGroupView").split(",");
		invstFormat.verifyContainerInAcntGroup(expectedList);

	}

	@Test(description="ACCT-01_15: deleting group",groups={"Regression"},dependsOnMethods="createGroup",priority=15,enabled=true)
	public void deleteGroup() {
		SeleniumUtil.click(invstFormat.deleteIcon());
		SeleniumUtil.waitForElement(null, 500);
		SeleniumUtil.click(invstFormat.deleteBtn());
	}

	//Updated By MRQA -- This is specific cobrands which is not having settings so need to delete group from acc page itself.
	@Test(description="ACCT-01_15: deleting group",groups={"Regression"},dependsOnMethods="createGroup",priority=16,enabled=true)
	public void deleteGroupfromaccounts() {

		SeleniumUtil.click(AccGrpValidation.viewByGroupTab());
		SeleniumUtil.click(invstFormat.deleteIcon());
		SeleniumUtil.waitForElement(null, 500);
		SeleniumUtil.click(invstFormat.deleteBtn());
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
