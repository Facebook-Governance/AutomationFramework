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
import java.util.Arrays;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.omni.pfm.PageProcessor.PageParser;
import com.omni.pfm.pages.AccountAddition.AccountAddition;
import com.omni.pfm.pages.BudgetEnhancement.Budget_No_Account_Loc;
import com.omni.pfm.pages.BudgetEnhancement.Budget_Select_Accounts_Loc;
import com.omni.pfm.pages.CashFlow.CashFlow_LandingPage_Loc;
import com.omni.pfm.pages.Expense_IncomeAnalysis.Expense_Income_Trend_Loc;
import com.omni.pfm.pages.InvestmentHoldings.InvestmentHoldings_Loc;
import com.omni.pfm.pages.Login.LoginPage;
import com.omni.pfm.pages.Networth.NetWorth_Loc;
import com.omni.pfm.pages.TransactionEnhancement1.Transaction_AccountDropDown_Loc;
import com.omni.pfm.testBase.TestBase;
import com.omni.pfm.utility.PropsUtil;
import com.omni.pfm.utility.SeleniumUtil;

public class AccountsGroupIntegration_Test extends TestBase {

	Logger logger = LoggerFactory.getLogger(Accounts_GroupValidation_Test.class);
	AccountsViewByGroup_Loc viewByGroup;
	AccountsLandingPage_Loc acntLandingPage;
	Expense_Income_Trend_Loc accountDropDown;
	Accounts_ViewByGroup_Loc createGroupMethod;
	InvestmentHoldings_Loc investmentHoldings;
	Budget_No_Account_Loc budgetNoActScreen;
	Budget_Select_Accounts_Loc selectAccountScreen;
	CashFlow_LandingPage_Loc cashFlowLoc;
	NetWorth_Loc netWorth;
	Transaction_AccountDropDown_Loc trnxAcntFilter;
	AccountAddition accountAdd;

	@BeforeClass()
	public void init() throws AWTException, InterruptedException {
		doInitialization("Accounts");
		TestBase.tc = TestBase.extent.startTest("Login", "Login");
		TestBase.test.appendChild(TestBase.tc);
		viewByGroup = new AccountsViewByGroup_Loc(d);
		acntLandingPage = new AccountsLandingPage_Loc(d);
		accountDropDown = new Expense_Income_Trend_Loc(d);
		createGroupMethod = new Accounts_ViewByGroup_Loc(d);
		investmentHoldings = new InvestmentHoldings_Loc(d);
		budgetNoActScreen = new Budget_No_Account_Loc(d);
		selectAccountScreen = new Budget_Select_Accounts_Loc(d);
		cashFlowLoc = new CashFlow_LandingPage_Loc(d);
		netWorth = new NetWorth_Loc(d);
		trnxAcntFilter = new Transaction_AccountDropDown_Loc(d);
		accountAdd = new AccountAddition();
	}

	@Test(description = "login to the user", groups = { "DesktopBrowsers,sanity" }, priority = 1, enabled = true)
	public void login() throws Exception {
		LoginPage.loginMain(d, loginParameter);
		accountAdd.linkAccount();
		accountAdd.addAggregatedAccount(PropsUtil.getDataPropertyValue("AccountsDagSite").trim(),
				PropsUtil.getDataPropertyValue("AccountsDagSitePassword").trim());
		PageParser.forceNavigate("AccountsPage", d);
	}

	@Test(description = "AT-97111,AT-97112,AT-97114:verify group name should not allow special charcter to be entered", groups = {
			"Desktop", "Smoke" }, dependsOnMethods = "login", priority = 2, enabled = true)

	public void verifySpecialCharInGroupName() throws InterruptedException {
		
		try {
			acntLandingPage.accountslayout().isDisplayed();
		}catch(Throwable e) {
			PageParser.forceNavigate("AccountsPage", d);
			SeleniumUtil.waitForPageToLoad(3000);
		}
		
		acntLandingPage.clickOnAcntGroupView();
		SeleniumUtil.waitUntilSpinnerWheelIsInvisible();
		viewByGroup.clickingOnCreateGroupBtn();
		String[] specialChar = PropsUtil.getDataPropertyValue("SplCharacters").split(",");
		viewByGroup.clickingOnGroupNameTxtBox();
		for (int i = 0; i < specialChar.length; i++) {
			viewByGroup.AccountGroupNameTxtBox().sendKeys(specialChar[i]);
			SeleniumUtil.waitForPageToLoad(1000);
			Assert.assertEquals(viewByGroup.specialCharErrorMsg().getText().trim(),
					PropsUtil.getDataPropertyValue("specialCharacterErrorMsg_Web").trim(),
					"Error :group name textbox is accepting special character also.");
			viewByGroup.AccountGroupNameTxtBox().clear();
		}
	}

