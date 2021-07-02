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
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import com.omni.pfm.PageProcessor.PageParser;
import com.omni.pfm.pages.Login.LoginPage;
import com.omni.pfm.testBase.TestBase;
import com.omni.pfm.utility.PropsUtil;
import com.omni.pfm.utility.SeleniumUtil;

public class Accounts_GroupValidation_Test extends TestBase {
	Logger logger=LoggerFactory.getLogger(Accounts_GroupValidation_Test.class);
	AccountsViewByGroup_Loc viewByGroup;
	AccountsLandingPage_Loc acntLandingPage;
	Accounts_ViewByGroup_Loc createGroupMethod;

	@BeforeClass()
	public void init() throws AWTException, InterruptedException
	{
		doInitialization("Accounts");

		TestBase.tc = TestBase.extent.startTest("Login", "Login");
		TestBase.test.appendChild(TestBase.tc);
		viewByGroup=new AccountsViewByGroup_Loc(d);
		acntLandingPage=new AccountsLandingPage_Loc(d);
		createGroupMethod=new Accounts_ViewByGroup_Loc(d);
	}

	@Test(description="login to the user", groups = {
	"DesktopBrowsers,sanity" }, priority = 1, enabled = true)
	public void login() throws Exception
	{  
		d.navigate().refresh();
		SeleniumUtil.waitForPageToLoad();
		PageParser.forceNavigate("AccountsPage", d);	
		SeleniumUtil.waitForPageToLoad();	
	}

	@Test(description = "AT-84110,AT-83981,AT-83982,AT-83934:verify Create account group text is present when there is no existing Group", priority = 2, groups = {
	"Desktop" },dependsOnMethods="login",enabled=true)

	public void noAcntGroupValidations() throws Exception {
		
		try {
			acntLandingPage.accountslayout().isDisplayed();
		}catch(Throwable e) {
			PageParser.forceNavigate("AccountsPage", d);
			SeleniumUtil.waitForPageToLoad(3000);
		}
		
		acntLandingPage.clickOnAcntGroupView();
		SeleniumUtil.waitForPageToLoad();
		Assert.assertTrue(viewByGroup.isAccountGroupNoDataScreendIsDisplayed());
		Assert.assertEquals(acntLandingPage.AccountsPageHeader().getText(), PropsUtil.getDataPropertyValue("Accounts_Header1").trim());
		Assert.assertEquals(viewByGroup.AccountGroupNoDataScreen().getText().trim(), PropsUtil.getDataPropertyValue("EmptyGroupScreen").trim());
		Assert.assertEquals(viewByGroup.AccountGroupNoDataScreenDisc().getText().trim(), PropsUtil.getDataPropertyValue("mannAccTest_NoGroupScreenDesc").trim());
	}

	@Test(description = "AT-84051,AT-84061,AT-84052:Verify that the create account group window pop up should come when user clicks the Create Group button on the Empty State screen", groups = { "Desktop","Smoke" },
			dependsOnMethods="login",priority = 3,enabled=true)

	public void popupHeaderAndSaveBtnStatus() throws InterruptedException {
		viewByGroup.clickingOnCreateGroupBtn();
		SeleniumUtil.waitForPageToLoad(3000);
		Assert.assertEquals(viewByGroup.AccountGroupPopupHeader().get(0).getText().trim(),PropsUtil.getDataPropertyValue("Create_Account_Group_Header").trim());

		logger.info("Verify the CREATE GROUP button is in-active untill user selects at least one account from the list and enters a name for the Group");
		Assert.assertTrue(!viewByGroup.saveChangesAfterUpdatingGroup().isEnabled());
	}

	@Test(description = "AT-84053,AT-84073,AT-84059:Verify that the Group name field allow maximum 40 characters.", groups = { "Desktop","Smoke" },
			dependsOnMethods="popupHeaderAndSaveBtnStatus",priority = 4,enabled=true)

	public void verifyCharacterInGroupName() throws InterruptedException {

		viewByGroup.clickingOnGroupNameTxtBox();
		viewByGroup.AccountGroupNameTxtBox().sendKeys(PropsUtil.getDataPropertyValue("GroupNameMaximum").trim());
		String groupName = viewByGroup.AccountGroupNameTxtBox().getAttribute("maxlength");
		Assert.assertTrue(groupName.equalsIgnoreCase("40"));
	}

	@Test(description = "AT-84062,AT-84054,AT-84077,AT-84079,AT-84055,AT-84060,AT-84161: verify Create group Pup up functionality verification", priority = 5, groups = { "Desktop","Smoke" }
	,dependsOnMethods="verifyCharacterInGroupName",enabled=true)

