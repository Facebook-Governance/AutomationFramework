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
import com.omni.pfm.pages.AccountSettings.AccountsSetting_GlobalSettings_Loc;
import com.omni.pfm.testBase.TestBase;
import com.omni.pfm.utility.PropsUtil;
import com.omni.pfm.utility.SeleniumUtil;

public class Accounts_DeleteAccount_Test extends TestBase {
	Logger logger = LoggerFactory.getLogger(Accounts_GroupValidation_Test.class);
	Accounts_DeleteAccount_Loc deleteAcnt;
	Accounts_AccountSettingPopUp_Loc accountSettiingsPopup;
	AccountsSetting_GlobalSettings_Loc globalSetting;

	@BeforeClass()
	public void init() throws AWTException, InterruptedException {
		doInitialization("Accounts");
		TestBase.tc = TestBase.extent.startTest("Login", "Login");
		TestBase.test.appendChild(TestBase.tc);
		deleteAcnt = new Accounts_DeleteAccount_Loc(d);
		accountSettiingsPopup = new Accounts_AccountSettingPopUp_Loc(d);
		globalSetting = new AccountsSetting_GlobalSettings_Loc(d);
	}

	@Test(description = "login to the user", groups = { "DesktopBrowsers,sanity" }, priority = 1, enabled = true)
	public void login() throws Exception {

		d.navigate().refresh();
		PageParser.forceNavigate("AccountsPage", d);
		SeleniumUtil.waitForPageToLoad(2000);
	}

	// ************** Verify Delete Popup In Accounts Page **********************

	@Test(description = "AT-92978,AT-84158,AT-84156,AT-84190,AT-92941,AT-92951,AT-92952,AT-92979:Verify Delete Account popup. ", groups = {
			"Regression" }, priority = 2, dependsOnMethods = "login", enabled = true)
	public void verifyDeletePopupHeaderInAcntsFinap() throws Exception {

		try {
			accountSettiingsPopup.accountslayout().isDisplayed();
		}catch(Throwable e) {
			PageParser.forceNavigate("AccountsPage", d);
			SeleniumUtil.waitForPageToLoad(3000);
		}
		
		SeleniumUtil.selectDropDown(d, "TESTDATA1", "Accounts Settings");
		// SeleniumUtil.selectDropDown(d, "Super CD Plus","Accounts Settings");
		SeleniumUtil.waitForPageToLoad(2000);
		accountSettiingsPopup.clickOnDeleteButton();
		SeleniumUtil.waitForPageToLoad(3000);
		Assert.assertEquals(deleteAcnt.popUpHeader().get(1).getText().trim(),
				PropsUtil.getDataPropertyValue("DeleteAccountHeaderText").trim());
	}

	@Test(description = "AT-92984,AT-92980,AT-92981,AT-92957,AT-84158,AT-84156,AT-84190,AT-92953,AT-92954:Ensure to check for a Aggregated Bank container Account setting icon is shown in Accounts Finapp and on click user lands to Edit Account popup. ", groups = {
			"Regression" }, priority = 3, dependsOnMethods = "login", enabled = true)
	public void verifyDeletePopUpContentInAcntsFinap() throws Exception {

		deleteAcnt.verifyPopupContent();
	}

	@Test(description = "AT-92982,AT-84103,AT-84162,AT-92960,AT-92987:Verify the text color in the buttons: CANCEL is in BLUE color & DELETE is in RED color.", groups = {
			"Regression" }, priority = 4, dependsOnMethods = "login", enabled = true)
	public void verifyPopupButtonsInAcntsFinap() throws Exception {
		SeleniumUtil.waitForPageToLoad(3000);
		Assert.assertEquals(deleteAcnt.popUpCancelBtn().getCssValue("color"),
				PropsUtil.getDataPropertyValue("CancelButtonColour").trim());
		Assert.assertEquals(deleteAcnt.popUpDeleteBtn().getCssValue("color"),
				PropsUtil.getDataPropertyValue("DeleteButtonColour").trim());
	}

	@Test(description = "AT-84163,AT-92961,AT-92963,AT-92985,AT-92988:By default the Delete button should be disabled in delete account floater and it should eable only when user check the check-box appeared there.", groups = {
			"Regression" }, priority = 5, dependsOnMethods = "login", enabled = true)
	public void verifyDeleteButtonInAcntsFinap() throws Exception {

		Assert.assertTrue(deleteAcnt.popUpDeleteBtn().getAttribute("class").trim()
				.contains(PropsUtil.getDataPropertyValue("DisableBtnDiscription").trim()));
	}

