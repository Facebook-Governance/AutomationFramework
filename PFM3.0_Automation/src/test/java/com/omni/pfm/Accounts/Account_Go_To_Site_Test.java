/**
* Copyright (c) 2018 Yodlee Inc. All Rights Reserved.
*
* This software is the confidential and proprietary information of
* Yodlee, Inc. Use is subject to license terms.
*
* @author sakshi Jain
*/

package com.omni.pfm.Accounts;

import java.awt.AWTException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.accessibility.examples.testng.webdriver.test.RunAccessibilityTest;
import com.omni.pfm.AccountAddition.YCOM_Regression_AccAndGroupCreation;
import com.omni.pfm.PageProcessor.PageParser;
import com.omni.pfm.pages.AccountAddition.AccountAddition;
import com.omni.pfm.pages.Login.LoginPage;
import com.omni.pfm.testBase.TestBase;
import com.omni.pfm.utility.PropsUtil;
import com.omni.pfm.utility.SeleniumUtil;

public class Account_Go_To_Site_Test extends TestBase{

	Logger logger=LoggerFactory.getLogger(Account_Go_To_Site_Test.class);

	Account_Go_To_Site_Loc accountsGoToSite;
	AccountsViewByGroup_Loc viewByGroup;
	AccountAddition accountAddition;
	
	
	@BeforeClass()
	public void init() throws AWTException, InterruptedException
	{
		doInitialization("Accounts");

		TestBase.tc = TestBase.extent.startTest("Login", "Login");
		TestBase.test.appendChild(TestBase.tc);

		accountsGoToSite = new Account_Go_To_Site_Loc(d);
		accountAddition=new AccountAddition();
		viewByGroup=new AccountsViewByGroup_Loc(d);
	}
	
	@Test(description="creating user and adding account.", groups = {
	"DesktopBrowsers,sanity" }, priority = 1, enabled = true)
	public void login() throws Exception
	{
		
		SeleniumUtil.waitForPageToLoad(2000);
		d.navigate().refresh();
		SeleniumUtil.waitForPageToLoad(2000);
		PageParser.forceNavigate("AccountsPage", d);	
		SeleniumUtil.waitForPageToLoad(7000);	
	}
	@Test(description = "AT-38605:Verify Go to Site Tab in cash Container is available in Account Settings level", groups = { "Regression" },
			priority = 2,dependsOnMethods="login",enabled = true)
	public void verifyGoToSiteInCashContainer() throws Exception {
		SeleniumUtil.waitForPageToLoad(6000);
		
		try {
			accountsGoToSite.accountslayout().isDisplayed();
		}catch(Throwable e) {
			PageParser.forceNavigate("AccountsPage", d);
			SeleniumUtil.waitForPageToLoad(3000);
		}
		
		boolean cashContainerGoToSiteVisibility  =SeleniumUtil.selectDropDownVisibility(d, "TESTDATA","Go to Site");
		Assert.assertTrue(cashContainerGoToSiteVisibility,"Go To site is not visible in cash container");
	}
	
	@Test(description = "AT-38605:Verify Go to Site Tab in card Container is available in Account Settings level", groups = { "Regression" },
			priority = 3,dependsOnMethods="login",enabled = true)
	public void verifyGoToSiteInCardContainer() throws Exception {
		SeleniumUtil.waitForPageToLoad(6000);
		boolean creditContainerGoToSiteVisibility =SeleniumUtil.selectDropDownVisibility(d, "Super CD Plus","Go to Site");
		Assert.assertTrue(creditContainerGoToSiteVisibility,"Go To site is not visible in credit container");
	}