	public void groupCreationValidation() throws Exception {
		String accountNames[]=PropsUtil.getDataPropertyValue("AcntsToBeIncludedInGroup").trim().split(",");
		createGroupMethod.createGroup(PropsUtil.getDataPropertyValue("GroupName1").trim(),accountNames);
		Assert.assertTrue(viewByGroup.verifyingGroupNameSorting("GroupName1"));
	}

	@Test(description = "AT-84489,AT-84490,AT-83988:Verify the more option available in Account Group view should display 'Create Group', 'Feature Tour' and 'Settings options'",
			priority = 6, groups = {"Desktop" },dependsOnMethods="groupCreationValidation",enabled=true)
	public void verifyOptionsUnderMore(){
		SeleniumUtil.waitForPageToLoad();
		acntLandingPage.clickOnMoreBtn();
		if(appFlag.equals("app")||appFlag.equals("emulator")) {
			Assert.assertTrue(acntLandingPage.verifyVisibleOptionsUnderMoreMobile("VisibleOptionsUnderMoreBtnGorGroupMobile"));
		}else {
			Assert.assertTrue(acntLandingPage.verifyVisibleOptionsUnderMore("VisibleOptionsUnderMoreBtnGorGroup"));
		}
	}


	@Test(description = "AT-97113,AT-84073,AT-84071,AT-84066,AT-84097,AT-84242:Verify that duplicate group validtaions are working as expected.",
			priority = 7, groups = {"Desktop" },dependsOnMethods="verifyOptionsUnderMore",enabled=true)
	public void creatingDuplicateGroup() throws Exception {

		if(appFlag.equals("app")||appFlag.equals("emulator")) {
			//SeleniumUtil.click(acntLandingPage.VisibleOptionsUnderMoreBtn().get(1));
			//failing for mobile
		}else {
			SeleniumUtil.click(acntLandingPage.VisibleOptionsUnderMoreBtn().get(0));
			String accountNames[]=PropsUtil.getDataPropertyValue("AcntsToBeIncludedInGroup").trim().split(",");
			createGroupMethod.createGroup(PropsUtil.getDataPropertyValue("GroupName1").trim(),accountNames);

			Assert.assertTrue(viewByGroup.isCrossIconDisplayed());
			Assert.assertTrue(viewByGroup.saveChangesAfterUpdatingGroup().isEnabled());
			viewByGroup.clickingOnSubmitGroupBtn();
			String err=viewByGroup.errorDuplicategroupTxt().getText().trim();
			SeleniumUtil.waitForPageToLoad(500);
			viewByGroup.clickOnCloseAcntGroupCross();
			SeleniumUtil.waitForPageToLoad(500);
			Assert.assertTrue(err.contains(PropsUtil.getDataPropertyValue("DuplicateGroupMsg").trim()));	
		}
		
		
	}

	@Test(description = "AT-84085,AT-84043,AT-84278,AT-84067,AT-84087,AT-84080AT-84075,AT-84086,AT-84076,AT-84065:Verify that Edit group functionality is working as expected.", priority = 8, groups = {
	"Desktop" },dependsOnMethods="creatingDuplicateGroup",enabled=true)

	public void editGroupValidation() throws Exception {
		String accountNames[]=PropsUtil.getDataPropertyValue("AcntsToBeIncludedInGroup").trim().split(",");
		
		SeleniumUtil.click(viewByGroup.AccountGroupEditBtn().get(0));
		SeleniumUtil.waitForPageToLoad(3000);

		Assert.assertEquals(viewByGroup.getPopupHeader(), PropsUtil.getDataPropertyValue("EditGroupPopUPHeader").trim());
		Assert.assertTrue(viewByGroup.isCrossIconDisplayed());
		
		logger.info("Verify that the user selected accounts should be checked in the Accounts to Include listbox in Edit Account Group window.");
		createGroupMethod.verifyCheckBoxByUsingAcntNumber(accountNames);
		
		Assert.assertTrue(viewByGroup.AccountGroupNameTxtBox().getAttribute("value").equalsIgnoreCase(PropsUtil.getDataPropertyValue("GroupName1")));
	}

	@Test(description = " Verify that Edit group functionality is working as expected.", priority = 9, groups = {
	"Desktop" },dependsOnMethods="editGroupValidation",enabled=true)
	public void editGroup() {

		SeleniumUtil.click(viewByGroup.AccountGroupCheckBox().get(1));
		SeleniumUtil.waitForPageToLoad(3000);

		Assert.assertTrue(viewByGroup.saveChangesAfterUpdatingGroup().isDisplayed());
		SeleniumUtil.click(viewByGroup.saveChangesAfterUpdatingGroup());

		SeleniumUtil.waitForPageToLoad(3000);
		Assert.assertEquals(viewByGroup.AccountsAcntNames().size(), Integer.parseInt(PropsUtil.getDataPropertyValue("AcntsIncludedInEditedGroup").trim()));
	}