	@Test(description = "AT-97115,AT-97116: verify select accounts section", groups = { "Desktop",
			"Smoke" }, priority = 3, enabled = true)
	public void verifySelectAcntSection() {
		Assert.assertTrue(viewByGroup.verifyingContainerNameUnderSelectAcnts(),
				"Error : container names are not proper");
		Assert.assertEquals(viewByGroup.selectAllLabel().getText().trim(),
				PropsUtil.getDataPropertyValue("SelectAllAcntLabel").trim(),
				"Error :select all account label is not displaying");
	}

	@Test(description = "AT-97117,AT-97118,AT-97119,AT-97120,AT-97122: verify accounts under cash,card,loan,rewards,insurance,bills container", groups = {
			"Desktop", "Smoke" }, priority = 4, enabled = true)
	public void verifyAcntsUnderContainer() {
		Assert.assertTrue(viewByGroup.getAccountsUnderContainer("ContainerIndex", "ContainerAccounts"),
				"Error :accounts are not falling under respective container");
	}

	@Test(description = "AT-97121AT-97123: verify accounts sorted alphabetically under investment container", groups = {
			"Desktop", "Smoke" }, priority = 5, enabled = true)
	public void verifyInvAlphabeticalOrder() {
		Assert.assertTrue(viewByGroup.getAccountsUnderContainer("IndexOfInvContainer", "FullAcntNamesUnderInvestment"),
				"Error : Investment accounts are not in alphabetical order");
	}

	@Test(description = "AT-97124: verify the 'Create Group' button should be disabled until the user enters a valid group name.", groups = {
			"Desktop", "Smoke" }, priority = 6, enabled = true)
	public void verifyCreateGroupBtn() {
		viewByGroup.creatingGroupWithAllAcnts("");
		Assert.assertFalse(viewByGroup.saveChangesAfterUpdatingGroup().isEnabled(),
				"Error :save changes button is enabled without providing group name.");
		viewByGroup.AccountGroupNameTxtBox().sendKeys(PropsUtil.getDataPropertyValue("GroupName1").trim());
		Assert.assertTrue(viewByGroup.saveChangesAfterUpdatingGroup().isEnabled(),
				"Error :save changes button is disabled after providing group name.");
	}

	@Test(description = "AT-97125:verify the 'create Group' button shoulbe be disable until the user selects at least one account", groups = {
			"Desktop", "Smoke" }, dependsOnMethods = "verifyCreateGroupBtn", priority = 7, enabled = true)
	public void verifyCreateGroupBtn1() {
		viewByGroup.clickOnSelectAllAcntCheckBox();
		Assert.assertFalse(viewByGroup.saveChangesAfterUpdatingGroup().isEnabled(),
				"Error: save changes button is enabled without selecting any account");
		viewByGroup.clickOnSelectAllAcntCheckBox();
		Assert.assertTrue(viewByGroup.saveChangesAfterUpdatingGroup().isEnabled(),
				"Error: save changes button is disabled without selecting any account");
	}

	// ######################## No Group FTUE FLOW ##############################

	@Test(description = "AT-97142,AT-97143,AT-97145,AT-97146,AT-97149:Verify EA groups dropdown ftue flow when account groups is not there", groups = {
			"Desktop", "Smoke" }, dependsOnMethods = "verifyCreateGroupBtn", priority = 8, enabled = true)
	public void verifyFTUEflowForEA() {
		viewByGroup.clickOnCloseAcntGroupCross();
		SeleniumUtil.waitUntilSpinnerWheelIsInvisible();
		PageParser.forceNavigate("Expense", d);
		accountDropDown.clickEIAccountDropDown();
		accountDropDown.clickOnGroupsTabUnderAcntDropdown();

		Assert.assertTrue(viewByGroup.isCreateGroupBtnDisplayed(),
				"Error : Create Group button is not displaying in expense account dropdown");
		Assert.assertTrue(viewByGroup.isAccountGroupNoDataScreendIsDisplayed(),
				"Error : no data screen is not displaying in expense account dropdown");
		Assert.assertEquals(viewByGroup.AccountGroupNoDataScreenDisc().getText().trim(),
				PropsUtil.getDataPropertyValue("NoGroupFTUEMessage").trim(),
				"Error : no data screen is not displaying in expense account dropdown");
	}

	@Test(description = "AT-97142,AT-97143,AT-97145,AT-97146:Verify IA groups fropdown ftue flow when account groups is not there", groups = {
			"Desktop", "Smoke" }, dependsOnMethods = "login", priority = 9, enabled = true)
	public void verifyFTUEflowForIA() {
		accountDropDown.navigateToIncomeAnalysis();
		accountDropDown.clickEIAccountDropDown();
		accountDropDown.clickOnGroupsTabUnderAcntDropdown();

		Assert.assertTrue(viewByGroup.isCreateGroupBtnDisplayed(),
				"Error : Create Group button not displaying in Income account dropdown");
		Assert.assertTrue(viewByGroup.isAccountGroupNoDataScreendIsDisplayed(),
				"Error : no data screen is not displaying in Income account dropdown");
		Assert.assertEquals(viewByGroup.AccountGroupNoDataScreenDisc().getText().trim(),
				PropsUtil.getDataPropertyValue("NoGroupFTUEMessage").trim(),
				"Error : no data screen is not displaying in Income account dropdown");
	}

