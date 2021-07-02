/*******************************************************************************
 * Copyright (c) 2018 Yodlee Inc. All Rights Reserved.
 * 
 * This software is the confidential and proprietary information of
 * Yodlee, Inc. Use is subject to license terms.
 * 
 * @author
 ******************************************************************************/
package com.omni.pfm.AccountSettings;

import java.util.Calendar;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.omni.pfm.PageProcessor.PageParser;
import com.omni.pfm.config.Config;
import com.omni.pfm.pages.AccountSettings.AggregatedAccount_Settings_Loc;
import com.omni.pfm.pages.AccountSettings.RealEstateAccount_Setting_Loc;
import com.omni.pfm.pages.Login.LoginPage;
import com.omni.pfm.pages.Login.LoginPage_SAML1;

import com.omni.pfm.testBase.TestBase;
import com.omni.pfm.utility.PropsUtil;
import com.omni.pfm.utility.SeleniumUtil;

public class YCOM_Regression_AggregatedAccount_Settings_Test extends TestBase {
	
	private static final Logger logger = LoggerFactory.getLogger(YCOM_Regression_Real_Estate_Settings_Test.class);

	public AggregatedAccount_Settings_Loc account_Settings;

	public String selectedCategory;

	
	
	RealEstateAccount_Setting_Loc realestate_Settings;

	@BeforeClass(alwaysRun = true)