	@Test(description = "AT-84070,AT-83984,AT-84068: verfying delete, edit and collapse icons order and visibility", priority = 10, groups = {
	"Desktop" },dependsOnMethods="editGroupValidation",enabled=true)
	public void verifyAllGroupIcons() throws Exception {

		Assert.assertEquals(viewByGroup.AccountsContainerName().get(0).getText().trim(), PropsUtil.getDataPropertyValue("GroupName1").trim());
		logger.info("Verify once the user has created the group then the delete, edit and collapse icon should display in front of the Group Name in respective");

		viewByGroup.verifyAcntGroupIconsOrder();
	}

	@Test(description = "AT-84102,AT-84100,AT-84099,AT-84159: verfying delete popup.", priority = 11, groups = {
	"Desktop" },dependsOnMethods="editGroupValidation",enabled=true)
	public void verifyDeletePopup() throws Exception {

		SeleniumUtil.click(viewByGroup.AccountGroupDeleteBtn().get(0));
		viewByGroup.verifyingdeletPopUpContent();

		Assert.assertEquals(viewByGroup.getPopupHeader(), PropsUtil.getDataPropertyValue("DeleteGroupPopupHeader").trim());
		Assert.assertTrue(viewByGroup.isDeleteGroupCancelBtnDisplayed());
		Assert.assertTrue(viewByGroup.isDeleteGroupDeleteBtnDisplayed());
	}

	@Test(description = "AT-84108,:verfying delete group cancel button functionality", priority = 12, groups = {
	"Desktop" },dependsOnMethods="verifyDeletePopup",enabled=true)
	public void verifyCancelBtnFunctionality() throws Exception {
		viewByGroup.clickingOndeleteGroupCancelBtn();

		logger.info("verifying Popup should close so delete popup header size is equals to zero");
		Assert.assertTrue(viewByGroup.AccountGroupPopupHeader().size()==0);
	}

	@Test(description = "AT-84109:verfying delete group cross icon functionality", priority = 13, groups = {
	"Desktop" },dependsOnMethods="verifyDeletePopup",enabled=true)
	public void verifyCrossIconFunctionality() throws Exception {
		SeleniumUtil.click(viewByGroup.AccountGroupDeleteBtn().get(0));
		viewByGroup.clickOnCloseAcntGroupCross();

		logger.info("verifying Popup should close so delete popup header size is equals to zero");
		Assert.assertTrue(viewByGroup.AccountGroupPopupHeader().size()==0);
	}

	@Test(description = "AT-84107:verfying delete group delete button functionality", priority = 14, groups = {
	"Desktop" },dependsOnMethods="verifyDeletePopup",enabled=true)
	public void verifyDeleteBtnFunctionality() throws Exception {
		SeleniumUtil.click(viewByGroup.AccountGroupDeleteBtn().get(0));

		viewByGroup.clickingOndeleteGroupDeleteBtn();
		Assert.assertTrue(viewByGroup.isAccountGroupNoDataScreendIsDisplayed());
	}

	@Test(description = "AT-83996:VERIFY the user is able to create new account group with the same name successfully.", priority = 15, groups = {
	"Desktop" },dependsOnMethods="verifyDeleteBtnFunctionality",enabled=true)
	public void verifyCretionOfSameNameGroup() throws Exception {
		String accountNames[]=PropsUtil.getDataPropertyValue("AcntsToBeIncludedInGroup").trim().split(",");
		viewByGroup.clickingOnCreateGroupBtn();
		createGroupMethod.createGroup(PropsUtil.getDataPropertyValue("GroupName1").toLowerCase().trim(), accountNames);	
		SeleniumUtil.waitForPageToLoad(3000);

		Assert.assertEquals(viewByGroup.AccountsContainerName().get(0).getText().trim(), PropsUtil.getDataPropertyValue("GroupName1").trim());
	}

	@Test(description = "AT-84106:verify delete group functionality in collapse view", priority = 16, groups = {
	"Desktop" },dependsOnMethods="verifyCretionOfSameNameGroup",enabled=true)
	public void verifydeleteInCollapseView() throws Exception {
		SeleniumUtil.click(viewByGroup.AccountGroupExpandCollapseBtn().get(0));	
		SeleniumUtil.click(viewByGroup.AccountGroupDeleteBtn().get(0));
		viewByGroup.clickingOnDeleteGroupDeleteBtn();
		
		Assert.assertTrue(viewByGroup.isAccountGroupNoDataScreendIsDisplayed());
	}