	@Test(description = "AT-97142,AT-97143,AT-97145,AT-97146: Verify Networth groups fropdown ftue flow when account groups is not there", groups = {
			"Desktop", "Smoke" }, dependsOnMethods = "login", priority = 10, enabled = true)
	public void verifyFTUEflowForNW() {
		netWorth.forceNavigateToNetWorth();
		SeleniumUtil.click(investmentHoldings.accountDropDown());
		accountDropDown.clickOnGroupsTabUnderAcntDropdown();

		Assert.assertTrue(viewByGroup.isCreateGroupBtnDisplayed(),
				"Error : Create Group is not displaying in Networth account dropdown");
		Assert.assertTrue(viewByGroup.isAccountGroupNoDataScreendIsDisplayed(),
				"Error : no data screen is not displaying in Networth account dropdown");
		Assert.assertEquals(viewByGroup.AccountGroupNoDataScreenDisc().getText().trim(),
				PropsUtil.getDataPropertyValue("NoGroupFTUEMessage").trim(),
				"Error : no data screen is not displaying in Networth account dropdown");
	}

	@Test(description = "AT-97142,AT-97143,AT-97145,AT-97146: verify manage button should navigate to Account Group finapp.", groups = {
			"Desktop", "Smoke" }, dependsOnMethods = "login", priority = 11, enabled = true)
	public void verifyFTUEflowForTxn() {
		PageParser.forceNavigate("Transaction", d);
		trnxAcntFilter.clickAccountFilter();
		trnxAcntFilter.clickingOnGroupLink();

		Assert.assertTrue(viewByGroup.isCreateGroupBtnDisplayed(),
				"Error : Create Group is not displaying in Transaction account dropdown");
		Assert.assertTrue(viewByGroup.isAccountGroupNoDataScreendIsDisplayed(),
				"Error : no data screen is not displaying in Transaction account dropdown");
		Assert.assertEquals(viewByGroup.AccountGroupNoDataScreenDisc().getText().trim(),
				PropsUtil.getDataPropertyValue("NoGroupFTUEMessage").trim(),
				"Error : no data screen is not displaying in Transaction account dropdown");
	}

	@Test(description = "AT-97152:verify if the user can not see all his accounts, the FTUE page should not be shown to the user.", groups = {
			"Desktop", "Smoke" }, dependsOnMethods = "login", priority = 12, enabled = false)
	public void verifyFTUEflowForCF() {
		PageParser.forceNavigate("CashFlow", d);
		SeleniumUtil.waitUntilSpinnerWheelIsInvisible();
		SeleniumUtil.click(investmentHoldings.accountDropDown());
		boolean status = true;

		// Depends on All Accounts key if the key is enable then only user can see FTUE,
		// by default value of this key is false so making test case as disabled.
		try {
			boolean isFTUEDisplayed = viewByGroup.isAccountGroupNoDataScreendIsDisplayed();
			if (isFTUEDisplayed == status) {
				status = false;
			}
		} catch (Exception e) {
		}
		Assert.assertTrue(status);
	}

	@Test(description = "AT-97142,AT-97143,AT-97145,AT-97146,AT-97144: verify manage button should navigate to Account Group finapp.", groups = {
			"Desktop", "Smoke" }, dependsOnMethods = "login", priority = 13, enabled = true)
	public void verifyFTUEflowForIH() {
		PageParser.forceNavigate("InvestmentHoldings", d);
		investmentHoldings.completeFtueFlow();
		SeleniumUtil.click(investmentHoldings.accountDropDown());
		accountDropDown.clickOnGroupsTabUnderAcntDropdown();

		Assert.assertTrue(viewByGroup.isCreateGroupBtnDisplayed(),
				"Error :Create Group is not displaying in InvestmentHoldings account dropdown");
		Assert.assertTrue(viewByGroup.isAccountGroupNoDataScreendIsDisplayed(),
				"Error : no data screen is not displaying in InvestmentHoldings account dropdown");
		Assert.assertEquals(viewByGroup.AccountGroupNoDataScreenDisc().getText().trim(),
				PropsUtil.getDataPropertyValue("NoGroupFTUEMessage").trim(),
				"Error : no data screen is not displaying in InvestmentHoldings account dropdown");
	}

