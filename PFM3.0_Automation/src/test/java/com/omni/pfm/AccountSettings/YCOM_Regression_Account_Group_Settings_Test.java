/*******************************************************************************
 * Copyright (c) 2018 Yodlee Inc. All Rights Reserved.
 * 
 * This software is the confidential and proprietary information of
 * Yodlee, Inc. Use is subject to license terms.
 * 
 * @author
 ******************************************************************************/
package com.omni.pfm.AccountSettings;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.omni.pfm.Accounts.Accounts_GroupValidation_Loc;
import com.omni.pfm.PageProcessor.PageParser;
import com.omni.pfm.pages.AccountAddition.AccountAddition;
import com.omni.pfm.pages.AccountSettings.AccountGroup_Settings_Loc;
import com.omni.pfm.pages.Login.LoginPage;
import com.omni.pfm.testBase.TestBase;
import com.omni.pfm.utility.PropsUtil;
import com.omni.pfm.utility.SeleniumUtil;

public class YCOM_Regression_Account_Group_Settings_Test extends TestBase {

	private static final Logger logger = LoggerFactory.getLogger(YCOM_Regression_Account_Group_Settings_Test.class);

	public AccountGroup_Settings_Loc groupSettingLoc;
	AccountAddition accountAdd;
	LoginPage login;
	Accounts_GroupValidation_Loc AccGrpValidation;

	@BeforeClass()
	public void init() throws Exception {
		doInitialization("Accounts group setting");
		login = new LoginPage();
		accountAdd = new AccountAddition();
		groupSettingLoc = new AccountGroup_Settings_Loc(d);
	}

	@Test(description = "creating user and adding account.", groups = {
			"DesktopBrowsers,sanity" }, priority = 1, enabled = true)
	public void login() throws Exception {
		LoginPage.loginMain(d, loginParameter);

		logger.info("Add Dag Site");
		SeleniumUtil.waitForPageToLoad();
		accountAdd.linkAccount();
		accountAdd.addAggregatedAccount("ihjuly1.site16441.1", "site16441.1");
	}

	@Test(description = "L1-44575: verify Create account group text", priority = 2, dependsOnMethods = "login", groups = {
			"Desktop" })
	public void noGroupValidations() throws Exception {

		logger.info("Navigating to Account Groups Page.");
		PageParser.forceNavigate("AccountGroups", d);
		SeleniumUtil.waitForPageToLoad(3000);
		Assert.assertEquals(groupSettingLoc.emptyGroupMessage().getText().trim(),
				PropsUtil.getDataPropertyValue("AccountsSettingEmptyMessage").trim());
	}

	@Test(description = "L1-44594, L1-44593, L1-44597: Verify 40 characters in group name", dependsOnMethods = "noGroupValidations", groups = {
			"Desktop" }, priority = 3)

	public void verifyCharacterInGroupName() throws InterruptedException {

		if (groupSettingLoc.isplusIconDisplayedMobile()) {

			// SeleniumUtil.click(groupSettingLoc.plusIcon());
			Assert.assertTrue(groupSettingLoc.plusIcon().isDisplayed(), "New Account Popup Group didnot Display.");
		} else {
			Assert.assertTrue(groupSettingLoc.newAccountGroupPopup().isDisplayed(),
					"New Account Popup Group didnot Display.");
		}

		SeleniumUtil.click(groupSettingLoc.createGroupBtn());
		Assert.assertEquals(groupSettingLoc.createGroupPopUpHeader().getText().trim(),
				PropsUtil.getDataPropertyValue("CreateGroupPopupHeader"));

		Assert.assertTrue(!groupSettingLoc.createOrUpdateGroup().isEnabled());

		SeleniumUtil.click(groupSettingLoc.groupNameTxtBox());
		groupSettingLoc.groupNameTxtBox().sendKeys(PropsUtil.getDataPropertyValue("VerifyGroupName"));

		String groupName = groupSettingLoc.groupNameTxtBox().getAttribute("value");
		Assert.assertTrue(groupName.length() == 40);
	}

	@Test(description = "L1-44592,L1-44595,L1-44602, L1-44578, L1-56022,  L1-44578, L1-44584, L1-44585, L1-44586, L1-44587, L1-44580, L1-44581:verify groups are succesfully created", dependsOnMethods = "noGroupValidations", priority = 4, groups = {
			"Desktop" })

	public void groupCreationValidation() throws Exception {

		groupSettingLoc.groupNameTxtBox().clear();
		SeleniumUtil.click(groupSettingLoc.groupNameTxtBox());
		groupSettingLoc.groupNameTxtBox().sendKeys(PropsUtil.getDataPropertyValue("Group_Name").trim());
		SeleniumUtil.click(groupSettingLoc.getAccountOptionChckBox().get(0));

		Assert.assertTrue(groupSettingLoc.createOrUpdateGroup().isEnabled());
		SeleniumUtil.click(groupSettingLoc.createOrUpdateGroup());
		Assert.assertEquals(groupSettingLoc.GroupName().getText().trim().toUpperCase(),
				PropsUtil.getDataPropertyValue("Group_Name").toUpperCase());

	}