	@Test(description = "AT-38605:Verify Go to Site Tab in investment Container is available in Account Settings level", groups = { "Regression" },
			priority = 4,dependsOnMethods="login",enabled = true)
	public void verifyGoToSiteIninvestmentContainer() throws Exception {
		SeleniumUtil.waitForPageToLoad(6000);
		boolean investmentContainerGoToSiteVisibility = SeleniumUtil.selectDropDownVisibility(d, "Investment Account 401k","Go to Site");
		Assert.assertTrue(investmentContainerGoToSiteVisibility,"Go To site is not visible in investment container");
	}
	@Test(description = "AT-38605:Verify Go to Site Tab in loan Container is available in Account Settings level", groups = { "Regression" },
			priority = 5,dependsOnMethods="login",enabled = true)
	public void verifyGoToSiteInloanContainer() throws Exception {
		SeleniumUtil.waitForPageToLoad(6000);
		boolean loanContainerGoToSiteVisibility = SeleniumUtil.selectDropDownVisibility(d, "LINE OF CREDIT","Go to Site");
		Assert.assertTrue(loanContainerGoToSiteVisibility,"Go To site is not visible in loan container");
	}
	@Test(description = "AT-38605:Verify Go to Site Tab in insurance Container is available in Account Settings level", groups = { "Regression" },
			priority = 6,dependsOnMethods="login",enabled = true)
	public void verifyGoToSiteInInsuranceContainer() throws Exception {
		SeleniumUtil.waitForPageToLoad(6000);
		boolean InsuranceContainerGoToSiteVisibility = SeleniumUtil.selectDropDownVisibility(d, "DAG INSURANCE","Go to Site");
		Assert.assertTrue(InsuranceContainerGoToSiteVisibility,"Go To site is not visible in insurance container");
	}
	@Test(description = "AT-38605:Verify Go to Site Tab in bill Container is available in Account Settings level", groups = { "Regression" },
			priority = 7,dependsOnMethods="login",enabled = true)
	public void verifyGoToSiteInBillContainer() throws Exception {
		SeleniumUtil.waitForPageToLoad(6000);
		boolean billContainerGoToSiteVisibility = SeleniumUtil.selectDropDownVisibility(d, "DAG BILLING","Go to Site");
		Assert.assertTrue(billContainerGoToSiteVisibility,"Go To site is not visible in bill container");
	}

	@Test(description = "AT-38605:Verify Go to Site Tab in rewards is available in Account Settings level", groups = { "Regression" }, 
			priority = 8,dependsOnMethods="login",enabled = true)
	public void verifyGoToInRewardsSite() throws Exception {
		boolean rewardsContainerGoToSiteVisibility = SeleniumUtil.selectDropDownVisibility(d, "YODLEE", "Go to Site");
		Assert.assertTrue(rewardsContainerGoToSiteVisibility,"Go To site is not visible in rewards container");
	}

	
	@Test(description="Adding manual account and real estate account", groups = { "Regression" },
			priority = 9,dependsOnMethods="login",enabled = true)
	public void addingManualAcnt() throws InterruptedException, AWTException {
		accountAddition.addManualAccount("Cash","MyAccountbank","null", "10001", "12345","null","null","null");
		SeleniumUtil.waitForPageToLoad(2000);	
	}
	
	@Test(description="Adding manual account and real estate account", groups = { "Regression" },
			priority = 10,dependsOnMethods="login",enabled = true)
	public void addingrealestateAcnt() throws InterruptedException, AWTException {
		accountAddition.addManualRealEstateAccount("RealEstateManual", "2000000", true);
		PageParser.forceNavigate("AccountsPage", d);	
		SeleniumUtil.waitForPageToLoad(6000);
	}

	@Test(description = "AT-39643:Verify GoToSite tab is not present in manual account and real estate account", groups = { "Regression" }, 
			priority = 11,dependsOnMethods="addingManualAcnt", enabled = true)

	/*
	 * go to site option should not be available for manual and real estate accounts.
	 */

	public void VerifyManualAndRealEstateAccount() throws Exception {
		
		try {
			accountsGoToSite.accountslayout().isDisplayed();
		}catch(Throwable e) {
			PageParser.forceNavigate("AccountsPage", d);
			SeleniumUtil.waitForPageToLoad(3000);
		}
		
		boolean manualContainerGoToSiteVisibility = SeleniumUtil.selectDropDownVisibility(d, "MyAccountbank","Go to Site");
		boolean realEstateContainerGoToSiteVisibility = SeleniumUtil.selectDropDownVisibility(d, "RealEstateManual","Go to Site");

		Assert.assertFalse(manualContainerGoToSiteVisibility,"Go To site is not visible in manual account container");
		Assert.assertFalse(realEstateContainerGoToSiteVisibility,"Go To site is not visible in real estate account container");

	}

	@Test(description = "AT-38608, AT-39639: Verify new window is opening after clicking on GOTo Site Tab", groups = { "Regression" }, 
			priority = 12,dependsOnMethods="login", enabled = true)

	/*
	 * New window should open after clicking on go to site tab at accounts level.
	 */