	@Test(description = "AT-92995,AT-92991,AT-92990,AT-92989,AT-92974,AT-92986,AT-92983,AT-92964,AT-92962,AT-84176,AT-84164,AT-92956,AT-92959:Delete Button should get enable after user check the box.", groups = {
			"Regression" }, priority = 6, dependsOnMethods = "login", enabled = true)
	public void verifyCBFunctionalityInAcntsFinap() throws Exception {

		deleteAcnt.clickingOndeletePopUpCB();
		Assert.assertTrue(deleteAcnt.popUpDeleteBtn().isEnabled());
		Assert.assertTrue(deleteAcnt.popUpCancelBtn().isEnabled());
	}

	@Test(description = "AT-84165:After click on the CANCEL button it should come back to the previous view.", groups = {
			"Regression" }, priority = 7, dependsOnMethods = "login", enabled = true)
	public void verifyCancelFunctionalityInAcntsFinap() throws Exception {

		deleteAcnt.clickingOnCancelBtn();
		Assert.assertEquals(accountSettiingsPopup.popUpHeader().getText().trim(),
				PropsUtil.getDataPropertyValue("AccountSettingPopUpHeader").trim());
	}

	@Test(description = "AT-84486,AT-84172,AT-92968,AT-92972:After deleting the Account the success message should display", groups = {
			"Regression" }, priority = 8, dependsOnMethods = "login", enabled = true)
	public void verifyDeletingFunctionalityInAcntsFinap() throws Exception {

		accountSettiingsPopup.clickOnDeleteButton();
		deleteAcnt.clickingOndeletePopUpCB();
		deleteAcnt.clickingOndeleteBtn();

		SeleniumUtil.waitForPageToLoad(2000);
		Assert.assertTrue(deleteAcnt.deleteAcntSuccessMsg().isDisplayed());
	}

	// *********** Verify Delete Site popup in Account Setting finapp
	// *****************

	@Test(description = "Navigation To Accounts Setting finapp", groups = {
			"Regression" }, priority = 9, enabled = true)
	public void navigationToAccountSettings() {
		PageParser.forceNavigate("AccountSettings", d);
		SeleniumUtil.waitForPageToLoad();
		Assert.assertEquals(globalSetting.AccountSettingPageHeader().getText().trim(),
				PropsUtil.getDataPropertyValue("SettingsPageTitle"));
	}

	@Test(description = "AT-93004,AT-76073:verify Delete site Popup.", groups = {
			"Regression" }, priority = 10, enabled = true)
	public void verifyDeleteSite() throws Exception {
		globalSetting.clickinOnDeleteSite();
		Assert.assertEquals(deleteAcnt.popUpHeader().get(0).getText().trim(),
				PropsUtil.getDataPropertyValue("DeleteTitle").trim());
	}

	@Test(description = "AT-76077,AT-76076,AT-76081,AT-76050:Ensure to check for a Aggregated Bank container Account setting icon is shown in Accounts Finapp and on click user lands to Edit Account popup. ", groups = {
			"Regression" }, priority = 11, enabled = true)
	public void verifyDeletePopUpContent() throws Exception {

		Assert.assertEquals(deleteAcnt.deletePopUpASContent1().getText().trim(),
				PropsUtil.getDataPropertyValue("deleteSite_confirm_msg").trim());
		Assert.assertEquals(deleteAcnt.deletePopUpASContent2().getText().trim(),
				PropsUtil.getDataPropertyValue("AccountDeleteWarningMessage").trim());
		Assert.assertEquals(deleteAcnt.deletePopUpASContent3().getText().trim(),
				PropsUtil.getDataPropertyValue("DeleteSiteConfirmationMsg").trim());
	}

	@Test(description = "AT-76056:By default the Delete button should be disabled in delete account floater and it should eable only when user check the check-box appeared there.", groups = {
			"Regression" }, priority = 12, enabled = true)
	public void verifyDeleteButtonState() throws Exception {

		Assert.assertTrue(deleteAcnt.popUpDeleteBtn().getAttribute("class").trim()
				.contains(PropsUtil.getDataPropertyValue("DisableBtnDiscription").trim()));
	}