	@Test(description = "AT-97148,AT-97620:verify after creating group the user should not be shown up ftue screen", groups = {
			"Desktop", "Smoke" }, dependsOnMethods = "verifyFTUEflowForIH", priority = 14, enabled = true)
	public void createGroup1() {
		boolean status = true;
		viewByGroup.clickingOnCreateGroupBtn();
		viewByGroup.creatingGroupWithAllAcnts(PropsUtil.getDataPropertyValue("GroupName1").trim());
		viewByGroup.clickingOnSubmitGroupBtn();

		try {
			boolean isFTUEDisplayed = viewByGroup.isAccountGroupNoDataScreendIsDisplayed();
			if (isFTUEDisplayed == status) {
				status = false;
			}
		} catch (Exception e) {
		}
		Assert.assertTrue(status);
	}

	@Test(description = "AT-97126,AT-97127:verify the group should get created and visible in account group finapp", groups = {
			"Desktop", "Smoke" }, dependsOnMethods = "createGroup1", priority = 15, enabled = true)
	public void verifyCreatedGroups() {

		PageParser.forceNavigate("AccountsPage", d);
		
		try {
			acntLandingPage.accountslayout().isDisplayed();
		}catch(Throwable e) {
			PageParser.forceNavigate("AccountsPage", d);
			SeleniumUtil.waitForPageToLoad(3000);
		}
		
		SeleniumUtil.waitUntilSpinnerWheelIsInvisible();
		acntLandingPage.clickOnAcntGroupView();

		SeleniumUtil.waitUntilSpinnerWheelIsInvisible();
		createGroupMethod.createMoreGroup();
		String accountNames[] = PropsUtil.getDataPropertyValue("AcntsToBeIncludedInGroup").trim().split(",");
		createGroupMethod.createGroup(PropsUtil.getDataPropertyValue("GroupName4").trim(), accountNames);
		SeleniumUtil.waitUntilToastMessageIsDisappeared();
		SeleniumUtil.refresh(d);
		PageParser.forceNavigate("AccountsPage", d);
		
		try {
			acntLandingPage.accountslayout().isDisplayed();
		}catch(Throwable e) {
			PageParser.forceNavigate("AccountsPage", d);
			SeleniumUtil.waitForPageToLoad(3000);
		}
		
		SeleniumUtil.waitUntilSpinnerWheelIsInvisible();
		acntLandingPage.clickOnAcntGroupView();
		List<String> accountGroupValues = SeleniumUtil
				.getWebElementsValue(SeleniumUtil.getWebElements(d, "AccountsContainerName", "AccountsPage", null));
		Assert.assertEquals(accountGroupValues,
				Arrays.asList(PropsUtil.getDataPropertyValue("GroupsNameList").split("\\,")),
				"Error : all the groups are not sorting alphabetically or groups are not created successfully.");
	}

	// ######################### Account group dropdown verification
	// ################################

	@Test(description = "AT-97128,AT-97157,AT-97162:verify the createGroupName4d group should be visible in Expense Analysis finapp", groups = {
			"Desktop", "Smoke" }, dependsOnMethods = "verifyCreatedGroups", priority = 16, enabled = true)
	public void verifyCreatedGroupInEAfinapp() {
		PageParser.forceNavigate("Expense", d);
		accountDropDown.clickEIAccountDropDown();
		accountDropDown.clickOnGroupsTabUnderAcntDropdown();
		Assert.assertTrue(accountDropDown.verifyAccountsGroupNameLabel("GroupsNameList"),
				"Error :the groups names under group dropdown are not proper");
		Assert.assertTrue(accountDropDown.verifyManageCreateGroups("CreateManageGroupLabels"),
				"Error :create group and manage group is not displaying under groups dropdown");
	}

	@Test(description = "AT-97153,AT-97154,AT-97156 :verify greyed out accounts and other relevant accounts under accounts group section in EA Finapp. ", groups = {
			"Desktop", "Smoke" }, dependsOnMethods = "verifyCreatedGroupInEAfinapp", priority = 17, enabled = true)
	public void verifyAcntsUnderGroupsInEA() {
		String SizeOfGroups[] = PropsUtil.getDataPropertyValue("SizeOfGroups").trim().split(",");

		Assert.assertTrue(accountDropDown.verifyGreyedOutAcnt("IncomeExpenseGreyedAcnts"),
				"Error :unsupported accounts are not greyed out");
		Assert.assertEquals(accountDropDown.verifyAllAcntsUnderGroupSection("1"), Integer.parseInt(SizeOfGroups[0]));
		Assert.assertEquals(accountDropDown.verifyAllAcntsUnderGroupSection("2"), Integer.parseInt(SizeOfGroups[1]));
	}

	@Test(description = "AT-97170,AT-97172,AT-97616: verify create group button should open create Group popup", groups = {
			"Desktop", "Smoke" }, dependsOnMethods = "verifyAcntsUnderGroupsInEA", priority = 18, enabled = true)
	public void verifyAddGroupBtnFunctionalityEA() {
		accountDropDown.clickingOnAddGroupBtn();
		SeleniumUtil.waitUntilSpinnerWheelIsInvisible();
		SeleniumUtil.waitFor(5);
		String actual = viewByGroup.AccountGroupPopupHeader().get(0).getText().trim();
		viewByGroup.clickOnCloseAcntGroupCross();
		Assert.assertEquals(actual, PropsUtil.getDataPropertyValue("Create_Account_Group_Header").trim(),
				"Error :Create group popup is not opening after clickin on create group button");
	}