	@Test(description = "L1-44592,L1-44595,L1-44602, L1-44578, L1-56022,  L1-44578, L1-44584, L1-44585, L1-44586, L1-44587, L1-44580, L1-44581:verify groups are succesfully created", dependsOnMethods = "noGroupValidations", priority = 5, groups = {
			"Desktop" })
	public void validatingPopUpLeftHeaders() {
		SeleniumUtil.click(groupSettingLoc.createGroupButtonTwo());

		groupSettingLoc.groupNameTxtBox().sendKeys(PropsUtil.getDataPropertyValue("ACCGroup2").trim());
		String popupText1 = groupSettingLoc.getAccountPopUpText().get(1).getText();

		String popupText2 = groupSettingLoc.getAccountPopUpText().get(2).getText();
		Assert.assertEquals(popupText2, PropsUtil.getDataPropertyValue("CreateGroupPopupText2"));
		Assert.assertEquals(popupText1, PropsUtil.getDataPropertyValue("CreateGroupPopupText1"));
	}

	@Test(description = "L1-44592,L1-44595,L1-44602, L1-44578, L1-56022,  L1-44578, L1-44584, L1-44585, L1-44586, L1-44587, L1-44580, L1-44581:verify groups are succesfully created", dependsOnMethods = "noGroupValidations", priority = 6, groups = {
			"Desktop" })
	public void creatingGroup2() {

		groupSettingLoc.getAccountOptionChckBox().get(2).click();
		groupSettingLoc.getAccountOptionChckBox().get(3).click();
		SeleniumUtil.click(groupSettingLoc.createOrUpdateGroup());
		SeleniumUtil.waitForPageToLoad();
		if (PageParser.isElementPresent("backToAccountGroupList", "AccountGroups", null)) {
			SeleniumUtil.click(groupSettingLoc.verifyBackToAccountGroupList());
			SeleniumUtil.waitForPageToLoad(1000);
		}
		SeleniumUtil.click(groupSettingLoc.Group2Name());
		SeleniumUtil.waitForPageToLoad(2000);
		Assert.assertEquals(groupSettingLoc.Group2NameHeader().getText().trim().toUpperCase(),
				PropsUtil.getDataPropertyValue("ACCGroup2").toUpperCase());
	}

	@Test(description = "L1-44607, L1-44608: verify Same Account added with DifferentGroupName", dependsOnMethods = "noGroupValidations", priority = 7, groups = {
			"Desktop" })
	public void verifyCheckBoxFunctionality() throws Exception {
		SeleniumUtil.click(groupSettingLoc.createGroupButtonTwo());
		SeleniumUtil.waitForPageToLoad(2000);

		groupSettingLoc.groupNameTxtBox().sendKeys(PropsUtil.getDataPropertyValue("ACCGroup3").trim());

		SeleniumUtil.click(groupSettingLoc.getAccountOptionChckBox().get(2));
		SeleniumUtil.waitForPageToLoad();
		SeleniumUtil.click(groupSettingLoc.getAccountOptionChckBox().get(3));
		SeleniumUtil.waitForPageToLoad();
		SeleniumUtil.click(groupSettingLoc.getAccountOptionChckBox().get(4));
		SeleniumUtil.waitForPageToLoad();
		// SeleniumUtil.click(groupSettingLoc.getAccountOptionChckBox().get(4));

		String Attribute = groupSettingLoc.getAccountOptionChckBox().get(4).getAttribute("aria-checked");
		System.out.println(Attribute);
		Assert.assertEquals(Attribute, PropsUtil.getDataPropertyValue("False"));
	}

	@Test(description = "L1-44607, L1-44608: verify Same Account added with DifferentGroupName", dependsOnMethods = "noGroupValidations", priority = 8, groups = {
			"Desktop" })
	public void sameAccountsDifferentGroupNameValidation() {
		SeleniumUtil.click(groupSettingLoc.createOrUpdateGroup());
		SeleniumUtil.waitForPageToLoad(3000);
		SeleniumUtil.waitUntilToastMessageIsDisappeared();
		SeleniumUtil.waitForPageToLoad(3000);
		if (PageParser.isElementPresent("backToAccountGroupList", "AccountGroups", null)) {
			SeleniumUtil.click(groupSettingLoc.verifyBackToAccountGroupList());
			SeleniumUtil.waitForPageToLoad(1000);
		}

		String actual[] = PropsUtil.getDataPropertyValue("Groups").split(",");
		for (int i = 0; i < groupSettingLoc.CreatedGroupList().size(); i++) {
			String expected = groupSettingLoc.CreatedGroupList().get(i).getText().toUpperCase();
			Assert.assertEquals(actual[i], expected);
		}
	}