	public void verifyNewWindow() throws Exception {
		SeleniumUtil.waitForPageToLoad(2000);
		SeleniumUtil.selectDropDown(d, "TESTDATA","Go to Site");
		SeleniumUtil.waitForPageToLoad(2000);
		accountsGoToSite.verifyPopUpWindow();
	}

	@Test(description="AT-38606,AT-76069:Verify that Go to Site option should be available after the delete site option", groups= {"Regression"},
			priority=13,dependsOnMethods="login",enabled=true)
	public void verifyPositionOfGoToSite() {
		PageParser.forceNavigate("AccountSettings", d);
		SeleniumUtil.waitForPageToLoad(2000);

		/*
		 * verifying Icon indexing at site level
		 */
		if (appFlag.equals("app") || appFlag.equals("emulator")) {
			SeleniumUtil.click(accountsGoToSite.dagsiteSettings());
		}
		accountsGoToSite.verifyingListIndexing();	
	}

	@Test(description = "AT-38605:Verify Go to site Tab is present at site level", groups = { "Regression" }, 
			priority = 14,dependsOnMethods="verifyPositionOfGoToSite", enabled = true)
	public void verifyAtSiteLevel() throws Exception {

		/*
		 * Verifying go to site window at site level in account setting page.
		 */
		boolean goToSiteVisibility = accountsGoToSite.checkVisibility(accountsGoToSite.goToSiteTab());
		SeleniumUtil.click(accountsGoToSite.goToSiteTab());
		SeleniumUtil.waitForPageToLoad();
		accountsGoToSite.verifyPopUpWindowAtSiteLevel();
		Assert.assertTrue(goToSiteVisibility,"Go To Site is not visible at Global account settings");
	}



	@Test(description = "AT-39636,AT-39641:create group and Verify that Go to Siteoption is available under  more option in Account Group view.", groups = { "Regression" },
			priority = 15,dependsOnMethods="login", enabled = true)
	public void veifyAtAccountGroupLevel() throws Exception {

		/*r
		 *VERIFYING go to site window in account group view. 
		 */
		PageParser.forceNavigate("AccountsPage", d);
		SeleniumUtil.waitForElement(null, 1500);
		
		try {
			accountsGoToSite.accountslayout().isDisplayed();
		}catch(Throwable e) {
			PageParser.forceNavigate("AccountsPage", d);
			SeleniumUtil.waitForPageToLoad(3000);
		}
		
		SeleniumUtil.click(accountsGoToSite.groupType());	
		SeleniumUtil.waitForPageToLoad(4000);
		viewByGroup.clickingOnCreateGroupBtn();
		viewByGroup.creatingGroupWithAllAcnts(PropsUtil.getDataPropertyValue("GroupName").trim());
		viewByGroup.clickingOnSubmitGroupBtn();
		SeleniumUtil.waitForPageToLoad(3000);
		accountsGoToSite.VerifyingGroupType();
	}

	@Test(description = "AT-39635,AT-39640:Verify Go To Site in Account Type View", groups = { "Regression" },
			priority = 16, dependsOnMethods="login",enabled = true)
	public void verifyAccTypeView() throws Exception {

		/*
		 * Verifying go to site window in account type view.
		 */
		SeleniumUtil.click(accountsGoToSite.accountType());
		SeleniumUtil.waitForPageToLoad();
		accountsGoToSite.VerifyingAccountTypeView();
	}

	@Test(description="AT-38721:Verify For the Loan Container it should display the principal amount in the account details.", groups = { "Regression" },
			priority = 17,dependsOnMethods="login", enabled = true)
	// userStory:B-07425
	public void verifyLoanContainer() throws Exception{
		PageParser.forceNavigate("AccountsPage", d);
		SeleniumUtil.waitForElement(null, 1500);

		try {
			accountsGoToSite.accountslayout().isDisplayed();
		}catch(Throwable e) {
			PageParser.forceNavigate("AccountsPage", d);
			SeleniumUtil.waitForPageToLoad(3000);
		}
		
		SeleniumUtil.click(accountsGoToSite.loanAccount());
		SeleniumUtil.waitForPageToLoad();
		boolean status=accountsGoToSite.loanAccountHeader().isDisplayed();		
		

		if(accountsGoToSite.isexpandCollapseArrow_Mob()) {
			SeleniumUtil.click(accountsGoToSite.expandCollapseArrow_Mob());
		}else {
			SeleniumUtil.click(accountsGoToSite.expandCollapseArrow());
		}
		String actualText=accountsGoToSite.principleAmountTxt().getText().trim();
		String expectedText=PropsUtil.getDataPropertyValue("GetText").trim();
		Assert.assertEquals(actualText, expectedText);
		Assert.assertTrue(status);

	}