	@Test(description = "AT-97167,AT-97168,AT-97169,AT-97147: verify manage button should navigate to Account Group finapp.", groups = {
			"Desktop", "Smoke" }, dependsOnMethods = "verifyAddGroupBtnFunctionalityEA", priority = 19, enabled = true)
	public void verifyManageGroupNavigationEA() {
		accountDropDown.clickingOnManageGroupBtn();
		SeleniumUtil.waitUntilSpinnerWheelIsInvisible();
		Assert.assertEquals(acntLandingPage.AccountsPageHeader().getText().trim(),
				PropsUtil.getDataPropertyValue("AccountGroupPageTitle").trim(),
				"Error : not navigating to account group finapp from manage group button");
		viewByGroup.clickingOnbackToRootFinapp();
		Assert.assertEquals(accountDropDown.EIAHeader().getText().trim(),
				PropsUtil.getDataPropertyValue("EA_Header").trim(),
				"Error : not navigating to EA finapp from back to EA finapp button");
	}

	@Test(description = "AT-97128,AT-97157,AT-97162:verify the created group should be visible in income Analysis finapp", groups = {
			"Desktop", "Smoke" }, dependsOnMethods = "verifyManageGroupNavigationEA", priority = 20, enabled = true)
	public void verifyCreatedGroupInIAfinapp() {
		accountDropDown.navigateToIncomeAnalysis();
		SeleniumUtil.waitUntilSpinnerWheelIsInvisible();
		accountDropDown.clickEIAccountDropDown();
		accountDropDown.clickOnGroupsTabUnderAcntDropdown();
		SeleniumUtil.waitForPageToLoad(3000);
		Assert.assertTrue(accountDropDown.verifyAccountsGroupNameLabel("GroupsNameList"),
				"Error :the groups names under group dropdown are not proper");
		Assert.assertTrue(accountDropDown.verifyManageCreateGroups("CreateManageGroupLabels"),
				"Error :create group and manage group is not displaying under groups dropdown");
	}

	@Test(description = "AT-97153,AT-97154,AT-97156 :verify greyed out accounts and other relevant accounts under accounts group section in EA Finapp. ", groups = {
			"Desktop", "Smoke" }, dependsOnMethods = "verifyCreatedGroupInIAfinapp", priority = 21, enabled = true)
	public void verifyAcntsUnderGroupsInIA() {
		String SizeOfGroups[] = PropsUtil.getDataPropertyValue("SizeOfGroups").trim().split(",");

		Assert.assertTrue(accountDropDown.verifyGreyedOutAcnt("IncomeExpenseGreyedAcnts"),
				"Error :unsupported accounts are not greyed out");
		Assert.assertEquals(accountDropDown.verifyAllAcntsUnderGroupSection("1"), Integer.parseInt(SizeOfGroups[0]));
		Assert.assertEquals(accountDropDown.verifyAllAcntsUnderGroupSection("2"), Integer.parseInt(SizeOfGroups[1]));
	}

	@Test(description = "AT-97170,AT-97172: verify create group button should open create Group popup", groups = {
			"Desktop", "Smoke" }, dependsOnMethods = "verifyAcntsUnderGroupsInIA", priority = 22, enabled = true)
	public void verifyAddGroupBtnFunctionalityIA() {
		accountDropDown.clickingOnAddGroupBtn();
		SeleniumUtil.waitUntilSpinnerWheelIsInvisible();
		String actual = viewByGroup.AccountGroupPopupHeader().get(0).getText().trim();
		viewByGroup.clickOnCloseAcntGroupCross();
		Assert.assertEquals(actual, PropsUtil.getDataPropertyValue("Create_Account_Group_Header").trim(),
				"Error :Create group popup is not opening after clickin on create group button");
	}

	@Test(description = "AT-97167,AT-97168,AT-97169,AT-97147: verify manage button should navigate to Account Group finapp.", groups = {
			"Desktop", "Smoke" }, dependsOnMethods = "verifyAddGroupBtnFunctionalityIA", priority = 23, enabled = true)
	public void verifyManageGroupNavigationIA() {
		accountDropDown.clickingOnManageGroupBtn();
		Assert.assertEquals(acntLandingPage.AccountsPageHeader().getText().trim(),
				PropsUtil.getDataPropertyValue("AccountGroupPageTitle").trim(),
				"Error : not navigating to account group finapp from manage group button");
		SeleniumUtil.waitUntilSpinnerWheelIsInvisible();
		viewByGroup.clickingOnbackToRootFinapp();
		Assert.assertEquals(accountDropDown.EIAHeader().getText().trim(),
				PropsUtil.getDataPropertyValue("IA_Header").trim(),
				"Error : not navigating to IA finapp from back to IA finapp button");
	}