	@Test(description = "L1-44577,L1-44609,L1-44598,L1-44600:Verify that duplicate group", dependsOnMethods = "noGroupValidations", priority = 9, groups = {
			"Desktop" })
	public void duplicateGroupValidations() throws Exception {
		SeleniumUtil.click(groupSettingLoc.createGroupButtonTwo());
		SeleniumUtil.click(groupSettingLoc.groupNameTxtBox());
		groupSettingLoc.groupNameTxtBox().clear();
		groupSettingLoc.groupNameTxtBox().sendKeys(PropsUtil.getDataPropertyValue("Group_Name"));
		groupSettingLoc.getAccountOptionChckBox().get(0).click();

		SeleniumUtil.click(groupSettingLoc.createOrUpdateGroup());

		Assert.assertTrue(groupSettingLoc.errorDuplicategroupTxt().isDisplayed(),"Duplicate group alert is not displayed");
		Assert.assertEquals(groupSettingLoc.errorDuplicategroupTxt().getText().trim(),
				PropsUtil.getDataPropertyValue("DuplicateGroupName").trim());
	}

	@Test(description = " Verify cross icon functionality", priority = 10, dependsOnMethods = "duplicateGroupValidations", groups = {
			"Desktop" })
	public void verifyCrossIcon() {
		SeleniumUtil.click(groupSettingLoc.closeDialog());
		Assert.assertEquals(groupSettingLoc.pageTitle().getText().trim(),
				PropsUtil.getDataPropertyValue("AccountGroupPageTitle"));
	}

	@Test(description = "L1-44610, L1-44611, L1-44612, L1-44613, L1-44626, L1-44627, L1-44625:  Verify that Edit group", dependsOnMethods = "noGroupValidations", priority = 11, groups = {
			"Desktop" })
	public void editGroupValidation() throws Exception {

		logger.info("Clicking on Test Automation User created Group.");
		SeleniumUtil.click(groupSettingLoc.groupList(PropsUtil.getDataPropertyValue("Group_Name").trim()));
		SeleniumUtil.waitForPageToLoad(2000);

		logger.info("Editing Test Automation User created Group.");
		SeleniumUtil.click(groupSettingLoc.editButton(PropsUtil.getDataPropertyValue("Group_Name")));

		String previousGroupName = groupSettingLoc.groupNameTxtBox().getAttribute("value");
		Assert.assertTrue(previousGroupName.equalsIgnoreCase(PropsUtil.getDataPropertyValue("Group_Name")));

		SeleniumUtil.click(groupSettingLoc.getAccountOptionChckBox().get(0));
		SeleniumUtil.click(groupSettingLoc.getAccountOptionChckBox().get(1));

		Assert.assertTrue(groupSettingLoc.saveChangesBtn().isDisplayed());

	}

	@Test(description = "L1-44606, L1-44628, L1-44629, L1-44630, L1-44631: Verify cancel functionality", dependsOnMethods = "editGroupValidation", priority = 12, groups = {

			"Desktop" })

	public void cancelGroupValidations() throws Exception {

		SeleniumUtil.click(groupSettingLoc.saveChangesBtn());
		SeleniumUtil.waitForPageToLoad(2000);
		if (groupSettingLoc.testAutomationGrp() != null)
			SeleniumUtil.click(groupSettingLoc.testAutomationGrp());

		SeleniumUtil.click(
				groupSettingLoc.deleteGroupButton(PropsUtil.getDataPropertyValue("Group_Name").toUpperCase().trim()));
		String confirmText = groupSettingLoc.confirmDeleteText().getText().trim();

		Assert.assertEquals(confirmText, PropsUtil.getDataPropertyValue("AccountGroupDeleteConfirmationMsg").trim());
	}

	@Test(description = "L1-44632, L1-44633: Verify group deletion functionality", priority = 13, dependsOnMethods = "cancelGroupValidations", groups = {

			"Desktop" })

	public void deleteGroupFunctionality() throws Exception {
		SeleniumUtil.click(groupSettingLoc.cancelButtonInGroup());
		SeleniumUtil.click(groupSettingLoc.deleteGroupButton(PropsUtil.getDataPropertyValue("Group_Name").trim()));
		SeleniumUtil.click(groupSettingLoc.deleteGroupOption());
		SeleniumUtil.waitUntilSpinnerWheelIsInvisible();
		SeleniumUtil.waitUntilToastMessageIsDisappeared();
		if(SeleniumUtil.isDisplayed(groupSettingLoc.testAutomationGrp(), 5)) {
			Assert.fail("Delete group functionality is not working");
		}
	}

	@Test(description = "L1-44699: verify the changes reflect to Account fianapp", dependsOnMethods = "deleteGroupFunctionality", priority = 14, groups = {
			"Desktop" })
	public void changesReflectOnAccountFinappValidation() throws Exception {
		PageParser.forceNavigate("AccountsPage", d);
		SeleniumUtil.click(groupSettingLoc.accGroupTab());
		SeleniumUtil.waitForPageToLoad(2000);
		Assert.assertEquals(groupSettingLoc.AccountGroupInAccFinapp().get(0).getText().trim(),
				PropsUtil.getDataPropertyValue("ACCGroup2"));
		Assert.assertEquals(groupSettingLoc.AccountGroupInAccFinapp().get(1).getText().trim(),
				PropsUtil.getDataPropertyValue("ACCGroup3"));
	}

}