	@Test(description="AT-38722:Verify For the Insurance Cotainer it should display the premium amount in the account details", groups = { "Regression" },
			priority = 18, dependsOnMethods="login",enabled = true)
	// userStory:B-07425
	public void verifyInsuranceContainer() throws Exception{
		PageParser.forceNavigate("AccountsPage", d);
		SeleniumUtil.waitForElement(null, 1500);

		try {
			accountsGoToSite.accountslayout().isDisplayed();
		}catch(Throwable e) {
			PageParser.forceNavigate("AccountsPage", d);
			SeleniumUtil.waitForPageToLoad(3000);
		}
		
		SeleniumUtil.click(accountsGoToSite.InsuranceAccount());		
		if(accountsGoToSite.isexpandCollapseArrow_Mob()) {
			SeleniumUtil.click(accountsGoToSite.expandCollapseArrow_Mob());
		}else {
			SeleniumUtil.click(accountsGoToSite.expandCollapseArrow());
		}

		String actualText=accountsGoToSite.premiumAmount().getText().trim();
		String expectedText=PropsUtil.getDataPropertyValue("GetText1").trim();
		Assert.assertEquals(actualText,expectedText);

	}

	@Test(description="AT-29031:Verify Header container and sub container Section should be in Title case at Account Setting level.", groups = { "Regression" },
			priority = 19,dependsOnMethods="login", enabled = true)
	//B-07022
	public void verifyHeaders() throws Exception{
		PageParser.forceNavigate("AccountSettings", d);
		SeleniumUtil.waitForElement(null, 1500);
		accountsGoToSite.verifyContainersInAcntSetting();
	}


	@Test(description="AT-29034:Verify Header container and sub container Section should be in Title case at Alert Setting level.", 
			priority = 20, dependsOnMethods="login",enabled = true)
	public void verifyHeadersInAlertSetting() throws Exception{

		PageParser.forceNavigate("AlertSetting", d);
		SeleniumUtil.waitForPageToLoad();
		accountsGoToSite.verifyContainerInAlertSetting();

	}

	@Test(description="AT-29028:Verify Header container and sub container Section should be in Title case In FIView",
			priority = 21,dependsOnMethods="login", enabled = true)
	//:B-07022
	public void verifyContainersInFI(){
		PageParser.forceNavigate("AccountsPage", d);
		SeleniumUtil.waitForElement(null, 1500);

		try {
			accountsGoToSite.accountslayout().isDisplayed();
		}catch(Throwable e) {
			PageParser.forceNavigate("AccountsPage", d);
			SeleniumUtil.waitForPageToLoad(3000);
		}
		
		accountsGoToSite.verifyContainerInFIView();
	}

	@Test(description="AT-29029:Verify Header container and sub container Section should be in Title case In AccountTypeView", groups = { "Regression" }, 
			priority = 22, dependsOnMethods="verifyContainersInFI", enabled = true)
	public void verifyContainersInAccType(){

		SeleniumUtil.click(accountsGoToSite.accountType());
		SeleniumUtil.waitForElement(null, 1000);

		accountsGoToSite.verifyContainerInAcntTypeView();
	}
	
	@Test(description="AT-29030: Verify Header container and sub container Section should be in Title case In Account Group View", 
			priority = 23,dependsOnMethods="login", enabled = true)

	public void verifyContainersInGroupType(){
		
		SeleniumUtil.click(accountsGoToSite.groupType());
		SeleniumUtil.waitForPageToLoad(3000);
		accountsGoToSite.verifyContainerInAcntGroupView();
	}	

	@Test(description="AT-29033: Verify Header container and sub container Section should be in Title case at Accounts Group level" , groups = { "Regression" },
			priority = 24, enabled = true)

	public void verifyInAccGroupLevel(){

		PageParser.forceNavigate("AccountsGroups", d);
		SeleniumUtil.waitForPageToLoad();
		accountsGoToSite.verifyContainerInAcntGroupLevel();
	}
	
	@Test(description="deleting group",priority=27,enabled=true)
	public void deleteGroup2() {

		SeleniumUtil.click(accountsGoToSite.deleteIcon());
		SeleniumUtil.waitForElement(null, 500);
		SeleniumUtil.click(accountsGoToSite.deleteBtn());
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