	@Test(description = "AT-97129,AT-97160,AT-97165:verify the created group should be visible in Investment Holding finapp", groups = {
			"Desktop", "Smoke" }, dependsOnMethods = "verifyCreatedGroups", priority = 24, enabled = true)
	public void verifyCreatedGroupInNetworthFinapp() {
		netWorth.forceNavigateToNetWorth();
		accountDropDown.clickOnGroupsTabUnderAcntDropdown();
		Assert.assertTrue(accountDropDown.verifyAccountsGroupNameLabel("GroupsNameList"),
				"Error :the groups names under group dropdown are not proper");
		Assert.assertTrue(accountDropDown.verifyManageCreateGroups("CreateManageGroupLabels"),
				"Error :create group and manage group is not displaying under groups dropdown");
	}

	@Test(description = "AT-97153,AT-97154,AT-97156 :verify greyed out accounts and other relevant accounts under accounts group section in EA Finapp. ", groups = {
			"Desktop",
			"Smoke" }, dependsOnMethods = "verifyCreatedGroupInNetworthFinapp", priority = 25, enabled = true)
	public void verifyAcntsUnderGroupsInNW() {
		String SizeOfGroups[] = PropsUtil.getDataPropertyValue("SizeOfGroups").trim().split(",");

		Assert.assertTrue(accountDropDown.verifyGreyedOutAcnt("NetworthGreyedAcnts"),
				"Error :unsupported accounts are not greyed out");
		Assert.assertEquals(accountDropDown.verifyAllAcntsUnderGroupSection("1"), Integer.parseInt(SizeOfGroups[0]));
		Assert.assertEquals(accountDropDown.verifyAllAcntsUnderGroupSection("2"), Integer.parseInt(SizeOfGroups[1]));
	}

	@Test(description = "AT-97170,AT-97172: verify create group button should open create Group popup", groups = {
			"Desktop", "Smoke" }, dependsOnMethods = "verifyAcntsUnderGroupsInNW", priority = 26, enabled = true)
	public void verifyAddGroupBtnFunctionalityNW() {
		accountDropDown.clickingOnAddGroupBtn();
		String actual = viewByGroup.AccountGroupPopupHeader().get(0).getText().trim();
		SeleniumUtil.waitFor(2);
		viewByGroup.clickOnCloseAcntGroupCross();
		Assert.assertEquals(actual, PropsUtil.getDataPropertyValue("Create_Account_Group_Header").trim(),
				"Error :Create group popup is not opening after clickin on create group button");
	}

	@Test(description = "AT-97167,AT-97168,AT-97169,AT-97147: verify manage button should navigate to Account Group finapp.", groups = {
			"Desktop", "Smoke" }, dependsOnMethods = "verifyAddGroupBtnFunctionalityNW", priority = 27, enabled = true)
	public void verifyManageGroupNavigationNW() {
		accountDropDown.clickingOnManageGroupBtn();
		SeleniumUtil.waitUntilSpinnerWheelIsInvisible();
		Assert.assertEquals(acntLandingPage.AccountsPageHeader().getText().trim(),
				PropsUtil.getDataPropertyValue("AccountGroupPageTitle").trim(),
				"Error : not navigating to account group finapp from manage group button");
		viewByGroup.clickingOnbackToRootFinapp();
		Assert.assertEquals(acntLandingPage.AccountsPageHeader().getText().trim(),
				PropsUtil.getDataPropertyValue("NetworthHeader").trim(),
				"Error : not navigating to NW finapp from back to NW finapp button");
	}

	@Test(description = "AT-97132:verify the created group should be visible in budget finapp", groups = { "Desktop",
			"Smoke" }, dependsOnMethods = "verifyCreatedGroups", priority = 28, enabled = false)
	// this is part of regression already covered in existing script hence making it
	// disable.
	public void verifyCreatedGroupInBudgetFinapp() {
		PageParser.forceNavigate("Budget", d);
		SeleniumUtil.click(budgetNoActScreen.GetStartedButton());
		SeleniumUtil.waitUntilSpinnerWheelIsInvisible();
		SeleniumUtil.click(selectAccountScreen.selectAcntDropDownBtn());
		Assert.assertTrue(selectAccountScreen.verifyAccountsGroupNameLabel("GroupsNameList"),
				"Error :the groups names under group dropdown are not proper");
	}