	@Test(description = "AT-76078,AT-76080: Verify the text color in the buttons: CANCEL is in BLUE color & DELETE is in RED color.", groups = {
			"Regression" }, priority = 13, enabled = true)
	public void verifyDeletePopupButtons() throws Exception {
		SeleniumUtil.waitForPageToLoad(3000);
		Assert.assertTrue(deleteAcnt.isCBVisibile());
		Assert.assertEquals(deleteAcnt.popUpCancelBtn().getCssValue("color"),
				PropsUtil.getDataPropertyValue("CancelButtonColour").trim());
		Assert.assertEquals(deleteAcnt.popUpDeleteBtn().getCssValue("color"),
				PropsUtil.getDataPropertyValue("DeleteButtonColour").trim());
	}

	@Test(description = "AT-76083,AT-76051,AT-76061:Delete Button should get enable after user check the box.", groups = {
			"Regression" }, priority = 14, enabled = true)
	public void verifyDeleteBtnDisability() throws Exception {

		deleteAcnt.clickingOndeletePopUpCB();
		Assert.assertTrue(deleteAcnt.popUpCancelBtn().isEnabled());
		Assert.assertTrue(deleteAcnt.popUpDeleteBtn().isEnabled());
	}

	@Test(description = "AT-76086:After click on the CANCEL button it should come back to the previous view.", groups = {
			"Regression" }, priority = 15, enabled = true)
	public void verifyCancelFunctionality() throws Exception {

		deleteAcnt.clickingOnCancelBtn();
		Assert.assertEquals(globalSetting.AccountSettingPageHeader().getText().trim(),
				PropsUtil.getDataPropertyValue("SettingsPageTitle"));
	}

	// ******************* Verify Delete Account popUp in Account Setting finapp
	// ***********************

	@Test(description = "AT-76033,AT-76034,AT-76074,AT-76045:Verify Delete Account popup. ", groups = {
			"Regression" }, priority = 16, enabled = true)
	public void verifyAccountSettingInCashContainer() throws Exception {
		SeleniumUtil.click(globalSetting.getSettingByAcntName("bank").get(0));
		SeleniumUtil.waitForPageToLoad(2000);

		accountSettiingsPopup.clickOnDeleteButton();
		Assert.assertEquals(deleteAcnt.popUpHeader().get(1).getText().trim(),
				PropsUtil.getDataPropertyValue("DeleteAccountHeaderText").trim());
	}

	@Test(description = "AT-76046,AT-76075,AT-76047:Ensure to check for a Aggregated Bank container Account setting icon is shown in Accounts Finapp and on click user lands to Edit Account popup. ", groups = {
			"Regression" }, priority = 17, enabled = true)
	public void verifyPopUpContent() throws Exception {

		deleteAcnt.verifyPopupContent();
	}

	@Test(description = "AT-76056,AT-76054:By default the Delete button should be disabled in delete account floater and it should eable only when user check the check-box appeared there.", groups = {
			"Regression" }, priority = 18, enabled = true)
	public void verifyDeleteButton() throws Exception {

		Assert.assertTrue(deleteAcnt.popUpDeleteBtn().getAttribute("class").trim()
				.contains(PropsUtil.getDataPropertyValue("DisableBtnDiscription").trim()));
	}

	@Test(description = "AT-76087,AT-76068,AT-76079,AT-76055:Delete Button should get enable after user check the box.", groups = {
			"Regression" }, priority = 19, enabled = true)
	public void verifyCBFunctionality() throws Exception {

		deleteAcnt.clickingOndeletePopUpCB();
		Assert.assertTrue(deleteAcnt.popUpDeleteBtn().isEnabled());
	}

	@Test(description = "AT-76053,AT-76049: Verify the text color in the buttons: CANCEL is in BLUE color & DELETE is in RED color.", groups = {
			"Regression" }, priority = 20, enabled = true)
	public void verifyPopupButtons() throws Exception {
		SeleniumUtil.waitForPageToLoad(3000);
		Assert.assertTrue(deleteAcnt.isCBVisibile());
		Assert.assertEquals(deleteAcnt.popUpCancelBtn().getCssValue("color"),
				PropsUtil.getDataPropertyValue("CancelButtonColour").trim());
		Assert.assertEquals(deleteAcnt.popUpDeleteBtn().getCssValue("color"),
				PropsUtil.getDataPropertyValue("DeleteButtonColour").trim());
	}
}