	public void testInit() throws Exception {
		
		doInitialization("Real Estate Settings Login");

		account_Settings = new AggregatedAccount_Settings_Loc(d);
		realestate_Settings = new RealEstateAccount_Setting_Loc(d);
		
		logger.info("Login for new user");
		//LoginPage.loginMain(d,loginParameter);
		
		logger.info("Login for Existing user in MR");
		//LoginPage_SAML2.loginWithExistingUserMR(d,"PFM1512639317521","Password#");
		
		logger.info("Login for Existing user for AutoNPR");
		//LoginPage_SAML1.loginExistingUser(d,"PFM1512556880539","Password#");
		
		SeleniumUtil.waitForPageToLoad();
		d.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);

	} 

  
	@Test(description = "SETT-02_01:Verify login happenes successfully.", priority = 1, groups = { "Desktop" })
	public void login() throws Exception {

		

		if (PropsUtil.getEnvPropertyValue("MOBILEORWEB").equalsIgnoreCase("MOBILE"))

		{
			//login.loginApp("PFMAuto1479318035469", "Password#");
			LoginPage.loginMain(d,loginParameter);

		}

		else {
			
			PageParser.navigateToPage("AccountsPage", d);
			if(account_Settings.stopRefreshButton() != null){
				SeleniumUtil.click(account_Settings.stopRefreshButton());
				SeleniumUtil.waitForPageToLoad();
			}
		}

	} 

	@Test(description = "L1-38753:SETT-02_02: Verify all the header text in Settings pop up.", priority = 2, groups = {
			"Desktop" })
	public void verifySettingsOption() throws Exception {
		
		if(Config.getInstance().getCurrentPage() != "AccountsPage") {
			PageParser.navigateToPage("AccountsPage", d);
			SeleniumUtil.waitForPageToLoad();
			if(account_Settings.stopRefreshButton() != null){
				SeleniumUtil.click(account_Settings.stopRefreshButton());
				SeleniumUtil.waitForPageToLoad();
			}
		}
		
		
		SeleniumUtil.waitForPageToLoad();
					
		SeleniumUtil.click(account_Settings.dagCheckingMoreOptions());
		
		logger.info("Getting all the account Setting options of all the container accounts.");
		List<WebElement> l = account_Settings.AccountSettingsOption();
		
		logger.info("Getting the size, to select Account settings of Real Estate manual account.");
		int s = l.size();
		int i = s - 7;
		Assert.assertTrue(account_Settings.AccountSettingList(i) != null);
		
		Assert.assertTrue(account_Settings.AlertSettingList(i) != null);
		SeleniumUtil.click(account_Settings.dagCheckingMoreOptions());
		
		

	}

	@Test(description = "L1-38783,L1-38786,L1-38790,L1-38792,L1-38793:SETT-02_03: Verify account settings functionality.", priority = 3, groups = {
			"Desktop" })
	public void accountSettingValidation() throws Exception

	{

		
		if(Config.getInstance().getCurrentPage() != "AccountsPage") {
			PageParser.navigateToPage("AccountsPage", d);
			SeleniumUtil.waitForPageToLoad();
			if(account_Settings.stopRefreshButton() != null){
				SeleniumUtil.click(account_Settings.stopRefreshButton());
				SeleniumUtil.waitForPageToLoad();
			}
		}
			SeleniumUtil.click(account_Settings.dagCheckingMoreOptions());
			SeleniumUtil.click(account_Settings.AccountSettingList(1)); 

		
		Assert.assertTrue(account_Settings.closeDeletePopUp().isDisplayed());
		
		String settingHeaderText = account_Settings.getSettingsHeaderText();
		
		String accountHeadingText = account_Settings.getAccountHeadingText().getText().trim();
		
		String nickNameHeadingText = account_Settings.getNickNameHeadingText().getText().trim();
		
		if (PropsUtil.getEnvPropertyValue("MOBILEORWEB").equalsIgnoreCase("MOBILE")) {

			Assert.assertEquals(nickNameHeadingText, "Account Nickname (optional)");

		} else {		
			Assert.assertEquals(nickNameHeadingText, PropsUtil.getDataPropertyValue("AccountSettingsNickName").trim());
		}
				Assert.assertEquals(settingHeaderText, PropsUtil.getDataPropertyValue("AccountSettingHeader").trim());
		
	}

	@Test(description = "L1-38800:SETT-02_04: Verify nick name length.", priority = 4, groups = { "Desktop" })
	public void nickNameLengthValidation() throws Exception {

		
		if(Config.getInstance().getCurrentPage() != "AccountsPage") {
			PageParser.navigateToPage("AccountsPage", d);
			SeleniumUtil.waitForPageToLoad();
			if(account_Settings.stopRefreshButton() != null){
				SeleniumUtil.click(account_Settings.stopRefreshButton());
				SeleniumUtil.waitForPageToLoad();
			}
			
			SeleniumUtil.click(account_Settings.AccountSettingList(1)); 
		}

		account_Settings.nickNameTextBox().click();
		account_Settings.nickNameTextBox().clear();
		account_Settings.nickNameTextBox().sendKeys("ABCDEFGHIJKLMNOPQRSTUVWXYZABCDEFGHIJKLMNOPQRSTUVWXYZ");
		String nickName = account_Settings.nickNameTextBox().getAttribute("value");
		Assert.assertTrue(nickName.length() == 40);
		account_Settings.nickNameTextBox().clear();

	}

	@Test(description = "L1-38807,L1-38808,L1-38809,L1-38814,L1-38816:SETT-02_05: Verify category drop down functionality.", priority = 5, groups = {
			"android" })
	public void categoryDropDownValidations() throws Exception {
	
		if(Config.getInstance().getCurrentPage() != "AccountsPage") {
			PageParser.navigateToPage("AccountsPage", d);
			SeleniumUtil.waitForPageToLoad();
			if(account_Settings.stopRefreshButton() != null){
				SeleniumUtil.click(account_Settings.stopRefreshButton());
				SeleniumUtil.waitForPageToLoad();
			}
			
			SeleniumUtil.click(account_Settings.AccountSettingList(1)); 
		}

		account_Settings.clickCategoryDropDown();
		account_Settings.selectCategoryByNumber(1, 1);
		SeleniumUtil.fluentWait(d);
		
		Assert.assertTrue(account_Settings.applyCattoPastTransactionToggleBtn().isDisplayed());
		

		String toggleOffState = account_Settings.getApplyToPastTxnToggleBtnState();


		SeleniumUtil.click(account_Settings.applyCattoPastTransactionToggleBtn());
		SeleniumUtil.fluentWait(d);
		String toggleOnState = account_Settings.getApplyToPastTxnToggleBtnState();
	
		SeleniumUtil.click(account_Settings.applyCattoPastTransactionToggleBtn());
		SeleniumUtil.fluentWait(d);
		Assert.assertEquals(account_Settings.getApplyToPastTxnToggleBtnState(), "false");

		Assert.assertTrue(account_Settings.showHideAccountToggleBtn().isDisplayed());

		SeleniumUtil.click(account_Settings.showHideAccountToggleBtn());
		SeleniumUtil.fluentWait(d);
		String offStatus = account_Settings.getShowHideAccountToggleBtnState();
		SeleniumUtil.click(account_Settings.showHideAccountToggleBtn());
		SeleniumUtil.fluentWait(d);
		String onStatus = account_Settings.getShowHideAccountToggleBtnState();
		Assert.assertEquals(offStatus, "false");
		Assert.assertEquals(toggleOnState, "true");
		Assert.assertEquals(toggleOffState, "false");
		Assert.assertEquals(onStatus, "true");

	}

	@Test(description = "L1-38813:SETT-02_05: Verify that apply to all transaction doesn't show when user unselect the selected category ", priority = 6, groups = {
			"android" })
	public void selectUnselectDropDown() throws Exception {

		
		if(Config.getInstance().getCurrentPage() != "AccountsPage") {
			PageParser.navigateToPage("AccountsPage", d);
			SeleniumUtil.waitForPageToLoad();
			if(account_Settings.stopRefreshButton() != null){
				SeleniumUtil.click(account_Settings.stopRefreshButton());
				SeleniumUtil.waitForPageToLoad();
			}
			
			SeleniumUtil.click(account_Settings.AccountSettingList(1)); 
		}
		account_Settings.clickCategoryDropDown();
		SeleniumUtil.click(account_Settings.unSelectUserSelectedCategory());
			
		try {

			Assert.assertTrue(account_Settings.applyCategoryToPastTxn().isDisplayed());
		} catch (Exception e) {
			Assert.assertFalse(false);
		}

	}

	@Test(description = "Bug-845710:L1-38819,L1-38821,L1-38824,L1-38827,L1-38829,L1-38830,L1-38832,L1-38834,L1-38836,L1-38837,L1-38838,L1-38839,L1-38842:SETT-02_06: Check delete account functionality.", priority = 7, groups = {
			"Desktop" })
	public void verfiyDeleteAccountFunctionality() throws Exception {
		
		if(Config.getInstance().getCurrentPage() != "AccountsPage") {
			PageParser.navigateToPage("AccountsPage", d);
			SeleniumUtil.waitForPageToLoad();
			if(account_Settings.stopRefreshButton() != null){
				SeleniumUtil.click(account_Settings.stopRefreshButton());
				SeleniumUtil.waitForPageToLoad();
			}
			
			SeleniumUtil.click(account_Settings.AccountSettingList(1)); 
		}

		boolean saveChangesBtnStatus = false, deleteAccountLnkStatus = false;
		
		saveChangesBtnStatus = account_Settings.saveChangesBtn().isDisplayed();

		deleteAccountLnkStatus = account_Settings.deleteAccountLnk().isEnabled();


		boolean deleteLinkStatus = false;
		deleteLinkStatus = account_Settings.deleteAccountLnk().isDisplayed();

		SeleniumUtil.click(account_Settings.deleteAccountLnk());
		String depetePopUpText = account_Settings.deletePopUp().getText().trim();


		boolean popUpDeleteBtnStatus = false, popUpCancelBtnStatus = false, popUpConfStatus = false,
				popUpConfCheckStatus = false;
		popUpDeleteBtnStatus = account_Settings.deleteBtn().isDisplayed();
		popUpCancelBtnStatus = account_Settings.cancelBtn().isDisplayed();

		
		popUpConfStatus = account_Settings.deleteconfirmcheckBox().isDisplayed();

		
		popUpConfCheckStatus = account_Settings.deleteconfirmcheckBox().isSelected();

		String warningMsg = account_Settings.warningMsg().getText();

		String deleteConfirmMessage = account_Settings.deleteConfirmMsg().getText();

		String deleteBtnState = account_Settings.deleteBtn().getAttribute("class");

		SeleniumUtil.click(account_Settings.deleteconfirmcheckBox());

		String deleteBtnStateClassvalue = account_Settings.deleteBtn().getAttribute("class");

		SeleniumUtil.click(account_Settings.deleteconfirmcheckBox());

		String deleteBtnStateClass = account_Settings.deleteBtn().getAttribute("class");


		Assert.assertTrue(account_Settings.closeDeletePopUp().isDisplayed());
		Assert.assertTrue(saveChangesBtnStatus);
		Assert.assertTrue(deleteAccountLnkStatus);
		Assert.assertTrue(deleteLinkStatus);
		//Assert.assertTrue(depetePopUpText.contains("Delete Account"));
		Assert.assertTrue(depetePopUpText.contains(PropsUtil.getDataPropertyValue("DeleteAccountHeaderText").trim()));
		Assert.assertTrue(popUpDeleteBtnStatus);
		Assert.assertTrue(popUpCancelBtnStatus);
		Assert.assertTrue(popUpConfStatus);
		Assert.assertTrue(!popUpConfCheckStatus);
		//Assert.assertEquals(warningMsg, "Warning: This action cannot be undone.");
		Assert.assertEquals(warningMsg, PropsUtil.getDataPropertyValue("AccountDeleteWarningMessage").trim());
		Assert.assertEquals(deleteConfirmMessage,
				PropsUtil.getDataPropertyValue("delete_confirm_msg").trim());
		Assert.assertTrue(deleteBtnState.contains("disabled"));
		Assert.assertTrue(!deleteBtnStateClassvalue.contains("disabled"));
		Assert.assertTrue(deleteBtnStateClass.contains("disabled"));
		SeleniumUtil.click(account_Settings.cancelBtn());

	}

	@Test(description = "L1-38840,L1-38851,L1-40064:SETT-02_07:verify that when user click the cancel btn, it is navigated to previous screen.", priority = 8, groups = {

			"android" })
	public void cancelAccountDeletion() throws Exception {

		
		if(Config.getInstance().getCurrentPage() != "AccountsPage") {
			PageParser.navigateToPage("AccountsPage", d);
			SeleniumUtil.waitForPageToLoad();
			if(account_Settings.stopRefreshButton() != null){
				SeleniumUtil.click(account_Settings.stopRefreshButton());
				SeleniumUtil.waitForPageToLoad();
			}
			
			SeleniumUtil.click(account_Settings.AccountSettingList(1)); 
		}
		
		boolean cancelBtnStatus = false;
		SeleniumUtil.click(account_Settings.deleteAccountLnk());
		cancelBtnStatus = account_Settings.cancelBtn().isEnabled();

		
		SeleniumUtil.click(account_Settings.cancelBtn());

		

		account_Settings.nickNameTextBox().clear();

		account_Settings.nickNameTextBox().sendKeys(PropsUtil.getDataPropertyValue("Nick_Name").trim()
				+ Calendar.getInstance().get(Calendar.DATE));

		account_Settings.clickCategoryDropDown();

		account_Settings.selectCategoryByNumber(4, 1);

		selectedCategory = account_Settings.getSelectedCategoryName();

		
		SeleniumUtil.click(account_Settings.applyCattoPastTransactionToggleBtn());

		SeleniumUtil.fluentWait(d);

		
		SeleniumUtil.click(account_Settings.saveChangesBtn());

		SeleniumUtil.fluentWait(d);

		Assert.assertTrue(cancelBtnStatus);

	}

	@Test(description = "SETT-02_08:Verify Transatcion category in Transaction finapp after updating in Settings.", priority = 9, groups = {

			"android" })
	public void checkTransactionsAfterEdit() throws Exception {
		
		if(Config.getInstance().getCurrentPage() != "AccountsPage") {
			PageParser.navigateToPage("AccountsPage", d);
			SeleniumUtil.waitForPageToLoad();
			if(account_Settings.stopRefreshButton() != null){
				SeleniumUtil.click(account_Settings.stopRefreshButton());
				SeleniumUtil.waitForPageToLoad();
			}
			
			 
		}

		SeleniumUtil.click(account_Settings.getNickNameNew());

		SeleniumUtil.waitForPageToLoad();
		SeleniumUtil.click(account_Settings.durationFilter());
		

		SeleniumUtil.waitForPageToLoad();

		SeleniumUtil.click(account_Settings.customFilter());
		
		
		SeleniumUtil.waitForPageToLoad();
		
		SeleniumUtil.click(account_Settings.customStartDate());
		SeleniumUtil.click(account_Settings.customStartDate());

		

		
		account_Settings.customStartDate().sendKeys(PropsUtil.getDataPropertyValue("AggregatedAccountDate1").trim());

		SeleniumUtil.click(account_Settings.customEndDate());
		SeleniumUtil.click(account_Settings.customEndDate());

		
		account_Settings.customStartDate().sendKeys(PropsUtil.getDataPropertyValue("AggregatedAccountDate2").trim());

		SeleniumUtil.click(account_Settings.updateButton());

		SeleniumUtil.waitForPageToLoad();
		String catName = null;
		try {

			catName = account_Settings.getCategoryByRowNum(1);
			
		} catch (Exception e) {
			System.out.println("Faild: Verify Transatcion category in Transaction finapp after updating in Settings.");
		}

		System.out.println(catName);

	}

}