	@Test(description = "AT-97133,AT-97158,AT-97162:verify the created group should be visible in cash flow finapp", groups = {
			"Desktop", "Smoke" }, dependsOnMethods = "verifyCreatedGroups", priority = 29, enabled = true)
	public void verifyCreatedGroupInCFFinapp() {
		PageParser.forceNavigate("CashFlow", d);
		SeleniumUtil.click(investmentHoldings.accountDropDown());
		Assert.assertTrue(cashFlowLoc.allCashFlowAcntsLabel().isDisplayed());
		Assert.assertEquals(cashFlowLoc.GroupsLabel().getText().trim(),
				PropsUtil.getDataPropertyValue("CashFlowGroupLabel").trim());
		Assert.assertTrue(cashFlowLoc.verifyAccountsGroupNameLabel("GroupsNameList"),
				"Error :the groups names under group dropdown are not proper");
		Assert.assertTrue(cashFlowLoc.verifyManageCreateGroups("CreateManageGroupLabels"),
				"Error :create group and manage group is not displaying under groups dropdown");
	}

	@Test(description = "AT-97153,AT-97154,AT-97156:verify greyed out accounts and other relevant accounts under accounts group section in EA Finapp. ", groups = {
			"Desktop", "Smoke" }, dependsOnMethods = "verifyCreatedGroupInCFFinapp", priority = 30, enabled = true)
	public void verifyAcntsUnderGroupsInCF() {
		String SizeOfGroups[] = PropsUtil.getDataPropertyValue("SizeOfGroups").trim().split(",");

		Assert.assertTrue(accountDropDown.verifyGreyedOutAcnt("CashFlowGreyedAcnts"),
				"Error :unsupported accounts are not greyed out");
		Assert.assertEquals(accountDropDown.verifyAllAcntsUnderGroupSectionInCF("1"),
				Integer.parseInt(SizeOfGroups[0]));
		Assert.assertEquals(accountDropDown.verifyAllAcntsUnderGroupSectionInCF("2"),
				Integer.parseInt(SizeOfGroups[1]));
	}

	@Test(description = "AT-97170,AT-97172,AT-97619: verify create group button should open create Group popup", groups = {
			"Desktop", "Smoke" }, dependsOnMethods = "verifyAcntsUnderGroupsInCF", priority = 31, enabled = true)
	public void verifyAddGroupBtnFunctionalityCF() {
		accountDropDown.clickingOnAddGroupBtn();
		SeleniumUtil.waitUntilSpinnerWheelIsInvisible();
		String actual = viewByGroup.AccountGroupPopupHeader().get(0).getText().trim();
		viewByGroup.clickOnCloseAcntGroupCross();
		Assert.assertEquals(actual, PropsUtil.getDataPropertyValue("Create_Account_Group_Header").trim(),
				"Error :Create group popup is not opening after clickin on create group button");
	}

	@Test(description = "AT-97167,AT-97168,AT-97169,AT-97147: verify manage button should navigate to Account Group finapp.", groups = {
			"Desktop", "Smoke" }, dependsOnMethods = "verifyAddGroupBtnFunctionalityCF", priority = 32, enabled = true)
	public void verifyManageGroupNavigationCF() {
		accountDropDown.clickingOnManageGroupBtn();
		Assert.assertEquals(acntLandingPage.AccountsPageHeader().getText().trim(),
				PropsUtil.getDataPropertyValue("AccountGroupPageTitle").trim(),
				"Error : not navigating to account group finapp from manage group button");
		SeleniumUtil.waitUntilSpinnerWheelIsInvisible();
		viewByGroup.clickingOnbackToRootFinapp();
		Assert.assertEquals(acntLandingPage.AccountsPageHeader().getText().trim(),
				PropsUtil.getDataPropertyValue("CashFlowHeader").trim(),
				"Error : not navigating to CF finapp from back to CF finapp button");
	}

	@Test(description = "AT-97131,AT-97159,AT-97164:verify the created group should be visible in transaction finapp", groups = {
			"Desktop", "Smoke" }, dependsOnMethods = "verifyCreatedGroups", priority = 33, enabled = true)
	public void verifyCreatedGroupInTxnFinapp() {
		PageParser.forceNavigate("Transaction", d);

		trnxAcntFilter.clickAccountFilter();
		trnxAcntFilter.clickingOnGroupLink();
		Assert.assertTrue(trnxAcntFilter.verifyAccountsGroupNameLabel("GroupsNameList"),
				"Error :the groups names under group dropdown are not proper");
		;
		Assert.assertTrue(trnxAcntFilter.verifyManageCreateGroups("CreateManageGroupLabels"),
				"Error :create group and manage group is not displaying under groups dropdown");
	}

	@Test(description = "AT-97153,AT-97154,AT-97156 :verify greyed out accounts and other relevant accounts under accounts group section in EA Finapp. ", groups = {
			"Desktop", "Smoke" }, dependsOnMethods = "verifyCreatedGroupInTxnFinapp", priority = 34, enabled = true)
	public void verifyAcntsUnderGroupsInTxn() {
		String SizeOfGroups[] = PropsUtil.getDataPropertyValue("SizeOfGroups").trim().split(",");

		Assert.assertTrue(accountDropDown.verifyGreyedOutAcnt("TxnGreyedAcnts"),
				"Error :unsupported accounts are not greyed out");
		Assert.assertEquals(accountDropDown.verifyAllAcntsUnderGroupSection("1"), Integer.parseInt(SizeOfGroups[0]));
		Assert.assertEquals(accountDropDown.verifyAllAcntsUnderGroupSection("2"), Integer.parseInt(SizeOfGroups[1]));
	}