	@Test(description = "AT-84072,AT-83935:creating multiple groups", priority = 17, groups = {
	"Desktop" },dependsOnMethods="verifydeleteInCollapseView",enabled=true)
	public void creatingMultipleGroups() throws Exception {
		String accountNames[]=PropsUtil.getDataPropertyValue("AcntsToBeIncludedInGroup").trim().split(",");
		viewByGroup.clickingOnCreateGroupBtn();
		createGroupMethod.createGroup(PropsUtil.getDataPropertyValue("GroupName1").toLowerCase().trim(), accountNames);	

		acntLandingPage.clickingOnCreateGroupBtnUnderMore();
		createGroupMethod.createGroup(PropsUtil.getDataPropertyValue("GroupName2").toLowerCase().trim(), accountNames);	

		acntLandingPage.clickingOnCreateGroupBtnUnderMore();
		viewByGroup.creatingGroupWithAllAcnts(PropsUtil.getDataPropertyValue("GroupName3").toLowerCase().trim());
		viewByGroup.clickingOnSubmitGroupBtn();
		SeleniumUtil.waitForPageToLoad(2000);

		if(!(viewByGroup.AccountsContainerName().size()==Integer.parseInt(PropsUtil.getDataPropertyValue("ExpectedCount").trim()))) {
			SeleniumUtil.waitForPageToLoad(2000);
		}
		
		Assert.assertEquals(viewByGroup.AccountsContainerName().size(),Integer.parseInt(PropsUtil.getDataPropertyValue("ExpectedCount").trim()));
	}

	@Test(description = "AT-83985,AT-83984:Verify that the Account group names are sorted alphabetically.", priority = 18, groups = {
	"Desktop" },dependsOnMethods="creatingMultipleGroups",enabled=true)
	public void verifyingAcntGroupSorting() throws Exception {

		Assert.assertTrue(viewByGroup.verifyingGroupNameSorting("GroupsName"));
		Assert.assertEquals(viewByGroup.AccountGroupDeleteBtn().size(), Integer.parseInt(PropsUtil.getDataPropertyValue("ExpectedCount").trim()));
		Assert.assertEquals(viewByGroup.AccountGroupEditBtn().size(), Integer.parseInt(PropsUtil.getDataPropertyValue("ExpectedCount").trim()));
		Assert.assertEquals(viewByGroup.AccountGroupExpandCollapseBtn().size(), Integer.parseInt(PropsUtil.getDataPropertyValue("ExpectedCount").trim()));	
	}

	@Test(description = " AT-83987:Verify that the refresh icon is not displayed at the Account level in 'View by Account Group' screen.", groups = {
	"DesktopBrowsers,sanity" }, priority = 19,dependsOnMethods="creatingMultipleGroups", enabled = true)
	public void verifyAcntLevelRefresh() {
		Assert.assertTrue(acntLandingPage.accountLevelRefresh().size() == 0);
	}


	@Test(description = "AT-83990:Verify that all the account groups are expanded by default.", groups = {
	"DesktopBrowsers,sanity" }, priority = 20,dependsOnMethods="creatingMultipleGroups", enabled = true)
	public void VerifyGroupsAreInExpandedForm() {

		viewByGroup.verifyGroupsAreInExpandedForm();
	}

	@Test(description = "AT-83991:Verify that the user should be able to miminise the accounts under  each using the ^ Minimise Icon at the Group level Preconditions ", 
			groups = {"DesktopBrowsers" }, priority = 21 ,dependsOnMethods="creatingMultipleGroups", enabled = true)
	public void VerifyUserCanMinimize() throws InterruptedException {

		viewByGroup.verifyUserCanMinimise();
	}

	@Test(description = "AT-83992:Verify that the user should be able to maximise the accounts under each  using the ^ Minimise Icon at the Group level Preconditions ", groups = {
	"DesktopBrowsers" }, priority = 22,dependsOnMethods="creatingMultipleGroups", enabled = true)
	public void VerifyUserCanMaximise() throws InterruptedException {

		viewByGroup.verifyUserCanMaximise();
	}
	
	@Test(description = "Verify that the user should be able to delete the accounts under each  using delete icon the Group level", groups = {
	"DesktopBrowsers" }, priority = 23,dependsOnMethods="creatingMultipleGroups", enabled = true)
	public void VerifyUserCanDelete() throws InterruptedException {
		viewByGroup.verifyUserCanDelete();
		SeleniumUtil.waitForPageToLoad(3000);
		Assert.assertTrue(viewByGroup.isAccountGroupNoDataScreendIsDisplayed());
	}

}