	@Test(description = "AT-97170,AT-97172: verify create group button should open create Group popup", groups = {
			"Desktop", "Smoke" }, dependsOnMethods = "verifyAcntsUnderGroupsInTxn", priority = 35, enabled = true)
	public void verifyAddGroupBtnFunctionalityTxn() {
		accountDropDown.clickingOnAddGroupBtn();
		String actual = viewByGroup.AccountGroupPopupHeader().get(0).getText().trim();
		viewByGroup.clickOnCloseAcntGroupCross();
		Assert.assertEquals(actual, PropsUtil.getDataPropertyValue("Create_Account_Group_Header").trim(),
				"Error :Create group popup is not opening after clickin on create group button");
	}

	@Test(description = "AT-97167,AT-97168,AT-97169,AT-97147: verify manage button should navigate to Account Group finapp.", groups = {
			"Desktop", "Smoke" }, dependsOnMethods = "verifyAddGroupBtnFunctionalityTxn", priority = 36, enabled = true)
	public void verifyManageGroupNavigationTxn() {
		accountDropDown.clickingOnManageGroupBtn();
		Assert.assertEquals(acntLandingPage.AccountsPageHeader().getText().trim(),
				PropsUtil.getDataPropertyValue("AccountGroupPageTitle").trim(),
				"Error : not navigating to account group finapp from manage group button");
		SeleniumUtil.waitUntilSpinnerWheelIsInvisible();
		viewByGroup.clickingOnbackToRootFinapp();
		Assert.assertEquals(acntLandingPage.AccountsPageHeader().getText().trim(),
				PropsUtil.getDataPropertyValue("TransactionTitle").trim(),
				"Error : not navigating to Txn finapp from back to Txn finapp button");
	}

	@Test(description = "AT-97130,AT-97161,AT-97166:verify the created group should be visible in Investment Holding finapp", groups = {
			"Desktop", "Smoke" }, dependsOnMethods = "verifyCreatedGroups", priority = 37, enabled = true)
	public void verifyCreatedGroupInIHFinapp() {
		PageParser.forceNavigate("InvestmentHoldings", d);

		SeleniumUtil.click(investmentHoldings.accountDropDown());
		accountDropDown.clickOnGroupsTabUnderAcntDropdown();
		Assert.assertTrue(accountDropDown.verifyManageCreateGroups("CreateManageGroupLabels"),
				"Error :create group and manage group is not displaying under groups dropdown");
	}

	@Test(description = "AT-97153,AT-97154,AT-97156,AT-97150,AT-97617,AT-97618:verify greyed out accounts and other relevant accounts under accounts group section in EA Finapp. ", groups = {
			"Desktop", "Smoke" }, dependsOnMethods = "verifyCreatedGroupInIHFinapp", priority = 38, enabled = true)
	public void verifyAcntsUnderGroupsInIH() {
		String SizeOfGroups[] = PropsUtil.getDataPropertyValue("SizeOfGroups").trim().split(",");

		Assert.assertTrue(accountDropDown.verifyGreyedOutAcnt("InvestmentGreyedAcnts"),
				"Error :unsupported accounts are not greyed out");
		Assert.assertEquals(accountDropDown.verifyAllAcntsUnderGroupSection("1"), Integer.parseInt(SizeOfGroups[0]));
//		Assert.assertEquals(accountDropDown.verifyAllAcntsUnderGroupSection("2"), Integer.parseInt(SizeOfGroups[1]));
	}

	@Test(description = "AT-97167,AT-97168,AT-97169,AT-97147: verify manage button should navigate to Account Group finapp.", groups = {
			"Desktop", "Smoke" }, dependsOnMethods = "verifyAcntsUnderGroupsInIH", priority = 39, enabled = true)
	public void verifyManageGroupNavigationIH() {
		accountDropDown.clickingOnManageGroupBtn();
		Assert.assertEquals(acntLandingPage.AccountsPageHeader().getText().trim(),
				PropsUtil.getDataPropertyValue("AccountGroupPageTitle").trim(),
				"Error : not navigating to account group finapp from manage group button");
		SeleniumUtil.waitUntilSpinnerWheelIsInvisible();
		viewByGroup.clickingOnbackToRootFinapp();
		Assert.assertEquals(acntLandingPage.AccountsPageHeader().getText().trim(),
				PropsUtil.getDataPropertyValue("IH_page_header").trim(),
				"Error : not navigating to IH finapp from back to IH finapp button");
	}

}
